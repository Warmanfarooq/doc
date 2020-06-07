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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NameTree
/*     */ {
/*     */   private long a;
/*     */   private Object b;
/*     */   
/*     */   public static NameTree create(Doc paramDoc, String paramString) throws PDFNetException {
/*  49 */     return new NameTree(Create(paramDoc.impl, paramString), paramDoc);
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
/*     */   public static NameTree find(Doc paramDoc, String paramString) throws PDFNetException {
/*  64 */     return new NameTree(Find(paramDoc.impl, paramString), paramDoc);
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
/*     */   public NameTree(Obj paramObj) {
/*  76 */     this.a = paramObj.a;
/*  77 */     this.b = paramObj.b;
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
/*     */   public boolean isValid() throws PDFNetException {
/*  91 */     return IsValid(this.a);
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
/*     */   public NameTreeIterator getIterator(byte[] paramArrayOfbyte) throws PDFNetException {
/* 112 */     return new NameTreeIterator(GetIterator(this.a, paramArrayOfbyte), this.b);
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
/*     */   public Obj getValue(byte[] paramArrayOfbyte) throws PDFNetException {
/* 125 */     return Obj.__Create(GetValue(this.a, paramArrayOfbyte), this.b);
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
/*     */   public NameTreeIterator getIterator() throws PDFNetException {
/* 147 */     return new NameTreeIterator(GetIterator(this.a), this.b);
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
/*     */   public void put(byte[] paramArrayOfbyte, Obj paramObj) throws PDFNetException {
/* 160 */     Put(this.a, paramArrayOfbyte, paramObj.a);
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
/*     */   public void erase(byte[] paramArrayOfbyte) throws PDFNetException {
/* 172 */     Erase(this.a, paramArrayOfbyte);
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
/* 183 */     Erase(this.a, paramDictIterator.a);
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
/* 194 */     return Obj.__Create(this.a, this.b);
/*     */   }
/*     */ 
/*     */   
/*     */   private NameTree(long paramLong, Object paramObject) {
/* 199 */     this.a = paramLong;
/* 200 */     this.b = paramObject;
/*     */   }
/*     */   
/*     */   static native long Create(long paramLong, String paramString);
/*     */   
/*     */   static native long Find(long paramLong, String paramString);
/*     */   
/*     */   static native boolean IsValid(long paramLong);
/*     */   
/*     */   static native long GetIterator(long paramLong, byte[] paramArrayOfbyte);
/*     */   
/*     */   static native long GetValue(long paramLong, byte[] paramArrayOfbyte);
/*     */   
/*     */   static native long GetIterator(long paramLong);
/*     */   
/*     */   static native void Put(long paramLong1, byte[] paramArrayOfbyte, long paramLong2);
/*     */   
/*     */   static native void Erase(long paramLong, byte[] paramArrayOfbyte);
/*     */   
/*     */   static native void Erase(long paramLong1, long paramLong2);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\sdf\NameTree.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */