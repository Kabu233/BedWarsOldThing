/*    */ package cn.rmc.bedwars.enums.shop;
/*    */ import java.util.Arrays;
/*    */ import java.util.Collections;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import cn.rmc.bedwars.enums.IShopItem;
/*    */ import cn.rmc.bedwars.enums.game.Resource;
/*    */ import cn.rmc.bedwars.game.Game;
/*    */ import cn.rmc.bedwars.game.PlayerData;
/*    */ import cn.rmc.bedwars.game.shop.Price;
/*    */ import cn.rmc.bedwars.utils.item.ItemBuilder;
/*    */ import cn.rmc.bedwars.utils.player.InventoryUtils;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public enum Utility implements IShopItem {
/*    */   ItemStack itemStack;
/*    */   HashMap<List<String>, Price> prices;
/*    */   Price price;
/* 20 */   GoldenApple("金苹果", (new ItemBuilder(Material.GOLDEN_APPLE)).toItemStack(), new Price(Resource.GOLD, Integer.valueOf(3)), new String[] { "全面治愈"
/*    */     }),
/* 22 */   BedBug("床虱", (new ItemBuilder(Material.SNOW_BALL)).setName("§b床虱").toItemStack(), new Price(Resource.IRON, Integer.valueOf(24)), new String[] { "在雪球所落地之处生成蠹虫, 干扰你的对手"
/*    */     }),
/* 24 */   DreamDefender("梦境守护者", (new ItemBuilder(Material.MONSTER_EGG)).setName("§b梦境守护者").toItemStack(), new Price(Resource.IRON, Integer.valueOf(120)), new String[] { "铁傀儡能守护你的基地."
/*    */     }),
/* 26 */   FireBall("火球", (new ItemBuilder(Material.FIREBALL)).toItemStack(), new Price(Resource.IRON, Integer.valueOf(40)), new String[] { "右键发射! 击飞在桥上行走的敌人!"
/*    */     }),
/* 28 */   TNT("TNT", (new ItemBuilder(Material.TNT)).toItemStack(), new Price(Resource.GOLD, Integer.valueOf(8)), new HashMap<List<String>, Price>() {  }, new String[] { "瞬间点燃, 适用于摧毁沿途防御工事!"
/*    */     
/*    */     }),
/* 31 */   EnderPearl("末影珍珠", (new ItemBuilder(Material.ENDER_PEARL)).toItemStack(), new Price(Resource.EMERALD, Integer.valueOf(4)), new HashMap<List<String>, Price>() {  }, new String[] { "入侵敌人基地的最快方法."
/*    */     
/*    */     }),
/* 34 */   WaterBucket("水桶", (new ItemBuilder(Material.WATER_BUCKET)).toItemStack(), new Price(Resource.GOLD, 
/* 35 */       Integer.valueOf(3)), new HashMap<List<String>, Price>() {  }, new String[] { "能很好的降低来犯敌人的速度.", "也可以抵御来自TNT的伤害."
/*    */     
/*    */     }),
/* 38 */   BridgeEgg("搭桥蛋", (new ItemBuilder(Material.EGG)).setName("§b搭桥蛋").toItemStack(), new Price(Resource.EMERALD, Integer.valueOf(1)), new HashMap<List<String>, Price>() {  }, new String[] { "扔出蛋后, 会在其飞行轨迹上生成一座桥."
/*    */     
/*    */     }),
/* 41 */   MagicMilk("魔法牛奶", (new ItemBuilder(Material.MILK_BUCKET)).setName("§f魔法牛奶").toItemStack(), new Price(Resource.GOLD, Integer.valueOf(4)), new String[] { "使用后, 30秒内避免触发陷阱."
/*    */     }),
/* 43 */   Sponge("海绵", (new ItemBuilder(Material.SPONGE, 4)).toItemStack(), new Price(Resource.GOLD, Integer.valueOf(3)), new HashMap<List<String>, Price>() {  }, new String[] { "用于吸收水分"
/*    */     
/*    */     }),
/* 46 */   CompactPopupTower("紧凑型速建防御塔", (new ItemBuilder(Material.CHEST)).setName("§b紧凑型速建防御塔").toItemStack(), new Price(Resource.IRON, Integer.valueOf(24)), new String[] { "建造一个速建防御塔!" });
/*    */   String displayName;
/*    */   String[] introduce;
/*    */   
/*    */   public HashMap<List<String>, Price> getPrices() {
/* 51 */     return this.prices;
/*    */   } public Price getPrice() {
/* 53 */     return this.price;
/*    */   } public String getDisplayName() {
/* 55 */     return this.displayName;
/*    */   }
/*    */ 
/*    */   
/*    */   Utility(String displayname, ItemStack is, Price price, String... introduce) {
/* 60 */     this.displayName = displayname;
/* 61 */     this.itemStack = is;
/* 62 */     this.price = price;
/* 63 */     this.introduce = introduce;
/*    */   }
/*    */   
/*    */   Utility(String displayname, ItemStack is, Price defaultprice, HashMap<List<String>, Price> prices, String... introduce) {
/* 67 */     this.displayName = displayname;
/* 68 */     this.itemStack = is;
/* 69 */     this.price = defaultprice;
/* 70 */     this.introduce = introduce;
/* 71 */     this.prices = prices;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack giveItem(PlayerData pd) {
/* 76 */     ItemBuilder itemBuilder = new ItemBuilder(this.itemStack.clone());
/* 77 */     return itemBuilder.clone().toItemStack();
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack showItem(PlayerData pd, Game g) {
/* 82 */     ItemBuilder itemBuilder = new ItemBuilder(this.itemStack.clone());
/* 83 */     itemBuilder.addLoreLine(InventoryUtils.getPrice(pd, this).getDisplay());
/* 84 */     if (this.introduce == null) {
/* 85 */       itemBuilder.addLoreLine("");
/*    */     } else {
/* 87 */       itemBuilder.addLoreLine("");
/* 88 */       for (String s : this.introduce) {
/* 89 */         itemBuilder.addLoreLine("§7" + s);
/*    */       }
/* 91 */       itemBuilder.addLoreLine("§r");
/*    */     } 
/*    */     
/* 94 */     return itemBuilder.clone().toItemStack();
/*    */   }
/*    */ }