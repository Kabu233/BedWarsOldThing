/*     */ package cn.rmc.bedwars.listener;
/*     */ import java.util.ArrayList;
/*     */ import cn.rmc.bedwars.BedWars;
/*     */ import cn.rmc.bedwars.enums.game.PlayerState;
/*     */ import cn.rmc.bedwars.game.Game;
/*     */ import cn.rmc.bedwars.game.PlayerData;
/*     */ import cn.rmc.bedwars.game.Team;
/*     */ import cn.rmc.bedwars.utils.MathUtils;
/*     */ import cn.rmc.bedwars.utils.world.LocationUtil;
/*     */ import net.minecraft.server.v1_8_R3.EntityFireball;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.entity.Egg;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.EntityType;
/*     */ import org.bukkit.entity.Fireball;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.entity.Silverfish;
/*     */ import org.bukkit.entity.TNTPrimed;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.block.BlockPlaceEvent;
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*     */ import org.bukkit.event.entity.EntityDamageEvent;
/*     */ import org.bukkit.event.entity.ProjectileHitEvent;
/*     */ import org.bukkit.event.entity.ProjectileLaunchEvent;
/*     */ import org.bukkit.event.player.PlayerBucketEmptyEvent;
/*     */ import org.bukkit.event.player.PlayerInteractAtEntityEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.event.player.PlayerItemConsumeEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.potion.PotionEffect;
/*     */ import org.bukkit.potion.PotionEffectType;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ import org.bukkit.util.Vector;
/*     */ 
/*     */ public class ItemListener implements Listener {
/*  41 */   private static final Logger log = LoggerFactory.getLogger(ItemListener.class);
/*     */   
/*  43 */   private final BedWars plugin = BedWars.getInstance();
/*     */   
/*     */   @EventHandler
/*     */   public void EggBridge(ProjectileLaunchEvent e) {
/*  47 */     if (!(e.getEntity() instanceof Egg))
/*  48 */       return;  Egg egg = (Egg)e.getEntity();
/*  49 */     if (!(egg.getShooter() instanceof Player))
/*  50 */       return;  Player p = (Player)egg.getShooter();
/*  51 */     PlayerData pd = BedWars.getInstance().getPlayerManager().get(p);
/*  52 */     Location pl = p.getLocation();
/*  53 */     (new EggBridgeRunnable(pd, egg)).runTaskTimer((Plugin)BedWars.getInstance(), 0L, 0L);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onSponge(BlockPlaceEvent e) {
/*  58 */     if (e.getBlock().getType() == Material.SPONGE) {
/*  59 */       PlayerData pd = this.plugin.getPlayerManager().get(e.getPlayer());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  65 */       (new SpongeRunnable(pd, e.getBlock())).runTaskAsynchronously((Plugin)this.plugin);
/*  66 */       for (Game game : BedWars.getInstance().getGameManager().getGameArrayList()) {
/*  67 */         if (game.isBlock(e.getBlock()).booleanValue()) {
/*  68 */           game.removeBlock(e.getBlock());
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void Potion(PlayerItemConsumeEvent e) {
/*  76 */     PlayerData pd = this.plugin.getPlayerManager().get(e.getPlayer());
/*  77 */     if (e.getItem().getType() == Material.POTION) {
/*  78 */       e.setCancelled(true);
/*  79 */       String dpn = e.getItem().getItemMeta().getDisplayName();
/*  80 */       Bukkit.getScheduler().runTaskLater((Plugin)BedWars.getInstance(), () -> { if (e.getPlayer().getItemInHand().getType() == Material.GLASS_BOTTLE || e.getPlayer().getItemInHand().getType() == Material.POTION) { if (dpn != null) { if (dpn.contains("速度")) e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 900, 1, false, false), true);  if (dpn.contains("跳跃")) e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 900, 4, false, false), true);  if (dpn.contains("隐身")) { e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 600, 1, false, false), true); pd.hideArmor(); pd.setVisionable(false); }  }  e.getPlayer().setItemInHand(null); }  }2L);
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
/* 105 */     if (e.getItem().getType() == Material.MILK_BUCKET) {
/* 106 */       e.setCancelled(true);
/* 107 */       Bukkit.getScheduler().runTaskLater((Plugin)BedWars.getInstance(), () -> { if (e.getPlayer().getItemInHand().getType() == Material.MILK_BUCKET || e.getPlayer().getItemInHand().getType() == Material.BUCKET) { pd.setMilktime(Long.valueOf(System.currentTimeMillis())); e.getPlayer().setItemInHand(null); }  }2L);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onBucket(PlayerBucketEmptyEvent e) {
/* 118 */     Bukkit.getScheduler().runTaskLater((Plugin)BedWars.getInstance(), () -> { if (e.getPlayer().getItemInHand().getType() == Material.WATER_BUCKET || e.getPlayer().getItemInHand().getType() == Material.BUCKET) e.getPlayer().setItemInHand(null);  }2L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 124 */   List<Player> fireUsed = new ArrayList<>();
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST)
/*     */   public void FireBall(PlayerInteractEvent e) {
/* 128 */     if (e.getItem() == null)
/* 129 */       return;  if (e.getAction() == null)
/* 130 */       return;  if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK)
/* 131 */       return;  if (e.getItem().getType() == Material.FIREBALL) {
/* 132 */       e.setCancelled(true);
/* 133 */       Player p = e.getPlayer();
/* 134 */       if (this.fireUsed.contains(p)) {
/* 135 */         p.sendMessage("§c请等待0.5秒后再使用...");
/*     */         return;
/*     */       } 
/* 138 */       ItemStack stack = p.getItemInHand();
/* 139 */       stack.setAmount(stack.getAmount() - 1);
/* 140 */       p.setItemInHand(stack);
/*     */       
/* 142 */       Fireball fireball = (Fireball)e.getPlayer().launchProjectile(Fireball.class);
/*     */       
/* 144 */       fireball.setShooter((ProjectileSource)e.getPlayer());
/* 145 */       fireball.setYield(4.0F);
/*     */ 
/*     */       
/* 148 */       fireball = setFireballDirection(fireball, e.getPlayer().getEyeLocation().getDirection());
/* 149 */       fireball.setVelocity(e.getPlayer().getEyeLocation().getDirection().multiply(2));
/*     */       
/* 151 */       this.fireUsed.add(p);
/* 152 */       p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 5, 0, false, false));
/* 153 */       Bukkit.getScheduler().runTaskLater((Plugin)BedWars.getInstance(), () -> { this.fireUsed.remove(p); p.removePotionEffect(PotionEffectType.SLOW); }10L);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Fireball setFireballDirection(Fireball fireball, Vector vector) {
/* 161 */     EntityFireball fb = ((CraftFireball)fireball).getHandle();
/*     */     
/* 163 */     fb.dirX = vector.getX() * 0.1D;
/* 164 */     fb.dirY = vector.getY() * 0.1D;
/* 165 */     fb.dirZ = vector.getZ() * 0.1D;
/*     */     
/* 167 */     return (Fireball)fb.getBukkitEntity();
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void FireBallHit(PlayerInteractAtEntityEvent e) {
/* 172 */     Player player = e.getPlayer();
/* 173 */     PlayerData playerData = BedWars.getInstance().getPlayerManager().get(player);
/* 174 */     if (playerData.getState() != PlayerState.FIGHTING && e.getRightClicked().getType() == EntityType.FIREBALL)
/* 175 */       e.setCancelled(true); 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void TNT(BlockPlaceEvent e) {
/* 180 */     Player p = e.getPlayer();
/* 181 */     PlayerData pd = BedWars.getInstance().getPlayerManager().get(p);
/* 182 */     Location loc = e.getBlock().getLocation();
/* 183 */     Game g = pd.getGame();
/*     */     
/* 185 */     if (e.getBlock().getType() == Material.TNT) {
/* 186 */       e.setCancelled(true);
/* 187 */       ItemStack stack = e.getPlayer().getItemInHand();
/* 188 */       stack.setAmount(e.getPlayer().getItemInHand().getAmount() - 1);
/* 189 */       e.getPlayer().setItemInHand(stack);
/* 190 */       e.getBlock().setType(Material.AIR);
/* 191 */       Location newloc = loc.clone().add(0.5D, 0.0D, 0.5D);
/*     */       
/* 193 */       TNTPrimed tnt = (TNTPrimed)newloc.getWorld().spawnEntity(newloc, EntityType.PRIMED_TNT);
/* 194 */       tnt.setFuseTicks(45);
/* 195 */       tnt.setYield(2.5F);
/* 196 */       if (Probability.probability(10)) {
/* 197 */         tnt.setIsIncendiary(true);
/*     */       }
/* 199 */       tnt.setVelocity(e.getPlayer().getEyeLocation().getDirection().multiply(0.1D));
/*     */       
/* 201 */       tnt.setCustomName(e.getPlayer().getName());
/*     */     } 
/*     */   }
/*     */   
/* 205 */   List<Entity> entities = new ArrayList<>();
/*     */   
/*     */   @EventHandler
/*     */   public void onLaunch(ProjectileLaunchEvent e) {
/* 209 */     if (e.getEntity().getShooter() instanceof Player && 
/* 210 */       e.getEntityType() == EntityType.SNOWBALL) {
/* 211 */       Player shooter = (Player)e.getEntity().getShooter();
/* 212 */       if (shooter.getItemInHand() != null) {
/* 213 */         ItemStack itemInHand = shooter.getItemInHand();
/* 214 */         if (itemInHand.getItemMeta() != null && itemInHand.getItemMeta().getDisplayName() != null && 
/* 215 */           itemInHand.getItemMeta().getDisplayName().equals("§b床虱")) {
/* 216 */           this.entities.add(e.getEntity());
/* 217 */           Bukkit.getScheduler().runTaskLater((Plugin)BedWars.getInstance(), () -> this.entities.remove(e.getEntity()), 1200L);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void BedBug(ProjectileHitEvent e) {
/* 229 */     if (e.getEntityType() != EntityType.SNOWBALL)
/* 230 */       return;  if (!(e.getEntity().getShooter() instanceof Player))
/* 231 */       return;  if (!this.entities.contains(e.getEntity()))
/* 232 */       return;  Player p = (Player)e.getEntity().getShooter();
/* 233 */     final Silverfish sf = (Silverfish)e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.SILVERFISH);
/* 234 */     GameListener.entityby.put(sf, BedWars.getInstance().getPlayerManager().get(p));
/* 235 */     sf.setHealth(8.0D);
/* 236 */     PlayerData pd = BedWars.getInstance().getPlayerManager().get(p);
/*     */     
/* 238 */     final ChatColor color = pd.getTeam().getTeamType().getChatColor();
/* 239 */     String progress = MathUtils.getProgressBar((int)sf.getHealth(), 8, 10, "■", color.toString(), "§7");
/* 240 */     sf.setCustomName(color + "60秒 §8[" + progress + "§8]");
/* 241 */     sf.setCustomNameVisible(true);
/*     */     
/* 243 */     (new BukkitRunnable() {
/* 244 */         float second = 60.0F;
/*     */ 
/*     */         
/*     */         public void run() {
/* 248 */           if (this.second <= 0.0F || sf.isDead()) {
/* 249 */             cancel();
/* 250 */             sf.remove();
/*     */             return;
/*     */           } 
/* 253 */           String progress = MathUtils.getProgressBar((int)sf.getHealth(), 8, 10, "■", color.toString(), "§7");
/* 254 */           sf.setCustomName(String.format(color + "%.1f秒 §8[" + progress + "§8]", new Object[] { Float.valueOf(this.second) }));
/* 255 */           this.second = (float)(this.second - 0.1D);
/*     */         }
/* 257 */       }).runTaskTimer((Plugin)BedWars.getInstance(), 0L, 2L);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void FIREBALLHIT(ProjectileHitEvent e) {
/* 262 */     if (e.getEntityType() != EntityType.FIREBALL)
/* 263 */       return;  if (!(e.getEntity().getShooter() instanceof Player))
/* 264 */       return;  Fireball fireball = (Fireball)e.getEntity();
/* 265 */     fireball.getNearbyEntities(3.0D, 3.0D, 3.0D).forEach(player -> {
/*     */           if (!(player instanceof Player)) {
/*     */             return;
/*     */           }
/*     */           Player p = (Player)player;
/*     */           PlayerData pd = BedWars.getInstance().getPlayerManager().get(p);
/*     */           if (pd.getState() != PlayerState.FIGHTING)
/*     */             return; 
/*     */           Vector vector = LocationUtil.getPosition(p.getLocation(), fireball.getLocation(), 1.0D);
/*     */           vector.setY(1.58125D);
/*     */           vector.multiply(0.95D);
/*     */           p.setVelocity(vector);
/*     */         });
/*     */   }
/*     */   
/*     */   public boolean hasRegion(Game g, Block block) {
/* 281 */     boolean hasRegion = false;
/* 282 */     for (Team team : g.getTeams()) {
/* 283 */       if (LocationUtil.hasRegion(block.getLocation(), team.getPos1(), team.getPos2())) {
/* 284 */         hasRegion = true;
/*     */         break;
/*     */       } 
/*     */     } 
/* 288 */     for (ResourceSpawner spawner : g.getSpawners()) {
/* 289 */       if (spawner.getLoc().distance(block.getLocation()) <= 2.0D) {
/* 290 */         hasRegion = true;
/*     */         break;
/*     */       } 
/*     */     } 
/* 294 */     if (block.getType() != Material.AIR) {
/* 295 */       hasRegion = true;
/*     */     }
/* 297 */     return hasRegion;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void Damage(EntityDamageByEntityEvent e) {
/* 304 */     if (e.getEntity() instanceof Player) {
/* 305 */       if (e.getDamage() <= 0.0D)
/* 306 */         return;  Entity damager = e.getDamager();
/* 307 */       switch (damager.getType()) {
/*     */         case PRIMED_TNT:
/*     */         case FIREBALL:
/* 310 */           e.setDamage(1.0D);
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   @EventHandler
/*     */   public void onDamageFall(EntityDamageEvent e) {
/* 317 */     if (e.getCause() == EntityDamageEvent.DamageCause.FALL && 
/* 318 */       e.getDamage() > 6.0D) {
/* 319 */       e.setDamage(e.getDamage() - 2.0D);
/*     */     }
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
/*     */   
/*     */   @EventHandler
/*     */   public void onBlockPlace(BlockPlaceEvent event) {
/* 430 */     CompactPopupTower.onCompactPopupTower(event);
/*     */   }
/*     */ }