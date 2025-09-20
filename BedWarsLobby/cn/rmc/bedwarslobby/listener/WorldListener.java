/*    */ package cn.rmc.bedwarslobby.listener;
/*    */ 
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.block.BlockBreakEvent;
/*    */ import org.bukkit.event.block.BlockIgniteEvent;
/*    */ import org.bukkit.event.block.BlockPlaceEvent;
/*    */ import org.bukkit.event.entity.FoodLevelChangeEvent;
/*    */ import org.bukkit.event.player.PlayerDropItemEvent;
/*    */ import org.bukkit.event.weather.LightningStrikeEvent;
/*    */ import org.bukkit.event.weather.WeatherChangeEvent;
/*    */ 
/*    */ public class WorldListener
/*    */   implements Listener {
/*    */   @EventHandler
/*    */   public void onBreak(BlockBreakEvent e) {
/* 18 */     e.setCancelled(true);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onPlace(BlockPlaceEvent e) {
/* 23 */     e.setCancelled(true);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onWra(WeatherChangeEvent e) {
/* 28 */     e.setCancelled(true);
/* 29 */     e.getWorld().setWeatherDuration(0);
/* 30 */     e.getWorld().setThundering(false);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onDrop(PlayerDropItemEvent e) {
/* 35 */     e.setCancelled(true);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onFoodLevelChange(FoodLevelChangeEvent e) {
/* 40 */     Player p = (Player)e.getEntity();
/* 41 */     p.setFoodLevel(20);
/* 42 */     e.setCancelled(true);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onLightning(LightningStrikeEvent e) {
/* 47 */     e.setCancelled(true);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onIgnite(BlockIgniteEvent e) {
/* 52 */     e.setCancelled(true);
/*    */   }
/*    */ }