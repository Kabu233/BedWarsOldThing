/*    */ package cn.rmc.bedwars.utils.nametag;
/*    */ 
/*    */ import java.util.Map;
/*    */ import java.util.UUID;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.plugin.java.JavaPlugin;
/*    */ 
/*    */ public class NametagHandler {
/*    */   private JavaPlugin plugin;
/*    */   private NametagAdapter adapter;
/*    */   
/* 14 */   public void setPlugin(JavaPlugin plugin) { this.plugin = plugin; } private NametagThread thread; private NametagListeners listeners; private Map<UUID, NametagBoard> boards; public void setAdapter(NametagAdapter adapter) { this.adapter = adapter; } public void setThread(NametagThread thread) { this.thread = thread; } public void setListeners(NametagListeners listeners) { this.listeners = listeners; } public void setBoards(Map<UUID, NametagBoard> boards) { this.boards = boards; } public void setTicks(long ticks) { this.ticks = ticks; } public void setHook(boolean hook) { this.hook = hook; }
/*    */   
/*    */   public JavaPlugin getPlugin() {
/* 17 */     return this.plugin;
/*    */   }
/* 19 */   public NametagAdapter getAdapter() { return this.adapter; }
/* 20 */   public NametagThread getThread() { return this.thread; } public NametagListeners getListeners() {
/* 21 */     return this.listeners;
/*    */   } public Map<UUID, NametagBoard> getBoards() {
/* 23 */     return this.boards;
/* 24 */   } private long ticks = 2L; public long getTicks() { return this.ticks; } private boolean hook = false; public boolean isHook() {
/* 25 */     return this.hook;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public NametagHandler(JavaPlugin plugin, NametagAdapter adapter) {
/* 34 */     if (plugin == null) {
/* 35 */       throw new RuntimeException("Nametag Handler can not be instantiated without a plugin instance!");
/*    */     }
/*    */     
/* 38 */     this.plugin = plugin;
/* 39 */     this.adapter = adapter;
/* 40 */     this.boards = new ConcurrentHashMap<>();
/*    */     
/* 42 */     setup();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setup() {
/* 50 */     this.listeners = new NametagListeners(this);
/* 51 */     this.plugin.getServer().getPluginManager().registerEvents(this.listeners, (Plugin)this.plugin);
/*    */     
/* 53 */     for (Player player : Bukkit.getOnlinePlayers()) {
/* 54 */       getBoards().putIfAbsent(player.getUniqueId(), new NametagBoard(player, this));
/*    */     }
/*    */     
/* 57 */     this.thread = new NametagThread(this);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void cleanup() {
/* 65 */     if (this.thread != null) {
/* 66 */       this.thread.stop();
/* 67 */       this.thread = null;
/*    */     } 
/*    */ 
/*    */     
/* 71 */     if (this.listeners != null) {
/* 72 */       HandlerList.unregisterAll(this.listeners);
/* 73 */       this.listeners = null;
/*    */     } 
/*    */ 
/*    */     
/* 77 */     for (UUID uuid : getBoards().keySet()) {
/* 78 */       Player player = Bukkit.getPlayer(uuid);
/*    */       
/* 80 */       if (player == null || !player.isOnline()) {
/*    */         continue;
/*    */       }
/*    */       
/* 84 */       getBoards().remove(uuid);
/* 85 */       if (!isHook())
/* 86 */         player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard()); 
/*    */     } 
/*    */   }
/*    */ }