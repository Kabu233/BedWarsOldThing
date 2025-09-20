/*    */ package cn.rmc.bedwars.utils.nametag;
/*    */ 
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NametagThread
/*    */   extends Thread
/*    */ {
/*    */   private NametagHandler handler;
/*    */   
/*    */   public NametagThread(NametagHandler handler) {
/* 15 */     this.handler = handler;
/* 16 */     start();
/*    */   }
/*    */   
/*    */   public void run() {
/*    */     while (true) {
/*    */       try {
/*    */         while (true)
/* 23 */         { tick();
/* 24 */           sleep(50L * this.handler.getTicks()); }  break;
/* 25 */       } catch (Exception e) {
/* 26 */         e.printStackTrace();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private void tick() {
/* 35 */     if (this.handler.getAdapter() == null) {
/*    */       return;
/*    */     }
/*    */     
/* 39 */     for (Player player : this.handler.getPlugin().getServer().getOnlinePlayers()) {
/* 40 */       NametagBoard board = this.handler.getBoards().get(player.getUniqueId());
/*    */ 
/*    */       
/* 43 */       if (board != null)
/* 44 */         board.update(); 
/*    */     } 
/*    */   }
/*    */ }