/*    */ package cn.rmc.bedwars.inventory.game.shops;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import cn.rmc.bedwars.enums.ITeamUpgrade;
/*    */ import cn.rmc.bedwars.enums.game.Resource;
/*    */ import cn.rmc.bedwars.enums.shop.TeamUpgrade;
/*    */ import cn.rmc.bedwars.game.PlayerData;
/*    */ import cn.rmc.bedwars.game.shop.Price;
/*    */ import cn.rmc.bedwars.game.shop.ShopItemBasic;
/*    */ import cn.rmc.bedwars.game.shop.items.TeamShopItem;
/*    */ import cn.rmc.bedwars.inventory.MenuBasic;
/*    */ import cn.rmc.bedwars.utils.NumberUtils;
/*    */ import cn.rmc.bedwars.utils.inventory.InventoryUI;
/*    */ import cn.rmc.bedwars.utils.item.ItemBuilder;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class TeamUpgradeShop
/*    */   extends MenuBasic {
/*    */   public TeamUpgradeShop(Player p) {
/* 23 */     super(p, "升级与陷阱", Integer.valueOf(6));
/* 24 */     this.pd.setCurrentShop(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public void Setup() {
/* 29 */     for (int i = 27; i < 36; i++) {
/* 30 */       this.inventoryUI.setItem(i, (InventoryUI.ClickableItem)new InventoryUI.EmptyClickableItem((new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte)7))
/* 31 */             .setName("&8⬆ &7可购买的")
/* 32 */             .setLore(new String[] { "§8⬇ §7陷阱队列" }).toItemStack()));
/*    */     } 
/* 34 */     int amount = 99999;
/* 35 */     switch (this.pd.getTeam().getTraps().size()) {
/*    */       case 0:
/* 37 */         amount = 1;
/*    */         break;
/*    */       case 1:
/* 40 */         amount = 2;
/*    */         break;
/*    */       case 2:
/* 43 */         amount = 4;
/*    */         break;
/*    */     } 
/* 46 */     int a = 1;
/* 47 */     for (int j = 39; j < 42; j++) {
/* 48 */       int slot = j - 38;
/* 49 */       this.inventoryUI.setItem(j, (InventoryUI.ClickableItem)new InventoryUI.EmptyClickableItem((new ItemBuilder(Material.STAINED_GLASS, a, (byte)8))
/* 50 */             .setName("§c陷阱#" + slot + ": 没有陷阱!")
/* 51 */             .addLoreLine("§7第" + NumberUtils.int2chineseNum(slot) + "个敌人进入己方基地后")
/* 52 */             .addLoreLine("§7会触发该陷阱!").addLoreLine("")
/* 53 */             .addLoreLine("§7购买的陷阱会在此排列, 具体")
/* 54 */             .addLoreLine("§7费用取决于已排列的陷阱数量.")
/* 55 */             .addLoreLine("")
/* 56 */             .addLoreLine("§7下一个陷阱: §b" + (new Price(Resource.DIAMOND, Integer.valueOf(amount))).getEzDisplay())
/* 57 */             .toItemStack()));
/* 58 */       a++;
/*    */     } 
/* 60 */     int ii = 39;
/* 61 */     for (Map.Entry<ITeamUpgrade, PlayerData> entry : (Iterable<Map.Entry<ITeamUpgrade, PlayerData>>)this.pd.getTeam().getTraps()) {
/* 62 */       int slot = ii - 38;
/* 63 */       ItemBuilder ib = new ItemBuilder(((ITeamUpgrade)entry.getKey()).getItemStack().clone());
/* 64 */       ib.setName("§a陷阱#" + slot + ": " + ((ITeamUpgrade)entry.getKey()).getDisplayName());
/* 65 */       for (String s : ((ITeamUpgrade)entry.getKey()).getIntroduces()) {
/* 66 */         ib.addLoreLine("§7" + s);
/*    */       }
/* 68 */       ib.addLoreLine("")
/* 69 */         .addLoreLine("§7第" + NumberUtils.int2chineseNum(slot) + "个敌人进入己方基地后")
/* 70 */         .addLoreLine("§7会触发该陷阱!").addLoreLine("")
/* 71 */         .addLoreLine("§7由" + ((PlayerData)entry.getValue()).getPlayer().getName() + "购买");
/* 72 */       this.inventoryUI.setItem(ii, (InventoryUI.ClickableItem)new InventoryUI.EmptyClickableItem(ib.toItemStack()));
/* 73 */       ii++;
/*    */     } 
/*    */     
/* 76 */     int up = 0;
/* 77 */     List<Integer> slotUp = Arrays.asList(new Integer[] { Integer.valueOf(10), Integer.valueOf(11), Integer.valueOf(12), Integer.valueOf(19), Integer.valueOf(20), Integer.valueOf(21) });
/* 78 */     for (ITeamUpgrade upgrade : ITeamUpgrade.upgrades) {
/* 79 */       this.inventoryUI.setItem(((Integer)slotUp.get(up)).intValue(), (InventoryUI.ClickableItem)(new TeamShopItem(this.pd.getTeam(), upgrade)).showItem(this.pd, ShopItemBasic.Where.Normal));
/* 80 */       up++;
/*    */     } 
/* 82 */     int tr = 0;
/* 83 */     List<Integer> slotTr = Arrays.asList(new Integer[] { Integer.valueOf(14), Integer.valueOf(15), Integer.valueOf(16), Integer.valueOf(23) });
/* 84 */     for (ITeamUpgrade upgrade : TeamUpgrade.getValues(ITeamUpgrade.UpgradeType.TRAP)) {
/* 85 */       this.inventoryUI.setItem(((Integer)slotTr.get(tr)).intValue(), (InventoryUI.ClickableItem)(new TeamShopItem(this.pd.getTeam(), upgrade)).showItem(this.pd, ShopItemBasic.Where.Normal));
/* 86 */       tr++;
/*    */     } 
/*    */   }
/*    */ }
