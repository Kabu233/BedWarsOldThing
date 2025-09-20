/*    */ package cn.rmc.bedwars.listener;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import cn.rmc.bedwars.BedWars;
/*    */ import cn.rmc.bedwars.enums.game.PlayerState;
/*    */ import cn.rmc.bedwars.game.PlayerData;
/*    */ import cn.rmc.bedwars.inventory.game.shops.QuickShop;
/*    */ import cn.rmc.bedwars.inventory.game.shops.TeamUpgradeShop;
/*    */ import net.citizensnpcs.api.event.NPCRightClickEvent;
/*    */ import net.citizensnpcs.api.npc.NPC;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ public class NPCListener
/*    */   implements Listener {
/* 19 */   public static ArrayList<NPC> items = new ArrayList<>();
/* 20 */   public static ArrayList<NPC> teams = new ArrayList<>();
/*    */   
/*    */   @EventHandler
/*    */   public void onClick(NPCRightClickEvent e) {
/* 24 */     Player p = e.getClicker();
/* 25 */     PlayerData pd = BedWars.getInstance().getPlayerManager().get(p);
/* 26 */     if (pd.getState() != PlayerState.FIGHTING) {
/* 27 */       e.setCancelled(true);
/*    */       
/*    */       return;
/*    */     } 
/* 31 */     if (items.contains(e.getNPC())) {
/* 32 */       Bukkit.getScheduler().runTaskLater((Plugin)BedWars.getInstance(), () -> (new QuickShop(p)).open(), 5L);
/*    */     }
/*    */ 
/*    */     
/* 36 */     if (teams.contains(e.getNPC()))
/* 37 */       Bukkit.getScheduler().runTaskLater((Plugin)BedWars.getInstance(), () -> (new TeamUpgradeShop(p)).open(), 5L); 
/*    */   }
/*    */ }