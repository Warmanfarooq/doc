/*     */ package com.pdftron.pdf.widget;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.content.res.TypedArray;
/*     */ import android.graphics.drawable.Drawable;
/*     */ import android.util.AttributeSet;
/*     */ import android.view.View;
/*     */ import android.view.animation.Interpolator;
/*     */ import androidx.core.view.OnApplyWindowInsetsListener;
/*     */ import androidx.core.view.ViewCompat;
/*     */ import androidx.core.view.WindowInsetsCompat;
/*     */ import androidx.interpolator.view.animation.FastOutLinearInInterpolator;
/*     */ import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StatusBarView
/*     */   extends View
/*     */   implements View.OnSystemUiVisibilityChangeListener
/*     */ {
/*  23 */   private static final String TAG = StatusBarView.class.getName();
/*     */   
/*     */   private Interpolator mShowInterpolator;
/*     */   
/*     */   private Interpolator mHideInterpolator;
/*     */   private int mShowHideDuration;
/*     */   private WindowInsetsCompat mLastInsets;
/*     */   private int mLastVisibility;
/*     */   
/*     */   public StatusBarView(Context context) {
/*  33 */     this(context, (AttributeSet)null);
/*     */   }
/*     */   
/*     */   public StatusBarView(Context context, AttributeSet attrs) {
/*  37 */     this(context, attrs, 0);
/*     */   }
/*     */   
/*     */   public StatusBarView(Context context, AttributeSet attrs, int defStyleAttr) {
/*  41 */     super(context, attrs, defStyleAttr); Drawable background; this.mShowInterpolator = null; this.mHideInterpolator = null; this.mShowHideDuration = 0; this.mLastInsets = null;
/*     */     this.mLastVisibility = 0;
/*  43 */     TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StatusBarView, defStyleAttr, R.style.StatusBarView);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  49 */       ViewCompat.setFitsSystemWindows(this, a.getBoolean(R.styleable.StatusBarView_android_fitsSystemWindows, false));
/*  50 */       background = a.getDrawable(R.styleable.StatusBarView_android_background);
/*     */     } finally {
/*  52 */       a.recycle();
/*     */     } 
/*     */     
/*  55 */     ViewCompat.setBackground(this, background);
/*     */     
/*  57 */     this.mShowInterpolator = (Interpolator)new LinearOutSlowInInterpolator();
/*  58 */     this.mHideInterpolator = (Interpolator)new FastOutLinearInInterpolator();
/*     */     
/*  60 */     this.mShowHideDuration = getResources().getInteger(R.integer.system_bars_enter_exit_duration);
/*     */     
/*  62 */     this.mLastVisibility = ViewCompat.getWindowSystemUiVisibility(this);
/*  63 */     setOnSystemUiVisibilityChangeListener(this);
/*     */     
/*  65 */     ViewCompat.setOnApplyWindowInsetsListener(this, new OnApplyWindowInsetsListener()
/*     */         {
/*     */ 
/*     */ 
/*     */           
/*     */           public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat insets)
/*     */           {
/*  72 */             if (StatusBarView.this.mLastInsets == null || (insets != null && insets
/*  73 */               .getSystemWindowInsetTop() != StatusBarView.this.mLastInsets.getSystemWindowInsetTop())) {
/*  74 */               StatusBarView.this.mLastInsets = insets;
/*  75 */               StatusBarView.this.requestLayout();
/*     */             } 
/*  77 */             return insets;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
/*  89 */     int height = (this.mLastInsets != null) ? this.mLastInsets.getSystemWindowInsetTop() : 0;
/*  90 */     int newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, 1073741824);
/*     */     
/*  92 */     super.onMeasure(widthMeasureSpec, newHeightMeasureSpec);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onSystemUiVisibilityChange(int visibility) {
/* 102 */     int diff = visibility ^ this.mLastVisibility;
/* 103 */     if ((diff & 0x4) == 4) {
/*     */       
/* 105 */       ViewCompat.animate(this).cancel();
/*     */       
/* 107 */       if ((visibility & 0x4) == 4) {
/*     */         
/* 109 */         hide();
/*     */       } else {
/*     */         
/* 112 */         show();
/*     */       } 
/*     */     } 
/* 115 */     this.mLastVisibility = visibility;
/*     */   }
/*     */   
/*     */   private void hide() {
/* 119 */     ViewCompat.animate(this)
/* 120 */       .alpha(0.0F)
/* 121 */       .setDuration(this.mShowHideDuration)
/* 122 */       .withEndAction(new Runnable()
/*     */         {
/*     */           public void run() {
/* 125 */             StatusBarView.this.setVisibility(8);
/* 126 */             StatusBarView.this.setAlpha(1.0F);
/*     */           }
/* 129 */         }).setInterpolator(this.mHideInterpolator)
/* 130 */       .withLayer();
/*     */   }
/*     */   
/*     */   private void show() {
/* 134 */     if (getVisibility() != 0) {
/* 135 */       setAlpha(0.0F);
/* 136 */       setVisibility(0);
/*     */     } 
/* 138 */     ViewCompat.animate(this)
/* 139 */       .alpha(1.0F)
/* 140 */       .setDuration(this.mShowHideDuration)
/* 141 */       .setInterpolator(this.mShowInterpolator)
/* 142 */       .withLayer();
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\widget\StatusBarView.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */