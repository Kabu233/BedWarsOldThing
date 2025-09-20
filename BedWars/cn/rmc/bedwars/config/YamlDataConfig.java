/*    */ package cn.rmc.bedwars.config;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.util.UUID;
/*    */ import cn.rmc.bedwars.BedWars;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class YamlDataConfig
/*    */   extends ConfigBasic
/*    */ {
/* 15 */   private static File folder = new File(BedWars.getInstance().getDataFolder() + "data/");
/*    */   public YamlDataConfig(UUID uuid) {
/* 17 */     super(folder, uuid.toString() + ".yml");
/*    */   }
/*    */ }