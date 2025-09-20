/*    */ package cn.rmc.bedwars.game.dream.swappage;
/*    */ import cn.rmc.bedwars.game.Game;
/*    */ import cn.rmc.bedwars.game.dream.DreamManager;
/*    */ 
/*    */ public class SwappageManager implements DreamManager {
/*    */   public SwappageManager(Game game) {
/*  7 */     this.game = game;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   Game game;
/*    */ 
/*    */ 
/*    */   
/*    */   public void start() {}
/*    */ 
/*    */   
/*    */   public void dispose() {}
/*    */ 
/*    */   
/*    */   public void init() {
/* 23 */     this.game.setStartmessage(new String[] { "▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬                             §l起床战争轮换模式                           玩家将随机交换队伍！\n              同时玩家也会与对应队伍的玩家交换位置！\n▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬" });
/*    */   }
/*    */ }