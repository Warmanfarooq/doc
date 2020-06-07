/*     */ package com.pdftron.common;
/*     */ 
/*     */ import com.pdftron.pdf.Point;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Matrix2D
/*     */ {
/*     */   private long a;
/*     */   
/*     */   public double getA() throws PDFNetException {
/* 110 */     return getA(this.a);
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
/*     */   public void setA(double paramDouble) throws PDFNetException {
/* 125 */     setA(this.a, paramDouble);
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
/*     */   public double getB() throws PDFNetException {
/* 140 */     return getB(this.a);
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
/*     */   public void setB(double paramDouble) throws PDFNetException {
/* 155 */     setB(this.a, paramDouble);
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
/*     */   public double getC() throws PDFNetException {
/* 170 */     return getC(this.a);
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
/*     */   public void setC(double paramDouble) throws PDFNetException {
/* 185 */     setC(this.a, paramDouble);
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
/*     */   public double getD() throws PDFNetException {
/* 200 */     return getD(this.a);
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
/*     */   public void setD(double paramDouble) throws PDFNetException {
/* 215 */     setD(this.a, paramDouble);
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
/*     */   public double getH() throws PDFNetException {
/* 230 */     return getH(this.a);
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
/*     */   public void setH(double paramDouble) throws PDFNetException {
/* 245 */     setH(this.a, paramDouble);
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
/*     */   public double getV() throws PDFNetException {
/* 260 */     return getV(this.a);
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
/*     */   public void setV(double paramDouble) throws PDFNetException {
/* 275 */     setV(this.a, paramDouble);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix2D() throws PDFNetException {
/* 284 */     this.a = Matrix2DCreate(1.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D);
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
/*     */   public Matrix2D(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6) throws PDFNetException {
/* 300 */     this.a = Matrix2DCreate(paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6);
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
/*     */   public void set(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6) throws PDFNetException {
/* 315 */     Set(this.a, paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6);
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
/*     */   public void concat(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6) throws PDFNetException {
/* 331 */     Concat(this.a, paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix2D multiply(Matrix2D paramMatrix2D) throws PDFNetException {
/* 342 */     return new Matrix2D(Multiply(this.a, paramMatrix2D.a));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 353 */     if (paramObject != null && paramObject.getClass().equals(getClass()))
/* 354 */       return Equals(this.a, ((Matrix2D)paramObject).a); 
/* 355 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 363 */     return HashCode(this.a);
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
/*     */   public Point multPoint(double paramDouble1, double paramDouble2) throws PDFNetException {
/* 375 */     double[] arrayOfDouble = Mult(this.a, paramDouble1, paramDouble2);
/* 376 */     return new Point(arrayOfDouble[0], arrayOfDouble[1]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix2D inverse() throws PDFNetException {
/* 386 */     return new Matrix2D(Inverse(this.a));
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
/*     */   public Matrix2D translate(double paramDouble1, double paramDouble2) throws PDFNetException {
/* 399 */     Translate(this.a, paramDouble1, paramDouble2);
/* 400 */     return this;
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
/*     */   public Matrix2D scale(double paramDouble1, double paramDouble2) throws PDFNetException {
/* 413 */     Scale(this.a, paramDouble1, paramDouble2);
/* 414 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Matrix2D zeroMatrix() throws PDFNetException {
/* 424 */     return new Matrix2D(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Matrix2D identityMatrix() throws PDFNetException {
/* 434 */     return new Matrix2D();
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
/*     */   public static Matrix2D rotationMatrix(double paramDouble) throws PDFNetException {
/* 446 */     return new Matrix2D(RotationMatrix(paramDouble));
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
/*     */   public void destroy() throws PDFNetException {
/* 458 */     if (this.a != 0L) {
/* 459 */       Destroy(this.a);
/* 460 */       this.a = 0L;
/*     */     } 
/*     */   }
/*     */   
/*     */   private Matrix2D(long paramLong) {
/* 465 */     this.a = paramLong;
/*     */   }
/*     */   
/*     */   public static Matrix2D __Create(long paramLong) {
/* 469 */     return new Matrix2D(paramLong);
/*     */   }
/*     */   
/*     */   public long __GetHandle() {
/* 473 */     return this.a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finalize() throws Throwable {
/* 480 */     destroy();
/*     */   }
/*     */   
/*     */   static native long Matrix2DCreate(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6);
/*     */   
/*     */   static native void Set(long paramLong, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6);
/*     */   
/*     */   static native void Concat(long paramLong, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6);
/*     */   
/*     */   static native long Multiply(long paramLong1, long paramLong2);
/*     */   
/*     */   static native boolean Equals(long paramLong1, long paramLong2);
/*     */   
/*     */   static native int HashCode(long paramLong);
/*     */   
/*     */   static native double[] Mult(long paramLong, double paramDouble1, double paramDouble2);
/*     */   
/*     */   static native long Inverse(long paramLong);
/*     */   
/*     */   static native void Translate(long paramLong, double paramDouble1, double paramDouble2);
/*     */   
/*     */   static native void Scale(long paramLong, double paramDouble1, double paramDouble2);
/*     */   
/*     */   static native long RotationMatrix(double paramDouble);
/*     */   
/*     */   static native double getA(long paramLong);
/*     */   
/*     */   static native void setA(long paramLong, double paramDouble);
/*     */   
/*     */   static native double getB(long paramLong);
/*     */   
/*     */   static native void setB(long paramLong, double paramDouble);
/*     */   
/*     */   static native double getC(long paramLong);
/*     */   
/*     */   static native void setC(long paramLong, double paramDouble);
/*     */   
/*     */   static native double getD(long paramLong);
/*     */   
/*     */   static native void setD(long paramLong, double paramDouble);
/*     */   
/*     */   static native double getH(long paramLong);
/*     */   
/*     */   static native void setH(long paramLong, double paramDouble);
/*     */   
/*     */   static native double getV(long paramLong);
/*     */   
/*     */   static native void setV(long paramLong, double paramDouble);
/*     */   
/*     */   static native void Destroy(long paramLong);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\common\Matrix2D.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */