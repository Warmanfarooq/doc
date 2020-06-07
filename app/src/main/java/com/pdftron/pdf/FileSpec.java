/*     */ package com.pdftron.pdf;
/*     */ 
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.filters.Filter;
/*     */ import com.pdftron.sdf.Doc;
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
/*     */ public class FileSpec
/*     */ {
/*     */   long a;
/*     */   Object b;
/*     */   
/*     */   public static FileSpec create(Doc paramDoc, String paramString) throws PDFNetException {
/*  43 */     return new FileSpec(Create(paramDoc.__GetHandle(), paramString, true), paramDoc);
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
/*     */   public static FileSpec create(Doc paramDoc, String paramString, boolean paramBoolean) throws PDFNetException {
/*  59 */     return new FileSpec(Create(paramDoc.__GetHandle(), paramString, paramBoolean), paramDoc);
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
/*     */   public static FileSpec createURL(Doc paramDoc, String paramString) throws PDFNetException {
/*  74 */     return new FileSpec(CreateURL(paramDoc.__GetHandle(), paramString), paramDoc);
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
/*     */   public FileSpec(Obj paramObj) {
/*  87 */     this.a = paramObj.__GetHandle();
/*  88 */     this.b = paramObj.__GetRefHandle();
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
/*     */   public boolean equals(Object paramObject) {
/* 101 */     if (paramObject != null && paramObject.getClass().equals(getClass()))
/*     */     {
/* 103 */       return (this.a == ((FileSpec)paramObject).a);
/*     */     }
/*     */     
/* 106 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 111 */     return (int)this.a;
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
/*     */   public boolean isValid() throws PDFNetException {
/* 124 */     return IsValid(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean export() throws PDFNetException {
/* 135 */     return Export(this.a, "");
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
/*     */   public boolean export(String paramString) throws PDFNetException {
/* 149 */     return Export(this.a, paramString);
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
/*     */   public Filter getFileData() throws PDFNetException {
/* 164 */     return Filter.__Create(GetFileData(this.a), null);
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
/*     */   public String getFilePath() throws PDFNetException {
/* 181 */     return GetFilePath(this.a);
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
/*     */   public void setDesc(String paramString) throws PDFNetException {
/* 193 */     SetDesc(this.a, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Obj getSDFObj() {
/* 203 */     return Obj.__Create(this.a, this.b);
/*     */   }
/*     */ 
/*     */   
/*     */   private FileSpec(long paramLong, Object paramObject) {
/* 208 */     this.a = paramLong;
/* 209 */     this.b = paramObject;
/*     */   }
/*     */   
/*     */   public static FileSpec __Create(long paramLong, Object paramObject) {
/* 213 */     if (paramLong == 0L) {
/* 214 */       return null;
/*     */     }
/* 216 */     return new FileSpec(paramLong, paramObject);
/*     */   }
/*     */   
/*     */   public long __GetHandle() {
/* 220 */     return this.a;
/*     */   }
/*     */   
/*     */   public Object __GetRefHandle() {
/* 224 */     return this.b;
/*     */   }
/*     */   
/*     */   static native long Create(long paramLong, String paramString, boolean paramBoolean);
/*     */   
/*     */   static native long CreateURL(long paramLong, String paramString);
/*     */   
/*     */   static native boolean IsValid(long paramLong);
/*     */   
/*     */   static native boolean Export(long paramLong, String paramString);
/*     */   
/*     */   static native long GetFileData(long paramLong);
/*     */   
/*     */   static native String GetFilePath(long paramLong);
/*     */   
/*     */   static native void SetDesc(long paramLong, String paramString);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\FileSpec.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */