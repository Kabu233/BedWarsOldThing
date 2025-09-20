/*    */ package net.jitse.npclib;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NPCLibOptions
/*    */ {
/* 18 */   MovementHandling moveHandling = MovementHandling.playerMoveEvent();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public NPCLibOptions setMovementHandling(MovementHandling moveHandling) {
/* 33 */     this.moveHandling = moveHandling;
/* 34 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static class MovementHandling
/*    */   {
/*    */     final boolean usePme;
/*    */ 
/*    */     
/*    */     final long updateInterval;
/*    */ 
/*    */ 
/*    */     
/*    */     private MovementHandling(boolean usePme, long updateInterval) {
/* 49 */       this.usePme = usePme;
/* 50 */       this.updateInterval = updateInterval;
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public static MovementHandling playerMoveEvent() {
/* 59 */       return new MovementHandling(true, 0L);
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public static MovementHandling repeatingTask(long updateInterval) {
/* 69 */       if (updateInterval <= 0L) {
/* 70 */         throw new IllegalArgumentException("Negative update interval");
/*    */       }
/* 72 */       return new MovementHandling(false, updateInterval);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\BedWarsLobby-1.0-SNAPSHOT.jar!\net\jitse\npclib\NPCLibOptions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */