/*     */ package cn.rmc.bedwarslobby;
/*     */ 
/*     */ import com.comphenix.protocol.events.PacketListener;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import cn.rmc.bedwarslobby.inventory.select.MapSelector;
/*     */ import cn.rmc.bedwarslobby.listener.NPCListener;
/*     */ import cn.rmc.bedwarslobby.listener.PacketListener;
/*     */ import cn.rmc.bedwarslobby.listener.PlayerInteractListener;
/*     */ import cn.rmc.bedwarslobby.manager.PlayerManager;
/*     */ import cn.rmc.bedwarslobby.manager.ScoreBoardManager;
/*     */ import cn.rmc.bedwarslobby.npc.AbstractNPC;
/*     */ import cn.rmc.bedwarslobby.npc.NPCStorage;
/*     */ import cn.rmc.bedwarslobby.util.Config;
/*     */ import cn.rmc.bedwarslobby.util.database.DataBase;
/*     */ import cn.rmc.bedwarslobby.util.database.KeyValue;
/*     */ import cn.rmc.bedwarslobby.util.holographic.Holographic;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Difficulty;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.command.CommandExecutor;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ public class BedWarsLobby extends JavaPlugin {
/*     */   private PlayerManager playerManager;
/*     */   private static DataBase dataBase;
/*     */   private ScoreBoardManager scoreBoardManager;
/*     */   
/*  31 */   public PlayerManager getPlayerManager() { return this.playerManager; } private static BedWarsLobby instance; public static Config config; NPCStorage npcStorage; public static DataBase getDataBase() {
/*  32 */     return dataBase;
/*     */   }
/*  34 */   public ScoreBoardManager getScoreBoardManager() { return this.scoreBoardManager; } public static BedWarsLobby getInstance() {
/*  35 */     return instance;
/*     */   }
/*     */   
/*     */   public NPCStorage getNpcStorage() {
/*  39 */     return this.npcStorage;
/*     */   }
/*     */   
/*     */   public void onEnable() {
/*  43 */     instance = this;
/*  44 */     configload();
/*  45 */     regListener();
/*  46 */     Bukkit.getPluginCommand("bedwars").setExecutor((CommandExecutor)new BedWarsCommand());
/*  47 */     Bukkit.getPluginCommand("claim").setExecutor((CommandExecutor)new ClaimCommand());
/*  48 */     regclass();
/*  49 */     regMysql();
/*     */     
/*  51 */     MapSelector.startRefresh();
/*     */   }
/*     */   
/*     */   public void onDisable() {
/*     */     try {
/*  56 */       SitUtils.sitted.values().forEach(Entity::remove);
/*  57 */       dataBase.getDataBaseCore().getTotalConnection().close();
/*     */       
/*  59 */       MapSelector.stopRefresh();
/*     */     } catch (Throwable $ex) {
/*     */       throw $ex;
/*     */     }  } void regListener() {
/*  63 */     Bukkit.getMessenger().registerOutgoingPluginChannel((Plugin)this, "BungeeCord");
/*  64 */     Arrays.<Listener>asList(new Listener[] { (Listener)new PlayerListener(), (Listener)new PlayerChatListener(), (Listener)new PlayerInteractListener(), (Listener)new UIListener(), (Listener)new WorldListener(), (Listener)new NPCListener()
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  71 */         }).forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, (Plugin)this));
/*  72 */     (new PAPIExpansion()).canRegister();
/*  73 */     (new PAPIExpansion()).register();
/*  74 */     ProtocolLibrary.getProtocolManager().addPacketListener((PacketListener)new PacketListener(this));
/*  75 */     this.npcStorage = new NPCStorage();
/*     */     
/*  77 */     Bukkit.getScheduler().runTaskTimer((Plugin)this, new Runnable()
/*     */         {
/*     */           public void run() {
/*  80 */             for (World w : Bukkit.getWorlds()) {
/*  81 */               w.setDifficulty(Difficulty.PEACEFUL);
/*  82 */               w.setTime(3000L);
/*     */             } 
/*     */           }
/*     */         },  120L, 120L);
/*     */     
/*  87 */     Bukkit.getScheduler().runTaskTimerAsynchronously((Plugin)this, new Runnable()
/*     */         {
/*     */           public void run() {
/*  90 */             NPCStorage.holographics.forEach((holographic, abstractNPC) -> holographic.refreshLines(abstractNPC.getHolographicLine()));
/*     */           }
/*     */         },  20L, 20L);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void regclass() {
/*  98 */     this.scoreBoardManager = new ScoreBoardManager();
/*  99 */     this.playerManager = new PlayerManager();
/*     */   }
/*     */   
/*     */   void configload() {
/* 103 */     if (!getDataFolder().exists()) {
/* 104 */       getDataFolder().mkdir();
/*     */     }
/* 106 */     File config2 = new File(getDataFolder(), "config.yml");
/* 107 */     if (!config2.exists()) {
/*     */       try {
/* 109 */         config2.createNewFile();
/* 110 */       } catch (IOException exception) {
/* 111 */         exception.printStackTrace();
/*     */       } 
/*     */     }
/* 114 */     config = new Config(config2);
/*     */   }
/*     */   
/*     */   void regMysql() {
/* 118 */     dataBase = DataBase.create("127.0.0.1", 3306, "youxi", "youxi", "mYcS6YDJDmJEesGJ");
/* 119 */     if (!dataBase.isTableExists("bedwars_lootchest"))
/* 120 */       dataBase.createTables("bedwars_lootchest", (new KeyValue("UUID", "VARCHAR(64)"))
/* 121 */           .add((new KeyValue("Name", "VARCHAR(16)"))
/* 122 */             .add(new KeyValue("Amount", "INT"))
/* 123 */             .add(new KeyValue("BoughtItem", "LONGTEXT"))
/* 124 */             .add(new KeyValue("LootItem", "LONGTEXT"))), null); 
/*     */   }
/*     */ }