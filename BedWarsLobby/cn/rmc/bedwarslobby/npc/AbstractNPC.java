/*    */ package cn.rmc.bedwarslobby.npc;
/*    */ import java.util.List;
/*    */ import cn.rmc.bedwarslobby.BedWarsLobby;
/*    */ import cn.rmc.bedwarslobby.util.holographic.Holographic;
/*    */ import net.jitse.npclib.NPCLib;
/*    */ import net.jitse.npclib.api.NPC;
/*    */ import net.jitse.npclib.api.skin.Skin;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.plugin.java.JavaPlugin;
/*    */ 
/*    */ public abstract class AbstractNPC {
/*    */   NPC npc;
/*    */   String name;
/*    */   
/*    */   public NPC getNpc() {
/* 18 */     return this.npc;
/*    */   } Skin skin; boolean useHolographic; public Player player; public String getName() {
/* 20 */     return this.name;
/*    */   } public Skin getSkin() {
/* 22 */     return this.skin;
/*    */   } public boolean isUseHolographic() {
/* 24 */     return this.useHolographic;
/*    */   } public Player getPlayer() {
/* 26 */     return this.player;
/*    */   }
/*    */   
/* 29 */   private final NPCLib lib = new NPCLib((JavaPlugin)BedWarsLobby.getInstance());
/*    */   
/*    */   public AbstractNPC(Player player, String name) {
/* 32 */     this.name = name;
/* 33 */     this.player = player;
/* 34 */     this.skin = null;
/* 35 */     this.useHolographic = false;
/*    */     
/* 37 */     this.npc = this.lib.createNPC();
/*    */   }
/*    */   
/*    */   public AbstractNPC(Player player, String name, Skin skin) {
/* 41 */     this.name = name;
/* 42 */     this.player = player;
/* 43 */     this.skin = skin;
/* 44 */     this.useHolographic = false;
/*    */     
/* 46 */     this.npc = this.lib.createNPC();
/*    */   }
/*    */   
/*    */   public AbstractNPC(Player player, String name, Skin skin, boolean useHolographic) {
/* 50 */     this.name = name;
/* 51 */     this.player = player;
/* 52 */     this.skin = skin;
/* 53 */     this.useHolographic = useHolographic;
/*    */     
/* 55 */     this.npc = this.lib.createNPC();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Holographic getHolographic() {
/* 66 */     Holographic holographic = new Holographic(getSpawnLocation().add(0.0D, getOff(), 0.0D));
/* 67 */     getHolographicLine().forEach(holographic::addLine);
/* 68 */     NPCStorage.holographics.put(holographic, this);
/* 69 */     return holographic;
/*    */   }
/*    */   
/*    */   public abstract List<String> getHolographicLine();
/*    */   
/*    */   public abstract double getOff();
/*    */   
/*    */   public abstract Location getSpawnLocation();
/*    */   
/*    */   public abstract ItemStack getHeldItem();
/*    */   
/*    */   public abstract boolean useSelfSkin();
/*    */   
/*    */   public abstract void onClick(Player paramPlayer);
/*    */ }