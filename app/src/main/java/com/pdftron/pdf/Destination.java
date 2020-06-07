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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Destination
/*     */ {
/*     */   public static final int e_XYZ = 0;
/*     */   public static final int e_Fit = 1;
/*     */   public static final int e_FitH = 2;
/*     */   public static final int e_FitV = 3;
/*     */   public static final int e_FitR = 4;
/*     */   public static final int e_FitB = 5;
/*     */   public static final int e_FitBH = 6;
/*     */   public static final int e_FitBV = 7;
/*     */   long a;
/*     */   Object b;
/*     */   
/*     */   public static Destination createXYZ(Page paramPage, double paramDouble1, double paramDouble2, double paramDouble3) throws PDFNetException {
/*  76 */     return new Destination(CreateXYZ(paramPage.a, paramDouble1, paramDouble2, paramDouble3), paramPage.b);
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
/*     */   public static Destination createFit(Page paramPage) throws PDFNetException {
/*  94 */     return new Destination(CreateFit(paramPage.a), paramPage.b);
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
/*     */   public static Destination createFitH(Page paramPage, double paramDouble) throws PDFNetException {
/* 112 */     return new Destination(CreateFitH(paramPage.a, paramDouble), paramPage.b);
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
/*     */   public static Destination createFitV(Page paramPage, double paramDouble) throws PDFNetException {
/* 130 */     return new Destination(CreateFitV(paramPage.a, paramDouble), paramPage.b);
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
/*     */   public static Destination createFitR(Page paramPage, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) throws PDFNetException {
/* 155 */     return new Destination(CreateFitR(paramPage.a, paramDouble1, paramDouble2, paramDouble3, paramDouble4), paramPage.b);
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
/*     */   public static Destination createFitB(Page paramPage) throws PDFNetException {
/* 174 */     return new Destination(CreateFitB(paramPage.a), paramPage.b);
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
/*     */   public static Destination createFitBH(Page paramPage, double paramDouble) throws PDFNetException {
/* 192 */     return new Destination(CreateFitBH(paramPage.a, paramDouble), paramPage.b);
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
/*     */   public static Destination createFitBV(Page paramPage, double paramDouble) throws PDFNetException {
/* 210 */     return new Destination(CreateFitBV(paramPage.a, paramDouble), paramPage.b);
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
/*     */   public Destination(Obj paramObj) {
/* 231 */     this.a = Create(paramObj.__GetHandle());
/* 232 */     this.b = paramObj.__GetRefHandle();
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
/*     */   public boolean isValid() throws PDFNetException {
/* 247 */     return IsValid(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFitType() throws PDFNetException {
/* 258 */     return GetFitType(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Page getPage() throws PDFNetException {
/* 269 */     return new Page(GetPage(this.a), this.b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPage(Page paramPage) throws PDFNetException {
/* 280 */     SetPage(this.a, paramPage.a);
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
/*     */   public Obj getSDFObj() {
/* 295 */     return Obj.__Create(this.a, this.b);
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
/*     */   public Obj getExplicitDestObj() throws PDFNetException {
/* 307 */     return Obj.__Create(GetExplicitDestObj(this.a), this.b);
/*     */   }
/*     */ 
/*     */   
/*     */   Destination(long paramLong, Object paramObject) {
/* 312 */     this.a = paramLong;
/* 313 */     this.b = paramObject;
/*     */   }
/*     */   
/*     */   static native long CreateXYZ(long paramLong, double paramDouble1, double paramDouble2, double paramDouble3);
/*     */   
/*     */   static native long CreateFit(long paramLong);
/*     */   
/*     */   static native long CreateFitH(long paramLong, double paramDouble);
/*     */   
/*     */   static native long CreateFitV(long paramLong, double paramDouble);
/*     */   
/*     */   static native long CreateFitR(long paramLong, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4);
/*     */   
/*     */   static native long CreateFitB(long paramLong);
/*     */   
/*     */   static native long CreateFitBH(long paramLong, double paramDouble);
/*     */   
/*     */   static native long CreateFitBV(long paramLong, double paramDouble);
/*     */   
/*     */   static native long Create(long paramLong);
/*     */   
/*     */   static native boolean IsValid(long paramLong);
/*     */   
/*     */   static native int GetFitType(long paramLong);
/*     */   
/*     */   static native long GetPage(long paramLong);
/*     */   
/*     */   static native void SetPage(long paramLong1, long paramLong2);
/*     */   
/*     */   static native long GetExplicitDestObj(long paramLong);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\Destination.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */