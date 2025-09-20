/*    */ package cn.rmc.bedwarslobby.util;
/*    */ 
/*    */ import java.math.BigDecimal;
/*    */ import java.text.NumberFormat;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class MathUtils
/*    */ {
/*    */   public static Boolean Chance(int chance) {
/* 11 */     if (Math.random() * 100.0D < chance) {
/* 12 */       return Boolean.valueOf(true);
/*    */     }
/* 14 */     return Boolean.valueOf(false);
/*    */   }
/*    */   
/*    */   public static String Format(Integer i) {
/* 18 */     return String.format("%,d", new Object[] { i });
/*    */   }
/*    */   
/*    */   public static String toRome(int number) {
/* 22 */     StringBuilder rNumber = new StringBuilder();
/* 23 */     int[] aArray = { 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1 };
/* 24 */     String[] rArray = { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };
/* 25 */     if (number < 1 || number > 3999) {
/* 26 */       rNumber = new StringBuilder("-1");
/*    */     } else {
/* 28 */       for (int i = 0; i < aArray.length; i++) {
/* 29 */         while (number >= aArray[i]) {
/* 30 */           rNumber.append(rArray[i]);
/* 31 */           number -= aArray[i];
/*    */         } 
/*    */       } 
/*    */     } 
/* 35 */     return rNumber.toString();
/*    */   }
/*    */   
/*    */   public static String getPercent(Integer num, Integer totalPeople) {
/*    */     Double p3;
/* 40 */     if (totalPeople.intValue() == 0) {
/* 41 */       p3 = Double.valueOf(0.0D);
/*    */     } else {
/* 43 */       p3 = Double.valueOf(num.intValue() * 1.0D / totalPeople.intValue());
/*    */     } 
/* 45 */     NumberFormat nf = NumberFormat.getPercentInstance();
/* 46 */     nf.setMinimumFractionDigits(2);
/* 47 */     String percent = nf.format(p3);
/* 48 */     return percent;
/*    */   }
/*    */   
/*    */   public static String CalculateUtil(BigDecimal a, BigDecimal b) {
/*    */     String str;
/* 53 */     if (b == null) {
/* 54 */       str = "-";
/* 55 */     } else if (b.compareTo(new BigDecimal(0)) == 0) {
/* 56 */       str = "-";
/*    */     } else {
/* 58 */       str = (a == null) ? "0.00%" : (a.multiply(new BigDecimal(100)).divide(b, 2, 4) + "%");
/*    */     } 
/* 60 */     String percent = str;
/* 61 */     return percent;
/*    */   }