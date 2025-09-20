/*    */ package cn.rmc.bedwarslobby.util;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ import java.lang.reflect.Method;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class ActionBarUtils
/*    */ {
/*    */   public static boolean works;
/*    */   public static String nmsver;
/*    */   private static Class<?> classCraftPlayer;
/*    */   private static Class<?> classPacketChat;
/*    */   private static Class<?> classPacket;
/*    */   private static Class<?> classChatSerializer;
/*    */   private static Class<?> classIChatComponent;
/*    */   private static Method methodSeralizeString;
/*    */   private static Class<?> classChatComponentText;
/*    */   private static Method methodGetHandle;
/*    */   private static Class<?> classEntityPlayer;
/*    */   private static Field fieldConnection;
/*    */   private static Class<?> classPlayerConnection;
/*    */   private static Method methodSendPacket;
/*    */   
/*    */   static {
/*    */     try {
/* 27 */       nmsver = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
/* 28 */       classCraftPlayer = Class.forName("org.bukkit.craftbukkit." + nmsver + ".entity.CraftPlayer");
/* 29 */       classPacketChat = Class.forName("net.minecraft.server." + nmsver + ".PacketPlayOutChat");
/* 30 */       classPacket = Class.forName("net.minecraft.server." + nmsver + ".Packet");
/* 31 */       if (nmsver.equalsIgnoreCase("v1_8_R1") || nmsver.equalsIgnoreCase("v1_7_")) {
/* 32 */         classChatSerializer = Class.forName("net.minecraft.server." + nmsver + ".ChatSerializer");
/* 33 */         classIChatComponent = Class.forName("net.minecraft.server." + nmsver + ".IChatBaseComponent");
/* 34 */         methodSeralizeString = classChatSerializer.getDeclaredMethod("a", new Class[] { String.class });
/*    */       } else {
/* 36 */         classChatComponentText = Class.forName("net.minecraft.server." + nmsver + ".ChatComponentText");
/* 37 */         classIChatComponent = Class.forName("net.minecraft.server." + nmsver + ".IChatBaseComponent");
/*    */       } 
/* 39 */       methodGetHandle = classCraftPlayer.getDeclaredMethod("getHandle", new Class[0]);
/* 40 */       classEntityPlayer = Class.forName("net.minecraft.server." + nmsver + ".EntityPlayer");
/* 41 */       fieldConnection = classEntityPlayer.getDeclaredField("playerConnection");
/* 42 */       classPlayerConnection = Class.forName("net.minecraft.server." + nmsver + ".PlayerConnection");
/* 43 */       methodSendPacket = classPlayerConnection.getDeclaredMethod("sendPacket", new Class[] { classPacket });
/* 44 */       works = true;
/* 45 */     } catch (Exception e) {
/* 46 */       works = false;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static void sendActionBar(Player player, String message) {
/* 52 */     if (works) {
/*    */       try {
/* 54 */         Object ppoc, p = classCraftPlayer.cast(player);
/* 55 */         if (nmsver.equalsIgnoreCase("v1_8_R1") || nmsver.equalsIgnoreCase("v1_7_")) {
/* 56 */           Object cbc = classIChatComponent.cast(methodSeralizeString.invoke(classChatSerializer, new Object[] { "{\"text\": \"" + message + "\"}" }));
/* 57 */           ppoc = classPacketChat.getConstructor(new Class[] { classIChatComponent, byte.class }).newInstance(new Object[] { cbc, Byte.valueOf((byte)2) });
/*    */         } else {
/* 59 */           Object o = classChatComponentText.getConstructor(new Class[] { String.class }).newInstance(new Object[] { message });
/* 60 */           ppoc = classPacketChat.getConstructor(new Class[] { classIChatComponent, byte.class }).newInstance(new Object[] { o, Byte.valueOf((byte)2) });
/*    */         } 
/* 62 */         Object h = methodGetHandle.invoke(p, new Object[0]);
/* 63 */         Object pc = fieldConnection.get(h);
/* 64 */         methodSendPacket.invoke(pc, new Object[] { ppoc });
/* 65 */       } catch (Exception ex) {
/* 66 */         ex.printStackTrace();
/* 67 */         works = false;
/*    */       } 
/*    */     }
/*    */   }
/*    */   
/*    */   public static void sendActionBarToAllPlayers(String message) {
/* 73 */     for (Player p : Bukkit.getOnlinePlayers())
/* 74 */       sendActionBar(p, message); 
/*    */   }
/*    */ }