/*    */ package cn.rmc.bedwars.utils;
/*    */ 
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.block.BlockFace;
/*    */ import org.bukkit.block.Sign;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.block.CraftSign;
/*    */ 
/*    */ public class BlockUtils
/*    */ {
/*    */   public static Block getBedNeighbor(Block head) {
/* 13 */     if (isBedBlock(head.getRelative(BlockFace.EAST)))
/* 14 */       return head.getRelative(BlockFace.EAST); 
/* 15 */     if (isBedBlock(head.getRelative(BlockFace.WEST)))
/* 16 */       return head.getRelative(BlockFace.WEST); 
/* 17 */     if (isBedBlock(head.getRelative(BlockFace.SOUTH))) {
/* 18 */       return head.getRelative(BlockFace.SOUTH);
/*    */     }
/* 20 */     return head.getRelative(BlockFace.NORTH);
/*    */   }
/*    */   
/*    */   public static Location getSignNeighbor(Block block) {
/* 24 */     Location result = null;
/* 25 */     if (block.getRelative(BlockFace.EAST).getType() == Material.SIGN_POST) {
/* 26 */       result = block.getRelative(BlockFace.EAST).getLocation();
/* 27 */     } else if (block.getRelative(BlockFace.WEST).getType() == Material.SIGN_POST) {
/* 28 */       result = block.getRelative(BlockFace.WEST).getLocation();
/* 29 */     } else if (block.getRelative(BlockFace.SOUTH).getType() == Material.SIGN_POST) {
/* 30 */       result = block.getRelative(BlockFace.SOUTH).getLocation();
/* 31 */     } else if (block.getRelative(BlockFace.NORTH).getType() == Material.SIGN_POST) {
/* 32 */       result = block.getRelative(BlockFace.NORTH).getLocation();
/*    */     } 
/* 34 */     return (result == null) ? null : result.add(0.5D, 0.0D, 0.5D);
/*    */   }
/*    */   public static boolean isBedBlock(Block isBed) {
/* 37 */     if (isBed == null) {
/* 38 */       return false;
/*    */     }
/* 40 */     return (isBed.getType() == Material.BED || isBed.getType() == Material.BED_BLOCK);
/*    */   }
/*    */   
/*    */   public static Location getSignFaceLoc(Sign s) {
/* 44 */     float yaw = 0.0F;
/* 45 */     switch (((CraftSign)s).getRawData()) {
/*    */       case 0:
/* 47 */         yaw = 0.0F;
/*    */         break;
/*    */       case 1:
/* 50 */         yaw = 22.5F;
/*    */         break;
/*    */       case 2:
/* 53 */         yaw = 45.0F;
/*    */         break;
/*    */       case 3:
/* 56 */         yaw = 67.5F;
/*    */         break;
/*    */       case 4:
/* 59 */         yaw = 90.0F;
/*    */         break;
/*    */       case 5:
/* 62 */         yaw = 112.5F;
/*    */         break;
/*    */       case 6:
/* 65 */         yaw = 135.0F;
/*    */         break;
/*    */       case 7:
/* 68 */         yaw = 157.5F;
/*    */         break;
/*    */       case 8:
/* 71 */         yaw = 180.0F;
/*    */         break;
/*    */       case 9:
/* 74 */         yaw = -157.5F;
/*    */         break;
/*    */       case 10:
/* 77 */         yaw = -135.0F;
/*    */         break;
/*    */       case 11:
/* 80 */         yaw = -112.5F;
/*    */         break;
/*    */       case 12:
/* 83 */         yaw = -90.0F;
/*    */         break;
/*    */       case 13:
/* 86 */         yaw = -67.5F;
/*    */         break;
/*    */       case 14:
/* 89 */         yaw = -45.0F;
/*    */         break;
/*    */       case 15:
/* 92 */         yaw = -22.5F; break;
/*    */     } 
/* 94 */     Location loc = s.getLocation().clone();
/* 95 */     loc.add(0.5D, 0.0D, 0.5D);
/* 96 */     loc.setYaw(yaw);
/* 97 */     return loc;
/*    */   }
/*    */ }