/*    */ package cn.rmc.bedwars.inventory.game.shops;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import cn.rmc.bedwars.enums.IShopItem;
/*    */ import cn.rmc.bedwars.enums.shop.Ranged;
/*    */ import cn.rmc.bedwars.game.PlayerData;
/*    */ import cn.rmc.bedwars.game.shop.ShopItemBasic;
/*    */ import cn.rmc.bedwars.game.shop.items.CommonShopItem;
/*    */ import cn.rmc.bedwars.inventory.game.ShopBasic;
/*    */ import cn.rmc.bedwars.utils.inventory.InventoryUI;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class RangedShop extends ShopBasic {
/*    */   public RangedShop(Player p) {
/* 15 */     super(p, "远程武器", Integer.valueOf(5));
/*    */   }
/*    */ 
/*    */   
/*    */   protected ArrayList<InventoryUI.ClickableItem> product(PlayerData pd) {
/* 20 */     ArrayList<InventoryUI.ClickableItem> result = new ArrayList<>();
/* 21 */     for (Ranged ranged : Ranged.values()) {
/* 22 */       result.add((new CommonShopItem(pd, (IShopItem)ranged)).showItem(pd, ShopItemBasic.Where.Normal));
/*    */     }
/* 24 */     return result;
/*    */   }
/*    */ }
