/*     */ package net.jitse.npclib.internal;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.properties.Property;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import net.jitse.npclib.NPCLib;
/*     */ import net.jitse.npclib.api.NPC;
/*     */ import net.jitse.npclib.api.events.NPCHideEvent;
/*     */ import net.jitse.npclib.api.events.NPCShowEvent;
/*     */ import net.jitse.npclib.api.skin.Skin;
/*     */ import net.jitse.npclib.api.state.NPCAnimation;
/*     */ import net.jitse.npclib.api.state.NPCSlot;
/*     */ import net.jitse.npclib.api.state.NPCState;
/*     */ import net.jitse.npclib.hologram.Hologram;
/*     */ import net.jitse.npclib.utilities.MathUtil;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.util.Vector;
/*     */ 
/*     */ public abstract class NPCBase implements NPC, NPCPacketHandler {
/*  31 */   protected final int entityId = Integer.MAX_VALUE - NPCManager.getAllNPCs().size();
/*  32 */   protected final Set<UUID> hasTeamRegistered = new HashSet<>();
/*  33 */   protected final Set<NPCState> activeStates = EnumSet.noneOf(NPCState.class);
/*     */   
/*     */   protected final MinecraftVersion version;
/*  36 */   private final Set<UUID> shown = new HashSet<>();
/*  37 */   private final Set<UUID> autoHidden = new HashSet<>();
/*     */   
/*  39 */   protected double cosFOV = Math.cos(Math.toRadians(60.0D));
/*     */ 
/*     */   
/*  42 */   protected UUID uuid = new UUID((new Random()).nextLong(), 0L);
/*  43 */   protected String name = this.uuid.toString().replace("-", "").substring(0, 10);
/*  44 */   protected GameProfile gameProfile = new GameProfile(this.uuid, this.name);
/*     */   
/*     */   protected boolean created = false;
/*     */   
/*     */   protected NPCLib instance;
/*     */   
/*     */   protected List<String> text;
/*     */   
/*     */   protected Location location;
/*     */   protected Skin skin;
/*  54 */   protected final Map<NPCSlot, ItemStack> items = new EnumMap<>(NPCSlot.class);
/*     */ 
/*     */   
/*  57 */   protected final Map<UUID, List<String>> playerText = new HashMap<>();
/*  58 */   protected final Map<UUID, Hologram> playerHologram = new HashMap<>();
/*     */   
/*     */   public NPCBase(NPCLib instance, List<String> text, MinecraftVersion version) {
/*  61 */     this.instance = instance;
/*  62 */     this.text = (text != null) ? text : Collections.<String>emptyList();
/*  63 */     this.version = version;
/*     */     
/*  65 */     NPCManager.add(this);
/*     */   }
/*     */   
/*     */   public NPCLib getInstance() {
/*  69 */     return this.instance;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Hologram getHologram(Player player) {
/*  74 */     Validate.notNull(player, "Player cannot be null.");
/*  75 */     return this.playerHologram.getOrDefault(player.getUniqueId(), null);
/*     */   }
/*     */ 
/*     */   
/*     */   public NPC removeText(Player player) {
/*  80 */     Validate.notNull(player, "Player cannot be null.");
/*  81 */     setText(player, (List<String>)null);
/*  82 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public NPC setText(Player player, List<String> text) {
/*  87 */     Validate.notNull(player, "Player cannot be null.");
/*  88 */     List<String> originalText = getText(player);
/*     */     
/*  90 */     if (text == null)
/*  91 */     { this.playerText.remove(player.getUniqueId()); }
/*  92 */     else { this.playerText.put(player.getUniqueId(), text); }
/*     */     
/*  94 */     if (originalText.size() != text.size()) {
/*  95 */       Hologram originalHologram = getHologram(player);
/*  96 */       originalHologram.hide(player);
/*  97 */       this.playerHologram.remove(player.getUniqueId());
/*     */     } 
/*  99 */     if (isShown(player)) {
/* 100 */       if (originalText.size() != text.size()) {
/* 101 */         getHologram(player).show(player);
/*     */       } else {
/* 103 */         Hologram hologram = getHologram(player);
/* 104 */         List<Object> updatePackets = hologram.getUpdatePackets(text);
/* 105 */         hologram.update(player, updatePackets);
/*     */       } 
/*     */     }
/* 108 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public NPC setText(List<String> text) {
/* 113 */     for (UUID uuid : this.shown) {
/* 114 */       Player player = Bukkit.getPlayer(uuid);
/* 115 */       if (player == null)
/* 116 */         continue;  setText(player, text);
/*     */     } 
/* 118 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getText(Player player) {
/* 123 */     Validate.notNull(player, "Player cannot be null.");
/* 124 */     return this.playerText.getOrDefault(player.getUniqueId(), this.text);
/*     */   }
/*     */ 
/*     */   
/*     */   public UUID getUniqueId() {
/* 129 */     return this.uuid;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getId() {
/* 134 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public NPC setSkin(Skin skin) {
/* 139 */     this.skin = skin;
/*     */     
/* 141 */     this.gameProfile.getProperties().get("textures").clear();
/* 142 */     if (skin != null) {
/* 143 */       this.gameProfile.getProperties().put("textures", new Property("textures", skin.getValue(), skin.getSignature()));
/*     */     }
/* 145 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void destroy() {
/* 150 */     NPCManager.remove(this);
/*     */ 
/*     */     
/* 153 */     for (UUID uuid : this.shown) {
/* 154 */       if (this.autoHidden.contains(uuid))
/* 155 */         continue;  Player player = Bukkit.getPlayer(uuid);
/* 156 */       if (player != null) hide(player, true); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void disableFOV() {
/* 161 */     this.cosFOV = 0.0D;
/*     */   }
/*     */   
/*     */   public void setFOV(double fov) {
/* 165 */     this.cosFOV = Math.cos(Math.toRadians(fov));
/*     */   }
/*     */   
/*     */   public Set<UUID> getShown() {
/* 169 */     return this.shown;
/*     */   }
/*     */   
/*     */   public Set<UUID> getAutoHidden() {
/* 173 */     return this.autoHidden;
/*     */   }
/*     */ 
/*     */   
/*     */   public Location getLocation() {
/* 178 */     return this.location;
/*     */   }
/*     */ 
/*     */   
/*     */   public World getWorld() {
/* 183 */     return (this.location != null) ? this.location.getWorld() : null;
/*     */   }
/*     */   
/*     */   public int getEntityId() {
/* 187 */     return this.entityId;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isShown(Player player) {
/* 192 */     Objects.requireNonNull(player, "Player object cannot be null");
/* 193 */     return (this.shown.contains(player.getUniqueId()) && !this.autoHidden.contains(player.getUniqueId()));
/*     */   }
/*     */ 
/*     */   
/*     */   public NPC setLocation(Location location) {
/* 198 */     this.location = location;
/* 199 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public NPC create() {
/* 204 */     createPackets();
/* 205 */     this.created = true;
/* 206 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCreated() {
/* 211 */     return this.created;
/*     */   }
/*     */   
/*     */   public void onLogout(Player player) {
/* 215 */     getAutoHidden().remove(player.getUniqueId());
/* 216 */     getShown().remove(player.getUniqueId());
/* 217 */     this.hasTeamRegistered.remove(player.getUniqueId());
/*     */   }
/*     */   
/*     */   public boolean inRangeOf(Player player) {
/* 221 */     if (player == null) return false; 
/* 222 */     if (!player.getWorld().equals(this.location.getWorld()))
/*     */     {
/* 224 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 229 */     double hideDistance = this.instance.getAutoHideDistance();
/* 230 */     double distanceSquared = player.getLocation().distanceSquared(this.location);
/* 231 */     double bukkitRange = (Bukkit.getViewDistance() << 4);
/*     */     
/* 233 */     return (distanceSquared <= MathUtil.square(hideDistance) && distanceSquared <= MathUtil.square(bukkitRange));
/*     */   }
/*     */   
/*     */   public boolean inViewOf(Player player) {
/* 237 */     Vector dir = this.location.toVector().subtract(player.getEyeLocation().toVector()).normalize();
/* 238 */     return (dir.dot(player.getEyeLocation().getDirection()) >= this.cosFOV);
/*     */   }
/*     */ 
/*     */   
/*     */   public void show(Player player) {
/* 243 */     show(player, false);
/*     */   }
/*     */   
/*     */   public void show(Player player, boolean auto) {
/* 247 */     NPCShowEvent event = new NPCShowEvent(this, player, auto);
/* 248 */     Bukkit.getServer().getPluginManager().callEvent((Event)event);
/* 249 */     if (event.isCancelled()) {
/*     */       return;
/*     */     }
/*     */     
/* 253 */     if (isShown(player)) {
/* 254 */       throw new IllegalArgumentException("NPC is already shown to player");
/*     */     }
/*     */     
/* 257 */     if (auto) {
/* 258 */       sendShowPackets(player);
/* 259 */       sendMetadataPacket(player);
/* 260 */       sendEquipmentPackets(player);
/*     */ 
/*     */       
/* 263 */       this.autoHidden.remove(player.getUniqueId());
/*     */     } else {
/*     */       
/* 266 */       this.shown.add(player.getUniqueId());
/*     */       
/* 268 */       if (inRangeOf(player) && inViewOf(player)) {
/*     */         
/* 270 */         sendShowPackets(player);
/* 271 */         sendMetadataPacket(player);
/* 272 */         sendEquipmentPackets(player);
/*     */       } else {
/*     */         
/* 275 */         this.autoHidden.add(player.getUniqueId());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void hide(Player player) {
/* 282 */     hide(player, false);
/*     */   }
/*     */   
/*     */   public void hide(Player player, boolean auto) {
/* 286 */     NPCHideEvent event = new NPCHideEvent(this, player, auto);
/* 287 */     Bukkit.getServer().getPluginManager().callEvent((Event)event);
/* 288 */     if (event.isCancelled()) {
/*     */       return;
/*     */     }
/*     */     
/* 292 */     if (!this.shown.contains(player.getUniqueId())) {
/* 293 */       throw new IllegalArgumentException("NPC cannot be hidden from player before calling NPC#show first");
/*     */     }
/*     */     
/* 296 */     if (auto) {
/* 297 */       if (this.autoHidden.contains(player.getUniqueId())) {
/* 298 */         throw new IllegalStateException("NPC cannot be auto-hidden twice");
/*     */       }
/*     */       
/* 301 */       sendHidePackets(player);
/*     */ 
/*     */       
/* 304 */       this.autoHidden.add(player.getUniqueId());
/*     */     } else {
/*     */       
/* 307 */       this.shown.remove(player.getUniqueId());
/*     */       
/* 309 */       if (inRangeOf(player)) {
/*     */         
/* 311 */         sendHidePackets(player);
/*     */       } else {
/*     */         
/* 314 */         this.autoHidden.remove(player.getUniqueId());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getState(NPCState state) {
/* 321 */     return this.activeStates.contains(state);
/*     */   }
/*     */ 
/*     */   
/*     */   public NPC toggleState(NPCState state) {
/* 326 */     if (this.activeStates.contains(state)) {
/* 327 */       this.activeStates.remove(state);
/*     */     } else {
/* 329 */       this.activeStates.add(state);
/*     */     } 
/*     */ 
/*     */     
/* 333 */     for (UUID shownUuid : this.shown) {
/* 334 */       Player player = Bukkit.getPlayer(shownUuid);
/* 335 */       if (player != null && isShown(player)) {
/* 336 */         sendMetadataPacket(player);
/*     */       }
/*     */     } 
/* 339 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void playAnimation(NPCAnimation animation) {
/* 344 */     for (UUID shownUuid : this.shown) {
/* 345 */       Player player = Bukkit.getPlayer(shownUuid);
/* 346 */       if (player != null && isShown(player)) {
/* 347 */         sendAnimationPacket(player, animation);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getItem(NPCSlot slot) {
/* 354 */     Objects.requireNonNull(slot, "Slot cannot be null");
/*     */     
/* 356 */     return this.items.get(slot);
/*     */   }
/*     */ 
/*     */   
/*     */   public NPC setItem(NPCSlot slot, ItemStack item) {
/* 361 */     Objects.requireNonNull(slot, "Slot cannot be null");
/*     */     
/* 363 */     this.items.put(slot, item);
/*     */     
/* 365 */     for (UUID shownUuid : this.shown) {
/* 366 */       Player player = Bukkit.getPlayer(shownUuid);
/* 367 */       if (player != null && isShown(player)) {
/* 368 */         sendEquipmentPacket(player, slot, false);
/*     */       }
/*     */     } 
/* 371 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getText() {
/* 376 */     return this.text;
/*     */   }
/*     */ 
/*     */   
/*     */   public void lookAt(Location location) {
/* 381 */     sendHeadRotationPackets(location);
/*     */   }
/*     */ 
/*     */   
/*     */   public MinecraftVersion getMinecraftVersion() {
/* 386 */     return this.version;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\BedWarsLobby-1.0-SNAPSHOT.jar!\net\jitse\npclib\internal\NPCBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */