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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ElementBuilder
/*     */   extends h
/*     */ {
/*     */   public ElementBuilder() throws PDFNetException {
/*  27 */     this.c = ElementBuilderCreate();
/*  28 */     clearList();
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
/*     */   public void destroy() throws PDFNetException {
/*  42 */     if (this.c != 0L) {
/*     */       
/*  44 */       Destroy(this.c);
/*  45 */       this.c = 0L;
/*     */     } 
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
/*     */   public void reset() throws PDFNetException {
/*  66 */     Reset(this.c, 0L);
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
/*     */   public void reset(GState paramGState) throws PDFNetException {
/*  89 */     Reset(this.c, paramGState.a);
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
/*     */   public Element createImage(Image paramImage) throws PDFNetException {
/* 103 */     return new Element(CreateImage(this.c, paramImage.a), this, paramImage.b);
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
/*     */   public Element createImage(Image paramImage, Matrix2D paramMatrix2D) throws PDFNetException {
/* 116 */     return new Element(CreateImage(this.c, paramImage.a, paramMatrix2D.__GetHandle()), this, paramImage.b);
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
/*     */   public Element createImage(Image paramImage, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) throws PDFNetException {
/* 133 */     return new Element(CreateImage(this.c, paramImage.a, paramDouble1, paramDouble2, paramDouble3, paramDouble4), this, paramImage.b);
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
/*     */   public Element createGroupBegin() throws PDFNetException {
/* 145 */     return new Element(CreateGroupBegin(this.c), this, null);
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
/*     */   public Element createGroupEnd() throws PDFNetException {
/* 157 */     return new Element(CreateGroupEnd(this.c), this, null);
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
/*     */   public Element createShading(Shading paramShading) throws PDFNetException {
/* 169 */     return new Element(CreateShading(this.c, paramShading.a), this, paramShading.b);
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
/*     */   public Element createForm(Obj paramObj) throws PDFNetException {
/* 181 */     return new Element(CreateFormObj(this.c, paramObj.__GetHandle()), this, paramObj
/* 182 */         .__GetRefHandle());
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
/*     */   public Element createForm(Page paramPage) throws PDFNetException {
/* 197 */     return new Element(CreateForm(this.c, paramPage.a), this, paramPage.b);
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
/*     */   public Element createForm(Page paramPage, PDFDoc paramPDFDoc) throws PDFNetException {
/* 212 */     return new Element(CreateForm(this.c, paramPage.a, paramPDFDoc.__GetHandle()), this, paramPDFDoc);
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
/*     */   public Element createTextBegin(Font paramFont, double paramDouble) throws PDFNetException {
/* 226 */     return new Element(CreateTextBegin(this.c, paramFont.a, paramDouble), this, paramFont.b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Element createTextBegin() throws PDFNetException {
/* 237 */     return new Element(CreateTextBegin(this.c), this, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Element createTextEnd() throws PDFNetException {
/* 248 */     return new Element(CreateTextEnd(this.c), this, null);
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
/*     */   public Element createTextRun(String paramString, Font paramFont, double paramDouble) throws PDFNetException {
/* 264 */     return new Element(CreateTextRun(this.c, paramString, paramFont.a, paramDouble), this, paramFont.b);
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
/*     */   public Element createTextRun(String paramString) throws PDFNetException {
/* 283 */     return new Element(CreateTextRun(this.c, paramString), this, null);
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
/*     */   public Element createUnicodeTextRun(String paramString) throws PDFNetException {
/* 300 */     return new Element(CreateUnicodeTextRun(this.c, paramString), this, null);
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
/*     */   public Element createTextNewLine(double paramDouble1, double paramDouble2) throws PDFNetException {
/* 316 */     return new Element(CreateTextNewLine(this.c, paramDouble1, paramDouble2), this, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Element createTextNewLine() throws PDFNetException {
/* 327 */     return new Element(CreateTextNewLine(this.c), this, null);
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
/*     */   public Element createPath(double[] paramArrayOfdouble, byte[] paramArrayOfbyte) throws PDFNetException {
/* 342 */     return new Element(CreatePath(this.c, paramArrayOfdouble, paramArrayOfbyte), this, null);
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
/*     */   public Element createRect(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) throws PDFNetException {
/* 357 */     return new Element(CreateRect(this.c, paramDouble1, paramDouble2, paramDouble3, paramDouble4), this, null);
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
/*     */   public Element createEllipse(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) throws PDFNetException {
/* 372 */     return new Element(CreateEllipse(this.c, paramDouble1, paramDouble2, paramDouble3, paramDouble4), this, null);
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
/*     */   public void pathBegin() throws PDFNetException {
/* 384 */     PathBegin(this.c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Element pathEnd() throws PDFNetException {
/* 395 */     return new Element(PathEnd(this.c), this, null);
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
/*     */   public void rect(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) throws PDFNetException {
/* 410 */     Rect(this.c, paramDouble1, paramDouble2, paramDouble3, paramDouble4);
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
/*     */   public void moveTo(double paramDouble1, double paramDouble2) throws PDFNetException {
/* 422 */     MoveTo(this.c, paramDouble1, paramDouble2);
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
/*     */   public void lineTo(double paramDouble1, double paramDouble2) throws PDFNetException {
/* 434 */     LineTo(this.c, paramDouble1, paramDouble2);
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
/*     */   public void curveTo(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6) throws PDFNetException {
/* 451 */     CurveTo(this.c, paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6);
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
/*     */   public void arcTo(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6) throws PDFNetException {
/* 467 */     ArcTo(this.c, paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6);
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
/*     */   public void arcTo(double paramDouble1, double paramDouble2, double paramDouble3, boolean paramBoolean1, boolean paramBoolean2, double paramDouble4, double paramDouble5) throws PDFNetException {
/* 493 */     ArcTo(this.c, paramDouble1, paramDouble2, paramDouble3, paramBoolean1, paramBoolean2, paramDouble4, paramDouble5);
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
/*     */   public void ellipse(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) throws PDFNetException {
/* 508 */     Ellipse(this.c, paramDouble1, paramDouble2, paramDouble3, paramDouble4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void closePath() throws PDFNetException {
/* 518 */     ClosePath(this.c);
/*     */   }
/*     */   
/*     */   static native long ElementBuilderCreate();
/*     */   
/*     */   static native void Destroy(long paramLong);
/*     */   
/*     */   static native void Reset(long paramLong1, long paramLong2);
/*     */   
/*     */   static native long CreateImage(long paramLong1, long paramLong2);
/*     */   
/*     */   static native long CreateImage(long paramLong1, long paramLong2, long paramLong3);
/*     */   
/*     */   static native long CreateImage(long paramLong1, long paramLong2, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4);
/*     */   
/*     */   static native long CreateGroupBegin(long paramLong);
/*     */   
/*     */   static native long CreateGroupEnd(long paramLong);
/*     */   
/*     */   static native long CreateShading(long paramLong1, long paramLong2);
/*     */   
/*     */   static native long CreateFormObj(long paramLong1, long paramLong2);
/*     */   
/*     */   static native long CreateForm(long paramLong1, long paramLong2);
/*     */   
/*     */   static native long CreateForm(long paramLong1, long paramLong2, long paramLong3);
/*     */   
/*     */   static native long CreateTextBegin(long paramLong1, long paramLong2, double paramDouble);
/*     */   
/*     */   static native long CreateTextBegin(long paramLong);
/*     */   
/*     */   static native long CreateTextEnd(long paramLong);
/*     */   
/*     */   static native long CreateTextRun(long paramLong1, String paramString, long paramLong2, double paramDouble);
/*     */   
/*     */   static native long CreateTextRun(long paramLong, String paramString);
/*     */   
/*     */   static native long CreateUnicodeTextRun(long paramLong, String paramString);
/*     */   
/*     */   static native long CreateTextNewLine(long paramLong, double paramDouble1, double paramDouble2);
/*     */   
/*     */   static native long CreateTextNewLine(long paramLong);
/*     */   
/*     */   static native long CreatePath(long paramLong, double[] paramArrayOfdouble, byte[] paramArrayOfbyte);
/*     */   
/*     */   static native long CreateRect(long paramLong, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4);
/*     */   
/*     */   static native long CreateEllipse(long paramLong, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4);
/*     */   
/*     */   static native void PathBegin(long paramLong);
/*     */   
/*     */   static native long PathEnd(long paramLong);
/*     */   
/*     */   static native void Rect(long paramLong, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4);
/*     */   
/*     */   static native void MoveTo(long paramLong, double paramDouble1, double paramDouble2);
/*     */   
/*     */   static native void LineTo(long paramLong, double paramDouble1, double paramDouble2);
/*     */   
/*     */   static native void CurveTo(long paramLong, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6);
/*     */   
/*     */   static native void ArcTo(long paramLong, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6);
/*     */   
/*     */   static native void ArcTo(long paramLong, double paramDouble1, double paramDouble2, double paramDouble3, boolean paramBoolean1, boolean paramBoolean2, double paramDouble4, double paramDouble5);
/*     */   
/*     */   static native void Ellipse(long paramLong, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4);
/*     */   
/*     */   static native void ClosePath(long paramLong);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\ElementBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */