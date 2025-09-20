/*    */ package cn.rmc.bedwars.enums;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import cn.rmc.bedwars.enums.shop.Block;
/*    */ import cn.rmc.bedwars.enums.shop.Melee;
/*    */ import cn.rmc.bedwars.enums.shop.Ranged;
/*    */ import cn.rmc.bedwars.enums.shop.Utility;
/*    */ import cn.rmc.bedwars.game.Game;
/*    */ import cn.rmc.bedwars.game.PlayerData;
/*    */ import cn.rmc.bedwars.game.shop.Price;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public interface IShopItem {
/* 16 */   public static final List<Class<? extends IShopItem>> clazz = new ArrayList<>(Arrays.asList((Class<? extends IShopItem>[])new Class[] { Block.class, Melee.class, Ranged.class, Potion.class, Utility.class }));
/*    */   
/*    */   String getDisplayName();
/*    */   
/*    */   Price getPrice();
/*    */   
/*    */   HashMap<List<String>, Price> getPrices();
/*    */   
/*    */   String name();
/*    */   
/*    */   ItemStack giveItem(PlayerData paramPlayerData);
/*    */   
/*    */   ItemStack showItem(PlayerData paramPlayerData, Game paramGame);
/*    */ }
