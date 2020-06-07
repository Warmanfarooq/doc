/*     */ package com.pdftron.pdf.widget;
/*     */ 
/*     */ import android.animation.TimeInterpolator;
/*     */ import android.content.Context;
/*     */ import android.graphics.Bitmap;
/*     */ import android.graphics.Canvas;
/*     */ import android.graphics.PointF;
/*     */ import android.graphics.RectF;
/*     */ import android.util.AttributeSet;
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.view.animation.AccelerateInterpolator;
/*     */ import android.view.animation.DecelerateInterpolator;
/*     */ import android.widget.RelativeLayout;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import com.pdftron.pdf.CurvePainter;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.model.AnnotStyle;
/*     */ import com.pdftron.pdf.model.FontResource;
/*     */ import com.pdftron.pdf.model.RotateInfo;
/*     */ import com.pdftron.pdf.model.RulerItem;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.utils.DrawingUtils;
/*     */ import com.pdftron.pdf.utils.InlineEditText;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AnnotView
/*     */   extends RelativeLayout
/*     */ {
/*     */   private static final boolean sUsePlatformRichFreeTextBox = false;
/*     */   private ViewGroup mView;
/*     */   private AnnotDrawingView mDrawingView;
/*     */   private InertRichEditor mWebView;
/*     */   private InlineEditText mSpacingEditor;
/*     */   private AnnotViewImpl mAnnotViewImpl;
/*     */   private boolean mDelayViewRemoval;
/*     */   private long mCurvePainterId;
/*     */   private SelectionHandleView mTopLeft;
/*     */   private SelectionHandleView mTopMiddle;
/*     */   private SelectionHandleView mTopRight;
/*     */   private SelectionHandleView mMiddleLeft;
/*     */   private SelectionHandleView mMiddleRight;
/*     */   private SelectionHandleView mBottomLeft;
/*     */   private SelectionHandleView mBottomMiddle;
/*     */   private SelectionHandleView mBottomRight;
/*     */   private ArrayList<SelectionHandleView> mSelectionHandleViews;
/*     */   private int mSelectionHandleRadius;
/*     */   private SelectionHandleView mActiveHandle;
/*     */   
/*     */   public AnnotView(Context context) {
/*  75 */     this(context, (AttributeSet)null);
/*     */   }
/*     */   
/*     */   public AnnotView(Context context, @Nullable AttributeSet attrs) {
/*  79 */     this(context, attrs, 0);
/*     */   }
/*     */   
/*     */   public AnnotView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
/*  83 */     super(context, attrs, defStyleAttr);
/*  84 */     init(context);
/*     */   }
/*     */   
/*     */   public void setCanDraw(boolean canDraw) {
/*  88 */     this.mDrawingView.setCanDraw(canDraw);
/*     */   }
/*     */   
/*     */   public boolean getCanDraw() {
/*  92 */     return this.mDrawingView.getCanDraw();
/*     */   }
/*     */   
/*     */   public long getCurvePainterId() {
/*  96 */     return this.mCurvePainterId;
/*     */   }
/*     */   
/*     */   public void setPage(int pageNum) {
/* 100 */     if (this.mAnnotViewImpl != null) {
/* 101 */       this.mAnnotViewImpl.mPageNum = pageNum;
/*     */     }
/*     */   }
/*     */   
/*     */   public void setAnnotStyle(PDFViewCtrl pdfViewCtrl, AnnotStyle annotStyle) {
/* 106 */     this.mAnnotViewImpl.setAnnotStyle(pdfViewCtrl, annotStyle);
/* 107 */     this.mDrawingView.setAnnotStyle(this.mAnnotViewImpl);
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
/* 118 */     if (annotStyle.getAnnotType() == 1010) {
/* 119 */       this.mDrawingView.setVisibility(8);
/* 120 */       this.mWebView.setVisibility(8);
/*     */     } else {
/* 122 */       this.mDrawingView.setVisibility(0);
/* 123 */       this.mWebView.setVisibility(8);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setInlineEditText(@NonNull InlineEditText inlineEditText) {
/* 128 */     this.mSpacingEditor = inlineEditText;
/* 129 */     this.mSpacingEditor.getEditText().setInputType(0);
/* 130 */     this.mSpacingEditor.getEditText().setEnabled(false);
/* 131 */     this.mSpacingEditor.getEditText().setFocusable(false);
/* 132 */     this.mSpacingEditor.getEditText().setFocusableInTouchMode(false);
/* 133 */     this.mSpacingEditor.getEditText().setCursorVisible(false);
/*     */     
/* 135 */     if (this.mAnnotViewImpl != null && this.mAnnotViewImpl.mAnnotStyle != null) {
/* 136 */       this.mSpacingEditor.getEditText().setText(this.mAnnotViewImpl.mAnnotStyle.getTextContent());
/*     */       
/* 138 */       if (Utils.isLollipop()) {
/* 139 */         this.mSpacingEditor.getEditText().setLetterSpacing(this.mAnnotViewImpl.mAnnotStyle.getLetterSpacing());
/* 140 */         this.mSpacingEditor.getEditText().addLetterSpacingHandle();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setCurvePainter(long id, CurvePainter curvePainter) {
/* 146 */     this.mCurvePainterId = id;
/* 147 */     this.mDrawingView.setCurvePainter(curvePainter);
/*     */   }
/*     */   
/*     */   public void setAnnotBitmap(Bitmap bitmap) {
/* 151 */     this.mDrawingView.setAnnotBitmap(bitmap);
/*     */   }
/*     */   
/*     */   public void prepareRemoval() {
/* 155 */     if (this.mSpacingEditor != null) {
/* 156 */       this.mSpacingEditor.close(true);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setDelayViewRemoval(boolean delayViewRemoval) {
/* 161 */     this.mDelayViewRemoval = delayViewRemoval;
/* 162 */     if (delayViewRemoval) {
/* 163 */       this.mAnnotViewImpl.removeCtrlPts();
/* 164 */       invalidate();
/*     */       
/* 166 */       setSelectionHandleVisible(false);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isDelayViewRemoval() {
/* 171 */     return this.mDelayViewRemoval;
/*     */   }
/*     */   
/*     */   public AnnotDrawingView getDrawingView() {
/* 175 */     return this.mDrawingView;
/*     */   }
/*     */   
/*     */   public InertRichEditor getRichEditor() {
/* 179 */     return this.mWebView;
/*     */   }
/*     */   
/*     */   public AutoScrollEditText getTextView() {
/* 183 */     return this.mSpacingEditor.getEditText();
/*     */   }
/*     */   
/*     */   public void setZoom(double zoom) {
/* 187 */     this.mDrawingView.setZoom(zoom);
/* 188 */     if (this.mSpacingEditor != null) {
/* 189 */       this.mSpacingEditor.getEditText().setZoom(zoom);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setPageNum(int pageNum) {
/* 194 */     this.mDrawingView.setPageNum(pageNum);
/*     */   }
/*     */   
/*     */   public void setHasPermission(boolean hasPermission) {
/* 198 */     this.mAnnotViewImpl.mHasSelectionPermission = hasPermission;
/*     */   }
/*     */   
/*     */   public void setActiveHandle(int which) {
/* 202 */     if (this.mAnnotViewImpl.isAnnotEditLine() || this.mAnnotViewImpl.isAnnotEditAdvancedShape()) {
/* 203 */       if (which >= 0 && this.mSelectionHandleViews != null && which < this.mSelectionHandleViews.size()) {
/* 204 */         this.mActiveHandle = this.mSelectionHandleViews.get(which);
/*     */       } else {
/* 206 */         this.mActiveHandle = null;
/*     */       } 
/*     */     } else {
/* 209 */       switch (which) {
/*     */         case 0:
/* 211 */           this.mActiveHandle = this.mBottomLeft;
/*     */           break;
/*     */         case 6:
/* 214 */           this.mActiveHandle = this.mBottomMiddle;
/*     */           break;
/*     */         case 1:
/* 217 */           this.mActiveHandle = this.mBottomRight;
/*     */           break;
/*     */         case 4:
/* 220 */           this.mActiveHandle = this.mMiddleRight;
/*     */           break;
/*     */         case 2:
/* 223 */           this.mActiveHandle = this.mTopRight;
/*     */           break;
/*     */         case 5:
/* 226 */           this.mActiveHandle = this.mTopMiddle;
/*     */           break;
/*     */         case 3:
/* 229 */           this.mActiveHandle = this.mTopLeft;
/*     */           break;
/*     */         case 7:
/* 232 */           this.mActiveHandle = this.mMiddleLeft;
/*     */           break;
/*     */         default:
/* 235 */           this.mActiveHandle = null;
/*     */           break;
/*     */       } 
/*     */     } 
/* 239 */     animateActiveHandle();
/*     */   }
/*     */   
/*     */   public void setCtrlPts(PointF[] pts) {
/* 243 */     this.mAnnotViewImpl.mCtrlPts = pts;
/*     */     
/* 245 */     layoutSelectionHandle(pts);
/*     */   }
/*     */   
/*     */   public void setAnnotRect(@Nullable RectF rect) {
/* 249 */     if (null == rect) {
/*     */       return;
/*     */     }
/* 252 */     this.mAnnotViewImpl.mAnnotRectF.set(rect);
/*     */     
/* 254 */     this.mAnnotViewImpl.mBBox.set(rect);
/* 255 */     this.mAnnotViewImpl.mPt1.set(rect.left, rect.top);
/* 256 */     this.mAnnotViewImpl.mPt2.set(rect.right, rect.bottom);
/* 257 */     this.mDrawingView.setAnnotRect(rect);
/*     */     
/* 259 */     if (this.mSpacingEditor != null) {
/*     */ 
/*     */       
/* 262 */       this.mAnnotViewImpl.mPt1.set(0.0F, 0.0F);
/* 263 */       this.mAnnotViewImpl.mPt2.set(rect.width(), rect.height());
/*     */     } 
/*     */     
/* 266 */     layoutTextViews();
/*     */   }
/*     */   
/*     */   public void setVertices(PointF... points) {
/* 270 */     if (this.mAnnotViewImpl.mAnnotStyle.getAnnotType() == 1012) {
/*     */ 
/*     */       
/* 273 */       if (this.mAnnotViewImpl.mVertices.isEmpty()) {
/* 274 */         this.mAnnotViewImpl.setVertices(points);
/*     */       }
/*     */     }
/* 277 */     else if (this.mAnnotViewImpl.mVertices.size() == 2 && points.length == 2) {
/* 278 */       this.mAnnotViewImpl.mVertices.set(0, points[0]);
/* 279 */       this.mAnnotViewImpl.mVertices.set(1, points[1]);
/*     */     } else {
/* 281 */       this.mAnnotViewImpl.setVertices(points);
/*     */     } 
/*     */     
/* 284 */     layoutSelectionHandle(points);
/*     */   }
/*     */   
/*     */   public void updateVertices(int index, PointF point) {
/* 288 */     if (index >= this.mAnnotViewImpl.mVertices.size()) {
/*     */       return;
/*     */     }
/* 291 */     this.mAnnotViewImpl.mVertices.set(index, point);
/*     */     
/* 293 */     layoutSelectionHandle(this.mAnnotViewImpl.mVertices.<PointF>toArray(new PointF[0]));
/*     */   }
/*     */   
/*     */   public RotateInfo handleRotation(PointF downPt, PointF movePt, boolean done) {
/* 297 */     if (this.mTopLeft.getVisibility() == 0) {
/* 298 */       setSelectionHandleVisible(false);
/*     */     }
/* 300 */     return this.mDrawingView.handleRotation(downPt, movePt, done);
/*     */   }
/*     */   
/*     */   public void snapToPerfectShape(@Nullable SnapMode mode) {
/* 304 */     this.mAnnotViewImpl.mSnapMode = mode;
/* 305 */     invalidate();
/*     */   }
/*     */   
/*     */   public void updateTextColor(int textColor) {
/* 309 */     if (this.mSpacingEditor != null) {
/* 310 */       this.mSpacingEditor.getEditText().updateTextColor(textColor);
/*     */     }
/*     */   }
/*     */   
/*     */   public void updateTextSize(float textSize) {
/* 315 */     if (this.mSpacingEditor != null) {
/* 316 */       this.mSpacingEditor.getEditText().updateTextSize(textSize);
/*     */     }
/*     */   }
/*     */   
/*     */   public void updateColor(int color) {
/* 321 */     this.mDrawingView.updateColor(color);
/* 322 */     if (this.mSpacingEditor != null) {
/* 323 */       this.mSpacingEditor.getEditText().updateColor(color);
/*     */     }
/*     */   }
/*     */   
/*     */   public void updateFillColor(int color) {
/* 328 */     this.mDrawingView.updateFillColor(color);
/* 329 */     if (this.mSpacingEditor != null) {
/* 330 */       this.mSpacingEditor.getEditText().updateFillColor(color);
/*     */     }
/*     */   }
/*     */   
/*     */   public void updateThickness(float thickness) {
/* 335 */     this.mDrawingView.updateThickness(thickness);
/* 336 */     if (this.mSpacingEditor != null) {
/* 337 */       this.mSpacingEditor.getEditText().updateThickness(thickness);
/*     */     }
/*     */   }
/*     */   
/*     */   public void updateOpacity(float opacity) {
/* 342 */     this.mDrawingView.updateOpacity(opacity);
/* 343 */     if (this.mSpacingEditor != null) {
/* 344 */       this.mSpacingEditor.getEditText().updateOpacity(opacity);
/*     */     }
/*     */   }
/*     */   
/*     */   public void updateIcon(String icon) {
/* 349 */     this.mDrawingView.updateIcon(icon);
/*     */   }
/*     */   
/*     */   public void updateFont(FontResource font) {
/* 353 */     if (this.mSpacingEditor != null) {
/* 354 */       this.mSpacingEditor.getEditText().updateFont(font);
/*     */     }
/*     */   }
/*     */   
/*     */   public void updateRulerItem(RulerItem rulerItem) {
/* 359 */     this.mDrawingView.updateRulerItem(rulerItem);
/*     */   }
/*     */   
/*     */   private void init(Context context) {
/* 363 */     this.mAnnotViewImpl = new AnnotViewImpl(context);
/*     */     
/* 365 */     this.mView = (ViewGroup)LayoutInflater.from(getContext()).inflate(R.layout.annot_view_layout, null);
/* 366 */     this.mDrawingView = (AnnotDrawingView)this.mView.findViewById(R.id.drawing_view);
/* 367 */     this.mWebView = (InertRichEditor)this.mView.findViewById(R.id.web_view);
/* 368 */     this.mWebView.setPadding(0, 0, 0, 0);
/* 369 */     this.mWebView.setEditorBackgroundColor(0);
/*     */     
/* 371 */     this.mSelectionHandleRadius = getResources().getDimensionPixelSize(R.dimen.selection_widget_size_w_margin) / 2;
/*     */     
/* 373 */     addView((View)this.mView);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onDraw(Canvas canvas) {
/* 378 */     super.onDraw(canvas);
/*     */     
/* 380 */     if (this.mWebView.getVisibility() == 0 || this.mSpacingEditor != null) {
/*     */       
/* 382 */       drawSelectionBox(canvas);
/*     */       
/* 384 */       if (this.mAnnotViewImpl.mSnapMode != null) {
/* 385 */         DrawingUtils.drawGuideline(this.mAnnotViewImpl.mSnapMode, this.mAnnotViewImpl.mGuidelinExtend, canvas, this.mAnnotViewImpl.mBBox, this.mAnnotViewImpl.mGuidelinePath, this.mAnnotViewImpl.mGuidelinePaint);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onLayout(boolean changed, int l, int t, int r, int b) {
/* 393 */     super.onLayout(changed, l, t, r, b);
/*     */     
/* 395 */     this.mView.layout(0, 0, r - l, b - t);
/* 396 */     this.mDrawingView.layout(0, 0, r - l, b - t);
/*     */     
/* 398 */     layoutTextViews();
/*     */   }
/*     */ 
/*     */   
/*     */   public void invalidate() {
/* 403 */     super.invalidate();
/*     */     
/* 405 */     if (0 == this.mWebView.getVisibility()) {
/* 406 */       this.mWebView.invalidate();
/*     */     }
/* 408 */     if (this.mSpacingEditor != null) {
/* 409 */       this.mSpacingEditor.getEditText().invalidate();
/*     */     }
/* 411 */     this.mDrawingView.invalidate();
/*     */   }
/*     */   
/*     */   private void drawSelectionBox(Canvas canvas) {
/* 415 */     if (!this.mAnnotViewImpl.mCanDrawCtrlPts) {
/*     */       return;
/*     */     }
/* 418 */     if (!this.mAnnotViewImpl.mHasSelectionPermission) {
/*     */       return;
/*     */     }
/* 421 */     float left = (this.mAnnotViewImpl.mCtrlPts[3]).x;
/* 422 */     float top = (this.mAnnotViewImpl.mCtrlPts[3]).y;
/* 423 */     float right = (this.mAnnotViewImpl.mCtrlPts[1]).x;
/* 424 */     float bottom = (this.mAnnotViewImpl.mCtrlPts[1]).y;
/* 425 */     DrawingUtils.drawSelectionBox(this.mAnnotViewImpl.mCtrlPtsPaint, getContext(), canvas, left, top, right, bottom, this.mAnnotViewImpl.mHasSelectionPermission);
/*     */   }
/*     */ 
/*     */   
/*     */   private void layoutTextViews() {
/* 430 */     if (this.mSpacingEditor != null && this.mAnnotViewImpl != null && 
/* 431 */       this.mSpacingEditor.getEditText().getDynamicLetterSpacingEnabled())
/*     */     {
/* 433 */       this.mSpacingEditor.getEditor().setScreenPosition(this.mAnnotViewImpl.mAnnotRectF.left, this.mAnnotViewImpl.mAnnotRectF.top, this.mAnnotViewImpl.mPageNum);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 439 */     if (0 == this.mWebView.getVisibility()) {
/* 440 */       this.mWebView.layout(Math.round(this.mAnnotViewImpl.mAnnotRectF.left), 
/* 441 */           Math.round(this.mAnnotViewImpl.mAnnotRectF.top), 
/* 442 */           Math.round(this.mAnnotViewImpl.mAnnotRectF.right), 
/* 443 */           Math.round(this.mAnnotViewImpl.mAnnotRectF.bottom));
/*     */     }
/*     */   }
/*     */   
/*     */   public void layoutSelectionHandle(PointF[] pts) {
/* 448 */     if (pts == null) {
/*     */       return;
/*     */     }
/* 451 */     if (null == this.mAnnotViewImpl) {
/*     */       return;
/*     */     }
/* 454 */     if (!this.mAnnotViewImpl.mCanDrawCtrlPts) {
/*     */       return;
/*     */     }
/* 457 */     if (!this.mAnnotViewImpl.mHasSelectionPermission) {
/*     */       return;
/*     */     }
/* 460 */     if (!this.mAnnotViewImpl.isAnnotResizable()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 465 */     if (this.mAnnotViewImpl.isAnnotEditLine() || this.mAnnotViewImpl.isAnnotEditAdvancedShape()) {
/* 466 */       selectionHandleAnnotEditAdvancedShape(pts);
/*     */     } else {
/* 468 */       selectionHandleAnnotEdit(pts);
/*     */     } 
/* 470 */     invalidate();
/*     */   }
/*     */   
/*     */   private void selectionHandleAnnotEditAdvancedShape(PointF[] pts) {
/* 474 */     int len = pts.length;
/* 475 */     if (null == this.mSelectionHandleViews) {
/* 476 */       this.mSelectionHandleViews = new ArrayList<>(len);
/* 477 */       for (int j = 0; j < len; j++) {
/* 478 */         SelectionHandleView v = new SelectionHandleView(getContext());
/* 479 */         this.mView.addView((View)v);
/* 480 */         this.mSelectionHandleViews.add(v);
/*     */       } 
/*     */     } 
/* 483 */     for (int i = 0; i < len; i++) {
/* 484 */       SelectionHandleView v = this.mSelectionHandleViews.get(i);
/* 485 */       if (this.mAnnotViewImpl.isCallout() && i == 10) {
/*     */         
/* 487 */         v.setVisibility(8);
/*     */       } else {
/*     */         
/* 490 */         PointF p = pts[i];
/* 491 */         if (p != null) {
/* 492 */           v.setVisibility(0);
/* 493 */           v.layout((int)(p.x + 0.5D) - this.mSelectionHandleRadius, (int)(p.y + 0.5D) - this.mSelectionHandleRadius, (int)(p.x + 0.5D) + this.mSelectionHandleRadius, (int)(p.y + 0.5D) + this.mSelectionHandleRadius);
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 498 */           v.setVisibility(8);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   private void addSelectionWidget() {
/* 504 */     if (null == this.mSelectionHandleViews) {
/* 505 */       this.mSelectionHandleViews = new ArrayList<>(8);
/*     */     }
/* 507 */     if (null == this.mTopLeft) {
/* 508 */       this.mTopLeft = new SelectionHandleView(getContext());
/* 509 */       this.mView.addView((View)this.mTopLeft);
/* 510 */       this.mSelectionHandleViews.add(this.mTopLeft);
/*     */     } 
/* 512 */     if (null == this.mTopMiddle) {
/* 513 */       this.mTopMiddle = new SelectionHandleView(getContext());
/* 514 */       addView((View)this.mTopMiddle);
/* 515 */       this.mSelectionHandleViews.add(this.mTopMiddle);
/*     */     } 
/* 517 */     if (null == this.mTopRight) {
/* 518 */       this.mTopRight = new SelectionHandleView(getContext());
/* 519 */       addView((View)this.mTopRight);
/* 520 */       this.mSelectionHandleViews.add(this.mTopRight);
/*     */     } 
/* 522 */     if (null == this.mMiddleLeft) {
/* 523 */       this.mMiddleLeft = new SelectionHandleView(getContext());
/* 524 */       addView((View)this.mMiddleLeft);
/* 525 */       this.mSelectionHandleViews.add(this.mMiddleLeft);
/*     */     } 
/* 527 */     if (null == this.mMiddleRight) {
/* 528 */       this.mMiddleRight = new SelectionHandleView(getContext());
/* 529 */       addView((View)this.mMiddleRight);
/* 530 */       this.mSelectionHandleViews.add(this.mMiddleRight);
/*     */     } 
/* 532 */     if (null == this.mBottomLeft) {
/* 533 */       this.mBottomLeft = new SelectionHandleView(getContext());
/* 534 */       addView((View)this.mBottomLeft);
/* 535 */       this.mSelectionHandleViews.add(this.mBottomLeft);
/*     */     } 
/* 537 */     if (null == this.mBottomMiddle) {
/* 538 */       this.mBottomMiddle = new SelectionHandleView(getContext());
/* 539 */       addView((View)this.mBottomMiddle);
/* 540 */       this.mSelectionHandleViews.add(this.mBottomMiddle);
/*     */     } 
/* 542 */     if (null == this.mBottomRight) {
/* 543 */       this.mBottomRight = new SelectionHandleView(getContext());
/* 544 */       addView((View)this.mBottomRight);
/* 545 */       this.mSelectionHandleViews.add(this.mBottomRight);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void selectionHandleAnnotEdit(PointF[] pts) {
/* 550 */     addSelectionWidget();
/*     */     
/* 552 */     if (this.mAnnotViewImpl.isStamp()) {
/* 553 */       this.mMiddleLeft.setVisibility(8);
/* 554 */       this.mMiddleRight.setVisibility(8);
/* 555 */       this.mTopMiddle.setVisibility(8);
/* 556 */       this.mBottomMiddle.setVisibility(8);
/*     */     } 
/*     */     
/* 559 */     PointF pt1 = pts[3];
/* 560 */     PointF pt2 = pts[1];
/* 561 */     PointF midH = pts[6];
/* 562 */     PointF midV = pts[7];
/*     */     
/* 564 */     int left = (int)(Math.min(pt1.x, pt2.x) + 0.5D);
/* 565 */     int right = (int)(Math.max(pt1.x, pt2.x) + 0.5D);
/* 566 */     int top = (int)(Math.min(pt1.y, pt2.y) + 0.5D);
/* 567 */     int bottom = (int)(Math.max(pt1.y, pt2.y) + 0.5D);
/*     */     
/* 569 */     int middle_x = (int)(midH.x + 0.5D);
/* 570 */     int middle_y = (int)(midV.y + 0.5D);
/*     */     
/* 572 */     this.mTopLeft.layout(left - this.mSelectionHandleRadius, top - this.mSelectionHandleRadius, left + this.mSelectionHandleRadius, top + this.mSelectionHandleRadius);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 577 */     this.mTopMiddle.layout(middle_x - this.mSelectionHandleRadius, top - this.mSelectionHandleRadius, middle_x + this.mSelectionHandleRadius, top + this.mSelectionHandleRadius);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 582 */     this.mTopRight.layout(right - this.mSelectionHandleRadius, top - this.mSelectionHandleRadius, right + this.mSelectionHandleRadius, top + this.mSelectionHandleRadius);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 587 */     this.mMiddleLeft.layout(left - this.mSelectionHandleRadius, middle_y - this.mSelectionHandleRadius, left + this.mSelectionHandleRadius, middle_y + this.mSelectionHandleRadius);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 592 */     this.mMiddleRight.layout(right - this.mSelectionHandleRadius, middle_y - this.mSelectionHandleRadius, right + this.mSelectionHandleRadius, middle_y + this.mSelectionHandleRadius);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 597 */     this.mBottomLeft.layout(left - this.mSelectionHandleRadius, bottom - this.mSelectionHandleRadius, left + this.mSelectionHandleRadius, bottom + this.mSelectionHandleRadius);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 602 */     this.mBottomMiddle.layout(middle_x - this.mSelectionHandleRadius, bottom - this.mSelectionHandleRadius, middle_x + this.mSelectionHandleRadius, bottom + this.mSelectionHandleRadius);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 607 */     this.mBottomRight.layout(right - this.mSelectionHandleRadius, bottom - this.mSelectionHandleRadius, right + this.mSelectionHandleRadius, bottom + this.mSelectionHandleRadius);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSelectionHandleVisible(boolean visible) {
/* 614 */     if (this.mSelectionHandleViews != null) {
/* 615 */       for (SelectionHandleView v : this.mSelectionHandleViews) {
/* 616 */         if (v != null) {
/* 617 */           v.setVisibility(visible ? 0 : 8);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void animateActiveHandle() {
/* 624 */     if (this.mSelectionHandleViews != null) {
/* 625 */       for (SelectionHandleView v : this.mSelectionHandleViews) {
/* 626 */         if (v != null) {
/* 627 */           if (this.mActiveHandle != null) {
/* 628 */             if (v == this.mActiveHandle) {
/*     */               
/* 630 */               v.animate()
/* 631 */                 .scaleX(1.5F)
/* 632 */                 .scaleY(1.5F)
/* 633 */                 .setInterpolator((TimeInterpolator)new DecelerateInterpolator())
/* 634 */                 .setDuration(50L)
/* 635 */                 .start();
/*     */               continue;
/*     */             } 
/* 638 */             v.animate()
/* 639 */               .scaleX(0.5F)
/* 640 */               .scaleY(0.5F)
/* 641 */               .setInterpolator((TimeInterpolator)new AccelerateInterpolator())
/* 642 */               .setDuration(50L)
/* 643 */               .start();
/*     */             continue;
/*     */           } 
/* 646 */           v.animate()
/* 647 */             .scaleX(1.0F)
/* 648 */             .scaleY(1.0F)
/* 649 */             .setInterpolator((TimeInterpolator)new AccelerateInterpolator())
/* 650 */             .setDuration(50L)
/* 651 */             .start();
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public enum SnapMode
/*     */   {
/* 659 */     HORIZONTAL,
/* 660 */     VERTICAL,
/* 661 */     ASPECT_RATIO_L,
/* 662 */     ASPECT_RATIO_R;
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\widget\AnnotView.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */