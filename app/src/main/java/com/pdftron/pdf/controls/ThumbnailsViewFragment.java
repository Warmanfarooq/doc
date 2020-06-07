/*      */ package com.pdftron.pdf.controls;
/*      */ 
/*      */ import android.content.Context;
/*      */ import android.content.DialogInterface;
/*      */ import android.content.Intent;
/*      */ import android.content.res.Configuration;
/*      */ import android.content.res.TypedArray;
/*      */ import android.net.Uri;
/*      */ import android.os.Bundle;
/*      */ import android.os.Parcelable;
/*      */ import android.util.DisplayMetrics;
/*      */ import android.util.SparseBooleanArray;
/*      */ import android.view.KeyEvent;
/*      */ import android.view.LayoutInflater;
/*      */ import android.view.Menu;
/*      */ import android.view.MenuItem;
/*      */ import android.view.View;
/*      */ import android.view.ViewGroup;
/*      */ import android.view.ViewTreeObserver;
/*      */ import android.widget.ProgressBar;
/*      */ import androidx.annotation.NonNull;
/*      */ import androidx.annotation.Nullable;
/*      */ import androidx.appcompat.widget.Toolbar;
/*      */ import androidx.core.view.ViewCompat;
/*      */ import androidx.fragment.app.DialogFragment;
/*      */ import androidx.fragment.app.Fragment;
/*      */ import androidx.fragment.app.FragmentActivity;
/*      */ import androidx.fragment.app.FragmentManager;
/*      */ import androidx.lifecycle.Observer;
/*      */ import androidx.lifecycle.ViewModelProviders;
/*      */ import androidx.recyclerview.widget.ItemTouchHelper;
/*      */ import androidx.recyclerview.widget.RecyclerView;
/*      */ import co.paulburke.android.itemtouchhelperdemo.helper.SimpleItemTouchHelperCallback;
/*      */ import com.github.clans.fab.FloatingActionButton;
/*      */ import com.github.clans.fab.FloatingActionMenu;
/*      */ import com.pdftron.pdf.PDFViewCtrl;
/*      */ import com.pdftron.pdf.Page;
/*      */ import com.pdftron.pdf.dialog.pagelabel.PageLabelDialog;
/*      */ import com.pdftron.pdf.dialog.pagelabel.PageLabelSetting;
/*      */ import com.pdftron.pdf.dialog.pagelabel.PageLabelSettingViewModel;
/*      */ import com.pdftron.pdf.dialog.pagelabel.PageLabelUtils;
/*      */ import com.pdftron.pdf.tools.R;
/*      */ import com.pdftron.pdf.tools.ToolManager;
/*      */ import com.pdftron.pdf.tools.UndoRedoManager;
/*      */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*      */ import com.pdftron.pdf.utils.AnalyticsParam;
/*      */ import com.pdftron.pdf.utils.AnnotUtils;
/*      */ import com.pdftron.pdf.utils.BookmarkManager;
/*      */ import com.pdftron.pdf.utils.CommonToast;
/*      */ import com.pdftron.pdf.utils.Event;
/*      */ import com.pdftron.pdf.utils.PdfViewCtrlSettingsManager;
/*      */ import com.pdftron.pdf.utils.ToolbarActionMode;
/*      */ import com.pdftron.pdf.utils.Utils;
/*      */ import com.pdftron.pdf.utils.ViewerUtils;
/*      */ import com.pdftron.pdf.widget.recyclerview.ItemClickHelper;
/*      */ import com.pdftron.pdf.widget.recyclerview.ItemSelectionHelper;
/*      */ import com.pdftron.pdf.widget.recyclerview.SimpleRecyclerView;
/*      */ import com.pdftron.pdf.widget.recyclerview.ViewHolderBindListener;
/*      */ import io.reactivex.Observable;
/*      */ import io.reactivex.ObservableEmitter;
/*      */ import io.reactivex.ObservableOnSubscribe;
/*      */ import io.reactivex.android.schedulers.AndroidSchedulers;
/*      */ import io.reactivex.disposables.CompositeDisposable;
/*      */ import io.reactivex.disposables.Disposable;
/*      */ import io.reactivex.functions.Action;
/*      */ import io.reactivex.functions.Consumer;
/*      */ import io.reactivex.schedulers.Schedulers;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ThumbnailsViewFragment
/*      */   extends DialogFragment
/*      */   implements ThumbnailsViewAdapter.EditPagesListener
/*      */ {
/*      */   private static final String BUNDLE_READ_ONLY_DOC = "read_only_doc";
/*      */   private static final String BUNDLE_EDIT_MODE = "edit_mode";
/*      */   private static final String BUNDLE_OUTPUT_FILE_URI = "output_file_uri";
/*      */   public static final int FILTER_MODE_NORMAL = 0;
/*      */   public static final int FILTER_MODE_ANNOTATED = 1;
/*      */   public static final int FILTER_MODE_BOOKMARKED = 2;
/*      */   FloatingActionMenu mFabMenu;
/*      */   private Uri mOutputFileUri;
/*      */   private boolean mIsReadOnly;
/*      */   private boolean mIsReadOnlySave;
/*      */   private Integer mInitSelectedItem;
/*      */   private PDFViewCtrl mPdfViewCtrl;
/*      */   private Toolbar mToolbar;
/*      */   private Toolbar mCabToolbar;
/*      */   private SimpleRecyclerView mRecyclerView;
/*      */   private ThumbnailsViewAdapter mAdapter;
/*      */   private ProgressBar mProgressBarView;
/*      */   private ItemSelectionHelper mItemSelectionHelper;
/*      */   private ItemTouchHelper mItemTouchHelper;
/*      */   private ToolbarActionMode mActionMode;
/*      */   private MenuItem mMenuItemUndo;
/*      */   private MenuItem mMenuItemRedo;
/*      */   private MenuItem mMenuItemRotate;
/*      */   private MenuItem mMenuItemDelete;
/*      */   private MenuItem mMenuItemDuplicate;
/*      */   private MenuItem mMenuItemExport;
/*      */   private MenuItem mMenuItemPageLabel;
/*      */   private MenuItem mMenuItemEdit;
/*      */   private MenuItem mMenuItemFilter;
/*      */   private MenuItem mMenuItemFilterAll;
/*      */   private MenuItem mMenuItemFilterAnnotated;
/*      */   private MenuItem mMenuItemFilterBookmarked;
/*      */   private int mSpanCount;
/*  127 */   private String mTitle = "";
/*      */   
/*      */   private boolean mHasEventAction;
/*      */   
/*      */   boolean mAddDocPagesDelay;
/*      */   
/*      */   int mPositionDelay;
/*      */   ThumbnailsViewAdapter.DocumentFormat mDocumentFormatDelay;
/*      */   Object mDataDelay;
/*      */   private OnThumbnailsViewDialogDismissListener mOnThumbnailsViewDialogDismissListener;
/*      */   private OnThumbnailsEditAttemptWhileReadOnlyListener mOnThumbnailsEditAttemptWhileReadOnlyListener;
/*      */   private OnExportThumbnailsListener mOnExportThumbnailsListener;
/*  139 */   private final CompositeDisposable mDisposables = new CompositeDisposable();
/*      */ 
/*      */   
/*      */   private ThumbnailsViewFilterMode mFilterMode;
/*      */   
/*      */   private boolean mStartInEdit;
/*      */ 
/*      */   
/*      */   public static ThumbnailsViewFragment newInstance() {
/*  148 */     return newInstance(false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ThumbnailsViewFragment newInstance(boolean readOnly) {
/*  155 */     return newInstance(readOnly, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ThumbnailsViewFragment newInstance(boolean readOnly, boolean editMode) {
/*  162 */     ThumbnailsViewFragment fragment = new ThumbnailsViewFragment();
/*  163 */     Bundle args = new Bundle();
/*  164 */     args.putBoolean("read_only_doc", readOnly);
/*  165 */     args.putBoolean("edit_mode", editMode);
/*  166 */     fragment.setArguments(args);
/*  167 */     return fragment;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onCreate(Bundle savedInstanceState) {
/*  175 */     super.onCreate(savedInstanceState);
/*      */     
/*  177 */     if (savedInstanceState != null) {
/*  178 */       this.mOutputFileUri = (Uri)savedInstanceState.getParcelable("output_file_uri");
/*      */     }
/*      */     
/*  181 */     int defaultFilterMode = 0;
/*  182 */     Context context = getContext();
/*  183 */     if (context != null) {
/*  184 */       defaultFilterMode = PdfViewCtrlSettingsManager.getThumbListFilterMode(context, defaultFilterMode);
/*      */     }
/*      */     
/*  187 */     if (getArguments() != null && 
/*  188 */       getArguments().getBoolean("edit_mode", false)) {
/*  189 */       this.mStartInEdit = true;
/*      */       
/*  191 */       defaultFilterMode = 0;
/*      */     } 
/*      */     
/*  194 */     this
/*  195 */       .mFilterMode = (ThumbnailsViewFilterMode)ViewModelProviders.of((Fragment)this, new ThumbnailsViewFilterMode.Factory(Integer.valueOf(defaultFilterMode))).get(ThumbnailsViewFilterMode.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
/*  204 */     return inflater.inflate(R.layout.controls_fragment_thumbnails_view, container, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
/*  212 */     if (this.mPdfViewCtrl == null) {
/*      */       return;
/*      */     }
/*      */     
/*  216 */     if (Utils.isNullOrEmpty(this.mTitle)) {
/*  217 */       this.mTitle = getString(R.string.controls_thumbnails_view_description);
/*      */     }
/*      */     
/*  220 */     int viewWidth = getDisplayWidth();
/*  221 */     int thumbSize = getResources().getDimensionPixelSize(R.dimen.controls_thumbnails_view_image_width);
/*  222 */     int thumbSpacing = getResources().getDimensionPixelSize(R.dimen.controls_thumbnails_view_grid_spacing);
/*      */     
/*  224 */     this.mSpanCount = (int)Math.floor((viewWidth / (thumbSize + thumbSpacing)));
/*      */     
/*  226 */     this.mToolbar = (Toolbar)view.findViewById(R.id.controls_thumbnails_view_toolbar);
/*  227 */     this.mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
/*      */     
/*  229 */     this.mCabToolbar = (Toolbar)view.findViewById(R.id.controls_thumbnails_view_cab);
/*  230 */     this.mCabToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
/*      */     
/*  232 */     this.mToolbar.setNavigationOnClickListener(new View.OnClickListener()
/*      */         {
/*      */           public void onClick(View v) {
/*  235 */             if (!ThumbnailsViewFragment.this.onBackPressed()) {
/*  236 */               ThumbnailsViewFragment.this.dismiss();
/*      */             }
/*      */           }
/*      */         });
/*      */     
/*  241 */     if (getArguments() != null) {
/*  242 */       Bundle args = getArguments();
/*  243 */       this.mIsReadOnly = args.getBoolean("read_only_doc", false);
/*  244 */       this.mIsReadOnlySave = this.mIsReadOnly;
/*      */     } 
/*      */     
/*  247 */     this.mToolbar.inflateMenu(R.menu.controls_fragment_thumbnail_browser_toolbar);
/*  248 */     this.mMenuItemEdit = this.mToolbar.getMenu().findItem(R.id.controls_action_edit);
/*  249 */     if (this.mMenuItemEdit != null) {
/*  250 */       this.mMenuItemEdit.setVisible(!this.mIsReadOnly);
/*      */     }
/*  252 */     this.mMenuItemFilter = this.mToolbar.getMenu().findItem(R.id.action_filter);
/*  253 */     this.mMenuItemFilterAll = this.mToolbar.getMenu().findItem(R.id.menu_filter_all);
/*  254 */     this.mMenuItemFilterAnnotated = this.mToolbar.getMenu().findItem(R.id.menu_filter_annotated);
/*  255 */     this.mMenuItemFilterBookmarked = this.mToolbar.getMenu().findItem(R.id.menu_filter_bookmarked);
/*  256 */     this.mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener()
/*      */         {
/*      */           public boolean onMenuItemClick(MenuItem item) {
/*  259 */             if (item.getItemId() == R.id.controls_action_edit) {
/*      */               
/*  261 */               ThumbnailsViewFragment.this.mActionMode = new ToolbarActionMode((Context)ThumbnailsViewFragment.this.getActivity(), ThumbnailsViewFragment.this.mCabToolbar);
/*  262 */               ThumbnailsViewFragment.this.mActionMode.startActionMode(ThumbnailsViewFragment.this.mActionModeCallback);
/*  263 */               return true;
/*  264 */             }  if (item.getItemId() == R.id.action_filter) {
/*  265 */               if (ThumbnailsViewFragment.this.mMenuItemFilterAll != null && ThumbnailsViewFragment.this
/*  266 */                 .mMenuItemFilterAnnotated != null && ThumbnailsViewFragment.this
/*  267 */                 .mMenuItemFilterBookmarked != null) {
/*  268 */                 Integer mode = ThumbnailsViewFragment.this.mFilterMode.getFilterMode();
/*  269 */                 if (mode != null)
/*  270 */                   switch (mode.intValue()) {
/*      */                     case 0:
/*  272 */                       ThumbnailsViewFragment.this.mMenuItemFilterAll.setChecked(true);
/*      */                       break;
/*      */                     case 1:
/*  275 */                       ThumbnailsViewFragment.this.mMenuItemFilterAnnotated.setChecked(true);
/*      */                       break;
/*      */                     case 2:
/*  278 */                       ThumbnailsViewFragment.this.mMenuItemFilterBookmarked.setChecked(true);
/*      */                       break;
/*      */                   }  
/*      */               } 
/*      */             } else {
/*  283 */               if (item.getItemId() == R.id.menu_filter_all) {
/*  284 */                 ThumbnailsViewFragment.this.mFilterMode.publishFilterTypeChange(Integer.valueOf(0));
/*  285 */                 return true;
/*  286 */               }  if (item.getItemId() == R.id.menu_filter_annotated) {
/*  287 */                 ThumbnailsViewFragment.this.mFilterMode.publishFilterTypeChange(Integer.valueOf(1));
/*  288 */                 return true;
/*  289 */               }  if (item.getItemId() == R.id.menu_filter_bookmarked) {
/*  290 */                 ThumbnailsViewFragment.this.mFilterMode.publishFilterTypeChange(Integer.valueOf(2));
/*  291 */                 return true;
/*      */               } 
/*  293 */             }  return false;
/*      */           }
/*      */         });
/*      */     
/*  297 */     this.mToolbar.setTitle(this.mTitle);
/*      */     
/*  299 */     this.mProgressBarView = (ProgressBar)view.findViewById(R.id.progress_bar_view);
/*  300 */     this.mProgressBarView.setVisibility(8);
/*      */     
/*  302 */     this.mRecyclerView = (SimpleRecyclerView)view.findViewById(R.id.controls_thumbnails_view_recycler_view);
/*  303 */     this.mRecyclerView.initView(this.mSpanCount, getResources().getDimensionPixelSize(R.dimen.controls_thumbnails_view_grid_spacing));
/*  304 */     this.mRecyclerView.setItemViewCacheSize(this.mSpanCount * 2);
/*  305 */     if (this.mPdfViewCtrl != null && this.mPdfViewCtrl.getToolManager() != null && (
/*  306 */       (ToolManager)this.mPdfViewCtrl.getToolManager()).isNightMode()) {
/*  307 */       this.mRecyclerView.setBackgroundColor(getResources().getColor(R.color.controls_thumbnails_view_bg_dark));
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/*  312 */       this.mRecyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
/*      */           {
/*      */             public void onGlobalLayout()
/*      */             {
/*  316 */               if (ThumbnailsViewFragment.this.mRecyclerView == null) {
/*      */                 return;
/*      */               }
/*      */               try {
/*  320 */                 ThumbnailsViewFragment.this.mRecyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
/*  321 */               } catch (Exception exception) {}
/*      */               
/*  323 */               if (ThumbnailsViewFragment.this.mAdapter == null) {
/*      */                 return;
/*      */               }
/*  326 */               ThumbnailsViewFragment.this.mAdapter.updateMainViewWidth(ThumbnailsViewFragment.this.getMainViewWidth());
/*  327 */               ThumbnailsViewFragment.this.updateSpanCount(ThumbnailsViewFragment.this.mSpanCount);
/*      */             }
/*      */           });
/*  330 */     } catch (Exception e) {
/*  331 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } 
/*      */     
/*  334 */     ItemClickHelper itemClickHelper = new ItemClickHelper();
/*  335 */     itemClickHelper.attachToRecyclerView((RecyclerView)this.mRecyclerView);
/*      */     
/*  337 */     this.mItemSelectionHelper = new ItemSelectionHelper();
/*  338 */     this.mItemSelectionHelper.attachToRecyclerView((RecyclerView)this.mRecyclerView);
/*  339 */     this.mItemSelectionHelper.setChoiceMode(2);
/*      */     
/*  341 */     this.mAdapter = new ThumbnailsViewAdapter((Context)getActivity(), this, getFragmentManager(), this.mPdfViewCtrl, null, this.mSpanCount, (ViewHolderBindListener)this.mItemSelectionHelper);
/*      */     
/*  343 */     this.mAdapter.registerAdapterDataObserver(this.mItemSelectionHelper.getDataObserver());
/*  344 */     this.mAdapter.updateMainViewWidth(getMainViewWidth());
/*  345 */     this.mRecyclerView.setAdapter((RecyclerView.Adapter)this.mAdapter);
/*      */ 
/*      */     
/*  348 */     this.mFilterMode.observeFilterTypeChanges(getViewLifecycleOwner(), new Observer<Integer>()
/*      */         {
/*      */           public void onChanged(Integer mode) {
/*  351 */             if (mode != null) {
/*  352 */               ThumbnailsViewFragment.this.populateThumbList(mode.intValue());
/*  353 */               ThumbnailsViewFragment.this.updateSharedPrefs(mode.intValue());
/*  354 */               switch (mode.intValue()) {
/*      */                 case 0:
/*  356 */                   ThumbnailsViewFragment.this.mToolbar.setTitle(ThumbnailsViewFragment.this.mTitle);
/*  357 */                   ThumbnailsViewFragment.this.mIsReadOnly = ThumbnailsViewFragment.this.mIsReadOnlySave;
/*      */                   break;
/*      */                 case 1:
/*  360 */                   ThumbnailsViewFragment.this.mToolbar.setTitle(String.format("%s (%s)", new Object[] {
/*  361 */                           ThumbnailsViewFragment.access$1400(this.this$0), this.this$0
/*  362 */                           .getResources().getString(R.string.action_filter_thumbnails_annotated) }));
/*  363 */                   ThumbnailsViewFragment.this.mIsReadOnly = true;
/*      */                   break;
/*      */                 case 2:
/*  366 */                   ThumbnailsViewFragment.this.mToolbar.setTitle(String.format("%s (%s)", new Object[] {
/*  367 */                           ThumbnailsViewFragment.access$1400(this.this$0), this.this$0
/*  368 */                           .getResources().getString(R.string.action_filter_thumbnails_bookmarked) }));
/*  369 */                   ThumbnailsViewFragment.this.mIsReadOnly = true;
/*      */                   break;
/*      */               } 
/*  372 */               ThumbnailsViewFragment.this.updateReadOnlyUI();
/*      */             } 
/*      */           }
/*      */         });
/*      */     
/*  377 */     this.mItemTouchHelper = new ItemTouchHelper((ItemTouchHelper.Callback)new SimpleItemTouchHelperCallback(this.mAdapter, this.mSpanCount, false, false));
/*  378 */     this.mItemTouchHelper.attachToRecyclerView((RecyclerView)this.mRecyclerView);
/*      */     
/*  380 */     itemClickHelper.setOnItemClickListener(new ItemClickHelper.OnItemClickListener()
/*      */         {
/*      */           public void onItemClick(RecyclerView recyclerView, View v, int position, long id) {
/*  383 */             if (ThumbnailsViewFragment.this.mActionMode == null) {
/*  384 */               int page = ThumbnailsViewFragment.this.mAdapter.getItem(position).intValue();
/*  385 */               ThumbnailsViewFragment.this.mAdapter.setCurrentPage(page);
/*  386 */               ThumbnailsViewFragment.this.mHasEventAction = true;
/*  387 */               AnalyticsHandlerAdapter.getInstance().sendEvent(30, 
/*  388 */                   AnalyticsParam.viewerNavigateByParam(4));
/*  389 */               ThumbnailsViewFragment.this.dismiss();
/*      */             } else {
/*  391 */               ThumbnailsViewFragment.this.mItemSelectionHelper.setItemChecked(position, !ThumbnailsViewFragment.this.mItemSelectionHelper.isItemChecked(position));
/*  392 */               ThumbnailsViewFragment.this.mActionMode.invalidate();
/*      */             } 
/*      */           }
/*      */         });
/*      */     
/*  397 */     itemClickHelper.setOnItemLongClickListener(new ItemClickHelper.OnItemLongClickListener()
/*      */         {
/*      */           public boolean onItemLongClick(RecyclerView recyclerView, View v, final int position, long id) {
/*  400 */             if (ThumbnailsViewFragment.this.mIsReadOnly) {
/*  401 */               return true;
/*      */             }
/*  403 */             if (ThumbnailsViewFragment.this.mActionMode == null) {
/*  404 */               ThumbnailsViewFragment.this.mItemSelectionHelper.setItemChecked(position, true);
/*      */               
/*  406 */               ThumbnailsViewFragment.this.mActionMode = new ToolbarActionMode((Context)ThumbnailsViewFragment.this.getActivity(), ThumbnailsViewFragment.this.mCabToolbar);
/*  407 */               ThumbnailsViewFragment.this.mActionMode.startActionMode(ThumbnailsViewFragment.this.mActionModeCallback);
/*      */             } else {
/*  409 */               if (ThumbnailsViewFragment.this.mIsReadOnly) {
/*  410 */                 if (ThumbnailsViewFragment.this.mOnThumbnailsEditAttemptWhileReadOnlyListener != null)
/*  411 */                   ThumbnailsViewFragment.this.mOnThumbnailsEditAttemptWhileReadOnlyListener.onThumbnailsEditAttemptWhileReadOnly(); 
/*  412 */                 return true;
/*      */               } 
/*  414 */               ThumbnailsViewFragment.this.mRecyclerView.post(new Runnable()
/*      */                   {
/*      */                     public void run() {
/*  417 */                       RecyclerView.ViewHolder holder = ThumbnailsViewFragment.this.mRecyclerView.findViewHolderForAdapterPosition(position);
/*  418 */                       ThumbnailsViewFragment.this.mItemTouchHelper.startDrag(holder);
/*      */                     }
/*      */                   });
/*      */             } 
/*      */             
/*  423 */             return true;
/*      */           }
/*      */         });
/*      */     
/*  427 */     getDialog().setOnKeyListener(new DialogInterface.OnKeyListener()
/*      */         {
/*      */           public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
/*  430 */             if (event.getKeyCode() == 4 && event.getAction() == 1) {
/*      */               
/*  432 */               if (ThumbnailsViewFragment.this.onBackPressed())
/*      */               {
/*  434 */                 return true;
/*      */               }
/*  436 */               dialog.dismiss();
/*      */             } 
/*      */             
/*  439 */             return false;
/*      */           }
/*      */         });
/*      */     
/*  443 */     this.mFabMenu = (FloatingActionMenu)view.findViewById(R.id.fab_menu);
/*  444 */     this.mFabMenu.setClosedOnTouchOutside(true);
/*  445 */     if (this.mIsReadOnly) {
/*  446 */       this.mFabMenu.setVisibility(8);
/*      */     }
/*      */     
/*  449 */     FloatingActionButton pagePdfButton = (FloatingActionButton)this.mFabMenu.findViewById(R.id.page_PDF);
/*  450 */     pagePdfButton.setOnClickListener(new View.OnClickListener()
/*      */         {
/*      */           public void onClick(View v) {
/*  453 */             ThumbnailsViewFragment.this.mFabMenu.close(true);
/*  454 */             if (ThumbnailsViewFragment.this.mIsReadOnly) {
/*  455 */               if (ThumbnailsViewFragment.this.mOnThumbnailsEditAttemptWhileReadOnlyListener != null)
/*  456 */                 ThumbnailsViewFragment.this.mOnThumbnailsEditAttemptWhileReadOnlyListener.onThumbnailsEditAttemptWhileReadOnly(); 
/*      */               return;
/*      */             } 
/*  459 */             boolean shouldUnlockRead = false;
/*      */             try {
/*  461 */               ThumbnailsViewFragment.this.mPdfViewCtrl.docLockRead();
/*  462 */               shouldUnlockRead = true;
/*  463 */               Page lastPage = ThumbnailsViewFragment.this.mPdfViewCtrl.getDoc().getPage(ThumbnailsViewFragment.this.mPdfViewCtrl.getDoc().getPageCount());
/*      */ 
/*      */               
/*  466 */               AddPageDialogFragment addPageDialogFragment = AddPageDialogFragment.newInstance(lastPage.getPageWidth(), lastPage.getPageHeight()).setInitialPageSize(AddPageDialogFragment.PageSize.Custom);
/*  467 */               addPageDialogFragment.setOnAddNewPagesListener(new AddPageDialogFragment.OnAddNewPagesListener()
/*      */                   {
/*      */                     public void onAddNewPages(Page[] pages) {
/*  470 */                       if (pages == null || pages.length == 0) {
/*      */                         return;
/*      */                       }
/*      */                       
/*  474 */                       ThumbnailsViewFragment.this.mAdapter.addDocPages(ThumbnailsViewFragment.this.getLastSelectedPage(), ThumbnailsViewAdapter.DocumentFormat.PDF_PAGE, pages);
/*  475 */                       ThumbnailsViewFragment.this.mHasEventAction = true;
/*  476 */                       AnalyticsHandlerAdapter.getInstance().sendEvent(29, 
/*  477 */                           AnalyticsParam.thumbnailsViewCountParam(5, pages.length));
/*      */                     }
/*      */                   });
/*  480 */               addPageDialogFragment.show(ThumbnailsViewFragment.this.getActivity().getSupportFragmentManager(), "add_page_dialog");
/*  481 */             } catch (Exception e) {
/*  482 */               AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */             } finally {
/*  484 */               if (shouldUnlockRead) {
/*  485 */                 ThumbnailsViewFragment.this.mPdfViewCtrl.docUnlockRead();
/*      */               }
/*      */             } 
/*      */           }
/*      */         });
/*      */     
/*  491 */     FloatingActionButton pdfDocButton = (FloatingActionButton)this.mFabMenu.findViewById(R.id.PDF_doc);
/*  492 */     pdfDocButton.setOnClickListener(new View.OnClickListener()
/*      */         {
/*      */           public void onClick(View v) {
/*  495 */             ThumbnailsViewFragment.this.mFabMenu.close(true);
/*  496 */             if (ThumbnailsViewFragment.this.mIsReadOnly) {
/*  497 */               if (ThumbnailsViewFragment.this.mOnThumbnailsEditAttemptWhileReadOnlyListener != null)
/*  498 */                 ThumbnailsViewFragment.this.mOnThumbnailsEditAttemptWhileReadOnlyListener.onThumbnailsEditAttemptWhileReadOnly(); 
/*      */               return;
/*      */             } 
/*  501 */             ThumbnailsViewFragment.this.launchAndroidFilePicker();
/*      */           }
/*      */         });
/*      */     
/*  505 */     FloatingActionButton imagePdfButton = (FloatingActionButton)this.mFabMenu.findViewById(R.id.image_PDF);
/*  506 */     imagePdfButton.setOnClickListener(new View.OnClickListener()
/*      */         {
/*      */           public void onClick(View v) {
/*  509 */             ThumbnailsViewFragment.this.mFabMenu.close(true);
/*  510 */             if (ThumbnailsViewFragment.this.mIsReadOnly) {
/*  511 */               if (ThumbnailsViewFragment.this.mOnThumbnailsEditAttemptWhileReadOnlyListener != null) {
/*  512 */                 ThumbnailsViewFragment.this.mOnThumbnailsEditAttemptWhileReadOnlyListener.onThumbnailsEditAttemptWhileReadOnly();
/*      */               }
/*      */               return;
/*      */             } 
/*  516 */             ThumbnailsViewFragment.this.mOutputFileUri = ViewerUtils.openImageIntent((Fragment)ThumbnailsViewFragment.this);
/*      */           }
/*      */         });
/*      */     
/*  520 */     FragmentActivity activity = getActivity();
/*  521 */     if (activity != null) {
/*      */       
/*  523 */       PageLabelSettingViewModel mPageLabelViewModel = (PageLabelSettingViewModel)ViewModelProviders.of(activity).get(PageLabelSettingViewModel.class);
/*  524 */       mPageLabelViewModel.observeOnComplete(getViewLifecycleOwner(), new Observer<Event<PageLabelSetting>>()
/*      */           {
/*      */             public void onChanged(@Nullable Event<PageLabelSetting> pageLabelSettingEvent) {
/*  527 */               if (pageLabelSettingEvent != null && !pageLabelSettingEvent.hasBeenHandled()) {
/*  528 */                 boolean isSuccessful = PageLabelUtils.setPageLabel(ThumbnailsViewFragment.this.mPdfViewCtrl, (PageLabelSetting)pageLabelSettingEvent
/*  529 */                     .getContentIfNotHandled());
/*      */                 
/*  531 */                 if (isSuccessful) {
/*  532 */                   ThumbnailsViewFragment.this.mHasEventAction = true;
/*      */                   
/*  534 */                   ThumbnailsViewFragment.this.mAdapter.updateAfterPageLabelEdit();
/*      */                   
/*  536 */                   ThumbnailsViewFragment.this.managePageLabelChanged();
/*  537 */                   CommonToast.showText(ThumbnailsViewFragment.this.getContext(), ThumbnailsViewFragment.this.getString(R.string.page_label_success), 1);
/*      */                 } else {
/*  539 */                   CommonToast.showText(ThumbnailsViewFragment.this.getContext(), ThumbnailsViewFragment.this.getString(R.string.page_label_failed), 1);
/*      */                 } 
/*      */               } 
/*      */             }
/*      */           });
/*      */     } 
/*      */     
/*  546 */     loadAttributes();
/*      */   }
/*      */   
/*      */   private void loadAttributes() {
/*  550 */     Context context = getContext();
/*  551 */     if (null == context) {
/*      */       return;
/*      */     }
/*  554 */     TypedArray a = context.obtainStyledAttributes(null, R.styleable.ThumbnailBrowser, R.attr.thumbnail_browser, R.style.ThumbnailBrowserStyle);
/*      */     
/*      */     try {
/*  557 */       boolean showFilterMenuItem = a.getBoolean(R.styleable.ThumbnailBrowser_showFilterMenuItem, true);
/*  558 */       boolean showAnnotatedMenuItem = a.getBoolean(R.styleable.ThumbnailBrowser_showFilterAnnotated, true);
/*  559 */       boolean showBookmarkedMenuItem = a.getBoolean(R.styleable.ThumbnailBrowser_showFilterBookmarked, true);
/*      */       
/*  561 */       if (!showAnnotatedMenuItem && !showBookmarkedMenuItem)
/*      */       {
/*  563 */         showFilterMenuItem = false;
/*      */       }
/*  565 */       if (this.mMenuItemFilter != null) {
/*  566 */         this.mMenuItemFilter.setVisible(showFilterMenuItem);
/*      */       }
/*  568 */       if (this.mMenuItemFilterAnnotated != null) {
/*  569 */         this.mMenuItemFilterAnnotated.setVisible(showAnnotatedMenuItem);
/*      */       }
/*  571 */       if (this.mMenuItemFilterBookmarked != null) {
/*  572 */         this.mMenuItemFilterBookmarked.setVisible(showBookmarkedMenuItem);
/*      */       }
/*      */     } finally {
/*  575 */       a.recycle();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void populateThumbList(final int mode) {
/*  580 */     this.mDisposables.add(
/*  581 */         getPages(this.mPdfViewCtrl, mode)
/*  582 */         .subscribeOn(Schedulers.io())
/*  583 */         .observeOn(AndroidSchedulers.mainThread())
/*  584 */         .doOnSubscribe(new Consumer<Disposable>()
/*      */           {
/*      */             public void accept(Disposable disposable) throws Exception {
/*  587 */               ThumbnailsViewFragment.this.mAdapter.clear();
/*  588 */               ThumbnailsViewFragment.this.mAdapter.notifyDataSetChanged();
/*  589 */               ThumbnailsViewFragment.this.mProgressBarView.setVisibility(0);
/*  590 */               ThumbnailsViewFragment.this.mRecyclerView.setVisibility(8);
/*      */             }
/*  593 */           }).subscribe(new Consumer<List<Integer>>()
/*      */           {
/*      */             public void accept(List<Integer> integers) throws Exception {
/*  596 */               ThumbnailsViewFragment.this.mAdapter.addAll(integers);
/*      */             }
/*      */           },  new Consumer<Throwable>()
/*      */           {
/*      */             public void accept(Throwable throwable) throws Exception
/*      */             {
/*  602 */               ThumbnailsViewFragment.this.mProgressBarView.setVisibility(8);
/*  603 */               CommonToast.showText((Context)ThumbnailsViewFragment.this.getActivity(), R.string.error_generic_message, 0);
/*      */               
/*  605 */               AnalyticsHandlerAdapter.getInstance().sendException(new RuntimeException(throwable));
/*      */             }
/*      */           }new Action()
/*      */           {
/*      */             public void run() throws Exception
/*      */             {
/*  611 */               ThumbnailsViewFragment.this.mProgressBarView.setVisibility(8);
/*  612 */               ThumbnailsViewFragment.this.mRecyclerView.setVisibility(0);
/*      */ 
/*      */               
/*  615 */               if (ThumbnailsViewFragment.this.mRecyclerView != null && ThumbnailsViewFragment.this.mAdapter != null && ThumbnailsViewFragment.this.mPdfViewCtrl != null) {
/*  616 */                 int pos = ThumbnailsViewFragment.this.mAdapter.getPositionForPage(ThumbnailsViewFragment.this.mPdfViewCtrl.getCurrentPage());
/*  617 */                 if (pos >= 0 && pos < ThumbnailsViewFragment.this.mAdapter.getItemCount()) {
/*  618 */                   ThumbnailsViewFragment.this.mRecyclerView.scrollToPosition(pos);
/*      */                 }
/*      */               } 
/*  621 */               if (ThumbnailsViewFragment.this.mStartInEdit) {
/*      */                 
/*  623 */                 ThumbnailsViewFragment.this.mStartInEdit = false;
/*  624 */                 if (mode == 0) {
/*      */                   
/*  626 */                   ThumbnailsViewFragment.this.mActionMode = new ToolbarActionMode((Context)ThumbnailsViewFragment.this.getActivity(), ThumbnailsViewFragment.this.mCabToolbar);
/*  627 */                   ThumbnailsViewFragment.this.mActionMode.startActionMode(ThumbnailsViewFragment.this.mActionModeCallback);
/*  628 */                   if (ThumbnailsViewFragment.this.mInitSelectedItem != null) {
/*  629 */                     ThumbnailsViewFragment.this.mItemSelectionHelper.setItemChecked(ThumbnailsViewFragment.this.mInitSelectedItem.intValue(), true);
/*  630 */                     ThumbnailsViewFragment.this.mActionMode.invalidate();
/*  631 */                     ThumbnailsViewFragment.this.mInitSelectedItem = null;
/*      */                   } 
/*      */                 } 
/*      */               } 
/*      */             }
/*      */           }));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static Observable<List<Integer>> getPages(final PDFViewCtrl pdfViewCtrl, final int mode) {
/*  642 */     return Observable.create(new ObservableOnSubscribe<List<Integer>>()
/*      */         {
/*      */           public void subscribe(ObservableEmitter<List<Integer>> emitter) throws Exception {
/*  645 */             if (pdfViewCtrl == null) {
/*  646 */               emitter.onComplete();
/*      */               return;
/*      */             } 
/*  649 */             boolean shouldUnlockRead = false;
/*      */             try {
/*  651 */               pdfViewCtrl.docLockRead();
/*  652 */               shouldUnlockRead = true;
/*      */               
/*  654 */               ArrayList<Integer> excludeList = new ArrayList<>();
/*  655 */               excludeList.add(Integer.valueOf(1));
/*  656 */               excludeList.add(Integer.valueOf(19));
/*      */               
/*  658 */               ArrayList<Integer> bookmarkedPages = new ArrayList<>();
/*  659 */               if (mode == 2) {
/*      */                 try {
/*  661 */                   bookmarkedPages = BookmarkManager.getPdfBookmarkedPageNumbers(pdfViewCtrl.getDoc());
/*  662 */                 } catch (Exception exception) {}
/*      */               }
/*      */ 
/*      */               
/*  666 */               int pageCount = pdfViewCtrl.getDoc().getPageCount();
/*  667 */               for (int pageNum = 1; pageNum <= pageCount; pageNum++) {
/*  668 */                 boolean canAdd = true;
/*  669 */                 if (mode == 1) {
/*  670 */                   int annotCount = AnnotUtils.getAnnotationCountOnPage(pdfViewCtrl, pageNum, excludeList);
/*  671 */                   canAdd = (annotCount > 0);
/*      */                 } 
/*  673 */                 if (mode == 2) {
/*  674 */                   canAdd = bookmarkedPages.contains(Integer.valueOf(pageNum));
/*      */                 }
/*  676 */                 if (canAdd) {
/*  677 */                   ArrayList<Integer> pages = new ArrayList<>();
/*  678 */                   pages.add(Integer.valueOf(pageNum));
/*  679 */                   emitter.onNext(pages);
/*      */                 } 
/*      */               } 
/*  682 */             } catch (Exception ex) {
/*  683 */               emitter.onError(ex);
/*      */             } finally {
/*  685 */               if (shouldUnlockRead) {
/*  686 */                 pdfViewCtrl.docUnlockRead();
/*      */               }
/*  688 */               emitter.onComplete();
/*      */             } 
/*      */           }
/*      */         });
/*      */   }
/*      */   
/*      */   private void updateSharedPrefs(int filterMode) {
/*  695 */     Context context = getContext();
/*  696 */     if (context != null) {
/*  697 */       PdfViewCtrlSettingsManager.updateThumbListFilterMode(context, filterMode);
/*      */     }
/*      */   }
/*      */   
/*      */   private void updateReadOnlyUI() {
/*  702 */     finishActionMode();
/*  703 */     if (this.mMenuItemEdit != null) {
/*  704 */       this.mMenuItemEdit.setVisible(!this.mIsReadOnly);
/*      */     }
/*  706 */     if (this.mFabMenu != null) {
/*  707 */       this.mFabMenu.setVisibility(this.mIsReadOnly ? 8 : 0);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onResume() {
/*  716 */     super.onResume();
/*      */     
/*  718 */     if (this.mPdfViewCtrl != null && this.mPdfViewCtrl.getToolManager() != null && (
/*  719 */       (ToolManager)this.mPdfViewCtrl.getToolManager()).canResumePdfDocWithoutReloading()) {
/*  720 */       addDocPages();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void onStart() {
/*  727 */     super.onStart();
/*  728 */     AnalyticsHandlerAdapter.getInstance().sendTimedEvent(27);
/*      */   }
/*      */ 
/*      */   
/*      */   public void onStop() {
/*  733 */     super.onStop();
/*  734 */     AnalyticsHandlerAdapter.getInstance().endTimedEvent(27);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addDocPages() {
/*  741 */     if (this.mAddDocPagesDelay && this.mDataDelay != null) {
/*  742 */       this.mAddDocPagesDelay = false;
/*  743 */       this.mAdapter.addDocPages(this.mPositionDelay, this.mDocumentFormatDelay, this.mDataDelay);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onSaveInstanceState(Bundle outState) {
/*  752 */     super.onSaveInstanceState(outState);
/*      */     
/*  754 */     if (this.mOutputFileUri != null) {
/*  755 */       outState.putParcelable("output_file_uri", (Parcelable)this.mOutputFileUri);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onActivityResult(int requestCode, int resultCode, Intent data) {
/*  764 */     super.onActivityResult(requestCode, resultCode, data);
/*      */     
/*  766 */     FragmentActivity fragmentActivity = getActivity();
/*  767 */     if (fragmentActivity == null) {
/*      */       return;
/*      */     }
/*      */     
/*  771 */     if (resultCode != -1) {
/*      */       return;
/*      */     }
/*      */     
/*  775 */     if (requestCode == 10004 || requestCode == 10003) {
/*      */       
/*  777 */       this.mPositionDelay = getLastSelectedPage();
/*  778 */       if (requestCode == 10004) {
/*  779 */         this.mDocumentFormatDelay = ThumbnailsViewAdapter.DocumentFormat.PDF_DOC;
/*  780 */         if (data == null || data.getData() == null) {
/*      */           return;
/*      */         }
/*  783 */         this.mDataDelay = data.getData();
/*      */       } else {
/*  785 */         this.mDocumentFormatDelay = ThumbnailsViewAdapter.DocumentFormat.IMAGE;
/*      */         try {
/*  787 */           Map imageIntent = ViewerUtils.readImageIntent(data, (Context)fragmentActivity, this.mOutputFileUri);
/*  788 */           if (!ViewerUtils.checkImageIntent(imageIntent)) {
/*  789 */             Utils.handlePdfFromImageFailed((Context)fragmentActivity, imageIntent);
/*      */             return;
/*      */           } 
/*  792 */           this.mDataDelay = ViewerUtils.getImageUri(imageIntent);
/*  793 */           AnalyticsHandlerAdapter.getInstance().sendEvent(29, 
/*  794 */               AnalyticsParam.thumbnailsViewParam(ViewerUtils.isImageFromCamera(imageIntent) ? 8 : 7));
/*      */         }
/*  796 */         catch (FileNotFoundException fileNotFoundException) {}
/*      */       } 
/*      */ 
/*      */       
/*  800 */       if (this.mDataDelay != null) {
/*  801 */         this.mAddDocPagesDelay = true;
/*  802 */         this.mHasEventAction = true;
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
/*      */   public ThumbnailsViewFragment setPdfViewCtrl(@NonNull PDFViewCtrl pdfViewCtrl) {
/*  814 */     this.mPdfViewCtrl = pdfViewCtrl;
/*  815 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOnThumbnailsViewDialogDismissListener(OnThumbnailsViewDialogDismissListener listener) {
/*  824 */     this.mOnThumbnailsViewDialogDismissListener = listener;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOnThumbnailsEditAttemptWhileReadOnlyListener(OnThumbnailsEditAttemptWhileReadOnlyListener listener) {
/*  833 */     this.mOnThumbnailsEditAttemptWhileReadOnlyListener = listener;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOnExportThumbnailsListener(OnExportThumbnailsListener listener) {
/*  842 */     this.mOnExportThumbnailsListener = listener;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setItemChecked(int position) {
/*  851 */     this.mInitSelectedItem = Integer.valueOf(position);
/*      */   }
/*      */   
/*      */   private void launchAndroidFilePicker() {
/*  855 */     Intent intent = new Intent("android.intent.action.GET_CONTENT");
/*      */     
/*  857 */     intent.setType("*/*");
/*      */     
/*  859 */     intent.addCategory("android.intent.category.OPENABLE");
/*      */     
/*  861 */     intent.putExtra("android.content.extra.SHOW_ADVANCED", true);
/*      */     
/*  863 */     intent.putExtra("android.provider.extra.SHOW_ADVANCED", true);
/*  864 */     startActivityForResult(intent, 10004);
/*      */   }
/*      */   
/*      */   private int getLastSelectedPage() {
/*  868 */     int lastSelectedPage = -1;
/*  869 */     if (this.mItemSelectionHelper.getCheckedItemCount() > 0) {
/*  870 */       lastSelectedPage = Integer.MIN_VALUE;
/*  871 */       SparseBooleanArray selectedItems = this.mItemSelectionHelper.getCheckedItemPositions();
/*  872 */       for (int i = 0; i < selectedItems.size(); i++) {
/*  873 */         if (selectedItems.valueAt(i)) {
/*  874 */           int position = selectedItems.keyAt(i);
/*  875 */           Integer itemMap = this.mAdapter.getItem(position);
/*  876 */           if (itemMap != null) {
/*  877 */             int pageNum = itemMap.intValue();
/*  878 */             if (pageNum > lastSelectedPage) {
/*  879 */               lastSelectedPage = pageNum;
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  888 */     return lastSelectedPage;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTitle(String title) {
/*  897 */     this.mTitle = title;
/*  898 */     if (this.mToolbar != null) {
/*  899 */       this.mToolbar.setTitle(title);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onConfigurationChanged(Configuration newConfig) {
/*  907 */     super.onConfigurationChanged(newConfig);
/*  908 */     if (this.mAdapter != null) {
/*  909 */       int viewWidth = getDisplayWidth();
/*  910 */       int thumbSize = getResources().getDimensionPixelSize(R.dimen.controls_thumbnails_view_image_width);
/*  911 */       int thumbSpacing = getResources().getDimensionPixelSize(R.dimen.controls_thumbnails_view_grid_spacing);
/*      */       
/*  913 */       this.mSpanCount = (int)Math.floor((viewWidth / (thumbSize + thumbSpacing)));
/*      */       
/*  915 */       this.mAdapter.updateMainViewWidth(viewWidth);
/*  916 */       updateSpanCount(this.mSpanCount);
/*      */     } 
/*      */     
/*  919 */     if (this.mActionMode != null) {
/*  920 */       this.mActionMode.invalidate();
/*      */     }
/*      */   }
/*      */   
/*      */   private int getMainViewWidth() {
/*  925 */     if (this.mRecyclerView != null && ViewCompat.isLaidOut((View)this.mRecyclerView)) {
/*  926 */       return this.mRecyclerView.getMeasuredWidth();
/*      */     }
/*  928 */     return getDisplayWidth();
/*      */   }
/*      */ 
/*      */   
/*      */   private int getDisplayWidth() {
/*  933 */     DisplayMetrics metrics = getResources().getDisplayMetrics();
/*  934 */     return metrics.widthPixels;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateSpanCount(int count) {
/*  943 */     this.mSpanCount = count;
/*  944 */     this.mRecyclerView.updateSpanCount(count);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ThumbnailsViewAdapter getAdapter() {
/*  951 */     return this.mAdapter;
/*      */   }
/*      */   
/*      */   private boolean finishActionMode() {
/*  955 */     boolean success = false;
/*  956 */     if (this.mActionMode != null) {
/*  957 */       success = true;
/*  958 */       this.mActionMode.finish();
/*  959 */       this.mActionMode = null;
/*      */     } 
/*  961 */     clearSelectedList();
/*  962 */     return success;
/*      */   }
/*      */   
/*      */   private void clearSelectedList() {
/*  966 */     if (this.mItemSelectionHelper != null) {
/*  967 */       this.mItemSelectionHelper.clearChoices();
/*      */     }
/*  969 */     if (this.mActionMode != null) {
/*  970 */       this.mActionMode.invalidate();
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean onBackPressed() {
/*  975 */     if (!isAdded()) {
/*  976 */       return false;
/*      */     }
/*      */     
/*  979 */     boolean handled = false;
/*  980 */     if (this.mActionMode != null) {
/*  981 */       handled = finishActionMode();
/*      */     }
/*  983 */     return handled;
/*      */   }
/*      */ 
/*      */   
/*      */   public void onDestroyView() {
/*  988 */     super.onDestroyView();
/*  989 */     this.mDisposables.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDismiss(DialogInterface dialog) {
/*  997 */     super.onDismiss(dialog);
/*      */     
/*  999 */     if (this.mPdfViewCtrl == null || this.mPdfViewCtrl.getDoc() == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 1004 */     AnalyticsHandlerAdapter.getInstance().sendEvent(28, 
/* 1005 */         AnalyticsParam.noActionParam(this.mHasEventAction));
/*      */     
/*      */     try {
/* 1008 */       if (this.mAdapter.getDocPagesModified())
/*      */       {
/* 1010 */         this.mPdfViewCtrl.updatePageLayout();
/*      */       }
/*      */       
/* 1013 */       this.mPdfViewCtrl.setCurrentPage(this.mAdapter.getCurrentPage());
/* 1014 */     } catch (Exception e) {
/* 1015 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } 
/*      */     
/* 1018 */     this.mAdapter.clearResources();
/* 1019 */     this.mAdapter.finish();
/*      */     
/*      */     try {
/* 1022 */       this.mPdfViewCtrl.cancelAllThumbRequests();
/* 1023 */     } catch (Exception e) {
/* 1024 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } 
/*      */     
/* 1027 */     if (this.mOnThumbnailsViewDialogDismissListener != null) {
/* 1028 */       this.mOnThumbnailsViewDialogDismissListener.onThumbnailsViewDialogDismiss(this.mAdapter.getCurrentPage(), this.mAdapter.getDocPagesModified());
/*      */     }
/*      */   }
/*      */   
/* 1032 */   private ToolbarActionMode.Callback mActionModeCallback = new ToolbarActionMode.Callback()
/*      */     {
/*      */       public boolean onCreateActionMode(ToolbarActionMode mode, Menu menu) {
/* 1035 */         mode.inflateMenu(R.menu.cab_controls_fragment_thumbnails_view);
/*      */         
/* 1037 */         ThumbnailsViewFragment.this.mMenuItemUndo = menu.findItem(R.id.controls_thumbnails_view_action_undo);
/* 1038 */         ThumbnailsViewFragment.this.mMenuItemRedo = menu.findItem(R.id.controls_thumbnails_view_action_redo);
/* 1039 */         ThumbnailsViewFragment.this.mMenuItemRotate = menu.findItem(R.id.controls_thumbnails_view_action_rotate);
/* 1040 */         ThumbnailsViewFragment.this.mMenuItemDelete = menu.findItem(R.id.controls_thumbnails_view_action_delete);
/* 1041 */         ThumbnailsViewFragment.this.mMenuItemDuplicate = menu.findItem(R.id.controls_thumbnails_view_action_duplicate);
/* 1042 */         ThumbnailsViewFragment.this.mMenuItemExport = menu.findItem(R.id.controls_thumbnails_view_action_export);
/* 1043 */         ThumbnailsViewFragment.this.mMenuItemPageLabel = menu.findItem(R.id.controls_thumbnails_view_action_page_label);
/*      */         
/* 1045 */         return true;
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean onPrepareActionMode(ToolbarActionMode mode, Menu menu) {
/* 1050 */         boolean isEnabled = (ThumbnailsViewFragment.this.mItemSelectionHelper.getCheckedItemCount() > 0);
/*      */         
/* 1052 */         if (ThumbnailsViewFragment.this.mMenuItemRotate != null) {
/* 1053 */           ThumbnailsViewFragment.this.mMenuItemRotate.setEnabled(isEnabled);
/* 1054 */           if (ThumbnailsViewFragment.this.mMenuItemRotate.getIcon() != null) {
/* 1055 */             ThumbnailsViewFragment.this.mMenuItemRotate.getIcon().setAlpha(isEnabled ? 255 : 150);
/*      */           }
/*      */         } 
/* 1058 */         if (ThumbnailsViewFragment.this.mMenuItemDelete != null) {
/* 1059 */           ThumbnailsViewFragment.this.mMenuItemDelete.setEnabled(isEnabled);
/* 1060 */           if (ThumbnailsViewFragment.this.mMenuItemDelete.getIcon() != null) {
/* 1061 */             ThumbnailsViewFragment.this.mMenuItemDelete.getIcon().setAlpha(isEnabled ? 255 : 150);
/*      */           }
/*      */         } 
/* 1064 */         if (ThumbnailsViewFragment.this.mMenuItemDuplicate != null) {
/* 1065 */           ThumbnailsViewFragment.this.mMenuItemDuplicate.setEnabled(isEnabled);
/* 1066 */           if (ThumbnailsViewFragment.this.mMenuItemDuplicate.getIcon() != null) {
/* 1067 */             ThumbnailsViewFragment.this.mMenuItemDuplicate.getIcon().setAlpha(isEnabled ? 255 : 150);
/*      */           }
/*      */         } 
/* 1070 */         if (ThumbnailsViewFragment.this.mMenuItemExport != null) {
/* 1071 */           ThumbnailsViewFragment.this.mMenuItemExport.setEnabled(isEnabled);
/* 1072 */           if (ThumbnailsViewFragment.this.mMenuItemExport.getIcon() != null) {
/* 1073 */             ThumbnailsViewFragment.this.mMenuItemExport.getIcon().setAlpha(isEnabled ? 255 : 150);
/*      */           }
/* 1075 */           ThumbnailsViewFragment.this.mMenuItemExport.setVisible((ThumbnailsViewFragment.this.mOnExportThumbnailsListener != null));
/*      */         } 
/* 1077 */         if (ThumbnailsViewFragment.this.mMenuItemPageLabel != null) {
/* 1078 */           ThumbnailsViewFragment.this.mMenuItemPageLabel.setEnabled(isEnabled);
/* 1079 */           if (ThumbnailsViewFragment.this.mMenuItemPageLabel.getIcon() != null) {
/* 1080 */             ThumbnailsViewFragment.this.mMenuItemPageLabel.getIcon().setAlpha(isEnabled ? 255 : 150);
/*      */           }
/*      */         } 
/*      */         
/* 1084 */         if (Utils.isTablet(ThumbnailsViewFragment.this.getContext()) || (ThumbnailsViewFragment.this.getResources().getConfiguration()).orientation == 2) {
/* 1085 */           mode.setTitle(ThumbnailsViewFragment.this.getString(R.string.controls_thumbnails_view_selected, new Object[] {
/* 1086 */                   Utils.getLocaleDigits(Integer.toString(ThumbnailsViewFragment.access$2000(this.this$0).getCheckedItemCount())) }));
/*      */         } else {
/* 1088 */           mode.setTitle(Utils.getLocaleDigits(Integer.toString(ThumbnailsViewFragment.this.mItemSelectionHelper.getCheckedItemCount())));
/*      */         } 
/* 1090 */         ThumbnailsViewFragment.this.updateUndoRedoIcons();
/* 1091 */         return true;
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean onActionItemClicked(ToolbarActionMode mode, MenuItem item) {
/* 1096 */         if (ThumbnailsViewFragment.this.mPdfViewCtrl == null) {
/* 1097 */           throw new NullPointerException("setPdfViewCtrl() must be called with a valid PDFViewCtrl");
/*      */         }
/*      */         
/* 1100 */         if (item.getItemId() == R.id.controls_thumbnails_view_action_rotate) {
/* 1101 */           if (ThumbnailsViewFragment.this.mIsReadOnly) {
/* 1102 */             if (ThumbnailsViewFragment.this.mOnThumbnailsEditAttemptWhileReadOnlyListener != null)
/* 1103 */               ThumbnailsViewFragment.this.mOnThumbnailsEditAttemptWhileReadOnlyListener.onThumbnailsEditAttemptWhileReadOnly(); 
/* 1104 */             return true;
/*      */           } 
/*      */           
/* 1107 */           SparseBooleanArray selectedItems = ThumbnailsViewFragment.this.mItemSelectionHelper.getCheckedItemPositions();
/* 1108 */           List<Integer> pageList = new ArrayList<>();
/* 1109 */           for (int i = 0; i < selectedItems.size(); i++) {
/* 1110 */             if (selectedItems.valueAt(i)) {
/* 1111 */               int position = selectedItems.keyAt(i);
/* 1112 */               ThumbnailsViewFragment.this.mAdapter.rotateDocPage(position + 1);
/* 1113 */               pageList.add(Integer.valueOf(position + 1));
/*      */             } 
/*      */           } 
/* 1116 */           ThumbnailsViewFragment.this.manageRotatePages(pageList);
/* 1117 */           ThumbnailsViewFragment.this.mHasEventAction = true;
/* 1118 */           AnalyticsHandlerAdapter.getInstance().sendEvent(29, 
/* 1119 */               AnalyticsParam.thumbnailsViewCountParam(2, selectedItems.size()));
/* 1120 */         } else if (item.getItemId() == R.id.controls_thumbnails_view_action_delete) {
/* 1121 */           int pageCount; if (ThumbnailsViewFragment.this.mIsReadOnly) {
/* 1122 */             if (ThumbnailsViewFragment.this.mOnThumbnailsEditAttemptWhileReadOnlyListener != null)
/* 1123 */               ThumbnailsViewFragment.this.mOnThumbnailsEditAttemptWhileReadOnlyListener.onThumbnailsEditAttemptWhileReadOnly(); 
/* 1124 */             return true;
/*      */           } 
/*      */           
/* 1127 */           List<Integer> pageList = new ArrayList<>();
/* 1128 */           SparseBooleanArray selectedItems = ThumbnailsViewFragment.this.mItemSelectionHelper.getCheckedItemPositions();
/*      */ 
/*      */           
/* 1131 */           boolean shouldUnlockRead = false;
/*      */           try {
/* 1133 */             ThumbnailsViewFragment.this.mPdfViewCtrl.docLockRead();
/* 1134 */             shouldUnlockRead = true;
/* 1135 */             pageCount = ThumbnailsViewFragment.this.mPdfViewCtrl.getDoc().getPageCount();
/* 1136 */           } catch (Exception ex) {
/* 1137 */             AnalyticsHandlerAdapter.getInstance().sendException(ex);
/* 1138 */             return true;
/*      */           } finally {
/* 1140 */             if (shouldUnlockRead) {
/* 1141 */               ThumbnailsViewFragment.this.mPdfViewCtrl.docUnlockRead();
/*      */             }
/*      */           } 
/*      */           
/* 1145 */           if (selectedItems.size() >= pageCount) {
/* 1146 */             CommonToast.showText(ThumbnailsViewFragment.this.getContext(), R.string.controls_thumbnails_view_delete_msg_all_pages);
/* 1147 */             ThumbnailsViewFragment.this.clearSelectedList();
/* 1148 */             return true;
/*      */           } 
/*      */           
/* 1151 */           for (int i = 0; i < selectedItems.size(); i++) {
/* 1152 */             if (selectedItems.valueAt(i)) {
/* 1153 */               pageList.add(Integer.valueOf(selectedItems.keyAt(i) + 1));
/*      */             }
/*      */           } 
/*      */           
/* 1157 */           Collections.sort(pageList, Collections.reverseOrder());
/* 1158 */           int count = pageList.size();
/* 1159 */           for (int j = 0; j < count; j++) {
/* 1160 */             ThumbnailsViewFragment.this.mAdapter.removeDocPage(((Integer)pageList.get(j)).intValue());
/*      */           }
/* 1162 */           ThumbnailsViewFragment.this.clearSelectedList();
/* 1163 */           ThumbnailsViewFragment.this.manageDeletePages(pageList);
/* 1164 */           ThumbnailsViewFragment.this.mHasEventAction = true;
/* 1165 */           AnalyticsHandlerAdapter.getInstance().sendEvent(29, 
/* 1166 */               AnalyticsParam.thumbnailsViewCountParam(3, selectedItems.size()));
/* 1167 */         } else if (item.getItemId() == R.id.controls_thumbnails_view_action_duplicate) {
/* 1168 */           if (ThumbnailsViewFragment.this.mAdapter != null) {
/* 1169 */             List<Integer> pageList = new ArrayList<>();
/* 1170 */             SparseBooleanArray selectedItems = ThumbnailsViewFragment.this.mItemSelectionHelper.getCheckedItemPositions();
/* 1171 */             for (int i = 0; i < selectedItems.size(); i++) {
/* 1172 */               if (selectedItems.valueAt(i)) {
/* 1173 */                 pageList.add(Integer.valueOf(selectedItems.keyAt(i) + 1));
/*      */               }
/*      */             } 
/* 1176 */             ThumbnailsViewFragment.this.mAdapter.duplicateDocPages(pageList);
/* 1177 */             ThumbnailsViewFragment.this.mHasEventAction = true;
/* 1178 */             AnalyticsHandlerAdapter.getInstance().sendEvent(29, 
/* 1179 */                 AnalyticsParam.thumbnailsViewCountParam(1, selectedItems.size()));
/*      */           } 
/* 1181 */         } else if (item.getItemId() == R.id.controls_thumbnails_view_action_export) {
/* 1182 */           if (ThumbnailsViewFragment.this.mOnExportThumbnailsListener != null) {
/* 1183 */             SparseBooleanArray selectedItems = ThumbnailsViewFragment.this.mItemSelectionHelper.getCheckedItemPositions();
/* 1184 */             ThumbnailsViewFragment.this.mOnExportThumbnailsListener.onExportThumbnails(selectedItems);
/* 1185 */             ThumbnailsViewFragment.this.mHasEventAction = true;
/* 1186 */             AnalyticsHandlerAdapter.getInstance().sendEvent(29, 
/* 1187 */                 AnalyticsParam.thumbnailsViewCountParam(4, selectedItems.size()));
/*      */           } 
/* 1189 */         } else if (item.getItemId() == R.id.controls_thumbnails_view_action_page_label) {
/* 1190 */           int pageCount; if (ThumbnailsViewFragment.this.mAdapter == null) {
/* 1191 */             return true;
/*      */           }
/*      */           
/* 1194 */           SparseBooleanArray selectedItems = ThumbnailsViewFragment.this.mItemSelectionHelper.getCheckedItemPositions();
/* 1195 */           int fromPage = Integer.MAX_VALUE;
/* 1196 */           int toPage = -1;
/* 1197 */           for (int i = 0; i < selectedItems.size(); i++) {
/* 1198 */             if (selectedItems.valueAt(i)) {
/* 1199 */               int page = selectedItems.keyAt(i) + 1;
/* 1200 */               pageCount = (page > toPage) ? page : toPage;
/* 1201 */               fromPage = (page < fromPage) ? page : fromPage;
/*      */             } 
/*      */           } 
/*      */           
/* 1205 */           int numPages = ThumbnailsViewFragment.this.mPdfViewCtrl.getPageCount();
/*      */           
/* 1207 */           if (fromPage < 1 || pageCount < 1 || pageCount < fromPage || fromPage > numPages) {
/* 1208 */             CommonToast.showText(ThumbnailsViewFragment.this.getContext(), ThumbnailsViewFragment.this.getString(R.string.page_label_failed), 1);
/* 1209 */             return true;
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1215 */           FragmentActivity activity = ThumbnailsViewFragment.this.getActivity();
/* 1216 */           FragmentManager fragManager = ThumbnailsViewFragment.this.getFragmentManager();
/* 1217 */           if (fragManager != null && activity != null) {
/* 1218 */             String prefix = PageLabelUtils.getPageLabelPrefix(ThumbnailsViewFragment.this.mPdfViewCtrl, fromPage);
/* 1219 */             PageLabelDialog dialog = PageLabelDialog.newInstance(fromPage, pageCount, numPages, prefix);
/* 1220 */             dialog.setStyle(1, R.style.CustomAppTheme);
/* 1221 */             dialog.show(fragManager, PageLabelDialog.TAG);
/*      */           } 
/* 1223 */         } else if (item.getItemId() == R.id.controls_thumbnails_view_action_undo) {
/* 1224 */           ToolManager toolManager = (ToolManager)ThumbnailsViewFragment.this.mPdfViewCtrl.getToolManager();
/* 1225 */           if (toolManager != null && toolManager.getUndoRedoManger() != null) {
/* 1226 */             String undoInfo = toolManager.getUndoRedoManger().undo(3, true);
/* 1227 */             ThumbnailsViewFragment.this.updateUndoRedoIcons();
/* 1228 */             if (!Utils.isNullOrEmpty(undoInfo)) {
/*      */               try {
/* 1230 */                 if (UndoRedoManager.isDeletePagesAction(ThumbnailsViewFragment.this.getContext(), undoInfo)) {
/* 1231 */                   List<Integer> pageList = UndoRedoManager.getPageList(undoInfo);
/* 1232 */                   if (pageList.size() != 0) {
/* 1233 */                     ThumbnailsViewFragment.this.mAdapter.updateAfterAddition(pageList);
/*      */                   }
/* 1235 */                 } else if (UndoRedoManager.isAddPagesAction(ThumbnailsViewFragment.this.getContext(), undoInfo)) {
/* 1236 */                   List<Integer> pageList = UndoRedoManager.getPageList(undoInfo);
/* 1237 */                   if (pageList.size() != 0) {
/* 1238 */                     ThumbnailsViewFragment.this.mAdapter.updateAfterDeletion(pageList);
/*      */                   }
/* 1240 */                 } else if (UndoRedoManager.isRotatePagesAction(ThumbnailsViewFragment.this.getContext(), undoInfo)) {
/* 1241 */                   List<Integer> pageList = UndoRedoManager.getPageList(undoInfo);
/* 1242 */                   if (pageList.size() != 0) {
/* 1243 */                     ThumbnailsViewFragment.this.mAdapter.updateAfterRotation(pageList);
/*      */                   }
/* 1245 */                 } else if (UndoRedoManager.isMovePageAction(ThumbnailsViewFragment.this.getContext(), undoInfo)) {
/* 1246 */                   ThumbnailsViewFragment.this.mAdapter.updateAfterMove(UndoRedoManager.getPageTo(undoInfo), 
/* 1247 */                       UndoRedoManager.getPageFrom(undoInfo));
/* 1248 */                 } else if (UndoRedoManager.isEditPageLabelsAction(ThumbnailsViewFragment.this.getContext(), undoInfo)) {
/* 1249 */                   ThumbnailsViewFragment.this.mAdapter.updateAfterPageLabelEdit();
/*      */                 } 
/* 1251 */               } catch (Exception e) {
/* 1252 */                 AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */               } 
/*      */             }
/*      */           } 
/* 1256 */         } else if (item.getItemId() == R.id.controls_thumbnails_view_action_redo) {
/* 1257 */           ToolManager toolManager = (ToolManager)ThumbnailsViewFragment.this.mPdfViewCtrl.getToolManager();
/* 1258 */           if (toolManager != null && toolManager.getUndoRedoManger() != null) {
/* 1259 */             String redoInfo = toolManager.getUndoRedoManger().redo(3, true);
/* 1260 */             ThumbnailsViewFragment.this.updateUndoRedoIcons();
/* 1261 */             if (!Utils.isNullOrEmpty(redoInfo)) {
/*      */               try {
/* 1263 */                 if (UndoRedoManager.isDeletePagesAction(ThumbnailsViewFragment.this.getContext(), redoInfo)) {
/* 1264 */                   List<Integer> pageList = UndoRedoManager.getPageList(redoInfo);
/* 1265 */                   if (pageList.size() != 0) {
/* 1266 */                     ThumbnailsViewFragment.this.mAdapter.updateAfterDeletion(pageList);
/*      */                   }
/* 1268 */                 } else if (UndoRedoManager.isAddPagesAction(ThumbnailsViewFragment.this.getContext(), redoInfo)) {
/* 1269 */                   List<Integer> pageList = UndoRedoManager.getPageList(redoInfo);
/* 1270 */                   if (pageList.size() != 0) {
/* 1271 */                     ThumbnailsViewFragment.this.mAdapter.updateAfterAddition(pageList);
/*      */                   }
/* 1273 */                 } else if (UndoRedoManager.isRotatePagesAction(ThumbnailsViewFragment.this.getContext(), redoInfo)) {
/* 1274 */                   List<Integer> pageList = UndoRedoManager.getPageList(redoInfo);
/* 1275 */                   if (pageList.size() != 0) {
/* 1276 */                     ThumbnailsViewFragment.this.mAdapter.updateAfterRotation(pageList);
/*      */                   }
/* 1278 */                 } else if (UndoRedoManager.isMovePageAction(ThumbnailsViewFragment.this.getContext(), redoInfo)) {
/* 1279 */                   ThumbnailsViewFragment.this.mAdapter.updateAfterMove(UndoRedoManager.getPageFrom(redoInfo), 
/* 1280 */                       UndoRedoManager.getPageTo(redoInfo));
/* 1281 */                 } else if (UndoRedoManager.isEditPageLabelsAction(ThumbnailsViewFragment.this.getContext(), redoInfo)) {
/* 1282 */                   ThumbnailsViewFragment.this.mAdapter.updateAfterPageLabelEdit();
/*      */                 } 
/* 1284 */               } catch (Exception e) {
/* 1285 */                 AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */               } 
/*      */             }
/*      */           } 
/*      */         } 
/*      */         
/* 1291 */         return true;
/*      */       }
/*      */ 
/*      */       
/*      */       public void onDestroyActionMode(ToolbarActionMode mode) {
/* 1296 */         ThumbnailsViewFragment.this.mActionMode = null;
/* 1297 */         ThumbnailsViewFragment.this.clearSelectedList();
/*      */       }
/*      */     };
/*      */   
/*      */   private void manageAddPages(List<Integer> pageList) {
/* 1302 */     if (this.mPdfViewCtrl == null) {
/* 1303 */       throw new NullPointerException("setPdfViewCtrl() must be called with a valid PDFViewCtrl");
/*      */     }
/*      */     
/* 1306 */     ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 1307 */     if (toolManager != null) {
/* 1308 */       toolManager.raisePagesAdded(pageList);
/*      */     }
/*      */     
/* 1311 */     updateUndoRedoIcons();
/*      */   }
/*      */   
/*      */   private void manageDeletePages(List<Integer> pageList) {
/* 1315 */     if (this.mPdfViewCtrl == null) {
/* 1316 */       throw new NullPointerException("setPdfViewCtrl() must be called with a valid PDFViewCtrl");
/*      */     }
/*      */     
/* 1319 */     ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 1320 */     if (toolManager != null) {
/* 1321 */       toolManager.raisePagesDeleted(pageList);
/*      */     }
/*      */     
/* 1324 */     updateUndoRedoIcons();
/*      */   }
/*      */   
/*      */   private void manageRotatePages(List<Integer> pageList) {
/* 1328 */     if (this.mPdfViewCtrl == null) {
/* 1329 */       throw new NullPointerException("setPdfViewCtrl() must be called with a valid PDFViewCtrl");
/*      */     }
/*      */     
/* 1332 */     ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 1333 */     if (toolManager != null) {
/* 1334 */       toolManager.raisePagesRotated(pageList);
/*      */     }
/*      */     
/* 1337 */     updateUndoRedoIcons();
/*      */   }
/*      */   
/*      */   private void manageMovePage(int fromPageNum, int toPageNum) {
/* 1341 */     if (this.mPdfViewCtrl == null) {
/* 1342 */       throw new NullPointerException("setPdfViewCtrl() must be called with a valid PDFViewCtrl");
/*      */     }
/*      */     
/* 1345 */     ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 1346 */     if (toolManager != null) {
/* 1347 */       toolManager.raisePageMoved(fromPageNum, toPageNum);
/*      */     }
/*      */     
/* 1350 */     updateUndoRedoIcons();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void managePageLabelChanged() {
/* 1358 */     if (this.mPdfViewCtrl == null) {
/* 1359 */       throw new NullPointerException("setPdfViewCtrl() must be called with a valid PDFViewCtrl");
/*      */     }
/*      */     
/* 1362 */     ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 1363 */     if (toolManager != null) {
/* 1364 */       toolManager.raisePageLabelChangedEvent();
/*      */     }
/*      */     
/* 1367 */     updateUndoRedoIcons();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onPagesAdded(List<Integer> pageList) {
/* 1375 */     manageAddPages(pageList);
/* 1376 */     if (this.mDocumentFormatDelay != null) {
/* 1377 */       if (this.mDocumentFormatDelay == ThumbnailsViewAdapter.DocumentFormat.PDF_DOC) {
/* 1378 */         AnalyticsHandlerAdapter.getInstance().sendEvent(29, 
/* 1379 */             AnalyticsParam.thumbnailsViewCountParam(6, pageList.size()));
/*      */       }
/* 1381 */       this.mDocumentFormatDelay = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onPageMoved(int fromPageNum, int toPageNum) {
/* 1390 */     manageMovePage(fromPageNum, toPageNum);
/*      */     
/* 1392 */     AnalyticsHandlerAdapter.getInstance().sendEvent(29, AnalyticsParam.thumbnailsViewParam(9));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateUndoRedoIcons() {
/* 1399 */     if (this.mPdfViewCtrl == null) {
/* 1400 */       throw new NullPointerException("setPdfViewCtrl() must be called with a valid PDFViewCtrl");
/*      */     }
/*      */     
/* 1403 */     if (this.mMenuItemUndo != null && this.mMenuItemRedo != null) {
/* 1404 */       boolean undoEnabled = false;
/* 1405 */       boolean redoEnabled = false;
/*      */       
/* 1407 */       ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 1408 */       if (toolManager != null) {
/* 1409 */         UndoRedoManager undoRedoManager = toolManager.getUndoRedoManger();
/* 1410 */         if (undoRedoManager != null) {
/* 1411 */           undoEnabled = undoRedoManager.isNextUndoEditPageAction();
/* 1412 */           redoEnabled = undoRedoManager.isNextRedoEditPageAction();
/*      */         } 
/*      */       } 
/*      */       
/* 1416 */       this.mMenuItemUndo.setEnabled(undoEnabled);
/* 1417 */       if (this.mMenuItemUndo.getIcon() != null) {
/* 1418 */         this.mMenuItemUndo.getIcon().setAlpha(undoEnabled ? 255 : 150);
/*      */       }
/* 1420 */       this.mMenuItemRedo.setEnabled(redoEnabled);
/* 1421 */       if (this.mMenuItemRedo.getIcon() != null)
/* 1422 */         this.mMenuItemRedo.getIcon().setAlpha(redoEnabled ? 255 : 150); 
/*      */     } 
/*      */   }
/*      */   
/*      */   public static interface OnExportThumbnailsListener {
/*      */     void onExportThumbnails(SparseBooleanArray param1SparseBooleanArray);
/*      */   }
/*      */   
/*      */   public static interface OnThumbnailsEditAttemptWhileReadOnlyListener {
/*      */     void onThumbnailsEditAttemptWhileReadOnly();
/*      */   }
/*      */   
/*      */   public static interface OnThumbnailsViewDialogDismissListener {
/*      */     void onThumbnailsViewDialogDismiss(int param1Int, boolean param1Boolean);
/*      */   }
/*      */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\controls\ThumbnailsViewFragment.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */