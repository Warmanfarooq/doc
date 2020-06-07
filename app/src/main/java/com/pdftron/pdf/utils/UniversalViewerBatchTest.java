/*     */ package com.pdftron.pdf.utils;
/*     */ 
/*     */ import android.app.AlertDialog;
/*     */ import android.content.Context;
/*     */ import android.content.DialogInterface;
/*     */ import android.util.Log;
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.Convert;
/*     */ import com.pdftron.pdf.DocumentConversion;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import java.io.File;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UniversalViewerBatchTest
/*     */   implements PDFViewCtrl.UniversalDocumentConversionListener
/*     */ {
/*  33 */   private int mCurrentFileConvertIndex = 1;
/*     */   
/*     */   private int mTotalFileCount;
/*     */   private File[] mFileList;
/*     */   private PDFViewCtrl mPdfViewCtrl;
/*     */   private boolean mPassing;
/*     */   private Context mContext;
/*     */   private PDFViewCtrl.UniversalDocumentConversionListener mOriginalConversionListener;
/*     */   
/*     */   public UniversalViewerBatchTest(Context context, PDFViewCtrl pdfViewCtrl, PDFViewCtrl.UniversalDocumentConversionListener originalConversionListener) {
/*  43 */     this.mPdfViewCtrl = pdfViewCtrl;
/*  44 */     this.mContext = context;
/*  45 */     this.mOriginalConversionListener = originalConversionListener;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/*  51 */     File directory = new File(Utils.getExternalDownloadDirectory(this.mContext), "UniversalDocTest/");
/*  52 */     if (!directory.exists() || !directory.isDirectory()) {
/*  53 */       Log.d(getClass().getName(), "UniversalDocTest folder does not exist or is not a directory");
/*  54 */       showPostDialog(false);
/*     */       return;
/*     */     } 
/*  57 */     this.mFileList = directory.listFiles();
/*  58 */     this.mTotalFileCount = this.mFileList.length;
/*  59 */     this.mCurrentFileConvertIndex = 0;
/*  60 */     this.mPassing = true;
/*     */     
/*  62 */     if (this.mTotalFileCount < 1) {
/*  63 */       Log.d(getClass().getName(), "UniversalDocTest folder does not have any files!");
/*  64 */       showPostDialog(false);
/*     */       
/*     */       return;
/*     */     } 
/*  68 */     this.mPdfViewCtrl.addUniversalDocumentConversionListener(this);
/*  69 */     testUniversalFile(0);
/*     */   }
/*     */   
/*     */   private void showPostDialog(boolean pass) {
/*  73 */     this.mPdfViewCtrl.addUniversalDocumentConversionListener(this.mOriginalConversionListener);
/*  74 */     AlertDialog.Builder aBuilder = new AlertDialog.Builder(this.mContext);
/*  75 */     if (!pass) {
/*  76 */       aBuilder.setMessage("Error has occurred while performing batch test. Please check log for details.");
/*     */     } else {
/*  78 */       aBuilder.setMessage("Batch test success!");
/*     */     } 
/*  80 */     aBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener()
/*     */         {
/*     */           public void onClick(DialogInterface dialog, int which) {
/*  83 */             dialog.dismiss();
/*     */           }
/*     */         });
/*  86 */     aBuilder.create().show();
/*     */   }
/*     */   
/*     */   private void testUniversalFile(int index) {
/*     */     try {
/*  91 */       DocumentConversion conversion = Convert.universalConversion(this.mFileList[index].getAbsolutePath(), null);
/*  92 */       this.mPdfViewCtrl.closeDoc();
/*  93 */       this.mPdfViewCtrl.openUniversalDocument(conversion);
/*  94 */     } catch (PDFNetException e) {
/*  95 */       conversionFinish(false);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void conversionFinish(boolean pass) {
/* 100 */     if (!pass) {
/* 101 */       Log.d(getClass().getName(), "Document " + this.mCurrentFileConvertIndex + " (" + this.mFileList[this.mCurrentFileConvertIndex].getName() + ") FAIL!");
/* 102 */       this.mPassing = false;
/*     */     } else {
/* 104 */       Log.d(getClass().getName(), "Document " + this.mCurrentFileConvertIndex + " (" + this.mFileList[this.mCurrentFileConvertIndex].getName() + ") pass");
/*     */     } 
/* 106 */     this.mCurrentFileConvertIndex++;
/* 107 */     if (this.mCurrentFileConvertIndex < this.mTotalFileCount) {
/* 108 */       testUniversalFile(this.mCurrentFileConvertIndex);
/*     */     } else {
/* 110 */       showPostDialog(this.mPassing);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onConversionEvent(PDFViewCtrl.ConversionState state, int totalPagesConverted) {
/* 116 */     switch (state) {
/*     */       case FAILED:
/* 118 */         conversionFinish(false);
/*     */         break;
/*     */       case FINISHED:
/* 121 */         conversionFinish(true);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pd\\utils\UniversalViewerBatchTest.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */