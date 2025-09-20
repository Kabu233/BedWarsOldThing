/*    */ package cn.rmc.bedwars.manager;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import cn.rmc.bedwars.game.Holographic;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HolographicManager
/*    */ {
/* 14 */   private List<Holographic> holographics = new ArrayList<>();
/*    */ 
/*    */   
/*    */   public void addHolographic(Holographic holo) {
/* 18 */     if (!this.holographics.contains(holo)) {
/* 19 */       this.holographics.add(holo);
/*    */     }
/*    */   }
/*    */   
/*    */   public void removeHolographic(Holographic holo) {
/* 24 */     if (this.holographics.contains(holo)) {
/* 25 */       this.holographics.remove(holo);
/*    */     }
/*    */   }
/*    */   
/*    */   public void deleteHolographic(Holographic holo) {
/* 30 */     if (this.holographics.contains(holo)) {
/* 31 */       holo.remove();
/* 32 */       this.holographics.remove(holo);
/*    */     } 
/*    */   }
/*    */   
/*    */   public List<Holographic> getPlayerHolographic(Player player) {
/* 37 */     List<Holographic> list = new ArrayList<>();
/* 38 */     for (Holographic holo : this.holographics) {
/* 39 */       if (holo.getPlayers().contains(player)) {
/* 40 */         list.add(holo);
/*    */       }
/*    */     } 
/* 43 */     return list;
/*    */   }
/*    */ }