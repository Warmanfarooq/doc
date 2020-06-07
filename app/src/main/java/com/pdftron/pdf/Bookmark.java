/*     */ package com.pdftron.pdf;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Bookmark
/*     */ {
/*     */   long a;
/*     */   private Object b;
/*     */   
/*     */   public static Bookmark create(PDFDoc paramPDFDoc, String paramString) throws PDFNetException {
/*  40 */     return new Bookmark(Create(paramPDFDoc.__GetHandle(), paramString), paramPDFDoc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Bookmark() {
/*  48 */     this.a = 0L;
/*  49 */     this.b = null;
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
/*     */   public Bookmark(Obj paramObj) {
/*  62 */     this.a = paramObj.__GetHandle();
/*  63 */     this.b = paramObj.__GetRefHandle();
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
/*     */   public boolean equals(Object paramObject) {
/*  75 */     if (paramObject != null && paramObject.getClass().equals(getClass())) {
/*  76 */       return (((Bookmark)paramObject).a == this.a);
/*     */     }
/*  78 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  86 */     return (int)this.a;
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
/* 100 */     return IsValid(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasChildren() throws PDFNetException {
/* 111 */     return HasChildren(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Bookmark getNext() throws PDFNetException {
/* 122 */     return new Bookmark(GetNext(this.a), this.b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Bookmark getPrev() throws PDFNetException {
/* 133 */     return new Bookmark(GetPrev(this.a), this.b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Bookmark getFirstChild() throws PDFNetException {
/* 144 */     return new Bookmark(GetFirstChild(this.a), this.b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Bookmark getLastChild() throws PDFNetException {
/* 155 */     return new Bookmark(GetLastChild(this.a), this.b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Bookmark getParent() throws PDFNetException {
/* 166 */     return new Bookmark(GetParent(this.a), this.b);
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
/*     */   public Bookmark find(String paramString) throws PDFNetException {
/* 178 */     return new Bookmark(Find(this.a, paramString), this.b);
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
/*     */   public Bookmark addChild(String paramString) throws PDFNetException {
/* 193 */     return new Bookmark(AddChild(this.a, paramString), this.b);
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
/*     */   public void addChild(Bookmark paramBookmark) throws PDFNetException {
/* 209 */     AddChild(this.a, paramBookmark.a);
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
/*     */   public Bookmark addNext(String paramString) throws PDFNetException {
/* 222 */     return new Bookmark(AddNext(this.a, paramString), this.b);
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
/*     */   public void addNext(Bookmark paramBookmark) throws PDFNetException {
/* 236 */     AddNext(this.a, paramBookmark.a);
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
/*     */   public Bookmark addPrev(String paramString) throws PDFNetException {
/* 249 */     return new Bookmark(AddPrev(this.a, paramString), this.b);
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
/*     */   public void addPrev(Bookmark paramBookmark) throws PDFNetException {
/* 263 */     AddPrev(this.a, paramBookmark.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void delete() throws PDFNetException {
/* 271 */     Delete(this.a);
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
/*     */   public void unlink() throws PDFNetException {
/* 285 */     Unlink(this.a);
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
/*     */   public int getIndent() throws PDFNetException {
/* 298 */     return GetIndent(this.a);
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
/*     */   public boolean isOpen() throws PDFNetException {
/* 311 */     return IsOpen(this.a);
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
/*     */   public void setOpen(boolean paramBoolean) throws PDFNetException {
/* 325 */     SetOpen(this.a, paramBoolean);
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
/*     */   public int getOpenCount() throws PDFNetException {
/* 339 */     return GetOpenCount(this.a);
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
/* 350 */     return GetTitle(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Obj getTitleObj() throws PDFNetException {
/* 361 */     return Obj.__Create(GetTitleObj(this.a), this.b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTitle(String paramString) throws PDFNetException {
/* 372 */     SetTitle(this.a, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Action getAction() throws PDFNetException {
/* 383 */     return new Action(GetAction(this.a), this.b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAction(Action paramAction) throws PDFNetException {
/* 394 */     SetAction(this.a, paramAction.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAction() throws PDFNetException {
/* 404 */     RemoveAction(this.a);
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
/*     */   public int getFlags() throws PDFNetException {
/* 418 */     return GetFlags(this.a);
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
/*     */   public void setFlags(int paramInt) throws PDFNetException {
/* 432 */     SetFlags(this.a, paramInt);
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
/*     */   public double[] getColor() throws PDFNetException {
/* 452 */     return GetColor(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setColor() throws PDFNetException {
/* 462 */     SetColor(this.a, 0.0D, 0.0D, 0.0D);
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
/*     */   public void setColor(double paramDouble1, double paramDouble2, double paramDouble3) throws PDFNetException {
/* 475 */     SetColor(this.a, paramDouble1, paramDouble2, paramDouble3);
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
/*     */   public Obj getSDFObj() {
/* 488 */     return Obj.__Create(this.a, this.b);
/*     */   }
/*     */ 
/*     */   
/*     */   Bookmark(long paramLong, Object paramObject) {
/* 493 */     this.a = paramLong;
/* 494 */     this.b = paramObject;
/*     */   }
/*     */   
/*     */   static native long Create(long paramLong, String paramString);
/*     */   
/*     */   static native boolean IsValid(long paramLong);
/*     */   
/*     */   static native boolean HasChildren(long paramLong);
/*     */   
/*     */   static native long GetNext(long paramLong);
/*     */   
/*     */   static native long GetPrev(long paramLong);
/*     */   
/*     */   static native long GetFirstChild(long paramLong);
/*     */   
/*     */   static native long GetLastChild(long paramLong);
/*     */   
/*     */   static native long GetParent(long paramLong);
/*     */   
/*     */   static native long Find(long paramLong, String paramString);
/*     */   
/*     */   static native long AddChild(long paramLong, String paramString);
/*     */   
/*     */   static native void AddChild(long paramLong1, long paramLong2);
/*     */   
/*     */   static native long AddNext(long paramLong, String paramString);
/*     */   
/*     */   static native void AddNext(long paramLong1, long paramLong2);
/*     */   
/*     */   static native long AddPrev(long paramLong, String paramString);
/*     */   
/*     */   static native void AddPrev(long paramLong1, long paramLong2);
/*     */   
/*     */   static native void Delete(long paramLong);
/*     */   
/*     */   static native void Unlink(long paramLong);
/*     */   
/*     */   static native int GetIndent(long paramLong);
/*     */   
/*     */   static native boolean IsOpen(long paramLong);
/*     */   
/*     */   static native void SetOpen(long paramLong, boolean paramBoolean);
/*     */   
/*     */   static native int GetOpenCount(long paramLong);
/*     */   
/*     */   static native String GetTitle(long paramLong);
/*     */   
/*     */   static native long GetTitleObj(long paramLong);
/*     */   
/*     */   static native void SetTitle(long paramLong, String paramString);
/*     */   
/*     */   static native long GetAction(long paramLong);
/*     */   
/*     */   static native void SetAction(long paramLong1, long paramLong2);
/*     */   
/*     */   static native void RemoveAction(long paramLong);
/*     */   
/*     */   static native int GetFlags(long paramLong);
/*     */   
/*     */   static native void SetFlags(long paramLong, int paramInt);
/*     */   
/*     */   static native double[] GetColor(long paramLong);
/*     */   
/*     */   static native void SetColor(long paramLong, double paramDouble1, double paramDouble2, double paramDouble3);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\Bookmark.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */