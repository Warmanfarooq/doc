/*     */ package com.pdftron.pdf.utils;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.graphics.Bitmap;
/*     */ import android.graphics.BitmapFactory;
/*     */ import android.graphics.Color;
/*     */ import android.graphics.PointF;
/*     */ import android.graphics.RectF;
/*     */ import android.net.Uri;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.filters.Filter;
/*     */ import com.pdftron.filters.SecondaryFileFilter;
/*     */ import com.pdftron.pdf.Annot;
/*     */ import com.pdftron.pdf.ColorPt;
/*     */ import com.pdftron.pdf.ColorSpace;
/*     */ import com.pdftron.pdf.ConversionOptions;
/*     */ import com.pdftron.pdf.Convert;
/*     */ import com.pdftron.pdf.DocumentConversion;
/*     */ import com.pdftron.pdf.Element;
/*     */ import com.pdftron.pdf.ElementBuilder;
/*     */ import com.pdftron.pdf.ElementWriter;
/*     */ import com.pdftron.pdf.GState;
/*     */ import com.pdftron.pdf.OfficeToPDFOptions;
/*     */ import com.pdftron.pdf.PDFDoc;
/*     */ import com.pdftron.pdf.PDFDraw;
/*     */ import com.pdftron.pdf.Page;
/*     */ import com.pdftron.pdf.Point;
/*     */ import com.pdftron.pdf.Rect;
/*     */ import com.pdftron.pdf.annots.Ink;
/*     */ import com.pdftron.sdf.Doc;
/*     */ import com.pdftron.sdf.SDFDoc;
/*     */ import io.reactivex.Single;
/*     */ import io.reactivex.SingleEmitter;
/*     */ import io.reactivex.SingleOnSubscribe;
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import org.apache.commons.io.FileUtils;
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
/*     */ public class StampManager
/*     */ {
/*     */   private StampManager() {}
/*     */   
/*     */   private static class LazyHolder
/*     */   {
/*  60 */     private static final StampManager INSTANCE = new StampManager();
/*     */   }
/*     */   
/*     */   public static StampManager getInstance() {
/*  64 */     return LazyHolder.INSTANCE;
/*     */   }
/*     */   
/*  67 */   private static String SIGNATURE_FILE_NAME_LEGACY = "SignatureFile.CompleteReader";
/*  68 */   private static String SIGNATURE_FILE_NAME = "_pdftron_Signature.pdf";
/*  69 */   private static String SIGNATURE_FOLDER_NAME = "_pdftron_Signature";
/*  70 */   private static String SIGNATURE_JPG_FOLDER_NAME = "_pdftron_SignatureJPG";
/*     */   
/*  72 */   private static int PAGE_BUFFER = 20;
/*     */   
/*     */   private String mDefaultSignatureFilename;
/*     */   
/*     */   private PDFDoc getStampDoc(File file) {
/*  77 */     PDFDoc pdfDoc = null;
/*     */     try {
/*  79 */       if (file.exists()) {
/*  80 */         pdfDoc = new PDFDoc(file.getAbsolutePath());
/*     */       } else {
/*  82 */         pdfDoc = new PDFDoc();
/*     */       } 
/*  84 */     } catch (PDFNetException pDFNetException) {}
/*     */     
/*  86 */     return pdfDoc;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   private File getStampFile(Context context) {
/*  91 */     if (this.mDefaultSignatureFilename == null) {
/*  92 */       return new File(context.getFilesDir().getAbsolutePath() + "/" + SIGNATURE_FILE_NAME_LEGACY);
/*     */     }
/*  94 */     return new File(this.mDefaultSignatureFilename);
/*     */   }
/*     */   
/*     */   public File getSavedSignatureJpgFolder(Context context) {
/*  98 */     File signatureFolder = new File(context.getFilesDir().getAbsolutePath() + "/" + SIGNATURE_JPG_FOLDER_NAME);
/*  99 */     if (!signatureFolder.exists()) {
/* 100 */       boolean success = signatureFolder.mkdir();
/* 101 */       if (!success) {
/* 102 */         return null;
/*     */       }
/*     */     } 
/* 105 */     if (!signatureFolder.isDirectory()) {
/* 106 */       return null;
/*     */     }
/* 108 */     return signatureFolder;
/*     */   }
/*     */   
/*     */   public File getSavedSignatureFolder(Context context) {
/* 112 */     File signatureFolder = new File(context.getFilesDir().getAbsolutePath() + "/" + SIGNATURE_FOLDER_NAME);
/* 113 */     if (!signatureFolder.exists()) {
/* 114 */       boolean success = signatureFolder.mkdir();
/* 115 */       if (!success) {
/* 116 */         return null;
/*     */       }
/*     */     } 
/* 119 */     if (!signatureFolder.isDirectory()) {
/* 120 */       return null;
/*     */     }
/* 122 */     return signatureFolder;
/*     */   }
/*     */   
/*     */   public File[] getSavedSignatures(Context context) {
/* 126 */     File signatureFolder = getSavedSignatureFolder(context);
/* 127 */     if (null == signatureFolder || context == null) {
/* 128 */       return null;
/*     */     }
/*     */     
/* 131 */     File[] files = signatureFolder.listFiles();
/* 132 */     if (files == null || files.length == 0) {
/* 133 */       importDefaultSignature(context, signatureFolder);
/*     */     }
/*     */     
/* 136 */     return signatureFolder.listFiles();
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean importDefaultSignature(@NonNull Context context, File signatureFolder) {
/* 141 */     File src = getStampFile(context);
/* 142 */     File dest = new File(signatureFolder, SIGNATURE_FILE_NAME);
/*     */     try {
/* 144 */       if (src.exists()) {
/* 145 */         FileUtils.copyFile(src, dest);
/* 146 */         return true;
/*     */       } 
/* 148 */     } catch (Exception ex) {
/* 149 */       AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*     */     } 
/* 151 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public File getSavedSignatureJpegFile(Context context, File signatureFile) {
/* 158 */     if (context == null || signatureFile == null || !signatureFile.exists()) {
/* 159 */       return null;
/*     */     }
/* 161 */     PDFDraw pdfDraw = null;
/*     */     try {
/* 163 */       File signatureJpgFolder = getSavedSignatureJpgFolder(context);
/* 164 */       String filename = FilenameUtils.getName(signatureFile.getAbsolutePath());
/* 165 */       String sigTempFilePath = signatureJpgFolder.getAbsolutePath() + "/" + filename + ".jpg";
/* 166 */       File outputFile = new File(sigTempFilePath);
/*     */       
/* 168 */       if (outputFile.exists()) {
/* 169 */         return outputFile;
/*     */       }
/*     */       
/* 172 */       Page page = getSignature(signatureFile.getAbsolutePath());
/* 173 */       if (page == null) {
/* 174 */         return null;
/*     */       }
/*     */       
/* 177 */       Rect cropBox = page.getCropBox();
/* 178 */       int width = (int)cropBox.getWidth();
/* 179 */       int height = (int)cropBox.getHeight();
/*     */       
/* 181 */       pdfDraw = new PDFDraw();
/* 182 */       pdfDraw.setPageTransparent(true);
/* 183 */       pdfDraw.setImageSize(width, height, true);
/* 184 */       pdfDraw.export(page, sigTempFilePath, "jpeg");
/* 185 */       return outputFile;
/* 186 */     } catch (Exception ex) {
/* 187 */       AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*     */       
/* 189 */       return null;
/*     */     } 
/*     */   }
/*     */   public Bitmap getSavedSignatureBitmap(Context context, File signatureFile) {
/* 193 */     File outputFile = getSavedSignatureJpegFile(context, signatureFile);
/* 194 */     if (outputFile == null) {
/* 195 */       return null;
/*     */     }
/* 197 */     return BitmapFactory.decodeFile(outputFile.getAbsolutePath());
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getSignatureFilePath(@NonNull Context context) {
/* 203 */     File signatureFolder = getSavedSignatureFolder(context);
/* 204 */     if (null == signatureFolder) {
/* 205 */       return null;
/*     */     }
/* 207 */     File signatureFile = new File(signatureFolder, SIGNATURE_FILE_NAME);
/* 208 */     return Utils.getFileNameNotInUse(signatureFile.getAbsolutePath());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void setDefaultSignatureFile(String pdfFilename) {
/* 218 */     this.mDefaultSignatureFilename = pdfFilename;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public String getDefaultSignatureFile() {
/* 226 */     return this.mDefaultSignatureFilename;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public boolean hasDefaultSignature(Context context) {
/* 231 */     boolean hasDefaultSignature = false;
/*     */     
/* 233 */     File stampFile = getStampFile(context);
/* 234 */     if (stampFile.exists()) {
/* 235 */       PDFDoc doc = getStampDoc(stampFile);
/* 236 */       boolean shouldUnlockRead = false;
/*     */       
/* 238 */       try { doc.lockRead();
/* 239 */         shouldUnlockRead = true;
/* 240 */         if (doc.getPageCount() > 0) {
/* 241 */           hasDefaultSignature = true;
/*     */         } }
/* 243 */       catch (PDFNetException pDFNetException) {  }
/*     */       finally
/* 245 */       { if (shouldUnlockRead) {
/* 246 */           Utils.unlockReadQuietly(doc);
/*     */         } }
/*     */     
/*     */     } 
/* 250 */     return hasDefaultSignature;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public Page getDefaultSignature(Context context) {
/* 255 */     Page page = null;
/*     */     
/* 257 */     File stampFile = getStampFile(context);
/* 258 */     if (stampFile.exists()) {
/* 259 */       PDFDoc doc = getStampDoc(stampFile);
/* 260 */       boolean shouldUnlockRead = false;
/*     */       
/* 262 */       try { doc.lockRead();
/* 263 */         shouldUnlockRead = true;
/* 264 */         if (doc.getPageCount() > 0) {
/* 265 */           page = doc.getPage(1);
/*     */         } }
/* 267 */       catch (PDFNetException pDFNetException) {  }
/*     */       finally
/* 269 */       { if (shouldUnlockRead) {
/* 270 */           Utils.unlockReadQuietly(doc);
/*     */         } }
/*     */     
/*     */     } 
/* 274 */     return page;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public void deleteDefaultSignature(Context context) {
/* 279 */     File stampFile = getStampFile(context);
/* 280 */     if (stampFile.exists()) {
/* 281 */       PDFDoc doc = getStampDoc(stampFile);
/* 282 */       boolean shouldUnlock = false;
/*     */       try {
/* 284 */         doc.lock();
/* 285 */         shouldUnlock = true;
/* 286 */         doc.pageRemove(doc.getPageIterator(1));
/* 287 */         doc.save(stampFile.getAbsolutePath(), SDFDoc.SaveMode.REMOVE_UNUSED, null);
/* 288 */       } catch (PDFNetException e) {
/* 289 */         AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*     */       } finally {
/* 291 */         if (shouldUnlock) {
/* 292 */           Utils.unlockQuietly(doc);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public Page getSignature(String signatureFilename) {
/* 299 */     Page page = null;
/*     */     
/* 301 */     File stampFile = new File(signatureFilename);
/* 302 */     if (stampFile.exists()) {
/* 303 */       PDFDoc doc = getStampDoc(stampFile);
/* 304 */       boolean shouldUnlockRead = false;
/*     */       
/* 306 */       try { doc.lockRead();
/* 307 */         shouldUnlockRead = true;
/* 308 */         if (doc.getPageCount() > 0) {
/* 309 */           page = doc.getPage(1);
/*     */         } }
/* 311 */       catch (PDFNetException pDFNetException) {  }
/*     */       finally
/* 313 */       { if (shouldUnlockRead) {
/* 314 */           Utils.unlockReadQuietly(doc);
/*     */         } }
/*     */     
/*     */     } 
/* 318 */     return page;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String createSignatureFromImage(Context context, Uri imageUri) {
/* 323 */     SecondaryFileFilter filter = null;
/* 324 */     PDFDoc pdfDoc = null;
/*     */     try {
/* 326 */       filter = new SecondaryFileFilter(context, imageUri);
/* 327 */       DocumentConversion conversion = Convert.universalConversion((Filter)filter, (ConversionOptions)new OfficeToPDFOptions("{\"DPI\": 96.0}"));
/*     */       
/* 329 */       conversion.convert();
/* 330 */       if (conversion.getDoc() != null) {
/* 331 */         pdfDoc = conversion.getDoc();
/* 332 */         String signaturePath = getSignatureFilePath(context);
/* 333 */         if (null != signaturePath) {
/* 334 */           pdfDoc.save(signaturePath, SDFDoc.SaveMode.REMOVE_UNUSED, null);
/* 335 */           return signaturePath;
/*     */         } 
/*     */       } 
/* 338 */     } catch (Exception e) {
/* 339 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */     } finally {
/* 341 */       Utils.closeQuietly(filter);
/* 342 */       Utils.closeQuietly(pdfDoc);
/*     */     } 
/* 344 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Single<Page> createSignature(final Context context, final String signatureFileName, final String svgString) {
/* 349 */     return Single.create(new SingleOnSubscribe<Page>()
/*     */         {
/*     */           public void subscribe(final SingleEmitter<Page> emitter) throws Exception {
/* 352 */             File svgFile = new File(Utils.getFileNameNotInUse((new File((new File(signatureFileName))
/* 353 */                     .getParentFile(), "temp_svg.svg")).getAbsolutePath()));
/*     */             
/*     */             try {
/* 356 */               FileWriter out = new FileWriter(svgFile);
/* 357 */               out.write(svgString);
/* 358 */               out.close();
/*     */               
/* 360 */               HTML2PDF.fromUrl(context, Uri.fromFile(svgFile).toString(), Uri.fromFile((new File(signatureFileName)).getParentFile()), (new File(signatureFileName)).getName(), new HTML2PDF.HTML2PDFListener()
/*     */                   {
/*     */                     public void onConversionFinished(String pdfOutput, boolean isLocal) {
/* 363 */                       PDFDoc doc = StampManager.this.getStampDoc(new File(pdfOutput));
/* 364 */                       if (null != doc) {
/*     */                         try {
/* 366 */                           Page page = doc.getPage(1);
/* 367 */                           emitter.onSuccess(page);
/* 368 */                         } catch (Exception ex) {
/* 369 */                           AnalyticsHandlerAdapter.getInstance().sendException(ex);
/* 370 */                           emitter.tryOnError(ex);
/*     */                         } 
/*     */                       }
/*     */                     }
/*     */ 
/*     */                     
/*     */                     public void onConversionFailed(String error) {
/* 377 */                       emitter.tryOnError(new RuntimeException(error));
/*     */                     }
/*     */                   });
/* 380 */             } catch (IOException e) {
/* 381 */               emitter.tryOnError(new RuntimeException(e));
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Page createVariableThicknessSignature(String signatureFilename, RectF signatureBBox, List<double[]> strokes, int strokeColor, float strokeThickness) {
/* 391 */     Page page = null;
/*     */     
/* 393 */     PDFDoc doc = createDocumentWithVariableThickness(signatureFilename, signatureBBox, strokes, strokeColor, strokeThickness);
/* 394 */     if (null != doc) {
/*     */       try {
/* 396 */         page = doc.getPage(1);
/* 397 */       } catch (Exception ex) {
/* 398 */         AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*     */       } 
/*     */     }
/* 401 */     return page;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private PDFDoc createDocumentWithVariableThickness(String signatureFilePath, RectF signatureBBox, List<double[]> paths, int strokeColor, float strokeThickness) {
/* 408 */     if (paths == null || paths.isEmpty()) {
/* 409 */       return null;
/*     */     }
/* 411 */     if (null == signatureFilePath) {
/* 412 */       return null;
/*     */     }
/* 414 */     PDFDoc doc = null;
/* 415 */     boolean shouldUnlock = false;
/*     */     try {
/* 417 */       File signatureFile = new File(signatureFilePath);
/* 418 */       doc = getStampDoc(signatureFile);
/* 419 */       doc.lock();
/* 420 */       shouldUnlock = true;
/* 421 */       if (doc.getPageCount() > 0) {
/* 422 */         doc.pageRemove(doc.getPageIterator(1));
/*     */       }
/*     */       
/* 425 */       ElementBuilder eb = new ElementBuilder();
/*     */       
/* 427 */       ElementWriter writer = new ElementWriter();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 434 */       float left = signatureBBox.left;
/* 435 */       float top = signatureBBox.top;
/* 436 */       float bottom = signatureBBox.bottom;
/* 437 */       float right = signatureBBox.right;
/* 438 */       Page page = doc.pageCreate(new Rect(0.0D, 0.0D, (right - left + 2.0F * strokeThickness), (top - bottom + 2.0F * strokeThickness)));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 445 */       writer.begin(page);
/* 446 */       eb.pathBegin();
/*     */       
/* 448 */       float delX = -left + strokeThickness;
/* 449 */       float delY = top + strokeThickness;
/*     */       
/* 451 */       for (double[] path : paths) {
/*     */         
/* 453 */         if (path.length == 0) {
/*     */           continue;
/*     */         }
/*     */ 
/*     */         
/* 458 */         int numOperators = (path.length / 2 - 1) / 3 + 1;
/* 459 */         byte[] operators = new byte[numOperators];
/* 460 */         operators[0] = 1;
/* 461 */         for (int j = 1; j < numOperators; j++) {
/* 462 */           operators[j] = 3;
/*     */         }
/*     */ 
/*     */         
/* 466 */         eb.createPath(path, operators);
/*     */         
/* 468 */         Element element = eb.pathEnd();
/* 469 */         element.setPathFill(true);
/* 470 */         element.setWindingFill(true);
/* 471 */         GState gstate = element.getGState();
/* 472 */         gstate.setTransform(1.0D, 0.0D, 0.0D, -1.0D, delX, delY);
/* 473 */         gstate.setFillColorSpace(ColorSpace.createDeviceRGB());
/* 474 */         gstate.setFillColor(Utils.color2ColorPt(strokeColor));
/* 475 */         writer.writePlacedElement(element);
/*     */       } 
/*     */       
/* 478 */       writer.end();
/* 479 */       doc.pagePushBack(page);
/*     */       
/* 481 */       doc.save(signatureFilePath, SDFDoc.SaveMode.REMOVE_UNUSED, null);
/* 482 */     } catch (PDFNetException e) {
/* 483 */       AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/* 484 */       Logger.INSTANCE.LogE("StampManager", e.getMessage());
/*     */     } finally {
/* 486 */       if (shouldUnlock) {
/* 487 */         Utils.unlockQuietly(doc);
/*     */       }
/*     */     } 
/*     */     
/* 491 */     return doc;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Page createSignature(String signatureFilename, RectF signatureBBox, LinkedList<LinkedList<PointF>> paths, int strokeColor, float strokeThickness) {
/* 498 */     Page page = null;
/*     */     
/* 500 */     PDFDoc doc = createDocument(signatureFilename, signatureBBox, paths, strokeColor, strokeThickness);
/* 501 */     if (null != doc) {
/*     */       try {
/* 503 */         page = doc.getPage(1);
/* 504 */       } catch (Exception ex) {
/* 505 */         AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*     */       } 
/*     */     }
/* 508 */     return page;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private PDFDoc createDocument(String signatureFilePath, RectF signatureBBox, LinkedList<LinkedList<PointF>> paths, int strokeColor, float strokeThickness) {
/* 515 */     if (null == signatureFilePath) {
/* 516 */       return null;
/*     */     }
/* 518 */     PDFDoc doc = null;
/* 519 */     boolean shouldUnlock = false;
/*     */     
/*     */     try {
/* 522 */       File signatureFile = new File(signatureFilePath);
/* 523 */       doc = getStampDoc(signatureFile);
/* 524 */       doc.lock();
/* 525 */       shouldUnlock = true;
/* 526 */       if (doc.getPageCount() > 0) {
/* 527 */         doc.pageRemove(doc.getPageIterator(1));
/*     */       }
/*     */ 
/*     */       
/* 531 */       Page page = doc.pageCreate(new Rect(0.0D, 0.0D, (signatureBBox.right - signatureBBox.left + (PAGE_BUFFER * 2)), (signatureBBox.top - signatureBBox.bottom + (PAGE_BUFFER * 2))));
/* 532 */       doc.pagePushBack(page);
/*     */ 
/*     */       
/* 535 */       Ink ink = Ink.create((Doc)doc, new Rect(PAGE_BUFFER, PAGE_BUFFER, (signatureBBox.right - signatureBBox.left + PAGE_BUFFER), (signatureBBox.top - signatureBBox.bottom + PAGE_BUFFER)));
/* 536 */       Annot.BorderStyle bs = ink.getBorderStyle();
/* 537 */       bs.setWidth(strokeThickness);
/* 538 */       ink.setBorderStyle(bs);
/*     */ 
/*     */       
/* 541 */       int i = 0;
/* 542 */       Point pdfPoint = new Point();
/* 543 */       for (LinkedList<PointF> pointList : paths) {
/* 544 */         int j = 0;
/* 545 */         for (PointF p : pointList) {
/* 546 */           pdfPoint.x = (p.x - signatureBBox.left + PAGE_BUFFER);
/* 547 */           pdfPoint.y = (signatureBBox.top - p.y + PAGE_BUFFER);
/* 548 */           ink.setPoint(i, j++, pdfPoint);
/*     */         } 
/* 550 */         i++;
/*     */       } 
/*     */       
/* 553 */       double r = Color.red(strokeColor) / 255.0D;
/* 554 */       double g = Color.green(strokeColor) / 255.0D;
/* 555 */       double b = Color.blue(strokeColor) / 255.0D;
/* 556 */       ink.setColor(new ColorPt(r, g, b), 3);
/* 557 */       ink.refreshAppearance();
/*     */ 
/*     */       
/* 560 */       Rect newBoundRect = ink.getRect();
/* 561 */       page.setCropBox(newBoundRect);
/*     */       
/* 563 */       ink.refreshAppearance();
/*     */       
/* 565 */       page.annotPushBack((Annot)ink);
/*     */       
/* 567 */       doc.save(signatureFilePath, SDFDoc.SaveMode.REMOVE_UNUSED, null);
/* 568 */     } catch (PDFNetException e) {
/* 569 */       AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*     */     } finally {
/* 571 */       if (shouldUnlock) {
/* 572 */         Utils.unlockQuietly(doc);
/*     */       }
/*     */     } 
/*     */     
/* 576 */     return doc;
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pd\\utils\StampManager.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */