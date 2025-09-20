/*    */ package net.jitse.npclib.listeners;
/*    */ 
/*    */ import net.jitse.npclib.internal.NPCBase;
/*    */ import net.jitse.npclib.internal.NPCManager;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ public class HandleMoveBase
/*    */ {
/*    */   void handleMove(Player player) {
/* 11 */     for (NPCBase npc : NPCManager.getAllNPCs()) {
/* 12 */       if (!npc.getShown().contains(player.getUniqueId())) {
/*    */         continue;
/*    */       }
/*    */       
/* 16 */       if (!npc.isShown(player) && npc.inRangeOf(player) && npc.inViewOf(player))
/*    */       {
/* 18 */         npc.show(player, true);
/*    */       }
/*    */       
/* 21 */       if (npc.isShown(player) && !npc.inRangeOf(player))
/*    */       {
/* 23 */         npc.hide(player, true);
/*    */       }
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\BedWarsLobby-1.0-SNAPSHOT.jar!\net\jitse\npclib\listeners\HandleMoveBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */