/*    */ package cn.rmc.bedwars.game.dream.ultimate.ultimates;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.HashMap;
/*    */ import java.util.Set;
/*    */ import java.util.UUID;
/*    */ import cn.rmc.bedwars.enums.ITeamUpgrade;
/*    */ import cn.rmc.bedwars.event.BedBreakEvent;
/*    */ import cn.rmc.bedwars.event.TeamUpgradeBuyEvent;
/*    */ import cn.rmc.bedwars.game.PlayerData;
/*    */ import cn.rmc.bedwars.game.dream.ultimate.UltimateBasic;
/*    */ import cn.rmc.bedwars.game.dream.ultimate.UltimateType;
/*    */ import cn.rmc.bedwars.utils.MathUtils;
/*    */ import cn.rmc.bedwars.utils.item.ItemBuilder;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.block.Action;
/*    */ import org.bukkit.event.block.BlockPlaceEvent;
/*    */ import org.bukkit.event.player.PlayerInteractEvent;
/*    */ import org.bukkit.event.player.PlayerPickupItemEvent;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class Gatherer
/*    */   extends UltimateBasic {
/*    */   public Gatherer(UUID uuid) {
/* 27 */     super(uuid, UltimateType.GATHERER);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack getItem() {
/* 34 */     return (new ItemBuilder(Material.ENDER_CHEST)).setName("§d随身末影箱 §7(右键点击)").toItemStack();
/*    */   }
/*    */ 
/*    */   
/*    */   public void onBlockPlace(BlockPlaceEvent e) {
/* 39 */     if (e.getBlockPlaced().getType() == Material.ENDER_CHEST) {
/* 40 */       e.setBuild(false);
/* 41 */       e.setCancelled(true);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void onInteract(PlayerInteractEvent e) {
/* 47 */     if (e.getItem() != null && 
/* 48 */       isSame(e.getPlayer().getItemInHand()))
/*    */     {
/* 50 */       if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
/* 51 */         e.setCancelled(true);
/* 52 */         getPlayer().openInventory(getPlayer().getEnderChest());
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onPickUp(PlayerPickupItemEvent e) {
/* 61 */     if (e.getItem().getItemStack().getItemMeta().getLore() != null && e.getItem().getItemStack().getItemMeta().getLore().size() != 0 && 
/* 62 */       Arrays.<Material>asList(new Material[] { Material.DIAMOND, Material.EMERALD }).contains(e.getItem().getItemStack().getType()) && 
/* 63 */       MathUtils.Chance(10).booleanValue()) {
/* 64 */       e.getPlayer().getInventory().addItem(new ItemStack[] { (new ItemBuilder(e.getItem().getItemStack().getType())).toItemStack() });
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onBedBroken(BedBreakEvent e) {
/* 73 */     HashMap<ITeamUpgrade, Integer> upgrades = getPd().getTeam().getTeamUpgrade();
/* 74 */     Set<ITeamUpgrade> teamUpgrades = upgrades.keySet();
/* 75 */     for (ITeamUpgrade upgrade : teamUpgrades) {
/* 76 */       int level = ((Integer)upgrades.get(upgrade)).intValue();
/* 77 */       if (upgrade.getMaxLevel() == null || level < upgrade.getMaxLevel().intValue()) {
/* 78 */         upgrades.put(upgrade, Integer.valueOf(level + 1));
/* 79 */         for (PlayerData player : getPd().getTeam().getAlivePlayers()) {
/* 80 */           player.getPlayer().sendMessage("§a" + getPd().getPlayer().getName() + "因职业获得了§6" + upgrade.getDisplayName());
/*    */         }
/* 82 */         Bukkit.getPluginManager().callEvent((Event)new TeamUpgradeBuyEvent(getPd().getTeam(), upgrade, Integer.valueOf(level + 1)));
/*    */         break;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }