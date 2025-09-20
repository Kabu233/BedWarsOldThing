/*    */ package cn.rmc.bedwars.utils;
/*    */ public class Group<A, B> {
/*    */   A a;
/*    */   B b;
/*    */   
/*    */   public Group(A a, B b) {
/*  7 */     this.a = a; this.b = b;
/*    */   }
/*  9 */   public A getA() { return this.a; } public B getB() {
/* 10 */     return this.b;
/*    */   }
/*    */ }