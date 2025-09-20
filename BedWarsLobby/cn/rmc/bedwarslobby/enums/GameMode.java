/*    */ package cn.rmc.bedwarslobby.enums;public enum GameMode {
/*    */   String displayname;
/*    */   TotalGM totalGM;
/*    */   Boolean isSpecial;
/*    */   String submode;
/*  6 */   FOUR_FOUR("4v4v4v4", "TEAM", TotalGM.NORMAL, Integer.valueOf(4), Boolean.valueOf(false)),
/*  7 */   EIGHT_ONE("单人模式", "SOLO", TotalGM.NORMAL, Integer.valueOf(1), Boolean.valueOf(true)),
/*  8 */   FOUR_FOUR_ULTIMATE("超能力起床 4v4v4v4", "TEAM", TotalGM.DREAMS, Integer.valueOf(4), Boolean.valueOf(false)),
/*  9 */   FOUR_FOUR_RUSH("疾速起床 4v4v4v4", "TEAM", TotalGM.DREAMS, Integer.valueOf(4), Boolean.valueOf(false)),
/* 10 */   FOUR_FOUR_ARMED("枪械起床 4v4v4v4", "TEAM", TotalGM.DREAMS, Integer.valueOf(4), Boolean.valueOf(false)); Integer amount;
/*    */   public String getDisplayname() {
/* 12 */     return this.displayname;
/*    */   } public TotalGM getTotalGM() {
/* 14 */     return this.totalGM;
/*    */   } public Boolean getIsSpecial() {
/* 16 */     return this.isSpecial;
/*    */   } public String getSubmode() {
/* 18 */     return this.submode;
/*    */   } public Integer getAmount() {
/* 20 */     return this.amount;
/*    */   }
/*    */   
/*    */   GameMode(String dpname, String submode, TotalGM tg, Integer players, Boolean isSpecial) {
/* 24 */     this.displayname = dpname;
/* 25 */     this.submode = submode;
/* 26 */     this.totalGM = tg;
/* 27 */     this.amount = players;
/* 28 */     this.isSpecial = isSpecial;
/*    */   }
/*    */   GameMode(String dpname, TotalGM tg, Boolean isSpecial) {
/* 31 */     this.displayname = dpname;
/* 32 */     this.totalGM = tg;
/* 33 */     this.isSpecial = isSpecial;
/*    */   }
/*    */   
/*    */   public enum TotalGM {
/* 37 */     NORMAL("综合统计"),
/* 38 */     DREAMS("§6梦幻模式"); public String dpname; public String getDpname() {
/* 39 */       return this.dpname;
/*    */     }
/*    */     
/*    */     TotalGM(String dpnam) {
/* 43 */       this.dpname = dpnam;
/*    */     }
/*    */   }
/*    */ }