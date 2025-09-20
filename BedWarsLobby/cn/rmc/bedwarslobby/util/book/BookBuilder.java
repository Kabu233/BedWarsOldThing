/*    */ package cn.rmc.bedwarslobby.util.book;
/*    */ 
/*    */ import com.comphenix.protocol.wrappers.BukkitConverters;
/*    */ import com.comphenix.protocol.wrappers.nbt.NbtBase;
/*    */ import com.comphenix.protocol.wrappers.nbt.NbtCompound;
/*    */ import com.comphenix.protocol.wrappers.nbt.NbtFactory;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class BookBuilder {
/*    */   ItemStack book;
/*    */   NbtCompound nbt;
/*    */   
/*    */   public BookBuilder() {
/* 15 */     this.book = new ItemStack(Material.WRITTEN_BOOK);
/* 16 */     this.book = (ItemStack)BukkitConverters.getItemStackConverter().getSpecific(this.book);
/* 17 */     this.nbt = NbtFactory.asCompound((NbtBase)NbtFactory.fromItemTag(this.book));
/*    */   }
/*    */   
/*    */   public static BookBuilder create() {
/* 21 */     return new BookBuilder();
/*    */   }
/*    */   
/*    */   public BookBuilder addPage(String page) {
/* 25 */     this.nbt.getListOrDefault("pages").add(page);
/* 26 */     return this;
/*    */   }
/*    */   
/*    */   public ItemStack getBook() {
/* 30 */     NbtFactory.setItemTag(this.book, this.nbt);
/* 31 */     return this.book;
/*    */   }
/*    */ }