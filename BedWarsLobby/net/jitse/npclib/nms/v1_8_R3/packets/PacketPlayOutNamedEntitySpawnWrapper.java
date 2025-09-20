/*    */ package net.jitse.npclib.nms.v1_8_R3.packets;
/*    */ 
/*    */ import com.comphenix.tinyprotocol.Reflection;
/*    */ import java.util.UUID;
/*    */ import net.minecraft.server.v1_8_R3.DataWatcher;
/*    */ import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;
/*    */ import org.bukkit.Location;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayOutNamedEntitySpawnWrapper
/*    */ {
/*    */   public PacketPlayOutNamedEntitySpawn create(UUID uuid, Location location, int entityId) {
/* 20 */     PacketPlayOutNamedEntitySpawn packetPlayOutNamedEntitySpawn = new PacketPlayOutNamedEntitySpawn();
/*    */     
/* 22 */     Reflection.getField(packetPlayOutNamedEntitySpawn.getClass(), "a", int.class)
/* 23 */       .set(packetPlayOutNamedEntitySpawn, Integer.valueOf(entityId));
/* 24 */     Reflection.getField(packetPlayOutNamedEntitySpawn.getClass(), "b", UUID.class)
/* 25 */       .set(packetPlayOutNamedEntitySpawn, uuid);
/* 26 */     Reflection.getField(packetPlayOutNamedEntitySpawn.getClass(), "c", int.class)
/* 27 */       .set(packetPlayOutNamedEntitySpawn, Integer.valueOf((int)Math.floor(location.getX() * 32.0D)));
/* 28 */     Reflection.getField(packetPlayOutNamedEntitySpawn.getClass(), "d", int.class)
/* 29 */       .set(packetPlayOutNamedEntitySpawn, Integer.valueOf((int)Math.floor(location.getY() * 32.0D)));
/* 30 */     Reflection.getField(packetPlayOutNamedEntitySpawn.getClass(), "e", int.class)
/* 31 */       .set(packetPlayOutNamedEntitySpawn, Integer.valueOf((int)Math.floor(location.getZ() * 32.0D)));
/* 32 */     Reflection.getField(packetPlayOutNamedEntitySpawn.getClass(), "f", byte.class)
/* 33 */       .set(packetPlayOutNamedEntitySpawn, Byte.valueOf((byte)(int)(location.getYaw() * 256.0F / 360.0F)));
/* 34 */     Reflection.getField(packetPlayOutNamedEntitySpawn.getClass(), "g", byte.class)
/* 35 */       .set(packetPlayOutNamedEntitySpawn, Byte.valueOf((byte)(int)(location.getPitch() * 256.0F / 360.0F)));
/*    */     
/* 37 */     DataWatcher dataWatcher = new DataWatcher(null);
/* 38 */     dataWatcher.a(10, Byte.valueOf(127));
/*    */     
/* 40 */     Reflection.getField(packetPlayOutNamedEntitySpawn.getClass(), "i", DataWatcher.class)
/* 41 */       .set(packetPlayOutNamedEntitySpawn, dataWatcher);
/*    */     
/* 43 */     return packetPlayOutNamedEntitySpawn;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\BedWarsLobby-1.0-SNAPSHOT.jar!\net\jitse\npclib\nms\v1_8_R3\packets\PacketPlayOutNamedEntitySpawnWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */