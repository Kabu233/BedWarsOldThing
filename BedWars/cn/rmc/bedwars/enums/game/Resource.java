/*    */ package cn.rmc.bedwars.enums.game;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.Material;
/*    */ 
/*    */ public enum Resource {
/*    */   private String where;
/*    */   private String displayName;
/*    */   private Material block;
/*    */   private Material item;
/*    */   private ChatColor color;
/* 11 */   IRON("铁锭", Material.IRON_BLOCK, Material.IRON_INGOT, ChatColor.WHITE, null),
/* 12 */   GOLD("金锭", Material.GOLD_BLOCK, Material.GOLD_INGOT, ChatColor.GOLD, null),
/* 13 */   EMERALD("绿宝石", Material.EMERALD_BLOCK, Material.EMERALD, ChatColor.DARK_GREEN, Arrays.asList(new Integer[] { Integer.valueOf(56), Integer.valueOf(40), Integer.valueOf(28) })),
/* 14 */   DIAMOND("钻石", Material.DIAMOND_BLOCK, Material.DIAMOND, ChatColor.AQUA, Arrays.asList(new Integer[] { Integer.valueOf(30), Integer.valueOf(24), Integer.valueOf(12) })); private List<Integer> level; public String getWhere() {
/* 15 */     return this.where;
/*    */   } public String getDisplayName() {
/* 17 */     return this.displayName;
/*    */   } public Material getBlock() {
/* 19 */     return this.block;
/*    */   } public Material getItem() {
/* 21 */     return this.item;
/*    */   } public ChatColor getColor() {
/* 23 */     return this.color;
/*    */   } public List<Integer> getLevel() {
/* 25 */     return this.level;
/*    */   }
/*    */   Resource(String displayname, Material block, Material item, ChatColor color, List<Integer> level) {
/* 28 */     this.displayName = displayname;
/* 29 */     this.where = name().toLowerCase();
/* 30 */     this.block = block;
/* 31 */     this.item = item;
/* 32 */     this.color = color;
/* 33 */     this.level = level;
/*    */   }
/*    */ }