/*    */ package net.jitse.npclib.api.state;
/*    */ 
/*    */ import java.util.Collection;
/*    */ 
/*    */ public enum NPCState
/*    */ {
/*  7 */   STANDING((byte)0),
/*  8 */   ON_FIRE((byte)1),
/*  9 */   CROUCHED((byte)2),
/* 10 */   INVISIBLE((byte)32);
/*    */   
/*    */   private final byte b;
/*    */   
/*    */   NPCState(byte b) {
/* 15 */     this.b = b;
/*    */   }
/*    */   
/*    */   public byte getByte() {
/* 19 */     return this.b;
/*    */   }
/*    */   
/*    */   public static byte getMasked(NPCState... states) {
/* 23 */     byte mask = 0;
/* 24 */     for (NPCState state : states) mask = (byte)(mask | state.getByte()); 
/* 25 */     return mask;
/*    */   }
/*    */   
/*    */   public static byte getMasked(Collection<NPCState> states) {
/* 29 */     byte mask = 0;
/* 30 */     for (NPCState state : states) mask = (byte)(mask | state.getByte()); 
/* 31 */     return mask;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\BedWarsLobby-1.0-SNAPSHOT.jar!\net\jitse\npclib\api\state\NPCState.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */