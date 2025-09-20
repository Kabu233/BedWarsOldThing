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
/*    */ public class ArmorShop
/*    */   extends ShopBasic {
/*    */   public ArmorShop(Player p) {
/* 15 */     super(p, "装备", Integer.valueOf(3));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected ArrayList<InventoryUI.ClickableItem> product(PlayerData pd) {
/* 21 */     ArrayList<InventoryUI.ClickableItem> result = new ArrayList<>();
/*    */     
/* 23 */     ArrayList<Equipment> equipments = Equipment.getValues(Equipment.Sort.Armor);
/* 24 */     for (Equipment equipment : equipments) {
/* 25 */       if (equipment.getPrice() == null)
/* 26 */         continue;  result.add((new SpecialShopItem(pd, equipment)).showItem(pd, ShopItemBasic.Where.Normal));
/*    */     }
/* 47 */     return result;
/*    */   }
/*    */ }
