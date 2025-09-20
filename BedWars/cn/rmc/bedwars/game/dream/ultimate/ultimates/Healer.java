/*     */ package cn.rmc.bedwars.game.dream.ultimate.ultimates;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import java.util.UUID;
/*     */ import cn.rmc.bedwars.BedWars;
/*     */ import cn.rmc.bedwars.event.PlayerKillEvent;
/*     */ import cn.rmc.bedwars.event.PlayerSpawnEvent;
/*     */ import cn.rmc.bedwars.game.PlayerData;
/*     */ import cn.rmc.bedwars.game.dream.ultimate.UltimateBasic;
/*     */ import cn.rmc.bedwars.game.dream.ultimate.UltimateType;
/*     */ import cn.rmc.bedwars.utils.item.ItemBuilder;
/*     */ import cn.rmc.bedwars.utils.world.ParticleEffects;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.entity.PotionSplashEvent;
/*     */ import org.bukkit.event.entity.ProjectileLaunchEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.potion.Potion;
/*     */ import org.bukkit.potion.PotionEffect;
/*     */ import org.bukkit.potion.PotionEffectType;
/*     */ import org.bukkit.potion.PotionType;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ import org.bukkit.scheduler.BukkitTask;
/*     */ import org.bukkit.util.Vector;
/*     */ 
/*     */ public class Healer
/*     */   extends UltimateBasic {
/*  34 */   static Random random = new Random(); ItemStack is;
/*     */   BukkitTask skill;
/*     */   
/*     */   public Healer(UUID uuid) {
/*  38 */     super(uuid, UltimateType.HEALER);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  43 */     this
/*  44 */       .is = (new ItemBuilder((new Potion(PotionType.REGEN)).splash().toItemStack(1))).setName("§a医师药水").toItemStack();
/*     */   }
/*     */   public ItemStack getItem() {
/*  47 */     return this.is;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onProjectileLaunch(ProjectileLaunchEvent e) {
/*  52 */     initTask();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPotionSplash(PotionSplashEvent e) {
/*  57 */     e.setCancelled(true);
/*     */     
/*  59 */     final Location loc = e.getEntity().getLocation();
/*     */     
/*  61 */     (new BukkitRunnable() {
/*  62 */         int i = 0;
/*     */ 
/*     */         
/*     */         public void run() {
/*  66 */           if (this.i >= 6) {
/*  67 */             cancel();
/*     */           }
/*  69 */           List<Location> locs = new ArrayList<>();
/*  70 */           for (int i = 0; i < 250; ) {
/*  71 */             double x = Math.random() * 5.0D * (Healer.random.nextBoolean() ? true : -1);
/*  72 */             double z = Math.random() * 5.0D * (Healer.random.nextBoolean() ? true : -1);
/*  73 */             Location add = loc.clone().add(x, 0.0D, z);
/*  74 */             if (loc.distance(add) > 5.0D)
/*  75 */               continue;  locs.add(add);
/*  76 */             i++;
/*     */           } 
/*  78 */           for (Location location : locs) {
/*  79 */             ParticleEffects.VILLAGER_HAPPY
/*  80 */               .display(new Vector(), 2000.0F, location, (Player[])Healer.this
/*  81 */                 .getPd().getTeam().getAlivePlayers().stream()
/*  82 */                 .map(PlayerData::getPlayer).toArray(x$0 -> new Player[x$0]));
/*     */           }
/*     */           
/*  85 */           for (PlayerData playerData : Healer.this.getPd().getTeam().getAlivePlayers()) {
/*  86 */             Bukkit.getScheduler().runTask((Plugin)BedWars.getInstance(), () -> {
/*     */                   if (playerData.getPlayer().getLocation().distance(loc) <= 5.0D) {
/*     */                     playerData.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 21, 0, false), true);
/*     */                   }
/*     */                 });
/*     */           } 
/*     */           
/*  93 */           this.i++;
/*     */         }
/*  95 */       }).runTaskTimerAsynchronously((Plugin)BedWars.getInstance(), 0L, 20L);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onSpawn(PlayerSpawnEvent e) {
/* 100 */     if (this.task == null)
/* 101 */       this
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 110 */         .task = (new BukkitRunnable() { public void run() { if (Healer.this.getPlayer() == null) { cancel(); } else { Healer.this.onBlocking(Boolean.valueOf(Healer.this.getPlayer().isBlocking())); }  } }).runTaskTimerAsynchronously((Plugin)BedWars.getInstance(), 2L, 2L); 
/*     */   } @EventHandler
/*     */   public void onDeath(PlayerKillEvent e) {
/* 113 */     if (getPd() != null) {
/* 114 */       this.skill = null;
/*     */     }
/*     */   }
/*     */   
/*     */   public void onStart() {
/* 119 */     super.onStart();
/* 120 */     if (this.task == null)
/* 121 */       this
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 130 */         .task = (new BukkitRunnable() { public void run() { if (Healer.this.getPlayer() == null) { cancel(); } else { Healer.this.onBlocking(Boolean.valueOf(Healer.this.getPlayer().isBlocking())); }  } }).runTaskTimerAsynchronously((Plugin)BedWars.getInstance(), 2L, 2L); 
/*     */   }
/*     */   
/*     */   public void dispose() {
/* 134 */     super.dispose();
/* 135 */     if (this.skill != null) this.skill.cancel();
/*     */   
/*     */   }
/*     */   
/*     */   public void onBlocking(Boolean isblock) {
/* 140 */     if (!isblock.booleanValue())
/* 141 */       return;  if (this.skill == null && getPlayer().getExp() <= 0.0F) {
/* 142 */       this
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 155 */         .skill = (new BukkitRunnable() { public void run() { if (!Healer.this.getPlayer().isBlocking()) return;  Bukkit.getScheduler().runTask((Plugin)BedWars.getInstance(), () -> { Healer.this.getPlayer().playSound(Healer.this.getPlayer().getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F); Healer.this.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 2400, 0)); Healer.this.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 1)); Healer.this.getPlayer().getInventory().remove(Healer.this.is); Healer.this.initTask(); }); Healer.this.skill = null; } }).runTaskLaterAsynchronously((Plugin)BedWars.getInstance(), 10L);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onTimerDone() {
/* 162 */     getPlayer().getInventory().addItem(new ItemStack[] { getItem() });
/*     */   }
/*     */ }