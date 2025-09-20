/*    */ package cn.rmc.bedwars.event;
/*    */ 
/*    */ import cn.rmc.bedwars.game.Game;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GameStartEvent
/*    */   extends Event
/*    */ {
/*    */   private Game game;
/*    */   
/*    */   public Game getGame() {
/* 15 */     return this.game;
/*    */   }
/*    */   
/*    */   public GameStartEvent(Game game) {
/* 19 */     this.game = game;
/*    */   }
/*    */   
/* 22 */   private static final HandlerList handlers = new HandlerList();
/*    */ 
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 26 */     return handlers;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 31 */     return handlers;
/*    */   }
/*    */ }