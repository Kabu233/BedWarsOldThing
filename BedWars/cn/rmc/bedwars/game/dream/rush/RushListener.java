/*     */ package cn.rmc.bedwars.game.dream.rush;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import cn.rmc.bedwars.BedWars;
/*     */ import cn.rmc.bedwars.enums.game.PlayerState;
/*     */ import cn.rmc.bedwars.enums.game.TeamState;
/*     */ import cn.rmc.bedwars.enums.shop.TeamUpgrade;
/*     */ import cn.rmc.bedwars.event.GameStartEvent;
/*     */ import cn.rmc.bedwars.event.PlayerSpawnEvent;
/*     */ import cn.rmc.bedwars.game.Game;
/*     */ import cn.rmc.bedwars.game.PlayerData;
/*     */ import cn.rmc.bedwars.game.ResourceSpawner;
/*     */ import cn.rmc.bedwars.game.Team;
/*     */ import cn.rmc.bedwars.utils.BlockUtils;
/*     */ import cn.rmc.bedwars.utils.world.LocationUtil;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.block.CraftBlock;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.block.Action;
/*     */ import org.bukkit.event.block.BlockPlaceEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.potion.PotionEffect;
/*     */ import org.bukkit.potion.PotionEffectType;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ import org.bukkit.util.Vector;
/*     */ 
/*     */ public class RushListener implements Listener {
/*     */   public RushListener(RushManager manager) {
/*  40 */     this.rushManager = manager;
/*     */   }
/*     */   RushManager rushManager;
/*     */   @EventHandler
/*     */   public void onStart(GameStartEvent e) {
/*  45 */     for (Team team : e.getGame().getTeams()) {
/*  46 */       team.getTeamUpgrade().put(TeamUpgrade.Forge, TeamUpgrade.Forge.getMaxLevel());
/*  47 */       team.getTeamUpgrade().put(TeamUpgrade.ManiacMiner, Integer.valueOf(1));
/*  48 */       if (team.getState() == TeamState.ALIVE) place(team); 
/*     */     } 
/*  50 */     for (PlayerData player : e.getGame().getOnlinePlayers()) {
/*  51 */       player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, 0, false, false), true);
/*  52 */       player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 99999, 0, false, false), true);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onInt(PlayerInteractEvent e) {
/*  59 */     if (e.getItem() == null || e.getItem().getType() != Material.WOOL)
/*  60 */       return;  PlayerData pd = BedWars.getInstance().getPlayerManager().get(e.getPlayer());
/*  61 */     if (e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_AIR) {
/*  62 */       this.rushManager.setState(pd, Boolean.valueOf(!this.rushManager.getState(pd).booleanValue()));
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(priority = EventPriority.LOWEST)
/*     */   public void onPlaceBlock(BlockPlaceEvent e) {
/*  68 */     if (e.isCancelled())
/*  69 */       return;  PlayerData pd = BedWars.getInstance().getPlayerManager().get(e.getPlayer());
/*  70 */     Game g = pd.getGame();
/*  71 */     if (e.getBlock().getType() != Material.WOOL || !this.rushManager.getState(pd).booleanValue())
/*  72 */       return;  Block block = e.getBlock();
/*  73 */     Vector vector = e.getBlock().getLocation().subtract(e.getBlockAgainst().getLocation()).toVector();
/*  74 */     placeBlock(block, vector, pd);
/*     */   }
/*     */ 
/*     */   
/*     */   public void placeBlock(final Block baseblock, final Vector vector, final PlayerData pd) {
/*  79 */     final Game g = pd.getGame();
/*  80 */     Bukkit.getScheduler().runTaskLater((Plugin)BedWars.getInstance(), () -> (new BukkitRunnable()
/*     */         {
/*  82 */           final byte by = ((CraftBlock)baseblock).getData();
/*  83 */           Block block = baseblock;
/*  84 */           int i = 1;
/*     */ 
/*     */           
/*     */           public void run() {
/*  88 */             if (this.i <= 5)
/*  89 */             { this.block = this.block.getLocation().add(vector).getBlock();
/*  90 */               if (!RushListener.this.hasRegion(g, this.block) && !RushListener.this.hasEntity(this.block) && this.block.getType() == Material.AIR)
/*  91 */               { this.block.getWorld().playSound(this.block.getLocation(), Sound.DIG_WOOL, 1.0F, 1.0F);
/*  92 */                 this.block.setType(Material.WOOL);
/*  93 */                 ((CraftBlock)this.block).setData(this.by);
/*  94 */                 pd.setBlocks_palced(pd.getBlocks_palced() + 1);
/*  95 */                 g.addBlock(this.block);
/*  96 */                 this.i++; }
/*  97 */               else { cancel(); }
/*     */                }
/*  99 */             else { cancel(); }
/*     */           
/*     */           }
/*     */         }).runTaskTimer((Plugin)BedWars.getInstance(), 0L, 2L), 2L);
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onRespawn(PlayerSpawnEvent e) {
/* 108 */     e.getPlayer().getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, 0, false, false), true);
/*     */   }
/*     */   
/*     */   public boolean hasRegion(Game g, Block block) {
/* 112 */     boolean hasRegion = false;
/* 113 */     for (Team team : g.getTeams()) {
/* 114 */       if (LocationUtil.hasRegion(block.getLocation(), team.getPos1(), team.getPos2())) {
/* 115 */         hasRegion = true;
/*     */         break;
/*     */       } 
/*     */     } 
/* 119 */     for (ResourceSpawner spawner : g.getSpawners()) {
/* 120 */       if (spawner.getLoc().distance(block.getLocation()) <= 2.0D) {
/* 121 */         hasRegion = true;
/*     */         break;
/*     */       } 
/*     */     } 
/* 125 */     return hasRegion;
/*     */   }
/*     */   
/*     */   public boolean hasEntity(Block block) {
/* 129 */     if (block == null) {
/* 130 */       return true;
/*     */     }
/* 132 */     if (block.getType() != Material.AIR) return true; 
/* 133 */     return isMan(block.getWorld().getNearbyEntities(block.getLocation().add(0.5D, -0.5D, 0.5D), 0.4999999D, 0.9999999D, 0.4999999D));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isMan(Collection<Entity> entities) {
/* 138 */     Iterator<Entity> iterator = entities.iterator(); if (iterator.hasNext()) { Entity entity = iterator.next();
/* 139 */       if (!(entity instanceof Player)) {
/* 140 */         return true;
/*     */       }
/* 142 */       PlayerData pd = BedWars.getInstance().getPlayerManager().get((Player)entity);
/* 143 */       return (pd.getState() == PlayerState.FIGHTING); }
/*     */ 
/*     */     
/* 146 */     return false;
/*     */   }
/*     */   
/*     */   public boolean place(Team team) {
/* 150 */     Game game = team.getGame();
/* 151 */     World world = team.getGame().getMap().getMiddle().getWorld();
/*     */     
/* 153 */     ArrayList<Location> Nloc1 = new ArrayList<>();
/* 154 */     Arrays.<Location>asList(new Location[] { team.getBedloc(), BlockUtils.getBedNeighbor(team.getBedloc().getBlock()).getLocation() }).forEach(loc -> {
/*     */           Nloc1.add(new Location(world, loc.getX(), loc.getY() + 1.0D, loc.getZ()));
/*     */           Nloc1.add(new Location(world, loc.getX() - 1.0D, loc.getY(), loc.getZ()));
/*     */           Nloc1.add(new Location(world, loc.getX() + 1.0D, loc.getY(), loc.getZ()));
/*     */           Nloc1.add(new Location(world, loc.getX(), loc.getY(), loc.getZ() - 1.0D));
/*     */           Nloc1.add(new Location(world, loc.getX(), loc.getY(), loc.getZ() + 1.0D));
/*     */         });
/* 161 */     Nloc1.removeIf(loc -> (loc.getBlock() != null && loc.getBlock().getType() != Material.AIR));
/* 162 */     Nloc1.forEach(block -> {
/*     */           block.getBlock().setType(Material.WOOD);
/*     */           
/*     */           game.addBlock(block.getBlock());
/*     */         });
/* 167 */     ArrayList<Location> Nloc2 = new ArrayList<>();
/* 168 */     Nloc1.forEach(loc -> {
/*     */           Nloc2.add(new Location(world, loc.getX(), loc.getY() + 1.0D, loc.getZ()));
/*     */           Nloc2.add(new Location(world, loc.getX() - 1.0D, loc.getY(), loc.getZ()));
/*     */           Nloc2.add(new Location(world, loc.getX() + 1.0D, loc.getY(), loc.getZ()));
/*     */           Nloc2.add(new Location(world, loc.getX(), loc.getY(), loc.getZ() - 1.0D));
/*     */           Nloc2.add(new Location(world, loc.getX(), loc.getY(), loc.getZ() + 1.0D));
/*     */         });
/* 175 */     Nloc2.removeIf(loc -> (loc.getBlock() != null && loc.getBlock().getType() != Material.AIR));
/* 176 */     Nloc2.forEach(block -> {
/*     */           block.getBlock().setType(Material.WOOL);
/*     */           
/*     */           ((CraftBlock)block.getBlock()).setData(team.getTeamType().getDyeColor().getData());
/*     */           game.addBlock(block.getBlock());
/*     */         });
/* 182 */     ArrayList<Location> Nloc3 = new ArrayList<>();
/* 183 */     Nloc2.forEach(loc -> {
/*     */           Nloc3.add(new Location(world, loc.getX(), loc.getY() + 1.0D, loc.getZ()));
/*     */           Nloc3.add(new Location(world, loc.getX() - 1.0D, loc.getY(), loc.getZ()));
/*     */           Nloc3.add(new Location(world, loc.getX() + 1.0D, loc.getY(), loc.getZ()));
/*     */           Nloc3.add(new Location(world, loc.getX(), loc.getY(), loc.getZ() - 1.0D));
/*     */           Nloc3.add(new Location(world, loc.getX(), loc.getY(), loc.getZ() + 1.0D));
/*     */         });
/* 190 */     Nloc3.removeIf(loc -> (loc.getBlock() != null && loc.getBlock().getType() != Material.AIR));
/* 191 */     Nloc3.forEach(block -> {
/*     */           block.getBlock().setType(Material.STAINED_GLASS);
/*     */           ((CraftBlock)block.getBlock()).setData(team.getTeamType().getDyeColor().getData());
/*     */           game.addBlock(block.getBlock());
/*     */         });
/* 196 */     return true;
/*     */   }
/*     */ }