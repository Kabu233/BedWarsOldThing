/*     */ package cn.rmc.bedwars.inventory.game;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import cn.rmc.bedwars.inventory.MenuBasic;
/*     */ import cn.rmc.bedwars.inventory.game.shops.ArmorShop;
/*     */ import cn.rmc.bedwars.inventory.game.shops.BlockShop;
/*     */ import cn.rmc.bedwars.inventory.game.shops.MeleeShop;
/*     */ import cn.rmc.bedwars.inventory.game.shops.PotionShop;
/*     */ import cn.rmc.bedwars.inventory.game.shops.QuickShop;
/*     */ import cn.rmc.bedwars.inventory.game.shops.RangedShop;
/*     */ import cn.rmc.bedwars.inventory.game.shops.ToolShop;
/*     */ import cn.rmc.bedwars.inventory.game.shops.UtilityShop;
/*     */ import cn.rmc.bedwars.utils.Group;
/*     */ import cn.rmc.bedwars.utils.inventory.InventoryUI;
/*     */ import cn.rmc.bedwars.utils.item.ItemBuilder;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.inventory.ClickType;
/*     */ import org.bukkit.event.inventory.InventoryClickEvent;
/*     */ import org.bukkit.inventory.ItemFlag;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ public abstract class ShopBasic extends MenuBasic {
/*  26 */   private static final HashMap<Integer, Group<ItemBuilder, Class<? extends ShopBasic>>> shops = new LinkedHashMap<Integer, Group<ItemBuilder, Class<? extends ShopBasic>>>()
/*     */     {
/*     */     
/*     */     };
/*     */ 
/*     */ 
/*     */   
/*     */   Integer slot;
/*     */ 
/*     */ 
/*     */   
/*     */   public ShopBasic(Player p, String title, Integer slot) {
/*  38 */     this(p, title, slot, (String)null);
/*     */   }
/*     */   
/*     */   public ShopBasic(Player p, String title, Integer slot, String symbol) {
/*  42 */     super(p, title, Integer.valueOf(6), symbol);
/*  43 */     this.slot = slot;
/*  44 */     this.pd.setCurrentShop(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void Setup() {
/*  49 */     int st = 0;
/*  50 */     for (Map.Entry<Integer, Group<ItemBuilder, Class<? extends ShopBasic>>> entry : shops.entrySet()) {
/*  51 */       final Group<ItemBuilder, Class<? extends ShopBasic>> group = entry.getValue();
/*  52 */       ItemBuilder itemBuilder = (ItemBuilder)group.getA();
/*  53 */       this.inventoryUI.setItem(st, (InventoryUI.ClickableItem)new InventoryUI.AbstractClickableItem(itemBuilder.addFlag(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES }).setLore(new String[] { "§e点击查看!" }).toItemStack())
/*     */           {
/*     */             public void onClick(InventoryClickEvent e) {
/*     */               try {
/*  57 */                 ShopBasic clazz = ((Class<ShopBasic>)group.getB()).getConstructor(new Class[] { Player.class }).newInstance(new Object[] { ShopBasic.access$000(this.this$0) });
/*  58 */                 clazz.open();
/*  59 */               } catch (InstantiationException|IllegalAccessException|java.lang.reflect.InvocationTargetException|NoSuchMethodException instantiationException) {
/*  60 */                 instantiationException.printStackTrace();
/*     */               } 
/*     */             }
/*     */           });
/*     */       
/*  65 */       st++;
/*     */     } 
/*  67 */     this.inventoryUI.getItem(this.slot.intValue()).setItemStack((new ItemBuilder(this.inventoryUI.getItem(this.slot.intValue()).getItemStack())).clearLores().toItemStack());
/*  68 */     for (int i = 9; i <= 17; i++) {
/*  69 */       if (i - 9 == this.slot.intValue()) {
/*  70 */         this.inventoryUI.setItem(i, (InventoryUI.ClickableItem)new InventoryUI.EmptyClickableItem((new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte)13))
/*  71 */               .setName("&8⬆ &7类别")
/*  72 */               .setLore(new String[] { "§8⬇ §7物品"
/*  73 */                 }).toItemStack()));
/*     */       } else {
/*  75 */         this.inventoryUI.setItem(i, (InventoryUI.ClickableItem)new InventoryUI.EmptyClickableItem((new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte)7))
/*  76 */               .setName("&8⬆ &7类别")
/*  77 */               .setLore(new String[] { "§8⬇ §7物品"
/*  78 */                 }).toItemStack()));
/*     */       } 
/*     */     } 
/*  81 */     ArrayList<InventoryUI.ClickableItem> product = product(this.pd);
/*  82 */     for (int j = 18; j < 54; j++) {
/*  83 */       if (j % 9 != 0 && j % 9 != 8) {
/*  84 */         if (product.size() == 0)
/*  85 */           break;  this.inventoryUI.setItem(j, product.get(0));
/*  86 */         product.remove(0);
/*     */       } 
/*     */     } 
/*  89 */     this.inventoryUI.setItem(45, (InventoryUI.ClickableItem)new InventoryUI.AbstractClickableItem((new ItemBuilder(Material.COMPASS)).setName("§a追踪器商店")
/*  90 */           .addLoreLine("§7为你的指南针购买追踪升级, 升级后之后")
/*  91 */           .addLoreLine("§7你可以追踪特定队伍的某位玩家, 死亡后失效")
/*  92 */           .addLoreLine("")
/*  93 */           .addLoreLine("§e点击打开!").toItemStack())
/*     */         {
/*     */           public void onClick(InventoryClickEvent e) {
/*  96 */             (new TrackerShop(ShopBasic.this.p, new CompassMenu(ShopBasic.this.p))).open();
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void InventoryClick(InventoryClickEvent e) {
/* 103 */     if (e.getSlotType() == InventoryType.SlotType.OUTSIDE) {
/* 104 */       ArrayList<Class<? extends ShopBasic>> clazzes = (ArrayList<Class<? extends ShopBasic>>)shops.values().stream().map(Group::getB).collect(Collectors.toCollection(ArrayList::new));
/* 105 */       int i = clazzes.indexOf(getClass());
/* 106 */       System.out.println(i);
/* 107 */       switch (e.getClick()) {
/*     */         case LEFT:
/* 109 */           if (i > 0) {
/*     */             try {
/* 111 */               ShopBasic clazz = ((Class<ShopBasic>)clazzes.get(i - 1)).getConstructor(new Class[] { Player.class }).newInstance(new Object[] { this.p });
/* 112 */               clazz.open();
/* 113 */             } catch (InstantiationException|IllegalAccessException|java.lang.reflect.InvocationTargetException|NoSuchMethodException instantiationException) {
/* 114 */               instantiationException.printStackTrace();
/*     */             } 
/*     */           }
/*     */           break;
/*     */         case RIGHT:
/* 119 */           if (i < clazzes.size() - 1) {
/*     */             try {
/* 121 */               ShopBasic clazz = ((Class<ShopBasic>)clazzes.get(i + 1)).getConstructor(new Class[] { Player.class }).newInstance(new Object[] { this.p });
/* 122 */               clazz.open();
/* 123 */             } catch (InstantiationException|IllegalAccessException|java.lang.reflect.InvocationTargetException|NoSuchMethodException instantiationException) {
/* 124 */               instantiationException.printStackTrace();
/*     */             } 
/*     */           }
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setShop(Integer slot, Group<ItemBuilder, Class<? extends ShopBasic>> group) {
/* 136 */     shops.put(slot, group);
/*     */   }
/*     */   
/*     */   protected abstract ArrayList<InventoryUI.ClickableItem> product(PlayerData paramPlayerData);
/*     */ }
