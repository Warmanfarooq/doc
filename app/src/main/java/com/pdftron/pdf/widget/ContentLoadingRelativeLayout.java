/*     */ package com.pdftron.pdf.widget;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.util.AttributeSet;
/*     */ import android.view.animation.AnimationUtils;
/*     */ import android.widget.RelativeLayout;
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
/*     */ public class ContentLoadingRelativeLayout
/*     */   extends RelativeLayout
/*     */ {
/*     */   private static final int MIN_SHOW_TIME = 500;
/*     */   private static final int MIN_DELAY = 500;
/*  22 */   private long mStartTime = -1L;
/*     */   
/*     */   private boolean mPostedHide = false;
/*     */   
/*     */   private boolean mPostedShow = false;
/*     */   
/*     */   private boolean mDismissed = false;
/*     */   
/*     */   private boolean mAnimateHide = true;
/*     */   
/*     */   private boolean mAnimateShow = true;
/*     */   
/*  34 */   private final Runnable mDelayedHide = new Runnable()
/*     */     {
/*     */       public void run() {
/*  37 */         ContentLoadingRelativeLayout.this.mPostedHide = false;
/*  38 */         ContentLoadingRelativeLayout.this.mStartTime = -1L;
/*  39 */         if (ContentLoadingRelativeLayout.this.mAnimateHide) {
/*  40 */           ContentLoadingRelativeLayout.this.startAnimation(AnimationUtils.loadAnimation(ContentLoadingRelativeLayout.this.getContext(), 17432577));
/*     */         }
/*  42 */         ContentLoadingRelativeLayout.this.setVisibility(8);
/*  43 */         ContentLoadingRelativeLayout.this.mPostedShow = false;
/*     */       }
/*     */     };
/*     */   
/*  47 */   private final Runnable mDelayedShow = new Runnable()
/*     */     {
/*     */       public void run() {
/*  50 */         ContentLoadingRelativeLayout.this.mPostedShow = false;
/*  51 */         if (!ContentLoadingRelativeLayout.this.mDismissed) {
/*  52 */           ContentLoadingRelativeLayout.this.mStartTime = System.currentTimeMillis();
/*  53 */           if (ContentLoadingRelativeLayout.this.mAnimateShow) {
/*  54 */             ContentLoadingRelativeLayout.this.startAnimation(AnimationUtils.loadAnimation(ContentLoadingRelativeLayout.this.getContext(), 17432576));
/*     */           }
/*  56 */           ContentLoadingRelativeLayout.this.setVisibility(0);
/*  57 */           ContentLoadingRelativeLayout.this.mPostedHide = false;
/*     */         } 
/*     */       }
/*     */     };
/*     */   
/*     */   public ContentLoadingRelativeLayout(Context context) {
/*  63 */     super(context);
/*     */   }
/*     */   
/*     */   public ContentLoadingRelativeLayout(Context context, AttributeSet attrs) {
/*  67 */     super(context, attrs);
/*     */   }
/*     */   
/*     */   public ContentLoadingRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
/*  71 */     super(context, attrs, defStyle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onAttachedToWindow() {
/*  81 */     super.onAttachedToWindow();
/*  82 */     removeCallbacks();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onDetachedFromWindow() {
/*  90 */     super.onDetachedFromWindow();
/*  91 */     removeCallbacks();
/*     */   }
/*     */   
/*     */   private void removeCallbacks() {
/*  95 */     removeCallbacks(this.mDelayedHide);
/*  96 */     removeCallbacks(this.mDelayedShow);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void hide(boolean forceHide) {
/* 106 */     hide(forceHide, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void hide(boolean forceHide, boolean animate) {
/* 117 */     this.mDismissed = true;
/* 118 */     removeCallbacks(this.mDelayedShow);
/* 119 */     this.mAnimateHide = animate;
/* 120 */     if (forceHide) {
/* 121 */       if (this.mAnimateHide) {
/* 122 */         startAnimation(AnimationUtils.loadAnimation(getContext(), 17432577));
/*     */       }
/* 124 */       setVisibility(8);
/* 125 */       this.mPostedShow = false;
/*     */     } else {
/* 127 */       long diff = System.currentTimeMillis() - this.mStartTime;
/* 128 */       if (diff >= 500L || this.mStartTime == -1L) {
/*     */ 
/*     */ 
/*     */         
/* 132 */         if (this.mAnimateHide) {
/* 133 */           startAnimation(AnimationUtils.loadAnimation(getContext(), 17432577));
/*     */         }
/* 135 */         setVisibility(8);
/* 136 */         this.mPostedShow = false;
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 141 */       else if (!this.mPostedHide) {
/* 142 */         postDelayed(this.mDelayedHide, 500L - diff);
/* 143 */         this.mPostedHide = true;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void show() {
/* 154 */     show(false, true, true);
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
/*     */   public void show(boolean forceShow, boolean delay, boolean animate) {
/* 166 */     this.mStartTime = -1L;
/* 167 */     this.mDismissed = false;
/* 168 */     removeCallbacks(this.mDelayedHide);
/* 169 */     this.mAnimateShow = animate;
/* 170 */     if (forceShow) {
/* 171 */       if (this.mAnimateShow) {
/* 172 */         startAnimation(AnimationUtils.loadAnimation(getContext(), 17432576));
/*     */       }
/* 174 */       setVisibility(0);
/* 175 */       this.mPostedShow = false;
/*     */     }
/* 177 */     else if (!this.mPostedShow) {
/* 178 */       if (delay) {
/* 179 */         postDelayed(this.mDelayedShow, 500L);
/*     */       } else {
/* 181 */         post(this.mDelayedShow);
/*     */       } 
/* 183 */       this.mPostedShow = true;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\widget\ContentLoadingRelativeLayout.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */