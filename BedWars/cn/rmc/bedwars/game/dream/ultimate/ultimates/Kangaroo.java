/*    */ package cn.rmc.bedwars.game.dream.ultimate.ultimates;
/*    */ 
/*    */ import java.util.UUID;
/*    */ import cn.rmc.bedwars.enums.game.PlayerState;
/*    */ import cn.rmc.bedwars.enums.shop.Utility;
/*    */ import cn.rmc.bedwars.event.BedBreakEvent;
/*    */ import cn.rmc.bedwars.event.PlayerKillEvent;
/*    */ import cn.rmc.bedwars.game.dream.ultimate.UltimateBasic;
/*    */ import cn.rmc.bedwars.game.dream.ultimate.UltimateType;
/*    */ import cn.rmc.bedwars.utils.MathUtils;
/*    */ import org.bukkit.GameMode;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.player.PlayerMoveEvent;
/*    */ import org.bukkit.event.player.PlayerToggleFlightEvent;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.util.Vector;
/*    */ 
/*    */ public class Kangaroo
/*    */   extends UltimateBasic {
/*    */   public Kangaroo(UUID uuid) {
/* 22 */     super(uuid, UltimateType.KANGAROO);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack getItem() {
/* 29 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   @EventHandler
/*    */   public void onMo(PlayerMoveEvent e) {
/* 35 */     if (!canGo())
/* 36 */       return;  if (!e.getPlayer().equals(getPlayer()))
/* 37 */       return;  if (getPlayer().getLevel() > 0 && getPd().getState() == PlayerState.FIGHTING) {
/* 38 */       e.getPlayer().setAllowFlight(false);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onMove(PlayerMoveEvent e) {
/* 45 */     Player player = e.getPlayer();
/* 46 */     player.setAllowFlight(false);
/* 47 */     if (!player.isOnGround() && player.getGameMode().equals(GameMode.SURVIVAL) && !player.getAllowFlight()) {
/* 48 */       player.setAllowFlight(true);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onToggleFlight(PlayerToggleFlightEvent e) {
/* 55 */     e.getPlayer().setFlying(false);
/* 56 */     e.getPlayer().setAllowFlight(false);
/* 57 */     e.setCancelled(true);
/* 58 */     Vector v = e.getPlayer().getLocation().getDirection().multiply(2.0D).setY(1.3D);
/* 59 */     e.getPlayer().setVelocity(v);
/*    */     
/* 61 */     initTask();
/*    */   }
/*    */ 
/*    */   
/*    */   public void onBedBreak(BedBreakEvent e) {
/* 66 */     e.getBreaker().getPlayer().getInventory().addItem(new ItemStack[] { Utility.MagicMilk.giveItem(e.getBreaker()) });
/*    */   }
/*    */ 
/*    */   
/*    */   public void dispose() {
/* 71 */     super.dispose();
/* 72 */     if (getPlayer() != null) {
/* 73 */       getPlayer().setAllowFlight(false);
/*    */     }
/*    */   }
/*    */   
/*    */   public void onDeath(PlayerKillEvent e) {
/* 78 */     if (MathUtils.Chance(50).booleanValue())
/* 79 */       e.setDrop(Boolean.valueOf(false)); 
/*    */   }
/*    */ }