/*    */ package cn.rmc.bedwars.utils;
/*    */ 
/*    */ import java.io.ByteArrayInputStream;
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.ObjectInputStream;
/*    */ import java.io.ObjectOutputStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ObjectUtil
/*    */ {
/*    */   public static byte[] obj2byte(Object obj) throws Exception {
/* 14 */     byte[] ret = null;
/* 15 */     ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 16 */     ObjectOutputStream out = new ObjectOutputStream(baos);
/* 17 */     out.writeObject(obj);
/* 18 */     out.flush();
/* 19 */     ret = baos.toByteArray();
/* 20 */     out.close();
/* 21 */     baos.close();
/* 22 */     return ret;
/*    */   }
/*    */   
/*    */   public static Object byte2obj(byte[] bytes) throws Exception {
/* 26 */     Object ret = null;
/* 27 */     ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
/* 28 */     ObjectInputStream in = new ObjectInputStream(bais);
/* 29 */     ret = in.readObject();
/* 30 */     in.close();
/* 31 */     bais.close();
/* 32 */     return ret;
/*    */   }
/*    */ }
