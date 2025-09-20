/*    */ package cn.rmc.bedwars.event;
/*    */ 
/*    */ import cn.rmc.bedwars.game.Game;
/*    */ import cn.rmc.bedwars.game.PlayerData;
/*    */ import cn.rmc.bedwars.game.Team;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ public class BedBreakEvent extends Event {
/*    */   PlayerData breaker;
/*    */   Team team;
/*    */   Game game;
/* 13 */   private static final HandlerList handlers = new HandlerList(); public PlayerData getBreaker() {
/* 14 */     return this.breaker;
/*    */   } public Team getTeam() {
/* 16 */     return this.team;
/*    */   } public Game getGame() {
/* 18 */     return this.game;
/*    */   }
/*    */   
/*    */   public BedBreakEvent(PlayerData pd, Team team) {
/* 22 */     this.breaker = pd;
/* 23 */     this.team = team;
/* 24 */     this.game = team.getGame();
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 28 */     return handlers;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 33 */     return handlers;
/*    */   }
/*    */ }