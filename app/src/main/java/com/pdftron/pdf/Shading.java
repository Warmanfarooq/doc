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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Shading
/*     */ {
/*     */   public static final int e_function_shading = 0;
/*     */   public static final int e_axial_shading = 1;
/*     */   public static final int e_radial_shading = 2;
/*     */   public static final int e_free_gouraud_shading = 3;
/*     */   public static final int e_lattice_gouraud_shading = 4;
/*     */   public static final int e_coons_shading = 5;
/*     */   public static final int e_tensor_shading = 6;
/*     */   public static final int e_null = 7;
/*     */   long a;
/*     */   Object b;
/*     */   
/*     */   public Shading(Obj paramObj) {
/*  63 */     this.a = paramObj.__GetHandle();
/*  64 */     this.b = paramObj.__GetRefHandle();
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
/*     */ 
/*     */ 
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
/* 105 */     return GetTypeStatic(paramObj.__GetHandle());
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
/*     */   public int getType() throws PDFNetException {
/* 118 */     return GetType(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Obj getSDFObj() {
/* 127 */     return Obj.__Create(this.a, this.b);
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
/*     */   public ColorSpace getBaseColorSpace() throws PDFNetException {
/* 139 */     return ColorSpace.__Create(GetBaseColorSpace(this.a), this.b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasBBox() throws PDFNetException {
/* 149 */     return HasBBox(this.a);
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
/*     */   public Rect getBBox() throws PDFNetException {
/* 167 */     return new Rect(GetBBox(this.a));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasBackground() throws PDFNetException {
/* 177 */     return HasBackground(this.a);
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
/*     */   public ColorPt getBackground() throws PDFNetException {
/* 199 */     return new ColorPt(GetBackground(this.a));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getAntialias() throws PDFNetException {
/* 210 */     return GetAntialias(this.a);
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
/*     */   public double getParamStart() throws PDFNetException {
/* 232 */     return GetParamStart(this.a);
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
/*     */   public double getParamEnd() throws PDFNetException {
/* 251 */     return GetParamEnd(this.a);
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
/*     */   public boolean isExtendStart() throws PDFNetException {
/* 267 */     return IsExtendStart(this.a);
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
/*     */   public boolean isExtendEnd() throws PDFNetException {
/* 283 */     return IsExtendEnd(this.a);
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
/*     */   public ColorPt getColor(double paramDouble) throws PDFNetException {
/* 298 */     return new ColorPt(GetColor(this.a, paramDouble));
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
/*     */   public double[] getCoords() throws PDFNetException {
/* 316 */     return GetCoords(this.a);
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
/*     */   public double[] getCoordsRadial() throws PDFNetException {
/* 336 */     return GetCoordsRadial(this.a);
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
/*     */   public double[] getDomain() throws PDFNetException {
/* 354 */     return GetDomain(this.a);
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
/*     */   public Matrix2D getMatrix() throws PDFNetException {
/* 368 */     return Matrix2D.__Create(GetMatrix(this.a));
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
/*     */   public ColorPt getColor(double paramDouble1, double paramDouble2) throws PDFNetException {
/* 385 */     return new ColorPt(GetColor(this.a, paramDouble1, paramDouble2));
/*     */   }
/*     */   
/*     */   private Shading(long paramLong, Object paramObject) {
/* 389 */     this.a = paramLong;
/* 390 */     this.b = paramObject;
/*     */   }
/*     */   
/*     */   static Shading a(long paramLong, Object paramObject) {
/* 394 */     if (paramLong == 0L) {
/* 395 */       return null;
/*     */     }
/* 397 */     return new Shading(paramLong, paramObject);
/*     */   }
/*     */   
/*     */   static native int GetTypeStatic(long paramLong);
/*     */   
/*     */   static native int GetType(long paramLong);
/*     */   
/*     */   static native long GetBaseColorSpace(long paramLong);
/*     */   
/*     */   static native boolean HasBBox(long paramLong);
/*     */   
/*     */   static native long GetBBox(long paramLong);
/*     */   
/*     */   static native boolean HasBackground(long paramLong);
/*     */   
/*     */   static native long GetBackground(long paramLong);
/*     */   
/*     */   static native boolean GetAntialias(long paramLong);
/*     */   
/*     */   static native double GetParamStart(long paramLong);
/*     */   
/*     */   static native double GetParamEnd(long paramLong);
/*     */   
/*     */   static native boolean IsExtendStart(long paramLong);
/*     */   
/*     */   static native boolean IsExtendEnd(long paramLong);
/*     */   
/*     */   static native long GetColor(long paramLong, double paramDouble);
/*     */   
/*     */   static native double[] GetCoords(long paramLong);
/*     */   
/*     */   static native double[] GetCoordsRadial(long paramLong);
/*     */   
/*     */   static native double[] GetDomain(long paramLong);
/*     */   
/*     */   static native long GetMatrix(long paramLong);
/*     */   
/*     */   static native long GetColor(long paramLong, double paramDouble1, double paramDouble2);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\Shading.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */