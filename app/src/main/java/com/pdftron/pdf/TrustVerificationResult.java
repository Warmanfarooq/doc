/*     */ package com.pdftron.pdf;
/*     */ 
/*     */ import com.pdftron.common.PDFNetException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TrustVerificationResult
/*     */ {
/*     */   private long a;
/*     */   
/*     */   public void destroy() throws PDFNetException {
/*  30 */     if (this.a != 0L) {
/*     */       
/*  32 */       Destroy(this.a);
/*  33 */       this.a = 0L;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finalize() throws Throwable {
/*  42 */     destroy();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TrustVerificationResult(long paramLong) {
/*  50 */     this.a = paramLong;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean wasSuccessful() throws PDFNetException {
/*  61 */     return WasSuccessful(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getResultString() throws PDFNetException {
/*  72 */     return GetResultString(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTimeOfTrustVerification() throws PDFNetException {
/*  83 */     return GetTimeOfTrustVerification(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VerificationOptions.TimeMode getTimeOfTrustVerificationEnum() throws PDFNetException {
/*  94 */     return VerificationOptions.TimeMode.a(GetTimeOfTrustVerificationEnum(this.a));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long __GetHandle() {
/* 101 */     return this.a;
/*     */   }
/*     */   
/*     */   static native void Destroy(long paramLong);
/*     */   
/*     */   static native boolean WasSuccessful(long paramLong);
/*     */   
/*     */   static native String GetResultString(long paramLong);
/*     */   
/*     */   static native long GetTimeOfTrustVerification(long paramLong);
/*     */   
/*     */   static native int GetTimeOfTrustVerificationEnum(long paramLong);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\TrustVerificationResult.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */