/*    */ package com.pdftron.pdf.widget;
/*    */ 
/*    */ import android.content.Context;
/*    */ import android.util.AttributeSet;
/*    */ import android.widget.RelativeLayout;
/*    */ 
/*    */ public class UniformWidthRelativeLayout extends RelativeLayout {
/*    */   public UniformWidthRelativeLayout(Context context) {
/*  9 */     super(context);
/*    */   }
/*    */   
/*    */   public UniformWidthRelativeLayout(Context context, AttributeSet attrs) {
/* 13 */     super(context, attrs);
/*    */   }
/*    */   
/*    */   public UniformWidthRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
/* 17 */     super(context, attrs, defStyleAttr);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
/* 22 */     super.onMeasure(widthMeasureSpec, widthMeasureSpec);
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\widget\UniformWidthRelativeLayout.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */