/*     */ package com.pdftron.pdf.annots;
/*     */ 
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.Annot;
/*     */ import com.pdftron.pdf.Point;
/*     */ import com.pdftron.pdf.Rect;
/*     */ import com.pdftron.sdf.Doc;
/*     */ import com.pdftron.sdf.Obj;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Line
/*     */   extends Markup
/*     */ {
/*     */   public static final int e_Square = 0;
/*     */   public static final int e_Circle = 1;
/*     */   public static final int e_Diamond = 2;
/*     */   public static final int e_OpenArrow = 3;
/*     */   public static final int e_ClosedArrow = 4;
/*     */   public static final int e_Butt = 5;
/*     */   public static final int e_ROpenArrow = 6;
/*     */   public static final int e_RClosedArrow = 7;
/*     */   
/*     */   public Line(Obj paramObj) {
/*  27 */     super(paramObj);
/*     */   }
/*     */ 
/*     */   
/*     */   public static final int e_Slash = 8;
/*     */   
/*     */   public static final int e_None = 9;
/*     */   
/*     */   public static final int e_Unknown = 10;
/*     */   
/*     */   public static final int e_LineArrow = 0;
/*     */   public static final int e_LineDimension = 1;
/*     */   public static final int e_null = 2;
/*     */   public static final int e_Inline = 0;
/*     */   public static final int e_Top = 1;
/*     */   
/*     */   public Line() {}
/*     */   
/*     */   Line(long paramLong, Object paramObject) {
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
/*     */   public Line(Annot paramAnnot) throws PDFNetException {
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
/*     */   public static Line create(Doc paramDoc, Rect paramRect) throws PDFNetException {
/*  76 */     return new Line(Create(paramDoc.__GetHandle(), paramRect.__GetHandle()), paramDoc);
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
/*     */   public Point getStartPoint() throws PDFNetException {
/*  88 */     return new Point(GetStartPointx(__GetHandle()), GetStartPointy(__GetHandle()));
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
/*     */   public void setStartPoint(Point paramPoint) throws PDFNetException {
/* 102 */     SetStartPoint(__GetHandle(), paramPoint.x, paramPoint.y);
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
/*     */   public Point getEndPoint() throws PDFNetException {
/* 114 */     return new Point(GetEndPointx(__GetHandle()), GetEndPointy(__GetHandle()));
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
/*     */   public void setEndPoint(Point paramPoint) throws PDFNetException {
/* 127 */     SetEndPoint(__GetHandle(), paramPoint.x, paramPoint.y);
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
/*     */   public int getStartStyle() throws PDFNetException {
/* 179 */     return GetStartStyle(__GetHandle());
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
/*     */   public void setStartStyle(int paramInt) throws PDFNetException {
/* 195 */     SetStartStyle(__GetHandle(), paramInt);
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
/*     */   public int getEndStyle() throws PDFNetException {
/* 210 */     return GetEndStyle(__GetHandle());
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
/*     */   public void setEndStyle(int paramInt) throws PDFNetException {
/* 226 */     SetEndStyle(__GetHandle(), paramInt);
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
/*     */   public double getLeaderLineLength() throws PDFNetException {
/* 247 */     return GetLeaderLineLength(__GetHandle());
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
/*     */   public void setLeaderLineLength(double paramDouble) throws PDFNetException {
/* 267 */     SetLeaderLineLength(__GetHandle(), paramDouble);
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
/*     */   public double getLeaderLineExtensionLength() throws PDFNetException {
/* 285 */     return GetLeaderLineExtensionLength(__GetHandle());
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
/*     */   public void setLeaderLineExtensionLength(double paramDouble) throws PDFNetException {
/* 304 */     SetLeaderLineExtensionLength(__GetHandle(), paramDouble);
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
/*     */   public boolean getShowCaption() throws PDFNetException {
/* 321 */     return GetShowCaption(__GetHandle());
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
/*     */   public void setShowCaption(boolean paramBoolean) throws PDFNetException {
/* 338 */     SetShowCaption(__GetHandle(), paramBoolean);
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
/*     */   public int getIntentType() throws PDFNetException {
/* 370 */     return GetIntentType(__GetHandle());
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
/*     */   public void setIntentType(int paramInt) throws PDFNetException {
/* 386 */     SetIntentType(__GetHandle(), paramInt);
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
/*     */   public double getLeaderLineOffset() throws PDFNetException {
/* 402 */     return GetLeaderLineOffset(__GetHandle());
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
/*     */   public void setLeaderLineOffset(double paramDouble) throws PDFNetException {
/* 419 */     SetLeaderLineOffset(__GetHandle(), paramDouble);
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
/*     */   public int getCaptionPosition() throws PDFNetException {
/* 449 */     return GetCaptionPosition(__GetHandle());
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
/*     */   public void setCaptionPosition(int paramInt) throws PDFNetException {
/* 466 */     SetCapPos(__GetHandle(), paramInt);
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
/*     */   public double getTextHOffset() throws PDFNetException {
/* 482 */     return GetTextHOffset(__GetHandle());
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
/*     */   public void setTextHOffset(double paramDouble) throws PDFNetException {
/* 499 */     SetTextHOffset(__GetHandle(), paramDouble);
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
/*     */   public double getTextVOffset() throws PDFNetException {
/* 516 */     return GetTextVOffset(__GetHandle());
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
/*     */   public void setTextVOffset(double paramDouble) throws PDFNetException {
/* 534 */     SetTextVOffset(__GetHandle(), paramDouble);
/*     */   }
/*     */   
/*     */   static native long Create(long paramLong1, long paramLong2);
/*     */   
/*     */   static native double GetStartPointx(long paramLong);
/*     */   
/*     */   static native double GetStartPointy(long paramLong);
/*     */   
/*     */   static native void SetStartPoint(long paramLong, double paramDouble1, double paramDouble2);
/*     */   
/*     */   static native double GetEndPointx(long paramLong);
/*     */   
/*     */   static native double GetEndPointy(long paramLong);
/*     */   
/*     */   static native void SetEndPoint(long paramLong, double paramDouble1, double paramDouble2);
/*     */   
/*     */   static native int GetStartStyle(long paramLong);
/*     */   
/*     */   static native void SetStartStyle(long paramLong, int paramInt);
/*     */   
/*     */   static native int GetEndStyle(long paramLong);
/*     */   
/*     */   static native void SetEndStyle(long paramLong, int paramInt);
/*     */   
/*     */   static native double GetLeaderLineLength(long paramLong);
/*     */   
/*     */   static native void SetLeaderLineLength(long paramLong, double paramDouble);
/*     */   
/*     */   static native double GetLeaderLineExtensionLength(long paramLong);
/*     */   
/*     */   static native void SetLeaderLineExtensionLength(long paramLong, double paramDouble);
/*     */   
/*     */   static native boolean GetShowCaption(long paramLong);
/*     */   
/*     */   static native void SetShowCaption(long paramLong, boolean paramBoolean);
/*     */   
/*     */   static native int GetIntentType(long paramLong);
/*     */   
/*     */   static native void SetIntentType(long paramLong, int paramInt);
/*     */   
/*     */   static native double GetLeaderLineOffset(long paramLong);
/*     */   
/*     */   static native void SetLeaderLineOffset(long paramLong, double paramDouble);
/*     */   
/*     */   static native int GetCaptionPosition(long paramLong);
/*     */   
/*     */   static native void SetCapPos(long paramLong, int paramInt);
/*     */   
/*     */   static native double GetTextHOffset(long paramLong);
/*     */   
/*     */   static native void SetTextHOffset(long paramLong, double paramDouble);
/*     */   
/*     */   static native double GetTextVOffset(long paramLong);
/*     */   
/*     */   static native void SetTextVOffset(long paramLong, double paramDouble);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\annots\Line.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */