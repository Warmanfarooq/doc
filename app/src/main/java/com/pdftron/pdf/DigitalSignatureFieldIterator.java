/*    */ package com.pdftron.pdf;
/*    */ 
/*    */ import com.pdftron.common.PDFNetException;
/*    */ import com.pdftron.common.PDFNetIterator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DigitalSignatureFieldIterator
/*    */   extends PDFNetIterator<DigitalSignatureField>
/*    */ {
/*    */   private Object a;
/*    */   
/*    */   public DigitalSignatureField next() {
/*    */     try {
/* 16 */       return new DigitalSignatureField(PDFNetIterator.Next(this.impl), this);
/*    */     }
/* 18 */     catch (PDFNetException pDFNetException) {
/*    */       
/* 20 */       return null;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Object clone() {
/* 26 */     return new DigitalSignatureFieldIterator(Clone(this.impl), this.a);
/*    */   }
/*    */ 
/*    */   
/*    */   DigitalSignatureFieldIterator(long paramLong, Object paramObject) {
/* 31 */     this.impl = paramLong;
/* 32 */     this.a = paramObject;
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\DigitalSignatureFieldIterator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */