/*      */ package com.pdftron.pdf.controls;
/*      */ 
/*      */ import android.app.ProgressDialog;
/*      */ import android.content.ContentResolver;
/*      */ import android.content.Context;
/*      */ import android.content.DialogInterface;
/*      */ import android.graphics.Bitmap;
/*      */ import android.net.Uri;
/*      */ import android.os.AsyncTask;
/*      */ import android.os.CountDownTimer;
/*      */ import android.util.Log;
/*      */ import android.util.SparseArray;
/*      */ import android.view.LayoutInflater;
/*      */ import android.view.View;
/*      */ import android.view.ViewGroup;
/*      */ import android.widget.ImageView;
/*      */ import android.widget.RelativeLayout;
/*      */ import android.widget.TextView;
/*      */ import androidx.annotation.NonNull;
/*      */ import androidx.annotation.Nullable;
/*      */ import androidx.fragment.app.FragmentManager;
/*      */ import androidx.recyclerview.widget.LinearLayoutManager;
/*      */ import androidx.recyclerview.widget.RecyclerView;
/*      */ import co.paulburke.android.itemtouchhelperdemo.helper.ItemTouchHelperAdapter;
/*      */ import com.pdftron.common.PDFNetException;
/*      */ import com.pdftron.filters.Filter;
/*      */ import com.pdftron.filters.SecondaryFileFilter;
/*      */ import com.pdftron.pdf.Convert;
/*      */ import com.pdftron.pdf.DocumentConversion;
/*      */ import com.pdftron.pdf.PDFDoc;
/*      */ import com.pdftron.pdf.PDFViewCtrl;
/*      */ import com.pdftron.pdf.Page;
/*      */ import com.pdftron.pdf.PageIterator;
/*      */ import com.pdftron.pdf.Rect;
/*      */ import com.pdftron.pdf.dialog.pagelabel.PageLabelUtils;
/*      */ import com.pdftron.pdf.tools.R;
/*      */ import com.pdftron.pdf.tools.ToolManager;
/*      */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*      */ import com.pdftron.pdf.utils.BookmarkManager;
/*      */ import com.pdftron.pdf.utils.CommonToast;
/*      */ import com.pdftron.pdf.utils.CustomAsyncTask;
/*      */ import com.pdftron.pdf.utils.Utils;
/*      */ import com.pdftron.pdf.widget.recyclerview.SimpleRecyclerViewAdapter;
/*      */ import com.pdftron.pdf.widget.recyclerview.ViewHolderBindListener;
/*      */ import com.pdftron.sdf.Obj;
/*      */ import com.squareup.picasso.Picasso;
/*      */ import java.io.File;
/*      */ import java.io.FileOutputStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.List;
/*      */ import java.util.ListIterator;
/*      */ import java.util.Map;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import java.util.concurrent.CopyOnWriteArrayList;
/*      */ import java.util.concurrent.locks.Lock;
/*      */ import java.util.concurrent.locks.ReentrantLock;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ThumbnailsViewAdapter
/*      */   extends SimpleRecyclerViewAdapter<Integer, ThumbnailsViewAdapter.PageViewHolder>
/*      */   implements PDFViewCtrl.ThumbAsyncListener, ItemTouchHelperAdapter, PasswordDialogFragment.PasswordDialogFragmentListener
/*      */ {
/*   74 */   private static final String TAG = ThumbnailsViewAdapter.class.getName();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean sDebug = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private EditPagesListener mEditPageListener;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Context mContext;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private FragmentManager mFragmentManager;
/*      */ 
/*      */ 
/*      */   
/*      */   private PDFViewCtrl mPdfViewCtrl;
/*      */ 
/*      */ 
/*      */   
/*      */   private LayoutInflater mLayoutInflater;
/*      */ 
/*      */ 
/*      */   
/*  106 */   private List<Integer> mDataList = new ArrayList<>();
/*      */   private ConcurrentHashMap<Integer, File> mThumbFileMap;
/*  108 */   private CopyOnWriteArrayList<File> mThumbFiles = new CopyOnWriteArrayList<>();
/*      */ 
/*      */   
/*      */   private SparseArray<LoadThumbnailTask> mTaskList;
/*      */ 
/*      */   
/*      */   private boolean mDocPagesModified = false;
/*      */ 
/*      */   
/*      */   private int mCurrentPage;
/*      */   
/*      */   private int mSpanCount;
/*      */   
/*      */   private int mRecyclerViewWidth;
/*      */   
/*  123 */   private final Object mPauseWorkLock = new Object();
/*      */   
/*      */   private boolean mPauseWork = false;
/*  126 */   private final Lock mDataLock = new ReentrantLock();
/*      */ 
/*      */   
/*      */   private int mPwdRequestLastPage;
/*      */ 
/*      */   
/*      */   private Uri mPwdRequestUri;
/*      */ 
/*      */ 
/*      */   
/*      */   public enum DocumentFormat
/*      */   {
/*  138 */     PDF_PAGE,
/*      */ 
/*      */ 
/*      */     
/*  142 */     BLANK_PDF_PAGE,
/*      */ 
/*      */ 
/*      */     
/*  146 */     PDF_DOC,
/*      */ 
/*      */ 
/*      */     
/*  150 */     IMAGE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ThumbnailsViewAdapter(Context context, EditPagesListener listener, FragmentManager fragmentManager, PDFViewCtrl pdfViewCtrl, @Nullable List<Integer> dataList, int spanCount, ViewHolderBindListener bindListener) {
/*  159 */     super(bindListener);
/*  160 */     this.mContext = context;
/*  161 */     this.mEditPageListener = listener;
/*  162 */     this.mFragmentManager = fragmentManager;
/*  163 */     this.mPdfViewCtrl = pdfViewCtrl;
/*  164 */     if (dataList != null) {
/*  165 */       this.mDataList.addAll(dataList);
/*      */     }
/*  167 */     this.mThumbFileMap = new ConcurrentHashMap<>();
/*  168 */     this.mTaskList = new SparseArray();
/*  169 */     this.mSpanCount = spanCount;
/*  170 */     this.mCurrentPage = this.mPdfViewCtrl.getCurrentPage();
/*  171 */     this.mPdfViewCtrl.addThumbAsyncListener(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
/*  179 */     super.onDetachedFromRecyclerView(recyclerView);
/*  180 */     this.mPdfViewCtrl.removeThumbAsyncListener(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void finish() {
/*  187 */     this.mPdfViewCtrl.removeThumbAsyncListener(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getItemCount() {
/*  195 */     return (this.mDataList != null) ? this.mDataList.size() : 0;
/*      */   }
/*      */   
/*      */   public void setItem(int position, int data) {
/*  199 */     if (this.mDataList != null && position >= 0 && position < this.mDataList.size()) {
/*  200 */       int oldPage = getItem(position).intValue();
/*  201 */       removeCachedPage(oldPage);
/*  202 */       this.mDataList.set(position, Integer.valueOf(data));
/*      */     } 
/*      */   }
/*      */   
/*      */   public void clear() {
/*  207 */     this.mDataLock.lock();
/*  208 */     this.mDataList.clear();
/*  209 */     this.mDataLock.unlock();
/*  210 */     clearResources();
/*      */   }
/*      */   
/*      */   public void setData(List<Integer> data) {
/*  214 */     clear();
/*  215 */     addAll(data);
/*      */   }
/*      */   
/*      */   public void addAll(List<Integer> data) {
/*  219 */     this.mDataLock.lock();
/*  220 */     this.mDataList.addAll(data);
/*  221 */     this.mDataLock.unlock();
/*      */     
/*  223 */     Utils.safeNotifyDataSetChanged((RecyclerView.Adapter)this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getItem(int position) {
/*  231 */     if (this.mDataList != null && position >= 0 && position < this.mDataList.size()) {
/*  232 */       return this.mDataList.get(position);
/*      */     }
/*  234 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
/*  242 */     View view = getLayoutInflater().inflate(R.layout.controls_thumbnails_view_grid_item, parent, false);
/*  243 */     return new PageViewHolder(view);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onBindViewHolder(PageViewHolder holder, int position) {
/*  251 */     super.onBindViewHolder(holder, position);
/*      */     
/*  253 */     if (this.mPdfViewCtrl == null) {
/*      */       return;
/*      */     }
/*      */     
/*  257 */     ViewGroup.LayoutParams params = holder.imageLayout.getLayoutParams();
/*  258 */     params.width = this.mRecyclerViewWidth / this.mSpanCount;
/*  259 */     params.height = (int)(params.width * 1.29D);
/*  260 */     holder.imageLayout.requestLayout();
/*      */     
/*  262 */     int pageNum = getItem(position).intValue();
/*      */     
/*  264 */     String pageLabel = PageLabelUtils.getPageLabelTitle(this.mPdfViewCtrl, pageNum);
/*  265 */     if (!Utils.isNullOrEmpty(pageLabel)) {
/*  266 */       holder.pageNumber.setText(pageLabel);
/*      */     } else {
/*  268 */       holder.pageNumber.setText(Utils.getLocaleDigits(Integer.toString(pageNum)));
/*      */     } 
/*      */     
/*  271 */     if (pageNum == this.mCurrentPage) {
/*  272 */       holder.pageNumber.setBackgroundResource(R.drawable.controls_thumbnails_view_rounded_edges_current);
/*      */     } else {
/*  274 */       holder.pageNumber.setBackgroundResource(R.drawable.controls_thumbnails_view_rounded_edges);
/*      */     } 
/*      */     
/*  277 */     File thumbFile = this.mThumbFileMap.get(Integer.valueOf(pageNum));
/*  278 */     if (thumbFile != null) {
/*  279 */       Picasso.get()
/*  280 */         .load(thumbFile)
/*  281 */         .into(holder.thumbImage);
/*      */     } else {
/*      */       try {
/*  284 */         this.mPdfViewCtrl.getThumbAsync(pageNum);
/*  285 */       } catch (Exception e) {
/*  286 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */       } 
/*  288 */       holder.thumbImage.setImageBitmap(null);
/*      */     } 
/*  290 */     holder.thumbImage.setBackgroundColor(this.mContext.getResources().getColor(R.color.controls_thumbnails_view_bg));
/*      */   }
/*      */   
/*      */   @NonNull
/*      */   private LayoutInflater getLayoutInflater() {
/*  295 */     if (this.mLayoutInflater == null) {
/*  296 */       this.mLayoutInflater = LayoutInflater.from(this.mContext);
/*      */     }
/*  298 */     return this.mLayoutInflater;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void add(Integer item) {
/*  306 */     if (this.mDataList != null && item != null) {
/*  307 */       this.mDataList.add(item);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void insert(Integer item, int position) {
/*  316 */     if (this.mDataList != null && item != null) {
/*  317 */       this.mDataList.add(position, item);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Integer item) {
/*  326 */     return (this.mDataList != null && item != null && this.mDataList.remove(item));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer removeAt(int location) {
/*  334 */     if (location < 0 || this.mDataList == null || location >= this.mDataList.size()) {
/*  335 */       return null;
/*      */     }
/*  337 */     return this.mDataList.remove(location);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateSpanCount(int count) {
/*  345 */     this.mSpanCount = count;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onItemMove(int fromPosition, int toPosition) {
/*  353 */     if (toPosition < getItemCount()) {
/*      */       
/*  355 */       Integer item = removeAt(fromPosition);
/*  356 */       insert(item, toPosition);
/*      */       
/*  358 */       notifyItemMoved(fromPosition, toPosition);
/*  359 */       return true;
/*      */     } 
/*  361 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onItemDrop(int fromPosition, int toPosition) {
/*  369 */     if (fromPosition != -1 && toPosition != -1 && fromPosition != toPosition)
/*      */     {
/*      */       
/*  372 */       moveDocPage(fromPosition, toPosition);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onItemDismiss(int position) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onThumbReceived(int page, int[] buf, int width, int height) {
/*  389 */     if (sDebug) Log.d(TAG, "onThumbReceived received page: " + Integer.toString(page)); 
/*  390 */     if (null == this.mDataList) {
/*      */       return;
/*      */     }
/*  393 */     int position = getPositionForPage(page);
/*      */     
/*  395 */     if (this.mTaskList.get(page) == null) {
/*  396 */       if (sDebug) Log.d(TAG, "startLoadBitmapTask for page: " + Integer.toString(page)); 
/*  397 */       LoadThumbnailTask task = new LoadThumbnailTask(position, page, buf, width, height);
/*  398 */       this.mTaskList.put(page, task);
/*  399 */       task.execute((Object[])new Void[0]);
/*      */     }
/*  401 */     else if (sDebug) {
/*  402 */       Log.d(TAG, "A task is already running for page: " + Integer.toString(page));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onPasswordDialogPositiveClick(int fileType, File file, String path, String password, String id) {
/*  411 */     addDocPages(this.mPwdRequestLastPage, DocumentFormat.PDF_DOC, this.mPwdRequestUri, password);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onPasswordDialogNegativeClick(int fileType, File file, String path) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onPasswordDialogDismiss(boolean forcedDismiss) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCurrentPage(int pageNum) {
/*  436 */     this.mCurrentPage = pageNum;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getCurrentPage() {
/*  443 */     return this.mCurrentPage;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getDocPagesModified() {
/*  450 */     return this.mDocPagesModified;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateMainViewWidth(int width) {
/*  459 */     this.mRecyclerViewWidth = width;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearResources() {
/*  466 */     for (Map.Entry<Integer, File> entry : this.mThumbFileMap.entrySet()) {
/*  467 */       File tmp = entry.getValue();
/*  468 */       if (tmp != null) {
/*  469 */         tmp.delete();
/*      */       }
/*      */     } 
/*  472 */     this.mThumbFileMap.clear();
/*      */     
/*  474 */     for (File file : this.mThumbFiles) {
/*  475 */       if (file != null && file.exists()) {
/*  476 */         if (sDebug)
/*  477 */           Log.d(TAG, "remove not cleaned up file: " + file.getAbsolutePath()); 
/*  478 */         file.delete();
/*      */       } 
/*      */     } 
/*  481 */     this.mThumbFiles.clear();
/*      */   }
/*      */   
/*      */   private void moveDocPage(int fromPosition, int toPosition) {
/*  485 */     this.mDocPagesModified = true;
/*      */ 
/*      */     
/*  488 */     if (fromPosition > -1 && fromPosition < getItemCount() && toPosition > -1 && toPosition < 
/*  489 */       getItemCount()) {
/*      */       
/*  491 */       int fromPageNum = fromPosition + 1;
/*  492 */       int toPageNum = toPosition + 1;
/*  493 */       boolean shouldUnlock = false;
/*      */       try {
/*  495 */         this.mPdfViewCtrl.docLock(true);
/*  496 */         shouldUnlock = true;
/*      */ 
/*      */         
/*  499 */         PDFDoc doc = this.mPdfViewCtrl.getDoc();
/*  500 */         if (doc == null) {
/*      */           return;
/*      */         }
/*  503 */         Page pageToMove = doc.getPage(fromPageNum);
/*  504 */         if (pageToMove == null) {
/*      */           return;
/*      */         }
/*      */         
/*  508 */         if (fromPageNum < toPageNum) {
/*      */           
/*  510 */           PageIterator moveTo = doc.getPageIterator(toPageNum + 1);
/*      */           
/*  512 */           doc.pageInsert(moveTo, pageToMove);
/*      */           
/*  514 */           PageIterator itr = doc.getPageIterator(fromPageNum);
/*  515 */           doc.pageRemove(itr);
/*  516 */         } else if (fromPageNum > toPageNum) {
/*      */           
/*  518 */           PageIterator moveTo = doc.getPageIterator(toPageNum);
/*      */           
/*  520 */           doc.pageInsert(moveTo, pageToMove);
/*      */           
/*  522 */           PageIterator itr = doc.getPageIterator(fromPageNum + 1);
/*  523 */           doc.pageRemove(itr);
/*      */         } 
/*      */ 
/*      */         
/*  527 */         updateUserBookmarks(fromPageNum, toPageNum, Long.valueOf(pageToMove.getSDFObj().getObjNum()), 
/*  528 */             Long.valueOf(doc.getPage(toPageNum).getSDFObj().getObjNum()));
/*      */         
/*  530 */         if (this.mEditPageListener != null) {
/*  531 */           this.mEditPageListener.onPageMoved(fromPageNum, toPageNum);
/*      */         }
/*  533 */       } catch (Exception e) {
/*  534 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */       } finally {
/*  536 */         if (shouldUnlock) {
/*  537 */           this.mPdfViewCtrl.docUnlock();
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/*  542 */       boolean currentPageUpdated = false;
/*  543 */       SparseArray<File> tempList = new SparseArray(); int i;
/*  544 */       for (i = Math.min(fromPosition, toPosition); i <= Math.max(fromPosition, toPosition); i++) {
/*  545 */         Integer itemMap = getItem(i);
/*  546 */         if (itemMap != null) {
/*  547 */           int oldPageNum = itemMap.intValue();
/*  548 */           int newPageNum = i + 1;
/*      */           
/*  550 */           File oldThumb = this.mThumbFileMap.get(Integer.valueOf(oldPageNum));
/*  551 */           if (!currentPageUpdated && oldPageNum == this.mCurrentPage) {
/*      */             
/*  553 */             this.mCurrentPage = newPageNum;
/*  554 */             currentPageUpdated = true;
/*      */           } 
/*  556 */           setItem(i, newPageNum);
/*  557 */           if (oldThumb != null) {
/*  558 */             tempList.put(newPageNum, oldThumb);
/*      */           }
/*      */         } 
/*      */       } 
/*  562 */       for (i = 0; i < tempList.size(); i++) {
/*  563 */         int key = tempList.keyAt(i);
/*  564 */         File file = (File)tempList.get(key);
/*  565 */         this.mThumbFileMap.put(Integer.valueOf(key), file);
/*      */       } 
/*      */       
/*  568 */       Utils.safeNotifyDataSetChanged((RecyclerView.Adapter)this);
/*      */     } 
/*      */   }
/*      */   
/*      */   private class AddDocPagesTask
/*      */     extends CustomAsyncTask<Void, Void, Void> {
/*      */     private static final int MIN_DELAY = 250;
/*      */     private ProgressDialog mProgressDialog;
/*      */     private CountDownTimer mCountDownTimer;
/*      */     private int mPosition;
/*      */     private DocumentFormat mDocumentFormat;
/*      */     private Object mData;
/*      */     private String mPassword;
/*      */     private boolean mInsert;
/*  582 */     private int mNewPageNum = 1;
/*      */     private boolean mIsNotPdf = false;
/*      */     private PDFDoc mDocTemp;
/*      */     
/*      */     AddDocPagesTask(Context context, int position, DocumentFormat documentFormat, Object data, String password) {
/*  587 */       super(context);
/*  588 */       this.mPosition = position;
/*  589 */       this.mDocumentFormat = documentFormat;
/*  590 */       this.mData = data;
/*  591 */       this.mPassword = password;
/*      */       
/*  593 */       this.mProgressDialog = new ProgressDialog(context);
/*  594 */       this.mProgressDialog.setIndeterminate(true);
/*  595 */       if (documentFormat == DocumentFormat.IMAGE) {
/*  596 */         this.mProgressDialog.setMessage(context.getResources().getString(R.string.add_image_wait));
/*  597 */         this.mProgressDialog.setCancelable(false);
/*      */       } else {
/*  599 */         this.mProgressDialog.setMessage(context.getResources().getString(R.string.add_pdf_wait));
/*  600 */         this.mProgressDialog.setCancelable(true);
/*      */       } 
/*  602 */       this.mProgressDialog.setOnDismissListener(new DialogInterface.OnDismissListener()
/*      */           {
/*      */             public void onDismiss(DialogInterface dialog) {
/*  605 */               AddDocPagesTask.this.cancel(true);
/*      */             }
/*      */           });
/*      */       
/*  609 */       this.mCountDownTimer = new CountDownTimer(250L, 251L)
/*      */         {
/*      */           public void onTick(long millisUntilFinished) {}
/*      */ 
/*      */ 
/*      */           
/*      */           public void onFinish() {
/*  616 */             AddDocPagesTask.this.mProgressDialog.show();
/*      */           }
/*      */         };
/*      */     }
/*      */     
/*      */     protected void onPreExecute() {
/*      */       int pageCount;
/*  623 */       Context context = getContext();
/*  624 */       if (context == null) {
/*      */         return;
/*      */       }
/*  627 */       boolean toastNeeded = true;
/*      */       
/*  629 */       this.mCountDownTimer.start();
/*      */ 
/*      */ 
/*      */       
/*  633 */       boolean shouldUnlockRead = false;
/*      */       try {
/*  635 */         ThumbnailsViewAdapter.this.mPdfViewCtrl.docLockRead();
/*  636 */         shouldUnlockRead = true;
/*  637 */         PDFDoc doc = ThumbnailsViewAdapter.this.mPdfViewCtrl.getDoc();
/*  638 */         if (doc == null) {
/*      */           return;
/*      */         }
/*  641 */         pageCount = doc.getPageCount();
/*  642 */       } catch (Exception ex) {
/*  643 */         AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*  644 */         cancel(true);
/*      */         return;
/*      */       } finally {
/*  647 */         if (shouldUnlockRead) {
/*  648 */           ThumbnailsViewAdapter.this.mPdfViewCtrl.docUnlockRead();
/*      */         }
/*      */       } 
/*  651 */       if (this.mPosition < 0) {
/*      */         
/*  653 */         this.mNewPageNum = pageCount + 1;
/*      */       } else {
/*  655 */         this.mNewPageNum = this.mPosition + 1;
/*      */       } 
/*  657 */       this.mInsert = (this.mNewPageNum <= pageCount);
/*      */ 
/*      */       
/*  660 */       this.mDocTemp = null;
/*  661 */       SecondaryFileFilter filter = null;
/*  662 */       if (this.mDocumentFormat == DocumentFormat.PDF_DOC || this.mDocumentFormat == DocumentFormat.IMAGE) {
/*  663 */         boolean canAdd, shouldUnlock = false;
/*      */         
/*      */         try {
/*  666 */           ContentResolver cr = Utils.getContentResolver(context);
/*  667 */           if (cr != null && Utils.isNotPdf(cr, (Uri)this.mData)) {
/*  668 */             this.mIsNotPdf = true;
/*      */             
/*      */             return;
/*      */           } 
/*  672 */           filter = new SecondaryFileFilter(context, (Uri)this.mData);
/*  673 */           this.mDocTemp = new PDFDoc((Filter)filter);
/*      */           
/*  675 */           canAdd = true;
/*  676 */           this.mDocTemp.lock();
/*  677 */           shouldUnlock = true;
/*      */ 
/*      */           
/*  680 */           if (!this.mDocTemp.initSecurityHandler() && (
/*  681 */             this.mPassword == null || !this.mDocTemp.initStdSecurityHandler(this.mPassword))) {
/*  682 */             canAdd = false;
/*  683 */             toastNeeded = false;
/*  684 */             ThumbnailsViewAdapter.this.mPwdRequestLastPage = this.mPosition;
/*  685 */             ThumbnailsViewAdapter.this.mPwdRequestUri = (Uri)this.mData;
/*      */             
/*  687 */             PasswordDialogFragment passwordDialog = PasswordDialogFragment.newInstance(0, (File)null, ((Uri)this.mData).getEncodedPath(), "");
/*  688 */             passwordDialog.setListener(ThumbnailsViewAdapter.this);
/*  689 */             passwordDialog.setMessage(R.string.dialog_password_message);
/*  690 */             passwordDialog.show(ThumbnailsViewAdapter.this.mFragmentManager, "password_dialog");
/*      */           
/*      */           }
/*      */           else {
/*      */ 
/*      */             
/*  696 */             Obj needsRenderingObj = this.mDocTemp.getRoot().findObj("NeedsRendering");
/*  697 */             if (needsRenderingObj != null && needsRenderingObj.isBool() && needsRenderingObj.getBool()) {
/*  698 */               canAdd = false;
/*  699 */               toastNeeded = false;
/*  700 */               Utils.showAlertDialogWithLink(context, context.getString(R.string.error_has_xfa_forms_message), "");
/*      */             
/*      */             }
/*      */             else {
/*      */               
/*  705 */               Obj collectionObj = this.mDocTemp.getRoot().findObj("Collection");
/*  706 */               if (collectionObj != null) {
/*  707 */                 canAdd = false;
/*  708 */                 toastNeeded = false;
/*  709 */                 Utils.showAlertDialogWithLink(context, context.getString(R.string.error_has_portfolio_message), "");
/*      */               } 
/*      */             } 
/*      */           } 
/*  713 */         } catch (Exception e) {
/*  714 */           canAdd = false;
/*      */         } finally {
/*  716 */           if (shouldUnlock) {
/*  717 */             Utils.unlockQuietly(this.mDocTemp);
/*      */           }
/*      */           
/*  720 */           if (this.mDocTemp == null) {
/*  721 */             Utils.closeQuietly(filter);
/*      */           }
/*      */         } 
/*      */         
/*  725 */         if (!canAdd) {
/*  726 */           this.mDocTemp = null;
/*  727 */           if (toastNeeded) {
/*  728 */             CommonToast.showText(context, context.getResources().getString(R.string.dialog_add_pdf_document_error_message), 0);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected Void doInBackground(Void... args) {
/*  737 */       if (isCancelled()) {
/*  738 */         return null;
/*      */       }
/*      */       
/*  741 */       boolean shouldUnlockDocTemp = false;
/*  742 */       boolean shouldUnlock = false;
/*  743 */       SecondaryFileFilter filter = null; try {
/*      */         Page page; Rect pageRect;
/*  745 */         PDFDoc doc = ThumbnailsViewAdapter.this.mPdfViewCtrl.getDoc();
/*  746 */         if (doc == null) {
/*  747 */           return null;
/*      */         }
/*      */         
/*  750 */         ThumbnailsViewAdapter.this.mPdfViewCtrl.docLock(true);
/*  751 */         shouldUnlock = true;
/*      */ 
/*      */         
/*  754 */         switch (this.mDocumentFormat) {
/*      */           case PDF_PAGE:
/*  756 */             if (this.mData != null && (this.mData instanceof Page || this.mData instanceof Page[])) {
/*      */               Page[] pages;
/*  758 */               if (this.mData instanceof Page[]) {
/*  759 */                 pages = (Page[])this.mData;
/*      */               } else {
/*  761 */                 pages = new Page[1];
/*  762 */                 pages[0] = (Page)this.mData;
/*      */               } 
/*      */               
/*  765 */               for (Page p : pages) {
/*  766 */                 if (isCancelled()) {
/*  767 */                   return null;
/*      */                 }
/*  769 */                 if (this.mInsert) {
/*  770 */                   PageIterator pageIterator = doc.getPageIterator(this.mNewPageNum);
/*  771 */                   doc.pageInsert(pageIterator, p);
/*      */                 } else {
/*  773 */                   doc.pagePushBack(p);
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */             break;
/*      */ 
/*      */           
/*      */           case BLANK_PDF_PAGE:
/*  781 */             page = doc.pageCreate();
/*  782 */             pageRect = ThumbnailsViewAdapter.this.getPDFPageRect(this.mNewPageNum - 1);
/*      */             
/*  784 */             page.setMediaBox(pageRect);
/*  785 */             page.setCropBox(pageRect);
/*  786 */             if (this.mInsert) {
/*  787 */               PageIterator pageIterator = doc.getPageIterator(this.mNewPageNum);
/*  788 */               doc.pageInsert(pageIterator, page); break;
/*      */             } 
/*  790 */             doc.pagePushBack(page);
/*      */             break;
/*      */           
/*      */           case IMAGE:
/*      */           case PDF_DOC:
/*  795 */             if (this.mIsNotPdf) {
/*  796 */               ContentResolver cr = Utils.getContentResolver(getContext());
/*  797 */               if (cr == null) {
/*  798 */                 return null;
/*      */               }
/*  800 */               filter = new SecondaryFileFilter(getContext(), (Uri)this.mData);
/*  801 */               DocumentConversion conv = Convert.universalConversion((Filter)filter, null);
/*  802 */               while (conv.getConversionStatus() == 1) {
/*  803 */                 conv.convertNextPage();
/*  804 */                 if (isCancelled()) {
/*  805 */                   return null;
/*      */                 }
/*      */               } 
/*      */               
/*  809 */               if (conv.getConversionStatus() == 2 || conv.getConversionStatus() != 0) {
/*      */                 break;
/*      */               }
/*      */               
/*  813 */               this.mDocTemp = conv.getDoc();
/*      */             } 
/*      */             
/*  816 */             if (isCancelled()) {
/*  817 */               return null;
/*      */             }
/*      */             
/*  820 */             if (this.mDocTemp != null) {
/*  821 */               this.mDocTemp.lock();
/*  822 */               shouldUnlockDocTemp = true;
/*  823 */               doc.insertPages(this.mNewPageNum, this.mDocTemp, 1, this.mDocTemp.getPageCount(), PDFDoc.InsertBookmarkMode.INSERT, null);
/*      */             } 
/*      */             break;
/*      */         } 
/*  827 */       } catch (Exception e) {
/*  828 */         AnalyticsHandlerAdapter.getInstance().sendException(e, "AddDocPagesTask");
/*  829 */         return null;
/*      */       } finally {
/*  831 */         if (shouldUnlock) {
/*  832 */           ThumbnailsViewAdapter.this.mPdfViewCtrl.docUnlock();
/*      */         }
/*  834 */         if (shouldUnlockDocTemp) {
/*  835 */           Utils.unlockQuietly(this.mDocTemp);
/*      */         }
/*  837 */         Utils.closeQuietly(this.mDocTemp, filter);
/*      */       } 
/*      */       
/*  840 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void onCancelled() {
/*  845 */       this.mCountDownTimer.cancel();
/*  846 */       if (this.mProgressDialog != null && this.mProgressDialog.isShowing()) {
/*  847 */         this.mProgressDialog.dismiss();
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     protected void onPostExecute(Void arg) {
/*  853 */       Context context = getContext();
/*  854 */       if (context == null) {
/*      */         return;
/*      */       }
/*  857 */       this.mCountDownTimer.cancel();
/*  858 */       if (this.mProgressDialog != null && this.mProgressDialog.isShowing()) {
/*  859 */         this.mProgressDialog.dismiss();
/*      */       }
/*      */       
/*  862 */       if (!isCancelled()) {
/*      */         int pageCount;
/*  864 */         boolean shouldUnlockRead = false;
/*      */         try {
/*  866 */           ThumbnailsViewAdapter.this.mPdfViewCtrl.docLockRead();
/*  867 */           shouldUnlockRead = true;
/*  868 */           PDFDoc doc = ThumbnailsViewAdapter.this.mPdfViewCtrl.getDoc();
/*  869 */           if (doc == null) {
/*      */             return;
/*      */           }
/*  872 */           pageCount = doc.getPageCount();
/*  873 */         } catch (Exception ex) {
/*  874 */           AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*  875 */           CommonToast.showText(context, context.getResources().getString(R.string.dialog_add_pdf_document_error_message), 0);
/*      */           return;
/*      */         } finally {
/*  878 */           if (shouldUnlockRead) {
/*  879 */             ThumbnailsViewAdapter.this.mPdfViewCtrl.docUnlockRead();
/*      */           }
/*      */         } 
/*  882 */         int pageAddedCnt = pageCount - ThumbnailsViewAdapter.this.mDataList.size();
/*      */         
/*  884 */         ThumbnailsViewAdapter.this.clear();
/*  885 */         for (int pageNum = 1; pageNum <= pageCount; pageNum++) {
/*  886 */           ThumbnailsViewAdapter.this.add(Integer.valueOf(pageNum));
/*      */         }
/*      */         
/*  889 */         Utils.safeNotifyDataSetChanged((RecyclerView.Adapter)ThumbnailsViewAdapter.this);
/*  890 */         ThumbnailsViewAdapter.this.safeScrollToPosition(this.mNewPageNum - 1);
/*      */         
/*  892 */         List<Integer> pageList = new ArrayList<>(pageAddedCnt);
/*  893 */         for (int i = 0; i < pageAddedCnt; i++) {
/*  894 */           pageList.add(Integer.valueOf(this.mNewPageNum + i));
/*      */         }
/*      */         
/*  897 */         if (ThumbnailsViewAdapter.this.mEditPageListener != null)
/*  898 */           ThumbnailsViewAdapter.this.mEditPageListener.onPagesAdded(pageList); 
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private class DuplicateDocPagesTask
/*      */     extends CustomAsyncTask<Void, Void, Void>
/*      */   {
/*      */     private static final int MIN_DELAY = 250;
/*      */     private ProgressDialog mProgressDialog;
/*      */     private CountDownTimer mCountDownTimer;
/*      */     private List<Integer> mPageList;
/*  910 */     private int mNewPageNum = 1;
/*      */     
/*      */     DuplicateDocPagesTask(Context context, List<Integer> pageList) {
/*  913 */       super(context);
/*  914 */       this.mPageList = pageList;
/*      */       
/*  916 */       this.mProgressDialog = new ProgressDialog(context);
/*  917 */       this.mProgressDialog.setIndeterminate(true);
/*  918 */       this.mProgressDialog.setMessage(context.getResources().getString(R.string.add_pdf_wait));
/*  919 */       this.mProgressDialog.setCancelable(true);
/*      */       
/*  921 */       this.mProgressDialog.setOnDismissListener(new DialogInterface.OnDismissListener()
/*      */           {
/*      */             public void onDismiss(DialogInterface dialog) {
/*  924 */               DuplicateDocPagesTask.this.cancel(true);
/*      */             }
/*      */           });
/*      */       
/*  928 */       this.mCountDownTimer = new CountDownTimer(250L, 251L)
/*      */         {
/*      */           public void onTick(long millisUntilFinished) {}
/*      */ 
/*      */ 
/*      */           
/*      */           public void onFinish() {
/*  935 */             DuplicateDocPagesTask.this.mProgressDialog.show();
/*      */           }
/*      */         };
/*      */     }
/*      */ 
/*      */     
/*      */     protected void onPreExecute() {
/*  942 */       this.mCountDownTimer.start();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected Void doInBackground(Void... args) {
/*  948 */       if (isCancelled()) {
/*  949 */         return null;
/*      */       }
/*      */       
/*  952 */       boolean shouldUnlock = false;
/*      */       try {
/*  954 */         PDFDoc doc = ThumbnailsViewAdapter.this.mPdfViewCtrl.getDoc();
/*  955 */         if (doc == null) {
/*  956 */           return null;
/*      */         }
/*  958 */         ThumbnailsViewAdapter.this.mPdfViewCtrl.docLock(true);
/*  959 */         shouldUnlock = true;
/*      */ 
/*      */         
/*  962 */         Collections.sort(this.mPageList, Collections.reverseOrder());
/*  963 */         int lastSelectedPage = ((Integer)this.mPageList.get(0)).intValue();
/*  964 */         this.mNewPageNum = lastSelectedPage + 1;
/*  965 */         int count = this.mPageList.size();
/*  966 */         for (int i = 0; i < count && 
/*  967 */           !isCancelled(); i++) {
/*      */ 
/*      */           
/*  970 */           Page page = doc.getPage(((Integer)this.mPageList.get(i)).intValue());
/*  971 */           PageIterator pageIterator = doc.getPageIterator(this.mNewPageNum);
/*  972 */           doc.pageInsert(pageIterator, page);
/*      */         } 
/*  974 */       } catch (Exception e) {
/*  975 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*  976 */         return null;
/*      */       } finally {
/*  978 */         if (shouldUnlock) {
/*  979 */           ThumbnailsViewAdapter.this.mPdfViewCtrl.docUnlock();
/*      */         }
/*      */       } 
/*      */       
/*  983 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void onCancelled() {
/*  988 */       this.mCountDownTimer.cancel();
/*  989 */       if (this.mProgressDialog != null && this.mProgressDialog.isShowing()) {
/*  990 */         this.mProgressDialog.dismiss();
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     protected void onPostExecute(Void arg) {
/*  996 */       Context context = getContext();
/*  997 */       if (context == null) {
/*      */         return;
/*      */       }
/*      */       
/* 1001 */       this.mCountDownTimer.cancel();
/* 1002 */       if (this.mProgressDialog != null && this.mProgressDialog.isShowing()) {
/* 1003 */         this.mProgressDialog.dismiss();
/*      */       }
/*      */       
/* 1006 */       if (!isCancelled()) {
/*      */         int pageCount;
/* 1008 */         boolean shouldUnlockRead = false;
/*      */         try {
/* 1010 */           ThumbnailsViewAdapter.this.mPdfViewCtrl.docLockRead();
/* 1011 */           shouldUnlockRead = true;
/* 1012 */           PDFDoc doc = ThumbnailsViewAdapter.this.mPdfViewCtrl.getDoc();
/* 1013 */           if (doc == null) {
/*      */             return;
/*      */           }
/* 1016 */           pageCount = doc.getPageCount();
/* 1017 */         } catch (Exception ex) {
/* 1018 */           AnalyticsHandlerAdapter.getInstance().sendException(ex);
/* 1019 */           CommonToast.showText(context, context.getResources().getString(R.string.dialog_add_pdf_document_error_message), 0);
/*      */           return;
/*      */         } finally {
/* 1022 */           if (shouldUnlockRead) {
/* 1023 */             ThumbnailsViewAdapter.this.mPdfViewCtrl.docUnlockRead();
/*      */           }
/*      */         } 
/* 1026 */         int pageAddedCnt = pageCount - ThumbnailsViewAdapter.this.mDataList.size();
/*      */         
/* 1028 */         ThumbnailsViewAdapter.this.clear();
/* 1029 */         for (int pageNum = 1; pageNum <= pageCount; pageNum++) {
/* 1030 */           ThumbnailsViewAdapter.this.add(Integer.valueOf(pageNum));
/*      */         }
/*      */         
/* 1033 */         Utils.safeNotifyDataSetChanged((RecyclerView.Adapter)ThumbnailsViewAdapter.this);
/* 1034 */         ThumbnailsViewAdapter.this.safeScrollToPosition(this.mNewPageNum - 1);
/*      */         
/* 1036 */         List<Integer> pageList = new ArrayList<>(pageAddedCnt);
/* 1037 */         for (int i = 0; i < pageAddedCnt; i++) {
/* 1038 */           pageList.add(Integer.valueOf(this.mNewPageNum + i));
/*      */         }
/*      */         
/* 1041 */         if (ThumbnailsViewAdapter.this.mEditPageListener != null) {
/* 1042 */           ThumbnailsViewAdapter.this.mEditPageListener.onPagesAdded(pageList);
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
/*      */   public void updateAfterAddition(List<Integer> pageList) {
/* 1054 */     int pageCount, pageNum = ((Integer)Collections.<Integer>min(pageList)).intValue();
/* 1055 */     this.mDocPagesModified = true;
/*      */     
/* 1057 */     boolean shouldUnlockRead = false;
/*      */     try {
/* 1059 */       this.mPdfViewCtrl.docLockRead();
/* 1060 */       shouldUnlockRead = true;
/* 1061 */       PDFDoc doc = this.mPdfViewCtrl.getDoc();
/* 1062 */       if (doc == null) {
/*      */         return;
/*      */       }
/* 1065 */       pageCount = doc.getPageCount();
/* 1066 */     } catch (Exception ex) {
/* 1067 */       AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*      */       return;
/*      */     } finally {
/* 1070 */       if (shouldUnlockRead) {
/* 1071 */         this.mPdfViewCtrl.docUnlockRead();
/*      */       }
/*      */     } 
/*      */     
/*      */     try {
/* 1076 */       clear();
/* 1077 */       for (int p = 1; p <= pageCount; p++) {
/* 1078 */         add(Integer.valueOf(p));
/*      */       }
/* 1080 */     } catch (Exception exception) {}
/*      */ 
/*      */     
/* 1083 */     Utils.safeNotifyDataSetChanged((RecyclerView.Adapter)this);
/* 1084 */     safeScrollToPosition(pageNum - 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateAfterDeletion(List<Integer> pageList) {
/* 1093 */     if (pageList == null || pageList.size() == 0) {
/*      */       return;
/*      */     }
/*      */     
/* 1097 */     this.mDocPagesModified = true;
/* 1098 */     this.mCurrentPage -= pageList.size();
/*      */     
/* 1100 */     Collections.sort(pageList);
/*      */ 
/*      */     
/* 1103 */     ListIterator<Integer> it = this.mDataList.listIterator();
/* 1104 */     int deleteCnt = 0;
/*      */     
/* 1106 */     SparseArray<File> tempList = new SparseArray();
/* 1107 */     while (it.hasNext()) {
/* 1108 */       Integer item = it.next();
/* 1109 */       Integer pageNum = item;
/*      */       try {
/* 1111 */         if (Collections.binarySearch((List)pageList, pageNum) >= 0) {
/* 1112 */           it.remove();
/*      */ 
/*      */           
/* 1115 */           removeCachedPage(pageNum.intValue());
/*      */           
/* 1117 */           deleteCnt++; continue;
/*      */         } 
/* 1119 */         File tmp = this.mThumbFileMap.get(pageNum);
/* 1120 */         int newPageNum = pageNum.intValue() - deleteCnt;
/* 1121 */         setItem(it.previousIndex(), newPageNum);
/* 1122 */         if (tmp != null) {
/* 1123 */           tempList.put(newPageNum, tmp);
/*      */         }
/*      */       }
/* 1126 */       catch (Exception exception) {}
/*      */     } 
/*      */     
/* 1129 */     for (int i = 0; i < tempList.size(); i++) {
/* 1130 */       int key = tempList.keyAt(i);
/* 1131 */       File file = (File)tempList.get(key);
/* 1132 */       this.mThumbFileMap.put(Integer.valueOf(key), file);
/*      */     } 
/*      */     
/* 1135 */     Utils.safeNotifyDataSetChanged((RecyclerView.Adapter)this);
/*      */ 
/*      */     
/* 1138 */     int firstPageChanged = ((Integer)Collections.<Integer>min(pageList)).intValue();
/* 1139 */     if (firstPageChanged == this.mDataList.size()) {
/* 1140 */       firstPageChanged--;
/*      */     }
/* 1142 */     safeScrollToPosition(firstPageChanged - 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateAfterRotation(List<Integer> pageList) {
/* 1151 */     if (pageList == null || pageList.size() == 0) {
/*      */       return;
/*      */     }
/*      */     
/* 1155 */     this.mDocPagesModified = true;
/*      */     
/* 1157 */     Collections.sort(pageList);
/*      */ 
/*      */     
/* 1160 */     ListIterator<Integer> it = this.mDataList.listIterator();
/* 1161 */     Integer pageNum = Integer.valueOf(1);
/* 1162 */     while (it.hasNext()) {
/* 1163 */       Integer item = it.next();
/* 1164 */       pageNum = item;
/*      */       try {
/* 1166 */         if (Collections.binarySearch((List)pageList, pageNum) >= 0)
/*      */         {
/* 1168 */           removeCachedPage(pageNum.intValue());
/*      */         }
/* 1170 */       } catch (Exception exception) {}
/*      */     } 
/*      */ 
/*      */     
/* 1174 */     Utils.safeNotifyDataSetChanged((RecyclerView.Adapter)this);
/*      */     
/* 1176 */     safeScrollToPosition(((Integer)pageList.get(0)).intValue() - 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateAfterPageLabelEdit() {
/* 1183 */     Utils.safeNotifyDataSetChanged((RecyclerView.Adapter)this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateAfterMove(int fromPageNum, int toPageNum) {
/*      */     try {
/* 1194 */       int start = Math.min(fromPageNum - 1, toPageNum - 1);
/* 1195 */       int end = Math.max(fromPageNum - 1, toPageNum - 1);
/* 1196 */       if (start >= 0 && end < getItemCount() && start != end) {
/*      */         
/* 1198 */         if (fromPageNum > toPageNum) {
/* 1199 */           Integer itemMap = getItem(end);
/* 1200 */           if (itemMap != null) {
/* 1201 */             this.mDataLock.lock();
/* 1202 */             boolean currentPageUpdated = false;
/* 1203 */             SparseArray<File> tempList = new SparseArray(); int i;
/* 1204 */             for (i = start; i <= end; i++) {
/* 1205 */               itemMap = getItem(i);
/* 1206 */               if (itemMap == null) {
/*      */                 break;
/*      */               }
/* 1209 */               int oldPageNum = itemMap.intValue();
/* 1210 */               int newPageNum = i + 1;
/* 1211 */               if (!currentPageUpdated && oldPageNum == this.mCurrentPage) {
/*      */                 
/* 1213 */                 this.mCurrentPage = newPageNum;
/* 1214 */                 currentPageUpdated = true;
/*      */               } 
/* 1216 */               File tmp = this.mThumbFileMap.get(Integer.valueOf(oldPageNum));
/* 1217 */               setItem(i, newPageNum);
/* 1218 */               if (tmp != null) {
/* 1219 */                 tempList.put(newPageNum, tmp);
/*      */               }
/*      */             } 
/* 1222 */             for (i = 0; i < tempList.size(); i++) {
/* 1223 */               int key = tempList.keyAt(i);
/* 1224 */               File file = (File)tempList.get(key);
/* 1225 */               this.mThumbFileMap.put(Integer.valueOf(key), file);
/*      */             } 
/* 1227 */             this.mDataLock.unlock();
/*      */           } 
/*      */         } else {
/* 1230 */           Integer itemMap = getItem(start);
/* 1231 */           if (itemMap != null) {
/* 1232 */             this.mDataLock.lock();
/* 1233 */             boolean currentPageUpdated = false;
/* 1234 */             SparseArray<File> tempList = new SparseArray(); int i;
/* 1235 */             for (i = end; i >= start; i--) {
/* 1236 */               itemMap = getItem(i);
/* 1237 */               if (itemMap == null) {
/*      */                 break;
/*      */               }
/* 1240 */               int oldPageNum = itemMap.intValue();
/* 1241 */               int newPageNum = i + 1;
/* 1242 */               if (!currentPageUpdated && oldPageNum == this.mCurrentPage) {
/*      */                 
/* 1244 */                 this.mCurrentPage = newPageNum;
/* 1245 */                 currentPageUpdated = true;
/*      */               } 
/* 1247 */               File tmp = this.mThumbFileMap.get(Integer.valueOf(oldPageNum));
/* 1248 */               setItem(i, newPageNum);
/* 1249 */               if (tmp != null) {
/* 1250 */                 tempList.put(newPageNum, tmp);
/*      */               }
/*      */             } 
/* 1253 */             for (i = 0; i < tempList.size(); i++) {
/* 1254 */               int key = tempList.keyAt(i);
/* 1255 */               File file = (File)tempList.get(key);
/* 1256 */               this.mThumbFileMap.put(Integer.valueOf(key), file);
/*      */             } 
/* 1258 */             this.mDataLock.unlock();
/*      */           } 
/*      */         } 
/*      */         
/* 1262 */         Utils.safeNotifyDataSetChanged((RecyclerView.Adapter)this);
/* 1263 */         safeScrollToPosition(toPageNum - 1);
/*      */       } 
/* 1265 */     } catch (Exception e) {
/* 1266 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void safeScrollToPosition(int scrollIndex) {
/* 1271 */     RecyclerView recyclerView = getRecyclerView();
/* 1272 */     if (recyclerView != null) {
/*      */       boolean scrollToPosition;
/* 1274 */       if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
/* 1275 */         LinearLayoutManager layoutManager = (LinearLayoutManager)recyclerView.getLayoutManager();
/* 1276 */         int firstVisibleIndex = layoutManager.findFirstVisibleItemPosition();
/* 1277 */         int lastVisibleIndex = layoutManager.findLastVisibleItemPosition();
/* 1278 */         scrollToPosition = (scrollIndex < firstVisibleIndex || scrollIndex > lastVisibleIndex);
/*      */       } else {
/* 1280 */         scrollToPosition = true;
/*      */       } 
/* 1282 */       if (scrollToPosition)
/*      */       {
/* 1284 */         recyclerView.scrollToPosition(scrollIndex);
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
/*      */   public void addDocPages(int position, DocumentFormat documentFormat, Object data) {
/* 1298 */     this.mDocPagesModified = true;
/* 1299 */     (new AddDocPagesTask(this.mContext, position, documentFormat, data, null)).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Object[])new Void[0]);
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
/*      */   public void addDocPages(int position, DocumentFormat documentFormat, Object data, String password) {
/* 1312 */     this.mDocPagesModified = true;
/* 1313 */     (new AddDocPagesTask(this.mContext, position, documentFormat, data, password)).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Object[])new Void[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void duplicateDocPages(List<Integer> pageList) {
/* 1322 */     this.mDocPagesModified = true;
/* 1323 */     (new DuplicateDocPagesTask(this.mContext, pageList)).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Object[])new Void[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeDocPage(int pageNum) {
/* 1332 */     this.mDocPagesModified = true;
/*      */     
/* 1334 */     boolean shouldUnlock = false;
/*      */     try {
/* 1336 */       PDFDoc doc = this.mPdfViewCtrl.getDoc();
/* 1337 */       if (doc == null) {
/*      */         return;
/*      */       }
/*      */       
/* 1341 */       this.mPdfViewCtrl.docLock(true);
/* 1342 */       shouldUnlock = true;
/*      */       
/* 1344 */       Page pageToDelete = doc.getPage(pageNum);
/* 1345 */       PageIterator pageIterator = doc.getPageIterator(pageNum);
/* 1346 */       doc.pageRemove(pageIterator);
/*      */       
/* 1348 */       removeUserBookmarks(Long.valueOf(pageToDelete.getSDFObj().getObjNum()), pageNum, doc.getPageCount());
/* 1349 */     } catch (Exception e) {
/* 1350 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } finally {
/* 1352 */       if (shouldUnlock) {
/* 1353 */         this.mPdfViewCtrl.docUnlock();
/*      */       }
/*      */     } 
/*      */     
/* 1357 */     int position = updatePageNumberOnDelete(pageNum);
/* 1358 */     if (position >= 0) {
/* 1359 */       notifyItemRemoved(position);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void rotateDocPage(int pageNum) {
/* 1369 */     this.mDocPagesModified = true;
/* 1370 */     boolean shouldUnlock = false;
/*      */     try {
/* 1372 */       PDFDoc doc = this.mPdfViewCtrl.getDoc();
/* 1373 */       if (doc == null) {
/*      */         return;
/*      */       }
/* 1376 */       this.mPdfViewCtrl.docLock(true);
/* 1377 */       shouldUnlock = true;
/* 1378 */       Page pageToRotate = doc.getPage(pageNum);
/* 1379 */       int pageRotation = (pageToRotate.getRotation() + 1) % 4;
/* 1380 */       pageToRotate.setRotation(pageRotation);
/* 1381 */     } catch (Exception e) {
/* 1382 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } finally {
/* 1384 */       if (shouldUnlock) {
/* 1385 */         this.mPdfViewCtrl.docUnlock();
/*      */       }
/*      */     } 
/*      */     
/* 1389 */     int position = getPositionForPage(pageNum);
/* 1390 */     if (position < 0)
/*      */     {
/* 1392 */       position = pageNum - 1;
/*      */     }
/*      */     
/* 1395 */     removeCachedPage(pageNum);
/*      */     
/* 1397 */     Utils.safeNotifyItemChanged((RecyclerView.Adapter)this, position);
/*      */   }
/*      */   
/*      */   private Rect getPDFPageRect(int pageNum) throws PDFNetException {
/* 1401 */     PDFDoc doc = this.mPdfViewCtrl.getDoc();
/* 1402 */     if (doc == null) {
/* 1403 */       return new Rect(0.0D, 0.0D, 0.0D, 0.0D);
/*      */     }
/* 1405 */     Page page = doc.getPage(pageNum);
/* 1406 */     double width = page.getPageWidth();
/* 1407 */     double height = page.getPageHeight();
/* 1408 */     return new Rect(0.0D, 0.0D, width, height);
/*      */   }
/*      */   private void updateUserBookmarks(int from, int to, Long pageToMoveObjNum, Long destPageObjNum) {
/*      */     try {
/*      */       ToolManager toolManager;
/* 1413 */       PDFDoc doc = this.mPdfViewCtrl.getDoc();
/* 1414 */       if (doc == null) {
/*      */         return;
/*      */       }
/* 1417 */       String filepath = doc.getFileName();
/*      */       
/*      */       try {
/* 1420 */         toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 1421 */       } catch (Exception e) {
/* 1422 */         toolManager = null;
/*      */       } 
/* 1424 */       if (toolManager != null && !toolManager.isReadOnly()) {
/* 1425 */         BookmarkManager.onPageMoved(this.mPdfViewCtrl, pageToMoveObjNum.longValue(), destPageObjNum.longValue(), to, false);
/*      */       } else {
/* 1427 */         BookmarkManager.onPageMoved(this.mContext, filepath, pageToMoveObjNum.longValue(), destPageObjNum.longValue(), from, to);
/*      */       } 
/* 1429 */     } catch (PDFNetException pDFNetException) {}
/*      */   }
/*      */   
/*      */   private void removeUserBookmarks(Long objNum, int pageNum, int pageCount) {
/*      */     try {
/*      */       ToolManager toolManager;
/* 1435 */       PDFDoc doc = this.mPdfViewCtrl.getDoc();
/* 1436 */       if (doc == null) {
/*      */         return;
/*      */       }
/* 1439 */       String filepath = doc.getFileName();
/*      */       
/*      */       try {
/* 1442 */         toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 1443 */       } catch (Exception e) {
/* 1444 */         toolManager = null;
/*      */       } 
/* 1446 */       if (toolManager != null && !toolManager.isReadOnly()) {
/* 1447 */         BookmarkManager.onPageDeleted(this.mPdfViewCtrl, objNum);
/*      */       } else {
/* 1449 */         BookmarkManager.onPageDeleted(this.mContext, filepath, objNum, pageNum, pageCount);
/*      */       } 
/* 1451 */     } catch (PDFNetException pDFNetException) {}
/*      */   }
/*      */ 
/*      */   
/*      */   private class LoadThumbnailTask
/*      */     extends AsyncTask<Void, Void, Bitmap>
/*      */   {
/*      */     private final int mPosition;
/*      */     
/*      */     private final int mPage;
/*      */     private int mWidth;
/*      */     private int mHeight;
/*      */     private int[] mBuffer;
/*      */     
/*      */     LoadThumbnailTask(int position, int page, int[] buffer, int width, int height) {
/* 1466 */       this.mPage = page;
/* 1467 */       this.mPosition = position;
/* 1468 */       this.mBuffer = buffer;
/* 1469 */       this.mWidth = width;
/* 1470 */       this.mHeight = height;
/*      */     }
/*      */ 
/*      */     
/*      */     protected Bitmap doInBackground(Void... voids) {
/* 1475 */       Bitmap bitmap = null;
/*      */ 
/*      */       
/* 1478 */       synchronized (ThumbnailsViewAdapter.this.mPauseWorkLock) {
/* 1479 */         while (ThumbnailsViewAdapter.this.mPauseWork && !isCancelled()) {
/*      */           try {
/* 1481 */             if (ThumbnailsViewAdapter.sDebug)
/* 1482 */               Log.d(ThumbnailsViewAdapter.TAG, "doInBackground - paused for page: " + Integer.toString(this.mPage)); 
/* 1483 */             ThumbnailsViewAdapter.this.mPauseWorkLock.wait();
/* 1484 */           } catch (InterruptedException e) {
/* 1485 */             e.printStackTrace();
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*      */       try {
/* 1491 */         if (this.mBuffer != null && this.mBuffer.length > 0) {
/* 1492 */           bitmap = Bitmap.createBitmap(this.mWidth, this.mHeight, Bitmap.Config.ARGB_8888);
/* 1493 */           bitmap.setPixels(this.mBuffer, 0, this.mWidth, 0, 0, this.mWidth, this.mHeight);
/*      */           
/* 1495 */           FileOutputStream fos = null;
/*      */           try {
/* 1497 */             File prev = (File)ThumbnailsViewAdapter.this.mThumbFileMap.get(Integer.valueOf(this.mPage));
/* 1498 */             if (prev != null) {
/* 1499 */               prev.delete();
/* 1500 */               ThumbnailsViewAdapter.this.mThumbFileMap.remove(Integer.valueOf(this.mPage));
/*      */             } 
/* 1502 */             File tmp = File.createTempFile("tmp", ".png");
/* 1503 */             ThumbnailsViewAdapter.this.mThumbFiles.add(tmp);
/* 1504 */             if (ThumbnailsViewAdapter.sDebug)
/* 1505 */               Log.d(ThumbnailsViewAdapter.TAG, "create bitmap for page: " + this.mPage); 
/* 1506 */             fos = new FileOutputStream(tmp);
/* 1507 */             bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
/* 1508 */             ThumbnailsViewAdapter.this.mThumbFileMap.put(Integer.valueOf(this.mPage), tmp);
/* 1509 */           } catch (Exception e) {
/* 1510 */             AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */           } finally {
/* 1512 */             Utils.closeQuietly(fos);
/*      */           } 
/*      */           
/* 1515 */           if (ThumbnailsViewAdapter.sDebug) {
/* 1516 */             Log.d(ThumbnailsViewAdapter.TAG, "doInBackground - finished work for page: " + Integer.toString(this.mPage));
/*      */           }
/* 1518 */         } else if (ThumbnailsViewAdapter.sDebug) {
/* 1519 */           Log.d(ThumbnailsViewAdapter.TAG, "doInBackground - Buffer is empty for page: " + Integer.toString(this.mPage));
/*      */         } 
/* 1521 */       } catch (Exception e) {
/* 1522 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/* 1523 */       } catch (OutOfMemoryError oom) {
/* 1524 */         Utils.manageOOM(ThumbnailsViewAdapter.this.mPdfViewCtrl);
/*      */       } 
/*      */       
/* 1527 */       return bitmap;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void onPostExecute(Bitmap result) {
/* 1532 */       if (ThumbnailsViewAdapter.sDebug) Log.d(ThumbnailsViewAdapter.TAG, "onPostExecute " + Integer.toString(this.mPage)); 
/* 1533 */       if (isCancelled()) {
/* 1534 */         if (ThumbnailsViewAdapter.sDebug) Log.d(ThumbnailsViewAdapter.TAG, "onPostExecute cancelled"); 
/* 1535 */         ThumbnailsViewAdapter.this.mTaskList.remove(this.mPage);
/*      */         
/*      */         return;
/*      */       } 
/* 1539 */       if (result != null) {
/* 1540 */         if (ThumbnailsViewAdapter.sDebug)
/* 1541 */           Log.d(ThumbnailsViewAdapter.TAG, "onPostExecute - notify change for page: " + this.mPage + " at position: " + this.mPosition); 
/* 1542 */         Utils.safeNotifyItemChanged((RecyclerView.Adapter)ThumbnailsViewAdapter.this, this.mPosition);
/*      */       } 
/* 1544 */       ThumbnailsViewAdapter.this.mTaskList.remove(this.mPage);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void onCancelled(Bitmap value) {
/* 1549 */       if (ThumbnailsViewAdapter.sDebug) Log.d(ThumbnailsViewAdapter.TAG, "onCancelled " + Integer.toString(this.mPage)); 
/* 1550 */       synchronized (ThumbnailsViewAdapter.this.mPauseWorkLock) {
/* 1551 */         ThumbnailsViewAdapter.this.mPauseWorkLock.notifyAll();
/*      */       } 
/* 1553 */       ThumbnailsViewAdapter.this.mTaskList.remove(this.mPage);
/*      */     }
/*      */   }
/*      */   
/*      */   public int getPositionForPage(int pageNum) {
/* 1558 */     if (this.mDataList != null) {
/* 1559 */       return this.mDataList.indexOf(Integer.valueOf(pageNum));
/*      */     }
/* 1561 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int updatePageNumberOnDelete(int deletedPage) {
/* 1571 */     int deletedPosition = -1;
/*      */ 
/*      */     
/* 1574 */     if (deletedPage == this.mCurrentPage) {
/*      */       int pageCount;
/* 1576 */       boolean shouldUnlockRead = false;
/*      */       try {
/* 1578 */         this.mPdfViewCtrl.docLockRead();
/* 1579 */         shouldUnlockRead = true;
/* 1580 */         PDFDoc doc = this.mPdfViewCtrl.getDoc();
/* 1581 */         if (doc == null) {
/* 1582 */           return deletedPosition;
/*      */         }
/* 1584 */         pageCount = doc.getPageCount();
/* 1585 */       } catch (Exception ex) {
/* 1586 */         AnalyticsHandlerAdapter.getInstance().sendException(ex);
/* 1587 */         return deletedPosition;
/*      */       } finally {
/* 1589 */         if (shouldUnlockRead) {
/* 1590 */           this.mPdfViewCtrl.docUnlockRead();
/*      */         }
/*      */       } 
/* 1593 */       if (deletedPage >= pageCount) {
/* 1594 */         this.mCurrentPage--;
/*      */       }
/* 1596 */     } else if (this.mCurrentPage > deletedPage) {
/* 1597 */       this.mCurrentPage--;
/*      */     } 
/*      */ 
/*      */     
/* 1601 */     ListIterator<Integer> it = this.mDataList.listIterator();
/* 1602 */     while (it.hasNext()) {
/* 1603 */       Integer item = it.next();
/* 1604 */       int page = item.intValue();
/*      */       try {
/* 1606 */         if (page > deletedPage) {
/* 1607 */           setItem(it.previousIndex(), page - 1); continue;
/* 1608 */         }  if (page == deletedPage) {
/* 1609 */           deletedPosition = it.previousIndex();
/* 1610 */           it.remove();
/*      */         } 
/* 1612 */       } catch (Exception exception) {}
/*      */     } 
/*      */ 
/*      */     
/* 1616 */     removeCachedPage(deletedPage);
/*      */     
/* 1618 */     return deletedPosition;
/*      */   }
/*      */   
/*      */   private void removeCachedPage(int pageNum) {
/* 1622 */     File tmp = this.mThumbFileMap.get(Integer.valueOf(pageNum));
/* 1623 */     if (tmp != null) {
/* 1624 */       tmp.delete();
/* 1625 */       this.mThumbFileMap.remove(Integer.valueOf(pageNum));
/*      */     } 
/*      */   }
/*      */   
/*      */   static class PageViewHolder
/*      */     extends RecyclerView.ViewHolder {
/*      */     RelativeLayout imageLayout;
/*      */     ImageView thumbImage;
/*      */     TextView pageNumber;
/*      */     
/*      */     PageViewHolder(View itemView) {
/* 1636 */       super(itemView);
/*      */       
/* 1638 */       this.imageLayout = (RelativeLayout)itemView.findViewById(R.id.item_image_layout);
/* 1639 */       this.thumbImage = (ImageView)itemView.findViewById(R.id.item_image);
/* 1640 */       this.pageNumber = (TextView)itemView.findViewById(R.id.item_text);
/*      */     }
/*      */   }
/*      */   
/*      */   public static void setDebug(boolean debug) {
/* 1645 */     sDebug = debug;
/*      */   }
/*      */   
/*      */   public static interface EditPagesListener {
/*      */     void onPageMoved(int param1Int1, int param1Int2);
/*      */     
/*      */     void onPagesAdded(List<Integer> param1List);
/*      */   }
/*      */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\controls\ThumbnailsViewAdapter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */