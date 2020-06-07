/*     */ package com.pdftron.pdf;
/*     */ 
/*     */ import com.pdftron.common.Matrix2D;
/*     */ import com.pdftron.common.PDFNetException;
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
/*     */ public class PatternColor
/*     */ {
/*     */   public static final int e_uncolored_tiling_pattern = 0;
/*     */   public static final int e_colored_tiling_pattern = 1;
/*     */   public static final int e_shading = 2;
/*     */   public static final int e_null = 3;
/*     */   public static final int e_constant_spacing = 0;
/*     */   public static final int e_no_distortion = 1;
/*     */   public static final int e_constant_spacing_fast_fill = 2;
/*     */   long a;
/*     */   private Object b;
/*     */   
/*     */   public PatternColor(Obj paramObj) {
/*  43 */     this.a = paramObj.__GetHandle();
/*  44 */     this.b = paramObj.__GetRefHandle();
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
/*     */   public static int getType(Obj paramObj) throws PDFNetException {
/*  69 */     return GetTypeObj(paramObj.__GetHandle());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getType() throws PDFNetException {
/*  80 */     return GetType(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Obj getSDFObj() throws PDFNetException {
/*  91 */     return Obj.__Create(this.a, this.b);
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
/*     */   public Matrix2D getMatrix() throws PDFNetException {
/* 107 */     return Matrix2D.__Create(GetMatrix(this.a));
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
/*     */   public Shading getShading() throws PDFNetException {
/* 121 */     return Shading.a(GetShading(this.a), this.b);
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
/*     */   public int getTilingType() throws PDFNetException {
/* 146 */     return GetTilingType(this.a);
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
/*     */   public Rect getBBox() throws PDFNetException {
/* 162 */     return new Rect(GetBBox(this.a));
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
/*     */   public double getXStep() throws PDFNetException {
/* 180 */     return GetXStep(this.a);
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
/*     */   public double getYStep() throws PDFNetException {
/* 194 */     return GetYStep(this.a);
/*     */   }
/*     */ 
/*     */   
/*     */   static PatternColor a(long paramLong, Object paramObject) {
/* 199 */     if (paramLong != 0L)
/*     */     {
/* 201 */       return new PatternColor(paramLong, paramObject);
/*     */     }
/*     */ 
/*     */     
/* 205 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private PatternColor(long paramLong, Object paramObject) {
/* 210 */     this.a = paramLong;
/* 211 */     this.b = paramObject;
/*     */   }
/*     */   
/*     */   static native int GetTypeObj(long paramLong);
/*     */   
/*     */   static native int GetType(long paramLong);
/*     */   
/*     */   static native long GetMatrix(long paramLong);
/*     */   
/*     */   static native long GetShading(long paramLong);
/*     */   
/*     */   static native int GetTilingType(long paramLong);
/*     */   
/*     */   static native long GetBBox(long paramLong);
/*     */   
/*     */   static native double GetXStep(long paramLong);
/*     */   
/*     */   static native double GetYStep(long paramLong);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\PatternColor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */