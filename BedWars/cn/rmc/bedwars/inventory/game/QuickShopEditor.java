/*    */ package cn.rmc.bedwars.inventory.game;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import cn.rmc.bedwars.BedWars;
/*    */ import cn.rmc.bedwars.game.PlayerData;
/*    */ import cn.rmc.bedwars.game.shop.ShopItemBasic;
/*    */ import cn.rmc.bedwars.inventory.MenuBasic;
/*    */ import cn.rmc.bedwars.inventory.game.shops.QuickShop;
/*    */ import cn.rmc.bedwars.utils.inventory.InventoryUI;
/*    */ import cn.rmc.bedwars.utils.item.ItemBuilder;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.Sound;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.inventory.InventoryClickEvent;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class QuickShopEditor
/*    */   extends MenuBasic {
/*    */   public QuickShopEditor(Player p) {
/* 22 */     super(p, "正在添加到快捷购买", Integer.valueOf(6), "QSE");
/* 23 */     p.playSound(p.getLocation(), Sound.NOTE_PLING, 1.0F, 1.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public void Setup() {
/* 28 */     ItemStack is = this.pd.getSetingItem().showItem(this.pd, ShopItemBasic.Where.NON).getItemStack();
/* 29 */     List<String> lores = is.getItemMeta().getLore();
/* 30 */     int last = 0;
/* 31 */     for (int i = 1; i + 1 <= lores.size(); i++) {
/* 32 */       if (((String)lores.get(lores.size() - i)).equals("§r")) {
/* 33 */         last = lores.size() + 1 - i;
/*    */       }
/*    */     } 
/* 36 */     for (; last + 1 <= lores.size(); last++) {
/* 37 */       lores.remove(last);
/*    */     }
/* 39 */     lores.add("§e正在添加物品到快捷购买栏!");
/* 40 */     ItemBuilder ib = new ItemBuilder(is);
/* 41 */     ib.setLore(lores);
/* 42 */     this.inventoryUI.setItem(4, (InventoryUI.ClickableItem)new InventoryUI.EmptyClickableItem(ib.toItemStack()));
/* 43 */     ArrayList<InventoryUI.ClickableItem> result = new ArrayList<>();
/* 44 */     HashMap<Integer, ShopItemBasic> map = this.pd.getQuickShopData().getItems(); int j;
/* 45 */     for (j = 0; j <= 20; j++) {
/* 46 */       if (map.containsKey(Integer.valueOf(j))) {
/* 47 */         InventoryUI.AbstractClickableItem clickableItem = ((ShopItemBasic)map.get(Integer.valueOf(j))).showItem(this.pd, ShopItemBasic.Where.QuickEditor);
/* 48 */         clickableItem.setItemStack((new ItemBuilder(clickableItem.getItemStack())).setLore(new String[] { "§e点击以替换" }).toItemStack());
/* 49 */         result.add(clickableItem);
/*    */       } else {
/* 51 */         result.add(new InventoryUI.AbstractClickableItem((new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte)14))
/* 52 */               .setName("§c空闲槽位!").setLore(new String[] { "§e点击以设置" }).toItemStack())
/*    */             {
/*    */               public void onClick(InventoryClickEvent e) {
/* 55 */                 if (QuickShopEditor.this.pd.getSetingItem() == null) {
/* 56 */                   QuickShopEditor.this.p.closeInventory();
/*    */                   return;
/*    */                 } 
/* 59 */                 (QuickShopEditor.this.pd.getQuickShopData()).items.keySet().removeIf(i -> (QuickShopEditor.this.pd.getQuickShopData().getItems().get(i) != null && ((ShopItemBasic)QuickShopEditor.this.pd.getQuickShopData().getItems().get(i)).getType().equals(QuickShopEditor.this.pd.getSetingItem().getType())));
/*    */                 
/* 61 */                 boolean isinslot = false;
/* 62 */                 int exit = 0;
/* 63 */                 int slots = 0;
/* 64 */                 for (int i = 18; i < 44; i++) {
/* 65 */                   if (i % 9 == 0 || i % 9 == 8) {
/* 66 */                     exit++;
/*    */                   
/*    */                   }
/* 69 */                   else if (i == e.getSlot()) {
/* 70 */                     slots = i - 18 - exit;
/* 71 */                     isinslot = true;
/*    */                     break;
/*    */                   } 
/*    */                 } 
/* 75 */                 if (isinslot) {
/* 76 */                   QuickShopEditor.this.pd.getQuickShopData().getItems().put(Integer.valueOf(slots), QuickShopEditor.this.pd.getSetingItem());
/* 77 */                   BedWars.getInstance().getQuickBuyManager().save(QuickShopEditor.this.pd.getUuid(), QuickShopEditor.this.pd.getQuickShopData());
/* 78 */                   QuickShopEditor.this.pd.setSetingItem(null);
/* 79 */                   (new QuickShop(QuickShopEditor.this.p)).open();
/*    */                 } 
/*    */               }
/*    */             });
/*    */       } 
/*    */     } 
/* 85 */     for (j = 18; j < 44; j++) {
/* 86 */       if (j % 9 != 0 && j % 9 != 8) {
/* 87 */         if (result.size() == 0)
/* 88 */           break;  this.inventoryUI.setItem(j, result.get(0));
/* 89 */         result.remove(0);
/*    */       } 
/*    */     } 
/*    */     
/* 93 */     ItemBuilder track = (new ItemBuilder(Material.COMPASS)).setName("§a追踪器商店").addLoreLine("§7为你的指南针购买追踪升级, 升级后之后").addLoreLine("§7你可以追踪特定队伍的某位玩家, 死亡后失效");
/* 94 */     this.inventoryUI.setItem(45, (InventoryUI.ClickableItem)new InventoryUI.AbstractClickableItem(track.toItemStack())
/*    */         {
/*    */           public void onClick(InventoryClickEvent e) {
/* 97 */             (new TrackerShop(QuickShopEditor.this.p, new QuickShopEditor(QuickShopEditor.this.p))).open();
/*    */           }
/*    */         });
/*    */   }
/*    */ }
