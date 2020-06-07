/*    */ package com.pdftron.pdf.widget;
/*    */ 
/*    */ import android.annotation.TargetApi;
/*    */ import android.content.Context;
/*    */ import android.graphics.Rect;
/*    */ import android.util.AttributeSet;
/*    */ import android.view.View;
/*    */ import android.view.WindowInsets;
/*    */ import androidx.annotation.AttrRes;
/*    */ import androidx.coordinatorlayout.widget.CoordinatorLayout;
/*    */ import androidx.core.view.ViewCompat;
/*    */ import com.pdftron.pdf.utils.Utils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IgnoreTopInsetCoordinatorLayout
/*    */   extends CoordinatorLayout
/*    */ {
/*    */   public IgnoreTopInsetCoordinatorLayout(Context context) {
/* 22 */     this(context, (AttributeSet)null);
/*    */   }
/*    */   
/*    */   public IgnoreTopInsetCoordinatorLayout(Context context, AttributeSet attrs) {
/* 26 */     this(context, attrs, 0);
/*    */   }
/*    */   
/*    */   public IgnoreTopInsetCoordinatorLayout(Context context, AttributeSet attrs, @AttrRes int defStyleAttr) {
/* 30 */     super(context, attrs, defStyleAttr);
/*    */     
/* 32 */     if (Utils.isLollipop() && ViewCompat.getFitsSystemWindows((View)this)) {
/*    */       
/* 34 */       int visibility = getSystemUiVisibility();
/* 35 */       visibility &= 0xFFFFFEFF;
/* 36 */       setSystemUiVisibility(visibility);
/*    */     } 
/* 38 */     setStatusBarBackground(null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean fitSystemWindows(Rect insets) {
/* 49 */     if (!Utils.isLollipop()) {
/* 50 */       insets.top = 0;
/*    */     }
/* 52 */     return super.fitSystemWindows(insets);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @TargetApi(21)
/*    */   public WindowInsets dispatchApplyWindowInsets(WindowInsets insets) {
/* 62 */     return super.dispatchApplyWindowInsets(insets.replaceSystemWindowInsets(insets
/* 63 */           .getSystemWindowInsetLeft(), 0, insets
/* 64 */           .getSystemWindowInsetRight(), insets.getSystemWindowInsetBottom()));
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\widget\IgnoreTopInsetCoordinatorLayout.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */