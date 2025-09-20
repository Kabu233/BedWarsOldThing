/*    */ package net.jitse.npclib.listeners;
/*    */ 
/*    */ import net.jitse.npclib.NPCLib;
/*    */ import net.jitse.npclib.internal.NPCBase;
/*    */ import net.jitse.npclib.internal.NPCManager;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.entity.PlayerDeathEvent;
/*    */ import org.bukkit.event.player.PlayerChangedWorldEvent;
/*    */ import org.bukkit.event.player.PlayerQuitEvent;
/*    */ import org.bukkit.event.player.PlayerRespawnEvent;
/*    */ import org.bukkit.event.player.PlayerTeleportEvent;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.scheduler.BukkitRunnable;
/*    */ 
/*    */ 
/*    */ public class PlayerListener
/*    */   extends HandleMoveBase
/*    */   implements Listener
/*    */ {
/*    */   private final NPCLib instance;
/*    */   
/*    */   public PlayerListener(NPCLib instance) {
/* 26 */     this.instance = instance;
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onPlayerQuit(PlayerQuitEvent event) {
/* 31 */     for (NPCBase npc : NPCManager.getAllNPCs()) {
/* 32 */       npc.onLogout(event.getPlayer());
/*    */     }
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onPlayerDeath(PlayerDeathEvent event) {
/* 38 */     Player player = event.getEntity();
/* 39 */     for (NPCBase npc : NPCManager.getAllNPCs()) {
/* 40 */       if (npc.isShown(player) && npc.getWorld().equals(player.getWorld())) {
/* 41 */         npc.hide(player, true);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @EventHandler
/*    */   public void onPlayerRespawn(PlayerRespawnEvent event) {
/* 49 */     final Player player = event.getPlayer();
/* 50 */     final Location respawn = event.getRespawnLocation();
/* 51 */     if (respawn.getWorld() != null && respawn.getWorld().equals(player.getWorld()))
/*    */     {
/*    */       
/* 54 */       (new BukkitRunnable()
/*    */         {
/*    */           public void run() {
/* 57 */             if (!player.isOnline()) {
/* 58 */               cancel();
/*    */               return;
/*    */             } 
/* 61 */             if (player.getLocation().equals(respawn)) {
/* 62 */               PlayerListener.this.handleMove(player);
/* 63 */               cancel();
/*    */             } 
/*    */           }
/* 66 */         }).runTaskTimer((Plugin)this.instance.getPlugin(), 0L, 1L);
/*    */     }
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
/* 72 */     Player player = event.getPlayer();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 81 */     handleMove(player);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onPlayerTeleport(PlayerTeleportEvent event) {
/* 86 */     handleMove(event.getPlayer());
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\BedWarsLobby-1.0-SNAPSHOT.jar!\net\jitse\npclib\listeners\PlayerListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */