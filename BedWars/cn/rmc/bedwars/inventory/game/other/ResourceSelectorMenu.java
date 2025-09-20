/*    */ package cn.rmc.bedwars.inventory.game.other;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import cn.rmc.bedwars.BedWars;
/*    */ import cn.rmc.bedwars.enums.game.Resource;
/*    */ import cn.rmc.bedwars.game.PlayerData;
/*    */ import cn.rmc.bedwars.inventory.MenuBasic;
/*    */ import cn.rmc.bedwars.inventory.game.QuickChatMenu;
/*    */ import cn.rmc.bedwars.utils.inventory.InventoryUI;
/*    */ import cn.rmc.bedwars.utils.item.ItemBuilder;
/*    */ import cn.rmc.bedwars.utils.player.LuckPermsUtil;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.inventory.InventoryClickEvent;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class ResourceSelectorMenu extends MenuBasic {
/*    */   String msg;
/*    */   
/*    */   public ResourceSelectorMenu(Player p, String msg) {
/* 23 */     super(p, "选择一个设置:", Integer.valueOf(4));
/* 24 */     this.msg = msg;
/*    */   }
/*    */ 
/*    */   
/*    */   public void Setup() {
/* 29 */     this.inventoryUI.setItem(31, (InventoryUI.ClickableItem)new InventoryUI.AbstractClickableItem((new ItemBuilder(Material.ARROW))
/* 30 */           .setName("§a返回").setLore(new String[] { "§7至快捷交流" }).toItemStack())
/*    */         {
/*    */           public void onClick(InventoryClickEvent e) {
/* 33 */             (new QuickChatMenu(ResourceSelectorMenu.this.p)).open();
/*    */           }
/*    */         });
/* 36 */     List<Resource> resources = new ArrayList<>();
/* 37 */     Collections.addAll(resources, Resource.values());
/* 38 */     int i = 10;
/* 39 */     for (Resource resource : resources) {
/* 40 */       final String name = "§a" + this.msg + resource.getColor() + "§l" + resource.getDisplayName();
/* 41 */       ItemBuilder item = new ItemBuilder(resource.getItem());
/* 42 */       item.setName(name);
/* 43 */       item.setLore(new String[] { "§7点击发送信息: \"" + name + "\"", "§7给对友!", "", "§e点击以发送!" });
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 49 */       this.inventoryUI.setItem(i, (InventoryUI.ClickableItem)new InventoryUI.AbstractClickableItem(item.toItemStack())
/*    */           {
/*    */             public void onClick(InventoryClickEvent e) {
/* 52 */               PlayerData pd = BedWars.getInstance().getPlayerManager().get((Player)e.getWhoClicked());
/* 53 */               for (PlayerData data : pd.getTeam().getPlayers()) {
/* 54 */                 data.getPlayer().sendMessage("§a§l团队> " + 
/* 55 */                     LuckPermsUtil.getPrefix(pd.getPlayer()) + pd.getPlayer().getName() + "§f: " + name);
/*    */               }
/* 57 */               pd.getPlayer().closeInventory();
/*    */             }
/*    */           });
/* 60 */       i += 2;
/*    */     } 
/*    */   }
/*    */ }