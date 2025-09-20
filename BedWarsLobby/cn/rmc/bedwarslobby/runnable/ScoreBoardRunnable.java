/*    */ package cn.rmc.bedwarslobby.runnable;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import cn.rmc.bedwarslobby.object.Board;
/*    */ import cn.rmc.bedwarslobby.object.PlayerData;
/*    */ import org.bukkit.scheduler.BukkitRunnable;
/*    */ 
/*    */ public class ScoreBoardRunnable
/*    */   extends BukkitRunnable
/*    */ {
/*    */   PlayerData f15pd;
/*    */   String title;
/*    */   String[] strings;
/*    */   
/*    */   public ScoreBoardRunnable(PlayerData pd, String title, String[] strings) {
/* 16 */     this.f15pd = pd;
/* 17 */     this.title = title;
/* 18 */     this.strings = strings;
/*    */   }
/*    */   
/*    */   public void run() {
/* 22 */     if (this.f15pd.getBoard() == null && this.f15pd.getPlayer() != null) {
/* 23 */       this.f15pd.setBoard(new Board(this.f15pd.getPlayer()));
/*    */     }
/* 25 */     this.f15pd.getBoard().send(this.title, Arrays.asList(this.strings));
/*    */   }
/*    */ }