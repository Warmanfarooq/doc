/*    */ package com.pdftron.pdf.widget;
/*    */ import android.content.Context;
/*    */ import android.util.AttributeSet;
/*    */ import android.view.View;
/*    */ import android.view.ViewGroup;
/*    */ import androidx.annotation.AttrRes;
/*    */ import androidx.annotation.NonNull;
/*    */ import androidx.core.view.OnApplyWindowInsetsListener;
/*    */ import androidx.core.view.ViewCompat;
/*    */ import androidx.core.view.WindowInsetsCompat;
/*    */ 
/*    */ public class FragmentLayout extends DispatchFairInsetsFrameLayout {
/* 13 */   private WindowInsetsCompat mLastInsets = null;
/*    */   
/*    */   public FragmentLayout(Context context) {
/* 16 */     this(context, (AttributeSet)null);
/*    */   }
/*    */   
/*    */   public FragmentLayout(Context context, AttributeSet attrs) {
/* 20 */     this(context, attrs, 0);
/*    */   }
/*    */   
/*    */   public FragmentLayout(Context context, AttributeSet attrs, @AttrRes int defStyleAttr) {
/* 24 */     super(context, attrs, defStyleAttr);
/*    */     
/* 26 */     ViewCompat.setOnApplyWindowInsetsListener((View)this, new OnApplyWindowInsetsListener()
/*    */         {
/*    */           public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat insets) {
/* 29 */             if (insets != null && FragmentLayout.this.mLastInsets != insets) {
/* 30 */               int childCount = FragmentLayout.this.getChildCount();
/* 31 */               for (int i = 0; i < childCount; i++) {
/* 32 */                 View child = FragmentLayout.this.getChildAt(i);
/*    */                 
/* 34 */                 if (child != null && !ViewCompat.getFitsSystemWindows(child))
/*    */                 {
/* 36 */                   FragmentLayout.this.applyMarginInsets(child, insets);
/*    */                 }
/*    */               } 
/* 39 */               FragmentLayout.this.mLastInsets = insets;
/*    */             } 
/* 41 */             return insets;
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   private void applyMarginInsets(@NonNull View view, @NonNull WindowInsetsCompat insets) {
/* 47 */     if (view.getLayoutParams() != null && view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
/*    */       
/* 49 */       ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
/*    */       
/* 51 */       if (mlp.leftMargin != insets.getSystemWindowInsetLeft() || mlp.topMargin != insets
/* 52 */         .getSystemWindowInsetTop() || mlp.rightMargin != insets
/* 53 */         .getSystemWindowInsetRight() || mlp.bottomMargin != insets
/* 54 */         .getSystemWindowInsetBottom()) {
/*    */         
/* 56 */         mlp.setMargins(insets.getSystemWindowInsetLeft(), insets
/* 57 */             .getSystemWindowInsetTop(), insets
/* 58 */             .getSystemWindowInsetRight(), insets
/* 59 */             .getSystemWindowInsetBottom());
/*    */         
/* 61 */         view.requestLayout();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\widget\FragmentLayout.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */