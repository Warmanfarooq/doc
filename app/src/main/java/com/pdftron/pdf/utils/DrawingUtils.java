/*     */ package com.pdftron.pdf.utils;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.content.res.Resources;
/*     */ import android.graphics.Canvas;
/*     */ import android.graphics.DashPathEffect;
/*     */ import android.graphics.Matrix;
/*     */ import android.graphics.Paint;
/*     */ import android.graphics.Path;
/*     */ import android.graphics.PathEffect;
/*     */ import android.graphics.PointF;
/*     */ import android.graphics.RectF;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.model.ink.InkItem;
/*     */ import com.pdftron.pdf.tools.CloudCreate;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.widget.AnnotView;
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
/*     */ public class DrawingUtils
/*     */ {
/*     */   public static final int sSelectionBoxMargin = 16;
/*     */   
/*     */   public static void drawSelectionBox(@NonNull Paint paint, @NonNull Context context, @NonNull Canvas canvas, float left, float top, float right, float bottom, boolean hasSelectionPermission) {
/*     */     float calculatedPadding;
/*  43 */     DashPathEffect dashPathEffect = new DashPathEffect(new float[] { Utils.convDp2Pix(context, 4.5F), Utils.convDp2Pix(context, 2.5F) }0.0F);
/*  44 */     paint.setStyle(Paint.Style.STROKE);
/*  45 */     float thickness = Utils.convDp2Pix(context, 2.2F);
/*  46 */     float padding = thickness * 1.5F;
/*  47 */     float cornerRad = thickness / 2.0F;
/*  48 */     float lineOverlapPadding = thickness / 2.0F;
/*     */     
/*  50 */     if (hasSelectionPermission) {
/*  51 */       calculatedPadding = lineOverlapPadding;
/*  52 */       paint.setColor(context.getResources().getColor(R.color.tools_annot_edit_line_shadow));
/*  53 */       paint.setStrokeWidth(thickness);
/*     */     } else {
/*  55 */       calculatedPadding = lineOverlapPadding + padding;
/*  56 */       paint.setColor(context.getResources().getColor(R.color.tools_annot_edit_line_shadow_no_permission));
/*  57 */       paint.setPathEffect((PathEffect)dashPathEffect);
/*  58 */       paint.setStrokeWidth(thickness * 1.1F);
/*     */     } 
/*  60 */     canvas.drawRoundRect(new RectF(left - calculatedPadding + thickness / 2.0F, top - calculatedPadding + thickness / 2.0F, right + calculatedPadding - thickness / 2.0F, bottom + calculatedPadding - thickness / 2.0F), cornerRad, cornerRad, paint);
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
/*     */   public static void drawCtrlPtsLine(@NonNull Resources resources, @NonNull Canvas canvas, @NonNull Paint paint, @NonNull PointF pt1, @NonNull PointF pt2, float radius, boolean hasPermission) {
/*  74 */     float left = pt1.x;
/*  75 */     float bottom = pt1.y;
/*  76 */     float right = pt2.x;
/*  77 */     float top = pt2.y;
/*     */     
/*  79 */     paint.setColor(resources.getColor(R.color.tools_selection_control_point));
/*  80 */     paint.setStyle(Paint.Style.FILL);
/*  81 */     if (hasPermission) {
/*  82 */       canvas.drawCircle(left, bottom, radius, paint);
/*  83 */       canvas.drawCircle(right, top, radius, paint);
/*     */     } 
/*     */     
/*  86 */     paint.setColor(resources.getColor(R.color.tools_selection_control_point_border));
/*  87 */     paint.setStyle(Paint.Style.STROKE);
/*  88 */     if (hasPermission) {
/*  89 */       canvas.drawCircle(left, bottom, radius, paint);
/*  90 */       canvas.drawCircle(right, top, radius, paint);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawCtrlPts(@NonNull Resources resources, @NonNull Canvas canvas, @NonNull Paint paint, @NonNull PointF pt1, @NonNull PointF pt2, @NonNull PointF midH, @NonNull PointF midV, float radius, boolean hasPermission, boolean maintainAspectRatio) {
/*  99 */     float left = Math.min(pt1.x, pt2.x);
/* 100 */     float right = Math.max(pt1.x, pt2.x);
/* 101 */     float top = Math.min(pt1.y, pt2.y);
/* 102 */     float bottom = Math.max(pt1.y, pt2.y);
/*     */     
/* 104 */     float middle_x = midH.x;
/* 105 */     float middle_y = midV.y;
/*     */ 
/*     */     
/* 108 */     paint.setColor(resources.getColor(R.color.tools_selection_control_point));
/* 109 */     paint.setStyle(Paint.Style.FILL);
/* 110 */     if (hasPermission) {
/* 111 */       canvas.drawCircle(left, bottom, radius, paint);
/* 112 */       canvas.drawCircle(right, bottom, radius, paint);
/* 113 */       canvas.drawCircle(right, top, radius, paint);
/* 114 */       canvas.drawCircle(left, top, radius, paint);
/*     */     } 
/*     */     
/* 117 */     if (!maintainAspectRatio && 
/* 118 */       hasPermission) {
/* 119 */       canvas.drawCircle(middle_x, bottom, radius, paint);
/* 120 */       canvas.drawCircle(right, middle_y, radius, paint);
/* 121 */       canvas.drawCircle(middle_x, top, radius, paint);
/* 122 */       canvas.drawCircle(left, middle_y, radius, paint);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 127 */     paint.setColor(resources.getColor(R.color.tools_selection_control_point_border));
/* 128 */     paint.setStyle(Paint.Style.STROKE);
/* 129 */     if (hasPermission) {
/* 130 */       canvas.drawCircle(left, bottom, radius, paint);
/* 131 */       canvas.drawCircle(right, bottom, radius, paint);
/* 132 */       canvas.drawCircle(right, top, radius, paint);
/* 133 */       canvas.drawCircle(left, top, radius, paint);
/*     */     } 
/*     */     
/* 136 */     if (!maintainAspectRatio && 
/* 137 */       hasPermission) {
/* 138 */       canvas.drawCircle(middle_x, bottom, radius, paint);
/* 139 */       canvas.drawCircle(right, middle_y, radius, paint);
/* 140 */       canvas.drawCircle(middle_x, top, radius, paint);
/* 141 */       canvas.drawCircle(left, middle_y, radius, paint);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawCtrlPtsAdvancedShape(@NonNull Resources resources, @NonNull Canvas canvas, @NonNull Paint paint, @NonNull PointF[] ctrlPts, float radius, boolean hasPermission, boolean skipEndPoint) {
/* 151 */     paint.setColor(resources.getColor(R.color.tools_selection_control_point));
/* 152 */     paint.setStyle(Paint.Style.FILL);
/* 153 */     if (hasPermission) {
/* 154 */       for (int i = 0; i < ctrlPts.length; i++) {
/* 155 */         if (!skipEndPoint || i != 10) {
/*     */ 
/*     */ 
/*     */           
/* 159 */           PointF pt = ctrlPts[i];
/* 160 */           if (pt != null) {
/* 161 */             canvas.drawCircle(pt.x, pt.y, radius, paint);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 167 */     paint.setColor(resources.getColor(R.color.tools_selection_control_point_border));
/* 168 */     paint.setStyle(Paint.Style.STROKE);
/* 169 */     if (hasPermission)
/* 170 */       for (int i = 0; i < ctrlPts.length; i++) {
/* 171 */         if (!skipEndPoint || i != 10) {
/*     */ 
/*     */ 
/*     */           
/* 175 */           PointF pt = ctrlPts[i];
/* 176 */           if (pt != null) {
/* 177 */             canvas.drawCircle(pt.x, pt.y, radius, paint);
/*     */           }
/*     */         } 
/*     */       }  
/*     */   }
/*     */   
/*     */   private static float xWithRotation(float x, float y, float width, float height, int degree) {
/* 184 */     double rad = Math.toRadians(degree);
/* 185 */     return (float)(x + height * Math.sin(rad) + width * Math.cos(rad));
/*     */   }
/*     */   
/*     */   private static float yWithRotation(float x, float y, float width, float height, int degree) {
/* 189 */     double rad = Math.toRadians(degree);
/* 190 */     return (float)(y + height * Math.cos(rad) - width * Math.sin(rad));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void drawDashedLine(@NonNull Canvas canvas, @NonNull Path path, float startX, float startY, float stopX, float stopY, @NonNull Paint paint) {
/* 196 */     path.moveTo(startX, startY);
/* 197 */     path.lineTo(stopX, stopY);
/*     */     
/* 199 */     canvas.drawPath(path, paint);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void drawGuideline(@NonNull AnnotView.SnapMode snapMode, float extend, @NonNull Canvas canvas, @NonNull RectF bbox, @NonNull Path path, @NonNull Paint paint) {
/* 204 */     float centerX = bbox.centerX();
/* 205 */     float centerY = bbox.centerY();
/*     */     
/* 207 */     float left = bbox.left;
/* 208 */     float top = bbox.top;
/* 209 */     float right = bbox.right;
/* 210 */     float bottom = bbox.bottom;
/*     */ 
/*     */     
/* 213 */     double len = MeasureUtils.getLineLength(left, bottom, right, top);
/* 214 */     double newLeft = left + (left - right) / len * extend;
/* 215 */     double newBottom = bottom + (bottom - top) / len * extend;
/* 216 */     double newRight = right + (right - left) / len * extend;
/* 217 */     double newTop = top + (top - bottom) / len * extend;
/*     */     
/* 219 */     path.reset();
/*     */     
/* 221 */     if (snapMode == AnnotView.SnapMode.HORIZONTAL) {
/* 222 */       drawDashedLine(canvas, path, centerX, top - extend, centerX, bottom + extend, paint);
/* 223 */     } else if (snapMode == AnnotView.SnapMode.VERTICAL) {
/* 224 */       drawDashedLine(canvas, path, left - extend, centerY, right + extend, centerY, paint);
/* 225 */     } else if (snapMode == AnnotView.SnapMode.ASPECT_RATIO_L) {
/* 226 */       drawDashedLine(canvas, path, (float)newLeft, (float)newBottom, (float)newRight, (float)newTop, paint);
/* 227 */     } else if (snapMode == AnnotView.SnapMode.ASPECT_RATIO_R) {
/* 228 */       drawDashedLine(canvas, path, (float)newLeft, (float)newTop, (float)newRight, (float)newBottom, paint);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawGuideline(int degree, float radius, @NonNull Canvas canvas, @NonNull RectF bbox, @NonNull Path path, @NonNull Paint paint) {
/* 237 */     float centerX = bbox.centerX();
/* 238 */     float centerY = bbox.centerY();
/* 239 */     float maxSize = Math.max(bbox.width(), bbox.height());
/* 240 */     float size = maxSize / 4.0F * 3.0F;
/* 241 */     paint.setStyle(Paint.Style.FILL);
/* 242 */     canvas.drawCircle(centerX, centerY, radius, paint);
/* 243 */     paint.setStyle(Paint.Style.STROKE);
/* 244 */     path.reset();
/*     */     
/* 246 */     drawDashedLine(canvas, path, centerX, centerY, centerX + size, centerY, paint);
/* 247 */     if (degree == 90) {
/* 248 */       drawDashedLine(canvas, path, centerX, centerY - size, centerX, centerY, paint);
/* 249 */     } else if (degree == 180) {
/* 250 */       drawDashedLine(canvas, path, centerX - size, centerY, centerX, centerY, paint);
/* 251 */     } else if (degree == -90) {
/* 252 */       drawDashedLine(canvas, path, centerX, centerY, centerX, centerY + size, paint);
/*     */     } 
/* 254 */     if (degree == 45 || degree == 135 || degree == 225 || degree == -45) {
/* 255 */       drawDashedLine(canvas, path, centerX, centerY, 
/* 256 */           xWithRotation(centerX, centerY, size, 0.0F, degree), 
/* 257 */           yWithRotation(centerX, centerY, size, 0.0F, degree), paint);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawInk(@NonNull PDFViewCtrl pdfViewCtrl, @NonNull Canvas canvas, @NonNull ArrayList<InkItem> inks, @Nullable Matrix transform, @Nullable PointF offset) {
/* 266 */     for (InkItem ink : inks) {
/* 267 */       ink.draw(canvas, pdfViewCtrl, transform, offset);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawRectangle(@NonNull Canvas canvas, @NonNull PointF pt1, @NonNull PointF pt2, float thicknessDraw, int fillColor, int strokeColor, @NonNull Paint fillPaint, @NonNull Paint paint) {
/* 276 */     float min_x = Math.min(pt1.x, pt2.x);
/* 277 */     float max_x = Math.max(pt1.x, pt2.x);
/* 278 */     float min_y = Math.min(pt1.y, pt2.y);
/* 279 */     float max_y = Math.max(pt1.y, pt2.y);
/*     */ 
/*     */ 
/*     */     
/* 283 */     float adjust = thicknessDraw / 2.0F;
/*     */     
/* 285 */     if (fillColor != 0) {
/* 286 */       canvas.drawRect(min_x + thicknessDraw, min_y + thicknessDraw, max_x - thicknessDraw, max_y - thicknessDraw, fillPaint);
/*     */     }
/*     */     
/* 289 */     if (strokeColor != 0) {
/* 290 */       canvas.drawRect(min_x + adjust, min_y + adjust, max_x - adjust, max_y - adjust, paint);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawOval(@NonNull Canvas canvas, @NonNull PointF pt1, @NonNull PointF pt2, float thicknessDraw, @NonNull RectF oval, int fillColor, int strokeColor, @NonNull Paint fillPaint, @NonNull Paint paint) {
/* 301 */     float min_x = Math.min(pt1.x, pt2.x);
/* 302 */     float max_x = Math.max(pt1.x, pt2.x);
/* 303 */     float min_y = Math.min(pt1.y, pt2.y);
/* 304 */     float max_y = Math.max(pt1.y, pt2.y);
/*     */ 
/*     */ 
/*     */     
/* 308 */     float adjust = thicknessDraw / 2.0F;
/* 309 */     min_x += adjust;
/* 310 */     max_x -= adjust;
/* 311 */     min_y += adjust;
/* 312 */     max_y -= adjust;
/*     */     
/* 314 */     oval.set(min_x, min_y, max_x, max_y);
/* 315 */     if (fillColor != 0) {
/* 316 */       canvas.drawArc(oval, 0.0F, 360.0F, false, fillPaint);
/*     */     }
/* 318 */     if (strokeColor != 0) {
/* 319 */       canvas.drawArc(oval, 0.0F, 360.0F, false, paint);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void drawLine(Canvas canvas, PointF pt1, PointF pt2, Paint paint) {
/* 324 */     canvas.drawLine(pt1.x, pt1.y, pt2.x, pt2.y, paint);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawArrow(Canvas canvas, PointF pt1, PointF pt2, PointF pt3, PointF pt4, Path path, Paint paint) {
/* 330 */     path.reset();
/*     */     
/* 332 */     path.moveTo(pt1.x, pt1.y);
/* 333 */     path.lineTo(pt2.x, pt2.y);
/*     */ 
/*     */     
/* 336 */     path.moveTo(pt3.x, pt3.y);
/* 337 */     path.lineTo(pt2.x, pt2.y);
/* 338 */     path.lineTo(pt4.x, pt4.y);
/*     */     
/* 340 */     canvas.drawPath(path, paint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void calcArrow(@NonNull PointF pt1, @NonNull PointF pt2, @NonNull PointF pt3, @NonNull PointF pt4, float thickness, double zoom) {
/* 349 */     pt3.set(pt2);
/* 350 */     pt4.set(pt2);
/*     */     
/* 352 */     double lineangle = Math.atan2((pt2.y - pt1.y), (pt2.x - pt1.x));
/* 353 */     double revangle = (lineangle > Math.PI) ? (lineangle - Math.PI) : (lineangle + Math.PI);
/*     */     
/* 355 */     double len = getLenOfLine(pt1, pt2, thickness, zoom, false);
/*     */     
/* 357 */     double phi1 = revangle + 0.5235987755982988D;
/* 358 */     double phi2 = revangle - 0.5235987755982988D;
/* 359 */     double ax = pt2.x + len * Math.cos(phi1);
/* 360 */     double ay = pt2.y + len * Math.sin(phi1);
/*     */     
/* 362 */     double bx = pt2.x + len * Math.cos(phi2);
/* 363 */     double by = pt2.y + len * Math.sin(phi2);
/*     */     
/* 365 */     pt3.set((float)ax, (float)ay);
/* 366 */     pt4.set((float)bx, (float)by);
/*     */   }
/*     */ 
/*     */   
/*     */   private static double getLenOfLine(@NonNull PointF pt1, @NonNull PointF pt2, float thickness, double zoom, boolean halfALine) {
/* 371 */     double len = (5.0F * thickness + 2.0F);
/* 372 */     len *= zoom;
/* 373 */     double constant = 0.35D;
/* 374 */     if (halfALine) {
/* 375 */       constant = 0.7D;
/*     */     }
/* 377 */     len = Math.min(distance(pt1, pt2) * constant, len);
/* 378 */     return len;
/*     */   }
/*     */   
/*     */   public static double distance(@NonNull PointF p1, @NonNull PointF p2) {
/* 382 */     double w = (p1.x - p2.x);
/* 383 */     double h = (p1.y - p2.y);
/* 384 */     return Math.sqrt(w * w + h * h);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawRuler(@NonNull Canvas canvas, @NonNull PointF pt1, @NonNull PointF pt2, @NonNull PointF pt3, @NonNull PointF pt4, @NonNull PointF pt5, @NonNull PointF pt6, @NonNull Path path, @NonNull Paint paint, @NonNull String text, double zoom) {
/* 391 */     path.reset();
/*     */     
/* 393 */     path.moveTo(pt1.x, pt1.y);
/* 394 */     path.lineTo(pt2.x, pt2.y);
/*     */ 
/*     */     
/* 397 */     path.moveTo(pt3.x, pt3.y);
/* 398 */     path.lineTo(pt4.x, pt4.y);
/*     */ 
/*     */     
/* 401 */     path.moveTo(pt5.x, pt5.y);
/* 402 */     path.lineTo(pt6.x, pt6.y);
/*     */     
/* 404 */     canvas.drawPath(path, paint);
/*     */ 
/*     */     
/* 407 */     float width = paint.getStrokeWidth();
/* 408 */     paint.setStrokeWidth(0.0F);
/* 409 */     paint.setTextAlign(Paint.Align.CENTER);
/* 410 */     paint.setTextSize((float)(12.0D * zoom));
/* 411 */     double yOffset = -4.0D * zoom;
/* 412 */     canvas.drawTextOnPath(text, path, 0.0F, (float)yOffset, paint);
/* 413 */     paint.setStrokeWidth(width);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void calcRuler(@NonNull PointF pt1, @NonNull PointF pt2, @NonNull PointF pt3, @NonNull PointF pt4, @NonNull PointF pt5, @NonNull PointF pt6, float thickness, double zoom) {
/* 420 */     double len = getLenOfLine(pt1, pt2, thickness, zoom, true);
/*     */     
/* 422 */     Vec2 start = new Vec2(pt1.x, pt1.y);
/* 423 */     Vec2 end = new Vec2(pt2.x, pt2.y);
/* 424 */     PointF midPt = midpoint(pt1, pt2);
/* 425 */     Vec2 mid = new Vec2(midPt.x, midPt.y);
/*     */ 
/*     */     
/* 428 */     calRulerButt(mid, start, len, pt3, pt4);
/*     */ 
/*     */     
/* 431 */     calRulerButt(mid, end, len, pt5, pt6);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void calRulerButt(@NonNull Vec2 start, @NonNull Vec2 end, double len, @NonNull PointF pt1, @NonNull PointF pt2) {
/* 436 */     Vec2 diff = Vec2.subtract(end, start);
/* 437 */     double lineLen = diff.length();
/* 438 */     double max = Math.max(lineLen, 0.013888888888888888D);
/* 439 */     Vec2 line = Vec2.multiply(diff, 1.0D / max);
/* 440 */     Vec2 line90 = line.getPerp();
/*     */     
/* 442 */     Vec2 temp = Vec2.multiply(line90, len * 0.5D);
/* 443 */     pt1.set(Vec2.subtract(end, temp).toPointF());
/* 444 */     pt2.set(Vec2.add(end, temp).toPointF());
/*     */   }
/*     */   
/*     */   public static PointF midpoint(@NonNull PointF pt1, @NonNull PointF pt2) {
/* 448 */     return new PointF((pt1.x + pt2.x) / 2.0F, (pt1.y + pt2.y) / 2.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawPolyline(@NonNull PDFViewCtrl pdfViewCtrl, int pageNum, @NonNull Canvas canvas, @NonNull ArrayList<PointF> canvasPoints, @NonNull Path path, @NonNull Paint paint, int strokeColor) {
/* 454 */     path.reset();
/* 455 */     PointF startPoint = null;
/* 456 */     for (PointF point : canvasPoints) {
/* 457 */       if (startPoint != null) {
/* 458 */         path.lineTo(point.x, point.y); continue;
/*     */       } 
/* 460 */       startPoint = point;
/* 461 */       path.moveTo(point.x, point.y);
/*     */     } 
/*     */     
/* 464 */     if (startPoint == null) {
/*     */       return;
/*     */     }
/*     */     
/* 468 */     if (strokeColor != 0) {
/* 469 */       if (pdfViewCtrl.isMaintainZoomEnabled()) {
/* 470 */         canvas.save();
/*     */         try {
/* 472 */           canvas.translate(0.0F, -pdfViewCtrl.getScrollYOffsetInTools(pageNum));
/* 473 */           canvas.drawPath(path, paint);
/*     */         } finally {
/* 475 */           canvas.restore();
/*     */         } 
/*     */       } else {
/* 478 */         canvas.drawPath(path, paint);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawPolygon(@NonNull PDFViewCtrl pdfViewCtrl, int pageNum, @NonNull Canvas canvas, @NonNull ArrayList<PointF> canvasPoints, @NonNull Path path, @NonNull Paint paint, int strokeColor, @NonNull Paint fillPaint, int fillColor) {
/* 487 */     drawPolygon(pdfViewCtrl, pageNum, canvas, canvasPoints, path, paint, strokeColor, fillPaint, fillColor, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawPolygon(@NonNull PDFViewCtrl pdfViewCtrl, int pageNum, @NonNull Canvas canvas, @NonNull ArrayList<PointF> canvasPoints, @NonNull Path path, @NonNull Paint paint, int strokeColor, @NonNull Paint fillPaint, int fillColor, @Nullable Matrix transform) {
/* 494 */     path.reset();
/* 495 */     PointF startPoint = null;
/* 496 */     for (PointF point : canvasPoints) {
/* 497 */       if (startPoint != null) {
/* 498 */         path.lineTo(point.x, point.y); continue;
/*     */       } 
/* 500 */       startPoint = point;
/* 501 */       path.moveTo(point.x, point.y);
/*     */     } 
/*     */     
/* 504 */     if (startPoint == null) {
/*     */       return;
/*     */     }
/* 507 */     path.lineTo(startPoint.x, startPoint.y);
/*     */     
/* 509 */     if (pdfViewCtrl.isMaintainZoomEnabled()) {
/* 510 */       canvas.save();
/*     */       try {
/* 512 */         canvas.translate(0.0F, -pdfViewCtrl.getScrollYOffsetInTools(pageNum));
/* 513 */         drawPolygonHelper(canvas, path, paint, strokeColor, fillPaint, fillColor, transform);
/*     */       } finally {
/* 515 */         canvas.restore();
/*     */       } 
/*     */     } else {
/* 518 */       drawPolygonHelper(canvas, path, paint, strokeColor, fillPaint, fillColor, transform);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawCloud(@NonNull PDFViewCtrl pdfViewCtrl, int pageNum, @NonNull Canvas canvas, @NonNull ArrayList<PointF> canvasPoints, @NonNull Path path, @NonNull Paint paint, int strokeColor, @NonNull Paint fillPaint, int fillColor, double borderIntensity) {
/* 526 */     ArrayList<PointF> poly = CloudCreate.getClosedPoly(canvasPoints);
/* 527 */     int size = poly.size();
/* 528 */     if (size < 3) {
/*     */       return;
/*     */     }
/*     */     
/* 532 */     double SAME_VERTEX_TH = 1.220703125E-4D;
/* 533 */     if (borderIntensity < 0.1D) {
/* 534 */       borderIntensity = 2.0D;
/*     */     }
/* 536 */     borderIntensity *= pdfViewCtrl.getZoom();
/* 537 */     boolean clockwise = CloudCreate.IsPolyWrapClockwise(poly);
/* 538 */     double sweepDirection = clockwise ? -1.0D : 1.0D;
/* 539 */     double maxCloudSize = 8.0D * borderIntensity;
/*     */     
/* 541 */     double lastCloudSize = maxCloudSize;
/* 542 */     double firstCloudSize = maxCloudSize;
/* 543 */     double edgeDegrees = 0.0D;
/* 544 */     PointF firstPos = poly.get(0);
/* 545 */     PointF lastEdge = CloudCreate.subtract(poly.get(0), poly.get(size - 2));
/* 546 */     boolean useLargeFirstArc = true;
/* 547 */     boolean hasFirstPoint = false;
/* 548 */     path.reset();
/* 549 */     float startX = 0.0F, startY = 0.0F;
/*     */     
/* 551 */     for (int i = 0; i < size - 1; i++) {
/* 552 */       PointF pos = poly.get(i);
/* 553 */       PointF edge = CloudCreate.subtract(poly.get(i + 1), pos);
/* 554 */       double length = edge.length();
/*     */       
/* 556 */       if (length > 1.220703125E-4D) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 561 */         PointF direction = CloudCreate.divide(edge, length);
/* 562 */         int numClouds = (int)Math.max(Math.floor(length / maxCloudSize), 1.0D);
/* 563 */         double cloudSize = length / numClouds;
/* 564 */         double edgeAngle = Math.atan2(direction.y, direction.x);
/*     */ 
/*     */ 
/*     */         
/* 568 */         pos = CloudCreate.subtract(pos, CloudCreate.multiply(direction, cloudSize * 0.5D));
/*     */ 
/*     */         
/* 571 */         double cross = CloudCreate.cross(lastEdge, edge);
/*     */         
/* 573 */         int c = 0;
/* 574 */         if (!hasFirstPoint) {
/*     */           
/* 576 */           c++;
/* 577 */           firstCloudSize = cloudSize;
/* 578 */           useLargeFirstArc = (cross * sweepDirection < 0.0D);
/* 579 */           pos = CloudCreate.add(pos, CloudCreate.multiply(direction, cloudSize));
/* 580 */           firstPos = pos;
/*     */           
/* 582 */           path.moveTo(firstPos.x, firstPos.y);
/* 583 */           startX = firstPos.x;
/* 584 */           startY = firstPos.y;
/* 585 */           hasFirstPoint = true;
/*     */         } 
/*     */         
/* 588 */         double radius = (lastCloudSize + cloudSize) * 0.25D;
/* 589 */         for (; c < numClouds; c++) {
/* 590 */           if (c == 1) {
/*     */             
/* 592 */             edgeDegrees = CloudCreate.toDegreesMod360(edgeAngle);
/* 593 */             radius = cloudSize * 0.5D;
/*     */           } 
/* 595 */           pos = CloudCreate.add(pos, CloudCreate.multiply(direction, cloudSize));
/* 596 */           boolean useLargeArc = (c == 0 && cross * sweepDirection < 0.0D);
/* 597 */           PointF point = CloudCreate.arcTo(path, startX, startY, radius, radius, edgeDegrees, useLargeArc, clockwise, pos.x, pos.y);
/* 598 */           startX = point.x;
/* 599 */           startY = point.y;
/*     */         } 
/* 601 */         edgeDegrees = CloudCreate.toDegreesMod360(edgeAngle);
/* 602 */         lastEdge = edge;
/* 603 */         lastCloudSize = cloudSize;
/*     */       } 
/* 605 */     }  if (!hasFirstPoint) {
/* 606 */       path.moveTo(firstPos.x, firstPos.y);
/* 607 */       startX = firstPos.x;
/* 608 */       startY = firstPos.y;
/*     */     } 
/* 610 */     double closingRadius = (firstCloudSize + lastCloudSize) * 0.25D;
/*     */     
/* 612 */     CloudCreate.arcTo(path, startX, startY, closingRadius, closingRadius, edgeDegrees, useLargeFirstArc, clockwise, firstPos.x, firstPos.y);
/*     */ 
/*     */     
/* 615 */     if (pdfViewCtrl.isMaintainZoomEnabled()) {
/* 616 */       canvas.save();
/*     */       try {
/* 618 */         canvas.translate(0.0F, -pdfViewCtrl.getScrollYOffsetInTools(pageNum));
/* 619 */         drawPolygonHelper(canvas, path, paint, strokeColor, fillPaint, fillColor, null);
/*     */       } finally {
/* 621 */         canvas.restore();
/*     */       } 
/*     */     } else {
/* 624 */       drawPolygonHelper(canvas, path, paint, strokeColor, fillPaint, fillColor, null);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void drawPolygonHelper(@NonNull Canvas canvas, @NonNull Path path, @NonNull Paint paint, int strokeColor, @NonNull Paint fillPaint, int fillColor, @Nullable Matrix transform) {
/* 631 */     Path drawPath = path;
/* 632 */     if (transform != null) {
/* 633 */       drawPath = new Path();
/* 634 */       drawPath.addPath(path, transform);
/*     */     } 
/* 636 */     if (fillColor != 0) {
/* 637 */       canvas.drawPath(drawPath, fillPaint);
/*     */     }
/* 639 */     if (strokeColor != 0)
/* 640 */       canvas.drawPath(drawPath, paint); 
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pd\\utils\DrawingUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */