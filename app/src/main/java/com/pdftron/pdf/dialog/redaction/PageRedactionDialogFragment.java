/*     */ package com.pdftron.pdf.dialog.redaction;
/*     */ 
/*     */ import android.app.AlertDialog;
/*     */ import android.app.Dialog;
/*     */ import android.content.Context;
/*     */ import android.content.DialogInterface;
/*     */ import android.os.Bundle;
/*     */ import android.text.Editable;
/*     */ import android.text.TextWatcher;
/*     */ import android.view.View;
/*     */ import android.widget.Button;
/*     */ import android.widget.CompoundButton;
/*     */ import android.widget.EditText;
/*     */ import android.widget.RadioButton;
/*     */ import android.widget.TextView;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.fragment.app.DialogFragment;
/*     */ import androidx.fragment.app.FragmentActivity;
/*     */ import androidx.lifecycle.ViewModelProviders;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.viewmodel.RedactionViewModel;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PageRedactionDialogFragment
/*     */   extends DialogFragment
/*     */ {
/*  30 */   public static final String TAG = PageRedactionDialogFragment.class.getName();
/*     */   
/*     */   private static final String CURRENT_PAGE = "RedactByPageDialog_Initial_currentpage";
/*     */   
/*     */   private static final String FROM_PAGE = "RedactByPageDialog_Initial_frompage";
/*     */   
/*     */   private static final String TO_PAGE = "RedactByPageDialog_Initial_topage";
/*     */   
/*     */   private static final String MAX_PAGE = "RedactByPageDialog_Initial_maxpage";
/*     */   private RadioButton mCurrentPageBtn;
/*     */   private RadioButton mPageRangeBtn;
/*     */   private EditText mFromPageEditText;
/*     */   private EditText mToPageEditText;
/*     */   private int mCurrentPage;
/*     */   private int mPageCount;
/*     */   private int mFromPage;
/*     */   private int mToPage;
/*     */   
/*     */   public static PageRedactionDialogFragment newInstance(int currentPage, int maxPage) {
/*  49 */     return newInstance(currentPage, 0, 0, maxPage);
/*     */   }
/*     */   
/*     */   public static PageRedactionDialogFragment newInstance(int currentPage, int fromPage, int toPage, int maxPage) {
/*  53 */     PageRedactionDialogFragment fragment = new PageRedactionDialogFragment();
/*  54 */     Bundle args = new Bundle();
/*  55 */     args.putInt("RedactByPageDialog_Initial_currentpage", currentPage);
/*  56 */     args.putInt("RedactByPageDialog_Initial_frompage", fromPage);
/*  57 */     args.putInt("RedactByPageDialog_Initial_topage", toPage);
/*  58 */     args.putInt("RedactByPageDialog_Initial_maxpage", maxPage);
/*  59 */     fragment.setArguments(args);
/*  60 */     return fragment;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
/*  66 */     FragmentActivity activity = getActivity();
/*  67 */     if (activity == null) {
/*  68 */       return super.onCreateDialog(savedInstanceState);
/*     */     }
/*     */     
/*  71 */     this.mCurrentPage = 1;
/*  72 */     this.mFromPage = 0;
/*  73 */     this.mToPage = 0;
/*     */     
/*  75 */     Bundle args = getArguments();
/*  76 */     if (args != null) {
/*  77 */       this.mCurrentPage = args.getInt("RedactByPageDialog_Initial_currentpage");
/*  78 */       this.mFromPage = args.getInt("RedactByPageDialog_Initial_frompage");
/*  79 */       this.mToPage = args.getInt("RedactByPageDialog_Initial_topage");
/*  80 */       this.mPageCount = args.getInt("RedactByPageDialog_Initial_maxpage");
/*     */     } 
/*     */     
/*  83 */     View view = activity.getLayoutInflater().inflate(R.layout.dialog_redact_by_page, null);
/*     */     
/*  85 */     this.mCurrentPageBtn = (RadioButton)view.findViewById(R.id.radio_pages_current);
/*  86 */     this.mPageRangeBtn = (RadioButton)view.findViewById(R.id.radio_pages_range);
/*  87 */     this.mFromPageEditText = (EditText)view.findViewById(R.id.page_range_from_edittext);
/*  88 */     this.mToPageEditText = (EditText)view.findViewById(R.id.page_range_to_edittext);
/*  89 */     TextView maxPageEditText = (TextView)view.findViewById(R.id.page_range_max);
/*     */     
/*  91 */     if (this.mFromPage > 0 && this.mToPage > 0) {
/*  92 */       this.mPageRangeBtn.setChecked(true);
/*  93 */       setPageRangeEnabled(true);
/*     */     } else {
/*  95 */       this.mCurrentPageBtn.setChecked(true);
/*  96 */       setPageRangeEnabled(false);
/*  97 */       this.mFromPage = this.mToPage = this.mCurrentPage;
/*     */     } 
/*     */     
/* 100 */     String currentPageStr = String.format(view
/* 101 */         .getContext().getResources().getString(R.string.redact_by_page_current), new Object[] { Integer.valueOf(this.mCurrentPage) });
/* 102 */     this.mCurrentPageBtn.setText(currentPageStr);
/* 103 */     this.mFromPageEditText.setText(String.valueOf(this.mFromPage));
/* 104 */     this.mToPageEditText.setText(String.valueOf(this.mToPage));
/*     */     
/* 106 */     String numPages = String.format(view
/* 107 */         .getContext().getResources().getString(R.string.page_label_max_page), new Object[] { Integer.valueOf(this.mPageCount) });
/* 108 */     maxPageEditText.setText(numPages);
/*     */     
/* 110 */     checkPageRange();
/*     */ 
/*     */     
/* 113 */     this.mCurrentPageBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
/*     */         {
/*     */           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
/* 116 */             if (isChecked) {
/* 117 */               PageRedactionDialogFragment.this.mPageRangeBtn.setChecked(false);
/* 118 */               PageRedactionDialogFragment.this.setPageRangeEnabled(false);
/*     */             } 
/* 120 */             PageRedactionDialogFragment.this.checkPageRange();
/*     */           }
/*     */         });
/*     */     
/* 124 */     this.mPageRangeBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
/*     */         {
/*     */           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
/* 127 */             if (isChecked) {
/* 128 */               PageRedactionDialogFragment.this.mCurrentPageBtn.setChecked(false);
/* 129 */               PageRedactionDialogFragment.this.setPageRangeEnabled(true);
/*     */             } 
/* 131 */             PageRedactionDialogFragment.this.checkPageRange();
/*     */           }
/*     */         });
/*     */     
/* 135 */     this.mFromPageEditText.addTextChangedListener(new TextWatcher()
/*     */         {
/*     */           public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
/*     */ 
/*     */ 
/*     */           
/*     */           public void onTextChanged(CharSequence s, int start, int before, int count) {
/* 142 */             PageRedactionDialogFragment.this.checkPageRange();
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void afterTextChanged(Editable s) {}
/*     */         });
/* 150 */     this.mToPageEditText.addTextChangedListener(new TextWatcher()
/*     */         {
/*     */           public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
/*     */ 
/*     */ 
/*     */           
/*     */           public void onTextChanged(CharSequence s, int start, int before, int count) {
/* 157 */             PageRedactionDialogFragment.this.checkPageRange();
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void afterTextChanged(Editable s) {}
/*     */         });
/* 165 */     AlertDialog.Builder builder = new AlertDialog.Builder((Context)getActivity());
/* 166 */     builder.setView(view)
/* 167 */       .setTitle(R.string.redact_by_page_title)
/* 168 */       .setPositiveButton(R.string.mark_redaction, new DialogInterface.OnClickListener()
/*     */         {
/*     */           public void onClick(DialogInterface dialog, int which) {
/* 171 */             PageRedactionDialogFragment.this.onComplete();
/* 172 */             PageRedactionDialogFragment.this.dismiss();
/*     */           }
/* 175 */         }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
/*     */         {
/*     */           public void onClick(DialogInterface dialog, int which) {
/* 178 */             PageRedactionDialogFragment.this.dismiss();
/*     */           }
/*     */         });
/* 181 */     return (Dialog)builder.create();
/*     */   }
/*     */   
/*     */   private void setPositiveButtonEnabled(boolean enabled) {
/* 185 */     AlertDialog dialog = (AlertDialog)getDialog();
/* 186 */     Button posButton = (dialog == null) ? null : dialog.getButton(-1);
/* 187 */     if (posButton != null) {
/* 188 */       posButton.setEnabled(enabled);
/*     */     }
/*     */   }
/*     */   
/*     */   private void setPageRangeEnabled(boolean enabled) {
/* 193 */     this.mFromPageEditText.setEnabled(enabled);
/* 194 */     this.mToPageEditText.setEnabled(enabled);
/*     */   }
/*     */   
/*     */   private void checkPageRange() {
/* 198 */     if (this.mCurrentPageBtn.isChecked()) {
/* 199 */       setPositiveButtonEnabled(true);
/*     */       return;
/*     */     } 
/* 202 */     String fromText = this.mFromPageEditText.getEditableText().toString();
/* 203 */     String toText = this.mToPageEditText.getEditableText().toString();
/*     */     
/*     */     try {
/* 206 */       this.mFromPage = Integer.parseInt(fromText);
/* 207 */       this.mToPage = Integer.parseInt(toText);
/*     */ 
/*     */       
/* 210 */       boolean isFromPageCorrect = (this.mFromPage <= this.mToPage && this.mFromPage >= 1 && this.mFromPage <= this.mPageCount);
/* 211 */       boolean isToPageCorrect = (this.mFromPage <= this.mToPage && this.mToPage >= 1 && this.mToPage <= this.mPageCount);
/*     */       
/* 213 */       String invalidPageRange = this.mFromPageEditText.getContext().getString(R.string.page_label_invalid_range);
/*     */       
/* 215 */       this.mFromPageEditText.setError(isFromPageCorrect ? null : invalidPageRange);
/* 216 */       this.mToPageEditText.setError(isToPageCorrect ? null : invalidPageRange);
/*     */       
/* 218 */       if (isFromPageCorrect && isToPageCorrect) {
/* 219 */         setPositiveButtonEnabled(true);
/*     */       } else {
/* 221 */         setPositiveButtonEnabled(false);
/*     */       } 
/* 223 */     } catch (NumberFormatException e) {
/* 224 */       setPositiveButtonEnabled(false);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void onComplete() {
/* 229 */     boolean currentChecked = this.mCurrentPageBtn.isChecked();
/* 230 */     boolean rangedChecked = this.mPageRangeBtn.isChecked();
/*     */     
/* 232 */     FragmentActivity activity = getActivity();
/* 233 */     if (null == activity) {
/* 234 */       throw new RuntimeException("Not attached to a valid activity");
/*     */     }
/* 236 */     RedactionViewModel redactionViewModel = (RedactionViewModel)ViewModelProviders.of(activity).get(RedactionViewModel.class);
/*     */     
/* 238 */     ArrayList<Integer> pages = new ArrayList<>();
/* 239 */     if (currentChecked) {
/* 240 */       pages.add(Integer.valueOf(this.mCurrentPage));
/* 241 */     } else if (rangedChecked) {
/* 242 */       for (int i = this.mFromPage; i <= this.mToPage; i++) {
/* 243 */         pages.add(Integer.valueOf(i));
/*     */       }
/*     */     } 
/* 246 */     redactionViewModel.onRedactByPage(pages);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\dialog\redaction\PageRedactionDialogFragment.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */