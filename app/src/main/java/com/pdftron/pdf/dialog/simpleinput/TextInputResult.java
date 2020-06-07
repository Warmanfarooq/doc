/*    */ package com.pdftron.pdf.dialog.simpleinput;
/*    */ 
/*    */ import androidx.annotation.NonNull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TextInputResult
/*    */ {
/*    */   private final int requestCode;
/*    */   @NonNull
/*    */   private final String result;
/*    */   
/*    */   public TextInputResult(int requestCode, @NonNull String result) {
/* 14 */     this.requestCode = requestCode;
/* 15 */     this.result = result;
/*    */   }
/*    */   
/*    */   public int getRequestCode() {
/* 19 */     return this.requestCode;
/*    */   }
/*    */   
/*    */   @NonNull
/*    */   public String getResult() {
/* 24 */     return this.result;
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\dialog\simpleinput\TextInputResult.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */