/*     */ package cn.rmc.bedwars.manager;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import cn.rmc.bedwars.BedWars;
/*     */ import cn.rmc.bedwars.enums.game.GameState;
/*     */ import cn.rmc.bedwars.enums.game.PlayerState;
/*     */ import cn.rmc.bedwars.enums.game.TeamState;
/*     */ import cn.rmc.bedwars.enums.game.TeamType;
/*     */ import cn.rmc.bedwars.game.Game;
/*     */ import cn.rmc.bedwars.game.PlayerData;
/*     */ import cn.rmc.bedwars.game.Team;
/*     */ import cn.rmc.bedwars.runnable.ScoreBoardRunnable;
/*     */ import cn.rmc.bedwars.utils.TimeUtils;
/*     */ import cn.rmc.bedwars.utils.player.TitleUtils;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ public class ScoreBoardManager {
/*     */   public ScoreBoardManager() {
/*  23 */     Bukkit.getScheduler().runTaskTimer((Plugin)BedWars.getInstance(), this::updateAll, 5L, 5L);
/*     */   }
/*     */   public void updateGame(Game g) {
/*  26 */     g.getPlayers().forEach(this::updatePlayer);
/*     */   }
/*     */   public void updateAll() {
/*  29 */     Bukkit.getOnlinePlayers().forEach(player -> updatePlayer(BedWars.getInstance().getPlayerManager().get(player)));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAdd(Game game) {
/*  35 */     StringBuilder sb = new StringBuilder();
/*  36 */     if (game.isPrivateGame()) sb.append("P"); 
/*  37 */     if (game.getGameMode().getTotalGM() == GameMode.TotalGM.DREAMS) sb.append("D"); 
/*  38 */     if (sb.length() == 0) return "    "; 
/*  39 */     return " [" + sb + "]    "; } public void updatePlayer(PlayerData pd) {
/*     */     Game g;
/*     */     ArrayList<String> asArray;
/*  42 */     SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd");
/*  43 */     String format = simpleDateFormat.format(new Date());
/*  44 */     String[] result = null;
/*     */     
/*  46 */     String server = "Mc91.top";
/*     */     
/*  48 */     if (pd.getState() == PlayerState.FIGHTING || pd.getState() == PlayerState.RESPAWNING || (pd
/*  49 */       .getState() == PlayerState.SPECING && pd.getTeam().getTeamType() != TeamType.NON)) {
/*  50 */       TitleUtils.sendTabTitle(pd.getPlayer(), "\n§b你正在游玩 §e91MC NetWork §b服务器\n", "\n  §b击杀数: §e" + pd
/*     */           
/*  52 */           .getKills() + " §b最终击杀数: §e" + pd.getFinalkills() + " §b破坏床数: §e" + pd.getBedbreak() + "  \n\n  §7官方Q群: §e§l916624383  \n    §7加入获取最新资讯, 或参与玩家交流!\n");
/*     */     } else {
/*     */       
/*  55 */       TitleUtils.sendTabTitle(pd.getPlayer(), "\n§b你正在游玩 §e91MC NetWork §b服务器\n", "\n  §7官方Q群: §e§l916624383  \n    §7加入获取最新资讯, 或参与玩家交流!\n");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  60 */     switch (pd.getState()) {
/*     */       case SPAWNING:
/*     */         return;
/*     */       case WAITING:
/*  64 */         g = pd.getGame();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  74 */         result = new String[] { "§7" + format + "  §8" + getAdd(g), "§r", "地图: §a" + g.getMap().getMapname(), "玩家: §a" + g.getOnlinePlayers().size() + "/" + g.getMaxplayer(), "§r§r", (pd.getGame().getState() == GameState.WAITING) ? "等待中..." : ("即将开始: §a" + g.getCount() + "秒"), "", "模式: §a" + pd.getGame().getGameMode().getDisplayname(), "版本: " + g.getGameMode().getTotalGM().getDpname() + "§7v1.0", "", "§e" + server };
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case FIGHTING:
/*     */       case RESPAWNING:
/*     */       case SPECING:
/*  82 */         g = (pd.getGame() == null) ? pd.getSpecGame() : pd.getGame();
/*  83 */         asArray = new ArrayList<>(Arrays.asList(new String[] { "§7" + format + " §7" + 
/*  84 */                 getAdd(g), "", g
/*     */                 
/*  86 */                 .getEventDisPlayName() + " - §a" + TimeUtils.formatIntoMMSS(g.getEventLeftTime()), "" }));
/*     */         
/*  88 */         for (Team team : g.getTeams()) {
/*  89 */           TeamType tt = team.getTeamType();
/*  90 */           StringBuilder sb = new StringBuilder();
/*  91 */           sb.append(tt.getEzname())
/*  92 */             .append(" §f")
/*  93 */             .append(tt.getDisplayname())
/*  94 */             .append(": ");
/*  95 */           switch (team.getState()) {
/*     */             case SPAWNING:
/*  97 */               sb.append("§a✔");
/*     */               break;
/*     */             case WAITING:
/* 100 */               sb.append("§a").append(team.getAlivePlayers().size());
/*     */               break;
/*     */             case FIGHTING:
/* 103 */               sb.append("§c✘");
/*     */               break;
/*     */           } 
/* 106 */           if (pd.getTeam().equals(team)) {
/* 107 */             sb.append(" §7你");
/*     */           }
/* 109 */           asArray.add(sb.toString());
/*     */         } 
/* 111 */         if ((pd.getGame() != null && !pd.getGame().getGameMode().getIsSpecial().booleanValue()) || (pd.getSpecGame() != null && !pd.getSpecGame().getGameMode().getIsSpecial().booleanValue())) {
/* 112 */           asArray.add("");
/* 113 */           asArray.add("击杀数: §a" + pd.getKills());
/* 114 */           asArray.add("最终击杀数: §a" + pd.getFinalkills());
/* 115 */           asArray.add("破坏床数: §a" + pd.getBedbreak());
/*     */         } 
/* 117 */         asArray.add("");
/* 118 */         asArray.add("§e" + server);
/* 119 */         result = asArray.<String>toArray(new String[0]);
/*     */         break;
/*     */     } 
/* 122 */     Bukkit.getScheduler().runTask((Plugin)BedWars.getInstance(), (BukkitRunnable)new ScoreBoardRunnable(pd, "§e§l起床战争", result));
/*     */   }
/*     */ }