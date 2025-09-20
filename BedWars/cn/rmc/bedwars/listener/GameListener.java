/*     */ package cn.rmc.bedwars.listener;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import cn.rmc.bedwars.BedWars;
/*     */ import cn.rmc.bedwars.enums.game.DeathCause;
/*     */ import cn.rmc.bedwars.enums.game.PlayerState;
/*     */ import cn.rmc.bedwars.enums.shop.TeamUpgrade;
/*     */ import cn.rmc.bedwars.event.PlayerKillEvent;
/*     */ import cn.rmc.bedwars.event.TeamUpgradeBuyEvent;
/*     */ import cn.rmc.bedwars.game.Game;
/*     */ import cn.rmc.bedwars.game.PlayerData;
/*     */ import cn.rmc.bedwars.game.ResourceSpawner;
/*     */ import cn.rmc.bedwars.game.Team;
/*     */ import cn.rmc.bedwars.utils.MathUtils;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.GameMode;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.BlockFace;
/*     */ import org.bukkit.entity.Creature;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.EntityType;
/*     */ import org.bukkit.entity.LivingEntity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.entity.Projectile;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.block.Action;
/*     */ import org.bukkit.event.block.BlockBreakEvent;
/*     */ import org.bukkit.event.block.BlockPlaceEvent;
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*     */ import org.bukkit.event.entity.EntityDamageEvent;
/*     */ import org.bukkit.event.entity.EntityTargetEvent;
/*     */ import org.bukkit.event.entity.PlayerDeathEvent;
/*     */ import org.bukkit.event.inventory.InventoryClickEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.event.player.PlayerMoveEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.potion.PotionEffectType;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ 
/*     */ public class GameListener implements Listener {
/*     */   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
/*     */   public void onBreak(BlockBreakEvent e) {
/*  48 */     Player p = e.getPlayer();
/*  49 */     PlayerData pd = BedWars.getInstance().getPlayerManager().get(p);
/*  50 */     Location loc = e.getBlock().getLocation();
/*  51 */     Game g = pd.getGame();
/*  52 */     switch (pd.getState()) {
/*     */       case PLAYERATTACK:
/*     */       case VOID:
/*     */       case FIRE:
/*     */       case FIREBALL:
/*  57 */         e.setCancelled(true);
/*     */         break;
/*     */       case TNT:
/*  60 */         switch (e.getBlock().getType()) {
/*     */           case PLAYERATTACK:
/*     */           case VOID:
/*  63 */             for (Team team : g.getTeams()) {
/*  64 */               if (team.getBedloc().equals(loc) || team.getBedloc().equals(BlockUtils.getBedNeighbor(loc.getBlock()).getLocation())) {
/*  65 */                 if (team.getPlayers().contains(pd)) {
/*  66 */                   e.setCancelled(true);
/*  67 */                   p.sendMessage("§c你不能破坏自己的床!"); break;
/*     */                 } 
/*  69 */                 if (p.getGameMode() != GameMode.CREATIVE)
/*  70 */                   e.setCancelled(true); 
/*  71 */                 team.destroy(pd, Boolean.valueOf(false));
/*     */                 break;
/*     */               } 
/*     */             } 
/*     */             break;
/*     */         } 
/*     */ 
/*     */         
/*  79 */         if (g.isBlock(e.getBlock()).booleanValue()) {
/*  80 */           g.removeBlock(e.getBlock());
/*  81 */           pd.setBlocks_broken(pd.getBlocks_broken() + 1); break;
/*     */         } 
/*  83 */         p.sendMessage("§c你只能破坏由玩家放置的方块!");
/*  84 */         e.setCancelled(true);
/*     */         break;
/*     */     } 
/*     */   } @EventHandler(priority = EventPriority.HIGHEST)
/*     */   public void onPlace(BlockPlaceEvent e) {
/*     */     double dis;
/*     */     boolean hasRegion;
/*     */     Team finalTeam;
/*  92 */     Player p = e.getPlayer();
/*  93 */     PlayerData pd = BedWars.getInstance().getPlayerManager().get(p);
/*  94 */     Location loc = e.getBlock().getLocation();
/*  95 */     Game g = pd.getGame();
/*  96 */     switch (pd.getState()) {
/*     */       
/*     */       case PLAYERATTACK:
/*     */       case VOID:
/*     */       case FIRE:
/*     */       case FIREBALL:
/* 102 */         e.setCancelled(true);
/*     */         break;
/*     */       case TNT:
/* 105 */         if (g.getState() == GameState.ENDING)
/* 106 */           e.setCancelled(true); 
/* 107 */         switch (e.getBlock().getType()) {
/*     */           case PLAYERATTACK:
/*     */           case VOID:
/* 110 */             if (e.getPlayer().getGameMode() != GameMode.CREATIVE) {
/* 111 */               e.setCancelled(true);
/*     */               return;
/*     */             } 
/* 114 */             dis = Double.MAX_VALUE;
/* 115 */             finalTeam = null;
/* 116 */             for (Team team : g.getTeams()) {
/* 117 */               if (team.getBedloc().distance(loc) < dis) {
/* 118 */                 dis = team.getBedloc().distance(loc);
/* 119 */                 finalTeam = team;
/*     */               } 
/*     */             } 
/* 122 */             if (finalTeam == null) {
/* 123 */               System.out.println("不存在该队!");
/*     */               return;
/*     */             } 
/* 126 */             if (finalTeam.getState() == TeamState.BROKEN) {
/* 127 */               finalTeam.setAlivePlayers(finalTeam.getPlayeredplayer());
/* 128 */               finalTeam.aliveBed(pd);
/*     */             } 
/*     */             break;
/*     */         } 
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
/* 150 */         hasRegion = false;
/*     */         
/* 152 */         for (Team team : g.getTeams()) {
/* 153 */           if (LocationUtil.hasRegion(loc, team.getPos1(), team.getPos2())) {
/* 154 */             hasRegion = true;
/*     */             break;
/*     */           } 
/*     */         } 
/* 158 */         for (ResourceSpawner spawner : g.getSpawners()) {
/* 159 */           if (spawner.getLoc().distance(e.getBlock().getLocation()) <= 2.0D) {
/* 160 */             hasRegion = true;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */         
/* 166 */         if (hasRegion) {
/* 167 */           e.setCancelled(true); break;
/*     */         } 
/* 169 */         pd.setBlocks_palced(pd.getBlocks_palced() + 1);
/* 170 */         g.addBlock(e.getBlock());
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST)
/*     */   public void onInteractBed(PlayerInteractEvent e) {
/* 181 */     if (e.getAction() != Action.RIGHT_CLICK_BLOCK)
/* 182 */       return;  if (e.getClickedBlock() != null && (e.getClickedBlock().getType() == Material.BED || e
/* 183 */       .getClickedBlock().getType() == Material.BED_BLOCK)) {
/* 184 */       if (e.getItem() != null && e.getItem().getType() != Material.AIR && e
/* 185 */         .getPlayer().isSneaking()) {
/*     */         return;
/*     */       }
/* 188 */       e.setCancelled(true);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onBoughtUpgrade(TeamUpgradeBuyEvent e) {
/* 197 */     if (e.getType() instanceof TeamUpgrade)
/* 198 */       switch ((TeamUpgrade)e.getType()) {
/*     */         case PLAYERATTACK:
/* 200 */           for (PlayerData alivePlayer : e.getTeam().getAlivePlayers()) {
/* 201 */             if (alivePlayer.getState() == PlayerState.FIGHTING) {
/* 202 */               List<ItemStack> stacks = new ArrayList<>();
/* 203 */               for (ItemStack content : alivePlayer.getPlayer().getInventory().getContents()) {
/* 204 */                 if (content != null && (
/* 205 */                   content.getType().name().contains("_AXE") || content.getType().name().contains("SWORD"))) {
/* 206 */                   content = (new ItemBuilder(content)).addEnchant(Enchantment.DAMAGE_ALL, 1).toItemStack();
/*     */                 }
/*     */ 
/*     */                 
/* 210 */                 stacks.add(content);
/*     */               } 
/* 212 */               alivePlayer.getPlayer().getInventory().setContents(stacks.<ItemStack>toArray(new ItemStack[0]));
/*     */             } 
/*     */           } 
/*     */           break;
/*     */         case VOID:
/* 217 */           for (PlayerData alivePlayer : e.getTeam().getAlivePlayers()) {
/* 218 */             alivePlayer.refreshArmor();
/*     */           }
/*     */           break;
/*     */         case FIRE:
/* 222 */           for (PlayerData alivePlayer : e.getTeam().getAlivePlayers()) {
/* 223 */             alivePlayer.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 99999, e.getBoughtLevel().intValue() - 1, false, false), true);
/*     */           }
/*     */           break;
/*     */       }  
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onClickOnState(InventoryClickEvent e) {
/* 231 */     Player p = (Player)e.getWhoClicked();
/* 232 */     PlayerData pd = BedWars.getInstance().getPlayerManager().get(p);
/* 233 */     switch (pd.getState()) {
/*     */       case PLAYERATTACK:
/*     */       case VOID:
/*     */       case FIREBALL:
/* 237 */         e.setCancelled(true);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 245 */   public static HashMap<PlayerData, PlayerData> lastDamage = new HashMap<>();
/* 246 */   public static HashMap<PlayerData, HashMap<PlayerData, Long>> damagetime = new HashMap<>();
/* 247 */   public static HashMap<Entity, PlayerData> entityby = new HashMap<>();
/*     */   
/* 249 */   protected static HashMap<PlayerData, DeathCause> lastcause = new HashMap<>();
/*     */   public static void fight(PlayerData pd, PlayerData dpd, DeathCause cause) {
/* 251 */     lastDamage.put(pd, dpd);
/* 252 */     lastcause.put(pd, cause);
/* 253 */     if (!damagetime.containsKey(pd)) {
/* 254 */       damagetime.put(pd, new HashMap<>());
/*     */     }
/* 256 */     ((HashMap<PlayerData, Long>)damagetime.get(pd)).put(dpd, Long.valueOf(System.currentTimeMillis()));
/* 257 */     if (pd.getPlayer().hasPotionEffect(PotionEffectType.INVISIBILITY) && cause == DeathCause.PLAYERATTACK) {
/* 258 */       pd.getPlayer().removePotionEffect(PotionEffectType.INVISIBILITY);
/* 259 */       pd.getPlayer().sendMessage("§c你被攻击了所以你的隐身失效了");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST)
/*     */   public void onAttacked(EntityDamageByEntityEvent e) {
/* 266 */     if (CitizensAPI.getNPCRegistry().isNPC(e.getEntity()))
/* 267 */       return;  if (e.getEntity() instanceof Player) {
/* 268 */       Player p = (Player)e.getEntity();
/* 269 */       PlayerData pd = BedWars.getInstance().getPlayerManager().get(p);
/* 270 */       if (pd.getState() != PlayerState.FIGHTING) {
/* 271 */         e.setCancelled(true);
/*     */         return;
/*     */       } 
/* 274 */       if (e.getDamager() instanceof Player) {
/* 275 */         Player damager = (Player)e.getDamager();
/* 276 */         PlayerData dpd = BedWars.getInstance().getPlayerManager().get(damager);
/* 277 */         if (dpd.getState() != PlayerState.FIGHTING) {
/* 278 */           e.setCancelled(true);
/*     */           return;
/*     */         } 
/* 281 */         if (pd.getTeam().equals(dpd.getTeam())) {
/* 282 */           e.setCancelled(true);
/*     */         } else {
/* 284 */           fight(pd, dpd, DeathCause.PLAYERATTACK);
/*     */         }
/*     */       
/*     */       }
/* 288 */       else if (e.getDamager() instanceof Projectile) {
/* 289 */         Projectile pro = (Projectile)e.getDamager();
/* 290 */         if (pro.getShooter() instanceof Player) {
/* 291 */           Player damager = (Player)pro.getShooter();
/* 292 */           PlayerData dpd = BedWars.getInstance().getPlayerManager().get(damager);
/* 293 */           if (dpd.getState() != PlayerState.FIGHTING || dpd.getPlayer().getGameMode() != GameMode.SURVIVAL) {
/* 294 */             e.setCancelled(true);
/*     */             return;
/*     */           } 
/* 297 */           if (pd.getTeam().equals(dpd.getTeam())) {
/* 298 */             if (pro.getType() != EntityType.FIREBALL) {
/* 299 */               e.setCancelled(true);
/*     */             }
/*     */           } else {
/* 302 */             switch (pro.getType()) {
/*     */               case PLAYERATTACK:
/* 304 */                 fight(pd, dpd, DeathCause.FIREBALL);
/*     */                 return;
/*     */               case VOID:
/* 307 */                 fight(pd, dpd, DeathCause.ARROW);
/*     */                 return;
/*     */             } 
/* 310 */             fight(pd, dpd, DeathCause.UNKNOWNPRO);
/*     */           }
/*     */         
/*     */         } 
/*     */       } else {
/*     */         
/* 316 */         if (entityby.containsKey(e.getDamager())) {
/* 317 */           PlayerData down = entityby.get(e.getDamager());
/* 318 */           if (pd.getTeam().equals(down.getTeam())) {
/* 319 */             e.setCancelled(true);
/*     */           } else {
/* 321 */             fight(pd, down, DeathCause.ENTITYATTACK);
/*     */           } 
/*     */         } 
/* 324 */         switch (e.getDamager().getType()) {
/*     */           case FIRE:
/* 326 */             fight(pd, BedWars.getInstance().getPlayerManager().get(Bukkit.getPlayer(e.getDamager().getCustomName())), DeathCause.TNT);
/*     */             break;
/*     */           case PLAYERATTACK:
/* 329 */             fight(pd, BedWars.getInstance().getPlayerManager().get((Player)((Projectile)e.getDamager()).getShooter()), DeathCause.FIREBALL);
/*     */             break;
/*     */         } 
/*     */ 
/*     */       
/*     */       } 
/* 335 */     } else if (entityby.containsKey(e.getEntity())) {
/* 336 */       PlayerData pd = entityby.get(e.getEntity());
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 341 */       if (e.getDamager() instanceof Player) {
/* 342 */         Player damager = (Player)e.getDamager();
/* 343 */         PlayerData dpd = BedWars.getInstance().getPlayerManager().get(damager);
/* 344 */         if (pd.getTeam().equals(dpd.getTeam()) || dpd.getState() != PlayerState.FIGHTING)
/*     */         {
/* 346 */           e.setCancelled(true);
/*     */         }
/* 348 */       } else if (e.getDamager() instanceof Projectile) {
/* 349 */         Projectile pro = (Projectile)e.getDamager();
/* 350 */         if (pro.getShooter() instanceof Player) {
/* 351 */           Player damager = (Player)pro.getShooter();
/* 352 */           PlayerData dpd = BedWars.getInstance().getPlayerManager().get(damager);
/* 353 */           if (dpd.getState() != PlayerState.FIGHTING || dpd.getPlayer().getGameMode() != GameMode.SURVIVAL) {
/* 354 */             e.setCancelled(true);
/*     */           }
/* 356 */           if (pd.getTeam().equals(dpd.getTeam()))
/*     */           {
/* 358 */             e.setCancelled(true);
/*     */           }
/*     */         }
/*     */       
/*     */       }
/* 363 */       else if (entityby.containsKey(e.getDamager())) {
/* 364 */         PlayerData dpd = entityby.get(e.getDamager());
/* 365 */         if (pd.getTeam().equals(dpd.getTeam())) {
/* 366 */           e.setCancelled(true);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST)
/*     */   public void onAttack(EntityDamageEvent e) {
/* 375 */     if (CitizensAPI.getNPCRegistry().isNPC(e.getEntity()))
/* 376 */       return;  if (e.getEntity() instanceof Player) {
/* 377 */       Player p = (Player)e.getEntity();
/* 378 */       PlayerData pd = BedWars.getInstance().getPlayerManager().get(p);
/* 379 */       if (pd.getCantAttackTimeStamp() != null && System.currentTimeMillis() - pd.getCantAttackTimeStamp().longValue() <= 2000L) {
/* 380 */         e.setCancelled(true);
/*     */       }
/* 382 */       switch (pd.getState()) {
/*     */         case PLAYERATTACK:
/*     */         case VOID:
/*     */         case FIRE:
/*     */         case FIREBALL:
/* 387 */           e.setCancelled(true);
/*     */           break;
/*     */         case TNT:
/* 390 */           if (e.getCause() == EntityDamageEvent.DamageCause.FIRE) {
/* 391 */             fight(pd, lastDamage.get(pd), DeathCause.FIRE);
/*     */           }
/* 393 */           if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
/* 394 */             fight(pd, lastDamage.get(pd), DeathCause.FALL);
/*     */           }
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST)
/*     */   public void onEntityDeath(EntityDeathEvent e) {
/* 404 */     entityby.remove(e.getEntity());
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST)
/*     */   public void onDeath(PlayerDeathEvent e) {
/*     */     ArrayList<PlayerData> assists;
/* 411 */     e.setDeathMessage(null);
/* 412 */     e.getDrops().clear();
/*     */     
/* 414 */     Player p = e.getEntity();
/* 415 */     PlayerData pd = BedWars.getInstance().getPlayerManager().get(p);
/* 416 */     e.getEntity().setHealth(20.0D);
/* 417 */     switch (pd.getState()) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case TNT:
/* 423 */         assists = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 431 */         pd.getGame().addFight(pd, lastDamage.get(pd), assists, lastcause.get(pd));
/* 432 */         damagetime.remove(pd);
/* 433 */         lastDamage.remove(pd);
/* 434 */         lastcause.remove(pd);
/* 435 */         for (Entity entity : entityby.keySet()) {
/* 436 */           Creature creature = (Creature)entity;
/* 437 */           if (creature.getTarget() != null && 
/* 438 */             creature.getTarget().equals(p)) {
/* 439 */             creature.setTarget(null);
/*     */           }
/*     */         } 
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST)
/*     */   public void onFall(PlayerMoveEvent e) {
/*     */     Game g;
/* 450 */     Player p = e.getPlayer();
/* 451 */     PlayerData pd = BedWars.getInstance().getPlayerManager().get(p);
/*     */     
/* 453 */     switch (pd.getState()) {
/*     */ 
/*     */       
/*     */       case TNT:
/* 457 */         g = pd.getGame();
/* 458 */         if (e.getTo().getY() <= 0.0D) {
/* 459 */           ArrayList<PlayerData> assists = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 467 */           g.addFight(pd, lastDamage.get(pd), assists, DeathCause.VOID);
/* 468 */           p.setHealth(20.0D);
/* 469 */           damagetime.remove(pd);
/* 470 */           lastDamage.remove(pd);
/* 471 */           lastcause.remove(pd);
/*     */         } 
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST)
/*     */   public void onTarget(EntityTargetEvent e) {
/* 483 */     if (!entityby.containsKey(e.getEntity()))
/* 484 */       return;  PlayerData pd = entityby.get(e.getEntity());
/* 485 */     if (e.getTarget() instanceof Player) {
/* 486 */       Player tp = (Player)e.getTarget();
/* 487 */       PlayerData tpd = BedWars.getInstance().getPlayerManager().get(tp);
/* 488 */       if (pd.getTeam().equals(tpd.getTeam()) || tpd.getState() != PlayerState.FIGHTING) {
/* 489 */         e.setCancelled(true);
/*     */       
/*     */       }
/*     */     }
/* 493 */     else if (entityby.containsKey(e.getTarget())) {
/* 494 */       PlayerData tpd = entityby.get(e.getTarget());
/* 495 */       if (pd.getTeam().equals(tpd.getTeam())) {
/* 496 */         e.setCancelled(true);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST)
/*     */   public void onMove(PlayerMoveEvent e) {
/* 506 */     for (Entity entity : entityby.keySet()) {
/* 507 */       PlayerData result = null;
/* 508 */       double lastdis = Double.MAX_VALUE;
/* 509 */       for (PlayerData playerData : ((PlayerData)entityby.get(entity)).getGame().getOnlinePlayers()) {
/* 510 */         if (playerData.getState() != PlayerState.FIGHTING || (
/* 511 */           (PlayerData)entityby.get(entity)).getTeam() == playerData.getTeam())
/* 512 */           continue;  double dis = entity.getLocation().distance(playerData.getPlayer().getLocation());
/* 513 */         if (dis < lastdis) {
/* 514 */           lastdis = dis;
/* 515 */           result = playerData;
/*     */         } 
/*     */       } 
/* 518 */       if (lastdis <= 10.0D) {
/* 519 */         Creature creature = (Creature)entity;
/* 520 */         creature.setTarget((LivingEntity)result.getPlayer());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST)
/*     */   public void onInt(PlayerInteractEvent e) {
/* 529 */     if (e.getMaterial() != Material.MONSTER_EGG || e.getAction() != Action.RIGHT_CLICK_BLOCK) {
/*     */       return;
/*     */     }
/* 532 */     e.setCancelled(true);
/* 533 */     Player p = e.getPlayer();
/* 534 */     PlayerData pd = BedWars.getInstance().getPlayerManager().get(p);
/* 535 */     EntityType et = ((SpawnEgg)e.getItem().getData()).getSpawnedType();
/* 536 */     if (et == null) {
/* 537 */       et = EntityType.IRON_GOLEM;
/*     */     }
/* 539 */     Location loc = e.getClickedBlock().getLocation();
/* 540 */     loc.setX(loc.getX() + 0.5D);
/* 541 */     loc.setZ(loc.getZ() + 0.5D);
/* 542 */     switch (e.getBlockFace()) {
/*     */       case PLAYERATTACK:
/* 544 */         loc.setY(loc.getY() + 1.0D);
/*     */         break;
/*     */       case VOID:
/* 547 */         loc.setY(loc.getY() - 2.0D);
/*     */         break;
/*     */       case FIRE:
/* 550 */         loc.setX(loc.getX() + 1.0D);
/*     */         break;
/*     */       case FIREBALL:
/* 553 */         loc.setZ(loc.getZ() + 1.0D);
/*     */         break;
/*     */       case TNT:
/* 556 */         loc.setX(loc.getX() - 1.0D);
/*     */         break;
/*     */       case ARROW:
/* 559 */         loc.setZ(loc.getZ() - 1.0D);
/*     */         break;
/*     */     } 
/* 562 */     ItemStack hand = e.getPlayer().getItemInHand();
/* 563 */     hand.setAmount(hand.getAmount() - 1);
/* 564 */     e.getPlayer().setItemInHand(hand);
/* 565 */     Entity entity = loc.getWorld().spawnEntity(loc, et);
/* 566 */     final LivingEntity livingEntity = (LivingEntity)entity;
/* 567 */     livingEntity.setHealth(30.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 572 */     final ChatColor color = pd.getTeam().getTeamType().getChatColor();
/* 573 */     String progress = MathUtils.getProgressBar((int)livingEntity.getHealth(), 30, 10, "■", color.toString(), "§7");
/* 574 */     livingEntity.setCustomName(color + "300秒 §8[" + progress + "§8]");
/* 575 */     livingEntity.setCustomNameVisible(true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 583 */     (new BukkitRunnable()
/*     */       {
/* 585 */         float second = 300.0F;
/*     */ 
/*     */         
/*     */         public void run() {
/* 589 */           if (this.second <= 0.0F || livingEntity.isDead()) {
/* 590 */             cancel();
/* 591 */             livingEntity.remove();
/*     */             
/*     */             return;
/*     */           } 
/* 595 */           String progress = MathUtils.getProgressBar((int)livingEntity.getHealth(), 30, 10, "■", color.toString(), "§7");
/*     */ 
/*     */           
/* 598 */           livingEntity.setCustomName(String.format(color + "%.1f秒 §8[" + progress + "§8]", new Object[] { Float.valueOf(this.second) }));
/*     */           
/* 600 */           this.second = (float)(this.second - 0.1D);
/*     */         }
/* 602 */       }).runTaskTimer((Plugin)BedWars.getInstance(), 0L, 2L);
/* 603 */     entityby.put(entity, pd);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST)
/*     */   public void onKill(PlayerKillEvent e) {
/* 612 */     Bukkit.getScheduler().runTaskLater((Plugin)BedWars.getInstance(), () -> { String message; e.getDeather().setTargetTeam(null); switch (e.getCause()) { case PLAYERATTACK: message = "被击倒"; break;case VOID: if (e.getKiller() == null) { message = "掉入了虚空"; break; }  message = "被击入虚空"; break;case FIRE: message = "被烤焦了"; break;case FIREBALL: case TNT: message = "被炸飞了"; break;case ARROW: message = "被射死了"; break;default: message = "死了"; break; }  StringBuilder sb = new StringBuilder(); sb.append(e.getDeather().getTeam().getTeamType().getChatColor()).append(e.getDeather().getPlayer().getName()).append(" §7"); sb.append(message); if (e.getKiller() != null) { e.getKiller().getPlayer().playSound(e.getKiller().getPlayer().getLocation(), Sound.ORB_PICKUP, 1.0F, 1.0F); sb.append(", 击杀者: ").append(e.getKiller().getTeam().getTeamType().getChatColor()).append(e.getKiller().getPlayer().getName()); }  if (e.getIsFinal().booleanValue()) sb.append("  §b§l最终击杀!");  if (e.getIsFinal().booleanValue()) { if (e.getKiller() != null) { e.getKiller().getPlayer().sendMessage("§6+20硬币! (最终击杀)"); e.getKiller().getPlayer().sendMessage("§b+10经验! (最终击杀)"); ActionBarUtils.sendActionBar(e.getKiller().getPlayer(), "§6+20硬币! §b+10经验!"); e.getKiller().setCoins(e.getKiller().getCoins() + 20); e.getKiller().setExpenience(e.getKiller().getExpenience() + 10); e.getKiller().setFinalkills(e.getKiller().getFinalkills() + 1); }  e.getDeather().setFinaldeaths(e.getDeather().getFinaldeaths() + 1); } else { if (e.getKiller() != null) e.getKiller().setKills(e.getKiller().getKills() + 1);  e.getDeather().setDeaths(e.getDeather().getDeaths() + 1); }  for (PlayerData player : e.getGame().getOnlinePlayers()) player.getPlayer().sendMessage(sb.toString());  }1L);
/*     */   }
/*     */ }