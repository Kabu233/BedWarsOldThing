/*     */ package cn.rmc.bedwars.manager;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import cn.rmc.bedwars.enums.game.MapObject;
/*     */ import cn.rmc.bedwars.enums.game.Resource;
/*     */ import cn.rmc.bedwars.enums.game.TeamType;
/*     */ import cn.rmc.bedwars.game.Map;
/*     */ import cn.rmc.bedwars.utils.Config;
/*     */ import cn.rmc.bedwars.utils.Log;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ public class MapManager
/*     */ {
/*  17 */   ArrayList<Map> maps = new ArrayList<>();
/*     */   Plugin plugin;
/*     */   File folder;
/*     */   
/*     */   public MapManager(Plugin plugin) {
/*  22 */     this.plugin = plugin;
/*  23 */     this.folder = new File(plugin.getDataFolder(), "Games");
/*  24 */     if (!this.folder.exists()) this.folder.mkdir(); 
/*  25 */     load();
/*     */   }
/*     */   void load() {
/*  28 */     for (File file : this.folder.listFiles()) {
/*  29 */       if (file.isDirectory()) {
/*  30 */         File yaml = new File(file, file.getName() + ".yml");
/*  31 */         File world = new File(file, "world");
/*  32 */         if (!yaml.exists()) {
/*  33 */           Log.warning(file.getName() + "游戏配置文件不存在 已跳过");
/*     */         } else {
/*     */ 
/*     */           
/*     */           try {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*  44 */             Log.info("开始加载" + file.getName());
/*  45 */             HashMap<String, Object> result = new HashMap<>();
/*  46 */             Config config = new Config(yaml);
/*     */             
/*  48 */             if (!config.getBoolean("enable")) {
/*  49 */               Log.info(file.getName() + "已关闭 已跳过");
/*     */             }
/*  51 */             for (MapObject value : MapObject.values()) {
/*  52 */               if (value.getType() == MapObject.Type.Total)
/*  53 */                 if (value == MapObject.TEAMSIZE) {
/*  54 */                   result.put(value.getWhere(), Integer.valueOf(config.getConfigurationSection("team").getKeys(false).size()));
/*     */                 } else {
/*     */                   
/*  57 */                   result.put(value.getWhere(), config.get(value.getWhere()));
/*     */                 }  
/*  59 */             }  HashMap<Resource, ArrayList<Location>> res = new HashMap<>();
/*  60 */             for (Resource resource : Resource.values()) {
/*  61 */               ArrayList<Location> locs = new ArrayList<>();
/*  62 */               String path = MapObject.RESOURCE.getWhere() + "." + resource.getWhere();
/*  63 */               if (config.getConfigurationSection(path) == null || config.getConfigurationSection(path).getKeys(false) == null) {
/*  64 */                 Log.warning("未发现" + resource.name() + "的坐标配置，已跳过");
/*     */               } else {
/*     */                 
/*  67 */                 for (String s : config.getConfigurationSection(path).getKeys(false)) {
/*  68 */                   locs.add((Location)config.get(path + "." + s));
/*     */                 }
/*  70 */                 res.put(resource, locs);
/*     */               } 
/*  72 */             }  result.put("resourceloc", res);
/*     */             
/*  74 */             HashMap<TeamType, String> displayname = new HashMap<>();
/*  75 */             HashMap<TeamType, Location> respawns = new HashMap<>(), chests = new HashMap<>();
/*  76 */             HashMap<TeamType, Location> beds = new HashMap<>(), teamresourceslocs = new HashMap<>();
/*  77 */             HashMap<TeamType, Location> itemshop = new HashMap<>(), teamshop = new HashMap<>(), pos1 = new HashMap<>(), pos2 = new HashMap<>();
/*  78 */             for (TeamType team : TeamType.values()) {
/*  79 */               if (config.get(MapObject.TEAM.getWhere() + "." + team.getWhere()) != null) {
/*  80 */                 String path = MapObject.TEAM.getWhere() + "." + team.getWhere();
/*  81 */                 displayname.put(team, team.getDisplayname());
/*     */ 
/*     */                 
/*  84 */                 itemshop.put(team, (Location)config.get(path + "." + MapObject.TeamConfig.ITEMSHOP.getWhere()));
/*  85 */                 teamshop.put(team, (Location)config.get(path + "." + MapObject.TeamConfig.TEAMSHOP.getWhere()));
/*  86 */                 respawns.put(team, (Location)config.get(path + "." + MapObject.TeamConfig.SPAWN.getWhere()));
/*  87 */                 chests.put(team, (Location)config.get(path + "." + MapObject.TeamConfig.CHEST.getWhere()));
/*  88 */                 beds.put(team, (Location)config.get(path + "." + MapObject.TeamConfig.BED.getWhere()));
/*  89 */                 teamresourceslocs.put(team, (Location)config.get(path + "." + MapObject.TeamConfig.RESOURCESLOC.getWhere()));
/*  90 */                 pos1.put(team, (Location)config.get(path + "." + MapObject.TeamConfig.POS1.getWhere()));
/*  91 */                 pos2.put(team, (Location)config.get(path + "." + MapObject.TeamConfig.POS2.getWhere()));
/*     */               } 
/*  93 */             }  result.put("teamshop", teamshop);
/*  94 */             result.put("itemshop", itemshop);
/*  95 */             result.put("teamdisplayname", displayname);
/*  96 */             result.put("teamspawn", respawns);
/*  97 */             result.put("teamchest", chests);
/*  98 */             result.put("teambed", beds);
/*  99 */             result.put("teamresourcesloc", teamresourceslocs);
/* 100 */             result.put("teampos1", pos1);
/* 101 */             result.put("teampos2", pos2);
/* 102 */             Map map = new Map(result);
/* 103 */             this.maps.add(map);
/* 104 */             map.getMiddle().getWorld().setTime(6000L);
/*     */           }
/* 106 */           catch (NullPointerException exception) {
/* 107 */             Log.warning("在加载" + file.getName() + "地图时发生错误");
/* 108 */             exception.printStackTrace();
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }