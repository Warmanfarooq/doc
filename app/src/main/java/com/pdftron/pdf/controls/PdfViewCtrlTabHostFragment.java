/*      */ package com.pdftron.pdf.controls;
/*      */ 
/*      */ import android.annotation.SuppressLint;
/*      */ import android.annotation.TargetApi;
/*      */ import android.app.Activity;
/*      */ import android.app.AlertDialog;
/*      */ import android.app.ProgressDialog;
/*      */ import android.content.Context;
/*      */ import android.content.DialogInterface;
/*      */ import android.content.res.ColorStateList;
/*      */ import android.content.res.Configuration;
/*      */ import android.graphics.PointF;
/*      */ import android.graphics.PorterDuff;
/*      */ import android.net.Uri;
/*      */ import android.os.AsyncTask;
/*      */ import android.os.Bundle;
/*      */ import android.os.Handler;
/*      */ import android.os.Looper;
/*      */ import android.text.Html;
/*      */ import android.util.Log;
/*      */ import android.util.SparseArray;
/*      */ import android.util.SparseBooleanArray;
/*      */ import android.view.KeyEvent;
/*      */ import android.view.LayoutInflater;
/*      */ import android.view.Menu;
/*      */ import android.view.MenuInflater;
/*      */ import android.view.MenuItem;
/*      */ import android.view.View;
/*      */ import android.view.ViewGroup;
/*      */ import android.view.ViewStub;
/*      */ import android.view.WindowManager;
/*      */ import android.widget.FrameLayout;
/*      */ import android.widget.ImageButton;
/*      */ import android.widget.RelativeLayout;
/*      */ import android.widget.TextView;
/*      */ import androidx.annotation.CallSuper;
/*      */ import androidx.annotation.NonNull;
/*      */ import androidx.annotation.Nullable;
/*      */ import androidx.appcompat.app.ActionBar;
/*      */ import androidx.appcompat.app.AppCompatActivity;
/*      */ import androidx.appcompat.view.menu.MenuBuilder;
/*      */ import androidx.appcompat.view.menu.MenuItemImpl;
/*      */ import androidx.appcompat.widget.Toolbar;
/*      */ import androidx.coordinatorlayout.widget.CoordinatorLayout;
/*      */ import androidx.core.graphics.drawable.DrawableCompat;
/*      */ import androidx.core.view.DisplayCutoutCompat;
/*      */ import androidx.core.view.OnApplyWindowInsetsListener;
/*      */ import androidx.core.view.ViewCompat;
/*      */ import androidx.core.view.WindowInsetsCompat;
/*      */ import androidx.fragment.app.Fragment;
/*      */ import androidx.fragment.app.FragmentActivity;
/*      */ import androidx.fragment.app.FragmentManager;
/*      */ import androidx.lifecycle.Observer;
/*      */ import androidx.lifecycle.ViewModelProviders;
/*      */ import androidx.transition.Fade;
/*      */ import androidx.transition.Slide;
/*      */ import androidx.transition.Transition;
/*      */ import androidx.transition.TransitionManager;
/*      */ import com.google.android.material.snackbar.Snackbar;
/*      */ import com.google.android.material.tabs.TabLayout;
/*      */ import com.pdftron.common.PDFNetException;
/*      */ import com.pdftron.filters.Filter;
/*      */ import com.pdftron.filters.SecondaryFileFilter;
/*      */ import com.pdftron.pdf.Annot;
/*      */ import com.pdftron.pdf.Bookmark;
/*      */ import com.pdftron.pdf.PDFDoc;
/*      */ import com.pdftron.pdf.PDFViewCtrl;
/*      */ import com.pdftron.pdf.Page;
/*      */ import com.pdftron.pdf.PageSet;
/*      */ import com.pdftron.pdf.TextSearchResult;
/*      */ import com.pdftron.pdf.config.ViewerConfig;
/*      */ import com.pdftron.pdf.dialog.BookmarksDialogFragment;
/*      */ import com.pdftron.pdf.dialog.OptimizeDialogFragment;
/*      */ import com.pdftron.pdf.dialog.RotateDialogFragment;
/*      */ import com.pdftron.pdf.dialog.ViewModePickerDialogFragment;
/*      */ import com.pdftron.pdf.dialog.annotlist.AnnotationListSortOrder;
/*      */ import com.pdftron.pdf.dialog.annotlist.BaseAnnotationSortOrder;
/*      */ import com.pdftron.pdf.dialog.menueditor.MenuEditorDialogFragment;
/*      */ import com.pdftron.pdf.dialog.menueditor.MenuEditorEvent;
/*      */ import com.pdftron.pdf.dialog.menueditor.MenuEditorViewModel;
/*      */ import com.pdftron.pdf.dialog.menueditor.model.MenuEditorItem;
/*      */ import com.pdftron.pdf.dialog.menueditor.model.MenuEditorItemContent;
/*      */ import com.pdftron.pdf.dialog.menueditor.model.MenuEditorItemHeader;
/*      */ import com.pdftron.pdf.dialog.pdflayer.PdfLayerDialog;
/*      */ import com.pdftron.pdf.dialog.pdflayer.PdfLayerUtils;
/*      */ import com.pdftron.pdf.dialog.redaction.SearchRedactionDialogFragment;
/*      */ import com.pdftron.pdf.model.ExternalFileInfo;
/*      */ import com.pdftron.pdf.model.FileInfo;
/*      */ import com.pdftron.pdf.model.OptimizeParams;
/*      */ import com.pdftron.pdf.model.PdfViewCtrlTabInfo;
/*      */ import com.pdftron.pdf.tools.QuickMenu;
/*      */ import com.pdftron.pdf.tools.QuickMenuItem;
/*      */ import com.pdftron.pdf.tools.R;
/*      */ import com.pdftron.pdf.tools.ToolManager;
/*      */ import com.pdftron.pdf.tools.UndoRedoManager;
/*      */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*      */ import com.pdftron.pdf.utils.AnalyticsParam;
/*      */ import com.pdftron.pdf.utils.CommonToast;
/*      */ import com.pdftron.pdf.utils.DialogFragmentTab;
/*      */ import com.pdftron.pdf.utils.PaneBehavior;
/*      */ import com.pdftron.pdf.utils.PdfViewCtrlSettingsManager;
/*      */ import com.pdftron.pdf.utils.PdfViewCtrlTabsManager;
/*      */ import com.pdftron.pdf.utils.ShortcutHelper;
/*      */ import com.pdftron.pdf.utils.UserCropUtilities;
/*      */ import com.pdftron.pdf.utils.Utils;
/*      */ import com.pdftron.pdf.utils.ViewerUtils;
/*      */ import com.pdftron.pdf.viewmodel.RedactionEvent;
/*      */ import com.pdftron.pdf.viewmodel.RedactionViewModel;
/*      */ import com.pdftron.pdf.widget.AppBarLayout;
/*      */ import com.pdftron.sdf.SDFDoc;
/*      */ import io.reactivex.android.schedulers.AndroidSchedulers;
/*      */ import io.reactivex.disposables.CompositeDisposable;
/*      */ import io.reactivex.disposables.Disposable;
/*      */ import io.reactivex.functions.Consumer;
/*      */ import io.reactivex.schedulers.Schedulers;
/*      */ import java.io.File;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.concurrent.atomic.AtomicBoolean;
/*      */ import org.apache.commons.io.FilenameUtils;
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
/*      */ public class PdfViewCtrlTabHostFragment
/*      */   extends Fragment
/*      */   implements PdfViewCtrlTabFragment.TabListener, AnnotationToolbar.AnnotationToolbarListener, ToolManager.QuickMenuListener, TabLayout.BaseOnTabSelectedListener, SearchResultsView.SearchResultsListener, ViewModePickerDialogFragment.ViewModePickerDialogFragmentListener, BookmarksDialogFragment.BookmarksDialogListener, BookmarksTabLayout.BookmarksTabsListener, UserCropSelectionDialogFragment.UserCropSelectionDialogFragmentListener, UserCropDialogFragment.OnUserCropDialogDismissListener, UserCropUtilities.AutoCropInBackgroundTask.AutoCropTaskListener, ThumbnailsViewFragment.OnThumbnailsViewDialogDismissListener, ThumbnailsViewFragment.OnThumbnailsEditAttemptWhileReadOnlyListener, ThumbnailsViewFragment.OnExportThumbnailsListener, View.OnLayoutChangeListener, View.OnSystemUiVisibilityChangeListener
/*      */ {
/*  161 */   private static final String TAG = PdfViewCtrlTabHostFragment.class.getName();
/*      */   
/*      */   public static final String BUNDLE_TAB_HOST_NAV_ICON = "bundle_tab_host_nav_icon";
/*      */   
/*      */   public static final String BUNDLE_TAB_HOST_TOOLBAR_MENU = "bundle_tab_host_toolbar_menu";
/*      */   
/*      */   public static final String BUNDLE_TAB_HOST_CONFIG = "bundle_tab_host_config";
/*      */   
/*      */   public static final String BUNDLE_TAB_HOST_QUIT_APP_WHEN_DONE_VIEWING = "bundle_tab_host_quit_app_when_done_viewing";
/*      */   
/*      */   public static final String BUNDLE_TAB_FRAGMENT_CLASS = "PdfViewCtrlTabHostFragment_tab_fragment_class";
/*      */   
/*      */   protected Class<? extends PdfViewCtrlTabFragment> mTabFragmentClass;
/*      */   
/*      */   private static final int MAX_TOOLBAR_ICON_COUNT = 7;
/*      */   
/*      */   protected static final int MAX_TOOLBAR_VISIBLE_ICON_COUNT = 5;
/*      */   
/*      */   private static final int HIDE_TOOLBARS_TIMER = 5000;
/*      */   
/*      */   private static final int HIDE_NAVIGATION_BAR_TIMER = 3000;
/*      */   private static final String KEY_IS_SEARCH_MODE = "is_search_mode";
/*      */   private static final String KEY_IS_RESTARTED = "is_fragment_restarted";
/*      */   public static final int ANIMATE_DURATION_SHOW = 250;
/*      */   public static final int ANIMATE_DURATION_HIDE = 250;
/*      */   private static boolean sDebug;
/*      */   protected boolean mQuitAppWhenDoneViewing;
/*  188 */   protected int mToolbarNavRes = R.drawable.ic_menu_white_24dp;
/*  189 */   protected int[] mToolbarMenuResArray = new int[] { R.menu.fragment_viewer };
/*      */   
/*      */   protected ViewerConfig mViewerConfig;
/*      */   
/*      */   protected View mFragmentView;
/*      */   
/*      */   protected AppBarLayout mAppBarLayout;
/*      */   protected Toolbar mToolbar;
/*      */   protected SearchToolbar mSearchToolbar;
/*      */   protected CustomFragmentTabLayout mTabLayout;
/*      */   protected FrameLayout mFragmentContainer;
/*      */   private UndoRedoPopupWindow mUndoRedoPopupWindow;
/*      */   protected String mStartupTabTag;
/*      */   protected boolean mMultiTabModeEnabled = true;
/*  203 */   private int mCurTabIndex = -1;
/*      */   
/*      */   protected ThumbnailsViewFragment mThumbFragment;
/*      */   
/*      */   protected BookmarksDialogFragment mBookmarksDialog;
/*      */   
/*      */   protected Bookmark mCurrentBookmark;
/*      */   
/*      */   protected int mLastSystemUIVisibility;
/*      */   
/*  213 */   protected AtomicBoolean mFileSystemChanged = new AtomicBoolean();
/*      */   
/*      */   protected boolean mFragmentPaused = true;
/*      */   
/*      */   private UserCropUtilities.AutoCropInBackgroundTask mAutoCropTask;
/*      */   
/*      */   private boolean mAutoCropTaskPaused;
/*      */   
/*      */   private String mAutoCropTaskTabTag;
/*      */   
/*      */   protected SearchResultsView mSearchResultsView;
/*      */   
/*      */   protected boolean mIsSearchMode;
/*      */   
/*      */   protected boolean mIsRestarted = false;
/*      */   
/*      */   protected boolean mAutoHideEnabled = true;
/*      */   protected boolean mWillShowAnnotationToolbar;
/*      */   protected MenuItem mMenuShare;
/*      */   protected MenuItem mMenuAnnotToolbar;
/*      */   protected MenuItem mMenuFormToolbar;
/*      */   protected MenuItem mMenuFillAndSignToolbar;
/*      */   protected MenuItem mMenuReflowMode;
/*      */   protected MenuItem mMenuViewMode;
/*      */   protected MenuItem mMenuPrint;
/*      */   protected MenuItem mMenuSearch;
/*      */   protected MenuItem mMenuUndo;
/*      */   protected MenuItem mMenuRedo;
/*      */   protected MenuItem mMenuEditPages;
/*      */   protected MenuItem mMenuCloseTab;
/*      */   protected MenuItem mMenuViewFileAttachment;
/*      */   protected MenuItem mMenuPdfLayers;
/*      */   protected MenuItem mMenuExport;
/*      */   protected MenuItem mMenuOptimize;
/*      */   protected MenuItem mMenuPasswordSave;
/*      */   protected MenuItem mMenuEditMenu;
/*      */   protected List<TabHostListener> mTabHostListeners;
/*      */   protected AppBarVisibilityListener mAppBarVisibilityListener;
/*  251 */   protected int mSystemWindowInsetTop = 0;
/*  252 */   protected int mSystemWindowInsetBottom = 0;
/*      */ 
/*      */   
/*  255 */   protected CompositeDisposable mDisposables = new CompositeDisposable();
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
/*      */   private boolean mToolbarTimerDisabled;
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
/*  389 */   private Handler mHideToolbarsHandler = new Handler(Looper.getMainLooper());
/*  390 */   private Runnable mHideToolbarsRunnable = new Runnable()
/*      */     {
/*      */       public void run() {
/*  393 */         PdfViewCtrlTabHostFragment.this.hideUI();
/*      */       }
/*      */     };
/*      */ 
/*      */   
/*  398 */   private Handler mHideNavigationBarHandler = new Handler(Looper.getMainLooper());
/*  399 */   private Runnable mHideNavigationBarRunnable = new Runnable()
/*      */     {
/*      */       public void run() {
/*  402 */         PdfViewCtrlTabFragment currentFragment = PdfViewCtrlTabHostFragment.this.getCurrentPdfViewCtrlFragment();
/*  403 */         if (currentFragment != null && currentFragment.isAnnotationMode()) {
/*  404 */           PdfViewCtrlTabHostFragment.this.showSystemStatusBar();
/*      */         }
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static PdfViewCtrlTabHostFragment newInstance(Bundle args) {
/*  415 */     PdfViewCtrlTabHostFragment fragment = new PdfViewCtrlTabHostFragment();
/*  416 */     fragment.setArguments(args);
/*  417 */     return fragment;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onCreate(Bundle savedInstanceState) {
/*  425 */     if (sDebug) {
/*  426 */       Log.v("LifeCycle", "HostFragment.onCreate");
/*      */     }
/*  428 */     super.onCreate(savedInstanceState);
/*      */     
/*  430 */     FragmentActivity fragmentActivity = getActivity();
/*      */     
/*  432 */     if (canRecreateActivity() && fragmentActivity instanceof AppCompatActivity && Utils.applyDayNight((AppCompatActivity)fragmentActivity)) {
/*      */       return;
/*      */     }
/*      */     
/*  436 */     if (getArguments() != null) {
/*  437 */       this.mToolbarNavRes = getArguments().getInt("bundle_tab_host_nav_icon", R.drawable.ic_menu_white_24dp);
/*  438 */       int[] menus = getArguments().getIntArray("bundle_tab_host_toolbar_menu");
/*  439 */       if (menus != null) {
/*  440 */         this.mToolbarMenuResArray = menus;
/*      */       }
/*  442 */       this.mViewerConfig = (ViewerConfig)getArguments().getParcelable("bundle_tab_host_config");
/*  443 */       this.mQuitAppWhenDoneViewing = getArguments().getBoolean("bundle_tab_host_quit_app_when_done_viewing", false);
/*      */       
/*  445 */       this.mTabFragmentClass = (Class<? extends PdfViewCtrlTabFragment>)getArguments().getSerializable("PdfViewCtrlTabHostFragment_tab_fragment_class");
/*      */     } 
/*  447 */     this.mTabFragmentClass = (this.mTabFragmentClass == null) ? getDefaultTabFragmentClass() : this.mTabFragmentClass;
/*      */     
/*  449 */     setHasOptionsMenu(true);
/*      */     
/*  451 */     if (savedInstanceState != null) {
/*  452 */       this.mIsSearchMode = savedInstanceState.getBoolean("is_search_mode");
/*  453 */       this.mIsRestarted = savedInstanceState.getBoolean("is_fragment_restarted");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
/*  463 */     if (sDebug) {
/*  464 */       Log.v("LifeCycle", "HostFragment.onCreateView");
/*      */     }
/*  466 */     return inflater.inflate(R.layout.controls_fragment_tabbed_pdfviewctrl, container, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
/*  474 */     if (sDebug) {
/*  475 */       Log.v("LifeCycle", "HostFragment.onViewCreated");
/*      */     }
/*  477 */     super.onViewCreated(view, savedInstanceState);
/*      */     
/*  479 */     this.mFragmentView = view;
/*  480 */     initViews();
/*  481 */     updateFullScreenModeLayout();
/*      */     
/*  483 */     createTabs(getArguments());
/*      */     
/*  485 */     updateTabLayout();
/*      */     
/*  487 */     adjustConfiguration();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onActivityCreated(@Nullable Bundle savedInstanceState) {
/*  495 */     super.onActivityCreated(savedInstanceState);
/*      */     
/*  497 */     FragmentActivity fragmentActivity = getActivity();
/*  498 */     if (fragmentActivity instanceof AppCompatActivity && useSupportActionBar()) {
/*  499 */       AppCompatActivity appCompatActivity = (AppCompatActivity)fragmentActivity;
/*  500 */       appCompatActivity.setSupportActionBar(this.mToolbar);
/*      */       
/*  502 */       ActionBar actionBar = appCompatActivity.getSupportActionBar();
/*  503 */       if (actionBar != null) {
/*  504 */         if (this.mViewerConfig != null && !Utils.isNullOrEmpty(this.mViewerConfig.getToolbarTitle())) {
/*  505 */           actionBar.setDisplayShowTitleEnabled(true);
/*  506 */           actionBar.setTitle(this.mViewerConfig.getToolbarTitle());
/*      */         } else {
/*  508 */           actionBar.setDisplayShowTitleEnabled(false);
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  513 */         actionBar.addOnMenuVisibilityListener(new ActionBar.OnMenuVisibilityListener()
/*      */             {
/*      */               public void onMenuVisibilityChanged(boolean isVisible) {
/*  516 */                 if (isVisible) {
/*      */                   
/*  518 */                   PdfViewCtrlTabFragment currentFragment = PdfViewCtrlTabHostFragment.this.getCurrentPdfViewCtrlFragment();
/*  519 */                   boolean isAnnotationMode = (currentFragment != null && currentFragment.isAnnotationMode());
/*  520 */                   if (!PdfViewCtrlTabHostFragment.this.mIsSearchMode && !isAnnotationMode) {
/*  521 */                     PdfViewCtrlTabHostFragment.this.stopHideToolbarsTimer();
/*      */                   }
/*      */                 } else {
/*  524 */                   PdfViewCtrlTabFragment currentFragment = PdfViewCtrlTabHostFragment.this.getCurrentPdfViewCtrlFragment();
/*  525 */                   boolean isAnnotationMode = (currentFragment != null && currentFragment.isAnnotationMode());
/*  526 */                   if (!PdfViewCtrlTabHostFragment.this.mIsSearchMode && !isAnnotationMode) {
/*  527 */                     PdfViewCtrlTabHostFragment.this.resetHideToolbarsTimer();
/*      */                   }
/*      */                 } 
/*      */               }
/*      */             });
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void onStart() {
/*  538 */     super.onStart();
/*  539 */     AnalyticsHandlerAdapter.getInstance().sendTimedEvent(1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onResume() {
/*  547 */     if (sDebug) {
/*  548 */       Log.v("LifeCycle", "HostFragment.onResume");
/*      */     }
/*  550 */     super.onResume();
/*      */     
/*  552 */     if (isHidden()) {
/*      */       return;
/*      */     }
/*      */     
/*  556 */     resumeFragment();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onPause() {
/*  564 */     if (sDebug) {
/*  565 */       Log.v("LifeCycle", "HostFragment.onPause");
/*      */     }
/*  567 */     pauseFragment();
/*      */     
/*  569 */     super.onPause();
/*      */   }
/*      */ 
/*      */   
/*      */   public void onStop() {
/*  574 */     super.onStop();
/*  575 */     AnalyticsHandlerAdapter.getInstance().endTimedEvent(1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDestroyView() {
/*  583 */     if (sDebug)
/*  584 */       Log.v("LifeCycle", "HostFragment.onDestroy"); 
/*  585 */     super.onDestroyView();
/*  586 */     this.mTabLayout.removeOnTabSelectedListener(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDestroy() {
/*  594 */     if (sDebug) {
/*  595 */       Log.v("LifeCycle", "HostFragment.onDestroy");
/*      */     }
/*      */     try {
/*  598 */       this.mTabLayout.removeAllFragments();
/*  599 */     } catch (Exception exception) {}
/*      */ 
/*      */     
/*  602 */     PdfViewCtrlTabsManager.getInstance().cleanup();
/*      */ 
/*      */     
/*  605 */     if (this.mDisposables != null && !this.mDisposables.isDisposed()) {
/*  606 */       this.mDisposables.dispose();
/*      */     }
/*      */     
/*  609 */     super.onDestroy();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onSaveInstanceState(@NonNull Bundle outState) {
/*  617 */     super.onSaveInstanceState(outState);
/*      */     
/*  619 */     outState.putBoolean("is_search_mode", this.mIsSearchMode);
/*  620 */     outState.putBoolean("is_fragment_restarted", true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
/*  628 */     FragmentActivity fragmentActivity = getActivity();
/*  629 */     if (fragmentActivity == null) {
/*      */       return;
/*      */     }
/*      */     
/*  633 */     if (this.mTabHostListeners != null) {
/*  634 */       for (TabHostListener listener : this.mTabHostListeners) {
/*  635 */         if (listener.onToolbarCreateOptionsMenu(menu, inflater)) {
/*      */           return;
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*  641 */     if (useSupportActionBar()) {
/*  642 */       for (int res : this.mToolbarMenuResArray) {
/*  643 */         inflater.inflate(res, menu);
/*      */       }
/*      */     }
/*      */     
/*  647 */     initOptionsMenu(menu);
/*  648 */     setOptionsMenuVisible(true);
/*      */     
/*  650 */     restoreSavedMenu();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onPrepareOptionsMenu(Menu menu) {
/*  658 */     FragmentActivity fragmentActivity = getActivity();
/*  659 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/*  660 */     if (fragmentActivity == null || currentFragment == null) {
/*      */       return;
/*      */     }
/*      */     
/*  664 */     if (this.mTabHostListeners != null) {
/*  665 */       for (TabHostListener listener : this.mTabHostListeners) {
/*  666 */         if (listener.onToolbarPrepareOptionsMenu(menu)) {
/*      */           return;
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*  673 */     stopHideToolbarsTimer();
/*      */     
/*  675 */     if (menu != null) {
/*      */       
/*  677 */       if (!this.mIsSearchMode) {
/*  678 */         updateCloseTabButtonVisibility(true);
/*      */       }
/*      */       
/*  681 */       if (this.mMenuViewFileAttachment != null) {
/*  682 */         if (currentFragment.getPdfDoc() != null && 
/*  683 */           Utils.hasFileAttachments(currentFragment.getPdfDoc())) {
/*  684 */           this.mMenuViewFileAttachment.setVisible(true);
/*      */         } else {
/*  686 */           this.mMenuViewFileAttachment.setVisible(false);
/*      */         } 
/*      */       }
/*      */       
/*  690 */       if (this.mMenuPdfLayers != null) {
/*  691 */         if ((this.mViewerConfig == null || this.mViewerConfig.isShowViewLayersToolbarOption()) && currentFragment
/*  692 */           .getPdfDoc() != null && PdfLayerUtils.hasPdfLayer(currentFragment.getPdfDoc())) {
/*  693 */           this.mMenuPdfLayers.setVisible(true);
/*      */         } else {
/*  695 */           this.mMenuPdfLayers.setVisible(false);
/*      */         } 
/*      */       }
/*      */       
/*  699 */       if (this.mMenuPasswordSave != null) {
/*  700 */         if (currentFragment.isPasswordProtected()) {
/*  701 */           this.mMenuPasswordSave.setTitle(getString(R.string.action_export_password_existing));
/*      */         } else {
/*  703 */           this.mMenuPasswordSave.setTitle(getString(R.string.action_export_password));
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/*  708 */       MenuItem undoItem = menu.findItem(R.id.undo);
/*  709 */       MenuItem redoItem = menu.findItem(R.id.redo);
/*  710 */       if (undoItem != null && redoItem != null) {
/*  711 */         ToolManager toolManager = currentFragment.getToolManager();
/*  712 */         UndoRedoManager undoRedoManager = (toolManager != null) ? toolManager.getUndoRedoManger() : null;
/*      */         
/*  714 */         if (!currentFragment.isReflowMode() && !this.mIsSearchMode && undoRedoManager != null) {
/*  715 */           String nextUndoAction = undoRedoManager.getNextUndoAction();
/*  716 */           if (!Utils.isNullOrEmpty(nextUndoAction) && toolManager.isShowUndoRedo()) {
/*  717 */             undoItem.setTitle(nextUndoAction);
/*  718 */             updateButtonUndo(true);
/*      */           } else {
/*  720 */             updateButtonUndo(false);
/*      */           } 
/*      */           
/*  723 */           String nextRedoAction = undoRedoManager.getNextRedoAction();
/*  724 */           if (!Utils.isNullOrEmpty(nextRedoAction) && toolManager.isShowUndoRedo()) {
/*  725 */             redoItem.setTitle(nextRedoAction);
/*  726 */             redoItem.setVisible(true);
/*      */           } else {
/*  728 */             redoItem.setVisible(false);
/*      */           } 
/*      */         } else {
/*  731 */           updateButtonUndo(false);
/*  732 */           redoItem.setVisible(false);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onOptionsItemSelected(MenuItem item) {
/*  743 */     if (this.mTabHostListeners != null) {
/*  744 */       for (TabHostListener listener : this.mTabHostListeners) {
/*  745 */         if (listener.onToolbarOptionsItemSelected(item)) {
/*  746 */           return true;
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*  751 */     FragmentActivity activity = getActivity();
/*  752 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/*  753 */     if (activity == null || currentFragment == null) {
/*  754 */       return false;
/*      */     }
/*      */     
/*  757 */     PDFViewCtrl pdfViewCtrl = currentFragment.getPDFViewCtrl();
/*  758 */     if (pdfViewCtrl == null) {
/*  759 */       return false;
/*      */     }
/*      */     
/*  762 */     if (!this.mIsSearchMode) {
/*  763 */       resetHideToolbarsTimer();
/*      */     }
/*      */     
/*  766 */     if (currentFragment.getToolManager() != null && currentFragment
/*  767 */       .getToolManager().getTool() != null) {
/*  768 */       ToolManager.ToolMode mode = ToolManager.getDefaultToolMode(currentFragment.getToolManager().getTool().getToolMode());
/*  769 */       if (mode == ToolManager.ToolMode.TEXT_CREATE || mode == ToolManager.ToolMode.CALLOUT_CREATE || mode == ToolManager.ToolMode.ANNOT_EDIT || mode == ToolManager.ToolMode.FORM_FILL)
/*      */       {
/*      */ 
/*      */         
/*  773 */         pdfViewCtrl.closeTool();
/*      */       }
/*      */     } 
/*      */     
/*  777 */     int id = item.getItemId();
/*      */     
/*  779 */     if (id == 16908332) {
/*  780 */       handleNavIconClick();
/*  781 */     } else if (!this.mIsSearchMode) {
/*  782 */       resetHideToolbarsTimer();
/*      */     } 
/*      */     
/*  785 */     if (id == R.id.undo) {
/*  786 */       undo();
/*  787 */     } else if (id == R.id.redo) {
/*  788 */       redo();
/*  789 */     } else if (id == R.id.action_share) {
/*  790 */       if (currentFragment.isDocumentReady()) {
/*  791 */         onShareOptionSelected();
/*  792 */         AnalyticsHandlerAdapter.getInstance().sendEvent(13);
/*      */       } 
/*  794 */     } else if (id == R.id.action_viewmode) {
/*  795 */       if (currentFragment.isDocumentReady()) {
/*  796 */         onViewModeOptionSelected();
/*      */       }
/*  798 */     } else if (id == R.id.action_annotation_toolbar) {
/*  799 */       if (currentFragment.isReflowMode()) {
/*  800 */         CommonToast.showText((Context)activity, R.string.reflow_disable_markup_clicked);
/*  801 */       } else if (currentFragment.isDocumentReady()) {
/*  802 */         showAnnotationToolbar(0, (ToolManager.ToolMode)null);
/*      */       } 
/*  804 */     } else if (id == R.id.action_form_toolbar) {
/*  805 */       if (currentFragment.isReflowMode()) {
/*  806 */         CommonToast.showText((Context)activity, R.string.reflow_disable_markup_clicked);
/*  807 */       } else if (currentFragment.isDocumentReady()) {
/*  808 */         showAnnotationToolbar(2, (ToolManager.ToolMode)null);
/*      */       } 
/*  810 */     } else if (id == R.id.action_fill_and_sign_toolbar) {
/*  811 */       if (currentFragment.isReflowMode()) {
/*  812 */         CommonToast.showText((Context)activity, R.string.reflow_disable_markup_clicked);
/*  813 */       } else if (currentFragment.isDocumentReady()) {
/*  814 */         showAnnotationToolbar(3, (ToolManager.ToolMode)null);
/*      */       } 
/*  816 */     } else if (id == R.id.action_print) {
/*  817 */       if (currentFragment.isDocumentReady()) {
/*  818 */         currentFragment.handlePrintAnnotationSummary();
/*      */       }
/*  820 */     } else if (id == R.id.action_close_tab) {
/*      */       
/*  822 */       if (!PdfViewCtrlSettingsManager.getMultipleTabs((Context)activity)) {
/*  823 */         closeTab(currentFragment.getTabTag(), currentFragment.getTabSource());
/*      */       }
/*  825 */     } else if (id == R.id.action_addpage) {
/*  826 */       if (!checkTabConversionAndAlert(R.string.cant_edit_while_converting_message, false)) {
/*  827 */         addNewPage();
/*  828 */         AnalyticsHandlerAdapter.getInstance().sendEvent(15);
/*      */       } 
/*  830 */     } else if (id == R.id.action_deletepage) {
/*  831 */       if (!checkTabConversionAndAlert(R.string.cant_edit_while_converting_message, false)) {
/*  832 */         requestDeleteCurrentPage();
/*  833 */         AnalyticsHandlerAdapter.getInstance().sendEvent(16);
/*      */       } 
/*  835 */     } else if (id == R.id.action_rotatepage) {
/*  836 */       if (!checkTabConversionAndAlert(R.string.cant_edit_while_converting_message, false)) {
/*  837 */         showRotateDialog();
/*  838 */         AnalyticsHandlerAdapter.getInstance().sendEvent(17);
/*      */       } 
/*  840 */     } else if (id == R.id.action_export_pages) {
/*  841 */       if (currentFragment.isDocumentReady() && 
/*  842 */         !checkTabConversionAndAlert(R.string.cant_edit_while_converting_message, false)) {
/*  843 */         onViewModeSelected("thumbnails", true, 
/*  844 */             Integer.valueOf(pdfViewCtrl.getCurrentPage()));
/*  845 */         AnalyticsHandlerAdapter.getInstance().sendEvent(18);
/*      */       }
/*      */     
/*  848 */     } else if (id == R.id.action_search) {
/*  849 */       if (currentFragment.isReflowMode()) {
/*  850 */         int messageID = R.string.reflow_disable_search_clicked;
/*  851 */         CommonToast.showText((Context)activity, messageID);
/*  852 */         return false;
/*      */       } 
/*      */       
/*  855 */       if (checkTabConversionAndAlert(R.string.cant_search_while_converting_message, true)) {
/*  856 */         return false;
/*      */       }
/*      */       
/*  859 */       if (this.mSearchToolbar == null || this.mToolbar == null) {
/*  860 */         return false;
/*      */       }
/*  862 */       if (currentFragment.isDocumentReady()) {
/*  863 */         startSearchMode();
/*  864 */         AnalyticsHandlerAdapter.getInstance().sendEvent(11);
/*      */       } 
/*  866 */     } else if (id == R.id.menu_export_copy) {
/*  867 */       if (currentFragment.isDocumentReady()) {
/*  868 */         onSaveAsOptionSelected();
/*      */       }
/*  870 */     } else if (id == R.id.menu_export_flattened_copy) {
/*  871 */       if (currentFragment.isDocumentReady()) {
/*  872 */         onFlattenOptionSelected();
/*      */       }
/*  874 */     } else if (id == R.id.menu_export_optimized_copy) {
/*  875 */       if (currentFragment.isDocumentReady()) {
/*  876 */         onSaveOptimizedCopySelected();
/*      */       }
/*  878 */     } else if (id == R.id.menu_export_cropped_copy) {
/*  879 */       if (currentFragment.isDocumentReady()) {
/*  880 */         onSaveCroppedCopySelected();
/*      */       }
/*  882 */     } else if (id == R.id.menu_export_password_copy) {
/*  883 */       if (currentFragment.isDocumentReady()) {
/*  884 */         onSavePasswordCopySelected();
/*      */       }
/*  886 */     } else if (id == R.id.action_file_attachment) {
/*  887 */       if (currentFragment.isDocumentReady()) {
/*  888 */         currentFragment.handleViewFileAttachments();
/*      */       }
/*  890 */     } else if (id == R.id.action_pdf_layers) {
/*  891 */       if (currentFragment.isDocumentReady()) {
/*  892 */         PdfLayerDialog pdfLayerDialog = new PdfLayerDialog((Context)activity, pdfViewCtrl);
/*      */         
/*  894 */         pdfLayerDialog.show();
/*      */       } 
/*  896 */     } else if (id == R.id.action_reflow_mode) {
/*  897 */       if (currentFragment.isDocumentReady()) {
/*  898 */         onToggleReflow();
/*      */       }
/*  900 */     } else if (id == R.id.action_edit_menu) {
/*  901 */       onEditToolbarMenu();
/*      */     } else {
/*  903 */       return false;
/*      */     } 
/*      */     
/*  906 */     return true;
/*      */   }
/*      */   
/*      */   protected void handleNavIconClick() {
/*  910 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/*  911 */     if (currentFragment != null) {
/*  912 */       currentFragment.closeKeyboard();
/*      */     }
/*  914 */     stopHideToolbarsTimer();
/*  915 */     if (this.mTabHostListeners != null) {
/*  916 */       for (TabHostListener listener : this.mTabHostListeners) {
/*  917 */         listener.onNavButtonPressed();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
/*  927 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/*  928 */     if (currentFragment == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  933 */     if (currentFragment.isOpenFileFailed()) {
/*  934 */       handleOpenFileFailed(currentFragment.getTabErrorCode());
/*  935 */       if (this.mTabHostListeners != null) {
/*  936 */         for (TabHostListener listener : this.mTabHostListeners) {
/*  937 */           listener.onOpenDocError();
/*      */         }
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @TargetApi(19)
/*      */   public void onSystemUiVisibilityChange(int visibility) {
/*  949 */     FragmentActivity activity = getActivity();
/*  950 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/*  951 */     if (activity == null || currentFragment == null) {
/*      */       return;
/*      */     }
/*      */     
/*  955 */     int diff = this.mLastSystemUIVisibility ^ visibility;
/*  956 */     if ((diff & 0x2) == 2)
/*      */     {
/*  958 */       if (currentFragment.isAnnotationMode()) {
/*  959 */         if ((visibility & 0x2) == 2) {
/*      */           
/*  961 */           stopHideNavigationBarTimer();
/*      */         } else {
/*      */           
/*  964 */           resetHideNavigationBarTimer();
/*      */         } 
/*      */       }
/*      */     }
/*      */     
/*  969 */     this.mLastSystemUIVisibility = visibility;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @CallSuper
/*      */   public void createTabs(Bundle args) {
/*  979 */     FragmentActivity activity = getActivity();
/*  980 */     if (activity == null) {
/*      */       return;
/*      */     }
/*      */     
/*  984 */     String startupTitle = null;
/*  985 */     String startupFileExtension = null;
/*  986 */     int startupItemSource = -1;
/*      */     
/*  988 */     if (args != null) {
/*  989 */       this.mStartupTabTag = this.mIsRestarted ? null : args.getString("bundle_tab_tag");
/*  990 */       startupTitle = args.getString("bundle_tab_title");
/*  991 */       startupFileExtension = args.getString("bundle_tab_file_extension");
/*  992 */       startupItemSource = args.getInt("bundle_tab_item_source");
/*      */ 
/*      */ 
/*      */       
/*  996 */       if (null != this.mStartupTabTag)
/*      */       {
/*  998 */         if (Utils.isNullOrEmpty(this.mStartupTabTag) || 
/*  999 */           Utils.isNullOrEmpty(startupTitle) || (startupItemSource == 2 && 
/* 1000 */           !Utils.isNotPdf(this.mStartupTabTag) && !(new File(this.mStartupTabTag)).exists())) {
/* 1001 */           CommonToast.showText((Context)activity, getString(R.string.error_opening_doc_message), 0);
/*      */           
/* 1003 */           if (this.mTabHostListeners != null) {
/* 1004 */             for (TabHostListener listener : this.mTabHostListeners) {
/* 1005 */               listener.onOpenDocError();
/*      */             }
/*      */           }
/*      */ 
/*      */           
/*      */           return;
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/* 1015 */     if (!PdfViewCtrlSettingsManager.getMultipleTabs((Context)activity)) {
/* 1016 */       this.mMultiTabModeEnabled = false;
/*      */     }
/* 1018 */     setTabLayoutVisible(this.mMultiTabModeEnabled);
/*      */ 
/*      */     
/* 1021 */     if (!this.mMultiTabModeEnabled) {
/* 1022 */       if (this.mStartupTabTag != null) {
/* 1023 */         PdfViewCtrlTabsManager.getInstance().cleanup();
/* 1024 */         PdfViewCtrlTabsManager.getInstance().clearAllPdfViewCtrlTabInfo((Context)activity);
/*      */       } else {
/*      */         
/* 1027 */         String latestViewedTabTag = PdfViewCtrlTabsManager.getInstance().getLatestViewedTabTag((Context)activity);
/* 1028 */         if (latestViewedTabTag != null) {
/* 1029 */           ArrayList<String> documents = new ArrayList<>(PdfViewCtrlTabsManager.getInstance().getDocuments((Context)activity));
/* 1030 */           for (String document : documents) {
/* 1031 */             if (!latestViewedTabTag.equals(document)) {
/* 1032 */               PdfViewCtrlTabsManager.getInstance().removeDocument((Context)activity, document);
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 1040 */     PdfViewCtrlTabsManager.getInstance().addDocument((Context)activity, this.mStartupTabTag);
/*      */ 
/*      */     
/* 1043 */     if (this.mMultiTabModeEnabled) {
/* 1044 */       removeExtraTabs();
/*      */     }
/*      */ 
/*      */     
/* 1048 */     for (String tag : PdfViewCtrlTabsManager.getInstance().getDocuments((Context)activity)) {
/* 1049 */       if (this.mTabLayout.getTabByTag(tag) != null) {
/*      */         continue;
/*      */       }
/*      */       
/* 1053 */       if (!this.mMultiTabModeEnabled && this.mStartupTabTag != null && 
/* 1054 */         !tag.equals(this.mStartupTabTag)) {
/*      */         continue;
/*      */       }
/*      */       
/* 1058 */       PdfViewCtrlTabInfo info = PdfViewCtrlTabsManager.getInstance().getPdfFViewCtrlTabInfo((Context)activity, tag);
/* 1059 */       int itemSource = -1;
/* 1060 */       String title = "";
/* 1061 */       String fileExtension = null;
/* 1062 */       String password = "";
/*      */       
/* 1064 */       if (info != null) {
/* 1065 */         itemSource = info.tabSource;
/* 1066 */         title = info.tabTitle;
/* 1067 */         fileExtension = info.fileExtension;
/* 1068 */         password = Utils.decryptIt((Context)activity, info.password);
/*      */       } 
/*      */       
/* 1071 */       if (args != null && tag.equals(this.mStartupTabTag)) {
/* 1072 */         itemSource = startupItemSource;
/* 1073 */         password = args.getString("bundle_tab_password");
/* 1074 */         title = startupTitle;
/*      */         
/*      */         try {
/* 1077 */           int index = FilenameUtils.indexOfExtension(title);
/* 1078 */           if (index != -1 && title != null) {
/* 1079 */             title = title.substring(0, index);
/* 1080 */             args.putString("bundle_tab_title", title);
/*      */           } 
/* 1082 */         } catch (Exception e) {
/* 1083 */           AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */         } 
/* 1085 */         fileExtension = startupFileExtension;
/*      */       } 
/* 1087 */       if (canAddNewDocumentToTabList(itemSource) && !Utils.isNullOrEmpty(title))
/*      */       {
/* 1089 */         addTab(tag.equals(this.mStartupTabTag) ? args : null, tag, title, fileExtension, password, itemSource);
/*      */       }
/*      */     } 
/*      */     
/* 1093 */     if (this.mStartupTabTag == null) {
/* 1094 */       this.mStartupTabTag = PdfViewCtrlTabsManager.getInstance().getLatestViewedTabTag((Context)activity);
/*      */     }
/* 1096 */     setCurrentTabByTag(this.mStartupTabTag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onSearchResultClicked(TextSearchResult result) {
/* 1104 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 1105 */     FragmentActivity fragmentActivity = getActivity();
/* 1106 */     if (fragmentActivity == null || currentFragment == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1110 */     PDFViewCtrl pdfViewCtrl = currentFragment.getPDFViewCtrl();
/* 1111 */     if (pdfViewCtrl != null && pdfViewCtrl.getCurrentPage() != result.getPageNum()) {
/* 1112 */       currentFragment.resetHidePageNumberIndicatorTimer();
/*      */     }
/*      */     
/* 1115 */     currentFragment.highlightFullTextSearchResult(result);
/* 1116 */     currentFragment.setCurrentPageHelper(result.getPageNum(), false);
/*      */     
/* 1118 */     if (!Utils.isTablet((Context)fragmentActivity)) {
/* 1119 */       hideSearchResults();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onFullTextSearchStart() {
/* 1128 */     if (this.mSearchToolbar != null) {
/* 1129 */       this.mSearchToolbar.setSearchProgressBarVisible(true);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onSearchResultFound(TextSearchResult result) {
/* 1138 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 1139 */     if (this.mSearchToolbar != null) {
/* 1140 */       this.mSearchToolbar.setSearchProgressBarVisible(false);
/*      */     }
/* 1142 */     if (result != null && currentFragment != null) {
/* 1143 */       currentFragment.highlightFullTextSearchResult(result);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onConfigurationChanged(Configuration newConfig) {
/* 1152 */     super.onConfigurationChanged(newConfig);
/*      */     
/* 1154 */     FragmentActivity fragmentActivity = getActivity();
/* 1155 */     if (fragmentActivity == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1159 */     if (isHidden()) {
/*      */       return;
/*      */     }
/*      */     
/* 1163 */     if (PdfViewCtrlSettingsManager.getFullScreenMode((Context)fragmentActivity)) {
/* 1164 */       setToolbarsVisible(false);
/* 1165 */       hideSystemUI();
/*      */     } 
/*      */     
/* 1168 */     if (this.mUndoRedoPopupWindow != null && this.mUndoRedoPopupWindow.isShowing()) {
/* 1169 */       this.mUndoRedoPopupWindow.dismiss();
/*      */     }
/*      */     
/* 1172 */     if (this.mSearchResultsView != null) {
/* 1173 */       PaneBehavior paneBehavior = PaneBehavior.from((View)this.mSearchResultsView);
/* 1174 */       if (paneBehavior != null) {
/* 1175 */         paneBehavior.onOrientationChanged((View)this.mSearchResultsView, newConfig.orientation);
/*      */       }
/*      */     } 
/*      */     
/* 1179 */     updateTabLayout();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onUserBookmarkClick(int pageNum) {
/* 1187 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 1188 */     if (currentFragment != null) {
/* 1189 */       if (!currentFragment.isNavigationListShowing() && this.mBookmarksDialog != null) {
/* 1190 */         this.mBookmarksDialog.dismiss();
/*      */       }
/* 1192 */       currentFragment.setCurrentPageHelper(pageNum, true);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onOutlineClicked(Bookmark parent, Bookmark bookmark) {
/* 1202 */     this.mCurrentBookmark = parent;
/*      */     
/* 1204 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 1205 */     if (currentFragment != null) {
/* 1206 */       if (!currentFragment.isNavigationListShowing() && this.mBookmarksDialog != null) {
/* 1207 */         this.mBookmarksDialog.dismiss();
/*      */       }
/* 1209 */       PDFViewCtrl pdfViewCtrl = currentFragment.getPDFViewCtrl();
/* 1210 */       if (pdfViewCtrl != null) {
/* 1211 */         currentFragment.setCurrentPageHelper(pdfViewCtrl.getCurrentPage(), false);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onAnnotationClicked(Annot annotation, int pageNum) {
/* 1221 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 1222 */     if (currentFragment != null) {
/* 1223 */       if (!currentFragment.isNavigationListShowing() && this.mBookmarksDialog != null) {
/* 1224 */         this.mBookmarksDialog.dismiss();
/*      */       }
/* 1226 */       if (currentFragment.getToolManager() != null) {
/* 1227 */         currentFragment.getToolManager().deselectAll();
/* 1228 */         currentFragment.getToolManager().selectAnnot(annotation, pageNum);
/*      */       } 
/* 1230 */       currentFragment.setCurrentPageHelper(pageNum, false);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onExportAnnotations(PDFDoc pdfDoc) {
/* 1239 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 1240 */     if (currentFragment != null) {
/* 1241 */       if (!currentFragment.isNavigationListShowing() && this.mBookmarksDialog != null) {
/* 1242 */         this.mBookmarksDialog.dismiss();
/*      */       }
/* 1244 */       currentFragment.onExportAnnotations(pdfDoc);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onUserCropMethodSelected(int cropMode) {
/* 1253 */     FragmentActivity activity = getActivity();
/* 1254 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 1255 */     if (activity == null || currentFragment == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1259 */     currentFragment.save(false, true, false);
/*      */     
/* 1261 */     PDFViewCtrl pdfViewCtrl = currentFragment.getPDFViewCtrl();
/* 1262 */     if (pdfViewCtrl == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1266 */     if (cropMode == 0) {
/* 1267 */       if (this.mAutoCropTask != null && this.mAutoCropTask.getStatus() == AsyncTask.Status.RUNNING) {
/* 1268 */         this.mAutoCropTask.cancel(true);
/*      */       }
/* 1270 */       this.mAutoCropTask = new UserCropUtilities.AutoCropInBackgroundTask((Context)activity, pdfViewCtrl, this);
/* 1271 */       this.mAutoCropTask.execute((Object[])new Void[0]);
/*      */     } else {
/* 1273 */       UserCropDialogFragment userCropDialog = UserCropDialogFragment.newInstance((cropMode == 2), false);
/*      */       
/* 1275 */       userCropDialog.setOnUserCropDialogDismissListener(this);
/* 1276 */       userCropDialog.setPdfViewCtrl(pdfViewCtrl);
/*      */       
/* 1278 */       userCropDialog.setStyle(1, R.style.CustomAppTheme);
/* 1279 */       FragmentManager fragmentManager = getFragmentManager();
/* 1280 */       if (fragmentManager != null) {
/* 1281 */         userCropDialog.show(fragmentManager, "usercrop_dialog");
/*      */       }
/*      */     } 
/*      */     
/* 1285 */     currentFragment.clearPageBackAndForwardStacks();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onUserCropSelectionDialogFragmentDismiss() {
/* 1293 */     resetHideToolbarsTimer();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onUserCropDialogDismiss(int pageNumberAtDismiss) {
/* 1301 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 1302 */     if (currentFragment == null) {
/*      */       return;
/*      */     }
/* 1305 */     currentFragment.setCurrentPageHelper(pageNumberAtDismiss, true);
/* 1306 */     currentFragment.userCropDialogDismiss();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onAutoCropTaskDone() {
/* 1314 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 1315 */     if (currentFragment == null) {
/*      */       return;
/*      */     }
/* 1318 */     currentFragment.userCropDialogDismiss();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onThumbnailsViewDialogDismiss(int pageNum, boolean docPagesModified) {
/* 1326 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 1327 */     if (currentFragment == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1331 */     currentFragment.onThumbnailsViewDialogDismiss(pageNum, docPagesModified);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onThumbnailsEditAttemptWhileReadOnly() {
/* 1339 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 1340 */     if (currentFragment == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1344 */     currentFragment.showReadOnlyAlert(this.mThumbFragment);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onExportThumbnails(SparseBooleanArray pageNums) {
/* 1352 */     FragmentActivity activity = getActivity();
/* 1353 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 1354 */     if (activity == null || currentFragment == null) {
/*      */       return;
/*      */     }
/* 1357 */     if (currentFragment.mTabSource == 2) {
/* 1358 */       handleThumbnailsExport(currentFragment.mCurrentFile.getParentFile(), pageNums);
/* 1359 */     } else if (currentFragment.mTabSource == 6) {
/* 1360 */       ExternalFileInfo fileInfo = Utils.buildExternalFile((Context)activity, currentFragment.mCurrentUriFile);
/* 1361 */       if (fileInfo != null) {
/* 1362 */         handleThumbnailsExport(fileInfo.getParent(), pageNums);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onTabDocumentLoaded(String tag) {
/* 1372 */     setToolbarsVisible(true, false);
/*      */ 
/*      */     
/* 1375 */     updatePrintDocumentMode();
/* 1376 */     updatePrintAnnotationsMode();
/* 1377 */     updatePrintSummaryMode();
/*      */ 
/*      */     
/* 1380 */     updateButtonViewModeIcon();
/*      */ 
/*      */     
/* 1383 */     updateUndoButtonVisibility(true);
/*      */     
/* 1385 */     updateShareButtonVisibility(true);
/*      */ 
/*      */     
/* 1388 */     updateIconsInReflowMode();
/*      */     
/* 1390 */     if (this.mStartupTabTag != null && this.mStartupTabTag.equals(tag)) {
/* 1391 */       setCurrentTabByTag(this.mStartupTabTag);
/*      */     }
/*      */     
/* 1394 */     if (this.mAutoCropTaskPaused && this.mAutoCropTaskTabTag != null && this.mAutoCropTaskTabTag.equals(getCurrentTabTag())) {
/* 1395 */       this.mAutoCropTaskPaused = false;
/* 1396 */       onUserCropMethodSelected(0);
/*      */     } 
/*      */     
/* 1399 */     setupRedaction();
/*      */     
/* 1401 */     if (this.mTabHostListeners != null) {
/* 1402 */       for (TabHostListener listener : this.mTabHostListeners) {
/* 1403 */         listener.onTabDocumentLoaded(tag);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onTabError(int errorCode, String info) {
/* 1413 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 1414 */     if (currentFragment == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1418 */     if (currentFragment.isOpenFileFailed()) {
/* 1419 */       AnalyticsHandlerAdapter.getInstance().setString(AnalyticsHandlerAdapter.CustomKeys.TAB_ERROR, 
/* 1420 */           String.format(Locale.US, "Error code %d: %s", new Object[] { Integer.valueOf(errorCode), info }));
/* 1421 */       handleOpenFileFailed(errorCode, info);
/* 1422 */       if (this.mTabHostListeners != null) {
/* 1423 */         for (TabHostListener listener : this.mTabHostListeners) {
/* 1424 */           listener.onOpenDocError();
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
/*      */   public void onOpenAddNewTab(Bundle args) {
/* 1436 */     if (args != null) {
/* 1437 */       String tag = args.getString("bundle_tab_tag");
/* 1438 */       String title = args.getString("bundle_tab_title");
/* 1439 */       String password = args.getString("bundle_tab_password");
/* 1440 */       int itemSource = args.getInt("bundle_tab_item_source");
/* 1441 */       onOpenAddNewTab(itemSource, tag, title, password);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onOpenAddNewTab(int itemSource, String tag, String title, String password) {
/* 1450 */     FragmentActivity fragmentActivity = getActivity();
/* 1451 */     if (fragmentActivity == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 1456 */     if (Utils.isNullOrEmpty(tag) || 
/* 1457 */       Utils.isNullOrEmpty(title) || (itemSource == 2 && 
/* 1458 */       !Utils.isNotPdf(tag) && !(new File(tag)).exists())) {
/* 1459 */       CommonToast.showText((Context)fragmentActivity, R.string.error_opening_doc_message, 0);
/*      */       
/* 1461 */       if (this.mTabHostListeners != null) {
/* 1462 */         for (TabHostListener listener : this.mTabHostListeners) {
/* 1463 */           listener.onOpenDocError();
/*      */         }
/*      */       }
/*      */       
/*      */       return;
/*      */     } 
/* 1469 */     this.mFileSystemChanged.set(true);
/*      */     
/* 1471 */     String fileExtension = FilenameUtils.getExtension(title);
/* 1472 */     String name = FilenameUtils.removeExtension(title);
/* 1473 */     TabLayout.Tab newTab = addTab((Bundle)null, tag, name, fileExtension, password, itemSource);
/* 1474 */     newTab.select();
/*      */ 
/*      */ 
/*      */     
/* 1478 */     PdfViewCtrlTabsManager.getInstance().addDocument((Context)fragmentActivity, tag);
/* 1479 */     removeExtraTabs();
/*      */   }
/*      */ 
/*      */   
/*      */   public void onShowTabInfo(String tag, String title, String fileExtension, int itemSource, int duration) {
/* 1484 */     handleShowTabInfo(tag, title, fileExtension, itemSource, duration);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onTabIdentityChanged(String oldTabTag, String newTabTag, String newTabTitle, String newFileExtension, int newTabSource) {
/* 1493 */     this.mFileSystemChanged.set(true);
/*      */     
/* 1495 */     if (this.mTabLayout != null) {
/* 1496 */       TabLayout.Tab tab = this.mTabLayout.getTabByTag(oldTabTag);
/* 1497 */       if (tab != null) {
/* 1498 */         this.mTabLayout.replaceTag(tab, newTabTag);
/* 1499 */         setTabView(tab.getCustomView(), newTabTag, newTabTitle, newFileExtension, newTabSource);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onPageThumbnailOptionSelected(boolean thumbnailEditMode, Integer checkedItem) {
/* 1509 */     FragmentActivity activity = getActivity();
/* 1510 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 1511 */     if (activity == null || currentFragment == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1515 */     PDFViewCtrl pdfViewCtrl = currentFragment.getPDFViewCtrl();
/* 1516 */     if (pdfViewCtrl == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1522 */     if (checkTabConversionAndAlert(R.string.cant_edit_while_converting_message, true)) {
/*      */       return;
/*      */     }
/*      */     
/* 1526 */     currentFragment.save(false, true, false);
/* 1527 */     pdfViewCtrl.pause();
/*      */     
/* 1529 */     boolean readonly = currentFragment.isTabReadOnly();
/* 1530 */     if (!readonly) {
/* 1531 */       if (this.mViewerConfig != null && !this.mViewerConfig.isThumbnailViewEditingEnabled())
/*      */       {
/* 1533 */         readonly = true;
/*      */       }
/* 1535 */       if (!pageThumbnailEditingEnabled())
/*      */       {
/* 1537 */         readonly = true;
/*      */       }
/*      */     } 
/* 1540 */     this.mThumbFragment = ThumbnailsViewFragment.newInstance(readonly, thumbnailEditMode);
/* 1541 */     this.mThumbFragment.setPdfViewCtrl(pdfViewCtrl);
/* 1542 */     this.mThumbFragment.setOnExportThumbnailsListener(this);
/* 1543 */     this.mThumbFragment.setOnThumbnailsViewDialogDismissListener(this);
/* 1544 */     this.mThumbFragment.setOnThumbnailsEditAttemptWhileReadOnlyListener(this);
/* 1545 */     this.mThumbFragment.setStyle(1, R.style.CustomAppTheme);
/* 1546 */     this.mThumbFragment.setTitle(getString(R.string.pref_viewmode_thumbnails_title));
/* 1547 */     if (checkedItem != null) {
/* 1548 */       this.mThumbFragment.setItemChecked(checkedItem.intValue() - 1);
/*      */     }
/*      */     
/* 1551 */     FragmentManager fragmentManager = getFragmentManager();
/* 1552 */     if (fragmentManager != null) {
/* 1553 */       this.mThumbFragment.show(fragmentManager, "thumbnails_fragment");
/*      */     }
/*      */   }
/*      */   
/*      */   protected boolean pageThumbnailEditingEnabled() {
/* 1558 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean onBackPressed() {
/* 1563 */     return handleBackPressed();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onTabPaused(FileInfo fileInfo, boolean isDocModifiedAfterOpening) {
/* 1574 */     if (this.mTabHostListeners != null) {
/* 1575 */       for (TabHostListener listener : this.mTabHostListeners) {
/* 1576 */         listener.onTabPaused(fileInfo, isDocModifiedAfterOpening);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void onTabJumpToSdCardFolder() {
/* 1583 */     if (this.mTabHostListeners != null) {
/* 1584 */       for (TabHostListener listener : this.mTabHostListeners) {
/* 1585 */         listener.onJumpToSdCardFolder();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void onUpdateOptionsMenu() {
/* 1592 */     if (this.mToolbar != null) {
/* 1593 */       onPrepareOptionsMenu(this.mToolbar.getMenu());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @TargetApi(19)
/*      */   public void onInkEditSelected(Annot inkAnnot, int pageNum) {
/* 1603 */     showAnnotationToolbar(1, inkAnnot, pageNum, ToolManager.ToolMode.INK_CREATE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @TargetApi(19)
/*      */   public void onOpenAnnotationToolbar(ToolManager.ToolMode mode) {
/* 1613 */     showAnnotationToolbar(0, mode);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onOpenEditToolbar(ToolManager.ToolMode mode) {
/* 1621 */     showAnnotationToolbar(1, mode);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onToggleReflow() {
/* 1629 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 1630 */     if (currentFragment == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1634 */     currentFragment.toggleReflow();
/* 1635 */     updateIconsInReflowMode();
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   private ArrayList<MenuEditorItem> getToolbarMenuItems() {
/* 1640 */     if (null == this.mToolbar) {
/* 1641 */       return null;
/*      */     }
/* 1643 */     int size = this.mToolbar.getMenu().size();
/* 1644 */     SparseArray<ArrayList<MenuEditorItem>> menuItemMap = new SparseArray(size);
/* 1645 */     menuItemMap.put(0, new ArrayList());
/* 1646 */     menuItemMap.put(1, new ArrayList());
/* 1647 */     for (int i = 0; i < size; i++) {
/* 1648 */       MenuItem menuItem = this.mToolbar.getMenu().getItem(i);
/* 1649 */       if (menuItem.isVisible() && menuItem.getIcon() != null && menuItem.getIcon().getConstantState() != null)
/*      */       {
/* 1651 */         if (menuItem instanceof MenuItemImpl) {
/*      */ 
/*      */ 
/*      */           
/* 1655 */           MenuEditorItemContent itemContent = new MenuEditorItemContent(menuItem.getItemId(), menuItem.getTitle().toString(), DrawableCompat.wrap(menuItem.getIcon().getConstantState().newDrawable()).mutate());
/*      */           
/* 1657 */           MenuItemImpl menuItemImpl = (MenuItemImpl)menuItem;
/* 1658 */           if (menuItemImpl.isActionButton()) {
/* 1659 */             ((ArrayList<MenuEditorItemContent>)menuItemMap.get(0)).add(itemContent);
/*      */           } else {
/* 1661 */             ((ArrayList<MenuEditorItemContent>)menuItemMap.get(1)).add(itemContent);
/*      */           } 
/*      */         } 
/*      */       }
/*      */     } 
/* 1666 */     ArrayList<MenuEditorItem> items = new ArrayList<>();
/* 1667 */     MenuEditorItemHeader header1 = new MenuEditorItemHeader(0, R.string.menu_editor_if_room_section_header, R.string.menu_editor_section_desc);
/*      */ 
/*      */ 
/*      */     
/* 1671 */     header1.setDraggingTitleId(R.string.menu_editor_dragging_if_room_section_header);
/* 1672 */     items.add(header1);
/* 1673 */     items.addAll((Collection<? extends MenuEditorItem>)menuItemMap.get(0));
/* 1674 */     MenuEditorItemHeader header2 = new MenuEditorItemHeader(1, R.string.menu_editor_never_section_header, 0);
/*      */ 
/*      */ 
/*      */     
/* 1678 */     header2.setDraggingTitleId(R.string.menu_editor_dragging_never_section_header);
/* 1679 */     items.add(header2);
/* 1680 */     items.addAll((Collection<? extends MenuEditorItem>)menuItemMap.get(1));
/*      */     
/* 1682 */     return items;
/*      */   }
/*      */   
/*      */   private void updateToolbarMenuOrder(ArrayList<MenuEditorItem> menuEditorItems) {
/* 1686 */     int group = -1;
/* 1687 */     for (int index = 0; index < menuEditorItems.size(); index++) {
/* 1688 */       MenuEditorItem newItem = menuEditorItems.get(index);
/* 1689 */       if (newItem.isHeader()) {
/* 1690 */         MenuEditorItemHeader header = (MenuEditorItemHeader)newItem;
/* 1691 */         group = header.getGroup();
/*      */       } else {
/* 1693 */         MenuEditorItemContent itemContent = (MenuEditorItemContent)newItem;
/* 1694 */         MenuItem oldMenu = this.mToolbar.getMenu().findItem(itemContent.getId());
/* 1695 */         if (oldMenu != null && oldMenu.getOrder() != index) {
/* 1696 */           this.mToolbar.getMenu().removeItem(oldMenu.getItemId());
/* 1697 */           MenuItem newMenu = this.mToolbar.getMenu().add(oldMenu.getGroupId(), oldMenu
/* 1698 */               .getItemId(), index, oldMenu.getTitle());
/* 1699 */           newMenu.setIcon(oldMenu.getIcon());
/* 1700 */           newMenu.setCheckable(oldMenu.isCheckable());
/* 1701 */           newMenu.setChecked(oldMenu.isChecked());
/* 1702 */           newMenu.setEnabled(oldMenu.isEnabled());
/* 1703 */           newMenu.setVisible(oldMenu.isVisible());
/* 1704 */           if (group == 0) {
/* 1705 */             if (index <= 5) {
/* 1706 */               newMenu.setShowAsAction(2);
/*      */             } else {
/* 1708 */               newMenu.setShowAsAction(1);
/*      */             } 
/*      */           } else {
/* 1711 */             newMenu.setShowAsAction(0);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/* 1716 */     initOptionsMenu(this.mToolbar.getMenu());
/*      */   }
/*      */   
/*      */   public void onEditToolbarMenu() {
/* 1720 */     ArrayList<MenuEditorItem> items = getToolbarMenuItems();
/* 1721 */     if (null == items) {
/*      */       return;
/*      */     }
/*      */     
/* 1725 */     stopHideToolbarsTimer();
/*      */     
/* 1727 */     final MenuEditorViewModel viewModel = (MenuEditorViewModel)ViewModelProviders.of(this).get(MenuEditorViewModel.class);
/* 1728 */     viewModel.setItems(items);
/* 1729 */     viewModel.getItemsLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayList<MenuEditorItem>>()
/*      */         {
/*      */           public void onChanged(ArrayList<MenuEditorItem> menuEditorItems) {
/* 1732 */             PdfViewCtrlTabHostFragment.this.updateToolbarMenuOrder(menuEditorItems);
/*      */           }
/*      */         });
/*      */     
/* 1736 */     this.mDisposables.add(viewModel.getObservable()
/* 1737 */         .subscribe(new Consumer<MenuEditorEvent>()
/*      */           {
/*      */             public void accept(MenuEditorEvent menuEditorEvent) throws Exception {
/* 1740 */               if (menuEditorEvent.getEventType() == MenuEditorEvent.Type.RESET) {
/* 1741 */                 FragmentActivity activity = PdfViewCtrlTabHostFragment.this.getActivity();
/* 1742 */                 if (activity == null || PdfViewCtrlTabHostFragment.this.mToolbar == null) {
/*      */                   return;
/*      */                 }
/* 1745 */                 PdfViewCtrlTabHostFragment.this.mToolbar.getMenu().clear();
/* 1746 */                 for (int res : PdfViewCtrlTabHostFragment.this.mToolbarMenuResArray) {
/* 1747 */                   PdfViewCtrlTabHostFragment.this.mToolbar.inflateMenu(res);
/*      */                 }
/* 1749 */                 PdfViewCtrlTabHostFragment.this.initOptionsMenu(PdfViewCtrlTabHostFragment.this.mToolbar.getMenu());
/* 1750 */                 PdfViewCtrlTabHostFragment.this.setOptionsMenuVisible(true);
/* 1751 */                 PdfViewCtrlTabHostFragment.this.onPrepareOptionsMenu(PdfViewCtrlTabHostFragment.this.mToolbar.getMenu());
/* 1752 */                 viewModel.setItems(PdfViewCtrlTabHostFragment.this.getToolbarMenuItems());
/*      */               } 
/*      */             }
/*      */           }));
/*      */ 
/*      */     
/* 1758 */     MenuEditorDialogFragment editorFragment = MenuEditorDialogFragment.newInstance();
/* 1759 */     editorFragment.setStyle(0, R.style.CustomAppTheme);
/* 1760 */     editorFragment.show(getChildFragmentManager(), MenuEditorDialogFragment.TAG);
/*      */     
/* 1762 */     editorFragment.setMenuEditorDialogFragmentListener(new MenuEditorDialogFragment.MenuEditorDialogFragmentListener()
/*      */         {
/*      */           public void onMenuEditorDialogDismiss() {
/* 1765 */             PdfViewCtrlTabHostFragment.this.resetHideToolbarsTimer();
/*      */ 
/*      */             
/* 1768 */             ArrayList<MenuEditorItem> newMenuItems = (ArrayList<MenuEditorItem>)viewModel.getItemsLiveData().getValue();
/* 1769 */             if (newMenuItems != null) {
/*      */               try {
/* 1771 */                 FragmentActivity fragmentActivity = PdfViewCtrlTabHostFragment.this.getActivity();
/* 1772 */                 if (fragmentActivity != null) {
/* 1773 */                   String menuJson = ViewerUtils.getMenuEditorItemsJSON(newMenuItems);
/* 1774 */                   PdfViewCtrlSettingsManager.setSavedHomeToolbarMenu((Context)fragmentActivity, menuJson);
/*      */                 } 
/* 1776 */               } catch (Exception ex) {
/* 1777 */                 AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*      */               } 
/*      */             }
/*      */           }
/*      */         });
/*      */   }
/*      */   
/*      */   public void restoreSavedMenu() {
/* 1785 */     FragmentActivity fragmentActivity = getActivity();
/* 1786 */     if (fragmentActivity != null) {
/* 1787 */       String savedToolbarMenu = PdfViewCtrlSettingsManager.getSavedHomeToolbarMenu((Context)fragmentActivity);
/* 1788 */       if (savedToolbarMenu != null) {
/*      */         try {
/* 1790 */           ArrayList<MenuEditorItem> menuEditorItems = ViewerUtils.getMenuEditorItemsArray(savedToolbarMenu);
/* 1791 */           updateToolbarMenuOrder(menuEditorItems);
/* 1792 */         } catch (Exception ex) {
/* 1793 */           AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SearchResultsView.SearchResultStatus onFullTextSearchFindText(boolean searchUp) {
/* 1804 */     SearchResultsView.SearchResultStatus status = SearchResultsView.SearchResultStatus.NOT_HANDLED;
/* 1805 */     if (this.mSearchResultsView != null && this.mSearchResultsView.isActive()) {
/* 1806 */       status = this.mSearchResultsView.getResult(searchUp);
/*      */     }
/* 1808 */     return status;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onTabThumbSliderStopTrackingTouch() {
/* 1816 */     resetHideToolbarsTimer();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onTabSingleTapConfirmed() {
/* 1824 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 1825 */     if (currentFragment == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1829 */     if (!currentFragment.isAnnotationMode() && !this.mIsSearchMode) {
/* 1830 */       if (currentFragment.isThumbSliderVisible()) {
/* 1831 */         hideUI();
/*      */       } else {
/* 1833 */         showUI();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onSearchProgressShow() {
/* 1843 */     if (this.mSearchToolbar != null) {
/* 1844 */       this.mSearchToolbar.setSearchProgressBarVisible(true);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onSearchProgressHide() {
/* 1853 */     if (this.mSearchToolbar != null) {
/* 1854 */       this.mSearchToolbar.setSearchProgressBarVisible(false);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getToolbarHeight() {
/* 1863 */     if (this.mToolbar != null && this.mToolbar.isShown()) {
/* 1864 */       if (isInFullScreenMode()) {
/* 1865 */         return this.mAppBarLayout.getHeight();
/*      */       }
/* 1867 */       return this.mToolbar.getHeight() + this.mTabLayout.getHeight();
/*      */     } 
/*      */     
/* 1870 */     return -1;
/*      */   }
/*      */   
/*      */   public Toolbar getToolbar() {
/* 1874 */     return this.mToolbar;
/*      */   }
/*      */   
/*      */   public boolean isInFullScreenMode() {
/* 1878 */     FragmentActivity fragmentActivity = getActivity();
/* 1879 */     if (fragmentActivity != null) {
/* 1880 */       return (Utils.isKitKat() && PdfViewCtrlSettingsManager.getFullScreenMode((Context)fragmentActivity));
/*      */     }
/* 1882 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDownloadedSuccessful() {
/* 1890 */     FragmentActivity fragmentActivity = getActivity();
/* 1891 */     if (fragmentActivity == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1895 */     PdfViewCtrlTabsManager.getInstance().addDocument((Context)fragmentActivity, this.mStartupTabTag);
/* 1896 */     removeExtraTabs();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addHostListener(TabHostListener listener) {
/* 1905 */     if (this.mTabHostListeners == null) {
/* 1906 */       this.mTabHostListeners = new ArrayList<>();
/*      */     }
/* 1908 */     if (!this.mTabHostListeners.contains(listener)) {
/* 1909 */       this.mTabHostListeners.add(listener);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeHostListener(TabHostListener listener) {
/* 1920 */     if (this.mTabHostListeners != null) {
/* 1921 */       this.mTabHostListeners.remove(listener);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAppBarVisibilityListener(AppBarVisibilityListener listener) {
/* 1930 */     this.mAppBarVisibilityListener = listener;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearHostListeners() {
/* 1938 */     if (this.mTabHostListeners != null) {
/* 1939 */       this.mTabHostListeners.clear();
/*      */     }
/*      */   }
/*      */   
/*      */   public void onSaveAsOptionSelected() {
/* 1944 */     PdfViewCtrlTabFragment fragment = getCurrentPdfViewCtrlFragment();
/* 1945 */     if (fragment != null) {
/* 1946 */       fragment.save(false, true, true);
/* 1947 */       fragment.handleSaveAsCopy();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void onFlattenOptionSelected() {
/* 1952 */     if (checkTabConversionAndAlert(R.string.cant_save_while_converting_message, false, true)) {
/*      */       return;
/*      */     }
/*      */     
/* 1956 */     FragmentActivity fragmentActivity = getActivity();
/* 1957 */     if (fragmentActivity == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1961 */     String msg = String.format(getString(R.string.dialog_flatten_message), new Object[] { getString(R.string.app_name) });
/* 1962 */     String title = getString(R.string.dialog_flatten_title);
/*      */     
/* 1964 */     Utils.getAlertDialogBuilder((Context)fragmentActivity, msg, title)
/* 1965 */       .setPositiveButton(R.string.tools_qm_flatten, new DialogInterface.OnClickListener()
/*      */         {
/*      */           public void onClick(DialogInterface dialog, int which) {
/* 1968 */             PdfViewCtrlTabFragment fragment = PdfViewCtrlTabHostFragment.this.getCurrentPdfViewCtrlFragment();
/* 1969 */             if (fragment != null) {
/* 1970 */               fragment.save(false, true, true);
/* 1971 */               fragment.handleSaveFlattenedCopy();
/*      */             }
/*      */           
/*      */           }
/* 1975 */         }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
/*      */         {
/*      */ 
/*      */ 
/*      */           
/*      */           public void onClick(DialogInterface dialog, int which) {}
/* 1981 */         }).create().show();
/*      */   }
/*      */   
/*      */   public void onSaveOptimizedCopySelected() {
/* 1985 */     if (checkTabConversionAndAlert(R.string.cant_save_while_converting_message, false, true)) {
/*      */       return;
/*      */     }
/*      */     
/* 1989 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 1990 */     if (currentFragment == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1994 */     currentFragment.save(false, true, true);
/*      */     
/* 1996 */     OptimizeDialogFragment dialog = OptimizeDialogFragment.newInstance();
/* 1997 */     dialog.setListener(new OptimizeDialogFragment.OptimizeDialogFragmentListener()
/*      */         {
/*      */           public void onOptimizeClicked(OptimizeParams result) {
/* 2000 */             PdfViewCtrlTabFragment fragment = PdfViewCtrlTabHostFragment.this.getCurrentPdfViewCtrlFragment();
/* 2001 */             if (fragment == null) {
/*      */               return;
/*      */             }
/* 2004 */             fragment.handleSaveOptimizedCopy(result);
/*      */           }
/*      */         });
/* 2007 */     FragmentManager fragmentManager = getFragmentManager();
/* 2008 */     if (fragmentManager != null) {
/* 2009 */       dialog.show(fragmentManager, "optimize_dialog");
/*      */     }
/*      */   }
/*      */   
/*      */   public void onSavePasswordCopySelected() {
/* 2014 */     if (checkTabConversionAndAlert(R.string.cant_save_while_converting_message, false, true)) {
/*      */       return;
/*      */     }
/*      */     
/* 2018 */     PdfViewCtrlTabFragment fragment = getCurrentPdfViewCtrlFragment();
/* 2019 */     if (fragment != null) {
/* 2020 */       fragment.save(false, true, true);
/* 2021 */       fragment.handleSavePasswordCopy();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void onSaveCroppedCopySelected() {
/* 2026 */     if (checkTabConversionAndAlert(R.string.cant_save_while_converting_message, false)) {
/*      */       return;
/*      */     }
/*      */     
/* 2030 */     final FragmentActivity activity = getActivity();
/* 2031 */     final PdfViewCtrlTabFragment fragment = getCurrentPdfViewCtrlFragment();
/* 2032 */     if (activity == null || fragment == null) {
/*      */       return;
/*      */     }
/* 2035 */     final PDFViewCtrl pdfViewCtrl = fragment.getPDFViewCtrl();
/* 2036 */     if (pdfViewCtrl == null) {
/*      */       return;
/*      */     }
/* 2039 */     fragment.save(false, true, true);
/*      */     
/* 2041 */     final ProgressDialog progressDialog = new ProgressDialog((Context)activity);
/*      */     
/* 2043 */     this.mDisposables.add(fragment.hasUserCropBoxDisposable()
/* 2044 */         .subscribeOn(Schedulers.io())
/* 2045 */         .observeOn(AndroidSchedulers.mainThread())
/* 2046 */         .doOnSubscribe(new Consumer<Disposable>()
/*      */           {
/*      */             public void accept(Disposable disposable) throws Exception {
/* 2049 */               progressDialog.setMessage(PdfViewCtrlTabHostFragment.this.getString(R.string.save_crop_wait));
/* 2050 */               progressDialog.setCancelable(false);
/* 2051 */               progressDialog.setProgressStyle(0);
/* 2052 */               progressDialog.setIndeterminate(true);
/*      */             }
/* 2055 */           }).subscribe(new Consumer<Boolean>()
/*      */           {
/*      */             public void accept(Boolean params) throws Exception {
/* 2058 */               progressDialog.dismiss();
/*      */               
/* 2060 */               pdfViewCtrl.requestRendering();
/* 2061 */               if (progressDialog.isShowing()) {
/* 2062 */                 progressDialog.dismiss();
/*      */               }
/* 2064 */               if (params != null) {
/* 2065 */                 if (params.booleanValue()) {
/* 2066 */                   fragment.handleSaveCroppedCopy();
/*      */                 } else {
/* 2068 */                   AlertDialog.Builder builder = new AlertDialog.Builder((Context)activity);
/* 2069 */                   builder.setMessage(PdfViewCtrlTabHostFragment.this.getString(R.string.save_crop_no_cropbox_warning_msg))
/* 2070 */                     .setCancelable(true);
/* 2071 */                   int posButton = R.string.save_crop_no_cropbox_warning_positive;
/* 2072 */                   int negButton = R.string.cancel;
/*      */                   
/* 2074 */                   builder.setPositiveButton(posButton, new DialogInterface.OnClickListener()
/*      */                       {
/*      */                         public void onClick(DialogInterface dialog, int which) {
/* 2077 */                           dialog.dismiss();
/*      */                           
/* 2079 */                           UserCropSelectionDialogFragment cropDialog = UserCropSelectionDialogFragment.newInstance();
/* 2080 */                           cropDialog.setUserCropSelectionDialogFragmentListener(PdfViewCtrlTabHostFragment.this);
/* 2081 */                           FragmentManager fragmentManager = PdfViewCtrlTabHostFragment.this.getFragmentManager();
/* 2082 */                           if (fragmentManager != null) {
/* 2083 */                             cropDialog.show(fragmentManager, "user_crop_mode_picker");
/*      */                           }
/* 2085 */                           PdfViewCtrlTabHostFragment.this.stopHideToolbarsTimer();
/*      */                         }
/* 2087 */                       }).setNegativeButton(negButton, new DialogInterface.OnClickListener()
/*      */                       {
/*      */                         public void onClick(DialogInterface dialog, int which) {
/* 2090 */                           dialog.dismiss();
/*      */                         }
/* 2092 */                       }).create().show();
/*      */                 } 
/*      */               }
/*      */             }
/*      */           }new Consumer<Throwable>()
/*      */           {
/*      */             public void accept(Throwable throwable) throws Exception {
/* 2099 */               progressDialog.dismiss();
/*      */             }
/*      */           }));
/*      */   }
/*      */ 
/*      */   
/*      */   @SuppressLint({"RestrictedApi"})
/*      */   private void initViews() {
/* 2107 */     FragmentActivity activity = getActivity();
/* 2108 */     if (activity == null || this.mFragmentView == null) {
/*      */       return;
/*      */     }
/*      */     
/* 2112 */     this.mFragmentView.addOnLayoutChangeListener(this);
/*      */     
/* 2114 */     if (Utils.isKitKat()) {
/*      */       
/* 2116 */       this.mFragmentView.setOnSystemUiVisibilityChangeListener(this);
/* 2117 */       this.mLastSystemUIVisibility = this.mFragmentView.getWindowSystemUiVisibility();
/*      */     } 
/*      */     
/* 2120 */     this.mTabLayout = (CustomFragmentTabLayout)this.mFragmentView.findViewById(R.id.doc_tabs);
/* 2121 */     this.mTabLayout.setup((Context)activity, 
/* 2122 */         getChildFragmentManager(), (this.mViewerConfig != null && 
/* 2123 */         !this.mViewerConfig.isAutoHideToolbarEnabled()) ? R.id.adjust_fragment_container : R.id.realtabcontent);
/*      */ 
/*      */     
/* 2126 */     this.mTabLayout.addOnTabSelectedListener(this);
/*      */     
/* 2128 */     this.mToolbar = (Toolbar)this.mFragmentView.findViewById(R.id.toolbar);
/* 2129 */     if (!useSupportActionBar()) {
/* 2130 */       for (int res : this.mToolbarMenuResArray) {
/* 2131 */         this.mToolbar.inflateMenu(res);
/*      */       }
/* 2133 */       onCreateOptionsMenu(this.mToolbar.getMenu(), new MenuInflater((Context)activity));
/* 2134 */       this.mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener()
/*      */           {
/*      */             public boolean onMenuItemClick(MenuItem menuItem) {
/* 2137 */               return PdfViewCtrlTabHostFragment.this.onOptionsItemSelected(menuItem);
/*      */             }
/*      */           });
/* 2140 */       this.mToolbar.setNavigationOnClickListener(new View.OnClickListener()
/*      */           {
/*      */             public void onClick(View v) {
/* 2143 */               PdfViewCtrlTabHostFragment.this.handleNavIconClick();
/*      */             }
/*      */           });
/* 2146 */       this.mToolbar.setMenuCallbacks(null, new MenuBuilder.Callback()
/*      */           {
/*      */             public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
/* 2149 */               return false;
/*      */             }
/*      */ 
/*      */             
/*      */             public void onMenuModeChange(MenuBuilder menu) {
/* 2154 */               PdfViewCtrlTabHostFragment.this.onUpdateOptionsMenu();
/*      */             }
/*      */           });
/*      */     } 
/*      */     
/* 2159 */     this.mSearchToolbar = (SearchToolbar)this.mFragmentView.findViewById(R.id.searchToolbar);
/* 2160 */     this.mSearchToolbar.setSearchToolbarListener(new SearchToolbar.SearchToolbarListener()
/*      */         {
/*      */ 
/*      */           
/*      */           public void onExitSearch()
/*      */           {
/* 2166 */             if (PdfViewCtrlTabHostFragment.this.mSearchResultsView != null && PdfViewCtrlTabHostFragment.this.mSearchResultsView.getVisibility() == 0) {
/* 2167 */               PdfViewCtrlTabHostFragment.this.hideSearchResults();
/*      */             } else {
/* 2169 */               PdfViewCtrlTabHostFragment.this.exitSearchMode();
/*      */             } 
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void onClearSearchQuery() {
/* 2176 */             PdfViewCtrlTabFragment currentFragment = PdfViewCtrlTabHostFragment.this.getCurrentPdfViewCtrlFragment();
/* 2177 */             if (currentFragment == null) {
/*      */               return;
/*      */             }
/* 2180 */             currentFragment.cancelFindText();
/* 2181 */             if (PdfViewCtrlTabHostFragment.this.mSearchResultsView != null) {
/* 2182 */               if (PdfViewCtrlTabHostFragment.this.mSearchResultsView.isActive()) {
/* 2183 */                 PdfViewCtrlTabHostFragment.this.mSearchResultsView.cancelGetResult();
/*      */               }
/* 2185 */               PdfViewCtrlTabHostFragment.this.hideSearchResults();
/*      */             } 
/*      */           }
/*      */ 
/*      */           
/*      */           public void onSearchQuerySubmit(String s) {
/* 2191 */             PdfViewCtrlTabFragment currentFragment = PdfViewCtrlTabHostFragment.this.getCurrentPdfViewCtrlFragment();
/* 2192 */             if (currentFragment != null) {
/* 2193 */               currentFragment.queryTextSubmit(s);
/*      */             }
/*      */             
/* 2196 */             if (PdfViewCtrlTabHostFragment.this.mSearchResultsView != null) {
/* 2197 */               PdfViewCtrlTabHostFragment.this.mSearchResultsView.findText(s);
/*      */             }
/*      */           }
/*      */ 
/*      */           
/*      */           public void onSearchQueryChange(String s) {
/* 2203 */             PdfViewCtrlTabFragment currentFragment = PdfViewCtrlTabHostFragment.this.getCurrentPdfViewCtrlFragment();
/* 2204 */             if (currentFragment != null) {
/* 2205 */               currentFragment.setSearchQuery(s);
/*      */             }
/*      */             
/* 2208 */             if (PdfViewCtrlTabHostFragment.this.mSearchResultsView != null && PdfViewCtrlTabHostFragment.this.mSearchResultsView.isActive() && !PdfViewCtrlTabHostFragment.this.mSearchResultsView.getSearchPattern().equals(s)) {
/* 2209 */               PdfViewCtrlTabHostFragment.this.mSearchResultsView.cancelGetResult();
/*      */             }
/*      */           }
/*      */ 
/*      */           
/*      */           public void onSearchOptionsItemSelected(MenuItem item, String searchQuery) {
/* 2215 */             PdfViewCtrlTabFragment currentFragment = PdfViewCtrlTabHostFragment.this.getCurrentPdfViewCtrlFragment();
/* 2216 */             if (currentFragment == null) {
/*      */               return;
/*      */             }
/* 2219 */             int id = item.getItemId();
/* 2220 */             if (id == R.id.action_list_all) {
/* 2221 */               if (currentFragment.isDocumentReady()) {
/* 2222 */                 PdfViewCtrlTabHostFragment.this.onListAllOptionSelected(searchQuery);
/* 2223 */                 AnalyticsHandlerAdapter.getInstance().sendEvent(12);
/*      */               } 
/* 2225 */             } else if (id == R.id.action_match_case) {
/* 2226 */               if (currentFragment.isDocumentReady()) {
/* 2227 */                 boolean isChecked = item.isChecked();
/* 2228 */                 PdfViewCtrlTabHostFragment.this.onSearchMatchCaseOptionSelected(!isChecked);
/* 2229 */                 item.setChecked(!isChecked);
/*      */               } 
/* 2231 */             } else if (id == R.id.action_whole_word && 
/* 2232 */               currentFragment.isDocumentReady()) {
/* 2233 */               boolean isChecked = item.isChecked();
/* 2234 */               PdfViewCtrlTabHostFragment.this.onSearchWholeWordOptionSelected(!isChecked);
/* 2235 */               item.setChecked(!isChecked);
/*      */             } 
/*      */           }
/*      */         });
/*      */ 
/*      */     
/* 2241 */     updateToolbarDrawable();
/*      */     
/* 2243 */     this.mAppBarLayout = (AppBarLayout)this.mFragmentView.findViewById(R.id.app_bar_layout);
/* 2244 */     if (this.mViewerConfig != null && !this.mViewerConfig.isShowTopToolbar()) {
/* 2245 */       this.mAppBarLayout.setVisibility(8);
/*      */     }
/*      */     
/* 2248 */     this.mFragmentContainer = (FrameLayout)this.mFragmentView.findViewById(R.id.realtabcontent);
/* 2249 */     if (this.mFragmentContainer != null)
/*      */     {
/*      */ 
/*      */       
/* 2253 */       ViewCompat.setOnApplyWindowInsetsListener((View)this.mFragmentContainer, new OnApplyWindowInsetsListener()
/*      */           {
/*      */             public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat insets) {
/* 2256 */               WindowInsetsCompat result = insets;
/* 2257 */               Context context = (view != null) ? view.getContext() : null;
/* 2258 */               if (context != null && !PdfViewCtrlSettingsManager.getFullScreenMode(context))
/*      */               {
/* 2260 */                 result = ViewCompat.onApplyWindowInsets(view, insets);
/*      */               }
/*      */ 
/*      */               
/* 2264 */               DisplayCutoutCompat cutout = insets.getDisplayCutout();
/* 2265 */               PdfViewCtrlTabFragment currentFragment = PdfViewCtrlTabHostFragment.this.getCurrentPdfViewCtrlFragment();
/* 2266 */               if (cutout != null)
/*      */               {
/*      */ 
/*      */                 
/* 2270 */                 if (currentFragment != null && PdfViewCtrlTabHostFragment.this.mAppBarLayout != null && !PdfViewCtrlTabHostFragment.this.mWillShowAnnotationToolbar) {
/* 2271 */                   if (PdfViewCtrlTabHostFragment.this.mAppBarLayout.getVisibility() == 0) {
/* 2272 */                     currentFragment.applyCutout(0, 0);
/*      */                   } else {
/* 2274 */                     currentFragment.applyCutout(cutout.getSafeInsetTop(), cutout.getSafeInsetBottom());
/*      */                   } 
/*      */                 }
/*      */               }
/*      */               
/* 2279 */               PdfViewCtrlTabHostFragment.this.mSystemWindowInsetTop = result.getSystemWindowInsetTop();
/* 2280 */               PdfViewCtrlTabHostFragment.this.mSystemWindowInsetBottom = result.getSystemWindowInsetBottom();
/*      */               
/* 2282 */               if (currentFragment != null && PdfViewCtrlTabHostFragment.this.mAppBarLayout != null)
/*      */               {
/* 2284 */                 if (PdfViewCtrlTabHostFragment.this.mAppBarLayout.getVisibility() == 0 && !PdfViewCtrlTabHostFragment.this.mWillShowAnnotationToolbar) {
/* 2285 */                   int topMargin = PdfViewCtrlTabHostFragment.this.getToolbarHeight();
/* 2286 */                   if (PdfViewCtrlTabHostFragment.this.mViewerConfig != null && !PdfViewCtrlTabHostFragment.this.mViewerConfig.isAutoHideToolbarEnabled()) {
/* 2287 */                     topMargin = 0;
/*      */                   }
/* 2289 */                   currentFragment.updateNavigationListLayout(topMargin, PdfViewCtrlTabHostFragment.this.mSystemWindowInsetBottom, !PdfViewCtrlTabHostFragment.this.mWillShowAnnotationToolbar);
/*      */                 } else {
/* 2291 */                   currentFragment.updateNavigationListLayout(0, 0, !PdfViewCtrlTabHostFragment.this.mWillShowAnnotationToolbar);
/*      */                 } 
/*      */               }
/*      */               
/* 2295 */               return result;
/*      */             }
/*      */           });
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
/*      */   
/*      */   public TabLayout.Tab addTab(@Nullable Bundle args, String tag, String title, String fileExtension, String password, int itemSource) {
/* 2313 */     if (args == null) {
/* 2314 */       args = PdfViewCtrlTabFragment.createBasicPdfViewCtrlTabBundle(tag, title, fileExtension, password, itemSource, this.mViewerConfig);
/*      */     }
/*      */ 
/*      */     
/* 2318 */     TabLayout.Tab tab = createTab(tag, title, fileExtension, itemSource);
/* 2319 */     if (tab != null) {
/* 2320 */       this.mTabLayout.addTab(tab, this.mTabFragmentClass, args);
/*      */     }
/*      */     
/* 2323 */     return tab;
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
/*      */   protected TabLayout.Tab createTab(String tag, String title, String fileExtension, int itemSource) {
/* 2336 */     TabLayout.Tab tab = this.mTabLayout.newTab().setTag(tag).setCustomView(R.layout.controls_fragment_tabbed_pdfviewctrl_tab);
/* 2337 */     setTabView(tab.getCustomView(), tag, title, fileExtension, itemSource);
/* 2338 */     return tab;
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
/*      */   protected void setTabView(View view, final String tag, final String title, final String fileExtension, final int itemSource) {
/* 2352 */     if (view == null) {
/*      */       return;
/*      */     }
/*      */     
/* 2356 */     view.setOnClickListener(new View.OnClickListener()
/*      */         {
/*      */           public void onClick(View v) {
/* 2359 */             if (PdfViewCtrlTabHostFragment.this.mTabLayout != null) {
/* 2360 */               TabLayout.Tab currentTab = PdfViewCtrlTabHostFragment.this.mTabLayout.getTabByTag(tag);
/* 2361 */               if (currentTab != null) {
/* 2362 */                 currentTab.select();
/*      */               }
/*      */             } 
/*      */           }
/*      */         });
/*      */     
/* 2368 */     view.setOnLongClickListener(new View.OnLongClickListener()
/*      */         {
/*      */           public boolean onLongClick(View v) {
/* 2371 */             PdfViewCtrlTabHostFragment.this.handleShowTabInfo(tag, title, fileExtension, itemSource, 0);
/* 2372 */             return true;
/*      */           }
/*      */         });
/*      */     
/* 2376 */     if (Utils.isMarshmallow()) {
/* 2377 */       view.setOnContextClickListener(new View.OnContextClickListener()
/*      */           {
/*      */             public boolean onContextClick(View v) {
/* 2380 */               return v.performLongClick();
/*      */             }
/*      */           });
/*      */     }
/*      */     
/* 2385 */     TextView textView = (TextView)view.findViewById(R.id.tab_pdfviewctrl_text);
/* 2386 */     if (textView != null) {
/* 2387 */       textView.setText(title);
/* 2388 */       if (this.mTabLayout != null) {
/* 2389 */         ColorStateList tint = this.mTabLayout.getTabTextColors();
/* 2390 */         textView.setTextColor(tint);
/*      */       } 
/*      */     } 
/*      */     
/* 2394 */     View closeButton = view.findViewById(R.id.tab_pdfviewctrl_close_button);
/* 2395 */     if (closeButton != null) {
/* 2396 */       closeButton.setOnClickListener(new View.OnClickListener()
/*      */           {
/*      */             public void onClick(View v) {
/* 2399 */               PdfViewCtrlTabHostFragment.this.closeTab(tag, itemSource);
/*      */             }
/*      */           });
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeTabAt(int index) {
/* 2411 */     FragmentActivity fragmentActivity = getActivity();
/* 2412 */     if (fragmentActivity == null) {
/*      */       return;
/*      */     }
/* 2415 */     if (this.mTabLayout.getTabCount() > index && index >= 0) {
/* 2416 */       TabLayout.Tab tab = this.mTabLayout.getTabAt(index);
/* 2417 */       if (tab != null) {
/* 2418 */         PdfViewCtrlTabsManager.getInstance().removeDocument((Context)fragmentActivity, (String)tab.getTag());
/* 2419 */         this.mTabLayout.removeTab(tab);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeTab(String filepath) {
/* 2430 */     FragmentActivity fragmentActivity = getActivity();
/* 2431 */     if (fragmentActivity == null) {
/*      */       return;
/*      */     }
/*      */     
/* 2435 */     String currentTabTag = getCurrentTabTag();
/* 2436 */     if (currentTabTag == null) {
/*      */       return;
/*      */     }
/*      */     
/* 2440 */     PdfViewCtrlTabsManager.getInstance().removeDocument((Context)fragmentActivity, filepath);
/*      */ 
/*      */     
/* 2443 */     String nextTabTagToSelect = null;
/* 2444 */     if (currentTabTag.equals(filepath))
/*      */     {
/* 2446 */       nextTabTagToSelect = PdfViewCtrlTabsManager.getInstance().getLatestViewedTabTag((Context)fragmentActivity);
/*      */     }
/*      */     
/* 2449 */     removeTab(filepath, nextTabTagToSelect);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeTab(String filepath, final String nextTabTagToSelect) {
/* 2459 */     FragmentActivity fragmentActivity = getActivity();
/* 2460 */     if (fragmentActivity == null || this.mTabLayout == null) {
/*      */       return;
/*      */     }
/*      */     
/* 2464 */     if (Utils.isNullOrEmpty(filepath)) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2470 */     setCurrentTabByTag(nextTabTagToSelect);
/*      */     
/* 2472 */     TabLayout.Tab closedTab = this.mTabLayout.getTabByTag(filepath);
/* 2473 */     if (closedTab != null) {
/* 2474 */       this.mTabLayout.removeTab(closedTab);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2481 */     this.mTabLayout.post(new Runnable()
/*      */         {
/*      */           public void run() {
/* 2484 */             PdfViewCtrlTabHostFragment.this.setCurrentTabByTag(nextTabTagToSelect);
/*      */           }
/*      */         });
/*      */     
/* 2488 */     if (this.mTabLayout.getTabCount() == 0) {
/* 2489 */       onLastTabClosed();
/*      */     }
/*      */   }
/*      */   
/*      */   private void onLastTabClosed() {
/* 2494 */     if (this.mTabHostListeners != null) {
/* 2495 */       for (TabHostListener listener : this.mTabHostListeners) {
/* 2496 */         listener.onLastTabClosed();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void closeAllTabs() {
/* 2505 */     FragmentActivity fragmentActivity = getActivity();
/* 2506 */     if (fragmentActivity == null || this.mTabLayout == null) {
/*      */       return;
/*      */     }
/* 2509 */     while (this.mTabLayout.getTabCount() > 0) {
/* 2510 */       TabLayout.Tab tab = this.mTabLayout.getTabAt(0);
/* 2511 */       if (tab != null) {
/* 2512 */         PdfViewCtrlTabsManager.getInstance().removeDocument((Context)fragmentActivity, (String)tab.getTag());
/* 2513 */         this.mTabLayout.removeTab(tab);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void closeTab(String tag) {
/* 2524 */     FragmentActivity fragmentActivity = getActivity();
/* 2525 */     if (fragmentActivity == null || this.mTabLayout == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 2530 */     Fragment fragment = this.mTabLayout.getFragmentByTag(tag);
/* 2531 */     if (fragment instanceof PdfViewCtrlTabFragment) {
/* 2532 */       closeTab(tag, ((PdfViewCtrlTabFragment)fragment).getTabSource());
/*      */     }
/*      */   }
/*      */   
/*      */   private void closeTab(final String tag, final int itemSource) {
/* 2537 */     FragmentActivity fragmentActivity = getActivity();
/* 2538 */     if (fragmentActivity == null || this.mTabLayout == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 2543 */     Fragment fragment = this.mTabLayout.getFragmentByTag(tag);
/* 2544 */     boolean isDocModified = false;
/* 2545 */     boolean isTabReadOnly = true;
/* 2546 */     PdfViewCtrlTabFragment pdfViewCtrlTabFragment = null;
/* 2547 */     if (fragment instanceof PdfViewCtrlTabFragment) {
/* 2548 */       pdfViewCtrlTabFragment = (PdfViewCtrlTabFragment)fragment;
/* 2549 */       isDocModified = pdfViewCtrlTabFragment.isDocModifiedAfterOpening();
/* 2550 */       isTabReadOnly = pdfViewCtrlTabFragment.isTabReadOnly();
/* 2551 */       pdfViewCtrlTabFragment.setCanAddToTabInfo(false);
/*      */     } 
/*      */     
/* 2554 */     if (this.mTabLayout.getTabCount() > 1) {
/* 2555 */       final PdfViewCtrlTabInfo info = PdfViewCtrlTabsManager.getInstance().getPdfFViewCtrlTabInfo((Context)fragmentActivity, tag);
/*      */       
/* 2557 */       if (itemSource != 5) {
/* 2558 */         String desc = getString((isDocModified && !isTabReadOnly) ? R.string.snack_bar_tab_saved_and_closed : R.string.snack_bar_tab_closed);
/* 2559 */         showSnackbar(desc, getString(R.string.reopen), new View.OnClickListener()
/*      */             {
/*      */               public void onClick(View v)
/*      */               {
/* 2563 */                 if (info != null) {
/* 2564 */                   PdfViewCtrlTabsManager.getInstance().addDocument(v.getContext(), tag);
/* 2565 */                   String password = (info.password == null) ? "" : Utils.decryptIt(v.getContext(), info.password);
/* 2566 */                   PdfViewCtrlTabHostFragment.this.addTab((Bundle)null, tag, info.tabTitle, info.fileExtension, password, itemSource);
/* 2567 */                   PdfViewCtrlTabHostFragment.this.setCurrentTabByTag(tag);
/*      */                   
/* 2569 */                   AnalyticsHandlerAdapter.getInstance().sendEvent(19, 
/* 2570 */                       AnalyticsParam.viewerUndoRedoParam("close_tab", 1));
/*      */                 } 
/*      */               }
/*      */             });
/* 2574 */         if (pdfViewCtrlTabFragment != null) {
/* 2575 */           pdfViewCtrlTabFragment.setSavedAndClosedShown();
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 2580 */     removeTab(tag);
/*      */     
/* 2582 */     if (this.mTabLayout.getTabCount() == 0) {
/* 2583 */       onLastTabClosed();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeExtraTabs() {
/* 2591 */     FragmentActivity fragmentActivity = getActivity();
/* 2592 */     if (fragmentActivity == null || this.mTabLayout == null) {
/*      */       return;
/*      */     }
/*      */     
/* 2596 */     if (!PdfViewCtrlSettingsManager.getMultipleTabs((Context)fragmentActivity)) {
/* 2597 */       while (this.mTabLayout.getTabCount() > 1) {
/* 2598 */         TabLayout.Tab tab = this.mTabLayout.getTabAt(0);
/* 2599 */         if (tab != null) {
/* 2600 */           PdfViewCtrlTabsManager.getInstance().removeDocument((Context)fragmentActivity, (String)tab.getTag());
/* 2601 */           this.mTabLayout.removeTab(tab);
/*      */         } 
/*      */       } 
/*      */       
/*      */       return;
/*      */     } 
/* 2607 */     while (PdfViewCtrlTabsManager.getInstance().getDocuments((Context)fragmentActivity).size() > getMaxTabCount()) {
/* 2608 */       String removedTabTag = PdfViewCtrlTabsManager.getInstance().removeOldestViewedTab((Context)fragmentActivity);
/* 2609 */       TabLayout.Tab removedTab = this.mTabLayout.getTabByTag(removedTabTag);
/* 2610 */       if (removedTab != null) {
/* 2611 */         this.mTabLayout.removeTab(removedTab);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getMaxTabCount() {
/* 2620 */     FragmentActivity fragmentActivity = getActivity();
/* 2621 */     if (this.mViewerConfig != null && this.mViewerConfig.getMaximumTabCount() > 0)
/* 2622 */       return this.mViewerConfig.getMaximumTabCount(); 
/* 2623 */     if (fragmentActivity == null)
/* 2624 */       return 0; 
/* 2625 */     if (PdfViewCtrlSettingsManager.getUnlimitedTabsEnabled((Context)fragmentActivity, false)) {
/* 2626 */       return 1000;
/*      */     }
/* 2628 */     return Utils.isTablet((Context)fragmentActivity) ? 5 : 3;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onUndoRedoPopupClosed() {
/* 2639 */     if (this.mUndoRedoPopupWindow != null && this.mUndoRedoPopupWindow.isShowing()) {
/* 2640 */       this.mUndoRedoPopupWindow.dismiss();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void undo() {
/* 2648 */     FragmentActivity fragmentActivity = getActivity();
/* 2649 */     final PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 2650 */     if (fragmentActivity == null || currentFragment == null) {
/*      */       return;
/*      */     }
/*      */     
/* 2654 */     currentFragment.undo(false);
/*      */     
/* 2656 */     if (currentFragment.getToolManager() != null) {
/* 2657 */       UndoRedoManager undoRedoManager = currentFragment.getToolManager().getUndoRedoManger();
/* 2658 */       if (undoRedoManager != null) {
/* 2659 */         setToolbarsVisible(false);
/*      */         
/*      */         try {
/* 2662 */           if (this.mUndoRedoPopupWindow != null && this.mUndoRedoPopupWindow.isShowing()) {
/* 2663 */             this.mUndoRedoPopupWindow.dismiss();
/*      */           }
/* 2665 */           this.mUndoRedoPopupWindow = new UndoRedoPopupWindow((Context)fragmentActivity, undoRedoManager, new UndoRedoPopupWindow.OnUndoRedoListener()
/*      */               {
/*      */                 public void onUndoRedoCalled()
/*      */                 {
/* 2669 */                   currentFragment.refreshPageCount();
/*      */                 }
/*      */               },  1);
/* 2672 */           this.mUndoRedoPopupWindow.showAtLocation(currentFragment.getView(), 8388661, 0, 0);
/* 2673 */         } catch (Exception ex) {
/* 2674 */           AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void redo() {
/* 2684 */     FragmentActivity fragmentActivity = getActivity();
/* 2685 */     final PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 2686 */     if (fragmentActivity == null || currentFragment == null) {
/*      */       return;
/*      */     }
/*      */     
/* 2690 */     currentFragment.redo(false);
/*      */     
/* 2692 */     if (currentFragment.getToolManager() != null) {
/* 2693 */       UndoRedoManager undoRedoManager = currentFragment.getToolManager().getUndoRedoManger();
/* 2694 */       if (undoRedoManager != null) {
/* 2695 */         setToolbarsVisible(false);
/*      */         
/*      */         try {
/* 2698 */           if (this.mUndoRedoPopupWindow != null && this.mUndoRedoPopupWindow.isShowing()) {
/* 2699 */             this.mUndoRedoPopupWindow.dismiss();
/*      */           }
/* 2701 */           this.mUndoRedoPopupWindow = new UndoRedoPopupWindow((Context)fragmentActivity, undoRedoManager, new UndoRedoPopupWindow.OnUndoRedoListener()
/*      */               {
/*      */                 public void onUndoRedoCalled()
/*      */                 {
/* 2705 */                   currentFragment.refreshPageCount();
/*      */                 }
/*      */               },  1);
/* 2708 */           this.mUndoRedoPopupWindow.showAtLocation(currentFragment.getView(), 8388661, 0, 0);
/* 2709 */         } catch (Exception ex) {
/* 2710 */           AnalyticsHandlerAdapter.getInstance().sendException(ex);
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
/*      */   
/*      */   protected boolean canAddNewDocumentToTabList(int itemSource) {
/* 2723 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setFragmentListeners(Fragment fragment) {
/* 2732 */     if (fragment instanceof PdfViewCtrlTabFragment) {
/* 2733 */       PdfViewCtrlTabFragment tabFragment = (PdfViewCtrlTabFragment)fragment;
/* 2734 */       tabFragment.setTabListener(this);
/* 2735 */       tabFragment.addAnnotationToolbarListener(this);
/* 2736 */       tabFragment.addQuickMenuListener(this);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void removeFragmentListeners(Fragment fragment) {
/* 2741 */     if (fragment instanceof PdfViewCtrlTabFragment) {
/* 2742 */       PdfViewCtrlTabFragment tabFragment = (PdfViewCtrlTabFragment)fragment;
/* 2743 */       tabFragment.removeAnnotationToolbarListener(this);
/* 2744 */       tabFragment.removeQuickMenuListener(this);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfViewCtrlTabFragment getCurrentPdfViewCtrlFragment() {
/* 2754 */     if (this.mTabLayout == null) {
/* 2755 */       return null;
/*      */     }
/* 2757 */     Fragment fragment = this.mTabLayout.getCurrentFragment();
/* 2758 */     if (fragment instanceof PdfViewCtrlTabFragment) {
/* 2759 */       return (PdfViewCtrlTabFragment)fragment;
/*      */     }
/*      */     
/* 2762 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void showTabInfo(String message, String path, String tag, final int itemSource, int duration) {
/*      */     final String filepath;
/* 2774 */     FragmentActivity fragmentActivity = getActivity();
/* 2775 */     PdfViewCtrlTabFragment fragment = getCurrentPdfViewCtrlFragment();
/* 2776 */     if (fragmentActivity == null || fragment == null || fragment.getView() == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 2781 */     if (itemSource == 6) {
/* 2782 */       Uri uri = Uri.parse(tag);
/* 2783 */       ExternalFileInfo info = Utils.buildExternalFile((Context)fragmentActivity, uri);
/* 2784 */       if (info != null) {
/* 2785 */         String uriFilename = Uri.encode(info.getFileName());
/* 2786 */         if (!Utils.isNullOrEmpty(uriFilename) && tag.endsWith(uriFilename)) {
/* 2787 */           filepath = tag.substring(0, tag.length() - uriFilename.length());
/*      */         } else {
/* 2789 */           filepath = "";
/*      */         } 
/*      */       } else {
/* 2792 */         filepath = "";
/*      */       } 
/*      */     } else {
/* 2795 */       filepath = path;
/*      */     } 
/* 2797 */     if (sDebug) {
/* 2798 */       if (Utils.isNullOrEmpty(filepath)) {
/* 2799 */         String tempPath = "";
/* 2800 */         FileInfo info = fragment.getCurrentFileInfo();
/* 2801 */         if (info != null) {
/* 2802 */           tempPath = info.getAbsolutePath();
/*      */         }
/* 2804 */         CommonToast.showText((Context)fragmentActivity, "DEBUG: [" + itemSource + "] [" + tempPath + "]");
/*      */       } else {
/* 2806 */         CommonToast.showText((Context)fragmentActivity, "DEBUG: [" + filepath + "]");
/*      */       } 
/*      */     }
/* 2809 */     final String filename = message;
/* 2810 */     if ((itemSource == 2 || itemSource == 5 || itemSource == 6 || itemSource == 13 || itemSource == 15) && 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2815 */       !Utils.isNullOrEmpty(filepath)) {
/* 2816 */       View.OnClickListener snackbarActionListener = new View.OnClickListener()
/*      */         {
/*      */           public void onClick(View view) {
/* 2819 */             String path = null;
/* 2820 */             String name = null;
/* 2821 */             if (PdfViewCtrlTabHostFragment.this.mTabHostListeners != null) {
/* 2822 */               if (PdfViewCtrlTabHostFragment.this.mTabLayout == null) {
/*      */                 return;
/*      */               }
/* 2825 */               if (itemSource == 5) {
/* 2826 */                 ArrayList<Fragment> fragments = PdfViewCtrlTabHostFragment.this.mTabLayout.getLiveFragments();
/* 2827 */                 for (Fragment fragment : fragments) {
/* 2828 */                   if (fragment instanceof PdfViewCtrlTabFragment) {
/* 2829 */                     PdfViewCtrlTabFragment pdfViewCtrlTabFragment = (PdfViewCtrlTabFragment)fragment;
/* 2830 */                     if (pdfViewCtrlTabFragment.mTabTag.contains(filepath) && pdfViewCtrlTabFragment.mTabTag
/* 2831 */                       .contains(filename)) {
/* 2832 */                       String fullFilePath = pdfViewCtrlTabFragment.getFilePath();
/* 2833 */                       if (!Utils.isNullOrEmpty(fullFilePath)) {
/* 2834 */                         path = FilenameUtils.getPath(fullFilePath);
/* 2835 */                         name = FilenameUtils.getName(fullFilePath);
/*      */                       } 
/*      */                     } 
/*      */                   } 
/*      */                 } 
/*      */               } else {
/* 2841 */                 path = filepath;
/* 2842 */                 name = filename;
/*      */               } 
/*      */               
/* 2845 */               for (TabHostListener listener : PdfViewCtrlTabHostFragment.this.mTabHostListeners) {
/* 2846 */                 listener.onShowFileInFolder(name, path, itemSource);
/*      */               }
/*      */             } 
/*      */           }
/*      */         };
/* 2851 */       showSnackbar(message, getString(R.string.snack_bar_file_info_message), snackbarActionListener, duration);
/*      */     } else {
/* 2853 */       showSnackbar(message, (String)null, (View.OnClickListener)null, duration);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setThumbSliderVisibility(boolean visible, boolean animateThumbSlider) {
/* 2864 */     FragmentActivity fragmentActivity = getActivity();
/* 2865 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 2866 */     if (fragmentActivity == null || currentFragment == null) {
/*      */       return;
/*      */     }
/*      */     
/* 2870 */     currentFragment.setThumbSliderVisible(visible, animateThumbSlider);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCurrentTabByTag(String tag) {
/* 2879 */     if (tag == null || this.mTabLayout == null) {
/*      */       return;
/*      */     }
/*      */     
/*      */     try {
/* 2884 */       for (int i = 0, sz = this.mTabLayout.getTabCount(); i < sz; i++) {
/* 2885 */         TabLayout.Tab tab = this.mTabLayout.getTabAt(i);
/* 2886 */         if (tab != null) {
/* 2887 */           String tabTag = (String)tab.getTag();
/* 2888 */           if (tabTag != null && tabTag.equals(tag)) {
/* 2889 */             tab.select();
/*      */             return;
/*      */           } 
/*      */         } 
/*      */       } 
/* 2894 */     } catch (Exception exception) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getCurrentTabTag() {
/* 2903 */     if (this.mTabLayout == null) {
/* 2904 */       return null;
/*      */     }
/* 2906 */     int curPosition = this.mTabLayout.getSelectedTabPosition();
/* 2907 */     if (curPosition != -1) {
/* 2908 */       TabLayout.Tab tab = this.mTabLayout.getTabAt(curPosition);
/* 2909 */       if (tab != null) {
/* 2910 */         return (String)tab.getTag();
/*      */       }
/*      */     } 
/*      */     
/* 2914 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onTabSelected(TabLayout.Tab tab) {
/* 2922 */     if (sDebug) {
/* 2923 */       Log.d(TAG, "Tab " + tab.getTag() + " is selected");
/*      */     }
/* 2925 */     FragmentActivity fragmentActivity = getActivity();
/* 2926 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 2927 */     if (fragmentActivity == null || currentFragment == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2933 */     String tabTag = (String)tab.getTag();
/* 2934 */     if (tabTag != null) {
/* 2935 */       setFragmentListeners(this.mTabLayout.getFragmentByTag(tabTag));
/*      */     }
/*      */     
/* 2938 */     if (this.mTabHostListeners != null && this.mCurTabIndex != -1 && this.mCurTabIndex != tab.getPosition()) {
/* 2939 */       for (TabHostListener listener : this.mTabHostListeners) {
/* 2940 */         listener.onTabChanged(tabTag);
/*      */       }
/* 2942 */       this.mQuitAppWhenDoneViewing = false;
/*      */     } 
/* 2944 */     this.mCurTabIndex = tab.getPosition();
/*      */ 
/*      */     
/* 2947 */     this.mCurrentBookmark = null;
/*      */     
/* 2949 */     exitSearchMode();
/* 2950 */     updateTabLayout();
/* 2951 */     setToolbarsVisible(true, false);
/* 2952 */     if (!currentFragment.isDocumentReady())
/*      */     {
/* 2954 */       stopHideToolbarsTimer();
/*      */     }
/*      */ 
/*      */     
/* 2958 */     updatePrintDocumentMode();
/* 2959 */     updatePrintAnnotationsMode();
/* 2960 */     updatePrintSummaryMode();
/*      */ 
/*      */     
/* 2963 */     updateButtonViewModeIcon();
/*      */ 
/*      */     
/* 2966 */     updateShareButtonVisibility(true);
/*      */ 
/*      */     
/* 2969 */     updateIconsInReflowMode();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onTabUnselected(TabLayout.Tab tab) {
/* 2977 */     if (sDebug) {
/* 2978 */       Log.d(TAG, "Tab " + tab.getTag() + " is unselected");
/*      */     }
/* 2980 */     String tabTag = (String)tab.getTag();
/* 2981 */     if (tabTag != null) {
/* 2982 */       removeFragmentListeners(this.mTabLayout.getFragmentByTag(tabTag));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onTabReselected(TabLayout.Tab tab) {
/* 2991 */     onTabSelected(tab);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onViewModeColorSelected(int colorMode) {
/* 2999 */     FragmentActivity fragmentActivity = getActivity();
/* 3000 */     if (fragmentActivity == null) {
/* 3001 */       return false;
/*      */     }
/*      */     
/* 3004 */     PdfViewCtrlSettingsManager.setColorMode((Context)fragmentActivity, colorMode);
/* 3005 */     return updateColorMode();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onViewModeSelected(String viewMode) {
/* 3013 */     onViewModeSelected(viewMode, false, (Integer)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onViewModeSelected(String viewMode, boolean thumbnailEditMode, Integer checkedItem) {
/*      */     UserCropSelectionDialogFragment dialog;
/*      */     FragmentManager fragmentManager;
/* 3024 */     FragmentActivity activity = getActivity();
/* 3025 */     if (activity == null) {
/*      */       return;
/*      */     }
/*      */     
/* 3029 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 3030 */     if (currentFragment == null) {
/*      */       return;
/*      */     }
/* 3033 */     PDFViewCtrl pdfViewCtrl = currentFragment.getPDFViewCtrl();
/* 3034 */     if (pdfViewCtrl == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 3039 */     PDFViewCtrl.PagePresentationMode mode = PDFViewCtrl.PagePresentationMode.SINGLE;
/* 3040 */     boolean updateViewMode = false;
/* 3041 */     switch (viewMode) {
/*      */       case "continuous":
/* 3043 */         mode = PDFViewCtrl.PagePresentationMode.SINGLE_CONT;
/* 3044 */         PdfViewCtrlSettingsManager.updateViewMode((Context)activity, "continuous");
/* 3045 */         updateViewMode = true;
/*      */         break;
/*      */       case "singlepage":
/* 3048 */         mode = PDFViewCtrl.PagePresentationMode.SINGLE;
/* 3049 */         PdfViewCtrlSettingsManager.updateViewMode((Context)activity, "singlepage");
/* 3050 */         updateViewMode = true;
/*      */         break;
/*      */       case "facing":
/* 3053 */         mode = PDFViewCtrl.PagePresentationMode.FACING;
/* 3054 */         PdfViewCtrlSettingsManager.updateViewMode((Context)activity, "facing");
/* 3055 */         updateViewMode = true;
/*      */         break;
/*      */       case "facingcover":
/* 3058 */         mode = PDFViewCtrl.PagePresentationMode.FACING_COVER;
/* 3059 */         PdfViewCtrlSettingsManager.updateViewMode((Context)activity, "facingcover");
/* 3060 */         updateViewMode = true;
/*      */         break;
/*      */       case "facing_cont":
/* 3063 */         mode = PDFViewCtrl.PagePresentationMode.FACING_CONT;
/* 3064 */         PdfViewCtrlSettingsManager.updateViewMode((Context)activity, "facing_cont");
/* 3065 */         updateViewMode = true;
/*      */         break;
/*      */       case "facingcover_cont":
/* 3068 */         mode = PDFViewCtrl.PagePresentationMode.FACING_COVER_CONT;
/* 3069 */         PdfViewCtrlSettingsManager.updateViewMode((Context)activity, "facingcover_cont");
/* 3070 */         updateViewMode = true;
/*      */         break;
/*      */       case "rotation":
/* 3073 */         pdfViewCtrl.rotateClockwise();
/*      */         try {
/* 3075 */           pdfViewCtrl.updatePageLayout();
/* 3076 */         } catch (Exception e) {
/* 3077 */           AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */         } 
/*      */         break;
/*      */       case "thumbnails":
/* 3081 */         onPageThumbnailOptionSelected(thumbnailEditMode, checkedItem);
/*      */         break;
/*      */       case "user_crop":
/* 3084 */         if (checkTabConversionAndAlert(R.string.cant_edit_while_converting_message, false)) {
/*      */           return;
/*      */         }
/* 3087 */         dialog = UserCropSelectionDialogFragment.newInstance();
/* 3088 */         dialog.setUserCropSelectionDialogFragmentListener(this);
/* 3089 */         fragmentManager = getFragmentManager();
/* 3090 */         if (fragmentManager != null) {
/* 3091 */           dialog.show(fragmentManager, "user_crop_mode_picker");
/*      */         }
/*      */         break;
/*      */       case "pref_reflowmode":
/* 3095 */         onToggleReflow();
/*      */         break;
/*      */       case "pref_rtlmode":
/* 3098 */         onToggleRtlMode();
/*      */         break;
/*      */     } 
/*      */     
/* 3102 */     if (updateViewMode) {
/* 3103 */       if (currentFragment.isReflowMode())
/*      */       {
/* 3105 */         onToggleReflow();
/*      */       }
/*      */       
/* 3108 */       currentFragment.updateViewMode(mode);
/*      */ 
/*      */       
/* 3111 */       updateButtonViewModeIcon();
/*      */     } 
/*      */ 
/*      */     
/* 3115 */     resetHideToolbarsTimer();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onViewModePickerDialogFragmentDismiss() {
/* 3123 */     resetHideToolbarsTimer();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onCustomColorModeSelected(int bgColor, int txtColor) {
/* 3131 */     FragmentActivity fragmentActivity = getActivity();
/* 3132 */     if (fragmentActivity == null) {
/* 3133 */       return false;
/*      */     }
/*      */     
/* 3136 */     PdfViewCtrlSettingsManager.setCustomColorModeTextColor((Context)fragmentActivity, txtColor);
/* 3137 */     PdfViewCtrlSettingsManager.setCustomColorModeBGColor((Context)fragmentActivity, bgColor);
/* 3138 */     PdfViewCtrlSettingsManager.setColorMode((Context)fragmentActivity, 4);
/*      */     
/* 3140 */     return updateColorMode();
/*      */   }
/*      */ 
/*      */   
/*      */   public void onBookmarksDialogWillDismiss(int tabIndex) {
/* 3145 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 3146 */     if (currentFragment != null && currentFragment.isNavigationListShowing()) {
/* 3147 */       currentFragment.closeNavigationList();
/*      */     }
/* 3149 */     else if (this.mBookmarksDialog != null) {
/* 3150 */       this.mBookmarksDialog.dismiss();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onBookmarksDialogDismissed(int tabIndex) {
/* 3160 */     resetHideToolbarsTimer();
/*      */     
/* 3162 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 3163 */     if (currentFragment != null) {
/* 3164 */       currentFragment.setBookmarkDialogCurrentTab(tabIndex);
/* 3165 */       currentFragment.resetHidePageNumberIndicatorTimer();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int onReflowZoomInOut(boolean flagZoomIn) {
/* 3174 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/*      */     
/* 3176 */     if (currentFragment == null) {
/* 3177 */       return 0;
/*      */     }
/*      */     
/* 3180 */     currentFragment.zoomInOutReflow(flagZoomIn);
/* 3181 */     return currentFragment.getReflowTextSize();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setToolbarTimerDisabled(boolean disable) {
/* 3190 */     this.mToolbarTimerDisabled = disable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void resetHideToolbarsTimer() {
/* 3198 */     stopHideToolbarsTimer();
/* 3199 */     if (this.mToolbarTimerDisabled) {
/*      */       return;
/*      */     }
/* 3202 */     if (this.mHideToolbarsHandler != null) {
/* 3203 */       this.mHideToolbarsHandler.postDelayed(this.mHideToolbarsRunnable, 5000L);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void stopHideToolbarsTimer() {
/* 3211 */     if (this.mHideToolbarsHandler != null) {
/* 3212 */       this.mHideToolbarsHandler.removeCallbacksAndMessages(null);
/*      */     }
/*      */   }
/*      */   
/*      */   private void resetHideNavigationBarTimer() {
/* 3217 */     stopHideNavigationBarTimer();
/* 3218 */     if (this.mHideNavigationBarHandler != null) {
/* 3219 */       this.mHideNavigationBarHandler.postDelayed(this.mHideNavigationBarRunnable, 3000L);
/*      */     }
/*      */   }
/*      */   
/*      */   private void stopHideNavigationBarTimer() {
/* 3224 */     if (this.mHideNavigationBarHandler != null) {
/* 3225 */       this.mHideNavigationBarHandler.removeCallbacksAndMessages(null);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void onShareOptionSelected() {
/* 3233 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 3234 */     if (currentFragment == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 3239 */     if (!checkTabConversionAndAlert(R.string.cant_share_while_converting_message, true)) {
/* 3240 */       currentFragment.save(false, true, true);
/* 3241 */       currentFragment.handleOnlineShare();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addNewPage() {
/* 3249 */     final PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 3250 */     if (currentFragment == null || !currentFragment.isDocumentReady()) {
/*      */       return;
/*      */     }
/*      */     
/* 3254 */     double pageWidth = 0.0D;
/* 3255 */     double pageHeight = 0.0D;
/* 3256 */     PDFViewCtrl pdfViewCtrl = currentFragment.getPDFViewCtrl();
/* 3257 */     if (pdfViewCtrl != null) {
/* 3258 */       boolean shouldUnlockRead = false;
/*      */       try {
/* 3260 */         pdfViewCtrl.docLockRead();
/* 3261 */         shouldUnlockRead = true;
/* 3262 */         Page lastPage = pdfViewCtrl.getDoc().getPage(pdfViewCtrl.getDoc().getPageCount());
/* 3263 */         if (lastPage == null)
/*      */           return; 
/* 3265 */         pageWidth = lastPage.getPageWidth();
/* 3266 */         pageHeight = lastPage.getPageHeight();
/* 3267 */       } catch (Exception e) {
/* 3268 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */         return;
/*      */       } finally {
/* 3271 */         if (shouldUnlockRead) {
/* 3272 */           pdfViewCtrl.docUnlockRead();
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 3278 */     AddPageDialogFragment addPageDialogFragment = AddPageDialogFragment.newInstance(pageWidth, pageHeight).setInitialPageSize(AddPageDialogFragment.PageSize.Custom);
/* 3279 */     addPageDialogFragment.setOnAddNewPagesListener(new AddPageDialogFragment.OnAddNewPagesListener()
/*      */         {
/*      */           public void onAddNewPages(Page[] pages) {
/* 3282 */             if (pages == null || pages.length == 0) {
/*      */               return;
/*      */             }
/*      */             
/* 3286 */             currentFragment.onAddNewPages(pages);
/*      */           }
/*      */         });
/* 3289 */     FragmentManager fragmentManager = getFragmentManager();
/* 3290 */     if (fragmentManager != null) {
/* 3291 */       addPageDialogFragment.show(fragmentManager, "add_page_overflow_menu");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void requestDeleteCurrentPage() {
/* 3299 */     FragmentActivity fragmentActivity = getActivity();
/* 3300 */     final PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 3301 */     if (fragmentActivity == null || currentFragment == null || !currentFragment.isDocumentReady()) {
/*      */       return;
/*      */     }
/*      */     
/* 3305 */     final PDFViewCtrl pdfViewCtrl = currentFragment.getPDFViewCtrl();
/* 3306 */     if (pdfViewCtrl == null) {
/*      */       return;
/*      */     }
/*      */     
/* 3310 */     PDFDoc doc = pdfViewCtrl.getDoc();
/*      */     try {
/* 3312 */       if (doc.getPageCount() < 2) {
/* 3313 */         CommonToast.showText((Context)fragmentActivity, R.string.controls_thumbnails_view_delete_msg_all_pages);
/*      */         return;
/*      */       } 
/* 3316 */     } catch (PDFNetException e) {
/*      */       return;
/*      */     } 
/*      */     
/* 3320 */     AlertDialog.Builder alertBuilder = new AlertDialog.Builder((Context)fragmentActivity);
/* 3321 */     alertBuilder.setTitle(R.string.action_delete_current_page);
/* 3322 */     alertBuilder.setMessage(R.string.dialog_delete_current_page);
/* 3323 */     alertBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener()
/*      */         {
/*      */           public void onClick(DialogInterface dialog, int which) {
/* 3326 */             currentFragment.onDeleteCurrentPage();
/* 3327 */             dialog.dismiss();
/*      */           }
/*      */         });
/* 3330 */     alertBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
/*      */         {
/*      */           public void onClick(DialogInterface dialog, int which)
/*      */           {
/* 3334 */             dialog.dismiss();
/*      */           }
/*      */         });
/* 3337 */     alertBuilder.setNeutralButton(R.string.action_delete_multiple, new DialogInterface.OnClickListener()
/*      */         {
/*      */           public void onClick(DialogInterface dialog, int which) {
/* 3340 */             dialog.dismiss();
/* 3341 */             PdfViewCtrlTabHostFragment.this.onViewModeSelected("thumbnails", true, 
/* 3342 */                 Integer.valueOf(pdfViewCtrl.getCurrentPage()));
/*      */           }
/*      */         });
/* 3345 */     alertBuilder.create().show();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void showRotateDialog() {
/* 3352 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 3353 */     FragmentManager fragmentManager = getFragmentManager();
/* 3354 */     if (fragmentManager == null || currentFragment == null || !currentFragment.isDocumentReady()) {
/*      */       return;
/*      */     }
/*      */     
/* 3358 */     PDFViewCtrl pdfViewCtrl = currentFragment.getPDFViewCtrl();
/* 3359 */     if (pdfViewCtrl != null) {
/* 3360 */       RotateDialogFragment.newInstance()
/* 3361 */         .setPdfViewCtrl(pdfViewCtrl)
/* 3362 */         .show(fragmentManager, "rotate_dialog");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void onViewModeOptionSelected() {
/* 3370 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 3371 */     if (currentFragment == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 3377 */     currentFragment.updateCurrentPageInfo();
/* 3378 */     PDFViewCtrl.PagePresentationMode currentViewMode = PDFViewCtrl.PagePresentationMode.SINGLE_CONT;
/* 3379 */     PDFViewCtrl pdfViewCtrl = currentFragment.getPDFViewCtrl();
/* 3380 */     if (pdfViewCtrl != null) {
/* 3381 */       currentViewMode = pdfViewCtrl.getPagePresentationMode();
/*      */     }
/* 3383 */     if (currentViewMode == PDFViewCtrl.PagePresentationMode.SINGLE_VERT) {
/* 3384 */       currentViewMode = PDFViewCtrl.PagePresentationMode.SINGLE_CONT;
/* 3385 */     } else if (currentViewMode == PDFViewCtrl.PagePresentationMode.FACING_VERT) {
/* 3386 */       currentViewMode = PDFViewCtrl.PagePresentationMode.FACING_CONT;
/* 3387 */     } else if (currentViewMode == PDFViewCtrl.PagePresentationMode.FACING_COVER_VERT) {
/* 3388 */       currentViewMode = PDFViewCtrl.PagePresentationMode.FACING_COVER_CONT;
/*      */     } 
/* 3390 */     boolean isRtlMode = currentFragment.isRtlMode();
/* 3391 */     boolean isReflowMode = currentFragment.isReflowMode();
/* 3392 */     int reflowTextSize = currentFragment.getReflowTextSize();
/* 3393 */     ArrayList<Integer> hiddenViewModeItems = new ArrayList<>();
/* 3394 */     if (this.mViewerConfig != null && !this.mViewerConfig.isShowCropOption()) {
/* 3395 */       hiddenViewModeItems.add(Integer.valueOf(ViewModePickerDialogFragment.ViewModePickerItems.ITEM_ID_USERCROP.getValue()));
/*      */     }
/* 3397 */     if (this.mViewerConfig != null && this.mViewerConfig.getHideViewModeIds() != null) {
/* 3398 */       for (int item : this.mViewerConfig.getHideViewModeIds()) {
/* 3399 */         hiddenViewModeItems.add(Integer.valueOf(item));
/*      */       }
/*      */     }
/*      */     
/* 3403 */     ViewModePickerDialogFragment dialog = ViewModePickerDialogFragment.newInstance(currentViewMode, isRtlMode, isReflowMode, reflowTextSize, hiddenViewModeItems);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3410 */     dialog.setViewModePickerDialogFragmentListener(this);
/* 3411 */     dialog.setStyle(0, R.style.CustomAppTheme);
/* 3412 */     FragmentManager fragmentManager = getFragmentManager();
/* 3413 */     if (fragmentManager != null) {
/* 3414 */       dialog.show(fragmentManager, "view_mode_picker");
/*      */     }
/*      */     
/* 3417 */     stopHideToolbarsTimer();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onOutlineOptionSelected() {
/* 3425 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 3426 */     if (currentFragment != null && currentFragment.isDocumentReady()) {
/* 3427 */       onOutlineOptionSelected(currentFragment.getBookmarkDialogCurrentTab());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onOutlineOptionSelected(int initialTabIndex) {
/* 3437 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 3438 */     if (currentFragment == null) {
/*      */       return;
/*      */     }
/* 3441 */     PDFViewCtrl pdfViewCtrl = currentFragment.getPDFViewCtrl();
/* 3442 */     if (pdfViewCtrl == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 3448 */     currentFragment.updateCurrentPageInfo();
/*      */     
/* 3450 */     if (currentFragment.isNavigationListShowing()) {
/*      */       
/* 3452 */       currentFragment.closeNavigationList();
/*      */       
/*      */       return;
/*      */     } 
/* 3456 */     if (this.mBookmarksDialog != null) {
/* 3457 */       this.mBookmarksDialog.dismiss();
/*      */     }
/*      */     
/* 3460 */     this.mBookmarksDialog = createBookmarkDialogFragmentInstance();
/*      */     
/* 3462 */     this.mBookmarksDialog.setPdfViewCtrl(pdfViewCtrl)
/* 3463 */       .setDialogFragmentTabs(getBookmarksDialogTabs(), initialTabIndex)
/* 3464 */       .setCurrentBookmark(this.mCurrentBookmark);
/* 3465 */     this.mBookmarksDialog.setBookmarksDialogListener(this);
/* 3466 */     this.mBookmarksDialog.setBookmarksTabsListener(this);
/* 3467 */     this.mBookmarksDialog.setStyle(1, R.style.CustomAppTheme);
/*      */     
/* 3469 */     if (canOpenNavigationListAsSideSheet()) {
/* 3470 */       int topMargin = getToolbarHeight();
/* 3471 */       if (this.mViewerConfig != null && !this.mViewerConfig.isAutoHideToolbarEnabled()) {
/* 3472 */         topMargin = 0;
/*      */       }
/* 3474 */       currentFragment.openNavigationList(this.mBookmarksDialog, topMargin, this.mSystemWindowInsetBottom);
/* 3475 */       this.mBookmarksDialog = null;
/*      */     } else {
/* 3477 */       FragmentManager fragmentManager = getFragmentManager();
/* 3478 */       if (fragmentManager != null) {
/* 3479 */         this.mBookmarksDialog.show(fragmentManager, "bookmarks_dialog");
/*      */       }
/*      */     } 
/*      */     
/* 3483 */     stopHideToolbarsTimer();
/*      */   }
/*      */   
/*      */   protected ArrayList<DialogFragmentTab> getBookmarksDialogTabs() {
/* 3487 */     DialogFragmentTab userBookmarkTab = createUserBookmarkDialogTab();
/* 3488 */     DialogFragmentTab outlineTab = createOutlineDialogTab();
/* 3489 */     DialogFragmentTab annotationTab = createAnnotationDialogTab();
/* 3490 */     ArrayList<DialogFragmentTab> dialogFragmentTabs = new ArrayList<>(3);
/* 3491 */     if (userBookmarkTab != null) {
/* 3492 */       boolean canAdd = (this.mViewerConfig == null || this.mViewerConfig.isShowUserBookmarksList());
/* 3493 */       if (canAdd) {
/* 3494 */         dialogFragmentTabs.add(userBookmarkTab);
/*      */       }
/*      */     } 
/* 3497 */     if (outlineTab != null) {
/* 3498 */       boolean canAdd = (this.mViewerConfig == null || this.mViewerConfig.isShowOutlineList());
/* 3499 */       if (canAdd) {
/* 3500 */         dialogFragmentTabs.add(outlineTab);
/*      */       }
/*      */     } 
/* 3503 */     if (annotationTab != null) {
/* 3504 */       boolean canAdd = (this.mViewerConfig == null || this.mViewerConfig.isShowAnnotationsList());
/* 3505 */       if (canAdd) {
/* 3506 */         dialogFragmentTabs.add(annotationTab);
/*      */       }
/*      */     } 
/* 3509 */     return dialogFragmentTabs;
/*      */   }
/*      */   
/*      */   protected boolean canOpenNavigationListAsSideSheet() {
/* 3513 */     FragmentActivity fragmentActivity = getActivity();
/* 3514 */     if (fragmentActivity == null) {
/* 3515 */       return false;
/*      */     }
/*      */     
/* 3518 */     if (this.mViewerConfig != null && !this.mViewerConfig.isNavigationListAsSheetOnLargeDevice()) {
/* 3519 */       return false;
/*      */     }
/*      */     
/* 3522 */     return Utils.isLargeTablet((Context)fragmentActivity);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected BookmarksDialogFragment createBookmarkDialogFragmentInstance() {
/* 3531 */     BookmarksDialogFragment.DialogMode mode = canOpenNavigationListAsSideSheet() ? BookmarksDialogFragment.DialogMode.SHEET : BookmarksDialogFragment.DialogMode.DIALOG;
/*      */ 
/*      */     
/* 3534 */     return BookmarksDialogFragment.newInstance(mode);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected DialogFragmentTab createUserBookmarkDialogTab() {
/* 3543 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 3544 */     if (currentFragment == null) {
/* 3545 */       return null;
/*      */     }
/*      */     
/* 3548 */     PDFViewCtrl pdfViewCtrl = currentFragment.getPDFViewCtrl();
/* 3549 */     if (pdfViewCtrl == null) {
/* 3550 */       return null;
/*      */     }
/*      */     
/* 3553 */     Bundle bundle = new Bundle();
/* 3554 */     boolean readonly = currentFragment.isTabReadOnly();
/* 3555 */     if (!readonly && this.mViewerConfig != null && !this.mViewerConfig.isUserBookmarksListEditingEnabled())
/*      */     {
/* 3557 */       readonly = true;
/*      */     }
/* 3559 */     bundle.putBoolean("is_read_only", readonly);
/* 3560 */     return new DialogFragmentTab(UserBookmarkDialogFragment.class, "tab-bookmark", 
/*      */         
/* 3562 */         Utils.getDrawable(getContext(), R.drawable.ic_bookmarks_white_24dp), null, 
/*      */         
/* 3564 */         getString(R.string.bookmark_dialog_fragment_bookmark_tab_title), bundle);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected DialogFragmentTab createOutlineDialogTab() {
/* 3574 */     return new DialogFragmentTab(OutlineDialogFragment.class, "tab-outline", 
/*      */         
/* 3576 */         Utils.getDrawable(getContext(), R.drawable.ic_outline_white_24dp), null, 
/*      */         
/* 3578 */         getString(R.string.bookmark_dialog_fragment_outline_tab_title), null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected DialogFragmentTab createAnnotationDialogTab() {
/* 3588 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 3589 */     Context context = getContext();
/* 3590 */     if (currentFragment == null || context == null) {
/* 3591 */       return null;
/*      */     }
/*      */     
/* 3594 */     Bundle bundle = new Bundle();
/* 3595 */     boolean readonly = currentFragment.isTabReadOnly();
/* 3596 */     if (!readonly && this.mViewerConfig != null && !this.mViewerConfig.annotationsListEditingEnabled())
/*      */     {
/* 3598 */       readonly = true;
/*      */     }
/* 3600 */     bundle.putBoolean("is_read_only", readonly);
/* 3601 */     bundle.putBoolean("is_right-to-left", currentFragment.isRtlMode());
/* 3602 */     bundle.putInt("sort_mode_as_int", 
/* 3603 */         PdfViewCtrlSettingsManager.getAnnotListSortOrder(context, (BaseAnnotationSortOrder)AnnotationListSortOrder.DATE_ASCENDING));
/*      */ 
/*      */     
/* 3606 */     return new DialogFragmentTab(AnnotationDialogFragment.class, "tab-annotation", 
/*      */         
/* 3608 */         Utils.getDrawable(context, R.drawable.ic_annotations_white_24dp), null, 
/*      */         
/* 3610 */         getString(R.string.bookmark_dialog_fragment_annotation_tab_title), bundle, R.menu.fragment_annotlist_sort);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void onListAllOptionSelected(String searchQuery) {
/* 3619 */     Context context = getContext();
/* 3620 */     if (context == null) {
/*      */       return;
/*      */     }
/*      */     
/* 3624 */     if (this.mSearchResultsView != null && this.mSearchResultsView.getVisibility() == 0) {
/* 3625 */       hideSearchResults();
/* 3626 */     } else if (!Utils.isNullOrEmpty(searchQuery)) {
/* 3627 */       showSearchResults(searchQuery);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void onSearchMatchCaseOptionSelected(boolean isChecked) {
/* 3637 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 3638 */     if (currentFragment == null) {
/*      */       return;
/*      */     }
/* 3641 */     currentFragment.setSearchMatchCase(isChecked);
/* 3642 */     currentFragment.resetFullTextResults();
/*      */     
/* 3644 */     if (this.mSearchResultsView == null) {
/*      */       return;
/*      */     }
/* 3647 */     if (this.mSearchResultsView.getDoc() == null || this.mSearchResultsView.getDoc() != currentFragment.getPdfDoc()) {
/* 3648 */       this.mSearchResultsView.setPdfViewCtrl(currentFragment.getPDFViewCtrl());
/*      */     }
/* 3650 */     this.mSearchResultsView.setMatchCase(isChecked);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void onSearchWholeWordOptionSelected(boolean isChecked) {
/* 3659 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 3660 */     if (currentFragment == null) {
/*      */       return;
/*      */     }
/* 3663 */     currentFragment.setSearchWholeWord(isChecked);
/* 3664 */     currentFragment.resetFullTextResults();
/*      */     
/* 3666 */     if (this.mSearchResultsView == null) {
/*      */       return;
/*      */     }
/* 3669 */     if (this.mSearchResultsView.getDoc() == null || this.mSearchResultsView.getDoc() != currentFragment.getPdfDoc()) {
/* 3670 */       this.mSearchResultsView.setPdfViewCtrl(currentFragment.getPDFViewCtrl());
/*      */     }
/* 3672 */     this.mSearchResultsView.setWholeWord(isChecked);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onAnnotationToolbarShown() {
/* 3680 */     this.mWillShowAnnotationToolbar = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onAnnotationToolbarClosed() {
/* 3688 */     FragmentActivity fragmentActivity = getActivity();
/* 3689 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 3690 */     if (fragmentActivity == null || currentFragment == null) {
/*      */       return;
/*      */     }
/*      */     
/* 3694 */     setToolbarsVisible(true);
/* 3695 */     showSystemUI();
/*      */     
/* 3697 */     if (currentFragment.isNavigationListShowing()) {
/*      */       
/* 3699 */       View view = getView();
/* 3700 */       if (view != null) {
/* 3701 */         ViewCompat.requestApplyInsets(view);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onShowAnnotationToolbarByShortcut(int mode) {
/* 3711 */     showAnnotationToolbar(mode, (ToolManager.ToolMode)null);
/*      */   }
/*      */   
/*      */   public boolean showAnnotationToolbar(int mode, ToolManager.ToolMode toolMode) {
/* 3715 */     return showAnnotationToolbar(mode, (Annot)null, 0, toolMode);
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
/*      */   protected boolean showAnnotationToolbar(final int mode, final Annot inkAnnot, final int pageNum, final ToolManager.ToolMode toolMode) {
/* 3731 */     FragmentActivity fragmentActivity = getActivity();
/* 3732 */     final PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 3733 */     if (fragmentActivity == null || currentFragment == null) {
/* 3734 */       return false;
/*      */     }
/*      */     
/* 3737 */     currentFragment.localFileWriteAccessCheck();
/*      */     
/* 3739 */     if (checkTabConversionAndAlert(R.string.cant_edit_while_converting_message, false)) {
/* 3740 */       return false;
/*      */     }
/*      */     
/* 3743 */     this.mWillShowAnnotationToolbar = true;
/*      */ 
/*      */     
/* 3746 */     boolean autoHideEnabled = this.mAutoHideEnabled;
/* 3747 */     this.mAutoHideEnabled = true;
/* 3748 */     setToolbarsVisible(false);
/* 3749 */     this.mAutoHideEnabled = autoHideEnabled;
/*      */     
/* 3751 */     if (Utils.isLollipop()) {
/* 3752 */       showSystemStatusBar();
/*      */     } else {
/* 3754 */       showSystemUI();
/*      */     } 
/* 3756 */     if (currentFragment.isNavigationListShowing()) {
/*      */       
/* 3758 */       View view = getView();
/* 3759 */       if (view != null) {
/* 3760 */         ViewCompat.requestApplyInsets(view);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 3765 */     Handler handler = new Handler(Looper.getMainLooper());
/* 3766 */     handler.postDelayed(new Runnable()
/*      */         {
/*      */           public void run()
/*      */           {
/* 3770 */             PdfViewCtrlTabHostFragment.this.stopHideToolbarsTimer();
/*      */             
/* 3772 */             currentFragment.showAnnotationToolbar(mode, inkAnnot, pageNum, toolMode, !currentFragment.isAnnotationMode());
/*      */           }
/*      */         }250L);
/* 3775 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onQuickMenuClicked(QuickMenuItem menuItem) {
/* 3783 */     hideUI();
/* 3784 */     if (menuItem.getItemId() == R.id.qm_free_text) {
/* 3785 */       showSystemStatusBar();
/*      */     }
/* 3787 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean onShowQuickMenu(QuickMenu quickmenu, Annot annot) {
/* 3792 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onQuickMenuShown() {
/* 3800 */     hideUI();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onQuickMenuDismissed() {
/* 3808 */     hideUI();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean checkTabConversionAndAlert(int messageID, boolean allowConverted) {
/* 3816 */     return checkTabConversionAndAlert(messageID, allowConverted, false);
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
/*      */   public boolean checkTabConversionAndAlert(int messageID, boolean allowConverted, boolean skipSpecialFileCheck) {
/* 3828 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 3829 */     return (currentFragment != null && currentFragment.checkTabConversionAndAlert(messageID, allowConverted, skipSpecialFileCheck));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void handleThumbnailsExport(File folder, SparseBooleanArray positions) {
/* 3839 */     FragmentActivity fragmentActivity = getActivity();
/* 3840 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 3841 */     if (fragmentActivity == null || currentFragment == null) {
/*      */       return;
/*      */     }
/*      */     
/* 3845 */     PDFDoc newDoc = null;
/* 3846 */     boolean error = true;
/* 3847 */     boolean shouldUnlock = false;
/*      */     try {
/* 3849 */       newDoc = exportPages(getPageSet(positions));
/* 3850 */       if (newDoc != null) {
/* 3851 */         newDoc.lock();
/* 3852 */         shouldUnlock = true;
/* 3853 */         File tempFile = new File(folder.getAbsolutePath(), currentFragment.getTabTitle() + " export.pdf");
/* 3854 */         String filename = Utils.getFileNameNotInUse(tempFile.getAbsolutePath());
/* 3855 */         File outputFile = new File(filename);
/* 3856 */         newDoc.save(filename, SDFDoc.SaveMode.REMOVE_UNUSED, null);
/* 3857 */         showExportPagesSuccess(2, filename, outputFile.getName());
/* 3858 */         error = false;
/*      */       } 
/* 3860 */     } catch (PDFNetException e) {
/* 3861 */       AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*      */     } finally {
/* 3863 */       if (shouldUnlock) {
/* 3864 */         Utils.unlockQuietly(newDoc);
/*      */       }
/* 3866 */       Utils.closeQuietly(newDoc);
/*      */     } 
/* 3868 */     if (error) {
/* 3869 */       Utils.showAlertDialog((Activity)fragmentActivity, getString(R.string.error_export_file), getString(R.string.error));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void handleThumbnailsExport(ExternalFileInfo folder, SparseBooleanArray positions) {
/* 3880 */     FragmentActivity fragmentActivity = getActivity();
/* 3881 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 3882 */     if (fragmentActivity == null || currentFragment == null) {
/*      */       return;
/*      */     }
/*      */     
/* 3886 */     String filename = Utils.getFileNameNotInUse(folder, currentFragment.getTabTitle() + " export.pdf");
/* 3887 */     if (folder == null || Utils.isNullOrEmpty(filename)) {
/* 3888 */       Utils.showAlertDialog((Activity)fragmentActivity, getString(R.string.error_export_file), getString(R.string.error));
/*      */       return;
/*      */     } 
/* 3891 */     ExternalFileInfo file = folder.createFile("application/pdf", filename);
/* 3892 */     if (file == null) {
/*      */       return;
/*      */     }
/* 3895 */     boolean error = true;
/* 3896 */     PDFDoc newDoc = null;
/* 3897 */     SecondaryFileFilter filter = null;
/*      */     try {
/* 3899 */       newDoc = exportPages(getPageSet(positions));
/* 3900 */       if (newDoc != null) {
/* 3901 */         filter = new SecondaryFileFilter((Context)fragmentActivity, file.getUri());
/* 3902 */         newDoc.save((Filter)filter, SDFDoc.SaveMode.REMOVE_UNUSED);
/* 3903 */         showExportPagesSuccess(6, file.getUri().toString(), file.getName());
/* 3904 */         error = false;
/*      */       } 
/* 3906 */     } catch (PDFNetException|java.io.IOException e) {
/* 3907 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } finally {
/* 3909 */       Utils.closeQuietly(newDoc, filter);
/*      */     } 
/* 3911 */     if (error) {
/* 3912 */       Utils.showAlertDialog((Activity)fragmentActivity, getString(R.string.error_export_file), getString(R.string.error));
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
/*      */   protected PDFDoc exportPages(PageSet pageSet) throws PDFNetException {
/* 3924 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 3925 */     if (currentFragment != null) {
/* 3926 */       PDFDoc newDoc = new PDFDoc();
/* 3927 */       newDoc.insertPages(0, currentFragment.getPdfDoc(), pageSet, PDFDoc.InsertBookmarkMode.INSERT, null);
/* 3928 */       return newDoc;
/*      */     } 
/* 3930 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static PageSet getPageSet(SparseBooleanArray positions) {
/* 3940 */     PageSet set = new PageSet();
/* 3941 */     int rangeBegin = -1;
/* 3942 */     int rangeEnd = -1;
/*      */     
/* 3944 */     for (int i = 0; i < positions.size(); i++) {
/* 3945 */       int key = positions.keyAt(i);
/* 3946 */       boolean isSelected = positions.get(key);
/* 3947 */       int page = key + 1;
/* 3948 */       if (isSelected) {
/* 3949 */         if (rangeBegin < 0) {
/* 3950 */           rangeBegin = page;
/* 3951 */           rangeEnd = page;
/* 3952 */         } else if (rangeBegin > 0) {
/* 3953 */           if (rangeEnd + 1 == page) {
/* 3954 */             rangeEnd++;
/*      */           } else {
/* 3956 */             set.addRange(rangeBegin, rangeEnd);
/* 3957 */             rangeBegin = rangeEnd = page;
/*      */           }
/*      */         
/*      */         } 
/* 3961 */       } else if (rangeBegin > 0) {
/* 3962 */         set.addRange(rangeBegin, rangeEnd);
/* 3963 */         rangeBegin = -1;
/* 3964 */         rangeEnd = -1;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 3969 */     if (rangeBegin > 0) {
/* 3970 */       set.addRange(rangeBegin, rangeEnd);
/*      */     }
/*      */     
/* 3973 */     return set;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void showExportPagesSuccess(final int itemSource, final String tag, final String filename) {
/* 3984 */     FragmentActivity fragmentActivity = getActivity();
/* 3985 */     if (fragmentActivity == null) {
/*      */       return;
/*      */     }
/*      */     
/* 3989 */     AlertDialog.Builder successDialogBuilder = Utils.getAlertDialogBuilder((Context)fragmentActivity, "", "");
/* 3990 */     successDialogBuilder.setNegativeButton(R.string.open, new DialogInterface.OnClickListener()
/*      */         {
/*      */           public void onClick(DialogInterface dialog, int which) {
/* 3993 */             PdfViewCtrlTabHostFragment.this.onOpenAddNewTab(itemSource, tag, filename, "");
/* 3994 */             PdfViewCtrlTabHostFragment.this.mThumbFragment.dismiss();
/*      */           }
/*      */         });
/* 3997 */     successDialogBuilder.setPositiveButton(R.string.ok, null);
/* 3998 */     successDialogBuilder.setMessage((CharSequence)Html.fromHtml(getString(R.string.export_success, new Object[] { filename })));
/* 3999 */     successDialogBuilder.create().show();
/*      */   }
/*      */   
/*      */   protected void adjustConfiguration() {
/* 4003 */     FragmentActivity fragmentActivity = getActivity();
/* 4004 */     if (null == fragmentActivity || null == this.mViewerConfig) {
/*      */       return;
/*      */     }
/* 4007 */     PdfViewCtrlSettingsManager.setFullScreenMode((Context)fragmentActivity, this.mViewerConfig.isFullscreenModeEnabled());
/* 4008 */     PdfViewCtrlSettingsManager.setMultipleTabs((Context)fragmentActivity, this.mViewerConfig.isMultiTabEnabled());
/* 4009 */     this.mMultiTabModeEnabled = this.mViewerConfig.isMultiTabEnabled();
/* 4010 */     setTabLayoutVisible(this.mMultiTabModeEnabled);
/*      */   }
/*      */   
/*      */   private void onToggleRtlMode() {
/* 4014 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 4015 */     if (currentFragment != null) {
/* 4016 */       currentFragment.toggleRtlMode();
/*      */     }
/*      */   }
/*      */   
/*      */   private void updateTabLayout() {
/* 4021 */     FragmentActivity fragmentActivity = getActivity();
/* 4022 */     if (fragmentActivity == null || this.mTabLayout == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 4027 */     RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(-1, -1);
/* 4028 */     boolean isLargeScreen = Utils.isLargeScreen((Context)fragmentActivity);
/* 4029 */     boolean permanentTopToolbar = (this.mViewerConfig != null && this.mViewerConfig.isPermanentTopToolbar());
/* 4030 */     if (permanentTopToolbar && !isInFullScreenMode()) {
/* 4031 */       this.mFragmentContainer.setFitsSystemWindows(false);
/*      */     }
/* 4033 */     params.addRule(3, (isLargeScreen || permanentTopToolbar) ? R.id.app_bar_layout : R.id.parent);
/* 4034 */     this.mFragmentContainer.setLayoutParams((ViewGroup.LayoutParams)params);
/* 4035 */     this.mAutoHideEnabled = (!isLargeScreen && !permanentTopToolbar);
/* 4036 */     if (!this.mAutoHideEnabled) {
/* 4037 */       showUI();
/*      */     }
/*      */     
/* 4040 */     if (getMaxTabCount() <= 3 && this.mTabLayout.getTabCount() > 1) {
/* 4041 */       this.mTabLayout.setTabGravity(0);
/* 4042 */       this.mTabLayout.setTabMode(1);
/*      */     } else {
/* 4044 */       this.mTabLayout.setTabMode(0);
/*      */     } 
/*      */     
/* 4047 */     int tabCount = this.mTabLayout.getTabCount();
/* 4048 */     for (int i = 0; i < tabCount; i++) {
/* 4049 */       TabLayout.Tab tab = this.mTabLayout.getTabAt(i);
/* 4050 */       if (tab != null) {
/* 4051 */         View view = tab.getCustomView();
/* 4052 */         if (view != null) {
/* 4053 */           ImageButton button = (ImageButton)view.findViewById(R.id.tab_pdfviewctrl_close_button);
/* 4054 */           if (button != null) {
/* 4055 */             ColorStateList tint = this.mTabLayout.getTabTextColors();
/*      */             
/* 4057 */             if (tint != null) {
/* 4058 */               button.setColorFilter(tint.getColorForState(button.getDrawableState(), tint.getDefaultColor()), PorterDuff.Mode.SRC_IN);
/*      */             }
/*      */             
/* 4061 */             if (!Utils.isTablet(getContext()) && Utils.isPortrait(getContext()) && !tab.isSelected()) {
/* 4062 */               button.setVisibility(8);
/*      */             } else {
/* 4064 */               button.setVisibility(0);
/*      */             } 
/*      */           } 
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
/*      */   public boolean updateColorMode() {
/* 4078 */     FragmentActivity fragmentActivity = getActivity();
/* 4079 */     if (fragmentActivity == null || this.mTabLayout == null) {
/* 4080 */       return false;
/*      */     }
/*      */     
/* 4083 */     if (canRecreateActivity() && fragmentActivity instanceof AppCompatActivity && Utils.applyDayNight((AppCompatActivity)fragmentActivity)) {
/* 4084 */       return true;
/*      */     }
/*      */     
/* 4087 */     ArrayList<Fragment> fragments = this.mTabLayout.getLiveFragments();
/* 4088 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 4089 */     for (Fragment fragment : fragments) {
/* 4090 */       if (fragment instanceof PdfViewCtrlTabFragment) {
/* 4091 */         PdfViewCtrlTabFragment pdfViewCtrlTabFragment = (PdfViewCtrlTabFragment)fragment;
/* 4092 */         if (fragment == currentFragment) {
/* 4093 */           pdfViewCtrlTabFragment.updateColorMode(); continue;
/*      */         } 
/* 4095 */         pdfViewCtrlTabFragment.setColorModeChanged();
/*      */       } 
/*      */     } 
/*      */     
/* 4099 */     return false;
/*      */   }
/*      */   
/*      */   private void updatePrintDocumentMode() {
/* 4103 */     FragmentActivity fragmentActivity = getActivity();
/* 4104 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 4105 */     if (fragmentActivity == null || currentFragment == null) {
/*      */       return;
/*      */     }
/*      */     
/* 4109 */     currentFragment.updatePrintDocumentMode(PdfViewCtrlSettingsManager.isPrintDocumentMode((Context)fragmentActivity));
/*      */   }
/*      */   
/*      */   private void updatePrintAnnotationsMode() {
/* 4113 */     FragmentActivity fragmentActivity = getActivity();
/* 4114 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 4115 */     if (fragmentActivity == null || currentFragment == null) {
/*      */       return;
/*      */     }
/*      */     
/* 4119 */     currentFragment.updatePrintAnnotationsMode(PdfViewCtrlSettingsManager.isPrintAnnotationsMode((Context)fragmentActivity));
/*      */   }
/*      */   
/*      */   private void updatePrintSummaryMode() {
/* 4123 */     FragmentActivity fragmentActivity = getActivity();
/* 4124 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 4125 */     if (fragmentActivity == null || currentFragment == null) {
/*      */       return;
/*      */     }
/*      */     
/* 4129 */     currentFragment.updatePrintSummaryMode(PdfViewCtrlSettingsManager.isPrintSummaryMode((Context)fragmentActivity));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateIconsInReflowMode() {
/* 4136 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 4137 */     if (currentFragment == null) {
/*      */       return;
/*      */     }
/* 4140 */     if (currentFragment.isReflowMode()) {
/* 4141 */       if (this.mMenuReflowMode != null) {
/* 4142 */         this.mMenuReflowMode.setChecked(true);
/*      */       }
/*      */       
/* 4145 */       int alpha = getResources().getInteger(R.integer.reflow_disabled_button_alpha);
/*      */       
/* 4147 */       if (this.mMenuAnnotToolbar != null) {
/* 4148 */         if (this.mMenuAnnotToolbar.getIcon() != null) {
/* 4149 */           this.mMenuAnnotToolbar.getIcon().setAlpha(alpha);
/*      */         }
/* 4151 */         this.mMenuAnnotToolbar.setEnabled(false);
/*      */       } 
/* 4153 */       if (this.mMenuSearch != null) {
/* 4154 */         if (this.mMenuSearch.getIcon() != null) {
/* 4155 */           this.mMenuSearch.getIcon().setAlpha(alpha);
/*      */         }
/* 4157 */         this.mMenuSearch.setEnabled(false);
/*      */       } 
/* 4159 */       if (this.mMenuFormToolbar != null) {
/* 4160 */         if (this.mMenuFormToolbar.getIcon() != null) {
/* 4161 */           this.mMenuFormToolbar.getIcon().setAlpha(alpha);
/*      */         }
/* 4163 */         this.mMenuFormToolbar.setEnabled(false);
/*      */       } 
/* 4165 */       if (this.mMenuFillAndSignToolbar != null) {
/* 4166 */         if (this.mMenuFillAndSignToolbar.getIcon() != null) {
/* 4167 */           this.mMenuFillAndSignToolbar.getIcon().setAlpha(alpha);
/*      */         }
/* 4169 */         this.mMenuFillAndSignToolbar.setEnabled(false);
/*      */       } 
/*      */     } else {
/* 4172 */       if (this.mMenuReflowMode != null) {
/* 4173 */         this.mMenuReflowMode.setChecked(false);
/*      */       }
/* 4175 */       if (this.mMenuAnnotToolbar != null) {
/* 4176 */         if (this.mMenuAnnotToolbar.getIcon() != null) {
/* 4177 */           this.mMenuAnnotToolbar.getIcon().setAlpha(255);
/*      */         }
/* 4179 */         this.mMenuAnnotToolbar.setEnabled(true);
/*      */       } 
/* 4181 */       if (this.mMenuSearch != null) {
/* 4182 */         if (this.mMenuSearch.getIcon() != null) {
/* 4183 */           this.mMenuSearch.getIcon().setAlpha(255);
/*      */         }
/* 4185 */         this.mMenuSearch.setEnabled(true);
/*      */       } 
/* 4187 */       if (this.mMenuFormToolbar != null) {
/* 4188 */         if (this.mMenuFormToolbar.getIcon() != null) {
/* 4189 */           this.mMenuFormToolbar.getIcon().setAlpha(255);
/*      */         }
/* 4191 */         this.mMenuFormToolbar.setEnabled(true);
/*      */       } 
/* 4193 */       if (this.mMenuFillAndSignToolbar != null) {
/* 4194 */         if (this.mMenuFillAndSignToolbar.getIcon() != null) {
/* 4195 */           this.mMenuFillAndSignToolbar.getIcon().setAlpha(255);
/*      */         }
/* 4197 */         this.mMenuFillAndSignToolbar.setEnabled(true);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void updateButtonViewModeIcon() {
/* 4203 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 4204 */     if (currentFragment == null) {
/*      */       return;
/*      */     }
/*      */     
/* 4208 */     if (this.mMenuViewMode == null || 
/* 4209 */       currentFragment.isContinuousPageMode());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateButtonUndo(boolean hasUndo) {
/* 4218 */     if (null == this.mMenuUndo) {
/*      */       return;
/*      */     }
/* 4221 */     updateUndoButtonVisibility(true);
/* 4222 */     if (hasUndo) {
/* 4223 */       this.mMenuUndo.setEnabled(true);
/* 4224 */       if (this.mMenuUndo.getIcon() != null) {
/* 4225 */         this.mMenuUndo.getIcon().setAlpha(255);
/*      */       }
/*      */     } else {
/* 4228 */       this.mMenuUndo.setEnabled(false);
/* 4229 */       if (this.mMenuUndo.getIcon() != null) {
/* 4230 */         int alpha = getResources().getInteger(R.integer.reflow_disabled_button_alpha);
/* 4231 */         this.mMenuUndo.getIcon().setAlpha(alpha);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void showUI() {
/* 4241 */     FragmentActivity fragmentActivity = getActivity();
/* 4242 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 4243 */     if (fragmentActivity == null || currentFragment == null) {
/*      */       return;
/*      */     }
/*      */     
/* 4247 */     boolean canShowToolbars = currentFragment.onShowToolbar();
/* 4248 */     boolean canExitFullscreenMode = currentFragment.onExitFullscreenMode();
/* 4249 */     boolean isThumbSliderVisible = currentFragment.isThumbSliderVisible();
/* 4250 */     boolean isAnnotationMode = currentFragment.isAnnotationMode();
/*      */ 
/*      */     
/* 4253 */     if (!isThumbSliderVisible && canShowToolbars && canExitFullscreenMode) {
/* 4254 */       setToolbarsVisible(true);
/*      */     }
/*      */     
/* 4257 */     if (!isAnnotationMode && canExitFullscreenMode) {
/* 4258 */       showSystemUI();
/*      */     }
/*      */     
/* 4261 */     if (!isInFullScreenMode() && currentFragment.isNavigationListShowing()) {
/*      */       
/* 4263 */       View view = getView();
/* 4264 */       if (view != null) {
/* 4265 */         ViewCompat.requestApplyInsets(view);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void hideUI() {
/* 4275 */     if (this.mViewerConfig != null && !this.mViewerConfig.isAutoHideToolbarEnabled()) {
/* 4276 */       setThumbSliderVisibility(false, true);
/*      */     } else {
/* 4278 */       FragmentActivity fragmentActivity = getActivity();
/* 4279 */       PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 4280 */       if (fragmentActivity == null || currentFragment == null) {
/*      */         return;
/*      */       }
/*      */       
/* 4284 */       boolean canHideToolbars = currentFragment.onHideToolbars();
/* 4285 */       boolean canEnterFullscreenMode = currentFragment.onEnterFullscreenMode();
/* 4286 */       boolean isThumbSliderVisible = currentFragment.isThumbSliderVisible();
/* 4287 */       boolean isAnnotationMode = currentFragment.isAnnotationMode();
/*      */       
/* 4289 */       if (isThumbSliderVisible && canHideToolbars) {
/* 4290 */         setToolbarsVisible(false);
/*      */       }
/*      */ 
/*      */       
/* 4294 */       if ((isThumbSliderVisible && canHideToolbars && canEnterFullscreenMode) || (!isThumbSliderVisible && canEnterFullscreenMode))
/*      */       {
/* 4296 */         if (isAnnotationMode) {
/* 4297 */           showSystemStatusBar();
/*      */         } else {
/* 4299 */           hideSystemUI();
/*      */         } 
/*      */       }
/*      */       
/* 4303 */       if (!isInFullScreenMode() && currentFragment.isNavigationListShowing()) {
/*      */         
/* 4305 */         View view = getView();
/* 4306 */         if (view != null) {
/* 4307 */           ViewCompat.requestApplyInsets(view);
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
/*      */   
/*      */   public void setToolbarsVisible(boolean visible) {
/* 4320 */     setToolbarsVisible(visible, true);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setViewerOverlayUIVisible(boolean visible) {
/* 4325 */     if (!this.mAutoHideEnabled) {
/*      */       return;
/*      */     }
/* 4328 */     if (visible) {
/* 4329 */       showUI();
/*      */     } else {
/* 4331 */       hideUI();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setToolbarsVisible(boolean visible, boolean animateThumbSlider) {
/* 4342 */     FragmentActivity fragmentActivity = getActivity();
/* 4343 */     if (fragmentActivity == null) {
/*      */       return;
/*      */     }
/*      */     
/* 4347 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 4348 */     boolean isAnnotationMode = (currentFragment != null && currentFragment.isAnnotationMode());
/* 4349 */     if (isAnnotationMode || this.mIsSearchMode) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 4354 */     if (visible) {
/* 4355 */       resetHideToolbarsTimer();
/* 4356 */       if (currentFragment != null) {
/* 4357 */         currentFragment.resetHidePageNumberIndicatorTimer();
/*      */       }
/*      */     } else {
/* 4360 */       stopHideToolbarsTimer();
/* 4361 */       if (currentFragment != null) {
/* 4362 */         currentFragment.hidePageNumberIndicator();
/*      */       }
/*      */     } 
/* 4365 */     if (visible || this.mAutoHideEnabled) {
/* 4366 */       animateToolbars(visible);
/*      */     }
/* 4368 */     setThumbSliderVisibility(visible, animateThumbSlider);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setTabLayoutVisible(boolean visible) {
/* 4377 */     FragmentActivity fragmentActivity = getActivity();
/* 4378 */     if (fragmentActivity == null || this.mTabLayout == null) {
/*      */       return;
/*      */     }
/*      */     
/* 4382 */     boolean canHide = (this.mAutoHideEnabled || this.mIsSearchMode);
/*      */     
/* 4384 */     int i = visible | (!canHide ? 1 : 0);
/*      */     
/* 4386 */     if (!this.mMultiTabModeEnabled) {
/*      */       
/* 4388 */       if (this.mTabLayout.getVisibility() == 0) {
/* 4389 */         this.mTabLayout.setVisibility(8);
/*      */       }
/* 4391 */     } else if (((this.mTabLayout.getVisibility() == 0) ? 1 : 0) != i) {
/*      */       
/* 4393 */       this.mTabLayout.setVisibility((i != 0) ? 0 : 8);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @TargetApi(19)
/*      */   protected void showSystemStatusBar() {
/* 4402 */     FragmentActivity fragmentActivity = getActivity();
/* 4403 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 4404 */     View view = getView();
/* 4405 */     if (fragmentActivity == null || currentFragment == null || view == null) {
/*      */       return;
/*      */     }
/* 4408 */     if (isInFullScreenMode()) {
/* 4409 */       int oldFlags = view.getSystemUiVisibility();
/* 4410 */       int newFlags = oldFlags;
/*      */ 
/*      */       
/* 4413 */       newFlags &= 0xFFFFFFFB;
/*      */ 
/*      */ 
/*      */       
/* 4417 */       newFlags |= 0x1002;
/*      */ 
/*      */       
/* 4420 */       if (newFlags != oldFlags) {
/* 4421 */         view.setSystemUiVisibility(newFlags);
/* 4422 */         view.requestLayout();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @TargetApi(16)
/*      */   protected void showSystemUI() {
/* 4434 */     FragmentActivity fragmentActivity = getActivity();
/* 4435 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 4436 */     View view = getView();
/* 4437 */     if (fragmentActivity == null || currentFragment == null || view == null) {
/*      */       return;
/*      */     }
/*      */     
/* 4441 */     if (isInFullScreenMode()) {
/* 4442 */       int oldFlags = view.getSystemUiVisibility();
/* 4443 */       int newFlags = oldFlags;
/*      */ 
/*      */       
/* 4446 */       newFlags &= 0xFFFFE7F9;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 4451 */       if (newFlags != oldFlags) {
/* 4452 */         view.setSystemUiVisibility(newFlags);
/* 4453 */         view.requestLayout();
/*      */       } 
/*      */     } 
/*      */     
/* 4457 */     if (sDebug) {
/* 4458 */       Log.d(TAG, "show system UI called");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @TargetApi(19)
/*      */   protected void hideSystemUI() {
/* 4468 */     FragmentActivity fragmentActivity = getActivity();
/* 4469 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 4470 */     View view = getView();
/* 4471 */     if (fragmentActivity == null || currentFragment == null || view == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4478 */     if (isInFullScreenMode()) {
/* 4479 */       int oldFlags = view.getSystemUiVisibility();
/* 4480 */       int newFlags = oldFlags;
/*      */ 
/*      */       
/* 4483 */       newFlags |= 0x806;
/*      */ 
/*      */ 
/*      */       
/* 4487 */       if (newFlags != oldFlags) {
/* 4488 */         view.setSystemUiVisibility(newFlags);
/* 4489 */         view.requestLayout();
/*      */       } 
/*      */     } 
/*      */     
/* 4493 */     if (sDebug) {
/* 4494 */       Log.d(TAG, "hide system UI called");
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
/*      */   private void updateFullScreenModeLayout() {
/* 4509 */     FragmentActivity fragmentActivity = getActivity();
/* 4510 */     View view = getView();
/* 4511 */     if (fragmentActivity == null || view == null || this.mAppBarLayout == null) {
/*      */       return;
/*      */     }
/*      */     
/* 4515 */     if (Utils.isKitKat()) {
/* 4516 */       int oldRootFlags = view.getSystemUiVisibility();
/* 4517 */       int newRootFlags = oldRootFlags;
/*      */       
/* 4519 */       int oldAppBarFlags = this.mAppBarLayout.getSystemUiVisibility();
/* 4520 */       int newAppBarFlags = oldAppBarFlags;
/*      */       
/* 4522 */       if (PdfViewCtrlSettingsManager.getFullScreenMode((Context)fragmentActivity)) {
/*      */         
/* 4524 */         newRootFlags |= 0x600;
/*      */ 
/*      */ 
/*      */         
/* 4528 */         newAppBarFlags |= 0x100;
/*      */       } else {
/*      */         
/* 4531 */         newRootFlags &= 0xFFFFF9FF;
/*      */ 
/*      */ 
/*      */         
/* 4535 */         newAppBarFlags &= 0xFFFFFEFF;
/*      */       } 
/*      */       
/* 4538 */       view.setSystemUiVisibility(newRootFlags);
/* 4539 */       this.mAppBarLayout.setSystemUiVisibility(newAppBarFlags);
/*      */ 
/*      */ 
/*      */       
/* 4543 */       if (newRootFlags != oldRootFlags || newAppBarFlags != oldAppBarFlags) {
/* 4544 */         view.requestLayout();
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 4549 */     ViewCompat.requestApplyInsets(view);
/*      */   }
/*      */   
/*      */   public void startSearchMode() {
/* 4553 */     FragmentActivity activity = getActivity();
/* 4554 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 4555 */     if (activity == null || currentFragment == null || this.mTabLayout == null) {
/*      */       return;
/*      */     }
/* 4558 */     if (this.mToolbar == null || this.mSearchToolbar == null) {
/*      */       return;
/*      */     }
/*      */     
/* 4562 */     Fade fade = new Fade();
/* 4563 */     TransitionManager.beginDelayedTransition((ViewGroup)this.mToolbar, (Transition)fade);
/* 4564 */     this.mToolbar.setVisibility(8);
/*      */     
/* 4566 */     TransitionManager.beginDelayedTransition((ViewGroup)this.mSearchToolbar, (Transition)fade);
/* 4567 */     this.mSearchToolbar.setVisibility(0);
/*      */     
/* 4569 */     if (this.mTabHostListeners != null) {
/* 4570 */       for (TabHostListener listener : this.mTabHostListeners) {
/* 4571 */         listener.onStartSearchMode();
/*      */       }
/*      */     }
/*      */ 
/*      */     
/* 4576 */     setToolbarsVisible(true);
/* 4577 */     setTabLayoutVisible(false);
/* 4578 */     setThumbSliderVisibility(false, true);
/* 4579 */     stopHideToolbarsTimer();
/* 4580 */     setSearchNavButtonsVisible(true);
/*      */     
/* 4582 */     this.mIsSearchMode = true;
/* 4583 */     currentFragment.setSearchMode(true);
/*      */     
/* 4585 */     currentFragment.hideBackAndForwardButtons();
/*      */     
/* 4587 */     if (!Utils.isLargeScreen((Context)activity)) {
/* 4588 */       this.mSearchToolbar.measure(0, 0);
/* 4589 */       int toolbarHeight = this.mSearchToolbar.getMeasuredHeight();
/* 4590 */       currentFragment.setViewerTopMargin(toolbarHeight + this.mSystemWindowInsetTop);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void exitSearchMode() {
/* 4598 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 4599 */     if (!this.mIsSearchMode || currentFragment == null || this.mTabLayout == null) {
/*      */       return;
/*      */     }
/*      */     
/* 4603 */     this.mIsSearchMode = false;
/* 4604 */     currentFragment.setSearchMode(false);
/*      */     
/* 4606 */     if (this.mTabHostListeners != null) {
/* 4607 */       for (TabHostListener listener : this.mTabHostListeners) {
/* 4608 */         listener.onExitSearchMode();
/*      */       }
/*      */     }
/*      */     
/* 4612 */     Fade fade = new Fade();
/* 4613 */     if (this.mSearchToolbar != null) {
/* 4614 */       TransitionManager.beginDelayedTransition((ViewGroup)this.mSearchToolbar, (Transition)fade);
/* 4615 */       this.mSearchToolbar.setVisibility(8);
/*      */     } 
/* 4617 */     if (this.mToolbar != null) {
/* 4618 */       TransitionManager.beginDelayedTransition((ViewGroup)this.mToolbar, (Transition)fade);
/* 4619 */       this.mToolbar.setVisibility(0);
/*      */     } 
/*      */ 
/*      */     
/* 4623 */     setTabLayoutVisible(true);
/* 4624 */     setToolbarsVisible(true);
/* 4625 */     if (this.mSearchToolbar != null) {
/* 4626 */       this.mSearchToolbar.setSearchProgressBarVisible(false);
/*      */     }
/* 4628 */     setSearchNavButtonsVisible(false);
/*      */ 
/*      */     
/* 4631 */     currentFragment.cancelFindText();
/*      */     
/* 4633 */     currentFragment.exitSearchMode();
/* 4634 */     setThumbSliderVisibility(true, true);
/*      */     
/* 4636 */     if (this.mSearchResultsView != null) {
/* 4637 */       hideSearchResults();
/* 4638 */       this.mSearchResultsView.reset();
/*      */     } 
/*      */     
/* 4641 */     currentFragment.setViewerTopMargin(0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void hideSearchResults() {
/* 4648 */     if (this.mSearchResultsView != null) {
/* 4649 */       this.mSearchResultsView.setVisibility(8);
/*      */     }
/*      */   }
/*      */   
/*      */   private void showSearchResults(String searchQuery) {
/* 4654 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 4655 */     if (currentFragment == null) {
/*      */       return;
/*      */     }
/*      */     
/* 4659 */     if (this.mSearchResultsView == null) {
/* 4660 */       this.mSearchResultsView = inflateSearchResultsView(this);
/*      */     }
/* 4662 */     if (this.mSearchResultsView != null) {
/* 4663 */       if (this.mSearchResultsView.getDoc() == null || this.mSearchResultsView.getDoc() != currentFragment.getPdfDoc()) {
/* 4664 */         this.mSearchResultsView.setPdfViewCtrl(currentFragment.getPDFViewCtrl());
/*      */       }
/*      */       
/* 4667 */       this.mSearchResultsView.setVisibility(0);
/* 4668 */       this.mSearchResultsView.findText(searchQuery);
/*      */       
/* 4670 */       onShowSearchResults(searchQuery);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void onShowSearchResults(String searchQuery) {
/* 4675 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 4676 */     if (this.mSearchResultsView == null || currentFragment == null) {
/*      */       return;
/*      */     }
/*      */     
/* 4680 */     this.mSearchResultsView.requestFocus();
/*      */     
/* 4682 */     currentFragment.setSearchQuery(searchQuery);
/* 4683 */     currentFragment.highlightSearchResults();
/*      */   }
/*      */   
/*      */   private SearchResultsView inflateSearchResultsView(SearchResultsView.SearchResultsListener listener) {
/* 4687 */     View view = getView();
/* 4688 */     if (view == null) {
/* 4689 */       return null;
/*      */     }
/*      */     
/* 4692 */     ViewStub stub = (ViewStub)view.findViewById(R.id.controls_search_results_stub);
/* 4693 */     if (stub != null) {
/* 4694 */       SearchResultsView searchResultsView = (SearchResultsView)stub.inflate();
/* 4695 */       CoordinatorLayout.LayoutParams clp = (CoordinatorLayout.LayoutParams)searchResultsView.getLayoutParams();
/* 4696 */       clp.setBehavior((CoordinatorLayout.Behavior)new PaneBehavior());
/* 4697 */       clp.gravity = PaneBehavior.getGravityForOrientation(getContext(), (getResources().getConfiguration()).orientation);
/* 4698 */       if (Utils.isLollipop()) {
/* 4699 */         searchResultsView.setElevation(getResources().getDimension(R.dimen.actionbar_elevation));
/*      */       }
/* 4701 */       searchResultsView.setSearchResultsListener(listener);
/* 4702 */       return searchResultsView;
/*      */     } 
/* 4704 */     return null;
/*      */   }
/*      */   
/*      */   private void adjustShareButtonShowAs(Activity activity) {
/* 4708 */     if (this.mMenuShare == null || this.mMenuShare.getOrder() != 0) {
/*      */       return;
/*      */     }
/* 4711 */     if (Utils.isScreenTooNarrow((Context)activity)) {
/* 4712 */       int count = Utils.toolbarIconMaxCount((Context)activity);
/* 4713 */       if (count >= 7) {
/* 4714 */         this.mMenuShare.setShowAsAction(2);
/*      */       } else {
/* 4716 */         this.mMenuShare.setShowAsAction(1);
/*      */       } 
/*      */     } else {
/* 4719 */       this.mMenuShare.setShowAsAction(2);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateShareButtonVisibility(boolean visible) {
/* 4729 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 4730 */     if (currentFragment != null && this.mMenuShare != null) {
/* 4731 */       this.mMenuShare.setVisible((visible && (this.mViewerConfig == null || this.mViewerConfig.isShowShareOption())));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateCloseTabButtonVisibility(boolean visible) {
/* 4741 */     FragmentActivity fragmentActivity = getActivity();
/* 4742 */     if (null == fragmentActivity) {
/*      */       return;
/*      */     }
/* 4745 */     if (this.mMenuCloseTab != null) {
/* 4746 */       if (!PdfViewCtrlSettingsManager.getMultipleTabs((Context)fragmentActivity)) {
/* 4747 */         this.mMenuCloseTab.setVisible((visible && (this.mViewerConfig == null || this.mViewerConfig.isShowCloseTabOption())));
/*      */       } else {
/* 4749 */         this.mMenuCloseTab.setVisible(false);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   protected void updateUndoButtonVisibility(boolean visible) {
/* 4755 */     if (null == this.mMenuUndo) {
/*      */       return;
/*      */     }
/* 4758 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 4759 */     if (currentFragment != null) {
/* 4760 */       ToolManager toolManager = currentFragment.getToolManager();
/* 4761 */       if (toolManager != null) {
/* 4762 */         this.mMenuUndo.setVisible((visible && toolManager.isShowUndoRedo() && (this.mViewerConfig == null || this.mViewerConfig.isDocumentEditingEnabled())));
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void initOptionsMenu(Menu menu) {
/* 4768 */     FragmentActivity fragmentActivity = getActivity();
/* 4769 */     if (fragmentActivity == null) {
/*      */       return;
/*      */     }
/* 4772 */     this.mMenuUndo = menu.findItem(R.id.undo);
/* 4773 */     this.mMenuRedo = menu.findItem(R.id.redo);
/* 4774 */     this.mMenuShare = menu.findItem(R.id.action_share);
/* 4775 */     adjustShareButtonShowAs((Activity)fragmentActivity);
/* 4776 */     this.mMenuViewMode = menu.findItem(R.id.action_viewmode);
/* 4777 */     this.mMenuAnnotToolbar = menu.findItem(R.id.action_annotation_toolbar);
/* 4778 */     this.mMenuFormToolbar = menu.findItem(R.id.action_form_toolbar);
/* 4779 */     this.mMenuFillAndSignToolbar = menu.findItem(R.id.action_fill_and_sign_toolbar);
/* 4780 */     this.mMenuReflowMode = menu.findItem(R.id.action_reflow_mode);
/* 4781 */     this.mMenuEditMenu = menu.findItem(R.id.action_edit_menu);
/* 4782 */     this.mMenuSearch = menu.findItem(R.id.action_search);
/* 4783 */     this.mMenuPrint = menu.findItem(R.id.action_print);
/* 4784 */     this.mMenuEditPages = menu.findItem(R.id.action_editpages);
/* 4785 */     this.mMenuCloseTab = menu.findItem(R.id.action_close_tab);
/* 4786 */     this.mMenuViewFileAttachment = menu.findItem(R.id.action_file_attachment);
/* 4787 */     this.mMenuPdfLayers = menu.findItem(R.id.action_pdf_layers);
/* 4788 */     this.mMenuExport = menu.findItem(R.id.action_export_options);
/* 4789 */     this.mMenuOptimize = menu.findItem(R.id.menu_export_optimized_copy);
/* 4790 */     this.mMenuPasswordSave = menu.findItem(R.id.menu_export_password_copy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setOptionsMenuVisible(boolean visible) {
/* 4799 */     if (this.mMenuSearch != null) {
/* 4800 */       this.mMenuSearch.setVisible((this.mViewerConfig == null || this.mViewerConfig.isShowSearchView()));
/*      */     }
/* 4802 */     if (this.mMenuUndo != null) {
/* 4803 */       this.mMenuUndo.setVisible((visible && (this.mViewerConfig == null || this.mViewerConfig.isDocumentEditingEnabled())));
/*      */     }
/* 4805 */     if (this.mMenuRedo != null) {
/* 4806 */       this.mMenuRedo.setVisible((visible && (this.mViewerConfig == null || this.mViewerConfig.isDocumentEditingEnabled())));
/*      */     }
/* 4808 */     if (this.mMenuShare != null) {
/* 4809 */       this.mMenuShare.setVisible((visible && (this.mViewerConfig == null || this.mViewerConfig.isShowShareOption())));
/*      */     }
/* 4811 */     if (this.mMenuAnnotToolbar != null) {
/* 4812 */       this.mMenuAnnotToolbar.setVisible((visible && (this.mViewerConfig == null || this.mViewerConfig.isShowAnnotationToolbarOption())));
/*      */     }
/* 4814 */     if (this.mMenuFormToolbar != null) {
/* 4815 */       this.mMenuFormToolbar.setVisible((visible && (this.mViewerConfig == null || this.mViewerConfig.isShowFormToolbarOption())));
/*      */     }
/* 4817 */     if (this.mMenuFillAndSignToolbar != null) {
/* 4818 */       this.mMenuFillAndSignToolbar.setVisible((visible && (this.mViewerConfig == null || this.mViewerConfig.isShowFillAndSignToolbarOption())));
/*      */     }
/* 4820 */     if (this.mMenuReflowMode != null) {
/* 4821 */       this.mMenuReflowMode.setVisible((visible && (this.mViewerConfig == null || this.mViewerConfig.isShowReflowOption())));
/*      */     }
/* 4823 */     if (this.mMenuEditMenu != null) {
/* 4824 */       this.mMenuEditMenu.setVisible((visible && (this.mViewerConfig == null || this.mViewerConfig.isShowEditMenuOption())));
/*      */     }
/* 4826 */     if (this.mMenuViewMode != null) {
/* 4827 */       this.mMenuViewMode.setVisible((visible && (this.mViewerConfig == null || this.mViewerConfig.isShowDocumentSettingsOption())));
/*      */     }
/* 4829 */     if (this.mMenuPrint != null) {
/* 4830 */       if (Utils.isKitKat()) {
/* 4831 */         this.mMenuPrint.setVisible((visible && (this.mViewerConfig == null || this.mViewerConfig.isShowPrintOption())));
/*      */       } else {
/* 4833 */         this.mMenuPrint.setVisible(false);
/*      */       } 
/*      */     }
/* 4836 */     if (this.mMenuEditPages != null) {
/* 4837 */       this.mMenuEditPages.setVisible((visible && (this.mViewerConfig == null || this.mViewerConfig.isShowEditPagesOption())));
/*      */     }
/* 4839 */     if (this.mMenuExport != null) {
/* 4840 */       this.mMenuExport.setVisible((visible && (this.mViewerConfig == null || this.mViewerConfig.isShowSaveCopyOption())));
/*      */     }
/* 4842 */     if (this.mMenuOptimize != null) {
/* 4843 */       this.mMenuOptimize.setVisible((visible && (this.mViewerConfig == null || !this.mViewerConfig.isUseStandardLibrary())));
/*      */     }
/*      */     
/* 4846 */     updateUndoButtonVisibility(visible);
/* 4847 */     updateShareButtonVisibility(visible);
/* 4848 */     updateCloseTabButtonVisibility(visible);
/* 4849 */     updateIconsInReflowMode();
/*      */   }
/*      */   
/*      */   private void setSearchNavButtonsVisible(boolean visible) {
/* 4853 */     FragmentActivity fragmentActivity = getActivity();
/* 4854 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 4855 */     if (fragmentActivity == null || currentFragment == null) {
/*      */       return;
/*      */     }
/*      */     
/* 4859 */     currentFragment.setSearchNavButtonsVisible(visible);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void handleOpenFileFailed(int errorCode) {
/* 4868 */     handleOpenFileFailed(errorCode, "");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void handleOpenFileFailed(int errorCode, String info) {
/* 4878 */     FragmentActivity fragmentActivity = getActivity();
/* 4879 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 4880 */     if (fragmentActivity == null || fragmentActivity.isFinishing() || currentFragment == null) {
/*      */       return;
/*      */     }
/*      */     
/* 4884 */     int messageId = R.string.error_opening_doc_message;
/* 4885 */     boolean shouldShowErrorMessage = true;
/* 4886 */     switch (errorCode) {
/*      */       case 3:
/* 4888 */         messageId = R.string.error_empty_file_message;
/*      */         break;
/*      */       case 4:
/* 4891 */         messageId = R.string.download_cancelled_message;
/*      */         break;
/*      */       case 6:
/* 4894 */         messageId = R.string.password_not_valid_message;
/*      */         break;
/*      */       case 7:
/* 4897 */         messageId = R.string.file_does_not_exist_message;
/*      */         break;
/*      */       case 9:
/* 4900 */         messageId = R.string.download_size_cancelled_message;
/*      */         break;
/*      */       case 11:
/* 4903 */         shouldShowErrorMessage = false;
/*      */         break;
/*      */     } 
/*      */     
/* 4907 */     if (shouldShowErrorMessage) {
/* 4908 */       String message = getString(messageId);
/* 4909 */       if (this.mQuitAppWhenDoneViewing) {
/* 4910 */         CommonToast.showText((Context)fragmentActivity, message, 1);
/*      */       } else {
/* 4912 */         String title = currentFragment.getTabTitle();
/* 4913 */         title = shortenTitle(title);
/* 4914 */         Utils.showAlertDialog((Activity)fragmentActivity, message, title);
/*      */       } 
/*      */     } 
/*      */     
/* 4918 */     if (errorCode != 6) {
/* 4919 */       currentFragment.removeFromRecentList();
/*      */     }
/*      */     
/* 4922 */     removeTab(currentFragment.getTabTag());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String shortenTitle(String title) {
/* 4933 */     int maxTitleCount = 20;
/* 4934 */     if (title.length() - 1 > 20) {
/* 4935 */       title = title.substring(0, 20);
/* 4936 */       title = title + "...";
/*      */     } 
/*      */     
/* 4939 */     return title;
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
/*      */   protected void handleShowTabInfo(String tag, String title, String fileExtension, int itemSource, int duration) {
/*      */     String message;
/* 4952 */     FragmentActivity fragmentActivity = getActivity();
/* 4953 */     if (fragmentActivity == null) {
/*      */       return;
/*      */     }
/*      */     
/* 4957 */     if (this.mTabHostListeners != null) {
/* 4958 */       for (TabHostListener listener : this.mTabHostListeners) {
/* 4959 */         if (!listener.canShowFileInFolder()) {
/*      */           return;
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 4966 */     String path = "";
/*      */     try {
/* 4968 */       if (itemSource == 6) {
/* 4969 */         Uri uri = Uri.parse(tag);
/* 4970 */         message = Utils.getUriDisplayName((Context)fragmentActivity, uri);
/* 4971 */         path = Utils.getUriDocumentPath(uri);
/* 4972 */       } else if (itemSource == 13 || itemSource == 15) {
/*      */         
/* 4974 */         message = title;
/*      */       } else {
/* 4976 */         message = FilenameUtils.getName(tag);
/* 4977 */         path = FilenameUtils.getPath(tag);
/*      */       } 
/* 4979 */     } catch (Exception e) {
/* 4980 */       message = title;
/*      */     } 
/*      */     
/* 4983 */     if (message == null) {
/* 4984 */       message = title;
/*      */     }
/*      */     
/* 4987 */     showTabInfo(message, path, tag, itemSource, duration);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean handleBackPressed() {
/* 4996 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 4997 */     if (currentFragment == null) {
/* 4998 */       return false;
/*      */     }
/*      */     
/* 5001 */     if (currentFragment.isAnnotationMode()) {
/* 5002 */       currentFragment.hideAnnotationToolbar();
/* 5003 */       return true;
/*      */     } 
/*      */     
/* 5006 */     if (this.mIsSearchMode) {
/* 5007 */       if (this.mSearchResultsView != null && this.mSearchResultsView.getVisibility() == 0) {
/* 5008 */         hideSearchResults();
/*      */       } else {
/* 5010 */         exitSearchMode();
/*      */       } 
/* 5012 */       return true;
/*      */     } 
/* 5014 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean readAndUnsetFileSystemChanged() {
/* 5023 */     return this.mFileSystemChanged.getAndSet(false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean handleKeyUp(int keyCode, KeyEvent event) {
/* 5034 */     if (sDebug) {
/* 5035 */       String output = "";
/* 5036 */       if (event.isShiftPressed()) {
/* 5037 */         output = output + "SHIFT ";
/*      */       }
/* 5039 */       if (event.isCtrlPressed()) {
/* 5040 */         output = output + "CTRL ";
/*      */       }
/* 5042 */       if (event.isAltPressed()) {
/* 5043 */         output = output + "ALT ";
/*      */       }
/* 5045 */       output = output + keyCode;
/* 5046 */       Log.d(TAG, "key: " + output);
/*      */     } 
/*      */     
/* 5049 */     FragmentActivity fragmentActivity = getActivity();
/* 5050 */     if (fragmentActivity == null) {
/* 5051 */       return false;
/*      */     }
/*      */     
/* 5054 */     if (keyCode == 66 && this.mSearchToolbar != null && this.mSearchToolbar.isJustSubmittedQuery()) {
/* 5055 */       this.mSearchToolbar.setJustSubmittedQuery(false);
/* 5056 */       return false;
/*      */     } 
/*      */     
/* 5059 */     if (ShortcutHelper.isCloseApp(keyCode, event)) {
/* 5060 */       fragmentActivity.finish();
/* 5061 */       return true;
/*      */     } 
/*      */     
/* 5064 */     PdfViewCtrlTabFragment currentFragment = getCurrentPdfViewCtrlFragment();
/* 5065 */     if (currentFragment == null || !currentFragment.isDocumentReady()) {
/* 5066 */       return false;
/*      */     }
/*      */     
/* 5069 */     if (this.mSearchToolbar != null && this.mSearchToolbar
/* 5070 */       .getSearchView() != null && currentFragment
/* 5071 */       .isSearchMode()) {
/* 5072 */       if (ShortcutHelper.isGotoNextSearch(keyCode, event)) {
/* 5073 */         currentFragment.gotoNextSearch();
/*      */ 
/*      */         
/* 5076 */         this.mSearchToolbar.getSearchView().clearFocus();
/* 5077 */         return true;
/*      */       } 
/* 5079 */       if (ShortcutHelper.isGotoPreviousSearch(keyCode, event)) {
/* 5080 */         currentFragment.gotoPreviousSearch();
/*      */ 
/*      */         
/* 5083 */         this.mSearchToolbar.getSearchView().clearFocus();
/* 5084 */         return true;
/*      */       } 
/*      */ 
/*      */       
/* 5088 */       return false;
/*      */     } 
/*      */     
/* 5091 */     if (currentFragment.handleKeyUp(keyCode, event)) {
/* 5092 */       return true;
/*      */     }
/*      */     
/* 5095 */     if (this.mTabLayout != null) {
/* 5096 */       boolean isNextDoc = ShortcutHelper.isGotoNextDoc(keyCode, event);
/* 5097 */       boolean isPreviousDoc = ShortcutHelper.isGotoPreviousDoc(keyCode, event);
/*      */       
/* 5099 */       if (isNextDoc || isPreviousDoc) {
/* 5100 */         int currentPosition = this.mTabLayout.getSelectedTabPosition();
/* 5101 */         int tabCounts = this.mTabLayout.getTabCount();
/* 5102 */         if (currentPosition == -1) {
/* 5103 */           return false;
/*      */         }
/* 5105 */         if (isNextDoc) {
/* 5106 */           currentPosition++;
/*      */         } else {
/* 5108 */           currentPosition += tabCounts - 1;
/*      */         } 
/* 5110 */         currentPosition %= tabCounts;
/* 5111 */         TabLayout.Tab tab = this.mTabLayout.getTabAt(currentPosition);
/* 5112 */         if (tab != null) {
/* 5113 */           tab.select();
/* 5114 */           return true;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 5119 */     if (this.mSearchToolbar != null && 
/* 5120 */       ShortcutHelper.isFind(keyCode, event)) {
/* 5121 */       if (this.mSearchToolbar.isShown()) {
/* 5122 */         if (this.mSearchToolbar.getSearchView() != null) {
/* 5123 */           this.mSearchToolbar.getSearchView().setFocusable(true);
/* 5124 */           this.mSearchToolbar.getSearchView().requestFocus();
/*      */         }
/*      */       
/* 5127 */       } else if (this.mMenuSearch != null) {
/* 5128 */         setToolbarsVisible(true);
/* 5129 */         onOptionsItemSelected(this.mMenuSearch);
/*      */       } 
/*      */       
/* 5132 */       return true;
/*      */     } 
/*      */ 
/*      */     
/* 5136 */     if (ShortcutHelper.isCloseTab(keyCode, event)) {
/* 5137 */       closeTab(currentFragment.getTabTag(), currentFragment.getTabSource());
/* 5138 */       return true;
/*      */     } 
/*      */     
/* 5141 */     if (ShortcutHelper.isOpenDrawer(keyCode, event) && 
/* 5142 */       this.mTabHostListeners != null) {
/* 5143 */       for (TabHostListener listener : this.mTabHostListeners) {
/* 5144 */         listener.onNavButtonPressed();
/*      */       }
/* 5146 */       return true;
/*      */     } 
/*      */ 
/*      */     
/* 5150 */     return false;
/*      */   }
/*      */   
/*      */   private void animateToolbars(boolean visible) {
/* 5154 */     FragmentActivity fragmentActivity = getActivity();
/* 5155 */     if (fragmentActivity == null || this.mAppBarLayout == null) {
/*      */       return;
/*      */     }
/*      */     
/* 5159 */     if (((this.mAppBarLayout.getVisibility() == 0)) == visible) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5169 */     if (Utils.isNougat() && 
/* 5170 */       getCurrentPdfViewCtrlFragment() != null && getCurrentPdfViewCtrlFragment().getPDFViewCtrl() != null) {
/* 5171 */       PointF point = getCurrentPdfViewCtrlFragment().getPDFViewCtrl().getCurrentMousePosition();
/* 5172 */       if (point.x != 0.0F || point.y != 0.0F) {
/* 5173 */         setTabLayoutVisible(visible);
/*      */       }
/*      */     } 
/*      */     
/* 5177 */     if (this.mViewerConfig == null || this.mViewerConfig.isShowTopToolbar()) {
/* 5178 */       int duration = visible ? 250 : 250;
/* 5179 */       Transition slide = (new Slide(48)).setDuration(duration);
/* 5180 */       TransitionManager.beginDelayedTransition((ViewGroup)this.mAppBarLayout, slide);
/* 5181 */       if (visible) {
/* 5182 */         this.mAppBarLayout.setVisibility(0);
/*      */       } else {
/* 5184 */         this.mAppBarLayout.setVisibility(8);
/*      */       } 
/*      */     } else {
/* 5187 */       this.mAppBarLayout.setVisibility(8);
/*      */     } 
/* 5189 */     if (this.mAppBarVisibilityListener != null) {
/* 5190 */       this.mAppBarVisibilityListener.onAppBarVisibilityChanged(visible);
/*      */     }
/*      */   }
/*      */   
/*      */   @TargetApi(19)
/*      */   private void showSnackbar(String mainMessage, String actionMessage, View.OnClickListener clickListener) {
/* 5196 */     showSnackbar(mainMessage, actionMessage, clickListener, 0);
/*      */   }
/*      */   
/*      */   @TargetApi(19)
/*      */   private void showSnackbar(String mainMessage, String actionMessage, final View.OnClickListener clickListener, int duration) {
/* 5201 */     if (this.mTabHostListeners != null) {
/* 5202 */       for (TabHostListener listener : this.mTabHostListeners) {
/* 5203 */         if (!listener.canShowFileCloseSnackbar()) {
/*      */           return;
/*      */         }
/*      */       } 
/*      */     }
/* 5208 */     View snackbarHolderView = this.mFragmentView.findViewById(R.id.controls_pane_coordinator_layout);
/* 5209 */     final Snackbar snackbar = Snackbar.make(snackbarHolderView, mainMessage, duration);
/* 5210 */     if (actionMessage != null && clickListener != null) {
/* 5211 */       View.OnClickListener listener = new View.OnClickListener()
/*      */         {
/*      */           public void onClick(View v)
/*      */           {
/* 5215 */             snackbar.dismiss();
/* 5216 */             clickListener.onClick(v);
/*      */           }
/*      */         };
/*      */       
/* 5220 */       snackbar.setAction(actionMessage.toUpperCase(), listener);
/*      */     } 
/* 5222 */     snackbar.show();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void resumeFragment() {
/* 5229 */     if (this.mTabHostListeners != null) {
/* 5230 */       for (TabHostListener listener : this.mTabHostListeners) {
/* 5231 */         listener.onTabHostShown();
/*      */       }
/*      */     }
/*      */     
/* 5235 */     if (!this.mFragmentPaused) {
/*      */       return;
/*      */     }
/* 5238 */     this.mFragmentPaused = false;
/*      */     
/* 5240 */     if (sDebug) {
/* 5241 */       Log.d(TAG, "resume HostFragment");
/*      */     }
/*      */     
/* 5244 */     FragmentActivity fragmentActivity = getActivity();
/* 5245 */     if (fragmentActivity == null) {
/*      */       return;
/*      */     }
/*      */     
/* 5249 */     updateIconsInReflowMode();
/*      */     
/* 5251 */     if (PdfViewCtrlSettingsManager.getScreenStayLock((Context)fragmentActivity)) {
/* 5252 */       fragmentActivity.getWindow().addFlags(128);
/*      */     }
/*      */     
/* 5255 */     if (Utils.isPie() && PdfViewCtrlSettingsManager.getFullScreenMode((Context)fragmentActivity)) {
/* 5256 */       int mode = 1;
/* 5257 */       if (this.mViewerConfig != null) {
/* 5258 */         mode = this.mViewerConfig.getLayoutInDisplayCutoutMode();
/*      */       }
/* 5260 */       WindowManager.LayoutParams params = fragmentActivity.getWindow().getAttributes();
/* 5261 */       params.layoutInDisplayCutoutMode = mode;
/*      */     } 
/*      */     
/* 5264 */     updateFullScreenModeLayout();
/* 5265 */     showSystemUI();
/*      */     
/* 5267 */     if (this.mIsSearchMode)
/*      */     {
/* 5269 */       startSearchMode();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void pauseFragment() {
/* 5277 */     if (this.mTabHostListeners != null) {
/* 5278 */       for (TabHostListener listener : this.mTabHostListeners) {
/* 5279 */         listener.onTabHostHidden();
/*      */       }
/*      */     }
/*      */     
/* 5283 */     if (this.mFragmentPaused) {
/*      */       return;
/*      */     }
/* 5286 */     this.mFragmentPaused = true;
/*      */     
/* 5288 */     if (sDebug) {
/* 5289 */       Log.d(TAG, "pause HostFragment");
/*      */     }
/*      */     
/* 5292 */     FragmentActivity fragmentActivity = getActivity();
/* 5293 */     if (fragmentActivity == null) {
/*      */       return;
/*      */     }
/*      */     
/* 5297 */     stopHideToolbarsTimer();
/* 5298 */     if (this.mSearchToolbar != null) {
/* 5299 */       this.mSearchToolbar.pause();
/*      */     }
/*      */     
/* 5302 */     if (PdfViewCtrlSettingsManager.getScreenStayLock((Context)fragmentActivity)) {
/* 5303 */       fragmentActivity.getWindow().clearFlags(128);
/*      */     }
/*      */     
/* 5306 */     if (Utils.isPie() && PdfViewCtrlSettingsManager.getFullScreenMode((Context)fragmentActivity)) {
/*      */       
/* 5308 */       WindowManager.LayoutParams params = fragmentActivity.getWindow().getAttributes();
/* 5309 */       params.layoutInDisplayCutoutMode = 0;
/*      */     } 
/*      */ 
/*      */     
/* 5313 */     if (this.mAutoCropTask != null && this.mAutoCropTask.getStatus() == AsyncTask.Status.RUNNING) {
/* 5314 */       this.mAutoCropTask.cancel(true);
/* 5315 */       this.mAutoCropTaskPaused = true;
/* 5316 */       this.mAutoCropTaskTabTag = getCurrentTabTag();
/*      */     } else {
/* 5318 */       this.mAutoCropTaskPaused = false;
/*      */     } 
/*      */     
/* 5321 */     if (this.mUndoRedoPopupWindow != null && this.mUndoRedoPopupWindow.isShowing()) {
/* 5322 */       this.mUndoRedoPopupWindow.dismiss();
/*      */     }
/* 5324 */     if (this.mMenuSearch != null) {
/* 5325 */       this.mMenuSearch.getIcon().setAlpha(255);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLongPressEnabled(boolean enabled) {
/* 5335 */     PdfViewCtrlTabFragment tabFragment = getCurrentPdfViewCtrlFragment();
/* 5336 */     if (tabFragment != null) {
/* 5337 */       PDFViewCtrl pdfViewCtrl = tabFragment.getPDFViewCtrl();
/* 5338 */       if (pdfViewCtrl != null) {
/* 5339 */         pdfViewCtrl.setLongPressEnabled(enabled);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setupRedaction() {
/* 5345 */     FragmentActivity activity = getActivity();
/* 5346 */     if (activity != null) {
/* 5347 */       RedactionViewModel redactionViewModel = (RedactionViewModel)ViewModelProviders.of(activity).get(RedactionViewModel.class);
/* 5348 */       this.mDisposables.add(redactionViewModel.getObservable()
/* 5349 */           .subscribe(new Consumer<RedactionEvent>()
/*      */             {
/*      */               public void accept(RedactionEvent redactionEvent) throws Exception {
/* 5352 */                 if (redactionEvent.getEventType() == RedactionEvent.Type.REDACT_BY_SEARCH_OPEN_SHEET) {
/* 5353 */                   PdfViewCtrlTabFragment currentFragment = PdfViewCtrlTabHostFragment.this.getCurrentPdfViewCtrlFragment();
/* 5354 */                   if (currentFragment == null) {
/*      */                     return;
/*      */                   }
/* 5357 */                   PDFViewCtrl pdfViewCtrl = currentFragment.getPDFViewCtrl();
/* 5358 */                   if (pdfViewCtrl == null) {
/*      */                     return;
/*      */                   }
/* 5361 */                   int topMargin = PdfViewCtrlTabHostFragment.this.getToolbarHeight();
/* 5362 */                   if (PdfViewCtrlTabHostFragment.this.mViewerConfig != null && !PdfViewCtrlTabHostFragment.this.mViewerConfig.isAutoHideToolbarEnabled()) {
/* 5363 */                     topMargin = 0;
/*      */                   }
/* 5365 */                   SearchRedactionDialogFragment dialog = SearchRedactionDialogFragment.newInstance();
/* 5366 */                   dialog.setPdfViewCtrl(pdfViewCtrl);
/* 5367 */                   dialog.setStyle(0, R.style.CustomAppTheme);
/* 5368 */                   currentFragment.openRedactionSearchList(dialog, topMargin, PdfViewCtrlTabHostFragment.this.mSystemWindowInsetBottom);
/* 5369 */                 } else if (redactionEvent.getEventType() == RedactionEvent.Type.REDACT_BY_SEARCH_CLOSE_CLICKED) {
/* 5370 */                   PdfViewCtrlTabFragment currentFragment = PdfViewCtrlTabHostFragment.this.getCurrentPdfViewCtrlFragment();
/* 5371 */                   if (currentFragment == null) {
/*      */                     return;
/*      */                   }
/* 5374 */                   currentFragment.closeRedactionSearchList();
/*      */                 } 
/*      */               }
/*      */             }));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTabCount() {
/* 5388 */     if (this.mTabLayout == null) {
/* 5389 */       return 0;
/*      */     }
/* 5391 */     return this.mTabLayout.getTabCount();
/*      */   }
/*      */   
/*      */   public void updateToolbarDrawable() {
/* 5395 */     FragmentActivity fragmentActivity = getActivity();
/* 5396 */     if (fragmentActivity == null) {
/*      */       return;
/*      */     }
/* 5399 */     if (this.mToolbar != null) {
/* 5400 */       if (Utils.isLargeScreenWidth((Context)fragmentActivity) && null == this.mViewerConfig) {
/* 5401 */         this.mToolbar.setNavigationIcon(null);
/*      */       }
/* 5403 */       else if (this.mToolbarNavRes == 0) {
/* 5404 */         this.mToolbar.setNavigationIcon(null);
/*      */       } else {
/* 5406 */         this.mToolbar.setNavigationIcon(this.mToolbarNavRes);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean canRecreateActivity() {
/* 5413 */     boolean canRecreate = true;
/* 5414 */     if (this.mTabHostListeners != null) {
/* 5415 */       for (TabHostListener listener : this.mTabHostListeners) {
/* 5416 */         if (!listener.canRecreateActivity()) {
/* 5417 */           canRecreate = false;
/*      */         }
/*      */       } 
/*      */     }
/* 5421 */     return canRecreate;
/*      */   }
/*      */   
/*      */   private boolean useSupportActionBar() {
/* 5425 */     return (this.mViewerConfig == null || this.mViewerConfig.isUseSupportActionBar());
/*      */   }
/*      */   
/*      */   public static void setDebug(boolean debug) {
/* 5429 */     sDebug = debug;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   protected Class<? extends PdfViewCtrlTabFragment> getDefaultTabFragmentClass() {
/* 5440 */     return PdfViewCtrlTabFragment.class;
/*      */   }
/*      */   
/*      */   public static interface TabHostListener {
/*      */     void onTabHostShown();
/*      */     
/*      */     void onTabHostHidden();
/*      */     
/*      */     void onLastTabClosed();
/*      */     
/*      */     void onTabChanged(String param1String);
/*      */     
/*      */     void onOpenDocError();
/*      */     
/*      */     void onNavButtonPressed();
/*      */     
/*      */     void onShowFileInFolder(String param1String1, String param1String2, int param1Int);
/*      */     
/*      */     boolean canShowFileInFolder();
/*      */     
/*      */     boolean canShowFileCloseSnackbar();
/*      */     
/*      */     boolean onToolbarCreateOptionsMenu(Menu param1Menu, MenuInflater param1MenuInflater);
/*      */     
/*      */     boolean onToolbarPrepareOptionsMenu(Menu param1Menu);
/*      */     
/*      */     boolean onToolbarOptionsItemSelected(MenuItem param1MenuItem);
/*      */     
/*      */     void onStartSearchMode();
/*      */     
/*      */     void onExitSearchMode();
/*      */     
/*      */     boolean canRecreateActivity();
/*      */     
/*      */     void onTabPaused(FileInfo param1FileInfo, boolean param1Boolean);
/*      */     
/*      */     void onJumpToSdCardFolder();
/*      */     
/*      */     void onTabDocumentLoaded(String param1String);
/*      */   }
/*      */   
/*      */   public static interface AppBarVisibilityListener {
/*      */     void onAppBarVisibilityChanged(boolean param1Boolean);
/*      */   }
/*      */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\controls\PdfViewCtrlTabHostFragment.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */