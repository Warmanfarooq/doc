/*     */ package com.pdftron.pdf.annots;
/*     */ 
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.Field;
/*     */ import com.pdftron.pdf.Page;
/*     */ import com.pdftron.pdf.Rect;
/*     */ import com.pdftron.sdf.Doc;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RadioButtonGroup
/*     */ {
/*     */   private long a;
/*     */   private Object b;
/*     */   
/*     */   public RadioButtonGroup(Field paramField) {
/*  25 */     this.a = CreateFromField(paramField.__GetHandle());
/*  26 */     this.b = paramField.__GetRefHandle();
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
/*     */   public static RadioButtonGroup create(Doc paramDoc, String paramString) throws PDFNetException {
/*  39 */     return new RadioButtonGroup(Create(paramDoc.__GetHandle(), paramString), paramDoc);
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
/*     */   public static RadioButtonGroup create(Doc paramDoc) throws PDFNetException {
/*  51 */     return create(paramDoc, "");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RadioButtonGroup() {
/*  59 */     this.a = 0L;
/*  60 */     this.b = null;
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
/*     */   public RadioButtonWidget add(Rect paramRect, String paramString) throws PDFNetException {
/*  75 */     return new RadioButtonWidget(Add(this.a, paramRect.__GetHandle(), paramString), this.b);
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
/*     */   public RadioButtonWidget add(Rect paramRect) throws PDFNetException {
/*  88 */     return add(paramRect, "");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNumButtons() throws PDFNetException {
/*  99 */     return GetNumButtons(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RadioButtonWidget getButton(int paramInt) throws PDFNetException {
/* 110 */     return new RadioButtonWidget(GetButton(this.a, paramInt), this.b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Field getField() throws PDFNetException {
/* 120 */     return Field.__Create(GetField(this.a), this.b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addGroupButtonsToPage(Page paramPage) throws PDFNetException {
/* 130 */     AddGroupButtonsToPage(this.a, paramPage.__GetHandle());
/*     */   }
/*     */   
/*     */   public void destroy() throws PDFNetException {
/* 134 */     if (this.a != 0L) {
/* 135 */       Destroy(this.a);
/* 136 */       this.a = 0L;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void finalize() throws Throwable {
/* 141 */     destroy();
/*     */   }
/*     */ 
/*     */   
/*     */   RadioButtonGroup(long paramLong, Object paramObject) {
/* 146 */     this.a = paramLong;
/* 147 */     this.b = paramObject;
/*     */   }
/*     */   
/*     */   static native long CreateFromField(long paramLong);
/*     */   
/*     */   static native long Create(long paramLong, String paramString);
/*     */   
/*     */   static native long Add(long paramLong1, long paramLong2, String paramString);
/*     */   
/*     */   static native int GetNumButtons(long paramLong);
/*     */   
/*     */   static native long GetButton(long paramLong, int paramInt);
/*     */   
/*     */   static native long GetField(long paramLong);
/*     */   
/*     */   static native void AddGroupButtonsToPage(long paramLong1, long paramLong2);
/*     */   
/*     */   static native void Destroy(long paramLong);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\annots\RadioButtonGroup.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */