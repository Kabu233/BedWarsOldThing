/*     */ package cn.rmc.bedwarslobby.util.book;
/*     */ 
/*     */ public class Component
/*     */ {
/*   5 */   String text = "";
/*   6 */   String color = "";
/*     */   boolean bold = false;
/*     */   boolean italic = false;
/*     */   boolean underlined = false;
/*     */   boolean strikethrough = false;
/*     */   boolean obfuscated = false;
/*  12 */   String clickAction = "";
/*  13 */   String clickValue = "";
/*  14 */   String hoverAction = "";
/*  15 */   String hoverValue = "";
/*     */ 
/*     */   
/*     */   boolean hoverValueQuote = true;
/*     */ 
/*     */   
/*     */   public String toString() {
/*  22 */     String s, s2, s3, s4 = "{\"text\":\"" + this.text + "\"";
/*  23 */     if (!this.color.isEmpty()) {
/*  24 */       s = s4 + ",\"color\":\"" + this.color + "\"";
/*     */     } else {
/*  26 */       s = s4 + ",\"color\":\"black\"";
/*     */     } 
/*  28 */     if (this.bold) {
/*  29 */       s = s + ",\"bold\":true";
/*     */     }
/*  31 */     if (this.italic) {
/*  32 */       s = s + ",\"italic\":true";
/*     */     }
/*  34 */     if (this.underlined) {
/*  35 */       s2 = s + ",\"underlined\":true";
/*     */     } else {
/*  37 */       s2 = s + ",\"underlined\":false";
/*     */     } 
/*  39 */     if (this.strikethrough) {
/*  40 */       s2 = s2 + ",\"strikethrough\":true";
/*     */     }
/*  42 */     if (this.obfuscated) {
/*  43 */       s2 = s2 + ",\"obfuscated\":true";
/*     */     }
/*  45 */     if (!this.clickAction.isEmpty()) {
/*  46 */       s3 = s2 + ",\"clickEvent\":{\"action\":\"" + this.clickAction + "\",\"value\":\"" + this.clickValue + "\"}";
/*     */     } else {
/*  48 */       s3 = s2 + ",\"clickEvent\":{\"action\":\"\",\"value\":\"\"}";
/*     */     } 
/*  50 */     if (!this.hoverAction.isEmpty()) {
/*  51 */       if (this.hoverValueQuote) {
/*  52 */         s3 = s3 + ",\"hoverEvent\":{\"action\":\"" + this.hoverAction + "\",\"value\":\"" + this.hoverValue + "\"}";
/*     */       } else {
/*  54 */         s3 = s3 + ",\"hoverEvent\":{\"action\":\"" + this.hoverAction + "\",\"value\":" + this.hoverValue + "}";
/*     */       } 
/*     */     }
/*  57 */     return s3 + "}";
/*     */   }
/*     */   
/*     */   public void setText(String text) {
/*  61 */     this.text = text;
/*     */   }
/*     */   
/*     */   public void setColor(String color) {
/*  65 */     this.color = color;
/*     */   }
/*     */   
/*     */   public void setBold() {
/*  69 */     this.bold = true;
/*     */   }
/*     */   
/*     */   public void setItalic() {
/*  73 */     this.italic = true;
/*     */   }
/*     */   
/*     */   public void setUnderlined() {
/*  77 */     this.underlined = true;
/*     */   }
/*     */   
/*     */   public void setStrikethrough() {
/*  81 */     this.strikethrough = true;
/*     */   }
/*     */   
/*     */   public void setObfuscated() {
/*  85 */     this.obfuscated = true;
/*     */   }
/*     */   
/*     */   public void setClickAction(String clickAction) {
/*  89 */     this.clickAction = clickAction;
/*     */   }
/*     */   
/*     */   public void setClickValue(String clickValue) {
/*  93 */     this.clickValue = clickValue;
/*     */   }
/*     */   
/*     */   public void setHoverAction(String hoverAction) {
/*  97 */     this.hoverAction = hoverAction;
/*     */   }
/*     */   
/*     */   public void setHoverValue(String hoverValue) {
/* 101 */     this.hoverValue = hoverValue;
/*     */   }
/*     */   
/*     */   public void setHoverValueQuote(boolean quote) {
/* 105 */     this.hoverValueQuote = quote;
/*     */   }
/*     */ }