/*     */ package cn.rmc.bedwars.utils.inventory;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import cn.rmc.bedwars.utils.item.ItemUtil;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.event.inventory.InventoryClickEvent;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.InventoryHolder;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ public abstract class InventoryUI {
/*     */   private String symbol;
/*     */   private final String title;
/*     */   private final int rowOffset;
/*     */   private final int rows;
/*     */   private int offset;
/*     */   private int page;
/*  20 */   private final List<Inventory2D> inventories = new LinkedList<>(); public List<Inventory2D> getInventories() { return this.inventories; }
/*     */ 
/*     */   
/*  23 */   public String getSymbol() { return this.symbol; } public String getTitle() {
/*  24 */     return this.title;
/*     */   }
/*  26 */   public int getRowOffset() { return this.rowOffset; } public int getRows() {
/*  27 */     return this.rows;
/*     */   }
/*  29 */   public void setOffset(int offset) { this.offset = offset; }
/*  30 */   public int getOffset() { return this.offset; } public int getPage() {
/*  31 */     return this.page;
/*     */   }
/*     */   public InventoryUI(String title, int rows) {
/*  34 */     this(title, rows, 0);
/*     */   }
/*     */   
/*     */   public InventoryUI(String title, int rows, String symbol) {
/*  38 */     this(title, rows, 0);
/*  39 */     this.symbol = symbol;
/*     */   }
/*     */   
/*     */   public InventoryUI(String title, boolean bool, int rows) {
/*  43 */     this(title, rows, 0);
/*     */   }
/*     */   
/*     */   public InventoryUI(String title, int rows, int rowOffset) {
/*  47 */     this.title = title;
/*  48 */     this.rows = rows;
/*  49 */     this.rowOffset = rowOffset;
/*     */   }
/*     */   
/*     */   public Inventory2D getCurrentUI() {
/*  53 */     return this.inventories.get(this.page);
/*     */   }
/*     */   
/*     */   public Inventory getCurrentPage() {
/*  57 */     if (this.inventories.size() == 0) {
/*  58 */       createNewInventory();
/*     */     }
/*     */     
/*  61 */     return ((Inventory2D)this.inventories.get(this.page)).toInventory();
/*     */   }
/*     */   
/*     */   public ClickableItem getItem(int slot) {
/*  65 */     if (this.inventories.size() == 0) {
/*  66 */       createNewInventory();
/*     */     }
/*     */     
/*  69 */     Inventory2D lastInventory = this.inventories.get(this.inventories.size() - 1);
/*  70 */     return lastInventory.getItem(slot);
/*     */   }
/*     */   
/*     */   public int getSize() {
/*  74 */     return this.rows * 9;
/*     */   }
/*     */   
/*     */   private void createNewInventory() {
/*  78 */     Inventory2D inventory = new Inventory2D(this.title, this.rows, this.rowOffset);
/*     */     
/*  80 */     if (this.inventories.size() > 0) {
/*  81 */       inventory.setItem(0, this.rows - 1, new AbstractClickableItem(
/*  82 */             ItemUtil.createItem(Material.ARROW, ChatColor.RED + "Page #" + this.inventories.size()))
/*     */           {
/*     */             public void onClick(InventoryClickEvent event) {
/*  85 */               InventoryUI.this.page--;
/*     */               try {
/*  87 */                 InventoryUI.Inventory2D inventory2D = InventoryUI.this.inventories.get(InventoryUI.this.page);
/*  88 */                 if (inventory2D == null) {
/*  89 */                   InventoryUI.this.page++;
/*     */                 } else {
/*  91 */                   event.getWhoClicked().openInventory(InventoryUI.this.getCurrentPage());
/*     */                 } 
/*  93 */               } catch (IndexOutOfBoundsException e) {
/*  94 */                 InventoryUI.this.page++;
/*     */               } 
/*     */             }
/*     */           });
/*     */       
/*  99 */       if (inventory.currentY == this.rows - 1 && inventory.currentX == -1) {
/* 100 */         inventory.currentX++;
/*     */       }
/*     */     } 
/*     */     
/* 104 */     this.inventories.add(inventory);
/*     */   }
/*     */   
/*     */   public void setItem(int x, int y, ClickableItem item) {
/* 108 */     if (this.inventories.size() == 0) {
/* 109 */       createNewInventory();
/*     */     }
/*     */     
/* 112 */     Inventory2D lastInventory = this.inventories.get(this.inventories.size() - 1);
/* 113 */     lastInventory.setItem(x - 1, y - 1, item);
/*     */   }
/*     */   
/*     */   public void setItem(int slot, ClickableItem item) {
/* 117 */     if (this.inventories.size() == 0) {
/* 118 */       createNewInventory();
/*     */     }
/*     */     
/* 121 */     Inventory2D lastInventory = this.inventories.get(this.inventories.size() - 1);
/* 122 */     lastInventory.setItem(slot, item);
/*     */   }
/*     */   
/*     */   public void addItem(ClickableItem item) {
/* 126 */     if (this.inventories.size() == 0) {
/* 127 */       createNewInventory();
/*     */     }
/*     */     
/* 130 */     Inventory2D lastInventory = this.inventories.get(this.inventories.size() - 1);
/*     */     
/* 132 */     if (lastInventory.currentY == this.rows - 1 && lastInventory.currentX >= 7 - this.offset) {
/* 133 */       lastInventory.setItem(8, this.rows - 1, new AbstractClickableItem(
/* 134 */             ItemUtil.createItem(Material.ARROW, ChatColor.RED + "Page #" + (this.inventories.size() + 1)))
/*     */           {
/*     */             public void onClick(InventoryClickEvent event) {
/* 137 */               InventoryUI.this.page++;
/*     */               try {
/* 139 */                 InventoryUI.Inventory2D inventory2D = InventoryUI.this.inventories.get(InventoryUI.this.page);
/* 140 */                 if (inventory2D == null) {
/* 141 */                   InventoryUI.this.page--;
/*     */                 } else {
/* 143 */                   event.getWhoClicked().openInventory(InventoryUI.this.getCurrentPage());
/*     */                 } 
/* 145 */               } catch (IndexOutOfBoundsException e) {
/* 146 */                 InventoryUI.this.page--;
/*     */               } 
/*     */             }
/*     */           });
/* 150 */       createNewInventory();
/* 151 */       addItem(item);
/*     */     } else {
/* 153 */       lastInventory.setItem(++lastInventory.currentX + this.offset, lastInventory.currentY, item);
/*     */     } 
/*     */     
/* 156 */     if (lastInventory.currentX >= 8 - this.offset) {
/* 157 */       lastInventory.currentX = this.offset - 1;
/* 158 */       lastInventory.currentY++;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void removeItem(int slot) {
/* 163 */     Inventory2D inventory2D = this.inventories.get(this.page);
/* 164 */     setItem(slot, null);
/* 165 */     for (int i = slot + 1; i < getSize(); i++) {
/* 166 */       ClickableItem item = getItem(i);
/*     */       
/* 168 */       setItem(i - 1, item);
/* 169 */       setItem(i, null);
/*     */     } 
/* 171 */     if (inventory2D.currentX >= 0) {
/* 172 */       inventory2D.currentX--;
/*     */     }
/* 174 */     else if (inventory2D.currentY > 0) {
/* 175 */       inventory2D.currentY--;
/* 176 */       inventory2D.currentX = 7;
/*     */     } 
/*     */   }
/*     */   
/*     */   public abstract void onInventoryClick(InventoryClickEvent paramInventoryClickEvent);
/*     */   
/*     */   public static interface ClickableItem {
/*     */     void onClick(InventoryClickEvent param1InventoryClickEvent);
/*     */     
/*     */     ItemStack getItemStack();
/*     */     
/*     */     void setItemStack(ItemStack param1ItemStack);
/*     */     
/*     */     ItemStack getDefaultItemStack();
/*     */   }
/*     */   
/*     */   public static class EmptyClickableItem implements ClickableItem {
/*     */     private final ItemStack defaultItemStack;
/*     */     private ItemStack itemStack;
/*     */     
/*     */     public void setItemStack(ItemStack itemStack) {
/* 197 */       this.itemStack = itemStack;
/*     */     }
/*     */     
/* 200 */     public ItemStack getDefaultItemStack() { return this.defaultItemStack; } public ItemStack getItemStack() {
/* 201 */       return this.itemStack;
/*     */     }
/*     */     public EmptyClickableItem(ItemStack itemStack) {
/* 204 */       this.itemStack = itemStack;
/* 205 */       this.defaultItemStack = itemStack;
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
/* 216 */       this.itemStack = itemStack;
/*     */     }
/*     */     
/* 219 */     public ItemStack getDefaultItemStack() { return this.defaultItemStack; } public ItemStack getItemStack() {
/* 220 */       return this.itemStack;
/*     */     }
/*     */     public AbstractClickableItem(ItemStack itemStack) {
/* 223 */       this.itemStack = itemStack;
/* 224 */       this.defaultItemStack = itemStack;
/*     */     } }
/*     */   public static class InventoryUIHolder implements InventoryHolder { private InventoryUI inventoryUI;
/*     */     private final String title;
/*     */     private final int slots;
/*     */     
/* 230 */     public void setInventoryUI(InventoryUI inventoryUI) { this.inventoryUI = inventoryUI; } public InventoryUI getInventoryUI() {
/* 231 */       return this.inventoryUI;
/*     */     }
/* 233 */     public String getTitle() { return this.title; } public int getSlots() {
/* 234 */       return this.slots;
/*     */     }
/*     */     private InventoryUIHolder(InventoryUI inventoryUI, String title, int slots) {
/* 237 */       this.inventoryUI = inventoryUI;
/* 238 */       this.title = title;
/* 239 */       this.slots = slots;
/*     */     }
/*     */ 
/*     */     
/*     */     public Inventory getInventory() {
/* 244 */       return this.inventoryUI.getCurrentPage();
/*     */     } }
/*     */   public class Inventory2D { private final InventoryUI.ClickableItem[][] items;
/*     */     private final String title;
/*     */     private final int rows;
/*     */     private Inventory cachedInventory;
/*     */     
/* 251 */     public InventoryUI.ClickableItem[][] getItems() { return this.items; }
/* 252 */     public String getTitle() { return this.title; } public int getRows() {
/* 253 */       return this.rows;
/*     */     } public Inventory getCachedInventory() {
/* 255 */       return this.cachedInventory;
/* 256 */     } private int currentX = -1; private int currentY; public int getCurrentX() { return this.currentX; } public int getCurrentY() {
/* 257 */       return this.currentY;
/*     */     }
/*     */     public Inventory2D(String title, int rows, int rowOffset) {
/* 260 */       this.currentY = rowOffset;
/* 261 */       this.title = title;
/* 262 */       this.rows = rows;
/* 263 */       this.items = new InventoryUI.ClickableItem[9][this.rows];
/*     */     }
/*     */     
/*     */     public void setItem(int x, int y, InventoryUI.ClickableItem clickableItem) {
/* 267 */       this.items[x][y] = clickableItem;
/*     */       
/* 269 */       if (this.cachedInventory != null) {
/* 270 */         int slot = y * 9 + x;
/*     */         
/* 272 */         this.cachedInventory.setItem(slot, (clickableItem != null) ? clickableItem.getItemStack() : null);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void setItem(int slot, InventoryUI.ClickableItem clickableItem) {
/* 277 */       int y = Math.abs(slot / 9);
/* 278 */       int x = -(y * 9 - slot);
/*     */       
/* 280 */       setItem(x, y, clickableItem);
/*     */     }
/*     */     
/*     */     public InventoryUI.ClickableItem getItem(int slot) {
/* 284 */       int y = Math.abs(slot / 9);
/* 285 */       int x = -(y * 9 - slot);
/* 286 */       if (this.items.length <= x) {
/* 287 */         return null;
/*     */       }
/* 289 */       InventoryUI.ClickableItem[] items = this.items[x];
/* 290 */       if (items.length <= y) {
/* 291 */         return null;
/*     */       }
/* 293 */       return items[y];
/*     */     }
/*     */     
/*     */     public Inventory toInventory() {
/* 297 */       if (this.cachedInventory != null) {
/* 298 */         return this.cachedInventory;
/*     */       }
/*     */       
/* 301 */       Inventory inventory = Bukkit.getServer().createInventory(new InventoryUI.InventoryUIHolder(InventoryUI.this, this.title, this.rows * 9), this.rows * 9, this.title);
/*     */       
/* 303 */       for (int y = 0; y < this.rows; y++) {
/* 304 */         for (int x = 0; x < 9; x++) {
/* 305 */           int slot = y * 9 + x;
/* 306 */           InventoryUI.ClickableItem item = this.items[x][y];
/* 307 */           if (item != null) {
/* 308 */             inventory.setItem(slot, item.getItemStack());
/*     */           }
/*     */         } 
/*     */       } 
/* 312 */       this.cachedInventory = inventory;
/* 313 */       return inventory;
/*     */     } }
/*     */ 
/*     */ }