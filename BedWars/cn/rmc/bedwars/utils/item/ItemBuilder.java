/*     */ package cn.rmc.bedwars.utils.item;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Color;
/*     */ import org.bukkit.DyeColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.inventory.ItemFlag;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.inventory.meta.LeatherArmorMeta;
/*     */ import org.bukkit.inventory.meta.SkullMeta;
/*     */ import org.bukkit.map.MapView;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemBuilder
/*     */ {
/*     */   private ItemStack is;
/*     */   
/*     */   public ItemBuilder(Material m) {
/*  34 */     this(m, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder(ItemStack is) {
/*  41 */     this.is = is;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder(Material m, int amount) {
/*  49 */     this.is = new ItemStack(m, amount);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder(Material m, int amount, byte durability) {
/*  58 */     this.is = new ItemStack(m, amount, (short)durability);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder clone() {
/*  65 */     return new ItemBuilder(this.is);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder setDurability(short dur) {
/*  72 */     this.is.setDurability(dur);
/*  73 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder setName(String name) {
/*  80 */     ItemMeta im = this.is.getItemMeta();
/*  81 */     im.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
/*  82 */     this.is.setItemMeta(im);
/*  83 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder addFlag(ItemFlag... flag) {
/*  90 */     ItemMeta im = this.is.getItemMeta();
/*  91 */     im.addItemFlags(flag);
/*  92 */     this.is.setItemMeta(im);
/*  93 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder addUnsafeEnchantment(Enchantment ench, int level) {
/* 101 */     this.is.addUnsafeEnchantment(ench, level);
/* 102 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder clearLores() {
/* 109 */     ItemMeta im = this.is.getItemMeta();
/* 110 */     im.setLore(new ArrayList());
/* 111 */     this.is.setItemMeta(im);
/* 112 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder removeEnchantment(Enchantment ench) {
/* 119 */     this.is.removeEnchantment(ench);
/* 120 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder removeEnchantments() {
/* 126 */     ItemMeta im = this.is.getItemMeta();
/* 127 */     im.getEnchants().forEach((enchantment, integer) -> im.removeEnchant(enchantment));
/* 128 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder setSkullOwner(String owner) {
/*     */     try {
/* 136 */       SkullMeta im = (SkullMeta)this.is.getItemMeta();
/* 137 */       im.setOwner(owner);
/* 138 */       this.is.setItemMeta((ItemMeta)im);
/* 139 */     } catch (ClassCastException classCastException) {}
/* 140 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder addEnchant(Enchantment ench, int level) {
/* 148 */     ItemMeta im = this.is.getItemMeta();
/* 149 */     im.addEnchant(ench, level, true);
/* 150 */     this.is.setItemMeta(im);
/* 151 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder addEnchantments(Map<Enchantment, Integer> enchantments) {
/* 158 */     this.is.addEnchantments(enchantments);
/* 159 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder setInfinityDurability() {
/* 165 */     this.is.setDurability('翿');
/* 166 */     return this;
/*     */   }
/*     */   public ItemBuilder setUnbreakable() {
/* 169 */     ItemMeta im = this.is.getItemMeta();
/* 170 */     im.spigot().setUnbreakable(true);
/* 171 */     this.is.setItemMeta(im);
/* 172 */     return this;
/*     */   }
/*     */   public ItemBuilder setType(Material material) {
/* 175 */     this.is.setType(material);
/* 176 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder setLore(String... lore) {
/* 183 */     ItemMeta im = this.is.getItemMeta();
/* 184 */     im.setLore(Arrays.asList(lore));
/* 185 */     this.is.setItemMeta(im);
/* 186 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder setLore(List<String> lore) {
/* 193 */     ItemMeta im = this.is.getItemMeta();
/* 194 */     im.setLore(lore);
/* 195 */     this.is.setItemMeta(im);
/* 196 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder setLore(int slot, String str) {
/* 206 */     ItemMeta im = this.is.getItemMeta();
/* 207 */     List<String> lore = im.getLore();
/* 208 */     lore.set(slot, str);
/* 209 */     im.setLore(lore);
/* 210 */     this.is.setItemMeta(im);
/* 211 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder removeLoreLine(String line) {
/* 217 */     ItemMeta im = this.is.getItemMeta();
/* 218 */     List<String> lore = new ArrayList<>(im.getLore());
/* 219 */     if (!lore.contains(line)) return this; 
/* 220 */     lore.remove(line);
/* 221 */     im.setLore(lore);
/* 222 */     this.is.setItemMeta(im);
/* 223 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder removeLoreLine(int index) {
/* 230 */     ItemMeta im = this.is.getItemMeta();
/* 231 */     List<String> lore = new ArrayList<>(im.getLore());
/* 232 */     if (index < 0 || index > lore.size()) return this; 
/* 233 */     lore.remove(index);
/* 234 */     im.setLore(lore);
/* 235 */     this.is.setItemMeta(im);
/* 236 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder addLoreLine(String line) {
/* 243 */     ItemMeta im = this.is.getItemMeta();
/* 244 */     List<String> lore = new ArrayList<>();
/* 245 */     if (im.hasLore()) lore = new ArrayList<>(im.getLore()); 
/* 246 */     lore.add(line);
/* 247 */     im.setLore(lore);
/* 248 */     this.is.setItemMeta(im);
/* 249 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder addLoreLine(String line, int pos) {
/* 257 */     ItemMeta im = this.is.getItemMeta();
/* 258 */     List<String> lore = new ArrayList<>(im.getLore());
/* 259 */     lore.add(pos, line);
/* 260 */     im.setLore(lore);
/* 261 */     this.is.setItemMeta(im);
/* 262 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder setDyeColor(DyeColor color) {
/* 270 */     this.is.setDurability((short)color.getData());
/* 271 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public ItemBuilder setWoolColor(DyeColor color) {
/* 281 */     if (!this.is.getType().equals(Material.WOOL)) return this; 
/* 282 */     this.is.setDurability((short)color.getData());
/* 283 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder setLeatherArmorColor(Color color) {
/*     */     try {
/* 291 */       LeatherArmorMeta im = (LeatherArmorMeta)this.is.getItemMeta();
/* 292 */       im.setColor(color);
/* 293 */       this.is.setItemMeta((ItemMeta)im);
/* 294 */     } catch (ClassCastException classCastException) {}
/*     */     
/* 296 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder setMapView(MapView mapView) {
/* 306 */     setDurability(mapView.getId());
/* 307 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack toItemStack() {
/* 326 */     return this.is;
/*     */   }
/*     */ }