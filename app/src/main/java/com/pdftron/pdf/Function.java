/*     */ package com.pdftron.pdf;
/*     */ 
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
/*     */ public class Function
/*     */ {
/*     */   public static final int e_sampled = 0;
/*     */   public static final int e_exponential = 2;
/*     */   public static final int e_stitching = 3;
/*     */   public static final int e_postscript = 4;
/*     */   private long a;
/*     */   private Object b;
/*     */   
/*     */   public Function(Obj paramObj) {
/*  27 */     this.a = FunctionCreateFromObj(paramObj.__GetHandle());
/*  28 */     this.b = paramObj.__GetRefHandle();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void destroy() {
/*  39 */     if (this.a != 0L) {
/*     */       
/*  41 */       Destroy(this.a);
/*  42 */       this.a = 0L;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void finalize() throws Throwable {
/*  48 */     destroy();
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
/*     */   public int getType() throws PDFNetException {
/*  73 */     return GetType(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInputCardinality() throws PDFNetException {
/*  84 */     return GetInputCardinality(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOutputCardinality() throws PDFNetException {
/*  95 */     return GetOutputCardinality(this.a);
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
/*     */   public double[] eval(double[] paramArrayOfdouble) throws PDFNetException {
/* 110 */     return Eval(this.a, paramArrayOfdouble);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Obj getSDFObj() {
/* 120 */     return Obj.__Create(GetSDFObj(this.a), this.b);
/*     */   }
/*     */ 
/*     */   
/*     */   private Function(long paramLong, Object paramObject) {
/* 125 */     this.a = paramLong;
/* 126 */     this.b = paramObject;
/*     */   }
/*     */   
/*     */   static Function a(long paramLong, Object paramObject) {
/* 130 */     if (paramLong == 0L) {
/* 131 */       return null;
/*     */     }
/* 133 */     return new Function(paramLong, paramObject);
/*     */   }
/*     */   
/*     */   static native long FunctionCreateFromObj(long paramLong);
/*     */   
/*     */   static native void Destroy(long paramLong);
/*     */   
/*     */   static native int GetType(long paramLong);
/*     */   
/*     */   static native int GetInputCardinality(long paramLong);
/*     */   
/*     */   static native int GetOutputCardinality(long paramLong);
/*     */   
/*     */   static native double[] Eval(long paramLong, double[] paramArrayOfdouble);
/*     */   
/*     */   static native long GetSDFObj(long paramLong);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\Function.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */