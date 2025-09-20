/*    */ package cn.rmc.bedwars.utils.player;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ import java.lang.reflect.Method;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class ActionBarUtils {
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
/* 26 */       nmsver = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
/* 27 */       classCraftPlayer = Class.forName("org.bukkit.craftbukkit." + nmsver + ".entity.CraftPlayer");
/* 28 */       classPacketChat = Class.forName("net.minecraft.server." + nmsver + ".PacketPlayOutChat");
/* 29 */       classPacket = Class.forName("net.minecraft.server." + nmsver + ".Packet");
/* 30 */       if (nmsver.equalsIgnoreCase("v1_8_R1") || nmsver.equalsIgnoreCase("v1_7_")) {
/* 31 */         classChatSerializer = Class.forName("net.minecraft.server." + nmsver + ".ChatSerializer");
/* 32 */         classIChatComponent = Class.forName("net.minecraft.server." + nmsver + ".IChatBaseComponent");
/* 33 */         methodSeralizeString = classChatSerializer.getDeclaredMethod("a", new Class[] { String.class });
/*    */       } else {
/* 35 */         classChatComponentText = Class.forName("net.minecraft.server." + nmsver + ".ChatComponentText");
/* 36 */         classIChatComponent = Class.forName("net.minecraft.server." + nmsver + ".IChatBaseComponent");
/*    */       } 
/* 38 */       methodGetHandle = classCraftPlayer.getDeclaredMethod("getHandle", new Class[0]);
/* 39 */       classEntityPlayer = Class.forName("net.minecraft.server." + nmsver + ".EntityPlayer");
/* 40 */       fieldConnection = classEntityPlayer.getDeclaredField("playerConnection");
/* 41 */       classPlayerConnection = Class.forName("net.minecraft.server." + nmsver + ".PlayerConnection");
/* 42 */       methodSendPacket = classPlayerConnection.getDeclaredMethod("sendPacket", new Class[] { classPacket });
/* 43 */       works = true;
/* 44 */     } catch (Exception e) {
/* 45 */       works = false;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void sendActionBar(Player player, String message) {
/* 50 */     if (!works)
/*    */       return;  try {
/* 52 */       Object ppoc, p = classCraftPlayer.cast(player);
/*    */       
/* 54 */       if (nmsver.equalsIgnoreCase("v1_8_R1") || nmsver.equalsIgnoreCase("v1_7_")) {
/* 55 */         Object cbc = classIChatComponent.cast(methodSeralizeString.invoke(classChatSerializer, new Object[] { "{\"text\": \"" + message + "\"}" }));
/* 56 */         ppoc = classPacketChat.getConstructor(new Class[] { classIChatComponent, byte.class }).newInstance(new Object[] { cbc, Byte.valueOf((byte)2) });
/*    */       } else {
/* 58 */         Object o = classChatComponentText.getConstructor(new Class[] { String.class }).newInstance(new Object[] { message });
/* 59 */         ppoc = classPacketChat.getConstructor(new Class[] { classIChatComponent, byte.class }).newInstance(new Object[] { o, Byte.valueOf((byte)2) });
/*    */       } 
/* 61 */       Object h = methodGetHandle.invoke(p, new Object[0]);
/* 62 */       Object pc = fieldConnection.get(h);
/* 63 */       methodSendPacket.invoke(pc, new Object[] { ppoc });
/* 64 */     } catch (Exception ex) {
/* 65 */       ex.printStackTrace();
/* 66 */       works = false;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void sendActionBarToAllPlayers(String message) {
/* 71 */     for (Player p : Bukkit.getOnlinePlayers())
/* 72 */       sendActionBar(p, message); 
/*    */   }
/*    */ }