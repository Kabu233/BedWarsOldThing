/*     */ package cn.rmc.bedwarslobby.util;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.bukkit.Color;
/*     */ import org.bukkit.DyeColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.inventory.ItemFlag;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.inventory.meta.LeatherArmorMeta;
/*     */ import org.bukkit.inventory.meta.SkullMeta;
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
/*  31 */     this(m, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder(ItemStack is) {
/*  38 */     this.is = is;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder(Material m, int amount) {
/*  46 */     this.is = new ItemStack(m, amount);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder(Material m, int amount, byte durability) {
/*  55 */     this.is = new ItemStack(m, amount, (short)durability);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder clone() {
/*  62 */     return new ItemBuilder(this.is);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder setDurability(short dur) {
/*  69 */     this.is.setDurability(dur);
/*  70 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder setName(String name) {
/*  77 */     ItemMeta im = this.is.getItemMeta();
/*  78 */     im.setDisplayName(name);
/*  79 */     this.is.setItemMeta(im);
/*  80 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder addFlag(ItemFlag... flag) {
/*  87 */     ItemMeta im = this.is.getItemMeta();
/*  88 */     im.addItemFlags(flag);
/*  89 */     this.is.setItemMeta(im);
/*  90 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder addUnsafeEnchantment(Enchantment ench, int level) {
/*  98 */     this.is.addUnsafeEnchantment(ench, level);
/*  99 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder removeEnchantment(Enchantment ench) {
/* 106 */     this.is.removeEnchantment(ench);
/* 107 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder setSkullOwner(String owner) {
/*     */     try {
/* 115 */       SkullMeta im = (SkullMeta)this.is.getItemMeta();
/* 116 */       im.setOwner(owner);
/* 117 */       this.is.setItemMeta((ItemMeta)im);
/* 118 */     } catch (ClassCastException classCastException) {}
/* 119 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder addEnchant(Enchantment ench, int level) {
/* 127 */     ItemMeta im = this.is.getItemMeta();
/* 128 */     im.addEnchant(ench, level, true);
/* 129 */     this.is.setItemMeta(im);
/* 130 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder addEnchantments(Map<Enchantment, Integer> enchantments) {
/* 137 */     this.is.addEnchantments(enchantments);
/* 138 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder setInfinityDurability() {
/* 144 */     this.is.setDurability('翿');
/* 145 */     return this;
/*     */   }
/*     */   public ItemBuilder setType(Material material) {
/* 148 */     this.is.setType(material);
/* 149 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder setLore(String... lore) {
/* 156 */     ItemMeta im = this.is.getItemMeta();
/* 157 */     im.setLore(Arrays.asList(lore));
/* 158 */     this.is.setItemMeta(im);
/* 159 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder setLore(List<String> lore) {
/* 166 */     ItemMeta im = this.is.getItemMeta();
/* 167 */     im.setLore(lore);
/* 168 */     this.is.setItemMeta(im);
/* 169 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder removeLoreLine(String line) {
/* 175 */     ItemMeta im = this.is.getItemMeta();
/* 176 */     List<String> lore = new ArrayList<>(im.getLore());
/* 177 */     if (!lore.contains(line)) return this; 
/* 178 */     lore.remove(line);
/* 179 */     im.setLore(lore);
/* 180 */     this.is.setItemMeta(im);
/* 181 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder removeLoreLine(int index) {
/* 188 */     ItemMeta im = this.is.getItemMeta();
/* 189 */     List<String> lore = new ArrayList<>(im.getLore());
/* 190 */     if (index < 0 || index > lore.size()) return this; 
/* 191 */     lore.remove(index);
/* 192 */     im.setLore(lore);
/* 193 */     this.is.setItemMeta(im);
/* 194 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder addLoreLine(String line) {
/* 201 */     ItemMeta im = this.is.getItemMeta();
/* 202 */     List<String> lore = new ArrayList<>();
/* 203 */     if (im.hasLore()) lore = new ArrayList<>(im.getLore()); 
/* 204 */     lore.add(line);
/* 205 */     im.setLore(lore);
/* 206 */     this.is.setItemMeta(im);
/* 207 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder addLoreLine(String line, int pos) {
/* 215 */     ItemMeta im = this.is.getItemMeta();
/* 216 */     List<String> lore = new ArrayList<>(im.getLore());
/* 217 */     lore.set(pos, line);
/* 218 */     im.setLore(lore);
/* 219 */     this.is.setItemMeta(im);
/* 220 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder setDyeColor(DyeColor color) {
/* 229 */     this.is.setDurability((short)color.getData());
/* 230 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public ItemBuilder setWoolColor(DyeColor color) {
/* 240 */     if (!this.is.getType().equals(Material.WOOL)) return this; 
/* 241 */     this.is.setDurability((short)color.getData());
/* 242 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder setLeatherArmorColor(Color color) {
/*     */     try {
/* 250 */       LeatherArmorMeta im = (LeatherArmorMeta)this.is.getItemMeta();
/* 251 */       im.setColor(color);
/* 252 */       this.is.setItemMeta((ItemMeta)im);
/* 253 */     } catch (ClassCastException classCastException) {}
/* 254 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack toItemStack() {
/* 261 */     return this.is;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\BedWarsLobby-1.0-SNAPSHOT.jar!\me\kabusama\bedwarslobb\\util\ItemBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */