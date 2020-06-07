/*    */ package com.pdftron.pdf.tools;
/*    */ 
/*    */ import androidx.annotation.Keep;
/*    */ import androidx.annotation.NonNull;
/*    */ import com.pdftron.common.PDFNetException;
/*    */ import com.pdftron.pdf.Annot;
/*    */ import com.pdftron.pdf.ColorPt;
/*    */ import com.pdftron.pdf.PDFDoc;
/*    */ import com.pdftron.pdf.PDFViewCtrl;
/*    */ import com.pdftron.pdf.Rect;
/*    */ import com.pdftron.pdf.annots.CheckBoxWidget;
/*    */ import com.pdftron.pdf.utils.Utils;
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
/*    */ @Keep
/*    */ public class CheckboxFieldCreate
/*    */   extends RectCreate
/*    */ {
/*    */   public CheckboxFieldCreate(@NonNull PDFViewCtrl ctrl) {
/* 31 */     super(ctrl);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ToolManager.ToolModeBase getToolMode() {
/* 39 */     return ToolManager.ToolMode.FORM_CHECKBOX_CREATE;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Annot createMarkup(@NonNull PDFDoc doc, Rect bbox) throws PDFNetException {
/* 44 */     CheckBoxWidget widget = CheckBoxWidget.create((Doc)doc, bbox, UUID.randomUUID().toString());
/* 45 */     ColorPt colorPt = Utils.color2ColorPt(-1);
/* 46 */     widget.setBackgroundColor(colorPt, 3);
/* 47 */     widget.getSDFObj().putString("pdftron", "");
/* 48 */     return (Annot)widget;
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\CheckboxFieldCreate.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */