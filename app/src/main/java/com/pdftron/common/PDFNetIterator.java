/*    */ package com.pdftron.common;
/*    */ 
/*    */ import java.util.Iterator;
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
/*    */ public abstract class PDFNetIterator<T>
/*    */   implements Cloneable, Iterator
/*    */ {
/*    */   protected long impl;
/*    */   
/*    */   public void Destroy() throws PDFNetException {
/* 20 */     Destroy(this.impl);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract T next();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract Object clone();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean hasNext() {
/* 44 */     return HasNext(this.impl);
/*    */   }
/*    */   
/*    */   public void remove() {}
/*    */   
/*    */   static native void Destroy(long paramLong);
/*    */   
/*    */   static native boolean HasNext(long paramLong);
/*    */   
/*    */   protected static native long Next(long paramLong);
/*    */   
/*    */   protected static native long NextD(long paramLong);
/*    */   
/*    */   protected static native long Clone(long paramLong);
/*    */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\common\PDFNetIterator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */