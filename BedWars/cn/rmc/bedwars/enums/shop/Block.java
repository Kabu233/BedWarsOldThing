/*    */ package cn.rmc.bedwars.enums.shop;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import cn.rmc.bedwars.enums.game.Resource;
/*    */ import cn.rmc.bedwars.game.Game;
/*    */ import cn.rmc.bedwars.game.PlayerData;
/*    */ import cn.rmc.bedwars.game.Team;
/*    */ import cn.rmc.bedwars.game.shop.Price;
/*    */ import cn.rmc.bedwars.utils.item.ItemBuilder;
/*    */ import cn.rmc.bedwars.utils.player.InventoryUtils;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public enum Block implements IShopItem {
/*    */   ItemStack itemStack;
/*    */   HashMap<List<String>, Price> prices;
/*    */   Price price;
/*    */   Price specialPrice;
/*    */   String displayName;
/* 20 */   WOOL("羊毛", (new ItemBuilder(Material.WOOL, 16)).toItemStack(), new Price(Resource.IRON, Integer.valueOf(4)), new String[] { "可用于搭桥穿越岛屿. 搭出的桥的颜色会对应你的队伍颜色." }),
/* 21 */   CLAY("硬化粘土", (new ItemBuilder(Material.STAINED_CLAY, 16)).toItemStack(), new Price(Resource.IRON, Integer.valueOf(12)), new String[] { "用于保卫床的基础方块." }),
/* 22 */   GLASS("防爆玻璃", (new ItemBuilder(Material.STAINED_GLASS, 4)).toItemStack(), new Price(Resource.IRON, Integer.valueOf(12)), new String[] { "免疫爆炸" }),
/* 23 */   ENDSTONE("末影石", (new ItemBuilder(Material.ENDER_STONE, 12)).toItemStack(), new Price(Resource.IRON, Integer.valueOf(24)), new String[] { "用于保护床的坚固方块." }),
/* 24 */   LADDER("梯子", (new ItemBuilder(Material.LADDER, 16)).toItemStack(), new Price(Resource.IRON, Integer.valueOf(4)), new String[] { "可用于救助在树上卡住的猫." }),
/* 25 */   OAKWOODPLANK("木板", (new ItemBuilder(Material.WOOD, 16)).toItemStack(), new Price(Resource.GOLD, Integer.valueOf(4)), new String[] { "用于保卫床的优质方块. 能有效", "抵御稿子的破坏" }),
/* 26 */   OBSIDIAN("黑曜石", (new ItemBuilder(Material.OBSIDIAN, 4)).toItemStack(), new Price(Resource.EMERALD, Integer.valueOf(4)), new String[] { "百分百保护你的床" }); String[] introduce;
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
/*    */ 
/*    */   
/*    */   Block(String displayname, ItemStack is, Price price, String... introduce) {
/* 41 */     this.displayName = displayname;
/* 42 */     this.itemStack = is;
/* 43 */     this.price = price;
/* 44 */     this.introduce = introduce;
/*    */   }
/*    */   
/*    */   Block(String displayname, ItemStack is, Price price, Price specialPrice, String... introduce) {
/* 48 */     this.displayName = displayname;
/* 49 */     this.itemStack = is;
/* 50 */     this.price = price;
/* 51 */     this.introduce = introduce;
/* 52 */     this.specialPrice = specialPrice;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack giveItem(PlayerData pd) {
/* 58 */     Team team = pd.getTeam();
/* 59 */     ItemBuilder itemBuilder = new ItemBuilder(this.itemStack.clone());
/* 60 */     switch (this) {
/*    */       case WOOL:
/*    */       case GLASS:
/*    */       case CLAY:
/* 64 */         itemBuilder.setDyeColor(team.getTeamType().getDyeColor()); break;
/*    */     } 
/* 66 */     return itemBuilder.clone().toItemStack();
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack showItem(PlayerData pd, Game g) {
/* 71 */     Team team = pd.getTeam();
/* 72 */     ItemBuilder itemBuilder = new ItemBuilder(this.itemStack.clone());
/* 73 */     switch (this) {
/*    */       case WOOL:
/*    */       case GLASS:
/*    */       case CLAY:
/* 77 */         itemBuilder.setDyeColor(team.getTeamType().getDyeColor());
/*    */         break;
/*    */     } 
/* 80 */     itemBuilder.addLoreLine(InventoryUtils.getPrice(pd, this).getDisplay());
/* 81 */     if (this.introduce == null) {
/* 82 */       itemBuilder.addLoreLine("");
/*    */     } else {
/* 84 */       itemBuilder.addLoreLine("");
/* 85 */       for (String s : this.introduce) {
/* 86 */         itemBuilder.addLoreLine("§7" + s);
/*    */       }
/* 88 */       itemBuilder.addLoreLine("§r");
/*    */     } 
/*    */     
/* 91 */     return itemBuilder.clone().toItemStack();
/*    */   }
/*    */ }