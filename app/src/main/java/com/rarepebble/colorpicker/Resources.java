/*    */ package com.rarepebble.colorpicker;
/*    */ 
/*    */ import android.content.Context;
/*    */ import android.graphics.Bitmap;
/*    */ import android.graphics.BitmapFactory;
/*    */ import android.graphics.BitmapShader;
/*    */ import android.graphics.Paint;
/*    */ import android.graphics.Path;
/*    */ import android.graphics.Shader;
/*    */ import android.util.DisplayMetrics;
/*    */ import android.util.TypedValue;
/*    */ import com.pdftron.pdf.tools.R;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ class Resources
/*    */ {
/*    */   private static final float LINE_WIDTH_DIP = 1.5F;
/*    */   private static final float POINTER_RADIUS_DIP = 7.0F;
/*    */   private static final int VIEW_OUTLINE_COLOR = -8355712;
/*    */   
/*    */   public static Paint makeLinePaint(Context context) {
/* 38 */     Paint paint = new Paint();
/* 39 */     paint.setColor(-8355712);
/* 40 */     paint.setStrokeWidth(dipToPixels(context, 1.5F));
/* 41 */     paint.setStyle(Paint.Style.STROKE);
/* 42 */     paint.setAntiAlias(true);
/* 43 */     return paint;
/*    */   }
/*    */   
/*    */   public static Paint makeCheckerPaint(Context context) {
/* 47 */     Paint paint = new Paint();
/* 48 */     Bitmap checkerBmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.checker_background);
/* 49 */     paint.setShader((Shader)new BitmapShader(checkerBmp, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
/* 50 */     paint.setStrokeWidth(dipToPixels(context, 1.5F));
/* 51 */     paint.setStyle(Paint.Style.FILL);
/* 52 */     paint.setAntiAlias(true);
/* 53 */     return paint;
/*    */   }
/*    */   
/*    */   public static Path makePointerPath(Context context) {
/* 57 */     Path pointerPath = new Path();
/* 58 */     float radiusPx = dipToPixels(context, 7.0F);
/* 59 */     pointerPath.addCircle(0.0F, 0.0F, radiusPx, Path.Direction.CW);
/* 60 */     return pointerPath;
/*    */   }
/*    */   
/*    */   public static float dipToPixels(Context context, float dipValue) {
/* 64 */     DisplayMetrics metrics = context.getResources().getDisplayMetrics();
/* 65 */     return TypedValue.applyDimension(1, dipValue, metrics);
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\rarepebble\colorpicker\Resources.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */