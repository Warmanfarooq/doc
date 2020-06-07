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
/*    */ public class KeyStrokeEventData
/*    */ {
/*    */   long a;
/*    */   
/*    */   public KeyStrokeEventData(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2) throws PDFNetException {
/* 28 */     this.a = KeyStrokeEventDataCreate(paramString1, paramString2, paramString3, paramInt1, paramInt2);
/*    */   }
/*    */ 
/*    */   
/*    */   public void destroy() throws PDFNetException {
/* 33 */     if (this.a != 0L) {
/*    */       
/* 35 */       Destroy(this.a);
/* 36 */       this.a = 0L;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected void finalize() throws Throwable {
/* 42 */     destroy();
/*    */   }
/*    */   
/*    */   static native void Destroy(long paramLong);
/*    */   
/*    */   static native long KeyStrokeEventDataCreate(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2);
/*    */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\KeyStrokeEventData.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */