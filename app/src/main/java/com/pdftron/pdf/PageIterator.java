/*    */ package com.pdftron.pdf;
/*    */ 
/*    */ import com.pdftron.common.PDFNetIterator;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PageIterator
/*    */   extends PDFNetIterator<Page>
/*    */ {
/*    */   private Object a;
/*    */   
/*    */   public Page next() {
/* 13 */     return new Page(PDFNetIterator.NextD(this.impl), this.a);
/*    */   }
/*    */ 
/*    */   
/*    */   public Object clone() {
/* 18 */     return new PageIterator(PDFNetIterator.Clone(this.impl), this.a);
/*    */   }
/*    */ 
/*    */   
/*    */   PageIterator(long paramLong, Object paramObject) {
/* 23 */     this.impl = paramLong;
/* 24 */     this.a = paramObject;
/*    */   }
/*    */ 
/*    */   
/*    */   final long a() {
/* 29 */     return this.impl;
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\PageIterator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */