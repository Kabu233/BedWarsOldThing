/*     */ package cn.rmc.bedwarslobby.inventory.store;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.stream.Collectors;
/*     */ import cn.rmc.bedwarslobby.BedWarsLobby;
/*     */ import cn.rmc.bedwarslobby.enums.Data;
/*     */ import cn.rmc.bedwarslobby.enums.PlayerState;
/*     */ import cn.rmc.bedwarslobby.inventory.MenuBasic;
/*     */ import cn.rmc.bedwarslobby.loot.LootChestBasic;
/*     */ import cn.rmc.bedwarslobby.loot.chest.NormalChest;
/*     */ import cn.rmc.bedwarslobby.loot.enums.Head;
/*     */ import cn.rmc.bedwarslobby.loot.enums.ShopSkin;
/*     */ import cn.rmc.bedwarslobby.loot.other.LootInfo;
/*     */ import cn.rmc.bedwarslobby.object.PlayerData;
/*     */ import cn.rmc.bedwarslobby.util.DataUtils;
/*     */ import cn.rmc.bedwarslobby.util.ItemBuilder;
/*     */ import cn.rmc.bedwarslobby.util.ItemUtil;
/*     */ import cn.rmc.bedwarslobby.util.MathUtils;
/*     */ import cn.rmc.bedwarslobby.util.inventory.InventoryUI;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.inventory.InventoryClickEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ import org.bukkit.scheduler.BukkitTask;
/*     */ 
/*     */ public class StoreMainMenu extends MenuBasic {
/*     */   PlayerData pd;
/*     */   
/*     */   public StoreMainMenu(PlayerData pd) {
/*  37 */     super(pd.getPlayer(), "§8起床主菜单&商店", Integer.valueOf(6));
/*  38 */     this.pd = pd;
/*     */   }
/*     */   
/*     */   protected void Setup() {
/*  42 */     this.inventoryUI.setItem(11, (InventoryUI.ClickableItem)new InventoryUI.AbstractClickableItem(
/*  43 */           createItem(Material.BLAZE_POWDER, "§a快捷栏管理", Arrays.asList(new String[] { "§7编辑每类物品的首选槽位.", "", "§e点击编辑!" })))
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onClick(InventoryClickEvent e)
/*     */           {
/*  51 */             e.getWhoClicked().sendMessage("§c无法找到关于这一项的数据!");
/*     */           }
/*     */         });
/*     */     
/*  55 */     this.inventoryUI.setItem(13, (InventoryUI.ClickableItem)new InventoryUI.AbstractClickableItem(
/*  56 */           createItem(Material.NETHER_STAR, "§a编辑快速购买", Arrays.asList(new String[] { "§7更改游戏内的快速购买栏布局.", "", "§e点击编辑!" })))
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onClick(InventoryClickEvent e)
/*     */           {
/*  64 */             e.getWhoClicked().sendMessage("§c无法找到关于这一项的数据!");
/*     */           }
/*     */         });
/*     */     
/*  68 */     this.inventoryUI.setItem(15, (InventoryUI.ClickableItem)new InventoryUI.AbstractClickableItem(
/*  69 */           createItem(Material.BED, "§a我的特效", Arrays.asList(new String[] { "§7浏览已解锁的起床特效", "§7或直接用硬币购买.", "§7你也可打开§6奖励箱", "§7解锁全新特效!", "", "§e点击浏览!" })))
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onClick(InventoryClickEvent e)
/*     */           {
/*  80 */             e.getWhoClicked().sendMessage("§c无法找到关于这一项的数据!");
/*     */           }
/*     */         });
/*     */     
/*  84 */     final Integer amount = this.pd.getLootChest();
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
/* 124 */     BukkitTask openRefresh = (new BukkitRunnable() { final List<String> start = Arrays.asList(new String[] { "", "▸" }); int i = 0; public void run() { if (this.i == this.start.size()) this.i = 0;  StoreMainMenu.this.inventoryUI.setItem(29, (InventoryUI.ClickableItem)new InventoryUI.AbstractClickableItem(StoreMainMenu.this.createItem(Head.LootChest.getBase64(), "§f打开奖励箱", Arrays.asList(new String[] { "§7点击打开包含§a普通§7,", "§9稀有§7,§5史诗§7和§6传奇§7特效", "§7的起床奖励箱!", "", "§f" + (String)this.start.get(this.i) + " " + ((this.val$amount.intValue() == 0) ? "§c0个§f奖励箱§c可用!" : ("§a" + MathUtils.Format(this.val$amount) + "个§f奖励箱§a可用!")), "", "§7当前可选取:", "§f奖励箱", "", "§e点击打开!" }))) { public void onClick(InventoryClickEvent e) { if (amount.intValue() == 0) { e.getWhoClicked().sendMessage("§c你没有任何箱子可以打开!"); } else if (LootChestBasic.openPlayers.contains(StoreMainMenu.this.pd.getPlayer())) { StoreMainMenu.this.pd.getPlayer().sendMessage("§c当你在菜单中你不能这么做!"); } else { NormalChest normalChest = new NormalChest((Player)e.getWhoClicked()); normalChest.Start(); }  } }); this.i++; } }).runTaskTimer((Plugin)BedWarsLobby.getInstance(), 0L, 5L);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 144 */     BukkitTask shopRefresh = (new BukkitRunnable() { final List<String> list = (List<String>)Arrays.<ShopSkin>stream(ShopSkin.values()).map(ShopSkin::getBase64).collect(Collectors.toList()); int i = 0; public void run() { if (this.i == this.list.size()) this.i = 0;  StoreMainMenu.this.inventoryUI.setItem(31, (InventoryUI.ClickableItem)new InventoryUI.AbstractClickableItem(StoreMainMenu.this.createItem(this.list.get(this.i), "§6购买起床奖励箱", Arrays.asList(new String[] { "§7点击查看91MC商城链接,", "§7购买§6起床奖励箱", "", "§e点击查看商城链接!" }))) { public void onClick(InventoryClickEvent e) { Player p = (Player)e.getWhoClicked(); p.closeInventory(); p.playSound(p.getLocation(), Sound.LEVEL_UP, 5.0F, 20.0F); ShopSkin.openBook(p); } }); this.i++; } }).runTaskTimerAsynchronously((Plugin)BedWarsLobby.getInstance(), 0L, 4L);
/* 145 */     List<LootInfo> list = new ArrayList<>(this.pd.getBoughtList());
/* 146 */     Collections.reverse(list);
/* 147 */     list.stream().sorted(Comparator.comparing(LootInfo::getRarity).reversed()).collect(Collectors.toList());
/* 148 */     List<String> lore = new ArrayList<>();
/* 149 */     if (list.isEmpty()) {
/* 150 */       for (int i = 0; i < 5; i++) {
/* 151 */         lore.add("§7" + (i + 1) + ". 无");
/*     */       }
/*     */     } else {
/* 154 */       int i = 0;
/*     */       
/* 156 */       for (LootInfo info : list) {
/* 157 */         if (i < 5) {
/* 158 */           lore.add("§7" + (i + 1) + ". " + info.getChestName());
/*     */         }
/*     */         
/* 161 */         i++;
/*     */       } 
/*     */       
/* 164 */       while (i < 5) {
/* 165 */         lore.add("§7" + (i + 1) + ". 无");
/* 166 */         i++;
/*     */       } 
/*     */     } 
/*     */     
/* 170 */     this.inventoryUI.setItem(33, (InventoryUI.ClickableItem)new InventoryUI.EmptyClickableItem(createItem(Material.BOOK, "§a奖励箱相关信息", 
/* 171 */             Arrays.asList(new String[] {
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 176 */                 "§7打开§6起床奖励箱§7以解锁特效!", "§6起床奖励箱§7可以在91MC商城购买:", "§ehttps://store.91mc.top", "", "§e你最近找到的前五个物品是:", lore.get(0), lore
/* 177 */                 .get(1), lore
/* 178 */                 .get(2), lore
/* 179 */                 .get(3), lore
/* 180 */                 .get(4)
/*     */               }))));
/* 182 */     this.inventoryUI.setItem(49, (InventoryUI.ClickableItem)new InventoryUI.AbstractClickableItem(createItem(Material.BARRIER, "§c关闭")) {
/*     */           public void onClick(InventoryClickEvent e) {
/* 184 */             e.getWhoClicked().closeInventory();
/*     */           }
/*     */         });
/* 187 */     this.inventoryUI.setItem(50, (InventoryUI.ClickableItem)new InventoryUI.EmptyClickableItem((new ItemBuilder(Material.EMERALD))
/* 188 */           .setName("§7总硬币: §6" + MathUtils.Format(DataUtils.getInt(this.pd.getPlayer().getUniqueId().toString(), Data.Field.COIN)))
/* 189 */           .setLore(new String[] { "§6https://store.91mc.top" }).toItemStack()));
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
/* 200 */     BukkitTask update = (new BukkitRunnable() { public void run() { if (StoreMainMenu.this.pd.getState() == PlayerState.OPENING) { StoreMainMenu.this.pd.getPlayer().updateInventory(); } else { cancel(); }  } }).runTaskTimer((Plugin)BedWarsLobby.getInstance(), 0L, 1L);
/* 201 */     Arrays.<BukkitTask>asList(new BukkitTask[] { openRefresh, shopRefresh, update }).forEach(task -> {
/*     */           this.pd.removeTask(task);
/*     */           this.pd.addTask(task);
/*     */         });
/*     */   }
/*     */   
/*     */   ItemStack createItem(Material material, String displayName) {
/* 208 */     ItemStack is = new ItemStack(material);
/* 209 */     ItemMeta im = is.getItemMeta();
/* 210 */     im.setDisplayName(displayName);
/* 211 */     is.setItemMeta(im);
/* 212 */     return is;
/*     */   }
/*     */   
/*     */   ItemStack createItem(Material material, String displayName, List<String> lore) {
/* 216 */     ItemStack is = new ItemStack(material);
/* 217 */     ItemMeta im = is.getItemMeta();
/* 218 */     im.setDisplayName(displayName);
/* 219 */     im.setLore(lore);
/* 220 */     is.setItemMeta(im);
/* 221 */     return is;
/*     */   }
/*     */   
/*     */   ItemStack createItem(Material material, Integer amount, String displayName, List<String> lore) {
/* 225 */     ItemStack is = new ItemStack(material);
/* 226 */     ItemMeta im = is.getItemMeta();
/* 227 */     im.setDisplayName(displayName);
/* 228 */     im.setLore(lore);
/* 229 */     is.setItemMeta(im);
/* 230 */     is.setAmount(amount.intValue());
/* 231 */     return is;
/*     */   }
/*     */   
/*     */   ItemStack createItem(String head, String displayName, List<String> lore) {
/* 235 */     ItemStack is = new ItemStack(ItemUtil.getCustomSkull(head));
/* 236 */     ItemMeta im = is.getItemMeta();
/* 237 */     im.setDisplayName(displayName);
/* 238 */     im.setLore(lore);
/* 239 */     is.setItemMeta(im);
/* 240 */     return is;
/*     */   }
/*     */ }