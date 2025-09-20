/*    */ package cn.rmc.bedwars.utils.player;
/*    */ import com.google.common.io.ByteArrayDataOutput;
/*    */ import com.google.common.io.ByteStreams;
/*    */ import cn.rmc.bedwars.BedWars;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ public class BungeeUtil {
/*    */   public static void sendPlayer(Player p, String server) {
/* 10 */     ByteArrayDataOutput out = ByteStreams.newDataOutput();
/* 11 */     out.writeUTF("Connect");
/* 12 */     out.writeUTF(server);
/* 13 */     p.sendPluginMessage((Plugin)BedWars.getInstance(), "BungeeCord", out.toByteArray());
/*    */   }
/*    */ }