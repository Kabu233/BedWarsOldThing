/*    */ package cn.rmc.bedwarslobby.util;
/*    */ 
/*    */ import com.comphenix.protocol.PacketType;
/*    */ import com.comphenix.protocol.ProtocolLibrary;
/*    */ import com.comphenix.protocol.events.PacketContainer;
/*    */ import com.comphenix.protocol.wrappers.EnumWrappers;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class EffectUtils
/*    */ {
/*    */   public static void enchantmentTable(Player p, Location location) {
/* 13 */     Location loc = location.clone();
/* 14 */     loc.setY(location.getY() + 2.5D);
/* 15 */     effectPacket(p, EnumWrappers.Particle.ENCHANTMENT_TABLE, loc, 10);
/*    */   }
/*    */   
/*    */   public static void hugeBoom(Player p, Location location) {
/* 19 */     Location loc = location.clone();
/* 20 */     loc.setY(location.getY() + 1.0D);
/* 21 */     effectPacket(p, EnumWrappers.Particle.EXPLOSION_HUGE, loc, 20);
/*    */   }
/*    */   
/*    */   public static void cloud(Player p, Location location) {
/* 25 */     Location loc = location.clone();
/* 26 */     loc.setY(location.getY() + 1.75D);
/* 27 */     effectPacket(p, EnumWrappers.Particle.CLOUD, loc, 0.0F, 0.0F, 0.0F, 0.1F, 1);
/*    */   }
/*    */   
/*    */   public static void effectPacket(Player p, EnumWrappers.Particle particle, Location loc, int count) {
/* 31 */     effectPacket(p, particle, loc, 0.0F, 0.0F, 0.0F, 0.0F, count);
/*    */   }
/*    */   
/*    */   public static void effectPacket(Player p, EnumWrappers.Particle particle, Location loc, float x, float y, float z, float data, int count) {
/*    */     try {
/* 36 */       PacketContainer effect = new PacketContainer(PacketType.Play.Server.WORLD_PARTICLES);
/* 37 */       effect.getParticles().write(0, particle);
/* 38 */       effect.getFloat().write(0, Float.valueOf((float)loc.getX()));
/* 39 */       effect.getFloat().write(1, Float.valueOf((float)loc.getY()));
/* 40 */       effect.getFloat().write(2, Float.valueOf((float)loc.getZ()));
/* 41 */       effect.getFloat().write(3, Float.valueOf(x));
/* 42 */       effect.getFloat().write(4, Float.valueOf(y));
/* 43 */       effect.getFloat().write(5, Float.valueOf(z));
/* 44 */       effect.getFloat().write(6, Float.valueOf(data));
/* 45 */       effect.getIntegers().write(0, Integer.valueOf(count));
/* 46 */       ProtocolLibrary.getProtocolManager().sendServerPacket(p, effect);
/*    */     } catch (Throwable $ex) {
/*    */       throw $ex;
/*    */     } 
/*    */   }