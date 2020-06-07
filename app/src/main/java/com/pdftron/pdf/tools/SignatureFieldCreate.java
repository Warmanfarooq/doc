/*    */ package com.pdftron.pdf.tools;
/*    */ 
/*    */ import androidx.annotation.Keep;
/*    */ import androidx.annotation.NonNull;
/*    */ import com.pdftron.common.PDFNetException;
/*    */ import com.pdftron.pdf.Annot;
/*    */ import com.pdftron.pdf.PDFDoc;
/*    */ import com.pdftron.pdf.PDFViewCtrl;
/*    */ import com.pdftron.pdf.Rect;
/*    */ import com.pdftron.pdf.annots.SignatureWidget;
/*    */ import com.pdftron.sdf.Doc;
/*    */ import java.util.UUID;
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
/*    */ 
/*    */ @Keep
/*    */ public class SignatureFieldCreate
/*    */   extends RectCreate
/*    */ {
/*    */   public SignatureFieldCreate(@NonNull PDFViewCtrl ctrl) {
/* 31 */     super(ctrl);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ToolManager.ToolModeBase getToolMode() {
/* 39 */     return ToolManager.ToolMode.FORM_SIGNATURE_CREATE;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getCreateAnnotType() {
/* 44 */     return 19;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected Annot createMarkup(@NonNull PDFDoc doc, Rect bbox) throws PDFNetException {
/* 52 */     SignatureWidget widget = SignatureWidget.create((Doc)doc, bbox, UUID.randomUUID().toString());
/* 53 */     widget.getSDFObj().putString("pdftron", "");
/* 54 */     return (Annot)widget;
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\SignatureFieldCreate.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */