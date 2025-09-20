/*    */ package net.jitse.npclib.api.state;
/*    */ 
/*    */ public enum NPCSlot
/*    */ {
/*  5 */   HELMET(4, "HEAD"),
/*  6 */   CHESTPLATE(3, "CHEST"),
/*  7 */   LEGGINGS(2, "LEGS"),
/*  8 */   BOOTS(1, "FEET"),
/*  9 */   MAINHAND(0, "MAINHAND"),
/* 10 */   OFFHAND(5, "OFFHAND");
/*    */   
/*    */   private final int slot;
/*    */   private final String nmsName;
/*    */   
/*    */   NPCSlot(int slot, String nmsName) {
/* 16 */     this.slot = slot;
/* 17 */     this.nmsName = nmsName;
/*    */   }
/*    */   
/*    */   public int getSlot() {
/* 21 */     return this.slot;
/*    */   }
/*    */   
/*    */   public String getNmsName() {
/* 25 */     return this.nmsName;
/*    */   }
/*    */   
/*    */   public <E extends Enum<E>> E getNmsEnum(Class<E> nmsEnumClass) {
/* 29 */     return Enum.valueOf(nmsEnumClass, this.nmsName);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\BedWarsLobby-1.0-SNAPSHOT.jar!\net\jitse\npclib\api\state\NPCSlot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */