/*     */ package com.pdftron.pdf;
/*     */ 
/*     */ import com.pdftron.common.Matrix2D;
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
/*     */ public class Page
/*     */ {
/*     */   public static final int e_action_trigger_page_open = 11;
/*     */   public static final int e_action_trigger_page_close = 12;
/*     */   public static final int e_media = 0;
/*     */   public static final int e_crop = 1;
/*     */   public static final int e_bleed = 2;
/*     */   public static final int e_trim = 3;
/*     */   public static final int e_art = 4;
/*     */   public static final int e_user_crop = 5;
/*     */   public static final int e_0 = 0;
/*     */   public static final int e_90 = 1;
/*     */   public static final int e_180 = 2;
/*     */   public static final int e_270 = 3;
/*     */   long a;
/*     */   Object b;
/*     */   
/*     */   public Page() {
/*  34 */     this.a = 0L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Page(Obj paramObj) {
/*  44 */     this.a = paramObj.__GetHandle();
/*  45 */     this.b = paramObj.__GetRefHandle();
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
/*  58 */     return IsValid(this.a);
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
/*     */   public int getIndex() throws PDFNetException {
/*  71 */     return GetIndex(this.a);
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
/*     */   public Obj getTriggerAction(int paramInt) throws PDFNetException {
/*  89 */     return Obj.__Create(GetTriggerAction(this.a, paramInt), this.b);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rect getBox(int paramInt) throws PDFNetException {
/* 156 */     return new Rect(GetBox(this.a, paramInt));
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
/*     */   public void setBox(int paramInt, Rect paramRect) throws PDFNetException {
/* 170 */     SetBox(this.a, paramInt, paramRect.a);
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
/*     */   public Rect getCropBox() throws PDFNetException {
/* 186 */     return new Rect(GetCropBox(this.a));
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
/*     */   public void setCropBox(Rect paramRect) throws PDFNetException {
/* 204 */     SetCropBox(this.a, paramRect.a);
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
/*     */   public Rect getMediaBox() throws PDFNetException {
/* 222 */     return new Rect(GetMediaBox(this.a));
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
/*     */   public void setMediaBox(Rect paramRect) throws PDFNetException {
/* 240 */     SetMediaBox(this.a, paramRect.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rect getVisibleContentBox() throws PDFNetException {
/* 251 */     return new Rect(GetVisibleContentBox(this.a));
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
/*     */   
/*     */   public static int addRotations(int paramInt1, int paramInt2) throws PDFNetException {
/* 279 */     return AddRotations(paramInt1, paramInt2);
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
/*     */   public static int subtractRotations(int paramInt1, int paramInt2) throws PDFNetException {
/* 292 */     return SubtractRotations(paramInt1, paramInt2);
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
/*     */   public static int rotationToDegree(int paramInt) throws PDFNetException {
/* 304 */     return RotationToDegree(paramInt);
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
/*     */   public static int degreeToRotation(int paramInt) throws PDFNetException {
/* 317 */     return DegreeToRotation(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRotation() throws PDFNetException {
/* 328 */     return GetRotation(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRotation(int paramInt) throws PDFNetException {
/* 339 */     SetRotation(this.a, paramInt);
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
/*     */   public double getPageWidth() throws PDFNetException {
/* 351 */     return GetPageWidth(this.a, 1);
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
/*     */   public double getPageWidth(int paramInt) throws PDFNetException {
/* 363 */     return GetPageWidth(this.a, paramInt);
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
/*     */   public double getPageHeight() throws PDFNetException {
/* 375 */     return GetPageHeight(this.a, 1);
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
/*     */   public double getPageHeight(int paramInt) throws PDFNetException {
/* 387 */     return GetPageHeight(this.a, paramInt);
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
/*     */   public Matrix2D getDefaultMatrix() throws PDFNetException {
/* 399 */     return Matrix2D.__Create(GetDefaultMatrix(this.a, false, 1, 0));
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
/*     */   public Matrix2D getDefaultMatrix(boolean paramBoolean, int paramInt1, int paramInt2) throws PDFNetException {
/* 417 */     return Matrix2D.__Create(GetDefaultMatrix(this.a, paramBoolean, paramInt1, paramInt2));
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
/*     */   public Obj getAnnots() throws PDFNetException {
/* 429 */     return Obj.__Create(GetAnnots(this.a), this.b);
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
/*     */   public int getNumAnnots() throws PDFNetException {
/* 441 */     return GetNumAnnots(this.a);
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
/*     */   public Annot getAnnot(int paramInt) throws PDFNetException {
/* 456 */     return new Annot(GetAnnot(this.a, paramInt), this.b);
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
/*     */   public void annotInsert(int paramInt, Annot paramAnnot) throws PDFNetException {
/* 470 */     AnnotInsert(this.a, paramInt, paramAnnot.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void annotPushBack(Annot paramAnnot) throws PDFNetException {
/* 481 */     AnnotPushBack(this.a, paramAnnot.a);
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
/*     */   public void annotPushFront(Annot paramAnnot) throws PDFNetException {
/* 493 */     AnnotPushFront(this.a, paramAnnot.a);
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
/*     */   public void annotRemove(Annot paramAnnot) throws PDFNetException {
/* 506 */     AnnotRemove(this.a, paramAnnot.a);
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
/*     */   public void annotRemove(int paramInt) throws PDFNetException {
/* 519 */     AnnotRemove(this.a, paramInt);
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
/*     */   public void scale(double paramDouble) throws PDFNetException {
/* 537 */     Scale(this.a, paramDouble);
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
/*     */   public void flattenField(Field paramField) throws PDFNetException {
/* 559 */     FlattenField(this.a, paramField.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasTransition() throws PDFNetException {
/* 570 */     return HasTransition(this.a);
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
/*     */   public double getUserUnitSize() throws PDFNetException {
/* 583 */     return GetUserUnitSize(this.a);
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
/*     */   public void setUserUnitSize(double paramDouble) throws PDFNetException {
/* 598 */     SetUserUnitSize(this.a, paramDouble);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Obj getResourceDict() throws PDFNetException {
/* 609 */     return Obj.__Create(GetResourceDict(this.a), this.b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Obj getContents() throws PDFNetException {
/* 620 */     return Obj.__Create(GetContents(this.a), this.b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Obj getSDFObj() {
/* 630 */     return Obj.__Create(this.a, this.b);
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
/*     */   public Obj findInheritedAttribute(String paramString) throws PDFNetException {
/* 655 */     return Obj.__Create(this.a, paramString);
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
/*     */   public Obj getThumb() throws PDFNetException {
/* 669 */     return Obj.__Create(GetThumb(this.a), this.b);
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
/*     */   public int[] getThumbInfo() throws PDFNetException {
/* 685 */     return GetThumbInfo(this.a);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Page __Create(long paramLong, Object paramObject) {
/* 690 */     return new Page(paramLong, paramObject);
/*     */   }
/*     */   
/*     */   Page(long paramLong, Object paramObject) {
/* 694 */     this.a = paramLong;
/* 695 */     this.b = paramObject;
/*     */   }
/*     */ 
/*     */   
/*     */   public long __GetHandle() {
/* 700 */     return this.a;
/*     */   }
/*     */   
/*     */   static native long GetTriggerAction(long paramLong, int paramInt);
/*     */   
/*     */   static native boolean IsValid(long paramLong);
/*     */   
/*     */   static native int GetIndex(long paramLong);
/*     */   
/*     */   static native long GetBox(long paramLong, int paramInt);
/*     */   
/*     */   static native void SetBox(long paramLong1, int paramInt, long paramLong2);
/*     */   
/*     */   static native long GetCropBox(long paramLong);
/*     */   
/*     */   static native void SetCropBox(long paramLong1, long paramLong2);
/*     */   
/*     */   static native long GetMediaBox(long paramLong);
/*     */   
/*     */   static native void SetMediaBox(long paramLong1, long paramLong2);
/*     */   
/*     */   static native long GetVisibleContentBox(long paramLong);
/*     */   
/*     */   static native int AddRotations(int paramInt1, int paramInt2);
/*     */   
/*     */   static native int SubtractRotations(int paramInt1, int paramInt2);
/*     */   
/*     */   static native int RotationToDegree(int paramInt);
/*     */   
/*     */   static native int DegreeToRotation(int paramInt);
/*     */   
/*     */   static native int GetRotation(long paramLong);
/*     */   
/*     */   static native void SetRotation(long paramLong, int paramInt);
/*     */   
/*     */   static native double GetPageWidth(long paramLong, int paramInt);
/*     */   
/*     */   static native double GetPageHeight(long paramLong, int paramInt);
/*     */   
/*     */   static native long GetDefaultMatrix(long paramLong, boolean paramBoolean, int paramInt1, int paramInt2);
/*     */   
/*     */   static native long GetAnnots(long paramLong);
/*     */   
/*     */   static native int GetNumAnnots(long paramLong);
/*     */   
/*     */   static native long GetAnnot(long paramLong, int paramInt);
/*     */   
/*     */   static native void AnnotInsert(long paramLong1, int paramInt, long paramLong2);
/*     */   
/*     */   static native void AnnotPushBack(long paramLong1, long paramLong2);
/*     */   
/*     */   static native void AnnotPushFront(long paramLong1, long paramLong2);
/*     */   
/*     */   static native void AnnotRemove(long paramLong1, long paramLong2);
/*     */   
/*     */   static native void AnnotRemove(long paramLong, int paramInt);
/*     */   
/*     */   static native void Scale(long paramLong, double paramDouble);
/*     */   
/*     */   static native void FlattenField(long paramLong1, long paramLong2);
/*     */   
/*     */   static native boolean HasTransition(long paramLong);
/*     */   
/*     */   static native double GetUserUnitSize(long paramLong);
/*     */   
/*     */   static native void SetUserUnitSize(long paramLong, double paramDouble);
/*     */   
/*     */   static native long GetResourceDict(long paramLong);
/*     */   
/*     */   static native long GetContents(long paramLong);
/*     */   
/*     */   static native int[] GetThumbInfo(long paramLong);
/*     */   
/*     */   static native long GetThumb(long paramLong);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\Page.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */