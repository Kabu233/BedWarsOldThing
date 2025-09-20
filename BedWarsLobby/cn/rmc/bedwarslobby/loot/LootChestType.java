/*    */ package cn.rmc.bedwarslobby.loot;
/*    */ 
/*    */ import cn.rmc.bedwarslobby.loot.chest.EasterChest;
/*    */ import cn.rmc.bedwarslobby.loot.chest.NormalChest;
/*    */ 
/*    */ public enum LootChestType {
/*    */   String displayName;
/*    */   String dbName;
/*  9 */   NORMAL("奖励箱", "NormalChest", (Class)NormalChest.class),
/* 10 */   EASTER("复活箱", "EasterChest", (Class)EasterChest.class); Class<? extends LootChestBasic> clazz;
/*    */   public String getDisplayName() {
/* 12 */     return this.displayName; }
/* 13 */   public String getDbName() { return this.dbName; } public Class<? extends LootChestBasic> getClazz() {
/* 14 */     return this.clazz;
/*    */   }
/*    */   LootChestType(String displayName, String dbName, Class<? extends LootChestBasic> clazz) {
/* 17 */     this.displayName = displayName;
/* 18 */     this.dbName = dbName;
/* 19 */     this.clazz = clazz;
/*    */   }
/*    */ }