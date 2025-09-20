/*    */ package cn.rmc.bedwars.game.dream.rush;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import cn.rmc.bedwars.BedWars;
/*    */ import cn.rmc.bedwars.enums.game.GameMode;
/*    */ import cn.rmc.bedwars.enums.game.Resource;
/*    */ import cn.rmc.bedwars.game.Game;
/*    */ import cn.rmc.bedwars.game.PlayerData;
/*    */ import cn.rmc.bedwars.game.dream.DreamManager;
/*    */ import cn.rmc.bedwars.utils.player.ActionBarUtils;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.event.HandlerList;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ public class RushManager implements DreamManager {
/* 20 */   private final HashMap<PlayerData, Boolean> state = new HashMap<>();
/*    */   Game g;
/*    */   GameMode gm;
/*    */   Listener listener;
/*    */   
/*    */   public RushManager(Game g) {
/* 26 */     this.g = g;
/* 27 */     this.gm = g.getGameMode();
/*    */   }
/*    */ 
/*    */   
/*    */   public void start() {
/* 32 */     for (Map.Entry<Resource, ArrayList<Location>> entry : (Iterable<Map.Entry<Resource, ArrayList<Location>>>)this.g.getMap().getResourceloc().entrySet()) {
/* 33 */       this.g.getResGenLevel().put(entry.getKey(), Integer.valueOf(2));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void dispose() {
/* 39 */     HandlerList.unregisterAll(this.listener);
/*    */   }
/*    */ 
/*    */   
/*    */   public void init() {
/* 44 */     this.g.setStartmessage(new String[] { "§a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬", "                                   §l起床战争", "", "   §e§l所有资源生成点自动满级！你的床有三层保护！手持羊毛点击", "                            §e§l左键开启建桥模式！", "", "§a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬" });
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 54 */     Bukkit.getPluginManager().registerEvents(this.listener = new RushListener(this), (Plugin)BedWars.getInstance());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setState(PlayerData pd, Boolean bol) {
/* 60 */     ActionBarUtils.sendActionBar(pd.getPlayer(), bol.booleanValue() ? "§a§l搭桥模式已开启" : "§c§l搭桥模式已关闭");
/* 61 */     this.state.put(pd, bol);
/*    */   }
/*    */   
/*    */   public Boolean getState(PlayerData pd) {
/* 65 */     return this.state.getOrDefault(pd, Boolean.valueOf(false));
/*    */   }
/*    */ }