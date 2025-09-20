/*    */ package cn.rmc.bedwars.enums.shop;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import cn.rmc.bedwars.enums.game.Resource;
/*    */ import cn.rmc.bedwars.game.Game;
/*    */ import cn.rmc.bedwars.game.PlayerData;
/*    */ import cn.rmc.bedwars.game.shop.Price;
/*    */ import cn.rmc.bedwars.utils.item.ItemBuilder;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.enchantments.Enchantment;
/*    */ import org.bukkit.inventory.ItemFlag;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public enum Ranged implements IShopItem {
/*    */   ItemStack itemStack;
/*    */   HashMap<List<String>, Price> prices;
/*    */   Price price;
/*    */   Price specialPrice;
/*    */   String displayName;
/* 20 */   ARROW("箭", (new ItemBuilder(Material.ARROW, 8)).toItemStack(), new Price(Resource.GOLD, Integer.valueOf(2)), null),
/* 21 */   BOW("弓", (new ItemBuilder(Material.BOW)).toItemStack(), new Price(Resource.GOLD, Integer.valueOf(12)), null),
/* 22 */   BOW_P("弓 (力量 I)", (new ItemBuilder(Material.BOW))
/* 23 */     .addEnchant(Enchantment.ARROW_DAMAGE, 1).toItemStack(), new Price(Resource.GOLD, Integer.valueOf(24)), null),
/* 24 */   BOW_PP("弓 (力量 I, 冲击 I)", (new ItemBuilder(Material.BOW))
/* 25 */     .addEnchant(Enchantment.ARROW_DAMAGE, 1)
/* 26 */     .addEnchant(Enchantment.ARROW_KNOCKBACK, 1).toItemStack(), new Price(Resource.EMERALD, Integer.valueOf(6)), null); String[] introduce;
/*    */   
/*    */   public HashMap<List<String>, Price> getPrices() {
/* 29 */     return this.prices;
/*    */   } public Price getPrice() {
/* 31 */     return this.price;
/*    */   } public Price getSpecialPrice() {
/* 33 */     return this.specialPrice;
/*    */   } public String getDisplayName() {
/* 35 */     return this.displayName;
/*    */   }
/*    */ 
/*    */   
/*    */   Ranged(String displayname, ItemStack is, Price price, String... introduce) {
/* 40 */     this.displayName = displayname;
/* 41 */     this.itemStack = is;
/* 42 */     this.price = price;
/* 43 */     this.introduce = introduce;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack giveItem(PlayerData pd) {
/* 48 */     ItemBuilder itemBuilder = new ItemBuilder(this.itemStack.clone());
/* 49 */     return itemBuilder.clone().toItemStack();
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack showItem(PlayerData pd, Game g) {
/* 54 */     ItemBuilder itemBuilder = new ItemBuilder(this.itemStack.clone());
/* 55 */     itemBuilder.addLoreLine(InventoryUtils.getPrice(pd, this).getDisplay());
/* 56 */     itemBuilder.addFlag(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
/* 57 */     if (this.introduce == null) {
/* 58 */       itemBuilder.addLoreLine("");
/*    */     } else {
/* 60 */       itemBuilder.addLoreLine("");
/* 61 */       for (String s : this.introduce) {
/* 62 */         itemBuilder.addLoreLine("§7" + s);
/*    */       }
/* 64 */       itemBuilder.addLoreLine("§r");
/*    */     } 
/*    */     
/* 67 */     return itemBuilder.clone().toItemStack();
/*    */   }
/*    */ }