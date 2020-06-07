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
/*     */ public class ColorPt
/*     */ {
/*     */   long a;
/*     */   
/*     */   public ColorPt() throws PDFNetException {
/*  19 */     this.a = ColorPtCreate(0.0D, 0.0D, 0.0D, 0.0D);
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
/*     */   public ColorPt(double paramDouble1, double paramDouble2, double paramDouble3) throws PDFNetException {
/*  32 */     this.a = ColorPtCreate(paramDouble1, paramDouble2, paramDouble3, 1.0D);
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
/*     */   public ColorPt(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) throws PDFNetException {
/*  46 */     this.a = ColorPtCreate(paramDouble1, paramDouble2, paramDouble3, paramDouble4);
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
/*     */   public void destroy() throws PDFNetException {
/*  59 */     if (this.a != 0L) {
/*     */       
/*  61 */       Destroy(this.a);
/*  62 */       this.a = 0L;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/*  71 */     if (paramObject != null && paramObject.getClass().equals(getClass()))
/*     */     {
/*  73 */       return Equals(this.a, ((ColorPt)paramObject).a);
/*     */     }
/*     */     
/*  76 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  85 */     return HashCode(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set() throws PDFNetException {
/*  96 */     Set(this.a, 0.0D, 0.0D, 0.0D, 0.0D);
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
/*     */   public void set(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) throws PDFNetException {
/* 110 */     Set(this.a, paramDouble1, paramDouble2, paramDouble3, paramDouble4);
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
/*     */   public void set(int paramInt, double paramDouble) throws PDFNetException {
/* 140 */     Set(this.a, paramInt, paramDouble);
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
/*     */   public double get(int paramInt) throws PDFNetException {
/* 156 */     return Get(this.a, paramInt);
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
/*     */   public void setColorantNum(int paramInt) throws PDFNetException {
/* 172 */     SetColorantNum(this.a, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finalize() throws Throwable {
/* 180 */     destroy();
/*     */   }
/*     */ 
/*     */   
/*     */   ColorPt(long paramLong) {
/* 185 */     this.a = paramLong;
/*     */   }
/*     */   
/*     */   public static ColorPt __Create(long paramLong) {
/* 189 */     if (paramLong == 0L) {
/* 190 */       return null;
/*     */     }
/* 192 */     return new ColorPt(paramLong);
/*     */   }
/*     */   
/*     */   public long __GetHandle() {
/* 196 */     return this.a;
/*     */   }
/*     */   
/*     */   static native long ColorPtCreate(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4);
/*     */   
/*     */   static native void Destroy(long paramLong);
/*     */   
/*     */   static native boolean Equals(long paramLong1, long paramLong2);
/*     */   
/*     */   static native int HashCode(long paramLong);
/*     */   
/*     */   static native void Set(long paramLong, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4);
/*     */   
/*     */   static native void Set(long paramLong, int paramInt, double paramDouble);
/*     */   
/*     */   static native double Get(long paramLong, int paramInt);
/*     */   
/*     */   static native void SetColorantNum(long paramLong, int paramInt);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\ColorPt.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */