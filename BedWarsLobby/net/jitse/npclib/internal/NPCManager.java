/*    */ package net.jitse.npclib.internal;
/*    */ 
/*    */ import java.util.Collections;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class NPCManager
/*    */ {
/* 18 */   private static Set<NPCBase> npcs = new HashSet<>();
/*    */   
/*    */   public static Set<NPCBase> getAllNPCs() {
/* 21 */     return npcs;
/*    */   }
/*    */   
/*    */   public static Set<NPCBase> getShownToPlayer(Player player) {
/* 25 */     Set<NPCBase> set = Collections.emptySet();
/* 26 */     for (NPCBase npc : getAllNPCs()) {
/* 27 */       if (npc.getShown().contains(player.getUniqueId())) {
/* 28 */         set.add(npc);
/*    */       }
/*    */     } 
/* 31 */     return set;
/*    */   }
/*    */   
/*    */   public static void add(NPCBase npc) {
/* 35 */     npcs.add(npc);
/*    */   }
/*    */   
/*    */   public static void remove(NPCBase npc) {
/* 39 */     npcs.remove(npc);
/*    */   }
/*    */   
/*    */   private NPCManager() {
/* 43 */     throw new SecurityException("You cannot initialize this class.");
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\BedWarsLobby-1.0-SNAPSHOT.jar!\net\jitse\npclib\internal\NPCManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */