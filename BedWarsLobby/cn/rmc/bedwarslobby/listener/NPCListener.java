/*    */ package cn.rmc.bedwarslobby.listener;
/*    */ 
/*    */ import cn.rmc.bedwarslobby.BedWarsLobby;
/*    */ import cn.rmc.bedwarslobby.event.EntityInteractEvent;
/*    */ import cn.rmc.bedwarslobby.util.book.BookBuilder;
/*    */ import cn.rmc.bedwarslobby.util.book.BookUtils;
/*    */ import cn.rmc.bedwarslobby.util.book.ComponentBuilder;
/*    */ import cn.rmc.bedwarslobby.util.book.PageBuilder;
/*    */ import net.jitse.npclib.api.events.NPCInteractEvent;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.player.PlayerJoinEvent;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.scheduler.BukkitRunnable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NPCListener
/*    */   implements Listener
/*    */ {
/*    */   @EventHandler
/*    */   public void onJoinPlayer(final PlayerJoinEvent e) {
/* 42 */     BedWarsLobby.getInstance().getNpcStorage().showPlayer(e.getPlayer());
/* 43 */     (new BukkitRunnable()
/*    */       {
/*    */         public void run() {
/* 46 */           NPCListener.openBook(e.getPlayer());
/*    */         }
/* 48 */       }).runTaskLater((Plugin)BedWarsLobby.getInstance(), 2L);
/*    */   }
/*    */ 
/*    */   
/*    */   @EventHandler
/*    */   public void onInteract(NPCInteractEvent e) {
/* 54 */     BedWarsLobby.getInstance().getNpcStorage().onClick(e.getNPC(), e.getWhoClicked());
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onInteractHolo(EntityInteractEvent e) {
/* 59 */     BedWarsLobby.getInstance().getNpcStorage().onClick(e.getEntityId().intValue(), e.getPlayer());
/*    */   }
/*    */   
/*    */   public static void openBook(Player p) {
/* 63 */     if (!p.hasPermission("bedwarslobby.noob.rewards"))
/* 64 */       BookUtils.openBook(p, BookBuilder.create()
/* 65 */           .addPage(PageBuilder.create()
/* 66 */             .addComponent(ComponentBuilder.create().setText("  §5§l内测福利\n\n§0起床战争当前内测当中,\n§0许多的功能暂未完善.\n为此我们推出了此福利.\n\n§0奖品如下:\n §0• 硬币\n §0• 奖励箱\n\n   ")
/*    */               
/* 68 */               .getComponent())
/* 69 */             .addComponent(ComponentBuilder.create().setText("§2§l§n领取免费福利")
/*    */               
/* 71 */               .setHoverShowText("§e点击免费领取!").setClickRunCommand("/claim claimNoobRewards").getComponent())
/* 72 */             .getPage())
/* 73 */           .getBook()); 
/*    */   }
/*    */ }