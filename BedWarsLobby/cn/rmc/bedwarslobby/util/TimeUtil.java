/*    */ package cn.rmc.bedwarslobby.util;
/*    */ 
/*    */ public class TimeUtil
/*    */ {
/*    */   public static String getTime(int seconds) {
/*  6 */     if (seconds < 60) {
/*  7 */       return seconds + "秒";
/*    */     }
/*  9 */     int minutes = seconds / 60;
/* 10 */     int s = 60 * minutes;
/* 11 */     int secondsLeft = seconds - s;
/* 12 */     if (minutes < 60) {
/* 13 */       return (secondsLeft > 0) ? (minutes + "分钟" + secondsLeft + "秒") : (minutes + "分钟");
/*    */     }
/* 15 */     if (minutes < 1440) {
/* 16 */       int days = minutes / 60;
/* 17 */       String time = days + "小时";
/* 18 */       int inMins = 60 * days;
/* 19 */       int leftOver = minutes - inMins;
/* 20 */       if (leftOver >= 1) {
/* 21 */         time = time + " " + leftOver + "分钟";
/*    */       }
/* 23 */       if (secondsLeft > 0) {
/* 24 */         time = time + " " + secondsLeft + "秒";
/*    */       }
/* 26 */       return time;
/*    */     } 
/* 28 */     int days2 = minutes / 1440;
/* 29 */     String time2 = days2 + "天";
/* 30 */     int inMins2 = 1440 * days2;
/* 31 */     int leftOver2 = minutes - inMins2;
/* 32 */     if (leftOver2 >= 1) {
/* 33 */       if (leftOver2 < 60) {
/* 34 */         time2 = time2 + " " + leftOver2 + "分钟";
/*    */       } else {
/* 36 */         int hours = leftOver2 / 60;
/* 37 */         String time3 = time2 + " " + hours + "小时";
/* 38 */         int hoursInMins = 60 * hours;
/* 39 */         int minsLeft = leftOver2 - hoursInMins;
/* 40 */         time2 = time3 + " " + minsLeft + "分钟";
/*    */       } 
/*    */     }
/* 43 */     if (secondsLeft > 0) {
/* 44 */       time2 = time2 + " " + secondsLeft + "秒";
/*    */     }
/* 46 */     return time2;
/*    */   }
/*    */ }