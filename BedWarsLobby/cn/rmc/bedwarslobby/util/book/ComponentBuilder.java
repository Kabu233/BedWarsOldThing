/*    */ package cn.rmc.bedwarslobby.util.book;
/*    */ 
/*    */ public class ComponentBuilder
/*    */ {
/*  5 */   Component component = new Component();
/*    */   
/*    */   public static ComponentBuilder create() {
/*  8 */     return new ComponentBuilder();
/*    */   }
/*    */   
/*    */   public Component getComponent() {
/* 12 */     return this.component;
/*    */   }
/*    */   
/*    */   public ComponentBuilder setText(String text) {
/* 16 */     this.component.setText(text);
/* 17 */     return this;
/*    */   }
/*    */   
/*    */   public ComponentBuilder setColor(String color) {
/* 21 */     this.component.setColor(color);
/* 22 */     return this;
/*    */   }
/*    */   
/*    */   public ComponentBuilder setBold() {
/* 26 */     this.component.setBold();
/* 27 */     return this;
/*    */   }
/*    */   
/*    */   public ComponentBuilder setItalic() {
/* 31 */     this.component.setItalic();
/* 32 */     return this;
/*    */   }
/*    */   
/*    */   public ComponentBuilder setUnderlined() {
/* 36 */     this.component.setUnderlined();
/* 37 */     return this;
/*    */   }
/*    */   
/*    */   public ComponentBuilder setStrikethrough() {
/* 41 */     this.component.setStrikethrough();
/* 42 */     return this;
/*    */   }
/*    */   
/*    */   public ComponentBuilder setObfuscated() {
/* 46 */     this.component.setObfuscated();
/* 47 */     return this;
/*    */   }
/*    */   
/*    */   public ComponentBuilder setClickRunCommand(String cmd) {
/* 51 */     this.component.setClickAction("run_command");
/* 52 */     this.component.setClickValue(cmd);
/* 53 */     return this;
/*    */   }
/*    */   
/*    */   public ComponentBuilder setClickSuggestCommand(String cmd) {
/* 57 */     this.component.setClickAction("suggest_command");
/* 58 */     this.component.setClickValue(cmd);
/* 59 */     return this;
/*    */   }
/*    */   
/*    */   public ComponentBuilder setClickOpenUrl(String url) {
/* 63 */     this.component.setClickAction("open_url");
/* 64 */     this.component.setClickValue(url);
/* 65 */     return this;
/*    */   }
/*    */   
/*    */   public ComponentBuilder setClickChangePage(int page) {
/* 69 */     this.component.setClickAction("change_page");
/* 70 */     this.component.setClickValue(String.valueOf(page));
/* 71 */     return this;
/*    */   }
/*    */   
/*    */   public ComponentBuilder setClickRawValue(String action, String value) {
/* 75 */     this.component.setClickAction(action);
/* 76 */     this.component.setClickValue(value);
/* 77 */     return this;
/*    */   }
/*    */   
/*    */   public ComponentBuilder setHoverShowText(String text) {
/* 81 */     this.component.setHoverAction("show_text");
/* 82 */     this.component.setHoverValue(text);
/* 83 */     return this;
/*    */   }
/*    */   
/*    */   public ComponentBuilder setHoverRawValue(String action, String value) {
/* 87 */     this.component.setHoverAction(action);
/* 88 */     this.component.setHoverValue(value);
/* 89 */     this.component.setHoverValueQuote(true);
/* 90 */     return this;
/*    */   }
/*    */   
/*    */   public ComponentBuilder setHoverRawValueWithoutQuote(String action, String value) {
/* 94 */     this.component.setHoverAction(action);
/* 95 */     this.component.setHoverValue(value);
/* 96 */     this.component.setHoverValueQuote(false);
/* 97 */     return this;
/*    */   }
/*    */ }