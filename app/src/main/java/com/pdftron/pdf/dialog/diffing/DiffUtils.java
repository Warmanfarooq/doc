/*     */ package com.pdftron.pdf.dialog.diffing;
/*     */ 
/*     */ import android.annotation.TargetApi;
/*     */ import android.app.Activity;
/*     */ import android.content.Context;
/*     */ import android.content.Intent;
/*     */ import android.net.Uri;
/*     */ import androidx.annotation.ColorInt;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import com.pdftron.filters.Filter;
/*     */ import com.pdftron.filters.SecondaryFileFilter;
/*     */ import com.pdftron.pdf.DiffOptions;
/*     */ import com.pdftron.pdf.PDFDoc;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.Page;
/*     */ import com.pdftron.pdf.controls.PdfViewCtrlTabFragment;
/*     */ import com.pdftron.pdf.model.FileInfo;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import com.pdftron.sdf.SDFDoc;
/*     */ import io.reactivex.Single;
/*     */ import io.reactivex.SingleEmitter;
/*     */ import io.reactivex.SingleOnSubscribe;
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
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
/*     */ public class DiffUtils
/*     */ {
/*     */   @TargetApi(19)
/*     */   public static void selectFile(Activity activity) {
/*  41 */     if (!Utils.isKitKat()) {
/*     */       return;
/*     */     }
/*  44 */     Intent intent = createIntent();
/*  45 */     activity.startActivityForResult(intent, 10004);
/*     */   }
/*     */ 
/*     */   
/*     */   @TargetApi(19)
/*     */   public static FileInfo handleActivityResult(Context context, int requestCode, int resultCode, @Nullable Intent data) {
/*  51 */     if (requestCode == 10004 && 
/*  52 */       resultCode == -1 && data != null && data.getData() != null) {
/*  53 */       Uri dataUri = data.getData();
/*  54 */       return getUriInfo(context, dataUri);
/*     */     } 
/*     */ 
/*     */     
/*  58 */     return null;
/*     */   }
/*     */   
/*     */   public static FileInfo getUriInfo(Context context, Uri dataUri) {
/*  62 */     String realPath = Utils.getRealPathFromURI(context, dataUri);
/*  63 */     if (!Utils.isNullOrEmpty(realPath)) {
/*  64 */       File currentFile = new File(realPath);
/*  65 */       if (currentFile.exists()) {
/*  66 */         return new FileInfo(2, currentFile, false, 1);
/*     */       }
/*     */     } 
/*  69 */     String title = Utils.getUriDisplayName(context, dataUri);
/*  70 */     return new FileInfo(13, dataUri.toString(), title, false, 1);
/*     */   }
/*     */   
/*     */   private static File getDefaultDiffFile(@NonNull Context context) {
/*  74 */     String fileName = "pdf-diff.pdf";
/*  75 */     File downloadFolder = Utils.getExternalDownloadDirectory(context);
/*  76 */     File diffFile = new File(downloadFolder, fileName);
/*  77 */     String diffName = Utils.getFileNameNotInUse(diffFile.getAbsolutePath());
/*  78 */     diffFile = new File(diffName);
/*  79 */     return diffFile;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Single<Uri> compareFiles(Context context, ArrayList<Uri> fileUris, @ColorInt int color1, @ColorInt int color2, int blendMode) {
/*  85 */     File diffFile = getDefaultDiffFile(context);
/*  86 */     return compareFiles(context, fileUris, color1, color2, blendMode, diffFile);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Single<Uri> compareFiles(final Context context, final ArrayList<Uri> fileUris, @ColorInt final int color1, @ColorInt final int color2, final int blendMode, final File diffFile) {
/*  92 */     return Single.create(new SingleOnSubscribe<Uri>()
/*     */         {
/*     */           public void subscribe(SingleEmitter<Uri> emitter) throws Exception {
/*  95 */             Uri uri = DiffUtils.compareFilesImpl(context, fileUris, color1, color2, blendMode, diffFile);
/*  96 */             if (uri != null) {
/*  97 */               emitter.onSuccess(uri);
/*     */             } else {
/*  99 */               emitter.tryOnError(new IllegalStateException("Invalid state when comparing files"));
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static Uri compareFilesImpl(Context context, ArrayList<Uri> fileUris, @ColorInt int color1, @ColorInt int color2, int blendMode) {
/* 109 */     File diffFile = getDefaultDiffFile(context);
/* 110 */     return compareFilesImpl(context, fileUris, color1, color2, blendMode, diffFile);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static Uri compareFilesImpl(Context context, ArrayList<Uri> fileUris, @ColorInt int color1, @ColorInt int color2, int blendMode, File diffFile) {
/* 117 */     if (fileUris.size() == 2) {
/* 118 */       Uri firstFile = fileUris.get(0);
/* 119 */       Uri secondFile = fileUris.get(1);
/*     */       
/* 121 */       PDFDoc pdfDoc1 = null;
/* 122 */       PDFDoc pdfDoc2 = null;
/* 123 */       PDFDoc diffDoc = null;
/*     */       
/*     */       try {
/* 126 */         pdfDoc1 = getPdfDoc(context, firstFile);
/* 127 */         pdfDoc2 = getPdfDoc(context, secondFile);
/*     */         
/* 129 */         diffDoc = diff(pdfDoc1, pdfDoc2, color1, color2, blendMode);
/* 130 */         diffDoc.lock();
/* 131 */         diffDoc.save(diffFile.getAbsolutePath(), SDFDoc.SaveMode.REMOVE_UNUSED, null);
/* 132 */         diffDoc.unlock();
/* 133 */         return Uri.fromFile(diffFile);
/* 134 */       } catch (Exception ex) {
/* 135 */         ex.printStackTrace();
/*     */       } finally {
/* 137 */         Utils.closeQuietly(pdfDoc1);
/* 138 */         Utils.closeQuietly(pdfDoc2);
/* 139 */         Utils.closeQuietly(diffDoc);
/*     */       } 
/*     */     } 
/*     */     
/* 143 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void updateDiff(PdfViewCtrlTabFragment pdfViewCtrlTabFragment, ArrayList<Uri> fileUris, @ColorInt int color1, @ColorInt int color2, int blendMode) {
/* 148 */     if (pdfViewCtrlTabFragment == null) {
/*     */       return;
/*     */     }
/*     */     
/* 152 */     PDFViewCtrl pdfViewCtrl = pdfViewCtrlTabFragment.getPDFViewCtrl();
/* 153 */     if (pdfViewCtrl == null) {
/*     */       return;
/*     */     }
/*     */     
/* 157 */     if (fileUris.size() == 2) {
/* 158 */       Uri firstFile = fileUris.get(0);
/* 159 */       Uri secondFile = fileUris.get(1);
/*     */       
/* 161 */       PDFDoc pdfDoc1 = null;
/* 162 */       PDFDoc pdfDoc2 = null;
/* 163 */       PDFDoc diffDoc = null;
/* 164 */       boolean shouldUnlock = false;
/*     */       try {
/* 166 */         pdfViewCtrl.docLock(true);
/* 167 */         shouldUnlock = true;
/*     */         
/* 169 */         PDFDoc currentDoc = pdfViewCtrl.getDoc();
/*     */         
/* 171 */         pdfDoc1 = getPdfDoc(pdfViewCtrl.getContext(), firstFile);
/* 172 */         pdfDoc2 = getPdfDoc(pdfViewCtrl.getContext(), secondFile);
/*     */         
/* 174 */         diffDoc = diff(pdfDoc1, pdfDoc2, color1, color2, blendMode);
/*     */         
/* 176 */         currentDoc.pageRemove(currentDoc.getPageIterator(1));
/* 177 */         currentDoc.insertPages(0, diffDoc, 1, diffDoc.getPageCount(), PDFDoc.InsertBookmarkMode.NONE, null);
/* 178 */       } catch (Exception ex) {
/* 179 */         ex.printStackTrace();
/*     */       } finally {
/* 181 */         Utils.closeQuietly(pdfDoc1);
/* 182 */         Utils.closeQuietly(pdfDoc2);
/* 183 */         Utils.closeQuietly(diffDoc);
/* 184 */         if (shouldUnlock) {
/* 185 */           pdfViewCtrl.docUnlock();
/*     */         }
/*     */         try {
/* 188 */           pdfViewCtrl.updatePageLayout();
/* 189 */         } catch (Exception ex) {
/* 190 */           ex.printStackTrace();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static PDFDoc diff(PDFDoc pdfDoc1, PDFDoc pdfDoc2, @ColorInt int color1, @ColorInt int color2, int blendMode) throws Exception {
/* 198 */     int count1 = pdfDoc1.getPageCount();
/* 199 */     int count2 = pdfDoc2.getPageCount();
/*     */     
/* 201 */     PDFDoc outDoc = new PDFDoc();
/* 202 */     DiffOptions diffOptions = new DiffOptions();
/* 203 */     diffOptions.setColorA(Utils.color2ColorPt(color1));
/* 204 */     diffOptions.setColorB(Utils.color2ColorPt(color2));
/* 205 */     diffOptions.setBlendMode(blendMode);
/*     */     
/* 207 */     for (int i = 1; i <= Math.max(count1, count2); i++) {
/* 208 */       Page page1 = pdfDoc1.getPage(i);
/* 209 */       Page page2 = pdfDoc2.getPage(i);
/*     */       
/* 211 */       outDoc.appendVisualDiff(page1, page2, diffOptions);
/*     */     } 
/*     */     
/* 214 */     return outDoc;
/*     */   }
/*     */   
/*     */   private static PDFDoc getPdfDoc(Context context, Uri fileUri) throws Exception {
/* 218 */     String realPath = Utils.getRealPathFromURI(context, fileUri);
/* 219 */     if (!Utils.isNullOrEmpty(realPath)) {
/* 220 */       File currentFile = new File(realPath);
/* 221 */       if (currentFile.exists()) {
/* 222 */         return new PDFDoc(currentFile.getAbsolutePath());
/*     */       }
/*     */     } 
/* 225 */     SecondaryFileFilter fileFilter = new SecondaryFileFilter(context, fileUri);
/* 226 */     return new PDFDoc((Filter)fileFilter);
/*     */   }
/*     */   
/*     */   @TargetApi(19)
/*     */   private static Intent createIntent() {
/* 231 */     Intent intent = new Intent("android.intent.action.OPEN_DOCUMENT");
/* 232 */     intent.setAction("android.intent.action.OPEN_DOCUMENT");
/* 233 */     intent.addCategory("android.intent.category.OPENABLE");
/* 234 */     intent.setType("application/pdf");
/* 235 */     intent.putExtra("android.content.extra.SHOW_ADVANCED", true);
/*     */     
/* 237 */     intent.putExtra("android.provider.extra.SHOW_ADVANCED", true);
/* 238 */     return intent;
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\dialog\diffing\DiffUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */