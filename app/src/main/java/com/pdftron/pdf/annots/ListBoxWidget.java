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
/*     */ public class ListBoxWidget
/*     */   extends Widget
/*     */ {
/*     */   public ListBoxWidget(Obj paramObj) {
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
/*     */   public ListBoxWidget() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ListBoxWidget(long paramLong, Object paramObject) {
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
/*     */   public ListBoxWidget(Annot paramAnnot) throws PDFNetException {
/*  60 */     super(paramAnnot.getSDFObj());
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
/*     */   public static ListBoxWidget create(Doc paramDoc, Rect paramRect, String paramString) throws PDFNetException {
/*  75 */     return new ListBoxWidget(Create(paramDoc.__GetHandle(), paramRect.__GetHandle(), paramString), paramDoc);
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
/*     */   public static ListBoxWidget create(Doc paramDoc, Rect paramRect) throws PDFNetException {
/*  89 */     return create(paramDoc, paramRect, "");
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
/*     */   public static ListBoxWidget create(Doc paramDoc, Rect paramRect, Field paramField) throws PDFNetException {
/* 104 */     return new ListBoxWidget(Create(paramDoc.__GetHandle(), paramRect.__GetHandle(), paramField.__GetHandle()), paramDoc);
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
/* 115 */     AddOption(__GetHandle(), paramString);
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
/* 126 */     AddOptions(__GetHandle(), paramArrayOfString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSelectedOptions(String[] paramArrayOfString) throws PDFNetException {
/* 137 */     SetSelectedOptions(__GetHandle(), paramArrayOfString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getSelectedOptions() throws PDFNetException {
/* 148 */     return GetSelectedOptions(__GetHandle());
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
/* 159 */     return GetOptions(__GetHandle());
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
/* 170 */     ReplaceOptions(__GetHandle(), paramArrayOfString);
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
/* 181 */     RemoveOption(__GetHandle(), paramString);
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
/*     */   static native void SetSelectedOptions(long paramLong, String[] paramArrayOfString);
/*     */   
/*     */   static native String[] GetSelectedOptions(long paramLong);
/*     */   
/*     */   static native String[] GetOptions(long paramLong);
/*     */   
/*     */   static native void ReplaceOptions(long paramLong, String[] paramArrayOfString);
/*     */   
/*     */   static native void RemoveOption(long paramLong, String paramString);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\annots\ListBoxWidget.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */