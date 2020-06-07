/*    */ package com.pdftron.pdf.controls;
/*    */ 
/*    */ import android.util.SparseIntArray;
/*    */ import com.pdftron.pdf.tools.R;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum AnnotationToolbarButtonId
/*    */ {
/* 13 */   PAN(R.id.controls_annotation_toolbar_tool_pan),
/* 14 */   CLOSE(R.id.controls_annotation_toolbar_btn_close),
/* 15 */   OVERFLOW(R.id.controls_annotation_toolbar_btn_more);
/*    */   
/*    */   public final int id;
/*    */   
/*    */   AnnotationToolbarButtonId(int id) {
/* 20 */     this.id = id;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static SparseIntArray getButtonVisibilityArray() {
/* 28 */     SparseIntArray array = new SparseIntArray();
/* 29 */     array.put(R.id.controls_annotation_toolbar_tool_pan, 0);
/* 30 */     array.put(R.id.controls_annotation_toolbar_btn_close, 0);
/* 31 */     array.put(R.id.controls_annotation_toolbar_btn_more, 0);
/* 32 */     return array;
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\controls\AnnotationToolbarButtonId.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */