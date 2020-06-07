/*     */ package com.pdftron.pdf.annots;
/*     */ 
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.Annot;
/*     */ import com.pdftron.pdf.ColorPt;
/*     */ import com.pdftron.pdf.Point;
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
/*     */ public class FreeText
/*     */   extends Markup
/*     */ {
/*     */   public static final int e_FreeText = 0;
/*     */   public static final int e_FreeTextCallout = 1;
/*     */   public static final int e_FreeTextTypeWriter = 2;
/*     */   public static final int e_Unknown = 3;
/*     */   
/*     */   public FreeText(Obj paramObj) {
/*  27 */     super(paramObj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FreeText() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private FreeText(long paramLong, Object paramObject) {
/*  46 */     super(paramLong, paramObject);
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
/*     */   public FreeText(Annot paramAnnot) throws PDFNetException {
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
/*     */   public static FreeText create(Doc paramDoc, Rect paramRect) throws PDFNetException {
/*  75 */     return new FreeText(Create(paramDoc.__GetHandle(), paramRect.__GetHandle()), paramDoc);
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
/*     */   public String getDefaultAppearance() throws PDFNetException {
/*  91 */     return GetDefaultAppearance(__GetHandle());
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
/*     */   public void setDefaultAppearance(String paramString) throws PDFNetException {
/* 107 */     SetDefaultAppearance(__GetHandle(), paramString);
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
/*     */   public int getQuaddingFormat() throws PDFNetException {
/* 126 */     return GetQuaddingFormat(__GetHandle());
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
/*     */   public void setQuaddingFormat(int paramInt) throws PDFNetException {
/* 148 */     SetQuaddingFormat(__GetHandle(), paramInt);
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
/*     */   public Point getCalloutLinePoint1() throws PDFNetException {
/* 170 */     return new Point(GetCalloutLinePoint1x(__GetHandle()), GetCalloutLinePoint1y(__GetHandle()));
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
/*     */   public Point getCalloutLinePoint2() throws PDFNetException {
/* 192 */     return new Point(GetCalloutLinePoint2x(__GetHandle()), GetCalloutLinePoint2y(__GetHandle()));
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
/*     */   public Point getCalloutLinePoint3() throws PDFNetException {
/* 214 */     return new Point(GetCalloutLinePoint3x(__GetHandle()), GetCalloutLinePoint3y(__GetHandle()));
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
/*     */   public void setCalloutLinePoints(Point paramPoint1, Point paramPoint2, Point paramPoint3) throws PDFNetException {
/* 234 */     SetCalloutLinePoints(__GetHandle(), paramPoint1.x, paramPoint1.y, paramPoint2.x, paramPoint2.y, paramPoint3.x, paramPoint3.y);
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
/*     */   public void setCalloutLinePoints(Point paramPoint1, Point paramPoint2) throws PDFNetException {
/* 252 */     SetCalloutLinePoints(__GetHandle(), paramPoint1.x, paramPoint1.y, paramPoint2.x, paramPoint2.y);
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
/*     */   public int getIntentName() throws PDFNetException {
/* 299 */     return GetIntentName(__GetHandle());
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
/*     */   public void setIntentName(int paramInt) throws PDFNetException {
/* 324 */     SetIntentName(__GetHandle(), paramInt);
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
/*     */   public int getEndingStyle() throws PDFNetException {
/* 342 */     return GetEndingStyle(__GetHandle());
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
/*     */   public void setEndingStyle(int paramInt) throws PDFNetException {
/* 362 */     SetEndingStyle(__GetHandle(), paramInt);
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
/*     */   public void setEndingStyle(String paramString) throws PDFNetException {
/* 381 */     SetEndingStyle(__GetHandle(), paramString);
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
/*     */   public void setTextColor(ColorPt paramColorPt, int paramInt) throws PDFNetException {
/* 401 */     SetTextColor(__GetHandle(), paramColorPt.__GetHandle(), paramInt);
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
/*     */   public ColorPt getTextColor() throws PDFNetException {
/* 417 */     return ColorPt.__Create(GetTextColor(__GetHandle()));
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
/*     */   public int getTextColorCompNum() throws PDFNetException {
/* 429 */     return GetTextColorCompNum(__GetHandle());
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
/*     */   public void setLineColor(ColorPt paramColorPt, int paramInt) throws PDFNetException {
/* 448 */     SetLineColor(__GetHandle(), paramColorPt.__GetHandle(), paramInt);
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
/*     */   public ColorPt getLineColor() throws PDFNetException {
/* 464 */     return ColorPt.__Create(GetLineColor(__GetHandle()));
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
/*     */   public int getLineColorCompNum() throws PDFNetException {
/* 476 */     return GetLineColorCompNum(__GetHandle());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFontSize(double paramDouble) throws PDFNetException {
/* 487 */     SetFontSize(__GetHandle(), paramDouble);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getFontSize() throws PDFNetException {
/* 498 */     return GetFontSize(__GetHandle());
/*     */   }
/*     */   
/*     */   static native long Create(long paramLong1, long paramLong2);
/*     */   
/*     */   static native String GetDefaultAppearance(long paramLong);
/*     */   
/*     */   static native void SetDefaultAppearance(long paramLong, String paramString);
/*     */   
/*     */   static native int GetQuaddingFormat(long paramLong);
/*     */   
/*     */   static native void SetQuaddingFormat(long paramLong, int paramInt);
/*     */   
/*     */   static native double GetCalloutLinePoint1x(long paramLong);
/*     */   
/*     */   static native double GetCalloutLinePoint1y(long paramLong);
/*     */   
/*     */   static native double GetCalloutLinePoint2x(long paramLong);
/*     */   
/*     */   static native double GetCalloutLinePoint2y(long paramLong);
/*     */   
/*     */   static native double GetCalloutLinePoint3x(long paramLong);
/*     */   
/*     */   static native double GetCalloutLinePoint3y(long paramLong);
/*     */   
/*     */   static native void SetCalloutLinePoints(long paramLong, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6);
/*     */   
/*     */   static native void SetCalloutLinePoints(long paramLong, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4);
/*     */   
/*     */   static native int GetIntentName(long paramLong);
/*     */   
/*     */   static native void SetIntentName(long paramLong, int paramInt);
/*     */   
/*     */   static native int GetEndingStyle(long paramLong);
/*     */   
/*     */   static native void SetEndingStyle(long paramLong, int paramInt);
/*     */   
/*     */   static native void SetEndingStyle(long paramLong, String paramString);
/*     */   
/*     */   static native void SetTextColor(long paramLong1, long paramLong2, int paramInt);
/*     */   
/*     */   static native long GetTextColor(long paramLong);
/*     */   
/*     */   static native int GetTextColorCompNum(long paramLong);
/*     */   
/*     */   static native void SetLineColor(long paramLong1, long paramLong2, int paramInt);
/*     */   
/*     */   static native long GetLineColor(long paramLong);
/*     */   
/*     */   static native int GetLineColorCompNum(long paramLong);
/*     */   
/*     */   static native void SetFontSize(long paramLong, double paramDouble);
/*     */   
/*     */   static native double GetFontSize(long paramLong);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\annots\FreeText.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */