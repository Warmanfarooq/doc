/*    */ package com.pdftron.pdf.tools;
/*    */ 
/*    */ import androidx.annotation.Keep;
/*    */ import androidx.annotation.NonNull;
/*    */ import com.pdftron.common.PDFNetException;
/*    */ import com.pdftron.pdf.PDFViewCtrl;
/*    */ import com.pdftron.pdf.utils.AnnotUtils;
/*    */ import com.pdftron.pdf.utils.Utils;
/*    */ import org.json.JSONException;
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
/*    */ 
/*    */ @Keep
/*    */ public class FreeTextSpacingCreate
/*    */   extends FreeTextCreate
/*    */ {
/*    */   public FreeTextSpacingCreate(@NonNull PDFViewCtrl ctrl) {
/* 26 */     super(ctrl);
/*    */     
/* 28 */     this.mFreeTextInlineToggleEnabled = false;
/*    */   }
/*    */ 
/*    */   
/*    */   public ToolManager.ToolModeBase getToolMode() {
/* 33 */     return ToolManager.ToolMode.FREE_TEXT_SPACING_CREATE;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getCreateAnnotType() {
/* 38 */     return 1010;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void inlineTextEditing(String interimText) {
/* 43 */     super.inlineTextEditing(interimText);
/*    */ 
/*    */     
/* 46 */     if (Utils.isLollipop()) {
/* 47 */       this.mInlineEditText.getEditText().addLetterSpacingHandle();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected void createAnnot(String contents) throws PDFNetException, JSONException {
/* 53 */     super.createAnnot(contents);
/*    */ 
/*    */     
/* 56 */     if (this.mInlineEditText != null && this.mInlineEditText.getEditText() != null) {
/* 57 */       AnnotUtils.applyCustomFreeTextAppearance(this.mPdfViewCtrl, this.mInlineEditText
/* 58 */           .getEditText(), this.mAnnot, this.mAnnotPageNum);
/*    */       
/* 60 */       this.mPdfViewCtrl.update(this.mAnnot, this.mPageNum);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\FreeTextSpacingCreate.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */