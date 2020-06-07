/*     */ package com.pdftron.pdf.widget.redaction;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.util.AttributeSet;
/*     */ import android.util.Pair;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import com.pdftron.pdf.Rect;
/*     */ import com.pdftron.pdf.TextSearchResult;
/*     */ import com.pdftron.pdf.controls.SearchResultsAdapter;
/*     */ import com.pdftron.pdf.controls.SearchResultsView;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RedactionSearchResultsView
/*     */   extends SearchResultsView
/*     */ {
/*     */   private RedactionSearchResultsListener mRedactionSearchResultsListener;
/*     */   
/*     */   public RedactionSearchResultsView(Context context) {
/*  31 */     this(context, (AttributeSet)null);
/*     */   }
/*     */   
/*     */   public RedactionSearchResultsView(Context context, AttributeSet attrs) {
/*  35 */     this(context, attrs, 0);
/*     */   }
/*     */   
/*     */   public RedactionSearchResultsView(Context context, AttributeSet attrs, int defStyleAttr) {
/*  39 */     super(context, attrs, defStyleAttr);
/*     */     
/*  41 */     this.mFadeOnClickEnabled = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRedactionSearchResultsListener(RedactionSearchResultsListener listener) {
/*  50 */     this.mRedactionSearchResultsListener = listener;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SearchResultsAdapter getAdapter() {
/*  55 */     return new RedactionSearchResultsAdapter(getContext(), R.layout.widget_redaction_search_results_list_item, this.mSearchResultList, this.mSectionTitleList);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void restartSearch() {
/*  61 */     super.restartSearch();
/*     */     
/*  63 */     if (this.mRedactionSearchResultsListener != null) {
/*  64 */       this.mRedactionSearchResultsListener.onRedactionSearchStart();
/*     */     }
/*     */   }
/*     */   
/*     */   public void toggleSelection() {
/*  69 */     if (this.mAdapter instanceof RedactionSearchResultsAdapter) {
/*  70 */       RedactionSearchResultsAdapter adapter = (RedactionSearchResultsAdapter)this.mAdapter;
/*  71 */       if (adapter.isSelected(this.mCurrentResult)) {
/*  72 */         adapter.removeSelected(this.mCurrentResult);
/*     */       } else {
/*  74 */         adapter.addSelected(this.mCurrentResult);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isAllSelected() {
/*  80 */     if (this.mAdapter instanceof RedactionSearchResultsAdapter) {
/*  81 */       return ((RedactionSearchResultsAdapter)this.mAdapter).isAllSelected();
/*     */     }
/*  83 */     return false;
/*     */   }
/*     */   
/*     */   public void selectAll() {
/*  87 */     if (this.mAdapter instanceof RedactionSearchResultsAdapter) {
/*  88 */       ((RedactionSearchResultsAdapter)this.mAdapter).selectAll();
/*     */     }
/*     */   }
/*     */   
/*     */   public void deselectAll() {
/*  93 */     if (this.mAdapter instanceof RedactionSearchResultsAdapter) {
/*  94 */       ((RedactionSearchResultsAdapter)this.mAdapter).deselectAll();
/*     */     }
/*     */   }
/*     */   
/*     */   @NonNull
/*     */   public ArrayList<Pair<Integer, ArrayList<Double>>> getSelections() {
/* 100 */     ArrayList<Pair<Integer, ArrayList<Double>>> highlights = new ArrayList<>();
/* 101 */     if (this.mAdapter instanceof RedactionSearchResultsAdapter) {
/* 102 */       HashSet<Integer> selected = ((RedactionSearchResultsAdapter)this.mAdapter).getSelected();
/* 103 */       for (Iterator<Integer> iterator = selected.iterator(); iterator.hasNext(); ) { int position = ((Integer)iterator.next()).intValue();
/* 104 */         TextSearchResult result = this.mSearchResultList.get(position);
/* 105 */         highlights.add(new Pair(Integer.valueOf(result.getPageNum()), this.mSearchResultHighlightList.get(result))); }
/*     */     
/*     */     } 
/* 108 */     return highlights;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Rect getRectForResult(@NonNull TextSearchResult result) {
/*     */     try {
/* 114 */       ArrayList<Double> quadList = (ArrayList<Double>)this.mSearchResultHighlightList.get(result);
/* 115 */       if (quadList != null) {
/* 116 */         Double[] quads = quadList.<Double>toArray(new Double[0]);
/* 117 */         return new Rect(quads[0].doubleValue(), quads[1].doubleValue(), quads[4].doubleValue(), quads[5].doubleValue());
/*     */       } 
/* 119 */     } catch (Exception exception) {}
/*     */     
/* 121 */     return null;
/*     */   }
/*     */   
/*     */   public static interface RedactionSearchResultsListener {
/*     */     void onRedactionSearchStart();
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\widget\redaction\RedactionSearchResultsView.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */