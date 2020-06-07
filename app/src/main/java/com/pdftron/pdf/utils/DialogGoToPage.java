/*     */ package com.pdftron.pdf.utils;
/*     */ 
/*     */ import android.app.AlertDialog;
/*     */ import android.content.Context;
/*     */ import android.content.DialogInterface;
/*     */ import android.view.KeyEvent;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.widget.EditText;
/*     */ import android.widget.FrameLayout;
/*     */ import android.widget.TextView;
/*     */ import androidx.annotation.NonNull;
/*     */ import com.pdftron.pdf.PDFDoc;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.controls.PdfViewCtrlTabFragment;
/*     */ import com.pdftron.pdf.controls.ReflowControl;
/*     */ import com.pdftron.pdf.dialog.pagelabel.PageLabelUtils;
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
/*     */ public class DialogGoToPage
/*     */ {
/*     */   private PdfViewCtrlTabFragment pdfViewCtrlTabFragment;
/*     */   private EditText mEditTextBox;
/*     */   private PDFViewCtrl mPdfViewCtrl;
/*     */   private ReflowControl mReflowControl;
/*     */   private Context mContext;
/*     */   private String mHint;
/*     */   private int mPageCount;
/*     */   private boolean mUsingLabel;
/*     */   
/*     */   public DialogGoToPage(@NonNull PdfViewCtrlTabFragment pdfViewCtrlTabFragment, @NonNull Context context, @NonNull PDFViewCtrl ctrl, ReflowControl reflowControl) {
/*  44 */     this.pdfViewCtrlTabFragment = pdfViewCtrlTabFragment;
/*  45 */     this.mPdfViewCtrl = ctrl;
/*  46 */     this.mContext = context;
/*  47 */     this.mReflowControl = reflowControl;
/*  48 */     this.mPageCount = 0;
/*  49 */     this.mHint = "";
/*  50 */     PDFDoc doc = this.mPdfViewCtrl.getDoc();
/*  51 */     if (doc != null) {
/*  52 */       boolean shouldUnlockRead = false;
/*     */       try {
/*  54 */         doc.lockRead();
/*  55 */         shouldUnlockRead = true;
/*  56 */         this.mPageCount = doc.getPageCount();
/*  57 */         if (this.mPageCount > 0) {
/*  58 */           this.mHint = String.format(pdfViewCtrlTabFragment.getResources().getString(R.string.dialog_gotopage_number), new Object[] { Integer.valueOf(1), Integer.valueOf(this.mPageCount) });
/*  59 */           String firstPage = PageLabelUtils.getPageLabelTitle(this.mPdfViewCtrl, 1);
/*  60 */           String lastPage = PageLabelUtils.getPageLabelTitle(this.mPdfViewCtrl, this.mPageCount);
/*  61 */           if (!Utils.isNullOrEmpty(firstPage) && !Utils.isNullOrEmpty(lastPage)) {
/*  62 */             this.mHint = String.format(pdfViewCtrlTabFragment.getResources().getString(R.string.dialog_gotopage_label), new Object[] { firstPage, lastPage });
/*  63 */             this.mUsingLabel = true;
/*     */           } 
/*     */         } 
/*  66 */       } catch (Exception ex) {
/*  67 */         AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*     */       } finally {
/*  69 */         if (shouldUnlockRead) {
/*  70 */           Utils.unlockReadQuietly(doc);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void show() {
/*  80 */     AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
/*  81 */     builder.setTitle(this.pdfViewCtrlTabFragment.getResources().getString(R.string.dialog_gotopage_title));
/*  82 */     this.mEditTextBox = new EditText(this.mContext);
/*  83 */     if (this.mPageCount > 0) {
/*  84 */       this.mEditTextBox.setHint(this.mHint);
/*     */     }
/*  86 */     if (!this.mUsingLabel) {
/*  87 */       this.mEditTextBox.setInputType(2);
/*     */     }
/*  89 */     this.mEditTextBox.setImeOptions(2);
/*  90 */     this.mEditTextBox.setFocusable(true);
/*  91 */     this.mEditTextBox.setSingleLine();
/*  92 */     ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1, -1);
/*  93 */     this.mEditTextBox.setLayoutParams(layoutParams);
/*  94 */     FrameLayout layout = new FrameLayout(this.mPdfViewCtrl.getContext());
/*  95 */     layout.addView((View)this.mEditTextBox);
/*  96 */     int topPadding = this.mContext.getResources().getDimensionPixelSize(R.dimen.alert_dialog_top_padding);
/*  97 */     int sidePadding = this.mContext.getResources().getDimensionPixelSize(R.dimen.alert_dialog_side_padding);
/*  98 */     layout.setPadding(sidePadding, topPadding, sidePadding, topPadding);
/*  99 */     builder.setView((View)layout);
/*     */     
/* 101 */     builder.setPositiveButton(this.pdfViewCtrlTabFragment.getResources().getString(R.string.ok), new DialogInterface.OnClickListener()
/*     */         {
/*     */           public void onClick(DialogInterface dialog, int which) {}
/*     */         });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 110 */     builder.setNegativeButton(this.pdfViewCtrlTabFragment.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener()
/*     */         {
/*     */           public void onClick(DialogInterface dialog, int which) {}
/*     */         });
/*     */     
/* 115 */     final AlertDialog dialog = builder.create();
/* 116 */     dialog.setOnShowListener(new DialogInterface.OnShowListener()
/*     */         {
/*     */           public void onShow(DialogInterface dialog) {
/* 119 */             DialogGoToPage.this.mEditTextBox.requestFocus();
/* 120 */             Utils.showSoftKeyboard(DialogGoToPage.this.mEditTextBox.getContext(), (View)DialogGoToPage.this.mEditTextBox);
/*     */           }
/*     */         });
/* 123 */     dialog.show();
/* 124 */     dialog.getButton(-1).setOnClickListener(new View.OnClickListener()
/*     */         {
/*     */           public void onClick(View v)
/*     */           {
/* 128 */             DialogGoToPage.this.goToPage(dialog);
/*     */           }
/*     */         });
/*     */     
/* 132 */     this.mEditTextBox.setOnEditorActionListener(new TextView.OnEditorActionListener()
/*     */         {
/*     */           
/*     */           public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
/*     */           {
/* 137 */             if (actionId == 2) {
/* 138 */               DialogGoToPage.this.goToPage(dialog);
/* 139 */               return true;
/*     */             } 
/* 141 */             return false;
/*     */           }
/*     */         });
/* 144 */     if (dialog.getWindow() != null)
/* 145 */       dialog.getWindow().setSoftInputMode(5); 
/*     */   }
/*     */   
/*     */   private void goToPage(AlertDialog dialog) {
/*     */     int i;
/* 150 */     if (this.mPdfViewCtrl == null) {
/*     */       return;
/*     */     }
/*     */     
/* 154 */     String text = this.mEditTextBox.getText().toString();
/*     */     
/*     */     try {
/* 157 */       i = Integer.parseInt(text);
/* 158 */     } catch (NumberFormatException nfe) {
/* 159 */       i = 0;
/*     */     } 
/* 161 */     int pageLabelNum = PageLabelUtils.getPageNumberFromLabel(this.mPdfViewCtrl, text);
/* 162 */     if (pageLabelNum > 0) {
/* 163 */       i = pageLabelNum;
/* 164 */     } else if (i > 0) {
/*     */       
/*     */       try {
/* 167 */         String lastPage = PageLabelUtils.getPageLabelTitle(this.mPdfViewCtrl, this.mPageCount);
/* 168 */         if (!Utils.isNullOrEmpty(lastPage)) {
/* 169 */           int lastPageNum = Integer.parseInt(lastPage);
/* 170 */           if (i > lastPageNum) {
/* 171 */             i = this.mPageCount;
/*     */           }
/*     */         } 
/* 174 */       } catch (NumberFormatException numberFormatException) {}
/*     */     } 
/*     */     
/* 177 */     if (i > 0 && i <= this.mPageCount) {
/* 178 */       this.pdfViewCtrlTabFragment.setCurrentPageHelper(i, true);
/* 179 */       if (this.mReflowControl != null) {
/*     */         try {
/* 181 */           this.mReflowControl.setCurrentPage(i);
/* 182 */         } catch (Exception e) {
/* 183 */           AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */         } 
/*     */       }
/*     */       
/* 187 */       if (null != dialog) {
/* 188 */         dialog.dismiss();
/*     */       }
/*     */     } else {
/* 191 */       this.mEditTextBox.setText("");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pd\\utils\DialogGoToPage.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */