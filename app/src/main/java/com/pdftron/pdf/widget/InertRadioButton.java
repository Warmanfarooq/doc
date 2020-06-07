/*    */ package com.pdftron.pdf.widget;
/*    */ 
/*    */ import android.content.Context;
/*    */ import android.util.AttributeSet;
/*    */ import android.view.KeyEvent;
/*    */ import android.view.MotionEvent;
/*    */ import androidx.appcompat.widget.AppCompatRadioButton;
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
/*    */ public class InertRadioButton
/*    */   extends AppCompatRadioButton
/*    */ {
/*    */   public InertRadioButton(Context context) {
/* 22 */     super(context);
/*    */   }
/*    */   
/*    */   public InertRadioButton(Context context, AttributeSet attrs) {
/* 26 */     super(context, attrs);
/*    */   }
/*    */   
/*    */   public InertRadioButton(Context context, AttributeSet attrs, int defStyle) {
/* 30 */     super(context, attrs, defStyle);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onTouchEvent(MotionEvent event) {
/* 35 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onKeyDown(int keyCode, KeyEvent event) {
/* 40 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
/* 45 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onKeyPreIme(int keyCode, KeyEvent event) {
/* 50 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onKeyShortcut(int keyCode, KeyEvent event) {
/* 55 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onKeyUp(int keyCode, KeyEvent event) {
/* 60 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onTrackballEvent(MotionEvent event) {
/* 65 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onKeyLongPress(int keyCode, KeyEvent event) {
/* 70 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\widget\InertRadioButton.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */