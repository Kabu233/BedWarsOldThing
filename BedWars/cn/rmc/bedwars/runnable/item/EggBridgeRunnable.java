/*    */ package cn.rmc.bedwars.runnable.item;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import cn.rmc.bedwars.game.PlayerData;
/*    */ import cn.rmc.bedwars.game.ResourceSpawner;
/*    */ import cn.rmc.bedwars.game.Team;
/*    */ import cn.rmc.bedwars.utils.world.LocationUtil;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.Sound;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.block.CraftBlock;
/*    */ import org.bukkit.entity.Egg;
/*    */ import org.bukkit.scheduler.BukkitRunnable;
/*    */ 
/*    */ public class EggBridgeRunnable
/*    */   extends BukkitRunnable
/*    */ {
/*    */   PlayerData pd;
/*    */   Egg egg;
/*    */   Location pl;
/*    */   
/*    */   public EggBridgeRunnable(PlayerData pd, Egg e) {
/* 24 */     this.pd = pd;
/* 25 */     this.egg = e;
/* 26 */     this.pl = pd.getPlayer().getLocation();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void run() {
/* 32 */     Location location = this.egg.getLocation();
/* 33 */     if (this.egg.isDead() || this.pl.getY() - this.egg.getLocation().getY() > 18.0D || this.pl.distance(this.egg.getLocation()) >= 45.0D) {
/* 34 */       cancel();
/*    */     }
/* 36 */     else if (this.pl.distance(location) > 3.0D) {
/* 37 */       Block block = location.clone().subtract(0.0D, 2.0D, 0.0D).getBlock();
/* 38 */       Block block2 = location.clone().subtract(1.0D, 2.0D, 0.0D).getBlock();
/* 39 */       Block block3 = location.clone().subtract(0.0D, 2.0D, 1.0D).getBlock();
/* 40 */       for (Block blocks : Arrays.<Block>asList(new Block[] { block, block2, block3 })) {
/* 41 */         boolean hasRegion = false;
/*    */         
/* 43 */         for (Team team : this.pd.getGame().getTeams()) {
/* 44 */           if (LocationUtil.hasRegion(this.pl, team.getPos1(), team.getPos2())) {
/* 45 */             hasRegion = true;
/*    */             
/*    */             break;
/*    */           } 
/*    */         } 
/* 50 */         for (ResourceSpawner spawner : this.pd.getGame().getSpawners()) {
/* 51 */           if (spawner.getLoc().distance(this.pl) <= 2.0D) {
/* 52 */             hasRegion = true;
/*    */             break;
/*    */           } 
/*    */         } 
/* 56 */         if (blocks.getType() == Material.AIR && !hasRegion) {
/* 57 */           blocks.setType(Material.WOOL);
/* 58 */           ((CraftBlock)blocks).setData(this.pd.getTeam().getTeamType().getDyeColor().getData());
/* 59 */           this.pd.getGame().addBlock(blocks);
/*    */         } 
/* 61 */         blocks.getLocation().getWorld().playSound(blocks.getLocation(), Sound.CHICKEN_EGG_POP, 1.0F, 1.0F);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }