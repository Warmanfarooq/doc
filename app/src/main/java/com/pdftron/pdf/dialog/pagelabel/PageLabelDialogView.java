/*     */ package com.pdftron.pdf.dialog.pagelabel;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.text.Editable;
/*     */ import android.text.TextWatcher;
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.widget.AdapterView;
/*     */ import android.widget.ArrayAdapter;
/*     */ import android.widget.CompoundButton;
/*     */ import android.widget.EditText;
/*     */ import android.widget.RadioButton;
/*     */ import android.widget.Spinner;
/*     */ import android.widget.SpinnerAdapter;
/*     */ import android.widget.TextView;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ 
/*     */ 
/*     */ 
/*     */ class PageLabelDialogView
/*     */   extends PageLabelView
/*     */ {
/*     */   private RadioButton mPageAll;
/*     */   private RadioButton mPageSelected;
/*     */   private RadioButton mPageRange;
/*     */   private EditText mFromPageEditTxt;
/*     */   private EditText mToPageEditTxt;
/*     */   private Spinner mStyleSpinner;
/*     */   private EditText mPrefixEditTxt;
/*     */   private EditText mStartNumEditTxt;
/*     */   private TextView mMaxPage;
/*     */   private TextView mPreview;
/*     */   private String mSelectedPageStr;
/*     */   private String mMaxPageStr;
/*     */   private String mPreviewTextStr;
/*     */   private String mInvalidStartNumStr;
/*     */   private String mInvalidPageRange;
/*     */   
/*     */   PageLabelDialogView(@NonNull ViewGroup parent, @NonNull PageLabelSettingChangeListener listener) {
/*  43 */     super(parent, listener);
/*  44 */     Context context = parent.getContext();
/*     */ 
/*     */     
/*  47 */     this.mSelectedPageStr = context.getResources().getString(R.string.page_label_selected_page);
/*  48 */     this.mMaxPageStr = context.getResources().getString(R.string.page_label_max_page);
/*  49 */     this.mPreviewTextStr = context.getString(R.string.page_label_preview);
/*  50 */     this.mInvalidStartNumStr = context.getString(R.string.page_label_invalid_start);
/*  51 */     this.mInvalidPageRange = context.getString(R.string.page_label_invalid_range);
/*     */ 
/*     */     
/*  54 */     View container = LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_page_label, parent, true);
/*     */     
/*  56 */     setupPageSettings(container, listener);
/*  57 */     setupNumberingSettings(container, listener);
/*  58 */     this.mPreview = (TextView)container.findViewById(R.id.page_label_preview);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updatePreview(String preview) {
/*  63 */     this.mPreview.setText(String.format("%s: %s", new Object[] { this.mPreviewTextStr, preview }));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void initViewStates(@Nullable PageLabelSetting mInitState) {
/*  69 */     if (mInitState != null) {
/*     */       
/*  71 */       this.mPageAll.setChecked(mInitState.isAll());
/*  72 */       this.mPageSelected.setChecked(mInitState.isSelectedPage());
/*  73 */       this.mPageRange.setChecked((!mInitState.isAll() && !mInitState.isSelectedPage()));
/*     */ 
/*     */       
/*  76 */       this.mToPageEditTxt.setText(String.valueOf(mInitState.getToPage()));
/*  77 */       this.mFromPageEditTxt.setText(String.valueOf(mInitState.getFromPage()));
/*  78 */       setPageRangeEnabled(this.mFromPageEditTxt, this.mToPageEditTxt, (
/*  79 */           !mInitState.isAll() && !mInitState.isSelectedPage()));
/*     */ 
/*     */       
/*  82 */       String selectedPage = String.format(this.mSelectedPageStr, new Object[] { Integer.valueOf(mInitState.selectedPage) });
/*  83 */       this.mPageSelected.setText(selectedPage);
/*     */       
/*  85 */       String numPages = String.format(this.mMaxPageStr, new Object[] { Integer.valueOf(mInitState.numPages) });
/*  86 */       this.mMaxPage.setText(numPages);
/*     */ 
/*     */       
/*  89 */       this.mStyleSpinner.setSelection(mInitState.getStyle().ordinal());
/*  90 */       this.mPrefixEditTxt.setText(mInitState.getPrefix());
/*  91 */       this.mStartNumEditTxt.setEnabled((mInitState.getStyle() != PageLabelSetting.PageLabelStyle.NONE));
/*  92 */       this.mStartNumEditTxt.setText(String.valueOf(mInitState.getStartNum()));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void invalidFromPage(boolean isValid) {
/*  98 */     if (isValid) {
/*  99 */       this.mFromPageEditTxt.setError(null);
/*     */     } else {
/* 101 */       this.mFromPageEditTxt.setError(this.mInvalidPageRange);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void invalidToPage(boolean isValid) {
/* 107 */     if (isValid) {
/* 108 */       this.mToPageEditTxt.setError(null);
/*     */     } else {
/* 110 */       this.mToPageEditTxt.setError(this.mInvalidPageRange);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void invalidStartNumber(boolean isValid) {
/* 116 */     if (isValid) {
/* 117 */       this.mStartNumEditTxt.setError(null);
/*     */     } else {
/* 119 */       this.mStartNumEditTxt.setError(this.mInvalidStartNumStr);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setupPageSettings(@NonNull View container, @NonNull final PageLabelSettingChangeListener listener) {
/* 127 */     this.mPageAll = (RadioButton)container.findViewById(R.id.radio_pages_all);
/* 128 */     this.mPageSelected = (RadioButton)container.findViewById(R.id.radio_pages_selected);
/* 129 */     this.mPageRange = (RadioButton)container.findViewById(R.id.radio_pages_range);
/* 130 */     this.mFromPageEditTxt = (EditText)container.findViewById(R.id.page_range_from_edittext);
/* 131 */     this.mToPageEditTxt = (EditText)container.findViewById(R.id.page_range_to_edittext);
/* 132 */     this.mMaxPage = (TextView)container.findViewById(R.id.page_range_max);
/*     */ 
/*     */     
/* 135 */     this.mPageAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
/*     */         {
/*     */           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
/* 138 */             if (isChecked) {
/* 139 */               PageLabelDialogView.this.mPageSelected.setChecked(false);
/* 140 */               PageLabelDialogView.this.mPageRange.setChecked(false);
/* 141 */               listener.setSelectedPage(false);
/*     */             } 
/* 143 */             listener.setAll(isChecked);
/*     */           }
/*     */         });
/*     */     
/* 147 */     this.mPageSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
/*     */         {
/*     */           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
/* 150 */             if (isChecked) {
/* 151 */               PageLabelDialogView.this.mPageAll.setChecked(false);
/* 152 */               PageLabelDialogView.this.mPageRange.setChecked(false);
/* 153 */               listener.setAll(false);
/*     */             } 
/* 155 */             listener.setSelectedPage(isChecked);
/*     */           }
/*     */         });
/*     */     
/* 159 */     this.mPageRange.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
/*     */         {
/*     */           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
/* 162 */             if (isChecked) {
/* 163 */               PageLabelDialogView.this.mPageAll.setChecked(false);
/* 164 */               PageLabelDialogView.this.mPageSelected.setChecked(false);
/* 165 */               listener.setAll(false);
/* 166 */               listener.setSelectedPage(false);
/*     */             } 
/* 168 */             PageLabelDialogView.this.setPageRangeEnabled(PageLabelDialogView.this.mFromPageEditTxt, PageLabelDialogView.this.mToPageEditTxt, isChecked);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 173 */     this.mFromPageEditTxt.addTextChangedListener(new SimpleTextWatcher()
/*     */         {
/*     */           public void afterTextChanged(Editable s) {
/* 176 */             String fromText = s.toString();
/* 177 */             String toText = PageLabelDialogView.this.mToPageEditTxt.getEditableText().toString();
/* 178 */             listener.setPageRange(fromText, toText);
/*     */           }
/*     */         });
/* 181 */     this.mToPageEditTxt.addTextChangedListener(new SimpleTextWatcher()
/*     */         {
/*     */           public void afterTextChanged(Editable s) {
/* 184 */             String fromText = PageLabelDialogView.this.mFromPageEditTxt.getEditableText().toString();
/* 185 */             String toText = s.toString();
/* 186 */             listener.setPageRange(fromText, toText);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private void setupNumberingSettings(@NonNull View container, @NonNull final PageLabelSettingChangeListener listener) {
/* 192 */     this.mStyleSpinner = (Spinner)container.findViewById(R.id.numbering_style_spinner);
/* 193 */     this.mStyleSpinner.setAdapter(getStyleSpinnerAdapter(container));
/* 194 */     this.mPrefixEditTxt = (EditText)container.findViewById(R.id.numbering_prefix_edittext);
/* 195 */     this.mStartNumEditTxt = (EditText)container.findViewById(R.id.numbering_start_edittext);
/*     */ 
/*     */     
/* 198 */     this.mStyleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
/*     */         {
/*     */           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
/* 201 */             PageLabelSetting.PageLabelStyle style = PageLabelSetting.PageLabelStyle.values()[position];
/* 202 */             listener.setStyle(style);
/* 203 */             PageLabelDialogView.this.mStartNumEditTxt.setEnabled((style != PageLabelSetting.PageLabelStyle.NONE));
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onNothingSelected(AdapterView<?> parent) {}
/*     */         });
/* 212 */     this.mPrefixEditTxt.addTextChangedListener(new SimpleTextWatcher()
/*     */         {
/*     */           public void afterTextChanged(Editable s) {
/* 215 */             listener.setPrefix(s.toString());
/*     */           }
/*     */         });
/*     */     
/* 219 */     this.mStartNumEditTxt.addTextChangedListener(new SimpleTextWatcher()
/*     */         {
/*     */           public void afterTextChanged(Editable s) {
/* 222 */             listener.setStartNumber(s.toString());
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private SpinnerAdapter getStyleSpinnerAdapter(View parent) {
/* 228 */     ArrayAdapter<CharSequence> adapter = new ArrayAdapter(parent.getContext(), 17367048);
/* 229 */     for (PageLabelSetting.PageLabelStyle style : PageLabelSetting.PageLabelStyle.values()) {
/* 230 */       adapter.add(style.mLabel);
/*     */     }
/* 232 */     adapter.setDropDownViewResource(17367049);
/* 233 */     return (SpinnerAdapter)adapter;
/*     */   }
/*     */   
/*     */   private void setPageRangeEnabled(EditText fromPageEt, EditText toPageEt, boolean isEnabled) {
/* 237 */     fromPageEt.setEnabled(isEnabled);
/* 238 */     toPageEt.setEnabled(isEnabled);
/*     */   }
/*     */   
/*     */   private static abstract class SimpleTextWatcher implements TextWatcher {
/*     */     private SimpleTextWatcher() {}
/*     */     
/*     */     public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
/*     */     
/*     */     public void onTextChanged(CharSequence s, int start, int before, int count) {}
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\dialog\pagelabel\PageLabelDialogView.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */