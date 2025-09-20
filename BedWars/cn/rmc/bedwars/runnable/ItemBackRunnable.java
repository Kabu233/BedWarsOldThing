/*    */ package cn.rmc.bedwars.runnable;
/*    */ 
/*    */ import cn.rmc.bedwars.game.PlayerData;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.scheduler.BukkitRunnable;
/*    */ 
/*    */ public class ItemBackRunnable extends BukkitRunnable {
/*    */   ItemStack is;
/*    */   PlayerData pd;
/*    */   
/*    */   public ItemBackRunnable(ItemStack is, PlayerData pd) {
/* 12 */     this.is = is;
/* 13 */     this.pd = pd;
/*    */   }
/*    */   
/*    */   public void run() {}
/*    */ }