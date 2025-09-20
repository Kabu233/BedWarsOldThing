/*    */ package cn.rmc.bedwars.manager;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import cn.rmc.bedwars.BedWars;
/*    */ import cn.rmc.bedwars.game.Game;
/*    */ import cn.rmc.bedwars.game.Map;
/*    */ 
/*    */ 
/*    */ public class GameManager
/*    */ {
/* 12 */   private final HashMap<String, Game> games = new HashMap<>(); public HashMap<String, Game> getGames() { return this.games; }
/*    */   
/* 14 */   private final ArrayList<Game> gameArrayList = new ArrayList<>(); public ArrayList<Game> getGameArrayList() { return this.gameArrayList; }
/*    */ 
/*    */   
/*    */   public GameManager() {
/* 18 */     load();
/*    */   }
/*    */   
/*    */   public void load() {
/* 22 */     (BedWars.getInstance().getMapManager()).maps.forEach(map -> {
/*    */           System.out.println("成功加载地图 " + map.getMapname());
/*    */           Game result = new Game(map);
/*    */           this.games.put(map.getMapname().toLowerCase(), result);
/*    */           this.gameArrayList.add(result);
/*    */         });
/*    */   }
/*    */ }