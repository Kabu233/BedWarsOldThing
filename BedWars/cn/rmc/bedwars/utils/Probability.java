/*    */ package cn.rmc.bedwars.utils;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ public class Probability {
/*  6 */   private static Random random = new Random();
/*    */   public static boolean probability(int prob) {
/*  8 */     if (random.nextInt(100) <= prob) {
/*  9 */       return true;
/*    */     }
/* 11 */     return false;
/*    */   }
/*    */   public static int probabilityInt(int min, int max) {
/* 14 */     return (int)(Math.random() * (max - min + 1) + min);
/*    */   }
/*    */ }
