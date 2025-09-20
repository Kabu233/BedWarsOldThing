/*    */ package cn.rmc.bedwars.inventory.game;
/*    */ 
/*    */ import cn.rmc.bedwars.inventory.MenuBasic;
/*    */ import cn.rmc.bedwars.utils.inventory.InventoryUI;
/*    */ import cn.rmc.bedwars.utils.item.ItemBuilder;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.inventory.InventoryClickEvent;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class CompassMenu extends MenuBasic {
/*    */   public CompassMenu(Player p) {
/* 13 */     super(p, "追踪器&快捷交流", Integer.valueOf(3));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void Setup() {
/* 21 */     ItemBuilder quick = (new ItemBuilder(Material.EMERALD)).setName("§a快捷交流").addLoreLine("§7发送高亮显示的聊天消息给你的对友!").addLoreLine("").addLoreLine("§e点击打开!");
/* 22 */     this.inventoryUI.setItem(11, (InventoryUI.ClickableItem)new InventoryUI.AbstractClickableItem(quick.toItemStack())
/*    */         {
/*    */           public void onClick(InventoryClickEvent e) {
/* 25 */             (new QuickChatMenu(CompassMenu.this.p)).open();
/*    */           }
/*    */         });
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 33 */     ItemBuilder track = (new ItemBuilder(Material.COMPASS)).setName("§a追踪器商店").addLoreLine("§7为你的指南针购买追踪升级, 升级后之后").addLoreLine("§7你可以追踪特定队伍的某位玩家, 死亡后失效").addLoreLine("").addLoreLine("§e点击打开!");
/* 34 */     this.inventoryUI.setItem(15, (InventoryUI.ClickableItem)new InventoryUI.AbstractClickableItem(track.toItemStack())
/*    */         {
/*    */           public void onClick(InventoryClickEvent e) {
/* 37 */             (new TrackerShop(CompassMenu.this.p, new CompassMenu(CompassMenu.this.p))).open();
/*    */           }
/*    */         });
/*    */   }
/*    */ }
