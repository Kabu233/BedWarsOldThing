/*    */ package cn.rmc.bedwars.inventory.spec;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import cn.rmc.bedwars.inventory.MenuBasic;
/*    */ import cn.rmc.bedwars.utils.MathUtils;
/*    */ import cn.rmc.bedwars.utils.inventory.InventoryUI;
/*    */ import cn.rmc.bedwars.utils.item.ItemBuilder;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.inventory.InventoryClickEvent;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.potion.PotionEffect;
/*    */ import org.bukkit.potion.PotionEffectType;
/*    */ 
/*    */ public class SpecSettings
/*    */   extends MenuBasic
/*    */ {
/*    */   public SpecSettings(Player p) {
/* 19 */     super(p, "旁观者设置", Integer.valueOf(4));
/*    */   }
/*    */ 
/*    */   
/*    */   public void Setup() {
/* 24 */     HashMap<Integer, Material> speeditem = new HashMap<>();
/* 25 */     speeditem.put(Integer.valueOf(0), Material.LEATHER_BOOTS);
/* 26 */     speeditem.put(Integer.valueOf(1), Material.CHAINMAIL_BOOTS);
/* 27 */     speeditem.put(Integer.valueOf(2), Material.IRON_BOOTS);
/* 28 */     speeditem.put(Integer.valueOf(3), Material.GOLD_BOOTS);
/* 29 */     speeditem.put(Integer.valueOf(4), Material.DIAMOND_BOOTS);
/* 30 */     for (Integer i : speeditem.keySet()) {
/* 31 */       Material itemtype = speeditem.get(i);
/* 32 */       Integer slot = Integer.valueOf(11 + i.intValue());
/* 33 */       if (i.intValue() == 0) {
/* 34 */         this.inventoryUI.setItem(slot.intValue(), (InventoryUI.ClickableItem)new InventoryUI.AbstractClickableItem((new ItemBuilder(itemtype)).setName("§7无").toItemStack())
/*    */             {
/*    */               public void onClick(InventoryClickEvent e)
/*    */               {
/* 38 */                 SpecSettings.this.p.removePotionEffect(PotionEffectType.SPEED);
/* 39 */                 SpecSettings.this.p.closeInventory(); }
/*    */             });
/*    */         continue;
/*    */       } 
/* 43 */       this.inventoryUI.setItem(slot.intValue(), (InventoryUI.ClickableItem)new InventoryUI.AbstractClickableItem((new ItemBuilder(itemtype)).setName("§a速度 " + MathUtils.toRome(i.intValue())).toItemStack())
/*    */           {
/*    */             public void onClick(InventoryClickEvent e)
/*    */             {
/* 47 */               SpecSettings.this.p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, i.intValue() - 1, false, false), true);
/* 48 */               SpecSettings.this.p.closeInventory();
/*    */             }
/*    */           });
/*    */     } 
/*    */     
/* 53 */     for (SpecOther.SpecItems item : SpecOther.SpecItems.values()) {
/* 54 */       this.inventoryUI.setItem(item.getSlot(), (InventoryUI.ClickableItem)new InventoryUI.AbstractClickableItem(item.toItemStack(this.p))
/*    */           {
/*    */             public void onClick(InventoryClickEvent e) {
/* 57 */               Player p = (Player)e.getWhoClicked();
/* 58 */               item.clickableItem(p);
/* 59 */               (new SpecSettings(p)).open();
/*    */             }
/*    */           });
/*    */     } 
/*    */   }
/*    */ }