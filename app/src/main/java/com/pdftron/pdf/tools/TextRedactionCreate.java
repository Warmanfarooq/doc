/*    */ package com.pdftron.pdf.tools;
/*    */ 
/*    */ import androidx.annotation.Keep;
/*    */ import androidx.annotation.NonNull;
/*    */ import com.pdftron.common.PDFNetException;
/*    */ import com.pdftron.pdf.Annot;
/*    */ import com.pdftron.pdf.PDFDoc;
/*    */ import com.pdftron.pdf.PDFViewCtrl;
/*    */ import com.pdftron.pdf.Rect;
/*    */ import com.pdftron.pdf.annots.Redaction;
/*    */ import com.pdftron.sdf.Doc;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Keep
/*    */ public class TextRedactionCreate
/*    */   extends TextMarkupCreate
/*    */ {
/*    */   public TextRedactionCreate(@NonNull PDFViewCtrl ctrl) {
/* 20 */     super(ctrl);
/*    */   }
/*    */ 
/*    */   
/*    */   public ToolManager.ToolModeBase getToolMode() {
/* 25 */     return ToolManager.ToolMode.TEXT_REDACTION;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getCreateAnnotType() {
/* 30 */     return 25;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Annot createMarkup(PDFDoc doc, Rect bbox) throws PDFNetException {
/* 35 */     return (Annot)Redaction.create((Doc)doc, bbox);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void createTextMarkup() {
/* 40 */     super.createTextMarkup();
/*    */     
/* 42 */     ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 43 */     if (toolManager.isAutoSelectAnnotation() || !this.mForceSameNextToolMode) {
/* 44 */       this.mNextToolMode = ToolManager.ToolMode.ANNOT_EDIT;
/*    */     } else {
/* 46 */       this.mNextToolMode = getToolMode();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\TextRedactionCreate.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */