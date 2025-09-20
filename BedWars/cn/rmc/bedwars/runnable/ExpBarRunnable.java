/*    */ package cn.rmc.bedwars.runnable;
/*    */ 
/*    */ import cn.rmc.bedwars.BedWars;
/*    */ import cn.rmc.bedwars.event.PlayerKillEvent;
/*    */ import cn.rmc.bedwars.game.PlayerData;
/*    */ import cn.rmc.bedwars.game.dream.ultimate.UltimateBasic;
/*    */ import cn.rmc.bedwars.game.dream.ultimate.UltimateManager;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.HandlerList;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.scheduler.BukkitRunnable;
/*    */ 
/*    */ public class ExpBarRunnable extends BukkitRunnable implements Listener {
/*    */   PlayerData pd;
/*    */   UltimateBasic ultimateBasic;
/*    */   Long time;
/*    */   Long last;
/*    */   
/*    */   public ExpBarRunnable() {}
/*    */   
/*    */   public ExpBarRunnable(UltimateBasic basic, Long time) {
/* 25 */     Bukkit.getPluginManager().registerEvents(this, (Plugin)BedWars.getInstance());
/* 26 */     this.ultimateBasic = basic;
/* 27 */     this.pd = basic.getPd();
/* 28 */     this.time = Long.valueOf(System.currentTimeMillis() + time.longValue());
/* 29 */     this.last = time;
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/* 34 */     Player player = this.pd.getPlayer();
/* 35 */     if (player != null && player.isOnline()) {
/* 36 */       long time = this.time.longValue() - System.currentTimeMillis();
/* 37 */       int seconds = (int)Math.round(time / 1000.0D);
/* 38 */       player.setLevel(seconds);
/* 39 */       player.setExp((float)time / (float)this.last.longValue());
/* 40 */       if (time <= 0L) {
/* 41 */         cancel();
/*    */       }
/*    */     } else {
/* 44 */       cancel();
/*    */     } 
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onDeath(PlayerKillEvent e) {
/* 50 */     if (e.getDeather().equals(this.pd)) {
/* 51 */       cancel();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public synchronized void cancel() throws IllegalStateException {
/* 57 */     this.ultimateBasic.exptask = null;
/* 58 */     if (((UltimateManager)this.pd.getGame().getDreamManager()).getUltimates().containsKey(this.pd))
/* 59 */       ((UltimateBasic)((UltimateManager)this.pd.getGame().getDreamManager()).getUltimates().get(this.pd)).onTimerDone(); 
/* 60 */     HandlerList.unregisterAll(this);
/* 61 */     super.cancel();
/*    */   }
/*    */ }