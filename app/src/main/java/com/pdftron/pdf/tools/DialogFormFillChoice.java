/*     */ package com.pdftron.pdf.tools;
/*     */ 
/*     */ import android.app.AlertDialog;
/*     */ import android.content.DialogInterface;
/*     */ import android.os.Bundle;
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.View;
/*     */ import android.view.inputmethod.InputMethodManager;
/*     */ import android.widget.Button;
/*     */ import android.widget.EditText;
/*     */ import android.widget.LinearLayout;
/*     */ import android.widget.RadioButton;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.recyclerview.widget.LinearLayoutManager;
/*     */ import androidx.recyclerview.widget.RecyclerView;
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.Action;
/*     */ import com.pdftron.pdf.ActionParameter;
/*     */ import com.pdftron.pdf.Annot;
/*     */ import com.pdftron.pdf.Field;
/*     */ import com.pdftron.pdf.PDFDoc;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.ViewChangeCollection;
/*     */ import com.pdftron.pdf.adapter.FormFillAdapter;
/*     */ import com.pdftron.pdf.annots.ComboBoxWidget;
/*     */ import com.pdftron.pdf.annots.ListBoxWidget;
/*     */ import com.pdftron.pdf.annots.Widget;
/*     */ import com.pdftron.pdf.utils.ActionUtils;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.sdf.Obj;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.TreeSet;
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
/*     */ public class DialogFormFillChoice
/*     */   extends AlertDialog
/*     */   implements View.OnClickListener, DialogInterface.OnDismissListener, View.OnFocusChangeListener, FormFillAdapter.OnItemSelectListener
/*     */ {
/*     */   private PDFViewCtrl mPdfViewCtrl;
/*     */   private Annot mAnnot;
/*     */   private int mAnnotPageNum;
/*     */   private Field mField;
/*     */   private boolean mSingleChoice;
/*     */   private boolean mMultiChoice;
/*     */   private boolean mIsCombo;
/*     */   private boolean mIsEditable;
/*     */   private boolean mCancelled;
/*     */   private EditText mOtherOptionText;
/*     */   private RadioButton mOtherOptionRadioBtn;
/*     */   private FormFillAdapter mFormFillAdapter;
/*     */   
/*     */   public DialogFormFillChoice(@NonNull PDFViewCtrl pdfViewCtrl, @NonNull Annot annot, int annotPageNum) {
/*  78 */     super(pdfViewCtrl.getContext());
/*     */     HashSet<Integer> selectedPositions;
/*  80 */     this.mPdfViewCtrl = pdfViewCtrl;
/*  81 */     this.mAnnot = annot;
/*  82 */     this.mAnnotPageNum = annotPageNum;
/*  83 */     this.mSingleChoice = true;
/*  84 */     this.mIsCombo = false;
/*  85 */     this.mIsEditable = false;
/*     */     
/*  87 */     String[] selectedOptions = null;
/*  88 */     String[] options = null;
/*     */     
/*  90 */     boolean shouldUnlockRead = false;
/*     */     try {
/*  92 */       pdfViewCtrl.docLockRead();
/*  93 */       shouldUnlockRead = true;
/*     */       
/*  95 */       Widget w = new Widget(this.mAnnot);
/*  96 */       this.mField = w.getField();
/*  97 */       if (!this.mField.isValid()) {
/*  98 */         dismiss();
/*     */         return;
/*     */       } 
/* 101 */       this.mIsCombo = this.mField.getFlag(14);
/* 102 */       this.mSingleChoice = (this.mIsCombo || !this.mField.getFlag(17));
/* 103 */       this.mMultiChoice = this.mField.getFlag(17);
/* 104 */       this.mIsEditable = this.mField.getFlag(15);
/*     */       
/* 106 */       if (this.mIsCombo) {
/* 107 */         ComboBoxWidget comboBoxWidget = new ComboBoxWidget(this.mAnnot);
/* 108 */         selectedOptions = new String[1];
/* 109 */         selectedOptions[0] = comboBoxWidget.getSelectedOption();
/* 110 */         options = comboBoxWidget.getOptions();
/*     */       } else {
/* 112 */         ListBoxWidget listBoxWidget = new ListBoxWidget(this.mAnnot);
/* 113 */         selectedOptions = listBoxWidget.getSelectedOptions();
/* 114 */         options = listBoxWidget.getOptions();
/*     */       } 
/* 116 */     } catch (Exception e) {
/* 117 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/* 118 */       dismiss();
/*     */       return;
/*     */     } finally {
/* 121 */       if (shouldUnlockRead) {
/* 122 */         pdfViewCtrl.docUnlockRead();
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 127 */     LayoutInflater inflater = (LayoutInflater)this.mPdfViewCtrl.getContext().getSystemService("layout_inflater");
/* 128 */     if (inflater == null) {
/*     */       return;
/*     */     }
/* 131 */     View view = inflater.inflate(R.layout.tools_dialog_formfillchoice, null);
/* 132 */     setView(view);
/* 133 */     setOnDismissListener(this);
/*     */ 
/*     */     
/* 136 */     LinearLayout otherOptionLayout = (LinearLayout)view.findViewById(R.id.tools_dialog_formfillchoice_edit_text_layout);
/* 137 */     this.mOtherOptionText = (EditText)view.findViewById(R.id.tools_dialog_formfillchoice_edit_text);
/* 138 */     this.mOtherOptionText.setOnFocusChangeListener(this);
/* 139 */     this.mOtherOptionText.setSelectAllOnFocus(true);
/* 140 */     this.mOtherOptionRadioBtn = (RadioButton)view.findViewById(R.id.tools_dialog_formfillchoice_edit_text_ratio_button);
/* 141 */     this.mOtherOptionRadioBtn.setOnClickListener(this);
/* 142 */     Button confirmButton = (Button)view.findViewById(R.id.button_ok);
/* 143 */     confirmButton.setOnClickListener(this);
/* 144 */     Button cancelButton = (Button)view.findViewById(R.id.button_cancel);
/* 145 */     cancelButton.setOnClickListener(this);
/*     */     
/* 147 */     if (!this.mIsEditable) {
/* 148 */       otherOptionLayout.setVisibility(8);
/* 149 */       cancelButton.setVisibility(8);
/* 150 */       confirmButton.setVisibility(8);
/*     */     } 
/*     */ 
/*     */     
/* 154 */     if (this.mSingleChoice) {
/* 155 */       selectedPositions = new HashSet<>(1);
/*     */       try {
/* 157 */         String selectedOption = null;
/* 158 */         if (options != null && selectedOptions != null && selectedOptions.length == 1) {
/* 159 */           selectedOption = selectedOptions[0];
/* 160 */           for (int i = 0; i < options.length; i++) {
/* 161 */             String opt = options[i];
/* 162 */             if (selectedOption.equals(opt)) {
/* 163 */               selectedPositions.add(Integer.valueOf(i));
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/* 168 */         if (selectedPositions.isEmpty() && otherOptionLayout.getVisibility() == 0) {
/* 169 */           this.mOtherOptionRadioBtn.setChecked(true);
/* 170 */           if (selectedOption != null) {
/* 171 */             this.mOtherOptionText.setText(selectedOption);
/*     */           }
/*     */         } 
/* 174 */       } catch (Exception e) {
/* 175 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */       } 
/*     */     } else {
/* 178 */       selectedPositions = new HashSet<>();
/* 179 */       otherOptionLayout.setVisibility(8);
/* 180 */       confirmButton.setVisibility(8);
/* 181 */       cancelButton.setVisibility(8);
/*     */       try {
/* 183 */         if (options != null && selectedOptions != null) {
/* 184 */           ArrayList<String> selectedArray = new ArrayList<>(selectedOptions.length);
/* 185 */           Collections.addAll(selectedArray, selectedOptions);
/* 186 */           for (int i = 0; i < options.length; i++) {
/* 187 */             String opt = options[i];
/* 188 */             if (selectedArray.contains(opt)) {
/* 189 */               selectedPositions.add(Integer.valueOf(i));
/*     */             }
/*     */           } 
/*     */         } 
/* 193 */       } catch (Exception e) {
/* 194 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */       } 
/*     */     } 
/*     */     
/* 198 */     RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view_form_fill);
/* 199 */     recyclerView.setLayoutManager((RecyclerView.LayoutManager)new LinearLayoutManager(pdfViewCtrl.getContext()));
/* 200 */     this.mFormFillAdapter = new FormFillAdapter(this.mField, selectedPositions, this);
/* 201 */     recyclerView.setAdapter((RecyclerView.Adapter)this.mFormFillAdapter);
/*     */     
/* 203 */     int selectedPosition = -1;
/* 204 */     if (!this.mSingleChoice && selectedPositions != null) {
/* 205 */       Iterator<Integer> itr = selectedPositions.iterator();
/* 206 */       if (itr.hasNext()) {
/* 207 */         selectedPosition = ((Integer)itr.next()).intValue();
/*     */       }
/*     */     } 
/* 210 */     if (selectedPosition != -1) {
/* 211 */       recyclerView.scrollToPosition(selectedPosition);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onClick(View v) {
/* 220 */     if (v.getId() == R.id.button_ok) {
/*     */       try {
/* 222 */         String defaultStr = this.mField.getDefaultValueAsString();
/* 223 */         if (this.mOtherOptionText.getText().toString().equals("")) {
/* 224 */           this.mOtherOptionText.setText(defaultStr);
/*     */         }
/* 226 */       } catch (PDFNetException e) {
/* 227 */         AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*     */       } 
/* 229 */       dismiss();
/* 230 */     } else if (v.getId() == R.id.button_cancel) {
/* 231 */       this.mCancelled = true;
/* 232 */       dismiss();
/* 233 */     } else if (v.getId() == this.mOtherOptionRadioBtn.getId() && this.mFormFillAdapter.hasSingleSelectedPosition()) {
/* 234 */       this.mFormFillAdapter.clearSingleSelectedPosition();
/* 235 */       this.mOtherOptionRadioBtn.setChecked(true);
/* 236 */       this.mOtherOptionText.requestFocus();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onItemSelected(int position) {
/* 245 */     if (this.mSingleChoice || (!this.mIsEditable && !this.mMultiChoice)) {
/* 246 */       dismiss();
/* 247 */     } else if (this.mOtherOptionText.hasFocus()) {
/* 248 */       this.mOtherOptionText.clearFocus();
/*     */       
/* 250 */       InputMethodManager imm = (InputMethodManager)getContext().getSystemService("input_method");
/* 251 */       if (imm != null) {
/* 252 */         imm.hideSoftInputFromWindow(this.mOtherOptionText.getWindowToken(), 0);
/*     */       }
/*     */     } 
/* 255 */     this.mOtherOptionRadioBtn.setChecked(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDismiss(DialogInterface dialog) {
/*     */     int fieldCount;
/* 263 */     if (this.mCancelled) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 269 */       fieldCount = this.mField.getOptCount();
/* 270 */     } catch (Exception e) {
/* 271 */       fieldCount = 0;
/*     */     } 
/*     */     
/* 274 */     boolean shouldUnlock = false;
/*     */     try {
/* 276 */       this.mPdfViewCtrl.docLock(true);
/* 277 */       shouldUnlock = true;
/*     */       
/* 279 */       if (this.mSingleChoice) {
/*     */         String str;
/*     */ 
/*     */         
/* 283 */         if (!this.mFormFillAdapter.hasSingleSelectedPosition()) {
/* 284 */           str = this.mOtherOptionText.getText().toString();
/*     */         } else {
/* 286 */           str = this.mField.getOpt(this.mFormFillAdapter.getSingleSelectedPosition());
/*     */         } 
/*     */         
/* 289 */         if (!this.mIsCombo || !this.mField.getValueAsString().equals(str)) {
/* 290 */           raiseAnnotationPreEditEvent(this.mAnnot);
/* 291 */           ViewChangeCollection view_change = this.mField.setValue(str);
/* 292 */           this.mPdfViewCtrl.refreshAndUpdate(view_change);
/* 293 */           executeAction(this.mField, 6);
/* 294 */           executeAction(this.mField, 2);
/*     */ 
/*     */           
/* 297 */           Widget widget = new Widget(this.mAnnot);
/* 298 */           Tool.updateFont(this.mPdfViewCtrl, widget, str);
/*     */           
/* 300 */           raiseAnnotationEditedEvent(this.mAnnot);
/*     */         } 
/* 302 */       } else if (fieldCount > 0) {
/* 303 */         raiseAnnotationPreEditEvent(this.mAnnot);
/* 304 */         PDFDoc doc = this.mPdfViewCtrl.getDoc();
/* 305 */         Obj arr = doc.createIndirectArray();
/* 306 */         HashSet<Integer> selectedPositions = this.mFormFillAdapter.getSelectedPositions();
/* 307 */         TreeSet<Integer> treeSet = new TreeSet<>(selectedPositions);
/*     */         
/* 309 */         StringBuilder strBuilder = new StringBuilder();
/* 310 */         for (Iterator<Integer> iterator = treeSet.iterator(); iterator.hasNext(); ) { int selectedPosition = ((Integer)iterator.next()).intValue();
/* 311 */           String text = this.mField.getOpt(selectedPosition);
/* 312 */           arr.pushBackText(text);
/* 313 */           strBuilder.append(text); }
/*     */         
/* 315 */         ViewChangeCollection view_change = this.mField.setValue(arr);
/* 316 */         this.mPdfViewCtrl.refreshAndUpdate(view_change);
/* 317 */         executeAction(this.mField, 6);
/* 318 */         executeAction(this.mField, 2);
/*     */ 
/*     */         
/* 321 */         Widget widget = new Widget(this.mAnnot);
/* 322 */         Tool.updateFont(this.mPdfViewCtrl, widget, strBuilder.toString());
/*     */         
/* 324 */         raiseAnnotationEditedEvent(this.mAnnot);
/*     */       } 
/* 326 */     } catch (Exception e) {
/* 327 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */     } finally {
/* 329 */       if (shouldUnlock) {
/* 330 */         this.mPdfViewCtrl.docUnlock();
/*     */       }
/* 332 */       dismiss();
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
/*     */   private void executeAction(Field fld, int type) {
/* 345 */     if (this.mAnnot != null) {
/*     */       try {
/* 347 */         Obj aa = this.mAnnot.getTriggerAction(type);
/* 348 */         if (aa != null) {
/*     */           
/* 350 */           Action a = new Action(aa);
/*     */           
/* 352 */           ActionParameter action_param = new ActionParameter(a, fld);
/* 353 */           ActionUtils.getInstance().executeAction(action_param, this.mPdfViewCtrl);
/*     */         } 
/* 355 */       } catch (PDFNetException e) {
/* 356 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private void raiseAnnotationPreEditEvent(Annot annot) {
/* 362 */     ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 363 */     HashMap<Annot, Integer> annots = new HashMap<>(1);
/* 364 */     annots.put(annot, Integer.valueOf(this.mAnnotPageNum));
/* 365 */     toolManager.raiseAnnotationsPreModifyEvent(annots);
/*     */   }
/*     */   
/*     */   private void raiseAnnotationEditedEvent(Annot annot) {
/* 369 */     ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 370 */     HashMap<Annot, Integer> annots = new HashMap<>(1);
/* 371 */     annots.put(annot, Integer.valueOf(this.mAnnotPageNum));
/*     */     
/* 373 */     Bundle bundle = Tool.getAnnotationModificationBundle(null);
/* 374 */     toolManager.raiseAnnotationsModifiedEvent(annots, bundle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onFocusChange(View v, boolean hasFocus) {
/* 382 */     if (v instanceof EditText)
/* 383 */       if (hasFocus) {
/* 384 */         if (this.mFormFillAdapter.hasSingleSelectedPosition()) {
/* 385 */           this.mFormFillAdapter.clearSingleSelectedPosition();
/*     */           
/* 387 */           InputMethodManager imm = (InputMethodManager)getContext().getSystemService("input_method");
/* 388 */           if (imm != null) {
/* 389 */             imm.showSoftInputFromInputMethod(this.mOtherOptionText.getWindowToken(), 0);
/*     */           }
/*     */         } 
/* 392 */         this.mOtherOptionRadioBtn.setChecked(true);
/*     */       } else {
/* 394 */         this.mOtherOptionRadioBtn.setChecked(false);
/*     */       }  
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\DialogFormFillChoice.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */