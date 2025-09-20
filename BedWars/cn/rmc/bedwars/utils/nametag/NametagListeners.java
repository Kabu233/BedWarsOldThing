/*    */ package cn.rmc.bedwars.utils.nametag;
/*    */ 
/*    */ import java.util.Map;
/*    */ import java.util.UUID;
/*    */ import cn.rmc.bedwars.utils.player.LuckPermsUtil;
/*    */ import net.luckperms.api.model.group.Group;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.EventPriority;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.player.PlayerJoinEvent;
/*    */ import org.bukkit.event.player.PlayerQuitEvent;
/*    */ 
/*    */ public class NametagListeners
/*    */   implements Listener {
/*    */   private NametagHandler handler;
/*    */   
/*    */   public NametagHandler getHandler() {
/* 20 */     return this.handler;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public NametagListeners(NametagHandler handler) {
/* 28 */     this.handler = handler;
/*    */   }
/*    */   
/*    */   @EventHandler(priority = EventPriority.LOWEST)
/*    */   public void onPlayerJoin(PlayerJoinEvent event) {
/* 33 */     getHandler().getBoards().putIfAbsent(event.getPlayer().getUniqueId(), new NametagBoard(event.getPlayer(), getHandler()));
/* 34 */     Player player = event.getPlayer();
/* 35 */     Group group = LuckPermsUtil.getGroup(player);
/* 36 */     for (Map.Entry<UUID, NametagBoard> entry : getHandler().getBoards().entrySet()) {
/* 37 */       NametagBoard board = entry.getValue();
/*    */       
/* 39 */       String s = LuckPermsUtil.getGrouppriority(group) + event.getPlayer().getName().substring(0, Math.min(5, event.getPlayer().getName().length())) + event.getPlayer().getName().hashCode();
/*    */ 
/*    */ 
/*    */       
/* 43 */       BufferedNametag nametag = new BufferedNametag(s.substring(0, Math.min(16, s.length())), LuckPermsUtil.getPrefixColor(event.getPlayer()), "", false, event.getPlayer());
/* 44 */       board.updateNametag(board.getScoreboard(), nametag);
/*    */     } 
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onPlayerQuit(PlayerQuitEvent event) {
/* 50 */     NametagBoard board = getHandler().getBoards().get(event.getPlayer().getUniqueId());
/*    */     
/* 52 */     if (board == null) {
/*    */       return;
/*    */     }
/*    */     
/* 56 */     board.cleanup();
/* 57 */     getHandler().getBoards().remove(event.getPlayer().getUniqueId());
/* 58 */     event.getPlayer().setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
/*    */   }
/*    */ }