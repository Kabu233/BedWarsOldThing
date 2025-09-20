/*    */ package cn.rmc.bedwarslobby.util;
/*    */ 
/*    */ import java.util.UUID;
/*    */ import cn.rmc.bedwarslobby.enums.Data;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class PlayerUtils
/*    */ {
/*    */   public static void setFly(Player p) {
/*    */     try {
/* 13 */       p.setAllowFlight(true);
/* 14 */       (((CraftPlayer)p).getHandle()).abilities.isFlying = true;
/* 15 */       ((CraftPlayer)p).getHandle().updateAbilities();
/* 16 */     } catch (Exception exception) {}
/*    */   }
/*    */ 
/*    */   
/*    */   public static void giveLobbyItem(Player p) {
/* 21 */     p.getInventory().setItem(2, (new ItemBuilder(Material.EMERALD)).setName("§a起床商店 §7(右键点击)").setLore(new String[] { "§7硬币: §6" + MathUtils.Format(DataUtils.getInt(p.getUniqueId().toString(), Data.Field.COIN)) }).toItemStack());
/*    */   }
/*    */   
/*    */   public static String getPrefixColor(UUID uuid) {
/* 25 */     return LuckPermsUtil.getPrefixColor(uuid);
/*    */   }
/*    */ }