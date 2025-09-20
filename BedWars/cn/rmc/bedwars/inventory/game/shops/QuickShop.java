/*    */ package cn.rmc.bedwars.inventory.game.shops;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import cn.rmc.bedwars.BedWars;
/*    */ import cn.rmc.bedwars.game.PlayerData;
/*    */ import cn.rmc.bedwars.game.shop.ShopItemBasic;
/*    */ import cn.rmc.bedwars.inventory.game.ShopBasic;
/*    */ import cn.rmc.bedwars.utils.inventory.InventoryUI;
/*    */ import cn.rmc.bedwars.utils.item.ItemBuilder;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class QuickShop
/*    */   extends ShopBasic {
/*    */   public QuickShop(Player p) {
/* 17 */     super(p, "快速商店", Integer.valueOf(0));
/*    */   }
/*    */ 
/*    */   
/*    */   protected ArrayList<InventoryUI.ClickableItem> product(PlayerData pd) {
/* 22 */     ArrayList<InventoryUI.ClickableItem> result = new ArrayList<>();
/* 23 */     HashMap<Integer, ShopItemBasic> map = pd.getQuickShopData().getItems();
/* 24 */     for (int i = 0; i <= 20; i++) {
/* 25 */       if (map.containsKey(Integer.valueOf(i))) {
/* 26 */         result.add(((ShopItemBasic)map.get(Integer.valueOf(i))).showItem(pd, ShopItemBasic.Where.Quick));
/*    */       } else {
/* 28 */         result.add(new InventoryUI.EmptyClickableItem((new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte)14))
/* 29 */               .setName("§c空闲槽位!").setLore(new String[] { "§7这是一个快速购买槽位! §bShift加左键点击", "§7任意商店物品添加到这里." }).toItemStack()));
/*    */       } 
/*    */     } 
/* 32 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public void open() {
/* 37 */     BedWars.getInstance().getPlayerManager().load(this.p);
/* 38 */     super.open();
/*    */   }
/*    */ }
