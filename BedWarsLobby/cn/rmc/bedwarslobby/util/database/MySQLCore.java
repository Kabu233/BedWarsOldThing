/*    */ package cn.rmc.bedwarslobby.util.database;
/*    */ 
/*    */ import com.zaxxer.hikari.HikariConfig;
/*    */ import com.zaxxer.hikari.HikariDataSource;
/*    */ import java.io.Closeable;
/*    */ import java.sql.Connection;
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ public class MySQLCore
/*    */   extends DataBaseCore {
/* 11 */   private static String driverName = "com.mysql.jdbc.Driver";
/*    */   
/*    */   private String username;
/*    */   
/*    */   private String password;
/*    */   private String url;
/*    */   private HikariDataSource f19ds;
/*    */   
/*    */   public MySQLCore(String host, int port, String dbname, String username, String password) {
/* 20 */     this.url = "jdbc:mysql://" + host + ":" + port + "/" + dbname + "?useSSL=false";
/* 21 */     this.username = username;
/* 22 */     this.password = password;
/* 23 */     HikariConfig config = new HikariConfig();
/* 24 */     config.setJdbcUrl(this.url);
/* 25 */     config.setUsername(username);
/* 26 */     config.setPassword(password);
/* 27 */     config.addDataSourceProperty("connectionTimeout", "3000");
/* 28 */     config.addDataSourceProperty("idleTimeout", "5000");
/* 29 */     config.addDataSourceProperty("maximumPoolSize", "10");
/*    */     try {
/* 31 */       this.f19ds = new HikariDataSource(config);
/* 32 */     } catch (Exception e) {
/* 33 */       System.out.println("无法连接 原因: " + e.getMessage());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Closeable getTotalConnection() {
/* 39 */     return (Closeable)this.f19ds;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean createTables(String tableName, KeyValue fields, String conditions) throws SQLException {
/* 44 */     String sql = "CREATE TABLE IF NOT EXISTS `" + tableName + "` ( " + fields.toCreateString() + ((conditions == null) ? "" : (" , " + conditions)) + " ) ENGINE = MyISAM DEFAULT CHARSET=GBK;";
/* 45 */     return execute(sql);
/*    */   }
/*    */ 
/*    */   
/*    */   public Connection getConnection() {
/*    */     try {
/* 51 */       return this.f19ds.getConnection();
/* 52 */     } catch (Exception e) {
/* 53 */       System.out.println("数据库操作出错: " + e.getMessage());
/* 54 */       System.out.println("登录URL: " + this.url);
/* 55 */       System.out.println("登录账户: " + this.username);
/* 56 */       System.out.println("登录密码: " + this.password);
/* 57 */       return null;
/*    */     } 
/*    */   }