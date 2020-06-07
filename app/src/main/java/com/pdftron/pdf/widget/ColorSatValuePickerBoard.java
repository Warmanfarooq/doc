/*     */ package com.pdftron.pdf.widget;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.content.res.TypedArray;
/*     */ import android.graphics.Bitmap;
/*     */ import android.graphics.Canvas;
/*     */ import android.graphics.Color;
/*     */ import android.graphics.ComposeShader;
/*     */ import android.graphics.LinearGradient;
/*     */ import android.graphics.Paint;
/*     */ import android.graphics.PorterDuff;
/*     */ import android.graphics.Shader;
/*     */ import android.os.Bundle;
/*     */ import android.os.Parcelable;
/*     */ import android.util.AttributeSet;
/*     */ import android.view.MotionEvent;
/*     */ import android.view.View;
/*     */ import androidx.annotation.ColorInt;
/*     */ import com.pdftron.pdf.controls.ColorPickerView;
/*     */ import com.pdftron.pdf.tools.R;
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
/*     */ public class ColorSatValuePickerBoard
/*     */   extends View
/*     */ {
/*  34 */   private static final String TAG = ColorSatValuePickerBoard.class.getName();
/*     */   
/*     */   private Paint mColorBoardPaint;
/*     */   
/*     */   private Paint mColorPointerPaint;
/*     */   
/*     */   private Bitmap mColorBoardBitmap;
/*     */   
/*     */   private int mColorPointerRadius;
/*     */   private OnHSVChangeListener mSatValueChangeListener;
/*  44 */   private float[] mColorHSV = new float[] { 0.0F, 1.0F, 1.0F };
/*     */   
/*     */   public ColorSatValuePickerBoard(Context context) {
/*  47 */     super(context);
/*  48 */     init((AttributeSet)null, R.attr.color_picker_style);
/*     */   }
/*     */   
/*     */   public ColorSatValuePickerBoard(Context context, AttributeSet attrs) {
/*  52 */     super(context, attrs);
/*  53 */     init(attrs, R.attr.color_picker_style);
/*     */   }
/*     */   
/*     */   public ColorSatValuePickerBoard(Context context, AttributeSet attrs, int defStyle) {
/*  57 */     super(context, attrs, defStyle);
/*  58 */     init(attrs, defStyle);
/*     */   }
/*     */   
/*     */   private void init(AttributeSet attrs, int defStyle) {
/*  62 */     this.mColorBoardPaint = new Paint(1);
/*  63 */     this.mColorBoardPaint.setDither(true);
/*     */     
/*  65 */     this.mColorPointerPaint = new Paint(1);
/*  66 */     this.mColorPointerPaint.setStyle(Paint.Style.STROKE);
/*  67 */     int strokeWidth = getContext().getResources().getDimensionPixelOffset(R.dimen.padding_xsmall);
/*  68 */     this.mColorPointerPaint.setStrokeWidth(strokeWidth);
/*     */     
/*  70 */     TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ColorSatValuePickerBoard, defStyle, R.style.ColorPickerStyle);
/*     */     
/*     */     try {
/*  73 */       this.mColorPointerRadius = a.getDimensionPixelOffset(R.styleable.ColorSatValuePickerBoard_value_pointer_radius, 2);
/*     */       
/*  75 */       int valueRingColor = a.getColor(R.styleable.ColorSatValuePickerBoard_ring_color, -1);
/*  76 */       this.mColorPointerPaint.setColor(valueRingColor);
/*     */       
/*  78 */       int valueRingShadow = a.getDimensionPixelOffset(R.styleable.ColorSatValuePickerBoard_ring_shadow_radius, 0);
/*  79 */       this.mColorPointerPaint.setShadowLayer(valueRingShadow, 5.0F, 5.0F, -16777216);
/*     */     } finally {
/*  81 */       a.recycle();
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
/*     */   protected void onSizeChanged(int width, int height, int oldw, int oldh) {
/*  94 */     this.mColorBoardBitmap = createColorWheelBitmap(width, height);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onDraw(Canvas canvas) {
/* 104 */     canvas.drawBitmap(this.mColorBoardBitmap, 0.0F, 0.0F, null);
/*     */ 
/*     */     
/* 107 */     float saturation = this.mColorHSV[1];
/* 108 */     float value = this.mColorHSV[2];
/*     */     
/* 110 */     float x = getWidth() * saturation;
/* 111 */     float y = getHeight() * (1.0F - value);
/*     */     
/* 113 */     canvas.drawCircle(x, y, this.mColorPointerRadius, this.mColorPointerPaint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Bitmap createColorWheelBitmap(int width, int height) {
/* 123 */     Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
/* 124 */     int hueOnlyColor = Color.HSVToColor(new float[] { this.mColorHSV[0], 1.0F, 1.0F });
/*     */     
/* 126 */     LinearGradient satGradient = new LinearGradient(0.0F, 0.0F, width, 0.0F, -1, hueOnlyColor, Shader.TileMode.CLAMP);
/* 127 */     LinearGradient valueGradient = new LinearGradient(0.0F, 0.0F, 0.0F, height, -1, -16777216, Shader.TileMode.CLAMP);
/* 128 */     ComposeShader composeShader = new ComposeShader((Shader)satGradient, (Shader)valueGradient, PorterDuff.Mode.MULTIPLY);
/* 129 */     this.mColorBoardPaint.setShader((Shader)composeShader);
/*     */     
/* 131 */     Canvas canvas = new Canvas(bitmap);
/* 132 */     canvas.drawRect(0.0F, 0.0F, width, height, this.mColorBoardPaint);
/* 133 */     return bitmap;
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
/*     */     float x, y;
/* 145 */     getParent().requestDisallowInterceptTouchEvent(true);
/* 146 */     int action = event.getAction();
/* 147 */     switch (action) {
/*     */       case 0:
/*     */       case 2:
/* 150 */         x = event.getX();
/* 151 */         y = event.getY();
/* 152 */         if (x >= 0.0F && x <= getWidth() && y >= 0.0F && y <= getHeight()) {
/* 153 */           this.mColorHSV[1] = x / getWidth();
/* 154 */           this.mColorHSV[2] = 1.0F - y / getHeight();
/* 155 */           invalidate();
/* 156 */           if (this.mSatValueChangeListener != null) {
/* 157 */             this.mSatValueChangeListener.onHSVChanged(this.mColorHSV);
/*     */           }
/*     */         } 
/* 160 */         return true;
/*     */       
/*     */       case 1:
/* 163 */         if (this.mSatValueChangeListener != null) {
/* 164 */           this.mSatValueChangeListener.OnColorChanged(this, 0);
/*     */         }
/* 166 */         return true;
/*     */     } 
/*     */     
/* 169 */     return super.onTouchEvent(event);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHue(float hue) {
/* 177 */     this.mColorHSV[0] = hue;
/* 178 */     if (getWidth() > 0 && getHeight() > 0) {
/* 179 */       this.mColorBoardBitmap = createColorWheelBitmap(getWidth(), getHeight());
/*     */     }
/* 181 */     invalidate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setColor(@ColorInt int color) {
/* 189 */     Color.colorToHSV(color, this.mColorHSV);
/* 190 */     if (getWidth() > 0 && getHeight() > 0) {
/* 191 */       this.mColorBoardBitmap = createColorWheelBitmap(getWidth(), getHeight());
/*     */     }
/* 193 */     invalidate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Parcelable onSaveInstanceState() {
/* 202 */     Bundle state = new Bundle();
/* 203 */     state.putFloatArray("color", this.mColorHSV);
/* 204 */     state.putParcelable("super", super.onSaveInstanceState());
/* 205 */     return (Parcelable)state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onRestoreInstanceState(Parcelable state) {
/* 214 */     if (state instanceof Bundle) {
/* 215 */       Bundle bundle = (Bundle)state;
/* 216 */       this.mColorHSV = bundle.getFloatArray("color");
/* 217 */       super.onRestoreInstanceState(bundle.getParcelable("super"));
/*     */     } else {
/* 219 */       super.onRestoreInstanceState(state);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOnSaturationValueChangelistener(OnHSVChangeListener listener) {
/* 228 */     this.mSatValueChangeListener = listener;
/*     */   }
/*     */   
/*     */   public static interface OnHSVChangeListener extends ColorPickerView.OnColorChangeListener {
/*     */     void onHSVChanged(float[] param1ArrayOffloat);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\widget\ColorSatValuePickerBoard.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */