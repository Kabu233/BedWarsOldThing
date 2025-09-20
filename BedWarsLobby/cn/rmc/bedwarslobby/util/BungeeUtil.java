/*    */ package cn.rmc.bedwarslobby.util;
/*    */ 
/*    */ import com.google.common.io.ByteArrayDataOutput;
/*    */ import com.google.common.io.ByteStreams;
/*    */ import cn.rmc.bedwarslobby.BedWarsLobby;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ public class BungeeUtil {
/*    */   public static void sendPlayer(Player p, String server) {
/* 11 */     ByteArrayDataOutput out = ByteStreams.newDataOutput();
/* 12 */     out.writeUTF("Connect");
/* 13 */     out.writeUTF(server);
/* 14 */     p.sendPluginMessage((Plugin)BedWarsLobby.getInstance(), "BungeeCord", out.toByteArray());
/*    */   }
/*    */ }