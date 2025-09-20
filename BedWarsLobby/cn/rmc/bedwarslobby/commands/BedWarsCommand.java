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
/*    */ public class BedWarsCommand implements CommandExecutor {
/*    */   public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
/* 15 */     if (!(sender instanceof Player)) {
/* 16 */       return true;
/*    */     }
/* 18 */     if (args.length == 0) {
/* 19 */       sender.sendMessage("§aBedWars v1.0-SNAPSHOT");
/* 20 */       sender.sendMessage("§aPowered by 91MC");
/* 21 */       return true;
/*    */     } 
/* 23 */     if (args[0].equalsIgnoreCase("claimNoobRewards")) {
/* 24 */       if (!sender.hasPermission("bedwarslobby.noob.rewards")) {
/* 25 */         PlayerData pd = BedWarsLobby.getInstance().getPlayerManager().get((Player)sender);
/* 26 */         pd.setLootChest(Integer.valueOf(pd.getLootChest().intValue() + 1000));
/* 27 */         DataUtils.addInt(pd.getPlayer().getUniqueId().toString(), Data.Field.COIN, Integer.valueOf(10000));
/* 28 */         sender.sendMessage(new String[] { "§a成功领取以下起床奖励:", "§6+1,000 起床战争奖励箱", "§6+10,000 起床战争硬币", "" });
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 34 */         Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "lp user " + sender
/* 35 */             .getName() + " permission set bedwarslobby.noob.rewards true");
/* 36 */         return true;
/*    */       } 
/* 38 */       sender.sendMessage("§c你已经领取过这个奖励了, 请勿重复领取.");
/* 39 */       return true;
/*    */     }
    return true;
/*    */   }
/*    */ }