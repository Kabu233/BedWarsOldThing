/*    */ package cn.rmc.bedwars.utils.inventory;
/*    */ 
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.inventory.InventoryClickEvent;
/*    */ 
/*    */ public class UIListener
/*    */   implements Listener
/*    */ {
/*    */   @EventHandler
/*    */   public void onClick(InventoryClickEvent event) {
/* 12 */     if (!(event.getInventory().getHolder() instanceof InventoryUI.InventoryUIHolder)) {
/*    */       return;
/*    */     }
/* 15 */     InventoryUI.InventoryUIHolder inventoryUIHolder = (InventoryUI.InventoryUIHolder)event.getInventory().getHolder();
/* 16 */     InventoryUI ui = inventoryUIHolder.getInventoryUI();
/* 17 */     ui.onInventoryClick(event);
/* 18 */     if (event.getInventory() == null) {
/*    */       return;
/*    */     }
/* 21 */     if (event.getCurrentItem() == null) {
/*    */       return;
/*    */     }
/* 24 */     if (event.getClickedInventory() == null || !event.getInventory().equals(event.getClickedInventory())) {
/*    */       return;
/*    */     }
/* 27 */     event.setCancelled(true);
/*    */ 
/*    */     
/* 30 */     InventoryUI.ClickableItem item = ui.getCurrentUI().getItem(event.getSlot());
/*    */     
/* 32 */     if (item == null) {
/*    */       return;
/*    */     }
/* 35 */     item.onClick(event);
/*    */   }
/*    */   @EventHandler
/*    */   public void onClicker(InventoryClickEvent event) {
/* 39 */     if (!(event.getInventory().getHolder() instanceof InventoryUI.InventoryUIHolder)) {
/*    */       return;
/*    */     }
/*    */     
/* 43 */     if (!(event.getClickedInventory().getHolder() instanceof InventoryUI.InventoryUIHolder))
/* 44 */       event.setCancelled(true); 
/*    */   }
/*    */ }