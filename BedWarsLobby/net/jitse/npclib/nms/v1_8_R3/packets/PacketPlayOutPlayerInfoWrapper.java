/*    */ package net.jitse.npclib.nms.v1_8_R3.packets;
/*    */ 
/*    */ import com.comphenix.tinyprotocol.Reflection;
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import net.minecraft.server.v1_8_R3.IChatBaseComponent;
/*    */ import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
/*    */ import net.minecraft.server.v1_8_R3.WorldSettings;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayOutPlayerInfoWrapper
/*    */ {
/*    */   public PacketPlayOutPlayerInfo create(PacketPlayOutPlayerInfo.EnumPlayerInfoAction action, GameProfile gameProfile, String name) {
/* 22 */     PacketPlayOutPlayerInfo packetPlayOutPlayerInfo = new PacketPlayOutPlayerInfo();
/* 23 */     Reflection.getField(packetPlayOutPlayerInfo.getClass(), "a", PacketPlayOutPlayerInfo.EnumPlayerInfoAction.class)
/* 24 */       .set(packetPlayOutPlayerInfo, action);
/*    */     
/* 26 */     packetPlayOutPlayerInfo.getClass();
/* 27 */     PacketPlayOutPlayerInfo.PlayerInfoData playerInfoData = new PacketPlayOutPlayerInfo.PlayerInfoData(packetPlayOutPlayerInfo, gameProfile, 1, WorldSettings.EnumGamemode.NOT_SET, IChatBaseComponent.ChatSerializer.a("{\"text\":\"[NPC] " + name + "\",\"color\":\"dark_gray\"}"));
/*    */     
/* 29 */     Reflection.FieldAccessor<List> fieldAccessor = Reflection.getField(packetPlayOutPlayerInfo.getClass(), "b", List.class);
/* 30 */     fieldAccessor.set(packetPlayOutPlayerInfo, Collections.singletonList(playerInfoData));
/*    */     
/* 32 */     return packetPlayOutPlayerInfo;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\BedWarsLobby-1.0-SNAPSHOT.jar!\net\jitse\npclib\nms\v1_8_R3\packets\PacketPlayOutPlayerInfoWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */