/*    */ package cn.rmc.bedwars.inventory.game;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import cn.rmc.bedwars.enums.game.QuickChat;
/*    */ import cn.rmc.bedwars.inventory.MenuBasic;
/*    */ import cn.rmc.bedwars.utils.inventory.InventoryUI;
/*    */ import cn.rmc.bedwars.utils.item.ItemBuilder;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.inventory.InventoryClickEvent;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class QuickChatMenu
/*    */   extends MenuBasic {
/*    */   public QuickChatMenu(Player p) {
/* 18 */     super(p, "快捷交流", Integer.valueOf(5));
/*    */   }
/*    */ 
/*    */   
/*    */   public void Setup() {
/* 23 */     this.inventoryUI.setItem(40, (InventoryUI.ClickableItem)new InventoryUI.AbstractClickableItem((new ItemBuilder(Material.ARROW))
/* 24 */           .setName("§a返回").setLore(new String[] { "§7至追踪器&快捷交流" }).toItemStack())
/*    */         {
/*    */           public void onClick(InventoryClickEvent e) {
/* 27 */             (new CompassMenu(QuickChatMenu.this.p)).open();
/*    */           }
/*    */         });
/*    */     
/* 31 */     List<QuickChat> list = new ArrayList<>();
/* 32 */     Collections.addAll(list, QuickChat.values()); int i;
/* 33 */     for (i = 10; i < 16; i++) {
/* 34 */       this.inventoryUI.setItem(i, ((QuickChat)list.get(0)).showItem(this.p));
/* 35 */       list.remove(0);
/*    */     } 
/* 37 */     for (i = 20; i < 26; i++) {
/* 38 */       this.inventoryUI.setItem(i, ((QuickChat)list.get(0)).showItem(this.p));
/* 39 */       list.remove(0);
/*    */     } 
/*    */   }
/*    */ }
