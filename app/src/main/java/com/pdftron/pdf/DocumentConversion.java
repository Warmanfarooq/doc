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
/*     */ public class DocumentConversion
/*     */ {
/*     */   public static final int e_success = 0;
/*     */   public static final int e_incomplete = 1;
/*     */   public static final int e_failure = 2;
/*     */   private long a;
/*     */   
/*     */   public void destroy() throws PDFNetException {
/*  35 */     if (this.a != 0L) {
/*  36 */       Destroy(this.a);
/*  37 */       this.a = 0L;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finalize() throws Throwable {
/*  45 */     destroy();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DocumentConversion(long paramLong) {
/*  52 */     this.a = paramLong;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int tryConvert() throws PDFNetException {
/*  62 */     return TryConvert(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void convert() throws PDFNetException {
/*  71 */     Convert(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void convertNextPage() throws PDFNetException {
/*  80 */     ConvertNextPage(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PDFDoc getDoc() throws PDFNetException {
/*  90 */     return PDFDoc.__Create(GetDoc(this.a));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getConversionStatus() throws PDFNetException {
/* 100 */     return GetConversionStatus(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cancelConversion() throws PDFNetException {
/* 109 */     CancelConversion(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCancelled() throws PDFNetException {
/* 119 */     return IsCancelled(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasProgressTracking() throws PDFNetException {
/* 129 */     return HasProgressTracking(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getProgress() throws PDFNetException {
/* 139 */     return GetProgress(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getProgressLabel() throws PDFNetException {
/* 149 */     return GetProgressLabel(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNumConvertedPages() throws PDFNetException {
/* 159 */     return GetNumConvertedPages(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getErrorString() throws PDFNetException {
/* 169 */     return GetErrorString(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNumWarnings() throws PDFNetException {
/* 179 */     return GetNumWarnings(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getWarningString(int paramInt) throws PDFNetException {
/* 190 */     return GetWarningString(this.a, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long __GetHandle() {
/* 197 */     return this.a;
/*     */   }
/*     */   
/*     */   public static DocumentConversion __Create(long paramLong) {
/* 201 */     return new DocumentConversion(paramLong);
/*     */   }
/*     */   
/*     */   static native void Destroy(long paramLong);
/*     */   
/*     */   static native int TryConvert(long paramLong);
/*     */   
/*     */   static native void Convert(long paramLong);
/*     */   
/*     */   static native void ConvertNextPage(long paramLong);
/*     */   
/*     */   static native long GetDoc(long paramLong);
/*     */   
/*     */   static native int GetConversionStatus(long paramLong);
/*     */   
/*     */   static native void CancelConversion(long paramLong);
/*     */   
/*     */   static native boolean IsCancelled(long paramLong);
/*     */   
/*     */   static native boolean HasProgressTracking(long paramLong);
/*     */   
/*     */   static native double GetProgress(long paramLong);
/*     */   
/*     */   static native String GetProgressLabel(long paramLong);
/*     */   
/*     */   static native int GetNumConvertedPages(long paramLong);
/*     */   
/*     */   static native String GetErrorString(long paramLong);
/*     */   
/*     */   static native int GetNumWarnings(long paramLong);
/*     */   
/*     */   static native String GetWarningString(long paramLong, int paramInt);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\DocumentConversion.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */