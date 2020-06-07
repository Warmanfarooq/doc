/*     */ package com.pdftron.pdf.tools;
/*     */ 
/*     */ import android.graphics.Canvas;
/*     */ import android.graphics.Matrix;
/*     */ import android.graphics.Path;
/*     */ import android.graphics.PointF;
/*     */ import androidx.annotation.Keep;
/*     */ import androidx.annotation.NonNull;
/*     */ import com.pdftron.common.Matrix2D;
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.Annot;
/*     */ import com.pdftron.pdf.PDFDoc;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.Point;
/*     */ import com.pdftron.pdf.Rect;
/*     */ import com.pdftron.pdf.annots.Polygon;
/*     */ import com.pdftron.pdf.utils.DrawingUtils;
/*     */ import com.pdftron.pdf.utils.PointFPool;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import java.util.ArrayList;
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
/*     */ @Keep
/*     */ public class CloudCreate
/*     */   extends PolygonCreate
/*     */ {
/*     */   public static final int BORDER_INTENSITY = 2;
/*     */   
/*     */   public CloudCreate(@NonNull PDFViewCtrl ctrl) {
/*  44 */     super(ctrl);
/*  45 */     this.mNextToolMode = getToolMode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolManager.ToolModeBase getToolMode() {
/*  53 */     return ToolManager.ToolMode.CLOUD_CREATE;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCreateAnnotType() {
/*  58 */     return 1005;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Annot createMarkup(@NonNull PDFDoc doc, ArrayList<Point> pagePoints) throws PDFNetException {
/*  67 */     Rect annotRect = Utils.getBBox(pagePoints);
/*  68 */     if (annotRect == null) {
/*  69 */       return null;
/*     */     }
/*  71 */     annotRect.inflate(this.mThickness);
/*     */     
/*  73 */     Polygon poly = (Polygon)super.createMarkup(doc, pagePoints);
/*  74 */     poly.setBorderEffect(1);
/*     */     
/*  76 */     int pointIdx = 0;
/*  77 */     for (Point point : pagePoints) {
/*  78 */       poly.setVertex(pointIdx++, point);
/*     */     }
/*  80 */     poly.setRect(annotRect);
/*     */     
/*  82 */     return (Annot)poly;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawMarkup(@NonNull Canvas canvas, Matrix tfm, @NonNull ArrayList<PointF> canvasPoints) {
/*  89 */     if (this.mPdfViewCtrl == null) {
/*     */       return;
/*     */     }
/*     */     
/*  93 */     DrawingUtils.drawCloud(this.mPdfViewCtrl, getPageNum(), canvas, canvasPoints, this.mPath, this.mPaint, this.mStrokeColor, this.mFillPaint, this.mFillColor, 2.0D);
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
/*     */   public static ArrayList<PointF> getClosedPoly(ArrayList<PointF> input) {
/* 105 */     ArrayList<PointF> poly = new ArrayList<>(input);
/* 106 */     int size = input.size();
/* 107 */     if (size >= 2) {
/* 108 */       PointF firstPoint = input.get(0);
/* 109 */       PointF lastPoint = input.get(size - 1);
/* 110 */       if (!firstPoint.equals(lastPoint)) {
/* 111 */         poly.add(firstPoint);
/*     */       }
/*     */     } 
/* 114 */     return poly;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean IsPolyWrapClockwise(ArrayList<PointF> canvasPoints) {
/* 123 */     PointF oldPoint = null;
/* 124 */     double accum = 0.0D;
/* 125 */     for (PointF point : canvasPoints) {
/* 126 */       if (oldPoint != null) {
/* 127 */         accum += ((point.x - oldPoint.x) * (point.y + oldPoint.y));
/*     */       }
/* 129 */       oldPoint = point;
/*     */     } 
/*     */     
/* 132 */     return (accum < 0.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public static PointF add(@NonNull PointF p1, @NonNull PointF p2) {
/* 137 */     return PointFPool.getInstance().obtain(p1.x + p2.x, p1.y + p2.y);
/*     */   }
/*     */ 
/*     */   
/*     */   public static PointF subtract(@NonNull PointF p1, @NonNull PointF p2) {
/* 142 */     return PointFPool.getInstance().obtain(p1.x - p2.x, p1.y - p2.y);
/*     */   }
/*     */ 
/*     */   
/*     */   public static PointF multiply(@NonNull PointF p, double n) {
/* 147 */     return PointFPool.getInstance().obtain((float)(p.x * n), (float)(p.y * n));
/*     */   }
/*     */ 
/*     */   
/*     */   public static PointF divide(@NonNull PointF p, double n) {
/* 152 */     return multiply(p, 1.0D / n);
/*     */   }
/*     */ 
/*     */   
/*     */   public static double cross(@NonNull PointF p1, @NonNull PointF p2) {
/* 157 */     return (p1.x * p2.y - p1.y * p2.x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double toDegreesMod360(double radians) {
/* 164 */     double PI_2 = 6.283185307179586D;
/* 165 */     double invPI = 0.3183098861837907D;
/* 166 */     double fracPart = (radians + PI_2) % PI_2 * invPI;
/* 167 */     return fracPart * 180.0D;
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
/*     */   public static void arcToCurves(double startX, double startY, ArrayList<Double> outPoints, ArrayList<Character> outOperations, double xr, double yr, double rx, boolean isLargeArc, boolean sweep, double endX, double endY) {
/*     */     int numVertices;
/* 180 */     if (xr < 0.0D) {
/* 181 */       xr = -xr;
/*     */     }
/* 183 */     if (yr < 0.0D) {
/* 184 */       yr = -yr;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 190 */     double dx2 = (startX - endX) / 2.0D;
/* 191 */     double dy2 = (startY - endY) / 2.0D;
/*     */     
/* 193 */     double cosA = Math.cos(rx);
/* 194 */     double sinA = Math.sin(rx);
/*     */ 
/*     */ 
/*     */     
/* 198 */     double x1 = cosA * dx2 + sinA * dy2;
/* 199 */     double y1 = -sinA * dx2 + cosA * dy2;
/*     */ 
/*     */ 
/*     */     
/* 203 */     double prx = xr * xr;
/* 204 */     double pry = yr * yr;
/* 205 */     double px1 = x1 * x1;
/* 206 */     double py1 = y1 * y1;
/*     */ 
/*     */ 
/*     */     
/* 210 */     double radiiCheck = px1 / prx + py1 / pry;
/* 211 */     if (radiiCheck > 1.0D) {
/* 212 */       xr = Math.sqrt(radiiCheck) * xr;
/* 213 */       yr = Math.sqrt(radiiCheck) * yr;
/* 214 */       prx = xr * xr;
/* 215 */       pry = yr * yr;
/*     */     } 
/*     */     
/* 218 */     double denom = prx * py1 + pry * px1;
/* 219 */     if (denom == 0.0D) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 228 */     double sign = (isLargeArc == sweep) ? -1.0D : 1.0D;
/* 229 */     double sq = (prx * pry - prx * py1 - pry * px1) / denom;
/* 230 */     double coef = sign * Math.sqrt((sq < 0.0D) ? 0.0D : sq);
/* 231 */     double cx1 = coef * xr * y1 / yr;
/* 232 */     double cy1 = coef * -(yr * x1 / xr);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 237 */     double sx2 = (startX + endX) / 2.0D;
/* 238 */     double sy2 = (startY + endY) / 2.0D;
/* 239 */     double cx = sx2 + cosA * cx1 - sinA * cy1;
/* 240 */     double cy = sy2 + sinA * cx1 + cosA * cy1;
/*     */ 
/*     */ 
/*     */     
/* 244 */     double ux = (x1 - cx1) / xr;
/* 245 */     double uy = (y1 - cy1) / yr;
/* 246 */     double vx = (-x1 - cx1) / xr;
/* 247 */     double vy = (-y1 - cy1) / yr;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 252 */     double n = Math.sqrt(ux * ux + uy * uy);
/* 253 */     double p = ux;
/* 254 */     sign = (uy < 0.0D) ? -1.0D : 1.0D;
/* 255 */     double startAngle = sign * Math.acos(p / n);
/*     */ 
/*     */ 
/*     */     
/* 259 */     n = Math.sqrt((ux * ux + uy * uy) * (vx * vx + vy * vy));
/* 260 */     p = ux * vx + uy * vy;
/* 261 */     sign = (ux * vy - uy * vx < 0.0D) ? -1.0D : 1.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 266 */     double acosValue = Math.max(Math.min(p / n, 1.0D), -1.0D);
/*     */     
/* 268 */     double sweepAngle = sign * Math.acos(acosValue);
/* 269 */     if (!sweep && sweepAngle > 0.0D) {
/* 270 */       sweepAngle -= 6.283185307179586D;
/* 271 */     } else if (sweep && sweepAngle < 0.0D) {
/* 272 */       sweepAngle += 6.283185307179586D;
/*     */     } 
/*     */ 
/*     */     
/* 276 */     double[] vertices = new double[26];
/* 277 */     boolean bLineTo = false;
/*     */     
/* 279 */     if (Math.abs(sweepAngle) < 1.0E-10D) {
/* 280 */       numVertices = 4;
/*     */ 
/*     */       
/* 283 */       bLineTo = true;
/*     */       
/* 285 */       vertices[0] = xr * Math.cos(startAngle);
/* 286 */       vertices[1] = yr * Math.sin(startAngle);
/* 287 */       vertices[2] = xr * Math.cos(startAngle + sweepAngle);
/* 288 */       vertices[3] = yr * Math.sin(startAngle + sweepAngle);
/*     */     } else {
/* 290 */       double totalSweep = 0.0D;
/*     */ 
/*     */       
/* 293 */       numVertices = 2;
/*     */       
/* 295 */       boolean done = false; do {
/*     */         double localSweep;
/* 297 */         if (sweepAngle < 0.0D) {
/* 298 */           double prev_sweep = totalSweep;
/* 299 */           localSweep = -1.5707963267948966D;
/* 300 */           totalSweep -= 1.5707963267948966D;
/* 301 */           if (totalSweep <= sweepAngle + 0.01D) {
/*     */             
/* 303 */             localSweep = sweepAngle - prev_sweep;
/* 304 */             done = true;
/*     */           } 
/*     */         } else {
/* 307 */           double prev_sweep = totalSweep;
/* 308 */           localSweep = 1.5707963267948966D;
/* 309 */           totalSweep += 1.5707963267948966D;
/* 310 */           if (totalSweep >= sweepAngle - 0.01D) {
/*     */             
/* 312 */             localSweep = sweepAngle - prev_sweep;
/* 313 */             done = true;
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 319 */         int OFFSET = numVertices - 2;
/*     */         
/* 321 */         double x0 = Math.cos(localSweep / 2.0D);
/* 322 */         double y0 = Math.sin(localSweep / 2.0D);
/* 323 */         double tx = (1.0D - x0) * 4.0D / 3.0D;
/* 324 */         double ty = y0 - tx * x0 / y0;
/* 325 */         double[] px = new double[4];
/* 326 */         double[] py = new double[4];
/* 327 */         px[0] = x0;
/* 328 */         py[0] = -y0;
/* 329 */         px[1] = x0 + tx;
/* 330 */         py[1] = -ty;
/* 331 */         px[2] = x0 + tx;
/* 332 */         py[2] = ty;
/* 333 */         px[3] = x0;
/* 334 */         py[3] = y0;
/*     */         
/* 336 */         double sn = Math.sin(startAngle + localSweep / 2.0D);
/* 337 */         double cs = Math.cos(startAngle + localSweep / 2.0D);
/*     */         
/* 339 */         for (int i = 0; i < 4; i++) {
/* 340 */           vertices[OFFSET + i * 2] = xr * (px[i] * cs - py[i] * sn);
/* 341 */           vertices[OFFSET + i * 2 + 1] = yr * (px[i] * sn + py[i] * cs);
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 346 */         numVertices += 6;
/* 347 */         startAngle += localSweep;
/*     */       }
/* 349 */       while (!done && numVertices < 26);
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 354 */       Matrix2D mtx = Matrix2D.rotationMatrix(-rx);
/* 355 */       mtx = mtx.translate(cx, cy);
/*     */       
/* 357 */       for (int i = 2; i < numVertices - 2; i += 2) {
/* 358 */         Point point = mtx.multPoint(vertices[i], vertices[i + 1]);
/* 359 */         vertices[i] = point.x;
/* 360 */         vertices[i + 1] = point.y;
/*     */       } 
/* 362 */     } catch (PDFNetException ignored) {
/*     */       return;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 368 */     vertices[0] = startX;
/* 369 */     vertices[1] = startY;
/* 370 */     if (numVertices > 2) {
/* 371 */       vertices[numVertices - 2] = endX;
/* 372 */       vertices[numVertices - 1] = endY;
/*     */     } 
/*     */     
/* 375 */     if (bLineTo) {
/* 376 */       outOperations.add(Character.valueOf('l'));
/* 377 */       outOperations.add(Character.valueOf('l'));
/* 378 */       outPoints.add(Double.valueOf(vertices[0]));
/* 379 */       outPoints.add(Double.valueOf(vertices[1]));
/* 380 */       outPoints.add(Double.valueOf(vertices[2]));
/* 381 */       outPoints.add(Double.valueOf(vertices[3]));
/*     */     } else {
/* 383 */       outOperations.add(Character.valueOf('l'));
/* 384 */       outPoints.add(Double.valueOf(vertices[0]));
/* 385 */       outPoints.add(Double.valueOf(vertices[1]));
/*     */       
/* 387 */       for (int i = 2; i < numVertices; i += 6) {
/* 388 */         outOperations.add(Character.valueOf('c'));
/* 389 */         outPoints.add(Double.valueOf(vertices[i]));
/* 390 */         outPoints.add(Double.valueOf(vertices[i + 1]));
/* 391 */         outPoints.add(Double.valueOf(vertices[i + 2]));
/* 392 */         outPoints.add(Double.valueOf(vertices[i + 3]));
/* 393 */         outPoints.add(Double.valueOf(vertices[i + 4]));
/* 394 */         outPoints.add(Double.valueOf(vertices[i + 5]));
/*     */       } 
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
/*     */   public static PointF arcTo(@NonNull Path path, float startX, float startY, double xr, double yr, double rx, boolean isLargeArc, boolean sweep, double endX, double endY) {
/* 409 */     ArrayList<Double> outPoints = new ArrayList<>(30);
/* 410 */     ArrayList<Character> outOperations = new ArrayList<>();
/* 411 */     arcToCurves(startX, startY, outPoints, outOperations, xr, yr, rx, isLargeArc, sweep, endX, endY);
/*     */ 
/*     */     
/* 414 */     int pointIndex = 0;
/* 415 */     for (Character operation : outOperations) {
/* 416 */       if (operation.charValue() == 'c') {
/* 417 */         path.cubicTo(((Double)outPoints.get(pointIndex)).floatValue(), ((Double)outPoints
/* 418 */             .get(pointIndex + 1)).floatValue(), ((Double)outPoints
/* 419 */             .get(pointIndex + 2)).floatValue(), ((Double)outPoints
/* 420 */             .get(pointIndex + 3)).floatValue(), ((Double)outPoints
/* 421 */             .get(pointIndex + 4)).floatValue(), ((Double)outPoints
/* 422 */             .get(pointIndex + 5)).floatValue());
/* 423 */         startX = ((Double)outPoints.get(pointIndex + 4)).floatValue();
/* 424 */         startY = ((Double)outPoints.get(pointIndex + 5)).floatValue();
/* 425 */         pointIndex += 6; continue;
/* 426 */       }  if (operation.charValue() == 'l') {
/* 427 */         path.lineTo(((Double)outPoints.get(pointIndex)).floatValue(), ((Double)outPoints
/* 428 */             .get(pointIndex + 1)).floatValue());
/* 429 */         startX = ((Double)outPoints.get(pointIndex)).floatValue();
/* 430 */         startY = ((Double)outPoints.get(pointIndex + 1)).floatValue();
/* 431 */         pointIndex += 2;
/*     */       } 
/*     */     } 
/*     */     
/* 435 */     return PointFPool.getInstance().obtain(startX, startY);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\CloudCreate.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */