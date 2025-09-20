/*     */ package cn.rmc.bedwarslobby.util.holographic;
/*     */ 
/*     */ import com.comphenix.protocol.PacketType;
/*     */ import com.comphenix.protocol.ProtocolLibrary;
/*     */ import com.comphenix.protocol.ProtocolManager;
/*     */ import com.comphenix.protocol.events.PacketContainer;
/*     */ import com.comphenix.protocol.wrappers.EnumWrappers;
/*     */ import com.comphenix.protocol.wrappers.WrappedDataWatcher;
/*     */ import com.comphenix.protocol.wrappers.WrappedWatchableObject;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.UUID;
/*     */ import cn.rmc.bedwarslobby.util.BukkitReflection;
/*     */ import net.minecraft.server.v1_8_R3.Packet;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HolographicLine
/*     */ {
/*  33 */   private Map<UUID, Integer> ids = new HashMap<>(); public Map<UUID, Integer> getIds() { return this.ids; }
/*  34 */    private Map<UUID, Object> packets = new HashMap<>(); public Map<UUID, Object> getPackets() { return this.packets; }
/*  35 */    private List<UUID> players = new ArrayList<>(); private Location location; private String title;
/*     */   public String getTitle() {
/*  37 */     return this.title;
/*  38 */   } private WrappedDataWatcher.Serializer stringserializer; private WrappedDataWatcher.Serializer booleanserializer; private List<ItemStack> equipment = new ArrayList<>(); private boolean removed; public List<ItemStack> getEquipment() { return this.equipment; }
/*  39 */   public WrappedDataWatcher.Serializer getStringserializer() { return this.stringserializer; }
/*  40 */   public WrappedDataWatcher.Serializer getBooleanserializer() { return this.booleanserializer; } public boolean isRemoved() {
/*  41 */     return this.removed;
/*     */   }
/*     */   public HolographicLine(Location loc, String title) {
/*  44 */     this.location = loc.clone();
/*  45 */     this.title = title;
/*  46 */     this.removed = false;
/*     */   }
/*     */   
/*     */   public List<Player> getPlayers() {
/*  50 */     List<Player> list = new ArrayList<>();
/*  51 */     this.packets.keySet().forEach(uuid -> {
/*     */           Player player = Bukkit.getPlayer(uuid);
/*     */           
/*     */           if (player != null && player.isOnline()) {
/*     */             list.add(player);
/*     */           }
/*     */         });
/*  58 */     return list;
/*     */   }
/*     */   
/*     */   public void setEquipment(List<ItemStack> equipment) {
/*  62 */     if (!this.removed) {
/*  63 */       this.equipment = new ArrayList<>();
/*  64 */       this.equipment.addAll(equipment);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getEntityId(Player p) {
/*  69 */     return ((Integer)this.ids.getOrDefault(p.getUniqueId(), Integer.valueOf(0))).intValue();
/*     */   }
/*     */   
/*     */   public Location getLocation() {
/*  73 */     return this.location.clone();
/*     */   }
/*     */   
/*     */   public void setTitle(String title) {
/*     */     try {
/*  78 */       if (!this.removed)
/*  79 */         if (this.title == null) {
/*  80 */           this.title = title;
/*     */           
/*  82 */           for (UUID uuid : this.packets.keySet()) {
/*  83 */             Player player = Bukkit.getPlayer(uuid);
/*  84 */             if (player != null && player.isOnline() && this.players.contains(uuid)) {
/*  85 */               display(player);
/*     */             }
/*     */           } 
/*     */         } else {
/*  89 */           this.title = title;
/*     */           
/*  91 */           for (UUID uuid : this.packets.keySet()) {
/*  92 */             Player player = Bukkit.getPlayer(uuid);
/*  93 */             if (player != null && player.isOnline()) {
/*  94 */               Location loc = player.getLocation().clone();
/*  95 */               loc.setY(this.location.getY());
/*  96 */               if (loc.getWorld().getName().equals(this.location.getWorld().getName()) && loc.distance(this.location) < 64.0D && player != null && player.isOnline() && this.players.contains(uuid)) {
/*  97 */                 ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
/*  98 */                 PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.ENTITY_METADATA);
/*  99 */                 packet.getIntegers().write(0, this.ids.get(uuid));
/* 100 */                 packet.getWatchableCollectionModifier().write(0, Collections.singletonList(new WrappedWatchableObject(2, title)));
/*     */                 
/* 102 */                 protocolManager.sendServerPacket(player, packet);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         }  
/*     */     } catch (Throwable $ex) {
/*     */       throw $ex;
/*     */     } 
/*     */   }
/*     */   public void display(Player player) {
/* 112 */     if (!this.removed && 
/* 113 */       player != null) {
/* 114 */       if (this.packets.containsKey(player.getUniqueId())) {
/* 115 */         BukkitReflection.sendPacket(player, this.packets.get(player.getUniqueId()));
/*     */       }
/*     */       
/* 118 */       Object packet = getPacket(this.location);
/* 119 */       Object destroyPacket = null;
/* 120 */       BukkitReflection.sendPacket(player, packet);
/*     */       
/*     */       try {
/* 123 */         Field declaredField = ((Class)Objects.<Class<?>>requireNonNull(BukkitReflection.getNMSClass("PacketPlayOutSpawnEntityLiving"))).getDeclaredField("a");
/* 124 */         declaredField.setAccessible(true);
/* 125 */         this.ids.put(player.getUniqueId(), (Integer)declaredField.get(packet));
/* 126 */         destroyPacket = getDestroyPacket(new int[] { ((Integer)declaredField.get(packet)).intValue() });
/* 127 */       } catch (Exception exception) {}
/*     */ 
/*     */       
/* 130 */       if (this.equipment.size() > 0) {
/*     */         try {
/* 132 */           Field declaredField = ((Class)Objects.<Class<?>>requireNonNull(BukkitReflection.getNMSClass("PacketPlayOutSpawnEntityLiving"))).getDeclaredField("a");
/* 133 */           declaredField.setAccessible(true);
/* 134 */           int j = 5;
/*     */           
/* 136 */           for (ItemStack itemStack : this.equipment) {
/* 137 */             j--;
/* 138 */             if (j < 0) {
/*     */               break;
/*     */             }
/*     */             
/* 142 */             PacketContainer equipmentPacket = getEquipmentPacket(((Integer)declaredField.get(packet)).intValue(), j, itemStack);
/* 143 */             ProtocolLibrary.getProtocolManager().sendServerPacket(player, equipmentPacket);
/*     */           } 
/* 145 */         } catch (Exception e) {
/* 146 */           e.printStackTrace();
/*     */         } 
/*     */       }
/*     */       
/* 150 */       this.packets.put(player.getUniqueId(), destroyPacket);
/* 151 */       this.players.add(player.getUniqueId());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void destroy(Player player) {
/* 157 */     if (!this.removed && 
/* 158 */       player != null && this.packets.containsKey(player.getUniqueId())) {
/* 159 */       BukkitReflection.sendPacket(player, this.packets.get(player.getUniqueId()));
/* 160 */       this.ids.remove(player.getUniqueId());
/* 161 */       this.packets.remove(player.getUniqueId());
/* 162 */       this.players.remove(player.getUniqueId());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void redisplay() {
/* 169 */     if (!this.removed) {
/* 170 */       this.packets.keySet().forEach(uuid -> {
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
/* 181 */     if (!this.removed) {
/* 182 */       for (UUID uuid : this.packets.keySet()) {
/* 183 */         Player player = Bukkit.getPlayer(uuid);
/* 184 */         if (player != null && player.isOnline()) {
/* 185 */           BukkitReflection.sendPacket(player, this.packets.get(uuid));
/*     */         }
/*     */       } 
/*     */       
/* 189 */       this.ids = new HashMap<>();
/* 190 */       this.packets = new HashMap<>();
/* 191 */       this.players = new ArrayList<>();
/* 192 */       this.removed = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void teleport(Location loc) {
/* 197 */     if (!this.removed && 
/* 198 */       loc.getWorld().getName().equals(this.location.getWorld().getName())) {
/* 199 */       for (UUID uuid : this.packets.keySet()) {
/* 200 */         Player player = Bukkit.getPlayer(uuid);
/* 201 */         if (player != null && player.isOnline() && this.players.contains(uuid)) {
/* 202 */           sendTeleportPacket(((Integer)this.ids.get(uuid)).intValue(), loc, player);
/*     */         }
/*     */       } 
/*     */       
/* 206 */       this.location = loc.clone();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void sendTeleportPacket(int id, Location loc, Player player) {
/*     */     try {
/* 213 */       Constructor constructor = BukkitReflection.getNMSClass("PacketPlayOutEntityTeleport").getConstructor(new Class[] { int.class, int.class, int.class, int.class, byte.class, byte.class, boolean.class });
/* 214 */       Method method = ((Class)Objects.<Class<?>>requireNonNull(BukkitReflection.getNMSClass("MathHelper"))).getMethod("floor", new Class[] { double.class });
/* 215 */       Object packet = constructor.newInstance(new Object[] { Integer.valueOf(id), method.invoke(null, new Object[] { Double.valueOf(this.location.getX() * 32.0D) }), method.invoke(null, new Object[] { Double.valueOf(this.location.getY() * 32.0D) }), method.invoke(null, new Object[] { Double.valueOf(this.location.getZ() * 32.0D) }), Byte.valueOf((byte)(int)(this.location.getYaw() * 256.0F / 360.0F)), Byte.valueOf((byte)(int)(this.location.getPitch() * 256.0F / 360.0F)), Boolean.valueOf(true) });
/* 216 */       BukkitReflection.sendPacket(player, packet);
/* 217 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Object getPacket(Location location) {
/*     */     try {
/* 224 */       Object cast = ((Class)Objects.<Class<?>>requireNonNull(BukkitReflection.getClass("CraftWorld"))).cast(location.getWorld());
/* 225 */       Object instance = ((Class)Objects.<Class<?>>requireNonNull(BukkitReflection.getNMSClass("EntityArmorStand"))).getConstructor(new Class[] { BukkitReflection.getNMSClass("World") }).newInstance(new Object[] { cast.getClass().getMethod("getHandle", new Class[0]).invoke(cast, new Object[0]) });
/* 226 */       if (this.title != null) {
/* 227 */         instance.getClass().getMethod("setCustomName", new Class[] { String.class }).invoke(instance, new Object[] { this.title });
/* 228 */         ((Class)Objects.<Class<?>>requireNonNull(BukkitReflection.getNMSClass("Entity"))).getMethod("setCustomNameVisible", new Class[] { boolean.class }).invoke(instance, new Object[] { Boolean.valueOf(true) });
/*     */       } 
/*     */       
/*     */       try {
/* 232 */         instance.getClass().getMethod("setGravity", new Class[] { boolean.class }).invoke(instance, new Object[] { Boolean.valueOf(false) });
/* 233 */       } catch (Exception var5) {
/* 234 */         instance.getClass().getMethod("setNoGravity", new Class[] { boolean.class }).invoke(instance, new Object[] { Boolean.valueOf(true) });
/*     */       } 
/*     */       
/* 237 */       instance.getClass().getMethod("setLocation", new Class[] { double.class, double.class, double.class, float.class, float.class }).invoke(instance, new Object[] { Double.valueOf(location.getX()), Double.valueOf(location.getY()), Double.valueOf(location.getZ()), Float.valueOf(0.0F), Float.valueOf(0.0F) });
/* 238 */       instance.getClass().getMethod("setBasePlate", new Class[] { boolean.class }).invoke(instance, new Object[] { Boolean.valueOf(false) });
/* 239 */       instance.getClass().getMethod("setInvisible", new Class[] { boolean.class }).invoke(instance, new Object[] { Boolean.valueOf(true) });
/* 240 */       return ((Class)Objects.<Class<?>>requireNonNull(BukkitReflection.getNMSClass("PacketPlayOutSpawnEntityLiving"))).getConstructor(new Class[] { BukkitReflection.getNMSClass("EntityLiving") }).newInstance(new Object[] { instance });
/* 241 */     } catch (Exception var6) {
/* 242 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private PacketContainer getEquipmentPacket(int id, int slot, ItemStack item) throws Exception {
/* 247 */     PacketContainer packet = new PacketContainer(PacketType.Play.Server.ENTITY_EQUIPMENT);
/* 248 */     packet.getIntegers().write(0, Integer.valueOf(id));
/* 249 */     slot = Math.min(slot, 4);
/* 250 */     slot = Math.max(slot, 0);
/* 251 */     if (packet.getIntegers().size() > 1) {
/* 252 */       packet.getIntegers().write(1, Integer.valueOf(slot));
/*     */     } else {
/* 254 */       switch (slot) {
/*     */         case 0:
/* 256 */           packet.getItemSlots().write(0, EnumWrappers.ItemSlot.MAINHAND);
/*     */           break;
/*     */         case 1:
/* 259 */           packet.getItemSlots().write(0, EnumWrappers.ItemSlot.FEET);
/*     */           break;
/*     */         case 2:
/* 262 */           packet.getItemSlots().write(0, EnumWrappers.ItemSlot.LEGS);
/*     */           break;
/*     */         case 3:
/* 265 */           packet.getItemSlots().write(0, EnumWrappers.ItemSlot.CHEST);
/*     */           break;
/*     */         case 4:
/* 268 */           packet.getItemSlots().write(0, EnumWrappers.ItemSlot.HEAD);
/*     */           break;
/*     */       } 
/*     */     } 
/* 272 */     packet.getItemModifier().write(0, item);
/* 273 */     return packet;
/*     */   }
/*     */   
/*     */   private Object getDestroyPacket(int... array) throws Exception {
/*     */     try {
/* 278 */       return ((Class)Objects.<Class<?>>requireNonNull(BukkitReflection.getNMSClass("PacketPlayOutEntityDestroy"))).getConstructor(new Class[] { int[].class }).newInstance(new Object[] { array });
/* 279 */     } catch (Exception var3) {
/* 280 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void sendPacket(Player p, Packet<?> packet) {
/* 285 */     (((CraftPlayer)p).getHandle()).playerConnection.sendPacket(packet);
/*     */   }
/*     */ }