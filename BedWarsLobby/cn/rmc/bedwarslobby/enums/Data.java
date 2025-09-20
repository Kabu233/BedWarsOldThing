/*    */ package cn.rmc.bedwarslobby.enums;
/*    */ 
/*    */ 
/*    */ public enum Data
/*    */ {
/*  6 */   PlayerInfo("PlayerInfo"),
/*  7 */   PlayerStats("PlayerStats"),
/*  8 */   MatchInfo("MatchInfo"); String where;
/*    */   public String getWhere() {
/* 10 */     return this.where;
/*    */   }
/*    */   
/*    */   Data(String where) {
/* 14 */     this.where = where;
/*    */   }
/*    */   
/*    */   public enum Field { String where;
/* 18 */     UUID("UUID", Data.PlayerInfo),
/* 19 */     LEVEL("Level", Data.PlayerInfo),
/* 20 */     COIN("Coins", Data.PlayerInfo),
/* 21 */     DREAMSINFO("DreamsInfo", Data.PlayerInfo),
/* 22 */     EXPERIENCE("Experience", Data.PlayerInfo),
/* 23 */     QUICKSHOP("QuickShop", Data.PlayerInfo),
/* 24 */     COLLECTED("Collected", Data.PlayerInfo),
/* 25 */     StatsUUID("UUID", Data.PlayerStats),
/* 26 */     GAMEMODE("Mode", Data.PlayerStats),
/* 27 */     WINSTREAK("Winstreak", Data.PlayerStats),
/* 28 */     WIN("Wins", Data.PlayerStats),
/* 29 */     LOSS("Losses", Data.PlayerStats),
/* 30 */     KILL("Kills", Data.PlayerStats),
/* 31 */     DEATH("Deaths", Data.PlayerStats),
/* 32 */     FINALKILL("FinalKills", Data.PlayerStats),
/* 33 */     FINALDEATH("FinalDeaths", Data.PlayerStats),
/* 34 */     BEDSBROKEN("BedsBroken", Data.PlayerStats),
/* 35 */     BEDSBREAK("BedsBreak", Data.PlayerStats),
/* 36 */     BLOCKPLACED("Block_Placed", Data.PlayerStats),
/* 37 */     BLOCKBROKEN("Block_Broken", Data.PlayerStats),
/* 38 */     DAILY_FINALKILLS("Daily_FinalKills", Data.PlayerStats),
/* 39 */     DAILY_WINS("Daily_Wins", Data.PlayerStats),
/* 40 */     WEEKLY_FINALKILLS("Weekly_FinalKills", Data.PlayerStats),
/* 41 */     WEEKLY_WINS("Weekly_Wins", Data.PlayerStats),
/* 42 */     MatchUUID("UUID", Data.MatchInfo),
/* 43 */     MATCHSTARTTINESTAMP("StartTimeStamp", Data.MatchInfo),
/* 44 */     MATCHENDTIMESTAMP("EndTimeStamp", Data.MatchInfo),
/* 45 */     MATCHINFO("Info", Data.MatchInfo); Data data; public String getWhere() {
/* 46 */       return this.where;
/*    */     } public Data getData() {
/* 48 */       return this.data;
/*    */     }
/*    */     
/*    */     Field(String where, Data data) {
/* 52 */       this.where = where;
/* 53 */       this.data = data;
/*    */     } }
/*    */ 
/*    */ }