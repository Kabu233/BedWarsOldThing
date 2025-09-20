/*    */ package cn.rmc.bedwars.inventory.game;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import cn.rmc.bedwars.game.shop.ShopItemBasic;
/*    */ import org.bukkit.configuration.serialization.ConfigurationSerializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class QuickShopData
/*    */   implements ConfigurationSerializable
/*    */ {
/*    */   public HashMap<Integer, ShopItemBasic> items;
/*    */   private String str;
/*    */   
/*    */   public HashMap<Integer, ShopItemBasic> getItems() {
/* 18 */     return this.items;
/*    */   }
/* 20 */   public String getStr() { return this.str; } public void setStr(String str) {
/* 21 */     this.str = str;
/*    */   }
/*    */   
/*    */   public QuickShopData(HashMap<Integer, ShopItemBasic> items) {
/* 25 */     this.items = items;
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<String, Object> serialize() {
/* 30 */     HashMap<String, Object> map = new HashMap<>();
/* 31 */     map.put("item", this.items);
/* 32 */     return map;
/*    */   }
/*    */ }
