/*     */ package cn.rmc.bedwars.listener;
/*     */ import java.util.HashMap;
/*     */ import java.util.Random;
/*     */ import java.util.concurrent.ThreadLocalRandom;
/*     */ import cn.rmc.bedwars.BedWars;
/*     */ import cn.rmc.bedwars.enums.game.PlayerState;
/*     */ import cn.rmc.bedwars.enums.game.TeamState;
/*     */ import cn.rmc.bedwars.game.PlayerData;
/*     */ import cn.rmc.bedwars.game.Team;
/*     */ import cn.rmc.bedwars.inventory.game.CompassMenu;
/*     */ import cn.rmc.bedwars.inventory.lobby.TeamSelector;
/*     */ import cn.rmc.bedwars.inventory.spec.SpecSelector;
/*     */ import cn.rmc.bedwars.inventory.spec.SpecSettings;
/*     */ import cn.rmc.bedwars.utils.player.BungeeUtil;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.block.Action;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.event.player.PlayerQuitEvent;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.scheduler.BukkitTask;
/*     */ 
/*     */ public class PlayerInteractListener implements Listener {
/*  28 */   private final HashMap<Player, BukkitTask> wait = new HashMap<>();
/*     */   @EventHandler
/*     */   public void onPlayerInteract(PlayerInteractEvent e) {
/*  31 */     Player p = e.getPlayer();
/*  32 */     PlayerData pd = BedWars.getInstance().getPlayerManager().get(p);
/*  33 */     switch (pd.getState()) {
/*     */ 
/*     */       
/*     */       case WAITING:
/*  37 */         if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK)
/*  38 */           return;  if (e.getItem() == null)
/*  39 */           return;  switch (e.getItem().getType()) {
/*     */           case SPAWNING:
/*  41 */             addLobbyQueue(p);
/*     */             break;
/*     */           case WAITING:
/*  44 */             (new TeamSelector(p)).open();
/*     */             break;
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           case FIGHTING:
/*  51 */             if (pd.getGame().getCountRunnable() != null) pd.getGame().getCountRunnable().cancel(); 
/*  52 */             pd.getGame().start();
/*     */             break;
/*     */         } 
/*     */         
/*     */         break;
/*     */       case FIGHTING:
/*  58 */         if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK)
/*  59 */           return;  if (e.getAction() == Action.RIGHT_CLICK_BLOCK && 
/*  60 */           e.getClickedBlock() != null && e.getClickedBlock().getType() == Material.CHEST) {
/*  61 */           for (Team team : pd.getGame().getTeams()) {
/*  62 */             if (e.getClickedBlock().getLocation().distance(team.getChestloc()) <= 5.0D) {
/*  63 */               if (team.getState() != TeamState.DEAD && team != pd.getTeam()) {
/*  64 */                 e.setCancelled(true);
/*  65 */                 p.sendMessage("§c该队伍尚未团灭，无法打开他们的箱子!");
/*     */               } 
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         }
/*  72 */         if ((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && 
/*  73 */           e.getItem() != null && e.getItem().getType() == Material.COMPASS) {
/*  74 */           (new CompassMenu(p)).open();
/*     */         }
/*     */         break;
/*     */       
/*     */       case RESPAWNING:
/*     */       case SPECING:
/*  80 */         e.setCancelled(true);
/*  81 */         if (e.getItem() == null)
/*  82 */           return;  switch (e.getItem().getType()) {
/*     */           case RESPAWNING:
/*  84 */             (new SpecSelector(p)).open();
/*     */             break;
/*     */           case SPECING:
/*  87 */             (new SpecSettings(p)).open();
/*     */             break;
/*     */           case null:
/*  90 */             BungeeUtil.sendPlayer(pd.getPlayer(), BedWars.getInstance().getLobbies().get(ThreadLocalRandom.current().nextInt(BedWars.getInstance().getLobbies().size())));
/*     */             break;
/*     */           case SPAWNING:
/*  93 */             addLobbyQueue(p);
/*     */             break;
/*     */         } 
/*     */         break;
/*     */     } 
/*     */   }
/*     */   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
/*     */   public void onQuit(PlayerQuitEvent e) {
/* 101 */     if (this.wait.containsKey(e.getPlayer())) {
/* 102 */       ((BukkitTask)this.wait.get(e.getPlayer())).cancel();
/* 103 */       this.wait.remove(e.getPlayer());
/*     */     } 
/*     */   }
/*     */   
/*     */   private void addLobbyQueue(Player p) {
/* 108 */     if (this.wait.containsKey(p)) {
/* 109 */       p.sendMessage("§c§l传送取消了!");
/* 110 */       ((BukkitTask)this.wait.get(p)).cancel();
/* 111 */       this.wait.remove(p);
/*     */     } else {
/* 113 */       p.sendMessage("§a§l3秒后将你传送到大厅...再次右键以取消传送!");
/* 114 */       BukkitTask result = Bukkit.getScheduler().runTaskLater((Plugin)BedWars.getInstance(), () -> BungeeUtil.sendPlayer(p, BedWars.getInstance().getLobbies().get((new Random()).nextInt(BedWars.getInstance().getLobbies().size()))), 60L);
/*     */ 
/*     */       
/* 117 */       this.wait.put(p, result);
/*     */     } 
/*     */   }
/*     */ }