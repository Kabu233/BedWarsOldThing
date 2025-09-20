/*    */ package cn.rmc.bedwars.listener;
/*    */ 
/*    */ import com.comphenix.protocol.PacketType;
/*    */ import com.comphenix.protocol.events.ListenerPriority;
/*    */ import com.comphenix.protocol.events.PacketAdapter;
/*    */ import com.comphenix.protocol.events.PacketContainer;
/*    */ import com.comphenix.protocol.events.PacketEvent;
/*    */ import com.comphenix.protocol.injector.GamePhase;
/*    */ import com.comphenix.protocol.wrappers.PlayerInfoData;
/*    */ import com.comphenix.protocol.wrappers.WrappedGameProfile;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import cn.rmc.bedwars.BedWars;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ 
/*    */ public class PacketListener
/*    */   extends PacketAdapter
/*    */ {
/*    */   public PacketListener() {
/* 21 */     super(PacketAdapter.params().listenerPriority(ListenerPriority.NORMAL).serverSide()
/* 22 */         .clientSide().optionAsync().gamePhase(GamePhase.PLAYING)
/* 23 */         .types(new PacketType[] { PacketType.Play.Server.PLAYER_INFO, PacketType.Play.Client.USE_ENTITY
/* 24 */           }).plugin((Plugin)BedWars.getInstance()));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onPacketReceiving(PacketEvent e) {
/* 33 */     if (e.getPacket() == null)
/* 34 */       return;  if (e.getPacketType() != PacketType.Play.Client.USE_ENTITY)
/* 35 */       return;  Integer entityId = (Integer)e.getPacket().getIntegers().readSafely(0);
/* 36 */     if (entityId == null)
/* 37 */       return;  if (entityId.intValue() == e.getPlayer().getEntityId()) {
/* 38 */       e.setCancelled(true);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void onPacketSending(PacketEvent e) {
/* 44 */     if (!e.isCancelled()) {
/*    */       
/* 46 */       PacketContainer packet = e.getPacket();
/* 47 */       if (e.getPacketType() == PacketType.Play.Server.PLAYER_INFO) {
/* 48 */         List<PlayerInfoData> data = (List<PlayerInfoData>)packet.getPlayerInfoDataLists().read(0);
/* 49 */         if (data == null) {
/*    */           return;
/*    */         }
/* 52 */         List<PlayerInfoData> result = new ArrayList<>();
/* 53 */         for (PlayerInfoData info : data) {
/*    */           
/* 55 */           WrappedGameProfile profile = info.getProfile();
/* 56 */           PlayerInfoData newData = new PlayerInfoData(profile, 1, info.getGameMode(), info.getDisplayName());
/* 57 */           result.add(newData);
/*    */         } 
/* 59 */         packet.getPlayerInfoDataLists().write(0, result);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }