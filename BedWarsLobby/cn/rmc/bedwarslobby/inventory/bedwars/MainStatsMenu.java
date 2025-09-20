/*    */ package cn.rmc.bedwarslobby.inventory.bedwars;
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
/*    */ public class MainStatsMenu extends MenuBasic {
/*    */   public MainStatsMenu(Player p) {
/* 14 */     super(p, "起床战争统计数据", Integer.valueOf(6));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void Setup() {
/* 19 */     this.inventoryUI.setItem(4, (InventoryUI.ClickableItem)(new StatsItem(this.player, "综合统计", "TOTAL")).build(Material.PAPER, Boolean.valueOf(true)));
/* 20 */     this.inventoryUI.setItem(21, (InventoryUI.ClickableItem)(new StatsItem(this.player, GameMode.EIGHT_ONE)).build(Material.PAPER, Boolean.valueOf(true)));
/* 21 */     this.inventoryUI.setItem(23, (InventoryUI.ClickableItem)(new StatsItem(this.player, GameMode.FOUR_FOUR)).build(Material.PAPER, Boolean.valueOf(true)));
/* 22 */     this.inventoryUI.setItem(31, (InventoryUI.ClickableItem)(new StatsItem(this.player, GameMode.FOUR_FOUR_RUSH)).build(Material.FEATHER, Boolean.valueOf(true)));
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 31 */     this.inventoryUI.setItem(49, (InventoryUI.ClickableItem)new InventoryUI.AbstractClickableItem((new ItemBuilder(Material.BARRIER)).setName("§c关闭").toItemStack())
/*    */         {
/*    */           public void onClick(InventoryClickEvent e) {
/* 34 */             MainStatsMenu.this.player.closeInventory();
/*    */           }
/*    */         });
/*    */   }
/*    */ }