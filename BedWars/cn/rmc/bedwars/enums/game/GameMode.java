/*    */ package cn.rmc.bedwars.enums.game;
/*    */ 
/*    */ public enum GameMode {
/*    */   String displayname;
/*    */   TotalGM totalGM;
/*    */   Boolean isSpecial;
/*  7 */   FOUR_FOUR("4v4v4v4", "TEAM", TotalGM.NORMAL, Boolean.valueOf(false)),
/*  8 */   EIGHT_ONE("单人模式", "SOLO", TotalGM.NORMAL, Boolean.valueOf(true)),
/*  9 */   FOUR_FOUR_ULTIMATE("超能力起床 4v4v4v4", "TEAM", TotalGM.DREAMS, Boolean.valueOf(false)),
/* 10 */   FOUR_FOUR_RUSH("急速起床 4v4v4v4", "TEAM", TotalGM.DREAMS, Boolean.valueOf(false)),
/* 11 */   FOUR_FOUR_ARMED("枪械起床 4v4v4v4", "TEAM", TotalGM.DREAMS, Boolean.valueOf(false)); String submode;
/*    */   public String getDisplayname() {
/* 13 */     return this.displayname; }
/* 14 */   public TotalGM getTotalGM() { return this.totalGM; }
/* 15 */   public Boolean getIsSpecial() { return this.isSpecial; } public String getSubmode() {
/* 16 */     return this.submode;
/*    */   }
/*    */   GameMode(String dpname, String submode, TotalGM tg, Boolean isSpecial) {
/* 19 */     this.displayname = dpname;
/* 20 */     this.submode = submode;
/* 21 */     this.totalGM = tg;
/* 22 */     this.isSpecial = isSpecial;
/*    */   }
/*    */   
/*    */   GameMode(String dpname, TotalGM tg, Boolean isSpecial) {
/* 26 */     this.displayname = dpname;
/* 27 */     this.totalGM = tg;
/* 28 */     this.isSpecial = isSpecial;
/*    */   }
/*    */   
/*    */   public enum TotalGM {
/* 32 */     NORMAL(""),
/* 33 */     DREAMS("§6梦幻模式");
/*    */     
/*    */     public String dpname;
/*    */     
/*    */     TotalGM(String dpnam) {
/* 38 */       this.dpname = dpnam;
/*    */     }
/*    */     
/*    */     public String getDpname() {
/* 42 */       return this.dpname;
/*    */     }
/*    */   }
/*    */ }