/*    */ package cn.rmc.bedwarslobby.loot;
/*    */ 
/*    */ import com.comphenix.protocol.events.PacketEvent;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import cn.rmc.bedwarslobby.BedWarsLobby;
/*    */ import cn.rmc.bedwarslobby.loot.utils.CameraUtils;
/*    */ import cn.rmc.bedwarslobby.loot.utils.SitUtils;
/*    */ import cn.rmc.bedwarslobby.object.PlayerData;
/*    */ import cn.rmc.bedwarslobby.runnable.StateRunnable;
/*    */ import cn.rmc.bedwarslobby.util.holographic.armor.ArmorHolographic;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.scheduler.BukkitRunnable;
/*    */ 
/*    */ 
/*    */ public abstract class LootChestBasic
/*    */ {
/* 23 */   public static List<Player> openPlayers = new ArrayList<>();
/* 24 */   public static HashMap<Player, LootChestBasic> chests = new HashMap<>();
/* 25 */   public static Location orgLocation = new Location(Bukkit.getWorld("world"), -79.5D, 104.0D, 76.5D, -90.0F, 0.0F); PlayerData pd; LootChestType chestType;
/*    */   
/* 27 */   public PlayerData getPd() { return this.pd; } public LootChestType getChestType() {
/* 28 */     return this.chestType;
/* 29 */   } StateRunnable stateRunnable; List<ArmorHolographic> armorHolographics = new ArrayList<>(); public List<ArmorHolographic> getArmorHolographics() { return this.armorHolographics; } public StateRunnable getStateRunnable() {
/* 30 */     return this.stateRunnable;
/* 31 */   } Map<Player, Location> locMap = new HashMap<>(); public Map<Player, Location> getLocMap() { return this.locMap; }
/*    */   
/*    */   public LootChestBasic(Player p, LootChestType chestType) {
/* 34 */     this.pd = BedWarsLobby.getInstance().getPlayerManager().get(p);
/* 35 */     this.chestType = chestType;
/* 36 */     chests.put(p, this);
/* 37 */     this.stateRunnable = new StateRunnable(p);
/* 38 */     this.locMap.put(p, p.getLocation());
/*    */   }
/*    */   
/*    */   public void createHolographic(ArmorHolographic armor) {
/* 42 */     this.armorHolographics.add(armor);
/* 43 */     armor.display(this.pd.getPlayer());
/*    */   }
/*    */   
/*    */   public void removeHolographic(int i) {
/* 47 */     ((ArmorHolographic)this.armorHolographics.get(i)).remove();
/*    */   }
/*    */   
/*    */   public void Start() {
/* 51 */     openPlayers.add(this.pd.getPlayer());
/*    */     
/* 53 */     this.pd.getPlayer().teleport(orgLocation.clone());
/* 54 */     CameraUtils.joinCamera(this.pd.getPlayer());
/*    */     
/* 56 */     for (Player p : Bukkit.getOnlinePlayers()) {
/* 57 */       p.hidePlayer(this.pd.getPlayer());
/*    */     }
/*    */     
/* 60 */     animation(true);
/*    */   }
/*    */   
/*    */   public void Close(final Player player) {
/* 64 */     if (openPlayers.contains(player)) {
/* 65 */       getArmorHolographics().forEach(ArmorHolographic::remove);
/* 66 */       openPlayers.remove(player);
/* 67 */       chests.remove(player, this);
/* 68 */       this.stateRunnable.cancel();
/* 69 */       SitUtils.unsitPlayer(player);
/* 70 */       (new BukkitRunnable() {
/*    */           public void run() {
/* 72 */             LootChestBasic.this.pd.getPlayer().teleport(LootChestBasic.this.locMap.get(player));
/*    */             
/* 74 */             for (Player p : Bukkit.getOnlinePlayers()) {
/* 75 */               p.showPlayer(LootChestBasic.this.pd.getPlayer());
/*    */             
/*    */             }
/*    */           }
/* 79 */         }).runTaskLater((Plugin)BedWarsLobby.getInstance(), 3L);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void PacketReceive(PacketEvent event) {
/* 84 */     onPacketReceive(event);
/*    */   }
/*    */   
/*    */   public abstract void animation(boolean paramBoolean);
/*    */   
/*    */   public abstract void selector();
/*    */   
/*    */   public abstract void openChest();
/*    */   
/*    */   public abstract void lootItem();
/*    */   
/*    */   public abstract void onPacketReceive(PacketEvent paramPacketEvent);
/*    */ }