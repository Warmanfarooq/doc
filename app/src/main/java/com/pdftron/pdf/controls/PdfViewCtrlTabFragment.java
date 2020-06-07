/*      */ package com.pdftron.pdf.controls;
/*      */ 
/*      */ import android.animation.Animator;
/*      */ import android.animation.TimeInterpolator;
/*      */ import android.annotation.TargetApi;
/*      */ import android.app.Activity;
/*      */ import android.app.AlertDialog;
/*      */ import android.app.ProgressDialog;
/*      */ import android.content.ContentResolver;
/*      */ import android.content.Context;
/*      */ import android.content.DialogInterface;
/*      */ import android.content.Intent;
/*      */ import android.content.res.Configuration;
/*      */ import android.graphics.Color;
/*      */ import android.graphics.PointF;
/*      */ import android.graphics.PorterDuff;
/*      */ import android.graphics.drawable.Drawable;
/*      */ import android.net.Uri;
/*      */ import android.os.AsyncTask;
/*      */ import android.os.Bundle;
/*      */ import android.os.Handler;
/*      */ import android.os.Looper;
/*      */ import android.os.ParcelFileDescriptor;
/*      */ import android.os.Parcelable;
/*      */ import android.os.SystemClock;
/*      */ import android.text.Editable;
/*      */ import android.text.SpannableString;
/*      */ import android.text.TextWatcher;
/*      */ import android.text.method.HideReturnsTransformationMethod;
/*      */ import android.text.method.PasswordTransformationMethod;
/*      */ import android.text.method.TransformationMethod;
/*      */ import android.text.style.ImageSpan;
/*      */ import android.util.DisplayMetrics;
/*      */ import android.util.Log;
/*      */ import android.util.Pair;
/*      */ import android.view.Display;
/*      */ import android.view.KeyEvent;
/*      */ import android.view.LayoutInflater;
/*      */ import android.view.MotionEvent;
/*      */ import android.view.PointerIcon;
/*      */ import android.view.View;
/*      */ import android.view.ViewGroup;
/*      */ import android.view.ViewPropertyAnimator;
/*      */ import android.view.ViewStub;
/*      */ import android.view.animation.AccelerateInterpolator;
/*      */ import android.view.animation.DecelerateInterpolator;
/*      */ import android.view.inputmethod.InputMethodManager;
/*      */ import android.webkit.MimeTypeMap;
/*      */ import android.webkit.URLUtil;
/*      */ import android.widget.CheckBox;
/*      */ import android.widget.CompoundButton;
/*      */ import android.widget.EditText;
/*      */ import android.widget.FrameLayout;
/*      */ import android.widget.ProgressBar;
/*      */ import android.widget.TextView;
/*      */ import androidx.annotation.NonNull;
/*      */ import androidx.annotation.Nullable;
/*      */ import androidx.fragment.app.DialogFragment;
/*      */ import androidx.fragment.app.Fragment;
/*      */ import androidx.fragment.app.FragmentActivity;
/*      */ import androidx.fragment.app.FragmentManager;
/*      */ import androidx.fragment.app.FragmentTransaction;
/*      */ import androidx.lifecycle.ViewModelProviders;
/*      */ import androidx.transition.ChangeBounds;
/*      */ import androidx.transition.Fade;
/*      */ import androidx.transition.Slide;
/*      */ import androidx.transition.Transition;
/*      */ import androidx.transition.TransitionManager;
/*      */ import androidx.transition.TransitionSet;
/*      */ import androidx.viewpager.widget.ViewPager;
/*      */ import com.google.android.material.floatingactionbutton.FloatingActionButton;
/*      */ import com.pdftron.common.PDFNetException;
/*      */ import com.pdftron.common.RecentlyUsedCache;
/*      */ import com.pdftron.filters.Filter;
/*      */ import com.pdftron.filters.MappedFile;
/*      */ import com.pdftron.filters.SecondaryFileFilter;
/*      */ import com.pdftron.pdf.Annot;
/*      */ import com.pdftron.pdf.ColorPt;
/*      */ import com.pdftron.pdf.ConversionOptions;
/*      */ import com.pdftron.pdf.Convert;
/*      */ import com.pdftron.pdf.DocumentConversion;
/*      */ import com.pdftron.pdf.OfficeToPDFOptions;
/*      */ import com.pdftron.pdf.PDFDoc;
/*      */ import com.pdftron.pdf.PDFNet;
/*      */ import com.pdftron.pdf.PDFViewCtrl;
/*      */ import com.pdftron.pdf.Page;
/*      */ import com.pdftron.pdf.PageIterator;
/*      */ import com.pdftron.pdf.Print;
/*      */ import com.pdftron.pdf.Rect;
/*      */ import com.pdftron.pdf.TextSearchResult;
/*      */ import com.pdftron.pdf.annots.FileAttachment;
/*      */ import com.pdftron.pdf.asynctask.GetTextInPageTask;
/*      */ import com.pdftron.pdf.asynctask.PDFDocLoaderTask;
/*      */ import com.pdftron.pdf.config.PDFViewCtrlConfig;
/*      */ import com.pdftron.pdf.config.ToolManagerBuilder;
/*      */ import com.pdftron.pdf.config.ViewerConfig;
/*      */ import com.pdftron.pdf.dialog.BookmarksDialogFragment;
/*      */ import com.pdftron.pdf.dialog.OptimizeDialogFragment;
/*      */ import com.pdftron.pdf.dialog.PortfolioDialogFragment;
/*      */ import com.pdftron.pdf.dialog.pagelabel.PageLabelUtils;
/*      */ import com.pdftron.pdf.dialog.redaction.SearchRedactionDialogFragment;
/*      */ import com.pdftron.pdf.model.ExternalFileInfo;
/*      */ import com.pdftron.pdf.model.FileInfo;
/*      */ import com.pdftron.pdf.model.OptimizeParams;
/*      */ import com.pdftron.pdf.model.PdfViewCtrlTabInfo;
/*      */ import com.pdftron.pdf.tools.AnnotEdit;
/*      */ import com.pdftron.pdf.tools.AnnotEditLine;
/*      */ import com.pdftron.pdf.tools.QuickMenu;
/*      */ import com.pdftron.pdf.tools.QuickMenuItem;
/*      */ import com.pdftron.pdf.tools.R;
/*      */ import com.pdftron.pdf.tools.TextSelect;
/*      */ import com.pdftron.pdf.tools.Tool;
/*      */ import com.pdftron.pdf.tools.ToolManager;
/*      */ import com.pdftron.pdf.tools.UndoRedoManager;
/*      */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*      */ import com.pdftron.pdf.utils.AnalyticsParam;
/*      */ import com.pdftron.pdf.utils.AppUtils;
/*      */ import com.pdftron.pdf.utils.BasicHTTPDownloadTask;
/*      */ import com.pdftron.pdf.utils.BasicHeadRequestTask;
/*      */ import com.pdftron.pdf.utils.BookmarkManager;
/*      */ import com.pdftron.pdf.utils.CommonToast;
/*      */ import com.pdftron.pdf.utils.DialogGoToPage;
/*      */ import com.pdftron.pdf.utils.FileInfoManager;
/*      */ import com.pdftron.pdf.utils.ImageMemoryCache;
/*      */ import com.pdftron.pdf.utils.Logger;
/*      */ import com.pdftron.pdf.utils.PageBackButtonInfo;
/*      */ import com.pdftron.pdf.utils.PathPool;
/*      */ import com.pdftron.pdf.utils.PdfViewCtrlSettingsManager;
/*      */ import com.pdftron.pdf.utils.PdfViewCtrlTabsManager;
/*      */ import com.pdftron.pdf.utils.RecentFilesManager;
/*      */ import com.pdftron.pdf.utils.ShortcutHelper;
/*      */ import com.pdftron.pdf.utils.UserCropUtilities;
/*      */ import com.pdftron.pdf.utils.Utils;
/*      */ import com.pdftron.pdf.utils.ViewerUtils;
/*      */ import com.pdftron.pdf.utils.cache.UriCacheManager;
/*      */ import com.pdftron.pdf.viewmodel.RichTextEvent;
/*      */ import com.pdftron.pdf.viewmodel.RichTextViewModel;
/*      */ import com.pdftron.pdf.widget.ContentLoadingRelativeLayout;
/*      */ import com.pdftron.pdf.widget.richtext.RCContainer;
/*      */ import com.pdftron.sdf.Doc;
/*      */ import com.pdftron.sdf.SDFDoc;
/*      */ import io.reactivex.Single;
/*      */ import io.reactivex.SingleEmitter;
/*      */ import io.reactivex.SingleObserver;
/*      */ import io.reactivex.SingleOnSubscribe;
/*      */ import io.reactivex.android.schedulers.AndroidSchedulers;
/*      */ import io.reactivex.disposables.CompositeDisposable;
/*      */ import io.reactivex.disposables.Disposable;
/*      */ import io.reactivex.functions.Consumer;
/*      */ import io.reactivex.observers.DisposableSingleObserver;
/*      */ import io.reactivex.schedulers.Schedulers;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.io.RandomAccessFile;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.net.URI;
/*      */ import java.net.URISyntaxException;
/*      */ import java.net.URLEncoder;
/*      */ import java.util.ArrayDeque;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Deque;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.concurrent.Callable;
/*      */ import org.apache.commons.io.FilenameUtils;
/*      */ import org.apache.commons.io.IOUtils;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class PdfViewCtrlTabFragment
/*      */   extends Fragment
/*      */   implements PDFViewCtrl.PageChangeListener, PDFViewCtrl.DocumentDownloadListener, PDFViewCtrl.UniversalDocumentConversionListener, PDFViewCtrl.DocumentLoadListener, PDFViewCtrl.RenderingListener, PDFViewCtrl.UniversalDocumentProgressIndicatorListener, ToolManager.PreToolManagerListener, ToolManager.QuickMenuListener, ToolManager.AnnotationModificationListener, ToolManager.PdfDocModificationListener, ToolManager.BasicAnnotationListener, ToolManager.OnGenericMotionEventListener, ToolManager.ToolChangedListener, ToolManager.AdvancedAnnotationListener, ReflowControl.OnReflowTapListener, ThumbnailSlider.OnThumbnailSliderTrackingListener, UndoRedoPopupWindow.OnUndoRedoListener, PortfolioDialogFragment.PortfolioDialogFragmentListener
/*      */ {
/*  221 */   private static final String TAG = PdfViewCtrlTabFragment.class.getName();
/*      */   
/*      */   protected static boolean sDebug;
/*      */   
/*      */   public static final String BUNDLE_TAB_TAG = "bundle_tab_tag";
/*      */   
/*      */   public static final String BUNDLE_TAB_TITLE = "bundle_tab_title";
/*      */   
/*      */   public static final String BUNDLE_TAB_FILE_EXTENSION = "bundle_tab_file_extension";
/*      */   
/*      */   public static final String BUNDLE_TAB_PASSWORD = "bundle_tab_password";
/*      */   
/*      */   public static final String BUNDLE_TAB_ITEM_SOURCE = "bundle_tab_item_source";
/*      */   
/*      */   public static final String BUNDLE_TAB_CONTENT_LAYOUT = "bundle_tab_content_layout";
/*      */   
/*      */   public static final String BUNDLE_TAB_PDFVIEWCTRL_ID = "bundle_tab_pdfviewctrl_id";
/*      */   
/*      */   public static final String BUNDLE_TAB_CONFIG = "bundle_tab_config";
/*      */   
/*      */   public static final String BUNDLE_TAB_CUSTOM_HEADERS = "bundle_tab_custom_headers";
/*      */   
/*      */   private File mCacheFolder;
/*      */   
/*      */   private static final String BUNDLE_OUTPUT_FILE_URI = "output_file_uri";
/*      */   
/*      */   private static final String BUNDLE_IMAGE_STAMP_TARGET_POINT = "image_stamp_target_point";
/*      */   
/*      */   private static final String BUNDLE_ANNOTATION_TOOLBAR_SHOW = "bundle_annotation_toolbar_show";
/*      */   
/*      */   private static final String BUNDLE_ANNOTATION_TOOLBAR_TOOL_MODE = "bundle_annotation_toolbar_tool_mode";
/*      */   protected static final int MAX_SIZE_PAGE_BACK_BUTTON_STACK = 50;
/*      */   protected static final float TAP_REGION_THRESHOLD = 0.14285715F;
/*      */   protected static final int HIDE_PAGE_NUMBER_INDICATOR = 5000;
/*      */   protected static final int MAX_CONVERSION_TIME_WITHOUT_NOTIFICATION = 20000;
/*      */   protected static final int SAVE_DOC_INTERVAL = 30000;
/*      */   protected static final int FORCE_SAVE_DOC_INTERVAL = 120000;
/*      */   public static final int ANIMATE_DURATION_SHOW = 250;
/*      */   public static final int ANIMATE_DURATION_HIDE = 250;
/*      */   protected View mOverlayStub;
/*      */   protected ThumbnailSlider mBottomNavBar;
/*      */   protected ContentLoadingRelativeLayout mProgressBarLayout;
/*      */   protected AnnotationToolbar mAnnotationToolbar;
/*      */   protected ViewGroup mViewerHost;
/*      */   protected View mPasswordLayout;
/*      */   protected EditText mPasswordInput;
/*      */   protected CheckBox mPasswordCheckbox;
/*      */   protected PageIndicatorLayout mPageNumberIndicator;
/*      */   protected ProgressBar mPageNumberIndicatorSpinner;
/*      */   protected TextView mPageNumberIndicatorAll;
/*      */   protected FindTextOverlay mSearchOverlay;
/*      */   protected FloatingActionButton mPageBackButton;
/*      */   protected FloatingActionButton mPageForwardButton;
/*      */   protected String mOpenUrlLink;
/*      */   protected String mTabTag;
/*      */   protected String mTabTitle;
/*      */   protected String mFileExtension;
/*      */   protected String mPassword;
/*      */   protected int mTabSource;
/*      */   protected int mContentLayout;
/*      */   protected int mPdfViewCtrlId;
/*      */   @Nullable
/*      */   protected ViewerConfig mViewerConfig;
/*      */   @Nullable
/*      */   protected JSONObject mCustomHeaders;
/*      */   private GetTextInPageTask mGetTextInPageTask;
/*      */   private PDFDocLoaderTask mPDFDocLoaderTask;
/*      */   protected Deque<PageBackButtonInfo> mPageBackStack;
/*      */   protected Deque<PageBackButtonInfo> mPageForwardStack;
/*  290 */   protected Boolean mInternalLinkClicked = Boolean.valueOf(false);
/*      */   protected PageBackButtonInfo mPreviousPageInfo;
/*      */   protected PageBackButtonInfo mCurrentPageInfo;
/*  293 */   protected Boolean mPushNextPageOnStack = Boolean.valueOf(false);
/*      */   
/*      */   protected DocumentConversion mDocumentConversion;
/*      */   
/*      */   protected boolean mHasWarnedAboutCanNotEditDuringConversion;
/*      */   
/*      */   protected boolean mShouldNotifyWhenConversionFinishes;
/*      */   
/*      */   protected String mTabConversionTempPath;
/*      */   protected boolean mUniversalConverted;
/*      */   protected View mRootView;
/*      */   protected View mStubPDFViewCtrl;
/*      */   protected FrameLayout mNavigationList;
/*      */   protected PDFViewCtrl mPdfViewCtrl;
/*      */   protected ToolManager mToolManager;
/*      */   protected PDFDoc mPdfDoc;
/*      */   protected boolean mIsEncrypted;
/*      */   protected boolean mIsOfficeDoc;
/*      */   protected boolean mIsOfficeDocReady;
/*      */   protected long mLastSuccessfulSave;
/*      */   protected boolean mDocumentLoading;
/*      */   protected boolean mDocumentLoaded;
/*      */   protected boolean mWaitingForSetPage;
/*      */   protected int mWaitingForSetPageNum;
/*  317 */   protected int mErrorCode = 0;
/*  318 */   protected int mDocumentState = 0;
/*      */   
/*      */   protected boolean mHasChangesSinceOpened;
/*      */   protected boolean mHasChangesSinceResumed;
/*      */   protected boolean mWasSavedAndClosedShown;
/*      */   protected ProgressDialog mDownloadDocumentDialog;
/*      */   protected boolean mDownloading;
/*      */   protected File mCurrentFile;
/*      */   protected Uri mCurrentUriFile;
/*  327 */   protected long mOriginalFileLength = -1L;
/*      */   
/*      */   protected boolean mNeedsCleanupFile = true;
/*      */   
/*      */   protected boolean mPrintDocumentChecked = true;
/*      */   protected boolean mPrintAnnotationsChecked = true;
/*      */   protected boolean mPrintSummaryChecked;
/*      */   protected int mPageCount;
/*      */   protected boolean mIsRtlMode;
/*      */   protected ReflowControl mReflowControl;
/*      */   protected boolean mIsReflowMode;
/*      */   protected boolean mUsedCacheCalled;
/*  339 */   protected int mSpinnerSize = 96;
/*      */   protected ProgressBar mUniversalDocSpinner;
/*      */   protected boolean mAnnotNotAddedDialogShown;
/*  342 */   protected final Object saveDocumentLock = new Object();
/*      */   
/*      */   protected boolean mCanAddToTabInfo = true;
/*      */   
/*      */   protected boolean mErrorOnOpeningDocument;
/*      */   
/*      */   protected boolean mLocalReadOnlyChecked;
/*      */   
/*      */   protected boolean mColorModeChanged;
/*      */   
/*      */   protected boolean mAnnotationSelected;
/*      */   
/*      */   protected Annot mSelectedAnnot;
/*      */   
/*      */   protected boolean mIsPageNumberIndicatorConversionSpinningRunning;
/*      */   
/*      */   protected boolean mInSearchMode;
/*      */   
/*      */   protected int mBookmarkDialogCurrentTab;
/*      */   protected boolean mToolbarOpenedFromMouseMovement;
/*      */   protected TabListener mTabListener;
/*      */   protected ArrayList<AnnotationToolbar.AnnotationToolbarListener> mAnnotationToolbarListeners;
/*      */   protected ArrayList<ToolManager.QuickMenuListener> mQuickMenuListeners;
/*      */   protected Uri mOutputFileUri;
/*      */   protected PointF mAnnotTargetPoint;
/*      */   protected int mAnnotTargetPage;
/*      */   protected Intent mAnnotIntentData;
/*      */   protected Long mTargetWidget;
/*      */   protected ToolManager.ToolMode mImageCreationMode;
/*      */   protected boolean mImageStampDelayCreation = false;
/*      */   protected boolean mImageSignatureDelayCreation = false;
/*      */   protected boolean mFileAttachmentDelayCreation = false;
/*      */   private boolean mScreenshotTempFileCreated = false;
/*  375 */   private String mScreenshotTempFilePath = null;
/*      */   
/*  377 */   private int mAnnotationToolbarMode = 0;
/*      */   private boolean mAnnotationToolbarShow;
/*  379 */   private ToolManager.ToolMode mAnnotationToolbarToolMode = null;
/*      */ 
/*      */   
/*  382 */   private int mRageScrollingCount = 0;
/*      */   
/*      */   private boolean mRageScrollingAsked;
/*      */   
/*      */   private boolean mOnUpCalled;
/*      */   
/*      */   private static final int RAGE_SCROLLING_COUNT_MAX = 3;
/*      */   
/*      */   protected boolean mShowingSpecialFileAlertDialog = false;
/*      */   protected AlertDialog mSpecialFileAlertDialog;
/*  392 */   protected String mSelectedFileAttachmentName = null;
/*      */ 
/*      */   
/*  395 */   protected CompositeDisposable mDisposables = new CompositeDisposable();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Single<File> mTempDownloadObservable;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean mSavingEnabled = true;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean mStateEnabled = true;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private enum RegionSingleTap
/*      */   {
/*  582 */     Left,
/*  583 */     Middle,
/*  584 */     Right;
/*      */   }
/*      */ 
/*      */   
/*  588 */   protected Handler mRequestSaveDocHandler = new Handler(Looper.getMainLooper());
/*  589 */   protected Runnable mTickSaveDocCallback = new Runnable()
/*      */     {
/*      */       public void run() {
/*  592 */         if (PdfViewCtrlTabFragment.this.isNotPdf()) {
/*      */           return;
/*      */         }
/*      */         
/*  596 */         if (PdfViewCtrlTabFragment.this.mPdfViewCtrl != null) {
/*  597 */           long currentTime = System.currentTimeMillis();
/*  598 */           boolean needsForceSave = false;
/*  599 */           if (currentTime - PdfViewCtrlTabFragment.this.mLastSuccessfulSave > 120000L)
/*      */           {
/*      */             
/*  602 */             needsForceSave = true;
/*      */           }
/*  604 */           PdfViewCtrlTabFragment.this.save(false, needsForceSave, false);
/*      */         } 
/*  606 */         PdfViewCtrlTabFragment.this.postTickSaveDoc();
/*      */       }
/*      */     };
/*      */   
/*      */   protected void postTickSaveDoc() {
/*  611 */     if (this.mRequestSaveDocHandler != null) {
/*  612 */       this.mRequestSaveDocHandler.postDelayed(this.mTickSaveDocCallback, 30000L);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*  618 */   private Handler mHidePageNumberAndPageBackButtonHandler = new Handler(Looper.getMainLooper());
/*  619 */   private Runnable mHidePageNumberAndPageBackButtonRunnable = new Runnable()
/*      */     {
/*      */       public void run() {
/*  622 */         PdfViewCtrlTabFragment.this.hidePageNumberIndicator();
/*      */       }
/*      */     };
/*      */   
/*  626 */   private Handler mPageNumberIndicatorConversionSpinningHandler = new Handler(Looper.getMainLooper());
/*  627 */   private Runnable mPageNumberIndicatorConversionSpinnerRunnable = new Runnable()
/*      */     {
/*      */       public void run() {
/*  630 */         if (PdfViewCtrlTabFragment.this.getActivity() != null && PdfViewCtrlTabFragment.this.mPageNumberIndicatorSpinner != null)
/*  631 */           PdfViewCtrlTabFragment.this.mPageNumberIndicatorSpinner.setVisibility(0); 
/*      */       }
/*      */     };
/*      */   
/*  635 */   private Handler mConversionFinishedMessageHandler = new Handler(Looper.getMainLooper());
/*  636 */   private Runnable mConversionFinishedMessageRunnable = new Runnable()
/*      */     {
/*      */       public void run() {
/*  639 */         if (PdfViewCtrlTabFragment.this.mDocumentConversion != null) {
/*  640 */           PdfViewCtrlTabFragment.this.mShouldNotifyWhenConversionFinishes = true;
/*      */         }
/*      */       }
/*      */     };
/*      */   
/*  645 */   private Handler mResetTextSelectionHandler = new Handler(Looper.getMainLooper());
/*  646 */   private Runnable mResetTextSelectionRunnable = new Runnable()
/*      */     {
/*      */       public void run() {
/*  649 */         FragmentActivity fragmentActivity = PdfViewCtrlTabFragment.this.getActivity();
/*  650 */         if (fragmentActivity == null || fragmentActivity.isFinishing() || PdfViewCtrlTabFragment.this.mToolManager == null) {
/*      */           return;
/*      */         }
/*  653 */         ToolManager.Tool tool = PdfViewCtrlTabFragment.this.mToolManager.getTool();
/*  654 */         if (tool instanceof TextSelect) {
/*  655 */           ((TextSelect)tool).resetSelection();
/*      */         }
/*      */       }
/*      */     };
/*      */   
/*  660 */   private final ReflowControl.OnPostProcessColorListener mOnPostProcessColorListener = new ReflowControl.OnPostProcessColorListener()
/*      */     {
/*      */       public ColorPt getPostProcessedColor(ColorPt cp)
/*      */       {
/*  664 */         if (PdfViewCtrlTabFragment.this.mPdfViewCtrl != null) {
/*  665 */           return PdfViewCtrlTabFragment.this.mPdfViewCtrl.getPostProcessedColor(cp);
/*      */         }
/*  667 */         return cp;
/*      */       }
/*      */     };
/*      */ 
/*      */   
/*      */   public class SaveFolderWrapper
/*      */   {
/*      */     private File mLocalCopyFile;
/*      */     
/*      */     private File mExternalTempFile;
/*      */     
/*      */     private ExternalFileInfo mExternalCopyFile;
/*      */     
/*      */     public SaveFolderWrapper(ExternalFileInfo ext, String suffix) {
/*  681 */       this(ext, true, suffix);
/*      */     }
/*      */     
/*      */     public SaveFolderWrapper(File local, String suffix) {
/*  685 */       this(local, true, suffix);
/*      */     }
/*      */     
/*      */     public SaveFolderWrapper(ExternalFileInfo ext, boolean needsCopy) {
/*  689 */       this(ext, needsCopy, (String)null);
/*      */     }
/*      */     
/*      */     public SaveFolderWrapper(File local, boolean needsCopy) {
/*  693 */       this(local, needsCopy, (String)null);
/*      */     }
/*      */     
/*      */     public SaveFolderWrapper(ExternalFileInfo ext, boolean needsCopy, String suffix) {
/*  697 */       this.mExternalTempFile = null;
/*  698 */       FragmentActivity fragmentActivity = PdfViewCtrlTabFragment.this.getActivity();
/*  699 */       if (fragmentActivity == null || ext == null || !Utils.isKitKat()) {
/*      */         return;
/*      */       }
/*  702 */       String copyFileName = Utils.getFileNameNotInUse(ext, getFileName(needsCopy, suffix));
/*  703 */       this.mExternalCopyFile = ext.createFile("application/pdf", copyFileName);
/*      */       try {
/*  705 */         this.mExternalTempFile = File.createTempFile("tmp", ".pdf", fragmentActivity.getFilesDir());
/*  706 */       } catch (Exception e) {
/*  707 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */       } 
/*      */     }
/*      */     
/*      */     public SaveFolderWrapper(File local, boolean needsCopy, String suffix) {
/*  712 */       File tempFile = new File(local, getFileName(needsCopy, suffix));
/*  713 */       String localCopyPath = Utils.getFileNameNotInUse(tempFile.getAbsolutePath());
/*  714 */       this.mLocalCopyFile = new File(localCopyPath);
/*      */     }
/*      */     
/*      */     public String getFileName(boolean needsCopy, String suffix) {
/*  718 */       String extension = ".pdf";
/*  719 */       if (needsCopy) {
/*  720 */         if (suffix == null) {
/*  721 */           suffix = "Copy";
/*      */         }
/*  723 */         extension = "-" + suffix + extension;
/*      */       } 
/*  725 */       return PdfViewCtrlTabFragment.this.mTabTitle + extension;
/*      */     }
/*      */     
/*      */     public PDFDoc getDoc() {
/*  729 */       if (this.mLocalCopyFile != null) {
/*  730 */         PdfViewCtrlTabFragment.this.copyFileSourceToTempFile(this.mLocalCopyFile);
/*  731 */       } else if (this.mExternalTempFile != null) {
/*  732 */         PdfViewCtrlTabFragment.this.copyFileSourceToTempFile(this.mExternalTempFile);
/*      */       } 
/*  734 */       PDFDoc copyDoc = null;
/*      */       try {
/*  736 */         if (this.mLocalCopyFile != null) {
/*  737 */           copyDoc = new PDFDoc(this.mLocalCopyFile.getAbsolutePath());
/*  738 */         } else if (getNewExternalUri() != null && 
/*  739 */           this.mExternalTempFile != null) {
/*  740 */           copyDoc = new PDFDoc(this.mExternalTempFile.getAbsolutePath());
/*      */         } 
/*      */         
/*  743 */         if (copyDoc != null && 
/*  744 */           null != PdfViewCtrlTabFragment.this.mPassword) {
/*  745 */           copyDoc.initStdSecurityHandler(PdfViewCtrlTabFragment.this.mPassword);
/*      */         }
/*      */       }
/*  748 */       catch (Exception e) {
/*  749 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*  750 */         copyDoc = null;
/*      */       } 
/*  752 */       return copyDoc;
/*      */     }
/*      */     
/*      */     public Pair<Boolean, String> save(PDFDoc doc) {
/*  756 */       return save(doc, true);
/*      */     }
/*      */     
/*      */     public Pair<Boolean, String> save(PDFDoc doc, boolean closeDoc) {
/*  760 */       if (PdfViewCtrlTabFragment.this.getActivity() == null) {
/*  761 */         return null;
/*      */       }
/*  763 */       boolean shouldUnlock = false;
/*  764 */       SecondaryFileFilter filter = null;
/*      */       try {
/*  766 */         if (this.mExternalCopyFile != null) {
/*  767 */           filter = new SecondaryFileFilter((Context)PdfViewCtrlTabFragment.this.getActivity(), this.mExternalCopyFile.getUri());
/*  768 */           doc.lock();
/*  769 */           shouldUnlock = true;
/*  770 */           doc.save((Filter)filter, SDFDoc.SaveMode.REMOVE_UNUSED);
/*  771 */           return new Pair(Boolean.valueOf(false), this.mExternalCopyFile.getUri().toString());
/*  772 */         }  if (this.mLocalCopyFile != null) {
/*  773 */           doc.lock();
/*  774 */           shouldUnlock = true;
/*  775 */           doc.save(this.mLocalCopyFile.getAbsolutePath(), SDFDoc.SaveMode.REMOVE_UNUSED, null);
/*  776 */           return new Pair(Boolean.valueOf(true), this.mLocalCopyFile.getAbsolutePath());
/*      */         } 
/*  778 */       } catch (Exception e) {
/*  779 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */       } finally {
/*  781 */         if (shouldUnlock) {
/*  782 */           Utils.unlockQuietly(doc);
/*      */         }
/*  784 */         if (closeDoc) {
/*  785 */           Utils.closeQuietly(doc);
/*      */         }
/*  787 */         Utils.closeQuietly(filter);
/*  788 */         cleanup();
/*      */       } 
/*  790 */       return null;
/*      */     }
/*      */     
/*      */     public void openInNewTab() {
/*  794 */       if (this.mExternalCopyFile != null) {
/*  795 */         PdfViewCtrlTabFragment.this.openFileUriInNewTab(this.mExternalCopyFile.getUri());
/*      */       } else {
/*  797 */         PdfViewCtrlTabFragment.this.openFileInNewTab(this.mLocalCopyFile);
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean isLocal() {
/*  802 */       return (this.mLocalCopyFile != null);
/*      */     }
/*      */     
/*      */     public Uri getNewExternalUri() {
/*  806 */       return (this.mExternalCopyFile != null) ? this.mExternalCopyFile.getUri() : null;
/*      */     }
/*      */     
/*      */     public File getNewLocalFile() {
/*  810 */       return this.mLocalCopyFile;
/*      */     }
/*      */     
/*      */     public String getNewTabTag() {
/*  814 */       if (this.mExternalCopyFile != null) {
/*  815 */         return this.mExternalCopyFile.getUri().toString();
/*      */       }
/*  817 */       return (this.mLocalCopyFile != null) ? this.mLocalCopyFile.getAbsolutePath() : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public String getNewTabTitle() {
/*  822 */       if (this.mExternalCopyFile != null) {
/*  823 */         return this.mExternalCopyFile.getFileName();
/*      */       }
/*  825 */       return (this.mLocalCopyFile != null) ? this.mLocalCopyFile.getName() : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getNewTabType() {
/*  830 */       if (this.mExternalCopyFile != null) {
/*  831 */         return 6;
/*      */       }
/*  833 */       return 2;
/*      */     }
/*      */ 
/*      */     
/*      */     public void cleanup() {
/*  838 */       if (this.mExternalTempFile != null)
/*      */       {
/*  840 */         this.mExternalTempFile.delete();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   protected boolean copyFileSourceToTempFile(File tempFile) {
/*  846 */     OutputStream fos = null;
/*      */     try {
/*  848 */       fos = new FileOutputStream(new File(tempFile.getAbsolutePath()));
/*  849 */       return copyFileSourceToOutputStream(getContext(), fos);
/*  850 */     } catch (Exception e) {
/*  851 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*  852 */       return false;
/*      */     } finally {
/*  854 */       IOUtils.closeQuietly(fos);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected boolean copyFileSourceToTempUri(Uri tempUri) {
/*  859 */     Context context = getContext();
/*  860 */     if (context == null) {
/*  861 */       return false;
/*      */     }
/*      */     
/*  864 */     OutputStream fos = null;
/*  865 */     ContentResolver contentResolver = Utils.getContentResolver(context);
/*  866 */     if (contentResolver == null) {
/*  867 */       return false;
/*      */     }
/*      */     try {
/*  870 */       fos = contentResolver.openOutputStream(tempUri);
/*  871 */       return copyFileSourceToOutputStream(context, fos);
/*  872 */     } catch (Exception e) {
/*  873 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*  874 */       return false;
/*      */     } finally {
/*  876 */       IOUtils.closeQuietly(fos);
/*      */     } 
/*      */   }
/*      */   protected boolean copyFileSourceToOutputStream(Context context, OutputStream fos) {
/*      */     ContentResolver contentResolver;
/*  881 */     if (context == null || fos == null) {
/*  882 */       return false;
/*      */     }
/*      */     
/*  885 */     InputStream is = null;
/*  886 */     boolean success = false;
/*  887 */     switch (this.mTabSource) {
/*      */       case 6:
/*      */       case 15:
/*  890 */         contentResolver = Utils.getContentResolver(context);
/*  891 */         if (contentResolver != null && null != this.mCurrentUriFile) {
/*      */           try {
/*  893 */             is = contentResolver.openInputStream(this.mCurrentUriFile);
/*  894 */           } catch (Exception e) {
/*  895 */             AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */           } 
/*      */         }
/*      */         break;
/*      */       case 2:
/*      */       case 5:
/*      */       case 13:
/*  902 */         if (null != this.mCurrentFile) {
/*      */           try {
/*  904 */             is = new FileInputStream(this.mCurrentFile);
/*  905 */           } catch (Exception e) {
/*  906 */             AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */           } 
/*      */         }
/*      */         break;
/*      */     } 
/*  911 */     if (null != is) {
/*      */       try {
/*  913 */         IOUtils.copy(is, fos);
/*  914 */         success = true;
/*  915 */       } catch (Exception e) {
/*  916 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */       } finally {
/*  918 */         Utils.closeQuietly(is);
/*      */       } 
/*      */     }
/*  921 */     return success;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onCreate(Bundle savedInstanceState) {
/*  929 */     if (sDebug) {
/*  930 */       Log.v("LifeCycle", "TabFragment.onCreate");
/*      */     }
/*  932 */     super.onCreate(savedInstanceState);
/*      */     
/*  934 */     FragmentActivity fragmentActivity = getActivity();
/*  935 */     if (fragmentActivity == null) {
/*      */       return;
/*      */     }
/*      */     
/*  939 */     if (savedInstanceState != null) {
/*  940 */       this.mOutputFileUri = (Uri)savedInstanceState.getParcelable("output_file_uri");
/*  941 */       this.mAnnotTargetPoint = (PointF)savedInstanceState.getParcelable("image_stamp_target_point");
/*  942 */       if (savedInstanceState.getBoolean("bundle_annotation_toolbar_show", false)) {
/*  943 */         this.mAnnotationToolbarShow = true;
/*  944 */         String mode = savedInstanceState.getString("bundle_annotation_toolbar_tool_mode", ToolManager.ToolMode.PAN.toString());
/*  945 */         this.mAnnotationToolbarToolMode = ToolManager.ToolMode.valueOf(mode);
/*      */       } 
/*      */     } 
/*      */     
/*  949 */     Bundle bundle = getArguments();
/*  950 */     if (bundle == null) {
/*  951 */       throw new NullPointerException("bundle cannot be null");
/*      */     }
/*      */     
/*  954 */     this
/*  955 */       .mCacheFolder = bundle.getBoolean("PdfViewCtrlTabFragment_bundle_cache_folder_uri", true) ? UriCacheManager.getCacheDir((Context)fragmentActivity) : Utils.getExternalDownloadDirectory((Context)fragmentActivity);
/*      */     
/*  957 */     this.mViewerConfig = (ViewerConfig)bundle.getParcelable("bundle_tab_config");
/*      */     
/*  959 */     String headerStr = bundle.getString("bundle_tab_custom_headers");
/*  960 */     if (headerStr != null) {
/*      */       try {
/*  962 */         this.mCustomHeaders = new JSONObject(headerStr);
/*  963 */       } catch (JSONException jSONException) {}
/*      */     }
/*      */ 
/*      */     
/*  967 */     this.mTabTag = bundle.getString("bundle_tab_tag");
/*  968 */     if (Utils.isNullOrEmpty(this.mTabTag)) {
/*  969 */       throw new NullPointerException("Tab tag cannot be null or empty");
/*      */     }
/*      */     
/*  972 */     this.mTabTitle = bundle.getString("bundle_tab_title");
/*  973 */     if (this.mTabTitle != null) {
/*  974 */       this.mTabTitle = this.mTabTitle.replaceAll("\\/", "-");
/*      */     }
/*      */     
/*  977 */     this.mFileExtension = bundle.getString("bundle_tab_file_extension");
/*      */     
/*  979 */     this.mPassword = bundle.getString("bundle_tab_password");
/*  980 */     if (Utils.isNullOrEmpty(this.mPassword)) {
/*  981 */       this.mPassword = Utils.getPassword((Context)fragmentActivity, this.mTabTag);
/*      */     }
/*      */     
/*  984 */     this.mTabSource = bundle.getInt("bundle_tab_item_source");
/*  985 */     if (this.mTabSource == 2) {
/*  986 */       this.mCurrentFile = new File(this.mTabTag);
/*      */     }
/*      */     
/*  989 */     this.mContentLayout = bundle.getInt("bundle_tab_content_layout", R.layout.controls_fragment_tabbed_pdfviewctrl_tab_content);
/*  990 */     this.mPdfViewCtrlId = bundle.getInt("bundle_tab_pdfviewctrl_id", R.id.pdfviewctrl);
/*      */     
/*  992 */     this.mPreviousPageInfo = new PageBackButtonInfo();
/*  993 */     this.mCurrentPageInfo = new PageBackButtonInfo();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
/* 1001 */     if (sDebug) {
/* 1002 */       Log.v("LifeCycle", "TabFragment.onCreateView");
/*      */     }
/* 1004 */     if (Utils.isNullOrEmpty(this.mTabTag)) {
/* 1005 */       throw new NullPointerException("Tab tag (file path) cannot be null or empty");
/*      */     }
/*      */     
/* 1008 */     int layoutResId = (this.mContentLayout == 0) ? R.layout.controls_fragment_tabbed_pdfviewctrl_tab_content : this.mContentLayout;
/* 1009 */     return inflater.inflate(layoutResId, container, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
/* 1017 */     if (sDebug) {
/* 1018 */       Log.v("LifeCycle", "TabFragment.onViewCreated");
/*      */     }
/* 1020 */     FragmentActivity fragmentActivity = getActivity();
/* 1021 */     if (fragmentActivity == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1025 */     this.mRootView = view;
/* 1026 */     loadPDFViewCtrlView();
/*      */     
/* 1028 */     initLayout();
/*      */     
/* 1030 */     setViewerHostVisible(false);
/* 1031 */     this.mViewerHost.setBackgroundColor(this.mPdfViewCtrl.getClientBackgroundColor());
/*      */     
/* 1033 */     this.mToolManager.setAdvancedAnnotationListener(this);
/*      */     
/* 1035 */     if (this.mViewerConfig == null) {
/* 1036 */       PDFNet.enableJavaScript(PdfViewCtrlSettingsManager.getEnableJavaScript((Context)fragmentActivity));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onResume() {
/* 1045 */     if (sDebug) {
/* 1046 */       Log.v("LifeCycle", "TabFragment.onResume");
/*      */     }
/* 1048 */     super.onResume();
/*      */     
/* 1050 */     if (isHidden()) {
/*      */       return;
/*      */     }
/*      */     
/* 1054 */     if (this.mToolManager != null) {
/* 1055 */       this.mToolManager.setCanResumePdfDocWithoutReloading(canResumeWithoutReloading());
/*      */     }
/*      */     
/* 1058 */     resumeFragment(true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onPause() {
/* 1066 */     if (sDebug) {
/* 1067 */       Log.v("LifeCycle", "TabFragment.onPause");
/*      */     }
/* 1069 */     pauseFragment();
/* 1070 */     forceSave();
/*      */     
/* 1072 */     super.onPause();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onStop() {
/* 1080 */     if (sDebug) {
/* 1081 */       Log.v("LifeCycle", "TabFragment.onStop");
/*      */     }
/* 1083 */     if (this.mTabSource == 5 && 
/* 1084 */       this.mDownloading) {
/* 1085 */       this.mDownloading = false;
/*      */ 
/*      */       
/* 1088 */       Utils.closeDocQuietly(this.mPdfViewCtrl);
/*      */       
/* 1090 */       if (this.mCurrentFile != null && this.mCurrentFile.exists())
/*      */       {
/* 1092 */         this.mCurrentFile.delete();
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1097 */     super.onStop();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDestroyView() {
/* 1105 */     if (sDebug) {
/* 1106 */       Log.v("LifeCycle", "TabFragment.onDestroyView");
/*      */     }
/* 1108 */     super.onDestroyView();
/*      */     
/* 1110 */     this.mDisposables.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDestroy() {
/* 1118 */     if (sDebug) {
/* 1119 */       Log.v("LifeCycle", "TabFragment.onDestroy");
/*      */     }
/* 1121 */     if (this.mReflowControl != null && this.mReflowControl.isReady()) {
/* 1122 */       this.mReflowControl.cleanUp();
/* 1123 */       this.mReflowControl.clearReflowOnTapListeners();
/* 1124 */       this.mReflowControl.clearOnPageChangeListeners();
/*      */     } 
/*      */ 
/*      */     
/* 1128 */     if (this.mToolManager != null) {
/* 1129 */       this.mToolManager.removeAnnotationModificationListener(this);
/* 1130 */       this.mToolManager.removePdfDocModificationListener(this);
/* 1131 */       this.mToolManager.removeToolChangedListener(this);
/*      */     } 
/*      */     
/* 1134 */     if (this.mPdfViewCtrl != null) {
/* 1135 */       this.mPdfViewCtrl.removeDocumentLoadListener(this);
/* 1136 */       this.mPdfViewCtrl.removePageChangeListener(this);
/* 1137 */       this.mPdfViewCtrl.removeDocumentDownloadListener(this);
/* 1138 */       this.mPdfViewCtrl.removeUniversalDocumentConversionListener(this);
/* 1139 */       this.mPdfViewCtrl.destroy();
/* 1140 */       this.mPdfViewCtrl = null;
/*      */     } 
/*      */     
/* 1143 */     if (this.mPdfDoc != null) {
/*      */       try {
/* 1145 */         this.mPdfDoc.close();
/* 1146 */       } catch (Exception e) {
/* 1147 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */       } finally {
/* 1149 */         this.mPdfDoc = null;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 1154 */     if (this.mTabConversionTempPath != null) {
/* 1155 */       File file = new File(this.mTabConversionTempPath);
/*      */       
/* 1157 */       file.delete();
/* 1158 */       this.mTabConversionTempPath = null;
/*      */     } 
/*      */     
/* 1161 */     if (this.mTabSource == 13 && this.mNeedsCleanupFile)
/*      */     {
/* 1163 */       cleanupTemporaryFile();
/*      */     }
/*      */     
/* 1166 */     if (this.mTabSource == 15 && this.mNeedsCleanupFile)
/*      */     {
/* 1168 */       cleanupTemporaryFile();
/*      */     }
/*      */     
/* 1171 */     super.onDestroy();
/*      */   }
/*      */   
/*      */   private void cleanupTemporaryFile() {
/* 1175 */     if (this.mTempDownloadObservable == null) {
/*      */       return;
/*      */     }
/* 1178 */     this.mTempDownloadObservable.subscribe(new SingleObserver<File>()
/*      */         {
/*      */           public void onSubscribe(Disposable d) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public void onSuccess(File file) {
/* 1187 */             if (file != null && file.exists()) {
/*      */               
/* 1189 */               String path = file.getAbsolutePath();
/* 1190 */               if (file.delete() && PdfViewCtrlTabFragment.sDebug) {
/* 1191 */                 Log.d(PdfViewCtrlTabFragment.TAG, "edit uri temp file deleted: " + path);
/*      */               }
/*      */             } 
/*      */           }
/*      */ 
/*      */           
/*      */           public void onError(Throwable e) {
/* 1198 */             Log.d(PdfViewCtrlTabFragment.TAG, "Error at: " + e);
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onHiddenChanged(boolean hidden) {
/* 1208 */     if (sDebug) {
/* 1209 */       Log.v("LifeCycle", "TabFragment.onHiddenChanged called with " + (hidden ? "Hidden" : "Visible") + " <" + this.mTabTag + ">");
/*      */     }
/* 1211 */     if (hidden) {
/* 1212 */       pauseFragment();
/*      */     } else {
/* 1214 */       resumeFragment(false);
/*      */     } 
/*      */     
/* 1217 */     super.onHiddenChanged(hidden);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onLowMemory() {
/* 1225 */     super.onLowMemory();
/*      */     
/* 1227 */     if (this.mPdfViewCtrl != null) {
/* 1228 */       this.mPdfViewCtrl.purgeMemoryDueToOOM();
/*      */     }
/* 1230 */     ImageMemoryCache.getInstance().clearAll();
/* 1231 */     PathPool.getInstance().clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onSaveInstanceState(@NonNull Bundle outState) {
/* 1239 */     super.onSaveInstanceState(outState);
/*      */     
/* 1241 */     if (this.mOutputFileUri != null) {
/* 1242 */       outState.putParcelable("output_file_uri", (Parcelable)this.mOutputFileUri);
/*      */     }
/* 1244 */     if (this.mAnnotTargetPoint != null) {
/* 1245 */       outState.putParcelable("image_stamp_target_point", (Parcelable)this.mAnnotTargetPoint);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1250 */     boolean showAnnotationToolbar = (this.mAnnotationToolbarMode == 0 && isAnnotationMode());
/* 1251 */     outState.putBoolean("bundle_annotation_toolbar_show", showAnnotationToolbar);
/* 1252 */     if (showAnnotationToolbar) {
/* 1253 */       ToolManager.ToolModeBase toolModeBase = this.mToolManager.getTool().getToolMode();
/* 1254 */       outState.putString("bundle_annotation_toolbar_tool_mode", toolModeBase.toString());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onConfigurationChanged(Configuration newConfig) {
/* 1263 */     super.onConfigurationChanged(newConfig);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1268 */     if (this.mPdfViewCtrl != null) {
/* 1269 */       this.mPdfViewCtrl.onConfigurationChanged(newConfig);
/* 1270 */       updateZoomLimits();
/*      */     } 
/*      */     
/* 1273 */     if (isAnnotationMode()) {
/* 1274 */       this.mAnnotationToolbar.onConfigurationChanged(newConfig);
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
/*      */   public void toolChanged(ToolManager.Tool newTool, ToolManager.Tool oldTool) {
/* 1286 */     this.mRageScrollingCount = 0;
/* 1287 */     if (newTool != null && newTool.getToolMode().equals(ToolManager.ToolMode.FORM_FILL) && 
/* 1288 */       this.mTabListener != null) {
/* 1289 */       this.mTabListener.setToolbarsVisible(false);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void fileCreated(String filePath, AnnotAction action) {
/*      */     Intent shareIntent;
/* 1296 */     switch (action) {
/*      */       case OPEN_TOOLBAR:
/* 1298 */         this.mScreenshotTempFileCreated = true;
/* 1299 */         this.mScreenshotTempFilePath = filePath;
/* 1300 */         this.mToolManager.deselectAll();
/* 1301 */         shareIntent = Utils.createShareIntentForFile(getContext(), filePath, "image/png");
/* 1302 */         startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.tools_screenshot_share_intent_title)));
/*      */         break;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void fileAttachmentSelected(FileAttachment attachment) {
/* 1314 */     FragmentActivity fragmentActivity = getActivity();
/* 1315 */     if (fragmentActivity == null) {
/*      */       return;
/*      */     }
/* 1318 */     if (attachment == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1322 */     if (this.mPdfViewCtrl == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1326 */     String attachmentPath = ViewerUtils.exportFileAttachment(this.mPdfViewCtrl, attachment, getExportDirectory());
/* 1327 */     if (null == attachmentPath) {
/*      */       return;
/*      */     }
/*      */     
/* 1331 */     File attachmentFile = new File(attachmentPath);
/* 1332 */     String extension = Utils.getExtension(attachmentPath);
/* 1333 */     if (Utils.isExtensionHandled(extension)) {
/* 1334 */       openFileInNewTab(attachmentFile);
/*      */     } else {
/* 1336 */       Uri uri = Utils.getUriForFile((Context)fragmentActivity, attachmentFile);
/* 1337 */       if (uri != null) {
/* 1338 */         Utils.shareGenericFile((Activity)fragmentActivity, uri);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void freehandStylusUsedFirstTime() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void imageStamperSelected(PointF targetPoint) {
/* 1355 */     this.mImageCreationMode = ToolManager.ToolMode.STAMPER;
/* 1356 */     this.mAnnotTargetPoint = targetPoint;
/* 1357 */     this.mOutputFileUri = ViewerUtils.openImageIntent(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public void imageSignatureSelected(PointF targetPoint, int targetPage, Long widget) {
/* 1362 */     this.mImageCreationMode = ToolManager.ToolMode.SIGNATURE;
/* 1363 */     this.mAnnotTargetPoint = targetPoint;
/* 1364 */     this.mAnnotTargetPage = targetPage;
/* 1365 */     this.mTargetWidget = widget;
/* 1366 */     this.mOutputFileUri = ViewerUtils.openImageIntent(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public void attachFileSelected(PointF targetPoint) {
/* 1371 */     this.mAnnotTargetPoint = targetPoint;
/* 1372 */     ViewerUtils.openFileIntent(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void freeTextInlineEditingStarted() {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean newFileSelectedFromTool(String filePath) {
/* 1385 */     if (Utils.isNullOrEmpty(filePath)) {
/* 1386 */       return false;
/*      */     }
/* 1388 */     File file = new File(filePath);
/* 1389 */     if (file.exists()) {
/* 1390 */       openFileInNewTab(file, "");
/* 1391 */       return true;
/*      */     } 
/*      */     
/* 1394 */     if (this.mCurrentFile != null) {
/* 1395 */       File parent = this.mCurrentFile.getParentFile();
/* 1396 */       File newFile = new File(parent, filePath);
/* 1397 */       if (newFile.exists()) {
/* 1398 */         openFileInNewTab(newFile, "");
/* 1399 */         return true;
/*      */       } 
/*      */     } 
/* 1402 */     if (this.mCurrentUriFile != null) {
/* 1403 */       FragmentActivity fragmentActivity = getActivity();
/* 1404 */       if (fragmentActivity != null) {
/* 1405 */         ExternalFileInfo fileInfo = Utils.buildExternalFile((Context)fragmentActivity, this.mCurrentUriFile);
/* 1406 */         if (fileInfo != null) {
/* 1407 */           ExternalFileInfo parent = fileInfo.getParent();
/* 1408 */           if (parent != null) {
/* 1409 */             ExternalFileInfo newFile = parent.getFile(filePath);
/* 1410 */             if (newFile != null && newFile.exists()) {
/* 1411 */               openFileUriInNewTab(newFile.getUri(), "");
/* 1412 */               return true;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/* 1418 */     return false;
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
/*      */   public void onPageChange(int old_page, int cur_page, PDFViewCtrl.PageChangeState state) {
/* 1432 */     FragmentActivity fragmentActivity = getActivity();
/* 1433 */     if (fragmentActivity == null || this.mPdfViewCtrl == null) {
/*      */       return;
/*      */     }
/* 1436 */     this.mRageScrollingCount = 0;
/*      */     
/* 1438 */     boolean shouldUnlockRead = false;
/*      */     try {
/* 1440 */       this.mPdfViewCtrl.docLockRead();
/* 1441 */       shouldUnlockRead = true;
/* 1442 */       this.mPageCount = this.mPdfViewCtrl.getDoc().getPageCount();
/* 1443 */     } catch (Exception e) {
/* 1444 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } finally {
/* 1446 */       if (shouldUnlockRead) {
/* 1447 */         this.mPdfViewCtrl.docUnlockRead();
/*      */       }
/*      */     } 
/*      */     
/* 1451 */     updatePageIndicator();
/*      */ 
/*      */     
/* 1454 */     if (this.mIsReflowMode && this.mReflowControl != null) {
/*      */       try {
/* 1456 */         this.mReflowControl.setCurrentPage(cur_page);
/* 1457 */       } catch (Exception e) {
/* 1458 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1464 */     this.mPreviousPageInfo.copyPageInfo(this.mCurrentPageInfo);
/* 1465 */     this.mCurrentPageInfo = getCurrentPageInfo();
/*      */ 
/*      */     
/* 1468 */     if (this.mPushNextPageOnStack.booleanValue() && state == PDFViewCtrl.PageChangeState.END)
/*      */     {
/*      */       
/* 1471 */       if (this.mPageBackStack.isEmpty() || ((PageBackButtonInfo)this.mPageBackStack.peek()).pageNum != this.mPdfViewCtrl.getCurrentPage()) {
/*      */         
/* 1473 */         if (this.mPageBackStack.size() >= 50) {
/* 1474 */           this.mPageBackStack.removeLast();
/*      */         }
/*      */         
/* 1477 */         this.mPageBackStack.push(getCurrentPageInfo());
/* 1478 */         this.mPushNextPageOnStack = Boolean.valueOf(false);
/*      */ 
/*      */ 
/*      */         
/* 1482 */         if (!this.mPageForwardStack.isEmpty()) {
/* 1483 */           this.mPageForwardStack.clear();
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 1489 */     if (this.mPreviousPageInfo.pageNum < 0) {
/* 1490 */       this.mPreviousPageInfo.copyPageInfo(this.mCurrentPageInfo);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1495 */     if (this.mInternalLinkClicked.booleanValue()) {
/* 1496 */       setCurrentPageHelper(cur_page, false, getCurrentPageInfo());
/* 1497 */       this.mInternalLinkClicked = Boolean.valueOf(false);
/*      */     } 
/*      */     
/* 1500 */     if (PdfViewCtrlSettingsManager.getPageNumberOverlayOption((Context)fragmentActivity)) {
/* 1501 */       resetHidePageNumberIndicatorTimer();
/*      */     }
/*      */ 
/*      */     
/* 1505 */     if (this.mWaitingForSetPage && this.mWaitingForSetPageNum == cur_page) {
/* 1506 */       this.mWaitingForSetPage = false;
/* 1507 */       this.mWaitingForSetPageNum = -1;
/* 1508 */       restoreFreeText();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDocumentLoaded() {
/* 1517 */     if (getActivity() == null || this.mPdfViewCtrl == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 1522 */     if (this.mBottomNavBar != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1527 */       this.mBottomNavBar.setPdfViewCtrl(this.mPdfViewCtrl);
/*      */       
/* 1529 */       this.mBottomNavBar.setThumbSliderListener(this);
/* 1530 */       this.mBottomNavBar.handleDocumentLoaded();
/*      */     } 
/*      */     
/* 1533 */     doDocumentLoaded();
/*      */     
/* 1535 */     resetHidePageNumberIndicatorTimer();
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
/*      */   public void onDownloadEvent(PDFViewCtrl.DownloadState state, int page_num, int page_downloaded, int page_count, String message) {
/* 1550 */     FragmentActivity fragmentActivity = getActivity();
/* 1551 */     if (fragmentActivity == null || this.mPdfViewCtrl == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1555 */     if (this.mDocumentConversion != null && sDebug) {
/* 1556 */       Log.e("UNIVERSAL SEQUENCE", "Got downloaded event of type " + state + " even though it should be a conversion.");
/*      */     }
/*      */ 
/*      */     
/* 1560 */     switch (state) {
/*      */       case OPEN_TOOLBAR:
/*      */       case CLOSE_TOOLBAR:
/* 1563 */         if (this.mDownloadDocumentDialog != null && this.mDownloadDocumentDialog.isShowing()) {
/* 1564 */           this.mDownloadDocumentDialog.dismiss();
/*      */         }
/*      */         
/* 1567 */         if (this.mPageCount != page_count) {
/* 1568 */           this.mPageCount = page_count;
/* 1569 */           this.mBottomNavBar.refreshPageCount();
/*      */         } 
/*      */         
/* 1572 */         if (state == PDFViewCtrl.DownloadState.FINISHED) {
/* 1573 */           this.mDownloading = false;
/* 1574 */           CommonToast.showText((Context)fragmentActivity, R.string.download_finished_message, 0);
/* 1575 */           if (this.mCurrentFile != null) {
/* 1576 */             if (Utils.isNotPdf(this.mCurrentFile.getAbsolutePath())) {
/* 1577 */               openOfficeDoc(this.mCurrentFile.getAbsolutePath(), false);
/*      */               return;
/*      */             } 
/*      */             try {
/* 1581 */               this.mPdfDoc = new PDFDoc(this.mCurrentFile.getAbsolutePath());
/* 1582 */             } catch (Exception e) {
/* 1583 */               this.mPdfDoc = null;
/*      */             } 
/* 1585 */             boolean error = false;
/* 1586 */             if (this.mPdfDoc != null) {
/*      */               try {
/* 1588 */                 String oldTabTag = this.mTabTag;
/* 1589 */                 int oldTabSource = this.mTabSource;
/* 1590 */                 this.mTabTag = this.mCurrentFile.getAbsolutePath();
/* 1591 */                 this.mTabSource = 2;
/* 1592 */                 this.mTabTitle = FilenameUtils.removeExtension((new File(this.mTabTag)).getName());
/* 1593 */                 if ((!this.mTabTag.equals(oldTabTag) || this.mTabSource != oldTabSource) && 
/* 1594 */                   this.mTabListener != null) {
/* 1595 */                   this.mTabListener.onTabIdentityChanged(oldTabTag, this.mTabTag, this.mTabTitle, this.mFileExtension, this.mTabSource);
/*      */                 }
/*      */                 
/* 1598 */                 ToolManager.Tool currentTool = this.mToolManager.getTool();
/* 1599 */                 int currentPage = this.mPdfViewCtrl.getCurrentPage();
/* 1600 */                 this.mToolManager.setReadOnly(false);
/* 1601 */                 checkPdfDoc();
/* 1602 */                 this.mToolManager.setTool(currentTool);
/* 1603 */                 this.mPdfViewCtrl.setCurrentPage(currentPage);
/* 1604 */               } catch (Exception e) {
/* 1605 */                 error = true;
/* 1606 */                 AnalyticsHandlerAdapter.getInstance().sendException(e, "checkPdfDoc");
/*      */               } 
/*      */             } else {
/* 1609 */               error = true;
/*      */             } 
/*      */             
/* 1612 */             if (error) {
/* 1613 */               handleOpeningDocumentFailed(1);
/*      */               
/*      */               return;
/*      */             } 
/*      */             
/* 1618 */             PdfViewCtrlTabInfo info = new PdfViewCtrlTabInfo();
/* 1619 */             info.tabTitle = this.mTabTitle;
/* 1620 */             info.tabSource = 2;
/* 1621 */             info.fileExtension = this.mFileExtension;
/*      */             
/* 1623 */             String mode = PdfViewCtrlSettingsManager.getViewMode((Context)fragmentActivity);
/* 1624 */             info.pagePresentationMode = getPagePresentationModeFromSettings(mode).getValue();
/*      */             
/* 1626 */             PdfViewCtrlTabsManager.getInstance().addPdfViewCtrlTabInfo((Context)fragmentActivity, this.mCurrentFile.getAbsolutePath(), info);
/* 1627 */             addToRecentList(info);
/*      */ 
/*      */ 
/*      */             
/* 1631 */             if (this.mTabListener != null) {
/* 1632 */               this.mTabListener.onDownloadedSuccessful();
/*      */             }
/*      */           } 
/*      */         } 
/*      */         break;
/*      */       
/*      */       case UPDATE_TOOLBAR:
/* 1639 */         if (sDebug)
/* 1640 */           Log.d(TAG, "DOWNLOAD_FAILED: " + message); 
/* 1641 */         if (this.mDownloadDocumentDialog != null && this.mDownloadDocumentDialog.isShowing()) {
/* 1642 */           this.mDownloadDocumentDialog.dismiss();
/*      */         }
/* 1644 */         if (message != null && !message.equals("cancelled")) {
/* 1645 */           CommonToast.showText((Context)fragmentActivity, R.string.download_failed_message, 0);
/* 1646 */           this.mErrorCode = 1;
/* 1647 */           handleOpeningDocumentFailed(this.mErrorCode);
/*      */         } 
/*      */         break;
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
/*      */   public void onRenderingStarted() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onRenderingFinished() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onConversionEvent(PDFViewCtrl.ConversionState state, int totalPagesConverted) {
/* 1679 */     FragmentActivity fragmentActivity = getActivity();
/* 1680 */     if (fragmentActivity == null || this.mPdfViewCtrl == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1684 */     switch (state) {
/*      */       case OPEN_TOOLBAR:
/* 1686 */         if (this.mPdfDoc == null) {
/* 1687 */           this.mPdfDoc = this.mPdfViewCtrl.getDoc();
/*      */         }
/* 1689 */         this.mPageCount = totalPagesConverted;
/* 1690 */         if (this.mPageCount > 0 && !this.mUsedCacheCalled) {
/* 1691 */           if (this.mViewerConfig == null || !this.mViewerConfig.isUseStandardLibrary()) {
/* 1692 */             RecentlyUsedCache.accessDocument(this.mTabTag, this.mPdfViewCtrl.getDoc());
/*      */           }
/* 1694 */           this.mUsedCacheCalled = true;
/*      */         } 
/* 1696 */         this.mBottomNavBar.refreshPageCount();
/* 1697 */         updatePageIndicator();
/* 1698 */         resetHidePageNumberIndicatorTimer();
/* 1699 */         if (!this.mIsPageNumberIndicatorConversionSpinningRunning) {
/* 1700 */           this
/* 1701 */             .mIsPageNumberIndicatorConversionSpinningRunning = this.mPageNumberIndicatorConversionSpinningHandler.postDelayed(this.mPageNumberIndicatorConversionSpinnerRunnable, 1000L);
/*      */         }
/*      */         break;
/*      */       case CLOSE_TOOLBAR:
/* 1705 */         this.mDocumentLoading = false;
/* 1706 */         if (this.mShouldNotifyWhenConversionFinishes) {
/* 1707 */           CommonToast.showText((Context)fragmentActivity, R.string.open_universal_succeeded, 0, 17, 0, 0);
/*      */         }
/*      */         
/* 1710 */         this.mIsOfficeDocReady = true;
/*      */         
/* 1712 */         this.mDocumentConversion = null;
/* 1713 */         this.mDocumentState = 9;
/*      */         
/* 1715 */         stopConversionSpinningIndicator();
/*      */ 
/*      */         
/* 1718 */         saveConversionTempCopy();
/*      */         break;
/*      */       case UPDATE_TOOLBAR:
/* 1721 */         if (sDebug && 
/* 1722 */           this.mDocumentConversion != null) {
/*      */           try {
/* 1724 */             Log.e(TAG, this.mDocumentConversion.getErrorString());
/* 1725 */           } catch (PDFNetException e) {
/* 1726 */             e.printStackTrace();
/*      */           } 
/*      */         }
/*      */ 
/*      */         
/* 1731 */         stopConversionSpinningIndicator();
/*      */         break;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onAddProgressIndicator() {
/* 1742 */     if (this.mPdfViewCtrl == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1746 */     if (this.mUniversalDocSpinner != null && this.mPdfViewCtrl.indexOfChild((View)this.mUniversalDocSpinner) >= 0) {
/* 1747 */       this.mPdfViewCtrl.removeView((View)this.mUniversalDocSpinner);
/*      */     }
/*      */     
/* 1750 */     this.mUniversalDocSpinner = new ProgressBar(this.mPdfViewCtrl.getContext());
/* 1751 */     this.mUniversalDocSpinner.measure(0, 0);
/* 1752 */     int width = this.mUniversalDocSpinner.getMeasuredWidth();
/* 1753 */     if (width > 0) {
/* 1754 */       this.mSpinnerSize = width;
/*      */     }
/* 1756 */     this.mUniversalDocSpinner.setIndeterminate(true);
/* 1757 */     this.mUniversalDocSpinner.setVisibility(4);
/* 1758 */     this.mPdfViewCtrl.addView((View)this.mUniversalDocSpinner);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onPositionProgressIndicatorPage(Rect position) {
/* 1768 */     if (this.mUniversalDocSpinner != null) {
/*      */       try {
/* 1770 */         int spinnerSize = this.mSpinnerSize;
/* 1771 */         if (spinnerSize > position.getWidth()) {
/* 1772 */           spinnerSize = (int)position.getWidth();
/*      */         }
/* 1774 */         if (spinnerSize > position.getHeight()) {
/* 1775 */           spinnerSize = (int)position.getHeight();
/*      */         }
/* 1777 */         int halfX = (int)(position.getX1() + position.getX2()) / 2;
/* 1778 */         int halfY = (int)(position.getY1() + position.getY2()) / 2;
/* 1779 */         halfX -= spinnerSize / 2;
/* 1780 */         halfY -= spinnerSize / 2;
/*      */         
/* 1782 */         this.mUniversalDocSpinner.layout(halfX, halfY, halfX + spinnerSize, halfY + spinnerSize);
/* 1783 */       } catch (Exception e) {
/* 1784 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
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
/*      */   public void onProgressIndicatorPageVisibilityChanged(boolean isVisible) {
/* 1796 */     if (this.mUniversalDocSpinner != null) {
/* 1797 */       if (isVisible) {
/* 1798 */         this.mUniversalDocSpinner.setVisibility(0);
/*      */       } else {
/* 1800 */         this.mUniversalDocSpinner.setVisibility(4);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onRemoveProgressIndicator() {
/* 1810 */     if (this.mUniversalDocSpinner != null && this.mPdfViewCtrl != null && this.mPdfViewCtrl
/* 1811 */       .indexOfChild((View)this.mUniversalDocSpinner) >= 0) {
/* 1812 */       this.mPdfViewCtrl.removeView((View)this.mUniversalDocSpinner);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onShowContentPendingIndicator() {
/* 1822 */     if (sDebug) {
/* 1823 */       Log.i("UNIVERSAL PROGRESS", "Told to show content pendering indicator");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onRemoveContentPendingIndicator() {
/* 1833 */     if (sDebug) {
/* 1834 */       Log.i("UNIVERSAL PROGRESS", "Told to hide content pendering indicator");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onGenericMotionEvent(MotionEvent event) {
/* 1845 */     if (!Utils.isNougat()) {
/*      */       return;
/*      */     }
/*      */     
/* 1849 */     View view = getView();
/* 1850 */     if (view == null || this.mPdfViewCtrl == null || this.mToolManager == null || this.mToolManager.getTool() == null) {
/*      */       return;
/*      */     }
/* 1853 */     Tool tool = (Tool)this.mToolManager.getTool();
/* 1854 */     ToolManager.ToolMode mode = ToolManager.getDefaultToolMode(tool.getToolMode());
/* 1855 */     float x = event.getX();
/* 1856 */     float y = event.getY();
/*      */     
/* 1858 */     float threshold = 2.0F;
/* 1859 */     if (y < threshold) {
/* 1860 */       if (this.mTabListener != null) {
/* 1861 */         this.mTabListener.setViewerOverlayUIVisible(true);
/* 1862 */         this.mToolbarOpenedFromMouseMovement = true;
/*      */       }
/*      */     
/* 1865 */     } else if (this.mTabListener != null && this.mToolbarOpenedFromMouseMovement) {
/* 1866 */       int height = this.mTabListener.getToolbarHeight();
/* 1867 */       if (y > height + threshold && 
/* 1868 */         this.mTabListener != null) {
/* 1869 */         this.mTabListener.setViewerOverlayUIVisible(false);
/* 1870 */         this.mToolbarOpenedFromMouseMovement = false;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1876 */     Context context = getContext();
/* 1877 */     if (context == null) {
/*      */       return;
/*      */     }
/* 1880 */     if (tool.isInsideQuickMenu(x, y)) {
/* 1881 */       view.setPointerIcon(PointerIcon.getSystemIcon(context, 1000));
/*      */       
/*      */       return;
/*      */     } 
/* 1885 */     if (mode == ToolManager.ToolMode.ANNOT_EDIT) {
/* 1886 */       AnnotEdit annotEdit = (AnnotEdit)tool;
/* 1887 */       if (annotEdit.isCtrlPtsHidden()) {
/* 1888 */         view.setPointerIcon(PointerIcon.getSystemIcon(context, 1000));
/*      */       } else {
/* 1890 */         int effectCtrlPointId = annotEdit.getEffectCtrlPointId(x + this.mPdfViewCtrl.getScrollX(), y + this.mPdfViewCtrl.getScrollY());
/* 1891 */         switch (effectCtrlPointId) {
/*      */           case 0:
/*      */           case 2:
/* 1894 */             view.setPointerIcon(PointerIcon.getSystemIcon(getContext(), 1016));
/*      */             return;
/*      */           case 1:
/*      */           case 3:
/* 1898 */             view.setPointerIcon(PointerIcon.getSystemIcon(getContext(), 1017));
/*      */             return;
/*      */           case 4:
/*      */           case 7:
/* 1902 */             view.setPointerIcon(PointerIcon.getSystemIcon(getContext(), 1014));
/*      */             return;
/*      */           case 5:
/*      */           case 6:
/* 1906 */             view.setPointerIcon(PointerIcon.getSystemIcon(getContext(), 1015));
/*      */             return;
/*      */           case -2:
/* 1909 */             view.setPointerIcon(PointerIcon.getSystemIcon(getContext(), 1013));
/*      */             return;
/*      */         } 
/* 1912 */         view.setPointerIcon(PointerIcon.getSystemIcon(getContext(), 1000));
/*      */       } 
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 1919 */     if (mode == ToolManager.ToolMode.ANNOT_EDIT_LINE) {
/* 1920 */       AnnotEditLine annotEditLine = (AnnotEditLine)tool;
/* 1921 */       int effectCtrlPointId = annotEditLine.getEffectCtrlPointId(x + this.mPdfViewCtrl.getScrollX(), y + this.mPdfViewCtrl.getScrollY());
/* 1922 */       if (effectCtrlPointId == 2) {
/* 1923 */         view.setPointerIcon(PointerIcon.getSystemIcon(getContext(), 1013));
/*      */       } else {
/* 1925 */         view.setPointerIcon(PointerIcon.getSystemIcon(getContext(), 1000));
/*      */       } 
/*      */       
/*      */       return;
/*      */     } 
/* 1930 */     if (mode == ToolManager.ToolMode.TEXT_SELECT) {
/* 1931 */       TextSelect textSelect = (TextSelect)tool;
/* 1932 */       if (textSelect.hitTest(x + this.mPdfViewCtrl.getScrollX(), y + this.mPdfViewCtrl.getScrollY()) >= 0) {
/* 1933 */         view.setPointerIcon(PointerIcon.getSystemIcon(getContext(), 1014));
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/* 1938 */     if (mode == ToolManager.ToolMode.TEXT_UNDERLINE || mode == ToolManager.ToolMode.TEXT_HIGHLIGHT || mode == ToolManager.ToolMode.TEXT_SQUIGGLY || mode == ToolManager.ToolMode.TEXT_STRIKEOUT) {
/*      */       
/* 1940 */       view.setPointerIcon(PointerIcon.getSystemIcon(getContext(), 1008));
/*      */       
/*      */       return;
/*      */     } 
/* 1944 */     boolean buttonPressed = (event.isButtonPressed(1) || event.isButtonPressed(4));
/*      */     
/* 1946 */     if (mode != ToolManager.ToolMode.PAN || tool.getNextToolMode() != ToolManager.ToolMode.PAN) {
/* 1947 */       if (mode != ToolManager.ToolMode.TEXT_SELECT) {
/* 1948 */         view.setPointerIcon(PointerIcon.getSystemIcon(getContext(), 1000));
/*      */         return;
/*      */       } 
/* 1951 */       if (buttonPressed) {
/*      */         return;
/*      */       }
/*      */     } 
/*      */     
/* 1956 */     boolean shouldUnlockRead = false;
/*      */     try {
/* 1958 */       this.mPdfViewCtrl.docLockRead();
/* 1959 */       shouldUnlockRead = true;
/*      */ 
/*      */       
/* 1962 */       int pageNum = this.mPdfViewCtrl.getPageNumberFromScreenPt(x, y);
/* 1963 */       boolean isTextUnderMouse = false;
/* 1964 */       boolean isAnnotUnderMouse = false;
/*      */       
/* 1966 */       if (pageNum > 0) {
/* 1967 */         if (this.mPdfViewCtrl.wereWordsPrepared(pageNum)) {
/* 1968 */           if (this.mPdfViewCtrl.isThereTextInRect((x - 1.0F), (y - 1.0F), (x + 1.0F), (y + 1.0F))) {
/* 1969 */             isTextUnderMouse = true;
/*      */           }
/*      */         } else {
/* 1972 */           this.mPdfViewCtrl.prepareWords(pageNum);
/*      */         } 
/*      */         
/* 1975 */         if (this.mPdfViewCtrl.wereAnnotsForMousePrepared(pageNum)) {
/* 1976 */           if (this.mPdfViewCtrl.getAnnotTypeUnder(x, y) != 28) {
/* 1977 */             isAnnotUnderMouse = true;
/*      */           }
/*      */         } else {
/* 1980 */           this.mPdfViewCtrl.prepareAnnotsForMouse(pageNum);
/*      */         } 
/*      */       } 
/*      */       
/* 1984 */       if (isAnnotUnderMouse) {
/* 1985 */         view.setPointerIcon(PointerIcon.getSystemIcon(getContext(), 1002));
/* 1986 */       } else if (isTextUnderMouse || mode == ToolManager.ToolMode.TEXT_SELECT) {
/* 1987 */         view.setPointerIcon(PointerIcon.getSystemIcon(getContext(), 1008));
/* 1988 */       } else if (buttonPressed) {
/* 1989 */         view.setPointerIcon(PointerIcon.getSystemIcon(getContext(), 1021));
/*      */       } else {
/* 1991 */         view.setPointerIcon(PointerIcon.getSystemIcon(getContext(), 1000));
/*      */       }
/*      */     
/* 1994 */     } catch (Exception e) {
/* 1995 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } finally {
/* 1997 */       if (shouldUnlockRead) {
/* 1998 */         this.mPdfViewCtrl.docUnlockRead();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onChangePointerIcon(PointerIcon pointerIcon) {
/* 2008 */     if (Utils.isNougat() && getView() != null) {
/* 2009 */       getView().setPointerIcon(pointerIcon);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onSingleTapConfirmed(MotionEvent e) {
/* 2018 */     FragmentActivity fragmentActivity = getActivity();
/* 2019 */     if (fragmentActivity == null || this.mPdfViewCtrl == null) {
/* 2020 */       return false;
/*      */     }
/* 2022 */     this.mRageScrollingCount = 0;
/*      */     
/* 2024 */     int x = (int)(e.getX() + 0.5D);
/* 2025 */     int y = (int)(e.getY() + 0.5D);
/*      */     
/* 2027 */     if (this.mToolManager != null && this.mToolManager
/* 2028 */       .getTool() != null && this.mToolManager
/* 2029 */       .getTool() instanceof com.pdftron.pdf.tools.Pan) {
/* 2030 */       boolean hasAnnotation = false;
/* 2031 */       boolean hasLink = false;
/*      */       
/* 2033 */       boolean shouldUnlockRead = false;
/*      */       try {
/* 2035 */         this.mPdfViewCtrl.docLockRead();
/* 2036 */         shouldUnlockRead = true;
/* 2037 */         this.mSelectedAnnot = this.mPdfViewCtrl.getAnnotationAt(x, y);
/* 2038 */         PDFViewCtrl.LinkInfo linkInfo = this.mPdfViewCtrl.getLinkAt(x, y);
/* 2039 */         if (this.mSelectedAnnot != null && this.mSelectedAnnot.isValid()) {
/* 2040 */           hasAnnotation = true;
/*      */         }
/* 2042 */         if (linkInfo != null) {
/* 2043 */           hasLink = true;
/*      */         }
/* 2045 */         if (hasAnnotation && this.mSelectedAnnot.getType() == 1) {
/* 2046 */           hasLink = true;
/*      */         }
/* 2048 */       } catch (Exception ex) {
/* 2049 */         AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*      */       } finally {
/* 2051 */         if (shouldUnlockRead) {
/* 2052 */           this.mPdfViewCtrl.docUnlockRead();
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 2058 */       if (hasLink)
/*      */       {
/*      */         
/* 2061 */         return this.mToolManager.isQuickMenuJustClosed();
/*      */       }
/*      */       
/* 2064 */       if (hasAnnotation) {
/* 2065 */         handleSpecialFile();
/*      */       }
/*      */       else {
/*      */         
/* 2069 */         if (!this.mToolManager.isQuickMenuJustClosed()) {
/*      */ 
/*      */           
/* 2072 */           boolean handled = false;
/*      */           
/* 2074 */           RegionSingleTap region = getRegionTap(x, y);
/* 2075 */           if (isSinglePageMode() && region != RegionSingleTap.Middle) {
/* 2076 */             boolean allowPageChangeOnTapEnabled = PdfViewCtrlSettingsManager.getAllowPageChangeOnTap((Context)fragmentActivity);
/* 2077 */             if (allowPageChangeOnTapEnabled) {
/* 2078 */               boolean checkNext = false;
/* 2079 */               boolean checkPrevious = false;
/* 2080 */               if (region == RegionSingleTap.Left) {
/* 2081 */                 if (isRtlMode()) {
/* 2082 */                   checkNext = true;
/*      */                 } else {
/* 2084 */                   checkPrevious = true;
/*      */                 } 
/* 2086 */               } else if (region == RegionSingleTap.Right) {
/* 2087 */                 if (isRtlMode()) {
/* 2088 */                   checkPrevious = true;
/*      */                 } else {
/* 2090 */                   checkNext = true;
/*      */                 } 
/*      */               } 
/* 2093 */               if (checkPrevious) {
/* 2094 */                 if (this.mPdfViewCtrl.canGotoPreviousPage()) {
/* 2095 */                   this.mPdfViewCtrl.gotoPreviousPage(PdfViewCtrlSettingsManager.getAllowPageChangeAnimation((Context)fragmentActivity));
/* 2096 */                   handled = true;
/*      */                 } 
/* 2098 */               } else if (checkNext && 
/* 2099 */                 this.mPdfViewCtrl.canGotoNextPage()) {
/* 2100 */                 this.mPdfViewCtrl.gotoNextPage(PdfViewCtrlSettingsManager.getAllowPageChangeAnimation((Context)fragmentActivity));
/* 2101 */                 handled = true;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */           
/* 2106 */           if (!handled && 
/* 2107 */             this.mTabListener != null) {
/* 2108 */             this.mTabListener.onTabSingleTapConfirmed();
/*      */           }
/*      */         } 
/*      */         
/* 2112 */         return true;
/*      */       } 
/*      */     } 
/* 2115 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onMove(MotionEvent e1, MotionEvent e2, float x_dist, float y_dist) {
/* 2123 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean onDown(MotionEvent e) {
/* 2128 */     this.mOnUpCalled = false;
/* 2129 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onUp(MotionEvent e, PDFViewCtrl.PriorEventMode priorEventMode) {
/* 2137 */     if (this.mPdfViewCtrl != null && priorEventMode == PDFViewCtrl.PriorEventMode.FLING)
/*      */     {
/*      */       
/* 2140 */       if (this.mToolManager != null && this.mToolManager
/* 2141 */         .getTool() instanceof com.pdftron.pdf.tools.Pan && 
/* 2142 */         !isContinuousPageMode() && this.mPdfViewCtrl
/* 2143 */         .getWidth() == this.mPdfViewCtrl.getViewCanvasWidth() && !this.mOnUpCalled) {
/*      */         
/* 2145 */         this.mOnUpCalled = true;
/*      */         
/* 2147 */         this.mRageScrollingCount++;
/*      */         
/* 2149 */         if (this.mRageScrollingCount >= 3) {
/* 2150 */           this.mRageScrollingCount = 0;
/* 2151 */           handleRageScrolling();
/*      */         } 
/*      */       } 
/*      */     }
/* 2155 */     if (priorEventMode != PDFViewCtrl.PriorEventMode.FLING) {
/* 2156 */       this.mRageScrollingCount = 0;
/*      */     }
/* 2158 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onScaleBegin(float x, float y) {
/* 2166 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onScale(float x, float y) {
/* 2174 */     this.mRageScrollingCount = 0;
/* 2175 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onScaleEnd(float x, float y) {
/* 2183 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onLongPress(MotionEvent e) {
/* 2191 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onScrollChanged(int l, int t, int oldl, int oldt) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onDoubleTap(MotionEvent e) {
/* 2207 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean onKeyUp(int keyCode, KeyEvent event) {
/* 2212 */     return handleKeyUp(keyCode, event);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onQuickMenuClicked(QuickMenuItem menuItem) {
/* 2220 */     boolean result = false;
/* 2221 */     if (this.mQuickMenuListeners != null) {
/* 2222 */       for (ToolManager.QuickMenuListener listener : this.mQuickMenuListeners) {
/* 2223 */         if (listener.onQuickMenuClicked(menuItem)) {
/* 2224 */           result = true;
/*      */         }
/*      */       } 
/*      */     }
/* 2228 */     this.mToolManager.setQuickMenuJustClosed(false);
/* 2229 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean onShowQuickMenu(QuickMenu quickMenu, Annot annot) {
/* 2234 */     boolean result = false;
/* 2235 */     if (this.mQuickMenuListeners != null) {
/* 2236 */       for (ToolManager.QuickMenuListener listener : this.mQuickMenuListeners) {
/* 2237 */         if (listener.onShowQuickMenu(quickMenu, annot)) {
/* 2238 */           result = true;
/*      */         }
/*      */       } 
/*      */     }
/* 2242 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onQuickMenuShown() {
/* 2250 */     if (this.mQuickMenuListeners != null) {
/* 2251 */       for (ToolManager.QuickMenuListener listener : this.mQuickMenuListeners) {
/* 2252 */         listener.onQuickMenuShown();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onQuickMenuDismissed() {
/* 2262 */     if (this.mQuickMenuListeners != null) {
/* 2263 */       for (ToolManager.QuickMenuListener listener : this.mQuickMenuListeners) {
/* 2264 */         listener.onQuickMenuDismissed();
/*      */       }
/*      */     }
/* 2267 */     if (Utils.isNougat() && getContext() != null) {
/* 2268 */       onChangePointerIcon(PointerIcon.getSystemIcon(getContext(), 1000));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onAnnotationsAdded(Map<Annot, Integer> annots) {
/* 2277 */     handleSpecialFile();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onAnnotationsPreModify(Map<Annot, Integer> annots) {
/* 2285 */     handleSpecialFile();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onAnnotationsModified(Map<Annot, Integer> annots, Bundle extra) {
/* 2293 */     handleSpecialFile();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onAnnotationsPreRemove(Map<Annot, Integer> annots) {
/* 2301 */     handleSpecialFile();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onAnnotationsRemoved(Map<Annot, Integer> annots) {
/* 2309 */     handleSpecialFile();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onAnnotationsRemovedOnPage(int pageNum) {
/* 2317 */     handleSpecialFile();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onAllAnnotationsRemoved() {
/* 2325 */     handleSpecialFile();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onAnnotationAction() {
/* 2333 */     handleSpecialFile();
/*      */   }
/*      */ 
/*      */   
/*      */   public void onPageLabelsChanged() {
/* 2338 */     handleSpecialFile();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onBookmarkModified() {
/* 2346 */     handleSpecialFile();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onPagesCropped() {
/* 2354 */     handleSpecialFile();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onPagesAdded(List<Integer> pageList) {
/* 2362 */     handleSpecialFile();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onPagesDeleted(List<Integer> pageList) {
/* 2370 */     handleSpecialFile();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onPagesRotated(List<Integer> pageList) {
/* 2378 */     handleSpecialFile();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onPageMoved(int from, int to) {
/* 2386 */     handleSpecialFile();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void annotationsCouldNotBeAdded(String errorMessage) {
/* 2394 */     FragmentActivity fragmentActivity = getActivity();
/* 2395 */     if (fragmentActivity == null) {
/*      */       return;
/*      */     }
/*      */     
/* 2399 */     if (!this.mAnnotNotAddedDialogShown) {
/* 2400 */       if (null == errorMessage) {
/* 2401 */         errorMessage = "Unknown Error";
/*      */       }
/* 2403 */       Utils.showAlertDialog((Activity)fragmentActivity, fragmentActivity.getString(R.string.annotation_could_not_be_added_dialog_msg, new Object[] { errorMessage }), fragmentActivity
/* 2404 */           .getString(R.string.error));
/* 2405 */       this.mAnnotNotAddedDialogShown = true;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onAnnotationSelected(Annot annot, int pageNum) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void onAnnotationUnselected() {}
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onInterceptAnnotationHandling(@Nullable Annot annot, Bundle extra, ToolManager.ToolMode toolMode) {
/*      */     try {
/* 2422 */       if (annot != null && annot.isValid() && annot.getType() == 1) {
/* 2423 */         this.mInternalLinkClicked = Boolean.valueOf(true);
/* 2424 */         updateCurrentPageInfo();
/*      */       } 
/* 2426 */     } catch (PDFNetException e) {
/* 2427 */       e.printStackTrace();
/*      */     } 
/*      */     
/* 2430 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean onInterceptDialog(AlertDialog dialog) {
/* 2435 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onReflowSingleTapUp(MotionEvent event) {
/* 2443 */     if (this.mTabListener != null) {
/* 2444 */       this.mTabListener.onTabSingleTapConfirmed();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onThumbSliderStartTrackingTouch() {
/* 2453 */     animatePageIndicator(false);
/* 2454 */     this.mPageBackButton.hide();
/* 2455 */     this.mPageForwardButton.hide();
/*      */     
/* 2457 */     updateCurrentPageInfo();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onThumbSliderStopTrackingTouch(int pageNum) {
/* 2465 */     if (this.mTabListener != null) {
/* 2466 */       this.mTabListener.onTabThumbSliderStopTrackingTouch();
/*      */     }
/*      */     
/* 2469 */     resetHidePageNumberIndicatorTimer();
/* 2470 */     setCurrentPageHelper(pageNum, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void userCropDialogDismiss() {
/* 2477 */     resetHidePageNumberIndicatorTimer();
/*      */     
/* 2479 */     resetAutoSavingTimer();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTabListener(TabListener listener) {
/* 2488 */     this.mTabListener = listener;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addAnnotationToolbarListener(AnnotationToolbar.AnnotationToolbarListener listener) {
/* 2497 */     if (this.mAnnotationToolbarListeners == null) {
/* 2498 */       this.mAnnotationToolbarListeners = new ArrayList<>();
/*      */     }
/* 2500 */     if (!this.mAnnotationToolbarListeners.contains(listener)) {
/* 2501 */       this.mAnnotationToolbarListeners.add(listener);
/*      */     }
/*      */   }
/*      */   
/*      */   public void removeAnnotationToolbarListener(AnnotationToolbar.AnnotationToolbarListener listener) {
/* 2506 */     if (this.mAnnotationToolbarListeners != null) {
/* 2507 */       this.mAnnotationToolbarListeners.remove(listener);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addQuickMenuListener(ToolManager.QuickMenuListener listener) {
/* 2517 */     if (this.mQuickMenuListeners == null) {
/* 2518 */       this.mQuickMenuListeners = new ArrayList<>();
/*      */     }
/* 2520 */     if (!this.mQuickMenuListeners.contains(listener)) {
/* 2521 */       this.mQuickMenuListeners.add(listener);
/*      */     }
/*      */   }
/*      */   
/*      */   public void removeQuickMenuListener(ToolManager.QuickMenuListener listener) {
/* 2526 */     if (this.mQuickMenuListeners != null) {
/* 2527 */       this.mQuickMenuListeners.remove(listener);
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
/*      */   public static Bundle createBasicPdfViewCtrlTabBundle(String tag, String title, String fileExtension, String password, int itemSource) {
/* 2543 */     return createBasicPdfViewCtrlTabBundle(tag, title, fileExtension, password, itemSource, (ViewerConfig)null);
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
/*      */   public static Bundle createBasicPdfViewCtrlTabBundle(String tag, String title, String fileExtension, String password, int itemSource, ViewerConfig config) {
/* 2559 */     Bundle args = new Bundle();
/* 2560 */     args.putString("bundle_tab_tag", tag);
/* 2561 */     args.putString("bundle_tab_title", title);
/* 2562 */     args.putString("bundle_tab_file_extension", fileExtension);
/* 2563 */     args.putString("bundle_tab_password", password);
/* 2564 */     args.putInt("bundle_tab_item_source", itemSource);
/* 2565 */     args.putParcelable("bundle_tab_config", (Parcelable)config);
/*      */     
/* 2567 */     return args;
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
/*      */   public static Bundle createBasicPdfViewCtrlTabBundle(@NonNull Context context, @NonNull Uri fileUri, @Nullable String password) {
/* 2580 */     return createBasicPdfViewCtrlTabBundle(context, fileUri, password, (ViewerConfig)null);
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
/*      */   public static Bundle createBasicPdfViewCtrlTabBundle(@NonNull Context context, @NonNull Uri fileUri, @Nullable String password, @Nullable ViewerConfig config) {
/* 2593 */     return createBasicPdfViewCtrlTabBundle(context, fileUri, (String)null, password, config);
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
/*      */   public static Bundle createBasicPdfViewCtrlTabBundle(@NonNull Context context, @NonNull Uri fileUri, @Nullable String title, @Nullable String password, @Nullable ViewerConfig config) {
/*      */     int itemSource;
/* 2607 */     String tag = fileUri.toString();
/* 2608 */     if (null == title) {
/* 2609 */       title = Utils.getValidTitle(context, fileUri);
/*      */     }
/* 2611 */     String fileExtension = "";
/* 2612 */     ContentResolver contentResolver = Utils.getContentResolver(context);
/* 2613 */     if (contentResolver != null) {
/* 2614 */       fileExtension = Utils.getUriExtension(contentResolver, fileUri);
/*      */     } else {
/* 2616 */       String msg = "Could not get ContentResolver in createBasicPdfViewCtrlTabBundle.";
/* 2617 */       Logger.INSTANCE.LogE(TAG, msg);
/*      */     } 
/*      */ 
/*      */     
/* 2621 */     if ("content".equals(fileUri.getScheme())) {
/*      */       
/* 2623 */       if (Utils.uriHasReadWritePermission(context, fileUri)) {
/* 2624 */         itemSource = 6;
/* 2625 */       } else if (contentResolver != null && Utils.isNotPdf(contentResolver, fileUri)) {
/* 2626 */         itemSource = 15;
/*      */       } else {
/* 2628 */         itemSource = 13;
/*      */       } 
/* 2630 */     } else if (URLUtil.isHttpUrl(tag) || URLUtil.isHttpsUrl(tag)) {
/* 2631 */       itemSource = 5;
/*      */     } else {
/*      */       
/* 2634 */       tag = fileUri.getPath();
/* 2635 */       if (tag != null && tag.startsWith("/android_asset/")) {
/*      */         
/* 2637 */         File copy = Utils.copyAssetsToTempFolder(context, tag, true);
/* 2638 */         if (copy != null) {
/* 2639 */           tag = copy.getAbsolutePath();
/*      */         }
/*      */       } 
/* 2642 */       itemSource = 2;
/*      */     } 
/* 2644 */     return createBasicPdfViewCtrlTabBundle(tag, title, fileExtension, password, itemSource, config);
/*      */   }
/*      */   
/*      */   protected boolean isNightModeForToolManager() {
/* 2648 */     FragmentActivity fragmentActivity = getActivity();
/* 2649 */     return (fragmentActivity != null && (
/* 2650 */       PdfViewCtrlSettingsManager.getColorMode((Context)fragmentActivity) == 3 || (
/* 2651 */       PdfViewCtrlSettingsManager.getColorMode((Context)fragmentActivity) == 4 && 
/* 2652 */       Utils.isColorDark(PdfViewCtrlSettingsManager.getCustomColorModeBGColor((Context)fragmentActivity)))));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getHasWarnedAboutCanNotEditDuringConversion() {
/* 2661 */     return this.mHasWarnedAboutCanNotEditDuringConversion;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setHasWarnedAboutCanNotEditDuringConversion() {
/* 2668 */     this.mHasWarnedAboutCanNotEditDuringConversion = true;
/* 2669 */     this.mShouldNotifyWhenConversionFinishes = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDocumentReady() {
/* 2678 */     return this.mDocumentLoaded;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateViewMode(PDFViewCtrl.PagePresentationMode pagePresentationMode) {
/* 2687 */     FragmentActivity fragmentActivity = getActivity();
/* 2688 */     if (fragmentActivity == null || this.mPdfViewCtrl == null) {
/*      */       return;
/*      */     }
/*      */     
/* 2692 */     if (this.mCanAddToTabInfo) {
/* 2693 */       PdfViewCtrlTabsManager.getInstance().updateViewModeForTab((Context)fragmentActivity, this.mTabTag, pagePresentationMode);
/*      */     }
/* 2695 */     boolean verticalSnapping = PdfViewCtrlSettingsManager.isVerticalScrollSnap((Context)fragmentActivity);
/* 2696 */     if (verticalSnapping) {
/* 2697 */       if (pagePresentationMode == PDFViewCtrl.PagePresentationMode.SINGLE_CONT) {
/* 2698 */         pagePresentationMode = PDFViewCtrl.PagePresentationMode.SINGLE_VERT;
/* 2699 */       } else if (pagePresentationMode == PDFViewCtrl.PagePresentationMode.FACING_CONT) {
/* 2700 */         pagePresentationMode = PDFViewCtrl.PagePresentationMode.FACING_VERT;
/* 2701 */       } else if (pagePresentationMode == PDFViewCtrl.PagePresentationMode.FACING_COVER_CONT) {
/* 2702 */         pagePresentationMode = PDFViewCtrl.PagePresentationMode.FACING_COVER_VERT;
/*      */       }
/*      */     
/* 2705 */     } else if (pagePresentationMode == PDFViewCtrl.PagePresentationMode.SINGLE_VERT) {
/* 2706 */       pagePresentationMode = PDFViewCtrl.PagePresentationMode.SINGLE_CONT;
/* 2707 */     } else if (pagePresentationMode == PDFViewCtrl.PagePresentationMode.FACING_VERT) {
/* 2708 */       pagePresentationMode = PDFViewCtrl.PagePresentationMode.FACING_CONT;
/* 2709 */     } else if (pagePresentationMode == PDFViewCtrl.PagePresentationMode.FACING_COVER_VERT) {
/* 2710 */       pagePresentationMode = PDFViewCtrl.PagePresentationMode.FACING_COVER_CONT;
/*      */     } 
/*      */     
/*      */     try {
/* 2714 */       updateZoomLimits();
/* 2715 */       this.mPdfViewCtrl.setPagePresentationMode(pagePresentationMode);
/* 2716 */     } catch (Exception e) {
/* 2717 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateZoomLimits() {
/* 2725 */     FragmentActivity fragmentActivity = getActivity();
/* 2726 */     if (fragmentActivity == null || this.mPdfViewCtrl == null) {
/*      */       return;
/*      */     }
/*      */     
/*      */     try {
/* 2731 */       boolean isMaintainZoomEnabled = PdfViewCtrlSettingsManager.getMaintainZoomOption((Context)fragmentActivity);
/* 2732 */       if (this.mViewerConfig != null && this.mViewerConfig.getPdfViewCtrlConfig() != null) {
/* 2733 */         isMaintainZoomEnabled = getPDFViewCtrlConfig((Context)fragmentActivity).isMaintainZoomEnabled();
/*      */       }
/* 2735 */       this.mPdfViewCtrl.setMaintainZoomEnabled(isMaintainZoomEnabled);
/*      */       
/* 2737 */       PDFViewCtrl.PageViewMode viewMode = PdfViewCtrlSettingsManager.getPageViewMode((Context)fragmentActivity);
/* 2738 */       if (this.mViewerConfig != null && this.mViewerConfig.getPdfViewCtrlConfig() != null) {
/* 2739 */         viewMode = getPDFViewCtrlConfig((Context)fragmentActivity).getPageViewMode();
/*      */       }
/* 2741 */       this.mPdfViewCtrl.setZoomLimits(PDFViewCtrl.ZoomLimitMode.RELATIVE, 1.0D, 20.0D);
/*      */       
/* 2743 */       if (!isMaintainZoomEnabled) {
/* 2744 */         this.mPdfViewCtrl.setPageRefViewMode(viewMode);
/*      */       } else {
/* 2746 */         this.mPdfViewCtrl.setPreferredViewMode(viewMode);
/*      */       } 
/* 2748 */     } catch (Exception e) {
/* 2749 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updatePageIndicator() {
/* 2757 */     if (this.mPdfViewCtrl == null) {
/*      */       return;
/*      */     }
/*      */     
/* 2761 */     int curPage = this.mPdfViewCtrl.getCurrentPage();
/*      */     
/* 2763 */     if (this.mPageNumberIndicatorAll != null) {
/* 2764 */       this.mPageNumberIndicatorAll.setText(PageLabelUtils.getPageNumberIndicator(this.mPdfViewCtrl, curPage, this.mPageCount));
/*      */     }
/*      */     
/* 2767 */     if (this.mBottomNavBar != null) {
/* 2768 */       this.mBottomNavBar.setProgress(curPage);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PDFViewCtrl getPDFViewCtrl() {
/* 2778 */     return this.mPdfViewCtrl;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ToolManager getToolManager() {
/* 2787 */     return (this.mPdfViewCtrl == null) ? null : (ToolManager)this.mPdfViewCtrl.getToolManager();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AnnotationToolbar getAnnotationToolbar() {
/* 2796 */     return this.mAnnotationToolbar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PDFDoc getPdfDoc() {
/* 2805 */     return (this.mPdfViewCtrl == null) ? null : this.mPdfViewCtrl.getDoc();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isOpenFileFailed() {
/* 2814 */     return this.mErrorOnOpeningDocument;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTabErrorCode() {
/* 2825 */     return this.mErrorCode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected PdfViewCtrlTabInfo saveCurrentPdfViewCtrlState() {
/* 2832 */     if (!this.mStateEnabled || !this.mCanAddToTabInfo || !isDocumentReady()) {
/* 2833 */       return null;
/*      */     }
/*      */     
/* 2836 */     FragmentActivity fragmentActivity = getActivity();
/* 2837 */     if (fragmentActivity == null || this.mPdfViewCtrl == null) {
/* 2838 */       return null;
/*      */     }
/*      */     
/* 2841 */     PdfViewCtrlTabInfo info = PdfViewCtrlTabsManager.getInstance().getPdfFViewCtrlTabInfo((Context)fragmentActivity, this.mTabTag);
/* 2842 */     if (info == null) {
/* 2843 */       info = new PdfViewCtrlTabInfo();
/*      */     }
/*      */ 
/*      */     
/* 2847 */     info.fileExtension = this.mFileExtension;
/* 2848 */     info.tabTitle = this.mTabTitle;
/* 2849 */     info.tabSource = this.mTabSource;
/* 2850 */     info.hScrollPos = this.mPdfViewCtrl.getHScrollPos();
/* 2851 */     info.vScrollPos = this.mPdfViewCtrl.getVScrollPos();
/* 2852 */     info.zoom = this.mPdfViewCtrl.getZoom();
/* 2853 */     info.lastPage = this.mPdfViewCtrl.getCurrentPage();
/* 2854 */     info.pageRotation = this.mPdfViewCtrl.getPageRotation();
/* 2855 */     info.setPagePresentationMode(this.mPdfViewCtrl.getPagePresentationMode());
/* 2856 */     info.isRtlMode = this.mIsRtlMode;
/* 2857 */     info.isReflowMode = this.mIsReflowMode;
/* 2858 */     if (this.mReflowControl != null) {
/*      */       try {
/* 2860 */         info.reflowTextSize = this.mReflowControl.getTextSizeInPercent();
/* 2861 */       } catch (Exception e) {
/* 2862 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */       } 
/*      */     }
/* 2865 */     info.bookmarkDialogCurrentTab = this.mBookmarkDialogCurrentTab;
/*      */     
/* 2867 */     PdfViewCtrlTabsManager.getInstance().addPdfViewCtrlTabInfo((Context)fragmentActivity, this.mTabTag, info);
/*      */     
/* 2869 */     return info;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isNavigationListShowing() {
/* 2878 */     return (this.mNavigationList != null && this.mNavigationList.getVisibility() == 0);
/*      */   }
/*      */   
/*      */   public void updateNavigationListLayout(int marginTop, int marginBottom, boolean animated) {
/* 2882 */     if (isNavigationListShowing())
/*      */     {
/* 2884 */       if ((marginTop > -1 || marginBottom > -1) && 
/* 2885 */         this.mNavigationList.getLayoutParams() != null && this.mNavigationList.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
/* 2886 */         ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)this.mNavigationList.getLayoutParams();
/* 2887 */         boolean changed = false;
/* 2888 */         if (marginTop > -1 && layoutParams.topMargin != marginTop) {
/* 2889 */           layoutParams.topMargin = marginTop;
/* 2890 */           changed = true;
/*      */         } 
/* 2892 */         if (marginBottom > -1 && layoutParams.bottomMargin != marginBottom) {
/* 2893 */           layoutParams.bottomMargin = marginBottom;
/* 2894 */           changed = true;
/*      */         } 
/* 2896 */         if (changed) {
/* 2897 */           if (animated) {
/* 2898 */             TransitionSet transitionSet = new TransitionSet();
/* 2899 */             transitionSet.addTransition((Transition)new ChangeBounds());
/* 2900 */             transitionSet.addTransition((Transition)new Fade());
/* 2901 */             transitionSet.setDuration(250L);
/* 2902 */             TransitionManager.beginDelayedTransition((ViewGroup)this.mNavigationList, (Transition)transitionSet);
/*      */           } 
/* 2904 */           this.mNavigationList.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
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
/*      */   public void openSideSheet(DialogFragment dialogFragment, String tag, int marginTop, int marginBottom) {
/* 2916 */     if (dialogFragment == null) {
/*      */       return;
/*      */     }
/* 2919 */     if (this.mNavigationList == null) {
/* 2920 */       this.mNavigationList = (FrameLayout)this.mRootView.findViewById(R.id.navigation_list);
/*      */     }
/*      */     
/* 2923 */     TransitionSet transitionSet = new TransitionSet();
/* 2924 */     transitionSet.addTransition((Transition)new ChangeBounds());
/* 2925 */     Slide slideFromEnd = new Slide(8388613);
/* 2926 */     slideFromEnd.addTarget((View)this.mNavigationList);
/* 2927 */     transitionSet.addTransition((Transition)new Fade());
/* 2928 */     transitionSet.setDuration(250L);
/* 2929 */     TransitionManager.beginDelayedTransition(this.mViewerHost, (Transition)transitionSet);
/* 2930 */     this.mNavigationList.setVisibility(0);
/* 2931 */     updateNavigationListLayout(marginTop, marginBottom, false);
/*      */     
/* 2933 */     resizeOverlay(true);
/* 2934 */     FragmentTransaction ft = getChildFragmentManager().beginTransaction();
/* 2935 */     ft.replace(R.id.navigation_list, (Fragment)dialogFragment, tag);
/* 2936 */     ft.commitAllowingStateLoss();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void openNavigationList(BookmarksDialogFragment bookmarksDialogFragment, int marginTop, int marginBottom) {
/* 2944 */     openSideSheet((DialogFragment)bookmarksDialogFragment, "bookmarks_dialog_" + this.mTabTag, marginTop, marginBottom);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void openRedactionSearchList(SearchRedactionDialogFragment searchRedactionDialogFragment, int marginTop, int marginBottom) {
/* 2955 */     openSideSheet((DialogFragment)searchRedactionDialogFragment, SearchRedactionDialogFragment.TAG + this.mTabTag, marginTop, marginBottom);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void closeSideSheet(String tag) {
/* 2966 */     Fragment fragment = getChildFragmentManager().findFragmentByTag(tag);
/* 2967 */     if (fragment instanceof DialogFragment) {
/* 2968 */       final DialogFragment dialogFragment = (DialogFragment)fragment;
/* 2969 */       if (this.mNavigationList != null) {
/* 2970 */         TransitionSet transitionSet = new TransitionSet();
/* 2971 */         transitionSet.addTransition((Transition)new ChangeBounds());
/* 2972 */         Slide slideFromEnd = new Slide(8388613);
/* 2973 */         slideFromEnd.addTarget((View)this.mNavigationList);
/* 2974 */         transitionSet.addTransition((Transition)slideFromEnd);
/* 2975 */         transitionSet.addTransition((Transition)new Fade());
/* 2976 */         transitionSet.setDuration(250L);
/* 2977 */         transitionSet.addListener(new Transition.TransitionListener()
/*      */             {
/*      */               public void onTransitionStart(@NonNull Transition transition) {}
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*      */               public void onTransitionEnd(@NonNull Transition transition) {
/* 2985 */                 dialogFragment.dismiss();
/*      */               }
/*      */ 
/*      */               
/*      */               public void onTransitionCancel(@NonNull Transition transition) {
/* 2990 */                 dialogFragment.dismiss();
/*      */               }
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*      */               public void onTransitionPause(@NonNull Transition transition) {}
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*      */               public void onTransitionResume(@NonNull Transition transition) {}
/*      */             });
/* 3003 */         TransitionManager.beginDelayedTransition(this.mViewerHost, (Transition)transitionSet);
/* 3004 */         this.mNavigationList.setVisibility(8);
/*      */         
/* 3006 */         resizeOverlay(false);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void closeNavigationList() {
/* 3015 */     closeSideSheet("bookmarks_dialog_" + this.mTabTag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void closeRedactionSearchList() {
/* 3022 */     closeSideSheet(SearchRedactionDialogFragment.TAG + this.mTabTag);
/*      */   }
/*      */   
/*      */   protected void resizeOverlay(boolean navListOpen) {
/* 3026 */     if (this.mOverlayStub != null && this.mOverlayStub.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
/* 3027 */       ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)this.mOverlayStub.getLayoutParams();
/* 3028 */       int endMargin = navListOpen ? this.mOverlayStub.getContext().getResources().getDimensionPixelSize(R.dimen.standard_side_sheet) : 0;
/* 3029 */       boolean changed = false;
/* 3030 */       if (Utils.isJellyBeanMR1()) {
/* 3031 */         if (endMargin != layoutParams.getMarginEnd()) {
/* 3032 */           layoutParams.setMarginEnd(endMargin);
/* 3033 */           changed = true;
/*      */         }
/*      */       
/* 3036 */       } else if (layoutParams.rightMargin != endMargin) {
/* 3037 */         layoutParams.rightMargin = endMargin;
/* 3038 */         changed = true;
/*      */       } 
/*      */       
/* 3041 */       if (changed) {
/* 3042 */         this.mOverlayStub.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
/* 3043 */         if (this.mOverlayStub instanceof ViewGroup) {
/* 3044 */           TransitionManager.beginDelayedTransition((ViewGroup)this.mOverlayStub, (Transition)new ChangeBounds());
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
/*      */   public boolean isAnnotationMode() {
/* 3056 */     return (this.mAnnotationToolbar != null && this.mAnnotationToolbar.getVisibility() == 0);
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
/*      */   public void showAnnotationToolbar(int mode, Annot inkAnnot, int pageNum, ToolManager.ToolMode toolMode, boolean dismissAfterUse) {
/* 3072 */     FragmentActivity fragmentActivity = getActivity();
/* 3073 */     if (fragmentActivity == null) {
/*      */       return;
/*      */     }
/* 3076 */     this.mToolManager.deselectAll();
/* 3077 */     createAnnotationToolbar();
/* 3078 */     this.mAnnotationToolbar.show(mode, inkAnnot, pageNum, toolMode, dismissAfterUse);
/* 3079 */     this.mAnnotationToolbarMode = mode;
/*      */   }
/*      */   
/*      */   public void createAnnotationToolbar() {
/* 3083 */     FragmentActivity fragmentActivity = getActivity();
/* 3084 */     if (fragmentActivity == null) {
/*      */       return;
/*      */     }
/*      */     
/* 3088 */     if (this.mAnnotationToolbar == null) {
/*      */       
/* 3090 */       this.mAnnotationToolbar = (AnnotationToolbar)this.mRootView.findViewById(R.id.annotationToolbar);
/* 3091 */       this.mAnnotationToolbar.setup(this.mToolManager, this);
/* 3092 */       this.mAnnotationToolbar.setButtonStayDown(PdfViewCtrlSettingsManager.getContinuousAnnotationEdit((Context)fragmentActivity));
/* 3093 */       this.mAnnotationToolbar.setAnnotationToolbarListener(new AnnotationToolbar.AnnotationToolbarListener()
/*      */           {
/*      */             public void onAnnotationToolbarClosed() {
/* 3096 */               if (PdfViewCtrlTabFragment.this.mAnnotationToolbarListeners != null) {
/* 3097 */                 for (AnnotationToolbar.AnnotationToolbarListener listener : PdfViewCtrlTabFragment.this.mAnnotationToolbarListeners) {
/* 3098 */                   listener.onAnnotationToolbarClosed();
/*      */                 }
/*      */               }
/*      */               
/* 3102 */               PdfViewCtrlTabFragment.this.setVisibilityOfImaginedToolbar(false);
/*      */             }
/*      */ 
/*      */             
/*      */             public void onAnnotationToolbarShown() {
/* 3107 */               if (PdfViewCtrlTabFragment.this.mAnnotationToolbarListeners != null) {
/* 3108 */                 for (AnnotationToolbar.AnnotationToolbarListener listener : PdfViewCtrlTabFragment.this.mAnnotationToolbarListeners) {
/* 3109 */                   listener.onAnnotationToolbarShown();
/*      */                 }
/*      */               }
/*      */               
/* 3113 */               PdfViewCtrlTabFragment.this.setVisibilityOfImaginedToolbar(true);
/*      */             }
/*      */ 
/*      */             
/*      */             public void onShowAnnotationToolbarByShortcut(int mode) {
/* 3118 */               if (PdfViewCtrlTabFragment.this.mAnnotationToolbarListeners != null) {
/* 3119 */                 for (AnnotationToolbar.AnnotationToolbarListener listener : PdfViewCtrlTabFragment.this.mAnnotationToolbarListeners) {
/* 3120 */                   listener.onShowAnnotationToolbarByShortcut(mode);
/*      */                 }
/*      */               }
/*      */             }
/*      */           });
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void hideAnnotationToolbar() {
/* 3132 */     if (this.mAnnotationToolbar != null) {
/* 3133 */       this.mAnnotationToolbar.close();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onUndoRedoCalled() {
/* 3142 */     refreshPageCount();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void initLayout() {
/* 3149 */     FragmentActivity activity = getActivity();
/* 3150 */     if (activity == null || this.mRootView == null) {
/*      */       return;
/*      */     }
/*      */     
/* 3154 */     loadProgressView();
/* 3155 */     loadOverlayView();
/*      */     
/* 3157 */     this.mDownloadDocumentDialog = new ProgressDialog((Context)activity);
/* 3158 */     this.mDownloadDocumentDialog.setMessage(getString(R.string.download_in_progress_message));
/* 3159 */     this.mDownloadDocumentDialog.setIndeterminate(true);
/* 3160 */     this.mDownloadDocumentDialog.setCancelable(true);
/* 3161 */     this.mDownloadDocumentDialog.setCanceledOnTouchOutside(false);
/* 3162 */     this.mDownloadDocumentDialog.setOnCancelListener(new DialogInterface.OnCancelListener()
/*      */         {
/*      */           public void onCancel(DialogInterface dialog) {
/* 3165 */             if (PdfViewCtrlTabFragment.this.mDownloadDocumentDialog != null && PdfViewCtrlTabFragment.this.mDownloadDocumentDialog.isShowing()) {
/* 3166 */               PdfViewCtrlTabFragment.this.mDownloadDocumentDialog.dismiss();
/*      */             }
/* 3168 */             PdfViewCtrlTabFragment.this.handleOpeningDocumentFailed(4);
/*      */           }
/*      */         });
/*      */     
/* 3172 */     if (Utils.isLollipop()) {
/* 3173 */       final RCContainer rCContainer = new RCContainer((Context)activity);
/* 3174 */       rCContainer.setup((View)this.mViewerHost, this.mToolManager);
/* 3175 */       RichTextViewModel richTextViewModel = (RichTextViewModel)ViewModelProviders.of(activity).get(RichTextViewModel.class);
/* 3176 */       this.mDisposables.add(richTextViewModel.getObservable()
/* 3177 */           .subscribe(new Consumer<RichTextEvent>()
/*      */             {
/*      */               public void accept(RichTextEvent richTextEvent) throws Exception {
/* 3180 */                 switch (richTextEvent.getEventType()) {
/*      */                   case OPEN_TOOLBAR:
/* 3182 */                     rCContainer.showAtLocation(PdfViewCtrlTabFragment.this.mRootView, 0, 0, 0);
/*      */                     break;
/*      */                   case CLOSE_TOOLBAR:
/* 3185 */                     rCContainer.dismiss();
/*      */                     break;
/*      */                   case UPDATE_TOOLBAR:
/* 3188 */                     rCContainer.updateToolbar(richTextEvent.getDecorationTypes());
/*      */                     break;
/*      */                 } 
/*      */               }
/*      */             }new Consumer<Throwable>()
/*      */             {
/*      */               public void accept(Throwable throwable) throws Exception {
/* 3195 */                 AnalyticsHandlerAdapter.getInstance().sendException(new Exception(throwable));
/*      */               }
/*      */             }));
/*      */     } else {
/* 3199 */       this.mToolManager.setShowRichContentOption(false);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected View loadStubProgress() {
/* 3204 */     return ((ViewStub)this.mRootView.findViewById(R.id.stub_progress)).inflate();
/*      */   }
/*      */   
/*      */   protected void loadProgressView() {
/* 3208 */     FragmentActivity fragmentActivity = getActivity();
/* 3209 */     if (fragmentActivity == null || this.mRootView == null) {
/*      */       return;
/*      */     }
/* 3212 */     if (this.mProgressBarLayout != null) {
/*      */       return;
/*      */     }
/* 3215 */     View stub = loadStubProgress();
/*      */     
/* 3217 */     this.mProgressBarLayout = (ContentLoadingRelativeLayout)stub.findViewById(R.id.progressBarLayout);
/* 3218 */     this.mProgressBarLayout.setOnClickListener(new View.OnClickListener()
/*      */         {
/*      */           public void onClick(View view) {
/* 3221 */             if (PdfViewCtrlTabFragment.this.mDocumentConversion != null) {
/*      */               try {
/* 3223 */                 if (PdfViewCtrlTabFragment.sDebug)
/* 3224 */                   Log.i("UNIVERSAL", String.format("Conversion status is %d and label is %s, number of converted pages is %d, has been cancelled? %s", new Object[] {
/* 3225 */                           Integer.valueOf(this.this$0.mDocumentConversion.getConversionStatus()), this.this$0.mDocumentConversion.getProgressLabel(), Integer.valueOf(this.this$0.mDocumentConversion.getNumConvertedPages()), 
/* 3226 */                           this.this$0.mDocumentConversion.isCancelled() ? "YES" : "NO" })); 
/* 3227 */               } catch (Exception e) {
/* 3228 */                 AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */               } 
/*      */             }
/* 3231 */             if (PdfViewCtrlTabFragment.this.mTabListener != null) {
/* 3232 */               PdfViewCtrlTabFragment.this.mTabListener.onTabSingleTapConfirmed();
/*      */             }
/*      */           }
/*      */         });
/*      */   }
/*      */   
/*      */   protected View loadStubPDFViewCtrl() {
/* 3239 */     return ((ViewStub)this.mRootView.findViewById(R.id.stub_pdfviewctrl)).inflate();
/*      */   }
/*      */   
/*      */   protected void loadPDFViewCtrlView() {
/* 3243 */     FragmentActivity fragmentActivity = getActivity();
/* 3244 */     if (fragmentActivity == null || this.mRootView == null) {
/*      */       return;
/*      */     }
/* 3247 */     if (this.mStubPDFViewCtrl != null) {
/*      */       return;
/*      */     }
/* 3250 */     this.mStubPDFViewCtrl = loadStubPDFViewCtrl();
/*      */     
/* 3252 */     this.mViewerHost = (ViewGroup)this.mStubPDFViewCtrl.findViewById(R.id.pdfViewCtrlHost);
/* 3253 */     int pdfViewCtrlResId = (this.mPdfViewCtrlId == 0) ? R.id.pdfviewctrl : this.mPdfViewCtrlId;
/* 3254 */     this.mPdfViewCtrl = (PDFViewCtrl)this.mStubPDFViewCtrl.findViewById(pdfViewCtrlResId);
/* 3255 */     if (null == this.mPdfViewCtrl) {
/*      */       
/* 3257 */       AnalyticsHandlerAdapter.getInstance().sendException(new Exception("loadPDFViewCtrlView PDFViewCtrl is null"));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 3299 */       AppUtils.setupPDFViewCtrl(this.mPdfViewCtrl, getPDFViewCtrlConfig((Context)fragmentActivity));
/* 3300 */       this.mPdfViewCtrl.setBuiltInPageSlidingEnabled(true);
/* 3301 */       this.mPdfViewCtrl.setPageBox(5);
/*      */       
/* 3303 */       updateZoomLimits();
/* 3304 */       PDFViewCtrl.PageViewMode viewMode = PdfViewCtrlSettingsManager.getPageViewMode((Context)fragmentActivity);
/* 3305 */       if (this.mViewerConfig != null && this.mViewerConfig.getPdfViewCtrlConfig() != null) {
/* 3306 */         viewMode = getPDFViewCtrlConfig((Context)fragmentActivity).getPageViewMode();
/*      */       }
/* 3308 */       this.mPdfViewCtrl.setPageViewMode(viewMode);
/* 3309 */       if (this.mViewerConfig != null && this.mViewerConfig.getPdfViewCtrlConfig() != null) {
/* 3310 */         this.mPdfViewCtrl.setImageSmoothing(getPDFViewCtrlConfig((Context)fragmentActivity).isImageSmoothing());
/*      */       }
/* 3312 */       else if (PdfViewCtrlSettingsManager.getImageSmoothing((Context)fragmentActivity)) {
/* 3313 */         this.mPdfViewCtrl.setImageSmoothing(true);
/*      */       } else {
/* 3315 */         this.mPdfViewCtrl.setImageSmoothing(false);
/*      */       }
/*      */     
/* 3318 */     } catch (Exception e) {
/* 3319 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } 
/*      */ 
/*      */     
/* 3323 */     this.mPdfViewCtrl.addPageChangeListener(this);
/* 3324 */     this.mPdfViewCtrl.addDocumentLoadListener(this);
/* 3325 */     this.mPdfViewCtrl.addDocumentDownloadListener(this);
/* 3326 */     this.mPdfViewCtrl.setRenderingListener(this);
/* 3327 */     this.mPdfViewCtrl.addUniversalDocumentConversionListener(this);
/* 3328 */     this.mPdfViewCtrl.setUniversalDocumentProgressIndicatorListener(this);
/*      */ 
/*      */ 
/*      */     
/* 3332 */     int toolManagerResId = (this.mViewerConfig != null && this.mViewerConfig.getToolManagerBuilderStyleRes() != 0) ? this.mViewerConfig.getToolManagerBuilderStyleRes() : R.style.TabFragmentToolManager;
/* 3333 */     ToolManagerBuilder toolManagerBuilder = (this.mViewerConfig == null) ? null : this.mViewerConfig.getToolManagerBuilder();
/* 3334 */     if (toolManagerBuilder == null) {
/*      */       
/* 3336 */       toolManagerBuilder = ToolManagerBuilder.from((Context)fragmentActivity, toolManagerResId);
/* 3337 */       if (this.mViewerConfig == null)
/*      */       {
/* 3339 */         toolManagerBuilder.setCopyAnnot(PdfViewCtrlSettingsManager.getCopyAnnotatedTextToNote((Context)fragmentActivity))
/* 3340 */           .setStylusAsPen(PdfViewCtrlSettingsManager.getStylusAsPen((Context)fragmentActivity))
/* 3341 */           .setInkSmoothing(PdfViewCtrlSettingsManager.getInkSmoothing((Context)fragmentActivity))
/* 3342 */           .setAutoSelect(PdfViewCtrlSettingsManager.isAutoSelectAnnotation((Context)fragmentActivity))
/* 3343 */           .setShowAnnotIndicator(PdfViewCtrlSettingsManager.getShowAnnotationIndicator((Context)fragmentActivity));
/*      */       }
/*      */     } 
/* 3346 */     this.mToolManager = toolManagerBuilder.build(this);
/* 3347 */     this.mToolManager.addToolChangedListener(this);
/* 3348 */     this.mToolManager.setNightMode(isNightModeForToolManager());
/* 3349 */     this.mToolManager.setCacheFileName(this.mTabTag);
/*      */     
/* 3351 */     if (this.mViewerConfig != null && this.mViewerConfig.isUseStandardLibrary()) {
/* 3352 */       this.mToolManager.disableToolMode(new ToolManager.ToolMode[] { ToolManager.ToolMode.RECT_REDACTION, ToolManager.ToolMode.TEXT_REDACTION });
/*      */     }
/* 3354 */     this.mToolManager.setAnnotationToolbarListener(new ToolManager.AnnotationToolbarListener()
/*      */         {
/*      */           public void inkEditSelected(Annot annot, int pageNum) {
/* 3357 */             if (PdfViewCtrlTabFragment.this.mTabListener != null) {
/* 3358 */               PdfViewCtrlTabFragment.this.mTabListener.onInkEditSelected(annot, pageNum);
/*      */             }
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public void openAnnotationToolbar(ToolManager.ToolMode mode) {
/* 3366 */             if (PdfViewCtrlTabFragment.this.mTabListener != null) {
/* 3367 */               PdfViewCtrlTabFragment.this.mTabListener.onOpenAnnotationToolbar(mode);
/*      */             }
/*      */           }
/*      */ 
/*      */           
/*      */           public int annotationToolbarHeight() {
/* 3373 */             FragmentActivity fragmentActivity = PdfViewCtrlTabFragment.this.getActivity();
/* 3374 */             if (fragmentActivity == null) {
/* 3375 */               return 0;
/*      */             }
/* 3377 */             if (PdfViewCtrlTabFragment.this.mAnnotationToolbar != null && PdfViewCtrlTabFragment.this.mAnnotationToolbar.getVisibility() == 0)
/*      */             {
/* 3379 */               return PdfViewCtrlTabFragment.this.mAnnotationToolbar.getHeight();
/*      */             }
/* 3381 */             return -1;
/*      */           }
/*      */ 
/*      */           
/*      */           public int toolbarHeight() {
/* 3386 */             if (PdfViewCtrlTabFragment.this.mTabListener != null) {
/* 3387 */               return PdfViewCtrlTabFragment.this.mTabListener.getToolbarHeight();
/*      */             }
/* 3389 */             return -1;
/*      */           }
/*      */ 
/*      */           
/*      */           public void openEditToolbar(ToolManager.ToolMode mode) {
/* 3394 */             if (PdfViewCtrlTabFragment.this.mTabListener != null) {
/* 3395 */               PdfViewCtrlTabFragment.this.mTabListener.onOpenEditToolbar(mode);
/*      */             }
/*      */           }
/*      */         });
/*      */   }
/*      */   
/*      */   protected PDFViewCtrlConfig getPDFViewCtrlConfig(Context context) {
/* 3402 */     PDFViewCtrlConfig pdfViewCtrlConfig = (this.mViewerConfig != null) ? this.mViewerConfig.getPdfViewCtrlConfig() : null;
/* 3403 */     if (null == pdfViewCtrlConfig) {
/* 3404 */       pdfViewCtrlConfig = PDFViewCtrlConfig.getDefaultConfig(context);
/*      */     }
/* 3406 */     return pdfViewCtrlConfig;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isPasswordProtected() {
/* 3413 */     return !Utils.isNullOrEmpty(this.mPassword);
/*      */   }
/*      */   
/*      */   protected View loadStubPassword(View view) {
/* 3417 */     return ((ViewStub)view.findViewById(R.id.stub_password)).inflate();
/*      */   }
/*      */   
/*      */   protected void loadPasswordView() {
/* 3421 */     FragmentActivity fragmentActivity = getActivity();
/* 3422 */     if (fragmentActivity == null || this.mRootView == null) {
/*      */       return;
/*      */     }
/* 3425 */     if (this.mPasswordLayout != null) {
/*      */       return;
/*      */     }
/* 3428 */     View stub = loadStubPassword(this.mRootView);
/*      */ 
/*      */     
/* 3431 */     this.mPasswordLayout = stub.findViewById(R.id.password_layout);
/* 3432 */     this.mPasswordInput = (EditText)stub.findViewById(R.id.password_input);
/* 3433 */     if (this.mPasswordInput != null) {
/* 3434 */       this.mPasswordInput.setImeOptions(2);
/* 3435 */       this.mPasswordInput.setOnEditorActionListener(new TextView.OnEditorActionListener()
/*      */           {
/*      */             public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
/*      */             {
/* 3439 */               FragmentActivity fragmentActivity = PdfViewCtrlTabFragment.this.getActivity();
/* 3440 */               if (fragmentActivity == null) {
/* 3441 */                 return false;
/*      */               }
/*      */ 
/*      */               
/* 3445 */               if (actionId == 2) {
/*      */                 try {
/* 3447 */                   if (PdfViewCtrlTabFragment.this.mPdfDoc != null && PdfViewCtrlTabFragment.this.mPdfDoc.initStdSecurityHandler(PdfViewCtrlTabFragment.this.mPasswordInput.getText().toString())) {
/*      */                     
/* 3449 */                     PdfViewCtrlTabFragment.this.mPassword = PdfViewCtrlTabFragment.this.mPasswordInput.getText().toString();
/* 3450 */                     PdfViewCtrlTabFragment.this.checkPdfDoc();
/* 3451 */                     InputMethodManager imm = (InputMethodManager)fragmentActivity.getSystemService("input_method");
/* 3452 */                     if (imm != null) {
/* 3453 */                       imm.hideSoftInputFromWindow(PdfViewCtrlTabFragment.this.mPasswordInput.getWindowToken(), 0);
/*      */                     }
/*      */                   } else {
/*      */                     
/* 3457 */                     PdfViewCtrlTabFragment.this.mPasswordInput.setText("");
/* 3458 */                     CommonToast.showText((Context)fragmentActivity, R.string.password_not_valid_message, 0);
/*      */                   } 
/* 3460 */                 } catch (Exception e) {
/* 3461 */                   PdfViewCtrlTabFragment.this.handleOpeningDocumentFailed(1);
/* 3462 */                   AnalyticsHandlerAdapter.getInstance().sendException(e, "checkPdfDoc");
/*      */                 } 
/*      */                 
/* 3465 */                 return true;
/*      */               } 
/* 3467 */               return false;
/*      */             }
/*      */           });
/* 3470 */       this.mPasswordInput.setOnKeyListener(new View.OnKeyListener()
/*      */           {
/*      */             public boolean onKey(View v, int keyCode, KeyEvent event) {
/* 3473 */               FragmentActivity fragmentActivity = PdfViewCtrlTabFragment.this.getActivity();
/* 3474 */               if (fragmentActivity == null) {
/* 3475 */                 return false;
/*      */               }
/*      */               
/* 3478 */               if (keyCode == 66) {
/*      */                 try {
/* 3480 */                   if (PdfViewCtrlTabFragment.this.mPdfDoc != null && PdfViewCtrlTabFragment.this.mPdfDoc.initStdSecurityHandler(PdfViewCtrlTabFragment.this.mPasswordInput.getText().toString())) {
/*      */                     
/* 3482 */                     PdfViewCtrlTabFragment.this.mPassword = PdfViewCtrlTabFragment.this.mPasswordInput.getText().toString();
/* 3483 */                     PdfViewCtrlTabFragment.this.checkPdfDoc();
/* 3484 */                     InputMethodManager imm = (InputMethodManager)fragmentActivity.getSystemService("input_method");
/* 3485 */                     if (imm != null) {
/* 3486 */                       imm.hideSoftInputFromWindow(PdfViewCtrlTabFragment.this.mPasswordInput.getWindowToken(), 0);
/*      */                     }
/*      */                   } else {
/*      */                     
/* 3490 */                     PdfViewCtrlTabFragment.this.mPasswordInput.setText("");
/* 3491 */                     CommonToast.showText((Context)fragmentActivity, R.string.password_not_valid_message, 0);
/*      */                   } 
/* 3493 */                 } catch (Exception e) {
/* 3494 */                   PdfViewCtrlTabFragment.this.handleOpeningDocumentFailed(1);
/* 3495 */                   AnalyticsHandlerAdapter.getInstance().sendException(e, "checkPdfDoc");
/*      */                 } 
/*      */                 
/* 3498 */                 return true;
/*      */               } 
/* 3500 */               return false;
/*      */             }
/*      */           });
/*      */     } 
/* 3504 */     this.mPasswordCheckbox = (CheckBox)stub.findViewById(R.id.password_checkbox);
/* 3505 */     if (this.mPasswordCheckbox != null) {
/* 3506 */       this.mPasswordCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
/*      */           {
/*      */             public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
/* 3509 */               if (!isChecked) {
/*      */                 
/* 3511 */                 PdfViewCtrlTabFragment.this.mPasswordInput.setTransformationMethod((TransformationMethod)PasswordTransformationMethod.getInstance());
/* 3512 */                 PdfViewCtrlTabFragment.this.mPasswordInput.setSelection(PdfViewCtrlTabFragment.this.mPasswordInput.getText().length());
/*      */               } else {
/*      */                 
/* 3515 */                 PdfViewCtrlTabFragment.this.mPasswordInput.setTransformationMethod((TransformationMethod)HideReturnsTransformationMethod.getInstance());
/* 3516 */                 PdfViewCtrlTabFragment.this.mPasswordInput.setSelection(PdfViewCtrlTabFragment.this.mPasswordInput.getText().length());
/*      */               } 
/*      */             }
/*      */           });
/*      */     }
/*      */   }
/*      */   
/*      */   protected View loadStubReflow() {
/* 3524 */     return ((ViewStub)this.mRootView.findViewById(R.id.stub_reflow)).inflate();
/*      */   }
/*      */   
/*      */   protected void loadReflowView() {
/* 3528 */     FragmentActivity fragmentActivity = getActivity();
/* 3529 */     if (fragmentActivity == null || this.mRootView == null) {
/*      */       return;
/*      */     }
/* 3532 */     if (this.mReflowControl != null) {
/*      */       return;
/*      */     }
/* 3535 */     View stub = loadStubReflow();
/* 3536 */     this.mReflowControl = (ReflowControl)stub.findViewById(R.id.reflow_pager);
/*      */   }
/*      */   
/*      */   protected View loadStubOverlay() {
/* 3540 */     return ((ViewStub)this.mRootView.findViewById(R.id.stub_overlay)).inflate();
/*      */   }
/*      */   
/*      */   protected void loadOverlayView() {
/* 3544 */     FragmentActivity activity = getActivity();
/* 3545 */     if (activity == null || this.mRootView == null) {
/*      */       return;
/*      */     }
/* 3548 */     if (this.mSearchOverlay != null) {
/*      */       return;
/*      */     }
/* 3551 */     View stub = loadStubOverlay();
/* 3552 */     this.mOverlayStub = stub;
/*      */     
/* 3554 */     this.mSearchOverlay = (FindTextOverlay)stub.findViewById(R.id.find_text_view);
/* 3555 */     this.mSearchOverlay.setPdfViewCtrl(this.mPdfViewCtrl);
/* 3556 */     this.mSearchOverlay.setFindTextOverlayListener(new FindTextOverlay.FindTextOverlayListener()
/*      */         {
/*      */           public void onGotoNextSearch(boolean useFullTextResults)
/*      */           {
/* 3560 */             SearchResultsView.SearchResultStatus status = SearchResultsView.SearchResultStatus.NOT_HANDLED;
/* 3561 */             if (useFullTextResults && 
/* 3562 */               PdfViewCtrlTabFragment.this.mTabListener != null) {
/* 3563 */               status = PdfViewCtrlTabFragment.this.mTabListener.onFullTextSearchFindText(false);
/*      */             }
/*      */             
/* 3566 */             if (status != SearchResultsView.SearchResultStatus.HANDLED && 
/* 3567 */               PdfViewCtrlTabFragment.this.mSearchOverlay != null) {
/* 3568 */               PdfViewCtrlTabFragment.this.mSearchOverlay.findText();
/*      */             }
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void onGotoPreviousSearch(boolean useFullTextResults) {
/* 3575 */             SearchResultsView.SearchResultStatus status = SearchResultsView.SearchResultStatus.NOT_HANDLED;
/* 3576 */             if (useFullTextResults && 
/* 3577 */               PdfViewCtrlTabFragment.this.mTabListener != null) {
/* 3578 */               status = PdfViewCtrlTabFragment.this.mTabListener.onFullTextSearchFindText(true);
/*      */             }
/*      */             
/* 3581 */             if (status != SearchResultsView.SearchResultStatus.HANDLED && 
/* 3582 */               PdfViewCtrlTabFragment.this.mSearchOverlay != null) {
/* 3583 */               if (status == SearchResultsView.SearchResultStatus.USE_FINDTEXT_FROM_END) {
/* 3584 */                 PdfViewCtrlTabFragment.this.mSearchOverlay.findText(PdfViewCtrlTabFragment.this.mPdfViewCtrl.getPageCount());
/*      */               } else {
/* 3586 */                 PdfViewCtrlTabFragment.this.mSearchOverlay.findText();
/*      */               } 
/*      */             }
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void onSearchProgressShow() {
/* 3594 */             if (PdfViewCtrlTabFragment.this.mTabListener != null) {
/* 3595 */               PdfViewCtrlTabFragment.this.mTabListener.onSearchProgressShow();
/*      */             }
/*      */           }
/*      */ 
/*      */           
/*      */           public void onSearchProgressHide() {
/* 3601 */             if (PdfViewCtrlTabFragment.this.mTabListener != null) {
/* 3602 */               PdfViewCtrlTabFragment.this.mTabListener.onSearchProgressHide();
/*      */             }
/*      */           }
/*      */         });
/*      */     
/* 3607 */     this.mBottomNavBar = (ThumbnailSlider)stub.findViewById(R.id.thumbseekbar);
/* 3608 */     this.mBottomNavBar.setOnMenuItemClickedListener(new ThumbnailSlider.OnMenuItemClickedListener()
/*      */         {
/*      */           public void onMenuItemClicked(int menuItemPosition) {
/* 3611 */             if (menuItemPosition == 0) {
/* 3612 */               if (PdfViewCtrlTabFragment.this.mTabListener != null) {
/* 3613 */                 PdfViewCtrlTabFragment.this.mTabListener.onPageThumbnailOptionSelected(false, null);
/*      */               }
/*      */             }
/* 3616 */             else if (PdfViewCtrlTabFragment.this.mTabListener != null) {
/* 3617 */               PdfViewCtrlTabFragment.this.mTabListener.onOutlineOptionSelected();
/*      */             } 
/*      */           }
/*      */         });
/*      */ 
/*      */     
/* 3623 */     this.mPageNumberIndicator = (PageIndicatorLayout)stub.findViewById(R.id.page_number_indicator_view);
/* 3624 */     this.mPageNumberIndicator.setVisibility(0);
/* 3625 */     this.mPageNumberIndicator.setPdfViewCtrl(this.mPdfViewCtrl);
/* 3626 */     animatePageIndicator(false);
/* 3627 */     this.mPageNumberIndicator.setOnClickListener(new View.OnClickListener()
/*      */         {
/*      */           public void onClick(View v) {
/* 3630 */             FragmentActivity fragmentActivity = PdfViewCtrlTabFragment.this.getActivity();
/* 3631 */             if (fragmentActivity == null) {
/*      */               return;
/*      */             }
/*      */             
/* 3635 */             DialogGoToPage dlgGotoPage = new DialogGoToPage(PdfViewCtrlTabFragment.this, (Context)fragmentActivity, PdfViewCtrlTabFragment.this.mPdfViewCtrl, PdfViewCtrlTabFragment.this.mReflowControl);
/* 3636 */             dlgGotoPage.show();
/*      */           }
/*      */         });
/*      */     
/* 3640 */     this.mPageNumberIndicatorAll = this.mPageNumberIndicator.getIndicator();
/* 3641 */     if (Utils.isJellyBeanMR1()) {
/* 3642 */       this.mPageNumberIndicatorAll.setTextDirection(3);
/*      */     }
/*      */     
/* 3645 */     this.mPageNumberIndicatorSpinner = this.mPageNumberIndicator.getSpinner();
/*      */ 
/*      */     
/* 3648 */     boolean canShow = (this.mViewerConfig == null || this.mViewerConfig.isPageStackEnabled());
/* 3649 */     stub.findViewById(R.id.page_forward_button_container).setVisibility(canShow ? 0 : 8);
/* 3650 */     stub.findViewById(R.id.page_back_button_container).setVisibility(canShow ? 0 : 8);
/*      */ 
/*      */     
/* 3653 */     this.mPageBackStack = new ArrayDeque<>();
/* 3654 */     this.mPageBackButton = (FloatingActionButton)stub.findViewById(R.id.page_back_button);
/* 3655 */     this.mPageBackButton.hide();
/* 3656 */     this.mPageBackButton.setOnClickListener(new View.OnClickListener()
/*      */         {
/*      */           public void onClick(View v) {
/* 3659 */             PdfViewCtrlTabFragment.this.jumpPageBack();
/*      */           }
/*      */         });
/*      */ 
/*      */     
/* 3664 */     this.mPageForwardStack = new ArrayDeque<>();
/* 3665 */     this.mPageForwardButton = (FloatingActionButton)stub.findViewById(R.id.page_forward_button);
/* 3666 */     this.mPageForwardButton.hide();
/* 3667 */     this.mPageForwardButton.setOnClickListener(new View.OnClickListener()
/*      */         {
/*      */           public void onClick(View v) {
/* 3670 */             PdfViewCtrlTabFragment.this.jumpPageForward();
/*      */           }
/*      */         });
/*      */     
/* 3674 */     if (Utils.isNougat()) {
/* 3675 */       View[] views = { (View)this.mBottomNavBar, (View)this.mPageNumberIndicatorAll, (View)this.mPageBackButton, (View)this.mPageForwardButton };
/* 3676 */       for (View v : views) {
/* 3677 */         v.setOnGenericMotionListener(new View.OnGenericMotionListener()
/*      */             {
/*      */               public boolean onGenericMotion(View v, MotionEvent event) {
/* 3680 */                 FragmentActivity fragmentActivity = PdfViewCtrlTabFragment.this.getActivity();
/* 3681 */                 if (fragmentActivity == null || !Utils.isNougat()) {
/* 3682 */                   return false;
/*      */                 }
/*      */                 
/* 3685 */                 PdfViewCtrlTabFragment.this.getToolManager().onChangePointerIcon(PointerIcon.getSystemIcon((Context)fragmentActivity, 1002));
/* 3686 */                 return true;
/*      */               }
/*      */             });
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void jumpPageBack() {
/* 3696 */     if (this.mTabListener != null) {
/* 3697 */       this.mTabListener.resetHideToolbarsTimer();
/*      */     }
/*      */     
/* 3700 */     if (!this.mPageBackStack.isEmpty()) {
/* 3701 */       PageBackButtonInfo previousPageInfo = this.mPageBackStack.pop();
/* 3702 */       PageBackButtonInfo currentPageInfo = getCurrentPageInfo();
/* 3703 */       boolean successfulPageChange = false;
/*      */ 
/*      */ 
/*      */       
/* 3707 */       if (previousPageInfo.pageNum == currentPageInfo.pageNum) {
/* 3708 */         if (!this.mPageBackStack.isEmpty()) {
/* 3709 */           previousPageInfo = this.mPageBackStack.pop();
/*      */         }
/*      */         else {
/*      */           
/* 3713 */           successfulPageChange = true;
/*      */         } 
/*      */       }
/*      */       
/* 3717 */       if (!successfulPageChange && previousPageInfo.pageNum > 0 && previousPageInfo.pageNum <= this.mPageCount) {
/* 3718 */         successfulPageChange = setPageState(previousPageInfo);
/*      */       }
/*      */ 
/*      */       
/* 3722 */       if (successfulPageChange && (this.mPageForwardStack.isEmpty() || ((PageBackButtonInfo)this.mPageForwardStack.peek()).pageNum != currentPageInfo.pageNum)) {
/* 3723 */         this.mPageForwardStack.push(currentPageInfo);
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 3729 */     if (this.mPageBackStack.isEmpty()) {
/* 3730 */       hidePageBackButton();
/*      */     }
/*      */     
/* 3733 */     if (!this.mPageForwardStack.isEmpty()) {
/* 3734 */       this.mPageForwardButton.show();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void jumpPageForward() {
/* 3741 */     if (this.mTabListener != null) {
/* 3742 */       this.mTabListener.resetHideToolbarsTimer();
/*      */     }
/*      */     
/* 3745 */     if (!this.mPageForwardStack.isEmpty()) {
/* 3746 */       PageBackButtonInfo nextPageInfo = this.mPageForwardStack.pop();
/* 3747 */       PageBackButtonInfo currentPageInfo = getCurrentPageInfo();
/* 3748 */       boolean successfulPageChange = false;
/*      */       
/* 3750 */       if (currentPageInfo.pageNum == nextPageInfo.pageNum) {
/* 3751 */         if (!this.mPageForwardStack.isEmpty()) {
/* 3752 */           nextPageInfo = this.mPageForwardStack.pop();
/*      */         } else {
/* 3754 */           successfulPageChange = true;
/*      */         } 
/*      */       }
/*      */       
/* 3758 */       if (!successfulPageChange && nextPageInfo.pageNum > 0 && nextPageInfo.pageNum <= this.mPageCount) {
/* 3759 */         successfulPageChange = setPageState(nextPageInfo);
/*      */       }
/*      */ 
/*      */       
/* 3763 */       if (successfulPageChange && (this.mPageBackStack.isEmpty() || ((PageBackButtonInfo)this.mPageBackStack.peek()).pageNum != currentPageInfo.pageNum)) {
/* 3764 */         this.mPageBackStack.push(currentPageInfo);
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 3770 */     if (this.mPageForwardStack.isEmpty()) {
/* 3771 */       hidePageForwardButton();
/*      */     }
/*      */     
/* 3774 */     if (!this.mPageBackStack.isEmpty()) {
/* 3775 */       this.mPageBackButton.show();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void gotoNextSearch() {
/* 3783 */     if (this.mSearchOverlay != null) {
/* 3784 */       this.mSearchOverlay.gotoNextSearch();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void gotoPreviousSearch() {
/* 3792 */     if (this.mSearchOverlay != null) {
/* 3793 */       this.mSearchOverlay.gotoPreviousSearch();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void cancelFindText() {
/* 3801 */     if (this.mSearchOverlay != null) {
/* 3802 */       this.mSearchOverlay.cancelFindText();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSearchQuery(String text) {
/* 3812 */     if (this.mSearchOverlay != null) {
/* 3813 */       this.mSearchOverlay.setSearchQuery(text);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSearchMatchCase(boolean matchCase) {
/* 3823 */     if (this.mSearchOverlay != null) {
/* 3824 */       this.mSearchOverlay.setSearchMatchCase(matchCase);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSearchWholeWord(boolean wholeWord) {
/* 3834 */     if (this.mSearchOverlay != null) {
/* 3835 */       this.mSearchOverlay.setSearchWholeWord(wholeWord);
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
/*      */   public void setSearchSettings(boolean matchCase, boolean wholeWord) {
/* 3847 */     if (this.mSearchOverlay != null) {
/* 3848 */       this.mSearchOverlay.setSearchSettings(matchCase, wholeWord);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void highlightSearchResults() {
/* 3856 */     if (this.mSearchOverlay != null) {
/* 3857 */       this.mSearchOverlay.highlightSearchResults();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void resetFullTextResults() {
/* 3865 */     if (this.mSearchOverlay != null) {
/* 3866 */       this.mSearchOverlay.resetFullTextResults();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void queryTextSubmit(String text) {
/* 3876 */     if (this.mSearchOverlay != null) {
/* 3877 */       this.mSearchOverlay.queryTextSubmit(text);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void exitSearchMode() {
/* 3885 */     if (this.mSearchOverlay != null) {
/* 3886 */       this.mSearchOverlay.exitSearchMode();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void resetHidePageNumberIndicatorTimer() {
/* 3894 */     if (!this.mDocumentLoaded) {
/*      */       return;
/*      */     }
/* 3897 */     stopHidePageNumberIndicatorTimer();
/*      */     
/* 3899 */     if (this.mPageNumberIndicator != null) {
/* 3900 */       boolean canShow = (this.mViewerConfig == null || this.mViewerConfig.isShowPageNumberIndicator());
/* 3901 */       animatePageIndicator(canShow);
/*      */     } 
/* 3903 */     if (this.mHidePageNumberAndPageBackButtonHandler != null) {
/* 3904 */       this.mHidePageNumberAndPageBackButtonHandler.postDelayed(this.mHidePageNumberAndPageBackButtonRunnable, 5000L);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void stopHidePageNumberIndicatorTimer() {
/* 3912 */     if (this.mHidePageNumberAndPageBackButtonHandler != null) {
/* 3913 */       this.mHidePageNumberAndPageBackButtonHandler.removeCallbacksAndMessages(null);
/*      */     }
/*      */   }
/*      */   
/*      */   private void stopConversionFinishedTimer() {
/* 3918 */     if (this.mConversionFinishedMessageHandler != null) {
/* 3919 */       this.mConversionFinishedMessageHandler.removeCallbacksAndMessages(null);
/*      */     }
/*      */   }
/*      */   
/*      */   private void stopConversionSpinningIndicator() {
/* 3924 */     if (this.mPageNumberIndicatorConversionSpinningHandler != null) {
/* 3925 */       this.mPageNumberIndicatorConversionSpinningHandler.removeCallbacksAndMessages(null);
/*      */     }
/* 3927 */     if (this.mPageNumberIndicatorSpinner != null) {
/* 3928 */       this.mPageNumberIndicatorSpinner.setVisibility(8);
/*      */     }
/* 3930 */     this.mIsPageNumberIndicatorConversionSpinningRunning = false;
/*      */   }
/*      */   
/*      */   protected void stopHandlers() {
/* 3934 */     stopHidePageNumberIndicatorTimer();
/* 3935 */     stopConversionSpinningIndicator();
/* 3936 */     stopConversionFinishedTimer();
/* 3937 */     stopAutoSavingTimer();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setCurrentPageHelper(int nextPageNum, boolean setPDFViewCtrl, PageBackButtonInfo landingPageInfo) {
/* 3944 */     setCurrentPageHelper(nextPageNum, setPDFViewCtrl);
/* 3945 */     this.mPageBackStack.push(landingPageInfo);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCurrentPageHelper(int nextPageNum, boolean setPDFViewCtrl) {
/* 3955 */     if (this.mPdfViewCtrl == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 3960 */     PageBackButtonInfo departurePageInfo = new PageBackButtonInfo();
/* 3961 */     boolean pageChangeOccurred = false;
/*      */ 
/*      */     
/* 3964 */     if (setPDFViewCtrl) {
/* 3965 */       departurePageInfo = getCurrentPageInfo();
/*      */       
/* 3967 */       this.mPdfViewCtrl.setCurrentPage(nextPageNum);
/*      */ 
/*      */     
/*      */     }
/* 3971 */     else if (nextPageNum == this.mCurrentPageInfo.pageNum) {
/*      */       
/* 3973 */       departurePageInfo.copyPageInfo(this.mPreviousPageInfo);
/* 3974 */       pageChangeOccurred = true;
/*      */     } else {
/*      */       
/* 3977 */       departurePageInfo = this.mCurrentPageInfo;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3983 */     if (departurePageInfo.pageNum > 0 && departurePageInfo.pageNum <= this.mPageCount && departurePageInfo.pageNum != nextPageNum) {
/*      */ 
/*      */       
/* 3986 */       if (this.mPageBackStack.isEmpty() || ((PageBackButtonInfo)this.mPageBackStack.peek()).pageNum != departurePageInfo.pageNum) {
/*      */         
/* 3988 */         if (this.mPageBackStack.size() >= 50) {
/* 3989 */           this.mPageBackStack.removeLast();
/*      */         
/*      */         }
/*      */       
/*      */       }
/*      */       else {
/*      */         
/* 3996 */         this.mPageBackStack.pop();
/*      */       } 
/*      */       
/* 3999 */       this.mPageBackStack.push(departurePageInfo);
/* 4000 */       if (!pageChangeOccurred) {
/* 4001 */         this.mPushNextPageOnStack = Boolean.valueOf(true);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 4006 */       if (!this.mPageForwardStack.isEmpty()) {
/* 4007 */         this.mPageForwardStack.clear();
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 4012 */     if (!this.mPageBackStack.isEmpty() && 
/* 4013 */       !this.mSearchOverlay.isShown()) {
/* 4014 */       this.mPageBackButton.show();
/*      */     }
/*      */ 
/*      */     
/* 4018 */     if (this.mPageForwardStack.isEmpty()) {
/* 4019 */       hidePageForwardButton();
/*      */     }
/*      */ 
/*      */     
/* 4023 */     if (this.mIsReflowMode && this.mReflowControl != null) {
/*      */       
/*      */       try {
/*      */ 
/*      */         
/* 4028 */         this.mReflowControl.setCurrentPage(this.mPdfViewCtrl.getCurrentPage());
/* 4029 */       } catch (Exception e) {
/* 4030 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateCurrentPageInfo() {
/* 4039 */     this.mCurrentPageInfo = getCurrentPageInfo();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PageBackButtonInfo getCurrentPageInfo() {
/* 4048 */     PageBackButtonInfo pageState = new PageBackButtonInfo();
/*      */     
/* 4050 */     if (this.mPdfViewCtrl != null) {
/* 4051 */       pageState.zoom = this.mPdfViewCtrl.getZoom();
/* 4052 */       pageState.pageRotation = this.mPdfViewCtrl.getPageRotation();
/* 4053 */       pageState.pagePresentationMode = this.mPdfViewCtrl.getPagePresentationMode();
/* 4054 */       pageState.hScrollPos = this.mPdfViewCtrl.getHScrollPos();
/* 4055 */       pageState.vScrollPos = this.mPdfViewCtrl.getVScrollPos();
/* 4056 */       pageState.pageNum = this.mPdfViewCtrl.getCurrentPage();
/*      */     } 
/*      */     
/* 4059 */     return pageState;
/*      */   }
/*      */   
/*      */   private void hidePageForwardButton() {
/* 4063 */     this.mPageForwardButton.hide();
/*      */   }
/*      */   
/*      */   private void hidePageBackButton() {
/* 4067 */     this.mPageBackButton.hide();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearPageBackAndForwardStacks() {
/* 4074 */     hidePageBackButton();
/* 4075 */     hidePageForwardButton();
/*      */ 
/*      */     
/* 4078 */     this.mPageBackStack.clear();
/* 4079 */     this.mPageForwardStack.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isTabReadOnly() {
/* 4088 */     return (this.mDocumentState == 5 || this.mDocumentState == 6 || this.mDocumentState == 3 || this.mDocumentState == 4 || this.mDocumentState == 8 || this.mDocumentState == 9 || this.mDocumentState == 10 || (this.mToolManager != null && this.mToolManager
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 4095 */       .isReadOnly()));
/*      */   }
/*      */ 
/*      */   
/*      */   private RegionSingleTap getRegionTap(int x, int y) {
/* 4100 */     RegionSingleTap regionSingleTap = RegionSingleTap.Middle;
/*      */     
/* 4102 */     if (this.mPdfViewCtrl != null) {
/* 4103 */       float width = this.mPdfViewCtrl.getWidth();
/* 4104 */       float widthThresh = width * 0.14285715F;
/* 4105 */       if (x <= widthThresh) {
/* 4106 */         regionSingleTap = RegionSingleTap.Left;
/* 4107 */       } else if (x >= width - widthThresh) {
/* 4108 */         regionSingleTap = RegionSingleTap.Right;
/*      */       } 
/*      */     } 
/*      */     
/* 4112 */     return regionSingleTap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isContinuousPageMode() {
/* 4122 */     if (this.mPdfViewCtrl == null) {
/* 4123 */       return false;
/*      */     }
/*      */     
/* 4126 */     PDFViewCtrl.PagePresentationMode mode = this.mPdfViewCtrl.getPagePresentationMode();
/* 4127 */     return (mode == PDFViewCtrl.PagePresentationMode.SINGLE_CONT || mode == PDFViewCtrl.PagePresentationMode.FACING_CONT || mode == PDFViewCtrl.PagePresentationMode.FACING_COVER_CONT);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isNonContinuousVerticalPageMode() {
/* 4133 */     if (this.mPdfViewCtrl == null) {
/* 4134 */       return false;
/*      */     }
/* 4136 */     PDFViewCtrl.PagePresentationMode mode = this.mPdfViewCtrl.getPagePresentationMode();
/* 4137 */     return (mode == PDFViewCtrl.PagePresentationMode.SINGLE_VERT || mode == PDFViewCtrl.PagePresentationMode.FACING_VERT || mode == PDFViewCtrl.PagePresentationMode.FACING_COVER_VERT);
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
/*      */   public boolean isSinglePageMode() {
/* 4149 */     return (!isContinuousPageMode() && !isNonContinuousVerticalPageMode());
/*      */   }
/*      */   
/*      */   private boolean setPageState(PageBackButtonInfo pageStateInfo) {
/* 4153 */     if (this.mPdfViewCtrl == null) {
/* 4154 */       return false;
/*      */     }
/*      */     
/* 4157 */     boolean successfulPageChange = this.mPdfViewCtrl.setCurrentPage(pageStateInfo.pageNum);
/*      */     
/* 4159 */     if (this.mIsReflowMode && this.mReflowControl != null) {
/*      */       try {
/* 4161 */         this.mReflowControl.setCurrentPage(pageStateInfo.pageNum);
/* 4162 */       } catch (Exception e) {
/* 4163 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4170 */     if (successfulPageChange && pageStateInfo.pageRotation == this.mPdfViewCtrl.getPageRotation() && pageStateInfo.pagePresentationMode == this.mPdfViewCtrl
/* 4171 */       .getPagePresentationMode()) {
/*      */ 
/*      */       
/* 4174 */       double desiredHPos = pageStateInfo.hScrollPos;
/* 4175 */       double desiredVPos = pageStateInfo.vScrollPos;
/* 4176 */       if (pageStateInfo.zoom > 0.0D) {
/* 4177 */         this.mPdfViewCtrl.setZoom(pageStateInfo.zoom);
/* 4178 */         if (Math.abs(this.mPdfViewCtrl.getZoom() - pageStateInfo.zoom) > 0.01D) {
/* 4179 */           double zoomDifference = this.mPdfViewCtrl.getZoom() / pageStateInfo.zoom;
/* 4180 */           desiredHPos *= zoomDifference;
/* 4181 */           desiredVPos *= zoomDifference;
/*      */         } 
/*      */       } 
/*      */       
/* 4185 */       if (desiredHPos > 0.0D || desiredVPos > 0.0D) {
/* 4186 */         this.mPdfViewCtrl.scrollTo((int)desiredHPos, (int)desiredVPos);
/*      */       }
/*      */     } 
/*      */     
/* 4190 */     return successfulPageChange;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isReflowMode() {
/* 4199 */     return this.mIsReflowMode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean toggleReflow() {
/* 4209 */     this.mIsReflowMode = !this.mIsReflowMode;
/* 4210 */     setReflowMode(this.mIsReflowMode);
/* 4211 */     return this.mIsReflowMode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getReflowTextSize() {
/*      */     try {
/* 4221 */       if (this.mReflowControl != null && this.mReflowControl.isReady()) {
/* 4222 */         return this.mReflowControl.getTextSizeInPercent();
/*      */       }
/* 4224 */     } catch (Exception e) {
/* 4225 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } 
/* 4227 */     return 100;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void zoomInOutReflow(boolean flagZoomIn) {
/* 4236 */     if (this.mIsReflowMode && this.mReflowControl != null) {
/*      */       try {
/* 4238 */         if (flagZoomIn) {
/* 4239 */           this.mReflowControl.zoomIn();
/*      */         } else {
/* 4241 */           this.mReflowControl.zoomOut();
/*      */         } 
/* 4243 */       } catch (Exception e) {
/* 4244 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setReflowMode(boolean isReflowMode) {
/* 4255 */     FragmentActivity fragmentActivity = getActivity();
/* 4256 */     if (fragmentActivity == null || this.mPdfViewCtrl == null || this.mPdfDoc == null) {
/*      */       return;
/*      */     }
/* 4259 */     loadReflowView();
/* 4260 */     if (this.mReflowControl == null) {
/*      */       return;
/*      */     }
/*      */     
/* 4264 */     this.mIsReflowMode = isReflowMode;
/* 4265 */     if (this.mIsReflowMode) {
/* 4266 */       int pageNum = this.mPdfViewCtrl.getCurrentPage();
/* 4267 */       this.mReflowControl.setup(this.mPdfViewCtrl.getDoc(), this.mOnPostProcessColorListener);
/*      */ 
/*      */       
/* 4270 */       setRtlMode(this.mIsRtlMode);
/* 4271 */       this.mReflowControl.clearReflowOnTapListeners();
/* 4272 */       this.mReflowControl.clearOnPageChangeListeners();
/* 4273 */       this.mReflowControl.addReflowOnTapListener(this);
/* 4274 */       this.mReflowControl.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
/*      */           {
/*      */             public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
/*      */ 
/*      */ 
/*      */             
/*      */             public void onPageSelected(int position) {
/* 4281 */               if (!PdfViewCtrlTabFragment.this.mIsReflowMode) {
/*      */                 return;
/*      */               }
/*      */               
/* 4285 */               if (PdfViewCtrlTabFragment.this.mIsRtlMode) {
/* 4286 */                 position = PdfViewCtrlTabFragment.this.mPageCount - 1 - position;
/*      */               }
/* 4288 */               int curPage = position + 1;
/* 4289 */               int oldPage = PdfViewCtrlTabFragment.this.mPdfViewCtrl.getCurrentPage();
/*      */               
/* 4291 */               PdfViewCtrlTabFragment.this.updatePageIndicator();
/*      */ 
/*      */ 
/*      */               
/*      */               try {
/* 4296 */                 if (PdfViewCtrlTabFragment.this.mReflowControl.isInternalLinkClicked()) {
/* 4297 */                   PdfViewCtrlTabFragment.this.mReflowControl.resetInternalLinkClicked();
/* 4298 */                   if (oldPage != curPage) {
/* 4299 */                     PdfViewCtrlTabFragment.this.setCurrentPageHelper(oldPage, false, PdfViewCtrlTabFragment.this.getCurrentPageInfo());
/*      */                   }
/*      */                 } 
/* 4302 */                 PdfViewCtrlTabFragment.this.mReflowControl.updateTextSize();
/* 4303 */               } catch (Exception e) {
/* 4304 */                 AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */               } 
/*      */               
/* 4307 */               PdfViewCtrlTabFragment.this.mPdfViewCtrl.setCurrentPage(curPage);
/*      */               
/* 4309 */               FragmentActivity fragmentActivity = PdfViewCtrlTabFragment.this.getActivity();
/* 4310 */               if (fragmentActivity != null && PdfViewCtrlSettingsManager.getPageNumberOverlayOption((Context)fragmentActivity)) {
/* 4311 */                 PdfViewCtrlTabFragment.this.resetHidePageNumberIndicatorTimer();
/*      */               }
/*      */             }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             public void onPageScrollStateChanged(int state) {}
/*      */           });
/* 4321 */       save(false, true, false);
/*      */       try {
/* 4323 */         this.mReflowControl.notifyPagesModified();
/* 4324 */         this.mReflowControl.setCurrentPage(pageNum);
/* 4325 */         this.mReflowControl.enableTurnPageOnTap(PdfViewCtrlSettingsManager.getAllowPageChangeOnTap((Context)fragmentActivity));
/* 4326 */       } catch (Exception e) {
/* 4327 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */       } 
/* 4329 */       this.mReflowControl.setVisibility(0);
/* 4330 */       updateReflowColorMode();
/* 4331 */       this.mPdfViewCtrl.setCurrentPage(pageNum);
/*      */       
/* 4333 */       updatePageIndicator();
/*      */ 
/*      */       
/* 4336 */       setViewerHostVisible(false);
/* 4337 */       this.mPdfViewCtrl.pause();
/*      */     } else {
/*      */       
/* 4340 */       this.mReflowControl.cleanUp();
/* 4341 */       this.mReflowControl.setVisibility(8);
/* 4342 */       this.mReflowControl.removeReflowOnTapListener(this);
/*      */ 
/*      */       
/* 4345 */       this.mPdfViewCtrl.resume();
/* 4346 */       setViewerHostVisible(true);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean toggleRtlMode() {
/* 4357 */     setRtlMode(!this.mIsRtlMode);
/* 4358 */     return this.mIsRtlMode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @TargetApi(17)
/*      */   public void setRtlMode(boolean isRtlMode) {
/* 4368 */     if (this.mPdfViewCtrl == null) {
/*      */       return;
/*      */     }
/*      */     
/* 4372 */     this.mIsRtlMode = isRtlMode;
/* 4373 */     PdfViewCtrlSettingsManager.updateInRTLMode(this.mPdfViewCtrl.getContext(), isRtlMode);
/*      */     try {
/* 4375 */       if (this.mReflowControl != null && this.mReflowControl.isReady()) {
/* 4376 */         this.mReflowControl.setRightToLeftDirection(isRtlMode);
/* 4377 */         if (this.mIsReflowMode && this.mPdfViewCtrl != null) {
/* 4378 */           int pageNum = this.mPdfViewCtrl.getCurrentPage();
/* 4379 */           this.mReflowControl.reset();
/* 4380 */           this.mReflowControl.setCurrentPage(pageNum);
/* 4381 */           this.mPdfViewCtrl.setCurrentPage(pageNum);
/*      */         } 
/*      */       } 
/* 4384 */       if (this.mPdfViewCtrl != null) {
/* 4385 */         this.mPdfViewCtrl.setRightToLeftLanguage(isRtlMode);
/*      */       }
/* 4387 */     } catch (Exception e) {
/* 4388 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } 
/* 4390 */     if (Utils.isJellyBeanMR1() && this.mBottomNavBar != null) {
/* 4391 */       Configuration config = getResources().getConfiguration();
/* 4392 */       if ((config.getLayoutDirection() == 1 && !isRtlMode) || (config
/* 4393 */         .getLayoutDirection() != 1 && isRtlMode)) {
/* 4394 */         this.mBottomNavBar.setReversed(true);
/*      */       } else {
/* 4396 */         this.mBottomNavBar.setReversed(false);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRtlMode() {
/* 4407 */     return this.mIsRtlMode;
/*      */   }
/*      */   
/*      */   protected void doDocumentLoaded() {
/* 4411 */     FragmentActivity fragmentActivity = getActivity();
/* 4412 */     if (fragmentActivity == null || this.mPdfViewCtrl == null) {
/*      */       return;
/*      */     }
/*      */     
/* 4416 */     if (this.mDocumentLoaded) {
/*      */       return;
/*      */     }
/* 4419 */     this.mDocumentLoaded = true;
/*      */     
/* 4421 */     this.mRageScrollingCount = 0;
/*      */ 
/*      */     
/* 4424 */     if (this.mReflowControl != null) {
/* 4425 */       this.mReflowControl.setup(this.mPdfViewCtrl.getDoc(), this.mOnPostProcessColorListener);
/*      */     }
/*      */     
/* 4428 */     setViewerHostVisible(true);
/* 4429 */     PdfViewCtrlTabInfo info = PdfViewCtrlTabsManager.getInstance().getPdfFViewCtrlTabInfo((Context)fragmentActivity, this.mTabTag);
/* 4430 */     if (info == null && this.mStateEnabled && 
/* 4431 */       PdfViewCtrlSettingsManager.getRememberLastPage((Context)fragmentActivity)) {
/* 4432 */       info = getInfoFromRecentList(getCurrentFileInfo());
/*      */     }
/*      */ 
/*      */     
/* 4436 */     boolean skipSetState = false;
/* 4437 */     if (!this.mStateEnabled) {
/* 4438 */       skipSetState = true;
/*      */     }
/* 4440 */     if (this.mTabConversionTempPath == null && (this.mDocumentState == 9 || this.mDocumentState == 8))
/*      */     {
/*      */ 
/*      */       
/* 4444 */       skipSetState = true;
/*      */     }
/*      */     
/* 4447 */     if (info != null && !skipSetState) {
/*      */       PDFViewCtrl.PagePresentationMode pagePresentationMode;
/*      */       
/* 4450 */       if (info.hasPagePresentationMode()) {
/* 4451 */         pagePresentationMode = info.getPagePresentationMode();
/*      */       } else {
/*      */         
/* 4454 */         String mode = PdfViewCtrlSettingsManager.getViewMode((Context)fragmentActivity);
/* 4455 */         pagePresentationMode = getPagePresentationModeFromSettings(mode);
/*      */       } 
/* 4457 */       updateViewMode(pagePresentationMode);
/*      */ 
/*      */ 
/*      */       
/* 4461 */       if ((this.mViewerConfig != null && this.mViewerConfig.isShowRightToLeftOption()) || 
/* 4462 */         PdfViewCtrlSettingsManager.hasRtlModeOption((Context)fragmentActivity)) {
/* 4463 */         PdfViewCtrlSettingsManager.updateRtlModeOption((Context)fragmentActivity, true);
/*      */         
/* 4465 */         if (!info.isRtlMode) {
/* 4466 */           info.isRtlMode = (this.mViewerConfig != null && this.mViewerConfig.isRightToLeftModeEnabled());
/*      */         }
/* 4468 */         setRtlMode(info.isRtlMode);
/*      */       } 
/*      */       
/* 4471 */       if (info.lastPage > 0) {
/* 4472 */         this.mPdfViewCtrl.setCurrentPage(info.lastPage);
/*      */       }
/* 4474 */       else if (this.mViewerConfig != null) {
/*      */         
/* 4476 */         int lastOpenUrlPage = ViewerUtils.getLastPageForURL((Context)fragmentActivity, this.mOpenUrlLink);
/* 4477 */         if (lastOpenUrlPage > 0) {
/* 4478 */           this.mPdfViewCtrl.setCurrentPage(lastOpenUrlPage);
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/*      */       try {
/* 4484 */         switch (info.pageRotation) {
/*      */ 
/*      */ 
/*      */           
/*      */           case 1:
/* 4489 */             this.mPdfViewCtrl.rotateClockwise();
/* 4490 */             this.mPdfViewCtrl.updatePageLayout();
/*      */             break;
/*      */           case 2:
/* 4493 */             this.mPdfViewCtrl.rotateClockwise();
/* 4494 */             this.mPdfViewCtrl.rotateClockwise();
/* 4495 */             this.mPdfViewCtrl.updatePageLayout();
/*      */             break;
/*      */           case 3:
/* 4498 */             this.mPdfViewCtrl.rotateCounterClockwise();
/* 4499 */             this.mPdfViewCtrl.updatePageLayout();
/*      */             break;
/*      */         } 
/* 4502 */       } catch (Exception e) {
/* 4503 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */       } 
/* 4505 */       if (info.zoom > 0.0D) {
/* 4506 */         this.mPdfViewCtrl.setZoom(info.zoom);
/*      */       }
/* 4508 */       if (info.hScrollPos > 0 || info.vScrollPos > 0) {
/* 4509 */         this.mPdfViewCtrl.scrollTo(info.hScrollPos, info.vScrollPos);
/*      */       }
/* 4511 */       if (info.isReflowMode != isReflowMode() && 
/* 4512 */         this.mTabListener != null) {
/* 4513 */         this.mTabListener.onToggleReflow();
/*      */       }
/*      */       
/* 4516 */       if (this.mReflowControl != null && this.mReflowControl.isReady()) {
/*      */         try {
/* 4518 */           this.mReflowControl.setTextSizeInPercent(info.reflowTextSize);
/* 4519 */         } catch (Exception e) {
/* 4520 */           AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */         } 
/*      */       }
/* 4523 */       this.mBookmarkDialogCurrentTab = info.bookmarkDialogCurrentTab;
/*      */     } else {
/*      */       
/* 4526 */       String mode = PdfViewCtrlSettingsManager.getViewMode((Context)fragmentActivity);
/* 4527 */       PDFViewCtrl.PagePresentationMode pagePresentationMode = getPagePresentationModeFromSettings(mode);
/* 4528 */       this.mPdfViewCtrl.setPagePresentationMode(pagePresentationMode);
/*      */     } 
/*      */     
/* 4531 */     if (this.mBookmarkDialogCurrentTab == -1) {
/* 4532 */       this.mBookmarkDialogCurrentTab = (Utils.getFirstBookmark(this.mPdfViewCtrl.getDoc()) != null) ? 1 : 0;
/*      */     }
/*      */     
/* 4535 */     updateColorMode();
/*      */     
/* 4537 */     PdfViewCtrlTabInfo tempInfo = saveCurrentPdfViewCtrlState();
/*      */     
/* 4539 */     if (info != null) {
/* 4540 */       addToRecentList(info);
/*      */     } else {
/* 4542 */       addToRecentList(tempInfo);
/*      */     } 
/*      */     
/* 4545 */     PdfViewCtrlTabsManager.getInstance().updateLastViewedTabTimestamp((Context)getActivity(), this.mTabTag);
/*      */     
/* 4547 */     if (this.mTabListener != null) {
/* 4548 */       this.mTabListener.onTabDocumentLoaded(getTabTag());
/*      */     }
/*      */     
/* 4551 */     toggleViewerVisibility(true);
/*      */     
/* 4553 */     if (this.mToolManager != null) {
/* 4554 */       String freeTextCacheFilename = this.mToolManager.getFreeTextCacheFileName();
/* 4555 */       if (Utils.cacheFileExists(getContext(), freeTextCacheFilename)) {
/* 4556 */         createRetrieveChangesDialog(freeTextCacheFilename);
/*      */       }
/* 4558 */       if (this.mViewerConfig != null) {
/* 4559 */         if (!this.mViewerConfig.isDocumentEditingEnabled()) {
/* 4560 */           this.mToolManager.setReadOnly(true);
/*      */         }
/* 4562 */         if (!this.mViewerConfig.isLongPressQuickMenuEnabled()) {
/* 4563 */           this.mToolManager.setDisableQuickMenu(true);
/*      */         }
/* 4565 */         if (this.mBottomNavBar != null) {
/*      */ 
/*      */ 
/*      */           
/* 4569 */           boolean canShowBookmark = (this.mViewerConfig.isShowBookmarksView() && (this.mViewerConfig.isShowAnnotationsList() || this.mViewerConfig.isShowOutlineList() || this.mViewerConfig.isShowUserBookmarksList()));
/* 4570 */           if (!canShowBookmark) {
/* 4571 */             this.mBottomNavBar.setMenuItemVisibility(1, 8);
/*      */           }
/* 4573 */           if (!this.mViewerConfig.isShowThumbnailView()) {
/* 4574 */             this.mBottomNavBar.setMenuItemVisibility(0, 8);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 4580 */     if (this.mImageStampDelayCreation) {
/* 4581 */       this.mImageStampDelayCreation = false;
/* 4582 */       ViewerUtils.createImageStamp((Activity)fragmentActivity, this.mAnnotIntentData, this.mPdfViewCtrl, this.mOutputFileUri, this.mAnnotTargetPoint);
/*      */     } 
/*      */     
/* 4585 */     if (this.mImageSignatureDelayCreation) {
/* 4586 */       this.mImageSignatureDelayCreation = false;
/* 4587 */       ViewerUtils.createImageSignature((Activity)getActivity(), this.mAnnotIntentData, this.mPdfViewCtrl, this.mOutputFileUri, this.mAnnotTargetPoint, this.mAnnotTargetPage, this.mTargetWidget);
/*      */     } 
/*      */ 
/*      */     
/* 4591 */     if (this.mFileAttachmentDelayCreation) {
/* 4592 */       this.mFileAttachmentDelayCreation = false;
/* 4593 */       ViewerUtils.createFileAttachment((Activity)getActivity(), this.mAnnotIntentData, this.mPdfViewCtrl, this.mAnnotTargetPoint);
/*      */     } 
/*      */     
/* 4596 */     if (Utils.isLargeScreenWidth((Context)fragmentActivity)) {
/* 4597 */       this.mPdfViewCtrl.setFocusableInTouchMode(true);
/* 4598 */       this.mPdfViewCtrl.requestFocus();
/*      */     } 
/*      */     
/* 4601 */     if (this.mAnnotationToolbarShow) {
/* 4602 */       this.mAnnotationToolbarShow = false;
/* 4603 */       if (this.mTabListener != null) {
/* 4604 */         if (this.mAnnotationToolbarToolMode == ToolManager.ToolMode.INK_CREATE) {
/* 4605 */           this.mTabListener.onOpenEditToolbar(this.mAnnotationToolbarToolMode);
/*      */         } else {
/* 4607 */           this.mTabListener.onOpenAnnotationToolbar(this.mAnnotationToolbarToolMode);
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   protected PDFViewCtrl.PagePresentationMode getPagePresentationModeFromSettings(String mode) {
/* 4614 */     PDFViewCtrl.PagePresentationMode pagePresentationMode = PDFViewCtrl.PagePresentationMode.SINGLE;
/* 4615 */     if (mode.equalsIgnoreCase("continuous")) {
/* 4616 */       pagePresentationMode = PDFViewCtrl.PagePresentationMode.SINGLE_CONT;
/* 4617 */     } else if (mode.equalsIgnoreCase("singlepage")) {
/* 4618 */       pagePresentationMode = PDFViewCtrl.PagePresentationMode.SINGLE;
/* 4619 */     } else if (mode.equalsIgnoreCase("facing")) {
/* 4620 */       pagePresentationMode = PDFViewCtrl.PagePresentationMode.FACING;
/* 4621 */     } else if (mode.equalsIgnoreCase("facingcover")) {
/* 4622 */       pagePresentationMode = PDFViewCtrl.PagePresentationMode.FACING_COVER;
/* 4623 */     } else if (mode.equalsIgnoreCase("facing_cont")) {
/* 4624 */       pagePresentationMode = PDFViewCtrl.PagePresentationMode.FACING_CONT;
/* 4625 */     } else if (mode.equalsIgnoreCase("facingcover_cont")) {
/* 4626 */       pagePresentationMode = PDFViewCtrl.PagePresentationMode.FACING_COVER_CONT;
/*      */     } 
/* 4628 */     return pagePresentationMode;
/*      */   }
/*      */   
/*      */   protected PdfViewCtrlTabInfo getInfoFromRecentList(FileInfo fileInfo) {
/* 4632 */     FragmentActivity fragmentActivity = getActivity();
/* 4633 */     if (fragmentActivity == null) {
/* 4634 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 4638 */     FileInfo recentFileInfo = RecentFilesManager.getInstance().getFile((Context)fragmentActivity, fileInfo);
/* 4639 */     return createTabInfoFromFileInfo(recentFileInfo);
/*      */   }
/*      */   
/*      */   protected PdfViewCtrlTabInfo createTabInfoFromFileInfo(FileInfo fileInfo) {
/* 4643 */     PdfViewCtrlTabInfo info = new PdfViewCtrlTabInfo();
/* 4644 */     if (fileInfo == null) {
/* 4645 */       return null;
/*      */     }
/* 4647 */     info.tabSource = fileInfo.getType();
/* 4648 */     info.lastPage = fileInfo.getLastPage();
/* 4649 */     info.pageRotation = fileInfo.getPageRotation();
/* 4650 */     info.setPagePresentationMode(fileInfo.getPagePresentationMode());
/* 4651 */     info.hScrollPos = fileInfo.getHScrollPos();
/* 4652 */     info.vScrollPos = fileInfo.getVScrollPos();
/* 4653 */     info.zoom = fileInfo.getZoom();
/* 4654 */     info.isReflowMode = fileInfo.isReflowMode();
/* 4655 */     info.reflowTextSize = fileInfo.getReflowTextSize();
/* 4656 */     info.isRtlMode = fileInfo.isRtlMode();
/* 4657 */     info.bookmarkDialogCurrentTab = fileInfo.getBookmarkDialogCurrentTab();
/* 4658 */     return info;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void forceSave() {
/* 4665 */     showDocumentSavedToast();
/* 4666 */     save(true, true, false, true);
/*      */   }
/*      */   
/*      */   protected void showDocumentSavedToast() {
/* 4670 */     FragmentActivity fragmentActivity = getActivity();
/* 4671 */     if (fragmentActivity == null || isNotPdf()) {
/*      */       return;
/*      */     }
/*      */     
/* 4675 */     if (this.mHasChangesSinceResumed) {
/* 4676 */       this.mHasChangesSinceResumed = false;
/* 4677 */       if (!this.mWasSavedAndClosedShown) {
/* 4678 */         CommonToast.showText((Context)fragmentActivity, R.string.document_saved_toast_message, 0);
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
/*      */   public void save(boolean close, boolean forceSave, boolean skipSpecialFileCheck) {
/* 4691 */     save(close, forceSave, skipSpecialFileCheck, close);
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
/*      */   public void save(boolean close, boolean forceSave, boolean skipSpecialFileCheck, boolean upload) {
/* 4703 */     if (isNotPdf()) {
/*      */       return;
/*      */     }
/* 4706 */     synchronized (this.saveDocumentLock) {
/* 4707 */       if (this.mDocumentConversion == null && Utils.isDocModified(this.mPdfDoc)) {
/* 4708 */         switch (this.mDocumentState) {
/*      */           case 0:
/*      */           case 1:
/*      */           case 2:
/* 4712 */             this.mDocumentState = 2;
/* 4713 */             saveHelper(close, forceSave, true, upload);
/*      */             break;
/*      */           case 5:
/* 4716 */             saveHelper(close, forceSave, false, upload);
/*      */             break;
/*      */           case 6:
/* 4719 */             if (!skipSpecialFileCheck) {
/* 4720 */               handleSpecialFile(close);
/*      */             }
/*      */             break;
/*      */           case 3:
/* 4724 */             saveHelper(close, forceSave, false, upload);
/*      */             break;
/*      */           case 4:
/* 4727 */             if (!skipSpecialFileCheck) {
/* 4728 */               handleSpecialFile(close);
/*      */             }
/*      */             break;
/*      */           
/*      */           case 9:
/* 4733 */             saveConversionTempHelper(close, forceSave, true);
/*      */             break;
/*      */           default:
/* 4736 */             if (close)
/*      */             {
/*      */               
/* 4739 */               saveHelper(true, forceSave, false, upload);
/*      */             }
/*      */             break;
/*      */         } 
/*      */       } else {
/* 4744 */         saveHelper(close, forceSave, false, upload);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void handleFailedSave(boolean close, Exception e) {
/* 4750 */     FragmentActivity fragmentActivity = getActivity();
/* 4751 */     if (fragmentActivity == null) {
/*      */       return;
/*      */     }
/*      */     
/* 4755 */     boolean handled = false;
/* 4756 */     if (Utils.isLollipop() && this.mCurrentFile != null) {
/* 4757 */       boolean isSDCardFile = Utils.isSdCardFile((Context)fragmentActivity, this.mCurrentFile);
/* 4758 */       if (isSDCardFile) {
/*      */         
/* 4760 */         this.mDocumentState = 5;
/* 4761 */         handled = true;
/*      */       } 
/*      */     } 
/* 4764 */     if (!handled) {
/* 4765 */       this.mDocumentState = 7;
/*      */     }
/* 4767 */     if (!this.mToolManager.isReadOnly()) {
/* 4768 */       this.mToolManager.setReadOnly(true);
/*      */     }
/* 4770 */     handleSpecialFile(close);
/*      */   }
/*      */   
/*      */   protected void saveHelper(boolean close, boolean forceSave, boolean hasChangesSinceLastSave, boolean upload) {
/* 4774 */     if (!this.mSavingEnabled)
/* 4775 */       return;  if (forceSave && 
/* 4776 */       this.mPdfViewCtrl != null) {
/* 4777 */       this.mPdfViewCtrl.cancelRendering();
/*      */     }
/*      */     
/* 4780 */     switch (this.mTabSource) {
/*      */       case 6:
/* 4782 */         if (hasChangesSinceLastSave) {
/* 4783 */           saveExternalFile(close, forceSave);
/*      */         }
/*      */         break;
/*      */       case 2:
/* 4787 */         if (hasChangesSinceLastSave) {
/* 4788 */           saveLocalFile(close, forceSave);
/*      */         }
/*      */         break;
/*      */       case 13:
/* 4792 */         if (hasChangesSinceLastSave) {
/* 4793 */           saveLocalFile(close, forceSave);
/*      */         }
/* 4795 */         if (close) {
/* 4796 */           saveBackEditUri();
/*      */         }
/*      */         break;
/*      */     } 
/* 4800 */     if (hasChangesSinceLastSave && this.mDocumentState == 2) {
/* 4801 */       this.mDocumentState = 1;
/*      */     }
/*      */     
/* 4804 */     if (forceSave && !close && 
/* 4805 */       this.mPdfViewCtrl != null) {
/* 4806 */       this.mPdfViewCtrl.requestRendering();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean docLock(boolean forceLock) {
/* 4812 */     if (this.mPdfViewCtrl == null || this.mPdfViewCtrl.getDoc() == null) {
/* 4813 */       return false;
/*      */     }
/*      */     
/*      */     try {
/* 4817 */       if (forceLock) {
/* 4818 */         if (sDebug) {
/* 4819 */           Log.d(TAG, "PDFDoc FORCE LOCK");
/*      */         }
/* 4821 */         this.mPdfViewCtrl.docLock(true);
/* 4822 */         return true;
/*      */       } 
/* 4824 */       if (sDebug) {
/* 4825 */         Log.d(TAG, "PDFDoc TRY LOCK");
/*      */       }
/* 4827 */       return this.mPdfViewCtrl.docTryLock(500);
/*      */     }
/* 4829 */     catch (PDFNetException e) {
/* 4830 */       AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/* 4831 */       return false;
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void docUnlock() {
/* 4836 */     if (this.mPdfViewCtrl == null) {
/*      */       return;
/*      */     }
/* 4839 */     this.mPdfViewCtrl.docUnlock();
/*      */   }
/*      */   
/*      */   protected void checkDocIntegrity() {
/* 4843 */     this.mHasChangesSinceOpened = true;
/* 4844 */     this.mHasChangesSinceResumed = true;
/* 4845 */     this.mNeedsCleanupFile = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void saveLocalFile(boolean close, boolean forceSave) {
/* 4855 */     if (this.mCurrentFile != null) {
/* 4856 */       if (Utils.isNotPdf(this.mCurrentFile.getAbsolutePath()))
/*      */         return; 
/* 4858 */       boolean shouldUnlock = false;
/*      */       try {
/* 4860 */         shouldUnlock = docLock((close || forceSave));
/* 4861 */         if (shouldUnlock) {
/* 4862 */           if (this.mPdfViewCtrl != null && this.mPdfViewCtrl.getDoc() == null) {
/* 4863 */             AnalyticsHandlerAdapter.getInstance().sendException(new Exception("doc from PdfViewCtrl is null while we lock the document!" + ((this.mPdfDoc == null) ? "" : " and the mPdfDoc is not null!") + " | source: " + this.mTabSource));
/*      */           }
/*      */ 
/*      */ 
/*      */           
/* 4868 */           if (sDebug) {
/* 4869 */             Log.d(TAG, "save local");
/* 4870 */             Log.d(TAG, "doc locked");
/*      */           } 
/* 4872 */           if (this.mToolManager.getUndoRedoManger() != null) {
/* 4873 */             this.mToolManager.getUndoRedoManger().takeUndoSnapshotForSafety();
/*      */           }
/* 4875 */           this.mPdfDoc.save(this.mCurrentFile.getAbsolutePath(), SDFDoc.SaveMode.INCREMENTAL, null);
/* 4876 */           this.mLastSuccessfulSave = System.currentTimeMillis();
/* 4877 */           checkDocIntegrity();
/*      */         } 
/* 4879 */       } catch (Exception e) {
/*      */ 
/*      */         
/* 4882 */         handleFailedSave(close, e);
/* 4883 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */       } finally {
/* 4885 */         if (shouldUnlock) {
/* 4886 */           docUnlock();
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
/*      */   public void saveExternalFile(boolean close, boolean forceSave) {
/* 4899 */     if (this.mCurrentUriFile != null) {
/* 4900 */       boolean shouldUnlock = false;
/*      */       try {
/* 4902 */         shouldUnlock = docLock((close || forceSave));
/* 4903 */         if (shouldUnlock) {
/* 4904 */           if (sDebug) {
/* 4905 */             Log.d(TAG, "save external file");
/* 4906 */             Log.d(TAG, "save external doc locked");
/*      */           } 
/* 4908 */           if (this.mToolManager.getUndoRedoManger() != null) {
/* 4909 */             this.mToolManager.getUndoRedoManger().takeUndoSnapshotForSafety();
/*      */           }
/* 4911 */           this.mPdfDoc.save();
/* 4912 */           this.mLastSuccessfulSave = System.currentTimeMillis();
/* 4913 */           checkDocIntegrity();
/*      */         } 
/* 4915 */       } catch (Exception e) {
/* 4916 */         handleFailedSave(close, e);
/* 4917 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */       } finally {
/* 4919 */         if (shouldUnlock) {
/* 4920 */           docUnlock();
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void saveBackEditUri() {
/* 4927 */     FragmentActivity fragmentActivity = getActivity();
/* 4928 */     if (fragmentActivity == null) {
/*      */       return;
/*      */     }
/* 4931 */     if (this.mCurrentFile == null || !this.mCurrentFile.exists()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 4937 */     if (this.mHasChangesSinceOpened) {
/*      */       
/* 4939 */       this.mNeedsCleanupFile = false;
/* 4940 */       InputStream is = null;
/* 4941 */       OutputStream fos = null;
/* 4942 */       FileInputStream fileInputStream = null;
/* 4943 */       FileOutputStream fileOutputStream = null;
/* 4944 */       RandomAccessFile raf = null;
/* 4945 */       ParcelFileDescriptor pfd = null;
/* 4946 */       boolean failed = true;
/* 4947 */       boolean readWrite = false;
/* 4948 */       ContentResolver contentResolver = Utils.getContentResolver((Context)fragmentActivity);
/* 4949 */       if (contentResolver != null) {
/*      */         try {
/* 4951 */           pfd = contentResolver.openFileDescriptor(Uri.parse(this.mTabTag), "rw");
/* 4952 */           readWrite = true;
/* 4953 */         } catch (Exception e) {
/*      */           try {
/* 4955 */             pfd = contentResolver.openFileDescriptor(Uri.parse(this.mTabTag), "w");
/* 4956 */           } catch (Exception exception) {}
/*      */         } 
/*      */       }
/*      */       
/* 4960 */       if (pfd != null) {
/* 4961 */         failed = false;
/*      */         try {
/* 4963 */           if (readWrite) {
/*      */             try {
/* 4965 */               fileInputStream = new FileInputStream(pfd.getFileDescriptor());
/*      */               
/* 4967 */               if (sDebug) {
/* 4968 */                 Log.d(TAG, "editUri | originalLength: " + this.mOriginalFileLength + " | stream: " + fileInputStream
/* 4969 */                     .available() + " | localLength: " + this.mCurrentFile.length());
/*      */               }
/* 4971 */               long originalFileLength = this.mOriginalFileLength;
/* 4972 */               long finalSize = this.mCurrentFile.length();
/* 4973 */               if (originalFileLength > finalSize) {
/* 4974 */                 throw new Exception("Original file size is bigger than saved file size. Something went wrong");
/*      */               }
/*      */               
/* 4977 */               long skipped = fileInputStream.skip(originalFileLength);
/* 4978 */               if (skipped == originalFileLength) {
/*      */                 
/* 4980 */                 fileOutputStream = new FileOutputStream(pfd.getFileDescriptor());
/*      */                 
/* 4982 */                 raf = new RandomAccessFile(this.mCurrentFile, "r");
/* 4983 */                 raf.seek(originalFileLength);
/* 4984 */                 long count = Utils.copyLarge(raf, fileOutputStream);
/* 4985 */                 fileOutputStream.getChannel().truncate(finalSize);
/*      */                 
/* 4987 */                 if (sDebug) {
/* 4988 */                   Log.d(TAG, "seek to position: " + originalFileLength + " | sizeToWrite is: " + (finalSize - originalFileLength) + " | actualWriteSize is: " + count + " | truncate to: " + finalSize);
/*      */                 }
/*      */               }
/*      */               else {
/*      */                 
/* 4993 */                 throw new Exception("Could not seek to size. Something went wrong");
/*      */               } 
/* 4995 */             } catch (Exception ex) {
/* 4996 */               AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*      */ 
/*      */ 
/*      */               
/* 5000 */               is = new FileInputStream(this.mCurrentFile);
/* 5001 */               fos = new FileOutputStream(pfd.getFileDescriptor());
/* 5002 */               IOUtils.copy(is, fos);
/*      */             } 
/*      */           } else {
/* 5005 */             is = new FileInputStream(this.mCurrentFile);
/* 5006 */             fos = new FileOutputStream(pfd.getFileDescriptor());
/* 5007 */             IOUtils.copy(is, fos);
/*      */           } 
/* 5009 */           this.mNeedsCleanupFile = true;
/* 5010 */         } catch (OutOfMemoryError oom) {
/* 5011 */           failed = true;
/* 5012 */           Utils.manageOOM(getContext(), this.mPdfViewCtrl);
/* 5013 */         } catch (Exception ex) {
/* 5014 */           failed = true;
/* 5015 */           AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*      */         } finally {
/* 5017 */           this.mHasChangesSinceOpened = false;
/* 5018 */           Utils.closeQuietly(is);
/* 5019 */           Utils.closeQuietly(fos);
/* 5020 */           Utils.closeQuietly(fileInputStream);
/* 5021 */           Utils.closeQuietly(fileOutputStream);
/* 5022 */           Utils.closeQuietly(raf);
/* 5023 */           Utils.closeQuietly(pfd);
/*      */         } 
/*      */       } 
/*      */       
/* 5027 */       if (failed) {
/*      */ 
/*      */         
/* 5030 */         if (this.mCurrentFile != null) {
/* 5031 */           PdfViewCtrlSettingsManager.updateEditUriBackupFilePath((Context)fragmentActivity, this.mCurrentFile.getAbsolutePath());
/*      */         }
/* 5033 */         removeFromRecentList();
/* 5034 */         File backupDir = Utils.getExternalDownloadDirectory((Context)fragmentActivity);
/* 5035 */         if (this.mCurrentFile != null && this.mCurrentFile.exists() && this.mCurrentFile.getParent().equals(backupDir.getPath())) {
/* 5036 */           CommonToast.showText((Context)fragmentActivity, getString(R.string.document_notify_failed_commit_message, new Object[] { backupDir.getName() }));
/*      */         } else {
/* 5038 */           CommonToast.showText((Context)fragmentActivity, R.string.document_save_error_toast_message);
/*      */         } 
/*      */       } 
/*      */     } else {
/* 5042 */       this.mNeedsCleanupFile = true;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void saveConversionTempHelper(boolean close, boolean forceSave, boolean hasChangesSinceLastSave) {
/* 5048 */     if (!hasChangesSinceLastSave) {
/*      */       return;
/*      */     }
/* 5051 */     if (this.mTabConversionTempPath != null) {
/* 5052 */       File file = new File(this.mTabConversionTempPath);
/* 5053 */       boolean shouldUnlock = false;
/*      */       try {
/* 5055 */         shouldUnlock = docLock((close || forceSave));
/* 5056 */         if (shouldUnlock) {
/* 5057 */           if (sDebug) {
/* 5058 */             Log.d(TAG, "save Conversion Temp");
/* 5059 */             Log.d(TAG, "doc locked");
/*      */           } 
/* 5061 */           if (this.mToolManager.getUndoRedoManger() != null) {
/* 5062 */             this.mToolManager.getUndoRedoManger().takeUndoSnapshotForSafety();
/*      */           }
/* 5064 */           this.mPdfDoc.save(file.getAbsolutePath(), SDFDoc.SaveMode.INCREMENTAL, null);
/*      */         } 
/* 5066 */       } catch (Exception e) {
/* 5067 */         handleFailedSave(close, e);
/* 5068 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */       } finally {
/* 5070 */         if (shouldUnlock) {
/* 5071 */           docUnlock();
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void saveConversionTempCopy() {
/* 5078 */     if (getActivity() == null) {
/*      */       return;
/*      */     }
/*      */     
/* 5082 */     boolean shouldUnlock = false;
/*      */     try {
/* 5084 */       this.mTabConversionTempPath = File.createTempFile("tmp", ".pdf", getActivity().getFilesDir()).getAbsolutePath();
/*      */       
/* 5086 */       this.mPdfDoc.lock();
/* 5087 */       shouldUnlock = true;
/* 5088 */       this.mPdfDoc.save(this.mTabConversionTempPath, SDFDoc.SaveMode.REMOVE_UNUSED, null);
/* 5089 */     } catch (Exception e) {
/*      */       
/* 5091 */       boolean showToast = true;
/* 5092 */       if (e instanceof PDFNetException) {
/* 5093 */         showToast = (((PDFNetException)e).getErrorCode() != 2L);
/*      */       }
/* 5095 */       if (showToast) {
/* 5096 */         handleFailedSave(false, e);
/*      */       }
/* 5098 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } finally {
/* 5100 */       if (shouldUnlock) {
/* 5101 */         Utils.unlockQuietly(this.mPdfDoc);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void localFileWriteAccessCheck() {
/* 5110 */     if (!this.mLocalReadOnlyChecked) {
/*      */       
/* 5112 */       this.mLocalReadOnlyChecked = true;
/* 5113 */       if (this.mTabSource == 2) {
/* 5114 */         if (!isTabReadOnly()) {
/* 5115 */           boolean shouldUnlockRead = false;
/*      */           try {
/* 5117 */             this.mPdfDoc.lockRead();
/* 5118 */             shouldUnlockRead = true;
/* 5119 */             boolean canSave = this.mPdfDoc.getSDFDoc().canSaveToPath(this.mCurrentFile.getAbsolutePath(), SDFDoc.SaveMode.INCREMENTAL);
/* 5120 */             this.mPdfDoc.unlockRead();
/* 5121 */             shouldUnlockRead = false;
/* 5122 */             if (!canSave) {
/*      */               
/* 5124 */               this.mDocumentState = 5;
/* 5125 */               this.mToolManager.setReadOnly(true);
/*      */             } 
/* 5127 */           } catch (Exception e) {
/* 5128 */             AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */           } finally {
/* 5130 */             if (shouldUnlockRead) {
/* 5131 */               Utils.unlockReadQuietly(this.mPdfDoc);
/*      */             }
/*      */           } 
/*      */         } 
/* 5135 */       } else if (this.mTabSource == 13 && (
/* 5136 */         this.mTabTag == null || !Utils.uriHasReadWritePermission(getContext(), Uri.parse(this.mTabTag)))) {
/* 5137 */         this.mDocumentState = 5;
/* 5138 */         this.mToolManager.setReadOnly(true);
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
/*      */   public boolean handleSpecialFile() {
/* 5150 */     return handleSpecialFile(false);
/*      */   }
/*      */   
/*      */   protected boolean handleSpecialFile(boolean close) {
/* 5154 */     FragmentActivity fragmentActivity = getActivity();
/* 5155 */     if (fragmentActivity == null) {
/* 5156 */       return false;
/*      */     }
/*      */     
/* 5159 */     if (this.mTabListener != null) {
/* 5160 */       this.mTabListener.onUpdateOptionsMenu();
/*      */     }
/*      */     
/* 5163 */     if (this.mTabSource == 5)
/*      */     {
/* 5165 */       if (this.mToolManager.isReadOnly()) {
/* 5166 */         CommonToast.showText((Context)fragmentActivity, R.string.download_not_finished_yet_with_changes_warning, 0);
/* 5167 */         return true;
/*      */       } 
/*      */     }
/*      */     
/* 5171 */     boolean ignore = true;
/* 5172 */     int message = R.string.document_read_only_warning_message;
/* 5173 */     int title = R.string.document_read_only_warning_title;
/*      */     
/* 5175 */     switch (this.mDocumentState) {
/*      */       case 5:
/*      */       case 6:
/* 5178 */         this.mDocumentState = 6;
/* 5179 */         ignore = false;
/* 5180 */         CommonToast.showText((Context)fragmentActivity, R.string.document_read_only_error_message, 0);
/*      */         break;
/*      */       case 3:
/*      */       case 4:
/* 5184 */         this.mDocumentState = 4;
/* 5185 */         CommonToast.showText((Context)fragmentActivity, R.string.document_corrupted_error_message, 0);
/* 5186 */         return true;
/*      */       case 7:
/* 5188 */         CommonToast.showText((Context)fragmentActivity, R.string.document_save_error_toast_message, 0);
/* 5189 */         return true;
/*      */       case 8:
/* 5191 */         CommonToast.showText((Context)fragmentActivity, R.string.cant_edit_while_converting_message);
/* 5192 */         return true;
/*      */       case 9:
/* 5194 */         ignore = false;
/*      */         break;
/*      */     } 
/*      */     
/* 5198 */     if (this.mUniversalConverted) {
/* 5199 */       title = R.string.document_converted_warning_title;
/*      */     }
/*      */     
/* 5202 */     if (!ignore && !close) {
/* 5203 */       AlertDialog.Builder builder = new AlertDialog.Builder((Context)fragmentActivity);
/* 5204 */       builder.setTitle(title).setMessage(message)
/* 5205 */         .setCancelable(false);
/*      */       
/* 5207 */       showSpecialFileAlertDialog(builder, this.mDocumentState, (DialogFragment)null);
/* 5208 */       return true;
/*      */     } 
/*      */     
/* 5211 */     return false;
/*      */   }
/*      */   
/*      */   public void showReadOnlyAlert(DialogFragment dialogToDismiss) {
/* 5215 */     FragmentActivity fragmentActivity = getActivity();
/* 5216 */     if (fragmentActivity == null) {
/*      */       return;
/*      */     }
/*      */     
/* 5220 */     int message = R.string.document_read_only_warning_message;
/* 5221 */     int title = R.string.document_read_only_warning_title;
/*      */     
/* 5223 */     AlertDialog.Builder builder = new AlertDialog.Builder((Context)fragmentActivity);
/* 5224 */     builder.setTitle(title).setMessage(message)
/* 5225 */       .setCancelable(false);
/*      */     
/* 5227 */     showSpecialFileAlertDialog(builder, this.mDocumentState, dialogToDismiss);
/*      */   }
/*      */   
/*      */   protected void showSpecialFileAlertDialog(AlertDialog.Builder builder, int state, final DialogFragment dialogToDismiss) {
/* 5231 */     if (this.mSpecialFileAlertDialog != null && this.mSpecialFileAlertDialog.isShowing()) {
/*      */       return;
/*      */     }
/*      */     try {
/* 5235 */       FragmentActivity fragmentActivity = getActivity();
/* 5236 */       if (fragmentActivity == null) {
/*      */         return;
/*      */       }
/* 5239 */       this.mShowingSpecialFileAlertDialog = true;
/* 5240 */       if (state == 6 || state == 9) {
/*      */         
/* 5242 */         builder.setPositiveButton(R.string.action_export_options, new DialogInterface.OnClickListener()
/*      */             {
/*      */               public void onClick(DialogInterface dialog, int which) {
/* 5245 */                 if (dialogToDismiss != null) {
/* 5246 */                   dialogToDismiss.dismiss();
/*      */                 }
/* 5248 */                 FragmentActivity fragmentActivity = PdfViewCtrlTabFragment.this.getActivity();
/* 5249 */                 if (fragmentActivity == null) {
/*      */                   return;
/*      */                 }
/*      */                 
/* 5253 */                 PdfViewCtrlTabFragment.this.mShowingSpecialFileAlertDialog = false;
/*      */                 
/* 5255 */                 boolean isSDCardFile = false;
/* 5256 */                 if (Utils.isLollipop() && PdfViewCtrlTabFragment.this.mCurrentFile != null) {
/* 5257 */                   isSDCardFile = Utils.isSdCardFile((Context)fragmentActivity, PdfViewCtrlTabFragment.this.mCurrentFile);
/*      */                 }
/* 5259 */                 if (isSDCardFile) {
/* 5260 */                   if (PdfViewCtrlTabFragment.this.mTabListener != null) {
/* 5261 */                     PdfViewCtrlTabFragment.this.mTabListener.onTabJumpToSdCardFolder();
/*      */                   }
/* 5263 */                   AnalyticsHandlerAdapter.getInstance().sendEvent(1, "Read Only SD Card File Jump To SD Card");
/*      */                 } else {
/* 5265 */                   PdfViewCtrlTabFragment.this.handleSpecialFilePositive();
/* 5266 */                   AnalyticsHandlerAdapter.getInstance().sendEvent(1, "Read Only File Saved a Copy");
/*      */                 } 
/* 5268 */                 dialog.dismiss();
/*      */               }
/* 5270 */             }).setNegativeButton(R.string.document_read_only_warning_negative, new DialogInterface.OnClickListener()
/*      */             {
/*      */               public void onClick(DialogInterface dialog, int which) {
/* 5273 */                 PdfViewCtrlTabFragment.this.mShowingSpecialFileAlertDialog = false;
/* 5274 */                 if (PdfViewCtrlTabFragment.this.mDocumentState != 9) {
/* 5275 */                   PdfViewCtrlTabFragment.this.mDocumentState = 5;
/*      */                 }
/* 5277 */                 dialog.dismiss();
/*      */               }
/*      */             });
/* 5280 */         this.mSpecialFileAlertDialog = builder.create();
/* 5281 */         this.mSpecialFileAlertDialog.show();
/*      */       } 
/* 5283 */     } catch (Exception ex) {
/* 5284 */       this.mShowingSpecialFileAlertDialog = false;
/* 5285 */       AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*      */     } 
/*      */   }
/*      */   
/*      */   private File getExportDirectory() {
/* 5290 */     Context context = getContext();
/* 5291 */     if (context == null) {
/* 5292 */       throw new IllegalStateException("Should not call getExportDirectory when context is invalid");
/*      */     }
/* 5294 */     File folder = Utils.getExternalDownloadDirectory(context);
/* 5295 */     if (this.mViewerConfig != null && !Utils.isNullOrEmpty(this.mViewerConfig.getSaveCopyExportPath())) {
/* 5296 */       File tempFolder = new File(this.mViewerConfig.getSaveCopyExportPath());
/* 5297 */       if (tempFolder.exists() && tempFolder.isDirectory()) {
/* 5298 */         folder = tempFolder;
/*      */       }
/*      */     } 
/* 5301 */     return folder;
/*      */   }
/*      */   
/*      */   protected void handleSpecialFilePositive() {
/* 5305 */     saveSpecialFile(new SaveFolderWrapper(getExportDirectory(), doesSaveDocNeedNewTab()));
/*      */   }
/*      */   
/*      */   protected Single<Pair<Boolean, String>> saveSpecialFileDisposable(final SaveFolderWrapper folderWrapper) {
/* 5309 */     return Single.create(new SingleOnSubscribe<Pair<Boolean, String>>()
/*      */         {
/*      */           public void subscribe(SingleEmitter<Pair<Boolean, String>> emitter) throws Exception {
/* 5312 */             boolean needsCopy = PdfViewCtrlTabFragment.this.doesSaveDocNeedNewTab();
/*      */             
/* 5314 */             PDFDoc copyDoc = PdfViewCtrlTabFragment.this.mPdfDoc;
/* 5315 */             if (needsCopy) {
/* 5316 */               copyDoc = folderWrapper.getDoc();
/* 5317 */               if (null == copyDoc) {
/* 5318 */                 folderWrapper.cleanup();
/* 5319 */                 emitter.tryOnError(new IllegalStateException("Could not get a copy of the doc. PDFDoc is null."));
/*      */                 return;
/*      */               } 
/*      */             } 
/*      */             try {
/* 5324 */               emitter.onSuccess(folderWrapper.save(copyDoc, needsCopy));
/* 5325 */             } catch (Exception e) {
/* 5326 */               AnalyticsHandlerAdapter.getInstance().sendException(e);
/* 5327 */               emitter.tryOnError(e);
/*      */             } 
/*      */           }
/*      */         });
/*      */   }
/*      */   
/*      */   protected void saveSpecialFile(final SaveFolderWrapper folderWrapper) {
/* 5334 */     final FragmentActivity activity = getActivity();
/* 5335 */     if (fragmentActivity == null) {
/*      */       return;
/*      */     }
/*      */     
/* 5339 */     final ProgressDialog progressDialog = new ProgressDialog((Context)fragmentActivity);
/*      */     
/* 5341 */     this.mDisposables.add(saveSpecialFileDisposable(folderWrapper)
/* 5342 */         .subscribeOn(Schedulers.io())
/* 5343 */         .observeOn(AndroidSchedulers.mainThread())
/* 5344 */         .doOnSubscribe(new Consumer<Disposable>()
/*      */           {
/*      */             public void accept(Disposable disposable) throws Exception {
/* 5347 */               progressDialog.setMessage(PdfViewCtrlTabFragment.this.getString(R.string.save_as_wait));
/* 5348 */               progressDialog.setCancelable(false);
/* 5349 */               progressDialog.setProgressStyle(0);
/* 5350 */               progressDialog.setIndeterminate(true);
/* 5351 */               progressDialog.show();
/*      */             }
/* 5354 */           }).subscribe(new Consumer<Pair<Boolean, String>>()
/*      */           {
/*      */             public void accept(Pair<Boolean, String> booleanStringPair) throws Exception {
/* 5357 */               progressDialog.dismiss();
/*      */               
/* 5359 */               boolean needsCopy = PdfViewCtrlTabFragment.this.doesSaveDocNeedNewTab();
/* 5360 */               if (needsCopy) {
/* 5361 */                 folderWrapper.openInNewTab();
/*      */               } else {
/* 5363 */                 CommonToast.showText((Context)activity, R.string.document_saved_toast_message);
/*      */                 
/* 5365 */                 PdfViewCtrlTabFragment.this.mDocumentState = 1;
/* 5366 */                 PdfViewCtrlTabFragment.this.mToolManager.setReadOnly(false);
/* 5367 */                 String oldTag = PdfViewCtrlTabFragment.this.mTabTag;
/* 5368 */                 PdfViewCtrlTabFragment.this.mTabTag = folderWrapper.getNewTabTag();
/* 5369 */                 PdfViewCtrlTabFragment.this.mTabTitle = folderWrapper.getNewTabTitle();
/* 5370 */                 PdfViewCtrlTabFragment.this.mTabSource = folderWrapper.getNewTabType();
/* 5371 */                 PdfViewCtrlTabFragment.this.mFileExtension = "pdf";
/* 5372 */                 if (folderWrapper.isLocal()) {
/* 5373 */                   PdfViewCtrlTabFragment.this.mCurrentFile = folderWrapper.getNewLocalFile();
/*      */                 } else {
/* 5375 */                   PdfViewCtrlTabFragment.this.mCurrentUriFile = folderWrapper.getNewExternalUri();
/*      */                 } 
/* 5377 */                 PdfViewCtrlTabFragment.this.mUniversalConverted = false;
/* 5378 */                 if (PdfViewCtrlTabFragment.this.mTabListener != null) {
/* 5379 */                   PdfViewCtrlTabFragment.this.mTabListener.onTabIdentityChanged(oldTag, PdfViewCtrlTabFragment.this.mTabTag, PdfViewCtrlTabFragment.this.mTabTitle, PdfViewCtrlTabFragment.this.mFileExtension, PdfViewCtrlTabFragment.this.mTabSource);
/*      */                 }
/*      */ 
/*      */                 
/* 5383 */                 PdfViewCtrlTabsManager.getInstance().removeDocument((Context)activity, oldTag);
/* 5384 */                 PdfViewCtrlTabsManager.getInstance().addDocument((Context)activity, PdfViewCtrlTabFragment.this.mTabTag);
/* 5385 */                 PdfViewCtrlTabFragment.this.saveCurrentPdfViewCtrlState();
/*      */                 
/* 5387 */                 if (folderWrapper.isLocal()) {
/* 5388 */                   PdfViewCtrlTabFragment.this.openLocalFile(folderWrapper.getNewTabTag());
/*      */                 } else {
/* 5390 */                   PdfViewCtrlTabFragment.this.openExternalFile(folderWrapper.getNewTabTag());
/*      */                 } 
/*      */               } 
/*      */             }
/*      */           }new Consumer<Throwable>()
/*      */           {
/*      */             public void accept(Throwable throwable) throws Exception {
/* 5397 */               progressDialog.dismiss();
/*      */               
/* 5399 */               CommonToast.showText((Context)activity, PdfViewCtrlTabFragment.this.getString(R.string.save_to_copy_failed));
/*      */               
/* 5401 */               AnalyticsHandlerAdapter.getInstance().sendException(new Exception(throwable), "saveSpecialFile");
/*      */             }
/*      */           }));
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleSaveFlattenedCopy() {
/* 5408 */     handleSaveFlattenedCopy(new SaveFolderWrapper(getExportDirectory(), "Flattened"));
/*      */   }
/*      */   
/*      */   public void handleSavePasswordCopy() {
/* 5412 */     PasswordDialogFragment passwordDialog = getPasswordDialog();
/* 5413 */     passwordDialog.setListener(new PasswordDialogFragment.PasswordDialogFragmentListener()
/*      */         {
/*      */           public void onPasswordDialogPositiveClick(int fileType, File file, String path, String password, String id) {
/* 5416 */             String suffix = "Protected";
/* 5417 */             if (Utils.isNullOrEmpty(password)) {
/* 5418 */               suffix = "Not_Protected";
/*      */             }
/* 5420 */             PdfViewCtrlTabFragment.this.handleSavePasswordCopy(new SaveFolderWrapper(PdfViewCtrlTabFragment.this.getExportDirectory(), suffix), password);
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public void onPasswordDialogNegativeClick(int fileType, File file, String path) {}
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public void onPasswordDialogDismiss(boolean forcedDismiss) {}
/*      */         });
/* 5433 */     FragmentManager fragmentManager = getFragmentManager();
/* 5434 */     if (fragmentManager != null) {
/* 5435 */       passwordDialog.show(fragmentManager, "password_dialog");
/*      */     }
/*      */   }
/*      */   
/*      */   public void handleSaveCroppedCopy() {
/* 5440 */     handleSaveCroppedCopy(new SaveFolderWrapper(getExportDirectory(), "Cropped"));
/*      */   }
/*      */   
/*      */   public void handleSaveOptimizedCopy(OptimizeParams params) {
/* 5444 */     handleSaveOptimizedCopy(new SaveFolderWrapper(getExportDirectory(), "Reduced"), params);
/*      */   }
/*      */   
/*      */   public void handleSaveAsCopy() {
/* 5448 */     handleSaveAsCopyPrompt(getExportDirectory(), (ExternalFileInfo)null);
/*      */   }
/*      */   protected void handleSaveAsCopyPrompt(final File folder, final ExternalFileInfo externalFolder) {
/*      */     String copyPath;
/* 5452 */     final FragmentActivity activity = getActivity();
/* 5453 */     if (fragmentActivity == null) {
/*      */       return;
/*      */     }
/* 5456 */     final boolean external = (externalFolder != null);
/*      */     
/* 5458 */     LayoutInflater inflater = (LayoutInflater)fragmentActivity.getSystemService("layout_inflater");
/* 5459 */     if (inflater == null) {
/*      */       return;
/*      */     }
/* 5462 */     View renameDialog = inflater.inflate(R.layout.dialog_rename_file, null);
/* 5463 */     String title = getString(R.string.action_export_options);
/*      */ 
/*      */ 
/*      */     
/* 5467 */     String extension = this.mTabTitle.contains("." + this.mFileExtension) ? "" : ("." + this.mFileExtension);
/* 5468 */     if (!external) {
/* 5469 */       File tempFile = new File(folder, this.mTabTitle + extension);
/* 5470 */       copyPath = (new File(Utils.getFileNameNotInUse(tempFile.getAbsolutePath()))).getName();
/*      */     } else {
/* 5472 */       copyPath = Utils.getFileNameNotInUse(externalFolder, this.mTabTitle + extension);
/*      */     } 
/*      */     
/* 5475 */     final EditText renameEditText = (EditText)renameDialog.findViewById(R.id.dialog_rename_file_edit);
/* 5476 */     renameEditText.setText(copyPath);
/*      */ 
/*      */     
/* 5479 */     int index = FilenameUtils.indexOfExtension(copyPath);
/* 5480 */     if (index == -1) {
/* 5481 */       index = copyPath.length();
/*      */     }
/* 5483 */     renameEditText.setSelection(0, index);
/* 5484 */     renameEditText.setHint(getString(R.string.dialog_rename_file_hint));
/*      */     
/* 5486 */     AlertDialog.Builder builder = new AlertDialog.Builder((Context)fragmentActivity);
/* 5487 */     builder.setView(renameDialog)
/* 5488 */       .setTitle(title)
/* 5489 */       .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener()
/*      */         {
/*      */           public void onClick(DialogInterface dialog, int which)
/*      */           {
/* 5493 */             if (!PdfViewCtrlTabFragment.this.isAdded()) {
/*      */               return;
/*      */             }
/* 5496 */             Boolean doAction = Boolean.valueOf(true);
/* 5497 */             String message = "";
/*      */             
/* 5499 */             File newFile = null;
/* 5500 */             ExternalFileInfo newExtFile = null;
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 5505 */             if (renameEditText.getText().toString().trim().length() == 0) {
/* 5506 */               doAction = Boolean.valueOf(false);
/* 5507 */               message = PdfViewCtrlTabFragment.this.getString(R.string.dialog_rename_invalid_file_name_message);
/*      */             }
/*      */             else {
/*      */               
/* 5511 */               String newFileName = renameEditText.getText().toString().trim();
/* 5512 */               if (!newFileName.toLowerCase().endsWith("." + PdfViewCtrlTabFragment.this.mFileExtension)) {
/* 5513 */                 newFileName = newFileName + "." + PdfViewCtrlTabFragment.this.mFileExtension;
/*      */               }
/*      */               
/* 5516 */               if (!external) {
/* 5517 */                 newFile = new File(folder, newFileName);
/*      */               } else {
/* 5519 */                 newExtFile = externalFolder.getFile(newFileName);
/*      */               } 
/*      */               
/* 5522 */               if ((!external && newFile.exists()) || (external && newExtFile != null)) {
/* 5523 */                 doAction = Boolean.valueOf(false);
/* 5524 */                 message = PdfViewCtrlTabFragment.this.getString(R.string.dialog_rename_invalid_file_name_already_exists_message);
/*      */ 
/*      */               
/*      */               }
/* 5528 */               else if (external) {
/* 5529 */                 String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(PdfViewCtrlTabFragment.this.mFileExtension);
/* 5530 */                 newExtFile = externalFolder.createFile(mimeType, newFileName);
/* 5531 */                 if (newExtFile == null) {
/* 5532 */                   doAction = Boolean.valueOf(false);
/* 5533 */                   message = PdfViewCtrlTabFragment.this.getString(R.string.error);
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */             
/* 5538 */             if (doAction.booleanValue()) {
/* 5539 */               PdfViewCtrlTabFragment.this.handleSaveAsCopy(newFile, newExtFile);
/*      */             }
/* 5541 */             else if (message.length() > 0) {
/* 5542 */               Utils.showAlertDialog(activity, message, PdfViewCtrlTabFragment.this
/* 5543 */                   .getString(R.string.alert));
/*      */             
/*      */             }
/*      */           
/*      */           }
/* 5548 */         }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
/*      */         {
/*      */           public void onClick(DialogInterface dialog, int which)
/*      */           {
/* 5552 */             dialog.cancel();
/*      */           }
/*      */         });
/*      */     
/* 5556 */     final AlertDialog dialog = builder.create();
/*      */     
/* 5558 */     dialog.setOnShowListener(new DialogInterface.OnShowListener()
/*      */         {
/*      */           public void onShow(DialogInterface dialog) {
/* 5561 */             if (renameEditText.length() > 0) {
/* 5562 */               ((AlertDialog)dialog).getButton(-1).setEnabled(true);
/*      */             } else {
/* 5564 */               ((AlertDialog)dialog).getButton(-1).setEnabled(false);
/*      */             } 
/*      */           }
/*      */         });
/*      */     
/* 5569 */     renameEditText.addTextChangedListener(new TextWatcher()
/*      */         {
/*      */           public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
/*      */ 
/*      */ 
/*      */           
/*      */           public void onTextChanged(CharSequence s, int start, int before, int count) {
/* 5576 */             if (s.length() > 0) {
/*      */               
/* 5578 */               dialog.getButton(-1).setEnabled(true);
/*      */             } else {
/*      */               
/* 5581 */               dialog.getButton(-1).setEnabled(false);
/*      */             } 
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public void afterTextChanged(Editable s) {}
/*      */         });
/* 5591 */     renameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener()
/*      */         {
/*      */           public void onFocusChange(View view, boolean hasFocus) {
/* 5594 */             if (hasFocus && dialog.getWindow() != null) {
/* 5595 */               dialog.getWindow().setSoftInputMode(5);
/*      */             }
/*      */           }
/*      */         });
/*      */     
/* 5600 */     dialog.show();
/*      */   }
/*      */   
/*      */   protected void handleSaveAsCopy(File file, ExternalFileInfo externalFileInfo) {
/* 5604 */     final FragmentActivity activity = getActivity();
/* 5605 */     if (fragmentActivity == null) {
/*      */       return;
/*      */     }
/*      */     
/* 5609 */     final ProgressDialog progressDialog = new ProgressDialog((Context)fragmentActivity);
/*      */     
/* 5611 */     this.mDisposables.add(saveAsCopyDisposable(file, externalFileInfo)
/* 5612 */         .subscribeOn(Schedulers.io())
/* 5613 */         .observeOn(AndroidSchedulers.mainThread())
/* 5614 */         .doOnSubscribe(new Consumer<Disposable>()
/*      */           {
/*      */             public void accept(Disposable disposable) throws Exception {
/* 5617 */               progressDialog.setMessage(PdfViewCtrlTabFragment.this.getString(R.string.save_as_wait));
/* 5618 */               progressDialog.setCancelable(false);
/* 5619 */               progressDialog.setProgressStyle(0);
/* 5620 */               progressDialog.setIndeterminate(true);
/* 5621 */               progressDialog.show();
/*      */             }
/* 5624 */           }).subscribe(new Consumer<Pair<Boolean, String>>()
/*      */           {
/*      */             public void accept(Pair<Boolean, String> params) throws Exception {
/* 5627 */               progressDialog.dismiss();
/*      */               
/* 5629 */               if (((Boolean)params.first).booleanValue()) {
/* 5630 */                 File copyFile = new File((String)params.second);
/* 5631 */                 PdfViewCtrlTabFragment.this.openFileInNewTab(copyFile);
/*      */               } else {
/* 5633 */                 Uri uri = Uri.parse((String)params.second);
/* 5634 */                 PdfViewCtrlTabFragment.this.openFileUriInNewTab(uri);
/*      */               } 
/* 5636 */               AnalyticsHandlerAdapter.getInstance().sendEvent(65, 
/* 5637 */                   AnalyticsParam.viewerSaveCopyParam(1));
/*      */             }
/*      */           }new Consumer<Throwable>()
/*      */           {
/*      */             public void accept(Throwable throwable) throws Exception {
/* 5642 */               progressDialog.dismiss();
/*      */               
/* 5644 */               CommonToast.showText((Context)activity, R.string.save_to_copy_failed);
/*      */               
/* 5646 */               AnalyticsHandlerAdapter.getInstance().sendException(new Exception(throwable), "handleSaveAsCopy");
/*      */             }
/*      */           }));
/*      */   }
/*      */ 
/*      */   
/*      */   protected Single<Pair<Boolean, String>> saveAsCopyDisposable(final File file, final ExternalFileInfo externalFileInfo) {
/* 5653 */     return Single.create(new SingleOnSubscribe<Pair<Boolean, String>>()
/*      */         {
/*      */           public void subscribe(SingleEmitter<Pair<Boolean, String>> emitter) throws Exception {
/* 5656 */             boolean external = (externalFileInfo != null); try {
/*      */               boolean fromLocal; String resultPath;
/* 5658 */               File copyFile = null;
/* 5659 */               if (!external) {
/* 5660 */                 copyFile = file;
/*      */               }
/*      */               
/* 5663 */               boolean success = false;
/* 5664 */               if (copyFile != null) {
/* 5665 */                 success = PdfViewCtrlTabFragment.this.copyFileSourceToTempFile(copyFile);
/* 5666 */               } else if (externalFileInfo != null) {
/* 5667 */                 success = PdfViewCtrlTabFragment.this.copyFileSourceToTempUri(externalFileInfo.getUri());
/*      */               } 
/*      */               
/* 5670 */               if (!success) {
/*      */                 
/* 5672 */                 emitter.tryOnError(new IllegalStateException("Unable to get a valid PDFDoc. Error occurred copying source file to temp file."));
/*      */ 
/*      */                 
/*      */                 return;
/*      */               } 
/*      */               
/* 5678 */               if (!external) {
/* 5679 */                 fromLocal = true;
/* 5680 */                 resultPath = copyFile.getAbsolutePath();
/*      */               } else {
/* 5682 */                 fromLocal = false;
/* 5683 */                 resultPath = externalFileInfo.getUri().toString();
/*      */               } 
/* 5685 */               if (resultPath != null) {
/* 5686 */                 emitter.onSuccess(new Pair(Boolean.valueOf(fromLocal), resultPath));
/*      */               } else {
/* 5688 */                 throw new Exception("Could not create resulting path");
/*      */               } 
/* 5690 */             } catch (Exception e) {
/* 5691 */               AnalyticsHandlerAdapter.getInstance().sendException(e);
/* 5692 */               emitter.tryOnError(e);
/*      */             } 
/*      */           }
/*      */         });
/*      */   }
/*      */   
/*      */   protected void handleSaveFlattenedCopy(SaveFolderWrapper folderWrapper) {
/* 5699 */     final FragmentActivity activity = getActivity();
/* 5700 */     if (fragmentActivity == null) {
/*      */       return;
/*      */     }
/*      */     
/* 5704 */     final ProgressDialog progressDialog = new ProgressDialog((Context)fragmentActivity);
/*      */     
/* 5706 */     this.mDisposables.add(saveFlattenedCopyDisposable(folderWrapper)
/* 5707 */         .subscribeOn(Schedulers.io())
/* 5708 */         .observeOn(AndroidSchedulers.mainThread())
/* 5709 */         .doOnSubscribe(new Consumer<Disposable>()
/*      */           {
/*      */             public void accept(Disposable disposable) throws Exception {
/* 5712 */               progressDialog.setMessage(PdfViewCtrlTabFragment.this.getString(R.string.save_flatten_wait));
/* 5713 */               progressDialog.setCancelable(false);
/* 5714 */               progressDialog.setProgressStyle(0);
/* 5715 */               progressDialog.setIndeterminate(true);
/* 5716 */               progressDialog.show();
/*      */             }
/* 5719 */           }).subscribe(new Consumer<Pair<Boolean, String>>()
/*      */           {
/*      */             public void accept(Pair<Boolean, String> params) throws Exception {
/* 5722 */               progressDialog.dismiss();
/*      */               
/* 5724 */               if (((Boolean)params.first).booleanValue()) {
/* 5725 */                 File copyFile = new File((String)params.second);
/* 5726 */                 PdfViewCtrlTabFragment.this.openFileInNewTab(copyFile);
/*      */               } else {
/* 5728 */                 Uri uri = Uri.parse((String)params.second);
/* 5729 */                 PdfViewCtrlTabFragment.this.openFileUriInNewTab(uri);
/*      */               } 
/* 5731 */               AnalyticsHandlerAdapter.getInstance().sendEvent(65, 
/* 5732 */                   AnalyticsParam.viewerSaveCopyParam(2));
/*      */             }
/*      */           }new Consumer<Throwable>()
/*      */           {
/*      */             public void accept(Throwable throwable) throws Exception {
/* 5737 */               progressDialog.dismiss();
/*      */               
/* 5739 */               CommonToast.showText((Context)activity, R.string.save_to_copy_failed);
/*      */               
/* 5741 */               AnalyticsHandlerAdapter.getInstance().sendException(new Exception(throwable), "handleSaveFlattenedCopy");
/*      */             }
/*      */           }));
/*      */   }
/*      */ 
/*      */   
/*      */   protected Single<Pair<Boolean, String>> saveFlattenedCopyDisposable(final SaveFolderWrapper folderWrapper) {
/* 5748 */     return Single.create(new SingleOnSubscribe<Pair<Boolean, String>>()
/*      */         {
/*      */           public void subscribe(SingleEmitter<Pair<Boolean, String>> emitter) throws Exception {
/* 5751 */             PDFDoc copyDoc = folderWrapper.getDoc();
/* 5752 */             if (null == copyDoc) {
/*      */               
/* 5754 */               folderWrapper.cleanup();
/* 5755 */               emitter.tryOnError(new IllegalStateException("Unable to get a valid PDFDoc. PDFDoc is null"));
/*      */               
/*      */               return;
/*      */             } 
/*      */             
/*      */             try {
/* 5761 */               ViewerUtils.flattenDoc(copyDoc);
/*      */               
/* 5763 */               emitter.onSuccess(folderWrapper.save(copyDoc));
/* 5764 */             } catch (Exception e) {
/* 5765 */               AnalyticsHandlerAdapter.getInstance().sendException(e);
/* 5766 */               emitter.tryOnError(e);
/*      */             } 
/*      */           }
/*      */         });
/*      */   }
/*      */   
/*      */   protected void handleSaveOptimizedCopy(SaveFolderWrapper folderWrapper, Object customObject) {
/* 5773 */     final FragmentActivity activity = getActivity();
/* 5774 */     if (fragmentActivity == null) {
/*      */       return;
/*      */     }
/*      */     
/* 5778 */     final ProgressDialog progressDialog = new ProgressDialog((Context)fragmentActivity);
/*      */     
/* 5780 */     this.mDisposables.add(saveOptimizedCopyDisposable(folderWrapper, customObject)
/* 5781 */         .subscribeOn(Schedulers.io())
/* 5782 */         .observeOn(AndroidSchedulers.mainThread())
/* 5783 */         .doOnSubscribe(new Consumer<Disposable>()
/*      */           {
/*      */             public void accept(Disposable disposable) throws Exception {
/* 5786 */               progressDialog.setMessage(PdfViewCtrlTabFragment.this.getString(R.string.save_optimize_wait));
/* 5787 */               progressDialog.setCancelable(false);
/* 5788 */               progressDialog.setProgressStyle(0);
/* 5789 */               progressDialog.setIndeterminate(true);
/* 5790 */               progressDialog.show();
/*      */             }
/* 5793 */           }).subscribe(new Consumer<Pair<Boolean, String>>()
/*      */           {
/*      */             public void accept(Pair<Boolean, String> params) throws Exception {
/* 5796 */               progressDialog.dismiss();
/*      */               
/* 5798 */               String size = null;
/* 5799 */               if (((Boolean)params.first).booleanValue()) {
/* 5800 */                 File copyFile = new File((String)params.second);
/* 5801 */                 PdfViewCtrlTabFragment.this.openFileInNewTab(copyFile);
/* 5802 */                 size = Utils.humanReadableByteCount(copyFile.length(), false);
/*      */               } else {
/* 5804 */                 Uri uri = Uri.parse((String)params.second);
/* 5805 */                 PdfViewCtrlTabFragment.this.openFileUriInNewTab(uri);
/* 5806 */                 ExternalFileInfo fileInfo = Utils.buildExternalFile((Context)activity, uri);
/* 5807 */                 if (fileInfo != null) {
/* 5808 */                   size = fileInfo.getSizeInfo();
/*      */                 }
/*      */               } 
/* 5811 */               if (size != null) {
/* 5812 */                 CommonToast.showText((Context)activity, PdfViewCtrlTabFragment.this.getString(R.string.save_optimize_new_size_toast, new Object[] { size }));
/*      */               }
/* 5814 */               AnalyticsHandlerAdapter.getInstance().sendEvent(65, 
/* 5815 */                   AnalyticsParam.viewerSaveCopyParam(4));
/*      */             }
/*      */           }new Consumer<Throwable>()
/*      */           {
/*      */             public void accept(Throwable throwable) throws Exception {
/* 5820 */               progressDialog.dismiss();
/*      */               
/* 5822 */               CommonToast.showText((Context)activity, R.string.save_to_copy_failed);
/*      */               
/* 5824 */               AnalyticsHandlerAdapter.getInstance().sendException(new Exception(throwable), "handleSaveOptimizedCopy");
/*      */             }
/*      */           }));
/*      */   }
/*      */ 
/*      */   
/*      */   protected Single<Pair<Boolean, String>> saveOptimizedCopyDisposable(final SaveFolderWrapper folderWrapper, final Object customObject) {
/* 5831 */     return Single.create(new SingleOnSubscribe<Pair<Boolean, String>>()
/*      */         {
/*      */           public void subscribe(SingleEmitter<Pair<Boolean, String>> emitter) throws Exception {
/* 5834 */             PDFDoc copyDoc = folderWrapper.getDoc();
/* 5835 */             if (null == copyDoc) {
/*      */               
/* 5837 */               folderWrapper.cleanup();
/* 5838 */               emitter.tryOnError(new IllegalStateException("Unable to get a valid PDFDoc. PDFDoc is null"));
/*      */               
/*      */               return;
/*      */             } 
/*      */             try {
/* 5843 */               OptimizeParams optimizeParams = (OptimizeParams)customObject;
/*      */               
/* 5845 */               OptimizeDialogFragment.optimize(copyDoc, optimizeParams);
/*      */               
/* 5847 */               emitter.onSuccess(folderWrapper.save(copyDoc, false));
/* 5848 */             } catch (Exception e) {
/* 5849 */               AnalyticsHandlerAdapter.getInstance().sendException(e);
/* 5850 */               emitter.tryOnError(e);
/*      */             } 
/*      */           }
/*      */         });
/*      */   }
/*      */   
/*      */   protected void handleSavePasswordCopy(SaveFolderWrapper folderWrapper, final Object customObject) {
/* 5857 */     final FragmentActivity activity = getActivity();
/* 5858 */     if (fragmentActivity == null) {
/*      */       return;
/*      */     }
/*      */     
/* 5862 */     final ProgressDialog progressDialog = new ProgressDialog((Context)fragmentActivity);
/*      */     
/* 5864 */     this.mDisposables.add(savePasswordCopyDisposable(folderWrapper, customObject)
/* 5865 */         .subscribeOn(Schedulers.io())
/* 5866 */         .observeOn(AndroidSchedulers.mainThread())
/* 5867 */         .doOnSubscribe(new Consumer<Disposable>()
/*      */           {
/*      */             public void accept(Disposable disposable) throws Exception {
/* 5870 */               progressDialog.setMessage(PdfViewCtrlTabFragment.this.getString(R.string.save_password_wait));
/* 5871 */               progressDialog.setCancelable(false);
/* 5872 */               progressDialog.setProgressStyle(0);
/* 5873 */               progressDialog.setIndeterminate(true);
/* 5874 */               progressDialog.show();
/*      */             }
/* 5877 */           }).subscribe(new Consumer<Pair<Boolean, String>>()
/*      */           {
/*      */             public void accept(Pair<Boolean, String> params) throws Exception {
/* 5880 */               progressDialog.dismiss();
/*      */               
/* 5882 */               String password = (String)customObject;
/* 5883 */               if (((Boolean)params.first).booleanValue()) {
/* 5884 */                 File copyFile = new File((String)params.second);
/* 5885 */                 PdfViewCtrlTabFragment.this.openFileInNewTab(copyFile, password);
/*      */               } else {
/* 5887 */                 Uri uri = Uri.parse((String)params.second);
/* 5888 */                 PdfViewCtrlTabFragment.this.openFileUriInNewTab(uri, password);
/*      */               } 
/* 5890 */               AnalyticsHandlerAdapter.getInstance().sendEvent(65, 
/* 5891 */                   AnalyticsParam.viewerSaveCopyParam(5));
/*      */             }
/*      */           }new Consumer<Throwable>()
/*      */           {
/*      */             public void accept(Throwable throwable) throws Exception {
/* 5896 */               progressDialog.dismiss();
/*      */               
/* 5898 */               CommonToast.showText((Context)activity, R.string.save_to_copy_failed);
/*      */               
/* 5900 */               AnalyticsHandlerAdapter.getInstance().sendException(new Exception(throwable), "handleSavePasswordCopy");
/*      */             }
/*      */           }));
/*      */   }
/*      */ 
/*      */   
/*      */   protected Single<Pair<Boolean, String>> savePasswordCopyDisposable(final SaveFolderWrapper folderWrapper, final Object customObject) {
/* 5907 */     return Single.create(new SingleOnSubscribe<Pair<Boolean, String>>()
/*      */         {
/*      */           public void subscribe(SingleEmitter<Pair<Boolean, String>> emitter) throws Exception {
/* 5910 */             PDFDoc copyDoc = folderWrapper.getDoc();
/* 5911 */             if (null == copyDoc) {
/*      */               
/* 5913 */               folderWrapper.cleanup();
/* 5914 */               emitter.tryOnError(new IllegalStateException("Unable to get a valid PDFDoc. PDFDoc is null"));
/*      */               
/*      */               return;
/*      */             } 
/*      */             try {
/* 5919 */               String password = (String)customObject;
/*      */               
/* 5921 */               ViewerUtils.passwordDoc(copyDoc, password);
/*      */               
/* 5923 */               emitter.onSuccess(folderWrapper.save(copyDoc));
/* 5924 */             } catch (Exception e) {
/* 5925 */               AnalyticsHandlerAdapter.getInstance().sendException(e);
/* 5926 */               emitter.tryOnError(e);
/*      */             } 
/*      */           }
/*      */         });
/*      */   }
/*      */   
/*      */   protected void handleSaveCroppedCopy(SaveFolderWrapper folderWrapper) {
/* 5933 */     final FragmentActivity activity = getActivity();
/* 5934 */     if (fragmentActivity == null) {
/*      */       return;
/*      */     }
/*      */     
/* 5938 */     final ProgressDialog progressDialog = new ProgressDialog((Context)fragmentActivity);
/*      */     
/* 5940 */     this.mDisposables.add(saveCroppedCopyDisposable(folderWrapper)
/* 5941 */         .subscribeOn(Schedulers.io())
/* 5942 */         .observeOn(AndroidSchedulers.mainThread())
/* 5943 */         .doOnSubscribe(new Consumer<Disposable>()
/*      */           {
/*      */             public void accept(Disposable disposable) throws Exception {
/* 5946 */               progressDialog.setMessage(PdfViewCtrlTabFragment.this.getString(R.string.save_crop_wait));
/* 5947 */               progressDialog.setCancelable(false);
/* 5948 */               progressDialog.setProgressStyle(0);
/* 5949 */               progressDialog.setIndeterminate(true);
/* 5950 */               progressDialog.show();
/*      */             }
/* 5953 */           }).subscribe(new Consumer<Pair<Boolean, String>>()
/*      */           {
/*      */             public void accept(Pair<Boolean, String> params) throws Exception {
/* 5956 */               progressDialog.dismiss();
/*      */               
/* 5958 */               if (((Boolean)params.first).booleanValue()) {
/* 5959 */                 File copyFile = new File((String)params.second);
/* 5960 */                 PdfViewCtrlTabFragment.this.openFileInNewTab(copyFile);
/*      */               } else {
/* 5962 */                 Uri uri = Uri.parse((String)params.second);
/* 5963 */                 PdfViewCtrlTabFragment.this.openFileUriInNewTab(uri);
/*      */               } 
/* 5965 */               AnalyticsHandlerAdapter.getInstance().sendEvent(65, 
/* 5966 */                   AnalyticsParam.viewerSaveCopyParam(3));
/*      */             }
/*      */           }new Consumer<Throwable>()
/*      */           {
/*      */             public void accept(Throwable throwable) throws Exception {
/* 5971 */               progressDialog.dismiss();
/*      */               
/* 5973 */               CommonToast.showText((Context)activity, R.string.save_to_copy_failed);
/*      */               
/* 5975 */               AnalyticsHandlerAdapter.getInstance().sendException(new Exception(throwable), "handleSaveCroppedCopy");
/*      */             }
/*      */           }));
/*      */   }
/*      */ 
/*      */   
/*      */   protected Single<Pair<Boolean, String>> saveCroppedCopyDisposable(final SaveFolderWrapper folderWrapper) {
/* 5982 */     return Single.create(new SingleOnSubscribe<Pair<Boolean, String>>()
/*      */         {
/*      */           public void subscribe(SingleEmitter<Pair<Boolean, String>> emitter) throws Exception {
/* 5985 */             PDFDoc copyDoc = folderWrapper.getDoc();
/* 5986 */             if (null == copyDoc) {
/*      */               
/* 5988 */               folderWrapper.cleanup();
/* 5989 */               emitter.tryOnError(new IllegalStateException("Unable to get a valid PDFDoc. PDFDoc is null."));
/*      */               
/*      */               return;
/*      */             } 
/*      */             
/*      */             try {
/* 5995 */               UserCropUtilities.cropDoc(copyDoc);
/*      */               
/* 5997 */               emitter.onSuccess(folderWrapper.save(copyDoc));
/* 5998 */             } catch (Exception e) {
/* 5999 */               AnalyticsHandlerAdapter.getInstance().sendException(e);
/* 6000 */               emitter.tryOnError(e);
/*      */             } 
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */   
/*      */   protected PasswordDialogFragment getPasswordDialog() {
/* 6008 */     String hint = "";
/* 6009 */     if (isPasswordProtected()) {
/* 6010 */       hint = getString(R.string.password_input_hint);
/*      */     }
/* 6012 */     PasswordDialogFragment passwordDialog = PasswordDialogFragment.newInstance(this.mTabSource, (File)null, (String)null, (String)null, hint);
/*      */     
/* 6014 */     return passwordDialog;
/*      */   }
/*      */   
/*      */   protected Single<Boolean> hasUserCropBoxDisposable() {
/* 6018 */     return Single.fromCallable(new Callable<Boolean>()
/*      */         {
/*      */           public Boolean call() throws Exception
/*      */           {
/* 6022 */             return Boolean.valueOf(PdfViewCtrlTabFragment.this.hasUserCropBox());
/*      */           }
/*      */         });
/*      */   }
/*      */   
/*      */   public boolean hasUserCropBox() {
/* 6028 */     boolean hasCrop = false;
/* 6029 */     boolean shouldUnlockRead = false;
/*      */     try {
/* 6031 */       this.mPdfViewCtrl.cancelRenderingAsync();
/* 6032 */       this.mPdfViewCtrl.docLockRead();
/* 6033 */       shouldUnlockRead = true;
/* 6034 */       PageIterator pageIterator = this.mPdfDoc.getPageIterator();
/* 6035 */       while (pageIterator.hasNext()) {
/* 6036 */         Page page = pageIterator.next();
/* 6037 */         if (!page.getCropBox().equals(page.getBox(5))) {
/* 6038 */           hasCrop = true;
/*      */           break;
/*      */         } 
/*      */       } 
/* 6042 */     } catch (Exception e) {
/* 6043 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } finally {
/* 6045 */       if (shouldUnlockRead) {
/* 6046 */         this.mPdfViewCtrl.docUnlockRead();
/*      */       }
/*      */     } 
/* 6049 */     return hasCrop;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleViewFileAttachments() {
/* 6055 */     PortfolioDialogFragment portfolioDialog = PortfolioDialogFragment.newInstance(2, R.string.file_attachments);
/* 6056 */     portfolioDialog.initParams(this.mPdfDoc);
/* 6057 */     portfolioDialog.setListener(this);
/* 6058 */     FragmentManager fragmentManager = getFragmentManager();
/* 6059 */     if (fragmentManager != null) {
/* 6060 */       portfolioDialog.show(fragmentManager, "portfolio_dialog");
/*      */     }
/*      */   }
/*      */   
/*      */   public void handleViewSelectedFileAttachment(final File localFolder, final ExternalFileInfo extFolder) {
/* 6065 */     if (Utils.isNullOrEmpty(this.mSelectedFileAttachmentName)) {
/* 6066 */       Log.e(TAG, "ERROR: mFileAttachment is NULL OR EMPTY");
/*      */       
/*      */       return;
/*      */     } 
/* 6070 */     final FragmentActivity activity = getActivity();
/* 6071 */     if (fragmentActivity == null || this.mPdfViewCtrl == null) {
/*      */       return;
/*      */     }
/*      */     
/* 6075 */     Integer fileType = null;
/* 6076 */     String destFolderPath = null;
/* 6077 */     if (localFolder != null) {
/* 6078 */       fileType = Integer.valueOf(0);
/* 6079 */       destFolderPath = localFolder.getAbsolutePath();
/* 6080 */     } else if (extFolder != null) {
/* 6081 */       fileType = Integer.valueOf(1);
/* 6082 */       destFolderPath = extFolder.getAbsolutePath();
/*      */     } 
/* 6084 */     if (destFolderPath != null && fileType != null) {
/* 6085 */       final ProgressDialog progressDialog = new ProgressDialog((Context)fragmentActivity);
/* 6086 */       this.mDisposables.add(ViewerUtils.extractFileFromPortfolioDisposable(fileType.intValue(), (Context)fragmentActivity, this.mPdfDoc, destFolderPath, this.mSelectedFileAttachmentName)
/* 6087 */           .subscribeOn(Schedulers.io())
/* 6088 */           .observeOn(AndroidSchedulers.mainThread())
/* 6089 */           .doOnSubscribe(new Consumer<Disposable>()
/*      */             {
/*      */               public void accept(Disposable disposable) throws Exception {
/* 6092 */                 progressDialog.setMessage(PdfViewCtrlTabFragment.this.getString(R.string.tools_misc_please_wait));
/* 6093 */                 progressDialog.setCancelable(false);
/* 6094 */                 progressDialog.setProgressStyle(0);
/* 6095 */                 progressDialog.setIndeterminate(true);
/* 6096 */                 progressDialog.show();
/*      */               }
/* 6099 */             }).subscribe(new Consumer<String>()
/*      */             {
/*      */               public void accept(String param) throws Exception {
/* 6102 */                 progressDialog.dismiss();
/*      */                 
/* 6104 */                 if (!Utils.isNullOrEmpty(param)) {
/* 6105 */                   if (Utils.isExtensionHandled(Utils.getExtension(PdfViewCtrlTabFragment.this.mSelectedFileAttachmentName))) {
/* 6106 */                     if (localFolder != null) {
/* 6107 */                       File file = new File(param);
/* 6108 */                       PdfViewCtrlTabFragment.this.openFileInNewTab(file);
/* 6109 */                     } else if (extFolder != null) {
/* 6110 */                       PdfViewCtrlTabFragment.this.openFileUriInNewTab(Uri.parse(param));
/*      */                     }
/*      */                   
/* 6113 */                   } else if (localFolder != null) {
/* 6114 */                     File file = new File(param);
/* 6115 */                     Uri uri = Utils.getUriForFile((Context)activity, file);
/* 6116 */                     if (uri != null) {
/* 6117 */                       Utils.shareGenericFile(activity, uri);
/*      */                     }
/* 6119 */                   } else if (extFolder != null) {
/* 6120 */                     Uri fileUri = Uri.parse(param);
/* 6121 */                     Utils.shareGenericFile(activity, fileUri);
/*      */                   } 
/*      */                 }
/*      */ 
/*      */                 
/* 6126 */                 PdfViewCtrlTabFragment.this.mSelectedFileAttachmentName = null;
/*      */               }
/*      */             }new Consumer<Throwable>()
/*      */             {
/*      */               public void accept(Throwable throwable) throws Exception {
/* 6131 */                 progressDialog.dismiss();
/*      */                 
/* 6133 */                 PdfViewCtrlTabFragment.this.mSelectedFileAttachmentName = null;
/*      */               }
/*      */             }));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void onPortfolioDialogFragmentFileClicked(int fileType, PortfolioDialogFragment dialog, String fileName) {
/* 6142 */     this.mSelectedFileAttachmentName = fileName;
/* 6143 */     handleViewSelectedFileAttachment(getExportDirectory(), (ExternalFileInfo)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleOnlineShare() {
/* 6150 */     FragmentActivity fragmentActivity = getActivity();
/* 6151 */     if (fragmentActivity == null) {
/*      */       return;
/*      */     }
/*      */     
/* 6155 */     switch (this.mTabSource) {
/*      */       case 2:
/*      */       case 13:
/* 6158 */         Utils.sharePdfFile((Activity)fragmentActivity, this.mCurrentFile);
/*      */         break;
/*      */       
/*      */       case 5:
/* 6162 */         if (this.mCurrentFile != null && this.mCurrentFile.isFile()) {
/* 6163 */           if (this.mToolManager.isReadOnly()) {
/* 6164 */             CommonToast.showText((Context)fragmentActivity, R.string.download_not_finished_yet_warning, 0);
/*      */             return;
/*      */           } 
/* 6167 */           Utils.sharePdfFile((Activity)fragmentActivity, this.mCurrentFile);
/*      */         } 
/*      */         break;
/*      */       case 6:
/* 6171 */         if (this.mCurrentUriFile != null) {
/* 6172 */           Utils.shareGenericFile((Activity)fragmentActivity, this.mCurrentUriFile);
/*      */         }
/*      */         break;
/*      */       case 15:
/* 6176 */         Utils.shareGenericFile((Activity)fragmentActivity, Uri.parse(this.mTabTag));
/*      */         break;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void cancelUniversalConversion() {
/* 6182 */     if (sDebug)
/* 6183 */       Log.i("UNIVERSAL_TABCYCLE", FilenameUtils.getName(this.mTabTag) + " Cancels universal conversion"); 
/* 6184 */     Utils.closeDocQuietly(this.mPdfViewCtrl);
/* 6185 */     setViewerHostVisible(false);
/* 6186 */     this.mDocumentLoaded = false;
/*      */   }
/*      */   
/*      */   private String getUrlEncodedTabFilename(String realUrl) {
/* 6190 */     String title = getTabTitleWithUniversalExtension(realUrl);
/*      */     try {
/* 6192 */       return URLEncoder.encode(title, "UTF-8");
/* 6193 */     } catch (UnsupportedEncodingException e) {
/* 6194 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/* 6195 */       Log.e(TAG, "We don't support utf-8 encoding for URLs?");
/*      */       
/* 6197 */       return title;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTabSource() {
/* 6206 */     return this.mTabSource;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getTabTag() {
/* 6215 */     return this.mTabTag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getTabTitle() {
/* 6224 */     return this.mTabTitle;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getTabTitleWithExtension() {
/* 6233 */     if (this.mTabTitle.toLowerCase().endsWith(".pdf")) {
/* 6234 */       return this.mTabTitle;
/*      */     }
/* 6236 */     return this.mTabTitle + ".pdf";
/*      */   }
/*      */   
/*      */   private String getTabTitleWithUniversalExtension(String realUrl) {
/* 6240 */     String ext = Utils.getExtension(realUrl);
/* 6241 */     if (Utils.isNullOrEmpty(ext)) {
/* 6242 */       ext = ".pdf";
/*      */     } else {
/* 6244 */       ext = "." + ext;
/* 6245 */     }  if (this.mTabTitle.toLowerCase().endsWith(ext)) {
/* 6246 */       return this.mTabTitle;
/*      */     }
/* 6248 */     return this.mTabTitle + ext;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSavingEnabled(boolean enabled) {
/* 6257 */     this.mSavingEnabled = enabled;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setStateEnabled(boolean enabled) {
/* 6266 */     this.mStateEnabled = enabled;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onThumbnailsViewDialogDismiss(int pageNum, boolean docPagesModified) {
/* 6276 */     resetHidePageNumberIndicatorTimer();
/*      */     
/* 6278 */     if (this.mPdfViewCtrl == null) {
/*      */       return;
/*      */     }
/*      */     
/* 6282 */     this.mPdfViewCtrl.resume();
/* 6283 */     setCurrentPageHelper(pageNum, false);
/*      */     
/* 6285 */     refreshPageCount();
/*      */     
/* 6287 */     if (docPagesModified) {
/* 6288 */       onDocPagesModified();
/*      */     }
/*      */     
/* 6291 */     if (this.mIsReflowMode) {
/*      */       
/* 6293 */       setViewerHostVisible(false);
/* 6294 */       this.mPdfViewCtrl.pause();
/*      */     } 
/*      */     
/* 6297 */     resetAutoSavingTimer();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDocPagesModified() {
/* 6304 */     if (this.mPdfViewCtrl == null) {
/*      */       return;
/*      */     }
/*      */     
/* 6308 */     clearPageBackAndForwardStacks();
/*      */ 
/*      */     
/* 6311 */     if (this.mIsReflowMode && this.mReflowControl != null) {
/*      */       try {
/* 6313 */         this.mReflowControl.notifyPagesModified();
/* 6314 */       } catch (Exception e) {
/* 6315 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public void onAddNewPages(Page[] pages) {
/* 6321 */     if (pages == null || pages.length == 0 || this.mPdfViewCtrl == null || this.mToolManager == null) {
/*      */       return;
/*      */     }
/* 6324 */     PDFDoc doc = this.mPdfViewCtrl.getDoc();
/* 6325 */     if (doc == null) {
/*      */       return;
/*      */     }
/*      */     
/* 6329 */     int currentPage = 0;
/* 6330 */     boolean shouldUnlock = false;
/*      */     try {
/* 6332 */       this.mPdfViewCtrl.docLock(true);
/* 6333 */       shouldUnlock = true;
/* 6334 */       List<Integer> pageList = new ArrayList<>();
/* 6335 */       for (int i = 1, cnt = pages.length; i <= cnt; i++) {
/* 6336 */         int newPageNum = this.mPdfViewCtrl.getCurrentPage() + i;
/* 6337 */         pageList.add(Integer.valueOf(newPageNum));
/* 6338 */         doc.pageInsert(doc.getPageIterator(newPageNum), pages[i - 1]);
/*      */       } 
/* 6340 */       this.mPageCount = doc.getPageCount();
/* 6341 */       currentPage = this.mPdfViewCtrl.getCurrentPage() + 1;
/* 6342 */       this.mPdfViewCtrl.setCurrentPage(currentPage);
/* 6343 */       updatePageIndicator();
/*      */       
/* 6345 */       this.mToolManager.raisePagesAdded(pageList);
/* 6346 */     } catch (Exception e) {
/* 6347 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } finally {
/* 6349 */       if (shouldUnlock) {
/* 6350 */         this.mPdfViewCtrl.docUnlock();
/*      */       }
/*      */       try {
/* 6353 */         this.mPdfViewCtrl.updatePageLayout();
/* 6354 */         onThumbnailsViewDialogDismiss(currentPage, true);
/* 6355 */       } catch (PDFNetException e) {
/* 6356 */         AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*      */       } 
/*      */     } 
/*      */     
/* 6360 */     onDocPagesModified();
/*      */   }
/*      */ 
/*      */   
/*      */   public void onAddNewPage(Page page) {
/* 6365 */     if (page == null) {
/*      */       return;
/*      */     }
/* 6368 */     Page[] pages = new Page[1];
/* 6369 */     pages[0] = page;
/* 6370 */     onAddNewPages(pages);
/*      */   }
/*      */   
/*      */   public void onDeleteCurrentPage() {
/* 6374 */     if (this.mPdfViewCtrl == null || this.mToolManager == null) {
/*      */       return;
/*      */     }
/*      */     
/* 6378 */     PDFDoc doc = this.mPdfViewCtrl.getDoc();
/* 6379 */     boolean shouldUnlock = false;
/* 6380 */     int currentPageNum = 0;
/*      */     try {
/* 6382 */       this.mPdfViewCtrl.docLock(true);
/* 6383 */       shouldUnlock = true;
/* 6384 */       currentPageNum = this.mPdfViewCtrl.getCurrentPage();
/* 6385 */       doc.pageRemove(doc.getPageIterator(currentPageNum));
/* 6386 */       this.mPageCount = doc.getPageCount();
/* 6387 */       updatePageIndicator();
/*      */       
/* 6389 */       List<Integer> pageList = new ArrayList<>(1);
/* 6390 */       pageList.add(Integer.valueOf(currentPageNum));
/* 6391 */       this.mToolManager.raisePagesDeleted(pageList);
/* 6392 */     } catch (Exception e) {
/* 6393 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */       return;
/*      */     } finally {
/* 6396 */       if (shouldUnlock) {
/* 6397 */         this.mPdfViewCtrl.docUnlock();
/*      */       }
/*      */       try {
/* 6400 */         onThumbnailsViewDialogDismiss(currentPageNum, true);
/* 6401 */         this.mPdfViewCtrl.updatePageLayout();
/* 6402 */       } catch (PDFNetException e) {
/* 6403 */         AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*      */       } 
/*      */     } 
/*      */     
/* 6407 */     onDocPagesModified();
/*      */   }
/*      */   
/*      */   protected void openLocalFile(String tag) {
/*      */     try {
/* 6412 */       if (this.mTabSource == 2 && !Utils.isNotPdf(tag)) {
/* 6413 */         this.mPdfDoc = new PDFDoc(tag);
/* 6414 */         checkPdfDoc();
/*      */       } 
/* 6416 */     } catch (Exception e) {
/* 6417 */       if (this.mCurrentFile != null && !this.mCurrentFile.exists()) {
/*      */         
/* 6419 */         this.mErrorCode = 7;
/* 6420 */       } else if (getContext() != null && !Utils.hasStoragePermission(getContext())) {
/* 6421 */         this.mErrorCode = 11;
/*      */       } else {
/*      */         
/* 6424 */         this.mErrorCode = 2;
/*      */       } 
/* 6426 */       handleOpeningDocumentFailed(this.mErrorCode);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void openExternalFile(String tag) {
/* 6431 */     if (!Utils.isNullOrEmpty(tag) && getContext() != null) {
/* 6432 */       this.mCurrentUriFile = Uri.parse(tag);
/* 6433 */       this.mPdfDoc = null;
/* 6434 */       if (this.mPDFDocLoaderTask != null && this.mPDFDocLoaderTask.getStatus() != AsyncTask.Status.FINISHED) {
/* 6435 */         this.mPDFDocLoaderTask.cancel(true);
/*      */       }
/* 6437 */       this.mPDFDocLoaderTask = new PDFDocLoaderTask(getContext());
/* 6438 */       this.mPDFDocLoaderTask.setFinishCallback(new PDFDocLoaderTask.onFinishListener()
/*      */           {
/*      */             public void onFinish(PDFDoc pdfDoc) {
/* 6441 */               PdfViewCtrlTabFragment.this.mPdfDoc = pdfDoc;
/* 6442 */               if (PdfViewCtrlTabFragment.this.mPdfDoc == null) {
/* 6443 */                 PdfViewCtrlTabFragment.this.handleOpeningDocumentFailed(1);
/*      */                 return;
/*      */               } 
/*      */               try {
/* 6447 */                 PdfViewCtrlTabFragment.this.checkPdfDoc();
/* 6448 */               } catch (Exception e) {
/* 6449 */                 PdfViewCtrlTabFragment.this.mPdfDoc = null;
/* 6450 */                 PdfViewCtrlTabFragment.this.handleOpeningDocumentFailed(1);
/* 6451 */                 AnalyticsHandlerAdapter.getInstance().sendException(e, "checkPdfDoc");
/*      */               } 
/*      */             }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             public void onCancelled() {}
/* 6460 */           }).execute((Object[])new Uri[] { this.mCurrentUriFile });
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void openEditUriFile(String uriString) {
/* 6465 */     FragmentActivity fragmentActivity = getActivity();
/* 6466 */     if (fragmentActivity == null) {
/*      */       return;
/*      */     }
/* 6469 */     Uri uri = Uri.parse(uriString);
/* 6470 */     final String fileName = this.mTabTitle + "." + Utils.getUriExtension(fragmentActivity.getContentResolver(), uri);
/*      */ 
/*      */     
/* 6473 */     this
/*      */ 
/*      */       
/* 6476 */       .mTempDownloadObservable = Utils.duplicateInFolder(Utils.getContentResolver((Context)fragmentActivity), uri, fileName, this.mCacheFolder).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).cache();
/*      */ 
/*      */     
/* 6479 */     this.mDisposables.add((Disposable)this.mTempDownloadObservable
/*      */         
/* 6481 */         .doOnSubscribe(new Consumer<Disposable>()
/*      */           {
/*      */             public void accept(Disposable disposable) {
/* 6484 */               if (disposable != null && !disposable.isDisposed() && PdfViewCtrlTabFragment.this.mDownloadDocumentDialog != null)
/*      */               {
/* 6486 */                 PdfViewCtrlTabFragment.this.mDownloadDocumentDialog.show();
/*      */               
/*      */               }
/*      */             }
/* 6490 */           }).subscribeWith((SingleObserver)new DisposableSingleObserver<File>()
/*      */           {
/*      */             public void onSuccess(File file) {
/* 6493 */               if (PdfViewCtrlTabFragment.this.mDownloadDocumentDialog != null) {
/* 6494 */                 PdfViewCtrlTabFragment.this.mDownloadDocumentDialog.dismiss();
/*      */               }
/* 6496 */               if (file != null) {
/* 6497 */                 PdfViewCtrlTabFragment.this.mCurrentFile = file;
/* 6498 */                 PdfViewCtrlTabFragment.this.mOriginalFileLength = PdfViewCtrlTabFragment.this.mCurrentFile.length();
/* 6499 */                 if (PdfViewCtrlTabFragment.this.mOriginalFileLength <= 0L) {
/* 6500 */                   PdfViewCtrlTabFragment.this.mCurrentFile = null;
/*      */                 }
/* 6502 */                 else if (PdfViewCtrlTabFragment.sDebug) {
/* 6503 */                   Log.d(PdfViewCtrlTabFragment.TAG, "save edit uri file to: " + PdfViewCtrlTabFragment.this.mCurrentFile.getAbsolutePath());
/*      */                 } 
/*      */               } 
/*      */               
/* 6507 */               if (PdfViewCtrlTabFragment.this.mCurrentFile != null) {
/*      */                 try {
/* 6509 */                   PdfViewCtrlTabFragment.this.mPdfDoc = new PDFDoc(PdfViewCtrlTabFragment.this.mCurrentFile.getAbsolutePath());
/* 6510 */                   PdfViewCtrlTabFragment.this.checkPdfDoc();
/* 6511 */                 } catch (Exception e) {
/* 6512 */                   PdfViewCtrlTabFragment.this.mPdfDoc = null;
/* 6513 */                   PdfViewCtrlTabFragment.this.handleOpeningDocumentFailed(1);
/* 6514 */                   String path = PdfViewCtrlTabFragment.this.mCurrentFile.getAbsolutePath();
/* 6515 */                   AnalyticsHandlerAdapter.getInstance().sendException(e, "checkPdfDoc " + path);
/*      */                 } 
/*      */               } else {
/* 6518 */                 PdfViewCtrlTabFragment.this.handleOpeningDocumentFailed(1);
/*      */               } 
/*      */             }
/*      */ 
/*      */             
/*      */             public void onError(Throwable e) {
/* 6524 */               if (PdfViewCtrlTabFragment.this.mDownloadDocumentDialog != null) {
/* 6525 */                 PdfViewCtrlTabFragment.this.mDownloadDocumentDialog.dismiss();
/*      */               }
/*      */               
/* 6528 */               if (e instanceof Exception) {
/* 6529 */                 if (e instanceof java.io.FileNotFoundException) {
/* 6530 */                   PdfViewCtrlTabFragment.this.handleOpeningDocumentFailed(7);
/* 6531 */                 } else if (e instanceof SecurityException) {
/* 6532 */                   PdfViewCtrlTabFragment.this.handleOpeningDocumentFailed(11);
/*      */                 } else {
/* 6534 */                   AnalyticsHandlerAdapter.getInstance().sendException((Exception)e, "title: " + fileName);
/*      */                 } 
/*      */               }
/*      */             }
/*      */           }));
/*      */   }
/*      */   
/*      */   private String getUrlWithoutParameters(String url) throws URISyntaxException {
/* 6542 */     URI uri = new URI(url);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6547 */     return (new URI(uri.getScheme(), uri.getAuthority(), uri.getPath(), null, uri.getFragment())).toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void openUrlFile(final String tag) {
/* 6554 */     FragmentActivity fragmentActivity = getActivity();
/* 6555 */     if (fragmentActivity == null || this.mPdfViewCtrl == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 6560 */     String realUrl = tag;
/* 6561 */     String extension = FilenameUtils.getExtension(tag);
/* 6562 */     if (Utils.isNullOrEmpty(extension) || extension.contains("?")) {
/*      */       try {
/* 6564 */         realUrl = getUrlWithoutParameters(tag);
/* 6565 */       } catch (Exception exception) {}
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 6570 */     String ext = Utils.getExtension(realUrl);
/* 6571 */     if (Utils.isNullOrEmpty(ext)) {
/*      */       
/* 6573 */       final String finalRealUrl = realUrl;
/* 6574 */       BasicHeadRequestTask.BasicHeadRequestTaskListener headListener = new BasicHeadRequestTask.BasicHeadRequestTaskListener()
/*      */         {
/*      */           public void onHeadRequestTask(Boolean pass, String result) {
/* 6577 */             if (pass.booleanValue() && result != null) {
/* 6578 */               boolean nonPDF = Utils.isNonPDFByMimeType(result);
/* 6579 */               String ext = MimeTypeMap.getSingleton().getExtensionFromMimeType(result);
/* 6580 */               PdfViewCtrlTabFragment.this.openUrlFileImpl(tag, finalRealUrl, nonPDF, ext);
/*      */             } else {
/*      */               
/* 6583 */               PdfViewCtrlTabFragment.this.openUrlFileImpl(tag, finalRealUrl, false, (String)null);
/*      */             } 
/*      */           }
/*      */         };
/* 6587 */       (new BasicHeadRequestTask((Context)fragmentActivity, headListener, tag, this.mCustomHeaders)).execute((Object[])new String[0]);
/*      */       
/*      */       return;
/*      */     } 
/* 6591 */     openUrlFileImpl(tag, realUrl, false, (String)null);
/*      */   }
/*      */   
/*      */   protected void openUrlFileImpl(String tag, String realUrl, boolean isNonPDF, @Nullable String ext) {
/* 6595 */     FragmentActivity fragmentActivity = getActivity();
/* 6596 */     if (fragmentActivity == null || this.mPdfViewCtrl == null) {
/*      */       return;
/*      */     }
/*      */     try {
/* 6600 */       this.mCanAddToTabInfo = false;
/* 6601 */       this.mToolManager.setReadOnly(true);
/*      */       
/* 6603 */       if (Utils.isNotPdf(realUrl) || isNonPDF) {
/* 6604 */         BasicHTTPDownloadTask.BasicHTTPDownloadTaskListener downListener = new BasicHTTPDownloadTask.BasicHTTPDownloadTaskListener()
/*      */           {
/*      */             public void onDownloadTask(Boolean pass, File saveFile) {
/* 6607 */               if (PdfViewCtrlTabFragment.this.mDownloadDocumentDialog != null && PdfViewCtrlTabFragment.this.mDownloadDocumentDialog.isShowing()) {
/* 6608 */                 PdfViewCtrlTabFragment.this.mDownloadDocumentDialog.dismiss();
/*      */               }
/* 6610 */               if (!pass.booleanValue()) {
/* 6611 */                 PdfViewCtrlTabFragment.this.mErrorCode = 1;
/* 6612 */                 PdfViewCtrlTabFragment.this.handleOpeningDocumentFailed(PdfViewCtrlTabFragment.this.mErrorCode);
/*      */               } else {
/* 6614 */                 PdfViewCtrlTabFragment.this.openOfficeDoc(saveFile.getAbsolutePath(), false);
/*      */               } 
/*      */             }
/*      */           };
/*      */         
/* 6619 */         String name = FilenameUtils.getName(realUrl);
/* 6620 */         if (ext != null) {
/* 6621 */           name = name + "." + ext;
/*      */         }
/* 6623 */         File saveFile = new File(getOpenUrlCacheFolder(), name);
/* 6624 */         saveFile = new File(Utils.getFileNameNotInUse(saveFile.getAbsolutePath()));
/* 6625 */         this.mCurrentFile = saveFile;
/* 6626 */         (new BasicHTTPDownloadTask((Context)fragmentActivity, downListener, tag, this.mCustomHeaders, saveFile)).execute((Object[])new String[0]);
/* 6627 */         this.mDownloadDocumentDialog.show();
/*      */       } else {
/* 6629 */         File cacheFile = new File(getOpenUrlCacheFolder(), getUrlEncodedTabFilename(realUrl));
/* 6630 */         String cacheFilePath = cacheFile.getAbsolutePath();
/* 6631 */         if (!Utils.isNullOrEmpty(cacheFilePath)) {
/* 6632 */           if (this.mViewerConfig == null)
/*      */           {
/*      */             
/* 6635 */             cacheFilePath = Utils.getFileNameNotInUse(cacheFilePath);
/*      */           }
/* 6637 */           this.mCurrentFile = new File(cacheFilePath);
/*      */         } 
/* 6639 */         this.mOpenUrlLink = tag;
/* 6640 */         PDFViewCtrl.HTTPRequestOptions httpRequestOptions = null;
/* 6641 */         if (this.mViewerConfig != null && this.mViewerConfig.isRestrictDownloadUsage()) {
/* 6642 */           httpRequestOptions = new PDFViewCtrl.HTTPRequestOptions();
/* 6643 */           httpRequestOptions.restrictDownloadUsage(true);
/*      */         } 
/* 6645 */         if (this.mCustomHeaders != null) {
/* 6646 */           if (null == httpRequestOptions) {
/* 6647 */             httpRequestOptions = new PDFViewCtrl.HTTPRequestOptions();
/*      */           }
/* 6649 */           Iterator<String> iter = this.mCustomHeaders.keys();
/* 6650 */           while (iter.hasNext()) {
/* 6651 */             String key = iter.next();
/* 6652 */             String val = this.mCustomHeaders.optString(key);
/* 6653 */             if (!Utils.isNullOrEmpty(val)) {
/* 6654 */               httpRequestOptions.addHeader(key, val);
/*      */             }
/*      */           } 
/*      */         } 
/* 6658 */         this.mPdfViewCtrl.openUrlAsync(tag, cacheFilePath, this.mPassword, httpRequestOptions);
/* 6659 */         this.mDownloading = true;
/* 6660 */         this.mDownloadDocumentDialog.show();
/*      */       } 
/* 6662 */     } catch (Exception e) {
/* 6663 */       if (this.mDownloadDocumentDialog != null && this.mDownloadDocumentDialog.isShowing()) {
/* 6664 */         this.mDownloadDocumentDialog.dismiss();
/*      */       }
/* 6666 */       this.mErrorCode = 1;
/* 6667 */       handleOpeningDocumentFailed(this.mErrorCode);
/* 6668 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected File getOpenUrlCacheFolder() {
/* 6673 */     Context context = getContext();
/* 6674 */     if (context == null) {
/* 6675 */       throw new IllegalStateException("Should not call getExportDirectory when context is invalid");
/*      */     }
/* 6677 */     File cacheFolder = Utils.getExternalDownloadDirectory(context);
/* 6678 */     if (this.mViewerConfig != null && !Utils.isNullOrEmpty(this.mViewerConfig.getOpenUrlCachePath())) {
/* 6679 */       File tempCacheFolder = new File(this.mViewerConfig.getOpenUrlCachePath());
/* 6680 */       if (tempCacheFolder.exists() && tempCacheFolder.isDirectory()) {
/* 6681 */         cacheFolder = tempCacheFolder;
/*      */       }
/*      */     } 
/* 6684 */     return cacheFolder;
/*      */   }
/*      */   
/*      */   protected void openConvertibleFormats(String tag) {
/* 6688 */     FragmentActivity fragmentActivity = getActivity();
/* 6689 */     if (fragmentActivity == null) {
/*      */       return;
/*      */     }
/* 6692 */     final ProgressDialog progressDialog = new ProgressDialog((Context)fragmentActivity);
/* 6693 */     this.mDisposables.add(convertToPdf(tag)
/* 6694 */         .subscribeOn(Schedulers.io())
/* 6695 */         .observeOn(AndroidSchedulers.mainThread())
/* 6696 */         .doOnSubscribe(new Consumer<Disposable>()
/*      */           {
/*      */             public void accept(Disposable disposable) throws Exception {
/* 6699 */               progressDialog.setMessage(PdfViewCtrlTabFragment.this.getString(R.string.convert_to_pdf_wait));
/* 6700 */               progressDialog.setCancelable(false);
/* 6701 */               progressDialog.setProgressStyle(0);
/* 6702 */               progressDialog.setIndeterminate(true);
/* 6703 */               progressDialog.show();
/*      */             }
/* 6706 */           }).subscribe(new Consumer<String>()
/*      */           {
/*      */             public void accept(String filePath) throws Exception {
/* 6709 */               progressDialog.dismiss();
/* 6710 */               PdfViewCtrlTabFragment.this.mCurrentFile = new File(filePath);
/* 6711 */               String oldTabTag = PdfViewCtrlTabFragment.this.mTabTag;
/* 6712 */               int oldTabSource = PdfViewCtrlTabFragment.this.mTabSource;
/* 6713 */               PdfViewCtrlTabFragment.this.mTabTag = PdfViewCtrlTabFragment.this.mCurrentFile.getAbsolutePath();
/* 6714 */               PdfViewCtrlTabFragment.this.mTabSource = 2;
/* 6715 */               PdfViewCtrlTabFragment.this.mTabTitle = FilenameUtils.removeExtension((new File(PdfViewCtrlTabFragment.this.mTabTag)).getName());
/* 6716 */               if ((!PdfViewCtrlTabFragment.this.mTabTag.equals(oldTabTag) || PdfViewCtrlTabFragment.this.mTabSource != oldTabSource) && 
/* 6717 */                 PdfViewCtrlTabFragment.this.mTabListener != null) {
/* 6718 */                 PdfViewCtrlTabFragment.this.mTabListener.onTabIdentityChanged(oldTabTag, PdfViewCtrlTabFragment.this.mTabTag, PdfViewCtrlTabFragment.this.mTabTitle, PdfViewCtrlTabFragment.this.mFileExtension, PdfViewCtrlTabFragment.this.mTabSource);
/*      */               }
/*      */               
/* 6721 */               PdfViewCtrlTabFragment.this.mToolManager.setReadOnly(false);
/* 6722 */               PdfViewCtrlTabFragment.this.openLocalFile(filePath);
/*      */             }
/*      */           }new Consumer<Throwable>()
/*      */           {
/*      */             public void accept(Throwable throwable) throws Exception {
/* 6727 */               progressDialog.dismiss();
/* 6728 */               PdfViewCtrlTabFragment.this.handleOpeningDocumentFailed(1);
/*      */             }
/*      */           }));
/*      */   }
/*      */ 
/*      */   
/*      */   protected Single<String> convertToPdf(final String filePath) {
/* 6735 */     return Single.create(new SingleOnSubscribe<String>()
/*      */         {
/*      */           public void subscribe(SingleEmitter<String> emitter) throws Exception {
/* 6738 */             File toConvertFile = new File(filePath);
/* 6739 */             String name = FilenameUtils.getName(filePath);
/* 6740 */             name = FilenameUtils.removeExtension(name);
/* 6741 */             File resultFile = new File(PdfViewCtrlTabFragment.this.getExportDirectory(), name + ".pdf");
/* 6742 */             String resultPath = Utils.getFileNameNotInUse(resultFile.getAbsolutePath());
/* 6743 */             PDFDoc newDoc = new PDFDoc();
/* 6744 */             Convert.toPdf((Doc)newDoc, filePath);
/* 6745 */             newDoc.save(resultPath, SDFDoc.SaveMode.REMOVE_UNUSED, null);
/* 6746 */             emitter.onSuccess(resultPath);
/*      */           }
/*      */         });
/*      */   }
/*      */   
/*      */   protected void openOfficeDoc(String tag, boolean isUri) {
/* 6752 */     openOfficeDoc(tag, isUri, (String)null);
/*      */   }
/*      */   
/*      */   protected void openOfficeDoc(final String tag, boolean isUri, final String pageOptionsJson) {
/* 6756 */     FragmentActivity fragmentActivity = getActivity();
/* 6757 */     if (fragmentActivity == null || this.mPdfViewCtrl == null || Utils.isNullOrEmpty(tag)) {
/*      */       return;
/*      */     }
/*      */     
/* 6761 */     if (this.mViewerConfig != null && this.mViewerConfig.isUseStandardLibrary()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 6766 */     if (Utils.isConvertibleFormat(tag)) {
/* 6767 */       openConvertibleFormats(tag);
/*      */       
/*      */       return;
/*      */     } 
/* 6771 */     this.mPdfDoc = null;
/*      */     
/* 6773 */     if (isUri) {
/* 6774 */       Uri uri1 = Uri.parse(tag);
/* 6775 */       this.mIsOfficeDoc = Utils.isOfficeDocument(fragmentActivity.getContentResolver(), uri1);
/*      */     } else {
/* 6777 */       this.mIsOfficeDoc = Utils.isOfficeDocument(tag);
/*      */     } 
/*      */ 
/*      */     
/* 6781 */     Uri uri = Uri.parse(tag);
/* 6782 */     if (isUri && !Utils.isUriSeekable((Context)fragmentActivity, uri)) {
/* 6783 */       String extension = Utils.getUriExtension(fragmentActivity.getContentResolver(), uri);
/* 6784 */       final String fileName = getTabTitle() + "." + extension;
/* 6785 */       this
/*      */ 
/*      */ 
/*      */         
/* 6789 */         .mTempDownloadObservable = Utils.duplicateInFolder(Utils.getContentResolver((Context)fragmentActivity), uri, fileName, this.mCacheFolder).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).cache();
/* 6790 */       this.mDisposables.add((Disposable)this.mTempDownloadObservable
/*      */           
/* 6792 */           .doOnSubscribe(new Consumer<Disposable>()
/*      */             {
/*      */               public void accept(Disposable disposable) {
/* 6795 */                 if (disposable != null && !disposable.isDisposed() && PdfViewCtrlTabFragment.this.mDownloadDocumentDialog != null)
/*      */                 {
/* 6797 */                   PdfViewCtrlTabFragment.this.mDownloadDocumentDialog.show();
/*      */                 
/*      */                 }
/*      */               }
/* 6801 */             }).subscribeWith((SingleObserver)new DisposableSingleObserver<File>()
/*      */             {
/*      */               public void onSuccess(File file) {
/* 6804 */                 if (PdfViewCtrlTabFragment.this.mDownloadDocumentDialog != null && PdfViewCtrlTabFragment.this.isVisible()) {
/* 6805 */                   PdfViewCtrlTabFragment.this.mDownloadDocumentDialog.dismiss();
/*      */                 }
/* 6807 */                 if (file != null && file.exists()) {
/* 6808 */                   PdfViewCtrlTabFragment.this.mNeedsCleanupFile = true;
/* 6809 */                   PdfViewCtrlTabFragment.this.tryToOpenOfficeDoc(false, file.getAbsolutePath(), pageOptionsJson);
/*      */                 } else {
/* 6811 */                   PdfViewCtrlTabFragment.this.tryToOpenOfficeDoc(true, tag, pageOptionsJson);
/*      */                 } 
/*      */               }
/*      */ 
/*      */               
/*      */               public void onError(Throwable e) {
/* 6817 */                 if (PdfViewCtrlTabFragment.this.mDownloadDocumentDialog != null) {
/* 6818 */                   PdfViewCtrlTabFragment.this.mDownloadDocumentDialog.dismiss();
/*      */                 }
/*      */                 
/* 6821 */                 if (e instanceof Exception) {
/* 6822 */                   if (e instanceof java.io.FileNotFoundException) {
/* 6823 */                     PdfViewCtrlTabFragment.this.handleOpeningDocumentFailed(7);
/* 6824 */                   } else if (e instanceof SecurityException) {
/* 6825 */                     PdfViewCtrlTabFragment.this.handleOpeningDocumentFailed(11);
/*      */                   } else {
/* 6827 */                     AnalyticsHandlerAdapter.getInstance().sendException((Exception)e, "title: " + fileName);
/*      */                   } 
/*      */                 }
/*      */               }
/*      */             }));
/*      */     } else {
/* 6833 */       tryToOpenOfficeDoc(isUri, tag, pageOptionsJson);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void tryToOpenOfficeDoc(boolean isUri, String tag, String pageOptionsJson) {
/*      */     try {
/* 6839 */       if (!isUri) {
/* 6840 */         this.mCurrentFile = new File(tag);
/* 6841 */         if (!this.mCurrentFile.exists()) {
/* 6842 */           handleOpeningDocumentFailed(7);
/*      */           return;
/*      */         } 
/* 6845 */         if (Utils.isNullOrEmpty(this.mTabConversionTempPath)) {
/* 6846 */           OfficeToPDFOptions options = null;
/* 6847 */           if (!Utils.isNullOrEmpty(pageOptionsJson)) {
/* 6848 */             if (sDebug)
/* 6849 */               Log.d(TAG, "PageSizes: " + pageOptionsJson); 
/* 6850 */             options = new OfficeToPDFOptions(pageOptionsJson);
/*      */           } 
/* 6852 */           if (null == options) {
/* 6853 */             if (this.mViewerConfig != null && this.mViewerConfig.getConversionOptions() != null) {
/*      */               
/* 6855 */               options = new OfficeToPDFOptions(this.mViewerConfig.getConversionOptions());
/*      */             } else {
/*      */               
/* 6858 */               if (sDebug)
/* 6859 */                 Log.d(TAG, "RemovePadding: true"); 
/* 6860 */               options = new OfficeToPDFOptions("{\"RemovePadding\": true}");
/*      */             } 
/*      */           }
/* 6863 */           this.mDocumentConversion = this.mPdfViewCtrl.openNonPDFUri(Uri.fromFile(this.mCurrentFile), (ConversionOptions)options);
/*      */         } 
/*      */       } else {
/* 6866 */         Uri uri = Uri.parse(tag);
/* 6867 */         this.mCurrentUriFile = uri;
/* 6868 */         if (Utils.isNullOrEmpty(this.mTabConversionTempPath)) {
/* 6869 */           this.mDocumentConversion = this.mPdfViewCtrl.openNonPDFUri(uri, null);
/*      */         }
/*      */       } 
/*      */       
/* 6873 */       this.mUniversalConverted = true;
/* 6874 */       this.mDocumentLoaded = false;
/*      */       
/* 6876 */       if (Utils.isNullOrEmpty(this.mTabConversionTempPath)) {
/* 6877 */         this.mDocumentState = 8;
/*      */       } else {
/*      */         
/* 6880 */         this.mPdfDoc = new PDFDoc(this.mTabConversionTempPath);
/* 6881 */         checkPdfDoc();
/* 6882 */         this.mDocumentState = 9;
/*      */       } 
/*      */       
/* 6885 */       this.mShouldNotifyWhenConversionFinishes = false;
/* 6886 */       this.mConversionFinishedMessageHandler.postDelayed(this.mConversionFinishedMessageRunnable, 20000L);
/* 6887 */       this.mIsEncrypted = false;
/*      */       
/* 6889 */       this.mToolManager.setTool(this.mToolManager.createTool((ToolManager.ToolModeBase)ToolManager.ToolMode.PAN, null));
/*      */       
/* 6891 */       this.mProgressBarLayout.show();
/* 6892 */     } catch (Exception e) {
/* 6893 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/* 6894 */       handleOpeningDocumentFailed(1);
/*      */     } 
/*      */   }
/*      */   protected void checkPdfDoc() throws PDFNetException {
/*      */     boolean hasRepairedXRef, initStdSecurityHandler;
/* 6899 */     FragmentActivity fragmentActivity = getActivity();
/* 6900 */     if (fragmentActivity == null || this.mPdfViewCtrl == null || this.mPdfDoc == null) {
/*      */       return;
/*      */     }
/* 6903 */     this.mDocumentLoading = false;
/*      */     
/* 6905 */     this.mDocumentLoaded = false;
/* 6906 */     this.mDocumentState = 0;
/*      */     
/* 6908 */     if (this.mTabListener != null) {
/* 6909 */       this.mTabListener.onUpdateOptionsMenu();
/*      */     }
/*      */     
/* 6912 */     boolean shouldUnlockRead = false;
/*      */ 
/*      */     
/* 6915 */     int pageCount = 0;
/*      */     try {
/* 6917 */       this.mPdfDoc.lockRead();
/* 6918 */       shouldUnlockRead = true;
/* 6919 */       hasRepairedXRef = this.mPdfDoc.hasRepairedXRef();
/* 6920 */       initStdSecurityHandler = this.mPdfDoc.initStdSecurityHandler(this.mPassword);
/* 6921 */       if (initStdSecurityHandler)
/*      */       {
/* 6923 */         pageCount = this.mPdfDoc.getPageCount();
/*      */       }
/*      */     } finally {
/* 6926 */       if (shouldUnlockRead) {
/* 6927 */         Utils.unlockReadQuietly(this.mPdfDoc);
/*      */       }
/*      */     } 
/*      */     
/* 6931 */     if (!initStdSecurityHandler) {
/* 6932 */       loadPasswordView();
/*      */       
/* 6934 */       this.mProgressBarLayout.hide(true);
/* 6935 */       if (sDebug)
/* 6936 */         Log.d(TAG, "hide progress bar"); 
/* 6937 */       this.mPasswordLayout.setVisibility(0);
/* 6938 */       this.mPasswordInput.requestFocus();
/* 6939 */       InputMethodManager imm = (InputMethodManager)fragmentActivity.getSystemService("input_method");
/* 6940 */       if (imm != null) {
/* 6941 */         imm.toggleSoftInput(2, 1);
/*      */       }
/* 6943 */       this.mBottomNavBar.setVisibility(8);
/*      */       return;
/*      */     } 
/* 6946 */     if (this.mPasswordLayout != null) {
/* 6947 */       this.mPasswordLayout.setVisibility(8);
/* 6948 */       this.mBottomNavBar.setVisibility(0);
/*      */     } 
/*      */     
/* 6951 */     if (hasRepairedXRef) {
/* 6952 */       this.mToolManager.setReadOnly(true);
/* 6953 */       this.mDocumentState = 3;
/*      */     } 
/*      */     
/* 6956 */     if (pageCount < 1) {
/* 6957 */       handleOpeningDocumentFailed(3);
/*      */     } else {
/* 6959 */       this.mPdfViewCtrl.setDoc(this.mPdfDoc);
/* 6960 */       if (this.mCurrentFile != null && 
/* 6961 */         !this.mCurrentFile.canWrite()) {
/* 6962 */         this.mToolManager.setReadOnly(true);
/* 6963 */         if (this.mDocumentState != 3) {
/* 6964 */           this.mDocumentState = 5;
/*      */         }
/*      */       } 
/*      */       
/* 6968 */       long size = getCurrentFileSize();
/* 6969 */       boolean canSave = Utils.hasEnoughStorageToSave(size);
/* 6970 */       if (!canSave) {
/* 6971 */         this.mToolManager.setReadOnly(true);
/* 6972 */         this.mDocumentState = 10;
/*      */       } 
/* 6974 */       this.mPageCount = pageCount;
/*      */ 
/*      */       
/* 6977 */       if (this.mPassword != null && this.mPassword.isEmpty() && 
/* 6978 */         !Utils.isNullOrEmpty(this.mTabTag) && this.mPdfDoc != null && (
/* 6979 */         this.mViewerConfig == null || !this.mViewerConfig.isUseStandardLibrary())) {
/* 6980 */         RecentlyUsedCache.accessDocument(this.mTabTag, this.mPdfDoc);
/*      */       }
/*      */ 
/*      */       
/* 6984 */       this.mIsEncrypted = (this.mPassword != null && !this.mPassword.isEmpty());
/*      */       
/* 6986 */       if (this.mToolManager != null && this.mToolManager.getTool() == null) {
/* 6987 */         this.mToolManager.setTool(this.mToolManager.createTool((ToolManager.ToolModeBase)ToolManager.ToolMode.PAN, null));
/*      */       }
/*      */       
/* 6990 */       FragmentManager fragmentManager = getFragmentManager();
/* 6991 */       if (fragmentManager != null) {
/* 6992 */         Fragment thumbFragment = fragmentManager.findFragmentByTag("thumbnails_fragment");
/* 6993 */         if (thumbFragment != null && thumbFragment.getView() != null && 
/* 6994 */           thumbFragment instanceof ThumbnailsViewFragment) {
/* 6995 */           ((ThumbnailsViewFragment)thumbFragment).addDocPages();
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 7002 */     resetAutoSavingTimer();
/*      */     
/* 7004 */     if (this.mTabListener != null) {
/* 7005 */       this.mTabListener.onUpdateOptionsMenu();
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
/*      */   public boolean handleKeyUp(int keyCode, KeyEvent event) {
/* 7017 */     FragmentActivity fragmentActivity = getActivity();
/* 7018 */     if (fragmentActivity == null || fragmentActivity.isFinishing() || !isDocumentReady() || this.mPdfViewCtrl == null) {
/* 7019 */       return false;
/*      */     }
/*      */     
/* 7022 */     if (this.mAnnotationToolbar == null) {
/* 7023 */       createAnnotationToolbar();
/*      */     }
/*      */     
/* 7026 */     if (this.mAnnotationToolbar.handleKeyUp(keyCode, event)) {
/* 7027 */       return true;
/*      */     }
/*      */     
/* 7030 */     if (ShortcutHelper.isUndo(keyCode, event)) {
/* 7031 */       this.mAnnotationToolbar.closePopups();
/* 7032 */       if (this.mTabListener != null) {
/* 7033 */         this.mTabListener.onUndoRedoPopupClosed();
/*      */       }
/* 7035 */       undo();
/* 7036 */       return true;
/*      */     } 
/*      */     
/* 7039 */     if (ShortcutHelper.isRedo(keyCode, event)) {
/* 7040 */       this.mAnnotationToolbar.closePopups();
/* 7041 */       if (this.mTabListener != null) {
/* 7042 */         this.mTabListener.onUndoRedoPopupClosed();
/*      */       }
/* 7044 */       redo();
/* 7045 */       return true;
/*      */     } 
/*      */     
/* 7048 */     if (ShortcutHelper.isPrint(keyCode, event)) {
/* 7049 */       handlePrintAnnotationSummary();
/* 7050 */       return true;
/*      */     } 
/*      */     
/* 7053 */     if (ShortcutHelper.isAddBookmark(keyCode, event)) {
/* 7054 */       addPageToBookmark();
/* 7055 */       return true;
/*      */     } 
/*      */     
/* 7058 */     if (!this.mPageBackStack.isEmpty() && ShortcutHelper.isJumpPageBack(keyCode, event)) {
/* 7059 */       jumpPageBack();
/* 7060 */       return true;
/*      */     } 
/*      */     
/* 7063 */     if (!this.mPageForwardStack.isEmpty() && ShortcutHelper.isJumpPageForward(keyCode, event)) {
/* 7064 */       jumpPageForward();
/* 7065 */       return true;
/*      */     } 
/*      */     
/* 7068 */     if (ShortcutHelper.isRotateClockwise(keyCode, event)) {
/*      */       try {
/* 7070 */         this.mPdfViewCtrl.rotateClockwise();
/* 7071 */         this.mPdfViewCtrl.updatePageLayout();
/* 7072 */       } catch (Exception e) {
/* 7073 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */       } 
/* 7075 */       return true;
/*      */     } 
/*      */     
/* 7078 */     if (ShortcutHelper.isRotateCounterClockwise(keyCode, event)) {
/*      */       try {
/* 7080 */         this.mPdfViewCtrl.rotateCounterClockwise();
/* 7081 */         this.mPdfViewCtrl.updatePageLayout();
/* 7082 */       } catch (Exception e) {
/* 7083 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */       } 
/* 7085 */       return true;
/*      */     } 
/*      */     
/* 7088 */     boolean isZoomIn = ShortcutHelper.isZoomIn(keyCode, event);
/* 7089 */     boolean isZoomOut = ShortcutHelper.isZoomOut(keyCode, event);
/* 7090 */     boolean isResetZoom = ShortcutHelper.isResetZoom(keyCode, event);
/* 7091 */     if (isZoomIn || isZoomOut || isResetZoom) {
/* 7092 */       ToolManager.Tool tool = this.mToolManager.getTool();
/* 7093 */       if (tool instanceof TextSelect) {
/* 7094 */         ((TextSelect)tool).closeQuickMenu();
/* 7095 */         ((TextSelect)tool).clearSelection();
/*      */       } 
/*      */       
/* 7098 */       if (isZoomIn) {
/* 7099 */         this.mPdfViewCtrl.setZoom(0, 0, this.mPdfViewCtrl.getZoom() * 1.5D, true, true);
/* 7100 */       } else if (isZoomOut) {
/* 7101 */         this.mPdfViewCtrl.setZoom(0, 0, this.mPdfViewCtrl.getZoom() / 1.5D, true, true);
/*      */       } else {
/* 7103 */         PointF point = this.mPdfViewCtrl.getCurrentMousePosition();
/* 7104 */         resetZoom(point);
/*      */       } 
/*      */       
/* 7107 */       if (tool instanceof TextSelect) {
/* 7108 */         this.mResetTextSelectionHandler.removeCallbacksAndMessages(null);
/* 7109 */         this.mResetTextSelectionHandler.postDelayed(this.mResetTextSelectionRunnable, 500L);
/* 7110 */       } else if (tool instanceof AnnotEdit) {
/* 7111 */         this.mToolManager.setTool(this.mToolManager.createTool(((AnnotEdit)tool).getCurrentDefaultToolMode(), tool));
/*      */       } 
/*      */       
/* 7114 */       return true;
/*      */     } 
/*      */     
/* 7117 */     if (ShortcutHelper.isGotoFirstPage(keyCode, event)) {
/* 7118 */       setCurrentPageHelper(1, true);
/* 7119 */       return true;
/*      */     } 
/*      */     
/* 7122 */     if (ShortcutHelper.isGotoLastPage(keyCode, event)) {
/* 7123 */       setCurrentPageHelper(this.mPdfViewCtrl.getPageCount(), true);
/* 7124 */       return true;
/*      */     } 
/*      */     
/* 7127 */     DisplayMetrics metrics = new DisplayMetrics();
/* 7128 */     Display display = getActivity().getWindowManager().getDefaultDisplay();
/* 7129 */     display.getMetrics(metrics);
/* 7130 */     int screenWidth = metrics.widthPixels;
/* 7131 */     int screenHeight = metrics.heightPixels;
/* 7132 */     int widthStep = screenWidth / 8;
/* 7133 */     int heightStep = screenHeight / 8;
/*      */     
/* 7135 */     if (ShortcutHelper.isPageUp(keyCode, event)) {
/* 7136 */       int dy = this.mPdfViewCtrl.getHeight() - heightStep;
/* 7137 */       int y = this.mPdfViewCtrl.getScrollY();
/* 7138 */       this.mPdfViewCtrl.scrollBy(0, -dy);
/* 7139 */       int newY = this.mPdfViewCtrl.getScrollY();
/* 7140 */       if (y == newY) {
/* 7141 */         this.mPdfViewCtrl.gotoPreviousPage();
/*      */       }
/*      */     } 
/*      */     
/* 7145 */     if (ShortcutHelper.isPageDown(keyCode, event)) {
/* 7146 */       int dy = this.mPdfViewCtrl.getHeight() - heightStep;
/* 7147 */       int y = this.mPdfViewCtrl.getScrollY();
/* 7148 */       this.mPdfViewCtrl.scrollBy(0, dy);
/* 7149 */       int newY = this.mPdfViewCtrl.getScrollY();
/* 7150 */       if (y == newY) {
/* 7151 */         this.mPdfViewCtrl.gotoNextPage();
/*      */       }
/* 7153 */       return true;
/*      */     } 
/*      */     
/* 7156 */     if (ViewerUtils.isViewerZoomed(this.mPdfViewCtrl)) {
/*      */ 
/*      */       
/* 7159 */       if (ShortcutHelper.isScrollToLeft(keyCode, event)) {
/* 7160 */         if (!this.mPdfViewCtrl.turnPageInNonContinuousMode(this.mPdfViewCtrl.getCurrentPage(), false)) {
/* 7161 */           this.mPdfViewCtrl.scrollBy(-widthStep, 0);
/*      */         }
/* 7163 */         return true;
/*      */       } 
/* 7165 */       if (ShortcutHelper.isScrollToUp(keyCode, event)) {
/* 7166 */         this.mPdfViewCtrl.scrollBy(0, -heightStep);
/* 7167 */         return true;
/*      */       } 
/* 7169 */       if (ShortcutHelper.isScrollToRight(keyCode, event)) {
/* 7170 */         if (!this.mPdfViewCtrl.turnPageInNonContinuousMode(this.mPdfViewCtrl.getCurrentPage(), true)) {
/* 7171 */           this.mPdfViewCtrl.scrollBy(widthStep, 0);
/*      */         }
/* 7173 */         return true;
/*      */       } 
/* 7175 */       if (ShortcutHelper.isScrollToDown(keyCode, event)) {
/* 7176 */         this.mPdfViewCtrl.scrollBy(0, heightStep);
/* 7177 */         return true;
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/* 7182 */       if (ShortcutHelper.isScrollToLeft(keyCode, event)) {
/* 7183 */         this.mPdfViewCtrl.gotoPreviousPage();
/* 7184 */         return true;
/*      */       } 
/* 7186 */       if (ShortcutHelper.isScrollToUp(keyCode, event)) {
/* 7187 */         if (isContinuousPageMode()) {
/* 7188 */           this.mPdfViewCtrl.scrollBy(0, -heightStep);
/*      */         } else {
/* 7190 */           this.mPdfViewCtrl.gotoPreviousPage();
/*      */         } 
/* 7192 */         return true;
/*      */       } 
/* 7194 */       if (ShortcutHelper.isScrollToRight(keyCode, event)) {
/* 7195 */         this.mPdfViewCtrl.gotoNextPage();
/* 7196 */         return true;
/*      */       } 
/* 7198 */       if (ShortcutHelper.isScrollToDown(keyCode, event)) {
/* 7199 */         if (isContinuousPageMode()) {
/* 7200 */           this.mPdfViewCtrl.scrollBy(0, heightStep);
/*      */         } else {
/* 7202 */           this.mPdfViewCtrl.gotoNextPage();
/*      */         } 
/* 7204 */         return true;
/*      */       } 
/*      */     } 
/*      */     
/* 7208 */     if (keyCode == 4) {
/* 7209 */       if (getToolManager() != null && getToolManager().getTool() != null && ((Tool)getToolManager().getTool()).isEditingAnnot()) {
/* 7210 */         this.mPdfViewCtrl.closeTool();
/* 7211 */         return true;
/*      */       } 
/* 7213 */       if (this.mTabListener != null) {
/* 7214 */         return this.mTabListener.onBackPressed();
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 7219 */     return isAnnotationMode();
/*      */   }
/*      */   
/*      */   protected void handleOpeningDocumentFailed(int errorCode) {
/* 7223 */     handleOpeningDocumentFailed(errorCode, "");
/*      */   }
/*      */   
/*      */   protected void handleOpeningDocumentFailed(int errorCode, String info) {
/* 7227 */     stopAutoSavingTimer();
/* 7228 */     this.mDocumentLoading = false;
/* 7229 */     this.mCanAddToTabInfo = false;
/* 7230 */     this.mErrorOnOpeningDocument = true;
/* 7231 */     this.mErrorCode = errorCode;
/* 7232 */     if (this.mTabListener != null) {
/* 7233 */       this.mTabListener.onTabError(this.mErrorCode, info);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void handlePrintAnnotationSummary() {
/* 7238 */     if (checkTabConversionAndAlert(R.string.cant_print_while_converting_message, true, false)) {
/*      */       return;
/*      */     }
/*      */     
/* 7242 */     PrintAnnotationsSummaryDialogFragment dialog = PrintAnnotationsSummaryDialogFragment.newInstance(this.mPrintDocumentChecked, this.mPrintAnnotationsChecked, this.mPrintSummaryChecked);
/*      */     
/* 7244 */     dialog.setPrintAnnotationsSummaryListener(new PrintAnnotationsSummaryDialogFragment.PrintAnnotationsSummaryListener()
/*      */         {
/*      */           public void onConfirmPrintAnnotationSummary(boolean documentChecked, boolean annotationsChecked, boolean summaryChecked) {
/* 7247 */             PdfViewCtrlTabFragment.this.updatePrintDocumentMode(documentChecked);
/* 7248 */             PdfViewCtrlTabFragment.this.updatePrintAnnotationsMode(annotationsChecked);
/* 7249 */             PdfViewCtrlTabFragment.this.updatePrintSummaryMode(summaryChecked);
/*      */             
/* 7251 */             PdfViewCtrlTabFragment.this.sentToPrinterDialog();
/*      */           }
/*      */         });
/* 7254 */     FragmentManager fragmentManager = getFragmentManager();
/* 7255 */     if (fragmentManager != null) {
/* 7256 */       dialog.show(fragmentManager, "print_annotations_summary_dialog");
/*      */     }
/* 7258 */     AnalyticsHandlerAdapter.getInstance().sendEvent(14);
/*      */   }
/*      */   
/*      */   protected void addPageToBookmark() {
/* 7262 */     FragmentActivity fragmentActivity = getActivity();
/* 7263 */     if (fragmentActivity == null || isTabReadOnly()) {
/*      */       return;
/*      */     }
/*      */     
/* 7267 */     boolean shouldUnlock = false;
/*      */     try {
/* 7269 */       this.mPdfViewCtrl.docLock(false);
/* 7270 */       shouldUnlock = true;
/*      */       
/* 7272 */       int currentPage = this.mPdfViewCtrl.getCurrentPage();
/* 7273 */       long pageObjNum = this.mPdfViewCtrl.getDoc().getPage(currentPage).getSDFObj().getObjNum();
/* 7274 */       if (isTabReadOnly()) {
/* 7275 */         BookmarkManager.addUserBookmark((Context)fragmentActivity, this.mPdfViewCtrl.getDoc().getFileName(), pageObjNum, currentPage);
/*      */       } else {
/* 7277 */         BookmarkManager.addPdfBookmark((Context)fragmentActivity, this.mPdfViewCtrl, pageObjNum, currentPage);
/*      */       } 
/* 7279 */       CommonToast.showText((Context)fragmentActivity, R.string.controls_misc_bookmark_added);
/* 7280 */     } catch (Exception e) {
/* 7281 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } finally {
/* 7283 */       if (shouldUnlock) {
/* 7284 */         this.mPdfViewCtrl.docUnlock();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void resetZoom(PointF point) {
/*      */     PDFViewCtrl.PageViewMode refMode;
/* 7291 */     if (this.mPdfViewCtrl.isMaintainZoomEnabled()) {
/* 7292 */       refMode = this.mPdfViewCtrl.getPreferredViewMode();
/*      */     } else {
/* 7294 */       refMode = this.mPdfViewCtrl.getPageRefViewMode();
/*      */     } 
/* 7296 */     this.mPdfViewCtrl.setPageViewMode(refMode, (int)point.x, (int)point.y, true);
/*      */   }
/*      */   
/*      */   protected void sentToPrinterDialog() {
/* 7300 */     int printContent = 0;
/* 7301 */     if (this.mPrintDocumentChecked) {
/* 7302 */       printContent |= 0x1;
/*      */     }
/* 7304 */     if (this.mPrintAnnotationsChecked) {
/* 7305 */       printContent |= 0x2;
/*      */     }
/* 7307 */     if (this.mPrintSummaryChecked) {
/* 7308 */       printContent |= 0x4;
/*      */     }
/* 7310 */     handlePrintJob(printContent);
/*      */   }
/*      */   
/*      */   private void handlePrintJob(int _printContent) {
/* 7314 */     FragmentActivity fragmentActivity = getActivity();
/* 7315 */     if (fragmentActivity == null || this.mPdfViewCtrl == null) {
/*      */       return;
/*      */     }
/*      */     
/* 7319 */     if (_printContent < 1 || _printContent > 7) {
/*      */       return;
/*      */     }
/*      */     
/* 7323 */     Integer printContent = Integer.valueOf(_printContent);
/* 7324 */     Boolean isRtl = Boolean.valueOf(isRtlMode());
/*      */     
/*      */     try {
/* 7327 */       if (this.mTabSource == 5) {
/* 7328 */         Print.startPrintJob((Context)fragmentActivity, getString(R.string.app_name), this.mPdfViewCtrl.getDoc(), printContent, isRtl);
/*      */       } else {
/* 7330 */         Print.startPrintJob((Context)fragmentActivity, getString(R.string.app_name), this.mPdfDoc, printContent, isRtl);
/*      */       } 
/* 7332 */     } catch (Exception e) {
/* 7333 */       CommonToast.showText((Context)fragmentActivity, R.string.error_printing_file, 0);
/* 7334 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onExportAnnotations(PDFDoc outputDoc) {
/*      */     ExternalFileInfo fileInfo;
/* 7344 */     FragmentActivity fragmentActivity = getActivity();
/* 7345 */     if (fragmentActivity == null) {
/*      */       return;
/*      */     }
/*      */     
/* 7349 */     switch (this.mTabSource) {
/*      */       case 2:
/*      */       case 5:
/*      */       case 13:
/* 7353 */         if (null == this.mCurrentFile || !this.mCurrentFile.exists()) {
/*      */           return;
/*      */         }
/* 7356 */         handleExportAnnotations(this.mCurrentFile.getParentFile(), outputDoc);
/*      */         return;
/*      */       case 6:
/* 7359 */         if (this.mCurrentUriFile == null) {
/*      */           return;
/*      */         }
/* 7362 */         fileInfo = Utils.buildExternalFile((Context)fragmentActivity, this.mCurrentUriFile);
/* 7363 */         if (fileInfo != null) {
/* 7364 */           handleExportAnnotations(fileInfo.getParent(), outputDoc);
/*      */         }
/*      */         return;
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void handleExportAnnotations(File folder, PDFDoc outputDoc) {
/* 7371 */     boolean shouldUnlock = false;
/* 7372 */     File outputFile = null;
/* 7373 */     boolean success = false;
/*      */     
/* 7375 */     if (folder == null || outputDoc == null) {
/*      */       return;
/*      */     }
/*      */     
/*      */     try {
/* 7380 */       String extension = getString(R.string.document_export_annotations_extension);
/* 7381 */       File tempFile = new File(folder, this.mTabTitle + extension + ".pdf");
/* 7382 */       String outputPath = Utils.getFileNameNotInUse(tempFile.getAbsolutePath());
/* 7383 */       if (Utils.isNullOrEmpty(outputPath)) {
/*      */         return;
/*      */       }
/* 7386 */       outputFile = new File(outputPath);
/* 7387 */       outputDoc.lock();
/* 7388 */       shouldUnlock = true;
/* 7389 */       outputDoc.save(outputFile.getAbsolutePath(), SDFDoc.SaveMode.REMOVE_UNUSED, null);
/* 7390 */       success = true;
/* 7391 */     } catch (Exception ePDFNet) {
/* 7392 */       AnalyticsHandlerAdapter.getInstance().sendException(ePDFNet);
/*      */     } finally {
/* 7394 */       if (shouldUnlock) {
/* 7395 */         Utils.unlockQuietly(outputDoc);
/*      */       }
/* 7397 */       Utils.closeQuietly(outputDoc);
/*      */     } 
/*      */     
/* 7400 */     if (success) {
/* 7401 */       openFileInNewTab(outputFile);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void handleExportAnnotations(ExternalFileInfo folder, PDFDoc outputDoc) {
/* 7406 */     FragmentActivity fragmentActivity = getActivity();
/* 7407 */     if (fragmentActivity == null || folder == null || outputDoc == null) {
/*      */       return;
/*      */     }
/*      */     
/* 7411 */     boolean shouldUnlock = false;
/* 7412 */     SecondaryFileFilter filter = null;
/*      */     try {
/* 7414 */       String extension = getString(R.string.document_export_annotations_extension);
/* 7415 */       String outputPath = Utils.getFileNameNotInUse(folder, this.mTabTitle + extension + ".pdf");
/* 7416 */       ExternalFileInfo outputFile = folder.createFile("application/pdf", outputPath);
/*      */       
/* 7418 */       if (outputFile != null) {
/* 7419 */         outputDoc.lock();
/* 7420 */         shouldUnlock = true;
/*      */         
/* 7422 */         filter = new SecondaryFileFilter((Context)fragmentActivity, outputFile.getUri());
/* 7423 */         outputDoc.save((Filter)filter, SDFDoc.SaveMode.REMOVE_UNUSED);
/* 7424 */         openFileUriInNewTab(outputFile.getUri());
/*      */       } 
/* 7426 */     } catch (Exception ePDFNet) {
/* 7427 */       AnalyticsHandlerAdapter.getInstance().sendException(ePDFNet);
/*      */     } finally {
/* 7429 */       if (shouldUnlock) {
/* 7430 */         Utils.unlockQuietly(outputDoc);
/*      */       }
/* 7432 */       Utils.closeQuietly(outputDoc, filter);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Uri getUriFile() {
/* 7442 */     return this.mCurrentUriFile;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public File getFile() {
/* 7451 */     return this.mCurrentFile;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void refreshPageCount() {
/* 7458 */     boolean shouldUnlockRead = false;
/*      */     try {
/* 7460 */       this.mPdfViewCtrl.docLockRead();
/* 7461 */       shouldUnlockRead = true;
/* 7462 */       this.mPageCount = this.mPdfViewCtrl.getDoc().getPageCount();
/* 7463 */     } catch (Exception e) {
/* 7464 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } finally {
/* 7466 */       if (shouldUnlockRead) {
/* 7467 */         this.mPdfViewCtrl.docUnlockRead();
/*      */       }
/*      */     } 
/* 7470 */     if (this.mBottomNavBar != null) {
/* 7471 */       this.mBottomNavBar.refreshPageCount();
/*      */     }
/* 7473 */     updatePageIndicator();
/* 7474 */     if (this.mTabListener != null) {
/* 7475 */       this.mTabListener.onUpdateOptionsMenu();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canDocBeSaved() {
/* 7485 */     return (this.mDocumentState != 8);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isNotPdf() {
/* 7494 */     return Utils.isNotPdf(this.mTabTag);
/*      */   }
/*      */   
/*      */   public boolean doesSaveDocNeedNewTab() {
/* 7498 */     return (this.mDocumentState != 8 && this.mDocumentState != 9);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateColorMode() {
/*      */     InputStream is;
/*      */     OutputStream os;
/* 7506 */     FragmentActivity activity = getActivity();
/* 7507 */     if (activity == null || this.mPdfViewCtrl == null) {
/*      */       return;
/*      */     }
/*      */     
/* 7511 */     int colorMode = PdfViewCtrlSettingsManager.getColorMode((Context)activity);
/* 7512 */     int clientBackgroundColor = getPDFViewCtrlConfig((Context)activity).getClientBackgroundColor();
/* 7513 */     int mode = 0;
/* 7514 */     int customBGColor = 0;
/* 7515 */     int customTxtColor = 0;
/* 7516 */     switch (colorMode) {
/*      */       case 4:
/* 7518 */         customBGColor = PdfViewCtrlSettingsManager.getCustomColorModeBGColor((Context)activity);
/* 7519 */         customTxtColor = PdfViewCtrlSettingsManager.getCustomColorModeTextColor((Context)activity);
/* 7520 */         clientBackgroundColor = getViewerBackgroundColor(customBGColor);
/* 7521 */         mode = 2;
/*      */         break;
/*      */       case 2:
/* 7524 */         mode = 2;
/* 7525 */         is = null;
/* 7526 */         os = null;
/*      */         try {
/* 7528 */           File filterFile = new File(activity.getCacheDir(), "sepia_mode_filter.png");
/* 7529 */           if (!filterFile.exists() || !filterFile.isFile()) {
/* 7530 */             is = getResources().openRawResource(R.raw.sepia_mode_filter);
/* 7531 */             os = new FileOutputStream(filterFile);
/* 7532 */             IOUtils.copy(is, os);
/*      */           } 
/* 7534 */           this.mPdfViewCtrl.setColorPostProcessMapFile((Filter)new MappedFile(filterFile.getAbsolutePath()));
/* 7535 */         } catch (Exception e) {
/* 7536 */           AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */         } finally {
/* 7538 */           Utils.closeQuietly(is);
/* 7539 */           Utils.closeQuietly(os);
/*      */         } 
/*      */         break;
/*      */       case 3:
/* 7543 */         clientBackgroundColor = getPDFViewCtrlConfig((Context)activity).getClientBackgroundColorDark();
/* 7544 */         mode = 3;
/*      */         break;
/*      */     } 
/*      */     
/*      */     try {
/* 7549 */       this.mPdfViewCtrl.setClientBackgroundColor(
/* 7550 */           Color.red(clientBackgroundColor), 
/* 7551 */           Color.green(clientBackgroundColor), 
/* 7552 */           Color.blue(clientBackgroundColor), false);
/* 7553 */       this.mPdfViewCtrl.setColorPostProcessMode(mode);
/* 7554 */       if (colorMode == 4) {
/* 7555 */         this.mPdfViewCtrl.setColorPostProcessColors(customBGColor, customTxtColor);
/*      */       }
/* 7557 */     } catch (Exception e) {
/* 7558 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } 
/* 7560 */     this.mViewerHost.setBackgroundColor(this.mPdfViewCtrl.getClientBackgroundColor());
/*      */     
/* 7562 */     this.mToolManager.setNightMode(isNightModeForToolManager());
/* 7563 */     if (this.mIsReflowMode) {
/* 7564 */       updateReflowColorMode();
/*      */     }
/*      */   }
/*      */   
/*      */   private static int getViewerBackgroundColor(int color) {
/* 7569 */     float[] hsv = new float[3];
/* 7570 */     Color.RGBToHSV(Color.red(color), Color.green(color), Color.blue(color), hsv);
/* 7571 */     float hue = hsv[0] / 360.0F;
/* 7572 */     float saturation = hsv[1];
/* 7573 */     float value = hsv[2];
/*      */     
/* 7575 */     float lowEarthHue = 0.05F;
/* 7576 */     float highEarthHue = 0.11F;
/* 7577 */     boolean earthTones = (hue >= lowEarthHue && hue <= highEarthHue);
/* 7578 */     if (value > 0.5D) {
/* 7579 */       if (earthTones) {
/* 7580 */         value = (float)(value - 0.2D);
/* 7581 */         saturation = Math.min(saturation * 2.0F, Math.min(saturation + 0.05F, 1.0F));
/*      */       } else {
/* 7583 */         value = (float)(value * 0.6D);
/*      */       } 
/* 7585 */     } else if (value >= 0.3D) {
/* 7586 */       value = value / 2.0F + 0.05F;
/* 7587 */     } else if (value >= 0.1D) {
/* 7588 */       value = (float)(value - 0.1D);
/*      */     } else {
/* 7590 */       value = (float)(value + 0.1D);
/*      */     } 
/* 7592 */     if (!earthTones) {
/* 7593 */       float dist = Math.min(0.05F, lowEarthHue - hue);
/* 7594 */       if (hue > highEarthHue) {
/* 7595 */         dist = Math.min(0.05F, hue - highEarthHue);
/*      */       }
/* 7597 */       saturation -= saturation * 20.0F * dist * 0.6F;
/*      */     } 
/*      */     
/* 7600 */     hsv[0] = hue * 360.0F;
/* 7601 */     hsv[1] = saturation;
/* 7602 */     hsv[2] = value;
/* 7603 */     return Color.HSVToColor(hsv);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateReflowColorMode() {
/* 7610 */     FragmentActivity fragmentActivity = getActivity();
/* 7611 */     if (fragmentActivity == null) {
/*      */       return;
/*      */     }
/* 7614 */     if (this.mReflowControl != null && this.mReflowControl.isReady()) {
/*      */       try {
/* 7616 */         switch (PdfViewCtrlSettingsManager.getColorMode((Context)fragmentActivity)) {
/*      */           case 1:
/* 7618 */             this.mReflowControl.setDayMode();
/*      */             break;
/*      */           case 2:
/* 7621 */             this.mReflowControl.setCustomColorMode(-5422);
/*      */             break;
/*      */           case 3:
/* 7624 */             this.mReflowControl.setNightMode();
/*      */             break;
/*      */           case 4:
/* 7627 */             this.mReflowControl.setCustomColorMode(PdfViewCtrlSettingsManager.getCustomColorModeBGColor((Context)fragmentActivity));
/*      */             break;
/*      */         } 
/* 7630 */       } catch (PDFNetException e) {
/* 7631 */         AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setColorModeChanged() {
/* 7640 */     this.mColorModeChanged = true;
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
/*      */   public boolean onShowToolbar() {
/* 7652 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onHideToolbars() {
/* 7662 */     return (this.mBottomNavBar != null && !this.mBottomNavBar.isProgressChanging());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onEnterFullscreenMode() {
/* 7672 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onExitFullscreenMode() {
/* 7682 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setVisibilityOfImaginedToolbar(boolean visible) {
/* 7687 */     if (this.mPdfViewCtrl == null) {
/*      */       return;
/*      */     }
/*      */     
/* 7691 */     AnnotationToolbar annotationToolbar = this.mAnnotationToolbar;
/* 7692 */     if (annotationToolbar == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7699 */     int canvasHeight = this.mPdfViewCtrl.getViewCanvasHeight();
/* 7700 */     int viewHeight = this.mPdfViewCtrl.getHeight();
/* 7701 */     int scrollY = this.mPdfViewCtrl.getScrollY();
/* 7702 */     this.mPdfViewCtrl.setPageViewMode(PDFViewCtrl.PageViewMode.ZOOM);
/* 7703 */     int toolbarHeight = annotationToolbar.getHeight();
/* 7704 */     if (visible) {
/* 7705 */       int newViewHeight = viewHeight - toolbarHeight;
/*      */ 
/*      */       
/* 7708 */       int[] offsets = new int[2];
/* 7709 */       if (canvasHeight > viewHeight) {
/* 7710 */         offsets[1] = canvasHeight;
/*      */       } else {
/* 7712 */         this.mPdfViewCtrl.getContentSize(offsets);
/*      */       } 
/* 7714 */       int newScrollableHeight = Math.max(offsets[1] - newViewHeight, 0);
/*      */       
/* 7716 */       int newScrollY = Math.min(newScrollableHeight, scrollY + toolbarHeight);
/* 7717 */       int translateOffset = (toolbarHeight - newScrollY + scrollY) / 2;
/* 7718 */       this.mPdfViewCtrl.setNextOnLayoutAdjustments(0, newScrollY - scrollY, true);
/* 7719 */       if (translateOffset > 0) {
/* 7720 */         this.mPdfViewCtrl.setTranslationY(-translateOffset);
/* 7721 */         ViewPropertyAnimator ani = this.mPdfViewCtrl.animate();
/* 7722 */         ani.translationY(0.0F);
/* 7723 */         ani.setDuration(300L);
/* 7724 */         ani.start();
/*      */       } 
/*      */     } else {
/* 7727 */       int newViewHeight = viewHeight + toolbarHeight;
/* 7728 */       int newGraySpace = Math.max(newViewHeight - canvasHeight, 0);
/*      */ 
/*      */ 
/*      */       
/* 7732 */       int scrollYHandledByPdfViewCtrl = 0;
/* 7733 */       if (canvasHeight > viewHeight) {
/* 7734 */         int distanceFromBottom = canvasHeight - viewHeight + scrollY;
/* 7735 */         scrollYHandledByPdfViewCtrl = Math.max(0, toolbarHeight - distanceFromBottom);
/*      */       } 
/*      */       
/* 7738 */       int newScrollY = Math.max(scrollY - toolbarHeight, 0);
/*      */       
/* 7740 */       int graySpaceOffset = newGraySpace / 2;
/* 7741 */       int translateOffset = toolbarHeight - scrollY + newScrollY - graySpaceOffset;
/* 7742 */       this.mPdfViewCtrl.setNextOnLayoutAdjustments(0, newScrollY - scrollY + scrollYHandledByPdfViewCtrl, true);
/* 7743 */       if (translateOffset > 0) {
/* 7744 */         this.mPdfViewCtrl.setTranslationY(translateOffset);
/* 7745 */         ViewPropertyAnimator ani = this.mPdfViewCtrl.animate();
/* 7746 */         ani.translationY(0.0F);
/* 7747 */         ani.setDuration(300L);
/* 7748 */         ani.start();
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setViewerTopMargin(int height) {
/* 7754 */     if (this.mPdfViewCtrl == null || !(this.mPdfViewCtrl.getLayoutParams() instanceof ViewGroup.MarginLayoutParams)) {
/*      */       return;
/*      */     }
/* 7757 */     ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams)this.mPdfViewCtrl.getLayoutParams();
/* 7758 */     params.topMargin = height;
/* 7759 */     this.mPdfViewCtrl.setLayoutParams((ViewGroup.LayoutParams)params);
/* 7760 */     this.mPdfViewCtrl.requestLayout();
/*      */   }
/*      */   
/*      */   public void applyCutout(int top, int bottom) {
/* 7764 */     if (this.mPdfViewCtrl == null) {
/*      */       return;
/*      */     }
/* 7767 */     if (this.mPdfViewCtrl.getDisplayCutoutTop() != top || this.mPdfViewCtrl.getDisplayCutoutBottom() != bottom) {
/* 7768 */       if (top == -1) {
/* 7769 */         top = this.mPdfViewCtrl.getDisplayCutoutTop();
/*      */       }
/* 7771 */       if (bottom == -1) {
/* 7772 */         bottom = this.mPdfViewCtrl.getDisplayCutoutBottom();
/*      */       }
/* 7774 */       this.mPdfViewCtrl.setDisplayCutout(top, bottom);
/*      */     } 
/* 7776 */     if (this.mReflowControl != null) {
/* 7777 */       this.mReflowControl.setPadding(0, top, 0, bottom);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setThumbSliderVisible(boolean visible, boolean animateThumbSlider) {
/* 7788 */     FragmentActivity fragmentActivity = getActivity();
/* 7789 */     if (fragmentActivity == null) {
/*      */       return;
/*      */     }
/* 7792 */     if (this.mBottomNavBar == null) {
/*      */       return;
/*      */     }
/*      */     
/* 7796 */     boolean isVisible = (this.mBottomNavBar.getVisibility() == 0);
/*      */     
/* 7798 */     if (visible) {
/* 7799 */       if (!isVisible) {
/* 7800 */         if (this.mViewerConfig == null || this.mViewerConfig.isShowBottomNavBar()) {
/* 7801 */           this.mBottomNavBar.show(animateThumbSlider);
/*      */         }
/*      */ 
/*      */         
/* 7805 */         if (this.mPageBackButton != null && !this.mPageBackStack.isEmpty()) {
/* 7806 */           this.mPageBackButton.show();
/*      */         }
/* 7808 */         if (this.mPageForwardButton != null && !this.mPageForwardStack.isEmpty()) {
/* 7809 */           this.mPageForwardButton.show();
/*      */         }
/*      */       }
/*      */     
/* 7813 */     } else if (isVisible) {
/* 7814 */       this.mBottomNavBar.dismiss(animateThumbSlider);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isThumbSliderVisible() {
/* 7820 */     return (this.mBottomNavBar != null && this.mBottomNavBar.getVisibility() == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onActivityResult(int requestCode, int resultCode, Intent data) {
/* 7828 */     super.onActivityResult(requestCode, resultCode, data);
/*      */     
/* 7830 */     if (-1 == resultCode) {
/* 7831 */       if (requestCode == 10003) {
/*      */ 
/*      */         
/* 7834 */         if (this.mImageCreationMode != null) {
/* 7835 */           if (this.mImageCreationMode == ToolManager.ToolMode.SIGNATURE) {
/* 7836 */             this.mImageSignatureDelayCreation = true;
/* 7837 */             this.mAnnotIntentData = data;
/* 7838 */             if (canResumeWithoutReloading()) {
/* 7839 */               this.mImageSignatureDelayCreation = false;
/* 7840 */               ViewerUtils.createImageSignature((Activity)getActivity(), this.mAnnotIntentData, this.mPdfViewCtrl, this.mOutputFileUri, this.mAnnotTargetPoint, this.mAnnotTargetPage, this.mTargetWidget);
/*      */             } 
/*      */           } else {
/*      */             
/* 7844 */             this.mImageStampDelayCreation = true;
/* 7845 */             this.mAnnotIntentData = data;
/* 7846 */             if (canResumeWithoutReloading()) {
/* 7847 */               this.mImageStampDelayCreation = false;
/* 7848 */               ViewerUtils.createImageStamp((Activity)getActivity(), this.mAnnotIntentData, this.mPdfViewCtrl, this.mOutputFileUri, this.mAnnotTargetPoint);
/*      */             } 
/*      */           } 
/*      */         }
/* 7852 */       } else if (requestCode == 10011) {
/*      */ 
/*      */         
/* 7855 */         this.mFileAttachmentDelayCreation = true;
/* 7856 */         this.mAnnotIntentData = data;
/* 7857 */         if (canResumeWithoutReloading()) {
/* 7858 */           this.mFileAttachmentDelayCreation = false;
/* 7859 */           ViewerUtils.createFileAttachment((Activity)getActivity(), this.mAnnotIntentData, this.mPdfViewCtrl, this.mAnnotTargetPoint);
/*      */         }
/*      */       
/*      */       } 
/* 7863 */     } else if (this.mToolManager != null && this.mToolManager.getTool() != null) {
/* 7864 */       ((Tool)this.mToolManager.getTool()).clearTargetPoint();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updatePrintDocumentMode(boolean enabled) {
/* 7875 */     FragmentActivity fragmentActivity = getActivity();
/* 7876 */     if (fragmentActivity == null) {
/*      */       return;
/*      */     }
/*      */     
/* 7880 */     this.mPrintDocumentChecked = enabled;
/* 7881 */     PdfViewCtrlSettingsManager.setPrintDocumentMode((Context)fragmentActivity, this.mPrintDocumentChecked);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updatePrintAnnotationsMode(boolean enabled) {
/* 7890 */     FragmentActivity fragmentActivity = getActivity();
/* 7891 */     if (fragmentActivity == null) {
/*      */       return;
/*      */     }
/*      */     
/* 7895 */     this.mPrintAnnotationsChecked = enabled;
/* 7896 */     PdfViewCtrlSettingsManager.setPrintAnnotationsMode((Context)fragmentActivity, this.mPrintAnnotationsChecked);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updatePrintSummaryMode(boolean enabled) {
/* 7905 */     FragmentActivity fragmentActivity = getActivity();
/* 7906 */     if (fragmentActivity == null) {
/*      */       return;
/*      */     }
/*      */     
/* 7910 */     this.mPrintSummaryChecked = enabled;
/* 7911 */     PdfViewCtrlSettingsManager.setPrintSummaryMode((Context)fragmentActivity, this.mPrintSummaryChecked);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateRecentList() {
/* 7918 */     if (!isDocumentReady()) {
/*      */       return;
/*      */     }
/*      */     
/* 7922 */     updateRecentFile(getCurrentFileInfo());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateRecentFile(FileInfo fileInfo) {
/* 7931 */     if (this.mPdfViewCtrl == null) {
/*      */       return;
/*      */     }
/*      */     
/* 7935 */     if (fileInfo != null) {
/* 7936 */       fileInfo.setHScrollPos(this.mPdfViewCtrl.getHScrollPos());
/* 7937 */       fileInfo.setVScrollPos(this.mPdfViewCtrl.getVScrollPos());
/* 7938 */       fileInfo.setZoom(this.mPdfViewCtrl.getZoom());
/* 7939 */       fileInfo.setLastPage(this.mPdfViewCtrl.getCurrentPage());
/* 7940 */       fileInfo.setPageRotation(this.mPdfViewCtrl.getPageRotation());
/* 7941 */       fileInfo.setPagePresentationMode(this.mPdfViewCtrl.getPagePresentationMode());
/* 7942 */       fileInfo.setReflowMode(this.mIsReflowMode);
/* 7943 */       if (this.mReflowControl != null && this.mReflowControl.isReady()) {
/*      */         try {
/* 7945 */           int reflowTextSize = this.mReflowControl.getTextSizeInPercent();
/* 7946 */           fileInfo.setReflowTextSize(reflowTextSize);
/* 7947 */         } catch (Exception e) {
/* 7948 */           AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */         } 
/*      */       }
/* 7951 */       fileInfo.setRtlMode(this.mIsRtlMode);
/* 7952 */       fileInfo.setBookmarkDialogCurrentTab(this.mBookmarkDialogCurrentTab);
/* 7953 */       updateRecentFilesManager(fileInfo);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void updateRecentFilesManager(FileInfo fileInfo) {
/* 7958 */     FragmentActivity fragmentActivity = getActivity();
/* 7959 */     if (fragmentActivity == null) {
/*      */       return;
/*      */     }
/*      */     
/* 7963 */     getRecentFilesManager().updateFile((Context)fragmentActivity, fileInfo);
/*      */   }
/*      */   
/*      */   protected FileInfoManager getRecentFilesManager() {
/* 7967 */     return (FileInfoManager)RecentFilesManager.getInstance();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeFromRecentList() {
/* 7974 */     FragmentActivity fragmentActivity = getActivity();
/* 7975 */     if (fragmentActivity == null) {
/*      */       return;
/*      */     }
/*      */     
/* 7979 */     switch (this.mTabSource) {
/*      */       case 2:
/* 7981 */         if (this.mCurrentFile != null) {
/* 7982 */           RecentFilesManager.getInstance().removeFile((Context)fragmentActivity, new FileInfo(2, this.mCurrentFile, this.mIsEncrypted, 1));
/*      */         }
/*      */         break;
/*      */       
/*      */       case 6:
/*      */       case 13:
/*      */       case 15:
/* 7989 */         RecentFilesManager.getInstance().removeFile((Context)fragmentActivity, new FileInfo(this.mTabSource, this.mTabTag, this.mTabTitle, this.mIsEncrypted, 1));
/*      */         break;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean containsInRecentList(FileInfo fileInfo) {
/* 7996 */     FragmentActivity fragmentActivity = getActivity();
/* 7997 */     return (fragmentActivity != null && RecentFilesManager.getInstance().containsFile((Context)fragmentActivity, fileInfo));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addToRecentList(PdfViewCtrlTabInfo tabInfo) {
/* 8006 */     if (tabInfo == null) {
/*      */       return;
/*      */     }
/*      */     
/* 8010 */     FileInfo fileInfo = null;
/*      */     try {
/* 8012 */       switch (tabInfo.tabSource) {
/*      */         case 2:
/*      */         case 5:
/* 8015 */           if (this.mCurrentFile != null) {
/* 8016 */             fileInfo = new FileInfo(2, this.mCurrentFile, this.mIsEncrypted, 1);
/*      */           }
/*      */           break;
/*      */         case 6:
/*      */         case 15:
/* 8021 */           fileInfo = new FileInfo(tabInfo.tabSource, this.mTabTag, this.mTabTitle, this.mIsEncrypted, 1);
/*      */           break;
/*      */         case 13:
/* 8024 */           if (this.mCurrentFile != null) {
/* 8025 */             fileInfo = new FileInfo(13, this.mTabTag, this.mTabTitle, this.mIsEncrypted, 1);
/*      */           }
/*      */           break;
/*      */       } 
/* 8029 */     } catch (Exception e) {
/* 8030 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } 
/*      */     
/* 8033 */     if (fileInfo != null) {
/* 8034 */       addRecentFile(tabInfo, fileInfo);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void addRecentFile(@NonNull PdfViewCtrlTabInfo tabInfo, @Nullable FileInfo fileInfo) {
/* 8039 */     if (fileInfo != null) {
/* 8040 */       fileInfo.setLastPage(tabInfo.lastPage);
/* 8041 */       fileInfo.setPageRotation(tabInfo.pageRotation);
/* 8042 */       fileInfo.setPagePresentationMode(tabInfo.getPagePresentationMode());
/* 8043 */       fileInfo.setHScrollPos(tabInfo.hScrollPos);
/* 8044 */       fileInfo.setVScrollPos(tabInfo.vScrollPos);
/* 8045 */       fileInfo.setZoom(tabInfo.zoom);
/* 8046 */       fileInfo.setReflowMode(tabInfo.isReflowMode);
/* 8047 */       fileInfo.setReflowTextSize(tabInfo.reflowTextSize);
/* 8048 */       fileInfo.setRtlMode(tabInfo.isRtlMode);
/* 8049 */       fileInfo.setBookmarkDialogCurrentTab(tabInfo.bookmarkDialogCurrentTab);
/*      */       
/* 8051 */       addToRecentFilesManager(fileInfo);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void addToRecentFilesManager(FileInfo fileInfo) {
/* 8056 */     FragmentActivity fragmentActivity = getActivity();
/* 8057 */     if (fragmentActivity == null) {
/*      */       return;
/*      */     }
/*      */     
/* 8061 */     RecentFilesManager.getInstance().addFile((Context)fragmentActivity, fileInfo);
/*      */   }
/*      */   
/*      */   public void resetAutoSavingTimer() {
/* 8065 */     stopAutoSavingTimer();
/* 8066 */     if (this.mRequestSaveDocHandler != null) {
/* 8067 */       this.mRequestSaveDocHandler.post(this.mTickSaveDocCallback);
/*      */     }
/*      */   }
/*      */   
/*      */   public void stopAutoSavingTimer() {
/* 8072 */     if (this.mRequestSaveDocHandler != null) {
/* 8073 */       this.mRequestSaveDocHandler.removeCallbacksAndMessages(null);
/*      */     }
/*      */   }
/*      */   
/*      */   public long getCurrentFileSize() {
/*      */     try {
/* 8079 */       if (this.mCurrentFile != null)
/* 8080 */         return this.mCurrentFile.length(); 
/* 8081 */       if (this.mCurrentUriFile != null) {
/* 8082 */         ExternalFileInfo externalFileInfo = Utils.buildExternalFile(getContext(), this.mCurrentUriFile);
/* 8083 */         if (externalFileInfo != null) {
/* 8084 */           return externalFileInfo.getSize();
/*      */         }
/*      */       } 
/* 8087 */     } catch (Exception ex) {
/* 8088 */       AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*      */     } 
/* 8090 */     return -1L;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FileInfo getCurrentFileInfo() {
/* 8099 */     FileInfo fileInfo = null;
/* 8100 */     switch (this.mTabSource) {
/*      */       case 2:
/*      */       case 5:
/* 8103 */         if (this.mCurrentFile != null) {
/* 8104 */           fileInfo = new FileInfo(2, this.mCurrentFile, this.mIsEncrypted, 1);
/*      */         }
/*      */         break;
/*      */       case 6:
/*      */       case 15:
/* 8109 */         fileInfo = new FileInfo(this.mTabSource, this.mTabTag, this.mTabTitle, this.mIsEncrypted, 1);
/*      */         break;
/*      */       case 13:
/* 8112 */         if (this.mCurrentFile != null) {
/* 8113 */           fileInfo = new FileInfo(13, this.mTabTag, this.mTabTitle, this.mIsEncrypted, 1);
/*      */         }
/*      */         break;
/*      */     } 
/* 8117 */     return fileInfo;
/*      */   }
/*      */   
/*      */   protected PdfViewCtrlTabInfo getCurrentTabInfo(Activity activity) {
/* 8121 */     PdfViewCtrlTabInfo info = new PdfViewCtrlTabInfo();
/* 8122 */     info.tabTitle = this.mTabTitle;
/* 8123 */     info.tabSource = this.mTabSource;
/* 8124 */     info.fileExtension = this.mFileExtension;
/* 8125 */     if (activity != null) {
/*      */       
/* 8127 */       String mode = PdfViewCtrlSettingsManager.getViewMode((Context)activity);
/* 8128 */       info.pagePresentationMode = getPagePresentationModeFromSettings(mode).getValue();
/* 8129 */       info.isRtlMode = PdfViewCtrlSettingsManager.getInRTLMode((Context)activity);
/*      */     } 
/*      */     
/* 8132 */     return info;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSearchNavButtonsVisible(boolean visible) {
/* 8141 */     FragmentActivity fragmentActivity = getActivity();
/* 8142 */     if (fragmentActivity == null) {
/*      */       return;
/*      */     }
/*      */     
/* 8146 */     int visibility = visible ? 0 : 8;
/* 8147 */     this.mSearchOverlay.setVisibility(visibility);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void hideBackAndForwardButtons() {
/* 8154 */     if (this.mPageBackButton != null) {
/* 8155 */       this.mPageBackButton.hide();
/*      */     }
/* 8157 */     if (this.mPageForwardButton != null) {
/* 8158 */       this.mPageForwardButton.hide();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void highlightFullTextSearchResult(TextSearchResult result) {
/* 8168 */     if (this.mSearchOverlay != null) {
/* 8169 */       this.mSearchOverlay.highlightFullTextSearchResult(result);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void openFileInNewTab(File file) {
/* 8174 */     openFileInNewTab(file, this.mPassword);
/*      */   }
/*      */   
/*      */   protected void openFileUriInNewTab(Uri fileUri) {
/* 8178 */     openFileUriInNewTab(fileUri, this.mPassword);
/*      */   }
/*      */   
/*      */   protected void openFileInNewTab(File file, String password) {
/* 8182 */     if (this.mPdfViewCtrl == null) {
/*      */       return;
/*      */     }
/*      */     
/* 8186 */     if (file == null) {
/* 8187 */       handleOpeningDocumentFailed(1);
/*      */       return;
/*      */     } 
/* 8190 */     if (!file.exists()) {
/* 8191 */       handleOpeningDocumentFailed(7);
/*      */       return;
/*      */     } 
/* 8194 */     if (this.mTabListener != null) {
/* 8195 */       this.mPdfViewCtrl.closeTool();
/* 8196 */       this.mTabListener.onOpenAddNewTab(2, file.getAbsolutePath(), file.getName(), password);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void openFileUriInNewTab(Uri fileUri, String password) {
/* 8201 */     FragmentActivity fragmentActivity = getActivity();
/* 8202 */     if (fragmentActivity == null || this.mPdfViewCtrl == null) {
/*      */       return;
/*      */     }
/*      */     
/* 8206 */     if (fileUri == null) {
/* 8207 */       handleOpeningDocumentFailed(1);
/*      */       return;
/*      */     } 
/* 8210 */     if (this.mTabListener != null) {
/* 8211 */       ExternalFileInfo info = Utils.buildExternalFile((Context)fragmentActivity, fileUri);
/* 8212 */       if (info != null) {
/* 8213 */         this.mPdfViewCtrl.closeTool();
/* 8214 */         this.mTabListener.onOpenAddNewTab(6, fileUri.toString(), info.getFileName(), password);
/*      */       } 
/*      */     } 
/*      */   } protected boolean canResumeWithoutReloading() {
/*      */     boolean exists;
/*      */     ContentResolver contentResolver;
/* 8220 */     FragmentActivity fragmentActivity = getActivity();
/* 8221 */     if (fragmentActivity == null || this.mPdfDoc == null) {
/* 8222 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 8227 */     switch (this.mTabSource) {
/*      */       
/*      */       case 2:
/*      */       case 5:
/*      */       case 13:
/* 8232 */         if (null == this.mCurrentFile || !this.mCurrentFile.exists()) {
/* 8233 */           return false;
/*      */         }
/*      */ 
/*      */         
/* 8237 */         return (this.mTabSource != 2 || 
/* 8238 */           !Utils.isNotPdf(this.mTabTag) || this.mIsOfficeDocReady);
/*      */       
/*      */       case 6:
/* 8241 */         if (this.mCurrentUriFile == null) {
/* 8242 */           return false;
/*      */         }
/* 8244 */         exists = Utils.uriHasReadPermission((Context)fragmentActivity, this.mCurrentUriFile);
/* 8245 */         contentResolver = Utils.getContentResolver((Context)fragmentActivity);
/* 8246 */         return (exists && contentResolver != null && 
/* 8247 */           !Utils.isNotPdf(contentResolver, Uri.parse(this.mTabTag)));
/*      */     } 
/*      */     
/* 8250 */     return false;
/*      */   }
/*      */   
/*      */   protected void resumeFragment(boolean fromOnResume) {
/* 8254 */     FragmentActivity fragmentActivity = getActivity();
/* 8255 */     if (fragmentActivity == null || this.mPdfViewCtrl == null) {
/*      */       return;
/*      */     }
/* 8258 */     if (sDebug) {
/* 8259 */       Log.d("timing", "resumeFragment start");
/*      */     }
/* 8261 */     this.mPdfViewCtrl.resume();
/* 8262 */     this.mHasChangesSinceResumed = false;
/*      */     
/* 8264 */     resetAutoSavingTimer();
/*      */     
/* 8266 */     if (this.mCanAddToTabInfo) {
/* 8267 */       PdfViewCtrlTabInfo tabInfo = PdfViewCtrlTabsManager.getInstance().getPdfFViewCtrlTabInfo((Context)fragmentActivity, this.mTabTag);
/* 8268 */       if (tabInfo != null) {
/*      */         
/* 8270 */         PdfViewCtrlTabsManager.getInstance().updateLastViewedTabTimestamp((Context)fragmentActivity, this.mTabTag);
/*      */ 
/*      */         
/* 8273 */         addToRecentList(tabInfo);
/* 8274 */       } else if (PdfViewCtrlTabsManager.getInstance().getNewPath(this.mTabTag) == null && 
/* 8275 */         !containsInRecentList(getCurrentFileInfo())) {
/*      */         
/* 8277 */         addToRecentList(getCurrentTabInfo((Activity)fragmentActivity));
/*      */       } 
/*      */     } 
/*      */     
/* 8281 */     toggleViewerVisibility(false);
/*      */     
/* 8283 */     if (null != this.mToolManager && this.mToolManager.getTool() instanceof com.pdftron.pdf.tools.TextHighlighter) {
/* 8284 */       highlightSearchResults();
/*      */     }
/*      */     
/* 8287 */     if (!this.mDocumentLoading) {
/* 8288 */       boolean handled = true;
/* 8289 */       this.mDocumentLoading = true;
/* 8290 */       if (this.mPdfDoc != null) {
/*      */         ExternalFileInfo externalFileInfo;
/*      */         ContentResolver contentResolver;
/* 8293 */         boolean fileNotExists = false;
/* 8294 */         switch (this.mTabSource) {
/*      */           
/*      */           case 2:
/*      */           case 5:
/*      */           case 13:
/* 8299 */             if (this.mCurrentFile == null) {
/* 8300 */               fileNotExists = true; break;
/* 8301 */             }  if (!this.mCurrentFile.exists()) {
/* 8302 */               String path = PdfViewCtrlTabsManager.getInstance().getNewPath(this.mTabTag);
/* 8303 */               if (!Utils.isNullOrEmpty(path) && (new File(path)).exists()) {
/* 8304 */                 String oldTabTag = this.mTabTag;
/* 8305 */                 this.mPdfDoc = null;
/* 8306 */                 this.mTabTag = path;
/* 8307 */                 this.mTabTitle = FilenameUtils.removeExtension((new File(path)).getName());
/* 8308 */                 this.mCurrentFile = new File(this.mTabTag);
/* 8309 */                 if (this.mTabListener != null)
/* 8310 */                   this.mTabListener.onTabIdentityChanged(oldTabTag, this.mTabTag, this.mTabTitle, this.mFileExtension, this.mTabSource); 
/*      */                 break;
/*      */               } 
/* 8313 */               fileNotExists = true;
/*      */               break;
/*      */             } 
/* 8316 */             if (!this.mIsOfficeDocReady && this.mTabSource == 2 && 
/* 8317 */               Utils.isNotPdf(this.mTabTag)) {
/*      */ 
/*      */               
/* 8320 */               openOfficeDoc(this.mTabTag, false);
/*      */               break;
/*      */             } 
/* 8323 */             toggleViewerVisibility(true);
/* 8324 */             this.mDocumentLoading = false;
/*      */             break;
/*      */ 
/*      */           
/*      */           case 15:
/* 8329 */             if (!this.mIsOfficeDocReady) {
/* 8330 */               openOfficeDoc(this.mTabTag, true);
/*      */               break;
/*      */             } 
/* 8333 */             toggleViewerVisibility(true);
/* 8334 */             this.mDocumentLoading = false;
/*      */             break;
/*      */ 
/*      */           
/*      */           case 6:
/* 8339 */             if (this.mCurrentUriFile == null) {
/* 8340 */               this.mErrorCode = 7;
/* 8341 */               handleOpeningDocumentFailed(this.mErrorCode); break;
/*      */             } 
/* 8343 */             externalFileInfo = Utils.buildExternalFile(getContext(), this.mCurrentUriFile);
/*      */ 
/*      */ 
/*      */             
/* 8347 */             if (!Utils.isUriSeekable((Context)fragmentActivity, this.mCurrentUriFile) && (externalFileInfo == null || !externalFileInfo.exists())) {
/* 8348 */               String path = PdfViewCtrlTabsManager.getInstance().getNewPath(this.mTabTag);
/* 8349 */               if (path == null) {
/* 8350 */                 fileNotExists = true; break;
/*      */               } 
/* 8352 */               externalFileInfo = Utils.buildExternalFile(getContext(), Uri.parse(path));
/* 8353 */               if (externalFileInfo == null || !externalFileInfo.exists()) {
/* 8354 */                 fileNotExists = true; break;
/*      */               } 
/* 8356 */               String oldTabTag = this.mTabTag;
/* 8357 */               this.mPdfDoc = null;
/* 8358 */               this.mTabTag = path;
/* 8359 */               this.mTabTitle = FilenameUtils.removeExtension(externalFileInfo.getName());
/* 8360 */               this.mCurrentUriFile = Uri.parse(this.mTabTag);
/* 8361 */               if (this.mTabListener != null) {
/* 8362 */                 this.mTabListener.onTabIdentityChanged(oldTabTag, this.mTabTag, this.mTabTitle, this.mFileExtension, this.mTabSource);
/*      */               }
/*      */               
/*      */               break;
/*      */             } 
/* 8367 */             contentResolver = Utils.getContentResolver((Context)fragmentActivity);
/* 8368 */             if (contentResolver == null) {
/* 8369 */               handled = false;
/*      */               break;
/*      */             } 
/* 8372 */             if (Utils.isNotPdf(contentResolver, Uri.parse(this.mTabTag))) {
/* 8373 */               openOfficeDoc(this.mTabTag, true);
/*      */               break;
/*      */             } 
/* 8376 */             toggleViewerVisibility(true);
/* 8377 */             this.mDocumentLoading = false;
/*      */             break;
/*      */ 
/*      */ 
/*      */           
/*      */           default:
/* 8383 */             handled = false; break;
/*      */         } 
/* 8385 */         if (fileNotExists) {
/*      */           
/* 8387 */           this.mErrorCode = 7;
/* 8388 */           handleOpeningDocumentFailed(this.mErrorCode);
/*      */         } 
/*      */       } 
/*      */       
/* 8392 */       if (this.mPdfDoc == null) {
/*      */         ContentResolver contentResolver;
/*      */         
/* 8395 */         switch (this.mTabSource) {
/*      */           case 2:
/* 8397 */             if (Utils.isNotPdf(this.mTabTag)) {
/* 8398 */               openOfficeDoc(this.mTabTag, false); break;
/*      */             } 
/* 8400 */             openLocalFile(this.mTabTag);
/*      */             break;
/*      */           
/*      */           case 6:
/* 8404 */             contentResolver = Utils.getContentResolver((Context)fragmentActivity);
/* 8405 */             if (contentResolver == null) {
/* 8406 */               handled = false;
/*      */               break;
/*      */             } 
/* 8409 */             if (Utils.isNotPdf(contentResolver, Uri.parse(this.mTabTag))) {
/* 8410 */               openOfficeDoc(this.mTabTag, true); break;
/*      */             } 
/* 8412 */             openExternalFile(this.mTabTag);
/*      */             break;
/*      */           
/*      */           case 5:
/* 8416 */             openUrlFile(this.mTabTag);
/*      */             break;
/*      */           case 13:
/* 8419 */             openEditUriFile(this.mTabTag);
/*      */             break;
/*      */           case 15:
/* 8422 */             openOfficeDoc(this.mTabTag, true);
/*      */             break;
/*      */           default:
/* 8425 */             handled = false; break;
/*      */         } 
/*      */       } 
/* 8428 */       if (!handled) {
/* 8429 */         this.mDocumentLoading = false;
/*      */       }
/*      */     } 
/*      */     
/* 8433 */     if (this.mColorModeChanged) {
/* 8434 */       this.mColorModeChanged = false;
/* 8435 */       updateColorMode();
/*      */     } 
/*      */     
/* 8438 */     if (this.mScreenshotTempFileCreated) {
/*      */       try {
/* 8440 */         File oldFile = new File(this.mScreenshotTempFilePath);
/* 8441 */         oldFile.delete();
/* 8442 */       } catch (Exception e) {
/* 8443 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */       } finally {
/* 8445 */         this.mScreenshotTempFileCreated = false;
/* 8446 */         this.mScreenshotTempFilePath = null;
/*      */       } 
/*      */     }
/*      */     
/* 8450 */     if (sDebug)
/* 8451 */       Log.d("timing", "resumeFragment end"); 
/*      */   }
/*      */   
/*      */   protected void pauseFragment() {
/* 8455 */     FragmentActivity fragmentActivity = getActivity();
/* 8456 */     if (fragmentActivity == null || this.mPdfViewCtrl == null) {
/*      */       return;
/*      */     }
/*      */     
/* 8460 */     stopHandlers();
/*      */     
/* 8462 */     if (this.mDocumentConversion != null) {
/* 8463 */       cancelUniversalConversion();
/*      */     }
/*      */     
/* 8466 */     updateRecentList();
/* 8467 */     if (this.mViewerConfig != null)
/*      */     {
/* 8469 */       ViewerUtils.setLastPageForURL((Context)fragmentActivity, this.mOpenUrlLink, this.mPdfViewCtrl.getCurrentPage());
/*      */     }
/*      */ 
/*      */     
/* 8473 */     if (this.mPassword != null && !this.mPassword.isEmpty()) {
/* 8474 */       PdfViewCtrlTabInfo info = PdfViewCtrlTabsManager.getInstance().getPdfFViewCtrlTabInfo((Context)fragmentActivity, this.mTabTag);
/* 8475 */       if (info != null) {
/* 8476 */         info.password = Utils.encryptIt((Context)fragmentActivity, this.mPassword);
/* 8477 */         PdfViewCtrlTabsManager.getInstance().addPdfViewCtrlTabInfo((Context)fragmentActivity, this.mTabTag, info);
/*      */       } 
/*      */     } 
/*      */     
/* 8481 */     if (this.mDownloadDocumentDialog != null && this.mDownloadDocumentDialog.isShowing()) {
/* 8482 */       this.mDownloadDocumentDialog.dismiss();
/*      */     }
/*      */     
/* 8485 */     if (this.mGetTextInPageTask != null && this.mGetTextInPageTask.getStatus() != AsyncTask.Status.FINISHED) {
/* 8486 */       this.mGetTextInPageTask.cancel(true);
/* 8487 */       this.mGetTextInPageTask = null;
/*      */     } 
/*      */     
/* 8490 */     if (this.mPDFDocLoaderTask != null && this.mPDFDocLoaderTask.getStatus() != AsyncTask.Status.FINISHED) {
/* 8491 */       this.mPDFDocLoaderTask.cancel(true);
/* 8492 */       this.mPDFDocLoaderTask = null;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 8497 */     showDocumentSavedToast();
/* 8498 */     save(false, true, true, true);
/*      */     
/* 8500 */     saveCurrentPdfViewCtrlState();
/*      */     
/* 8502 */     if (this.mPdfViewCtrl != null) {
/* 8503 */       this.mPdfViewCtrl.pause();
/* 8504 */       this.mPdfViewCtrl.purgeMemory();
/*      */     } 
/*      */     
/* 8507 */     closeKeyboard();
/* 8508 */     this.mDocumentLoading = false;
/*      */     
/* 8510 */     if (this.mTabListener != null) {
/* 8511 */       this.mTabListener.onTabPaused(getCurrentFileInfo(), isDocModifiedAfterOpening());
/*      */     }
/*      */   }
/*      */   
/*      */   protected void closeKeyboard() {
/* 8516 */     FragmentActivity fragmentActivity = getActivity();
/* 8517 */     if (fragmentActivity == null) {
/*      */       return;
/*      */     }
/* 8520 */     if (this.mPasswordLayout != null && this.mPasswordLayout.getVisibility() == 0) {
/* 8521 */       Utils.hideSoftKeyboard((Context)fragmentActivity, this.mPasswordLayout);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void setViewerHostVisible(boolean visible) {
/* 8526 */     if (this.mPdfViewCtrl == null) {
/*      */       return;
/*      */     }
/* 8529 */     if (visible) {
/* 8530 */       this.mPdfViewCtrl.setVisibility(0);
/* 8531 */       if (sDebug)
/* 8532 */         Log.d(TAG, "show viewer"); 
/*      */     } else {
/* 8534 */       this.mPdfViewCtrl.setVisibility(4);
/* 8535 */       if (sDebug)
/* 8536 */         Log.d(TAG, "hide viewer"); 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void toggleViewerVisibility(boolean visible) {
/* 8541 */     if (this.mIsReflowMode) {
/*      */       return;
/*      */     }
/* 8544 */     setViewerHostVisible(visible);
/* 8545 */     if (this.mProgressBarLayout != null) {
/* 8546 */       if (visible) {
/* 8547 */         this.mProgressBarLayout.hide(false);
/* 8548 */         if (sDebug)
/* 8549 */           Log.d(TAG, "hide progress bar"); 
/*      */       } else {
/* 8551 */         this.mProgressBarLayout.show();
/* 8552 */         if (sDebug) {
/* 8553 */           Log.d(TAG, "show progress bar");
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createRetrieveChangesDialog(final String cacheFileName) {
/* 8591 */     Context context = getContext();
/* 8592 */     if (context == null) {
/*      */       return;
/*      */     }
/* 8595 */     AlertDialog.Builder builder = new AlertDialog.Builder(context);
/* 8596 */     builder.setMessage(R.string.freetext_restore_cache_message)
/* 8597 */       .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener()
/*      */         {
/*      */           public void onClick(DialogInterface dialog, int which) {
/* 8600 */             Context context = PdfViewCtrlTabFragment.this.getContext();
/* 8601 */             if (context == null) {
/*      */               return;
/*      */             }
/* 8604 */             JSONObject obj = Utils.retrieveToolCache(context, cacheFileName);
/* 8605 */             if (null == obj || PdfViewCtrlTabFragment.this.mPdfViewCtrl == null) {
/*      */               return;
/*      */             }
/*      */             
/* 8609 */             PdfViewCtrlTabFragment.this.mToolManager.setTool(PdfViewCtrlTabFragment.this.mToolManager.createTool((ToolManager.ToolModeBase)ToolManager.ToolMode.TEXT_CREATE, null));
/*      */             try {
/* 8611 */               int page = obj.getInt("pageNum");
/* 8612 */               if (PdfViewCtrlTabFragment.this.mPdfViewCtrl.getCurrentPage() != page) {
/* 8613 */                 if (PdfViewCtrlTabFragment.sDebug)
/* 8614 */                   Log.d(PdfViewCtrlTabFragment.TAG, "restoreFreeText mWaitingForSetPage: " + page); 
/* 8615 */                 PdfViewCtrlTabFragment.this.mPdfViewCtrl.setCurrentPage(page);
/* 8616 */                 PdfViewCtrlTabFragment.this.mWaitingForSetPage = true;
/* 8617 */                 PdfViewCtrlTabFragment.this.mWaitingForSetPageNum = page;
/*      */               } else {
/* 8619 */                 PdfViewCtrlTabFragment.this.restoreFreeText();
/*      */               } 
/* 8621 */             } catch (JSONException e) {
/* 8622 */               AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*      */             }
/*      */           
/*      */           }
/* 8626 */         }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
/*      */         {
/*      */           public void onClick(DialogInterface dialog, int which) {
/* 8629 */             Context context = PdfViewCtrlTabFragment.this.getContext();
/* 8630 */             if (context == null) {
/*      */               return;
/*      */             }
/* 8633 */             if (PdfViewCtrlTabFragment.sDebug)
/* 8634 */               Log.d(PdfViewCtrlTabFragment.TAG, "cancel"); 
/* 8635 */             Utils.deleteCacheFile(context, cacheFileName);
/*      */           }
/*      */         });
/*      */     
/* 8639 */     AlertDialog dialog = builder.create();
/* 8640 */     dialog.show();
/*      */   }
/*      */   
/*      */   private void restoreFreeText() {
/* 8644 */     if (this.mPdfViewCtrl == null || this.mToolManager == null) {
/*      */       return;
/*      */     }
/*      */     
/* 8648 */     this.mToolManager.setTool(this.mToolManager.createTool((ToolManager.ToolModeBase)ToolManager.ToolMode.TEXT_CREATE, null));
/* 8649 */     String freeTextCacheFilename = this.mToolManager.getFreeTextCacheFileName();
/* 8650 */     JSONObject obj = Utils.retrieveToolCache(getContext(), freeTextCacheFilename);
/* 8651 */     if (obj != null) {
/*      */       try {
/* 8653 */         JSONObject pointObj = obj.getJSONObject("targetPoint");
/* 8654 */         int x = pointObj.getInt("x");
/* 8655 */         int y = pointObj.getInt("y");
/* 8656 */         MotionEvent event = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), 0, x, y, 0);
/* 8657 */         this.mPdfViewCtrl.dispatchTouchEvent(event);
/* 8658 */         event = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), 1, x, y, 0);
/* 8659 */         this.mPdfViewCtrl.dispatchTouchEvent(event);
/* 8660 */       } catch (JSONException e) {
/* 8661 */         AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   protected void hidePageNumberIndicator() {
/* 8667 */     if (null != this.mPageNumberIndicator) {
/* 8668 */       animatePageIndicator(false);
/*      */     }
/* 8670 */     if (null != this.mPageBackButton) {
/* 8671 */       this.mPageBackButton.hide();
/*      */     }
/* 8673 */     if (null != this.mPageForwardButton) {
/* 8674 */       this.mPageForwardButton.hide();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCanAddToTabInfo(boolean enabled) {
/* 8685 */     this.mCanAddToTabInfo = enabled;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getFilePath() {
/* 8694 */     if (this.mCurrentFile != null) {
/* 8695 */       return this.mCurrentFile.getAbsolutePath();
/*      */     }
/* 8697 */     if (this.mCurrentUriFile != null) {
/* 8698 */       return this.mCurrentUriFile.getPath();
/*      */     }
/* 8700 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDocModifiedAfterOpening() {
/* 8709 */     return (Utils.isDocModified(this.mPdfDoc) || this.mDocumentState == 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSearchMode(boolean enabled) {
/* 8718 */     this.mInSearchMode = enabled;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSearchMode() {
/* 8727 */     return this.mInSearchMode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void undo() {
/* 8734 */     undo(true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void undo(boolean sendAnalytics) {
/* 8743 */     FragmentActivity fragmentActivity = getActivity();
/* 8744 */     if (fragmentActivity == null || this.mPdfViewCtrl == null || this.mToolManager == null) {
/*      */       return;
/*      */     }
/*      */     
/* 8748 */     UndoRedoManager undoRedoManager = this.mToolManager.getUndoRedoManger();
/* 8749 */     if (undoRedoManager != null && undoRedoManager.canUndo()) {
/* 8750 */       String undoInfo = undoRedoManager.undo(1, sendAnalytics);
/* 8751 */       UndoRedoManager.jumpToUndoRedo(this.mPdfViewCtrl, undoInfo, true);
/* 8752 */       refreshPageCount();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void redo() {
/* 8760 */     redo(true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void redo(boolean sendAnalytics) {
/* 8769 */     FragmentActivity fragmentActivity = getActivity();
/* 8770 */     if (fragmentActivity == null || this.mPdfViewCtrl == null || this.mToolManager == null) {
/*      */       return;
/*      */     }
/*      */     
/* 8774 */     UndoRedoManager undoRedoManager = this.mToolManager.getUndoRedoManger();
/* 8775 */     if (undoRedoManager != null && undoRedoManager.canRedo()) {
/* 8776 */       String redoInfo = undoRedoManager.redo(1, sendAnalytics);
/* 8777 */       UndoRedoManager.jumpToUndoRedo(this.mPdfViewCtrl, redoInfo, false);
/* 8778 */       refreshPageCount();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSavedAndClosedShown() {
/* 8786 */     this.mWasSavedAndClosedShown = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBookmarkDialogCurrentTab(int index) {
/* 8795 */     this.mBookmarkDialogCurrentTab = index;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getBookmarkDialogCurrentTab() {
/* 8804 */     return this.mBookmarkDialogCurrentTab;
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
/*      */   protected boolean checkTabConversionAndAlert(int messageID, boolean allowConverted, boolean skipSpecialFileCheck) {
/* 8816 */     FragmentActivity fragmentActivity = getActivity();
/* 8817 */     if (fragmentActivity == null) {
/* 8818 */       return false;
/*      */     }
/*      */     
/* 8821 */     localFileWriteAccessCheck();
/* 8822 */     if (isTabReadOnly()) {
/* 8823 */       if (canDocBeSaved() && allowConverted) {
/* 8824 */         return false;
/*      */       }
/* 8826 */       if (canDocBeSaved()) {
/* 8827 */         if (!isNotPdf() && skipSpecialFileCheck) {
/* 8828 */           return false;
/*      */         }
/* 8830 */         handleSpecialFile();
/*      */       
/*      */       }
/* 8833 */       else if (getHasWarnedAboutCanNotEditDuringConversion()) {
/* 8834 */         CommonToast.showText((Context)fragmentActivity, messageID);
/*      */       } else {
/* 8836 */         setHasWarnedAboutCanNotEditDuringConversion();
/* 8837 */         Utils.getAlertDialogNoTitleBuilder((Context)fragmentActivity, messageID)
/* 8838 */           .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener()
/*      */             {
/*      */ 
/*      */ 
/*      */               
/*      */               public void onClick(DialogInterface dialog, int which) {}
/* 8844 */             }).setCancelable(false)
/* 8845 */           .create().show();
/*      */       } 
/*      */ 
/*      */       
/* 8849 */       return true;
/*      */     } 
/*      */     
/* 8852 */     return false;
/*      */   }
/*      */   
/*      */   protected void handleRageScrolling() {
/* 8856 */     final FragmentActivity activity = getActivity();
/* 8857 */     if (fragmentActivity == null || this.mPdfViewCtrl == null) {
/*      */       return;
/*      */     }
/* 8860 */     if (!PdfViewCtrlSettingsManager.getShowRageScrollingInfo((Context)fragmentActivity)) {
/*      */       return;
/*      */     }
/* 8863 */     if (this.mViewerConfig != null) {
/*      */       return;
/*      */     }
/*      */     
/* 8867 */     if (this.mRageScrollingAsked) {
/*      */       return;
/*      */     }
/* 8870 */     this.mRageScrollingAsked = true;
/* 8871 */     LayoutInflater inflater = LayoutInflater.from((Context)fragmentActivity);
/* 8872 */     View customLayout = inflater.inflate(R.layout.alert_dialog_with_checkbox, null);
/* 8873 */     TextView dialogTextView = (TextView)customLayout.findViewById(R.id.dialog_message);
/*      */     
/* 8875 */     final CheckBox dialogCheckBox = (CheckBox)customLayout.findViewById(R.id.dialog_checkbox);
/* 8876 */     dialogCheckBox.setChecked(true);
/*      */     
/* 8878 */     int pixelSize = (int)Utils.convDp2Pix((Context)fragmentActivity, 24.0F);
/* 8879 */     String rawContent = getString(R.string.rage_scrolling_body);
/* 8880 */     SpannableString content = new SpannableString(rawContent);
/* 8881 */     Drawable drawable = getResources().getDrawable(R.drawable.ic_viewing_mode_white_24dp);
/* 8882 */     drawable.mutate().setColorFilter(getResources().getColor(R.color.gray600), PorterDuff.Mode.SRC_IN);
/* 8883 */     drawable.setBounds(0, 0, pixelSize, pixelSize);
/* 8884 */     ImageSpan span = new ImageSpan(drawable, 0);
/* 8885 */     String placeholder = "[gear]";
/* 8886 */     int start = rawContent.indexOf(placeholder);
/* 8887 */     if (start >= 0) {
/* 8888 */       content.setSpan(span, start, start + placeholder.length(), 17);
/*      */     }
/* 8890 */     dialogTextView.setText((CharSequence)content);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 8917 */     AlertDialog.Builder dialogBuilder = (new AlertDialog.Builder((Context)fragmentActivity)).setView(customLayout).setTitle(R.string.rage_scrolling_title).setPositiveButton(R.string.rage_scrolling_positive, new DialogInterface.OnClickListener() { public void onClick(DialogInterface dialog, int which) { boolean showAgain = !dialogCheckBox.isChecked(); AnalyticsHandlerAdapter.getInstance().sendEvent(63, AnalyticsParam.rageScrollingParam("switch", dialogCheckBox.isChecked())); PdfViewCtrlSettingsManager.updateShowRageScrollingInfo((Context)activity, showAgain); if (PdfViewCtrlTabFragment.this.mPdfViewCtrl != null) { PDFViewCtrl.PagePresentationMode mode = PdfViewCtrlTabFragment.this.mPdfViewCtrl.getPagePresentationMode(); if (mode == PDFViewCtrl.PagePresentationMode.SINGLE) { PdfViewCtrlTabFragment.this.updateViewMode(PDFViewCtrl.PagePresentationMode.SINGLE_CONT); } else if (mode == PDFViewCtrl.PagePresentationMode.FACING) { PdfViewCtrlTabFragment.this.updateViewMode(PDFViewCtrl.PagePresentationMode.FACING_CONT); } else if (mode == PDFViewCtrl.PagePresentationMode.FACING_COVER) { PdfViewCtrlTabFragment.this.updateViewMode(PDFViewCtrl.PagePresentationMode.FACING_COVER_CONT); }  }  } }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
/*      */         {
/*      */           public void onClick(DialogInterface dialog, int which) {
/* 8920 */             boolean showAgain = !dialogCheckBox.isChecked();
/* 8921 */             PdfViewCtrlSettingsManager.updateShowRageScrollingInfo((Context)activity, showAgain);
/*      */             
/* 8923 */             AnalyticsHandlerAdapter.getInstance().sendEvent(63, 
/*      */                 
/* 8925 */                 AnalyticsParam.rageScrollingParam("cancel", dialogCheckBox.isChecked()));
/*      */           }
/*      */         });
/* 8928 */     dialogBuilder.create().show();
/*      */   }
/*      */   
/*      */   public static void setDebug(boolean debug) {
/* 8932 */     sDebug = debug;
/*      */   }
/*      */ 
/*      */   
/*      */   private void animatePageIndicator(boolean show) {
/* 8937 */     if (show) {
/* 8938 */       this.mPageNumberIndicator.animate()
/* 8939 */         .scaleX(1.0F)
/* 8940 */         .scaleY(1.0F)
/* 8941 */         .alpha(1.0F)
/* 8942 */         .setDuration(200L)
/* 8943 */         .setInterpolator((TimeInterpolator)new DecelerateInterpolator())
/* 8944 */         .setListener(new Animator.AnimatorListener()
/*      */           {
/*      */             public void onAnimationStart(Animator animation) {
/* 8947 */               PdfViewCtrlTabFragment.this.mPageNumberIndicator.setVisibility(0);
/*      */             }
/*      */ 
/*      */ 
/*      */             
/*      */             public void onAnimationEnd(Animator animation) {}
/*      */ 
/*      */ 
/*      */             
/*      */             public void onAnimationCancel(Animator animation) {
/* 8957 */               PdfViewCtrlTabFragment.this.mPageNumberIndicator.setVisibility(0);
/*      */             }
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             public void onAnimationRepeat(Animator animation) {}
/*      */           });
/*      */     } else {
/* 8966 */       this.mPageNumberIndicator.animate()
/* 8967 */         .scaleX(0.0F)
/* 8968 */         .scaleY(0.0F)
/* 8969 */         .alpha(0.0F)
/* 8970 */         .setDuration(200L)
/* 8971 */         .setInterpolator((TimeInterpolator)new AccelerateInterpolator())
/* 8972 */         .setListener(new Animator.AnimatorListener()
/*      */           {
/*      */             public void onAnimationStart(Animator animation) {}
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             public void onAnimationEnd(Animator animation) {
/* 8980 */               PdfViewCtrlTabFragment.this.mPageNumberIndicator.setVisibility(8);
/*      */             }
/*      */ 
/*      */             
/*      */             public void onAnimationCancel(Animator animation) {
/* 8985 */               PdfViewCtrlTabFragment.this.mPageNumberIndicator.setVisibility(8);
/*      */             }
/*      */             
/*      */             public void onAnimationRepeat(Animator animation) {}
/*      */           });
/*      */     } 
/*      */   }
/*      */   
/*      */   public static interface TabListener {
/*      */     void onTabDocumentLoaded(String param1String);
/*      */     
/*      */     void onTabError(int param1Int, String param1String);
/*      */     
/*      */     void onOpenAddNewTab(int param1Int, String param1String1, String param1String2, String param1String3);
/*      */     
/*      */     void onShowTabInfo(String param1String1, String param1String2, String param1String3, int param1Int1, int param1Int2);
/*      */     
/*      */     void onInkEditSelected(Annot param1Annot, int param1Int);
/*      */     
/*      */     void onOpenAnnotationToolbar(ToolManager.ToolMode param1ToolMode);
/*      */     
/*      */     void onOpenEditToolbar(ToolManager.ToolMode param1ToolMode);
/*      */     
/*      */     void onToggleReflow();
/*      */     
/*      */     SearchResultsView.SearchResultStatus onFullTextSearchFindText(boolean param1Boolean);
/*      */     
/*      */     void onTabThumbSliderStopTrackingTouch();
/*      */     
/*      */     void onTabSingleTapConfirmed();
/*      */     
/*      */     void onSearchProgressShow();
/*      */     
/*      */     void onSearchProgressHide();
/*      */     
/*      */     void resetHideToolbarsTimer();
/*      */     
/*      */     void setToolbarsVisible(boolean param1Boolean);
/*      */     
/*      */     void setViewerOverlayUIVisible(boolean param1Boolean);
/*      */     
/*      */     int getToolbarHeight();
/*      */     
/*      */     void onDownloadedSuccessful();
/*      */     
/*      */     void onUndoRedoPopupClosed();
/*      */     
/*      */     void onTabIdentityChanged(String param1String1, String param1String2, String param1String3, String param1String4, int param1Int);
/*      */     
/*      */     void onOutlineOptionSelected();
/*      */     
/*      */     void onPageThumbnailOptionSelected(boolean param1Boolean, Integer param1Integer);
/*      */     
/*      */     boolean onBackPressed();
/*      */     
/*      */     void onTabPaused(FileInfo param1FileInfo, boolean param1Boolean);
/*      */     
/*      */     void onTabJumpToSdCardFolder();
/*      */     
/*      */     void onUpdateOptionsMenu();
/*      */   }
/*      */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\controls\PdfViewCtrlTabFragment.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */