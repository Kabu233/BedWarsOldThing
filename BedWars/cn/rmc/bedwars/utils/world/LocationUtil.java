/*     */ package cn.rmc.bedwars.utils.world;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.server.v1_8_R3.Entity;
/*     */ import net.minecraft.server.v1_8_R3.EntityLiving;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.EntityType;
/*     */ import org.bukkit.util.Vector;
/*     */ 
/*     */ public class LocationUtil {
/*     */   public static Location standardLocation(Location loc) {
/*  17 */     Location newLoc = loc.clone();
/*     */     
/*  19 */     newLoc.setX(loc.getBlockX() + 0.5D);
/*  20 */     newLoc.setY(loc.getBlockY());
/*  21 */     newLoc.setZ(loc.getBlockZ() + 0.5D);
/*  22 */     newLoc.setPitch(0.0F);
/*  23 */     float yaw = loc.getYaw();
/*  24 */     if (yaw > -45.0F || yaw < -315.0F) {
/*  25 */       yaw = 0.0F;
/*  26 */     } else if (yaw > -135.0F && yaw < -45.0F) {
/*  27 */       yaw = -90.0F;
/*  28 */     } else if (yaw > -225.0F && yaw < -135.0F) {
/*  29 */       yaw = -180.0F;
/*  30 */     } else if (yaw > -315.0F && yaw < -225.0F) {
/*  31 */       yaw = -270.0F;
/*     */     } 
/*  33 */     newLoc.setYaw(yaw);
/*     */     
/*  35 */     return newLoc;
/*     */   }
/*     */   public static List<Location> getGround(Location center, double radius) {
/*  38 */     List<Location> locations = new ArrayList<>();
/*  39 */     Location min = new Location(center.getWorld(), center.getX() - radius, center.getY() - radius, center.getZ() - radius);
/*  40 */     Location max = new Location(center.getWorld(), center.getX() + radius, center.getY() + radius, center.getZ() + radius); double x;
/*  41 */     for (x = min.getX(); x <= max.getX(); x += 0.6D) {
/*  42 */       double y; for (y = min.getY(); y <= max.getY(); y += 0.6D) {
/*  43 */         double z; for (z = min.getZ(); z <= max.getZ(); z += 0.6D) {
/*  44 */           locations.add(new Location(center.getWorld(), x, y, z));
/*     */         }
/*     */       } 
/*     */     } 
/*  48 */     return locations;
/*     */   }
/*     */   
/*     */   public static List<Location> getBlockNearbyLocations(Location loc) {
/*  52 */     List<Location> locs = new ArrayList<>();
/*     */     
/*  54 */     locs.add(loc.clone().add(1.0D, 0.0D, 0.0D));
/*  55 */     locs.add(loc.clone().add(-1.0D, 0.0D, 0.0D));
/*  56 */     locs.add(loc.clone().add(0.0D, 0.0D, 1.0D));
/*  57 */     locs.add(loc.clone().add(0.0D, 0.0D, -1.0D));
/*     */     
/*  59 */     return locs;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean hasRegion(Location location, Location loc1, Location loc2) {
/*  64 */     double x1 = loc1.getX();
/*  65 */     double x2 = loc2.getX();
/*  66 */     double y1 = loc1.getY();
/*  67 */     double y2 = loc2.getY();
/*  68 */     double z1 = loc1.getZ();
/*  69 */     double z2 = loc2.getZ();
/*  70 */     double minY = Math.min(y1, y2);
/*  71 */     double maxY = Math.max(y1, y2);
/*  72 */     double minZ = Math.min(z1, z2);
/*  73 */     double maxZ = Math.max(z1, z2);
/*  74 */     double minX = Math.min(x1, x2);
/*  75 */     double maxX = Math.max(x1, x2);
/*  76 */     if (location.getWorld().equals(loc1.getWorld()) && location.getWorld().equals(loc2.getWorld()) && 
/*  77 */       location.getX() > minX && location.getX() < maxX && 
/*  78 */       location.getY() > minY && location.getY() < maxY) {
/*  79 */       return (location.getZ() > minZ && location.getZ() < maxZ);
/*     */     }
/*     */ 
/*     */     
/*  83 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<Location> getLocations(Location start, Location end) {
/*  90 */     if (end.getWorld() != start.getWorld()) {
/*  91 */       return new ArrayList<>();
/*     */     }
/*     */     
/*  94 */     List<Location> locations = new ArrayList<>();
/*  95 */     for (int x = start.getBlockX(); x <= end.getBlockX(); x++) {
/*  96 */       for (int y = start.getBlockY(); y <= end.getBlockY(); y++) {
/*  97 */         for (int z = start.getBlockZ(); z <= end.getBlockZ(); z++) {
/*  98 */           locations.add(new Location(start.getWorld(), x, y, z));
/*     */         }
/*     */       } 
/*     */     } 
/* 102 */     return locations;
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<Location> getLocationswithoutAir(Location start, Location end) {
/* 107 */     if (end.getWorld() != start.getWorld()) {
/* 108 */       return new ArrayList<>();
/*     */     }
/*     */     
/* 111 */     List<Location> locations = new ArrayList<>();
/* 112 */     for (int x = start.getBlockX(); x <= end.getBlockX(); x++) {
/* 113 */       for (int y = start.getBlockY(); y <= end.getBlockY(); y++) {
/* 114 */         for (int z = start.getBlockZ(); z <= end.getBlockZ(); z++) {
/* 115 */           Location locs = new Location(start.getWorld(), x, y, z);
/* 116 */           if (locs.getBlock().getType() != Material.AIR) {
/* 117 */             locations.add(locs);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 122 */     return locations;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Location getMiddle(Location loc1, Location loc2) {
/* 131 */     double x1 = loc1.getX();
/* 132 */     double x2 = loc2.getX();
/* 133 */     double y1 = loc1.getY();
/* 134 */     double y2 = loc2.getY();
/* 135 */     double z1 = loc1.getZ();
/* 136 */     double z2 = loc2.getZ();
/* 137 */     return new Location(loc1.getWorld(), (x1 + x2) / 2.0D, (y1 + y2) / 2.0D, (z1 + z2) / 2.0D, loc1.getYaw(), loc1.getPitch());
/*     */   }
/*     */   
/*     */   public static List<Block> getBlocks(Block start, Block end) {
/* 141 */     List<Block> blocks = new ArrayList<>();
/* 142 */     for (Location loc : getLocations(start.getLocation(), end.getLocation())) {
/* 143 */       blocks.add(loc.getBlock());
/*     */     }
/* 145 */     return blocks;
/*     */   }
/*     */   
/*     */   public static List<Block> getBlocks(Location start, Location end) {
/* 149 */     List<Block> blocks = new ArrayList<>();
/* 150 */     for (Location loc : getLocations(start, end)) {
/* 151 */       blocks.add(loc.getBlock());
/*     */     }
/* 153 */     return blocks;
/*     */   }
/*     */   public static List<Block> getBlockswithoutAir(Location start, Location end) {
/* 156 */     List<Block> blocks = new ArrayList<>();
/* 157 */     for (Location loc : getLocations(start, end)) {
/* 158 */       if (loc.getBlock().getType() != null && loc.getBlock().getType() != Material.AIR) {
/* 159 */         blocks.add(loc.getBlock());
/*     */       }
/*     */     } 
/* 162 */     return blocks;
/*     */   }
/*     */   
/*     */   public static List<EntityLiving> getNearbyPlayers(Location where, int range) {
/* 166 */     List<EntityLiving> found = new ArrayList<>();
/*     */ 
/*     */ 
/*     */     
/* 170 */     for (Entity entity : where.getWorld().getEntities()) {
/* 171 */       if (isInBorder(where, entity.getLocation(), range)) {
/* 172 */         Entity e = ((CraftEntity)entity).getHandle();
/*     */         
/* 174 */         if (entity.getType() == EntityType.PLAYER && e.isAlive()) {
/* 175 */           found.add((EntityLiving)e);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 181 */     return found;
/*     */   }
/*     */   
/*     */   public static List<Entity> getNearbyEntities(Location where, double range, EntityType type) {
/* 185 */     List<Entity> found = new ArrayList<>();
/*     */     
/* 187 */     for (Entity entity : where.getWorld().getEntities()) {
/* 188 */       if (getDistance(entity.getLocation(), where) <= range && 
/* 189 */         entity.getType() == type && !entity.isDead()) {
/* 190 */         found.add(entity);
/*     */       }
/*     */     } 
/* 193 */     return found;
/*     */   }
/*     */   
/*     */   public static double getDistance(Location loc1, Location loc2) {
/* 197 */     return Math.abs(loc1.getX() - loc2.getX()) + Math.abs(loc1.getY() - loc2.getY()) + 
/* 198 */       Math.abs(loc1.getZ() - loc2.getZ());
/*     */   }
/*     */   
/*     */   public static boolean isInBorder(Location center, Location notCenter, double range) {
/* 202 */     double x = center.getX(), z = center.getZ();
/* 203 */     double x1 = notCenter.getBlockX(), z1 = notCenter.getBlockZ();
/*     */     
/* 205 */     return (x1 < x + range && z1 < z + range && x1 > x - range && z1 > z - range);
/*     */   }
/*     */   
/*     */   public static Location getLocation(Location location, int x, int y, int z) {
/* 209 */     Location loc = location.getBlock().getLocation();
/* 210 */     loc.add(x, y, z);
/* 211 */     return loc;
/*     */   }
/*     */   public static Vector getPosition(Location location1, Location location2) {
/* 214 */     double X = location1.getX() - location2.getX();
/* 215 */     double Y = location1.getY() - location2.getY();
/* 216 */     double Z = location1.getZ() - location2.getZ();
/* 217 */     return new Vector(X, Y, Z);
/*     */   }
/*     */   
/*     */   public static Vector getPosition(Location location1, Location location2, double Y) {
/* 221 */     double X = location1.getX() - location2.getX();
/* 222 */     double Z = location1.getZ() - location2.getZ();
/* 223 */     return new Vector(X, Y, Z);
/*     */   }
/*     */ }