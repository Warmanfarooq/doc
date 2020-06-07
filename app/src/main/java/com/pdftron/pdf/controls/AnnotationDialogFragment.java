/*      */ package com.pdftron.pdf.controls;
/*      */ 
/*      */ import android.content.Context;
/*      */ import android.os.Bundle;
/*      */ import android.view.ContextMenu;
/*      */ import android.view.LayoutInflater;
/*      */ import android.view.Menu;
/*      */ import android.view.MenuItem;
/*      */ import android.view.View;
/*      */ import android.view.ViewGroup;
/*      */ import android.widget.ImageView;
/*      */ import android.widget.ProgressBar;
/*      */ import android.widget.TextView;
/*      */ import androidx.annotation.NonNull;
/*      */ import androidx.annotation.Nullable;
/*      */ import androidx.fragment.app.Fragment;
/*      */ import androidx.lifecycle.Observer;
/*      */ import androidx.lifecycle.ViewModelProvider;
/*      */ import androidx.lifecycle.ViewModelProviders;
/*      */ import androidx.recyclerview.widget.LinearLayoutManager;
/*      */ import androidx.recyclerview.widget.RecyclerView;
/*      */ import com.github.clans.fab.FloatingActionButton;
/*      */ import com.pdftron.common.PDFNetException;
/*      */ import com.pdftron.pdf.Annot;
/*      */ import com.pdftron.pdf.PDFDoc;
/*      */ import com.pdftron.pdf.PDFViewCtrl;
/*      */ import com.pdftron.pdf.Page;
/*      */ import com.pdftron.pdf.Print;
/*      */ import com.pdftron.pdf.TextExtractor;
/*      */ import com.pdftron.pdf.dialog.annotlist.AnnotationListSortOrder;
/*      */ import com.pdftron.pdf.dialog.annotlist.AnnotationListSorter;
/*      */ import com.pdftron.pdf.dialog.annotlist.AnnotationListUtil;
/*      */ import com.pdftron.pdf.dialog.annotlist.BaseAnnotationListSorter;
/*      */ import com.pdftron.pdf.dialog.annotlist.BaseAnnotationSortOrder;
/*      */ import com.pdftron.pdf.tools.R;
/*      */ import com.pdftron.pdf.tools.ToolManager;
/*      */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*      */ import com.pdftron.pdf.utils.AnalyticsParam;
/*      */ import com.pdftron.pdf.utils.AnnotUtils;
/*      */ import com.pdftron.pdf.utils.CommonToast;
/*      */ import com.pdftron.pdf.utils.PdfViewCtrlSettingsManager;
/*      */ import com.pdftron.pdf.utils.Utils;
/*      */ import com.pdftron.pdf.utils.ViewerUtils;
/*      */ import com.pdftron.pdf.widget.recyclerview.ItemClickHelper;
/*      */ import io.reactivex.Observable;
/*      */ import io.reactivex.Single;
/*      */ import io.reactivex.android.schedulers.AndroidSchedulers;
/*      */ import io.reactivex.disposables.CompositeDisposable;
/*      */ import io.reactivex.disposables.Disposable;
/*      */ import io.reactivex.functions.Action;
/*      */ import io.reactivex.functions.Consumer;
/*      */ import io.reactivex.functions.Function;
/*      */ import io.reactivex.schedulers.Schedulers;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.concurrent.Callable;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class AnnotationDialogFragment
/*      */   extends NavigationListDialogFragment
/*      */ {
/*      */   public static final String BUNDLE_IS_READ_ONLY = "is_read_only";
/*      */   public static final String BUNDLE_IS_RTL = "is_right-to-left";
/*      */   public static final String BUNDLE_KEY_SORT_MODE = "sort_mode_as_int";
/*      */   private static final int CONTEXT_MENU_DELETE_ITEM = 0;
/*      */   private static final int CONTEXT_MENU_DELETE_ITEM_ON_PAGE = 1;
/*      */   private static final int CONTEXT_MENU_DELETE_ALL = 2;
/*      */   protected boolean mIsReadOnly;
/*      */   protected boolean mIsRtl;
/*      */   protected BaseAnnotationSortOrder mAnnotationListSortOrder;
/*      */   private ArrayList<AnnotationInfo> mAnnotation;
/*      */   private AnnotationsAdapter mAnnotationsAdapter;
/*      */   private RecyclerView mRecyclerView;
/*      */   private TextView mEmptyTextView;
/*      */   protected PDFViewCtrl mPdfViewCtrl;
/*      */   private FloatingActionButton mFab;
/*      */   protected AnnotationDialogListener mAnnotationDialogListener;
/*      */   private ProgressBar mProgressBarView;
/*      */   protected BaseAnnotationListSorter mSorter;
/*      */   private Observable<List<AnnotationInfo>> mAnnotListObservable;
/*  103 */   private final CompositeDisposable mDisposables = new CompositeDisposable();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected BaseAnnotationSortOrder mSortOrder;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static AnnotationDialogFragment newInstance() {
/*  133 */     return new AnnotationDialogFragment();
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
/*      */   public static AnnotationDialogFragment newInstance(boolean isReadOnly, boolean isRtl, @NonNull AnnotationListSortOrder annotationListSortOrder) {
/*  145 */     Bundle args = newBundle(isReadOnly, isRtl, annotationListSortOrder);
/*      */     
/*  147 */     AnnotationDialogFragment fragment = new AnnotationDialogFragment();
/*  148 */     fragment.setArguments(args);
/*  149 */     return fragment;
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
/*      */   public static Bundle newBundle(boolean isReadOnly, boolean isRtl, @NonNull AnnotationListSortOrder annotationListSortOrder) {
/*  161 */     Bundle args = new Bundle();
/*  162 */     args.putBoolean("is_read_only", isReadOnly);
/*  163 */     args.putBoolean("is_right-to-left", isRtl);
/*  164 */     args.putInt("sort_mode_as_int", annotationListSortOrder.value);
/*  165 */     return args;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AnnotationDialogFragment setPdfViewCtrl(@NonNull PDFViewCtrl pdfViewCtrl) {
/*  175 */     this.mPdfViewCtrl = pdfViewCtrl;
/*  176 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AnnotationDialogFragment setReadOnly(boolean isReadOnly) {
/*  187 */     Bundle args = getArguments();
/*  188 */     if (args == null) {
/*  189 */       args = new Bundle();
/*      */     }
/*  191 */     args.putBoolean("is_read_only", isReadOnly);
/*  192 */     setArguments(args);
/*      */     
/*  194 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AnnotationDialogFragment setRtlMode(boolean isRtl) {
/*  205 */     Bundle args = getArguments();
/*  206 */     if (args == null) {
/*  207 */       args = new Bundle();
/*      */     }
/*  209 */     args.putBoolean("is_right-to-left", isRtl);
/*  210 */     setArguments(args);
/*      */     
/*  212 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAnnotationDialogListener(AnnotationDialogListener listener) {
/*  221 */     this.mAnnotationDialogListener = listener;
/*      */   }
/*      */   
/*  224 */   private Observer<BaseAnnotationSortOrder> mSortOrderObserver = new Observer<BaseAnnotationSortOrder>()
/*      */     {
/*      */       private void updateSharedPrefs(AnnotationListSortOrder sortOrder)
/*      */       {
/*  228 */         Context context = AnnotationDialogFragment.this.getContext();
/*  229 */         if (context != null) {
/*  230 */           PdfViewCtrlSettingsManager.updateAnnotListSortOrder(context, (BaseAnnotationSortOrder)sortOrder);
/*      */         }
/*      */       }
/*      */ 
/*      */       
/*      */       public void onChanged(@Nullable BaseAnnotationSortOrder sortOrder) {
/*  236 */         if (sortOrder instanceof AnnotationListSortOrder)
/*  237 */           switch ((AnnotationListSortOrder)sortOrder) {
/*      */             case DATE_ASCENDING:
/*  239 */               updateSharedPrefs(AnnotationListSortOrder.DATE_ASCENDING);
/*  240 */               AnnotationDialogFragment.this.mSortOrder = (BaseAnnotationSortOrder)AnnotationListSortOrder.DATE_ASCENDING;
/*      */               break;
/*      */             case POSITION_ASCENDING:
/*  243 */               updateSharedPrefs(AnnotationListSortOrder.POSITION_ASCENDING);
/*  244 */               AnnotationDialogFragment.this.mSortOrder = (BaseAnnotationSortOrder)AnnotationListSortOrder.POSITION_ASCENDING;
/*      */               break;
/*      */           }  
/*      */       }
/*      */     };
/*      */   
/*      */   public void prepareOptionsMenu(Menu menu) {
/*  251 */     if (null == menu) {
/*      */       return;
/*      */     }
/*  254 */     MenuItem sortByDateItem = menu.findItem(R.id.menu_annotlist_sort_by_date);
/*  255 */     MenuItem sortByPosItem = menu.findItem(R.id.menu_annotlist_sort_by_position);
/*  256 */     if (sortByDateItem == null || sortByPosItem == null) {
/*      */       return;
/*      */     }
/*  259 */     if (this.mSortOrder instanceof AnnotationListSortOrder) {
/*  260 */       switch ((AnnotationListSortOrder)this.mSortOrder) {
/*      */         case DATE_ASCENDING:
/*  262 */           sortByDateItem.setChecked(true);
/*      */           break;
/*      */         case POSITION_ASCENDING:
/*  265 */           sortByPosItem.setChecked(true);
/*      */           break;
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean onOptionsItemSelected(MenuItem item) {
/*  273 */     int id = item.getItemId();
/*  274 */     if (id == R.id.menu_annotlist_sort_by_date) {
/*  275 */       this.mSorter.publishSortOrderChange((BaseAnnotationSortOrder)AnnotationListSortOrder.DATE_ASCENDING);
/*  276 */     } else if (id == R.id.menu_annotlist_sort_by_position) {
/*  277 */       this.mSorter.publishSortOrderChange((BaseAnnotationSortOrder)AnnotationListSortOrder.POSITION_ASCENDING);
/*      */     } else {
/*  279 */       return super.onOptionsItemSelected(item);
/*      */     } 
/*  281 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public void onCreate(@Nullable Bundle savedInstanceState) {
/*  286 */     super.onCreate(savedInstanceState);
/*      */     
/*  288 */     Bundle args = getArguments();
/*  289 */     if (args != null) {
/*  290 */       this.mIsReadOnly = args.getBoolean("is_read_only");
/*  291 */       this.mIsRtl = args.getBoolean("is_right-to-left");
/*      */     } 
/*  293 */     this.mAnnotationListSortOrder = getSortOrder(args);
/*  294 */     this.mAnnotation = new ArrayList<>();
/*  295 */     this.mAnnotListObservable = AnnotationListUtil.from(this.mPdfViewCtrl);
/*  296 */     this.mSorter = getSorter();
/*      */   }
/*      */   
/*      */   @NonNull
/*      */   protected BaseAnnotationSortOrder getSortOrder(@Nullable Bundle args) {
/*  301 */     return (args != null && args.containsKey("sort_mode_as_int")) ? 
/*  302 */       (BaseAnnotationSortOrder)AnnotationListSortOrder.fromValue(args
/*  303 */         .getInt("sort_mode_as_int", AnnotationListSortOrder.DATE_ASCENDING.value)) : (BaseAnnotationSortOrder)AnnotationListSortOrder.DATE_ASCENDING;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   protected BaseAnnotationListSorter getSorter() {
/*  310 */     return (BaseAnnotationListSorter)ViewModelProviders.of((Fragment)this, (ViewModelProvider.Factory)new AnnotationListSorter.Factory(this.mAnnotationListSortOrder))
/*      */       
/*  312 */       .get(AnnotationListSorter.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
/*  322 */     View view = inflater.inflate(R.layout.controls_fragment_annotation_dialog, null);
/*      */ 
/*      */     
/*  325 */     this.mRecyclerView = (RecyclerView)view.findViewById(R.id.recyclerview_control_annotation);
/*  326 */     this.mEmptyTextView = (TextView)view.findViewById(R.id.control_annotation_textview_empty);
/*  327 */     this.mProgressBarView = (ProgressBar)view.findViewById(R.id.progress_bar_view);
/*      */     
/*  329 */     this.mFab = (FloatingActionButton)view.findViewById(R.id.export_annotations_button);
/*  330 */     if (this.mIsReadOnly) {
/*  331 */       this.mFab.setVisibility(8);
/*      */     }
/*  333 */     this.mFab.setOnClickListener(new View.OnClickListener()
/*      */         {
/*      */           public void onClick(View v) {
/*  336 */             if (AnnotationDialogFragment.this.mAnnotationDialogListener != null) {
/*  337 */               AnnotationDialogFragment.this.mDisposables.add(AnnotationDialogFragment.this.prepareAnnotations()
/*  338 */                   .subscribeOn(Schedulers.io())
/*  339 */                   .observeOn(AndroidSchedulers.mainThread())
/*  340 */                   .doOnSubscribe(new Consumer<Disposable>()
/*      */                     {
/*      */                       public void accept(Disposable disposable) throws Exception {
/*  343 */                         AnnotationDialogFragment.this.mProgressBarView.setVisibility(0);
/*      */                       }
/*  346 */                     }).subscribe(new Consumer<PDFDoc>()
/*      */                     {
/*      */                       public void accept(PDFDoc pdfDoc) throws Exception {
/*  349 */                         AnnotationDialogFragment.this.mProgressBarView.setVisibility(8);
/*  350 */                         if (AnnotationDialogFragment.this.mAnnotationDialogListener != null) {
/*  351 */                           AnnotationDialogFragment.this.mAnnotationDialogListener.onExportAnnotations(pdfDoc);
/*      */                         }
/*      */                       }
/*      */                     },  new Consumer<Throwable>()
/*      */                     {
/*      */                       public void accept(Throwable throwable) throws Exception
/*      */                       {
/*  358 */                         AnnotationDialogFragment.this.mProgressBarView.setVisibility(8);
/*  359 */                         AnalyticsHandlerAdapter.getInstance().sendException(new Exception(throwable));
/*      */                       }
/*      */                     }));
/*      */             }
/*      */             
/*  364 */             AnnotationDialogFragment.this.onEventAction();
/*  365 */             AnalyticsHandlerAdapter.getInstance().sendEvent(35, 
/*  366 */                 AnalyticsParam.annotationsListActionParam(1));
/*      */           }
/*      */         });
/*      */ 
/*      */     
/*  371 */     ItemClickHelper itemClickHelper = new ItemClickHelper();
/*  372 */     itemClickHelper.attachToRecyclerView(this.mRecyclerView);
/*  373 */     itemClickHelper.setOnItemClickListener(new ItemClickHelper.OnItemClickListener()
/*      */         {
/*      */           public void onItemClick(RecyclerView recyclerView, View view, int position, long id) {
/*  376 */             AnnotationDialogFragment.this.onEventAction();
/*  377 */             AnalyticsHandlerAdapter.getInstance().sendEvent(30, 
/*  378 */                 AnalyticsParam.viewerNavigateByParam(3));
/*      */             
/*  380 */             AnnotationInfo annotInfo = AnnotationDialogFragment.this.mAnnotation.get(position);
/*  381 */             if (AnnotationDialogFragment.this.mPdfViewCtrl != null) {
/*  382 */               ViewerUtils.jumpToAnnotation(AnnotationDialogFragment.this.mPdfViewCtrl, annotInfo.getAnnotation(), annotInfo.getPageNum());
/*      */             }
/*      */ 
/*      */             
/*  386 */             if (AnnotationDialogFragment.this.mAnnotationDialogListener != null) {
/*  387 */               AnnotationDialogFragment.this.mAnnotationDialogListener.onAnnotationClicked(annotInfo.getAnnotation(), annotInfo.getPageNum());
/*      */             }
/*      */           }
/*      */         });
/*      */     
/*  392 */     return view;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
/*  400 */     super.onViewCreated(view, savedInstanceState);
/*  401 */     this.mAnnotationsAdapter = new AnnotationsAdapter(this.mAnnotation);
/*  402 */     this.mRecyclerView.setLayoutManager((RecyclerView.LayoutManager)new LinearLayoutManager(view.getContext()));
/*  403 */     this.mRecyclerView.setAdapter(this.mAnnotationsAdapter);
/*      */     
/*  405 */     this.mEmptyTextView.setText(R.string.controls_annotation_dialog_loading);
/*      */     
/*  407 */     this.mSorter.observeSortOrderChanges(getViewLifecycleOwner(), new Observer<BaseAnnotationSortOrder>()
/*      */         {
/*      */           public void onChanged(@Nullable BaseAnnotationSortOrder annotationListSortOrder) {
/*  410 */             if (annotationListSortOrder != null) {
/*  411 */               AnnotationDialogFragment.this.mAnnotationsAdapter.clear();
/*  412 */               AnnotationDialogFragment.this.mAnnotationsAdapter.notifyDataSetChanged();
/*  413 */               AnnotationDialogFragment.this.populateAnnotationList();
/*      */             } 
/*      */           }
/*      */         });
/*      */ 
/*      */     
/*  419 */     this.mSorter.observeSortOrderChanges(getViewLifecycleOwner(), this.mSortOrderObserver);
/*      */     
/*  421 */     setupLiveUpdate();
/*      */   }
/*      */   
/*  424 */   private ToolManager.AnnotationModificationListener mModificationListener = new ToolManager.AnnotationModificationListener()
/*      */     {
/*      */       public void onAnnotationsAdded(Map<Annot, Integer> annots) {
/*  427 */         if (AnnotationDialogFragment.this.mAnnotationsAdapter != null) {
/*  428 */           AnnotationDialogFragment.this.mAnnotationsAdapter.addAll(AnnotationDialogFragment.this.getAnnotationInfoForChangeSet(annots));
/*      */         }
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public void onAnnotationsPreModify(Map<Annot, Integer> annots) {}
/*      */ 
/*      */ 
/*      */       
/*      */       public void onAnnotationsModified(Map<Annot, Integer> annots, Bundle extra) {
/*  439 */         if (AnnotationDialogFragment.this.mAnnotationsAdapter != null) {
/*  440 */           AnnotationDialogFragment.this.mAnnotationsAdapter.replaceAll(AnnotationDialogFragment.this.getAnnotationInfoForChangeSet(annots));
/*      */         }
/*      */       }
/*      */ 
/*      */       
/*      */       public void onAnnotationsPreRemove(Map<Annot, Integer> annots) {
/*  446 */         if (AnnotationDialogFragment.this.mAnnotationsAdapter != null) {
/*  447 */           AnnotationDialogFragment.this.mAnnotationsAdapter.removeAll(AnnotationDialogFragment.this.getAnnotationInfoForChangeSet(annots));
/*      */         }
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void onAnnotationsRemoved(Map<Annot, Integer> annots) {
/*  456 */         if (AnnotationDialogFragment.this.mPdfViewCtrl != null && AnnotationDialogFragment.this.mPdfViewCtrl.getToolManager() instanceof ToolManager) {
/*  457 */           ((ToolManager)AnnotationDialogFragment.this.mPdfViewCtrl.getToolManager()).deselectAll();
/*      */         }
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public void onAnnotationsRemovedOnPage(int pageNum) {}
/*      */ 
/*      */ 
/*      */       
/*      */       public void annotationsCouldNotBeAdded(String errorMessage) {}
/*      */     };
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setupLiveUpdate() {
/*  473 */     if (this.mPdfViewCtrl != null) {
/*  474 */       ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/*  475 */       toolManager.addAnnotationModificationListener(this.mModificationListener);
/*      */     } 
/*      */   }
/*      */   
/*      */   private ArrayList<AnnotationInfo> getAnnotationInfoForChangeSet(Map<Annot, Integer> annots) {
/*  480 */     ArrayList<AnnotationInfo> annotList = new ArrayList<>();
/*  481 */     TextExtractor textExtractor = new TextExtractor();
/*  482 */     for (Map.Entry<Annot, Integer> entry : annots.entrySet()) {
/*  483 */       Annot key = entry.getKey();
/*  484 */       Integer value = entry.getValue();
/*  485 */       if (key != null && value != null) {
/*      */         try {
/*  487 */           AnnotationInfo info = AnnotationListUtil.toAnnotationInfo(key, this.mPdfViewCtrl.getDoc().getPage(value.intValue()), textExtractor);
/*  488 */           if (info != null) {
/*  489 */             annotList.add(info);
/*      */           }
/*  491 */         } catch (PDFNetException e) {
/*  492 */           AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*      */         } 
/*      */       }
/*      */     } 
/*  496 */     return annotList;
/*      */   }
/*      */   
/*      */   private Single<PDFDoc> prepareAnnotations() {
/*  500 */     return Single.fromCallable(new Callable<PDFDoc>()
/*      */         {
/*      */           public PDFDoc call() throws Exception {
/*  503 */             return Print.exportAnnotations(AnnotationDialogFragment.this.mPdfViewCtrl.getDoc(), AnnotationDialogFragment.this.mIsRtl);
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
/*      */   private void populateAnnotationList() {
/*  515 */     this.mDisposables.add(this.mAnnotListObservable
/*      */         
/*  517 */         .map(new Function<List<AnnotationInfo>, List<AnnotationInfo>>()
/*      */           {
/*      */             public List<AnnotationInfo> apply(List<AnnotationInfo> annotationInfos) throws Exception {
/*  520 */               if (AnnotationDialogFragment.this.mSorter instanceof AnnotationListSorter) {
/*      */                 
/*  522 */                 ((AnnotationListSorter)AnnotationDialogFragment.this.mSorter).sort(annotationInfos);
/*  523 */                 return annotationInfos;
/*      */               } 
/*  525 */               return annotationInfos;
/*      */             }
/*  529 */           }).subscribeOn(Schedulers.io())
/*  530 */         .observeOn(AndroidSchedulers.mainThread())
/*  531 */         .subscribe(new Consumer<List<AnnotationInfo>>()
/*      */           {
/*      */             public void accept(List<AnnotationInfo> annotationInfos) throws Exception {
/*  534 */               AnnotationDialogFragment.this.mAnnotationsAdapter.addAll(annotationInfos);
/*      */               
/*  536 */               if (AnnotationDialogFragment.this.mAnnotationsAdapter.getItemCount() > 0) {
/*  537 */                 AnnotationDialogFragment.this.mEmptyTextView.setVisibility(8);
/*      */               }
/*      */             }
/*      */           },  new Consumer<Throwable>()
/*      */           {
/*      */             public void accept(Throwable throwable) throws Exception
/*      */             {
/*  544 */               AnnotationDialogFragment.this.mProgressBarView.setVisibility(8);
/*  545 */               AnnotationDialogFragment.this.mFab.setVisibility(8);
/*  546 */               AnnotationDialogFragment.this.mEmptyTextView.setText(R.string.controls_annotation_dialog_empty);
/*  547 */               CommonToast.showText((Context)AnnotationDialogFragment.this.getActivity(), R.string.error_generic_message, 0);
/*      */               
/*  549 */               AnalyticsHandlerAdapter.getInstance().sendException(new RuntimeException(throwable));
/*      */             }
/*      */           }new Action()
/*      */           {
/*      */             public void run() throws Exception {
/*  554 */               if (AnnotationDialogFragment.this.mFab != null) {
/*  555 */                 AnnotationDialogFragment.this.mFab.setVisibility((AnnotationDialogFragment.this.mAnnotationsAdapter.getItemCount() > 0) ? 0 : 8);
/*      */                 
/*  557 */                 if (AnnotationDialogFragment.this.mIsReadOnly) {
/*  558 */                   AnnotationDialogFragment.this.mFab.setVisibility(8);
/*      */                 }
/*  560 */                 AnnotationDialogFragment.this.mEmptyTextView.setText(R.string.controls_annotation_dialog_empty);
/*  561 */                 if (AnnotationDialogFragment.this.mAnnotationsAdapter.getItemCount() == 0) {
/*  562 */                   AnnotationDialogFragment.this.mEmptyTextView.setVisibility(0);
/*  563 */                   AnnotationDialogFragment.this.mRecyclerView.setVisibility(8);
/*      */                 } else {
/*  565 */                   AnnotationDialogFragment.this.mEmptyTextView.setVisibility(8);
/*  566 */                   AnnotationDialogFragment.this.mRecyclerView.setVisibility(0);
/*      */                 } 
/*  568 */                 AnnotationDialogFragment.this.mProgressBarView.setVisibility(8);
/*      */               } 
/*      */             }
/*      */           }));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onStop() {
/*  580 */     super.onStop();
/*      */   }
/*      */ 
/*      */   
/*      */   public void onDestroyView() {
/*  585 */     super.onDestroyView();
/*  586 */     if (this.mPdfViewCtrl != null) {
/*  587 */       ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/*  588 */       toolManager.removeAnnotationModificationListener(this.mModificationListener);
/*      */     } 
/*  590 */     this.mDisposables.clear();
/*      */   }
/*      */   
/*      */   private void deleteOnPage(AnnotationInfo annotationInfo) {
/*  594 */     if (this.mPdfViewCtrl == null) {
/*      */       return;
/*      */     }
/*      */     
/*  598 */     int pageNum = annotationInfo.getPageNum();
/*  599 */     boolean hasChange = false;
/*  600 */     boolean shouldUnlock = false;
/*      */ 
/*      */     
/*      */     try {
/*  604 */       this.mPdfViewCtrl.docLock(true);
/*  605 */       shouldUnlock = true;
/*  606 */       ArrayList<AnnotationInfo> items = this.mAnnotationsAdapter.getItemsOnPage(pageNum);
/*  607 */       Page page = this.mPdfViewCtrl.getDoc().getPage(pageNum);
/*  608 */       for (AnnotationInfo info : items) {
/*  609 */         if (info.getAnnotation() != null) {
/*  610 */           page.annotRemove(info.getAnnotation());
/*  611 */           this.mAnnotationsAdapter.remove(info);
/*      */         } 
/*      */       } 
/*  614 */       this.mPdfViewCtrl.update(true);
/*  615 */       hasChange = this.mPdfViewCtrl.getDoc().hasChangesSinceSnapshot();
/*  616 */     } catch (Exception e) {
/*  617 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } finally {
/*  619 */       if (shouldUnlock) {
/*  620 */         this.mPdfViewCtrl.docUnlock();
/*      */       }
/*      */     } 
/*      */     
/*  624 */     if (hasChange) {
/*  625 */       ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/*  626 */       if (toolManager != null) {
/*  627 */         toolManager.raiseAnnotationsRemovedEvent(pageNum);
/*      */       }
/*      */     } 
/*      */     
/*  631 */     this.mAnnotationsAdapter.notifyDataSetChanged();
/*      */   }
/*      */   
/*      */   private void deleteAll() {
/*  635 */     if (this.mPdfViewCtrl == null) {
/*      */       return;
/*      */     }
/*      */     
/*  639 */     boolean hasChange = false;
/*  640 */     boolean shouldUnlock = false;
/*      */ 
/*      */     
/*      */     try {
/*  644 */       this.mPdfViewCtrl.docLock(true);
/*  645 */       shouldUnlock = true;
/*      */       
/*  647 */       AnnotUtils.safeDeleteAllAnnots(this.mPdfViewCtrl.getDoc());
/*  648 */       this.mPdfViewCtrl.update(true);
/*  649 */       hasChange = this.mPdfViewCtrl.getDoc().hasChangesSinceSnapshot();
/*  650 */     } catch (Exception e) {
/*  651 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } finally {
/*  653 */       if (shouldUnlock) {
/*  654 */         this.mPdfViewCtrl.docUnlock();
/*      */       }
/*      */     } 
/*      */     
/*  658 */     if (hasChange) {
/*  659 */       ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/*  660 */       if (toolManager != null) {
/*  661 */         toolManager.raiseAllAnnotationsRemovedEvent();
/*      */       }
/*      */     } 
/*      */     
/*  665 */     this.mAnnotationsAdapter.clear();
/*  666 */     this.mAnnotationsAdapter.notifyDataSetChanged();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class AnnotationInfo
/*      */   {
/*      */     private int mType;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int mPageNum;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String mContent;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String mAuthor;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Annot mAnnotation;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String mDate;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final double mY2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     AnnotationInfo() {
/*  712 */       this(0, 0, "", "", "", null, 0.0D);
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
/*      */     
/*      */     public AnnotationInfo(int type, int pageNum, String content, String author, String date, @Nullable Annot annotation, double yPos) {
/*  735 */       this.mType = type;
/*  736 */       this.mPageNum = pageNum;
/*  737 */       this.mContent = content;
/*  738 */       this.mAuthor = author;
/*  739 */       this.mDate = date;
/*  740 */       this.mAnnotation = annotation;
/*  741 */       this.mY2 = yPos;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getType() {
/*  751 */       return this.mType;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setType(int mType) {
/*  761 */       this.mType = mType;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getPageNum() {
/*  768 */       return this.mPageNum;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setPageNum(int mPageNum) {
/*  777 */       this.mPageNum = mPageNum;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getContent() {
/*  784 */       return this.mContent;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setContent(String mContent) {
/*  793 */       this.mContent = mContent;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getAuthor() {
/*  800 */       return this.mAuthor;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setAuthor(String author) {
/*  809 */       this.mAuthor = author;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Nullable
/*      */     public Annot getAnnotation() {
/*  817 */       return this.mAnnotation;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getDate() {
/*  826 */       return this.mDate;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double getY2() {
/*  836 */       return this.mY2;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  842 */       if (this == o) return true; 
/*  843 */       if (o == null || getClass() != o.getClass()) return false;
/*      */       
/*  845 */       AnnotationInfo info = (AnnotationInfo)o;
/*      */       
/*  847 */       return (this.mAnnotation != null) ? this.mAnnotation.equals(info.mAnnotation) : ((info.mAnnotation == null));
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  852 */       return (this.mAnnotation != null) ? this.mAnnotation.hashCode() : 0;
/*      */     }
/*      */   }
/*      */   
/*      */   private class AnnotationsAdapter
/*      */     extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
/*      */     private static final int STATE_UNKNOWN = 0;
/*      */     private static final int STATE_SECTIONED_CELL = 1;
/*      */     private static final int STATE_REGULAR_CELL = 2;
/*      */     private ArrayList<AnnotationInfo> mAnnotation;
/*      */     private int[] mCellStates;
/*      */     
/*  864 */     private RecyclerView.AdapterDataObserver observer = new RecyclerView.AdapterDataObserver() {
/*      */         public void onChanged() {
/*  866 */           AnnotationsAdapter.this.mCellStates = (AnnotationsAdapter.this.mAnnotation == null) ? null : new int[AnnotationsAdapter.this.mAnnotation.size()];
/*      */         }
/*      */       };
/*      */     
/*      */     AnnotationsAdapter(ArrayList<AnnotationInfo> objects) {
/*  871 */       this.mAnnotation = objects;
/*      */       
/*  873 */       this.mCellStates = new int[objects.size()];
/*  874 */       registerAdapterDataObserver(this.observer);
/*      */     }
/*      */     
/*      */     public void addAll(List<AnnotationInfo> annotationInfos) {
/*  878 */       this.mAnnotation.addAll(annotationInfos);
/*  879 */       notifyDataSetChanged();
/*      */     }
/*      */     
/*      */     public void removeAll(List<AnnotationInfo> annotationInfos) {
/*  883 */       this.mAnnotation.removeAll(annotationInfos);
/*  884 */       notifyDataSetChanged();
/*      */     }
/*      */     
/*      */     public void replaceAll(List<AnnotationInfo> annotationInfos) {
/*  888 */       this.mAnnotation.removeAll(annotationInfos);
/*  889 */       this.mAnnotation.addAll(annotationInfos);
/*  890 */       notifyDataSetChanged();
/*      */     }
/*      */     
/*      */     AnnotationInfo getItem(int position) {
/*  894 */       if (this.mAnnotation != null && position >= 0 && position < this.mAnnotation.size()) {
/*  895 */         return this.mAnnotation.get(position);
/*      */       }
/*  897 */       return null;
/*      */     }
/*      */     
/*      */     ArrayList<AnnotationInfo> getItemsOnPage(int pageNum) {
/*  901 */       ArrayList<AnnotationInfo> list = new ArrayList<>();
/*  902 */       if (this.mAnnotation != null) {
/*  903 */         for (AnnotationInfo info : this.mAnnotation) {
/*  904 */           if (info.getPageNum() == pageNum) {
/*  905 */             list.add(info);
/*      */           }
/*      */         } 
/*  908 */         return list;
/*      */       } 
/*  910 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     @NonNull
/*      */     public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
/*  916 */       View view = LayoutInflater.from(AnnotationDialogFragment.this.getContext()).inflate(R.layout.controls_fragment_annotation_listview_item, parent, false);
/*      */       
/*  918 */       return new ViewHolder(view);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     @NonNull
/*      */     public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
/*  925 */       Context context = AnnotationDialogFragment.this.getContext();
/*  926 */       if (context == null) {
/*      */         return;
/*      */       }
/*      */       
/*  930 */       boolean needSeparator = false;
/*  931 */       AnnotationInfo annotationInfo = this.mAnnotation.get(position);
/*  932 */       if (position < this.mCellStates.length) {
/*  933 */         switch (this.mCellStates[position]) {
/*      */           case 1:
/*  935 */             needSeparator = true;
/*      */             break;
/*      */           case 2:
/*  938 */             needSeparator = false;
/*      */             break;
/*      */           
/*      */           default:
/*  942 */             if (position == 0) {
/*  943 */               needSeparator = true;
/*      */             } else {
/*  945 */               AnnotationInfo previousAnnotation = this.mAnnotation.get(position - 1);
/*  946 */               if (annotationInfo.getPageNum() != previousAnnotation.getPageNum()) {
/*  947 */                 needSeparator = true;
/*      */               }
/*      */             } 
/*      */ 
/*      */             
/*  952 */             this.mCellStates[position] = needSeparator ? 1 : 2;
/*      */             break;
/*      */         } 
/*      */       
/*      */       }
/*  957 */       ViewHolder viewHolder = (ViewHolder)holder;
/*      */       
/*  959 */       if (needSeparator) {
/*  960 */         viewHolder.separator.setText(String.format(AnnotationDialogFragment.this.getString(R.string.controls_annotation_dialog_page), new Object[] { Integer.valueOf(annotationInfo.getPageNum()) }));
/*  961 */         viewHolder.separator.setVisibility(0);
/*      */       } else {
/*  963 */         viewHolder.separator.setVisibility(8);
/*      */       } 
/*  965 */       String content = annotationInfo.getContent();
/*  966 */       if (Utils.isNullOrEmpty(content)) {
/*  967 */         viewHolder.line1.setVisibility(8);
/*      */       } else {
/*  969 */         viewHolder.line1.setText(annotationInfo.getContent());
/*  970 */         viewHolder.line1.setVisibility(0);
/*      */       } 
/*      */ 
/*      */       
/*  974 */       viewHolder.icon.setImageResource(AnnotUtils.getAnnotImageResId(annotationInfo.getType()));
/*      */       
/*  976 */       StringBuilder descBuilder = new StringBuilder();
/*  977 */       if (PdfViewCtrlSettingsManager.getAnnotListShowAuthor(context)) {
/*  978 */         String author = annotationInfo.getAuthor();
/*  979 */         if (!author.isEmpty()) {
/*  980 */           descBuilder.append(author).append(", ");
/*      */         }
/*      */       } 
/*  983 */       descBuilder.append(annotationInfo.getDate());
/*  984 */       viewHolder.line2.setText(descBuilder.toString());
/*      */ 
/*      */       
/*  987 */       Annot annot = annotationInfo.getAnnotation();
/*      */       
/*  989 */       int color = AnnotUtils.getAnnotColor(annot);
/*  990 */       if (color == -1) {
/*  991 */         color = -16777216;
/*      */       }
/*  993 */       viewHolder.icon.setColorFilter(color);
/*  994 */       viewHolder.icon.setAlpha(AnnotUtils.getAnnotOpacity(annot));
/*      */     }
/*      */ 
/*      */     
/*      */     public int getItemCount() {
/*  999 */       if (this.mAnnotation != null) {
/* 1000 */         return this.mAnnotation.size();
/*      */       }
/* 1002 */       return 0;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1007 */       this.mAnnotation.clear();
/*      */     }
/*      */     
/*      */     public boolean remove(AnnotationInfo annotInfo) {
/* 1011 */       return this.mAnnotation.remove(annotInfo);
/*      */     }
/*      */     
/*      */     private class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
/*      */       TextView separator;
/*      */       TextView line1;
/*      */       TextView line2;
/*      */       ImageView icon;
/*      */       
/*      */       public ViewHolder(View itemView) {
/* 1021 */         super(itemView);
/* 1022 */         this.separator = (TextView)itemView.findViewById(R.id.textview_annotation_recyclerview_item_separator);
/* 1023 */         this.icon = (ImageView)itemView.findViewById(R.id.imageview_annotation_recyclerview_item);
/* 1024 */         this.line1 = (TextView)itemView.findViewById(R.id.textview_annotation_recyclerview_item);
/* 1025 */         this.line2 = (TextView)itemView.findViewById(R.id.textview_desc_recyclerview_item);
/* 1026 */         if (!AnnotationDialogFragment.this.mIsReadOnly) {
/* 1027 */           itemView.setOnCreateContextMenuListener(this);
/*      */         }
/*      */       }
/*      */ 
/*      */       
/*      */       public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
/* 1033 */         final int position = AnnotationDialogFragment.this.mRecyclerView.getChildAdapterPosition(view);
/* 1034 */         AnnotationInfo item = AnnotationDialogFragment.this.mAnnotationsAdapter.getItem(position);
/* 1035 */         if (item != null) {
/* 1036 */           String title = String.format(AnnotationDialogFragment.this.getString(R.string.controls_annotation_dialog_page), new Object[] { Integer.valueOf(item.getPageNum()) });
/* 1037 */           String author = item.getAuthor();
/* 1038 */           if (!Utils.isNullOrEmpty(author)) {
/* 1039 */             title = title + " " + AnnotationDialogFragment.this.getString(R.string.controls_annotation_dialog_author) + " " + author;
/*      */           }
/* 1041 */           menu.setHeaderTitle(title);
/*      */         } 
/* 1043 */         String[] menuItems = AnnotationDialogFragment.this.getResources().getStringArray(R.array.annotation_dialog_context_menu);
/* 1044 */         menu.add(0, 0, 0, menuItems[0]);
/* 1045 */         String deleteOnPage = menuItems[1];
/* 1046 */         if (item != null) {
/* 1047 */           deleteOnPage = deleteOnPage + " " + item.getPageNum();
/*      */         }
/* 1049 */         menu.add(0, 1, 1, deleteOnPage);
/* 1050 */         menu.add(0, 2, 2, menuItems[2]);
/* 1051 */         MenuItem.OnMenuItemClickListener listener = new MenuItem.OnMenuItemClickListener()
/*      */           {
/*      */             public boolean onMenuItemClick(MenuItem item) {
/* 1054 */               ViewHolder.this.onContextMenuItemClicked(item, position);
/* 1055 */               return true;
/*      */             }
/*      */           };
/* 1058 */         menu.getItem(0).setOnMenuItemClickListener(listener);
/* 1059 */         menu.getItem(1).setOnMenuItemClickListener(listener);
/* 1060 */         menu.getItem(2).setOnMenuItemClickListener(listener);
/*      */       } void onContextMenuItemClicked(MenuItem item, int position) {
/*      */         AnnotationInfo annotationInfo;
/*      */         Annot annot;
/* 1064 */         int menuItemIndex = item.getItemId();
/* 1065 */         switch (menuItemIndex) {
/*      */           case 0:
/* 1067 */             annotationInfo = AnnotationDialogFragment.this.mAnnotationsAdapter.getItem(position);
/* 1068 */             if (annotationInfo == null || AnnotationDialogFragment.this.mPdfViewCtrl == null) {
/*      */               return;
/*      */             }
/* 1071 */             annot = annotationInfo.getAnnotation();
/* 1072 */             if (annot != null) {
/* 1073 */               int annotPageNum = annotationInfo.getPageNum();
/* 1074 */               HashMap<Annot, Integer> annots = new HashMap<>(1);
/* 1075 */               annots.put(annot, Integer.valueOf(annotPageNum));
/* 1076 */               ToolManager toolManager = (ToolManager)AnnotationDialogFragment.this.mPdfViewCtrl.getToolManager();
/* 1077 */               boolean shouldUnlock = false;
/*      */ 
/*      */               
/*      */               try {
/* 1081 */                 AnnotationDialogFragment.this.mPdfViewCtrl.docLock(true);
/* 1082 */                 shouldUnlock = true;
/* 1083 */                 if (toolManager != null) {
/* 1084 */                   toolManager.raiseAnnotationsPreRemoveEvent(annots);
/*      */                 }
/*      */                 
/* 1087 */                 Page page = AnnotationDialogFragment.this.mPdfViewCtrl.getDoc().getPage(annotPageNum);
/* 1088 */                 page.annotRemove(annot);
/* 1089 */                 AnnotationDialogFragment.this.mPdfViewCtrl.update(annot, annotPageNum);
/*      */ 
/*      */                 
/* 1092 */                 if (toolManager != null) {
/* 1093 */                   toolManager.raiseAnnotationsRemovedEvent(annots);
/*      */                 }
/* 1095 */               } catch (Exception e) {
/* 1096 */                 AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */               } finally {
/* 1098 */                 if (shouldUnlock) {
/* 1099 */                   AnnotationDialogFragment.this.mPdfViewCtrl.docUnlock();
/*      */                 }
/*      */               } 
/*      */             } 
/* 1103 */             AnnotationDialogFragment.this.onEventAction();
/* 1104 */             AnalyticsHandlerAdapter.getInstance().sendEvent(35, 
/* 1105 */                 AnalyticsParam.annotationsListActionParam(2));
/*      */             break;
/*      */           case 1:
/* 1108 */             annotationInfo = AnnotationDialogFragment.this.mAnnotationsAdapter.getItem(position);
/* 1109 */             if (annotationInfo != null && annotationInfo.getAnnotation() != null) {
/* 1110 */               AnnotationDialogFragment.this.deleteOnPage(annotationInfo);
/*      */             }
/* 1112 */             AnnotationDialogFragment.this.onEventAction();
/* 1113 */             AnalyticsHandlerAdapter.getInstance().sendEvent(35, 
/* 1114 */                 AnalyticsParam.annotationsListActionParam(3));
/*      */             break;
/*      */           case 2:
/* 1117 */             AnnotationDialogFragment.this.deleteAll();
/* 1118 */             AnnotationDialogFragment.this.onEventAction();
/* 1119 */             AnalyticsHandlerAdapter.getInstance().sendEvent(35, 
/* 1120 */                 AnalyticsParam.annotationsListActionParam(4));
/*      */             break;
/*      */         } 
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public static interface AnnotationDialogListener {
/*      */     void onAnnotationClicked(Annot param1Annot, int param1Int);
/*      */     
/*      */     void onExportAnnotations(PDFDoc param1PDFDoc);
/*      */   }
/*      */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\controls\AnnotationDialogFragment.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */