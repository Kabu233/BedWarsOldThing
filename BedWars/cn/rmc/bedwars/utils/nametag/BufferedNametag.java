/*    */ package cn.rmc.bedwars.utils.nametag;
/*    */ public class BufferedNametag { @NonNull
/*    */   private String groupName; @NonNull
/*    */   private String prefix; @NonNull
/*    */   private String suffix;
/*    */   
/*  7 */   public void setGroupName(@NonNull String groupName) { if (groupName == null) throw new NullPointerException("groupName is marked non-null but is null");  this.groupName = groupName; } @NonNull private boolean friendlyInvis; private NameTagVisibility nameTagVisibility; @NonNull private Player player; public void setPrefix(@NonNull String prefix) { if (prefix == null) throw new NullPointerException("prefix is marked non-null but is null");  this.prefix = prefix; } public void setSuffix(@NonNull String suffix) { if (suffix == null) throw new NullPointerException("suffix is marked non-null but is null");  this.suffix = suffix; } public void setFriendlyInvis(@NonNull boolean friendlyInvis) { this.friendlyInvis = friendlyInvis; } public void setNameTagVisibility(NameTagVisibility nameTagVisibility) { this.nameTagVisibility = nameTagVisibility; } public void setPlayer(@NonNull Player player) { if (player == null) throw new NullPointerException("player is marked non-null but is null");  this.player = player; } public BufferedNametag(@NonNull String groupName, @NonNull String prefix, @NonNull String suffix, @NonNull boolean friendlyInvis, NameTagVisibility nameTagVisibility, @NonNull Player player) { if (groupName == null) throw new NullPointerException("groupName is marked non-null but is null");  if (prefix == null) throw new NullPointerException("prefix is marked non-null but is null");  if (suffix == null) throw new NullPointerException("suffix is marked non-null but is null");  if (player == null) throw new NullPointerException("player is marked non-null but is null");  this.groupName = groupName; this.prefix = prefix; this.suffix = suffix; this.friendlyInvis = friendlyInvis; this.nameTagVisibility = nameTagVisibility; this.player = player; } public BufferedNametag(@NonNull String groupName, @NonNull String prefix, @NonNull String suffix, @NonNull boolean friendlyInvis, @NonNull Player player) {
/*  8 */     if (groupName == null) throw new NullPointerException("groupName is marked non-null but is null");  if (prefix == null) throw new NullPointerException("prefix is marked non-null but is null");  if (suffix == null) throw new NullPointerException("suffix is marked non-null but is null");  if (player == null) throw new NullPointerException("player is marked non-null but is null");  this.groupName = groupName; this.prefix = prefix; this.suffix = suffix; this.friendlyInvis = friendlyInvis; this.player = player;
/*    */   }
/*    */   
/*    */   @NonNull
/* 12 */   public String getGroupName() { return this.groupName; } @NonNull public String getPrefix() { return this.prefix; } @NonNull public String getSuffix() { return this.suffix; }
/*    */   @NonNull
/* 14 */   public boolean isFriendlyInvis() { return this.friendlyInvis; }
/* 15 */   public NameTagVisibility getNameTagVisibility() { return this.nameTagVisibility; } @NonNull
/*    */   public Player getPlayer() {
/* 17 */     return this.player;
/*    */   } }