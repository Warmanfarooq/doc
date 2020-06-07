/*     */ package com.pdftron.pdf.annots;
/*     */ 
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.Annot;
/*     */ import com.pdftron.pdf.DigitalSignatureField;
/*     */ import com.pdftron.pdf.Field;
/*     */ import com.pdftron.pdf.Image;
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
/*     */ public class SignatureWidget
/*     */   extends Widget
/*     */ {
/*     */   public SignatureWidget(Obj paramObj) {
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
/*     */   public SignatureWidget() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SignatureWidget(long paramLong, Object paramObject) {
/*  44 */     super(paramLong, paramObject);
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
/*     */   public SignatureWidget(Annot paramAnnot) throws PDFNetException {
/*  60 */     super(paramAnnot.getSDFObj());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DigitalSignatureField getDigitalSignatureField() throws PDFNetException {
/*  70 */     return DigitalSignatureField.__Create(GetDigitalSignatureField(__GetHandle()), __GetRefHandle());
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
/*     */   public void createSignatureAppearance(Image paramImage) throws PDFNetException {
/*  82 */     CreateSignatureAppearance(__GetHandle(), paramImage.__GetHandle());
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
/*     */   public static SignatureWidget create(Doc paramDoc, Rect paramRect, String paramString) throws PDFNetException {
/*  96 */     return new SignatureWidget(CreateSigWidgetAndFieldWithName(paramDoc.__GetHandle(), paramRect.__GetHandle(), paramString), paramDoc);
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
/*     */   public static SignatureWidget create(Doc paramDoc, Rect paramRect) throws PDFNetException {
/* 108 */     return new SignatureWidget(CreateSigWidgetAndFieldWithName(paramDoc.__GetHandle(), paramRect.__GetHandle()), paramDoc);
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
/*     */   public static SignatureWidget create(Doc paramDoc, Rect paramRect, Field paramField) throws PDFNetException {
/* 122 */     return new SignatureWidget(CreateFromField(paramDoc.__GetHandle(), paramRect.__GetHandle(), paramField.__GetHandle()), paramDoc);
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
/*     */   public static SignatureWidget create(Doc paramDoc, Rect paramRect, DigitalSignatureField paramDigitalSignatureField) throws PDFNetException {
/* 136 */     return new SignatureWidget(CreateFromDigitalSignatureField(paramDoc.__GetHandle(), paramRect.__GetHandle(), paramDigitalSignatureField.__GetHandle()), paramDoc);
/*     */   }
/*     */   
/*     */   static native long GetDigitalSignatureField(long paramLong);
/*     */   
/*     */   static native void CreateSignatureAppearance(long paramLong1, long paramLong2);
/*     */   
/*     */   static native long CreateSigWidgetAndFieldWithName(long paramLong1, long paramLong2, String paramString);
/*     */   
/*     */   static native long CreateSigWidgetAndFieldWithName(long paramLong1, long paramLong2);
/*     */   
/*     */   static native long CreateFromField(long paramLong1, long paramLong2, long paramLong3);
/*     */   
/*     */   static native long CreateFromDigitalSignatureField(long paramLong1, long paramLong2, long paramLong3);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\annots\SignatureWidget.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */