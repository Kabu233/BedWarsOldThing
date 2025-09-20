/*    */ package cn.rmc.bedwars.enums;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum Data
/*    */ {
/* 11 */   PlayerInfo("PlayerInfo"),
/* 12 */   PlayerStats("PlayerStats"),
/* 13 */   MatchInfo("MatchInfo");
/*    */   
/*    */   String where;
/*    */   
/*    */   Data(String where) {
/* 18 */     this.where = where;
/*    */   }
/*    */   
/*    */   public String getWhere() {
/* 22 */     return this.where;
/*    */   }
/*    */   
/*    */   public enum Field {
/*    */     String where;
/* 27 */     UUID("UUID", Data.PlayerInfo),
/* 28 */     LEVEL("Level", Data.PlayerInfo),
/* 29 */     COIN("Coins", Data.PlayerInfo),
/* 30 */     DREAMSINFO("DreamsInfo", Data.PlayerInfo),
/* 31 */     EXPERIENCE("Experience", Data.PlayerInfo),
/* 32 */     QUICKSHOP("QuickShop", Data.PlayerInfo),
/* 33 */     COLLECTED("Collected", Data.PlayerInfo),
/* 34 */     StatsUUID("UUID", Data.PlayerStats),
/* 35 */     GAMEMODE("Mode", Data.PlayerStats),
/* 36 */     WINSTREAK("Winstreak", Data.PlayerStats),
/* 37 */     WIN("Wins", Data.PlayerStats),
/* 38 */     LOSS("Losses", Data.PlayerStats),
/* 39 */     KILL("Kills", Data.PlayerStats),
/* 40 */     DEATH("Deaths", Data.PlayerStats),
/* 41 */     FINALKILL("FinalKills", Data.PlayerStats),
/* 42 */     FINALDEATH("FinalDeaths", Data.PlayerStats),
/* 43 */     BEDSBROKEN("BedsBroken", Data.PlayerStats),
/* 44 */     BEDSBREAK("BedsBreak", Data.PlayerStats),
/* 45 */     BLOCKPLACED("Block_Placed", Data.PlayerStats),
/* 46 */     BLOCKBROKEN("Block_Broken", Data.PlayerStats),
/* 47 */     DAILY_FINALKILLS("Daily_FinalKills", Data.PlayerStats),
/* 48 */     DAILY_WINS("Daily_Wins", Data.PlayerStats),
/* 49 */     WEEKLY_FINALKILLS("Weekly_FinalKills", Data.PlayerStats),
/* 50 */     WEEKLY_WINS("Weekly_Wins", Data.PlayerStats),
/* 51 */     MatchUUID("UUID", Data.MatchInfo),
/* 52 */     MATCHSTARTTINESTAMP("StartTimeStamp", Data.MatchInfo),
/* 53 */     MATCHENDTIMESTAMP("EndTimeStamp", Data.MatchInfo),
/* 54 */     MATCHINFO("Info", Data.MatchInfo); Data data;
/*    */     public String getWhere() {
/* 56 */       return this.where; } public Data getData() {
/* 57 */       return this.data;
/*    */     }
/*    */     Field(String where, Data data) {
/* 60 */       this.where = where;
/* 61 */       this.data = data;
/*    */     }
/*    */     
/*    */     public static List<Field> getValues(Data data, Field... except) {
/* 65 */       List<Field> fields = new ArrayList<>();
/* 66 */       for (Field value : values()) {
/* 67 */         if (value.getData() == data && 
/* 68 */           !Arrays.<Field>asList(except).contains(value))
/* 69 */           fields.add(value); 
/*    */       } 
/* 71 */       return fields;
/*    */     }
/*    */   }
/*    */ }