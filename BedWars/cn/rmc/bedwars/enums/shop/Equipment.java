/*    */ package cn.rmc.bedwars.enums.shop;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import cn.rmc.bedwars.enums.game.Resource;
/*    */ import cn.rmc.bedwars.game.shop.Price;
/*    */ import cn.rmc.bedwars.utils.item.ItemBuilder;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.enchantments.Enchantment;
/*    */ import org.bukkit.inventory.ItemFlag;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ 
/*    */ public enum Equipment
/*    */ {
/* 15 */   WOODEN_PICKAXE("木镐 (效率 I)", (new ItemBuilder(Material.WOOD_PICKAXE)).addEnchant(Enchantment.DIG_SPEED, 1).toItemStack(), new Price(Resource.IRON, Integer.valueOf(10)), Sort.Pickaxe),
/* 16 */   IRON_PICKAXE("铁镐 (效率 II)", (new ItemBuilder(Material.IRON_PICKAXE)).addEnchant(Enchantment.DIG_SPEED, 2).toItemStack(), new Price(Resource.IRON, Integer.valueOf(10)), Sort.Pickaxe),
/* 17 */   GOLDEN_PICKAXE("金镐 (效率 III)", (new ItemBuilder(Material.GOLD_PICKAXE)).addEnchant(Enchantment.DIG_SPEED, 3).addEnchant(Enchantment.DAMAGE_ALL, 2).toItemStack(), new Price(Resource.GOLD, Integer.valueOf(3)), Sort.Pickaxe),
/* 18 */   DIAMOND_PICKAXE("钻石镐 (效率 III)", (new ItemBuilder(Material.DIAMOND_PICKAXE)).addEnchant(Enchantment.DIG_SPEED, 3).toItemStack(), new Price(Resource.GOLD, Integer.valueOf(6)), Sort.Pickaxe),
/*    */   
/* 20 */   WOODEN_AXE("木斧 (效率 I)", (new ItemBuilder(Material.WOOD_AXE)).addEnchant(Enchantment.DIG_SPEED, 1).toItemStack(), new Price(Resource.IRON, Integer.valueOf(10)), Sort.Axe),
/* 21 */   STONE_AXE("石斧 (效率 I)", (new ItemBuilder(Material.STONE_AXE)).addEnchant(Enchantment.DIG_SPEED, 1).toItemStack(), new Price(Resource.IRON, Integer.valueOf(10)), Sort.Axe),
/* 22 */   IRON_AXE("铁斧 (效率 II)", (new ItemBuilder(Material.IRON_AXE)).addEnchant(Enchantment.DIG_SPEED, 2).toItemStack(), new Price(Resource.GOLD, Integer.valueOf(3)), Sort.Axe),
/* 23 */   DIAMOND_AXE("钻石斧 (效率 III)", (new ItemBuilder(Material.DIAMOND_AXE)).addEnchant(Enchantment.DIG_SPEED, 3).toItemStack(), new Price(Resource.GOLD, Integer.valueOf(6)), Sort.Axe),
/*    */   
/* 25 */   Shears("永久的剪刀", (new ItemBuilder(Material.SHEARS)).toItemStack(), new Price(Resource.IRON, Integer.valueOf(20)), Sort.Shears),
/*    */   
/* 27 */   LEATHER_Armor(null, (new ItemBuilder(Material.LEATHER_BOOTS)).toItemStack(), null, Sort.Armor),
/* 28 */   CHAIN_Armor("永久的铁链护甲", (new ItemBuilder(Material.CHAINMAIL_BOOTS)).toItemStack(), new Price(Resource.IRON, Integer.valueOf(24)), Sort.Armor),
/* 29 */   IRON_Armor("永久的铁护甲", (new ItemBuilder(Material.IRON_BOOTS)).toItemStack(), new Price(Resource.GOLD, Integer.valueOf(12)), Sort.Armor),
/* 30 */   DIAMOND_Armor("永久的钻石护甲", (new ItemBuilder(Material.DIAMOND_BOOTS)).toItemStack(), new Price(Resource.EMERALD, Integer.valueOf(6)), Sort.Armor); Sort sort; ItemStack item;
/*    */   public Sort getSort() {
/* 32 */     return this.sort;
/*    */   }
/*    */   Price prices; Price price; String displayName;
/*    */   public Price getPrices() {
/* 36 */     return this.prices;
/*    */   } public Price getPrice() {
/* 38 */     return this.price;
/*    */   } public String getDisplayName() {
/* 40 */     return this.displayName;
/*    */   }
/*    */   
/*    */   Equipment(String displayname, ItemStack is, Price price, Sort sort) {
/* 44 */     this.sort = sort;
/* 45 */     this.item = is;
/* 46 */     this.price = price;
/* 47 */     this.displayName = displayname;
/*    */   }
/*    */   
/*    */   public ItemStack getItem() {
/* 51 */     return (new ItemBuilder(this.item.clone())).addLoreLine(this.price.getDisplay()).addFlag(new ItemFlag[] { ItemFlag.HIDE_UNBREAKABLE }).toItemStack();
/*    */   }
/*    */   public ItemStack getgiveItem() {
/* 54 */     return (new ItemBuilder(this.item.clone())).setUnbreakable().toItemStack();
/*    */   }
/*    */   public ItemStack getLegs() {
/* 57 */     if (this == LEATHER_Armor) {
/* 58 */       return (new ItemBuilder(Material.LEATHER_LEGGINGS)).setUnbreakable().toItemStack();
/*    */     }
/* 60 */     return (new ItemBuilder(Material.valueOf(this.item.getType().name().split("_")[0] + "_LEGGINGS"))).toItemStack();
/*    */   }
/*    */   public static ArrayList<Equipment> getValues(Sort sort) {
/* 63 */     ArrayList<Equipment> result = new ArrayList<>();
/* 64 */     for (Equipment value : values()) {
/* 65 */       if (value.sort == sort) {
/* 66 */         result.add(value);
/*    */       }
/*    */     } 
/* 69 */     return result;
/*    */   }
/*    */   
/* 72 */   public enum Sort { Pickaxe,
/* 73 */     Axe,
/* 74 */     Shears,
/* 75 */     Armor; }
/*    */ 
/*    */ }