/*     */ package cn.rmc.bedwars.utils.item;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.properties.Property;
/*     */ import java.beans.ConstructorProperties;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.Arrays;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import cn.rmc.bedwars.utils.BukkitReflection;
/*     */ import org.apache.commons.lang.WordUtils;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.inventory.ItemFlag;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.inventory.meta.PotionMeta;
/*     */ import org.bukkit.inventory.meta.SkullMeta;
/*     */ import org.bukkit.potion.PotionEffect;
/*     */ 
/*     */ public final class ItemUtil
/*     */ {
/*     */   private ItemUtil() {
/*  25 */     throw new RuntimeException("Cannot instantiate a utility class.");
/*     */   }
/*     */   
/*     */   public static String formatMaterial(Material material) {
/*  29 */     String name = material.toString();
/*  30 */     name = name.replace('_', ' ');
/*  31 */     String result = "" + name.charAt(0);
/*     */     
/*  33 */     for (int i = 1; i < name.length(); i++) {
/*  34 */       if (name.charAt(i - 1) == ' ') {
/*  35 */         result = result + name.charAt(i);
/*     */       } else {
/*  37 */         result = result + Character.toLowerCase(name.charAt(i));
/*     */       } 
/*     */     } 
/*     */     
/*  41 */     return result;
/*     */   }
/*     */   
/*     */   public static String getLore(ItemStack is, int line) {
/*     */     try {
/*  46 */       return is.getItemMeta().getLore().get(line);
/*  47 */     } catch (Exception ex) {
/*  48 */       return "NULL";
/*     */     } 
/*     */   }
/*     */   
/*     */   public static ItemStack enchantItem(ItemStack itemStack, ItemEnchant... enchantments) {
/*  53 */     Arrays.<ItemEnchant>asList(enchantments).forEach(enchantment -> itemStack.addUnsafeEnchantment(enchantment.enchantment, enchantment.level));
/*  54 */     return itemStack;
/*     */   }
/*     */   public static ItemStack addPotionEffect(ItemStack itemStack, PotionEffect... potionEffect) {
/*  57 */     ItemStack result = itemStack;
/*  58 */     for (PotionEffect pe : potionEffect) {
/*  59 */       PotionMeta meta = (PotionMeta)result.getItemMeta();
/*  60 */       meta.addCustomEffect(pe, true);
/*  61 */       result.setItemMeta((ItemMeta)meta);
/*     */     } 
/*  63 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getName(ItemStack item) {
/*  68 */     if (item.getDurability() != 0) {
/*  69 */       String str = BukkitReflection.getItemStackName(item);
/*  70 */       if (str != null) {
/*  71 */         if (str.contains(".")) {
/*  72 */           str = WordUtils.capitalize(item.getType().toString().toLowerCase().replace("_", " "));
/*     */         }
/*     */         
/*  75 */         return str;
/*     */       } 
/*     */     } 
/*     */     
/*  79 */     String reflectedName = item.getType().toString().replace("_", " ");
/*  80 */     char[] chars = reflectedName.toLowerCase().toCharArray();
/*  81 */     boolean found = false;
/*     */     
/*  83 */     for (int i = 0; i < chars.length; i++) {
/*  84 */       if (!found && Character.isLetter(chars[i])) {
/*  85 */         chars[i] = Character.toUpperCase(chars[i]);
/*  86 */         found = true;
/*  87 */       } else if (Character.isWhitespace(chars[i]) || chars[i] == '.' || chars[i] == '\'') {
/*  88 */         found = false;
/*     */       } 
/*     */     } 
/*     */     
/*  92 */     return String.valueOf(chars);
/*     */   }
/*     */   
/*     */   public static ItemStack createItem(Material material, String name) {
/*  96 */     ItemStack item = new ItemStack(material);
/*  97 */     ItemMeta meta = item.getItemMeta();
/*  98 */     meta.setDisplayName(name);
/*  99 */     item.setItemMeta(meta);
/* 100 */     return item;
/*     */   }
/*     */   
/*     */   public static ItemStack createItem(Material material, String name, int amount) {
/* 104 */     ItemStack item = new ItemStack(material, amount);
/* 105 */     ItemMeta meta = item.getItemMeta();
/* 106 */     meta.setDisplayName(name);
/* 107 */     item.setItemMeta(meta);
/* 108 */     return item;
/*     */   }
/*     */   
/*     */   public static ItemStack createItem(Material material, String name, int amount, short damage) {
/* 112 */     ItemStack item = new ItemStack(material, amount, damage);
/* 113 */     ItemMeta meta = item.getItemMeta();
/* 114 */     meta.setDisplayName(name);
/* 115 */     item.setItemMeta(meta);
/* 116 */     return item;
/*     */   }
/*     */   
/*     */   public static ItemStack hideEnchants(ItemStack item) {
/* 120 */     ItemMeta meta = item.getItemMeta();
/* 121 */     meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE });
/* 122 */     item.setItemMeta(meta);
/* 123 */     return item;
/*     */   }
/*     */   
/*     */   public static ItemStack setUnbreakable(ItemStack item) {
/* 127 */     ItemMeta meta = item.getItemMeta();
/* 128 */     meta.spigot().setUnbreakable(true);
/* 129 */     meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_UNBREAKABLE });
/* 130 */     item.setItemMeta(meta);
/* 131 */     return item;
/*     */   }
/*     */   
/*     */   public static ItemStack renameItem(ItemStack item, String name) {
/* 135 */     ItemMeta meta = item.getItemMeta();
/* 136 */     meta.setDisplayName(name);
/* 137 */     item.setItemMeta(meta);
/* 138 */     return item;
/*     */   }
/*     */   
/*     */   public static ItemStack reloreItem(ItemStack item, String... lores) {
/* 142 */     return reloreItem(ReloreType.OVERWRITE, item, lores);
/*     */   }
/*     */   public static ItemStack reloreItem(ReloreType type, ItemStack item, String... lores) {
/*     */     List<String> nLore;
/* 146 */     ItemMeta meta = item.getItemMeta();
/* 147 */     List<String> lore = meta.getLore();
/* 148 */     if (lore == null) {
/* 149 */       lore = new LinkedList<>();
/*     */     }
/*     */     
/* 152 */     switch (type) {
/*     */       case APPEND:
/* 154 */         lore.addAll(Arrays.asList(lores));
/* 155 */         meta.setLore(lore);
/*     */         break;
/*     */       case PREPEND:
/* 158 */         nLore = new LinkedList<>(Arrays.asList(lores));
/* 159 */         nLore.addAll(lore);
/* 160 */         meta.setLore(nLore);
/*     */         break;
/*     */       case OVERWRITE:
/* 163 */         meta.setLore(Arrays.asList(lores));
/*     */         break;
/*     */     } 
/* 166 */     item.setItemMeta(meta);
/* 167 */     return item;
/*     */   }
/*     */   public static ItemStack getCustomSkull(String url) {
/* 170 */     ItemStack ib = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
/* 171 */     SkullMeta skull = (SkullMeta)ib.getItemMeta();
/* 172 */     GameProfile profile = new GameProfile(UUID.randomUUID(), null);
/* 173 */     profile.getProperties().put("textures", new Property("textures", url));
/*     */     
/* 175 */     try { Field profileField = skull.getClass().getDeclaredField("profile");
/* 176 */       profileField.setAccessible(true);
/* 177 */       profileField.set(skull, profile); }
/* 178 */     catch (NoSuchFieldException|IllegalAccessException e) { e.printStackTrace(); }
/* 179 */      ib.setItemMeta((ItemMeta)skull);
/* 180 */     return ib;
/*     */   }
/*     */   public static ItemStack addItemFlag(ItemStack item, ItemFlag flag) {
/* 183 */     ItemMeta meta = item.getItemMeta();
/* 184 */     meta.addItemFlags(new ItemFlag[] { flag });
/* 185 */     item.setItemMeta(meta);
/* 186 */     return item;
/*     */   }
/*     */   
/*     */   public static class ItemEnchant {
/*     */     private final Enchantment enchantment;
/*     */     private final int level;
/*     */     
/*     */     @ConstructorProperties({"enchantment", "level"})
/*     */     public ItemEnchant(Enchantment enchantment, int level) {
/* 195 */       this.enchantment = enchantment;
/* 196 */       this.level = level;
/*     */     }
/*     */   }
/*     */   
/*     */   public enum ReloreType {
/* 201 */     OVERWRITE,
/* 202 */     PREPEND,
/* 203 */     APPEND;
/*     */   }
/*     */ }