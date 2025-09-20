/*    */ package cn.rmc.bedwarslobby.runnable;
/*    */ 
/*    */ import cn.rmc.bedwarslobby.BedWarsLobby;
/*    */ import cn.rmc.bedwarslobby.loot.LootChestBasic;
/*    */ import cn.rmc.bedwarslobby.loot.utils.CameraUtils;
/*    */ import cn.rmc.bedwarslobby.loot.utils.SitUtils;
/*    */ import cn.rmc.bedwarslobby.util.ActionBarUtils;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.scheduler.BukkitRunnable;
/*    */ import org.bukkit.scheduler.BukkitTask;
/*    */ 
/*    */ 
/*    */ public class StateRunnable
/*    */ {
/* 17 */   BukkitTask task = (new BukkitRunnable() {
/*    */       public void run() {
/* 19 */         if (LootChestBasic.openPlayers.contains(p)) {
/* 20 */           for (Player online : Bukkit.getOnlinePlayers()) {
/* 21 */             online.hidePlayer(p);
/*    */           }
/*    */         } else {
/* 24 */           for (Player online2 : Bukkit.getOnlinePlayers()) {
/* 25 */             online2.showPlayer(p);
/*    */           }
/*    */         }
/*    */       }
/* 32 */     }).runTaskTimer((Plugin)BedWarsLobby.getInstance(), 0L, 1L);
/*    */ 
/*    */   
/*    */   public void cancel() {
/* 36 */     if (this.task != null) {
/* 37 */       this.task.cancel();
/* 38 */       this.task = null;
/*    */     } 
/*    */   }
/*    */   
/*    */   public StateRunnable(final Player p) {}
/*    */ }