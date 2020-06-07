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
/*     */ import com.pdftron.pdf.annots.Polygon;
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
/*     */ public class AreaMeasureCreate
/*     */   extends PolygonCreate
/*     */ {
/*     */   private MeasureImpl mMeasureImpl;
/*     */   
/*     */   public AreaMeasureCreate(@NonNull PDFViewCtrl ctrl) {
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
/*  43 */     return ToolManager.ToolMode.AREA_MEASURE_CREATE;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCreateAnnotType() {
/*  48 */     return 1009;
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
/*     */   public boolean onDown(MotionEvent e) {
/*  60 */     this.mMeasureImpl.handleDown(this.mPdfViewCtrl.getContext());
/*     */     
/*  62 */     boolean result = super.onDown(e);
/*     */     
/*  64 */     this.mLoupeEnabled = true;
/*  65 */     setLoupeInfo(e.getX(), e.getY());
/*  66 */     animateLoupe(true);
/*     */     
/*  68 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onMove(MotionEvent e1, MotionEvent e2, float x_dist, float y_dist) {
/*  73 */     boolean result = super.onMove(e1, e2, x_dist, y_dist);
/*     */     
/*  75 */     setLoupeInfo(e2.getX(), e2.getY());
/*     */     
/*  77 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onUp(MotionEvent e, PDFViewCtrl.PriorEventMode priorEventMode) {
/*  82 */     animateLoupe(false);
/*  83 */     return super.onUp(e, priorEventMode);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Annot createMarkup(@NonNull PDFDoc doc, ArrayList<Point> pagePoints) throws PDFNetException {
/*  88 */     Polygon polygon = new Polygon(super.createMarkup(doc, pagePoints));
/*  89 */     polygon.setContents(adjustContents());
/*  90 */     this.mMeasureImpl.commit((Annot)polygon);
/*  91 */     return (Annot)polygon;
/*     */   }
/*     */   
/*     */   private String adjustContents() {
/*  95 */     return adjustContents(this.mMeasureImpl, this.mPagePoints);
/*     */   }
/*     */   
/*     */   static String adjustContents(MeasureImpl measureImpl, ArrayList<Point> points) {
/*  99 */     double area = getArea(points);
/* 100 */     MeasureInfo axis = measureImpl.getAxis();
/* 101 */     MeasureInfo areaMeasure = measureImpl.getMeasure();
/* 102 */     if (axis == null || areaMeasure == null) {
/* 103 */       return "";
/*     */     }
/*     */     
/* 106 */     double convertedArea = area * axis.getFactor() * axis.getFactor() * areaMeasure.getFactor();
/* 107 */     return measureImpl.getMeasurementText(convertedArea, areaMeasure);
/*     */   }
/*     */   
/*     */   static double getArea(ArrayList<Point> points) {
/* 111 */     int numPoints = points.size();
/* 112 */     double area = 0.0D;
/*     */     
/* 114 */     for (int i = 0; i < numPoints; i++) {
/* 115 */       Point point = points.get(i);
/* 116 */       double addX = point.x;
/* 117 */       double addY = ((Point)points.get((i == numPoints - 1) ? 0 : (i + 1))).y;
/* 118 */       double subX = ((Point)points.get((i == numPoints - 1) ? 0 : (i + 1))).x;
/* 119 */       double subY = point.y;
/* 120 */       area += addX * addY - subX * subY;
/*     */     } 
/* 122 */     return Math.abs(area) / 2.0D;
/*     */   }
/*     */   
/*     */   public static void adjustContents(Annot annot, RulerItem rulerItem, ArrayList<Point> points) {
/*     */     try {
/* 127 */       MeasureImpl measure = new MeasureImpl(AnnotUtils.getAnnotType(annot));
/* 128 */       measure.updateRulerItem(rulerItem);
/* 129 */       String result = adjustContents(measure, points);
/* 130 */       annot.setContents(result);
/* 131 */       measure.commit(annot);
/* 132 */     } catch (Exception ex) {
/* 133 */       AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDraw(Canvas canvas, Matrix tfm) {
/* 139 */     super.onDraw(canvas, tfm);
/*     */     
/* 141 */     drawLoupe();
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canDrawLoupe() {
/* 146 */     return !this.mDrawingLoupe;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getLoupeType() {
/* 151 */     return 2;
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\AreaMeasureCreate.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */