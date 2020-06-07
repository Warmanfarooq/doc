/*     */ package com.pdftron.pdf.annots;
/*     */ 
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.Annot;
/*     */ import com.pdftron.pdf.Point;
/*     */ import com.pdftron.pdf.QuadPoint;
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
/*     */ public class TextMarkup
/*     */   extends Markup
/*     */ {
/*     */   public TextMarkup(Obj paramObj) {
/*  29 */     super(paramObj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextMarkup() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   TextMarkup(long paramLong, Object paramObject) {
/*  48 */     super(paramLong, paramObject);
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
/*     */   public TextMarkup(Annot paramAnnot) throws PDFNetException {
/*  63 */     super(paramAnnot.getSDFObj());
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
/*  82 */     return GetQuadPointCount(__GetHandle());
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
/* 102 */     Point point2 = new Point(GetQuadPointp1x(__GetHandle(), paramInt), GetQuadPointp1y(__GetHandle(), paramInt));
/* 103 */     Point point3 = new Point(GetQuadPointp2x(__GetHandle(), paramInt), GetQuadPointp2y(__GetHandle(), paramInt));
/* 104 */     Point point4 = new Point(GetQuadPointp3x(__GetHandle(), paramInt), GetQuadPointp3y(__GetHandle(), paramInt));
/* 105 */     Point point1 = new Point(GetQuadPointp4x(__GetHandle(), paramInt), GetQuadPointp4y(__GetHandle(), paramInt));
/* 106 */     return new QuadPoint(point2, point3, point4, point1);
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
/*     */   public void setQuadPoint(int paramInt, QuadPoint paramQuadPoint) throws PDFNetException {
/* 129 */     SetQuadPoint(__GetHandle(), paramInt, paramQuadPoint.p1.x, paramQuadPoint.p1.y, paramQuadPoint.p2.x, paramQuadPoint.p2.y, paramQuadPoint.p3.x, paramQuadPoint.p3.y, paramQuadPoint.p4.x, paramQuadPoint.p4.y);
/*     */   }
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


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\annots\TextMarkup.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */