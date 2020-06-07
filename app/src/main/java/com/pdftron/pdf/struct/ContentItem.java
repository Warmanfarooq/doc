/*     */ package com.pdftron.pdf.struct;
/*     */ 
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.Page;
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
/*     */ public class ContentItem
/*     */ {
/*     */   public static final int e_MCR = 0;
/*     */   public static final int e_MCID = 1;
/*     */   public static final int e_OBJR = 2;
/*     */   public static final int e_Unknown = 3;
/*     */   private long a;
/*     */   private Object b;
/*     */   
/*     */   public void destroy() throws PDFNetException {
/*  38 */     if (this.a != 0L) {
/*     */       
/*  40 */       Destroy(this.a);
/*  41 */       this.a = 0L;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finalize() throws Throwable {
/*  50 */     destroy();
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
/*     */   public int getType() throws PDFNetException {
/*  62 */     return GetType(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SElement getParent() throws PDFNetException {
/*  73 */     return new SElement(GetParent(this.a), this.b);
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
/*     */   public Page getPage() throws PDFNetException {
/*  86 */     return Page.__Create(GetPage(this.a), this.b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Obj getSDFObj() throws PDFNetException {
/*  97 */     return Obj.__Create(GetSDFObj(this.a), this.b);
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
/*     */   public int getMCID() throws PDFNetException {
/* 112 */     return GetMCID(this.a);
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
/*     */   public Obj getContainingStm() throws PDFNetException {
/* 128 */     return Obj.__Create(GetContainingStm(this.a), this.b);
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
/*     */   public Obj getStmOwner() throws PDFNetException {
/* 143 */     return Obj.__Create(GetStmOwner(this.a), this.b);
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
/*     */   public Obj getRefObj() throws PDFNetException {
/* 156 */     return Obj.__Create(GetRefObj(this.a), this.b);
/*     */   }
/*     */ 
/*     */   
/*     */   ContentItem(long paramLong, Object paramObject) {
/* 161 */     this.a = paramLong;
/* 162 */     this.b = paramObject;
/*     */   }
/*     */   
/*     */   static native void Destroy(long paramLong);
/*     */   
/*     */   static native int GetType(long paramLong);
/*     */   
/*     */   static native long GetParent(long paramLong);
/*     */   
/*     */   static native long GetPage(long paramLong);
/*     */   
/*     */   static native long GetSDFObj(long paramLong);
/*     */   
/*     */   static native int GetMCID(long paramLong);
/*     */   
/*     */   static native long GetContainingStm(long paramLong);
/*     */   
/*     */   static native long GetStmOwner(long paramLong);
/*     */   
/*     */   static native long GetRefObj(long paramLong);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\struct\ContentItem.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */