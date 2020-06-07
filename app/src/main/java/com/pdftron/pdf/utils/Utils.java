/*      */ package com.pdftron.pdf.utils;
/*      */ 
/*      */ import android.annotation.TargetApi;
/*      */ import android.app.Activity;
/*      */ import android.app.ActivityManager;
/*      */ import android.app.AlertDialog;
/*      */ import android.content.ClipData;
/*      */ import android.content.ClipboardManager;
/*      */ import android.content.ContentResolver;
/*      */ import android.content.ContentUris;
/*      */ import android.content.Context;
/*      */ import android.content.DialogInterface;
/*      */ import android.content.Intent;
/*      */ import android.content.UriPermission;
/*      */ import android.content.pm.PackageManager;
/*      */ import android.content.res.Configuration;
/*      */ import android.content.res.Resources;
/*      */ import android.content.res.TypedArray;
/*      */ import android.content.res.XmlResourceParser;
/*      */ import android.database.Cursor;
/*      */ import android.graphics.Bitmap;
/*      */ import android.graphics.BitmapFactory;
/*      */ import android.graphics.Canvas;
/*      */ import android.graphics.Color;
/*      */ import android.graphics.ColorFilter;
/*      */ import android.graphics.Paint;
/*      */ import android.graphics.Point;
/*      */ import android.graphics.PointF;
/*      */ import android.graphics.PorterDuff;
/*      */ import android.graphics.PorterDuffColorFilter;
/*      */ import android.graphics.RectF;
/*      */ import android.graphics.drawable.BitmapDrawable;
/*      */ import android.graphics.drawable.Drawable;
/*      */ import android.graphics.drawable.StateListDrawable;
/*      */ import android.graphics.drawable.VectorDrawable;
/*      */ import android.net.Uri;
/*      */ import android.os.Build;
/*      */ import android.os.Debug;
/*      */ import android.os.Environment;
/*      */ import android.os.Looper;
/*      */ import android.os.ParcelFileDescriptor;
/*      */ import android.os.Parcelable;
/*      */ import android.os.StatFs;
/*      */ import android.provider.DocumentsContract;
/*      */ import android.provider.MediaStore;
/*      */ import android.text.Html;
/*      */ import android.text.method.LinkMovementMethod;
/*      */ import android.util.Base64;
/*      */ import android.util.Log;
/*      */ import android.util.TypedValue;
/*      */ import android.view.Display;
/*      */ import android.view.View;
/*      */ import android.view.WindowManager;
/*      */ import android.view.inputmethod.InputMethodManager;
/*      */ import android.webkit.MimeTypeMap;
/*      */ import android.webkit.URLUtil;
/*      */ import android.widget.TextView;
/*      */ import androidx.annotation.ColorInt;
/*      */ import androidx.annotation.DrawableRes;
/*      */ import androidx.annotation.NonNull;
/*      */ import androidx.annotation.Nullable;
/*      */ import androidx.annotation.RequiresApi;
/*      */ import androidx.appcompat.app.AppCompatActivity;
/*      */ import androidx.appcompat.content.res.AppCompatResources;
/*      */ import androidx.core.app.ActivityCompat;
/*      */ import androidx.core.app.ShareCompat;
/*      */ import androidx.core.content.FileProvider;
/*      */ import androidx.core.graphics.ColorUtils;
/*      */ import androidx.fragment.app.FragmentActivity;
/*      */ import androidx.lifecycle.Lifecycle;
/*      */ import androidx.recyclerview.widget.RecyclerView;
/*      */ import com.google.android.material.snackbar.Snackbar;
/*      */ import com.pdftron.common.PDFNetException;
/*      */ import com.pdftron.fdf.FDFDoc;
/*      */ import com.pdftron.filters.Filter;
/*      */ import com.pdftron.filters.SecondaryFileFilter;
/*      */ import com.pdftron.pdf.Annot;
/*      */ import com.pdftron.pdf.Bookmark;
/*      */ import com.pdftron.pdf.ColorPt;
/*      */ import com.pdftron.pdf.FileSpec;
/*      */ import com.pdftron.pdf.PDFDoc;
/*      */ import com.pdftron.pdf.PDFViewCtrl;
/*      */ import com.pdftron.pdf.Page;
/*      */ import com.pdftron.pdf.Point;
/*      */ import com.pdftron.pdf.Rect;
/*      */ import com.pdftron.pdf.annots.Markup;
/*      */ import com.pdftron.pdf.annots.Polygon;
/*      */ import com.pdftron.pdf.annots.Popup;
/*      */ import com.pdftron.pdf.model.ExternalFileInfo;
/*      */ import com.pdftron.pdf.model.FileInfo;
/*      */ import com.pdftron.pdf.model.PdfViewCtrlTabInfo;
/*      */ import com.pdftron.pdf.tools.R;
/*      */ import com.pdftron.pdf.tools.ToolManager;
/*      */ import com.pdftron.sdf.Doc;
/*      */ import com.pdftron.sdf.NameTree;
/*      */ import com.pdftron.sdf.NameTreeIterator;
/*      */ import io.reactivex.Single;
/*      */ import io.reactivex.SingleEmitter;
/*      */ import io.reactivex.SingleOnSubscribe;
/*      */ import java.io.BufferedOutputStream;
/*      */ import java.io.Closeable;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.io.RandomAccessFile;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.net.HttpURLConnection;
/*      */ import java.net.URL;
/*      */ import java.text.Bidi;
/*      */ import java.text.NumberFormat;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Calendar;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Random;
/*      */ import javax.crypto.Cipher;
/*      */ import javax.crypto.SecretKey;
/*      */ import javax.crypto.SecretKeyFactory;
/*      */ import javax.crypto.spec.DESKeySpec;
/*      */ import org.apache.commons.io.FileUtils;
/*      */ import org.apache.commons.io.FilenameUtils;
/*      */ import org.apache.commons.io.IOUtils;
/*      */ import org.apache.commons.lang3.ArrayUtils;
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
/*      */ public class Utils
/*      */ {
/*  154 */   private static final String TAG = Utils.class.getName();
/*      */   
/*      */   public static final int MAX_NUM_DUPLICATED_FILES = 100;
/*      */   
/*  158 */   private static String[] PERMISSIONS_STORAGE = new String[] { "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE" };
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int URI_TYPE_UNKNOWN = 0;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int URI_TYPE_FILE = 1;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int URI_TYPE_DOCUMENT = 2;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int URI_TYPE_TREE = 3;
/*      */ 
/*      */   
/*      */   private static final String URI_PATH_PREFIX_DOCUMENT = "/document/";
/*      */ 
/*      */   
/*      */   private static final String URI_PATH_PREFIX_TREE = "/tree/";
/*      */ 
/*      */   
/*      */   public static final double INTERSECTION_EPSILON = 1.0E-30D;
/*      */ 
/*      */   
/*      */   public static final double vertex_dist_epsilon = 1.0E-8D;
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isJellyBeanMR1() {
/*  191 */     return (Build.VERSION.SDK_INT >= 17);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isJellyBeanMR2() {
/*  200 */     return (Build.VERSION.SDK_INT >= 18);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isKitKat() {
/*  209 */     return (Build.VERSION.SDK_INT >= 19);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isKitKatOnly() {
/*  218 */     return (Build.VERSION.SDK_INT == 19);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isLollipop() {
/*  227 */     return (Build.VERSION.SDK_INT >= 21);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isLollipopMR1() {
/*  237 */     return (Build.VERSION.SDK_INT >= 22);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isMarshmallow() {
/*  246 */     return (Build.VERSION.SDK_INT >= 23);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isNougat() {
/*  255 */     return (Build.VERSION.SDK_INT >= 24);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isOreo() {
/*  264 */     return (Build.VERSION.SDK_INT >= 26);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isPie() {
/*  273 */     return (Build.VERSION.SDK_INT >= 28);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isAndroidQ() {
/*  282 */     return (Build.VERSION.SDK_INT >= 29);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public static File copyAssetsToTempFolder(@NonNull Context context, @NonNull String path, boolean force) {
/*  294 */     InputStream inputStream = null;
/*  295 */     FileOutputStream outputStream = null;
/*      */     try {
/*  297 */       String fileName = FilenameUtils.getName(path);
/*  298 */       String assetKey = "android_asset/";
/*  299 */       int index = path.indexOf(assetKey);
/*  300 */       String realPath = path.substring(index + assetKey.length());
/*  301 */       File output = new File(context.getCacheDir(), fileName);
/*  302 */       if (!force) {
/*  303 */         String newPath = getFileNameNotInUse(output.getAbsolutePath());
/*  304 */         output = new File(newPath);
/*      */       } 
/*  306 */       inputStream = context.getResources().getAssets().open(realPath);
/*  307 */       outputStream = new FileOutputStream(output);
/*  308 */       IOUtils.copy(inputStream, outputStream);
/*  309 */       return output;
/*  310 */     } catch (Exception ex) {
/*  311 */       ex.printStackTrace();
/*      */     } finally {
/*  313 */       closeQuietly(inputStream);
/*  314 */       closeQuietly(outputStream);
/*      */     } 
/*  316 */     return null;
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
/*      */   public static String copyResourceToTempFolder(Context context, int resId, boolean force, String resourceName) throws PDFNetException {
/*  330 */     if (context == null) {
/*  331 */       throw new PDFNetException("", 0L, "com.pdftron.pdf.PDFNet", "copyResourceToTempFolder()", "Context cannot be null to initialize resource file.");
/*      */     }
/*  333 */     File resFile = new File(context.getFilesDir() + File.separator + "resourceName");
/*  334 */     if (!resFile.exists() || force) {
/*  335 */       File filesDir = context.getFilesDir();
/*  336 */       StatFs stat = new StatFs(filesDir.getPath());
/*  337 */       long size = stat.getAvailableBlocks() * stat.getBlockSize();
/*  338 */       if (size < 2903023L) {
/*  339 */         throw new PDFNetException("", 0L, "com.pdftron.pdf.PDFNet", "copyResourceToTempFolder()", "Not enough space available to copy resources file.");
/*      */       }
/*      */       
/*  342 */       Resources rs = context.getResources();
/*      */       
/*  344 */       InputStream inputStream = null;
/*  345 */       FileOutputStream outputStream = null;
/*      */       try {
/*  347 */         inputStream = rs.openRawResource(resId);
/*  348 */         outputStream = context.openFileOutput(resourceName, 0);
/*  349 */         byte[] buffer = new byte[1024];
/*      */         
/*      */         int read;
/*  352 */         while ((read = inputStream.read(buffer)) != -1) {
/*  353 */           outputStream.write(buffer, 0, read);
/*      */         }
/*  355 */         outputStream.flush();
/*  356 */       } catch (Resources.NotFoundException var13) {
/*  357 */         throw new PDFNetException("", 0L, "com.pdftron.pdf.PDFNet", "initializeResource()", "Resource file ID does not exist.");
/*  358 */       } catch (FileNotFoundException var14) {
/*  359 */         throw new PDFNetException("", 0L, "com.pdftron.pdf.PDFNet", "initializeResource()", "Resource file not found.");
/*  360 */       } catch (IOException var15) {
/*  361 */         throw new PDFNetException("", 0L, "com.pdftron.pdf.PDFNet", "initializeResource()", "Error writing resource file to internal storage.");
/*  362 */       } catch (Exception var16) {
/*  363 */         throw new PDFNetException("", 0L, "com.pdftron.pdf.PDFNet", "initializeResource()", "Unknown error.");
/*      */       } finally {
/*  365 */         closeQuietly(inputStream);
/*  366 */         closeQuietly(outputStream);
/*      */       } 
/*      */     } 
/*      */     
/*  370 */     return context.getFilesDir().getAbsolutePath();
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
/*      */   public static File copyResourceToLocal(Context context, int resId, String name, String ext) {
/*      */     File tempFile;
/*  385 */     InputStream is = null;
/*  386 */     OutputStream fos = null;
/*      */     
/*      */     try {
/*  389 */       tempFile = new File(context.getFilesDir(), name + ext);
/*  390 */       is = context.getResources().openRawResource(resId);
/*  391 */       fos = new FileOutputStream(tempFile);
/*  392 */       IOUtils.copy(is, fos);
/*  393 */     } catch (Exception e) {
/*  394 */       tempFile = null;
/*  395 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } finally {
/*  397 */       closeQuietly(fos);
/*  398 */       closeQuietly(is);
/*      */     } 
/*  400 */     return tempFile;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isTablet(Context context) {
/*  410 */     if (context == null) {
/*  411 */       return false;
/*      */     }
/*  413 */     boolean result = false;
/*      */     try {
/*  415 */       result = context.getResources().getBoolean(R.bool.isTablet);
/*  416 */     } catch (Exception exception) {}
/*      */ 
/*      */     
/*  419 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isLargeTablet(Context context) {
/*  429 */     if (context == null) {
/*  430 */       return false;
/*      */     }
/*  432 */     boolean result = false;
/*      */     try {
/*  434 */       result = context.getResources().getBoolean(R.bool.isLargeTablet);
/*  435 */     } catch (Exception exception) {}
/*      */     
/*  437 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isLargeScreen(Context context) {
/*  447 */     if (context == null) {
/*  448 */       return false;
/*      */     }
/*      */     
/*  451 */     if (!isChromebook(context) || !PdfViewCtrlSettingsManager.isDesktopUI(context)) {
/*  452 */       return false;
/*      */     }
/*      */     
/*  455 */     float minWidth = 1024.0F;
/*  456 */     float minHeight = 768.0F;
/*      */     
/*  458 */     Point pt = new Point();
/*  459 */     getDisplaySize(context, pt);
/*  460 */     float dpx = convPix2Dp(context, pt.x);
/*  461 */     float dpy = convPix2Dp(context, pt.y);
/*      */     
/*  463 */     return (dpx > minWidth && dpy > minHeight);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isLargeScreenWidth(Context context) {
/*  473 */     if (context == null) {
/*  474 */       return false;
/*      */     }
/*      */     
/*  477 */     if (!isChromebook(context) || !PdfViewCtrlSettingsManager.isDesktopUI(context)) {
/*  478 */       return false;
/*      */     }
/*      */     
/*  481 */     float minWidth = 1024.0F;
/*      */     
/*  483 */     Point pt = new Point();
/*  484 */     getDisplaySize(context, pt);
/*  485 */     float dpx = convPix2Dp(context, pt.x);
/*      */     
/*  487 */     return (dpx > minWidth);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isChromebook(Context context) {
/*  492 */     PackageManager pm = context.getPackageManager();
/*      */     
/*  494 */     if (pm.hasSystemFeature("android.hardware.type.pc")) {
/*  495 */       return true;
/*      */     }
/*  497 */     return (Build.BRAND != null && Build.BRAND.equals("chromium") && Build.MANUFACTURER != null && Build.MANUFACTURER
/*  498 */       .equals("chromium"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isPortrait(Context context) {
/*  508 */     return (context != null && (context.getResources().getConfiguration()).orientation == 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isLandscape(Context context) {
/*  519 */     return (context != null && (context.getResources().getConfiguration()).orientation == 2);
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
/*      */   public static Rect convertFromPageRectToScreenRect(PDFViewCtrl pdfViewCtrl, Rect rect, int pageNum) throws PDFNetException {
/*  532 */     rect.normalize();
/*      */     
/*  534 */     double x1 = rect.getX1();
/*  535 */     double y1 = rect.getY1();
/*  536 */     double x2 = rect.getX2();
/*  537 */     double y2 = rect.getY2();
/*      */     
/*  539 */     double[] pts1 = pdfViewCtrl.convPagePtToScreenPt(x1, y1, pageNum);
/*  540 */     double[] pts2 = pdfViewCtrl.convPagePtToScreenPt(x2, y2, pageNum);
/*      */     
/*  542 */     Rect retRect = new Rect(pts1[0], pts1[1], pts2[0], pts2[1]);
/*  543 */     retRect.normalize();
/*      */     
/*  545 */     return retRect;
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
/*      */   public static Rect convertFromPageRectToScrollViewerRect(PDFViewCtrl pdfViewCtrl, Rect rect, int pageNum) throws PDFNetException {
/*  558 */     rect.normalize();
/*      */     
/*  560 */     double x1 = rect.getX1();
/*  561 */     double y1 = rect.getY1();
/*  562 */     double x2 = rect.getX2();
/*  563 */     double y2 = rect.getY2();
/*      */     
/*  565 */     double[] pts1 = pdfViewCtrl.convPagePtToHorizontalScrollingPt(x1, y1, pageNum);
/*  566 */     double[] pts2 = pdfViewCtrl.convPagePtToHorizontalScrollingPt(x2, y2, pageNum);
/*      */     
/*  568 */     return new Rect(pts1[0], pts1[1], pts2[0], pts2[1]);
/*      */   }
/*      */   
/*      */   public static Rect getPageRect(PDFViewCtrl pdfViewCtrl, int pageNum) {
/*  572 */     if (pageNum >= 1) {
/*  573 */       boolean shouldUnlockRead = false;
/*      */       try {
/*  575 */         pdfViewCtrl.docLockRead();
/*  576 */         shouldUnlockRead = true;
/*  577 */         Page page = pdfViewCtrl.getDoc().getPage(pageNum);
/*  578 */         if (page != null) {
/*  579 */           Rect r = page.getBox(pdfViewCtrl.getPageBox());
/*  580 */           r.normalize();
/*  581 */           return r;
/*      */         } 
/*  583 */       } catch (Exception e) {
/*  584 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */       } finally {
/*  586 */         if (shouldUnlockRead) {
/*  587 */           pdfViewCtrl.docUnlockRead();
/*      */         }
/*      */       } 
/*      */     } 
/*  591 */     return null;
/*      */   }
/*      */   
/*      */   public static Rect getScreenRectInPageSpace(PDFViewCtrl pdfViewCtrl, int pageNum) {
/*  595 */     if (pageNum >= 1) {
/*  596 */       boolean shouldUnlockRead = false;
/*      */       try {
/*  598 */         pdfViewCtrl.docLockRead();
/*  599 */         shouldUnlockRead = true;
/*  600 */         int x1 = 0;
/*  601 */         int y1 = 0;
/*  602 */         int x2 = pdfViewCtrl.getWidth();
/*  603 */         int y2 = pdfViewCtrl.getHeight();
/*      */ 
/*      */         
/*  606 */         double[] pts1 = pdfViewCtrl.convScreenPtToPagePt(x1, y1, pageNum);
/*  607 */         double[] pts2 = pdfViewCtrl.convScreenPtToPagePt(x2, y1, pageNum);
/*  608 */         double[] pts3 = pdfViewCtrl.convScreenPtToPagePt(x2, y2, pageNum);
/*  609 */         double[] pts4 = pdfViewCtrl.convScreenPtToPagePt(x1, y2, pageNum);
/*  610 */         double min_x = Math.min(Math.min(Math.min(pts1[0], pts2[0]), pts3[0]), pts4[0]);
/*  611 */         double max_x = Math.max(Math.max(Math.max(pts1[0], pts2[0]), pts3[0]), pts4[0]);
/*  612 */         double min_y = Math.min(Math.min(Math.min(pts1[1], pts2[1]), pts3[1]), pts4[1]);
/*  613 */         double max_y = Math.max(Math.max(Math.max(pts1[1], pts2[1]), pts3[1]), pts4[1]);
/*  614 */         return new Rect(min_x, min_y, max_x, max_y);
/*  615 */       } catch (Exception e) {
/*  616 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */       } finally {
/*  618 */         if (shouldUnlockRead) {
/*  619 */           pdfViewCtrl.docUnlockRead();
/*      */         }
/*      */       } 
/*      */     } 
/*  623 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static RectF buildPageBoundBoxOnClient(PDFViewCtrl pdfViewCtrl, int pageNum) {
/*  634 */     RectF rect = null;
/*  635 */     if (pageNum >= 1) {
/*  636 */       boolean shouldUnlockRead = false;
/*      */       try {
/*  638 */         pdfViewCtrl.docLockRead();
/*  639 */         shouldUnlockRead = true;
/*  640 */         Page page = pdfViewCtrl.getDoc().getPage(pageNum);
/*  641 */         if (page != null) {
/*  642 */           rect = new RectF();
/*  643 */           Rect r = page.getBox(pdfViewCtrl.getPageBox());
/*      */           
/*  645 */           double x1 = r.getX1();
/*  646 */           double y1 = r.getY1();
/*  647 */           double x2 = r.getX2();
/*  648 */           double y2 = r.getY2();
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  653 */           double[] pts1 = pdfViewCtrl.convPagePtToScreenPt(x1, y1, pageNum);
/*  654 */           double[] pts2 = pdfViewCtrl.convPagePtToScreenPt(x2, y1, pageNum);
/*  655 */           double[] pts3 = pdfViewCtrl.convPagePtToScreenPt(x2, y2, pageNum);
/*  656 */           double[] pts4 = pdfViewCtrl.convPagePtToScreenPt(x1, y2, pageNum);
/*      */           
/*  658 */           double min_x = Math.min(Math.min(Math.min(pts1[0], pts2[0]), pts3[0]), pts4[0]);
/*  659 */           double max_x = Math.max(Math.max(Math.max(pts1[0], pts2[0]), pts3[0]), pts4[0]);
/*  660 */           double min_y = Math.min(Math.min(Math.min(pts1[1], pts2[1]), pts3[1]), pts4[1]);
/*  661 */           double max_y = Math.max(Math.max(Math.max(pts1[1], pts2[1]), pts3[1]), pts4[1]);
/*      */           
/*  663 */           float sx = pdfViewCtrl.getScrollX();
/*  664 */           float sy = pdfViewCtrl.getScrollY();
/*  665 */           rect = new RectF();
/*  666 */           rect.set((float)min_x + sx, (float)min_y + sy, (float)max_x + sx, (float)max_y + sy);
/*      */         } 
/*  668 */       } catch (Exception e) {
/*  669 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */       } finally {
/*  671 */         if (shouldUnlockRead) {
/*  672 */           pdfViewCtrl.docUnlockRead();
/*      */         }
/*      */       } 
/*      */     } 
/*  676 */     return rect;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void snapPointToRect(PointF point, RectF rect) {
/*  686 */     if (rect != null) {
/*  687 */       if (point.x < rect.left) {
/*  688 */         point.x = rect.left;
/*  689 */       } else if (point.x > rect.right) {
/*  690 */         point.x = rect.right;
/*      */       } 
/*  692 */       if (point.y < rect.top) {
/*  693 */         point.y = rect.top;
/*  694 */       } else if (point.y > rect.bottom) {
/*  695 */         point.y = rect.bottom;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static PointF[] getVerticesFromPoly(@NonNull PDFViewCtrl pdfViewCtrl, @NonNull Polygon polygon, int pageNum) {
/*  702 */     boolean shouldUnlockRead = false;
/*      */     
/*  704 */     try { pdfViewCtrl.docLockRead();
/*  705 */       shouldUnlockRead = true;
/*      */       
/*  707 */       int count = polygon.getVertexCount();
/*  708 */       PointF[] pts = new PointF[count];
/*  709 */       for (int i = 0; i < count; i++) {
/*  710 */         Point p = polygon.getVertex(i);
/*  711 */         double[] screenPts = pdfViewCtrl.convPagePtToScreenPt(p.x, p.y, pageNum);
/*  712 */         pts[i] = new PointF((float)Math.round(screenPts[0]), (float)Math.round(screenPts[1]));
/*      */       } 
/*  714 */       return pts; }
/*  715 */     catch (Exception exception) {  }
/*      */     finally
/*  717 */     { if (shouldUnlockRead) {
/*  718 */         pdfViewCtrl.docUnlockRead();
/*      */       } }
/*      */     
/*  721 */     return null;
/*      */   }
/*      */   
/*      */   public static Rect getBBox(ArrayList<Point> pagePoints) {
/*  725 */     double min_x = Double.MAX_VALUE;
/*  726 */     double min_y = Double.MAX_VALUE;
/*  727 */     double max_x = Double.MIN_VALUE;
/*  728 */     double max_y = Double.MIN_VALUE;
/*      */     
/*  730 */     for (Point point : pagePoints) {
/*  731 */       min_x = Math.min(min_x, point.x);
/*  732 */       max_x = Math.max(max_x, point.x);
/*  733 */       min_y = Math.min(min_y, point.y);
/*  734 */       max_y = Math.max(max_y, point.y);
/*      */     } 
/*      */     
/*      */     try {
/*  738 */       if (min_x == Double.MAX_VALUE && min_y == Double.MAX_VALUE && max_x == Double.MIN_VALUE && max_y == Double.MIN_VALUE)
/*      */       {
/*      */         
/*  741 */         return null;
/*      */       }
/*  743 */       Rect rect = new Rect(min_x, min_y, max_x, max_y);
/*  744 */       rect.normalize();
/*  745 */       return rect;
/*      */     }
/*  747 */     catch (Exception e) {
/*  748 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isSystemLanguagePersianArabic() {
/*  757 */     if (!isJellyBeanMR1()) {
/*  758 */       return false;
/*      */     }
/*  760 */     int directionality = Character.getDirectionality(Locale.getDefault().getDisplayName().charAt(0));
/*  761 */     return (directionality == 1 || directionality == 2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isRtlLayout(Context context) {
/*  772 */     if (isJellyBeanMR1() && context != null) {
/*  773 */       Configuration config = context.getResources().getConfiguration();
/*  774 */       return (config.getLayoutDirection() == 1);
/*      */     } 
/*  776 */     return false;
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
/*      */   public static boolean isLeftToRightString(String str) {
/*  788 */     if (isNullOrEmpty(str)) {
/*  789 */       return false;
/*      */     }
/*      */     
/*  792 */     int sz = str.length();
/*  793 */     for (int i = 0; i < sz; i++) {
/*  794 */       char u = str.charAt(i);
/*  795 */       if (('֐' <= u && u <= '׿') || ('؀' <= u && u <= 'ۿ') || ('ݐ' <= u && u <= 'ݿ') || ('ﭐ' <= u && u <= '﷿') || ('ﹰ' <= u && u <= '﻿'))
/*      */       {
/*  797 */         return false;
/*      */       }
/*  799 */       if (('A' <= u && u <= 'Z') || ('a' <= u && u <= 'z') || ('ﬀ' <= u && u <= 'ﬆ')) {
/*  800 */         return true;
/*      */       }
/*      */     } 
/*  803 */     return false;
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
/*      */   public static boolean isRightToLeftString(String str) {
/*  815 */     if (isNullOrEmpty(str)) {
/*  816 */       return false;
/*      */     }
/*      */     
/*  819 */     int sz = str.length();
/*  820 */     for (int i = 0; i < sz; i++) {
/*  821 */       char u = str.charAt(i);
/*  822 */       if (('֐' <= u && u <= '׿') || ('؀' <= u && u <= 'ۿ') || ('ݐ' <= u && u <= 'ݿ') || ('ﭐ' <= u && u <= '﷿') || ('ﹰ' <= u && u <= '﻿'))
/*      */       {
/*  824 */         return true;
/*      */       }
/*  826 */       if (('A' <= u && u <= 'Z') || ('a' <= u && u <= 'z') || ('ﬀ' <= u && u <= 'ﬆ')) {
/*  827 */         return false;
/*      */       }
/*      */     } 
/*  830 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean hasRightToLeftChar(String str) {
/*  841 */     if (str == null || str.length() == 0) {
/*  842 */       return false;
/*      */     }
/*      */     
/*  845 */     int sz = str.length();
/*  846 */     for (int i = 0; i < sz; i++) {
/*  847 */       char u = str.charAt(i);
/*  848 */       if (('֐' <= u && u <= '׿') || ('؀' <= u && u <= 'ۿ') || ('ݐ' <= u && u <= 'ݿ') || ('ﭐ' <= u && u <= '﷿') || ('ﹰ' <= u && u <= '﻿'))
/*      */       {
/*  850 */         return true;
/*      */       }
/*      */     } 
/*  853 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getBidiString(@NonNull String str) {
/*  864 */     StringBuilder out = new StringBuilder(str);
/*  865 */     if (Bidi.requiresBidi(str.toCharArray(), 0, str.length())) {
/*  866 */       Bidi bidi = new Bidi(str, -2);
/*  867 */       out = new StringBuilder();
/*  868 */       for (int i = 0; i < bidi.getRunCount(); i++) {
/*  869 */         String s1 = str.substring(bidi.getRunStart(i), bidi.getRunLimit(i));
/*  870 */         if (bidi.getRunLevel(i) % 2 == 1) {
/*  871 */           s1 = (new StringBuffer(s1)).reverse().toString();
/*      */         }
/*  873 */         out.append(s1);
/*      */       } 
/*      */     } 
/*  876 */     return out.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getLocaleDigits(String input) {
/*  886 */     StringBuilder output = new StringBuilder();
/*  887 */     for (int i = 0; i < input.length(); i++) {
/*  888 */       char ch = input.charAt(i);
/*  889 */       if (ch >= '0' && ch <= '9') {
/*  890 */         output.append(String.format(Locale.getDefault(), "%d", new Object[] { Integer.valueOf(ch - 48) }));
/*      */       } else {
/*  892 */         output.append(ch);
/*      */       } 
/*      */     } 
/*  895 */     return output.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isNullOrEmpty(String string) {
/*  905 */     return (string == null || string.trim().isEmpty());
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
/*      */   private static String getUriColumnInfo(@NonNull Context context, @Nullable Uri contentUri, String column) {
/*  917 */     if (contentUri == null) {
/*  918 */       return null;
/*      */     }
/*      */     
/*  921 */     Cursor cursor = null;
/*  922 */     ContentResolver contentResolver = getContentResolver(context);
/*  923 */     if (contentResolver == null) {
/*  924 */       return null;
/*      */     }
/*      */     try {
/*  927 */       String[] proj = { column };
/*  928 */       cursor = contentResolver.query(contentUri, proj, null, null, null);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  933 */       if (null != cursor && cursor.moveToFirst() && cursor.getColumnCount() > 0 && cursor.getCount() > 0) {
/*  934 */         int column_index = cursor.getColumnIndexOrThrow(proj[0]);
/*  935 */         return cursor.getString(column_index);
/*      */       } 
/*  937 */       return "";
/*  938 */     } catch (Exception e) {
/*  939 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } finally {
/*  941 */       if (cursor != null) {
/*  942 */         cursor.close();
/*      */       }
/*      */     } 
/*  945 */     return "";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getRealPathFromImageURI(Context context, Uri imageUri) {
/*  956 */     if (null == context || null == imageUri) {
/*  957 */       return "";
/*      */     }
/*  959 */     Cursor cursor = null;
/*  960 */     ContentResolver contentResolver = getContentResolver(context);
/*  961 */     if (contentResolver == null) {
/*  962 */       return "";
/*      */     }
/*      */     
/*      */     try {
/*  966 */       String[] proj = { "_data" };
/*  967 */       cursor = contentResolver.query(imageUri, proj, null, null, null);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  972 */       if (cursor != null && cursor.moveToFirst() && cursor.getColumnCount() > 0 && cursor.getCount() > 0) {
/*  973 */         int column_index = cursor.getColumnIndexOrThrow(proj[0]);
/*  974 */         return cursor.getString(column_index);
/*      */       } 
/*  976 */       return "";
/*  977 */     } catch (Exception e) {
/*  978 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } finally {
/*  980 */       if (cursor != null) {
/*  981 */         cursor.close();
/*      */       }
/*      */     } 
/*  984 */     return "";
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
/*      */   public static Bitmap getBitmapFromImageUri(Context context, Uri imageUri, String backupFilepath) {
/*  996 */     if (null == context || null == imageUri) {
/*  997 */       return null;
/*      */     }
/*  999 */     ContentResolver contentResolver = getContentResolver(context);
/* 1000 */     if (contentResolver == null) {
/* 1001 */       return null;
/*      */     }
/*      */     
/* 1004 */     try { Bitmap bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri);
/* 1005 */       if (bitmap == null)
/*      */       {
/* 1007 */         if (!isNullOrEmpty(backupFilepath)) {
/* 1008 */           FileInputStream fileInputStream = new FileInputStream(backupFilepath);
/* 1009 */           bitmap = BitmapFactory.decodeStream(fileInputStream);
/*      */         } 
/*      */       }
/* 1012 */       return bitmap; }
/* 1013 */     catch (FileNotFoundException fileNotFoundException) {  }
/* 1014 */     catch (Exception e)
/* 1015 */     { AnalyticsHandlerAdapter.getInstance().sendException(e); }
/* 1016 */     catch (OutOfMemoryError oom)
/* 1017 */     { manageOOM(context, null); }
/*      */     
/* 1019 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getUriDisplayName(@NonNull Context context, @Nullable Uri contentUri) {
/* 1030 */     if (contentUri == null) {
/* 1031 */       return null;
/*      */     }
/*      */     
/* 1034 */     String displayName = null;
/* 1035 */     String[] projection = { "_display_name" };
/*      */     
/* 1037 */     if ("file".equalsIgnoreCase(contentUri.getScheme())) {
/* 1038 */       return contentUri.getLastPathSegment();
/*      */     }
/* 1040 */     Cursor cursor = null;
/* 1041 */     ContentResolver contentResolver = getContentResolver(context);
/* 1042 */     if (contentResolver == null) {
/* 1043 */       return null;
/*      */     }
/*      */     try {
/* 1046 */       cursor = contentResolver.query(contentUri, projection, null, null, null);
/* 1047 */       if (cursor != null && cursor.moveToFirst() && cursor.getColumnCount() > 0 && cursor.getCount() > 0) {
/* 1048 */         int nameIndex = cursor.getColumnIndexOrThrow(projection[0]);
/* 1049 */         if (nameIndex >= 0) {
/* 1050 */           displayName = cursor.getString(nameIndex);
/*      */         }
/*      */       } 
/* 1053 */     } catch (Exception e) {
/* 1054 */       displayName = null;
/*      */     } finally {
/* 1056 */       if (cursor != null) {
/* 1057 */         cursor.close();
/*      */       }
/*      */     } 
/* 1060 */     return displayName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long getUriFileSize(@NonNull ContentResolver contentResolver, @NonNull Uri uri) {
/* 1071 */     long size = -1L;
/*      */ 
/*      */     
/* 1074 */     if ("file".equalsIgnoreCase(uri.getScheme())) {
/* 1075 */       File file = new File(uri.getPath());
/* 1076 */       if (file.exists()) {
/* 1077 */         size = file.length();
/*      */       }
/*      */     } else {
/*      */       
/* 1081 */       String[] projection = { "_size" };
/* 1082 */       Cursor cursor = null;
/*      */       try {
/* 1084 */         cursor = contentResolver.query(uri, projection, null, null, null);
/* 1085 */         if (cursor != null && cursor.getColumnCount() > 0 && cursor.moveToFirst()) {
/*      */           
/* 1087 */           int index = cursor.getColumnIndex("_size");
/* 1088 */           if (index >= 0) {
/* 1089 */             size = cursor.getInt(index);
/*      */           }
/*      */         } 
/* 1092 */       } catch (Exception ignored) {
/* 1093 */         size = -1L;
/*      */       } finally {
/* 1095 */         if (cursor != null) {
/* 1096 */           cursor.close();
/*      */         }
/*      */       } 
/*      */     } 
/* 1100 */     return size;
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
/*      */   @Nullable
/*      */   public static String getUriExtension(@NonNull ContentResolver contentResolver, @NonNull Uri uri) {
/*      */     String extension;
/* 1115 */     if ("content".equals(uri.getScheme())) {
/*      */       
/* 1117 */       MimeTypeMap mime = MimeTypeMap.getSingleton();
/* 1118 */       extension = mime.getExtensionFromMimeType(contentResolver.getType(uri));
/*      */     }
/*      */     else {
/*      */       
/* 1122 */       extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(new File(uri.getPath())).toString());
/*      */     } 
/*      */     
/* 1125 */     return extension;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float convDp2Pix(@NonNull Context context, float dp) {
/* 1132 */     float density = (context.getResources().getDisplayMetrics()).density;
/* 1133 */     return dp * density;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float convPix2Dp(@NonNull Context context, float pix) {
/* 1140 */     float density = (context.getResources().getDisplayMetrics()).density;
/* 1141 */     return pix / density;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void getDisplaySize(@NonNull Context context, Point outSize) {
/* 1151 */     if (outSize == null) {
/*      */       return;
/*      */     }
/* 1154 */     WindowManager wm = (WindowManager)context.getSystemService("window");
/* 1155 */     if (wm != null) {
/* 1156 */       Display display = wm.getDefaultDisplay();
/* 1157 */       display.getSize(outSize);
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
/*      */   public static boolean isDoNotRequestThumbFile(@Nullable ContentResolver cr, @Nullable String fileUriString) {
/* 1170 */     if (cr == null || isNullOrEmpty(fileUriString)) {
/* 1171 */       return false;
/*      */     }
/*      */     try {
/* 1174 */       Uri fileUri = Uri.parse(fileUriString);
/* 1175 */       String mime = cr.getType(fileUri);
/* 1176 */       if (mime != null) {
/* 1177 */         String[] mimeTypes = { "application/msword", "application/vnd.ms-powerpoint", "application/vnd.ms-excel" };
/* 1178 */         for (String type : mimeTypes) {
/* 1179 */           if (mime.equalsIgnoreCase(type)) {
/* 1180 */             return true;
/*      */           }
/*      */         } 
/*      */       } 
/* 1184 */     } catch (Exception exception) {}
/*      */ 
/*      */     
/* 1187 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isDoNotRequestThumbFile(String filePath) {
/* 1197 */     if (isNullOrEmpty(filePath)) {
/* 1198 */       return false;
/*      */     }
/* 1200 */     String ext = getExtension(filePath);
/* 1201 */     String[] fileTypes = { "doc", "ppt", "xls" };
/* 1202 */     for (String type : fileTypes) {
/* 1203 */       if (ext.equalsIgnoreCase(type)) {
/* 1204 */         return true;
/*      */       }
/*      */     } 
/*      */     
/* 1208 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isNonPDFByMimeType(@NonNull String mimeType) {
/* 1215 */     String[] mimeTypes = Constants.OFFICE_FILE_MIME_TYPES;
/* 1216 */     for (String type : mimeTypes) {
/* 1217 */       if (mimeType.equalsIgnoreCase(type)) {
/* 1218 */         return true;
/*      */       }
/*      */     } 
/* 1221 */     mimeTypes = Constants.IMAGE_FILE_MIME_TYPES;
/* 1222 */     for (String type : mimeTypes) {
/* 1223 */       if (mimeType.equalsIgnoreCase(type)) {
/* 1224 */         return true;
/*      */       }
/*      */     } 
/* 1227 */     return false;
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
/*      */   public static boolean isOfficeDocument(@NonNull ContentResolver cr, Uri fileUri) {
/*      */     try {
/* 1240 */       String mime = cr.getType(fileUri);
/* 1241 */       if (mime != null) {
/* 1242 */         String[] mimeTypes = Constants.OFFICE_FILE_MIME_TYPES;
/* 1243 */         for (String type : mimeTypes) {
/* 1244 */           if (mime.equalsIgnoreCase(type)) {
/* 1245 */             return true;
/*      */           }
/*      */         } 
/*      */       } 
/* 1249 */     } catch (Exception exception) {}
/*      */ 
/*      */     
/* 1252 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isOfficeDocument(String filePath) {
/* 1262 */     if (isNullOrEmpty(filePath)) {
/* 1263 */       return false;
/*      */     }
/*      */     
/* 1266 */     String ext = getExtension(filePath);
/* 1267 */     String[] fileTypes = Constants.FILE_NAME_EXTENSIONS_DOC;
/* 1268 */     for (String type : fileTypes) {
/* 1269 */       if (ext.equalsIgnoreCase(type)) {
/* 1270 */         return true;
/*      */       }
/*      */     } 
/*      */     
/* 1274 */     return false;
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
/*      */   public static boolean isConvertibleFormat(String filePath) {
/* 1286 */     if (isNullOrEmpty(filePath)) {
/* 1287 */       return false;
/*      */     }
/*      */     
/* 1290 */     String ext = getExtension(filePath);
/* 1291 */     String[] fileTypes = Constants.FILE_NAME_EXTENSIONS_OTHERS;
/* 1292 */     for (String type : fileTypes) {
/* 1293 */       if (ext.equalsIgnoreCase(type)) {
/* 1294 */         return true;
/*      */       }
/*      */     } 
/*      */     
/* 1298 */     return false;
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
/*      */   public static boolean isImageFile(@NonNull ContentResolver cr, Uri fileUri) {
/*      */     try {
/* 1311 */       String mime = cr.getType(fileUri);
/* 1312 */       if (mime != null) {
/* 1313 */         String[] mimeTypes = Constants.IMAGE_FILE_MIME_TYPES;
/* 1314 */         for (String type : mimeTypes) {
/* 1315 */           if (mime.equalsIgnoreCase(type)) {
/* 1316 */             return true;
/*      */           }
/*      */         } 
/*      */       } 
/* 1320 */     } catch (Exception exception) {}
/*      */ 
/*      */     
/* 1323 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isImageFile(String filePath) {
/* 1334 */     if (isNullOrEmpty(filePath)) {
/* 1335 */       return false;
/*      */     }
/*      */     
/* 1338 */     String ext = getExtension(filePath);
/* 1339 */     String[] fileTypes = Constants.FILE_NAME_EXTENSIONS_IMAGE;
/* 1340 */     for (String type : fileTypes) {
/* 1341 */       if (ext.equalsIgnoreCase(type)) {
/* 1342 */         return true;
/*      */       }
/*      */     } 
/*      */     
/* 1346 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isNotPdf(String filePath) {
/* 1356 */     return (isOfficeDocument(filePath) || isImageFile(filePath) || isConvertibleFormat(filePath));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isNotPdf(@NonNull ContentResolver cr, Uri fileUri) {
/* 1367 */     return (isOfficeDocument(cr, fileUri) || isImageFile(cr, fileUri) || isNotPdf(fileUri.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String decryptIt(Context context, String value) {
/* 1378 */     if (isNullOrEmpty(value)) {
/* 1379 */       return value;
/*      */     }
/*      */     
/* 1382 */     String cryptoPass = context.getString((context.getApplicationInfo()).labelRes);
/*      */     try {
/* 1384 */       DESKeySpec keySpec = new DESKeySpec(cryptoPass.getBytes("UTF8"));
/* 1385 */       SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
/* 1386 */       SecretKey key = keyFactory.generateSecret(keySpec);
/*      */       
/* 1388 */       byte[] encrypedPwdBytes = Base64.decode(value, 0);
/*      */       
/* 1390 */       Cipher cipher = Cipher.getInstance("DES");
/* 1391 */       cipher.init(2, key);
/* 1392 */       byte[] decrypedValueBytes = cipher.doFinal(encrypedPwdBytes);
/*      */       
/* 1394 */       return new String(decrypedValueBytes);
/*      */     }
/* 1396 */     catch (Exception e) {
/* 1397 */       Log.e(e.getClass().getName(), e.getMessage());
/*      */       
/* 1399 */       return value;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String encryptIt(Context context, String value) {
/* 1410 */     String cryptoPass = context.getString((context.getApplicationInfo()).labelRes);
/*      */     try {
/* 1412 */       DESKeySpec keySpec = new DESKeySpec(cryptoPass.getBytes("UTF8"));
/* 1413 */       SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
/* 1414 */       SecretKey key = keyFactory.generateSecret(keySpec);
/*      */       
/* 1416 */       byte[] clearText = value.getBytes("UTF8");
/*      */       
/* 1418 */       Cipher cipher = Cipher.getInstance("DES");
/* 1419 */       cipher.init(1, key);
/*      */       
/* 1421 */       return Base64.encodeToString(cipher.doFinal(clearText), 0);
/*      */     }
/* 1423 */     catch (Exception e) {
/* 1424 */       Log.d(e.getClass().getName(), e.getMessage());
/*      */       
/* 1426 */       return value;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   public static String getPassword(@NonNull Context context, @Nullable String filepath) {
/* 1438 */     if (isNullOrEmpty(filepath)) {
/* 1439 */       return "";
/*      */     }
/* 1441 */     PdfViewCtrlTabInfo info = PdfViewCtrlTabsManager.getInstance().getPdfFViewCtrlTabInfo(context, filepath);
/* 1442 */     if (info != null && !isNullOrEmpty(info.password)) {
/* 1443 */       return decryptIt(context, info.password);
/*      */     }
/* 1445 */     return "";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static AlertDialog.Builder getAlertDialogNoTitleBuilder(Context context, int messageId) {
/* 1456 */     return getAlertDialogBuilder(context, context.getResources().getString(messageId), "");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static AlertDialog.Builder getAlertDialogBuilder(Context context, int messageId, int titleId) {
/* 1467 */     return getAlertDialogBuilder(context, context.getResources().getString(messageId), context.getResources().getString(titleId));
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
/*      */   public static AlertDialog.Builder getAlertDialogBuilder(Context context, String message, String title) {
/* 1479 */     AlertDialog.Builder builder = new AlertDialog.Builder(context);
/*      */     
/* 1481 */     builder.setMessage(message)
/* 1482 */       .setCancelable(true);
/*      */     
/* 1484 */     if (!isNullOrEmpty(title)) {
/* 1485 */       builder.setTitle(title);
/*      */     }
/* 1487 */     return builder;
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
/*      */   public static String humanReadableByteCount(long bytes, boolean si) {
/* 1500 */     int unit = si ? 1000 : 1024;
/* 1501 */     if (bytes < unit) {
/* 1502 */       if (isSystemLanguagePersianArabic()) {
/* 1503 */         return String.format(Locale.getDefault(), "%dB", new Object[] { Long.valueOf(bytes) });
/*      */       }
/* 1505 */       return bytes + " B";
/*      */     } 
/* 1507 */     int exp = (int)(Math.log(bytes) / Math.log(unit));
/*      */     
/* 1509 */     String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + "";
/* 1510 */     if (isSystemLanguagePersianArabic()) {
/* 1511 */       return String.format(Locale.getDefault(), "%.1f%sB", new Object[] { Double.valueOf(bytes / Math.pow(unit, exp)), pre });
/*      */     }
/* 1513 */     return String.format(Locale.getDefault(), "%.1f %sB", new Object[] { Double.valueOf(bytes / Math.pow(unit, exp)), pre });
/*      */   }
/*      */   
/*      */   public static Long getReadableByteValue(long bytes, boolean si) {
/* 1517 */     int unit = si ? 1000 : 1024;
/* 1518 */     if (bytes < unit) return Long.valueOf(bytes); 
/* 1519 */     int exp = (int)(Math.log(bytes) / Math.log(unit));
/* 1520 */     return Long.valueOf((long)(bytes / Math.pow(unit, exp)));
/*      */   }
/*      */   
/*      */   public static String getReadableByteUnit(long bytes, boolean si) {
/* 1524 */     int unit = si ? 1000 : 1024;
/* 1525 */     if (bytes < unit) return "B"; 
/* 1526 */     int exp = (int)(Math.log(bytes) / Math.log(unit));
/*      */     
/* 1528 */     return (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + "";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getByteCount(long bytes) {
/* 1538 */     return NumberFormat.getNumberInstance().format(bytes);
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
/*      */   public static String getDisplayNameFromImageUri(Context context, Uri imageUri, String backupFilepath) {
/* 1550 */     if (null == context || null == imageUri) {
/* 1551 */       return null;
/*      */     }
/*      */     try {
/* 1554 */       String filename = getUriColumnInfo(context, imageUri, "_display_name");
/* 1555 */       if (isNullOrEmpty(filename) && 
/* 1556 */         !isNullOrEmpty(backupFilepath)) {
/* 1557 */         filename = FilenameUtils.getBaseName(backupFilepath);
/*      */       }
/*      */       
/* 1560 */       return FilenameUtils.removeExtension(filename);
/* 1561 */     } catch (Exception e) {
/* 1562 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */       
/* 1564 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @TargetApi(21)
/*      */   public static boolean isSdCardFile(@Nullable Context context, File file) {
/* 1576 */     if (context == null || !isKitKat())
/*      */     {
/* 1578 */       return false;
/*      */     }
/* 1580 */     if (file != null) {
/* 1581 */       if (file.getParentFile() == null || file.getAbsolutePath().equals("/storage"))
/*      */       {
/* 1583 */         return false;
/*      */       }
/* 1585 */       File storageDirectory = Environment.getExternalStorageDirectory();
/* 1586 */       File[] rootDirs = context.getExternalFilesDirs(null);
/* 1587 */       if (rootDirs != null && rootDirs.length > 0) {
/* 1588 */         for (File dir : rootDirs) {
/* 1589 */           if (dir != null) {
/*      */             try {
/* 1591 */               if (!FilenameUtils.equals(storageDirectory.getAbsolutePath(), dir.getAbsolutePath()) && 
/* 1592 */                 !FileUtils.directoryContains(storageDirectory, dir)) {
/* 1593 */                 while (dir.getParentFile() != null && !dir.getAbsolutePath().equalsIgnoreCase("/storage")) {
/* 1594 */                   if (FilenameUtils.equals(file.getAbsolutePath(), dir.getAbsolutePath()) || 
/* 1595 */                     FileUtils.directoryContains(dir, file))
/*      */                   {
/* 1597 */                     return true;
/*      */                   }
/* 1599 */                   dir = dir.getParentFile();
/*      */                 } 
/*      */               }
/* 1602 */             } catch (Exception exception) {}
/*      */           }
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/* 1608 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static File convExternalContentUriToFile(@NonNull Context activity, @Nullable Uri beamUri) {
/* 1619 */     if (beamUri == null) {
/* 1620 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1628 */     if (beamUri.getAuthority().contains("media")) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1635 */       Cursor pathCursor = null;
/*      */       try {
/* 1637 */         String[] projection = { "_data" };
/* 1638 */         ContentResolver contentResolver = getContentResolver(activity);
/* 1639 */         if (contentResolver == null) {
/* 1640 */           return null;
/*      */         }
/* 1642 */         pathCursor = contentResolver.query(beamUri, projection, null, null, null);
/*      */         
/* 1644 */         if (pathCursor != null && pathCursor.moveToFirst() && pathCursor
/* 1645 */           .getCount() > 0 && pathCursor.getColumnCount() > 0) {
/*      */           
/* 1647 */           int filenameIndex = pathCursor.getColumnIndex("_data");
/*      */           
/* 1649 */           if (filenameIndex == -1) {
/* 1650 */             return null;
/*      */           }
/*      */           
/* 1653 */           String fileName = pathCursor.getString(filenameIndex);
/*      */           
/* 1655 */           return new File(fileName);
/*      */         } 
/* 1657 */       } catch (Exception e) {
/* 1658 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */       } finally {
/* 1660 */         if (pathCursor != null) {
/* 1661 */           pathCursor.close();
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1667 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void showAlertDialogWithLink(@NonNull Context context, String message, @Nullable String title) {
/* 1678 */     AlertDialog.Builder builder = new AlertDialog.Builder(context);
/*      */     
/* 1680 */     builder.setMessage((CharSequence)Html.fromHtml(message))
/* 1681 */       .setCancelable(true)
/* 1682 */       .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener()
/*      */         {
/*      */           public void onClick(DialogInterface dialog, int which)
/*      */           {
/* 1686 */             dialog.cancel();
/*      */           }
/*      */         });
/* 1689 */     if (title != null && title.length() > 0) {
/* 1690 */       builder.setTitle(title);
/*      */     }
/* 1692 */     AlertDialog d = builder.create();
/* 1693 */     d.show();
/*      */ 
/*      */     
/* 1696 */     ((TextView)d.findViewById(16908299)).setMovementMethod(LinkMovementMethod.getInstance());
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
/*      */   public static int getScreenWidth(Context context) {
/* 1708 */     WindowManager wm = (WindowManager)context.getSystemService("window");
/* 1709 */     if (wm == null) {
/* 1710 */       return 0;
/*      */     }
/* 1712 */     Display display = wm.getDefaultDisplay();
/* 1713 */     Point size = new Point();
/* 1714 */     display.getSize(size);
/* 1715 */     return size.x;
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
/*      */   public static int getScreenHeight(Context context) {
/* 1728 */     WindowManager wm = (WindowManager)context.getSystemService("window");
/* 1729 */     if (wm == null) {
/* 1730 */       return 0;
/*      */     }
/* 1732 */     Display display = wm.getDefaultDisplay();
/* 1733 */     Point size = new Point();
/* 1734 */     display.getSize(size);
/* 1735 */     return size.y;
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
/*      */   public static int getRealScreenWidth(Context context) {
/* 1748 */     if (Build.VERSION.SDK_INT >= 17) {
/* 1749 */       WindowManager wm = (WindowManager)context.getSystemService("window");
/* 1750 */       if (wm == null) {
/* 1751 */         return 0;
/*      */       }
/* 1753 */       Display display = wm.getDefaultDisplay();
/* 1754 */       Point size = new Point();
/* 1755 */       display.getRealSize(size);
/* 1756 */       return size.x;
/*      */     } 
/* 1758 */     return getScreenWidth(context);
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
/*      */   public static int getRealScreenHeight(Context context) {
/* 1770 */     if (Build.VERSION.SDK_INT >= 17) {
/* 1771 */       WindowManager wm = (WindowManager)context.getSystemService("window");
/* 1772 */       if (wm == null) {
/* 1773 */         return 0;
/*      */       }
/* 1775 */       Display display = wm.getDefaultDisplay();
/* 1776 */       Point size = new Point();
/* 1777 */       display.getRealSize(size);
/* 1778 */       return size.y;
/*      */     } 
/* 1780 */     return getScreenHeight(context);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isScreenTooNarrow(@NonNull Context context) {
/* 1791 */     int screenWidth = getScreenWidth(context);
/* 1792 */     int minIconSize = (int)convDp2Pix(context, 48.0F);
/*      */     
/* 1794 */     return (screenWidth < minIconSize * 9);
/*      */   }
/*      */   
/*      */   public static int toolbarIconMaxCount(@NonNull Context context) {
/* 1798 */     int screenWidth = getScreenWidth(context);
/* 1799 */     int minIconSize = (int)convDp2Pix(context, 48.0F);
/* 1800 */     return (int)(screenWidth / minIconSize);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @TargetApi(21)
/*      */   public static String getUriTreePath(Uri uri) {
/*      */     String treePath;
/* 1811 */     if (!isLollipop()) {
/* 1812 */       return uri.getPath();
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/* 1817 */       treePath = DocumentsContract.getTreeDocumentId(uri);
/* 1818 */     } catch (IllegalArgumentException e) {
/* 1819 */       treePath = "";
/*      */     } 
/* 1821 */     return treePath;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @TargetApi(21)
/*      */   public static String getUriDocumentPath(Uri uri) {
/*      */     String docPath;
/* 1833 */     if (!isLollipop()) {
/* 1834 */       return uri.getPath();
/*      */     }
/*      */     
/*      */     try {
/* 1838 */       docPath = DocumentsContract.getDocumentId(uri);
/* 1839 */     } catch (IllegalArgumentException e) {
/* 1840 */       docPath = "";
/*      */     } 
/* 1842 */     return docPath;
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
/*      */   public static void safeShowAlertDialog(Context context, CharSequence message, String title) {
/* 1856 */     if (!(context instanceof Activity)) {
/*      */       return;
/*      */     }
/* 1859 */     if (!validActivity((Activity)context)) {
/*      */       return;
/*      */     }
/* 1862 */     AlertDialog.Builder builder = new AlertDialog.Builder(context);
/* 1863 */     builder.setMessage(message)
/* 1864 */       .setCancelable(true)
/* 1865 */       .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener()
/*      */         {
/*      */           public void onClick(DialogInterface dialog, int which)
/*      */           {
/* 1869 */             dialog.cancel();
/*      */           }
/*      */         });
/* 1872 */     if (!isNullOrEmpty(title)) {
/* 1873 */       builder.setTitle(title);
/*      */     }
/* 1875 */     builder.create().show();
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
/*      */   public static void safeShowAlertDialog(@NonNull Context context, int messageId, int titleId) {
/* 1889 */     safeShowAlertDialog(context, context.getResources().getString(messageId), context.getResources().getString(titleId));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void showAlertDialog(Activity activity, final CharSequence message, final String title) {
/* 1900 */     final WeakReference<Activity> activityRef = new WeakReference<>(activity);
/* 1901 */     activity.runOnUiThread(new Runnable()
/*      */         {
/*      */           public void run() {
/* 1904 */             Activity activity = activityRef.get();
/* 1905 */             if (Utils.validActivity(activity)) {
/* 1906 */               Utils.safeShowAlertDialog((Context)activity, message, title);
/*      */             }
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean validActivity(@Nullable Activity activity) {
/* 1919 */     if (activity == null) {
/* 1920 */       return false;
/*      */     }
/*      */     
/* 1923 */     if (activity instanceof FragmentActivity) {
/* 1924 */       return (!isActivityFinishingOrDestroyed(activity) && validLifecycleState((FragmentActivity)activity));
/*      */     }
/* 1926 */     return !isActivityFinishingOrDestroyed(activity);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isActivityFinishingOrDestroyed(@NonNull Activity activity) {
/* 1932 */     if (isJellyBeanMR1()) {
/* 1933 */       return (activity.isFinishing() || activity.isDestroyed());
/*      */     }
/* 1935 */     return activity.isFinishing();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean validLifecycleState(@NonNull FragmentActivity activity) {
/* 1941 */     return activity.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void showAlertDialog(Activity activity, int messageId, int titleId) {
/* 1952 */     showAlertDialog(activity, activity.getResources().getString(messageId), activity.getResources().getString(titleId));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isAppInstalledOnDevice(Context context, String uri) {
/*      */     boolean app_installed;
/* 1963 */     PackageManager pm = context.getPackageManager();
/*      */     
/*      */     try {
/* 1966 */       pm.getPackageInfo(uri, 1);
/* 1967 */       app_installed = true;
/* 1968 */     } catch (PackageManager.NameNotFoundException e) {
/* 1969 */       app_installed = false;
/*      */     } 
/* 1971 */     return app_installed;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getFileNameNotInUse(String original) {
/* 1981 */     if (isNullOrEmpty(original)) {
/* 1982 */       return "";
/*      */     }
/*      */     
/* 1985 */     String extension = FilenameUtils.getExtension(original);
/*      */     
/* 1987 */     String newFileName = original;
/* 1988 */     int i = 1;
/*      */     while (true) {
/* 1990 */       File newFile = new File(newFileName);
/* 1991 */       if (!newFile.exists()) {
/*      */         break;
/*      */       }
/* 1994 */       newFileName = FilenameUtils.removeExtension(original) + " (" + String.valueOf(i) + ")." + extension;
/*      */       
/* 1996 */       if (++i % 10 == 0) {
/* 1997 */         Random random = new Random();
/* 1998 */         i = Math.abs(random.nextInt()) / 10 * 10 + 1;
/*      */       } 
/*      */     } 
/*      */     
/* 2002 */     return newFileName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getFileNameNotInUse(ExternalFileInfo folder, String original) {
/* 2013 */     if (folder == null || isNullOrEmpty(original)) {
/* 2014 */       return "";
/*      */     }
/* 2016 */     String extension = FilenameUtils.getExtension(original);
/*      */     
/* 2018 */     String newFileName = original;
/* 2019 */     int i = 1;
/*      */     
/* 2021 */     while (folder.getFile(newFileName) != null) {
/*      */ 
/*      */ 
/*      */       
/* 2025 */       newFileName = FilenameUtils.removeExtension(original) + " (" + String.valueOf(i) + ")." + extension;
/*      */       
/* 2027 */       if (++i % 10 == 0) {
/* 2028 */         Random random = new Random();
/* 2029 */         i = Math.abs(random.nextInt()) / 10 * 10 + 1;
/*      */       } 
/*      */     } 
/*      */     
/* 2033 */     return newFileName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public static Intent createGenericShareIntent(@NonNull Activity activity, @Nullable Uri uri) {
/*      */     Intent sharingIntent;
/* 2046 */     if (uri == null) {
/* 2047 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 2051 */     String uriStr = uri.toString();
/* 2052 */     String mimeType = "application/*";
/*      */     
/* 2054 */     String extension = MimeTypeMap.getFileExtensionFromUrl(uriStr);
/* 2055 */     if (isNullOrEmpty(extension)) {
/* 2056 */       extension = getExtension(getUriDisplayName((Context)activity, uri));
/*      */     }
/* 2058 */     if (!isNullOrEmpty(extension)) {
/* 2059 */       mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
/*      */     }
/*      */ 
/*      */     
/* 2063 */     if (!isExtensionHandled(extension)) {
/* 2064 */       sharingIntent = new Intent("android.intent.action.VIEW");
/* 2065 */       sharingIntent.setDataAndType(uri, mimeType);
/*      */     } else {
/* 2067 */       String signature = String.format(activity.getString(R.string.share_email_body), new Object[] { activity.getString(R.string.app_name) });
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2072 */       sharingIntent = ShareCompat.IntentBuilder.from(activity).setStream(uri).setSubject(uri.getLastPathSegment()).setHtmlText(signature).getIntent();
/* 2073 */       sharingIntent.setType(mimeType);
/* 2074 */       if (isImageFile(activity.getContentResolver(), uri)) {
/* 2075 */         sharingIntent.setClipData(ClipData.newUri(activity.getContentResolver(), "thumbnail", uri));
/*      */       }
/*      */     } 
/* 2078 */     sharingIntent.addFlags(1);
/* 2079 */     return sharingIntent;
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
/*      */   public static Intent createGenericShareIntents(@NonNull Activity activity, ArrayList<Uri> uris) {
/*      */     Intent sharingIntent;
/* 2093 */     if (uris == null || uris.size() == 0) {
/* 2094 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 2098 */     String extension = MimeTypeMap.getFileExtensionFromUrl(((Uri)uris.get(0)).toString());
/* 2099 */     String mimeType = "application/*";
/* 2100 */     if (extension != null) {
/* 2101 */       mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
/*      */     }
/*      */ 
/*      */     
/* 2105 */     boolean isView = false;
/* 2106 */     for (Uri uri : uris) {
/* 2107 */       if (!isExtensionHandled(MimeTypeMap.getFileExtensionFromUrl(uri.toString()))) {
/* 2108 */         isView = true;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/* 2113 */     if (isView) {
/* 2114 */       sharingIntent = new Intent("android.intent.action.VIEW");
/* 2115 */       sharingIntent.setDataAndType(uris.get(0), mimeType);
/*      */     } else {
/* 2117 */       ShareCompat.IntentBuilder intentBuilder = ShareCompat.IntentBuilder.from(activity);
/* 2118 */       for (Uri uri : uris) {
/* 2119 */         if (uri != null) {
/* 2120 */           intentBuilder.addStream(uri);
/*      */         }
/*      */       } 
/*      */       
/* 2124 */       StringBuilder subject = new StringBuilder();
/* 2125 */       subject.append(activity.getResources().getString(R.string.app_name))
/* 2126 */         .append(" ")
/* 2127 */         .append(activity.getResources().getString(R.string.document))
/* 2128 */         .append("s - ");
/* 2129 */       int count = uris.size();
/* 2130 */       for (int i = 0; i < count - 1; i++) {
/* 2131 */         subject.append(((Uri)uris.get(i)).getLastPathSegment())
/* 2132 */           .append(", ");
/*      */       }
/* 2134 */       subject.append(((Uri)uris.get(count - 1)).getLastPathSegment());
/* 2135 */       intentBuilder.setSubject(subject.toString());
/* 2136 */       sharingIntent = intentBuilder.getIntent();
/* 2137 */       sharingIntent.putParcelableArrayListExtra("android.intent.extra.STREAM", uris);
/* 2138 */       sharingIntent.setType(mimeType);
/*      */     } 
/*      */     
/* 2141 */     sharingIntent.addFlags(1);
/* 2142 */     return sharingIntent;
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
/*      */   public static Intent createShareIntent(@NonNull Activity activity, @NonNull File file) {
/* 2155 */     return createGenericShareIntent(activity, getUriForFile((Context)activity, file));
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
/*      */   @Nullable
/*      */   public static Intent createShareIntents(@NonNull Activity activity, ArrayList<FileInfo> files) {
/* 2169 */     if (files == null || files.size() == 0) {
/* 2170 */       return null;
/*      */     }
/* 2172 */     ArrayList<Uri> uris = new ArrayList<>(files.size());
/* 2173 */     for (int i = 0; i < files.size(); i++) {
/* 2174 */       Uri uri = getUriForFile((Context)activity, ((FileInfo)files.get(i)).getFile());
/* 2175 */       if (uri != null) {
/* 2176 */         uris.add(uri);
/*      */       }
/*      */     } 
/* 2179 */     return createGenericShareIntents(activity, uris);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void sharePdfFile(@NonNull Activity activity, @NonNull File file) {
/*      */     try {
/* 2191 */       Intent shareIntent = createShareIntent(activity, file);
/* 2192 */       if (shareIntent != null && shareIntent.resolveActivity(activity.getPackageManager()) != null) {
/* 2193 */         activity.startActivity(Intent.createChooser(shareIntent, activity.getResources().getString(R.string.action_file_share)));
/*      */       }
/* 2195 */     } catch (Exception e) {
/* 2196 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
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
/*      */   public static void sharePdfFiles(@NonNull Activity activity, ArrayList<FileInfo> files) {
/*      */     try {
/* 2209 */       Intent shareIntent = createShareIntents(activity, files);
/* 2210 */       if (shareIntent != null && shareIntent.resolveActivity(activity.getPackageManager()) != null) {
/* 2211 */         activity.startActivity(Intent.createChooser(shareIntent, activity.getResources().getString(R.string.action_file_share)));
/*      */       }
/* 2213 */     } catch (Exception e) {
/* 2214 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
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
/*      */   public static void shareGenericFile(@NonNull Activity activity, @NonNull Uri file) {
/*      */     try {
/* 2227 */       Intent shareIntent = createGenericShareIntent(activity, file);
/* 2228 */       if (shareIntent != null && shareIntent.resolveActivity(activity.getPackageManager()) != null) {
/* 2229 */         activity.startActivity(Intent.createChooser(shareIntent, activity.getResources().getString(R.string.action_file_share)));
/*      */       }
/* 2231 */     } catch (Exception e) {
/* 2232 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
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
/*      */   public static void shareGenericFiles(@NonNull Activity activity, ArrayList<Uri> files) {
/*      */     try {
/* 2245 */       Intent shareIntent = createGenericShareIntents(activity, files);
/* 2246 */       if (shareIntent != null && shareIntent.resolveActivity(activity.getPackageManager()) != null) {
/* 2247 */         activity.startActivity(Intent.createChooser(shareIntent, activity.getResources().getString(R.string.action_file_share)));
/*      */       }
/* 2249 */     } catch (Exception e) {
/* 2250 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ColorPt color2ColorPt(@ColorInt int color) {
/* 2261 */     double red = Color.red(color) / 255.0D;
/* 2262 */     double green = Color.green(color) / 255.0D;
/* 2263 */     double blue = Color.blue(color) / 255.0D;
/* 2264 */     ColorPt colorPt = null;
/*      */     try {
/* 2266 */       colorPt = new ColorPt(red, green, blue);
/* 2267 */     } catch (PDFNetException e) {
/* 2268 */       e.printStackTrace();
/* 2269 */       AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*      */     } 
/* 2271 */     return colorPt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int colorPt2color(ColorPt colorPt) {
/*      */     try {
/* 2282 */       int r = (int)Math.round(colorPt.get(0) * 255.0D);
/* 2283 */       int g = (int)Math.round(colorPt.get(1) * 255.0D);
/* 2284 */       int b = (int)Math.round(colorPt.get(2) * 255.0D);
/* 2285 */       return Color.rgb(r, g, b);
/* 2286 */     } catch (PDFNetException e) {
/* 2287 */       e.printStackTrace();
/*      */       
/* 2289 */       return -1;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void showSoftKeyboard(Context context, @Nullable View view) {
/*      */     try {
/* 2300 */       InputMethodManager imm = (InputMethodManager)context.getSystemService("input_method");
/* 2301 */       if (imm != null) {
/* 2302 */         if (null == view) {
/* 2303 */           imm.toggleSoftInput(2, 1);
/*      */         } else {
/* 2305 */           imm.showSoftInput(view, 1);
/*      */         } 
/*      */       }
/* 2308 */     } catch (Exception exception) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void hideSoftKeyboard(Context context, View view) {
/*      */     try {
/* 2320 */       InputMethodManager imm = (InputMethodManager)context.getSystemService("input_method");
/* 2321 */       if (imm != null && imm.isActive()) {
/* 2322 */         imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
/*      */       }
/* 2324 */     } catch (Exception exception) {}
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
/*      */   @TargetApi(19)
/*      */   public static ExternalFileInfo buildExternalFile(@Nullable Context context, Uri uri) {
/* 2338 */     if (context == null) {
/* 2339 */       return null;
/*      */     }
/*      */     
/* 2342 */     ExternalFileInfo file = null;
/* 2343 */     ExternalFileInfo root = null;
/* 2344 */     String treePath = getUriTreePath(uri);
/* 2345 */     ContentResolver contentResolver = getContentResolver(context);
/* 2346 */     if (contentResolver == null) {
/* 2347 */       return null;
/*      */     }
/*      */     
/*      */     try {
/* 2351 */       List<UriPermission> permissionList = contentResolver.getPersistedUriPermissions();
/* 2352 */       for (UriPermission uriPermission : permissionList) {
/*      */         
/* 2354 */         if (getUriType(uriPermission.getUri()) == 3) {
/* 2355 */           ExternalFileInfo tempRoot = new ExternalFileInfo(context, null, uriPermission.getUri());
/*      */ 
/*      */ 
/*      */           
/* 2359 */           if (tempRoot.exists() && tempRoot.isDirectory()) {
/*      */             
/* 2361 */             String rootTreePath = tempRoot.getTreePath();
/* 2362 */             if (rootTreePath.equals(treePath)) {
/* 2363 */               root = tempRoot; break;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/* 2368 */       if (root != null) {
/* 2369 */         file = root.buildTree(uri);
/*      */       }
/* 2371 */     } catch (Exception e) {
/* 2372 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/* 2373 */       file = null;
/*      */     } 
/*      */     
/* 2376 */     return file;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getUriType(Uri uri) {
/* 2387 */     int type = 0;
/* 2388 */     if (uri != null) {
/* 2389 */       if ("content".equalsIgnoreCase(uri.getScheme())) {
/* 2390 */         String path = uri.getPath();
/* 2391 */         if (path.startsWith("/document/")) {
/* 2392 */           type = 2;
/* 2393 */         } else if (path.startsWith("/tree/")) {
/* 2394 */           type = 3;
/*      */         } 
/* 2396 */       } else if ("file".equalsIgnoreCase(uri.getScheme())) {
/* 2397 */         type = 1;
/*      */       } 
/*      */     }
/* 2400 */     return type;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void handleEmptyPopup(PDFViewCtrl pdfViewCtrl, Markup markup) {
/*      */     try {
/* 2411 */       Popup popup = markup.getPopup();
/* 2412 */       if (popup == null || !popup.isValid()) {
/* 2413 */         popup = Popup.create((Doc)pdfViewCtrl.getDoc(), markup.getRect());
/* 2414 */         popup.setParent((Annot)markup);
/* 2415 */         markup.setPopup(popup);
/*      */       } 
/* 2417 */     } catch (Exception e) {
/* 2418 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isDocModified(PDFDoc pdfDoc) {
/* 2429 */     boolean isModified = false;
/* 2430 */     if (pdfDoc != null) {
/* 2431 */       boolean shouldUnlockRead = false;
/*      */       try {
/* 2433 */         pdfDoc.lockRead();
/* 2434 */         shouldUnlockRead = true;
/* 2435 */         isModified = pdfDoc.isModified();
/* 2436 */       } catch (Exception e) {
/* 2437 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */       } finally {
/* 2439 */         if (shouldUnlockRead) {
/* 2440 */           unlockReadQuietly(pdfDoc);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 2445 */     return isModified;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static JSONObject retrieveToolCache(Context context, String cacheFileName) {
/* 2456 */     ObjectInputStream in = null;
/*      */     try {
/* 2458 */       in = new ObjectInputStream(new FileInputStream(new File(new File(context.getCacheDir(), "") + cacheFileName)));
/* 2459 */       String str = (String)in.readObject();
/* 2460 */       return new JSONObject(str);
/* 2461 */     } catch (Exception e) {
/* 2462 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/* 2463 */       e.printStackTrace();
/*      */     } finally {
/* 2465 */       closeQuietly(in);
/*      */     } 
/* 2467 */     return null;
/*      */   }
/*      */   
/*      */   public static String getCachedContents(JSONObject obj) {
/*      */     try {
/* 2472 */       return obj.getString("contents");
/* 2473 */     } catch (JSONException e) {
/* 2474 */       e.printStackTrace();
/*      */       
/* 2476 */       return "";
/*      */     } 
/*      */   }
/*      */   public static Rect getCachedBBox(JSONObject obj) {
/*      */     try {
/* 2481 */       JSONObject rectObj = obj.getJSONObject("bbox");
/* 2482 */       int x1 = rectObj.getInt("x1");
/* 2483 */       int x2 = rectObj.getInt("x2");
/* 2484 */       int y1 = rectObj.getInt("y1");
/* 2485 */       int y2 = rectObj.getInt("y2");
/* 2486 */       return new Rect(x1, y1, x2, y2);
/* 2487 */     } catch (Exception e) {
/* 2488 */       e.printStackTrace();
/*      */       
/* 2490 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean cacheFileExists(Context context, String cacheFileName) {
/* 2501 */     if (context == null) {
/* 2502 */       return false;
/*      */     }
/* 2504 */     File file = new File(new File(context.getCacheDir(), "") + cacheFileName);
/* 2505 */     Log.d(TAG, "cacheFile: " + file.getAbsolutePath() + "; exists: " + file.exists() + "; length:" + file.length());
/* 2506 */     return (file.exists() && file.length() > 0L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void deleteCacheFile(Context context, String cacheFileName) {
/* 2516 */     if (context == null) {
/*      */       return;
/*      */     }
/* 2519 */     File file = new File(new File(context.getCacheDir(), "") + cacheFileName);
/* 2520 */     if (file.exists())
/*      */     {
/* 2522 */       file.delete();
/*      */     }
/*      */   }
/*      */   
/*      */   @RequiresApi(api = 21)
/*      */   public static Bitmap getBitmapByVecDrawable(VectorDrawable vectorDrawable) {
/* 2528 */     Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable
/* 2529 */         .getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
/* 2530 */     Canvas canvas = new Canvas(bitmap);
/* 2531 */     vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
/* 2532 */     vectorDrawable.draw(canvas);
/* 2533 */     return bitmap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Bitmap convDrawableToBitmap(Drawable drawable) {
/*      */     Bitmap bitmap;
/* 2545 */     if (drawable instanceof BitmapDrawable) {
/* 2546 */       BitmapDrawable bitmapDrawable = (BitmapDrawable)drawable;
/* 2547 */       if (bitmapDrawable.getBitmap() != null) {
/* 2548 */         return bitmapDrawable.getBitmap();
/*      */       }
/*      */     } 
/*      */     
/* 2552 */     if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
/* 2553 */       bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
/*      */     } else {
/* 2555 */       bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
/*      */     } 
/*      */     
/* 2558 */     Canvas canvas = new Canvas(bitmap);
/* 2559 */     drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
/* 2560 */     drawable.draw(canvas);
/* 2561 */     return bitmap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean hasStoragePermission(Context context) {
/* 2571 */     if (isMarshmallow() && (
/* 2572 */       ActivityCompat.checkSelfPermission(context, "android.permission.READ_EXTERNAL_STORAGE") != 0 || 
/*      */       
/* 2574 */       ActivityCompat.checkSelfPermission(context, "android.permission.WRITE_EXTERNAL_STORAGE") != 0)) {
/*      */ 
/*      */       
/* 2577 */       Log.i("permission", "Storage permissions has NOT been granted. Needs to request permissions.");
/* 2578 */       return false;
/*      */     } 
/*      */     
/* 2581 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void requestStoragePermissions(Activity activity, View layout, final int requestCode) {
/* 2591 */     if (layout != null && (
/* 2592 */       ActivityCompat.shouldShowRequestPermissionRationale(activity, "android.permission.READ_EXTERNAL_STORAGE") || 
/*      */       
/* 2594 */       ActivityCompat.shouldShowRequestPermissionRationale(activity, "android.permission.WRITE_EXTERNAL_STORAGE"))) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2600 */       Log.i("permission", "Displaying storage permission rationale to provide additional context.");
/*      */ 
/*      */       
/* 2603 */       final WeakReference<Activity> activityRef = new WeakReference<>(activity);
/* 2604 */       Snackbar.make(layout, R.string.permission_storage_rationale, 0)
/*      */         
/* 2606 */         .setAction(activity.getString(R.string.ok).toUpperCase(), new View.OnClickListener()
/*      */           {
/*      */             public void onClick(View view)
/*      */             {
/* 2610 */               Activity activity = activityRef.get();
/* 2611 */               if (activity != null && !activity.isFinishing())
/*      */               {
/* 2613 */                 ActivityCompat.requestPermissions(activity, Utils.PERMISSIONS_STORAGE, requestCode);
/*      */ 
/*      */               
/*      */               }
/*      */             }
/* 2618 */           }).show();
/*      */     } else {
/*      */       
/* 2621 */       ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, requestCode);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isExtensionHandled(String extension) {
/* 2632 */     return (extension != null && !extension.isEmpty() && 
/* 2633 */       Arrays.<String>asList(Constants.FILE_NAME_EXTENSIONS_VALID).contains(extension.toLowerCase()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isMimeTypeHandled(String mimeType) {
/* 2643 */     return ((mimeType != null && mimeType.startsWith("image/*")) || 
/* 2644 */       isExtensionHandled(MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isItemInList(String item, String[] array) {
/* 2651 */     return (item != null && Arrays.<String>asList(array).contains(item.toLowerCase()));
/*      */   }
/*      */   
/*      */   public static String[] getAllGoogleDocsSupportedTypes() {
/* 2655 */     return (String[])ArrayUtils.addAll((Object[])Constants.ALL_FILE_MIME_TYPES, (Object[])Constants.ALL_GOOGLE_DOCS_TYPES);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getExtension(String filename) {
/* 2662 */     String ext = FilenameUtils.getExtension(filename);
/* 2663 */     if (!isNullOrEmpty(ext)) {
/* 2664 */       return ext.toLowerCase();
/*      */     }
/* 2666 */     return "";
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
/*      */   public static long copyLarge(RandomAccessFile inputFile, OutputStream output) throws IOException {
/* 2678 */     return copyLarge(inputFile, output, new byte[4096]);
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
/*      */   public static long copyLarge(RandomAccessFile inputFile, OutputStream output, byte[] buffer) throws IOException {
/* 2692 */     long count = 0L;
/*      */     
/*      */     int n;
/* 2695 */     for (; -1 != (n = inputFile.read(buffer)); count += n) {
/* 2696 */       output.write(buffer, 0, n);
/*      */     }
/*      */     
/* 2699 */     return count;
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
/*      */   @Nullable
/*      */   public static String getRealPathFromURI(@Nullable Context context, @Nullable Uri uri) {
/* 2716 */     if (context == null || uri == null) {
/* 2717 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/* 2722 */       if (isKitKat() && DocumentsContract.isDocumentUri(context, uri)) {
/* 2723 */         String docId = DocumentsContract.getDocumentId(uri);
/*      */         
/* 2725 */         if ("com.android.externalstorage.documents".equals(uri.getAuthority())) {
/* 2726 */           String[] split = docId.split(":");
/* 2727 */           String type = split[0];
/*      */           
/* 2729 */           if ("primary".equalsIgnoreCase(type)) {
/* 2730 */             return Environment.getExternalStorageDirectory() + "/" + split[1];
/*      */           
/*      */           }
/*      */         }
/*      */         else {
/*      */           
/* 2736 */           if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
/* 2737 */             if (docId.startsWith("raw:")) {
/* 2738 */               return docId.replaceFirst("raw:", "");
/*      */             }
/* 2740 */             Uri contentUri = ContentUris.withAppendedId(
/* 2741 */                 Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId).longValue());
/* 2742 */             return getDataColumn(context, contentUri, null, null);
/*      */           } 
/*      */           
/* 2745 */           if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
/* 2746 */             String[] split = docId.split(":");
/* 2747 */             String type = split[0];
/*      */             
/* 2749 */             Uri contentUri = null;
/* 2750 */             if ("image".equals(type)) {
/* 2751 */               contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
/* 2752 */             } else if ("video".equals(type)) {
/* 2753 */               contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
/* 2754 */             } else if ("audio".equals(type)) {
/* 2755 */               contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
/*      */             } 
/*      */             
/* 2758 */             String selection = "_id=?";
/* 2759 */             String[] selectionArgs = { split[1] };
/*      */ 
/*      */ 
/*      */             
/* 2763 */             return getDataColumn(context, contentUri, "_id=?", selectionArgs);
/*      */           } 
/*      */         } 
/*      */       } else {
/* 2767 */         if ("content".equalsIgnoreCase(uri.getScheme())) {
/* 2768 */           return getDataColumn(context, uri, null, null);
/*      */         }
/*      */         
/* 2771 */         if ("file".equalsIgnoreCase(uri.getScheme())) {
/* 2772 */           return uri.getPath();
/*      */         }
/*      */       } 
/*      */       
/* 2776 */       File actualFile = new File(uri.getPath());
/* 2777 */       if (actualFile.exists()) {
/* 2778 */         return uri.getPath();
/*      */       }
/* 2780 */     } catch (Exception e) {
/* 2781 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/* 2782 */       return null;
/*      */     } 
/*      */     
/* 2785 */     return null;
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
/*      */   public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
/* 2801 */     Cursor cursor = null;
/* 2802 */     String column = "_data";
/* 2803 */     String[] projection = { "_data" };
/*      */ 
/*      */ 
/*      */     
/* 2807 */     ContentResolver contentResolver = getContentResolver(context);
/* 2808 */     if (contentResolver == null) {
/* 2809 */       return null;
/*      */     }
/*      */     
/* 2812 */     try { cursor = contentResolver.query(uri, projection, selection, selectionArgs, null);
/*      */ 
/*      */       
/* 2815 */       int column_index = cursor.getColumnIndexOrThrow("_data");
/* 2816 */       String filepath = cursor.getString(column_index);
/* 2817 */       if (cursor != null && cursor.moveToFirst() && cursor.getCount() > 0 && cursor.getColumnCount() > 0 && (new File(filepath)).exists()) {
/* 2818 */         return filepath;
/*      */       } }
/*      */     
/* 2821 */     catch (Exception exception) {  }
/*      */     finally
/* 2823 */     { if (cursor != null)
/* 2824 */         cursor.close();  }
/*      */     
/* 2826 */     return null;
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
/*      */   public static Single<File> duplicateInFolder(@Nullable final ContentResolver cr, @Nullable final Uri uri, @Nullable final String title, @NonNull final File outputFolder) {
/* 2850 */     return Single.create(new SingleOnSubscribe<File>()
/*      */         {
/*      */           public void subscribe(SingleEmitter<File> emitter) throws Exception {
/* 2853 */             if (cr == null || uri == null || Utils.isNullOrEmpty(title)) {
/* 2854 */               emitter.tryOnError(new IllegalStateException("Missing content resolver, Uri, or file title"));
/*      */               
/*      */               return;
/*      */             } 
/* 2858 */             InputStream is = null;
/*      */             try {
/* 2860 */               String validTitle = Utils.getValidFilename(title);
/* 2861 */               if (!outputFolder.isDirectory()) {
/* 2862 */                 FileUtils.forceMkdir(outputFolder);
/*      */               }
/* 2864 */               File file = new File(outputFolder, validTitle);
/* 2865 */               String tempDownloadPath = Utils.getFileNameNotInUse(file.getAbsolutePath());
/* 2866 */               if (Utils.isNullOrEmpty(tempDownloadPath)) {
/* 2867 */                 emitter.tryOnError(new RuntimeException("Invalid temp download path"));
/*      */                 return;
/*      */               } 
/* 2870 */               File tempFile = new File(tempDownloadPath);
/* 2871 */               is = cr.openInputStream(uri);
/* 2872 */               if (is != null) {
/* 2873 */                 int size = is.available();
/* 2874 */                 if (file.exists() && 
/* 2875 */                   Utils.isNotPdf(file.getAbsolutePath()) && 
/* 2876 */                   !file.getAbsolutePath().equals(tempFile.getAbsolutePath()) && size == file
/* 2877 */                   .length()) {
/*      */                   
/* 2879 */                   tempFile = file;
/* 2880 */                   Log.d(Utils.TAG, "Found office URI cache, skip download.");
/*      */                 } else {
/* 2882 */                   FileUtils.copyInputStreamToFile(is, tempFile);
/*      */                 } 
/*      */               } 
/* 2885 */               emitter.onSuccess(tempFile);
/*      */             } finally {
/* 2887 */               Utils.closeQuietly(is);
/*      */             } 
/*      */           }
/*      */         });
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
/*      */   public static Single<File> duplicateInDownload(@NonNull Context context, @Nullable ContentResolver cr, @Nullable Uri uri, @Nullable String title) {
/* 2902 */     return duplicateInFolder(cr, uri, title, 
/*      */ 
/*      */         
/* 2905 */         getExternalDownloadDirectory(context));
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
/*      */   public static String getValidFilename(@Nullable String filename) {
/* 2917 */     int MAX_FILENAME_LENGTH = 128;
/* 2918 */     int SHORT_SEGMENT_LENGTH = 10;
/*      */     
/* 2920 */     if (isNullOrEmpty(filename)) {
/* 2921 */       Random random = new Random();
/* 2922 */       return "file " + Math.abs(random.nextInt()) + ".pdf";
/*      */     } 
/*      */     
/* 2925 */     if (filename.length() > 128) {
/* 2926 */       filename = filename.substring(0, 128);
/* 2927 */       String[] strs = filename.split(" ");
/* 2928 */       String lastStr = strs[strs.length - 1];
/* 2929 */       if (lastStr.length() <= 10) {
/* 2930 */         filename = filename.substring(0, filename.length() - lastStr.length() - 1);
/*      */       }
/*      */     } 
/*      */     
/* 2934 */     if (!isExtensionHandled(getExtension(filename))) {
/* 2935 */       filename = filename + ".pdf";
/*      */     }
/* 2937 */     return filename;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   public static String getValidTitle(@NonNull Context context, @NonNull Uri fileUri) {
/* 2949 */     int MAX_FILENAME_LENGTH = 32;
/* 2950 */     int SHORT_SEGMENT_LENGTH = 8;
/*      */     
/* 2952 */     String title = getUriDisplayName(context, fileUri);
/* 2953 */     String tag = fileUri.toString();
/* 2954 */     if (isNullOrEmpty(title) || URLUtil.isHttpUrl(tag) || URLUtil.isHttpsUrl(tag)) {
/* 2955 */       title = fileUri.getLastPathSegment();
/* 2956 */       if (isNullOrEmpty(title)) {
/* 2957 */         title = "untitled";
/*      */       }
/*      */     } 
/*      */     
/* 2961 */     if (title.length() > 32) {
/* 2962 */       title = title.substring(0, 32);
/* 2963 */       String[] strs = title.split(" ");
/* 2964 */       String lastStr = strs[strs.length - 1];
/* 2965 */       if (lastStr.length() <= 8) {
/* 2966 */         title = title.substring(0, title.length() - lastStr.length() - 1);
/*      */       }
/*      */     } 
/*      */     
/* 2970 */     return title;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean uriHasReadWritePermission(Context context, Uri uri) {
/* 2981 */     return checkUriPermission(context, uri, "rw");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean uriHasReadPermission(@Nullable Context context, Uri uri) {
/* 2992 */     return (context != null && checkUriPermission(context, uri, "r"));
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
/*      */   private static boolean checkUriPermission(Context context, Uri uri, String permission) {
/* 3004 */     if (context == null || uri == null || isNullOrEmpty(permission)) {
/* 3005 */       return false;
/*      */     }
/* 3007 */     if (permission.equals("r") || permission.equals("rw")) {
/* 3008 */       ParcelFileDescriptor pfd = null;
/* 3009 */       ContentResolver contentResolver = getContentResolver(context);
/* 3010 */       if (contentResolver == null) {
/* 3011 */         return false;
/*      */       }
/*      */       
/* 3014 */       try { pfd = contentResolver.openFileDescriptor(uri, permission);
/* 3015 */         if (pfd != null) {
/* 3016 */           return true;
/*      */         } }
/* 3018 */       catch (Exception exception) {  }
/*      */       finally
/* 3020 */       { closeQuietly(pfd); }
/*      */     
/*      */     } 
/* 3023 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void unlockQuietly(PDFDoc doc) {
/* 3032 */     if (doc != null) {
/*      */       try {
/* 3034 */         doc.unlock();
/* 3035 */       } catch (Exception exception) {}
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
/*      */   public static void unlockReadQuietly(PDFDoc doc) {
/* 3047 */     if (doc != null) {
/*      */       try {
/* 3049 */         doc.unlockRead();
/* 3050 */       } catch (Exception exception) {}
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
/*      */   public static void closeDocQuietly(PDFViewCtrl pdfViewCtrl) {
/*      */     try {
/* 3063 */       if (pdfViewCtrl != null) {
/* 3064 */         pdfViewCtrl.closeDoc();
/*      */       }
/* 3066 */     } catch (Exception exception) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void closeQuietly(SecondaryFileFilter filter) {
/* 3077 */     if (filter != null) {
/* 3078 */       filter.close();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void closeQuietly(PDFDoc doc, SecondaryFileFilter filter) {
/*      */     try {
/* 3090 */       if (doc != null) {
/* 3091 */         doc.close();
/* 3092 */       } else if (filter != null) {
/* 3093 */         filter.close();
/*      */       } 
/* 3095 */     } catch (Exception exception) {}
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
/*      */   public static void closeQuietly(PDFDoc doc) {
/* 3107 */     if (doc != null) {
/*      */       try {
/* 3109 */         doc.close();
/* 3110 */       } catch (Exception exception) {}
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
/*      */   public static void closeQuietly(ParcelFileDescriptor pfd) {
/*      */     try {
/* 3124 */       if (pfd != null) {
/* 3125 */         pfd.close();
/*      */       }
/* 3127 */     } catch (Exception exception) {}
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
/*      */   public static void closeQuietly(Closeable c) {
/*      */     try {
/* 3140 */       if (c != null) {
/* 3141 */         c.close();
/*      */       }
/* 3143 */     } catch (IOException iOException) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void closeQuietly(FDFDoc doc) {
/*      */     try {
/* 3155 */       if (doc != null) {
/* 3156 */         doc.close();
/*      */       }
/* 3158 */     } catch (Exception exception) {}
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
/*      */   @Nullable
/*      */   public static Uri getUriForFile(@NonNull Context context, @NonNull File file) {
/* 3172 */     String filepath = file.getAbsolutePath();
/* 3173 */     String extension = FilenameUtils.getExtension(filepath);
/* 3174 */     filepath = FilenameUtils.removeExtension(filepath);
/* 3175 */     filepath = filepath + "." + extension.toLowerCase();
/*      */     
/* 3177 */     if (!isNougat())
/*      */     {
/* 3179 */       return Uri.fromFile(new File(filepath));
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/* 3184 */       return FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".pdftron.fileprovider", new File(filepath));
/* 3185 */     } catch (Exception e) {
/* 3186 */       AnalyticsHandlerAdapter.getInstance().sendException(e, filepath);
/*      */       
/* 3188 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Intent createShareIntentForFile(@NonNull Context context, @NonNull String filePath, @NonNull String mimeType) {
/* 3199 */     Uri shareFile = getUriForFile(context, new File(filePath));
/* 3200 */     Intent shareIntent = new Intent();
/* 3201 */     shareIntent.setAction("android.intent.action.SEND");
/* 3202 */     shareIntent.putExtra("android.intent.extra.STREAM", (Parcelable)shareFile);
/* 3203 */     shareIntent.setType(mimeType);
/* 3204 */     return shareIntent;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void safeNotifyDataSetChanged(RecyclerView.Adapter adapter) {
/* 3211 */     if (adapter != null) {
/*      */       try {
/* 3213 */         adapter.notifyDataSetChanged();
/* 3214 */       } catch (Exception e) {
/* 3215 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void safeNotifyItemChanged(RecyclerView.Adapter adapter, int position) {
/* 3224 */     if (adapter != null) {
/*      */       try {
/* 3226 */         adapter.notifyItemChanged(position);
/* 3227 */       } catch (Exception e) {
/* 3228 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void safeRemoveItemDecoration(RecyclerView recyclerView, RecyclerView.ItemDecoration itemDecoration) {
/* 3237 */     if (recyclerView != null && itemDecoration != null) {
/*      */       try {
/* 3239 */         recyclerView.removeItemDecoration(itemDecoration);
/* 3240 */       } catch (Exception e) {
/* 3241 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void manageOOM(@Nullable Context context) {
/* 3252 */     manageOOM(context, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void manageOOM(@Nullable PDFViewCtrl pdfViewCtrl) {
/* 3261 */     Context context = (pdfViewCtrl == null) ? null : pdfViewCtrl.getContext();
/* 3262 */     manageOOM(context, pdfViewCtrl);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void manageOOM(@Nullable Context context, @Nullable PDFViewCtrl pdfViewCtrl) {
/* 3272 */     String oldMemStr = "", newMemStr = "";
/* 3273 */     String memoryClass = "";
/* 3274 */     ActivityManager.MemoryInfo memInfo = null;
/* 3275 */     ActivityManager activityManager = null;
/*      */ 
/*      */     
/* 3278 */     if (context != null) {
/* 3279 */       activityManager = (ActivityManager)context.getSystemService("activity");
/* 3280 */       if (activityManager != null) {
/* 3281 */         memInfo = new ActivityManager.MemoryInfo();
/* 3282 */         activityManager.getMemoryInfo(memInfo);
/* 3283 */         oldMemStr = "available memory size before cleanup: " + ((float)memInfo.availMem / 1048576.0F) + "MB, ";
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 3288 */     ImageMemoryCache.getInstance().clearAll();
/* 3289 */     PathPool.getInstance().clear();
/* 3290 */     if (pdfViewCtrl != null)
/*      */     {
/* 3292 */       pdfViewCtrl.purgeMemory();
/*      */     }
/*      */ 
/*      */     
/* 3296 */     if (activityManager != null) {
/* 3297 */       activityManager.getMemoryInfo(memInfo);
/* 3298 */       newMemStr = "available memory size after cleanup: " + ((float)memInfo.availMem / 1048576.0F) + "MB, ";
/* 3299 */       memoryClass = ", memory class: " + activityManager.getMemoryClass();
/*      */     } 
/*      */     
/* 3302 */     if (context != null) {
/* 3303 */       AnalyticsHandlerAdapter.getInstance().sendException(new Exception("OOM - " + oldMemStr + newMemStr + "native heap allocated size: " + (
/*      */             
/* 3305 */             (float)Debug.getNativeHeapAllocatedSize() / 1048576.0F) + "MB" + memoryClass));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public static ContentResolver getContentResolver(@Nullable Context context) {
/* 3315 */     return (context == null) ? null : context.getContentResolver();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public static File getExternalFilesDir(@Nullable Context context, String type) {
/* 3323 */     return (context == null) ? null : context.getExternalFilesDir(type);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @RequiresApi(api = 19)
/*      */   @Nullable
/*      */   public static File[] getExternalFilesDirs(@Nullable Context context, String type) {
/* 3332 */     return (context == null) ? null : context.getExternalFilesDirs(type);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public static Resources getResources(@Nullable Context context) {
/* 3340 */     return (context == null) ? null : context.getResources();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getResourceDrawable(Context context, String name) {
/* 3347 */     int res = 0;
/* 3348 */     if (context != null) {
/* 3349 */       res = context.getResources().getIdentifier(name, "drawable", (context.getApplicationInfo()).packageName);
/*      */     }
/* 3351 */     return res;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getResourceColor(Context context, String name) {
/* 3358 */     int res = 0;
/* 3359 */     if (context != null) {
/* 3360 */       res = context.getResources().getIdentifier(name, "color", (context.getApplicationInfo()).packageName);
/*      */     }
/* 3362 */     return res;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getResourceRaw(Context context, String name) {
/* 3369 */     int res = 0;
/* 3370 */     if (context != null) {
/* 3371 */       res = context.getResources().getIdentifier(name, "raw", (context.getApplicationInfo()).packageName);
/*      */     }
/* 3373 */     return res;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int adjustAlphaColor(int color, float factor) {
/* 3384 */     int alpha = Math.round(Color.alpha(color) * factor);
/* 3385 */     int red = Color.red(color);
/* 3386 */     int green = Color.green(color);
/* 3387 */     int blue = Color.blue(color);
/* 3388 */     return Color.argb(alpha, red, green, blue);
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
/*      */   public static ArrayList<String> getFileNamesFromPortfolio(File file, String password) {
/* 3400 */     ArrayList<String> entryNames = new ArrayList<>();
/*      */     
/* 3402 */     PDFDoc doc = null;
/*      */     try {
/* 3404 */       doc = new PDFDoc(file.getAbsolutePath());
/* 3405 */       if (doc.initStdSecurityHandler(password)) {
/* 3406 */         entryNames = getFileNamesFromPortfolio(doc);
/*      */       }
/* 3408 */     } catch (Exception e) {
/* 3409 */       entryNames = new ArrayList<>();
/*      */     } finally {
/* 3411 */       if (doc != null) {
/*      */         try {
/* 3413 */           doc.close();
/* 3414 */         } catch (Exception exception) {}
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 3420 */     return entryNames;
/*      */   }
/*      */   
/*      */   public static ArrayList<String> getFileNamesFromPortfolio(Context context, Uri fileUri, String password) {
/* 3424 */     ArrayList<String> entryNames = new ArrayList<>();
/*      */     
/* 3426 */     PDFDoc doc = null;
/* 3427 */     SecondaryFileFilter filter = null;
/*      */     try {
/* 3429 */       filter = new SecondaryFileFilter(context, fileUri);
/* 3430 */       doc = new PDFDoc((Filter)filter);
/* 3431 */       if (doc.initStdSecurityHandler(password)) {
/* 3432 */         entryNames = getFileNamesFromPortfolio(doc);
/*      */       }
/* 3434 */     } catch (Exception e) {
/* 3435 */       entryNames = new ArrayList<>();
/*      */     } finally {
/* 3437 */       closeQuietly(doc, filter);
/*      */     } 
/*      */     
/* 3440 */     return entryNames;
/*      */   }
/*      */ 
/*      */   
/*      */   public static ArrayList<String> getFileNamesFromPortfolio(PDFDoc doc) {
/* 3445 */     if (doc == null) {
/* 3446 */       return new ArrayList<>();
/*      */     }
/* 3448 */     ArrayList<String> entryNames = new ArrayList<>();
/* 3449 */     boolean shouldUnlockRead = false;
/*      */     try {
/* 3451 */       doc.lockRead();
/* 3452 */       shouldUnlockRead = true;
/* 3453 */       NameTree files = NameTree.find((Doc)doc.getSDFDoc(), "EmbeddedFiles");
/* 3454 */       if (files.isValid()) {
/* 3455 */         NameTreeIterator i = files.getIterator();
/* 3456 */         while (i.hasNext()) {
/* 3457 */           String entryName = i.key().getAsPDFText();
/* 3458 */           FileSpec file_spec = new FileSpec(i.value());
/* 3459 */           if (file_spec.isValid()) {
/* 3460 */             entryNames.add(file_spec.getFilePath());
/*      */           } else {
/* 3462 */             entryNames.add(entryName);
/*      */           } 
/* 3464 */           i.next();
/*      */         } 
/*      */       } 
/* 3467 */     } catch (Exception e) {
/* 3468 */       entryNames = new ArrayList<>();
/*      */     } finally {
/* 3470 */       if (shouldUnlockRead) {
/* 3471 */         unlockReadQuietly(doc);
/*      */       }
/*      */     } 
/* 3474 */     return entryNames;
/*      */   }
/*      */   
/*      */   public static boolean hasFileAttachments(PDFDoc doc) {
/* 3478 */     boolean result = false;
/* 3479 */     boolean shouldUnlockRead = false;
/*      */     try {
/* 3481 */       doc.lockRead();
/* 3482 */       shouldUnlockRead = true;
/* 3483 */       NameTree file_map = NameTree.find((Doc)doc, "EmbeddedFiles");
/* 3484 */       if (!file_map.isValid()) return false; 
/* 3485 */       result = true;
/* 3486 */     } catch (PDFNetException e) {
/* 3487 */       AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*      */     } finally {
/* 3489 */       if (shouldUnlockRead) {
/* 3490 */         unlockReadQuietly(doc);
/*      */       }
/*      */     } 
/* 3493 */     return result;
/*      */   }
/*      */   
/*      */   public static HashMap<String, String> parseDefaults(Context context, int xmlRes) {
/* 3497 */     HashMap<String, String> result = new HashMap<>();
/* 3498 */     XmlResourceParser parser = context.getResources().getXml(xmlRes);
/* 3499 */     if (parser != null) {
/*      */       try {
/* 3501 */         int eventType = parser.getEventType();
/* 3502 */         String lastKey = null;
/* 3503 */         while (eventType != 1) {
/* 3504 */           if (eventType == 2) {
/* 3505 */             String key = parser.getName();
/* 3506 */             if ((key.equals("key") || key.equals("value")) && parser.next() == 4) {
/* 3507 */               String value = parser.getText();
/* 3508 */               if (key.equals("key")) {
/* 3509 */                 lastKey = value;
/*      */               } else {
/* 3511 */                 result.put(lastKey, value);
/*      */               } 
/* 3513 */               parser.next();
/*      */             } 
/*      */           } 
/* 3516 */           eventType = parser.next();
/*      */         } 
/* 3518 */       } catch (Exception ex) {
/* 3519 */         ex.printStackTrace();
/*      */       } finally {
/* 3521 */         parser.close();
/*      */       } 
/*      */     }
/* 3524 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @ColorInt
/*      */   public static int getAccentColor(@NonNull Context context) {
/* 3535 */     TypedValue value = new TypedValue();
/* 3536 */     context.getTheme().resolveAttribute(R.attr.colorAccent, value, true);
/* 3537 */     return value.data;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @ColorInt
/*      */   public static int getPrimaryColor(@NonNull Context context) {
/* 3548 */     TypedValue value = new TypedValue();
/* 3549 */     context.getTheme().resolveAttribute(R.attr.colorPrimary, value, true);
/* 3550 */     return value.data;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @ColorInt
/*      */   public static int getPrimaryDarkColor(@NonNull Context context) {
/* 3561 */     TypedValue value = new TypedValue();
/* 3562 */     context.getTheme().resolveAttribute(R.attr.colorPrimaryDark, value, true);
/* 3563 */     return value.data;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @ColorInt
/*      */   public static int getForegroundColor(@NonNull Context context) {
/* 3574 */     TypedValue value = new TypedValue();
/* 3575 */     context.getTheme().resolveAttribute(16842800, value, true);
/* 3576 */     return value.data;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @ColorInt
/*      */   public static int getBackgroundColor(@NonNull Context context) {
/* 3587 */     TypedValue value = new TypedValue();
/* 3588 */     context.getTheme().resolveAttribute(16842801, value, true);
/* 3589 */     return value.data;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isDeviceNightMode(@NonNull Context context) {
/* 3599 */     int currentNightMode = (context.getResources().getConfiguration()).uiMode & 0x30;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3609 */     return (currentNightMode == 32);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean applyDayNight(@NonNull AppCompatActivity activity) {
/* 3618 */     boolean isDarkMode = PdfViewCtrlSettingsManager.isDarkMode((Context)activity);
/* 3619 */     if (isDeviceNightMode((Context)activity) != isDarkMode) {
/* 3620 */       int mode = isDarkMode ? 2 : 1;
/*      */ 
/*      */       
/* 3623 */       if (isPie()) {
/* 3624 */         boolean followSystem = PdfViewCtrlSettingsManager.getFollowSystemDarkMode((Context)activity);
/* 3625 */         if (followSystem) {
/* 3626 */           mode = -1;
/*      */         }
/*      */       } 
/*      */       
/* 3630 */       activity.getDelegate().setLocalNightMode(mode);
/* 3631 */       return activity.getDelegate().applyDayNight();
/*      */     } 
/* 3633 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isColorDark(int color) {
/* 3643 */     return isColorDark(color, 0.5F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isColorDark(int color, float threshold) {
/* 3654 */     double bgDarkness = 1.0D - (0.299D * Color.red(color) + 0.587D * Color.green(color) + 0.114D * Color.blue(color)) / 255.0D;
/* 3655 */     return (bgDarkness > threshold);
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
/*      */   public static boolean isTwoColorSimilar(int color1, int color2, float threshold) {
/* 3667 */     double[] lab1 = new double[3];
/* 3668 */     double[] lab2 = new double[3];
/* 3669 */     ColorUtils.colorToLAB(color1, lab1);
/* 3670 */     ColorUtils.colorToLAB(color2, lab2);
/* 3671 */     return (ColorUtils.distanceEuclidean(lab1, lab2) < threshold);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getFontFileName(String fontFilePath) {
/* 3682 */     String fileName = fontFilePath.substring(fontFilePath.lastIndexOf("/") + 1);
/*      */     
/* 3684 */     int lastPeriodPos = fileName.lastIndexOf(".");
/* 3685 */     if (lastPeriodPos > 0) {
/* 3686 */       return fileName.substring(0, lastPeriodPos);
/*      */     }
/* 3688 */     return fileName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getColorHexString(@ColorInt int color) {
/* 3699 */     return String.format("#%06X", new Object[] { Integer.valueOf(0xFFFFFF & color) });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getPostProcessedColor(@NonNull PDFViewCtrl pdfViewCtrl, int color) {
/* 3710 */     ColorPt colorPt = color2ColorPt(color);
/* 3711 */     ColorPt postColorPt = pdfViewCtrl.getPostProcessedColor(colorPt);
/* 3712 */     return colorPt2color(postColorPt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Bitmap replace9PatchColor(Bitmap inputBmp, int toColor) {
/* 3722 */     if (inputBmp == null) {
/* 3723 */       return null;
/*      */     }
/* 3725 */     int width = inputBmp.getWidth();
/* 3726 */     int height = inputBmp.getHeight();
/*      */     
/* 3728 */     Bitmap outputBitmap = Bitmap.createBitmap(width, height, inputBmp.getConfig());
/*      */     
/* 3730 */     int[] allPixels = new int[height * width];
/*      */     
/* 3732 */     inputBmp.getPixels(allPixels, 0, width, 0, 0, width, height);
/*      */     
/* 3734 */     for (int i = 0; i < allPixels.length; i++) {
/* 3735 */       if (allPixels[i] != -1) {
/* 3736 */         allPixels[i] = toColor;
/*      */       }
/*      */     } 
/*      */     
/* 3740 */     outputBitmap.setPixels(allPixels, 0, width, 0, 0, width, height);
/* 3741 */     return outputBitmap;
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
/*      */   public static Bitmap replace9PatchColor(Bitmap inputBmp, int fromColor, int toColor) {
/* 3753 */     if (inputBmp == null) {
/* 3754 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 3758 */     int width = inputBmp.getWidth() - 2;
/* 3759 */     int height = inputBmp.getHeight() - 2;
/* 3760 */     Bitmap insideBmp = Bitmap.createBitmap(inputBmp, 1, 1, width, height);
/* 3761 */     int[] insidePixels = new int[width * height];
/* 3762 */     insideBmp.getPixels(insidePixels, 0, width, 0, 0, width, height);
/*      */     
/* 3764 */     for (int i = 0, cnt = insidePixels.length; i < cnt; i++) {
/* 3765 */       insidePixels[i] = (insidePixels[i] == fromColor) ? toColor : insidePixels[i];
/*      */     }
/*      */     
/* 3768 */     Bitmap outputBitmap = Bitmap.createBitmap(width, height, inputBmp.getConfig());
/* 3769 */     outputBitmap.setPixels(insidePixels, 0, width, 0, 0, width, height);
/* 3770 */     return outputBitmap;
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
/*      */   public static StateListDrawable createImageDrawableSelector(@NonNull Context context, int resourceId, int color) {
/* 3783 */     return createImageDrawableSelector(context, resourceId, color, 98);
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
/*      */   public static StateListDrawable createImageDrawableSelector(@NonNull Context context, int resourceId, int color, int disableOpacity) {
/* 3802 */     Resources resources = context.getResources();
/* 3803 */     Drawable drawable = getDrawable(context, resourceId);
/* 3804 */     if (drawable == null)
/*      */     {
/* 3806 */       return (new StateListDrawableBuilder()).build();
/*      */     }
/* 3808 */     int width = drawable.getMinimumWidth();
/* 3809 */     int height = drawable.getMinimumHeight();
/* 3810 */     Bitmap icon = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
/* 3811 */     Canvas canvas = new Canvas(icon);
/* 3812 */     drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
/* 3813 */     drawable.draw(canvas);
/*      */ 
/*      */     
/* 3816 */     Bitmap enableIcon = tint(icon, color);
/* 3817 */     BitmapDrawable enableDrawable = new BitmapDrawable(resources, enableIcon);
/*      */ 
/*      */     
/* 3820 */     Bitmap disableIcon = adjustAlpha(icon, disableOpacity);
/* 3821 */     BitmapDrawable disableDrawable = new BitmapDrawable(resources, disableIcon);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3826 */     return (new StateListDrawableBuilder()).setDisabledDrawable((Drawable)disableDrawable).setNormalDrawable((Drawable)enableDrawable).build();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Bitmap tint(Bitmap bitmap, int color) {
/* 3837 */     if (bitmap == null) {
/* 3838 */       return null;
/*      */     }
/*      */     
/* 3841 */     Paint paint = new Paint();
/* 3842 */     PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN);
/* 3843 */     paint.setColorFilter((ColorFilter)porterDuffColorFilter);
/*      */     
/* 3845 */     int width = bitmap.getWidth();
/* 3846 */     int height = bitmap.getHeight();
/* 3847 */     Bitmap outputBitmap = Bitmap.createBitmap(width, height, bitmap.getConfig());
/* 3848 */     Canvas canvas = new Canvas(outputBitmap);
/* 3849 */     canvas.drawBitmap(bitmap, 0.0F, 0.0F, paint);
/*      */     
/* 3851 */     return outputBitmap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Bitmap adjustAlpha(Bitmap bitmap, int alpha) {
/* 3862 */     if (bitmap == null) {
/* 3863 */       return null;
/*      */     }
/*      */     
/* 3866 */     Paint paint = new Paint();
/* 3867 */     paint.setAlpha(alpha);
/*      */     
/* 3869 */     int width = bitmap.getWidth();
/* 3870 */     int height = bitmap.getHeight();
/* 3871 */     Bitmap outputBitmap = Bitmap.createBitmap(width, height, bitmap.getConfig());
/* 3872 */     Canvas canvas = new Canvas(outputBitmap);
/* 3873 */     canvas.drawBitmap(bitmap, 0.0F, 0.0F, paint);
/*      */     
/* 3875 */     return outputBitmap;
/*      */   }
/*      */   
/*      */   public static int getThemeAttrColor(@NonNull Context context, int attr) {
/* 3879 */     TypedArray typedArray = context.obtainStyledAttributes(new int[] { attr });
/* 3880 */     int color = typedArray.getColor(0, 0);
/* 3881 */     typedArray.recycle();
/* 3882 */     return color;
/*      */   }
/*      */   
/*      */   public static Drawable getDrawableWithTint(@NonNull Context context, @DrawableRes int drawableId, int tintColor) {
/* 3886 */     Drawable drawable = context.getResources().getDrawable(drawableId);
/* 3887 */     drawable.mutate();
/* 3888 */     drawable.setColorFilter(tintColor, PorterDuff.Mode.SRC_IN);
/* 3889 */     return drawable;
/*      */   }
/*      */   
/*      */   public static String getDeviceName() {
/* 3893 */     String manufacturer = Build.MANUFACTURER;
/* 3894 */     String model = Build.MODEL;
/* 3895 */     if (model.toLowerCase().startsWith(manufacturer.toLowerCase())) {
/* 3896 */       return capitalize(model);
/*      */     }
/* 3898 */     return capitalize(manufacturer) + " " + model;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String capitalize(String s) {
/* 3909 */     if (s == null || s.length() == 0) {
/* 3910 */       return "";
/*      */     }
/* 3912 */     char first = s.charAt(0);
/* 3913 */     if (Character.isUpperCase(first)) {
/* 3914 */       return s;
/*      */     }
/* 3916 */     return Character.toUpperCase(first) + s.substring(1);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isUriSeekable(Context context, Uri uri) {
/* 3921 */     ParcelFileDescriptor pfd = null;
/* 3922 */     ContentResolver contentResolver = getContentResolver(context);
/* 3923 */     boolean seekable = false;
/* 3924 */     if (contentResolver != null) {
/*      */       try {
/* 3926 */         pfd = contentResolver.openFileDescriptor(uri, "rw");
/* 3927 */         seekable = true;
/* 3928 */       } catch (Exception e) {
/* 3929 */         e.printStackTrace();
/*      */       } finally {
/* 3931 */         closeQuietly(pfd);
/*      */       } 
/*      */     }
/* 3934 */     return seekable;
/*      */   }
/*      */   
/*      */   public static String getDateTimeFormatFromField(@Nullable String fieldValue, boolean isDate) {
/* 3938 */     if (fieldValue == null) {
/* 3939 */       return "";
/*      */     }
/* 3941 */     String s = fieldValue;
/*      */     
/* 3943 */     boolean handled = false;
/* 3944 */     if (s.contains("AFTime_Keystroke"))
/*      */     {
/*      */       
/* 3947 */       if (s.contains("AFTime_Keystroke(0)")) {
/* 3948 */         s = "HH:mm";
/* 3949 */         handled = true;
/* 3950 */       } else if (s.contains("AFTime_Keystroke(1)")) {
/* 3951 */         s = "h:mm a";
/* 3952 */         handled = true;
/* 3953 */       } else if (s.contains("AFTime_Keystroke(2)")) {
/* 3954 */         s = "HH:mm:ss";
/* 3955 */         handled = true;
/* 3956 */       } else if (s.contains("AFTime_Keystroke(3)")) {
/* 3957 */         s = "h:mm:ss a";
/* 3958 */         handled = true;
/*      */       } 
/*      */     }
/* 3961 */     if (!handled) {
/* 3962 */       s = s.substring(s.indexOf("\"") + 1);
/* 3963 */       s = s.substring(0, s.indexOf("\""));
/*      */     } 
/* 3965 */     if (isDate) {
/* 3966 */       s = s.replace("YYYY", "yyyy");
/* 3967 */       s = s.replace("DD", "dd");
/* 3968 */       s = s.replace("tt", "a");
/* 3969 */       s = s.replaceAll("m", "M");
/*      */     } else {
/* 3971 */       s = s.replaceAll("M", "m");
/* 3972 */       s = s.replace("tt", "a");
/*      */     } 
/* 3974 */     return s;
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
/*      */   public static void handlePdfFromImageFailed(@NonNull Context context, @NonNull Map imageIntent) {
/* 3988 */     if (ViewerUtils.isImageFromCamera(imageIntent)) {
/* 3989 */       String imageFilePath = ViewerUtils.getImageFilePath(imageIntent);
/* 3990 */       if (imageFilePath != null) {
/* 3991 */         FileUtils.deleteQuietly(new File(imageFilePath));
/*      */       }
/*      */     } 
/*      */     
/* 3995 */     CommonToast.showText(context, R.string.dialog_add_photo_document_filename_error_message, 0);
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
/*      */   public static void setTextCopy(@NonNull Annot annot) {
/*      */     try {
/* 4008 */       annot.getSDFObj().putString("textcopy", "");
/* 4009 */     } catch (PDFNetException e) {
/* 4010 */       AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
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
/*      */   public static void removeTextCopy(@NonNull Annot annot) {
/* 4023 */     if (isTextCopy(annot)) {
/*      */       try {
/* 4025 */         annot.getSDFObj().erase("textcopy");
/* 4026 */       } catch (PDFNetException e) {
/* 4027 */         AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*      */       } 
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
/*      */   public static boolean isTextCopy(@NonNull Annot annot) {
/*      */     try {
/* 4043 */       return (annot.getSDFObj().findObj("textcopy") != null);
/* 4044 */     } catch (PDFNetException e) {
/* 4045 */       AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*      */       
/* 4047 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static long getFreeDiskStorage() {
/*      */     try {
/* 4055 */       StatFs external = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());
/* 4056 */       if (isJellyBeanMR2()) {
/* 4057 */         return external.getFreeBytes();
/*      */       }
/* 4059 */       return (external.getAvailableBlocks() * external.getBlockSize());
/*      */     }
/* 4061 */     catch (Exception e) {
/* 4062 */       e.printStackTrace();
/*      */       
/* 4064 */       return -1L;
/*      */     } 
/*      */   }
/*      */   public static boolean hasEnoughStorageToSave(long fileSize) {
/* 4068 */     if (!isKitKat())
/*      */     {
/*      */       
/* 4071 */       return true;
/*      */     }
/* 4073 */     long MEGABYTE = 1048576L;
/* 4074 */     long freeStorage = getFreeDiskStorage();
/* 4075 */     return (freeStorage > 10L * MEGABYTE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Bookmark getFirstBookmark(PDFDoc pdfDoc) {
/* 4086 */     boolean shouldUnlockRead = false;
/*      */     try {
/* 4088 */       pdfDoc.lockRead();
/* 4089 */       shouldUnlockRead = true;
/* 4090 */       Bookmark firstBookmark = pdfDoc.getFirstBookmark();
/* 4091 */       if (firstBookmark != null && firstBookmark.isValid()) {
/* 4092 */         return firstBookmark;
/*      */       }
/* 4094 */     } catch (PDFNetException e) {
/* 4095 */       AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*      */     } finally {
/* 4097 */       if (shouldUnlockRead) {
/* 4098 */         unlockReadQuietly(pdfDoc);
/*      */       }
/*      */     } 
/* 4101 */     return null;
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
/*      */   @Nullable
/*      */   public static Drawable getDrawable(@Nullable Context context, @DrawableRes int drawableRes) {
/* 4117 */     if (context == null) {
/* 4118 */       return null;
/*      */     }
/*      */     
/* 4121 */     return AppCompatResources.getDrawable(context, drawableRes);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isAnnotationHandlerToolMode(@NonNull ToolManager.ToolMode toolMode) {
/* 4132 */     return (toolMode == ToolManager.ToolMode.ANNOT_EDIT || toolMode == ToolManager.ToolMode.ANNOT_EDIT_LINE || toolMode == ToolManager.ToolMode.ANNOT_EDIT_ADVANCED_SHAPE || toolMode == ToolManager.ToolMode.ANNOT_EDIT_TEXT_MARKUP || toolMode == ToolManager.ToolMode.ANNOT_EDIT_RECT_GROUP || toolMode == ToolManager.ToolMode.LINK_ACTION || toolMode == ToolManager.ToolMode.FORM_FILL || toolMode == ToolManager.ToolMode.RICH_MEDIA);
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
/*      */   public static Point calcIntersection(double ax, double ay, double bx, double by, double cx, double cy, double dx, double dy) {
/* 4144 */     double num = (ay - cy) * (dx - cx) - (ax - cx) * (dy - cy);
/* 4145 */     double den = (bx - ax) * (dy - cy) - (by - ay) * (dx - cx);
/* 4146 */     if (Math.abs(den) < 1.0E-30D) return null; 
/* 4147 */     double r = num / den;
/* 4148 */     double x = ax + r * (bx - ax);
/* 4149 */     double y = ay + r * (by - ay);
/* 4150 */     return new Point(x, y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double angleBetweenTwoPointsWithPivot(double point1X, double point1Y, double point2X, double point2Y, double pivotX, double pivotY) {
/* 4157 */     double angle1 = Math.atan2(point1Y - pivotY, point1X - pivotX);
/* 4158 */     double angle2 = Math.atan2(point2Y - pivotY, point2X - pivotX);
/*      */     
/* 4160 */     return Math.toDegrees(angle2 - angle1);
/*      */   }
/*      */   
/*      */   public static boolean isSamePoint(double ax, double ay, double bx, double by) {
/* 4164 */     double x = Math.abs(ax - bx);
/* 4165 */     double y = Math.abs(ay - by);
/* 4166 */     if (x < 0.1D && y < 0.1D) {
/* 4167 */       return true;
/*      */     }
/* 4169 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double calcLinePointDistance(double x1, double y1, double x2, double y2, double x, double y) {
/* 4177 */     double dx = x2 - x1;
/* 4178 */     double dy = y2 - y1;
/* 4179 */     double d = Math.sqrt(dx * dx + dy * dy);
/* 4180 */     if (d < 1.0E-8D) {
/* 4181 */       return calcDistance(x1, y1, x, y);
/*      */     }
/* 4183 */     return ((x - x2) * dy - (y - y2) * dx) / d;
/*      */   }
/*      */   
/*      */   public static double calcDistance(double x1, double y1, double x2, double y2) {
/* 4187 */     double dx = x2 - x1;
/* 4188 */     double dy = y2 - y1;
/* 4189 */     return Math.sqrt(dx * dx + dy * dy);
/*      */   }
/*      */   
/*      */   public static int findMinIndex(double[] numbers) {
/* 4193 */     if (numbers == null || numbers.length == 0) return -1; 
/* 4194 */     double minVal = numbers[0];
/* 4195 */     int minIdx = 0;
/* 4196 */     for (int i = 1; i < numbers.length; i++) {
/* 4197 */       if (numbers[i] < minVal) {
/* 4198 */         minVal = numbers[i];
/* 4199 */         minIdx = i;
/*      */       } 
/*      */     } 
/* 4202 */     return minIdx;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void copy(File src, File dst) throws IOException {
/* 4207 */     InputStream in = new FileInputStream(src);
/*      */     try {
/* 4209 */       OutputStream out = new FileOutputStream(dst);
/*      */       
/*      */       try {
/* 4212 */         byte[] buf = new byte[1024];
/*      */         int len;
/* 4214 */         while ((len = in.read(buf)) > 0) {
/* 4215 */           out.write(buf, 0, len);
/*      */         }
/*      */       } finally {
/* 4218 */         out.close();
/*      */       } 
/*      */     } finally {
/* 4221 */       in.close();
/*      */     } 
/*      */   }
/*      */   
/*      */   public static boolean isImageCopied(@Nullable Context context) {
/* 4226 */     if (context == null) {
/* 4227 */       return false;
/*      */     }
/* 4229 */     ClipboardManager clipboard = (ClipboardManager)context.getSystemService("clipboard");
/* 4230 */     ContentResolver cr = context.getContentResolver();
/* 4231 */     if (clipboard == null || cr == null) {
/* 4232 */       return false;
/*      */     }
/* 4234 */     ClipData clip = clipboard.getPrimaryClip();
/*      */     
/* 4236 */     if (clip != null) {
/* 4237 */       ClipData.Item item = clip.getItemAt(0);
/* 4238 */       Uri pasteUri = item.getUri();
/* 4239 */       if (isImageFile(cr, pasteUri)) {
/* 4240 */         return true;
/*      */       }
/*      */     } 
/* 4243 */     return false;
/*      */   }
/*      */   
/*      */   public static LinkedHashMap<String, String> convJSONToMap(String jsonString) {
/* 4247 */     if (isNullOrEmpty(jsonString)) {
/* 4248 */       return new LinkedHashMap<>();
/*      */     }
/*      */     try {
/* 4251 */       JSONObject jsonObject = new JSONObject(jsonString);
/* 4252 */       Iterator<String> nameItr = jsonObject.keys();
/* 4253 */       LinkedHashMap<String, String> outMap = new LinkedHashMap<>();
/* 4254 */       while (nameItr.hasNext()) {
/* 4255 */         String name = nameItr.next();
/* 4256 */         outMap.put(name, jsonObject.getString(name));
/*      */       } 
/* 4258 */       return outMap;
/* 4259 */     } catch (Exception ex) {
/* 4260 */       AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*      */       
/* 4262 */       return new LinkedHashMap<>();
/*      */     } 
/*      */   }
/*      */   public static JSONObject convMapToJSON(LinkedHashMap<String, String> item) {
/* 4266 */     if (item != null) {
/*      */       try {
/* 4268 */         return new JSONObject(item);
/* 4269 */       } catch (Exception ex) {
/* 4270 */         AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*      */       } 
/*      */     }
/* 4273 */     return new JSONObject();
/*      */   }
/*      */   
/*      */   public static Single<File> simpleHTTPDownload(final String link, final File outputFile) {
/* 4277 */     return Single.create(new SingleOnSubscribe<File>()
/*      */         {
/*      */           public void subscribe(SingleEmitter<File> emitter) throws Exception
/*      */           {
/* 4281 */             OutputStream filestream = null;
/*      */             try {
/* 4283 */               URL url = new URL(link);
/* 4284 */               if (!outputFile.exists()) {
/* 4285 */                 outputFile.createNewFile();
/*      */               }
/* 4287 */               HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
/* 4288 */               filestream = new BufferedOutputStream(new FileOutputStream(outputFile));
/* 4289 */               IOUtils.copy(urlConnection.getInputStream(), filestream);
/* 4290 */               emitter.onSuccess(outputFile);
/* 4291 */             } catch (Exception e) {
/* 4292 */               AnalyticsHandlerAdapter.getInstance().sendException(e);
/* 4293 */               if (outputFile.exists()) {
/* 4294 */                 outputFile.delete();
/*      */               }
/* 4296 */               emitter.tryOnError(e);
/*      */             } finally {
/* 4298 */               IOUtils.closeQuietly(filestream);
/*      */             } 
/*      */           }
/*      */         });
/*      */   }
/*      */   
/*      */   public static boolean isEmulator() {
/* 4305 */     return (Build.FINGERPRINT.startsWith("generic") || Build.FINGERPRINT
/* 4306 */       .startsWith("unknown") || Build.MODEL
/* 4307 */       .contains("google_sdk") || Build.MODEL
/* 4308 */       .contains("Emulator") || Build.MODEL
/* 4309 */       .contains("Android SDK built for x86") || Build.MANUFACTURER
/* 4310 */       .contains("Genymotion") || (Build.BRAND
/* 4311 */       .startsWith("generic") && Build.DEVICE.startsWith("generic")) || "google_sdk"
/* 4312 */       .equals(Build.PRODUCT));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @RequiresApi(19)
/*      */   public static Intent createSystemPickerIntent() {
/* 4320 */     return createSystemPickerIntent(Constants.ALL_FILE_MIME_TYPES);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @RequiresApi(19)
/*      */   public static Intent createSystemPickerIntent(String[] fileMimeTypes) {
/* 4328 */     Intent intent = new Intent("android.intent.action.OPEN_DOCUMENT");
/* 4329 */     intent.setAction("android.intent.action.OPEN_DOCUMENT");
/* 4330 */     intent.addCategory("android.intent.category.OPENABLE");
/* 4331 */     intent.setType("*/*");
/* 4332 */     intent.putExtra("android.intent.extra.MIME_TYPES", fileMimeTypes);
/* 4333 */     intent.putExtra("android.content.extra.SHOW_ADVANCED", true);
/*      */     
/* 4335 */     intent.putExtra("android.provider.extra.SHOW_ADVANCED", true);
/* 4336 */     return intent;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static File getExternalDownloadDirectory(@NonNull Context context) {
/* 4343 */     return isAndroidQ() ? context
/* 4344 */       .getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) : 
/* 4345 */       Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void throwIfNotOnMainThread() throws IllegalStateException {
/* 4352 */     if (Looper.myLooper() != Looper.getMainLooper()) {
/* 4353 */       throw new IllegalStateException("Method must be invoked from the main thread.");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void throwIfOnMainThread() throws IllegalStateException {
/* 4361 */     if (Looper.myLooper() == Looper.getMainLooper()) {
/* 4362 */       throw new IllegalStateException("Must not be invoked from the main thread.");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getScreenshotFileName() {
/* 4370 */     SimpleDateFormat simpleDate = new SimpleDateFormat("'Screenshot_'yyyyMMdd-HHmmss", Locale.US);
/* 4371 */     return simpleDate.format(Calendar.getInstance().getTime());
/*      */   }
/*      */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pd\\utils\Utils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */