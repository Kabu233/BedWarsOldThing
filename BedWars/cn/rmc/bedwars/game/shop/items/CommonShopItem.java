/*     */ package cn.rmc.bedwars.game.shop.items;
/*     */ import java.util.Map;
/*     */ import cn.rmc.bedwars.BedWars;
/*     */ import cn.rmc.bedwars.enums.IShopItem;
/*     */ import cn.rmc.bedwars.enums.shop.TeamUpgrade;
/*     */ import cn.rmc.bedwars.game.PlayerData;
/*     */ import cn.rmc.bedwars.game.shop.Price;
/*     */ import cn.rmc.bedwars.game.shop.ShopItemBasic;
/*     */ import cn.rmc.bedwars.inventory.MenuBasic;
/*     */ import cn.rmc.bedwars.inventory.game.QuickShopEditor;
/*     */ import cn.rmc.bedwars.utils.item.ItemBuilder;
/*     */ import cn.rmc.bedwars.utils.player.InventoryUtils;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.event.inventory.ClickType;
/*     */ import org.bukkit.event.inventory.InventoryClickEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ public class CommonShopItem extends ShopItemBasic {
/*     */   IShopItem iShopItem;
/*     */   
/*     */   public CommonShopItem(PlayerData pd, IShopItem item) {
/*  26 */     super(pd, "CommonShopItem-" + item.name(), item, item.getDisplayName(), item.showItem(pd, pd.getGame()), item.giveItem(pd), 
/*  27 */         InventoryUtils.getPrice(pd, item));
/*  28 */     this.iShopItem = item;
/*     */   }
/*     */   public InventoryUI.AbstractClickableItem showItem(final PlayerData pd, final ShopItemBasic.Where where) {
/*  31 */     ItemBuilder ib = new ItemBuilder(this.show.clone());
/*  32 */     if (InventoryUtils.isEnoughItem(pd.getPlayer(), this.price).booleanValue()) {
/*  33 */       ib.setName("§a" + this.displayname);
/*     */     } else {
/*  35 */       ib.setName("§c" + this.displayname);
/*     */     } 
/*  37 */     if (this.iShopItem.name().contains("SWORD") && ((Integer)pd.getTeam().getTeamUpgrade().get(TeamUpgrade.SharpenedSwords)).intValue() >= 1) {
/*  38 */       ib.addLoreLine("§7已升级: §e锋利" + MathUtils.toRome(((Integer)pd.getTeam().getTeamUpgrade().get(TeamUpgrade.SharpenedSwords)).intValue()), 1);
/*     */     }
/*  40 */     switch (where) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case NUMBER_KEY:
/*  49 */         if (!pd.getQuickShopData().getStr().contains(getTotaltype())) {
/*  50 */           ib.addLoreLine("§bShift加左键添加至快速购买!");
/*     */         }
/*     */         break;
/*     */       case LEFT:
/*  54 */         ib.addLoreLine("§bShift加左键从快捷购买移除!");
/*     */         break;
/*     */     } 
/*     */     
/*  58 */     ib.addLoreLine(InventoryUtils.isEnoughItem(pd.getPlayer(), this.price).booleanValue() ? "§e点击购买!" : ("§c你没有足够的" + this.price.getResource().getDisplayName()));
/*  59 */     return new InventoryUI.AbstractClickableItem(ib.toItemStack()) { public void onClick(InventoryClickEvent e) { boolean isinslot; int exit;
/*     */           int slots;
/*     */           int i;
/*  62 */           if (e.getAction() == null)
/*  63 */             return;  if (e.getClick() == null)
/*  64 */             return;  switch (e.getClick()) {
/*     */             case NUMBER_KEY:
/*  66 */               switch (where) {
/*     */                 case NUMBER_KEY:
/*     */                 case LEFT:
/*  69 */                   if (InventoryUtils.isEnoughItem(pd.getPlayer(), CommonShopItem.this.price).booleanValue()) {
/*  70 */                     if (CommonShopItem.this.give.getType().name().contains("SWORD")) {
/*  71 */                       for (ItemStack content : pd.getPlayer().getInventory().getContents()) {
/*  72 */                         if (content != null) {
/*  73 */                           ItemStack origin; int j; switch (content.getType()) {
/*     */                             case NUMBER_KEY:
/*  75 */                               pd.getPlayer().getInventory().remove(Material.WOOD_SWORD);
/*     */                               break;
/*     */                             
/*     */                             case LEFT:
/*     */                             case SHIFT_LEFT:
/*     */                             case null:
/*  81 */                               origin = content.clone();
/*  82 */                               pd.getPlayer().getInventory().remove(origin);
/*  83 */                               for (j = 0; j < pd.getPlayer().getInventory().getSize(); j++) {
/*  84 */                                 if (pd.getPlayer().getInventory().getItem(j) == null) {
/*  85 */                                   pd.getPlayer().getInventory().setItem(j, origin);
/*     */                                   return;
/*     */                                 } 
/*  88 */                                 if (pd.getPlayer().getInventory().getItem(j).getType() == Material.AIR) {
/*  89 */                                   pd.getPlayer().getInventory().setItem(j, origin);
/*     */                                   return;
/*     */                                 } 
/*     */                               } 
/*     */                               break;
/*     */                           } 
/*     */                         } 
/*     */                       } 
/*     */                     }
/*  98 */                     pd.getPlayer().sendMessage("§a你购买了§6" + CommonShopItem.this.displayname);
/*  99 */                     InventoryUtils.deleteitem(pd.getPlayer(), CommonShopItem.this.price);
/* 100 */                     ItemBuilder ib = new ItemBuilder(CommonShopItem.this.give);
/* 101 */                     if (CommonShopItem.this.give.getType().name().contains("SWORD") && ((Integer)pd
/* 102 */                       .getTeam().getTeamUpgrade().get(TeamUpgrade.SharpenedSwords)).intValue() >= 1) {
/* 103 */                       ib.addEnchant(Enchantment.DAMAGE_ALL, ((Integer)pd.getTeam().getTeamUpgrade().get(TeamUpgrade.SharpenedSwords)).intValue());
/* 104 */                       ib.addEnchant(Enchantment.DAMAGE_ALL, 1);
/*     */                     } 
/* 106 */                     int hotbarSlot = e.getHotbarButton();
/*     */ 
/*     */                     
/* 109 */                     ItemStack put = pd.getPlayer().getInventory().getItem(hotbarSlot);
/* 110 */                     if (put != null && 
/* 111 */                       put.getType() != Material.AIR && put.getType() != ib.toItemStack().getType()) {
/* 112 */                       ItemStack origin = put.clone();
/* 113 */                       pd.getPlayer().getInventory().remove(put);
/* 114 */                       Bukkit.getScheduler().runTaskLater((Plugin)BedWars.getInstance(), () -> { for (int i = 0; i < pd.getPlayer().getInventory().getSize(); i++) { if (pd.getPlayer().getInventory().getItem(i) == null) { pd.getPlayer().getInventory().setItem(i, origin); return; }  if (pd.getPlayer().getInventory().getItem(i).getType() == Material.AIR) { pd.getPlayer().getInventory().setItem(i, origin); return; }  }  }2L);
/*     */                     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                     
/* 129 */                     if (put != null && put.getType() == ib.toItemStack().getType()) {
/* 130 */                       pd.getPlayer().getInventory().addItem(new ItemStack[] { ib.toItemStack() });
/*     */                     } else {
/* 132 */                       pd.getPlayer().getInventory().setItem(hotbarSlot, ib.toItemStack());
/* 133 */                     }  pd.getPlayer().playSound(pd.getPlayer().getLocation(), Sound.NOTE_PLING, Float.parseFloat("1.0"), Float.parseFloat("2.0"));
/* 134 */                     CommonShopItem.this.menu().Setup(); break;
/*     */                   } 
/* 136 */                   pd.getPlayer().sendMessage("§c" + CommonShopItem.this.price.getResource().getDisplayName() + "不足! 还需要" + CommonShopItem.this.price.getResource().getDisplayName() + "x" + 
/* 137 */                       InventoryUtils.manyleft(pd.getPlayer(), CommonShopItem.this.price) + "!");
/* 138 */                   pd.getPlayer().playSound(pd.getPlayer().getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 0.5F);
/*     */                   break;
/*     */               } 
/*     */               
/*     */               break;
/*     */             case LEFT:
/* 144 */               switch (where) {
/*     */                 case NUMBER_KEY:
/*     */                 case LEFT:
/* 147 */                   if (InventoryUtils.isEnoughItem(pd.getPlayer(), CommonShopItem.this.price).booleanValue()) {
/* 148 */                     if (CommonShopItem.this.give.getType().name().contains("SWORD")) {
/* 149 */                       for (ItemStack content : pd.getPlayer().getInventory().getContents()) {
/* 150 */                         if (content != null) {
/* 151 */                           ItemStack origin; switch (content.getType()) {
/*     */                             case NUMBER_KEY:
/* 153 */                               pd.getPlayer().getInventory().remove(Material.WOOD_SWORD);
/*     */                               break;
/*     */                             
/*     */                             case LEFT:
/*     */                             case SHIFT_LEFT:
/*     */                             case null:
/* 159 */                               origin = content.clone();
/* 160 */                               pd.getPlayer().getInventory().remove(origin);
/* 161 */                               Bukkit.getScheduler().runTaskLater((Plugin)BedWars.getInstance(), () -> pd.getPlayer().getInventory().addItem(new ItemStack[] { origin }, ), 10L);
/*     */                               break;
/*     */                           } 
/*     */ 
/*     */                         
/*     */                         } 
/*     */                       } 
/*     */                     }
/* 169 */                     pd.getPlayer().sendMessage("§a你购买了§6" + CommonShopItem.this.displayname);
/* 170 */                     InventoryUtils.deleteitem(pd.getPlayer(), CommonShopItem.this.price);
/* 171 */                     ItemBuilder ib = new ItemBuilder(CommonShopItem.this.give);
/* 172 */                     if (CommonShopItem.this.give.getType().name().contains("SWORD") && ((Integer)pd
/* 173 */                       .getTeam().getTeamUpgrade().get(TeamUpgrade.SharpenedSwords)).intValue() >= 1) {
/* 174 */                       ib.addEnchant(Enchantment.DAMAGE_ALL, ((Integer)pd.getTeam().getTeamUpgrade().get(TeamUpgrade.SharpenedSwords)).intValue());
/* 175 */                       ib.addEnchant(Enchantment.DAMAGE_ALL, 1);
/*     */                     } 
/* 177 */                     pd.getPlayer().getInventory().addItem(new ItemStack[] { ib.toItemStack() });
/* 178 */                     pd.getPlayer().playSound(pd.getPlayer().getLocation(), Sound.NOTE_PLING, Float.parseFloat("1.0"), Float.parseFloat("2.0"));
/*     */                     
/* 180 */                     CommonShopItem.this.menu().Setup(); break;
/*     */                   } 
/* 182 */                   pd.getPlayer().sendMessage("§c" + CommonShopItem.this.price.getResource().getDisplayName() + "不足! 还需要" + CommonShopItem.this.price.getResource().getDisplayName() + "x" + 
/* 183 */                       InventoryUtils.manyleft(pd.getPlayer(), CommonShopItem.this.price) + "!");
/* 184 */                   pd.getPlayer().playSound(pd.getPlayer().getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 0.5F);
/*     */                   break;
/*     */                 
/*     */                 case SHIFT_LEFT:
/* 188 */                   if (pd.getSetingItem() == null) {
/* 189 */                     pd.getPlayer().closeInventory();
/*     */                     return;
/*     */                   } 
/* 192 */                   pd.getQuickShopData().getItems().keySet().removeIf(i -> (pd.getQuickShopData().getItems().get(i) != null && ((ShopItemBasic)pd.getQuickShopData().getItems().get(i)).getType().equals(pd.getSetingItem().getType())));
/*     */                   
/* 194 */                   isinslot = false;
/* 195 */                   exit = 0;
/* 196 */                   slots = 0;
/* 197 */                   for (i = 18; i < 44; i++) {
/* 198 */                     if (i % 9 == 0 || i % 9 == 8) {
/* 199 */                       exit++;
/*     */                     
/*     */                     }
/* 202 */                     else if (i == e.getSlot()) {
/* 203 */                       slots = i - 18 - exit;
/* 204 */                       isinslot = true;
/*     */                       break;
/*     */                     } 
/*     */                   } 
/* 208 */                   if (isinslot) {
/* 209 */                     (pd.getQuickShopData()).items.put(Integer.valueOf(slots), pd.getSetingItem());
/* 210 */                     BedWars.getInstance().getQuickBuyManager().save(pd.getUuid(), pd.getQuickShopData());
/* 211 */                     (new QuickShop(pd.getPlayer())).open();
/*     */                   } 
/*     */                   break;
/*     */               } 
/*     */               break;
/*     */             case SHIFT_LEFT:
/* 217 */               switch (where) {
/*     */                 case LEFT:
/* 219 */                   for (Map.Entry<Integer, ShopItemBasic> entry : (Iterable<Map.Entry<Integer, ShopItemBasic>>)pd.getQuickShopData().getItems().entrySet()) {
/* 220 */                     if (entry.getValue() != null && ((ShopItemBasic)entry
/* 221 */                       .getValue()).getType().equals(CommonShopItem.this.getType())) {
/* 222 */                       pd.getQuickShopData().getItems().remove(entry.getKey());
/* 223 */                       BedWars.getInstance().getQuickBuyManager().save(pd.getUuid(), pd.getQuickShopData());
/* 224 */                       CommonShopItem.this.menu().Setup();
/*     */                       break;
/*     */                     } 
/*     */                   } 
/*     */                   break;
/*     */                 case NUMBER_KEY:
/* 230 */                   pd.setSetingItem(new CommonShopItem(pd, CommonShopItem.this.iShopItem));
/* 231 */                   Bukkit.getScheduler().runTaskLater((Plugin)BedWars.getInstance(), () -> (new QuickShopEditor(pd.getPlayer())).open(), 5L);
/*     */                   break;
/*     */               } 
/*     */               break;
/*     */           }  }
/*     */          }
/*     */       ;
/*     */   }
/*     */ }