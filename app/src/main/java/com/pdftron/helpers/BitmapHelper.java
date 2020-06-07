/*    */ package com.pdftron.helpers;
/*    */ 
/*    */ import android.graphics.Bitmap;
/*    */ 
/*    */ 
/*    */ public class BitmapHelper
/*    */ {
/*    */   public static class CustomBitmap
/*    */   {
/*    */     public Bitmap bitmap;
/*    */     public int[] bitmapArray;
/*    */     private int a;
/*    */     private int b;
/*    */     
/*    */     public CustomBitmap(int param1Int1, int param1Int2) {
/* 16 */       this.a = param1Int1;
/* 17 */       this.b = param1Int2;
/* 18 */       if (param1Int1 != 0 && param1Int2 != 0) {
/* 19 */         this.bitmapArray = new int[param1Int1 * param1Int2];
/*    */       }
/*    */     }
/*    */ 
/*    */     
/*    */     public CustomBitmap(int param1Int1, int param1Int2, Bitmap param1Bitmap) {
/* 25 */       this(param1Int1, param1Int2);
/*    */     }
/*    */     
/*    */     public void finalize() {
/* 29 */       if (this.bitmapArray != null && this.a != 0 && this.b != 0) {
/* 30 */         this.bitmap = Bitmap.createBitmap(this.bitmapArray, this.a, this.b, Bitmap.Config.ARGB_4444);
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public static void drawInRect(Bitmap paramBitmap, Graphics paramGraphics, int paramInt1, int paramInt2) {}
/*    */ 
/*    */   
/*    */   public static int getHeight(Bitmap paramBitmap) {
/* 40 */     return paramBitmap.getHeight();
/*    */   }
/*    */   
/*    */   public static int getWidth(Bitmap paramBitmap) {
/* 44 */     return paramBitmap.getWidth();
/*    */   }
/*    */   
/*    */   public static void createArrayFromBitmap(Bitmap paramBitmap, int[] paramArrayOfint, int paramInt1, int paramInt2) {
/* 48 */     if (paramArrayOfint.length == paramInt1 * paramInt2) {
/* 49 */       paramBitmap.getPixels(paramArrayOfint, 0, paramInt1, 0, 0, paramInt1, paramInt2);
/*    */     }
/*    */   }
/*    */   
/*    */   public static Bitmap getBitmap(int[] paramArrayOfint) {
/* 54 */     if (paramArrayOfint == null) {
/* 55 */       return null;
/*    */     }
/*    */     
/* 58 */     int i = paramArrayOfint[paramArrayOfint.length - 2];
/* 59 */     int j = paramArrayOfint[paramArrayOfint.length - 1];
/*    */     
/*    */     Bitmap bitmap;
/* 62 */     (bitmap = Bitmap.createBitmap(i, j, Bitmap.Config.ARGB_8888)).setPixels(paramArrayOfint, 0, i, 0, 0, i, j);
/* 63 */     return bitmap;
/*    */   }
/*    */   
/*    */   public static byte[] getColor(int[] paramArrayOfint) {
/*    */     byte[] arrayOfByte;
/* 68 */     (arrayOfByte = new byte[3])[0] = (byte)paramArrayOfint[0];
/* 69 */     arrayOfByte[1] = (byte)paramArrayOfint[1];
/* 70 */     arrayOfByte[2] = (byte)paramArrayOfint[2];
/* 71 */     return arrayOfByte;
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\helpers\BitmapHelper.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */