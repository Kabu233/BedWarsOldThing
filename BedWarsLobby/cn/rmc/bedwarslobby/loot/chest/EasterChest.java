/*    */ package cn.rmc.bedwarslobby.loot.chest;
/*    */ 
/*    */ import com.comphenix.protocol.events.PacketEvent;
/*    */ import cn.rmc.bedwarslobby.loot.LootChestBasic;
/*    */ import cn.rmc.bedwarslobby.loot.LootChestType;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class EasterChest extends LootChestBasic {
/*    */   public EasterChest(Player p) {
/* 10 */     super(p, LootChestType.EASTER);
/*    */   }
/*    */   
/*    */   public void animation(boolean sound) {}
/*    */   
/*    */   public void selector() {}
/*    */   
/*    */   public void openChest() {}
/*    */   
/*    */   public void lootItem() {}
/*    */   
/*    */   public void onPacketReceive(PacketEvent event) {}
/*    */ }