/*    */ package cn.rmc.bedwars.enums.shop;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import cn.rmc.bedwars.enums.game.Resource;
/*    */ import cn.rmc.bedwars.game.Game;
/*    */ import cn.rmc.bedwars.game.PlayerData;
/*    */ import cn.rmc.bedwars.game.shop.Price;
/*    */ import cn.rmc.bedwars.utils.item.ItemBuilder;
/*    */ import org.bukkit.inventory.ItemFlag;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.potion.PotionType;
/*    */ 
/*    */ public enum Potion implements IShopItem {
/*    */   ItemStack itemStack;
/*    */   HashMap<List<String>, Price> prices;
/*    */   Price price;
/*    */   Price specialPrice;
/*    */   String displayName;
/* 19 */   Speed("速度II药水 (45秒)", (new ItemBuilder((new org.bukkit.potion.Potion(PotionType.FIRE_RESISTANCE))
/* 20 */       .toItemStack(1))).setName("§f速度II药水 (45秒)").toItemStack(), new Price(Resource.EMERALD, Integer.valueOf(1)), new String[] { "§9速度II (0:45)" }),
/* 21 */   Jump("跳跃提升V药水 (45秒)", (new ItemBuilder((new org.bukkit.potion.Potion(PotionType.JUMP))
/* 22 */       .toItemStack(1))).setName("§f跳跃提升药水 (45秒)").toItemStack(), new Price(Resource.EMERALD, Integer.valueOf(1)), new String[] { "§9跳跃提升V (0:45)" }),
/* 23 */   Invisibility("隐身药水 (30秒)", (new ItemBuilder((new org.bukkit.potion.Potion(PotionType.INVISIBILITY))
/* 24 */       .toItemStack(1))).setName("§f隐身药水 (30秒)").toItemStack(), new Price(Resource.EMERALD, Integer.valueOf(2)), new String[] { "§9完全隐身" }); String[] introduce;
/*    */   
/*    */   public HashMap<List<String>, Price> getPrices() {
/* 27 */     return this.prices;
/*    */   } public Price getPrice() {
/* 29 */     return this.price;
/*    */   } public Price getSpecialPrice() {
/* 31 */     return this.specialPrice;
/*    */   } public String getDisplayName() {
/* 33 */     return this.displayName;
/*    */   }
/*    */ 
/*    */   
/*    */   Potion(String displayname, ItemStack is, Price price, String... introduce) {
/* 38 */     this.displayName = displayname;
/* 39 */     this.itemStack = is;
/* 40 */     this.price = price;
/* 41 */     this.introduce = introduce;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack giveItem(PlayerData pd) {
/* 46 */     ItemBuilder itemBuilder = new ItemBuilder(this.itemStack.clone());
/* 47 */     itemBuilder.addFlag(new ItemFlag[] { ItemFlag.HIDE_POTION_EFFECTS });
/* 48 */     return itemBuilder.toItemStack();
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack showItem(PlayerData pd, Game g) {
/* 53 */     ItemBuilder itemBuilder = new ItemBuilder(this.itemStack.clone());
/* 54 */     itemBuilder.addFlag(new ItemFlag[] { ItemFlag.HIDE_POTION_EFFECTS });
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