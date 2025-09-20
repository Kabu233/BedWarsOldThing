/*    */ package cn.rmc.bedwars.game.dream.ultimate;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import cn.rmc.bedwars.game.PlayerData;
/*    */ import cn.rmc.bedwars.inventory.game.ShopBasic;
/*    */ import cn.rmc.bedwars.utils.inventory.InventoryUI;
/*    */ import org.bukkit.Sound;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.inventory.InventoryClickEvent;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class UltimateShop extends ShopBasic {
/*    */   public UltimateShop(Player p) {
/* 14 */     super(p, "超能力", Integer.valueOf(8));
/*    */   }
/*    */ 
/*    */   
/*    */   protected ArrayList<InventoryUI.ClickableItem> product(final PlayerData pd) {
/* 19 */     ArrayList<InventoryUI.ClickableItem> result = new ArrayList<>();
/* 20 */     final UltimateManager dm = (UltimateManager)pd.getGame().getDreamManager();
/* 21 */     for (UltimateType type : UltimateType.values()) {
/* 22 */       if (dm.getUltimate(pd) != type) {
/* 23 */         result.add(new InventoryUI.AbstractClickableItem(type.showItem(pd))
/*    */             {
/*    */               public void onClick(InventoryClickEvent e) {
/* 26 */                 dm.setUltimate(pd, type, 2);
/* 27 */                 UltimateShop.this.p.playSound(UltimateShop.this.p.getLocation(), Sound.NOTE_PLING, Float.parseFloat("1.0"), Float.parseFloat("2.0"));
/* 28 */                 UltimateShop.this.Setup();
/*    */               }
/*    */             });
/*    */       } else {
/* 32 */         result.add(new InventoryUI.EmptyClickableItem(type.showItem(pd)));
/*    */       } 
/*    */     } 
/*    */     
/* 36 */     return result;
/*    */   }
/*    */ }