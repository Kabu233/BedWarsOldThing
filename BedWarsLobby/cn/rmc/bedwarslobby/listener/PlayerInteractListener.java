/*    */ package cn.rmc.bedwarslobby.listener;
/*    */ 
/*    */ import java.util.Objects;
/*    */ import cn.rmc.bedwarslobby.BedWarsLobby;
/*    */ import cn.rmc.bedwarslobby.inventory.store.StoreMainMenu;
/*    */ import cn.rmc.bedwarslobby.loot.LootChestBasic;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.entity.EntityType;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.block.Action;
/*    */ import org.bukkit.event.player.PlayerInteractEntityEvent;
/*    */ import org.bukkit.event.player.PlayerInteractEvent;
/*    */ 
/*    */ public class PlayerInteractListener
/*    */   implements Listener {
/*    */   @EventHandler
/*    */   public void onInt(PlayerInteractEvent e) {
/* 20 */     Player p = e.getPlayer();
/* 21 */     if ((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && 
/* 22 */       e.getItem() != null && 
/* 23 */       Objects.requireNonNull(e.getItem().getType()) == Material.EMERALD) {
/* 24 */       (new StoreMainMenu(BedWarsLobby.getInstance().getPlayerManager().get(p))).open();
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @EventHandler
/*    */   public void onIntEntity(PlayerInteractEntityEvent e) {
/* 32 */     if (e.getRightClicked().getType() == EntityType.ARMOR_STAND) {
/* 33 */       Player p = e.getPlayer();
/* 34 */       if (LootChestBasic.openPlayers.contains(p))
/* 35 */         e.setCancelled(true); 
/*    */     } 
/*    */   }
/*    */ }