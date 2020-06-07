/*    */ package com.pdftron.pdf.controls;
/*    */ 
/*    */ import androidx.fragment.app.DialogFragment;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NavigationListDialogFragment
/*    */   extends DialogFragment
/*    */ {
/*    */   protected AnalyticsEventListener mAnalyticsEventListener;
/*    */   
/*    */   public void setAnalyticsEventListener(AnalyticsEventListener listener) {
/* 33 */     this.mAnalyticsEventListener = listener;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void onEventAction() {
/* 42 */     if (this.mAnalyticsEventListener != null)
/* 43 */       this.mAnalyticsEventListener.onEventAction(); 
/*    */   }
/*    */   
/*    */   public static interface AnalyticsEventListener {
/*    */     void onEventAction();
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\controls\NavigationListDialogFragment.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */