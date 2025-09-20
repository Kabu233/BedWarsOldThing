/*    */ package cn.rmc.bedwarslobby.manager;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.UUID;
/*    */ import cn.rmc.bedwarslobby.object.PlayerData;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class PlayerManager
/*    */ {
/* 10 */   HashMap<UUID, PlayerData> data = new HashMap<>();
/*    */   
/*    */   public PlayerData add(Player p) {
/* 13 */     if (!this.data.containsKey(p.getUniqueId())) {
/* 14 */       PlayerData pd = new PlayerData(p.getUniqueId());
/* 15 */       this.data.put(p.getUniqueId(), pd);
/*    */     } 
/* 17 */     return this.data.get(p.getUniqueId());
/*    */   }
/*    */   
/*    */   public PlayerData get(Player p) {
/* 21 */     return this.data.containsKey(p.getUniqueId()) ? this.data.get(p.getUniqueId()) : add(p);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void remove(Player p) {
/* 28 */     get(p).save();
/* 29 */     get(p).removeAllTask();
/* 30 */     this.data.remove(p.getUniqueId());
/*    */   }
/*    */ }