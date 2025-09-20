/*     */ package cn.rmc.bedwars.game;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import cn.rmc.bedwars.BedWars;
/*     */ import cn.rmc.bedwars.enums.ITeamUpgrade;
/*     */ import cn.rmc.bedwars.enums.game.TeamState;
/*     */ import cn.rmc.bedwars.enums.game.TeamType;
/*     */ import cn.rmc.bedwars.enums.shop.TeamUpgrade;
/*     */ import cn.rmc.bedwars.utils.player.TitleUtils;
/*     */ import net.citizensnpcs.api.npc.NPC;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.potion.PotionEffect;
/*     */ import org.bukkit.potion.PotionEffectType;
/*     */ 
/*     */ public class Team {
/*     */   private TeamType teamType;
/*     */   private int maxplayer;
/*     */   private Game game;
/*     */   private TeamState state;
/*     */   
/*  27 */   public void setTeamType(TeamType teamType) { this.teamType = teamType; } public void setMaxplayer(int maxplayer) { this.maxplayer = maxplayer; } public void setGame(Game game) { this.game = game; } public void setState(TeamState state) { this.state = state; } public void setAlivePlayers(Set<PlayerData> alivePlayers) { this.alivePlayers = alivePlayers; } public void setPlayers(Set<PlayerData> players) { this.players = players; } public void setPlayeredplayer(Set<PlayerData> playeredplayer) { this.playeredplayer = playeredplayer; } public void setTeamname(String teamname) { this.teamname = teamname; } public void setSpawnloc(Location spawnloc) { this.spawnloc = spawnloc; } public void setChestloc(Location chestloc) { this.chestloc = chestloc; } public void setBedloc(Location bedloc) { this.bedloc = bedloc; } public void setResloc(Location resloc) { this.resloc = resloc; } public void setPos1(Location pos1) { this.pos1 = pos1; } public void setPos2(Location pos2) { this.pos2 = pos2; } public void setItemshop(Location itemshop) { this.itemshop = itemshop; } public void setItemshopnpc(NPC itemshopnpc) { this.itemshopnpc = itemshopnpc; } public void setTeamshop(Location teamshop) { this.teamshop = teamshop; } public void setTeamshopnpc(NPC teamshopnpc) { this.teamshopnpc = teamshopnpc; } public void setTeamUpgrade(HashMap<ITeamUpgrade, Integer> teamUpgrade) { this.teamUpgrade = teamUpgrade; } public void setTraps(ArrayList<Map.Entry<ITeamUpgrade, PlayerData>> traps) { this.traps = traps; }
/*     */   
/*  29 */   public TeamType getTeamType() { return this.teamType; }
/*  30 */   public int getMaxplayer() { return this.maxplayer; }
/*  31 */   public Game getGame() { return this.game; } public TeamState getState() {
/*  32 */     return this.state;
/*  33 */   } private Set<PlayerData> alivePlayers = new HashSet<>(); public Set<PlayerData> getAlivePlayers() { return this.alivePlayers; }
/*  34 */    private Set<PlayerData> players = new HashSet<>(); public Set<PlayerData> getPlayers() { return this.players; }
/*  35 */    private Set<PlayerData> playeredplayer = new HashSet<>(); private String teamname; private Location spawnloc; private Location chestloc; private Location bedloc; private Location resloc; public Set<PlayerData> getPlayeredplayer() { return this.playeredplayer; }
/*     */    private Location pos1; private Location pos2; private Location itemshop; private NPC itemshopnpc; private Location teamshop; private NPC teamshopnpc; public String getTeamname() {
/*  37 */     return this.teamname; }
/*  38 */   public Location getSpawnloc() { return this.spawnloc; }
/*  39 */   public Location getChestloc() { return this.chestloc; }
/*  40 */   public Location getBedloc() { return this.bedloc; }
/*  41 */   public Location getResloc() { return this.resloc; }
/*  42 */   public Location getPos1() { return this.pos1; }
/*  43 */   public Location getPos2() { return this.pos2; }
/*  44 */   public Location getItemshop() { return this.itemshop; }
/*  45 */   public NPC getItemshopnpc() { return this.itemshopnpc; }
/*  46 */   public Location getTeamshop() { return this.teamshop; } public NPC getTeamshopnpc() {
/*  47 */     return this.teamshopnpc;
/*     */   }
/*     */   
/*  50 */   private HashMap<ITeamUpgrade, Integer> teamUpgrade = new HashMap<>(); public HashMap<ITeamUpgrade, Integer> getTeamUpgrade() { return this.teamUpgrade; }
/*     */ 
/*     */ 
/*     */   
/*  54 */   private ArrayList<Map.Entry<ITeamUpgrade, PlayerData>> traps = new ArrayList<>(); public ArrayList<Map.Entry<ITeamUpgrade, PlayerData>> getTraps() { return this.traps; }
/*     */ 
/*     */   
/*     */   public Team(TeamType teamType, Game game) {
/*  58 */     this.teamType = teamType;
/*  59 */     this.game = game;
/*  60 */     this.state = TeamState.ALIVE;
/*  61 */     if (game != null) {
/*  62 */       Map map = game.getMap();
/*  63 */       this.teamname = map.getTeamdisplayname().get(teamType);
/*  64 */       this.spawnloc = map.getSpawns().get(teamType);
/*  65 */       this.maxplayer = map.getEachmaxplayer();
/*  66 */       this.chestloc = map.getTeamchests().get(teamType);
/*  67 */       this.bedloc = map.getBeds().get(teamType);
/*  68 */       this.resloc = map.getTeamresourcesloc().get(teamType);
/*  69 */       this.pos1 = map.getTeampos1().get(teamType);
/*  70 */       this.pos2 = map.getTeampos2().get(teamType);
/*  71 */       this.itemshop = map.getItemshop().get(teamType);
/*  72 */       this.teamshop = map.getTeamshop().get(teamType);
/*     */     } 
/*     */   }
/*     */   public void init() {
/*  76 */     for (ITeamUpgrade value : ITeamUpgrade.upgrades) {
/*  77 */       this.teamUpgrade.put(value, Integer.valueOf(0));
/*     */     }
/*     */   }
/*     */   
/*     */   public void destroy(PlayerData pd, Boolean isFinal) {
/*  82 */     setState(TeamState.BROKEN);
/*  83 */     Bukkit.getPluginManager().callEvent((Event)new BedBreakEvent(pd, this));
/*  84 */     Bukkit.getScheduler().runTask((Plugin)BedWars.getInstance(), () -> BlockUtils.getBedNeighbor(getBedloc().getBlock()).setType(Material.AIR));
/*  85 */     if (pd != null) {
/*  86 */       pd.setBedbreak(pd.getBedbreak() + 1);
/*     */     }
/*  88 */     for (PlayerData player : this.playeredplayer) {
/*  89 */       player.setBedbroken(player.getBedbroken() + 1);
/*     */     }
/*  91 */     for (PlayerData player : this.game.getPlayers()) {
/*  92 */       if (!isFinal.booleanValue()) {
/*  93 */         if (player.getTeam() == this) {
/*  94 */           TitleUtils.sendFullTitle(player.getPlayer(), Integer.valueOf(0), Integer.valueOf(60), Integer.valueOf(0), "§c床已被破坏!", "§f死亡后无法重生!");
/*  95 */           player.getPlayer().sendMessage(new String[] { "", "§f§l床被破坏了 > §7你的床被拆了, 破坏者: " + pd
/*     */                 
/*  97 */                 .getTeam().getTeamType().getChatColor() + pd.getPlayer().getName(), "" });
/*     */ 
/*     */ 
/*     */           
/* 101 */           player.getPlayer().playSound(player.getPlayer().getLocation(), Sound.WITHER_DEATH, 1.0F, 1.0F);
/*     */           
/*     */           continue;
/*     */         } 
/* 105 */         player.getPlayer().playSound(player.getPlayer().getLocation(), Sound.ENDERDRAGON_GROWL, 1.0F, 1.0F);
/*     */ 
/*     */         
/* 108 */         player.getPlayer().sendMessage(new String[] { "", "§f§l床被破坏了 > " + this.teamType
/*     */               
/* 110 */               .getChatColor() + this.teamType.getDisplayname() + "§7的床被拆了, 破坏者: " + pd.getTeam().getTeamType().getChatColor() + pd.getPlayer().getName(), "" });
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 116 */     BedWars.getInstance().getScoreBoardManager().updateAll();
/* 117 */     detect();
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
/*     */   public void aliveBed(PlayerData pd) {}
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
/*     */   public Boolean toTrap(PlayerData pd) {
/* 162 */     if (this.traps.size() <= 0) {
/* 163 */       return Boolean.valueOf(false);
/*     */     }
/* 165 */     ITeamUpgrade t = (ITeamUpgrade)((Map.Entry)this.traps.get(0)).getKey();
/* 166 */     this.traps.remove(0);
/* 167 */     for (PlayerData player : this.alivePlayers) {
/* 168 */       player.getPlayer().playSound(player.getPlayer().getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 0.5F);
/*     */     }
/* 170 */     if (t instanceof TeamUpgrade) {
/* 171 */       Bukkit.getScheduler().runTask((Plugin)BedWars.getInstance(), () -> {
/*     */             int i;
/*     */             switch ((TeamUpgrade)t) {
/*     */               case ItsaTrap:
/*     */                 for (PlayerData player : this.alivePlayers) {
/*     */                   TitleUtils.sendFullTitle(player.getPlayer(), Integer.valueOf(0), Integer.valueOf(40), Integer.valueOf(0), "§c§l陷阱触发!", pd.getTeam().getTeamType().getwithcolor() + "§f这是个陷阱 触发了!");
/*     */                   player.getPlayer().sendMessage("§c§l这是个陷阱触发了!");
/*     */                 } 
/*     */                 pd.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 0, false, false));
/*     */                 pd.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 120, 0, false, false));
/*     */                 break;
/*     */               
/*     */               case CounterOffensiveTrap:
/*     */                 for (PlayerData player : this.alivePlayers) {
/*     */                   TitleUtils.sendFullTitle(player.getPlayer(), Integer.valueOf(0), Integer.valueOf(40), Integer.valueOf(0), "§c§l陷阱触发!", pd.getTeam().getTeamType().getwithcolor() + "§f你的 反击陷阱 触发了!");
/*     */                   player.getPlayer().sendMessage("§c§l反击陷阱触发了!");
/*     */                   player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 0, false, false));
/*     */                   player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 200, 1, false, false));
/*     */                 } 
/*     */                 break;
/*     */               case AlarmTrap:
/*     */                 for (PlayerData player : this.alivePlayers) {
/*     */                   TitleUtils.sendFullTitle(player.getPlayer(), Integer.valueOf(0), Integer.valueOf(40), Integer.valueOf(0), "§c§l警报!!!", pd.getTeam().getTeamType().getwithcolor() + "§f触发了报警陷阱!");
/*     */                   player.getPlayer().sendMessage(pd.getTeam().getTeamType().getwithcolor() + "§c§l玩家" + pd.getTeam().getTeamType().getChatColor() + pd.getPlayer().getName() + "§c§l触发了报警陷阱!");
/*     */                 } 
/*     */                 for (i = 0; i < 120; i += 2) {
/*     */                   Bukkit.getScheduler().runTaskLater((Plugin)BedWars.getInstance(), (), i);
/*     */                 }
/*     */                 break;
/*     */               case MineFatigueTrap:
/*     */                 for (PlayerData player : this.alivePlayers) {
/*     */                   TitleUtils.sendFullTitle(player.getPlayer(), Integer.valueOf(0), Integer.valueOf(40), Integer.valueOf(0), "§c§l陷阱触发!", pd.getTeam().getTeamType().getwithcolor() + "§f挖掘疲劳陷阱 触发了!");
/*     */                   player.getPlayer().sendMessage("§c§l挖掘疲劳陷阱触发了!");
/*     */                 } 
/*     */                 pd.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 200, 0, false, false));
/*     */                 break;
/*     */             } 
/*     */           });
/*     */     }
/* 210 */     return Boolean.valueOf(true);
/*     */   }
/*     */   public void detect() {
/* 213 */     if (this.alivePlayers.size() <= 0) {
/* 214 */       setState(TeamState.DEAD);
/* 215 */       BedWars.getInstance().getScoreBoardManager().updateAll();
/* 216 */       for (PlayerData player : this.game.getPlayers()) {
/* 217 */         player.getPlayer().sendMessage(new String[] { "", " §f§l团灭 > " + this.teamType
/*     */               
/* 219 */               .getChatColor() + this.teamType.getDisplayname() + "§c已被淘汰!", "" });
/*     */       } 
/*     */ 
/*     */       
/* 223 */       int i = 0;
/* 224 */       int dead = 0;
/* 225 */       Team winner = null;
/* 226 */       for (Team team : this.game.getTeams()) {
/* 227 */         if (team.getState() == TeamState.ALIVE || team.getState() == TeamState.BROKEN) {
/* 228 */           winner = team;
/* 229 */           i++;
/*     */         } 
/* 231 */         if (team.getState() == TeamState.DEAD) {
/* 232 */           dead++;
/*     */         }
/*     */       } 
/* 235 */       if (i <= 1 && this.game.getTeams().size() - dead <= 1) {
/* 236 */         assert winner != null;
/* 237 */         this.game.end(winner);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   public void death() {
/* 242 */     if (this.alivePlayers.size() <= 0) {
/* 243 */       setState(TeamState.DEAD);
/* 244 */       Bukkit.getScheduler().runTask((Plugin)BedWars.getInstance(), () -> BlockUtils.getBedNeighbor(getBedloc().getBlock()).setType(Material.AIR));
/* 245 */       for (PlayerData player : this.game.getPlayers()) {
/* 246 */         player.getPlayer().sendMessage(new String[] { "", " §f§l团灭 > " + this.teamType
/*     */               
/* 248 */               .getChatColor() + this.teamType.getDisplayname() + "§c已被淘汰!", "" });
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addPlayer(PlayerData pd) {
/* 257 */     pd.setTeam(this);
/* 258 */     this.players.add(pd);
/* 259 */     this.alivePlayers.add(pd);
/*     */   }
/*     */   
/*     */   public void removePlayer(PlayerData pd) {
/* 263 */     pd.setTeam(new Team(TeamType.NON, null));
/* 264 */     this.players.remove(pd);
/* 265 */     this.alivePlayers.remove(pd);
/*     */   }
/*     */   
/*     */   public int getPlayersKillWithFinalKill() {
/* 269 */     int i = 0;
/* 270 */     for (PlayerData player : this.playeredplayer) {
/* 271 */       i += player.getFinalkills();
/* 272 */       i += player.getKills();
/*     */     } 
/* 274 */     return i;
/*     */   }
/*     */ }