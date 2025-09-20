/*     */ package net.jitse.npclib.listeners;
/*     */ 
/*     */ import com.comphenix.tinyprotocol.Reflection;
/*     */ import com.comphenix.tinyprotocol.TinyProtocol;
/*     */ import io.netty.channel.Channel;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import net.jitse.npclib.NPCLib;
/*     */ import net.jitse.npclib.api.NPC;
/*     */ import net.jitse.npclib.api.events.NPCInteractEvent;
/*     */ import net.jitse.npclib.internal.MinecraftVersion;
/*     */ import net.jitse.npclib.internal.NPCBase;
/*     */ import net.jitse.npclib.internal.NPCManager;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PacketListener
/*     */ {
/*  28 */   private final MinecraftVersion version = MinecraftVersion.valueOf(
/*  29 */       Bukkit.getServer().getClass().getPackage().getName()
/*  30 */       .replace("org.bukkit.craftbukkit", "")
/*  31 */       .replace(".", "").toUpperCase());
/*     */ 
/*     */   
/*  34 */   private final Class<?> packetPlayInUseEntityClazz = Reflection.getClass("{nms}.PacketPlayInUseEntity", new String[] { "net.minecraft.network.protocol.game.PacketPlayInUseEntity" });
/*     */ 
/*     */ 
/*     */   
/*  38 */   private final Reflection.FieldAccessor<Integer> entityIdField = Reflection.getField(this.packetPlayInUseEntityClazz, this.version.isAboveOrEqual(MinecraftVersion.V1_20_R4) ? "b" : "a", int.class);
/*  39 */   private final Reflection.FieldAccessor<?> actionField = Reflection.getField(this.packetPlayInUseEntityClazz, Object.class, 
/*  40 */       this.version.isAboveOrEqual(MinecraftVersion.V1_20_R4) ? 1 : 0);
/*     */ 
/*     */   
/*  43 */   private final Set<UUID> delay = new HashSet<>();
/*     */   
/*     */   private Plugin plugin;
/*     */   
/*     */   public void start(NPCLib instance) {
/*  48 */     this.plugin = (Plugin)instance.getPlugin();
/*     */     
/*  50 */     new TinyProtocol(this.plugin)
/*     */       {
/*     */         public Object onPacketInAsync(Player player, Channel channel, Object packet)
/*     */         {
/*  54 */           return PacketListener.this.handleInteractPacket(player, packet) ? super.onPacketInAsync(player, channel, packet) : null;
/*     */         }
/*     */       };
/*     */   }
/*     */   private boolean handleInteractPacket(Player player, Object packet) {
/*     */     NPCInteractEvent.ClickType clickType;
/*  60 */     if (!this.packetPlayInUseEntityClazz.isInstance(packet) || player == null) {
/*  61 */       return true;
/*     */     }
/*  63 */     NPCBase npc = null;
/*  64 */     int packetEntityId = ((Integer)this.entityIdField.get(packet)).intValue();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  72 */     for (NPCBase testNPC : NPCManager.getAllNPCs()) {
/*  73 */       if (testNPC.isCreated() && testNPC.isShown(player) && testNPC.getEntityId() == packetEntityId) {
/*  74 */         npc = testNPC;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*  79 */     if (npc == null)
/*     */     {
/*  81 */       return true;
/*     */     }
/*     */     
/*  84 */     if (this.delay.contains(player.getUniqueId())) {
/*  85 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  89 */     if (this.version.isAboveOrEqual(MinecraftVersion.V1_17_R1)) {
/*  90 */       if (this.actionField.get(packet).getClass().getTypeName().contains("PacketPlayInUseEntity$1")) {
/*  91 */         clickType = NPCInteractEvent.ClickType.RIGHT_CLICK;
/*  92 */       } else if (this.actionField.get(packet).getClass().getTypeName().contains("PacketPlayInUseEntity$d") || this.actionField
/*  93 */         .get(packet).getClass().getTypeName().contains("PacketPlayInUseEntity$e")) {
/*  94 */         clickType = NPCInteractEvent.ClickType.LEFT_CLICK;
/*     */       } else {
/*  96 */         throw new IllegalStateException("Did not recognize click type: " + this.actionField.get(packet).getClass().getTypeName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 120 */       clickType = this.actionField.get(packet).toString().equals("ATTACK") ? NPCInteractEvent.ClickType.LEFT_CLICK : NPCInteractEvent.ClickType.RIGHT_CLICK;
/*     */     } 
/*     */ 
/*     */     
/* 124 */     this.delay.add(player.getUniqueId());
/* 125 */     Bukkit.getScheduler().runTask(this.plugin, new TaskCallNpcInteractEvent(new NPCInteractEvent(player, clickType, (NPC)npc), this));
/* 126 */     return false;
/*     */   }
/*     */   
/*     */   private static final class TaskCallNpcInteractEvent
/*     */     implements Runnable
/*     */   {
/*     */     private NPCInteractEvent eventToCall;
/*     */     private PacketListener listener;
/* 134 */     private static Location playerLocation = new Location(null, 0.0D, 0.0D, 0.0D);
/*     */     
/*     */     TaskCallNpcInteractEvent(NPCInteractEvent eventToCall, PacketListener listener) {
/* 137 */       this.eventToCall = eventToCall;
/* 138 */       this.listener = listener;
/*     */     }
/*     */ 
/*     */     
/*     */     public void run() {
/* 143 */       Player player = this.eventToCall.getWhoClicked();
/* 144 */       this.listener.delay.remove(player.getUniqueId());
/* 145 */       if (!player.getWorld().equals(this.eventToCall.getNPC().getWorld())) {
/*     */         return;
/*     */       }
/* 148 */       double distance = player.getLocation(playerLocation).distanceSquared(this.eventToCall.getNPC().getLocation());
/* 149 */       if (distance <= 64.0D)
/* 150 */         Bukkit.getPluginManager().callEvent((Event)this.eventToCall); 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\BedWarsLobby-1.0-SNAPSHOT.jar!\net\jitse\npclib\listeners\PacketListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */