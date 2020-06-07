/*    */ package com.pdftron.pdf.asynctask;
/*    */ 
/*    */ import android.os.AsyncTask;
/*    */ import androidx.annotation.NonNull;
/*    */ import androidx.annotation.Nullable;
/*    */ import com.pdftron.pdf.PDFDoc;
/*    */ import com.pdftron.pdf.PDFViewCtrl;
/*    */ import com.pdftron.pdf.TextExtractor;
/*    */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GetTextInPageTask
/*    */   extends AsyncTask<Void, Void, String>
/*    */ {
/* 22 */   private TextExtractor mTextExtractor = new TextExtractor();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private Callback mCallback;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public GetTextInPageTask(@NonNull PDFViewCtrl pdfViewCtrl) {
/* 43 */     boolean shouldUnlockRead = false;
/*    */     try {
/* 45 */       PDFDoc pdfDoc = pdfViewCtrl.getDoc();
/* 46 */       if (pdfDoc != null) {
/* 47 */         pdfViewCtrl.docLockRead();
/* 48 */         shouldUnlockRead = true;
/* 49 */         this.mTextExtractor.begin(pdfDoc.getPage(pdfViewCtrl.getCurrentPage()));
/*    */       } 
/* 51 */     } catch (Exception e) {
/* 52 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*    */     } finally {
/* 54 */       if (shouldUnlockRead) {
/* 55 */         pdfViewCtrl.docUnlockRead();
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setCallback(@Nullable Callback callback) {
/* 68 */     this.mCallback = callback;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected String doInBackground(Void... voids) {
/*    */     try {
/* 77 */       return this.mTextExtractor.getAsText();
/* 78 */     } catch (Exception e) {
/* 79 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/* 80 */       return null;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void onPostExecute(String text) {
/* 89 */     super.onPostExecute(text);
/* 90 */     if (this.mCallback != null)
/* 91 */       this.mCallback.getText(text); 
/*    */   }
/*    */   
/*    */   public static interface Callback {
/*    */     void getText(String param1String);
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\asynctask\GetTextInPageTask.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */