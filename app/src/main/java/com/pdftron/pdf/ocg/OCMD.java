/*     */ package com.pdftron.pdf.ocg;
/*     */ 
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.PDFDoc;
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
/*     */ public class OCMD
/*     */ {
/*     */   public static final int e_AllOn = 0;
/*     */   public static final int e_AnyOn = 1;
/*     */   public static final int e_AnyOff = 2;
/*     */   public static final int e_AllOff = 3;
/*     */   private long a;
/*     */   private Object b;
/*     */   
/*     */   public static OCMD create(PDFDoc paramPDFDoc, Obj paramObj, int paramInt) throws PDFNetException {
/*  54 */     return new OCMD(Create(paramPDFDoc.__GetHandle(), paramObj.__GetHandle(), paramInt), paramPDFDoc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OCMD(Obj paramObj) {
/*  65 */     this.a = paramObj.__GetHandle();
/*  66 */     this.b = paramObj.__GetRefHandle();
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
/*     */   public boolean isValid() throws PDFNetException {
/*  78 */     return IsValid(this.a);
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
/*     */   public Obj getOCGs() throws PDFNetException {
/*  91 */     return Obj.__Create(GetOCGs(this.a), this.b);
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
/*     */   public int getVisibilityPolicy() throws PDFNetException {
/* 104 */     return GetVisibilityPolicy(this.a);
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
/*     */   public void setVisibilityPolicy(int paramInt) throws PDFNetException {
/* 117 */     SetVisibilityPolicy(this.a, paramInt);
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
/*     */   public Obj getVisibilityExpression() throws PDFNetException {
/* 130 */     return Obj.__Create(GetVisibilityExpression(this.a), this.b);
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
/*     */   public Obj getSDFObj() throws PDFNetException {
/* 159 */     return Obj.__Create(this.a, this.b);
/*     */   }
/*     */ 
/*     */   
/*     */   public static OCMD __Create(long paramLong, Object paramObject) {
/* 164 */     return new OCMD(paramLong, paramObject);
/*     */   }
/*     */   
/*     */   private OCMD(long paramLong, Object paramObject) {
/* 168 */     this.a = paramLong;
/* 169 */     this.b = paramObject;
/*     */   }
/*     */   
/*     */   static native long Create(long paramLong1, long paramLong2, int paramInt);
/*     */   
/*     */   static native boolean IsValid(long paramLong);
/*     */   
/*     */   static native long GetOCGs(long paramLong);
/*     */   
/*     */   static native int GetVisibilityPolicy(long paramLong);
/*     */   
/*     */   static native void SetVisibilityPolicy(long paramLong, int paramInt);
/*     */   
/*     */   static native long GetVisibilityExpression(long paramLong);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\ocg\OCMD.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */