/*    */ package cn.rmc.bedwars.inventory.game.shops;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import cn.rmc.bedwars.enums.shop.Equipment;
/*    */ import cn.rmc.bedwars.game.PlayerData;
/*    */ import cn.rmc.bedwars.game.shop.ShopItemBasic;
/*    */ import cn.rmc.bedwars.game.shop.items.SpecialShopItem;
/*    */ import cn.rmc.bedwars.inventory.game.ShopBasic;
/*    */ import cn.rmc.bedwars.utils.inventory.InventoryUI;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class ToolShop
/*    */   extends ShopBasic
/*    */ {
/*    */   public ToolShop(Player p) {
/* 16 */     super(p, "装备", Integer.valueOf(4));
/*    */   }
/*    */ 
/*    */   
/*    */   protected ArrayList<InventoryUI.ClickableItem> product(PlayerData pd) {
/* 21 */     ArrayList<InventoryUI.ClickableItem> result = new ArrayList<>();
/*    */     
/* 23 */     result.add((new SpecialShopItem(pd, Equipment.Shears)).showItem(pd, ShopItemBasic.Where.Normal));
/* 24 */     result.add((new SpecialShopItem(pd, Equipment.Sort.Pickaxe)).showItem(pd, ShopItemBasic.Where.Normal));
/* 25 */     result.add((new SpecialShopItem(pd, Equipment.Sort.Axe)).showItem(pd, ShopItemBasic.Where.Normal));
/* 26 */     return result;
/*    */   }
/*    */ }
