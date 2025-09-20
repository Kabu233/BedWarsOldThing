/*     */ package cn.rmc.bedwarslobby.util;
/*     */ 
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import org.bukkit.ChatColor;
/*     */ 
/*     */ public class LevelUtils
/*     */ {
/*  10 */   static int EASY_LEVELS = 4;
/*  11 */   static int EASY_LEVELS_XP = 7000;
/*  12 */   static int XP_PER_PRESTIGE = 480000 + EASY_LEVELS_XP;
/*  13 */   static int LEVELS_PER_PRESTIGE = 100;
/*  14 */   static int HIGHEST_PRESTIGE = 10;
/*     */ 
/*     */   
/*     */   public static Integer getExpForLevel(int level) {
/*  18 */     if (level == 0) return Integer.valueOf(0);
/*     */     
/*  20 */     int respectedLevel = getLevelRespectingPrestige(level).intValue();
/*  21 */     if (respectedLevel > EASY_LEVELS) {
/*  22 */       return Integer.valueOf(5000);
/*     */     }
/*     */     
/*  25 */     switch (respectedLevel) {
/*     */       case 1:
/*  27 */         return Integer.valueOf(500);
/*     */       case 2:
/*  29 */         return Integer.valueOf(1000);
/*     */       case 3:
/*  31 */         return Integer.valueOf(2000);
/*     */       case 4:
/*  33 */         return Integer.valueOf(3500);
/*     */     } 
/*  35 */     return Integer.valueOf(5000);
/*     */   }
/*     */   
/*     */   public static Integer getLevelRespectingPrestige(int level) {
/*  39 */     if (level > HIGHEST_PRESTIGE * LEVELS_PER_PRESTIGE) {
/*  40 */       return Integer.valueOf(level - HIGHEST_PRESTIGE * LEVELS_PER_PRESTIGE);
/*     */     }
/*  42 */     return Integer.valueOf(level % LEVELS_PER_PRESTIGE);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] get(int exp) {
/*  48 */     int prestiges = exp / XP_PER_PRESTIGE;
/*  49 */     int level = prestiges * LEVELS_PER_PRESTIGE;
/*  50 */     int expWithoutPrestiges = exp - prestiges * XP_PER_PRESTIGE;
/*     */     int i;
/*  52 */     for (i = 1; i <= EASY_LEVELS; i++) {
/*  53 */       int expForEasyLevel = getExpForLevel(i).intValue();
/*  54 */       if (expWithoutPrestiges < expForEasyLevel) {
/*     */         break;
/*     */       }
/*  57 */       level++;
/*  58 */       expWithoutPrestiges -= expForEasyLevel;
/*     */     } 
/*  60 */     i = expWithoutPrestiges / 5000;
/*  61 */     int fl = level + i;
/*  62 */     int expForLevel = getExpForLevel(fl + 1).intValue();
/*  63 */     expWithoutPrestiges -= 5000 * i;
/*     */ 
/*     */     
/*  66 */     return new int[] { fl, expForLevel, expWithoutPrestiges };
/*     */   }
/*     */   public static Integer getLevel(int exp) {
/*  69 */     return Integer.valueOf(get(exp)[0]);
/*     */   }
/*     */   public static String getLevelwithColorByLevel(int level) {
/*  72 */     ChatColor chatColor = null;
/*  73 */     List<ChatColor> color = Arrays.asList(new ChatColor[] { ChatColor.GRAY, ChatColor.WHITE, ChatColor.GOLD, ChatColor.AQUA, ChatColor.DARK_GREEN, ChatColor.DARK_AQUA, ChatColor.DARK_RED, ChatColor.LIGHT_PURPLE, ChatColor.BLUE, ChatColor.DARK_PURPLE });
/*     */     
/*  75 */     int pre = level / 100;
/*  76 */     if (color.size() >= pre) {
/*  77 */       chatColor = color.get((pre == 0) ? 0 : (pre - 1));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  87 */     if (chatColor == null) {
/*  88 */       String lv = String.valueOf(level);
/*  89 */       return "§c[§6" + lv.charAt(0) + "§e" + lv.charAt(1) + "§a" + lv.charAt(2) + "§b" + lv.charAt(3) + "§d✫§5]";
/*     */     } 
/*  91 */     return chatColor + "[" + level + "✫]";
/*     */   }
/*     */   
/*     */   public static String getLevelwithColorwithoutBracketByLevel(int level) {
/*  95 */     ChatColor chatColor = null;
/*  96 */     List<ChatColor> color = Arrays.asList(new ChatColor[] { ChatColor.GRAY, ChatColor.WHITE, ChatColor.GOLD, ChatColor.AQUA, ChatColor.DARK_GREEN, ChatColor.DARK_AQUA, ChatColor.DARK_RED, ChatColor.LIGHT_PURPLE, ChatColor.BLUE, ChatColor.DARK_PURPLE });
/*     */ 
/*     */     
/*  99 */     int pre = level / 100;
/* 100 */     if (color.size() >= pre) {
/* 101 */       chatColor = color.get(pre);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 111 */     if (chatColor == null) {
/* 112 */       String lv = String.valueOf(level);
/* 113 */       return "§6" + lv.charAt(0) + "§e" + lv.charAt(1) + "§a" + lv.charAt(2) + "§b" + lv.charAt(3) + "§d✫";
/*     */     } 
/* 115 */     return chatColor + "" + level + "✫";
/*     */   }
/*     */   
/*     */   public static String[] getLevelProgress(int exp) {
/* 119 */     int[] info = get(exp);
/* 120 */     DecimalFormat df = new DecimalFormat("0.0");
/* 121 */     double i = info[2] / info[1] * 10.0D;
/* 122 */     StringBuilder stb1 = new StringBuilder("§b");
/* 123 */     for (int a = 0; a < (int)i; a++) {
/* 124 */       stb1.append("■");
/*     */     }
/* 126 */     StringBuilder stb2 = new StringBuilder("§7");
/* 127 */     for (int b = 0; b < 10 - (int)i; b++) {
/* 128 */       stb2.append("■");
/*     */     }
/* 130 */     return new String[] { "§b" + ((info[2] >= 1000) ? (df
/* 131 */         .format(info[2] / 1000.0D) + "k") : MathUtils.Format(Integer.valueOf(info[2]))) + "§7/§a" + ((info[1] >= 1000) ? (df
/* 132 */         .format(info[1] / 1000.0D) + "k") : MathUtils.Format(Integer.valueOf(info[1]))), stb1
/* 133 */         .toString() + stb2.toString() };
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\BedWarsLobby-1.0-SNAPSHOT.jar!\me\kabusama\bedwarslobb\\util\LevelUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */