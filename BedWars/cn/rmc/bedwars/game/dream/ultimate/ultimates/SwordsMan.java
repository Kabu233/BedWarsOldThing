/*     */ package cn.rmc.bedwars.game.dream.ultimate.ultimates;
/*     */ 
/*     */ import java.util.UUID;
/*     */ import cn.rmc.bedwars.BedWars;
/*     */ import cn.rmc.bedwars.event.PlayerKillEvent;
/*     */ import cn.rmc.bedwars.event.PlayerSpawnEvent;
/*     */ import cn.rmc.bedwars.game.dream.ultimate.UltimateBasic;
/*     */ import cn.rmc.bedwars.game.dream.ultimate.UltimateType;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ import org.bukkit.scheduler.BukkitTask;
/*     */ import org.bukkit.util.Vector;
/*     */ 
/*     */ public class SwordsMan
/*     */   extends UltimateBasic
/*     */ {
/*     */   BukkitTask skill;
/*     */   Location lastLoc;
/*  24 */   Long lasttime = Long.valueOf(0L);
/*     */   boolean isteleported = false;
/*     */   
/*     */   public SwordsMan(UUID uuid) {
/*  28 */     super(uuid, UltimateType.SWORDSMAN);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getItem() {
/*  34 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlocking(Boolean isblock) {
/*  39 */     if (!isblock.booleanValue())
/*  40 */       return;  if (getPlayer().getExp() > 0.0F) {
/*  41 */       (new BukkitRunnable()
/*     */         {
/*     */           public void run() {
/*  44 */             if (SwordsMan.this.getPlayer().isBlocking() && SwordsMan.this.skill == null && SwordsMan.this.lastLoc != null && !SwordsMan.this.isteleported && System.currentTimeMillis() - SwordsMan.this.lasttime.longValue() >= 1500L) {
/*  45 */               SwordsMan.this.getPlayer().teleport(SwordsMan.this.lastLoc);
/*  46 */               SwordsMan.this.getPlayer().playSound(SwordsMan.this.getPlayer().getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 1.0F);
/*  47 */               SwordsMan.this.isteleported = true;
/*     */             } 
/*     */           }
/*  50 */         }).runTaskLater((Plugin)BedWars.getInstance(), 10L);
/*     */       return;
/*     */     } 
/*  53 */     if (this.skill == null && getPlayer().getExp() <= 0.0F) {
/*  54 */       this
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
/*     */         
/*  92 */         .skill = (new BukkitRunnable() { public void run() { if (!SwordsMan.this.getPlayer().isBlocking()) { SwordsMan.this.skill = null; return; }  (new BukkitRunnable() { public void run() { if (SwordsMan.this.getPlayer().isBlocking()) { if (SwordsMan.this.getPlayer().getExp() < 1.0F) { SwordsMan.this.getPlayer().setExp(SwordsMan.this.getPlayer().getExp() + 0.05F); SwordsMan.this.getPlayer().playSound(SwordsMan.this.getPlayer().getLocation(), Sound.NOTE_BASS, 1.0F, 1.0F); } else { SwordsMan.this.isteleported = false; SwordsMan.this.lastLoc = SwordsMan.this.getPlayer().getLocation(); Vector v = SwordsMan.this.getPlayer().getLocation().getDirection().multiply(2.0D).setY(1); SwordsMan.this.getPlayer().setVelocity(v); SwordsMan.this.getPlayer().setExp(0.0F); SwordsMan.this.getPlayer().setLevel(0); SwordsMan.this.initTask(); SwordsMan.this.lasttime = Long.valueOf(System.currentTimeMillis()); SwordsMan.this.skill = null; Bukkit.getScheduler().runTaskLater((Plugin)BedWars.getInstance(), () -> SwordsMan.this.isteleported = true, 20L * SwordsMan.this.getType().getCooldown().intValue()); cancel(); }  } else { SwordsMan.this.getPlayer().setExp(0.0F); SwordsMan.this.getPlayer().setLevel(0); SwordsMan.this.skill = null; cancel(); }  } }).runTaskTimerAsynchronously((Plugin)BedWars.getInstance(), 1L, 1L); } }).runTaskLaterAsynchronously((Plugin)BedWars.getInstance(), 10L);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onStart() {
/*  98 */     super.onStart();
/*  99 */     if (this.task == null) {
/* 100 */       this
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 109 */         .task = (new BukkitRunnable() { public void run() { if (SwordsMan.this.getPlayer() == null) { cancel(); } else { SwordsMan.this.onBlocking(Boolean.valueOf(SwordsMan.this.getPlayer().isBlocking())); }  } }).runTaskTimerAsynchronously((Plugin)BedWars.getInstance(), 2L, 2L);
/*     */     }
/*     */   }
/*     */   
/*     */   public void dispose() {
/* 114 */     super.dispose();
/* 115 */     if (this.skill != null) this.skill.cancel();
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public void onSpawn(PlayerSpawnEvent e) {
/* 121 */     if (this.task == null)
/* 122 */       this
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
/* 133 */         .task = (new BukkitRunnable() { public void run() { if (SwordsMan.this.getPlayer() == null) { System.out.println("b"); cancel(); } else { SwordsMan.this.onBlocking(Boolean.valueOf(SwordsMan.this.getPlayer().isBlocking())); }  } }).runTaskTimerAsynchronously((Plugin)BedWars.getInstance(), 2L, 2L); 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onDeath(PlayerKillEvent e) {
/* 138 */     if (getPd() != null) {
/* 139 */       if (this.skill != null) this.skill.cancel(); 
/* 140 */       this.skill = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onKill(PlayerKillEvent e) {
/* 146 */     if (this.exptask != null) this.exptask.cancel(); 
/* 147 */     if (this.skill != null) this.skill.cancel(); 
/* 148 */     getPlayer().setLevel(0);
/* 149 */     getPlayer().setExp(0.0F);
/*     */   }
/*     */ }