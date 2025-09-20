/*    */ package cn.rmc.bedwars.game;
/*    */ import java.util.HashMap;
/*    */ import cn.rmc.bedwars.enums.game.TeamType;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.WorldBorder;
/*    */ 
/*    */ public class Map {
/*    */   private String mapname;
/*    */   private Location middle;
/*    */   private int borderSize;
/*    */   private int eachmaxplayer;
/*    */   private GameMode gamemode;
/*    */   private HashMap<TeamType, Location> spawns;
/*    */   private HashMap<TeamType, String> teamdisplayname;
/*    */   private HashMap<TeamType, Location> beds;
/*    */   
/*    */   public String getMapname() {
/* 18 */     return this.mapname;
/*    */   } private HashMap<TeamType, Location> teamchests; private HashMap<TeamType, Location> teamresourcesloc; private HashMap<Resource, ArrayList<Location>> resourceloc; private HashMap<TeamType, Location> itemshop; private HashMap<TeamType, Location> teamshop; private HashMap<TeamType, Location> teampos1; private HashMap<TeamType, Location> teampos2; private Config gamefile; public Location getMiddle() {
/* 20 */     return this.middle;
/*    */   } public int getBorderSize() {
/* 22 */     return this.borderSize;
/*    */   } public int getEachmaxplayer() {
/* 24 */     return this.eachmaxplayer;
/*    */   } public GameMode getGamemode() {
/* 26 */     return this.gamemode;
/*    */   } public HashMap<TeamType, Location> getSpawns() {
/* 28 */     return this.spawns;
/*    */   } public HashMap<TeamType, String> getTeamdisplayname() {
/* 30 */     return this.teamdisplayname;
/*    */   } public HashMap<TeamType, Location> getBeds() {
/* 32 */     return this.beds;
/*    */   } public HashMap<TeamType, Location> getTeamchests() {
/* 34 */     return this.teamchests;
/*    */   } public HashMap<TeamType, Location> getTeamresourcesloc() {
/* 36 */     return this.teamresourcesloc;
/*    */   }
/*    */   public HashMap<Resource, ArrayList<Location>> getResourceloc() {
/* 39 */     return this.resourceloc;
/*    */   } public HashMap<TeamType, Location> getItemshop() {
/* 41 */     return this.itemshop;
/*    */   } public HashMap<TeamType, Location> getTeamshop() {
/* 43 */     return this.teamshop;
/*    */   } public HashMap<TeamType, Location> getTeampos1() {
/* 45 */     return this.teampos1;
/*    */   } public HashMap<TeamType, Location> getTeampos2() {
/* 47 */     return this.teampos2;
/*    */   } public Config getGamefile() {
/* 49 */     return this.gamefile;
/*    */   }
/*    */   
/*    */   public Map(HashMap<String, Object> hashMap) {
/* 53 */     this.mapname = (String)hashMap.get("name");
/* 54 */     this.gamefile = (Config)hashMap.get("gamefile");
/* 55 */     this.middle = (Location)hashMap.get("middle");
/* 56 */     this.gamemode = GameMode.valueOf((String)hashMap.get("gamemode"));
/* 57 */     this.eachmaxplayer = ((Integer)hashMap.get("eachmaxplayer")).intValue();
/* 58 */     this.borderSize = ((Integer)hashMap.get("bordersize")).intValue();
/* 59 */     this.spawns = (HashMap<TeamType, Location>)hashMap.get("teamspawn");
/* 60 */     this.beds = (HashMap<TeamType, Location>)hashMap.get("teambed");
/* 61 */     this.teamdisplayname = (HashMap<TeamType, String>)hashMap.get("teamdisplayname");
/* 62 */     this.teamchests = (HashMap<TeamType, Location>)hashMap.get("teamchest");
/* 63 */     this.teamresourcesloc = (HashMap<TeamType, Location>)hashMap.get("teamresourcesloc");
/* 64 */     this.resourceloc = (HashMap<Resource, ArrayList<Location>>)hashMap.get("resourceloc");
/* 65 */     this.itemshop = (HashMap<TeamType, Location>)hashMap.get("itemshop");
/* 66 */     this.teamshop = (HashMap<TeamType, Location>)hashMap.get("teamshop");
/* 67 */     this.teampos1 = (HashMap<TeamType, Location>)hashMap.get("teampos1");
/* 68 */     this.teampos2 = (HashMap<TeamType, Location>)hashMap.get("teampos2");
/* 69 */     this.middle.getWorld().setGameRuleValue("doMobSpawning", "false");
/* 70 */     this.middle.getWorld().setGameRuleValue("doDaylightCycle", "false");
/* 71 */     this.middle.getWorld().setGameRuleValue("doFireTick", "false");
/* 72 */     this.middle.getWorld().setTime(3000L);
/*    */     
/* 74 */     setupWorldBorder(this.middle, this.borderSize);
/*    */   }
/*    */   
/*    */   public void setupWorldBorder(Location middle, int borderSize) {
/* 78 */     World world = middle.getWorld();
/* 79 */     WorldBorder worldBorder = world.getWorldBorder();
/*    */     
/* 81 */     worldBorder.setCenter(middle);
/* 82 */     worldBorder.setSize(borderSize);
/* 83 */     worldBorder.setDamageAmount(2.0D);
/* 84 */     worldBorder.setDamageBuffer(5.0D);
/* 85 */     worldBorder.setWarningDistance(10);
/* 86 */     worldBorder.setWarningTime(5);
/*    */   }
/*    */ }