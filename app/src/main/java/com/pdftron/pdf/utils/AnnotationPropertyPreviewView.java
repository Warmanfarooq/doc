/*     */ package com.pdftron.pdf.utils;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.content.res.TypedArray;
/*     */ import android.graphics.Bitmap;
/*     */ import android.graphics.BitmapFactory;
/*     */ import android.graphics.BitmapShader;
/*     */ import android.graphics.Canvas;
/*     */ import android.graphics.Color;
/*     */ import android.graphics.Paint;
/*     */ import android.graphics.Path;
/*     */ import android.graphics.PorterDuff;
/*     */ import android.graphics.Rect;
/*     */ import android.graphics.Shader;
/*     */ import android.graphics.Typeface;
/*     */ import android.graphics.drawable.LayerDrawable;
/*     */ import android.text.TextPaint;
/*     */ import android.util.AttributeSet;
/*     */ import androidx.annotation.ColorInt;
/*     */ import androidx.appcompat.widget.AppCompatImageView;
/*     */ import com.pdftron.pdf.StrokeOutlineBuilder;
/*     */ import com.pdftron.pdf.config.ToolStyleConfig;
/*     */ import com.pdftron.pdf.model.AnnotStyle;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import java.io.File;
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
/*     */ public class AnnotationPropertyPreviewView
/*     */   extends AppCompatImageView
/*     */ {
/*  40 */   private String mPreviewText = "Aa";
/*     */   private float mPaintPadding;
/*  42 */   private int mAnnotType = 28;
/*     */   
/*     */   private Paint mPaint;
/*     */   
/*     */   private Paint mSignaturePaint;
/*     */   private Paint mFillPaint;
/*     */   private Paint mTextPaint;
/*     */   private Path mPath;
/*     */   private Paint mTransparentPaint;
/*     */   private Paint mOuterStrokePaint;
/*     */   private Path mStrokeOutlinePath;
/*     */   private double mWidth;
/*     */   private float mTextSizeRatio;
/*     */   private boolean mDrawTransparent = false;
/*  56 */   private int mParentBackground = -1;
/*     */   private boolean mDrawStroke = true;
/*  58 */   private float mMinTextSize = 0.0F;
/*     */   
/*     */   private boolean mUseStrokeRatio = false;
/*     */   private float mMaxThickness;
/*     */   private boolean showPressurePreview = false;
/*     */   
/*     */   public AnnotationPropertyPreviewView(Context context) {
/*  65 */     super(context);
/*  66 */     init((AttributeSet)null);
/*     */   }
/*     */   
/*     */   public AnnotationPropertyPreviewView(Context context, AttributeSet attrs) {
/*  70 */     super(context, attrs);
/*  71 */     init(attrs);
/*     */   }
/*     */   
/*     */   public AnnotationPropertyPreviewView(Context context, AttributeSet attrs, int defStyle) {
/*  75 */     super(context, attrs, defStyle);
/*  76 */     init(attrs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAnnotType(int annotType) {
/*  85 */     this.mAnnotType = annotType;
/*  86 */     this.mMaxThickness = ToolStyleConfig.getInstance().getDefaultMaxThickness(getContext(), annotType);
/*     */   }
/*     */   
/*     */   private void init(AttributeSet attrs) {
/*  90 */     this.mPaint = new Paint();
/*  91 */     this.mPaint.setStrokeJoin(Paint.Join.ROUND);
/*  92 */     this.mPaint.setStrokeCap(Paint.Cap.ROUND);
/*  93 */     this.mPaint.setAntiAlias(true);
/*  94 */     this.mPaint.setStyle(Paint.Style.STROKE);
/*     */     
/*  96 */     this.mSignaturePaint = new Paint();
/*  97 */     this.mSignaturePaint.setStrokeJoin(Paint.Join.ROUND);
/*  98 */     this.mSignaturePaint.setStrokeCap(Paint.Cap.ROUND);
/*  99 */     this.mSignaturePaint.setAntiAlias(true);
/* 100 */     this.mSignaturePaint.setStyle(Paint.Style.FILL);
/*     */     
/* 102 */     this.mFillPaint = new Paint();
/* 103 */     this.mFillPaint.setStrokeJoin(Paint.Join.ROUND);
/* 104 */     this.mFillPaint.setStrokeCap(Paint.Cap.ROUND);
/* 105 */     this.mFillPaint.setAntiAlias(true);
/* 106 */     this.mFillPaint.setStyle(Paint.Style.FILL);
/*     */     
/* 108 */     this.mTextPaint = new Paint(1);
/* 109 */     this.mTextPaint.setStrokeJoin(Paint.Join.ROUND);
/* 110 */     this.mTextPaint.setStrokeCap(Paint.Cap.ROUND);
/* 111 */     this.mTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
/* 112 */     this.mTextPaint.setTextAlign(Paint.Align.CENTER);
/*     */     
/* 114 */     this.mOuterStrokePaint = new Paint(1);
/* 115 */     this.mOuterStrokePaint.setStyle(Paint.Style.STROKE);
/* 116 */     this.mOuterStrokePaint.setTextAlign(Paint.Align.CENTER);
/* 117 */     this.mOuterStrokePaint.setStrokeWidth(Utils.convDp2Pix(getContext(), 1.0F));
/*     */     
/* 119 */     this.mTransparentPaint = new Paint(1);
/* 120 */     this.mTransparentPaint.setStyle(Paint.Style.FILL);
/* 121 */     Bitmap transparent = BitmapFactory.decodeResource(getResources(), R.drawable.transparent_checker);
/* 122 */     this.mTransparentPaint.setShader((Shader)new BitmapShader(transparent, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
/* 123 */     this.mTransparentPaint.setAlpha(137);
/* 124 */     setWillNotDraw(false);
/*     */     
/* 126 */     this.mPath = new Path();
/*     */     
/* 128 */     TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.AnnotationPropertyPreviewView, 0, 0);
/*     */     
/* 130 */     boolean drawTransparent = a.getBoolean(R.styleable.AnnotationPropertyPreviewView_transparent_background, false);
/* 131 */     setDrawTransparentBackground(drawTransparent);
/*     */     
/* 133 */     boolean useStrokeRatio = a.getBoolean(R.styleable.AnnotationPropertyPreviewView_use_stroke_ratio, false);
/* 134 */     setUseStrokeRatio(useStrokeRatio);
/*     */     
/* 136 */     this.mParentBackground = a.getColor(R.styleable.AnnotationPropertyPreviewView_parent_background, -1);
/* 137 */     int strokeColor = a.getColor(R.styleable.AnnotationPropertyPreviewView_stroke_color, getContext().getResources().getColor(R.color.tools_eraser_gray));
/* 138 */     setInnerOuterStrokeColor(strokeColor);
/* 139 */     String previewText = a.getString(R.styleable.AnnotationPropertyPreviewView_preview_text);
/* 140 */     if (!Utils.isNullOrEmpty(previewText)) {
/* 141 */       setPreviewText(previewText);
/*     */     }
/* 143 */     this.mMinTextSize = a.getDimensionPixelOffset(R.styleable.AnnotationPropertyPreviewView_min_text_size, 0);
/* 144 */     boolean drawStroke = a.getBoolean(R.styleable.AnnotationPropertyPreviewView_draw_stroke, true);
/* 145 */     setDrawInnerOuterStroke(drawStroke);
/* 146 */     a.recycle();
/* 147 */     this.mPaintPadding = Utils.convDp2Pix(getContext(), 2.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onSizeChanged(int w, int h, int oldw, int oldh) {
/* 155 */     super.onSizeChanged(w, h, oldw, oldh);
/*     */     
/* 157 */     this.mPath.reset();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDrawTransparentBackground(boolean draw) {
/* 166 */     this.mDrawTransparent = draw;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInnerOuterStrokeColor(@ColorInt int color) {
/* 176 */     this.mOuterStrokePaint.setColor(color);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDrawInnerOuterStroke(boolean drawStroke) {
/* 186 */     this.mDrawStroke = drawStroke;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUseStrokeRatio(boolean useStrokeRatio) {
/* 195 */     this.mUseStrokeRatio = useStrokeRatio;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setShowPressurePreview(boolean showPressurePreview) {
/* 204 */     this.showPressurePreview = showPressurePreview;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPreviewText(String text) {
/* 213 */     this.mPreviewText = text;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateFillPreview(AnnotStyle annotStyle) {
/* 222 */     if (!Utils.isNullOrEmpty(annotStyle.getIcon())) {
/* 223 */       setImageDrawable(annotStyle.getIconDrawable(getContext()));
/*     */     }
/* 225 */     if (annotStyle.hasTextStyle()) {
/* 226 */       float maxTextSize = ToolStyleConfig.getInstance().getDefaultMaxTextSize(getContext());
/* 227 */       updateFreeTextStyle(annotStyle.getTextColor(), annotStyle.getTextSize() / maxTextSize);
/*     */     } 
/* 229 */     if (annotStyle.hasFont() && !Utils.isNullOrEmpty(annotStyle.getFontPath())) {
/* 230 */       setFontPath(annotStyle.getFontPath());
/*     */     }
/* 232 */     updateFillPreview(annotStyle.getColor(), annotStyle.getFillColor(), annotStyle.getThickness(), annotStyle.getOpacity());
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
/*     */   public void updateFillPreview(int stroke, int fill, double thickness, double opacity) {
/* 244 */     int alpha = (int)(255.0D * opacity);
/* 245 */     if (stroke == 0) {
/* 246 */       this.mPaint.setColor(Color.argb(0, 0, 0, 0));
/* 247 */       this.mSignaturePaint.setColor(Color.argb(0, 0, 0, 0));
/*     */     } else {
/* 249 */       this.mPaint.setColor(Color.argb(alpha, Color.red(stroke), Color.green(stroke), Color.blue(stroke)));
/* 250 */       this.mSignaturePaint.setColor(Color.argb(alpha, Color.red(stroke), Color.green(stroke), Color.blue(stroke)));
/*     */     } 
/*     */     
/* 253 */     if (fill == 0) {
/* 254 */       this.mFillPaint.setColor(Color.argb(0, 0, 0, 0));
/*     */     } else {
/* 256 */       this.mFillPaint.setColor(Color.argb(alpha, Color.red(fill), Color.green(fill), Color.blue(fill)));
/*     */     } 
/*     */     
/* 259 */     this.mTextPaint.setAlpha(alpha);
/*     */ 
/*     */     
/* 262 */     if (this.mAnnotType == 0 && 
/* 263 */       getDrawable() != null && getDrawable() instanceof LayerDrawable) {
/* 264 */       LayerDrawable layerDrawable = (LayerDrawable)getDrawable();
/* 265 */       layerDrawable.getDrawable(0).mutate();
/* 266 */       layerDrawable.getDrawable(0).setAlpha(alpha);
/* 267 */       layerDrawable.getDrawable(0).setColorFilter(stroke, PorterDuff.Mode.SRC_IN);
/* 268 */       layerDrawable.getDrawable(1).mutate();
/* 269 */       layerDrawable.getDrawable(1).setAlpha(alpha);
/*     */     } 
/*     */     
/* 272 */     this.mWidth = thickness;
/*     */     
/* 274 */     invalidate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFontPath(String fontPath) {
/* 283 */     if (Utils.isNullOrEmpty(fontPath)) {
/*     */       return;
/*     */     }
/* 286 */     File file = new File(fontPath);
/* 287 */     if (file.exists()) {
/* 288 */       Typeface typeFace = Typeface.createFromFile(file);
/* 289 */       if (typeFace != null) {
/* 290 */         this.mTextPaint.setTypeface(typeFace);
/* 291 */         this.mOuterStrokePaint.setTypeface(typeFace);
/* 292 */         invalidate();
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
/*     */   public void updateFreeTextStyle(@ColorInt int textColor, float textSizeRatio) {
/* 304 */     this.mTextSizeRatio = textSizeRatio;
/* 305 */     this.mTextPaint.setColor(textColor);
/* 306 */     invalidate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setParentBackgroundColor(int color) {
/* 317 */     this.mParentBackground = color;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onDraw(Canvas canvas) {
/*     */     float strokeWidth;
/* 329 */     if (getDrawable() != null) {
/* 330 */       super.onDraw(canvas);
/*     */       
/*     */       return;
/*     */     } 
/* 334 */     int saveCount = canvas.getSaveCount();
/* 335 */     canvas.save();
/* 336 */     canvas.translate(getPaddingLeft(), getPaddingTop());
/* 337 */     float realWidth = (canvas.getWidth() - getPaddingLeft() + getPaddingRight());
/* 338 */     float realHeight = (canvas.getHeight() - getPaddingTop() + getPaddingBottom());
/* 339 */     float widthScale = realWidth / canvas.getWidth();
/* 340 */     float heightScale = realHeight / canvas.getHeight();
/* 341 */     canvas.scale(widthScale, heightScale);
/*     */     
/* 343 */     float maxStroke = (Math.min(canvas.getWidth(), canvas.getHeight()) * 3 / 8);
/*     */ 
/*     */     
/* 346 */     if (this.mUseStrokeRatio) {
/* 347 */       float strokeRatio = (this.mWidth < this.mMaxThickness) ? ((float)this.mWidth / this.mMaxThickness) : 1.0F;
/* 348 */       strokeWidth = maxStroke * strokeRatio;
/* 349 */       if (strokeWidth > 0.0F && strokeWidth < Utils.convDp2Pix(getContext(), 2.0F)) {
/* 350 */         strokeWidth = Utils.convDp2Pix(getContext(), 2.0F);
/*     */       }
/*     */     } else {
/* 353 */       strokeWidth = Utils.convDp2Pix(getContext(), (float)this.mWidth);
/* 354 */       if (strokeWidth > maxStroke) {
/* 355 */         strokeWidth = maxStroke;
/* 356 */       } else if (strokeWidth > 0.0F && strokeWidth < Utils.convDp2Pix(getContext(), 2.0F)) {
/* 357 */         strokeWidth = Utils.convDp2Pix(getContext(), 2.0F);
/*     */       } 
/*     */     } 
/* 360 */     this.mPaint.setStrokeWidth(strokeWidth);
/*     */     
/* 362 */     drawTransparentBackground(canvas);
/* 363 */     switch (this.mAnnotType) {
/*     */       case 19:
/* 365 */         drawTextOnly(canvas);
/*     */         break;
/*     */       case 2:
/*     */       case 1007:
/*     */       case 1010:
/*     */       case 1011:
/* 371 */         drawTextWithRect(canvas);
/*     */         break;
/*     */       case 8:
/* 374 */         drawHighlight(canvas);
/*     */         break;
/*     */       case 9:
/* 377 */         drawUnderline(canvas);
/*     */         break;
/*     */       case 11:
/* 380 */         drawStrikeOut(canvas);
/*     */         break;
/*     */       case 10:
/* 383 */         drawSquiggly(canvas);
/*     */         break;
/*     */       case 14:
/*     */       case 1004:
/* 387 */         drawFreehand(canvas);
/*     */         break;
/*     */       case 1002:
/* 390 */         if (this.showPressurePreview) {
/* 391 */           drawSignatureWithPressure(canvas); break;
/*     */         } 
/* 393 */         drawSigStroke(canvas);
/*     */         break;
/*     */       
/*     */       case 1001:
/* 397 */         drawArrow(canvas);
/*     */         break;
/*     */       case 7:
/*     */       case 1008:
/* 401 */         drawPolyline(canvas);
/*     */         break;
/*     */       case 4:
/*     */       case 25:
/*     */       case 1012:
/* 406 */         drawRect(canvas);
/*     */         break;
/*     */       case 6:
/*     */       case 1009:
/* 410 */         drawPolygon(canvas, false);
/*     */         break;
/*     */       case 1005:
/* 413 */         drawPolygon(canvas, true);
/*     */         break;
/*     */       case 5:
/* 416 */         drawOval(canvas);
/*     */         break;
/*     */       case 1003:
/* 419 */         drawEraser(canvas);
/*     */         break;
/*     */       default:
/* 422 */         drawLine(canvas);
/*     */         break;
/*     */     } 
/* 425 */     canvas.restoreToCount(saveCount);
/*     */   }
/*     */   
/*     */   private void drawTransparentBackground(Canvas canvas) {
/* 429 */     boolean drawTransparent = (this.mDrawTransparent && this.mPaint.getAlpha() < 255 && this.mFillPaint.getAlpha() < 255);
/*     */     
/* 431 */     if (AnnotStyle.isFreeTextGroup(this.mAnnotType)) {
/* 432 */       drawTransparent = (drawTransparent && this.mTextPaint.getAlpha() < 255);
/*     */     }
/* 434 */     if (drawTransparent) {
/* 435 */       canvas.drawRect(this.mPaintPadding, this.mPaintPadding, getWidth() - this.mPaintPadding, getHeight() - this.mPaintPadding, this.mTransparentPaint);
/*     */     }
/*     */   }
/*     */   
/*     */   private void drawLine(Canvas canvas) {
/* 440 */     this.mPaint.setStrokeJoin(Paint.Join.MITER);
/* 441 */     this.mPaint.setStrokeCap(Paint.Cap.SQUARE);
/*     */     
/* 443 */     float strokeWidth = this.mPaint.getStrokeWidth();
/* 444 */     float pad = this.mPaintPadding + strokeWidth / 2.0F;
/*     */ 
/*     */     
/* 447 */     float x1 = pad;
/* 448 */     float y1 = getMeasuredHeight() - pad;
/* 449 */     float x2 = getMeasuredWidth() - pad;
/*     */     
/* 451 */     float y2 = pad;
/*     */     
/* 453 */     canvas.drawLine(x1, y1, x2, y2, this.mPaint);
/*     */     
/* 455 */     if (this.mDrawStroke && hasSameRgb(this.mParentBackground, this.mPaint.getColor())) {
/* 456 */       this.mOuterStrokePaint.setStrokeJoin(Paint.Join.MITER);
/* 457 */       this.mOuterStrokePaint.setStrokeCap(Paint.Cap.SQUARE);
/*     */       
/* 459 */       float dx = x2 - x1;
/* 460 */       float dy = y1 - y2;
/* 461 */       if (dy == 0.0F) {
/*     */         return;
/*     */       }
/* 464 */       float len = strokeWidth / 2.0F;
/* 465 */       double a = Math.atan((dx / dy));
/* 466 */       double sina = Math.sin(a);
/* 467 */       double cosa = Math.cos(a);
/* 468 */       float w = (float)(len * cosa);
/* 469 */       float h = (float)(len * sina);
/*     */       
/* 471 */       len = (strokeWidth - this.mOuterStrokePaint.getStrokeWidth()) / 2.0F;
/* 472 */       float w2 = (float)(len * sina);
/* 473 */       float h2 = (float)(len * cosa);
/*     */       
/* 475 */       Path path = new Path();
/* 476 */       path.moveTo(x1 + w - w2, y1 + h + h2);
/* 477 */       path.lineTo(x2 + w + w2, y2 + h - h2);
/* 478 */       path.lineTo(x2 - w + w2, y2 - h - h2);
/* 479 */       path.lineTo(x1 - w - w2, y1 - h + h2);
/* 480 */       path.lineTo(x1 + w - w2, y1 + h + h2);
/* 481 */       canvas.drawPath(path, this.mOuterStrokePaint);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void drawArrow(Canvas canvas) {
/* 486 */     this.mPaint.setStrokeJoin(Paint.Join.MITER);
/* 487 */     this.mPaint.setStrokeCap(Paint.Cap.SQUARE);
/*     */     
/* 489 */     float cos30 = (float)Math.cos(0.5235987750000001D);
/* 490 */     float sin30 = (float)Math.sin(0.5235987750000001D);
/* 491 */     float arrowLength = Utils.convDp2Pix(getContext(), 20.0F);
/*     */     
/* 493 */     float strokeWidth = this.mPaint.getStrokeWidth();
/* 494 */     float pad = this.mPaintPadding + strokeWidth;
/*     */     
/* 496 */     float x1 = pad;
/* 497 */     float y1 = getMeasuredHeight() - pad;
/* 498 */     float x2 = getMeasuredWidth() - pad;
/* 499 */     float arrowOffset = pad / 6.0F;
/* 500 */     float y2 = pad + arrowOffset;
/* 501 */     float dx = x1 - x2;
/* 502 */     float dy = y1 - y2;
/* 503 */     float len = dx * dx + dy * dy;
/* 504 */     if (len == 0.0F) {
/*     */       return;
/*     */     }
/* 507 */     len = (float)Math.sqrt(len);
/* 508 */     dx /= len;
/* 509 */     dy /= len;
/* 510 */     float dx1 = dx * cos30 - dy * sin30;
/* 511 */     float dy1 = dy * cos30 + dx * sin30;
/* 512 */     float x3 = x2 + arrowLength * dx1;
/* 513 */     float y3 = y2 + arrowLength * dy1;
/* 514 */     float dx2 = dx * cos30 + dy * sin30;
/* 515 */     float dy2 = dy * cos30 - dx * sin30;
/* 516 */     float x4 = x2 + arrowLength * dx2;
/* 517 */     float y4 = y2 + arrowLength * dy2;
/* 518 */     float x5 = x2 + dx * strokeWidth;
/* 519 */     float y5 = y2 + dy * strokeWidth;
/*     */     
/* 521 */     Path path = new Path();
/*     */ 
/*     */     
/* 524 */     path.moveTo(x5, y5);
/* 525 */     path.lineTo(x1, y1);
/*     */ 
/*     */     
/* 528 */     path.moveTo(x3, y3);
/* 529 */     path.lineTo(x2, y2);
/* 530 */     path.lineTo(x4, y4);
/* 531 */     canvas.drawPath(path, this.mPaint);
/*     */     
/* 533 */     if (this.mDrawStroke && hasSameRgb(this.mParentBackground, this.mPaint.getColor())) {
/* 534 */       this.mOuterStrokePaint.setStrokeJoin(Paint.Join.MITER);
/* 535 */       this.mOuterStrokePaint.setStrokeCap(Paint.Cap.SQUARE);
/* 536 */       canvas.drawPath(path, this.mOuterStrokePaint);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void drawPolyline(Canvas canvas) {
/* 541 */     this.mPaint.setStrokeJoin(Paint.Join.MITER);
/* 542 */     this.mPaint.setStrokeCap(Paint.Cap.SQUARE);
/*     */     
/* 544 */     float strokeWidth = this.mPaint.getStrokeWidth();
/* 545 */     float pad = this.mPaintPadding + strokeWidth / 2.0F;
/* 546 */     Path path = getPolylinePath(pad);
/* 547 */     canvas.drawPath(path, this.mPaint);
/*     */     
/* 549 */     if (this.mDrawStroke && hasSameRgb(this.mParentBackground, this.mPaint.getColor())) {
/* 550 */       this.mOuterStrokePaint.setStrokeJoin(Paint.Join.MITER);
/* 551 */       this.mOuterStrokePaint.setStrokeCap(Paint.Cap.SQUARE);
/* 552 */       canvas.drawPath(path, this.mOuterStrokePaint);
/*     */     } 
/*     */   }
/*     */   
/*     */   private Path getPolylinePath(float padding) {
/* 557 */     Path path = new Path();
/* 558 */     float w = getMeasuredWidth() - 2.0F * padding;
/* 559 */     float w_6 = w / 6.0F;
/* 560 */     float h = getMeasuredHeight() - 2.0F * padding;
/* 561 */     float h_2 = h / 2.0F;
/*     */     
/* 563 */     path.moveTo(padding, padding + h);
/* 564 */     path.lineTo(padding + w_6, padding + h_2);
/* 565 */     path.lineTo(padding + w - w_6, padding + h_2);
/* 566 */     path.lineTo(padding + w, padding);
/* 567 */     return path;
/*     */   }
/*     */   
/*     */   private void drawRect(Canvas canvas) {
/* 571 */     this.mPaint.setStrokeJoin(Paint.Join.MITER);
/* 572 */     this.mFillPaint.setStrokeJoin(Paint.Join.MITER);
/* 573 */     this.mPaint.setStrokeCap(Paint.Cap.SQUARE);
/* 574 */     this.mFillPaint.setStrokeCap(Paint.Cap.SQUARE);
/* 575 */     float strokeWidth = this.mPaint.getStrokeWidth();
/* 576 */     float pos = this.mPaintPadding + strokeWidth / 2.0F;
/* 577 */     float w = getMeasuredWidth();
/* 578 */     float h = getMeasuredHeight();
/* 579 */     canvas.drawRect(pos, pos, w - pos, h - pos, this.mFillPaint);
/* 580 */     canvas.drawRect(pos, pos, w - pos, h - pos, this.mPaint);
/*     */ 
/*     */     
/* 583 */     if (this.mDrawStroke && this.mFillPaint.getColor() == 0 && hasSameRgb(this.mParentBackground, this.mPaint.getColor())) {
/* 584 */       canvas.drawRect(this.mPaintPadding, this.mPaintPadding, w - this.mPaintPadding, h - this.mPaintPadding, this.mOuterStrokePaint);
/* 585 */       float innerPos = pos + strokeWidth / 2.0F;
/* 586 */       canvas.drawRect(innerPos, innerPos, w - innerPos, h - innerPos, this.mOuterStrokePaint);
/*     */     } 
/*     */     
/* 589 */     if (this.mDrawStroke && hasSameRgb(this.mParentBackground, this.mFillPaint.getColor()) && (this.mPaint.getColor() == 0 || this.mPaint.getStrokeWidth() == 0.0F)) {
/* 590 */       canvas.drawRect(pos, pos, w - pos, h - pos, this.mOuterStrokePaint);
/*     */     }
/*     */   }
/*     */   
/*     */   private void drawPolygon(Canvas canvas, boolean isCloud) {
/* 595 */     this.mPaint.setStrokeJoin(Paint.Join.MITER);
/* 596 */     this.mFillPaint.setStrokeJoin(Paint.Join.MITER);
/* 597 */     this.mPaint.setStrokeCap(Paint.Cap.SQUARE);
/* 598 */     this.mFillPaint.setStrokeCap(Paint.Cap.SQUARE);
/* 599 */     float strokeWidth = this.mPaint.getStrokeWidth();
/* 600 */     float pos = this.mPaintPadding + strokeWidth / 2.0F;
/* 601 */     Path path = getPolygonPath(pos, isCloud);
/* 602 */     canvas.drawPath(path, this.mFillPaint);
/* 603 */     canvas.drawPath(path, this.mPaint);
/*     */ 
/*     */     
/* 606 */     if (this.mDrawStroke && this.mFillPaint.getColor() == 0 && hasSameRgb(this.mParentBackground, this.mPaint.getColor())) {
/* 607 */       path = getPolygonPath(this.mPaintPadding, isCloud);
/* 608 */       canvas.drawPath(path, this.mOuterStrokePaint);
/* 609 */       float innerPos = pos + strokeWidth / 2.0F;
/* 610 */       path = getPolygonPath(innerPos, isCloud);
/* 611 */       canvas.drawPath(path, this.mOuterStrokePaint);
/*     */     } 
/*     */     
/* 614 */     if (this.mDrawStroke && hasSameRgb(this.mParentBackground, this.mFillPaint.getColor()) && (this.mPaint.getColor() == 0 || this.mPaint.getStrokeWidth() == 0.0F)) {
/* 615 */       path = getPolygonPath(pos, isCloud);
/* 616 */       canvas.drawPath(path, this.mOuterStrokePaint);
/*     */     } 
/*     */   }
/*     */   
/*     */   private Path getPolygonPath(float padding, boolean isCloud) {
/* 621 */     Path path = new Path();
/* 622 */     float w = getMeasuredWidth() - 2.0F * padding;
/* 623 */     float w_2 = w / 2.0F;
/* 624 */     float w_4 = w / 4.0F;
/* 625 */     float w_6 = w / 6.0F;
/* 626 */     float h = getMeasuredHeight() - 2.0F * padding;
/* 627 */     float h_2 = h / 2.0F;
/* 628 */     float h_6 = h / 6.0F;
/* 629 */     if (isCloud) {
/* 630 */       path.moveTo(padding + w_2, padding + h);
/* 631 */       path.lineTo(padding + w_6, padding + h);
/* 632 */       path.cubicTo(padding, padding + h, padding, padding + h_2, padding + w_6, padding + h_2);
/* 633 */       path.cubicTo(padding + w_6, padding - h_6, padding + w - w_6, padding - h_6, padding + w - w_6, padding + h_2);
/* 634 */       path.cubicTo(padding + w, padding + h_2, padding + w, padding + h, padding + w - w_6, padding + h);
/* 635 */       path.lineTo(padding + w_2, padding + h);
/*     */     } else {
/* 637 */       path.moveTo(padding, padding + h_2);
/* 638 */       path.lineTo(padding + w_4, padding);
/* 639 */       path.lineTo(padding + w - w_4, padding);
/* 640 */       path.lineTo(padding + w, padding + h_2);
/* 641 */       path.lineTo(padding + w - w_4, padding + h);
/* 642 */       path.lineTo(padding + w_4, padding + h);
/* 643 */       path.lineTo(padding, padding + h_2);
/*     */     } 
/* 645 */     return path;
/*     */   }
/*     */   
/*     */   private void drawOval(Canvas canvas) {
/* 649 */     if (Utils.isLollipop()) {
/* 650 */       float strokeWidth = this.mPaint.getStrokeWidth();
/* 651 */       float pos = this.mPaintPadding + strokeWidth / 2.0F;
/* 652 */       canvas.drawOval(pos, pos, getMeasuredWidth() - pos, getMeasuredHeight() - pos, this.mFillPaint);
/* 653 */       canvas.drawOval(pos, pos, getMeasuredWidth() - pos, getMeasuredHeight() - pos, this.mPaint);
/* 654 */       if (this.mDrawStroke && this.mFillPaint.getColor() == 0 && hasSameRgb(this.mParentBackground, this.mPaint.getColor())) {
/* 655 */         canvas.drawOval(this.mPaintPadding, this.mPaintPadding, getMeasuredWidth() - this.mPaintPadding, getMeasuredHeight() - this.mPaintPadding, this.mOuterStrokePaint);
/* 656 */         float innerPos = pos + strokeWidth / 2.0F;
/* 657 */         canvas.drawOval(innerPos, innerPos, getMeasuredWidth() - innerPos, getMeasuredHeight() - innerPos, this.mOuterStrokePaint);
/*     */       } 
/* 659 */       if (this.mDrawStroke && hasSameRgb(this.mParentBackground, this.mFillPaint.getColor()) && (this.mPaint.getColor() == 0 || this.mPaint.getStrokeWidth() == 0.0F)) {
/* 660 */         canvas.drawOval(pos, pos, getMeasuredWidth() - pos, getMeasuredHeight() - pos, this.mOuterStrokePaint);
/*     */       }
/*     */     } else {
/* 663 */       canvas.drawCircle(getMeasuredWidth() * 0.5F, getMeasuredHeight() * 0.5F, getMeasuredWidth() * 0.3F, this.mFillPaint);
/* 664 */       canvas.drawCircle(getMeasuredWidth() * 0.5F, getMeasuredHeight() * 0.5F, getMeasuredWidth() * 0.3F, this.mPaint);
/*     */       
/* 666 */       if (this.mDrawStroke && this.mFillPaint.getColor() == 0 && hasSameRgb(this.mParentBackground, this.mPaint.getColor())) {
/* 667 */         float outerRadius = getMeasuredWidth() * 0.3F + this.mPaint.getStrokeWidth() / 2.0F;
/* 668 */         float innerRadius = getMeasuredWidth() * 0.3F - this.mPaint.getStrokeWidth() / 2.0F;
/* 669 */         canvas.drawCircle(getMeasuredWidth() * 0.5F, getMeasuredHeight() * 0.5F, outerRadius, this.mOuterStrokePaint);
/* 670 */         canvas.drawCircle(getMeasuredWidth() * 0.5F, getMeasuredHeight() * 0.5F, innerRadius, this.mOuterStrokePaint);
/*     */       } 
/*     */       
/* 673 */       if (this.mDrawStroke && hasSameRgb(this.mParentBackground, this.mFillPaint.getColor()) && (this.mPaint.getColor() == 0 || this.mPaint.getStrokeWidth() == 0.0F)) {
/* 674 */         canvas.drawCircle(getMeasuredWidth() * 0.5F, getMeasuredHeight() * 0.5F, getMeasuredWidth() * 0.3F, this.mOuterStrokePaint);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void drawEraser(Canvas canvas) {
/* 681 */     int minRadius = (int)(getMeasuredHeight() * 0.1F);
/* 682 */     int maxRadius = (int)(getMeasuredHeight() * 0.5F);
/* 683 */     float eraserRange = ToolStyleConfig.getInstance().getDefaultThicknessRange(getContext(), 1003);
/* 684 */     float radiusRange = (maxRadius - minRadius);
/* 685 */     float scale = radiusRange / eraserRange;
/* 686 */     float eraserMinThickness = ToolStyleConfig.getInstance().getDefaultMinThickness(getContext(), 1003);
/* 687 */     int radius = (int)((this.mWidth - eraserMinThickness) * scale + minRadius);
/*     */ 
/*     */     
/* 690 */     Paint paint = new Paint();
/* 691 */     paint.setStyle(Paint.Style.FILL_AND_STROKE);
/* 692 */     paint.setAntiAlias(true);
/* 693 */     paint.setColor(getResources().getColor(R.color.tools_eraser_gray));
/* 694 */     canvas.drawCircle(getMeasuredWidth() * 0.5F, getMeasuredHeight() * 0.5F, radius, paint);
/*     */   }
/*     */   
/*     */   private void drawFreehand(Canvas canvas) {
/* 698 */     this.mPath.moveTo(getMeasuredWidth() * 0.3F, getMeasuredHeight() * 0.8F);
/* 699 */     this.mPath.quadTo(getMeasuredWidth() * 0.3F, getMeasuredHeight() * 0.3F, getMeasuredWidth() * 0.8F, getMeasuredHeight() * 0.2F);
/*     */     
/* 701 */     canvas.drawPath(this.mPath, this.mPaint);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void drawSignatureWithPressure(Canvas canvas) {
/* 707 */     float width = getMeasuredWidth();
/* 708 */     float height = getMeasuredHeight();
/* 709 */     float vertOffset = height / 2.0F;
/* 710 */     float horizOffset = 3.1415927F;
/* 711 */     float widthScale = width / 6.2831855F;
/* 712 */     float heightScale = height / 4.0F;
/*     */ 
/*     */     
/* 715 */     int steps = 11;
/* 716 */     float delX = width / steps;
/* 717 */     double[] curve = new double[(steps - 1) * 2];
/* 718 */     double[] pressures = new double[steps - 1];
/*     */ 
/*     */     
/* 721 */     for (int t = 1; t < steps; t++) {
/* 722 */       double x = (t * delX);
/* 723 */       double y = heightScale * Math.sin(x / widthScale + horizOffset) + vertOffset;
/* 724 */       curve[t * 2 - 2] = x;
/* 725 */       curve[t * 2 - 1] = y;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 730 */     double delP = 1.0D / pressures.length / 2.0D;
/* 731 */     pressures[0] = 0.0D;
/* 732 */     for (int i = 1; i < pressures.length; i++) {
/* 733 */       if (i <= pressures.length / 2) {
/* 734 */         pressures[i] = pressures[i - 1] + delP;
/*     */       } else {
/* 736 */         pressures[i] = pressures[i - 1] - delP;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 741 */     StrokeOutlineBuilder strokeOutlineBuilder = new StrokeOutlineBuilder(this.mWidth);
/*     */     
/* 743 */     for (int j = 0; j < curve.length; j += 2) {
/* 744 */       strokeOutlineBuilder.addPoint(curve[j], curve[j + 1], pressures[j / 2]);
/*     */     }
/*     */ 
/*     */     
/* 748 */     double[] smoothCurve = strokeOutlineBuilder.getOutline();
/*     */ 
/*     */     
/* 751 */     if (this.mStrokeOutlinePath == null) {
/* 752 */       this.mStrokeOutlinePath = PathPool.getInstance().obtain();
/*     */     } else {
/* 754 */       this.mStrokeOutlinePath.reset();
/*     */     } 
/*     */     
/* 757 */     this.mStrokeOutlinePath.moveTo((float)smoothCurve[0], (float)smoothCurve[1]);
/* 758 */     for (int k = 2, cnt = smoothCurve.length; k < cnt; k += 6) {
/* 759 */       this.mStrokeOutlinePath.cubicTo((float)smoothCurve[k], (float)smoothCurve[k + 1], (float)smoothCurve[k + 2], (float)smoothCurve[k + 3], (float)smoothCurve[k + 4], (float)smoothCurve[k + 5]);
/*     */     }
/*     */ 
/*     */     
/* 763 */     canvas.drawPath(this.mStrokeOutlinePath, this.mSignaturePaint);
/*     */   }
/*     */   
/*     */   private void drawSigStroke(Canvas canvas) {
/* 767 */     this.mPaint.setStrokeWidth((float)this.mWidth);
/* 768 */     drawFreehand(canvas);
/*     */   }
/*     */   
/*     */   private void drawTextWithRect(Canvas canvas) {
/* 772 */     drawRect(canvas);
/* 773 */     drawTextOnly(canvas);
/*     */   }
/*     */   
/*     */   private void drawTextOnly(Canvas canvas) {
/* 777 */     float currTextSize = this.mTextPaint.getTextSize();
/* 778 */     Rect bounds = new Rect();
/* 779 */     this.mTextPaint.getTextBounds(this.mPreviewText, 0, this.mPreviewText.length(), bounds);
/* 780 */     float maxTextSize = currTextSize * canvas.getWidth() / bounds.width();
/* 781 */     float textSize = this.mTextSizeRatio * maxTextSize;
/* 782 */     if (textSize < this.mMinTextSize) {
/* 783 */       textSize = this.mMinTextSize;
/*     */     }
/*     */     
/* 786 */     this.mTextPaint.setTextSize(textSize);
/* 787 */     this.mOuterStrokePaint.setTextSize(textSize);
/*     */     
/* 789 */     float xPos = canvas.getWidth() * 0.5F;
/* 790 */     float yPos = canvas.getHeight() * 0.5F - (this.mTextPaint.descent() + this.mTextPaint.ascent()) * 0.5F;
/*     */     
/* 792 */     canvas.drawText(this.mPreviewText, xPos, yPos, this.mTextPaint);
/*     */     
/* 794 */     if (this.mDrawStroke && this.mFillPaint.getColor() == 0 && hasSameRgb(this.mParentBackground, this.mTextPaint.getColor())) {
/* 795 */       canvas.drawText(this.mPreviewText, xPos, yPos, this.mOuterStrokePaint);
/*     */     }
/*     */   }
/*     */   
/*     */   private void drawSquiggly(Canvas canvas) {
/* 800 */     TextPaint textPaint = new TextPaint();
/* 801 */     textPaint.setAntiAlias(true);
/* 802 */     textPaint.setColor(getContext().getResources().getColor(R.color.controls_annot_style_markup_text));
/* 803 */     float currTextSize = textPaint.getTextSize();
/* 804 */     Rect bounds = new Rect();
/* 805 */     textPaint.getTextBounds(this.mPreviewText, 0, this.mPreviewText.length(), bounds);
/* 806 */     float maxTextSize = currTextSize * (canvas.getWidth() - 2.0F * this.mPaintPadding) / bounds.width();
/*     */     
/* 808 */     textPaint.setTextSize(maxTextSize);
/*     */     
/* 810 */     Paint.FontMetrics fm = new Paint.FontMetrics();
/* 811 */     textPaint.setTextAlign(Paint.Align.CENTER);
/* 812 */     textPaint.getFontMetrics(fm);
/*     */     
/* 814 */     float xPos = canvas.getWidth() * 0.5F;
/* 815 */     float yPos = canvas.getHeight() * 0.5F - (textPaint.descent() + textPaint.ascent()) / 2.0F;
/* 816 */     canvas.drawText(this.mPreviewText, xPos, yPos, (Paint)textPaint);
/*     */     
/* 818 */     float size = Utils.convDp2Pix(getContext(), 4.0F);
/* 819 */     Path path = new Path();
/* 820 */     float startX = xPos - textPaint.measureText(this.mPreviewText) * 0.5F;
/* 821 */     float startY = canvas.getHeight() - textPaint.descent();
/* 822 */     float endX = xPos + textPaint.measureText(this.mPreviewText) * 0.5F;
/* 823 */     boolean upward = true;
/* 824 */     path.moveTo(startX, startY);
/*     */     
/* 826 */     while (startX < endX) {
/* 827 */       float nextX = startX + size * 2.0F;
/* 828 */       float midY = upward ? (startY - size) : (startY + size);
/* 829 */       float midX = startX + size;
/* 830 */       path.quadTo(midX, midY, nextX, startY);
/* 831 */       path.moveTo(nextX, startY);
/* 832 */       startX = nextX;
/* 833 */       upward = !upward;
/*     */     } 
/* 835 */     canvas.drawPath(path, this.mPaint);
/*     */   }
/*     */   
/*     */   private void drawUnderline(Canvas canvas) {
/* 839 */     TextPaint textPaint = new TextPaint();
/* 840 */     textPaint.setAntiAlias(true);
/* 841 */     textPaint.setColor(getContext().getResources().getColor(R.color.controls_annot_style_markup_text));
/* 842 */     float currTextSize = textPaint.getTextSize();
/* 843 */     Rect bounds = new Rect();
/* 844 */     textPaint.getTextBounds(this.mPreviewText, 0, this.mPreviewText.length(), bounds);
/* 845 */     float maxTextSize = currTextSize * (canvas.getWidth() - 2.0F * this.mPaintPadding) / bounds.width();
/*     */     
/* 847 */     textPaint.setTextSize(maxTextSize);
/*     */     
/* 849 */     Paint.FontMetrics fm = new Paint.FontMetrics();
/* 850 */     textPaint.setTextAlign(Paint.Align.CENTER);
/* 851 */     textPaint.getFontMetrics(fm);
/*     */     
/* 853 */     float xPos = canvas.getWidth() * 0.5F;
/* 854 */     float yPos = canvas.getHeight() * 0.5F - (textPaint.descent() + textPaint.ascent()) / 2.0F;
/*     */     
/* 856 */     canvas.drawLine(xPos - textPaint.measureText(this.mPreviewText) * 0.5F, canvas.getHeight() - textPaint.descent(), xPos + textPaint.measureText(this.mPreviewText) * 0.5F, canvas.getHeight() - textPaint.descent(), this.mPaint);
/* 857 */     canvas.drawText(this.mPreviewText, xPos, yPos, (Paint)textPaint);
/*     */   }
/*     */   
/*     */   private void drawStrikeOut(Canvas canvas) {
/* 861 */     TextPaint textPaint = new TextPaint();
/* 862 */     textPaint.setAntiAlias(true);
/* 863 */     textPaint.setColor(getContext().getResources().getColor(R.color.controls_annot_style_markup_text));
/* 864 */     float currTextSize = textPaint.getTextSize();
/* 865 */     Rect bounds = new Rect();
/* 866 */     textPaint.getTextBounds(this.mPreviewText, 0, this.mPreviewText.length(), bounds);
/* 867 */     float maxTextSize = currTextSize * (canvas.getWidth() - 2.0F * this.mPaintPadding) / bounds.width();
/*     */     
/* 869 */     textPaint.setTextSize(maxTextSize);
/*     */     
/* 871 */     Paint.FontMetrics fm = new Paint.FontMetrics();
/* 872 */     textPaint.setTextAlign(Paint.Align.CENTER);
/* 873 */     textPaint.getFontMetrics(fm);
/*     */     
/* 875 */     float xPos = canvas.getWidth() * 0.5F;
/* 876 */     float yPos = canvas.getHeight() * 0.5F - (textPaint.descent() + textPaint.ascent()) / 2.0F;
/* 877 */     canvas.drawText(this.mPreviewText, xPos, yPos, (Paint)textPaint);
/* 878 */     canvas.drawLine(xPos - textPaint.measureText(this.mPreviewText) * 0.5F, canvas.getHeight() * 0.5F, xPos + textPaint.measureText(this.mPreviewText) * 0.5F, canvas.getHeight() * 0.5F, this.mPaint);
/*     */   }
/*     */   
/*     */   private void drawHighlight(Canvas canvas) {
/* 882 */     this.mPaint.setStyle(Paint.Style.FILL);
/* 883 */     TextPaint textPaint = new TextPaint();
/* 884 */     textPaint.setAntiAlias(true);
/* 885 */     textPaint.setColor(getContext().getResources().getColor(R.color.controls_annot_style_markup_text));
/* 886 */     float currTextSize = textPaint.getTextSize();
/* 887 */     Rect bounds = new Rect();
/* 888 */     textPaint.getTextBounds(this.mPreviewText, 0, this.mPreviewText.length(), bounds);
/* 889 */     float maxTextSize = currTextSize * (canvas.getWidth() - 2.0F * this.mPaintPadding) / bounds.width();
/*     */     
/* 891 */     textPaint.setTextSize(maxTextSize);
/*     */     
/* 893 */     Paint.FontMetrics fm = new Paint.FontMetrics();
/* 894 */     textPaint.setTextAlign(Paint.Align.CENTER);
/* 895 */     textPaint.getFontMetrics(fm);
/*     */     
/* 897 */     float xPos = canvas.getWidth() * 0.5F;
/* 898 */     float yPos = canvas.getHeight() * 0.5F - (textPaint.descent() + textPaint.ascent()) / 2.0F;
/* 899 */     canvas.drawRect(xPos - textPaint.measureText(this.mPreviewText) * 0.5F, yPos - textPaint.getTextSize() + 5.0F, xPos + textPaint.measureText(this.mPreviewText) * 0.5F, yPos + 10.0F, this.mPaint);
/* 900 */     canvas.drawText(this.mPreviewText, xPos, yPos, (Paint)textPaint);
/*     */   }
/*     */   
/*     */   private static boolean hasSameRgb(int color1, int color2) {
/* 904 */     return ((color1 & 0xFFFFFF) == (color2 & 0xFFFFFF));
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pd\\utils\AnnotationPropertyPreviewView.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */