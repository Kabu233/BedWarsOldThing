/*    */ package cn.rmc.bedwars.inventory.game.shops;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import cn.rmc.bedwars.enums.IShopItem;
/*    */ import cn.rmc.bedwars.enums.shop.Block;
/*    */ import cn.rmc.bedwars.game.PlayerData;
/*    */ import cn.rmc.bedwars.game.shop.ShopItemBasic;
/*    */ import cn.rmc.bedwars.game.shop.items.CommonShopItem;
/*    */ import cn.rmc.bedwars.inventory.game.ShopBasic;
/*    */ import cn.rmc.bedwars.utils.inventory.InventoryUI;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class BlockShop extends ShopBasic {
/*    */   public BlockShop(Player p) {
/* 15 */     super(p, "方块", Integer.valueOf(1));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected ArrayList<InventoryUI.ClickableItem> product(PlayerData pd) {
/* 21 */     ArrayList<InventoryUI.ClickableItem> result = new ArrayList<>();
/* 22 */     for (Block block : Block.values()) {
/* 23 */       if (!pd.getGame().getGameMode().name().contains("RUSH") || block != Block.OBSIDIAN)
/* 24 */         result.add((new CommonShopItem(pd, (IShopItem)block)).showItem(pd, ShopItemBasic.Where.Normal)); 
/*    */     } 
/* 26 */     return result;
/*    */   }
/*    */ }