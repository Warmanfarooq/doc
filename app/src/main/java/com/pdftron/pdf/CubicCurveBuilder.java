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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CubicCurveBuilder
/*     */ {
/*     */   private long a;
/*     */   
/*     */   public void destroy() throws PDFNetException {
/*  33 */     if (this.a != 0L) {
/*     */       
/*  35 */       Destroy(this.a);
/*  36 */       this.a = 0L;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finalize() throws Throwable {
/*  45 */     destroy();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CubicCurveBuilder(long paramLong) {
/*  53 */     this.a = paramLong;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int numSourcePoints() throws PDFNetException {
/*  64 */     return NumSourcePoints(this.a);
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
/*     */   public void addSourcePoint(double paramDouble1, double paramDouble2) throws PDFNetException {
/*  76 */     AddSourcePoint(this.a, paramDouble1, paramDouble2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int numCubicPoints() throws PDFNetException {
/*  87 */     return NumCubicPoints(this.a);
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
/*     */   public double getCubicXCoord(int paramInt) throws PDFNetException {
/*  99 */     return GetCubicXCoord(this.a, paramInt);
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
/*     */   public double getCubicYCoord(int paramInt) throws PDFNetException {
/* 111 */     return GetCubicYCoord(this.a, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long __GetHandle() {
/* 118 */     return this.a;
/*     */   }
/*     */   
/*     */   static native void Destroy(long paramLong);
/*     */   
/*     */   static native int NumSourcePoints(long paramLong);
/*     */   
/*     */   static native void AddSourcePoint(long paramLong, double paramDouble1, double paramDouble2);
/*     */   
/*     */   static native int NumCubicPoints(long paramLong);
/*     */   
/*     */   static native double GetCubicXCoord(long paramLong, int paramInt);
/*     */   
/*     */   static native double GetCubicYCoord(long paramLong, int paramInt);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\CubicCurveBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */