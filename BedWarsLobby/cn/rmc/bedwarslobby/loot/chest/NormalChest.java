/*     */ package cn.rmc.bedwarslobby.loot.chest;
/*     */ 
/*     */ import com.comphenix.protocol.PacketType;
/*     */ import com.comphenix.protocol.ProtocolLibrary;
/*     */ import com.comphenix.protocol.events.PacketContainer;
/*     */ import com.comphenix.protocol.events.PacketEvent;
/*     */ import com.comphenix.protocol.wrappers.Vector3F;
/*     */ import com.comphenix.protocol.wrappers.WrappedDataWatcher;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import java.util.concurrent.ThreadLocalRandom;
/*     */ import cn.rmc.bedwarslobby.BedWarsLobby;
/*     */ import cn.rmc.bedwarslobby.enums.Data;
/*     */ import cn.rmc.bedwarslobby.loot.LootChestBasic;
/*     */ import cn.rmc.bedwarslobby.loot.LootChestType;
/*     */ import cn.rmc.bedwarslobby.loot.enums.Head;
/*     */ import cn.rmc.bedwarslobby.loot.enums.Rarity;
/*     */ import cn.rmc.bedwarslobby.loot.enums.ShopSkin;
/*     */ import cn.rmc.bedwarslobby.loot.other.LootInfo;
/*     */ import cn.rmc.bedwarslobby.loot.utils.CameraUtils;
/*     */ import cn.rmc.bedwarslobby.object.PlayerData;
/*     */ import cn.rmc.bedwarslobby.util.ActionBarUtils;
/*     */ import cn.rmc.bedwarslobby.util.DataUtils;
/*     */ import cn.rmc.bedwarslobby.util.EffectUtils;
/*     */ import cn.rmc.bedwarslobby.util.ItemUtil;
/*     */ import cn.rmc.bedwarslobby.util.MathUtils;
/*     */ import cn.rmc.bedwarslobby.util.TitleUtils;
/*     */ import cn.rmc.bedwarslobby.util.holographic.armor.ArmorHolographic;
/*     */ import cn.rmc.bedwarslobby.util.holographic.armor.ItemHolographicLine;
/*     */ import org.apache.commons.lang3.RandomUtils;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ 
/*     */ public class NormalChest
/*     */   extends LootChestBasic
/*     */ {
/*  45 */   List<ArmorHolographic> selectors = new ArrayList<>();
/*  46 */   ArmorHolographic swordBackup = null;
/*  47 */   ArmorHolographic chestBackup = null;
/*  48 */   List<ArmorHolographic> itemLoots = new ArrayList<>();
/*  49 */   Player player = getPd().getPlayer();
/*     */   
/*     */   public NormalChest(Player p) {
/*  52 */     super(p, LootChestType.NORMAL);
/*     */   }
/*     */   
/*     */   public void animation(final boolean sound) {
/*  56 */     final Location location = orgLocation.clone().add(3.75D, 0.0D, 0.0D);
/*  57 */     final ArmorHolographic sword = new ArmorHolographic(new Location(location.getWorld(), location.getX() + 0.346D, location.getY() + 1.3D + 10.0D, location.getZ() + 0.518D), ItemHolographicLine.ShowType.ANI_HAND);
/*  58 */     sword.setItem(new ItemStack(Material.DIAMOND_SWORD));
/*  59 */     this.swordBackup = sword;
/*  60 */     final ArmorHolographic chest = new ArmorHolographic(new Location(location.getWorld(), location.getX(), location.getY() + 10.0D, location.getZ()), ItemHolographicLine.ShowType.ANI_HEAD);
/*  61 */     chest.setItem(ItemUtil.getCustomSkull(Head.LootChest.getBase64()));
/*  62 */     this.chestBackup = chest;
/*  63 */     createHolographic(sword);
/*  64 */     createHolographic(chest);
/*  65 */     if (sound) {
/*  66 */       getPd().getPlayer().playSound(location, Sound.PISTON_EXTEND, 1.0F, 0.0F);
/*     */     }
/*     */     
/*  69 */     (new BukkitRunnable() {
/*  70 */         int i = 0;
/*     */         
/*     */         public void run() {
/*  73 */           this.i++;
/*  74 */           if (!NormalChest.this.player.isOnline()) {
/*  75 */             cancel();
/*     */           } else {
/*  77 */             if (this.i == 32) {
/*  78 */               cancel();
/*  79 */               if (NormalChest.this.player.isOnline() && LootChestBasic.openPlayers.contains(NormalChest.this.player)) {
/*  80 */                 NormalChest.this.player.playSound(location, Sound.ZOMBIE_METAL, 3.0F, 0.0F);
/*  81 */                 EffectUtils.cloud(NormalChest.this.player, location);
/*  82 */                 if (sound) {
/*  83 */                   NormalChest.this.selector();
/*     */                 }
/*     */               } 
/*     */             } 
/*     */             
/*  88 */             NormalChest.this.relativeMove(chest.getItem().getEntityId(NormalChest.this.player), 0.0D, -0.3D, 0.0D, false, NormalChest.this.player);
/*  89 */             NormalChest.this.relativeMove(sword.getItem().getEntityId(NormalChest.this.player), 0.0D, -0.3D, 0.0D, false, NormalChest.this.player);
/*     */           } 
/*     */         }
/*  92 */       }).runTaskTimer((Plugin)BedWarsLobby.getInstance(), 0L, 1L);
/*     */   }
/*     */   
/*     */   public void selector() {
/*  96 */     final Location loc = orgLocation.clone().add(3.75D, 0.0D, 0.0D);
/*  97 */     (new BukkitRunnable()
/*     */       {
/*     */         public void run() {
/* 100 */           Location select = loc.clone().add(-1.35D, 1.0D, -0.55D);
/* 101 */           final Integer amount = NormalChest.this.getPd().getLootChest();
/* 102 */           final ArmorHolographic selectorArmor = new ArmorHolographic(new Location(select.getWorld(), (int)select.getX(), (int)select.getY(), (int)select.getZ()), ItemHolographicLine.ShowType.SELECTOR_CHEST);
/* 103 */           selectorArmor.setItem(ItemUtil.getCustomSkull(Head.LootChest.getBase64()));
/* 104 */           selectorArmor.addDownLine("§l打开起床奖励箱");
/* 105 */           selectorArmor.addDownLine((amount.intValue() == 0) ? "§c§l没箱子了!" : ("§e§l剩余" + MathUtils.Format(amount) + "个!"));
/* 106 */           selectorArmor.addLookingTask(NormalChest.this.player, "§e点击打开!", (Sound)null);
/* 107 */           selectorArmor.addClickHandler(new ArmorHolographic.ClickHandler() {
/*     */                 public void onClick(Player p, int entityId) {
/* 109 */                   if (NormalChest.this.player.equals(p) && selectorArmor.isSame(entityId, NormalChest.this.player) && selectorArmor.isLooking(NormalChest.this.player)) {
/* 110 */                     if (amount.intValue() != 0) {
/* 111 */                       NormalChest.this.openChest();
/*     */                     } else {
/* 113 */                       NormalChest.this.player.sendMessage("§c你没有任何箱子可以打开, 请前往商店购买!");
/* 114 */                       NormalChest.this.Close(NormalChest.this.player);
/*     */                     } 
/*     */                   }
/*     */                 }
/*     */               });
/*     */ 
/*     */           
/* 121 */           Location shop = loc.clone().add(-1.35D, 1.0D, 1.55D);
/* 122 */           final ArmorHolographic shopArmor = new ArmorHolographic(new Location(shop.getWorld(), (int)shop.getX(), (int)shop.getY(), (int)shop.getZ()), ItemHolographicLine.ShowType.SELECTOR_SHOP);
/* 123 */           shopArmor.addDownLine("§6§l商店");
/* 124 */           shopArmor.addLookingTaskByShop(NormalChest.this.player, (String)null, Sound.LEVEL_UP);
/* 125 */           shopArmor.addClickHandler(new ArmorHolographic.ClickHandler() {
/*     */                 public void onClick(Player p, int entityId) {
/* 127 */                   if (NormalChest.this.player.equals(p) && shopArmor.isSame(entityId, NormalChest.this.player) && shopArmor.isLooking(NormalChest.this.player)) {
/* 128 */                     ShopSkin.openBook(NormalChest.this.player);
/*     */                   }
/*     */                 }
/*     */               });
/*     */           
/* 133 */           Location close_left = LootChestBasic.orgLocation.clone().add(-0.55D, 1.149999976158142D, -2.0D);
/* 134 */           final ArmorHolographic leftCloseArmor = new ArmorHolographic(new Location(close_left.getWorld(), (int)close_left.getX(), (int)close_left.getY(), (int)close_left.getZ()), ItemHolographicLine.ShowType.SELECTOR_CLOSE_LEFT);
/* 135 */           leftCloseArmor.setItem(ItemUtil.getCustomSkull(Head.Close.getBase64()));
/* 136 */           leftCloseArmor.addDownLine("§c§l关闭");
/* 137 */           leftCloseArmor.addLookingTask(NormalChest.this.player, (String)null, Sound.VILLAGER_NO);
/* 138 */           leftCloseArmor.addClickHandler(new ArmorHolographic.ClickHandler() {
/*     */                 public void onClick(Player p, int entityId) {
/* 140 */                   if (NormalChest.this.player.equals(p) && leftCloseArmor.isSame(entityId, NormalChest.this.player) && leftCloseArmor.isLooking(NormalChest.this.player)) {
/* 141 */                     NormalChest.this.Close(NormalChest.this.player);
/*     */                   }
/*     */                 }
/*     */               });
/*     */           
/* 146 */           Location close_right = LootChestBasic.orgLocation.clone().add(-0.55D, 1.149999976158142D, 3.0D);
/* 147 */           final ArmorHolographic rightCloseArmor = new ArmorHolographic(new Location(close_right.getWorld(), (int)close_right.getX(), (int)close_right.getY(), (int)close_right.getZ()), ItemHolographicLine.ShowType.SELECTOR_CLOSE_RIGHT);
/* 148 */           rightCloseArmor.setItem(ItemUtil.getCustomSkull(Head.Close.getBase64()));
/* 149 */           rightCloseArmor.addDownLine("§c§l关闭");
/* 150 */           rightCloseArmor.addLookingTask(NormalChest.this.player, (String)null, Sound.VILLAGER_NO);
/* 151 */           rightCloseArmor.addClickHandler(new ArmorHolographic.ClickHandler() {
/*     */                 public void onClick(Player p, int entityId) {
/* 153 */                   if (NormalChest.this.player.equals(p) && rightCloseArmor.isSame(entityId, NormalChest.this.player) && rightCloseArmor.isLooking(NormalChest.this.player)) {
/* 154 */                     NormalChest.this.Close(NormalChest.this.player);
/*     */                   }
/*     */                 }
/*     */               });
/*     */           
/* 159 */           Arrays.<ArmorHolographic>asList(new ArmorHolographic[] { selectorArmor, shopArmor, leftCloseArmor, rightCloseArmor }).forEach(armorHolographic -> {
/*     */                 NormalChest.this.createHolographic(armorHolographic);
/*     */                 NormalChest.this.selectors.add(armorHolographic);
/*     */               });
/* 163 */           CameraUtils.quitCamera(NormalChest.this.player);
/* 164 */           NormalChest.this.player.sendMessage(new String[] { "", "          §a§l选择一项操作§7或§c§l按Shift取消", "" });
/*     */         }
/* 166 */       }).runTaskLater((Plugin)BedWarsLobby.getInstance(), 20L);
/*     */   }
/*     */   
/*     */   public void openChest() {
/* 170 */     this.selectors.forEach(ArmorHolographic::remove);
/* 171 */     if (!this.itemLoots.isEmpty()) {
/* 172 */       this.itemLoots.forEach(ArmorHolographic::remove);
/* 173 */       this.itemLoots = new ArrayList<>();
/*     */     } 
/*     */     
/* 176 */     CameraUtils.players.add(this.player);
/* 177 */     CameraUtils.sit.set(false);
/* 178 */     ActionBarUtils.sendActionBar(this.player, "");
/* 179 */     final Location location = orgLocation.clone().add(3.75D, 0.0D, 0.0D);
/* 180 */     this.player.playSound(location, Sound.PISTON_RETRACT, 1.0F, 0.0F);
/* 181 */     relativeMove(this.swordBackup.getItem().getEntityId(this.player), 0.0D, 0.45D, 0.0D, false, this.player);
/* 182 */     (new BukkitRunnable() {
/* 183 */         int i = 0;
/*     */         
/*     */         public void run() {
/* 186 */           if (this.i > 25) {
/* 187 */             EffectUtils.hugeBoom(NormalChest.this.player, location);
/* 188 */             NormalChest.this.player.playSound(location, Sound.EXPLODE, 1.0F, 1.0F);
/* 189 */             NormalChest.this.swordBackup.remove();
/* 190 */             NormalChest.this.chestBackup.remove();
/* 191 */             NormalChest.this.lootItem();
/* 192 */             NormalChest.this.getPd().setLootChest(Integer.valueOf(NormalChest.this.getPd().getLootChest().intValue() - 1));
/* 193 */             NormalChest.this.getPd().save();
/* 194 */             cancel();
/*     */           } else {
/* 196 */             NormalChest.this.headPose(NormalChest.this.chestBackup.getItem().getEntityId(NormalChest.this.player), new Vector3F(NormalChest.this.randomFloat(RandomUtils.nextFloat(1.0F, 2.5F)), -45.0F, NormalChest.this.randomFloat(RandomUtils.nextFloat(1.0F, 2.5F))), NormalChest.this.player);
/* 197 */             EffectUtils.cloud(NormalChest.this.player, location);
/* 198 */             EffectUtils.enchantmentTable(NormalChest.this.player, location);
/* 199 */             NormalChest.this.player.playSound(NormalChest.this.player.getLocation(), Sound.DIG_GRASS, 0.8F, 0.3F);
/* 200 */             this.i++;
/*     */           } 
/*     */         }
/* 203 */       }).runTaskTimer((Plugin)BedWarsLobby.getInstance(), 20L, 3L);
/*     */   }
/*     */   
/*     */   public void lootItem() {
/* 207 */     Location loc = orgLocation.clone().add(3.5D, 0.0D, 0.0D);
/* 208 */     Location trueLoc = loc.clone().add(-0.75D, 2.15D, 0.0D);
/* 209 */     List<LootInfo> loots = getLoots();
/* 210 */     Location leftLoc = trueLoc.clone().add(-0.3D, 0.5D, -1.75D);
/* 211 */     ArmorHolographic leftItemLoot = new ArmorHolographic(new Location(leftLoc.getWorld(), leftLoc.getX(), leftLoc.getY(), leftLoc.getZ()), ItemHolographicLine.ShowType.LOOT_HEAD_LEFT);
/* 212 */     LootInfo loot$1 = loots.get(ThreadLocalRandom.current().nextInt(loots.size()));
/* 213 */     leftItemLoot.setItem(loot$1.getItemStack());
/* 214 */     leftItemLoot.addDownLine(loot$1.getRarity().toColor() + loot$1.getName());
/* 215 */     leftItemLoot.addDownLine(loot$1.getRarity().toColor() + "§l" + loot$1.getType());
/* 216 */     leftItemLoot.addLookingTask(this.player, "§e点击装备!", Sound.ITEM_PICKUP);
/* 217 */     leftItemLoot.addClickHandler((p, entityId) -> {
/*     */           if (this.player.equals(p) && leftItemLoot.isSame(entityId, this.player) && leftItemLoot.isLooking(this.player)) {
/*     */             this.player.sendMessage("§a已成功选择§6" + ChatColor.stripColor(loot$1.getName()) + "§a为你的" + ChatColor.stripColor(loot$1.getType()) + "!");
/*     */           }
/*     */         });
/*     */     
/* 223 */     Location midLoc = trueLoc.clone().add(0.0D, 0.5D, 0.0D);
/* 224 */     ArmorHolographic midItemLoot = new ArmorHolographic(new Location(midLoc.getWorld(), midLoc.getX(), midLoc.getY(), midLoc.getZ()), ItemHolographicLine.ShowType.LOOT_HEAD_MIDDLE);
/* 225 */     LootInfo loot$2 = loots.get(ThreadLocalRandom.current().nextInt(loots.size()));
/* 226 */     midItemLoot.setItem(loot$2.getItemStack());
/* 227 */     midItemLoot.addDownLine(loot$2.getRarity().toColor() + loot$2.getName());
/* 228 */     midItemLoot.addDownLine(loot$2.getRarity().toColor() + "§l" + loot$2.getType());
/* 229 */     midItemLoot.addLookingTask(this.player, "§e点击装备!", Sound.ITEM_PICKUP);
/* 230 */     midItemLoot.addClickHandler((p, entityId) -> {
/*     */           if (this.player.equals(p) && midItemLoot.isSame(entityId, this.player) && midItemLoot.isLooking(this.player)) {
/*     */             this.player.sendMessage("§a已成功选择§6" + ChatColor.stripColor(loot$2.getName()) + "§a为你的" + ChatColor.stripColor(loot$2.getType()) + "!");
/*     */           }
/*     */         });
/*     */     
/* 236 */     Location rightLoc = trueLoc.clone().add(-0.3D, 0.5D, 1.75D);
/* 237 */     ArmorHolographic rightItemLoot = new ArmorHolographic(new Location(rightLoc.getWorld(), rightLoc.getX(), rightLoc.getY(), rightLoc.getZ()), ItemHolographicLine.ShowType.LOOT_HEAD_RIGHT);
/* 238 */     LootInfo loot$3 = loots.get(ThreadLocalRandom.current().nextInt(loots.size()));
/* 239 */     rightItemLoot.setItem(loot$3.getItemStack());
/* 240 */     rightItemLoot.addDownLine(loot$3.getRarity().toColor() + loot$3.getName());
/* 241 */     rightItemLoot.addDownLine(loot$3.getRarity().toColor() + "§l" + loot$3.getType());
/* 242 */     rightItemLoot.addLookingTask(this.player, "§e点击装备!", Sound.ITEM_PICKUP);
/* 243 */     rightItemLoot.addClickHandler((p, entityId) -> {
/*     */           if (this.player.equals(p) && rightItemLoot.isSame(entityId, this.player) && rightItemLoot.isLooking(this.player)) {
/*     */             this.player.sendMessage("§a已成功选择§6" + ChatColor.stripColor(loot$3.getName()) + "§a为你的" + ChatColor.stripColor(loot$3.getType()) + "!");
/*     */           }
/*     */         });
/*     */     
/* 249 */     Arrays.<ArmorHolographic>asList(new ArmorHolographic[] { leftItemLoot, midItemLoot, rightItemLoot }).forEach(armorHolographic -> {
/*     */           createHolographic(armorHolographic);
/*     */           this.itemLoots.add(armorHolographic);
/*     */         });
/* 253 */     Arrays.<LootInfo>asList(new LootInfo[] { loot$1, loot$2, loot$3 }).forEach(lootInfo -> {
/*     */           int coins = 500;
/*     */           if (lootInfo.getRarity() == Rarity.LEGENDARY) {
/*     */             sendLegendsToPlayer(this.player, lootInfo.getAllName());
/*     */           }
/*     */           switch (lootInfo.getRarity()) {
/*     */             case RARE:
/*     */               coins *= 2;
/*     */               break;
/*     */             
/*     */             case EPIC:
/*     */               coins *= 3;
/*     */               break;
/*     */             case LEGENDARY:
/*     */               coins *= 4;
/*     */               break;
/*     */           } 
/*     */           this.player.sendMessage("§7你在起床奖励箱中找到了 " + lootInfo.getChestName() + "§7!");
/*     */           PlayerData pd = BedWarsLobby.getInstance().getPlayerManager().get(this.player);
/*     */           if (pd.hasBought(lootInfo)) {
/*     */             DataUtils.addInt(this.player.getUniqueId().toString(), Data.Field.COIN, Integer.valueOf(coins));
/*     */             this.player.sendMessage("§7因为你已拥有了他, 所以你获得了§e§l" + String.format("%,d", new Object[] { Integer.valueOf(coins) }) + "硬币§7!");
/*     */           } else {
/*     */             pd.getBoughtList().add(lootInfo);
/*     */           } 
/*     */         });
/* 279 */     animation(false);
/* 280 */     selector();
/*     */   }
/*     */   
/*     */   public void onPacketReceive(PacketEvent event) {
/* 284 */     if (event.getPacket() != null) {
/* 285 */       Player player = event.getPlayer();
/* 286 */       if (event.getPacket().getType() == PacketType.Play.Client.USE_ENTITY) {
/* 287 */         Integer entityId = (Integer)event.getPacket().getIntegers().readSafely(0);
/* 288 */         if (entityId == null) {
/*     */           return;
/*     */         }
/*     */         
/* 292 */         if (entityId.intValue() == player.getEntityId()) {
/* 293 */           event.setCancelled(true);
/*     */         }
/*     */         
/* 296 */         if (CameraUtils.players.contains(player)) {
/*     */           return;
/*     */         }
/*     */         
/* 300 */         getArmorHolographics().forEach(armorHolographic -> {
/*     */               if (armorHolographic.getClickHandler() != null) {
/*     */                 armorHolographic.getClickHandler().onClick(event.getPlayer(), entityId.intValue());
/*     */               }
/*     */             });
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void relativeMove(int entityId, double x, double y, double z, boolean onGround, Player player) {
/*     */     try {
/* 313 */       if (x != 0.0D || y != 0.0D || z != 0.0D || onGround) {
/* 314 */         PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.REL_ENTITY_MOVE);
/* 315 */         packet.getIntegers().write(0, Integer.valueOf(entityId));
/* 316 */         packet.getBytes().write(0, Byte.valueOf((byte)getPacketLoc(x)));
/* 317 */         packet.getBytes().write(1, Byte.valueOf((byte)getPacketLoc(y)));
/* 318 */         packet.getBytes().write(2, Byte.valueOf((byte)getPacketLoc(z)));
/* 319 */         packet.getBooleans().write(0, Boolean.valueOf(onGround));
/* 320 */         ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
/*     */       } 
/*     */     } catch (Throwable $ex) {
/*     */       throw $ex;
/*     */     } 
/*     */   } void headPose(int entityId, Vector3F vector, Player player) { try {
/* 326 */       PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.ENTITY_METADATA);
/* 327 */       packet.getIntegers().write(0, Integer.valueOf(entityId));
/* 328 */       WrappedDataWatcher watcher = new WrappedDataWatcher();
/* 329 */       watcher.setObject(11, vector);
/* 330 */       packet.getWatchableCollectionModifier().write(0, watcher.getWatchableObjects());
/* 331 */       ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
/*     */     } catch (Throwable $ex) {
/*     */       throw $ex;
/*     */     }  } private static int getPacketLoc(double loc) {
/* 335 */     return (int)Math.floor(loc * 32.0D);
/*     */   }
/*     */   
/*     */   public float randomFloat(float f) {
/* 339 */     return f * ((new Random()).nextBoolean() ? -1 : true);
/*     */   }
/*     */   
/*     */   List<LootInfo> getLoots() {
/* 343 */     return Arrays.asList(new LootInfo[] { new LootInfo(ItemUtil.getCustomSkull("eyJ0aW1lc3RhbXAiOjE0OTc4Nzc1MzQ1NzcsInByb2ZpbGVJZCI6ImRiYTJmMTE1NmNjMjQwMWJhOWU5YjRkMjdmN2M4OTdkIiwicHJvZmlsZU5hbWUiOiJjb2RlbmFtZV9CIiwic2lnbmF0dXJlUmVxdWlyZWQiOnRydWUsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8yMzM4Nzg2Njk3M2Y2YmRjOTlhOGRlOTk4NTFmZTIwYmJmNmM4NmU3ZGU2Y2NkM2U0NDI5NTlkNzVkNjVmMWUifX19"), "魔法供应师", "店主皮肤", Rarity.COMMON), new LootInfo(ItemUtil.getCustomSkull("eyJ0aW1lc3RhbXAiOjE1Nzg5MzkxODg5MjAsInByb2ZpbGVJZCI6IjE5MjUyMWI0ZWZkYjQyNWM4OTMxZjAyYTg0OTZlMTFiIiwicHJvZmlsZU5hbWUiOiJTZXJpYWxpemFibGUiLCJzaWduYXR1cmVSZXF1aXJlZCI6dHJ1ZSwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzQ1MTNlYzdjYTk4ZDMyOGExYzlmMTc3OTdiNTZmODQxYjg0MWMyYzE4NmIyYzM2Y2VkNGU5NGM1MzM5NGMwNzgifX19"), "星光", "店主皮肤", Rarity.COMMON), new LootInfo(ItemUtil.getCustomSkull("eyJ0aW1lc3RhbXAiOjE1MTYxNTk3NzIyMDEsInByb2ZpbGVJZCI6IjcwOTU2NDU0NTJkOTRiYTI5YzcwZDFmYTY3YjhkYTQyIiwicHJvZmlsZU5hbWUiOiJIaWRkdXMiLCJzaWduYXR1cmVSZXF1aXJlZCI6dHJ1ZSwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2QzNzNkZWRjYTM5NjQ4ZGE4ZWQ3NmViYzdmYTY0YzQwY2Y2M2UzNjBlZWNkYzYzMTYyOTIyZTczYjFmNTM5NiJ9fX0="), "南陈佳人", "店主皮肤", Rarity.COMMON), new LootInfo(ItemUtil.getCustomSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzA4MDgyODRiYTUwZWMwODhkY2Y2ZDllNTU0OWU5MWY4MjU4NWYxYzI4Mjk5YzQwNTkzY2M4YWFmMmRiNTAifX19"), "灯笼", "粒子效果图像", Rarity.COMMON), new LootInfo(ItemUtil.getCustomSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTBlNGM2NzBjOWIyYzE5YTQ4YTkyMzExYThkN2Y4MmEzOTY1YmMyOTdhMjIwYzg5ZTE2NjgyNTQxN2U4In19fQ=="), "TNT", "粒子效果图像", Rarity.COMMON), new LootInfo(ItemUtil.getCustomSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDFmZTI3YTEzYzVmYzE3NTE1Y2FlNjk1ODUyNzE2MzI2YjJiNWRmNDdkOGQ2Yjk1YTc4OWFlMzhjYWM3YjEifX19"), "光谱", "粒子效果图像", Rarity.COMMON), new LootInfo(ItemUtil.getCustomSkull("eyJ0aW1lc3RhbXAiOjE1MDc0ODc1OTk5NTMsInByb2ZpbGVJZCI6IjQxZDNhYmMyZDc0OTQwMGM5MDkwZDU0MzRkMDM4MzFiIiwicHJvZmlsZU5hbWUiOiJNZWdha2xvb24iLCJzaWduYXR1cmVSZXF1aXJlZCI6dHJ1ZSwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzY2Y2ZhYjFkMmY3NjNmMzMzYjVlMWJkY2QxYWQxNjJjN2YxMmExYWMyMjI0NDYzYTE0NDY0M2VjMjNmOTgifX19"), "蝙蝠十字架", "最终击杀效果", Rarity.LEGENDARY), new LootInfo(ItemUtil.getCustomSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzU0MTlmY2U1MDZhNDk1MzQzYTFkMzY4YTcxZDIyNDEzZjA4YzZkNjdjYjk1MWQ2NTZjZDAzZjgwYjRkM2QzIn19fQ=="), "礼盒爆炸", "最终击杀效果", Rarity.EPIC), new LootInfo(new ItemStack(Material.ANVIL), "铁砧", "最终击杀效果", Rarity.COMMON) });
/*     */   }
/*     */   
/*     */   private void sendLegendsToPlayer(final Player p, final String info) {
/* 347 */     final List<String> titles = Arrays.asList(new String[] { "§6§l传奇!", "§6§l传奇!", "§e§l传奇!", "§6§l传奇!", "§e§l传奇!", "§6§l传奇!", "§6§l传奇!" });
/* 348 */     p.playSound(p.getLocation(), Sound.ENDERDRAGON_GROWL, 1.0F, 1.0F);
/* 349 */     (new BukkitRunnable() {
/* 350 */         int i = 0;
/*     */         
/*     */         public void run() {
/* 353 */           if (this.i == titles.size()) {
/* 354 */             cancel();
/*     */           } else {
/* 356 */             TitleUtils.sendFullTitle(p, Integer.valueOf(0), Integer.valueOf(60), Integer.valueOf(0), titles.get(this.i), "§f" + info);
/* 357 */             this.i++;
/*     */           } 
/*     */         }
/* 360 */       }).runTaskTimer((Plugin)BedWarsLobby.getInstance(), 0L, 5L);
/*     */   }
/*     */ }