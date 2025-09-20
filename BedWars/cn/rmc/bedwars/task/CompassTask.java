/*    */ package cn.rmc.bedwars.task;
/*    */ 
/*    */ import cn.rmc.bedwars.enums.game.PlayerState;
/*    */ import cn.rmc.bedwars.game.Game;
/*    */ import cn.rmc.bedwars.game.PlayerData;
/*    */ import cn.rmc.bedwars.game.Team;
/*    */ import cn.rmc.bedwars.utils.player.ActionBarUtils;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.scheduler.BukkitRunnable;
/*    */ 
/*    */ public class CompassTask
/*    */   extends BukkitRunnable {
/*    */   Game g;
/*    */   
/*    */   public CompassTask(Game g) {
/* 16 */     this.g = g;
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/* 21 */     for (PlayerData player : this.g.getOnlinePlayers()) {
/* 22 */       if (player.getState() != PlayerState.FIGHTING || 
/* 23 */         player.getTargetTeam() == null)
/* 24 */         continue;  Team team = player.getTargetTeam();
/* 25 */       PlayerData pd = null;
/* 26 */       Location loc = player.getPlayer().getLocation();
/* 27 */       double maxdistance = Double.MAX_VALUE;
/* 28 */       for (PlayerData playerData : team.getAlivePlayers()) {
/* 29 */         double distance = loc.distance(playerData.getPlayer().getLocation());
/* 30 */         if (distance < maxdistance) {
/* 31 */           pd = playerData;
/* 32 */           maxdistance = distance;
/*    */         } 
/*    */       } 
/* 35 */       if (pd == null) {
/* 36 */         ActionBarUtils.sendActionBar(player.getPlayer(), "§c§l该队没有玩家存活!");
/* 37 */         player.getPlayer().setCompassTarget(player.getTeam().getSpawnloc()); continue;
/*    */       } 
/* 39 */       String name = pd.getTeam().getTeamType().getChatColor() + pd.getPlayer().getName();
/* 40 */       ActionBarUtils.sendActionBar(player.getPlayer(), "§f目标: " + name + " §f- 距离: §a" + String.format("%.1f", new Object[] { Double.valueOf(maxdistance) }) + "m");
/* 41 */       player.getPlayer().setCompassTarget(pd.getPlayer().getLocation());
/*    */     } 
/*    */   }
/*    */ }