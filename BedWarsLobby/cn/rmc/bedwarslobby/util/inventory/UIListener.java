/*    */ package cn.rmc.bedwarslobby.util.inventory;
/*    */ 
/*    */ import cn.rmc.bedwarslobby.BedWarsLobby;
/*    */ import cn.rmc.bedwarslobby.enums.PlayerState;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.inventory.InventoryClickEvent;
/*    */ import org.bukkit.event.inventory.InventoryCloseEvent;
/*    */ import org.bukkit.event.inventory.InventoryOpenEvent;
/*    */ 
/*    */ public class UIListener
/*    */   implements Listener {
/*    */   @EventHandler
/*    */   public void onClick(InventoryClickEvent event) {
/* 16 */     if (event.getInventory() == null || !(event.getInventory().getHolder() instanceof InventoryUI.InventoryUIHolder) || event.getCurrentItem() == null) {
/*    */       return;
/*    */     }
/* 19 */     InventoryUI.InventoryUIHolder inventoryUIHolder = (InventoryUI.InventoryUIHolder)event.getInventory().getHolder();
/* 20 */     event.setCancelled(true);
/* 21 */     if (event.getClickedInventory() == null || !event.getInventory().equals(event.getClickedInventory())) {
/*    */       return;
/*    */     }
/* 24 */     InventoryUI ui = inventoryUIHolder.getInventoryUI();
/* 25 */     InventoryUI.ClickableItem item = ui.getCurrentUI().getItem(event.getSlot());
/* 26 */     if (item == null) {
/*    */       return;
/*    */     }
/* 29 */     item.onClick(event);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onInvOpen(InventoryOpenEvent e) {
/* 34 */     if (e.getInventory() != null && e.getInventory().getHolder() instanceof InventoryUI.InventoryUIHolder && e.getPlayer() instanceof Player) {
/* 35 */       Player p = (Player)e.getPlayer();
/* 36 */       if (BedWarsLobby.getInstance().getPlayerManager().get(p) != null) {
/* 37 */         BedWarsLobby.getInstance().getPlayerManager().get(p).setState(PlayerState.OPENING);
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onInvClose(InventoryCloseEvent e) {
/* 44 */     if (e.getInventory() == null || !(e.getInventory().getHolder() instanceof InventoryUI.InventoryUIHolder)) {
/*    */       return;
/*    */     }
/* 47 */     Player p = (Player)e.getPlayer();
/* 48 */     if (BedWarsLobby.getInstance().getPlayerManager().get(p) != null)
/* 49 */       BedWarsLobby.getInstance().getPlayerManager().get(p).setState(PlayerState.BREAKING); 
/*    */   }
/*    */ }