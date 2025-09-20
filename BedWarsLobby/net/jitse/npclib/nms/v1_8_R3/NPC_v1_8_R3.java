/*     */ package net.jitse.npclib.nms.v1_8_R3;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.properties.Property;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import net.jitse.npclib.NPCLib;
/*     */ import net.jitse.npclib.api.skin.Skin;
/*     */ import net.jitse.npclib.api.state.NPCAnimation;
/*     */ import net.jitse.npclib.api.state.NPCSlot;
/*     */ import net.jitse.npclib.hologram.Hologram;
/*     */ import net.jitse.npclib.internal.MinecraftVersion;
/*     */ import net.jitse.npclib.nms.v1_8_R3.packets.PacketPlayOutAnimationWrapper;
/*     */ import net.jitse.npclib.nms.v1_8_R3.packets.PacketPlayOutEntityHeadRotationWrapper;
/*     */ import net.jitse.npclib.nms.v1_8_R3.packets.PacketPlayOutEntityMetadataWrapper;
/*     */ import net.jitse.npclib.nms.v1_8_R3.packets.PacketPlayOutNamedEntitySpawnWrapper;
/*     */ import net.jitse.npclib.nms.v1_8_R3.packets.PacketPlayOutPlayerInfoWrapper;
/*     */ import net.jitse.npclib.nms.v1_8_R3.packets.PacketPlayOutScoreboardTeamWrapper;
/*     */ import net.minecraft.server.v1_8_R3.Packet;
/*     */ import net.minecraft.server.v1_8_R3.PacketPlayOutAnimation;
/*     */ import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
/*     */ import net.minecraft.server.v1_8_R3.PacketPlayOutEntityEquipment;
/*     */ import net.minecraft.server.v1_8_R3.PacketPlayOutEntityMetadata;
/*     */ import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;
/*     */ import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
/*     */ import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardTeam;
/*     */ import net.minecraft.server.v1_8_R3.PlayerConnection;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.util.Vector;
/*     */ 
/*     */ public class NPC_v1_8_R3 extends NPCBase {
/*     */   private PacketPlayOutNamedEntitySpawn packetPlayOutNamedEntitySpawn;
/*     */   private PacketPlayOutScoreboardTeam packetPlayOutScoreboardTeamRegister;
/*     */   private PacketPlayOutPlayerInfo packetPlayOutPlayerInfoAdd;
/*  41 */   private final HashMap<UUID, Integer> playerInfoRemoveTimers = new HashMap<>(); private PacketPlayOutPlayerInfo packetPlayOutPlayerInfoRemove; private PacketPlayOutEntityHeadRotation packetPlayOutEntityHeadRotation; private PacketPlayOutEntityDestroy packetPlayOutEntityDestroy;
/*     */   
/*     */   public NPC_v1_8_R3(NPCLib instance, List<String> lines) {
/*  44 */     super(instance, lines, MinecraftVersion.V1_8_R3);
/*     */   }
/*     */ 
/*     */   
/*     */   public Hologram getHologram(Player player) {
/*  49 */     Hologram holo = super.getHologram(player);
/*  50 */     if (holo == null) {
/*  51 */       holo = new Hologram(this.version, this.location.clone().add(0.0D, 0.5D, 0.0D), getText(player));
/*     */     }
/*  53 */     this.playerHologram.put(player.getUniqueId(), holo);
/*  54 */     return holo;
/*     */   }
/*     */ 
/*     */   
/*     */   public void createPackets() {
/*  59 */     PacketPlayOutPlayerInfoWrapper packetPlayOutPlayerInfoWrapper = new PacketPlayOutPlayerInfoWrapper();
/*     */ 
/*     */     
/*  62 */     this
/*  63 */       .packetPlayOutScoreboardTeamRegister = (new PacketPlayOutScoreboardTeamWrapper()).createRegisterTeam(this.name);
/*     */     
/*  65 */     this
/*  66 */       .packetPlayOutPlayerInfoAdd = packetPlayOutPlayerInfoWrapper.create(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, this.gameProfile, this.name);
/*     */     
/*  68 */     this
/*  69 */       .packetPlayOutNamedEntitySpawn = (new PacketPlayOutNamedEntitySpawnWrapper()).create(this.uuid, this.location, this.entityId);
/*     */     
/*  71 */     this
/*  72 */       .packetPlayOutEntityHeadRotation = (new PacketPlayOutEntityHeadRotationWrapper()).create(this.location, this.entityId);
/*     */     
/*  74 */     this
/*  75 */       .packetPlayOutPlayerInfoRemove = packetPlayOutPlayerInfoWrapper.create(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, this.gameProfile, this.name);
/*     */ 
/*     */     
/*  78 */     this.packetPlayOutEntityDestroy = new PacketPlayOutEntityDestroy(new int[] { this.entityId });
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendShowPackets(Player player) {
/*  83 */     PlayerConnection playerConnection = (((CraftPlayer)player).getHandle()).playerConnection;
/*     */     
/*  85 */     if (this.hasTeamRegistered.add(player.getUniqueId()))
/*  86 */       playerConnection.sendPacket((Packet)this.packetPlayOutScoreboardTeamRegister); 
/*  87 */     playerConnection.sendPacket((Packet)this.packetPlayOutPlayerInfoAdd);
/*  88 */     playerConnection.sendPacket((Packet)this.packetPlayOutNamedEntitySpawn);
/*  89 */     playerConnection.sendPacket((Packet)this.packetPlayOutEntityHeadRotation);
/*     */     
/*  91 */     getHologram(player).show(player);
/*     */ 
/*     */     
/*  94 */     if (this.playerInfoRemoveTimers.containsKey(player.getUniqueId())) {
/*  95 */       Bukkit.getScheduler().cancelTask(((Integer)this.playerInfoRemoveTimers.get(player.getUniqueId())).intValue());
/*     */     }
/*     */     
/*  98 */     this.playerInfoRemoveTimers.put(player.getUniqueId(), Integer.valueOf(Bukkit.getScheduler().runTaskLater((Plugin)this.instance.getPlugin(), () -> { if (isShown(player)) playerConnection.sendPacket((Packet)this.packetPlayOutPlayerInfoRemove);  this.playerInfoRemoveTimers.remove(player.getUniqueId()); }200L)
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 103 */           .getTaskId()));
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendHidePackets(Player player) {
/* 108 */     PlayerConnection playerConnection = (((CraftPlayer)player).getHandle()).playerConnection;
/*     */     
/* 110 */     playerConnection.sendPacket((Packet)this.packetPlayOutEntityDestroy);
/* 111 */     playerConnection.sendPacket((Packet)this.packetPlayOutPlayerInfoRemove);
/*     */     
/* 113 */     getHologram(player).hide(player);
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendMetadataPacket(Player player) {
/* 118 */     PlayerConnection playerConnection = (((CraftPlayer)player).getHandle()).playerConnection;
/* 119 */     PacketPlayOutEntityMetadata packet = (new PacketPlayOutEntityMetadataWrapper()).create(this.activeStates, this.entityId);
/*     */     
/* 121 */     playerConnection.sendPacket((Packet)packet);
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendEquipmentPacket(Player player, NPCSlot slot, boolean auto) {
/* 126 */     PlayerConnection playerConnection = (((CraftPlayer)player).getHandle()).playerConnection;
/*     */     
/* 128 */     if (slot == NPCSlot.OFFHAND) {
/* 129 */       if (!auto) {
/* 130 */         throw new UnsupportedOperationException("Offhand is not supported on servers below 1.9");
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/* 135 */     ItemStack item = getItem(slot);
/*     */     
/* 137 */     PacketPlayOutEntityEquipment packet = new PacketPlayOutEntityEquipment(this.entityId, slot.getSlot(), CraftItemStack.asNMSCopy(item));
/* 138 */     playerConnection.sendPacket((Packet)packet);
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendAnimationPacket(Player player, NPCAnimation animation) {
/* 143 */     if (animation == NPCAnimation.SWING_OFFHAND) {
/* 144 */       throw new IllegalArgumentException("Offhand Swing Animations are only available on 1.9 and up.");
/*     */     }
/*     */     
/* 147 */     PlayerConnection playerConnection = (((CraftPlayer)player).getHandle()).playerConnection;
/*     */     
/* 149 */     PacketPlayOutAnimation packet = (new PacketPlayOutAnimationWrapper()).create(animation, this.entityId);
/* 150 */     playerConnection.sendPacket((Packet)packet);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateSkin(Skin skin) {
/* 155 */     GameProfile newProfile = new GameProfile(this.uuid, this.name);
/* 156 */     newProfile.getProperties().get("textures").clear();
/* 157 */     newProfile.getProperties().put("textures", new Property("textures", skin.getValue(), skin.getSignature()));
/* 158 */     this.packetPlayOutPlayerInfoAdd = (new PacketPlayOutPlayerInfoWrapper()).create(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, newProfile, this.name);
/*     */     
/* 160 */     for (UUID shownUuid : getShown()) {
/* 161 */       Player player = Bukkit.getPlayer(shownUuid);
/* 162 */       if (player != null && isShown(player)) {
/* 163 */         sendHidePackets(player);
/*     */         
/* 165 */         sendShowPackets(player);
/* 166 */         sendMetadataPacket(player);
/* 167 */         sendEquipmentPackets(player);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendHeadRotationPackets(Location location) {
/* 174 */     for (UUID shownUuid : getShown()) {
/* 175 */       Player player = Bukkit.getPlayer(shownUuid);
/* 176 */       if (player != null && isShown(player)) {
/* 177 */         PlayerConnection connection = (((CraftPlayer)player).getHandle()).playerConnection;
/*     */         
/* 179 */         Location npcLocation = getLocation();
/* 180 */         Vector dirBetweenLocations = location.toVector().subtract(npcLocation.toVector());
/*     */         
/* 182 */         npcLocation.setDirection(dirBetweenLocations);
/*     */         
/* 184 */         float yaw = npcLocation.getYaw();
/* 185 */         float pitch = npcLocation.getPitch();
/*     */         
/* 187 */         connection.sendPacket((Packet)new PacketPlayOutEntity.PacketPlayOutEntityLook(getEntityId(), (byte)(int)(yaw % 360.0D * 256.0D / 360.0D), (byte)(int)(pitch % 360.0D * 256.0D / 360.0D), false));
/* 188 */         connection.sendPacket((Packet)(new PacketPlayOutEntityHeadRotationWrapper()).create(npcLocation, this.entityId));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\BedWarsLobby-1.0-SNAPSHOT.jar!\net\jitse\npclib\nms\v1_8_R3\NPC_v1_8_R3.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */