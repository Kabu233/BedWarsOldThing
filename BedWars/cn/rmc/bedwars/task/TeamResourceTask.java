/*     */ package cn.rmc.bedwars.task;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import cn.rmc.bedwars.BedWars;
/*     */ import cn.rmc.bedwars.enums.game.GameMode;
/*     */ import cn.rmc.bedwars.enums.game.GameState;
/*     */ import cn.rmc.bedwars.enums.game.Resource;
/*     */ import cn.rmc.bedwars.enums.shop.TeamUpgrade;
/*     */ import cn.rmc.bedwars.game.Game;
/*     */ import cn.rmc.bedwars.game.Team;
/*     */ import cn.rmc.bedwars.runnable.ResourceSpawnRunnable;
/*     */ import cn.rmc.bedwars.utils.Probability;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ 
/*     */ public class TeamResourceTask extends BukkitRunnable {
/*  20 */   HashMap<Team, Integer> iron = new HashMap<>(); Game g;
/*  21 */   HashMap<Team, Integer> gold = new HashMap<>();
/*  22 */   HashMap<Team, Integer> emerald = new HashMap<>();
/*     */   
/*     */   public TeamResourceTask(Game g) {
/*  25 */     this.g = g;
/*  26 */     for (Iterator<Team> iterator = g.getTeams().iterator(); iterator.hasNext(); ) { Team team = iterator.next();
/*  27 */       Arrays.<Resource>asList(new Resource[] { Resource.IRON, Resource.GOLD }).forEach(resource -> gen(resource, team));
/*  28 */       add(team); }
/*     */   
/*     */   }
/*     */   
/*     */   public void run() {
/*  33 */     if (this.g.getState() == GameState.ENDING) {
/*  34 */       cancel();
/*     */     }
/*  36 */     for (Iterator<Team> iterator = this.g.getTeams().iterator(); iterator.hasNext(); ) { Team team = iterator.next();
/*  37 */       int forgelevel = ((Integer)team.getTeamUpgrade().get(TeamUpgrade.Forge)).intValue();
/*  38 */       int Iron = 999999;
/*  39 */       int Gold = 999999;
/*  40 */       int Emerald = 999999;
/*  41 */       add(team);
/*  42 */       switch (this.g.getGameMode()) {
/*     */         
/*     */         case EIGHT_ONE:
/*  45 */           switch (forgelevel) {
/*     */             case 0:
/*  47 */               Iron = 40;
/*  48 */               Gold = 160;
/*  49 */               Emerald = -1;
/*     */               break;
/*     */             case 1:
/*  52 */               Iron = 35;
/*  53 */               Gold = 140;
/*  54 */               Emerald = -1;
/*     */               break;
/*     */             case 2:
/*  57 */               Iron = 30;
/*  58 */               Gold = 130;
/*  59 */               Emerald = -1;
/*     */               break;
/*     */             case 3:
/*  62 */               Iron = 20;
/*  63 */               Gold = 120;
/*  64 */               Emerald = 600;
/*     */               break;
/*     */             case 4:
/*  67 */               Iron = 15;
/*  68 */               Gold = 110;
/*  69 */               Emerald = 400;
/*     */               break;
/*     */           } 
/*     */           break;
/*     */         default:
/*  74 */           switch (forgelevel) {
/*     */             case 0:
/*  76 */               Iron = 20;
/*  77 */               Gold = 120;
/*  78 */               Emerald = -1;
/*     */               break;
/*     */             case 1:
/*  81 */               Iron = 18;
/*  82 */               Gold = 108;
/*  83 */               Emerald = -1;
/*     */               break;
/*     */             case 2:
/*  86 */               Iron = 16;
/*  87 */               Gold = 96;
/*  88 */               Emerald = -1;
/*     */               break;
/*     */             case 3:
/*  91 */               Iron = 14;
/*  92 */               Gold = 76;
/*  93 */               Emerald = 600;
/*     */               break;
/*     */             case 4:
/*  96 */               Iron = 13;
/*  97 */               Gold = 67;
/*  98 */               Emerald = 400;
/*     */               break;
/*     */           } 
/*     */           
/*     */           break;
/*     */       } 
/* 104 */       if (((Integer)this.iron.get(team)).intValue() >= Iron) {
/* 105 */         gen(Resource.IRON, team);
/* 106 */         if (Probability.probability(40)) {
/* 107 */           Bukkit.getScheduler().runTaskLater((Plugin)BedWars.getInstance(), () -> gen(Resource.IRON, team), 3L);
/*     */         }
/* 109 */         this.iron.remove(team);
/*     */       } 
/*     */       
/* 112 */       if (((Integer)this.gold.get(team)).intValue() >= Gold) {
/* 113 */         gen(Resource.GOLD, team);
/* 114 */         this.gold.remove(team);
/*     */       } 
/*     */       
/* 117 */       if (Emerald != -1 && ((Integer)this.emerald.get(team)).intValue() >= Emerald) {
/* 118 */         gen(Resource.EMERALD, team);
/* 119 */         this.emerald.remove(team);
/*     */       }  }
/*     */   
/*     */   }
/*     */   private void gen(Resource res, Team team) {
/* 124 */     (new ResourceSpawnRunnable(res, team)).runTask((Plugin)BedWars.getInstance());
/*     */   }
/*     */   
/*     */   private void add(Team team) {
/* 128 */     if (this.iron.containsKey(team)) {
/* 129 */       this.iron.put(team, Integer.valueOf(((Integer)this.iron.get(team)).intValue() + 1));
/*     */     } else {
/* 131 */       this.iron.put(team, Integer.valueOf(1));
/*     */     } 
/* 133 */     if (this.gold.containsKey(team)) {
/* 134 */       this.gold.put(team, Integer.valueOf(((Integer)this.gold.get(team)).intValue() + 1));
/*     */     } else {
/* 136 */       this.gold.put(team, Integer.valueOf(1));
/*     */     } 
/* 138 */     if (this.emerald.containsKey(team)) {
/* 139 */       this.emerald.put(team, Integer.valueOf(((Integer)this.emerald.get(team)).intValue() + 1));
/*     */     } else {
/* 141 */       this.emerald.put(team, Integer.valueOf(1));
/*     */     } 
/*     */   }
/*     */ }