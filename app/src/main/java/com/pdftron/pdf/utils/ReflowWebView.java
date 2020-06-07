/*     */ package com.pdftron.pdf.utils;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.util.AttributeSet;
/*     */ import android.view.GestureDetector;
/*     */ import android.view.MotionEvent;
/*     */ import android.view.ScaleGestureDetector;
/*     */ import android.webkit.WebView;
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
/*     */ public class ReflowWebView
/*     */   extends WebView
/*     */ {
/*     */   private ScaleGestureDetector mScaleGestureDetector;
/*     */   private GestureDetector mGestureDetector;
/*     */   private int mOrientation;
/*     */   private float mFlingThreshSpeed;
/*     */   private boolean mPageTop;
/*     */   private boolean mPageBottom;
/*     */   private boolean mDone;
/*     */   private ReflowWebViewCallback mCallback;
/*     */   
/*     */   public void setListener(ReflowWebViewCallback listener) {
/*  86 */     this.mCallback = listener;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReflowWebView(Context context) {
/*  93 */     super(context);
/*  94 */     init(context);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReflowWebView(Context context, AttributeSet attrs) {
/* 101 */     super(context, attrs);
/* 102 */     init(context);
/*     */   }
/*     */   
/*     */   private void init(Context context) {
/* 106 */     this.mScaleGestureDetector = new ScaleGestureDetector(context, (ScaleGestureDetector.OnScaleGestureListener)new ScaleListener());
/* 107 */     this.mGestureDetector = new GestureDetector(getContext(), new TapListener());
/*     */     
/* 109 */     this.mFlingThreshSpeed = Utils.convDp2Pix(context, 1000.0F);
/*     */   }
/*     */   
/*     */   public void setOrientation(int orientation) {
/* 113 */     this.mOrientation = orientation;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onTouchEvent(MotionEvent ev) {
/* 118 */     super.onTouchEvent(ev);
/* 119 */     if (this.mGestureDetector != null) {
/* 120 */       this.mGestureDetector.onTouchEvent(ev);
/*     */     }
/* 122 */     if (this.mScaleGestureDetector != null) {
/* 123 */       this.mScaleGestureDetector.onTouchEvent(ev);
/*     */     }
/*     */     
/* 126 */     return true;
/*     */   }
/*     */   
/*     */   private void detectPageEnds() {
/* 130 */     if (this.mOrientation != 1) {
/*     */       return;
/*     */     }
/* 133 */     this.mPageTop = false;
/* 134 */     this.mPageBottom = false;
/* 135 */     if (computeVerticalScrollRange() <= computeVerticalScrollOffset() + 
/* 136 */       computeVerticalScrollExtent()) {
/* 137 */       this.mPageBottom = true;
/*     */     }
/* 139 */     if (getScrollY() == 0) {
/* 140 */       this.mPageTop = true;
/*     */     }
/*     */   }
/*     */   
/*     */   private void onPageBottom() {
/* 145 */     if (this.mDone) {
/*     */       return;
/*     */     }
/* 148 */     if (this.mCallback != null) {
/* 149 */       this.mCallback.onPageBottom(this);
/*     */     }
/* 151 */     this.mDone = true;
/*     */   } public static interface ReflowWebViewCallback {
/*     */     boolean onReflowWebViewScaleBegin(WebView param1WebView, ScaleGestureDetector param1ScaleGestureDetector); boolean onReflowWebViewScale(WebView param1WebView, ScaleGestureDetector param1ScaleGestureDetector); void onReflowWebViewScaleEnd(WebView param1WebView, ScaleGestureDetector param1ScaleGestureDetector); void onReflowWebViewSingleTapUp(WebView param1WebView, MotionEvent param1MotionEvent); void onPageTop(WebView param1WebView); void onPageBottom(WebView param1WebView); }
/*     */   private void onPageTop() {
/* 155 */     if (this.mDone) {
/*     */       return;
/*     */     }
/* 158 */     if (this.mCallback != null) {
/* 159 */       this.mCallback.onPageTop(this);
/*     */     }
/* 161 */     this.mDone = true;
/*     */   }
/*     */   
/*     */   private class TapListener
/*     */     implements GestureDetector.OnGestureListener {
/*     */     public boolean onSingleTapUp(MotionEvent event) {
/* 167 */       if (ReflowWebView.this.mCallback != null) {
/* 168 */         ReflowWebView.this.mCallback.onReflowWebViewSingleTapUp(ReflowWebView.this, event);
/*     */       }
/* 170 */       return true;
/*     */     }
/*     */     private TapListener() {}
/*     */     
/*     */     public boolean onDown(MotionEvent event) {
/* 175 */       ReflowWebView.this.mDone = false;
/* 176 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
/* 182 */       if (ReflowWebView.this.mOrientation == 1) {
/* 183 */         ReflowWebView.this.detectPageEnds();
/*     */       }
/* 185 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
/* 191 */       if (ReflowWebView.this.mOrientation == 1) {
/* 192 */         if (Math.abs(velocityY) > ReflowWebView.this.mFlingThreshSpeed) {
/* 193 */           if (velocityY < 0.0F) {
/* 194 */             if (ReflowWebView.this.mPageBottom) {
/* 195 */               ReflowWebView.this.onPageBottom();
/*     */             }
/*     */           }
/* 198 */           else if (ReflowWebView.this.mPageTop) {
/* 199 */             ReflowWebView.this.onPageTop();
/*     */           } 
/*     */         }
/*     */         
/* 203 */         ReflowWebView.this.detectPageEnds();
/*     */       } 
/* 205 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void onShowPress(MotionEvent event) {}
/*     */ 
/*     */     
/*     */     public void onLongPress(MotionEvent event) {}
/*     */   }
/*     */ 
/*     */   
/*     */   private class ScaleListener
/*     */     extends ScaleGestureDetector.SimpleOnScaleGestureListener
/*     */   {
/*     */     private ScaleListener() {}
/*     */     
/*     */     public boolean onScaleBegin(ScaleGestureDetector detector) {
/* 222 */       return (ReflowWebView.this.mCallback == null || ReflowWebView.this.mCallback.onReflowWebViewScaleBegin(ReflowWebView.this, detector));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean onScale(ScaleGestureDetector detector) {
/* 227 */       return (ReflowWebView.this.mCallback == null || ReflowWebView.this.mCallback.onReflowWebViewScale(ReflowWebView.this, detector));
/*     */     }
/*     */ 
/*     */     
/*     */     public void onScaleEnd(ScaleGestureDetector detector) {
/* 232 */       if (ReflowWebView.this.mCallback != null)
/* 233 */         ReflowWebView.this.mCallback.onReflowWebViewScaleEnd(ReflowWebView.this, detector); 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pd\\utils\ReflowWebView.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */