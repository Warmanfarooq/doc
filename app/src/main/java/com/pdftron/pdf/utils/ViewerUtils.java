/*      */ package com.pdftron.pdf.utils;
/*      */ 
/*      */ import android.animation.Animator;
/*      */ import android.animation.AnimatorSet;
/*      */ import android.animation.FloatEvaluator;
/*      */ import android.animation.ObjectAnimator;
/*      */ import android.animation.TypeEvaluator;
/*      */ import android.app.Activity;
/*      */ import android.content.ComponentName;
/*      */ import android.content.ContentResolver;
/*      */ import android.content.Context;
/*      */ import android.content.Intent;
/*      */ import android.content.pm.PackageManager;
/*      */ import android.content.pm.ResolveInfo;
/*      */ import android.content.res.Resources;
/*      */ import android.graphics.Bitmap;
/*      */ import android.graphics.Canvas;
/*      */ import android.graphics.Matrix;
/*      */ import android.graphics.Path;
/*      */ import android.graphics.PointF;
/*      */ import android.graphics.RectF;
/*      */ import android.graphics.drawable.BitmapDrawable;
/*      */ import android.graphics.drawable.ColorDrawable;
/*      */ import android.graphics.drawable.Drawable;
/*      */ import android.graphics.drawable.StateListDrawable;
/*      */ import android.media.ExifInterface;
/*      */ import android.net.Uri;
/*      */ import android.os.ParcelFileDescriptor;
/*      */ import android.os.Parcelable;
/*      */ import android.util.Log;
/*      */ import android.view.View;
/*      */ import android.webkit.MimeTypeMap;
/*      */ import androidx.annotation.NonNull;
/*      */ import androidx.annotation.Nullable;
/*      */ import androidx.fragment.app.Fragment;
/*      */ import androidx.fragment.app.FragmentActivity;
/*      */ import com.pdftron.common.PDFNetException;
/*      */ import com.pdftron.filters.Filter;
/*      */ import com.pdftron.filters.FilterReader;
/*      */ import com.pdftron.filters.FilterWriter;
/*      */ import com.pdftron.filters.SecondaryFileFilter;
/*      */ import com.pdftron.pdf.Annot;
/*      */ import com.pdftron.pdf.ConversionOptions;
/*      */ import com.pdftron.pdf.Convert;
/*      */ import com.pdftron.pdf.DocumentConversion;
/*      */ import com.pdftron.pdf.FileSpec;
/*      */ import com.pdftron.pdf.PDFDoc;
/*      */ import com.pdftron.pdf.PDFViewCtrl;
/*      */ import com.pdftron.pdf.Rect;
/*      */ import com.pdftron.pdf.WordToPDFOptions;
/*      */ import com.pdftron.pdf.annots.FileAttachment;
/*      */ import com.pdftron.pdf.annots.Widget;
/*      */ import com.pdftron.pdf.dialog.menueditor.model.MenuEditorItem;
/*      */ import com.pdftron.pdf.dialog.menueditor.model.MenuEditorItemContent;
/*      */ import com.pdftron.pdf.dialog.menueditor.model.MenuEditorItemHeader;
/*      */ import com.pdftron.pdf.model.ExternalFileInfo;
/*      */ import com.pdftron.pdf.tools.FileAttachmentCreate;
/*      */ import com.pdftron.pdf.tools.R;
/*      */ import com.pdftron.pdf.tools.Signature;
/*      */ import com.pdftron.pdf.tools.Stamper;
/*      */ import com.pdftron.pdf.tools.ToolManager;
/*      */ import com.pdftron.sdf.Doc;
/*      */ import com.pdftron.sdf.NameTree;
/*      */ import com.pdftron.sdf.NameTreeIterator;
/*      */ import com.pdftron.sdf.SDFDoc;
/*      */ import com.pdftron.sdf.SecurityHandler;
/*      */ import io.reactivex.Single;
/*      */ import io.reactivex.SingleEmitter;
/*      */ import io.reactivex.SingleOnSubscribe;
/*      */ import java.io.File;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.HashMap;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import org.apache.commons.io.FileUtils;
/*      */ import org.apache.commons.io.FilenameUtils;
/*      */ import org.apache.commons.io.IOUtils;
/*      */ import org.json.JSONArray;
/*      */ import org.json.JSONException;
/*      */ import org.json.JSONObject;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ViewerUtils
/*      */ {
/*      */   private static final String IMAGE_INTENT_IS_CAMERA = "camera";
/*      */   private static final String IMAGE_INTENT_URI = "uri";
/*      */   private static final String IMAGE_INTENT_PATH = "path";
/*      */   private static final String KEY_MENU_EDITOR_ID = "id";
/*      */   private static final String KEY_MENU_EDITOR_GROUP_IF_ROOM = "ifroom";
/*      */   private static final String KEY_MENU_EDITOR_GROUP_NEVER = "never";
/*      */   
/*      */   public static boolean isViewerZoomed(PDFViewCtrl pdfViewCtrl) {
/*      */     PDFViewCtrl.PageViewMode refMode;
/*  116 */     if (pdfViewCtrl.isMaintainZoomEnabled()) {
/*  117 */       refMode = pdfViewCtrl.getPreferredViewMode();
/*      */     } else {
/*  119 */       refMode = pdfViewCtrl.getPageRefViewMode();
/*      */     } 
/*  121 */     double zoom = pdfViewCtrl.getZoom();
/*  122 */     double refZoom = 0.0D;
/*      */     try {
/*  124 */       refZoom = pdfViewCtrl.getZoomForViewMode(refMode);
/*  125 */     } catch (PDFNetException ex) {
/*  126 */       Log.v("Tool", ex.getMessage());
/*      */     } 
/*  128 */     boolean zoomed = true;
/*  129 */     if (refZoom > 0.0D && zoom > 0.0D) {
/*  130 */       double zoomFactor = refZoom / zoom;
/*  131 */       if (zoomFactor > 0.95D && zoomFactor < 1.05D) {
/*  132 */         zoomed = false;
/*      */       }
/*      */     } 
/*  135 */     return zoomed;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void openFileIntent(@NonNull Activity activity) {
/*  144 */     openFileIntent(activity, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void openFileIntent(@NonNull Fragment fragment) {
/*  153 */     openFileIntent(null, fragment);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void openFileIntent(Activity activity, Fragment fragment) {
/*  163 */     if (!Utils.isKitKat()) {
/*      */       return;
/*      */     }
/*  166 */     if (activity == null && fragment == null) {
/*      */       return;
/*      */     }
/*  169 */     Intent intent = new Intent("android.intent.action.OPEN_DOCUMENT");
/*  170 */     intent.addCategory("android.intent.category.OPENABLE");
/*  171 */     intent.setType("*/*");
/*      */     
/*  173 */     if (fragment != null) {
/*  174 */       fragment.startActivityForResult(intent, 10011);
/*      */     } else {
/*  176 */       activity.startActivityForResult(intent, 10011);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void createFileAttachment(Activity activity, Intent data, PDFViewCtrl pdfViewCtrl, PointF targetPoint) {
/*  181 */     if (activity == null || activity.getContentResolver() == null || data == null || data.getData() == null || targetPoint == null) {
/*      */       return;
/*      */     }
/*  184 */     InputStream fis = null;
/*  185 */     OutputStream fos = null;
/*  186 */     String tempPath = null;
/*      */     try {
/*  188 */       Uri fileUri = data.getData();
/*  189 */       String extension = Utils.getUriExtension(activity.getContentResolver(), fileUri);
/*  190 */       String name = Utils.getUriDisplayName((Context)activity, fileUri);
/*  191 */       if (Utils.isNullOrEmpty(extension)) {
/*      */         return;
/*      */       }
/*  194 */       if (Utils.isNullOrEmpty(name)) {
/*  195 */         name = "untitled" + extension;
/*      */       }
/*      */ 
/*      */       
/*  199 */       fis = activity.getContentResolver().openInputStream(fileUri);
/*  200 */       if (fis != null) {
/*  201 */         FileAttachmentCreate tool; tempPath = activity.getFilesDir() + "/" + name;
/*  202 */         tempPath = Utils.getFileNameNotInUse(tempPath);
/*  203 */         fos = new FileOutputStream(new File(tempPath));
/*  204 */         IOUtils.copy(fis, fos);
/*      */ 
/*      */         
/*  207 */         if (((ToolManager)pdfViewCtrl.getToolManager()).getTool().getToolMode() != ToolManager.ToolMode.FILE_ATTACHMENT_CREATE) {
/*  208 */           tool = (FileAttachmentCreate)((ToolManager)pdfViewCtrl.getToolManager()).createTool((ToolManager.ToolModeBase)ToolManager.ToolMode.FILE_ATTACHMENT_CREATE, null);
/*  209 */           ((ToolManager)pdfViewCtrl.getToolManager()).setTool((ToolManager.Tool)tool);
/*      */         } else {
/*  211 */           tool = (FileAttachmentCreate)((ToolManager)pdfViewCtrl.getToolManager()).getTool();
/*      */         } 
/*  213 */         tool.setTargetPoint(targetPoint, false);
/*  214 */         int pageNum = pdfViewCtrl.getPageNumberFromScreenPt(targetPoint.x, targetPoint.y);
/*  215 */         boolean success = tool.createFileAttachment(targetPoint, pageNum, tempPath);
/*  216 */         if (!success) {
/*  217 */           CommonToast.showText((Context)activity, activity.getString(R.string.attach_file_error), 0);
/*      */         }
/*      */       } 
/*  220 */     } catch (FileNotFoundException e) {
/*  221 */       CommonToast.showText((Context)activity, activity.getString(R.string.image_stamper_file_not_found_error), 0);
/*  222 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*  223 */     } catch (Exception e) {
/*  224 */       CommonToast.showText((Context)activity, activity.getString(R.string.attach_file_error), 0);
/*  225 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } finally {
/*  227 */       Utils.closeQuietly(fis);
/*  228 */       Utils.closeQuietly(fos);
/*  229 */       if (tempPath != null) {
/*  230 */         File tempFile = new File(tempPath);
/*  231 */         if (tempFile.exists()) {
/*  232 */           tempFile.delete();
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Uri openImageIntent(@NonNull Activity activity) {
/*  244 */     return openImageIntent(activity, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Uri openImageIntent(@NonNull Fragment fragment) {
/*  253 */     return openImageIntent(null, fragment);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Uri openImageIntent(@Nullable Activity activity, @Nullable Fragment fragment) {
/*      */     FragmentActivity fragmentActivity;
/*  265 */     if (null == activity && null == fragment) {
/*  266 */       return null;
/*      */     }
/*      */     
/*  269 */     if (activity != null) {
/*  270 */       Activity activityContext = activity;
/*      */     } else {
/*  272 */       fragmentActivity = fragment.getActivity();
/*      */     } 
/*  274 */     if (null == fragmentActivity) {
/*  275 */       return null;
/*      */     }
/*      */     
/*  278 */     return openImageIntent((Activity)fragmentActivity, fragment, fragmentActivity.getExternalCacheDir());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Uri openImageIntent(@NonNull Activity activity, @Nullable Fragment fragment, @NonNull File folder) {
/*  293 */     String fname = "IMG_" + System.currentTimeMillis() + ".jpg";
/*  294 */     File sdImageMainDirectory = new File(folder, fname);
/*  295 */     Uri outputFileUri = Utils.getUriForFile((Context)activity, sdImageMainDirectory);
/*  296 */     if (outputFileUri == null) {
/*  297 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  301 */     List<Intent> cameraIntents = new ArrayList<>();
/*  302 */     Intent captureIntent = new Intent("android.media.action.IMAGE_CAPTURE");
/*  303 */     PackageManager packageManager = activity.getPackageManager();
/*  304 */     List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
/*  305 */     for (ResolveInfo res : listCam) {
/*  306 */       String packageName = res.activityInfo.packageName;
/*  307 */       Intent intent = new Intent(captureIntent);
/*  308 */       intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
/*  309 */       intent.setPackage(packageName);
/*  310 */       intent.putExtra("output", (Parcelable)outputFileUri);
/*  311 */       cameraIntents.add(intent);
/*      */     } 
/*      */ 
/*      */     
/*  315 */     Intent fileIntent = new Intent("android.intent.action.GET_CONTENT");
/*  316 */     fileIntent.setType("image/*");
/*  317 */     cameraIntents.add(fileIntent);
/*      */ 
/*      */     
/*  320 */     Intent galleryIntent = new Intent("android.intent.action.PICK");
/*  321 */     galleryIntent.setType("image/*");
/*      */ 
/*      */ 
/*      */     
/*  325 */     Intent chooserIntent = Intent.createChooser(galleryIntent, activity.getString(R.string.image_intent_title));
/*      */ 
/*      */     
/*  328 */     chooserIntent.putExtra("android.intent.extra.INITIAL_INTENTS", cameraIntents.<Parcelable>toArray(new Parcelable[0]));
/*      */     
/*  330 */     if (fragment != null) {
/*  331 */       fragment.startActivityForResult(chooserIntent, 10003);
/*      */     } else {
/*  333 */       activity.startActivityForResult(chooserIntent, 10003);
/*      */     } 
/*  335 */     return outputFileUri;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void createImageSignature(Activity activity, Intent data, PDFViewCtrl pdfViewCtrl, Uri outputFileUri, PointF targetPoint, int targetPage, Long widget) {
/*  340 */     if (activity == null) {
/*      */       return;
/*      */     }
/*  343 */     if (targetPoint == null && widget == null) {
/*      */       return;
/*      */     }
/*      */     try {
/*      */       Signature tool;
/*  348 */       Map imageIntent = readImageIntent(data, (Context)activity, outputFileUri);
/*  349 */       if (!checkImageIntent(imageIntent)) {
/*  350 */         Utils.handlePdfFromImageFailed((Context)activity, imageIntent);
/*      */         return;
/*      */       } 
/*  353 */       String filePath = (String)imageIntent.get("path");
/*  354 */       Uri imageUri = (Uri)imageIntent.get("uri");
/*  355 */       Boolean isCamera = (Boolean)imageIntent.get("camera");
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  360 */       if (((ToolManager)pdfViewCtrl.getToolManager()).getTool().getToolMode() != ToolManager.ToolMode.SIGNATURE) {
/*  361 */         tool = (Signature)((ToolManager)pdfViewCtrl.getToolManager()).createTool((ToolManager.ToolModeBase)ToolManager.ToolMode.SIGNATURE, null);
/*  362 */         ((ToolManager)pdfViewCtrl.getToolManager()).setTool((ToolManager.Tool)tool);
/*      */       } else {
/*  364 */         tool = (Signature)((ToolManager)pdfViewCtrl.getToolManager()).getTool();
/*      */       } 
/*  366 */       if (targetPoint != null) {
/*  367 */         tool.setTargetPoint(targetPoint, targetPage);
/*      */       }
/*  369 */       Annot targetAnnot = null;
/*  370 */       if (widget != null) {
/*  371 */         targetAnnot = Widget.__Create(widget.longValue(), pdfViewCtrl.getDoc());
/*      */       }
/*  373 */       String signaturePath = StampManager.getInstance().createSignatureFromImage((Context)activity, imageUri);
/*  374 */       if (signaturePath != null) {
/*  375 */         tool.create(signaturePath, targetAnnot);
/*      */       }
/*      */ 
/*      */       
/*  379 */       if (isCamera.booleanValue()) {
/*  380 */         FileUtils.deleteQuietly(new File(filePath));
/*      */       }
/*  382 */       AnalyticsHandlerAdapter.getInstance().sendEvent(64, 
/*  383 */           AnalyticsParam.createNewParam(isCamera.booleanValue() ? 8 : 7, 1));
/*      */     }
/*  385 */     catch (FileNotFoundException e) {
/*  386 */       CommonToast.showText((Context)activity, activity.getString(R.string.image_stamper_file_not_found_error), 0);
/*  387 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*  388 */     } catch (Exception e) {
/*  389 */       CommonToast.showText((Context)activity, activity.getString(R.string.image_stamper_error), 0);
/*  390 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static Uri getImageUriFromIntent(@Nullable Intent data, @NonNull Activity activity, @NonNull Uri outputFileUri) {
/*      */     try {
/*  397 */       Map imageIntent = readImageIntent(data, (Context)activity, outputFileUri);
/*  398 */       if (!checkImageIntent(imageIntent)) {
/*  399 */         Utils.handlePdfFromImageFailed((Context)activity, imageIntent);
/*  400 */         return null;
/*      */       } 
/*  402 */       return (Uri)imageIntent.get("uri");
/*  403 */     } catch (FileNotFoundException e) {
/*  404 */       CommonToast.showText((Context)activity, activity.getString(R.string.image_stamper_file_not_found_error), 0);
/*  405 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */       
/*  407 */       return null;
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void createImageStamp(Activity activity, Intent data, PDFViewCtrl pdfViewCtrl, Uri outputFileUri, PointF imageStampTargetPoint) {
/*  412 */     if (activity == null) {
/*      */       return;
/*      */     }
/*      */     try {
/*      */       Stamper stamperTool;
/*  417 */       Map imageIntent = readImageIntent(data, (Context)activity, outputFileUri);
/*  418 */       if (!checkImageIntent(imageIntent)) {
/*  419 */         Utils.handlePdfFromImageFailed((Context)activity, imageIntent);
/*      */         return;
/*      */       } 
/*  422 */       String filePath = (String)imageIntent.get("path");
/*  423 */       Uri imageUri = (Uri)imageIntent.get("uri");
/*  424 */       Boolean isCamera = (Boolean)imageIntent.get("camera");
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  429 */       if (((ToolManager)pdfViewCtrl.getToolManager()).getTool().getToolMode() != ToolManager.ToolMode.STAMPER) {
/*  430 */         stamperTool = (Stamper)((ToolManager)pdfViewCtrl.getToolManager()).createTool((ToolManager.ToolModeBase)ToolManager.ToolMode.STAMPER, null);
/*  431 */         ((ToolManager)pdfViewCtrl.getToolManager()).setTool((ToolManager.Tool)stamperTool);
/*      */       } else {
/*  433 */         stamperTool = (Stamper)((ToolManager)pdfViewCtrl.getToolManager()).getTool();
/*      */       } 
/*  435 */       stamperTool.setTargetPoint(imageStampTargetPoint, false);
/*  436 */       boolean success = stamperTool.createImageStamp(imageUri, 0, imageUri.getEncodedPath());
/*  437 */       if (!success) {
/*  438 */         CommonToast.showText((Context)activity, activity.getString(R.string.image_stamper_error), 0);
/*      */       }
/*      */ 
/*      */       
/*  442 */       if (isCamera.booleanValue()) {
/*  443 */         FileUtils.deleteQuietly(new File(filePath));
/*      */       }
/*  445 */       AnalyticsHandlerAdapter.getInstance().sendEvent(48, 
/*  446 */           AnalyticsParam.createNewParam(isCamera.booleanValue() ? 8 : 7, 1));
/*      */     }
/*  448 */     catch (FileNotFoundException e) {
/*  449 */       CommonToast.showText((Context)activity, activity.getString(R.string.image_stamper_file_not_found_error), 0);
/*  450 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*  451 */     } catch (Exception e) {
/*  452 */       CommonToast.showText((Context)activity, activity.getString(R.string.image_stamper_error), 0);
/*  453 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static String exportFileAttachment(@NonNull PDFViewCtrl pdfViewCtrl, @NonNull FileAttachment attachment) {
/*  459 */     return exportFileAttachment(pdfViewCtrl, attachment, Utils.getExternalDownloadDirectory(pdfViewCtrl.getContext()));
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static String exportFileAttachment(@NonNull PDFViewCtrl pdfViewCtrl, @NonNull FileAttachment attachment, @NonNull File directory) {
/*  464 */     if (!directory.isDirectory()) {
/*  465 */       return null;
/*      */     }
/*  467 */     boolean shouldUnlockRead = false;
/*      */     
/*      */     try {
/*  470 */       pdfViewCtrl.docLockRead();
/*  471 */       shouldUnlockRead = true;
/*  472 */       String filename = attachment.getFileSpec().getFilePath();
/*  473 */       String extension = Utils.getExtension(filename);
/*  474 */       filename = FilenameUtils.getName(filename);
/*  475 */       if (Utils.isNullOrEmpty(extension))
/*      */       {
/*  477 */         filename = filename + ".pdf";
/*      */       }
/*  479 */       File attachmentFile = new File(directory, filename);
/*  480 */       String attachmentFilePath = Utils.getFileNameNotInUse(attachmentFile.getAbsolutePath());
/*  481 */       attachmentFile = new File(attachmentFilePath);
/*  482 */       attachment.export(attachmentFile.getAbsolutePath());
/*  483 */       return attachmentFile.getAbsolutePath();
/*  484 */     } catch (Exception e) {
/*  485 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } finally {
/*  487 */       if (shouldUnlockRead) {
/*  488 */         pdfViewCtrl.docUnlockRead();
/*      */       }
/*      */     } 
/*  491 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Map readImageIntent(Intent data, @NonNull Context context, @Nullable Uri outputFileUri) throws FileNotFoundException {
/*      */     boolean isCamera;
/*      */     Uri imageUri;
/*      */     String filePath;
/*  504 */     if (outputFileUri == null) {
/*  505 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  509 */     if (data == null || data.getData() == null) {
/*  510 */       isCamera = true;
/*      */     } else {
/*  512 */       String action = data.getAction();
/*  513 */       isCamera = (action != null && action.equals("android.media.action.IMAGE_CAPTURE"));
/*      */     } 
/*      */ 
/*      */     
/*  517 */     if (isCamera) {
/*  518 */       imageUri = outputFileUri;
/*      */     } else {
/*  520 */       imageUri = data.getData();
/*      */     } 
/*      */ 
/*      */     
/*  524 */     if (isCamera) {
/*  525 */       filePath = imageUri.getPath();
/*      */     } else {
/*  527 */       filePath = Utils.getRealPathFromImageURI(context, imageUri);
/*  528 */       if (Utils.isNullOrEmpty(filePath)) {
/*  529 */         filePath = imageUri.getPath();
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  534 */     if (!isCamera) {
/*      */       
/*  536 */       ContentResolver contentResolver = Utils.getContentResolver(context);
/*  537 */       if (contentResolver == null) {
/*  538 */         return null;
/*      */       }
/*  540 */       if (contentResolver.getType(imageUri) == null) {
/*  541 */         String extension = MimeTypeMap.getFileExtensionFromUrl(imageUri.getPath());
/*  542 */         String[] extensions = { "jpeg", "jpg", "tiff", "tif", "gif", "png", "bmp" };
/*      */ 
/*      */         
/*  545 */         if (!Arrays.<String>asList(extensions).contains(extension) && extension != null && !extension.equals("")) {
/*  546 */           throw new FileNotFoundException("file extension is not an image extension");
/*      */         }
/*      */       } else {
/*      */         
/*  550 */         String type = contentResolver.getType(imageUri);
/*  551 */         if (type != null && !type.startsWith("image/")) {
/*  552 */           throw new FileNotFoundException("type is not an image");
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  557 */     Map<String, Object> output = new HashMap<>();
/*  558 */     output.put("uri", imageUri);
/*  559 */     output.put("path", filePath);
/*  560 */     output.put("camera", Boolean.valueOf(isCamera));
/*  561 */     return output;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean checkImageIntent(Map imageIntent) {
/*  571 */     return (imageIntent != null && imageIntent
/*  572 */       .get("path") != null && imageIntent.get("path") instanceof String && imageIntent
/*  573 */       .get("uri") != null && imageIntent.get("uri") instanceof Uri && imageIntent
/*  574 */       .get("camera") != null && imageIntent.get("camera") instanceof Boolean);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Uri getImageUri(Map imageIntent) {
/*  584 */     return (imageIntent == null) ? null : (Uri)imageIntent.get("uri");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getImageFilePath(Map imageIntent) {
/*  594 */     return (imageIntent == null) ? null : (String)imageIntent.get("path");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isImageFromCamera(Map imageIntent) {
/*  604 */     return (imageIntent != null && ((Boolean)imageIntent.get("camera")).booleanValue());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String imageIntentToPdf(Context context, Uri imageUri, String imagePath, String outputPath) throws PDFNetException, FileNotFoundException {
/*  616 */     if (context == null || (imageUri == null && Utils.isNullOrEmpty(imagePath)) || outputPath == null) {
/*  617 */       return null;
/*      */     }
/*  619 */     PDFDoc outDoc = null;
/*  620 */     SecondaryFileFilter filter = null;
/*      */     try {
/*      */       DocumentConversion conv;
/*  623 */       if (imageUri != null) {
/*  624 */         filter = new SecondaryFileFilter(context, imageUri);
/*  625 */         conv = Convert.universalConversion((Filter)filter, (ConversionOptions)new WordToPDFOptions("{\"DPI\": 96.0}"));
/*      */       } else {
/*  627 */         conv = Convert.universalConversion(imagePath, (ConversionOptions)new WordToPDFOptions("{\"DPI\": 96.0}"));
/*      */       } 
/*  629 */       conv.convert();
/*  630 */       if (conv.getDoc() == null) {
/*  631 */         return null;
/*      */       }
/*  633 */       outDoc = conv.getDoc();
/*  634 */       outDoc.save(outputPath, SDFDoc.SaveMode.REMOVE_UNUSED, null);
/*  635 */       return outputPath;
/*      */     } finally {
/*  637 */       Utils.closeQuietly(outDoc, filter);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String imageIntentToPdf(Context context, Uri imageUri, String imagePath, ExternalFileInfo documentFile) throws PDFNetException, IOException {
/*  652 */     if (context == null || (imageUri == null && Utils.isNullOrEmpty(imagePath)) || documentFile == null) {
/*  653 */       return null;
/*      */     }
/*  655 */     PDFDoc outDoc = null;
/*  656 */     SecondaryFileFilter filter = null;
/*      */     
/*      */     try {
/*      */       DocumentConversion conv;
/*  660 */       if (imageUri != null) {
/*  661 */         filter = new SecondaryFileFilter(context, imageUri);
/*  662 */         conv = Convert.universalConversion((Filter)filter, (ConversionOptions)new WordToPDFOptions("{\"DPI\": 96.0}"));
/*      */       } else {
/*  664 */         conv = Convert.universalConversion(imagePath, (ConversionOptions)new WordToPDFOptions("{\"DPI\": 96.0}"));
/*      */       } 
/*  666 */       conv.convert();
/*  667 */       if (conv.getDoc() == null) {
/*  668 */         return null;
/*      */       }
/*  670 */       outDoc = conv.getDoc();
/*      */       
/*  672 */       SecondaryFileFilter outFilter = new SecondaryFileFilter(context, documentFile.getUri());
/*      */       
/*  674 */       outDoc.save((Filter)outFilter, SDFDoc.SaveMode.REMOVE_UNUSED);
/*  675 */       return documentFile.getAbsolutePath();
/*      */     } finally {
/*  677 */       Utils.closeQuietly(outDoc, filter);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Bitmap getImageBitmap(Context context, Map imageIntent) {
/*  689 */     Uri imageUri = getImageUri(imageIntent);
/*  690 */     String filePath = getImageFilePath(imageIntent);
/*  691 */     if (imageUri == null || Utils.isNullOrEmpty(filePath)) {
/*  692 */       return null;
/*      */     }
/*      */     
/*  695 */     Bitmap bitmap = Utils.getBitmapFromImageUri(context, imageUri, filePath);
/*      */     
/*      */     try {
/*  698 */       int imageRotation = getImageRotation(context, imageIntent);
/*  699 */       if (bitmap != null && imageRotation != 0) {
/*  700 */         Matrix matrix = new Matrix();
/*  701 */         matrix.postRotate(imageRotation);
/*  702 */         bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
/*      */       } 
/*  704 */     } catch (OutOfMemoryError oom) {
/*  705 */       Utils.manageOOM(context);
/*  706 */       return null;
/*      */     } 
/*      */     
/*  709 */     return bitmap;
/*      */   }
/*      */   
/*      */   private static int getImageRotation(Context context, Map imageIntent) {
/*  713 */     Uri imageUri = getImageUri(imageIntent);
/*  714 */     File imageFile = new File(imageUri.getPath());
/*  715 */     ExifInterface exif = null;
/*  716 */     ParcelFileDescriptor pfd = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void jumpToRect(PDFViewCtrl pdfViewCtrl, Rect rect, int pageNum) {
/*      */     try {
/*  791 */       pdfViewCtrl.setCurrentPage(pageNum);
/*  792 */       int color = pdfViewCtrl.getContext().getResources().getColor(R.color.annotation_flashing_box);
/*  793 */       View flashingView = createFlashingView(pdfViewCtrl, rect, pageNum, color);
/*      */       
/*  795 */       animateView(flashingView, pdfViewCtrl);
/*  796 */     } catch (Exception e) {
/*  797 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void jumpToAnnotation(PDFViewCtrl pdfViewCtrl, Annot annot, int pageNum) {
/*      */     try {
/*  810 */       pdfViewCtrl.setCurrentPage(pageNum);
/*  811 */       Rect annotRect = pdfViewCtrl.getPageRectForAnnot(annot, pageNum);
/*  812 */       int color = pdfViewCtrl.getContext().getResources().getColor(R.color.annotation_flashing_box);
/*  813 */       View flashingView = createFlashingView(pdfViewCtrl, annotRect, pageNum, color);
/*      */       
/*  815 */       animateView(flashingView, pdfViewCtrl);
/*  816 */     } catch (Exception e) {
/*  817 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void animateUndoRedo(PDFViewCtrl pdfViewCtrl, Rect annotRect, int pageNum) {
/*  829 */     pdfViewCtrl.setCurrentPage(pageNum);
/*  830 */     int color = pdfViewCtrl.getContext().getResources().getColor(R.color.undo_redo_flashing_box);
/*  831 */     View flashingView = createFlashingView(pdfViewCtrl, annotRect, pageNum, color);
/*  832 */     animateUndoRedoView(flashingView, pdfViewCtrl);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void animateScreenRect(PDFViewCtrl pdfViewCtrl, Rect screenRect) {
/*  837 */     int color = pdfViewCtrl.getContext().getResources().getColor(R.color.annotation_flashing_box);
/*      */     
/*  839 */     View flashingView = new View(pdfViewCtrl.getContext());
/*  840 */     flashingView.setBackgroundColor(color);
/*      */     
/*      */     try {
/*  843 */       double left = Math.min(screenRect.getX1(), screenRect.getX2());
/*  844 */       double top = Math.min(screenRect.getY1(), screenRect.getY2());
/*  845 */       double right = Math.max(screenRect.getX1(), screenRect.getX2());
/*  846 */       double bottom = Math.max(screenRect.getY1(), screenRect.getY2());
/*      */       
/*  848 */       int sx = pdfViewCtrl.getScrollX();
/*  849 */       int sy = pdfViewCtrl.getScrollY();
/*      */       
/*  851 */       Rect flashingRect = new Rect(left + sx, top + sy, right + sx, bottom + sy);
/*  852 */       flashingRect.normalize();
/*      */       
/*  854 */       int flashingRectLeft = (int)flashingRect.getX1();
/*  855 */       int flashingRectTop = (int)flashingRect.getY1();
/*  856 */       int flashingRectRight = (int)flashingRect.getX2();
/*  857 */       int flashingRectBottom = (int)flashingRect.getY2();
/*  858 */       flashingView.layout(flashingRectLeft, flashingRectTop, flashingRectRight, flashingRectBottom);
/*  859 */       pdfViewCtrl.addView(flashingView);
/*  860 */       animateView(flashingView, pdfViewCtrl);
/*  861 */     } catch (Exception ex) {
/*  862 */       ex.printStackTrace();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Rect scrollToAnnotRect(PDFViewCtrl pdfViewCtrl, Rect annotRect, int pageNum) throws PDFNetException {
/*      */     int x, y;
/*  877 */     double[] pts1 = { 0.0D, 0.0D };
/*  878 */     double[] pts2 = { 0.0D, 0.0D };
/*      */     try {
/*  880 */       double annotX1 = annotRect.getX1();
/*  881 */       double annotY1 = annotRect.getY1();
/*  882 */       double annotX2 = annotRect.getX2();
/*  883 */       double annotY2 = annotRect.getY2();
/*      */ 
/*      */       
/*  886 */       int MIN_LENGTH = 10;
/*  887 */       double d1 = annotRect.getWidth();
/*  888 */       if (d1 < 10.0D) {
/*  889 */         annotX1 -= (10.0D - d1) / 2.0D;
/*  890 */         annotX2 += (10.0D - d1) / 2.0D;
/*      */       } 
/*  892 */       double d2 = annotRect.getHeight();
/*  893 */       if (d2 < 10.0D) {
/*  894 */         annotY1 -= (10.0D - d2) / 2.0D;
/*  895 */         annotY2 += (10.0D - d2) / 2.0D;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  902 */       pts1 = pdfViewCtrl.convPagePtToScreenPt(annotX1, annotY1, pageNum);
/*      */       
/*  904 */       pts2 = pdfViewCtrl.convPagePtToScreenPt(annotX2, annotY2, pageNum);
/*  905 */     } catch (PDFNetException e) {
/*  906 */       AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  911 */     double top = (pts1[1] < pts2[1]) ? pts1[1] : pts2[1];
/*  912 */     double bottom = (pts1[1] > pts2[1]) ? pts1[1] : pts2[1];
/*  913 */     double left = (pts1[0] < pts2[0]) ? pts1[0] : pts2[0];
/*  914 */     double right = (pts1[0] > pts2[0]) ? pts1[0] : pts2[0];
/*      */ 
/*      */     
/*  917 */     double screenHeight = pdfViewCtrl.getHeight();
/*  918 */     double annotHeight = Math.abs(top - bottom);
/*  919 */     double offsetY = Math.abs(screenHeight - annotHeight) / 2.0D;
/*  920 */     if (screenHeight > annotHeight) {
/*  921 */       y = pdfViewCtrl.getScrollY() + (int)Math.round(top - offsetY);
/*  922 */       top = offsetY;
/*      */     } else {
/*  924 */       y = pdfViewCtrl.getScrollY() + (int)Math.round(top + offsetY);
/*  925 */       top = -offsetY;
/*      */     } 
/*  927 */     if (y < 0) {
/*  928 */       top += y;
/*      */     }
/*  930 */     int canvasOffsetY = (int)(y + screenHeight - pdfViewCtrl.getViewCanvasHeight());
/*  931 */     if (canvasOffsetY > 0) {
/*  932 */       top += (y - ((y > canvasOffsetY) ? (y - canvasOffsetY) : 0));
/*  933 */       y -= canvasOffsetY;
/*      */     } 
/*  935 */     bottom = top + annotHeight;
/*  936 */     if (y < 0) {
/*  937 */       y = 0;
/*      */     }
/*      */     
/*  940 */     double screenWidth = pdfViewCtrl.getWidth();
/*  941 */     double annotWidth = Math.abs(right - left);
/*  942 */     double offsetX = Math.abs(screenWidth - annotWidth) / 2.0D;
/*  943 */     if (screenWidth > annotWidth) {
/*  944 */       x = pdfViewCtrl.getScrollX() + (int)Math.round(left - offsetX);
/*  945 */       left = offsetX;
/*      */     } else {
/*  947 */       x = pdfViewCtrl.getScrollX() + (int)Math.round(left + offsetX);
/*  948 */       left = -offsetX;
/*      */     } 
/*  950 */     int scrollOffset = pdfViewCtrl.getScrollOffsetForCanvasId(pdfViewCtrl.getCurCanvasId());
/*  951 */     if (pdfViewCtrl.isMaintainZoomEnabled() || pdfViewCtrl.getScrollX() == scrollOffset)
/*      */     {
/*      */       
/*  954 */       x -= scrollOffset;
/*      */     }
/*  956 */     if (x < 0) {
/*  957 */       left += x;
/*      */     }
/*  959 */     int canvasOffsetX = (int)(x + screenWidth - pdfViewCtrl.getViewCanvasWidth());
/*  960 */     if (canvasOffsetX > 0) {
/*  961 */       left += (x - ((x > canvasOffsetX) ? (x - canvasOffsetX) : 0));
/*  962 */       x -= canvasOffsetX;
/*      */     } 
/*  964 */     right = left + annotWidth;
/*  965 */     if (x < 0) {
/*  966 */       x = 0;
/*      */     }
/*  968 */     int sx = pdfViewCtrl.getScrollX();
/*  969 */     int sy = pdfViewCtrl.getScrollY();
/*  970 */     int leftBound = (int)(x - annotWidth / 2.0D);
/*  971 */     int rightBound = (int)(x + annotWidth / 2.0D);
/*  972 */     int topBound = (int)(y - annotHeight / 2.0D);
/*  973 */     int bottomBound = y + (int)annotHeight / 2;
/*      */     
/*  975 */     sx -= scrollOffset;
/*      */     
/*  977 */     if (rightBound >= sx + screenWidth / 2.0D) {
/*  978 */       int dist = rightBound - sx - (int)screenWidth / 2;
/*  979 */       sx += dist;
/*      */     } 
/*  981 */     if (leftBound <= sx - screenWidth / 2.0D) {
/*  982 */       int dist = sx - (int)screenWidth / 2 - leftBound;
/*  983 */       sx -= dist;
/*      */     } 
/*      */     
/*  986 */     if (bottomBound >= sy + screenHeight / 2.0D) {
/*  987 */       int dist = bottomBound - sy;
/*  988 */       sy += dist;
/*      */     } 
/*  990 */     if (topBound <= sy - screenHeight / 2.0D || y <= sy - screenHeight / 4.0D) {
/*  991 */       int dist = sy - (int)screenHeight / 4 - topBound;
/*  992 */       sy -= dist;
/*      */     } 
/*  994 */     if (x == 0) {
/*  995 */       sx = x;
/*      */     }
/*  997 */     if (y == 0) {
/*  998 */       sy = y;
/*      */     }
/* 1000 */     pdfViewCtrl.scrollTo(sx, sy);
/*      */ 
/*      */ 
/*      */     
/* 1004 */     sx = x + scrollOffset;
/* 1005 */     sy = y;
/*      */ 
/*      */     
/* 1008 */     return new Rect(left + sx, top + sy, right + sx, bottom + sy);
/*      */   }
/*      */ 
/*      */   
/*      */   private static View createFlashingView(PDFViewCtrl pdfViewCtrl, Rect annotRect, int pageNum, int color) {
/* 1013 */     View flashingView = new View(pdfViewCtrl.getContext());
/* 1014 */     flashingView.setBackgroundColor(color);
/*      */     
/*      */     try {
/* 1017 */       Rect flashingRect = scrollToAnnotRect(pdfViewCtrl, annotRect, pageNum);
/* 1018 */       flashingRect.normalize();
/*      */ 
/*      */       
/* 1021 */       int flashingRectLeft = (int)flashingRect.getX1();
/* 1022 */       int flashingRectTop = (int)flashingRect.getY1();
/* 1023 */       int flashingRectRight = (int)flashingRect.getX2();
/* 1024 */       int flashingRectBottom = (int)flashingRect.getY2();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1029 */       flashingView.layout(flashingRectLeft, flashingRectTop, flashingRectRight, flashingRectBottom);
/* 1030 */       pdfViewCtrl.addView(flashingView);
/* 1031 */     } catch (PDFNetException ex) {
/* 1032 */       AnalyticsHandlerAdapter.getInstance().sendException((Exception)ex);
/*      */     } 
/*      */     
/* 1035 */     return flashingView;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void animateView(final View view, final PDFViewCtrl pdfViewCtrl) {
/* 1049 */     Animator.AnimatorListener animListener = new Animator.AnimatorListener()
/*      */       {
/*      */         public void onAnimationStart(Animator animation) {}
/*      */ 
/*      */ 
/*      */         
/*      */         public void onAnimationRepeat(Animator animation) {}
/*      */ 
/*      */ 
/*      */         
/*      */         public void onAnimationEnd(Animator animation) {
/* 1060 */           pdfViewCtrl.removeView(view);
/*      */         }
/*      */ 
/*      */         
/*      */         public void onAnimationCancel(Animator animation) {
/* 1065 */           pdfViewCtrl.removeView(view);
/*      */         }
/*      */       };
/*      */ 
/*      */     
/* 1070 */     Animator fader = createAlphaAnimator(view, animListener);
/*      */ 
/*      */ 
/*      */     
/* 1074 */     AnimatorSet animation = new AnimatorSet();
/* 1075 */     animation.playTogether(new Animator[] { fader });
/* 1076 */     animation.start();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void animateUndoRedoView(final View view, final PDFViewCtrl pdfViewCtrl) {
/* 1083 */     Animator.AnimatorListener animListener = new Animator.AnimatorListener()
/*      */       {
/*      */         public void onAnimationStart(Animator animation) {}
/*      */ 
/*      */ 
/*      */         
/*      */         public void onAnimationRepeat(Animator animation) {}
/*      */ 
/*      */ 
/*      */         
/*      */         public void onAnimationEnd(Animator animation) {
/* 1094 */           pdfViewCtrl.removeView(view);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public void onAnimationCancel(Animator animation) {}
/*      */       };
/* 1103 */     ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "alpha", new float[] { 0.0F, 0.7F, 0.0F });
/*      */     
/* 1105 */     objectAnimator.setDuration(500L);
/* 1106 */     objectAnimator.setEvaluator((TypeEvaluator)new FloatEvaluator());
/* 1107 */     objectAnimator.addListener(animListener);
/*      */ 
/*      */ 
/*      */     
/* 1111 */     AnimatorSet animation = new AnimatorSet();
/* 1112 */     animation.playTogether(new Animator[] { (Animator)objectAnimator });
/* 1113 */     animation.start();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Animator createAlphaAnimator(View view, Animator.AnimatorListener listener) {
/* 1126 */     if (view == null) {
/* 1127 */       return null;
/*      */     }
/*      */     
/* 1130 */     ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "alpha", new float[] { 0.0F, 1.0F, 0.4F, 1.0F, 0.0F });
/* 1131 */     objectAnimator.setDuration(1500L);
/* 1132 */     objectAnimator.setEvaluator((TypeEvaluator)new FloatEvaluator());
/* 1133 */     if (listener != null) {
/* 1134 */       objectAnimator.addListener(listener);
/*      */     }
/* 1136 */     return (Animator)objectAnimator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public static Annot getAnnotById(PDFViewCtrl ctrl, String id, int pageNum) {
/* 1149 */     if (null == ctrl || null == ctrl.getDoc() || null == id) {
/* 1150 */       return null;
/*      */     }
/*      */     
/* 1153 */     boolean shouldUnlockRead = false;
/*      */     try {
/* 1155 */       ctrl.docLockRead();
/* 1156 */       shouldUnlockRead = true;
/* 1157 */       ArrayList<Annot> annots = ctrl.getAnnotationsOnPage(pageNum);
/* 1158 */       for (Annot annotation : annots) {
/* 1159 */         if (annotation != null && annotation.isValid() && annotation.getUniqueID() != null) {
/* 1160 */           String annotId = annotation.getUniqueID().getAsPDFText();
/* 1161 */           if (id.equals(annotId)) {
/* 1162 */             return annotation;
/*      */           }
/*      */         } 
/*      */       } 
/* 1166 */     } catch (Exception e) {
/* 1167 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } finally {
/* 1169 */       if (shouldUnlockRead) {
/* 1170 */         ctrl.docUnlockRead();
/*      */       }
/*      */     } 
/* 1173 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getSelectedString(PDFViewCtrl pdfViewCtrl) {
/* 1183 */     StringBuilder text = new StringBuilder();
/* 1184 */     if (pdfViewCtrl.hasSelection()) {
/* 1185 */       int sel_pg_begin = pdfViewCtrl.getSelectionBeginPage();
/* 1186 */       int sel_pg_end = pdfViewCtrl.getSelectionEndPage();
/* 1187 */       boolean shouldUnlockRead = false;
/*      */       try {
/* 1189 */         pdfViewCtrl.docLockRead();
/* 1190 */         shouldUnlockRead = true;
/* 1191 */         for (int pg = sel_pg_begin; pg <= sel_pg_end; pg++) {
/* 1192 */           PDFViewCtrl.Selection sel = pdfViewCtrl.getSelection(pg);
/* 1193 */           String t = sel.getAsUnicode();
/* 1194 */           text.append(t);
/*      */         } 
/* 1196 */       } catch (Exception e) {
/* 1197 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */       } finally {
/* 1199 */         if (shouldUnlockRead) {
/* 1200 */           pdfViewCtrl.docUnlockRead();
/*      */         }
/*      */       } 
/*      */     } 
/* 1204 */     return text.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BitmapDrawable getBitmapDrawable(@NonNull Context context, int drawableId, int width, int height, int targetColor, boolean roundedCorner) {
/* 1213 */     return getBitmapDrawable(context, drawableId, width, height, targetColor, roundedCorner, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BitmapDrawable getBitmapDrawable(@NonNull Context context, int drawableId, int width, int height, int targetColor, boolean roundedCorner, boolean nonWhiteOnly) {
/* 1223 */     Resources resources = context.getResources();
/* 1224 */     Bitmap icon = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
/* 1225 */     Drawable drawable = Utils.getDrawable(context, drawableId);
/* 1226 */     Canvas canvas = new Canvas(icon);
/* 1227 */     if (roundedCorner) {
/*      */       
/* 1229 */       Path clipPath = new Path();
/* 1230 */       RectF rect = new RectF(0.0F, 0.0F, width, height);
/* 1231 */       float corner = Utils.convDp2Pix(context, 4.0F);
/* 1232 */       clipPath.addRoundRect(rect, corner, corner, Path.Direction.CW);
/* 1233 */       canvas.clipPath(clipPath);
/*      */     } 
/* 1235 */     if (drawable != null) {
/* 1236 */       drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
/* 1237 */       drawable.draw(canvas);
/*      */     } 
/*      */     
/* 1240 */     if (nonWhiteOnly) {
/* 1241 */       int originalColor = icon.getPixel(width / 2, height / 2);
/* 1242 */       if (originalColor != targetColor)
/*      */       {
/* 1244 */         icon = Utils.replace9PatchColor(icon, targetColor);
/*      */       }
/*      */     } else {
/* 1247 */       icon = Utils.replace9PatchColor(icon, icon.getPixel(width / 2, height / 2), targetColor);
/*      */     } 
/* 1249 */     return new BitmapDrawable(resources, icon);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static StateListDrawable createBackgroundSelector(Drawable drawable) {
/* 1258 */     return (new StateListDrawableBuilder()).setPressedDrawable(drawable).setSelectedDrawable(drawable).setHoveredDrawable(drawable).setNormalDrawable((Drawable)new ColorDrawable(0)).build();
/*      */   }
/*      */   
/*      */   public static int getLastPageForURL(Context context, String url) {
/* 1262 */     if (context == null || Utils.isNullOrEmpty(url)) {
/* 1263 */       return -1;
/*      */     }
/* 1265 */     String cache = PdfViewCtrlSettingsManager.getOpenUrlAsyncCache(context);
/* 1266 */     if (!Utils.isNullOrEmpty(cache)) {
/*      */       try {
/* 1268 */         LinkedHashMap<String, String> cacheMap = Utils.convJSONToMap(cache);
/* 1269 */         String result = cacheMap.get(url);
/* 1270 */         if (!Utils.isNullOrEmpty(result)) {
/* 1271 */           int page = Integer.parseInt(result);
/* 1272 */           if (page > 0) {
/* 1273 */             return page;
/*      */           }
/*      */         } 
/* 1276 */       } catch (Exception ex) {
/* 1277 */         AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*      */       } 
/*      */     }
/* 1280 */     return -1;
/*      */   }
/*      */   
/*      */   public static void setLastPageForURL(Context context, String url, int page) {
/* 1284 */     if (context == null || Utils.isNullOrEmpty(url) || page < 1) {
/*      */       return;
/*      */     }
/* 1287 */     int max = 25;
/* 1288 */     String cache = PdfViewCtrlSettingsManager.getOpenUrlAsyncCache(context);
/*      */     try {
/* 1290 */       LinkedHashMap<String, String> cacheMap = Utils.convJSONToMap(cache);
/*      */       
/* 1292 */       if (cacheMap.size() > max) {
/* 1293 */         String firstKey = cacheMap.keySet().iterator().next();
/* 1294 */         cacheMap.remove(firstKey);
/*      */       } 
/* 1296 */       cacheMap.put(url, String.valueOf(page));
/* 1297 */       JSONObject cacheJson = Utils.convMapToJSON(cacheMap);
/* 1298 */       PdfViewCtrlSettingsManager.setOpenUrlAsyncCache(context, cacheJson.toString());
/* 1299 */     } catch (Exception ex) {
/* 1300 */       AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void passwordDoc(PDFDoc doc, String password) {
/* 1305 */     if (doc != null) {
/* 1306 */       boolean shouldUnlock = false;
/*      */       try {
/* 1308 */         doc.lock();
/* 1309 */         shouldUnlock = true;
/*      */         
/* 1311 */         doc.removeSecurity();
/* 1312 */         if (!Utils.isNullOrEmpty(password)) {
/*      */           
/* 1314 */           SecurityHandler new_handler = new SecurityHandler(3);
/* 1315 */           new_handler.changeUserPassword(password);
/*      */ 
/*      */           
/* 1318 */           new_handler.setPermission(4, true);
/*      */ 
/*      */           
/* 1321 */           doc.setSecurityHandler(new_handler);
/*      */         } 
/* 1323 */       } catch (Exception e) {
/* 1324 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */       } finally {
/* 1326 */         if (shouldUnlock) {
/* 1327 */           Utils.unlockQuietly(doc);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void flattenDoc(PDFDoc doc) {
/* 1334 */     if (doc != null) {
/* 1335 */       boolean shouldUnlock = false;
/*      */       try {
/* 1337 */         doc.lock();
/* 1338 */         shouldUnlock = true;
/* 1339 */         PDFDoc.FlattenMode[] flattenModes = { PDFDoc.FlattenMode.ANNOTS, PDFDoc.FlattenMode.FORMS };
/* 1340 */         doc.flattenAnnotationsAdvanced(flattenModes);
/* 1341 */       } catch (Exception e) {
/* 1342 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */       } finally {
/* 1344 */         if (shouldUnlock) {
/* 1345 */           Utils.unlockQuietly(doc);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public static Single<String> extractFileFromPortfolioDisposable(final int type, final Context context, final PDFDoc portfolioDoc, final String destFolderPath, final String fileName) {
/* 1352 */     return Single.create(new SingleOnSubscribe<String>()
/*      */         {
/*      */           public void subscribe(SingleEmitter<String> emitter) throws Exception {
/* 1355 */             boolean shouldUnlockRead = false;
/*      */             try {
/* 1357 */               portfolioDoc.lockRead();
/* 1358 */               shouldUnlockRead = true;
/* 1359 */               emitter.onSuccess(ViewerUtils.extractFileFromPortfolio(type, context, portfolioDoc, destFolderPath, fileName));
/* 1360 */             } catch (Exception e) {
/* 1361 */               AnalyticsHandlerAdapter.getInstance().sendException(e);
/* 1362 */               emitter.tryOnError(e);
/*      */             } finally {
/* 1364 */               if (shouldUnlockRead) {
/* 1365 */                 portfolioDoc.unlockRead();
/*      */               }
/*      */             } 
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */   
/*      */   public static String extractFileFromPortfolio(int type, Context context, PDFDoc portfolioDoc, String destFolderPath, String fileName) {
/* 1374 */     if (portfolioDoc == null) {
/* 1375 */       return "";
/*      */     }
/* 1377 */     String retValue = "";
/* 1378 */     SecondaryFileFilter filter = null;
/*      */     try {
/* 1380 */       NameTree files = NameTree.find((Doc)portfolioDoc.getSDFDoc(), "EmbeddedFiles");
/* 1381 */       if (files.isValid()) {
/*      */         
/* 1383 */         NameTreeIterator iter = files.getIterator();
/* 1384 */         while (iter.hasNext()) {
/* 1385 */           String entryName = iter.key().getAsPDFText();
/* 1386 */           FileSpec file_spec = new FileSpec(iter.value());
/* 1387 */           if (file_spec.isValid()) {
/* 1388 */             entryName = file_spec.getFilePath();
/*      */           }
/* 1390 */           if (entryName.equalsIgnoreCase(fileName))
/* 1391 */             if (type == 1) {
/*      */               
/* 1393 */               ExternalFileInfo newFileInfo = null;
/*      */               
/* 1395 */               for (int i = 0; i < 100; i++) {
/* 1396 */                 String newFileName; Uri parentUri = Uri.parse(destFolderPath);
/* 1397 */                 ExternalFileInfo parentUriInfo = Utils.buildExternalFile(context, parentUri);
/* 1398 */                 if (parentUriInfo == null) {
/* 1399 */                   return "";
/*      */                 }
/*      */                 
/* 1402 */                 if (i == 0) {
/* 1403 */                   newFileName = fileName;
/*      */                 } else {
/* 1405 */                   String extension = FilenameUtils.getExtension(fileName);
/* 1406 */                   newFileName = FilenameUtils.removeExtension(fileName) + " (" + String.valueOf(i) + ")." + extension;
/*      */                 } 
/*      */                 
/* 1409 */                 if (parentUriInfo.findFile(newFileName) == null) {
/* 1410 */                   Uri tempUri = ExternalFileInfo.appendPathComponent(parentUri, newFileName);
/* 1411 */                   String extension = MimeTypeMap.getFileExtensionFromUrl(tempUri.toString());
/* 1412 */                   MimeTypeMap mime = MimeTypeMap.getSingleton();
/* 1413 */                   String newFileType = mime.getMimeTypeFromExtension(extension);
/* 1414 */                   newFileInfo = parentUriInfo.createFile(newFileType, newFileName);
/*      */                   break;
/*      */                 } 
/*      */               } 
/* 1418 */               if (newFileInfo != null) {
/* 1419 */                 retValue = newFileInfo.getAbsolutePath();
/*      */                 
/* 1421 */                 Filter stm = file_spec.getFileData();
/* 1422 */                 if (stm != null) {
/* 1423 */                   filter = new SecondaryFileFilter(context, newFileInfo.getUri(), 1);
/* 1424 */                   FilterWriter filterWriter = new FilterWriter((Filter)filter);
/* 1425 */                   FilterReader filterReader = new FilterReader(stm);
/* 1426 */                   filterWriter.writeFilter(filterReader);
/* 1427 */                   filterWriter.flushAll();
/*      */                   break;
/*      */                 } 
/*      */               } 
/*      */             } else {
/* 1432 */               String fullFileName = (new File(destFolderPath, fileName)).getAbsolutePath();
/* 1433 */               String newFileName = fullFileName;
/*      */               int i;
/* 1435 */               for (i = 1; i < 100; ) {
/* 1436 */                 File file = new File(newFileName);
/* 1437 */                 if (file.exists()) {
/*      */ 
/*      */                   
/* 1440 */                   String extension = FilenameUtils.getExtension(fullFileName);
/*      */                   
/* 1442 */                   newFileName = FilenameUtils.removeExtension(fullFileName) + " (" + String.valueOf(i) + ")." + extension;
/*      */                   
/*      */                   i++;
/*      */                 } 
/*      */               } 
/* 1447 */               if (i < 100) {
/* 1448 */                 retValue = newFileName;
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 1453 */                 Filter stm = file_spec.getFileData();
/* 1454 */                 if (stm != null) {
/* 1455 */                   stm.writeToFile(newFileName, false); break;
/*      */                 } 
/*      */               } else {
/*      */                 break;
/*      */               } 
/* 1460 */             }   iter.next();
/*      */         } 
/*      */       } 
/* 1463 */     } catch (Exception e) {
/* 1464 */       retValue = "";
/*      */     } finally {
/* 1466 */       Utils.closeQuietly(filter);
/*      */     } 
/*      */     
/* 1469 */     return retValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getMenuEditorItemsJSON(@NonNull ArrayList<MenuEditorItem> newMenuItems) throws JSONException {
/* 1477 */     int group = -1;
/* 1478 */     JSONObject menuJson = new JSONObject();
/* 1479 */     JSONArray shownArray = new JSONArray();
/* 1480 */     JSONArray overflowArray = new JSONArray();
/* 1481 */     for (int index = 0; index < newMenuItems.size(); index++) {
/* 1482 */       MenuEditorItem newItem = newMenuItems.get(index);
/* 1483 */       if (newItem.isHeader()) {
/* 1484 */         MenuEditorItemHeader header = (MenuEditorItemHeader)newItem;
/* 1485 */         group = header.getGroup();
/*      */       } else {
/* 1487 */         MenuEditorItemContent itemContent = (MenuEditorItemContent)newItem;
/* 1488 */         if (group == 0) {
/* 1489 */           JSONObject item = new JSONObject();
/* 1490 */           item.put("id", itemContent.getId());
/* 1491 */           shownArray.put(item);
/*      */         } else {
/* 1493 */           JSONObject item = new JSONObject();
/* 1494 */           item.put("id", itemContent.getId());
/* 1495 */           overflowArray.put(item);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1499 */     menuJson.put("ifroom", shownArray);
/* 1500 */     menuJson.put("never", overflowArray);
/*      */     
/* 1502 */     return menuJson.toString();
/*      */   }
/*      */   
/*      */   public static ArrayList<MenuEditorItem> getMenuEditorItemsArray(String savedToolbarMenu) throws JSONException {
/* 1506 */     ArrayList<MenuEditorItem> menuEditorItems = new ArrayList<>();
/* 1507 */     JSONObject menuJson = new JSONObject(savedToolbarMenu);
/* 1508 */     JSONArray shownArray = menuJson.getJSONArray("ifroom");
/* 1509 */     JSONArray overflowArray = menuJson.getJSONArray("never");
/* 1510 */     MenuEditorItemHeader header1 = new MenuEditorItemHeader(0, "placeholder", "");
/* 1511 */     menuEditorItems.add(header1);
/* 1512 */     for (int i = 0; i < shownArray.length(); i++) {
/* 1513 */       JSONObject object = shownArray.getJSONObject(i);
/* 1514 */       int id = object.getInt("id");
/* 1515 */       MenuEditorItemContent content = new MenuEditorItemContent(id, "placeholder", 0);
/* 1516 */       menuEditorItems.add(content);
/*      */     } 
/* 1518 */     MenuEditorItemHeader header2 = new MenuEditorItemHeader(1, "placeholder", "");
/* 1519 */     menuEditorItems.add(header2);
/* 1520 */     for (int j = 0; j < overflowArray.length(); j++) {
/* 1521 */       JSONObject object = overflowArray.getJSONObject(j);
/* 1522 */       int id = object.getInt("id");
/* 1523 */       MenuEditorItemContent content = new MenuEditorItemContent(id, "placeholder", 0);
/* 1524 */       menuEditorItems.add(content);
/*      */     } 
/*      */     
/* 1527 */     return menuEditorItems;
/*      */   }
/*      */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pd\\utils\ViewerUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */