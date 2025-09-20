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
/*    */ public class Magnum
/*    */   extends GunBasic {
/*    */   public Magnum() {
/* 14 */     super(GunType.MAGNUM);
/*    */   }
/*    */ 
/*    */   
/*    */   public void playEffect(Location location) {
/* 19 */     ParticleEffects.CRIT_MAGIC.display(new Vector(), 0.0F, location, 50.0D);
/*    */   }
/*    */ 
/*    */   
/*    */   public void playSound(Player player) {
/* 24 */     player.getLocation().getWorld().playSound(player.getLocation(), Sound.FIREWORK_LARGE_BLAST2, 1.0F, 1.0F);
/*    */   }
/*    */ }