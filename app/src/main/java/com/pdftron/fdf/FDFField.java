/*     */ package com.pdftron.fdf;
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
/*     */ public class FDFField
/*     */ {
/*     */   private long a;
/*     */   private Object b;
/*     */   
/*     */   public FDFField(Obj paramObj1, Obj paramObj2) throws PDFNetException {
/*  18 */     this.a = FDFFieldCreate(paramObj1.__GetHandle(), paramObj2.__GetHandle());
/*  19 */     this.b = paramObj1.__GetRefHandle();
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
/*  32 */     if (this.a != 0L) {
/*     */       
/*  34 */       Destroy(this.a);
/*  35 */       this.a = 0L;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void finalize() throws Throwable {
/*  41 */     destroy();
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
/*     */   public Obj getValue() throws PDFNetException {
/*  54 */     return Obj.__Create(GetValue(this.a), this.b);
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
/*     */   public void setValue(Obj paramObj) throws PDFNetException {
/*  66 */     SetValue(this.a, paramObj.__GetHandle());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() throws PDFNetException {
/*  77 */     return GetName(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPartialName() throws PDFNetException {
/*  88 */     return GetPartialName(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Obj getSDFObj() throws PDFNetException {
/*  98 */     return Obj.__Create(GetSDFObj(this.a), this.b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Obj findAttribute(String paramString) throws PDFNetException {
/* 109 */     return Obj.__Create(FindAttribute(this.a, paramString), this.b);
/*     */   }
/*     */ 
/*     */   
/*     */   FDFField(long paramLong, Object paramObject) {
/* 114 */     this.a = paramLong;
/* 115 */     this.b = paramObject;
/*     */   }
/*     */   
/*     */   static native long FDFFieldCreate(long paramLong1, long paramLong2);
/*     */   
/*     */   static native void Destroy(long paramLong);
/*     */   
/*     */   static native long GetValue(long paramLong);
/*     */   
/*     */   static native void SetValue(long paramLong1, long paramLong2);
/*     */   
/*     */   static native String GetName(long paramLong);
/*     */   
/*     */   static native String GetPartialName(long paramLong);
/*     */   
/*     */   static native long GetSDFObj(long paramLong);
/*     */   
/*     */   static native long FindAttribute(long paramLong, String paramString);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\fdf\FDFField.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */