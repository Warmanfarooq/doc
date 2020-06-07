/*     */ package com.rarepebble.colorpicker;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.graphics.Bitmap;
/*     */ import android.graphics.Canvas;
/*     */ import android.graphics.Color;
/*     */ import android.graphics.Paint;
/*     */ import android.graphics.Path;
/*     */ import android.graphics.PointF;
/*     */ import android.graphics.Rect;
/*     */ import android.graphics.RectF;
/*     */ import android.os.Build;
/*     */ import android.util.AttributeSet;
/*     */ import android.view.MotionEvent;
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
/*     */ 
/*     */ public class HueSatView
/*     */   extends SquareView
/*     */   implements ObservableColor.Observer
/*     */ {
/*     */   private final Paint borderPaint;
/*     */   private final Paint pointerPaint;
/*     */   private final Path pointerPath;
/*     */   private final Path borderPath;
/*  40 */   private final Rect viewRect = new Rect();
/*     */   
/*     */   private int w;
/*     */   private int h;
/*     */   private Bitmap bitmap;
/*  45 */   private int colorValueOverlayAlpha = 255;
/*     */   
/*     */   private Paint colorValueOverlayPaint;
/*  48 */   private final PointF pointer = new PointF();
/*  49 */   private ObservableColor observableColor = new ObservableColor(0);
/*     */   
/*     */   public HueSatView(Context context) {
/*  52 */     this(context, (AttributeSet)null);
/*     */   }
/*     */   
/*     */   public HueSatView(Context context, AttributeSet attrs) {
/*  56 */     super(context, attrs);
/*     */     
/*  58 */     this.borderPaint = Resources.makeLinePaint(context);
/*  59 */     this.pointerPaint = Resources.makeLinePaint(context);
/*  60 */     this.pointerPaint.setColor(-16777216);
/*  61 */     this.pointerPath = Resources.makePointerPath(context);
/*  62 */     this.borderPath = new Path();
/*  63 */     this.bitmap = makeBitmap(1);
/*  64 */     this.colorValueOverlayPaint = new Paint();
/*  65 */     this.colorValueOverlayPaint.setColor(getResources().getColor(17170444));
/*  66 */     this.colorValueOverlayPaint.setAlpha(this.colorValueOverlayAlpha);
/*     */   }
/*     */   
/*     */   public void observeColor(ObservableColor observableColor) {
/*  70 */     this.observableColor = observableColor;
/*  71 */     observableColor.addObserver(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateColor(ObservableColor observableColor) {
/*  76 */     observableColor.getValue();
/*  77 */     this.colorValueOverlayAlpha = (int)((1.0F - observableColor.getValue()) * 255.0F);
/*  78 */     this.colorValueOverlayPaint.setAlpha(this.colorValueOverlayAlpha);
/*  79 */     setPointer(this.pointer, observableColor.getHue(), observableColor.getSat(), this.w);
/*  80 */     optimisePointerColor();
/*  81 */     invalidate();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onSizeChanged(int w, int h, int oldw, int oldh) {
/*  86 */     this.w = w;
/*  87 */     this.h = h;
/*  88 */     this.viewRect.set(0, 0, w, h);
/*  89 */     float inset = this.borderPaint.getStrokeWidth() / 2.0F;
/*  90 */     makeBorderPath(this.borderPath, w, h, inset);
/*     */     
/*  92 */     int scale = 2;
/*  93 */     int bitmapRadius = Math.min(w, h) / 2;
/*  94 */     this.bitmap = makeBitmap(bitmapRadius);
/*     */ 
/*     */     
/*  97 */     updateColor(this.observableColor);
/*     */   }
/*     */   
/*     */   private static void makeBorderPath(Path borderPath, int w, int h, float inset) {
/* 101 */     w = (int)(w - inset);
/* 102 */     h = (int)(h - inset);
/* 103 */     borderPath.reset();
/* 104 */     borderPath.moveTo(w, inset);
/* 105 */     borderPath.lineTo(w, h);
/* 106 */     borderPath.lineTo(inset, h);
/* 107 */     borderPath.addArc(new RectF(inset, inset, (2 * w), (2 * h)), 180.0F, 270.0F);
/* 108 */     borderPath.close();
/*     */   }
/*     */   
/*     */   public boolean onTouchEvent(MotionEvent event) {
/*     */     boolean withinPicker;
/* 113 */     int action = event.getActionMasked();
/* 114 */     switch (action) {
/*     */       case 0:
/* 116 */         withinPicker = clamp(this.pointer, event.getX(), event.getY(), true);
/* 117 */         if (withinPicker) update(); 
/* 118 */         return withinPicker;
/*     */       case 2:
/* 120 */         clamp(this.pointer, event.getX(), event.getY(), false);
/* 121 */         update();
/* 122 */         getParent().requestDisallowInterceptTouchEvent(true);
/* 123 */         return true;
/*     */     } 
/* 125 */     return super.onTouchEvent(event);
/*     */   }
/*     */   
/*     */   private void update() {
/* 129 */     this.observableColor.updateHueSat(
/* 130 */         hueForPos(this.pointer.x, this.pointer.y, this.w), 
/* 131 */         satForPos(this.pointer.x, this.pointer.y, this.w), this);
/*     */     
/* 133 */     optimisePointerColor();
/* 134 */     invalidate();
/*     */   }
/*     */   
/*     */   private boolean clamp(PointF pointer, float x, float y, boolean rejectOutside) {
/* 138 */     x = Math.min(x, this.w);
/* 139 */     y = Math.min(y, this.h);
/* 140 */     float dx = this.w - x;
/* 141 */     float dy = this.h - y;
/* 142 */     float r = (float)Math.sqrt((dx * dx + dy * dy));
/* 143 */     boolean outside = (r > this.w);
/* 144 */     if (!outside || !rejectOutside) {
/* 145 */       if (outside) {
/* 146 */         x = this.w - dx * this.w / r;
/* 147 */         y = this.w - dy * this.w / r;
/*     */       } 
/* 149 */       pointer.set(x, y);
/*     */     } 
/* 151 */     return !outside;
/*     */   }
/*     */   
/*     */   private void optimisePointerColor() {
/* 155 */     this.pointerPaint.setColor(
/* 156 */         (this.observableColor.getLightnessWithValue(1.0F) > 0.5D) ? -16777216 : -1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onDraw(Canvas canvas) {
/* 162 */     super.onDraw(canvas);
/* 163 */     canvas.drawBitmap(this.bitmap, null, this.viewRect, null);
/* 164 */     canvas.drawPath(this.borderPath, this.colorValueOverlayPaint);
/* 165 */     canvas.drawPath(this.borderPath, this.borderPaint);
/*     */     
/* 167 */     canvas.save();
/* 168 */     if (Build.VERSION.SDK_INT >= 18) {
/* 169 */       canvas.clipPath(this.borderPath);
/*     */     }
/* 171 */     canvas.translate(this.pointer.x, this.pointer.y);
/* 172 */     canvas.drawPath(this.pointerPath, this.pointerPaint);
/* 173 */     canvas.restore();
/*     */   }
/*     */ 
/*     */   
/*     */   private static Bitmap makeBitmap(int radiusPx) {
/* 178 */     int[] colors = new int[radiusPx * radiusPx];
/* 179 */     float[] hsv = { 0.0F, 0.0F, 1.0F };
/* 180 */     for (int y = 0; y < radiusPx; y++) {
/* 181 */       for (int x = 0; x < radiusPx; x++) {
/* 182 */         int i = x + y * radiusPx;
/* 183 */         float sat = satForPos(x, y, radiusPx);
/* 184 */         int alpha = (int)(Math.max(0.0F, Math.min(1.0F, (1.0F - sat) * radiusPx)) * 255.0F);
/* 185 */         hsv[0] = hueForPos(x, y, radiusPx);
/* 186 */         hsv[1] = sat;
/* 187 */         colors[i] = Color.HSVToColor(alpha, hsv);
/*     */       } 
/*     */     } 
/* 190 */     return Bitmap.createBitmap(colors, radiusPx, radiusPx, Bitmap.Config.ARGB_8888);
/*     */   }
/*     */   
/*     */   private static float hueForPos(float x, float y, float radiusPx) {
/* 194 */     double r = (radiusPx - 1.0F);
/* 195 */     double dx = (r - x) / r;
/* 196 */     double dy = (r - y) / r;
/* 197 */     double angle = Math.atan2(dy, dx);
/* 198 */     double hue = 360.0D * angle / 1.5707963267948966D;
/* 199 */     return (float)hue;
/*     */   }
/*     */   
/*     */   private static float satForPos(float x, float y, float radiusPx) {
/* 203 */     double r = (radiusPx - 1.0F);
/* 204 */     double dx = (r - x) / r;
/* 205 */     double dy = (r - y) / r;
/* 206 */     double sat = dx * dx + dy * dy;
/* 207 */     return (float)sat;
/*     */   }
/*     */   
/*     */   private static void setPointer(PointF pointer, float hue, float sat, float radiusPx) {
/* 211 */     float r = radiusPx - 1.0F;
/* 212 */     double distance = r * Math.sqrt(sat);
/* 213 */     double angle = (hue / 360.0F) * Math.PI / 2.0D;
/* 214 */     double dx = distance * Math.cos(angle);
/* 215 */     double dy = distance * Math.sin(angle);
/* 216 */     pointer.set(r - (float)dx, r - (float)dy);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\rarepebble\colorpicker\HueSatView.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */