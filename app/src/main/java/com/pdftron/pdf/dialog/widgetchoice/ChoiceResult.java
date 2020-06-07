/*    */ package com.pdftron.pdf.dialog.widgetchoice;
/*    */ 
/*    */ import androidx.annotation.Nullable;
/*    */ 
/*    */ public class ChoiceResult {
/*    */   private final long widget;
/*    */   private final int page;
/*    */   private final boolean singleChoice;
/*    */   @Nullable
/*    */   private final String[] options;
/*    */   
/*    */   public ChoiceResult(long widget, int page, boolean singleChoice, @Nullable String[] options) {
/* 13 */     this.widget = widget;
/* 14 */     this.page = page;
/* 15 */     this.singleChoice = singleChoice;
/* 16 */     this.options = options;
/*    */   }
/*    */   
/*    */   public long getWidget() {
/* 20 */     return this.widget;
/*    */   }
/*    */   
/*    */   public int getPage() {
/* 24 */     return this.page;
/*    */   }
/*    */   
/*    */   public boolean isSingleChoice() {
/* 28 */     return this.singleChoice;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public String[] getOptions() {
/* 33 */     return this.options;
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\dialog\widgetchoice\ChoiceResult.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */