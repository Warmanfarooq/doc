/*     */ package com.pdftron.pdf.widget;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.content.res.TypedArray;
/*     */ import android.graphics.Bitmap;
/*     */ import android.graphics.Canvas;
/*     */ import android.graphics.Color;
/*     */ import android.graphics.Paint;
/*     */ import android.graphics.Shader;
/*     */ import android.graphics.SweepGradient;
/*     */ import android.os.Bundle;
/*     */ import android.os.Parcelable;
/*     */ import android.util.AttributeSet;
/*     */ import android.view.MotionEvent;
/*     */ import android.view.View;
/*     */ import androidx.annotation.ColorInt;
/*     */ import com.pdftron.pdf.controls.ColorPickerView;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ColorHuePickerWheel
/*     */   extends View
/*     */ {
/*  28 */   private static final String TAG = ColorHuePickerWheel.class.getName();
/*     */   
/*     */   private Paint mColorWheelPaint;
/*     */   
/*     */   private Paint mTransparentInnerCirclePaint;
/*     */   
/*     */   private Paint mColorPointerPaint;
/*     */   
/*     */   private Bitmap mColorWheelBitmap;
/*     */   
/*     */   private int mColorWheelRadius;
/*     */   
/*     */   private int mWheelWidth;
/*     */   
/*     */   private int mColorPointerRadius;
/*     */   
/*     */   private OnHueChangeListener mHueChangeListener;
/*  45 */   private float mColorHue = 0.0F;
/*     */   
/*     */   public ColorHuePickerWheel(Context context, AttributeSet attrs, int defStyle) {
/*  48 */     super(context, attrs, defStyle);
/*  49 */     init(attrs, defStyle);
/*     */   }
/*     */   
/*     */   public ColorHuePickerWheel(Context context, AttributeSet attrs) {
/*  53 */     super(context, attrs);
/*  54 */     init(attrs, R.attr.color_picker_style);
/*     */   }
/*     */   
/*     */   public ColorHuePickerWheel(Context context) {
/*  58 */     super(context);
/*  59 */     init((AttributeSet)null, R.attr.color_picker_style);
/*     */   }
/*     */ 
/*     */   
/*     */   private void init(AttributeSet attrs, int defStyle) {
/*  64 */     this.mColorPointerPaint = new Paint(1);
/*  65 */     this.mColorPointerPaint.setStyle(Paint.Style.STROKE);
/*  66 */     int strokeWidth = getContext().getResources().getDimensionPixelOffset(R.dimen.padding_xsmall);
/*  67 */     this.mColorPointerPaint.setStrokeWidth(strokeWidth);
/*     */     
/*  69 */     this.mColorWheelPaint = new Paint(1);
/*  70 */     this.mColorWheelPaint.setDither(true);
/*     */     
/*  72 */     this.mTransparentInnerCirclePaint = new Paint(1);
/*  73 */     this.mTransparentInnerCirclePaint.setColor(Utils.getThemeAttrColor(getContext(), 16842801));
/*     */     
/*  75 */     TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ColorHuePickerWheel, defStyle, R.style.ColorPickerStyle);
/*     */     try {
/*  77 */       this.mWheelWidth = a.getDimensionPixelOffset(R.styleable.ColorHuePickerWheel_wheel_width, -1);
/*  78 */       this.mColorPointerRadius = a.getDimensionPixelOffset(R.styleable.ColorHuePickerWheel_value_pointer_radius, 2);
/*     */       
/*  80 */       int valueRingColor = a.getColor(R.styleable.ColorHuePickerWheel_ring_color, -1);
/*  81 */       this.mColorPointerPaint.setColor(valueRingColor);
/*     */       
/*  83 */       int valueRingShadow = a.getDimensionPixelOffset(R.styleable.ColorHuePickerWheel_ring_shadow_radius, 0);
/*  84 */       this.mColorPointerPaint.setShadowLayer(valueRingShadow, 5.0F, 5.0F, -16777216);
/*     */     } finally {
/*  86 */       a.recycle();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
/*  97 */     int widthSize = MeasureSpec.getSize(widthMeasureSpec);
/*  98 */     int heightSize = MeasureSpec.getSize(heightMeasureSpec);
/*  99 */     int size = Math.min(widthSize, heightSize);
/* 100 */     setMeasuredDimension(size, size);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onDraw(Canvas canvas) {
/* 108 */     int centerX = getWidth() / 2;
/* 109 */     int centerY = getHeight() / 2;
/*     */ 
/*     */ 
/*     */     
/* 113 */     canvas.drawBitmap(this.mColorWheelBitmap, (centerX - this.mColorWheelRadius), (centerY - this.mColorWheelRadius), null);
/*     */ 
/*     */     
/* 116 */     float hueAngle = (float)Math.toRadians(this.mColorHue);
/* 117 */     int pointerPos = this.mColorWheelRadius - this.mWheelWidth / 2;
/* 118 */     int colorPointX = (int)(-Math.cos(hueAngle) * pointerPos) + centerX;
/* 119 */     int colorPointY = (int)(-Math.sin(hueAngle) * pointerPos) + centerY;
/* 120 */     canvas.drawCircle(colorPointX, colorPointY, this.mColorPointerRadius, this.mColorPointerPaint);
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
/*     */   protected void onSizeChanged(int width, int height, int oldw, int oldh) {
/* 132 */     this.mColorWheelRadius = width / 2 - getPaddingLeft();
/*     */     
/* 134 */     this.mColorWheelBitmap = createColorWheelBitmap(this.mColorWheelRadius * 2, this.mColorWheelRadius * 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Bitmap createColorWheelBitmap(int width, int height) {
/* 145 */     Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
/*     */     
/* 147 */     int colorCount = 12;
/* 148 */     int colorAngleStep = 30;
/* 149 */     int[] colors = new int[colorCount + 1];
/* 150 */     float[] hsv = { 0.0F, 1.0F, 1.0F };
/* 151 */     for (int i = 0; i < colors.length; i++) {
/* 152 */       hsv[0] = ((i * colorAngleStep + 180) % 360);
/* 153 */       colors[i] = Color.HSVToColor(hsv);
/*     */     } 
/* 155 */     colors[colorCount] = colors[0];
/*     */     
/* 157 */     SweepGradient sweepGradient = new SweepGradient((width / 2), (height / 2), colors, null);
/*     */     
/* 159 */     this.mColorWheelPaint.setShader((Shader)sweepGradient);
/*     */     
/* 161 */     Canvas canvas = new Canvas(bitmap);
/* 162 */     canvas.drawCircle((width / 2), (height / 2), this.mColorWheelRadius, this.mColorWheelPaint);
/* 163 */     if (this.mWheelWidth >= 0) {
/* 164 */       canvas.drawCircle((width / 2), (height / 2), (this.mColorWheelRadius - this.mWheelWidth), this.mTransparentInnerCirclePaint);
/*     */     }
/* 166 */     return bitmap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onTouchEvent(MotionEvent event) {
/*     */     int x, y, cx, cy;
/*     */     double d;
/* 179 */     getParent().requestDisallowInterceptTouchEvent(true);
/* 180 */     int action = event.getAction();
/* 181 */     switch (action) {
/*     */       case 0:
/*     */       case 2:
/* 184 */         x = (int)event.getX();
/* 185 */         y = (int)event.getY();
/* 186 */         cx = x - getWidth() / 2;
/* 187 */         cy = y - getHeight() / 2;
/* 188 */         d = Math.sqrt((cx * cx + cy * cy));
/* 189 */         if (d <= this.mColorWheelRadius && d > (this.mColorWheelRadius - this.mWheelWidth)) {
/* 190 */           this.mColorHue = (float)(Math.toDegrees(Math.atan2(cy, cx)) + 180.0D);
/* 191 */           invalidate();
/* 192 */           if (this.mHueChangeListener != null) {
/* 193 */             this.mHueChangeListener.onHueChanged(this.mColorHue);
/*     */           }
/*     */         } 
/* 196 */         return true;
/*     */       
/*     */       case 1:
/* 199 */         if (this.mHueChangeListener != null) {
/* 200 */           this.mHueChangeListener.OnColorChanged(this, 0);
/*     */         }
/* 202 */         return true;
/*     */     } 
/*     */     
/* 205 */     return super.onTouchEvent(event);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setColor(@ColorInt int color) {
/* 213 */     float[] hsv = new float[3];
/* 214 */     Color.colorToHSV(color, hsv);
/* 215 */     this.mColorHue = hsv[0];
/* 216 */     invalidate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Parcelable onSaveInstanceState() {
/* 225 */     Bundle state = new Bundle();
/* 226 */     state.putFloat("hue", this.mColorHue);
/* 227 */     state.putParcelable("super", super.onSaveInstanceState());
/* 228 */     return (Parcelable)state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onRestoreInstanceState(Parcelable state) {
/* 238 */     if (state instanceof Bundle) {
/* 239 */       Bundle bundle = (Bundle)state;
/* 240 */       this.mColorHue = bundle.getFloat("hue");
/* 241 */       super.onRestoreInstanceState(bundle.getParcelable("super"));
/*     */     } else {
/* 243 */       super.onRestoreInstanceState(state);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOnHueChangeListener(OnHueChangeListener listener) {
/* 252 */     this.mHueChangeListener = listener;
/*     */   }
/*     */   
/*     */   public static interface OnHueChangeListener extends ColorPickerView.OnColorChangeListener {
/*     */     void onHueChanged(float param1Float);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\widget\ColorHuePickerWheel.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */