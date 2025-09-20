/*    */ package cn.rmc.bedwars.runnable;
/*    */ 
/*    */ import com.boydti.fawe.util.EditSessionBuilder;
/*    */ import com.boydti.fawe.util.TaskManager;
/*    */ import com.sk89q.worldedit.EditSession;
/*    */ import com.sk89q.worldedit.Vector;
/*    */ import com.sk89q.worldedit.blocks.BaseBlock;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ import java.util.concurrent.ConcurrentMap;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.scheduler.BukkitRunnable;
/*    */ 
/*    */ public abstract class BlockPlaceRunnable
/*    */   extends BukkitRunnable
/*    */ {
/*    */   private World world;
/*    */   private final ConcurrentMap<Location, ItemStack> blocks;
/*    */   private final int totalBlocks;
/*    */   private final Iterator<Location> iterator;
/*    */   
/*    */   public World getWorld() {
/* 26 */     return this.world;
/* 27 */   } public ConcurrentMap<Location, ItemStack> getBlocks() { return this.blocks; }
/* 28 */   public int getTotalBlocks() { return this.totalBlocks; } public Iterator<Location> getIterator() {
/* 29 */     return this.iterator;
/* 30 */   } private int blockIndex = 0; public int getBlockIndex() { return this.blockIndex; }
/* 31 */    private int blocksPlaced = 0; public int getBlocksPlaced() { return this.blocksPlaced; } public boolean isCompleted() {
/* 32 */     return this.completed;
/*    */   } private boolean completed = false;
/*    */   public BlockPlaceRunnable(World world, Map<Location, ItemStack> blocks) {
/* 35 */     this.world = world;
/* 36 */     this.blocks = new ConcurrentHashMap<>();
/* 37 */     this.blocks.putAll(blocks);
/* 38 */     this.totalBlocks = blocks.keySet().size();
/* 39 */     this.iterator = blocks.keySet().iterator();
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/* 44 */     if (this.blocks.isEmpty() || !this.iterator.hasNext()) {
/* 45 */       finish();
/* 46 */       this.completed = true;
/* 47 */       cancel();
/*    */       
/*    */       return;
/*    */     } 
/* 51 */     TaskManager.IMP.async(() -> {
/*    */           EditSession editSession = (new EditSessionBuilder(this.world.getName())).fastmode(Boolean.valueOf(true)).allowedRegionsEverywhere().autoQueue(Boolean.valueOf(false)).limitUnlimited().build();
/*    */ 
/*    */ 
/*    */           
/*    */           for (Map.Entry<Location, ItemStack> entry : this.blocks.entrySet()) {
/*    */             try {
/*    */               editSession.setBlock(new Vector(((Location)entry.getKey()).getBlockX(), ((Location)entry.getKey()).getBlockY(), ((Location)entry.getKey()).getZ()), new BaseBlock(((ItemStack)entry.getValue()).getTypeId(), ((ItemStack)entry.getValue()).getDurability()));
/* 59 */             } catch (Exception exception) {}
/*    */           } 
/*    */           editSession.flushQueue();
/*    */           TaskManager.IMP.task(this.blocks::clear);
/*    */         });
/*    */   }
/*    */   
/*    */   public abstract void finish();
/*    */ }