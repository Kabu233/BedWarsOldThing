/*     */ package cn.rmc.bedwarslobby.inventory.select;
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.stream.Stream;
/*     */ import cn.rmc.bedwarslobby.BedWarsLobby;
/*     */ import cn.rmc.bedwarslobby.enums.GameMode;
/*     */ import cn.rmc.bedwarslobby.util.ServerStatusUtils;
/*     */ import cn.rmc.bedwarslobby.util.inventory.InventoryUI;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.inventory.InventoryClickEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.scheduler.BukkitTask;
/*     */ 
/*     */ public enum MapSelector {
/*  24 */   FOUR_MODERN(GameMode.FOUR_FOUR, "现代之城", new HashMap<String, Integer>()
/*     */     {
/*     */ 
/*     */     
/*     */     }),
/*  29 */   FOUR_STONE(GameMode.FOUR_FOUR, "石头阵", new HashMap<String, Integer>()
/*     */     {
/*     */ 
/*     */     
/*     */     }),
/*  34 */   FOUR_CLAY(GameMode.FOUR_FOUR, "粘土世界", new HashMap<String, Integer>()
/*     */     {
/*     */ 
/*     */     
/*     */     }),
/*  39 */   FOUR_FARM(GameMode.FOUR_FOUR, "农场", new HashMap<String, Integer>()
/*     */     {
/*     */     
/*     */     });
/*     */   
/*     */   public GameMode getGameMode() {
/*  45 */     return this.gameMode; }
/*  46 */   public String getDisplayName() { return this.displayName; } public HashMap<String, Integer> getServers() {
/*  47 */     return this.servers;
/*  48 */   } final HashMap<String, ServerStatusUtils.ServerStatus> serverStatuses = new HashMap<>(); final GameMode gameMode; final String displayName; final HashMap<String, Integer> servers; static BukkitTask refresh; public HashMap<String, ServerStatusUtils.ServerStatus> getServerStatuses() { return this.serverStatuses; }
/*     */ 
/*     */ 
/*     */   
/*     */   MapSelector(GameMode gameMode, String displayName, HashMap<String, Integer> servers) {
/*  53 */     this.gameMode = gameMode;
/*  54 */     this.displayName = displayName;
/*  55 */     this.servers = servers;
/*  56 */     updateStatus();
/*     */   }
/*     */   
/*     */   public void updateStatus() {
/*  60 */     this.servers.forEach((server, port) -> {
/*     */           try {
/*     */             ServerStatusUtils.ServerStatus status = ServerStatusUtils.pingServer("127.0.0.1", port.intValue());
/*     */ 
/*     */ 
/*     */             
/*     */             ServerStatusUtils.ServerStatus ns = new ServerStatusUtils.ServerStatus(status.getMotd(), status.getProtocolVersion(), status.getOnlinePlayers(), status.getMaxPlayers(), status.isOnline(), status.isWaiting(), server);
/*     */ 
/*     */ 
/*     */             
/*     */             Bukkit.getScheduler().runTask((Plugin)BedWarsLobby.getInstance(), ());
/*  71 */           } catch (IOException e) {
/*     */             Bukkit.getScheduler().runTask((Plugin)BedWarsLobby.getInstance(), ());
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<ServerStatusUtils.ServerStatus> getAllAvailableServers() {
/*  80 */     List<ServerStatusUtils.ServerStatus> allServers = new ArrayList<>();
/*  81 */     for (MapSelector selector : values()) {
/*  82 */       allServers.addAll(selector.getAvailableServers());
/*     */     }
/*  84 */     Collections.shuffle(allServers);
/*  85 */     return allServers;
/*     */   }
/*     */   
/*     */   public static ServerStatusUtils.ServerStatus getMostPopulatedAvailableServer() {
/*  89 */     List<ServerStatusUtils.ServerStatus> available = getAllAvailableServers();
/*  90 */     if (available.isEmpty()) {
/*  91 */       return null;
/*     */     }
/*  93 */     return available.stream().max(Comparator.comparingInt(ServerStatusUtils.ServerStatus::getOnlinePlayers))
/*  94 */       .orElse(available.get((new Random()).nextInt(available.size())));
/*     */   }
/*     */   
/*     */   public static void startRefresh() {
/*  98 */     if (refresh != null) {
/*  99 */       refresh.cancel();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 109 */     refresh = (new BukkitRunnable() { public void run() { for (MapSelector selector : MapSelector.values()) selector.updateStatus();  } }).runTaskTimerAsynchronously((Plugin)BedWarsLobby.getInstance(), 0L, 20L);
/*     */   }
/*     */   
/*     */   public static void stopRefresh() {
/* 113 */     if (refresh != null) {
/* 114 */       refresh.cancel();
/* 115 */       refresh = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<ServerStatusUtils.ServerStatus> getAvailableServers() {
/* 122 */     List<ServerStatusUtils.ServerStatus> availables = (List<ServerStatusUtils.ServerStatus>)this.serverStatuses.values().stream().filter(ServerStatusUtils.ServerStatus::isAvailable).collect(Collectors.toList());
/* 123 */     Collections.shuffle(availables);
/* 124 */     return availables;
/*     */   }
/*     */   
/*     */   public ServerStatusUtils.ServerStatus getMostPopulatedServer() {
/* 128 */     if (getAvailableServers().isEmpty()) return null; 
/* 129 */     return getAvailableServers().stream()
/* 130 */       .max(Comparator.comparingInt(ServerStatusUtils.ServerStatus::getOnlinePlayers))
/* 131 */       .orElse(getAvailableServers().get((new Random()).nextInt(getAvailableServers().size())));
/*     */   }
/*     */   
/*     */   public static void randomTeleportPlayer(Player player) {
/* 135 */     ServerStatusUtils.ServerStatus target = getMostPopulatedAvailableServer();
/* 136 */     if (target == null) {
/* 137 */       player.sendMessage("§c当前没有可用的房间!");
/*     */       
/*     */       return;
/*     */     } 
/* 141 */     BungeeUtil.sendPlayer(player, target.getServer());
/* 142 */     player.sendMessage("§a正在将你传送至起床房间中...");
/*     */   }
/*     */   
/*     */   public void randomTeleport(Player player) {
/* 146 */     ServerStatusUtils.ServerStatus target = getMostPopulatedServer();
/* 147 */     if (target == null) {
/* 148 */       player.sendMessage("§c当前没有可用的房间!");
/*     */       
/*     */       return;
/*     */     } 
/* 152 */     BungeeUtil.sendPlayer(player, target.getServer());
/* 153 */     player.sendMessage("§a正在将你传送至" + this.displayName + "房间中...");
/*     */   }
/*     */   
/*     */   public InventoryUI.ClickableItem getItem(String str, final BukkitTask task) {
/* 157 */     return (InventoryUI.ClickableItem)new InventoryUI.AbstractClickableItem((new ItemBuilder(Material.PAPER)).setName("§a" + this.displayName)
/* 158 */         .setLore(new String[] {
/* 159 */             "§8" + this.gameMode.getDisplayname(), "", "§7可用游戏: §a" + 
/*     */             
/* 161 */             getAvailableServerCountByMap(this.displayName), "", "§7今日可用次数: §a无限次数", "", "§a" + str + " 点击进行游戏!"
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 166 */           }).toItemStack())
/*     */       {
/*     */         public void onClick(InventoryClickEvent e) {
/* 169 */           Player p = (Player)e.getWhoClicked();
/* 170 */           MapSelector.this.randomTeleport(p);
/*     */           
/* 172 */           task.cancel();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public static InventoryUI.ClickableItem getRandomItem(String str, final BukkitTask task) {
/* 178 */     return (InventoryUI.ClickableItem)new InventoryUI.AbstractClickableItem((new ItemBuilder(Material.FIREWORK)).setName("§a随机地图")
/* 179 */         .setLore(new String[] {
/* 180 */             "§8" + GameMode.FOUR_FOUR.getDisplayname(), "", "§7今日可用次数: §a无限次数", "", "§a" + str + " 点击进行游戏!"
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 185 */           }).toItemStack())
/*     */       {
/*     */         public void onClick(InventoryClickEvent e) {
/* 188 */           Player p = (Player)e.getWhoClicked();
/* 189 */           MapSelector.randomTeleportPlayer(p);
/*     */           
/* 191 */           task.cancel();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public static List<MapSelector> getAllAvailableRooms() {
/* 197 */     return (List<MapSelector>)Arrays.<MapSelector>stream(values())
/* 198 */       .filter(selector -> !selector.getAvailableServers().isEmpty())
/* 199 */       .collect(Collectors.toList());
/*     */   }
/*     */   
/*     */   public static int getAvailableServerCountByMap(String displayName) {
/* 203 */     return Arrays.<MapSelector>stream(values())
/* 204 */       .filter(selector -> selector.getDisplayName().equals(displayName))
/* 205 */       .mapToInt(selector -> selector.getAvailableServers().size())
/* 206 */       .sum();
/*     */   }
/*     */   
/*     */   public static int getAvailablePlayerCountByMap(String displayName) {
/* 210 */     return Arrays.<MapSelector>stream(values())
/* 211 */       .filter(selector -> selector.getDisplayName().equals(displayName))
/* 212 */       .flatMap(selector -> selector.getAvailableServers().stream())
/* 213 */       .mapToInt(ServerStatusUtils.ServerStatus::getOnlinePlayers)
/* 214 */       .sum();
/*     */   }
/*     */   
/*     */   public int getAvailablePlayerCount() {
/* 218 */     return getAvailableServers().stream()
/* 219 */       .mapToInt(ServerStatusUtils.ServerStatus::getOnlinePlayers)
/* 220 */       .sum();
/*     */   }
/*     */   
/*     */   public static Map<GameMode, Integer> getPlayerCountByGameMode() {
/* 224 */     Map<GameMode, Integer> countMap = new EnumMap<>(GameMode.class);
/*     */     
/* 226 */     for (MapSelector selector : values()) {
/*     */ 
/*     */ 
/*     */       
/* 230 */       int total = selector.serverStatuses.values().stream().filter(ServerStatusUtils.ServerStatus::isOnline).mapToInt(ServerStatusUtils.ServerStatus::getOnlinePlayers).sum();
/*     */       
/* 232 */       countMap.put(selector.getGameMode(), Integer.valueOf(total));
/*     */     } 
/*     */     
/* 235 */     return countMap;
/*     */   }
/*     */   
/*     */   public static int getPlayerCountByGameMode(GameMode gameMode) {
/* 239 */     return Arrays.<MapSelector>stream(values())
/* 240 */       .filter(selector -> (selector.getGameMode() == gameMode))
/* 241 */       .flatMap(selector -> selector.serverStatuses.values().stream())
/* 242 */       .filter(ServerStatusUtils.ServerStatus::isOnline)
/* 243 */       .mapToInt(ServerStatusUtils.ServerStatus::getOnlinePlayers)
/* 244 */       .sum();
/*     */   }
/*     */   
/*     */   public static int getTotalOnlinePlayers() {
/* 248 */     return Arrays.<MapSelector>stream(values())
/* 249 */       .flatMap(selector -> selector.serverStatuses.values().stream())
/* 250 */       .filter(ServerStatusUtils.ServerStatus::isOnline)
/* 251 */       .mapToInt(ServerStatusUtils.ServerStatus::getOnlinePlayers)
/* 252 */       .sum();
/*     */   }
/*     */ }