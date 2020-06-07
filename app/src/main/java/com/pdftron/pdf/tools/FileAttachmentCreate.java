/*    */ package com.pdftron.pdf.tools;
/*    */ 
/*    */ import android.graphics.PointF;
/*    */ import androidx.annotation.Keep;
/*    */ import androidx.annotation.NonNull;
/*    */ import com.pdftron.common.PDFNetException;
/*    */ import com.pdftron.pdf.Annot;
/*    */ import com.pdftron.pdf.PDFDoc;
/*    */ import com.pdftron.pdf.PDFViewCtrl;
/*    */ import com.pdftron.pdf.Rect;
/*    */ import com.pdftron.pdf.annots.FileAttachment;
/*    */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*    */ import com.pdftron.pdf.utils.Utils;
/*    */ import com.pdftron.sdf.Doc;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Keep
/*    */ public class FileAttachmentCreate
/*    */   extends SimpleTapShapeCreate
/*    */ {
/*    */   private String mSelectedFilePath;
/*    */   
/*    */   public FileAttachmentCreate(@NonNull PDFViewCtrl ctrl) {
/* 26 */     super(ctrl);
/*    */   }
/*    */   
/*    */   public boolean createFileAttachment(PointF targetPoint, int pageNum, String outputPath) {
/* 30 */     this.mSelectedFilePath = outputPath;
/*    */     
/* 32 */     return createAnnotation(targetPoint, pageNum);
/*    */   }
/*    */ 
/*    */   
/*    */   public void addAnnotation() {
/* 37 */     if (this.mPt2 == null) {
/* 38 */       AnalyticsHandlerAdapter.getInstance().sendException(new Exception("target point is not specified."));
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 43 */     if (this.mPdfViewCtrl == null) {
/*    */       return;
/*    */     }
/*    */     
/* 47 */     setNextToolModeHelper();
/* 48 */     setCurrentDefaultToolModeHelper(getToolMode());
/*    */     
/* 50 */     ((ToolManager)this.mPdfViewCtrl.getToolManager()).onAttachFileSelected(this.mPt2);
/*    */   }
/*    */ 
/*    */   
/*    */   public ToolManager.ToolModeBase getToolMode() {
/* 55 */     return ToolManager.ToolMode.FILE_ATTACHMENT_CREATE;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getCreateAnnotType() {
/* 60 */     return 16;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Annot createMarkup(@NonNull PDFDoc doc, Rect bbox) throws PDFNetException {
/* 65 */     if (Utils.isNullOrEmpty(this.mSelectedFilePath)) {
/* 66 */       return null;
/*    */     }
/* 68 */     FileAttachment fileAttachment = FileAttachment.create((Doc)doc, bbox, this.mSelectedFilePath);
/* 69 */     fileAttachment.setIcon(2);
/* 70 */     return (Annot)fileAttachment;
/*    */   }
/*    */ 
/*    */   
/*    */   public void clearTargetPoint() {
/* 75 */     resetPts();
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\FileAttachmentCreate.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */