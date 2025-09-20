/*    */ package cn.rmc.bedwarslobby.loot.enums;
/*    */ 
/*    */ import org.bukkit.ChatColor;
/*    */ 
/*    */ public enum Rarity
/*    */ {
/*    */   final String name;
/*  8 */   FREE("§7免费"),
/*  9 */   COMMON("§a普通"),
/* 10 */   RARE("§b稀有"),
/* 11 */   EPIC("§5史诗"),
/* 12 */   LEGENDARY("§6传奇");
/*    */   public String getName() {
/* 14 */     return this.name;
/*    */   }
/*    */   Rarity(String name) {
/* 17 */     this.name = name;
/*    */   }
/*    */   
/*    */   public String toColor() {
/* 21 */     return ChatColor.getLastColors(this.name);
/*    */   }
/*    */ }