/*    */ package com.pdftron.pdf.controls;
/*    */ 
/*    */ import android.content.Context;
/*    */ import android.graphics.Canvas;
/*    */ import android.graphics.drawable.Drawable;
/*    */ import android.util.AttributeSet;
/*    */ import android.view.MotionEvent;
/*    */ import androidx.appcompat.widget.AppCompatSeekBar;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MirrorSeekBar
/*    */   extends AppCompatSeekBar
/*    */ {
/*    */   boolean mIsReversed = false;
/* 21 */   Drawable mDrawable = null;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MirrorSeekBar(Context context) {
/* 27 */     super(context);
/* 28 */     this.mIsReversed = false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MirrorSeekBar(Context context, AttributeSet attrs) {
/* 35 */     super(context, attrs);
/* 36 */     this.mIsReversed = false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MirrorSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
/* 43 */     super(context, attrs, defStyleAttr);
/* 44 */     this.mIsReversed = false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isReversed() {
/* 51 */     return this.mIsReversed;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setReversed(boolean isReversed) {
/* 60 */     this.mIsReversed = isReversed;
/* 61 */     if (isReversed) {
/* 62 */       this.mDrawable = getBackground();
/* 63 */       setBackground(null);
/* 64 */     } else if (this.mDrawable != null) {
/* 65 */       setBackground(this.mDrawable);
/*    */     } 
/* 67 */     invalidate();
/* 68 */     refreshDrawableState();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void onDraw(Canvas canvas) {
/* 78 */     if (this.mIsReversed) {
/* 79 */       float px = getWidth() / 2.0F;
/* 80 */       float py = getHeight() / 2.0F;
/* 81 */       canvas.scale(-1.0F, 1.0F, px, py);
/*    */     } 
/* 83 */     super.onDraw(canvas);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean onTouchEvent(MotionEvent event) {
/* 93 */     if (this.mIsReversed) {
/* 94 */       event.setLocation(getWidth() - event.getX(), event.getY());
/*    */     }
/*    */     
/* 97 */     return super.onTouchEvent(event);
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\controls\MirrorSeekBar.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */