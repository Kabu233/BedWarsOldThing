/*    */ package net.jitse.npclib.nms.v1_8_R3.packets;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import net.jitse.npclib.api.state.NPCState;
/*    */ import net.minecraft.server.v1_8_R3.DataWatcher;
/*    */ import net.minecraft.server.v1_8_R3.PacketPlayOutEntityMetadata;
/*    */ 
/*    */ 
/*    */ public class PacketPlayOutEntityMetadataWrapper
/*    */ {
/*    */   public PacketPlayOutEntityMetadata create(Collection<NPCState> activateStates, int entityId) {
/* 12 */     DataWatcher dataWatcher = new DataWatcher(null);
/* 13 */     byte masked = NPCState.getMasked(activateStates);
/* 14 */     dataWatcher.a(0, Byte.valueOf(masked));
/*    */     
/* 16 */     return new PacketPlayOutEntityMetadata(entityId, dataWatcher, true);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\BedWarsLobby-1.0-SNAPSHOT.jar!\net\jitse\npclib\nms\v1_8_R3\packets\PacketPlayOutEntityMetadataWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */