/*     */ package com.pdftron.pdf;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Stamper
/*     */ {
/*     */   public static final int e_relative_scale = 1;
/*     */   public static final int e_absolute_size = 2;
/*     */   public static final int e_font_size = 3;
/*     */   public static final int e_horizontal_left = -1;
/*     */   public static final int e_horizontal_center = 0;
/*     */   public static final int e_horizontal_right = 1;
/*     */   public static final int e_vertical_bottom = -1;
/*     */   public static final int e_vertical_center = 0;
/*     */   public static final int e_vertical_top = 1;
/*     */   public static final int e_align_left = -1;
/*     */   public static final int e_align_center = 0;
/*     */   public static final int e_align_right = 1;
/*     */   private long a;
/*     */   
/*     */   public Stamper(int paramInt, double paramDouble1, double paramDouble2) {
/*  77 */     this.a = StamperCreate(paramInt, paramDouble1, paramDouble2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void destroy() {
/*  87 */     if (this.a != 0L) {
/*  88 */       Destroy(this.a);
/*  89 */       this.a = 0L;
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
/*     */   public void stampImage(PDFDoc paramPDFDoc, Image paramImage, PageSet paramPageSet) {
/* 105 */     StampImage(this.a, paramPDFDoc.__GetHandle(), paramImage.a, paramPageSet.a);
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
/*     */   public void stampPage(PDFDoc paramPDFDoc, Page paramPage, PageSet paramPageSet) {
/* 120 */     StampPage(this.a, paramPDFDoc.__GetHandle(), paramPage.a, paramPageSet.a);
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
/*     */   public void stampText(PDFDoc paramPDFDoc, String paramString, PageSet paramPageSet) {
/* 134 */     StampText(this.a, paramPDFDoc.__GetHandle(), paramString, paramPageSet.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFont(Font paramFont) {
/* 145 */     SetFont(this.a, paramFont.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFontColor(ColorPt paramColorPt) {
/* 155 */     SetFontColor(this.a, paramColorPt.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOpacity(double paramDouble) {
/* 165 */     SetOpacity(this.a, paramDouble);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRotation(double paramDouble) {
/* 175 */     SetRotation(this.a, paramDouble);
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
/*     */   public void setAsBackground(boolean paramBoolean) {
/* 187 */     SetAsBackground(this.a, paramBoolean);
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
/*     */   public void setAsAnnotation(boolean paramBoolean) {
/* 201 */     SetAsAnnotation(this.a, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void showsOnScreen(boolean paramBoolean) {
/* 211 */     ShowsOnScreen(this.a, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void showsOnPrint(boolean paramBoolean) {
/* 221 */     ShowsOnPrint(this.a, paramBoolean);
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
/*     */   public void setPosition(double paramDouble1, double paramDouble2) {
/* 235 */     SetPosition(this.a, paramDouble1, paramDouble2);
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
/*     */   public void setPosition(double paramDouble1, double paramDouble2, boolean paramBoolean) {
/* 255 */     SetPosition(this.a, paramDouble1, paramDouble2, paramBoolean);
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
/*     */   public void setAlignment(int paramInt1, int paramInt2) {
/* 276 */     SetAlignment(this.a, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTextAlignment(int paramInt) {
/* 286 */     SetTextAlignment(this.a, paramInt);
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
/*     */   public void setSize(int paramInt, double paramDouble1, double paramDouble2) {
/* 313 */     SetSize(this.a, paramInt, paramDouble1, paramDouble2);
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
/*     */   public static void deleteStamps(PDFDoc paramPDFDoc, PageSet paramPageSet) {
/* 325 */     DeleteStamps(paramPDFDoc.__GetHandle(), paramPageSet.a);
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
/*     */   public static boolean hasStamps(PDFDoc paramPDFDoc, PageSet paramPageSet) {
/* 338 */     return HasStamps(paramPDFDoc.__GetHandle(), paramPageSet.a);
/*     */   }
/*     */   
/*     */   static native long StamperCreate(int paramInt, double paramDouble1, double paramDouble2);
/*     */   
/*     */   static native void Destroy(long paramLong);
/*     */   
/*     */   static native void StampImage(long paramLong1, long paramLong2, long paramLong3, long paramLong4);
/*     */   
/*     */   static native void StampPage(long paramLong1, long paramLong2, long paramLong3, long paramLong4);
/*     */   
/*     */   static native void StampText(long paramLong1, long paramLong2, String paramString, long paramLong3);
/*     */   
/*     */   static native void SetFont(long paramLong1, long paramLong2);
/*     */   
/*     */   static native void SetFontColor(long paramLong1, long paramLong2);
/*     */   
/*     */   static native void SetOpacity(long paramLong, double paramDouble);
/*     */   
/*     */   static native void SetRotation(long paramLong, double paramDouble);
/*     */   
/*     */   static native void SetAsBackground(long paramLong, boolean paramBoolean);
/*     */   
/*     */   static native void SetAsAnnotation(long paramLong, boolean paramBoolean);
/*     */   
/*     */   static native void ShowsOnScreen(long paramLong, boolean paramBoolean);
/*     */   
/*     */   static native void ShowsOnPrint(long paramLong, boolean paramBoolean);
/*     */   
/*     */   static native void SetPosition(long paramLong, double paramDouble1, double paramDouble2);
/*     */   
/*     */   static native void SetPosition(long paramLong, double paramDouble1, double paramDouble2, boolean paramBoolean);
/*     */   
/*     */   static native void SetAlignment(long paramLong, int paramInt1, int paramInt2);
/*     */   
/*     */   static native void SetTextAlignment(long paramLong, int paramInt);
/*     */   
/*     */   static native void SetSize(long paramLong, int paramInt, double paramDouble1, double paramDouble2);
/*     */   
/*     */   static native void DeleteStamps(long paramLong1, long paramLong2);
/*     */   
/*     */   static native boolean HasStamps(long paramLong1, long paramLong2);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\Stamper.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */