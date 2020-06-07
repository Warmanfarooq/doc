/*    */ package com.pdftron.sdf;
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
/*    */ public class DocSnapshot
/*    */ {
/*    */   private long a;
/*    */   
/*    */   public void destroy() throws PDFNetException {
/* 30 */     if (this.a != 0L) {
/*    */       
/* 32 */       Destroy(this.a);
/* 33 */       this.a = 0L;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void finalize() throws Throwable {
/* 42 */     destroy();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DocSnapshot(long paramLong) {
/* 50 */     this.a = paramLong;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getHash() throws PDFNetException {
/* 61 */     return GetHash(this.a);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isValid() throws PDFNetException {
/* 72 */     return IsValid(this.a);
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
/*    */   public boolean equals(DocSnapshot paramDocSnapshot) throws PDFNetException {
/* 84 */     return Equals(this.a, paramDocSnapshot.__GetHandle());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long __GetHandle() {
/* 91 */     return this.a;
/*    */   }
/*    */   
/*    */   static native void Destroy(long paramLong);
/*    */   
/*    */   static native int GetHash(long paramLong);
/*    */   
/*    */   static native boolean IsValid(long paramLong);
/*    */   
/*    */   static native boolean Equals(long paramLong1, long paramLong2);
/*    */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\sdf\DocSnapshot.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */