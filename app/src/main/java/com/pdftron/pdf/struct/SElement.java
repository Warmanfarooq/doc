/*     */ package com.pdftron.pdf.struct;
/*     */ 
/*     */ import com.pdftron.common.PDFNetException;
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
/*     */ public class SElement
/*     */ {
/*     */   private long a;
/*     */   private Object b;
/*     */   
/*     */   public SElement() throws PDFNetException {
/*  26 */     this.a = SElementCreate(0L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SElement(Obj paramObj) throws PDFNetException {
/*  37 */     this.a = SElementCreate(paramObj.__GetHandle());
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
/*     */   public boolean isValid() throws PDFNetException {
/*  49 */     return IsValid(this.a);
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
/*     */   public String getType() throws PDFNetException {
/*  64 */     return GetType(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNumKids() throws PDFNetException {
/*  75 */     return GetNumKids(this.a);
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
/*     */   public boolean isContentItem(int paramInt) throws PDFNetException {
/*  90 */     return IsContentItem(this.a, paramInt);
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
/*     */   public ContentItem getAsContentItem(int paramInt) throws PDFNetException {
/* 105 */     return new ContentItem(GetAsContentItem(this.a, paramInt), this.b);
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
/*     */   public SElement getAsStructElem(int paramInt) throws PDFNetException {
/* 120 */     return new SElement(GetAsStructElem(this.a, paramInt), this.b);
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
/*     */   public SElement getParent() throws PDFNetException {
/* 136 */     return new SElement(GetParent(this.a), this.b);
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
/*     */   public STree getStructTreeRoot() throws PDFNetException {
/* 148 */     return new STree(GetStructTreeRoot(this.a), this.b);
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
/*     */   public boolean hasTitle() throws PDFNetException {
/* 162 */     return HasTitle(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTitle() throws PDFNetException {
/* 173 */     return GetTitle(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Obj getID() throws PDFNetException {
/* 184 */     return Obj.__Create(GetID(this.a), this.b);
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
/*     */   public boolean hasActualText() throws PDFNetException {
/* 199 */     return HasActualText(this.a);
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
/*     */   public String getActualText() throws PDFNetException {
/* 214 */     return GetActualText(this.a);
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
/*     */   public boolean hasAlt() throws PDFNetException {
/* 229 */     return HasAlt(this.a);
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
/*     */   public String getAlt() throws PDFNetException {
/* 244 */     return GetAlt(this.a);
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
/* 255 */     return Obj.__Create(GetSDFObj(this.a), this.b);
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
/* 268 */     if (this.a != 0L) {
/*     */       
/* 270 */       Destroy(this.a);
/* 271 */       this.a = 0L;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finalize() throws Throwable {
/* 280 */     destroy();
/*     */   }
/*     */ 
/*     */   
/*     */   public static SElement __Create(long paramLong, Object paramObject) {
/* 285 */     return new SElement(paramLong, paramObject);
/*     */   }
/*     */   
/*     */   SElement(long paramLong, Object paramObject) {
/* 289 */     this.a = paramLong;
/* 290 */     this.b = paramObject;
/*     */   }
/*     */   
/*     */   static native void Destroy(long paramLong);
/*     */   
/*     */   static native long SElementCreate(long paramLong);
/*     */   
/*     */   static native boolean IsValid(long paramLong);
/*     */   
/*     */   static native String GetType(long paramLong);
/*     */   
/*     */   static native int GetNumKids(long paramLong);
/*     */   
/*     */   static native boolean IsContentItem(long paramLong, int paramInt);
/*     */   
/*     */   static native long GetAsContentItem(long paramLong, int paramInt);
/*     */   
/*     */   static native long GetAsStructElem(long paramLong, int paramInt);
/*     */   
/*     */   static native long GetParent(long paramLong);
/*     */   
/*     */   static native long GetStructTreeRoot(long paramLong);
/*     */   
/*     */   static native boolean HasTitle(long paramLong);
/*     */   
/*     */   static native String GetTitle(long paramLong);
/*     */   
/*     */   static native long GetID(long paramLong);
/*     */   
/*     */   static native boolean HasActualText(long paramLong);
/*     */   
/*     */   static native String GetActualText(long paramLong);
/*     */   
/*     */   static native boolean HasAlt(long paramLong);
/*     */   
/*     */   static native String GetAlt(long paramLong);
/*     */   
/*     */   static native long GetSDFObj(long paramLong);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\struct\SElement.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */