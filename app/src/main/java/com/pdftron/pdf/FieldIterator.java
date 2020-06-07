/*    */ package com.pdftron.pdf;
/*    */ 
/*    */ import com.pdftron.common.PDFNetException;
/*    */ import com.pdftron.common.PDFNetIterator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FieldIterator
/*    */   extends PDFNetIterator<Field>
/*    */ {
/*    */   private Object a;
/*    */   
/*    */   public Field next() {
/*    */     try {
/* 16 */       return new Field(PDFNetIterator.Next(this.impl), this);
/*    */     }
/* 18 */     catch (PDFNetException pDFNetException) {
/*    */       
/* 20 */       return null;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Object clone() {
/* 26 */     return new FieldIterator(Clone(this.impl), this.a);
/*    */   }
/*    */ 
/*    */   
/*    */   FieldIterator(long paramLong, Object paramObject) {
/* 31 */     this.impl = paramLong;
/* 32 */     this.a = paramObject;
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\FieldIterator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */