/*    */ package cn.rmc.bedwarslobby.loot.other;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ 
/*    */ public class LootList {
/*  8 */   List<LootInfo> infoList = new ArrayList<>();
/*    */   
/*    */   public LootList(LootInfo info) {
/* 11 */     this.infoList.add(info);
/*    */   }
/*    */   
/*    */   public List<LootInfo> getInfoList() {
/* 15 */     List<LootInfo> reverse = new ArrayList<>(this.infoList);
/* 16 */     Collections.reverse(reverse);
/* 17 */     return reverse;
/*    */   }
/*    */ }