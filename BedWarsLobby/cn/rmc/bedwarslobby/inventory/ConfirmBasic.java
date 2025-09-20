/*    */ package cn.rmc.bedwarslobby.inventory;
/*    */ 
/*    */ import cn.rmc.bedwarslobby.BedWarsLobby;
/*    */ import cn.rmc.bedwarslobby.util.ItemBuilder;
/*    */ import cn.rmc.bedwarslobby.util.MathUtils;
/*    */ import cn.rmc.bedwarslobby.util.inventory.InventoryUI;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.Sound;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.inventory.InventoryClickEvent;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public abstract class ConfirmBasic extends MenuBasic {
/*    */   Integer price;
/*    */   String product;
/*    */   
/*    */   public abstract void Confirm(Integer paramInteger);
/*    */   
/*    */   public abstract void unConfirm();
/*    */   
/*    */   public ConfirmBasic(Player p, Integer price, String product) {
/* 22 */     super(p, "你确定吗?", Integer.valueOf(3));
/* 23 */     this.price = price;
/* 24 */     this.product = product;
/* 25 */     Setup();
/* 26 */     p.playSound(p.getLocation(), Sound.NOTE_PLING, 1.0F, 1.0F);
/* 27 */     p.openInventory(this.inventoryUI.getCurrentPage());
/*    */   }
/*    */ 
/*    */   
/*    */   public void Setup() {
/* 32 */     this.inventoryUI.setItem(11, (InventoryUI.ClickableItem)new InventoryUI.AbstractClickableItem((new ItemBuilder(Material.STAINED_CLAY, 1, (byte)13)).setName("§a确认").setLore(new String[] { "§7以" + MathUtils.Format(this.price) + "硬币的价格购买" + this.product + "." }).toItemStack())
/*    */         {
/*    */           public void onClick(InventoryClickEvent e) {
/* 35 */             ConfirmBasic.this.player.playSound(ConfirmBasic.this.player.getLocation(), Sound.NOTE_STICKS, 1.0F, 1.0F);
/* 36 */             ConfirmBasic.this.Confirm(ConfirmBasic.this.price);
/* 37 */             ConfirmBasic.this.player.sendMessage("§6恭喜你购买了§a" + ConfirmBasic.this.product + "§6!");
/* 38 */             BedWarsLobby.getInstance().getScoreBoardManager().sendScoreBoard(ConfirmBasic.this.player);
/*    */           }
/*    */         });
/* 41 */     this.inventoryUI.setItem(15, (InventoryUI.ClickableItem)new InventoryUI.AbstractClickableItem((new ItemBuilder(Material.STAINED_CLAY, 1, (byte)14)).setName("§c取消").setLore(new String[] { "§7返回上一级菜单." }).toItemStack())
/*    */         {
/*    */           public void onClick(InventoryClickEvent e) {
/* 44 */             ConfirmBasic.this.unConfirm();
/*    */           }
/*    */         });
/*    */   }
/*    */ }