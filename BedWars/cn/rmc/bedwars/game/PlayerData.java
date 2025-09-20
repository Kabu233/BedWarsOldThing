/*     */ package cn.rmc.bedwars.game;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Method;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.Statement;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import cn.rmc.bedwars.enums.Data;
/*     */ import cn.rmc.bedwars.enums.game.PlayerState;
/*     */ import cn.rmc.bedwars.enums.game.Resource;
/*     */ import cn.rmc.bedwars.enums.shop.Equipment;
/*     */ import cn.rmc.bedwars.enums.shop.TeamUpgrade;
/*     */ import cn.rmc.bedwars.inventory.MenuBasic;
/*     */ import cn.rmc.bedwars.utils.BukkitReflection;
/*     */ import cn.rmc.bedwars.utils.Group;
/*     */ import cn.rmc.bedwars.utils.database.KeyValue;
/*     */ import cn.rmc.bedwars.utils.item.ItemBuilder;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.PlayerInventory;
/*     */ import org.json.JSONObject;
/*     */ 
/*     */ public class PlayerData {
/*     */   private UUID uuid;
/*     */   private Player player;
/*     */   private Board scoreBoard;
/*     */   private Integer currentExperience;
/*     */   private Integer currentLevel;
/*     */   private PlayerState state;
/*     */   private Team team;
/*     */   private Game game;
/*     */   private Long cantAttackTimeStamp;
/*     */   private QuickShopData quickShopData;
/*     */   private MenuBasic currentShop;
/*     */   private MenuBasic teamselector;
/*     */   private ShopItemBasic setingItem;
/*     */   private Game specGame;
/*     */   private Integer lastshout;
/*     */   
/*  45 */   public void setUuid(UUID uuid) { this.uuid = uuid; } public void setPlayer(Player player) { this.player = player; } public void setScoreBoard(Board scoreBoard) { this.scoreBoard = scoreBoard; } public void setCurrentExperience(Integer currentExperience) { this.currentExperience = currentExperience; } public void setCurrentLevel(Integer currentLevel) { this.currentLevel = currentLevel; } public void setState(PlayerState state) { this.state = state; } public void setTeam(Team team) { this.team = team; } public void setGame(Game game) { this.game = game; } public void setCantAttackTimeStamp(Long cantAttackTimeStamp) { this.cantAttackTimeStamp = cantAttackTimeStamp; } public void setQuickShopData(QuickShopData quickShopData) { this.quickShopData = quickShopData; } public void setCurrentShop(MenuBasic currentShop) { this.currentShop = currentShop; } public void setTeamselector(MenuBasic teamselector) { this.teamselector = teamselector; } public void setSetingItem(ShopItemBasic setingItem) { this.setingItem = setingItem; } public void setSpecGame(Game specGame) { this.specGame = specGame; } public void setLastshout(Integer lastshout) { this.lastshout = lastshout; } public void setSpecOther(SpecOther specOther) { this.specOther = specOther; } public void setSpectateMode(boolean spectateMode) { this.spectateMode = spectateMode; } public void setArmors(ArrayList<Equipment> Armors) { this.Armors = Armors; } public void setArmor(Equipment Armor) { this.Armor = Armor; } public void setCut(Boolean Cut) { this.Cut = Cut; } public void setPickaxe(Equipment pickaxe) { this.pickaxe = pickaxe; } public void setAxe(Equipment axe) { this.axe = axe; } public void setMilktime(Long milktime) { this.milktime = milktime; } public void setSpawngiven(HashMap<Resource, Integer> spawngiven) { this.spawngiven = spawngiven; } public void setTargetTeam(Team targetTeam) { this.targetTeam = targetTeam; } public void setVisionable(boolean visionable) { this.visionable = visionable; } public void setLoginSpec(boolean isLoginSpec) { this.isLoginSpec = isLoginSpec; } public void setCollections(HashMap<Resource, Integer> collections) { this.collections = collections; } public void setDataBase(DataBase dataBase) { this.dataBase = dataBase; } public String toString() { return "PlayerData(uuid=" + getUuid() + ", player=" + getPlayer() + ", scoreBoard=" + getScoreBoard() + ", currentExperience=" + getCurrentExperience() + ", currentLevel=" + getCurrentLevel() + ", state=" + getState() + ", team=" + getTeam() + ", game=" + getGame() + ", cantAttackTimeStamp=" + getCantAttackTimeStamp() + ", quickShopData=" + getQuickShopData() + ", currentShop=" + getCurrentShop() + ", teamselector=" + getTeamselector() + ", setingItem=" + getSetingItem() + ", specGame=" + getSpecGame() + ", lastshout=" + getLastshout() + ", specOther=" + getSpecOther() + ", spectateMode=" + isSpectateMode() + ", Armors=" + getArmors() + ", Armor=" + getArmor() + ", Cut=" + getCut() + ", pickaxe=" + getPickaxe() + ", axe=" + getAxe() + ", milktime=" + getMilktime() + ", spawngiven=" + getSpawngiven() + ", targetTeam=" + getTargetTeam() + ", visionable=" + isVisionable() + ", isLoginSpec=" + isLoginSpec() + ", iswin=" + getIswin() + ", kills=" + getKills() + ", finalkills=" + getFinalkills() + ", deaths=" + getDeaths() + ", finaldeaths=" + getFinaldeaths() + ", bedbreak=" + getBedbreak() + ", bedbroken=" + getBedbroken() + ", coins=" + getCoins() + ", expenience=" + getExpenience() + ", blocks_broken=" + getBlocks_broken() + ", blocks_palced=" + getBlocks_palced() + ", collections=" + getCollections() + ", dataBase=" + getDataBase() + ")"; }
/*     */   
/*  47 */   public UUID getUuid() { return this.uuid; }
/*  48 */   public Player getPlayer() { return this.player; }
/*  49 */   public Board getScoreBoard() { return this.scoreBoard; }
/*  50 */   public Integer getCurrentExperience() { return this.currentExperience; } public Integer getCurrentLevel() {
/*  51 */     return this.currentLevel;
/*     */   }
/*  53 */   public PlayerState getState() { return this.state; }
/*  54 */   public Team getTeam() { return this.team; }
/*  55 */   public Game getGame() { return this.game; }
/*  56 */   public Long getCantAttackTimeStamp() { return this.cantAttackTimeStamp; }
/*  57 */   public QuickShopData getQuickShopData() { return this.quickShopData; }
/*  58 */   public MenuBasic getCurrentShop() { return this.currentShop; }
/*  59 */   public MenuBasic getTeamselector() { return this.teamselector; }
/*  60 */   public ShopItemBasic getSetingItem() { return this.setingItem; }
/*  61 */   public Game getSpecGame() { return this.specGame; } public Integer getLastshout() {
/*  62 */     return this.lastshout;
/*  63 */   } private SpecOther specOther = null; public SpecOther getSpecOther() { return this.specOther; }
/*     */    private boolean spectateMode = false; public boolean isSpectateMode() {
/*  65 */     return this.spectateMode;
/*     */   }
/*     */   
/*  68 */   private ArrayList<Equipment> Armors = new ArrayList<>(); public ArrayList<Equipment> getArmors() { return this.Armors; }
/*  69 */    private Equipment Armor = Equipment.LEATHER_Armor; public Equipment getArmor() { return this.Armor; }
/*     */    public void addArmor(Equipment equipment) {
/*  71 */     this.Armors.add(equipment);
/*  72 */     setArmor(equipment);
/*     */   }
/*  74 */   private Boolean Cut = Boolean.valueOf(false); public Boolean getCut() { return this.Cut; }
/*  75 */    private Equipment pickaxe = null; public Equipment getPickaxe() { return this.pickaxe; }
/*  76 */    private Equipment axe = null; private Long milktime; private HashMap<Resource, Integer> spawngiven; private Team targetTeam; public Equipment getAxe() { return this.axe; }
/*     */ 
/*     */   
/*  79 */   public Long getMilktime() { return this.milktime; }
/*  80 */   public HashMap<Resource, Integer> getSpawngiven() { return this.spawngiven; }
/*  81 */   public Team getTargetTeam() { return this.targetTeam; } private boolean visionable = true; public boolean isVisionable() {
/*  82 */     return this.visionable;
/*     */   } private boolean isLoginSpec = false; private Boolean iswin; private int kills; private int finalkills; private int deaths; private int finaldeaths; private int bedbreak; private int bedbroken; private int coins; private int expenience; private int blocks_broken; private int blocks_palced; private HashMap<Resource, Integer> collections; DataBase dataBase; public boolean isLoginSpec() {
/*  84 */     return this.isLoginSpec;
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
/*     */   public void giveWaitItem() {
/*  97 */     PlayerInventory playerInventory = this.player.getInventory();
/*  98 */     playerInventory.clear();
/*  99 */     PlayerUtils.clearArmor(this);
/*     */     
/* 101 */     playerInventory.setItem(0, (new ItemBuilder(Material.NOTE_BLOCK)).setName("§a队伍选择 §7(右键点击)").setLore(new String[] { "§7右键来选择你的队伍" }).toItemStack());
/*     */     
/* 103 */     if (this.player.hasPermission("bw.forcestart")) {
/* 104 */       playerInventory.setItem(7, (new ItemBuilder(Material.DIAMOND)).setName("§b强制开始 §7(右键点击)").setLore(new String[] { "§7右键来强制开始这局游戏" }).toItemStack());
/*     */     }
/* 106 */     playerInventory.setItem(8, (new ItemBuilder(Material.BED)).setName("§c返回大厅 §7(右键点击)").setLore(new String[] { "§7右键来离开并回到大厅" }).toItemStack());
/* 107 */     this.player.updateInventory();
/*     */   }
/*     */   public void giveSpecItem() {
/* 110 */     PlayerInventory playerInventory = this.player.getInventory();
/* 111 */     playerInventory.clear();
/* 112 */     this.player.setItemOnCursor(null);
/* 113 */     PlayerUtils.clearArmor(this);
/* 114 */     playerInventory.setItem(0, (new ItemBuilder(Material.COMPASS)).setName("§a传送器 §7(右键点击)").setLore(new String[] { "§7右键来观察玩家!" }).toItemStack());
/* 115 */     playerInventory.setItem(4, (new ItemBuilder(Material.REDSTONE_COMPARATOR)).setName("§b旁观者设置 §7(右键点击)").setLore(new String[] { "§7右键点击更改你的旁观者设置!" }).toItemStack());
/* 116 */     playerInventory.setItem(7, (new ItemBuilder(Material.PAPER)).setName("§e再来一把 §7(右键点击)").toItemStack());
/* 117 */     playerInventory.setItem(8, (new ItemBuilder(Material.BED)).setName("§c返回大厅 §7(右键点击)").setLore(new String[] { "§7右键来离开并回到大厅" }).toItemStack());
/* 118 */     this.player.updateInventory();
/*     */   }
/*     */   public void giveGameItem() {
/* 121 */     PlayerInventory playerInventory = this.player.getInventory();
/* 122 */     playerInventory.clear();
/* 123 */     refreshArmor();
/* 124 */     ArrayList<ItemStack> items = new ArrayList<>();
/* 125 */     giveSword();
/* 126 */     if (this.Cut.booleanValue()) {
/* 127 */       items.add((new ItemBuilder(Material.SHEARS)).setUnbreakable().toItemStack());
/*     */     }
/* 129 */     if (this.pickaxe != null) {
/* 130 */       items.add(this.pickaxe.getgiveItem());
/*     */     }
/* 132 */     if (this.axe != null) {
/* 133 */       items.add(this.axe.getgiveItem());
/*     */     }
/* 135 */     playerInventory.setItem(17, (new ItemBuilder(Material.COMPASS)).setName("§a指南针 §7(右键点击)").toItemStack());
/* 136 */     playerInventory.addItem(items.<ItemStack>toArray(new ItemStack[0]));
/* 137 */     this.player.updateInventory();
/*     */   }
/*     */   public void giveSword() {
/* 140 */     PlayerInventory playerInventory = this.player.getInventory();
/* 141 */     ArrayList<ItemStack> items = new ArrayList<>();
/* 142 */     ItemBuilder ib = (new ItemBuilder(Material.WOOD_SWORD)).setUnbreakable();
/* 143 */     if (((Integer)this.team.getTeamUpgrade().get(TeamUpgrade.SharpenedSwords)).intValue() >= 1) {
/* 144 */       ib.addEnchant(Enchantment.DAMAGE_ALL, ((Integer)this.team.getTeamUpgrade().get(TeamUpgrade.SharpenedSwords)).intValue());
/*     */     }
/* 146 */     items.add(ib.toItemStack());
/* 147 */     playerInventory.addItem(items.<ItemStack>toArray(new ItemStack[0]));
/*     */   }
/*     */   
/*     */   public void refreshArmor() {
/* 151 */     ItemBuilder helmet = (new ItemBuilder(Material.LEATHER_HELMET)).setUnbreakable().addEnchant(Enchantment.WATER_WORKER, 1).setLeatherArmorColor(this.team.getTeamType().getDyeColor().getColor());
/*     */     
/* 153 */     ItemBuilder chestplate = (new ItemBuilder(Material.LEATHER_CHESTPLATE)).setUnbreakable().setLeatherArmorColor(this.team.getTeamType().getDyeColor().getColor());
/* 154 */     ItemBuilder leggings = (new ItemBuilder(getArmor().getLegs())).setUnbreakable().setLeatherArmorColor(this.team.getTeamType().getDyeColor().getColor());
/* 155 */     ItemBuilder boots = (new ItemBuilder(getArmor().getgiveItem())).setLeatherArmorColor(this.team.getTeamType().getDyeColor().getColor());
/* 156 */     if (((Integer)this.team.getTeamUpgrade().get(TeamUpgrade.ReinforcedArmor)).intValue() != 0) {
/* 157 */       int i = ((Integer)this.team.getTeamUpgrade().get(TeamUpgrade.ReinforcedArmor)).intValue();
/* 158 */       for (ItemBuilder itemBuilder : Arrays.<ItemBuilder>asList(new ItemBuilder[] { helmet, chestplate, leggings, boots })) {
/* 159 */         itemBuilder.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, i);
/*     */       }
/*     */     } 
/* 162 */     this.player.getInventory().setHelmet(helmet.toItemStack());
/* 163 */     this.player.getInventory().setChestplate(chestplate.toItemStack());
/* 164 */     this.player.getInventory().setLeggings(leggings.toItemStack());
/* 165 */     this.player.getInventory().setBoots(boots.toItemStack());
/* 166 */     this.player.updateInventory();
/*     */   }
/*     */   public void death() {
/* 169 */     if (this.axe != null && Equipment.getValues(Equipment.Sort.Axe).indexOf(this.axe) >= 1) {
/* 170 */       setAxe(Equipment.getValues(Equipment.Sort.Axe).get(Equipment.getValues(Equipment.Sort.Axe).indexOf(this.axe) - 1));
/*     */     }
/* 172 */     if (this.pickaxe != null && Equipment.getValues(Equipment.Sort.Pickaxe).indexOf(this.pickaxe) >= 1) {
/* 173 */       setPickaxe(Equipment.getValues(Equipment.Sort.Pickaxe).get(Equipment.getValues(Equipment.Sort.Pickaxe).indexOf(this.pickaxe) - 1));
/*     */     }
/* 175 */     ((Game)BedWars.getInstance().getGameManager().getGameArrayList().get(0)).toSpec(this, Boolean.valueOf(false));
/* 176 */     (new RespawnRunnable(this)).runTaskTimerAsynchronously((Plugin)BedWars.getInstance(), 0L, 20L);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void spawn() {
/* 182 */     setState(PlayerState.FIGHTING);
/* 183 */     for (PotionEffect potionEffect : this.player.getActivePotionEffects()) {
/* 184 */       this.player.removePotionEffect(potionEffect.getType());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 189 */     this.player.spigot().setCollidesWithEntities(true);
/*     */     
/* 191 */     setCantAttackTimeStamp(Long.valueOf(System.currentTimeMillis()));
/* 192 */     this.player.setGameMode(GameMode.SURVIVAL);
/* 193 */     this.player.setLevel(LevelUtils.getLevel(getCurrentExperience().intValue()).intValue());
/* 194 */     this.player.teleport(this.team.getSpawnloc());
/* 195 */     this.player.setFlying(false);
/* 196 */     this.player.setAllowFlight(false);
/*     */     
/* 198 */     giveGameItem();
/* 199 */     Bukkit.getPluginManager().callEvent((Event)new PlayerSpawnEvent(this));
/* 200 */     if (this.spawngiven != null) {
/* 201 */       for (Map.Entry<Resource, Integer> entry : this.spawngiven.entrySet()) {
/* 202 */         this.player.getInventory().addItem(new ItemStack[] { new ItemStack(((Resource)entry.getKey()).getItem(), ((Integer)entry.getValue()).intValue()) });
/*     */       } 
/*     */     }
/* 205 */     if (((Integer)this.team.getTeamUpgrade().get(TeamUpgrade.ManiacMiner)).intValue() != 0) {
/* 206 */       this.player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 99999, ((Integer)this.team.getTeamUpgrade().get(TeamUpgrade.ManiacMiner)).intValue() - 1, false, false));
/*     */     }
/* 208 */     for (PlayerData data : this.game.onlinePlayers) {
/* 209 */       if (data.getState() == PlayerState.FIGHTING) {
/* 210 */         data.getPlayer().showPlayer(getPlayer()); continue;
/*     */       } 
/* 212 */       getPlayer().hidePlayer(data.getPlayer());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void hideArmor() {
/* 219 */     Object packet1 = null;
/* 220 */     Object packet2 = null;
/* 221 */     Object packet3 = null;
/* 222 */     Object packet4 = null;
/*     */     try {
/* 224 */       Constructor constructor = BukkitReflection.getNMSClass("PacketPlayOutEntityEquipment").getConstructor(new Class[] { int.class, int.class, 
/* 225 */             BukkitReflection.getNMSClass("ItemStack") });
/*     */       
/* 227 */       Object as = BukkitReflection.getClass("inventory.CraftItemStack").getMethod("asNMSCopy", new Class[] { ItemStack.class }).invoke(null, new Object[] { new ItemStack(Material.AIR) });
/* 228 */       packet1 = constructor.newInstance(new Object[] { Integer.valueOf(this.player.getEntityId()), Integer.valueOf(1), as });
/* 229 */       packet2 = constructor.newInstance(new Object[] { Integer.valueOf(this.player.getEntityId()), Integer.valueOf(2), as });
/* 230 */       packet3 = constructor.newInstance(new Object[] { Integer.valueOf(this.player.getEntityId()), Integer.valueOf(3), as });
/* 231 */       packet4 = constructor.newInstance(new Object[] { Integer.valueOf(this.player.getEntityId()), Integer.valueOf(4), as });
/*     */     }
/* 233 */     catch (Exception exception) {}
/*     */     
/* 235 */     final Set<PlayerData> players = this.team.getPlayers();
/* 236 */     final Object finalPacket = packet2;
/* 237 */     final Object finalPacket1 = packet4;
/* 238 */     final Object finalPacket2 = packet3;
/* 239 */     final Object finalPacket3 = packet1;
/* 240 */     (new BukkitRunnable() {
/* 241 */         Location loc = PlayerData.this.player.getLocation().clone();
/*     */ 
/*     */         
/*     */         public void run() {
/* 245 */           if (!PlayerData.this.player.hasPotionEffect(PotionEffectType.INVISIBILITY) || PlayerData.this.player == null || !PlayerData.this.player.isOnline()) {
/* 246 */             PlayerData.this.setVisionable(true);
/*     */             
/* 248 */             cancel();
/* 249 */             PlayerData.this.showArmor();
/*     */             return;
/*     */           } 
/* 252 */           if (PlayerData.this.player.isOnline() && (this.loc.getX() != PlayerData.this.player.getLocation().getX() || this.loc
/* 253 */             .getY() != PlayerData.this.player.getLocation().getY() || this.loc
/* 254 */             .getZ() != PlayerData.this.player.getLocation().getZ())) {
/* 255 */             PlayerData.this.player.getWorld()
/* 256 */               .playEffect(PlayerData.this
/* 257 */                 .player.getLocation().clone().add((Math.random() - Math.random()) * 0.5D, 0.05D, (
/* 258 */                   Math.random() - Math.random()) * 0.5D), Effect.FOOTSTEP, 0);
/*     */           }
/*     */           
/* 261 */           for (PlayerData pd : PlayerData.this.game.getPlayers()) {
/* 262 */             Player p = pd.getPlayer();
/* 263 */             if (p != PlayerData.this.player && !players.contains(pd)) {
/* 264 */               BukkitReflection.sendPacket(p, finalPacket3);
/* 265 */               BukkitReflection.sendPacket(p, finalPacket);
/* 266 */               BukkitReflection.sendPacket(p, finalPacket2);
/* 267 */               BukkitReflection.sendPacket(p, finalPacket1);
/*     */             } 
/*     */           } 
/*     */         }
/* 271 */       }).runTaskTimer((Plugin)BedWars.getInstance(), 0L, 10L);
/*     */   }
/*     */   
/*     */   private void showArmor() {
/*     */     try {
/* 276 */       Constructor constructor = BukkitReflection.getNMSClass("PacketPlayOutEntityEquipment").getConstructor(new Class[] { int.class, int.class, 
/* 277 */             BukkitReflection.getNMSClass("ItemStack") });
/* 278 */       Method method = BukkitReflection.getClass("inventory.CraftItemStack").getMethod("asNMSCopy", new Class[] { ItemStack.class });
/* 279 */       Object packet1 = constructor.newInstance(new Object[] { Integer.valueOf(this.player.getEntityId()), Integer.valueOf(1), method
/* 280 */             .invoke(null, new Object[] { this.player.getInventory().getBoots() }) });
/* 281 */       Object packet2 = constructor.newInstance(new Object[] { Integer.valueOf(this.player.getEntityId()), Integer.valueOf(2), method
/* 282 */             .invoke(null, new Object[] { this.player.getInventory().getLeggings() }) });
/* 283 */       Object packet3 = constructor.newInstance(new Object[] { Integer.valueOf(this.player.getEntityId()), Integer.valueOf(3), method
/* 284 */             .invoke(null, new Object[] { this.player.getInventory().getChestplate() }) });
/* 285 */       Object packet4 = constructor.newInstance(new Object[] { Integer.valueOf(this.player.getEntityId()), Integer.valueOf(4), method
/* 286 */             .invoke(null, new Object[] { this.player.getInventory().getHelmet() }) });
/* 287 */       Set<PlayerData> players = this.team.getPlayers();
/* 288 */       for (PlayerData pd : this.game.getPlayers()) {
/* 289 */         Player p = pd.getPlayer();
/* 290 */         if (p != this.player && !players.contains(pd)) {
/* 291 */           BukkitReflection.sendPacket(p, packet1);
/* 292 */           BukkitReflection.sendPacket(p, packet2);
/* 293 */           BukkitReflection.sendPacket(p, packet3);
/* 294 */           BukkitReflection.sendPacket(p, packet4);
/*     */         } 
/*     */       } 
/* 297 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 303 */     if (this == o) return true; 
/* 304 */     if (o == null || getClass() != o.getClass()) return false; 
/* 305 */     PlayerData that = (PlayerData)o;
/* 306 */     return Objects.equals(this.uuid, that.uuid);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 311 */     return Objects.hash(new Object[] { this.uuid });
/*     */   }
/*     */   
/* 314 */   public Boolean getIswin() { return this.iswin; }
/* 315 */   public void setIswin(Boolean iswin) { this.iswin = iswin; }
/* 316 */   public int getKills() { return this.kills; } public int getFinalkills() { return this.finalkills; } public int getDeaths() { return this.deaths; } public int getFinaldeaths() { return this.finaldeaths; } public int getBedbreak() { return this.bedbreak; } public PlayerData(UUID uuid) { this.iswin = Boolean.valueOf(false);
/* 317 */     this.kills = 0; this.finalkills = 0; this.deaths = 0; this.finaldeaths = 0; this.bedbreak = 0; this.bedbroken = 0; this.coins = 0; this.expenience = 0; this.blocks_broken = 0; this.blocks_palced = 0;
/*     */ 
/*     */ 
/*     */     
/* 321 */     this.collections = new HashMap<>();
/*     */     
/* 323 */     this.dataBase = BedWars.getInstance().getDataBase(); this.uuid = uuid; this.player = Bukkit.getPlayer(uuid); this.state = PlayerState.SPAWNING; this.Armors.add(Equipment.LEATHER_Armor); this.team = new Team(TeamType.NON, null); this.currentExperience = Integer.valueOf((DataUtils.get(uuid.toString(), Data.Field.EXPERIENCE) == null) ? 0 : Integer.parseInt(DataUtils.get(uuid.toString(), Data.Field.EXPERIENCE))); this.currentLevel = LevelUtils.getLevel(this.currentExperience.intValue()); } public int getBedbroken() { return this.bedbroken; } public int getCoins() { return this.coins; } public int getExpenience() { return this.expenience; } public int getBlocks_broken() { return this.blocks_broken; } public int getBlocks_palced() { return this.blocks_palced; } public DataBase getDataBase() { return this.dataBase; } public void setKills(int kills) { this.kills = kills; } public void setFinalkills(int finalkills) { this.finalkills = finalkills; } public void setDeaths(int deaths) { this.deaths = deaths; } public void setFinaldeaths(int finaldeaths) { this.finaldeaths = finaldeaths; } public void setBedbreak(int bedbreak) { this.bedbreak = bedbreak; } public void setBedbroken(int bedbroken) { this.bedbroken = bedbroken; }
/*     */   public void setCoins(int coins) { this.coins = coins; }
/*     */   public void setExpenience(int expenience) { this.expenience = expenience; }
/*     */   public void setBlocks_broken(int blocks_broken) { this.blocks_broken = blocks_broken; }
/*     */   public void setBlocks_palced(int blocks_palced) { this.blocks_palced = blocks_palced; }
/*     */   public HashMap<Resource, Integer> getCollections() { return this.collections; }
/* 329 */   public void saveData() { List<Data.Field> values = Data.Field.getValues(Data.PlayerStats, new Data.Field[] { Data.Field.StatsUUID, Data.Field.GAMEMODE });
/*     */ 
/*     */     
/* 332 */     for (String gm : Arrays.<String>asList(new String[] { this.game.getGameMode().name(), this.game.getGameMode().getSubmode(), this.game.getGameMode().getTotalGM().name(), "TOTAL" })) {
/* 333 */       KeyValue keyValue1 = new KeyValue();
/* 334 */       KeyValue keyValue2 = (new KeyValue()).add("UUID", this.uuid).add("Mode", gm);
/* 335 */       boolean bool = false;
/*     */       try {
/* 337 */         Group<Group<Connection, Statement>, ResultSet> group = this.dataBase.getDataBaseCore().executeQuery("SELECT * FROM `playerstats` WHERE " + keyValue2.toWhereString() + " LIMIT 1");
/* 338 */         ResultSet b = (ResultSet)group.getB();
/* 339 */         if (b.next()) {
/* 340 */           bool = true;
/* 341 */           for (Data.Field value : values) {
/* 342 */             keyValue1.add(value.getWhere(), Integer.valueOf(b.getInt(value.getWhere())));
/*     */           }
/*     */         } else {
/* 345 */           for (Data.Field value : values) {
/* 346 */             keyValue1.add(value.getWhere(), Integer.valueOf(0));
/*     */           }
/*     */         } 
/* 349 */         b.close();
/* 350 */         ((Statement)((Group)group.getA()).getB()).close();
/* 351 */         ((Connection)((Group)group.getA()).getA()).close();
/*     */         
/* 353 */         keyValue1.addInt(Data.Field.KILL.getWhere(), Integer.valueOf(this.kills));
/* 354 */         keyValue1.addInt(Data.Field.FINALKILL.getWhere(), Integer.valueOf(this.finalkills));
/* 355 */         keyValue1.addInt(Data.Field.DAILY_FINALKILLS.getWhere(), Integer.valueOf(this.finalkills));
/* 356 */         keyValue1.addInt(Data.Field.WEEKLY_FINALKILLS.getWhere(), Integer.valueOf(this.finalkills));
/* 357 */         keyValue1.addInt(Data.Field.DEATH.getWhere(), Integer.valueOf(this.deaths));
/* 358 */         keyValue1.addInt(Data.Field.FINALDEATH.getWhere(), Integer.valueOf(this.finaldeaths));
/* 359 */         keyValue1.addInt(Data.Field.BEDSBREAK.getWhere(), Integer.valueOf(this.bedbreak));
/* 360 */         keyValue1.addInt(Data.Field.BEDSBROKEN.getWhere(), Integer.valueOf(this.bedbroken));
/* 361 */         keyValue1.addInt(Data.Field.BLOCKBROKEN.getWhere(), Integer.valueOf(this.blocks_broken));
/* 362 */         keyValue1.addInt(Data.Field.BLOCKPLACED.getWhere(), Integer.valueOf(this.blocks_palced));
/*     */         
/* 364 */         if (this.iswin.booleanValue()) {
/* 365 */           keyValue1.addInt(Data.Field.WIN.getWhere(), Integer.valueOf(1));
/* 366 */           keyValue1.addInt(Data.Field.WINSTREAK.getWhere(), Integer.valueOf(1));
/* 367 */           keyValue1.addInt(Data.Field.DAILY_WINS.getWhere(), Integer.valueOf(1));
/* 368 */           keyValue1.addInt(Data.Field.WEEKLY_WINS.getWhere(), Integer.valueOf(1));
/*     */         } else {
/* 370 */           keyValue1.addInt(Data.Field.LOSS.getWhere(), Integer.valueOf(1));
/* 371 */           keyValue1.set(Data.Field.WINSTREAK.getWhere(), Integer.valueOf(0));
/*     */         } 
/* 373 */         if (bool) {
/* 374 */           this.dataBase.getDataBaseCore().executeUpdate("UPDATE `playerstats` SET " + keyValue1.toUpdateString() + " WHERE " + keyValue2.toWhereString()); continue;
/*     */         } 
/* 376 */         keyValue1.add(keyValue2);
/* 377 */         this.dataBase.getDataBaseCore().executeUpdate("INSERT INTO `playerstats` " + keyValue1.toInsertString());
/*     */       }
/* 379 */       catch (SQLException throwables) {
/* 380 */         throwables.printStackTrace();
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 387 */     List<Data.Field> valuess = Data.Field.getValues(Data.PlayerInfo, new Data.Field[] { Data.Field.UUID });
/*     */ 
/*     */     
/* 390 */     KeyValue where = (new KeyValue()).add("UUID", this.uuid);
/* 391 */     KeyValue kv = new KeyValue();
/* 392 */     boolean exist = false;
/*     */     try {
/* 394 */       Group<Group<Connection, Statement>, ResultSet> group = this.dataBase.getDataBaseCore().executeQuery("SELECT * FROM `playerinfo` WHERE " + where.toWhereString());
/* 395 */       ResultSet b = (ResultSet)group.getB();
/* 396 */       if (b.next()) {
/* 397 */         exist = true;
/* 398 */         for (Data.Field value : valuess) {
/* 399 */           String str = b.getString(value.getWhere());
/* 400 */           if (b.wasNull())
/* 401 */             continue;  kv.add(value.getWhere(), str);
/*     */         } 
/*     */       } 
/* 404 */       b.close();
/* 405 */       ((Statement)((Group)group.getA()).getB()).close();
/* 406 */       ((Connection)((Group)group.getA()).getA()).close();
/* 407 */       kv.addInt(Data.Field.COIN.getWhere(), Integer.valueOf(this.coins));
/* 408 */       kv.addInt(Data.Field.EXPERIENCE.getWhere(), Integer.valueOf(this.expenience));
/* 409 */       kv.set(Data.Field.COLLECTED.getWhere(), 
/* 410 */           DataUtils.JsongetCollection(kv.getString(Data.Field.COLLECTED.getWhere()), this.collections));
/* 411 */       if (exist) {
/* 412 */         this.dataBase.getDataBaseCore().executeUpdate("UPDATE `playerinfo` SET " + kv.toUpdateString() + " WHERE " + where.toWhereString());
/*     */       } else {
/* 414 */         kv.add(where);
/* 415 */         this.dataBase.getDataBaseCore().executeUpdate("INSERT INTO `playerinfo` " + kv.toInsertString());
/*     */       } 
/* 417 */     } catch (SQLException throwables) {
/* 418 */       throwables.printStackTrace();
/*     */     }  }
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
/*     */   public JSONObject getJsonStats() {
/* 434 */     JSONObject jo = new JSONObject();
/* 435 */     jo.put("Name", getPlayer().getName());
/* 436 */     jo.put("Coins", this.coins);
/* 437 */     jo.put("Experience", this.expenience);
/* 438 */     jo.put("Kills", this.kills);
/* 439 */     jo.put("FinalKills", this.finalkills);
/* 440 */     jo.put("Deaths", this.deaths);
/* 441 */     jo.put("FinalDeaths", this.finaldeaths);
/* 442 */     jo.put("BedBreak", this.bedbreak);
/* 443 */     jo.put("BedBroken", this.bedbroken);
/* 444 */     jo.put("Blocks_Placed", this.blocks_palced);
/* 445 */     jo.put("Blocks_Broken", this.blocks_broken);
/* 446 */     return jo;
/*     */   }
/*     */   
/*     */   public void sendMessage(String... message) {
/* 450 */     for (String s : message)
/* 451 */       this.player.sendMessage(ChatColor.translateAlternateColorCodes('&', s)); 
/*     */   }
/*     */ }