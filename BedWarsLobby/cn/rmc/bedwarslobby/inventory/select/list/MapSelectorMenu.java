/*    */ package cn.rmc.bedwarslobby.inventory.select.list;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import java.util.stream.Collectors;
/*    */ import cn.rmc.bedwarslobby.BedWarsLobby;
/*    */ import cn.rmc.bedwarslobby.inventory.MenuBasic;
/*    */ import cn.rmc.bedwarslobby.inventory.select.MapSelector;
/*    */ import cn.rmc.bedwarslobby.util.ItemBuilder;
/*    */ import cn.rmc.bedwarslobby.util.inventory.InventoryUI;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.inventory.InventoryClickEvent;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.scheduler.BukkitRunnable;
/*    */ import org.bukkit.scheduler.BukkitTask;
/*    */ 
/*    */ 
/*    */ public class MapSelectorMenu
/*    */   extends MenuBasic
/*    */ {
/*    */   BukkitTask task;
/*    */   List<String> cross;
/*    */   
/*    */   public MapSelectorMenu(Player p) {
/* 27 */     super(p, "§8玩起床4v4v4v4模式", Integer.valueOf(6));
/* 28 */     this.cross = Arrays.asList(new String[] { "►", "" });
/*    */   }
/*    */ 
/*    */   
/*    */   protected void Setup() {
/* 33 */     this
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 46 */       .task = (new BukkitRunnable() { int i = 0; public void run() { try { if (this.i == MapSelectorMenu.this.cross.size()) this.i = 0;  MapSelectorMenu.this.init(MapSelectorMenu.this.cross.get(this.i), MapSelectorMenu.this.task); MapSelectorMenu.this.player.updateInventory(); this.i++; } catch (Exception e) { cancel(); }  } }).runTaskTimer((Plugin)BedWarsLobby.getInstance(), 0L, 10L);
/*    */     
/* 48 */     this.inventoryUI.setItem(49, (InventoryUI.ClickableItem)new InventoryUI.AbstractClickableItem((new ItemBuilder(Material.ARROW)).setName("§a返回")
/* 49 */           .setLore(new String[] { "§7至游玩起床战争." }).toItemStack())
/*    */         {
/*    */           public void onClick(InventoryClickEvent e) {
/* 52 */             (new PlayMenu((Player)e.getWhoClicked())).open();
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   void init(String str, BukkitTask task) {
/* 58 */     this.inventoryUI.setItem(40, MapSelector.getRandomItem(str, task));
/*    */     
/* 60 */     List<Integer> slots = Arrays.asList(new Integer[] { Integer.valueOf(17), Integer.valueOf(18), Integer.valueOf(26), Integer.valueOf(27) });
/* 61 */     if (MapSelector.getAllAvailableRooms().isEmpty()) {
/* 62 */       this.inventoryUI.setItem(22, (InventoryUI.ClickableItem)new InventoryUI.EmptyClickableItem((new ItemBuilder(Material.BARRIER)).setName("§c无")
/* 63 */             .setLore(new String[] { "§7没有找到房间!" }).toItemStack()));
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 68 */     List<MapSelector> selectors = (List<MapSelector>)MapSelector.getAllAvailableRooms().stream().sorted((o1, o2) -> Integer.compare(o2.getAvailablePlayerCount(), o1.getAvailablePlayerCount())).collect(Collectors.toList());
/*    */     
/* 70 */     int slot = 10;
/* 71 */     for (int i = 0; i < selectors.size() && slot < 35; i++) {
/* 72 */       MapSelector selector = selectors.get(i);
/*    */       
/* 74 */       slot++;
/* 75 */       if (slots.contains(Integer.valueOf(slot)) && slot >= 35) {
/*    */         break;
/*    */       }
/* 78 */       this.inventoryUI.setItem(slot, selector.getItem(str, task));
/* 79 */       slot++;
/*    */     } 
/*    */   }
/*    */ }