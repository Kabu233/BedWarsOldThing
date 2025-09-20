/*    */ package cn.rmc.bedwarslobby.util;
/*    */ 
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.scoreboard.Scoreboard;
/*    */ import org.bukkit.scoreboard.Team;
/*    */ 
/*    */ public class TagUtils
/*    */ {
/*    */   public static void setTag(Player p, Player target, String prefix, String suffix) {
/* 10 */     if (prefix.length() > 16) {
/* 11 */       prefix = prefix.substring(0, 16);
/*    */     }
/* 13 */     if (suffix.length() > 16) {
/* 14 */       suffix = suffix.substring(0, 16);
/*    */     }
/* 16 */     Scoreboard board = target.getScoreboard();
/* 17 */     Team t = board.getTeam(p.getName());
/* 18 */     if (t == null) {
/* 19 */       Team t2 = board.registerNewTeam(p.getName());
/* 20 */       t2.setPrefix(prefix);
/* 21 */       t2.setSuffix(suffix);
/* 22 */       t2.addEntry(p.getName());
/*    */       return;
/*    */     } 
/* 25 */     t.setPrefix(prefix);
/* 26 */     t.setSuffix(suffix);
/* 27 */     if (!t.hasEntry(p.getName())) {
/* 28 */       t.addEntry(p.getName());
/*    */     }
/*    */   }
/*    */   
/*    */   public static void unregisterTag(Player p, Player target) {
/* 33 */     Scoreboard board = p.getScoreboard();
/* 34 */     if (board != null && board.getEntryTeam(p.getName()) != null)
/* 35 */       board.getEntryTeam(p.getName()).unregister(); 
/*    */   }
/*    */ }