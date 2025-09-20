/*    */ package cn.rmc.bedwars.task;
/*    */ 
/*    */ import cn.rmc.bedwars.BedWars;
/*    */ import cn.rmc.bedwars.enums.game.GameState;
/*    */ import cn.rmc.bedwars.game.Game;
/*    */ import cn.rmc.bedwars.game.PlayerData;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.scheduler.BukkitRunnable;
/*    */ import org.bukkit.scoreboard.DisplaySlot;
/*    */ import org.bukkit.scoreboard.Objective;
/*    */ import org.bukkit.scoreboard.Scoreboard;
/*    */ 
/*    */ public class HealthRefreshTask extends BukkitRunnable {
/*    */   public HealthRefreshTask(Game g) {
/* 17 */     this.g = g;
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/* 22 */     if (this.g.getState() == GameState.ENDING) {
/* 23 */       cancel();
/*    */     }
/* 25 */     Bukkit.getScheduler().runTask((Plugin)BedWars.getInstance(), () -> this.g.getOnlinePlayers().forEach(()));
/*    */   }
/*    */   
/*    */   Game g;
/*    */ }