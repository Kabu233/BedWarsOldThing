/*     */ package cn.rmc.bedwars.game.dream.armed;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import cn.rmc.bedwars.BedWars;
/*     */ import cn.rmc.bedwars.enums.game.PlayerState;
/*     */ import cn.rmc.bedwars.game.PlayerData;
/*     */ import cn.rmc.bedwars.manager.PlayerManager;
/*     */ import cn.rmc.bedwars.utils.item.ItemUtil;
/*     */ import net.citizensnpcs.api.CitizensAPI;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.inventory.InventoryClickEvent;
/*     */ import org.bukkit.event.inventory.InventoryDragEvent;
/*     */ import org.bukkit.event.player.PlayerDropItemEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.event.player.PlayerItemHeldEvent;
/*     */ import org.bukkit.event.player.PlayerPickupItemEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ import org.bukkit.util.Vector;
/*     */ 
/*     */ public abstract class GunBasic implements Listener {
/*     */   private final GunType type;
/*     */   
/*     */   public GunType getType() {
/*  36 */     return this.type;
/*     */   }
/*     */   
/*     */   public GunBasic(GunType type) {
/*  40 */     Bukkit.getPluginManager().registerEvents(this, (Plugin)BedWars.getInstance());
/*  41 */     this.type = type;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Vector> getVectors(Player player) {
/*  46 */     return Collections.singletonList(player.getEyeLocation().getDirection());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dispose() {
/*  54 */     HandlerList.unregisterAll(this);
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onRightClick(PlayerInteractEvent e) {
/*  60 */     Player p = e.getPlayer();
/*  61 */     ItemStack item = p.getItemInHand();
/*  62 */     if (item == null)
/*     */       return; 
/*  64 */     if (!e.getAction().name().contains("RIGHT"))
/*  65 */       return;  if (this.type.getMaterial() == item.getType()) {
/*  66 */       e.setCancelled(true);
/*  67 */       if (getAnno(item).intValue() > 0)
/*  68 */         shoot(p, item); 
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onInvChange(PlayerItemHeldEvent e) {
/*  74 */     Player p = e.getPlayer();
/*  75 */     ItemStack item = p.getInventory().getItem(e.getNewSlot());
/*  76 */     if (item == null || item.getType() != this.type.getMaterial()) {
/*  77 */       p.setExp(0.0F);
/*  78 */       p.setLevel(0);
/*     */     } else {
/*  80 */       ready(p, item);
/*     */     } 
/*     */   }
/*     */   @EventHandler
/*     */   public void onLeftClick(PlayerInteractEvent e) {
/*  85 */     ItemStack item = e.getItem();
/*  86 */     if (item == null)
/*  87 */       return;  if (!e.getAction().name().contains("LEFT"))
/*  88 */       return;  if (this.type.getMaterial() == item.getType()) {
/*  89 */       if (getAnno(item).intValue() >= this.type.getMaxClipAmmo().intValue())
/*  90 */         return;  if (item.getDurability() == 0) {
/*  91 */         reload(e.getPlayer(), item);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void shoot(Player p, ItemStack is) {
/*  97 */     PlayerData pd = BedWars.getInstance().getPlayerManager().get(p);
/*  98 */     if (p.getExp() != 1.0F)
/*  99 */       return;  playSound(p);
/* 100 */     HashMap<Entity, Double> damages = new HashMap<>();
/* 101 */     HashMap<Vector, Location> locs = new HashMap<>();
/* 102 */     List<Vector> vect = new ArrayList<>(getVectors(p));
/* 103 */     List<Vector> prevect = new ArrayList<>();
/* 104 */     for (int i = 0; i <= this.type.getRange().intValue(); i++) {
/* 105 */       for (Vector vector : vect) {
/* 106 */         if (prevect.contains(vector))
/* 107 */           continue;  Location loc = ((Location)locs.getOrDefault(vector, p.getEyeLocation())).add(vector);
/* 108 */         locs.put(vector, loc);
/* 109 */         vector.normalize();
/*     */         
/* 111 */         if (i <= 0)
/* 112 */           continue;  if (loc.getBlock().getType() != Material.AIR) {
/* 113 */           prevect.add(vector);
/*     */           continue;
/*     */         } 
/* 116 */         playEffect(loc);
/* 117 */         Collection<Entity> entities = loc.getWorld().getNearbyEntities(loc, 0.7D, 2.0D, 0.7D);
/* 118 */         boolean bre = false;
/* 119 */         for (Entity entity : entities) {
/* 120 */           if (!(entity instanceof Player) || 
/* 121 */             CitizensAPI.getNPCRegistry().isNPC(entity)) {
/*     */             continue;
/*     */           }
/* 124 */           if (p == entity) {
/*     */             continue;
/*     */           }
/* 127 */           if (entity.getLocation().distanceSquared(loc) > 1.0D && ((Player)entity).getEyeLocation().distanceSquared(loc) > 1.0D)
/* 128 */             continue;  Player player = (Player)entity;
/* 129 */           PlayerData pdd = BedWars.getInstance().getPlayerManager().get(player);
/* 130 */           if (pdd != null && pdd.getState() != PlayerState.FIGHTING)
/* 131 */             continue;  double v = this.type.getDamage().doubleValue() * (1.0D + ((Integer)pd.getTeam().getTeamUpgrade().get(ArmedTeamUpgrade.DeadShot)).intValue() * 0.05D);
/* 132 */           damages.put(entity, Double.valueOf(((Double)damages.getOrDefault(entity, Double.valueOf(0.0D))).doubleValue() + v));
/* 133 */           bre = true;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 139 */         if (bre) prevect.add(vector); 
/*     */       } 
/*     */     } 
/* 142 */     PlayerManager m = BedWars.getInstance().getPlayerManager();
/* 143 */     if (!damages.isEmpty()) p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1.0F, 2.0F); 
/* 144 */     for (Map.Entry<Entity, Double> entry : damages.entrySet()) {
/* 145 */       Player pa = (Player)entry.getKey();
/* 146 */       PlayerData pdd = m.get(pa);
/* 147 */       if (pd.getTeam() == pdd.getTeam())
/* 148 */         continue;  GameListener.fight(pdd, pd, DeathCause.PLAYERATTACK);
/* 149 */       pa.damage(((Double)entry.getValue()).doubleValue());
/* 150 */       onHit(pdd);
/*     */     } 
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
/* 165 */     reduceAnno(is);
/* 166 */     p.setItemInHand(is);
/*     */     
/* 168 */     p.setExp(0.0F);
/* 169 */     if (getAnno(is).intValue() > 0) {
/* 170 */       ready(p, is);
/* 171 */     } else if (is.getDurability() == 0) {
/* 172 */       reload(p, is);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onPick(PlayerPickupItemEvent e) {
/* 179 */     Player p = e.getPlayer();
/*     */ 
/*     */     
/* 182 */     Bukkit.getScheduler().runTaskLater((Plugin)BedWars.getInstance(), () -> { ItemStack item = p.getItemInHand(); if (item == null) return;  if (ItemUtil.getLore(item, 8).equals(ItemUtil.getLore(e.getItem().getItemStack(), 8))) if (item.getType() == this.type.getMaterial()) { ready(p, item); } else { p.setExp(0.0F); p.setLevel(0); }   }1L);
/*     */   }
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
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onDrop(PlayerDropItemEvent e) {
/* 198 */     Player p = e.getPlayer();
/* 199 */     int i = 0;
/* 200 */     if (e.getItemDrop().getItemStack().getType() != this.type.getMaterial())
/*     */       return; 
/* 202 */     Bukkit.getScheduler().runTaskLater((Plugin)BedWars.getInstance(), () -> { ItemStack item = p.getItemInHand(); if (item == null) return;  if (item.getType() == this.type.getMaterial()) { ready(p, item); } else { p.setExp(0.0F); p.setLevel(0); }  }1L);
/*     */   }
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
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onClick(InventoryClickEvent e) {
/* 217 */     if (e.getAction() == null)
/* 218 */       return;  Player p = (Player)e.getWhoClicked();
/*     */     
/* 220 */     Bukkit.getScheduler().runTaskLater((Plugin)BedWars.getInstance(), () -> { ItemStack item = p.getItemInHand(); if (item == null) return;  if (item.getType() == this.type.getMaterial()) { ready(p, item); } else { p.setExp(0.0F); p.setLevel(0); }  }1L);
/*     */   }
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
/*     */   @EventHandler
/*     */   public void onDrag(InventoryDragEvent e) {
/* 234 */     Player p = (Player)e.getWhoClicked();
/* 235 */     Bukkit.getScheduler().runTaskLater((Plugin)BedWars.getInstance(), () -> { ItemStack item = p.getItemInHand(); if (item == null) return;  if (item.getType() == this.type.getMaterial()) { ready(p, item); } else { p.setExp(0.0F); p.setLevel(0); }  }1L);
/*     */   }
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
/*     */   public void reload(final Player p, ItemStack is) {
/* 269 */     p.setLevel(0);
/* 270 */     p.playSound(p.getLocation(), Sound.HORSE_GALLOP, 0.5F, 0.0F);
/*     */     
/* 272 */     int a = 0;
/* 273 */     if (is.getDurability() != 0) {
/* 274 */       a = (int)(((is.getType().getMaxDurability() - is.getDurability()) * 20) * this.type.getReload().doubleValue() / is.getType().getMaxDurability());
/*     */     }
/*     */     
/* 277 */     final int finalA = a;
/* 278 */     (new BukkitRunnable() {
/* 279 */         int i = finalA;
/*     */ 
/*     */         
/*     */         public void run() {
/* 283 */           ItemStack is = p.getItemInHand();
/* 284 */           if (is.getType() != GunBasic.this.type.getMaterial()) {
/* 285 */             cancel();
/*     */             return;
/*     */           } 
/* 288 */           this.i += 2;
/* 289 */           if (this.i <= GunBasic.this.type.getReload().doubleValue() * 20.0D) {
/* 290 */             is.setDurability((short)(int)(GunBasic.this.type.getMaterial().getMaxDurability() * (20.0D * GunBasic.this.type.getReload().doubleValue() - this.i) / 20.0D * GunBasic.this.type.getReload().doubleValue()));
/*     */           } else {
/*     */             
/* 293 */             GunBasic.this.setAnno(is, GunBasic.this.type.getMaxClipAmmo());
/* 294 */             p.setItemInHand(is);
/* 295 */             p.setLevel(GunBasic.this.getAnno(is).intValue());
/* 296 */             p.setExp(1.0F);
/* 297 */             cancel();
/*     */           } 
/*     */         }
/* 300 */       }).runTaskTimer((Plugin)BedWars.getInstance(), 0L, 2L);
/*     */   }
/*     */   public Integer getAnno(ItemStack is) {
/* 303 */     String replace = ((String)is.getItemMeta().getLore().get(1)).replace(" §8• §7发射器剩余数量: §a", "");
/* 304 */     return Integer.valueOf(Integer.parseInt(replace));
/*     */   }
/*     */   public void setAnno(ItemStack is, Integer i) {
/* 307 */     ItemMeta im = is.getItemMeta();
/* 308 */     List<String> lore = im.getLore();
/* 309 */     lore.set(1, " §8• §7发射器剩余数量: §a" + i);
/* 310 */     im.setLore(lore);
/* 311 */     is.setItemMeta(im);
/*     */   }
/*     */   public void ready(final Player p, ItemStack is) {
/* 314 */     if (is.getDurability() != 0) {
/* 315 */       reload(p, is);
/*     */       return;
/*     */     } 
/* 318 */     Integer integer = getAnno(is);
/* 319 */     Bukkit.getScheduler().runTaskLater((Plugin)BedWars.getInstance(), () -> p.setLevel(integer.intValue()), 1L);
/* 320 */     (new BukkitRunnable() {
/* 321 */         int i = 0;
/*     */ 
/*     */         
/*     */         public void run() {
/* 325 */           ItemStack is = p.getItemInHand();
/* 326 */           if (is.getType() != GunBasic.this.type.getMaterial()) {
/* 327 */             cancel();
/*     */             return;
/*     */           } 
/* 330 */           this.i++;
/* 331 */           if (this.i <= GunBasic.this.type.getFireRate().doubleValue() * 20.0D) {
/* 332 */             p.setExp((float)(this.i / 20.0D * GunBasic.this.type.getFireRate().doubleValue()));
/*     */           } else {
/*     */             
/* 335 */             cancel();
/*     */           } 
/*     */         }
/* 338 */       }).runTaskTimer((Plugin)BedWars.getInstance(), 0L, 1L);
/*     */   }
/*     */   public void reduceAnno(ItemStack is) {
/* 341 */     setAnno(is, Integer.valueOf(getAnno(is).intValue() - 1));
/*     */   }
/*     */   
/*     */   public void onHit(PlayerData pd) {}
/*     */   
/*     */   public abstract void playEffect(Location paramLocation);
/*     */   
/*     */   public abstract void playSound(Player paramPlayer);
/*     */ }