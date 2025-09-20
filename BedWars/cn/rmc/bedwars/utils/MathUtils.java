/*    */ package cn.rmc.bedwars.utils;
/*    */ 
/*    */ import java.math.BigDecimal;
/*    */ import java.text.NumberFormat;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Comparator;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import cn.rmc.bedwars.game.PlayerData;
/*    */ 
/*    */ public class MathUtils
/*    */ {
/*    */   public static Boolean Chance(int chance) {
/* 14 */     if (Math.random() * 100.0D < chance) {
/* 15 */       return Boolean.valueOf(true);
/*    */     }
/* 17 */     return Boolean.valueOf(false);
/*    */   }
/*    */   
/*    */   public static String Format(Integer i) {
/* 21 */     return String.format("%,d", new Object[] { i });
/*    */   }
/*    */   public static String toRome(int number) {
/* 24 */     StringBuilder rNumber = new StringBuilder();
/* 25 */     int[] aArray = { 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1 };
/* 26 */     String[] rArray = { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };
/*    */     
/* 28 */     if (number < 1 || number > 3999) {
/* 29 */       rNumber = new StringBuilder("-1");
/*    */     } else {
/* 31 */       for (int i = 0; i < aArray.length; i++) {
/* 32 */         while (number >= aArray[i]) {
/* 33 */           rNumber.append(rArray[i]);
/* 34 */           number -= aArray[i];
/*    */         } 
/*    */       } 
/*    */     } 
/* 38 */     return rNumber.toString();
/*    */   }
/*    */   
/*    */   public static List<Map.Entry<PlayerData, Integer>> gettop(Map<PlayerData, Integer> map) {
/* 42 */     List<Map.Entry<PlayerData, Integer>> list = new ArrayList<>(map.entrySet());
/* 43 */     list.sort((Comparator)Map.Entry.comparingByValue());
/* 44 */     List<Map.Entry<PlayerData, Integer>> l = new ArrayList<>();
/* 45 */     for (int i = list.size() - 1; i >= 0; i--) {
/* 46 */       l.add(list.get(i));
/*    */     }
/* 48 */     return l;
/*    */   }
/*    */   
/*    */   public static String getPercent(Integer num, Integer totalPeople) {
/*    */     Double p3;
/* 53 */     if (totalPeople.intValue() == 0) {
/* 54 */       p3 = Double.valueOf(0.0D);
/*    */     } else {
/* 56 */       p3 = Double.valueOf(num.intValue() * 1.0D / totalPeople.intValue());
/*    */     } 
/* 58 */     NumberFormat nf = NumberFormat.getPercentInstance();
/* 59 */     nf.setMinimumFractionDigits(2);
/* 60 */     String percent = nf.format(p3);
/* 61 */     return percent;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String CalculateUtil(BigDecimal a, BigDecimal b) {
/* 68 */     String percent = (b == null) ? "-" : ((b.compareTo(new BigDecimal(0)) == 0) ? "-" : ((a == null) ? "0.00%" : (a.multiply(new BigDecimal(100)).divide(b, 2, 4) + "%")));
/* 69 */     return percent;
/*    */   }
/*    */   public static String getProgressBar(int xp, int xpToLevelUp, int length, String symbol, String unlock, String lock) {
/* 72 */     StringBuilder sb = new StringBuilder();
/* 73 */     if (xpToLevelUp == 0) {
/* 74 */       xp = 1;
/* 75 */       xpToLevelUp = 1;
/*    */     } 
/* 77 */     int max = xp * length / xpToLevelUp;
/* 78 */     for (int i = 0; i < length; i++) {
/* 79 */       if (i < max) {
/* 80 */         sb.append(unlock).append(symbol);
/*    */       } else {
/* 82 */         sb.append(lock).append(symbol);
/*    */       } 
/*    */     } 
/* 85 */     return sb.toString();
/*    */   }
/*    */ }