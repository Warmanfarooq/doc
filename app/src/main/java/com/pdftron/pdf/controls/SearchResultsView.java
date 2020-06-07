/*     */ package com.pdftron.pdf.controls;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.os.AsyncTask;
/*     */ import android.util.AttributeSet;
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.view.animation.Animation;
/*     */ import android.view.animation.AnimationUtils;
/*     */ import android.widget.ProgressBar;
/*     */ import android.widget.RelativeLayout;
/*     */ import android.widget.TextView;
/*     */ import androidx.annotation.AttrRes;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.recyclerview.widget.DividerItemDecoration;
/*     */ import androidx.recyclerview.widget.LinearLayoutManager;
/*     */ import androidx.recyclerview.widget.RecyclerView;
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.Action;
/*     */ import com.pdftron.pdf.Bookmark;
/*     */ import com.pdftron.pdf.Destination;
/*     */ import com.pdftron.pdf.PDFDoc;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.TextSearchResult;
/*     */ import com.pdftron.pdf.asynctask.FindTextTask;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.CommonToast;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import com.pdftron.pdf.widget.recyclerview.ItemClickHelper;
/*     */ import com.pdftron.sdf.DictIterator;
/*     */ import com.pdftron.sdf.Obj;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ public class SearchResultsView
/*     */   extends RelativeLayout
/*     */   implements FindTextTask.Callback {
/*     */   private TextView mEmptyView;
/*     */   private RelativeLayout mProgressLayout;
/*     */   private ProgressBar mProgressBar;
/*     */   private TextView mProgressText;
/*     */   protected SearchResultsAdapter mAdapter;
/*     */   
/*     */   public enum SearchResultStatus {
/*  48 */     NOT_HANDLED(0),
/*  49 */     HANDLED(1),
/*  50 */     USE_FINDTEXT(2),
/*  51 */     USE_FINDTEXT_FROM_END(3);
/*     */     
/*     */     private final int value;
/*     */     
/*     */     SearchResultStatus(int value) {
/*  56 */       this.value = value;
/*     */     }
/*     */     
/*     */     public int getValue() {
/*  60 */       return this.value;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  70 */   private final ArrayList<Section> mSectionList = new ArrayList<>();
/*  71 */   protected final ArrayList<String> mSectionTitleList = new ArrayList<>();
/*  72 */   protected ArrayList<TextSearchResult> mSearchResultList = new ArrayList<>();
/*  73 */   protected HashMap<TextSearchResult, ArrayList<Double>> mSearchResultHighlightList = new HashMap<>();
/*  74 */   protected int mCurrentResult = -1;
/*     */   
/*     */   private String mLastSearchPattern;
/*  77 */   private int mTextSearchMode = 112;
/*     */   
/*     */   private boolean mListenerWaitingForResult = false;
/*     */   
/*     */   private PDFViewCtrl mPdfViewCtrl;
/*     */   
/*     */   private FindTextTask mTask;
/*     */   
/*     */   private PopulateSectionList mPopulateSectionListTask;
/*     */   
/*     */   private Animation mAnimationFadeOut;
/*     */   
/*     */   private Animation mAnimationFadeIn;
/*     */   private boolean mClickEnabled = true;
/*     */   private boolean mIsSectionListPopulated;
/*     */   private SearchResultsListener mSearchResultsListener;
/*     */   protected boolean mFadeOnClickEnabled = true;
/*     */   
/*     */   public SearchResultsView(Context context) {
/*  96 */     this(context, (AttributeSet)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SearchResultsView(Context context, AttributeSet attrs) {
/* 103 */     this(context, attrs, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SearchResultsView(Context context, AttributeSet attrs, @AttrRes int defStyleAttr) {
/* 110 */     super(context, attrs, defStyleAttr);
/*     */     
/* 112 */     LayoutInflater.from(context).inflate(R.layout.controls_search_results, (ViewGroup)this, true);
/*     */     
/* 114 */     setBackgroundColor(Utils.getBackgroundColor(getContext()));
/*     */     
/* 116 */     this.mProgressLayout = (RelativeLayout)findViewById(R.id.progress_layout);
/* 117 */     this.mProgressBar = (ProgressBar)findViewById(R.id.dialog_search_results_progress_bar);
/* 118 */     this.mProgressText = (TextView)findViewById(R.id.progress_text);
/* 119 */     this.mEmptyView = (TextView)findViewById(16908292);
/* 120 */     RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
/*     */     
/* 122 */     this.mAdapter = getAdapter();
/* 123 */     recyclerView.setAdapter(this.mAdapter);
/* 124 */     LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
/* 125 */     recyclerView.setLayoutManager((RecyclerView.LayoutManager)layoutManager);
/*     */ 
/*     */     
/* 128 */     DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
/* 129 */     recyclerView.addItemDecoration((RecyclerView.ItemDecoration)dividerItemDecoration);
/*     */     
/* 131 */     ItemClickHelper clickHelper = new ItemClickHelper();
/* 132 */     clickHelper.attachToRecyclerView(recyclerView);
/* 133 */     clickHelper.setOnItemClickListener(new ItemClickHelper.OnItemClickListener()
/*     */         {
/*     */           public void onItemClick(RecyclerView recyclerView, View view, int position, long id) {
/* 136 */             if (!SearchResultsView.this.mClickEnabled) {
/*     */               return;
/*     */             }
/*     */             
/* 140 */             SearchResultsView.this.mCurrentResult = position;
/*     */             
/* 142 */             if (SearchResultsView.this.mSearchResultsListener != null) {
/*     */               
/* 144 */               TextSearchResult result = SearchResultsView.this.mSearchResultList.get(position);
/* 145 */               SearchResultsView.this.mSearchResultsListener.onSearchResultClicked(result);
/*     */             } 
/*     */             
/* 148 */             if (Utils.isTablet(SearchResultsView.this.getContext()) && SearchResultsView.this.mFadeOnClickEnabled) {
/* 149 */               SearchResultsView.this.startAnimation(SearchResultsView.this.mAnimationFadeOut);
/*     */             }
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 155 */     this.mAnimationFadeOut = AnimationUtils.loadAnimation(getContext(), R.anim.controls_search_results_popup_fadeout);
/* 156 */     this.mAnimationFadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.controls_search_results_popup_fadein);
/*     */ 
/*     */     
/* 159 */     this.mAnimationFadeOut.setAnimationListener(new Animation.AnimationListener()
/*     */         {
/*     */           public void onAnimationStart(Animation animation)
/*     */           {
/* 163 */             SearchResultsView.this.mClickEnabled = false;
/*     */           }
/*     */ 
/*     */           
/*     */           public void onAnimationEnd(Animation animation) {
/* 168 */             SearchResultsView.this.startAnimation(SearchResultsView.this.mAnimationFadeIn);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public void onAnimationRepeat(Animation animation) {}
/*     */         });
/* 175 */     this.mAnimationFadeIn.setAnimationListener(new Animation.AnimationListener()
/*     */         {
/*     */           public void onAnimationStart(Animation animation) {}
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onAnimationEnd(Animation animation) {
/* 183 */             SearchResultsView.this.mClickEnabled = true;
/*     */           }
/*     */ 
/*     */           
/*     */           public void onAnimationRepeat(Animation animation) {}
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   protected SearchResultsAdapter getAdapter() {
/* 193 */     return new SearchResultsAdapter(getContext(), R.layout.controls_search_results_popup_list_item, this.mSearchResultList, this.mSectionTitleList);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSearchResultsListener(SearchResultsListener listener) {
/* 203 */     this.mSearchResultsListener = listener;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPdfViewCtrl(PDFViewCtrl pdfViewCtrl) {
/* 212 */     if (pdfViewCtrl == null) {
/*     */       return;
/*     */     }
/* 215 */     this.mPdfViewCtrl = pdfViewCtrl;
/* 216 */     reset();
/* 217 */     startPopulateSectionListTask();
/* 218 */     resetProgressText();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PDFDoc getDoc() {
/* 227 */     return (this.mPdfViewCtrl == null) ? null : this.mPdfViewCtrl.getDoc();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public String getSearchPattern() {
/* 235 */     return (this.mLastSearchPattern != null) ? this.mLastSearchPattern : "";
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
/* 240 */     super.onVisibilityChanged(changedView, visibility);
/* 241 */     if (visibility == 8) {
/* 242 */       cancelSearch();
/* 243 */       cancelPopulateSectionListTask();
/*     */     } else {
/* 245 */       startPopulateSectionListTask();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/* 253 */     cancelSearch();
/* 254 */     clearSearchResults();
/* 255 */     this.mLastSearchPattern = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/* 262 */     return (this.mTask != null && !this.mTask.isCancelled() && (this.mTask.isRunning() || this.mTask.isFinished()));
/*     */   }
/*     */   
/*     */   private void clearSearchResults() {
/* 266 */     this.mSearchResultList.clear();
/* 267 */     this.mAdapter.notifyDataSetChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(TextSearchResult item) {
/* 276 */     this.mSearchResultList.add(item);
/* 277 */     this.mAdapter.setRtlMode((this.mPdfViewCtrl != null && this.mPdfViewCtrl.getRightToLeftLanguage()));
/* 278 */     this.mAdapter.notifyDataSetChanged();
/*     */   }
/*     */   
/*     */   private void resetProgressText() {
/* 282 */     this.mProgressText.setText(getContext().getResources().getString(R.string.tools_misc_please_wait));
/*     */   }
/*     */   
/*     */   private void updateProgressText(int pagesSearched) {
/*     */     try {
/* 287 */       int pageCount = this.mPdfViewCtrl.getDoc().getPageCount();
/* 288 */       this.mProgressText.setText(getContext().getResources().getString(R.string.search_progress_text, new Object[] { Integer.valueOf(pagesSearched), Integer.valueOf(pageCount) }));
/* 289 */     } catch (Exception e) {
/* 290 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
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
/*     */   public SearchResultStatus getResult(boolean searchUp) {
/* 302 */     int pageNum = this.mPdfViewCtrl.getCurrentPage();
/* 303 */     SearchResultStatus status = SearchResultStatus.NOT_HANDLED;
/* 304 */     TextSearchResult result = null;
/* 305 */     if (this.mSearchResultList.size() > 0 && this.mTask != null) {
/* 306 */       if (this.mCurrentResult != -1 && ((TextSearchResult)this.mSearchResultList.get(this.mCurrentResult)).getPageNum() == pageNum) {
/*     */         
/* 308 */         if (searchUp) {
/* 309 */           if (this.mCurrentResult - 1 >= 0) {
/*     */             
/* 311 */             this.mCurrentResult--;
/* 312 */             result = this.mSearchResultList.get(this.mCurrentResult);
/*     */           }
/* 314 */           else if (this.mTask.isFinished() && !this.mTask.isCancelled()) {
/*     */             
/* 316 */             this.mCurrentResult = this.mSearchResultList.size() - 1;
/* 317 */             result = this.mSearchResultList.get(this.mCurrentResult);
/* 318 */           } else if (this.mTask.isRunning()) {
/*     */ 
/*     */             
/* 321 */             status = SearchResultStatus.USE_FINDTEXT_FROM_END;
/*     */           }
/*     */         
/*     */         }
/* 325 */         else if (this.mCurrentResult + 1 < this.mSearchResultList.size()) {
/*     */           
/* 327 */           this.mCurrentResult++;
/* 328 */           result = this.mSearchResultList.get(this.mCurrentResult);
/*     */         }
/* 330 */         else if (this.mTask.isFinished() && !this.mTask.isCancelled()) {
/*     */ 
/*     */           
/* 333 */           this.mCurrentResult = 0;
/* 334 */           result = this.mSearchResultList.get(this.mCurrentResult);
/* 335 */         } else if (this.mTask.isRunning()) {
/*     */           
/* 337 */           this.mListenerWaitingForResult = true;
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 342 */         if (this.mTask.isRunning()) {
/* 343 */           if (this.mCurrentResult != -1) {
/* 344 */             if (((TextSearchResult)this.mSearchResultList.get(this.mCurrentResult)).getPageNum() < pageNum)
/*     */             {
/*     */               
/* 347 */               status = SearchResultStatus.USE_FINDTEXT;
/*     */             }
/*     */           } else {
/* 350 */             status = SearchResultStatus.USE_FINDTEXT;
/*     */           } 
/*     */         }
/* 353 */         if (status != SearchResultStatus.USE_FINDTEXT)
/*     */         {
/* 355 */           if (searchUp) {
/* 356 */             if (this.mTask.isRunning()) {
/*     */ 
/*     */               
/* 359 */               for (int i = this.mCurrentResult; i >= 0; i--) {
/* 360 */                 if (((TextSearchResult)this.mSearchResultList.get(i)).getPageNum() <= pageNum) {
/*     */                   
/* 362 */                   this.mCurrentResult = i;
/* 363 */                   result = this.mSearchResultList.get(this.mCurrentResult);
/*     */                   break;
/*     */                 } 
/*     */               } 
/*     */             } else {
/* 368 */               for (int i = this.mSearchResultList.size() - 1; i >= 0; i--) {
/* 369 */                 if (((TextSearchResult)this.mSearchResultList.get(i)).getPageNum() <= pageNum) {
/*     */                   
/* 371 */                   this.mCurrentResult = i;
/* 372 */                   result = this.mSearchResultList.get(this.mCurrentResult);
/*     */                   break;
/*     */                 } 
/*     */               } 
/*     */             } 
/* 377 */             if (result == null)
/*     */             {
/* 379 */               if (this.mTask.isFinished() && !this.mTask.isCancelled()) {
/*     */                 
/* 381 */                 this.mCurrentResult = this.mSearchResultList.size() - 1;
/* 382 */                 result = this.mSearchResultList.get(this.mCurrentResult);
/*     */               } else {
/* 384 */                 status = SearchResultStatus.USE_FINDTEXT;
/*     */               } 
/*     */             }
/*     */           } else {
/* 388 */             if (this.mTask.isRunning()) {
/*     */ 
/*     */               
/* 391 */               for (int i = 0; i <= this.mCurrentResult; i++) {
/* 392 */                 if (((TextSearchResult)this.mSearchResultList.get(i)).getPageNum() >= pageNum) {
/*     */                   
/* 394 */                   this.mCurrentResult = i;
/* 395 */                   result = this.mSearchResultList.get(this.mCurrentResult);
/*     */                   break;
/*     */                 } 
/*     */               } 
/*     */             } else {
/* 400 */               for (int i = 0; i < this.mSearchResultList.size(); i++) {
/* 401 */                 if (((TextSearchResult)this.mSearchResultList.get(i)).getPageNum() >= pageNum) {
/*     */                   
/* 403 */                   this.mCurrentResult = i;
/* 404 */                   result = this.mSearchResultList.get(this.mCurrentResult);
/*     */                   break;
/*     */                 } 
/*     */               } 
/*     */             } 
/* 409 */             if (result == null)
/*     */             {
/* 411 */               if (this.mTask.isFinished() && !this.mTask.isCancelled()) {
/*     */                 
/* 413 */                 this.mCurrentResult = 0;
/* 414 */                 result = this.mSearchResultList.get(this.mCurrentResult);
/*     */               } else {
/* 416 */                 status = SearchResultStatus.USE_FINDTEXT;
/*     */               } 
/*     */             }
/*     */           } 
/*     */         }
/*     */       } 
/*     */     }
/* 423 */     if (result != null) {
/* 424 */       if (this.mSearchResultsListener != null) {
/* 425 */         this.mSearchResultsListener.onSearchResultFound(result);
/*     */       }
/* 427 */       status = SearchResultStatus.HANDLED;
/* 428 */     } else if (this.mListenerWaitingForResult) {
/* 429 */       if (this.mSearchResultsListener != null) {
/* 430 */         this.mSearchResultsListener.onFullTextSearchStart();
/*     */       }
/* 432 */       status = SearchResultStatus.HANDLED;
/*     */     } 
/* 434 */     return status;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cancelGetResult() {
/* 441 */     if (this.mListenerWaitingForResult) {
/* 442 */       if (this.mSearchResultsListener != null) {
/* 443 */         this.mSearchResultsListener.onSearchResultFound(null);
/*     */       }
/* 445 */       this.mListenerWaitingForResult = false;
/* 446 */       CommonToast.showText(getContext(), getContext().getResources().getString(R.string.search_results_canceled), 0, 17, 0, 0);
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
/*     */   
/*     */   public void findText(@NonNull String pattern) {
/* 459 */     pattern = Utils.getBidiString(pattern);
/*     */     
/* 461 */     if (this.mLastSearchPattern == null || !pattern.equals(this.mLastSearchPattern)) {
/*     */       
/* 463 */       this.mLastSearchPattern = pattern;
/* 464 */     } else if (this.mLastSearchPattern != null && pattern.equals(this.mLastSearchPattern)) {
/*     */ 
/*     */ 
/*     */       
/* 468 */       if (this.mTask != null && pattern.equals(this.mTask.getPattern())) {
/* 469 */         if (this.mTask.isRunning()) {
/*     */           return;
/*     */         }
/* 472 */         if (this.mTask.isFinished()) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 479 */     if (isSectionListPopulated()) {
/* 480 */       restartSearch();
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
/*     */   public void cancelSearch() {
/* 492 */     if (this.mTask != null) {
/* 493 */       this.mTask.cancel(true);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void restartSearch() {
/* 504 */     cancelSearch();
/* 505 */     clearSearchResults();
/*     */     
/* 507 */     this.mTask = new FindTextTask(this.mPdfViewCtrl, this.mLastSearchPattern, this.mTextSearchMode, this.mSectionList, this.mSectionTitleList);
/* 508 */     this.mTask.setCallback(this);
/* 509 */     this.mTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Object[])new Void[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMatchCase(boolean matchCase) {
/* 519 */     if (matchCase) {
/*     */       
/* 521 */       this.mTextSearchMode |= 0x2;
/*     */     } else {
/*     */       
/* 524 */       this.mTextSearchMode &= 0xFFFFFFFD;
/*     */     } 
/* 526 */     restartSearch();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWholeWord(boolean wholeWord) {
/* 535 */     if (wholeWord) {
/*     */       
/* 537 */       this.mTextSearchMode |= 0x4;
/*     */     } else {
/*     */       
/* 540 */       this.mTextSearchMode &= 0xFFFFFFFB;
/*     */     } 
/* 542 */     if (this.mAdapter != null) {
/* 543 */       this.mAdapter.setWholeWord(wholeWord);
/*     */     }
/* 545 */     restartSearch();
/*     */   }
/*     */ 
/*     */   
/*     */   private void startPopulateSectionListTask() {
/* 550 */     if (this.mPdfViewCtrl == null || isSectionListPopulated()) {
/*     */       return;
/*     */     }
/*     */     
/* 554 */     if (this.mPopulateSectionListTask != null && this.mPopulateSectionListTask.isRunning()) {
/*     */       return;
/*     */     }
/*     */     
/* 558 */     Bookmark firstBookmark = Utils.getFirstBookmark(this.mPdfViewCtrl.getDoc());
/* 559 */     if (firstBookmark != null) {
/* 560 */       this.mPopulateSectionListTask = new PopulateSectionList(firstBookmark);
/* 561 */       this.mPopulateSectionListTask.setCallback(new PopulateSectionList.Callback()
/*     */           {
/*     */             public void onPopulateSectionListFinished(ArrayList<Section> sectionList)
/*     */             {
/* 565 */               synchronized (SearchResultsView.this.mSectionList) {
/* 566 */                 SearchResultsView.this.mSectionList.clear();
/* 567 */                 SearchResultsView.this.mSectionList.addAll(sectionList);
/*     */               } 
/* 569 */               SearchResultsView.this.restartSearch();
/*     */             }
/*     */           });
/*     */       
/* 573 */       this.mPopulateSectionListTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Object[])new Void[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void cancelPopulateSectionListTask() {
/* 580 */     if (this.mPopulateSectionListTask != null && this.mPopulateSectionListTask.isRunning()) {
/* 581 */       this.mPopulateSectionListTask.cancel(true);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isSectionListPopulated() {
/* 588 */     if (this.mIsSectionListPopulated) {
/* 589 */       return true;
/*     */     }
/*     */     
/* 592 */     this
/*     */       
/* 594 */       .mIsSectionListPopulated = (!this.mSectionList.isEmpty() || (this.mPopulateSectionListTask != null && this.mPopulateSectionListTask.isFinished()) || Utils.getFirstBookmark(this.mPdfViewCtrl.getDoc()) == null);
/*     */     
/* 596 */     return this.mIsSectionListPopulated;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onFindTextTaskStarted() {
/* 606 */     this.mProgressLayout.setVisibility(0);
/* 607 */     this.mEmptyView.setVisibility(8);
/* 608 */     this.mProgressBar.setVisibility(0);
/* 609 */     this.mAdapter.setRtlMode((this.mPdfViewCtrl != null && this.mPdfViewCtrl.getRightToLeftLanguage()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onFindTextTaskProgressUpdated(boolean foundResultOnPage, int pagesSearched, ArrayList<TextSearchResult> results) {
/* 621 */     this.mSearchResultList.clear();
/* 622 */     this.mSearchResultList.addAll(results);
/* 623 */     this.mAdapter.notifyDataSetChanged();
/*     */ 
/*     */     
/* 626 */     updateProgressText(pagesSearched);
/*     */     
/* 628 */     if (foundResultOnPage && this.mSearchResultList.size() > 0 && this.mListenerWaitingForResult) {
/* 629 */       if (this.mSearchResultsListener != null) {
/* 630 */         TextSearchResult result = null;
/* 631 */         if (this.mCurrentResult != -1 && this.mCurrentResult + 1 < this.mSearchResultList.size())
/*     */         {
/* 633 */           result = this.mSearchResultList.get(++this.mCurrentResult);
/*     */         }
/* 635 */         this.mSearchResultsListener.onSearchResultFound(result);
/*     */       } 
/* 637 */       this.mListenerWaitingForResult = false;
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
/*     */   
/*     */   public void onFindTextTaskFinished(int numResults, ArrayList<TextSearchResult> results, HashMap<TextSearchResult, ArrayList<Double>> highlights) {
/* 650 */     this.mSearchResultList.clear();
/* 651 */     this.mSearchResultList.addAll(results);
/* 652 */     this.mAdapter.notifyDataSetChanged();
/*     */     
/* 654 */     this.mSearchResultHighlightList = highlights;
/*     */ 
/*     */     
/* 657 */     this.mProgressBar.setVisibility(8);
/*     */ 
/*     */     
/* 660 */     if (numResults > 0) {
/* 661 */       this.mEmptyView.setVisibility(8);
/* 662 */       this.mProgressText.setText(getContext().getResources().getString(R.string.search_results_text, new Object[] { Integer.valueOf(numResults) }));
/*     */     } else {
/* 664 */       this.mEmptyView.setVisibility(0);
/* 665 */       this.mProgressLayout.setVisibility(8);
/*     */     } 
/*     */     
/* 668 */     if (this.mListenerWaitingForResult) {
/*     */ 
/*     */       
/* 671 */       if (this.mSearchResultsListener != null) {
/* 672 */         if (this.mSearchResultList.size() > 0) {
/*     */           
/* 674 */           TextSearchResult result = this.mSearchResultList.get(0);
/* 675 */           this.mSearchResultsListener.onSearchResultFound(result);
/*     */         } else {
/*     */           
/* 678 */           this.mSearchResultsListener.onSearchResultFound(null);
/* 679 */           CommonToast.showText(getContext(), getContext().getResources().getString(R.string.search_results_none), 0, 17, 0, 0);
/*     */         } 
/*     */       }
/* 682 */       this.mListenerWaitingForResult = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onFindTextTaskCancelled() {
/* 691 */     cancelGetResult();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Section
/*     */   {
/*     */     public double left;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double bottom;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double right;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double top;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Bookmark mBookmark;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int mPageNum;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Section(Bookmark bookmark, int pageNum) {
/* 743 */       this.mBookmark = bookmark;
/* 744 */       this.mPageNum = pageNum;
/* 745 */       this.left = -1.0D;
/* 746 */       this.bottom = -1.0D;
/* 747 */       this.right = -1.0D;
/* 748 */       this.top = -1.0D;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setPosition(Destination dest) {
/*     */       try {
/* 758 */         Obj obj = dest.getSDFObj();
/* 759 */         if (obj.isArray() || obj.isDict()) {
/* 760 */           switch (dest.getFitType()) {
/*     */             
/*     */             case 0:
/* 763 */               if (obj.isArray() && obj.size() == 5L) {
/* 764 */                 if (obj.getAt(2).isNumber()) {
/* 765 */                   this.left = obj.getAt(2).getNumber();
/*     */                 }
/* 767 */                 if (obj.getAt(3).isNumber())
/* 768 */                   this.top = obj.getAt(3).getNumber();  break;
/*     */               } 
/* 770 */               if (obj.isDict()) {
/* 771 */                 DictIterator dictIterator = obj.getDictIterator();
/* 772 */                 while (dictIterator.hasNext()) {
/* 773 */                   Obj value = dictIterator.value();
/* 774 */                   if (value.isArray() && value.size() == 5L) {
/* 775 */                     if (value.getAt(2).isNumber()) {
/* 776 */                       this.left = value.getAt(2).getNumber();
/*     */                     }
/* 778 */                     if (value.getAt(3).isNumber()) {
/* 779 */                       this.top = value.getAt(3).getNumber();
/*     */                     }
/*     */                   } 
/* 782 */                   dictIterator.next();
/*     */                 } 
/*     */               } 
/*     */               break;
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             case 2:
/* 791 */               if (obj.isArray() && obj.size() == 3L) {
/* 792 */                 if (obj.getAt(2).isNumber())
/* 793 */                   this.top = obj.getAt(2).getNumber();  break;
/*     */               } 
/* 795 */               if (obj.isDict()) {
/* 796 */                 DictIterator dictIterator = obj.getDictIterator();
/* 797 */                 while (dictIterator.hasNext()) {
/* 798 */                   Obj value = dictIterator.value();
/* 799 */                   if (value.isArray() && value.size() == 3L && 
/* 800 */                     value.getAt(2).isNumber()) {
/* 801 */                     this.top = value.getAt(2).getNumber();
/*     */                   }
/*     */                   
/* 804 */                   dictIterator.next();
/*     */                 } 
/*     */               } 
/*     */               break;
/*     */             
/*     */             case 3:
/* 810 */               if (obj.isArray() && obj.size() == 3L) {
/* 811 */                 if (obj.getAt(2).isNumber())
/* 812 */                   this.left = obj.getAt(2).getNumber();  break;
/*     */               } 
/* 814 */               if (obj.isDict()) {
/* 815 */                 DictIterator dictIterator = obj.getDictIterator();
/* 816 */                 while (dictIterator.hasNext()) {
/* 817 */                   Obj value = dictIterator.value();
/* 818 */                   if (value.isArray() && value.size() == 3L && 
/* 819 */                     value.getAt(2).isNumber()) {
/* 820 */                     this.left = value.getAt(2).getNumber();
/*     */                   }
/*     */                   
/* 823 */                   dictIterator.next();
/*     */                 } 
/*     */               } 
/*     */               break;
/*     */             
/*     */             case 4:
/* 829 */               if (obj.isArray() && obj.size() == 6L) {
/* 830 */                 if (obj.getAt(2).isNumber()) {
/* 831 */                   this.left = obj.getAt(2).getNumber();
/*     */                 }
/* 833 */                 if (obj.getAt(3).isNumber()) {
/* 834 */                   this.bottom = obj.getAt(3).getNumber();
/*     */                 }
/* 836 */                 if (obj.getAt(4).isNumber()) {
/* 837 */                   this.right = obj.getAt(4).getNumber();
/*     */                 }
/* 839 */                 if (obj.getAt(5).isNumber())
/* 840 */                   this.top = obj.getAt(5).getNumber();  break;
/*     */               } 
/* 842 */               if (obj.isDict()) {
/* 843 */                 DictIterator dictIterator = obj.getDictIterator();
/* 844 */                 while (dictIterator.hasNext()) {
/* 845 */                   Obj value = dictIterator.value();
/* 846 */                   if (value.isArray() && value.size() == 6L) {
/* 847 */                     if (value.getAt(2).isNumber()) {
/* 848 */                       this.left = value.getAt(2).getNumber();
/*     */                     }
/* 850 */                     if (value.getAt(3).isNumber()) {
/* 851 */                       this.bottom = value.getAt(3).getNumber();
/*     */                     }
/* 853 */                     if (value.getAt(4).isNumber()) {
/* 854 */                       this.right = value.getAt(4).getNumber();
/*     */                     }
/* 856 */                     if (value.getAt(5).isNumber()) {
/* 857 */                       this.top = value.getAt(5).getNumber();
/*     */                     }
/*     */                   } 
/* 860 */                   dictIterator.next();
/*     */                 } 
/*     */               } 
/*     */               break;
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             case 6:
/* 869 */               if (obj.isArray() && obj.size() == 3L) {
/* 870 */                 if (obj.getAt(2).isNumber())
/* 871 */                   this.top = obj.getAt(2).getNumber();  break;
/*     */               } 
/* 873 */               if (obj.isDict()) {
/* 874 */                 DictIterator dictIterator = obj.getDictIterator();
/* 875 */                 while (dictIterator.hasNext()) {
/* 876 */                   Obj value = dictIterator.value();
/* 877 */                   if (value.isArray() && value.size() == 3L && 
/* 878 */                     value.getAt(2).isNumber()) {
/* 879 */                     this.top = value.getAt(2).getNumber();
/*     */                   }
/*     */                   
/* 882 */                   dictIterator.next();
/*     */                 } 
/*     */               } 
/*     */               break;
/*     */             
/*     */             case 7:
/* 888 */               if (obj.isArray() && obj.size() == 3L) {
/* 889 */                 if (obj.getAt(2).isNumber())
/* 890 */                   this.left = obj.getAt(2).getNumber();  break;
/*     */               } 
/* 892 */               if (obj.isDict()) {
/* 893 */                 DictIterator dictIterator = obj.getDictIterator();
/* 894 */                 while (dictIterator.hasNext()) {
/* 895 */                   Obj value = dictIterator.value();
/* 896 */                   if (value.isArray() && value.size() == 3L && 
/* 897 */                     value.getAt(2).isNumber()) {
/* 898 */                     this.left = value.getAt(2).getNumber();
/*     */                   }
/*     */                   
/* 901 */                   dictIterator.next();
/*     */                 } 
/*     */               } 
/*     */               break;
/*     */           } 
/*     */         }
/* 907 */       } catch (PDFNetException e) {
/* 908 */         this.left = -1.0D;
/* 909 */         this.bottom = -1.0D;
/* 910 */         this.right = -1.0D;
/* 911 */         this.top = -1.0D;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static class PopulateSectionList
/*     */     extends AsyncTask<Void, Void, Void> {
/*     */     Bookmark mBookmark;
/*     */     Callback mCallback;
/* 920 */     private ArrayList<Section> mPopulatedSectionList = new ArrayList<>();
/*     */ 
/*     */ 
/*     */     
/*     */     PopulateSectionList(Bookmark bookmark) {
/* 925 */       this.mBookmark = bookmark;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setCallback(@Nullable Callback callback) {
/* 935 */       this.mCallback = callback;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected Void doInBackground(Void... params) {
/*     */       try {
/* 944 */         populateSectionList(this.mBookmark);
/* 945 */       } catch (PDFNetException e) {
/* 946 */         this.mPopulatedSectionList.clear();
/*     */       } 
/* 948 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void onPostExecute(Void aVoid) {
/* 956 */       super.onPostExecute(aVoid);
/*     */       
/* 958 */       if (this.mCallback != null) {
/* 959 */         this.mCallback.onPopulateSectionListFinished(this.mPopulatedSectionList);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void populateSectionList(Bookmark root) throws PDFNetException {
/* 968 */       for (Bookmark item = root; item.isValid() && !isCancelled(); item = item.getNext()) {
/* 969 */         Action action = item.getAction();
/* 970 */         if (action.isValid() && action.getType() == 0) {
/* 971 */           Destination dest = action.getDest();
/* 972 */           if (dest.isValid()) {
/* 973 */             Section section = new Section(item, dest.getPage().getIndex());
/* 974 */             section.setPosition(dest);
/* 975 */             this.mPopulatedSectionList.add(section);
/*     */           } 
/*     */         } 
/*     */         
/* 979 */         if (item.hasChildren()) {
/* 980 */           populateSectionList(item.getFirstChild());
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     boolean isRunning() {
/* 987 */       return (getStatus() == Status.RUNNING);
/*     */     }
/*     */     
/*     */     boolean isFinished() {
/* 991 */       return (getStatus() == Status.FINISHED);
/*     */     }
/*     */     
/*     */     public static interface Callback {
/*     */       void onPopulateSectionListFinished(ArrayList<Section> param2ArrayList);
/*     */     }
/*     */   }
/*     */   
/*     */   public static interface SearchResultsListener {
/*     */     void onSearchResultClicked(TextSearchResult param1TextSearchResult);
/*     */     
/*     */     void onFullTextSearchStart();
/*     */     
/*     */     void onSearchResultFound(TextSearchResult param1TextSearchResult);
/*     */   }
/*     */   
/*     */   public static interface Callback {
/*     */     void onPopulateSectionListFinished(ArrayList<Section> param1ArrayList);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\controls\SearchResultsView.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */