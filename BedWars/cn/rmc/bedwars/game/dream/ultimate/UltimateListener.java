/*    */ package cn.rmc.bedwars.game.dream.ultimate;
/*    */ 
/*    */ import cn.rmc.bedwars.BedWars;
/*    */ import cn.rmc.bedwars.enums.game.TeamState;
/*    */ import cn.rmc.bedwars.enums.game.TeamType;
/*    */ import cn.rmc.bedwars.event.PlayerKillEvent;
/*    */ import cn.rmc.bedwars.game.Game;
/*    */ import cn.rmc.bedwars.game.PlayerData;
/*    */ import cn.rmc.bedwars.manager.PlayerManager;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.EventPriority;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.entity.EntityDamageEvent;
/*    */ import org.bukkit.event.player.PlayerJoinEvent;
/*    */ import org.bukkit.event.player.PlayerQuitEvent;
/*    */ 
/*    */ public class UltimateListener implements Listener {
/*    */   UltimateManager manager;
/*    */   
/*    */   public UltimateListener(UltimateManager ultimateManager) {
/* 22 */     this.manager = ultimateManager;
/*    */   }
/*    */   
/*    */   @EventHandler(priority = EventPriority.LOWEST)
/*    */   public void PlayerJoin(PlayerJoinEvent e) {
/* 27 */     PlayerManager pm = BedWars.getInstance().getPlayerManager();
/* 28 */     PlayerData pd = pm.get(e.getPlayer());
/* 29 */     if (pd == null)
/* 30 */       return;  Game g = pd.getGame();
/* 31 */     if (g != null && 
/* 32 */       pd.getTeam().getState() == TeamState.ALIVE && pd.getTeam().getTeamType() != TeamType.NON) {
/* 33 */       this.manager.initP(pd);
/*    */     }
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onFD(PlayerKillEvent e) {
/* 39 */     if (this.manager.ultimates.containsKey(e.getDeather()) && 
/* 40 */       e.getIsFinal().booleanValue()) {
/* 41 */       this.manager.delete(e.getDeather());
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   @EventHandler
/*    */   public void PlayerLeave(PlayerQuitEvent e) {
/* 48 */     Player p = e.getPlayer();
/*    */     
/* 50 */     PlayerData pd = BedWars.getInstance().getPlayerManager().get(p);
/* 51 */     if (pd.getGame() != null) {
/* 52 */       this.manager.delete(pd);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   @EventHandler
/*    */   public void onDamage(EntityDamageEvent e) {
/* 59 */     if (e.getCause() == EntityDamageEvent.DamageCause.FALL) e.setCancelled(true); 
/*    */   }
/*    */ }