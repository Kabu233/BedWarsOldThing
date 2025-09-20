/*    */ package cn.rmc.bedwarslobby.loot.enums;
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum Head
/*    */ {
/*  7 */   LootChest("eyJ0aW1lc3RhbXAiOjE0OTQ3ODIwNDQ0MTMsInByb2ZpbGVJZCI6IjkzYzdmMmUxMTg2MzQ5NzU4OGE2ZWI0YzUwYjRhZGZiIiwic2lnbmF0dXJlUmVxdWlyZWQiOnRydWUsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS83ZmI5OThjZmYzNWU0NmE3Y2I2Y2E5Y2E3NGQ2YzU0NjExODdkYzdlOTRhYzU4NWY3OGJlZWRmYWY5ZjI2ZTIifX19"),
/*  8 */   Close("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWE2Nzg3YmEzMjU2NGU3YzJmM2EwY2U2NDQ5OGVjYmIyM2I4OTg0NWU1YTY2YjVjZWM3NzM2ZjcyOWVkMzcifX19");
/*    */   public String getBase64() {
/* 10 */     return this.base64;
/*    */   }
/*    */   String base64;
/*    */   Head(String base64) {
/* 14 */     this.base64 = base64;
/*    */   }
/*    */ }