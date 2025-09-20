/*    */ package cn.rmc.bedwarslobby.inventory.bedwars;
/*    */ import cn.rmc.bedwarslobby.inventory.MenuBasic;
/*    */ import cn.rmc.bedwarslobby.util.ItemBuilder;
/*    */ import cn.rmc.bedwarslobby.util.inventory.InventoryUI;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.inventory.InventoryClickEvent;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class DreamPlayMenu extends MenuBasic {
/*    */   public DreamPlayMenu(Player p) {
/* 12 */     super(p, "游玩§6Dream模式", Integer.valueOf(3));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void Setup() {
/* 17 */     this.inventoryUI.setItem(11, (InventoryUI.ClickableItem)getItem(Material.NETHER_STAR, Integer.valueOf(4), "超能力起床 4v4v4v4", "FOUR_FOUR_ULTIMATE"));
/* 18 */     this.inventoryUI.setItem(13, (InventoryUI.ClickableItem)getItem(Material.FEATHER, Integer.valueOf(4), "疾速起床 4v4v4v4", "FOUR_FOUR_RUSH"));
/* 19 */     this.inventoryUI.setItem(15, (InventoryUI.ClickableItem)getItem(Material.DIAMOND_HOE, Integer.valueOf(4), "枪械起床 4v4v4v4", "FOUR_FOUR_ARMED"));
/*    */   }
/*    */   
/*    */   public InventoryUI.AbstractClickableItem getItem(Material material, Integer amount, String name, final String sjname) {
/* 23 */     ItemBuilder ib = new ItemBuilder(material, amount.intValue());
/* 24 */     ib.setName("§b" + name);
/* 25 */     ib.addLoreLine("§7游玩§6" + name);
/* 26 */     return new InventoryUI.AbstractClickableItem(ib.toItemStack())
/*    */       {
/*    */         public void onClick(InventoryClickEvent e) {
/* 29 */           DreamPlayMenu.this.player.performCommand("sj open " + sjname);
/*    */         }
/*    */       };
/*    */   }
/*    */ }