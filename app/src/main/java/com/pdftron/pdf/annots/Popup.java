/*     */ package com.pdftron.pdf.annots;
/*     */ 
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.Annot;
/*     */ import com.pdftron.pdf.Rect;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Popup
/*     */   extends Annot
/*     */ {
/*     */   public Popup(Obj paramObj) {
/*  28 */     super(paramObj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Popup() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Popup(long paramLong, Object paramObject) {
/*  47 */     super(paramLong, paramObject);
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
/*     */   public Popup(Annot paramAnnot) throws PDFNetException {
/*  61 */     super(paramAnnot.getSDFObj());
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
/*     */   public static Popup create(Doc paramDoc, Rect paramRect) throws PDFNetException {
/*  75 */     return new Popup(Create(paramDoc.__GetHandle(), paramRect.__GetHandle()), paramDoc);
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
/*     */   public Annot getParent() throws PDFNetException {
/*  91 */     return __Create(GetParent(__GetHandle()), __GetRefHandle());
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
/*     */   public void setParent(Annot paramAnnot) throws PDFNetException {
/* 108 */     SetParent(__GetHandle(), paramAnnot.__GetHandle());
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
/*     */   public boolean isOpen() throws PDFNetException {
/* 125 */     return IsOpen(__GetHandle());
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
/*     */   public void setOpen(boolean paramBoolean) throws PDFNetException {
/* 143 */     SetOpen(__GetHandle(), paramBoolean);
/*     */   }
/*     */   
/*     */   static native long Create(long paramLong1, long paramLong2);
/*     */   
/*     */   static native long GetParent(long paramLong);
/*     */   
/*     */   static native void SetParent(long paramLong1, long paramLong2);
/*     */   
/*     */   static native boolean IsOpen(long paramLong);
/*     */   
/*     */   static native void SetOpen(long paramLong, boolean paramBoolean);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\annots\Popup.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */