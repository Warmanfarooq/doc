/*      */ package com.pdftron.pdf.tools;
/*      */ 
/*      */ import android.annotation.SuppressLint;
/*      */ import android.content.Context;
/*      */ import android.graphics.RectF;
/*      */ import android.graphics.drawable.Drawable;
/*      */ import android.graphics.drawable.GradientDrawable;
/*      */ import android.view.LayoutInflater;
/*      */ import android.view.Menu;
/*      */ import android.view.MenuInflater;
/*      */ import android.view.MenuItem;
/*      */ import android.view.MotionEvent;
/*      */ import android.view.PointerIcon;
/*      */ import android.view.View;
/*      */ import android.view.ViewGroup;
/*      */ import android.view.animation.Animation;
/*      */ import android.view.animation.AnimationUtils;
/*      */ import android.view.animation.DecelerateInterpolator;
/*      */ import android.view.animation.Interpolator;
/*      */ import android.view.animation.ScaleAnimation;
/*      */ import android.widget.Button;
/*      */ import android.widget.LinearLayout;
/*      */ import android.widget.RelativeLayout;
/*      */ import android.widget.ScrollView;
/*      */ import androidx.annotation.IdRes;
/*      */ import androidx.annotation.MenuRes;
/*      */ import androidx.annotation.NonNull;
/*      */ import androidx.annotation.Nullable;
/*      */ import androidx.appcompat.widget.AppCompatImageButton;
/*      */ import com.pdftron.pdf.Annot;
/*      */ import com.pdftron.pdf.PDFViewCtrl;
/*      */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*      */ import com.pdftron.pdf.utils.AnalyticsParam;
/*      */ import com.pdftron.pdf.utils.Utils;
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
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
/*      */ public class QuickMenu
/*      */   extends RelativeLayout
/*      */   implements View.OnClickListener, MenuItem.OnMenuItemClickListener
/*      */ {
/*   51 */   private static final String TAG = QuickMenu.class.getName();
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int PADDING_OFFSET_DP = 40;
/*      */ 
/*      */ 
/*      */   
/*      */   protected final PDFViewCtrl mParentView;
/*      */ 
/*      */ 
/*      */   
/*      */   protected View mAnchor;
/*      */ 
/*      */ 
/*      */   
/*      */   protected Annot mAnnot;
/*      */ 
/*      */ 
/*      */   
/*      */   protected ArrayList<QuickMenuItem> mFirstMenuItems;
/*      */ 
/*      */ 
/*      */   
/*      */   protected ArrayList<QuickMenuItem> mSecondMenuItems;
/*      */ 
/*      */ 
/*      */   
/*      */   protected ArrayList<QuickMenuItem> mOverflowMenuItems;
/*      */ 
/*      */ 
/*      */   
/*      */   protected RelativeLayout mMenuView;
/*      */ 
/*      */ 
/*      */   
/*      */   protected LinearLayout mMainLayout;
/*      */ 
/*      */ 
/*      */   
/*      */   protected LinearLayout mOverflowLayout;
/*      */ 
/*      */   
/*      */   protected ScrollView mOverflowLayoutRoot;
/*      */ 
/*      */   
/*      */   protected ToolManager mToolManager;
/*      */ 
/*      */   
/*      */   protected View mBgView;
/*      */ 
/*      */   
/*      */   protected Animation mFadeInAnim;
/*      */ 
/*      */   
/*      */   protected ScaleAnimation mMain2OverflowAnim;
/*      */ 
/*      */   
/*      */   protected ScaleAnimation mOverflow2MainAnim;
/*      */ 
/*      */   
/*  112 */   private MotionEvent mLastEventForwarded = null;
/*      */   private int mPopupWidth;
/*      */   private int mPopupHeight;
/*      */   private int mMainWidth;
/*      */   private int mMainHeight;
/*      */   private int mOverflowWidth;
/*      */   private int mOverflowHeight;
/*      */   private QuickMenuItem mSelectedMenuItem;
/*      */   private int mPaddingOffsetPx;
/*      */   private AppCompatImageButton mBackButton;
/*      */   private boolean mAlignTop = false;
/*      */   private OnDismissListener mListener;
/*  124 */   private int mDividerVisibility = 0;
/*      */   private QuickMenuItem mOverflowMenuItem;
/*      */   private QuickMenuBuilder mMenu;
/*  127 */   private int mAnalyticsQuickMenuType = 3;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean mActionHasPerformed;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ToolManager.ToolModeBase mToolMode;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public QuickMenu(PDFViewCtrl parent, boolean annotationPermission, @Nullable ToolManager.ToolModeBase toolMode) {
/*  144 */     super(parent.getContext());
/*  145 */     Context context = parent.getContext();
/*  146 */     this.mParentView = parent;
/*  147 */     this.mFirstMenuItems = new ArrayList<>();
/*  148 */     this.mSecondMenuItems = new ArrayList<>();
/*  149 */     this.mOverflowMenuItems = new ArrayList<>();
/*  150 */     this.mToolManager = (ToolManager)parent.getToolManager();
/*  151 */     this.mMenu = new QuickMenuBuilder(context, this.mToolManager, annotationPermission);
/*  152 */     this.mToolMode = toolMode;
/*  153 */     initView();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public QuickMenu(PDFViewCtrl parent, boolean annotationPermission) {
/*  163 */     this(parent, annotationPermission, (ToolManager.ToolModeBase)null);
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
/*      */   public QuickMenu(PDFViewCtrl parent, @Nullable ToolManager.ToolModeBase toolMode) {
/*  176 */     this(parent, true, toolMode);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public QuickMenu(PDFViewCtrl parent) {
/*  186 */     this(parent, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public QuickMenu(PDFViewCtrl parent, RectF anchor) {
/*  196 */     this(parent);
/*  197 */     setAnchorRect(anchor);
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
/*      */   public QuickMenu(PDFViewCtrl parent, RectF anchor, List<QuickMenuItem> menu_titles) {
/*  210 */     this(parent, anchor);
/*      */     
/*  212 */     addMenuEntries(menu_titles);
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
/*      */   public QuickMenu(PDFViewCtrl parent, RectF anchor, List<QuickMenuItem> menu_titles, int maxRowSize) {
/*  225 */     this(parent, anchor);
/*  226 */     addMenuEntries(menu_titles, maxRowSize);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void initMenuEntries() {
/*  233 */     addMenuEntries(this.mMenu.getMenuItems());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void initMenuEntries(@MenuRes int menuRes) {
/*  243 */     inflate(menuRes);
/*  244 */     initMenuEntries();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void initMenuEntries(@MenuRes int menuRes, int maxRowSize) {
/*  255 */     inflate(menuRes);
/*  256 */     addMenuEntries(this.mMenu.getMenuItems(), maxRowSize);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addMenuEntries(List<QuickMenuItem> menuEntries) {
/*  266 */     for (QuickMenuItem entry : menuEntries) {
/*  267 */       switch (entry.getDisplayMode()) {
/*      */         case 0:
/*  269 */           if (entry.getOrder() > -1) {
/*  270 */             this.mFirstMenuItems.add(entry.getOrder(), entry); break;
/*      */           } 
/*  272 */           this.mFirstMenuItems.add(entry);
/*      */           break;
/*      */         
/*      */         case 1:
/*  276 */           if (entry.getOrder() > -1) {
/*  277 */             this.mSecondMenuItems.add(entry.getOrder(), entry); break;
/*      */           } 
/*  279 */           this.mSecondMenuItems.add(entry);
/*      */           break;
/*      */         
/*      */         default:
/*  283 */           if (entry.getOrder() > -1) {
/*  284 */             this.mOverflowMenuItems.add(entry.getOrder(), entry); break;
/*      */           } 
/*  286 */           this.mOverflowMenuItems.add(entry);
/*      */           break;
/*      */       } 
/*  289 */       entry.setOnMenuItemClickListener(this);
/*      */     } 
/*  291 */     initMenuView();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean removeMenuEntries(@NonNull List<QuickMenuItem> menuEntries) {
/*  302 */     boolean result = false;
/*  303 */     for (QuickMenuItem entry : menuEntries) {
/*  304 */       switch (entry.getDisplayMode()) {
/*      */         case 0:
/*  306 */           result = this.mFirstMenuItems.remove(entry);
/*      */           break;
/*      */         case 1:
/*  309 */           result = this.mSecondMenuItems.remove(entry);
/*      */           break;
/*      */         default:
/*  312 */           result = this.mOverflowMenuItems.remove(entry); break;
/*      */       } 
/*  314 */       entry.setOnMenuItemClickListener(this);
/*      */     } 
/*  316 */     initMenuView();
/*      */     
/*  318 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addMenuEntries(List<QuickMenuItem> menuEntries, int maxRowSize) {
/*  329 */     addMenuEntries(menuEntries);
/*  330 */     this.mFirstMenuItems.remove(this.mOverflowMenuItem);
/*  331 */     if (maxRowSize > 0 && this.mSecondMenuItems.size() > maxRowSize) {
/*  332 */       int diffSize = this.mSecondMenuItems.size() - maxRowSize;
/*  333 */       List<QuickMenuItem> list = this.mSecondMenuItems.subList(this.mSecondMenuItems.size() - diffSize, this.mSecondMenuItems.size());
/*  334 */       this.mFirstMenuItems.addAll(new ArrayList<>(list));
/*  335 */       this.mSecondMenuItems.removeAll(new ArrayList(list));
/*      */     } 
/*      */ 
/*      */     
/*  339 */     int firstRowSize = maxRowSize - 1;
/*  340 */     if (firstRowSize > 0 && this.mFirstMenuItems.size() > firstRowSize) {
/*  341 */       int diffSize = this.mFirstMenuItems.size() - firstRowSize;
/*  342 */       List<QuickMenuItem> list = this.mFirstMenuItems.subList(this.mFirstMenuItems.size() - diffSize, this.mFirstMenuItems.size());
/*  343 */       this.mOverflowMenuItems.addAll(new ArrayList<>(list));
/*  344 */       this.mFirstMenuItems.removeAll(new ArrayList(list));
/*      */     } 
/*      */     
/*  347 */     initMenuView();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPointerIconClickable() {
/*  354 */     if (isShowing() && Utils.isNougat()) {
/*  355 */       this.mParentView.getToolManager().onChangePointerIcon(PointerIcon.getSystemIcon(getContext(), 1002));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @SuppressLint({"InflateParams"})
/*      */   protected void initView() {
/*  364 */     setVisibility(8);
/*  365 */     if (Utils.isLollipop()) {
/*  366 */       setElevation(2.0F);
/*      */     }
/*  368 */     LayoutInflater inflater = (LayoutInflater)this.mParentView.getContext().getSystemService("layout_inflater");
/*  369 */     if (inflater == null) {
/*      */       return;
/*      */     }
/*  372 */     this.mMenuView = (RelativeLayout)inflater.inflate(R.layout.quick_menu_layout, null);
/*  373 */     addView((View)this.mMenuView);
/*  374 */     this.mMainLayout = (LinearLayout)this.mMenuView.findViewById(R.id.main_group);
/*  375 */     this.mOverflowLayoutRoot = (ScrollView)this.mMenuView.findViewById(R.id.overflow_group_root);
/*  376 */     this.mOverflowLayout = (LinearLayout)this.mMenuView.findViewById(R.id.overflow_group);
/*  377 */     this.mBgView = this.mMenuView.findViewById(R.id.bg_view);
/*  378 */     this.mBackButton = (AppCompatImageButton)this.mMenuView.findViewById(R.id.back_btn);
/*  379 */     this.mBackButton.setColorFilter(Utils.getForegroundColor(getContext()));
/*  380 */     if (Utils.isNougat()) {
/*  381 */       this.mBackButton.setOnGenericMotionListener(new OnGenericMotionListener()
/*      */           {
/*      */             public boolean onGenericMotion(View view, MotionEvent motionEvent) {
/*  384 */               QuickMenu.this.setPointerIconClickable();
/*  385 */               return true;
/*      */             }
/*      */           });
/*      */     }
/*      */     
/*  390 */     this.mPaddingOffsetPx = (int)Utils.convDp2Pix(getContext(), 40.0F);
/*      */     
/*  392 */     initBackground();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void initMenuView() {
/*  400 */     if (this.mOverflowMenuItem == null) {
/*  401 */       this.mOverflowMenuItem = new QuickMenuItem(getContext(), R.id.qm_overflow, 0);
/*  402 */       this.mOverflowMenuItem.setIcon(R.drawable.ic_overflow_black_24dp);
/*  403 */       this.mOverflowMenuItem.setTitle("Overflow");
/*  404 */       this.mOverflowMenuItem.setOnMenuItemClickListener(this);
/*      */     } 
/*  406 */     if (!this.mOverflowMenuItems.isEmpty() && !this.mFirstMenuItems.contains(this.mOverflowMenuItem)) {
/*  407 */       this.mFirstMenuItems.add(this.mOverflowMenuItem);
/*      */     }
/*      */     
/*  410 */     initMainMenuView();
/*      */     
/*  412 */     initOverflowMenuView();
/*      */     
/*  414 */     measureLayoutSize();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void initMainMenuView() {
/*  422 */     if (this.mSecondMenuItems == null || this.mSecondMenuItems.isEmpty()) {
/*  423 */       setDividerVisibility(8);
/*      */     } else {
/*  425 */       setDividerVisibility(this.mDividerVisibility);
/*      */     } 
/*      */     
/*  428 */     LinearLayout mFirstRowView = (LinearLayout)this.mMenuView.findViewById(R.id.group1);
/*  429 */     LinearLayout mSecondRowView = (LinearLayout)this.mMenuView.findViewById(R.id.group2);
/*      */ 
/*      */     
/*  432 */     addMenuButtons(this.mFirstMenuItems, (ViewGroup)mFirstRowView);
/*      */ 
/*      */     
/*  435 */     addMenuButtons(this.mSecondMenuItems, (ViewGroup)mSecondRowView);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDividerVisibility(int visibility) {
/*  445 */     View divider = this.mMenuView.findViewById(R.id.divider);
/*  446 */     divider.setVisibility(visibility);
/*  447 */     this.mDividerVisibility = visibility;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void initOverflowMenuView() {
/*  454 */     this.mOverflowLayout.removeAllViews();
/*  455 */     for (QuickMenuItem entry : this.mOverflowMenuItems) {
/*  456 */       Button button; if (!entry.isVisible()) {
/*      */         continue;
/*      */       }
/*      */       
/*  460 */       if (entry.hasSubMenu()) {
/*  461 */         View view = entry.createSubMenuView();
/*      */       } else {
/*  463 */         button = entry.createTextButton();
/*  464 */         ViewGroup.LayoutParams lp = button.getLayoutParams();
/*  465 */         lp.width = (int)Utils.convDp2Pix(getContext(), 120.0F);
/*  466 */         button.setLayoutParams(lp);
/*      */       } 
/*  468 */       button.setEnabled(entry.isEnabled());
/*  469 */       if (Utils.isNougat()) {
/*  470 */         button.setOnGenericMotionListener(new OnGenericMotionListener()
/*      */             {
/*      */               public boolean onGenericMotion(View view, MotionEvent motionEvent) {
/*  473 */                 QuickMenu.this.setPointerIconClickable();
/*  474 */                 return true;
/*      */               }
/*      */             });
/*      */       }
/*  478 */       this.mOverflowLayout.addView((View)button);
/*      */     } 
/*      */     
/*  481 */     this.mOverflowLayout.addView((View)this.mBackButton);
/*  482 */     this.mOverflowLayout.measure(0, 0);
/*  483 */     this.mOverflowLayoutRoot.measure(0, 0);
/*  484 */     this.mOverflowWidth = this.mOverflowLayoutRoot.getMeasuredWidth();
/*  485 */     this.mOverflowHeight = this.mOverflowLayoutRoot.getMeasuredHeight();
/*      */ 
/*      */     
/*  488 */     int availableHeight = this.mToolManager.getPDFViewCtrl().getMeasuredHeight();
/*  489 */     if (this.mOverflowHeight > availableHeight) {
/*  490 */       this.mOverflowHeight = availableHeight - this.mPaddingOffsetPx;
/*      */     }
/*      */     
/*  493 */     this.mBackButton.setOnClickListener(this);
/*  494 */     this.mBackButton.setBackground(getContext().getResources().getDrawable(R.drawable.btn_borderless));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void initBackground() {
/*  501 */     if (!Utils.isLollipop()) {
/*  502 */       this.mBgView.setBackground(getContext().getResources().getDrawable(R.drawable.quickmenu_bg_rect_old_api));
/*      */     }
/*      */     
/*  505 */     Drawable drawable = this.mBgView.getBackground();
/*  506 */     if (drawable instanceof GradientDrawable) {
/*  507 */       GradientDrawable bgShape = (GradientDrawable)this.mBgView.getBackground();
/*  508 */       bgShape.setColor(Utils.getBackgroundColor(getContext()));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void initAnimation() {
/*  516 */     this.mFadeInAnim = AnimationUtils.loadAnimation(getContext(), R.anim.controls_quickmenu_show);
/*  517 */     float main2OverflowWidthScale = this.mOverflowWidth / this.mMainWidth;
/*  518 */     float main2OverflowHeightScale = this.mOverflowHeight / this.mMainHeight;
/*  519 */     float pivotX = 1.0F;
/*  520 */     float pivotY = 1.0F;
/*  521 */     if (Utils.isRtlLayout(getContext())) {
/*  522 */       pivotX = 0.0F;
/*      */     }
/*  524 */     if (this.mAlignTop) {
/*  525 */       pivotY = 0.0F;
/*      */     }
/*  527 */     this.mMain2OverflowAnim = new ScaleAnimation(1.0F, main2OverflowWidthScale, 1.0F, main2OverflowHeightScale, 1, pivotX, 1, pivotY);
/*      */     
/*  529 */     this.mOverflow2MainAnim = new ScaleAnimation(main2OverflowWidthScale, 1.0F, main2OverflowHeightScale, 1.0F, 1, pivotX, 1, pivotY);
/*      */ 
/*      */     
/*  532 */     this.mMain2OverflowAnim.setDuration(100L);
/*  533 */     this.mOverflow2MainAnim.setDuration(100L);
/*  534 */     this.mMain2OverflowAnim.setInterpolator((Interpolator)new DecelerateInterpolator());
/*  535 */     this.mOverflow2MainAnim.setInterpolator((Interpolator)new DecelerateInterpolator());
/*  536 */     this.mMain2OverflowAnim.setFillEnabled(true);
/*  537 */     this.mMain2OverflowAnim.setFillAfter(true);
/*  538 */     this.mOverflow2MainAnim.setFillEnabled(true);
/*  539 */     this.mOverflow2MainAnim.setFillAfter(true);
/*  540 */     this.mMain2OverflowAnim.setAnimationListener(new Animation.AnimationListener()
/*      */         {
/*      */           public void onAnimationStart(Animation animation) {}
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public void onAnimationEnd(Animation animation) {
/*  548 */             QuickMenu.this.mOverflowLayoutRoot.setVisibility(0);
/*  549 */             QuickMenu.this.mOverflowLayoutRoot.startAnimation(QuickMenu.this.mFadeInAnim);
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public void onAnimationRepeat(Animation animation) {}
/*      */         });
/*  559 */     this.mOverflow2MainAnim.setAnimationListener(new Animation.AnimationListener()
/*      */         {
/*      */           public void onAnimationStart(Animation animation) {}
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public void onAnimationEnd(Animation animation) {
/*  567 */             QuickMenu.this.mMainLayout.setVisibility(0);
/*  568 */             QuickMenu.this.mMainLayout.startAnimation(QuickMenu.this.mFadeInAnim);
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public void onAnimationRepeat(Animation animation) {}
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void measureLayoutSize() {
/*  583 */     this.mMainLayout.measure(0, 0);
/*  584 */     this.mMainWidth = this.mMainLayout.getMeasuredWidth();
/*  585 */     this.mMainHeight = this.mMainLayout.getMeasuredHeight();
/*      */     
/*  587 */     this.mPopupWidth = this.mMainWidth + this.mPaddingOffsetPx;
/*  588 */     this.mPopupHeight = this.mMainHeight;
/*  589 */     if (this.mOverflowHeight > this.mPopupHeight) {
/*  590 */       this.mPopupHeight = this.mOverflowHeight;
/*      */     }
/*  592 */     this.mPopupHeight += this.mPaddingOffsetPx;
/*      */     
/*  594 */     this.mMenuView.setLayoutParams((ViewGroup.LayoutParams)new LayoutParams(this.mPopupWidth, this.mPopupHeight));
/*  595 */     this.mMenuView.setClickable(false);
/*      */     
/*  597 */     LayoutParams layoutParams = new LayoutParams(this.mPopupWidth, this.mPopupHeight);
/*  598 */     setLayoutParams((ViewGroup.LayoutParams)layoutParams);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void requestLocation() {
/*  605 */     if (this.mAnchor == null) {
/*      */       return;
/*      */     }
/*      */     
/*  609 */     int margin = (int)Utils.convDp2Pix(getContext(), 20.0F);
/*  610 */     int subHeight = this.mPopupHeight - this.mMainHeight;
/*      */ 
/*      */     
/*  613 */     int offsetX = this.mAnchor.getRight() / 2 + this.mAnchor.getLeft() / 2 - this.mPopupWidth / 2;
/*  614 */     if (offsetX < 0) {
/*  615 */       offsetX = 0;
/*  616 */     } else if (offsetX + this.mPopupWidth > this.mParentView.getWidth()) {
/*  617 */       offsetX = this.mParentView.getWidth() - this.mPopupWidth;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  622 */     int offsetY = this.mAnchor.getTop() - this.mPopupHeight;
/*  623 */     this.mAlignTop = false;
/*      */ 
/*      */     
/*  626 */     if (offsetY + margin < 0) {
/*  627 */       offsetY = this.mAnchor.getTop() - this.mMainHeight - 2 * margin;
/*  628 */       this.mAlignTop = true;
/*      */     } 
/*      */ 
/*      */     
/*  632 */     if (offsetY + margin < 0) {
/*  633 */       offsetY = this.mAnchor.getBottom();
/*  634 */       this.mAlignTop = true;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  639 */     if (offsetY + this.mPopupHeight - margin / 2 > this.mParentView.getHeight()) {
/*  640 */       offsetY = this.mAnchor.getBottom() - subHeight + margin;
/*  641 */       this.mAlignTop = false;
/*      */     } 
/*      */ 
/*      */     
/*  645 */     if (offsetY + margin < 0) {
/*  646 */       offsetY = -margin;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  652 */     if (offsetY + this.mPopupHeight - margin / 2 > this.mParentView.getHeight()) {
/*  653 */       this.mAlignTop = true;
/*  654 */       offsetY = this.mAnchor.getTop() - this.mMainHeight - 2 * margin;
/*      */       
/*  656 */       if (offsetY + this.mPopupHeight - margin / 2 > this.mParentView.getHeight()) {
/*  657 */         int diff = offsetY + this.mPopupHeight - margin / 2 - this.mParentView.getHeight();
/*  658 */         offsetY -= diff;
/*      */       } 
/*      */ 
/*      */       
/*  662 */       if (offsetY + margin < 0) {
/*  663 */         offsetY = -margin;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  668 */     measure(MeasureSpec.makeMeasureSpec(this.mPopupWidth, 1073741824),
/*  669 */         MeasureSpec.makeMeasureSpec(this.mPopupHeight, 1073741824));
/*      */     
/*  671 */     offsetX += this.mParentView.getScrollX();
/*  672 */     offsetY += this.mParentView.getScrollY();
/*  673 */     layout(offsetX, offsetY, offsetX + this.mPopupWidth, offsetY + this.mPopupHeight);
/*      */     
/*  675 */     if (Utils.isRtlLayout(getContext())) {
/*  676 */       this.mOverflowLayoutRoot.layout(margin, margin, this.mOverflowWidth + margin, this.mOverflowHeight + margin);
/*      */     }
/*      */     
/*  679 */     if (this.mAlignTop) {
/*  680 */       int marginBottom = (this.mMainHeight + margin < this.mPopupHeight - margin) ? (this.mMainHeight + margin) : (this.mPopupHeight - margin);
/*  681 */       this.mBgView.layout(margin, margin, this.mMainWidth + margin, marginBottom);
/*      */       
/*  683 */       if (!this.mOverflowMenuItems.isEmpty()) {
/*  684 */         ArrayList<View> childs = new ArrayList<>();
/*  685 */         for (int i = 0; i < this.mOverflowLayout.getChildCount(); i++) {
/*  686 */           childs.add(this.mOverflowLayout.getChildAt(i));
/*      */         }
/*  688 */         this.mOverflowLayout.removeAllViews();
/*  689 */         for (View view : childs) {
/*  690 */           this.mOverflowLayout.addView(view);
/*  691 */           if (Utils.isNougat()) {
/*  692 */             view.setOnGenericMotionListener(new OnGenericMotionListener()
/*      */                 {
/*      */                   public boolean onGenericMotion(View view, MotionEvent motionEvent) {
/*  695 */                     QuickMenu.this.setPointerIconClickable();
/*  696 */                     return true;
/*      */                   }
/*      */                 });
/*      */           }
/*      */           
/*  701 */           if (view == this.mBackButton) {
/*  702 */             view.layout(view.getLeft(), 0, view.getRight(), this.mBackButton.getHeight()); continue;
/*      */           } 
/*  704 */           view.layout(view.getLeft(), view.getTop() + view.getHeight(), view.getRight(), view.getBottom() + view.getHeight());
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/*  709 */       int marginTop = (this.mPopupHeight - this.mMainHeight - margin > margin) ? (this.mPopupHeight - this.mMainHeight - margin) : margin;
/*      */       
/*  711 */       this.mMainLayout.layout(margin, marginTop, this.mMainWidth + margin, this.mPopupHeight - margin);
/*  712 */       this.mBgView.layout(margin, marginTop, this.mMainWidth + margin, this.mPopupHeight - margin);
/*      */     } 
/*  714 */     initAnimation();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void show(int quickMenuType) {
/*  723 */     AnalyticsHandlerAdapter.getInstance().sendTimedEvent(37, AnalyticsParam.quickMenuOpenParam(quickMenuType));
/*  724 */     this.mAnalyticsQuickMenuType = quickMenuType;
/*  725 */     show();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void show() {
/*  732 */     if (this.mToolManager != null && this.mToolManager.onShowQuickMenu(this, this.mAnnot)) {
/*      */       return;
/*      */     }
/*      */     
/*  736 */     if (this.mFirstMenuItems.isEmpty() && this.mSecondMenuItems.isEmpty() && this.mOverflowMenuItems.isEmpty()) {
/*      */       return;
/*      */     }
/*  739 */     post(new Runnable()
/*      */         {
/*      */           public void run() {
/*  742 */             QuickMenu.this.requestLocation();
/*      */           }
/*      */         });
/*  745 */     this.mParentView.addView((View)this);
/*  746 */     this.mMenuView.requestFocus();
/*  747 */     setVisibility(0);
/*  748 */     bringToFront();
/*  749 */     if (this.mToolManager != null) {
/*  750 */       this.mToolManager.onQuickMenuShown();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void dismiss() {
/*  758 */     if (this.mLastEventForwarded != null) {
/*  759 */       MotionEvent upEvent = MotionEvent.obtain(this.mLastEventForwarded);
/*  760 */       upEvent.setAction(1);
/*  761 */       this.mLastEventForwarded = null;
/*  762 */       this.mToolManager.getPDFViewCtrl().onTouchEvent(upEvent);
/*      */     } 
/*  764 */     setVisibility(8);
/*  765 */     this.mMenuView.setVisibility(8);
/*  766 */     if (this.mListener != null) {
/*  767 */       this.mListener.onDismiss();
/*      */     }
/*  769 */     this.mParentView.removeView((View)this);
/*  770 */     AnalyticsHandlerAdapter.getInstance().endTimedEvent(37);
/*  771 */     AnalyticsHandlerAdapter.getInstance().sendEvent(38, 
/*  772 */         AnalyticsParam.quickMenuDismissParam(this.mAnalyticsQuickMenuType, this.mActionHasPerformed));
/*      */     
/*  774 */     if (!this.mActionHasPerformed && 
/*  775 */       this.mSelectedMenuItem != null && this.mSelectedMenuItem.hasSubMenu()) {
/*  776 */       int id = this.mSelectedMenuItem.getItemId();
/*  777 */       if (id == R.id.qm_floating_sig) {
/*  778 */         AnalyticsHandlerAdapter.getInstance()
/*  779 */           .sendEvent(39, 
/*  780 */             AnalyticsParam.quickMenuParam(this.mAnalyticsQuickMenuType, "signature", 
/*      */               
/*  782 */               AnalyticsHandlerAdapter.getInstance().getQuickMenuNextAction(4)));
/*  783 */       } else if (id == R.id.qm_shape) {
/*  784 */         AnalyticsHandlerAdapter.getInstance()
/*  785 */           .sendEvent(39, 
/*  786 */             AnalyticsParam.quickMenuParam(this.mAnalyticsQuickMenuType, "shapes", 
/*      */               
/*  788 */               AnalyticsHandlerAdapter.getInstance().getQuickMenuNextAction(10)));
/*  789 */       } else if (id == R.id.qm_form) {
/*  790 */         AnalyticsHandlerAdapter.getInstance()
/*  791 */           .sendEvent(39, 
/*  792 */             AnalyticsParam.quickMenuParam(this.mAnalyticsQuickMenuType, "forms", 
/*      */               
/*  794 */               AnalyticsHandlerAdapter.getInstance().getQuickMenuNextAction(14)));
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
/*      */   public void setOnDismissListener(OnDismissListener listener) {
/*  806 */     this.mListener = listener;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onClick(View v) {
/*  814 */     if (v == this.mBackButton) {
/*  815 */       hideOverflowAnim();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public QuickMenuItem getSelectedMenuItem() {
/*  825 */     return this.mSelectedMenuItem;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void addMenuButtons(ArrayList<QuickMenuItem> menuItems, ViewGroup rowView) {
/*  835 */     rowView.removeAllViews();
/*  836 */     boolean containsFlatten = false;
/*  837 */     for (QuickMenuItem entry : menuItems) {
/*  838 */       Button button; if (!entry.isVisible()) {
/*      */         continue;
/*      */       }
/*      */       
/*  842 */       if (entry.getItemId() == R.id.qm_flatten) {
/*  843 */         containsFlatten = true;
/*      */       }
/*  845 */       if (entry.hasIcon() && !containsFlatten) {
/*  846 */         AppCompatImageButton appCompatImageButton = entry.createImageButton();
/*      */       } else {
/*  848 */         button = entry.createTextButton();
/*      */       } 
/*  850 */       button.setEnabled(entry.isEnabled());
/*  851 */       if (entry.getOrder() >= 0) {
/*  852 */         rowView.addView((View)button, entry.getOrder());
/*      */       } else {
/*  854 */         rowView.addView((View)button);
/*      */       } 
/*  856 */       if (Utils.isNougat()) {
/*  857 */         button.setOnGenericMotionListener(new OnGenericMotionListener()
/*      */             {
/*      */               public boolean onGenericMotion(View view, MotionEvent motionEvent) {
/*  860 */                 QuickMenu.this.setPointerIconClickable();
/*  861 */                 return true;
/*      */               }
/*      */             });
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isShowing() {
/*  874 */     return (getVisibility() == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void showOverflowAnim() {
/*  881 */     Animation fadeOutAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.controls_quickmenu_hide);
/*  882 */     fadeOutAnimation.setAnimationListener(new FadeOutAnimListener((View)this.mMainLayout, this.mBgView, (Animation)this.mMain2OverflowAnim));
/*  883 */     this.mMainLayout.startAnimation(fadeOutAnimation);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void hideOverflowAnim() {
/*  890 */     Animation fadeOutAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.controls_quickmenu_hide);
/*  891 */     fadeOutAnimation.setAnimationListener(new FadeOutAnimListener((View)this.mOverflowLayoutRoot, this.mBgView, (Animation)this.mOverflow2MainAnim));
/*  892 */     this.mOverflowLayoutRoot.startAnimation(fadeOutAnimation);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAnchor(@NonNull View anchor) {
/*  901 */     this.mAnchor = anchor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAnchorRect(@NonNull RectF anchor_rect) {
/*  910 */     if (this.mAnchor == null) {
/*  911 */       this.mAnchor = new View(getContext());
/*      */     }
/*  913 */     this.mAnchor.setVisibility(4);
/*  914 */     this.mAnchor.layout((int)anchor_rect.left, (int)anchor_rect.top, (int)anchor_rect.right, (int)anchor_rect.bottom);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAnnot(@Nullable Annot annot) {
/*  922 */     this.mAnnot = annot;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Annot getAnnot() {
/*  930 */     return this.mAnnot;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<QuickMenuItem> getFirstRowMenuItems() {
/*  940 */     return this.mFirstMenuItems;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<QuickMenuItem> getSecondRowMenuItems() {
/*  950 */     return this.mSecondMenuItems;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<QuickMenuItem> getOverflowMenuItems() {
/*  960 */     return this.mOverflowMenuItems;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public View getMenuBackground() {
/*  970 */     return this.mBgView;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AppCompatImageButton getBackButton() {
/*  980 */     return this.mBackButton;
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
/*      */   @NonNull
/*      */   public Menu getMenu() {
/*  993 */     return this.mMenu;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   public MenuInflater getMenuInflater() {
/* 1003 */     return new MenuInflater(getContext());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void inflate(@MenuRes int menuRes) {
/* 1013 */     getMenuInflater().inflate(menuRes, this.mMenu);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public QuickMenuItem findMenuItem(@IdRes int itemId) {
/* 1024 */     return (QuickMenuItem)getMenu().findItem(itemId);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean onMenuItemClick(MenuItem item) {
/* 1029 */     this.mSelectedMenuItem = (QuickMenuItem)item;
/*      */     
/* 1031 */     if (item.hasSubMenu()) {
/* 1032 */       this.mFirstMenuItems.clear();
/* 1033 */       this.mSecondMenuItems.clear();
/* 1034 */       this.mOverflowMenuItems.clear();
/* 1035 */       QuickMenuBuilder subMenu = (QuickMenuBuilder)item.getSubMenu();
/* 1036 */       addMenuEntries(subMenu.getMenuItems(), 4);
/* 1037 */       this.mMainLayout.setVisibility(0);
/* 1038 */       this.mOverflowLayoutRoot.setVisibility(4);
/* 1039 */       this.mParentView.removeView((View)this);
/* 1040 */       show();
/* 1041 */       return true;
/*      */     } 
/*      */     
/* 1044 */     if (!item.equals(this.mOverflowMenuItem)) {
/* 1045 */       String action = null;
/* 1046 */       int nextAction = 0;
/* 1047 */       int id = item.getItemId();
/* 1048 */       if (id == R.id.qm_floating_sig) {
/* 1049 */         action = "signature";
/* 1050 */         nextAction = 2;
/* 1051 */       } else if (id == R.id.qm_arrow) {
/* 1052 */         action = "shapes";
/* 1053 */         nextAction = 5;
/* 1054 */       } else if (id == R.id.qm_polyline) {
/* 1055 */         action = "shapes";
/* 1056 */         nextAction = 6;
/* 1057 */       } else if (id == R.id.qm_oval) {
/* 1058 */         action = "shapes";
/* 1059 */         nextAction = 7;
/* 1060 */       } else if (id == R.id.qm_polygon) {
/* 1061 */         action = "shapes";
/* 1062 */         nextAction = 6;
/* 1063 */       } else if (id == R.id.qm_cloud) {
/* 1064 */         action = "shapes";
/* 1065 */         nextAction = 9;
/* 1066 */       } else if (id == R.id.qm_ruler) {
/* 1067 */         action = "shapes";
/* 1068 */         nextAction = 15;
/* 1069 */       } else if (id == R.id.qm_callout) {
/* 1070 */         action = "shapes";
/* 1071 */         nextAction = 16;
/* 1072 */       } else if (id == R.id.qm_form_text) {
/* 1073 */         action = "forms";
/* 1074 */         nextAction = 11;
/* 1075 */       } else if (id == R.id.qm_form_check_box) {
/* 1076 */         action = "forms";
/* 1077 */         nextAction = 12;
/* 1078 */       } else if (id == R.id.qm_form_signature) {
/* 1079 */         action = "forms";
/* 1080 */         nextAction = 13;
/*      */       } 
/*      */       
/* 1083 */       if (action == null) {
/* 1084 */         AnalyticsHandlerAdapter.getInstance()
/* 1085 */           .sendEvent(39, 
/* 1086 */             AnalyticsParam.quickMenuParam(this.mAnalyticsQuickMenuType, 
/* 1087 */               AnalyticsHandlerAdapter.getInstance().getQuickMenuAction(id, this.mToolMode)));
/*      */       } else {
/* 1089 */         AnalyticsHandlerAdapter.getInstance()
/* 1090 */           .sendEvent(39, 
/* 1091 */             AnalyticsParam.quickMenuParam(this.mAnalyticsQuickMenuType, action, 
/* 1092 */               AnalyticsHandlerAdapter.getInstance().getQuickMenuNextAction(nextAction)));
/*      */       } 
/*      */       
/* 1095 */       this.mActionHasPerformed = true;
/* 1096 */       dismiss();
/* 1097 */       return true;
/*      */     } 
/* 1099 */     showOverflowAnim();
/* 1100 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static interface OnDismissListener
/*      */   {
/*      */     void onDismiss();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public class FadeOutAnimListener
/*      */     implements Animation.AnimationListener
/*      */   {
/*      */     View mNextView;
/*      */ 
/*      */ 
/*      */     
/*      */     Animation mNextAnimation;
/*      */ 
/*      */ 
/*      */     
/*      */     View mView;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     FadeOutAnimListener(View view, View nextView, Animation nextAnimation) {
/* 1132 */       this.mView = view;
/* 1133 */       this.mNextView = nextView;
/* 1134 */       this.mNextAnimation = nextAnimation;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void onAnimationStart(Animation animation) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void onAnimationEnd(Animation animation) {
/* 1154 */       this.mView.setVisibility(4);
/* 1155 */       this.mNextView.startAnimation(this.mNextAnimation);
/*      */     }
/*      */     
/*      */     public void onAnimationRepeat(Animation animation) {}
/*      */   }
/*      */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\QuickMenu.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */