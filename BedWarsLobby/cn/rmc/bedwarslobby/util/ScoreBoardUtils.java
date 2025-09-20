/*     */ package cn.rmc.bedwarslobby.util;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.scoreboard.DisplaySlot;
/*     */ 
/*     */ public class ScoreBoardUtils
/*     */ {
/*     */   private String[] Board;
/*     */   
/*     */   private int amount() {
/*  16 */     return this.Board.length;
/*     */   }
/*     */   
/*     */   private String[] cut(String[] content) {
/*  20 */     String[] elements = Arrays.<String>copyOf(content, amount());
/*  21 */     if (elements[0] == null) {
/*  22 */       elements[0] = "Unamed board";
/*     */     }
/*  24 */     if (elements[0].length() > 32) {
/*  25 */       elements[0] = elements[0].substring(0, 32);
/*     */     }
/*  27 */     for (int i = 1; i < elements.length; i++) {
/*  28 */       if (elements[i] != null && elements[i].length() > 40) {
/*  29 */         elements[i] = elements[i].substring(0, 40);
/*     */       }
/*     */     } 
/*  32 */     return elements;
/*     */   }
/*     */   
/*     */   public void SidebarDisplay(Player p, String title, String[] args) {
/*  36 */     ArrayList<String> result = new ArrayList<>();
/*  37 */     result.add(title);
/*  38 */     result.addAll(Arrays.asList(args));
/*  39 */     SidebarDisplay(p, result.<String>toArray(new String[0]));
/*     */   }
/*     */   
/*     */   public void SidebarDisplay(Player p, String[] elements) {
/*  43 */     this.Board = elements;
/*  44 */     String[] elements2 = cut(elements);
/*     */     try {
/*  46 */       if (p.getScoreboard() == null || p.getScoreboard() == Bukkit.getScoreboardManager().getMainScoreboard()) {
/*  47 */         p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
/*     */       }
/*  49 */       if (p.getScoreboard().getObjective(p.getUniqueId().toString().substring(0, 16)) == null) {
/*  50 */         p.getScoreboard().registerNewObjective(p.getUniqueId().toString().substring(0, 16), "dummy");
/*  51 */         p.getScoreboard().getObjective(p.getUniqueId().toString().substring(0, 16)).setDisplaySlot(DisplaySlot.SIDEBAR);
/*     */       } 
/*  53 */       p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(elements2[0]);
/*  54 */       int enter = 1;
/*  55 */       for (int i = 1; i < elements2.length; i++) {
/*  56 */         if (elements2[i] != null) {
/*  57 */           if (elements2[i].equals("")) {
/*  58 */             StringBuilder sb = new StringBuilder();
/*  59 */             for (int a = 1; a <= enter; a++) {
/*  60 */               sb.append("§r");
/*     */             }
/*  62 */             elements2[i] = sb.toString();
/*  63 */             enter++;
/*     */           } 
/*  65 */           if (p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore(elements2[i]).getScore() != amount() - i) {
/*  66 */             p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore(elements2[i]).setScore(amount() - i);
/*  67 */             for (String string : p.getScoreboard().getEntries()) {
/*  68 */               if (p.getScoreboard().getObjective(p.getUniqueId().toString().substring(0, 16)).getScore(string).getScore() == amount() - i && !string.equals(elements2[i])) {
/*  69 */                 p.getScoreboard().resetScores(string);
/*     */               }
/*     */             } 
/*     */           } 
/*  73 */           for (String entry : p.getScoreboard().getEntries()) {
/*  74 */             boolean toErase = true;
/*  75 */             int length = elements2.length;
/*  76 */             int i2 = 0;
/*     */             
/*  78 */             while (i2 < length) {
/*     */ 
/*     */               
/*  81 */               String element = elements2[i2];
/*  82 */               if (element == null || !element.equals(entry) || p.getScoreboard().getObjective(p.getUniqueId().toString().substring(0, 16)).getScore(entry).getScore() != amount() - Arrays.<String>asList(elements2).indexOf(element)) {
/*  83 */                 i2++; continue;
/*     */               } 
/*  85 */               toErase = false;
/*     */               
/*     */               break;
/*     */             } 
/*  89 */             Set<String> name = new HashSet<>();
/*  90 */             for (Player pl : Bukkit.getOnlinePlayers()) {
/*  91 */               name.add(pl.getName());
/*     */             }
/*  93 */             if (name.contains(entry)) {
/*     */               return;
/*     */             }
/*  96 */             if (toErase) {
/*  97 */               p.getScoreboard().resetScores(entry);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/* 102 */     } catch (Exception e) {
/* 103 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }