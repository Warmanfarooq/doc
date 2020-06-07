/*    */ package com.pdftron.pdf;
/*    */ 
/*    */ import com.pdftron.common.PDFNetIterator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GSChangesIterator
/*    */   extends PDFNetIterator<Integer>
/*    */ {
/*    */   private Object a;
/*    */   
/*    */   public Integer next() {
/* 14 */     return new Integer((int)PDFNetIterator.NextD(this.impl));
/*    */   }
/*    */ 
/*    */   
/*    */   public Object clone() {
/* 19 */     return new GSChangesIterator(PDFNetIterator.Clone(this.impl), this.a);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   GSChangesIterator(long paramLong, Object paramObject) {
/* 28 */     this.impl = paramLong;
/* 29 */     this.a = paramObject;
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\GSChangesIterator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */