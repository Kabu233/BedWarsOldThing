/*    */ package cn.rmc.bedwars.utils.world;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.util.Arrays;
/*    */ import java.util.Objects;
/*    */ import cn.rmc.bedwars.BedWars;
/*    */ import cn.rmc.bedwars.utils.FileUtil;
/*    */ import org.apache.commons.io.FileUtils;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MapUtil
/*    */ {
/*    */   public static void makeWorldBackup(String worldName) {
/* 20 */     if (worldName == null) {
/*    */       return;
/*    */     }
/* 23 */     World world = Bukkit.getWorld(worldName);
/* 24 */     if (world == null) {
/*    */       return;
/*    */     }
/*    */     try {
/* 28 */       FileUtil.copyFolder(world.getWorldFolder().getPath(), (new File(BedWars.getInstance().getDataFolder(), "backup")).getPath());
/* 29 */       for (File file : (File[])Objects.<File[]>requireNonNull((new File(BedWars.getInstance().getDataFolder(), "backup//" + worldName)).listFiles())) {
/* 30 */         if (!Arrays.<String>asList(new String[] { "region", "level.dat" }).contains(file.getName())) {
/* 31 */           FileUtil.delete(file.getPath());
/*    */         }
/*    */       } 
/* 34 */     } catch (Exception e) {
/* 35 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static void resetMap(File backUpFolder) {
/* 41 */     for (File file : (File[])Objects.<File[]>requireNonNull(backUpFolder.listFiles())) {
/*    */       try {
/* 43 */         FileUtils.deleteDirectory(new File(BedWars.getInstance().getDataFolder(), file.getName()));
/* 44 */         FileUtil.delete((new File(Bukkit.getWorldContainer(), file.getName())).getPath());
/* 45 */         FileUtils.copyDirectory(backUpFolder, Bukkit.getWorldContainer());
/* 46 */       } catch (Exception e) {
/* 47 */         e.printStackTrace();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }