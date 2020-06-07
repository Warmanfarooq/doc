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
/*     */ 
/*     */ 
/*     */ public class NumberTree
/*     */ {
/*     */   private long a;
/*     */   private Object b;
/*     */   
/*     */   public NumberTree(Obj paramObj) {
/*  35 */     this.a = paramObj.a;
/*  36 */     this.b = paramObj.b;
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
/*  49 */     return (this.a != 0L);
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
/*     */   public DictIterator getIterator(long paramLong) throws PDFNetException {
/*  71 */     return new NumberTreeIterator(GetIterator(this.a, paramLong), this.b);
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
/*     */   public Obj getValue(long paramLong) throws PDFNetException {
/*  83 */     return Obj.__Create(GetValue(this.a, paramLong), this.b);
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
/*     */   public DictIterator getIterator() throws PDFNetException {
/* 101 */     return new NumberTreeIterator(GetIterator(this.a), this.b);
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
/*     */   public void put(long paramLong, Obj paramObj) throws PDFNetException {
/* 116 */     Put(this.a, paramLong, paramObj.a);
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
/*     */   public void erase(long paramLong) throws PDFNetException {
/* 128 */     Erase(this.a, paramLong);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void erase(DictIterator paramDictIterator) throws PDFNetException {
/* 139 */     EraseIt(this.a, paramDictIterator.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Obj getSDFObj() {
/* 150 */     return Obj.__Create(this.a, this.b);
/*     */   }
/*     */   
/*     */   static native long GetIterator(long paramLong1, long paramLong2);
/*     */   
/*     */   static native long GetValue(long paramLong1, long paramLong2);
/*     */   
/*     */   static native long GetIterator(long paramLong);
/*     */   
/*     */   static native void Put(long paramLong1, long paramLong2, long paramLong3);
/*     */   
/*     */   static native void Erase(long paramLong1, long paramLong2);
/*     */   
/*     */   static native void EraseIt(long paramLong1, long paramLong2);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\sdf\NumberTree.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */