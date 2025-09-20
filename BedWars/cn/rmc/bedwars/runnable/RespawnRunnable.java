/*    */ package cn.rmc.bedwars.runnable;
/*    */ 
/*    */ import cn.rmc.bedwars.BedWars;
/*    */ import cn.rmc.bedwars.game.PlayerData;
/*    */ import cn.rmc.bedwars.utils.player.TitleUtils;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.Sound;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.scheduler.BukkitRunnable;
/*    */ 
/*    */ public class RespawnRunnable
/*    */   extends BukkitRunnable {
/*    */   PlayerData pd;
/* 14 */   int i = 5;
/*    */   public RespawnRunnable(PlayerData pd) {
/* 16 */     this.pd = pd;
/* 17 */     int i = 0;
/* 18 */     for (float f = 1.7F; f < 2.0F; f = (float)(f + 0.1D)) {
/* 19 */       float finalf = f;
/* 20 */       Bukkit.getScheduler().runTaskLater((Plugin)BedWars.getInstance(), () -> pd.getPlayer().playSound(pd.getPlayer().getLocation(), Sound.LEVEL_UP, 1.0F, finalf), (i * 2));
/*    */ 
/*    */       
/* 23 */       i++;
/*    */     } 
/* 25 */     int finali = i;
/* 26 */     for (; i < finali + 7; i++) {
/* 27 */       Bukkit.getScheduler().runTaskLater((Plugin)BedWars.getInstance(), () -> pd.getPlayer().playSound(pd.getPlayer().getLocation(), Sound.LEVEL_UP, 1.0F, 2.0F), (i * 2));
/*    */     }
/*    */ 
/*    */     
/* 31 */     pd.getPlayer().getInventory().clear();
/* 32 */     pd.getPlayer().setItemOnCursor(null);
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/* 37 */     if (!this.pd.getPlayer().isOnline()) {
/* 38 */       cancel();
/*    */     }
/* 40 */     if (this.i > 0) {
/* 41 */       TitleUtils.sendFullTitle(this.pd.getPlayer(), Integer.valueOf(0), Integer.valueOf(60), Integer.valueOf(0), "§c你死了!", "§e你将在§c" + this.i + "§e秒后重生!");
/* 42 */       this.pd.getPlayer().sendMessage("§e你将在§c" + this.i + "§e秒后重生!");
/*    */     } 
/* 44 */     if (this.i <= 0) {
/* 45 */       TitleUtils.sendFullTitle(this.pd.getPlayer(), Integer.valueOf(5), Integer.valueOf(60), Integer.valueOf(5), "§a已重生!", "");
/* 46 */       this.pd.getPlayer().sendMessage("§e你已经重生!");
/* 47 */       this.pd.getPlayer().setHealth(20.0D);
/* 48 */       Bukkit.getScheduler().runTask((Plugin)BedWars.getInstance(), this.pd::spawn);
/* 49 */       this.pd.getGame().getSpecs().remove(this.pd);
/* 50 */       cancel();
/*    */     } 
/* 52 */     this.i--;
/*    */   }
/*    */ }