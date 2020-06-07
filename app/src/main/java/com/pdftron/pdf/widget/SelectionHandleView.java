/*    */ package com.pdftron.pdf.widget;
/*    */ 
/*    */ import android.content.Context;
/*    */ import android.util.AttributeSet;
/*    */ import android.view.MotionEvent;
/*    */ import androidx.annotation.DimenRes;
/*    */ import androidx.annotation.DrawableRes;
/*    */ import com.google.android.material.floatingactionbutton.FloatingActionButton;
/*    */ import com.pdftron.pdf.tools.R;
/*    */ 
/*    */ public class SelectionHandleView
/*    */   extends RotateHandleView
/*    */ {
/*    */   public SelectionHandleView(Context context) {
/* 15 */     this(context, (AttributeSet)null);
/*    */   }
/*    */   
/*    */   public SelectionHandleView(Context context, AttributeSet attrs) {
/* 19 */     this(context, attrs, 0);
/*    */   }
/*    */   
/*    */   public SelectionHandleView(Context context, AttributeSet attrs, int defStyleAttr) {
/* 23 */     super(context, attrs, defStyleAttr);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void init() {
/* 28 */     super.init();
/*    */     
/* 30 */     setImageResource(R.drawable.ic_circle_black_24dp);
/* 31 */     setCustomSize(R.dimen.selection_widget_size);
/*    */   }
/*    */   
/*    */   public void setImageResource(@DrawableRes int resId) {
/* 35 */     this.mFab.setImageResource(resId);
/*    */   }
/*    */   
/*    */   public void setCustomSize(@DimenRes int sizeRes) {
/* 39 */     if (this.mFab instanceof FloatingActionButton) {
/* 40 */       int size = getResources().getDimensionPixelSize(sizeRes);
/* 41 */       if (size % 2 != 0)
/*    */       {
/* 43 */         size--;
/*    */       }
/* 45 */       ((FloatingActionButton)this.mFab).setCustomSize(size);
/*    */     } else {
/*    */       
/* 48 */       this.mFab.setPadding(0, 0, 0, 0);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onTouchEvent(MotionEvent event) {
/* 54 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\widget\SelectionHandleView.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */