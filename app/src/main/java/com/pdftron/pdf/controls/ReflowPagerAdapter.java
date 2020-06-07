/*     */ package com.pdftron.pdf.controls;
/*     */ 
/*     */ import android.annotation.SuppressLint;
/*     */ import android.app.Activity;
/*     */ import android.content.Context;
/*     */ import android.content.Intent;
/*     */ import android.net.Uri;
/*     */ import android.net.http.SslError;
/*     */ import android.os.AsyncTask;
/*     */ import android.os.Handler;
/*     */ import android.os.Message;
/*     */ import android.util.Log;
/*     */ import android.util.LongSparseArray;
/*     */ import android.util.Patterns;
/*     */ import android.util.SparseArray;
/*     */ import android.view.MotionEvent;
/*     */ import android.view.ScaleGestureDetector;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.webkit.SslErrorHandler;
/*     */ import android.webkit.WebChromeClient;
/*     */ import android.webkit.WebView;
/*     */ import android.webkit.WebViewClient;
/*     */ import android.widget.FrameLayout;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.viewpager.widget.PagerAdapter;
/*     */ import androidx.viewpager.widget.ViewPager;
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.PDFDoc;
/*     */ import com.pdftron.pdf.Page;
/*     */ import com.pdftron.pdf.ReflowProcessor;
/*     */ import com.pdftron.pdf.RequestHandler;
/*     */ import com.pdftron.pdf.asynctask.HtmlPostProcessColorTask;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.ReflowWebView;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import io.reactivex.Completable;
/*     */ import io.reactivex.disposables.CompositeDisposable;
/*     */ import io.reactivex.functions.Action;
/*     */ import io.reactivex.schedulers.Schedulers;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileWriter;
/*     */ import java.io.InputStream;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Locale;
/*     */ import java.util.Vector;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.commons.io.IOUtils;
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
/*     */ public class ReflowPagerAdapter
/*     */   extends PagerAdapter
/*     */   implements ReflowWebView.ReflowWebViewCallback, RequestHandler.RequestHandlerCallback
/*     */ {
/*  73 */   private static final String TAG = ReflowPagerAdapter.class.getName();
/*     */   
/*     */   private static boolean sDebug;
/*     */   private static final String LIGHT_MODE_LOADING_FILE = "file:///android_asset/loading_page_light.html";
/*     */   private static final String DARK_MODE_LOADING_FILE = "file:///android_asset/loading_page_dark.html";
/*     */   private static final String NIGHT_MODE_LOADING_FILE = "file:///android_asset/loading_page_night.html";
/*     */   private static final float TAP_REGION_THRESHOLD = 0.14285715F;
/*     */   
/*     */   private enum ColorMode
/*     */   {
/*  83 */     DayMode,
/*  84 */     NightMode,
/*  85 */     CustomMode;
/*     */   }
/*     */   
/*  88 */   private ColorMode mColorMode = ColorMode.DayMode;
/*  89 */   private int mBackgroundColorMode = 16777215;
/*     */   
/*     */   private RequestHandler mRequestHandler;
/*     */   
/*     */   private PDFDoc mDoc;
/*     */   
/*     */   private int mPageCount;
/*     */   private ViewPager mViewPager;
/*     */   private SparseArray<String> mReflowFiles;
/*     */   private SparseArray<ReflowWebView> mViewHolders;
/*     */   private boolean mIsRtlMode;
/*     */   private boolean mDoTurnPageOnTap;
/*     */   private boolean mIsInternalLinkClicked = false;
/* 102 */   private CopyOnWriteArrayList<PDFDoc> mReflowWithImageCleanup = new CopyOnWriteArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 108 */   private static float[] SCALES = new float[] { 0.05F, 0.1F, 0.25F, 0.5F, 0.75F, 1.0F, 1.25F, 1.5F, 2.0F, 4.0F, 8.0F, 16.0F };
/* 109 */   private final int mDefaultScaleIndex = 5;
/* 110 */   private static final int mMaxIndex = SCALES.length - 1;
/* 111 */   public static final int TH_MIN_SCAlE = Math.round(SCALES[0] * 100.0F);
/* 112 */   public static final int TH_MAX_SCAlE = Math.round(SCALES[mMaxIndex] * 100.0F);
/*     */   private static final float TH_SCAlE_GESTURE = 1.25F;
/* 114 */   private int mScaleIndex = 5;
/*     */   
/*     */   private Context mContext;
/*     */   private float mScaleFactor;
/*     */   private float mLastScaleFactor;
/*     */   private float mThConsecutiveScales;
/*     */   private boolean mZoomInFlag;
/* 121 */   private LongSparseArray<Integer> mObjNumMap = new LongSparseArray();
/* 122 */   private int mLastProcessedObjNum = 0;
/*     */   private ReflowControl.OnPostProcessColorListener mOnPostProcessColorListener;
/* 124 */   private final CopyOnWriteArrayList<HtmlPostProcessColorTask> mHtmlPostProcessColorTasks = new CopyOnWriteArrayList<>();
/*     */   
/* 126 */   private CompositeDisposable mDisposables = new CompositeDisposable();
/*     */   private static final boolean sReflowWithImage = true;
/*     */   
/*     */   private static class ClickHandler
/*     */     extends Handler
/*     */   {
/*     */     private static final int GO_TO_NEXT_PAGE = 1;
/*     */     private static final int GO_TO_PREVIOUS_PAGE = 2;
/*     */     private static final int CLICK_ON_URL = 3;
/*     */     private final WeakReference<ReflowPagerAdapter> mCtrl;
/*     */     
/*     */     ClickHandler(ReflowPagerAdapter ctrl) {
/* 138 */       this.mCtrl = new WeakReference<>(ctrl);
/*     */     }
/*     */ 
/*     */     
/*     */     public void handleMessage(Message msg) {
/* 143 */       ReflowPagerAdapter ctrl = this.mCtrl.get();
/* 144 */       if (ctrl != null && ctrl.mViewPager != null) {
/* 145 */         ViewPager viewPager = ctrl.mViewPager;
/* 146 */         int position = viewPager.getCurrentItem();
/* 147 */         switch (msg.what) {
/*     */           case 1:
/* 149 */             viewPager.setCurrentItem(position + 1);
/*     */             break;
/*     */           case 2:
/* 152 */             viewPager.setCurrentItem(position - 1);
/*     */             break;
/*     */         } 
/*     */       } 
/* 156 */       if (msg.what == 3) {
/* 157 */         removeMessages(1);
/* 158 */         removeMessages(2);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/* 163 */   private final ClickHandler mClickHandler = new ClickHandler(this);
/*     */   
/*     */   private static class UpdateReflowHandler
/*     */     extends Handler {
/*     */     private final WeakReference<ReflowPagerAdapter> mCtrl;
/*     */     
/*     */     UpdateReflowHandler(ReflowPagerAdapter ctrl) {
/* 170 */       this.mCtrl = new WeakReference<>(ctrl);
/*     */     }
/*     */ 
/*     */     
/*     */     public void handleMessage(Message msg) {
/* 175 */       final ReflowPagerAdapter ctrl = this.mCtrl.get();
/* 176 */       if (ctrl != null) {
/* 177 */         Vector<?> params = (Vector)msg.obj;
/* 178 */         final RequestHandler.JobRequestResult result = (RequestHandler.JobRequestResult)params.elementAt(0);
/* 179 */         String outFilename = (String)params.elementAt(1);
/* 180 */         final Object customData = params.elementAt(2);
/*     */ 
/*     */ 
/*     */         
/* 184 */         InputStream is = null;
/* 185 */         String htmlStr = null;
/*     */         try {
/* 187 */           is = new FileInputStream(outFilename);
/* 188 */           htmlStr = IOUtils.toString(is);
/* 189 */         } catch (Exception exception) {
/*     */         
/*     */         } finally {
/* 192 */           if (is != null) {
/* 193 */             Utils.closeQuietly(is);
/*     */           }
/*     */         } 
/* 196 */         if (htmlStr != null) {
/* 197 */           String nightModeAddition = "";
/* 198 */           if (ctrl.mColorMode == ColorMode.NightMode) {
/* 199 */             nightModeAddition = "-webkit-filter: invert(100%) brightness(150%) contrast(90%); filter: invert(100%) brightness(150%) contrast(90%); } html { -webkit-filter: invert(90%); filter: invert(90%); margin: 0vw 5vw 0vw 5vw;}";
/*     */           }
/*     */           
/* 202 */           String css = String.format(Locale.getDefault(), "<style>img { padding: 0; margin: auto; display: block; max-height: 95vh; max-width: 100%%; %s } html {margin: 0vw 5vw 0vw 5vw;} </style>", new Object[] { nightModeAddition });
/*     */           
/* 204 */           htmlStr = htmlStr.replace("</head>", String.format(Locale.getDefault(), "%s\n</head>", new Object[] { css }));
/*     */           
/* 206 */           LinkedHashSet<String> imageNumbersOnPage = new LinkedHashSet<>();
/* 207 */           HashMap<String, Integer> startIndexMap = new HashMap<>();
/* 208 */           HashMap<String, Integer> endIndexMap = new HashMap<>();
/*     */           
/* 210 */           String tag = "reflowimaae";
/* 211 */           int foundIndex = htmlStr.indexOf("reflowimaae");
/* 212 */           int prevFoundIndex = foundIndex;
/* 213 */           String prevString = null;
/* 214 */           while (foundIndex >= 0) {
/* 215 */             String num = htmlStr.substring(foundIndex + "reflowimaae".length(), foundIndex + "reflowimaae".length() + 4);
/* 216 */             if (startIndexMap.get(num) == null) {
/* 217 */               startIndexMap.put(num, Integer.valueOf(foundIndex));
/*     */             }
/* 219 */             if (null == prevString) {
/* 220 */               prevString = num;
/*     */             } else {
/* 222 */               boolean changed = !num.equals(prevString);
/* 223 */               if (changed) {
/* 224 */                 endIndexMap.put(prevString, Integer.valueOf(prevFoundIndex));
/* 225 */                 prevString = num;
/*     */               } 
/*     */             } 
/* 228 */             imageNumbersOnPage.add(num);
/* 229 */             prevFoundIndex = foundIndex;
/* 230 */             foundIndex = htmlStr.indexOf("reflowimaae", foundIndex + 1);
/*     */           } 
/* 232 */           if (prevString != null && 
/* 233 */             endIndexMap.get(prevString) == null) {
/* 234 */             endIndexMap.put(prevString, Integer.valueOf(prevFoundIndex));
/*     */           }
/*     */ 
/*     */           
/* 238 */           int position = ((Integer)customData).intValue();
/* 239 */           int pageNumber = position + 1;
/*     */           
/* 241 */           File cacheDir = ctrl.mContext.getCacheDir();
/* 242 */           File reflowCache = new File(cacheDir, "reflow");
/*     */           
/* 244 */           String processedHtmlStr = htmlStr;
/*     */           
/* 246 */           String[] imageNumbersOnPageArr = (String[])imageNumbersOnPage.toArray((Object[])new String[0]);
/* 247 */           boolean needsReplace = false;
/*     */           
/* 249 */           for (int i = imageNumbersOnPageArr.length - 1; i >= 0; i--) {
/* 250 */             String num = imageNumbersOnPageArr[i];
/* 251 */             int intNum = -1;
/*     */             try {
/* 253 */               intNum = Integer.parseInt(num);
/* 254 */             } catch (Exception exception) {}
/*     */ 
/*     */             
/* 257 */             if (intNum != -1) {
/* 258 */               String filename = String.format(Locale.getDefault(), "image_extract_%d_%04d.png", new Object[] { Integer.valueOf(pageNumber), Integer.valueOf(intNum) });
/* 259 */               File sourceFile = new File(reflowCache, filename);
/* 260 */               String imageStringHTML = String.format(Locale.getDefault(), "<img src=\"%s\" />", new Object[] { sourceFile.getAbsoluteFile() });
/*     */               
/* 262 */               Integer startIndex = startIndexMap.get(num);
/* 263 */               Integer endIndex = endIndexMap.get(num);
/* 264 */               if (startIndex != null && endIndex != null) {
/* 265 */                 String pre = processedHtmlStr.substring(0, startIndex.intValue());
/* 266 */                 String post = processedHtmlStr.substring(endIndex.intValue() + "reflowimaae".length() + 4);
/* 267 */                 processedHtmlStr = pre + imageStringHTML + post;
/* 268 */                 needsReplace = true;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 273 */           if (needsReplace) {
/* 274 */             FileWriter fileWriter = null;
/*     */             try {
/* 276 */               fileWriter = new FileWriter(outFilename);
/* 277 */               fileWriter.write(processedHtmlStr);
/* 278 */             } catch (Exception exception) {
/*     */             
/*     */             } finally {
/* 281 */               if (fileWriter != null) {
/* 282 */                 Utils.closeQuietly(fileWriter);
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 289 */         if (ctrl.mColorMode == ColorMode.CustomMode || ctrl.mColorMode == ColorMode.NightMode) {
/* 290 */           HtmlPostProcessColorTask task = new HtmlPostProcessColorTask(ctrl.mContext, outFilename);
/* 291 */           task.setOnPostProcessColorListener(ctrl.mOnPostProcessColorListener);
/* 292 */           task.setCallback(new HtmlPostProcessColorTask.Callback()
/*     */               {
/*     */                 public void onPostProcessColorFinished(@NonNull HtmlPostProcessColorTask htmlPostProcessColorTask, String outputFilename) {
/* 295 */                   if (ReflowPagerAdapter.sDebug)
/* 296 */                     Log.d(ReflowPagerAdapter.TAG, "update reflow of page #" + (1 + ((Integer)customData).intValue())); 
/* 297 */                   ctrl.updateReflow(result, outputFilename, customData);
/* 298 */                   ctrl.mHtmlPostProcessColorTasks.remove(htmlPostProcessColorTask);
/*     */                 }
/*     */               });
/* 301 */           ctrl.mHtmlPostProcessColorTasks.add(task);
/* 302 */           task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Object[])new Void[0]);
/*     */         } else {
/* 304 */           ctrl.updateReflow(result, outFilename, customData);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/* 310 */   private final UpdateReflowHandler mUpdateReflowHandler = new UpdateReflowHandler(this);
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
/*     */   private ReflowPagerAdapterCallback mCallback;
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
/*     */   public void setListener(ReflowPagerAdapterCallback listener) {
/* 333 */     this.mCallback = listener;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReflowPagerAdapter(ViewPager viewPager, Context context, PDFDoc doc) {
/* 344 */     this.mViewPager = viewPager;
/* 345 */     this.mDoc = doc;
/* 346 */     this.mContext = context;
/* 347 */     this.mRequestHandler = new RequestHandler(this);
/* 348 */     this.mPageCount = 0;
/* 349 */     boolean shouldUnlockRead = false;
/*     */     
/*     */     try {
/* 352 */       this.mDoc.lockRead();
/* 353 */       shouldUnlockRead = true;
/* 354 */       this.mPageCount = this.mDoc.getPageCount();
/* 355 */       this.mReflowFiles = new SparseArray(this.mPageCount);
/* 356 */       this.mViewHolders = new SparseArray(this.mPageCount);
/* 357 */     } catch (PDFNetException e) {
/* 358 */       AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*     */     } finally {
/* 360 */       if (shouldUnlockRead) {
/* 361 */         Utils.unlockReadQuietly(this.mDoc);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 366 */     viewPager.setOffscreenPageLimit(1);
/*     */     
/* 368 */     File cacheDir = context.getCacheDir();
/* 369 */     File reflowCache = new File(cacheDir, "reflow");
/* 370 */     FileUtils.deleteQuietly(reflowCache);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void resetAdapter() {
/* 377 */     int curPosition = this.mViewPager.getCurrentItem();
/* 378 */     this.mViewPager.setAdapter(this);
/* 379 */     this.mViewPager.setCurrentItem(curPosition, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPagesModified() {
/* 386 */     if (sDebug) Log.d(TAG, "pages were modified."); 
/* 387 */     ReflowProcessor.cancelAllRequests();
/* 388 */     this.mPageCount = 0;
/* 389 */     boolean shouldUnlockRead = false;
/*     */     try {
/* 391 */       this.mDoc.lockRead();
/* 392 */       shouldUnlockRead = true;
/* 393 */       this.mPageCount = this.mDoc.getPageCount();
/* 394 */       this.mReflowFiles = new SparseArray(this.mPageCount);
/* 395 */       this.mViewHolders = new SparseArray(this.mPageCount);
/* 396 */     } catch (PDFNetException e) {
/* 397 */       AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*     */     } finally {
/* 399 */       if (shouldUnlockRead) {
/* 400 */         Utils.unlockReadQuietly(this.mDoc);
/*     */       }
/*     */     } 
/* 403 */     this.mObjNumMap.clear();
/* 404 */     this.mLastProcessedObjNum = 0;
/* 405 */     resetAdapter();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cleanup() {
/* 412 */     if (sDebug) Log.d(TAG, "Cleanup"); 
/* 413 */     ReflowProcessor.cancelAllRequests();
/* 414 */     for (HtmlPostProcessColorTask task : this.mHtmlPostProcessColorTasks) {
/* 415 */       task.cancel(true);
/* 416 */       task.setOnPostProcessColorListener(null);
/* 417 */       task.setCallback(null);
/*     */     } 
/* 419 */     this.mClickHandler.removeCallbacksAndMessages(null);
/* 420 */     this.mUpdateReflowHandler.removeCallbacksAndMessages(null);
/* 421 */     this.mDisposables.clear();
/*     */ 
/*     */     
/* 424 */     this.mReflowWithImageCleanup.clear();
/*     */   }
/*     */   
/*     */   @SuppressLint({"SetJavaScriptEnabled"})
/*     */   private ReflowWebView getReflowWebView() {
/* 429 */     ReflowWebView webView = new ReflowWebView(this.mContext);
/* 430 */     if (this.mViewPager instanceof ReflowControl) {
/* 431 */       webView.setOrientation(((ReflowControl)this.mViewPager).getOrientation());
/*     */     }
/* 433 */     webView.clearCache(true);
/* 434 */     webView.getSettings().setJavaScriptEnabled(true);
/* 435 */     webView.setWillNotCacheDrawing(false);
/* 436 */     webView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
/*     */ 
/*     */     
/* 439 */     webView.setListener(this);
/* 440 */     webView.setOnLongClickListener(new View.OnLongClickListener()
/*     */         {
/*     */           public boolean onLongClick(View v) {
/* 443 */             WebView.HitTestResult result = ((WebView)v).getHitTestResult();
/* 444 */             if (result != null) {
/* 445 */               int type = result.getType();
/* 446 */               if (5 == type && 
/* 447 */                 result.getExtra() != null && v.getContext() instanceof Activity) {
/* 448 */                 Uri uri = Uri.parse(result.getExtra());
/* 449 */                 if (uri.getPath() != null) {
/* 450 */                   File imageFile = new File(uri.getPath());
/* 451 */                   if (imageFile.isFile() && imageFile.exists()) {
/* 452 */                     Uri imageUri = Utils.getUriForFile(v.getContext(), imageFile);
/* 453 */                     if (imageUri != null) {
/* 454 */                       Utils.shareGenericFile((Activity)v.getContext(), imageUri);
/*     */                     }
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */             
/* 461 */             return false;
/*     */           }
/*     */         });
/*     */     
/* 465 */     webView.setWebChromeClient(new WebChromeClient());
/* 466 */     webView.setWebViewClient(new WebViewClient()
/*     */         {
/*     */           public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
/*     */           {
/* 470 */             super.onReceivedError(view, errorCode, description, failingUrl);
/* 471 */             Log.e(ReflowPagerAdapter.TAG, description + " url: " + failingUrl);
/*     */           }
/*     */ 
/*     */           
/*     */           public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
/* 476 */             super.onReceivedSslError(view, handler, error);
/* 477 */             Log.e(ReflowPagerAdapter.TAG, error.toString());
/*     */           }
/*     */ 
/*     */           
/*     */           public boolean shouldOverrideUrlLoading(WebView view, String url) {
/* 482 */             if (ReflowPagerAdapter.this.mClickHandler != null) {
/* 483 */               ReflowPagerAdapter.this.mClickHandler.sendEmptyMessage(3);
/*     */             }
/* 485 */             if (url.startsWith("file:///") && url.endsWith(".html")) {
/* 486 */               int slashPos = url.lastIndexOf('/');
/* 487 */               boolean shouldUnlockRead = false;
/*     */               try {
/* 489 */                 long curObjNum = Long.parseLong(url.substring(slashPos + 1, url.length() - 5));
/* 490 */                 int curPageNum = 0;
/* 491 */                 if (ReflowPagerAdapter.this.mObjNumMap.get(curObjNum) != null) {
/* 492 */                   curPageNum = ((Integer)ReflowPagerAdapter.this.mObjNumMap.get(curObjNum)).intValue();
/*     */                 } else {
/*     */                   try {
/* 495 */                     ReflowPagerAdapter.this.mDoc.lockRead();
/* 496 */                     shouldUnlockRead = true;
/* 497 */                     for (int i = ReflowPagerAdapter.this.mLastProcessedObjNum + 1; i <= ReflowPagerAdapter.this.mPageCount; i++) {
/* 498 */                       Page page = ReflowPagerAdapter.this.mDoc.getPage(i);
/* 499 */                       long objNum = page.getSDFObj().getObjNum();
/* 500 */                       ++ReflowPagerAdapter.this.mLastProcessedObjNum;
/* 501 */                       ReflowPagerAdapter.this.mObjNumMap.put(objNum, Integer.valueOf(i));
/* 502 */                       if (objNum == curObjNum) {
/* 503 */                         curPageNum = i;
/*     */                         break;
/*     */                       } 
/*     */                     } 
/* 507 */                   } catch (Exception exception) {
/*     */                   
/*     */                   } finally {
/* 510 */                     if (shouldUnlockRead) {
/* 511 */                       Utils.unlockReadQuietly(ReflowPagerAdapter.this.mDoc);
/*     */                     }
/*     */                   } 
/*     */                 } 
/* 515 */                 if (curPageNum != 0) {
/* 516 */                   ReflowPagerAdapter.this.mIsInternalLinkClicked = true;
/* 517 */                   ReflowPagerAdapter.this.mViewPager.setCurrentItem(ReflowPagerAdapter.this.mIsRtlMode ? (ReflowPagerAdapter.this.mPageCount - curPageNum) : (curPageNum - 1));
/*     */                 } 
/* 519 */               } catch (NumberFormatException e) {
/* 520 */                 return true;
/*     */               }
/*     */             
/* 523 */             } else if (url.startsWith("mailto:") || Patterns.EMAIL_ADDRESS.matcher(url).matches()) {
/* 524 */               if (url.startsWith("mailto:")) {
/* 525 */                 url = url.substring(7);
/*     */               }
/* 527 */               Intent intent = new Intent("android.intent.action.SENDTO", Uri.fromParts("mailto", url, null));
/* 528 */               ReflowPagerAdapter.this.mContext.startActivity(Intent.createChooser(intent, ReflowPagerAdapter.this.mContext.getResources().getString(R.string.tools_misc_sendemail)));
/*     */             } else {
/*     */               
/* 531 */               if (!url.startsWith("https://") && !url.startsWith("http://")) {
/* 532 */                 url = "http://" + url;
/*     */               }
/* 534 */               if (ReflowPagerAdapter.sDebug) Log.d(ReflowPagerAdapter.TAG, url); 
/* 535 */               Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
/* 536 */               ReflowPagerAdapter.this.mContext.startActivity(Intent.createChooser(intent, ReflowPagerAdapter.this.mContext.getResources().getString(R.string.tools_misc_openwith)));
/*     */             } 
/*     */             
/* 539 */             return true;
/*     */           }
/*     */         });
/*     */     
/* 543 */     switch (this.mColorMode) {
/*     */       case DayMode:
/* 545 */         webView.setBackgroundColor(-1);
/*     */         break;
/*     */       case NightMode:
/* 548 */         webView.setBackgroundColor(-16777216);
/*     */         break;
/*     */       case CustomMode:
/* 551 */         webView.setBackgroundColor(this.mBackgroundColorMode);
/*     */         break;
/*     */     } 
/* 554 */     webView.loadUrl("about:blank");
/*     */     
/* 556 */     return webView;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRightToLeftDirection(boolean isRtlMode) {
/* 566 */     this.mIsRtlMode = isRtlMode;
/* 567 */     if (sDebug) Log.d("Reflow Right to Left", this.mIsRtlMode ? "True" : "False");
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRightToLeftDirection() {
/* 574 */     return this.mIsRtlMode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDayMode() {
/* 581 */     this.mColorMode = ColorMode.DayMode;
/* 582 */     updateColorMode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNightMode() {
/* 589 */     this.mColorMode = ColorMode.NightMode;
/* 590 */     updateColorMode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCustomColorMode(int backgroundColorMode) {
/* 597 */     this.mBackgroundColorMode = backgroundColorMode;
/* 598 */     this.mColorMode = ColorMode.CustomMode;
/* 599 */     updateColorMode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isDayMode() {
/* 606 */     return (this.mColorMode == ColorMode.DayMode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isNightMode() {
/* 613 */     return (this.mColorMode == ColorMode.NightMode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isCustomColorMode() {
/* 620 */     return (this.mColorMode == ColorMode.CustomMode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateColorMode() {
/* 630 */     this.mReflowFiles.clear();
/*     */     
/* 632 */     resetAdapter();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void enableTurnPageOnTap(boolean enabled) {
/* 641 */     this.mDoTurnPageOnTap = enabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTextSizeInPercent(int textSize) {
/* 650 */     this.mScaleIndex = 5;
/* 651 */     for (int i = 0; i <= mMaxIndex; i++) {
/* 652 */       if (textSize == Math.round(SCALES[i] * 100.0F)) {
/* 653 */         this.mScaleIndex = i;
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTextSizeInPercent() {
/* 663 */     return Math.round(SCALES[this.mScaleIndex] * 100.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void zoomIn() {
/* 670 */     if (this.mScaleIndex == mMaxIndex) {
/*     */       return;
/*     */     }
/* 673 */     this.mScaleIndex++;
/* 674 */     setTextZoom();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void zoomOut() {
/* 681 */     if (this.mScaleIndex == 0) {
/*     */       return;
/*     */     }
/* 684 */     this.mScaleIndex--;
/* 685 */     setTextZoom();
/*     */   }
/*     */   
/*     */   public void setTextZoom() {
/* 689 */     int position = this.mViewPager.getCurrentItem();
/* 690 */     int indexOfKey = this.mViewHolders.indexOfKey(position);
/* 691 */     if (indexOfKey >= 0) {
/* 692 */       ReflowWebView webView = (ReflowWebView)this.mViewHolders.valueAt(indexOfKey);
/* 693 */       if (webView != null) {
/* 694 */         setTextZoom((WebView)webView);
/* 695 */         webView.invalidate();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setTextZoom(WebView webView) {
/* 701 */     if (webView != null) {
/* 702 */       webView.getSettings().setTextZoom(Math.round(SCALES[this.mScaleIndex] * 100.0F));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInternalLinkClicked() {
/* 710 */     return this.mIsInternalLinkClicked;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetInternalLinkClicked() {
/* 718 */     this.mIsInternalLinkClicked = false;
/*     */   }
/*     */   
/*     */   public int getCurrentPage() {
/* 722 */     return this.mIsRtlMode ? (this.mPageCount - this.mViewPager.getCurrentItem()) : (this.mViewPager.getCurrentItem() + 1);
/*     */   }
/*     */   
/*     */   public void setCurrentPage(int pageNum) {
/* 726 */     this.mViewPager.setCurrentItem(this.mIsRtlMode ? (this.mPageCount - pageNum) : (pageNum - 1), false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void startUpdate(@NonNull ViewGroup container) {}
/*     */ 
/*     */   
/*     */   public int getCount() {
/* 735 */     return this.mPageCount;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
/* 740 */     return (view == object);
/*     */   }
/*     */ 
/*     */   
/*     */   public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
/* 745 */     if (sDebug) Log.d(TAG, "Removing page #" + (position + 1)); 
/* 746 */     FrameLayout frameLayout = (FrameLayout)object;
/* 747 */     frameLayout.removeAllViews();
/* 748 */     this.mViewHolders.put(position, null);
/* 749 */     container.removeView((View)frameLayout);
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public Object instantiateItem(@NonNull ViewGroup container, int position) {
/* 755 */     FrameLayout layout = new FrameLayout(this.mContext);
/* 756 */     ReflowWebView webView = getReflowWebView();
/*     */     
/* 758 */     int pagePosition = this.mIsRtlMode ? (this.mPageCount - 1 - position) : position;
/* 759 */     String filename = (String)this.mReflowFiles.get(pagePosition);
/* 760 */     boolean need_load_flag = true;
/* 761 */     if (filename != null) {
/* 762 */       File file = new File(filename);
/* 763 */       if (file.exists()) {
/* 764 */         need_load_flag = false;
/* 765 */         if (sDebug) Log.d(TAG, "the file at page #" + (position + 1) + " already received"); 
/* 766 */         webView.loadUrl("file:///" + filename);
/* 767 */         setTextZoom((WebView)webView);
/*     */       } 
/*     */     } 
/*     */     
/* 771 */     if (need_load_flag) {
/* 772 */       String loadingFile = "file:///android_asset/loading_page_light.html";
/* 773 */       if (this.mColorMode == ColorMode.NightMode) {
/* 774 */         loadingFile = "file:///android_asset/loading_page_night.html";
/* 775 */       } else if (this.mColorMode == ColorMode.CustomMode) {
/* 776 */         int r = this.mBackgroundColorMode & 0xFF;
/* 777 */         int g = this.mBackgroundColorMode >> 8 & 0xFF;
/* 778 */         int b = this.mBackgroundColorMode >> 16 & 0xFF;
/* 779 */         double luminance = 0.2126D * r + 0.7152D * g + 0.0722D * b;
/* 780 */         if (luminance < 128.0D) {
/* 781 */           loadingFile = "file:///android_asset/loading_page_dark.html";
/*     */         }
/*     */       } 
/* 784 */       webView.loadUrl(loadingFile);
/*     */       
/* 786 */       this.mDisposables.add(requestPageAsync(webView.getContext(), pagePosition, position)
/* 787 */           .subscribeOn(Schedulers.io()).subscribe());
/*     */     } 
/*     */     
/* 790 */     FrameLayout parent = (FrameLayout)webView.getParent();
/* 791 */     if (parent != null)
/*     */     {
/*     */       
/* 794 */       parent.removeAllViews();
/*     */     }
/* 796 */     this.mViewHolders.put(position, webView);
/* 797 */     layout.addView((View)webView);
/* 798 */     container.addView((View)layout);
/* 799 */     return layout;
/*     */   }
/*     */   
/*     */   private Completable requestPageAsync(final Context context, final int pagePosition, final int position) {
/* 803 */     return Completable.fromAction(new Action()
/*     */         {
/*     */           public void run() throws Exception {
/* 806 */             boolean shouldUnlockRead = false;
/*     */             
/*     */             try {
/* 809 */               int pageNumber = pagePosition + 1;
/* 810 */               if (ReflowPagerAdapter.sDebug) Log.d(ReflowPagerAdapter.TAG, "request for page #" + pageNumber); 
/* 811 */               ReflowPagerAdapter.this.mDoc.lockRead();
/* 812 */               shouldUnlockRead = true;
/* 813 */               Page page = ReflowPagerAdapter.this.mDoc.getPage(pageNumber);
/*     */ 
/*     */               
/*     */               try {
/* 817 */                 Page pageForReflow = ReflowUtils.prepareImagesForPage(context, ReflowPagerAdapter.this.mDoc, pageNumber, ReflowPagerAdapter.this.mReflowWithImageCleanup);
/* 818 */                 if (pageForReflow != null) {
/* 819 */                   page = pageForReflow;
/*     */                 }
/* 821 */               } catch (Exception ex) {
/*     */ 
/*     */                 
/* 824 */                 AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*     */               } 
/* 826 */               ReflowProcessor.getReflow(page, ReflowPagerAdapter.this.mRequestHandler, Integer.valueOf(position));
/* 827 */             } catch (Exception e) {
/* 828 */               AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */             } finally {
/* 830 */               if (shouldUnlockRead) {
/* 831 */                 Utils.unlockReadQuietly(ReflowPagerAdapter.this.mDoc);
/*     */               }
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void RequestHandlerProc(RequestHandler.JobRequestResult result, String outFilename, Object customData) {
/* 841 */     Thread.yield();
/* 842 */     Message msg = new Message();
/* 843 */     msg.setTarget(this.mUpdateReflowHandler);
/* 844 */     Vector<Object> v = new Vector();
/* 845 */     v.add(result);
/* 846 */     v.add(outFilename);
/* 847 */     v.add(customData);
/* 848 */     msg.obj = v;
/* 849 */     msg.sendToTarget();
/*     */   }
/*     */   
/*     */   private void updateReflow(RequestHandler.JobRequestResult result, String outFilename, Object customData) {
/* 853 */     int position = ((Integer)customData).intValue();
/* 854 */     int pagePosition = this.mIsRtlMode ? (this.mPageCount - 1 - position) : position;
/*     */     
/* 856 */     ReflowWebView webView = (ReflowWebView)this.mViewHolders.get(position);
/* 857 */     if (webView == null) {
/* 858 */       if (result == RequestHandler.JobRequestResult.FINISHED) {
/*     */         
/* 860 */         if (sDebug) Log.d(TAG, "initially received page #" + (position + 1)); 
/* 861 */         this.mReflowFiles.put(pagePosition, outFilename);
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 867 */     if (result == RequestHandler.JobRequestResult.FAILED) {
/*     */       
/* 869 */       if (sDebug)
/* 870 */         Log.d(TAG, "error in parsing page " + outFilename + " (" + (position + 1) + ")"); 
/* 871 */       webView.loadUrl("about:blank");
/*     */       
/*     */       return;
/*     */     } 
/* 875 */     if (result == RequestHandler.JobRequestResult.CANCELLED) {
/* 876 */       if (sDebug)
/* 877 */         Log.d(TAG, "cancelled parsing page " + outFilename + " (" + (position + 1) + ")"); 
/* 878 */       webView.loadUrl("about:blank");
/*     */       
/*     */       return;
/*     */     } 
/* 882 */     if (sDebug) Log.d(TAG, "received page #" + (position + 1)); 
/* 883 */     this.mReflowFiles.put(pagePosition, outFilename);
/*     */     
/* 885 */     webView.loadUrl("file:///" + outFilename);
/* 886 */     setTextZoom((WebView)webView);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onReflowWebViewScaleBegin(WebView webView, ScaleGestureDetector detector) {
/* 891 */     this.mScaleFactor = this.mLastScaleFactor = SCALES[this.mScaleIndex];
/* 892 */     this.mThConsecutiveScales = 1.25F;
/* 893 */     this.mZoomInFlag = true;
/* 894 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onReflowWebViewScale(WebView webView, ScaleGestureDetector detector) {
/* 899 */     this.mScaleFactor *= detector.getScaleFactor();
/*     */ 
/*     */     
/* 902 */     if (Math.max(this.mLastScaleFactor / this.mScaleFactor, this.mScaleFactor / this.mLastScaleFactor) < 1.25F) {
/* 903 */       return true;
/*     */     }
/*     */     
/* 906 */     if (this.mZoomInFlag && this.mScaleFactor > this.mLastScaleFactor && this.mScaleFactor / this.mLastScaleFactor < this.mThConsecutiveScales) {
/* 907 */       return true;
/*     */     }
/*     */     
/* 910 */     if (!this.mZoomInFlag && this.mLastScaleFactor > this.mScaleFactor && this.mLastScaleFactor / this.mScaleFactor < this.mThConsecutiveScales) {
/* 911 */       return true;
/*     */     }
/*     */     
/* 914 */     if (this.mLastScaleFactor > this.mScaleFactor) {
/* 915 */       if (this.mScaleIndex > 0) {
/* 916 */         this.mScaleIndex--;
/* 917 */         this.mLastScaleFactor = this.mScaleFactor = SCALES[this.mScaleIndex];
/* 918 */         if (this.mZoomInFlag) {
/* 919 */           this.mThConsecutiveScales = 1.25F;
/*     */         }
/* 921 */         this.mZoomInFlag = false;
/*     */       }
/*     */     
/* 924 */     } else if (this.mScaleIndex < mMaxIndex) {
/* 925 */       this.mScaleIndex++;
/* 926 */       this.mLastScaleFactor = this.mScaleFactor = SCALES[this.mScaleIndex];
/* 927 */       if (!this.mZoomInFlag) {
/* 928 */         this.mThConsecutiveScales = 1.25F;
/*     */       }
/* 930 */       this.mZoomInFlag = true;
/*     */     } 
/*     */ 
/*     */     
/* 934 */     setTextZoom(webView);
/*     */     
/* 936 */     this.mThConsecutiveScales *= 1.25F;
/* 937 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onReflowWebViewScaleEnd(WebView webView, ScaleGestureDetector detector) {}
/*     */ 
/*     */   
/*     */   public void onReflowWebViewSingleTapUp(WebView webView, MotionEvent event) {
/* 946 */     if (this.mDoTurnPageOnTap) {
/* 947 */       int x = (int)(event.getX() + 0.5D);
/* 948 */       float width = this.mViewPager.getWidth();
/* 949 */       float widthThresh = width * 0.14285715F;
/* 950 */       int curPosition = this.mViewPager.getCurrentItem();
/* 951 */       if (x <= widthThresh) {
/* 952 */         if (curPosition > 0) {
/* 953 */           if (this.mClickHandler != null) {
/* 954 */             this.mClickHandler.sendEmptyMessageDelayed(2, 200L);
/*     */           }
/*     */           return;
/*     */         } 
/* 958 */       } else if (x >= width - widthThresh && 
/* 959 */         curPosition < this.mPageCount - 1) {
/* 960 */         if (this.mClickHandler != null) {
/* 961 */           this.mClickHandler.sendEmptyMessageDelayed(1, 200L);
/*     */         }
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 967 */     if (this.mCallback != null) {
/* 968 */       this.mCallback.onReflowPagerSingleTapUp(event);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPageTop(WebView webView) {
/* 974 */     setCurrentPage(getCurrentPage() - 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPageBottom(WebView webView) {
/* 979 */     setCurrentPage(getCurrentPage() + 1);
/*     */   }
/*     */   
/*     */   void setOnPostProcessColorListener(ReflowControl.OnPostProcessColorListener listener) {
/* 983 */     this.mOnPostProcessColorListener = listener;
/*     */   }
/*     */   
/*     */   public static void setDebug(boolean debug) {
/* 987 */     sDebug = debug;
/*     */   }
/*     */   
/*     */   public static interface ReflowPagerAdapterCallback {
/*     */     void onReflowPagerSingleTapUp(MotionEvent param1MotionEvent);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\controls\ReflowPagerAdapter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */