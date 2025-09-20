/*    */ package cn.rmc.bedwars.game.dream.armed;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import cn.rmc.bedwars.BedWars;
/*    */ import cn.rmc.bedwars.enums.IShopItem;
/*    */ import cn.rmc.bedwars.enums.ITeamUpgrade;
/*    */ import cn.rmc.bedwars.enums.game.PlayerState;
/*    */ import cn.rmc.bedwars.game.Game;
/*    */ import cn.rmc.bedwars.game.PlayerData;
/*    */ import cn.rmc.bedwars.game.dream.DreamManager;
/*    */ import cn.rmc.bedwars.inventory.game.ShopBasic;
/*    */ import cn.rmc.bedwars.utils.Group;
/*    */ import cn.rmc.bedwars.utils.item.ItemBuilder;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.event.HandlerList;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ public class ArmedManager implements DreamManager {
/*    */   Game game;
/* 25 */   List<GunBasic> guns = new ArrayList<>();
/*    */   Listener listener;
/*    */   
/*    */   public ArmedManager(Game game) {
/* 29 */     this.game = game;
/*    */   }
/*    */ 
/*    */   
/*    */   public void start() {
/* 34 */     Bukkit.getPluginManager().registerEvents(this.listener = new ArmedListener(this), (Plugin)BedWars.getInstance());
/* 35 */     for (GunType value : GunType.values()) {
/*    */       try {
/* 37 */         GunBasic gunBasic = value.getClazz().getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
/* 38 */         this.guns.add(gunBasic);
/* 39 */       } catch (InstantiationException|IllegalAccessException|java.lang.reflect.InvocationTargetException|NoSuchMethodException e) {
/* 40 */         e.printStackTrace();
/*    */       } 
/*    */     } 
/* 43 */     for (PlayerData player : this.game.getOnlinePlayers()) {
/* 44 */       if (player.getState() == PlayerState.FIGHTING) {
/* 45 */         player.getPlayer().getInventory().addItem(new ItemStack[] { GunType.PISTOL.giveItem(player) });
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void dispose() {
/* 52 */     for (GunBasic gun : this.guns) {
/* 53 */       gun.dispose();
/*    */     }
/* 55 */     HandlerList.unregisterAll(this.listener);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void init() {
/* 61 */     ShopBasic.setShop(Integer.valueOf(5), new Group((new ItemBuilder(Material.WOOD_HOE)).setName("§a发射器"), ArmedShop.class));
/* 62 */     IShopItem.clazz.add(GunType.class);
/* 63 */     ITeamUpgrade.upgrades.addAll(Arrays.asList(ArmedTeamUpgrade.values()));
/*    */   }
/*    */ }