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
/*    */ import com.pdftron.pdf.annots.RadioButtonGroup;
/*    */ import com.pdftron.pdf.annots.RadioButtonWidget;
/*    */ import com.pdftron.pdf.utils.Utils;
/*    */ import com.pdftron.sdf.Doc;
/*    */ import java.util.UUID;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Keep
/*    */ public class RadioGroupFieldCreate
/*    */   extends RectCreate
/*    */ {
/* 26 */   private RadioButtonGroup mTargetGroup = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public RadioGroupFieldCreate(@NonNull PDFViewCtrl ctrl) {
/* 34 */     super(ctrl);
/*    */   }
/*    */   
/*    */   public void setTargetGroup(RadioButtonGroup group) {
/* 38 */     this.mTargetGroup = group;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Annot createMarkup(@NonNull PDFDoc doc, Rect bbox) throws PDFNetException {
/* 43 */     if (null == this.mTargetGroup) {
/* 44 */       this.mTargetGroup = RadioButtonGroup.create((Doc)doc, UUID.randomUUID().toString());
/*    */     }
/* 46 */     RadioButtonWidget annot = this.mTargetGroup.add(bbox);
/* 47 */     annot.getSDFObj().putString("pdftron", "");
/* 48 */     ColorPt colorPt = Utils.color2ColorPt(-1);
/* 49 */     annot.setBackgroundColor(colorPt, 3);
/* 50 */     return (Annot)annot;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ToolManager.ToolModeBase getToolMode() {
/* 58 */     return ToolManager.ToolMode.FORM_RADIO_GROUP_CREATE;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getCreateAnnotType() {
/* 63 */     return 19;
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\RadioGroupFieldCreate.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */