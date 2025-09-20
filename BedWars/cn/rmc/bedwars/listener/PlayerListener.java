/*     */ package cn.rmc.bedwars.listener;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import cn.rmc.bedwars.BedWars;
/*     */ import cn.rmc.bedwars.enums.game.GameMode;
/*     */ import cn.rmc.bedwars.enums.game.GameState;
/*     */ import cn.rmc.bedwars.enums.game.PlayerState;
/*     */ import cn.rmc.bedwars.enums.game.Resource;
/*     */ import cn.rmc.bedwars.game.Game;
/*     */ import cn.rmc.bedwars.game.PlayerData;
/*     */ import cn.rmc.bedwars.manager.PlayerManager;
/*     */ import cn.rmc.bedwars.utils.inventory.InventoryUI;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.block.Action;
/*     */ import org.bukkit.event.entity.FoodLevelChangeEvent;
/*     */ import org.bukkit.event.inventory.InventoryClickEvent;
/*     */ import org.bukkit.event.inventory.InventoryCloseEvent;
/*     */ import org.bukkit.event.inventory.InventoryType;
/*     */ import org.bukkit.event.player.AsyncPlayerChatEvent;
/*     */ import org.bukkit.event.player.PlayerDropItemEvent;
/*     */ import org.bukkit.event.player.PlayerInteractAtEntityEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.event.player.PlayerJoinEvent;
/*     */ import org.bukkit.event.player.PlayerLoginEvent;
/*     */ import org.bukkit.event.player.PlayerPickupItemEvent;
/*     */ import org.bukkit.event.player.PlayerQuitEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ public class PlayerListener implements Listener {
/*     */   @EventHandler
/*     */   public void Playerinto(PlayerLoginEvent e) {
/*  39 */     if (BedWars.getInstance().getGameManager().getGameArrayList().isEmpty())
/*  40 */       return;  Game g = BedWars.getInstance().getGameManager().getGameArrayList().get(0);
/*  41 */     PlayerManager pm = BedWars.getInstance().getPlayerManager();
/*  42 */     PlayerData pd = pm.get(e.getPlayer());
/*  43 */     if ((g.getState() == GameState.FIGHTING && !g.getPlayers().contains(pd)) || (g
/*  44 */       .getOnlinePlayers().size() >= g.getTeams().size() * g.getMap().getEachmaxplayer() && g.isPrivateGame())) {
/*  45 */       if (e.getPlayer().hasPermission("bedwars.admin"))
/*  46 */       { e.allow(); }
/*  47 */       else if (!g.isPrivateGame()) { e.disallow(PlayerLoginEvent.Result.KICK_OTHER, "游戏已开始"); }
/*  48 */       else { e.allow(); }
/*     */     
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.LOW)
/*     */   public void PlayerJoin(PlayerJoinEvent e) {
/*  57 */     e.setJoinMessage(null);
/*  58 */     PlayerManager pm = BedWars.getInstance().getPlayerManager();
/*  59 */     pm.add(e.getPlayer());
/*  60 */     if (pm.get(e.getPlayer()).getPlayer() != e.getPlayer()) {
/*  61 */       pm.get(e.getPlayer()).setPlayer(e.getPlayer());
/*     */     }
/*  63 */     PlayerData pd = pm.get(e.getPlayer());
/*  64 */     Game g = BedWars.getInstance().getGameManager().getGameArrayList().get(0);
/*     */     
/*  66 */     if (g != null) {
/*  67 */       switch (g.getState()) {
/*     */         case SPAWNING:
/*     */         case WAITING:
/*  70 */           g.add(pm.get(e.getPlayer()));
/*     */           break;
/*     */         case SPECING:
/*  73 */           if (pd.getTeam().getTeamType() == TeamType.NON) {
/*  74 */             g.toSpec(pd, Boolean.valueOf(true));
/*  75 */             g.onlinePlayers.add(pd);
/*     */             return;
/*     */           } 
/*  78 */           if (pd.getTeam().getState() == TeamState.ALIVE) {
/*  79 */             pd.death();
/*  80 */             pd.getTeam().getAlivePlayers().add(pd);
/*  81 */             g.onlinePlayers.add(pd); break;
/*     */           } 
/*  83 */           g.toSpec(pd, Boolean.valueOf(true));
/*  84 */           pd.setLoginSpec(true);
/*  85 */           g.onlinePlayers.add(pd);
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(priority = EventPriority.LOW)
/*     */   public void PlayerLeave(PlayerQuitEvent e) {
/*  93 */     e.setQuitMessage(null);
/*  94 */     Player p = e.getPlayer();
/*     */     
/*  96 */     PlayerData pd = BedWars.getInstance().getPlayerManager().get(p);
/*  97 */     pd.getTeam().getAlivePlayers().remove(pd);
/*  98 */     pd.setScoreBoard(null);
/*  99 */     if (pd.getGame() != null) {
/* 100 */       pd.getGame().remove(pd);
/*     */     }
/* 102 */     if (pd.getSpecGame() != null) {
/* 103 */       (pd.getSpecGame()).onlinePlayers.remove(pd);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onHungry(FoodLevelChangeEvent e) {
/* 109 */     if (!(e.getEntity() instanceof Player))
/* 110 */       return;  Player p = (Player)e.getEntity();
/* 111 */     p.setFoodLevel(20);
/* 112 */     e.setCancelled(true);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onChat(AsyncPlayerChatEvent e) {
/* 117 */     e.setCancelled(true);
/* 118 */     Player p = e.getPlayer();
/* 119 */     PlayerData pd = BedWars.getInstance().getPlayerManager().get(p);
/* 120 */     send(pd, e.getMessage());
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onNB(InventoryClickEvent e) {
/*     */     try {
/* 126 */       if (!(e.getWhoClicked() instanceof Player))
/* 127 */         return;  if (e.getClickedInventory().getType() == InventoryType.CRAFTING || e
/* 128 */         .getClickedInventory().getType() == InventoryType.WORKBENCH) {
/* 129 */         e.setCancelled(true);
/*     */       }
/* 131 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onInteractEntity(PlayerInteractAtEntityEvent e) {
/* 137 */     if (e.getRightClicked() instanceof Player) {
/* 138 */       PlayerData pd = BedWars.getInstance().getPlayerManager().get(e.getPlayer());
/* 139 */       if (pd.getState() != PlayerState.SPECING)
/* 140 */         return;  PlayerData td = BedWars.getInstance().getPlayerManager().get((Player)e.getRightClicked());
/* 141 */       if (td.getState() != PlayerState.FIGHTING)
/* 142 */         return;  pd.getSpecOther().setTarget(td.getPlayer(), false);
/* 143 */       pd.getSpecOther().setFirstPersonTargeting(true);
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onInteractInSpec(PlayerInteractEvent e) {
/* 149 */     PlayerData pd = BedWars.getInstance().getPlayerManager().get(e.getPlayer());
/* 150 */     if (pd.getState() == PlayerState.SPECING && (
/* 151 */       e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) && 
/* 152 */       pd.getSpecOther().isFirstPersonTargeting())
/* 153 */       (new SpecSelector(pd.getPlayer())).open(); 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onExitFirstPerson(PlayerToggleSneakEvent e) {
/* 158 */     PlayerData pd = BedWars.getInstance().getPlayerManager().get(e.getPlayer());
/* 159 */     if (pd.getState() != PlayerState.SPECING)
/* 160 */       return;  if (pd.getSpecOther().isFirstPersonTargeting()) {
/* 161 */       pd.getSpecOther().setFirstPersonTargeting(false);
/*     */     }
/*     */   }
/*     */   
/* 165 */   List<String> words = Arrays.asList(new String[] { "gg", "goodgame", "good game" });
/* 166 */   List<PlayerData> wordsSenders = new ArrayList<>();
/*     */   
/*     */   public void send(PlayerData pd, String message) {
/* 169 */     String name = LuckPermsUtil.getPrefix(pd.getPlayer()) + pd.getPlayer().getName() + LuckPermsUtil.getSuffix(pd.getPlayer());
/* 170 */     String team = pd.getTeam().getTeamType().getChatColor() + "[" + pd.getTeam().getTeamType().getDisplayname() + "]§7";
/* 171 */     if (pd.getGame() != null && pd.getGame().getState() == GameState.ENDING && 
/* 172 */       pd.getPlayer().hasPermission("golden.gg") && 
/* 173 */       !this.wordsSenders.contains(pd)) {
/* 174 */       for (String str : this.words) {
/* 175 */         if (str.equalsIgnoreCase(message)) {
/* 176 */           for (Player player : Bukkit.getOnlinePlayers()) {
/* 177 */             player.sendMessage(name + "§f: §6" + message);
/*     */           }
/* 179 */           this.wordsSenders.add(pd);
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 186 */     switch (pd.getState()) {
/*     */       case SPAWNING:
/*     */       case WAITING:
/* 189 */         for (Player player : Bukkit.getOnlinePlayers()) {
/* 190 */           player.sendMessage(name + "§f: " + message);
/*     */         }
/*     */         break;
/*     */       
/*     */       case SPECING:
/* 195 */         if (pd.getSpecGame().getState() == GameState.ENDING) {
/* 196 */           for (Player player : Bukkit.getOnlinePlayers()) {
/* 197 */             player.sendMessage(name + "§f: " + message);
/*     */           }
/*     */           return;
/*     */         } 
/* 201 */         for (PlayerData player : ((pd.getGame() == null) ? pd.getSpecGame() : pd.getGame()).getOnlinePlayers()) {
/* 202 */           if (player.getState() == PlayerState.RESPAWNING || player.getState() == PlayerState.SPECING) {
/* 203 */             player.getPlayer().sendMessage("§7[旁观者] " + name + "§f: " + message);
/*     */           }
/*     */         } 
/*     */         break;
/*     */       case RESPAWNING:
/* 208 */         if (pd.getGame().getState() == GameState.ENDING) {
/* 209 */           for (Player player : Bukkit.getOnlinePlayers()) {
/* 210 */             player.sendMessage(name + "§f: " + message);
/*     */           }
/*     */           return;
/*     */         } 
/* 214 */         for (PlayerData player : ((pd.getGame() == null) ? pd.getSpecGame() : pd.getGame()).getOnlinePlayers()) {
/* 215 */           if (player.getState() == PlayerState.RESPAWNING || player.getState() == PlayerState.SPECING) {
/* 216 */             player.getPlayer().sendMessage("§7[旁观者] " + name + "§f: " + message);
/*     */           }
/*     */         } 
/*     */         break;
/*     */       case FIGHTING:
/* 221 */         if (pd.getGame().getState() == GameState.ENDING) {
/* 222 */           for (Player player : Bukkit.getOnlinePlayers())
/* 223 */             player.sendMessage(name + "§f: " + message); 
/*     */           break;
/*     */         } 
/* 226 */         switch (pd.getGame().getGameMode()) {
/*     */           case SPAWNING:
/* 228 */             for (PlayerData player : pd.getGame().getOnlinePlayers()) {
/* 229 */               player.getPlayer().sendMessage(team + " " + name + "§f: " + message);
/*     */             }
/*     */             break;
/*     */         } 
/*     */         
/* 234 */         for (PlayerData player : pd.getTeam().getAlivePlayers()) {
/* 235 */           player.getPlayer().sendMessage(LevelUtils.getLevelwithColorByLevel(pd.getCurrentLevel().intValue()) + " " + team + " " + name + "§f: " + message);
/*     */         }
/*     */         break;
/*     */     } 
/*     */   }
/*     */   private boolean _isFuckingNoPutInChest(ItemStack itemStack) {
/* 287 */     if (itemStack == null)
/* 288 */       return false; 
/* 289 */     Material material = itemStack.getType();
/* 290 */     String mName = material.name().toLowerCase();
/* 291 */     return (material == Material.WOOD_SWORD || mName.contains("axe") || mName.contains("shear") || material == Material.COMPASS);
/*     */   }
/*     */   
/*     */   private void checkInv(Player player) {
/* 295 */     int i = 0;
/* 296 */     for (ItemStack is : player.getInventory().getContents()) {
/* 297 */       if (is != null && 
/* 298 */         is.getType() != Material.WOOD_SWORD && 
/* 299 */         is.getType().name().contains("SWORD"))
/* 300 */         i++; 
/*     */     } 
/* 302 */     if (i == 0) {
/* 303 */       BedWars.getInstance().getPlayerManager().get(player).giveSword();
/*     */     } else {
/* 305 */       player.getInventory().remove(Material.WOOD_SWORD);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void hasHigher(Player player) {
/* 310 */     for (int i = 0; i < player.getInventory().getSize(); i++) {
/* 311 */       ItemStack is = player.getInventory().getItem(i);
/* 312 */       if (is != null && 
/* 313 */         is.getType() != Material.WOOD_SWORD && 
/* 314 */         is.getType().name().contains("SWORD")) {
/* 315 */         player.getInventory().remove(Material.WOOD_SWORD);
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/* 321 */   List<Player> openedChests = new ArrayList<>();
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onCloseInv(InventoryCloseEvent e) {
/* 326 */     if (e.getInventory().getType() == InventoryType.ENDER_CHEST || this.openedChests.contains(e.getPlayer())) {
/* 327 */       if (!(e.getPlayer() instanceof Player))
/* 328 */         return;  for (int slot = 0; slot < e.getInventory().getSize(); slot++) {
/* 329 */         ItemStack content = e.getInventory().getItem(slot);
/* 330 */         if (content == null) {
/* 331 */           hasHigher((Player)e.getPlayer());
/*     */         
/*     */         }
/* 334 */         else if (_isFuckingNoPutInChest(content)) {
/* 335 */           e.getPlayer().getInventory().addItem(new ItemStack[] { content });
/* 336 */           e.getInventory().remove(content);
/*     */         
/*     */         }
/* 339 */         else if (content.getType().name().contains("SWORD")) {
/* 340 */           checkInv((Player)e.getPlayer());
/*     */         } else {
/* 342 */           hasHigher((Player)e.getPlayer());
/*     */         } 
/*     */       } 
/* 345 */       this.openedChests.remove(e.getPlayer());
/*     */     } 
/*     */     
/* 348 */     if (!(e.getInventory().getHolder() instanceof InventoryUI.InventoryUIHolder))
/* 349 */       return;  InventoryUI.InventoryUIHolder holder = (InventoryUI.InventoryUIHolder)e.getInventory().getHolder();
/* 350 */     if (holder.getInventoryUI().getSymbol() != null && holder.getInventoryUI().getSymbol().equals("QSE")) {
/* 351 */       PlayerData pd = BedWars.getInstance().getPlayerManager().get((Player)e.getPlayer());
/* 352 */       BedWars.getInstance().getQuickBuyManager().save(pd.getPlayer().getUniqueId(), pd.getQuickShopData());
/* 353 */       pd.setSetingItem(null);
/*     */     } 
/* 355 */     if (holder.getInventoryUI().getSymbol() != null && holder.getInventoryUI().getSymbol().equals("TS")) {
/* 356 */       PlayerData pd = BedWars.getInstance().getPlayerManager().get((Player)e.getPlayer());
/* 357 */       pd.setTeamselector(null);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onInteractChest(PlayerInteractEvent e) {
/* 364 */     Player p = e.getPlayer();
/* 365 */     PlayerData pd = BedWars.getInstance().getPlayerManager().get(p);
/* 366 */     if (pd.getState() == PlayerState.FIGHTING) {
/* 367 */       if (e.getAction() != Action.RIGHT_CLICK_BLOCK)
/* 368 */         return;  if (e.getClickedBlock() == null)
/* 369 */         return;  if (e.getClickedBlock().getType() == Material.CHEST) {
/* 370 */         this.openedChests.add(e.getPlayer());
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onPickUp(PlayerPickupItemEvent e) {
/* 377 */     Player p = e.getPlayer();
/* 378 */     PlayerData pd = BedWars.getInstance().getPlayerManager().get(p);
/* 379 */     ItemStack finae = e.getItem().getItemStack().clone();
/* 380 */     if (pd.getState() != PlayerState.FIGHTING) {
/* 381 */       e.setCancelled(true);
/*     */       return;
/*     */     } 
/* 384 */     if (finae.getType() == Material.BED || finae.getType() == Material.BED_BLOCK) {
/* 385 */       e.getPlayer().getInventory().remove(e.getItem().getItemStack());
/* 386 */       e.getItem().remove();
/*     */     } 
/* 388 */     if (finae.getItemMeta().getLore() == null)
/* 389 */       return;  boolean isres = false;
/* 390 */     Resource res = null;
/* 391 */     for (Resource value : Resource.values()) {
/* 392 */       if (e.getItem().getItemStack().getType().equals(value.getItem())) {
/* 393 */         isres = true;
/* 394 */         res = value;
/*     */       } 
/*     */     } 
/* 397 */     if (!isres) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 407 */     pd.getCollections().put(res, Integer.valueOf(pd.getCollections().containsKey(res) ? (((Integer)pd.getCollections().get(res)).intValue() + 1) : 1));
/* 408 */     e.getItem().setItemStack((new ItemBuilder(e.getItem().getItemStack())).clearLores().toItemStack());
/* 409 */     if (e.getItem().getItemStack().getType() == Material.IRON_INGOT || e.getItem().getItemStack().getType() == Material.GOLD_INGOT) {
/* 410 */       Location location = pd.getTeam().getResloc();
/* 411 */       if (location.distance(e.getPlayer().getLocation()) <= 2.0D) {
/* 412 */         for (Entity entity : location.getWorld().getNearbyEntities(location, 2.0D, 2.0D, 2.0D)) {
/* 413 */           if (!(entity instanceof Player) || 
/* 414 */             e.getPlayer().getUniqueId() == entity.getUniqueId())
/* 415 */             continue;  PlayerData gamePlayer2 = BedWars.getInstance().getPlayerManager().get((Player)entity);
/* 416 */           if (pd.getTeam().getTeamType() != gamePlayer2.getTeam().getTeamType()) {
/*     */             continue;
/*     */           }
/* 419 */           if (gamePlayer2.getState() != PlayerState.FIGHTING) {
/*     */             return;
/*     */           }
/* 422 */           gamePlayer2.getPlayer().getInventory().addItem(new ItemStack[] { e.getItem().getItemStack() });
/* 423 */           e.getItem().remove();
/*     */         } 
/*     */       }
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
/*     */   @EventHandler
/*     */   public void onArmor(InventoryClickEvent e) {
/* 443 */     if (e.getInventory().getHolder() == null)
/* 444 */       return;  if (Arrays.<Integer>asList(new Integer[] { Integer.valueOf(5), Integer.valueOf(6), Integer.valueOf(7), Integer.valueOf(8) }).contains(Integer.valueOf(e.getRawSlot())) && e.getWhoClicked().getInventory().getHolder().equals(e.getInventory().getHolder()) && e
/* 445 */       .getInventory().getTitle().equals("container.crafting"))
/* 446 */       e.setCancelled(true); 
/*     */   }
/*     */   @EventHandler
/*     */   public void onItem(PlayerDropItemEvent e) {
/* 450 */     ItemStack is = e.getItemDrop().getItemStack();
/* 451 */     PlayerData pd = BedWars.getInstance().getPlayerManager().get(e.getPlayer());
/* 452 */     if (pd.getState() != PlayerState.FIGHTING) {
/* 453 */       e.setCancelled(true);
/*     */       return;
/*     */     } 
/* 456 */     if (is.getType() == Material.COMPASS || is.getType() == Material.WOOD_SWORD || is.getType().name().contains("AXE") || is.getType().name().contains("SHEAR")) {
/* 457 */       ItemStack orig = is.clone();
/* 458 */       Bukkit.getScheduler().runTaskLater((Plugin)BedWars.getInstance(), () -> e.getPlayer().getInventory().addItem(new ItemStack[] { orig }, ), 5L);
/*     */ 
/*     */       
/* 461 */       e.getItemDrop().remove();
/*     */     }
/* 463 */     else if (is.getType().name().contains("SWORD")) {
/* 464 */       int i = 0;
/* 465 */       for (ItemStack itemStack : e.getPlayer().getInventory().getContents()) {
/* 466 */         if (itemStack != null && 
/* 467 */           itemStack.getType().name().contains("SWORD")) {
/* 468 */           i++;
/*     */         }
/*     */       } 
/* 471 */       if (i <= 0)
/* 472 */         pd.giveSword(); 
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onGet(PlayerPickupItemEvent e) {
/* 478 */     PlayerData pd = BedWars.getInstance().getPlayerManager().get(e.getPlayer());
/* 479 */     if (e.getItem().getItemStack().getType().name().contains("SWORD")) {
/* 480 */       int i = 0;
/* 481 */       for (ItemStack itemStack : e.getPlayer().getInventory().getContents()) {
/* 482 */         if (itemStack != null && 
/* 483 */           itemStack.getType().name().contains("SWORD")) i++; 
/*     */       } 
/* 485 */       if (i <= 0) {
/* 486 */         pd.giveSword();
/*     */       } else {
/* 488 */         e.getPlayer().getInventory().remove(Material.WOOD_SWORD);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   @EventHandler
/*     */   public void onCraft(CraftItemEvent e) {
/* 494 */     e.setCancelled(true);
/*     */   }
/*     */ }