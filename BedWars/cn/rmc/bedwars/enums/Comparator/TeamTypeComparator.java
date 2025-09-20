/*    */ package cn.rmc.bedwars.enums.Comparator;
/*    */ 
/*    */ import java.util.Comparator;
/*    */ import cn.rmc.bedwars.enums.game.TeamType;
/*    */ 
/*    */ public class TeamTypeComparator
/*    */   implements Comparator<TeamType>
/*    */ {
/*    */   public int compare(TeamType o1, TeamType o2) {
/* 10 */     if (o1 == null || o2 == null)
/* 11 */       return 0; 
/* 12 */     if (TeamType.valueswithList().indexOf(o1) > TeamType.valueswithList().indexOf(o2)) {
/* 13 */       return 1;
/*    */     }
/* 15 */     return -1;
/*    */   }
/*    */ }