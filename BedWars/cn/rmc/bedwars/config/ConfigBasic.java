/*    */ package cn.rmc.bedwars.config;
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import cn.rmc.bedwars.BedWars;
/*    */ import cn.rmc.bedwars.utils.Config;
/*    */ 
/*    */ public class ConfigBasic {
/*    */   private static Config config;
/*    */   
/*    */   public static Config getConfig() {
/* 11 */     return config;
/*    */   }
/*    */   private File file;
/*    */   public ConfigBasic(File folder, String file) {
/* 15 */     if (!folder.exists()) {
/* 16 */       folder.mkdirs();
/*    */     }
/* 18 */     this.file = new File(BedWars.getInstance().getDataFolder(), file);
/* 19 */     load();
/*    */   }
/*    */   public ConfigBasic(String file) {
/* 22 */     this.file = new File(BedWars.getInstance().getDataFolder(), file);
/* 23 */     load();
/*    */   }
/*    */   public void load() {
/* 26 */     if (!this.file.exists()) {
/*    */       try {
/* 28 */         BedWars.getInstance().saveResource(this.file.getName(), true);
/* 29 */       } catch (Exception exception) {
/*    */         try {
/* 31 */           this.file.createNewFile();
/* 32 */         } catch (IOException e) {
/* 33 */           e.printStackTrace();
/*    */         } 
/*    */       } 
/*    */     }
/*    */     
/* 38 */     config = new Config(this.file);
/*    */   }
/*    */   public void save() {
/*    */     try {
/* 42 */       config.save(this.file);
/* 43 */     } catch (IOException e) {
/* 44 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }