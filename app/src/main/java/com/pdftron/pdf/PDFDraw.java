/*     */ package com.pdftron.pdf;
/*     */ 
/*     */ import android.graphics.Bitmap;
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.helpers.BitmapHelper;
/*     */ import com.pdftron.helpers.Graphics;
/*     */ import com.pdftron.pdf.ocg.Context;
/*     */ import com.pdftron.sdf.Obj;
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
/*     */ public class PDFDraw
/*     */   extends h
/*     */ {
/*     */   public static final int e_postprocess_none = 0;
/*     */   public static final int e_postprocess_invert = 1;
/*     */   public static final int e_rgba = 0;
/*     */   public static final int e_bgra = 1;
/*     */   public static final int e_rgb = 2;
/*     */   public static final int e_bgr = 3;
/*     */   public static final int e_gray = 4;
/*     */   public static final int e_gray_alpha = 5;
/*     */   private long a;
/*     */   private long d;
/*     */   private Bitmap e;
/*     */   
/*     */   public PDFDraw() throws PDFNetException {
/* 692 */     this.e = null; this.a = PDFDrawCreate(92.0D); this.d = 0L; clearList(); } public PDFDraw(double paramDouble) throws PDFNetException { this.e = null;
/*     */     this.a = PDFDrawCreate(paramDouble);
/*     */     this.d = 0L;
/*     */     clearList(); }
/*     */ 
/*     */   
/*     */   public void destroy() throws PDFNetException {
/*     */     if (this.a != 0L) {
/*     */       Destroy(this.a, this.d);
/*     */       this.a = 0L;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void finalize() throws Throwable {
/*     */     destroy();
/*     */   }
/*     */   
/*     */   public void setRasterizerType(int paramInt) throws PDFNetException {
/*     */     SetRasterizerType(this.a, paramInt);
/*     */   }
/*     */   
/*     */   public void setDPI(double paramDouble) throws PDFNetException {
/*     */     SetDPI(this.a, paramDouble);
/*     */   }
/*     */   
/*     */   public void setImageSize(int paramInt1, int paramInt2) throws PDFNetException {
/*     */     SetImageSize(this.a, paramInt1, paramInt2, true);
/*     */   }
/*     */   
/*     */   public void setImageSize(int paramInt1, int paramInt2, boolean paramBoolean) throws PDFNetException {
/*     */     SetImageSize(this.a, paramInt1, paramInt2, paramBoolean);
/*     */   }
/*     */   
/*     */   public void setPageBox(int paramInt) throws PDFNetException {
/*     */     SetPageBox(this.a, paramInt);
/*     */   }
/*     */   
/*     */   public void setClipRect(Rect paramRect) throws PDFNetException {
/*     */     SetClipRect(this.a, paramRect.a);
/*     */   }
/*     */   
/*     */   public void setFlipYAxis(boolean paramBoolean) throws PDFNetException {
/*     */     SetFlipYAxis(this.a, paramBoolean);
/*     */   }
/*     */   
/*     */   public void setRotate(int paramInt) throws PDFNetException {
/*     */     SetRotate(this.a, paramInt);
/*     */   }
/*     */   
/*     */   public void setDrawAnnotations(boolean paramBoolean) throws PDFNetException {
/*     */     SetDrawAnnotations(this.a, paramBoolean);
/*     */   }
/*     */   
/*     */   public void setHighlightFields(boolean paramBoolean) {
/*     */     SetHighlightFields(this.a, paramBoolean);
/*     */   }
/*     */   
/*     */   public void setAntiAliasing(boolean paramBoolean) throws PDFNetException {
/*     */     SetAntiAliasing(this.a, paramBoolean);
/*     */   }
/*     */   
/*     */   public void setPathHinting(boolean paramBoolean) throws PDFNetException {
/*     */     SetPathHinting(this.a, paramBoolean);
/*     */   }
/*     */   
/*     */   public void setThinLineAdjustment(boolean paramBoolean1, boolean paramBoolean2) {
/*     */     SetThinLineAdjustment(this.a, paramBoolean1, paramBoolean2);
/*     */   }
/*     */   
/*     */   public void setImageSmoothing() throws PDFNetException {
/*     */     SetImageSmoothing(this.a, true, false);
/*     */   }
/*     */   
/*     */   public void setImageSmoothing(boolean paramBoolean) throws PDFNetException {
/*     */     SetImageSmoothing(this.a, paramBoolean, false);
/*     */   }
/*     */   
/*     */   public void setImageSmoothing(boolean paramBoolean1, boolean paramBoolean2) throws PDFNetException {
/*     */     SetImageSmoothing(this.a, paramBoolean1, paramBoolean2);
/*     */   }
/*     */   
/*     */   public void setCaching() throws PDFNetException {
/*     */     SetCaching(this.a, true);
/*     */   }
/*     */   
/*     */   public void setCaching(boolean paramBoolean) throws PDFNetException {
/*     */     SetCaching(this.a, paramBoolean);
/*     */   }
/*     */   
/*     */   public void setGamma(double paramDouble) throws PDFNetException {
/*     */     SetGamma(this.a, paramDouble);
/*     */   }
/*     */   
/*     */   public void setOCGContext(Context paramContext) throws PDFNetException {
/*     */     if (paramContext == null) {
/*     */       SetOCGContext(this.a, 0L);
/*     */       return;
/*     */     } 
/*     */     SetOCGContext(this.a, paramContext.__GetHandle());
/*     */   }
/*     */   
/*     */   public void setPrintMode(boolean paramBoolean) throws PDFNetException {
/*     */     SetPrintMode(this.a, paramBoolean);
/*     */   }
/*     */   
/*     */   public void setDefaultPageColor(byte paramByte1, byte paramByte2, byte paramByte3) throws PDFNetException {
/*     */     SetDefaultPageColor(this.a, paramByte1, paramByte2, paramByte3);
/*     */   }
/*     */   
/*     */   public void setPageTransparent(boolean paramBoolean) throws PDFNetException {
/*     */     SetPageTransparent(this.a, paramBoolean);
/*     */   }
/*     */   
/*     */   public void setColorPostProcessMode(int paramInt) {
/*     */     SetColorPostProcessMode(this.a, paramInt);
/*     */   }
/*     */   
/*     */   public void setOverprint(int paramInt) throws PDFNetException {
/*     */     SetOverprint(this.a, paramInt);
/*     */   }
/*     */   
/*     */   public void export(Page paramPage, String paramString) throws PDFNetException {
/*     */     Export(this.a, paramPage.a, paramString, "PNG", 0L);
/*     */   }
/*     */   
/*     */   public void export(Page paramPage, String paramString1, String paramString2) throws PDFNetException {
/*     */     Export(this.a, paramPage.a, paramString1, paramString2, 0L);
/*     */   }
/*     */   
/*     */   public void export(Page paramPage, String paramString1, String paramString2, Obj paramObj) throws PDFNetException {
/*     */     Export(this.a, paramPage.a, paramString1, paramString2, paramObj.__GetHandle());
/*     */   }
/*     */   
/*     */   public Bitmap getBitmap(Page paramPage) {
/*     */     return getBitmap(paramPage, null);
/*     */   }
/*     */   
/*     */   public Bitmap getBitmap(Page paramPage, Bitmap paramBitmap) {
/*     */     long arrayOfLong[], l = (arrayOfLong = GetBitmap(this.a, paramPage.a))[0];
/*     */     int j = (int)arrayOfLong[1];
/*     */     int i = (int)arrayOfLong[2];
/*     */     int k;
/*     */     if ((k = j * i) == 0)
/*     */       return null; 
/*     */     BitmapHelper.CustomBitmap customBitmap = new BitmapHelper.CustomBitmap(j, i, paramBitmap);
/*     */     SetDataBuf(this.a, l, customBitmap.bitmapArray);
/*     */     customBitmap.finalize();
/*     */     return customBitmap.bitmap;
/*     */   }
/*     */   
/*     */   public byte[] getByteBuffer(Page paramPage) {
/*     */     long arrayOfLong[], l1 = (arrayOfLong = GetBitmap(this.a, paramPage.a))[0];
/*     */     long l2 = arrayOfLong[2];
/*     */     long l3 = arrayOfLong[3];
/*     */     int i;
/*     */     byte[] arrayOfByte = new byte[i = (int)l2 * (int)l3];
/*     */     SetDataBufByte(this.a, l1, arrayOfByte);
/*     */     return arrayOfByte;
/*     */   }
/*     */   
/*     */   public class IntBufferData {
/*     */     public int[] data;
/*     */     public int width = 0;
/*     */     public int height = 0;
/*     */     public int stride = 0;
/*     */     
/*     */     public IntBufferData(PDFDraw this$0) {}
/*     */   }
/*     */   
/*     */   public IntBufferData getIntBuffer(Page paramPage) {
/*     */     long arrayOfLong[], l = (arrayOfLong = GetBitmap(this.a, paramPage.a))[0];
/*     */     IntBufferData intBufferData;
/*     */     (intBufferData = new IntBufferData(this)).width = (int)arrayOfLong[1];
/*     */     intBufferData.height = (int)arrayOfLong[2];
/*     */     intBufferData.stride = (int)arrayOfLong[3];
/*     */     int i = intBufferData.width * intBufferData.height;
/*     */     intBufferData.data = new int[i];
/*     */     SetDataBuf(this.a, l, intBufferData.data);
/*     */     return intBufferData;
/*     */   }
/*     */   
/*     */   public void drawInRect(Graphics paramGraphics, Page paramPage, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*     */     this.e = getBitmap(paramPage, this.e);
/*     */     BitmapHelper.drawInRect(this.e, paramGraphics, paramInt3, paramInt4);
/*     */   }
/*     */   
/*     */   public void setErrorReportProc(ErrorReportProc paramErrorReportProc, Object paramObject) throws PDFNetException {
/*     */     if (this.d != 0L)
/*     */       DestroyProcData(this.d); 
/*     */     this.d = SetErrorReportProc(this.a, paramErrorReportProc, paramObject);
/*     */   }
/*     */   
/*     */   public Separation[] getSeparationBitmaps(Page paramPage) throws PDFNetException {
/*     */     Separation[] arrayOfSeparation;
/*     */     return arrayOfSeparation = GetSeparationBitmaps(this.a, paramPage.a);
/*     */   }
/*     */   
/*     */   static native long PDFDrawCreate(double paramDouble);
/*     */   
/*     */   static native void Destroy(long paramLong1, long paramLong2);
/*     */   
/*     */   static native void DestroyProcData(long paramLong);
/*     */   
/*     */   static native void SetRasterizerType(long paramLong, int paramInt);
/*     */   
/*     */   static native void SetDPI(long paramLong, double paramDouble);
/*     */   
/*     */   static native void SetImageSize(long paramLong, int paramInt1, int paramInt2, boolean paramBoolean);
/*     */   
/*     */   static native void SetPageBox(long paramLong, int paramInt);
/*     */   
/*     */   static native void SetClipRect(long paramLong1, long paramLong2);
/*     */   
/*     */   static native void SetFlipYAxis(long paramLong, boolean paramBoolean);
/*     */   
/*     */   static native void SetRotate(long paramLong, int paramInt);
/*     */   
/*     */   static native void SetDrawAnnotations(long paramLong, boolean paramBoolean);
/*     */   
/*     */   static native void SetHighlightFields(long paramLong, boolean paramBoolean);
/*     */   
/*     */   static native void SetPathHinting(long paramLong, boolean paramBoolean);
/*     */   
/*     */   static native void SetAntiAliasing(long paramLong, boolean paramBoolean);
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
/*     */   static native void SetPageTransparent(long paramLong, boolean paramBoolean);
/*     */   
/*     */   static native void SetDefaultPageColor(long paramLong, byte paramByte1, byte paramByte2, byte paramByte3);
/*     */   
/*     */   static native void SetOverprint(long paramLong, int paramInt);
/*     */   
/*     */   static native void SetColorPostProcessMode(long paramLong, int paramInt);
/*     */   
/*     */   static native void Export(long paramLong1, long paramLong2, String paramString1, String paramString2, long paramLong3);
/*     */   
/*     */   static native long SetErrorReportProc(long paramLong, ErrorReportProc paramErrorReportProc, Object paramObject);
/*     */   
/*     */   static native long[] GetBitmap(long paramLong1, long paramLong2);
/*     */   
/*     */   static native void SetDataBuf(long paramLong1, long paramLong2, int[] paramArrayOfint);
/*     */   
/*     */   static native void SetDataBufByte(long paramLong1, long paramLong2, byte[] paramArrayOfbyte);
/*     */   
/*     */   static native Separation[] GetSeparationBitmaps(long paramLong1, long paramLong2);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\PDFDraw.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */