/*     */ package com.pdftron.pdf.controls;
/*     */ 
/*     */ import android.app.AlertDialog;
/*     */ import android.app.Dialog;
/*     */ import android.content.Context;
/*     */ import android.content.DialogInterface;
/*     */ import android.os.Bundle;
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.widget.Button;
/*     */ import android.widget.CheckBox;
/*     */ import android.widget.LinearLayout;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.fragment.app.DialogFragment;
/*     */ import com.pdftron.pdf.tools.R;
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
/*     */ public class PrintAnnotationsSummaryDialogFragment
/*     */   extends DialogFragment
/*     */ {
/*     */   private PrintAnnotationsSummaryListener mPrintAnnotationsSummaryListener;
/*     */   private static final String DOCUMENT_CHECKED = "document_checked";
/*     */   private static final String ANNOTATIONS_CHECKED = "annotations_checked";
/*     */   private static final String SUMMARY_CHECKED = "summary_checked";
/*     */   private boolean mDocumentChecked;
/*     */   private boolean mAnnotationsChecked;
/*     */   private boolean mSummaryChecked;
/*  41 */   LinearLayout mLinearLayoutRoot = null;
/*  42 */   LinearLayout mLinearLayoutDocument = null;
/*  43 */   LinearLayout mLinearLayoutAnnotations = null;
/*  44 */   LinearLayout mLinearLayoutSummary = null;
/*     */   
/*  46 */   Button mDialogButton = null;
/*  47 */   AlertDialog mDialog = null;
/*  48 */   CheckBox mCheckBoxAnnots = null;
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
/*     */   public static PrintAnnotationsSummaryDialogFragment newInstance(boolean documentChecked, boolean annotationsChecked, boolean summaryChecked) {
/*  62 */     PrintAnnotationsSummaryDialogFragment fragment = new PrintAnnotationsSummaryDialogFragment();
/*     */     
/*  64 */     Bundle args = new Bundle();
/*  65 */     args.putBoolean("document_checked", documentChecked);
/*  66 */     args.putBoolean("annotations_checked", annotationsChecked);
/*  67 */     args.putBoolean("summary_checked", summaryChecked);
/*     */     
/*  69 */     fragment.setArguments(args);
/*     */     
/*  71 */     return fragment;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onCreate(@Nullable Bundle savedInstanceState) {
/*  76 */     super.onCreate(savedInstanceState);
/*     */     
/*  78 */     Bundle args = getArguments();
/*  79 */     if (args != null) {
/*  80 */       this.mDocumentChecked = args.getBoolean("document_checked", true);
/*  81 */       this.mAnnotationsChecked = args.getBoolean("annotations_checked", true);
/*  82 */       this.mSummaryChecked = args.getBoolean("summary_checked", false);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public Dialog onCreateDialog(Bundle savedInstanceState) {
/*  92 */     AlertDialog.Builder builder = new AlertDialog.Builder((Context)getActivity());
/*  93 */     LayoutInflater inflater = getActivity().getLayoutInflater();
/*  94 */     View view = inflater.inflate(R.layout.dialog_print_annotations_summary, null);
/*  95 */     builder.setView(view);
/*     */     
/*  97 */     this.mLinearLayoutRoot = (LinearLayout)view.findViewById(R.id.dialog_print_annotations_summary_root_view);
/*  98 */     this.mLinearLayoutDocument = (LinearLayout)view.findViewById(R.id.document_content_view);
/*  99 */     this.mLinearLayoutAnnotations = (LinearLayout)view.findViewById(R.id.annotations_content_view);
/* 100 */     this.mLinearLayoutSummary = (LinearLayout)view.findViewById(R.id.summary_content_view);
/*     */     
/* 102 */     ViewGroup.LayoutParams lp = this.mLinearLayoutDocument.getLayoutParams();
/* 103 */     if (this.mDocumentChecked) {
/* 104 */       lp.height = (int)getActivity().getResources().getDimension(R.dimen.print_annotations_summary_dist_document_annotations);
/* 105 */       this.mLinearLayoutAnnotations.setVisibility(0);
/*     */     } else {
/* 107 */       lp.height = (int)getActivity().getResources().getDimension(R.dimen.print_annotations_summary_dist_annotations_summary);
/* 108 */       this.mLinearLayoutAnnotations.setVisibility(8);
/*     */     } 
/*     */     
/* 111 */     lp = this.mLinearLayoutAnnotations.getLayoutParams();
/* 112 */     lp.height = (int)getActivity().getResources().getDimension(R.dimen.print_annotations_summary_dist_annotations_summary);
/*     */     
/* 114 */     lp = this.mLinearLayoutSummary.getLayoutParams();
/* 115 */     if (this.mDocumentChecked) {
/* 116 */       lp.height = (int)getActivity().getResources().getDimension(R.dimen.print_annotations_summary_dist_document_annotations);
/*     */     } else {
/* 118 */       lp.height = 2 * (int)getActivity().getResources().getDimension(R.dimen.print_annotations_summary_dist_document_annotations);
/*     */     } 
/*     */     
/* 121 */     CheckBox checkBoxDocument = (CheckBox)view.findViewById(R.id.checkBoxDocument);
/* 122 */     checkBoxDocument.setChecked(this.mDocumentChecked);
/* 123 */     checkBoxDocument.setOnClickListener(new View.OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/* 126 */             PrintAnnotationsSummaryDialogFragment.this.mDocumentChecked = !PrintAnnotationsSummaryDialogFragment.this.mDocumentChecked;
/* 127 */             PrintAnnotationsSummaryDialogFragment.this.updateWidgets();
/*     */           }
/*     */         });
/*     */     
/* 131 */     this.mCheckBoxAnnots = (CheckBox)view.findViewById(R.id.checkBoxAnnots);
/* 132 */     this.mCheckBoxAnnots.setChecked(this.mAnnotationsChecked);
/* 133 */     this.mCheckBoxAnnots.setOnClickListener(new View.OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/* 136 */             PrintAnnotationsSummaryDialogFragment.this.mAnnotationsChecked = !PrintAnnotationsSummaryDialogFragment.this.mAnnotationsChecked;
/* 137 */             PrintAnnotationsSummaryDialogFragment.this.updateWidgets();
/*     */           }
/*     */         });
/*     */     
/* 141 */     CheckBox checkBoxSummary = (CheckBox)view.findViewById(R.id.checkBoxSummary);
/* 142 */     checkBoxSummary.setChecked(this.mSummaryChecked);
/* 143 */     checkBoxSummary.setOnClickListener(new View.OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/* 146 */             PrintAnnotationsSummaryDialogFragment.this.mSummaryChecked = !PrintAnnotationsSummaryDialogFragment.this.mSummaryChecked;
/* 147 */             PrintAnnotationsSummaryDialogFragment.this.updateWidgets();
/*     */           }
/*     */         });
/*     */     
/* 151 */     builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener()
/*     */         {
/*     */           public void onClick(DialogInterface dialog, int which)
/*     */           {
/* 155 */             if (PrintAnnotationsSummaryDialogFragment.this.mPrintAnnotationsSummaryListener != null) {
/* 156 */               PrintAnnotationsSummaryDialogFragment.this.mPrintAnnotationsSummaryListener.onConfirmPrintAnnotationSummary(PrintAnnotationsSummaryDialogFragment.this.mDocumentChecked, PrintAnnotationsSummaryDialogFragment.this.mAnnotationsChecked, PrintAnnotationsSummaryDialogFragment.this.mSummaryChecked);
/*     */             }
/*     */           }
/*     */         });
/* 160 */     builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
/*     */         {
/*     */           public void onClick(DialogInterface dialog, int which)
/*     */           {
/* 164 */             PrintAnnotationsSummaryDialogFragment.this.dismiss();
/*     */           }
/*     */         });
/*     */     
/* 168 */     builder.setTitle(R.string.dialog_print_annotations_summary_title);
/*     */     
/* 170 */     this.mDialog = builder.create();
/* 171 */     return (Dialog)this.mDialog;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onStart() {
/* 179 */     super.onStart();
/* 180 */     this.mDialogButton = this.mDialog.getButton(-1);
/*     */     
/* 182 */     updateWidgets();
/*     */   }
/*     */   
/*     */   public void setPrintAnnotationsSummaryListener(PrintAnnotationsSummaryListener listener) {
/* 186 */     this.mPrintAnnotationsSummaryListener = listener;
/*     */   }
/*     */   
/*     */   private void updateWidgets() {
/* 190 */     if (this.mDialogButton != null) {
/* 191 */       if (!this.mSummaryChecked && !this.mDocumentChecked) {
/* 192 */         this.mDialogButton.setEnabled(false);
/*     */       } else {
/* 194 */         this.mDialogButton.setEnabled(true);
/*     */       } 
/*     */     }
/*     */     
/* 198 */     ViewGroup.LayoutParams lpDocument = this.mLinearLayoutDocument.getLayoutParams();
/* 199 */     ViewGroup.LayoutParams lpSummary = this.mLinearLayoutSummary.getLayoutParams();
/* 200 */     if (this.mDocumentChecked) {
/* 201 */       lpDocument.height = (int)getActivity().getResources().getDimension(R.dimen.print_annotations_summary_dist_document_annotations);
/* 202 */       lpSummary.height = (int)getActivity().getResources().getDimension(R.dimen.print_annotations_summary_dist_document_annotations);
/* 203 */       this.mLinearLayoutAnnotations.setVisibility(0);
/*     */     } else {
/* 205 */       lpDocument.height = (int)getActivity().getResources().getDimension(R.dimen.print_annotations_summary_dist_annotations_summary);
/* 206 */       lpSummary.height = 2 * (int)getActivity().getResources().getDimension(R.dimen.print_annotations_summary_dist_document_annotations);
/* 207 */       this.mLinearLayoutAnnotations.setVisibility(8);
/*     */     } 
/*     */     
/* 210 */     if (this.mDocumentChecked && this.mSummaryChecked) {
/* 211 */       this.mCheckBoxAnnots.setChecked(true);
/* 212 */       this.mCheckBoxAnnots.setEnabled(false);
/*     */     } else {
/* 214 */       this.mCheckBoxAnnots.setChecked(this.mAnnotationsChecked);
/* 215 */       this.mCheckBoxAnnots.setEnabled(true);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static interface PrintAnnotationsSummaryListener {
/*     */     void onConfirmPrintAnnotationSummary(boolean param1Boolean1, boolean param1Boolean2, boolean param1Boolean3);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\controls\PrintAnnotationsSummaryDialogFragment.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */