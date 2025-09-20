/*     */ package cn.rmc.bedwarslobby.util.holographic.armor;
/*     */ 
/*     */ import com.comphenix.protocol.PacketType;
/*     */ import com.comphenix.protocol.ProtocolLibrary;
/*     */ import com.comphenix.protocol.events.PacketContainer;
/*     */ import com.comphenix.protocol.wrappers.Vector3F;
/*     */ import com.comphenix.protocol.wrappers.WrappedDataWatcher;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.ThreadLocalRandom;
/*     */ import net.minecraft.server.v1_8_R3.Packet;
/*     */ import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
/*     */ import net.minecraft.server.v1_8_R3.PacketPlayOutEntityTeleport;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemHolographicLine
/*     */ {
/*  31 */   private Map<UUID, Integer> ids = new HashMap<>();
/*  32 */   private Map<UUID, Object> packets = new HashMap<>();
/*  33 */   private List<UUID> players = new ArrayList<>();
/*     */   private Location location;
/*     */   private ItemStack itemStack;
/*     */   private boolean removed;
/*     */   private ShowType showType;
/*     */   
/*     */   public ItemHolographicLine(Location loc, ShowType type) {
/*  40 */     this.location = loc.clone();
/*  41 */     this.showType = type;
/*  42 */     this.removed = false;
/*     */   }
/*     */   
/*     */   public List<Player> getPlayers() {
/*  46 */     List<Player> list = new ArrayList<>();
/*  47 */     this.packets.keySet().forEach(uuid -> {
/*     */           Player player = Bukkit.getPlayer(uuid);
/*     */           
/*     */           if (player != null && player.isOnline()) {
/*     */             list.add(player);
/*     */           }
/*     */         });
/*  54 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setItem(ItemStack is) {
/*     */     try {
/*  60 */       if (!this.removed)
/*  61 */         if (this.itemStack == null) {
/*  62 */           this.itemStack = is;
/*     */           
/*  64 */           for (UUID uuid : this.packets.keySet()) {
/*  65 */             Player player = Bukkit.getPlayer(uuid);
/*  66 */             if (player != null && player.isOnline() && this.players.contains(uuid)) {
/*  67 */               display(player);
/*     */             }
/*     */           } 
/*     */         } else {
/*  71 */           this.itemStack = is;
/*     */           
/*  73 */           for (UUID uuid : this.packets.keySet()) {
/*  74 */             Player player = Bukkit.getPlayer(uuid);
/*  75 */             if (player != null && player.isOnline()) {
/*  76 */               Location loc = player.getLocation().clone();
/*  77 */               loc.setY(this.location.getY());
/*  78 */               if (loc.getWorld().getName().equals(this.location.getWorld().getName()) && loc.distance(this.location) < 64.0D && player.isOnline() && this.players.contains(uuid)) {
/*  79 */                 PacketContainer equipment = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.ENTITY_EQUIPMENT);
/*  80 */                 equipment.getIntegers().write(0, this.ids.get(uuid));
/*  81 */                 equipment.getIntegers().write(1, this.showType.getValue());
/*  82 */                 equipment.getItemModifier().write(0, is);
/*  83 */                 ProtocolLibrary.getProtocolManager().sendServerPacket(player, equipment);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         }  
/*     */     } catch (Throwable $ex) {
/*     */       throw $ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void display(Player player) {
/*     */     try {
/*  95 */       if (!this.removed && 
/*  96 */         player != null) {
/*  97 */         if (!this.ids.containsKey(player.getUniqueId())) {
/*  98 */           this.ids.put(player.getUniqueId(), Integer.valueOf(ThreadLocalRandom.current().nextInt(2147483647)));
/*     */         }
/*     */         
/* 101 */         PacketContainer entity = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.SPAWN_ENTITY_LIVING);
/* 102 */         entity.getIntegers().write(0, this.ids.get(player.getUniqueId()));
/* 103 */         entity.getIntegers().write(1, Integer.valueOf(30));
/* 104 */         entity.getIntegers().write(2, Integer.valueOf(getPacketLoc(this.location.getX())));
/* 105 */         entity.getIntegers().write(3, Integer.valueOf(getPacketLoc(this.location.getY())));
/* 106 */         entity.getIntegers().write(4, Integer.valueOf(getPacketLoc(this.location.getZ())));
/* 107 */         WrappedDataWatcher watcher = new WrappedDataWatcher();
/* 108 */         watcher.setObject(0, Byte.valueOf((byte)32));
/* 109 */         if (!this.showType.name().contains("HAND")) {
/* 110 */           watcher.setObject(11, this.showType.getVector3F());
/*     */         } else {
/* 112 */           watcher.setObject(14, this.showType.getVector3F());
/*     */         } 
/*     */         
/* 115 */         entity.getDataWatcherModifier().write(0, watcher);
/* 116 */         ProtocolLibrary.getProtocolManager().sendServerPacket(player, entity);
/* 117 */         PacketContainer equipment = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.ENTITY_EQUIPMENT);
/* 118 */         equipment.getIntegers().write(0, this.ids.get(player.getUniqueId()));
/* 119 */         equipment.getIntegers().write(1, this.showType.getValue());
/* 120 */         equipment.getItemModifier().write(0, this.itemStack);
/* 121 */         ProtocolLibrary.getProtocolManager().sendServerPacket(player, equipment);
/* 122 */         Object destroyPacket = new PacketPlayOutEntityDestroy(new int[] { ((Integer)this.ids.get(player.getUniqueId())).intValue() });
/* 123 */         this.packets.put(player.getUniqueId(), destroyPacket);
/* 124 */         this.players.add(player.getUniqueId());
/*     */       } 
/*     */     } catch (Throwable $ex) {
/*     */       throw $ex;
/*     */     } 
/*     */   } public void destroy(Player player) {
/* 130 */     if (!this.removed && 
/* 131 */       player != null && this.packets.containsKey(player.getUniqueId())) {
/* 132 */       sendPacket(player, (Packet)this.packets.get(player.getUniqueId()));
/* 133 */       this.ids.remove(player.getUniqueId());
/* 134 */       this.packets.remove(player.getUniqueId());
/* 135 */       this.players.remove(player.getUniqueId());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void redisplay() {
/* 142 */     if (!this.removed) {
/* 143 */       this.packets.keySet().forEach(uuid -> {
/*     */             Player player = Bukkit.getPlayer(uuid);
/*     */             if (player != null && player.isOnline()) {
/*     */               display(player);
/*     */             }
/*     */           });
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void remove() {
/* 154 */     if (!this.removed) {
/* 155 */       for (UUID uuid : this.packets.keySet()) {
/* 156 */         Player player = Bukkit.getPlayer(uuid);
/* 157 */         if (player != null && player.isOnline()) {
/* 158 */           sendPacket(player, (Packet)this.packets.get(player.getUniqueId()));
/*     */         }
/*     */       } 
/*     */       
/* 162 */       this.ids = new HashMap<>();
/* 163 */       this.packets = new HashMap<>();
/* 164 */       this.players = new ArrayList<>();
/* 165 */       this.removed = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void teleport(Location loc, Player player) {
/* 170 */     Location location = loc.clone();
/* 171 */     PacketPlayOutEntityTeleport teleport = new PacketPlayOutEntityTeleport(((Integer)this.ids.get(player.getUniqueId())).intValue(), (int)(location.getX() * 32.0D), (int)(location.getY() * 32.0D), (int)(location.getZ() * 32.0D), (byte)(int)(location.getYaw() * 256.0F / 360.0F), (byte)(int)(location.getPitch() * 256.0F / 360.0F), false);
/* 172 */     sendPacket(player, (Packet<?>)teleport);
/*     */   }
/*     */   
/*     */   public Location getLocation() {
/* 176 */     return this.location.clone();
/*     */   }
/*     */   
/*     */   public int getEntityId(Player p) {
/* 180 */     return ((Integer)this.ids.getOrDefault(p.getUniqueId(), Integer.valueOf(0))).intValue();
/*     */   }
/*     */   
/*     */   public boolean isRemoved() {
/* 184 */     return this.removed;
/*     */   }
/*     */   
/*     */   public static void sendPacket(Player p, Packet<?> packet) {
/* 188 */     (((CraftPlayer)p).getHandle()).playerConnection.sendPacket(packet);
/*     */   }
/*     */   
/*     */   private static int getPacketLoc(double loc) {
/* 192 */     return (int)Math.floor(loc * 32.0D);
/*     */   }
/*     */   
/*     */   public enum ShowType {
/* 196 */     ANI_HEAD(0, -45, 0),
/* 197 */     ANI_HAND(80, 0, 0),
/* 198 */     SELECTOR_CHEST(0, -25, 0),
/* 199 */     SELECTOR_SHOP(0, 75, 0),
/* 200 */     SELECTOR_CLOSE_LEFT(0, -20, 0),
/* 201 */     SELECTOR_CLOSE_RIGHT(0, -160, 0),
/* 202 */     LOOT_HEAD_LEFT(0, 70, 0),
/* 203 */     LOOT_HEAD_MIDDLE(0, 90, 0),
/* 204 */     LOOT_HEAD_RIGHT(0, 110, 0),
/* 205 */     LOOT_HAND_LEFT(80, 70, 0),
/* 206 */     LOOT_HAND_MIDDLE(80, 90, 0),
/* 207 */     LOOT_HAND_RIGHT(80, 110, 0);
/*     */     
/*     */     Vector3F vector3F;
/*     */     
/*     */     ShowType(int x, int y, int z) {
/* 212 */       this.vector3F = new Vector3F(x, y, z);
/*     */     }
/*     */     
/*     */     public Integer getValue() {
/* 216 */       switch (this) {
/*     */         case ANI_HAND:
/*     */         case LOOT_HAND_LEFT:
/*     */         case LOOT_HAND_RIGHT:
/*     */         case LOOT_HAND_MIDDLE:
/* 221 */           return Integer.valueOf(0);
/*     */       } 
/* 223 */       return Integer.valueOf(4);
/*     */     }
/*     */ 
/*     */     
/*     */     public Vector3F getVector3F() {
/* 228 */       return this.vector3F;
/*     */     }
/*     */   }
/*     */ }