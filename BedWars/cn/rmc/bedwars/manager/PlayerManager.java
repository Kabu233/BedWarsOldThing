/*    */ package cn.rmc.bedwars.manager;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.UUID;
/*    */ import cn.rmc.bedwars.BedWars;
/*    */ import cn.rmc.bedwars.enums.IShopItem;
/*    */ import cn.rmc.bedwars.enums.shop.Block;
/*    */ import cn.rmc.bedwars.enums.shop.Equipment;
/*    */ import cn.rmc.bedwars.enums.shop.Melee;
/*    */ import cn.rmc.bedwars.enums.shop.Potion;
/*    */ import cn.rmc.bedwars.enums.shop.Ranged;
/*    */ import cn.rmc.bedwars.enums.shop.Utility;
/*    */ import cn.rmc.bedwars.game.PlayerData;
/*    */ import cn.rmc.bedwars.game.shop.ShopItemBasic;
/*    */ import cn.rmc.bedwars.game.shop.items.CommonShopItem;
/*    */ import cn.rmc.bedwars.game.shop.items.SpecialShopItem;
/*    */ import cn.rmc.bedwars.inventory.game.QuickShopData;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.json.JSONObject;
/*    */ 
/*    */ public class PlayerManager {
/* 22 */   private HashMap<UUID, PlayerData> data = new HashMap<>();
/*    */   public PlayerData add(Player p) {
/* 24 */     if (!this.data.containsKey(p.getUniqueId())) {
/* 25 */       PlayerData pd = new PlayerData(p.getUniqueId());
/* 26 */       this.data.put(p.getUniqueId(), pd);
/*    */     } 
/* 28 */     return this.data.get(p.getUniqueId());
/*    */   } public void load(Player player) {
/*    */     QuickShopData quickShop;
/* 31 */     UUID uuid = player.getUniqueId();
/* 32 */     final PlayerData pd = get(player);
/*    */     
/* 34 */     if (BedWars.getInstance().getQuickBuyManager().getData(uuid) == null) {
/* 35 */       quickShop = new QuickShopData(new HashMap<Integer, ShopItemBasic>()
/*    */           {
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */           
/*    */           });
/* 50 */     } else if (pd.getQuickShopData() != null && pd.getQuickShopData().getStr() != null) {
/* 51 */       Map<String, Object> str = (new JSONObject(pd.getQuickShopData().getStr())).toMap();
/* 52 */       HashMap<Integer, String> data = new HashMap<>();
/* 53 */       for (Map.Entry<String, Object> entry : str.entrySet()) {
/* 54 */         data.put(Integer.valueOf(Integer.parseInt(entry.getKey())), (String)entry.getValue());
/*    */       }
/*    */       
/* 57 */       HashMap<Integer, ShopItemBasic> hashMap = new HashMap<>();
/* 58 */       for (Integer i : data.keySet()) {
/*    */         try {
/* 60 */           String type = data.get(i);
/* 61 */           String[] args = type.split("-");
/* 62 */           if (type.contains("CommonShopItem")) {
/* 63 */             IShopItem iShopItem = null;
/*    */             
/* 65 */             for (Class<? extends IShopItem> clazz : (Iterable<Class<? extends IShopItem>>)IShopItem.clazz) {
/*    */               try {
/* 67 */                 iShopItem = (IShopItem)clazz.getMethod("valueOf", new Class[] { String.class }).invoke(null, new Object[] { args[1] });
/*    */                 break;
/* 69 */               } catch (IllegalArgumentException|java.lang.reflect.InvocationTargetException|NoSuchMethodException|IllegalAccessException illegalArgumentException) {}
/*    */             } 
/*    */ 
/*    */             
/* 73 */             hashMap.put(i, new CommonShopItem(pd, iShopItem));
/*    */           } 
/* 75 */           if (type.contains("SpecialShopItemArmor")) {
/* 76 */             hashMap.put(i, new SpecialShopItem(pd, Equipment.valueOf(args[1])));
/*    */           }
/* 78 */           if (type.contains("SpecialShopItemTool"))
/* 79 */             hashMap.put(i, new SpecialShopItem(pd, Equipment.Sort.valueOf(args[1]))); 
/*    */         } catch (Exception ex) {
/* 81 */           ex.printStackTrace();
/*    */         } 
/* 83 */       }  quickShop = new QuickShopData(hashMap);
/*    */     } else {
/* 85 */       quickShop = BedWars.getInstance().getQuickBuyManager().getData(uuid);
/*    */     } 
/* 87 */     pd.setQuickShopData(quickShop);
/* 88 */     HashMap<Integer, String> result = new HashMap<>();
/* 89 */     for (Integer i : (pd.getQuickShopData()).items.keySet()) {
/* 90 */       result.put(i, ((ShopItemBasic)(pd.getQuickShopData()).items.get(i)).getTotaltype());
/*    */     }
/* 92 */     BedWars.getInstance().getPlayerManager().get(Bukkit.getPlayer(uuid)).getQuickShopData().setStr((new JSONObject(result)).toString());
/*    */   }
/*    */   public PlayerData get(Player p) {
/* 95 */     return this.data.get(p.getUniqueId());
/*    */   }
/*    */ }