/*     */ package cn.rmc.bedwars.game.dream.ultimate.ultimates;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import cn.rmc.bedwars.BedWars;
/*     */ import cn.rmc.bedwars.enums.game.PlayerState;
/*     */ import cn.rmc.bedwars.game.Game;
/*     */ import cn.rmc.bedwars.game.PlayerData;
/*     */ import cn.rmc.bedwars.game.Team;
/*     */ import cn.rmc.bedwars.utils.item.ItemBuilder;
/*     */ import cn.rmc.bedwars.utils.player.InventoryUtils;
/*     */ import net.minecraft.server.v1_8_R3.MathHelper;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.block.BlockFace;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.event.block.Action;
/*     */ import org.bukkit.event.block.BlockPlaceEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ import org.bukkit.util.Vector;
/*     */ 
/*     */ public class Builder extends UltimateBasic {
/*     */   int mode;
/*     */   
/*     */   public Builder(UUID uuid) {
/*  34 */     super(uuid, UltimateType.BUILDER);
/*     */ 
/*     */     
/*  37 */     this.mode = 1;
/*     */   }
/*     */   
/*     */   public ItemStack getItem() {
/*  41 */     return (new ItemBuilder(Material.BRICK)).setName("§a建筑师工具 §7-§e " + ((this.mode == 1) ? "建桥" : "建墙"))
/*  42 */       .addLoreLine("§7轻松地建造桥梁和墙壁!").addLoreLine("§7被动地生成羊毛.").toItemStack();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockPlace(BlockPlaceEvent e) {
/*  49 */     if (!isSame(e.getItemInHand()))
/*  50 */       return;  e.setBuild(false);
/*  51 */     e.setCancelled(true);
/*  52 */     if (this.mode == 1) {
/*  53 */       Vector vector = e.getBlock().getLocation().subtract(e.getBlockAgainst().getLocation()).toVector();
/*  54 */       if (vector.getY() == 0.0D) {
/*  55 */         buildBridge(e.getBlock(), e.getBlockAgainst());
/*     */       } else {
/*  57 */         buildBridge(e.getBlockAgainst().getRelative(getBlockFace(e.getPlayer().getLocation().getYaw())), e.getBlockAgainst());
/*     */       } 
/*     */     } else {
/*  60 */       buildWall(e.getBlock(), getBlockFace(e.getPlayer().getLocation().getYaw()));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onInteract(PlayerInteractEvent e) {
/*  67 */     if (e.getItem() != null && 
/*  68 */       isSame(e.getPlayer().getItemInHand())) {
/*     */       
/*  70 */       if (e.getAction() == Action.RIGHT_CLICK_BLOCK && 
/*  71 */         e.getClickedBlock() != null && 
/*  72 */         e.getClickedBlock().getType() == Material.BED_BLOCK && 
/*  73 */         getPd().getTeam().getBedloc().distance(e.getClickedBlock().getLocation()) <= 5.0D) {
/*  74 */         e.setCancelled(true);
/*  75 */         packageBed(e.getClickedBlock());
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  81 */       if (e.getAction() == Action.LEFT_CLICK_AIR) {
/*  82 */         this.mode = (this.mode == 1) ? 2 : 1;
/*  83 */         getPlayer().setItemInHand(getItem());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onStart() {
/*  93 */     super.onStart();
/*  94 */     if (this.task == null) {
/*  95 */       this
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 103 */         .task = (new BukkitRunnable() { public void run() { if (Builder.this.getPlayer() == null || !Builder.this.getPlayer().isOnline()) cancel();  if (Builder.this.getPd().getState() != PlayerState.FIGHTING) cancel();  Builder.this.getPlayer().getInventory().addItem(new ItemStack[] { (new ItemBuilder(Material.WOOL, 1)).setDyeColor(this.this$0.getPd().getTeam().getTeamType().getDyeColor()).toItemStack() }); } }).runTaskTimer((Plugin)BedWars.getInstance(), 60L, 60L);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onSpawn(PlayerSpawnEvent e) {
/* 110 */     if (this.task == null) {
/* 111 */       this
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 119 */         .task = (new BukkitRunnable() { public void run() { if (Builder.this.getPlayer() == null || !Builder.this.getPlayer().isOnline()) cancel();  if (Builder.this.getPd().getState() != PlayerState.FIGHTING) cancel();  Builder.this.getPlayer().getInventory().addItem(new ItemStack[] { (new ItemBuilder(Material.WOOL, 1)).setDyeColor(this.this$0.getPd().getTeam().getTeamType().getDyeColor()).toItemStack() }); } }).runTaskTimer((Plugin)BedWars.getInstance(), 60L, 60L);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasRegion(Game g, Block block) {
/* 126 */     boolean hasRegion = false;
/* 127 */     for (Team team : g.getTeams()) {
/* 128 */       if (LocationUtil.hasRegion(block.getLocation(), team.getPos1(), team.getPos2())) {
/* 129 */         hasRegion = true;
/*     */         break;
/*     */       } 
/*     */     } 
/* 133 */     for (ResourceSpawner spawner : g.getSpawners()) {
/* 134 */       if (spawner.getLoc().distance(block.getLocation()) <= 2.0D) {
/* 135 */         hasRegion = true;
/*     */         break;
/*     */       } 
/*     */     } 
/* 139 */     return hasRegion;
/*     */   }
/*     */ 
/*     */   
/*     */   public void buildBridge(Block baseblock, Block against) {
/* 144 */     Vector vector = baseblock.getLocation().subtract(against.getLocation()).toVector();
/* 145 */     List<Location> locs = new ArrayList<>();
/* 146 */     Block block = baseblock;
/* 147 */     for (int i = 0; i <= 9; i++) {
/* 148 */       if (i > 0) block = block.getLocation().add(vector).getBlock(); 
/* 149 */       locs.add(block.getLocation());
/*     */     } 
/* 151 */     build(locs, 3, true);
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
/*     */   public void buildWall(Block block, BlockFace blockFace) {
/*     */     int i;
/* 176 */     ArrayList<Location> air = new ArrayList<>();
/* 177 */     Location loc = block.getLocation();
/*     */     
/* 179 */     switch (blockFace) {
/*     */       
/*     */       case EAST:
/*     */       case WEST:
/* 183 */         for (i = 0; i < 5; i++) {
/* 184 */           air.add(loc.clone().add(0.0D, i, 0.0D));
/* 185 */           air.add(loc.clone().add(0.0D, i, -1.0D));
/* 186 */           air.add(loc.clone().add(0.0D, i, 1.0D));
/* 187 */           air.add(loc.clone().add(0.0D, i, -2.0D));
/* 188 */           air.add(loc.clone().add(0.0D, i, 2.0D));
/*     */         } 
/*     */         break;
/*     */       
/*     */       case SOUTH:
/*     */       case NORTH:
/* 194 */         for (i = 0; i < 5; i++) {
/* 195 */           air.add(loc.clone().add(0.0D, i, 0.0D));
/* 196 */           air.add(loc.clone().add(-1.0D, i, 0.0D));
/* 197 */           air.add(loc.clone().add(1.0D, i, 0.0D));
/* 198 */           air.add(loc.clone().add(-2.0D, i, 0.0D));
/* 199 */           air.add(loc.clone().add(2.0D, i, 0.0D));
/*     */         } 
/*     */         break;
/*     */     } 
/* 203 */     air.removeIf(loca -> (loca.getBlock() == null));
/* 204 */     build(air, 0, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void packageBed(Block block) {
/* 209 */     ArrayList<Location> air = new ArrayList<>();
/* 210 */     World world = block.getWorld();
/* 211 */     Arrays.<Location>asList(new Location[] { block.getLocation(), BlockUtils.getBedNeighbor(block).getLocation() }).forEach(loc -> {
/*     */           air.add(new Location(world, loc.getX(), loc.getY() + 1.0D, loc.getZ()));
/*     */           air.add(new Location(world, loc.getX() - 1.0D, loc.getY(), loc.getZ()));
/*     */           air.add(new Location(world, loc.getX() + 1.0D, loc.getY(), loc.getZ()));
/*     */           air.add(new Location(world, loc.getX(), loc.getY(), loc.getZ() - 1.0D));
/*     */           air.add(new Location(world, loc.getX(), loc.getY(), loc.getZ() + 1.0D));
/*     */         });
/* 218 */     air.removeIf(loc -> (loc.getBlock() == null));
/* 219 */     build(air, 0, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void build(List<Location> air, int period, boolean airs) {
/* 225 */     long l = 0L;
/* 226 */     boolean[] tip = { false };
/* 227 */     for (Location loc : air) {
/* 228 */       if (!InventoryUtils.isEnoughItem(getPlayer(), Material.WOOL, Integer.valueOf((int)(l + 1L))).booleanValue()) {
/* 229 */         getPlayer().sendMessage("§c你已经没有羊毛了!");
/*     */         break;
/*     */       } 
/* 232 */       if (airs && loc.getBlock().getType() != getItem().getType() && loc.getBlock().getType() != Material.AIR)
/* 233 */         break;  Bukkit.getScheduler().runTaskLater((Plugin)BedWars.getInstance(), () -> { if (!hasRegion(getPd().getGame(), loc.getBlock()) && (loc.getBlock().getType() == Material.AIR || loc.getBlock().getType() == getItem().getType())) if (InventoryUtils.isEnoughItem(getPlayer(), Material.WOOL, Integer.valueOf(1)).booleanValue()) { InventoryUtils.deleteitem(getPlayer(), Material.WOOL, 1); getPd().getGame().addBlock(loc.getBlock()); loc.getWorld().playSound(loc, Sound.DIG_WOOL, 1.0F, 1.0F); loc.getBlock().setType(Material.WOOL); ((CraftBlock)loc.getBlock()).setData(getPd().getTeam().getTeamType().getDyeColor().getData()); } else if (!tip[0]) { tip[0] = true; getPlayer().sendMessage("§c你已经没有羊毛了!"); }   }l * period);
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
/* 248 */       l++;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean hasEntity(Block block) {
/* 253 */     if (block == null) {
/* 254 */       return true;
/*     */     }
/* 256 */     if (block.getType() != Material.AIR) return true; 
/* 257 */     return isMan(block.getWorld().getNearbyEntities(block.getLocation().add(0.5D, -0.5D, 0.5D), 0.4999999D, 0.9999999D, 0.4999999D));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isMan(Collection<Entity> entities) {
/* 262 */     Iterator<Entity> iterator = entities.iterator(); if (iterator.hasNext()) { Entity entity = iterator.next();
/* 263 */       if (!(entity instanceof Player)) {
/* 264 */         return true;
/*     */       }
/* 266 */       PlayerData pd = BedWars.getInstance().getPlayerManager().get((Player)entity);
/* 267 */       return (pd.getState() == PlayerState.FIGHTING); }
/*     */ 
/*     */     
/* 270 */     return false;
/*     */   }
/* 272 */   private static final BlockFace[] HORIZONTALS = new BlockFace[] { BlockFace.SOUTH, BlockFace.WEST, BlockFace.NORTH, BlockFace.EAST };
/*     */   public static BlockFace getBlockFace(float yaw) {
/* 274 */     return getHorizontal(MathHelper.floor((yaw * 4.0F / 360.0F) + 0.5D) & 0x3);
/*     */   }
/*     */   public static BlockFace getOpposite(float yaw) {
/* 277 */     switch (getBlockFace(yaw)) {
/*     */       case EAST:
/* 279 */         return BlockFace.WEST;
/*     */       case SOUTH:
/* 281 */         return BlockFace.NORTH;
/*     */       case WEST:
/* 283 */         return BlockFace.EAST;
/*     */       case NORTH:
/* 285 */         return BlockFace.SOUTH;
/*     */     } 
/* 287 */     return null;
/*     */   }
/*     */   public static BlockFace getHorizontal(int p_176731_0_) {
/* 290 */     return HORIZONTALS[MathHelper.a(p_176731_0_ % HORIZONTALS.length)];
/*     */   }
/*     */ }