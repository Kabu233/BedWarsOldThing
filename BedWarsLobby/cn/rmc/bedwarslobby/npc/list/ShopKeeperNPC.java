/*    */ package cn.rmc.bedwarslobby.npc.list;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import cn.rmc.bedwarslobby.BedWarsLobby;
/*    */ import cn.rmc.bedwarslobby.inventory.store.StoreMainMenu;
/*    */ import cn.rmc.bedwarslobby.npc.AbstractNPC;
/*    */ import cn.rmc.bedwarslobby.npc.NPCSkin;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class ShopKeeperNPC
/*    */   extends AbstractNPC {
/*    */   public ShopKeeperNPC(Player player) {
/* 18 */     super(player, "shopkeeper", NPCSkin.SHOPKEEPER.getNPCSkin());
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> getHolographicLine() {
/* 23 */     List<String> line = new ArrayList<>();
/* 24 */     line.add("§e§l点击打开");
/* 25 */     line.add("§b店主");
/* 26 */     return line;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getOff() {
/* 31 */     return 0.35D;
/*    */   }
/*    */ 
/*    */   
/*    */   public Location getSpawnLocation() {
/* 36 */     return new Location(Bukkit.getWorld("world"), -58.5D, 69.0D, 0.5D, -90.0F, 0.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getHeldItem() {
/* 41 */     return new ItemStack(Material.PAPER);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean useSelfSkin() {
/* 46 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onClick(Player player) {
/* 51 */     (new StoreMainMenu(BedWarsLobby.getInstance().getPlayerManager().get(player))).open();
/*    */   }
/*    */ }