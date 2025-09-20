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
/*    */ public class Pistol
/*    */   extends GunBasic
/*    */ {
/*    */   public Pistol() {
/* 15 */     super(GunType.PISTOL);
/*    */   }
/*    */ 
/*    */   
/*    */   public void playEffect(Location location) {
/* 20 */     ParticleEffects.CRIT.display(new Vector(), 0.0F, location, 50.0D);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void playSound(Player player) {
/* 27 */     player.getLocation().getWorld().playSound(player.getLocation(), Sound.IRONGOLEM_HIT, 1.0F, 2.0F);
/*    */   }
/*    */ }