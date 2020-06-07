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
/*    */ public class RectRedactionCreate
/*    */   extends RectCreate
/*    */ {
/*    */   public RectRedactionCreate(@NonNull PDFViewCtrl ctrl) {
/* 20 */     super(ctrl);
/*    */   }
/*    */ 
/*    */   
/*    */   public ToolManager.ToolModeBase getToolMode() {
/* 25 */     return ToolManager.ToolMode.RECT_REDACTION;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getCreateAnnotType() {
/* 30 */     return 25;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Annot createMarkup(@NonNull PDFDoc doc, Rect bbox) throws PDFNetException {
/* 35 */     return (Annot)Redaction.create((Doc)doc, bbox);
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\RectRedactionCreate.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */