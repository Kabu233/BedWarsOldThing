/*    */ package cn.rmc.bedwarslobby.listener;
/*    */ 
/*    */ import cn.rmc.bedwarslobby.BedWarsLobby;
/*    */ import cn.rmc.bedwarslobby.object.PlayerData;
/*    */ import cn.rmc.bedwarslobby.util.LevelUtils;
/*    */ import cn.rmc.bedwarslobby.util.LuckPermsUtil;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.player.AsyncPlayerChatEvent;
/*    */ 
/*    */ public class PlayerChatListener
/*    */   implements Listener {
/*    */   @EventHandler
/*    */   public void onChat(AsyncPlayerChatEvent e) {
/* 16 */     Player p = e.getPlayer();
/* 17 */     PlayerData pd = BedWarsLobby.getInstance().getPlayerManager().get(p);
/* 18 */     e.setFormat(LevelUtils.getLevelwithColorByLevel(pd.getCurrentLevel().intValue()) + " " + LuckPermsUtil.getPrefix(p) + p.getName() + LuckPermsUtil.getSuffix(p) + "§f: " + e.getMessage());
/*    */   }
/*    */ }