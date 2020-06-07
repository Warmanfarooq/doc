/*    */ package com.pdftron.pdf.tools;
/*    */ 
/*    */ import androidx.annotation.Keep;
/*    */ import androidx.annotation.NonNull;
/*    */ import com.pdftron.common.PDFNetException;
/*    */ import com.pdftron.pdf.Annot;
/*    */ import com.pdftron.pdf.PDFDoc;
/*    */ import com.pdftron.pdf.PDFViewCtrl;
/*    */ import com.pdftron.pdf.Rect;
/*    */ import com.pdftron.pdf.annots.Underline;
/*    */ import com.pdftron.sdf.Doc;
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
/*    */ 
/*    */ @Keep
/*    */ public class TextUnderlineCreate
/*    */   extends TextMarkupCreate
/*    */ {
/*    */   public TextUnderlineCreate(@NonNull PDFViewCtrl ctrl) {
/* 29 */     super(ctrl);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ToolManager.ToolModeBase getToolMode() {
/* 37 */     return ToolManager.ToolMode.TEXT_UNDERLINE;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getCreateAnnotType() {
/* 42 */     return 9;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected Annot createMarkup(PDFDoc doc, Rect bbox) throws PDFNetException {
/* 50 */     return (Annot)Underline.create((Doc)doc, bbox);
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\TextUnderlineCreate.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */