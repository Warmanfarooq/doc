/*     */ package com.pdftron.pdf;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Highlights
/*     */ {
/*     */   long a;
/*     */   
/*     */   public Highlights() {
/*  55 */     this.a = HighlightsCreate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void destroy() {
/*  66 */     if (this.a != 0L) {
/*     */       
/*  68 */       Delete(this.a);
/*  69 */       this.a = 0L;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void finalize() throws Throwable {
/*  75 */     destroy();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void load(String paramString) {
/*  86 */     Load(this.a, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void save(String paramString) {
/*  96 */     Save(this.a, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(Highlights paramHighlights) {
/* 107 */     Add(this.a, paramHighlights.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 115 */     Clear(this.a);
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
/*     */   public void begin(PDFDoc paramPDFDoc) {
/* 128 */     Begin(this.a, paramPDFDoc.__GetHandle());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasNext() {
/* 138 */     return HasNext(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void next() {
/* 146 */     Next(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCurrentPageNumber() {
/* 156 */     return GetCurrentPageNumber(this.a);
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
/*     */   public double[] getCurrentQuads() {
/* 172 */     return GetCurrentQuads(this.a);
/*     */   }
/*     */ 
/*     */   
/*     */   public Highlights(long paramLong) {
/* 177 */     this.a = paramLong;
/*     */   }
/*     */   
/*     */   static native long HighlightsCreate();
/*     */   
/*     */   static native void Delete(long paramLong);
/*     */   
/*     */   static native void Load(long paramLong, String paramString);
/*     */   
/*     */   static native void Save(long paramLong, String paramString);
/*     */   
/*     */   static native void Add(long paramLong1, long paramLong2);
/*     */   
/*     */   static native void Clear(long paramLong);
/*     */   
/*     */   static native void Begin(long paramLong1, long paramLong2);
/*     */   
/*     */   static native boolean HasNext(long paramLong);
/*     */   
/*     */   static native void Next(long paramLong);
/*     */   
/*     */   static native int GetCurrentPageNumber(long paramLong);
/*     */   
/*     */   static native double[] GetCurrentQuads(long paramLong);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\Highlights.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */