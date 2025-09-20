/*    */ package net.jitse.npclib.api.skin;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonParser;
/*    */ import java.io.IOException;
/*    */ import java.net.HttpURLConnection;
/*    */ import java.net.URL;
/*    */ import java.util.Scanner;
/*    */ import java.util.concurrent.ExecutorService;
/*    */ import java.util.concurrent.Executors;
/*    */ import org.bukkit.Bukkit;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MineSkinFetcher
/*    */ {
/*    */   private static final String MINESKIN_API = "https://api.mineskin.org/v2/skins/";
/* 24 */   private static final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor();
/*    */   
/*    */   public static void fetchSkinFromUUIDAsync(String uuid, String token, Callback callback) {
/* 27 */     EXECUTOR.execute(() -> {
/*    */           try {
/*    */             StringBuilder builder = new StringBuilder();
/*    */             
/*    */             HttpURLConnection httpURLConnection = (HttpURLConnection)(new URL("https://api.mineskin.org/v2/skins/" + uuid)).openConnection();
/*    */             
/*    */             httpURLConnection.setRequestProperty("User-Agent", "NPCLib/v2.13.1");
/*    */             
/*    */             httpURLConnection.setRequestProperty("Accept", "application/json");
/*    */             
/*    */             httpURLConnection.setRequestProperty("Authorization", "Bearer " + token);
/*    */             
/*    */             httpURLConnection.setRequestMethod("GET");
/*    */             httpURLConnection.setDoOutput(true);
/*    */             httpURLConnection.setDoInput(true);
/*    */             httpURLConnection.connect();
/*    */             Scanner scanner = new Scanner(httpURLConnection.getInputStream());
/*    */             while (scanner.hasNextLine()) {
/*    */               builder.append(scanner.nextLine());
/*    */             }
/*    */             scanner.close();
/*    */             httpURLConnection.disconnect();
/*    */             JsonObject jsonObject = (JsonObject)(new JsonParser()).parse(builder.toString());
/*    */             JsonObject textures = jsonObject.get("skin").getAsJsonObject().get("texture").getAsJsonObject().get("data").getAsJsonObject();
/*    */             String value = textures.get("value").getAsString();
/*    */             String signature = textures.get("signature").getAsString();
/*    */             callback.call(new Skin(value, signature));
/* 54 */           } catch (IOException exception) {
/*    */             Bukkit.getLogger().severe("Could not fetch skin! (Id: " + uuid + "). Message: " + exception.getMessage());
/*    */             exception.printStackTrace();
/*    */             callback.failed();
/*    */           } 
/*    */         });
/*    */   }
/*    */   
/*    */   public static void fetchSkinFromUUIDSync(String uuid, String token, Callback callback) {
/*    */     try {
/* 64 */       StringBuilder builder = new StringBuilder();
/* 65 */       HttpURLConnection httpURLConnection = (HttpURLConnection)(new URL("https://api.mineskin.org/v2/skins/" + uuid)).openConnection();
/* 66 */       httpURLConnection.setRequestProperty("User-Agent", "NPCLib/v2.13");
/* 67 */       httpURLConnection.setRequestProperty("Accept", "application/json");
/* 68 */       httpURLConnection.setRequestProperty("Authorization", "Bearer " + token);
/* 69 */       httpURLConnection.setRequestMethod("GET");
/* 70 */       httpURLConnection.setDoOutput(true);
/* 71 */       httpURLConnection.setDoInput(true);
/* 72 */       httpURLConnection.connect();
/*    */       
/* 74 */       Scanner scanner = new Scanner(httpURLConnection.getInputStream());
/* 75 */       while (scanner.hasNextLine()) {
/* 76 */         builder.append(scanner.nextLine());
/*    */       }
/*    */       
/* 79 */       scanner.close();
/* 80 */       httpURLConnection.disconnect();
/*    */       
/* 82 */       JsonObject jsonObject = (JsonObject)(new JsonParser()).parse(builder.toString());
/*    */ 
/*    */       
/* 85 */       JsonObject textures = jsonObject.get("skin").getAsJsonObject().get("texture").getAsJsonObject().get("data").getAsJsonObject();
/* 86 */       String value = textures.get("value").getAsString();
/* 87 */       String signature = textures.get("signature").getAsString();
/*    */       
/* 89 */       callback.call(new Skin(value, signature));
/* 90 */     } catch (IOException exception) {
/* 91 */       Bukkit.getLogger().severe("Could not fetch skin! (Id: " + uuid + "). Message: " + exception.getMessage());
/* 92 */       exception.printStackTrace();
/* 93 */       callback.failed();
/*    */     } 
/*    */   }
/*    */   
/*    */   public static interface Callback {
/*    */     void call(Skin param1Skin);
/*    */     
/*    */     default void failed() {}
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\BedWarsLobby-1.0-SNAPSHOT.jar!\net\jitse\npclib\api\skin\MineSkinFetcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */