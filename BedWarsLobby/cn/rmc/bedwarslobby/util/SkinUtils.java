/*    */ package cn.rmc.bedwarslobby.util;
/*    */ import com.google.gson.JsonObject;
/*    */ import java.io.InputStreamReader;
/*    */ import java.net.URL;
/*    */ import java.sql.Connection;
/*    */ import java.sql.PreparedStatement;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class SkinUtils {
/* 13 */   private static final Gson gson = new Gson();
/* 14 */   private static final JsonParser jsonParser = new JsonParser();
/*    */   
/*    */   public static String[] getProfileBySkinRestorer(String name) {
/*    */     try {
/* 18 */       String url = "jdbc:mysql://localhost:3306/skinsrestorer";
/* 19 */       String user = "skinsrestorer";
/* 20 */       String password = "T5f72YNLprLmTPkn";
/*    */       
/* 22 */       try (Connection conn = DriverManager.getConnection(url, user, password)) {
/* 23 */         String str = String.format("SELECT %s FROM sr_player_skins WHERE %s = ?", new Object[] { "value, signature", "last_known_name" });
/*    */         
/* 25 */         PreparedStatement stmt = conn.prepareStatement(str);
/* 26 */         stmt.setString(1, name);
/*    */         
/* 28 */         ResultSet rs = stmt.executeQuery();
/* 29 */         if (rs.next()) {
/* 30 */           return new String[] { rs
/* 31 */               .getString("value"), rs
/* 32 */               .getString("signature") };
/*    */         }
/*    */       }
/* 35 */       catch (SQLException e) {
/* 36 */         e.printStackTrace();
/*    */       } 
/* 38 */       return null;
/*    */     } catch (Throwable $ex) {
/*    */       throw $ex;
/*    */     }  } public static String[] getProfile(String playerName) {
/*    */     try {
/* 43 */       String uuid = fetchUUID(playerName);
/* 44 */       if (uuid == null) {
/* 45 */         return null;
/*    */       }
/* 47 */       Map<String, Object> profileData = fetchProfileData(uuid);
/* 48 */       if (profileData == null) {
/* 49 */         return null;
/*    */       }
/* 51 */       List<Map<String, String>> properties = (List<Map<String, String>>)profileData.get("properties");
/* 52 */       if (properties == null || properties.isEmpty()) {
/* 53 */         return null;
/*    */       }
/*    */ 
/*    */ 
/*    */       
/* 58 */       Map<String, String> textures = properties.stream().filter(p -> "textures".equals(p.get("name"))).findFirst().orElse(null);
/* 59 */       if (textures.get("value") == null || textures.get("signature") == null)
/* 60 */         return null; 
/* 61 */       return new String[] { textures
/* 62 */           .get("value"), textures
/* 63 */           .get("signature") };
/*    */     } catch (Throwable $ex) {
/*    */       throw $ex;
/*    */     } 
/*    */   } private static String fetchUUID(String playerName) throws IOException {
/* 68 */     URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + playerName);
/* 69 */     try (InputStreamReader reader = new InputStreamReader(url.openStream())) {
/* 70 */       JsonObject jsonObject = jsonParser.parse(reader).getAsJsonObject();
/* 71 */       if (jsonObject == null || !jsonObject.has("id")) {
/* 72 */         return null;
/*    */       }
/* 74 */       return jsonObject.get("id").getAsString();
/*    */     } 
/*    */   }
/*    */   
/*    */   private static Map<String, Object> fetchProfileData(String uuid) throws IOException {
/* 79 */     URL url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
/* 80 */     try (InputStreamReader reader = new InputStreamReader(url.openStream())) {
/* 81 */       return (Map)gson.fromJson(reader, Map.class);
/*    */     } 
/*    */   }
/*    */ }