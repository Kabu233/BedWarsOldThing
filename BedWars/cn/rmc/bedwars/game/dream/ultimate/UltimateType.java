/*     */ package cn.rmc.bedwars.game.dream.ultimate;
/*     */ import java.util.UUID;
/*     */ import cn.rmc.bedwars.game.PlayerData;
/*     */ import cn.rmc.bedwars.game.dream.ultimate.ultimates.Builder;
/*     */ import cn.rmc.bedwars.game.dream.ultimate.ultimates.Frozo;
/*     */ import cn.rmc.bedwars.game.dream.ultimate.ultimates.Gatherer;
/*     */ import cn.rmc.bedwars.game.dream.ultimate.ultimates.Healer;
/*     */ import cn.rmc.bedwars.game.dream.ultimate.ultimates.Kangaroo;
/*     */ import cn.rmc.bedwars.game.dream.ultimate.ultimates.SwordsMan;
/*     */ import cn.rmc.bedwars.utils.item.ItemBuilder;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemFlag;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ public enum UltimateType {
/*  18 */   KANGAROO("袋鼠", (new ItemBuilder(Material.RABBIT_FOOT)).toItemStack(), (Class)Kangaroo.class, Integer.valueOf(10), new String[] { "§7· 二段跳!", "§7· 在死亡时有概率保留资源!", "§7· 破坏床能够获得牛奶!"
/*     */ 
/*     */ 
/*     */     
/*     */     }),
/*  23 */   SWORDSMAN("剑客", (new ItemBuilder(Material.GOLD_SWORD)).addEnchant(Enchantment.DURABILITY, 1).addFlag(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS }).toItemStack(), (Class)SwordsMan.class, 
/*  24 */     Integer.valueOf(5), new String[] { "§7· 将剑长按右键向前冲刺!", "§7· 再次长按右键回到原点!", "§7· 击杀重置冷却时间!"
/*     */ 
/*     */ 
/*     */     
/*     */     }),
/*  29 */   HEALER("医师", (new ItemBuilder(Material.GOLDEN_APPLE)).toItemStack(), (Class)Healer.class, Integer.valueOf(20), new String[] { "§7· 用剑长按右键治疗自身!", "§7· 丢下药水治疗友方玩家!"
/*     */ 
/*     */     
/*     */     }),
/*  33 */   FROZO("冰霜法师", (new ItemBuilder(Material.SNOW_BLOCK)).toItemStack(), (Class)Frozo.class, Integer.valueOf(20), new String[] { "§7· 丢下药水减缓敌方的速度!", "§7· 击杀能够获得雪球! (上限16个)"
/*     */ 
/*     */     
/*     */     }),
/*  37 */   BUILDER("建筑师", (new ItemBuilder(Material.BRICK)).toItemStack(), (Class)Builder.class, Integer.valueOf(-1), new String[] { "§7· 建桥!", "§7· 建墙!", "§7· 自动保护你的床", "", "§7左键更改模式, 放置来触发!", "", "§7§o嘿,尝试用它来右键点击你的床!"
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }),
/*  46 */   DEMOLITION("破坏者", (new ItemBuilder(Material.FLINT_AND_STEEL)).toItemStack(), (Class)Demolition.class, Integer.valueOf(15), new String[] { "§7· 烧毁一定范围内的羊毛!", "§7· 死亡时掉落TNT!", "§7· 破坏床时获得一个苦力怕蛋!"
/*     */ 
/*     */ 
/*     */     
/*     */     }),
/*  51 */   GATHERER("收集者", (new ItemBuilder(Material.EMERALD)).toItemStack(), (Class)Gatherer.class, Integer.valueOf(-1), new String[] { "§7· 有机会从资源生成点获得双倍钻石/绿宝石!", "§7· 随身末影箱!", "§7· 我方床被摧毁时，获得一项免费团队升级" }); private final String DBName;
/*     */   private final ItemStack item;
/*     */   private final Class<? extends UltimateBasic> clazz;
/*     */   private final Integer cooldown;
/*     */   private final String[] introduce;
/*     */   
/*     */   public String getDBName() {
/*  58 */     return this.DBName;
/*     */   } public ItemStack getItem() {
/*  60 */     return this.item;
/*     */   } public Class<? extends UltimateBasic> getClazz() {
/*  62 */     return this.clazz;
/*     */   } public Integer getCooldown() {
/*  64 */     return this.cooldown;
/*     */   } public String[] getIntroduce() {
/*  66 */     return this.introduce;
/*     */   }
/*     */   
/*     */   UltimateType(String DBName, ItemStack item, Class<? extends UltimateBasic> clazz, Integer cooldown, String[] introduce) {
/*  70 */     this.DBName = DBName;
/*  71 */     this.item = item;
/*  72 */     this.clazz = clazz;
/*  73 */     this.introduce = introduce;
/*  74 */     this.cooldown = cooldown;
/*     */   }
/*     */   
/*     */   public UltimateBasic getUltimate(Player owner) {
/*  78 */     UltimateBasic perk = null;
/*     */     try {
/*  80 */       perk = this.clazz.getDeclaredConstructor(new Class[] { UUID.class }).newInstance(new Object[] { owner.getUniqueId() });
/*  81 */     } catch (InstantiationException|java.lang.reflect.InvocationTargetException|IllegalAccessException|NoSuchMethodException e) {
/*  82 */       e.printStackTrace();
/*     */     } 
/*  84 */     return perk;
/*     */   }
/*     */   
/*     */   public ItemStack showItem(PlayerData pd) {
/*  88 */     ItemBuilder ib = new ItemBuilder(this.item.clone());
/*  89 */     ib.setName("§a" + this.DBName);
/*  90 */     ib.setLore(this.introduce);
/*  91 */     if (this.cooldown.intValue() != -1) {
/*  92 */       ib.addLoreLine("");
/*  93 */       ib.addLoreLine("§7冷却: §a" + getCooldown() + "秒");
/*     */     } 
/*  95 */     ib.addLoreLine("");
/*  96 */     UltimateType ult = ((UltimateManager)pd.getGame().getDreamManager()).getUltimate(pd);
/*  97 */     if (ult == this) {
/*  98 */       ib.addLoreLine("§a已选择!");
/*     */     } else {
/* 100 */       ib.addLoreLine("§e点击选择!");
/*     */     } 
/* 102 */     return ib.toItemStack();
/*     */   }
/*     */ }