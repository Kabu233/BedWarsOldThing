/*     */ package cn.rmc.bedwars.game.dream.ultimate.ultimates;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import java.util.UUID;
/*     */ import cn.rmc.bedwars.BedWars;
/*     */ import cn.rmc.bedwars.enums.game.PlayerState;
/*     */ import cn.rmc.bedwars.event.PlayerKillEvent;
/*     */ import cn.rmc.bedwars.game.PlayerData;
/*     */ import cn.rmc.bedwars.game.dream.ultimate.UltimateBasic;
/*     */ import cn.rmc.bedwars.game.dream.ultimate.UltimateType;
/*     */ import cn.rmc.bedwars.utils.item.ItemBuilder;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Effect;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.event.entity.PotionSplashEvent;
/*     */ import org.bukkit.event.entity.ProjectileLaunchEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.potion.Potion;
/*     */ import org.bukkit.potion.PotionEffect;
/*     */ import org.bukkit.potion.PotionEffectType;
/*     */ import org.bukkit.potion.PotionType;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ 
/*     */ public class Frozo extends UltimateBasic {
/*  30 */   static Random random = new Random();
/*     */   ItemStack is;
/*     */   
/*     */   public Frozo(UUID uuid) {
/*  34 */     super(uuid, UltimateType.FROZO);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  39 */     this
/*  40 */       .is = (new ItemBuilder((new Potion(PotionType.SLOWNESS)).splash().toItemStack(1))).setName("§a冰霜法师药水").toItemStack();
/*     */   }
/*     */   public ItemStack getItem() {
/*  43 */     return this.is;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onProjectileLaunch(ProjectileLaunchEvent e) {
/*  50 */     initTask();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPotionSplash(PotionSplashEvent e) {
/*  55 */     e.setCancelled(true);
/*     */     
/*  57 */     final Location loc = e.getEntity().getLocation();
/*     */     
/*  59 */     (new BukkitRunnable() {
/*  60 */         int i = 0;
/*     */ 
/*     */         
/*     */         public void run() {
/*  64 */           if (this.i >= 24) {
/*  65 */             cancel();
/*     */           }
/*  67 */           List<Location> locs = new ArrayList<>();
/*  68 */           for (int i = 0; i < 200; ) {
/*  69 */             double x = Math.random() * 5.0D * (Frozo.random.nextBoolean() ? true : -1);
/*  70 */             double z = Math.random() * 5.0D * (Frozo.random.nextBoolean() ? true : -1);
/*  71 */             Location add = loc.clone().add(x, 0.0D, z);
/*  72 */             if (loc.distance(add) > 5.0D)
/*  73 */               continue;  locs.add(add);
/*  74 */             i++;
/*     */           } 
/*  76 */           for (Location location : locs) {
/*  77 */             location.getWorld().playEffect(location, Effect.SNOWBALL_BREAK, 1);
/*     */           }
/*     */ 
/*     */           
/*  81 */           for (Iterator<PlayerData> iterator = Frozo.this.getPd().getGame().getOnlinePlayers().iterator(); iterator.hasNext(); ) { PlayerData playerData = iterator.next();
/*  82 */             Bukkit.getScheduler().runTask((Plugin)BedWars.getInstance(), () -> {
/*     */                   if (playerData.getPlayer().getLocation().distance(loc) <= 5.0D && playerData.getState() == PlayerState.FIGHTING && playerData.getTeam() != Frozo.this.getPd().getTeam()) {
/*     */                     playerData.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 21, 4, false), true);
/*     */ 
/*     */                     
/*     */                     playerData.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 6, 4, false), true);
/*     */                   } 
/*     */                 }); }
/*     */ 
/*     */           
/*  92 */           this.i++;
/*     */         }
/*  94 */       }).runTaskTimerAsynchronously((Plugin)BedWars.getInstance(), 0L, 5L);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onKill(PlayerKillEvent e) {
/*  99 */     getPlayer().getInventory().addItem(new ItemStack[] { (new ItemBuilder(Material.SNOW_BALL, (int)(Math.random() * 16.0D))).toItemStack() });
/*     */   }
/*     */ 
/*     */   
/*     */   public void onTimerDone() {
/* 104 */     getPlayer().getInventory().addItem(new ItemStack[] { getItem() });
/*     */   }
/*     */ }