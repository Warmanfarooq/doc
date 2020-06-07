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
/*     */ public class TextWidget
/*     */   extends Widget
/*     */ {
/*     */   public TextWidget(Obj paramObj) {
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
/*     */   public TextWidget() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private TextWidget(long paramLong, Object paramObject) {
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
/*     */   public TextWidget(Annot paramAnnot) throws PDFNetException {
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
/*     */   public static TextWidget create(Doc paramDoc, Rect paramRect, String paramString) throws PDFNetException {
/*  76 */     return new TextWidget(Create(paramDoc.__GetHandle(), paramRect.__GetHandle(), paramString), paramDoc);
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
/*     */   public static TextWidget create(Doc paramDoc, Rect paramRect) throws PDFNetException {
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
/*     */   public static TextWidget create(Doc paramDoc, Rect paramRect, Field paramField) throws PDFNetException {
/* 105 */     return new TextWidget(Create(paramDoc.__GetHandle(), paramRect.__GetHandle(), paramField.__GetHandle()), paramDoc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setText(String paramString) throws PDFNetException {
/* 116 */     SetText(__GetHandle(), paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getText() throws PDFNetException {
/* 127 */     return GetText(__GetHandle());
/*     */   }
/*     */   
/*     */   static native long Create(long paramLong1, long paramLong2, String paramString);
/*     */   
/*     */   static native long Create(long paramLong1, long paramLong2, long paramLong3);
/*     */   
/*     */   static native void SetText(long paramLong, String paramString);
/*     */   
/*     */   static native String GetText(long paramLong);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\annots\TextWidget.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */