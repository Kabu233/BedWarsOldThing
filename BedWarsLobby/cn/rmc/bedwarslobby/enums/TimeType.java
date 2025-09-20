/*    */ package cn.rmc.bedwarslobby.enums;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ 
/*    */ public enum TimeType
/*    */ {
/*    */   String name;
/*  9 */   LIFETIME("生涯"),
/* 10 */   WEEKLY("每周"),
/* 11 */   DAILY("每天"); public String getName() {
/* 12 */     return this.name;
/*    */   }
/*    */   TimeType(String name) {
/* 15 */     this.name = name;
/*    */   }
/*    */   public static List<TimeType> getValues() {
/* 18 */     return Arrays.asList((Object[])values().clone());
/*    */   }
/*    */ }