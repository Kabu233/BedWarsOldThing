/*     */ package cn.rmc.bedwars.inventory.spec;
/*     */ 
/*     */ import cn.rmc.bedwars.BedWars;
/*     */ import cn.rmc.bedwars.enums.game.PlayerState;
/*     */ import cn.rmc.bedwars.game.PlayerData;
/*     */ import cn.rmc.bedwars.utils.database.DataBase;
/*     */ import cn.rmc.bedwars.utils.database.KeyValue;
/*     */ import cn.rmc.bedwars.utils.player.ActionBarUtils;
/*     */ import cn.rmc.bedwars.utils.player.LuckPermsUtil;
/*     */ import cn.rmc.bedwars.utils.player.PlayerUtils;
/*     */ import cn.rmc.bedwars.utils.player.TitleUtils;
/*     */ import org.bukkit.GameMode;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.potion.PotionEffectType;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ import org.bukkit.scheduler.BukkitTask;
/*     */ 
/*     */ public class SpecOther {
/*     */   Player player;
/*     */   Player target;
/*     */   
/*  25 */   public Player getPlayer() { return this.player; } public Player getTarget() {
/*  26 */     return this.target;
/*     */   }
/*  28 */   String speedType = "NONE"; public String getSpeedType() { return this.speedType; }
/*  29 */   boolean autoTeleport = false; public boolean isAutoTeleport() { return this.autoTeleport; }
/*  30 */   boolean nightVision = false; public boolean isNightVision() { return this.nightVision; }
/*  31 */   boolean autoFirstPerson = false; public boolean isAutoFirstPerson() { return this.autoFirstPerson; }
/*  32 */   boolean hideOthers = false; public boolean isHideOthers() { return this.hideOthers; } boolean firstPersonTargeting = false; public boolean isFirstPersonTargeting() {
/*  33 */     return this.firstPersonTargeting;
/*     */   }
/*  35 */   DataBase dataBase = BedWars.getInstance().getDataBase(); BukkitTask task; public DataBase getDataBase() { return this.dataBase; } public BukkitTask getTask() {
/*  36 */     return this.task;
/*     */   }
/*     */   public SpecOther(Player p) {
/*  39 */     this.player = p;
/*  40 */     loadData();
/*  41 */     startTask();
/*     */   }
/*     */   
/*     */   void loadData() {
/*  45 */     if (!this.dataBase.isTableExists("game_spec"))
/*  46 */       return;  if (!this.dataBase.dbExist("game_spec", new KeyValue("UUID", this.player.getUniqueId().toString()))) {
/*  47 */       this.dataBase.dbInsert("game_spec", (new KeyValue("UUID", this.player.getUniqueId().toString())).add(new KeyValue("Name", this.player.getName()))
/*  48 */           .add(new KeyValue("SelectedSpeedType", this.speedType)).add(new KeyValue("AutoTeleport", Boolean.valueOf(this.autoTeleport)))
/*  49 */           .add((new KeyValue("NightVision", Boolean.valueOf(this.nightVision))).add(new KeyValue("AutoFirstPerson", Boolean.valueOf(this.autoFirstPerson)))).add(new KeyValue("HideOthers", Boolean.valueOf(this.hideOthers))));
/*     */       
/*     */       return;
/*     */     } 
/*  53 */     for (KeyValue value : this.dataBase.dbSelect("game_spec", (new KeyValue("SelectedSpeedType", "VARCHAR(32)")).add(new KeyValue("AutoTeleport", "VARCHAR(5)"))
/*  54 */         .add(new KeyValue("NightVision", "VARCHAR(5)")).add(new KeyValue("AutoFirstPerson", "VARCHAR(5)")).add(new KeyValue("HideOthers", "VARCHAR(5)")), new KeyValue("UUID", this.player
/*  55 */           .getUniqueId()))) {
/*  56 */       this.speedType = value.getString("SelectedSpeedType");
/*  57 */       this.autoTeleport = formatString(value.getString("AutoTeleport"));
/*  58 */       this.nightVision = formatString(value.getString("NightVision"));
/*  59 */       this.autoFirstPerson = formatString(value.getString("AutoFirstPerson"));
/*  60 */       this.hideOthers = formatString(value.getString("HideOthers"));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void saveData() {
/*  65 */     this.dataBase.dbUpdate("game_spec", (new KeyValue("SelectedSpeedType", this.speedType)).add(new KeyValue("AutoTeleport", Boolean.valueOf(this.autoTeleport)))
/*  66 */         .add((new KeyValue("NightVision", Boolean.valueOf(this.nightVision))).add(new KeyValue("AutoFirstPerson", Boolean.valueOf(this.autoFirstPerson)))).add(new KeyValue("HideOthers", Boolean.valueOf(this.hideOthers))), new KeyValue("UUID", this.player
/*  67 */           .getUniqueId().toString()));
/*     */   }
/*     */   
/*     */   boolean formatString(String str) {
/*  71 */     return str.equalsIgnoreCase("true");
/*     */   }
/*     */   
/*     */   public void setAutoTeleport(boolean autoTeleport) {
/*  75 */     this.autoTeleport = autoTeleport;
/*  76 */     this.player.sendMessage(autoTeleport ? "§a你将会自动传送至所选择的玩家位置!" : "§c你将不会被自动传送!");
/*  77 */     saveData();
/*     */   }
/*     */   
/*     */   public void setNightVision(boolean nightVision) {
/*  81 */     this.nightVision = nightVision;
/*  82 */     if (this.player.hasPotionEffect(PotionEffectType.NIGHT_VISION))
/*  83 */       this.player.removePotionEffect(PotionEffectType.NIGHT_VISION); 
/*  84 */     if (nightVision)
/*  85 */       this.player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 99999, 1, false, false)); 
/*  86 */     this.player.sendMessage((nightVision ? "§a你开启了" : "§c你关闭了") + "夜视效果!");
/*  87 */     saveData();
/*     */   }
/*     */   
/*     */   public void setAutoFirstPerson(boolean autoFirstPerson) {
/*  91 */     this.autoFirstPerson = autoFirstPerson;
/*  92 */     this.player.sendMessage(autoFirstPerson ? "§a你默认将会使用第一人称旁观模式!" : "§c你默认将会使用第三人称旁观模式!");
/*  93 */     saveData();
/*     */   }
/*     */   
/*     */   public void setHideOthers(boolean hideOthers) {
/*  97 */     this.hideOthers = hideOthers;
/*  98 */     this.player.sendMessage(hideOthers ? "§c你现在不会看到其它的旁观者!" : "§a你现在可以看到其它的旁观者!");
/*     */     
/* 100 */     saveData();
/*     */   }
/*     */   
/*     */   public void setFirstPersonTargeting(boolean firstPersonTargeting) {
/* 104 */     this.firstPersonTargeting = firstPersonTargeting;
/* 105 */     PlayerData pd = BedWars.getInstance().getPlayerManager().get(this.player);
/* 106 */     if (firstPersonTargeting) {
/* 107 */       pd.getPlayer().setGameMode(GameMode.SPECTATOR);
/* 108 */       pd.getPlayer().teleport((Entity)this.target);
/* 109 */       pd.getPlayer().setSpectatorTarget((Entity)this.target);
/* 110 */       TitleUtils.sendFullTitle(pd.getPlayer(), Integer.valueOf(0), Integer.valueOf(40), Integer.valueOf(0), "§a正在旁观§7 " + LuckPermsUtil.getPrefixColor(this.target) + this.target.getName(), "§a点击左键打开菜单 §c按Shift键退出");
/*     */       
/*     */       return;
/*     */     } 
/* 114 */     pd.getPlayer().setGameMode(GameMode.SURVIVAL);
/* 115 */     PlayerUtils.setFly(pd);
/* 116 */     TitleUtils.sendFullTitle(pd.getPlayer(), Integer.valueOf(0), Integer.valueOf(40), Integer.valueOf(0), "§e已退出旁观模式.", "");
/*     */   }
/*     */   
/*     */   public void setTarget(Player target, boolean tp) {
/* 120 */     this.target = target;
/* 121 */     if (tp) {
/* 122 */       this.player.teleport(target.getLocation().add(0.0D, 0.5D, 0.0D));
/* 123 */       this.player.sendMessage("§a已成功传送到" + 
/* 124 */           BedWars.getInstance().getPlayerManager().get(target).getTeam().getTeamType().getChatColor() + target.getName() + "§a的位置!");
/*     */     } 
/*     */     
/* 127 */     if (this.autoFirstPerson)
/* 128 */       setFirstPersonTargeting(true); 
/*     */   }
/*     */   
/*     */   public void startTask() {
/* 132 */     if (this.task != null)
/* 133 */       return;  final PlayerData pd = BedWars.getInstance().getPlayerManager().get(this.player);
/*     */     
/* 135 */     this
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
/* 171 */       .task = (new BukkitRunnable() { public void run() { String msg; if (pd.getSpecGame() != null) for (PlayerData playerData : pd.getSpecGame().getSpecs()) { if (playerData.getState() != PlayerState.SPECING) continue;  if (SpecOther.this.hideOthers) { pd.getPlayer().hidePlayer(playerData.getPlayer()); continue; }  pd.getPlayer().showPlayer(playerData.getPlayer()); }   if (SpecOther.this.target == null) return;  if (SpecOther.this.autoTeleport && !SpecOther.this.firstPersonTargeting) { int i = (int)SpecOther.this.target.getLocation().distance(SpecOther.this.player.getLocation()); if (i >= 15) SpecOther.this.player.teleport(SpecOther.this.target.getLocation().add(0.0D, 0.5D, 0.0D));  }  PlayerData td = BedWars.getInstance().getPlayerManager().get(SpecOther.this.target); if (td.getState() != PlayerState.FIGHTING) { ActionBarUtils.sendActionBar(SpecOther.this.player, "§c目标已丢失!"); SpecOther.this.target = null; return; }  if (SpecOther.this.firstPersonTargeting) { msg = "§f目标§7: §a§l" + td.getPlayer().getName() + " §f血量: §a§l" + ((int)td.getPlayer().getHealth() * 5) + "% §a点击左键打开菜单 §c按Shift键退出"; } else { msg = "§f目标: §a§l" + td.getPlayer().getName() + " §f血量: §a§l" + ((int)td.getPlayer().getHealth() * 5) + "% §f距离: §a§l" + String.format("%.1f", new Object[] { Double.valueOf(this.this$0.player.getLocation().distance(this.this$0.target.getLocation())) }) + "m"; }  ActionBarUtils.sendActionBar(pd.getPlayer(), msg); } }).runTaskTimer((Plugin)BedWars.getInstance(), 0L, 5L);
/*     */   }
/*     */   
/*     */   public void cancelTask() {
/* 175 */     if (this.task != null) {
/* 176 */       this.task.cancel();
/* 177 */       this.task = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public enum SpecItems
/*     */   {
/* 183 */     AUTO_TELEPORT((String)Material.COMPASS, Material.COMPASS, 20),
/* 184 */     NIGHT_VISION((String)Material.EYE_OF_ENDER, Material.ENDER_PEARL, 21),
/* 185 */     AUTO_FIRST_PERSON((String)Material.WATCH, Material.WATCH, 23),
/* 186 */     HIDE_OTHERS((String)Material.REDSTONE, Material.GLOWSTONE_DUST, 24);
/*     */     final Material enable; final Material disable; final int slot;
/* 188 */     public Material getEnable() { return this.enable; }
/* 189 */     public Material getDisable() { return this.disable; } public int getSlot() {
/* 190 */       return this.slot;
/*     */     }
/*     */     SpecItems(Material enable, Material disable, int slot) {
/* 193 */       this.enable = enable;
/* 194 */       this.disable = disable;
/* 195 */       this.slot = slot;
/*     */     }
/*     */     
/*     */     public ItemStack toItemStack(Player player) {
/* 199 */       SpecOther spec = BedWars.getInstance().getPlayerManager().get(player).getSpecOther();
/* 200 */       return (new ItemBuilder(getSpecBoolean(spec) ? this.disable : this.enable))
/* 201 */         .setName(getName(spec)).setLore(getLore(spec)).toItemStack();
/*     */     }
/*     */     
/*     */     public void clickableItem(Player player) {
/* 205 */       SpecOther spec = BedWars.getInstance().getPlayerManager().get(player).getSpecOther();
/* 206 */       switch (this) {
/*     */         case AUTO_TELEPORT:
/* 208 */           spec.setAutoTeleport(!spec.autoTeleport);
/*     */           break;
/*     */         
/*     */         case NIGHT_VISION:
/* 212 */           spec.setNightVision(!spec.nightVision);
/*     */           break;
/*     */         
/*     */         case AUTO_FIRST_PERSON:
/* 216 */           spec.setAutoFirstPerson(!spec.autoFirstPerson);
/*     */           break;
/*     */         
/*     */         case HIDE_OTHERS:
/* 220 */           spec.setHideOthers(!spec.hideOthers);
/*     */           break;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean getSpecBoolean(SpecOther other) {
/* 227 */       switch (this) {
/*     */         case AUTO_TELEPORT:
/* 229 */           return other.autoTeleport;
/*     */         case NIGHT_VISION:
/* 231 */           return other.nightVision;
/*     */         case AUTO_FIRST_PERSON:
/* 233 */           return other.autoFirstPerson;
/*     */         case HIDE_OTHERS:
/* 235 */           return other.hideOthers;
/*     */       } 
/* 237 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     String getName(SpecOther other) {
/* 242 */       switch (this) {
/*     */         case AUTO_TELEPORT:
/* 244 */           return (other.autoTeleport ? "§c停" : "§a启") + "用自动传送";
/*     */         case NIGHT_VISION:
/* 246 */           return (other.nightVision ? "§c停" : "§a启") + "用夜视";
/*     */         case AUTO_FIRST_PERSON:
/* 248 */           return (other.autoFirstPerson ? "§c停" : "§a启") + "用第一人称旁观";
/*     */         case HIDE_OTHERS:
/* 250 */           return (other.hideOthers ? "§c隐藏" : "§a显示") + "旁观者";
/*     */       } 
/* 252 */       return "";
/*     */     }
/*     */ 
/*     */     
/*     */     String[] getLore(SpecOther other) {
/* 257 */       switch (this) {
/*     */         case AUTO_TELEPORT:
/* 259 */           return new String[] { "§7点击" + (other.autoTeleport ? "停" : "启") + "用自动传送!" };
/*     */ 
/*     */         
/*     */         case NIGHT_VISION:
/* 263 */           return new String[] { "§7点击" + (other.nightVision ? "停" : "启") + "用夜视!" };
/*     */ 
/*     */         
/*     */         case AUTO_FIRST_PERSON:
/* 267 */           return new String[] { "§7点击确定使用指南针时", "§7" + (other.autoFirstPerson ? "停" : "启") + "用第一人称旁观!", "§7你也可以右键点击一位玩家", "§7来启用第一人称旁观." };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case HIDE_OTHERS:
/* 274 */           return new String[] { "§7点击" + (other.hideOthers ? "隐藏" : "显示") + "其它旁观者!" };
/*     */       } 
/*     */ 
/*     */       
/* 278 */       return new String[0];
/*     */     }
/*     */   }
/*     */ }