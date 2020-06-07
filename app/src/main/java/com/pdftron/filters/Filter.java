/*     */ package com.pdftron.filters;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Filter
/*     */ {
/*     */   protected long impl;
/*     */   protected Filter attached;
/*     */   protected Object ref;
/*     */   
/*     */   public void destroy() throws PDFNetException {
/*  52 */     if (this.attached == null && this.ref == null && this.impl != 0L) {
/*     */       
/*  54 */       Destroy(this.impl);
/*  55 */       this.impl = 0L;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finalize() throws Throwable {
/*  64 */     destroy();
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
/*     */   public void attachFilter(Filter paramFilter) throws PDFNetException {
/*  77 */     if (paramFilter != null) {
/*     */       
/*  79 */       AttachFilter(this.impl, paramFilter.impl);
/*  80 */       paramFilter.attached = this;
/*     */     } 
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
/*     */   public Filter releaseAttachedFilter() throws PDFNetException {
/*     */     long l;
/*  94 */     if ((l = ReleaseAttachedFilter(this.impl)) == 0L) {
/*  95 */       return null;
/*     */     }
/*  97 */     return new Filter(l, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Filter getAttachedFilter() throws PDFNetException {
/* 108 */     return new Filter(GetAttachedFilter(this.impl), this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Filter getSourceFilter() throws PDFNetException {
/* 119 */     long l = GetSourceFilter(this.impl);
/* 120 */     Filter filter = this;
/* 121 */     while (filter.attached != null)
/*     */     {
/* 123 */       filter = filter.attached;
/*     */     }
/* 125 */     return new Filter(l, filter);
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
/* 136 */     return GetName(this.impl);
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
/*     */   public String getDecodeName() throws PDFNetException {
/* 149 */     return GetDecodeName(this.impl);
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
/*     */   public void setStreamLength(long paramLong) throws PDFNetException {
/* 165 */     SetStreamLength(this.impl, paramLong);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() throws PDFNetException {
/* 176 */     Flush(this.impl);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flushAll() throws PDFNetException {
/* 186 */     FlushAll(this.impl);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInputFilter() throws PDFNetException {
/* 197 */     return IsInputFilter(this.impl);
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
/*     */   public boolean canSeek() throws PDFNetException {
/* 209 */     return CanSeek(this.impl);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void seek(long paramLong, int paramInt) throws PDFNetException {
/* 241 */     Seek(this.impl, paramLong, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long size() throws PDFNetException {
/* 252 */     return Size(this.impl);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long tell() throws PDFNetException {
/* 263 */     return Tell(this.impl);
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
/*     */   public Filter createInputIterator() throws PDFNetException {
/* 278 */     return new Filter(CreateInputIterator(this.impl), null);
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
/*     */   public String getFilePath() throws PDFNetException {
/* 290 */     return GetFilePath(this.impl);
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
/*     */   public void writeToFile(String paramString, boolean paramBoolean) {
/* 303 */     WriteToFile(this.impl, paramString, paramBoolean);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Filter(long paramLong, Filter paramFilter) {
/* 308 */     this.impl = paramLong;
/* 309 */     this.attached = paramFilter;
/*     */   }
/*     */   
/*     */   public static Filter __Create(long paramLong, Filter paramFilter) {
/* 313 */     return new Filter(paramLong, paramFilter);
/*     */   }
/*     */   
/*     */   public long __GetHandle() {
/* 317 */     return this.impl;
/*     */   }
/*     */   
/*     */   public void __SetRefHandle(Object paramObject) {
/* 321 */     this.ref = paramObject;
/*     */   }
/*     */   
/*     */   static native void Destroy(long paramLong);
/*     */   
/*     */   static native void AttachFilter(long paramLong1, long paramLong2);
/*     */   
/*     */   static native long ReleaseAttachedFilter(long paramLong);
/*     */   
/*     */   static native long GetAttachedFilter(long paramLong);
/*     */   
/*     */   static native long GetSourceFilter(long paramLong);
/*     */   
/*     */   static native String GetName(long paramLong);
/*     */   
/*     */   static native String GetDecodeName(long paramLong);
/*     */   
/*     */   static native void SetStreamLength(long paramLong1, long paramLong2);
/*     */   
/*     */   static native void Flush(long paramLong);
/*     */   
/*     */   static native void FlushAll(long paramLong);
/*     */   
/*     */   static native boolean IsInputFilter(long paramLong);
/*     */   
/*     */   static native boolean CanSeek(long paramLong);
/*     */   
/*     */   static native void Seek(long paramLong1, long paramLong2, int paramInt);
/*     */   
/*     */   static native long Size(long paramLong);
/*     */   
/*     */   static native long Tell(long paramLong);
/*     */   
/*     */   static native long CreateInputIterator(long paramLong);
/*     */   
/*     */   static native String GetFilePath(long paramLong);
/*     */   
/*     */   static native void WriteToFile(long paramLong, String paramString, boolean paramBoolean);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\filters\Filter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */