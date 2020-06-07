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
/*     */ public class PDFDocInfo
/*     */ {
/*     */   private long a;
/*     */   private Object b;
/*     */   
/*     */   public String getTitle() throws PDFNetException {
/*  19 */     return GetTitle(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Obj getTitleObj() throws PDFNetException {
/*  29 */     return Obj.__Create(GetTitleObj(this.a), this.b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTitle(String paramString) throws PDFNetException {
/*  40 */     SetTitle(this.a, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAuthor() throws PDFNetException {
/*  50 */     return GetAuthor(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Obj getAuthorObj() throws PDFNetException {
/*  60 */     return Obj.__Create(GetAuthorObj(this.a), this.b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAuthor(String paramString) throws PDFNetException {
/*  71 */     SetAuthor(this.a, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSubject() throws PDFNetException {
/*  81 */     return GetSubject(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Obj getSubjectObj() throws PDFNetException {
/*  91 */     return Obj.__Create(GetSubjectObj(this.a), this.b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSubject(String paramString) throws PDFNetException {
/* 102 */     SetSubject(this.a, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getKeywords() throws PDFNetException {
/* 112 */     return GetKeywords(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Obj getKeywordsObj() throws PDFNetException {
/* 122 */     return Obj.__Create(GetKeywordsObj(this.a), this.b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setKeywords(String paramString) throws PDFNetException {
/* 133 */     SetKeywords(this.a, paramString);
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
/*     */   public String getCreator() throws PDFNetException {
/* 145 */     return GetCreator(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Obj getCreatorObj() throws PDFNetException {
/* 155 */     return Obj.__Create(GetCreatorObj(this.a), this.b);
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
/* 167 */     SetCreator(this.a, paramString);
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
/*     */   public String getProducer() throws PDFNetException {
/* 179 */     return GetProducer(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Obj getProducerObj() throws PDFNetException {
/* 189 */     return Obj.__Create(GetProducerObj(this.a), this.b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setProducer(String paramString) throws PDFNetException {
/* 200 */     SetProducer(this.a, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Date getCreationDate() throws PDFNetException {
/* 211 */     return new Date(GetCreationDate(this.a));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCreationDate(Date paramDate) throws PDFNetException {
/* 222 */     SetCreationDate(this.a, paramDate.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Date getModDate() throws PDFNetException {
/* 233 */     return new Date(GetModDate(this.a));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setModDate(Date paramDate) throws PDFNetException {
/* 244 */     SetModDate(this.a, paramDate.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Obj getSDFObj() {
/* 254 */     return Obj.__Create(this.a, this.b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PDFDocInfo(Obj paramObj) {
/* 264 */     this.a = paramObj.__GetHandle();
/* 265 */     this.b = paramObj.__GetRefHandle();
/*     */   }
/*     */   
/*     */   PDFDocInfo(long paramLong, Object paramObject) {
/* 269 */     this.a = paramLong;
/* 270 */     this.b = paramObject;
/*     */   }
/*     */   
/*     */   static native String GetTitle(long paramLong);
/*     */   
/*     */   static native long GetTitleObj(long paramLong);
/*     */   
/*     */   static native void SetTitle(long paramLong, String paramString);
/*     */   
/*     */   static native String GetAuthor(long paramLong);
/*     */   
/*     */   static native long GetAuthorObj(long paramLong);
/*     */   
/*     */   static native void SetAuthor(long paramLong, String paramString);
/*     */   
/*     */   static native String GetSubject(long paramLong);
/*     */   
/*     */   static native long GetSubjectObj(long paramLong);
/*     */   
/*     */   static native void SetSubject(long paramLong, String paramString);
/*     */   
/*     */   static native String GetKeywords(long paramLong);
/*     */   
/*     */   static native long GetKeywordsObj(long paramLong);
/*     */   
/*     */   static native void SetKeywords(long paramLong, String paramString);
/*     */   
/*     */   static native String GetCreator(long paramLong);
/*     */   
/*     */   static native long GetCreatorObj(long paramLong);
/*     */   
/*     */   static native void SetCreator(long paramLong, String paramString);
/*     */   
/*     */   static native String GetProducer(long paramLong);
/*     */   
/*     */   static native long GetProducerObj(long paramLong);
/*     */   
/*     */   static native void SetProducer(long paramLong, String paramString);
/*     */   
/*     */   static native long GetCreationDate(long paramLong);
/*     */   
/*     */   static native void SetCreationDate(long paramLong1, long paramLong2);
/*     */   
/*     */   static native long GetModDate(long paramLong);
/*     */   
/*     */   static native void SetModDate(long paramLong1, long paramLong2);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\PDFDocInfo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */