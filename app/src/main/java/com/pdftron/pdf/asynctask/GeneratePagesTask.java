/*     */ package com.pdftron.pdf.asynctask;
/*     */ 
/*     */ import android.app.ProgressDialog;
/*     */ import android.content.Context;
/*     */ import android.content.DialogInterface;
/*     */ import android.os.AsyncTask;
/*     */ import android.os.Handler;
/*     */ import androidx.annotation.NonNull;
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.PDFDoc;
/*     */ import com.pdftron.pdf.PDFDocGenerator;
/*     */ import com.pdftron.pdf.Page;
/*     */ import com.pdftron.pdf.controls.AddPageDialogFragment;
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
/*     */ public class GeneratePagesTask
/*     */   extends AsyncTask<Void, Void, Boolean>
/*     */ {
/*     */   private static final double LINE_SHADE = 0.35D;
/*     */   private static final double LINE_SHADE_BLUEPRINT = 0.85D;
/*     */   private static final double MARGIN_RED = 1.0D;
/*     */   private static final double MARGIN_GREEN = 0.5D;
/*     */   private static final double MARGIN_BLUE = 0.5D;
/*     */   private AddPageDialogFragment.PageSize mPageSize;
/*     */   private AddPageDialogFragment.PageOrientation mPageOrientation;
/*     */   private AddPageDialogFragment.PageColor mPageColor;
/*     */   private AddPageDialogFragment.PageType mPageType;
/*     */   private double mPageWidth;
/*     */   private double mPageHeight;
/*     */   private boolean mShouldCreateNewPdf;
/*     */   private int mNumOfPages;
/*     */   private String mTitle;
/*     */   private PDFDoc mFinalDoc;
/*     */   private Page[] mPages;
/*     */   private AddPageDialogFragment.OnAddNewPagesListener mOnAddNewPagesListener;
/*     */   private AddPageDialogFragment.OnCreateNewDocumentListener mOnCreateNewDocumentListener;
/*     */   private Handler mProgressDialogHandler;
/*     */   private ProgressDialog mProgressDialog;
/*     */   
/*  51 */   private Runnable mProgressDialogExecutor = new Runnable()
/*     */     {
/*     */       public void run() {
/*  54 */         GeneratePagesTask.this.mProgressDialog.show();
/*     */       }
/*     */     };
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GeneratePagesTask(@NonNull Context context, int numOfPages, String title, AddPageDialogFragment.PageSize pageSize, AddPageDialogFragment.PageOrientation pageOrientation, AddPageDialogFragment.PageColor pageColor, AddPageDialogFragment.PageType pageType, double pageWidth, double pageHeight, boolean shouldCreateNewPdf, AddPageDialogFragment.OnCreateNewDocumentListener onCreateNewDocumentListener, AddPageDialogFragment.OnAddNewPagesListener onAddNewPagesListener) {
/*  86 */     this.mNumOfPages = numOfPages;
/*  87 */     this.mTitle = title;
/*  88 */     this.mOnCreateNewDocumentListener = onCreateNewDocumentListener;
/*  89 */     this.mOnAddNewPagesListener = onAddNewPagesListener;
/*  90 */     this.mPageSize = pageSize;
/*  91 */     this.mPageColor = pageColor;
/*  92 */     this.mPageType = pageType;
/*  93 */     this.mPageWidth = pageWidth;
/*  94 */     this.mPageHeight = pageHeight;
/*  95 */     this.mShouldCreateNewPdf = shouldCreateNewPdf;
/*  96 */     this.mPageOrientation = pageOrientation;
/*  97 */     this.mPages = new Page[numOfPages];
/*     */     
/*  99 */     this.mProgressDialogHandler = new Handler();
/* 100 */     this.mProgressDialog = new ProgressDialog(context);
/* 101 */     this.mProgressDialog.setIndeterminate(true);
/* 102 */     this.mProgressDialog.setMessage(context.getString(R.string.tools_misc_please_wait));
/* 103 */     this.mProgressDialog.setProgressStyle(0);
/* 104 */     this.mProgressDialog.setCancelable(true);
/* 105 */     this.mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener()
/*     */         {
/*     */           public void onCancel(DialogInterface dialog) {
/* 108 */             GeneratePagesTask.this.cancel(true);
/* 109 */             dialog.dismiss();
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onPreExecute() {
/* 119 */     this.mProgressDialogHandler.postDelayed(this.mProgressDialogExecutor, 790L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Boolean doInBackground(Void... params) {
/* 127 */     PDFDoc doc = null;
/* 128 */     Page page = null;
/*     */     
/* 130 */     double width = 8.5D;
/* 131 */     double height = 11.0D;
/*     */     
/* 133 */     if (this.mPageSize == AddPageDialogFragment.PageSize.Legal) {
/* 134 */       height = 14.0D;
/* 135 */     } else if (this.mPageSize == AddPageDialogFragment.PageSize.Ledger) {
/* 136 */       width = 11.0D;
/* 137 */       height = 17.0D;
/* 138 */     } else if (this.mPageSize == AddPageDialogFragment.PageSize.A3) {
/* 139 */       width = 11.69D;
/* 140 */       height = 16.53D;
/* 141 */     } else if (this.mPageSize == AddPageDialogFragment.PageSize.A4) {
/* 142 */       width = 8.27D;
/* 143 */       height = 11.69D;
/* 144 */     } else if (this.mPageSize == AddPageDialogFragment.PageSize.Custom) {
/* 145 */       width = this.mPageWidth;
/* 146 */       height = this.mPageHeight;
/*     */     } 
/*     */     
/* 149 */     if ((this.mPageOrientation == AddPageDialogFragment.PageOrientation.Portrait && width > height) || (this.mPageOrientation == AddPageDialogFragment.PageOrientation.Landscape && height > width)) {
/*     */       
/* 151 */       double temp = width;
/*     */       
/* 153 */       width = height;
/* 154 */       height = temp;
/*     */     } 
/*     */     
/*     */     try {
/* 158 */       if (isCancelled()) {
/* 159 */         return Boolean.valueOf(false);
/*     */       }
/* 161 */       if (this.mShouldCreateNewPdf) {
/* 162 */         this.mFinalDoc = new PDFDoc();
/*     */       }
/* 164 */       double lineShade = (this.mPageColor == AddPageDialogFragment.PageColor.Blueprint) ? 0.85D : 0.35D;
/* 165 */       double bgR = ((AddPageDialogFragment.PageColorValues[this.mPageColor.ordinal()] & 0xFF0000) >> 16) / 255.0D;
/* 166 */       double bgG = ((AddPageDialogFragment.PageColorValues[this.mPageColor.ordinal()] & 0xFF00) >> 8) / 255.0D;
/* 167 */       double bgB = (AddPageDialogFragment.PageColorValues[this.mPageColor.ordinal()] & 0xFF) / 255.0D;
/* 168 */       if (this.mPageType == AddPageDialogFragment.PageType.Grid) {
/* 169 */         doc = PDFDocGenerator.generateGridPaperDoc(width, height, 0.25D, 0.45D, lineShade, lineShade, lineShade, bgR, bgG, bgB);
/* 170 */       } else if (this.mPageType == AddPageDialogFragment.PageType.Graph) {
/* 171 */         doc = PDFDocGenerator.generateGraphPaperDoc(width, height, 0.25D, 0.45D, 1.7D, 5, lineShade, lineShade, lineShade, bgR, bgG, bgB);
/* 172 */       } else if (this.mPageType == AddPageDialogFragment.PageType.Music) {
/* 173 */         doc = PDFDocGenerator.generateMusicPaperDoc(width, height, 0.5D, 10, 6.5D, 0.25D, lineShade, lineShade, lineShade, bgR, bgG, bgB);
/* 174 */       } else if (this.mPageType == AddPageDialogFragment.PageType.Lined) {
/* 175 */         double leftMarginR = (this.mPageColor == AddPageDialogFragment.PageColor.White) ? 1.0D : (((this.mPageColor == AddPageDialogFragment.PageColor.Blueprint) ? 0.85D : 0.35D) * 0.7D);
/*     */         
/* 177 */         double leftMarginG = (this.mPageColor == AddPageDialogFragment.PageColor.White) ? 0.5D : (((this.mPageColor == AddPageDialogFragment.PageColor.Blueprint) ? 0.85D : 0.35D) * 0.7D);
/*     */         
/* 179 */         double leftMarginB = (this.mPageColor == AddPageDialogFragment.PageColor.White) ? 0.5D : (((this.mPageColor == AddPageDialogFragment.PageColor.Blueprint) ? 0.85D : 0.35D) * 0.7D);
/*     */ 
/*     */         
/* 182 */         double rightMarginR = (this.mPageColor == AddPageDialogFragment.PageColor.White) ? 1.0D : (((this.mPageColor == AddPageDialogFragment.PageColor.Blueprint) ? 0.85D : 0.35D) * 0.45D);
/*     */         
/* 184 */         double rightMarginG = (this.mPageColor == AddPageDialogFragment.PageColor.White) ? 0.8D : (((this.mPageColor == AddPageDialogFragment.PageColor.Blueprint) ? 0.85D : 0.35D) * 0.45D);
/*     */         
/* 186 */         double rightMarginB = (this.mPageColor == AddPageDialogFragment.PageColor.White) ? 0.8D : (((this.mPageColor == AddPageDialogFragment.PageColor.Blueprint) ? 0.85D : 0.35D) * 0.45D);
/*     */ 
/*     */         
/* 189 */         doc = PDFDocGenerator.generateLinedPaperDoc(width, height, 0.25D, 0.45D, lineShade, lineShade, lineShade, 1.2D, leftMarginR, leftMarginG, leftMarginB, rightMarginR, rightMarginG, rightMarginB, bgR, bgG, bgB, 0.85D, 0.35D);
/*     */       }
/* 191 */       else if (this.mPageType == AddPageDialogFragment.PageType.Blank) {
/* 192 */         doc = PDFDocGenerator.generateBlankPaperDoc(width, height, bgR, bgG, bgB);
/* 193 */       } else if (this.mPageType == AddPageDialogFragment.PageType.Dotted) {
/* 194 */         doc = PDFDocGenerator.generateDottedPaperDoc(width, height, 0.25D, 2.0D, lineShade, lineShade, lineShade, bgR, bgG, bgB);
/* 195 */       } else if (this.mPageType == AddPageDialogFragment.PageType.IsometricDotted) {
/* 196 */         doc = PDFDocGenerator.generateIsometricDottedPaperDoc(width, height, 0.25D, 2.0D, lineShade, lineShade, lineShade, bgR, bgG, bgB);
/*     */       } 
/*     */       
/* 199 */       for (int i = 0; i < this.mNumOfPages; i++) {
/* 200 */         if (page == null && doc != null) {
/* 201 */           page = doc.getPage(1);
/*     */         }
/* 203 */         if (!this.mShouldCreateNewPdf) {
/* 204 */           this.mPages[i] = page;
/*     */         } else {
/* 206 */           this.mFinalDoc.pagePushBack(page);
/*     */         }
/*     */       
/*     */       } 
/* 210 */     } catch (PDFNetException e) {
/* 211 */       return Boolean.valueOf(false);
/*     */     } 
/*     */     
/* 214 */     return Boolean.valueOf(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onPostExecute(Boolean pass) {
/* 222 */     super.onPostExecute(pass);
/*     */     
/* 224 */     if (this.mProgressDialogHandler != null) {
/* 225 */       this.mProgressDialogHandler.removeCallbacks(this.mProgressDialogExecutor);
/*     */     }
/* 227 */     if (this.mProgressDialog != null && this.mProgressDialog.isShowing()) {
/* 228 */       this.mProgressDialog.dismiss();
/*     */     }
/*     */     
/* 231 */     if (!pass.booleanValue()) {
/*     */       return;
/*     */     }
/*     */     
/* 235 */     if (this.mShouldCreateNewPdf && this.mOnCreateNewDocumentListener != null) {
/* 236 */       this.mOnCreateNewDocumentListener.onCreateNewDocument(this.mFinalDoc, this.mTitle);
/*     */     }
/* 238 */     if (!this.mShouldCreateNewPdf && this.mOnAddNewPagesListener != null)
/* 239 */       this.mOnAddNewPagesListener.onAddNewPages(this.mPages); 
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\asynctask\GeneratePagesTask.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */