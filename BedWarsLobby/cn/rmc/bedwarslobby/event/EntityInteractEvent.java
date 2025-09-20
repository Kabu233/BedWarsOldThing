/*    */ package cn.rmc.bedwarslobby.event;
/*    */ 
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ public class EntityInteractEvent
/*    */   extends Event {
/*  9 */   private static final HandlerList handlers = new HandlerList();
/*    */   private Player player;
/*    */   private Integer entityId;
/*    */   
/*    */   public Player getPlayer() {
/* 14 */     return this.player;
/*    */   }
/*    */   
/*    */   public Integer getEntityId() {
/* 18 */     return this.entityId;
/*    */   }
/*    */   
/*    */   public EntityInteractEvent(Player player, Integer entityId) {
/* 22 */     this.player = player;
/* 23 */     this.entityId = entityId;
/*    */   }
/*    */   
/*    */   public HandlerList getHandlers() {
/* 27 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 31 */     return handlers;
/*    */   }
/*    */ }