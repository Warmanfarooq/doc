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
/*    */ import com.pdftron.pdf.annots.ComboBoxWidget;
/*    */ import com.pdftron.pdf.annots.Widget;
/*    */ import com.pdftron.pdf.utils.Utils;
/*    */ import com.pdftron.sdf.Doc;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Keep
/*    */ public class ComboBoxFieldCreate
/*    */   extends ChoiceFieldCreate
/*    */ {
/*    */   public ComboBoxFieldCreate(@NonNull PDFViewCtrl ctrl) {
/* 26 */     super(ctrl);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isCombo() {
/* 31 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isSingleChoice() {
/* 36 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public ToolManager.ToolModeBase getToolMode() {
/* 41 */     return ToolManager.ToolMode.FORM_COMBO_BOX_CREATE;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getCreateAnnotType() {
/* 46 */     return 19;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Annot createMarkup(@NonNull PDFDoc doc, Rect bbox) throws PDFNetException {
/* 51 */     ComboBoxWidget widget = ComboBoxWidget.create((Doc)doc, bbox);
/* 52 */     ColorPt colorPt = Utils.color2ColorPt(-1);
/* 53 */     widget.setBackgroundColor(colorPt, 3);
/* 54 */     widget.getSDFObj().putString("pdftron", "");
/*    */     
/* 56 */     setWidgetStyle(doc, (Widget)widget, "");
/*    */     
/* 58 */     return (Annot)widget;
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\ComboBoxFieldCreate.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */