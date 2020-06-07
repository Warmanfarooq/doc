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
/*     */ import com.pdftron.pdf.Rect;
/*     */ import com.pdftron.pdf.annots.Polygon;
/*     */ import com.pdftron.pdf.model.AnnotStyle;
/*     */ import com.pdftron.pdf.utils.MeasureImpl;
/*     */ import com.pdftron.pdf.utils.MeasureUtils;
/*     */ import com.pdftron.sdf.Doc;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Keep
/*     */ public class RectAreaMeasureCreate
/*     */   extends RectCreate
/*     */ {
/*     */   private MeasureImpl mMeasureImpl;
/*     */   
/*     */   public RectAreaMeasureCreate(@NonNull PDFViewCtrl ctrl) {
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
/*  43 */     return ToolManager.ToolMode.RECT_AREA_MEASURE_CREATE;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCreateAnnotType() {
/*  48 */     return 1012;
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
/*     */   protected Annot createMarkup(@NonNull PDFDoc doc, Rect bbox) throws PDFNetException {
/*  88 */     Polygon poly = new Polygon(Polygon.create((Doc)doc, 6, bbox));
/*  89 */     ArrayList<Point> pagePoints = convRectToPoints(bbox);
/*     */     
/*  91 */     int pointIdx = 0;
/*  92 */     for (Point point : pagePoints) {
/*  93 */       poly.setVertex(pointIdx++, point);
/*     */     }
/*  95 */     bbox.inflate(this.mThickness);
/*  96 */     poly.setRect(bbox);
/*     */     
/*  98 */     poly.setContents(adjustContents(pagePoints));
/*  99 */     this.mMeasureImpl.commit((Annot)poly);
/*     */ 
/*     */     
/* 102 */     poly.setCustomData(MeasureUtils.K_RECT_AREA, "pdftron");
/* 103 */     return (Annot)poly;
/*     */   }
/*     */   
/*     */   private String adjustContents(ArrayList<Point> pagePoints) {
/* 107 */     return AreaMeasureCreate.adjustContents(this.mMeasureImpl, pagePoints);
/*     */   }
/*     */   
/*     */   @NonNull
/*     */   private static ArrayList<Point> convRectToPoints(@NonNull Rect bbox) throws PDFNetException {
/* 112 */     ArrayList<Point> pts = new ArrayList<>();
/* 113 */     bbox.normalize();
/* 114 */     pts.add(new Point(bbox.getX1(), bbox.getY1()));
/* 115 */     pts.add(new Point(bbox.getX1(), bbox.getY2()));
/* 116 */     pts.add(new Point(bbox.getX2(), bbox.getY2()));
/* 117 */     pts.add(new Point(bbox.getX2(), bbox.getY1()));
/* 118 */     return pts;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDraw(Canvas canvas, Matrix tfm) {
/* 123 */     super.onDraw(canvas, tfm);
/*     */     
/* 125 */     drawLoupe();
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canDrawLoupe() {
/* 130 */     return !this.mDrawingLoupe;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getLoupeType() {
/* 135 */     return 2;
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\RectAreaMeasureCreate.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */