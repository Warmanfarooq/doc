/*     */ package com.pdftron.pdf.widget;
/*     */ 
/*     */ import android.annotation.TargetApi;
/*     */ import android.content.Context;
/*     */ import android.graphics.Canvas;
/*     */ import android.graphics.Color;
/*     */ import android.graphics.Paint;
/*     */ import android.graphics.PointF;
/*     */ import android.graphics.Rect;
/*     */ import android.graphics.Typeface;
/*     */ import android.os.Bundle;
/*     */ import android.text.InputFilter;
/*     */ import android.text.Layout;
/*     */ import android.text.Spanned;
/*     */ import android.util.AttributeSet;
/*     */ import android.view.KeyEvent;
/*     */ import android.view.MotionEvent;
/*     */ import android.view.View;
/*     */ import android.view.inputmethod.EditorInfo;
/*     */ import android.view.inputmethod.InputConnection;
/*     */ import android.widget.TextView;
/*     */ import android.widget.Toast;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.appcompat.widget.AppCompatEditText;
/*     */ import androidx.core.view.inputmethod.EditorInfoCompat;
/*     */ import androidx.core.view.inputmethod.InputConnectionCompat;
/*     */ import androidx.core.view.inputmethod.InputContentInfoCompat;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.Rect;
/*     */ import com.pdftron.pdf.model.AnnotStyle;
/*     */ import com.pdftron.pdf.model.FontResource;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.tools.ToolManager;
/*     */ import com.pdftron.pdf.utils.DrawingUtils;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AutoScrollEditText
/*     */   extends AppCompatEditText
/*     */ {
/*     */   private SelectionHandleView mSpacingHandle;
/*     */   private int mSelectionHandleRadius;
/*     */   private AutoScrollEditTextListener mListener;
/*     */   private AutoScrollEditTextSpacingListener mSpacingListener;
/*     */   private AnnotViewImpl mAnnotViewImpl;
/*  56 */   private float mTextSize = 12.0F;
/*     */   
/*     */   private int mTextColor;
/*     */   
/*     */   private int mMaxCharacterCount;
/*     */   
/*     */   private PointF mDownPt;
/*     */   
/*     */   private float mPureTextWidth;
/*     */   
/*     */   private boolean mShowSpacingBox;
/*     */   private Paint mPaint;
/*  68 */   private PointF mTempPt1 = new PointF();
/*  69 */   private PointF mTempPt2 = new PointF();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Rect mViewBounds;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean mAutoResize;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private Rect mDefaultRect;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AutoScrollEditText(Context context) {
/*  98 */     super(context);
/*  99 */     init();
/*     */   }
/*     */   
/*     */   public AutoScrollEditText(Context context, AttributeSet attrs) {
/* 103 */     super(context, attrs);
/* 104 */     init();
/*     */   }
/*     */   
/*     */   public AutoScrollEditText(Context context, AttributeSet attrs, int defStyleAttr) {
/* 108 */     super(context, attrs, defStyleAttr);
/* 109 */     init();
/*     */   }
/*     */   
/*     */   private void init() {
/* 113 */     setFilters(getDefaultInputFilters());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
/* 120 */     InputConnection ic = super.onCreateInputConnection(editorInfo);
/* 121 */     EditorInfoCompat.setContentMimeTypes(editorInfo, new String[] { "image/*" });
/* 122 */     InputConnectionCompat.OnCommitContentListener callback = new InputConnectionCompat.OnCommitContentListener()
/*     */       {
/*     */         public boolean onCommitContent(InputContentInfoCompat inputContentInfo, int flags, Bundle opts) {
/* 125 */           AutoScrollEditText.this.showInvalidInputToast();
/* 126 */           return true;
/*     */         }
/*     */       };
/* 129 */     return InputConnectionCompat.createWrapper(ic, editorInfo, callback);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAutoScrollEditTextListener(AutoScrollEditTextListener listener) {
/* 138 */     this.mListener = listener;
/*     */   }
/*     */   
/*     */   public void setAutoScrollEditTextSpacingListener(AutoScrollEditTextSpacingListener listener) {
/* 142 */     this.mSpacingListener = listener;
/*     */   }
/*     */   
/*     */   public boolean getDynamicLetterSpacingEnabled() {
/* 146 */     return this.mShowSpacingBox;
/*     */   }
/*     */   
/*     */   @TargetApi(21)
/*     */   public void removeSpacingHandle() {
/* 151 */     this.mShowSpacingBox = false;
/* 152 */     PDFViewCtrl pdfViewCtrl = getPdfViewCtrl();
/* 153 */     if (pdfViewCtrl != null && this.mSpacingHandle != null) {
/* 154 */       pdfViewCtrl.removeView((View)this.mSpacingHandle);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @TargetApi(21)
/*     */   public void addLetterSpacingHandle() {
/* 164 */     this.mShowSpacingBox = true;
/*     */ 
/*     */     
/* 167 */     if (null == this.mSpacingHandle) {
/* 168 */       this.mSpacingHandle = new SelectionHandleView(getContext());
/* 169 */       this.mSpacingHandle.setImageResource(R.drawable.ic_fill_and_sign_resizing);
/* 170 */       this.mSpacingHandle.setCustomSize(R.dimen.resize_widget_size);
/*     */     } 
/* 172 */     PDFViewCtrl pdfViewCtrl = getPdfViewCtrl();
/* 173 */     if (pdfViewCtrl != null && this.mSpacingHandle.getParent() == null)
/*     */     {
/*     */       
/* 176 */       pdfViewCtrl.addView((View)this.mSpacingHandle);
/*     */     }
/*     */     
/* 179 */     if (null == this.mPaint) {
/* 180 */       this.mPaint = new Paint();
/* 181 */       this.mPaint.setAntiAlias(true);
/* 182 */       this.mPaint.setColor(-16777216);
/* 183 */       this.mPaint.setStyle(Paint.Style.STROKE);
/* 184 */       this.mPaint.setStrokeJoin(Paint.Join.MITER);
/* 185 */       this.mPaint.setStrokeCap(Paint.Cap.SQUARE);
/* 186 */       this.mPaint.setStrokeWidth(Utils.convDp2Pix(getContext(), 1.0F));
/* 187 */       this.mSelectionHandleRadius = getContext().getResources().getDimensionPixelSize(R.dimen.resize_widget_size_w_margin) / 2;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDefaultRect(@Nullable Rect defaultRect) {
/* 197 */     if (null == defaultRect) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/* 202 */       this.mDefaultRect = Utils.convertFromPageRectToScreenRect(this.mAnnotViewImpl.mPdfViewCtrl, defaultRect, this.mAnnotViewImpl.mPageNum);
/* 203 */     } catch (Exception ex) {
/* 204 */       this.mDefaultRect = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onKeyUp(int keyCode, KeyEvent event) {
/* 214 */     return (this.mListener != null && this.mListener.onKeyUp(keyCode, event));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onKeyPreIme(int keyCode, KeyEvent event) {
/* 223 */     return (this.mListener != null && this.mListener.onKeyPreIme(keyCode, event));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onAttachedToWindow() {
/* 228 */     super.onAttachedToWindow();
/*     */     
/* 230 */     if (this.mAnnotViewImpl != null) {
/* 231 */       this.mAnnotViewImpl.mPt1.set(getLeft(), getTop());
/* 232 */       this.mAnnotViewImpl.mPt2.set(getRight(), getBottom());
/* 233 */       updatePadding(getLeft(), getTop(), getRight(), getBottom());
/*     */     } 
/* 235 */     updateBBox();
/* 236 */     updateResizeHandle();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onScrollChanged(int horiz, int vert, int oldHoriz, int oldVert) {
/* 241 */     super.onScrollChanged(horiz, vert, oldHoriz, oldVert);
/*     */     
/* 243 */     if (this.mAnnotViewImpl != null) {
/* 244 */       this.mAnnotViewImpl.mPt1.set(getScrollX(), getScrollY());
/* 245 */       this.mAnnotViewImpl.mPt2.set((getScrollX() + getWidth()), (getScrollY() + getHeight()));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onTouchEvent(MotionEvent event) {
/* 251 */     boolean result = super.onTouchEvent(event);
/*     */     
/* 253 */     if (this.mSpacingHandle != null) {
/* 254 */       float x = event.getX();
/* 255 */       float y = event.getY();
/*     */       
/* 257 */       int offsetX = 0;
/* 258 */       int offsetY = 0;
/* 259 */       if (this.mViewBounds != null) {
/* 260 */         offsetX = this.mViewBounds.left;
/* 261 */         offsetY = this.mViewBounds.top;
/*     */       } 
/*     */       
/* 264 */       int left = this.mSpacingHandle.getLeft() - offsetX;
/* 265 */       int top = this.mSpacingHandle.getTop() - offsetY;
/* 266 */       int right = this.mSpacingHandle.getRight() - offsetX;
/* 267 */       int bottom = this.mSpacingHandle.getBottom() - offsetY;
/*     */       
/* 269 */       switch (event.getAction()) {
/*     */         case 0:
/* 271 */           if (Utils.isLollipop() && 
/* 272 */             x >= left && x <= right && y >= top && y <= bottom) {
/*     */             
/* 274 */             Paint fakeTextPaint = new Paint((Paint)getPaint());
/* 275 */             fakeTextPaint.setLetterSpacing(0.0F);
/* 276 */             this.mPureTextWidth = getLetterOnlyWidth((TextView)this, fakeTextPaint);
/*     */             
/* 278 */             this.mDownPt = new PointF(x, y);
/*     */           } 
/*     */           break;
/*     */         
/*     */         case 2:
/* 283 */           if (this.mDownPt != null && Utils.isLollipop()) {
/* 284 */             float fullSize = x - (this.mSelectionHandleRadius * 2) - this.mAnnotViewImpl.mPt1.x;
/* 285 */             float spacingSize = fullSize - this.mPureTextWidth;
/*     */             
/* 287 */             float font_sz = this.mTextSize * (float)this.mAnnotViewImpl.mZoom;
/* 288 */             float letterSpacing = spacingSize / font_sz / this.mMaxCharacterCount;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 294 */             setLetterSpacing(Math.max(0.0F, letterSpacing));
/*     */           } 
/*     */           break;
/*     */         case 1:
/* 298 */           if (this.mDownPt != null) {
/* 299 */             requestLayout();
/* 300 */             invalidate();
/* 301 */             if (this.mSpacingListener != null) {
/* 302 */               this.mSpacingListener.onUp();
/*     */             }
/*     */           } 
/* 305 */           this.mDownPt = null;
/*     */           break;
/*     */       } 
/*     */     
/*     */     } 
/* 310 */     if (this.mDownPt != null) {
/* 311 */       return true;
/*     */     }
/* 313 */     if (isEnabled()) {
/* 314 */       return result;
/*     */     }
/* 316 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
/* 322 */     super.onLayout(changed, left, top, right, bottom);
/*     */     
/* 324 */     this.mViewBounds = getViewBounds();
/* 325 */     updateBBox();
/* 326 */     updateResizeHandle();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onDraw(Canvas canvas) {
/* 331 */     if (this.mAnnotViewImpl != null && !isWidget()) {
/* 332 */       DrawingUtils.drawRectangle(canvas, this.mAnnotViewImpl.mPt1, this.mAnnotViewImpl.mPt2, this.mAnnotViewImpl.mThicknessDraw, this.mAnnotViewImpl.mFillColor, this.mAnnotViewImpl.mStrokeColor, this.mAnnotViewImpl.mFillPaint, this.mAnnotViewImpl.mPaint);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 339 */     if (this.mShowSpacingBox) {
/* 340 */       drawResizeBox(canvas);
/*     */     }
/*     */     
/* 343 */     super.onDraw(canvas);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
/* 348 */     super.onTextChanged(text, start, lengthBefore, lengthAfter);
/*     */     
/* 350 */     updateBBox();
/* 351 */     updateResizeHandle();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBackgroundColor(int color) {
/* 356 */     if (this.mAutoResize) {
/*     */       return;
/*     */     }
/*     */     
/* 360 */     super.setBackgroundColor(color);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSuggestionsEnabled() {
/* 365 */     return false;
/*     */   }
/*     */   
/*     */   private static float getLetterOnlyWidth(TextView textView, Paint textPaint) {
/* 369 */     List<CharSequence> contents = getLines(textView);
/* 370 */     float maxWidth = 0.0F;
/* 371 */     for (CharSequence c : contents) {
/* 372 */       String text = c.toString().trim();
/* 373 */       float width = textPaint.measureText(text);
/* 374 */       maxWidth = Math.max(width, maxWidth);
/*     */     } 
/* 376 */     return maxWidth;
/*     */   }
/*     */   
/*     */   private void updateBBox() {
/* 380 */     if (this.mAnnotViewImpl != null && (this.mShowSpacingBox || this.mAutoResize)) {
/* 381 */       PointF bottomRight = this.mAnnotViewImpl.mPt2;
/*     */       
/* 383 */       List<CharSequence> contents = getLines((TextView)this);
/* 384 */       float maxWidth = 0.0F;
/* 385 */       float maxHeight = 0.0F;
/* 386 */       this.mMaxCharacterCount = 0;
/* 387 */       for (CharSequence c : contents) {
/* 388 */         String text = c.toString().trim();
/* 389 */         float width = getPaint().measureText(text);
/* 390 */         maxWidth = Math.max(width, maxWidth);
/* 391 */         Paint.FontMetrics metrics = getPaint().getFontMetrics();
/* 392 */         maxHeight += metrics.bottom - metrics.top;
/* 393 */         this.mMaxCharacterCount = Math.max(text.length(), this.mMaxCharacterCount);
/*     */       } 
/* 395 */       bottomRight.set(this.mAnnotViewImpl.mPt1.x + getPaddingLeft() + maxWidth + getPaddingRight(), this.mAnnotViewImpl.mPt1.y + 
/* 396 */           getPaddingTop() + maxHeight + getPaddingBottom());
/* 397 */       if (this.mAutoResize && this.mDefaultRect != null) {
/*     */         try {
/* 399 */           bottomRight.x = Math.max(bottomRight.x, this.mAnnotViewImpl.mPt1.x + (int)(this.mDefaultRect.getWidth() + 0.5D));
/* 400 */           bottomRight.y = Math.max(bottomRight.y, this.mAnnotViewImpl.mPt1.y + (int)(this.mDefaultRect.getHeight() + 0.5D));
/* 401 */         } catch (Exception exception) {}
/*     */       }
/*     */       
/* 404 */       this.mAnnotViewImpl.mPt2.set(bottomRight);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateResizeHandle() {
/* 409 */     if (this.mSpacingHandle != null && this.mAnnotViewImpl != null) {
/* 410 */       if (this.mMaxCharacterCount > 1) {
/*     */         
/* 412 */         this.mSpacingHandle.setVisibility(0);
/*     */       } else {
/* 414 */         this.mSpacingHandle.setVisibility(8);
/*     */       } 
/* 416 */       int x = Math.round(this.mAnnotViewImpl.mPt2.x) + this.mSelectionHandleRadius;
/* 417 */       int y = Math.round((this.mAnnotViewImpl.mPt2.y - this.mAnnotViewImpl.mPt1.y) / 2.0F);
/*     */       
/* 419 */       int offsetX = 0;
/* 420 */       int offsetY = 0;
/* 421 */       if (this.mViewBounds != null) {
/* 422 */         offsetX += this.mViewBounds.left;
/* 423 */         offsetY += this.mViewBounds.top;
/*     */       } 
/* 425 */       x += offsetX;
/* 426 */       y += offsetY;
/*     */       
/* 428 */       this.mSpacingHandle.layout(x, y - this.mSelectionHandleRadius, x + this.mSelectionHandleRadius * 2, y + this.mSelectionHandleRadius);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void drawResizeBox(Canvas canvas) {
/* 437 */     if (this.mAnnotViewImpl != null) {
/* 438 */       float width = this.mAnnotViewImpl.mPt2.x - this.mAnnotViewImpl.mPt1.x - this.mAnnotViewImpl.mThicknessDraw * 2.0F;
/* 439 */       float eachWidth = width / this.mMaxCharacterCount;
/* 440 */       this.mPaint.setColor(this.mTextColor);
/*     */       
/* 442 */       float x1 = this.mAnnotViewImpl.mPt1.x + this.mAnnotViewImpl.mThicknessDraw;
/* 443 */       float y1 = this.mAnnotViewImpl.mPt1.y + this.mAnnotViewImpl.mThicknessDraw;
/* 444 */       float y2 = this.mAnnotViewImpl.mPt2.y - this.mAnnotViewImpl.mThicknessDraw;
/* 445 */       for (int i = 1; i <= this.mMaxCharacterCount; i++) {
/* 446 */         if (i == 1) {
/* 447 */           this.mTempPt1.set(x1, y1);
/* 448 */           this.mTempPt2.set(this.mTempPt1.x + eachWidth, y2);
/*     */         } else {
/* 450 */           this.mTempPt1.set(this.mTempPt2.x, y1);
/* 451 */           this.mTempPt2.set(this.mTempPt1.x + eachWidth, y2);
/*     */         } 
/* 453 */         DrawingUtils.drawRectangle(canvas, this.mTempPt1, this.mTempPt2, 0.0F, 0, this.mTextColor, this.mAnnotViewImpl.mFillPaint, this.mPaint);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private Rect getViewBounds() {
/* 464 */     PDFViewCtrl pdfViewCtrl = getPdfViewCtrl();
/* 465 */     if (pdfViewCtrl != null) {
/* 466 */       Rect bounds = new Rect();
/*     */       
/* 468 */       getDrawingRect(bounds);
/*     */       
/* 470 */       pdfViewCtrl.offsetDescendantRectToMyCoords((View)this, bounds);
/* 471 */       return bounds;
/*     */     } 
/* 473 */     return null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private PDFViewCtrl getPdfViewCtrl() {
/* 478 */     return (this.mAnnotViewImpl != null) ? this.mAnnotViewImpl.mPdfViewCtrl : null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Rect getBoundingRect() {
/* 483 */     updateBBox();
/* 484 */     if (this.mAnnotViewImpl != null) {
/*     */       try {
/* 486 */         return new Rect(this.mAnnotViewImpl.mPt1.x, this.mAnnotViewImpl.mPt1.y, this.mAnnotViewImpl.mPt2.x, this.mAnnotViewImpl.mPt2.y);
/* 487 */       } catch (Exception exception) {}
/*     */     }
/*     */     
/* 490 */     return null;
/*     */   }
/*     */   
/*     */   static List<CharSequence> getLines(@NonNull TextView view) {
/* 494 */     List<CharSequence> lines = new ArrayList<>();
/* 495 */     Layout layout = view.getLayout();
/*     */     
/* 497 */     if (layout != null) {
/*     */       
/* 499 */       int lineCount = layout.getLineCount();
/*     */ 
/*     */       
/* 502 */       CharSequence text = layout.getText();
/*     */ 
/*     */       
/* 505 */       for (int i = 0, startIndex = 0; i < lineCount; i++) {
/*     */ 
/*     */         
/* 508 */         int endIndex = layout.getLineEnd(i);
/*     */ 
/*     */ 
/*     */         
/* 512 */         lines.add(text.subSequence(startIndex, endIndex));
/*     */ 
/*     */ 
/*     */         
/* 516 */         startIndex = endIndex;
/*     */       } 
/*     */     } 
/* 519 */     return lines;
/*     */   }
/*     */   
/*     */   private boolean isWidget() {
/* 523 */     if (this.mAnnotViewImpl != null) {
/* 524 */       return (this.mAnnotViewImpl.mAnnotStyle.getAnnotType() == 19);
/*     */     }
/* 526 */     return false;
/*     */   }
/*     */   
/*     */   private void updatePadding(int left, int top, int right, int bottom) {
/* 530 */     if (this.mAnnotViewImpl == null || isWidget()) {
/*     */       return;
/*     */     }
/* 533 */     int paddingH = (int)((this.mAnnotViewImpl.mThicknessDraw * 2.0F) + 0.5D);
/* 534 */     int paddingV = (int)((this.mAnnotViewImpl.mThicknessDraw * 2.0F) + 0.5D);
/* 535 */     if (paddingH > (right - left) / 2) {
/* 536 */       paddingH = (int)(this.mAnnotViewImpl.mThicknessDraw + 0.5D);
/*     */     }
/* 538 */     if (paddingV > (bottom - top) / 2) {
/* 539 */       paddingV = (int)(this.mAnnotViewImpl.mThicknessDraw + 0.5D);
/*     */     }
/* 541 */     setPadding(paddingH, paddingV, paddingH, paddingV);
/*     */   }
/*     */   
/*     */   private void updatePadding() {
/* 545 */     updatePadding(getLeft(), getTop(), getRight(), getBottom());
/*     */   }
/*     */   
/*     */   public void setAnnotStyle(AnnotViewImpl annotViewImpl) {
/* 549 */     this.mAnnotViewImpl = annotViewImpl;
/*     */     
/* 551 */     this.mTextSize = this.mAnnotViewImpl.mAnnotStyle.getTextSize();
/* 552 */     this.mTextColor = this.mAnnotViewImpl.mAnnotStyle.getTextColor();
/* 553 */     updateTextColor(this.mTextColor);
/* 554 */     updateTextSize(this.mTextSize);
/*     */     
/* 556 */     this.mAnnotViewImpl.loadFont();
/* 557 */     updateFont(this.mAnnotViewImpl.mAnnotStyle.getFont());
/*     */     
/* 559 */     PDFViewCtrl pdfViewCtrl = getPdfViewCtrl();
/* 560 */     if (pdfViewCtrl != null && pdfViewCtrl.getToolManager() instanceof ToolManager) {
/* 561 */       ToolManager tm = (ToolManager)pdfViewCtrl.getToolManager();
/* 562 */       this.mAutoResize = tm.isAutoResizeFreeText();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setAnnotStyle(PDFViewCtrl pdfViewCtrl, AnnotStyle annotStyle) {
/* 567 */     this.mAnnotViewImpl = new AnnotViewImpl(pdfViewCtrl, annotStyle);
/* 568 */     this.mAnnotViewImpl.setZoom(pdfViewCtrl.getZoom());
/*     */     
/* 570 */     setAnnotStyle(this.mAnnotViewImpl);
/*     */   }
/*     */   
/*     */   public void updateTextColor(int textColor) {
/* 574 */     this.mTextColor = textColor;
/* 575 */     int color = Utils.getPostProcessedColor(this.mAnnotViewImpl.mPdfViewCtrl, this.mTextColor);
/* 576 */     int r = Color.red(color);
/* 577 */     int g = Color.green(color);
/* 578 */     int b = Color.blue(color);
/* 579 */     int opacity = (int)(this.mAnnotViewImpl.mOpacity * 255.0F);
/* 580 */     color = Color.argb(opacity, r, g, b);
/* 581 */     setTextColor(color);
/*     */   }
/*     */   
/*     */   public void updateTextSize(float textSize) {
/* 585 */     this.mTextSize = textSize;
/* 586 */     float font_sz = this.mTextSize * (float)this.mAnnotViewImpl.mZoom;
/* 587 */     setTextSize(0, font_sz);
/*     */   }
/*     */   
/*     */   public void updateColor(int color) {
/* 591 */     this.mAnnotViewImpl.updateColor(color);
/* 592 */     updatePadding();
/* 593 */     invalidate();
/*     */   }
/*     */   
/*     */   public void updateFillColor(int color) {
/* 597 */     this.mAnnotViewImpl.updateFillColor(color);
/* 598 */     invalidate();
/*     */   }
/*     */   
/*     */   public void updateThickness(float thickness) {
/* 602 */     this.mAnnotViewImpl.updateThickness(thickness);
/* 603 */     updatePadding();
/* 604 */     invalidate();
/*     */   }
/*     */   
/*     */   public void updateOpacity(float opacity) {
/* 608 */     this.mAnnotViewImpl.updateOpacity(opacity);
/* 609 */     updateTextColor(this.mTextColor);
/*     */   }
/*     */   
/*     */   public void updateFont(FontResource font) {
/* 613 */     if (null == font || Utils.isNullOrEmpty(font.getFilePath())) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/* 618 */       Typeface typeFace = Typeface.createFromFile(font.getFilePath());
/* 619 */       setTypeface(typeFace);
/* 620 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setZoom(double zoom) {
/* 626 */     this.mAnnotViewImpl.setZoom(zoom);
/*     */     
/* 628 */     updatePadding();
/*     */     
/* 630 */     float font_sz = this.mTextSize * (float)this.mAnnotViewImpl.mZoom;
/* 631 */     setTextSize(0, font_sz);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputFilter[] getDefaultInputFilters() {
/* 638 */     return new InputFilter[] { new EmojiFilter() };
/*     */   } public static interface AutoScrollEditTextListener {
/*     */     boolean onKeyUp(int param1Int, KeyEvent param1KeyEvent); boolean onKeyPreIme(int param1Int, KeyEvent param1KeyEvent); }
/*     */   private void showInvalidInputToast() {
/* 642 */     Toast.makeText(getContext(), R.string.edit_text_invalid_content, 0).show();
/*     */   }
/*     */   
/*     */   public static interface AutoScrollEditTextSpacingListener {
/*     */     void onUp();
/*     */   }
/*     */   
/*     */   private class EmojiFilter implements InputFilter {
/*     */     public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
/* 651 */       for (int i = start; i < end; i++) {
/* 652 */         int type = Character.getType(source.charAt(i));
/* 653 */         if (type == 19 || type == 28) {
/* 654 */           AutoScrollEditText.this.showInvalidInputToast();
/* 655 */           return "";
/*     */         } 
/*     */       } 
/* 658 */       return null;
/*     */     }
/*     */     
/*     */     private EmojiFilter() {}
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\widget\AutoScrollEditText.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */