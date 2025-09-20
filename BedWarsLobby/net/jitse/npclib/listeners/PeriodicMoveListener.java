/*    */ package net.jitse.npclib.listeners;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.UUID;
/*    */ import net.jitse.npclib.NPCLib;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.player.PlayerJoinEvent;
/*    */ import org.bukkit.event.player.PlayerQuitEvent;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.scheduler.BukkitTask;
/*    */ 
/*    */ public class PeriodicMoveListener
/*    */   extends HandleMoveBase
/*    */   implements Listener {
/*    */   private final NPCLib instance;
/*    */   private final long updateInterval;
/* 20 */   private final HashMap<UUID, BukkitTask> tasks = new HashMap<>();
/*    */   
/*    */   public PeriodicMoveListener(NPCLib instance, long updateInterval) {
/* 23 */     this.instance = instance;
/* 24 */     this.updateInterval = updateInterval;
/*    */   }
/*    */ 
/*    */   
/*    */   private void startTask(UUID uuid) {
/* 29 */     this.tasks.put(uuid, Bukkit.getScheduler().runTaskTimer((Plugin)this.instance.getPlugin(), () -> { Player player = Bukkit.getPlayer(uuid); if (player != null) handleMove(player);  }1L, this.updateInterval));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @EventHandler
/*    */   public void onPlayerJoin(PlayerJoinEvent event) {
/* 39 */     startTask(event.getPlayer().getUniqueId());
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onPlayerQuit(PlayerQuitEvent event) {
/* 44 */     BukkitTask task = this.tasks.remove(event.getPlayer().getUniqueId());
/* 45 */     if (task != null)
/* 46 */       task.cancel(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\BedWarsLobby-1.0-SNAPSHOT.jar!\net\jitse\npclib\listeners\PeriodicMoveListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */