/*     */ package cn.rmc.bedwars.enums.shop;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import cn.rmc.bedwars.enums.ITeamUpgrade;
/*     */ import cn.rmc.bedwars.enums.game.Resource;
/*     */ import cn.rmc.bedwars.game.shop.Price;
/*     */ import cn.rmc.bedwars.utils.item.ItemBuilder;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.inventory.ItemFlag;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ public enum TeamUpgrade implements ITeamUpgrade {
/*     */   ItemStack itemStack;
/*     */   String displayName;
/*     */   String introduce;
/*     */   String[] introduces;
/*     */   Price price;
/*  19 */   SharpenedSwords("锋利附魔", ITeamUpgrade.UpgradeType.NORMAL, (new ItemBuilder(Material.IRON_SWORD)).toItemStack(), "己方所有成员的剑和斧将永久获得锋利 I附魔!", Integer.valueOf(1), new Price(Resource.DIAMOND, 
/*  20 */       Integer.valueOf(8)), new Price(Resource.DIAMOND, Integer.valueOf(4))),
/*  21 */   ReinforcedArmor("装备强化", ITeamUpgrade.UpgradeType.NORMAL, (new ItemBuilder(Material.IRON_CHESTPLATE)).toItemStack(), "己方所有成员的盔甲将获得永久的保护附魔!", Integer.valueOf(4), new HashMap<Integer, List<Object>>()
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }),
/*  29 */   ManiacMiner("疯狂矿工", ITeamUpgrade.UpgradeType.NORMAL, (new ItemBuilder(Material.GOLD_PICKAXE)).toItemStack(), "己方所有成员获得永久急迫效果.", Integer.valueOf(2), new HashMap<Integer, List<Object>>()
/*     */     {
/*     */ 
/*     */     
/*     */     }),
/*  34 */   Forge("熔炉强化", ITeamUpgrade.UpgradeType.NORMAL, (new ItemBuilder(Material.FURNACE)).toItemStack(), "提升自己岛上生成资源的效率.", Integer.valueOf(4), new HashMap<Integer, List<Object>>()
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }),
/*  41 */   HealPool("治愈池", ITeamUpgrade.UpgradeType.NORMAL, (new ItemBuilder(Material.BEACON)).toItemStack(), "基地附近成员获得生命恢复效果.", Integer.valueOf(1), new Price(Resource.DIAMOND, 
/*  42 */       Integer.valueOf(3)), new Price(Resource.DIAMOND, Integer.valueOf(1))),
/*  43 */   DragonBuff("龙增益", ITeamUpgrade.UpgradeType.NORMAL, (new ItemBuilder(Material.DRAGON_EGG)).toItemStack(), "你的队伍在死亡竞赛中将会有两条末影龙而不是一条!", Integer.valueOf(1), new Price(Resource.DIAMOND, 
/*  44 */       Integer.valueOf(3))),
/*  45 */   ItsaTrap("这是一个陷阱!", ITeamUpgrade.UpgradeType.TRAP, (new ItemBuilder(Material.TRIPWIRE_HOOK)).toItemStack(), new String[] { "造成失明与缓慢效果，", "持续8秒"
/*     */     }),
/*  47 */   CounterOffensiveTrap("反击陷阱", ITeamUpgrade.UpgradeType.TRAP, (new ItemBuilder(Material.FEATHER)).toItemStack(), new String[] { "赋予附近的队友", "速度I效果", "持续15秒"
/*     */     }),
/*  49 */   AlarmTrap("报警陷阱", ITeamUpgrade.UpgradeType.TRAP, (new ItemBuilder(Material.REDSTONE_TORCH_ON)).toItemStack(), new String[] { "显示隐身的玩家,", "及其名称与队伍名." }),
/*  50 */   MineFatigueTrap("挖掘疲劳陷阱", ITeamUpgrade.UpgradeType.TRAP, (new ItemBuilder(Material.IRON_PICKAXE)).addFlag(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES }, ).toItemStack(), new String[] { "造成挖掘疲劳效果, 持续10秒." });
/*     */   
/*     */   Price SpecialPrice;
/*     */   
/*     */   Integer maxLevel;
/*     */   
/*     */   ITeamUpgrade.UpgradeType type;
/*     */   HashMap<Integer, List<Object>> info;
/*     */   
/*     */   public ItemStack getItemStack() {
/*  60 */     return this.itemStack;
/*     */   } public String getDisplayName() {
/*  62 */     return this.displayName;
/*     */   }
/*     */   public String[] getIntroduces() {
/*  65 */     return this.introduces;
/*     */   } public Price getPrice() {
/*  67 */     return this.price;
/*     */   } public Price getSpecialPrice() {
/*  69 */     return this.SpecialPrice;
/*     */   } public Integer getMaxLevel() {
/*  71 */     return this.maxLevel;
/*     */   } public ITeamUpgrade.UpgradeType getType() {
/*  73 */     return this.type;
/*     */   } public HashMap<Integer, List<Object>> getInfo() {
/*  75 */     return this.info;
/*     */   }
/*     */ 
/*     */   
/*     */   TeamUpgrade(String displayname, ITeamUpgrade.UpgradeType type, ItemStack is, String introduce, Integer maxLevel, Price price) {
/*  80 */     this.displayName = displayname;
/*  81 */     this.itemStack = is;
/*  82 */     this.introduce = introduce;
/*  83 */     this.maxLevel = maxLevel;
/*  84 */     this.price = price;
/*  85 */     this.type = type;
/*     */   }
/*     */   
/*     */   TeamUpgrade(String displayname, ITeamUpgrade.UpgradeType type, ItemStack is, String introduce, Integer maxLevel, Price price, Price SpecialPrice) {
/*  89 */     this.displayName = displayname;
/*  90 */     this.itemStack = is;
/*  91 */     this.introduce = introduce;
/*  92 */     this.maxLevel = maxLevel;
/*  93 */     this.price = price;
/*  94 */     this.type = type;
/*  95 */     this.SpecialPrice = SpecialPrice;
/*     */   }
/*     */   
/*     */   TeamUpgrade(String displayname, ITeamUpgrade.UpgradeType type, ItemStack is, String... introduce) {
/*  99 */     this.displayName = displayname;
/* 100 */     this.itemStack = is;
/* 101 */     this.introduces = introduce;
/* 102 */     this.type = type;
/*     */   }
/*     */   
/*     */   TeamUpgrade(String displayname, ITeamUpgrade.UpgradeType type, ItemStack is, String introduce, Integer maxLevel, HashMap<Integer, List<Object>> info) {
/* 106 */     this.displayName = displayname;
/* 107 */     this.itemStack = is;
/* 108 */     this.introduce = introduce;
/* 109 */     this.maxLevel = maxLevel;
/* 110 */     this.info = info;
/* 111 */     this.type = type;
/*     */   }
/*     */   
/*     */   public ItemStack showItem(Integer currentLevel, Game g) {
/* 115 */     ItemBuilder itemBuilder = new ItemBuilder(this.itemStack.clone());
/* 116 */     itemBuilder.addFlag(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS });
/* 117 */     if (this.type == ITeamUpgrade.UpgradeType.NORMAL) {
/* 118 */       itemBuilder.addLoreLine("§7" + this.introduce);
/* 119 */       itemBuilder.addLoreLine("");
/* 120 */       if (this.maxLevel.intValue() == 1) {
/* 121 */         itemBuilder.addLoreLine((g.getGameMode().getIsSpecial().booleanValue() && getSpecialPrice() != null) ? this.SpecialPrice.getDisplay() : this.price.getDisplay());
/*     */       } else {
/* 123 */         for (int i = 1; i <= this.maxLevel.intValue(); i++) {
/* 124 */           List<Object> obj = this.info.get(Integer.valueOf(i));
/* 125 */           String introducing = (String)obj.get(0);
/* 126 */           Price price = g.getGameMode().getIsSpecial().booleanValue() ? ((Price[])obj.get(1))[1] : ((Price[])obj.get(1))[0];
/* 127 */           if (i <= currentLevel.intValue()) {
/* 128 */             itemBuilder.addLoreLine("§a等级" + i + ": " + introducing + ", " + price.getEzDisplay());
/*     */           } else {
/* 130 */             itemBuilder.addLoreLine("§7等级" + i + ": " + introducing + ", §b" + price.getEzDisplay());
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 135 */     if (this.type == ITeamUpgrade.UpgradeType.TRAP) {
/* 136 */       for (String s : this.introduces) {
/* 137 */         itemBuilder.addLoreLine("§7" + s);
/*     */       }
/* 139 */       itemBuilder.addLoreLine("");
/* 140 */       int amount = 99999;
/* 141 */       switch (currentLevel.intValue()) {
/*     */         case 0:
/* 143 */           amount = 1;
/*     */           break;
/*     */         case 1:
/* 146 */           amount = 2;
/*     */           break;
/*     */         case 2:
/* 149 */           amount = 4;
/*     */           break;
/*     */       } 
/* 152 */       itemBuilder.addLoreLine((new Price(Resource.DIAMOND, Integer.valueOf(amount))).getDisplay());
/*     */     } 
/* 154 */     itemBuilder.addLoreLine("");
/*     */     
/* 156 */     return itemBuilder.toItemStack();
/*     */   }
/*     */   public static ArrayList<ITeamUpgrade> getValues(ITeamUpgrade.UpgradeType sort) {
/* 159 */     ArrayList<ITeamUpgrade> result = new ArrayList<>();
/* 160 */     for (TeamUpgrade value : values()) {
/* 161 */       if (value.getType() == sort) {
/* 162 */         result.add(value);
/*     */       }
/*     */     } 
/* 165 */     return result;
/*     */   }
/*     */ }