/*     */ package com.pdftron.pdf;
/*     */ 
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.helpers.DoubleRectangle2D;
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
/*     */ public class Rect
/*     */ {
/*     */   long a;
/*     */   
/*     */   public Rect() throws PDFNetException {
/*  30 */     this.a = RectCreate(0.0D, 0.0D, 0.0D, 0.0D);
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
/*     */   public Rect(Obj paramObj) throws PDFNetException {
/*  42 */     this.a = RectCreate(paramObj.__GetHandle());
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
/*     */   public Rect(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) throws PDFNetException {
/*  61 */     this.a = RectCreate(paramDouble1, paramDouble2, paramDouble3, paramDouble4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rect(DoubleRectangle2D paramDoubleRectangle2D) throws PDFNetException {
/*  72 */     this.a = RectCreate(paramDoubleRectangle2D.x, paramDoubleRectangle2D.y, paramDoubleRectangle2D.x + paramDoubleRectangle2D.width, paramDoubleRectangle2D.y + paramDoubleRectangle2D.height);
/*     */   }
/*     */   
/*     */   Rect(long paramLong) {
/*  76 */     this.a = paramLong;
/*     */   }
/*     */   
/*     */   public static Rect __Create(long paramLong) {
/*  80 */     if (paramLong == 0L) return null; 
/*  81 */     return new Rect(paramLong);
/*     */   }
/*     */   
/*     */   public long __GetHandle() {
/*  85 */     return this.a;
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject) {
/*  89 */     if (paramObject != null && paramObject.getClass().equals(getClass()))
/*  90 */       return Equals(this.a, ((Rect)paramObject).a); 
/*  91 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  96 */     return HashCode(this.a);
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
/*     */   public void attach(Obj paramObj) throws PDFNetException {
/* 108 */     Attach(this.a, paramObj.__GetHandle());
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
/*     */   public boolean update() throws PDFNetException {
/* 120 */     return Update(this.a, 0L);
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
/*     */   public boolean update(Obj paramObj) throws PDFNetException {
/* 136 */     return Update(this.a, paramObj.__GetHandle());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] get() throws PDFNetException {
/* 146 */     return Get(this.a);
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
/*     */   public void set(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) throws PDFNetException {
/* 164 */     Set(this.a, paramDouble1, paramDouble2, paramDouble3, paramDouble4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(DoubleRectangle2D paramDoubleRectangle2D) throws PDFNetException {
/* 175 */     Set(this.a, paramDoubleRectangle2D.x, paramDoubleRectangle2D.y, paramDoubleRectangle2D.x + paramDoubleRectangle2D.width, paramDoubleRectangle2D.y + paramDoubleRectangle2D.height);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getWidth() throws PDFNetException {
/* 185 */     return Width(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getHeight() throws PDFNetException {
/* 195 */     return Height(this.a);
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
/*     */   public boolean contains(double paramDouble1, double paramDouble2) throws PDFNetException {
/* 210 */     return Contains(this.a, paramDouble1, paramDouble2);
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
/*     */   public boolean intersectRect(Rect paramRect1, Rect paramRect2) throws PDFNetException {
/* 228 */     return IntersectRect(this.a, paramRect1.a, paramRect2.a);
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
/*     */   public void normalize() throws PDFNetException {
/* 242 */     Normalize(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void inflate(double paramDouble) {
/* 252 */     Inflate(this.a, paramDouble);
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
/*     */   public void inflate(double paramDouble1, double paramDouble2) {
/* 266 */     Inflate(this.a, paramDouble1, paramDouble2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void destroy() {
/* 276 */     if (this.a != 0L) {
/* 277 */       Destroy(this.a);
/* 278 */       this.a = 0L;
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
/*     */   public DoubleRectangle2D getRectangle() throws PDFNetException {
/* 290 */     double[] arrayOfDouble = Get(this.a);
/* 291 */     return new DoubleRectangle2D(arrayOfDouble[0], arrayOfDouble[1], arrayOfDouble[2] - arrayOfDouble[0], arrayOfDouble[3] - arrayOfDouble[1]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getX1() throws PDFNetException {
/* 301 */     return GetX1(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getY1() throws PDFNetException {
/* 311 */     return GetY1(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getX2() throws PDFNetException {
/* 321 */     return GetX2(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getY2() throws PDFNetException {
/* 331 */     return GetY2(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setX1(double paramDouble) throws PDFNetException {
/* 342 */     SetX1(this.a, paramDouble);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setY1(double paramDouble) throws PDFNetException {
/* 353 */     SetY1(this.a, paramDouble);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setX2(double paramDouble) throws PDFNetException {
/* 364 */     SetX2(this.a, paramDouble);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setY2(double paramDouble) throws PDFNetException {
/* 375 */     SetY2(this.a, paramDouble);
/*     */   }
/*     */   
/*     */   protected void finalize() throws Throwable {
/* 379 */     destroy();
/*     */   }
/*     */   
/*     */   static native long RectCreate(long paramLong);
/*     */   
/*     */   static native long RectCreate(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4);
/*     */   
/*     */   static native boolean Equals(long paramLong1, long paramLong2);
/*     */   
/*     */   static native int HashCode(long paramLong);
/*     */   
/*     */   static native void Attach(long paramLong1, long paramLong2);
/*     */   
/*     */   static native boolean Update(long paramLong1, long paramLong2);
/*     */   
/*     */   static native double[] Get(long paramLong);
/*     */   
/*     */   static native void Set(long paramLong, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4);
/*     */   
/*     */   static native double Width(long paramLong);
/*     */   
/*     */   static native double Height(long paramLong);
/*     */   
/*     */   static native boolean Contains(long paramLong, double paramDouble1, double paramDouble2);
/*     */   
/*     */   static native boolean IntersectRect(long paramLong1, long paramLong2, long paramLong3);
/*     */   
/*     */   static native void Normalize(long paramLong);
/*     */   
/*     */   static native void Inflate(long paramLong, double paramDouble);
/*     */   
/*     */   static native void Inflate(long paramLong, double paramDouble1, double paramDouble2);
/*     */   
/*     */   static native void Destroy(long paramLong);
/*     */   
/*     */   static native double GetX1(long paramLong);
/*     */   
/*     */   static native double GetY1(long paramLong);
/*     */   
/*     */   static native double GetX2(long paramLong);
/*     */   
/*     */   static native double GetY2(long paramLong);
/*     */   
/*     */   static native void SetX1(long paramLong, double paramDouble);
/*     */   
/*     */   static native void SetY1(long paramLong, double paramDouble);
/*     */   
/*     */   static native void SetX2(long paramLong, double paramDouble);
/*     */   
/*     */   static native void SetY2(long paramLong, double paramDouble);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\Rect.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */