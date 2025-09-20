/*     */ package cn.rmc.bedwarslobby.util.inventory;
/*     */ 
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import cn.rmc.bedwarslobby.util.ItemUtil;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.event.inventory.InventoryClickEvent;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.InventoryHolder;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ public class InventoryUI {
/*     */   private final String title;
/*     */   private final int rowOffset;
/*     */   private final int rows;
/*     */   private int offset;
/*     */   private int page;
/*  20 */   private final List<Inventory2D> inventories = new LinkedList<>(); public List<Inventory2D> getInventories() { return this.inventories; }
/*     */   
/*     */   public String getTitle() {
/*  23 */     return this.title;
/*     */   }
/*  25 */   public int getRowOffset() { return this.rowOffset; } public int getRows() {
/*  26 */     return this.rows;
/*     */   }
/*  28 */   public void setOffset(int offset) { this.offset = offset; }
/*  29 */   public int getOffset() { return this.offset; } public int getPage() {
/*  30 */     return this.page;
/*     */   }
/*     */   public InventoryUI(String title, int rows) {
/*  33 */     this(title, rows, 0);
/*     */   }
/*     */   
/*     */   public InventoryUI(String title, boolean bool, int rows) {
/*  37 */     this(title, rows, 0);
/*     */   }
/*     */   
/*     */   public InventoryUI(String title, int rows, int rowOffset) {
/*  41 */     this.title = title;
/*  42 */     this.rows = rows;
/*  43 */     this.rowOffset = rowOffset;
/*     */   }
/*     */   
/*     */   public Inventory2D getCurrentUI() {
/*  47 */     return this.inventories.get(this.page);
/*     */   }
/*     */   
/*     */   public Inventory getCurrentPage() {
/*  51 */     if (this.inventories.size() == 0) {
/*  52 */       createNewInventory();
/*     */     }
/*     */     
/*  55 */     return ((Inventory2D)this.inventories.get(this.page)).toInventory();
/*     */   }
/*     */   
/*     */   public ClickableItem getItem(int slot) {
/*  59 */     if (this.inventories.size() == 0) {
/*  60 */       createNewInventory();
/*     */     }
/*     */     
/*  63 */     Inventory2D lastInventory = this.inventories.get(this.inventories.size() - 1);
/*  64 */     return lastInventory.getItem(slot);
/*     */   }
/*     */   
/*     */   public int getSize() {
/*  68 */     return this.rows * 9;
/*     */   }
/*     */   
/*     */   private void createNewInventory() {
/*  72 */     Inventory2D inventory = new Inventory2D(this.title, this.rows, this.rowOffset);
/*     */     
/*  74 */     if (this.inventories.size() > 0) {
/*  75 */       inventory.setItem(0, this.rows - 1, new AbstractClickableItem(
/*  76 */             ItemUtil.createItem(Material.ARROW, ChatColor.RED + "Page #" + this.inventories.size()))
/*     */           {
/*     */             public void onClick(InventoryClickEvent event) {
/*  79 */               InventoryUI.this.page--;
/*     */               try {
/*  81 */                 InventoryUI.Inventory2D inventory2D = InventoryUI.this.inventories.get(InventoryUI.this.page);
/*  82 */                 if (inventory2D == null) {
/*  83 */                   InventoryUI.this.page++;
/*     */                 } else {
/*  85 */                   event.getWhoClicked().openInventory(InventoryUI.this.getCurrentPage());
/*     */                 } 
/*  87 */               } catch (IndexOutOfBoundsException e) {
/*  88 */                 InventoryUI.this.page++;
/*     */               } 
/*     */             }
/*     */           });
/*     */       
/*  93 */       if (inventory.currentY == this.rows - 1 && inventory.currentX == -1) {
/*  94 */         inventory.currentX++;
/*     */       }
/*     */     } 
/*     */     
/*  98 */     this.inventories.add(inventory);
/*     */   }
/*     */   
/*     */   public void setItem(int x, int y, ClickableItem item) {
/* 102 */     if (this.inventories.size() == 0) {
/* 103 */       createNewInventory();
/*     */     }
/*     */     
/* 106 */     Inventory2D lastInventory = this.inventories.get(this.inventories.size() - 1);
/* 107 */     lastInventory.setItem(x - 1, y - 1, item);
/*     */   }
/*     */   
/*     */   public void setItem(int slot, ClickableItem item) {
/* 111 */     if (this.inventories.size() == 0) {
/* 112 */       createNewInventory();
/*     */     }
/*     */     
/* 115 */     Inventory2D lastInventory = this.inventories.get(this.inventories.size() - 1);
/* 116 */     lastInventory.setItem(slot, item);
/*     */   }
/*     */   
/*     */   public void addItem(ClickableItem item) {
/* 120 */     if (this.inventories.size() == 0) {
/* 121 */       createNewInventory();
/*     */     }
/*     */     
/* 124 */     Inventory2D lastInventory = this.inventories.get(this.inventories.size() - 1);
/*     */     
/* 126 */     if (lastInventory.currentY == this.rows - 1 && lastInventory.currentX >= 7 - this.offset) {
/* 127 */       lastInventory.setItem(8, this.rows - 1, new AbstractClickableItem(
/* 128 */             ItemUtil.createItem(Material.ARROW, ChatColor.RED + "Page #" + (this.inventories.size() + 1)))
/*     */           {
/*     */             public void onClick(InventoryClickEvent event) {
/* 131 */               InventoryUI.this.page++;
/*     */               try {
/* 133 */                 InventoryUI.Inventory2D inventory2D = InventoryUI.this.inventories.get(InventoryUI.this.page);
/* 134 */                 if (inventory2D == null) {
/* 135 */                   InventoryUI.this.page--;
/*     */                 } else {
/* 137 */                   event.getWhoClicked().openInventory(InventoryUI.this.getCurrentPage());
/*     */                 } 
/* 139 */               } catch (IndexOutOfBoundsException e) {
/* 140 */                 InventoryUI.this.page--;
/*     */               } 
/*     */             }
/*     */           });
/* 144 */       createNewInventory();
/* 145 */       addItem(item);
/*     */     } else {
/* 147 */       lastInventory.setItem(++lastInventory.currentX + this.offset, lastInventory.currentY, item);
/*     */     } 
/*     */     
/* 150 */     if (lastInventory.currentX >= 8 - this.offset) {
/* 151 */       lastInventory.currentX = this.offset - 1;
/* 152 */       lastInventory.currentY++;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void removeItem(int slot) {
/* 157 */     Inventory2D inventory2D = this.inventories.get(this.page);
/* 158 */     setItem(slot, null);
/* 159 */     for (int i = slot + 1; i < getSize(); i++) {
/* 160 */       ClickableItem item = getItem(i);
/*     */       
/* 162 */       setItem(i - 1, item);
/* 163 */       setItem(i, null);
/*     */     } 
/* 165 */     if (inventory2D.currentX >= 0) {
/* 166 */       inventory2D.currentX--;
/*     */     }
/* 168 */     else if (inventory2D.currentY > 0) {
/* 169 */       inventory2D.currentY--;
/* 170 */       inventory2D.currentX = 7;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static interface ClickableItem {
/*     */     void onClick(InventoryClickEvent param1InventoryClickEvent);
/*     */     
/*     */     ItemStack getItemStack();
/*     */     
/*     */     void setItemStack(ItemStack param1ItemStack);
/*     */     
/*     */     ItemStack getDefaultItemStack(); }
/*     */   
/*     */   public static class EmptyClickableItem implements ClickableItem {
/*     */     private final ItemStack defaultItemStack;
/*     */     private ItemStack itemStack;
/*     */     
/*     */     public void setItemStack(ItemStack itemStack) {
/* 188 */       this.itemStack = itemStack;
/*     */     }
/*     */     
/* 191 */     public ItemStack getDefaultItemStack() { return this.defaultItemStack; } public ItemStack getItemStack() {
/* 192 */       return this.itemStack;
/*     */     }
/*     */     public EmptyClickableItem(ItemStack itemStack) {
/* 195 */       this.itemStack = itemStack;
/* 196 */       this.defaultItemStack = itemStack;
/*     */     }
/*     */     
/*     */     public void onClick(InventoryClickEvent event) {}
/*     */   }
/*     */   
/*     */   public static abstract class AbstractClickableItem implements ClickableItem {
/*     */     private final ItemStack defaultItemStack;
/*     */     private ItemStack itemStack;
/*     */     
/*     */     public void setItemStack(ItemStack itemStack) {
/* 207 */       this.itemStack = itemStack;
/*     */     }
/*     */     
/* 210 */     public ItemStack getDefaultItemStack() { return this.defaultItemStack; } public ItemStack getItemStack() {
/* 211 */       return this.itemStack;
/*     */     }
/*     */     public AbstractClickableItem(ItemStack itemStack) {
/* 214 */       this.itemStack = itemStack;
/* 215 */       this.defaultItemStack = itemStack;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class InventoryUIHolder implements InventoryHolder { private InventoryUI inventoryUI;
/*     */     
/* 221 */     public void setInventoryUI(InventoryUI inventoryUI) { this.inventoryUI = inventoryUI; } private String title; private int slots; public InventoryUI getInventoryUI() {
/* 222 */       return this.inventoryUI;
/*     */     }
/* 224 */     public String getTitle() { return this.title; } public int getSlots() {
/* 225 */       return this.slots;
/*     */     }
/*     */     private InventoryUIHolder(InventoryUI inventoryUI, String title, int slots) {
/* 228 */       this.inventoryUI = inventoryUI;
/* 229 */       this.title = title;
/* 230 */       this.slots = slots;
/*     */     }
/*     */ 
/*     */     
/*     */     public Inventory getInventory() {
/* 235 */       return this.inventoryUI.getCurrentPage();
/*     */     } }
/*     */   public class Inventory2D { private final InventoryUI.ClickableItem[][] items;
/*     */     private final String title;
/*     */     private final int rows;
/*     */     private Inventory cachedInventory;
/*     */     
/* 242 */     public InventoryUI.ClickableItem[][] getItems() { return this.items; }
/* 243 */     public String getTitle() { return this.title; } public int getRows() {
/* 244 */       return this.rows;
/*     */     } public Inventory getCachedInventory() {
/* 246 */       return this.cachedInventory;
/* 247 */     } private int currentX = -1; private int currentY; public int getCurrentX() { return this.currentX; } public int getCurrentY() {
/* 248 */       return this.currentY;
/*     */     }
/*     */     public Inventory2D(String title, int rows, int rowOffset) {
/* 251 */       this.currentY = rowOffset;
/* 252 */       this.title = title;
/* 253 */       this.rows = rows;
/* 254 */       this.items = new InventoryUI.ClickableItem[9][this.rows];
/*     */     }
/*     */     
/*     */     public void setItem(int x, int y, InventoryUI.ClickableItem clickableItem) {
/* 258 */       this.items[x][y] = clickableItem;
/*     */       
/* 260 */       if (this.cachedInventory != null) {
/* 261 */         int slot = y * 9 + x;
/*     */         
/* 263 */         this.cachedInventory.setItem(slot, (clickableItem != null) ? clickableItem.getItemStack() : null);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void setItem(int slot, InventoryUI.ClickableItem clickableItem) {
/* 268 */       int y = Math.abs(slot / 9);
/* 269 */       int x = -(y * 9 - slot);
/*     */       
/* 271 */       setItem(x, y, clickableItem);
/*     */     }
/*     */     
/*     */     public InventoryUI.ClickableItem getItem(int slot) {
/* 275 */       int y = Math.abs(slot / 9);
/* 276 */       int x = -(y * 9 - slot);
/* 277 */       if (this.items.length <= x) {
/* 278 */         return null;
/*     */       }
/* 280 */       InventoryUI.ClickableItem[] items = this.items[x];
/* 281 */       if (items.length <= y) {
/* 282 */         return null;
/*     */       }
/* 284 */       return items[y];
/*     */     }
/*     */     
/*     */     public Inventory toInventory() {
/* 288 */       if (this.cachedInventory != null) {
/* 289 */         return this.cachedInventory;
/*     */       }
/*     */       
/* 292 */       Inventory inventory = Bukkit.getServer().createInventory(new InventoryUI.InventoryUIHolder(InventoryUI.this, this.title, this.rows * 9), this.rows * 9, this.title);
/*     */       
/* 294 */       for (int y = 0; y < this.rows; y++) {
/* 295 */         for (int x = 0; x < 9; x++) {
/* 296 */           int slot = y * 9 + x;
/* 297 */           InventoryUI.ClickableItem item = this.items[x][y];
/* 298 */           if (item != null) {
/* 299 */             inventory.setItem(slot, item.getItemStack());
/*     */           }
/*     */         } 
/*     */       } 
/* 303 */       this.cachedInventory = inventory;
/* 304 */       return inventory;
/*     */     } }
/*     */ 
/*     */ }