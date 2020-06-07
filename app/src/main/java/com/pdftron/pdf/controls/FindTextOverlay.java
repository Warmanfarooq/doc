/*     */ package com.pdftron.pdf.controls;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.util.AttributeSet;
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.constraintlayout.widget.ConstraintLayout;
/*     */ import com.google.android.material.floatingactionbutton.FloatingActionButton;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.TextSearchResult;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.tools.TextHighlighter;
/*     */ import com.pdftron.pdf.tools.ToolManager;
/*     */ import com.pdftron.pdf.utils.CommonToast;
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
/*     */ public class FindTextOverlay
/*     */   extends ConstraintLayout
/*     */   implements PDFViewCtrl.TextSearchListener
/*     */ {
/*     */   private FindTextOverlayListener mFindTextOverlayListener;
/*     */   protected FloatingActionButton mButtonSearchNext;
/*     */   protected FloatingActionButton mButtonSearchPrev;
/*     */   protected PDFViewCtrl mPdfViewCtrl;
/*     */   protected boolean mSearchMatchCase;
/*     */   protected boolean mSearchWholeWord;
/*     */   protected boolean mSearchUp;
/*     */   
/*     */   public void setFindTextOverlayListener(FindTextOverlayListener listener) {
/*  52 */     this.mFindTextOverlayListener = listener;
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
/*  63 */   protected String mSearchQuery = "";
/*     */   
/*     */   protected boolean mSearchSettingsChanged;
/*     */   protected boolean mUseFullTextResults;
/*     */   protected int mNumSearchRunning;
/*     */   protected boolean mShowSearchCancelMessage = true;
/*     */   
/*     */   public FindTextOverlay(Context context) {
/*  71 */     this(context, null);
/*     */   }
/*     */   
/*     */   public FindTextOverlay(Context context, AttributeSet attrs) {
/*  75 */     this(context, attrs, 0);
/*     */   }
/*     */   
/*     */   public FindTextOverlay(Context context, AttributeSet attrs, int defStyleAttr) {
/*  79 */     super(context, attrs, defStyleAttr);
/*  80 */     init();
/*     */   }
/*     */   
/*     */   private void init() {
/*  84 */     View view = LayoutInflater.from(getContext()).inflate(R.layout.controls_find_text_overlay, (ViewGroup)this);
/*     */ 
/*     */     
/*  87 */     this.mButtonSearchNext = (FloatingActionButton)view.findViewById(R.id.search_button_next);
/*  88 */     this.mButtonSearchNext.setOnClickListener(new OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/*  91 */             FindTextOverlay.this.gotoNextSearch();
/*     */           }
/*     */         });
/*  94 */     this.mButtonSearchPrev = (FloatingActionButton)view.findViewById(R.id.search_button_prev);
/*  95 */     this.mButtonSearchPrev.setOnClickListener(new OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/*  98 */             FindTextOverlay.this.gotoPreviousSearch();
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPdfViewCtrl(@NonNull PDFViewCtrl pdfViewCtrl) {
/* 109 */     this.mPdfViewCtrl = pdfViewCtrl;
/*     */     
/* 111 */     this.mPdfViewCtrl.setTextSearchListener(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void gotoNextSearch() {
/* 118 */     if (this.mPdfViewCtrl == null) {
/*     */       return;
/*     */     }
/*     */     
/* 122 */     this.mSearchUp = false;
/* 123 */     if (this.mFindTextOverlayListener != null) {
/* 124 */       this.mFindTextOverlayListener.onGotoNextSearch(this.mUseFullTextResults);
/*     */     } else {
/* 126 */       findText();
/*     */     } 
/* 128 */     highlightSearchResults();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void gotoPreviousSearch() {
/* 135 */     if (this.mPdfViewCtrl == null) {
/*     */       return;
/*     */     }
/*     */     
/* 139 */     this.mSearchUp = true;
/* 140 */     if (this.mFindTextOverlayListener != null) {
/* 141 */       this.mFindTextOverlayListener.onGotoPreviousSearch(this.mUseFullTextResults);
/*     */     } else {
/* 143 */       findText();
/*     */     } 
/*     */     
/* 146 */     highlightSearchResults();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSearchQuery(String text) {
/* 156 */     if (this.mSearchQuery != null && !this.mSearchQuery.equals(text)) {
/* 157 */       this.mUseFullTextResults = false;
/*     */     }
/* 159 */     this.mSearchQuery = text;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSearchMatchCase(boolean matchCase) {
/* 168 */     setSearchSettings(matchCase, this.mSearchWholeWord);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSearchWholeWord(boolean wholeWord) {
/* 177 */     setSearchSettings(this.mSearchMatchCase, wholeWord);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSearchSettings(boolean matchCase, boolean wholeWord) {
/* 187 */     this.mSearchMatchCase = matchCase;
/* 188 */     this.mSearchWholeWord = wholeWord;
/* 189 */     this.mSearchSettingsChanged = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetFullTextResults() {
/* 196 */     this.mUseFullTextResults = false;
/* 197 */     highlightSearchResults();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void queryTextSubmit(String text) {
/* 206 */     this.mSearchQuery = text;
/* 207 */     findText();
/* 208 */     highlightSearchResults();
/*     */   }
/*     */   
/*     */   public void findText() {
/* 212 */     findText(-1);
/*     */   }
/*     */   
/*     */   public void findText(int pageNum) {
/* 216 */     if (this.mPdfViewCtrl != null && this.mSearchQuery != null && this.mSearchQuery.trim().length() > 0) {
/* 217 */       this.mUseFullTextResults = false;
/* 218 */       this.mPdfViewCtrl.findText(this.mSearchQuery, this.mSearchMatchCase, this.mSearchWholeWord, this.mSearchUp, false, pageNum);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void highlightSearchResults() {
/* 226 */     if (this.mPdfViewCtrl == null) {
/*     */       return;
/*     */     }
/* 229 */     ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 230 */     String prevPattern = null;
/* 231 */     if (toolManager.getTool() instanceof TextHighlighter) {
/* 232 */       TextHighlighter prevTool = (TextHighlighter)toolManager.getTool();
/* 233 */       prevPattern = prevTool.getSearchPattern();
/*     */     } 
/*     */ 
/*     */     
/* 237 */     if (prevPattern == null || !this.mSearchQuery.equals(prevPattern) || this.mSearchSettingsChanged) {
/* 238 */       if (this.mSearchQuery.trim().length() > 0) {
/* 239 */         ToolManager.Tool tool = toolManager.createTool((ToolManager.ToolModeBase)ToolManager.ToolMode.TEXT_HIGHLIGHTER, null);
/* 240 */         if (tool instanceof TextHighlighter) {
/* 241 */           TextHighlighter highlighter = (TextHighlighter)tool;
/* 242 */           toolManager.setTool((ToolManager.Tool)highlighter);
/* 243 */           highlighter.start(this.mSearchQuery, this.mSearchMatchCase, this.mSearchWholeWord, false);
/*     */         } 
/*     */       } 
/*     */       
/* 247 */       this.mSearchSettingsChanged = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void exitSearchMode() {
/* 255 */     if (this.mPdfViewCtrl == null) {
/*     */       return;
/*     */     }
/* 258 */     ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/*     */     
/* 260 */     if (toolManager.getTool() instanceof TextHighlighter) {
/* 261 */       TextHighlighter highlighter = (TextHighlighter)toolManager.getTool();
/* 262 */       this.mPdfViewCtrl.clearSelection();
/* 263 */       highlighter.clear();
/* 264 */       this.mPdfViewCtrl.invalidate();
/*     */     } 
/* 266 */     toolManager.setTool(toolManager.createTool((ToolManager.ToolModeBase)ToolManager.ToolMode.PAN, null));
/*     */ 
/*     */     
/* 269 */     this.mUseFullTextResults = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void highlightFullTextSearchResult(TextSearchResult result) {
/* 278 */     if (this.mPdfViewCtrl == null) {
/*     */       return;
/*     */     }
/* 281 */     ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/*     */     
/* 283 */     if (result.getCode() == 2) {
/* 284 */       this.mPdfViewCtrl.requestFocus();
/* 285 */       this.mShowSearchCancelMessage = false;
/* 286 */       cancelFindText();
/* 287 */       this.mPdfViewCtrl.selectAndJumpWithHighlights(result.getHighlights());
/*     */       
/* 289 */       if (toolManager.getTool() instanceof TextHighlighter) {
/* 290 */         TextHighlighter highlighter = (TextHighlighter)toolManager.getTool();
/* 291 */         highlighter.update();
/* 292 */         highlighter.highlightSelection();
/*     */       } 
/*     */       
/* 295 */       this.mUseFullTextResults = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cancelFindText() {
/* 303 */     if (this.mPdfViewCtrl != null) {
/* 304 */       this.mPdfViewCtrl.cancelFindText();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onTextSearchStart() {
/* 313 */     this.mNumSearchRunning++;
/* 314 */     if (this.mFindTextOverlayListener != null) {
/* 315 */       this.mFindTextOverlayListener.onSearchProgressShow();
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
/*     */   public void onTextSearchProgress(int progress) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onTextSearchEnd(PDFViewCtrl.TextSearchResult result) {
/* 336 */     if (this.mPdfViewCtrl == null) {
/*     */       return;
/*     */     }
/* 339 */     this.mNumSearchRunning--;
/* 340 */     if (this.mPdfViewCtrl != null) {
/* 341 */       this.mPdfViewCtrl.requestFocus();
/*     */     }
/*     */     
/* 344 */     if (this.mFindTextOverlayListener != null) {
/* 345 */       this.mFindTextOverlayListener.onSearchProgressHide();
/* 346 */       if (this.mNumSearchRunning > 0) {
/* 347 */         this.mFindTextOverlayListener.onSearchProgressShow();
/*     */       }
/*     */     } 
/* 350 */     ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/*     */     
/* 352 */     switch (result) {
/*     */       case NOT_FOUND:
/* 354 */         CommonToast.showText(getContext(), getContext().getString(R.string.search_results_none), 0, 17, 0, 0);
/*     */         break;
/*     */       case FOUND:
/* 357 */         if (toolManager.getTool() instanceof TextHighlighter) {
/* 358 */           TextHighlighter highlighter = (TextHighlighter)toolManager.getTool();
/* 359 */           highlighter.update();
/* 360 */           highlighter.highlightSelection();
/*     */         } 
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case INVALID_INPUT:
/* 368 */         CommonToast.showText(getContext(), getContext().getString(R.string.search_results_invalid), 0, 17, 0, 0);
/*     */         break;
/*     */     } 
/* 371 */     this.mShowSearchCancelMessage = true;
/*     */   }
/*     */   
/*     */   public static interface FindTextOverlayListener {
/*     */     void onGotoNextSearch(boolean param1Boolean);
/*     */     
/*     */     void onGotoPreviousSearch(boolean param1Boolean);
/*     */     
/*     */     void onSearchProgressShow();
/*     */     
/*     */     void onSearchProgressHide();
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\controls\FindTextOverlay.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */