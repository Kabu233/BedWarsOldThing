/*     */ package cn.rmc.bedwars.game.dream.ultimate.ultimates;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import java.util.UUID;
/*     */ import cn.rmc.bedwars.BedWars;
/*     */ import cn.rmc.bedwars.event.BedBreakEvent;
/*     */ import cn.rmc.bedwars.event.PlayerKillEvent;
/*     */ import cn.rmc.bedwars.game.dream.ultimate.UltimateBasic;
/*     */ import cn.rmc.bedwars.game.dream.ultimate.UltimateType;
/*     */ import cn.rmc.bedwars.utils.item.ItemBuilder;
/*     */ import cn.rmc.bedwars.utils.world.LocationUtil;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.block.BlockFace;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.EntityType;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.block.Action;
/*     */ import org.bukkit.event.entity.EntityExplodeEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.metadata.FixedMetadataValue;
/*     */ import org.bukkit.metadata.MetadataValue;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ public class Demolition extends UltimateBasic {
/*  32 */   static Random random = new Random();
/*     */   ItemStack is;
/*     */   
/*     */   public Demolition(UUID uuid) {
/*  36 */     super(uuid, UltimateType.DEMOLITION);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  41 */     this.is = (new ItemBuilder(Material.FLINT_AND_STEEL)).setName("§a破坏者工具").toItemStack();
/*     */   }
/*     */   public ItemStack getItem() {
/*  44 */     return this.is;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onInteract(PlayerInteractEvent e) {
/*  50 */     if (e.getItem() == null)
/*  51 */       return;  if (e.getAction() == null)
/*  52 */       return;  if (e.getAction() != Action.RIGHT_CLICK_BLOCK)
/*  53 */       return;  if (e.getItem().getType() == Material.FLINT_AND_STEEL) {
/*     */       
/*  55 */       e.setCancelled(true);
/*     */       
/*  57 */       Player p = e.getPlayer();
/*  58 */       Block block = e.getClickedBlock();
/*     */       
/*  60 */       if (block == null)
/*     */         return; 
/*  62 */       if (block.getType() != Material.WOOL) {
/*  63 */         p.sendMessage("§e只能作用在§a羊毛§e上！");
/*  64 */         p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1.1F, 1.1F);
/*     */         return;
/*     */       } 
/*  67 */       List<Location> set = new ArrayList<>();
/*  68 */       set.add(block.getLocation());
/*  69 */       List<Location> preset = new ArrayList<>();
/*  70 */       for (int i = 0; i < 5; i++) {
/*  71 */         for (Location location : set) {
/*     */           
/*  73 */           for (Location blockNearbyLocation : LocationUtil.getBlockNearbyLocations(location)) {
/*  74 */             if (set.contains(blockNearbyLocation) || 
/*  75 */               !getPd().getGame().isBlock(blockNearbyLocation.getBlock()).booleanValue() || blockNearbyLocation
/*  76 */               .getBlock().getType() != Material.WOOL)
/*  77 */               continue;  preset.add(blockNearbyLocation);
/*  78 */             Block relative = blockNearbyLocation.getBlock().getRelative(BlockFace.UP);
/*  79 */             Bukkit.getScheduler().runTaskLater((Plugin)BedWars.getInstance(), () -> { if (relative.getType() == Material.AIR) relative.setType(Material.FIRE);  Bukkit.getScheduler().runTaskLater((Plugin)BedWars.getInstance(), (), 10L); }i * 12L);
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  88 */         set = new ArrayList<>(preset);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDeath(PlayerKillEvent e) {
/*  96 */     Location location = e.getDeather().getPlayer().getLocation();
/*  97 */     Entity entity = location.getWorld().spawnEntity(location.add(0.0D, 1.0D, 0.0D), EntityType.PRIMED_TNT);
/*  98 */     entity.setMetadata("BOOM", (MetadataValue)new FixedMetadataValue((Plugin)BedWars.getInstance(), "1"));
/*     */   }
/*     */   @EventHandler
/*     */   public void onExplo(EntityExplodeEvent e) {
/* 102 */     if (e.getEntity().hasMetadata("BOOM")) {
/* 103 */       e.blockList().clear();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBedBreak(BedBreakEvent e) {
/* 109 */     getPlayer().getInventory().addItem(new ItemStack[] { (new ItemBuilder(Material.MONSTER_EGG, 2, (byte)50)).toItemStack() });
/*     */   }
/*     */ }