/*     */ package cn.rmc.bedwars.utils;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ public final class TimeUtils {
/*   9 */   private static final ThreadLocal<StringBuilder> mmssBuilder = ThreadLocal.withInitial(StringBuilder::new);
/*  10 */   private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String formatIntoHHMMSS(int secs) {
/*  16 */     return formatIntoMMSS(secs);
/*     */   }
/*     */   
/*     */   public static String formatLongIntoHHMMSS(long secs) {
/*  20 */     int unconvertedSeconds = (int)secs;
/*  21 */     return formatIntoMMSS(unconvertedSeconds);
/*     */   }
/*     */   
/*     */   public static String formatIntoMMSS(int secs) {
/*  25 */     int seconds = secs % 60;
/*  26 */     secs -= seconds;
/*  27 */     long minutesCount = (secs / 60);
/*  28 */     long minutes = minutesCount % 60L;
/*  29 */     minutesCount -= minutes;
/*  30 */     long hours = minutesCount / 60L;
/*  31 */     StringBuilder result = mmssBuilder.get();
/*  32 */     result.setLength(0);
/*  33 */     if (hours > 0L) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  41 */       result.append(hours);
/*  42 */       result.append(":");
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  52 */     result.append(minutes);
/*  53 */     result.append(":");
/*  54 */     if (seconds < 10) {
/*  55 */       result.append("0");
/*     */     }
/*     */     
/*  58 */     result.append(seconds);
/*  59 */     return result.toString();
/*     */   }
/*     */   
/*     */   public static String formatLongIntoMMSS(long secs) {
/*  63 */     int unconvertedSeconds = (int)secs;
/*  64 */     return formatIntoMMSS(unconvertedSeconds);
/*     */   }
/*     */   
/*     */   public static String formatIntoDetailedString(int secs) {
/*  68 */     if (secs == 0) {
/*  69 */       return "0 seconds";
/*     */     }
/*  71 */     int remainder = secs % 86400;
/*  72 */     int days = secs / 86400;
/*  73 */     int hours = remainder / 3600;
/*  74 */     int minutes = remainder / 60 - hours * 60;
/*  75 */     int seconds = remainder % 3600 - minutes * 60;
/*  76 */     String fDays = (days > 0) ? (" " + days + " day" + ((days > 1) ? "s" : "")) : "";
/*  77 */     String fHours = (hours > 0) ? (" " + hours + " hour" + ((hours > 1) ? "s" : "")) : "";
/*  78 */     String fMinutes = (minutes > 0) ? (" " + minutes + " minute" + ((minutes > 1) ? "s" : "")) : "";
/*  79 */     String fSeconds = (seconds > 0) ? (" " + seconds + " second" + ((seconds > 1) ? "s" : "")) : "";
/*  80 */     return (fDays + fHours + fMinutes + fSeconds).trim();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String formatLongIntoDetailedString(long secs) {
/*  85 */     int unconvertedSeconds = (int)secs;
/*  86 */     return formatIntoDetailedString(unconvertedSeconds);
/*     */   }
/*     */   
/*     */   public static String formatIntoCalendarString(Date date) {
/*  90 */     return dateFormat.format(date);
/*     */   }
/*     */   
/*     */   public static int parseTime(String time) {
/*  94 */     if (!time.equals("0") && !time.equals("")) {
/*  95 */       String[] lifeMatch = { "w", "d", "h", "m", "s" };
/*  96 */       int[] lifeInterval = { 604800, 86400, 3600, 60, 1 };
/*  97 */       int seconds = -1;
/*     */       
/*  99 */       for (int i = 0; i < lifeMatch.length; i++) {
/* 100 */         for (Matcher matcher = Pattern.compile("([0-9]+)" + lifeMatch[i]).matcher(time); matcher.find(); seconds += Integer.parseInt(matcher.group(1)) * lifeInterval[i]) {
/* 101 */           if (seconds == -1) {
/* 102 */             seconds = 0;
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 107 */       if (seconds == -1) {
/* 108 */         throw new IllegalArgumentException("Invalid time provided.");
/*     */       }
/* 110 */       return seconds;
/*     */     } 
/*     */     
/* 113 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getSecondsBetween(Date a, Date b) {
/* 119 */     return (int)getSecondsBetweenLong(a, b);
/*     */   }
/*     */   
/*     */   public static long getSecondsBetweenLong(Date a, Date b) {
/* 123 */     long diff = a.getTime() - b.getTime();
/* 124 */     long absDiff = Math.abs(diff);
/* 125 */     return absDiff / 1000L;
/*     */   }
/*     */   public static String getTime(int seconds) {
/* 128 */     if (seconds < 60) {
/* 129 */       return seconds + "秒";
/*     */     }
/* 131 */     int minutes = seconds / 60;
/* 132 */     int s = 60 * minutes;
/* 133 */     int secondsLeft = seconds - s;
/* 134 */     if (minutes < 60) {
/* 135 */       return (secondsLeft > 0) ? (minutes + "分钟" + secondsLeft + "秒") : (minutes + "分钟");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 141 */     if (minutes < 1440) {
/* 142 */       int i = minutes / 60;
/* 143 */       String str = i + "h";
/* 144 */       int j = 60 * i;
/* 145 */       int k = minutes - j;
/* 146 */       if (k >= 1) {
/* 147 */         str = str + " " + k + "分钟";
/*     */       }
/*     */       
/* 150 */       if (secondsLeft > 0) {
/* 151 */         str = str + " " + secondsLeft + "秒";
/*     */       }
/*     */       
/* 154 */       return str;
/*     */     } 
/* 156 */     int days = minutes / 1440;
/* 157 */     String time = days + "天";
/* 158 */     int inMins = 1440 * days;
/* 159 */     int leftOver = minutes - inMins;
/* 160 */     if (leftOver >= 1) {
/* 161 */       if (leftOver < 60) {
/* 162 */         time = time + " " + leftOver + "分钟";
/*     */       } else {
/* 164 */         int hours = leftOver / 60;
/* 165 */         time = time + " " + hours + "小时";
/* 166 */         int hoursInMins = 60 * hours;
/* 167 */         int minsLeft = leftOver - hoursInMins;
/* 168 */         time = time + " " + minsLeft + "分钟";
/*     */       } 
/*     */     }
/*     */     
/* 172 */     if (secondsLeft > 0) {
/* 173 */       time = time + " " + secondsLeft + "秒";
/*     */     }
/*     */     
/* 176 */     return time;
/*     */   }
/*     */ }