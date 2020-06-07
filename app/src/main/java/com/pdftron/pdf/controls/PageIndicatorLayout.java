/*     */ package com.pdftron.pdf.controls;
/*     */ 
/*     */ import android.annotation.TargetApi;
/*     */ import android.content.Context;
/*     */ import android.util.AttributeSet;
/*     */ import android.view.Gravity;
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.view.ViewTreeObserver;
/*     */ import android.widget.LinearLayout;
/*     */ import android.widget.ProgressBar;
/*     */ import android.widget.TextView;
/*     */ import androidx.annotation.Nullable;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.dialog.pagelabel.PageLabelUtils;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PageIndicatorLayout
/*     */   extends LinearLayout
/*     */   implements PDFViewCtrl.PageChangeListener, View.OnAttachStateChangeListener
/*     */ {
/*  30 */   private static final String TAG = PageIndicatorLayout.class.getName();
/*     */   private TextView mIndicator;
/*     */   private ProgressBar mSpinner;
/*     */   private PDFViewCtrl mPDFViewCtrl;
/*     */   private View mMainView;
/*     */   private boolean mAutoAdjustPosition = false;
/*     */   private boolean mHasAttachedToWindow = false;
/*     */   private ViewTreeObserver.OnGlobalLayoutListener mPdfViewCtrlLayoutListener;
/*  38 */   private int mGravity = 8388691;
/*  39 */   private int mPdfViewCtrlVisibility = 0;
/*     */ 
/*     */   
/*     */   private OnPDFViewVisibilityChanged mPDFVisibilityChangeListener;
/*     */ 
/*     */   
/*     */   public PageIndicatorLayout(Context context) {
/*  46 */     this(context, (AttributeSet)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PageIndicatorLayout(Context context, @Nullable AttributeSet attrs) {
/*  53 */     this(context, attrs, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PageIndicatorLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
/*  60 */     super(context, attrs, defStyleAttr);
/*  61 */     init();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @TargetApi(21)
/*     */   public PageIndicatorLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
/*  69 */     super(context, attrs, defStyleAttr, defStyleRes);
/*  70 */     init();
/*     */   }
/*     */   
/*     */   private void init() {
/*  74 */     this.mMainView = LayoutInflater.from(getContext()).inflate(R.layout.view_page_indicator, (ViewGroup)this);
/*  75 */     this.mIndicator = (TextView)findViewById(R.id.page_number_indicator_all_pages);
/*  76 */     this.mSpinner = (ProgressBar)findViewById(R.id.page_number_indicator_spinner);
/*     */     
/*  78 */     setVisibility(8);
/*  79 */     setClickable(false);
/*  80 */     if (Utils.isJellyBeanMR1()) {
/*  81 */       this.mIndicator.setTextDirection(3);
/*     */     }
/*  83 */     this.mPdfViewCtrlLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener()
/*     */       {
/*     */         public void onGlobalLayout() {
/*  86 */           if (PageIndicatorLayout.this.mPDFViewCtrl == null) {
/*     */             return;
/*     */           }
/*  89 */           if (PageIndicatorLayout.this.mPDFViewCtrl.getVisibility() != PageIndicatorLayout.this.mPdfViewCtrlVisibility && PageIndicatorLayout.this.mPDFVisibilityChangeListener != null) {
/*     */             
/*  91 */             PageIndicatorLayout.this.mPDFVisibilityChangeListener.onPDFViewVisibilityChanged(PageIndicatorLayout.this.mPdfViewCtrlVisibility, PageIndicatorLayout.this.mPDFViewCtrl.getVisibility());
/*  92 */             PageIndicatorLayout.this.mPdfViewCtrlVisibility = PageIndicatorLayout.this.mPDFViewCtrl.getVisibility();
/*     */           } 
/*     */         }
/*     */       };
/*     */   }
/*     */   public static interface OnPDFViewVisibilityChanged {
/*     */     void onPDFViewVisibilityChanged(int param1Int1, int param1Int2); }
/*     */   private void addPdfViewCtrlListeners() {
/* 100 */     if (this.mPDFViewCtrl != null) {
/* 101 */       this.mPDFViewCtrl.addPageChangeListener(this);
/* 102 */       this.mPdfViewCtrlVisibility = this.mPDFViewCtrl.getVisibility();
/* 103 */       this.mPDFViewCtrl.getViewTreeObserver().addOnGlobalLayoutListener(this.mPdfViewCtrlLayoutListener);
/* 104 */       this.mPDFViewCtrl.addOnAttachStateChangeListener(this);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPdfViewCtrl(PDFViewCtrl pdfViewCtrl) {
/* 114 */     this.mPDFViewCtrl = pdfViewCtrl;
/* 115 */     if (this.mHasAttachedToWindow) {
/* 116 */       autoAdjustPosition();
/* 117 */       addPdfViewCtrlListeners();
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
/*     */ 
/*     */   
/*     */   protected void onAttachedToWindow() {
/* 133 */     super.onAttachedToWindow();
/* 134 */     autoAdjustPosition();
/* 135 */     addPdfViewCtrlListeners();
/* 136 */     this.mHasAttachedToWindow = true;
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
/*     */   protected void onDetachedFromWindow() {
/* 148 */     super.onDetachedFromWindow();
/* 149 */     if (this.mPDFViewCtrl != null) {
/* 150 */       this.mPDFViewCtrl.removePageChangeListener(this);
/* 151 */       this.mPDFViewCtrl.getViewTreeObserver().removeOnGlobalLayoutListener(this.mPdfViewCtrlLayoutListener);
/* 152 */       this.mPDFViewCtrl.removeOnAttachStateChangeListener(this);
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
/*     */   public void onPageChange(int old_page, int cur_page, PDFViewCtrl.PageChangeState state) {
/* 166 */     String pageLabel = PageLabelUtils.getPageNumberIndicator(this.mPDFViewCtrl, cur_page);
/* 167 */     this.mIndicator.setText(pageLabel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextView getIndicator() {
/* 176 */     return this.mIndicator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ProgressBar getSpinner() {
/* 185 */     return this.mSpinner;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAutoAdjustPosition(boolean autoAdjust) {
/* 194 */     this.mAutoAdjustPosition = autoAdjust;
/*     */   }
/*     */   
/*     */   private void autoAdjustPosition() {
/* 198 */     if (this.mPDFViewCtrl == null || !this.mAutoAdjustPosition) {
/*     */       return;
/*     */     }
/* 201 */     int[] position = calculateAutoAdjustPosition();
/* 202 */     setX(position[0]);
/* 203 */     setY(position[1]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] calculateAutoAdjustPosition() {
/* 212 */     int y, result[] = new int[2];
/* 213 */     if (this.mMainView.getLayoutParams() == null || !(this.mMainView.getLayoutParams() instanceof MarginLayoutParams)) {
/* 214 */       return result;
/*     */     }
/*     */     
/* 217 */     measure(0, 0);
/* 218 */     MarginLayoutParams mlp = (MarginLayoutParams)this.mMainView.getLayoutParams();
/*     */ 
/*     */ 
/*     */     
/* 222 */     int verticalGravity = this.mGravity & 0x70;
/* 223 */     switch (verticalGravity) {
/*     */       case 48:
/* 225 */         y = this.mPDFViewCtrl.getTop() + mlp.topMargin;
/*     */         break;
/*     */       case 16:
/* 228 */         y = this.mPDFViewCtrl.getTop() + this.mPDFViewCtrl.getHeight() / 2 - getMeasuredHeight() / 2 + mlp.topMargin;
/*     */         break;
/*     */       default:
/* 231 */         y = this.mPDFViewCtrl.getBottom() - getMeasuredHeight() - mlp.bottomMargin;
/*     */         break;
/*     */     } 
/*     */     
/* 235 */     result[1] = y;
/*     */ 
/*     */     
/* 238 */     int gravity = Utils.isJellyBeanMR1() ? Gravity.getAbsoluteGravity(this.mGravity, getLayoutDirection()) : this.mGravity;
/* 239 */     int horizontalGravity = gravity & 0x7;
/* 240 */     switch (horizontalGravity)
/*     */     { case 5:
/* 242 */         x = this.mPDFViewCtrl.getRight() - getMeasuredWidth() - mlp.leftMargin - mlp.rightMargin;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 252 */         result[0] = x;
/* 253 */         return result;case 1: x = this.mPDFViewCtrl.getLeft() + this.mPDFViewCtrl.getWidth() / 2 - getMeasuredWidth() / 2 + mlp.leftMargin; result[0] = x; return result; }  int x = this.mPDFViewCtrl.getLeft() + mlp.leftMargin; result[0] = x; return result;
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
/*     */   
/*     */   public void setGravity(int gravity) {
/* 268 */     super.setGravity(gravity);
/* 269 */     this.mGravity = gravity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOnPdfViewCtrlVisibilityChangeListener(OnPDFViewVisibilityChanged listener) {
/* 278 */     this.mPDFVisibilityChangeListener = listener;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onViewAttachedToWindow(View v) {
/* 283 */     if (this.mPDFVisibilityChangeListener != null) {
/* 284 */       this.mPDFVisibilityChangeListener.onPDFViewVisibilityChanged(8, this.mPDFViewCtrl.getVisibility());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onViewDetachedFromWindow(View v) {
/* 290 */     if (this.mPDFVisibilityChangeListener != null)
/* 291 */       this.mPDFVisibilityChangeListener.onPDFViewVisibilityChanged(this.mPDFViewCtrl.getVisibility(), 8); 
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\controls\PageIndicatorLayout.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */