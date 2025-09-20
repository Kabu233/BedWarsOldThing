/*    */ package cn.rmc.bedwars.runnable.item.tower;
/*    */ 
/*    */ 
/*    */ public class TowerBlock {
/*    */   private int horizontal;
/*    */   private int height;
/*    */   
/*  8 */   public int getHorizontal() { return this.horizontal; } private int vertical; private Material type; public int getHeight() {
/*  9 */     return this.height; }
/* 10 */   public int getVertical() { return this.vertical; } public Material getType() {
/* 11 */     return this.type;
/*    */   }
/*    */   public TowerBlock(int horizontal, int height, int vertical) {
/* 14 */     this(horizontal, height, vertical, Material.WOOL);
/*    */   }
/*    */   
/*    */   public TowerBlock(int horizontal, int height, int vertical, Material type) {
/* 18 */     this.horizontal = horizontal;
/* 19 */     this.height = height;
/* 20 */     this.vertical = vertical;
/* 21 */     this.type = type;
/*    */   }
/*    */ }