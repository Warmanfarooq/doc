/*    */ package com.pdftron.fdf;
/*    */ 
/*    */ import com.pdftron.common.PDFNetIterator;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FDFFieldIterator
/*    */   extends PDFNetIterator<FDFField>
/*    */ {
/*    */   private Object a;
/*    */   
/*    */   public FDFField next() {
/* 25 */     return new FDFField(Next(this.impl), this);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object clone() {
/* 33 */     return new FDFFieldIterator(Clone(this.impl), this.a);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   FDFFieldIterator(long paramLong, Object paramObject) {
/* 44 */     this.impl = paramLong;
/* 45 */     this.a = paramObject;
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\fdf\FDFFieldIterator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */