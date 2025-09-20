/*     */ package cn.rmc.bedwarslobby.util;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.properties.Property;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.Arrays;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
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
/*     */ public final class ItemUtil {
/*     */   private ItemUtil() {
/*  21 */     throw new RuntimeException("Cannot instantiate a utility class.");
/*     */   }
/*     */   
/*     */   public static String formatMaterial(Material material) {
/*  25 */     String name = material.toString();
/*  26 */     name = name.replace('_', ' ');
/*  27 */     String result = "" + name.charAt(0);
/*     */     
/*  29 */     for (int i = 1; i < name.length(); i++) {
/*  30 */       if (name.charAt(i - 1) == ' ') {
/*  31 */         result = result + name.charAt(i);
/*     */       } else {
/*  33 */         result = result + Character.toLowerCase(name.charAt(i));
/*     */       } 
/*     */     } 
/*     */     
/*  37 */     return result;
/*     */   }
/*     */   
/*     */   public static ItemStack enchantItem(ItemStack itemStack, ItemEnchant... enchantments) {
/*  41 */     Arrays.<ItemEnchant>asList(enchantments).forEach(enchantment -> itemStack.addUnsafeEnchantment(enchantment.enchantment, enchantment.level));
/*  42 */     return itemStack;
/*     */   }
/*     */   public static ItemStack addPotionEffect(ItemStack itemStack, PotionEffect... potionEffect) {
/*  45 */     ItemStack result = itemStack;
/*  46 */     for (PotionEffect pe : potionEffect) {
/*  47 */       PotionMeta meta = (PotionMeta)result.getItemMeta();
/*  48 */       meta.addCustomEffect(pe, true);
/*  49 */       result.setItemMeta((ItemMeta)meta);
/*     */     } 
/*  51 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getName(ItemStack item) {
/*  56 */     if (item.getDurability() != 0) {
/*  57 */       String str = BukkitReflection.getItemStackName(item);
/*  58 */       if (str != null) {
/*  59 */         if (str.contains(".")) {
/*  60 */           str = WordUtils.capitalize(item.getType().toString().toLowerCase().replace("_", " "));
/*     */         }
/*     */         
/*  63 */         return str;
/*     */       } 
/*     */     } 
/*     */     
/*  67 */     String reflectedName = item.getType().toString().replace("_", " ");
/*  68 */     char[] chars = reflectedName.toLowerCase().toCharArray();
/*  69 */     boolean found = false;
/*     */     
/*  71 */     for (int i = 0; i < chars.length; i++) {
/*  72 */       if (!found && Character.isLetter(chars[i])) {
/*  73 */         chars[i] = Character.toUpperCase(chars[i]);
/*  74 */         found = true;
/*  75 */       } else if (Character.isWhitespace(chars[i]) || chars[i] == '.' || chars[i] == '\'') {
/*  76 */         found = false;
/*     */       } 
/*     */     } 
/*     */     
/*  80 */     return String.valueOf(chars);
/*     */   }
/*     */   
/*     */   public static ItemStack createItem(Material material, String name) {
/*  84 */     ItemStack item = new ItemStack(material);
/*  85 */     ItemMeta meta = item.getItemMeta();
/*  86 */     meta.setDisplayName(name);
/*  87 */     item.setItemMeta(meta);
/*  88 */     return item;
/*     */   }
/*     */   
/*     */   public static ItemStack createItem(Material material, String name, int amount) {
/*  92 */     ItemStack item = new ItemStack(material, amount);
/*  93 */     ItemMeta meta = item.getItemMeta();
/*  94 */     meta.setDisplayName(name);
/*  95 */     item.setItemMeta(meta);
/*  96 */     return item;
/*     */   }
/*     */   
/*     */   public static ItemStack createItem(Material material, String name, int amount, short damage) {
/* 100 */     ItemStack item = new ItemStack(material, amount, damage);
/* 101 */     ItemMeta meta = item.getItemMeta();
/* 102 */     meta.setDisplayName(name);
/* 103 */     item.setItemMeta(meta);
/* 104 */     return item;
/*     */   }
/*     */   
/*     */   public static ItemStack hideEnchants(ItemStack item) {
/* 108 */     ItemMeta meta = item.getItemMeta();
/* 109 */     meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE });
/* 110 */     item.setItemMeta(meta);
/* 111 */     return item;
/*     */   }
/*     */   
/*     */   public static ItemStack setUnbreakable(ItemStack item) {
/* 115 */     ItemMeta meta = item.getItemMeta();
/* 116 */     meta.spigot().setUnbreakable(true);
/* 117 */     meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_UNBREAKABLE });
/* 118 */     item.setItemMeta(meta);
/* 119 */     return item;
/*     */   }
/*     */   
/*     */   public static ItemStack renameItem(ItemStack item, String name) {
/* 123 */     ItemMeta meta = item.getItemMeta();
/* 124 */     meta.setDisplayName(name);
/* 125 */     item.setItemMeta(meta);
/* 126 */     return item;
/*     */   }
/*     */   
/*     */   public static ItemStack reloreItem(ItemStack item, String... lores) {
/* 130 */     return reloreItem(ReloreType.OVERWRITE, item, lores);
/*     */   }
/*     */   public static ItemStack reloreItem(ReloreType type, ItemStack item, String... lores) {
/*     */     List<String> nLore;
/* 134 */     ItemMeta meta = item.getItemMeta();
/* 135 */     List<String> lore = meta.getLore();
/* 136 */     if (lore == null) {
/* 137 */       lore = new LinkedList<>();
/*     */     }
/*     */     
/* 140 */     switch (type) {
/*     */       case APPEND:
/* 142 */         lore.addAll(Arrays.asList(lores));
/* 143 */         meta.setLore(lore);
/*     */         break;
/*     */       case PREPEND:
/* 146 */         nLore = new LinkedList<>(Arrays.asList(lores));
/* 147 */         nLore.addAll(lore);
/* 148 */         meta.setLore(nLore);
/*     */         break;
/*     */       case OVERWRITE:
/* 151 */         meta.setLore(Arrays.asList(lores));
/*     */         break;
/*     */     } 
/* 154 */     item.setItemMeta(meta);
/* 155 */     return item;
/*     */   }
/*     */   public static ItemStack getCustomSkull(String url) {
/* 158 */     ItemStack ib = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
/* 159 */     SkullMeta skull = (SkullMeta)ib.getItemMeta();
/* 160 */     GameProfile profile = new GameProfile(UUID.randomUUID(), null);
/* 161 */     profile.getProperties().put("textures", new Property("textures", url));
/*     */     
/* 163 */     try { Field profileField = skull.getClass().getDeclaredField("profile");
/* 164 */       profileField.setAccessible(true);
/* 165 */       profileField.set(skull, profile); }
/* 166 */     catch (NoSuchFieldException|IllegalAccessException e) { e.printStackTrace(); }
/* 167 */      ib.setItemMeta((ItemMeta)skull);
/* 168 */     return ib;
/*     */   }
/*     */   public static ItemStack addItemFlag(ItemStack item, ItemFlag flag) {
/* 171 */     ItemMeta meta = item.getItemMeta();
/* 172 */     meta.addItemFlags(new ItemFlag[] { flag });
/* 173 */     item.setItemMeta(meta);
/* 174 */     return item;
/*     */   }
/*     */   
/*     */   public static class ItemEnchant {
/*     */     private final Enchantment enchantment;
/*     */     private final int level;
/*     */     
/*     */     @ConstructorProperties({"enchantment", "level"})
/*     */     public ItemEnchant(Enchantment enchantment, int level) {
/* 183 */       this.enchantment = enchantment;
/* 184 */       this.level = level;
/*     */     }
/*     */   }
/*     */   
/*     */   public enum ReloreType {
/* 189 */     OVERWRITE,
/* 190 */     PREPEND,
/* 191 */     APPEND;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\BedWarsLobby-1.0-SNAPSHOT.jar!\me\kabusama\bedwarslobb\\util\ItemUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */