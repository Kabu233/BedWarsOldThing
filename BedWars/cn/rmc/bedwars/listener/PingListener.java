/*    */ package cn.rmc.bedwars.listener;
/*    */ 
/*    */ import cn.rmc.bedwars.BedWars;
/*    */ import cn.rmc.bedwars.enums.game.GameState;
/*    */ import cn.rmc.bedwars.game.Game;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.server.ServerListPingEvent;
/*    */ 
/*    */ public class PingListener implements Listener {
/*    */   @EventHandler
/*    */   public void onPing(ServerListPingEvent e) {
/* 13 */     Game game = BedWars.getInstance().getGameManager().getGameArrayList().get(0);
/* 14 */     e.setMaxPlayers(game.getMaxplayer());
/* 15 */     if (game.isPrivateGame()) {
/* 16 */       e.setMotd("私人模式");
/*    */       return;
/*    */     } 
/* 19 */     switch (game.getState()) {
/*    */       case WAITING:
/*    */       case COUNTING:
/* 22 */         e.setMotd("等待中");
/*    */         break;
/*    */       case FIGHTING:
/* 25 */         e.setMotd("战斗中");
/*    */         break;
/*    */       case ENDING:
/* 28 */         e.setMotd("结束中");
/*    */         break;
/*    */     } 
/*    */   }
/*    */ }