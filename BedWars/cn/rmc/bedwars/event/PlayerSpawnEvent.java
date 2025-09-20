/*    */ package cn.rmc.bedwars.event;
/*    */ import cn.rmc.bedwars.game.Game;
/*    */ import cn.rmc.bedwars.game.PlayerData;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ public class PlayerSpawnEvent extends Event {
/*    */   private final Game game;
/*    */   private final PlayerData player;
/* 10 */   private static final HandlerList handlers = new HandlerList(); public Game getGame() {
/* 11 */     return this.game;
/*    */   } public PlayerData getPlayer() {
/* 13 */     return this.player;
/*    */   }
/*    */   
/*    */   public PlayerSpawnEvent(PlayerData player) {
/* 17 */     this.game = player.getGame();
/* 18 */     this.player = player;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 22 */     return handlers;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 27 */     return handlers;
/*    */   }
/*    */ }