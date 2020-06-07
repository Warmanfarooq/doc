/*    */ package com.pdftron.pdf;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CancelFlag
/*    */ {
/*  7 */   private long a = Create();
/*    */ 
/*    */   
/*    */   public void set(boolean paramBoolean) {
/* 11 */     Set(this.a, paramBoolean);
/*    */   }
/*    */   
/*    */   public void destroy() {
/* 15 */     Destroy(this.a);
/*    */   }
/*    */   
/*    */   public long __GetHandle() {
/* 19 */     return this.a;
/*    */   }
/*    */   
/*    */   static native long Create();
/*    */   
/*    */   static native void Set(long paramLong, boolean paramBoolean);
/*    */   
/*    */   static native void Destroy(long paramLong);
/*    */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\CancelFlag.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */