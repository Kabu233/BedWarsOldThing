/*     */ package cn.rmc.bedwarslobby.util;
/*     */ 
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Objects;
/*     */ import java.util.UUID;
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
/*     */ public class LuckPermsUtil
/*     */ {
/*  22 */   static LuckPerms luckPerms = LuckPermsProvider.get();
/*  23 */   static UserManager userManager = luckPerms.getUserManager();
/*  24 */   static GroupManager groupManager = luckPerms.getGroupManager();
/*     */ 
/*     */   
/*     */   public static String getPrefix(Player p) {
/*  28 */     return getPrefix(p.getUniqueId());
/*     */   }
/*     */   
/*     */   public static String getPrefix(UUID uuid) {
/*  32 */     boolean clean = false;
/*  33 */     if (!userManager.isLoaded(uuid)) {
/*  34 */       clean = true;
/*  35 */       userManager.loadUser(uuid);
/*     */     } 
/*  37 */     User user = userManager.getUser(uuid);
/*  38 */     String prefix = (user == null || user.getCachedData().getMetaData(QueryOptions.defaultContextualOptions()).getPrefix() == null) ? null : user.getCachedData().getMetaData(QueryOptions.defaultContextualOptions()).getPrefix();
/*  39 */     if (clean && user != null) {
/*  40 */       userManager.cleanupUser(user);
/*     */     }
/*  42 */     if (prefix == null || prefix.contains("§f")) {
/*  43 */       return "§7";
/*     */     }
/*  45 */     return ChatColor.translateAlternateColorCodes('&', prefix);
/*     */   }
/*     */   
/*     */   public static String getSuffix(Player p) {
/*  49 */     User user = userManager.getUser(p.getUniqueId());
/*  50 */     if (user == null || user.getCachedData().getMetaData().getSuffix() == null) {
/*  51 */       return "§7";
/*     */     }
/*  53 */     return ChatColor.translateAlternateColorCodes('&', user.getCachedData().getMetaData().getSuffix());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Group getGroup(Player p) {
/*  61 */     User user = userManager.getUser(p.getUniqueId());
/*  62 */     if (user == null) {
/*  63 */       return null;
/*     */     }
/*  65 */     return user.getNodes().stream()
/*  66 */       .filter(NodeType.INHERITANCE::matches)
/*  67 */       .map(NodeType.INHERITANCE::cast)
/*  68 */       .filter(n -> n.getContexts().isSatisfiedBy((ContextSet)QueryOptions.defaultContextualOptions().context()))
/*  69 */       .map(InheritanceNode::getGroupName)
/*  70 */       .map(n -> groupManager.getGroup(n))
/*  71 */       .filter(Objects::nonNull)
/*  72 */       .min(LuckPermsUtil::compare).orElse(groupManager.getGroup("default"));
/*     */   } public static Integer getGrouppriority(@NonNull Group group) {
/*  74 */     if (group == null) throw new NullPointerException("group is marked non-null but is null"); 
/*  75 */     return Integer.valueOf(1000 - group.getWeight().getAsInt());
/*     */   } public static String getRank(@NonNull Player p) {
/*  77 */     if (p == null) throw new NullPointerException("p is marked non-null but is null"); 
/*  78 */     User user = userManager.getUser(p.getUniqueId());
/*  79 */     assert user != null;
/*  80 */     return user.getNodes().stream()
/*  81 */       .filter(NodeType.INHERITANCE::matches)
/*  82 */       .map(NodeType.INHERITANCE::cast)
/*  83 */       .filter(n -> n.getContexts().isSatisfiedBy((ContextSet)QueryOptions.defaultContextualOptions().context()))
/*  84 */       .map(InheritanceNode::getGroupName)
/*  85 */       .map(n -> groupManager.getGroup(n))
/*  86 */       .filter(Objects::nonNull)
/*  87 */       .min(LuckPermsUtil::compare)
/*  88 */       .map(Group::getName)
/*  89 */       .map(LuckPermsUtil::convertGroupDisplayName)
/*  90 */       .orElse("");
/*     */   }
/*     */   private static String convertGroupDisplayName(String groupName) {
/*  93 */     Group group = groupManager.getGroup(groupName);
/*  94 */     if (group != null) {
/*  95 */       groupName = ChatColor.translateAlternateColorCodes('&', group.getFriendlyName());
/*     */     }
/*  97 */     return groupName;
/*     */   }
/*     */   public static String formatTime(int i) {
/* 100 */     SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy/MM/dd hh:mm:ss");
/* 101 */     return simpleDateFormat.format(Long.valueOf(System.currentTimeMillis() + (i * 1000)));
/*     */   }
/*     */   
/*     */   private static int compare(Group o1, Group o2) {
/* 105 */     int ret = Integer.compare(o1.getWeight().orElse(0), o2.getWeight().orElse(0));
/* 106 */     if (ret == 1) return -1; 
/* 107 */     return 1;
/*     */   }
/*     */   
/*     */   public static String getPrefixColor(UUID uuid) {
/* 111 */     String prefix = getPrefix(uuid);
/* 112 */     return getLastColor(prefix).replace(" ", "");
/*     */   }
/*     */   
/*     */   public static String getLastColor(String s) {
/* 116 */     if (s != null && !s.isEmpty()) {
/* 117 */       int c = s.lastIndexOf('§');
/* 118 */       if (c == -1) {
/* 119 */         c = s.lastIndexOf("&");
/* 120 */         if (c == -1) {
/* 121 */           return "";
/*     */         }
/*     */       } 
/*     */       
/* 125 */       String clr = s.substring(c);
/* 126 */       if (c - 2 >= 0 && (s.charAt(c - 2) == '§' || s.charAt(c - 2) == '&')) {
/* 127 */         clr = s.substring(c - 2);
/*     */       }
/*     */       
/* 130 */       return clr;
/*     */     } 
/* 132 */     return "";
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\BedWarsLobby-1.0-SNAPSHOT.jar!\me\kabusama\bedwarslobb\\util\LuckPermsUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */