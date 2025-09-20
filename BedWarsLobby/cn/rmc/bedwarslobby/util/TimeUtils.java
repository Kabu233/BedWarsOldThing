/*     */ package cn.rmc.bedwarslobby.util;
/*     */ 
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ public final class TimeUtils {
/*  10 */   private static final ThreadLocal<StringBuilder> mmssBuilder = ThreadLocal.withInitial(StringBuilder::new);
/*  11 */   private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String formatIntoHHMMSS(int secs) {
/*  17 */     return formatIntoMMSS(secs);
/*     */   }
/*     */   
/*     */   public static String formatLongIntoHHMMSS(long secs) {
/*  21 */     int unconvertedSeconds = (int)secs;
/*  22 */     return formatIntoMMSS(unconvertedSeconds);
/*     */   }
/*     */   
/*     */   public static String formatIntoMMSS(int secs) {
/*  26 */     int seconds = secs % 60;
/*  27 */     long minutesCount = ((secs - seconds) / 60);
/*  28 */     long minutes = minutesCount % 60L;
/*  29 */     long hours = (minutesCount - minutes) / 60L;
/*  30 */     StringBuilder result = mmssBuilder.get();
/*  31 */     result.setLength(0);
/*  32 */     if (hours > 0L) {
/*  33 */       result.append(hours);
/*  34 */       result.append(":");
/*     */     } 
/*  36 */     result.append(minutes);
/*  37 */     result.append(":");
/*  38 */     if (seconds < 10) {
/*  39 */       result.append("0");
/*     */     }
/*  41 */     result.append(seconds);
/*  42 */     return result.toString();
/*     */   }
/*     */   
/*     */   public static String formatLongIntoMMSS(long secs) {
/*  46 */     int unconvertedSeconds = (int)secs;
/*  47 */     return formatIntoMMSS(unconvertedSeconds);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String formatIntoDetailedString(int secs) {
/*     */     String str, str2, str3, str4;
/*  55 */     if (secs == 0) {
/*  56 */       return "0 seconds";
/*     */     }
/*  58 */     int remainder = secs % 86400;
/*  59 */     int days = secs / 86400;
/*  60 */     int hours = remainder / 3600;
/*  61 */     int minutes = remainder / 60 - hours * 60;
/*  62 */     int seconds = remainder % 3600 - minutes * 60;
/*  63 */     if (days > 0) {
/*  64 */       str = " " + days + " day" + ((days > 1) ? "s" : "");
/*     */     } else {
/*  66 */       str = "";
/*     */     } 
/*  68 */     String fDays = str;
/*  69 */     if (hours > 0) {
/*  70 */       str2 = " " + hours + " hour" + ((hours > 1) ? "s" : "");
/*     */     } else {
/*  72 */       str2 = "";
/*     */     } 
/*  74 */     String fHours = str2;
/*  75 */     if (minutes > 0) {
/*  76 */       str3 = " " + minutes + " minute" + ((minutes > 1) ? "s" : "");
/*     */     } else {
/*  78 */       str3 = "";
/*     */     } 
/*  80 */     String fMinutes = str3;
/*  81 */     if (seconds > 0) {
/*  82 */       str4 = " " + seconds + " second" + ((seconds > 1) ? "s" : "");
/*     */     } else {
/*  84 */       str4 = "";
/*     */     } 
/*  86 */     String fSeconds = str4;
/*  87 */     return (fDays + fHours + fMinutes + fSeconds).trim();
/*     */   }
/*     */   
/*     */   public static String formatLongIntoDetailedString(long secs) {
/*  91 */     int unconvertedSeconds = (int)secs;
/*  92 */     return formatIntoDetailedString(unconvertedSeconds);
/*     */   }
/*     */   
/*     */   public static String formatIntoCalendarString(Date date) {
/*  96 */     return dateFormat.format(date);
/*     */   }
/*     */   
/*     */   public static int parseTime(String time) {
/* 100 */     if (!time.equals("0") && !time.equals("")) {
/* 101 */       String[] lifeMatch = { "w", "d", "h", "m", "s" };
/* 102 */       int[] lifeInterval = { 604800, 86400, 3600, 60, 1 };
/* 103 */       int seconds = -1;
/* 104 */       for (int i = 0; i < lifeMatch.length; i++) {
/* 105 */         Matcher matcher = Pattern.compile("([0-9]+)" + lifeMatch[i]).matcher(time);
/* 106 */         while (matcher.find()) {
/* 107 */           if (seconds == -1) {
/* 108 */             seconds = 0;
/*     */           }
/* 110 */           seconds += Integer.parseInt(matcher.group(1)) * lifeInterval[i];
/*     */         } 
/*     */       } 
/* 113 */       if (seconds == -1) {
/* 114 */         throw new IllegalArgumentException("Invalid time provided.");
/*     */       }
/* 116 */       return seconds;
/*     */     } 
/* 118 */     return 0;
/*     */   }
/*     */   
/*     */   public static int getSecondsBetween(Date a, Date b) {
/* 122 */     return (int)getSecondsBetweenLong(a, b);
/*     */   }
/*     */   
/*     */   public static long getSecondsBetweenLong(Date a, Date b) {
/* 126 */     long diff = a.getTime() - b.getTime();
/* 127 */     long absDiff = Math.abs(diff);
/* 128 */     return absDiff / 1000L;
/*     */   }
/*     */   
/*     */   public static String getTime(int seconds) {
/* 132 */     if (seconds < 60) {
/* 133 */       return seconds + "秒";
/*     */     }
/* 135 */     int minutes = seconds / 60;
/* 136 */     int s = 60 * minutes;
/* 137 */     int secondsLeft = seconds - s;
/* 138 */     if (minutes < 60) {
/* 139 */       return (secondsLeft > 0) ? (minutes + "分钟" + secondsLeft + "秒") : (minutes + "分钟");
/*     */     }
/* 141 */     if (minutes < 1440) {
/* 142 */       int days = minutes / 60;
/* 143 */       String time = days + "h";
/* 144 */       int inMins = 60 * days;
/* 145 */       int leftOver = minutes - inMins;
/* 146 */       if (leftOver >= 1) {
/* 147 */         time = time + " " + leftOver + "分钟";
/*     */       }
/* 149 */       if (secondsLeft > 0) {
/* 150 */         time = time + " " + secondsLeft + "秒";
/*     */       }
/* 152 */       return time;
/*     */     } 
/* 154 */     int days2 = minutes / 1440;
/* 155 */     String time2 = days2 + "天";
/* 156 */     int inMins2 = 1440 * days2;
/* 157 */     int leftOver2 = minutes - inMins2;
/* 158 */     if (leftOver2 >= 1) {
/* 159 */       if (leftOver2 < 60) {
/* 160 */         time2 = time2 + " " + leftOver2 + "分钟";
/*     */       } else {
/* 162 */         int hours = leftOver2 / 60;
/* 163 */         String time3 = time2 + " " + hours + "小时";
/* 164 */         int hoursInMins = 60 * hours;
/* 165 */         int minsLeft = leftOver2 - hoursInMins;
/* 166 */         time2 = time3 + " " + minsLeft + "分钟";
/*     */       } 
/*     */     }
/* 169 */     if (secondsLeft > 0) {
/* 170 */       time2 = time2 + " " + secondsLeft + "秒";
/*     */     }
/* 172 */     return time2;
/*     */   }
/*     */ }


.