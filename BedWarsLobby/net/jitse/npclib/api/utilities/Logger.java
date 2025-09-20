/*    */ package net.jitse.npclib.api.utilities;
/*    */ 
/*    */ import java.util.logging.Level;
/*    */ import org.bukkit.Bukkit;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Logger
/*    */ {
/*    */   private final String prefix;
/*    */   private boolean enabled = true;
/*    */   
/*    */   public Logger(String prefix) {
/* 18 */     this.prefix = prefix + " ";
/*    */   }
/*    */   
/*    */   public void disable() {
/* 22 */     this.enabled = false;
/*    */   }
/*    */   
/*    */   public void info(String info) {
/* 26 */     log(Level.INFO, info);
/*    */   }
/*    */   
/*    */   public void warning(String warning) {
/* 30 */     log(Level.WARNING, warning);
/*    */   }
/*    */   
/*    */   public void warning(String warning, Throwable throwable) {
/* 34 */     log(Level.WARNING, warning, throwable);
/*    */   }
/*    */   
/*    */   public void severe(String severe) {
/* 38 */     log(Level.SEVERE, severe);
/*    */   }
/*    */   
/*    */   public void severe(String severe, Throwable throwable) {
/* 42 */     log(Level.SEVERE, severe, throwable);
/*    */   }
/*    */   
/*    */   public void log(Level level, String message) {
/* 46 */     if (this.enabled) {
/* 47 */       Bukkit.getLogger().log(level, this.prefix + message);
/*    */     }
/*    */   }
/*    */   
/*    */   public void log(Level level, String message, Throwable throwable) {
/* 52 */     if (this.enabled)
/* 53 */       Bukkit.getLogger().log(level, this.prefix + message, throwable); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\BedWarsLobby-1.0-SNAPSHOT.jar!\net\jitse\npclib\ap\\utilities\Logger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */