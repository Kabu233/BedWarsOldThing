/*    */ package cn.rmc.bedwars.utils;
/*    */ 
/*    */ import java.sql.Connection;
/*    */ import java.sql.DriverManager;
/*    */ import java.sql.PreparedStatement;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ import java.util.Objects;
/*    */ 
/*    */ public class SkinUtils {
/*    */   public static String getSignaturePlayer(String name) {
/* 12 */     return ((String[])Objects.requireNonNull((T)getProfileBySkinRestorer(name)))[1];
/*    */   }
/*    */   
/*    */   public static String[] getProfileBySkinRestorer(String uuid) {
/*    */     try {
/* 17 */       String url = "jdbc:mysql://localhost:3306/skinsrestorer";
/* 18 */       String user = "skinsrestorer";
/* 19 */       String password = "T5f72YNLprLmTPkn";
/*    */       
/* 21 */       try (Connection conn = DriverManager.getConnection(url, user, password)) {
/* 22 */         String str = String.format("SELECT %s FROM sr_player_skins WHERE %s = ?", new Object[] { "value, signature", "last_known_name" });
/*    */         
/* 24 */         PreparedStatement stmt = conn.prepareStatement(str);
/* 25 */         stmt.setString(1, uuid);
/*    */         
/* 27 */         ResultSet rs = stmt.executeQuery();
/* 28 */         if (rs.next()) {
/* 29 */           return new String[] { rs
/* 30 */               .getString("value"), rs
/* 31 */               .getString("signature") };
/*    */         }
/*    */       }
/* 34 */       catch (SQLException e) {
/* 35 */         e.printStackTrace();
/*    */       } 
/* 37 */       return null;
/*    */     } catch (Throwable $ex) {
/*    */       throw $ex;
/*    */     } 
/*    */   }
/*    */