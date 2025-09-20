/*     */ package cn.rmc.bedwars;
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import cn.rmc.bedwars.command.BedwarsCommand;
/*     */ import cn.rmc.bedwars.config.DataBaseConfig;
/*     */ import cn.rmc.bedwars.enums.DataBaseType;
/*     */ import cn.rmc.bedwars.listener.PingListener;
/*     */ import cn.rmc.bedwars.listener.PlayerListener;
/*     */ import cn.rmc.bedwars.manager.ConfigManager;
/*     */ import cn.rmc.bedwars.manager.GameManager;
/*     */ import cn.rmc.bedwars.manager.HolographicManager;
/*     */ import cn.rmc.bedwars.manager.MapManager;
/*     */ import cn.rmc.bedwars.manager.PlayerManager;
/*     */ import cn.rmc.bedwars.manager.QuickBuyManager;
/*     */ import cn.rmc.bedwars.manager.ScoreBoardManager;
/*     */ import cn.rmc.bedwars.utils.Config;
/*     */ import cn.rmc.bedwars.utils.database.DataBase;
/*     */ import cn.rmc.bedwars.utils.nametag.NametagHandler;
/*     */ import net.minecraft.server.v1_8_R3.MinecraftServer;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ public final class BedWars extends JavaPlugin {
/*     */   private static BedWars instance;
/*     */   
/*     */   public static BedWars getInstance() {
/*  31 */     return instance;
/*     */   } private String prefix; public String getPrefix() {
/*  33 */     return this.prefix;
/*     */   }
/*  35 */   private final List<String> lobbies = new ArrayList<>(); private ConfigManager configManager; private PlayerManager playerManager; private HolographicManager holographicManager; private ScoreBoardManager scoreBoardManager; private MapManager mapManager; private GameManager gameManager; private QuickBuyManager quickBuyManager; private NametagHandler nametagHandler; private DataBase dataBase; public List<String> getLobbies() { return this.lobbies; }
/*     */   
/*  37 */   public ConfigManager getConfigManager() { return this.configManager; }
/*  38 */   public PlayerManager getPlayerManager() { return this.playerManager; }
/*  39 */   public HolographicManager getHolographicManager() { return this.holographicManager; }
/*  40 */   public ScoreBoardManager getScoreBoardManager() { return this.scoreBoardManager; }
/*  41 */   public MapManager getMapManager() { return this.mapManager; }
/*  42 */   public GameManager getGameManager() { return this.gameManager; }
/*  43 */   public QuickBuyManager getQuickBuyManager() { return this.quickBuyManager; }
/*  44 */   public NametagHandler getNametagHandler() { return this.nametagHandler; } public DataBase getDataBase() {
/*  45 */     return this.dataBase;
/*     */   }
/*     */   public void onEnable() {
/*  48 */     instance = this;
/*  49 */     restoremap();
/*  50 */     Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "npc remove all");
/*  51 */     Bukkit.getScheduler().runTask((Plugin)this, () -> {
/*     */           loadManager();
/*     */           regListener();
/*     */           regCmd();
/*     */           initMySQL();
/*     */         });
/*  57 */     for (Object o : getConfig().getList("lobbys")) {
/*  58 */       this.lobbies.add((String)o);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/*  64 */     this.dataBase.close();
/*  65 */     Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "npc remove all");
/*     */   }
/*     */   private void initMySQL() {
/*  68 */     Config config = DataBaseConfig.getConfig();
/*  69 */     this.dataBase = DataBase.create(config.getString("ip"), config
/*  70 */         .getInt("port"), config.getString("database"), config.getString("username"), config
/*  71 */         .getString("password"));
/*     */   }
/*     */   
/*     */   private void loadManager() {
/*  75 */     this.configManager = new ConfigManager();
/*  76 */     this.prefix = Config.getConfig().getString("prefix", "§e§l起床战争§r ");
/*  77 */     this.playerManager = new PlayerManager();
/*  78 */     this.scoreBoardManager = new ScoreBoardManager();
/*  79 */     this.mapManager = new MapManager((Plugin)this);
/*  80 */     new DataBaseConfig();
/*  81 */     this.holographicManager = new HolographicManager();
/*  82 */     this.gameManager = new GameManager();
/*  83 */     this.quickBuyManager = new QuickBuyManager(DataBaseType.MYSQL);
/*  84 */     this.nametagHandler = new NametagHandler(this, (NametagAdapter)new DefaultNametagAdapter());
/*     */   }
/*     */   
/*     */   private void regListener() {
/*  88 */     Bukkit.getMessenger().registerOutgoingPluginChannel((Plugin)this, "BungeeCord");
/*  89 */     ProtocolLibrary.getProtocolManager().addPacketListener((PacketListener)new PacketListener());
/*  90 */     Arrays.<Listener>asList(new Listener[] { (Listener)new WorldListener(), (Listener)new PlayerListener(), (Listener)new PlayerInteractListener(), (Listener)new UIListener(), (Listener)new NPCListener(), (Listener)new ItemListener(), (Listener)new GameListener(), (Listener)new PingListener()
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 100 */         }).forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, (Plugin)this));
/*     */   }
/*     */   
/*     */   private void restoremap() {
/* 104 */     File file = new File(getDataFolder(), "backup");
/* 105 */     if (file.exists()) {
/* 106 */       MapUtil.resetMap(file);
/*     */     }
/*     */   }
/*     */   
/*     */   private void regCmd() {
/* 111 */     (new BedwarsCommand()).register();
/* 112 */     registerCommand((Command)new ShoutCommand());
/*     */   }
/*     */   
/*     */   public static void sendMsg(CommandSender sender, String... msg) {
/* 116 */     for (String s : msg) {
/* 117 */       sender.sendMessage(ChatColor.translateAlternateColorCodes('&', getInstance().getPrefix() + " &c" + s).replace("<player_name>", sender.getName()));
/*     */     }
/*     */   }
/*     */   
/*     */   private void registerCommand(Command cmd) {
/* 122 */     (MinecraftServer.getServer()).server.getCommandMap().register(cmd.getName(), getName(), cmd);
/*     */   }
/*     */ }