/*     */ package com.pdftron.pdf.model.ink;
/*     */ 
/*     */ import android.graphics.Canvas;
/*     */ import android.graphics.Matrix;
/*     */ import android.graphics.Paint;
/*     */ import android.graphics.Path;
/*     */ import android.graphics.PointF;
/*     */ import android.util.Log;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.annots.Ink;
/*     */ import com.pdftron.pdf.utils.Logger;
/*     */ import com.pdftron.pdf.utils.PathPool;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InkItem
/*     */ {
/*  31 */   private static final String TAG = InkItem.class.getName();
/*     */   
/*     */   private static boolean sDebug = false;
/*     */   
/*     */   private static final int ACTION_PEN_DOWN = 211;
/*     */   private static final int ACTION_PEN_MOVE = 213;
/*     */   private static final int ACTION_PEN_UP = 212;
/*     */   public final String id;
/*     */   @Nullable
/*  40 */   public List<PointF> currentActiveStroke = new ArrayList<>();
/*     */   
/*     */   public final List<List<PointF>> finishedStrokes;
/*  43 */   private final Map<List<PointF>, Path> drawPaths = new HashMap<>();
/*     */   
/*     */   public final int pageNumber;
/*     */   public final int color;
/*     */   public final float opacity;
/*     */   public final float baseThickness;
/*     */   public float paintThickness;
/*     */   public final boolean isStylus;
/*  51 */   public Paint paint = null;
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public List<PointF> previousStroke;
/*     */ 
/*     */   
/*     */   public boolean shouldAnimateUndoRedo = true;
/*     */ 
/*     */ 
/*     */   
/*     */   public InkItem copy() {
/*  63 */     InkItem newInkItem = new InkItem(this.id, null, new ArrayList<>(this.finishedStrokes), this.pageNumber, this.color, this.opacity, this.baseThickness, this.paintThickness, this.isStylus);
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
/*  74 */     newInkItem.shouldAnimateUndoRedo = this.shouldAnimateUndoRedo;
/*  75 */     newInkItem.previousStroke = (this.previousStroke == null) ? null : new ArrayList<>(this.previousStroke);
/*  76 */     return newInkItem;
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
/*     */   public InkItem(String id, @Nullable List<PointF> currentActiveStroke, List<List<PointF>> finishedStrokes, int pageNumber, int color, float opacity, float baseThickness, float paintThickness, boolean isStylus) {
/*  89 */     this.id = id;
/*  90 */     this.currentActiveStroke = currentActiveStroke;
/*  91 */     this.finishedStrokes = finishedStrokes;
/*  92 */     this.pageNumber = pageNumber;
/*  93 */     this.color = color;
/*  94 */     this.opacity = opacity;
/*  95 */     this.baseThickness = baseThickness;
/*  96 */     this.paintThickness = paintThickness;
/*  97 */     this.isStylus = isStylus;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InkItem(int pageNumber, int color, float opacity, float baseThickness, boolean isStylus, PDFViewCtrl pdfViewCtrl) {
/* 107 */     this.id = UUID.randomUUID().toString();
/*     */     
/* 109 */     this.finishedStrokes = new ArrayList<>();
/*     */     
/* 111 */     float zoom = (float)pdfViewCtrl.getZoom();
/* 112 */     this.paintThickness = baseThickness * zoom;
/*     */     
/* 114 */     this.color = color;
/* 115 */     this.opacity = opacity;
/* 116 */     this.baseThickness = baseThickness;
/* 117 */     this.isStylus = isStylus;
/* 118 */     this.pageNumber = pageNumber;
/*     */   }
/*     */   
/*     */   public void addPoint(float x, float y, float pressure, int action) {
/* 122 */     switch (action) {
/*     */       case 0:
/*     */       case 211:
/* 125 */         onDown(x, y, pressure);
/*     */         return;
/*     */       case 2:
/*     */       case 213:
/* 129 */         onMove(x, y, pressure);
/*     */         return;
/*     */ 
/*     */       
/*     */       case 1:
/*     */       case 212:
/* 135 */         onUp();
/*     */         return;
/*     */     } 
/* 138 */     if (sDebug) {
/* 139 */       Log.d(InkItem.class.getName(), "Unhandled state " + action);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isPageInPages(PDFViewCtrl pdfViewCtrl, int page) {
/* 145 */     int[] pages = pdfViewCtrl.getVisiblePagesInTransition();
/* 146 */     for (int p : pages) {
/* 147 */       if (p == page) {
/* 148 */         return true;
/*     */       }
/*     */     } 
/* 151 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void draw(@NonNull Canvas canvas, @NonNull PDFViewCtrl pdfViewCtrl, @Nullable Matrix transform, @Nullable PointF offset) {
/* 161 */     if (!pdfViewCtrl.isContinuousPagePresentationMode(pdfViewCtrl.getPagePresentationMode()) && 
/* 162 */       !isPageInPages(pdfViewCtrl, this.pageNumber)) {
/*     */       return;
/*     */     }
/*     */     
/* 166 */     if (this.currentActiveStroke != null) {
/* 167 */       Path currentPath = createPathFromCurrentActiveStroke(this.currentActiveStroke, pdfViewCtrl, offset);
/* 168 */       drawPathOnCanvas(canvas, currentPath, pdfViewCtrl, transform);
/*     */     } 
/*     */ 
/*     */     
/* 172 */     float newPaintThickness = (float)(this.baseThickness * pdfViewCtrl.getZoom());
/* 173 */     boolean hasZoomedSinceLastDraw = (newPaintThickness != this.paintThickness);
/* 174 */     if (hasZoomedSinceLastDraw) {
/* 175 */       this.paintThickness = newPaintThickness;
/* 176 */       this.drawPaths.clear();
/*     */     } 
/*     */ 
/*     */     
/* 180 */     for (int i = 0; i < this.finishedStrokes.size(); i++) {
/* 181 */       Path previousPath; List<PointF> screenStroke = this.finishedStrokes.get(i);
/*     */       
/* 183 */       if (this.drawPaths.containsKey(screenStroke)) {
/* 184 */         previousPath = this.drawPaths.get(screenStroke);
/*     */       } else {
/* 186 */         previousPath = createPathFromFinishedStroke(i, pdfViewCtrl, offset);
/* 187 */         this.drawPaths.put(screenStroke, previousPath);
/*     */       } 
/*     */       
/* 190 */       drawPathOnCanvas(canvas, previousPath, pdfViewCtrl, transform);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected Path createPathFromCurrentActiveStroke(@NonNull List<PointF> points, @NonNull PDFViewCtrl pdfViewCtrl, @Nullable PointF offset) {
/* 195 */     return createPathFromPagePoint(points, pdfViewCtrl, offset);
/*     */   }
/*     */   
/*     */   protected Path createPathFromFinishedStroke(int index, @NonNull PDFViewCtrl pdfViewCtrl, @Nullable PointF offset) {
/* 199 */     return createPathFromPagePoint(this.finishedStrokes.get(index), pdfViewCtrl, offset);
/*     */   }
/*     */   
/*     */   private Path createPathFromPagePoint(@NonNull List<PointF> points, @NonNull PDFViewCtrl pdfViewCtrl, @Nullable PointF offset) {
/* 203 */     float xOffset = 0.0F;
/* 204 */     float yOffset = 0.0F;
/* 205 */     if (offset != null) {
/* 206 */       xOffset = offset.x;
/* 207 */       yOffset = offset.y;
/*     */     } 
/*     */     
/* 210 */     Path path = PathPool.getInstance().obtain();
/*     */     
/* 212 */     if (points.size() < 1)
/* 213 */       return path; 
/* 214 */     if (points.size() == 1) {
/* 215 */       PointF pt1 = points.get(0);
/* 216 */       float[] scrPt = convPagePtToHorizontalScrollingPt(pt1.x, pt1.y, pdfViewCtrl);
/* 217 */       path.moveTo(scrPt[0], scrPt[1]);
/*     */       
/* 219 */       path.lineTo(scrPt[0] + 0.01F, scrPt[1]);
/* 220 */     } else if (this.isStylus) {
/*     */       
/* 222 */       float[] pts = convPagePtToHorizontalScrollingPt(((PointF)points.get(0)).x, ((PointF)points.get(0)).y, pdfViewCtrl);
/* 223 */       path.moveTo(pts[0] - xOffset, pts[1] - yOffset);
/*     */       
/* 225 */       for (PointF point : points) {
/* 226 */         float[] arrayOfFloat = convPagePtToHorizontalScrollingPt(point.x, point.y, pdfViewCtrl);
/* 227 */         path.lineTo(arrayOfFloat[0] - xOffset, arrayOfFloat[1] - yOffset);
/*     */       } 
/*     */     } else {
/*     */       
/* 231 */       double[] currentBeizerPts, inputLine = new double[points.size() * 2];
/* 232 */       for (int i = 0, cnt = points.size(); i < cnt; i++) {
/* 233 */         float[] pts = convPagePtToHorizontalScrollingPt(((PointF)points.get(i)).x, ((PointF)points.get(i)).y, pdfViewCtrl);
/* 234 */         inputLine[i * 2] = (pts[0] - xOffset);
/* 235 */         inputLine[i * 2 + 1] = (pts[1] - yOffset);
/*     */       } 
/*     */ 
/*     */       
/*     */       try {
/* 240 */         currentBeizerPts = Ink.getBezierControlPoints(inputLine);
/* 241 */       } catch (Exception e) {
/* 242 */         return path;
/*     */       } 
/*     */       
/* 245 */       path.moveTo((float)currentBeizerPts[0], (float)currentBeizerPts[1]);
/* 246 */       for (int j = 2, k = currentBeizerPts.length; j < k; j += 6) {
/* 247 */         path.cubicTo((float)currentBeizerPts[j], (float)currentBeizerPts[j + 1], (float)currentBeizerPts[j + 2], (float)currentBeizerPts[j + 3], (float)currentBeizerPts[j + 4], (float)currentBeizerPts[j + 5]);
/*     */       }
/*     */     } 
/*     */     
/* 251 */     return path;
/*     */   }
/*     */   
/*     */   protected float[] convPagePtToHorizontalScrollingPt(float x, float y, @NonNull PDFViewCtrl pdfViewCtrl) {
/* 255 */     double[] pt = pdfViewCtrl.convPagePtToHorizontalScrollingPt(x, y, this.pageNumber);
/* 256 */     return new float[] { (float)pt[0], (float)pt[1] };
/*     */   }
/*     */ 
/*     */   
/*     */   private void drawPathOnCanvas(@NonNull Canvas canvas, @NonNull Path path, @NonNull PDFViewCtrl pdfViewCtrl, @Nullable Matrix transform) {
/* 261 */     Path drawPath = path;
/* 262 */     if (transform != null) {
/* 263 */       drawPath = new Path();
/* 264 */       drawPath.addPath(path, transform);
/*     */     } 
/*     */     
/* 267 */     if (pdfViewCtrl.isMaintainZoomEnabled()) {
/* 268 */       canvas.save();
/*     */       try {
/* 270 */         canvas.translate(0.0F, -pdfViewCtrl.getScrollYOffsetInTools(this.pageNumber));
/* 271 */         canvas.drawPath(drawPath, getPaint(pdfViewCtrl));
/*     */       } finally {
/* 273 */         canvas.restore();
/*     */       } 
/*     */     } else {
/* 276 */       canvas.drawPath(drawPath, getPaint(pdfViewCtrl));
/*     */     } 
/*     */   }
/*     */   
/*     */   public Paint getPaint(@NonNull PDFViewCtrl pdfViewCtrl) {
/* 281 */     if (this.paint == null) {
/* 282 */       this.paint = new Paint();
/* 283 */       this.paint.setStrokeJoin(Paint.Join.ROUND);
/* 284 */       this.paint.setStrokeCap(Paint.Cap.ROUND);
/* 285 */       this.paint.setStyle(Paint.Style.STROKE);
/* 286 */       this.paint.setStrokeWidth(this.paintThickness);
/* 287 */       this.paint.setAntiAlias(true);
/* 288 */       this.paint.setColor(Utils.getPostProcessedColor(pdfViewCtrl, this.color));
/* 289 */       this.paint.setAlpha((int)(255.0F * this.opacity));
/*     */     } 
/*     */     
/* 292 */     if (this.paintThickness != this.paint.getStrokeWidth()) {
/* 293 */       this.paint.setStrokeWidth(this.paintThickness);
/*     */     }
/*     */     
/* 296 */     return this.paint;
/*     */   }
/*     */   
/*     */   protected void onDown(float x, float y, float pressure) {
/* 300 */     this.currentActiveStroke = new ArrayList<>();
/* 301 */     this.currentActiveStroke.add(new PointF(x, y));
/*     */   }
/*     */   
/*     */   protected void onMove(float x, float y, float pressure) {
/* 305 */     if (this.currentActiveStroke == null) {
/* 306 */       Logger.INSTANCE.LogE(TAG, "currentActiveStroke is null in onMove. This should not happen. Missing onDown call");
/*     */       return;
/*     */     } 
/* 309 */     this.currentActiveStroke.add(new PointF(x, y));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onUp() {
/* 315 */     if (this.currentActiveStroke == null) {
/* 316 */       Logger.INSTANCE.LogE(TAG, "currentActiveStroke is null in onUp. This should not happen. Missing onDown call");
/*     */       return;
/*     */     } 
/* 319 */     List<PointF> newStroke = Collections.unmodifiableList(this.currentActiveStroke);
/* 320 */     this.finishedStrokes.add(newStroke);
/* 321 */     this.previousStroke = newStroke;
/* 322 */     this.currentActiveStroke = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 327 */     return this.id.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 332 */     if (this == obj) {
/* 333 */       return true;
/*     */     }
/* 335 */     if (obj == null || getClass() != obj.getClass()) {
/* 336 */       return false;
/*     */     }
/* 338 */     InkItem other = (InkItem)obj;
/* 339 */     return this.id.equals(other.id);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\model\ink\InkItem.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */