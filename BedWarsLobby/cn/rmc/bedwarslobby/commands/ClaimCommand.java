/*    */ package cn.rmc.bedwarslobby.commands;
/*    */ 
/*    */ import cn.rmc.bedwarslobby.BedWarsLobby;
/*    */ import cn.rmc.bedwarslobby.enums.Data;
/*    */ import cn.rmc.bedwarslobby.object.PlayerData;
/*    */ import cn.rmc.bedwarslobby.util.DataUtils;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class ClaimCommand
/*    */   implements CommandExecutor
/*    */ {
/*    */   public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
/* 17 */     if (!(sender instanceof Player)) {
/* 18 */       return true;
/*    */     }
/* 20 */     if (args.length == 0) {
/* 21 */       sender.sendMessage("§aBedWars v1.0-SNAPSHOT");
/* 22 */       sender.sendMessage("§aPowered by 91MC");
/* 23 */       return true;
/*    */     } 
/* 25 */     if (args[0].equalsIgnoreCase("claimNoobRewards")) {
/* 26 */       if (!sender.hasPermission("bedwarslobby.noob.rewards")) {
/* 27 */         PlayerData pd = BedWarsLobby.getInstance().getPlayerManager().get((Player)sender);
/* 28 */         pd.setLootChest(Integer.valueOf(pd.getLootChest().intValue() + 1000));
/* 29 */         DataUtils.addInt(pd.getPlayer().getUniqueId().toString(), Data.Field.COIN, Integer.valueOf(10000));
/* 30 */         sender.sendMessage(new String[] { "§a成功领取以下起床奖励:", "§6+1,000 起床战争奖励箱", "§6+10,000 起床战争硬币", "" });
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 36 */         Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "lp user " + sender
/* 37 */             .getName() + " permission set bedwarslobby.noob.rewards true");
/* 38 */         return true;
/*    */       } 
/* 40 */       sender.sendMessage("§c你已经领取过这个奖励了, 请勿重复领取.");
/* 41 */       return true;
/*    */     } 
/*    */     
/* 44 */     return true;
/*    */   }
/*    */ }