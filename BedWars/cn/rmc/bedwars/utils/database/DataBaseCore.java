/*    */ package cn.rmc.bedwars.utils.database;
/*    */ 
/*    */ import java.io.Closeable;
/*    */ import java.sql.Connection;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ import java.sql.Statement;
/*    */ import cn.rmc.bedwars.utils.Group;
/*    */ 
/*    */ 
/*    */ public abstract class DataBaseCore
/*    */ {
/*    */   public abstract boolean createTables(String paramString1, KeyValue paramKeyValue, String paramString2) throws SQLException;
/*    */   
/*    */   public boolean execute(String sql) throws SQLException {
/* 16 */     Group<Connection, Statement> group = getStatement();
/* 17 */     boolean execute = ((Statement)group.getB()).execute(sql);
/* 18 */     ((Statement)group.getB()).close();
/* 19 */     ((Connection)group.getA()).close();
/* 20 */     return execute;
/*    */   }
/*    */   
/*    */   public Group<Group<Connection, Statement>, ResultSet> executeQuery(String sql) throws SQLException {
/* 24 */     Group<Connection, Statement> group = getStatement();
/* 25 */     ResultSet resultSet = ((Statement)group.getB()).executeQuery(sql);
/* 26 */     return new Group(group, resultSet);
/*    */   }
/*    */   
/*    */   public int executeUpdate(String sql) throws SQLException {
/* 30 */     Group<Connection, Statement> group = getStatement();
/* 31 */     int i = ((Statement)group.getB()).executeUpdate(sql);
/* 32 */     ((Statement)group.getB()).close();
/* 33 */     ((Connection)group.getA()).close();
/*    */     
/* 35 */     return i;
/*    */   }
/*    */   
/*    */   public abstract Connection getConnection();
/*    */   
/*    */   public abstract Closeable getTotalConnection();
/*    */   
/*    */   private Group<Connection, Statement> getStatement() throws SQLException {
/* 43 */     Connection connection = getConnection();
/* 44 */     return new Group(connection, connection.createStatement());
/*    */   }
/*    */ }