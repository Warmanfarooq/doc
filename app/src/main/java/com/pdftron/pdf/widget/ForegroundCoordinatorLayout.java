/*     */ package com.pdftron.pdf.widget;
/*     */ 
/*     */ import android.annotation.TargetApi;
/*     */ import android.content.Context;
/*     */ import android.content.res.TypedArray;
/*     */ import android.graphics.Canvas;
/*     */ import android.graphics.Rect;
/*     */ import android.graphics.drawable.Drawable;
/*     */ import android.util.AttributeSet;
/*     */ import android.view.View;
/*     */ import androidx.annotation.AttrRes;
/*     */ import androidx.coordinatorlayout.widget.CoordinatorLayout;
/*     */ import androidx.core.view.GravityCompat;
/*     */ import androidx.core.view.ViewCompat;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ForegroundCoordinatorLayout
/*     */   extends CoordinatorLayout
/*     */   implements ForegroundLayout
/*     */ {
/*     */   private static final int DEFAULT_FOREGROUND_GRAVITY = 8388659;
/*     */   private Drawable mForeground;
/*     */   private int mForegroundGravity;
/*     */   private Rect mSelfBounds;
/*     */   private Rect mOverlayBounds;
/*     */   private boolean mForegroundBoundsChanged;
/*     */   
/*     */   public ForegroundCoordinatorLayout(Context context) {
/*  34 */     this(context, (AttributeSet)null);
/*     */   }
/*     */   
/*     */   public ForegroundCoordinatorLayout(Context context, AttributeSet attrs) {
/*  38 */     this(context, attrs, 0);
/*     */   }
/*     */   
/*     */   public ForegroundCoordinatorLayout(Context context, AttributeSet attrs, @AttrRes int defStyleAttr) {
/*  42 */     super(context, attrs, defStyleAttr); Drawable foreground; int foregroundGravity; this.mForeground = null; this.mForegroundGravity = 8388659; this.mSelfBounds = new Rect(); this.mOverlayBounds = new Rect();
/*     */     this.mForegroundBoundsChanged = false;
/*  44 */     TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ForegroundCoordinatorLayout, defStyleAttr, 0);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  50 */       foreground = a.getDrawable(R.styleable.ForegroundCoordinatorLayout_android_foreground);
/*  51 */       foregroundGravity = a.getInt(R.styleable.ForegroundCoordinatorLayout_android_foregroundGravity, 8388659);
/*     */     } finally {
/*  53 */       a.recycle();
/*     */     } 
/*     */     
/*  56 */     if (foreground != null) {
/*  57 */       setForeground(foreground);
/*     */     }
/*     */     
/*  60 */     setForegroundGravity(foregroundGravity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawableStateChanged() {
/*  68 */     super.drawableStateChanged();
/*     */     
/*  70 */     if (this.mForeground != null && this.mForeground.isStateful()) {
/*  71 */       this.mForeground.setState(getDrawableState());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean verifyDrawable(Drawable who) {
/*  80 */     return (super.verifyDrawable(who) || who == this.mForeground);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void jumpDrawablesToCurrentState() {
/*  89 */     super.jumpDrawablesToCurrentState();
/*  90 */     if (this.mForeground != null) this.mForeground.jumpToCurrentState();
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Drawable getForeground() {
/*  99 */     return this.mForeground;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setForeground(Drawable drawable) {
/* 108 */     if (this.mForeground != drawable) {
/* 109 */       if (this.mForeground != null) {
/* 110 */         this.mForeground.setCallback(null);
/* 111 */         unscheduleDrawable(this.mForeground);
/*     */       } 
/*     */       
/* 114 */       this.mForeground = drawable;
/*     */       
/* 116 */       if (drawable != null) {
/* 117 */         setWillNotDraw(false);
/* 118 */         drawable.setCallback((Drawable.Callback)this);
/* 119 */         if (drawable.isStateful()) {
/* 120 */           drawable.setState(getDrawableState());
/*     */         }
/*     */       } else {
/* 123 */         setWillNotDraw(true);
/*     */       } 
/*     */       
/* 126 */       requestLayout();
/* 127 */       invalidate();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getForegroundGravity() {
/* 138 */     return this.mForegroundGravity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setForegroundGravity(int gravity) {
/* 148 */     if (this.mForegroundGravity != gravity) {
/* 149 */       this.mForegroundGravity = gravity;
/*     */       
/* 151 */       invalidate();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onLayout(boolean changed, int l, int t, int r, int b) {
/* 160 */     super.onLayout(changed, l, t, r, b);
/* 161 */     this.mForegroundBoundsChanged = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onSizeChanged(int w, int h, int oldw, int oldh) {
/* 169 */     super.onSizeChanged(w, h, oldw, oldh);
/* 170 */     this.mForegroundBoundsChanged = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void draw(Canvas canvas) {
/* 180 */     super.draw(canvas);
/*     */     
/* 182 */     Drawable foreground = this.mForeground;
/* 183 */     if (foreground != null) {
/* 184 */       if (this.mForegroundBoundsChanged) {
/* 185 */         this.mForegroundBoundsChanged = false;
/* 186 */         Rect selfBounds = this.mSelfBounds;
/* 187 */         Rect overlayBounds = this.mOverlayBounds;
/*     */         
/* 189 */         selfBounds.set(0, 0, getWidth(), getHeight());
/*     */         
/* 191 */         int ld = ViewCompat.getLayoutDirection((View)this);
/* 192 */         GravityCompat.apply(this.mForegroundGravity, foreground.getIntrinsicWidth(), foreground
/* 193 */             .getIntrinsicHeight(), selfBounds, overlayBounds, ld);
/* 194 */         foreground.setBounds(overlayBounds);
/*     */       } 
/*     */       
/* 197 */       foreground.draw(canvas);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @TargetApi(21)
/*     */   public void drawableHotspotChanged(float x, float y) {
/* 208 */     super.drawableHotspotChanged(x, y);
/* 209 */     if (this.mForeground != null)
/* 210 */       this.mForeground.setHotspot(x, y); 
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\widget\ForegroundCoordinatorLayout.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */