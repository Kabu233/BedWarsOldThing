/*    */ package cn.rmc.bedwars.inventory.lobby;
/*    */ 
/*    */ import cn.rmc.bedwars.enums.game.GameState;
/*    */ import cn.rmc.bedwars.enums.game.TeamType;
/*    */ import cn.rmc.bedwars.game.Game;
/*    */ import cn.rmc.bedwars.game.PlayerData;
/*    */ import cn.rmc.bedwars.game.Team;
/*    */ import cn.rmc.bedwars.inventory.MenuBasic;
/*    */ import cn.rmc.bedwars.utils.inventory.InventoryUI;
/*    */ import cn.rmc.bedwars.utils.item.ItemBuilder;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.Sound;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.inventory.InventoryClickEvent;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class TeamSelector extends MenuBasic {
/*    */   public TeamSelector(Player p) {
/* 19 */     super(p, "队伍选择器", Integer.valueOf(1), "TS");
/* 20 */     this.pd.setTeamselector(this);
/*    */   }
/*    */   
/*    */   public void Setup() {
/* 24 */     final Game g = this.pd.getGame();
/* 25 */     int i = 0;
/* 26 */     for (Team team : g.getTeams()) {
/* 27 */       ItemBuilder ib = new ItemBuilder(Material.STAINED_GLASS_PANE, team.getPlayers().size());
/* 28 */       ib.setDyeColor(team.getTeamType().getDyeColor());
/* 29 */       ib.setName(team.getTeamType().getwithcolor() + " §7(§a" + team.getPlayers().size() + "§f/§b" + g.getMap().getEachmaxplayer() + "§7)");
/* 30 */       ib.addLoreLine("§7点击加入");
/* 31 */       ib.addLoreLine(team.getTeamType().getwithcolor() + "§7.");
/* 32 */       ib.addLoreLine("");
/* 33 */       if (team.getPlayers().size() != 0) {
/* 34 */         ib.addLoreLine("§7当前该队玩家:");
/* 35 */         for (PlayerData player : team.getPlayers()) {
/* 36 */           ib.addLoreLine(team.getTeamType().getChatColor() + player.getPlayer().getName());
/*    */         }
/*    */       } else {
/* 39 */         ib.addLoreLine("§7当前该队暂无玩家.");
/*    */       } 
/* 41 */       ib.addLoreLine("");
/* 42 */       if (this.pd.getTeam().getTeamType().equals(team.getTeamType())) {
/* 43 */         ib.addLoreLine("§a已选择!");
/*    */       } else {
/* 45 */         ib.addLoreLine("§e点击加入!");
/*    */       } 
/* 47 */       this.inventoryUI.setItem(i, (InventoryUI.ClickableItem)new InventoryUI.AbstractClickableItem(ib.toItemStack())
/*    */           {
/*    */             public void onClick(InventoryClickEvent e) {
/* 50 */               int each = (g.getOnlinePlayers().size() / 4 == 0) ? 1 : (g.getOnlinePlayers().size() / 4 + 1);
/* 51 */               if (TeamSelector.this.pd.getGame().getState() == GameState.FIGHTING) {
/* 52 */                 TeamSelector.this.pd.getPlayer().sendMessage("§c游戏已经开始了.");
/*    */                 return;
/*    */               } 
/* 55 */               if (TeamSelector.this.pd.getTeam().getTeamType().equals(TeamType.NON)) {
/* 56 */                 if (team.getPlayers().size() >= each && !g.isPrivateGame()) {
/* 57 */                   TeamSelector.this.pd.getPlayer().sendMessage("§c为了队伍人数平均, 你不能加入此队伍!");
/* 58 */                   TeamSelector.this.pd.getPlayer().playSound(TeamSelector.this.pd.getPlayer().getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 0.5F);
/*    */                   return;
/*    */                 } 
/* 61 */                 if (team.getPlayers().size() < g.getMap().getEachmaxplayer()) {
/* 62 */                   team.addPlayer(TeamSelector.this.pd);
/* 63 */                   TeamSelector.this.pd.getPlayer().closeInventory();
/* 64 */                   TeamSelector.this.pd.getPlayer().playSound(TeamSelector.this.pd.getPlayer().getLocation(), Sound.NOTE_PLING, 1.0F, 2.0F);
/* 65 */                   TeamSelector.this.pd.getPlayer().sendMessage("§e你加入了" + team.getTeamType().getwithcolor() + "§e!");
/*    */                 } else {
/* 67 */                   TeamSelector.this.pd.getPlayer().sendMessage("§c你选择的队伍已满人!");
/* 68 */                   TeamSelector.this.pd.getPlayer().playSound(TeamSelector.this.pd.getPlayer().getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 0.5F);
/*    */                 } 
/* 70 */               } else if (TeamSelector.this.pd.getTeam().getTeamType() != TeamType.NON && TeamSelector.this.pd.getTeam().getTeamType() != team.getTeamType()) {
/* 71 */                 if (team.getPlayers().size() >= each && !g.isPrivateGame()) {
/* 72 */                   TeamSelector.this.pd.getPlayer().sendMessage("§c为了队伍人数平均, 你不能加入此队伍!");
/* 73 */                   TeamSelector.this.pd.getPlayer().playSound(TeamSelector.this.pd.getPlayer().getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 0.5F);
/*    */                   return;
/*    */                 } 
/* 76 */                 if (team.getPlayers().size() < g.getMap().getEachmaxplayer()) {
/* 77 */                   TeamSelector.this.pd.getTeam().removePlayer(TeamSelector.this.pd);
/* 78 */                   team.addPlayer(TeamSelector.this.pd);
/* 79 */                   TeamSelector.this.pd.getPlayer().playSound(TeamSelector.this.pd.getPlayer().getLocation(), Sound.NOTE_PLING, 1.0F, 2.0F);
/* 80 */                   TeamSelector.this.pd.getPlayer().sendMessage("§e你加入了" + team.getTeamType().getwithcolor() + "§e!");
/* 81 */                   TeamSelector.this.pd.getPlayer().closeInventory();
/*    */                 } else {
/* 83 */                   TeamSelector.this.pd.getPlayer().sendMessage("§c你选择的队伍已满人!");
/* 84 */                   TeamSelector.this.pd.getPlayer().playSound(TeamSelector.this.pd.getPlayer().getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 0.5F);
/*    */                 } 
/*    */               } 
/* 87 */               for (PlayerData player : TeamSelector.this.pd.getGame().getOnlinePlayers()) {
/* 88 */                 if (player.getTeamselector() != null) {
/* 89 */                   player.getTeamselector().Setup();
/*    */                 }
/*    */               } 
/*    */             }
/*    */           });
/* 94 */       i++;
/*    */     } 
/*    */   }
/*    */ }