/*    */ package net.jitse.npclib.internal;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum MinecraftVersion
/*    */ {
/*  9 */   V1_8_R3,
/* 10 */   V1_9_R1,
/* 11 */   V1_9_R2,
/* 12 */   V1_10_R1,
/* 13 */   V1_11_R1,
/* 14 */   V1_12_R1,
/* 15 */   V1_13_R1,
/* 16 */   V1_13_R2,
/* 17 */   V1_14_R1,
/* 18 */   V1_15_R1,
/* 19 */   V1_16_R1,
/* 20 */   V1_16_R2,
/* 21 */   V1_16_R3,
/* 22 */   V1_17_R1,
/* 23 */   V1_18_R1,
/* 24 */   V1_18_R2,
/* 25 */   V1_19_R1,
/* 26 */   V1_19_R2,
/* 27 */   V1_19_R3,
/* 28 */   V1_20_R1,
/* 29 */   V1_20_R2,
/* 30 */   V1_20_R3,
/* 31 */   V1_20_R4,
/* 32 */   V1_21_R1,
/* 33 */   V1_21_R2,
/* 34 */   V1_21_R3,
/* 35 */   V1_21_R4;
/*    */   
/*    */   public boolean isAboveOrEqual(MinecraftVersion compare) {
/* 38 */     return (ordinal() >= compare.ordinal());
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\BedWarsLobby-1.0-SNAPSHOT.jar!\net\jitse\npclib\internal\MinecraftVersion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */