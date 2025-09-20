/*    */ package cn.rmc.bedwarslobby.npc.list;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import cn.rmc.bedwarslobby.enums.GameMode;
/*    */ import cn.rmc.bedwarslobby.inventory.select.MapSelector;
/*    */ import cn.rmc.bedwarslobby.inventory.select.list.PlayMenu;
/*    */ import cn.rmc.bedwarslobby.npc.AbstractNPC;
/*    */ import cn.rmc.bedwarslobby.npc.NPCSkin;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class PlayFourNPC
/*    */   extends AbstractNPC
/*    */ {
/*    */   public PlayFourNPC(Player player) {
/* 19 */     super(player, "play_four", NPCSkin.PLAY_BEDWARS_FOUR_FOUR.getNPCSkin());
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> getHolographicLine() {
/* 24 */     List<String> lines = new ArrayList<>();
/* 25 */     lines.add("§e§l点击开始游戏");
/* 26 */     lines.add("§b4v4v4v4模式 §c§l内测");
/* 27 */     int online = MapSelector.getPlayerCountByGameMode(GameMode.FOUR_FOUR);
/* 28 */     lines.add(String.format("§e§l%,d名玩家", new Object[] { Integer.valueOf(online) }));
/* 29 */     return lines;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getOff() {
/* 34 */     return 0.65D;
/*    */   }
/*    */ 
/*    */   
/*    */   public Location getSpawnLocation() {
/* 39 */     return new Location(Bukkit.getWorld("world"), -49.5D, 70.0D, 0.5D, -90.0F, 0.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getHeldItem() {
/* 44 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean useSelfSkin() {
/* 49 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onClick(Player player) {
/* 54 */     (new PlayMenu(player)).open();
/*    */   }
/*    */ }