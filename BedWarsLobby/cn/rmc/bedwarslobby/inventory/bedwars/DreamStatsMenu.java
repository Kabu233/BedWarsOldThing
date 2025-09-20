/*    */ package cn.rmc.bedwarslobby.inventory.bedwars;
/*    */ 
/*    */ import cn.rmc.bedwarslobby.enums.GameMode;
/*    */ import cn.rmc.bedwarslobby.inventory.MenuBasic;
/*    */ import cn.rmc.bedwarslobby.inventory.item.StatsItem;
/*    */ import cn.rmc.bedwarslobby.util.ItemBuilder;
/*    */ import cn.rmc.bedwarslobby.util.inventory.InventoryUI;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.inventory.InventoryClickEvent;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class DreamStatsMenu extends MenuBasic {
/*    */   public DreamStatsMenu(Player p) {
/* 15 */     super(p, "起床战争梦幻模式统计数据", Integer.valueOf(6));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void Setup() {
/* 20 */     this.inventoryUI.setItem(4, (InventoryUI.ClickableItem)(new StatsItem(this.player, "梦幻综合统计", "DREAMS")).build(Material.EYE_OF_ENDER, Boolean.valueOf(true)));
/* 21 */     this.inventoryUI.setItem(20, (InventoryUI.ClickableItem)(new StatsItem(this.player, GameMode.FOUR_FOUR_RUSH)).build(Material.FEATHER, Boolean.valueOf(true)));
/* 22 */     this.inventoryUI.setItem(22, (InventoryUI.ClickableItem)(new StatsItem(this.player, GameMode.FOUR_FOUR_ULTIMATE)).build(Material.NETHER_STAR, Boolean.valueOf(true)));
/* 23 */     this.inventoryUI.setItem(24, (InventoryUI.ClickableItem)(new StatsItem(this.player, GameMode.FOUR_FOUR_ARMED)).build(Material.NETHER_STAR, Boolean.valueOf(true)));
/* 24 */     this.inventoryUI.setItem(49, (InventoryUI.ClickableItem)new InventoryUI.AbstractClickableItem((new ItemBuilder(Material.ARROW)).setName("§a返回").setLore(new String[] { "§7至起床战争综合统计" }).toItemStack())
/*    */         {
/*    */           public void onClick(InventoryClickEvent e) {
/* 27 */             (new MainStatsMenu(DreamStatsMenu.this.player)).open();
/*    */           }
/*    */         });
/*    */   }
/*    */ }