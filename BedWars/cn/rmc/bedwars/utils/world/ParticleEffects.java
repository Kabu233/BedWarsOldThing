/*      */ package cn.rmc.bedwars.utils.world;
/*      */ 
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.lang.reflect.Field;
/*      */ import java.lang.reflect.Method;
/*      */ import java.util.Arrays;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import cn.rmc.bedwars.utils.BukkitReflection;
/*      */ import org.bukkit.Bukkit;
/*      */ import org.bukkit.Color;
/*      */ import org.bukkit.Location;
/*      */ import org.bukkit.Material;
/*      */ import org.bukkit.entity.Player;
/*      */ import org.bukkit.util.Vector;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public enum ParticleEffects
/*      */ {
/*   52 */   EXPLOSION_NORMAL("explode", 0, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }),
/*   60 */   EXPLOSION_LARGE("largeexplode", 1, -1, new ParticleProperty[0]),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   68 */   EXPLOSION_HUGE("hugeexplosion", 2, -1, new ParticleProperty[0]),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   76 */   FIREWORKS_SPARK("fireworksSpark", 3, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }),
/*   84 */   WATER_BUBBLE("bubble", 4, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL, ParticleProperty.REQUIRES_WATER
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }),
/*   92 */   WATER_SPLASH("splash", 5, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }),
/*  100 */   WATER_WAKE("wake", 6, 7, new ParticleProperty[] { ParticleProperty.DIRECTIONAL
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }),
/*  108 */   SUSPENDED("suspended", 7, -1, new ParticleProperty[] { ParticleProperty.REQUIRES_WATER
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }),
/*  116 */   SUSPENDED_DEPTH("depthSuspend", 8, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }),
/*  124 */   CRIT("crit", 9, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }),
/*  132 */   CRIT_MAGIC("magicCrit", 10, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }),
/*  140 */   SMOKE_NORMAL("smoke", 11, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }),
/*  148 */   SMOKE_LARGE("largesmoke", 12, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }),
/*  157 */   SPELL("spell", 13, -1, new ParticleProperty[0]),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  166 */   SPELL_INSTANT("instantSpell", 14, -1, new ParticleProperty[0]),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  175 */   SPELL_MOB("mobSpell", 15, -1, new ParticleProperty[] { ParticleProperty.COLORABLE
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }),
/*  184 */   SPELL_MOB_AMBIENT("mobSpellAmbient", 16, -1, new ParticleProperty[] { ParticleProperty.COLORABLE
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }),
/*  193 */   SPELL_WITCH("witchMagic", 17, -1, new ParticleProperty[0]),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  201 */   DRIP_WATER("dripWater", 18, -1, new ParticleProperty[0]),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  209 */   DRIP_LAVA("dripLava", 19, -1, new ParticleProperty[0]),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  217 */   VILLAGER_ANGRY("angryVillager", 20, -1, new ParticleProperty[0]),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  225 */   VILLAGER_HAPPY("happyVillager", 21, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }),
/*  233 */   TOWN_AURA("townaura", 22, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }),
/*  241 */   NOTE("note", 23, -1, new ParticleProperty[] { ParticleProperty.COLORABLE
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }),
/*  249 */   PORTAL("portal", 24, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }),
/*  257 */   ENCHANTMENT_TABLE("enchantmenttable", 25, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }),
/*  265 */   FLAME("flame", 26, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }),
/*  273 */   LAVA("lava", 27, -1, new ParticleProperty[0]),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  281 */   FOOTSTEP("footstep", 28, -1, new ParticleProperty[0]),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  289 */   CLOUD("cloud", 29, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }),
/*  297 */   REDSTONE("reddust", 30, -1, new ParticleProperty[] { ParticleProperty.COLORABLE
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }),
/*  305 */   SNOWBALL("snowballpoof", 31, -1, new ParticleProperty[0]),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  313 */   SNOW_SHOVEL("snowshovel", 32, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }),
/*  321 */   SLIME("slime", 33, -1, new ParticleProperty[0]),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  329 */   HEART("heart", 34, -1, new ParticleProperty[0]),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  337 */   BARRIER("barrier", 35, 8, new ParticleProperty[0]),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  344 */   ITEM_CRACK("iconcrack", 36, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL, ParticleProperty.REQUIRES_DATA
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }),
/*  352 */   BLOCK_CRACK("blockcrack", 37, -1, new ParticleProperty[] { ParticleProperty.REQUIRES_DATA
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }),
/*  359 */   BLOCK_DUST("blockdust", 38, 7, new ParticleProperty[] { ParticleProperty.DIRECTIONAL, ParticleProperty.REQUIRES_DATA
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }),
/*  367 */   WATER_DROP("droplet", 39, 8, new ParticleProperty[0]),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  374 */   ITEM_TAKE("take", 40, 8, new ParticleProperty[0]),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  383 */   MOB_APPEARANCE("mobappearance", 41, 8, new ParticleProperty[0]);
/*      */   static {
/*  385 */     NAME_MAP = new HashMap<>();
/*  386 */     ID_MAP = new HashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  394 */     for (ParticleEffects effect : values()) {
/*  395 */       NAME_MAP.put(effect.name, effect);
/*  396 */       ID_MAP.put(Integer.valueOf(effect.id), effect);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static final Map<String, ParticleEffects> NAME_MAP;
/*      */   private static final Map<Integer, ParticleEffects> ID_MAP;
/*      */   private final String name;
/*      */   private final int id;
/*      */   private final int requiredVersion;
/*      */   private final List<ParticleProperty> properties;
/*      */   
/*      */   ParticleEffects(String name, int id, int requiredVersion, ParticleProperty... properties) {
/*  409 */     this.name = name;
/*  410 */     this.id = id;
/*  411 */     this.requiredVersion = requiredVersion;
/*  412 */     this.properties = Arrays.asList(properties);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getName() {
/*  421 */     return this.name;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getId() {
/*  430 */     return this.id;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getRequiredVersion() {
/*  439 */     return this.requiredVersion;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasProperty(ParticleProperty property) {
/*  448 */     return this.properties.contains(property);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSupported() {
/*  457 */     if (this.requiredVersion == -1) {
/*  458 */       return true;
/*      */     }
/*  460 */     return (ParticlePacket.getVersion() >= this.requiredVersion);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ParticleEffects fromName(String name) {
/*  470 */     for (Map.Entry<String, ParticleEffects> entry : NAME_MAP.entrySet()) {
/*  471 */       if (!((String)entry.getKey()).equalsIgnoreCase(name)) {
/*      */         continue;
/*      */       }
/*  474 */       return entry.getValue();
/*      */     } 
/*  476 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ParticleEffects fromId(int id) {
/*  486 */     for (Map.Entry<Integer, ParticleEffects> entry : ID_MAP.entrySet()) {
/*  487 */       if (((Integer)entry.getKey()).intValue() != id) {
/*      */         continue;
/*      */       }
/*  490 */       return entry.getValue();
/*      */     } 
/*  492 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isWater(Location location) {
/*  502 */     Material material = location.getBlock().getType();
/*  503 */     return (material == Material.WATER || material == Material.STATIONARY_WATER);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isLongDistance(Location location, List<Player> players) {
/*  513 */     String world = location.getWorld().getName();
/*  514 */     for (Player player : players) {
/*  515 */       Location playerLocation = player.getLocation();
/*  516 */       if (!world.equals(playerLocation.getWorld().getName()) || playerLocation.distanceSquared(location) < 65536.0D) {
/*      */         continue;
/*      */       }
/*  519 */       return true;
/*      */     } 
/*  521 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isDataCorrect(ParticleEffects effect, ParticleData data) {
/*  532 */     return (((effect == BLOCK_CRACK || effect == BLOCK_DUST) && data instanceof BlockData) || (effect == ITEM_CRACK && data instanceof ItemData));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isColorCorrect(ParticleEffects effect, ParticleColor color) {
/*  543 */     return (((effect == SPELL_MOB || effect == SPELL_MOB_AMBIENT || effect == REDSTONE) && color instanceof OrdinaryColor) || (effect == NOTE && color instanceof NoteColor));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void display(float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, double range) throws ParticleVersionException, ParticleDataException, IllegalArgumentException {
/*  563 */     if (!isSupported()) {
/*  564 */       throw new ParticleVersionException("This particle effect is not supported by your server version");
/*      */     }
/*  566 */     if (hasProperty(ParticleProperty.REQUIRES_DATA)) {
/*  567 */       throw new ParticleDataException("This particle effect requires additional data");
/*      */     }
/*  569 */     if (hasProperty(ParticleProperty.REQUIRES_WATER) && !isWater(center)) {
/*  570 */       throw new IllegalArgumentException("There is no water at the center location");
/*      */     }
/*  572 */     (new ParticlePacket(this, offsetX, offsetY, offsetZ, speed, amount, (range > 256.0D), null)).sendTo(center, range);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void display(float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, List<Player> players) throws ParticleVersionException, ParticleDataException, IllegalArgumentException {
/*  592 */     if (!isSupported()) {
/*  593 */       throw new ParticleVersionException("This particle effect is not supported by your server version");
/*      */     }
/*  595 */     if (hasProperty(ParticleProperty.REQUIRES_DATA)) {
/*  596 */       throw new ParticleDataException("This particle effect requires additional data");
/*      */     }
/*  598 */     if (hasProperty(ParticleProperty.REQUIRES_WATER) && !isWater(center)) {
/*  599 */       throw new IllegalArgumentException("There is no water at the center location");
/*      */     }
/*  601 */     (new ParticlePacket(this, offsetX, offsetY, offsetZ, speed, amount, isLongDistance(center, players), null)).sendTo(center, players);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void display(float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, Player... players) throws ParticleVersionException, ParticleDataException, IllegalArgumentException {
/*  620 */     display(offsetX, offsetY, offsetZ, speed, amount, center, Arrays.asList(players));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void display(Vector direction, float speed, Location center, double range) throws ParticleVersionException, ParticleDataException, IllegalArgumentException {
/*  637 */     if (!isSupported()) {
/*  638 */       throw new ParticleVersionException("This particle effect is not supported by your server version");
/*      */     }
/*  640 */     if (hasProperty(ParticleProperty.REQUIRES_DATA)) {
/*  641 */       throw new ParticleDataException("This particle effect requires additional data");
/*      */     }
/*  643 */     if (!hasProperty(ParticleProperty.DIRECTIONAL)) {
/*  644 */       throw new IllegalArgumentException("This particle effect is not directional");
/*      */     }
/*  646 */     if (hasProperty(ParticleProperty.REQUIRES_WATER) && !isWater(center)) {
/*  647 */       throw new IllegalArgumentException("There is no water at the center location");
/*      */     }
/*  649 */     (new ParticlePacket(this, direction, speed, (range > 256.0D), null)).sendTo(center, range);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void display(Vector direction, float speed, Location center, List<Player> players) throws ParticleVersionException, ParticleDataException, IllegalArgumentException {
/*  666 */     if (!isSupported()) {
/*  667 */       throw new ParticleVersionException("This particle effect is not supported by your server version");
/*      */     }
/*  669 */     if (hasProperty(ParticleProperty.REQUIRES_DATA)) {
/*  670 */       throw new ParticleDataException("This particle effect requires additional data");
/*      */     }
/*  672 */     if (!hasProperty(ParticleProperty.DIRECTIONAL)) {
/*  673 */       throw new IllegalArgumentException("This particle effect is not directional");
/*      */     }
/*  675 */     if (hasProperty(ParticleProperty.REQUIRES_WATER) && !isWater(center)) {
/*  676 */       throw new IllegalArgumentException("There is no water at the center location");
/*      */     }
/*  678 */     (new ParticlePacket(this, direction, speed, isLongDistance(center, players), null)).sendTo(center, players);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void display(Vector direction, float speed, Location center, Player... players) throws ParticleVersionException, ParticleDataException, IllegalArgumentException {
/*  694 */     display(direction, speed, center, Arrays.asList(players));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void display(ParticleColor color, Location center, double range) throws ParticleVersionException, ParticleColorException {
/*  709 */     if (!isSupported()) {
/*  710 */       throw new ParticleVersionException("This particle effect is not supported by your server version");
/*      */     }
/*  712 */     if (!hasProperty(ParticleProperty.COLORABLE)) {
/*  713 */       throw new ParticleColorException("This particle effect is not colorable");
/*      */     }
/*  715 */     if (!isColorCorrect(this, color)) {
/*  716 */       throw new ParticleColorException("The particle color type is incorrect");
/*      */     }
/*  718 */     (new ParticlePacket(this, color, (range > 256.0D))).sendTo(center, range);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void display(ParticleColor color, Location center, List<Player> players) throws ParticleVersionException, ParticleColorException {
/*  733 */     if (!isSupported()) {
/*  734 */       throw new ParticleVersionException("This particle effect is not supported by your server version");
/*      */     }
/*  736 */     if (!hasProperty(ParticleProperty.COLORABLE)) {
/*  737 */       throw new ParticleColorException("This particle effect is not colorable");
/*      */     }
/*  739 */     if (!isColorCorrect(this, color)) {
/*  740 */       throw new ParticleColorException("The particle color type is incorrect");
/*      */     }
/*  742 */     (new ParticlePacket(this, color, isLongDistance(center, players))).sendTo(center, players);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void display(ParticleColor color, Location center, Player... players) throws ParticleVersionException, ParticleColorException {
/*  756 */     display(color, center, Arrays.asList(players));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void display(ParticleData data, float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, double range) throws ParticleVersionException, ParticleDataException {
/*  776 */     if (!isSupported()) {
/*  777 */       throw new ParticleVersionException("This particle effect is not supported by your server version");
/*      */     }
/*  779 */     if (!hasProperty(ParticleProperty.REQUIRES_DATA)) {
/*  780 */       throw new ParticleDataException("This particle effect does not require additional data");
/*      */     }
/*  782 */     if (!isDataCorrect(this, data)) {
/*  783 */       throw new ParticleDataException("The particle data type is incorrect");
/*      */     }
/*  785 */     (new ParticlePacket(this, offsetX, offsetY, offsetZ, speed, amount, (range > 256.0D), data)).sendTo(center, range);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void display(ParticleData data, float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, List<Player> players) throws ParticleVersionException, ParticleDataException {
/*  805 */     if (!isSupported()) {
/*  806 */       throw new ParticleVersionException("This particle effect is not supported by your server version");
/*      */     }
/*  808 */     if (!hasProperty(ParticleProperty.REQUIRES_DATA)) {
/*  809 */       throw new ParticleDataException("This particle effect does not require additional data");
/*      */     }
/*  811 */     if (!isDataCorrect(this, data)) {
/*  812 */       throw new ParticleDataException("The particle data type is incorrect");
/*      */     }
/*  814 */     (new ParticlePacket(this, offsetX, offsetY, offsetZ, speed, amount, isLongDistance(center, players), data)).sendTo(center, players);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void display(ParticleData data, float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, Player... players) throws ParticleVersionException, ParticleDataException {
/*  833 */     display(data, offsetX, offsetY, offsetZ, speed, amount, center, Arrays.asList(players));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void display(ParticleData data, Vector direction, float speed, Location center, double range) throws ParticleVersionException, ParticleDataException {
/*  850 */     if (!isSupported()) {
/*  851 */       throw new ParticleVersionException("This particle effect is not supported by your server version");
/*      */     }
/*  853 */     if (!hasProperty(ParticleProperty.REQUIRES_DATA)) {
/*  854 */       throw new ParticleDataException("This particle effect does not require additional data");
/*      */     }
/*  856 */     if (!isDataCorrect(this, data)) {
/*  857 */       throw new ParticleDataException("The particle data type is incorrect");
/*      */     }
/*  859 */     (new ParticlePacket(this, direction, speed, (range > 256.0D), data)).sendTo(center, range);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void display(ParticleData data, Vector direction, float speed, Location center, List<Player> players) throws ParticleVersionException, ParticleDataException {
/*  876 */     if (!isSupported()) {
/*  877 */       throw new ParticleVersionException("This particle effect is not supported by your server version");
/*      */     }
/*  879 */     if (!hasProperty(ParticleProperty.REQUIRES_DATA)) {
/*  880 */       throw new ParticleDataException("This particle effect does not require additional data");
/*      */     }
/*  882 */     if (!isDataCorrect(this, data)) {
/*  883 */       throw new ParticleDataException("The particle data type is incorrect");
/*      */     }
/*  885 */     (new ParticlePacket(this, direction, speed, isLongDistance(center, players), data)).sendTo(center, players);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void display(ParticleData data, Vector direction, float speed, Location center, Player... players) throws ParticleVersionException, ParticleDataException {
/*  901 */     display(data, direction, speed, center, Arrays.asList(players));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public enum ParticleProperty
/*      */   {
/*  916 */     REQUIRES_WATER,
/*      */ 
/*      */ 
/*      */     
/*  920 */     REQUIRES_DATA,
/*      */ 
/*      */ 
/*      */     
/*  924 */     DIRECTIONAL,
/*      */ 
/*      */ 
/*      */     
/*  928 */     COLORABLE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static abstract class ParticleData
/*      */   {
/*      */     private final Material material;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final byte data;
/*      */ 
/*      */ 
/*      */     
/*      */     private final int[] packetData;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ParticleData(Material material, byte data) {
/*  952 */       this.material = material;
/*  953 */       this.data = data;
/*  954 */       this.packetData = new int[] { material.getId(), data };
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Material getMaterial() {
/*  963 */       return this.material;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public byte getData() {
/*  972 */       return this.data;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int[] getPacketData() {
/*  981 */       return this.packetData;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getPacketDataString() {
/*  990 */       return "_" + this.packetData[0] + "_" + this.packetData[1];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final class ItemData
/*      */     extends ParticleData
/*      */   {
/*      */     public ItemData(Material material, byte data) {
/* 1011 */       super(material, data);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final class BlockData
/*      */     extends ParticleData
/*      */   {
/*      */     public BlockData(Material material, byte data) throws IllegalArgumentException {
/* 1033 */       super(material, data);
/* 1034 */       if (!material.isBlock()) {
/* 1035 */         throw new IllegalArgumentException("The material is not a block");
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static abstract class ParticleColor
/*      */   {
/*      */     public abstract float getValueX();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public abstract float getValueY();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public abstract float getValueZ();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final class OrdinaryColor
/*      */     extends ParticleColor
/*      */   {
/*      */     private final int red;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final int green;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final int blue;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public OrdinaryColor(int red, int green, int blue) throws IllegalArgumentException {
/* 1093 */       if (red < 0) {
/* 1094 */         throw new IllegalArgumentException("The red value is lower than 0");
/*      */       }
/* 1096 */       if (red > 255) {
/* 1097 */         throw new IllegalArgumentException("The red value is higher than 255");
/*      */       }
/* 1099 */       this.red = red;
/* 1100 */       if (green < 0) {
/* 1101 */         throw new IllegalArgumentException("The green value is lower than 0");
/*      */       }
/* 1103 */       if (green > 255) {
/* 1104 */         throw new IllegalArgumentException("The green value is higher than 255");
/*      */       }
/* 1106 */       this.green = green;
/* 1107 */       if (blue < 0) {
/* 1108 */         throw new IllegalArgumentException("The blue value is lower than 0");
/*      */       }
/* 1110 */       if (blue > 255) {
/* 1111 */         throw new IllegalArgumentException("The blue value is higher than 255");
/*      */       }
/* 1113 */       this.blue = blue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public OrdinaryColor(Color color) {
/* 1122 */       this(color.getRed(), color.getGreen(), color.getBlue());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getRed() {
/* 1131 */       return this.red;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getGreen() {
/* 1140 */       return this.green;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getBlue() {
/* 1149 */       return this.blue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public float getValueX() {
/* 1159 */       return this.red / 255.0F;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public float getValueY() {
/* 1169 */       return this.green / 255.0F;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public float getValueZ() {
/* 1179 */       return this.blue / 255.0F;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final class NoteColor
/*      */     extends ParticleColor
/*      */   {
/*      */     private final int note;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public NoteColor(int note) throws IllegalArgumentException {
/* 1201 */       if (note < 0) {
/* 1202 */         throw new IllegalArgumentException("The note value is lower than 0");
/*      */       }
/* 1204 */       if (note > 24) {
/* 1205 */         throw new IllegalArgumentException("The note value is higher than 24");
/*      */       }
/* 1207 */       this.note = note;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public float getValueX() {
/* 1217 */       return this.note / 24.0F;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public float getValueY() {
/* 1227 */       return 0.0F;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public float getValueZ() {
/* 1237 */       return 0.0F;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class ParticleDataException
/*      */     extends RuntimeException
/*      */   {
/*      */     private static final long serialVersionUID = 3203085387160737484L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ParticleDataException(String message) {
/* 1259 */       super(message);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class ParticleColorException
/*      */     extends RuntimeException
/*      */   {
/*      */     private static final long serialVersionUID = 3203085387160737484L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ParticleColorException(String message) {
/* 1280 */       super(message);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class ParticleVersionException
/*      */     extends RuntimeException
/*      */   {
/*      */     private static final long serialVersionUID = 3203085387160737484L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ParticleVersionException(String message) {
/* 1301 */       super(message);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static final class ParticlePacket
/*      */   {
/*      */     private static int version;
/*      */ 
/*      */     
/*      */     private static Class<?> enumParticle;
/*      */ 
/*      */     
/*      */     private static Constructor<?> packetConstructor;
/*      */ 
/*      */     
/*      */     private static Method getHandle;
/*      */     
/*      */     private static Field playerConnection;
/*      */     
/*      */     private static Method sendPacket;
/*      */     
/*      */     private static boolean initialized;
/*      */     
/*      */     private final ParticleEffects effect;
/*      */     
/*      */     private float offsetX;
/*      */     
/*      */     private final float offsetY;
/*      */     
/*      */     private final float offsetZ;
/*      */     
/*      */     private final float speed;
/*      */     
/*      */     private final int amount;
/*      */     
/*      */     private final boolean longDistance;
/*      */     
/*      */     private final ParticleEffects.ParticleData data;
/*      */     
/*      */     private Object packet;
/*      */ 
/*      */     
/*      */     public ParticlePacket(ParticleEffects effect, float offsetX, float offsetY, float offsetZ, float speed, int amount, boolean longDistance, ParticleEffects.ParticleData data) throws IllegalArgumentException {
/* 1346 */       initialize();
/* 1347 */       if (speed < 0.0F) {
/* 1348 */         throw new IllegalArgumentException("The speed is lower than 0");
/*      */       }
/* 1350 */       if (amount < 0) {
/* 1351 */         throw new IllegalArgumentException("The amount is lower than 0");
/*      */       }
/* 1353 */       this.effect = effect;
/* 1354 */       this.offsetX = offsetX;
/* 1355 */       this.offsetY = offsetY;
/* 1356 */       this.offsetZ = offsetZ;
/* 1357 */       this.speed = speed;
/* 1358 */       this.amount = amount;
/* 1359 */       this.longDistance = longDistance;
/* 1360 */       this.data = data;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ParticlePacket(ParticleEffects effect, Vector direction, float speed, boolean longDistance, ParticleEffects.ParticleData data) throws IllegalArgumentException {
/* 1374 */       this(effect, (float)direction.getX(), (float)direction.getY(), (float)direction.getZ(), speed, 0, longDistance, data);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ParticlePacket(ParticleEffects effect, ParticleEffects.ParticleColor color, boolean longDistance) {
/* 1385 */       this(effect, color.getValueX(), color.getValueY(), color.getValueZ(), 1.0F, 0, longDistance, null);
/* 1386 */       if (effect == ParticleEffects.REDSTONE && color instanceof ParticleEffects.OrdinaryColor && ((ParticleEffects.OrdinaryColor)color).getRed() == 0) {
/* 1387 */         this.offsetX = 1.1754944E-38F;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static void initialize() throws VersionIncompatibleException {
/* 1399 */       if (initialized) {
/*      */         return;
/*      */       }
/*      */       try {
/* 1403 */         version = Integer.parseInt(Character.toString(BukkitReflection.PackageType.getServerVersion().charAt(3)));
/* 1404 */         if (version > 7) {
/* 1405 */           enumParticle = BukkitReflection.PackageType.MINECRAFT_SERVER.getClass("EnumParticle");
/*      */         }
/* 1407 */         Class<?> packetClass = BukkitReflection.PackageType.MINECRAFT_SERVER.getClass((version < 7) ? "Packet63WorldParticles" : "PacketPlayOutWorldParticles");
/* 1408 */         packetConstructor = BukkitReflection.getConstructor(packetClass, new Class[0]);
/* 1409 */         getHandle = BukkitReflection.getMethod("CraftPlayer", BukkitReflection.PackageType.CRAFTBUKKIT_ENTITY, "getHandle", new Class[0]);
/* 1410 */         playerConnection = BukkitReflection.getField("EntityPlayer", BukkitReflection.PackageType.MINECRAFT_SERVER, false, "playerConnection");
/* 1411 */         sendPacket = BukkitReflection.getMethod(playerConnection.getType(), "sendPacket", new Class[] { BukkitReflection.PackageType.MINECRAFT_SERVER.getClass("Packet") });
/* 1412 */       } catch (Exception exception) {
/* 1413 */         throw new VersionIncompatibleException("Your current bukkit version seems to be incompatible with this library", exception);
/*      */       } 
/* 1415 */       initialized = true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static int getVersion() {
/* 1424 */       if (!initialized) {
/* 1425 */         initialize();
/*      */       }
/* 1427 */       return version;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static boolean isInitialized() {
/* 1437 */       return initialized;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void initializePacket(Location center) throws PacketInstantiationException {
/* 1447 */       if (this.packet != null) {
/*      */         return;
/*      */       }
/*      */       try {
/* 1451 */         this.packet = packetConstructor.newInstance(new Object[0]);
/* 1452 */         if (version < 8) {
/* 1453 */           String name = this.effect.getName();
/* 1454 */           if (this.data != null) {
/* 1455 */             name = name + this.data.getPacketDataString();
/*      */           }
/* 1457 */           BukkitReflection.setValue(this.packet, true, "a", name);
/*      */         } else {
/* 1459 */           BukkitReflection.setValue(this.packet, true, "a", enumParticle.getEnumConstants()[this.effect.getId()]);
/* 1460 */           BukkitReflection.setValue(this.packet, true, "j", Boolean.valueOf(this.longDistance));
/* 1461 */           if (this.data != null) {
/* 1462 */             int[] packetData = this.data.getPacketData();
/* 1463 */             (new int[1])[0] = packetData[0] | packetData[1] << 12; BukkitReflection.setValue(this.packet, true, "k", (this.effect == ParticleEffects.ITEM_CRACK) ? packetData : new int[1]);
/*      */           } 
/*      */         } 
/* 1466 */         BukkitReflection.setValue(this.packet, true, "b", Float.valueOf((float)center.getX()));
/* 1467 */         BukkitReflection.setValue(this.packet, true, "c", Float.valueOf((float)center.getY()));
/* 1468 */         BukkitReflection.setValue(this.packet, true, "d", Float.valueOf((float)center.getZ()));
/* 1469 */         BukkitReflection.setValue(this.packet, true, "e", Float.valueOf(this.offsetX));
/* 1470 */         BukkitReflection.setValue(this.packet, true, "f", Float.valueOf(this.offsetY));
/* 1471 */         BukkitReflection.setValue(this.packet, true, "g", Float.valueOf(this.offsetZ));
/* 1472 */         BukkitReflection.setValue(this.packet, true, "h", Float.valueOf(this.speed));
/* 1473 */         BukkitReflection.setValue(this.packet, true, "i", Integer.valueOf(this.amount));
/* 1474 */       } catch (Exception exception) {
/* 1475 */         throw new PacketInstantiationException("Packet instantiation failed", exception);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void sendTo(Location center, Player player) throws PacketInstantiationException, PacketSendingException {
/* 1489 */       initializePacket(center);
/*      */       try {
/* 1491 */         sendPacket.invoke(playerConnection.get(getHandle.invoke(player, new Object[0])), new Object[] { this.packet });
/* 1492 */       } catch (Exception exception) {
/* 1493 */         throw new PacketSendingException("Failed to send the packet to player '" + player.getName() + "'", exception);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void sendTo(Location center, List<Player> players) throws IllegalArgumentException {
/* 1506 */       if (players.isEmpty()) {
/* 1507 */         throw new IllegalArgumentException("The player list is empty");
/*      */       }
/* 1509 */       for (Player player : players) {
/* 1510 */         sendTo(center, player);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void sendTo(Location center, double range) throws IllegalArgumentException {
/* 1523 */       if (range < 1.0D) {
/* 1524 */         throw new IllegalArgumentException("The range is lower than 1");
/*      */       }
/* 1526 */       String worldName = center.getWorld().getName();
/* 1527 */       double squared = range * range;
/* 1528 */       for (Player player : Bukkit.getOnlinePlayers()) {
/* 1529 */         if (!player.getWorld().getName().equals(worldName) || player.getLocation().distanceSquared(center) > squared) {
/*      */           continue;
/*      */         }
/* 1532 */         sendTo(center, player);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static final class VersionIncompatibleException
/*      */       extends RuntimeException
/*      */     {
/*      */       private static final long serialVersionUID = 3203085387160737484L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public VersionIncompatibleException(String message, Throwable cause) {
/* 1554 */         super(message, cause);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static final class PacketInstantiationException
/*      */       extends RuntimeException
/*      */     {
/*      */       private static final long serialVersionUID = 3203085387160737484L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public PacketInstantiationException(String message, Throwable cause) {
/* 1576 */         super(message, cause);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static final class PacketSendingException
/*      */       extends RuntimeException
/*      */     {
/*      */       private static final long serialVersionUID = 3203085387160737484L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public PacketSendingException(String message, Throwable cause) {
/* 1598 */         super(message, cause);
/*      */       }
/*      */     }
/*      */   }
/*      */ }