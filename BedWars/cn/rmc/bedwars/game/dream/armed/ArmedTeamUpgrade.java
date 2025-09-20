/*    */ package cn.rmc.bedwars.game.dream.armed;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import cn.rmc.bedwars.enums.ITeamUpgrade;
/*    */ import cn.rmc.bedwars.enums.game.Resource;
/*    */ import cn.rmc.bedwars.game.Game;
/*    */ import cn.rmc.bedwars.game.shop.Price;
/*    */ import cn.rmc.bedwars.utils.item.ItemBuilder;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.inventory.ItemFlag;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public enum ArmedTeamUpgrade
/*    */   implements ITeamUpgrade {
/*    */   HashMap<Integer, List<Object>> info;
/*    */   ITeamUpgrade.UpgradeType type;
/* 20 */   DeadShot("枪械强化", ITeamUpgrade.UpgradeType.NORMAL, (new ItemBuilder(Material.DIAMOND_HOE)).toItemStack(), "全团队的枪械将会造成更多的伤害", Integer.valueOf(4), new HashMap<Integer, List<Object>>() {  }
/*    */   ); Integer maxLevel; Price SpecialPrice; Price price;
/*    */   String[] introduces;
/*    */   String introduce;
/*    */   String displayName;
/*    */   ItemStack itemStack;
/*    */   
/*    */   public ItemStack getItemStack() {
/* 28 */     return this.itemStack;
/*    */   } public String getDisplayName() {
/* 30 */     return this.displayName;
/*    */   }
/*    */   public String[] getIntroduces() {
/* 33 */     return this.introduces;
/*    */   } public Price getPrice() {
/* 35 */     return this.price;
/*    */   } public Price getSpecialPrice() {
/* 37 */     return this.SpecialPrice;
/*    */   } public Integer getMaxLevel() {
/* 39 */     return this.maxLevel;
/*    */   } public ITeamUpgrade.UpgradeType getType() {
/* 41 */     return this.type;
/*    */   } public HashMap<Integer, List<Object>> getInfo() {
/* 43 */     return this.info;
/*    */   }
/*    */   
/*    */   ArmedTeamUpgrade(String displayname, ITeamUpgrade.UpgradeType type, ItemStack is, String introduce, Integer maxLevel, HashMap<Integer, List<Object>> info) {
/* 47 */     this.displayName = displayname;
/* 48 */     this.itemStack = is;
/* 49 */     this.introduce = introduce;
/* 50 */     this.maxLevel = maxLevel;
/* 51 */     this.info = info;
/* 52 */     this.type = type;
/*    */   }
/*    */   
/*    */   public ItemStack showItem(Integer currentLevel, Game g) {
/* 56 */     ItemBuilder itemBuilder = new ItemBuilder(this.itemStack.clone());
/* 57 */     itemBuilder.addFlag(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS });
/* 58 */     if (this.type == ITeamUpgrade.UpgradeType.NORMAL) {
/* 59 */       itemBuilder.addLoreLine("§7" + this.introduce);
/* 60 */       itemBuilder.addLoreLine("");
/* 61 */       if (this.maxLevel.intValue() == 1) {
/* 62 */         itemBuilder.addLoreLine((g.getGameMode().getIsSpecial().booleanValue() && getSpecialPrice() != null) ? this.SpecialPrice.getDisplay() : this.price.getDisplay());
/*    */       } else {
/* 64 */         for (int i = 1; i <= this.maxLevel.intValue(); i++) {
/* 65 */           List<Object> obj = this.info.get(Integer.valueOf(i));
/* 66 */           String introducing = (String)obj.get(0);
/* 67 */           Price price = g.getGameMode().getIsSpecial().booleanValue() ? ((Price[])obj.get(1))[1] : ((Price[])obj.get(1))[0];
/* 68 */           if (i <= currentLevel.intValue()) {
/* 69 */             itemBuilder.addLoreLine("§a等级" + i + ": " + introducing + ", " + price.getEzDisplay());
/*    */           } else {
/* 71 */             itemBuilder.addLoreLine("§7等级" + i + ": " + introducing + ", §b" + price.getEzDisplay());
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } 
/* 76 */     itemBuilder.addLoreLine("");
/*    */     
/* 78 */     return itemBuilder.toItemStack();
/*    */   }
/*    */   public static ArrayList<ITeamUpgrade> getValues(ITeamUpgrade.UpgradeType sort) {
/* 81 */     ArrayList<ITeamUpgrade> result = new ArrayList<>();
/* 82 */     for (ITeamUpgrade value : values()) {
/* 83 */       if (value.getType() == sort) {
/* 84 */         result.add(value);
/*    */       }
/*    */     } 
/* 87 */     return result;
/*    */   }
/*    */ }
