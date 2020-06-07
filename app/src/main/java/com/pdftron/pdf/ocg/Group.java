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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Group
/*     */ {
/*     */   long a;
/*     */   private Object b;
/*     */   
/*     */   public static Group create(PDFDoc paramPDFDoc, String paramString) throws PDFNetException {
/*  55 */     return new Group(Create(paramPDFDoc.__GetHandle(), paramString), paramPDFDoc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Group(Obj paramObj) {
/*  66 */     this.a = paramObj.__GetHandle();
/*  67 */     this.b = paramObj.__GetRefHandle();
/*     */   }
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
/*     */   public String getName() throws PDFNetException {
/*  89 */     return GetName(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setName(String paramString) throws PDFNetException {
/* 100 */     SetName(this.a, paramString);
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
/*     */   public boolean getCurrentState(Context paramContext) throws PDFNetException {
/* 113 */     return GetCurrentState(this.a, paramContext.a);
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
/*     */   public void setCurrentState(Context paramContext, boolean paramBoolean) throws PDFNetException {
/* 125 */     SetCurrentState(this.a, paramContext.a, paramBoolean);
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
/*     */   public boolean getInitialState(Config paramConfig) throws PDFNetException {
/* 142 */     return GetInitialState(this.a, paramConfig.a);
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
/*     */   public void setInitialState(Config paramConfig, boolean paramBoolean) throws PDFNetException {
/* 155 */     SetInitialState(this.a, paramConfig.a, paramBoolean);
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
/* 172 */     return Obj.__Create(GetIntent(this.a), this.b);
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
/* 184 */     SetIntent(this.a, paramObj.__GetHandle());
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
/*     */   public boolean isLocked(Config paramConfig) throws PDFNetException {
/* 197 */     return IsLocked(this.a, paramConfig.a);
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
/*     */   public void setLocked(Config paramConfig, boolean paramBoolean) throws PDFNetException {
/* 210 */     SetLocked(this.a, paramConfig.a, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasUsage() throws PDFNetException {
/* 221 */     return HasUsage(this.a);
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
/*     */   public Obj getSDFObj() throws PDFNetException {
/* 248 */     return Obj.__Create(this.a, this.b);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Group __Create(long paramLong, Object paramObject) {
/* 253 */     return new Group(paramLong, paramObject);
/*     */   }
/*     */   
/*     */   private Group(long paramLong, Object paramObject) {
/* 257 */     this.a = paramLong;
/* 258 */     this.b = paramObject;
/*     */   }
/*     */   
/*     */   static native long Create(long paramLong, String paramString);
/*     */   
/*     */   static native boolean IsValid(long paramLong);
/*     */   
/*     */   static native String GetName(long paramLong);
/*     */   
/*     */   static native void SetName(long paramLong, String paramString);
/*     */   
/*     */   static native boolean GetCurrentState(long paramLong1, long paramLong2);
/*     */   
/*     */   static native void SetCurrentState(long paramLong1, long paramLong2, boolean paramBoolean);
/*     */   
/*     */   static native boolean GetInitialState(long paramLong1, long paramLong2);
/*     */   
/*     */   static native void SetInitialState(long paramLong1, long paramLong2, boolean paramBoolean);
/*     */   
/*     */   static native long GetIntent(long paramLong);
/*     */   
/*     */   static native void SetIntent(long paramLong1, long paramLong2);
/*     */   
/*     */   static native boolean IsLocked(long paramLong1, long paramLong2);
/*     */   
/*     */   static native void SetLocked(long paramLong1, long paramLong2, boolean paramBoolean);
/*     */   
/*     */   static native boolean HasUsage(long paramLong);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\ocg\Group.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */