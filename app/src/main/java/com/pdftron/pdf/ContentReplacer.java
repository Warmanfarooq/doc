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
/*     */ public class ContentReplacer
/*     */ {
/*  47 */   private long a = ContentReplacerCreate();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addImage(Rect paramRect, Obj paramObj) throws PDFNetException {
/*  70 */     AddImage(this.a, paramRect.a, paramObj.__GetHandle());
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
/*     */   public void addText(Rect paramRect, String paramString) throws PDFNetException {
/*  90 */     AddText(this.a, paramRect.a, paramString);
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
/*     */   public void addString(String paramString1, String paramString2) throws PDFNetException {
/* 108 */     AddString(this.a, paramString1, paramString2);
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
/*     */   public void setMatchStrings(String paramString1, String paramString2) throws PDFNetException {
/* 127 */     SetMatchStrings(this.a, paramString1, paramString2);
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
/*     */   public void process(Page paramPage) throws PDFNetException {
/* 139 */     Process(this.a, paramPage.a);
/*     */   }
/*     */   
/*     */   protected void finalize() throws Throwable {
/* 143 */     destroy();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void destroy() {
/* 153 */     if (this.a != 0L) {
/* 154 */       ContentReplacerDestroy(this.a);
/* 155 */       this.a = 0L;
/*     */     } 
/*     */   }
/*     */   
/*     */   static native long ContentReplacerCreate();
/*     */   
/*     */   static native void ContentReplacerDestroy(long paramLong);
/*     */   
/*     */   static native void AddImage(long paramLong1, long paramLong2, long paramLong3);
/*     */   
/*     */   static native void AddText(long paramLong1, long paramLong2, String paramString);
/*     */   
/*     */   static native void AddString(long paramLong, String paramString1, String paramString2);
/*     */   
/*     */   static native void SetMatchStrings(long paramLong, String paramString1, String paramString2);
/*     */   
/*     */   static native void Process(long paramLong1, long paramLong2);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\ContentReplacer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */