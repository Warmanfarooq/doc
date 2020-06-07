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
/*    */ import com.pdftron.pdf.annots.ListBoxWidget;
/*    */ import com.pdftron.pdf.annots.Widget;
/*    */ import com.pdftron.pdf.utils.Utils;
/*    */ import com.pdftron.sdf.Doc;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Keep
/*    */ public class ListBoxFieldCreate
/*    */   extends ChoiceFieldCreate
/*    */ {
/*    */   public ListBoxFieldCreate(@NonNull PDFViewCtrl ctrl) {
/* 27 */     super(ctrl);
/*    */     
/* 29 */     this.mNextToolMode = getToolMode();
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isCombo() {
/* 34 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isSingleChoice() {
/* 39 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public ToolManager.ToolModeBase getToolMode() {
/* 44 */     return ToolManager.ToolMode.FORM_LIST_BOX_CREATE;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getCreateAnnotType() {
/* 49 */     return 19;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Annot createMarkup(@NonNull PDFDoc doc, Rect bbox) throws PDFNetException {
/* 54 */     ListBoxWidget widget = ListBoxWidget.create((Doc)doc, bbox);
/* 55 */     widget.getField().setFlag(17, true);
/* 56 */     ColorPt colorPt = Utils.color2ColorPt(-1);
/* 57 */     widget.setBackgroundColor(colorPt, 3);
/* 58 */     widget.getSDFObj().putString("pdftron", "");
/*    */     
/* 60 */     setWidgetStyle(doc, (Widget)widget, "");
/*    */     
/* 62 */     return (Annot)widget;
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\ListBoxFieldCreate.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */