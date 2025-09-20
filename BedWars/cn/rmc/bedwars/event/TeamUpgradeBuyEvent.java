/*    */ package cn.rmc.bedwars.event;
/*    */ 
/*    */ import cn.rmc.bedwars.enums.ITeamUpgrade;
/*    */ import cn.rmc.bedwars.game.Team;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ public class TeamUpgradeBuyEvent extends Event {
/*    */   private final Team team;
/*    */   
/*    */   public Team getTeam() {
/* 12 */     return this.team;
/*    */   } private final ITeamUpgrade type; private final Integer boughtLevel; public ITeamUpgrade getType() {
/* 14 */     return this.type;
/*    */   } public Integer getBoughtLevel() {
/* 16 */     return this.boughtLevel;
/*    */   }
/*    */   
/*    */   public TeamUpgradeBuyEvent(Team team, ITeamUpgrade type, Integer boughtLevel) {
/* 20 */     this.team = team;
/* 21 */     this.type = type;
/* 22 */     this.boughtLevel = boughtLevel;
/*    */   }
/*    */   
/* 25 */   private static final HandlerList handlers = new HandlerList();
/*    */   
/*    */   public HandlerList getHandlers() {
/* 28 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 32 */     return handlers;
/*    */   }
/*    */ }