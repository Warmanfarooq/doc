/*     */ package com.pdftron.pdf;
/*     */ 
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.ocg.Context;
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
/*     */ public class ElementReader
/*     */   extends h
/*     */ {
/*     */   private Object a;
/*     */   
/*     */   public ElementReader() throws PDFNetException {
/*  52 */     this.c = ElementReaderCreate();
/*  53 */     this.a = null;
/*  54 */     clearList();
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
/*     */   public void destroy() throws PDFNetException {
/*  67 */     if (this.c != 0L) {
/*     */       
/*  69 */       Destroy(this.c);
/*  70 */       this.c = 0L;
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
/*     */   public void begin(Page paramPage) throws PDFNetException {
/*  85 */     Begin1(this.c, paramPage.a);
/*  86 */     this.a = paramPage.b;
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
/*     */   public void begin(Page paramPage, Context paramContext) throws PDFNetException {
/* 104 */     Begin2(this.c, paramPage.a, paramContext.__GetHandle());
/* 105 */     this.a = paramPage.b;
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
/*     */   public void begin(Obj paramObj) throws PDFNetException {
/* 122 */     BeginStm1(this.c, paramObj.__GetHandle());
/* 123 */     this.a = paramObj.__GetRefHandle();
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
/*     */   public void begin(Obj paramObj1, Obj paramObj2) throws PDFNetException {
/* 143 */     BeginStm2(this.c, paramObj1.__GetHandle(), paramObj2.__GetHandle());
/* 144 */     this.a = paramObj1.__GetRefHandle();
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
/*     */   public void begin(Obj paramObj1, Obj paramObj2, Context paramContext) throws PDFNetException {
/* 168 */     BeginStm3(this.c, paramObj1.__GetHandle(), paramObj2.__GetHandle(), paramContext.__GetHandle());
/* 169 */     this.a = paramObj1.__GetRefHandle();
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
/*     */   public Element next() throws PDFNetException {
/*     */     long l;
/* 187 */     if ((l = Next(this.c)) != 0L) {
/* 188 */       return new Element(l, this, this.a);
/*     */     }
/* 190 */     return null;
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
/*     */   public Element current() throws PDFNetException {
/*     */     long l;
/* 206 */     if ((l = Current(this.c)) != 0L) {
/* 207 */       return new Element(l, this, this.a);
/*     */     }
/* 209 */     return null;
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
/*     */   public void formBegin() throws PDFNetException {
/* 228 */     FormBegin(this.c);
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
/*     */   public void patternBegin(boolean paramBoolean1, boolean paramBoolean2) throws PDFNetException {
/* 255 */     PatternBegin(this.c, paramBoolean1, paramBoolean2);
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
/*     */   public void patternBegin(boolean paramBoolean) throws PDFNetException {
/* 269 */     PatternBegin(this.c, paramBoolean, false);
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
/*     */   public void type3FontBegin(CharData paramCharData, Obj paramObj) throws PDFNetException {
/* 293 */     if (paramObj != null) {
/* 294 */       Type3FontBegin(this.c, paramCharData.a, paramObj.__GetHandle()); return;
/*     */     } 
/* 296 */     Type3FontBegin(this.c, paramCharData.a, 0L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void type3FontBegin(CharData paramCharData) throws PDFNetException {
/* 307 */     Type3FontBegin(this.c, paramCharData.a, 0L);
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
/*     */   public boolean end() throws PDFNetException {
/* 324 */     return End(this.c);
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
/*     */   public GSChangesIterator getChangesIterator() throws PDFNetException {
/* 339 */     return new GSChangesIterator(GetChangesIterator(this.c), this.a);
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
/*     */   public boolean isChanged(int paramInt) throws PDFNetException {
/* 352 */     return IsChanged(this.c, paramInt);
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
/*     */   public void clearChangeList() throws PDFNetException {
/* 364 */     ClearChangeList(this.c);
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
/*     */   public Obj getFont(String paramString) throws PDFNetException {
/* 382 */     return Obj.__Create(GetFont(this.c, paramString), this.a);
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
/*     */   public Obj getXObject(String paramString) {
/* 394 */     return Obj.__Create(GetXObject(this.c, paramString), this.a);
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
/*     */   public Obj getShading(String paramString) {
/* 406 */     return Obj.__Create(GetShading(this.c, paramString), this.a);
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
/*     */   public Obj getColorSpace(String paramString) {
/* 418 */     return Obj.__Create(GetColorSpace(this.c, paramString), this.a);
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
/*     */   public Obj getPattern(String paramString) {
/* 430 */     return Obj.__Create(GetPattern(this.c, paramString), this.a);
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
/*     */   public Obj getExtGState(String paramString) {
/* 442 */     return Obj.__Create(GetExtGState(this.c, paramString), this.a);
/*     */   }
/*     */   
/*     */   static native long ElementReaderCreate();
/*     */   
/*     */   static native void Destroy(long paramLong);
/*     */   
/*     */   static native void Begin1(long paramLong1, long paramLong2);
/*     */   
/*     */   static native void Begin2(long paramLong1, long paramLong2, long paramLong3);
/*     */   
/*     */   static native void BeginStm1(long paramLong1, long paramLong2);
/*     */   
/*     */   static native void BeginStm2(long paramLong1, long paramLong2, long paramLong3);
/*     */   
/*     */   static native void BeginStm3(long paramLong1, long paramLong2, long paramLong3, long paramLong4);
/*     */   
/*     */   static native long Next(long paramLong);
/*     */   
/*     */   static native long Current(long paramLong);
/*     */   
/*     */   static native void FormBegin(long paramLong);
/*     */   
/*     */   static native void PatternBegin(long paramLong, boolean paramBoolean1, boolean paramBoolean2);
/*     */   
/*     */   static native void Type3FontBegin(long paramLong1, long paramLong2, long paramLong3);
/*     */   
/*     */   static native boolean End(long paramLong);
/*     */   
/*     */   static native long GetChangesIterator(long paramLong);
/*     */   
/*     */   static native boolean IsChanged(long paramLong, int paramInt);
/*     */   
/*     */   static native void ClearChangeList(long paramLong);
/*     */   
/*     */   static native long GetFont(long paramLong, String paramString);
/*     */   
/*     */   static native long GetXObject(long paramLong, String paramString);
/*     */   
/*     */   static native long GetShading(long paramLong, String paramString);
/*     */   
/*     */   static native long GetColorSpace(long paramLong, String paramString);
/*     */   
/*     */   static native long GetPattern(long paramLong, String paramString);
/*     */   
/*     */   static native long GetExtGState(long paramLong, String paramString);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\ElementReader.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */