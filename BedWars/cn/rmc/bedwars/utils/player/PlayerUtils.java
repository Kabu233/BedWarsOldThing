/*    */ package cn.rmc.bedwars.utils.player;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ import java.lang.reflect.Method;
/*    */ import cn.rmc.bedwars.game.PlayerData;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ public class PlayerUtils
/*    */ {
/*    */   public static void setFly(PlayerData pd) {
/*    */     try {
/* 13 */       Player p = pd.getPlayer();
/* 14 */       p.setAllowFlight(true);
/* 15 */       Class<?> cpClass = p.getClass();
/* 16 */       Method getHandle = cpClass.getDeclaredMethod("getHandle", new Class[0]);
/* 17 */       getHandle.setAccessible(true);
/* 18 */       Object NMSPlayer = getHandle.invoke(p, new Object[0]);
/* 19 */       Field abi = NMSPlayer.getClass().getSuperclass().getDeclaredField("abilities");
/* 20 */       abi.setAccessible(true);
/* 21 */       Object abiObj = abi.get(NMSPlayer);
/* 22 */       Field isFlying = abiObj.getClass().getDeclaredField("isFlying");
/* 23 */       isFlying.setAccessible(true);
/* 24 */       isFlying.set(abiObj, Boolean.valueOf(true));
/* 25 */       Method update = NMSPlayer.getClass().getSuperclass().getDeclaredMethod("updateAbilities", new Class[0]);
/* 26 */       update.setAccessible(true);
/* 27 */       update.invoke(NMSPlayer, new Object[0]);
/* 28 */     } catch (Exception exception) {}
/*    */   }
/*    */   public static void clearArmor(PlayerData pd) {
/* 31 */     Player player = pd.getPlayer();
/* 32 */     player.getInventory().setHelmet(null);
/* 33 */     player.getInventory().setChestplate(null);
/* 34 */     player.getInventory().setLeggings(null);
/* 35 */     player.getInventory().setBoots(null);
/*    */   }
/*    */   
/*    */   public static String getNameColor(Player p) {
/* 39 */     return LuckPermsUtil.getPrefixColor(p);
/*    */   }
/*    */ }