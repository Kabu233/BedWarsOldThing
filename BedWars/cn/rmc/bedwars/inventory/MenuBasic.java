/*    */ package cn.rmc.bedwars.inventory;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import cn.rmc.bedwars.BedWars;
/*    */ import cn.rmc.bedwars.game.PlayerData;
/*    */ import cn.rmc.bedwars.utils.inventory.InventoryUI;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.inventory.InventoryClickEvent;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ public abstract class MenuBasic {
/* 14 */   public static HashMap<Player, ArrayList<MenuBasic>> uis = new HashMap<>();
/*    */   protected InventoryUI inventoryUI;
/*    */   protected PlayerData pd;
/*    */   protected Player p;
/*    */   protected MenuBasic mb;
/*    */   
/*    */   public MenuBasic(Player p, String title, Integer rows) {
/* 21 */     this(p, title, rows, null);
/*    */   }
/*    */   public MenuBasic(Player p, String title, Integer rows, String symbol) {
/* 24 */     this.p = p;
/* 25 */     this.pd = BedWars.getInstance().getPlayerManager().get(p);
/* 26 */     this.inventoryUI = new InventoryUI(title, rows.intValue(), symbol)
/*    */       {
/*    */         public void onInventoryClick(InventoryClickEvent e) {
/* 29 */           MenuBasic.this.InventoryClick(e);
/*    */         }
/*    */       };
/* 32 */     this.mb = this;
/* 33 */     if (!uis.containsKey(uis)) {
/* 34 */       uis.put(p, new ArrayList<>());
/*    */     }
/* 36 */     ArrayList<MenuBasic> al = uis.get(p);
/* 37 */     al.add(this);
/* 38 */     uis.put(p, al);
/*    */   }
/*    */   
/*    */   public MenuBasic(String title, Integer rows) {
/* 42 */     this.inventoryUI = new InventoryUI(title, rows.intValue())
/*    */       {
/*    */         public void onInventoryClick(InventoryClickEvent e) {
/* 45 */           MenuBasic.this.InventoryClick(e);
/*    */         }
/*    */       };
/* 48 */     this.mb = this;
/*    */   }
/*    */ 
/*    */   
/*    */   public void InventoryClick(InventoryClickEvent e) {}
/*    */ 
/*    */   
/*    */   public void open() {
/* 56 */     Setup();
/* 57 */     Bukkit.getScheduler().runTask((Plugin)BedWars.getInstance(), () -> this.p.openInventory(this.inventoryUI.getCurrentPage()));
/*    */   }
/*    */   public void destery() {
/* 60 */     this.inventoryUI = null;
/* 61 */     this.mb = null;
/*    */   }
/*    */   
/*    */   public abstract void Setup();
/*    */ }