/*    */ package cn.rmc.bedwars.inventory.game;
/*    */ 
/*    */ import cn.rmc.bedwars.enums.game.Resource;
/*    */ import cn.rmc.bedwars.enums.game.TeamState;
/*    */ import cn.rmc.bedwars.game.PlayerData;
/*    */ import cn.rmc.bedwars.game.Team;
/*    */ import cn.rmc.bedwars.game.shop.Price;
/*    */ import cn.rmc.bedwars.inventory.MenuBasic;
/*    */ import cn.rmc.bedwars.utils.inventory.InventoryUI;
/*    */ import cn.rmc.bedwars.utils.item.ItemBuilder;
/*    */ import cn.rmc.bedwars.utils.player.InventoryUtils;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.Sound;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.inventory.InventoryClickEvent;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class TrackerShop extends MenuBasic {
/*    */   public TrackerShop(Player p, MenuBasic backMenu) {
/* 20 */     super(p, "购买敌人追踪器", Integer.valueOf(4));
/* 21 */     this.backMenu = backMenu;
/*    */   }
/*    */ 
/*    */   
/*    */   public void Setup() {
/*    */     String str;
/* 27 */     if (this.backMenu.equals(new CompassMenu(this.p))) {
/* 28 */       str = "追踪器&快捷交流";
/*    */     } else {
/* 30 */       str = "快速购买";
/* 31 */     }  this.inventoryUI.setItem(31, (InventoryUI.ClickableItem)new InventoryUI.AbstractClickableItem((new ItemBuilder(Material.ARROW))
/* 32 */           .setName("§a返回").setLore(new String[] { "§7至" + str }).toItemStack())
/*    */         {
/*    */           public void onClick(InventoryClickEvent e) {
/* 35 */             TrackerShop.this.backMenu.open();
/*    */           }
/*    */         });
/* 38 */     boolean couldBuy = true;
/* 39 */     for (Team team : this.pd.getGame().getTeams()) {
/* 40 */       if (!this.pd.getTeam().equals(team) && 
/* 41 */         team.getState() == TeamState.ALIVE) {
/* 42 */         couldBuy = false;
/*    */         break;
/*    */       } 
/*    */     } 
/* 46 */     int i = 10;
/* 47 */     final Price price = new Price(Resource.EMERALD, Integer.valueOf(2));
/* 48 */     final boolean isenough = InventoryUtils.isEnoughItem(this.p, price).booleanValue();
/* 49 */     for (Team team : this.pd.getGame().getTeams()) {
/* 50 */       if (this.pd.getTeam().equals(team) || 
/* 51 */         team.getState() == TeamState.DEAD)
/*    */         continue; 
/* 53 */       ItemBuilder item = new ItemBuilder(Material.WOOL);
/* 54 */       item.setDyeColor(team.getTeamType().getDyeColor());
/* 55 */       item.setName((isenough ? "§a" : "§c") + "追踪" + team.getTeamType().getDisplayname());
/* 56 */       item.addLoreLine("§7为你的指南针购买追踪升级,");
/* 57 */       item.addLoreLine("§7其可以为你追踪特定队伍的");
/* 58 */       item.addLoreLine("§7离你最近的玩家, 直到你死亡.");
/* 59 */       item.addLoreLine("").addLoreLine(price.getDisplay()).addLoreLine("");
/* 60 */       item.addLoreLine((this.pd.getTargetTeam() != null && this.pd.getTargetTeam().equals(team)) ? "§a你已经追踪了这个队伍!" : (couldBuy ? (isenough ? "§a点击解锁!" : "§c你没有足够的绿宝石!") : "§c破坏所有敌方的床解锁!"));
/*    */       
/* 62 */       final boolean finalCouldBuy = couldBuy;
/* 63 */       this.inventoryUI.setItem(i, (InventoryUI.ClickableItem)new InventoryUI.AbstractClickableItem(item.toItemStack())
/*    */           {
/*    */             public void onClick(InventoryClickEvent e) {
/* 66 */               if (!finalCouldBuy)
/* 67 */                 return;  if (isenough) {
/* 68 */                 if (TrackerShop.this.pd.getTargetTeam() != null && TrackerShop.this.pd.getTargetTeam().equals(team)) {
/* 69 */                   TrackerShop.this.p.closeInventory();
/* 70 */                   TrackerShop.this.p.sendMessage("§c你已经追踪了这个队伍!");
/*    */                 } else {
/* 72 */                   TrackerShop.this.pd.setTargetTeam(team);
/* 73 */                   InventoryUtils.deleteitem(TrackerShop.this.p, price);
/* 74 */                   TrackerShop.this.pd.getPlayer().playSound(TrackerShop.this.pd.getPlayer().getLocation(), Sound.NOTE_PLING, 1.0F, 2.0F);
/* 75 */                   TrackerShop.this.p.sendMessage("§a你购买了§6" + team.getTeamType().getDisplayname() + "追踪器");
/* 76 */                   TrackerShop.this.p.sendMessage("§c你将在死后失去追踪该队的能力!");
/* 77 */                   TrackerShop.this.p.closeInventory();
/*    */                 } 
/*    */               }
/*    */             }
/*    */           });
/* 82 */       i++;
/*    */     } 
/*    */   }
/*    */   
/*    */   MenuBasic backMenu;
/*    */ }
