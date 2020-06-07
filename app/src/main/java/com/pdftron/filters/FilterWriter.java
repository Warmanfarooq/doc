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
/*     */ public class FilterWriter
/*     */ {
/*     */   private Filter a;
/*     */   private long b;
/*     */   
/*     */   public void destroy() throws PDFNetException {
/*  30 */     if (this.b != 0L) {
/*     */       
/*  32 */       Destroy(this.b);
/*  33 */       this.b = 0L;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finalize() throws PDFNetException {
/*  42 */     destroy();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FilterWriter() throws PDFNetException {
/*  52 */     this.b = FilterWriterCreate();
/*  53 */     this.a = null;
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
/*     */   public FilterWriter(Filter paramFilter) throws PDFNetException {
/*  65 */     this.b = FilterWriterCreate(paramFilter.impl);
/*  66 */     this.a = paramFilter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeUChar(byte paramByte) throws PDFNetException {
/*  77 */     WriteUChar(this.b, paramByte);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeInt(int paramInt) throws PDFNetException {
/*  88 */     WriteInt(this.b, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeString(String paramString) throws PDFNetException {
/*  99 */     WriteString(this.b, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeFilter(FilterReader paramFilterReader) throws PDFNetException {
/* 110 */     WriteFilter(this.b, paramFilterReader.a);
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
/*     */   public void writeLine(String paramString) throws PDFNetException {
/* 122 */     WriteLine(this.b, paramString);
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
/*     */   public long writeBuffer(byte[] paramArrayOfbyte) throws PDFNetException {
/* 135 */     return WriteBuffer(this.b, paramArrayOfbyte);
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
/* 146 */     AttachFilter(this.b, paramFilter.impl);
/* 147 */     this.a = paramFilter;
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
/* 158 */     return this.a;
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
/* 177 */     Seek(this.b, paramLong, paramInt);
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
/* 188 */     return Tell(this.b);
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
/* 200 */     return Count(this.b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() throws PDFNetException {
/* 210 */     Flush(this.b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flushAll() throws PDFNetException {
/* 220 */     FlushAll(this.b);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public long __GetHandle() {
/* 226 */     return this.b;
/*     */   }
/*     */   
/*     */   static native void Destroy(long paramLong);
/*     */   
/*     */   static native long FilterWriterCreate();
/*     */   
/*     */   static native long FilterWriterCreate(long paramLong);
/*     */   
/*     */   static native void WriteUChar(long paramLong, byte paramByte);
/*     */   
/*     */   static native void WriteInt(long paramLong, int paramInt);
/*     */   
/*     */   static native void WriteString(long paramLong, String paramString);
/*     */   
/*     */   static native void WriteFilter(long paramLong1, long paramLong2);
/*     */   
/*     */   static native void WriteLine(long paramLong, String paramString);
/*     */   
/*     */   static native long WriteBuffer(long paramLong, byte[] paramArrayOfbyte);
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


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\filters\FilterWriter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */