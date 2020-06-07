/*    */ package com.pdftron.pdf;
/*    */ 
/*    */ import com.pdftron.common.PDFNetException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class KeyStrokeActionResult
/*    */ {
/*    */   private long a;
/*    */   
/*    */   public boolean isValid() throws PDFNetException {
/* 18 */     return IsValid(this.a);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getText() throws PDFNetException {
/* 30 */     return GetText(this.a);
/*    */   }
/*    */ 
/*    */   
/*    */   public void destroy() throws PDFNetException {
/* 35 */     if (this.a != 0L) {
/*    */       
/* 37 */       Destroy(this.a);
/* 38 */       this.a = 0L;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected void finalize() throws Throwable {
/* 44 */     destroy();
/*    */   }
/*    */ 
/*    */   
/*    */   KeyStrokeActionResult(long paramLong) {
/* 49 */     this.a = paramLong;
/*    */   }
/*    */   
/*    */   static native void Destroy(long paramLong);
/*    */   
/*    */   static native boolean IsValid(long paramLong);
/*    */   
/*    */   static native String GetText(long paramLong);
/*    */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\KeyStrokeActionResult.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */