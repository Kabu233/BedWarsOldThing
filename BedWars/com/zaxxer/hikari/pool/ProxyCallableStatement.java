/*    */ package com.zaxxer.hikari.pool;
/*    */ 
/*    */ import java.sql.CallableStatement;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ProxyCallableStatement
/*    */   extends ProxyPreparedStatement
/*    */   implements CallableStatement
/*    */ {
/*    */   protected ProxyCallableStatement(ProxyConnection connection, CallableStatement statement) {
/* 30 */     super(connection, statement);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\BedWars-1.0-SNAPSHOT (1).jar!\com\zaxxer\hikari\pool\ProxyCallableStatement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */