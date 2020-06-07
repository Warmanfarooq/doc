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
/*     */ public class Ink
/*     */   extends Markup
/*     */ {
/*     */   public enum BlendMode
/*     */   {
/*  16 */     COMPATIBLE(0),
/*  17 */     NORMAL(1),
/*  18 */     MULTIPLY(2),
/*  19 */     SCREEN(3),
/*  20 */     DIFFERENCE(4),
/*  21 */     DARKEN(5),
/*  22 */     LIGHTEN(6),
/*  23 */     COLOR_DODGE(7),
/*  24 */     COLOR_BURN(8),
/*  25 */     EXCLUSION(9),
/*  26 */     HARD_LIGHT(10),
/*  27 */     OVERLAY(11),
/*  28 */     SOFT_LIGHT(12),
/*  29 */     LUMINOSITY(13),
/*  30 */     HUE(14),
/*  31 */     SATURATION(15),
/*  32 */     COLOR(16);
/*     */     
/*     */     private final int a;
/*     */     
/*     */     BlendMode(int param1Int1) {
/*  37 */       this.a = param1Int1;
/*     */     }
/*     */     
/*     */     public final int getValue() {
/*  41 */       return this.a;
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
/*     */   public Ink(Obj paramObj) {
/*  55 */     super(paramObj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Ink() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Ink(long paramLong, Object paramObject) {
/*  72 */     super(paramLong, paramObject);
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
/*     */   public Ink(Annot paramAnnot) throws PDFNetException {
/*  86 */     super(paramAnnot.getSDFObj());
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
/*     */   public static Ink create(Doc paramDoc, Rect paramRect) throws PDFNetException {
/*  99 */     return new Ink(Create(paramDoc.__GetHandle(), paramRect.__GetHandle()), paramDoc);
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
/*     */   public int getPathCount() throws PDFNetException {
/* 116 */     return GetPathCount(__GetHandle());
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
/*     */   public int getPointCount(int paramInt) throws PDFNetException {
/* 136 */     return GetPointCount(__GetHandle(), paramInt);
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
/*     */   public Point GetPoint(int paramInt1, int paramInt2) throws PDFNetException {
/* 157 */     return new Point(GetPointx(__GetHandle(), paramInt1, paramInt2), GetPointy(__GetHandle(), paramInt1, paramInt2));
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
/*     */   public void setPoint(int paramInt1, int paramInt2, Point paramPoint) throws PDFNetException {
/* 178 */     SetPoint(__GetHandle(), paramInt1, paramInt2, paramPoint.x, paramPoint.y);
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
/*     */   public boolean erase(Point paramPoint1, Point paramPoint2, double paramDouble) throws PDFNetException {
/* 190 */     return Erase(__GetHandle(), paramPoint1.x, paramPoint1.y, paramPoint2.x, paramPoint2.y, paramDouble);
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
/*     */   public boolean erasePaths(Point paramPoint1, Point paramPoint2, double paramDouble) throws PDFNetException {
/* 202 */     return ErasePaths(__GetHandle(), paramPoint1.x, paramPoint1.y, paramPoint2.x, paramPoint2.y, paramDouble);
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
/*     */   public static boolean erasePoints(Obj paramObj, Rect paramRect, Point paramPoint1, Point paramPoint2, double paramDouble) throws PDFNetException {
/* 216 */     return ErasePoints(paramObj.__GetHandle(), paramRect.__GetHandle(), paramPoint1.x, paramPoint1.y, paramPoint2.x, paramPoint2.y, paramDouble);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getSmoothing() throws PDFNetException {
/* 225 */     return GetSmoothing(__GetHandle());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSmoothing(boolean paramBoolean) throws PDFNetException {
/* 235 */     SetSmoothing(__GetHandle(), paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBlendMode() throws PDFNetException {
/* 246 */     return GetBlendMode(__GetHandle());
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
/*     */   public void setBlendMode(BlendMode paramBlendMode) throws PDFNetException {
/* 258 */     SetBlendMode(__GetHandle(), paramBlendMode.getValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double[] getBezierControlPoints(double[] paramArrayOfdouble) throws PDFNetException {
/* 268 */     return GetBezierControlPoints(paramArrayOfdouble);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getHighlightIntent() throws PDFNetException {
/* 279 */     return GetHighlightIntent(__GetHandle());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHighlightIntent(boolean paramBoolean) throws PDFNetException {
/* 290 */     SetHighlightIntent(__GetHandle(), paramBoolean);
/*     */   }
/*     */   
/*     */   static native long Create(long paramLong1, long paramLong2);
/*     */   
/*     */   static native int GetPathCount(long paramLong);
/*     */   
/*     */   static native int GetPointCount(long paramLong, int paramInt);
/*     */   
/*     */   static native double GetPointx(long paramLong, int paramInt1, int paramInt2);
/*     */   
/*     */   static native double GetPointy(long paramLong, int paramInt1, int paramInt2);
/*     */   
/*     */   static native void SetPoint(long paramLong, int paramInt1, int paramInt2, double paramDouble1, double paramDouble2);
/*     */   
/*     */   static native boolean Erase(long paramLong, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5);
/*     */   
/*     */   static native boolean ErasePaths(long paramLong, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5);
/*     */   
/*     */   static native boolean ErasePoints(long paramLong1, long paramLong2, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5);
/*     */   
/*     */   static native boolean GetSmoothing(long paramLong);
/*     */   
/*     */   static native void SetSmoothing(long paramLong, boolean paramBoolean);
/*     */   
/*     */   static native int GetBlendMode(long paramLong);
/*     */   
/*     */   static native void SetBlendMode(long paramLong, int paramInt);
/*     */   
/*     */   static native double[] GetBezierControlPoints(double[] paramArrayOfdouble);
/*     */   
/*     */   static native boolean GetHighlightIntent(long paramLong);
/*     */   
/*     */   static native void SetHighlightIntent(long paramLong, boolean paramBoolean);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\annots\Ink.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */