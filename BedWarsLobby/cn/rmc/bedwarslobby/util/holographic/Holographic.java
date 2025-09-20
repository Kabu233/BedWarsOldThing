/*     */ package cn.rmc.bedwarslobby.util.holographic;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.entity.Player;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Holographic
/*     */ {
/*     */   Location location;
/*     */   
/*     */   public Location getLocation() {
/*  17 */     return this.location;
/*  18 */   } List<HolographicLine> lines; Double offset = Double.valueOf(0.4D); List<Player> players; public Double getOffset() { return this.offset; }
/*  19 */   public List<HolographicLine> getLines() { return this.lines; } public List<Player> getPlayers() {
/*  20 */     return this.players;
/*     */   }
/*     */   public Holographic(Location loc) {
/*  23 */     this.location = loc;
/*  24 */     this.lines = new ArrayList<>();
/*  25 */     this.players = new ArrayList<>();
/*     */   }
/*     */   
/*     */   public void addLine(String title) {
/*  29 */     HolographicLine line = new HolographicLine(this.location.clone().add(0.0D, -this.offset.doubleValue() * this.lines.size(), 0.0D), title);
/*  30 */     this.lines.add(line);
/*  31 */     this.players.forEach(line::display);
/*     */   }
/*     */   
/*     */   public void setLine(Integer slot, String title) {
/*  35 */     ((HolographicLine)this.lines.get(slot.intValue())).setTitle(title);
/*     */   }
/*     */   
/*     */   public void setLines(List<String> lines) {
/*  39 */     this.lines.forEach(lines::remove);
/*  40 */     this.lines.clear();
/*  41 */     lines.forEach(this::addLine);
/*     */   }
/*     */   
/*     */   public void removeLine(Integer slot) {
/*  45 */     ((HolographicLine)this.lines.get(slot.intValue())).remove();
/*  46 */     this.lines.remove(slot);
/*  47 */     sort();
/*     */   }
/*     */   
/*     */   public void display(Player p) {
/*  51 */     if (!this.players.contains(p)) {
/*  52 */       this.players.add(p);
/*     */     }
/*     */     
/*  55 */     this.lines.forEach(holographicLine -> holographicLine.display(p));
/*     */   }
/*     */   
/*     */   public void destory(Player p) {
/*  59 */     this.players.remove(p);
/*  60 */     this.lines.forEach(holographicLine -> holographicLine.destroy(p));
/*     */   }
/*     */   
/*     */   public void destoryAll() {
/*  64 */     for (HolographicLine line : this.lines) {
/*  65 */       for (Player player : this.players) {
/*  66 */         line.destroy(player);
/*     */       }
/*     */     } 
/*     */     
/*  70 */     this.players.clear();
/*     */   }
/*     */   
/*     */   public void remove() {
/*  74 */     destoryAll();
/*     */     
/*  76 */     for (HolographicLine line : this.lines) {
/*  77 */       line.remove();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSame(int i, Player player) {
/*  83 */     for (HolographicLine line : this.lines) {
/*  84 */       int entityId = line.getEntityId(player);
/*  85 */       if (i == entityId) {
/*  86 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  90 */     return false;
/*     */   }
/*     */   
/*     */   private void sort() {
/*  94 */     int i = 0;
/*     */     
/*  96 */     for (HolographicLine line : this.lines) {
/*  97 */       line.teleport(this.location.clone().add(0.0D, -this.offset.doubleValue() * this.lines.size(), 0.0D));
/*  98 */       i++;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void refreshLines(List<String> newLines) {
/* 104 */     if (newLines.size() == this.lines.size()) {
/* 105 */       for (int i = 0; i < newLines.size(); i++) {
/* 106 */         ((HolographicLine)this.lines.get(i)).setTitle(newLines.get(i));
/*     */       }
/*     */     }
/* 109 */     else if (newLines.size() > this.lines.size()) {
/* 110 */       int i; for (i = 0; i < this.lines.size(); i++) {
/* 111 */         ((HolographicLine)this.lines.get(i)).setTitle(newLines.get(i));
/*     */       }
/* 113 */       for (i = this.lines.size(); i < newLines.size(); i++) {
/* 114 */         addLine(newLines.get(i));
/*     */       }
/*     */     } else {
/*     */       
/* 118 */       for (int i = 0; i < newLines.size(); i++) {
/* 119 */         ((HolographicLine)this.lines.get(i)).setTitle(newLines.get(i));
/*     */       }
/* 121 */       while (this.lines.size() > newLines.size())
/* 122 */         removeLine(Integer.valueOf(this.lines.size() - 1)); 
/*     */     } 
/*     */   }
/*     */ }