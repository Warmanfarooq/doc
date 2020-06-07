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
/*     */ public class ElementWriter
/*     */   extends h
/*     */ {
/*     */   public static final int e_underlay = 0;
/*     */   public static final int e_overlay = 1;
/*     */   public static final int e_replacement = 2;
/*  20 */   private long a = ElementWriterCreate();
/*  21 */   private Object d = null;
/*     */ 
/*     */ 
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
/*  34 */     if (this.a != 0L) {
/*     */       
/*  36 */       Destroy(this.a);
/*  37 */       this.a = 0L;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void begin(Page paramPage) throws PDFNetException {
/*  64 */     Begin(this.a, paramPage.a, 1, true, true, 0L);
/*  65 */     this.d = paramPage.b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void begin(Page paramPage, int paramInt) throws PDFNetException {
/*  76 */     Begin(this.a, paramPage.a, paramInt, true, true, 0L);
/*  77 */     this.d = paramPage.b;
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
/*     */   public void begin(Page paramPage, int paramInt, boolean paramBoolean) throws PDFNetException {
/*  92 */     Begin(this.a, paramPage.a, paramInt, paramBoolean, true, 0L);
/*  93 */     this.d = paramPage.b;
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
/*     */   public void begin(Page paramPage, int paramInt, boolean paramBoolean1, boolean paramBoolean2) throws PDFNetException {
/* 113 */     Begin(this.a, paramPage.a, paramInt, paramBoolean1, paramBoolean2, 0L);
/* 114 */     this.d = paramPage.b;
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
/*     */   public void begin(Page paramPage, int paramInt, boolean paramBoolean1, boolean paramBoolean2, Obj paramObj) throws PDFNetException {
/* 136 */     Begin(this.a, paramPage.a, paramInt, paramBoolean1, paramBoolean2, paramObj.__GetHandle());
/* 137 */     this.d = paramPage.b;
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
/*     */   public void begin(Obj paramObj) throws PDFNetException {
/* 150 */     BeginObj(this.a, paramObj.__GetHandle(), true, 0L);
/* 151 */     this.d = paramObj;
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
/*     */   public void begin(Obj paramObj, boolean paramBoolean) throws PDFNetException {
/* 168 */     BeginObj(this.a, paramObj.__GetHandle(), paramBoolean, 0L);
/* 169 */     this.d = paramObj;
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
/*     */   public void begin(Obj paramObj1, boolean paramBoolean, Obj paramObj2) throws PDFNetException {
/* 188 */     BeginObj(this.a, paramObj1.__GetHandle(), paramBoolean, paramObj2.__GetHandle());
/* 189 */     this.d = paramObj1;
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
/*     */   public void begin(Doc paramDoc) throws PDFNetException {
/* 207 */     Begin(this.a, paramDoc.__GetHandle(), true);
/* 208 */     this.d = paramDoc;
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
/*     */   public void begin(Doc paramDoc, boolean paramBoolean) throws PDFNetException {
/* 222 */     Begin(this.a, paramDoc.__GetHandle(), paramBoolean);
/* 223 */     this.d = paramDoc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Obj end() throws PDFNetException {
/* 234 */     return Obj.__Create(End(this.a), this.d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeElement(Element paramElement) throws PDFNetException {
/* 245 */     WriteElement(this.a, paramElement.a);
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
/*     */   public void writePlacedElement(Element paramElement) throws PDFNetException {
/* 267 */     WritePlacedElement(this.a, paramElement.a);
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
/*     */   public void flush() throws PDFNetException {
/* 279 */     Flush(this.a);
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
/*     */   public void writeBuffer(byte[] paramArrayOfbyte) throws PDFNetException {
/* 292 */     WriteBuffer(this.a, paramArrayOfbyte);
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
/*     */   public void writeString(String paramString) throws PDFNetException {
/* 304 */     WriteString(this.a, paramString);
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
/*     */   public void writeGStateChanges(Element paramElement) throws PDFNetException {
/* 316 */     WriteGStateChanges(this.a, paramElement.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDefaultGState(ElementReader paramElementReader) throws PDFNetException {
/* 327 */     SetDefaultGState(this.a, paramElementReader.c);
/*     */   }
/*     */   
/*     */   static native long ElementWriterCreate();
/*     */   
/*     */   static native void Destroy(long paramLong);
/*     */   
/*     */   static native void Begin(long paramLong1, long paramLong2, int paramInt, boolean paramBoolean1, boolean paramBoolean2, long paramLong3);
/*     */   
/*     */   static native void Begin(long paramLong1, long paramLong2, boolean paramBoolean);
/*     */   
/*     */   static native void BeginObj(long paramLong1, long paramLong2, boolean paramBoolean, long paramLong3);
/*     */   
/*     */   static native long End(long paramLong);
/*     */   
/*     */   static native void WriteElement(long paramLong1, long paramLong2);
/*     */   
/*     */   static native void WritePlacedElement(long paramLong1, long paramLong2);
/*     */   
/*     */   static native void Flush(long paramLong);
/*     */   
/*     */   static native void WriteBuffer(long paramLong, byte[] paramArrayOfbyte);
/*     */   
/*     */   static native void WriteString(long paramLong, String paramString);
/*     */   
/*     */   static native void WriteGStateChanges(long paramLong1, long paramLong2);
/*     */   
/*     */   static native void SetDefaultGState(long paramLong1, long paramLong2);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\ElementWriter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */