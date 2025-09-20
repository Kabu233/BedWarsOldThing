/*     */ package cn.rmc.bedwarslobby.util.database;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class KeyValue
/*     */ {
/*  10 */   private final Map<Object, Object> keyvalues = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KeyValue(String key, Object value) {
/*  16 */     add(key, value);
/*     */   }
/*     */   
/*     */   public KeyValue add(String key, Object value) {
/*  20 */     this.keyvalues.put(key, value);
/*  21 */     return this;
/*     */   }
/*     */   
/*     */   public KeyValue add(KeyValue kv) {
/*  25 */     for (Map.Entry<Object, Object> entry : kv.keyvalues.entrySet()) {
/*  26 */       this.keyvalues.put(entry.getKey(), entry.getValue());
/*     */     }
/*  28 */     return this;
/*     */   }
/*     */   
/*     */   public KeyValue set(String key, Object value) {
/*  32 */     this.keyvalues.put(key, value);
/*  33 */     return this;
/*     */   }
/*     */   
/*     */   public KeyValue addInt(String key, Integer add) {
/*  37 */     this.keyvalues.put(key, Integer.valueOf(Integer.parseInt(String.valueOf(this.keyvalues.getOrDefault(key, Integer.valueOf(0)))) + add.intValue()));
/*  38 */     return this;
/*     */   }
/*     */   
/*     */   public String[] getKeys() {
/*  42 */     return (String[])this.keyvalues.keySet().stream().map(v0 -> v0.toString())
/*     */       
/*  44 */       .toArray(x$0 -> new String[x$0]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getString(String key) {
/*  50 */     Object obj = this.keyvalues.get(key);
/*  51 */     return (String)obj;
/*     */   }
/*     */   
/*     */   public Object[] getValues() {
/*  55 */     List<Object> keys = new ArrayList();
/*  56 */     for (Map.Entry<Object, Object> next : this.keyvalues.entrySet()) {
/*  57 */       keys.add(next.getValue());
/*     */     }
/*  59 */     return keys.toArray(new Object[0]);
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/*  63 */     return this.keyvalues.isEmpty();
/*     */   }
/*     */   
/*     */   public String toCreateString() {
/*  67 */     StringBuilder sb = new StringBuilder();
/*  68 */     for (Map.Entry<Object, Object> next : this.keyvalues.entrySet()) {
/*  69 */       sb.append("`");
/*  70 */       sb.append(next.getKey());
/*  71 */       sb.append("` ");
/*  72 */       sb.append(next.getValue());
/*  73 */       sb.append(", ");
/*     */     } 
/*  75 */     return sb.substring(0, sb.length() - 2);
/*     */   }
/*     */   
/*     */   public String toInsertString() {
/*  79 */     String ks = "";
/*  80 */     String vs = "";
/*  81 */     for (Map.Entry<Object, Object> next : this.keyvalues.entrySet()) {
/*  82 */       ks = ks + "`" + next.getKey() + "`, ";
/*  83 */       vs = vs + "'" + next.getValue() + "', ";
/*     */     } 
/*  85 */     return "(" + ks.substring(0, ks.length() - 2) + ") VALUES (" + vs.substring(0, vs.length() - 2) + ")";
/*     */   }
/*     */   
/*     */   public String toKeys() {
/*  89 */     StringBuilder sb = new StringBuilder();
/*  90 */     for (Object next : this.keyvalues.keySet()) {
/*  91 */       sb.append("`");
/*  92 */       sb.append(next);
/*  93 */       sb.append("`, ");
/*     */     } 
/*  95 */     return sb.substring(0, sb.length() - 2);
/*     */   }
/*     */   
/*     */   public String toString() {
/*  99 */     return this.keyvalues.toString();
/*     */   }
/*     */   
/*     */   public String toUpdateString() {
/* 103 */     StringBuilder sb = new StringBuilder();
/* 104 */     for (Map.Entry<Object, Object> next : this.keyvalues.entrySet()) {
/* 105 */       sb.append("`");
/* 106 */       sb.append(next.getKey());
/* 107 */       sb.append("`='");
/* 108 */       sb.append(next.getValue());
/* 109 */       sb.append("' ,");
/*     */     } 
/* 111 */     return sb.substring(0, sb.length() - 2);
/*     */   }
/*     */   
/*     */   public String toWhereString() {
/* 115 */     StringBuilder sb = new StringBuilder();
/* 116 */     for (Map.Entry<Object, Object> next : this.keyvalues.entrySet()) {
/* 117 */       sb.append("`");
/* 118 */       sb.append(next.getKey());
/* 119 */       sb.append("`='");
/* 120 */       sb.append(next.getValue());
/* 121 */       sb.append("' and ");
/*     */     } 
/* 123 */     return sb.substring(0, sb.length() - 5);
/*     */   }
/*     */   
/*     */   public KeyValue() {}
/*     */ }