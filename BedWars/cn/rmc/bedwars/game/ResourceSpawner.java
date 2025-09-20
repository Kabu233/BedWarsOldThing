/*    */ package cn.rmc.bedwars.game;
/*    */ 
/*    */ import java.util.Collections;
/*    */ import java.util.UUID;
/*    */ import cn.rmc.bedwars.BedWars;
/*    */ import cn.rmc.bedwars.enums.game.Resource;
/*    */ import cn.rmc.bedwars.utils.MathUtils;
/*    */ import cn.rmc.bedwars.utils.item.ItemBuilder;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.Item;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.scheduler.BukkitRunnable;
/*    */ import org.bukkit.util.Vector;
/*    */ 
/*    */ public class ResourceSpawner {
/*    */   Game g;
/*    */   
/*    */   public Location getLoc() {
/* 24 */     return this.loc;
/*    */   }
/*    */   Resource res; Location loc;
/*    */   Holographic holo1;
/*    */   Holographic holo2;
/*    */   Holographic holo3;
/*    */   Holographic holoBlock;
/*    */   
/*    */   public ResourceSpawner(Game g, Resource res, Location loc) {
/* 33 */     this.g = g;
/* 34 */     this.res = res;
/* 35 */     this.loc = loc;
/*    */   }
/*    */   public void spawn() {
/* 38 */     int dia = 0, em = 0;
/* 39 */     for (Entity entity : this.loc.getWorld().getNearbyEntities(this.loc, 5.0D, 3.0D, 5.0D)) {
/* 40 */       if (entity instanceof Item) {
/* 41 */         switch (((Item)entity).getItemStack().getType()) {
/*    */           case DIAMOND:
/* 43 */             dia++;
/*    */           
/*    */           case EMERALD:
/* 46 */             em++;
/*    */         } 
/*    */ 
/*    */       
/*    */       }
/*    */     } 
/* 52 */     if ((this.res == Resource.DIAMOND && dia >= 8) || (this.res == Resource.EMERALD && em >= 4)) {
/*    */       return;
/*    */     }
/* 55 */     Item item = this.loc.getWorld().dropItem(this.loc.clone().add(0.0D, 2.0D, 0.0D), (new ItemBuilder(this.res.getItem())).setLore(new String[] { UUID.randomUUID().toString() }).toItemStack());
/* 56 */     item.setVelocity(new Vector());
/* 57 */     item.setPickupDelay(5);
/*    */   }
/*    */   public void initHolo() {
/* 60 */     Location locs = this.loc.clone();
/* 61 */     this.holoBlock = new Holographic(locs.add(0.0D, 1.25D, 0.0D), null);
/* 62 */     this.holoBlock.setEquipment(Collections.singletonList(new ItemStack(this.res.getBlock())));
/* 63 */     Bukkit.getScheduler().runTaskTimer((Plugin)BedWars.getInstance(), () -> this.holoBlock.moveArmorStand(this.holoBlock.getLocation().getY()), 1L, 1L);
/*    */ 
/*    */     
/* 66 */     this.holo3 = new Holographic(locs.add(0.0D, 0.55D, 0.0D), null);
/* 67 */     this.holo2 = new Holographic(locs.add(0.0D, 0.35D, 0.0D), this.res.getColor() + ChatColor.BOLD.toString() + this.res.getDisplayName());
/* 68 */     this.holo1 = new Holographic(locs.add(0.0D, 0.35D, 0.0D), "§e等级§c" + MathUtils.toRome(((Integer)this.g.getResGenLevel().get(this.res)).intValue() + 1));
/* 69 */     countdown();
/* 70 */     for (PlayerData player : this.g.getOnlinePlayers())
/* 71 */       display(player); 
/*    */   }
/*    */   
/*    */   public void countdown() {
/* 75 */     final int i = ((Integer)this.res.getLevel().get(((Integer)this.g.getResGenLevel().get(this.res)).intValue())).intValue();
/* 76 */     (new BukkitRunnable() {
/* 77 */         int cout = i;
/*    */         
/*    */         public void run() {
/* 80 */           this.cout--;
/*    */           
/* 82 */           ResourceSpawner.this.holo3.setTitle("§e在§c" + this.cout + "§e秒后产出");
/* 83 */           if (this.cout <= 0) {
/* 84 */             ResourceSpawner.this.spawn();
/* 85 */             ResourceSpawner.this.countdown();
/* 86 */             cancel();
/*    */           } 
/*    */         }
/* 89 */       }).runTaskTimer((Plugin)BedWars.getInstance(), 20L, 20L);
/*    */   }
/*    */   public void display(PlayerData pd) {
/* 92 */     this.holoBlock.display(pd.getPlayer());
/* 93 */     this.holo1.display(pd.getPlayer());
/* 94 */     this.holo2.display(pd.getPlayer());
/* 95 */     this.holo3.display(pd.getPlayer());
/*    */   }
/*    */ }