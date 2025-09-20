/*     */ package cn.rmc.bedwars.utils;
/*     */ 
/*     */ import java.math.BigDecimal;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NumberUtils
/*     */ {
/*  17 */   private static final String[] CN_NUM = new String[] { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  22 */   private static final String[] CN_UNIT = new String[] { "", "十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String CN_NEGATIVE = "负";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String CN_POINT = "点";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String int2chineseNum(int intNum) {
/*  43 */     StringBuffer sb = new StringBuffer();
/*  44 */     boolean isNegative = false;
/*  45 */     if (intNum < 0) {
/*  46 */       isNegative = true;
/*  47 */       intNum *= -1;
/*     */     } 
/*  49 */     int count = 0;
/*  50 */     while (intNum > 0) {
/*  51 */       sb.insert(0, CN_NUM[intNum % 10] + CN_UNIT[count]);
/*  52 */       intNum /= 10;
/*  53 */       count++;
/*     */     } 
/*     */     
/*  56 */     if (isNegative) {
/*  57 */       sb.insert(0, "负");
/*     */     }
/*     */     
/*  60 */     return sb.toString().replaceAll("零[千百十]", "零").replaceAll("零+万", "万")
/*  61 */       .replaceAll("零+亿", "亿").replaceAll("亿万", "亿零")
/*  62 */       .replaceAll("零+", "零").replaceAll("零$", "");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String bigDecimal2chineseNum(BigDecimal bigDecimalNum) {
/*  73 */     if (bigDecimalNum == null) {
/*  74 */       return CN_NUM[0];
/*     */     }
/*  76 */     StringBuffer sb = new StringBuffer();
/*     */ 
/*     */     
/*  79 */     String numStr = bigDecimalNum.abs().stripTrailingZeros().toPlainString();
/*     */     
/*  81 */     String[] split = numStr.split("\\.");
/*  82 */     String integerStr = int2chineseNum(Integer.parseInt(split[0]));
/*     */     
/*  84 */     sb.append(integerStr);
/*     */ 
/*     */     
/*  87 */     if (split.length == 2) {
/*     */       
/*  89 */       sb.append("点");
/*  90 */       String decimalStr = split[1];
/*  91 */       char[] chars = decimalStr.toCharArray();
/*  92 */       for (int i = 0; i < chars.length; i++) {
/*  93 */         int index = Integer.parseInt(String.valueOf(chars[i]));
/*  94 */         sb.append(CN_NUM[index]);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  99 */     int signum = bigDecimalNum.signum();
/* 100 */     if (signum == -1) {
/* 101 */       sb.insert(0, "负");
/*     */     }
/*     */     
/* 104 */     return sb.toString();
/*     */   }
/*     */ }