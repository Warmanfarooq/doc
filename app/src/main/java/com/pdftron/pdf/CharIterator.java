/*    */ package com.pdftron.pdf;
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
/*    */ public class CharIterator
/*    */   extends PDFNetIterator<CharData>
/*    */ {
/*    */   private Object a;
/*    */   
/*    */   public CharData next() {
/* 18 */     return new CharData(PDFNetIterator.Next(this.impl), this);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object clone() {
/* 26 */     return new CharIterator(Clone(this.impl), this.a);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   CharIterator(long paramLong, Object paramObject) {
/* 37 */     this.impl = paramLong;
/* 38 */     this.a = paramObject;
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\CharIterator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */