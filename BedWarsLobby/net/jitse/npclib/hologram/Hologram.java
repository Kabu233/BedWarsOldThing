/*     */ package net.jitse.npclib.hologram;
/*     */ 
/*     */ import com.comphenix.tinyprotocol.Reflection;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import net.jitse.npclib.internal.MinecraftVersion;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.entity.ArmorStand;
/*     */ import org.bukkit.entity.Player;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Hologram
/*     */ {
/*     */   private static final double DELTA = 0.3D;
/*  24 */   private static final Class<?> CHAT_BASE_COMPONENT_CLASS = Reflection.getClass("{nms}.IChatBaseComponent", new String[] { "net.minecraft.network.chat.IChatBaseComponent" });
/*  25 */   private static final Class<?> ENTITY_ARMOR_STAND_CLASS = Reflection.getClass("{nms}.EntityArmorStand", new String[] { "net.minecraft.world.entity.decoration.EntityArmorStand" });
/*  26 */   private static final Class<?> ENTITY_LIVING_CLASS = Reflection.getClass("{nms}.EntityLiving", new String[] { "net.minecraft.world.entity.EntityLiving" });
/*  27 */   private static final Class<?> ENTITY_CLASS = Reflection.getClass("{nms}.Entity", new String[] { "net.minecraft.world.entity.Entity" });
/*  28 */   private static final Class<?> CRAFT_WORLD_CLASS = Reflection.getCraftBukkitClass("CraftWorld");
/*  29 */   private static final Class<?> CRAFT_PLAYER_CLASS = Reflection.getCraftBukkitClass("entity.CraftPlayer");
/*     */   
/*  31 */   private static final Class<?> BUKKIT_ENTITY_ARMOR_STAND_CLASS = Reflection.getClass("org.bukkit.entity.ArmorStand");
/*     */   
/*  33 */   private static final Class<?> PACKET_PLAY_OUT_SPAWN_ENTITY_LIVING_CLASS = Reflection.getClass("{nms}.PacketPlayOutSpawnEntityLiving", new String[] { "net.minecraft.network.protocol.game.PacketPlayOutSpawnEntityLiving", "net.minecraft.network.protocol.game.PacketPlayOutSpawnEntity" });
/*     */   
/*  35 */   private static final Class<?> PACKET_PLAY_OUT_ENTITY_DESTROY_CLASS = Reflection.getClass("{nms}.PacketPlayOutEntityDestroy", new String[] { "net.minecraft.network.protocol.game.PacketPlayOutEntityDestroy" });
/*     */   
/*  37 */   private static final Class<?> PACKET_PLAY_OUT_ENTITY_METADATA_CLASS = Reflection.getClass("{nms}.PacketPlayOutEntityMetadata", new String[] { "net.minecraft.network.protocol.game.PacketPlayOutEntityMetadata" });
/*     */   
/*  39 */   private static final Class<?> DATAWATCHER_CLASS = Reflection.getClass("{nms}.DataWatcher", new String[] { "net.minecraft.network.syncher.DataWatcher" });
/*  40 */   private static final Class<?> ENTITY_PLAYER_CLASS = Reflection.getClass("{nms}.EntityPlayer", new String[] { "net.minecraft.server.level.EntityPlayer" });
/*  41 */   private static final Class<?> PLAYER_CONNECTION_CLASS = Reflection.getClass("{nms}.PlayerConnection", new String[] { "net.minecraft.server.network.PlayerConnection" });
/*  42 */   private static final Class<?> PACKET_CLASS = Reflection.getClass("{nms}.Packet", new String[] { "net.minecraft.network.protocol.Packet" });
/*     */ 
/*     */ 
/*     */   
/*  46 */   private static final Reflection.ConstructorInvoker PACKET_PLAY_OUT_ENTITY_DESTROY_CONSTRUCTOR = Reflection.getConstructor(PACKET_PLAY_OUT_ENTITY_DESTROY_CLASS, new Class[] { int[].class });
/*     */ 
/*     */   
/*  49 */   private static final Reflection.FieldAccessor<?> PLAYER_CONNECTION_FIELD = Reflection.getField(ENTITY_PLAYER_CLASS, PLAYER_CONNECTION_CLASS, 0);
/*     */ 
/*     */   
/*  52 */   private static final Reflection.MethodInvoker GET_BUKKIT_ENTITY = Reflection.getMethod(ENTITY_ARMOR_STAND_CLASS, "getBukkitEntity", new Class[0]);
/*     */ 
/*     */   
/*  55 */   private static final Reflection.MethodInvoker PLAYER_GET_HANDLE_METHOD = Reflection.getMethod(CRAFT_PLAYER_CLASS, "getHandle", new Class[0]);
/*     */ 
/*     */ 
/*     */   
/*  59 */   private static final Reflection.MethodInvoker GET_DATAWATCHER_METHOD = Reflection.getTypedMethod(ENTITY_CLASS, null, DATAWATCHER_CLASS, new Class[0]);
/*     */ 
/*     */   
/*  62 */   private final List<Object> armorStands = new ArrayList();
/*  63 */   private final List<Object> showPackets = new ArrayList();
/*  64 */   private final List<Object> hidePackets = new ArrayList();
/*  65 */   private final List<Object> metaPackets = new ArrayList();
/*     */   
/*     */   private final Reflection.ConstructorInvoker packetPlayOutSpawnEntityLivingConstructor;
/*     */   
/*     */   private final Reflection.ConstructorInvoker packetPlayOutEntityMetadataConstructor;
/*     */   
/*     */   private final Reflection.MethodInvoker setSmallMethod;
/*     */   
/*     */   private final Reflection.MethodInvoker setLocationMethod;
/*     */   
/*     */   private final Reflection.MethodInvoker setInvisibleMethod;
/*     */   private final Reflection.MethodInvoker setBasePlateMethod;
/*     */   private final Reflection.MethodInvoker setArmsMethod;
/*     */   private final Reflection.MethodInvoker sendPacketMethod;
/*     */   private final Reflection.MethodInvoker getIdMethod;
/*     */   private final Reflection.MethodInvoker setMarkerMethod;
/*     */   private Reflection.MethodInvoker getNonDefaultValuesMethod;
/*     */   private Reflection.MethodInvoker getUUIDMethod;
/*     */   private Object emptyVec3d;
/*     */   private Object armorStandEntityType;
/*  85 */   private Reflection.ConstructorInvoker chatComponentTextConstructor = null;
/*  86 */   private Reflection.MethodInvoker chatComponentFromString = null;
/*     */   
/*     */   private final MinecraftVersion version;
/*     */   
/*     */   private final Location start;
/*     */   private final Object worldServer;
/*     */   private List<String> text;
/*     */   
/*     */   public Hologram(MinecraftVersion version, Location location, List<String> text) {
/*  95 */     this.version = version;
/*  96 */     this.start = location;
/*  97 */     this.text = text;
/*     */     
/*  99 */     this.worldServer = Reflection.getMethod(CRAFT_WORLD_CLASS, "getHandle", new Class[0]).invoke(location.getWorld(), new Object[0]);
/*     */     
/* 101 */     if (version.isAboveOrEqual(MinecraftVersion.V1_21_R4)) {
/* 102 */       this.setMarkerMethod = Reflection.getMethod(ENTITY_ARMOR_STAND_CLASS, "u", new Class[] { boolean.class });
/*     */     }
/* 104 */     else if (version.isAboveOrEqual(MinecraftVersion.V1_21_R1)) {
/* 105 */       this.setMarkerMethod = Reflection.getMethod(ENTITY_ARMOR_STAND_CLASS, "v", new Class[] { boolean.class });
/*     */     }
/* 107 */     else if (version.isAboveOrEqual(MinecraftVersion.V1_18_R1)) {
/* 108 */       this.setMarkerMethod = Reflection.getMethod(ENTITY_ARMOR_STAND_CLASS, "t", new Class[] { boolean.class });
/*     */     }
/* 110 */     else if (version.isAboveOrEqual(MinecraftVersion.V1_8_R3)) {
/* 111 */       this.setMarkerMethod = Reflection.getMethod(BUKKIT_ENTITY_ARMOR_STAND_CLASS, "setMarker", new Class[] { boolean.class });
/*     */     } else {
/*     */       
/* 114 */       throw new IllegalStateException("Could not find setMarker method for EntityArmorStand on version " + version);
/*     */     } 
/*     */ 
/*     */     
/* 118 */     if (version.isAboveOrEqual(MinecraftVersion.V1_21_R4)) {
/* 119 */       Class<?> vec3dClass = Reflection.getClass("net.minecraft.world.phys.Vec3D");
/* 120 */       Reflection.ConstructorInvoker vec3dConstructor = Reflection.getConstructor(vec3dClass, new Class[] { double.class, double.class, double.class });
/*     */       
/* 122 */       this.emptyVec3d = vec3dConstructor.invoke(new Object[] { Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0) });
/* 123 */       Class<?> entityTypesClass = Reflection.getClass("net.minecraft.world.entity.EntityTypes");
/*     */       try {
/* 125 */         this.armorStandEntityType = entityTypesClass.getField("g").get(null);
/* 126 */       } catch (IllegalAccessException|NoSuchFieldException e) {
/* 127 */         Bukkit.broadcastMessage("Could not find ArmorStand entity type for V1_21_R4, expected to be net.minecraft.world.entity.EntityTypes$g");
/*     */         
/* 129 */         throw new RuntimeException(e);
/*     */       } 
/* 131 */       this.getUUIDMethod = Reflection.getTypedMethod(ENTITY_CLASS, "cG", UUID.class, new Class[0]);
/*     */       
/* 133 */       this
/* 134 */         .packetPlayOutSpawnEntityLivingConstructor = Reflection.getConstructor(PACKET_PLAY_OUT_SPAWN_ENTITY_LIVING_CLASS, new Class[] { 
/*     */             int.class, UUID.class, double.class, double.class, double.class, float.class, float.class, entityTypesClass, int.class, vec3dClass, 
/*     */             double.class });
/* 137 */       this
/* 138 */         .packetPlayOutEntityMetadataConstructor = Reflection.getConstructor(PACKET_PLAY_OUT_ENTITY_METADATA_CLASS, new Class[] { int.class, List.class });
/*     */ 
/*     */       
/* 141 */       this.getNonDefaultValuesMethod = Reflection.getMethod(DATAWATCHER_CLASS, "c", new Class[0]);
/* 142 */     } else if (version.isAboveOrEqual(MinecraftVersion.V1_21_R3)) {
/* 143 */       Class<?> vec3dClass = Reflection.getClass("net.minecraft.world.phys.Vec3D");
/* 144 */       Reflection.ConstructorInvoker vec3dConstructor = Reflection.getConstructor(vec3dClass, new Class[] { double.class, double.class, double.class });
/*     */       
/* 146 */       this.emptyVec3d = vec3dConstructor.invoke(new Object[] { Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0) });
/* 147 */       Class<?> entityTypesClass = Reflection.getClass("net.minecraft.world.entity.EntityTypes");
/*     */       try {
/* 149 */         this.armorStandEntityType = entityTypesClass.getField("f").get(null);
/* 150 */       } catch (IllegalAccessException|NoSuchFieldException e) {
/* 151 */         Bukkit.broadcastMessage("Could not find ArmorStand entity type for V1_21_R3, expected to be net.minecraft.world.entity.EntityTypes$g");
/*     */         
/* 153 */         throw new RuntimeException(e);
/*     */       } 
/* 155 */       this.getUUIDMethod = Reflection.getTypedMethod(ENTITY_CLASS, "cG", UUID.class, new Class[0]);
/*     */       
/* 157 */       this
/* 158 */         .packetPlayOutSpawnEntityLivingConstructor = Reflection.getConstructor(PACKET_PLAY_OUT_SPAWN_ENTITY_LIVING_CLASS, new Class[] { 
/*     */             int.class, UUID.class, double.class, double.class, double.class, float.class, float.class, entityTypesClass, int.class, vec3dClass, 
/*     */             double.class });
/* 161 */       this
/* 162 */         .packetPlayOutEntityMetadataConstructor = Reflection.getConstructor(PACKET_PLAY_OUT_ENTITY_METADATA_CLASS, new Class[] { int.class, List.class });
/*     */ 
/*     */       
/* 165 */       this.getNonDefaultValuesMethod = Reflection.getMethod(DATAWATCHER_CLASS, "c", new Class[0]);
/* 166 */     } else if (version.isAboveOrEqual(MinecraftVersion.V1_21_R2)) {
/* 167 */       Class<?> vec3dClass = Reflection.getClass("net.minecraft.world.phys.Vec3D");
/* 168 */       Reflection.ConstructorInvoker vec3dConstructor = Reflection.getConstructor(vec3dClass, new Class[] { double.class, double.class, double.class });
/*     */       
/* 170 */       this.emptyVec3d = vec3dConstructor.invoke(new Object[] { Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0) });
/* 171 */       Class<?> entityTypesClass = Reflection.getClass("net.minecraft.world.entity.EntityTypes");
/*     */       try {
/* 173 */         this.armorStandEntityType = entityTypesClass.getField("f").get(null);
/* 174 */       } catch (IllegalAccessException|NoSuchFieldException e) {
/* 175 */         Bukkit.broadcastMessage("Could not find ArmorStand entity type for V1_21_R2, expected to be net.minecraft.world.entity.EntityTypes$f");
/*     */         
/* 177 */         throw new RuntimeException(e);
/*     */       } 
/* 179 */       this.getUUIDMethod = Reflection.getTypedMethod(ENTITY_CLASS, "cG", UUID.class, new Class[0]);
/*     */       
/* 181 */       this
/* 182 */         .packetPlayOutSpawnEntityLivingConstructor = Reflection.getConstructor(PACKET_PLAY_OUT_SPAWN_ENTITY_LIVING_CLASS, new Class[] { 
/*     */             int.class, UUID.class, double.class, double.class, double.class, float.class, float.class, entityTypesClass, int.class, vec3dClass, 
/*     */             double.class });
/* 185 */       this
/* 186 */         .packetPlayOutEntityMetadataConstructor = Reflection.getConstructor(PACKET_PLAY_OUT_ENTITY_METADATA_CLASS, new Class[] { int.class, List.class });
/*     */ 
/*     */       
/* 189 */       this.getNonDefaultValuesMethod = Reflection.getMethod(DATAWATCHER_CLASS, "c", new Class[0]);
/* 190 */     } else if (version.isAboveOrEqual(MinecraftVersion.V1_21_R1)) {
/* 191 */       Class<?> vec3dClass = Reflection.getClass("net.minecraft.world.phys.Vec3D");
/* 192 */       Reflection.ConstructorInvoker vec3dConstructor = Reflection.getConstructor(vec3dClass, new Class[] { double.class, double.class, double.class });
/*     */       
/* 194 */       this.emptyVec3d = vec3dConstructor.invoke(new Object[] { Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0) });
/* 195 */       Class<?> entityTypesClass = Reflection.getClass("net.minecraft.world.entity.EntityTypes");
/*     */       try {
/* 197 */         this.armorStandEntityType = entityTypesClass.getField("d").get(null);
/* 198 */       } catch (IllegalAccessException|NoSuchFieldException e) {
/* 199 */         Bukkit.broadcastMessage("Could not find ArmorStand entity type for V1_21_R1, expected to be net.minecraft.world.entity.EntityTypes$c");
/*     */         
/* 201 */         throw new RuntimeException(e);
/*     */       } 
/* 203 */       this.getUUIDMethod = Reflection.getTypedMethod(ENTITY_CLASS, "cz", UUID.class, new Class[0]);
/*     */       
/* 205 */       this
/* 206 */         .packetPlayOutSpawnEntityLivingConstructor = Reflection.getConstructor(PACKET_PLAY_OUT_SPAWN_ENTITY_LIVING_CLASS, new Class[] { 
/*     */             int.class, UUID.class, double.class, double.class, double.class, float.class, float.class, entityTypesClass, int.class, vec3dClass, 
/*     */             double.class });
/* 209 */       this
/* 210 */         .packetPlayOutEntityMetadataConstructor = Reflection.getConstructor(PACKET_PLAY_OUT_ENTITY_METADATA_CLASS, new Class[] { int.class, List.class });
/*     */ 
/*     */       
/* 213 */       this.getNonDefaultValuesMethod = Reflection.getMethod(DATAWATCHER_CLASS, "c", new Class[0]);
/* 214 */     } else if (version.isAboveOrEqual(MinecraftVersion.V1_19_R2)) {
/* 215 */       this
/* 216 */         .packetPlayOutSpawnEntityLivingConstructor = Reflection.getConstructor(PACKET_PLAY_OUT_SPAWN_ENTITY_LIVING_CLASS, new Class[] { ENTITY_CLASS });
/* 217 */       this
/* 218 */         .packetPlayOutEntityMetadataConstructor = Reflection.getConstructor(PACKET_PLAY_OUT_ENTITY_METADATA_CLASS, new Class[] { int.class, List.class });
/*     */ 
/*     */       
/* 221 */       this.getNonDefaultValuesMethod = Reflection.getMethod(DATAWATCHER_CLASS, "c", new Class[0]);
/*     */     } else {
/* 223 */       this
/* 224 */         .packetPlayOutSpawnEntityLivingConstructor = Reflection.getConstructor(PACKET_PLAY_OUT_SPAWN_ENTITY_LIVING_CLASS, new Class[] { ENTITY_LIVING_CLASS });
/* 225 */       this
/* 226 */         .packetPlayOutEntityMetadataConstructor = Reflection.getConstructor(PACKET_PLAY_OUT_ENTITY_METADATA_CLASS, new Class[] { int.class, DATAWATCHER_CLASS, boolean.class });
/*     */     } 
/*     */     
/* 229 */     if (version.isAboveOrEqual(MinecraftVersion.V1_19_R1)) {
/* 230 */       this.chatComponentFromString = Reflection.getMethod(
/* 231 */           Reflection.getClass("{obc}.util.CraftChatMessage"), "fromString", new Class[] { String.class });
/*     */     } else {
/* 233 */       this
/* 234 */         .chatComponentTextConstructor = Reflection.getConstructor(Reflection.getClass("{nms}.ChatComponentText", new String[] { "net.minecraft.network.chat.ChatComponentText" }), new Class[] { String.class });
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 239 */     if (version.isAboveOrEqual(MinecraftVersion.V1_21_R4)) {
/* 240 */       this.setLocationMethod = Reflection.getMethod(ENTITY_CLASS, "a_", new Class[] { double.class, double.class, double.class });
/*     */       
/* 242 */       this.setSmallMethod = Reflection.getMethod(ENTITY_ARMOR_STAND_CLASS, "t", new Class[] { boolean.class });
/*     */       
/* 244 */       this.setInvisibleMethod = Reflection.getMethod(ENTITY_ARMOR_STAND_CLASS, "k", new Class[] { boolean.class });
/*     */       
/* 246 */       this.setBasePlateMethod = Reflection.getMethod(ENTITY_ARMOR_STAND_CLASS, "b", new Class[] { boolean.class });
/*     */       
/* 248 */       this.setArmsMethod = Reflection.getMethod(ENTITY_ARMOR_STAND_CLASS, "a", new Class[] { boolean.class });
/*     */       
/* 250 */       this.sendPacketMethod = Reflection.getMethod(PLAYER_CONNECTION_CLASS, "b", new Class[] { PACKET_CLASS });
/*     */       
/* 252 */       this.getIdMethod = Reflection.getMethod(ENTITY_CLASS, "ao", new Class[0]);
/*     */     }
/* 254 */     else if (version.isAboveOrEqual(MinecraftVersion.V1_21_R2)) {
/* 255 */       this.setLocationMethod = Reflection.getMethod(ENTITY_CLASS, "a_", new Class[] { double.class, double.class, double.class });
/*     */       
/* 257 */       this.setSmallMethod = Reflection.getMethod(ENTITY_ARMOR_STAND_CLASS, "u", new Class[] { boolean.class });
/*     */       
/* 259 */       this.setInvisibleMethod = Reflection.getMethod(ENTITY_ARMOR_STAND_CLASS, "k", new Class[] { boolean.class });
/*     */       
/* 261 */       this.setBasePlateMethod = Reflection.getMethod(ENTITY_ARMOR_STAND_CLASS, "b", new Class[] { boolean.class });
/*     */       
/* 263 */       this.setArmsMethod = Reflection.getMethod(ENTITY_ARMOR_STAND_CLASS, "a", new Class[] { boolean.class });
/*     */       
/* 265 */       this.sendPacketMethod = Reflection.getMethod(PLAYER_CONNECTION_CLASS, "b", new Class[] { PACKET_CLASS });
/*     */       
/* 267 */       this.getIdMethod = Reflection.getMethod(ENTITY_CLASS, "ar", new Class[0]);
/*     */     }
/* 269 */     else if (version.isAboveOrEqual(MinecraftVersion.V1_21_R1)) {
/* 270 */       this.setLocationMethod = Reflection.getMethod(ENTITY_CLASS, "a_", new Class[] { double.class, double.class, double.class });
/*     */       
/* 272 */       this.setSmallMethod = Reflection.getMethod(ENTITY_ARMOR_STAND_CLASS, "u", new Class[] { boolean.class });
/*     */       
/* 274 */       this.setInvisibleMethod = Reflection.getMethod(ENTITY_ARMOR_STAND_CLASS, "k", new Class[] { boolean.class });
/*     */       
/* 276 */       this.setBasePlateMethod = Reflection.getMethod(ENTITY_ARMOR_STAND_CLASS, "b", new Class[] { boolean.class });
/*     */       
/* 278 */       this.setArmsMethod = Reflection.getMethod(ENTITY_ARMOR_STAND_CLASS, "a", new Class[] { boolean.class });
/*     */       
/* 280 */       this.sendPacketMethod = Reflection.getMethod(PLAYER_CONNECTION_CLASS, "b", new Class[] { PACKET_CLASS });
/*     */       
/* 282 */       this.getIdMethod = Reflection.getMethod(ENTITY_CLASS, "an", new Class[0]);
/*     */     }
/* 284 */     else if (version.isAboveOrEqual(MinecraftVersion.V1_20_R4)) {
/* 285 */       this.setLocationMethod = Reflection.getMethod(ENTITY_CLASS, "a_", new Class[] { double.class, double.class, double.class });
/*     */       
/* 287 */       this.setSmallMethod = Reflection.getMethod(ENTITY_ARMOR_STAND_CLASS, "t", new Class[] { boolean.class });
/*     */       
/* 289 */       this.setInvisibleMethod = Reflection.getMethod(ENTITY_ARMOR_STAND_CLASS, "k", new Class[] { boolean.class });
/*     */       
/* 291 */       this.setBasePlateMethod = Reflection.getMethod(ENTITY_ARMOR_STAND_CLASS, "b", new Class[] { boolean.class });
/*     */       
/* 293 */       this.setArmsMethod = Reflection.getMethod(ENTITY_ARMOR_STAND_CLASS, "a", new Class[] { boolean.class });
/*     */       
/* 295 */       this.sendPacketMethod = Reflection.getMethod(PLAYER_CONNECTION_CLASS, "b", new Class[] { PACKET_CLASS });
/*     */       
/* 297 */       this.getIdMethod = Reflection.getMethod(ENTITY_CLASS, "al", new Class[0]);
/*     */     }
/* 299 */     else if (version.isAboveOrEqual(MinecraftVersion.V1_20_R3)) {
/* 300 */       this.setLocationMethod = Reflection.getMethod(ENTITY_CLASS, "a_", new Class[] { double.class, double.class, double.class });
/*     */       
/* 302 */       this.setSmallMethod = Reflection.getMethod(ENTITY_ARMOR_STAND_CLASS, "t", new Class[] { boolean.class });
/*     */       
/* 304 */       this.setInvisibleMethod = Reflection.getMethod(ENTITY_ARMOR_STAND_CLASS, "j", new Class[] { boolean.class });
/*     */       
/* 306 */       this.setBasePlateMethod = Reflection.getMethod(ENTITY_ARMOR_STAND_CLASS, "s", new Class[] { boolean.class });
/*     */       
/* 308 */       this.setArmsMethod = Reflection.getMethod(ENTITY_ARMOR_STAND_CLASS, "a", new Class[] { boolean.class });
/*     */ 
/*     */ 
/*     */       
/* 312 */       Class<?> clazz = Reflection.getClass("net.minecraft.network.PacketSendListener");
/* 313 */       this.sendPacketMethod = Reflection.getMethod(PLAYER_CONNECTION_CLASS, "a", new Class[] { PACKET_CLASS, clazz });
/*     */       
/* 315 */       this.getIdMethod = Reflection.getMethod(ENTITY_CLASS, "aj", new Class[0]);
/*     */     }
/* 317 */     else if (version.isAboveOrEqual(MinecraftVersion.V1_20_R2)) {
/* 318 */       this.setLocationMethod = Reflection.getMethod(ENTITY_CLASS, "e", new Class[] { double.class, double.class, double.class });
/*     */       
/* 320 */       this.setSmallMethod = Reflection.getMethod(ENTITY_ARMOR_STAND_CLASS, "t", new Class[] { boolean.class });
/*     */       
/* 322 */       this.setInvisibleMethod = Reflection.getMethod(ENTITY_ARMOR_STAND_CLASS, "j", new Class[] { boolean.class });
/*     */       
/* 324 */       this.setBasePlateMethod = Reflection.getMethod(ENTITY_ARMOR_STAND_CLASS, "s", new Class[] { boolean.class });
/*     */       
/* 326 */       this.setArmsMethod = Reflection.getMethod(ENTITY_ARMOR_STAND_CLASS, "a", new Class[] { boolean.class });
/*     */ 
/*     */ 
/*     */       
/* 330 */       Class<?> clazz = Reflection.getClass("net.minecraft.network.PacketSendListener");
/* 331 */       this.sendPacketMethod = Reflection.getMethod(PLAYER_CONNECTION_CLASS, "a", new Class[] { PACKET_CLASS, clazz });
/*     */       
/* 333 */       this.getIdMethod = Reflection.getMethod(ENTITY_CLASS, "ah", new Class[0]);
/*     */     }
/* 335 */     else if (version.isAboveOrEqual(MinecraftVersion.V1_19_R3)) {
/* 336 */       this.setLocationMethod = Reflection.getMethod(ENTITY_CLASS, "e", new Class[] { double.class, double.class, double.class });
/*     */       
/* 338 */       this.setSmallMethod = Reflection.getMethod(ENTITY_ARMOR_STAND_CLASS, "t", new Class[] { boolean.class });
/*     */       
/* 340 */       this.setInvisibleMethod = Reflection.getMethod(ENTITY_ARMOR_STAND_CLASS, "j", new Class[] { boolean.class });
/*     */       
/* 342 */       this.setBasePlateMethod = Reflection.getMethod(ENTITY_ARMOR_STAND_CLASS, "s", new Class[] { boolean.class });
/*     */       
/* 344 */       this.setArmsMethod = Reflection.getMethod(ENTITY_ARMOR_STAND_CLASS, "a", new Class[] { boolean.class });
/*     */       
/* 346 */       this.sendPacketMethod = Reflection.getMethod(PLAYER_CONNECTION_CLASS, "a", new Class[] { PACKET_CLASS });
/*     */       
/* 348 */       this.getIdMethod = Reflection.getMethod(ENTITY_CLASS, "af", new Class[0]);
/*     */     }
/* 350 */     else if (version.isAboveOrEqual(MinecraftVersion.V1_19_R2)) {
/* 351 */       this.setLocationMethod = Reflection.getMethod(ENTITY_CLASS, "f", new Class[] { double.class, double.class, double.class });
/*     */       
/* 353 */       this.setSmallMethod = Reflection.getMethod(ENTITY_ARMOR_STAND_CLASS, "a", new Class[] { boolean.class });
/*     */       
/* 355 */       this.setInvisibleMethod = Reflection.getMethod(ENTITY_ARMOR_STAND_CLASS, "j", new Class[] { boolean.class });
/*     */       
/* 357 */       this.setBasePlateMethod = Reflection.getMethod(ENTITY_ARMOR_STAND_CLASS, "s", new Class[] { boolean.class });
/*     */       
/* 359 */       this.setArmsMethod = Reflection.getMethod(ENTITY_ARMOR_STAND_CLASS, "r", new Class[] { boolean.class });
/*     */       
/* 361 */       this.sendPacketMethod = Reflection.getMethod(PLAYER_CONNECTION_CLASS, "a", new Class[] { PACKET_CLASS });
/*     */       
/* 363 */       this.getIdMethod = Reflection.getMethod(ENTITY_CLASS, "ah", new Class[0]);
/*     */     }
/* 365 */     else if (version.isAboveOrEqual(MinecraftVersion.V1_18_R1)) {
/* 366 */       this.setLocationMethod = Reflection.getMethod(ENTITY_CLASS, "e", new Class[] { double.class, double.class, double.class });
/*     */       
/* 368 */       this.setSmallMethod = Reflection.getMethod(ENTITY_ARMOR_STAND_CLASS, "a", new Class[] { boolean.class });
/*     */       
/* 370 */       this.setInvisibleMethod = Reflection.getMethod(ENTITY_ARMOR_STAND_CLASS, "j", new Class[] { boolean.class });
/*     */       
/* 372 */       this.setBasePlateMethod = Reflection.getMethod(ENTITY_ARMOR_STAND_CLASS, "e", new Class[] { boolean.class });
/*     */       
/* 374 */       this.setArmsMethod = Reflection.getMethod(ENTITY_ARMOR_STAND_CLASS, "r", new Class[] { boolean.class });
/*     */       
/* 376 */       this.sendPacketMethod = Reflection.getMethod(PLAYER_CONNECTION_CLASS, "a", new Class[] { PACKET_CLASS });
/*     */       
/* 378 */       this.getIdMethod = Reflection.getMethod(ENTITY_ARMOR_STAND_CLASS, "ae", new Class[0]);
/*     */     }
/* 380 */     else if (version.isAboveOrEqual(MinecraftVersion.V1_16_R3)) {
/* 381 */       this.setLocationMethod = Reflection.getMethod(ENTITY_CLASS, "setPosition", new Class[] { double.class, double.class, double.class });
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
/* 400 */       this.setSmallMethod = Reflection.getMethod(ENTITY_ARMOR_STAND_CLASS, "setSmall", new Class[] { boolean.class });
/*     */       
/* 402 */       this.setInvisibleMethod = Reflection.getMethod(ENTITY_ARMOR_STAND_CLASS, "setInvisible", new Class[] { boolean.class });
/*     */       
/* 404 */       this.setBasePlateMethod = Reflection.getMethod(ENTITY_ARMOR_STAND_CLASS, "setBasePlate", new Class[] { boolean.class });
/*     */       
/* 406 */       this.setArmsMethod = Reflection.getMethod(ENTITY_ARMOR_STAND_CLASS, "setArms", new Class[] { boolean.class });
/*     */       
/* 408 */       this.sendPacketMethod = Reflection.getMethod(PLAYER_CONNECTION_CLASS, "sendPacket", new Class[] { PACKET_CLASS });
/*     */       
/* 410 */       this.getIdMethod = Reflection.getMethod(ENTITY_ARMOR_STAND_CLASS, "getId", new Class[0]);
/*     */     } else {
/*     */       
/* 413 */       this.setLocationMethod = Reflection.getMethod(ENTITY_ARMOR_STAND_CLASS, "setLocation", new Class[] { double.class, double.class, double.class, float.class, float.class });
/*     */       
/* 415 */       this.setSmallMethod = Reflection.getMethod(ENTITY_ARMOR_STAND_CLASS, "setSmall", new Class[] { boolean.class });
/*     */       
/* 417 */       this.setInvisibleMethod = Reflection.getMethod(ENTITY_ARMOR_STAND_CLASS, "setInvisible", new Class[] { boolean.class });
/*     */       
/* 419 */       this.setBasePlateMethod = Reflection.getMethod(ENTITY_ARMOR_STAND_CLASS, "setBasePlate", new Class[] { boolean.class });
/*     */       
/* 421 */       this.setArmsMethod = Reflection.getMethod(ENTITY_ARMOR_STAND_CLASS, "setArms", new Class[] { boolean.class });
/*     */       
/* 423 */       this.sendPacketMethod = Reflection.getMethod(PLAYER_CONNECTION_CLASS, "sendPacket", new Class[] { PACKET_CLASS });
/*     */       
/* 425 */       this.getIdMethod = Reflection.getMethod(ENTITY_ARMOR_STAND_CLASS, "getId", new Class[0]);
/*     */     } 
/*     */ 
/*     */     
/* 429 */     createPackets();
/*     */   }
/*     */   private void createPackets() {
/*     */     Reflection.MethodInvoker gravityMethod, customNameMethod, customNameVisibilityMethod;
/*     */     Reflection.ConstructorInvoker entityArmorStandConstructor;
/* 434 */     if (this.version.isAboveOrEqual(MinecraftVersion.V1_20_R4)) {
/* 435 */       gravityMethod = Reflection.getMethod(ENTITY_CLASS, "f", new Class[] { boolean.class });
/* 436 */     } else if (this.version.isAboveOrEqual(MinecraftVersion.V1_18_R1)) {
/* 437 */       gravityMethod = Reflection.getMethod(ENTITY_CLASS, "e", new Class[] { boolean.class });
/* 438 */     } else if (this.version.isAboveOrEqual(MinecraftVersion.V1_10_R1)) {
/* 439 */       gravityMethod = Reflection.getMethod(ENTITY_CLASS, "setNoGravity", new Class[] { boolean.class });
/*     */     } else {
/* 441 */       gravityMethod = Reflection.getMethod(ENTITY_ARMOR_STAND_CLASS, "setGravity", new Class[] { boolean.class });
/*     */     } 
/*     */ 
/*     */     
/* 445 */     if (this.version.isAboveOrEqual(MinecraftVersion.V1_19_R1)) {
/* 446 */       customNameMethod = Reflection.getMethod(ENTITY_CLASS, "b", new Class[] { CHAT_BASE_COMPONENT_CLASS });
/* 447 */     } else if (this.version.isAboveOrEqual(MinecraftVersion.V1_18_R1)) {
/* 448 */       customNameMethod = Reflection.getMethod(ENTITY_CLASS, "a", new Class[] { CHAT_BASE_COMPONENT_CLASS });
/*     */     } else {
/* 450 */       customNameMethod = Reflection.getMethod(ENTITY_CLASS, "setCustomName", new Class[] {
/* 451 */             this.version.isAboveOrEqual(MinecraftVersion.V1_13_R1) ? CHAT_BASE_COMPONENT_CLASS : String.class
/*     */           });
/*     */     } 
/*     */     
/* 455 */     if (this.version.isAboveOrEqual(MinecraftVersion.V1_21_R4)) {
/* 456 */       customNameVisibilityMethod = Reflection.getMethod(ENTITY_CLASS, "o", new Class[] { boolean.class });
/* 457 */     } else if (this.version.isAboveOrEqual(MinecraftVersion.V1_21_R1)) {
/* 458 */       customNameVisibilityMethod = Reflection.getMethod(ENTITY_CLASS, "p", new Class[] { boolean.class });
/* 459 */     } else if (this.version.isAboveOrEqual(MinecraftVersion.V1_20_R4)) {
/* 460 */       customNameVisibilityMethod = Reflection.getMethod(ENTITY_CLASS, "o", new Class[] { boolean.class });
/* 461 */     } else if (this.version.isAboveOrEqual(MinecraftVersion.V1_18_R1)) {
/* 462 */       customNameVisibilityMethod = Reflection.getMethod(ENTITY_CLASS, "n", new Class[] { boolean.class });
/*     */     } else {
/* 464 */       customNameVisibilityMethod = Reflection.getMethod(ENTITY_CLASS, "setCustomNameVisible", new Class[] { boolean.class });
/*     */     } 
/*     */     
/* 467 */     Location location = this.start.clone().add(0.0D, 0.3D * this.text.size() + ((this.setMarkerMethod != null) ? 1.0F : 0.0F), 0.0D);
/* 468 */     Class<?> worldClass = this.worldServer.getClass().getSuperclass();
/*     */     
/* 470 */     if (this.start.getWorld().getEnvironment() != World.Environment.NORMAL) {
/* 471 */       worldClass = worldClass.getSuperclass();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 482 */       entityArmorStandConstructor = this.version.isAboveOrEqual(MinecraftVersion.V1_14_R1) ? Reflection.getConstructor(ENTITY_ARMOR_STAND_CLASS, new Class[] { worldClass, double.class, double.class, double.class }) : Reflection.getConstructor(ENTITY_ARMOR_STAND_CLASS, new Class[] { worldClass });
/* 483 */     } catch (IllegalStateException exception) {
/* 484 */       worldClass = worldClass.getSuperclass();
/*     */ 
/*     */ 
/*     */       
/* 488 */       entityArmorStandConstructor = this.version.isAboveOrEqual(MinecraftVersion.V1_14_R1) ? Reflection.getConstructor(ENTITY_ARMOR_STAND_CLASS, new Class[] { worldClass, double.class, double.class, double.class }) : Reflection.getConstructor(ENTITY_ARMOR_STAND_CLASS, new Class[] { worldClass });
/*     */     } 
/*     */ 
/*     */     
/* 492 */     for (String line : this.text) {
/*     */ 
/*     */       
/* 495 */       Object entityArmorStand = this.version.isAboveOrEqual(MinecraftVersion.V1_14_R1) ? entityArmorStandConstructor.invoke(new Object[] { this.worldServer, Double.valueOf(location.getX()), Double.valueOf(location.getY()), Double.valueOf(location.getZ()) }) : entityArmorStandConstructor.invoke(new Object[] { this.worldServer });
/*     */       
/* 497 */       if (this.version.isAboveOrEqual(MinecraftVersion.V1_16_R3)) {
/* 498 */         this.setLocationMethod.invoke(entityArmorStand, new Object[] { Double.valueOf(location.getX()), Double.valueOf(location.getY()), Double.valueOf(location.getZ()) });
/*     */       } else {
/* 500 */         this.setLocationMethod.invoke(entityArmorStand, new Object[] { Double.valueOf(location.getX()), Double.valueOf(location.getY()), Double.valueOf(location.getZ()), Integer.valueOf(0), Integer.valueOf(0) });
/*     */       } 
/*     */       
/* 503 */       if (this.version.isAboveOrEqual(MinecraftVersion.V1_19_R1)) {
/* 504 */         customNameMethod.invoke(entityArmorStand, new Object[] { ((Object[])this.chatComponentFromString.invoke(null, new Object[] { line }))[0] });
/*     */       } else {
/* 506 */         customNameMethod.invoke(entityArmorStand, new Object[] { this.version.isAboveOrEqual(MinecraftVersion.V1_13_R1) ? this.chatComponentTextConstructor
/* 507 */               .invoke(new Object[] { line }) : line });
/*     */       } 
/*     */       
/* 510 */       customNameVisibilityMethod.invoke(entityArmorStand, new Object[] { Boolean.valueOf(true) });
/* 511 */       gravityMethod.invoke(entityArmorStand, new Object[] { Boolean.valueOf(this.version.isAboveOrEqual(MinecraftVersion.V1_9_R2)) });
/* 512 */       this.setSmallMethod.invoke(entityArmorStand, new Object[] { Boolean.valueOf(true) });
/* 513 */       this.setInvisibleMethod.invoke(entityArmorStand, new Object[] { Boolean.valueOf(true) });
/* 514 */       if (this.version.isAboveOrEqual(MinecraftVersion.V1_18_R1)) {
/*     */         
/* 516 */         this.setBasePlateMethod.invoke(entityArmorStand, new Object[] { Boolean.valueOf(true) });
/*     */       } else {
/* 518 */         this.setBasePlateMethod.invoke(entityArmorStand, new Object[] { Boolean.valueOf(false) });
/*     */       } 
/* 520 */       this.setArmsMethod.invoke(entityArmorStand, new Object[] { Boolean.valueOf(false) });
/*     */       
/* 522 */       if (this.setMarkerMethod != null) {
/* 523 */         if (this.version.isAboveOrEqual(MinecraftVersion.V1_21_R1)) {
/* 524 */           this.setMarkerMethod.invoke(entityArmorStand, new Object[] { Boolean.valueOf(true) });
/*     */         } else {
/* 526 */           Object bukkitEntity = GET_BUKKIT_ENTITY.invoke(entityArmorStand, new Object[0]);
/* 527 */           ArmorStand as = (ArmorStand)bukkitEntity;
/* 528 */           as.setMarker(true);
/*     */         } 
/*     */       }
/*     */       
/* 532 */       this.armorStands.add(entityArmorStand);
/*     */ 
/*     */       
/* 535 */       if (this.version.isAboveOrEqual(MinecraftVersion.V1_21_R1))
/* 536 */       { this.showPackets.add(this.packetPlayOutSpawnEntityLivingConstructor.invoke(new Object[] { 
/* 537 */                 Integer.valueOf(((Integer)this.getIdMethod.invoke(entityArmorStand, new Object[0])).intValue()), this.getUUIDMethod.invoke(entityArmorStand, new Object[0]), 
/* 538 */                 Double.valueOf(location.getX()), Double.valueOf(location.getY()), Double.valueOf(location.getZ()), Float.valueOf(location.getPitch()), Float.valueOf(location.getYaw()), this.armorStandEntityType, 
/* 539 */                 Integer.valueOf(0), this.emptyVec3d, Integer.valueOf(0) })); }
/* 540 */       else { this.showPackets.add(this.packetPlayOutSpawnEntityLivingConstructor.invoke(new Object[] { entityArmorStand })); }
/*     */       
/* 542 */       this.hidePackets.add(PACKET_PLAY_OUT_ENTITY_DESTROY_CONSTRUCTOR
/* 543 */           .invoke(new Object[] { { ((Integer)this.getIdMethod.invoke(entityArmorStand, new Object[0])).intValue() } }));
/*     */       
/* 545 */       if (this.version.isAboveOrEqual(MinecraftVersion.V1_19_R2)) {
/* 546 */         Object dataWatcher = GET_DATAWATCHER_METHOD.invoke(entityArmorStand, new Object[0]);
/* 547 */         this.metaPackets.add(this.packetPlayOutEntityMetadataConstructor.invoke(new Object[] { this.getIdMethod
/* 548 */                 .invoke(entityArmorStand, new Object[0]), this.getNonDefaultValuesMethod
/* 549 */                 .invoke(dataWatcher, new Object[0]) }));
/*     */       } else {
/*     */         
/* 552 */         this.metaPackets.add(this.packetPlayOutEntityMetadataConstructor.invoke(new Object[] { this.getIdMethod
/* 553 */                 .invoke(entityArmorStand, new Object[0]), GET_DATAWATCHER_METHOD
/* 554 */                 .invoke(entityArmorStand, new Object[0]), 
/* 555 */                 Boolean.valueOf(true) }));
/*     */       } 
/*     */       
/* 558 */       location.subtract(0.0D, 0.3D, 0.0D);
/*     */     } 
/*     */   }
/*     */   public List<Object> getUpdatePackets(List<String> text) {
/*     */     Reflection.MethodInvoker customNameMethod;
/* 563 */     List<Object> updatePackets = new ArrayList();
/*     */     
/* 565 */     if (this.text.size() != text.size()) {
/* 566 */       throw new IllegalArgumentException("When updating the text, the old and new text should have the same amount of lines");
/*     */     }
/*     */ 
/*     */     
/* 570 */     if (this.version.isAboveOrEqual(MinecraftVersion.V1_19_R1)) {
/* 571 */       customNameMethod = Reflection.getMethod(ENTITY_CLASS, "b", new Class[] { CHAT_BASE_COMPONENT_CLASS });
/* 572 */     } else if (this.version.isAboveOrEqual(MinecraftVersion.V1_18_R1)) {
/* 573 */       customNameMethod = Reflection.getMethod(ENTITY_CLASS, "a", new Class[] { CHAT_BASE_COMPONENT_CLASS });
/*     */     } else {
/* 575 */       customNameMethod = Reflection.getMethod(ENTITY_CLASS, "setCustomName", new Class[] {
/* 576 */             this.version.isAboveOrEqual(MinecraftVersion.V1_13_R1) ? CHAT_BASE_COMPONENT_CLASS : String.class
/*     */           });
/*     */     } 
/* 579 */     Location location = this.start.clone().add(0.0D, 0.3D * text.size() + ((this.setMarkerMethod != null) ? 1.0F : 0.0F), 0.0D);
/* 580 */     for (int i = 0; i < text.size(); i++) {
/* 581 */       Object entityArmorStand = this.armorStands.get(i);
/* 582 */       String oldLine = this.text.get(i);
/* 583 */       String newLine = text.get(i);
/*     */ 
/*     */       
/* 586 */       if (this.version.isAboveOrEqual(MinecraftVersion.V1_19_R1)) {
/* 587 */         customNameMethod.invoke(entityArmorStand, new Object[] { ((Object[])this.chatComponentFromString.invoke(null, new Object[] { newLine }))[0] });
/*     */       } else {
/* 589 */         customNameMethod.invoke(entityArmorStand, new Object[] { this.version.isAboveOrEqual(MinecraftVersion.V1_13_R1) ? this.chatComponentTextConstructor
/* 590 */               .invoke(new Object[] { newLine }) : newLine });
/*     */       } 
/*     */       
/* 593 */       if (this.version.isAboveOrEqual(MinecraftVersion.V1_21_R1))
/* 594 */       { this.showPackets.set(i, this.packetPlayOutSpawnEntityLivingConstructor.invoke(new Object[] { 
/* 595 */                 Integer.valueOf(((Integer)this.getIdMethod.invoke(entityArmorStand, new Object[0])).intValue()), this.getUUIDMethod.invoke(entityArmorStand, new Object[0]), 
/* 596 */                 Double.valueOf(location.getX()), Double.valueOf(location.getY()), Double.valueOf(location.getZ()), Float.valueOf(location.getPitch()), Float.valueOf(location.getYaw()), this.armorStandEntityType, 
/* 597 */                 Integer.valueOf(0), this.emptyVec3d, Integer.valueOf(0) })); }
/* 598 */       else { this.showPackets.set(i, this.packetPlayOutSpawnEntityLivingConstructor.invoke(new Object[] { entityArmorStand })); }
/*     */       
/* 600 */       if (newLine.isEmpty() && !oldLine.isEmpty()) {
/* 601 */         updatePackets.add(this.hidePackets.get(i));
/* 602 */       } else if (!newLine.isEmpty() && oldLine.isEmpty()) {
/* 603 */         updatePackets.add(this.showPackets.get(i));
/* 604 */       } else if (!oldLine.equals(newLine)) {
/*     */         
/* 606 */         if (this.version.isAboveOrEqual(MinecraftVersion.V1_19_R2)) {
/* 607 */           Object dataWatcher = GET_DATAWATCHER_METHOD.invoke(entityArmorStand, new Object[0]);
/* 608 */           updatePackets.add(this.packetPlayOutEntityMetadataConstructor.invoke(new Object[] { this.getIdMethod
/* 609 */                   .invoke(entityArmorStand, new Object[0]), this.getNonDefaultValuesMethod
/* 610 */                   .invoke(dataWatcher, new Object[0]) }));
/*     */         } else {
/* 612 */           updatePackets.add(this.packetPlayOutEntityMetadataConstructor.invoke(new Object[] { this.getIdMethod
/* 613 */                   .invoke(entityArmorStand, new Object[0]), GET_DATAWATCHER_METHOD
/* 614 */                   .invoke(entityArmorStand, new Object[0]), 
/* 615 */                   Boolean.valueOf(true) }));
/*     */         } 
/*     */       } 
/* 618 */       location.subtract(0.0D, 0.3D, 0.0D);
/*     */     } 
/*     */     
/* 621 */     this.text = text;
/*     */     
/* 623 */     return updatePackets;
/*     */   }
/*     */   
/*     */   public void update(Player player, List<Object> updatePackets) {
/* 627 */     Object playerConnection = PLAYER_CONNECTION_FIELD.get(PLAYER_GET_HANDLE_METHOD.invoke(player, new Object[0]));
/*     */     
/* 629 */     for (Object packet : updatePackets) {
/* 630 */       if (this.version.isAboveOrEqual(MinecraftVersion.V1_20_R4)) {
/* 631 */         this.sendPacketMethod.invoke(playerConnection, new Object[] { packet }); continue;
/* 632 */       }  if (this.version.isAboveOrEqual(MinecraftVersion.V1_20_R2)) {
/* 633 */         this.sendPacketMethod.invoke(playerConnection, new Object[] { packet, null }); continue;
/* 634 */       }  this.sendPacketMethod.invoke(playerConnection, new Object[] { packet });
/*     */     } 
/*     */   }
/*     */   
/*     */   public void show(Player player) {
/* 639 */     Object playerConnection = PLAYER_CONNECTION_FIELD.get(PLAYER_GET_HANDLE_METHOD.invoke(player, new Object[0]));
/*     */     
/* 641 */     for (int i = 0; i < this.text.size(); i++) {
/* 642 */       if (!((String)this.text.get(i)).isEmpty()) {
/* 643 */         if (this.version.isAboveOrEqual(MinecraftVersion.V1_20_R4))
/* 644 */         { this.sendPacketMethod.invoke(playerConnection, new Object[] { this.showPackets.get(i) }); }
/* 645 */         else if (this.version.isAboveOrEqual(MinecraftVersion.V1_20_R2))
/* 646 */         { this.sendPacketMethod.invoke(playerConnection, new Object[] { this.showPackets.get(i), null }); }
/* 647 */         else { this.sendPacketMethod.invoke(playerConnection, new Object[] { this.showPackets.get(i) }); }
/*     */         
/* 649 */         if (this.version.isAboveOrEqual(MinecraftVersion.V1_15_R1))
/* 650 */           if (this.version.isAboveOrEqual(MinecraftVersion.V1_20_R4))
/* 651 */           { this.sendPacketMethod.invoke(playerConnection, new Object[] { this.metaPackets.get(i) }); }
/* 652 */           else if (this.version.isAboveOrEqual(MinecraftVersion.V1_20_R2))
/* 653 */           { this.sendPacketMethod.invoke(playerConnection, new Object[] { this.metaPackets.get(i), null }); }
/* 654 */           else { this.sendPacketMethod.invoke(playerConnection, new Object[] { this.metaPackets.get(i) }); }
/*     */            
/*     */       } 
/*     */     } 
/*     */   }
/*     */   public void hide(Player player) {
/* 660 */     Object playerConnection = PLAYER_CONNECTION_FIELD.get(PLAYER_GET_HANDLE_METHOD.invoke(player, new Object[0]));
/*     */     
/* 662 */     for (int i = 0; i < this.text.size(); i++) {
/* 663 */       if (!((String)this.text.get(i)).isEmpty())
/* 664 */         if (this.version.isAboveOrEqual(MinecraftVersion.V1_20_R4))
/* 665 */         { this.sendPacketMethod.invoke(playerConnection, new Object[] { this.hidePackets.get(i) }); }
/* 666 */         else if (this.version.isAboveOrEqual(MinecraftVersion.V1_20_R2))
/* 667 */         { this.sendPacketMethod.invoke(playerConnection, new Object[] { this.hidePackets.get(i), null }); }
/* 668 */         else { this.sendPacketMethod.invoke(playerConnection, new Object[] { this.hidePackets.get(i) }); }
/*     */          
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\BedWarsLobby-1.0-SNAPSHOT.jar!\net\jitse\npclib\hologram\Hologram.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */