/*      */ package com.pdftron.pdf.tools;
/*      */ 
/*      */ import android.content.Context;
/*      */ import android.graphics.Canvas;
/*      */ import android.graphics.Color;
/*      */ import android.graphics.ComposeShader;
/*      */ import android.graphics.LinearGradient;
/*      */ import android.graphics.Paint;
/*      */ import android.graphics.Point;
/*      */ import android.graphics.PorterDuff;
/*      */ import android.graphics.RectF;
/*      */ import android.graphics.Shader;
/*      */ import android.util.AttributeSet;
/*      */ import android.view.MotionEvent;
/*      */ import android.view.View;
/*      */ import com.pdftron.pdf.utils.Utils;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class ColorPickerView
/*      */   extends View
/*      */ {
/*      */   private static final int PANEL_SAT_VAL = 0;
/*      */   private static final int PANEL_HUE = 1;
/*      */   private static final int PANEL_ALPHA = 2;
/*      */   private static final float BORDER_WIDTH_PX = 1.0F;
/*  398 */   private float HUE_PANEL_WIDTH = 30.0F;
/*      */ 
/*      */ 
/*      */   
/*  402 */   private float ALPHA_PANEL_HEIGHT = 20.0F;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  407 */   private float PANEL_SPACING = 10.0F;
/*      */ 
/*      */ 
/*      */   
/*  411 */   private float PALETTE_CIRCLE_TRACKER_RADIUS = 5.0F;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  416 */   private float RECTANGLE_TRACKER_OFFSET = 2.0F;
/*      */ 
/*      */   
/*  419 */   private static float mDensity = 1.0F;
/*      */   
/*  421 */   private final float[] mOnDrawHsv = new float[3];
/*  422 */   private final RectF mOnDrawRect = new RectF();
/*  423 */   private final Point mOnDrawPoint = new Point();
/*      */   
/*      */   private OnColorChangedListener mListener;
/*      */   
/*      */   private Paint mSatValPaint;
/*      */   
/*      */   private Paint mSatValTrackerPaint;
/*      */   
/*      */   private Paint mHuePaint;
/*      */   
/*      */   private Paint mHueTrackerPaint;
/*      */   
/*      */   private Paint mAlphaPaint;
/*      */   private Paint mAlphaTextPaint;
/*      */   private Paint mBorderPaint;
/*      */   private Shader mValShader;
/*      */   private Shader mSatShader;
/*      */   private Shader mHueShader;
/*      */   private Shader mAlphaShader;
/*  442 */   private int mAlpha = 255;
/*  443 */   private float mHue = 360.0F;
/*  444 */   private float mSat = 0.0F;
/*  445 */   private float mVal = 0.0F;
/*      */   
/*  447 */   private String mAlphaSliderText = "Alpha";
/*  448 */   private int mSliderTrackerColor = -14935012;
/*  449 */   private int mBorderColor = -9539986;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean mShowAlphaPanel = false;
/*      */ 
/*      */   
/*  456 */   private int mLastTouchedPanel = 0;
/*      */ 
/*      */   
/*      */   private float mDrawingOffset;
/*      */ 
/*      */   
/*      */   private RectF mDrawingRect;
/*      */ 
/*      */   
/*      */   private RectF mSatValRect;
/*      */ 
/*      */   
/*      */   private RectF mHueRect;
/*      */ 
/*      */   
/*      */   private RectF mAlphaRect;
/*      */ 
/*      */   
/*      */   private AlphaPatternDrawable mAlphaPattern;
/*      */ 
/*      */   
/*  477 */   private Point mStartTouchPoint = null;
/*      */   
/*      */   ColorPickerView(Context context) {
/*  480 */     this(context, (AttributeSet)null);
/*      */   }
/*      */   
/*      */   ColorPickerView(Context context, AttributeSet attrs) {
/*  484 */     this(context, attrs, 0);
/*      */   }
/*      */   
/*      */   ColorPickerView(Context context, AttributeSet attrs, int defStyle) {
/*  488 */     super(context, attrs, defStyle);
/*  489 */     init();
/*      */   }
/*      */   
/*      */   private void init() {
/*  493 */     mDensity = (getContext().getResources().getDisplayMetrics()).density;
/*  494 */     this.PALETTE_CIRCLE_TRACKER_RADIUS *= mDensity;
/*  495 */     this.RECTANGLE_TRACKER_OFFSET *= mDensity;
/*  496 */     this.HUE_PANEL_WIDTH *= mDensity;
/*  497 */     this.ALPHA_PANEL_HEIGHT *= mDensity;
/*  498 */     this.PANEL_SPACING *= mDensity;
/*      */     
/*  500 */     this.mDrawingOffset = calculateRequiredOffset();
/*      */     
/*  502 */     initPaintTools();
/*      */ 
/*      */     
/*  505 */     setFocusable(true);
/*  506 */     setFocusableInTouchMode(true);
/*      */   }
/*      */   
/*      */   private void initPaintTools() {
/*  510 */     this.mSatValPaint = new Paint();
/*  511 */     this.mSatValTrackerPaint = new Paint();
/*  512 */     this.mHuePaint = new Paint();
/*  513 */     this.mHueTrackerPaint = new Paint();
/*  514 */     this.mAlphaPaint = new Paint();
/*  515 */     this.mAlphaTextPaint = new Paint();
/*  516 */     this.mBorderPaint = new Paint();
/*      */ 
/*      */     
/*  519 */     this.mSatValTrackerPaint.setStyle(Paint.Style.STROKE);
/*  520 */     this.mSatValTrackerPaint.setStrokeWidth(2.0F * mDensity);
/*  521 */     this.mSatValTrackerPaint.setAntiAlias(true);
/*      */     
/*  523 */     this.mHueTrackerPaint.setColor(this.mSliderTrackerColor);
/*  524 */     this.mHueTrackerPaint.setStyle(Paint.Style.STROKE);
/*  525 */     this.mHueTrackerPaint.setStrokeWidth(2.0F * mDensity);
/*  526 */     this.mHueTrackerPaint.setAntiAlias(true);
/*      */     
/*  528 */     this.mAlphaTextPaint.setColor(-14935012);
/*  529 */     this.mAlphaTextPaint.setTextSize(14.0F * mDensity);
/*  530 */     this.mAlphaTextPaint.setAntiAlias(true);
/*  531 */     this.mAlphaTextPaint.setTextAlign(Paint.Align.CENTER);
/*  532 */     this.mAlphaTextPaint.setFakeBoldText(true);
/*      */   }
/*      */   
/*      */   private float calculateRequiredOffset() {
/*  536 */     float offset = Math.max(this.PALETTE_CIRCLE_TRACKER_RADIUS, this.RECTANGLE_TRACKER_OFFSET);
/*  537 */     offset = Math.max(offset, 1.0F * mDensity);
/*      */     
/*  539 */     return offset * 1.5F;
/*      */   }
/*      */   
/*      */   private int[] buildHueColorArray() {
/*  543 */     int[] hue = new int[361];
/*      */     
/*  545 */     int count = 0;
/*  546 */     for (int i = hue.length - 1; i >= 0; i--, count++) {
/*  547 */       hue[count] = Color.HSVToColor(new float[] { i, 1.0F, 1.0F });
/*      */     } 
/*      */     
/*  550 */     return hue;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onDraw(Canvas canvas) {
/*  555 */     if (this.mDrawingRect.width() <= 0.0F || this.mDrawingRect.height() <= 0.0F)
/*      */       return; 
/*  557 */     drawSatValPanel(canvas);
/*  558 */     drawHuePanel(canvas);
/*  559 */     drawAlphaPanel(canvas);
/*      */   }
/*      */ 
/*      */   
/*      */   private void drawSatValPanel(Canvas canvas) {
/*  564 */     RectF rect = this.mSatValRect;
/*      */ 
/*      */     
/*  567 */     this.mBorderPaint.setColor(this.mBorderColor);
/*  568 */     canvas.drawRect(this.mDrawingRect.left, this.mDrawingRect.top, rect.right + 1.0F, rect.bottom + 1.0F, this.mBorderPaint);
/*      */ 
/*      */     
/*  571 */     if (this.mValShader == null) {
/*  572 */       this.mValShader = (Shader)new LinearGradient(rect.left, rect.top, rect.left, rect.bottom, -1, -16777216, Shader.TileMode.CLAMP);
/*      */     }
/*      */ 
/*      */     
/*  576 */     int rgb = Color.HSVToColor(new float[] { this.mHue, 1.0F, 1.0F });
/*      */     
/*  578 */     this.mSatShader = (Shader)new LinearGradient(rect.left, rect.top, rect.right, rect.top, -1, rgb, Shader.TileMode.CLAMP);
/*      */     
/*  580 */     ComposeShader mShader = new ComposeShader(this.mValShader, this.mSatShader, PorterDuff.Mode.MULTIPLY);
/*  581 */     this.mSatValPaint.setShader((Shader)mShader);
/*      */     
/*  583 */     canvas.drawRect(rect, this.mSatValPaint);
/*      */     
/*  585 */     Point p = satValToPoint(this.mSat, this.mVal);
/*      */     
/*  587 */     this.mSatValTrackerPaint.setColor(-16777216);
/*  588 */     canvas.drawCircle(p.x, p.y, this.PALETTE_CIRCLE_TRACKER_RADIUS - 1.0F * mDensity, this.mSatValTrackerPaint);
/*      */     
/*  590 */     this.mSatValTrackerPaint.setColor(-2236963);
/*  591 */     canvas.drawCircle(p.x, p.y, this.PALETTE_CIRCLE_TRACKER_RADIUS, this.mSatValTrackerPaint);
/*      */   }
/*      */   
/*      */   private void drawHuePanel(Canvas canvas) {
/*  595 */     RectF rect = this.mHueRect;
/*      */ 
/*      */     
/*  598 */     this.mBorderPaint.setColor(this.mBorderColor);
/*  599 */     canvas.drawRect(rect.left - 1.0F, rect.top - 1.0F, rect.right + 1.0F, rect.bottom + 1.0F, this.mBorderPaint);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  606 */     if (this.mHueShader == null) {
/*  607 */       this.mHueShader = (Shader)new LinearGradient(rect.left, rect.top, rect.left, rect.bottom, buildHueColorArray(), null, Shader.TileMode.CLAMP);
/*  608 */       this.mHuePaint.setShader(this.mHueShader);
/*      */     } 
/*      */     
/*  611 */     canvas.drawRect(rect, this.mHuePaint);
/*      */     
/*  613 */     float rectHeight = 4.0F * mDensity / 2.0F;
/*      */     
/*  615 */     Point p = hueToPoint(this.mHue);
/*      */     
/*  617 */     this.mOnDrawRect.set(rect.left - this.RECTANGLE_TRACKER_OFFSET, p.y - rectHeight, rect.right + this.RECTANGLE_TRACKER_OFFSET, p.y + rectHeight);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  623 */     canvas.drawRoundRect(this.mOnDrawRect, 2.0F, 2.0F, this.mHueTrackerPaint);
/*      */   }
/*      */   
/*      */   private void drawAlphaPanel(Canvas canvas) {
/*  627 */     if (!this.mShowAlphaPanel || this.mAlphaRect == null || this.mAlphaPattern == null)
/*      */       return; 
/*  629 */     RectF rect = this.mAlphaRect;
/*      */ 
/*      */     
/*  632 */     this.mBorderPaint.setColor(this.mBorderColor);
/*  633 */     canvas.drawRect(rect.left - 1.0F, rect.top - 1.0F, rect.right + 1.0F, rect.bottom + 1.0F, this.mBorderPaint);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  640 */     this.mAlphaPattern.draw(canvas);
/*      */     
/*  642 */     this.mOnDrawHsv[0] = this.mHue;
/*  643 */     this.mOnDrawHsv[1] = this.mSat;
/*  644 */     this.mOnDrawHsv[2] = this.mVal;
/*  645 */     int color = Color.HSVToColor(this.mOnDrawHsv);
/*  646 */     int acolor = Color.HSVToColor(0, this.mOnDrawHsv);
/*      */     
/*  648 */     this.mAlphaShader = (Shader)new LinearGradient(rect.left, rect.top, rect.right, rect.top, color, acolor, Shader.TileMode.CLAMP);
/*      */ 
/*      */     
/*  651 */     this.mAlphaPaint.setShader(this.mAlphaShader);
/*      */     
/*  653 */     canvas.drawRect(rect, this.mAlphaPaint);
/*      */     
/*  655 */     if (!Utils.isNullOrEmpty(this.mAlphaSliderText)) {
/*  656 */       canvas.drawText(this.mAlphaSliderText, rect.centerX(), rect.centerY() + 4.0F * mDensity, this.mAlphaTextPaint);
/*      */     }
/*      */     
/*  659 */     float rectWidth = 4.0F * mDensity / 2.0F;
/*      */     
/*  661 */     Point p = alphaToPoint(this.mAlpha);
/*      */     
/*  663 */     this.mOnDrawRect.set(p.x - rectWidth, rect.top - this.RECTANGLE_TRACKER_OFFSET, p.x + rectWidth, rect.bottom + this.RECTANGLE_TRACKER_OFFSET);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  669 */     canvas.drawRoundRect(this.mOnDrawRect, 2.0F, 2.0F, this.mHueTrackerPaint);
/*      */   }
/*      */   
/*      */   private Point hueToPoint(float hue) {
/*  673 */     RectF rect = this.mHueRect;
/*  674 */     float height = rect.height();
/*      */     
/*  676 */     this.mOnDrawPoint.y = (int)(height - hue * height / 360.0F + rect.top);
/*  677 */     this.mOnDrawPoint.x = (int)rect.left;
/*      */     
/*  679 */     return this.mOnDrawPoint;
/*      */   }
/*      */   
/*      */   private Point satValToPoint(float sat, float val) {
/*  683 */     RectF rect = this.mSatValRect;
/*  684 */     float height = rect.height();
/*  685 */     float width = rect.width();
/*      */     
/*  687 */     this.mOnDrawPoint.x = (int)(sat * width + rect.left);
/*  688 */     this.mOnDrawPoint.y = (int)((1.0F - val) * height + rect.top);
/*      */     
/*  690 */     return this.mOnDrawPoint;
/*      */   }
/*      */   
/*      */   private Point alphaToPoint(int alpha) {
/*  694 */     RectF rect = this.mAlphaRect;
/*  695 */     float width = rect.width();
/*      */     
/*  697 */     this.mOnDrawPoint.x = (int)(width - alpha * width / 255.0F + rect.left);
/*  698 */     this.mOnDrawPoint.y = (int)rect.top;
/*      */     
/*  700 */     return this.mOnDrawPoint;
/*      */   }
/*      */   
/*      */   private float[] pointToSatVal(float x, float y) {
/*  704 */     RectF rect = this.mSatValRect;
/*  705 */     float[] result = new float[2];
/*      */     
/*  707 */     float width = rect.width();
/*  708 */     float height = rect.height();
/*      */     
/*  710 */     if (x < rect.left) {
/*  711 */       x = 0.0F;
/*  712 */     } else if (x > rect.right) {
/*  713 */       x = width;
/*      */     } else {
/*  715 */       x -= rect.left;
/*      */     } 
/*      */     
/*  718 */     if (y < rect.top) {
/*  719 */       y = 0.0F;
/*  720 */     } else if (y > rect.bottom) {
/*  721 */       y = height;
/*      */     } else {
/*  723 */       y -= rect.top;
/*      */     } 
/*      */     
/*  726 */     result[0] = 1.0F / width * x;
/*  727 */     result[1] = 1.0F - 1.0F / height * y;
/*      */     
/*  729 */     return result;
/*      */   }
/*      */   
/*      */   private float pointToHue(float y) {
/*  733 */     RectF rect = this.mHueRect;
/*      */     
/*  735 */     float height = rect.height();
/*      */     
/*  737 */     if (y < rect.top) {
/*  738 */       y = 0.0F;
/*  739 */     } else if (y > rect.bottom) {
/*  740 */       y = height;
/*      */     } else {
/*  742 */       y -= rect.top;
/*      */     } 
/*      */     
/*  745 */     return 360.0F - y * 360.0F / height;
/*      */   }
/*      */   
/*      */   private int pointToAlpha(int x) {
/*  749 */     RectF rect = this.mAlphaRect;
/*  750 */     int width = (int)rect.width();
/*      */     
/*  752 */     if (x < rect.left) {
/*  753 */       x = 0;
/*  754 */     } else if (x > rect.right) {
/*  755 */       x = width;
/*      */     } else {
/*  757 */       x -= (int)rect.left;
/*      */     } 
/*      */     
/*  760 */     return 255 - x * 255 / width;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean onTrackballEvent(MotionEvent event) {
/*  765 */     float x = event.getX();
/*  766 */     float y = event.getY();
/*      */     
/*  768 */     boolean update = false;
/*      */     
/*  770 */     if (event.getAction() == 2) {
/*      */       float sat; float val; float hue; int alpha;
/*  772 */       switch (this.mLastTouchedPanel) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case 0:
/*  778 */           sat = this.mSat + x / 50.0F;
/*  779 */           val = this.mVal - y / 50.0F;
/*      */           
/*  781 */           if (sat < 0.0F) {
/*  782 */             sat = 0.0F;
/*  783 */           } else if (sat > 1.0F) {
/*  784 */             sat = 1.0F;
/*      */           } 
/*      */           
/*  787 */           if (val < 0.0F) {
/*  788 */             val = 0.0F;
/*  789 */           } else if (val > 1.0F) {
/*  790 */             val = 1.0F;
/*      */           } 
/*      */           
/*  793 */           this.mSat = sat;
/*  794 */           this.mVal = val;
/*      */           
/*  796 */           update = true;
/*      */           break;
/*      */ 
/*      */ 
/*      */         
/*      */         case 1:
/*  802 */           hue = this.mHue - y * 10.0F;
/*      */           
/*  804 */           if (hue < 0.0F) {
/*  805 */             hue = 0.0F;
/*  806 */           } else if (hue > 360.0F) {
/*  807 */             hue = 360.0F;
/*      */           } 
/*      */           
/*  810 */           this.mHue = hue;
/*      */           
/*  812 */           update = true;
/*      */           break;
/*      */ 
/*      */ 
/*      */         
/*      */         case 2:
/*  818 */           if (!this.mShowAlphaPanel || this.mAlphaRect == null) {
/*  819 */             update = false;
/*      */             break;
/*      */           } 
/*  822 */           alpha = (int)(this.mAlpha - x * 10.0F);
/*      */           
/*  824 */           if (alpha < 0) {
/*  825 */             alpha = 0;
/*  826 */           } else if (alpha > 255) {
/*  827 */             alpha = 255;
/*      */           } 
/*      */           
/*  830 */           this.mAlpha = alpha;
/*      */ 
/*      */           
/*  833 */           update = true;
/*      */           break;
/*      */       } 
/*      */ 
/*      */     
/*      */     } 
/*  839 */     if (update) {
/*      */       
/*  841 */       if (this.mListener != null) {
/*  842 */         this.mListener.onColorChanged(Color.HSVToColor(this.mAlpha, new float[] { this.mHue, this.mSat, this.mVal }));
/*      */       }
/*      */       
/*  845 */       invalidate();
/*  846 */       return true;
/*      */     } 
/*      */     
/*  849 */     return super.onTrackballEvent(event);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onTouchEvent(MotionEvent event) {
/*  855 */     boolean update = false;
/*      */     
/*  857 */     switch (event.getAction()) {
/*      */ 
/*      */       
/*      */       case 0:
/*  861 */         this.mStartTouchPoint = new Point((int)event.getX(), (int)event.getY());
/*      */         
/*  863 */         update = moveTrackersIfNeeded(event);
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 2:
/*  869 */         update = moveTrackersIfNeeded(event);
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 1:
/*  875 */         this.mStartTouchPoint = null;
/*      */         
/*  877 */         update = moveTrackersIfNeeded(event);
/*      */         break;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  883 */     if (update) {
/*      */       
/*  885 */       if (this.mListener != null) {
/*  886 */         this.mListener.onColorChanged(Color.HSVToColor(this.mAlpha, new float[] { this.mHue, this.mSat, this.mVal }));
/*      */       }
/*      */       
/*  889 */       invalidate();
/*  890 */       return true;
/*      */     } 
/*      */     
/*  893 */     return super.onTouchEvent(event);
/*      */   }
/*      */   
/*      */   private boolean moveTrackersIfNeeded(MotionEvent event) {
/*  897 */     if (this.mStartTouchPoint == null) return false;
/*      */     
/*  899 */     boolean update = false;
/*      */     
/*  901 */     int startX = this.mStartTouchPoint.x;
/*  902 */     int startY = this.mStartTouchPoint.y;
/*      */ 
/*      */     
/*  905 */     if (this.mHueRect.contains(startX, startY)) {
/*  906 */       this.mLastTouchedPanel = 1;
/*      */       
/*  908 */       this.mHue = pointToHue(event.getY());
/*      */       
/*  910 */       update = true;
/*  911 */     } else if (this.mSatValRect.contains(startX, startY)) {
/*      */       
/*  913 */       this.mLastTouchedPanel = 0;
/*      */       
/*  915 */       float[] result = pointToSatVal(event.getX(), event.getY());
/*      */       
/*  917 */       this.mSat = result[0];
/*  918 */       this.mVal = result[1];
/*      */       
/*  920 */       update = true;
/*  921 */     } else if (this.mAlphaRect != null && this.mAlphaRect.contains(startX, startY)) {
/*      */       
/*  923 */       this.mLastTouchedPanel = 2;
/*      */       
/*  925 */       this.mAlpha = pointToAlpha((int)event.getX());
/*      */       
/*  927 */       update = true;
/*      */     } 
/*      */     
/*  930 */     return update;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
/*  935 */     int width = 0;
/*  936 */     int height = 0;
/*      */     
/*  938 */     int widthMode = MeasureSpec.getMode(widthMeasureSpec);
/*  939 */     int heightMode = MeasureSpec.getMode(heightMeasureSpec);
/*      */     
/*  941 */     int widthAllowed = MeasureSpec.getSize(widthMeasureSpec);
/*  942 */     int heightAllowed = MeasureSpec.getSize(heightMeasureSpec);
/*      */ 
/*      */     
/*  945 */     widthAllowed = chooseWidth(widthMode, widthAllowed);
/*  946 */     heightAllowed = chooseHeight(heightMode, heightAllowed);
/*      */ 
/*      */     
/*  949 */     if (!this.mShowAlphaPanel) {
/*  950 */       height = (int)(widthAllowed - this.PANEL_SPACING - this.HUE_PANEL_WIDTH);
/*      */ 
/*      */       
/*  953 */       if (height > heightAllowed) {
/*  954 */         height = heightAllowed;
/*  955 */         width = (int)(height + this.PANEL_SPACING + this.HUE_PANEL_WIDTH);
/*      */       } else {
/*  957 */         width = widthAllowed;
/*      */       } 
/*      */     } else {
/*      */       
/*  961 */       width = (int)(heightAllowed - this.ALPHA_PANEL_HEIGHT + this.HUE_PANEL_WIDTH);
/*      */       
/*  963 */       if (width > widthAllowed) {
/*  964 */         width = widthAllowed;
/*  965 */         height = (int)(widthAllowed - this.HUE_PANEL_WIDTH + this.ALPHA_PANEL_HEIGHT);
/*      */       } else {
/*  967 */         height = heightAllowed;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  973 */     setMeasuredDimension(width, height);
/*      */   }
/*      */   
/*      */   private int chooseWidth(int mode, int size) {
/*  977 */     if (mode == Integer.MIN_VALUE || mode == 1073741824) {
/*  978 */       return size;
/*      */     }
/*  980 */     return getPrefferedWidth();
/*      */   }
/*      */ 
/*      */   
/*      */   private int chooseHeight(int mode, int size) {
/*  985 */     if (mode == Integer.MIN_VALUE || mode == 1073741824) {
/*  986 */       return size;
/*      */     }
/*  988 */     return getPrefferedHeight();
/*      */   }
/*      */ 
/*      */   
/*      */   private int getPrefferedWidth() {
/*  993 */     int width = getPrefferedHeight();
/*      */     
/*  995 */     if (this.mShowAlphaPanel) {
/*  996 */       width = (int)(width - this.PANEL_SPACING + this.ALPHA_PANEL_HEIGHT);
/*      */     }
/*      */     
/*  999 */     return (int)(width + this.HUE_PANEL_WIDTH + this.PANEL_SPACING);
/*      */   }
/*      */   
/*      */   private int getPrefferedHeight() {
/* 1003 */     int height = (int)(200.0F * mDensity);
/*      */     
/* 1005 */     if (this.mShowAlphaPanel) {
/* 1006 */       height = (int)(height + this.PANEL_SPACING + this.ALPHA_PANEL_HEIGHT);
/*      */     }
/*      */     
/* 1009 */     return height;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onSizeChanged(int w, int h, int oldw, int oldh) {
/* 1014 */     super.onSizeChanged(w, h, oldw, oldh);
/*      */     
/* 1016 */     this.mDrawingRect = new RectF();
/* 1017 */     this.mDrawingRect.left = this.mDrawingOffset + getPaddingLeft();
/* 1018 */     this.mDrawingRect.right = w - this.mDrawingOffset - getPaddingRight();
/* 1019 */     this.mDrawingRect.top = this.mDrawingOffset + getPaddingTop();
/* 1020 */     this.mDrawingRect.bottom = h - this.mDrawingOffset - getPaddingBottom();
/*      */     
/* 1022 */     setUpSatValRect();
/* 1023 */     setUpHueRect();
/* 1024 */     setUpAlphaRect();
/*      */   }
/*      */ 
/*      */   
/*      */   private void setUpSatValRect() {
/* 1029 */     RectF dRect = this.mDrawingRect;
/* 1030 */     float panelSide = dRect.height() - 2.0F;
/*      */     
/* 1032 */     if (this.mShowAlphaPanel) {
/* 1033 */       panelSide -= this.PANEL_SPACING + this.ALPHA_PANEL_HEIGHT;
/*      */     }
/*      */     
/* 1036 */     float left = dRect.left + 1.0F;
/* 1037 */     float top = dRect.top + 1.0F;
/* 1038 */     float bottom = top + panelSide;
/* 1039 */     float right = left + panelSide;
/*      */     
/* 1041 */     this.mSatValRect = new RectF(left, top, right, bottom);
/*      */   }
/*      */   
/*      */   private void setUpHueRect() {
/* 1045 */     RectF dRect = this.mDrawingRect;
/*      */     
/* 1047 */     float left = dRect.right - this.HUE_PANEL_WIDTH + 1.0F;
/* 1048 */     float top = dRect.top + 1.0F;
/* 1049 */     float bottom = dRect.bottom - 1.0F - (this.mShowAlphaPanel ? (this.PANEL_SPACING + this.ALPHA_PANEL_HEIGHT) : 0.0F);
/* 1050 */     float right = dRect.right - 1.0F;
/*      */     
/* 1052 */     this.mHueRect = new RectF(left, top, right, bottom);
/*      */   }
/*      */   
/*      */   private void setUpAlphaRect() {
/* 1056 */     if (!this.mShowAlphaPanel)
/*      */       return; 
/* 1058 */     RectF dRect = this.mDrawingRect;
/*      */     
/* 1060 */     float left = dRect.left + 1.0F;
/* 1061 */     float top = dRect.bottom - this.ALPHA_PANEL_HEIGHT + 1.0F;
/* 1062 */     float bottom = dRect.bottom - 1.0F;
/* 1063 */     float right = dRect.right - 1.0F;
/*      */     
/* 1065 */     this.mAlphaRect = new RectF(left, top, right, bottom);
/*      */ 
/*      */     
/* 1068 */     this.mAlphaPattern = new AlphaPatternDrawable((int)(5.0F * mDensity));
/* 1069 */     this.mAlphaPattern.setBounds(Math.round(this.mAlphaRect.left), 
/* 1070 */         Math.round(this.mAlphaRect.top), Math.round(this.mAlphaRect.right), 
/* 1071 */         Math.round(this.mAlphaRect.bottom));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void setOnColorChangedListener(OnColorChangedListener listener) {
/* 1081 */     this.mListener = listener;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void setBorderColor(int color) {
/* 1090 */     this.mBorderColor = color;
/* 1091 */     invalidate();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int getBorderColor() {
/* 1098 */     return this.mBorderColor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int getColor() {
/* 1107 */     return Color.HSVToColor(this.mAlpha, new float[] { this.mHue, this.mSat, this.mVal });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void setColor(int color) {
/* 1116 */     setColor(color, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void setColor(int color, boolean callback) {
/* 1127 */     int alpha = Color.alpha(color);
/* 1128 */     int red = Color.red(color);
/* 1129 */     int blue = Color.blue(color);
/* 1130 */     int green = Color.green(color);
/*      */     
/* 1132 */     float[] hsv = new float[3];
/*      */     
/* 1134 */     Color.RGBToHSV(red, green, blue, hsv);
/*      */     
/* 1136 */     this.mAlpha = alpha;
/* 1137 */     this.mHue = hsv[0];
/* 1138 */     this.mSat = hsv[1];
/* 1139 */     this.mVal = hsv[2];
/*      */     
/* 1141 */     if (callback && this.mListener != null) {
/* 1142 */       this.mListener.onColorChanged(Color.HSVToColor(this.mAlpha, new float[] { this.mHue, this.mSat, this.mVal }));
/*      */     }
/*      */     
/* 1145 */     invalidate();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   float getDrawingOffset() {
/* 1158 */     return this.mDrawingOffset;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void setAlphaSliderVisible(boolean visible) {
/* 1169 */     if (this.mShowAlphaPanel != visible) {
/* 1170 */       this.mShowAlphaPanel = visible;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1177 */       this.mValShader = null;
/* 1178 */       this.mSatShader = null;
/* 1179 */       this.mHueShader = null;
/* 1180 */       this.mAlphaShader = null;
/*      */       
/* 1182 */       requestLayout();
/*      */     } 
/*      */   }
/*      */   
/*      */   void setSliderTrackerColor(int color) {
/* 1187 */     this.mSliderTrackerColor = color;
/*      */     
/* 1189 */     this.mHueTrackerPaint.setColor(this.mSliderTrackerColor);
/*      */     
/* 1191 */     invalidate();
/*      */   }
/*      */   
/*      */   int getSliderTrackerColor() {
/* 1195 */     return this.mSliderTrackerColor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void setAlphaSliderText(int res) {
/* 1205 */     String text = getContext().getString(res);
/* 1206 */     setAlphaSliderText(text);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void setAlphaSliderText(String text) {
/* 1216 */     this.mAlphaSliderText = text;
/* 1217 */     invalidate();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   String getAlphaSliderText() {
/* 1228 */     return this.mAlphaSliderText;
/*      */   }
/*      */   
/*      */   static interface OnColorChangedListener {
/*      */     void onColorChanged(int param1Int);
/*      */   }
/*      */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\ColorPickerView.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */