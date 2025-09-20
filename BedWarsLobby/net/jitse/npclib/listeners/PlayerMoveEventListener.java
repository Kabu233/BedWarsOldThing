/*    */ package net.jitse.npclib.listeners;
/*    */ 
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.player.PlayerMoveEvent;
/*    */ 
/*    */ public class PlayerMoveEventListener
/*    */   extends HandleMoveBase implements Listener {
/*    */   @EventHandler
/*    */   public void onPlayerMove(PlayerMoveEvent event) {
/* 12 */     Location from = event.getFrom();
/* 13 */     Location to = event.getTo();
/*    */ 
/*    */     
/* 16 */     if (to != null && (from.getBlockX() != to.getBlockX() || from
/* 17 */       .getBlockY() != to.getBlockY() || from
/* 18 */       .getBlockZ() != to.getBlockZ()))
/* 19 */       handleMove(event.getPlayer()); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\BedWarsLobby-1.0-SNAPSHOT.jar!\net\jitse\npclib\listeners\PlayerMoveEventListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */