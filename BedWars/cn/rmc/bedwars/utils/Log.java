/*    */ package cn.rmc.bedwars.utils;
/*    */ 
/*    */ import java.util.logging.Logger;
/*    */ import cn.rmc.bedwars.BedWars;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ public class Log
/*    */ {
/* 11 */   private static Logger logger = BedWars.getInstance().getLogger();
/* 12 */   private static String prefix = translate(BedWars.getInstance().getPrefix());
/*    */ 
/*    */ 
/*    */   
/*    */   private static boolean debug = true;
/*    */ 
/*    */ 
/*    */   
/*    */   public static void info(String log) {
/* 21 */     logger.info(log);
/*    */   }
/*    */   
/*    */   public static void debug(String msg) {
/* 25 */     if (debug) {
/* 26 */       logger.info("[DEBUG] " + msg);
/*    */     }
/*    */   }
/*    */   
/*    */   public static void broadcast(String msg) {
/* 31 */     Bukkit.getServer().broadcastMessage(translate(msg));
/*    */   }
/*    */   
/*    */   public static void warning(String warn) {
/* 35 */     logger.warning(warn);
/*    */   }
/*    */   
/*    */   public static void send(CommandSender sender, String msg) {
/* 39 */     sender.sendMessage(prefix + translate(msg));
/*    */   }
/*    */   
/*    */   public static String translate(String msg) {
/* 43 */     return ChatColor.translateAlternateColorCodes('&', msg);
/*    */   }
/*    */ }