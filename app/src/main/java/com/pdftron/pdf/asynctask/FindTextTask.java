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
/*     */ import com.pdftron.pdf.controls.SearchResultsView;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import org.apache.commons.lang3.ArrayUtils;
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
/*     */ public class FindTextTask
/*     */   extends AsyncTask<Void, Boolean, Integer>
/*     */ {
/*     */   private PDFDoc mPdfDoc;
/*     */   private String mPattern;
/*     */   private int mTextSearchMode;
/*  38 */   private ArrayList<TextSearchResult> mResults = new ArrayList<>();
/*  39 */   private HashMap<TextSearchResult, ArrayList<Double>> mHighlights = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final ArrayList<SearchResultsView.Section> mSectionList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final ArrayList<String> mSectionTitleList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int mPagesSearched;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String mFacingCoverText;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Callback mCallback;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FindTextTask(@NonNull PDFViewCtrl pdfViewCtrl, String pattern, int textSearchMode, ArrayList<SearchResultsView.Section> sectionList, ArrayList<String> sectionTitleList) {
/*  83 */     this.mPdfDoc = pdfViewCtrl.getDoc();
/*  84 */     this.mPattern = pattern;
/*  85 */     this.mTextSearchMode = textSearchMode;
/*  86 */     this.mSectionList = sectionList;
/*  87 */     this.mSectionTitleList = sectionTitleList;
/*  88 */     this.mFacingCoverText = pdfViewCtrl.getContext().getResources().getString(R.string.pref_viewmode_facingcover_short);
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
/*  99 */     this.mCallback = callback;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onPreExecute() {
/* 104 */     if (this.mCallback != null) {
/* 105 */       this.mCallback.onFindTextTaskStarted();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected Integer doInBackground(Void... params) {
/* 111 */     if (this.mPdfDoc == null) {
/* 112 */       return Integer.valueOf(0);
/*     */     }
/*     */     
/* 115 */     TextSearch textSearch = new TextSearch();
/* 116 */     int numResults = 0;
/* 117 */     boolean foundResultOnPage = false;
/*     */     
/* 119 */     synchronized (this.mSectionList) {
/* 120 */       int currentSectionIndex = !this.mSectionList.isEmpty() ? 0 : -1;
/*     */       
/* 122 */       boolean shouldUnlockRead = false;
/*     */       try {
/* 124 */         this.mPdfDoc.lockRead();
/* 125 */         shouldUnlockRead = true;
/* 126 */         this.mPagesSearched = 0;
/*     */         
/* 128 */         if (!textSearch.begin(this.mPdfDoc, this.mPattern, this.mTextSearchMode, -1, -1)) {
/*     */           
/* 130 */           AnalyticsHandlerAdapter.getInstance().sendException(new Exception("pattern: " + this.mPattern + " | mode: " + this.mTextSearchMode));
/* 131 */           return Integer.valueOf(0);
/*     */         } 
/*     */         
/* 134 */         while (!isCancelled()) {
/* 135 */           TextSearchResult result = textSearch.run();
/*     */           
/* 137 */           if (result.getCode() == 2) {
/*     */             
/* 139 */             this.mResults.add(numResults, result);
/* 140 */             foundResultOnPage = true;
/*     */             
/* 142 */             if (currentSectionIndex >= 0) {
/*     */               
/* 144 */               double resultX = -1.0D, resultY = -1.0D;
/* 145 */               Highlights highlights = result.getHighlights();
/*     */ 
/*     */               
/* 148 */               highlights.begin(this.mPdfDoc);
/* 149 */               ArrayList<Double> resultQuads = new ArrayList<>();
/* 150 */               while (highlights.hasNext()) {
/* 151 */                 double[] quads = highlights.getCurrentQuads();
/* 152 */                 int quad_count = quads.length / 8;
/*     */                 
/* 154 */                 if (quad_count == 0) {
/* 155 */                   highlights.next();
/*     */                   continue;
/*     */                 } 
/* 158 */                 Double[] doubleArray = ArrayUtils.toObject(quads);
/* 159 */                 List<Double> list = Arrays.asList(doubleArray);
/* 160 */                 resultQuads.addAll(list);
/* 161 */                 highlights.next();
/*     */               } 
/* 163 */               this.mHighlights.put(result, resultQuads);
/*     */               
/* 165 */               highlights.begin(this.mPdfDoc);
/* 166 */               if (highlights.hasNext()) {
/* 167 */                 double[] quads = highlights.getCurrentQuads();
/* 168 */                 if (quads.length / 8 > 0) {
/* 169 */                   resultX = quads[0];
/* 170 */                   resultY = quads[1];
/*     */                 } 
/*     */               } 
/* 173 */               if (result.getPageNum() < ((SearchResultsView.Section)this.mSectionList.get(0)).mPageNum) {
/* 174 */                 this.mSectionTitleList.add(numResults, this.mFacingCoverText);
/*     */               }
/*     */               else {
/*     */                 
/* 178 */                 for (int index = currentSectionIndex; index < this.mSectionList.size(); index++) {
/* 179 */                   if (index < this.mSectionList.size() - 1) {
/*     */                     
/* 181 */                     SearchResultsView.Section nextSection = this.mSectionList.get(index + 1);
/* 182 */                     boolean inNextSection = false;
/* 183 */                     if (result.getPageNum() < nextSection.mPageNum) {
/* 184 */                       inNextSection = false;
/* 185 */                     } else if (result.getPageNum() == nextSection.mPageNum && resultX >= 0.0D && resultY >= 0.0D) {
/*     */                       
/* 187 */                       if (nextSection.top >= 0.0D) {
/* 188 */                         inNextSection = (resultY < nextSection.top);
/* 189 */                         if (inNextSection && nextSection.left >= 0.0D) {
/* 190 */                           inNextSection = (resultX >= nextSection.left);
/*     */                         }
/* 192 */                       } else if (nextSection.left >= 0.0D) {
/* 193 */                         inNextSection = (resultX >= nextSection.left);
/*     */                       } 
/*     */                     } else {
/* 196 */                       inNextSection = true;
/*     */                     } 
/* 198 */                     if (!inNextSection) {
/* 199 */                       this.mSectionTitleList.add(numResults, ((SearchResultsView.Section)this.mSectionList.get(index)).mBookmark.getTitle());
/* 200 */                       currentSectionIndex = index;
/*     */                       break;
/*     */                     } 
/*     */                   } else {
/* 204 */                     this.mSectionTitleList.add(numResults, ((SearchResultsView.Section)this.mSectionList.get(index)).mBookmark.getTitle());
/* 205 */                     currentSectionIndex = index;
/*     */                     break;
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             } 
/* 211 */             numResults++; continue;
/* 212 */           }  if (result.getCode() == 1) {
/* 213 */             this.mPagesSearched++;
/*     */             
/* 215 */             publishProgress((Object[])new Boolean[] { Boolean.valueOf(foundResultOnPage) });
/* 216 */             foundResultOnPage = false;
/*     */           }
/*     */         
/*     */         }
/*     */       
/* 221 */       } catch (PDFNetException e) {
/* 222 */         AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*     */       } finally {
/* 224 */         if (shouldUnlockRead) {
/* 225 */           Utils.unlockReadQuietly(this.mPdfDoc);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 230 */     return Integer.valueOf(numResults);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onProgressUpdate(Boolean... values) {
/* 235 */     if (this.mCallback != null) {
/* 236 */       this.mCallback.onFindTextTaskProgressUpdated(values[0].booleanValue(), this.mPagesSearched, this.mResults);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onPostExecute(Integer numResults) {
/* 242 */     if (this.mCallback != null) {
/* 243 */       this.mCallback.onFindTextTaskFinished(numResults.intValue(), this.mResults, this.mHighlights);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onCancelled(Integer numResults) {
/* 249 */     if (this.mCallback != null) {
/* 250 */       this.mCallback.onFindTextTaskCancelled();
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isRunning() {
/* 255 */     return (getStatus() == Status.RUNNING);
/*     */   }
/*     */   
/*     */   public boolean isFinished() {
/* 259 */     return (getStatus() == Status.FINISHED);
/*     */   }
/*     */   
/*     */   public String getPattern() {
/* 263 */     return this.mPattern;
/*     */   }
/*     */   
/*     */   public static interface Callback {
/*     */     void onFindTextTaskStarted();
/*     */     
/*     */     void onFindTextTaskProgressUpdated(boolean param1Boolean, int param1Int, ArrayList<TextSearchResult> param1ArrayList);
/*     */     
/*     */     void onFindTextTaskFinished(int param1Int, ArrayList<TextSearchResult> param1ArrayList, HashMap<TextSearchResult, ArrayList<Double>> param1HashMap);
/*     */     
/*     */     void onFindTextTaskCancelled();
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\asynctask\FindTextTask.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */