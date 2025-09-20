/*     */ package cn.rmc.bedwars.game.dream.ultimate;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import cn.rmc.bedwars.BedWars;
/*     */ import cn.rmc.bedwars.enums.Data;
/*     */ import cn.rmc.bedwars.enums.game.GameMode;
/*     */ import cn.rmc.bedwars.game.Game;
/*     */ import cn.rmc.bedwars.game.PlayerData;
/*     */ import cn.rmc.bedwars.game.dream.DreamManager;
/*     */ import cn.rmc.bedwars.inventory.game.ShopBasic;
/*     */ import cn.rmc.bedwars.utils.Group;
/*     */ import cn.rmc.bedwars.utils.item.ItemBuilder;
/*     */ import cn.rmc.bedwars.utils.player.DataUtils;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.event.HandlerList;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.json.JSONObject;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UltimateManager
/*     */   implements DreamManager
/*     */ {
/*     */   Game g;
/*     */   GameMode gm;
/*  28 */   HashMap<PlayerData, UltimateBasic> ultimates = new HashMap<>(); Listener listener; public HashMap<PlayerData, UltimateBasic> getUltimates() { return this.ultimates; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UltimateManager(Game g) {
/*  34 */     this.g = g;
/*  35 */     this.gm = g.getGameMode();
/*     */   }
/*     */ 
/*     */   
/*     */   public void start() {
/*  40 */     for (PlayerData pd : this.g.getOnlinePlayers()) {
/*  41 */       initP(pd);
/*     */     }
/*  43 */     Bukkit.getScheduler().runTaskLaterAsynchronously((Plugin)BedWars.getInstance(), () -> { for (PlayerData player : this.g.getOnlinePlayers()) { player.getPlayer().sendMessage("§a§l超能力已启用!"); System.out.println(player.getPlayer().getName()); if (this.ultimates.containsKey(player)) ((UltimateBasic)this.ultimates.get(player)).onStart();  }  }200L);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  51 */     Bukkit.getPluginManager().registerEvents(this.listener = new UltimateListener(this), (Plugin)BedWars.getInstance());
/*     */   }
/*     */ 
/*     */   
/*     */   public void dispose() {
/*  56 */     this.ultimates.forEach((playerData, ultimateBasic) -> ultimateBasic.dispose());
/*  57 */     HandlerList.unregisterAll(this.listener);
/*     */   }
/*     */ 
/*     */   
/*     */   public void init() {
/*  62 */     this.g.setStartmessage(new String[] { "§a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬", "                                   起床战争", "", "        §e§l在商店里选择一项超能力！它们将会在10秒后启用！", "", "§a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬" });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  70 */     ShopBasic.setShop(Integer.valueOf(8), new Group((new ItemBuilder(Material.BEACON)).setName("§a超能力"), UltimateShop.class));
/*     */   }
/*     */   public void initP(PlayerData pd) {
/*     */     JSONObject jo;
/*     */     JSONObject jou;
/*  75 */     if (DataUtils.isValueeNull(pd.getPlayer().getUniqueId().toString(), Data.Field.DREAMSINFO)) {
/*  76 */       jo = new JSONObject();
/*     */     } else {
/*  78 */       jo = new JSONObject(DataUtils.get(pd.getPlayer().getUniqueId().toString(), Data.Field.DREAMSINFO));
/*     */     } 
/*     */     
/*  81 */     if (jo.isNull("Ultimates")) {
/*  82 */       jou = new JSONObject();
/*     */     } else {
/*  84 */       jou = jo.getJSONObject("Ultimates");
/*     */     } 
/*  86 */     if (jou.isNull("Ultimates_Type")) {
/*  87 */       setUltimate(pd, UltimateType.KANGAROO, 1);
/*     */     } else {
/*  89 */       setUltimate(pd, UltimateType.valueOf(jou.getString("Ultimates_Type")), 0);
/*     */     } 
/*     */   }
/*     */   public void setUltimate(PlayerData pd, UltimateType type, int i) {
/*     */     JSONObject jo, jou;
/*  94 */     if (this.ultimates.containsKey(pd)) {
/*  95 */       ((UltimateBasic)this.ultimates.get(pd)).dispose();
/*     */     }
/*     */     
/*  98 */     if (DataUtils.isValueeNull(pd.getPlayer().getUniqueId().toString(), Data.Field.DREAMSINFO)) {
/*  99 */       jo = new JSONObject();
/*     */     } else {
/* 101 */       jo = new JSONObject(DataUtils.get(pd.getPlayer().getUniqueId().toString(), Data.Field.DREAMSINFO));
/*     */     } 
/*     */     
/* 104 */     if (jo.isNull("Ultimates")) {
/* 105 */       jou = new JSONObject();
/*     */     } else {
/* 107 */       jou = jo.getJSONObject("Ultimates");
/*     */     } 
/* 109 */     jou.put("Ultimates_Type", type.name());
/* 110 */     jo.put("Ultimates", jou);
/* 111 */     DataUtils.set(pd.getPlayer().getUniqueId().toString(), Data.Field.DREAMSINFO, jo.toString());
/* 112 */     UltimateBasic ultimate = type.getUltimate(pd.getPlayer());
/* 113 */     if (canGo() && (ultimate.getType().getCooldown().intValue() == -1 || pd.getPlayer().getLevel() <= 0)) ultimate.onStart(); 
/* 114 */     this.ultimates.put(pd, ultimate);
/* 115 */     switch (i) {
/*     */       case 2:
/* 117 */         pd.getPlayer().sendMessage("§a更改你的超能力为§6" + ultimate.getType().getDBName() + "§a!");
/*     */         break;
/*     */       
/*     */       case 1:
/* 121 */         pd.getPlayer().sendMessage("§e你尚未选择一项超能力,因此你的超能力被设定为§6§l" + ultimate.getType().getDBName() + "§e!");
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canGo() {
/* 128 */     return (System.currentTimeMillis() - this.g.getStartTimeStamp().longValue() > 10000L);
/*     */   }
/*     */   
/*     */   public void delete(PlayerData pd) {
/* 132 */     if (this.ultimates.containsKey(pd)) {
/* 133 */       if (((UltimateBasic)this.ultimates.get(pd)).exptask != null) {
/* 134 */         ((UltimateBasic)this.ultimates.get(pd)).exptask.cancel();
/*     */       }
/* 136 */       ((UltimateBasic)this.ultimates.get(pd)).dispose();
/*     */       
/* 138 */       this.ultimates.remove(pd);
/*     */     } 
/*     */   }
/*     */   
/*     */   public UltimateType getUltimate(PlayerData pd) {
/* 143 */     return this.ultimates.containsKey(pd) ? ((UltimateBasic)this.ultimates.get(pd)).getType() : null;
/*     */   }
/*     */ }