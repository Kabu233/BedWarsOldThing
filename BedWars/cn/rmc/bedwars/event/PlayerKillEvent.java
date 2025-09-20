/*    */ package cn.rmc.bedwars.event;
/*    */ import java.util.ArrayList;
/*    */ import cn.rmc.bedwars.enums.game.DeathCause;
/*    */ import cn.rmc.bedwars.game.Game;
/*    */ import cn.rmc.bedwars.game.PlayerData;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ public class PlayerKillEvent extends Event {
/*    */   private final Game game;
/*    */   private final PlayerData killer;
/*    */   private final PlayerData deather;
/*    */   private final ArrayList<PlayerData> assisters;
/*    */   private final DeathCause cause;
/* 14 */   private static final HandlerList handlers = new HandlerList(); private final Boolean isFinal; public Game getGame() {
/* 15 */     return this.game;
/*    */   } public PlayerData getKiller() {
/* 17 */     return this.killer;
/*    */   } public PlayerData getDeather() {
/* 19 */     return this.deather;
/*    */   } public ArrayList<PlayerData> getAssisters() {
/* 21 */     return this.assisters;
/*    */   } public DeathCause getCause() {
/* 23 */     return this.cause;
/*    */   }
/*    */   public Boolean getIsFinal() {
/* 26 */     return this.isFinal;
/*    */   }
/* 28 */   public Boolean getDrop() { return this.drop; } public void setDrop(Boolean drop) {
/* 29 */     this.drop = drop;
/* 30 */   } private Boolean drop = Boolean.valueOf(true);
/*    */   
/*    */   public PlayerKillEvent(PlayerData killer, PlayerData deather, ArrayList<PlayerData> assisters, DeathCause cause, Boolean isFinal) {
/* 33 */     this.game = deather.getGame();
/* 34 */     this.killer = killer;
/* 35 */     this.deather = deather;
/* 36 */     this.assisters = assisters;
/* 37 */     this.cause = cause;
/* 38 */     this.isFinal = isFinal;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 43 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 47 */     return handlers;
/*    */   }
/*    */ }