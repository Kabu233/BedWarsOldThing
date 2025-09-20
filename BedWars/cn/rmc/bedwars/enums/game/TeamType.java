/*    */ package cn.rmc.bedwars.enums.game;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.Color;
/*    */ import org.bukkit.DyeColor;
/*    */ 
/*    */ public enum TeamType {
/*    */   private final String ez;
/*    */   private final String ezname;
/*    */   private final String displayname;
/*    */   private final String where;
/* 13 */   NON("N", "无", "无", Color.BLACK, ChatColor.getByChar('0'), DyeColor.BLACK),
/* 14 */   RED("R", "§c红", "红队", Color.RED, ChatColor.getByChar('c'), DyeColor.RED),
/* 15 */   BLUE("B", "§9蓝", "蓝队", Color.BLUE, ChatColor.getByChar('9'), DyeColor.BLUE),
/* 16 */   GREEN("G", "§a绿", "绿队", Color.fromRGB(65280), ChatColor.getByChar('a'), DyeColor.LIME),
/* 17 */   YELLOW("Y", "§e黄", "黄队", Color.YELLOW, ChatColor.getByChar('e'), DyeColor.YELLOW),
/* 18 */   AQUA("A", "§b青", "青队", Color.AQUA, ChatColor.AQUA, DyeColor.LIGHT_BLUE),
/* 19 */   WHITE("W", "§f白", "白队", Color.WHITE, ChatColor.WHITE, DyeColor.WHITE),
/* 20 */   PINK("P", "§d粉", "粉队", Color.fromRGB(255, 192, 203), ChatColor.LIGHT_PURPLE, DyeColor.PINK),
/* 21 */   GARY("G", "§8灰", "灰队", Color.GRAY, ChatColor.DARK_GRAY, DyeColor.GRAY); private final Color color; private final ChatColor chatColor; public String getEz() {
/* 22 */     return this.ez;
/*    */   } private final DyeColor dyeColor; private final String teamName; public String getEzname() {
/* 24 */     return this.ezname;
/*    */   } public String getDisplayname() {
/* 26 */     return this.displayname;
/*    */   } public String getWhere() {
/* 28 */     return this.where;
/*    */   } public Color getColor() {
/* 30 */     return this.color;
/*    */   } public ChatColor getChatColor() {
/* 32 */     return this.chatColor;
/*    */   } public DyeColor getDyeColor() {
/* 34 */     return this.dyeColor;
/*    */   } public String getTeamName() {
/* 36 */     return this.teamName;
/*    */   }
/*    */   
/*    */   TeamType(String ez, String ezname, String displayname, Color color, ChatColor chatcolor, DyeColor dyeColor) {
/* 40 */     this.ez = ez;
/* 41 */     this.ezname = ezname;
/* 42 */     this.displayname = displayname;
/* 43 */     this.where = name().toLowerCase();
/* 44 */     this.color = color;
/* 45 */     this.chatColor = chatcolor;
/* 46 */     this.dyeColor = dyeColor;
/* 47 */     this.teamName = name().toLowerCase();
/*    */   }
/*    */   public String getwithcolor() {
/* 50 */     return this.chatColor.toString() + this.displayname;
/*    */   }
/*    */   public static List<TeamType> valueswithList() {
/* 53 */     return new ArrayList<>(Arrays.asList(values()));
/*    */   }
/*    */ }