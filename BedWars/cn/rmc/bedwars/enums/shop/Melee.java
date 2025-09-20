/*    */ package cn.rmc.bedwars.enums.shop;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import cn.rmc.bedwars.enums.game.Resource;
/*    */ import cn.rmc.bedwars.game.Game;
/*    */ import cn.rmc.bedwars.game.PlayerData;
/*    */ import cn.rmc.bedwars.game.shop.Price;
/*    */ import cn.rmc.bedwars.utils.item.ItemBuilder;
/*    */ import cn.rmc.bedwars.utils.player.InventoryUtils;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.enchantments.Enchantment;
/*    */ import org.bukkit.inventory.ItemFlag;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public enum Melee implements IShopItem {
/*    */   ItemStack itemStack;
/*    */   HashMap<List<String>, Price> prices;
/*    */   Price price;
/*    */   Price specialPrice;
/*    */   String displayName;
/* 21 */   STONE_SWORD("石剑", (new ItemBuilder(Material.STONE_SWORD)).toItemStack(), new Price(Resource.IRON, Integer.valueOf(10)), null),
/* 22 */   IRON_SWORD("铁剑", (new ItemBuilder(Material.IRON_SWORD)).toItemStack(), new Price(Resource.GOLD, Integer.valueOf(7)), null),
/* 23 */   DIAMOND_SWORD("钻石剑", (new ItemBuilder(Material.DIAMOND_SWORD)).toItemStack(), new Price(Resource.EMERALD, Integer.valueOf(3)), null),
/* 24 */   STICK_SWORD("击退棒 (击退 I)", (new ItemBuilder(Material.STICK)).addEnchant(Enchantment.KNOCKBACK, 1).toItemStack(), new Price(Resource.GOLD, Integer.valueOf(5)), null);
/*    */   String[] introduce;
/*    */   
/*    */   public HashMap<List<String>, Price> getPrices() {
/* 28 */     return this.prices;
/*    */   } public Price getPrice() {
/* 30 */     return this.price;
/*    */   } public Price getSpecialPrice() {
/* 32 */     return this.specialPrice;
/*    */   } public String getDisplayName() {
/* 34 */     return this.displayName;
/*    */   }
/*    */ 
/*    */   
/*    */   Melee(String displayname, ItemStack is, Price price, String... introduce) {
/* 39 */     this.displayName = displayname;
/* 40 */     this.itemStack = is;
/* 41 */     this.price = price;
/* 42 */     this.introduce = introduce;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack giveItem(PlayerData pd) {
/* 47 */     ItemBuilder itemBuilder = new ItemBuilder(this.itemStack.clone());
/* 48 */     return itemBuilder.setUnbreakable().toItemStack();
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack showItem(PlayerData pd, Game g) {
/* 53 */     ItemBuilder itemBuilder = new ItemBuilder(this.itemStack.clone());
/* 54 */     itemBuilder.addFlag(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS });
/* 55 */     itemBuilder.addLoreLine(InventoryUtils.getPrice(pd, this).getDisplay());
/* 56 */     if (this.introduce == null) {
/* 57 */       itemBuilder.addLoreLine("");
/*    */     } else {
/* 59 */       itemBuilder.addLoreLine("");
/* 60 */       for (String s : this.introduce) {
/* 61 */         itemBuilder.addLoreLine("§7" + s);
/*    */       }
/* 63 */       itemBuilder.addLoreLine("§r");
/*    */     } 
/*    */     
/* 66 */     return itemBuilder.clone().toItemStack();
/*    */   }
/*    */ }