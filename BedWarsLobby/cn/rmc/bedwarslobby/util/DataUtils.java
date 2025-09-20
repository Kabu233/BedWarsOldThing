/*    */ package cn.rmc.bedwarslobby.util;
/*    */ 
/*    */ import cn.rmc.bedwarslobby.BedWarsLobby;
/*    */ import cn.rmc.bedwarslobby.enums.Data;
/*    */ import cn.rmc.bedwarslobby.util.database.KeyValue;
/*    */ 
/*    */ public class DataUtils
/*    */ {
/*    */   public static boolean isValueeNull(String uuid, Data.Field field) {
/* 10 */     return (BedWarsLobby.getDataBase().dbSelectFirst(field.getData().getWhere(), field.getWhere(), new KeyValue(Data.Field.UUID.getWhere(), uuid)) == null);
/*    */   }
/*    */   
/*    */   public static boolean isFieldNull(String uuid, Data data) {
/* 14 */     return (BedWarsLobby.getDataBase().dbSelectFirst(data.getWhere(), "UUID", new KeyValue(Data.Field.UUID.getWhere(), uuid)) == null);
/*    */   }
/*    */   
/*    */   public static boolean isFieldNull(String uuid, Data data, String mode) {
/* 18 */     return (BedWarsLobby.getDataBase().dbSelectFirst(data.getWhere(), "UUID", (new KeyValue(Data.Field.UUID.getWhere(), uuid)).add("Mode", mode)) == null);
/*    */   }
/*    */   
/*    */   public static void createField(String uuid, Data data) {
/* 22 */     BedWarsLobby.getDataBase().dbInsert(data.getWhere(), new KeyValue("UUID", uuid));
/*    */   }
/*    */   
/*    */   public static void createField(String uuid, Data data, String gamemode) {
/* 26 */     BedWarsLobby.getDataBase().dbInsert(data.getWhere(), (new KeyValue("UUID", uuid)).add("Mode", gamemode));
/*    */   }
/*    */   
/*    */   public static String get(String uuid, Data.Field field) {
/* 30 */     return BedWarsLobby.getDataBase().dbSelectFirst(field.getData().getWhere(), field.getWhere(), new KeyValue("UUID", uuid));
/*    */   }
/*    */   
/*    */   public static void addInt(String uuid, Data.Field field, Integer i) {
/* 34 */     if (isFieldNull(uuid, field.getData())) {
/* 35 */       createField(uuid, field.getData());
/*    */     }
/* 37 */     int before = 0;
/* 38 */     if (!isValueeNull(uuid, field)) {
/* 39 */       before = Integer.parseInt(get(uuid, field));
/*    */     }
/* 41 */     BedWarsLobby.getDataBase().dbUpdate(field.getData().getWhere(), new KeyValue(field.getWhere(), Integer.valueOf(before + i.intValue())), new KeyValue("UUID", uuid));
/*    */   }
/*    */   
/*    */   public static String get(String uuid, Data.Field field, String mode) {
/* 45 */     return BedWarsLobby.getDataBase().dbSelectFirst(field.getData().getWhere(), field.getWhere(), (new KeyValue("UUID", uuid)).add("Mode", mode));
/*    */   }
/*    */   
/*    */   public static Integer getInt(String uuid, Data.Field field, String mode) {
/* 49 */     String re = get(uuid, field, mode);
/* 50 */     return Integer.valueOf((re == null) ? 0 : Integer.parseInt(re));
/*    */   }
/*    */   
/*    */   public static Integer getInt(String uuid, Data.Field field) {
/* 54 */     String re = get(uuid, field);
/* 55 */     return Integer.valueOf((re == null) ? 0 : Integer.parseInt(re));
/*    */   }
/*    */   
/*    */   public static boolean isValueeNull(String uuid, Data.Field field, String mode) {
/* 59 */     return (BedWarsLobby.getDataBase().dbSelectFirst(field.getData().getWhere(), field.getWhere(), (new KeyValue(Data.Field.UUID.getWhere(), uuid)).add("Mode", mode)) == null);
/*    */   }
/*    */   
/*    */   public static void addInt(String uuid, Data.Field field, Integer i, String mode) {
/* 63 */     if (isFieldNull(uuid, field.getData(), mode)) {
/* 64 */       createField(uuid, field.getData(), mode);
/*    */     }
/* 66 */     int before = 0;
/* 67 */     if (!isValueeNull(uuid, field, mode)) {
/* 68 */       before = Integer.parseInt(get(uuid, field, mode));
/*    */     }
/* 70 */     BedWarsLobby.getDataBase().dbUpdate(field.getData().getWhere(), new KeyValue(field.getWhere(), Integer.valueOf(before + i.intValue())), (new KeyValue("UUID", uuid)).add("Mode", mode));
/*    */   }
/*    */   
/*    */   public static void set(String uuid, Data.Field field, String str) {
/* 74 */     if (isFieldNull(uuid, field.getData())) {
/* 75 */       createField(uuid, field.getData());
/*    */     }
/* 77 */     BedWarsLobby.getDataBase().dbUpdate(field.getData().getWhere(), new KeyValue(field.getWhere(), str), new KeyValue("UUID", uuid));
/*    */   }
/*    */ }