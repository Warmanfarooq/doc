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
/*    */ import com.pdftron.pdf.annots.PolyLine;
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
/*    */ @Keep
/*    */ public class PolylineCreate
/*    */   extends AdvancedShapeCreate
/*    */ {
/*    */   public PolylineCreate(@NonNull PDFViewCtrl ctrl) {
/* 37 */     super(ctrl);
/* 38 */     this.mNextToolMode = getToolMode();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ToolManager.ToolModeBase getToolMode() {
/* 46 */     return ToolManager.ToolMode.POLYLINE_CREATE;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getCreateAnnotType() {
/* 51 */     return 7;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected Annot createMarkup(@NonNull PDFDoc doc, ArrayList<Point> pagePoints) throws PDFNetException {
/* 60 */     Rect annotRect = Utils.getBBox(pagePoints);
/* 61 */     if (annotRect == null) {
/* 62 */       return null;
/*    */     }
/* 64 */     annotRect.inflate(this.mThickness);
/*    */     
/* 66 */     PolyLine poly = new PolyLine(PolyLine.create((Doc)doc, 7, annotRect));
/*    */     
/* 68 */     int pointIdx = 0;
/* 69 */     for (Point point : pagePoints) {
/* 70 */       poly.setVertex(pointIdx++, point);
/*    */     }
/* 72 */     poly.setRect(annotRect);
/*    */     
/* 74 */     return (Annot)poly;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void drawMarkup(@NonNull Canvas canvas, Matrix tfm, @NonNull ArrayList<PointF> canvasPoints) {
/* 81 */     if (this.mPdfViewCtrl == null) {
/*    */       return;
/*    */     }
/* 84 */     DrawingUtils.drawPolyline(this.mPdfViewCtrl, getPageNum(), canvas, canvasPoints, this.mPath, this.mPaint, this.mStrokeColor);
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\PolylineCreate.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */