/*     */ package com.pdftron.sdf;
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
/*     */ public class ResultSnapshot
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
/*     */   public ResultSnapshot(long paramLong) {
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
/*     */   public DocSnapshot currentState() throws PDFNetException {
/*  61 */     return new DocSnapshot(CurrentState(this.a));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DocSnapshot previousState() throws PDFNetException {
/*  72 */     return new DocSnapshot(PreviousState(this.a));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOk() throws PDFNetException {
/*  83 */     return IsOk(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNullTransition() throws PDFNetException {
/*  94 */     return IsNullTransition(this.a);
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
/*     */   static native long CurrentState(long paramLong);
/*     */   
/*     */   static native long PreviousState(long paramLong);
/*     */   
/*     */   static native boolean IsOk(long paramLong);
/*     */   
/*     */   static native boolean IsNullTransition(long paramLong);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\sdf\ResultSnapshot.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */