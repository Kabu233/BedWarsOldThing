/*    */ package cn.rmc.bedwars.enums;
/*    */ public interface ITeamUpgrade {
/*    */   ItemStack getItemStack();
/*    */   
/*    */   String getDisplayName();
/*    */   
/*    */   String[] getIntroduces();
/*    */   
/*    */   Price getPrice();
/*    */   
/*    */   Price getSpecialPrice();
/*    */   
/* 13 */   public static final ArrayList<ITeamUpgrade> upgrades = new ArrayList<>(TeamUpgrade.getValues(UpgradeType.NORMAL));
/*    */ 
/*    */   
/*    */   Integer getMaxLevel();
/*    */ 
/*    */   
/*    */   UpgradeType getType();
/*    */ 
/*    */   
/*    */   HashMap<Integer, List<Object>> getInfo();
/*    */ 
/*    */   
/*    */   ItemStack showItem(Integer paramInteger, Game paramGame);
/*    */ 
/*    */   
/*    */   public enum UpgradeType
/*    */   {
/* 30 */     NORMAL, TRAP;
/*    */   }
/*    */ }