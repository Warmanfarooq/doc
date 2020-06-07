/*     */ package com.pdftron.pdf.dialog.redaction;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.os.Bundle;
/*     */ import android.view.KeyEvent;
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.MenuItem;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.widget.Button;
/*     */ import android.widget.CheckBox;
/*     */ import android.widget.TextView;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.appcompat.widget.Toolbar;
/*     */ import androidx.fragment.app.DialogFragment;
/*     */ import androidx.fragment.app.FragmentActivity;
/*     */ import androidx.lifecycle.ViewModelProviders;
/*     */ import com.google.android.material.textfield.TextInputEditText;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.Rect;
/*     */ import com.pdftron.pdf.TextSearchResult;
/*     */ import com.pdftron.pdf.controls.SearchResultsView;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import com.pdftron.pdf.utils.ViewerUtils;
/*     */ import com.pdftron.pdf.viewmodel.RedactionViewModel;
/*     */ import com.pdftron.pdf.widget.redaction.RedactionSearchResultsView;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SearchRedactionDialogFragment
/*     */   extends DialogFragment
/*     */   implements SearchResultsView.SearchResultsListener, RedactionSearchResultsView.RedactionSearchResultsListener, Toolbar.OnMenuItemClickListener
/*     */ {
/*  40 */   public static final String TAG = SearchRedactionDialogFragment.class.getName();
/*     */   
/*     */   public static SearchRedactionDialogFragment newInstance() {
/*  43 */     return new SearchRedactionDialogFragment();
/*     */   }
/*     */ 
/*     */   
/*     */   private PDFViewCtrl mPdfViewCtrl;
/*     */   
/*     */   private RedactionSearchResultsView mSearchResultsView;
/*     */   private CheckBox mSelectAllCheckBox;
/*     */   private RedactionViewModel mRedactionViewModel;
/*     */   
/*     */   @Nullable
/*     */   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
/*  55 */     View view = inflater.inflate(R.layout.fragment_redact_by_search, null);
/*     */     
/*  57 */     FragmentActivity activity = getActivity();
/*  58 */     if (this.mPdfViewCtrl == null || activity == null) {
/*  59 */       return view;
/*     */     }
/*  61 */     this.mRedactionViewModel = (RedactionViewModel)ViewModelProviders.of(activity).get(RedactionViewModel.class);
/*     */     
/*  63 */     Toolbar toolbar = (Toolbar)view.findViewById(R.id.toolbar);
/*  64 */     Toolbar searchToolbar = (Toolbar)view.findViewById(R.id.searchToolbar);
/*  65 */     TextInputEditText editText = (TextInputEditText)view.findViewById(R.id.textInput);
/*  66 */     View selectAllView = view.findViewById(R.id.selectAllView);
/*  67 */     this.mSelectAllCheckBox = (CheckBox)view.findViewById(R.id.selectAllCheckBox);
/*     */     
/*  69 */     this.mSearchResultsView = (RedactionSearchResultsView)view.findViewById(R.id.searchResultsView);
/*  70 */     Button redactBtn = (Button)view.findViewById(R.id.redactBtn);
/*     */ 
/*     */     
/*  73 */     toolbar.setNavigationIcon(null);
/*  74 */     toolbar.setBackgroundColor(Utils.getBackgroundColor((Context)activity));
/*  75 */     toolbar.setTitle(R.string.tools_qm_redact_by_search);
/*  76 */     toolbar.inflateMenu(R.menu.fragment_search_redaction_main);
/*  77 */     toolbar.setOnMenuItemClickListener(this);
/*     */ 
/*     */     
/*  80 */     searchToolbar.inflateMenu(R.menu.fragment_search_redaction_search);
/*  81 */     searchToolbar.setOnMenuItemClickListener(this);
/*  82 */     editText.requestFocus();
/*  83 */     editText.setOnEditorActionListener(new TextView.OnEditorActionListener()
/*     */         {
/*     */           public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
/*  86 */             if (actionId == 6) {
/*  87 */               SearchRedactionDialogFragment.this.mSearchResultsView.findText(v.getText().toString());
/*  88 */               Utils.hideSoftKeyboard(v.getContext(), (View)v);
/*  89 */               return true;
/*     */             } 
/*  91 */             return false;
/*     */           }
/*     */         });
/*     */ 
/*     */     
/*  96 */     selectAllView.setOnClickListener(new View.OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/*  99 */             if (SearchRedactionDialogFragment.this.mSelectAllCheckBox.isChecked()) {
/* 100 */               SearchRedactionDialogFragment.this.mSearchResultsView.deselectAll();
/*     */             } else {
/* 102 */               SearchRedactionDialogFragment.this.mSearchResultsView.selectAll();
/*     */             } 
/* 104 */             SearchRedactionDialogFragment.this.mSelectAllCheckBox.setChecked(!SearchRedactionDialogFragment.this.mSelectAllCheckBox.isChecked());
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 109 */     this.mSearchResultsView.setPdfViewCtrl(this.mPdfViewCtrl);
/* 110 */     this.mSearchResultsView.setSearchResultsListener(this);
/* 111 */     this.mSearchResultsView.setRedactionSearchResultsListener(this);
/*     */ 
/*     */     
/* 114 */     redactBtn.setOnClickListener(new View.OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/* 117 */             SearchRedactionDialogFragment.this.mRedactionViewModel.onRedactBySearch(SearchRedactionDialogFragment.this.mSearchResultsView.getSelections());
/* 118 */             FragmentActivity activity = SearchRedactionDialogFragment.this.getActivity();
/* 119 */             if (null != activity) {
/* 120 */               if (Utils.isLargeTablet((Context)activity)) {
/* 121 */                 SearchRedactionDialogFragment.this.mRedactionViewModel.onRedactBySearchCloseClicked();
/*     */               } else {
/* 123 */                 SearchRedactionDialogFragment.this.dismiss();
/*     */               } 
/*     */             }
/*     */           }
/*     */         });
/*     */     
/* 129 */     return view;
/*     */   }
/*     */   
/*     */   public void setPdfViewCtrl(@NonNull PDFViewCtrl pdfViewCtrl) {
/* 133 */     this.mPdfViewCtrl = pdfViewCtrl;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onMenuItemClick(MenuItem item) {
/* 138 */     int id = item.getItemId();
/* 139 */     if (id == R.id.action_close) {
/* 140 */       FragmentActivity activity = getActivity();
/* 141 */       if (null != activity) {
/* 142 */         if (Utils.isLargeTablet((Context)activity)) {
/* 143 */           this.mRedactionViewModel.onRedactBySearchCloseClicked();
/*     */         } else {
/* 145 */           dismiss();
/*     */         } 
/*     */       }
/* 148 */       return true;
/* 149 */     }  if (id == R.id.action_match_case) {
/* 150 */       boolean isChecked = item.isChecked();
/* 151 */       this.mSearchResultsView.setMatchCase(!isChecked);
/* 152 */       item.setChecked(!isChecked);
/* 153 */       return true;
/* 154 */     }  if (id == R.id.action_whole_word) {
/* 155 */       boolean isChecked = item.isChecked();
/* 156 */       this.mSearchResultsView.setWholeWord(!isChecked);
/* 157 */       item.setChecked(!isChecked);
/* 158 */       return true;
/*     */     } 
/* 160 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onSearchResultClicked(TextSearchResult result) {
/* 165 */     FragmentActivity activity = getActivity();
/* 166 */     if (null != activity) {
/* 167 */       this.mPdfViewCtrl.setCurrentPage(result.getPageNum());
/* 168 */       if (Utils.isLargeTablet((Context)activity)) {
/*     */         
/* 170 */         Rect bbox = this.mSearchResultsView.getRectForResult(result);
/* 171 */         if (bbox != null) {
/* 172 */           ViewerUtils.jumpToRect(this.mPdfViewCtrl, bbox, result.getPageNum());
/*     */         }
/* 174 */         this.mRedactionViewModel.onRedactBySearchItemClicked(result);
/*     */       } 
/* 176 */       this.mSearchResultsView.toggleSelection();
/* 177 */       if (this.mSearchResultsView.isAllSelected()) {
/* 178 */         this.mSelectAllCheckBox.setChecked(true);
/*     */       } else {
/* 180 */         this.mSelectAllCheckBox.setChecked(false);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onFullTextSearchStart() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onSearchResultFound(TextSearchResult result) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void onRedactionSearchStart() {
/* 198 */     this.mSelectAllCheckBox.setChecked(false);
/* 199 */     this.mSearchResultsView.deselectAll();
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\dialog\redaction\SearchRedactionDialogFragment.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */