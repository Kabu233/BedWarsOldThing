/*    */ package cn.rmc.bedwars.runnable;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import cn.rmc.bedwars.game.Board;
/*    */ import cn.rmc.bedwars.game.PlayerData;
/*    */ import org.bukkit.scheduler.BukkitRunnable;
/*    */ 
/*    */ public class ScoreBoardRunnable
/*    */   extends BukkitRunnable {
/*    */   PlayerData pd;
/*    */   String title;
/*    */   String[] strings;
/*    */   
/*    */   public ScoreBoardRunnable(PlayerData pd, String title, String[] strings) {
/* 15 */     this.pd = pd;
/* 16 */     this.title = title;
/* 17 */     this.strings = strings;
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/* 22 */     if (this.pd.getScoreBoard() == null) this.pd.setScoreBoard(new Board(this.pd.getPlayer())); 
/* 23 */     this.pd.getScoreBoard().send(this.title, Arrays.asList(this.strings));
/*    */   }
/*    */ }