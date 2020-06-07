/*     */ package android.print;
/*     */ 
/*     */ import android.annotation.TargetApi;
/*     */ import android.content.ContentResolver;
/*     */ import android.content.Context;
/*     */ import android.net.Uri;
/*     */ import android.os.CancellationSignal;
/*     */ import android.os.ParcelFileDescriptor;
/*     */ import androidx.annotation.Nullable;
/*     */ import com.pdftron.pdf.model.ExternalFileInfo;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import org.apache.commons.io.FilenameUtils;
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
/*     */ public class PdfPrint
/*     */ {
/*     */   private final PrintAttributes printAttributes;
/*     */   private PdfPrintListener mListener;
/*     */   
/*     */   public void setPdfPrintListener(PdfPrintListener listener) {
/*  39 */     this.mListener = listener;
/*     */   }
/*     */   
/*     */   public PdfPrint(PrintAttributes printAttributes) {
/*  43 */     this.printAttributes = printAttributes;
/*     */   }
/*     */ 
/*     */   
/*     */   public void print(PrintDocumentAdapter printAdapter, File outputFolder, String fileName) {
/*  48 */     boolean folderExists = (outputFolder.exists() || outputFolder.mkdirs());
/*     */     
/*  50 */     String outputPath = Utils.getFileNameNotInUse((new File(outputFolder, FilenameUtils.removeExtension(fileName) + ".pdf")).getAbsolutePath());
/*     */     
/*  52 */     ParcelFileDescriptor fileDesc = folderExists ? getOutputFile(new File(outputPath)) : null;
/*     */     
/*  54 */     print(printAdapter, new File(outputPath), (ExternalFileInfo)null, fileDesc);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void print(Context context, PrintDocumentAdapter printAdapter, Uri outputFolder, String fileName) {
/*  60 */     ExternalFileInfo externalFile = new ExternalFileInfo(context, null, outputFolder);
/*  61 */     String outputPath = Utils.getFileNameNotInUse(externalFile, fileName);
/*  62 */     ExternalFileInfo outputFile = externalFile.createFile("application/pdf", outputPath);
/*     */     
/*  64 */     if (outputFile != null) {
/*  65 */       ParcelFileDescriptor fileDesc = getOutputUriFile(context, outputFile.getUri());
/*     */       
/*  67 */       print(printAdapter, (File)null, outputFile, fileDesc);
/*     */     } else {
/*  69 */       this.mListener.onError(null);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void print(final PrintDocumentAdapter printAdapter, final File localFile, final ExternalFileInfo externalFile, final ParcelFileDescriptor fileDescriptor) {
/*  78 */     String inputPath = null;
/*  79 */     if (localFile != null) {
/*  80 */       inputPath = localFile.getAbsolutePath();
/*  81 */     } else if (externalFile != null) {
/*  82 */       inputPath = externalFile.getUri().toString();
/*     */     } 
/*  84 */     if (null == inputPath) {
/*     */       return;
/*     */     }
/*  87 */     final String outputFilePath = inputPath;
/*     */     
/*  89 */     final PrintDocumentAdapter.WriteResultCallback wc = new PrintDocumentAdapter.WriteResultCallback()
/*     */       {
/*     */         private void handleError() {
/*  92 */           if (localFile != null) {
/*  93 */             localFile.delete();
/*     */           } else {
/*  95 */             externalFile.delete();
/*     */           } 
/*     */         }
/*     */         
/*     */         private void closeFileDescriptor() {
/* 100 */           if (fileDescriptor != null) {
/*     */             try {
/* 102 */               fileDescriptor.close();
/* 103 */             } catch (IOException e) {
/* 104 */               AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */             } 
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public void onWriteFinished(PageRange[] pages) {
/* 111 */           closeFileDescriptor();
/* 112 */           super.onWriteFinished(pages);
/* 113 */           if (PdfPrint.this.mListener != null) {
/* 114 */             PdfPrint.this.mListener.onWriteFinished(outputFilePath);
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public void onWriteFailed(CharSequence error) {
/* 120 */           closeFileDescriptor();
/* 121 */           handleError();
/* 122 */           super.onWriteFailed(error);
/* 123 */           if (PdfPrint.this.mListener != null) {
/* 124 */             PdfPrint.this.mListener.onError((error != null) ? error.toString() : null);
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public void onWriteCancelled() {
/* 130 */           closeFileDescriptor();
/* 131 */           handleError();
/* 132 */           super.onWriteCancelled();
/* 133 */           if (PdfPrint.this.mListener != null) {
/* 134 */             PdfPrint.this.mListener.onError(null);
/*     */           }
/*     */         }
/*     */       };
/*     */     
/* 139 */     PrintDocumentAdapter.LayoutResultCallback lc = new PrintDocumentAdapter.LayoutResultCallback()
/*     */       {
/*     */         public void onLayoutCancelled()
/*     */         {
/* 143 */           super.onLayoutCancelled();
/* 144 */           if (PdfPrint.this.mListener != null) {
/* 145 */             PdfPrint.this.mListener.onError(null);
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public void onLayoutFailed(CharSequence error) {
/* 151 */           super.onLayoutFailed(error);
/* 152 */           if (PdfPrint.this.mListener != null) {
/* 153 */             PdfPrint.this.mListener.onError((error != null) ? error.toString() : null);
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public void onLayoutFinished(PrintDocumentInfo info, boolean changed) {
/* 159 */           if (null == fileDescriptor) {
/* 160 */             if (PdfPrint.this.mListener != null) {
/* 161 */               PdfPrint.this.mListener.onError(null);
/*     */             }
/*     */             return;
/*     */           } 
/* 165 */           printAdapter.onWrite(new PageRange[] { PageRange.ALL_PAGES }, fileDescriptor, new CancellationSignal(), wc);
/*     */         }
/*     */       };
/*     */     
/* 169 */     printAdapter.onLayout(null, this.printAttributes, null, lc, null);
/*     */   }
/*     */   
/*     */   private ParcelFileDescriptor getOutputUriFile(Context context, Uri filePath) {
/*     */     try {
/* 174 */       ContentResolver cr = Utils.getContentResolver(context);
/* 175 */       if (cr == null) {
/* 176 */         return null;
/*     */       }
/* 178 */       return cr.openFileDescriptor(filePath, "rw");
/* 179 */     } catch (FileNotFoundException e) {
/* 180 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */       
/* 182 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private ParcelFileDescriptor getOutputFile(File outputPath) {
/*     */     try {
/* 188 */       boolean success = outputPath.createNewFile();
/* 189 */       if (success) {
/* 190 */         return ParcelFileDescriptor.open(outputPath, 805306368);
/*     */       }
/* 192 */     } catch (Exception e) {
/* 193 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */     } 
/* 195 */     return null;
/*     */   }
/*     */   
/*     */   public static interface PdfPrintListener {
/*     */     void onWriteFinished(String param1String);
/*     */     
/*     */     void onError(@Nullable String param1String);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\android\print\PdfPrint.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */