/*    */ package com.pdftron.pdf.controls;
/*    */ 
/*    */ import android.content.Context;
/*    */ import android.view.View;
/*    */ import android.widget.TextView;
/*    */ import com.pdftron.pdf.tools.R;
/*    */ import com.pdftron.pdf.tools.UndoRedoManager;
/*    */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*    */ import com.pdftron.pdf.utils.AnalyticsParam;
/*    */ import com.pdftron.pdf.utils.Utils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AnnotToolbarOverflowPopupWindow
/*    */   extends UndoRedoPopupWindow
/*    */ {
/*    */   private AnnotationToolbar mAnnotationToolbar;
/*    */   
/*    */   public AnnotToolbarOverflowPopupWindow(Context context, UndoRedoManager undoRedoManager, OnUndoRedoListener listener, AnnotationToolbar annotationToolbar) {
/* 22 */     this(context, undoRedoManager, listener, R.layout.dialog_annot_toolbar_overflow, 2, annotationToolbar);
/*    */   }
/*    */   
/*    */   protected AnnotToolbarOverflowPopupWindow(Context context, UndoRedoManager undoRedoManager, OnUndoRedoListener listener, int layoutResource, int locationId, AnnotationToolbar annotationToolbar) {
/* 26 */     super(context, undoRedoManager, listener, layoutResource, locationId);
/* 27 */     setAnnotationToolbar(annotationToolbar);
/* 28 */     init();
/*    */   }
/*    */   
/*    */   private void init() {
/* 32 */     TextView showMoreTitle = (TextView)getContentView().findViewById(R.id.show_more_title);
/* 33 */     showMoreTitle.setText(this.mAnnotationToolbar.isExpanded() ? R.string.show_fewer_tools : R.string.show_all_tools);
/* 34 */     showMoreTitle.setOnClickListener(new View.OnClickListener()
/*    */         {
/*    */           public void onClick(View v) {
/* 37 */             AnnotToolbarOverflowPopupWindow.this.mAnnotationToolbar.toggleExpanded();
/* 38 */             AnalyticsHandlerAdapter.getInstance().sendEvent(58, 
/* 39 */                 AnalyticsParam.showAllToolsParam(AnnotToolbarOverflowPopupWindow.this.mAnnotationToolbar.isExpanded()));
/* 40 */             AnnotToolbarOverflowPopupWindow.this.dismiss();
/*    */           }
/*    */         });
/*    */     
/* 44 */     if (!this.mAnnotationToolbar.isExpanded() && (
/* 45 */       Utils.isLandscape(showMoreTitle.getContext()) || Utils.isTablet(showMoreTitle.getContext()))) {
/* 46 */       showMoreTitle.setVisibility(8);
/*    */     }
/*    */   }
/*    */   
/*    */   private void setAnnotationToolbar(AnnotationToolbar annotationToolbar) {
/* 51 */     this.mAnnotationToolbar = annotationToolbar;
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\controls\AnnotToolbarOverflowPopupWindow.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */