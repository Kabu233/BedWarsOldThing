/*    */ package cn.rmc.bedwars.inventory.game.other;
/*    */ 
/*    */ import cn.rmc.bedwars.BedWars;
/*    */ import cn.rmc.bedwars.enums.game.TeamState;
/*    */ import cn.rmc.bedwars.game.PlayerData;
/*    */ import cn.rmc.bedwars.game.Team;
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
/*    */ public class SelectorTeamMenu
/*    */   extends MenuBasic {
/*    */   public SelectorTeamMenu(Player p, String msg) {
/* 20 */     super(p, "选择一个设置:", Integer.valueOf(4));
/* 21 */     this.msg = msg;
/*    */   }
/*    */   String msg;
/*    */   
/*    */   public void Setup() {
/* 26 */     this.inventoryUI.setItem(31, (InventoryUI.ClickableItem)new InventoryUI.AbstractClickableItem((new ItemBuilder(Material.ARROW))
/* 27 */           .setName("§a返回").setLore(new String[] { "§7至快捷交流" }).toItemStack())
/*    */         {
/*    */           public void onClick(InventoryClickEvent e) {
/* 30 */             (new QuickChatMenu(SelectorTeamMenu.this.p)).open();
/*    */           }
/*    */         });
/*    */     
/* 34 */     int i = 10;
/* 35 */     for (Team team : this.pd.getGame().getTeams()) {
/* 36 */       if (this.pd.getTeam().equals(team) || 
/* 37 */         team.getState() == TeamState.DEAD)
/* 38 */         continue;  final String name = "§a" + this.msg + team.getTeamType().getChatColor() + "§l" + team.getTeamType().getDisplayname();
/* 39 */       ItemBuilder item = new ItemBuilder(Material.WOOL);
/* 40 */       item.setDyeColor(team.getTeamType().getDyeColor());
/* 41 */       item.setName(name);
/* 42 */       item.setLore(new String[] { "§7点击发送信息: \"" + name + "\"", "§7给对友!", "", "§e点击以发送!" });
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 48 */       this.inventoryUI.setItem(i, (InventoryUI.ClickableItem)new InventoryUI.AbstractClickableItem(item.toItemStack())
/*    */           {
/*    */             public void onClick(InventoryClickEvent e) {
/* 51 */               PlayerData pd = BedWars.getInstance().getPlayerManager().get((Player)e.getWhoClicked());
/* 52 */               for (PlayerData data : pd.getTeam().getPlayers()) {
/* 53 */                 data.getPlayer().sendMessage("§a§l团队> " + 
/* 54 */                     LuckPermsUtil.getPrefix(pd.getPlayer()) + pd.getPlayer().getName() + "§f: " + name);
/*    */               }
/* 56 */               pd.getPlayer().closeInventory();
/*    */             }
/*    */           });
/* 59 */       i++;
/*    */     } 
/*    */   }
/*    */ }