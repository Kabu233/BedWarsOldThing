/*    */ package cn.rmc.bedwarslobby.util.book;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class PageBuilder
/*    */ {
/*  8 */   List<Component> componentList = new ArrayList<>();
/*    */   
/*    */   public static PageBuilder create() {
/* 11 */     return new PageBuilder();
/*    */   }
/*    */   
/*    */   public PageBuilder addComponent(Component component) {
/* 15 */     this.componentList.add(component);
/* 16 */     return this;
/*    */   }
/*    */   
/*    */   public String getPage() {
/* 20 */     String page = "[";
/* 21 */     for (int i = 0; i < this.componentList.size(); i++) {
/* 22 */       if (i > 0) {
/* 23 */         page = page + ",";
/*    */       }
/* 25 */       page = page + ((Component)this.componentList.get(i)).toString();
/*    */     } 
/* 27 */     return page + "]";
/*    */   }
/*    */ }