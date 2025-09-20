/*    */ package cn.rmc.bedwars.task;
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import cn.rmc.bedwars.BedWars;
/*    */ import cn.rmc.bedwars.enums.game.GameState;
/*    */ import cn.rmc.bedwars.enums.game.PlayerState;
/*    */ import cn.rmc.bedwars.game.Game;
/*    */ import cn.rmc.bedwars.game.PlayerData;
/*    */ import cn.rmc.bedwars.utils.player.ActionBarUtils;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.scheduler.BukkitRunnable;
/*    */ 
/*    */ public class TimerTask extends BukkitRunnable {
/* 15 */   HashMap<PlayerData, Integer> timer = new HashMap<>(); Game g;
/*    */   
/*    */   public TimerTask(Game g) {
/* 18 */     this.g = g;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void run() {
/* 30 */     if (this.g.getState() == GameState.ENDING) cancel(); 
/* 31 */     for (Iterator<PlayerData> iterator = this.g.getOnlinePlayers().iterator(); iterator.hasNext(); ) { PlayerData pd = iterator.next();
/* 32 */       if (pd.getLastshout() != null && pd.getLastshout().intValue() >= -5) {
/* 33 */         pd.setLastshout(Integer.valueOf(pd.getLastshout().intValue() - 1));
/*    */       }
/* 35 */       if (pd.getState() == PlayerState.FIGHTING) {
/* 36 */         if (this.timer.containsKey(pd) && ((Integer)this.timer.get(pd)).intValue() >= 60) {
/* 37 */           Bukkit.getScheduler().runTask((Plugin)BedWars.getInstance(), () -> {
/*    */                 pd.getPlayer().sendMessage("§b+25经验! (时长奖励)");
/*    */                 pd.getPlayer().sendMessage("§6+20硬币! (时长奖励)");
/*    */                 ActionBarUtils.sendActionBar(pd.getPlayer(), "§6+20硬币! §b+25经验!");
/*    */                 pd.setExpenience(pd.getExpenience() + 25);
/*    */                 pd.setCoins(pd.getCoins() + 20);
/*    */                 this.timer.remove(pd);
/*    */               });
/*    */         }
/* 46 */         if (this.timer.containsKey(pd)) {
/* 47 */           this.timer.put(pd, Integer.valueOf(((Integer)this.timer.get(pd)).intValue() + 1)); continue;
/*    */         } 
/* 49 */         this.timer.put(pd, Integer.valueOf(1));
/*    */       }  }
/*    */   
/*    */   }
/*    */ }