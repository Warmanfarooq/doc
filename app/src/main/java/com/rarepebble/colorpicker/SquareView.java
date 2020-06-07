/*    */ package com.rarepebble.colorpicker;
/*    */ 
/*    */ import android.content.Context;
/*    */ import android.util.AttributeSet;
/*    */ import android.view.View;
/*    */ 
/*    */ class SquareView
/*    */   extends View {
/*    */   private static final int MIN_SIZE_DIP = 200;
/*    */   private final int minSizePx;
/*    */   
/*    */   public SquareView(Context context, AttributeSet attrs) {
/* 13 */     super(context, attrs);
/* 14 */     this.minSizePx = (int)Resources.dipToPixels(context, 200.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
/* 20 */     int w = MeasureSpec.getSize(widthMeasureSpec);
/* 21 */     int h = MeasureSpec.getSize(heightMeasureSpec);
/* 22 */     int modeW = MeasureSpec.getMode(widthMeasureSpec);
/* 23 */     int modeH = MeasureSpec.getMode(heightMeasureSpec);
/* 24 */     int size = this.minSizePx;
/* 25 */     if (modeW == 0) {
/* 26 */       size = h;
/*    */     }
/* 28 */     else if (modeH == 0) {
/* 29 */       size = w;
/*    */     } else {
/*    */       
/* 32 */       size = Math.min(w, h);
/*    */     } 
/* 34 */     size = Math.max(size, this.minSizePx);
/* 35 */     setMeasuredDimension(size, size);
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\rarepebble\colorpicker\SquareView.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */