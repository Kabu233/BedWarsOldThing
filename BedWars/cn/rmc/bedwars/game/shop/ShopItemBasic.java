/*     */ package cn.rmc.bedwars.game.shop;
/*     */ import java.util.List;
/*     */ import cn.rmc.bedwars.enums.ITeamUpgrade;
/*     */ import cn.rmc.bedwars.game.PlayerData;
/*     */ import cn.rmc.bedwars.game.Team;
/*     */ import cn.rmc.bedwars.inventory.MenuBasic;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ public abstract class ShopItemBasic {
/*     */   protected PlayerData pd;
/*     */   protected String displayname;
/*     */   protected ItemStack show;
/*     */   protected ItemStack give;
/*     */   
/*     */   protected MenuBasic menu() {
/*  16 */     return this.pd.getCurrentShop();
/*     */   }
/*     */   private Object type; private String totaltype; protected Price price; protected Team team;
/*     */   
/*     */   public Object getType() {
/*  21 */     return this.type;
/*     */   } public String getTotaltype() {
/*  23 */     return this.totaltype;
/*     */   } public Price getPrice() {
/*  25 */     return this.price;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShopItemBasic(PlayerData pd, String totaltype, Object type, String displayname, ItemStack show, ItemStack give, Price price) {
/*  32 */     this.pd = pd;
/*  33 */     this.displayname = displayname;
/*  34 */     this.show = show;
/*  35 */     this.give = give;
/*  36 */     this.price = price;
/*  37 */     this.totaltype = totaltype;
/*  38 */     this.type = type;
/*     */   }
/*     */   
/*     */   public ShopItemBasic(PlayerData pd, String totaltype, Object type, String displayname, ItemStack show, Price price) {
/*  42 */     this.pd = pd;
/*  43 */     this.displayname = displayname;
/*  44 */     this.show = show;
/*  45 */     this.price = price;
/*  46 */     this.totaltype = totaltype;
/*  47 */     this.type = type;
/*     */   }
/*     */   public ShopItemBasic(Team team, ITeamUpgrade teamUpgrade) {
/*     */     int amount;
/*  51 */     this.team = team;
/*  52 */     switch (teamUpgrade.getType()) {
/*     */       case NORMAL:
/*  54 */         this.displayname = teamUpgrade.getDisplayName();
/*  55 */         this.show = teamUpgrade.showItem((Integer)team.getTeamUpgrade().get(teamUpgrade), team.getGame());
/*  56 */         this
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*  70 */           .price = team.getGame().getGameMode().getIsSpecial().booleanValue() ? ((teamUpgrade.getMaxLevel().intValue() == 1) ? ((teamUpgrade.getSpecialPrice() == null) ? teamUpgrade.getPrice() : teamUpgrade.getSpecialPrice()) : ((((Price[])((List)teamUpgrade.getInfo().get(Integer.valueOf(1))).get(1)).length == 2) ? (((Integer)team.getTeamUpgrade().get(teamUpgrade)).equals(teamUpgrade.getMaxLevel()) ? ((Price[])((List)teamUpgrade.getInfo().get(team.getTeamUpgrade().get(teamUpgrade))).get(1))[1] : ((Price[])((List)teamUpgrade.getInfo().get(Integer.valueOf(((Integer)team.getTeamUpgrade().get(teamUpgrade)).intValue() + 1))).get(1))[1]) : (((Integer)team.getTeamUpgrade().get(teamUpgrade)).equals(teamUpgrade.getMaxLevel()) ? ((Price[])((List)teamUpgrade.getInfo().get(team.getTeamUpgrade().get(teamUpgrade))).get(1))[0] : ((Price[])((List)teamUpgrade.getInfo().get(Integer.valueOf(((Integer)team.getTeamUpgrade().get(teamUpgrade)).intValue() + 1))).get(1))[0]))) : ((teamUpgrade.getMaxLevel().intValue() == 1) ? teamUpgrade.getPrice() : (((Integer)team.getTeamUpgrade().get(teamUpgrade)).equals(teamUpgrade.getMaxLevel()) ? ((Price[])((List)teamUpgrade.getInfo().get(team.getTeamUpgrade().get(teamUpgrade))).get(1))[0] : ((Price[])((List)teamUpgrade.getInfo().get(Integer.valueOf(((Integer)team.getTeamUpgrade().get(teamUpgrade)).intValue() + 1))).get(1))[0]));
/*     */         break;
/*     */       case TRAP:
/*  73 */         this.displayname = teamUpgrade.getDisplayName();
/*  74 */         this.show = teamUpgrade.showItem(Integer.valueOf(team.getTraps().size()), team.getGame());
/*  75 */         amount = 99999;
/*  76 */         switch (team.getTraps().size()) {
/*     */           case 0:
/*  78 */             amount = 1;
/*     */             break;
/*     */           case 1:
/*  81 */             amount = 2;
/*     */             break;
/*     */           case 2:
/*  84 */             amount = 4;
/*     */             break;
/*     */         } 
/*  87 */         this.price = new Price(Resource.DIAMOND, Integer.valueOf(amount));
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   public ShopItemBasic(PlayerData pd, String totaltype, Object type) {
/*  93 */     this.pd = pd;
/*  94 */     this.type = type;
/*  95 */     this.totaltype = totaltype;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 102 */     return (new ToStringBuilder(this))
/* 103 */       .append("pd", this.pd)
/* 104 */       .append("displayname", this.displayname)
/* 105 */       .append("show", this.show)
/* 106 */       .append("give", this.give)
/* 107 */       .append("type", this.type)
/* 108 */       .append("price", this.price).toString();
/*     */   }
/*     */   public abstract InventoryUI.AbstractClickableItem showItem(PlayerData paramPlayerData, Where paramWhere);
/*     */   
/* 112 */   public enum Where { NON,
/* 113 */     Quick,
/* 114 */     Normal,
/* 115 */     QuickEditor; }
/*     */ 
/*     */ }