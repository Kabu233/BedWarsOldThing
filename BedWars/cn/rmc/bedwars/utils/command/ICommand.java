/*     */ package cn.rmc.bedwars.utils.command;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Parameter;
/*     */ import java.util.Arrays;
/*     */ import cn.rmc.bedwars.utils.Log;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.command.CommandMap;
/*     */ import org.bukkit.command.CommandSender;
/*     */ 
/*     */ public abstract class ICommand extends Command {
/*     */   private final Class SUB_CLASS;
/*     */   private String onlyConsole;
/*     */   private String onlyPlayer;
/*     */   private String noSubCommand;
/*     */   private String noPermission;
/*     */   
/*     */   public String toString() {
/*  20 */     return "ICommand(SUB_CLASS=" + this.SUB_CLASS + ", onlyConsole=" + this.onlyConsole + ", onlyPlayer=" + this.onlyPlayer + ", noSubCommand=" + this.noSubCommand + ", noPermission=" + this.noPermission + ")";
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOnlyConsole(String onlyConsole) {
/*  25 */     this.onlyConsole = onlyConsole; } public void setOnlyPlayer(String onlyPlayer) { this.onlyPlayer = onlyPlayer; } public void setNoSubCommand(String noSubCommand) { this.noSubCommand = noSubCommand; } public void setNoPermission(String noPermission) { this.noPermission = noPermission; }
/*     */ 
/*     */   
/*     */   public ICommand(Class clz, String name, String... aliases) {
/*  29 */     super(name, "BedWars", "/<command>", Arrays.asList(aliases));
/*  30 */     this.SUB_CLASS = clz;
/*  31 */     this.onlyConsole = "&conly console use.";
/*  32 */     this.onlyPlayer = "&conly player use.";
/*  33 */     this.noSubCommand = "&cunknown subcommand";
/*  34 */     this.noPermission = "&cyou don't have permissions.";
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean execute(CommandSender sender, String label, String[] args) {
/*  39 */     if (args.length >= 1) {
/*  40 */       Method method = getMethodByCommand(args[0]);
/*  41 */       if (method != null) {
/*  42 */         if (check(sender, method.<Cmd>getAnnotation(Cmd.class))) {
/*     */           try {
/*  44 */             method.invoke(this, new Object[] { sender, label, args });
/*  45 */           } catch (IllegalAccessException|java.lang.reflect.InvocationTargetException e) {
/*  46 */             e.printStackTrace();
/*     */           } 
/*     */         }
/*     */       } else {
/*  50 */         sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.noSubCommand));
/*     */       } 
/*     */     } else {
/*  53 */       sendHelpMsg(sender);
/*     */     } 
/*  55 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean check(CommandSender sender, Cmd cmd) {
/*  66 */     if (!sender.hasPermission(cmd.permission())) {
/*  67 */       sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.noPermission));
/*  68 */       return false;
/*  69 */     }  if (cmd.cmdSender() == Cmd.CmdSender.PLAYER && !(sender instanceof org.bukkit.entity.Player)) {
/*  70 */       sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.onlyPlayer));
/*  71 */       return false;
/*  72 */     }  if (cmd.cmdSender() == Cmd.CmdSender.CONSOLE && sender instanceof org.bukkit.entity.Player) {
/*  73 */       sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.onlyConsole));
/*  74 */       return false;
/*     */     } 
/*  76 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Method getMethodByCommand(String subCmd) {
/*  86 */     Method[] methods = this.SUB_CLASS.getMethods();
/*  87 */     for (Method method : methods) {
/*  88 */       Cmd cmd = method.<Cmd>getAnnotation(Cmd.class);
/*  89 */       if (cmd != null && (
/*  90 */         cmd.IgnoreCase() ? subCmd.equals(cmd.value()) : subCmd.equalsIgnoreCase(cmd.value()))) {
/*  91 */         Parameter[] parameter = method.getParameters();
/*  92 */         if (parameter.length == 3 && parameter[0].getType() == CommandSender.class && parameter[1].getType() == String.class && parameter[2].getType() == String[].class) {
/*  93 */           return method;
/*     */         }
/*  95 */         Log.warning("found a Illegal sub command method in command " + getName() + " called: " + method.getName() + " in class: " + this.SUB_CLASS);
/*     */       } 
/*     */     } 
/*  98 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean Register() {
/*     */     try {
/* 111 */       Field commandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
/* 112 */       commandMap.setAccessible(true);
/* 113 */       CommandMap map = (CommandMap)commandMap.get(Bukkit.getServer());
/* 114 */       map.register("bedwars", this);
/* 115 */       return true;
/* 116 */     } catch (NoSuchFieldException|SecurityException|IllegalArgumentException|IllegalAccessException e) {
/* 117 */       Log.warning("Error while register Command: " + this + " due to\n");
/* 118 */       Log.warning(e.getLocalizedMessage());
/*     */       
/* 120 */       return false;
/*     */     } 
/*     */   } public void register() {
/* 123 */     Register();
/*     */   }
/*     */   
/*     */   public void unregister() {
/*     */     try {
/* 128 */       Field commandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
/* 129 */       commandMap.setAccessible(true);
/* 130 */       CommandMap map = (CommandMap)commandMap.get(Bukkit.getServer());
/* 131 */       unregister(map);
/*     */     } catch (Throwable $ex) {
/*     */       throw $ex;
/*     */     }  } protected String buildString(Object... objs) {
/* 135 */     StringBuilder sb = new StringBuilder();
/* 136 */     for (Object tmp : objs) {
/* 137 */       sb.append(tmp);
/*     */     }
/* 139 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public abstract void sendHelpMsg(CommandSender paramCommandSender);
/*     */ }