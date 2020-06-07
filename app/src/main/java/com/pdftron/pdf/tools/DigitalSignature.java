/*     */ package com.pdftron.pdf.tools;
/*     */ 
/*     */ import android.content.ContentResolver;
/*     */ import android.content.Context;
/*     */ import android.content.DialogInterface;
/*     */ import android.net.Uri;
/*     */ import android.os.Environment;
/*     */ import android.view.MotionEvent;
/*     */ import androidx.annotation.Keep;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.Annot;
/*     */ import com.pdftron.pdf.Date;
/*     */ import com.pdftron.pdf.DigitalSignatureField;
/*     */ import com.pdftron.pdf.Field;
/*     */ import com.pdftron.pdf.Image;
/*     */ import com.pdftron.pdf.PDFDoc;
/*     */ import com.pdftron.pdf.PDFDraw;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.Page;
/*     */ import com.pdftron.pdf.Rect;
/*     */ import com.pdftron.pdf.annots.SignatureWidget;
/*     */ import com.pdftron.pdf.annots.Widget;
/*     */ import com.pdftron.pdf.dialog.digitalsignature.DialogSignatureInfo;
/*     */ import com.pdftron.pdf.dialog.digitalsignature.DigitalSignatureDialogFragment;
/*     */ import com.pdftron.pdf.dialog.signature.SignatureDialogFragment;
/*     */ import com.pdftron.pdf.dialog.signature.SignatureDialogFragmentBuilder;
/*     */ import com.pdftron.pdf.interfaces.OnCreateSignatureListener;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.AnnotUtils;
/*     */ import com.pdftron.pdf.utils.CommonToast;
/*     */ import com.pdftron.pdf.utils.Logger;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import com.pdftron.sdf.Doc;
/*     */ import com.pdftron.sdf.SDFDoc;
/*     */ import io.reactivex.disposables.CompositeDisposable;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Keep
/*     */ public class DigitalSignature
/*     */   extends Signature
/*     */ {
/*  55 */   private static final String TAG = DigitalSignature.class.getName();
/*     */ 
/*     */   
/*     */   private final String mDefaultFileSignedFilePath;
/*     */   
/*     */   private final CompositeDisposable mDisposables;
/*     */   
/*     */   private static final String DEFAULT_FILENAME = "sample_signed_0.pdf";
/*     */   
/*     */   @Nullable
/*     */   private Uri mKeystore;
/*     */   
/*     */   @Nullable
/*     */   private String mPassword;
/*     */ 
/*     */   
/*     */   public DigitalSignature(@NonNull PDFViewCtrl ctrl) {
/*  72 */     super(ctrl);
/*     */     
/*  74 */     this.mConfirmBtnStrRes = R.string.tools_qm_sign_and_save;
/*  75 */     this.mDisposables = new CompositeDisposable();
/*     */     
/*  77 */     this.mNextToolMode = getToolMode();
/*  78 */     this
/*     */ 
/*     */       
/*  81 */       .mDefaultFileSignedFilePath = Utils.isAndroidQ() ? (new File(Utils.getExternalDownloadDirectory(ctrl.getContext()), "sample_signed_0.pdf")).getAbsolutePath() : (new File(Environment.getExternalStorageDirectory(), "sample_signed_0.pdf")).getAbsolutePath();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onClose() {
/*  86 */     super.onClose();
/*     */     
/*  88 */     if (this.mDisposables != null && !this.mDisposables.isDisposed()) {
/*  89 */       this.mDisposables.dispose();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SignatureDialogFragment createSignatureDialogFragment(Long targetWidget, ToolManager toolManager) {
/* 107 */     DigitalSignatureDialogFragment fragment = (DigitalSignatureDialogFragment)(new SignatureDialogFragmentBuilder()).usingTargetPoint(this.mTargetPoint).usingTargetPage(this.mTargetPageNum).usingTargetWidget(targetWidget).usingColor(this.mColor).usingStrokeWidth(this.mStrokeThickness).usingShowSavedSignatures(toolManager.isShowSavedSignature()).usingShowSignatureFromImage(toolManager.isShowSignatureFromImage()).usingConfirmBtnStrRes(this.mConfirmBtnStrRes).usingPressureSensitive(toolManager.isUsingPressureSensitiveSignatures()).usingDefaultKeystore((toolManager.getDigitalSignatureKeystore() != null)).build(this.mPdfViewCtrl.getContext(), DigitalSignatureDialogFragment.class);
/*     */     
/* 109 */     fragment.setOnKeystoreUpdatedListener(new OnCreateSignatureListener.OnKeystoreUpdatedListener()
/*     */         {
/*     */           public void onKeystoreFileUpdated(@Nullable Uri keystore) {
/* 112 */             DigitalSignature.this.mKeystore = keystore;
/*     */           }
/*     */ 
/*     */           
/*     */           public void onKeystorePasswordUpdated(@Nullable String password) {
/* 117 */             DigitalSignature.this.mPassword = password;
/*     */           }
/*     */         });
/* 120 */     return (SignatureDialogFragment)fragment;
/*     */   }
/*     */ 
/*     */   
/*     */   public ToolManager.ToolModeBase getToolMode() {
/* 125 */     return ToolManager.ToolMode.DIGITAL_SIGNATURE;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCreateAnnotType() {
/* 130 */     return 1002;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean addSignatureStampToWidget(Page page) {
/* 136 */     String signatureImagePath = createSignatureImageFile(this.mPdfViewCtrl.getContext(), page);
/*     */     
/* 138 */     return signPdf(signatureImagePath);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void handleExistingSignatureWidget(int x, int y) {
/* 143 */     boolean handled = false;
/* 144 */     if (this.mWidget != null) {
/*     */       try {
/* 146 */         Field field = this.mWidget.getField();
/* 147 */         if (field.isValid() && field.getValue() != null) {
/* 148 */           showSignatureInfo();
/* 149 */           handled = true;
/*     */         } 
/* 151 */       } catch (Exception exception) {}
/*     */     }
/*     */     
/* 154 */     if (!handled) {
/* 155 */       super.handleExistingSignatureWidget(x, y);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onLongPress(MotionEvent e) {
/* 161 */     return onSingleTapConfirmed(e);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onUp(MotionEvent e, PDFViewCtrl.PriorEventMode priorEventMode) {
/* 166 */     return false;
/*     */   }
/*     */   
/*     */   protected boolean signPdf(String signaturePath) {
/* 170 */     ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 171 */     String keystorePath = toolManager.getDigitalSignatureKeystore();
/* 172 */     if (keystorePath != null) {
/* 173 */       this.mKeystore = Uri.fromFile(new File(keystorePath));
/* 174 */       this.mPassword = toolManager.getDigitalSignatureKeystorePassword();
/*     */     } 
/*     */ 
/*     */     
/* 178 */     if (this.mKeystore == null) {
/* 179 */       CommonToast.showText(this.mPdfViewCtrl
/* 180 */           .getContext(), R.string.tools_digitalsignature_missing_keystore, 0);
/*     */ 
/*     */ 
/*     */       
/* 184 */       return false;
/*     */     } 
/* 186 */     return signPdfImpl(signaturePath, this.mKeystore);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean signPdfImpl(@NonNull String signaturePath, @NonNull Uri keystore) {
/* 194 */     String newFilePath, origFilePath = null;
/*     */     
/*     */     try {
/* 197 */       newFilePath = this.mPdfViewCtrl.getDoc().getFileName();
/* 198 */       origFilePath = this.mPdfViewCtrl.getDoc().getFileName();
/* 199 */       if (newFilePath == null || newFilePath.length() == 0) {
/* 200 */         newFilePath = this.mDefaultFileSignedFilePath;
/*     */       } else {
/* 202 */         String s = newFilePath.substring(0, newFilePath.lastIndexOf("."));
/* 203 */         newFilePath = s + "_signed_0.pdf";
/*     */       } 
/* 205 */     } catch (Exception e) {
/* 206 */       newFilePath = this.mDefaultFileSignedFilePath;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 211 */     int i = 1;
/*     */     while (true) {
/* 213 */       File signedFile = new File(newFilePath);
/* 214 */       if (signedFile.exists()) {
/* 215 */         String s = newFilePath.substring(0, newFilePath.lastIndexOf("_"));
/* 216 */         newFilePath = s + "_" + i++ + ".pdf";
/*     */         
/*     */         continue;
/*     */       } 
/*     */       break;
/*     */     } 
/* 222 */     if (null != origFilePath) {
/* 223 */       saveFile(origFilePath);
/* 224 */       copyFile(origFilePath, newFilePath);
/* 225 */       File copiedFile = new File(newFilePath);
/* 226 */       int currPage = this.mPdfViewCtrl.getCurrentPage();
/* 227 */       boolean shouldUnlock = false;
/* 228 */       PDFDoc copiedDoc = null;
/*     */ 
/*     */       
/* 231 */       try { Annot selectedAnnot = null;
/* 232 */         SignatureWidget widget = null;
/* 233 */         copiedDoc = new PDFDoc(copiedFile.getAbsolutePath());
/*     */         
/* 235 */         copiedDoc.lock();
/* 236 */         shouldUnlock = true;
/* 237 */         Page page = copiedDoc.getPage(currPage);
/* 238 */         int annotationCount = page.getNumAnnots(); int a;
/* 239 */         for (a = 0; a < annotationCount; a++) {
/*     */           try {
/* 241 */             Annot annotation = page.getAnnot(a);
/* 242 */             if (null != annotation && annotation
/* 243 */               .isValid() && annotation
/* 244 */               .getSDFObj().getObjNum() == this.mAnnot.getSDFObj().getObjNum()) {
/* 245 */               selectedAnnot = annotation;
/*     */               break;
/*     */             } 
/* 248 */           } catch (PDFNetException e) {
/*     */             
/* 250 */             AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*     */           } 
/*     */         } 
/*     */         
/* 254 */         if (selectedAnnot != null) {
/* 255 */           widget = new SignatureWidget(selectedAnnot);
/*     */         }
/*     */         
/* 258 */         if (widget != null)
/* 259 */         { signDigitalSignatureField(copiedDoc, signaturePath, widget, keystore, (this.mPassword == null) ? "" : this.mPassword);
/*     */ 
/*     */ 
/*     */           
/* 263 */           copiedDoc.save(copiedFile.getAbsolutePath(), SDFDoc.SaveMode.INCREMENTAL, null);
/*     */ 
/*     */           
/* 266 */           ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 267 */           toolManager.onNewFileCreated(copiedFile.getAbsolutePath());
/*     */           
/* 269 */           CommonToast.showText(this.mPdfViewCtrl.getContext(), String.format(getStringFromResId(R.string.tools_digitalsignature_msg_saved), new Object[] { copiedFile.getAbsolutePath() }), 1); }
/*     */         else
/* 271 */         { CommonToast.showText(this.mPdfViewCtrl.getContext(), R.string.tools_digitalsignature_msg_file_locked, 1);
/* 272 */           a = 0;
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
/* 285 */           return a; }  a = 1; return a; } catch (PDFNetException e) { CommonToast.showText(this.mPdfViewCtrl.getContext(), getStringFromResId(R.string.tools_digitalsignature_msg_failed_to_save), 1); return false; } catch (IOException e) { CommonToast.showText(this.mPdfViewCtrl.getContext(), getStringFromResId(R.string.tools_digitalsignature_msg_failed_to_save), 1); return false; } finally { if (shouldUnlock) Utils.unlockQuietly(copiedDoc);  Utils.closeQuietly(copiedDoc); }
/*     */     
/*     */     } 
/* 288 */     return false;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void signDigitalSignatureField(@NonNull PDFDoc outputDoc, @NonNull String signaturePath, @NonNull SignatureWidget widget, @NonNull Uri keystore, @NonNull String password) throws PDFNetException, IOException {
/* 309 */     Image img = Image.create((Doc)outputDoc, signaturePath);
/* 310 */     widget.createSignatureAppearance(img);
/*     */ 
/*     */     
/* 313 */     DigitalSignatureField signatureField = new DigitalSignatureField(widget.getField());
/*     */ 
/*     */     
/* 316 */     signatureField.signOnNextSave(getByteArrayFromUri(keystore), password);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private byte[] getByteArrayFromUri(@NonNull Uri uri) throws IOException {
/* 321 */     ContentResolver cr = Utils.getContentResolver(this.mPdfViewCtrl.getContext());
/* 322 */     if (cr != null) {
/* 323 */       InputStream inputStream = null;
/*     */       try {
/* 325 */         inputStream = cr.openInputStream(uri);
/* 326 */         if (inputStream != null) {
/* 327 */           return IOUtils.toByteArray(inputStream);
/*     */         }
/*     */       } finally {
/* 330 */         Utils.closeQuietly(inputStream);
/*     */       } 
/*     */     } 
/* 333 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static String createSignatureImageFile(Context context, Page page) {
/* 339 */     String sigTempFilePath = context.getFilesDir().getAbsolutePath() + "/" + SIGNATURE_TEMP_FILE;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 344 */       Rect cropBox = page.getCropBox();
/* 345 */       int width = (int)cropBox.getWidth();
/* 346 */       int height = (int)cropBox.getHeight();
/* 347 */       PDFDraw pdfDraw = new PDFDraw();
/* 348 */       pdfDraw.setPageTransparent(true);
/* 349 */       pdfDraw.setImageSize(width, height, true);
/* 350 */       pdfDraw.export(page, sigTempFilePath, "jpeg");
/* 351 */     } catch (PDFNetException e) {
/* 352 */       e.printStackTrace();
/* 353 */       return null;
/*     */     } 
/*     */     
/* 356 */     return sigTempFilePath;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void showSignatureInfo() {
/* 364 */     if (this.mAnnot != null) {
/* 365 */       boolean shouldUnlock = false;
/*     */       try {
/* 367 */         this.mPdfViewCtrl.docLockRead();
/* 368 */         shouldUnlock = true;
/* 369 */         Widget widget = new Widget(this.mAnnot);
/* 370 */         Field field = widget.getField();
/* 371 */         DigitalSignatureField signatureField = new DigitalSignatureField(field);
/* 372 */         if (field.isValid() && field.getValue() != null && signatureField.hasCryptographicSignature()) {
/*     */           
/* 374 */           String documentPermissionMsg = getPermissionString(this.mPdfViewCtrl.getContext(), signatureField.getDocumentPermissions());
/* 375 */           String location = null;
/* 376 */           String reason = null;
/* 377 */           String signatureName = null;
/* 378 */           String contactInfo = null;
/* 379 */           String signingTimeString = null;
/* 380 */           if (hasSigningInfo(signatureField)) {
/* 381 */             location = signatureField.getLocation();
/* 382 */             reason = signatureField.getReason();
/* 383 */             signatureName = signatureField.getSignatureName();
/* 384 */             contactInfo = signatureField.getContactInfo();
/* 385 */             Date signingTime = signatureField.getSigningTime();
/* 386 */             signingTimeString = signingTime.isValid() ? AnnotUtils.getLocalDate(signingTime).toString() : null;
/*     */           } 
/*     */           
/* 389 */           DialogSignatureInfo dialog = new DialogSignatureInfo(this.mPdfViewCtrl.getContext());
/* 390 */           dialog.setLocation(location);
/* 391 */           dialog.setReason(reason);
/* 392 */           dialog.setName(signatureName);
/* 393 */           dialog.setContactInfo(contactInfo);
/* 394 */           dialog.setSigningTime(signingTimeString);
/* 395 */           dialog.setDocumentPermission(documentPermissionMsg);
/* 396 */           dialog.setOnDismissListener(new DialogInterface.OnDismissListener()
/*     */               {
/*     */                 public void onDismiss(DialogInterface dialog) {
/* 399 */                   DigitalSignature.this.unsetAnnot();
/* 400 */                   DigitalSignature.this.mNextToolMode = ToolManager.ToolMode.PAN;
/*     */                 }
/*     */               });
/* 403 */           dialog.show();
/*     */         } 
/* 405 */       } catch (Exception exception) {
/*     */       
/*     */       } finally {
/* 408 */         if (shouldUnlock) {
/* 409 */           this.mPdfViewCtrl.docUnlockRead();
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void saveFile(String oldPath) {
/* 416 */     boolean shouldUnlock = false;
/*     */     try {
/* 418 */       this.mPdfViewCtrl.docLock(true);
/* 419 */       shouldUnlock = true;
/* 420 */       this.mPdfViewCtrl.getDoc().save(oldPath, SDFDoc.SaveMode.INCREMENTAL, null);
/* 421 */     } catch (Exception e) {
/* 422 */       e.printStackTrace();
/*     */     } finally {
/* 424 */       if (shouldUnlock) {
/* 425 */         this.mPdfViewCtrl.docUnlock();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private String getPermissionString(@NonNull Context context, @NonNull DigitalSignatureField.DocumentPermissions permissions) {
/*     */     int result;
/* 434 */     switch (permissions) {
/*     */       case e_no_changes_allowed:
/* 436 */         result = R.string.tools_digitalsignature_doc_permission_no_changes;
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
/* 451 */         return context.getString(result);case e_formfilling_signing_allowed: result = R.string.tools_digitalsignature_doc_permission_formfill_sign; return context.getString(result);case e_annotating_formfilling_signing_allowed: result = R.string.tools_digitalsignature_doc_permission_formfill_sign_annot; return context.getString(result);case e_unrestricted: result = R.string.tools_digitalsignature_doc_permission_unrestricted; return context.getString(result);
/*     */     } 
/*     */     Logger.INSTANCE.LogE(TAG, "Unrecognized digital signature document permission level.");
/*     */     return null; } private boolean hasSigningInfo(@NonNull DigitalSignatureField signatureField) throws PDFNetException {
/* 455 */     return (signatureField.getSubFilter() != DigitalSignatureField.SubFilterType.e_ETSI_RFC3161);
/*     */   }
/*     */   
/*     */   private void copyFile(String oldPath, String newPath) {
/* 459 */     InputStream is = null;
/* 460 */     OutputStream fos = null;
/*     */     try {
/* 462 */       is = new FileInputStream(oldPath);
/* 463 */       fos = new FileOutputStream(newPath);
/* 464 */       IOUtils.copy(is, fos);
/* 465 */     } catch (Exception e) {
/* 466 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */     } finally {
/* 468 */       Utils.closeQuietly(fos);
/* 469 */       Utils.closeQuietly(is);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\DigitalSignature.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */