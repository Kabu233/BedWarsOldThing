/*    */ package cn.rmc.bedwars.game.dream.armed.guns;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ import cn.rmc.bedwars.game.dream.armed.GunBasic;
/*    */ import cn.rmc.bedwars.game.dream.armed.GunType;
/*    */ import cn.rmc.bedwars.utils.world.ParticleEffects;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.Sound;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.util.Vector;
/*    */ 
/*    */ public class Shotgun
/*    */   extends GunBasic
/*    */ {
/*    */   public Shotgun() {
/* 18 */     super(GunType.SHOTGUN);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void playEffect(Location location) {
/* 24 */     ParticleEffects.SMOKE_LARGE.display(new Vector(), 0.0F, location, 50.0D);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public List<Vector> getVectors(Player player) {
/* 30 */     Location location = player.getLocation();
/* 31 */     List<Vector> vectors = new ArrayList<>();
/* 32 */     for (int i = 0; i < 8; i++) {
/* 33 */       Random r = new Random();
/* 34 */       double yaw = Math.toRadians((-location.getYaw() - 90.0F));
/* 35 */       double pitch = Math.toRadians(-location.getPitch());
/* 36 */       double[] spread = { 1.0D, 1.0D, 1.0D };
/*    */       
/* 38 */       for (int t = 0; t < 3; t++) {
/* 39 */         spread[t] = (r.nextDouble() - r.nextDouble()) * 2.0D * 0.1D;
/*    */       }
/*    */       
/* 42 */       double x = Math.cos(pitch) * Math.cos(yaw) + spread[0];
/* 43 */       double y = Math.sin(pitch) + spread[1];
/* 44 */       double z = -Math.sin(yaw) * Math.cos(pitch) + spread[2];
/* 45 */       Vector dirVel = new Vector(x, y, z);
/* 46 */       vectors.add(dirVel);
/*    */     } 
/* 48 */     return vectors;
/*    */   }
/*    */ 
/*    */   
/*    */   public void playSound(Player player) {
/* 53 */     player.getLocation().getWorld().playSound(player.getLocation(), Sound.EXPLODE, 1.0F, 2.0F);
/*    */   }
/*    */ }