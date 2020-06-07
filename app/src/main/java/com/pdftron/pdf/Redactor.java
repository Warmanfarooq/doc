/*     */ package com.pdftron.pdf;
/*     */ 
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.sdf.Doc;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Redactor
/*     */ {
/*     */   public static class Redaction
/*     */   {
/*     */     long a;
/*     */     
/*     */     public Redaction(int param1Int, Rect param1Rect, boolean param1Boolean, String param1String) throws PDFNetException {
/*  49 */       this.a = Redactor.RedactionCreate(param1Int, param1Rect.a, param1Boolean, param1String);
/*     */     }
/*     */     
/*     */     protected void finalize() throws Throwable {
/*  53 */       destroy();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void destroy() {
/*  63 */       if (this.a != 0L) {
/*  64 */         Redactor.RedactionDestroy(this.a);
/*  65 */         this.a = 0L;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Appearance
/*     */   {
/*     */     public boolean redactionOverlay = true;
/*     */     
/*     */     public boolean border = true;
/*     */     
/*     */     public boolean useOverlayText = true;
/*     */     
/*  80 */     public double minFontSize = 2.0D;
/*  81 */     public double maxFontSize = 24.0D;
/*  82 */     public int horizTextAlignment = -1;
/*  83 */     public int vertTextAlignment = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean showRedactedContentRegions = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  98 */     public ColorPt positiveOverlayColor = new ColorPt(1.0D, 1.0D, 1.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 103 */     public ColorPt negativeOverlayColor = new ColorPt(1.0D, 1.0D, 1.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 136 */     public ColorPt textColor = new ColorPt(0.0D, 0.0D, 0.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 167 */     public ColorPt redactedContentColor = new ColorPt(0.3D, 0.3D, 0.3D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Font font;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void redact(Doc paramDoc, Redaction[] paramArrayOfRedaction, Appearance paramAppearance, boolean paramBoolean1, boolean paramBoolean2) throws PDFNetException {
/* 184 */     long[] arrayOfLong = new long[paramArrayOfRedaction.length];
/* 185 */     for (byte b = 0; b < paramArrayOfRedaction.length; b++) {
/* 186 */       arrayOfLong[b] = (paramArrayOfRedaction[b]).a;
/*     */     }
/* 188 */     long l1 = 0L;
/* 189 */     if (paramAppearance.font != null) l1 = paramAppearance.font.a;
/*     */     
/* 191 */     long l2 = 0L;
/* 192 */     if (paramAppearance.positiveOverlayColor != null) l2 = paramAppearance.positiveOverlayColor.a;
/*     */     
/* 194 */     long l3 = 0L;
/* 195 */     if (paramAppearance.negativeOverlayColor != null) l3 = paramAppearance.negativeOverlayColor.a;
/*     */     
/* 197 */     long l4 = 0L;
/* 198 */     if (paramAppearance.textColor != null) l4 = paramAppearance.textColor.a;
/*     */     
/* 200 */     long l5 = 0L;
/* 201 */     if (paramAppearance.redactedContentColor != null) l5 = paramAppearance.redactedContentColor.a;
/*     */     
/* 203 */     Redact(paramDoc.__GetHandle(), arrayOfLong, paramAppearance.redactionOverlay, l2, l3, paramAppearance.border, paramAppearance.useOverlayText, l1, paramAppearance.minFontSize, paramAppearance.maxFontSize, l4, paramAppearance.horizTextAlignment, paramAppearance.vertTextAlignment, paramAppearance.showRedactedContentRegions, l5, paramBoolean1, paramBoolean2);
/*     */   }
/*     */   
/*     */   static native long RedactionCreate(int paramInt, long paramLong, boolean paramBoolean, String paramString);
/*     */   
/*     */   static native void RedactionDestroy(long paramLong);
/*     */   
/*     */   static native void Redact(long paramLong1, long[] paramArrayOflong, boolean paramBoolean1, long paramLong2, long paramLong3, boolean paramBoolean2, boolean paramBoolean3, long paramLong4, double paramDouble1, double paramDouble2, long paramLong5, int paramInt1, int paramInt2, boolean paramBoolean4, long paramLong6, boolean paramBoolean5, boolean paramBoolean6);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\Redactor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */