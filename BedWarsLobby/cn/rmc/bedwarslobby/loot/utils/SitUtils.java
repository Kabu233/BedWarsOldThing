/*    */ package cn.rmc.bedwarslobby.loot.utils;
/*    */ import java.util.HashMap;
/*    */ import cn.rmc.bedwarslobby.util.ActionBarUtils;
/*    */ import org.bukkit.entity.ArmorStand;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.EntityType;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class SitUtils {
/* 10 */   public static HashMap<Player, ArmorStand> sitted = new HashMap<>();
/*    */   
/*    */   public static void sitPlayer(Player p) {
/* 13 */     if (sitted.containsKey(p)) {
/* 14 */       unsitPlayer(p);
/*    */     }
/*    */     
/* 17 */     ArmorStand stand = (ArmorStand)p.getWorld().spawnEntity(p.getLocation(), EntityType.ARMOR_STAND);
/* 18 */     stand.setVisible(false);
/* 19 */     stand.setPassenger((Entity)p);
/* 20 */     sitted.put(p, stand);
/* 21 */     ActionBarUtils.sendActionBar(p, "");
/*    */   }
/*    */   
/*    */   public static void unsitPlayer(Player p) {
/* 25 */     if (sitted.containsKey(p) && 
/* 26 */       sitted.get(p) != null) {
/* 27 */       ((ArmorStand)sitted.get(p)).remove();
/* 28 */       sitted.remove(p);
/*    */     } 
/*    */   }
/*    */ }