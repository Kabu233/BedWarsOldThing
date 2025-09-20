/*    */ package cn.rmc.bedwars.utils;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import org.bukkit.configuration.serialization.ConfigurationSerializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DataMap<K, V>
/*    */   extends HashMap<K, V>
/*    */   implements ConfigurationSerializable, Serializable
/*    */ {
/*    */   public V put(K key, V value) {
/* 18 */     return super.put(key, value);
/*    */   }
/*    */ 
/*    */   
/*    */   public V get(Object key) {
/* 23 */     return super.get(key);
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<String, Object> serialize() {
/* 28 */     return (Map)this;
/*    */   }
/*    */ }