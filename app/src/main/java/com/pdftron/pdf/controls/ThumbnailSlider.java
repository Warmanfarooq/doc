/*     */ package com.pdftron.pdf.controls;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.content.res.TypedArray;
/*     */ import android.graphics.Bitmap;
/*     */ import android.graphics.PorterDuff;
/*     */ import android.graphics.RectF;
/*     */ import android.graphics.drawable.BitmapDrawable;
/*     */ import android.graphics.drawable.Drawable;
/*     */ import android.os.Handler;
/*     */ import android.os.Looper;
/*     */ import android.util.AttributeSet;
/*     */ import android.view.Display;
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.view.WindowManager;
/*     */ import android.view.animation.Animation;
/*     */ import android.view.animation.AnimationUtils;
/*     */ import android.widget.ImageButton;
/*     */ import android.widget.ImageView;
/*     */ import android.widget.LinearLayout;
/*     */ import android.widget.PopupWindow;
/*     */ import android.widget.SeekBar;
/*     */ import android.widget.TextView;
/*     */ import androidx.annotation.DrawableRes;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.annotation.RequiresApi;
/*     */ import androidx.appcompat.widget.TooltipCompat;
/*     */ import androidx.constraintlayout.widget.ConstraintLayout;
/*     */ import androidx.constraintlayout.widget.ConstraintSet;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.Page;
/*     */ import com.pdftron.pdf.Rect;
/*     */ import com.pdftron.pdf.dialog.pagelabel.PageLabelUtils;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.tools.ToolManager;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.AnalyticsParam;
/*     */ import com.pdftron.pdf.utils.ImageMemoryCache;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import java.lang.annotation.Retention;
/*     */ import java.lang.annotation.RetentionPolicy;
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
/*     */ public class ThumbnailSlider
/*     */   extends LinearLayout
/*     */   implements PDFViewCtrl.ThumbAsyncListener, PDFViewCtrl.DocumentLoadListener, PDFViewCtrl.PageChangeListener, View.OnClickListener
/*     */ {
/*     */   private static final float SCREEN_RATIO_THUMB_SIZE = 3.6F;
/*     */   public static final int POSITION_LEFT = 0;
/*     */   public static final int POSITION_RIGHT = 1;
/*     */   private MirrorSeekBar mSeekBar;
/*     */   private ConstraintLayout mRootView;
/*     */   private ImageView mThumbnailViewImage;
/*     */   private TextView mPageIndicator;
/*     */   private PopupWindow mThumbnailViewPopup;
/*     */   private PDFViewCtrl mPdfViewCtrl;
/*     */   private View mShadowView;
/*     */   private int mPageCount;
/*     */   private int mSeekBarMax;
/*     */   private int mCurrentPage;
/*     */   private int mPDFViewCtrlId;
/*     */   private boolean mIsProgressChanging;
/*     */   private int mThumbViewWidth;
/*     */   private int mThumbViewHeight;
/*     */   private int mScreenWidth;
/*     */   private int mScreenHeight;
/*     */   private boolean mViewerReady;
/*     */   private int mCurrentThumbnailPageNumber;
/*     */   private int mBlankDayResId;
/*     */   private int mBlankNightResId;
/*     */   private ThumbnailState mThumbnailState;
/*     */   private OnThumbnailSliderTrackingListener mListener;
/*     */   
/*     */   private enum ThumbnailState
/*     */   {
/*  98 */     None,
/*  99 */     Lingering,
/* 100 */     Correct;
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
/* 140 */   private static int SEEKBAR_GRANULARITY = 100;
/*     */   
/*     */   private ImageButton mLeftItemImageBtn;
/*     */   
/*     */   private ImageButton mRightItemImageBtn;
/*     */   
/*     */   private OnMenuItemClickedListener mMenuItemClickedListener;
/*     */   
/* 148 */   private Handler mRemoveOldThumbHandler = new Handler(Looper.getMainLooper());
/* 149 */   private Runnable mRemoveOldThumbRunnable = new Runnable()
/*     */     {
/*     */       public void run() {
/* 152 */         ThumbnailSlider.this.removeOldThumbTick();
/* 153 */         ThumbnailSlider.this.mRemoveOldThumbHandler.postDelayed(this, 50L);
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ThumbnailSlider(Context context) {
/* 161 */     this(context, (AttributeSet)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ThumbnailSlider(Context context, AttributeSet attrs) {
/* 168 */     this(context, attrs, R.attr.thumbnail_slider);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ThumbnailSlider(Context context, AttributeSet attrs, int defStyleAttr) {
/* 175 */     super(context, attrs, defStyleAttr);
/* 176 */     init(context, attrs, defStyleAttr, R.style.ThumbnailSliderStyle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RequiresApi(api = 21)
/*     */   public ThumbnailSlider(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
/* 184 */     super(context, attrs, defStyleAttr, defStyleRes);
/* 185 */     init(context, attrs, defStyleAttr, defStyleRes);
/*     */   }
/*     */   
/*     */   private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
/* 189 */     initScreenSize(context);
/*     */     
/* 191 */     this.mViewerReady = false;
/* 192 */     this.mCurrentThumbnailPageNumber = -1;
/* 193 */     this.mPageCount = 1;
/* 194 */     this.mIsProgressChanging = false;
/* 195 */     this.mListener = null;
/*     */     
/* 197 */     setOrientation(1);
/*     */     
/* 199 */     inflateLayout(context);
/* 200 */     View barLayout = findViewById(R.id.controls_thumbnail_slider_scrubberview);
/* 201 */     this.mShadowView = findViewById(R.id.controls_thumbnail_shadow_view);
/* 202 */     this.mSeekBar = (MirrorSeekBar)findViewById(R.id.controls_thumbnail_slider_scrubberview_seekbar);
/* 203 */     this.mLeftItemImageBtn = (ImageButton)findViewById(R.id.controls_thumbnail_slider_left_menu_button);
/* 204 */     this.mRightItemImageBtn = (ImageButton)findViewById(R.id.controls_thumbnail_slider_right_menu_button);
/* 205 */     this.mRootView = (ConstraintLayout)LayoutInflater.from(context).inflate(R.layout.controls_thumbnail_slider_preview, null);
/* 206 */     this.mThumbnailViewImage = (ImageView)this.mRootView.findViewById(R.id.controls_thumbnail_slider_thumbview_thumb);
/* 207 */     this.mPageIndicator = (TextView)this.mRootView.findViewById(R.id.controls_thumbnail_slider_thumbview_pagenumber);
/*     */     
/* 209 */     this.mThumbnailViewPopup = new PopupWindow((View)this.mRootView, (int)(this.mThumbViewHeight / 3.6F), (int)(this.mThumbViewHeight / 3.6F));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 214 */     this.mThumbnailViewPopup.setAnimationStyle(16973826);
/*     */     
/* 216 */     this.mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
/* 217 */           int currentPage = 1;
/*     */ 
/*     */           
/*     */           public void onStartTrackingTouch(SeekBar seekBar) {
/* 221 */             if (ThumbnailSlider.this.mPdfViewCtrl != null) {
/*     */               
/* 223 */               int[] sliderPosition = new int[2];
/* 224 */               ThumbnailSlider.this.getLocationInWindow(sliderPosition);
/*     */               
/* 226 */               int posX = sliderPosition[0] + ThumbnailSlider.this.getWidth() / 2 - ThumbnailSlider.this.mThumbnailViewPopup.getWidth() / 2;
/* 227 */               int posY = sliderPosition[1] - (int)Utils.convDp2Pix(ThumbnailSlider.this.getContext(), 2.0F) - ThumbnailSlider.this.mThumbnailViewPopup.getHeight();
/*     */               
/* 229 */               ThumbnailSlider.this.mThumbnailViewPopup.showAtLocation((View)ThumbnailSlider.this.mPdfViewCtrl, 51, posX, posY);
/*     */             } 
/*     */ 
/*     */             
/* 233 */             ThumbnailSlider.this.mIsProgressChanging = true;
/* 234 */             if (ThumbnailSlider.this.mListener != null) {
/* 235 */               ThumbnailSlider.this.mListener.onThumbSliderStartTrackingTouch();
/*     */             }
/*     */           }
/*     */ 
/*     */           
/*     */           public void onStopTrackingTouch(SeekBar seekBar) {
/* 241 */             ThumbnailSlider.this.mThumbnailViewPopup.dismiss();
/* 242 */             ThumbnailSlider.this.mIsProgressChanging = false;
/* 243 */             if (ThumbnailSlider.this.mPdfViewCtrl != null) {
/* 244 */               ThumbnailSlider.this.mPdfViewCtrl.setCurrentPage(ThumbnailSlider.this.mCurrentPage);
/*     */               try {
/* 246 */                 ThumbnailSlider.this.mPdfViewCtrl.cancelAllThumbRequests();
/* 247 */               } catch (Exception e) {
/* 248 */                 AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */               } 
/*     */             } 
/* 251 */             if (ThumbnailSlider.this.mListener != null) {
/* 252 */               ThumbnailSlider.this.mListener.onThumbSliderStopTrackingTouch(ThumbnailSlider.this.mCurrentPage);
/*     */             }
/*     */           }
/*     */ 
/*     */           
/*     */           public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
/* 258 */             if (ThumbnailSlider.this.mSeekBarMax > ThumbnailSlider.SEEKBAR_GRANULARITY) {
/* 259 */               this.currentPage = progress + 1;
/*     */             } else {
/* 261 */               this.currentPage = progress * ThumbnailSlider.this.mPageCount / ThumbnailSlider.SEEKBAR_GRANULARITY + 1;
/*     */             } 
/* 263 */             if (ThumbnailSlider.this.mPdfViewCtrl != null) {
/* 264 */               String currentPageLabel = PageLabelUtils.getPageLabelTitle(ThumbnailSlider.this.mPdfViewCtrl, this.currentPage);
/* 265 */               if (!Utils.isNullOrEmpty(currentPageLabel)) {
/* 266 */                 ThumbnailSlider.this.mPageIndicator.setText(currentPageLabel);
/*     */               } else {
/* 268 */                 ThumbnailSlider.this.mPageIndicator.setText(Utils.getLocaleDigits(Integer.toString(this.currentPage)));
/*     */               } 
/*     */             } 
/* 271 */             ThumbnailSlider.this.mCurrentPage = this.currentPage;
/*     */             
/* 273 */             if (ThumbnailSlider.this.mViewerReady) {
/* 274 */               ThumbnailSlider.this.requestThumb();
/*     */             }
/*     */           }
/*     */         });
/*     */     
/* 279 */     this.mBlankDayResId = R.drawable.white_square;
/* 280 */     this.mBlankNightResId = R.drawable.black_square;
/*     */     
/* 282 */     this.mLeftItemImageBtn.setOnClickListener(this);
/* 283 */     this.mRightItemImageBtn.setOnClickListener(this);
/*     */     
/* 285 */     TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ThumbnailSlider, defStyleAttr, defStyleRes);
/*     */     try {
/* 287 */       this.mPDFViewCtrlId = typedArray.getResourceId(R.styleable.ThumbnailSlider_pdfviewctrlId, -1);
/* 288 */       int leftIconId = typedArray.getResourceId(R.styleable.ThumbnailSlider_leftMenuItemDrawable, -1);
/* 289 */       if (leftIconId != -1) {
/* 290 */         setMenuItem(leftIconId, 0);
/*     */       }
/*     */       
/* 293 */       int rightIconId = typedArray.getResourceId(R.styleable.ThumbnailSlider_rightMenuItemDrawable, -1);
/* 294 */       if (rightIconId != -1) {
/* 295 */         setMenuItem(rightIconId, 1);
/*     */       }
/*     */       
/* 298 */       String contentDescriptionL = typedArray.getString(R.styleable.ThumbnailSlider_leftMenuItemContentDescription);
/* 299 */       if (!Utils.isNullOrEmpty(contentDescriptionL)) {
/* 300 */         setMenuItemContentDescription(0, contentDescriptionL);
/*     */       }
/*     */       
/* 303 */       String contentDescriptionR = typedArray.getString(R.styleable.ThumbnailSlider_rightMenuItemContentDescription);
/* 304 */       if (!Utils.isNullOrEmpty(contentDescriptionR)) {
/* 305 */         setMenuItemContentDescription(1, contentDescriptionR);
/*     */       }
/*     */ 
/*     */       
/* 309 */       int primaryColor = Utils.getPrimaryColor(getContext());
/* 310 */       int seekbarColor = typedArray.getColor(R.styleable.ThumbnailSlider_seekbarColor, primaryColor);
/* 311 */       Drawable progressDrawable = this.mSeekBar.getProgressDrawable();
/* 312 */       progressDrawable.setColorFilter(seekbarColor, PorterDuff.Mode.SRC_IN);
/* 313 */       Drawable thumbDrawable = this.mSeekBar.getThumb();
/* 314 */       thumbDrawable.setColorFilter(seekbarColor, PorterDuff.Mode.SRC_IN);
/*     */ 
/*     */       
/* 317 */       int leftMenuItemColor = typedArray.getColor(R.styleable.ThumbnailSlider_leftMenuItemColor, primaryColor);
/* 318 */       int rightMenuItemColor = typedArray.getColor(R.styleable.ThumbnailSlider_rightMenuItemColor, primaryColor);
/* 319 */       this.mLeftItemImageBtn.setColorFilter(leftMenuItemColor, PorterDuff.Mode.SRC_IN);
/* 320 */       this.mRightItemImageBtn.setColorFilter(rightMenuItemColor, PorterDuff.Mode.SRC_IN);
/*     */ 
/*     */       
/* 323 */       int backgroundColor = typedArray.getColor(R.styleable.ThumbnailSlider_colorBackground, 0);
/* 324 */       if (backgroundColor != 0) {
/* 325 */         barLayout.setBackgroundColor(backgroundColor);
/*     */       }
/*     */ 
/*     */       
/* 329 */       boolean showShadow = typedArray.getBoolean(R.styleable.ThumbnailSlider_shadowEnabled, true);
/* 330 */       this.mShadowView.setVisibility(showShadow ? 0 : 8);
/*     */     } finally {
/* 332 */       typedArray.recycle();
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void inflateLayout(@NonNull Context context) {
/* 337 */     LayoutInflater.from(context).inflate(R.layout.controls_thumbnail_slider, (ViewGroup)this);
/*     */   }
/*     */   
/*     */   private void initScreenSize(Context context) {
/* 341 */     WindowManager wm = (WindowManager)context.getSystemService("window");
/* 342 */     if (wm == null) {
/*     */       return;
/*     */     }
/* 345 */     Display display = wm.getDefaultDisplay();
/* 346 */     this.mScreenWidth = display.getWidth();
/* 347 */     this.mScreenHeight = display.getHeight();
/* 348 */     this.mThumbViewWidth = Math.min(this.mScreenWidth, this.mScreenHeight);
/* 349 */     this.mThumbViewHeight = Math.max(this.mScreenWidth, this.mScreenHeight);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onThumbReceived(int page, int[] buf, int width, int height) {
/* 357 */     boolean addThumb = true;
/*     */     
/* 359 */     if (this.mThumbnailState == ThumbnailState.Correct) {
/* 360 */       addThumb = false;
/*     */     }
/*     */     
/* 363 */     if (addThumb) {
/* 364 */       this.mCurrentThumbnailPageNumber = page;
/*     */       
/* 366 */       if (page != this.mCurrentPage) {
/* 367 */         this.mRemoveOldThumbHandler.postDelayed(this.mRemoveOldThumbRunnable, 50L);
/* 368 */         this.mThumbnailState = ThumbnailState.Lingering;
/*     */       
/*     */       }
/* 371 */       else if (width > this.mScreenWidth || height > this.mScreenHeight) {
/* 372 */         AnalyticsHandlerAdapter.getInstance().sendEvent(46, AnalyticsParam.hugeThumbParam(width, height, buf.length, 4));
/*     */       } else {
/*     */         
/*     */         try {
/* 376 */           ImageMemoryCache imageMemoryCache = ImageMemoryCache.getInstance();
/* 377 */           Bitmap bitmap = imageMemoryCache.getBitmapFromReusableSet(width, height, Bitmap.Config.ARGB_8888);
/* 378 */           if (bitmap == null) {
/* 379 */             bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
/*     */           }
/* 381 */           bitmap.setPixels(buf, 0, width, 0, 0, width, height);
/* 382 */           RectF pageDim = getThumbnailSize(this.mCurrentPage);
/* 383 */           setThumbnailViewImageBitmap(bitmap, 0, (int)pageDim.width(), (int)pageDim.height());
/* 384 */           ImageMemoryCache.getInstance().addBitmapToReusableSet(bitmap);
/* 385 */           this.mRemoveOldThumbHandler.removeCallbacks(this.mRemoveOldThumbRunnable);
/* 386 */           this.mThumbnailState = ThumbnailState.Correct;
/* 387 */         } catch (Exception e) {
/* 388 */           AnalyticsHandlerAdapter.getInstance().sendException(e);
/* 389 */         } catch (OutOfMemoryError oom) {
/* 390 */           Utils.manageOOM(getContext(), this.mPdfViewCtrl);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void removeOldThumbTick() {
/* 398 */     this.mRemoveOldThumbHandler.removeCallbacks(this.mRemoveOldThumbRunnable);
/*     */     
/* 400 */     if (this.mThumbnailState == ThumbnailState.Lingering) {
/* 401 */       boolean nightMode; int resId; this.mCurrentThumbnailPageNumber = -1;
/* 402 */       this.mThumbnailState = ThumbnailState.None;
/* 403 */       RectF pageDim = getThumbnailSize(this.mCurrentPage);
/*     */ 
/*     */       
/*     */       try {
/* 407 */         nightMode = ((ToolManager)this.mPdfViewCtrl.getToolManager()).isNightMode();
/* 408 */       } catch (Exception e) {
/* 409 */         nightMode = false;
/*     */       } 
/*     */       
/* 412 */       if (nightMode) {
/* 413 */         resId = this.mBlankNightResId;
/*     */       } else {
/* 415 */         resId = this.mBlankDayResId;
/*     */       } 
/* 417 */       setThumbnailViewImageBitmap((Bitmap)null, resId, (int)pageDim.width(), (int)pageDim.height());
/*     */     } 
/*     */   }
/*     */   
/*     */   private void requestThumb() {
/* 422 */     boolean requestThumbnail = true;
/* 423 */     boolean blankPage = false;
/*     */     
/* 425 */     if (this.mThumbnailState == ThumbnailState.None) {
/* 426 */       requestThumbnail = true;
/* 427 */       blankPage = true;
/* 428 */     } else if (this.mCurrentPage == this.mCurrentThumbnailPageNumber) {
/* 429 */       requestThumbnail = false;
/* 430 */       this.mThumbnailState = ThumbnailState.Correct;
/* 431 */       this.mRemoveOldThumbHandler.removeCallbacks(this.mRemoveOldThumbRunnable);
/*     */     } else {
/* 433 */       this.mThumbnailState = ThumbnailState.Lingering;
/* 434 */       this.mRemoveOldThumbHandler.removeCallbacks(this.mRemoveOldThumbRunnable);
/* 435 */       this.mRemoveOldThumbHandler.postDelayed(this.mRemoveOldThumbRunnable, 50L);
/*     */     } 
/*     */     
/* 438 */     if (requestThumbnail) {
/*     */       try {
/* 440 */         this.mPdfViewCtrl.getThumbAsync(this.mCurrentPage);
/* 441 */       } catch (Exception e) {
/* 442 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */       } 
/*     */     }
/*     */     
/* 446 */     if (blankPage) {
/* 447 */       boolean nightMode; int resId; RectF pageDim = getThumbnailSize(this.mCurrentPage);
/*     */ 
/*     */       
/*     */       try {
/* 451 */         nightMode = ((ToolManager)this.mPdfViewCtrl.getToolManager()).isNightMode();
/* 452 */       } catch (Exception e) {
/* 453 */         nightMode = false;
/*     */       } 
/*     */       
/* 456 */       if (nightMode) {
/* 457 */         resId = this.mBlankNightResId;
/*     */       } else {
/* 459 */         resId = this.mBlankDayResId;
/*     */       } 
/* 461 */       setThumbnailViewImageBitmap((Bitmap)null, resId, (int)pageDim.width(), (int)pageDim.height());
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
/*     */   private void setThumbnailViewImageBitmap(Bitmap bitmap, int resId, int width, int height) {
/*     */     try {
/* 478 */       if (this.mThumbnailViewImage != null) {
/* 479 */         Bitmap scaledBitmap; BitmapDrawable bitmapDrawable = (BitmapDrawable)this.mThumbnailViewImage.getDrawable();
/* 480 */         if (bitmapDrawable != null) {
/* 481 */           Bitmap bmp = bitmapDrawable.getBitmap();
/* 482 */           ImageMemoryCache.getInstance().addBitmapToReusableSet(bmp);
/*     */         } 
/*     */         
/* 485 */         if (bitmap == null) {
/* 486 */           scaledBitmap = ImageMemoryCache.getInstance().decodeSampledBitmapFromResource(getResources(), resId, width, height);
/*     */         } else {
/* 488 */           scaledBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 493 */         ConstraintSet constraintSet = new ConstraintSet();
/* 494 */         constraintSet.clone(this.mRootView);
/*     */         
/* 496 */         if (width > height) {
/* 497 */           constraintSet.constrainWidth(R.id.controls_thumbnail_slider_thumbview_thumb_container, 0);
/* 498 */           constraintSet.constrainHeight(R.id.controls_thumbnail_slider_thumbview_thumb_container, -2);
/* 499 */           constraintSet.clear(R.id.controls_thumbnail_slider_thumbview_thumb_container, 3);
/*     */         } else {
/* 501 */           constraintSet.constrainWidth(R.id.controls_thumbnail_slider_thumbview_thumb_container, -2);
/* 502 */           constraintSet.constrainHeight(R.id.controls_thumbnail_slider_thumbview_thumb_container, 0);
/* 503 */           constraintSet.connect(R.id.controls_thumbnail_slider_thumbview_thumb_container, 3, 0, 3);
/*     */         } 
/* 505 */         constraintSet.applyTo(this.mRootView);
/*     */         
/* 507 */         this.mThumbnailViewImage.setImageBitmap(scaledBitmap);
/*     */       } 
/* 509 */     } catch (Exception e) {
/* 510 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/* 511 */       if (this.mThumbnailViewImage != null) {
/* 512 */         this.mThumbnailViewImage.setImageDrawable(null);
/*     */       }
/* 514 */     } catch (OutOfMemoryError oom) {
/* 515 */       if (this.mThumbnailViewImage != null) {
/* 516 */         this.mThumbnailViewImage.setImageDrawable(null);
/*     */       }
/* 518 */       Utils.manageOOM(getContext(), this.mPdfViewCtrl);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearResources() {
/* 528 */     this.mRemoveOldThumbHandler.removeCallbacksAndMessages(null);
/* 529 */     if (this.mThumbnailViewImage != null) {
/* 530 */       BitmapDrawable bitmapDrawable = (BitmapDrawable)this.mThumbnailViewImage.getDrawable();
/* 531 */       if (bitmapDrawable != null) {
/* 532 */         Bitmap bmp = bitmapDrawable.getBitmap();
/* 533 */         ImageMemoryCache.getInstance().addBitmapToReusableSet(bmp);
/*     */       } 
/* 535 */       this.mThumbnailViewImage.setImageDrawable(null);
/*     */     } 
/*     */     
/* 538 */     if (this.mPdfViewCtrl != null) {
/* 539 */       this.mPdfViewCtrl.removeDocumentLoadListener(this);
/* 540 */       this.mPdfViewCtrl.removeThumbAsyncListener(this);
/* 541 */       this.mPdfViewCtrl.removePageChangeListener(this);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setProgress(int currentPage) {
/*     */     int progress;
/* 552 */     if (currentPage <= 1) {
/* 553 */       progress = 0;
/*     */     }
/* 555 */     else if (this.mSeekBarMax > SEEKBAR_GRANULARITY) {
/* 556 */       progress = (currentPage == this.mPageCount) ? this.mPageCount : (currentPage - 1);
/*     */     } else {
/* 558 */       progress = (currentPage == this.mPageCount) ? SEEKBAR_GRANULARITY : ((currentPage - 1) * SEEKBAR_GRANULARITY / this.mPageCount);
/*     */     } 
/*     */     
/* 561 */     this.mSeekBar.setProgress(progress);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPdfViewCtrl(PDFViewCtrl pdfViewCtrl) {
/* 570 */     if (pdfViewCtrl == null) {
/* 571 */       throw new NullPointerException("pdfViewCtrl can't be null");
/*     */     }
/* 573 */     this.mPdfViewCtrl = pdfViewCtrl;
/* 574 */     this.mPdfViewCtrl.addDocumentLoadListener(this);
/* 575 */     this.mPdfViewCtrl.addThumbAsyncListener(this);
/* 576 */     this.mPdfViewCtrl.addPageChangeListener(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setThumbSliderListener(OnThumbnailSliderTrackingListener listener) {
/* 585 */     this.mListener = listener;
/*     */   }
/*     */ 
/*     */   
/*     */   private RectF getThumbnailSize(int pageNum) {
/* 590 */     double thumbWidth = (this.mThumbViewWidth / 3.6F);
/* 591 */     double thumbHeight = (this.mThumbViewHeight / 3.6F);
/* 592 */     if (this.mThumbViewWidth == 0 || this.mThumbViewHeight == 0) {
/* 593 */       AnalyticsHandlerAdapter.getInstance().sendException(new Exception("mThumbViewWidth/mThumbViewHeight are zero!"));
/*     */     }
/*     */ 
/*     */     
/* 597 */     if (this.mPdfViewCtrl != null && this.mPdfViewCtrl.getDoc() != null) {
/*     */       try {
/* 599 */         Page page = this.mPdfViewCtrl.getDoc().getPage(pageNum);
/* 600 */         if (page != null) {
/* 601 */           Rect pageCropBox = page.getCropBox();
/* 602 */           double pageWidth = pageCropBox.getWidth();
/* 603 */           double pageHeight = pageCropBox.getHeight();
/* 604 */           int pageRotation = page.getRotation();
/* 605 */           if (pageRotation == 1 || pageRotation == 3) {
/* 606 */             double tmp = pageWidth;
/*     */             
/* 608 */             pageWidth = pageHeight;
/* 609 */             pageHeight = tmp;
/*     */           } 
/* 611 */           double scale = Math.min(thumbWidth / pageWidth, thumbHeight / pageHeight);
/* 612 */           thumbWidth = scale * pageWidth;
/* 613 */           thumbHeight = scale * pageHeight;
/* 614 */           if ((int)thumbWidth == 0 || (int)thumbHeight == 0) {
/* 615 */             AnalyticsHandlerAdapter.getInstance().sendException(new Exception("thumb width/height are zero! page width/height (" + pageWidth + "," + pageHeight + ")"));
/*     */           }
/*     */         }
/*     */       
/* 619 */       } catch (Exception e) {
/* 620 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */       } 
/*     */     }
/* 623 */     return new RectF(0.0F, 0.0F, (float)thumbWidth, (float)thumbHeight);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isProgressChanging() {
/* 630 */     return this.mIsProgressChanging;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
/* 638 */     super.onVisibilityChanged(changedView, visibility);
/*     */ 
/*     */     
/* 641 */     if (this.mPdfViewCtrl != null && this.mPdfViewCtrl.isValid() && 
/* 642 */       this.mPageCount > 0 && 
/* 643 */       visibility == 0) {
/* 644 */       setProgress(this.mPdfViewCtrl.getCurrentPage());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDocumentLoaded() {
/* 655 */     handleDocumentLoaded();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleDocumentLoaded() {
/* 662 */     this.mViewerReady = true;
/* 663 */     if (this.mPdfViewCtrl != null) {
/* 664 */       this.mThumbnailState = ThumbnailState.None;
/* 665 */       refreshPageCount();
/*     */       
/* 667 */       setProgress(this.mPdfViewCtrl.getCurrentPage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void refreshPageCount() {
/* 675 */     if (this.mPdfViewCtrl != null) {
/* 676 */       this.mPageCount = 0;
/* 677 */       boolean shouldUnlockRead = false;
/*     */       try {
/* 679 */         this.mPdfViewCtrl.docLockRead();
/* 680 */         shouldUnlockRead = true;
/* 681 */         this.mPageCount = this.mPdfViewCtrl.getDoc().getPageCount();
/* 682 */       } catch (Exception e) {
/* 683 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */       } finally {
/* 685 */         if (shouldUnlockRead) {
/* 686 */           this.mPdfViewCtrl.docUnlockRead();
/*     */         }
/*     */       } 
/*     */       
/* 690 */       if (this.mPageCount <= 0) {
/* 691 */         this.mPageCount = 1;
/*     */       }
/* 693 */       this.mSeekBarMax = (this.mPageCount > SEEKBAR_GRANULARITY) ? this.mPageCount : SEEKBAR_GRANULARITY;
/* 694 */       this.mSeekBar.setMax(this.mSeekBarMax - 1);
/*     */       
/* 696 */       if (this.mPageCount == 1) {
/* 697 */         this.mSeekBar.setVisibility(4);
/*     */       } else {
/* 699 */         this.mSeekBar.setVisibility(0);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isReversed() {
/* 708 */     return this.mSeekBar.isReversed();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setReversed(boolean isReversed) {
/* 717 */     this.mSeekBar.setReversed(isReversed);
/* 718 */     this.mSeekBar.invalidate();
/*     */   }
/*     */   
/*     */   public void show() {
/* 722 */     show(true);
/*     */   }
/*     */   
/*     */   public void show(boolean isAnimate) {
/* 726 */     if (isAnimate) {
/* 727 */       Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.thumbslider_slide_in_bottom);
/* 728 */       anim.setAnimationListener(new Animation.AnimationListener()
/*     */           {
/*     */             public void onAnimationStart(Animation animation) {
/* 731 */               ThumbnailSlider.this.setVisibility(0);
/*     */             }
/*     */ 
/*     */ 
/*     */             
/*     */             public void onAnimationEnd(Animation animation) {}
/*     */ 
/*     */ 
/*     */             
/*     */             public void onAnimationRepeat(Animation animation) {}
/*     */           });
/* 742 */       setVisibility(4);
/* 743 */       startAnimation(anim);
/*     */     } else {
/* 745 */       setVisibility(0);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void dismiss() {
/* 750 */     dismiss(true);
/*     */   }
/*     */   
/*     */   public void dismiss(boolean isAnimate) {
/* 754 */     if (isAnimate) {
/* 755 */       Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.thumbslider_slide_out_bottom);
/* 756 */       anim.setAnimationListener(new Animation.AnimationListener()
/*     */           {
/*     */             public void onAnimationStart(Animation animation) {}
/*     */ 
/*     */ 
/*     */             
/*     */             public void onAnimationEnd(Animation animation) {
/* 763 */               ThumbnailSlider.this.setVisibility(8);
/*     */             }
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             public void onAnimationRepeat(Animation animation) {}
/*     */           });
/* 771 */       startAnimation(anim);
/*     */     } else {
/* 773 */       setVisibility(8);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onAttachedToWindow() {
/* 783 */     super.onAttachedToWindow();
/* 784 */     if (this.mPdfViewCtrl == null && this.mPDFViewCtrlId != -1) {
/* 785 */       View pdfView = getRootView().findViewById(this.mPDFViewCtrlId);
/* 786 */       if (pdfView != null && pdfView instanceof PDFViewCtrl) {
/* 787 */         setPdfViewCtrl((PDFViewCtrl)pdfView);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onDetachedFromWindow() {
/* 794 */     super.onDetachedFromWindow();
/* 795 */     clearResources();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPageChange(int old_page, int cur_page, PDFViewCtrl.PageChangeState state) {
/* 800 */     refreshPageCount();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onClick(View v) {
/* 806 */     if (this.mMenuItemClickedListener == null) {
/*     */       return;
/*     */     }
/* 809 */     if (v.getId() == this.mLeftItemImageBtn.getId()) {
/* 810 */       this.mMenuItemClickedListener.onMenuItemClicked(0);
/* 811 */     } else if (v.getId() == this.mRightItemImageBtn.getId()) {
/* 812 */       this.mMenuItemClickedListener.onMenuItemClicked(1);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setOnMenuItemClickedListener(OnMenuItemClickedListener listener) {
/* 817 */     this.mMenuItemClickedListener = listener;
/*     */   }
/*     */   
/*     */   public void setMenuItem(@DrawableRes int drawableRes, int position) {
/* 821 */     Drawable icon = Utils.getDrawable(getContext(), drawableRes);
/* 822 */     if (icon != null) {
/* 823 */       setMenuItem(icon, position);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setMenuItem(@NonNull Drawable icon, int position) {
/* 828 */     if (position == 0) {
/* 829 */       this.mLeftItemImageBtn.setImageDrawable(icon);
/* 830 */       this.mLeftItemImageBtn.setVisibility(0);
/*     */     } else {
/* 832 */       this.mRightItemImageBtn.setImageDrawable(icon);
/* 833 */       this.mRightItemImageBtn.setVisibility(0);
/*     */     } 
/*     */   }
/*     */   public void setMenuItemContentDescription(int position, String content) {
/*     */     ImageButton imageButton;
/* 838 */     View view = null;
/* 839 */     switch (position) {
/*     */       case 0:
/* 841 */         imageButton = this.mLeftItemImageBtn;
/*     */         break;
/*     */       case 1:
/* 844 */         imageButton = this.mRightItemImageBtn; break;
/*     */     } 
/* 846 */     if (null != imageButton) {
/* 847 */       TooltipCompat.setTooltipText((View)imageButton, content);
/* 848 */       imageButton.setContentDescription(content);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setMenuItemVisibility(int position, int visiblity) {
/*     */     ImageButton menuItem;
/* 854 */     switch (position) {
/*     */       case 0:
/* 856 */         menuItem = this.mLeftItemImageBtn;
/*     */         break;
/*     */       case 1:
/* 859 */         menuItem = this.mRightItemImageBtn;
/*     */         break;
/*     */       default:
/* 862 */         menuItem = null;
/*     */         break;
/*     */     } 
/* 865 */     if (menuItem != null)
/* 866 */       menuItem.setVisibility(visiblity); 
/*     */   }
/*     */   
/*     */   @Retention(RetentionPolicy.SOURCE)
/*     */   public static @interface MenuItemPosition {}
/*     */   
/*     */   public static interface OnMenuItemClickedListener {
/*     */     void onMenuItemClicked(int param1Int);
/*     */   }
/*     */   
/*     */   public static interface OnThumbnailSliderTrackingListener {
/*     */     void onThumbSliderStartTrackingTouch();
/*     */     
/*     */     void onThumbSliderStopTrackingTouch(int param1Int);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\controls\ThumbnailSlider.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */