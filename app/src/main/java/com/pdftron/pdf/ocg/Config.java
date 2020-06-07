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
/*     */ public class Config
/*     */ {
/*     */   long a;
/*     */   Object b;
/*     */   
/*     */   public static Config create(PDFDoc paramPDFDoc, boolean paramBoolean) throws PDFNetException {
/*  37 */     return new Config(Create(paramPDFDoc.__GetHandle(), paramBoolean), paramPDFDoc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean IsValid() {
/*  46 */     return (this.a != 0L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Config(Obj paramObj) {
/*  56 */     this.a = paramObj.__GetHandle();
/*  57 */     this.b = paramObj.__GetRefHandle();
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
/*     */   public Obj getOrder() throws PDFNetException {
/*  72 */     return Obj.__Create(GetOrder(this.a), this.b);
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
/*     */   public void setOrder(Obj paramObj) throws PDFNetException {
/*  86 */     SetOrder(this.a, paramObj.__GetHandle());
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
/*     */   public String getName() throws PDFNetException {
/*  98 */     return GetName(this.a);
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
/*     */   public void setName(String paramString) throws PDFNetException {
/* 111 */     SetName(this.a, paramString);
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
/*     */   public String getCreator() throws PDFNetException {
/* 124 */     return GetCreator(this.a);
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
/*     */   public void setCreator(String paramString) throws PDFNetException {
/* 136 */     SetCreator(this.a, paramString);
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
/*     */   public String getInitBaseState() throws PDFNetException {
/* 159 */     return GetInitBaseState(this.a);
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
/*     */   public Obj getInitOnStates() throws PDFNetException {
/* 173 */     return Obj.__Create(GetInitOnStates(this.a), this.b);
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
/*     */   public Obj getInitOffStates() throws PDFNetException {
/* 187 */     return Obj.__Create(GetInitOffStates(this.a), this.b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInitBaseState(String paramString) throws PDFNetException {
/* 198 */     SetInitBaseState(this.a, paramString);
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
/*     */   public void setInitOnStates(Obj paramObj) throws PDFNetException {
/* 210 */     SetInitOnStates(this.a, paramObj.__GetHandle());
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
/*     */   public void setInitOffStates(Obj paramObj) throws PDFNetException {
/* 222 */     SetInitOffStates(this.a, paramObj.__GetHandle());
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
/*     */   public Obj getIntent() throws PDFNetException {
/* 239 */     return Obj.__Create(GetIntent(this.a), this.b);
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
/*     */   public void setIntent(Obj paramObj) throws PDFNetException {
/* 251 */     SetIntent(this.a, paramObj.__GetHandle());
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
/*     */   public Obj getLockedOCGs() throws PDFNetException {
/* 264 */     return Obj.__Create(GetLockedOCGs(this.a), this.b);
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
/*     */   public void setLockedOCGs(Obj paramObj) throws PDFNetException {
/* 278 */     SetLockedOCGs(this.a, paramObj.__GetHandle());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Obj getSDFObj() throws PDFNetException {
/* 289 */     return Obj.__Create(this.a, this.b);
/*     */   }
/*     */ 
/*     */   
/*     */   private Config(long paramLong, Object paramObject) {
/* 294 */     this.a = paramLong;
/* 295 */     this.b = paramObject;
/*     */   }
/*     */   
/*     */   public static Config __Create(long paramLong, Object paramObject) {
/* 299 */     if (paramLong == 0L) return null; 
/* 300 */     return new Config(paramLong, paramObject);
/*     */   }
/*     */   
/*     */   static native long Create(long paramLong, boolean paramBoolean);
/*     */   
/*     */   static native long GetOrder(long paramLong);
/*     */   
/*     */   static native void SetOrder(long paramLong1, long paramLong2);
/*     */   
/*     */   static native String GetName(long paramLong);
/*     */   
/*     */   static native void SetName(long paramLong, String paramString);
/*     */   
/*     */   static native String GetCreator(long paramLong);
/*     */   
/*     */   static native void SetCreator(long paramLong, String paramString);
/*     */   
/*     */   static native String GetInitBaseState(long paramLong);
/*     */   
/*     */   static native long GetInitOnStates(long paramLong);
/*     */   
/*     */   static native long GetInitOffStates(long paramLong);
/*     */   
/*     */   static native void SetInitBaseState(long paramLong, String paramString);
/*     */   
/*     */   static native void SetInitOnStates(long paramLong1, long paramLong2);
/*     */   
/*     */   static native void SetInitOffStates(long paramLong1, long paramLong2);
/*     */   
/*     */   static native long GetIntent(long paramLong);
/*     */   
/*     */   static native void SetIntent(long paramLong1, long paramLong2);
/*     */   
/*     */   static native long GetLockedOCGs(long paramLong);
/*     */   
/*     */   static native void SetLockedOCGs(long paramLong1, long paramLong2);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\ocg\Config.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */