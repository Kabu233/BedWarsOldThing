/*     */ package cn.rmc.bedwarslobby.util.holographic.armor;
/*     */ 
/*     */ import com.comphenix.protocol.PacketType;
/*     */ import com.comphenix.protocol.ProtocolLibrary;
/*     */ import com.comphenix.protocol.events.PacketContainer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.stream.Collectors;
/*     */ import cn.rmc.bedwarslobby.BedWarsLobby;
/*     */ import cn.rmc.bedwarslobby.loot.enums.ShopSkin;
/*     */ import cn.rmc.bedwarslobby.util.ItemUtil;
/*     */ import cn.rmc.bedwarslobby.util.PacketUtils;
/*     */ import cn.rmc.bedwarslobby.util.holographic.HolographicLine;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ import org.bukkit.scheduler.BukkitTask;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ArmorHolographic
/*     */ {
/*     */   Location location;
/*  32 */   Double offset = Double.valueOf(0.4D);
/*     */   ItemHolographicLine item;
/*     */   HolographicLine upLine;
/*     */   List<HolographicLine> downLines;
/*     */   List<Player> players;
/*     */   List<BukkitTask> tasks;
/*     */   ClickHandler clickHandler;
/*     */   
/*     */   public ArmorHolographic(Location loc, ItemHolographicLine.ShowType type) {
/*  41 */     this.location = loc.clone();
/*  42 */     this.item = new ItemHolographicLine(this.location, type);
/*  43 */     this.downLines = new ArrayList<>();
/*  44 */     this.players = new ArrayList<>();
/*  45 */     this.tasks = new ArrayList<>();
/*     */   }
/*     */   
/*     */   public void setItem(ItemStack is) {
/*  49 */     this.item.setItem(is);
/*  50 */     this.players.forEach(player -> this.item.display(player));
/*     */   }
/*     */   
/*     */   public void addUpLine(String text) {
/*  54 */     this.upLine = new HolographicLine(this.location.clone().add(0.0D, 0.005D, 0.0D), text);
/*  55 */     this.players.forEach(player -> this.upLine.display(player));
/*     */   }
/*     */   
/*     */   public void remUpLine() {
/*  59 */     this.upLine.remove();
/*     */   }
/*     */   
/*     */   public void addDownLine(String text) {
/*  63 */     if (this.downLines.size() == 0) {
/*  64 */       HolographicLine line = new HolographicLine(this.location.clone().add(0.0D, -1.14D, 0.0D), text);
/*  65 */       this.downLines.add(line);
/*  66 */       this.players.forEach(line::display);
/*     */     } else {
/*  68 */       HolographicLine line = new HolographicLine(((HolographicLine)this.downLines.get(this.downLines.size() - 1)).getLocation().clone().add(0.0D, -0.35D, 0.0D), text);
/*  69 */       this.downLines.add(line);
/*  70 */       this.players.forEach(line::display);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDownLine(Integer slot, String text) {
/*  76 */     ((HolographicLine)this.downLines.get(slot.intValue())).setTitle(text);
/*     */   }
/*     */   
/*     */   public void display(Player p) {
/*  80 */     if (!this.players.contains(p)) {
/*  81 */       this.players.add(p);
/*     */     }
/*     */     
/*  84 */     if (this.item != null) {
/*  85 */       this.item.display(p);
/*     */     }
/*     */     
/*  88 */     if (this.upLine != null) {
/*  89 */       this.upLine.display(p);
/*     */     }
/*     */     
/*  92 */     if (this.downLines.size() != 0) {
/*  93 */       this.downLines.forEach(holographicLine -> holographicLine.display(p));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void destroy(Player p) {
/*  99 */     this.players.remove(p);
/* 100 */     if (this.item != null) {
/* 101 */       this.item.destroy(p);
/*     */     }
/*     */     
/* 104 */     if (this.upLine != null) {
/* 105 */       this.upLine.destroy(p);
/*     */     }
/*     */     
/* 108 */     if (this.downLines.size() != 0) {
/* 109 */       this.downLines.forEach(holographicLine -> holographicLine.destroy(p));
/*     */     }
/*     */     
/* 112 */     this.tasks.forEach(BukkitTask::cancel);
/*     */   }
/*     */   
/*     */   public void destoryAll() {
/* 116 */     for (Player player : this.players) {
/* 117 */       this.item.destroy(player);
/* 118 */       if (this.upLine != null) {
/* 119 */         this.upLine.destroy(player);
/*     */       }
/*     */       
/* 122 */       for (HolographicLine line : this.downLines) {
/* 123 */         line.destroy(player);
/*     */       }
/*     */       
/* 126 */       this.tasks.forEach(BukkitTask::cancel);
/*     */     } 
/*     */     
/* 129 */     this.players.clear();
/*     */   }
/*     */   
/*     */   public void remove() {
/* 133 */     destoryAll();
/* 134 */     this.item.remove();
/* 135 */     if (this.upLine != null && !this.upLine.isRemoved()) {
/* 136 */       this.upLine.remove();
/*     */     }
/*     */     
/* 139 */     for (HolographicLine line : this.downLines) {
/* 140 */       line.remove();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSame(int i, Player player) {
/* 146 */     if (this.item != null && i == this.item.getEntityId(player))
/* 147 */       return true; 
/* 148 */     if (this.upLine != null && i == this.upLine.getEntityId(player)) {
/* 149 */       return true;
/*     */     }
/* 151 */     for (HolographicLine line : this.downLines) {
/* 152 */       int entityId = line.getEntityId(player);
/* 153 */       if (i == entityId) {
/* 154 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 158 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isLooking(Player player) {
/* 163 */     return PacketUtils.checkLocLooking(player, this.location);
/*     */   }
/*     */   
/*     */   public void addLookingTask(final Player player, final String upText, final Sound sound) {
/* 167 */     this.tasks.add((new BukkitRunnable() {
/* 168 */           final AtomicBoolean move = new AtomicBoolean(false);
/*     */           
/*     */           public void run() {
/* 171 */             if (!ArmorHolographic.this.players.contains(player)) {
/* 172 */               cancel();
/* 173 */             } else if (ArmorHolographic.this.item.isRemoved()) {
/* 174 */               cancel();
/*     */             }
/* 176 */             else if (!this.move.get()) {
/* 177 */               if (ArmorHolographic.this.isLooking(player)) {
/* 178 */                 if (upText != null) {
/* 179 */                   ArmorHolographic.this.addUpLine(upText);
/* 180 */                   PacketUtils.teleportPacket(ArmorHolographic.this.upLine.getEntityId(player), PacketUtils.toSelectorLocation(player, ArmorHolographic.this.upLine.getLocation()), player);
/*     */                 } 
/*     */                 
/* 183 */                 ArmorHolographic.this.moveTo(player);
/* 184 */                 if (sound != null) {
/* 185 */                   if (sound == Sound.ITEM_PICKUP) {
/* 186 */                     player.playSound(ArmorHolographic.this.location, sound, 1.0F, 1.0F);
/*     */                   } else {
/* 188 */                     player.playSound(ArmorHolographic.this.location, sound, 5.0F, 5.0F);
/*     */                   } 
/*     */                 }
/*     */                 
/* 192 */                 this.move.set(true);
/*     */               } 
/* 194 */             } else if (!ArmorHolographic.this.isLooking(player)) {
/* 195 */               if (upText != null) {
/* 196 */                 ArmorHolographic.this.remUpLine();
/*     */               }
/*     */               
/* 199 */               ArmorHolographic.this.back(player);
/* 200 */               this.move.set(false);
/*     */             
/*     */             }
/*     */           
/*     */           }
/* 205 */         }).runTaskTimer((Plugin)BedWarsLobby.getInstance(), 0L, 1L));
/*     */   }
/*     */   
/*     */   public void addLookingTaskByShop(final Player player, final String upText, final Sound sound) {
/* 209 */     final List<String> list = (List<String>)Arrays.<ShopSkin>stream(ShopSkin.values()).map(ShopSkin::getBase64).collect(Collectors.toList());
/* 210 */     this.tasks.add((new BukkitRunnable() {
/* 211 */           int i = 0;
/* 212 */           final AtomicBoolean move = new AtomicBoolean(false);
/*     */           
/*     */           public void run() {
/*     */             try {
/* 216 */               if (!ArmorHolographic.this.players.contains(player)) {
/* 217 */                 cancel();
/* 218 */               } else if (ArmorHolographic.this.item.isRemoved()) {
/* 219 */                 cancel();
/*     */               } else {
/* 221 */                 if (this.i == list.size()) {
/* 222 */                   this.i = 0;
/*     */                 }
/*     */                 
/* 225 */                 PacketContainer equipShop = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.ENTITY_EQUIPMENT);
/* 226 */                 equipShop.getIntegers().write(0, Integer.valueOf(ArmorHolographic.this.item.getEntityId(player)));
/* 227 */                 equipShop.getIntegers().write(1, Integer.valueOf(4));
/* 228 */                 equipShop.getItemModifier().write(0, ItemUtil.getCustomSkull(list.get(this.i)));
/* 229 */                 ProtocolLibrary.getProtocolManager().sendServerPacket(player, equipShop);
/* 230 */                 this.i++;
/* 231 */                 if (!this.move.get()) {
/* 232 */                   if (ArmorHolographic.this.isLooking(player)) {
/* 233 */                     if (upText != null) {
/* 234 */                       ArmorHolographic.this.addUpLine(upText);
/* 235 */                       PacketUtils.teleportPacket(ArmorHolographic.this.upLine.getEntityId(player), PacketUtils.toSelectorLocation(player, ArmorHolographic.this.upLine.getLocation()), player);
/*     */                     } 
/*     */                     
/* 238 */                     ArmorHolographic.this.moveTo(player);
/* 239 */                     if (sound != null) {
/* 240 */                       player.playSound(ArmorHolographic.this.location, sound, 5.0F, 20.0F);
/*     */                     }
/*     */                     
/* 243 */                     this.move.set(true);
/*     */                   } 
/* 245 */                 } else if (!ArmorHolographic.this.isLooking(player)) {
/* 246 */                   if (upText != null) {
/* 247 */                     ArmorHolographic.this.remUpLine();
/*     */                   }
/*     */                   
/* 250 */                   ArmorHolographic.this.back(player);
/* 251 */                   this.move.set(false);
/*     */                 } 
/*     */               } 
/*     */             } catch (Throwable $ex) {
/*     */               throw $ex;
/* 256 */             }  } }).runTaskTimer((Plugin)BedWarsLobby.getInstance(), 0L, 3L));
/*     */   }
/*     */   
/*     */   private void moveTo(Player player) {
/* 260 */     this.item.teleport(PacketUtils.toSelectorLocation(player, this.item.getLocation()), player);
/*     */     
/* 262 */     for (HolographicLine downLine : this.downLines) {
/* 263 */       PacketUtils.teleportPacket(downLine.getEntityId(player), PacketUtils.toSelectorLocation(player, downLine.getLocation()), player);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void back(Player player) {
/* 269 */     this.item.teleport(this.item.getLocation(), player);
/*     */     
/* 271 */     for (HolographicLine downLine : this.downLines) {
/* 272 */       PacketUtils.teleportPacket(downLine.getEntityId(player), downLine.getLocation(), player);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void addClickHandler(ClickHandler clickHandler) {
/* 278 */     this.clickHandler = clickHandler;
/*     */   }
/*     */   
/*     */   public Location getLocation() {
/* 282 */     return this.location;
/*     */   }
/*     */   
/*     */   public Double getOffset() {
/* 286 */     return this.offset;
/*     */   }
/*     */   
/*     */   public ItemHolographicLine getItem() {
/* 290 */     return this.item;
/*     */   }
/*     */   
/*     */   public HolographicLine getUpLine() {
/* 294 */     return this.upLine;
/*     */   }
/*     */   
/*     */   public List<HolographicLine> getDownLines() {
/* 298 */     return this.downLines;
/*     */   }
/*     */   
/*     */   public List<Player> getPlayers() {
/* 302 */     return this.players;
/*     */   }
/*     */   
/*     */   public List<BukkitTask> getTasks() {
/* 306 */     return this.tasks;
/*     */   }
/*     */   
/*     */   public ClickHandler getClickHandler() {
/* 310 */     return this.clickHandler;
/*     */   }
/*     */   
/*     */   public static interface ClickHandler {
/*     */     void onClick(Player param1Player, int param1Int);
/*     */   }
/*     */ }