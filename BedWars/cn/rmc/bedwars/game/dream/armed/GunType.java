/*     */ package cn.rmc.bedwars.game.dream.armed;
/*     */ import cn.rmc.bedwars.enums.game.Resource;
/*     */ import cn.rmc.bedwars.game.PlayerData;
/*     */ import cn.rmc.bedwars.game.dream.armed.guns.Magnum;
/*     */ import cn.rmc.bedwars.game.shop.Price;
/*     */ import cn.rmc.bedwars.utils.item.ItemBuilder;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.inventory.ItemFlag;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ public enum GunType implements IShopItem {
/*     */   GunType(String displayName, Class<? extends GunBasic> clazz, Material material, Price price, Integer clipAmmo, Double damage, Integer maxClipAmmo, Double fireRate, Double reload, Integer range) {
/*     */     this.displayName = displayName;
/*     */     this.clazz = clazz;
/*     */     this.material = material;
/*     */     this.price = price;
/*     */     this.clipAmmo = clipAmmo;
/*     */     this.damage = damage;
/*     */     this.maxClipAmmo = maxClipAmmo;
/*     */     this.fireRate = fireRate;
/*     */     this.reload = reload;
/*     */     this.range = range;
/*     */   }
/*     */   
/*     */   String displayName;
/*     */   Class<? extends GunBasic> clazz;
/*  27 */   PISTOL("X型发射器", (Class)Pistol.class, Material.WOOD_HOE, null, 
/*  28 */     Integer.valueOf(12), Double.valueOf(4.0D), Integer.valueOf(12), Double.valueOf(0.4D), Double.valueOf(1.5D), Integer.valueOf(30)),
/*  29 */   MAGNUM("马格南发射器", (Class)Magnum.class, Material.GOLD_HOE, new Price(Resource.GOLD, Integer.valueOf(8)), 
/*  30 */     Integer.valueOf(0), Double.valueOf(6.0D), Integer.valueOf(6), Double.valueOf(0.6D), Double.valueOf(3.0D), Integer.valueOf(40)),
/*  31 */   RIFLE("连续发射器", (Class)Rifle.class, Material.STONE_HOE, new Price(Resource.GOLD, Integer.valueOf(8)), 
/*  32 */     Integer.valueOf(0), Double.valueOf(4.0D), Integer.valueOf(25), Double.valueOf(0.2D), Double.valueOf(3.0D), Integer.valueOf(40)),
/*  33 */   SMG("冲锋发射器", (Class)SMG.class, Material.DIAMOND_HOE, new Price(Resource.IRON, Integer.valueOf(50)), 
/*  34 */     Integer.valueOf(0), Double.valueOf(2.0D), Integer.valueOf(45), Double.valueOf(0.1D), Double.valueOf(2.0D), Integer.valueOf(30)),
/*  35 */   NOTAFLAMETHROWER("火焰喷射器", (Class)NotaFlamethrower.class, Material.FLINT_AND_STEEL, new Price(Resource.GOLD, Integer.valueOf(12)), 
/*  36 */     Integer.valueOf(350), Double.valueOf(2.0D), Integer.valueOf(50), Double.valueOf(0.1D), Double.valueOf(3.0D), Integer.valueOf(20)),
/*  37 */   SHOTGUN("散弹发射器", (Class)Shotgun.class, Material.IRON_HOE, new Price(Resource.EMERALD, Integer.valueOf(1)), 
/*  38 */     Integer.valueOf(0), Double.valueOf(2.0D), Integer.valueOf(4), Double.valueOf(1.0D), Double.valueOf(4.0D), Integer.valueOf(10)); Material material; Price price; Integer clipAmmo; Double damage; Integer maxClipAmmo; Double fireRate; Double reload; Integer range;
/*     */   public String getDisplayName() {
/*  40 */     return this.displayName;
/*     */   }
/*     */   public Class<? extends GunBasic> getClazz() {
/*  43 */     return this.clazz;
/*     */   } public Material getMaterial() {
/*  45 */     return this.material;
/*     */   } public Price getPrice() {
/*  47 */     return this.price;
/*     */   } public Integer getClipAmmo() {
/*  49 */     return this.clipAmmo;
/*     */   } public Double getDamage() {
/*  51 */     return this.damage;
/*     */   } public Integer getMaxClipAmmo() {
/*  53 */     return this.maxClipAmmo;
/*     */   } public Double getFireRate() {
/*  55 */     return this.fireRate;
/*     */   } public Double getReload() {
/*  57 */     return this.reload;
/*     */   } public Integer getRange() {
/*  59 */     return this.range;
/*     */   }
/*     */ 
/*     */   
/*     */   public HashMap<List<String>, Price> getPrices() {
/*  64 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack giveItem(PlayerData pd) {
/*  69 */     ItemBuilder builder = new ItemBuilder(this.material);
/*  70 */     builder.addFlag(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
/*  71 */     builder.setName("§6" + this.displayName);
/*  72 */     builder.addLoreLine("")
/*  73 */       .addLoreLine(" §8• §7发射器剩余数量: §a" + this.maxClipAmmo)
/*  74 */       .addLoreLine(" §8• §7发射器伤害: §a" + this.damage)
/*  75 */       .addLoreLine(" §8• §7发射器最大数量: §a" + this.maxClipAmmo)
/*  76 */       .addLoreLine(" §8• §7发射器射速: §a" + this.fireRate + "s")
/*  77 */       .addLoreLine(" §8• §7发射器安装速度: §a" + this.reload + "s")
/*  78 */       .addLoreLine(" §8• §7发射器射程距离: §a" + this.range)
/*  79 */       .addLoreLine("")
/*  80 */       .addLoreLine("§7" + UUID.randomUUID().toString().substring(0, 6));
/*  81 */     return builder.toItemStack();
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack showItem(PlayerData pd, Game g) {
/*  86 */     ItemBuilder builder = new ItemBuilder(this.material);
/*     */     
/*  88 */     builder.addFlag(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
/*  89 */     builder.setName("§6" + this.displayName);
/*  90 */     builder.addLoreLine(InventoryUtils.getPrice(pd, this).getDisplay());
/*  91 */     builder.addLoreLine("")
/*  92 */       .addLoreLine(" §8• §7发射器剩余数量: §a" + this.maxClipAmmo)
/*  93 */       .addLoreLine(" §8• §7发射器伤害: §a" + this.damage)
/*  94 */       .addLoreLine(" §8• §7发射器最大数量: §a" + this.maxClipAmmo)
/*  95 */       .addLoreLine(" §8• §7发射器射速: §a" + this.fireRate + "s")
/*  96 */       .addLoreLine(" §8• §7发射器安装速度: §a" + this.reload + "s")
/*  97 */       .addLoreLine(" §8• §7发射器射程距离: §a" + this.range)
/*  98 */       .addLoreLine("");
/*  99 */     return builder.toItemStack();
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<? extends ShopBasic> getShopClass() {
/* 104 */     return (Class)ArmedShop.class;
/*     */   }
/*     */ }