/*    */ package net.jitse.npclib.api.state;
/*    */ 
/*    */ public enum NPCAnimation
/*    */ {
/*  5 */   SWING_MAINHAND(0),
/*  6 */   TAKE_DAMAGE(1),
/*  7 */   SWING_OFFHAND(3),
/*  8 */   CRITICAL_DAMAGE(4),
/*  9 */   MAGICAL_DAMAGE(5);
/*    */   
/*    */   private int id;
/*    */   
/*    */   NPCAnimation(int id) {
/* 14 */     this.id = id;
/*    */   }
/*    */   
/*    */   public int getId() {
/* 18 */     return this.id;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\BedWarsLobby-1.0-SNAPSHOT.jar!\net\jitse\npclib\api\state\NPCAnimation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */