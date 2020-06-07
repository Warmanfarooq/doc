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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PolyLine
/*     */   extends Line
/*     */ {
/*     */   public static final int e_PolygonCloud = 0;
/*     */   public static final int e_PolyLineDimension = 1;
/*     */   public static final int e_PolygonDimension = 2;
/*     */   public static final int e_Unknown = 3;
/*     */   
/*     */   public PolyLine(Obj paramObj) {
/*  28 */     super(paramObj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PolyLine() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PolyLine(long paramLong, Object paramObject) {
/*  47 */     super(paramLong, paramObject);
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
/*     */   public PolyLine(Annot paramAnnot) throws PDFNetException {
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
/*     */   public static PolyLine create(Doc paramDoc, Rect paramRect) throws PDFNetException {
/*  76 */     return new PolyLine(Create(paramDoc.__GetHandle(), paramRect.__GetHandle()), paramDoc);
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
/*     */   public int getVertexCount() throws PDFNetException {
/*  91 */     return GetVertexCount(__GetHandle());
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
/*     */   public Point getVertex(int paramInt) throws PDFNetException {
/* 108 */     return new Point(GetVertexx(__GetHandle(), paramInt), GetVertexy(__GetHandle(), paramInt));
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
/*     */   public void setVertex(int paramInt, Point paramPoint) throws PDFNetException {
/* 125 */     SetVertex(__GetHandle(), paramInt, paramPoint.x, paramPoint.y);
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
/*     */   public int getIntentName() throws PDFNetException {
/* 168 */     return GetIntentName(__GetHandle());
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
/*     */   public void setIntentName(int paramInt) throws PDFNetException {
/* 188 */     SetIntentName(__GetHandle(), paramInt);
/*     */   }
/*     */   
/*     */   static native long Create(long paramLong1, long paramLong2);
/*     */   
/*     */   static native int GetVertexCount(long paramLong);
/*     */   
/*     */   static native double GetVertexx(long paramLong, int paramInt);
/*     */   
/*     */   static native double GetVertexy(long paramLong, int paramInt);
/*     */   
/*     */   static native void SetVertex(long paramLong, int paramInt, double paramDouble1, double paramDouble2);
/*     */   
/*     */   static native int GetIntentName(long paramLong);
/*     */   
/*     */   static native void SetIntentName(long paramLong, int paramInt);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\annots\PolyLine.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */