/*     */ package net.jitse.npclib;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.jitse.npclib.api.NPC;
/*     */ import net.jitse.npclib.api.utilities.Logger;
/*     */ import net.jitse.npclib.listeners.ChunkListener;
/*     */ import net.jitse.npclib.listeners.PacketListener;
/*     */ import net.jitse.npclib.listeners.PeriodicMoveListener;
/*     */ import net.jitse.npclib.listeners.PlayerListener;
/*     */ import net.jitse.npclib.listeners.PlayerMoveEventListener;
/*     */ import net.jitse.npclib.metrics.NPCLibMetrics;
/*     */ import net.jitse.npclib.nms.v1_8_R3.NPC_v1_8_R3;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ import org.bukkit.plugin.java.JavaPlugin;
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class NPCLib
/*     */ {
/*     */   private final JavaPlugin plugin;
/*     */   private final Logger logger;
/*  24 */   private Class<?> npcClass = null;
/*     */   
/*  26 */   private double autoHideDistance = 50.0D;
/*     */   
/*     */   private NPCLib(JavaPlugin plugin, NPCLibOptions.MovementHandling moveHandling) {
/*  29 */     this.plugin = plugin;
/*  30 */     this.logger = new Logger("NPCLib");
/*     */ 
/*     */ 
/*     */     
/*  34 */     this.npcClass = NPC_v1_8_R3.class;
/*     */     
/*  36 */     PluginManager pluginManager = plugin.getServer().getPluginManager();
/*     */     
/*  38 */     pluginManager.registerEvents((Listener)new PlayerListener(this), (Plugin)plugin);
/*  39 */     pluginManager.registerEvents((Listener)new ChunkListener(this), (Plugin)plugin);
/*     */     
/*  41 */     if (moveHandling.usePme) {
/*  42 */       pluginManager.registerEvents((Listener)new PlayerMoveEventListener(), (Plugin)plugin);
/*     */     } else {
/*  44 */       pluginManager.registerEvents((Listener)new PeriodicMoveListener(this, moveHandling.updateInterval), (Plugin)plugin);
/*     */     } 
/*     */ 
/*     */     
/*  48 */     (new PacketListener()).start(this);
/*     */ 
/*     */     
/*  51 */     System.setProperty("bstats.relocatecheck", "false");
/*  52 */     new NPCLibMetrics(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NPCLib(JavaPlugin plugin) {
/*  58 */     this(plugin, NPCLibOptions.MovementHandling.playerMoveEvent());
/*     */   }
/*     */   
/*     */   public NPCLib(JavaPlugin plugin, NPCLibOptions options) {
/*  62 */     this(plugin, options.moveHandling);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JavaPlugin getPlugin() {
/*  69 */     return this.plugin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAutoHideDistance(double autoHideDistance) {
/*  79 */     this.autoHideDistance = autoHideDistance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getAutoHideDistance() {
/*  86 */     return this.autoHideDistance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Logger getLogger() {
/*  93 */     return this.logger;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NPC createNPC(List<String> text) {
/*     */     try {
/* 104 */       return (NPC)this.npcClass.getConstructors()[0].newInstance(new Object[] { this, text });
/* 105 */     } catch (Exception exception) {
/* 106 */       this.logger.warning("Failed to create NPC. Please report the following stacktrace message", exception);
/*     */ 
/*     */       
/* 109 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NPC createNPC() {
/* 118 */     return createNPC(null);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\BedWarsLobby-1.0-SNAPSHOT.jar!\net\jitse\npclib\NPCLib.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */