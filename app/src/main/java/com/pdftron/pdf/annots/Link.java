/*     */ package com.pdftron.pdf.annots;
/*     */ 
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.Action;
/*     */ import com.pdftron.pdf.Annot;
/*     */ import com.pdftron.pdf.Point;
/*     */ import com.pdftron.pdf.QuadPoint;
/*     */ import com.pdftron.pdf.Rect;
/*     */ import com.pdftron.sdf.Doc;
/*     */ import com.pdftron.sdf.Obj;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Link
/*     */   extends Annot
/*     */ {
/*     */   public static final int e_none = 0;
/*     */   public static final int e_invert = 1;
/*     */   public static final int e_outline = 2;
/*     */   public static final int e_push = 3;
/*     */   
/*     */   public Link(Obj paramObj) {
/*  26 */     super(paramObj);
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
/*     */   public Link() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Link(long paramLong, Object paramObject) {
/*  51 */     super(paramLong, paramObject);
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
/*     */   public Link(Annot paramAnnot) throws PDFNetException {
/*  65 */     super(paramAnnot.getSDFObj());
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
/*     */   public static Link create(Doc paramDoc, Rect paramRect) throws PDFNetException {
/*  79 */     return new Link(Create(paramDoc.__GetHandle(), paramRect.__GetHandle()), paramDoc);
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
/*     */   public static Link create(Doc paramDoc, Rect paramRect, Action paramAction) throws PDFNetException {
/*  94 */     return new Link(Create(paramDoc.__GetHandle(), paramRect.__GetHandle(), paramAction.__GetHandle()), paramDoc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAction() throws PDFNetException {
/* 104 */     RemoveAction(__GetHandle());
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
/*     */   public Action getAction() throws PDFNetException {
/* 118 */     return Action.__Create(GetAction(__GetHandle()), __GetRefHandle());
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
/*     */   public void setAction(Action paramAction) throws PDFNetException {
/* 133 */     SetAction(__GetHandle(), paramAction.__GetHandle());
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
/*     */   public int getHighlightingMode() throws PDFNetException {
/* 172 */     return GetHighlightingMode(__GetHandle());
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
/*     */   public void setHighlightingMode(int paramInt) throws PDFNetException {
/* 193 */     SetHighlightingMode(__GetHandle(), paramInt);
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
/*     */   public int getQuadPointCount() throws PDFNetException {
/* 212 */     return GetQuadPointCount(__GetHandle());
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
/*     */   public QuadPoint getQuadPoint(int paramInt) throws PDFNetException {
/* 232 */     Point point2 = new Point(GetQuadPointp1x(__GetHandle(), paramInt), GetQuadPointp1y(__GetHandle(), paramInt));
/* 233 */     Point point3 = new Point(GetQuadPointp2x(__GetHandle(), paramInt), GetQuadPointp2y(__GetHandle(), paramInt));
/* 234 */     Point point4 = new Point(GetQuadPointp3x(__GetHandle(), paramInt), GetQuadPointp3y(__GetHandle(), paramInt));
/* 235 */     Point point1 = new Point(GetQuadPointp4x(__GetHandle(), paramInt), GetQuadPointp4y(__GetHandle(), paramInt));
/* 236 */     return new QuadPoint(point2, point3, point4, point1);
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
/*     */   public void setQuadPoint(int paramInt, QuadPoint paramQuadPoint) throws PDFNetException {
/* 257 */     SetQuadPoint(__GetHandle(), paramInt, paramQuadPoint.p1.x, paramQuadPoint.p1.y, paramQuadPoint.p2.x, paramQuadPoint.p2.y, paramQuadPoint.p3.x, paramQuadPoint.p3.y, paramQuadPoint.p4.x, paramQuadPoint.p4.y);
/*     */   }
/*     */   
/*     */   static native long Create(long paramLong1, long paramLong2);
/*     */   
/*     */   static native long Create(long paramLong1, long paramLong2, long paramLong3);
/*     */   
/*     */   static native void RemoveAction(long paramLong);
/*     */   
/*     */   static native long GetAction(long paramLong);
/*     */   
/*     */   static native void SetAction(long paramLong1, long paramLong2);
/*     */   
/*     */   static native int GetHighlightingMode(long paramLong);
/*     */   
/*     */   static native void SetHighlightingMode(long paramLong, int paramInt);
/*     */   
/*     */   static native int GetQuadPointCount(long paramLong);
/*     */   
/*     */   static native double GetQuadPointp1x(long paramLong, int paramInt);
/*     */   
/*     */   static native double GetQuadPointp1y(long paramLong, int paramInt);
/*     */   
/*     */   static native double GetQuadPointp2x(long paramLong, int paramInt);
/*     */   
/*     */   static native double GetQuadPointp2y(long paramLong, int paramInt);
/*     */   
/*     */   static native double GetQuadPointp3x(long paramLong, int paramInt);
/*     */   
/*     */   static native double GetQuadPointp3y(long paramLong, int paramInt);
/*     */   
/*     */   static native double GetQuadPointp4x(long paramLong, int paramInt);
/*     */   
/*     */   static native double GetQuadPointp4y(long paramLong, int paramInt);
/*     */   
/*     */   static native void SetQuadPoint(long paramLong, int paramInt, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\annots\Link.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */