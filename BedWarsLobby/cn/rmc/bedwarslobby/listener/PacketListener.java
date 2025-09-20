/*    */ package cn.rmc.bedwarslobby.listener;
/*    */ import com.comphenix.protocol.PacketType;
/*    */ import com.comphenix.protocol.events.ListenerPriority;
/*    */ import com.comphenix.protocol.events.PacketAdapter;
/*    */ import com.comphenix.protocol.events.PacketEvent;
/*    */ import javax.annotation.Nonnull;
/*    */ import cn.rmc.bedwarslobby.event.EntityInteractEvent;
/*    */ import cn.rmc.bedwarslobby.loot.LootChestBasic;
/*    */ import cn.rmc.bedwarslobby.loot.utils.CameraUtils;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.plugin.java.JavaPlugin;
/*    */ 
/*    */ public class PacketListener extends PacketAdapter {
/*    */   public PacketListener(@Nonnull JavaPlugin plugin) {
/* 18 */     super(PacketAdapter.params().listenerPriority(ListenerPriority.NORMAL).serverSide().clientSide().optionAsync().gamePhase(GamePhase.PLAYING).types(new PacketType[] { PacketType.Play.Client.USE_ENTITY, PacketType.Play.Client.STEER_VEHICLE
/* 19 */           }).plugin((Plugin)plugin));
/*    */   }
/*    */   
/*    */   public void onPacketReceiving(PacketEvent event) {
/* 23 */     if (event.getPacket() != null) {
/* 24 */       Player player = event.getPlayer();
/* 25 */       if (event.getPacket().getType() == PacketType.Play.Client.STEER_VEHICLE) {
/* 26 */         if (CameraUtils.players.contains(player)) {
/* 27 */           event.getPacket().getBooleans().write(1, Boolean.valueOf(false));
/*    */         }
/* 29 */       } else if (event.getPacket().getType() == PacketType.Play.Client.USE_ENTITY) {
/* 30 */         if (LootChestBasic.chests.containsKey(player)) {
/* 31 */           ((LootChestBasic)LootChestBasic.chests.get(player)).PacketReceive(event);
/*    */           return;
/*    */         } 
/* 34 */         Integer entityId = (Integer)event.getPacket().getIntegers().readSafely(0);
/* 35 */         Bukkit.getPluginManager().callEvent((Event)new EntityInteractEvent(player, entityId));
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }