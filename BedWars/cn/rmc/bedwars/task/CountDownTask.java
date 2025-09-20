/*    */ package cn.rmc.bedwars.task;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import cn.rmc.bedwars.BedWars;
/*    */ import cn.rmc.bedwars.game.Game;
/*    */ import cn.rmc.bedwars.game.PlayerData;
/*    */ import cn.rmc.bedwars.utils.player.TitleUtils;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.Sound;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.scheduler.BukkitRunnable;
/*    */ 
/*    */ public class CountDownTask extends BukkitRunnable {
/*    */   Game game;
/* 16 */   int i = 120; public void setI(int i) { this.i = i; }
/*    */   
/* 18 */   List<Integer> tip = Arrays.asList(new Integer[] { Integer.valueOf(30), Integer.valueOf(15), Integer.valueOf(10), Integer.valueOf(5), Integer.valueOf(4), Integer.valueOf(3), Integer.valueOf(2), Integer.valueOf(1) });
/*    */   
/*    */   public CountDownTask(Game g) {
/* 21 */     this.game = g;
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/* 26 */     this.game.setCount(this.i);
/* 27 */     BedWars.getInstance().getScoreBoardManager().updateAll();
/* 28 */     if (!this.game.isPrivateGame()) {
/* 29 */       if (45 < this.i && this.game.getOnlinePlayers().size() >= this.game.getMaxplayer() / 2) {
/* 30 */         this.i = 45;
/*    */       }
/* 32 */       if (25 < this.i && this.game.getOnlinePlayers().size() >= this.game.getMaxplayer() * 3 / 4) {
/* 33 */         this.i = 25;
/*    */       }
/*    */     } 
/* 36 */     if (this.i > 0) {
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 52 */       this.game.getPlayers().forEach(playerData -> {
/*    */             if (this.tip.contains(Integer.valueOf(this.i))) {
/*    */               if (this.i >= 10) {
/*    */                 playerData.getPlayer().sendMessage("§e游戏将在§a" + this.i + "§e秒后开始!");
/*    */                 TitleUtils.sendFullTitle(playerData.getPlayer(), Integer.valueOf(0), Integer.valueOf(60), Integer.valueOf(0), "§a" + this.i, "");
/*    */               } else if (this.i >= 4) {
/*    */                 playerData.getPlayer().sendMessage("§e游戏将在§6" + this.i + "§e秒后开始!");
/*    */                 TitleUtils.sendFullTitle(playerData.getPlayer(), Integer.valueOf(0), Integer.valueOf(60), Integer.valueOf(0), "§6" + this.i, "");
/*    */               } else {
/*    */                 TitleUtils.sendFullTitle(playerData.getPlayer(), Integer.valueOf(0), Integer.valueOf(60), Integer.valueOf(0), "§c" + this.i, "");
/*    */                 playerData.getPlayer().sendMessage("§e游戏将在§c" + this.i + "§e秒后开始!");
/*    */               } 
/*    */               playerData.getPlayer().playSound(playerData.getPlayer().getLocation(), Sound.NOTE_STICKS, 1.0F, 1.0F);
/*    */             } 
/*    */           });
/*    */     } else {
/* 68 */       Bukkit.getScheduler().runTask((Plugin)BedWars.getInstance(), this.game::start);
/* 69 */       cancel();
/*    */     } 
/* 71 */     this.i--;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public synchronized void cancel() throws IllegalStateException {
/* 77 */     this.game.setCountRunnable(null);
/* 78 */     super.cancel();
/*    */   }
/*    */ }