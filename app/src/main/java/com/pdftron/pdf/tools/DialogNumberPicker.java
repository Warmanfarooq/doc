/*    */ package com.pdftron.pdf.tools;
/*    */ 
/*    */ import android.annotation.TargetApi;
/*    */ import android.app.AlertDialog;
/*    */ import android.content.Context;
/*    */ import android.view.LayoutInflater;
/*    */ import android.view.View;
/*    */ import android.widget.NumberPicker;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @TargetApi(11)
/*    */ public class DialogNumberPicker
/*    */   extends AlertDialog
/*    */ {
/*    */   private NumberPicker mInteger;
/*    */   private NumberPicker mDecimal;
/*    */   
/*    */   public DialogNumberPicker(Context context, float val) {
/* 25 */     super(context);
/*    */     
/* 27 */     LayoutInflater inflater = (LayoutInflater)context.getSystemService("layout_inflater");
/* 28 */     View view = inflater.inflate(R.layout.tools_dialog_numberpicker, null);
/* 29 */     this.mInteger = (NumberPicker)view.findViewById(R.id.tools_dialog_numberpicker_integer);
/* 30 */     this.mDecimal = (NumberPicker)view.findViewById(R.id.tools_dialog_numberpicker_decimal);
/*    */     
/* 32 */     this.mInteger.setMinValue(0);
/* 33 */     this.mInteger.setMaxValue(50);
/* 34 */     this.mInteger.setOnLongPressUpdateInterval(50L);
/* 35 */     this.mInteger.setValue((int)val);
/*    */     
/* 37 */     this.mDecimal.setMinValue(0);
/* 38 */     this.mDecimal.setMaxValue(9);
/* 39 */     val -= (int)val;
/* 40 */     val = (float)Math.floor((val * 10.0F + 0.5F));
/* 41 */     this.mDecimal.setValue((int)val);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 47 */     setView(view);
/*    */   }
/*    */   
/*    */   public float getNumber() {
/* 51 */     float v = this.mInteger.getValue() + this.mDecimal.getValue() / 10.0F;
/* 52 */     return v;
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\DialogNumberPicker.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */