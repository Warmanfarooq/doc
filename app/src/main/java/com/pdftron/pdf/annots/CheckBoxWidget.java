/*     */ package com.pdftron.pdf.annots;
/*     */ 
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.Annot;
/*     */ import com.pdftron.pdf.Field;
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
/*     */ public class CheckBoxWidget
/*     */   extends Widget
/*     */ {
/*     */   public CheckBoxWidget(Obj paramObj) {
/*  25 */     super(paramObj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CheckBoxWidget() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private CheckBoxWidget(long paramLong, Object paramObject) {
/*  45 */     super(paramLong, paramObject);
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
/*     */   public CheckBoxWidget(Annot paramAnnot) throws PDFNetException {
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
/*     */   
/*     */   public static CheckBoxWidget create(Doc paramDoc, Rect paramRect, String paramString) throws PDFNetException {
/*  76 */     return new CheckBoxWidget(Create(paramDoc.__GetHandle(), paramRect.__GetHandle(), paramString), paramDoc);
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
/*     */   public static CheckBoxWidget create(Doc paramDoc, Rect paramRect) throws PDFNetException {
/*  90 */     return create(paramDoc, paramRect, "");
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
/*     */   public static CheckBoxWidget create(Doc paramDoc, Rect paramRect, Field paramField) throws PDFNetException {
/* 105 */     return new CheckBoxWidget(Create(paramDoc.__GetHandle(), paramRect.__GetHandle(), paramField.__GetHandle()), paramDoc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isChecked() throws PDFNetException {
/* 116 */     return IsChecked(__GetHandle());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setChecked(boolean paramBoolean) throws PDFNetException {
/* 127 */     SetChecked(__GetHandle(), paramBoolean);
/*     */   }
/*     */   
/*     */   static native long Create(long paramLong1, long paramLong2, String paramString);
/*     */   
/*     */   static native long Create(long paramLong1, long paramLong2, long paramLong3);
/*     */   
/*     */   static native boolean IsChecked(long paramLong);
/*     */   
/*     */   static native void SetChecked(long paramLong, boolean paramBoolean);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\annots\CheckBoxWidget.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */