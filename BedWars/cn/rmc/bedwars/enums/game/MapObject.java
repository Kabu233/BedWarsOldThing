/*    */ package cn.rmc.bedwars.enums.game;
/*    */ 
/*    */ public enum MapObject
/*    */ {
/*    */   private String where;
/*  6 */   NAME(Type.Total),
/*  7 */   MIDDLE(Type.Total),
/*  8 */   BORDERSIZE(Type.Total),
/*  9 */   EACHMAXPLAYER(Type.Total),
/* 10 */   GAMEMODE(Type.Total),
/* 11 */   TEAMSIZE(Type.Total),
/* 12 */   RESOURCE(Type.Res),
/* 13 */   TEAM(Type.Team); private Type type;
/*    */   public String getWhere() {
/* 15 */     return this.where;
/*    */   } public Type getType() {
/* 17 */     return this.type;
/*    */   }
/*    */   
/*    */   MapObject(Type type) {
/* 21 */     this.where = name().toLowerCase();
/* 22 */     this.type = type;
/*    */   }
/*    */   
/*    */   public enum TeamConfig { private String where;
/* 26 */     DISPLAYNAME,
/* 27 */     BED,
/* 28 */     POS1,
/* 29 */     POS2,
/* 30 */     ITEMSHOP,
/* 31 */     TEAMSHOP,
/* 32 */     SPAWN,
/* 33 */     CHEST,
/* 34 */     RESOURCESLOC; public String getWhere() {
/* 35 */       return this.where;
/*    */     }
/*    */     TeamConfig() {
/* 38 */       this.where = name().toLowerCase();
/*    */     } }
/*    */   
/*    */   public enum Type {
/* 42 */     Total, Team, Res;
/*    */   }
/*    */ }