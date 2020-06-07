/*    */ package com.pdftron.pdf.annots;
/*    */ 
/*    */ import com.pdftron.common.PDFNetException;
/*    */ import com.pdftron.pdf.Annot;
/*    */ import com.pdftron.sdf.Obj;
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
/*    */ 
/*    */ 
/*    */ public class RadioButtonWidget
/*    */   extends Widget
/*    */ {
/*    */   public RadioButtonWidget(Obj paramObj) {
/* 25 */     super(paramObj);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public RadioButtonWidget() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   RadioButtonWidget(long paramLong, Object paramObject) {
/* 44 */     super(paramLong, paramObject);
/*    */   }
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
/*    */   public RadioButtonWidget(Annot paramAnnot) throws PDFNetException {
/* 59 */     super(paramAnnot.getSDFObj());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public RadioButtonGroup getGroup() throws PDFNetException {
/* 70 */     return new RadioButtonGroup(GetGroup(__GetHandle()), __GetRefHandle());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void enableButton() throws PDFNetException {
/* 80 */     EnableButton(__GetHandle());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isEnabled() throws PDFNetException {
/* 91 */     return IsEnabled(__GetHandle());
/*    */   }
/*    */   
/*    */   static native long GetGroup(long paramLong);
/*    */   
/*    */   static native void EnableButton(long paramLong);
/*    */   
/*    */   static native boolean IsEnabled(long paramLong);
/*    */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\annots\RadioButtonWidget.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */