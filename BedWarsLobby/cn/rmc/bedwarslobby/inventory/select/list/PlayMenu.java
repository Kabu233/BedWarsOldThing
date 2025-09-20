/*    */ package cn.rmc.bedwarslobby.inventory.select.list;
/*    */ 
/*    */ import cn.rmc.bedwarslobby.inventory.MenuBasic;
/*    */ import cn.rmc.bedwarslobby.inventory.select.MapSelector;
/*    */ import cn.rmc.bedwarslobby.util.ItemBuilder;
/*    */ import cn.rmc.bedwarslobby.util.inventory.InventoryUI;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.inventory.InventoryClickEvent;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class PlayMenu
/*    */   extends MenuBasic
/*    */ {
/*    */   public PlayMenu(Player p) {
/* 16 */     super(p, "§8游玩起床战争", Integer.valueOf(4));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void Setup() {
/* 22 */     this.inventoryUI.setItem(12, (InventoryUI.ClickableItem)new InventoryUI.AbstractClickableItem((new ItemBuilder(Material.BED))
/* 23 */           .setName("§a起床战争 (4v4v4v4)").setLore(new String[] { "§7游玩起床战争4v4v4v4模式", "", "§7与其他3名队友联手来击败其余3支队伍!", "§7破坏敌人的床以阻止他们重生!", "§7保护你的床免受破坏!", "", "§e点击开始游戏!"
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */             
/* 31 */             }).toItemStack())
/*    */         {
/*    */           public void onClick(InventoryClickEvent e) {
/* 34 */             MapSelector.randomTeleportPlayer((Player)e.getWhoClicked());
/*    */           }
/*    */         });
/*    */     
/* 38 */     this.inventoryUI.setItem(14, (InventoryUI.ClickableItem)new InventoryUI.AbstractClickableItem((new ItemBuilder(Material.SIGN))
/* 39 */           .setName("§a地图选择器 (4v4v4v4)").setLore(new String[] { "§7在可用的地图中挑选一张游玩.", "", "§e点击浏览!"
/*    */ 
/*    */ 
/*    */             
/* 43 */             }).toItemStack())
/*    */         {
/*    */           public void onClick(InventoryClickEvent e) {
/* 46 */             (new MapSelectorMenu((Player)e.getWhoClicked())).open();
/*    */           }
/*    */         });
/*    */     
/* 50 */     this.inventoryUI.setItem(27, (InventoryUI.ClickableItem)new InventoryUI.AbstractClickableItem((new ItemBuilder(Material.BLAZE_POWDER))
/* 51 */           .setName("§a快捷栏管理").setLore(new String[] { "§7编辑每类物品的首选槽位.", "", "§e点击编辑!"
/*    */ 
/*    */ 
/*    */             
/* 55 */             }).toItemStack()) {
/*    */           public void onClick(InventoryClickEvent e) {
/* 57 */             e.getWhoClicked().sendMessage("§c无法找到关于这一项的数据!");
/*    */           }
/*    */         });
/*    */     
/* 61 */     this.inventoryUI.setItem(31, (InventoryUI.ClickableItem)new InventoryUI.AbstractClickableItem((new ItemBuilder(Material.BARRIER))
/* 62 */           .setName("§c关闭").toItemStack())
/*    */         {
/*    */           public void onClick(InventoryClickEvent e) {
/* 65 */             e.getWhoClicked().closeInventory();
/*    */           }
/*    */         });
/* 68 */     this.inventoryUI.setItem(35, (InventoryUI.ClickableItem)new InventoryUI.AbstractClickableItem((new ItemBuilder(Material.ENDER_PEARL))
/* 69 */           .setName("§c点击这里重新加入!").setLore(new String[] { "§7如果你掉线了, 点击这里来重新回到游戏"
/*    */             
/* 71 */             }).toItemStack())
/*    */         {
/*    */           public void onClick(InventoryClickEvent e) {
/* 74 */             e.getWhoClicked().sendMessage("§c无法找到关于这一项的数据!");
/*    */           }
/*    */         });
/*    */   }
/*    */ }