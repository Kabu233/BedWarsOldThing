/*    */ package cn.rmc.bedwars.listener;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import cn.rmc.bedwars.BedWars;
/*    */ import cn.rmc.bedwars.game.PlayerData;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.entity.EntityType;
/*    */ import org.bukkit.entity.Fireball;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.entity.Projectile;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.EventPriority;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.block.BlockBurnEvent;
/*    */ import org.bukkit.event.entity.EntityDeathEvent;
/*    */ import org.bukkit.event.entity.EntityExplodeEvent;
/*    */ import org.bukkit.event.weather.WeatherChangeEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldListener
/*    */   implements Listener
/*    */ {
/*    */   @EventHandler
/*    */   public void WeatherChange(WeatherChangeEvent e) {
/* 31 */     e.setCancelled(true);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onKillEntity(EntityDeathEvent e) {
/* 36 */     e.setDroppedExp(0);
/* 37 */     if (!(e.getEntity() instanceof Player)) e.getDrops().clear(); 
/*    */   }
/*    */   
/*    */   @EventHandler(priority = EventPriority.HIGHEST)
/*    */   public void onEntityExplode(EntityExplodeEvent e) {
/* 42 */     if (e.getEntity().getType() == EntityType.PRIMED_TNT)
/*    */     {
/* 44 */       if (e.getEntity().getCustomName() != null) {
/* 45 */         Player p = Bukkit.getPlayer(e.getEntity().getCustomName());
/* 46 */         PlayerData pd = BedWars.getInstance().getPlayerManager().get(p);
/* 47 */         e.blockList().removeIf(block -> (!pd.getGame().isBlock(block).booleanValue() || block.getType() == Material.BED));
/*    */       } else {
/* 49 */         e.blockList().clear();
/*    */       } 
/*    */     }
/* 52 */     if (e.getEntity().getType() == EntityType.FIREBALL) {
/*    */       try {
/* 54 */         Player p = (Player)((Projectile)e.getEntity()).getShooter();
/* 55 */         PlayerData pd = BedWars.getInstance().getPlayerManager().get(p);
/* 56 */         Fireball ball = (Fireball)e.getEntity();
/* 57 */         e.blockList().removeIf(block -> (!pd.getGame().isBlock(block).booleanValue() || block.getType() == Material.BED || block.getType() == Material.ENDER_STONE));
/*    */       }
/* 59 */       catch (Exception ex) {
/* 60 */         e.blockList().removeIf(block -> (block.getType() == Material.BED || block.getType() == Material.ENDER_STONE));
/*    */       } 
/*    */     }
/*    */ 
/*    */     
/* 65 */     Location location = e.getLocation().getBlock().getLocation();
/* 66 */     List<Block> blocklist = new ArrayList<>(e.blockList());
/* 67 */     List<Block> glass = new ArrayList<>();
/* 68 */     for (Block block : e.blockList()) {
/* 69 */       if (block.getType() == Material.GLASS || block.getType() == Material.STAINED_GLASS) {
/* 70 */         glass.add(block);
/*    */       }
/*    */     } 
/* 73 */     e.blockList().removeAll(glass);
/* 74 */     List<Block> blocks = new ArrayList<>();
/* 75 */     for (Block block2 : e.blockList()) {
/* 76 */       for (Block glassblock : glass) {
/* 77 */         if (location.distance(block2.getLocation()) > location.distance(glassblock.getLocation()) && location.distance(block2.getLocation()) > block2.getLocation().distance(glassblock.getLocation())) {
/* 78 */           blocks.add(block2);
/*    */         }
/*    */       } 
/*    */     } 
/* 82 */     e.blockList().removeAll(blocks);
/* 83 */     List<Block> addblocks = new ArrayList<>();
/* 84 */     addblocks.add(location.clone().add(0.0D, 1.0D, 0.0D).getBlock());
/* 85 */     addblocks.add(location.clone().add(0.0D, -1.0D, 0.0D).getBlock());
/* 86 */     addblocks.add(location.clone().add(0.0D, 0.0D, 1.0D).getBlock());
/* 87 */     addblocks.add(location.clone().add(0.0D, 0.0D, -1.0D).getBlock());
/* 88 */     addblocks.add(location.clone().add(1.0D, 0.0D, 0.0D).getBlock());
/* 89 */     addblocks.add(location.clone().add(-1.0D, 0.0D, 0.0D).getBlock());
/* 90 */     for (Block block3 : addblocks) {
/* 91 */       if (blocklist.contains(block3) && block3.getType() != Material.GLASS && block3.getType() != Material.STAINED_GLASS) {
/* 92 */         e.blockList().add(block3);
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onFireBurn(BlockBurnEvent event) {
/* 99 */     event.setCancelled(true);
/*    */   }
/*    */ }