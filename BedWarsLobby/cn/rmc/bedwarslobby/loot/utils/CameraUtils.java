/*     */ package cn.rmc.bedwarslobby.loot.utils;
/*     */ import com.comphenix.protocol.PacketType;
/*     */ import com.comphenix.protocol.ProtocolLibrary;
/*     */ import com.comphenix.protocol.events.PacketContainer;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import cn.rmc.bedwarslobby.BedWarsLobby;
/*     */ import net.minecraft.server.v1_8_R3.Container;
/*     */ import net.minecraft.server.v1_8_R3.ContainerPlayer;
/*     */ import net.minecraft.server.v1_8_R3.EntityHuman;
/*     */ import net.minecraft.server.v1_8_R3.EntityPlayer;
/*     */ import net.minecraft.server.v1_8_R3.Packet;
/*     */ import net.minecraft.server.v1_8_R3.PacketPlayOutCloseWindow;
/*     */ import org.bukkit.GameMode;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
/*     */ import org.bukkit.entity.ArmorStand;
/*     */ import org.bukkit.entity.EntityType;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.potion.PotionEffect;
/*     */ import org.bukkit.potion.PotionEffectType;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ 
/*     */ public class CameraUtils {
/*  31 */   public static List<Player> players = new ArrayList<>();
/*  32 */   public static Map<Player, ArmorStand> stands = new HashMap<>();
/*  33 */   public static AtomicBoolean sit = new AtomicBoolean(false);
/*  34 */   static Map<Player, ItemStack> holdOn = new HashMap<>();
/*     */   
/*     */   public static void joinCamera(final Player p) {
/*     */     try {
/*  38 */       ArmorStand stand = (ArmorStand)p.getWorld().spawnEntity(p.getLocation(), EntityType.ARMOR_STAND);
/*  39 */       stand.setGravity(false);
/*  40 */       stand.setVisible(false);
/*  41 */       PacketContainer camera = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.CAMERA);
/*  42 */       camera.getIntegers().write(0, Integer.valueOf(stand.getEntityId()));
/*  43 */       ProtocolLibrary.getProtocolManager().sendServerPacket(p, camera);
/*  44 */       players.add(p);
/*  45 */       stands.put(p, stand);
/*  46 */       p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 99999, 99999, true), true);
/*     */       
/*  48 */       fakeGameMode(p, GameMode.CREATIVE);
/*  49 */       sit.set(true);
/*     */       
/*  51 */       hideExperienceBar(p);
/*  52 */       if (p.getItemInHand() != null) {
/*  53 */         holdOn.put(p, p.getItemInHand());
/*  54 */         p.setItemInHand(null);
/*     */       } 
/*     */       
/*  57 */       (new BukkitRunnable()
/*     */         {
/*     */           public void run() {
/*  60 */             if (CameraUtils.players.contains(p)) {
/*  61 */               p.closeInventory();
/*  62 */               CameraUtils.blockInventory(p);
/*     */             } else {
/*  64 */               cancel();
/*     */             }  }
/*  66 */         }).runTaskTimer((Plugin)BedWarsLobby.getInstance(), 0L, 1L);
/*     */     } catch (Throwable $ex) {
/*     */       throw $ex;
/*     */     }  } public static void quitCamera(Player p) {
/*     */     try {
/*  71 */       if (sit.get()) {
/*  72 */         ((ArmorStand)stands.get(p)).remove();
/*  73 */         Location loc = p.getLocation().clone();
/*  74 */         loc.setYaw(-90.0F);
/*  75 */         p.teleport(loc);
/*  76 */         SitUtils.sitPlayer(p);
/*  77 */         showExperienceBar(p);
/*  78 */         fakeGameMode(p, GameMode.SURVIVAL);
/*  79 */         PacketContainer camera = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.CAMERA);
/*  80 */         camera.getIntegers().write(0, Integer.valueOf(p.getEntityId()));
/*  81 */         if (holdOn.containsKey(p)) {
/*  82 */           p.getInventory().setItem(2, holdOn.get(p));
/*  83 */           holdOn.remove(p);
/*     */         } 
/*  85 */         ProtocolLibrary.getProtocolManager().sendServerPacket(p, camera);
/*  86 */         p.removePotionEffect(PotionEffectType.INVISIBILITY);
/*     */       } 
/*  88 */       players.remove(p);
/*     */     } catch (Throwable $ex) {
/*     */       throw $ex;
/*     */     } 
/*     */   } private static void hideExperienceBar(Player player) {
/*     */     try {
/*  94 */       PacketContainer expPacket = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.EXPERIENCE);
/*  95 */       expPacket.getFloat().write(0, Float.valueOf(0.0F));
/*  96 */       expPacket.getIntegers()
/*  97 */         .write(0, Integer.valueOf(0))
/*  98 */         .write(1, Integer.valueOf(0));
/*  99 */       ProtocolLibrary.getProtocolManager().sendServerPacket(player, expPacket);
/*     */     } catch (Throwable $ex) {
/*     */       throw $ex;
/*     */     } 
/*     */   } private static void showExperienceBar(Player player) {
/*     */     try {
/* 105 */       PacketContainer expPacket = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.EXPERIENCE);
/* 106 */       expPacket.getFloat().write(0, Float.valueOf(player.getExp()));
/* 107 */       expPacket.getIntegers()
/* 108 */         .write(0, Integer.valueOf(player.getLevel()))
/* 109 */         .write(1, Integer.valueOf(player.getTotalExperience()));
/* 110 */       ProtocolLibrary.getProtocolManager().sendServerPacket(player, expPacket);
/*     */     } catch (Throwable $ex) {
/*     */       throw $ex;
/*     */     } 
/*     */   }
/*     */   public static void fakeGameMode(Player p, GameMode mode) { try {
/* 116 */       PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.GAME_STATE_CHANGE);
/*     */       
/* 118 */       packet.getIntegers().write(0, Integer.valueOf(3));
/* 119 */       packet.getFloat().write(0, Float.valueOf(mode.getValue()));
/*     */       
/* 121 */       ProtocolLibrary.getProtocolManager().sendServerPacket(p, packet);
/*     */     } catch (Throwable $ex) {
/*     */       throw $ex;
/*     */     }  } static void blockInventory(Player player) {
/*     */     try {
/* 126 */       EntityPlayer entityPlayer = ((CraftPlayer)player).getHandle();
/* 127 */       Field activeContainerField = EntityHuman.class.getDeclaredField("activeContainer");
/* 128 */       activeContainerField.setAccessible(true);
/* 129 */       Container container = (Container)activeContainerField.get(entityPlayer);
/* 130 */       if (container instanceof ContainerPlayer) {
/* 131 */         ContainerPlayer newContainer = new ContainerPlayer(entityPlayer.inventory, !entityPlayer.world.isClientSide, (EntityHuman)entityPlayer);
/* 132 */         activeContainerField.set(entityPlayer, newContainer);
/* 133 */         entityPlayer.playerConnection.sendPacket((Packet)new PacketPlayOutCloseWindow(0));
/* 134 */         entityPlayer.updateInventory((Container)newContainer);
/*     */       } 
/*     */     } catch (Throwable $ex) {
/*     */       throw $ex;
/*     */     } 
/*     */   }
/*     */ }