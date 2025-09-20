/*    */ package cn.rmc.bedwars.inventory.game.shops;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import cn.rmc.bedwars.enums.IShopItem;
/*    */ import cn.rmc.bedwars.enums.shop.Utility;
/*    */ import cn.rmc.bedwars.game.PlayerData;
/*    */ import cn.rmc.bedwars.game.shop.ShopItemBasic;
/*    */ import cn.rmc.bedwars.game.shop.items.CommonShopItem;
/*    */ import cn.rmc.bedwars.inventory.game.ShopBasic;
/*    */ import cn.rmc.bedwars.utils.inventory.InventoryUI;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class UtilityShop
/*    */   extends ShopBasic {
/*    */   public UtilityShop(Player p) {
/* 16 */     super(p, "实用工具", Integer.valueOf(7));
/*    */   }
/*    */ 
/*    */   
/*    */   protected ArrayList<InventoryUI.ClickableItem> product(PlayerData pd) {
/* 21 */     ArrayList<InventoryUI.ClickableItem> result = new ArrayList<>();
/* 22 */     for (Utility utility : Utility.values()) {
/* 23 */       result.add((new CommonShopItem(pd, (IShopItem)utility)).showItem(pd, ShopItemBasic.Where.Normal));
/*    */     }
/* 25 */     return result;
/*    */   }
/*    */ }