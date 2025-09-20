/*     */ package cn.rmc.bedwars.manager;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import cn.rmc.bedwars.BedWars;
/*     */ import cn.rmc.bedwars.config.DataBaseConfig;
/*     */ import cn.rmc.bedwars.config.YamlDataConfig;
/*     */ import cn.rmc.bedwars.enums.Data;
/*     */ import cn.rmc.bedwars.enums.DataBaseType;
/*     */ import cn.rmc.bedwars.enums.IShopItem;
/*     */ import cn.rmc.bedwars.enums.shop.Block;
/*     */ import cn.rmc.bedwars.enums.shop.Equipment;
/*     */ import cn.rmc.bedwars.game.PlayerData;
/*     */ import cn.rmc.bedwars.game.shop.ShopItemBasic;
/*     */ import cn.rmc.bedwars.game.shop.items.CommonShopItem;
/*     */ import cn.rmc.bedwars.game.shop.items.SpecialShopItem;
/*     */ import cn.rmc.bedwars.inventory.game.QuickShopData;
/*     */ import cn.rmc.bedwars.utils.player.DataUtils;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.json.JSONObject;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class QuickBuyManager
/*     */ {
/*     */   private DataBaseType type;
/*     */   private static String prefix;
/*     */   
/*     */   public QuickBuyManager(DataBaseType type) {
/*  39 */     this.type = type;
/*  40 */     prefix = DataBaseConfig.getConfig().getString("table_prefix", "bedwars_");
/*     */   }
/*     */   
/*     */   public void save(UUID uuid, QuickShopData data) {
/*     */     try {
/*  45 */       HashMap<Integer, String> result = new HashMap<>();
/*  46 */       for (Integer i : data.getItems().keySet()) {
/*  47 */         result.put(i, ((ShopItemBasic)data.getItems().get(i)).getTotaltype());
/*     */       }
/*  49 */       BedWars.getInstance().getPlayerManager().get(Bukkit.getPlayer(uuid)).getQuickShopData().setStr((new JSONObject(result)).toString());
/*  50 */       if (this.type == DataBaseType.MYSQL) {
/*  51 */         DataUtils.set(uuid.toString(), Data.Field.QUICKSHOP, (new JSONObject(result)).toString());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*  60 */       else if (this.type == DataBaseType.YAML) {
/*  61 */         YamlDataConfig a = new YamlDataConfig(uuid);
/*  62 */         YamlDataConfig.getConfig().set("data", data);
/*  63 */         a.save();
/*     */       } 
/*     */     } catch (Throwable $ex) {
/*     */       throw $ex;
/*     */     }  } public QuickShopData getData(UUID uuid) {
/*     */     try {
/*  69 */       PlayerData pd = BedWars.getInstance().getPlayerManager().get(Bukkit.getPlayer(uuid));
/*  70 */       if (this.type == DataBaseType.MYSQL) {
/*  71 */         if (DataUtils.isValueeNull(uuid.toString(), Data.Field.QUICKSHOP)) {
/*  72 */           return null;
/*     */         }
/*  74 */         Map<String, Object> str = (new JSONObject(DataUtils.get(uuid.toString(), Data.Field.QUICKSHOP))).toMap();
/*  75 */         HashMap<Integer, String> data = new HashMap<>();
/*  76 */         for (Map.Entry<String, Object> entry : str.entrySet()) {
/*  77 */           data.put(Integer.valueOf(Integer.parseInt(entry.getKey())), (String)entry.getValue());
/*     */         }
/*     */         
/*  80 */         HashMap<Integer, ShopItemBasic> result = new HashMap<>();
/*  81 */         for (Integer i : data.keySet()) {
/*     */           try {
/*  83 */             String type = data.get(i);
/*  84 */             String[] args = type.split("-");
/*  85 */             if (type.contains("CommonShopItem")) {
/*  86 */               IShopItem iShopItem = null;
/*     */               
/*  88 */               for (Class<? extends IShopItem> clazz : (Iterable<Class<? extends IShopItem>>)IShopItem.clazz) {
/*     */                 try {
/*  90 */                   iShopItem = (IShopItem)clazz.getMethod("valueOf", new Class[] { String.class }).invoke(null, new Object[] { args[1] });
/*     */                   break;
/*  92 */                 } catch (IllegalArgumentException|java.lang.reflect.InvocationTargetException exceptione) {}
/*     */               } 
/*     */ 
/*     */ 
/*     */               
/*  97 */               if (pd.getGame() != null && pd.getGame().getGameMode() != null && iShopItem != null && pd.getGame().getGameMode().name().contains("RUSH") && iShopItem.equals(Block.OBSIDIAN))
/*     */                 continue; 
/*  99 */               if (pd.getGame() != null && pd.getGame().getGameMode() != null && iShopItem != null && !pd.getGame().getGameMode().name().contains("ARMED") && iShopItem instanceof cn.rmc.bedwars.game.dream.armed.GunType)
/*     */                 continue; 
/* 101 */               if (iShopItem == null)
/* 102 */                 continue;  result.put(i, new CommonShopItem(pd, iShopItem));
/*     */             } 
/* 104 */             if (type.contains("SpecialShopItemArmor")) {
/* 105 */               result.put(i, new SpecialShopItem(pd, Equipment.valueOf(args[1])));
/*     */             }
/* 107 */             if (type.contains("SpecialShopItemTool")) {
/* 108 */               result.put(i, new SpecialShopItem(pd, Equipment.Sort.valueOf(args[1])));
/*     */             }
/* 110 */           } catch (Exception exception) {}
/*     */         } 
/* 112 */         return new QuickShopData(result);
/*     */       } 
/* 114 */       if (this.type == DataBaseType.YAML) {
/* 115 */         YamlDataConfig a = new YamlDataConfig(uuid);
/* 116 */         if (YamlDataConfig.getConfig().get("data") == null) {
/* 117 */           return null;
/*     */         }
/* 119 */         return (QuickShopData)YamlDataConfig.getConfig().get("data");
/*     */       } 
/*     */       
/* 122 */       return null;
/*     */     } catch (Throwable $ex) {
/*     */       throw $ex;
/*     */     } 
/*     */   }
/*     */ }