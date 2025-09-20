/*    */ package net.jitse.npclib.metrics;
/*    */ 
/*    */ import net.jitse.npclib.NPCLib;
/*    */ import net.jitse.npclib.internal.NPCManager;
/*    */ import org.bstats.bukkit.Metrics;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ public class NPCLibMetrics {
/*    */   private static final int BSTATS_PLUGIN_ID = 7214;
/*    */   
/*    */   public NPCLibMetrics(NPCLib instance) {
/* 12 */     Metrics metrics = new Metrics((Plugin)instance.getPlugin(), 7214);
/* 13 */     metrics.addCustomChart((Metrics.CustomChart)new Metrics.SingleLineChart("npcs", () -> Integer.valueOf(NPCManager.getAllNPCs().size())));
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\BedWarsLobby-1.0-SNAPSHOT.jar!\net\jitse\npclib\metrics\NPCLibMetrics.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */