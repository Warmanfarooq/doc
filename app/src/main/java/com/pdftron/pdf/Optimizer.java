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
/*     */ public class Optimizer
/*     */ {
/*     */   public static class ImageSettings
/*     */   {
/*     */     public static final int e_retain = 0;
/*     */     public static final int e_flate = 1;
/*     */     public static final int e_jpeg = 2;
/*     */     public static final int e_jpeg2000 = 3;
/*     */     public static final int e_none = 4;
/*     */     public static final int e_off = 0;
/*     */     public static final int e_default = 1;
/*  38 */     private int a = 0;
/*  39 */     private int b = 1;
/*  40 */     private long c = 5L;
/*  41 */     private double d = 225.0D;
/*  42 */     private double e = 150.0D;
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean f = false;
/*     */ 
/*     */     
/*     */     private boolean g = false;
/*     */ 
/*     */ 
/*     */     
/*     */     public void setImageDPI(double param1Double1, double param1Double2) {
/*  54 */       this.d = param1Double1;
/*  55 */       this.e = param1Double2;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setCompressionMode(int param1Int) {
/*  80 */       this.a = param1Int;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setDownsampleMode(int param1Int) {
/*  96 */       this.b = param1Int;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setQuality(long param1Long) {
/* 105 */       this.c = param1Long;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forceRecompression(boolean param1Boolean) {
/* 116 */       this.f = param1Boolean;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forceChanges(boolean param1Boolean) {
/* 125 */       this.g = param1Boolean;
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
/*     */   public static class MonoImageSettings
/*     */   {
/* 144 */     private int a = 0;
/* 145 */     private int b = 1;
/* 146 */     private double c = 450.0D;
/* 147 */     private double d = 300.0D;
/*     */     private boolean f = false;
/*     */     private boolean g = false;
/* 150 */     private double e = 8.5D;
/*     */     
/*     */     public static final int e_jbig2 = 0;
/*     */     
/*     */     public static final int e_flate = 1;
/*     */     public static final int e_none = 2;
/*     */     public static final int e_off = 0;
/*     */     public static final int e_default = 1;
/*     */     
/*     */     public void setImageDPI(double param1Double1, double param1Double2) {
/* 160 */       this.c = param1Double1;
/* 161 */       this.d = param1Double2;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setCompressionMode(int param1Int) {
/* 180 */       this.a = param1Int;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setDownsampleMode(int param1Int) {
/* 196 */       this.b = param1Int;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forceRecompression(boolean param1Boolean) {
/* 207 */       this.f = param1Boolean;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forceChanges(boolean param1Boolean) {
/* 216 */       this.g = param1Boolean;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setJBIG2Threshold(double param1Double) {
/* 226 */       this.e = param1Double;
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
/*     */   public static class TextSettings
/*     */   {
/*     */     private boolean a = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean b = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void subsetFonts(boolean param1Boolean) {
/* 256 */       this.a = param1Boolean;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void embedFonts(boolean param1Boolean) {
/* 268 */       this.b = param1Boolean;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class OptimizerSettings
/*     */   {
/*     */     private ImageSettings a;
/*     */ 
/*     */     
/*     */     private ImageSettings b;
/*     */ 
/*     */     
/*     */     private MonoImageSettings c;
/*     */ 
/*     */     
/*     */     private TextSettings d;
/*     */ 
/*     */     
/*     */     private boolean e = true;
/*     */ 
/*     */ 
/*     */     
/*     */     public void setColorImageSettings(ImageSettings param1ImageSettings) {
/* 294 */       this.a = param1ImageSettings;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setGrayscaleImageSettings(ImageSettings param1ImageSettings) {
/* 302 */       this.b = param1ImageSettings;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setMonoImageSettings(MonoImageSettings param1MonoImageSettings) {
/* 310 */       this.c = param1MonoImageSettings;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setTextSettings(TextSettings param1TextSettings) {
/* 318 */       this.d = param1TextSettings;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void removeCustomEntries(boolean param1Boolean) {
/* 327 */       this.e = param1Boolean;
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
/*     */   public static void optimize(PDFDoc paramPDFDoc) {
/* 342 */     a(paramPDFDoc, new ImageSettings(), new ImageSettings(), new MonoImageSettings(), new TextSettings(), true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void optimize(PDFDoc paramPDFDoc, OptimizerSettings paramOptimizerSettings) {
/* 352 */     a(paramPDFDoc, (OptimizerSettings.a(paramOptimizerSettings) == null) ? new ImageSettings() : OptimizerSettings.a(paramOptimizerSettings), 
/* 353 */         (OptimizerSettings.b(paramOptimizerSettings) == null) ? new ImageSettings() : OptimizerSettings.b(paramOptimizerSettings), 
/* 354 */         (OptimizerSettings.c(paramOptimizerSettings) == null) ? new MonoImageSettings() : OptimizerSettings.c(paramOptimizerSettings), 
/* 355 */         (OptimizerSettings.d(paramOptimizerSettings) == null) ? new TextSettings() : OptimizerSettings.d(paramOptimizerSettings), 
/* 356 */         OptimizerSettings.e(paramOptimizerSettings));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void a(PDFDoc paramPDFDoc, ImageSettings paramImageSettings1, ImageSettings paramImageSettings2, MonoImageSettings paramMonoImageSettings, TextSettings paramTextSettings, boolean paramBoolean) {
/* 366 */     Optimize(paramPDFDoc.__GetHandle(), ImageSettings.a(paramImageSettings1), ImageSettings.b(paramImageSettings1), 
/* 367 */         ImageSettings.c(paramImageSettings1), ImageSettings.d(paramImageSettings1), ImageSettings.e(paramImageSettings1), 
/* 368 */         ImageSettings.f(paramImageSettings1), ImageSettings.g(paramImageSettings1), 
/* 369 */         ImageSettings.a(paramImageSettings2), ImageSettings.b(paramImageSettings2), 
/* 370 */         ImageSettings.c(paramImageSettings2), ImageSettings.d(paramImageSettings2), ImageSettings.e(paramImageSettings2), 
/* 371 */         ImageSettings.f(paramImageSettings2), ImageSettings.g(paramImageSettings2), 
/* 372 */         MonoImageSettings.a(paramMonoImageSettings), MonoImageSettings.b(paramMonoImageSettings), 
/* 373 */         MonoImageSettings.c(paramMonoImageSettings), MonoImageSettings.d(paramMonoImageSettings), 
/* 374 */         MonoImageSettings.e(paramMonoImageSettings), MonoImageSettings.f(paramMonoImageSettings), 
/* 375 */         TextSettings.a(paramTextSettings), TextSettings.b(paramTextSettings), paramBoolean, MonoImageSettings.g(paramMonoImageSettings));
/*     */   }
/*     */   
/*     */   static native void Optimize(long paramLong1, int paramInt1, int paramInt2, long paramLong2, double paramDouble1, double paramDouble2, boolean paramBoolean1, boolean paramBoolean2, int paramInt3, int paramInt4, long paramLong3, double paramDouble3, double paramDouble4, boolean paramBoolean3, boolean paramBoolean4, int paramInt5, int paramInt6, double paramDouble5, double paramDouble6, boolean paramBoolean5, boolean paramBoolean6, boolean paramBoolean7, boolean paramBoolean8, boolean paramBoolean9, double paramDouble7);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\Optimizer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */