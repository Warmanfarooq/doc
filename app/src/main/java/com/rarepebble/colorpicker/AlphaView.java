/*    */ package com.rarepebble.colorpicker;
/*    */ 
/*    */ import android.content.Context;
/*    */ import android.graphics.Bitmap;
/*    */ import android.util.AttributeSet;
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
/*    */ public class AlphaView
/*    */   extends SliderViewBase
/*    */   implements ObservableColor.Observer
/*    */ {
/* 25 */   private ObservableColor observableColor = new ObservableColor(0);
/*    */   
/*    */   public AlphaView(Context context) {
/* 28 */     this(context, (AttributeSet)null);
/*    */   }
/*    */   
/*    */   public AlphaView(Context context, AttributeSet attrs) {
/* 32 */     super(context, attrs);
/*    */   }
/*    */   
/*    */   public void observeColor(ObservableColor observableColor) {
/* 36 */     this.observableColor = observableColor;
/* 37 */     observableColor.addObserver(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateColor(ObservableColor observableColor) {
/* 42 */     setPos(observableColor.getAlpha() / 255.0F);
/* 43 */     updateBitmap();
/* 44 */     invalidate();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void notifyListener(float currentPos) {
/* 49 */     this.observableColor.updateAlpha((int)(currentPos * 255.0F), this);
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getPointerColor(float currentPos) {
/* 54 */     float solidColorLightness = this.observableColor.getLightness();
/* 55 */     float posLightness = 1.0F + currentPos * (solidColorLightness - 1.0F);
/* 56 */     return (posLightness > 0.5F) ? -16777216 : -1;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Bitmap makeBitmap(int w, int h) {
/* 61 */     boolean isWide = (w > h);
/* 62 */     int n = Math.max(w, h);
/* 63 */     int color = this.observableColor.getColor();
/* 64 */     int[] colors = new int[n];
/* 65 */     for (int i = 0; i < n; i++) {
/* 66 */       float alpha = isWide ? (i / n) : (1.0F - i / n);
/* 67 */       colors[i] = color & 0xFFFFFF | (int)(alpha * 255.0F) << 24;
/*    */     } 
/* 69 */     int bmpWidth = isWide ? w : 1;
/* 70 */     int bmpHeight = isWide ? 1 : h;
/* 71 */     return Bitmap.createBitmap(colors, bmpWidth, bmpHeight, Bitmap.Config.ARGB_8888);
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\rarepebble\colorpicker\AlphaView.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */