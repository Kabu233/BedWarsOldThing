/*    */ package cn.rmc.bedwars.game.dream.armed.guns;
/*    */ 
/*    */ import cn.rmc.bedwars.game.dream.armed.GunBasic;
/*    */ import cn.rmc.bedwars.game.dream.armed.GunType;
/*    */ import cn.rmc.bedwars.utils.world.ParticleEffects;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.Sound;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.util.Vector;
/*    */ 
/*    */ public class SMG extends GunBasic {
/*    */   public SMG() {
/* 13 */     super(GunType.SMG);
/*    */   }
/*    */ 
/*    */   
/*    */   public void playEffect(Location location) {
/* 18 */     ParticleEffects.FIREWORKS_SPARK.display(new Vector(), 0.0F, location, 50.0D);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void playSound(Player player) {
/* 25 */     player.getLocation().getWorld().playSound(player.getLocation(), Sound.FIREWORK_BLAST2, 1.0F, 2.0F);
/*    */   }
/*    */ }