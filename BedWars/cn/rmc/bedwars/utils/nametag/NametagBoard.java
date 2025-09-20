/*     */ package cn.rmc.bedwars.utils.nametag;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import org.apache.commons.lang.StringEscapeUtils;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.scoreboard.DisplaySlot;
/*     */ import org.bukkit.scoreboard.Objective;
/*     */ import org.bukkit.scoreboard.Scoreboard;
/*     */ import org.bukkit.scoreboard.Team;
/*     */ 
/*     */ public class NametagBoard {
/*     */   private final UUID uuid;
/*     */   
/*     */   public UUID getUuid() {
/*  19 */     return this.uuid;
/*     */   } private NametagHandler handler; public NametagHandler getHandler() {
/*  21 */     return this.handler;
/*     */   }
/*  23 */   private Set<String> bufferedTeams = new HashSet<>(); public Set<String> getBufferedTeams() { return this.bufferedTeams; }
/*  24 */    private Map<String, List<String>> bufferedPlayers = new ConcurrentHashMap<>(); public Map<String, List<String>> getBufferedPlayers() { return this.bufferedPlayers; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NametagBoard(Player player, NametagHandler handler) {
/*  33 */     this.uuid = player.getUniqueId();
/*  34 */     this.handler = handler;
/*  35 */     setup(player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setup(Player player) {
/*  44 */     Scoreboard scoreboard = getScoreboard();
/*     */ 
/*     */     
/*  47 */     player.setScoreboard(scoreboard);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Scoreboard getScoreboard() {
/*  56 */     Player player = Bukkit.getPlayer(getUuid());
/*  57 */     if (getHandler().isHook() || player.getScoreboard() != Bukkit.getScoreboardManager().getMainScoreboard()) {
/*  58 */       return player.getScoreboard();
/*     */     }
/*  60 */     return Bukkit.getScoreboardManager().getNewScoreboard();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateHealthBelow(Player player, Scoreboard scoreboard) {
/*  71 */     if (this.handler.getAdapter().showHealthBelowName(player)) {
/*     */       
/*  73 */       Objective objective = scoreboard.getObjective(DisplaySlot.BELOW_NAME);
/*  74 */       if (objective == null) {
/*  75 */         objective = scoreboard.registerNewObjective("showhealth", "dummy");
/*  76 */         objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
/*  77 */         objective.setDisplayName(ChatColor.RED + StringEscapeUtils.unescapeJava("❤"));
/*     */       } 
/*  79 */       Objective objective2 = scoreboard.getObjective(DisplaySlot.PLAYER_LIST);
/*  80 */       if (objective2 == null) {
/*  81 */         objective2 = scoreboard.registerNewObjective("showhealthtab", "dummy");
/*  82 */         objective2.setDisplaySlot(DisplaySlot.PLAYER_LIST);
/*  83 */         objective2.setDisplayName(ChatColor.RED + StringEscapeUtils.unescapeJava("❤"));
/*     */       } 
/*     */ 
/*     */       
/*  87 */       for (Player loopPlayer : Bukkit.getOnlinePlayers()) {
/*  88 */         objective.getScore(loopPlayer.getName()).setScore((int)loopPlayer.getHealth());
/*  89 */         objective2.getScore(loopPlayer.getName()).setScore((int)loopPlayer.getHealth());
/*     */       }
/*     */     
/*     */     }
/*  93 */     else if (scoreboard.getObjective(DisplaySlot.BELOW_NAME) != null) {
/*  94 */       Objective objective = scoreboard.getObjective(DisplaySlot.BELOW_NAME);
/*  95 */       objective.unregister();
/*  96 */       Objective objective2 = scoreboard.getObjective(DisplaySlot.PLAYER_LIST);
/*  97 */       objective2.unregister();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Team getOrRegisterTeam(Scoreboard scoreboard, String name) {
/* 110 */     Team team = scoreboard.getTeam(name);
/*     */     
/* 112 */     if (team == null) {
/* 113 */       team = scoreboard.registerNewTeam(name);
/*     */     }
/*     */     
/* 116 */     return team;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateNametags(Player player, Scoreboard scoreboard) {
/* 126 */     List<BufferedNametag> nametags = this.handler.getAdapter().getPlate(player);
/*     */     
/* 128 */     if (nametags == null) {
/*     */       return;
/*     */     }
/*     */     
/* 132 */     Set<String> toReturn = new HashSet<>();
/* 133 */     Map<String, List<String>> strings = new HashMap<>();
/*     */     
/* 135 */     for (BufferedNametag bufferedNametag : nametags) {
/* 136 */       Team team = getOrRegisterTeam(scoreboard, bufferedNametag.getGroupName());
/*     */       
/* 138 */       toReturn.add(team.getName());
/* 139 */       getBufferedTeams().remove(team.getName());
/*     */       
/* 141 */       String prefix = (bufferedNametag.getPrefix() != null) ? bufferedNametag.getPrefix() : ChatColor.RESET.toString();
/* 142 */       String suffix = (bufferedNametag.getSuffix() != null) ? bufferedNametag.getSuffix() : ChatColor.RESET.toString();
/*     */       
/* 144 */       team.setPrefix(prefix);
/* 145 */       team.setSuffix(suffix);
/*     */       
/* 147 */       if (bufferedNametag.getPlayer() != null) {
/* 148 */         if (!team.hasEntry(bufferedNametag.getPlayer().getName())) {
/* 149 */           team.addEntry(bufferedNametag.getPlayer().getName());
/*     */         }
/* 151 */         List<String> inner = new ArrayList<>();
/* 152 */         if (strings.containsKey(team.getName())) {
/* 153 */           inner = strings.get(team.getName());
/*     */         }
/* 155 */         inner.add(bufferedNametag.getPlayer().getName());
/* 156 */         strings.put(team.getName(), inner);
/*     */       } 
/*     */ 
/*     */       
/* 160 */       team.setCanSeeFriendlyInvisibles(bufferedNametag.isFriendlyInvis());
/* 161 */       if (bufferedNametag.getNameTagVisibility() != null) team.setNameTagVisibility(bufferedNametag.getNameTagVisibility());
/*     */     
/*     */     } 
/*     */     
/* 165 */     for (String newGroupName : getBufferedTeams()) {
/* 166 */       Team team = scoreboard.getTeam(newGroupName);
/*     */       
/* 168 */       if (team == null) {
/*     */         continue;
/*     */       }
/*     */       
/* 172 */       for (String entry : team.getEntries()) {
/* 173 */         team.removeEntry(entry);
/*     */       }
/* 175 */       team.unregister();
/*     */     } 
/*     */     
/* 178 */     getBufferedTeams().clear();
/* 179 */     getBufferedTeams().addAll(toReturn);
/*     */ 
/*     */     
/* 182 */     for (String teamName : getBufferedTeams()) {
/* 183 */       List<String> members = strings.get(teamName);
/* 184 */       Team team = scoreboard.getTeam(teamName);
/*     */       
/* 186 */       if (team == null) {
/*     */         continue;
/*     */       }
/*     */       
/* 190 */       for (String entry : team.getEntries()) {
/* 191 */         if (members.contains(entry)) {
/*     */           continue;
/*     */         }
/* 194 */         team.removeEntry(entry);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateNametag(Scoreboard scoreboard, BufferedNametag bufferedNametag) {
/* 207 */     if (bufferedNametag == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 214 */     Team team = getOrRegisterTeam(scoreboard, bufferedNametag.getGroupName());
/*     */ 
/*     */     
/* 217 */     getBufferedTeams().remove(team.getName());
/*     */     
/* 219 */     String prefix = (bufferedNametag.getPrefix() != null) ? bufferedNametag.getPrefix() : ChatColor.RESET.toString();
/* 220 */     String suffix = (bufferedNametag.getSuffix() != null) ? bufferedNametag.getSuffix() : ChatColor.RESET.toString();
/*     */     
/* 222 */     team.setPrefix(prefix);
/* 223 */     team.setSuffix(suffix);
/*     */     
/* 225 */     if (bufferedNametag.getPlayer() != null && 
/* 226 */       !team.hasEntry(bufferedNametag.getPlayer().getName())) {
/* 227 */       team.addEntry(bufferedNametag.getPlayer().getName());
/*     */     }
/*     */ 
/*     */     
/* 231 */     team.setCanSeeFriendlyInvisibles(bufferedNametag.isFriendlyInvis());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update() {
/* 270 */     Scoreboard scoreboard = getScoreboard();
/* 271 */     Player player = Bukkit.getPlayer(getUuid());
/*     */     
/* 273 */     updateHealthBelow(player, scoreboard);
/* 274 */     updateNametags(player, scoreboard);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cleanup() {
/* 281 */     this.bufferedPlayers.clear();
/* 282 */     this.bufferedTeams.clear();
/*     */   }
/*     */ }