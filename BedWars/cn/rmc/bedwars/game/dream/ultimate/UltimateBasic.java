/*     */ package cn.rmc.bedwars.game.dream.ultimate;
/*     */ import java.util.UUID;
/*     */ import cn.rmc.bedwars.enums.game.PlayerState;
/*     */ import cn.rmc.bedwars.event.BedBreakEvent;
/*     */ import cn.rmc.bedwars.event.PlayerKillEvent;
/*     */ import cn.rmc.bedwars.event.PlayerSpawnEvent;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.block.BlockBreakEvent;
/*     */ import org.bukkit.event.block.BlockPlaceEvent;
/*     */ import org.bukkit.event.entity.EntityDamageByBlockEvent;
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*     */ import org.bukkit.event.entity.EntityDamageEvent;
/*     */ import org.bukkit.event.entity.EntityDeathEvent;
/*     */ import org.bukkit.event.entity.FoodLevelChangeEvent;
/*     */ import org.bukkit.event.entity.PotionSplashEvent;
/*     */ import org.bukkit.event.entity.ProjectileHitEvent;
/*     */ import org.bukkit.event.entity.ProjectileLaunchEvent;
/*     */ import org.bukkit.event.inventory.CraftItemEvent;
/*     */ import org.bukkit.event.inventory.InventoryClickEvent;
/*     */ import org.bukkit.event.player.PlayerDropItemEvent;
/*     */ import org.bukkit.event.player.PlayerInteractAtEntityEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.event.player.PlayerItemConsumeEvent;
/*     */ import org.bukkit.event.player.PlayerMoveEvent;
/*     */ import org.bukkit.event.player.PlayerPickupItemEvent;
/*     */ import org.bukkit.event.player.PlayerToggleFlightEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ public abstract class UltimateBasic implements Listener {
/*     */   private final UltimateType type;
/*     */   private final UUID owner;
/*     */   
/*     */   public UltimateType getType() {
/*  34 */     return this.type;
/*     */   } public BukkitTask task; public BukkitTask exptask; public UUID getOwner() {
/*  36 */     return this.owner;
/*     */   }
/*     */ 
/*     */   
/*     */   public UltimateBasic(UUID uuid, UltimateType type) {
/*  41 */     Bukkit.getPluginManager().registerEvents(this, (Plugin)BedWars.getInstance());
/*  42 */     this.owner = uuid;
/*  43 */     this.type = type;
/*     */   }
/*     */   
/*     */   public Player getPlayer() {
/*  47 */     return Bukkit.getPlayer(this.owner);
/*     */   }
/*     */   
/*     */   public PlayerData getPd() {
/*  51 */     return (getPlayer() == null) ? null : BedWars.getInstance().getPlayerManager().get(getPlayer());
/*     */   }
/*     */   
/*     */   public boolean canGo() {
/*  55 */     return (System.currentTimeMillis() - getPd().getGame().getStartTimeStamp().longValue() > 10000L && getPd().getState() == PlayerState.FIGHTING);
/*     */   }
/*     */ 
/*     */   
/*     */   public void initTask() {
/*  60 */     this
/*  61 */       .exptask = (new ExpBarRunnable(this, Long.valueOf(this.type.getCooldown().intValue() * 1000L))).runTaskTimer((Plugin)BedWars.getInstance(), 0L, 0L);
/*     */   }
/*     */   
/*     */   public void dispose() {
/*  65 */     if (this.task != null) {
/*  66 */       this.task.cancel();
/*  67 */       this.task = null;
/*     */     } 
/*  69 */     if (getItem() != null && getPlayer().getInventory().contains(getItem().getType())) {
/*  70 */       getPlayer().getInventory().remove(getItem().getType());
/*     */     }
/*  72 */     HandlerList.unregisterAll(this);
/*     */   }
/*     */   
/*     */   public void onTimerDone() {}
/*     */   
/*     */   public void onStart() {
/*  78 */     if (getItem() != null) getPlayer().getInventory().addItem(new ItemStack[] { getItem() });
/*     */   
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
/*     */   @EventHandler(priority = EventPriority.HIGHEST)
/*     */   public void onInventoryClick(InventoryClickEvent event) {
/*  92 */     Player p = (Player)event.getWhoClicked();
/*  93 */     Inventory clicked = event.getClickedInventory();
/*  94 */     if (event.getInventory().getHolder() instanceof org.bukkit.block.Chest || event.getInventory().getHolder() instanceof org.bukkit.block.DoubleChest || event
/*  95 */       .getInventory().getHolder() == null)
/*     */     {
/*  97 */       if (clicked == event.getWhoClicked().getInventory()) {
/*     */         
/*  99 */         ItemStack clickedOn = event.getCurrentItem();
/* 100 */         if (isSame(clickedOn)) {
/* 101 */           event.setCancelled(true);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isSame(ItemStack itemStack) {
/* 108 */     if (itemStack == null)
/* 109 */       return false; 
/* 110 */     if (getItem() == null) return false; 
/* 111 */     if (itemStack.getItemMeta() == null) return false; 
/* 112 */     if (itemStack.getItemMeta().getDisplayName() == null) return false;
/*     */     
/* 114 */     return (itemStack.getType() == getItem().getType() && itemStack
/* 115 */       .getItemMeta().getDisplayName()
/* 116 */       .equals(getItem().getItemMeta().getDisplayName()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onSpawnd(PlayerSpawnEvent e) {
/* 124 */     if (getItem() == null)
/* 125 */       return;  if (!canGo())
/*     */       return; 
/* 127 */     onSpawn(e);
/* 128 */     if (!e.getPlayer().getPlayer().equals(getPlayer()))
/* 129 */       return;  e.getPlayer().getPlayer().getInventory().addItem(new ItemStack[] { getItem() });
/*     */   }
/*     */   @EventHandler
/*     */   public void onDroped(PlayerDropItemEvent e) {
/* 133 */     if (getItem() == null)
/* 134 */       return;  if (!canGo())
/* 135 */       return;  if (!e.getPlayer().equals(getPlayer()))
/* 136 */       return;  if (isSame(e.getItemDrop().getItemStack())) e.setCancelled(true); 
/*     */   }
/*     */   @EventHandler(priority = EventPriority.HIGHEST)
/*     */   public void onDeathed(PlayerKillEvent e) {
/* 140 */     if (getItem() == null)
/* 141 */       return;  if (!canGo())
/* 142 */       return;  if (!e.getDeather().getPlayer().equals(getPlayer()))
/* 143 */       return;  if (this.task != null) {
/* 144 */       this.task.cancel();
/* 145 */       this.task = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onPickUpEvent(PlayerPickupItemEvent e) {
/* 151 */     if (e.getPlayer().equals(getPlayer())) onPickUp(e); 
/*     */   }
/*     */   @EventHandler(priority = EventPriority.HIGHEST)
/*     */   public void onKillEvent(PlayerKillEvent e) {
/* 155 */     if (!canGo())
/* 156 */       return;  if (e.getDeather().getPlayer().equals(getPlayer())) {
/* 157 */       onDeath(e);
/*     */     }
/* 159 */     if (e.getKiller() == null) {
/*     */       return;
/*     */     }
/* 162 */     if (e.getKiller().getPlayer().equals(getPlayer())) {
/* 163 */       onKill(e);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onBedBreakEvent(BedBreakEvent e) {
/* 169 */     if (getPd().getState() != PlayerState.FIGHTING) {
/*     */       return;
/*     */     }
/* 172 */     if (e.getBreaker().equals(getPd())) {
/* 173 */       onBedBreak(e);
/*     */     }
/* 175 */     if (e.getTeam() == getPd().getTeam()) onBedBroken(e); 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onKillEntityEvent(EntityDeathEvent e) {
/* 180 */     if (this.type.getCooldown().intValue() != -1 && getPlayer().getLevel() > 0) {
/*     */       return;
/*     */     }
/* 183 */     if (getPd().getState() != PlayerState.FIGHTING) {
/*     */       return;
/*     */     }
/* 186 */     if (e.getEntity().getKiller() == null) {
/*     */       return;
/*     */     }
/* 189 */     if (e.getEntity().getKiller().equals(getPlayer())) {
/* 190 */       onKillEntity(e);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onMoveEvent(PlayerMoveEvent e) {
/* 196 */     if (!canGo())
/* 197 */       return;  if (this.type.getCooldown().intValue() != -1 && getPlayer().getLevel() > 0) {
/*     */       return;
/*     */     }
/* 200 */     if (getPd().getState() != PlayerState.FIGHTING) {
/*     */       return;
/*     */     }
/* 203 */     if (e.getPlayer().equals(getPlayer())) {
/* 204 */       onMove(e);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onToggleFlightEvent(PlayerToggleFlightEvent e) {
/* 210 */     if (!canGo())
/* 211 */       return;  if (this.type.getCooldown().intValue() != -1 && getPlayer().getLevel() > 0) {
/*     */       return;
/*     */     }
/* 214 */     if (getPd().getState() != PlayerState.FIGHTING) {
/*     */       return;
/*     */     }
/* 217 */     if (e.getPlayer().equals(getPlayer())) {
/* 218 */       onToggleFlight(e);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onInteractEvent(PlayerInteractEvent e) {
/* 224 */     if (!canGo())
/* 225 */       return;  if (this.type.getCooldown().intValue() != -1 && getPlayer().getLevel() > 0) {
/*     */       return;
/*     */     }
/* 228 */     if (getPd().getState() != PlayerState.FIGHTING) {
/*     */       return;
/*     */     }
/* 231 */     if (e.getPlayer().equals(getPlayer()))
/* 232 */       onInteract(e); 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onInteractEntityEvent(PlayerInteractAtEntityEvent e) {
/* 237 */     if (!canGo())
/* 238 */       return;  if (this.type.getCooldown().intValue() != -1 && getPlayer().getLevel() > 0) {
/*     */       return;
/*     */     }
/* 241 */     if (getPd().getState() != PlayerState.FIGHTING) {
/*     */       return;
/*     */     }
/* 244 */     if (e.getPlayer().equals(getPlayer())) {
/* 245 */       onInteractEntity(e);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onMake(CraftItemEvent e) {
/* 251 */     if (!canGo())
/* 252 */       return;  if (this.type.getCooldown().intValue() != -1 && getPlayer().getLevel() > 0) {
/*     */       return;
/*     */     }
/* 255 */     if (getPd().getState() != PlayerState.FIGHTING) {
/*     */       return;
/*     */     }
/* 258 */     if (e.isCancelled()) {
/*     */       return;
/*     */     }
/* 261 */     if (e.getWhoClicked().equals(getPlayer())) {
/* 262 */       onCraft(e);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onItemConsume(PlayerItemConsumeEvent e) {
/* 268 */     if (!canGo())
/* 269 */       return;  if (this.type.getCooldown().intValue() != -1 && getPlayer().getLevel() > 0) {
/*     */       return;
/*     */     }
/* 272 */     if (getPd().getState() != PlayerState.FIGHTING) {
/*     */       return;
/*     */     }
/* 275 */     if (e.isCancelled()) {
/*     */       return;
/*     */     }
/* 278 */     if (e.getPlayer().equals(getPlayer())) {
/* 279 */       onConsume(e);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onProjectileHitE(ProjectileHitEvent e) {
/* 285 */     if (!canGo())
/* 286 */       return;  if (this.type.getCooldown().intValue() != -1 && getPlayer().getLevel() > 0) {
/*     */       return;
/*     */     }
/* 289 */     if (getPd().getState() != PlayerState.FIGHTING) {
/*     */       return;
/*     */     }
/* 292 */     if (!(e.getEntity() instanceof org.bukkit.entity.Arrow))
/* 293 */       return;  if (e.getEntity().getShooter().equals(getPlayer())) {
/* 294 */       onProjectileHit(e);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void ProjectileLaunchEvent(ProjectileLaunchEvent e) {
/* 300 */     if (!canGo())
/* 301 */       return;  if (this.type.getCooldown().intValue() != -1 && getPlayer().getLevel() > 0) {
/*     */       return;
/*     */     }
/* 304 */     if (getPd().getState() != PlayerState.FIGHTING) {
/*     */       return;
/*     */     }
/* 307 */     if (e.isCancelled()) {
/*     */       return;
/*     */     }
/*     */     
/* 311 */     if (e.getEntity().getShooter().equals(getPlayer())) {
/* 312 */       onProjectileLaunch(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onDamagedE(EntityDamageEvent e) {
/* 319 */     if (!canGo())
/* 320 */       return;  if (this.type.getCooldown().intValue() != -1 && getPlayer().getLevel() > 0) {
/*     */       return;
/*     */     }
/* 323 */     if (getPd().getState() != PlayerState.FIGHTING) {
/*     */       return;
/*     */     }
/* 326 */     if (e.isCancelled()) {
/*     */       return;
/*     */     }
/* 329 */     if (e.getEntity().equals(getPlayer())) {
/* 330 */       onDamaged(e);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onEntityDamagedE(EntityDamageByEntityEvent e) {
/* 336 */     if (!canGo())
/* 337 */       return;  if (this.type.getCooldown().intValue() != -1 && getPlayer().getLevel() > 0) {
/*     */       return;
/*     */     }
/* 340 */     if (getPd().getState() != PlayerState.FIGHTING) {
/*     */       return;
/*     */     }
/* 343 */     if (e.isCancelled()) {
/*     */       return;
/*     */     }
/* 346 */     if (e.getEntity().equals(getPlayer())) {
/* 347 */       onEntityDamaged(e);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onBlockDamagedE(EntityDamageByBlockEvent e) {
/* 353 */     if (!canGo())
/* 354 */       return;  if (this.type.getCooldown().intValue() != -1 && getPlayer().getLevel() > 0) {
/*     */       return;
/*     */     }
/* 357 */     if (getPd().getState() != PlayerState.FIGHTING) {
/*     */       return;
/*     */     }
/* 360 */     if (e.isCancelled()) {
/*     */       return;
/*     */     }
/* 363 */     if (e.getEntity().equals(getPlayer())) {
/* 364 */       onBlockDamaged(e);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onDealDamagedE(EntityDamageByEntityEvent e) {
/* 370 */     if (!canGo())
/* 371 */       return;  if (this.type.getCooldown().intValue() != -1 && getPlayer().getLevel() > 0) {
/*     */       return;
/*     */     }
/* 374 */     if (getPd().getState() != PlayerState.FIGHTING) {
/*     */       return;
/*     */     }
/* 377 */     if (e.isCancelled()) {
/*     */       return;
/*     */     }
/* 380 */     if (e.getDamager().equals(getPlayer())) {
/* 381 */       onDealDamage(e);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onDealDamageProdE(EntityDamageByEntityEvent e) {
/* 387 */     if (!canGo())
/* 388 */       return;  if (this.type.getCooldown().intValue() != -1 && getPlayer().getLevel() > 0) {
/*     */       return;
/*     */     }
/* 391 */     if (getPd().getState() != PlayerState.FIGHTING) {
/*     */       return;
/*     */     }
/* 394 */     if (e.isCancelled()) {
/*     */       return;
/*     */     }
/* 397 */     if (e.getDamager() instanceof Projectile && ((Projectile)e.getDamager()).getShooter().equals(getPlayer())) {
/* 398 */       onDealProjectileDamage(e);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onFoodLevelChange(FoodLevelChangeEvent e) {
/* 404 */     if (!canGo())
/* 405 */       return;  if (this.type.getCooldown().intValue() != -1 && getPlayer().getLevel() > 0) {
/*     */       return;
/*     */     }
/* 408 */     if (getPd().getState() != PlayerState.FIGHTING) {
/*     */       return;
/*     */     }
/* 411 */     if (e.isCancelled()) {
/*     */       return;
/*     */     }
/* 414 */     if (e.getEntity().equals(getPlayer())) {
/* 415 */       onFoodLevelChanged(e);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onBlockPlaceE(BlockPlaceEvent e) {
/* 421 */     if (getPd().getState() != PlayerState.FIGHTING) {
/*     */       return;
/*     */     }
/* 424 */     if (e.isCancelled()) {
/*     */       return;
/*     */     }
/* 427 */     if (e.getPlayer().equals(getPlayer()))
/* 428 */       onBlockPlace(e); 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onPotionSplashE(PotionSplashEvent e) {
/* 433 */     if (!canGo())
/* 434 */       return;  if (e.getEntity().getShooter() == null)
/* 435 */       return;  if (e.getEntity().getShooter() == getPlayer()) {
/* 436 */       onPotionSplash(e);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onBlockBreakE(BlockBreakEvent e) {
/* 442 */     if (!canGo())
/* 443 */       return;  if (this.type.getCooldown().intValue() != -1 && getPlayer().getLevel() > 0) {
/*     */       return;
/*     */     }
/* 446 */     if (getPd().getState() != PlayerState.FIGHTING) {
/*     */       return;
/*     */     }
/* 449 */     if (e.isCancelled()) {
/*     */       return;
/*     */     }
/* 452 */     if (e.getPlayer().equals(getPlayer()))
/* 453 */       onBlockBreak(e); 
/*     */   }
/*     */   
/*     */   public void onMove(PlayerMoveEvent e) {}
/*     */   
/*     */   public void onToggleFlight(PlayerToggleFlightEvent e) {}
/*     */   
/*     */   public void onBedBreak(BedBreakEvent e) {}
/*     */   
/*     */   public void onDamaged(EntityDamageEvent e) {}
/*     */   
/*     */   public void onBlockDamaged(EntityDamageByBlockEvent e) {}
/*     */   
/*     */   public void onEntityDamaged(EntityDamageByEntityEvent e) {}
/*     */   
/*     */   public void onDealDamage(EntityDamageByEntityEvent e) {}
/*     */   
/*     */   public void onDealProjectileDamage(EntityDamageByEntityEvent e) {}
/*     */   
/*     */   public void onFoodLevelChanged(FoodLevelChangeEvent e) {}
/*     */   
/*     */   public void onProjectileLaunch(ProjectileLaunchEvent e) {}
/*     */   
/*     */   public void onProjectileHit(ProjectileHitEvent e) {}
/*     */   
/*     */   public void onPotionSplash(PotionSplashEvent e) {}
/*     */   
/*     */   public void onConsume(PlayerItemConsumeEvent e) {}
/*     */   
/*     */   public void onCraft(CraftItemEvent e) {}
/*     */   
/*     */   public void onInteract(PlayerInteractEvent e) {}
/*     */   
/*     */   public void onInteractEntity(PlayerInteractAtEntityEvent e) {}
/*     */   
/*     */   public void onKillEntity(EntityDeathEvent e) {}
/*     */   
/*     */   public void onBlockPlace(BlockPlaceEvent e) {}
/*     */   
/*     */   public void onBlockBreak(BlockBreakEvent e) {}
/*     */   
/*     */   public void onKill(PlayerKillEvent e) {}
/*     */   
/*     */   public void onDeath(PlayerKillEvent e) {}
/*     */   
/*     */   public void onSpawn(PlayerSpawnEvent e) {}
/*     */   
/*     */   public void onPickUp(PlayerPickupItemEvent e) {}
/*     */   
/*     */   public void onBedBroken(BedBreakEvent e) {}
/*     */   
/*     */   public abstract ItemStack getItem();
/*     */ }