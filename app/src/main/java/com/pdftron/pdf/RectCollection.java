/*    */ package com.pdftron.pdf;
/*    */ 
/*    */ import com.pdftron.common.PDFNetException;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RectCollection
/*    */ {
/*    */   public void addRect(Rect paramRect) {
/* 17 */     this.a.add(paramRect);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void addRect(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) throws PDFNetException {
/* 23 */     this.a.add(new Rect(paramDouble1, paramDouble2, paramDouble3, paramDouble4));
/*    */   }
/*    */ 
/*    */   
/*    */   public Rect getRectAt(int paramInt) {
/* 28 */     return this.a.get(paramInt);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getNumRects() {
/* 33 */     return this.a.size();
/*    */   }
/*    */ 
/*    */   
/*    */   public void clear() {
/* 38 */     this.a.clear();
/*    */   }
/*    */   
/* 41 */   private ArrayList<Rect> a = new ArrayList<>();
/*    */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\RectCollection.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */