/*    */ package cn.rmc.bedwars.utils.player;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import cn.rmc.bedwars.enums.IShopItem;
/*    */ import cn.rmc.bedwars.game.PlayerData;
/*    */ import cn.rmc.bedwars.game.shop.Price;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class InventoryUtils
/*    */ {
/*    */   public static Price getPrice(PlayerData pd, IShopItem item) {
/* 15 */     if (item.getPrices() == null || pd.getGame() == null || pd.getGame().getGameMode() == null) return item.getPrice(); 
/* 16 */     if (item.getPrices() == null) return item.getPrice(); 
/* 17 */     for (Map.Entry<List<String>, Price> entry : (Iterable<Map.Entry<List<String>, Price>>)item.getPrices().entrySet()) {
/* 18 */       String s1 = pd.getGame().getGameMode().name().toLowerCase();
/* 19 */       for (String s : entry.getKey()) {
/* 20 */         if (s1.contains(s.toLowerCase())) {
/* 21 */           return entry.getValue();
/*    */         }
/*    */       } 
/*    */     } 
/* 25 */     return item.getPrice();
/*    */   }
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
/*    */   public static Boolean isEnoughItem(Player player, Price price) {
/* 39 */     Material material = price.getResource().getItem();
/* 40 */     int amount = price.getAmount().intValue();
/* 41 */     return Boolean.valueOf((getitemininv(player, material).intValue() >= amount));
/*    */   }
/*    */   public static Boolean isEnoughItem(Player player, Material material, Integer amount) {
/* 44 */     return Boolean.valueOf((getitemininv(player, material).intValue() >= amount.intValue()));
/*    */   }
/*    */   
/*    */   public static Integer manyleft(Player player, Price price) {
/* 48 */     Material material = price.getResource().getItem();
/* 49 */     int amount = price.getAmount().intValue();
/* 50 */     return Integer.valueOf(amount - getitemininv(player, material).intValue());
/*    */   }
/*    */   public static void deleteitem(Player player, Price price) {
/* 53 */     int i = price.getAmount().intValue();
/* 54 */     Material material = price.getResource().getItem();
/* 55 */     for (ItemStack is : player.getInventory().getContents()) {
/* 56 */       if (i <= 0)
/* 57 */         break;  if (is != null && is.getType() == material) {
/* 58 */         if (i >= is.getAmount()) {
/* 59 */           i -= is.getAmount();
/* 60 */           player.getInventory().removeItem(new ItemStack[] { is });
/*    */         } else {
/* 62 */           is.setAmount(is.getAmount() - i);
/* 63 */           i = 0;
/*    */         } 
/*    */       }
/*    */     } 
/* 67 */     player.updateInventory();
/*    */   }
/*    */   public static void deleteitem(Player player, Material material, int i) {
/* 70 */     for (ItemStack is : player.getInventory().getContents()) {
/* 71 */       if (i <= 0)
/* 72 */         break;  if (is != null && is.getType() == material) {
/* 73 */         if (i >= is.getAmount()) {
/* 74 */           i -= is.getAmount();
/* 75 */           player.getInventory().removeItem(new ItemStack[] { is });
/*    */         } else {
/* 77 */           is.setAmount(is.getAmount() - i);
/* 78 */           i = 0;
/*    */         } 
/*    */       }
/*    */     } 
/* 82 */     player.updateInventory();
/*    */   }
/*    */   public static Integer getitemininv(Player player, Material material) {
/* 85 */     int i = 0;
/* 86 */     for (ItemStack is : player.getInventory().getContents()) {
/* 87 */       if (is != null && is.getType() == material) {
/* 88 */         i += is.getAmount();
/*    */       }
/*    */     } 
/* 91 */     return Integer.valueOf(i);
/*    */   }
/*    */ }