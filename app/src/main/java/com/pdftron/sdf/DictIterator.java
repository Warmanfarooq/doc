/*     */ package com.pdftron.sdf;
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
/*     */ public class DictIterator
/*     */ {
/*     */   long a;
/*     */   private Object b;
/*     */   
/*     */   public void destroy() throws PDFNetException {
/*  33 */     if (this.a != 0L) {
/*     */       
/*  35 */       Destroy(this.a);
/*  36 */       this.a = 0L;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finalize() throws Throwable {
/*  45 */     destroy();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void next() throws PDFNetException {
/*  55 */     Next(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Obj key() throws PDFNetException {
/*  66 */     return Obj.__Create(Key(this.a), this.b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Obj value() throws PDFNetException {
/*  77 */     return Obj.__Create(Value(this.a), this.b);
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
/*     */   public boolean hasNext() throws PDFNetException {
/*  89 */     return HasNext(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   DictIterator(long paramLong, Object paramObject) {
/* 100 */     this.a = paramLong;
/* 101 */     this.b = paramObject;
/*     */   }
/*     */ 
/*     */   
/*     */   public long __GetHandle() {
/* 106 */     return this.a;
/*     */   }
/*     */   
/*     */   static native void Destroy(long paramLong);
/*     */   
/*     */   static native void Next(long paramLong);
/*     */   
/*     */   static native long Key(long paramLong);
/*     */   
/*     */   static native long Value(long paramLong);
/*     */   
/*     */   static native boolean HasNext(long paramLong);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\sdf\DictIterator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */