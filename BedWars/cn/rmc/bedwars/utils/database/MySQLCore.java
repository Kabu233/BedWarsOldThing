/*    */ package cn.rmc.bedwars.utils.database;
/*    */ 
/*    */ import com.zaxxer.hikari.HikariConfig;
/*    */ import com.zaxxer.hikari.HikariDataSource;
/*    */ import java.io.Closeable;
/*    */ import java.sql.Connection;
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ 
/*    */ public class MySQLCore
/*    */   extends DataBaseCore
/*    */ {
/* 13 */   private static String driverName = "com.mysql.jdbc.Driver";
/*    */ 
/*    */   
/*    */   private String username;
/*    */ 
/*    */   
/*    */   private String password;
/*    */ 
/*    */   
/*    */   private String url;
/*    */   
/*    */   private HikariDataSource ds;
/*    */ 
/*    */   
/*    */   public MySQLCore(String host, int port, String dbname, String username, String password) {
/* 28 */     this.url = "jdbc:mysql://" + host + ":" + port + "/" + dbname + "?useSSL=false";
/* 29 */     this.username = username;
/* 30 */     this.password = password;
/*    */     
/* 32 */     HikariConfig config = new HikariConfig();
/* 33 */     config.setJdbcUrl(this.url);
/* 34 */     config.setUsername(username);
/* 35 */     config.setPassword(password);
/*    */     
/* 37 */     config.addDataSourceProperty("connectionTimeout", "1000");
/* 38 */     config.addDataSourceProperty("idleTimeout", "5000");
/* 39 */     config.addDataSourceProperty("maximumPoolSize", "5");
/*    */     
/*    */     try {
/* 42 */       this.ds = new HikariDataSource(config);
/*    */     }
/* 44 */     catch (Exception e) {
/* 45 */       System.out.println("无法连接 原因: " + e.getMessage());
/*    */     } 
/*    */   }
/*    */   
/*    */   public Closeable getTotalConnection() {
/* 59 */     return (Closeable)this.ds;
/*    */   }
/*    */
/*    */   
/*    */   public boolean createTables(String tableName, KeyValue fields, String conditions) throws SQLException {
/* 69 */     String sql = "CREATE TABLE IF NOT EXISTS `" + tableName + "` ( " + fields.toCreateString() + ((conditions == null) ? "" : (" , " + conditions)) + " ) ENGINE = MyISAM DEFAULT CHARSET=GBK;";
/*    */ 
/*    */     
/* 72 */     return execute(sql);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Connection getConnection() {
/*    */     try {
/* 80 */       return this.ds.getConnection();
/*    */     }
/* 82 */     catch (Exception e) {
/*    */       
/* 84 */       System.out.println("数据库操作出错: " + e.getMessage());
/* 85 */       System.out.println("登录URL: " + this.url);
/* 86 */       System.out.println("登录账户: " + this.username);
/* 87 */       System.out.println("登录密码: " + this.password);
/*    */       
/* 89 */       return null;
/*    */     } 
/*    */   }
/*    */ }