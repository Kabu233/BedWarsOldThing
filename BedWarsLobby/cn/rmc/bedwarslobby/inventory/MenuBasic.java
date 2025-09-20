/*    */ package cn.rmc.bedwarslobby.inventory;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import cn.rmc.bedwarslobby.util.inventory.InventoryUI;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Listener;
/*    */ 
/*    */ public abstract class MenuBasic
/*    */   implements Listener {
/* 11 */   public static HashMap<Player, ArrayList<MenuBasic>> uis = new HashMap<>();
/*    */   
/*    */   protected InventoryUI inventoryUI;
/*    */   
/*    */   protected Player player;
/* 16 */   protected MenuBasic f2mb = this;
/*    */   
/*    */   protected abstract void Setup();
/*    */   
/*    */   public MenuBasic(Player p, String title, Integer rows) {
/* 21 */     this.player = p;
/* 22 */     this.inventoryUI = new InventoryUI(title, rows.intValue());
/* 23 */     if (!uis.containsKey(uis)) {
/* 24 */       uis.put(p, new ArrayList<>());
/*    */     }
/* 26 */     ArrayList<MenuBasic> al = uis.get(p);
/* 27 */     al.add(this);
/* 28 */     uis.put(p, al);
/*    */   }
/*    */   
/*    */   public MenuBasic(String title, Integer rows) {
/* 32 */     this.inventoryUI = new InventoryUI(title, rows.intValue());
/*    */   }
/*    */   
/*    */   public void open() {
/* 36 */     Setup();
/* 37 */     this.player.openInventory(this.inventoryUI.getCurrentPage());
/*    */   }
/*    */   
/*    */   public void destery() {
/* 41 */     this.inventoryUI = null;
/* 42 */     this.f2mb = null;
/*    */   }
/*    */ }