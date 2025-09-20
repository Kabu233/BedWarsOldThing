/*     */ package cn.rmc.bedwars.utils.player;
/*     */ 
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Objects;
/*     */ import lombok.NonNull;
/*     */ import net.luckperms.api.LuckPerms;
/*     */ import net.luckperms.api.LuckPermsProvider;
/*     */ import net.luckperms.api.context.ContextSet;
/*     */ import net.luckperms.api.model.group.Group;
/*     */ import net.luckperms.api.model.group.GroupManager;
/*     */ import net.luckperms.api.model.user.User;
/*     */ import net.luckperms.api.model.user.UserManager;
/*     */ import net.luckperms.api.node.NodeType;
/*     */ import net.luckperms.api.node.types.InheritanceNode;
/*     */ import net.luckperms.api.query.QueryOptions;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.entity.Player;
/*     */ 
/*     */ public class LuckPermsUtil {
/*  20 */   static LuckPerms luckPerms = LuckPermsProvider.get();
/*  21 */   static UserManager userManager = luckPerms.getUserManager();
/*  22 */   static GroupManager groupManager = luckPerms.getGroupManager();
/*     */ 
/*     */   
/*     */   public static String getPrefix(Player p) {
/*  26 */     User user = userManager.getUser(p.getUniqueId());
/*  27 */     if (user == null || user.getCachedData().getMetaData().getPrefix() == null) {
/*  28 */       return "§7";
/*     */     }
/*  30 */     return ChatColor.translateAlternateColorCodes('&', user.getCachedData().getMetaData().getPrefix());
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getSuffix(Player p) {
/*  35 */     User user = userManager.getUser(p.getUniqueId());
/*  36 */     if (user == null || user.getCachedData().getMetaData().getSuffix() == null) {
/*  37 */       return "§7";
/*     */     }
/*  39 */     return ChatColor.translateAlternateColorCodes('&', user.getCachedData().getMetaData().getSuffix());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getPrefixColor(Player p) {
/*  47 */     String prefix = getPrefix(p);
/*  48 */     return getLastColor(prefix).replace(" ", "");
/*     */   }
/*     */   
/*     */   public static String getLastColor(String s) {
/*  52 */     if (s == null || s.isEmpty()) {
/*  53 */       return "";
/*     */     }
/*  55 */     int c = s.lastIndexOf('§');
/*  56 */     if (c == -1) {
/*  57 */       c = s.lastIndexOf("&");
/*  58 */       if (c == -1) {
/*  59 */         return "";
/*     */       }
/*     */     } 
/*  62 */     String clr = s.substring(c);
/*  63 */     if (c - 2 >= 0 && (
/*  64 */       s.charAt(c - 2) == '§' || s.charAt(c - 2) == '&')) {
/*  65 */       clr = s.substring(c - 2);
/*     */     }
/*     */     
/*  68 */     return clr;
/*     */   }
/*     */   public static Group getGroup(Player p) {
/*  71 */     User user = userManager.getUser(p.getUniqueId());
/*  72 */     if (user == null) {
/*  73 */       return null;
/*     */     }
/*  75 */     return user.getNodes().stream()
/*  76 */       .filter(NodeType.INHERITANCE::matches)
/*  77 */       .map(NodeType.INHERITANCE::cast)
/*  78 */       .filter(n -> n.getContexts().isSatisfiedBy((ContextSet)QueryOptions.defaultContextualOptions().context()))
/*  79 */       .map(InheritanceNode::getGroupName)
/*  80 */       .map(n -> groupManager.getGroup(n))
/*  81 */       .filter(Objects::nonNull)
/*  82 */       .min(LuckPermsUtil::compare).orElse(groupManager.getGroup("default"));
/*     */   }
/*     */   public static Integer getGrouppriority(Group group) {
/*  85 */     return Integer.valueOf(1000 - group.getWeight().getAsInt());
/*     */   } public static String getRank(@NonNull Player p) {
/*  87 */     if (p == null) throw new NullPointerException("p is marked non-null but is null"); 
/*  88 */     User user = userManager.getUser(p.getUniqueId());
/*  89 */     assert user != null;
/*  90 */     return user.getNodes().stream()
/*  91 */       .filter(NodeType.INHERITANCE::matches)
/*  92 */       .map(NodeType.INHERITANCE::cast)
/*  93 */       .filter(n -> n.getContexts().isSatisfiedBy((ContextSet)QueryOptions.defaultContextualOptions().context()))
/*  94 */       .map(InheritanceNode::getGroupName)
/*  95 */       .map(n -> groupManager.getGroup(n))
/*  96 */       .filter(Objects::nonNull)
/*  97 */       .min(LuckPermsUtil::compare)
/*  98 */       .map(Group::getName)
/*  99 */       .map(LuckPermsUtil::convertGroupDisplayName)
/* 100 */       .orElse("");
/*     */   }
/*     */   private static String convertGroupDisplayName(String groupName) {
/* 103 */     Group group = groupManager.getGroup(groupName);
/* 104 */     if (group != null) {
/* 105 */       groupName = ChatColor.translateAlternateColorCodes('&', group.getFriendlyName());
/*     */     }
/* 107 */     return groupName;
/*     */   }
/*     */   public static String formatTime(int i) {
/* 110 */     SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy/MM/dd hh:mm:ss");
/* 111 */     return simpleDateFormat.format(Long.valueOf(System.currentTimeMillis() + (i * 1000)));
/*     */   }
/*     */   
/*     */   private static int compare(Group o1, Group o2) {
/* 115 */     int ret = Integer.compare(o1.getWeight().orElse(0), o2.getWeight().orElse(0));
/* 116 */     if (ret == 1) return -1; 
/* 117 */     return 1;
/*     */   }
/*     */ }