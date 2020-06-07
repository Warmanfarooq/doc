/*    */ package com.pdftron.pdf.widget;
/*    */ 
/*    */ import android.content.Context;
/*    */ import android.util.AttributeSet;
/*    */ import android.view.LayoutInflater;
/*    */ import android.view.MotionEvent;
/*    */ import android.view.ViewGroup;
/*    */ import android.widget.ImageView;
/*    */ import android.widget.LinearLayout;
/*    */ import com.pdftron.pdf.tools.R;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RotateHandleView
/*    */   extends LinearLayout
/*    */ {
/*    */   protected ImageView mFab;
/*    */   private RotateHandleViewListener mListener;
/*    */   float mDX;
/*    */   float mDY;
/*    */   
/*    */   public RotateHandleView(Context context) {
/* 29 */     this(context, (AttributeSet)null);
/*    */   }
/*    */   
/*    */   public RotateHandleView(Context context, AttributeSet attrs) {
/* 33 */     this(context, attrs, 0);
/*    */   }
/*    */   
/*    */   public RotateHandleView(Context context, AttributeSet attrs, int defStyleAttr) {
/* 37 */     super(context, attrs, defStyleAttr);
/*    */     
/* 39 */     init();
/*    */   }
/*    */   
/*    */   protected void init() {
/* 43 */     LayoutInflater.from(getContext()).inflate(R.layout.view_rotate_handle, (ViewGroup)this);
/* 44 */     this.mFab = (ImageView)findViewById(R.id.rotate_fab);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onLayout(boolean changed, int l, int t, int r, int b) {
/* 49 */     super.onLayout(changed, l, t, r, b);
/*    */     
/* 51 */     LayoutParams lp = (LayoutParams)this.mFab.getLayoutParams();
/* 52 */     if (this.mFab.getMeasuredWidth() == 0 || this.mFab.getMeasuredHeight() == 0) {
/* 53 */       this.mFab.measure(0, 0);
/*    */     }
/* 55 */     int width = this.mFab.getMeasuredWidth();
/* 56 */     int height = this.mFab.getMeasuredHeight();
/* 57 */     this.mFab.layout(lp.leftMargin, lp.topMargin, lp.leftMargin + width, lp.topMargin + height);
/*    */   }
/*    */   
/*    */   public void setListener(RotateHandleViewListener listener) {
/* 61 */     this.mListener = listener;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onTouchEvent(MotionEvent event) {
/* 66 */     switch (event.getAction()) {
/*    */       case 0:
/* 68 */         this.mDX = getX() - event.getRawX();
/* 69 */         this.mDY = getY() - event.getRawY();
/*    */         
/* 71 */         if (this.mListener != null) {
/* 72 */           this.mListener.onDown(event.getRawX(), event.getRawY());
/*    */         }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 95 */         return true;case 2: animate().x(event.getRawX() + this.mDX).y(event.getRawY() + this.mDY).setDuration(0L).start(); if (this.mListener != null) this.mListener.onMove(event.getRawX(), event.getRawY());  return true;case 1: case 3: if (this.mListener != null) this.mListener.onUp(event.getRawX(), event.getRawY(), event.getX(), event.getY());  return true;
/*    */     } 
/*    */     return false;
/*    */   }
/*    */   
/*    */   public static interface RotateHandleViewListener {
/*    */     void onDown(float param1Float1, float param1Float2);
/*    */     
/*    */     void onMove(float param1Float1, float param1Float2);
/*    */     
/*    */     void onUp(float param1Float1, float param1Float2, float param1Float3, float param1Float4);
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\widget\RotateHandleView.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */