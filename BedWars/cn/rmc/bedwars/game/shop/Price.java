/*    */ package cn.rmc.bedwars.game.shop;
/*    */ import cn.rmc.bedwars.enums.game.Resource;
/*    */ 
/*    */ public class Price {
/*    */   protected Resource resource;
/*    */   
/*    */   public Resource getResource() {
/*  8 */     return this.resource;
/*    */   } protected Integer amount;
/*    */   public Integer getAmount() {
/* 11 */     return this.amount;
/*    */   }
/*    */   
/*    */   public Price(Resource resource, Integer amount) {
/* 15 */     this.resource = resource;
/* 16 */     this.amount = amount;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDisplay() {
/* 21 */     return "§7花费: " + this.resource.getColor() + this.amount + "块" + this.resource.getDisplayName();
/*    */   }
/*    */   public String getEzDisplay() {
/* 24 */     return this.amount + " " + this.resource.getDisplayName();
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 29 */     return "Price{resource=" + this.resource + ", amount=" + this.amount + '}';
/*    */   }
/*    */ }
