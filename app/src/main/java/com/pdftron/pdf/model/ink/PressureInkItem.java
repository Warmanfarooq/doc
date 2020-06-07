/*     */ package com.pdftron.pdf.model.ink;
/*     */ 
/*     */ import android.graphics.Paint;
/*     */ import android.graphics.Path;
/*     */ import android.graphics.PointF;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.utils.PathPool;
/*     */ import com.pdftron.pdf.utils.PressureInkUtils;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ public class PressureInkItem
/*     */   extends InkItem
/*     */ {
/*  20 */   public List<Float> currentActivePressure = new ArrayList<>();
/*     */ 
/*     */   
/*     */   public final List<List<Float>> finishedPressures;
/*     */ 
/*     */   
/*     */   public InkItem copy() {
/*  27 */     PressureInkItem newInkItem = new PressureInkItem(this.id, null, null, new ArrayList<>(this.finishedStrokes), new ArrayList<>(this.finishedPressures), this.pageNumber, this.color, this.opacity, this.baseThickness, this.paintThickness, this.isStylus);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  40 */     newInkItem.shouldAnimateUndoRedo = this.shouldAnimateUndoRedo;
/*  41 */     newInkItem.previousStroke = (this.previousStroke == null) ? null : new ArrayList<>(this.previousStroke);
/*  42 */     return newInkItem;
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
/*     */   public PressureInkItem(String id, @Nullable List<PointF> currentActiveStroke, @Nullable List<Float> currentActivePressure, List<List<PointF>> finishedStrokes, List<List<Float>> finishedPressures, int pageNumber, int color, float opacity, float baseThickness, float paintThickness, boolean isStylus) {
/*  56 */     super(id, currentActiveStroke, finishedStrokes, pageNumber, color, opacity, baseThickness, paintThickness, isStylus);
/*  57 */     this.finishedPressures = finishedPressures;
/*  58 */     this.currentActivePressure = currentActivePressure;
/*     */   }
/*     */   
/*     */   public PressureInkItem(int pageNumber, int color, float opacity, float baseThickness, boolean isStylus, PDFViewCtrl pdfViewCtrl) {
/*  62 */     super(pageNumber, color, opacity, baseThickness, isStylus, pdfViewCtrl);
/*  63 */     this.finishedPressures = new ArrayList<>();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onDown(float x, float y, float pressure) {
/*  68 */     super.onDown(x, y, pressure);
/*  69 */     this.currentActivePressure = new ArrayList<>();
/*  70 */     this.currentActivePressure.add(Float.valueOf(pressure));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onMove(float x, float y, float pressure) {
/*  75 */     super.onMove(x, y, pressure);
/*  76 */     if (this.currentActivePressure == null) {
/*  77 */       throw new RuntimeException("This should not happen. Missing onDown call");
/*     */     }
/*  79 */     this.currentActivePressure.add(Float.valueOf(pressure));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onUp() {
/*  84 */     super.onUp();
/*  85 */     if (this.currentActivePressure == null) {
/*  86 */       throw new RuntimeException("This should not happen. Missing onDown call");
/*     */     }
/*  88 */     List<Float> newPressure = Collections.unmodifiableList(this.currentActivePressure);
/*  89 */     this.finishedPressures.add(newPressure);
/*  90 */     this.currentActivePressure = null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Path createPathFromCurrentActiveStroke(@NonNull List<PointF> points, @NonNull PDFViewCtrl pdfViewCtrl, @Nullable PointF offset) {
/*  95 */     return createPathFromPressurePagePoint(points, this.currentActivePressure, pdfViewCtrl, offset);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Path createPathFromFinishedStroke(int index, @NonNull PDFViewCtrl pdfViewCtrl, @Nullable PointF offset) {
/* 100 */     return createPathFromPressurePagePoint(this.finishedStrokes.get(index), this.finishedPressures.get(index), pdfViewCtrl, offset);
/*     */   }
/*     */   
/*     */   private Path createPathFromPressurePagePoint(List<PointF> points, List<Float> pressure, PDFViewCtrl pdfViewCtrl, PointF offset) {
/* 104 */     float xOffset = 0.0F;
/* 105 */     float yOffset = 0.0F;
/* 106 */     if (offset != null) {
/* 107 */       xOffset = offset.x;
/* 108 */       yOffset = offset.y;
/*     */     } 
/*     */     
/* 111 */     List<List<PointF>> pathList = new ArrayList<>();
/* 112 */     List<List<Float>> thicknessesList = new ArrayList<>();
/* 113 */     thicknessesList.add(pressure);
/*     */ 
/*     */     
/* 116 */     List<PointF> convertedPoints = new ArrayList<>();
/* 117 */     for (PointF pt : points) {
/* 118 */       float[] newPt = convPagePtToHorizontalScrollingPt(pt.x, pt.y, pdfViewCtrl);
/* 119 */       convertedPoints.add(new PointF(newPt[0] - xOffset, newPt[1] - yOffset));
/*     */     } 
/* 121 */     pathList.add(convertedPoints);
/*     */ 
/*     */ 
/*     */     
/* 125 */     List<double[]> outlines = PressureInkUtils.generateOutlines(pathList, thicknessesList, this.paintThickness);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 132 */     Path path = PathPool.getInstance().obtain();
/* 133 */     path.setFillType(Path.FillType.WINDING);
/*     */     
/* 135 */     double[] outline = (outlines == null) ? null : outlines.get(0);
/* 136 */     if (outline != null && outline.length > 2) {
/*     */       
/* 138 */       path.moveTo((float)outline[0], (float)outline[1]);
/* 139 */       for (int i = 2, cnt = outline.length; i < cnt; i += 6) {
/* 140 */         path.cubicTo((float)outline[i], (float)outline[i + 1], (float)outline[i + 2], (float)outline[i + 3], (float)outline[i + 4], (float)outline[i + 5]);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 145 */     return path;
/*     */   }
/*     */ 
/*     */   
/*     */   public Paint getPaint(@NonNull PDFViewCtrl pdfViewCtrl) {
/* 150 */     if (this.paint == null) {
/* 151 */       this.paint = new Paint();
/* 152 */       this.paint.setStrokeCap(Paint.Cap.ROUND);
/* 153 */       this.paint.setStyle(Paint.Style.FILL_AND_STROKE);
/* 154 */       this.paint.setStrokeWidth(0.0F);
/* 155 */       this.paint.setAntiAlias(true);
/* 156 */       this.paint.setColor(Utils.getPostProcessedColor(pdfViewCtrl, this.color));
/* 157 */       this.paint.setAlpha((int)(255.0F * this.opacity));
/*     */     } 
/*     */     
/* 160 */     return this.paint;
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\model\ink\PressureInkItem.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */