/*     */ package cn.rmc.bedwarslobby.object;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import cn.rmc.bedwarslobby.BedWarsLobby;
/*     */ import cn.rmc.bedwarslobby.util.LevelUtils;
/*     */ import cn.rmc.bedwarslobby.util.PlayerUtils;
/*     */ import cn.rmc.bedwarslobby.util.database.KeyValue;
/*     */ import cn.rmc.bedwarslobby.util.holographic.Holographic;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.java.JavaPlugin;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ 
/*     */ public class LeaderBoard {
/*  21 */   static DataBaseCore dataBaseCore = BedWarsLobby.getDataBase().getDataBaseCore();
/*     */   
/*  23 */   static JavaPlugin plugin = (JavaPlugin)BedWarsLobby.getInstance();
/*     */   
/*  25 */   static Location boardLoc = new Location(Bukkit.getWorld("world"), 10.5D, 51.0D, 31.5D);
/*  26 */   static List<TimeType> timeTypes = TimeType.getValues();
/*  27 */   static List<Mode> modes = Mode.getValues();
/*     */   
/*  29 */   static HashMap<String, SQLData> sqls = new HashMap<>();
/*     */   
/*     */   Player player;
/*  32 */   Mode mode = Mode.TOTAL;
/*     */   Holographic level;
/*     */   Holographic win;
/*     */   Holographic winsel;
/*  36 */   TimeType wincurtype = TimeType.LIFETIME;
/*     */   Holographic fk;
/*     */   Holographic fksel;
/*  39 */   TimeType fkcurtype = TimeType.LIFETIME;
/*     */   
/*     */   Holographic selector;
/*  42 */   static HashMap<UUID, String> utn = new HashMap<>(); UUIDAPI uuidapi; public LeaderBoard(Player p) {
/*  43 */     this.uuidapi = (uuid -> {
/*     */         if (utn.containsKey(uuid)) {
/*     */           return PlayerUtils.getPrefixColor(uuid) + (String)utn.get(uuid);
/*     */         }
/*     */         String name = BedWarsLobby.getDataBase().dbSelectFirst("litebans_history", "name", new KeyValue("uuid", uuid));
/*     */         if (name == null) {
/*     */           return "Unknown";
/*     */         }
/*     */         utn.put(uuid, name);
/*     */         return PlayerUtils.getPrefixColor(uuid) + name;
/*     */       });
/*  54 */     this.player = p;
/*     */   }
/*     */ 
/*     */   
/*     */   public void init() {
/*  59 */     this.level = new Holographic(boardLoc.clone().add(0.0D, 0.0D, 0.0D));
/*     */     
/*  61 */     this.win = new Holographic(boardLoc.clone().add(0.0D, 0.0D, 6.0D));
/*  62 */     this.winsel = new Holographic(boardLoc.clone().add(0.0D, -4.95D, 6.0D));
/*  63 */     this.fk = new Holographic(boardLoc.clone().add(0.0D, 0.0D, 3.0D));
/*  64 */     this.fksel = new Holographic(boardLoc.clone().add(0.0D, -4.95D, 3.0D));
/*  65 */     this.selector = new Holographic(boardLoc.clone().add(0.0D, -4.15D, 9.0D));
/*  66 */     initClickableLine();
/*  67 */     for (Holographic hologram : Arrays.<Holographic>asList(new Holographic[] { this.level, this.win, this.winsel, this.fk, this.fksel, this.selector })) {
/*  68 */       hologram.display(this.player);
/*     */     }
/*     */   }
/*     */   
/*     */   public void remove() {
/*  73 */     for (Holographic hologram : Arrays.<Holographic>asList(new Holographic[] { this.level, this.win, this.winsel, this.fk, this.fksel, this.selector })) {
/*  74 */       hologram.remove();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void initClickableLine() {
/*  80 */     this.winsel.setLines(Arrays.asList(new String[] { "§6§l点击切换", getlb(LBType.WINS) }));
/*     */ 
/*     */     
/*  83 */     this.fksel.setLines(Arrays.asList(new String[] { "§6§l点击切换", getlb(LBType.FINALKILLS) }));
/*     */ 
/*     */     
/*  86 */     List<String> selector = new ArrayList<>();
/*  87 */     selector.add("§b§n点击切换");
/*     */     
/*  89 */     for (int i = 0; i <= 2; i++) {
/*  90 */       selector.add(((i == 0) ? "§a§l" : "§7") + ((Mode)modes.get(i)).getName());
/*     */     }
/*     */     
/*  93 */     this.selector.setLines(selector);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 102 */     List<String> fk = new ArrayList<>(Arrays.asList(new String[] { "§b§l" + this.fkcurtype.getName() + LBType.FINALKILLS.getDpname(), "§7" + this.mode.getName() }));
/* 103 */     List<String> win = new ArrayList<>(Arrays.asList(new String[] { "§b§l" + this.wincurtype.getName() + LBType.WINS.getDpname(), "§7" + this.mode.getName() }));
/* 104 */     List<String> level = new ArrayList<>(Arrays.asList(new String[] { "§b§l起床战争等级", "§7所有模式" }));
/* 105 */     for (int j = 1; j <= 10; j++) {
/* 106 */       fk.add(null);
/* 107 */       win.add(null);
/*     */ 
/*     */       
/* 110 */       level.add(null);
/*     */     } 
/*     */     
/* 113 */     this.fk.setLines(fk);
/* 114 */     this.win.setLines(win);
/* 115 */     this.level.setLines(level);
/*     */ 
/*     */     
/* 118 */     (new BukkitRunnable()
/*     */       {
/*     */         public void run() {
/* 121 */           LeaderBoard.this.update(LeaderBoard.LBType.WINS, Boolean.valueOf(false));
/* 122 */           LeaderBoard.this.update(LeaderBoard.LBType.FINALKILLS, Boolean.valueOf(false));
/* 123 */           LeaderBoard.this.update(LeaderBoard.LBType.LEVEL, Boolean.valueOf(false));
/*     */         }
/* 125 */       }).runTaskLaterAsynchronously((Plugin)BedWarsLobby.getInstance(), 2L);
/*     */   }
/*     */   
/*     */   public void update(final LBType lbType, final Boolean tip) {
/* 129 */     (new BukkitRunnable()
/*     */       {
/*     */         public void run() {
/* 132 */           String sql = null; try {
/*     */             int line; String field; List<HashMap<String, Object>> data; int rank; String field1; List<HashMap<String, Object>> data1;
/*     */             int rankff;
/*     */             List<HashMap<String, Object>> data2;
/*     */             int rankaa;
/* 137 */             switch (lbType) {
/*     */               case WINS:
/* 139 */                 field = LeaderBoard.this.getField(LeaderBoard.this.wincurtype, lbType);
/* 140 */                 sql = "SELECT * FROM PlayerStats WHERE `Mode` = '" + LeaderBoard.this.mode.getSqlname() + "' ORDER BY `" + field + "` DESC LIMIT 10";
/*     */                 
/* 142 */                 data = LeaderBoard.this.getData(sql, new String[] { "UUID", field });
/* 143 */                 line = 2;
/* 144 */                 rank = 1;
/* 145 */                 for (HashMap<String, Object> map : data) {
/* 146 */                   UUID uuid = UUID.fromString((String)map.get("UUID"));
/* 147 */                   String name = LeaderBoard.this.uuidapi.getName(uuid);
/* 148 */                   Long i = (Long)map.get(field);
/*     */ 
/*     */                   
/* 151 */                   LeaderBoard.this.setText(LeaderBoard.this.win, Integer.valueOf(line), "§a" + rank++ + ". §7" + name + " §7- §e" + i);
/* 152 */                   line++;
/*     */                 } 
/*     */                 
/* 155 */                 for (; line <= 11; line++)
/*     */                 {
/* 157 */                   LeaderBoard.this.setText(LeaderBoard.this.win, Integer.valueOf(line), null);
/*     */                 }
/*     */                 
/* 160 */                 LeaderBoard.this.setText(LeaderBoard.this.win, Integer.valueOf(0), "§b§l" + LeaderBoard.this.wincurtype.getName() + LeaderBoard.LBType.WINS.getDpname());
/* 161 */                 if (tip.booleanValue()) LeaderBoard.this.player.sendMessage("§a你已切换排行榜为§b§l" + LeaderBoard.this.wincurtype.getName()); 
/*     */                 break;
/*     */               case FINALKILLS:
/* 164 */                 field1 = LeaderBoard.this.getField(LeaderBoard.this.fkcurtype, lbType);
/* 165 */                 sql = "SELECT * FROM PlayerStats WHERE `Mode` = '" + LeaderBoard.this.mode.getSqlname() + "' ORDER BY `" + field1 + "` DESC LIMIT 10";
/*     */                 
/* 167 */                 data1 = LeaderBoard.this.getData(sql, new String[] { "UUID", field1 });
/* 168 */                 line = 2;
/* 169 */                 rankff = 1;
/* 170 */                 for (HashMap<String, Object> map : data1) {
/* 171 */                   UUID uuid = UUID.fromString((String)map.get("UUID"));
/* 172 */                   String name = LeaderBoard.this.uuidapi.getName(uuid);
/* 173 */                   Long i = (Long)map.get(field1);
/*     */ 
/*     */                   
/* 176 */                   LeaderBoard.this.setText(LeaderBoard.this.fk, Integer.valueOf(line), "§a" + rankff++ + ". §7" + name + " §7- §e" + i);
/* 177 */                   line++;
/*     */                 } 
/*     */                 
/* 180 */                 for (; line <= 11; line++) {
/* 181 */                   LeaderBoard.this.setText(LeaderBoard.this.fk, Integer.valueOf(line), null);
/*     */                 }
/*     */                 
/* 184 */                 LeaderBoard.this.setText(LeaderBoard.this.fk, Integer.valueOf(0), "§b§l" + LeaderBoard.this.fkcurtype.getName() + LeaderBoard.LBType.FINALKILLS.getDpname());
/*     */                 
/* 186 */                 if (tip.booleanValue()) LeaderBoard.this.player.sendMessage("§a你已切换排行榜为§b§l" + LeaderBoard.this.fkcurtype.getName()); 
/*     */                 break;
/*     */               case LEVEL:
/* 189 */                 sql = "SELECT * FROM PlayerInfo ORDER BY `Experience` DESC LIMIT 10";
/* 190 */                 data2 = LeaderBoard.this.getData(sql, new String[] { "UUID", "Experience" });
/* 191 */                 line = 2;
/* 192 */                 rankaa = 1;
/* 193 */                 for (HashMap<String, Object> map : data2) {
/* 194 */                   UUID uuid = UUID.fromString((String)map.get("UUID"));
/* 195 */                   String name = LeaderBoard.this.uuidapi.getName(uuid);
/* 196 */                   Long i = (Long)map.get("Experience");
/* 197 */                   LeaderBoard.this.setText(LeaderBoard.this.level, Integer.valueOf(line), "§a" + rankaa++ + ". §7" + name + " §7- §e" + LevelUtils.getLevel(Integer.parseInt(String.valueOf(i))));
/*     */ 
/*     */                   
/* 200 */                   line++;
/*     */                 } 
/* 202 */                 for (; line <= 10; line++) {
/* 203 */                   LeaderBoard.this.setText(LeaderBoard.this.level, Integer.valueOf(line), null);
/*     */                 }
/*     */                 
/* 206 */                 if (tip.booleanValue()) LeaderBoard.this.player.sendMessage("§a你已刷新排行榜"); 
/*     */                 break;
/*     */               case MODE:
/* 209 */                 LeaderBoard.this.update(LeaderBoard.LBType.WINS, Boolean.valueOf(false));
/* 210 */                 LeaderBoard.this.update(LeaderBoard.LBType.FINALKILLS, Boolean.valueOf(false));
/*     */ 
/*     */                 
/* 213 */                 LeaderBoard.this.setText(LeaderBoard.this.win, Integer.valueOf(1), "§7" + LeaderBoard.this.mode.getName());
/* 214 */                 LeaderBoard.this.setText(LeaderBoard.this.fk, Integer.valueOf(1), "§7" + LeaderBoard.this.mode.getName());
/* 215 */                 LeaderBoard.this.player.sendMessage("§a你已切换排行榜为§b§l" + LeaderBoard.this.mode.getName());
/*     */                 break;
/*     */             } 
/* 218 */           } catch (Exception ex) {
/* 219 */             ex.printStackTrace();
/*     */           } 
/*     */         }
/* 222 */       }).runTaskAsynchronously((Plugin)BedWarsLobby.getInstance());
/*     */   }
/*     */   
/*     */   public String getId(String str) {
/* 226 */     return str + this.player.getName();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onClick(Integer entityId) {
/* 231 */     if (this.level.isSame(entityId.intValue(), this.player)) {
/* 232 */       this.player.playSound(this.player.getLocation(), Sound.CLICK, 1.0F, 3.0F);
/* 233 */       BedWarsLobby.getInstance().getScoreBoardManager().sendScoreBoard(this.player);
/* 234 */       update(LBType.LEVEL, Boolean.valueOf(true));
/*     */     }
/* 236 */     else if (this.win.isSame(entityId.intValue(), this.player) || this.winsel.isSame(entityId.intValue(), this.player)) {
/* 237 */       if (timeTypes.indexOf(this.wincurtype) >= timeTypes.size() - 1) {
/* 238 */         this.wincurtype = timeTypes.get(0);
/*     */       } else {
/* 240 */         this.wincurtype = timeTypes.get(timeTypes.indexOf(this.wincurtype) + 1);
/*     */       } 
/* 242 */       this.player.playSound(this.player.getLocation(), Sound.CLICK, 1.0F, 3.0F);
/* 243 */       update(LBType.WINS, Boolean.valueOf(true));
/*     */       
/* 245 */       setText(this.winsel, Integer.valueOf(1), getlb(LBType.WINS));
/*     */     }
/* 247 */     else if (this.fk.isSame(entityId.intValue(), this.player) || this.fksel.isSame(entityId.intValue(), this.player)) {
/* 248 */       if (timeTypes.indexOf(this.fkcurtype) >= timeTypes.size() - 1) {
/* 249 */         this.fkcurtype = timeTypes.get(0);
/*     */       } else {
/* 251 */         this.fkcurtype = timeTypes.get(timeTypes.indexOf(this.fkcurtype) + 1);
/*     */       } 
/* 253 */       this.player.playSound(this.player.getLocation(), Sound.CLICK, 1.0F, 3.0F);
/* 254 */       update(LBType.FINALKILLS, Boolean.valueOf(true));
/*     */       
/* 256 */       setText(this.fksel, Integer.valueOf(1), getlb(LBType.FINALKILLS));
/*     */     }
/* 258 */     else if (this.selector.isSame(entityId.intValue(), this.player)) {
/* 259 */       if (modes.indexOf(this.mode) >= modes.size() - 1) {
/* 260 */         this.mode = modes.get(0);
/*     */       } else {
/* 262 */         this.mode = modes.get(modes.indexOf(this.mode) + 1);
/*     */       } 
/* 264 */       this.player.playSound(this.player.getLocation(), Sound.CLICK, 1.0F, 3.0F);
/* 265 */       update(LBType.MODE, Boolean.valueOf(true));
/*     */ 
/*     */ 
/*     */       
/* 269 */       setText(this.selector, Integer.valueOf(1), "§a" + this.mode.getName());
/* 270 */       setText(this.selector, Integer.valueOf(2), "§7" + getModenext(1).getName());
/* 271 */       setText(this.selector, Integer.valueOf(3), "§7" + getModenext(2).getName());
/*     */     } 
/*     */   }
/*     */   
/*     */   public Mode getModenext(int i) {
/* 276 */     if (modes.indexOf(this.mode) + i > modes.size() - 1) {
/* 277 */       return modes.get(modes.indexOf(this.mode) + i - modes.size());
/*     */     }
/* 279 */     return modes.get(modes.indexOf(this.mode) + i);
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
/*     */   public void setText(Holographic hg, Integer line, String str) {
/* 301 */     hg.setLine(line, str);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getlb(LBType lbType) {
/* 306 */     StringBuilder sb = new StringBuilder();
/* 307 */     TimeType curtt = null;
/* 308 */     switch (lbType) {
/*     */       case WINS:
/* 310 */         curtt = this.wincurtype;
/*     */         break;
/*     */       case FINALKILLS:
/* 313 */         curtt = this.fkcurtype;
/*     */         break;
/*     */     } 
/* 316 */     Iterator<TimeType> it = timeTypes.iterator();
/* 317 */     while (it.hasNext()) {
/* 318 */       TimeType tt = it.next();
/* 319 */       if (tt == curtt) {
/* 320 */         sb.append("§a").append(tt.getName());
/*     */       } else {
/* 322 */         sb.append("§7").append(tt.getName());
/*     */       } 
/* 324 */       if (it.hasNext()) {
/* 325 */         sb.append(" ");
/*     */       }
/*     */     } 
/* 328 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public String getField(TimeType timeType, LBType lbType) {
/* 332 */     if (timeType == TimeType.LIFETIME) {
/* 333 */       return lbType.name();
/*     */     }
/* 335 */     return timeType.getSqlname() + "_" + lbType.getSqlname();
/*     */   }
/*     */   
/*     */   public enum LBType {
/* 339 */     MODE(null, null),
/* 340 */     FINALKILLS("最终击杀数", "FinalKills"),
/* 341 */     WINS("胜利数", "Wins"),
/* 342 */     LEVEL(null, null); String dpname;
/*     */     String sqlname;
/*     */     
/*     */     public String getDpname() {
/* 346 */       return this.dpname;
/*     */     } public String getSqlname() {
/* 348 */       return this.sqlname;
/*     */     }
/*     */     
/*     */     LBType(String str, String sqlname) {
/* 352 */       this.dpname = str;
/* 353 */       this.sqlname = sqlname;
/*     */     } }
/*     */   
/*     */   public enum Mode {
/* 357 */     TOTAL("所有模式"),
/*     */ 
/*     */     
/* 360 */     TEAM("团队"),
/* 361 */     FOUR_FOUR("4v4v4v4"),
/* 362 */     FOUR_FOUR_RUSH("疾速4v4v4v4"),
/* 363 */     FOUR_FOUR_ULTIMATE("超能力4v4v4v4"),
/* 364 */     FOUR_FOUR_ARMED("枪械4v4v4v4"); String name; String sqlname;
/*     */     
/*     */     public String getName() {
/* 367 */       return this.name;
/*     */     } public String getSqlname() {
/* 369 */       return this.sqlname;
/*     */     }
/*     */     Mode(String name) {
/* 372 */       this.name = name;
/* 373 */       this.sqlname = name();
/*     */     }
/*     */     public static List<Mode> getValues() {
/* 376 */       return Arrays.asList((Object[])values().clone());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public enum TimeType
/*     */   {
/* 384 */     LIFETIME("生涯", null),
/* 385 */     WEEKLY("每周", "Weekly"),
/* 386 */     DAILY("每天", "Daily"); String name; String sqlname; public String getName() {
/* 387 */       return this.name;
/*     */     } public String getSqlname() {
/* 389 */       return this.sqlname;
/*     */     }
/*     */     TimeType(String name, String sqlname) {
/* 392 */       this.name = name;
/* 393 */       this.sqlname = sqlname;
/*     */     }
/*     */     public static List<TimeType> getValues() {
/* 396 */       return Arrays.asList((Object[])values().clone());
/*     */     }
/*     */   }
/*     */   
/*     */   public List<HashMap<String, Object>> getData(String sql, String... fields) {
/* 401 */     StringBuilder sb = new StringBuilder();
/* 402 */     sb.append(sql).append("-");
/* 403 */     for (String field : fields) {
/* 404 */       sb.append(field);
/*     */     }
/* 406 */     String unique = sb.toString();
/* 407 */     if (sqls.containsKey(unique)) {
/* 408 */       return ((SQLData)sqls.get(unique)).getData();
/*     */     }
/* 410 */     SQLData sqlData = new SQLData(sql, fields);
/* 411 */     sqls.put(unique, sqlData);
/* 412 */     return sqlData.getData();
/*     */   }
/*     */   
/*     */   static interface UUIDAPI {
/*     */     String getName(UUID param1UUID);
/*     */   }
/*     */ }