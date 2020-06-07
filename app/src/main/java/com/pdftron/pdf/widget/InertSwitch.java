/*    */ package com.pdftron.pdf.widget;
/*    */ 
/*    */ import android.content.Context;
/*    */ import android.util.AttributeSet;
/*    */ import android.view.KeyEvent;
/*    */ import android.view.MotionEvent;
/*    */ import androidx.appcompat.widget.SwitchCompat;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InertSwitch
/*    */   extends SwitchCompat
/*    */ {
/*    */   public InertSwitch(Context context) {
/* 21 */     super(context);
/*    */   }
/*    */   
/*    */   public InertSwitch(Context context, AttributeSet attrs) {
/* 25 */     super(context, attrs);
/*    */   }
/*    */   
/*    */   public InertSwitch(Context context, AttributeSet attrs, int defStyle) {
/* 29 */     super(context, attrs, defStyle);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onTouchEvent(MotionEvent event) {
/* 34 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onKeyDown(int keyCode, KeyEvent event) {
/* 39 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
/* 44 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onKeyPreIme(int keyCode, KeyEvent event) {
/* 49 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onKeyShortcut(int keyCode, KeyEvent event) {
/* 54 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onKeyUp(int keyCode, KeyEvent event) {
/* 59 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onTrackballEvent(MotionEvent event) {
/* 64 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onKeyLongPress(int keyCode, KeyEvent event) {
/* 69 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\widget\InertSwitch.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */