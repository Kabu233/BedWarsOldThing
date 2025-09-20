/*    */ package cn.rmc.bedwars.manager;
/*    */ import java.util.Arrays;
/*    */ import cn.rmc.bedwars.config.Config;
/*    */ import cn.rmc.bedwars.config.ConfigBasic;
/*    */ import cn.rmc.bedwars.config.DataBaseConfig;
/*    */ 
/*    */ public class ConfigManager {
/*    */   private ConfigBasic database;
/*    */   
/*    */   public ConfigBasic getDatabase() {
/* 11 */     return this.database;
/*    */   } private Config config; public Config getConfig() {
/* 13 */     return this.config;
/*    */   }
/*    */   public ConfigManager() {
/* 16 */     load();
/*    */   }
/*    */   public void load() {
/* 19 */     this.config = new Config();
/* 20 */     this.database = (ConfigBasic)new DataBaseConfig();
/*    */   }
/*    */   public void saveAll() {
/* 23 */     Arrays.<ConfigBasic>asList(new ConfigBasic[] { this.database, (ConfigBasic)this.config }).forEach(ConfigBasic::save);
/*    */   }
/*    */ }