/*    */ package cn.rmc.bedwarslobby.npc.list;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import cn.rmc.bedwarslobby.enums.Data;
/*    */ import cn.rmc.bedwarslobby.inventory.bedwars.MainStatsMenu;
/*    */ import cn.rmc.bedwarslobby.npc.AbstractNPC;
/*    */ import cn.rmc.bedwarslobby.util.DataUtils;
/*    */ import cn.rmc.bedwarslobby.util.LevelUtils;
/*    */ import cn.rmc.bedwarslobby.util.MathUtils;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class ProfileNPC
/*    */   extends AbstractNPC {
/*    */   public ProfileNPC(Player player) {
/* 20 */     super(player, "profile");
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> getHolographicLine() {
/* 25 */     List<String> lines = new ArrayList<>();
/*    */     
/* 27 */     Integer wins = DataUtils.getInt(this.player.getUniqueId().toString(), Data.Field.WIN, "TOTAL");
/*    */     
/* 29 */     Integer winStreak = DataUtils.getInt(this.player.getUniqueId().toString(), Data.Field.WINSTREAK, "TOTAL");
/* 30 */     int exp = DataUtils.getInt(this.player.getUniqueId().toString(), Data.Field.EXPERIENCE).intValue();
/* 31 */     Integer lv = LevelUtils.getLevel(exp);
/* 32 */     String[] progress = LevelUtils.getLevelProgress(exp);
/* 33 */     lines.add("§6§l你的起床战争信息");
/* 34 */     lines.add("§f你的等级: " + LevelUtils.getLevelwithColorByLevel(lv.intValue()));
/* 35 */     lines.add("§f进度: " + progress[0]);
/* 36 */     lines.add("§f总胜场: §a" + MathUtils.Format(wins));
/* 37 */     lines.add("§f当前连胜场数: §a" + MathUtils.Format(winStreak));
/* 38 */     lines.add("§e§l点击查看数据");
/* 39 */     return lines;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getOff() {
/* 44 */     return 1.85D;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onClick(Player player) {
/* 49 */     (new MainStatsMenu(player)).open();
/*    */   }
/*    */ 
/*    */   
/*    */   public Location getSpawnLocation() {
/* 54 */     return new Location(Bukkit.getWorld("world"), 0.5D, 75.0D, 0.5D, 90.0F, 0.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getHeldItem() {
/* 59 */     return new ItemStack(Material.PAPER);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean useSelfSkin() {
/* 64 */     return true;
/*    */   }
/*    */ }