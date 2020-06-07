/*     */ package com.pdftron.pdf.utils;
/*     */ 
/*     */ import android.annotation.TargetApi;
/*     */ import android.content.Context;
/*     */ import android.net.Uri;
/*     */ import android.print.PdfPrint;
/*     */ import android.print.PrintAttributes;
/*     */ import android.print.PrintDocumentAdapter;
/*     */ import android.webkit.URLUtil;
/*     */ import android.webkit.WebView;
/*     */ import android.webkit.WebViewClient;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import java.io.File;
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
/*     */ 
/*     */ @TargetApi(19)
/*     */ public class HTML2PDF
/*     */ {
/*     */   private static final boolean sDebug = false;
/*  49 */   public static final String TAG = HTML2PDF.class.getName();
/*     */ 
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   private Uri mOutputFolderUri;
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   private WebView mWebView;
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private HTML2PDFListener mListener;
/*     */ 
/*     */   
/*     */   private static final String DEFAULT_FILE_NAME = "untitled.pdf";
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOutputFolder(File outputFolder) {
/*  70 */     this.mOutputFolderUri = Uri.fromFile(outputFolder);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOutputFolder(Uri outputFolder) {
/*  79 */     this.mOutputFolderUri = outputFolder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOutputFileName(String fileName) {
/*  88 */     this.mOutputFileName = fileName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHorizontalDpi(int horizontalDpi) {
/*  97 */     this.mHorizontalDpi = horizontalDpi;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVerticalDpi(int verticalDpi) {
/* 106 */     this.mVerticalDpi = verticalDpi;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMargins(PrintAttributes.Margins margins) {
/* 115 */     this.mMargins = margins;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMediaSize(PrintAttributes.MediaSize mediaSize) {
/* 124 */     this.mMediaSize = mediaSize;
/*     */   }
/*     */ 
/*     */   
/* 128 */   private String mOutputFileName = "untitled.pdf";
/* 129 */   private int mHorizontalDpi = 600;
/* 130 */   private int mVerticalDpi = 600;
/* 131 */   private PrintAttributes.Margins mMargins = PrintAttributes.Margins.NO_MARGINS;
/* 132 */   private PrintAttributes.MediaSize mMediaSize = PrintAttributes.MediaSize.NA_LETTER;
/*     */   
/*     */   public void setHTML2PDFListener(HTML2PDFListener listener) {
/* 135 */     this.mListener = listener;
/*     */   }
/*     */   
/*     */   public HTML2PDF(@NonNull Context context) {
/* 139 */     this(new WebView(context), Uri.fromFile(Utils.getExternalDownloadDirectory(context)));
/*     */   }
/*     */   
/*     */   public HTML2PDF(@NonNull WebView webView) {
/* 143 */     this(webView, Uri.fromFile(Utils.getExternalDownloadDirectory(webView.getContext())));
/*     */   }
/*     */   
/*     */   public HTML2PDF(@NonNull WebView webView, @NonNull Uri outputFolderUri) {
/* 147 */     this.mWebView = webView;
/* 148 */     this.mOutputFolderUri = outputFolderUri;
/*     */   }
/*     */   
/*     */   public void doHtml2Pdf() {
/* 152 */     this.mWebView.setWebViewClient(new WebViewClient()
/*     */         {
/*     */           public boolean shouldOverrideUrlLoading(WebView view, String url) {
/* 155 */             return false;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onPageFinished(WebView view, String url) {
/* 164 */             HTML2PDF.this.mOutputFileName = 
/* 165 */               Utils.getValidFilename(
/* 166 */                 HTML2PDF.this.mOutputFileName.equals("untitled.pdf") ? view
/* 167 */                 .getTitle() : HTML2PDF.this.mOutputFileName);
/*     */             
/* 169 */             HTML2PDF.this.createWebPrintJob();
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @TargetApi(19)
/*     */   public static void fromUrl(Context context, String url, HTML2PDFListener listener) {
/* 183 */     HTML2PDF html2PDF = new HTML2PDF(context);
/* 184 */     html2PDF.setHTML2PDFListener(listener);
/* 185 */     html2PDF.fromUrl(url);
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
/*     */   @TargetApi(19)
/*     */   public static void fromUrl(@NonNull Context context, @NonNull String url, @NonNull File folder, @Nullable HTML2PDFListener listener) {
/* 198 */     HTML2PDF html2PDF = new HTML2PDF(context);
/* 199 */     html2PDF.setOutputFolder(folder);
/* 200 */     html2PDF.setHTML2PDFListener(listener);
/* 201 */     html2PDF.fromUrl(url);
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
/*     */   @TargetApi(19)
/*     */   public static void fromUrl(@NonNull Context context, @NonNull String url, @NonNull Uri folder, @Nullable HTML2PDFListener listener) {
/* 214 */     HTML2PDF html2PDF = new HTML2PDF(context);
/* 215 */     html2PDF.setOutputFolder(folder);
/* 216 */     html2PDF.setHTML2PDFListener(listener);
/* 217 */     html2PDF.fromUrl(url);
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
/*     */   
/*     */   @TargetApi(19)
/*     */   public static void fromUrl(@NonNull Context context, @NonNull String url, @NonNull Uri folder, @NonNull String outputFileName, @Nullable HTML2PDFListener listener) {
/* 231 */     HTML2PDF html2PDF = new HTML2PDF(context);
/* 232 */     html2PDF.setOutputFolder(folder);
/* 233 */     html2PDF.setHTML2PDFListener(listener);
/* 234 */     html2PDF.setOutputFileName(outputFileName);
/* 235 */     html2PDF.fromUrl(url);
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
/*     */   @TargetApi(19)
/*     */   public static void fromHTMLDocument(@NonNull Context context, @Nullable String baseUrl, @NonNull String htmlDocument, @Nullable HTML2PDFListener listener) {
/* 248 */     HTML2PDF html2PDF = new HTML2PDF(context);
/* 249 */     html2PDF.setHTML2PDFListener(listener);
/* 250 */     html2PDF.fromHTMLDocument(baseUrl, htmlDocument);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @TargetApi(19)
/*     */   public static void fromWebView(@NonNull WebView webView, @Nullable HTML2PDFListener listener) {
/* 261 */     HTML2PDF html2PDF = new HTML2PDF(webView);
/* 262 */     html2PDF.setHTML2PDFListener(listener);
/* 263 */     html2PDF.doHtml2Pdf();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fromUrl(String url) {
/* 272 */     this.mWebView.loadUrl(url);
/* 273 */     doHtml2Pdf();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fromHTMLDocument(String baseUrl, String htmlDocument) {
/* 283 */     this.mWebView.loadDataWithBaseURL(baseUrl, htmlDocument, "text/HTML", "UTF-8", null);
/* 284 */     doHtml2Pdf();
/*     */   }
/*     */   
/*     */   private void createWebPrintJob() {
/* 288 */     Context context = this.mWebView.getContext();
/*     */     
/* 290 */     PrintDocumentAdapter printAdapter = null;
/* 291 */     if (Utils.isLollipop()) {
/* 292 */       String jobName = context.getString(R.string.app_name) + " Document";
/* 293 */       printAdapter = this.mWebView.createPrintDocumentAdapter(jobName);
/* 294 */     } else if (Utils.isKitKat()) {
/* 295 */       printAdapter = this.mWebView.createPrintDocumentAdapter();
/*     */     } else {
/* 297 */       throw new RuntimeException("Android 19 (KitKat) is required to use HTML2PDF");
/*     */     } 
/*     */     
/* 300 */     if (printAdapter != null) {
/* 301 */       if ("content".equals(this.mOutputFolderUri.getScheme())) {
/*     */         
/* 303 */         PdfPrint pdfPrint = setupPdfPrint(false);
/* 304 */         pdfPrint.print(context, printAdapter, this.mOutputFolderUri, this.mOutputFileName);
/* 305 */       } else if (URLUtil.isHttpUrl(this.mOutputFolderUri.toString()) || URLUtil.isHttpsUrl(this.mOutputFolderUri.toString())) {
/*     */         
/* 307 */         if (this.mListener != null) {
/* 308 */           this.mListener.onConversionFailed(null);
/*     */         }
/*     */       } else {
/*     */         
/* 312 */         PdfPrint pdfPrint = setupPdfPrint(true);
/* 313 */         String outputPath = this.mOutputFolderUri.getPath();
/* 314 */         if (outputPath != null) {
/* 315 */           pdfPrint.print(printAdapter, new File(this.mOutputFolderUri.getPath()), this.mOutputFileName);
/*     */         }
/* 317 */         else if (this.mListener != null) {
/* 318 */           this.mListener.onConversionFailed(null);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PdfPrint setupPdfPrint(final boolean isLocal) {
/* 329 */     PrintAttributes attributes = (new PrintAttributes.Builder()).setMediaSize(this.mMediaSize).setResolution(new PrintAttributes.Resolution("pdf", "pdf", this.mHorizontalDpi, this.mVerticalDpi)).setMinMargins(this.mMargins).build();
/*     */     
/* 331 */     PdfPrint pdfPrint = new PdfPrint(attributes);
/* 332 */     pdfPrint.setPdfPrintListener(new PdfPrint.PdfPrintListener()
/*     */         {
/*     */ 
/*     */           
/*     */           public void onWriteFinished(String output)
/*     */           {
/* 338 */             if (HTML2PDF.this.mListener != null) {
/* 339 */               HTML2PDF.this.mListener.onConversionFinished(output, isLocal);
/*     */             }
/*     */           }
/*     */ 
/*     */           
/*     */           public void onError(String error) {
/* 345 */             if (HTML2PDF.this.mListener != null) {
/* 346 */               HTML2PDF.this.mListener.onConversionFailed(error);
/*     */             }
/*     */           }
/*     */         });
/* 350 */     return pdfPrint;
/*     */   }
/*     */   
/*     */   public static interface HTML2PDFListener {
/*     */     void onConversionFinished(String param1String, boolean param1Boolean);
/*     */     
/*     */     void onConversionFailed(@Nullable String param1String);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pd\\utils\HTML2PDF.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */