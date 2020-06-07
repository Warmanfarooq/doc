/*     */ package com.pdftron.pdf;
/*     */ 
/*     */ import com.pdftron.common.Matrix2D;
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.filters.Filter;
/*     */ import com.pdftron.pdf.ocg.Context;
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
/*     */ public class PDFRasterizer
/*     */ {
/*     */   public static final int e_postprocess_none = 0;
/*     */   public static final int e_postprocess_invert = 1;
/*     */   public static final int e_postprocess_gradient_map = 2;
/*     */   public static final int e_postprocess_night_mode = 3;
/*  24 */   private long a = PDFRasterizerCreate();
/*  25 */   private long b = PDFRasterizerCreateCancelFlag();
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
/*     */   public void destroy() throws PDFNetException {
/*  38 */     if (this.a != 0L || this.b != 0L) {
/*  39 */       Destroy(this.a, this.b);
/*  40 */       this.a = 0L;
/*  41 */       this.b = 0L;
/*     */     } 
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
/*     */   public void setAntiAliasing(boolean paramBoolean) throws PDFNetException {
/*  59 */     SetAntiAliasing(this.a, paramBoolean);
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
/*     */   public void setCancel(boolean paramBoolean) throws PDFNetException {
/*  76 */     SetCancel(this.b, paramBoolean);
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
/*     */   public void setPathHinting(boolean paramBoolean) throws PDFNetException {
/*  92 */     SetPathHinting(this.a, paramBoolean);
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
/*     */   public void setDrawAnnotations(boolean paramBoolean) throws PDFNetException {
/* 105 */     SetDrawAnnotations(this.a, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHighlightFields(boolean paramBoolean) {
/* 115 */     SetHighlightFields(this.a, paramBoolean);
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
/*     */   public void setThinLineAdjustment(boolean paramBoolean1, boolean paramBoolean2) {
/* 134 */     SetThinLineAdjustment(this.a, paramBoolean1, paramBoolean2);
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
/*     */   public void setImageSmoothing() throws PDFNetException {
/* 152 */     SetImageSmoothing(this.a, true, false);
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
/*     */   public void setImageSmoothing(boolean paramBoolean) throws PDFNetException {
/* 164 */     SetImageSmoothing(this.a, paramBoolean, false);
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
/*     */   public void setImageSmoothing(boolean paramBoolean1, boolean paramBoolean2) throws PDFNetException {
/* 178 */     SetImageSmoothing(this.a, paramBoolean1, paramBoolean2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCaching() throws PDFNetException {
/* 188 */     SetCaching(this.a, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCaching(boolean paramBoolean) throws PDFNetException {
/* 199 */     SetCaching(this.a, paramBoolean);
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
/*     */   public void setGamma(double paramDouble) throws PDFNetException {
/* 218 */     SetGamma(this.a, paramDouble);
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
/*     */   public void setOCGContext(Context paramContext) throws PDFNetException {
/* 235 */     if (paramContext == null) {
/* 236 */       SetOCGContext(this.a, 0L); return;
/*     */     } 
/* 238 */     SetOCGContext(this.a, paramContext.__GetHandle());
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
/*     */   public void setPrintMode(boolean paramBoolean) throws PDFNetException {
/* 254 */     SetPrintMode(this.a, paramBoolean);
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
/*     */   public void setOverprint(int paramInt) throws PDFNetException {
/* 267 */     SetOverprint(this.a, paramInt);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setColorPostProcessMode(int paramInt) {
/* 297 */     SetColorPostProcessMode(this.a, paramInt);
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
/*     */   public void setColorPostProcessColors(int paramInt1, int paramInt2) throws PDFNetException {
/* 315 */     SetColorPostProcessColors(this.a, paramInt1, paramInt2);
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
/*     */   public void setColorPostProcessMapFile(Filter paramFilter) throws PDFNetException {
/* 331 */     SetColorPostProcessMapFile(this.a, paramFilter.__GetHandle());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColorPostProcessMode() {
/* 338 */     return GetColorPostProcessMode(this.a);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void rasterize(Page paramPage, int[] paramArrayOfint, int paramInt1, int paramInt2, boolean paramBoolean, Matrix2D paramMatrix2D, Rect paramRect) throws PDFNetException {
/* 367 */     RasterizeToIntBuffer(this.a, paramPage.a, paramArrayOfint, paramInt1, paramInt2, paramBoolean, paramMatrix2D.__GetHandle(), (paramRect == null) ? 0L : paramRect.a, 0L, this.b);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] rasterize(Page paramPage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean, Matrix2D paramMatrix2D, Rect paramRect) throws PDFNetException {
/* 396 */     byte[] arrayOfByte = new byte[paramInt1 * paramInt2 * paramInt4];
/* 397 */     Rasterize(this.a, paramPage.a, arrayOfByte, paramInt1, paramInt2, paramInt3, paramInt4, paramBoolean, paramMatrix2D.__GetHandle(), (paramRect == null) ? 0L : paramRect.a);
/* 398 */     return arrayOfByte;
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
/*     */   public Separation[] rasterizeSeparations(Page paramPage, int paramInt1, int paramInt2, Matrix2D paramMatrix2D, Rect paramRect, boolean paramBoolean) throws PDFNetException {
/* 423 */     setCancel(paramBoolean);
/*     */     Separation[] arrayOfSeparation;
/* 425 */     return arrayOfSeparation = RasterizeSeparations(this.a, paramPage.a, paramInt1, paramInt2, paramMatrix2D.__GetHandle(), (paramRect == null) ? 0L : paramRect.a, this.b);
/*     */   }
/*     */   
/*     */   static native long PDFRasterizerCreate();
/*     */   
/*     */   static native long PDFRasterizerCreateCancelFlag();
/*     */   
/*     */   static native void Destroy(long paramLong1, long paramLong2);
/*     */   
/*     */   static native void SetDrawAnnotations(long paramLong, boolean paramBoolean);
/*     */   
/*     */   static native void SetHighlightFields(long paramLong, boolean paramBoolean);
/*     */   
/*     */   static native void SetAntiAliasing(long paramLong, boolean paramBoolean);
/*     */   
/*     */   static native void SetPathHinting(long paramLong, boolean paramBoolean);
/*     */   
/*     */   static native void SetThinLineAdjustment(long paramLong, boolean paramBoolean1, boolean paramBoolean2);
/*     */   
/*     */   static native void SetImageSmoothing(long paramLong, boolean paramBoolean1, boolean paramBoolean2);
/*     */   
/*     */   static native void SetCaching(long paramLong, boolean paramBoolean);
/*     */   
/*     */   static native void SetGamma(long paramLong, double paramDouble);
/*     */   
/*     */   static native void SetOCGContext(long paramLong1, long paramLong2);
/*     */   
/*     */   static native void SetPrintMode(long paramLong, boolean paramBoolean);
/*     */   
/*     */   static native void SetOverprint(long paramLong, int paramInt);
/*     */   
/*     */   static native void SetCancel(long paramLong, boolean paramBoolean);
/*     */   
/*     */   static native void SetColorPostProcessMode(long paramLong, int paramInt);
/*     */   
/*     */   static native void SetColorPostProcessColors(long paramLong, int paramInt1, int paramInt2);
/*     */   
/*     */   static native void SetColorPostProcessMapFile(long paramLong1, long paramLong2);
/*     */   
/*     */   static native int GetColorPostProcessMode(long paramLong);
/*     */   
/*     */   static native void Rasterize(long paramLong1, long paramLong2, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean, long paramLong3, long paramLong4);
/*     */   
/*     */   static native void RasterizeToIntBuffer(long paramLong1, long paramLong2, int[] paramArrayOfint, int paramInt1, int paramInt2, boolean paramBoolean, long paramLong3, long paramLong4, long paramLong5, long paramLong6);
/*     */   
/*     */   static native Separation[] RasterizeSeparations(long paramLong1, long paramLong2, int paramInt1, int paramInt2, long paramLong3, long paramLong4, long paramLong5);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\PDFRasterizer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */