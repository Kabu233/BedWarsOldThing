/*     */ package cn.rmc.bedwars.game;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import cn.rmc.bedwars.BedWars;
/*     */ import cn.rmc.bedwars.enums.game.EventType;
/*     */ import cn.rmc.bedwars.enums.game.GameMode;
/*     */ import cn.rmc.bedwars.enums.game.GameState;
/*     */ import cn.rmc.bedwars.enums.game.PlayerState;
/*     */ import cn.rmc.bedwars.enums.game.Resource;
/*     */ import cn.rmc.bedwars.enums.game.TeamState;
/*     */ import cn.rmc.bedwars.enums.game.TeamType;
/*     */ import cn.rmc.bedwars.event.GameStartEvent;
/*     */ import cn.rmc.bedwars.event.PlayerKillEvent;
/*     */ import cn.rmc.bedwars.game.dream.DreamManager;
/*     */ import cn.rmc.bedwars.task.CountDownTask;
/*     */ import cn.rmc.bedwars.task.EventTimerTask;
/*     */ import cn.rmc.bedwars.utils.MathUtils;
/*     */ import cn.rmc.bedwars.utils.player.LevelUtils;
/*     */ import cn.rmc.bedwars.utils.player.PlayerUtils;
/*     */ import cn.rmc.bedwars.utils.player.TitleUtils;
/*     */ import net.citizensnpcs.api.CitizensAPI;
/*     */ import net.citizensnpcs.api.npc.NPC;
/*     */ import net.citizensnpcs.trait.Gravity;
/*     */ import net.citizensnpcs.trait.HologramTrait;
/*     */ import net.citizensnpcs.trait.LookClose;
/*     */ import net.citizensnpcs.trait.SkinTrait;
/*     */ import net.minecraft.server.v1_8_R3.MinecraftServer;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Chunk;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.entity.EnderDragon;
/*     */ import org.bukkit.entity.EntityType;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.potion.PotionEffect;
/*     */ import org.json.JSONObject;
/*     */ 
/*     */ public class Game {
/*  46 */   public final List<PlayerData> onlinePlayers = new ArrayList<>(); public List<PlayerData> getOnlinePlayers() { return this.onlinePlayers; }
/*     */   
/*  48 */   private final BedWars plugin = BedWars.getInstance();
/*  49 */   private final ScoreBoardManager scoreBoardManager = this.plugin.getScoreBoardManager();
/*  50 */   private GameState state; private EventTimerTask eventTimerTask; private final UUID uuid; private Team win; private final GameMode gameMode; public GameState getState() { return this.state; } public void setState(GameState state) {
/*  51 */     this.state = state;
/*     */   } public EventTimerTask getEventTimerTask() {
/*  53 */     return this.eventTimerTask;
/*     */   } public UUID getUuid() {
/*  55 */     return this.uuid;
/*     */   } public Team getWin() {
/*  57 */     return this.win;
/*     */   } public GameMode getGameMode() {
/*  59 */     return this.gameMode;
/*     */   }
/*  61 */   private final List<PlayerData> players = new ArrayList<>(); public List<PlayerData> getPlayers() { return this.players; }
/*     */   
/*  63 */   private final List<PlayerData> specs = new ArrayList<>(); private final int maxplayer; private final Map map; public List<PlayerData> getSpecs() { return this.specs; }
/*     */    public int getMaxplayer() {
/*  65 */     return this.maxplayer;
/*     */   } public Map getMap() {
/*  67 */     return this.map;
/*     */   }
/*  69 */   private final ArrayList<Block> blocks = new ArrayList<>(); private final List<Team> teams; public List<Team> getTeams() {
/*  70 */     return this.teams;
/*     */   }
/*  72 */   private final HashMap<Resource, Integer> resGenLevel = new HashMap<>(); public HashMap<Resource, Integer> getResGenLevel() { return this.resGenLevel; }
/*     */   
/*  74 */   private final ArrayList<ResourceSpawner> spawners = new ArrayList<>(); private DreamManager dreamManager; private int EventLeftTime; private String EventDisPlayName; private EventType eventType; private Resource EventUpgradeType; private int EventUpgradeLevel; private int count; private Long startTimeStamp; private Long endTimeStamp; public ArrayList<ResourceSpawner> getSpawners() { return this.spawners; }
/*     */    public DreamManager getDreamManager() {
/*  76 */     return this.dreamManager;
/*     */   }
/*  78 */   public int getEventLeftTime() { return this.EventLeftTime; } public void setEventLeftTime(int EventLeftTime) {
/*  79 */     this.EventLeftTime = EventLeftTime;
/*     */   }
/*  81 */   public String getEventDisPlayName() { return this.EventDisPlayName; } public void setEventDisPlayName(String EventDisPlayName) {
/*  82 */     this.EventDisPlayName = EventDisPlayName;
/*     */   }
/*  84 */   public EventType getEventType() { return this.eventType; } public void setEventType(EventType eventType) {
/*  85 */     this.eventType = eventType;
/*     */   }
/*  87 */   public Resource getEventUpgradeType() { return this.EventUpgradeType; } public void setEventUpgradeType(Resource EventUpgradeType) {
/*  88 */     this.EventUpgradeType = EventUpgradeType;
/*     */   }
/*  90 */   public int getEventUpgradeLevel() { return this.EventUpgradeLevel; } public void setEventUpgradeLevel(int EventUpgradeLevel) {
/*  91 */     this.EventUpgradeLevel = EventUpgradeLevel;
/*     */   }
/*     */   
/*     */   public int getCount() {
/*  95 */     return this.count; } public void setCount(int count) {
/*  96 */     this.count = count;
/*     */   } public Long getStartTimeStamp() {
/*  98 */     return this.startTimeStamp;
/*     */   } public Long getEndTimeStamp() {
/* 100 */     return this.endTimeStamp;
/*     */   }
/* 102 */   private boolean selectBed = false; public boolean isSelectBed() { return this.selectBed; } public void setSelectBed(boolean selectBed) {
/* 103 */     this.selectBed = selectBed;
/*     */   }
/*     */   
/*     */   private boolean privateGame = false;
/* 107 */   public void setPrivateGame(boolean privateGame) { this.privateGame = privateGame; } public boolean isPrivateGame() {
/* 108 */     return this.privateGame;
/*     */   }
/*     */   
/* 111 */   String[] startmessage = new String[] { "§a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬", "                                   §l起床战争", "", "   §e§l保护你的床并摧毁敌人的床。收集铁锭，金锭，绿宝石和钻石", "                    §e§l来升级，使自身和队伍变得更强。", "", "§a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬" }; CountDownTask countRunnable; public void setStartmessage(String[] startmessage) { this.startmessage = startmessage; }
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
/*     */   public Game(Map map) {
/* 124 */     this.map = map;
/* 125 */     this.teams = new ArrayList<>();
/* 126 */     List<TeamType> types = new ArrayList<>(map.getSpawns().keySet());
/* 127 */     types.sort((Comparator<? super TeamType>)new TeamTypeComparator());
/* 128 */     for (TeamType type : types) {
/* 129 */       this.teams.add(new Team(type, this));
/*     */     }
/* 131 */     this.maxplayer = map.getEachmaxplayer() * this.teams.size();
/* 132 */     this.gameMode = map.getGamemode();
/* 133 */     setState(GameState.WAITING);
/* 134 */     this.uuid = UUID.randomUUID();
/* 135 */     MinecraftServer.getServer().setMotd("等待中");
/* 136 */     init();
/*     */   }
/*     */   
/*     */   public void init() {
/* 140 */     for (Map.Entry<Resource, ArrayList<Location>> entry : this.map.getResourceloc().entrySet()) {
/* 141 */       this.resGenLevel.put(entry.getKey(), Integer.valueOf(0));
/*     */     }
/* 143 */     if (this.gameMode.getTotalGM() == GameMode.TotalGM.DREAMS) {
/* 144 */       switch (this.gameMode) {
/*     */         case Resource:
/* 146 */           this.dreamManager = (DreamManager)new RushManager(this);
/*     */           break;
/*     */         case BEDGONE:
/* 149 */           this.dreamManager = (DreamManager)new UltimateManager(this);
/*     */           break;
/*     */         case SUDDENDEATH:
/* 152 */           this.dreamManager = (DreamManager)new ArmedManager(this);
/*     */           break;
/*     */       } 
/*     */     }
/* 156 */     if (this.dreamManager != null) this.dreamManager.init(); 
/* 157 */     this.teams.forEach(Team::init);
/*     */   }
/*     */ 
/*     */   
/*     */   public void start() {
/* 162 */     this.startTimeStamp = Long.valueOf(System.currentTimeMillis());
/*     */     
/* 164 */     grouping();
/* 165 */     initNPC();
/* 166 */     setState(GameState.FIGHTING);
/*     */     
/* 168 */     for (PlayerData playerData : this.onlinePlayers) {
/* 169 */       this.plugin.getPlayerManager().load(playerData.getPlayer());
/* 170 */       playerData.setState(PlayerState.FIGHTING);
/* 171 */       if (playerData.getTeam().getTeamType() == TeamType.NON) {
/* 172 */         toSpec(playerData, Boolean.valueOf(true));
/* 173 */         playerData.setLoginSpec(true);
/* 174 */         playerData.setSpectateMode(true);
/*     */         continue;
/*     */       } 
/* 177 */       playerData.spawn();
/* 178 */       playerData.getPlayer().closeInventory();
/* 179 */       this.scoreBoardManager.updatePlayer(playerData);
/*     */     } 
/* 181 */     for (Team team : this.teams) {
/*     */       
/* 183 */       if (team.getAlivePlayers().size() <= 0) {
/* 184 */         team.death(); continue;
/*     */       } 
/* 186 */       for (PlayerData player : team.getPlayers()) {
/* 187 */         team.getPlayeredplayer().add(player);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 192 */     GameStartEvent e = new GameStartEvent(this);
/* 193 */     Bukkit.getPluginManager().callEvent((Event)e);
/* 194 */     for (Map.Entry<Resource, ArrayList<Location>> entry : this.map.getResourceloc().entrySet()) {
/* 195 */       for (Location location : entry.getValue()) {
/* 196 */         this.spawners.add(new ResourceSpawner(this, entry.getKey(), location));
/*     */       }
/*     */     } 
/* 199 */     this.spawners.forEach(ResourceSpawner::initHolo);
/* 200 */     HashMap<Location, ItemStack> blocks = new HashMap<>();
/* 201 */     LocationUtil.getLocations(this.map.getMiddle().clone().add(-20.0D, -15.0D, -20.0D), this.map.getMiddle().clone().add(21.0D, 12.0D, 21.0D))
/* 202 */       .forEach(loc -> {
/*     */           if (loc.getBlock().getType() != Material.AIR)
/*     */             blocks.put(loc, new ItemStack(Material.AIR)); 
/*     */         });
/* 206 */     (new BlockPlaceRunnable(this.map.getMiddle().getWorld(), blocks)
/*     */       {
/*     */         
/*     */         public void finish() {}
/* 210 */       }).runTaskAsynchronously((Plugin)this.plugin);
/* 211 */     this.eventTimerTask = new EventTimerTask(this);
/* 212 */     this.eventTimerTask.runTaskTimerAsynchronously((Plugin)this.plugin, 0L, 20L);
/* 213 */     (new LocDetectTask(this)).runTaskTimerAsynchronously((Plugin)this.plugin, 0L, 20L);
/* 214 */     (new CompassTask(this)).runTaskTimerAsynchronously((Plugin)this.plugin, 0L, 20L);
/* 215 */     (new TeamResourceTask(this)).runTaskTimer((Plugin)this.plugin, 0L, 1L);
/* 216 */     (new TimerTask(this)).runTaskTimerAsynchronously((Plugin)this.plugin, 0L, 20L);
/*     */     
/* 218 */     MinecraftServer.getServer().setMotd("战斗中");
/* 219 */     this.players.forEach(playerData -> {
/*     */           playerData.getPlayer().closeInventory();
/*     */           playerData.getPlayer().sendMessage(this.startmessage);
/*     */         });
/* 223 */     if (this.dreamManager != null) this.dreamManager.start();
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
/*     */   public void end(Team Winner) {
/* 246 */     this.state = GameState.ENDING;
/* 247 */     MinecraftServer.getServer().setMotd("结束中");
/* 248 */     this.win = Winner;
/* 249 */     this.endTimeStamp = Long.valueOf(System.currentTimeMillis());
/* 250 */     StringBuilder sb = new StringBuilder();
/* 251 */     int i = 0;
/* 252 */     if (Bukkit.getOnlinePlayers().isEmpty()) {
/* 253 */       CitizensAPI.getNPCRegistry().deregisterAll();
/* 254 */       Bukkit.getScheduler().runTaskLater((Plugin)this.plugin, Bukkit::shutdown, 60L);
/*     */       return;
/*     */     } 
/* 257 */     for (PlayerData player : Winner.getPlayeredplayer()) {
/* 258 */       player.setIswin(Boolean.valueOf(true));
/* 259 */       if (i != 0) {
/* 260 */         sb.append(", ");
/*     */       }
/* 262 */       sb.append(player.getPlayer().getName());
/* 263 */       i++;
/*     */     } 
/* 265 */     Map<PlayerData, Integer> map = new HashMap<>();
/* 266 */     for (PlayerData player : this.players) {
/* 267 */       map.put(player, Integer.valueOf(player.getKills() + player.getFinalkills()));
/*     */     }
/* 269 */     List<Map.Entry<PlayerData, Integer>> top = MathUtils.gettop(map);
/* 270 */     Map.Entry<PlayerData, Integer> one = (top.size() < 1) ? null : top.get(0);
/* 271 */     Map.Entry<PlayerData, Integer> two = (top.size() < 2) ? null : top.get(1);
/* 272 */     Map.Entry<PlayerData, Integer> three = (top.size() < 3) ? null : top.get(2);
/* 273 */     for (PlayerData player : this.onlinePlayers) {
/* 274 */       if (Winner.getPlayers().contains(player)) {
/* 275 */         TitleUtils.sendFullTitle(player.getPlayer(), Integer.valueOf(0), Integer.valueOf(200), Integer.valueOf(0), "§6§l胜利", ""); continue;
/*     */       } 
/* 277 */       TitleUtils.sendFullTitle(player.getPlayer(), Integer.valueOf(0), Integer.valueOf(200), Integer.valueOf(0), "§c§l游戏结束", "");
/*     */     } 
/*     */     
/* 280 */     this.onlinePlayers.forEach(playerData -> playerData.getPlayer().sendMessage(new String[] { 
/*     */             "§a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬", "                                   §f§l起床战争", "", "                           " + Winner.getTeamType().getChatColor() + Winner.getTeamType().getDisplayname() + " §7- " + sb.toString(), "", "", "                   §a击杀数第一名 §7- " + ((one == null) ? "无" : (((PlayerData)one.getKey()).getPlayer().getName() + " §7- " + one.getValue())), "                   §6击杀数第二名 §7- " + ((two == null) ? "无" : (((PlayerData)two.getKey()).getPlayer().getName() + " §7- " + two.getValue())), "                   §c击杀数第三名 §7- " + ((three == null) ? "无" : (((PlayerData)three.getKey()).getPlayer().getName() + " §7- " + three.getValue())), "", 
/*     */             "§a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬" }));
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
/* 294 */     Bukkit.getScheduler().runTaskLater((Plugin)this.plugin, () -> { for (PlayerData player : this.onlinePlayers) { if (this.privateGame) { player.getPlayer().sendMessage("§c因为你在私人游戏中，所以你的统计信息并未改变！"); continue; }  int level = LevelUtils.getLevel(player.getCurrentExperience().intValue()).intValue(); String[] info = LevelUtils.getLevelProgressEnd(player.getCurrentExperience().intValue()); player.getPlayer().sendMessage(new String[] { "§a§l▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬", "                            §f§l奖励总览§f§l", "", "   §7你获得了", "     • §6" + player.getCoins() + " 起床战争硬币", "", "                           §b起床战争经验", "                   §bLevel " + level + "          Level " + (level + 1), "", "§8[" + info[1] + "§8]", "                          " + info[0], "", "§7你获得了 §b" + player.getExpenience() + " 起床战争经验", "", "§a§l▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬" }, ); }  saveData(); CitizensAPI.getNPCRegistry().deregisterAll(); if (!this.privateGame) for (PlayerData player : this.players) player.saveData();   }40L);
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
/* 329 */     Bukkit.getScheduler().runTaskLater((Plugin)this.plugin, () -> { for (PlayerData playerData : this.onlinePlayers) BungeeUtil.sendPlayer(playerData.getPlayer(), BedWars.getInstance().getLobbies().get((new Random()).nextInt(BedWars.getInstance().getLobbies().size())));  Bukkit.getScheduler().runTaskLater((Plugin)this.plugin, Bukkit::shutdown, 60L); }200L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CountDownTask getCountRunnable() {
/* 337 */     return this.countRunnable; } public void setCountRunnable(CountDownTask countRunnable) {
/* 338 */     this.countRunnable = countRunnable;
/*     */   }
/*     */   
/*     */   public synchronized void add(PlayerData pd) {
/* 342 */     pd.giveWaitItem();
/* 343 */     pd.setState(PlayerState.WAITING);
/* 344 */     pd.setGame(this);
/* 345 */     this.players.add(pd);
/* 346 */     this.onlinePlayers.add(pd);
/* 347 */     this.players.forEach(playerData -> playerData.getPlayer().sendMessage(PlayerUtils.getNameColor(pd.getPlayer()) + pd.getPlayer().getName() + "§e加入了游戏 (§b" + this.onlinePlayers.size() + "§e/§b" + this.maxplayer + "§e)"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 354 */     pd.getPlayer().setGameMode(GameMode.ADVENTURE);
/* 355 */     pd.getPlayer().teleport(this.map.getMiddle());
/* 356 */     this.scoreBoardManager.updatePlayer(pd);
/* 357 */     if ((this.onlinePlayers.size() >= this.maxplayer / 4 || isPrivateGame()) && this.countRunnable == null) {
/* 358 */       setState(GameState.COUNTING);
/* 359 */       this.countRunnable = new CountDownTask(this);
/* 360 */       if (isPrivateGame()) this.countRunnable.setI(60); 
/* 361 */       this.countRunnable.runTaskTimerAsynchronously((Plugin)this.plugin, 20L, 20L);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void remove(PlayerData pd) {
/* 367 */     this.onlinePlayers.remove(pd);
/* 368 */     pd.setTargetTeam(null);
/* 369 */     pd.getTeam().getAlivePlayers().remove(pd);
/* 370 */     if (pd.getTeam().getTeamType() != TeamType.NON && this.state == GameState.FIGHTING && pd.getTeam().getState() != TeamState.DEAD) {
/* 371 */       pd.getTeam().detect();
/*     */     }
/* 373 */     if (getState() == GameState.WAITING || getState() == GameState.COUNTING) {
/* 374 */       pd.getTeam().removePlayer(pd);
/*     */       
/* 376 */       this.players.remove(pd);
/* 377 */       if (this.onlinePlayers.size() == 0) {
/* 378 */         setPrivateGame(false);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 383 */     if (this.onlinePlayers.size() < this.maxplayer / 4 && getState() == GameState.COUNTING && !this.privateGame && 
/* 384 */       this.countRunnable != null) {
/* 385 */       setState(GameState.WAITING);
/* 386 */       this.countRunnable.cancel();
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
/*     */   public void toSpec(PlayerData pa, Boolean isfinal) {
/* 399 */     this.specs.add(pa);
/* 400 */     for (PotionEffect activePotionEffect : pa.getPlayer().getActivePotionEffects()) {
/* 401 */       pa.getPlayer().removePotionEffect(activePotionEffect.getType());
/*     */     }
/* 403 */     pa.getPlayer().teleport(this.map.getMiddle());
/* 404 */     PlayerUtils.setFly(pa);
/* 405 */     pa.getPlayer().spigot().setCollidesWithEntities(false);
/*     */     
/* 407 */     pa.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 99999, 1, false, false));
/* 408 */     PlayerUtils.clearArmor(pa);
/* 409 */     pa.getPlayer().updateInventory();
/* 410 */     for (PlayerData data : this.onlinePlayers) {
/* 411 */       if (data.getState() == PlayerState.FIGHTING) {
/* 412 */         data.getPlayer().hidePlayer(pa.getPlayer()); continue;
/*     */       } 
/* 414 */       pa.getPlayer().showPlayer(data.getPlayer());
/*     */     } 
/*     */     
/* 417 */     if (isfinal.booleanValue()) {
/* 418 */       pa.setSpecGame(this);
/* 419 */       pa.setSpecOther(new SpecOther(pa.getPlayer()));
/* 420 */       pa.setState(PlayerState.SPECING);
/*     */       
/* 422 */       Bukkit.getScheduler().runTaskLater((Plugin)this.plugin, pa::giveSpecItem, 5L);
/*     */     } else {
/* 424 */       pa.setState(PlayerState.RESPAWNING);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void initNPC() {
/* 429 */     CitizensAPI.getNPCRegistry().deregisterAll();
/* 430 */     for (Team team : this.teams) {
/* 431 */       Chunk chunk = team.getSpawnloc().getChunk();
/* 432 */       if (!chunk.isLoaded()) chunk.load(); 
/* 433 */       Iterator<PlayerData> it = team.getPlayers().iterator();
/* 434 */       NPC itemn = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "§r");
/* 435 */       itemn.data().setPersistent("nameplate-visible", ((Boolean)itemn.data().get("nameplate-visible", Boolean.valueOf(false))).toString());
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 440 */       ((LookClose)itemn.getTrait(LookClose.class)).lookClose(true);
/* 441 */       ((Gravity)itemn.getTrait(Gravity.class)).toggle();
/* 442 */       ((SkinTrait)itemn.getTrait(SkinTrait.class)).setSkinName(it.hasNext() ? ((PlayerData)it.next()).getPlayer().getName() : "KaBuSaMa");
/* 443 */       team.setItemshopnpc(itemn);
/*     */ 
/*     */       
/* 446 */       ((HologramTrait)itemn.getTrait(HologramTrait.class)).addLine("§e§l右键点击");
/* 447 */       ((HologramTrait)itemn.getTrait(HologramTrait.class)).addLine("§b道具商店");
/* 448 */       NPCListener.items.add(itemn);
/*     */       
/* 450 */       itemn.spawn(team.getItemshop());
/*     */       
/* 452 */       NPC teamn = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "§r");
/* 453 */       ((Gravity)teamn.getTrait(Gravity.class)).toggle();
/* 454 */       teamn.data().setPersistent("nameplate-visible", ((Boolean)teamn.data().get("nameplate-visible", Boolean.valueOf(false))).toString());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 461 */       ((LookClose)teamn.getTrait(LookClose.class)).lookClose(true);
/* 462 */       ((SkinTrait)teamn.getTrait(SkinTrait.class)).setSkinName(it.hasNext() ? ((PlayerData)it.next()).getPlayer().getName() : "KaBuSaMa");
/*     */ 
/*     */       
/* 465 */       ((HologramTrait)teamn.getTrait(HologramTrait.class)).addLine("§e§l右键点击");
/* 466 */       ((HologramTrait)teamn.getTrait(HologramTrait.class)).addLine("§b商店");
/* 467 */       ((HologramTrait)teamn.getTrait(HologramTrait.class)).addLine("§b团队模式");
/*     */       
/* 469 */       team.setTeamshopnpc(teamn);
/* 470 */       NPCListener.teams.add(teamn);
/*     */       
/* 472 */       teamn.spawn(team.getTeamshop());
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
/*     */   public void addFight(PlayerData playerData, PlayerData killer, ArrayList<PlayerData> assist, DeathCause cause) {
/* 488 */     boolean isfinal = (playerData.getTeam().getState() == TeamState.BROKEN);
/* 489 */     PlayerKillEvent ke = new PlayerKillEvent(killer, playerData, assist, cause, Boolean.valueOf(isfinal));
/* 490 */     Bukkit.getPluginManager().callEvent((Event)ke);
/*     */ 
/*     */ 
/*     */     
/* 494 */     HashMap<Resource, Integer> result = new HashMap<>();
/* 495 */     for (ItemStack stack : playerData.getPlayer().getInventory().getContents()) {
/* 496 */       if (stack != null)
/* 497 */         for (Resource value : Resource.values()) {
/* 498 */           if (stack.getType() == value.getItem()) {
/* 499 */             if (result.containsKey(value)) {
/* 500 */               result.put(value, Integer.valueOf(((Integer)result.get(value)).intValue() + stack.getAmount()));
/*     */             } else {
/* 502 */               result.put(value, Integer.valueOf(stack.getAmount()));
/*     */             } 
/*     */           }
/*     */         }  
/*     */     } 
/* 507 */     if (!ke.getDrop().booleanValue()) {
/* 508 */       playerData.setSpawngiven(result);
/* 509 */     } else if (killer != null) {
/* 510 */       for (Resource value : Resource.values()) {
/* 511 */         if (result.containsKey(value) && 
/* 512 */           killer.getState() == PlayerState.FIGHTING) {
/* 513 */           killer.getPlayer().getInventory().addItem(new ItemStack[] { (new ItemBuilder(value.getItem(), ((Integer)result.get(value)).intValue())).toItemStack() });
/* 514 */           killer.getPlayer().sendMessage(value.getColor() + "+" + result.get(value) + value.getDisplayName());
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 520 */     if (isfinal) {
/* 521 */       playerData.getPlayer().teleport(this.map.getMiddle());
/* 522 */       playerData.getTeam().getAlivePlayers().remove(playerData);
/* 523 */       toSpec(playerData, Boolean.valueOf(true));
/* 524 */       playerData.getTeam().detect();
/*     */     } else {
/* 526 */       playerData.death();
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
/*     */   public void goEvent() {
/*     */     Iterator<Team> iterator;
/*     */     Team max;
/* 545 */     switch (getEventType()) {
/*     */       case Resource:
/* 547 */         switch (getEventUpgradeType()) {
/*     */           case Resource:
/* 549 */             this.resGenLevel.put(Resource.DIAMOND, Integer.valueOf(getEventUpgradeLevel()));
/*     */             break;
/*     */           case BEDGONE:
/* 552 */             this.resGenLevel.put(Resource.EMERALD, Integer.valueOf(getEventUpgradeLevel()));
/*     */             break;
/*     */         } 
/* 555 */         for (ResourceSpawner spawner : this.spawners) {
/* 556 */           if (spawner.res == Resource.DIAMOND) {
/* 557 */             spawner.holo1.setTitle("§e等级§c" + MathUtils.toRome(((Integer)getResGenLevel().get(Resource.DIAMOND)).intValue() + 1));
/*     */           }
/* 559 */           if (spawner.res == Resource.EMERALD) {
/* 560 */             spawner.holo1.setTitle("§e等级§c" + MathUtils.toRome(((Integer)getResGenLevel().get(Resource.EMERALD)).intValue() + 1));
/*     */           }
/*     */         } 
/* 563 */         for (PlayerData player : this.onlinePlayers) {
/* 564 */           player.getPlayer().sendMessage(getEventUpgradeType().getColor() + getEventUpgradeType().getDisplayName() + "生成器§e已被升级至§c" + MathUtils.toRome(getEventUpgradeLevel() + 1) + "§e级!");
/*     */         }
/*     */         break;
/*     */       case BEDGONE:
/* 568 */         for (Team team : this.teams) {
/* 569 */           if (team.getState() == TeamState.ALIVE) {
/* 570 */             team.destroy(null, Boolean.valueOf(true));
/*     */           }
/*     */         } 
/* 573 */         for (PlayerData player : this.onlinePlayers) {
/* 574 */           TitleUtils.sendFullTitle(player.getPlayer(), Integer.valueOf(0), Integer.valueOf(60), Integer.valueOf(0), "§c床已被破坏!", "§f所有的床已被破坏!");
/* 575 */           player.getPlayer().playSound(player.getPlayer().getLocation(), Sound.ENDERDRAGON_GROWL, 1.0F, 1.0F);
/* 576 */           player.getPlayer().sendMessage(new String[] { "§c§l所有床已被破坏." });
/*     */         } 
/*     */         break;
/*     */ 
/*     */       
/*     */       case SUDDENDEATH:
/* 582 */         for (iterator = this.teams.iterator(); iterator.hasNext(); ) { Team team = iterator.next();
/* 583 */           if (team.getState() != TeamState.DEAD) {
/* 584 */             Bukkit.getScheduler().runTask((Plugin)this.plugin, () -> {
/*     */                   EnderDragon dragon = (EnderDragon)this.map.getMiddle().getWorld().spawnEntity(this.map.getMiddle(), EntityType.ENDER_DRAGON);
/*     */                   dragon.setCustomName(team.getTeamType().getChatColor() + team.getTeamType().getDisplayname() + " 龙");
/*     */                   dragon.setCustomNameVisible(true);
/*     */                   int a = 1;
/*     */                   if (((Integer)team.getTeamUpgrade().get(TeamUpgrade.DragonBuff)).intValue() == 1) {
/*     */                     a++;
/*     */                     EnderDragon dragon1 = (EnderDragon)this.map.getMiddle().getWorld().spawnEntity(this.map.getMiddle(), EntityType.ENDER_DRAGON);
/*     */                     dragon1.setCustomName(team.getTeamType().getChatColor() + team.getTeamType().getDisplayname() + " 龙");
/*     */                     dragon1.setCustomNameVisible(true);
/*     */                   } 
/*     */                   for (PlayerData onlinePlayer : getOnlinePlayers()) {
/*     */                     onlinePlayer.getPlayer().sendMessage("§e+" + a + " " + team.getTeamType().getChatColor() + team.getTeamType().getDisplayname() + " 龙");
/*     */                   }
/*     */                 });
/*     */           } }
/*     */         
/*     */         break;
/*     */       
/*     */       case GAMEEND:
/* 604 */         max = null;
/* 605 */         for (Team team : this.teams) {
/* 606 */           if (max == null) max = team; 
/* 607 */           if (max.getPlayersKillWithFinalKill() < team.getPlayersKillWithFinalKill()) {
/* 608 */             max = team;
/*     */           }
/*     */         } 
/* 611 */         if (max != null)
/* 612 */           end(max); 
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void saveData() {
/* 618 */     JSONObject jo = new JSONObject();
/* 619 */     jo.put("UUID", this.uuid);
/* 620 */     jo.put("Server", UUID.randomUUID().toString());
/* 621 */     jo.put("Map", this.map.getMapname());
/* 622 */     jo.put("GameMode", this.gameMode);
/* 623 */     jo.put("Winner", this.win.getTeamType());
/* 624 */     jo.put("StartTimeStamp", this.startTimeStamp);
/* 625 */     jo.put("EndTimeStamp", this.endTimeStamp);
/* 626 */     jo.put("EachPlayer", this.map.getEachmaxplayer());
/* 627 */     HashMap<TeamType, JSONObject> teaminfo = new HashMap<>();
/* 628 */     for (Team team : this.teams) {
/* 629 */       JSONObject teamjo = new JSONObject();
/* 630 */       JSONObject pjo = new JSONObject();
/* 631 */       for (PlayerData player : team.getPlayeredplayer()) {
/* 632 */         pjo.put(player.getUuid().toString(), player.getJsonStats());
/*     */       }
/* 634 */       teamjo.put("PlayerInfo", pjo);
/* 635 */       teaminfo.put(team.getTeamType(), teamjo);
/*     */     } 
/* 637 */     jo.put("TeamInfo", teaminfo);
/* 638 */     DataUtils.set(this.uuid.toString(), Data.Field.MATCHINFO, jo.toString());
/*     */   }
/*     */   
/*     */   private void grouping() {
/* 642 */     Collections.shuffle(this.players);
/* 643 */     if (this.privateGame) {
/*     */       return;
/*     */     }
/* 646 */     for (PlayerData player : this.players) {
/* 647 */       player.getPlayer().closeInventory();
/* 648 */       int i = Integer.MAX_VALUE;
/* 649 */       Team finalteam = null;
/* 650 */       if (player.getTeam().getTeamType() != TeamType.NON)
/* 651 */         continue;  for (Team team : this.teams) {
/* 652 */         if (team.getPlayers().size() <= i) {
/* 653 */           i = team.getPlayers().size();
/* 654 */           finalteam = team;
/*     */         } 
/*     */       } 
/* 657 */       assert finalteam != null;
/* 658 */       finalteam.addPlayer(player);
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
/*     */   public void addBlock(Block b) {
/* 686 */     this.blocks.add(b);
/*     */   }
/*     */   public void removeBlock(Block b) {
/* 689 */     this.blocks.remove(b);
/*     */   }
/*     */   public Boolean isBlock(Block b) {
/* 692 */     return Boolean.valueOf(this.blocks.contains(b));
/*     */   }
/*     */ }