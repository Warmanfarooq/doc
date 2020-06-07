/*     */ package com.pdftron.pdf;
/*     */ 
/*     */ import com.pdftron.common.PDFNetException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PageLabel
/*     */ {
/*     */   public static final int e_decimal = 0;
/*     */   public static final int e_roman_uppercase = 1;
/*     */   public static final int e_roman_lowercase = 2;
/*     */   public static final int e_alphabetic_uppercase = 3;
/*     */   public static final int e_alphabetic_lowercase = 4;
/*     */   public static final int e_none = 5;
/*     */   long a;
/*     */   private Object b;
/*     */   
/*     */   public static PageLabel create(Doc paramDoc, int paramInt) throws PDFNetException {
/* 113 */     return new PageLabel(Create(paramDoc.__GetHandle(), paramInt, "", 1), paramDoc);
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
/*     */   public static PageLabel create(Doc paramDoc, int paramInt, String paramString) throws PDFNetException {
/* 127 */     return new PageLabel(Create(paramDoc.__GetHandle(), paramInt, paramString, 1), paramDoc);
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
/*     */   public static PageLabel create(Doc paramDoc, int paramInt1, String paramString, int paramInt2) throws PDFNetException {
/* 143 */     return new PageLabel(Create(paramDoc.__GetHandle(), paramInt1, paramString, paramInt2), paramDoc);
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
/*     */   public PageLabel() throws PDFNetException {
/* 156 */     this.a = PageLabelCreate(0L, -1, -1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PageLabel(Obj paramObj) throws PDFNetException {
/* 167 */     this.a = PageLabelCreate(paramObj.__GetHandle(), -1, -1);
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
/*     */   public PageLabel(Obj paramObj, int paramInt) throws PDFNetException {
/* 179 */     this.a = PageLabelCreate(paramObj.__GetHandle(), paramInt, -1);
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
/*     */   public PageLabel(Obj paramObj, int paramInt1, int paramInt2) throws PDFNetException {
/* 192 */     this.a = PageLabelCreate(paramObj.__GetHandle(), paramInt1, paramInt2);
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
/*     */   public void destroy() throws PDFNetException {
/* 207 */     if (this.a != 0L) {
/*     */       
/* 209 */       Destroy(this.a);
/* 210 */       this.a = 0L;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void finalize() throws Throwable {
/* 216 */     destroy();
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
/*     */   public boolean equals(Object paramObject) {
/* 232 */     if (paramObject != null && paramObject.getClass().equals(getClass()))
/*     */     {
/* 234 */       return Equals(this.a, ((PageLabel)paramObject).a);
/*     */     }
/*     */     
/* 237 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 242 */     return HashCode(this.a);
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
/* 255 */     return IsValid(this.a);
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
/*     */   public String getLabelTitle(int paramInt) throws PDFNetException {
/* 269 */     return GetLabelTitle(this.a, paramInt);
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
/*     */   public void setStyle(int paramInt) throws PDFNetException {
/* 290 */     SetStyle(this.a, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getStyle() throws PDFNetException {
/* 301 */     return GetStyle(this.a);
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
/*     */   public String getPrefix() throws PDFNetException {
/* 313 */     return GetPrefix(this.a);
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
/*     */   public void setPrefix(String paramString) throws PDFNetException {
/* 325 */     SetPrefix(this.a, paramString);
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
/*     */   public int getStart() throws PDFNetException {
/* 337 */     return GetStart(this.a);
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
/*     */   public void setStart(int paramInt) throws PDFNetException {
/* 350 */     SetStart(this.a, paramInt);
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
/*     */   public int getFirstPageNum() throws PDFNetException {
/* 362 */     return GetFirstPageNum(this.a);
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
/*     */   public int getLastPageNum() throws PDFNetException {
/* 374 */     return GetLastPageNum(this.a);
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
/* 385 */     return Obj.__Create(GetSDFObj(this.a), this.b);
/*     */   }
/*     */ 
/*     */   
/*     */   PageLabel(long paramLong, Object paramObject) {
/* 390 */     this.a = paramLong;
/* 391 */     this.b = paramObject;
/*     */   }
/*     */   
/*     */   static native long Create(long paramLong, int paramInt1, String paramString, int paramInt2);
/*     */   
/*     */   static native long PageLabelCreate(long paramLong, int paramInt1, int paramInt2);
/*     */   
/*     */   static native void Destroy(long paramLong);
/*     */   
/*     */   static native boolean Equals(long paramLong1, long paramLong2);
/*     */   
/*     */   static native int HashCode(long paramLong);
/*     */   
/*     */   static native boolean IsValid(long paramLong);
/*     */   
/*     */   static native String GetLabelTitle(long paramLong, int paramInt);
/*     */   
/*     */   static native void SetStyle(long paramLong, int paramInt);
/*     */   
/*     */   static native int GetStyle(long paramLong);
/*     */   
/*     */   static native String GetPrefix(long paramLong);
/*     */   
/*     */   static native void SetPrefix(long paramLong, String paramString);
/*     */   
/*     */   static native int GetStart(long paramLong);
/*     */   
/*     */   static native void SetStart(long paramLong, int paramInt);
/*     */   
/*     */   static native int GetFirstPageNum(long paramLong);
/*     */   
/*     */   static native int GetLastPageNum(long paramLong);
/*     */   
/*     */   static native long GetSDFObj(long paramLong);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\PageLabel.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */