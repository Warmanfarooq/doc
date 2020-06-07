/*     */ package com.pdftron.pdf.tools;
/*     */ 
/*     */ import android.graphics.Canvas;
/*     */ import android.graphics.Matrix;
/*     */ import android.graphics.PointF;
/*     */ import android.view.MotionEvent;
/*     */ import androidx.annotation.Keep;
/*     */ import androidx.annotation.NonNull;
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.Annot;
/*     */ import com.pdftron.pdf.PDFDoc;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.Rect;
/*     */ import com.pdftron.pdf.annots.Line;
/*     */ import com.pdftron.pdf.model.AnnotStyle;
/*     */ import com.pdftron.pdf.model.MeasureInfo;
/*     */ import com.pdftron.pdf.model.RulerItem;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.AnnotUtils;
/*     */ import com.pdftron.pdf.utils.DrawingUtils;
/*     */ import com.pdftron.pdf.utils.MeasureImpl;
/*     */ import com.pdftron.pdf.utils.MeasureUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Keep
/*     */ public class RulerCreate
/*     */   extends ArrowCreate
/*     */ {
/*     */   private PointF mPt5;
/*     */   private PointF mPt6;
/*  35 */   private String mText = "";
/*     */ 
/*     */   
/*     */   private MeasureImpl mMeasureImpl;
/*     */ 
/*     */ 
/*     */   
/*     */   public RulerCreate(@NonNull PDFViewCtrl ctrl) {
/*  43 */     super(ctrl);
/*     */     
/*  45 */     this.mPt5 = new PointF(0.0F, 0.0F);
/*  46 */     this.mPt6 = new PointF(0.0F, 0.0F);
/*     */     
/*  48 */     this.mMeasureImpl = new MeasureImpl(getCreateAnnotType());
/*     */     
/*  50 */     ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/*  51 */     setSnappingEnabled(toolManager.isSnappingEnabledForMeasurementTools());
/*     */   }
/*     */ 
/*     */   
/*     */   public ToolManager.ToolModeBase getToolMode() {
/*  56 */     return ToolManager.ToolMode.RULER_CREATE;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCreateAnnotType() {
/*  61 */     return 1006;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setupAnnotProperty(AnnotStyle annotStyle) {
/*  66 */     super.setupAnnotProperty(annotStyle);
/*     */     
/*  68 */     this.mMeasureImpl.setupAnnotProperty(this.mPdfViewCtrl.getContext(), annotStyle);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onDown(MotionEvent e) {
/*  73 */     this.mMeasureImpl.handleDown(this.mPdfViewCtrl.getContext());
/*     */     
/*  75 */     boolean result = super.onDown(e);
/*     */     
/*  77 */     this.mLoupeEnabled = true;
/*  78 */     setLoupeInfo(e.getX(), e.getY());
/*  79 */     animateLoupe(true);
/*     */     
/*  81 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onMove(MotionEvent e1, MotionEvent e2, float x_dist, float y_dist) {
/*  86 */     super.onMove(e1, e2, x_dist, y_dist);
/*     */ 
/*     */     
/*  89 */     if (this.mAllowTwoFingerScroll) {
/*  90 */       animateLoupe(false);
/*  91 */       this.mLoupeEnabled = false;
/*  92 */       return false;
/*     */     } 
/*  94 */     if (this.mAllowOneFingerScrollWithStylus) {
/*  95 */       animateLoupe(false);
/*  96 */       this.mLoupeEnabled = false;
/*  97 */       return false;
/*     */     } 
/*     */     
/* 100 */     this.mText = adjustContents();
/* 101 */     setLoupeInfo(e2.getX(), e2.getY());
/*     */     
/* 103 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onUp(MotionEvent e, PDFViewCtrl.PriorEventMode priorEventMode) {
/* 108 */     animateLoupe(false);
/* 109 */     return super.onUp(e, priorEventMode);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Annot createMarkup(@NonNull PDFDoc doc, Rect bbox) throws PDFNetException {
/* 114 */     Line line = new Line(super.createMarkup(doc, bbox));
/* 115 */     line.setShowCaption(true);
/* 116 */     line.setContents(adjustContents());
/* 117 */     line.setEndStyle(5);
/* 118 */     line.setStartStyle(5);
/* 119 */     line.setCaptionPosition(1);
/* 120 */     this.mMeasureImpl.commit((Annot)line);
/* 121 */     return (Annot)line;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void calculateEndingStyle() {
/* 126 */     DrawingUtils.calcRuler(this.mPt1, this.mPt2, this.mPt3, this.mPt4, this.mPt5, this.mPt6, this.mThickness, this.mZoom);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDraw(Canvas canvas, Matrix tfm) {
/* 133 */     if (this.mAllowTwoFingerScroll) {
/*     */       return;
/*     */     }
/* 136 */     if (this.mIsAllPointsOutsidePage) {
/*     */       return;
/*     */     }
/*     */     
/* 140 */     DrawingUtils.drawRuler(canvas, this.mPt1, this.mPt2, this.mPt3, this.mPt4, this.mPt5, this.mPt6, this.mOnDrawPath, this.mPaint, this.mText, this.mZoom);
/*     */ 
/*     */     
/* 143 */     drawLoupe();
/*     */   }
/*     */ 
/*     */   
/*     */   private String adjustContents() {
/* 148 */     double[] pts1 = this.mPdfViewCtrl.convScreenPtToPagePt(this.mPt1.x, this.mPt1.y, this.mDownPageNum);
/* 149 */     double[] pts2 = this.mPdfViewCtrl.convScreenPtToPagePt(this.mPt2.x, this.mPt2.y, this.mDownPageNum);
/*     */     
/* 151 */     double pt1x = pts1[0];
/* 152 */     double pt1y = pts1[1];
/* 153 */     double pt2x = pts2[0];
/* 154 */     double pt2y = pts2[1];
/*     */     
/* 156 */     return adjustContents(this.mMeasureImpl, pt1x, pt1y, pt2x, pt2y);
/*     */   }
/*     */   
/*     */   private static String adjustContents(MeasureImpl measureImpl, double pt1x, double pt1y, double pt2x, double pt2y) {
/* 160 */     double lineLength = MeasureUtils.getLineLength(pt1x, pt1y, pt2x, pt2y);
/* 161 */     MeasureInfo axis = measureImpl.getAxis();
/* 162 */     MeasureInfo distanceMeasure = measureImpl.getMeasure();
/* 163 */     if (axis == null || distanceMeasure == null) {
/* 164 */       return "";
/*     */     }
/*     */     
/* 167 */     double distance = lineLength * axis.getFactor() * distanceMeasure.getFactor();
/* 168 */     return measureImpl.getMeasurementText(distance, distanceMeasure);
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
/*     */   public static void adjustContents(Annot annot, RulerItem rulerItem, double pt1x, double pt1y, double pt2x, double pt2y) {
/*     */     try {
/* 182 */       MeasureImpl measure = new MeasureImpl(AnnotUtils.getAnnotType(annot));
/* 183 */       measure.updateRulerItem(rulerItem);
/* 184 */       String result = adjustContents(measure, pt1x, pt1y, pt2x, pt2y);
/* 185 */       annot.setContents(result);
/* 186 */       measure.commit(annot);
/* 187 */     } catch (Exception ex) {
/* 188 */       AnalyticsHandlerAdapter.getInstance().sendException(ex);
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
/*     */   public static String getLabel(RulerItem rulerItem, double pt1x, double pt1y, double pt2x, double pt2y) {
/* 202 */     MeasureImpl measure = new MeasureImpl(1006);
/* 203 */     measure.updateRulerItem(rulerItem);
/* 204 */     return adjustContents(measure, pt1x, pt1y, pt2x, pt2y);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canDrawLoupe() {
/* 209 */     return !this.mDrawingLoupe;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getLoupeType() {
/* 214 */     return 2;
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\RulerCreate.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */