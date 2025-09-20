/*    */ package cn.rmc.bedwars.utils.player;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import cn.rmc.bedwars.BedWars;
/*    */ import cn.rmc.bedwars.enums.Data;
/*    */ import cn.rmc.bedwars.enums.game.Resource;
/*    */ import cn.rmc.bedwars.utils.database.KeyValue;
/*    */ import org.json.JSONObject;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DataUtils
/*    */ {
/*    */   public static boolean isValueeNull(String uuid, Data.Field field) {
/* 18 */     return (BedWars.getInstance().getDataBase().dbSelectFirst(field.getData().getWhere(), field.getWhere(), new KeyValue(Data.Field.UUID.getWhere(), uuid)) == null);
/*    */   }
/*    */   
/*    */   public static boolean isFieldNull(String uuid, Data data) {
/* 22 */     return (BedWars.getInstance().getDataBase().dbSelectFirst(data.getWhere(), "UUID", new KeyValue(Data.Field.UUID.getWhere(), uuid)) == null);
/*    */   }
/*    */   
/*    */   public static boolean isFieldNull(String uuid, Data data, String mode) {
/* 26 */     return (BedWars.getInstance().getDataBase().dbSelectFirst(data.getWhere(), "UUID", (new KeyValue(Data.Field.UUID.getWhere(), uuid)).add("Mode", mode)) == null);
/*    */   }
/*    */   
/*    */   public static void createField(String uuid, Data data) {
/* 30 */     BedWars.getInstance().getDataBase().dbInsert(data.getWhere(), new KeyValue("UUID", uuid));
/*    */   }
/*    */   
/*    */   public static void createField(String uuid, Data data, String gamemode) {
/* 34 */     BedWars.getInstance().getDataBase().dbInsert(data.getWhere(), (new KeyValue("UUID", uuid)).add("Mode", gamemode));
/*    */   }
/*    */   
/*    */   public static String get(String uuid, Data.Field field) {
/* 38 */     return BedWars.getInstance().getDataBase().dbSelectFirst(field.getData().getWhere(), field.getWhere(), new KeyValue("UUID", uuid));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String get(String uuid, Data.Field field, String mode) {
/* 50 */     return BedWars.getInstance().getDataBase().dbSelectFirst(field.getData().getWhere(), field.getWhere(), (new KeyValue("UUID", uuid)).add("Mode", mode));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static JSONObject JsongetCollection(String before, HashMap<Resource, Integer> hash) {
/*    */     JSONObject jo;
/* 58 */     if (before == null) {
/* 59 */       jo = new JSONObject();
/*    */     } else {
/* 61 */       jo = new JSONObject(before);
/*    */     } 
/*    */ 
/*    */     
/* 65 */     for (Map.Entry<Resource, Integer> entry : hash.entrySet()) {
/* 66 */       int i = jo.isNull(((Resource)entry.getKey()).name()) ? 0 : jo.getInt(((Resource)entry.getKey()).name());
/* 67 */       jo.put(((Resource)entry.getKey()).name(), i + ((Integer)entry.getValue()).intValue());
/*    */     } 
/* 69 */     return jo;
/*    */   }
/*    */   
/*    */   public static void set(String uuid, Data.Field field, String str) {
/* 73 */     if (isFieldNull(uuid, field.getData())) {
/* 74 */       createField(uuid, field.getData());
/*    */     }
/*    */     
/* 77 */     BedWars.getInstance().getDataBase().dbUpdate(field.getData().getWhere(), new KeyValue(field.getWhere(), str), new KeyValue("UUID", uuid));
/*    */   }
/*    */   
/*    */   public static void set(String uuid, Data.Field field, String str, String mode) {
/* 81 */     if (isFieldNull(uuid, field.getData(), mode)) {
/* 82 */       createField(uuid, field.getData(), mode);
/*    */     }
/*    */     
/* 85 */     BedWars.getInstance().getDataBase().dbUpdate(field.getData().getWhere(), new KeyValue(field.getWhere(), str), (new KeyValue("UUID", uuid)).add("Mode", mode));
/*    */   }
/*    */ }