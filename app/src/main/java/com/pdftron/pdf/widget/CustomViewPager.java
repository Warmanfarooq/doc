/*    */ package com.pdftron.pdf.widget;
/*    */ 
/*    */ import android.content.Context;
/*    */ import android.util.AttributeSet;
/*    */ import android.view.MotionEvent;
/*    */ import androidx.viewpager.widget.ViewPager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CustomViewPager
/*    */   extends ViewPager
/*    */ {
/*    */   private boolean mIsSwippingEnabled = true;
/*    */   
/*    */   public CustomViewPager(Context context) {
/* 21 */     super(context);
/*    */   }
/*    */   
/*    */   public CustomViewPager(Context context, AttributeSet attrs) {
/* 25 */     super(context, attrs);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setSwippingEnabled(boolean enabled) {
/* 33 */     this.mIsSwippingEnabled = enabled;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean onInterceptTouchEvent(MotionEvent ev) {
/* 44 */     return (this.mIsSwippingEnabled && super.onInterceptTouchEvent(ev));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean onTouchEvent(MotionEvent ev) {
/* 55 */     return (this.mIsSwippingEnabled && super.onTouchEvent(ev));
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\widget\CustomViewPager.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */