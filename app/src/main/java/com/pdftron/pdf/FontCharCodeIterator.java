/*    */ package com.pdftron.pdf;
/*    */ 
/*    */ import com.pdftron.common.PDFNetIterator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FontCharCodeIterator
/*    */   extends PDFNetIterator<Long>
/*    */ {
/*    */   private Object a;
/*    */   
/*    */   public Long next() {
/* 14 */     return new Long(PDFNetIterator.NextD(this.impl));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object clone() {
/* 20 */     return new FontCharCodeIterator(PDFNetIterator.Clone(this.impl), this.a);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   FontCharCodeIterator(long paramLong, Object paramObject) {
/* 27 */     this.impl = paramLong;
/* 28 */     this.a = paramObject;
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\FontCharCodeIterator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */