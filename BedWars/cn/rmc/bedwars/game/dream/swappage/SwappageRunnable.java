/*    */ package cn.rmc.bedwars.game.dream.swappage;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.stream.Collectors;
/*    */ import cn.rmc.bedwars.enums.game.TeamState;
/*    */ import cn.rmc.bedwars.game.Game;
/*    */ import cn.rmc.bedwars.game.Team;
/*    */ import cn.rmc.bedwars.utils.MathUtils;
/*    */ 
/*    */ 
/*    */ public class SwappageRunnable
/*    */   implements Runnable
/*    */ {
/*    */   Game game;
/*    */   
/*    */   public void run() {
/* 18 */     if (MathUtils.Chance(50).booleanValue())
/* 19 */       return;  List<Team> teams = (List<Team>)(new ArrayList(this.game.getTeams())).stream().filter(team -> (team.getState() == TeamState.ALIVE)).collect(Collectors.toList());
/*    */   }
/*    */ }