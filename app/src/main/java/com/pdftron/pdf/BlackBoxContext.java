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
/*    */ 
/*    */ 
/*    */ public class BlackBoxContext
/*    */ {
/*    */   public static final int e_success = 0;
/*    */   public static final int e_incomplete = 1;
/*    */   public static final int e_failure = 2;
/*    */   private long a;
/*    */   
/*    */   public synchronized void destroy() throws PDFNetException {
/* 33 */     if (this.a != 0L) {
/*    */       
/* 35 */       Destroy(this.a);
/* 36 */       this.a = 0L;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void destroyImpl() throws PDFNetException {
/* 45 */     destroy();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void finalize() throws Throwable {
/* 52 */     destroy();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BlackBoxContext(long paramLong) {
/* 60 */     this.a = paramLong;
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
/*    */   public String doOperation(String paramString) throws PDFNetException {
/* 72 */     return DoOperation(this.a, paramString);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PDFDoc getDoc() throws PDFNetException {
/* 83 */     return PDFDoc.__Create(GetDoc(this.a));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long __GetHandle() {
/* 90 */     return this.a;
/*    */   }
/*    */   
/*    */   static native void Destroy(long paramLong);
/*    */   
/*    */   static native String DoOperation(long paramLong, String paramString);
/*    */   
/*    */   static native long GetDoc(long paramLong);
/*    */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\BlackBoxContext.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */