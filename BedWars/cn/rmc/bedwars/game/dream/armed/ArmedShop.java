/*    */ package cn.rmc.bedwars.game.dream.armed;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import cn.rmc.bedwars.game.PlayerData;
/*    */ import cn.rmc.bedwars.game.shop.ShopItemBasic;
/*    */ import cn.rmc.bedwars.game.shop.items.CommonShopItem;
/*    */ import cn.rmc.bedwars.inventory.game.ShopBasic;
/*    */ import cn.rmc.bedwars.utils.inventory.InventoryUI;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class ArmedShop
/*    */   extends ShopBasic
/*    */ {
/*    */   public ArmedShop(Player p) {
/* 15 */     super(p, "枪械", Integer.valueOf(5));
/*    */   }
/*    */ 
/*    */   
/*    */   protected ArrayList<InventoryUI.ClickableItem> product(PlayerData pd) {
/* 20 */     ArrayList<InventoryUI.ClickableItem> result = new ArrayList<>();
/* 21 */     for (GunType gunType : GunType.values()) {
/* 22 */       if (gunType.getPrice() != null)
/* 23 */         result.add((new CommonShopItem(pd, gunType)).showItem(pd, ShopItemBasic.Where.Normal)); 
/*    */     } 
/* 25 */     return result;
/*    */   }
/*    */ }