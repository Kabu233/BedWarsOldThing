/*    */ package net.jitse.npclib.nms.v1_8_R3.packets;
/*    */ 
/*    */ import com.comphenix.tinyprotocol.Reflection;
/*    */ import net.minecraft.server.v1_8_R3.PacketPlayOutEntityHeadRotation;
/*    */ import org.bukkit.Location;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayOutEntityHeadRotationWrapper
/*    */ {
/*    */   public PacketPlayOutEntityHeadRotation create(Location location, int entityId) {
/* 17 */     PacketPlayOutEntityHeadRotation packetPlayOutEntityHeadRotation = new PacketPlayOutEntityHeadRotation();
/*    */     
/* 19 */     Reflection.getField(packetPlayOutEntityHeadRotation.getClass(), "a", int.class)
/* 20 */       .set(packetPlayOutEntityHeadRotation, Integer.valueOf(entityId));
/* 21 */     Reflection.getField(packetPlayOutEntityHeadRotation.getClass(), "b", byte.class)
/* 22 */       .set(packetPlayOutEntityHeadRotation, Byte.valueOf((byte)(int)((int)location.getYaw() * 256.0F / 360.0F)));
/*    */     
/* 24 */     return packetPlayOutEntityHeadRotation;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\BedWarsLobby-1.0-SNAPSHOT.jar!\net\jitse\npclib\nms\v1_8_R3\packets\PacketPlayOutEntityHeadRotationWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */