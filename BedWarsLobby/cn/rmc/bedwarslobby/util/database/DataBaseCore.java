/*    */ package cn.rmc.bedwarslobby.util.database;
/*    */ 
/*    */ import java.io.Closeable;
/*    */ import java.sql.Connection;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ import java.sql.Statement;
/*    */ 
/*    */ public abstract class DataBaseCore
/*    */ {
/*    */   public abstract boolean createTables(String paramString1, KeyValue paramKeyValue, String paramString2) throws SQLException;
/*    */   
/*    */   public abstract Connection getConnection();
/*    */   
/*    */   public abstract Closeable getTotalConnection();
/*    */   
/*    */   public boolean execute(String sql) throws SQLException {
/* 18 */     Group<Connection, Statement> group = getStatement();
/* 19 */     boolean execute = ((Statement)group.getB()).execute(sql);
/* 20 */     ((Statement)group.getB()).close();
/* 21 */     ((Connection)group.getA()).close();
/* 22 */     return execute;
/*    */   }
/*    */   
/*    */   public Group<Group<Connection, Statement>, ResultSet> executeQuery(String sql) throws SQLException {
/* 26 */     Group<Connection, Statement> group = getStatement();
/* 27 */     ResultSet resultSet = ((Statement)group.getB()).executeQuery(sql);
/* 28 */     return new Group<>(group, resultSet);
/*    */   }
/*    */   
/*    */   public int executeUpdate(String sql) throws SQLException {
/* 32 */     Group<Connection, Statement> group = getStatement();
/* 33 */     int i = ((Statement)group.getB()).executeUpdate(sql);
/* 34 */     ((Statement)group.getB()).close();
/* 35 */     ((Connection)group.getA()).close();
/* 36 */     return i;
/*    */   }
/*    */   
/*    */   private Group<Connection, Statement> getStatement() throws SQLException {
/* 40 */     Connection connection = getConnection();
/* 41 */     return new Group<>(connection, connection.createStatement());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static class Group<A, B>
/*    */   {
/*    */     A f17a;
/*    */     
/*    */     B f18b;
/*    */ 
/*    */     
/*    */     public Group(A a, B b) {
/* 54 */       this.f17a = a;
/* 55 */       this.f18b = b;
/*    */     }
/*    */     
/*    */     public A getA() {
/* 59 */       return this.f17a;
/*    */     }
/*    */     
/*    */     public B getB() {
/* 63 */       return this.f18b;
/*    */     }
/*    */   }
/*    */ }