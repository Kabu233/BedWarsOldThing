/*    */ package cn.rmc.bedwars.enums.game;
/*    */ import cn.rmc.bedwars.BedWars;
/*    */ import cn.rmc.bedwars.game.PlayerData;
/*    */ import cn.rmc.bedwars.inventory.game.other.ResourceSelectorMenu;
/*    */ import cn.rmc.bedwars.inventory.game.other.SelectorTeamMenu;
/*    */ import cn.rmc.bedwars.utils.inventory.InventoryUI;
/*    */ import cn.rmc.bedwars.utils.item.ItemBuilder;
/*    */ import cn.rmc.bedwars.utils.player.LuckPermsUtil;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.inventory.InventoryClickEvent;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public enum QuickChat {
/*    */   Material material;
/*    */   String name;
/*    */   String[] description;
/* 18 */   HELLO(Material.BOOK, "你好( ﾟ◡ﾟ)/!", new String[0], SelectorType.NON),
/* 19 */   COMING_BACK(Material.BOOK, "我正在回基地的路上", new String[0], SelectorType.NON),
/* 20 */   DEFENDING(Material.IRON_FENCE, "我在守家!", new String[0], SelectorType.NON),
/* 21 */   ATTACKING(Material.IRON_SWORD, "我正在进攻", new String[] { "§7你可以选择团队." }, SelectorType.TEAM),
/* 22 */   COLLECTING(Material.DIAMOND, "我正在收集资源!", new String[] { "§7你将可以选择资源." }, SelectorType.RESOURCE),
/* 23 */   HAVE_RESOURCES(Material.CHEST, "我有资源!", new String[] { "§7你将可以选择资源." }, SelectorType.RESOURCE),
/* 24 */   THANK_YOU(Material.BOOK, "谢谢!", new String[0], SelectorType.NON),
/* 25 */   TO_BASE(Material.BOOK, "回到基地!", new String[0], SelectorType.NON),
/* 26 */   TO_DEFEND(Material.IRON_FENCE, "请防守基地!", new String[0], SelectorType.NON),
/* 27 */   TO_ATTACK(Material.IRON_SWORD, "一起进攻!", new String[] { "§7你可以选择团队." }, SelectorType.TEAM),
/* 28 */   NEED_RESOURCES(Material.DIAMOND, "我们需要资源!", new String[] { "§7你将可以选择资源." }, SelectorType.RESOURCE),
/* 29 */   PLAYER_INCOMING(Material.FEATHER, "有人进攻!!", new String[0], SelectorType.NON); SelectorType selectorType;
/*    */   public Material getMaterial() {
/* 31 */     return this.material; }
/* 32 */   public String getName() { return this.name; }
/* 33 */   public String[] getDescription() { return this.description; } public SelectorType getSelectorType() {
/* 34 */     return this.selectorType;
/*    */   }
/*    */   QuickChat(Material material, String name, String[] description, SelectorType selectorType) {
/* 37 */     this.material = material;
/* 38 */     this.name = name;
/* 39 */     this.description = description;
/* 40 */     this.selectorType = selectorType;
/*    */   }
/*    */   
/*    */   public ItemStack toItemStack() {
/* 44 */     return (new ItemBuilder(this.material)).setName("§a" + this.name).setLore(this.description)
/* 45 */       .addLoreLine("").addLoreLine("§e点击以发送!").toItemStack();
/*    */   }
/*    */   
/*    */   public InventoryUI.ClickableItem showItem(final Player p) {
/* 49 */     if (this.selectorType == SelectorType.NON)
/* 50 */       return (InventoryUI.ClickableItem)new InventoryUI.AbstractClickableItem(toItemStack())
/*    */         {
/*    */           public void onClick(InventoryClickEvent e) {
/* 53 */             PlayerData pd = BedWars.getInstance().getPlayerManager().get((Player)e.getWhoClicked());
/* 54 */             for (PlayerData data : pd.getTeam().getPlayers()) {
/* 55 */               data.getPlayer().sendMessage("§a§l团队> " + 
/* 56 */                   LuckPermsUtil.getPrefix(pd.getPlayer()) + pd.getPlayer().getName() + "§f: §a" + QuickChat.this.name);
/*    */             }
/* 58 */             pd.getPlayer().closeInventory();
/*    */           }
/*    */         }; 
/* 61 */     if (this.selectorType == SelectorType.TEAM) {
/* 62 */       return (InventoryUI.ClickableItem)new InventoryUI.AbstractClickableItem(toItemStack())
/*    */         {
/*    */           public void onClick(InventoryClickEvent e) {
/* 65 */             (new SelectorTeamMenu(p, QuickChat.this.getKey())).open();
/*    */           }
/*    */         };
/*    */     }
/* 69 */     return (InventoryUI.ClickableItem)new InventoryUI.AbstractClickableItem(toItemStack())
/*    */       {
/*    */         public void onClick(InventoryClickEvent e) {
/* 72 */           (new ResourceSelectorMenu(p, QuickChat.this.getKey())).open();
/*    */         }
/*    */       };
/*    */   }
/*    */   
/*    */   public String getKey() {
/* 78 */     switch (this) {
/*    */       case ATTACKING:
/* 80 */         return "我正在进攻";
/*    */       case COLLECTING:
/* 82 */         return "我正在收集";
/*    */       case HAVE_RESOURCES:
/* 84 */         return "我有";
/*    */       case TO_ATTACK:
/* 86 */         return "一起进攻";
/*    */       case NEED_RESOURCES:
/* 88 */         return "我们需要";
/*    */     } 
/* 90 */     return "";
/*    */   }
/*    */   
/*    */   enum SelectorType
/*    */   {
/* 95 */     NON, TEAM, RESOURCE;
/*    */   }
/*    */ }