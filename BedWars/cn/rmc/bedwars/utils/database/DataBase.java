/*     */ package cn.rmc.bedwars.utils.database;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.DatabaseMetaData;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import cn.rmc.bedwars.utils.Group;
/*     */ 
/*     */ public class DataBase {
/*     */   public DataBase(DataBaseCore core) {
/*  16 */     this.dataBaseCore = core;
/*     */   }
/*     */   private final DataBaseCore dataBaseCore;
/*     */   public static DataBase create(String ip, int port, String table, String user, String password) {
/*  20 */     return new DataBase(new MySQLCore(ip, port, table, user, password));
/*     */   }
/*     */   
/*     */   public boolean close() {
/*     */     try {
/*  25 */       this.dataBaseCore.getTotalConnection().close();
/*  26 */       return true;
/*  27 */     } catch (Exception exception) {
/*     */       
/*  29 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean createTables(String tableName, KeyValue fields, String Conditions) {
/*     */     try {
/*  89 */       this.dataBaseCore.createTables(tableName, fields, Conditions);
/*  90 */       return isTableExists(tableName);
/*  91 */     } catch (Exception e) {
/*  92 */       sqlerr("创建数据表 " + tableName + " 异常(内部方法)...", e);
/*     */       
/*  94 */       return false;
/*     */     } 
/*     */   }
/*     */   public int dbDelete(String tableName, KeyValue fields) {
/*  98 */     String sql = "DELETE FROM `" + tableName + "` WHERE " + fields.toWhereString();
/*     */     try {
/* 100 */       return this.dataBaseCore.executeUpdate(sql);
/* 101 */     } catch (Exception e) {
/* 102 */       sqlerr(sql, e);
/*     */       
/* 104 */       return 0;
/*     */     } 
/*     */   }
/*     */   public boolean dbExist(String tableName, KeyValue fields) {
/* 108 */     String sql = "SELECT * FROM " + tableName + " WHERE " + fields.toWhereString();
/*     */     try {
/* 110 */       Group<Group<Connection, Statement>, ResultSet> group = this.dataBaseCore.executeQuery(sql);
/* 111 */       boolean next = ((ResultSet)group.getB()).next();
/* 112 */       ((ResultSet)group.getB()).close();
/* 113 */       ((Statement)((Group)group.getA()).getB()).close();
/* 114 */       ((Connection)((Group)group.getA()).getA()).close();
/* 115 */       return next;
/* 116 */     } catch (Exception e) {
/* 117 */       sqlerr(sql, e);
/*     */       
/* 119 */       return false;
/*     */     } 
/*     */   }
/*     */   public int dbInsert(String tabName, KeyValue fields) {
/* 123 */     String sql = "INSERT INTO `" + tabName + "` " + fields.toInsertString();
/*     */     try {
/* 125 */       return this.dataBaseCore.executeUpdate(sql);
/* 126 */     } catch (Exception e) {
/* 127 */       sqlerr(sql, e);
/*     */       
/* 129 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<KeyValue> dbSelect(String tableName, KeyValue fields, KeyValue selCondition) {
/* 137 */     String sql = "SELECT " + fields.toKeys() + " FROM `" + tableName + "`" + ((selCondition == null) ? "" : (" WHERE " + selCondition.toWhereString()));
/* 138 */     List<KeyValue> kvlist = new ArrayList<>();
/*     */     try {
/* 140 */       Group<Group<Connection, Statement>, ResultSet> group = this.dataBaseCore.executeQuery(sql);
/* 141 */       ResultSet dbresult = (ResultSet)group.getB();
/* 142 */       while (dbresult.next()) {
/* 143 */         KeyValue kv = new KeyValue();
/* 144 */         for (String col : fields.getKeys()) {
/* 145 */           kv.add(col, dbresult.getString(col));
/*     */         }
/* 147 */         kvlist.add(kv);
/*     */       } 
/* 149 */       ((ResultSet)group.getB()).close();
/* 150 */       ((Statement)((Group)group.getA()).getB()).close();
/* 151 */       ((Connection)((Group)group.getA()).getA()).close();
/* 152 */     } catch (Exception e) {
/* 153 */       sqlerr(sql, e);
/*     */     } 
/* 155 */     return kvlist;
/*     */   }
/*     */ 
/*     */   
/*     */   public String dbSelectFirst(String tableName, String fields, KeyValue selConditions) {
/* 160 */     String sql = "SELECT " + fields + " FROM " + tableName + " WHERE " + selConditions.toWhereString() + " LIMIT 1";
/*     */     try {
/* 162 */       Group<Group<Connection, Statement>, ResultSet> group = this.dataBaseCore.executeQuery(sql);
/* 163 */       ResultSet dbresult = (ResultSet)group.getB();
/* 164 */       String s = null;
/* 165 */       if (dbresult.next()) {
/* 166 */         s = dbresult.getString(fields);
/*     */       }
/* 168 */       ((ResultSet)group.getB()).close();
/* 169 */       ((Statement)((Group)group.getA()).getB()).close();
/* 170 */       ((Connection)((Group)group.getA()).getA()).close();
/* 171 */       return s;
/* 172 */     } catch (Exception e) {
/* 173 */       sqlerr(sql, e);
/*     */       
/* 175 */       return null;
/*     */     } 
/*     */   }
/*     */   public byte[] dbSelectFirstByte(String tableName, String fields, UUID selConditions) {
/* 179 */     String sql = "SELECT " + fields + " from " + tableName + " where uuid = '" + selConditions.toString() + "';";
/*     */     try {
/* 181 */       Group<Group<Connection, Statement>, ResultSet> group = this.dataBaseCore.executeQuery(sql);
/* 182 */       ResultSet dbresult = (ResultSet)group.getB();
/* 183 */       byte[] bytes = null;
/* 184 */       if (dbresult.next()) {
/* 185 */         bytes = dbresult.getBytes(1);
/*     */       }
/* 187 */       ((ResultSet)group.getB()).close();
/* 188 */       ((Statement)((Group)group.getA()).getB()).close();
/* 189 */       ((Connection)((Group)group.getA()).getA()).close();
/* 190 */       return bytes;
/*     */     }
/* 192 */     catch (Exception e) {
/* 193 */       sqlerr(sql, e);
/*     */       
/* 195 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public int dbUpdate(String tabName, KeyValue fields, KeyValue upCondition) {
/* 200 */     String sql = "UPDATE `" + tabName + "` SET " + fields.toUpdateString() + " WHERE " + upCondition.toWhereString();
/*     */     try {
/* 202 */       return this.dataBaseCore.executeUpdate(sql);
/* 203 */     } catch (Exception e) {
/* 204 */       sqlerr(sql, e);
/*     */       
/* 206 */       return 0;
/*     */     } 
/*     */   }
/*     */   public int dbUpdatebyte(String tabName, String key, byte[] vaule, KeyValue upCondition) throws SQLException {
/* 210 */     Connection connection = this.dataBaseCore.getConnection();
/* 211 */     PreparedStatement ps = connection.prepareStatement("UPDATE `" + tabName + "` SET `" + key + "`= ? WHERE " + upCondition
/* 212 */         .toWhereString());
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 217 */       ps.setBytes(1, vaule);
/* 218 */       int i = ps.executeUpdate();
/* 219 */       ps.close();
/* 220 */       connection.close();
/* 221 */       return i;
/*     */     }
/* 223 */     catch (Exception e) {
/* 224 */       e.printStackTrace();
/*     */ 
/*     */       
/* 227 */       return 0;
/*     */     } 
/*     */   }
/*     */   public DataBaseCore getDataBaseCore() {
/* 231 */     return this.dataBaseCore;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValueExists(String tableName, KeyValue fields, KeyValue selCondition) {
/* 238 */     String sql = "SELECT " + fields.toKeys() + " FROM `" + tableName + "`" + ((selCondition == null) ? "" : (" WHERE " + selCondition.toWhereString()));
/*     */     try {
/* 240 */       Group<Group<Connection, Statement>, ResultSet> group = this.dataBaseCore.executeQuery(sql);
/* 241 */       ResultSet dbresult = (ResultSet)group.getB();
/* 242 */       boolean next = dbresult.next();
/* 243 */       ((ResultSet)group.getB()).close();
/* 244 */       ((Statement)((Group)group.getA()).getB()).close();
/* 245 */       ((Connection)((Group)group.getA()).getA()).close();
/* 246 */       return next;
/* 247 */     } catch (Exception e) {
/* 248 */       sqlerr(sql, e);
/*     */       
/* 250 */       return false;
/*     */     } 
/*     */   }
/*     */   public boolean isFieldExists(String tableName, KeyValue fields) {
/*     */     try {
/* 255 */       Connection connection = this.dataBaseCore.getConnection();
/* 256 */       DatabaseMetaData dbm = connection.getMetaData();
/* 257 */       ResultSet tables = dbm.getTables(null, null, tableName, null);
/* 258 */       if (tables.next()) {
/* 259 */         ResultSet f = dbm.getColumns(null, null, tableName, fields.getKeys()[0]);
/* 260 */         boolean next = f.next();
/* 261 */         f.close();
/* 262 */         connection.close();
/* 263 */         return next;
/*     */       } 
/* 265 */     } catch (SQLException e) {
/* 266 */       sqlerr("判断 表名:" + tableName + " 字段名:" + fields.getKeys()[0] + " 是否存在时出错!", e);
/*     */     } 
/* 268 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isTableExists(String tableName) {
/*     */     try {
/* 273 */       Connection connection = this.dataBaseCore.getConnection();
/* 274 */       DatabaseMetaData dbm = connection.getMetaData();
/* 275 */       ResultSet tables = dbm.getTables(null, null, tableName, null);
/* 276 */       boolean next = tables.next();
/* 277 */       connection.close();
/* 278 */       return next;
/* 279 */     } catch (SQLException e) {
/* 280 */       sqlerr("判断 表名:" + tableName + " 是否存在时出错!", e);
/*     */       
/* 282 */       return false;
/*     */     } 
/*     */   }
/*     */   public void sqlerr(String sql, Exception e) {
/* 286 */     System.out.println("数据库操作出错: " + e.getMessage());
/* 287 */     System.out.println("SQL查询语句: " + sql);
/*     */   }
/*     */ }