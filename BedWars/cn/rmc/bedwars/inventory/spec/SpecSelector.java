/*    */ package cn.rmc.bedwars.inventory.spec;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import cn.rmc.bedwars.BedWars;
/*    */ import cn.rmc.bedwars.enums.game.PlayerState;
/*    */ import cn.rmc.bedwars.game.Game;
/*    */ import cn.rmc.bedwars.game.PlayerData;
/*    */ import cn.rmc.bedwars.game.Team;
/*    */ import cn.rmc.bedwars.inventory.MenuBasic;
/*    */ import cn.rmc.bedwars.utils.inventory.InventoryUI;
/*    */ import cn.rmc.bedwars.utils.item.ItemBuilder;
/*    */ import cn.rmc.bedwars.utils.player.ActionBarUtils;
/*    */ import cn.rmc.bedwars.utils.player.LuckPermsUtil;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.inventory.ClickType;
/*    */ import org.bukkit.event.inventory.InventoryClickEvent;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class SpecSelector
/*    */   extends MenuBasic
/*    */ {
/*    */   PlayerData pd;
/*    */   
/*    */   public SpecSelector(Player p) {
/* 28 */     super(p, "旁观者选择", Integer.valueOf(2));
/* 29 */     this.pd = BedWars.getInstance().getPlayerManager().get(p);
/*    */   }
/*    */   
/*    */   public void Setup() {
/* 33 */     Game g = this.pd.getSpecGame();
/* 34 */     ArrayList<String> str = new ArrayList<>();
/* 35 */     g.getOnlinePlayers().forEach(playerData -> {
/*    */           if (playerData.getState() == PlayerState.FIGHTING) {
/*    */             str.add(playerData.getPlayer().getName());
/*    */           }
/*    */         });
/* 40 */     Collections.sort(str);
/* 41 */     ArrayList<Player> players = new ArrayList<>();
/* 42 */     str.forEach(player -> players.add(Bukkit.getPlayer(player)));
/* 43 */     for (int i = 0; i <= 17; i++) {
/* 44 */       if (i + 1 <= players.size()) {
/* 45 */         final Player p = players.get(i);
/* 46 */         ItemBuilder ib = new ItemBuilder(Material.SKULL_ITEM, 1, (byte)3);
/*    */         try {
/* 48 */           ib.setSkullOwner(p.getName());
/* 49 */         } catch (Exception exception) {}
/* 50 */         ib.setName(LuckPermsUtil.getPrefixColor(p) + p.getName());
/* 51 */         ib.addLoreLine("§7血量: §f" + ((int)p.getHealth() * 5) + "%");
/* 52 */         Team team = BedWars.getInstance().getPlayerManager().get(p).getTeam();
/* 53 */         ib.addLoreLine("§7队伍: " + team.getTeamType().getChatColor() + team.getTeamType().getDisplayname());
/*    */         
/* 55 */         ib.addLoreLine("");
/* 56 */         ib.addLoreLine("§7左键点击旁观!");
/* 57 */         ib.addLoreLine("§7右键点击举报!");
/* 58 */         this.inventoryUI.setItem(i, (InventoryUI.ClickableItem)new InventoryUI.AbstractClickableItem(ib.toItemStack())
/*    */             {
/*    */               public void onClick(InventoryClickEvent e) {
/* 61 */                 if (e.getClick() == ClickType.LEFT) {
/* 62 */                   if (BedWars.getInstance().getPlayerManager().get(p).getState() == PlayerState.FIGHTING) {
/* 63 */                     if (SpecSelector.this.pd.getSpecOther().isFirstPersonTargeting())
/* 64 */                       SpecSelector.this.pd.getSpecOther().setFirstPersonTargeting(false); 
/* 65 */                     SpecSelector.this.pd.getSpecOther().setTarget(p, true);
/*    */                   } else {
/* 67 */                     SpecSelector.this.pd.getPlayer().closeInventory();
/* 68 */                     ActionBarUtils.sendActionBar(SpecSelector.this.pd.getPlayer(), "§c目标丢失!");
/*    */                   } 
/* 70 */                 } else if (e.getClick() == ClickType.RIGHT) {
/* 71 */                   e.getWhoClicked().sendMessage("§f可以输入§b/report§f指令查看更多!");
/*    */                 } 
/*    */               }
/*    */             });
/*    */       } else {
/* 76 */         this.inventoryUI.setItem(i, (InventoryUI.ClickableItem)new InventoryUI.EmptyClickableItem(new ItemStack(Material.AIR)));
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }