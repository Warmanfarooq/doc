/*     */ package com.pdftron.sdf;
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
/*     */ public class ObjSet
/*     */ {
/*  15 */   private long a = Create();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void destroy() {
/*  26 */     if (this.a != 0L) {
/*     */       
/*  28 */       Destroy(this.a);
/*  29 */       this.a = 0L;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finalize() throws Throwable {
/*  38 */     destroy();
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
/*     */   public Obj createName(String paramString) throws PDFNetException {
/*  50 */     return Obj.__Create(CreateName(this.a, paramString), this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Obj createArray() throws PDFNetException {
/*  61 */     return Obj.__Create(CreateArray(this.a), this);
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
/*     */   public Obj createBool(boolean paramBoolean) throws PDFNetException {
/*  73 */     return Obj.__Create(CreateBool(this.a, paramBoolean), this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Obj createDict() throws PDFNetException {
/*  84 */     return Obj.__Create(CreateDict(this.a), this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Obj createNull() throws PDFNetException {
/*  95 */     return Obj.__Create(CreateNull(this.a), this);
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
/*     */   public Obj createNumber(double paramDouble) throws PDFNetException {
/* 107 */     return Obj.__Create(CreateNumber(this.a, paramDouble), this);
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
/*     */   public Obj createString(String paramString) throws PDFNetException {
/* 119 */     return Obj.__Create(CreateString(this.a, paramString), this);
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
/*     */   public Obj createFromJson(String paramString) throws PDFNetException {
/* 131 */     return Obj.__Create(CreateFromJson(this.a, paramString), this);
/*     */   }
/*     */   
/*     */   static native long Create();
/*     */   
/*     */   static native void Destroy(long paramLong);
/*     */   
/*     */   static native long CreateName(long paramLong, String paramString);
/*     */   
/*     */   static native long CreateArray(long paramLong);
/*     */   
/*     */   static native long CreateBool(long paramLong, boolean paramBoolean);
/*     */   
/*     */   static native long CreateDict(long paramLong);
/*     */   
/*     */   static native long CreateNull(long paramLong);
/*     */   
/*     */   static native long CreateNumber(long paramLong, double paramDouble);
/*     */   
/*     */   static native long CreateString(long paramLong, String paramString);
/*     */   
/*     */   static native long CreateFromJson(long paramLong, String paramString);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\sdf\ObjSet.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */