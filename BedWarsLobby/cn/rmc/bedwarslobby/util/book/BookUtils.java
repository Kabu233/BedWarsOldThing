/*    */ package cn.rmc.bedwarslobby.util.book;
/*    */ 
/*    */ import com.comphenix.protocol.PacketType;
/*    */ import com.comphenix.protocol.ProtocolLibrary;
/*    */ import com.comphenix.protocol.events.PacketContainer;
/*    */ import com.comphenix.protocol.utility.MinecraftReflection;
/*    */ import com.comphenix.protocol.wrappers.BukkitConverters;
/*    */ import com.comphenix.protocol.wrappers.nbt.NbtBase;
/*    */ import com.comphenix.protocol.wrappers.nbt.NbtCompound;
/*    */ import com.comphenix.protocol.wrappers.nbt.NbtFactory;
/*    */ import java.util.List;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ 
/*    */ public class BookUtils
/*    */ {
/*    */   public static void openBook(Player p, ItemStack book) {
/* 20 */     int slot = p.getInventory().getHeldItemSlot();
/* 21 */     ItemStack old = p.getInventory().getItem(slot);
/* 22 */     p.getInventory().setItem(slot, book); try {
/*    */       Class<?> bfc, upc;
/* 24 */       PacketContainer pc = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.CUSTOM_PAYLOAD);
/* 25 */       pc.getModifier().writeDefaults();
/*    */       try {
/* 27 */         bfc = MinecraftReflection.class.getClassLoader().loadClass("io.netty.buffer.ByteBuf");
/* 28 */         upc = MinecraftReflection.class.getClassLoader().loadClass("io.netty.buffer.Unpooled");
/* 29 */       } catch (Throwable th) {
/* 30 */         bfc = MinecraftReflection.class.getClassLoader().loadClass("net.minecraft.util.io.netty.buffer.ByteBuf");
/* 31 */         upc = MinecraftReflection.class.getClassLoader().loadClass("net.minecraft.util.io.netty.buffer.Unpooled");
/*    */       } 
/* 33 */       Object bf = upc.getMethod("buffer", new Class[] { int.class }).invoke(null, new Object[] { Integer.valueOf(256) });
/* 34 */       bfc.getMethod("setByte", new Class[] { int.class, int.class }).invoke(bf, new Object[] { Integer.valueOf(0), Integer.valueOf(0) });
/* 35 */       bfc.getMethod("writerIndex", new Class[] { int.class }).invoke(bf, new Object[] { Integer.valueOf(1) });
/* 36 */       pc.getModifier().write(1, MinecraftReflection.getPacketDataSerializer(bf));
/* 37 */       pc.getStrings().write(0, "MC|BOpen");
/* 38 */       ProtocolLibrary.getProtocolManager().sendServerPacket(p, pc);
/* 39 */     } catch (Exception e) {
/* 40 */       e.printStackTrace();
/*    */     } 
/* 42 */     p.getInventory().setItem(slot, old);
/*    */   }
/*    */   
/*    */   public static void openBook(Player p, List<String> pages) {
/* 46 */     ItemStack book = (ItemStack)BukkitConverters.getItemStackConverter().getSpecific(new ItemStack(Material.WRITTEN_BOOK));
/* 47 */     NbtCompound nbt = NbtFactory.asCompound((NbtBase)NbtFactory.fromItemTag(book));
/* 48 */     for (int i = 0; i < pages.size(); i++) {
/* 49 */       nbt.getListOrDefault("pages").add(pages.get(i));
/*    */     }
/* 51 */     NbtFactory.setItemTag(book, nbt);
/* 52 */     openBook(p, book);
/*    */   }
/*    */ }