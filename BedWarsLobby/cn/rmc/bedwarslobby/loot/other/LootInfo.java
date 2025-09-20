/*    */ package cn.rmc.bedwarslobby.loot.other;
/*    */ 
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class LootInfo {
/*    */   String name;
/*    */   String type;
/*    */   
/*  9 */   public String getName() { return this.name; } String description; ItemStack itemStack; Rarity rarity; public String getType() {
/* 10 */     return this.type; }
/* 11 */   public String getDescription() { return this.description; }
/* 12 */   public ItemStack getItemStack() { return this.itemStack; } public Rarity getRarity() {
/* 13 */     return this.rarity;
/*    */   }
/*    */   public LootInfo(ItemStack is, String name, String type, Rarity rarity) {
/* 16 */     this.itemStack = is;
/* 17 */     this.name = name;
/* 18 */     this.type = type;
/* 19 */     this.rarity = rarity;
/*    */   }
/*    */   
/*    */   public String getAllName() {
/* 23 */     return this.name + " " + this.type;
/*    */   }
/*    */   
/*    */   public String getChestName() {
/* 27 */     return this.rarity.toColor() + getAllName();
/*    */   }
/*    */ }