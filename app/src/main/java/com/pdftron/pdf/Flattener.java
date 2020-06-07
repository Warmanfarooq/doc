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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Flattener
/*     */ {
/*     */   public static final int e_very_strict = 0;
/*     */   public static final int e_strict = 1;
/*     */   public static final int e_default = 2;
/*     */   public static final int e_keep_most = 3;
/*     */   public static final int e_keep_all = 4;
/*     */   public static final int e_simple = 0;
/*     */   public static final int e_fast = 1;
/*  41 */   private long a = FlattenerCreate();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDPI(int paramInt) throws PDFNetException {
/*  52 */     SetDPI(this.a, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaximumImagePixels(int paramInt) throws PDFNetException {
/*  61 */     SetMaximumImagePixels(this.a, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPreferJPG(boolean paramBoolean) throws PDFNetException {
/*  70 */     SetPreferJPG(this.a, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setJPGQuality(int paramInt) throws PDFNetException {
/*  79 */     SetJPGQuality(this.a, paramInt);
/*     */   }
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
/*     */   public void SetThreshold(int paramInt) throws PDFNetException {
/* 101 */     SetThreshold(this.a, paramInt);
/*     */   }
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
/*     */ 
/*     */   
/*     */   public void Process(PDFDoc paramPDFDoc, int paramInt) throws PDFNetException {
/* 127 */     Process(this.a, paramPDFDoc.__GetHandle(), paramInt);
/*     */   }
/*     */   
/*     */   protected void finalize() throws Throwable {
/* 131 */     destroy();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void destroy() {
/* 141 */     if (this.a != 0L) {
/* 142 */       Destroy(this.a);
/* 143 */       this.a = 0L;
/*     */     } 
/*     */   }
/*     */   
/*     */   static native long FlattenerCreate();
/*     */   
/*     */   static native void Destroy(long paramLong);
/*     */   
/*     */   static native void SetDPI(long paramLong, int paramInt);
/*     */   
/*     */   static native void SetMaximumImagePixels(long paramLong, int paramInt);
/*     */   
/*     */   static native void SetPreferJPG(long paramLong, boolean paramBoolean);
/*     */   
/*     */   static native void SetJPGQuality(long paramLong, int paramInt);
/*     */   
/*     */   static native void SetThreshold(long paramLong, int paramInt);
/*     */   
/*     */   static native void Process(long paramLong1, long paramLong2, int paramInt);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\Flattener.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */