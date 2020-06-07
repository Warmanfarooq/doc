/*     */ package com.pdftron.pdf.asynctask;
/*     */ 
/*     */ import android.os.AsyncTask;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.Highlights;
/*     */ import com.pdftron.pdf.PDFDoc;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.TextSearch;
/*     */ import com.pdftron.pdf.TextSearchResult;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.Utils;
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
/*     */ public class GenerateHighlightsTask
/*     */   extends AsyncTask<Void, Void, Void>
/*     */ {
/*     */   private int mPageStart;
/*     */   private int mPageEnd;
/*     */   private String mSearchPattern;
/*     */   private Callback mCallback;
/*     */   private int mTextSearchMode;
/*     */   private Highlights[] mHighlights;
/*     */   private final boolean mIsRtl;
/*     */   private final PDFDoc mDoc;
/*     */   
/*     */   public GenerateHighlightsTask(@NonNull PDFViewCtrl pdfViewCtrl, int pageStart, int pageEnd, String searchPattern, boolean matchCase, boolean matchWholeWords, boolean useRegularExpressions) {
/*  75 */     this.mPageStart = pageStart;
/*  76 */     this.mPageEnd = pageEnd;
/*  77 */     this.mSearchPattern = searchPattern;
/*     */     
/*  79 */     this.mIsRtl = pdfViewCtrl.getRightToLeftLanguage();
/*  80 */     this.mDoc = pdfViewCtrl.getDoc();
/*     */     
/*  82 */     this.mTextSearchMode = 48;
/*  83 */     if (matchCase) {
/*  84 */       this.mTextSearchMode |= 0x2;
/*     */     }
/*  86 */     if (matchWholeWords) {
/*  87 */       this.mTextSearchMode |= 0x4;
/*     */     }
/*  89 */     if (useRegularExpressions) {
/*  90 */       this.mTextSearchMode |= 0x1;
/*     */     }
/*     */     
/*  93 */     if (pageEnd > pageStart) {
/*  94 */       this.mHighlights = new Highlights[pageEnd - pageStart + 1];
/*     */     } else {
/*  96 */       this.mHighlights = new Highlights[1];
/*     */     } 
/*  98 */     for (int i = 0, cnt = this.mHighlights.length; i < cnt; i++) {
/*  99 */       this.mHighlights[i] = new Highlights();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCallback(@Nullable Callback callback) {
/* 111 */     this.mCallback = callback;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Void doInBackground(Void... params) {
/* 116 */     TextSearch textSearch = new TextSearch();
/* 117 */     int i = 0;
/*     */     
/* 119 */     if (this.mIsRtl) {
/* 120 */       textSearch.setRightToLeftLanguage(true);
/*     */     }
/*     */     
/* 123 */     boolean shouldUnlockRead = false;
/*     */     try {
/* 125 */       this.mDoc.lockRead();
/* 126 */       shouldUnlockRead = true;
/*     */       
/* 128 */       if (!textSearch.begin(this.mDoc, this.mSearchPattern, this.mTextSearchMode, this.mPageStart, this.mPageEnd))
/*     */       {
/* 130 */         return null;
/*     */       }
/*     */       
/* 133 */       while (!isCancelled()) {
/* 134 */         TextSearchResult result = textSearch.run();
/*     */         
/* 136 */         if (result.getCode() == 2) {
/*     */           
/* 138 */           this.mHighlights[i].add(result.getHighlights()); continue;
/* 139 */         }  if (result.getCode() == 1) {
/* 140 */           i++;
/*     */         }
/*     */       }
/*     */     
/*     */     }
/* 145 */     catch (PDFNetException e) {
/* 146 */       AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*     */     } finally {
/* 148 */       if (shouldUnlockRead) {
/* 149 */         Utils.unlockReadQuietly(this.mDoc);
/*     */       }
/*     */     } 
/*     */     
/* 153 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onCancelled() {
/* 158 */     if (this.mCallback != null) {
/* 159 */       this.mCallback.onHighlightsTaskCancelled(this.mPageStart, this.mPageEnd);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onPostExecute(Void param) {
/* 165 */     if (this.mCallback != null)
/* 166 */       this.mCallback.onHighlightsTaskFinished(this.mHighlights, this.mPageStart, this.mPageEnd); 
/*     */   }
/*     */   
/*     */   public static interface Callback {
/*     */     void onHighlightsTaskCancelled(int param1Int1, int param1Int2);
/*     */     
/*     */     void onHighlightsTaskFinished(Highlights[] param1ArrayOfHighlights, int param1Int1, int param1Int2);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\asynctask\GenerateHighlightsTask.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */