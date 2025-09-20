/*    */ package net.jitse.npclib.nms.v1_8_R3.packets;
/*    */ 
/*    */ import com.comphenix.tinyprotocol.Reflection;
/*    */ import net.jitse.npclib.api.state.NPCAnimation;
/*    */ import net.minecraft.server.v1_8_R3.PacketPlayOutAnimation;
/*    */ 
/*    */ public class PacketPlayOutAnimationWrapper
/*    */ {
/*    */   public PacketPlayOutAnimation create(NPCAnimation npcAnimation, int entityId) {
/* 10 */     PacketPlayOutAnimation packetPlayOutAnimation = new PacketPlayOutAnimation();
/*    */     
/* 12 */     Reflection.getField(packetPlayOutAnimation.getClass(), "a", int.class)
/* 13 */       .set(packetPlayOutAnimation, Integer.valueOf(entityId));
/* 14 */     Reflection.getField(packetPlayOutAnimation.getClass(), "b", int.class)
/* 15 */       .set(packetPlayOutAnimation, Integer.valueOf(npcAnimation.getId()));
/*    */     
/* 17 */     return packetPlayOutAnimation;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\BedWarsLobby-1.0-SNAPSHOT.jar!\net\jitse\npclib\nms\v1_8_R3\packets\PacketPlayOutAnimationWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */