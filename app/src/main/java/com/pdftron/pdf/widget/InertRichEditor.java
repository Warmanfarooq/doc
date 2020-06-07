/*    */ package com.pdftron.pdf.widget;
/*    */ 
/*    */ import android.content.Context;
/*    */ import android.util.AttributeSet;
/*    */ import android.view.KeyEvent;
/*    */ import android.view.MotionEvent;
/*    */ import com.pdftron.pdf.widget.richtext.PTRichEditor;
/*    */ 
/*    */ class InertRichEditor
/*    */   extends PTRichEditor {
/*    */   public InertRichEditor(Context context) {
/* 12 */     super(context);
/*    */   }
/*    */   
/*    */   public InertRichEditor(Context context, AttributeSet attrs) {
/* 16 */     super(context, attrs);
/*    */   }
/*    */   
/*    */   public InertRichEditor(Context context, AttributeSet attrs, int defStyleAttr) {
/* 20 */     super(context, attrs, defStyleAttr);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onTouchEvent(MotionEvent event) {
/* 25 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onKeyDown(int keyCode, KeyEvent event) {
/* 30 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
/* 35 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onKeyPreIme(int keyCode, KeyEvent event) {
/* 40 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onKeyShortcut(int keyCode, KeyEvent event) {
/* 45 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onKeyUp(int keyCode, KeyEvent event) {
/* 50 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onTrackballEvent(MotionEvent event) {
/* 55 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onKeyLongPress(int keyCode, KeyEvent event) {
/* 60 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\widget\InertRichEditor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */