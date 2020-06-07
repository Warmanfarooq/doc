/*      */ package com.pdftron.pdf.controls;
/*      */ 
/*      */ import android.content.Context;
/*      */ import android.content.DialogInterface;
/*      */ import android.content.res.Configuration;
/*      */ import android.graphics.Bitmap;
/*      */ import android.graphics.Point;
/*      */ import android.graphics.RectF;
/*      */ import android.graphics.drawable.BitmapDrawable;
/*      */ import android.graphics.drawable.ColorDrawable;
/*      */ import android.graphics.drawable.Drawable;
/*      */ import android.os.Bundle;
/*      */ import android.util.Log;
/*      */ import android.view.Display;
/*      */ import android.view.LayoutInflater;
/*      */ import android.view.MotionEvent;
/*      */ import android.view.View;
/*      */ import android.view.ViewGroup;
/*      */ import android.view.WindowManager;
/*      */ import android.view.animation.AlphaAnimation;
/*      */ import android.view.animation.Animation;
/*      */ import android.view.animation.Interpolator;
/*      */ import android.view.animation.LinearInterpolator;
/*      */ import android.widget.Button;
/*      */ import android.widget.FrameLayout;
/*      */ import android.widget.ImageButton;
/*      */ import android.widget.RelativeLayout;
/*      */ import android.widget.TextView;
/*      */ import androidx.annotation.NonNull;
/*      */ import androidx.appcompat.widget.Toolbar;
/*      */ import androidx.fragment.app.DialogFragment;
/*      */ import com.edmodo.cropper.CropImageView;
/*      */ import com.pdftron.common.Matrix2D;
/*      */ import com.pdftron.common.PDFNetException;
/*      */ import com.pdftron.filters.Filter;
/*      */ import com.pdftron.filters.MappedFile;
/*      */ import com.pdftron.pdf.PDFDoc;
/*      */ import com.pdftron.pdf.PDFRasterizer;
/*      */ import com.pdftron.pdf.PDFViewCtrl;
/*      */ import com.pdftron.pdf.Page;
/*      */ import com.pdftron.pdf.PageIterator;
/*      */ import com.pdftron.pdf.Point;
/*      */ import com.pdftron.pdf.Rect;
/*      */ import com.pdftron.pdf.tools.R;
/*      */ import com.pdftron.pdf.tools.ToolManager;
/*      */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*      */ import com.pdftron.pdf.utils.AnalyticsParam;
/*      */ import com.pdftron.pdf.utils.CommonToast;
/*      */ import com.pdftron.pdf.utils.CustomAsyncTask;
/*      */ import com.pdftron.pdf.utils.ImageMemoryCache;
/*      */ import com.pdftron.pdf.utils.PdfViewCtrlSettingsManager;
/*      */ import com.pdftron.pdf.utils.UserCropUtilities;
/*      */ import com.pdftron.pdf.utils.Utils;
/*      */ import com.pdftron.pdf.widget.ContentLoadingRelativeLayout;
/*      */ import java.io.File;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import org.apache.commons.io.IOUtils;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class UserCropDialogFragment
/*      */   extends DialogFragment
/*      */ {
/*   69 */   private static final String TAG = UserCropDialogFragment.class.getName();
/*      */   
/*      */   private static final int DEFAULT_MEM_CACHE_SIZE = 51200;
/*      */   
/*      */   private static final String BUNDLE_REMOVE_CROP_HELPER_MODE = "removeCropHelperMode";
/*      */   
/*      */   private static final String BUNDLE_IMAGE_CROP_MODE = "imageCropMode";
/*      */   
/*      */   private static final int MAX_PAGES_TO_PRERENDER_PER_DIRECTION = 4;
/*      */   
/*      */   private static final long MILLISECONDS_BEFORE_SHOWING_PROGRESS = 500L;
/*      */   
/*      */   private boolean mImageCropMode;
/*      */   
/*      */   private Bitmap mImageToCrop;
/*      */   
/*      */   private Bitmap mCroppedBitmap;
/*      */   
/*      */   private boolean mRemoveCropHelperMode;
/*      */   
/*      */   private String mLayoutName;
/*      */   
/*      */   private boolean mIsActive;
/*      */   
/*      */   private int mCurrentPage;
/*      */   
/*      */   private int mTotalPages;
/*      */   
/*      */   private PDFViewCtrl mPdfViewCtrl;
/*      */   
/*      */   private Button mCropEvenOddButton;
/*      */   
/*      */   private RelativeLayout mCropPageHost;
/*      */   
/*      */   private View mBlankPagePlaceholder;
/*      */   
/*      */   private ContentLoadingRelativeLayout mBlankPageSpinnerHost;
/*      */   
/*      */   private View mCropImageBorder;
/*      */   
/*      */   private CropImageView mCropImageView;
/*      */   private TextView mPageNumberTextView;
/*      */   private View mDisablingOverlay;
/*      */   private View mProgressBarHost;
/*      */   private boolean mDisablingOverlayShowing;
/*      */   private boolean mSpinnerShowing;
/*      */   private int mPagesToPreRenderPerDirection;
/*      */   private OnUserCropDialogDismissListener mOnUserCropDialogDismissListener;
/*  117 */   private int mCropPageDetails = 1;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean mIsCropped = false;
/*      */ 
/*      */ 
/*      */   
/*      */   private PageCropProperties[] mPageProperties;
/*      */ 
/*      */ 
/*      */   
/*      */   private DrawImageTask mCurrentJob;
/*      */ 
/*      */ 
/*      */   
/*      */   private AlphaAnimation mSpinnerAlphaAnimation;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class PageCropProperties
/*      */   {
/*      */     Rect mUserCropBox;
/*      */ 
/*      */     
/*      */     Rect mCropBox;
/*      */ 
/*      */     
/*      */     int mRotation;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static UserCropDialogFragment newInstance() {
/*  152 */     return newInstance(false, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static UserCropDialogFragment newInstance(boolean removeCropHelperMode, boolean imageCropMode) {
/*  159 */     UserCropDialogFragment fragment = new UserCropDialogFragment();
/*  160 */     Bundle args = new Bundle();
/*  161 */     args.putBoolean("removeCropHelperMode", removeCropHelperMode);
/*  162 */     args.putBoolean("imageCropMode", imageCropMode);
/*  163 */     fragment.setArguments(args);
/*  164 */     return fragment;
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
/*      */   public void onCreate(Bundle savedInstanceState) {
/*  176 */     super.onCreate(savedInstanceState);
/*      */     
/*  178 */     Bundle args = getArguments();
/*  179 */     if (args != null) {
/*  180 */       this.mRemoveCropHelperMode = args.getBoolean("removeCropHelperMode", false);
/*  181 */       this.mImageCropMode = args.getBoolean("imageCropMode", false);
/*      */     } 
/*      */     
/*  184 */     if (savedInstanceState != null && this.mPdfViewCtrl == null) {
/*  185 */       dismiss();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
/*  195 */     View view = inflater.inflate(R.layout.fragment_user_crop_dialog, null);
/*  196 */     if (this.mRemoveCropHelperMode) {
/*  197 */       if (getDialog().getWindow() != null) {
/*  198 */         getDialog().getWindow().setBackgroundDrawable((Drawable)new ColorDrawable(0));
/*      */       }
/*      */     } else {
/*  201 */       int width = 0;
/*  202 */       int height = 0;
/*  203 */       WindowManager wm = (WindowManager)view.getContext().getSystemService("window");
/*  204 */       if (wm != null) {
/*  205 */         Display display = wm.getDefaultDisplay();
/*  206 */         Point size = new Point();
/*  207 */         display.getSize(size);
/*  208 */         width = size.x - 10;
/*  209 */         height = size.y - 10;
/*      */       } 
/*      */       
/*  212 */       int maxImageSize = width * height * 4;
/*  213 */       if (maxImageSize > 0) {
/*  214 */         int maxImages = 51200000 / maxImageSize;
/*  215 */         if (maxImages > 0) {
/*  216 */           this.mPagesToPreRenderPerDirection = Math.min(4, (maxImages - 1) / 2);
/*      */         }
/*      */       } 
/*      */     } 
/*  220 */     initUI(view);
/*  221 */     return view;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public UserCropDialogFragment setPdfViewCtrl(@NonNull PDFViewCtrl pdfViewCtrl) {
/*  232 */     this.mIsActive = true;
/*  233 */     this.mPdfViewCtrl = pdfViewCtrl;
/*  234 */     this.mCurrentPage = pdfViewCtrl.getCurrentPage();
/*  235 */     boolean shouldUnlockRead = false;
/*      */     try {
/*  237 */       pdfViewCtrl.docLockRead();
/*  238 */       shouldUnlockRead = true;
/*  239 */       this.mTotalPages = pdfViewCtrl.getDoc().getPageCount();
/*  240 */       int totalItems = this.mTotalPages + 1;
/*  241 */       this.mPageProperties = new PageCropProperties[totalItems];
/*  242 */     } catch (Exception ex) {
/*  243 */       AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*      */     } finally {
/*  245 */       if (shouldUnlockRead) {
/*  246 */         pdfViewCtrl.docUnlockRead();
/*      */       }
/*      */     } 
/*      */     
/*  250 */     this.mDisablingOverlayShowing = false;
/*  251 */     this.mSpinnerShowing = false;
/*  252 */     this.mPagesToPreRenderPerDirection = 0;
/*      */     
/*  254 */     if (!this.mRemoveCropHelperMode) {
/*  255 */       ImageMemoryCache.getInstance().setMemCacheSize(51200);
/*      */     }
/*      */     
/*  258 */     return this;
/*      */   }
/*      */   
/*      */   private void initUI(final View view) {
/*  262 */     final FrameLayout layout = (FrameLayout)view.findViewById(R.id.layout_root);
/*  263 */     this.mPageNumberTextView = (TextView)view.findViewById(R.id.page_num_text_view);
/*  264 */     this.mLayoutName = layout.getTag().toString();
/*      */     
/*  266 */     this.mDisablingOverlay = view.findViewById(R.id.disabling_overlay);
/*  267 */     this.mProgressBarHost = view.findViewById(R.id.progress_bar_host);
/*  268 */     this.mDisablingOverlay.setOnTouchListener(new View.OnTouchListener()
/*      */         {
/*      */           public boolean onTouch(View v, MotionEvent event)
/*      */           {
/*  272 */             return true;
/*      */           }
/*      */         });
/*      */ 
/*      */     
/*  277 */     this.mCropPageHost = (RelativeLayout)view.findViewById(R.id.page_crop_host);
/*      */     
/*  279 */     this.mCropImageBorder = view.findViewById(R.id.image_crop_border);
/*      */     
/*  281 */     this.mCropImageView = (CropImageView)view.findViewById(R.id.image_crop_view);
/*  282 */     this.mCropImageView.setGuidelines(0);
/*      */     
/*  284 */     this.mCropImageBorder.setVisibility(8);
/*      */     
/*  286 */     this.mBlankPagePlaceholder = view.findViewById(R.id.blank_page_placeholder);
/*  287 */     this.mBlankPageSpinnerHost = (ContentLoadingRelativeLayout)view.findViewById(R.id.blank_page_progress_bar_host);
/*      */     
/*  289 */     Toolbar toolbar = (Toolbar)view.findViewById(R.id.toolbar);
/*  290 */     toolbar.setTitle(R.string.pref_viewmode_user_crop);
/*  291 */     toolbar.setNavigationOnClickListener(new View.OnClickListener()
/*      */         {
/*      */           public void onClick(View v) {
/*  294 */             UserCropDialogFragment.this.dismiss();
/*      */           }
/*      */         });
/*      */     
/*  298 */     if (this.mRemoveCropHelperMode) {
/*  299 */       final View manualCropRoot = view.findViewById(R.id.manual_crop_root_layout);
/*  300 */       manualCropRoot.setVisibility(8);
/*      */       
/*  302 */       setCropBoxAndClose();
/*  303 */     } else if (this.mImageCropMode) {
/*  304 */       final View manualCropRoot = view.findViewById(R.id.manual_crop_root_layout);
/*  305 */       this.mCropPageHost.postDelayed(new Runnable()
/*      */           {
/*      */             public void run()
/*      */             {
/*  309 */               (layout.getLayoutParams()).height = -2;
/*  310 */               (UserCropDialogFragment.this.mCropPageHost.getLayoutParams()).height = -2;
/*  311 */               (manualCropRoot.getLayoutParams()).height = -2;
/*  312 */               manualCropRoot.invalidate();
/*  313 */               UserCropDialogFragment.this.mCropPageHost.invalidate();
/*  314 */               layout.invalidate();
/*  315 */               if ((UserCropDialogFragment.this.mLayoutName.equals("layout-sw480dp-port") || UserCropDialogFragment.this.mLayoutName.equals("layout-sw480dp-land")) && 
/*  316 */                 view.getWidth() < 540) {
/*  317 */                 View buttonHost = view.findViewById(R.id.page_buttons_host);
/*  318 */                 int[] location = new int[2];
/*  319 */                 buttonHost.getLocationInWindow(location);
/*  320 */                 int buttonsLeft = location[0];
/*  321 */                 View pageNumText = view.findViewById(R.id.page_num_text_view);
/*  322 */                 pageNumText.getLocationInWindow(location);
/*  323 */                 int pageNumRight = location[0] + pageNumText.getWidth();
/*  324 */                 if (pageNumRight > buttonsLeft - 10) {
/*  325 */                   RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)buttonHost.getLayoutParams();
/*  326 */                   layoutParams.addRule(11, -1);
/*  327 */                   layoutParams.addRule(14, 0);
/*      */                 } 
/*      */               } 
/*      */ 
/*      */               
/*  332 */               UserCropDialogFragment.this.mCropImageBorder.setVisibility(0);
/*      */               
/*  334 */               if (UserCropDialogFragment.this.mImageToCrop == null) {
/*  335 */                 throw new NullPointerException("setImageToCropBitmap() must be called with a valid Bitmap");
/*      */               }
/*  337 */               UserCropDialogFragment.this.mCropImageView.setImageBitmap(UserCropDialogFragment.this.mImageToCrop);
/*  338 */               UserCropDialogFragment.this.mCropImageView.setFixedAspectRatio(true);
/*      */ 
/*      */ 
/*      */               
/*  342 */               UserCropDialogFragment.this.resizePageProperties(UserCropDialogFragment.this.mImageToCrop.getWidth(), UserCropDialogFragment.this.mImageToCrop.getHeight());
/*      */             }
/*      */           }200L);
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  349 */       this.mCropPageHost.postDelayed(new Runnable()
/*      */           {
/*      */             public void run() {
/*  352 */               if (UserCropDialogFragment.this.mIsActive) {
/*      */                 
/*  354 */                 if ((UserCropDialogFragment.this.mLayoutName.equals("layout-sw480dp-port") || UserCropDialogFragment.this.mLayoutName.equals("layout-sw480dp-land")) && 
/*  355 */                   view.getWidth() < 540) {
/*  356 */                   View buttonHost = view.findViewById(R.id.page_buttons_host);
/*  357 */                   int[] location = new int[2];
/*  358 */                   buttonHost.getLocationInWindow(location);
/*  359 */                   int buttonsLeft = location[0];
/*  360 */                   View pageNumText = view.findViewById(R.id.page_num_text_view);
/*  361 */                   pageNumText.getLocationInWindow(location);
/*  362 */                   int pageNumRight = location[0] + pageNumText.getWidth();
/*  363 */                   if (pageNumRight > buttonsLeft - 10) {
/*  364 */                     RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)buttonHost.getLayoutParams();
/*  365 */                     layoutParams.addRule(11, -1);
/*  366 */                     layoutParams.addRule(14, 0);
/*      */                   } 
/*      */                 } 
/*      */ 
/*      */                 
/*  371 */                 UserCropDialogFragment.this.mCropImageBorder.setVisibility(0);
/*  372 */                 UserCropDialogFragment.this.setPage(UserCropDialogFragment.this.mCurrentPage);
/*      */               } 
/*      */             }
/*      */           }200L);
/*      */     } 
/*      */     
/*  378 */     getButtons(view);
/*      */ 
/*      */     
/*  381 */     if (this.mDisablingOverlayShowing) {
/*  382 */       this.mDisablingOverlay.setVisibility(0);
/*      */     }
/*  384 */     if (this.mSpinnerShowing) {
/*  385 */       this.mProgressBarHost.setVisibility(0);
/*      */     }
/*      */   }
/*      */   
/*      */   public void setImageToCropBitmap(@NonNull Bitmap bitmap) {
/*  390 */     this.mImageToCrop = bitmap;
/*      */   }
/*      */   
/*      */   private void getButtons(View view) {
/*  394 */     ImageButton nextButton = (ImageButton)view.findViewById(R.id.next_button);
/*  395 */     nextButton.setOnClickListener(new View.OnClickListener() {
/*      */           public void onClick(View v) {
/*  397 */             if (UserCropDialogFragment.this.mDisablingOverlayShowing) {
/*      */               return;
/*      */             }
/*  400 */             UserCropDialogFragment.this.switchPage(true);
/*      */           }
/*      */         });
/*  403 */     ImageButton prevButton = (ImageButton)view.findViewById(R.id.prev_button);
/*  404 */     prevButton.setOnClickListener(new View.OnClickListener() {
/*      */           public void onClick(View v) {
/*  406 */             if (UserCropDialogFragment.this.mDisablingOverlayShowing) {
/*      */               return;
/*      */             }
/*  409 */             UserCropDialogFragment.this.switchPage(false);
/*      */           }
/*      */         });
/*      */     
/*  413 */     if (Utils.isRtlLayout(getContext())) {
/*  414 */       nextButton.setImageResource(R.drawable.ic_chevron_left_black_24dp);
/*  415 */       prevButton.setImageResource(R.drawable.ic_chevron_right_black_24dp);
/*      */     } 
/*      */     
/*  418 */     Button doneButton = (Button)view.findViewById(R.id.done_button);
/*  419 */     doneButton.setOnClickListener(new View.OnClickListener() {
/*      */           public void onClick(View v) {
/*  421 */             if (UserCropDialogFragment.this.mDisablingOverlayShowing) {
/*      */               return;
/*      */             }
/*  424 */             UserCropDialogFragment.this.mIsCropped = true;
/*  425 */             UserCropDialogFragment.this.setCropBoxAndClose();
/*  426 */             AnalyticsHandlerAdapter.getInstance().sendEvent(56, 
/*  427 */                 AnalyticsParam.cropPageParam(2, UserCropDialogFragment.this.mCropPageDetails));
/*      */           }
/*      */         });
/*  430 */     Button cropAllButton = (Button)view.findViewById(R.id.crop_all_button);
/*  431 */     cropAllButton.setOnClickListener(new View.OnClickListener() {
/*      */           public void onClick(View v) {
/*  433 */             if (UserCropDialogFragment.this.mDisablingOverlayShowing) {
/*      */               return;
/*      */             }
/*  436 */             UserCropDialogFragment.this.applyCropToPagesAndFlash(MultiApplyMode.All);
/*  437 */             UserCropDialogFragment.this.mCropPageDetails = 2;
/*      */           }
/*      */         });
/*  440 */     this.mCropEvenOddButton = (Button)view.findViewById(R.id.crop_evenodd_button);
/*  441 */     this.mCropEvenOddButton.setOnClickListener(new View.OnClickListener()
/*      */         {
/*      */           public void onClick(View v) {
/*  444 */             if (UserCropDialogFragment.this.mDisablingOverlayShowing) {
/*      */               return;
/*      */             }
/*  447 */             UserCropDialogFragment.this.applyCropToPagesAndFlash((UserCropDialogFragment.this.mCurrentPage % 2 == 0) ? MultiApplyMode.Even : MultiApplyMode.Odd);
/*  448 */             UserCropDialogFragment.this.mCropPageDetails = 3;
/*      */           }
/*      */         });
/*  451 */     this.mCropEvenOddButton.setText((this.mCurrentPage % 2 == 0) ? R.string.user_crop_manual_crop_even_pages : R.string.user_crop_manual_crop_odd_pages);
/*      */     
/*  453 */     if (this.mImageCropMode) {
/*  454 */       nextButton.setVisibility(8);
/*  455 */       prevButton.setVisibility(8);
/*  456 */       cropAllButton.setVisibility(8);
/*  457 */       this.mCropEvenOddButton.setVisibility(8);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onConfigurationChanged(Configuration newConfig) {
/*  466 */     super.onConfigurationChanged(newConfig);
/*      */     
/*  468 */     if (this.mCurrentJob != null && !this.mCurrentJob.isDone()) {
/*  469 */       this.mCurrentJob.cancelRasterizing();
/*      */     }
/*      */     
/*  472 */     this.mCurrentJob = null;
/*      */     
/*  474 */     if (this.mCropImageView.hasBitmap() && !this.mImageCropMode) {
/*  475 */       updatePageCropFromImageView(this.mPageProperties[this.mCurrentPage], this.mCropImageView.getCropRectPercentageMargins());
/*  476 */       this.mCropImageView.getImageBitmapAndClear();
/*      */     } 
/*      */     
/*  479 */     if (!this.mImageCropMode) {
/*  480 */       ImageMemoryCache.getInstance().clearCache();
/*      */     }
/*      */     
/*  483 */     if (this.mSpinnerAlphaAnimation != null && !this.mSpinnerAlphaAnimation.hasEnded()) {
/*  484 */       this.mSpinnerAlphaAnimation.cancel();
/*      */     }
/*  486 */     if (this.mDisablingOverlayShowing) {
/*  487 */       this.mDisablingOverlay.setVisibility(0);
/*      */     }
/*  489 */     if (this.mSpinnerShowing) {
/*  490 */       this.mProgressBarHost.setVisibility(0);
/*      */     }
/*      */     
/*  493 */     ViewGroup viewGroup = (ViewGroup)getView();
/*  494 */     if (viewGroup != null) {
/*  495 */       viewGroup.removeAllViewsInLayout();
/*  496 */       View view = onCreateView(getActivity().getLayoutInflater(), viewGroup, (Bundle)null);
/*  497 */       viewGroup.addView(view);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDismiss(DialogInterface dialog) {
/*  506 */     super.onDismiss(dialog);
/*  507 */     if (this.mCurrentJob != null && !this.mCurrentJob.isDone()) {
/*  508 */       this.mCurrentJob.cancelRasterizing();
/*      */     }
/*      */     
/*  511 */     if (this.mOnUserCropDialogDismissListener != null) {
/*  512 */       this.mOnUserCropDialogDismissListener.onUserCropDialogDismiss(this.mCurrentPage);
/*      */     }
/*      */     
/*  515 */     if (!this.mIsCropped && !this.mRemoveCropHelperMode) {
/*  516 */       AnalyticsHandlerAdapter.getInstance().sendEvent(56, 
/*  517 */           AnalyticsParam.cropPageParam(5));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onPause() {
/*  526 */     if (this.mCurrentJob != null && !this.mCurrentJob.isDone()) {
/*  527 */       this.mCurrentJob.cancelRasterizing();
/*      */     }
/*      */     
/*  530 */     super.onPause();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDestroy() {
/*  538 */     this.mIsActive = false;
/*  539 */     ImageMemoryCache.getInstance().clearCache();
/*  540 */     super.onDestroy();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOnUserCropDialogDismissListener(OnUserCropDialogDismissListener listener) {
/*  549 */     this.mOnUserCropDialogDismissListener = listener;
/*      */   }
/*      */   
/*      */   private void switchPage(boolean next) {
/*  553 */     if (!next && this.mCurrentPage > 1) {
/*  554 */       setPage(this.mCurrentPage - 1);
/*  555 */     } else if (next) {
/*  556 */       boolean shouldUnlockRead = false;
/*      */       try {
/*  558 */         this.mPdfViewCtrl.docLockRead();
/*  559 */         shouldUnlockRead = true;
/*  560 */         int pageCount = this.mPdfViewCtrl.getDoc().getPageCount();
/*  561 */         if (this.mCurrentPage < pageCount) {
/*  562 */           setPage(this.mCurrentPage + 1);
/*      */         }
/*  564 */       } catch (Exception ex) {
/*  565 */         AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*      */       } finally {
/*  567 */         if (shouldUnlockRead) {
/*  568 */           this.mPdfViewCtrl.docUnlockRead();
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private class DrawImageTask
/*      */     extends CustomAsyncTask<Void, Void, Bitmap>
/*      */   {
/*      */     private int mPageNumber;
/*      */     
/*      */     private PDFRasterizer mRasterizer;
/*      */     private Point mViewDimensions;
/*      */     private PDFDoc mPdfDoc;
/*      */     private boolean mGotBitmap;
/*      */     private boolean mIsCancelled;
/*      */     int widthCopy;
/*      */     int heightCopy;
/*      */     
/*      */     public int getPageNumber() {
/*  589 */       return this.mPageNumber;
/*      */     }
/*      */     
/*      */     public boolean cancelRasterizing() {
/*  593 */       if (this.mRasterizer != null) {
/*      */         try {
/*  595 */           this.mRasterizer.setCancel(true);
/*  596 */         } catch (Exception e) {
/*  597 */           AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */         } 
/*      */       }
/*  600 */       return cancel(false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isDone() {
/*  607 */       return (this.mGotBitmap || isCancelled());
/*      */     }
/*      */     
/*      */     public DrawImageTask(Context context, int pageNumber, Point viewDimensions) {
/*  611 */       super(context);
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
/*  665 */       this.widthCopy = 0;
/*  666 */       this.heightCopy = 0; this.mPageNumber = pageNumber; this.mViewDimensions = viewDimensions; this.mPdfDoc = UserCropDialogFragment.this.mPdfViewCtrl.getDoc(); this.mGotBitmap = false; this.mIsCancelled = false; if (viewDimensions.x <= 0.0D || viewDimensions.y <= 0.0D)
/*      */         Log.e(UserCropDialogFragment.TAG, "Dimensions are 0 or less");  try { int customBGColor, customTxtColor; InputStream is; OutputStream os; this.mRasterizer = new PDFRasterizer(); this.mRasterizer.setDrawAnnotations(true); int colorMode = PdfViewCtrlSettingsManager.getColorMode(context); int mode = 0; switch (colorMode) { case 4: mode = 2; customBGColor = PdfViewCtrlSettingsManager.getCustomColorModeBGColor(context); customTxtColor = PdfViewCtrlSettingsManager.getCustomColorModeTextColor(context); this.mRasterizer.setColorPostProcessColors(customBGColor, customTxtColor); break;
/*      */           case 2: mode = 2; is = null; os = null; try { File filterFile = new File(context.getCacheDir(), "sepia_mode_filter.png"); if (!filterFile.exists() || !filterFile.isFile()) { is = UserCropDialogFragment.this.getResources().openRawResource(R.raw.sepia_mode_filter); os = new FileOutputStream(filterFile); IOUtils.copy(is, os); }  this.mRasterizer.setColorPostProcessMapFile((Filter)new MappedFile(filterFile.getAbsolutePath())); } catch (Exception e) { AnalyticsHandlerAdapter.getInstance().sendException(e); } finally { Utils.closeQuietly(is); Utils.closeQuietly(os); }  break;
/*      */           case 3:
/*  670 */             mode = 3; break; }  this.mRasterizer.setColorPostProcessMode(mode); } catch (Exception e) { AnalyticsHandlerAdapter.getInstance().sendException(e); }  } protected Bitmap doInBackground(Void... jobParam) { if (this.mPageNumber > 0) {
/*  671 */         boolean shouldUnlockRead = false;
/*      */         try {
/*  673 */           if (isCancelled() || this.mRasterizer == null) {
/*  674 */             return null;
/*      */           }
/*  676 */           this.mPdfDoc.lockRead();
/*  677 */           shouldUnlockRead = true;
/*  678 */           Page page = this.mPdfDoc.getPage(this.mPageNumber);
/*      */           
/*  680 */           double scaleFactor = 1.0D;
/*      */           
/*  682 */           Rect bbox = page.getCropBox();
/*  683 */           bbox.normalize();
/*      */           
/*  685 */           double pageWidth = page.getPageWidth();
/*  686 */           double pageHeight = page.getPageHeight();
/*      */           
/*  688 */           double pageScaleFactorX = this.mViewDimensions.x / pageWidth;
/*  689 */           double pageScaleFactorY = this.mViewDimensions.y / pageHeight;
/*      */           
/*  691 */           scaleFactor *= Math.max(pageScaleFactorX, pageScaleFactorY);
/*      */           
/*  693 */           if (scaleFactor <= 0.0D) {
/*  694 */             return null;
/*      */           }
/*      */ 
/*      */           
/*  698 */           Matrix2D mtx = new Matrix2D(scaleFactor, 0.0D, 0.0D, scaleFactor, 0.0D, 0.0D);
/*  699 */           mtx = mtx.multiply(page.getDefaultMatrix(true, 1, 0));
/*      */           
/*  701 */           int width = (int)(pageWidth * scaleFactor);
/*  702 */           int height = (int)(pageHeight * scaleFactor);
/*      */           
/*  704 */           int comps = 4;
/*  705 */           int stride = (width * comps + 3) / 4 * 4;
/*      */           
/*  707 */           if (isCancelled()) {
/*  708 */             return null;
/*      */           }
/*      */           
/*  711 */           int[] img = new int[stride * height];
/*      */ 
/*      */ 
/*      */           
/*  715 */           this.widthCopy = width;
/*  716 */           this.heightCopy = height;
/*  717 */           this.mRasterizer.rasterize(page, img, width, height, false, mtx, bbox);
/*  718 */           width = this.widthCopy;
/*  719 */           height = this.heightCopy;
/*      */           
/*  721 */           if (isCancelled()) {
/*  722 */             return null;
/*      */           }
/*      */           
/*  725 */           ImageMemoryCache imageMemoryCache = ImageMemoryCache.getInstance();
/*  726 */           Bitmap bitmap = imageMemoryCache.getBitmapFromReusableSet(width, height, Bitmap.Config.ARGB_8888);
/*  727 */           if (bitmap == null) {
/*  728 */             bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
/*      */           }
/*  730 */           bitmap.setPixels(img, 0, width, 0, 0, width, height);
/*  731 */           return bitmap;
/*  732 */         } catch (Exception e) {
/*  733 */           AnalyticsHandlerAdapter.getInstance().sendException(e);
/*  734 */         } catch (OutOfMemoryError oom) {
/*  735 */           Utils.manageOOM(getContext(), UserCropDialogFragment.this.mPdfViewCtrl);
/*      */         } finally {
/*  737 */           if (shouldUnlockRead) {
/*  738 */             Utils.unlockReadQuietly(this.mPdfDoc);
/*      */           }
/*      */         } 
/*      */       } 
/*  742 */       return null; }
/*      */ 
/*      */ 
/*      */     
/*      */     protected void onCancelled(Bitmap result) {
/*  747 */       super.onCancelled(result);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void onPostExecute(Bitmap result) {
/*  752 */       Context context = getContext();
/*  753 */       if (context == null) {
/*      */         return;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  759 */       if (isCancelled() || result == null || this.mIsCancelled || !UserCropDialogFragment.this.mIsActive) {
/*      */         return;
/*      */       }
/*  762 */       this.mGotBitmap = true;
/*  763 */       if (UserCropDialogFragment.this.mCurrentPage == this.mPageNumber) {
/*  764 */         ImageMemoryCache.getInstance().addBitmapToCache("UserCrop" + this.mPageNumber, new BitmapDrawable(context.getResources(), result));
/*  765 */         UserCropDialogFragment.this.readyNextPage(this.mPageNumber, result);
/*      */       } else {
/*  767 */         ImageMemoryCache.getInstance().addBitmapToCache("UserCrop" + this.mPageNumber, new BitmapDrawable(context.getResources(), result));
/*  768 */         UserCropDialogFragment.this.preRenderIfNecessary();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void setPage(int pageNumber) {
/*  776 */     if (pageNumber != this.mCurrentPage) {
/*  777 */       if (this.mPageProperties[this.mCurrentPage] != null && this.mCropImageView.hasBitmap()) {
/*  778 */         updatePageCropFromImageView(this.mPageProperties[this.mCurrentPage], this.mCropImageView.getCropRectPercentageMargins());
/*  779 */         this.mCropImageView.getImageBitmapAndClear();
/*      */       } 
/*  781 */       this.mCurrentPage = pageNumber;
/*  782 */       this.mCropEvenOddButton.setText((this.mCurrentPage % 2 == 0) ? R.string.user_crop_manual_crop_even_pages : R.string.user_crop_manual_crop_odd_pages);
/*      */     } 
/*      */     
/*  785 */     this.mBlankPageSpinnerHost.hide(true, false);
/*  786 */     this.mBlankPageSpinnerHost.show();
/*  787 */     this.mPageNumberTextView.setText(String.format(getActivity().getResources().getString(R.string.user_crop_manual_crop_page_label), new Object[] { Integer.valueOf(pageNumber) }));
/*      */     
/*  789 */     PDFDoc doc = this.mPdfViewCtrl.getDoc();
/*  790 */     boolean shouldUnlockRead = false;
/*      */     try {
/*  792 */       doc.lockRead();
/*  793 */       shouldUnlockRead = true;
/*  794 */       Page page = doc.getPage(pageNumber);
/*  795 */       PageCropProperties currentProperties = getCropPropertiesForPage(pageNumber);
/*  796 */       Point newPageSize = calculateBlankPageSize(page.getPageWidth(), page.getPageHeight());
/*  797 */       resizePageProperties((int)newPageSize.x, (int)newPageSize.y);
/*  798 */       BitmapDrawable drawable = ImageMemoryCache.getInstance().getBitmapFromCache("UserCrop" + pageNumber);
/*  799 */       if (drawable != null) {
/*  800 */         readyNextPage(pageNumber, drawable.getBitmap());
/*      */       }
/*  802 */       else if (this.mCurrentJob == null || this.mCurrentJob.getPageNumber() != pageNumber) {
/*  803 */         if (this.mCurrentJob != null && !this.mCurrentJob.isDone()) {
/*  804 */           this.mCurrentJob.cancelRasterizing();
/*      */         }
/*      */         
/*  807 */         this.mCurrentJob = new DrawImageTask((Context)getActivity(), pageNumber, newPageSize);
/*  808 */         this.mCurrentJob.execute((Object[])new Void[0]);
/*      */       }
/*      */     
/*  811 */     } catch (Exception e) {
/*  812 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } finally {
/*  814 */       if (shouldUnlockRead) {
/*  815 */         Utils.unlockReadQuietly(doc);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private PageCropProperties getCropPropertiesForPage(int pageNumber) {
/*  821 */     PDFDoc doc = this.mPdfViewCtrl.getDoc();
/*  822 */     boolean shouldUnlockRead = false;
/*      */     
/*      */     try {
/*  825 */       doc.lockRead();
/*  826 */       shouldUnlockRead = true;
/*  827 */       Page page = doc.getPage(pageNumber);
/*  828 */       PageCropProperties currentProperties = this.mPageProperties[pageNumber];
/*  829 */       if (currentProperties == null) {
/*  830 */         currentProperties = new PageCropProperties();
/*  831 */         this.mPageProperties[pageNumber] = currentProperties;
/*      */       } 
/*  833 */       if (currentProperties.mCropBox == null) {
/*  834 */         currentProperties.mCropBox = page.getCropBox();
/*  835 */         currentProperties.mUserCropBox = page.getBox(5);
/*  836 */         currentProperties.mRotation = page.getRotation();
/*      */       } 
/*  838 */       return currentProperties;
/*  839 */     } catch (PDFNetException e) {
/*  840 */       AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*      */     } finally {
/*  842 */       if (shouldUnlockRead) {
/*  843 */         Utils.unlockReadQuietly(doc);
/*      */       }
/*      */     } 
/*  846 */     return null;
/*      */   }
/*      */   
/*      */   private void readyNextPage(int pageNumber, Bitmap newBitmap) {
/*  850 */     if (pageNumber == this.mCurrentPage && newBitmap != null) {
/*  851 */       Bitmap oldBitmap = this.mCropImageView.getImageBitmapAndClear();
/*  852 */       ImageMemoryCache.getInstance().addBitmapToReusableSet(oldBitmap);
/*      */       
/*  854 */       this.mCropImageView.setImageBitmap(newBitmap);
/*      */ 
/*      */       
/*  857 */       resizePageProperties(newBitmap.getWidth(), newBitmap.getHeight());
/*      */       
/*  859 */       SetCropRectOnView();
/*      */     } 
/*  861 */     preRenderIfNecessary();
/*      */   }
/*      */   
/*      */   private void preRenderIfNecessary() {
/*  865 */     if (this.mCurrentJob != null && !this.mCurrentJob.isDone()) {
/*      */       return;
/*      */     }
/*  868 */     int pageToRender = getNextPageToPreRender(this.mCurrentPage);
/*  869 */     while (pageToRender > 0 && pageToRender <= this.mTotalPages && Math.abs(pageToRender - this.mCurrentPage) <= this.mPagesToPreRenderPerDirection) {
/*  870 */       BitmapDrawable drawable = ImageMemoryCache.getInstance().getBitmapFromCache("UserCrop" + pageToRender);
/*  871 */       if (drawable == null) {
/*  872 */         PDFDoc doc = this.mPdfViewCtrl.getDoc();
/*  873 */         if (doc == null) {
/*      */           return;
/*      */         }
/*  876 */         boolean shouldUnlockRead = false;
/*      */         
/*      */         try {
/*  879 */           doc.lockRead();
/*  880 */           shouldUnlockRead = true;
/*  881 */           Page page = doc.getPage(pageToRender);
/*  882 */           Point newPageSize = calculateBlankPageSize(page.getPageWidth(), page.getPageHeight());
/*  883 */           this.mCurrentJob = new DrawImageTask((Context)getActivity(), pageToRender, newPageSize);
/*  884 */           this.mCurrentJob.execute((Object[])new Void[0]);
/*  885 */         } catch (PDFNetException e) {
/*  886 */           AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*      */         } finally {
/*  888 */           if (shouldUnlockRead) {
/*  889 */             Utils.unlockReadQuietly(doc);
/*      */           }
/*      */         } 
/*      */         return;
/*      */       } 
/*  894 */       pageToRender = getNextPageToPreRender(pageToRender);
/*      */     } 
/*      */   }
/*      */   
/*      */   private int getNextPageToPreRender(int lastRenderedPage) {
/*  899 */     if (lastRenderedPage < 1 || lastRenderedPage > this.mTotalPages) {
/*  900 */       return -1;
/*      */     }
/*  902 */     if (lastRenderedPage > this.mCurrentPage) {
/*  903 */       int i = Math.abs(this.mCurrentPage - lastRenderedPage);
/*  904 */       int j = lastRenderedPage - i - i;
/*  905 */       if (j < 1) {
/*  906 */         return lastRenderedPage + 1;
/*      */       }
/*  908 */       return j;
/*      */     } 
/*  910 */     int pageDiff = Math.abs(this.mCurrentPage - lastRenderedPage);
/*  911 */     int nextPage = lastRenderedPage + pageDiff + pageDiff + 1;
/*  912 */     if (nextPage > this.mTotalPages) {
/*  913 */       return lastRenderedPage - 1;
/*      */     }
/*  915 */     return nextPage;
/*      */   }
/*      */ 
/*      */   
/*      */   private void SetCropRectOnView() {
/*  920 */     if (this.mPageProperties[this.mCurrentPage] != null && (this.mPageProperties[this.mCurrentPage]).mUserCropBox != null) {
/*      */       try {
/*  922 */         Rect cropBox = (this.mPageProperties[this.mCurrentPage]).mCropBox;
/*  923 */         if (cropBox.getWidth() > 0.0D && cropBox.getHeight() > 0.0D) {
/*  924 */           Rect userCropBox = (this.mPageProperties[this.mCurrentPage]).mUserCropBox;
/*  925 */           RectF cropPercentageRect = new RectF();
/*  926 */           cropPercentageRect.left = (float)((userCropBox.getX1() - cropBox.getX1()) / cropBox.getWidth());
/*  927 */           cropPercentageRect.right = (float)((cropBox.getX2() - userCropBox.getX2()) / cropBox.getWidth());
/*  928 */           cropPercentageRect.bottom = (float)((userCropBox.getY1() - cropBox.getY1()) / cropBox.getHeight());
/*  929 */           cropPercentageRect.top = (float)((cropBox.getY2() - userCropBox.getY2()) / cropBox.getHeight());
/*      */           
/*  931 */           rotateRectangle(cropPercentageRect, (this.mPageProperties[this.mCurrentPage]).mRotation);
/*      */           
/*  933 */           this.mCropImageView.setCropRectPercentageMargins(cropPercentageRect);
/*      */         } 
/*  935 */       } catch (Exception e) {
/*  936 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private void updatePageCropFromImageView(PageCropProperties properties, RectF percentageRect) {
/*  942 */     if (properties != null) {
/*      */       try {
/*  944 */         int rot = Page.subtractRotations(0, properties.mRotation);
/*  945 */         rotateRectangle(percentageRect, rot);
/*      */         
/*  947 */         Rect cropBox = properties.mCropBox;
/*  948 */         if (cropBox != null && cropBox.getWidth() > 0.0D && cropBox.getHeight() > 0.0D) {
/*  949 */           if (properties.mUserCropBox == null) {
/*  950 */             properties.mUserCropBox = new Rect();
/*      */           }
/*  952 */           properties.mUserCropBox.setX1(cropBox.getX1() + percentageRect.left * cropBox.getWidth());
/*  953 */           properties.mUserCropBox.setX2(cropBox.getX2() - percentageRect.right * cropBox.getWidth());
/*  954 */           properties.mUserCropBox.setY1(cropBox.getY1() + percentageRect.bottom * cropBox.getHeight());
/*  955 */           properties.mUserCropBox.setY2(cropBox.getY2() - percentageRect.top * cropBox.getHeight());
/*      */         } 
/*  957 */       } catch (Exception e) {
/*  958 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private Point calculateBlankPageSize(double pageWidth, double pageHeight) {
/*  964 */     int marginSize = this.mCropImageView.getMarginSize();
/*      */     
/*  966 */     double hostWidth = (this.mCropPageHost.getMeasuredWidth() - 2 * marginSize);
/*  967 */     double hostHeight = (this.mCropPageHost.getMeasuredHeight() - 2 * marginSize);
/*      */ 
/*      */     
/*  970 */     double widthScale = hostWidth / pageWidth;
/*  971 */     double heightScale = hostHeight / pageHeight;
/*      */     
/*  973 */     if (widthScale < heightScale) {
/*  974 */       return new Point((int)hostWidth, (int)(pageHeight * widthScale));
/*      */     }
/*  976 */     return new Point((int)(pageWidth * heightScale), (int)hostHeight);
/*      */   }
/*      */ 
/*      */   
/*      */   private void resizePageProperties(int pageWidth, int pageHeight) {
/*  981 */     int margin = this.mCropImageView.getMarginSize();
/*      */     
/*  983 */     FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)this.mCropImageView.getLayoutParams();
/*  984 */     layoutParams.width = pageWidth + margin + margin;
/*  985 */     layoutParams.height = pageHeight + margin + margin;
/*  986 */     this.mCropImageView.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
/*      */     
/*  988 */     layoutParams = (FrameLayout.LayoutParams)this.mBlankPagePlaceholder.getLayoutParams();
/*  989 */     layoutParams.width = pageWidth;
/*  990 */     layoutParams.height = pageHeight;
/*  991 */     this.mBlankPagePlaceholder.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void setCropBoxAndClose() {
/*  997 */     if (this.mDisablingOverlayShowing) {
/*      */       return;
/*      */     }
/* 1000 */     if (!this.mImageCropMode && this.mCurrentJob != null && !this.mCurrentJob.isDone()) {
/* 1001 */       this.mCurrentJob.cancelRasterizing();
/*      */     }
/* 1003 */     this.mIsActive = true;
/* 1004 */     if (!this.mImageCropMode && this.mPageProperties[this.mCurrentPage] != null && 
/* 1005 */       this.mCropImageView.hasBitmap()) {
/* 1006 */       updatePageCropFromImageView(this.mPageProperties[this.mCurrentPage], this.mCropImageView.getCropRectPercentageMargins());
/*      */     }
/*      */ 
/*      */     
/* 1010 */     if (!this.mImageCropMode) {
/* 1011 */       PDFDoc doc = this.mPdfViewCtrl.getDoc();
/* 1012 */       this.mPdfViewCtrl.cancelRendering();
/* 1013 */       SetCropBoxTask mCropBoxTask = new SetCropBoxTask((Context)getActivity(), doc);
/* 1014 */       mCropBoxTask.execute((Object[])new Void[0]);
/*      */     } else {
/* 1016 */       this.mCroppedBitmap = this.mCropImageView.getCroppedImage();
/* 1017 */       dismiss();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void applyCropToPagesAndFlash(MultiApplyMode applyMode) {
/* 1022 */     if (this.mDisablingOverlayShowing) {
/*      */       return;
/*      */     }
/* 1025 */     if (this.mCropImageView.hasBitmap() && this.mPageProperties[this.mCurrentPage] != null) {
/* 1026 */       RectF percentageRect = this.mCropImageView.getCropRectPercentageMargins();
/*      */ 
/*      */       
/* 1029 */       updatePageCropFromImageView(this.mPageProperties[this.mCurrentPage], percentageRect);
/*      */       
/*      */       try {
/* 1032 */         int rot = Page.subtractRotations(0, (this.mPageProperties[this.mCurrentPage]).mRotation);
/* 1033 */         rotateRectangle(percentageRect, rot);
/*      */         
/* 1035 */         Rect cropBox = (this.mPageProperties[this.mCurrentPage]).mCropBox;
/* 1036 */         if (cropBox != null && cropBox.getWidth() > 0.0D && cropBox.getHeight() > 0.0D) {
/*      */ 
/*      */ 
/*      */           
/* 1040 */           Rect cropMargins = new Rect(percentageRect.left * cropBox.getWidth(), percentageRect.bottom * cropBox.getHeight(), percentageRect.right * cropBox.getWidth(), percentageRect.top * cropBox.getHeight());
/*      */           
/* 1042 */           ApplyCropToPagesTask applyCropTask = new ApplyCropToPagesTask((Context)getActivity(), cropMargins, this.mPdfViewCtrl.getDoc(), applyMode);
/* 1043 */           applyCropTask.execute((Object[])new Void[0]);
/* 1044 */           CommonToast.showText((Context)getActivity(), getCropInfoString(applyMode));
/*      */         } 
/* 1046 */       } catch (PDFNetException e) {
/* 1047 */         AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private class SetCropBoxTask
/*      */     extends CustomAsyncTask<Void, Integer, Boolean>
/*      */   {
/*      */     private PDFDoc mPdfDoc;
/*      */     
/*      */     private long mTaskStartTime;
/*      */     boolean mHasChange;
/*      */     
/*      */     public SetCropBoxTask(@NonNull Context context, PDFDoc doc) {
/* 1062 */       super(context);
/*      */       
/* 1064 */       this.mPdfDoc = doc;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void onPreExecute() {
/* 1069 */       super.onPreExecute();
/*      */       
/* 1071 */       UserCropDialogFragment.this.mDisablingOverlay.setVisibility(0);
/* 1072 */       UserCropDialogFragment.this.mDisablingOverlayShowing = true;
/*      */       
/* 1074 */       this.mTaskStartTime = System.nanoTime() / 1000000L;
/*      */     }
/*      */ 
/*      */     
/*      */     protected Boolean doInBackground(Void... params) {
/* 1079 */       if (UserCropDialogFragment.this.mPdfViewCtrl == null || this.mPdfDoc == null) {
/* 1080 */         return Boolean.valueOf(false);
/*      */       }
/*      */       
/* 1083 */       boolean success = false;
/* 1084 */       boolean shouldUnlock = false;
/*      */       try {
/* 1086 */         this.mPdfDoc.lock();
/* 1087 */         shouldUnlock = true;
/*      */         
/* 1089 */         if (UserCropDialogFragment.this.mRemoveCropHelperMode) {
/* 1090 */           removeCropHelper();
/*      */         } else {
/* 1092 */           cropPagesHelper();
/*      */         } 
/* 1094 */         this.mHasChange = this.mPdfDoc.hasChangesSinceSnapshot();
/* 1095 */         success = true;
/* 1096 */       } catch (PDFNetException e) {
/* 1097 */         AnalyticsHandlerAdapter.getInstance().sendException((Exception)e, "USER_CROP");
/*      */       } finally {
/* 1099 */         if (shouldUnlock) {
/* 1100 */           Utils.unlockQuietly(this.mPdfDoc);
/*      */         }
/*      */       } 
/* 1103 */       return Boolean.valueOf(success);
/*      */     }
/*      */     
/*      */     private void cropPagesHelper() throws PDFNetException {
/* 1107 */       int currPageNum = 1;
/* 1108 */       PageIterator itr = this.mPdfDoc.getPageIterator();
/* 1109 */       while (itr.hasNext()) {
/* 1110 */         Object obj = itr.next();
/* 1111 */         Page page = (Page)obj;
/* 1112 */         PageCropProperties pageProperties = UserCropDialogFragment.this.mPageProperties[currPageNum];
/* 1113 */         if (pageProperties != null && pageProperties.mUserCropBox != null) {
/*      */           try {
/* 1115 */             if (pageProperties.mUserCropBox.getX1() - pageProperties.mCropBox.getX1() <= 0.1D && pageProperties.mCropBox
/* 1116 */               .getX2() - pageProperties.mUserCropBox.getX2() <= 0.1D && pageProperties.mUserCropBox
/* 1117 */               .getY1() - pageProperties.mCropBox.getY1() <= 0.1D && pageProperties.mCropBox
/* 1118 */               .getY2() - pageProperties.mUserCropBox.getY2() <= 0.1D) {
/* 1119 */               UserCropUtilities.removeUserCropFromPage(page);
/*      */             } else {
/* 1121 */               page.setBox(5, pageProperties.mUserCropBox);
/*      */             } 
/* 1123 */           } catch (PDFNetException e) {
/* 1124 */             AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*      */           } 
/*      */         }
/*      */         
/* 1128 */         if (currPageNum % 100 == 99) {
/* 1129 */           publishProgress((Object[])new Integer[] { Integer.valueOf(currPageNum) });
/*      */         }
/*      */         
/* 1132 */         currPageNum++;
/*      */       } 
/*      */     }
/*      */     
/*      */     private void removeCropHelper() throws PDFNetException {
/* 1137 */       int currPageNum = 1;
/* 1138 */       PageIterator itr = this.mPdfDoc.getPageIterator();
/* 1139 */       while (itr.hasNext()) {
/* 1140 */         Object obj = itr.next();
/* 1141 */         Page page = (Page)obj;
/*      */         
/*      */         try {
/* 1144 */           UserCropUtilities.removeUserCropFromPage(page);
/* 1145 */         } catch (PDFNetException e) {
/* 1146 */           AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*      */         } 
/*      */         
/* 1149 */         if (currPageNum % 100 == 99) {
/* 1150 */           publishProgress((Object[])new Integer[] { Integer.valueOf(currPageNum) });
/*      */         }
/*      */         
/* 1153 */         currPageNum++;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     protected void onCancelled(Boolean succeeded) {
/* 1159 */       super.onCancelled(succeeded);
/*      */       
/* 1161 */       UserCropDialogFragment.this.removeSpinner();
/*      */     }
/*      */ 
/*      */     
/*      */     protected void onPostExecute(Boolean succeeded) {
/* 1166 */       super.onPostExecute(succeeded);
/*      */       
/* 1168 */       UserCropDialogFragment.this.removeSpinner();
/*      */       try {
/* 1170 */         UserCropDialogFragment.this.mPdfViewCtrl.updatePageLayout();
/* 1171 */       } catch (Exception e) {
/* 1172 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */       } 
/*      */       
/* 1175 */       if (succeeded.booleanValue()) {
/* 1176 */         if (this.mHasChange) {
/* 1177 */           ToolManager toolManager = (ToolManager)UserCropDialogFragment.this.mPdfViewCtrl.getToolManager();
/* 1178 */           if (toolManager != null) {
/* 1179 */             toolManager.raisePagesCropped();
/*      */           }
/*      */         } 
/* 1182 */         UserCropDialogFragment.this.dismiss();
/*      */       } 
/*      */       
/* 1185 */       UserCropDialogFragment.this.removeSpinner();
/*      */     }
/*      */ 
/*      */     
/*      */     protected void onProgressUpdate(Integer... values) {
/* 1190 */       super.onProgressUpdate((Object[])values);
/*      */       
/* 1192 */       if (getContext() == null) {
/*      */         return;
/*      */       }
/*      */       
/* 1196 */       long now = System.nanoTime() / 1000000L;
/*      */       
/* 1198 */       if (now - this.mTaskStartTime > 500L) {
/* 1199 */         UserCropDialogFragment.this.showSpinner();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private class ApplyCropToPagesTask
/*      */     extends CustomAsyncTask<Void, Integer, Boolean>
/*      */   {
/*      */     private Rect mMarginRect;
/*      */     
/*      */     private PDFDoc mDoc;
/*      */     
/*      */     private long mTaskStartTime;
/*      */     
/*      */     private MultiApplyMode mMode;
/*      */     
/*      */     public ApplyCropToPagesTask(Context context, Rect marginRect, PDFDoc doc, MultiApplyMode mode) {
/* 1217 */       super(context);
/*      */       
/* 1219 */       this.mMarginRect = marginRect;
/* 1220 */       this.mDoc = doc;
/* 1221 */       this.mMode = mode;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void onPreExecute() {
/* 1226 */       super.onPreExecute();
/* 1227 */       UserCropDialogFragment.this.mDisablingOverlay.setVisibility(0);
/* 1228 */       UserCropDialogFragment.this.mDisablingOverlayShowing = true;
/*      */       
/* 1230 */       this.mTaskStartTime = System.nanoTime() / 1000000L;
/*      */     }
/*      */ 
/*      */     
/*      */     protected Boolean doInBackground(Void... params) {
/* 1235 */       return Boolean.valueOf(applyCropToAllPages(this.mMarginRect, this.mDoc));
/*      */     }
/*      */     
/*      */     boolean applyCropToAllPages(Rect marginRect, PDFDoc doc) {
/* 1239 */       boolean success = false;
/*      */ 
/*      */       
/* 1242 */       boolean shouldUnlockRead = false;
/*      */       try {
/* 1244 */         doc.lockRead();
/* 1245 */         shouldUnlockRead = true;
/*      */         
/* 1247 */         PageIterator itr = doc.getPageIterator();
/* 1248 */         int pageNumber = 1;
/* 1249 */         while (itr.hasNext() && !isCancelled()) {
/* 1250 */           Object pageObj = itr.next();
/* 1251 */           Page page = (Page)pageObj;
/*      */           
/* 1253 */           if (this.mMode == MultiApplyMode.Even && pageNumber % 2 != 0) {
/* 1254 */             pageNumber++; continue;
/*      */           } 
/* 1256 */           if (this.mMode == MultiApplyMode.Odd && pageNumber % 2 == 0) {
/* 1257 */             pageNumber++;
/*      */             
/*      */             continue;
/*      */           } 
/* 1261 */           if (UserCropDialogFragment.this.mPageProperties[pageNumber] == null) {
/* 1262 */             PageCropProperties cropProperties = new PageCropProperties();
/* 1263 */             cropProperties.mCropBox = page.getCropBox();
/* 1264 */             cropProperties.mRotation = page.getRotation();
/* 1265 */             cropProperties.mUserCropBox = new Rect();
/* 1266 */             UserCropDialogFragment.this.mPageProperties[pageNumber] = cropProperties;
/*      */           } 
/* 1268 */           Rect userCrop = (UserCropDialogFragment.this.mPageProperties[pageNumber]).mUserCropBox;
/* 1269 */           Rect crop = (UserCropDialogFragment.this.mPageProperties[pageNumber]).mCropBox;
/*      */           
/* 1271 */           if (marginRect.getX1() + marginRect.getX2() + 10.0D > crop.getWidth()) {
/* 1272 */             userCrop.setX1(crop.getX1());
/* 1273 */             userCrop.setX2(crop.getX2());
/*      */           } else {
/* 1275 */             userCrop.setX1(crop.getX1() + marginRect.getX1());
/* 1276 */             userCrop.setX2(crop.getX2() - marginRect.getX2());
/*      */           } 
/*      */           
/* 1279 */           if (marginRect.getY1() + marginRect.getY2() + 10.0D > crop.getHeight()) {
/* 1280 */             userCrop.setY1(crop.getY1());
/* 1281 */             userCrop.setY2(crop.getY2());
/*      */           } else {
/* 1283 */             userCrop.setY1(crop.getY1() + marginRect.getY1());
/* 1284 */             userCrop.setY2(crop.getY2() - marginRect.getY2());
/*      */           } 
/*      */           
/* 1287 */           pageNumber++;
/*      */           
/* 1289 */           if (pageNumber % 100 == 99) {
/* 1290 */             publishProgress((Object[])new Integer[] { Integer.valueOf(pageNumber) });
/*      */           }
/*      */         } 
/* 1293 */         success = true;
/* 1294 */       } catch (PDFNetException e) {
/* 1295 */         AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*      */       } finally {
/* 1297 */         if (shouldUnlockRead) {
/* 1298 */           Utils.unlockReadQuietly(doc);
/*      */         }
/*      */       } 
/* 1301 */       return success;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void onProgressUpdate(Integer... values) {
/* 1306 */       super.onProgressUpdate((Object[])values);
/*      */       
/* 1308 */       if (getContext() == null) {
/*      */         return;
/*      */       }
/*      */       
/* 1312 */       long now = System.nanoTime() / 1000000L;
/*      */       
/* 1314 */       if (now - this.mTaskStartTime > 500L) {
/* 1315 */         UserCropDialogFragment.this.showSpinner();
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     protected void onCancelled(Boolean aBoolean) {
/* 1321 */       super.onCancelled(aBoolean);
/*      */       
/* 1323 */       UserCropDialogFragment.this.removeSpinner();
/*      */     }
/*      */ 
/*      */     
/*      */     protected void onPostExecute(Boolean aBoolean) {
/* 1328 */       super.onPostExecute(aBoolean);
/*      */       
/* 1330 */       UserCropDialogFragment.this.removeSpinner();
/*      */     }
/*      */   }
/*      */   
/*      */   private enum MultiApplyMode {
/* 1335 */     All,
/* 1336 */     Even,
/* 1337 */     Odd;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void showSpinner() {
/* 1343 */     setCancelable(false);
/* 1344 */     if (this.mSpinnerShowing) {
/*      */       return;
/*      */     }
/* 1347 */     this.mSpinnerShowing = true;
/*      */     
/* 1349 */     this.mProgressBarHost.setVisibility(0);
/* 1350 */     this.mSpinnerAlphaAnimation = new AlphaAnimation(0.0F, 1.0F);
/* 1351 */     this.mSpinnerAlphaAnimation.setDuration(500L);
/* 1352 */     this.mSpinnerAlphaAnimation.setInterpolator((Interpolator)new LinearInterpolator());
/* 1353 */     this.mProgressBarHost.startAnimation((Animation)this.mSpinnerAlphaAnimation);
/*      */   }
/*      */   
/*      */   private void removeSpinner() {
/* 1357 */     setCancelable(true);
/* 1358 */     this.mProgressBarHost.setVisibility(8);
/* 1359 */     this.mDisablingOverlay.setVisibility(8);
/* 1360 */     this.mDisablingOverlayShowing = false;
/* 1361 */     if (this.mSpinnerAlphaAnimation != null && !this.mSpinnerAlphaAnimation.hasEnded()) {
/* 1362 */       this.mSpinnerAlphaAnimation.cancel();
/*      */     }
/* 1364 */     if (this.mSpinnerShowing) {
/* 1365 */       this.mSpinnerShowing = false;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void rotateRectangle(RectF rect, int rotation) {
/*      */     float temp;
/* 1374 */     switch (rotation) {
/*      */       case 0:
/*      */         return;
/*      */       case 1:
/* 1378 */         temp = rect.left;
/* 1379 */         rect.left = rect.bottom;
/* 1380 */         rect.bottom = rect.right;
/* 1381 */         rect.right = rect.top;
/* 1382 */         rect.top = temp;
/*      */         return;
/*      */       case 2:
/* 1385 */         temp = rect.left;
/* 1386 */         rect.left = rect.right;
/* 1387 */         rect.right = temp;
/* 1388 */         temp = rect.bottom;
/* 1389 */         rect.bottom = rect.top;
/* 1390 */         rect.top = temp;
/*      */         return;
/*      */       case 3:
/* 1393 */         temp = rect.left;
/* 1394 */         rect.left = rect.top;
/* 1395 */         rect.top = rect.right;
/* 1396 */         rect.right = rect.bottom;
/* 1397 */         rect.bottom = temp;
/*      */         break;
/*      */     } 
/*      */   }
/*      */   private String getCropInfoString(MultiApplyMode mode) {
/* 1402 */     int res = R.string.user_crop_manual_crop_crop_all_toast;
/* 1403 */     if (mode == MultiApplyMode.Even) {
/* 1404 */       res = R.string.user_crop_manual_crop_crop_even_toast;
/* 1405 */     } else if (mode == MultiApplyMode.Odd) {
/* 1406 */       res = R.string.user_crop_manual_crop_crop_odd_toast;
/*      */     } 
/* 1408 */     return String.format(getActivity().getResources()
/* 1409 */         .getString(res), new Object[] {
/* 1410 */           getString(R.string.user_crop_manual_crop_done)
/*      */         });
/*      */   }
/*      */   
/*      */   public static interface OnUserCropDialogDismissListener {
/*      */     void onUserCropDialogDismiss(int param1Int);
/*      */   }
/*      */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\controls\UserCropDialogFragment.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */