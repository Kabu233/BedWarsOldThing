/*    */ package net.jitse.npclib.nms.v1_8_R3.packets;
/*    */ 
/*    */ import com.comphenix.tinyprotocol.Reflection;
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardTeam;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayOutScoreboardTeamWrapper
/*    */ {
/*    */   public PacketPlayOutScoreboardTeam createRegisterTeam(String name) {
/* 19 */     PacketPlayOutScoreboardTeam packetPlayOutScoreboardTeam = new PacketPlayOutScoreboardTeam();
/*    */     
/* 21 */     Reflection.getField(packetPlayOutScoreboardTeam.getClass(), "h", int.class)
/* 22 */       .set(packetPlayOutScoreboardTeam, Integer.valueOf(0));
/* 23 */     Reflection.getField(packetPlayOutScoreboardTeam.getClass(), "b", String.class)
/* 24 */       .set(packetPlayOutScoreboardTeam, name);
/* 25 */     Reflection.getField(packetPlayOutScoreboardTeam.getClass(), "a", String.class)
/* 26 */       .set(packetPlayOutScoreboardTeam, name);
/* 27 */     Reflection.getField(packetPlayOutScoreboardTeam.getClass(), "e", String.class)
/* 28 */       .set(packetPlayOutScoreboardTeam, "never");
/* 29 */     Reflection.getField(packetPlayOutScoreboardTeam.getClass(), "i", int.class)
/* 30 */       .set(packetPlayOutScoreboardTeam, Integer.valueOf(1));
/* 31 */     Reflection.FieldAccessor<Collection> collectionFieldAccessor = Reflection.getField(packetPlayOutScoreboardTeam
/* 32 */         .getClass(), "g", Collection.class);
/* 33 */     collectionFieldAccessor.set(packetPlayOutScoreboardTeam, Collections.singletonList(name));
/*    */     
/* 35 */     return packetPlayOutScoreboardTeam;
/*    */   }
/*    */   
/*    */   public PacketPlayOutScoreboardTeam createUnregisterTeam(String name) {
/* 39 */     PacketPlayOutScoreboardTeam packetPlayOutScoreboardTeam = new PacketPlayOutScoreboardTeam();
/*    */     
/* 41 */     Reflection.getField(packetPlayOutScoreboardTeam.getClass(), "h", int.class)
/* 42 */       .set(packetPlayOutScoreboardTeam, Integer.valueOf(1));
/* 43 */     Reflection.getField(packetPlayOutScoreboardTeam.getClass(), "a", String.class)
/* 44 */       .set(packetPlayOutScoreboardTeam, name);
/*    */     
/* 46 */     return packetPlayOutScoreboardTeam;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\BedWarsLobby-1.0-SNAPSHOT.jar!\net\jitse\npclib\nms\v1_8_R3\packets\PacketPlayOutScoreboardTeamWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */