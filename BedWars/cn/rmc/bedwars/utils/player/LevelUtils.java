/*     */ package cn.rmc.bedwars.utils.player;
/*     */ 
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import cn.rmc.bedwars.utils.MathUtils;
/*     */ import org.bukkit.ChatColor;
/*     */ 
/*     */ public class LevelUtils
/*     */ {
/*  11 */   static int EASY_LEVELS = 4;
/*  12 */   static int EASY_LEVELS_XP = 7000;
/*  13 */   static int XP_PER_PRESTIGE = 480000 + EASY_LEVELS_XP;
/*  14 */   static int LEVELS_PER_PRESTIGE = 100;
/*  15 */   static int HIGHEST_PRESTIGE = 10;
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
/*     */   public static int[] get(int exp) {
/*  47 */     int prestiges = exp / XP_PER_PRESTIGE;
/*  48 */     int level = prestiges * LEVELS_PER_PRESTIGE;
/*  49 */     int expWithoutPrestiges = exp - prestiges * XP_PER_PRESTIGE;
/*     */     int i;
/*  51 */     for (i = 1; i <= EASY_LEVELS; i++) {
/*  52 */       int expForEasyLevel = getExpForLevel(i).intValue();
/*  53 */       if (expWithoutPrestiges < expForEasyLevel) {
/*     */         break;
/*     */       }
/*  56 */       level++;
/*  57 */       expWithoutPrestiges -= expForEasyLevel;
/*     */     } 
/*  59 */     i = expWithoutPrestiges / 5000;
/*  60 */     int fl = level + i;
/*  61 */     int expForLevel = getExpForLevel(fl + 1).intValue();
/*  62 */     expWithoutPrestiges -= 5000 * i;
/*     */ 
/*     */     
/*  65 */     return new int[] { fl, expForLevel, expWithoutPrestiges };
/*     */   }
/*     */   public static Integer getLevel(int exp) {
/*  68 */     return Integer.valueOf(get(exp)[0]);
/*     */   }
/*     */   public static String getLevelwithColorByLevel(int level) {
/*  71 */     ChatColor chatColor = null;
/*  72 */     List<ChatColor> color = Arrays.asList(new ChatColor[] { ChatColor.GRAY, ChatColor.WHITE, ChatColor.GOLD, ChatColor.AQUA, ChatColor.DARK_GREEN, ChatColor.DARK_AQUA, ChatColor.DARK_RED, ChatColor.LIGHT_PURPLE, ChatColor.BLUE, ChatColor.DARK_PURPLE });
/*     */     
/*  74 */     int pre = level / 100;
/*  75 */     if (color.size() >= pre) {
/*  76 */       chatColor = color.get((pre == 0) ? 0 : (pre - 1));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  86 */     if (chatColor == null) {
/*  87 */       String lv = String.valueOf(level);
/*  88 */       return "§c[§6" + lv.charAt(0) + "§e" + lv.charAt(1) + "§a" + lv.charAt(2) + "§b" + lv.charAt(3) + "§d✫§5]";
/*     */     } 
/*  90 */     return chatColor + "[" + level + "✫]";
/*     */   }
/*     */   
/*     */   public static String getLevelwithColorwithoutBracketByLevel(int level) {
/*  94 */     ChatColor chatColor = null;
/*  95 */     List<ChatColor> color = Arrays.asList(new ChatColor[] { ChatColor.GRAY, ChatColor.WHITE, ChatColor.GOLD, ChatColor.AQUA, ChatColor.DARK_GREEN, ChatColor.DARK_AQUA, ChatColor.DARK_RED, ChatColor.LIGHT_PURPLE, ChatColor.BLUE, ChatColor.DARK_PURPLE });
/*     */ 
/*     */     
/*  98 */     int pre = level / 100;
/*  99 */     if (color.size() >= pre) {
/* 100 */       chatColor = color.get(pre);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 110 */     if (chatColor == null) {
/* 111 */       String lv = String.valueOf(level);
/* 112 */       return "§6" + lv.charAt(0) + "§e" + lv.charAt(1) + "§a" + lv.charAt(2) + "§b" + lv.charAt(3) + "§d✫";
/*     */     } 
/* 114 */     return chatColor + "" + level + "✫";
/*     */   }
/*     */   
/*     */   public static String[] getLevelProgress(int exp) {
/* 118 */     int[] info = get(exp);
/* 119 */     DecimalFormat df = new DecimalFormat("0.00");
/* 120 */     double i = info[2] / info[1] * 10.0D;
/* 121 */     StringBuilder stb1 = new StringBuilder("§b");
/* 122 */     for (int a = 0; a < (int)i; a++) {
/* 123 */       stb1.append("■");
/*     */     }
/* 125 */     StringBuilder stb2 = new StringBuilder("§7");
/* 126 */     for (int b = 0; b < 10 - (int)i; b++) {
/* 127 */       stb2.append("■");
/*     */     }
/* 129 */     return new String[] { "§a" + ((info[2] >= 1000) ? (df
/* 130 */         .format(info[2] / 1000.0D) + "k") : MathUtils.Format(Integer.valueOf(info[2]))) + "/§b" + ((info[1] >= 1000) ? (df
/* 131 */         .format(info[1] / 1000.0D) + "k") : MathUtils.Format(Integer.valueOf(info[1]))), stb1
/* 132 */         .toString() + stb2.toString() };
/*     */   }
/*     */   
/*     */   public static String[] getLevelProgressEnd(int exp) {
/* 136 */     int[] info = get(exp);
/*     */     
/* 138 */     double i = info[2] / info[1] * 35.0D;
/* 139 */     StringBuilder stb1 = new StringBuilder("§b");
/* 140 */     for (int a = 0; a < (int)i; a++) {
/* 141 */       stb1.append("■");
/*     */     }
/* 143 */     StringBuilder stb2 = new StringBuilder("§7");
/* 144 */     for (int b = 0; b < 35 - (int)i; b++) {
/* 145 */       stb2.append("■");
/*     */     }
/* 147 */     return new String[] { "§a" + 
/* 148 */         formatDouble(info[2]) + " §7/ §b" + formatDouble(info[1]) + " §7(" + getPercent(info[2], info[1]) + "§7)", stb1
/* 149 */         .toString() + stb2.toString() };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String formatDouble(double d) {
/* 158 */     return String.format("%,d", new Object[] { Integer.valueOf((int)d) });
/*     */   }
/*     */   
/*     */   public static String getPercent(double x, double y) {
/* 162 */     DecimalFormat df = new DecimalFormat();
/* 163 */     df.applyPattern("0.0");
/* 164 */     return (y == 0.0D) ? "100%" : (df.format(x / y * 100.0D).replace(".0", "") + "%");
/*     */   }
/*     */ }