/*    */ package net.jitse.npclib.api.events;
/*    */ 
/*    */ import net.jitse.npclib.api.NPC;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NPCInteractEvent
/*    */   extends Event
/*    */ {
/* 17 */   private static final HandlerList handlers = new HandlerList();
/*    */   
/*    */   private final Player player;
/*    */   private final ClickType clickType;
/*    */   private final NPC npc;
/*    */   
/*    */   public NPCInteractEvent(Player player, ClickType clickType, NPC npc) {
/* 24 */     this.player = player;
/* 25 */     this.clickType = clickType;
/* 26 */     this.npc = npc;
/*    */   }
/*    */   
/*    */   public Player getWhoClicked() {
/* 30 */     return this.player;
/*    */   }
/*    */   
/*    */   public ClickType getClickType() {
/* 34 */     return this.clickType;
/*    */   }
/*    */   
/*    */   public NPC getNPC() {
/* 38 */     return this.npc;
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
/*    */   
/*    */   public enum ClickType {
/* 51 */     LEFT_CLICK, RIGHT_CLICK;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\BedWarsLobby-1.0-SNAPSHOT.jar!\net\jitse\npclib\api\events\NPCInteractEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */