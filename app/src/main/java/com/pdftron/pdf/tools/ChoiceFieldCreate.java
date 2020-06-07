/*    */ package com.pdftron.pdf.tools;
/*    */ 
/*    */ import androidx.annotation.Keep;
/*    */ import com.pdftron.pdf.Annot;
/*    */ import com.pdftron.pdf.PDFViewCtrl;
/*    */ 
/*    */ @Keep
/*    */ public abstract class ChoiceFieldCreate
/*    */   extends TextFieldCreate
/*    */ {
/*    */   public ChoiceFieldCreate(PDFViewCtrl ctrl) {
/* 12 */     super(ctrl);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void raiseAnnotationAddedEvent(Annot annot, int page) {
/* 17 */     super.raiseAnnotationAddedEvent(annot, page);
/*    */     
/* 19 */     if (annot != null)
/*    */     {
/* 21 */       showWidgetChoiceDialog(annot.__GetHandle(), page, isSingleChoice(), isCombo(), null);
/*    */     }
/*    */   }
/*    */   
/*    */   protected abstract boolean isCombo();
/*    */   
/*    */   protected abstract boolean isSingleChoice();
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\ChoiceFieldCreate.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */