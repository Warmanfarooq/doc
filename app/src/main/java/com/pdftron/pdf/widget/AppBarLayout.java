/*    */ package com.pdftron.pdf.widget;
/*    */ 
/*    */ import android.annotation.TargetApi;
/*    */ import android.content.Context;
/*    */ import android.graphics.Rect;
/*    */ import android.util.AttributeSet;
/*    */ import android.view.ViewOutlineProvider;
/*    */ import android.view.WindowInsets;
/*    */ import android.widget.LinearLayout;
/*    */ import androidx.annotation.AttrRes;
/*    */ import com.pdftron.pdf.utils.Utils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AppBarLayout
/*    */   extends LinearLayout
/*    */ {
/*    */   public AppBarLayout(Context context) {
/* 21 */     this(context, (AttributeSet)null);
/*    */   }
/*    */   
/*    */   public AppBarLayout(Context context, AttributeSet attrs) {
/* 25 */     this(context, attrs, 0);
/*    */   }
/*    */   
/*    */   public AppBarLayout(Context context, AttributeSet attrs, @AttrRes int defStyleAttr) {
/* 29 */     super(context, attrs, defStyleAttr);
/* 30 */     setOrientation(1);
/*    */     
/* 32 */     if (Utils.isLollipop()) {
/* 33 */       setOutlineProvider(ViewOutlineProvider.PADDED_BOUNDS);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean fitSystemWindows(Rect insets) {
/* 43 */     if (!Utils.isLollipop())
/*    */     {
/* 45 */       insets.bottom = 0;
/*    */     }
/* 47 */     return super.fitSystemWindows(insets);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @TargetApi(21)
/*    */   public WindowInsets onApplyWindowInsets(WindowInsets insets) {
/* 57 */     WindowInsets newInsets = insets.replaceSystemWindowInsets(insets.getSystemWindowInsetLeft(), insets
/* 58 */         .getSystemWindowInsetTop(), insets.getSystemWindowInsetRight(), 0);
/*    */     
/* 60 */     return super.onApplyWindowInsets(newInsets);
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\widget\AppBarLayout.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */