/*    */ package cn.rmc.bedwars.command;
/*    */ 
/*    */ import cn.rmc.bedwars.BedWars;
/*    */ import cn.rmc.bedwars.enums.game.GameMode;
/*    */ import cn.rmc.bedwars.enums.game.PlayerState;
/*    */ import cn.rmc.bedwars.game.PlayerData;
/*    */ import cn.rmc.bedwars.utils.player.LuckPermsUtil;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class ShoutCommand
/*    */   extends Command {
/*    */   public ShoutCommand() {
/* 15 */     super("shout");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(CommandSender sender, String commandLabel, String[] args) {
/* 20 */     if (!(sender instanceof Player)) return true; 
/* 21 */     Player p = (Player)sender;
/* 22 */     PlayerData pd = BedWars.getInstance().getPlayerManager().get(p);
/* 23 */     if (pd.getState() != PlayerState.FIGHTING) {
/* 24 */       p.sendMessage("§c当前状态不能使用此指令!");
/* 25 */       return true;
/*    */     } 
/* 27 */     if (pd.getGame().getGameMode().equals(GameMode.EIGHT_ONE)) {
/* 28 */       p.sendMessage("§c当前模式禁止使用此指令!");
/* 29 */       return true;
/*    */     } 
/* 31 */     if (args.length == 0) {
/* 32 */       p.sendMessage("§c缺少参数! 使用方法: /shout <消息>");
/* 33 */       return true;
/*    */     } 
/* 35 */     if (pd.getLastshout() == null || pd.getLastshout().intValue() <= 0) {
/* 36 */       String team = pd.getTeam().getTeamType().getChatColor() + "[" + pd.getTeam().getTeamType().getDisplayname() + "]§7";
/* 37 */       StringBuilder sb = new StringBuilder();
/* 38 */       for (String arg : args) {
/* 39 */         sb.append(arg).append(" ");
/*    */       }
/* 41 */       for (PlayerData player : pd.getGame().getOnlinePlayers()) {
/* 42 */         player.getPlayer().sendMessage("§6[喊话] " + team + " " + LuckPermsUtil.getPrefix(p) + p.getName() + LuckPermsUtil.getSuffix(p) + "§f: " + sb);
/*    */       }
/* 44 */       pd.setLastshout(Integer.valueOf(60));
/*    */     } else {
/* 46 */       p.sendMessage("§c你必须等待§e" + pd.getLastshout() + " §c秒后才能再次使用/shout!");
/*    */     } 
/* 48 */     return true;
/*    */   }
/*    */ }