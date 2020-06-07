/*    */ package com.rarepebble.colorpicker;
/*    */ 
/*    */ import android.content.Context;
/*    */ import android.graphics.Bitmap;
/*    */ import android.graphics.Color;
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
/*    */ public class ValueView
/*    */   extends SliderViewBase
/*    */   implements ObservableColor.Observer
/*    */ {
/* 26 */   private ObservableColor observableColor = new ObservableColor(0);
/*    */   
/*    */   public ValueView(Context context) {
/* 29 */     this(context, (AttributeSet)null);
/*    */   }
/*    */   
/*    */   public ValueView(Context context, AttributeSet attrs) {
/* 33 */     super(context, attrs);
/*    */   }
/*    */   
/*    */   public void observeColor(ObservableColor observableColor) {
/* 37 */     this.observableColor = observableColor;
/* 38 */     observableColor.addObserver(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateColor(ObservableColor observableColor) {
/* 43 */     setPos(this.observableColor.getValue());
/* 44 */     updateBitmap();
/* 45 */     invalidate();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void notifyListener(float currentPos) {
/* 50 */     this.observableColor.updateValue(currentPos, this);
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getPointerColor(float currentPos) {
/* 55 */     float brightColorLightness = this.observableColor.getLightness();
/* 56 */     float posLightness = currentPos * brightColorLightness;
/* 57 */     return (posLightness > 0.5F) ? -16777216 : -1;
/*    */   }
/*    */   
/*    */   protected Bitmap makeBitmap(int w, int h) {
/* 61 */     boolean isWide = (w > h);
/* 62 */     int n = Math.max(w, h);
/* 63 */     int[] colors = new int[n];
/*    */     
/* 65 */     float[] hsv = { 0.0F, 0.0F, 0.0F };
/* 66 */     this.observableColor.getHsv(hsv);
/*    */     
/* 68 */     for (int i = 0; i < n; i++) {
/* 69 */       hsv[2] = isWide ? (i / n) : (1.0F - i / n);
/* 70 */       colors[i] = Color.HSVToColor(hsv);
/*    */     } 
/* 72 */     int bmpWidth = isWide ? w : 1;
/* 73 */     int bmpHeight = isWide ? 1 : h;
/* 74 */     return Bitmap.createBitmap(colors, bmpWidth, bmpHeight, Bitmap.Config.ARGB_8888);
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\rarepebble\colorpicker\ValueView.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */