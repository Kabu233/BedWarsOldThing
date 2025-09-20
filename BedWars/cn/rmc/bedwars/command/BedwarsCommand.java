/*    */ package cn.rmc.bedwars.command;
/*    */ 
/*    */ import cn.rmc.bedwars.BedWars;
/*    */ import cn.rmc.bedwars.utils.command.Cmd;
/*    */ import cn.rmc.bedwars.utils.command.ICommand;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ public class BedwarsCommand
/*    */   extends ICommand {
/*    */   public BedwarsCommand() {
/* 11 */     super(BedwarsCommand.class, "bedwars", new String[] { "bw" });
/*    */   }
/*    */ 
/*    */   
/*    */   public void sendHelpMsg(CommandSender sender) {
/* 16 */     sender.sendMessage(new String[] { "" });
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Cmd(value = "help", permission = "bw.help", cmdSender = Cmd.CmdSender.PLAYER)
/*    */   public void help(CommandSender sender, String label, String[] args) {
/* 23 */     BedWars.sendMsg(sender, new String[] { "§f使用§b/shout <内容>§f进行喊话!" });
/*    */   }
/*    */ }