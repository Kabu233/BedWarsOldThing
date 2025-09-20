/*     */ package cn.rmc.bedwarslobby.util.database;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.DatabaseMetaData;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class DataBase
/*     */ {
/*     */   private final DataBaseCore dataBaseCore;
/*     */   
/*     */   public DataBase(DataBaseCore core) {
/*  17 */     this.dataBaseCore = core;
/*     */   }
/*     */   
/*     */   public static DataBase create(String ip, int port, String table, String user, String password) {
/*  21 */     return new DataBase(new MySQLCore(ip, port, table, user, password));
/*     */   }
/*     */   
/*     */   public boolean close() {
/*     */     try {
/*  26 */       this.dataBaseCore.getTotalConnection().close();
/*  27 */       return true;
/*  28 */     } catch (Exception e) {
/*  29 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean createTables(String tableName, KeyValue fields, String Conditions) {
/*     */     try {
/*  35 */       this.dataBaseCore.createTables(tableName, fields, Conditions);
/*  36 */       return isTableExists(tableName);
/*  37 */     } catch (Exception e) {
/*  38 */       sqlerr("创建数据表 " + tableName + " 异常(内部方法)...", e);
/*  39 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public int dbDelete(String tableName, KeyValue fields) {
/*  44 */     String sql = "DELETE FROM `" + tableName + "` WHERE " + fields.toWhereString();
/*     */     try {
/*  46 */       return this.dataBaseCore.executeUpdate(sql);
/*  47 */     } catch (Exception e) {
/*  48 */       sqlerr(sql, e);
/*  49 */       return 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean dbExist(String tableName, KeyValue fields) {
/*  54 */     String sql = "SELECT * FROM " + tableName + " WHERE " + fields.toWhereString();
/*     */     try {
/*  56 */       DataBaseCore.Group<DataBaseCore.Group<Connection, Statement>, ResultSet> group = this.dataBaseCore.executeQuery(sql);
/*  57 */       boolean next = ((ResultSet)group.getB()).next();
/*  58 */       ((ResultSet)group.getB()).close();
/*  59 */       ((Statement)((DataBaseCore.Group)group.getA()).getB()).close();
/*  60 */       ((Connection)((DataBaseCore.Group)group.getA()).getA()).close();
/*  61 */       return next;
/*  62 */     } catch (Exception e) {
/*  63 */       sqlerr(sql, e);
/*  64 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public int dbInsert(String tabName, KeyValue fields) {
/*  69 */     String sql = "INSERT INTO `" + tabName + "` " + fields.toInsertString();
/*     */     try {
/*  71 */       return this.dataBaseCore.executeUpdate(sql);
/*  72 */     } catch (Exception e) {
/*  73 */       sqlerr(sql, e);
/*  74 */       return 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   public List<KeyValue> dbSelect(String tableName, KeyValue fields, KeyValue selCondition) {
/*  79 */     String sql = "SELECT " + fields.toKeys() + " FROM `" + tableName + "`" + ((selCondition == null) ? "" : (" WHERE " + selCondition.toWhereString()));
/*  80 */     List<KeyValue> kvlist = new ArrayList<>();
/*     */     try {
/*  82 */       DataBaseCore.Group<DataBaseCore.Group<Connection, Statement>, ResultSet> group = this.dataBaseCore.executeQuery(sql);
/*  83 */       ResultSet dbresult = group.getB();
/*  84 */       while (dbresult.next()) {
/*  85 */         KeyValue kv = new KeyValue();
/*  86 */         for (String col : fields.getKeys()) {
/*  87 */           kv.add(col, dbresult.getString(col));
/*     */         }
/*  89 */         kvlist.add(kv);
/*     */       } 
/*  91 */       ((ResultSet)group.getB()).close();
/*  92 */       ((Statement)((DataBaseCore.Group)group.getA()).getB()).close();
/*  93 */       ((Connection)((DataBaseCore.Group)group.getA()).getA()).close();
/*  94 */     } catch (Exception e) {
/*  95 */       sqlerr(sql, e);
/*     */     } 
/*  97 */     return kvlist;
/*     */   }
/*     */   
/*     */   public String dbSelectFirst(String tableName, String fields, KeyValue selConditions) {
/* 101 */     String sql = "SELECT " + fields + " FROM " + tableName + " WHERE " + selConditions.toWhereString() + " LIMIT 1";
/*     */     try {
/* 103 */       DataBaseCore.Group<DataBaseCore.Group<Connection, Statement>, ResultSet> group = this.dataBaseCore.executeQuery(sql);
/* 104 */       ResultSet dbresult = group.getB();
/* 105 */       String s = null;
/* 106 */       if (dbresult.next()) {
/* 107 */         s = dbresult.getString(fields);
/*     */       }
/* 109 */       ((ResultSet)group.getB()).close();
/* 110 */       ((Statement)((DataBaseCore.Group)group.getA()).getB()).close();
/* 111 */       ((Connection)((DataBaseCore.Group)group.getA()).getA()).close();
/* 112 */       return s;
/* 113 */     } catch (Exception e) {
/* 114 */       sqlerr(sql, e);
/* 115 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public int dbUpdate(String tabName, KeyValue fields, KeyValue upCondition) {
/* 120 */     String sql = "UPDATE `" + tabName + "` SET " + fields.toUpdateString() + " WHERE " + upCondition.toWhereString();
/*     */     try {
/* 122 */       return this.dataBaseCore.executeUpdate(sql);
/* 123 */     } catch (Exception e) {
/* 124 */       sqlerr(sql, e);
/* 125 */       return 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   public int dbUpdatebyte(String tabName, String key, byte[] vaule, KeyValue upCondition) throws SQLException {
/* 130 */     Connection connection = this.dataBaseCore.getConnection();
/* 131 */     PreparedStatement ps = connection.prepareStatement("UPDATE `" + tabName + "` SET `" + key + "`= ? WHERE " + upCondition.toWhereString());
/*     */     try {
/* 133 */       ps.setBytes(1, vaule);
/* 134 */       int i = ps.executeUpdate();
/* 135 */       ps.close();
/* 136 */       connection.close();
/* 137 */       return i;
/* 138 */     } catch (Exception e) {
/* 139 */       e.printStackTrace();
/* 140 */       return 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   public DataBaseCore getDataBaseCore() {
/* 145 */     return this.dataBaseCore;
/*     */   }
/*     */   
/*     */   public boolean isTableExists(String tableName) {
/*     */     try {
/* 150 */       Connection connection = this.dataBaseCore.getConnection();
/* 151 */       DatabaseMetaData dbm = connection.getMetaData();
/* 152 */       ResultSet tables = dbm.getTables(null, null, tableName, null);
/* 153 */       boolean next = tables.next();
/* 154 */       connection.close();
/* 155 */       return next;
/* 156 */     } catch (SQLException e) {
/* 157 */       sqlerr("判断 表名:" + tableName + " 是否存在时出错!", e);
/* 158 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void sqlerr(String sql, Exception e) {
/* 163 */     System.out.println("数据库操作出错: " + e.getMessage());
/* 164 */     System.out.println("SQL查询语句: " + sql);
/*     */   }
/*     */ }