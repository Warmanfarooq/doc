/*    */ package com.pdftron.pdf.tools;
/*    */ 
/*    */ import android.graphics.Canvas;
/*    */ import android.graphics.Matrix;
/*    */ import android.graphics.PointF;
/*    */ import androidx.annotation.Keep;
/*    */ import androidx.annotation.NonNull;
/*    */ import com.pdftron.common.PDFNetException;
/*    */ import com.pdftron.pdf.Annot;
/*    */ import com.pdftron.pdf.PDFDoc;
/*    */ import com.pdftron.pdf.PDFViewCtrl;
/*    */ import com.pdftron.pdf.Point;
/*    */ import com.pdftron.pdf.Rect;
/*    */ import com.pdftron.pdf.annots.Polygon;
/*    */ import com.pdftron.pdf.utils.DrawingUtils;
/*    */ import com.pdftron.pdf.utils.Utils;
/*    */ import com.pdftron.sdf.Doc;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Keep
/*    */ public class PolygonCreate
/*    */   extends AdvancedShapeCreate
/*    */ {
/*    */   public PolygonCreate(@NonNull PDFViewCtrl ctrl) {
/* 38 */     super(ctrl);
/* 39 */     this.mNextToolMode = getToolMode();
/* 40 */     this.mHasFill = true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ToolManager.ToolModeBase getToolMode() {
/* 48 */     return ToolManager.ToolMode.POLYGON_CREATE;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getCreateAnnotType() {
/* 53 */     return 6;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected Annot createMarkup(@NonNull PDFDoc doc, ArrayList<Point> pagePoints) throws PDFNetException {
/* 62 */     Rect annotRect = Utils.getBBox(pagePoints);
/* 63 */     if (annotRect == null) {
/* 64 */       return null;
/*    */     }
/* 66 */     annotRect.inflate(this.mThickness);
/*    */     
/* 68 */     Polygon poly = new Polygon(Polygon.create((Doc)doc, 6, annotRect));
/*    */     
/* 70 */     int pointIdx = 0;
/* 71 */     for (Point point : pagePoints) {
/* 72 */       poly.setVertex(pointIdx++, point);
/*    */     }
/* 74 */     poly.setRect(annotRect);
/*    */     
/* 76 */     return (Annot)poly;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void drawMarkup(@NonNull Canvas canvas, Matrix tfm, @NonNull ArrayList<PointF> canvasPoints) {
/* 83 */     if (this.mPdfViewCtrl == null) {
/*    */       return;
/*    */     }
/*    */     
/* 87 */     DrawingUtils.drawPolygon(this.mPdfViewCtrl, getPageNum(), canvas, canvasPoints, this.mPath, this.mPaint, this.mStrokeColor, this.mFillPaint, this.mFillColor);
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\PolygonCreate.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */