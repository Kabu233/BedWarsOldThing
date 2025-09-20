/*     */ package cn.rmc.bedwars.game.shop.items;
/*     */ 
/*     */ import java.util.Map;
/*     */ import cn.rmc.bedwars.enums.ITeamUpgrade;
/*     */ import cn.rmc.bedwars.event.TeamUpgradeBuyEvent;
/*     */ import cn.rmc.bedwars.game.PlayerData;
/*     */ import cn.rmc.bedwars.game.Team;
/*     */ import cn.rmc.bedwars.game.shop.Price;
/*     */ import cn.rmc.bedwars.game.shop.ShopItemBasic;
/*     */ import cn.rmc.bedwars.inventory.game.shops.TeamUpgradeShop;
/*     */ import cn.rmc.bedwars.utils.MathUtils;
/*     */ import cn.rmc.bedwars.utils.inventory.InventoryUI;
/*     */ import cn.rmc.bedwars.utils.item.ItemBuilder;
/*     */ import cn.rmc.bedwars.utils.player.InventoryUtils;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.inventory.ClickType;
/*     */ import org.bukkit.event.inventory.InventoryClickEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ public class TeamShopItem extends ShopItemBasic {
/*     */   public TeamShopItem(Team team, ITeamUpgrade item) {
/*  24 */     super(team, item);
/*  25 */     this.upgrade = item;
/*     */   }
/*     */   ITeamUpgrade upgrade;
/*     */   
/*     */   public InventoryUI.AbstractClickableItem showItem(final PlayerData pd, ShopItemBasic.Where where) {
/*  30 */     final Integer level = (Integer)this.team.getTeamUpgrade().get(this.upgrade);
/*  31 */     ItemBuilder ib = new ItemBuilder(this.show.clone());
/*  32 */     Sort sort = null;
/*  33 */     switch (this.upgrade.getType()) {
/*     */       case BOUGHT:
/*  35 */         if (this.upgrade.getMaxLevel().intValue() != 1)
/*  36 */           if (level.intValue() >= this.upgrade.getMaxLevel().intValue()) {
/*  37 */             this.displayname += " " + MathUtils.toRome(this.upgrade.getMaxLevel().intValue());
/*     */           } else {
/*  39 */             this.displayname += " " + MathUtils.toRome(level.intValue() + 1);
/*     */           }  
/*     */       case UNENOUGH:
/*  42 */         if (this.upgrade.getMaxLevel() != null && level.intValue() >= this.upgrade.getMaxLevel().intValue()) {
/*  43 */           ib.setName("§a" + this.displayname);
/*  44 */           sort = Sort.BOUGHT;
/*  45 */           ib.addLoreLine("§a已解锁!"); break;
/*  46 */         }  if (InventoryUtils.isEnoughItem(pd.getPlayer(), this.price).booleanValue()) {
/*  47 */           ib.setName("§a" + this.displayname);
/*  48 */           sort = Sort.NON;
/*  49 */           ib.addLoreLine("§e点击购买!"); break;
/*     */         } 
/*  51 */         ib.setName("§c" + this.displayname);
/*  52 */         sort = Sort.UNENOUGH;
/*  53 */         ib.addLoreLine("§c你没有足够的" + this.price.getResource().getDisplayName());
/*     */         break;
/*     */     } 
/*  56 */     final Sort finalSort = sort;
/*  57 */     return new InventoryUI.AbstractClickableItem(ib.toItemStack())
/*     */       {
/*     */         public void onClick(InventoryClickEvent e) {
/*  60 */           if (e.getAction() == null)
/*  61 */             return;  if (e.getClick() == null)
/*  62 */             return;  if (e.getClick() == ClickType.LEFT)
/*  63 */             switch (finalSort) {
/*     */ 
/*     */               
/*     */               case UNENOUGH:
/*  67 */                 pd.getPlayer().sendMessage("§c资源不足, 无法解锁该升级!");
/*  68 */                 pd.getPlayer().playSound(pd.getPlayer().getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 0.5F);
/*     */                 break;
/*     */               case NON:
/*  71 */                 for (PlayerData player : pd.getTeam().getAlivePlayers()) {
/*  72 */                   player.getPlayer().sendMessage("§a" + pd.getPlayer().getPlayer().getName() + "购买了§6" + TeamShopItem.this.upgrade.getDisplayName());
/*     */                 }
/*  74 */                 InventoryUtils.deleteitem(pd.getPlayer(), TeamShopItem.this.price);
/*  75 */                 switch (TeamShopItem.this.upgrade.getType()) {
/*     */                   case BOUGHT:
/*  77 */                     TeamShopItem.this.team.getTeamUpgrade().put(TeamShopItem.this.upgrade, Integer.valueOf(level.intValue() + 1));
/*  78 */                     Bukkit.getPluginManager().callEvent((Event)new TeamUpgradeBuyEvent(pd.getTeam(), TeamShopItem.this.upgrade, Integer.valueOf(level.intValue() + 1)));
/*  79 */                     pd.getCurrentShop().Setup();
/*     */                     break;
/*     */                   case UNENOUGH:
/*  82 */                     TeamShopItem.this.team.getTraps().add(new Map.Entry<ITeamUpgrade, PlayerData>()
/*     */                         {
/*     */                           public ITeamUpgrade getKey() {
/*  85 */                             return TeamShopItem.this.upgrade;
/*     */                           }
/*     */ 
/*     */                           
/*     */                           public PlayerData getValue() {
/*  90 */                             return pd;
/*     */                           }
/*     */ 
/*     */                           
/*     */                           public PlayerData setValue(PlayerData value) {
/*  95 */                             return null;
/*     */                           }
/*     */                         });
/*  98 */                     (new TeamUpgradeShop(pd.getPlayer())).open();
/*     */                     break;
/*     */                 } 
/* 101 */                 pd.getPlayer().playSound(pd.getPlayer().getLocation(), Sound.NOTE_PLING, 1.0F, 2.0F);
/*     */                 break;
/*     */             }  
/*     */         }
/*     */       };
/*     */   }
/*     */   
/* 108 */   public enum Sort { BOUGHT, NON, UNENOUGH; }
/*     */ 
/*     */ }