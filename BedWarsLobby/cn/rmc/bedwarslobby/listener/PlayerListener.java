/*     */ package cn.rmc.bedwarslobby.listener;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import cn.rmc.bedwarslobby.BedWarsLobby;
/*     */ import cn.rmc.bedwarslobby.inventory.MenuBasic;
/*     */ import cn.rmc.bedwarslobby.loot.LootChestBasic;
/*     */ import cn.rmc.bedwarslobby.loot.utils.CameraUtils;
/*     */ import cn.rmc.bedwarslobby.loot.utils.SitUtils;
/*     */ import cn.rmc.bedwarslobby.util.PlayerUtils;
/*     */ import cn.rmc.bedwarslobby.util.TitleUtils;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.GameMode;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.entity.EntityDamageEvent;
/*     */ import org.bukkit.event.inventory.InventoryClickEvent;
/*     */ import org.bukkit.event.inventory.InventoryOpenEvent;
/*     */ import org.bukkit.event.inventory.InventoryType;
/*     */ import org.bukkit.event.player.PlayerDropItemEvent;
/*     */ import org.bukkit.event.player.PlayerItemHeldEvent;
/*     */ import org.bukkit.event.player.PlayerJoinEvent;
/*     */ import org.bukkit.event.player.PlayerMoveEvent;
/*     */ import org.bukkit.event.player.PlayerQuitEvent;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.spigotmc.event.entity.EntityDismountEvent;
/*     */ 
/*     */ public class PlayerListener implements Listener {
/*     */   @EventHandler
/*     */   public void onJoin(PlayerJoinEvent e) {
/*  33 */     e.setJoinMessage(null);
/*     */     
/*  35 */     Player p = e.getPlayer();
/*  36 */     e.getPlayer().teleport(new Location(Bukkit.getWorld("world"), -8.5D, 75.0D, 0.0D, 90.0F, 0.0F));
/*  37 */     PlayerUtils.setFly(p);
/*  38 */     PlayerUtils.giveLobbyItem(p);
/*  39 */     TitleUtils.sendFullTitle(p, Integer.valueOf(0), Integer.valueOf(100), Integer.valueOf(0), "§e§l起床战争", "");
/*  40 */     for (int i = 0; i < 25; i++) {
/*  41 */       p.sendMessage(" ");
/*     */     }
/*  43 */     BedWarsLobby.getInstance().getPlayerManager().add(e.getPlayer());
/*  44 */     BedWarsLobby.getInstance().getScoreBoardManager().sendScoreBoard(p);
/*  45 */     e.getPlayer().setGameMode(GameMode.ADVENTURE);
/*  46 */     Bukkit.getScheduler().runTaskLater((Plugin)BedWarsLobby.getInstance(), () -> PlayerUtils.giveLobbyItem(p), 10L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onMove(PlayerMoveEvent e) {
/*  56 */     if (e.getTo().getY() <= 0.0D) {
/*  57 */       e.getPlayer().teleport(new Location(Bukkit.getWorld("world"), -8.5D, 75.0D, 0.0D, 90.0F, 0.0F));
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onDamage(EntityDamageEvent e) {
/*  63 */     if (e.getEntity() instanceof Player) {
/*  64 */       e.setCancelled(true);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onInventoryOpen(InventoryOpenEvent e) {
/*  70 */     if (CameraUtils.players.contains(e.getPlayer()))
/*  71 */       e.setCancelled(true); 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onInvMove(InventoryClickEvent e) {
/*  76 */     if (e.getClickedInventory().getType() == InventoryType.PLAYER) {
/*  77 */       if (e.getWhoClicked().getGameMode() == GameMode.CREATIVE)
/*  78 */         return;  e.setCancelled(true);
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void ondrop(PlayerDropItemEvent e) {
/*  84 */     e.setCancelled(true);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onChangeInv(PlayerItemHeldEvent e) {
/*  89 */     if (CameraUtils.players.contains(e.getPlayer()) && !SitUtils.sitted.containsKey(e.getPlayer()))
/*  90 */       e.setCancelled(true); 
/*  91 */     e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.NOTE_STICKS, 1.0F, 1.0F);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onQuit(PlayerQuitEvent e) {
/*  96 */     e.setQuitMessage(null);
/*     */     
/*  98 */     if (MenuBasic.uis.containsKey(e.getPlayer())) {
/*  99 */       ((ArrayList)MenuBasic.uis.get(e.getPlayer())).forEach(MenuBasic::destery);
/*     */     }
/* 101 */     BedWarsLobby.getInstance().getPlayerManager().remove(e.getPlayer());
/* 102 */     SitUtils.unsitPlayer(e.getPlayer());
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onDismount(EntityDismountEvent e) {
/* 107 */     if (e.getEntity() instanceof Player) {
/* 108 */       Player p = (Player)e.getEntity();
/* 109 */       if (!LootChestBasic.chests.containsKey(p) || CameraUtils.players.contains(p) || !SitUtils.sitted.containsKey(p)) {
/*     */         return;
/*     */       }
/* 112 */       ((LootChestBasic)LootChestBasic.chests.get(p)).Close(p);
/*     */     } 
/*     */   }
/*     */ }