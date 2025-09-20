/*    */ package cn.rmc.bedwars.task;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import cn.rmc.bedwars.BedWars;
/*    */ import cn.rmc.bedwars.enums.game.GameState;
/*    */ import cn.rmc.bedwars.enums.game.PlayerState;
/*    */ import cn.rmc.bedwars.enums.shop.TeamUpgrade;
/*    */ import cn.rmc.bedwars.game.Game;
/*    */ import cn.rmc.bedwars.game.PlayerData;
/*    */ import cn.rmc.bedwars.game.Team;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.potion.PotionEffect;
/*    */ import org.bukkit.potion.PotionEffectType;
/*    */ import org.bukkit.scheduler.BukkitRunnable;
/*    */ 
/*    */ public class LocDetectTask
/*    */   extends BukkitRunnable {
/*    */   Game g;
/* 20 */   public HashMap<PlayerData, HashMap<Team, Long>> data = new HashMap<>();
/*    */   public LocDetectTask(Game g) {
/* 22 */     this.g = g;
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/* 27 */     if (this.g.getState() == GameState.ENDING) {
/* 28 */       cancel();
/*    */     }
/* 30 */     for (PlayerData player : this.g.getOnlinePlayers()) {
/* 31 */       if (player.getState() != PlayerState.FIGHTING)
/* 32 */         continue;  for (Team team : this.g.getTeams()) {
/* 33 */         if (team != player.getTeam() && 
/* 34 */           player.getPlayer().getLocation().distance(team.getSpawnloc()) <= 20.0D) {
/* 35 */           if (!this.data.containsKey(player)) this.data.put(player, new HashMap<>()); 
/* 36 */           if ((!((HashMap)this.data.get(player)).containsKey(team) || (((HashMap)this.data
/* 37 */             .get(player)).containsKey(team) && System.currentTimeMillis() - ((Long)((HashMap)this.data.get(player)).get(team)).longValue() >= 60000L)) && (
/* 38 */             player.getMilktime() == null || System.currentTimeMillis() - player.getMilktime().longValue() >= 60000L)) {
/* 39 */             boolean b = team.toTrap(player).booleanValue();
/* 40 */             if (b) {
/* 41 */               ((HashMap<Team, Long>)this.data.get(player)).put(team, Long.valueOf(System.currentTimeMillis()));
/*    */             }
/*    */           } 
/*    */         } 
/*    */       } 
/* 46 */       if (((Integer)player.getTeam().getTeamUpgrade().get(TeamUpgrade.HealPool)).intValue() >= 1) {
/* 47 */         if (player.getPlayer().getLocation().distance(player.getTeam().getSpawnloc()) <= 20.0D) {
/* 48 */           Bukkit.getScheduler().runTask((Plugin)BedWars.getInstance(), () -> player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 99999, 0, false, false)));
/*    */           
/*    */           continue;
/*    */         } 
/* 52 */         for (PotionEffect effect : player.getPlayer().getActivePotionEffects()) {
/* 53 */           if (effect.getType().equals(PotionEffectType.REGENERATION)) {
/* 54 */             System.out.println(effect.getAmplifier());
/* 55 */             Bukkit.getScheduler().runTask((Plugin)BedWars.getInstance(), () -> player.getPlayer().removePotionEffect(PotionEffectType.REGENERATION));
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }