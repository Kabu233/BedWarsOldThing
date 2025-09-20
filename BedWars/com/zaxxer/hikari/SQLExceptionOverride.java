/*    */ package com.zaxxer.hikari;
/*    */ 
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface SQLExceptionOverride
/*    */ {
/*    */   public enum Override
/*    */   {
/* 15 */     CONTINUE_EVICT,
/* 16 */     DO_NOT_EVICT;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default Override adjudicate(SQLException sqlException) {
/* 28 */     return Override.CONTINUE_EVICT;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\BedWars-1.0-SNAPSHOT (1).jar!\com\zaxxer\hikari\SQLExceptionOverride.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */