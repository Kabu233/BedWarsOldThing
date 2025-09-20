/*    */ package cn.rmc.bedwarslobby.manager;
/*    */ 
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ import java.util.List;
/*    */ import cn.rmc.bedwarslobby.BedWarsLobby;
/*    */ import cn.rmc.bedwarslobby.enums.Data;
/*    */ import cn.rmc.bedwarslobby.util.DataUtils;
/*    */ import cn.rmc.bedwarslobby.util.LevelUtils;
/*    */ import cn.rmc.bedwarslobby.util.MathUtils;
/*    */ import cn.rmc.bedwarslobby.util.ScoreBoardUtils;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.scheduler.BukkitRunnable;
/*    */ 
/*    */ public class ScoreBoardManager {
/* 17 */   List<String> titles = MathUtils.getTitles("起床战争");
/*    */   
/*    */   public void sendScoreBoard(final Player p) {
/* 20 */     (new BukkitRunnable() {
/* 21 */         int i = 0;
/*    */         
/*    */         public void run() {
/*    */           try {
/* 25 */             if (!p.isOnline()) {
/* 26 */               cancel();
/*    */               return;
/*    */             } 
/* 29 */             if (this.i == ScoreBoardManager.this.titles.size()) {
/* 30 */               this.i = 0;
/*    */             }
/* 32 */             SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd");
/* 33 */             String format = simpleDateFormat.format(new Date());
/* 34 */             Integer kills = Integer.valueOf(DataUtils.getInt(p.getUniqueId().toString(), Data.Field.KILL, "TOTAL").intValue() + DataUtils.getInt(p.getUniqueId().toString(), Data.Field.FINALKILL, "TOTAL").intValue());
/* 35 */             Integer wins = DataUtils.getInt(p.getUniqueId().toString(), Data.Field.WIN, "TOTAL");
/* 36 */             int exp = DataUtils.getInt(p.getUniqueId().toString(), Data.Field.EXPERIENCE).intValue();
/* 37 */             Integer lv = LevelUtils.getLevel(exp);
/* 38 */             String[] progress = LevelUtils.getLevelProgress(exp);
/* 39 */             (new ScoreBoardUtils()).SidebarDisplay(p, ScoreBoardManager.this.titles.get(this.i), new String[] { "§7" + format + "  §8BL1", "§r", "等级: " + 
/*    */                   
/* 41 */                   LevelUtils.getLevelwithColorwithoutBracketByLevel(lv.intValue()), "§r§r", "进度: " + progress[0], "§8[" + progress[1] + "§8]", "§r§r§r", "奖励箱: §6" + 
/*    */ 
/*    */ 
/*    */                   
/* 45 */                   MathUtils.Format(BedWarsLobby.getInstance().getPlayerManager().get(this.val$p).getLootChest()), "§r§r§r§r", "硬币: §6" + 
/*    */                   
/* 47 */                   MathUtils.Format(DataUtils.getInt(this.val$p.getUniqueId().toString(), Data.Field.COIN)), "§r§r§r§r§r", "总击杀数: §a" + 
/*    */                   
/* 49 */                   MathUtils.Format(kills), "总胜场: §a" + 
/* 50 */                   MathUtils.Format(wins), "§r§r§r§r§r§r", "§eMc91.top" });
/*    */ 
/*    */ 
/*    */             
/* 54 */             this.i++;
/* 55 */           } catch (Exception e) {
/* 56 */             cancel();
/*    */           } 
/*    */         }
/* 59 */       }).runTaskTimer((Plugin)BedWarsLobby.getInstance(), 0L, 2L);
/*    */   }
/*    */ }