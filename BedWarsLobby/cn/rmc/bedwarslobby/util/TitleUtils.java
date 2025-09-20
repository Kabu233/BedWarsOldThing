/*     */ package cn.rmc.bedwarslobby.util;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.Objects;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.entity.Player;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TitleUtils
/*     */ {
/*     */   public static void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String message) {
/*  17 */     sendTitle(player, fadeIn, stay, fadeOut, message, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void sendSubtitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String message) {
/*  22 */     sendTitle(player, fadeIn, stay, fadeOut, null, message);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void sendFullTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle) {
/*  27 */     sendTitle(player, fadeIn, stay, fadeOut, title, subtitle);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void sendPacket(Player player, Object packet) {
/*     */     try {
/*  33 */       Object handle = player.getClass().getMethod("getHandle", new Class[0]).invoke(player, new Object[0]);
/*     */       
/*  35 */       Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
/*  36 */       playerConnection.getClass()
/*  37 */         .getMethod("sendPacket", new Class[] { getNMSClass("Packet")
/*  38 */           }).invoke(playerConnection, new Object[] { packet });
/*  39 */     } catch (Exception e) {
/*  40 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static Class<?> getNMSClass(String name) {
/*  46 */     String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
/*     */     try {
/*  48 */       return Class.forName("net.minecraft.server." + version + "." + name);
/*  49 */     } catch (ClassNotFoundException e) {
/*  50 */       e.printStackTrace();
/*     */       
/*  52 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle) {
/*     */     try {
/*  58 */       if (title != null) {
/*  59 */         title = ChatColor.translateAlternateColorCodes('&', title);
/*  60 */         title = title.replaceAll("%player%", player.getDisplayName());
/*     */         
/*  62 */         Object e = ((Class)Objects.requireNonNull((T)getNMSClass("PacketPlayOutTitle"))).getDeclaredClasses()[0].getField("TIMES").get(null);
/*     */ 
/*     */         
/*  65 */         Object chatTitle = ((Class)Objects.requireNonNull((T)getNMSClass("IChatBaseComponent"))).getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke(null, new Object[] { "{\"text\":\"" + title + "\"}" });
/*     */ 
/*     */         
/*  68 */         Constructor subtitleConstructor = ((Class)Objects.<Class<?>>requireNonNull(getNMSClass("PacketPlayOutTitle"))).getConstructor(new Class[] { ((Class)Objects.requireNonNull((T)getNMSClass("PacketPlayOutTitle"))).getDeclaredClasses()[0], 
/*  69 */               getNMSClass("IChatBaseComponent"), int.class, int.class, int.class });
/*     */         
/*  71 */         Object titlePacket = subtitleConstructor.newInstance(new Object[] { e, chatTitle, fadeIn, stay, fadeOut });
/*     */         
/*  73 */         sendPacket(player, titlePacket);
/*     */         
/*  75 */         e = ((Class)Objects.requireNonNull((T)getNMSClass("PacketPlayOutTitle"))).getDeclaredClasses()[0].getField("TITLE").get(null);
/*     */ 
/*     */         
/*  78 */         chatTitle = ((Class)Objects.requireNonNull((T)getNMSClass("IChatBaseComponent"))).getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke(null, new Object[] { "{\"text\":\"" + title + "\"}" });
/*     */         
/*  80 */         subtitleConstructor = ((Class)Objects.<Class<?>>requireNonNull(getNMSClass("PacketPlayOutTitle"))).getConstructor(new Class[] { ((Class)Objects.requireNonNull((T)getNMSClass("PacketPlayOutTitle"))).getDeclaredClasses()[0], 
/*  81 */               getNMSClass("IChatBaseComponent") });
/*     */         
/*  83 */         titlePacket = subtitleConstructor.newInstance(new Object[] { e, chatTitle });
/*  84 */         sendPacket(player, titlePacket);
/*     */       } 
/*  86 */       if (subtitle != null) {
/*  87 */         subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);
/*  88 */         subtitle = subtitle.replaceAll("%player%", player.getDisplayName());
/*     */         
/*  90 */         Object e = ((Class)Objects.requireNonNull((T)getNMSClass("PacketPlayOutTitle"))).getDeclaredClasses()[0].getField("TIMES").get(null);
/*     */ 
/*     */         
/*  93 */         Object chatSubtitle = ((Class)Objects.requireNonNull((T)getNMSClass("IChatBaseComponent"))).getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke(null, new Object[] { "{\"text\":\"" + title + "\"}" });
/*     */ 
/*     */         
/*  96 */         Constructor subtitleConstructor = ((Class)Objects.<Class<?>>requireNonNull(getNMSClass("PacketPlayOutTitle"))).getConstructor(new Class[] { ((Class)Objects.requireNonNull((T)getNMSClass("PacketPlayOutTitle"))).getDeclaredClasses()[0], 
/*  97 */               getNMSClass("IChatBaseComponent"), int.class, int.class, int.class });
/*     */         
/*  99 */         Object subtitlePacket = subtitleConstructor.newInstance(new Object[] { e, chatSubtitle, fadeIn, stay, fadeOut });
/*     */         
/* 101 */         sendPacket(player, subtitlePacket);
/*     */         
/* 103 */         e = ((Class)Objects.requireNonNull((T)getNMSClass("PacketPlayOutTitle"))).getDeclaredClasses()[0].getField("SUBTITLE").get(null);
/*     */ 
/*     */         
/* 106 */         chatSubtitle = ((Class)Objects.requireNonNull((T)getNMSClass("IChatBaseComponent"))).getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke(null, new Object[] { "{\"text\":\"" + subtitle + "\"}" });
/*     */         
/* 108 */         subtitleConstructor = ((Class)Objects.<Class<?>>requireNonNull(getNMSClass("PacketPlayOutTitle"))).getConstructor(new Class[] { ((Class)Objects.requireNonNull((T)getNMSClass("PacketPlayOutTitle"))).getDeclaredClasses()[0], 
/* 109 */               getNMSClass("IChatBaseComponent"), int.class, int.class, int.class });
/*     */         
/* 111 */         subtitlePacket = subtitleConstructor.newInstance(new Object[] { e, chatSubtitle, fadeIn, stay, fadeOut });
/*     */         
/* 113 */         sendPacket(player, subtitlePacket);
/*     */       } 
/* 115 */     } catch (Exception var11) {
/* 116 */       var11.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void clearTitle(Player player) {
/* 121 */     sendTitle(player, Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), "", "");
/*     */   }
/*     */   
/*     */   public static void sendTabTitle(Player player, String header, String footer) {
/* 125 */     if (header == null) {
/* 126 */       header = "";
/*     */     }
/* 128 */     header = ChatColor.translateAlternateColorCodes('&', header);
/* 129 */     if (footer == null) {
/* 130 */       footer = "";
/*     */     }
/* 132 */     footer = ChatColor.translateAlternateColorCodes('&', footer);
/* 133 */     header = header.replaceAll("%player%", player.getDisplayName());
/* 134 */     footer = footer.replaceAll("%player%", player.getDisplayName());
/*     */ 
/*     */     
/*     */     try {
/* 138 */       Object tabHeader = ((Class)Objects.requireNonNull((T)getNMSClass("IChatBaseComponent"))).getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke(null, new Object[] { "{\"text\":\"" + header + "\"}" });
/*     */ 
/*     */       
/* 141 */       Object tabFooter = ((Class)Objects.requireNonNull((T)getNMSClass("IChatBaseComponent"))).getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke(null, new Object[] { "{\"text\":\"" + footer + "\"}" });
/*     */       
/* 143 */       Constructor<?> titleConstructor = ((Class)Objects.<Class<?>>requireNonNull(getNMSClass("PacketPlayOutPlayerListHeaderFooter"))).getConstructor(new Class[0]);
/* 144 */       Object packet = titleConstructor.newInstance(new Object[0]);
/* 145 */       Field aField = packet.getClass().getDeclaredField("a");
/* 146 */       aField.setAccessible(true);
/* 147 */       aField.set(packet, tabHeader);
/* 148 */       Field bField = packet.getClass().getDeclaredField("b");
/* 149 */       bField.setAccessible(true);
/* 150 */       bField.set(packet, tabFooter);
/* 151 */       sendPacket(player, packet);
/* 152 */     } catch (Exception ex) {
/* 153 */       ex.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


.