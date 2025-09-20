/*    */ package cn.rmc.bedwars.game.dream.armed;
/*    */ 
/*    */ import cn.rmc.bedwars.event.PlayerSpawnEvent;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.EventPriority;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.inventory.InventoryClickEvent;
/*    */ import org.bukkit.event.player.PlayerDropItemEvent;
/*    */ import org.bukkit.inventory.Inventory;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class ArmedListener
/*    */   implements Listener
/*    */ {
/*    */   ArmedManager manager;
/*    */   
/*    */   public ArmedListener(ArmedManager manager) {
/* 20 */     this.manager = manager;
/*    */   }
/*    */   @EventHandler
/*    */   public void onSpawn(PlayerSpawnEvent e) {
/* 24 */     e.getPlayer().getPlayer().getInventory().addItem(new ItemStack[] { GunType.PISTOL.giveItem(e.getPlayer()) });
/*    */   }
/*    */   @EventHandler(priority = EventPriority.HIGHEST)
/*    */   public void onDrop(PlayerDropItemEvent e) {
/* 28 */     if (e.getItemDrop().getItemStack().getType() == Material.WOOD_HOE) e.setCancelled(true); 
/*    */   }
/*    */   @EventHandler(priority = EventPriority.HIGHEST)
/*    */   public void onCLick(InventoryClickEvent event) {
/* 32 */     Player p = (Player)event.getWhoClicked();
/* 33 */     Inventory clicked = event.getClickedInventory();
/* 34 */     if (event.getInventory().getHolder() instanceof org.bukkit.block.Chest || event.getInventory().getHolder() instanceof org.bukkit.block.DoubleChest || event
/* 35 */       .getInventory().getHolder() == null)
/*    */     {
/* 37 */       if (clicked == event.getWhoClicked().getInventory()) {
/*    */         
/* 39 */         ItemStack clickedOn = event.getCurrentItem();
/* 40 */         if (clickedOn.getType() == Material.WOOD_HOE)
/* 41 */           event.setCancelled(true); 
/*    */       } 
/*    */     }
/*    */   }
/*    */ }