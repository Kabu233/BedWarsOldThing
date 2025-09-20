/*    */ package cn.rmc.bedwars.runnable;
/*    */ 
/*    */ import java.util.UUID;
/*    */ import cn.rmc.bedwars.enums.game.Resource;
/*    */ import cn.rmc.bedwars.game.Team;
/*    */ import cn.rmc.bedwars.utils.item.ItemBuilder;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.Item;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.scheduler.BukkitRunnable;
/*    */ import org.bukkit.util.Vector;
/*    */ 
/*    */ 
/*    */ public class ResourceSpawnRunnable
/*    */   extends BukkitRunnable
/*    */ {
/*    */   private Resource type;
/*    */   private Location loc;
/*    */   
/*    */   public void setType(Resource type) {
/* 23 */     this.type = type; } public void setLoc(Location loc) { this.loc = loc; } public void setMiddle(Boolean middle) { this.middle = middle; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof ResourceSpawnRunnable)) return false;  ResourceSpawnRunnable other = (ResourceSpawnRunnable)o; if (!other.canEqual(this)) return false;  Object this$type = getType(), other$type = other.getType(); if ((this$type == null) ? (other$type != null) : !this$type.equals(other$type)) return false;  Object this$loc = getLoc(), other$loc = other.getLoc(); if ((this$loc == null) ? (other$loc != null) : !this$loc.equals(other$loc)) return false;  Object this$middle = getMiddle(), other$middle = other.getMiddle(); return !((this$middle == null) ? (other$middle != null) : !this$middle.equals(other$middle)); } protected boolean canEqual(Object other) { return other instanceof ResourceSpawnRunnable; } public int hashCode() { int PRIME = 59; result = 1; Object $type = getType(); result = result * 59 + (($type == null) ? 43 : $type.hashCode()); Object $loc = getLoc(); result = result * 59 + (($loc == null) ? 43 : $loc.hashCode()); Object $middle = getMiddle(); return result * 59 + (($middle == null) ? 43 : $middle.hashCode()); } public String toString() { return "ResourceSpawnRunnable(type=" + getType() + ", loc=" + getLoc() + ", middle=" + getMiddle() + ")"; }
/*    */   
/* 25 */   public Resource getType() { return this.type; } public Location getLoc() {
/* 26 */     return this.loc;
/* 27 */   } private Boolean middle = Boolean.valueOf(false); public Boolean getMiddle() { return this.middle; }
/*    */    public ResourceSpawnRunnable(Resource type, Location loc) {
/* 29 */     this.type = type;
/* 30 */     this.loc = loc;
/*    */   }
/*    */   public ResourceSpawnRunnable(Resource type, Location loc, Boolean isdouble) {
/* 33 */     this.type = type;
/* 34 */     this.loc = loc;
/* 35 */     this.middle = isdouble;
/*    */   }
/*    */   public ResourceSpawnRunnable(Resource type, Team team) {
/* 38 */     this.type = type;
/* 39 */     this.loc = team.getResloc();
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/* 44 */     Location l = this.loc.clone();
/* 45 */     l.setPitch(90.0F);
/* 46 */     int iron = 0;
/* 47 */     int gold = 0;
/* 48 */     int emerald = 0;
/* 49 */     for (Entity entity : this.loc.getWorld().getNearbyEntities(this.loc, 5.0D, 3.0D, 5.0D)) {
/* 50 */       if (entity instanceof Item) {
/* 51 */         switch (((Item)entity).getItemStack().getType()) {
/*    */           case IRON_INGOT:
/* 53 */             iron++;
/*    */           
/*    */           case GOLD_INGOT:
/* 56 */             gold++;
/*    */           
/*    */           case EMERALD:
/* 59 */             emerald++;
/*    */         } 
/*    */ 
/*    */       
/*    */       }
/*    */     } 
/* 65 */     if (!this.middle.booleanValue() && ((this.type.getItem() == Material.IRON_INGOT && iron >= 120) || (this.type
/* 66 */       .getItem() == Material.GOLD_INGOT && gold >= 12) || (this.type
/* 67 */       .getItem() == Material.EMERALD && emerald >= 4))) {
/*    */       return;
/*    */     }
/* 70 */     Item item = this.loc.getWorld().dropItem(l, new ItemStack(this.type.getItem()));
/* 71 */     item.setCustomName(UUID.randomUUID().toString());
/* 72 */     item.setPickupDelay(5);
/* 73 */     item.setItemStack((new ItemBuilder(item.getItemStack())).setLore(new String[] { UUID.randomUUID().toString() }).toItemStack());
/* 74 */     item.setVelocity(new Vector());
/*    */   }
/*    */ }