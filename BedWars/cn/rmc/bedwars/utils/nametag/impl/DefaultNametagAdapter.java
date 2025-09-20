/*    */ package cn.rmc.bedwars.utils.nametag.impl;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import cn.rmc.bedwars.BedWars;
/*    */ import cn.rmc.bedwars.enums.game.GameState;
/*    */ import cn.rmc.bedwars.enums.game.PlayerState;
/*    */ import cn.rmc.bedwars.enums.game.TeamType;
/*    */ import cn.rmc.bedwars.game.Game;
/*    */ import cn.rmc.bedwars.game.PlayerData;
/*    */ import cn.rmc.bedwars.utils.nametag.BufferedNametag;
/*    */ import cn.rmc.bedwars.utils.nametag.NametagAdapter;
/*    */ import cn.rmc.bedwars.utils.player.LuckPermsUtil;
/*    */ import net.luckperms.api.model.group.Group;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.scoreboard.NameTagVisibility;
/*    */ 
/*    */ public class DefaultNametagAdapter
/*    */   implements NametagAdapter
/*    */ {
/*    */   public List<BufferedNametag> getPlate(Player p) {
/* 22 */     List<BufferedNametag> result = new ArrayList<>();
/* 23 */     PlayerData pd = BedWars.getInstance().getPlayerManager().get(p);
/* 24 */     Game g = BedWars.getInstance().getGameManager().getGameArrayList().get(0);
/* 25 */     switch (g.getState())
/*    */     { case WAITING:
/*    */       case COUNTING:
/* 28 */         for (PlayerData pdd : g.getOnlinePlayers()) {
/* 29 */           Player online = pdd.getPlayer();
/* 30 */           Group group = LuckPermsUtil.getGroup(online);
/* 31 */           String s = LuckPermsUtil.getGrouppriority(group) + online.getName().substring(0, Math.min(5, online.getName().length())) + online.getName().hashCode();
/* 32 */           result.add(new BufferedNametag(s
/* 33 */                 .substring(0, Math.min(16, s.length())), 
/* 34 */                 LuckPermsUtil.getPrefixColor(online), "", false, online));
/*    */         }
/* 69 */         return result; }  for (PlayerData pdd : g.getOnlinePlayers()) { Player online = pdd.getPlayer(); TeamType tt = pdd.getTeam().getTeamType(); switch (pdd.getState()) { case WAITING: case COUNTING: case null: if (pd.getState() == PlayerState.SPECING || pd.getState() == PlayerState.RESPAWNING) { if (pdd.isSpectateMode()) { result.add(new BufferedNametag("AAASPEC", "§6§l观察者 ", "", false, NameTagVisibility.ALWAYS, online)); continue; }  result.add(new BufferedNametag("ASPEC", "§7", "", false, NameTagVisibility.ALWAYS, online)); }  continue; }  String s = pdd.getTeam().getTeamType().getEz() + pdd.getPlayer().getName(); result.add(new BufferedNametag(s.substring(0, Math.min(16, s.length())), tt.getChatColor() + tt.getEzname() + " ", "", false, pdd.isVisionable() ? NameTagVisibility.ALWAYS : NameTagVisibility.NEVER, online)); }  return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean showHealthBelowName(Player player) {
/* 74 */     PlayerData pd = BedWars.getInstance().getPlayerManager().get(player);
/* 75 */     Game g = BedWars.getInstance().getGameManager().getGameArrayList().get(0);
/* 76 */     if (g == null) return false; 
/* 77 */     switch (g.getState()) {
/*    */       case WAITING:
/*    */       case COUNTING:
/* 80 */         return false;
/*    */     } 
/* 82 */     return true;
/*    */   }
/*    */ }