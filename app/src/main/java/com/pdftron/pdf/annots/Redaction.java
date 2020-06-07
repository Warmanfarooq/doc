/*     */ package com.pdftron.pdf.annots;
/*     */ 
/*     */ import com.pdftron.common.PDFNetException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Redaction
/*     */   extends Markup
/*     */ {
/*     */   public static final int e_LeftJustified = 0;
/*     */   public static final int e_Centered = 1;
/*     */   public static final int e_RightJustified = 2;
/*     */   public static final int e_None = 3;
/*     */   
/*     */   public Redaction(Obj paramObj) {
/*  54 */     super(paramObj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Redaction() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Redaction(long paramLong, Object paramObject) {
/*  73 */     super(paramLong, paramObject);
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
/*     */   public Redaction(Annot paramAnnot) throws PDFNetException {
/*  87 */     super(paramAnnot.getSDFObj());
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
/*     */   public static Redaction create(Doc paramDoc, Rect paramRect) throws PDFNetException {
/* 101 */     return new Redaction(Create(paramDoc.__GetHandle(), paramRect.__GetHandle()), paramDoc);
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
/*     */   public int getQuadPointCount() throws PDFNetException {
/* 117 */     return GetQuadPointCount(__GetHandle());
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
/*     */   public QuadPoint getQuadPoint(int paramInt) throws PDFNetException {
/* 134 */     Point point2 = new Point(GetQuadPointp1x(__GetHandle(), paramInt), GetQuadPointp1y(__GetHandle(), paramInt));
/* 135 */     Point point3 = new Point(GetQuadPointp2x(__GetHandle(), paramInt), GetQuadPointp2y(__GetHandle(), paramInt));
/* 136 */     Point point4 = new Point(GetQuadPointp3x(__GetHandle(), paramInt), GetQuadPointp3y(__GetHandle(), paramInt));
/* 137 */     Point point1 = new Point(GetQuadPointp4x(__GetHandle(), paramInt), GetQuadPointp4y(__GetHandle(), paramInt));
/* 138 */     return new QuadPoint(point2, point3, point4, point1);
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
/* 159 */     SetQuadPoint(__GetHandle(), paramInt, paramQuadPoint.p1.x, paramQuadPoint.p1.y, paramQuadPoint.p2.x, paramQuadPoint.p2.y, paramQuadPoint.p3.x, paramQuadPoint.p3.y, paramQuadPoint.p4.x, paramQuadPoint.p4.y);
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
/*     */   public Obj getAppFormXO() throws PDFNetException {
/* 178 */     return Obj.__Create(GetAppFormXO(__GetHandle()), __GetRefHandle());
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
/*     */   public void setAppFormXO(Obj paramObj) throws PDFNetException {
/* 197 */     SetAppFormXO(__GetHandle(), paramObj.__GetHandle());
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
/*     */   public String getOverlayText() throws PDFNetException {
/* 213 */     return GetOverlayText(__GetHandle());
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
/*     */   public void setOverlayText(String paramString) throws PDFNetException {
/* 229 */     SetOverlayText(__GetHandle(), paramString);
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
/*     */   public boolean getUseRepeat() throws PDFNetException {
/* 245 */     return GetUseRepeat(__GetHandle());
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
/*     */   public void setUseRepeat(boolean paramBoolean) throws PDFNetException {
/* 261 */     SetUseRepeat(__GetHandle(), paramBoolean);
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
/*     */   public String getOverlayTextAppearance() throws PDFNetException {
/* 276 */     return GetOverlayTextAppearance(__GetHandle());
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
/*     */   public void setOverlayTextAppearance(String paramString) throws PDFNetException {
/* 291 */     SetOverlayTextAppearance(__GetHandle(), paramString);
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
/*     */   public int getQuadForm() throws PDFNetException {
/* 326 */     return GetQuadForm(__GetHandle());
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
/*     */   public void setQuadForm(int paramInt) throws PDFNetException {
/* 341 */     SetQuadForm(__GetHandle(), paramInt);
/*     */   }
/*     */   
/*     */   static native long Create(long paramLong1, long paramLong2);
/*     */   
/*     */   static native int GetQuadPointCount(long paramLong);
/*     */   
/*     */   static native long GetAppFormXO(long paramLong);
/*     */   
/*     */   static native void SetAppFormXO(long paramLong1, long paramLong2);
/*     */   
/*     */   static native String GetOverlayText(long paramLong);
/*     */   
/*     */   static native void SetOverlayText(long paramLong, String paramString);
/*     */   
/*     */   static native boolean GetUseRepeat(long paramLong);
/*     */   
/*     */   static native void SetUseRepeat(long paramLong, boolean paramBoolean);
/*     */   
/*     */   static native String GetOverlayTextAppearance(long paramLong);
/*     */   
/*     */   static native void SetOverlayTextAppearance(long paramLong, String paramString);
/*     */   
/*     */   static native int GetQuadForm(long paramLong);
/*     */   
/*     */   static native void SetQuadForm(long paramLong, int paramInt);
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


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\annots\Redaction.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */