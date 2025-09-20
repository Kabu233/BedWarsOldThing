/*    */ package cn.rmc.bedwars.game.dream.armed.guns;
/*    */ 
/*    */ import cn.rmc.bedwars.game.PlayerData;
/*    */ import cn.rmc.bedwars.game.dream.armed.GunBasic;
/*    */ import cn.rmc.bedwars.game.dream.armed.GunType;
/*    */ import cn.rmc.bedwars.utils.world.ParticleEffects;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.Sound;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.util.Vector;
/*    */ 
/*    */ public class NotaFlamethrower
/*    */   extends GunBasic {
/*    */   public NotaFlamethrower() {
/* 15 */     super(GunType.NOTAFLAMETHROWER);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void playEffect(Location location) {
/* 21 */     ParticleEffects.FLAME.display(new Vector(), 0.0F, location, 50.0D);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void playSound(Player player) {
/* 27 */     player.getLocation().getWorld().playSound(player.getLocation(), Sound.FIRE, 1.0F, 2.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onHit(PlayerData pd) {
/* 32 */     pd.getPlayer().setFireTicks(100);
/*    */   }
/*    */ }