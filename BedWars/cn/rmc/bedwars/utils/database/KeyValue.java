/*     */ package cn.rmc.bedwars.utils.database;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class KeyValue {
/*   9 */   private final Map<Object, Object> keyvalues = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KeyValue(String key, Object value) {
/*  15 */     add(key, value);
/*     */   }
/*     */   
/*     */   public KeyValue add(String key, Object value) {
/*  19 */     this.keyvalues.put(key, value);
/*  20 */     return this;
/*     */   }
/*     */   public KeyValue add(KeyValue kv) {
/*  23 */     for (Map.Entry<Object, Object> entry : kv.keyvalues.entrySet()) {
/*  24 */       this.keyvalues.put(entry.getKey(), entry.getValue());
/*     */     }
/*  26 */     return this;
/*     */   }
/*     */   public KeyValue set(String key, Object value) {
/*  29 */     this.keyvalues.put(key, value);
/*  30 */     return this;
/*     */   }
/*     */   public KeyValue addInt(String key, Integer add) {
/*  33 */     this.keyvalues.put(key, Integer.valueOf(Integer.parseInt(String.valueOf(this.keyvalues.getOrDefault(key, Integer.valueOf(0)))) + add.intValue()));
/*  34 */     return this;
/*     */   }
/*     */   
/*     */   public String[] getKeys() {
/*  38 */     return (String[])this.keyvalues.keySet().stream().map(Object::toString).toArray(x$0 -> new String[x$0]);
/*     */   }
/*     */   
/*     */   public String getString(String key) {
/*  42 */     Object obj = this.keyvalues.get(key);
/*  43 */     return (String)obj;
/*     */   }
/*     */   
/*     */   public Object[] getValues() {
/*  47 */     List<Object> keys = new ArrayList();
/*  48 */     for (Map.Entry<Object, Object> next : this.keyvalues.entrySet()) {
/*  49 */       keys.add(next.getValue());
/*     */     }
/*  51 */     return keys.toArray(new Object[0]);
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/*  55 */     return this.keyvalues.isEmpty();
/*     */   }
/*     */   
/*     */   public String toCreateString() {
/*  59 */     StringBuilder sb = new StringBuilder();
/*  60 */     for (Map.Entry<Object, Object> next : this.keyvalues.entrySet()) {
/*  61 */       sb.append("`");
/*  62 */       sb.append(next.getKey());
/*  63 */       sb.append("` ");
/*  64 */       sb.append(next.getValue());
/*  65 */       sb.append(", ");
/*     */     } 
/*  67 */     return sb.substring(0, sb.length() - 2);
/*     */   }
/*     */   
/*     */   public String toInsertString() {
/*  71 */     String ks = "";
/*  72 */     String vs = "";
/*  73 */     for (Map.Entry<Object, Object> next : this.keyvalues.entrySet()) {
/*  74 */       ks = ks + "`" + next.getKey() + "`, ";
/*  75 */       vs = vs + "'" + next.getValue() + "', ";
/*     */     } 
/*  77 */     return "(" + ks.substring(0, ks.length() - 2) + ") VALUES (" + vs
/*  78 */       .substring(0, vs.length() - 2) + ")";
/*     */   }
/*     */   
/*     */   public String toKeys() {
/*  82 */     StringBuilder sb = new StringBuilder();
/*  83 */     for (Object next : this.keyvalues.keySet()) {
/*  84 */       sb.append("`");
/*  85 */       sb.append(next);
/*  86 */       sb.append("`, ");
/*     */     } 
/*  88 */     return sb.substring(0, sb.length() - 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  93 */     return this.keyvalues.toString();
/*     */   }
/*     */   
/*     */   public String toUpdateString() {
/*  97 */     StringBuilder sb = new StringBuilder();
/*  98 */     for (Map.Entry<Object, Object> next : this.keyvalues.entrySet()) {
/*  99 */       sb.append("`");
/* 100 */       sb.append(next.getKey());
/* 101 */       sb.append("`='");
/* 102 */       sb.append(next.getValue());
/* 103 */       sb.append("' ,");
/*     */     } 
/* 105 */     return sb.substring(0, sb.length() - 2);
/*     */   }
/*     */   
/*     */   public String toWhereString() {
/* 109 */     StringBuilder sb = new StringBuilder();
/* 110 */     for (Map.Entry<Object, Object> next : this.keyvalues.entrySet()) {
/* 111 */       sb.append("`");
/* 112 */       sb.append(next.getKey());
/* 113 */       sb.append("`='");
/* 114 */       sb.append(next.getValue());
/* 115 */       sb.append("' and ");
/*     */     } 
/* 117 */     return sb.substring(0, sb.length() - 5);
/*     */   }
/*     */   
/*     */   public KeyValue() {}
/*     */ }