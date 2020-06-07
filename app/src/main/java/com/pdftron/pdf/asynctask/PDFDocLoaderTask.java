/*    */ package com.pdftron.pdf.asynctask;
/*    */ 
/*    */ import android.content.Context;
/*    */ import android.net.Uri;
/*    */ import com.pdftron.filters.Filter;
/*    */ import com.pdftron.filters.SecondaryFileFilter;
/*    */ import com.pdftron.pdf.PDFDoc;
/*    */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*    */ import com.pdftron.pdf.utils.CustomAsyncTask;
/*    */ import com.pdftron.pdf.utils.Utils;
/*    */ 
/*    */ public class PDFDocLoaderTask
/*    */   extends CustomAsyncTask<Uri, Void, PDFDoc> {
/*    */   private onFinishListener mListener;
/*    */   
/*    */   public PDFDocLoaderTask(Context context) {
/* 17 */     super(context);
/*    */   }
/*    */   
/*    */   public PDFDocLoaderTask setFinishCallback(onFinishListener listener) {
/* 21 */     this.mListener = listener;
/* 22 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   protected PDFDoc doInBackground(Uri... uris) {
/* 27 */     if (uris.length < 1) {
/* 28 */       return null;
/*    */     }
/* 30 */     Uri uri = uris[0];
/* 31 */     Context context = getContext();
/* 32 */     if (uri == null || context == null) {
/* 33 */       return null;
/*    */     }
/*    */     
/* 36 */     SecondaryFileFilter fileFilter = null;
/* 37 */     PDFDoc pdfDoc = null;
/*    */     try {
/* 39 */       fileFilter = new SecondaryFileFilter(context, uri);
/* 40 */       pdfDoc = new PDFDoc((Filter)fileFilter);
/* 41 */       return pdfDoc;
/* 42 */     } catch (Exception ex) {
/* 43 */       AnalyticsHandlerAdapter.getInstance().sendException(ex);
/* 44 */       return null;
/*    */     } finally {
/* 46 */       if (pdfDoc == null) {
/* 47 */         Utils.closeQuietly(fileFilter);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onPostExecute(PDFDoc pdfDoc) {
/* 54 */     super.onPostExecute(pdfDoc);
/* 55 */     if (this.mListener != null) {
/* 56 */       this.mListener.onFinish(pdfDoc);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onCancelled(PDFDoc pdfDoc) {
/* 62 */     super.onCancelled(pdfDoc);
/*    */     
/* 64 */     if (this.mListener != null)
/* 65 */       this.mListener.onCancelled(); 
/*    */   }
/*    */   
/*    */   public static interface onFinishListener {
/*    */     void onFinish(PDFDoc param1PDFDoc);
/*    */     
/*    */     void onCancelled();
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\asynctask\PDFDocLoaderTask.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */