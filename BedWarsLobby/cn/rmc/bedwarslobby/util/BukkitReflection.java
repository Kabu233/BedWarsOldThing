/*     */ package cn.rmc.bedwarslobby.util;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BukkitReflection
/*     */ {
/*     */   private static final String CRAFT_BUKKIT_PACKAGE;
/*     */   private static final String NET_MINECRAFT_SERVER_PACKAGE;
/*     */   private static final Class CRAFT_SERVER_CLASS;
/*     */   private static final Method CRAFT_SERVER_GET_HANDLE_METHOD;
/*     */   private static final Class PLAYER_LIST_CLASS;
/*     */   private static final Field PLAYER_LIST_MAX_PLAYERS_FIELD;
/*     */   private static final Class CRAFT_PLAYER_CLASS;
/*     */   private static final Method CRAFT_PLAYER_GET_HANDLE_METHOD;
/*     */   private static final Class ENTITY_PLAYER_CLASS;
/*     */   private static final Field ENTITY_PLAYER_PING_FIELD;
/*     */   private static final Class CRAFT_ITEM_STACK_CLASS;
/*     */   private static final Method CRAFT_ITEM_STACK_AS_NMS_COPY_METHOD;
/*     */   static final boolean $assertionsDisabled;
/*     */   
/*     */   static {
/*     */     try {
/*  33 */       String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
/*  34 */       CRAFT_BUKKIT_PACKAGE = "org.bukkit.craftbukkit." + version + ".";
/*  35 */       NET_MINECRAFT_SERVER_PACKAGE = "net.minecraft.server." + version + ".";
/*  36 */       CRAFT_SERVER_CLASS = Class.forName(CRAFT_BUKKIT_PACKAGE + "CraftServer");
/*  37 */       CRAFT_SERVER_GET_HANDLE_METHOD = CRAFT_SERVER_CLASS.getDeclaredMethod("getHandle", new Class[0]);
/*  38 */       CRAFT_SERVER_GET_HANDLE_METHOD.setAccessible(true);
/*  39 */       PLAYER_LIST_CLASS = Class.forName(NET_MINECRAFT_SERVER_PACKAGE + "PlayerList");
/*  40 */       PLAYER_LIST_MAX_PLAYERS_FIELD = PLAYER_LIST_CLASS.getDeclaredField("maxPlayers");
/*  41 */       PLAYER_LIST_MAX_PLAYERS_FIELD.setAccessible(true);
/*  42 */       CRAFT_PLAYER_CLASS = Class.forName(CRAFT_BUKKIT_PACKAGE + "entity.CraftPlayer");
/*  43 */       CRAFT_PLAYER_GET_HANDLE_METHOD = CRAFT_PLAYER_CLASS.getDeclaredMethod("getHandle", new Class[0]);
/*  44 */       CRAFT_PLAYER_GET_HANDLE_METHOD.setAccessible(true);
/*  45 */       ENTITY_PLAYER_CLASS = Class.forName(NET_MINECRAFT_SERVER_PACKAGE + "EntityPlayer");
/*  46 */       ENTITY_PLAYER_PING_FIELD = ENTITY_PLAYER_CLASS.getDeclaredField("ping");
/*  47 */       ENTITY_PLAYER_PING_FIELD.setAccessible(true);
/*  48 */       CRAFT_ITEM_STACK_CLASS = Class.forName(CRAFT_BUKKIT_PACKAGE + "inventory.CraftItemStack");
/*  49 */       CRAFT_ITEM_STACK_AS_NMS_COPY_METHOD = CRAFT_ITEM_STACK_CLASS.getDeclaredMethod("asNMSCopy", new Class[] { ItemStack.class });
/*  50 */       CRAFT_ITEM_STACK_AS_NMS_COPY_METHOD.setAccessible(true);
/*  51 */     } catch (Exception var1) {
/*  52 */       var1.printStackTrace();
/*  53 */       throw new RuntimeException("Failed to initialize Bukkit/NMS Reflection");
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void sendLightning(Player p, Location l) {
/*  58 */     Class<?> light = getNMSClass("EntityLightning");
/*     */     try {
/*  60 */       assert light != null;
/*     */ 
/*     */       
/*  63 */       Constructor<?> constu = light.getConstructor(new Class[] { getNMSClass("World"), double.class, double.class, double.class, boolean.class, boolean.class });
/*  64 */       Object wh = p.getWorld().getClass().getMethod("getHandle", new Class[0]).invoke(p.getWorld(), new Object[0]);
/*  65 */       Object lighobj = constu.newInstance(new Object[] { wh, Double.valueOf(l.getX()), Double.valueOf(l.getY()), Double.valueOf(l.getZ()), Boolean.valueOf(true), Boolean.valueOf(true) });
/*  66 */       Object obj = getNMSClass("PacketPlayOutSpawnEntityWeather").getConstructor(new Class[] { getNMSClass("Entity") }).newInstance(new Object[] { lighobj });
/*  67 */       sendPacket(p, obj);
/*  68 */       p.playSound(p.getLocation(), Sound.AMBIENCE_THUNDER, 100.0F, 1.0F);
/*  69 */     } catch (IllegalAccessException|IllegalArgumentException|InstantiationException|NoSuchMethodException|SecurityException|java.lang.reflect.InvocationTargetException var7) {
/*  70 */       var7.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Class<?> getNMSClass(String name) {
/*  75 */     String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
/*     */     try {
/*  77 */       return Class.forName("net.minecraft.server." + version + "." + name);
/*  78 */     } catch (ClassNotFoundException var3) {
/*  79 */       var3.printStackTrace();
/*  80 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Class<?> getClass(String name) {
/*  85 */     String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
/*     */     try {
/*  87 */       return Class.forName("org.bukkit.craftbukkit." + version + "." + name);
/*  88 */     } catch (Exception e) {
/*  89 */       e.printStackTrace();
/*  90 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void sendPacket(Player player, Object packet) {
/*     */     try {
/*  96 */       Object handle = player.getClass().getMethod("getHandle", new Class[0]).invoke(player, new Object[0]);
/*  97 */       Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
/*  98 */       playerConnection.getClass().getMethod("sendPacket", new Class[] { getNMSClass("Packet") }).invoke(playerConnection, new Object[] { packet });
/*  99 */     } catch (Exception var4) {
/* 100 */       var4.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int getPing(Player player) {
/*     */     try {
/* 106 */       int ping = ENTITY_PLAYER_PING_FIELD.getInt(CRAFT_PLAYER_GET_HANDLE_METHOD.invoke(player, new Object[0]));
/* 107 */       if (ping > 0) {
/* 108 */         return ping;
/*     */       }
/* 110 */       return 0;
/* 111 */     } catch (Exception e) {
/* 112 */       return 1;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void setMaxPlayers(Server server, int slots) {
/*     */     try {
/* 118 */       PLAYER_LIST_MAX_PLAYERS_FIELD.set(CRAFT_SERVER_GET_HANDLE_METHOD.invoke(server, new Object[0]), Integer.valueOf(slots));
/* 119 */     } catch (Exception var3) {
/* 120 */       var3.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String getItemStackName(ItemStack itemStack) {
/*     */     try {
/* 126 */       return (String)CRAFT_ITEM_STACK_AS_NMS_COPY_METHOD.invoke(itemStack, new Object[] { itemStack });
/* 127 */     } catch (Exception var2) {
/* 128 */       var2.printStackTrace();
/* 129 */       return "";
/*     */     } 
/*     */   }
/*     */ }