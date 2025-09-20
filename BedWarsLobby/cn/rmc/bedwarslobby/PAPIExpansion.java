/*    */ package cn.rmc.bedwarslobby;
/*    */ 
/*    */ import me.clip.placeholderapi.expansion.PlaceholderExpansion;
/*    */ import cn.rmc.bedwarslobby.object.PlayerData;
/*    */ import cn.rmc.bedwarslobby.util.LevelUtils;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class PAPIExpansion
/*    */   extends PlaceholderExpansion {
/*    */   public String onPlaceholderRequest(Player p, String params) {
/* 11 */     String result = null;
/* 12 */     PlayerData pd = BedWarsLobby.getInstance().getPlayerManager().get(p);
/* 13 */     if (params.equalsIgnoreCase("level")) {
/* 14 */       result = LevelUtils.getLevelwithColorByLevel(pd.getCurrentLevel().intValue());
/*    */     }
/* 16 */     return result;
/*    */   }
/*    */   
/*    */   public String getIdentifier() {
/* 20 */     return "bedwars";
/*    */   }
/*    */   
/*    */   public String getAuthor() {
/* 24 */     return "Yeoc";
/*    */   }
/*    */   
/*    */   public String getVersion() {
/* 28 */     return "1.0";
/*    */   }
/*    */ }