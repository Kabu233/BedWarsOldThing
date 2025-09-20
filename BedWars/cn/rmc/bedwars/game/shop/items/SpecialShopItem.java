/*     */ package cn.rmc.bedwars.game.shop.items;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Map;
/*     */ import cn.rmc.bedwars.BedWars;
/*     */ import cn.rmc.bedwars.enums.shop.Equipment;
/*     */ import cn.rmc.bedwars.enums.shop.TeamUpgrade;
/*     */ import cn.rmc.bedwars.game.PlayerData;
/*     */ import cn.rmc.bedwars.game.shop.Price;
/*     */ import cn.rmc.bedwars.game.shop.ShopItemBasic;
/*     */ import cn.rmc.bedwars.inventory.MenuBasic;
/*     */ import cn.rmc.bedwars.inventory.game.QuickShopEditor;
/*     */ import cn.rmc.bedwars.inventory.game.shops.QuickShop;
/*     */ import cn.rmc.bedwars.utils.MathUtils;
/*     */ import cn.rmc.bedwars.utils.inventory.InventoryUI;
/*     */ import cn.rmc.bedwars.utils.item.ItemBuilder;
/*     */ import cn.rmc.bedwars.utils.player.InventoryUtils;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.event.inventory.ClickType;
/*     */ import org.bukkit.event.inventory.InventoryClickEvent;
/*     */ import org.bukkit.inventory.ItemFlag;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ public class SpecialShopItem extends ShopItemBasic {
/*     */   Sort sort;
/*     */   Equipment.Sort eqsort;
/*     */   Equipment equipment;
/*     */   
/*     */   public SpecialShopItem(PlayerData pd, Equipment equipment) {
/*  32 */     super(pd, "SpecialShopItemArmor-" + equipment.name(), equipment, equipment.getDisplayName(), equipment.getItem(), equipment.getPrice());
/*  33 */     this.equipment = equipment;
/*  34 */     this.eqsort = equipment.getSort();
/*     */   }
/*     */   public SpecialShopItem(PlayerData pd, Equipment.Sort sort) {
/*  37 */     super(pd, "SpecialShopItemTool-" + sort.name(), sort);
/*  38 */     this.eqsort = sort;
/*     */   }
/*     */   public InventoryUI.AbstractClickableItem showItem(final PlayerData pd, final ShopItemBasic.Where where) {
/*     */     boolean isinq;
/*  42 */     final ArrayList<Equipment> equipments = Equipment.getValues(this.eqsort);
/*  43 */     int level = 0;
/*  44 */     switch (this.eqsort) {
/*     */       case NUMBER_KEY:
/*  46 */         if (pd.getAxe() == null) {
/*  47 */           this.equipment = equipments.get(0);
/*  48 */           this.show = this.equipment.getItem();
/*  49 */           level = 1;
/*  50 */           this.displayname = this.equipment.getDisplayName();
/*  51 */           this.price = this.equipment.getPrice(); break;
/*  52 */         }  if (pd.getAxe() == equipments.get(equipments.size() - 1)) {
/*  53 */           this.equipment = equipments.get(equipments.size() - 1);
/*  54 */           this.show = this.equipment.getItem();
/*  55 */           level = 4;
/*  56 */           this.displayname = this.equipment.getDisplayName();
/*  57 */           this.price = this.equipment.getPrice(); break;
/*     */         } 
/*  59 */         this.equipment = equipments.get(equipments.indexOf(pd.getAxe()) + 1);
/*  60 */         this.show = this.equipment.getItem();
/*  61 */         level = equipments.indexOf(pd.getAxe()) + 2;
/*  62 */         this.displayname = this.equipment.getDisplayName();
/*  63 */         this.price = this.equipment.getPrice();
/*     */         break;
/*     */       
/*     */       case LEFT:
/*  67 */         if (pd.getPickaxe() == null) {
/*  68 */           this.equipment = equipments.get(0);
/*  69 */           this.show = this.equipment.getItem();
/*  70 */           level = 1;
/*  71 */           this.displayname = this.equipment.getDisplayName();
/*  72 */           this.price = this.equipment.getPrice(); break;
/*     */         } 
/*  74 */         if (pd.getPickaxe() == equipments.get(equipments.size() - 1)) {
/*  75 */           this.equipment = equipments.get(equipments.size() - 1);
/*  76 */           this.show = this.equipment.getItem();
/*  77 */           level = 4;
/*  78 */           this.displayname = this.equipment.getDisplayName();
/*  79 */           this.price = this.equipment.getPrice(); break;
/*     */         } 
/*  81 */         this.equipment = equipments.get(equipments.indexOf(pd.getPickaxe()) + 1);
/*  82 */         this.show = this.equipment.getItem();
/*  83 */         level = equipments.indexOf(pd.getPickaxe()) + 2;
/*  84 */         this.displayname = this.equipment.getDisplayName();
/*  85 */         this.price = this.equipment.getPrice();
/*     */         break;
/*     */       
/*     */       case SHIFT_LEFT:
/*  89 */         if (pd.getArmors().contains(this.equipment)) {
/*  90 */           this.sort = Sort.BOUGHT;
/*     */ 
/*     */ 
/*     */           
/*     */           break;
/*     */         } 
/*     */ 
/*     */         
/*  98 */         if (equipments.indexOf(this.equipment) > equipments.indexOf(pd.getArmor())) {
/*  99 */           this.sort = Sort.NON;
/*     */           break;
/*     */         } 
/* 102 */         this.sort = Sort.COVERED;
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case null:
/* 108 */         if (pd.getCut().booleanValue()) {
/* 109 */           this.sort = Sort.BOUGHT; break;
/*     */         } 
/* 111 */         this.sort = Sort.NON;
/*     */         break;
/*     */     } 
/*     */     
/* 115 */     final ItemBuilder ib = new ItemBuilder(this.show.clone());
/* 116 */     ib.addFlag(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 123 */     switch (this.equipment.getSort()) {
/*     */       case NUMBER_KEY:
/* 125 */         if (((Integer)pd.getTeam().getTeamUpgrade().get(TeamUpgrade.SharpenedSwords)).intValue() >= 1) {
/* 126 */           ib.addEnchant(Enchantment.DAMAGE_ALL, ((Integer)pd.getTeam().getTeamUpgrade().get(TeamUpgrade.SharpenedSwords)).intValue());
/* 127 */           ib.addLoreLine("§7已升级: §e锋利" + MathUtils.toRome(((Integer)pd.getTeam().getTeamUpgrade().get(TeamUpgrade.SharpenedSwords)).intValue()), 1);
/*     */         } 
/*     */       case LEFT:
/* 130 */         ib.addLoreLine("§7等级: §e" + MathUtils.toRome(level));
/* 131 */         ib.addLoreLine("");
/* 132 */         ib.addLoreLine("§7该道具可升级.");
/* 133 */         ib.addLoreLine("§7死亡将会导致损失一级!");
/* 134 */         ib.addLoreLine("");
/* 135 */         ib.addLoreLine("§7每次重生时, 至少为最低等级.");
/*     */         break;
/*     */       case null:
/* 138 */         ib.addLoreLine("");
/* 139 */         ib.addLoreLine("§7用于破坏羊毛, 每次重生时会获得剪刀.");
/*     */         break;
/*     */       case SHIFT_LEFT:
/* 142 */         if (((Integer)pd.getTeam().getTeamUpgrade().get(TeamUpgrade.ReinforcedArmor)).intValue() >= 1) {
/* 143 */           ib.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, ((Integer)pd.getTeam().getTeamUpgrade().get(TeamUpgrade.ReinforcedArmor)).intValue());
/* 144 */           ib.addLoreLine("§7已升级: §e保护" + MathUtils.toRome(((Integer)pd.getTeam().getTeamUpgrade().get(TeamUpgrade.ReinforcedArmor)).intValue()), 1);
/*     */         } 
/*     */         break;
/*     */     } 
/* 148 */     ib.addLoreLine("");
/* 149 */     switch (where) {
/*     */       case NUMBER_KEY:
/* 151 */         isinq = false;
/* 152 */         for (ShopItemBasic itemBasic : pd.getQuickShopData().getItems().values()) {
/* 153 */           if (itemBasic.getType().equals(getType())) {
/* 154 */             isinq = true;
/*     */             break;
/*     */           } 
/*     */         } 
/* 158 */         if (!isinq) {
/* 159 */           ib.addLoreLine("§bShift加左键添加至快速购买!");
/*     */         }
/*     */         break;
/*     */       case LEFT:
/* 163 */         ib.addLoreLine("§bShift加左键从快捷购买移除!");
/*     */         break;
/*     */     } 
/* 166 */     switch (this.eqsort) {
/*     */       case NUMBER_KEY:
/* 168 */         if (pd.getAxe() == equipments.get(equipments.size() - 1)) {
/* 169 */           ib.setName("§a" + this.displayname);
/* 170 */           ib.addLoreLine("§a已解锁"); break;
/*     */         } 
/* 172 */         if (InventoryUtils.isEnoughItem(pd.getPlayer(), this.price).booleanValue()) {
/* 173 */           ib.setName("§a" + this.displayname);
/* 174 */           ib.addLoreLine("§e点击升级!"); break;
/*     */         } 
/* 176 */         ib.setName("§c" + this.displayname);
/* 177 */         ib.addLoreLine("§c你没有足够的" + this.price.getResource().getDisplayName());
/*     */         break;
/*     */ 
/*     */       
/*     */       case LEFT:
/* 182 */         if (pd.getPickaxe() == equipments.get(equipments.size() - 1)) {
/* 183 */           ib.setName("§a" + this.displayname);
/* 184 */           ib.addLoreLine("§a已解锁"); break;
/*     */         } 
/* 186 */         if (InventoryUtils.isEnoughItem(pd.getPlayer(), this.price).booleanValue()) {
/* 187 */           ib.setName("§a" + this.displayname);
/* 188 */           ib.addLoreLine("§e点击升级!"); break;
/*     */         } 
/* 190 */         ib.setName("§c" + this.displayname);
/* 191 */         ib.addLoreLine("§c你没有足够的" + this.price.getResource().getDisplayName());
/*     */         break;
/*     */ 
/*     */       
/*     */       case SHIFT_LEFT:
/*     */       case null:
/* 197 */         switch (this.sort) {
/*     */           case NUMBER_KEY:
/*     */           case LEFT:
/* 200 */             if (InventoryUtils.isEnoughItem(pd.getPlayer(), this.price).booleanValue()) {
/* 201 */               ib.addLoreLine("§e点击购买!");
/*     */               break;
/*     */             } 
/* 204 */             ib.setName("§c" + this.displayname);
/* 205 */             ib.addLoreLine("§c你没有足够的" + this.price.getResource().getDisplayName());
/*     */             break;
/*     */           
/*     */           case SHIFT_LEFT:
/* 209 */             ib.addLoreLine("§a已解锁");
/*     */             break;
/*     */         } 
/*     */         break;
/*     */     } 
/* 214 */     return new InventoryUI.AbstractClickableItem(ib.toItemStack()) { public void onClick(InventoryClickEvent e) { boolean isinslot; int exit;
/*     */           int slots;
/*     */           int i;
/* 217 */           if (e.getAction() == null)
/* 218 */             return;  if (e.getClick() == null)
/* 219 */             return;  switch (e.getClick()) {
/*     */             case NUMBER_KEY:
/*     */             case LEFT:
/* 222 */               switch (where) {
/*     */                 case NUMBER_KEY:
/*     */                 case LEFT:
/* 225 */                   switch (SpecialShopItem.this.eqsort) {
/*     */                     case NUMBER_KEY:
/* 227 */                       if (pd.getAxe() == equipments.get(equipments.size() - 1)) {
/*     */                         return;
/*     */                       }
/* 230 */                       if (InventoryUtils.isEnoughItem(pd.getPlayer(), SpecialShopItem.this.price).booleanValue()) {
/* 231 */                         pd.getPlayer().sendMessage("§a你购买了§6" + SpecialShopItem.this.displayname);
/* 232 */                         pd.setAxe(SpecialShopItem.this.equipment);
/* 233 */                         pd.getPlayer().playSound(pd.getPlayer().getLocation(), Sound.NOTE_PLING, 1.0F, 2.0F);
/* 234 */                         InventoryUtils.deleteitem(pd.getPlayer(), SpecialShopItem.this.price);
/* 235 */                         for (ItemStack content : pd.getPlayer().getInventory().getContents()) {
/* 236 */                           if (content != null && 
/* 237 */                             content.getType().name().contains("_AXE")) {
/* 238 */                             pd.getPlayer().getInventory().remove(content);
/*     */                           }
/*     */                         } 
/* 241 */                         pd.getPlayer().getInventory().addItem(new ItemStack[] { this.val$pd.getAxe().getgiveItem() });
/* 242 */                         SpecialShopItem.this.menu().Setup(); break;
/*     */                       } 
/* 244 */                       pd.getPlayer().sendMessage("§c" + SpecialShopItem.this.price.getResource().getDisplayName() + "不足! 还需要" + SpecialShopItem.this.price.getResource().getDisplayName() + "x" + 
/* 245 */                           InventoryUtils.manyleft(pd.getPlayer(), SpecialShopItem.this.price) + "!");
/* 246 */                       pd.getPlayer().playSound(pd.getPlayer().getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 0.5F);
/*     */                       break;
/*     */ 
/*     */                     
/*     */                     case LEFT:
/* 251 */                       if (pd.getPickaxe() == equipments.get(equipments.size() - 1)) {
/*     */                         return;
/*     */                       }
/* 254 */                       if (InventoryUtils.isEnoughItem(pd.getPlayer(), SpecialShopItem.this.price).booleanValue()) {
/* 255 */                         pd.getPlayer().sendMessage("§a你购买了§6" + SpecialShopItem.this.displayname);
/* 256 */                         pd.setPickaxe(SpecialShopItem.this.equipment);
/* 257 */                         pd.getPlayer().playSound(pd.getPlayer().getLocation(), Sound.NOTE_PLING, 1.0F, 2.0F);
/* 258 */                         InventoryUtils.deleteitem(pd.getPlayer(), SpecialShopItem.this.price);
/* 259 */                         for (ItemStack content : pd.getPlayer().getInventory().getContents()) {
/* 260 */                           if (content != null && 
/* 261 */                             content.getType().name().contains("_PICKAXE")) {
/* 262 */                             pd.getPlayer().getInventory().remove(content);
/*     */                           }
/*     */                         } 
/* 265 */                         pd.getPlayer().getInventory().addItem(new ItemStack[] { this.val$pd.getPickaxe().getgiveItem() });
/* 266 */                         SpecialShopItem.this.menu().Setup(); break;
/*     */                       } 
/* 268 */                       pd.getPlayer().sendMessage("§c" + SpecialShopItem.this.price.getResource().getDisplayName() + "不足! 还需要" + SpecialShopItem.this.price.getResource().getDisplayName() + "x" + 
/* 269 */                           InventoryUtils.manyleft(pd.getPlayer(), SpecialShopItem.this.price) + "!");
/* 270 */                       pd.getPlayer().playSound(pd.getPlayer().getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 0.5F);
/*     */                       break;
/*     */ 
/*     */                     
/*     */                     case SHIFT_LEFT:
/* 275 */                       switch (SpecialShopItem.this.sort) {
/*     */                         case NUMBER_KEY:
/* 277 */                           if (InventoryUtils.isEnoughItem(pd.getPlayer(), SpecialShopItem.this.price).booleanValue()) {
/* 278 */                             switch (SpecialShopItem.this.eqsort) {
/*     */                               case SHIFT_LEFT:
/* 280 */                                 pd.addArmor(SpecialShopItem.this.equipment);
/*     */                                 break;
/*     */                               case null:
/* 283 */                                 pd.setCut(Boolean.valueOf(true)); break;
/*     */                             } 
/* 285 */                             pd.getPlayer().sendMessage("§a你购买了§6" + SpecialShopItem.this.displayname);
/* 286 */                             pd.refreshArmor();
/* 287 */                             pd.getPlayer().playSound(pd.getPlayer().getLocation(), Sound.NOTE_PLING, 1.0F, 2.0F);
/* 288 */                             InventoryUtils.deleteitem(pd.getPlayer(), SpecialShopItem.this.price);
/* 289 */                             SpecialShopItem.this.menu().Setup(); break;
/*     */                           } 
/* 291 */                           pd.getPlayer().sendMessage("§c" + SpecialShopItem.this.price.getResource().getDisplayName() + "不足! 还需要" + SpecialShopItem.this.price.getResource().getDisplayName() + "x" + 
/* 292 */                               InventoryUtils.manyleft(pd.getPlayer(), SpecialShopItem.this.price) + "!");
/* 293 */                           pd.getPlayer().playSound(pd.getPlayer().getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 0.5F);
/*     */                           break;
/*     */                         
/*     */                         case LEFT:
/* 297 */                           pd.getPlayer().sendMessage("§c你已经拥有了一个更高级的物品.");
/* 298 */                           pd.getPlayer().playSound(pd.getPlayer().getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 0.5F);
/*     */                           break;
/*     */                         case SHIFT_LEFT:
/*     */                           return;
/*     */                       } 
/*     */                       break;
/*     */                     case null:
/* 305 */                       switch (SpecialShopItem.this.sort) {
/*     */                         case NUMBER_KEY:
/* 307 */                           if (InventoryUtils.isEnoughItem(pd.getPlayer(), SpecialShopItem.this.price).booleanValue()) {
/* 308 */                             switch (SpecialShopItem.this.eqsort) {
/*     */                               case SHIFT_LEFT:
/* 310 */                                 pd.addArmor(SpecialShopItem.this.equipment);
/*     */                                 break;
/*     */                               case null:
/* 313 */                                 pd.setCut(Boolean.valueOf(true)); break;
/*     */                             } 
/* 315 */                             pd.getPlayer().sendMessage("§a你购买了§6" + SpecialShopItem.this.displayname);
/* 316 */                             pd.refreshArmor();
/* 317 */                             InventoryUtils.deleteitem(pd.getPlayer(), SpecialShopItem.this.price);
/* 318 */                             if (e.getClick() == ClickType.NUMBER_KEY) {
/* 319 */                               int j = e.getHotbarButton();
/* 320 */                               ItemStack is = pd.getPlayer().getInventory().getItem(j);
/* 321 */                               if (is != null && 
/* 322 */                                 is.getType() != Material.AIR && is.getType() != ib.toItemStack().getType()) {
/* 323 */                                 ItemStack origin = is.clone();
/* 324 */                                 pd.getPlayer().getInventory().remove(is);
/* 325 */                                 Bukkit.getScheduler().runTaskLater((Plugin)BedWars.getInstance(), () -> { for (int j = 0; j < pd.getPlayer().getInventory().getSize(); j++) { if (pd.getPlayer().getInventory().getItem(j) == null) { pd.getPlayer().getInventory().setItem(j, origin); return; }  if (pd.getPlayer().getInventory().getItem(j).getType() == Material.AIR) { pd.getPlayer().getInventory().setItem(j, origin); return; }  }  }2L);
/*     */                               } 
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
/* 339 */                               pd.getPlayer().getInventory().setItem(j, SpecialShopItem.this.equipment.getgiveItem());
/*     */                             } else {
/* 341 */                               pd.getPlayer().getInventory().addItem(new ItemStack[] { this.this$0.equipment.getgiveItem() });
/* 342 */                             }  pd.getPlayer().playSound(pd.getPlayer().getLocation(), Sound.NOTE_PLING, 1.0F, 2.0F);
/* 343 */                             SpecialShopItem.this.menu().Setup(); break;
/*     */                           } 
/* 345 */                           pd.getPlayer().sendMessage("§c" + SpecialShopItem.this.price.getResource().getDisplayName() + "不足! 还需要" + SpecialShopItem.this.price.getResource().getDisplayName() + "x" + 
/* 346 */                               InventoryUtils.manyleft(pd.getPlayer(), SpecialShopItem.this.price) + "!");
/* 347 */                           pd.getPlayer().playSound(pd.getPlayer().getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 0.5F);
/*     */                           break;
/*     */                         
/*     */                         case SHIFT_LEFT:
/*     */                           return;
/*     */                       } 
/*     */                       break;
/*     */                   } 
/*     */                   break;
/*     */                 case SHIFT_LEFT:
/* 357 */                   if (pd.getSetingItem() == null) {
/* 358 */                     pd.getPlayer().closeInventory();
/*     */                     return;
/*     */                   } 
/* 361 */                   pd.getQuickShopData().getItems().keySet().removeIf(i -> (pd.getQuickShopData().getItems().get(i) != null && ((ShopItemBasic)pd.getQuickShopData().getItems().get(i)).getType().equals(pd.getSetingItem().getType())));
/*     */                   
/* 363 */                   isinslot = false;
/* 364 */                   exit = 0;
/* 365 */                   slots = 0;
/* 366 */                   for (i = 18; i < 44; i++) {
/* 367 */                     if (i % 9 == 0 || i % 9 == 8) {
/* 368 */                       exit++;
/*     */                     
/*     */                     }
/* 371 */                     else if (i == e.getSlot()) {
/* 372 */                       slots = i - 18 - exit;
/* 373 */                       isinslot = true;
/*     */                       break;
/*     */                     } 
/*     */                   } 
/* 377 */                   if (isinslot) {
/* 378 */                     (pd.getQuickShopData()).items.put(Integer.valueOf(slots), pd.getSetingItem());
/* 379 */                     BedWars.getInstance().getQuickBuyManager().save(pd.getUuid(), pd.getQuickShopData());
/* 380 */                     (new QuickShop(pd.getPlayer())).open();
/*     */                   } 
/*     */                   break;
/*     */               } 
/*     */               break;
/*     */             case SHIFT_LEFT:
/* 386 */               switch (where) {
/*     */                 case LEFT:
/* 388 */                   for (Map.Entry<Integer, ShopItemBasic> entry : (Iterable<Map.Entry<Integer, ShopItemBasic>>)pd.getQuickShopData().getItems().entrySet()) {
/* 389 */                     if (pd.getQuickShopData().getItems().get(entry.getKey()) != null && ((ShopItemBasic)entry
/* 390 */                       .getValue()).getType().equals(SpecialShopItem.this.getType())) {
/* 391 */                       pd.getQuickShopData().getItems().remove(entry.getKey());
/* 392 */                       BedWars.getInstance().getQuickBuyManager().save(pd.getUuid(), pd.getQuickShopData());
/* 393 */                       SpecialShopItem.this.menu().Setup();
/*     */                       break;
/*     */                     } 
/*     */                   } 
/*     */                   break;
/*     */                 case NUMBER_KEY:
/* 399 */                   switch (SpecialShopItem.this.eqsort) {
/*     */                     case SHIFT_LEFT:
/*     */                     case null:
/* 402 */                       pd.setSetingItem(new SpecialShopItem(pd, SpecialShopItem.this.equipment));
/*     */                       break;
/*     */                     case NUMBER_KEY:
/*     */                     case LEFT:
/* 406 */                       pd.setSetingItem(new SpecialShopItem(pd, SpecialShopItem.this.eqsort));
/*     */                       break;
/*     */                   } 
/* 409 */                   Bukkit.getScheduler().runTaskLater((Plugin)BedWars.getInstance(), () -> (new QuickShopEditor(pd.getPlayer())).open(), 5L);
/*     */                   break;
/*     */               } 
/*     */               break;
/*     */           }  }
/*     */          }
/*     */       ;
/*     */   }
/*     */   
/*     */   public enum Sort {
/* 419 */     COVERED, BOUGHT, NON;
/*     */   }
/*     */ }