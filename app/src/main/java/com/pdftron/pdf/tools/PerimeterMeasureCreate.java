/*     */ package com.pdftron.pdf.tools;
/*     */ 
/*     */ import android.graphics.Canvas;
/*     */ import android.graphics.Matrix;
/*     */ import android.view.MotionEvent;
/*     */ import androidx.annotation.Keep;
/*     */ import androidx.annotation.NonNull;
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.Annot;
/*     */ import com.pdftron.pdf.PDFDoc;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.Point;
/*     */ import com.pdftron.pdf.annots.PolyLine;
/*     */ import com.pdftron.pdf.model.AnnotStyle;
/*     */ import com.pdftron.pdf.model.MeasureInfo;
/*     */ import com.pdftron.pdf.model.RulerItem;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.AnnotUtils;
/*     */ import com.pdftron.pdf.utils.MeasureImpl;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Keep
/*     */ public class PerimeterMeasureCreate
/*     */   extends PolylineCreate
/*     */ {
/*     */   private MeasureImpl mMeasureImpl;
/*     */   
/*     */   public PerimeterMeasureCreate(@NonNull PDFViewCtrl ctrl) {
/*  33 */     super(ctrl);
/*     */     
/*  35 */     this.mMeasureImpl = new MeasureImpl(getCreateAnnotType());
/*     */     
/*  37 */     ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/*  38 */     setSnappingEnabled(toolManager.isSnappingEnabledForMeasurementTools());
/*     */   }
/*     */ 
/*     */   
/*     */   public ToolManager.ToolModeBase getToolMode() {
/*  43 */     return ToolManager.ToolMode.PERIMETER_MEASURE_CREATE;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCreateAnnotType() {
/*  48 */     return 1008;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setupAnnotProperty(AnnotStyle annotStyle) {
/*  53 */     super.setupAnnotProperty(annotStyle);
/*     */     
/*  55 */     this.mMeasureImpl.setupAnnotProperty(this.mPdfViewCtrl.getContext(), annotStyle);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDown(MotionEvent e) {
/*  61 */     this.mMeasureImpl.handleDown(this.mPdfViewCtrl.getContext());
/*     */     
/*  63 */     boolean result = super.onDown(e);
/*     */     
/*  65 */     this.mLoupeEnabled = true;
/*  66 */     setLoupeInfo(e.getX(), e.getY());
/*  67 */     animateLoupe(true);
/*     */     
/*  69 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onMove(MotionEvent e1, MotionEvent e2, float x_dist, float y_dist) {
/*  74 */     boolean result = super.onMove(e1, e2, x_dist, y_dist);
/*     */     
/*  76 */     setLoupeInfo(e2.getX(), e2.getY());
/*     */     
/*  78 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onUp(MotionEvent e, PDFViewCtrl.PriorEventMode priorEventMode) {
/*  83 */     animateLoupe(false);
/*  84 */     return super.onUp(e, priorEventMode);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Annot createMarkup(@NonNull PDFDoc doc, ArrayList<Point> pagePoints) throws PDFNetException {
/*  89 */     PolyLine polyLine = new PolyLine(super.createMarkup(doc, pagePoints));
/*  90 */     polyLine.setContents(adjustContents());
/*  91 */     this.mMeasureImpl.commit((Annot)polyLine);
/*  92 */     return (Annot)polyLine;
/*     */   }
/*     */   
/*     */   private String adjustContents() {
/*  96 */     return adjustContents(this.mMeasureImpl, this.mPagePoints);
/*     */   }
/*     */   
/*     */   private static String adjustContents(MeasureImpl measureImpl, ArrayList<Point> points) {
/* 100 */     double perimeter = getPerimeter(points);
/* 101 */     MeasureInfo axis = measureImpl.getAxis();
/* 102 */     MeasureInfo distanceMeasure = measureImpl.getMeasure();
/* 103 */     if (axis == null || distanceMeasure == null) {
/* 104 */       return "";
/*     */     }
/*     */     
/* 107 */     double convertedPerimeter = perimeter * axis.getFactor() * distanceMeasure.getFactor();
/* 108 */     return measureImpl.getMeasurementText(convertedPerimeter, distanceMeasure);
/*     */   }
/*     */   
/*     */   private static double getPerimeter(ArrayList<Point> points) {
/* 112 */     double perimeter = 0.0D;
/* 113 */     Point prevPoint = null;
/* 114 */     for (Point point : points) {
/* 115 */       if (prevPoint != null) {
/* 116 */         perimeter += Math.sqrt(Math.pow(point.x - prevPoint.x, 2.0D) + Math.pow(point.y - prevPoint.y, 2.0D));
/*     */       }
/* 118 */       prevPoint = point;
/*     */     } 
/* 120 */     return perimeter;
/*     */   }
/*     */   
/*     */   public static void adjustContents(Annot annot, RulerItem rulerItem, ArrayList<Point> points) {
/*     */     try {
/* 125 */       MeasureImpl measure = new MeasureImpl(AnnotUtils.getAnnotType(annot));
/* 126 */       measure.updateRulerItem(rulerItem);
/* 127 */       String result = adjustContents(measure, points);
/* 128 */       annot.setContents(result);
/* 129 */       measure.commit(annot);
/* 130 */     } catch (Exception ex) {
/* 131 */       AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDraw(Canvas canvas, Matrix tfm) {
/* 137 */     super.onDraw(canvas, tfm);
/*     */     
/* 139 */     drawLoupe();
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canDrawLoupe() {
/* 144 */     return !this.mDrawingLoupe;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getLoupeType() {
/* 149 */     return 2;
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\PerimeterMeasureCreate.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */