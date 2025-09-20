/*     */ package cn.rmc.bedwarslobby.inventory.item;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.HashMap;
/*     */ import cn.rmc.bedwarslobby.BedWarsLobby;
/*     */ import cn.rmc.bedwarslobby.enums.Data;
/*     */ import cn.rmc.bedwarslobby.enums.GameMode;
/*     */ import cn.rmc.bedwarslobby.util.ItemBuilder;
/*     */ import cn.rmc.bedwarslobby.util.database.DataBaseCore;
/*     */ import cn.rmc.bedwarslobby.util.database.KeyValue;
/*     */ import cn.rmc.bedwarslobby.util.inventory.InventoryUI;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.entity.Player;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StatsItem
/*     */ {
/*     */   Player f6p;
/*     */   String sqlname;
/*     */   String displayname;
/*     */   Integer amount;
/*     */   HashMap<Data.Field, Integer> data;
/*     */   
/*     */   public StatsItem(Player p, GameMode gamemode) {
/*  30 */     this.amount = Integer.valueOf(1);
/*  31 */     this.data = new HashMap<>();
/*  32 */     this.f6p = p;
/*  33 */     this.sqlname = gamemode.name();
/*  34 */     this.displayname = gamemode.getDisplayname();
/*  35 */     this.amount = gamemode.getAmount();
/*  36 */     init();
/*     */   }
/*     */   
/*     */   public StatsItem(Player p, GameMode.TotalGM tgm) {
/*  40 */     this.amount = Integer.valueOf(1);
/*  41 */     this.data = new HashMap<>();
/*  42 */     this.f6p = p;
/*  43 */     this.sqlname = tgm.name();
/*  44 */     this.displayname = tgm.getDpname();
/*  45 */     init();
/*     */   }
/*     */   
/*     */   public StatsItem(Player p, String DPName, String sqlname) {
/*  49 */     this.amount = Integer.valueOf(1);
/*  50 */     this.data = new HashMap<>();
/*  51 */     this.f6p = p;
/*  52 */     this.sqlname = sqlname;
/*  53 */     this.displayname = DPName;
/*  54 */     init();
/*     */   }
/*     */   
/*     */   public void init() {
/*  58 */     KeyValue add = (new KeyValue("UUID", this.f6p.getUniqueId())).add("Mode", this.sqlname);
/*     */     
/*     */     try {
/*  61 */       DataBaseCore.Group<DataBaseCore.Group<Connection, Statement>, ResultSet> group = BedWarsLobby.getDataBase().getDataBaseCore().executeQuery("SELECT * FROM playerstats WHERE " + add.toWhereString() + " LIMIT 1");
/*  62 */       ResultSet resultSet = (ResultSet)group.getB();
/*  63 */       if (resultSet.next()) {
/*  64 */         for (Data.Field value : Data.Field.values()) {
/*  65 */           if (value.getData() == Data.PlayerStats && value != Data.Field.StatsUUID && value != Data.Field.GAMEMODE) {
/*  66 */             this.data.put(value, Integer.valueOf(resultSet.getInt(value.getWhere())));
/*     */           }
/*     */         } 
/*     */       }
/*  70 */       ((ResultSet)group.getB()).close();
/*  71 */       ((Statement)((DataBaseCore.Group)group.getA()).getB()).close();
/*  72 */       ((Connection)((DataBaseCore.Group)group.getA()).getA()).close();
/*  73 */     } catch (SQLException throwables) {
/*  74 */       throwables.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public InventoryUI.EmptyClickableItem build(Material material, Boolean showRank) {
/*  79 */     DecimalFormat df = new DecimalFormat("0.00");
/*  80 */     ItemBuilder ib = new ItemBuilder(material, this.amount.intValue());
/*  81 */     ib.setName("§a" + this.displayname + "统计信息");
/*  82 */     Integer fk = getInt(Data.Field.FINALKILL);
/*  83 */     Integer fd = getInt(Data.Field.FINALDEATH);
/*  84 */     ib.addLoreLine("§7游戏场次: §a" + (getInt(Data.Field.WIN).intValue() + getInt(Data.Field.LOSS).intValue())).addLoreLine("").addLoreLine("§7摧毁的床数: §a" + getInt(Data.Field.BEDSBREAK)).addLoreLine("§7被摧毁的床数: §a" + getInt(Data.Field.BEDSBROKEN)).addLoreLine("").addLoreLine("§7击杀数: §a" + getInt(Data.Field.KILL)).addLoreLine("§7死亡次数: §a" + getInt(Data.Field.DEATH)).addLoreLine("§7最终击杀总数: §a" + fk);
/*  85 */     if (showRank.booleanValue()) {
/*  86 */       ib.addLoreLine("§7每周最终击杀数: §a" + getInt(Data.Field.WEEKLY_FINALKILLS)).addLoreLine("§7每日最终击杀数: §a" + getInt(Data.Field.DAILY_FINALKILLS));
/*     */     }
/*  88 */     ib.addLoreLine("§7最终死亡数: §a" + fd).addLoreLine("§7最终击杀/死亡比: §a" + ((fd.intValue() == 0) ? "0.00" : df.format((fk.intValue() / fd.intValue())))).addLoreLine("").addLoreLine("§7总胜场: §a" + getInt(Data.Field.WIN));
/*  89 */     if (showRank.booleanValue()) {
/*  90 */       ib.addLoreLine("§7每周胜场: §a" + getInt(Data.Field.WEEKLY_WINS)).addLoreLine("§7每日胜场: §a" + getInt(Data.Field.DAILY_WINS));
/*     */     }
/*  92 */     ib.addLoreLine("§7失败场次: §a" + getInt(Data.Field.LOSS)).addLoreLine("").addLoreLine("§7连胜场次: §a" + getInt(Data.Field.WINSTREAK));
/*  93 */     return new InventoryUI.EmptyClickableItem(ib.toItemStack());
/*     */   }
/*     */   
/*     */   public Integer getInt(Data.Field field) {
/*  97 */     if (this.data.get(field) == null) {
/*  98 */       return Integer.valueOf(0);
/*     */     }
/* 100 */     return this.data.get(field);
/*     */   }
/*     */ }