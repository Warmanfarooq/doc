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
/*     */ public class FilterReader
/*     */ {
/*     */   private Filter b;
/*     */   long a;
/*     */   
/*     */   public FilterReader() throws PDFNetException {
/*  30 */     this.a = FilterReaderCreate();
/*  31 */     this.b = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FilterReader(Filter paramFilter) throws PDFNetException {
/*  42 */     this.a = FilterReaderCreate(paramFilter.impl);
/*  43 */     this.b = paramFilter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finalize() throws PDFNetException {
/*  51 */     destroy();
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
/*     */   public void destroy() throws PDFNetException {
/*  65 */     if (this.a != 0L) {
/*     */       
/*  67 */       Destroy(this.a);
/*  68 */       this.a = 0L;
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
/*     */   public int get() throws PDFNetException {
/*  80 */     return Get(this.a);
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
/*     */   public int peek() throws PDFNetException {
/*  92 */     return Peek(this.a);
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
/*     */   public long read(byte[] paramArrayOfbyte) throws PDFNetException {
/* 106 */     return Read(this.a, paramArrayOfbyte);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void attachFilter(Filter paramFilter) throws PDFNetException {
/* 117 */     this.b = paramFilter;
/* 118 */     AttachFilter(this.a, paramFilter.impl);
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
/* 129 */     return this.b;
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
/*     */   public void seek(long paramLong, int paramInt) throws PDFNetException {
/* 148 */     Seek(this.a, paramLong, paramInt);
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
/* 159 */     return Tell(this.a);
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
/*     */   public long count() throws PDFNetException {
/* 171 */     return Count(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() throws PDFNetException {
/* 181 */     Flush(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flushAll() throws PDFNetException {
/* 191 */     FlushAll(this.a);
/*     */   }
/*     */ 
/*     */   
/*     */   public long __GetHandle() {
/* 196 */     return this.a;
/*     */   }
/*     */   
/*     */   static native long FilterReaderCreate();
/*     */   
/*     */   static native long FilterReaderCreate(long paramLong);
/*     */   
/*     */   static native void Destroy(long paramLong);
/*     */   
/*     */   static native int Get(long paramLong);
/*     */   
/*     */   static native int Peek(long paramLong);
/*     */   
/*     */   static native long Read(long paramLong, byte[] paramArrayOfbyte);
/*     */   
/*     */   static native void AttachFilter(long paramLong1, long paramLong2);
/*     */   
/*     */   static native void Seek(long paramLong1, long paramLong2, int paramInt);
/*     */   
/*     */   static native long Tell(long paramLong);
/*     */   
/*     */   static native long Count(long paramLong);
/*     */   
/*     */   static native void Flush(long paramLong);
/*     */   
/*     */   static native void FlushAll(long paramLong);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\filters\FilterReader.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */