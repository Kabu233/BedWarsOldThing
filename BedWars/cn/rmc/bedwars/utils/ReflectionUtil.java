/*    */ package cn.rmc.bedwars.utils;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileFilter;
/*    */ import java.net.URL;
/*    */ import java.net.URLDecoder;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Enumeration;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ReflectionUtil
/*    */ {
/*    */   public static List<Class<?>> getClasssFromPackage(String packageName) {
/* 21 */     List<Class<?>> clazzs = new ArrayList<>();
/*    */     
/* 23 */     boolean recursive = true;
/*    */     
/* 25 */     String packageDirName = packageName.replace('.', '/');
/*    */ 
/*    */     
/*    */     try {
/* 29 */       Enumeration<URL> dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
/* 30 */       while (dirs.hasMoreElements()) {
/*    */         
/* 32 */         URL url = dirs.nextElement();
/* 33 */         String protocol = url.getProtocol();
/*    */         
/* 35 */         if ("file".equals(protocol)) {
/* 36 */           String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
/* 37 */           findClassInPackageByFile(packageName, filePath, recursive, clazzs);
/*    */         }
/*    */       
/*    */       } 
/* 41 */     } catch (Exception e) {
/* 42 */       e.printStackTrace();
/*    */     } 
/* 44 */     return clazzs;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void findClassInPackageByFile(String packageName, String filePath, final boolean recursive, List<Class<?>> clazzs) {
/* 52 */     File dir = new File(filePath);
/* 53 */     if (!dir.exists() || !dir.isDirectory()) {
/*    */       return;
/*    */     }
/*    */     
/* 57 */     File[] dirFiles = dir.listFiles(new FileFilter()
/*    */         {
/*    */           public boolean accept(File file) {
/* 60 */             boolean acceptDir = (recursive && file.isDirectory());
/* 61 */             boolean acceptClass = file.getName().endsWith("class");
/* 62 */             return (acceptDir || acceptClass);
/*    */           }
/*    */         });
/*    */     
/* 66 */     for (File file : dirFiles) {
/* 67 */       if (file.isDirectory()) {
/* 68 */         findClassInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), recursive, clazzs);
/*    */       } else {
/* 70 */         String className = file.getName().substring(0, file.getName().length() - 6);
/*    */         try {
/* 72 */           clazzs.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + "." + className));
/* 73 */         } catch (Exception e) {
/* 74 */           e.printStackTrace();
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }
