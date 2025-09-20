/*    */ package net.jitse.npclib.api.events;
/*    */ 
/*    */ import net.jitse.npclib.api.NPC;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NPCShowEvent
/*    */   extends Event
/*    */   implements Cancellable
/*    */ {
/* 18 */   private static final HandlerList handlers = new HandlerList();
/*    */   
/*    */   private boolean cancelled = false;
/*    */   
/*    */   private final NPC npc;
/*    */   private final Player player;
/*    */   private final boolean automatic;
/*    */   
/*    */   public NPCShowEvent(NPC npc, Player player, boolean automatic) {
/* 27 */     this.npc = npc;
/* 28 */     this.player = player;
/* 29 */     this.automatic = automatic;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setCancelled(boolean cancelled) {
/* 34 */     this.cancelled = cancelled;
/*    */   }
/*    */   
/*    */   public NPC getNPC() {
/* 38 */     return this.npc;
/*    */   }
/*    */   
/*    */   public Player getPlayer() {
/* 42 */     return this.player;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isAutomatic() {
/* 49 */     return this.automatic;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isCancelled() {
/* 54 */     return this.cancelled;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 59 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 63 */     return handlers;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\BedWarsLobby-1.0-SNAPSHOT.jar!\net\jitse\npclib\api\events\NPCShowEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */