/*    */ package cn.rmc.bedwars.runnable.item;
/*    */ import cn.rmc.bedwars.BedWars;
/*    */ import cn.rmc.bedwars.game.PlayerData;
/*    */ import cn.rmc.bedwars.utils.world.LocationUtil;
/*    */ import cn.rmc.bedwars.utils.world.ParticleEffects;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.util.Vector;
/*    */ 
/*    */ public class SpongeRunnable extends BukkitRunnable {
/*    */   Block b;
/*    */   PlayerData pd;
/*    */   
/*    */   public SpongeRunnable(PlayerData pd, Block b) {
/* 18 */     this.pd = pd;
/* 19 */     this.b = b;
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/* 24 */     Location loc = this.b.getLocation().clone().add(0.5D, 0.5D, 0.5D);
/*    */ 
/*    */     
/* 27 */     Bukkit.getScheduler().runTaskAsynchronously((Plugin)BedWars.getInstance(), () -> {
/*    */           for (Location location : LocationUtil.getGround(loc, 1.2D)) {
/*    */             ParticleEffects.CLOUD.display(new Vector(), 1000.0F, location, 50.0D);
/*    */             if (location.getBlock().getType() == Material.STATIONARY_WATER || location.getBlock().getType() == Material.WATER || location.getBlock().getType() == Material.WATER_LILY) {
/*    */               Bukkit.getScheduler().runTask((Plugin)BedWars.getInstance(), ());
/*    */             }
/*    */           } 
/*    */         });
/* 35 */     Bukkit.getScheduler().runTaskLaterAsynchronously((Plugin)BedWars.getInstance(), () -> { for (Location location : LocationUtil.getGround(loc, 2.4D)) { ParticleEffects.CLOUD.display(new Vector(), 1000.0F, location, 50.0D); if (location.getBlock().getType() == Material.STATIONARY_WATER || location.getBlock().getType() == Material.WATER || location.getBlock().getType() == Material.WATER_LILY) Bukkit.getScheduler().runTask((Plugin)BedWars.getInstance(), ());  }  }10L);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 43 */     Bukkit.getScheduler().runTaskLaterAsynchronously((Plugin)BedWars.getInstance(), () -> { for (Location location : LocationUtil.getGround(loc, 3.6D)) { ParticleEffects.CLOUD.display(new Vector(), 1000.0F, location, 50.0D); if (location.getBlock().getType() == Material.STATIONARY_WATER || location.getBlock().getType() == Material.WATER || location.getBlock().getType() == Material.WATER_LILY) Bukkit.getScheduler().runTask((Plugin)BedWars.getInstance(), ());  }  }20L);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 51 */     Bukkit.getScheduler().runTask((Plugin)BedWars.getInstance(), () -> {
/*    */           this.pd.getGame().removeBlock(loc.getBlock());
/*    */           loc.getBlock().setType(Material.AIR);
/*    */         });
/* 55 */     for (int i = 1; i <= 2; i++) {
/* 56 */       Bukkit.getScheduler().runTaskLaterAsynchronously((Plugin)BedWars.getInstance(), () -> { for (Location location : LocationUtil.getGround(loc, 2.4D)) ParticleEffects.CLOUD.display(new Vector(), 1000.0F, location, 50.0D);  }(20 + 10 * i));
/*    */     } 
/*    */   }
/*    */ }