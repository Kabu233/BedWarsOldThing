/*     */ package cn.rmc.bedwars.task;
/*     */ 
/*     */ import cn.rmc.bedwars.BedWars;
/*     */ import cn.rmc.bedwars.enums.game.EventType;
/*     */ import cn.rmc.bedwars.enums.game.GameState;
/*     */ import cn.rmc.bedwars.enums.game.Resource;
/*     */ import cn.rmc.bedwars.game.Game;
/*     */ import cn.rmc.bedwars.utils.MathUtils;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ 
/*     */ public class EventTimerTask
/*     */   extends BukkitRunnable {
/*     */   Game g;
/*     */   Resource need;
/*  17 */   Boolean bedbroken = Boolean.valueOf(false);
/*  18 */   Boolean suddendeath = Boolean.valueOf(false);
/*  19 */   Boolean gameover = Boolean.valueOf(false);
/*  20 */   int i = 0; public void setI(int i) { this.i = i; }
/*     */   
/*  22 */   final int normal = 361;
/*  23 */   final int end = 601;
/*  24 */   int bed = 601;
/*     */   public EventTimerTask(Game g) {
/*  26 */     this.g = g;
/*  27 */     if (g.getGameMode().name().contains("RUSH")) {
/*  28 */       this.bedbroken = Boolean.valueOf(true);
/*  29 */       this.bed = 901;
/*  30 */       this.i = this.bed;
/*  31 */       g.setEventDisPlayName("床自毁");
/*  32 */       g.setEventType(EventType.BEDGONE);
/*  33 */       g.setEventUpgradeType(null);
/*  34 */       g.setEventUpgradeLevel(0);
/*  35 */       this.suddendeath = Boolean.valueOf(true);
/*     */     } else {
/*  37 */       this.need = Resource.DIAMOND;
/*  38 */       g.setEventType(EventType.Resource);
/*  39 */       g.setEventUpgradeLevel(1);
/*  40 */       g.setEventUpgradeType(this.need);
/*  41 */       g.setEventDisPlayName(this.need.getDisplayName() + "生成点" + MathUtils.toRome(2) + "级");
/*  42 */       this.i = 361;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void run() {
/*  48 */     if (this.g.getState() == GameState.ENDING) {
/*  49 */       cancel();
/*     */     }
/*  51 */     if (this.g.getState() == GameState.ENDING) cancel(); 
/*  52 */     this.i--;
/*  53 */     this.g.setEventLeftTime(this.i);
/*  54 */     BedWars.getInstance().getScoreBoardManager().updateAll();
/*  55 */     if (this.i <= 1)
/*  56 */       Bukkit.getScheduler().runTaskLater((Plugin)BedWars.getInstance(), this::updateTask, 19L); 
/*     */   }
/*     */   
/*     */   public void updateTask() {
/*  60 */     this.g.goEvent();
/*  61 */     int diamond = ((Integer)this.g.getResGenLevel().get(Resource.DIAMOND)).intValue();
/*  62 */     int emerald = ((Integer)this.g.getResGenLevel().get(Resource.EMERALD)).intValue();
/*  63 */     if (this.gameover.booleanValue()) {
/*  64 */       this.i = 601;
/*  65 */       this.g.setEventDisPlayName("游戏结束");
/*  66 */       this.g.setEventType(EventType.GAMEEND);
/*  67 */       this.g.setEventUpgradeType(null);
/*  68 */       this.g.setEventUpgradeLevel(0);
/*     */       return;
/*     */     } 
/*  71 */     if (this.suddendeath.booleanValue()) {
/*  72 */       this.i = 601;
/*  73 */       this.g.setEventDisPlayName("绝杀模式");
/*  74 */       this.g.setEventType(EventType.SUDDENDEATH);
/*  75 */       this.g.setEventUpgradeType(null);
/*  76 */       this.g.setEventUpgradeLevel(0);
/*  77 */       this.gameover = Boolean.valueOf(true);
/*     */       return;
/*     */     } 
/*  80 */     if (this.bedbroken.booleanValue()) {
/*  81 */       this.i = this.bed;
/*  82 */       this.g.setEventDisPlayName("床自毁");
/*  83 */       this.g.setEventType(EventType.BEDGONE);
/*  84 */       this.g.setEventUpgradeType(null);
/*  85 */       this.g.setEventUpgradeLevel(0);
/*  86 */       this.suddendeath = Boolean.valueOf(true);
/*     */       return;
/*     */     } 
/*  89 */     if (emerald >= diamond) {
/*  90 */       this.need = Resource.DIAMOND;
/*  91 */       this.g.setEventDisPlayName(this.need.getDisplayName() + "生成点" + MathUtils.toRome(diamond + 2) + "级");
/*  92 */       this.g.setEventUpgradeType(this.need);
/*  93 */       this.g.setEventUpgradeLevel(diamond + 1);
/*     */     } else {
/*  95 */       this.need = Resource.EMERALD;
/*  96 */       this.g.setEventDisPlayName(this.need.getDisplayName() + "生成点" + MathUtils.toRome(emerald + 2) + "级");
/*  97 */       this.g.setEventUpgradeType(this.need);
/*  98 */       this.g.setEventUpgradeLevel(emerald + 1);
/*  99 */       if (emerald >= 1) {
/* 100 */         this.bedbroken = Boolean.valueOf(true);
/*     */       }
/*     */     } 
/* 103 */     this.i = 361;
/*     */   }
/*     */ }