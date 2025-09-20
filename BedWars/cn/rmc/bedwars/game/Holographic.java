/*     */ package cn.rmc.bedwars.game;
/*     */ import com.comphenix.protocol.PacketType;
/*     */ import com.comphenix.protocol.ProtocolLibrary;
/*     */ import com.comphenix.protocol.ProtocolManager;
/*     */ import com.comphenix.protocol.events.PacketContainer;
/*     */ import com.comphenix.protocol.wrappers.EnumWrappers;
/*     */ import com.comphenix.protocol.wrappers.WrappedDataWatcher;
/*     */ import com.comphenix.protocol.wrappers.WrappedWatchableObject;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import cn.rmc.bedwars.BedWars;
/*     */ import cn.rmc.bedwars.utils.BukkitReflection;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ import org.bukkit.scheduler.BukkitTask;
/*     */ 
/*     */ public class Holographic {
/*     */   private Map<UUID, Integer> ids;
/*     */   private Map<UUID, Object> packets;
/*     */   private List<UUID> players;
/*     */   private Location location;
/*     */   private String title;
/*     */   private BukkitTask task;
/*     */   private List<ItemStack> equipment;
/*     */   private WrappedDataWatcher.Serializer stringserializer;
/*     */   private WrappedDataWatcher.Serializer booleanserializer;
/*     */   private boolean removed;
/*  40 */   private HashMap<Holographic, Location> armorloc = new HashMap<>();
/*  41 */   private HashMap<Holographic, Boolean> armorupward = new HashMap<>();
/*  42 */   private HashMap<Holographic, Integer> armoralgebra = new HashMap<>();
/*     */   
/*     */   public boolean isRemoved() {
/*  45 */     return this.removed;
/*     */   }
/*     */   
/*     */   public Holographic(Location loc, String title) {
/*  49 */     this.ids = new HashMap<>();
/*  50 */     this.packets = new HashMap<>();
/*  51 */     this.players = new ArrayList<>();
/*  52 */     this.equipment = new ArrayList<>();
/*  53 */     this.location = loc.clone();
/*  54 */     this.title = title;
/*  55 */     this.removed = false;
/*  56 */     this
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  85 */       .task = (new BukkitRunnable() { public void run() { List<UUID> list = new ArrayList<>(); for (UUID uuid : Holographic.this.packets.keySet()) { Player player = Bukkit.getPlayer(uuid); if (player == null || !player.isOnline()) { list.add(uuid); continue; }  Location loc2 = player.getLocation().clone(); loc2.setY(Holographic.this.location.getY()); if (Holographic.this.players.contains(uuid)) { if (!loc2.getWorld().getName().equals(Holographic.this.location.getWorld().getName()) || loc2.distance(Holographic.this.location) >= 64.0D) { BukkitReflection.sendPacket(player, Holographic.this.packets.get(player.getUniqueId())); Holographic.this.players.remove(uuid); }  continue; }  if (loc2.getWorld().getName().equals(Holographic.this.location.getWorld().getName()) && loc2.distance(Holographic.this.location) < 64.0D) Holographic.this.display(player);  }  for (UUID uuid : list) { Holographic.this.ids.remove(uuid); Holographic.this.packets.remove(uuid); Holographic.this.players.remove(uuid); }  } }).runTaskTimer((Plugin)BedWars.getInstance(), 1L, 1L);
/*  86 */     BedWars.getInstance().getHolographicManager().addHolographic(this);
/*     */   }
/*     */   
/*     */   public List<Player> getPlayers() {
/*  90 */     List<Player> list = new ArrayList<>();
/*  91 */     this.packets.keySet().forEach(uuid -> {
/*     */           Player player = Bukkit.getPlayer(uuid);
/*     */           if (player != null && player.isOnline()) {
/*     */             list.add(player);
/*     */           }
/*     */         });
/*  97 */     return list;
/*     */   }
/*     */   
/*     */   public void setEquipment(List<ItemStack> equipment) {
/* 101 */     if (this.removed) {
/*     */       return;
/*     */     }
/* 104 */     this.equipment = new ArrayList<>();
/* 105 */     this.equipment.addAll(equipment);
/*     */   }
/*     */   
/*     */   public Location getLocation() {
/* 109 */     return this.location.clone();
/*     */   }
/*     */   
/*     */   public String getTitle() {
/* 113 */     return this.title;
/*     */   }
/*     */   
/*     */   public void setTitle(String title) {
/* 117 */     if (this.removed) {
/*     */       return;
/*     */     }
/* 120 */     if (this.title == null) {
/* 121 */       this.title = title;
/* 122 */       for (UUID uuid : this.packets.keySet()) {
/* 123 */         Player player = Bukkit.getPlayer(uuid);
/* 124 */         if (player != null && player.isOnline() && this.players.contains(uuid)) {
/* 125 */           display(player);
/*     */         }
/*     */       } 
/*     */     } else {
/* 129 */       this.title = title;
/* 130 */       for (UUID uuid : this.packets.keySet()) {
/* 131 */         Player player = Bukkit.getPlayer(uuid);
/* 132 */         if (player != null && player.isOnline()) {
/* 133 */           Location loc = player.getLocation().clone();
/* 134 */           loc.setY(this.location.getY());
/* 135 */           if (loc.getWorld().getName().equals(this.location.getWorld().getName()) && loc.distance(this.location) < 64.0D && player != null && player
/* 136 */             .isOnline() && this.players.contains(uuid)) {
/* 137 */             ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
/* 138 */             PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.ENTITY_METADATA);
/* 139 */             packet.getIntegers().write(0, this.ids.get(uuid));
/* 140 */             packet.getWatchableCollectionModifier().write(0, 
/* 141 */                 Arrays.asList(new WrappedWatchableObject[] { new WrappedWatchableObject(2, title) }));
/*     */             try {
/* 143 */               protocolManager.sendServerPacket(player, packet);
/* 144 */             } catch (InvocationTargetException e) {
/* 145 */               e.printStackTrace();
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void display(Player player) {
/* 154 */     if (this.removed) {
/*     */       return;
/*     */     }
/* 157 */     if (player == null || this.task == null) {
/*     */       return;
/*     */     }
/* 160 */     if (this.packets.containsKey(player.getUniqueId())) {
/* 161 */       BukkitReflection.sendPacket(player, this.packets.get(player.getUniqueId()));
/*     */     }
/* 163 */     Object packet = getPacket(this.location);
/* 164 */     Object destroyPacket = null;
/* 165 */     BukkitReflection.sendPacket(player, packet);
/*     */     try {
/* 167 */       Field declaredField = BukkitReflection.getNMSClass("PacketPlayOutSpawnEntityLiving").getDeclaredField("a");
/* 168 */       declaredField.setAccessible(true);
/* 169 */       this.ids.put(player.getUniqueId(), Integer.valueOf(((Integer)declaredField.get(packet)).intValue()));
/* 170 */       destroyPacket = getDestroyPacket(new int[] { ((Integer)declaredField.get(packet)).intValue() });
/* 171 */     } catch (Exception exception) {}
/*     */     
/* 173 */     if (this.equipment.size() > 0) {
/*     */       try {
/* 175 */         Field declaredField = BukkitReflection.getNMSClass("PacketPlayOutSpawnEntityLiving").getDeclaredField("a");
/* 176 */         declaredField.setAccessible(true);
/* 177 */         int j = 5;
/* 178 */         for (ItemStack itemStack : this.equipment) {
/* 179 */           j--;
/* 180 */           if (j < 0) {
/*     */             break;
/*     */           }
/* 183 */           PacketContainer equipmentPacket = getEquipmentPacket(((Integer)declaredField.get(packet)).intValue(), j, itemStack);
/*     */           
/* 185 */           ProtocolLibrary.getProtocolManager().sendServerPacket(player, equipmentPacket);
/*     */         } 
/* 187 */       } catch (Exception e) {
/* 188 */         e.printStackTrace();
/*     */       } 
/*     */     }
/* 191 */     this.packets.put(player.getUniqueId(), destroyPacket);
/* 192 */     this.players.add(player.getUniqueId());
/*     */   }
/*     */   
/*     */   public void destroy(Player player) {
/* 196 */     if (this.removed) {
/*     */       return;
/*     */     }
/* 199 */     if (player != null && this.packets.containsKey(player.getUniqueId())) {
/* 200 */       BukkitReflection.sendPacket(player, this.packets.get(player.getUniqueId()));
/* 201 */       this.ids.remove(player.getUniqueId());
/* 202 */       this.packets.remove(player.getUniqueId());
/* 203 */       this.players.remove(player.getUniqueId());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void redisplay() {
/* 208 */     if (this.removed) {
/*     */       return;
/*     */     }
/* 211 */     this.packets.keySet().forEach(uuid -> {
/*     */           Player player = Bukkit.getPlayer(uuid);
/*     */           if (player != null && player.isOnline()) {
/*     */             display(player);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void remove() {
/* 220 */     if (this.removed) {
/*     */       return;
/*     */     }
/* 223 */     this.task.cancel();
/* 224 */     for (UUID uuid : this.packets.keySet()) {
/* 225 */       Player player = Bukkit.getPlayer(uuid);
/* 226 */       if (player != null && player.isOnline()) {
/* 227 */         BukkitReflection.sendPacket(player, this.packets.get(uuid));
/*     */       }
/*     */     } 
/* 230 */     this.ids = new HashMap<>();
/* 231 */     this.packets = new HashMap<>();
/* 232 */     this.players = new ArrayList<>();
/* 233 */     this.removed = true;
/* 234 */     BedWars.getInstance().getHolographicManager().removeHolographic(this);
/*     */   }
/*     */   
/*     */   public void teleport(Location loc) {
/* 238 */     if (this.removed) {
/*     */       return;
/*     */     }
/* 241 */     if (!loc.getWorld().getName().equals(this.location.getWorld().getName())) {
/*     */       return;
/*     */     }
/* 244 */     for (UUID uuid : this.packets.keySet()) {
/* 245 */       Player player = Bukkit.getPlayer(uuid);
/* 246 */       if (player != null && player.isOnline() && this.players.contains(uuid)) {
/* 247 */         sendTeleportPacket(((Integer)this.ids.get(uuid)).intValue(), loc, player);
/*     */       }
/*     */     } 
/* 250 */     this.location = loc.clone();
/*     */   }
/*     */ 
/*     */   
/*     */   private void sendTeleportPacket(int id, Location loc, Player player) {
/*     */     try {
/* 256 */       Constructor constructor = BukkitReflection.getNMSClass("PacketPlayOutEntityTeleport").getConstructor(new Class[] { int.class, int.class, int.class, int.class, byte.class, byte.class, boolean.class });
/*     */       
/* 258 */       Method method = BukkitReflection.getNMSClass("MathHelper").getMethod("floor", new Class[] { double.class });
/* 259 */       Object packet = constructor.newInstance(new Object[] { Integer.valueOf(id), method.invoke(null, new Object[] { Double.valueOf(this.location.getX() * 32.0D) }), method
/* 260 */             .invoke(null, new Object[] { Double.valueOf(this.location.getY() * 32.0D) }), method.invoke(null, new Object[] { Double.valueOf(this.location.getZ() * 32.0D)
/* 261 */               }), Byte.valueOf((byte)(int)(this.location.getYaw() * 256.0F / 360.0F)), Byte.valueOf((byte)(int)(this.location.getPitch() * 256.0F / 360.0F)), 
/* 262 */             Boolean.valueOf(true) });
/* 263 */       BukkitReflection.sendPacket(player, packet);
/* 264 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */   
/*     */   private Object getPacket(Location location) {
/*     */     try {
/* 270 */       Object cast = BukkitReflection.getClass("CraftWorld").cast(location.getWorld());
/*     */       
/* 272 */       Object instance = BukkitReflection.getNMSClass("EntityArmorStand").getConstructor(new Class[] { BukkitReflection.getNMSClass("World") }).newInstance(new Object[] { cast.getClass().getMethod("getHandle", new Class[0]).invoke(cast, new Object[0]) });
/*     */       
/* 274 */       if (this.title != null) {
/* 275 */         instance.getClass().getMethod("setCustomName", new Class[] { String.class }).invoke(instance, new Object[] { this.title });
/* 276 */         BukkitReflection.getNMSClass("Entity").getMethod("setCustomNameVisible", new Class[] { boolean.class }).invoke(instance, new Object[] { Boolean.valueOf(true) });
/*     */       } 
/*     */       try {
/* 279 */         instance.getClass().getMethod("setGravity", new Class[] { boolean.class }).invoke(instance, new Object[] { Boolean.valueOf(false) });
/* 280 */       } catch (Exception ex) {
/* 281 */         instance.getClass().getMethod("setNoGravity", new Class[] { boolean.class }).invoke(instance, new Object[] { Boolean.valueOf(true) });
/*     */       } 
/* 283 */       instance.getClass().getMethod("setLocation", new Class[] { double.class, double.class, double.class, float.class, float.class
/* 284 */           }).invoke(instance, new Object[] { Double.valueOf(location.getX()), Double.valueOf(location.getY()), Double.valueOf(location.getZ()), Float.valueOf(0.0F), Float.valueOf(0.0F) });
/* 285 */       instance.getClass().getMethod("setBasePlate", new Class[] { boolean.class }).invoke(instance, new Object[] { Boolean.valueOf(false) });
/* 286 */       instance.getClass().getMethod("setInvisible", new Class[] { boolean.class }).invoke(instance, new Object[] { Boolean.valueOf(true) });
/* 287 */       return BukkitReflection.getNMSClass("PacketPlayOutSpawnEntityLiving").getConstructor(new Class[] { BukkitReflection.getNMSClass("EntityLiving")
/* 288 */           }).newInstance(new Object[] { instance });
/* 289 */     } catch (Exception e) {
/* 290 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private PacketContainer getEquipmentPacket(int id, int slot, ItemStack item) throws Exception {
/* 295 */     PacketContainer packet = new PacketContainer(PacketType.Play.Server.ENTITY_EQUIPMENT);
/* 296 */     packet.getIntegers().write(0, Integer.valueOf(id));
/* 297 */     slot = (slot > 4) ? 4 : slot;
/* 298 */     slot = (slot < 0) ? 0 : slot;
/* 299 */     if (packet.getIntegers().size() > 1) {
/* 300 */       packet.getIntegers().write(1, Integer.valueOf(slot));
/*     */     } else {
/* 302 */       switch (slot) {
/*     */         case 1:
/* 304 */           packet.getItemSlots().write(0, EnumWrappers.ItemSlot.FEET);
/*     */           break;
/*     */         case 2:
/* 307 */           packet.getItemSlots().write(0, EnumWrappers.ItemSlot.LEGS);
/*     */           break;
/*     */         case 3:
/* 310 */           packet.getItemSlots().write(0, EnumWrappers.ItemSlot.CHEST);
/*     */           break;
/*     */         case 4:
/* 313 */           packet.getItemSlots().write(0, EnumWrappers.ItemSlot.HEAD);
/*     */           break;
/*     */         case 0:
/* 316 */           packet.getItemSlots().write(0, EnumWrappers.ItemSlot.MAINHAND);
/*     */           break;
/*     */       } 
/*     */ 
/*     */     
/*     */     } 
/* 322 */     packet.getItemModifier().write(0, item);
/* 323 */     return packet;
/*     */   }
/*     */   
/*     */   private Object getDestroyPacket(int... array) throws Exception {
/*     */     try {
/* 328 */       return BukkitReflection.getNMSClass("PacketPlayOutEntityDestroy").getConstructor(new Class[] { int[].class }).newInstance(new Object[] { array });
/* 329 */     } catch (Exception e) {
/* 330 */       return null;
/*     */     } 
/*     */   }
/*     */   public void moveArmorStand(double height) {
/* 334 */     if (!this.armorloc.containsKey(this)) {
/* 335 */       this.armorloc.put(this, getLocation().clone());
/*     */     }
/* 337 */     if (!this.armorupward.containsKey(this)) {
/* 338 */       this.armorupward.put(this, Boolean.valueOf(true));
/*     */     }
/* 340 */     if (!this.armoralgebra.containsKey(this)) {
/* 341 */       this.armoralgebra.put(this, Integer.valueOf(0));
/*     */     }
/* 343 */     this.armoralgebra.put(this, Integer.valueOf(((Integer)this.armoralgebra.get(this)).intValue() + 1));
/* 344 */     Location location = this.armorloc.get(this);
/* 345 */     if (location.getY() >= height + 0.3D) {
/* 346 */       this.armoralgebra.put(this, Integer.valueOf(0));
/* 347 */       this.armorupward.put(this, Boolean.valueOf(false));
/* 348 */     } else if (location.getY() <= height - 0.3D) {
/* 349 */       this.armoralgebra.put(this, Integer.valueOf(0));
/* 350 */       this.armorupward.put(this, Boolean.valueOf(true));
/*     */     } 
/* 352 */     Integer algebra = this.armoralgebra.get(this);
/* 353 */     if (39 > algebra.intValue()) {
/* 354 */       if (((Boolean)this.armorupward.get(this)).booleanValue()) {
/* 355 */         location.setY(location.getY() + 0.015D);
/*     */       } else {
/* 357 */         location.setY(location.getY() - 0.015D);
/*     */       } 
/* 359 */     } else if (algebra.intValue() >= 50) {
/* 360 */       this.armoralgebra.put(this, Integer.valueOf(0));
/* 361 */       this.armorupward.put(this, Boolean.valueOf(!((Boolean)this.armorupward.get(this)).booleanValue()));
/*     */     } 
/* 363 */     Float turn = Float.valueOf(1.0F);
/* 364 */     if (!((Boolean)this.armorupward.get(this)).booleanValue()) {
/* 365 */       turn = Float.valueOf(-turn.floatValue());
/*     */     }
/* 367 */     Float changeyaw = Float.valueOf(0.0F);
/* 368 */     if (algebra.intValue() == 1 || algebra.intValue() == 40) {
/* 369 */       changeyaw = Float.valueOf(changeyaw.floatValue() + 2.0F * turn.floatValue());
/* 370 */     } else if (algebra.intValue() == 2 || algebra.intValue() == 39) {
/* 371 */       changeyaw = Float.valueOf(changeyaw.floatValue() + 3.0F * turn.floatValue());
/* 372 */     } else if (algebra.intValue() == 3 || algebra.intValue() == 38) {
/* 373 */       changeyaw = Float.valueOf(changeyaw.floatValue() + 4.0F * turn.floatValue());
/* 374 */     } else if (algebra.intValue() == 4 || algebra.intValue() == 37) {
/* 375 */       changeyaw = Float.valueOf(changeyaw.floatValue() + 5.0F * turn.floatValue());
/* 376 */     } else if (algebra.intValue() == 5 || algebra.intValue() == 36) {
/* 377 */       changeyaw = Float.valueOf(changeyaw.floatValue() + 6.0F * turn.floatValue());
/* 378 */     } else if (algebra.intValue() == 6 || algebra.intValue() == 35) {
/* 379 */       changeyaw = Float.valueOf(changeyaw.floatValue() + 7.0F * turn.floatValue());
/* 380 */     } else if (algebra.intValue() == 7 || algebra.intValue() == 34) {
/* 381 */       changeyaw = Float.valueOf(changeyaw.floatValue() + 8.0F * turn.floatValue());
/* 382 */     } else if (algebra.intValue() == 8 || algebra.intValue() == 33) {
/* 383 */       changeyaw = Float.valueOf(changeyaw.floatValue() + 9.0F * turn.floatValue());
/* 384 */     } else if (algebra.intValue() == 9 || algebra.intValue() == 32) {
/* 385 */       changeyaw = Float.valueOf(changeyaw.floatValue() + 10.0F * turn.floatValue());
/* 386 */     } else if (algebra.intValue() == 10 || algebra.intValue() == 31) {
/* 387 */       changeyaw = Float.valueOf(changeyaw.floatValue() + 11.0F * turn.floatValue());
/* 388 */     } else if (algebra.intValue() == 11 || algebra.intValue() == 30) {
/* 389 */       changeyaw = Float.valueOf(changeyaw.floatValue() + 11.0F * turn.floatValue());
/* 390 */     } else if (algebra.intValue() == 12 || algebra.intValue() == 29) {
/* 391 */       changeyaw = Float.valueOf(changeyaw.floatValue() + 12.0F * turn.floatValue());
/* 392 */     } else if (algebra.intValue() == 13 || algebra.intValue() == 28) {
/* 393 */       changeyaw = Float.valueOf(changeyaw.floatValue() + 12.0F * turn.floatValue());
/* 394 */     } else if (algebra.intValue() == 14 || algebra.intValue() == 27) {
/* 395 */       changeyaw = Float.valueOf(changeyaw.floatValue() + 13.0F * turn.floatValue());
/* 396 */     } else if (algebra.intValue() == 15 || algebra.intValue() == 26) {
/* 397 */       changeyaw = Float.valueOf(changeyaw.floatValue() + 13.0F * turn.floatValue());
/* 398 */     } else if (algebra.intValue() == 16 || algebra.intValue() == 25) {
/* 399 */       changeyaw = Float.valueOf(changeyaw.floatValue() + 14.0F * turn.floatValue());
/* 400 */     } else if (algebra.intValue() == 17 || algebra.intValue() == 24) {
/* 401 */       changeyaw = Float.valueOf(changeyaw.floatValue() + 14.0F * turn.floatValue());
/* 402 */     } else if (algebra.intValue() == 18 || algebra.intValue() == 23) {
/* 403 */       changeyaw = Float.valueOf(changeyaw.floatValue() + 15.0F * turn.floatValue());
/* 404 */     } else if (algebra.intValue() == 19 || algebra.intValue() == 22) {
/* 405 */       changeyaw = Float.valueOf(changeyaw.floatValue() + 15.0F * turn.floatValue());
/* 406 */     } else if (algebra.intValue() == 20 || algebra.intValue() == 21) {
/* 407 */       changeyaw = Float.valueOf(changeyaw.floatValue() + 16.0F * turn.floatValue());
/* 408 */     } else if (algebra.intValue() == 41) {
/* 409 */       changeyaw = Float.valueOf(changeyaw.floatValue() + 2.0F * turn.floatValue());
/* 410 */     } else if (algebra.intValue() == 42) {
/* 411 */       changeyaw = Float.valueOf(changeyaw.floatValue() + 2.0F * turn.floatValue());
/* 412 */     } else if (algebra.intValue() == 43) {
/* 413 */       changeyaw = Float.valueOf(changeyaw.floatValue() + 2.0F * turn.floatValue());
/* 414 */     } else if (algebra.intValue() == 44) {
/* 415 */       changeyaw = Float.valueOf(changeyaw.floatValue() + 1.0F * turn.floatValue());
/* 416 */     } else if (algebra.intValue() == 45) {
/* 417 */       changeyaw = Float.valueOf(changeyaw.floatValue() + -1.0F * turn.floatValue());
/* 418 */     } else if (algebra.intValue() == 46) {
/* 419 */       changeyaw = Float.valueOf(changeyaw.floatValue() + -1.0F * turn.floatValue());
/* 420 */     } else if (algebra.intValue() == 47) {
/* 421 */       changeyaw = Float.valueOf(changeyaw.floatValue() + -2.0F * turn.floatValue());
/* 422 */     } else if (algebra.intValue() == 48) {
/* 423 */       changeyaw = Float.valueOf(changeyaw.floatValue() + -2.0F * turn.floatValue());
/* 424 */     } else if (algebra.intValue() == 49) {
/* 425 */       changeyaw = Float.valueOf(changeyaw.floatValue() + -2.0F * turn.floatValue());
/* 426 */     } else if (algebra.intValue() == 50) {
/* 427 */       changeyaw = Float.valueOf(changeyaw.floatValue() + -2.0F * turn.floatValue());
/*     */     } 
/* 429 */     double yaw = location.getYaw();
/* 430 */     yaw += changeyaw.floatValue() * 1.0D;
/* 431 */     yaw = (yaw > 360.0D) ? (yaw - 360.0D) : yaw;
/* 432 */     yaw = (yaw < -360.0D) ? (yaw + 360.0D) : yaw;
/* 433 */     location.setYaw((float)yaw);
/* 434 */     teleport(location);
/*     */   }
/*     */ }