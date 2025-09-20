/*    */ package net.jitse.npclib.internal;
/*    */ 
/*    */ import net.jitse.npclib.api.state.NPCAnimation;
/*    */ import net.jitse.npclib.api.state.NPCSlot;
/*    */ import org.bukkit.Location;
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
/*    */ interface NPCPacketHandler
/*    */ {
/*    */   void createPackets();
/*    */   
/*    */   void sendShowPackets(Player paramPlayer);
/*    */   
/*    */   void sendHidePackets(Player paramPlayer);
/*    */   
/*    */   void sendMetadataPacket(Player paramPlayer);
/*    */   
/*    */   void sendEquipmentPacket(Player paramPlayer, NPCSlot paramNPCSlot, boolean paramBoolean);
/*    */   
/*    */   void sendAnimationPacket(Player paramPlayer, NPCAnimation paramNPCAnimation);
/*    */   
/*    */   void sendHeadRotationPackets(Location paramLocation);
/*    */   
/*    */   default void sendEquipmentPackets(Player player) {
/* 33 */     for (NPCSlot slot : NPCSlot.values())
/* 34 */       sendEquipmentPacket(player, slot, true); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\BedWarsLobby-1.0-SNAPSHOT.jar!\net\jitse\npclib\internal\NPCPacketHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */