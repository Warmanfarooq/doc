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
/*     */ public class ComboBoxWidget
/*     */   extends Widget
/*     */ {
/*     */   public ComboBoxWidget(Obj paramObj) {
/*  24 */     super(paramObj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ComboBoxWidget() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ComboBoxWidget(long paramLong, Object paramObject) {
/*  43 */     super(paramLong, paramObject);
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
/*     */   public ComboBoxWidget(Annot paramAnnot) throws PDFNetException {
/*  58 */     super(paramAnnot.getSDFObj());
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
/*     */   public static ComboBoxWidget create(Doc paramDoc, Rect paramRect, String paramString) throws PDFNetException {
/*  73 */     return new ComboBoxWidget(Create(paramDoc.__GetHandle(), paramRect.__GetHandle(), paramString), paramDoc);
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
/*     */   public static ComboBoxWidget create(Doc paramDoc, Rect paramRect) throws PDFNetException {
/*  87 */     return create(paramDoc, paramRect, "");
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
/*     */   public static ComboBoxWidget create(Doc paramDoc, Rect paramRect, Field paramField) throws PDFNetException {
/* 102 */     return new ComboBoxWidget(Create(paramDoc.__GetHandle(), paramRect.__GetHandle(), paramField.__GetHandle()), paramDoc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addOption(String paramString) throws PDFNetException {
/* 113 */     AddOption(__GetHandle(), paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addOptions(String[] paramArrayOfString) throws PDFNetException {
/* 124 */     AddOptions(__GetHandle(), paramArrayOfString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSelectedOption(String paramString) throws PDFNetException {
/* 135 */     SetSelectedOption(__GetHandle(), paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSelectedOption() throws PDFNetException {
/* 146 */     return GetSelectedOption(__GetHandle());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getOptions() throws PDFNetException {
/* 157 */     return GetOptions(__GetHandle());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void replaceOptions(String[] paramArrayOfString) throws PDFNetException {
/* 168 */     ReplaceOptions(__GetHandle(), paramArrayOfString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeOption(String paramString) throws PDFNetException {
/* 179 */     RemoveOption(__GetHandle(), paramString);
/*     */   }
/*     */   
/*     */   static native long Create(long paramLong1, long paramLong2, String paramString);
/*     */   
/*     */   static native long Create(long paramLong1, long paramLong2, long paramLong3);
/*     */   
/*     */   static native void AddOption(long paramLong, String paramString);
/*     */   
/*     */   static native void AddOptions(long paramLong, String[] paramArrayOfString);
/*     */   
/*     */   static native void SetSelectedOption(long paramLong, String paramString);
/*     */   
/*     */   static native String GetSelectedOption(long paramLong);
/*     */   
/*     */   static native String[] GetOptions(long paramLong);
/*     */   
/*     */   static native void ReplaceOptions(long paramLong, String[] paramArrayOfString);
/*     */   
/*     */   static native void RemoveOption(long paramLong, String paramString);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\annots\ComboBoxWidget.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */