/*     */ package com.pdftron.pdf.controls;
/*     */ 
/*     */ import android.app.AlertDialog;
/*     */ import android.content.Context;
/*     */ import android.content.DialogInterface;
/*     */ import android.os.AsyncTask;
/*     */ import android.os.Bundle;
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.Menu;
/*     */ import android.view.MenuItem;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.widget.PopupMenu;
/*     */ import android.widget.TextView;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.recyclerview.widget.ItemTouchHelper;
/*     */ import androidx.recyclerview.widget.RecyclerView;
/*     */ import co.paulburke.android.itemtouchhelperdemo.helper.ItemTouchHelperAdapter;
/*     */ import com.github.clans.fab.FloatingActionButton;
/*     */ import com.pdftron.pdf.Bookmark;
/*     */ import com.pdftron.pdf.PDFDoc;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.asynctask.PopulateUserBookmarkListTask;
/*     */ import com.pdftron.pdf.dialog.simplelist.EditListAdapter;
/*     */ import com.pdftron.pdf.dialog.simplelist.EditListItemTouchHelperCallback;
/*     */ import com.pdftron.pdf.dialog.simplelist.EditListViewHolder;
/*     */ import com.pdftron.pdf.model.UserBookmarkItem;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.tools.ToolManager;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.AnalyticsParam;
/*     */ import com.pdftron.pdf.utils.BookmarkManager;
/*     */ import com.pdftron.pdf.utils.CommonToast;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import com.pdftron.pdf.widget.recyclerview.ItemClickHelper;
/*     */ import com.pdftron.pdf.widget.recyclerview.SimpleRecyclerView;
/*     */ import com.pdftron.pdf.widget.recyclerview.ViewHolderBindListener;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public class UserBookmarkDialogFragment
/*     */   extends NavigationListDialogFragment
/*     */ {
/*  61 */   private static final String TAG = UserBookmarkDialogFragment.class.getName();
/*     */ 
/*     */   
/*     */   public static final String BUNDLE_FILE_PATH = "file_path";
/*     */ 
/*     */   
/*     */   public static final String BUNDLE_IS_READ_ONLY = "is_read_only";
/*     */ 
/*     */   
/*     */   private static final int CONTEXT_MENU_EDIT_ITEM = 0;
/*     */ 
/*     */   
/*     */   private static final int CONTEXT_MENU_DELETE_ITEM = 1;
/*     */   
/*     */   private static final int CONTEXT_MENU_DELETE_ALL = 2;
/*     */   
/*     */   private PopulateUserBookmarkListTask mTask;
/*     */   
/*  79 */   private ArrayList<UserBookmarkItem> mSource = new ArrayList<>();
/*     */ 
/*     */   
/*     */   private UserBookmarksAdapter mUserBookmarksAdapter;
/*     */ 
/*     */   
/*     */   private SimpleRecyclerView mRecyclerView;
/*     */ 
/*     */   
/*     */   private ItemTouchHelper mItemTouchHelper;
/*     */ 
/*     */   
/*     */   private EditListItemTouchHelperCallback mTouchCallback;
/*     */ 
/*     */   
/*     */   private FloatingActionButton mFab;
/*     */ 
/*     */   
/*     */   private PDFViewCtrl mPdfViewCtrl;
/*     */ 
/*     */   
/*     */   private boolean mReadOnly;
/*     */ 
/*     */   
/*     */   private String mFilePath;
/*     */   
/*     */   private boolean mModified;
/*     */   
/*     */   private boolean mRebuild;
/*     */   
/*     */   private UserBookmarkDialogListener mUserBookmarkDialogListener;
/*     */ 
/*     */   
/*     */   public static UserBookmarkDialogFragment newInstance() {
/* 113 */     return new UserBookmarkDialogFragment();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UserBookmarkDialogFragment setPdfViewCtrl(PDFViewCtrl pdfViewCtrl) {
/* 123 */     this.mPdfViewCtrl = pdfViewCtrl;
/* 124 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UserBookmarkDialogFragment setFilePath(String filePath) {
/* 134 */     Bundle args = getArguments();
/* 135 */     if (args == null) {
/* 136 */       args = new Bundle();
/*     */     }
/* 138 */     args.putString("file_path", filePath);
/* 139 */     setArguments(args);
/*     */     
/* 141 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UserBookmarkDialogFragment setReadOnly(boolean isReadOnly) {
/* 152 */     Bundle args = getArguments();
/* 153 */     if (args == null) {
/* 154 */       args = new Bundle();
/*     */     }
/* 156 */     args.putBoolean("is_read_only", isReadOnly);
/* 157 */     setArguments(args);
/*     */     
/* 159 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCreate(Bundle savedInstanceState) {
/* 167 */     super.onCreate(savedInstanceState);
/*     */     
/* 169 */     Bundle args = getArguments();
/* 170 */     if (args != null) {
/* 171 */       this.mReadOnly = args.getBoolean("is_read_only", false);
/* 172 */       this.mFilePath = args.getString("file_path", null);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
/* 181 */     View view = inflater.inflate(R.layout.controls_fragment_bookmark_dialog, null);
/*     */     
/* 183 */     this.mUserBookmarksAdapter = new UserBookmarksAdapter((Context)getActivity(), this.mSource, null);
/* 184 */     this.mRecyclerView = (SimpleRecyclerView)view.findViewById(R.id.controls_bookmark_recycler_view);
/* 185 */     this.mRecyclerView.initView(0, 0);
/*     */     
/* 187 */     this.mRecyclerView.setAdapter((RecyclerView.Adapter)this.mUserBookmarksAdapter);
/*     */     
/* 189 */     this.mFab = (FloatingActionButton)view.findViewById(R.id.control_bookmark_add);
/* 190 */     this.mFab.setOnClickListener(new View.OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/* 193 */             Context context = UserBookmarkDialogFragment.this.getContext();
/* 194 */             if (context == null || UserBookmarkDialogFragment.this.mPdfViewCtrl == null || UserBookmarkDialogFragment.this.mPdfViewCtrl.getDoc() == null) {
/*     */               return;
/*     */             }
/*     */             try {
/* 198 */               int curPageNum = UserBookmarkDialogFragment.this.mPdfViewCtrl.getCurrentPage();
/* 199 */               long curObjNum = UserBookmarkDialogFragment.this.mPdfViewCtrl.getDoc().getPage(curPageNum).getSDFObj().getObjNum();
/* 200 */               UserBookmarkItem item = new UserBookmarkItem(context, curObjNum, curPageNum);
/* 201 */               if (!UserBookmarkDialogFragment.this.mUserBookmarksAdapter.contains(item)) {
/* 202 */                 UserBookmarkDialogFragment.this.mUserBookmarksAdapter.add(item);
/* 203 */                 UserBookmarkDialogFragment.this.mModified = true;
/* 204 */                 UserBookmarkDialogFragment.this.mUserBookmarksAdapter.notifyItemInserted(UserBookmarkDialogFragment.this.mUserBookmarksAdapter.getItemCount() - 1);
/* 205 */                 UserBookmarkDialogFragment.this.mRecyclerView.smoothScrollToPosition(UserBookmarkDialogFragment.this.mUserBookmarksAdapter.getItemCount() - 1);
/*     */               } else {
/*     */                 
/* 208 */                 CommonToast.showText(UserBookmarkDialogFragment.this.getContext(), UserBookmarkDialogFragment.this.getContext().getResources().getString(R.string.controls_user_bookmark_dialog_bookmark_exist_warning), 0);
/*     */               } 
/* 210 */             } catch (Exception e) {
/* 211 */               AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */             } 
/* 213 */             UserBookmarkDialogFragment.this.onEventAction();
/* 214 */             AnalyticsHandlerAdapter.getInstance().sendEvent(34, 
/* 215 */                 AnalyticsParam.userBookmarksActionParam(1));
/*     */           }
/*     */         });
/*     */     
/* 219 */     ItemClickHelper itemClickHelper = new ItemClickHelper();
/* 220 */     itemClickHelper.attachToRecyclerView((RecyclerView)this.mRecyclerView);
/*     */ 
/*     */     
/* 223 */     this.mTouchCallback = new EditListItemTouchHelperCallback(this.mUserBookmarksAdapter, !this.mReadOnly, getResources().getColor(R.color.gray));
/* 224 */     this.mItemTouchHelper = new ItemTouchHelper((ItemTouchHelper.Callback)this.mTouchCallback);
/* 225 */     this.mItemTouchHelper.attachToRecyclerView((RecyclerView)this.mRecyclerView);
/*     */     
/* 227 */     itemClickHelper.setOnItemClickListener(new ItemClickHelper.OnItemClickListener()
/*     */         {
/*     */           public void onItemClick(RecyclerView recyclerView, View view, int position, long id) {
/* 230 */             if (UserBookmarkDialogFragment.this.mUserBookmarkDialogListener != null) {
/* 231 */               UserBookmarkItem item = UserBookmarkDialogFragment.this.mUserBookmarksAdapter.getItem(position);
/* 232 */               if (item == null) {
/*     */                 return;
/*     */               }
/* 235 */               UserBookmarkDialogFragment.this.mUserBookmarkDialogListener.onUserBookmarkClicked(item.pageNumber);
/* 236 */               UserBookmarkDialogFragment.this.onEventAction();
/* 237 */               AnalyticsHandlerAdapter.getInstance().sendEvent(30, 
/* 238 */                   AnalyticsParam.viewerNavigateByParam(2));
/*     */             } 
/*     */           }
/*     */         });
/*     */     
/* 243 */     itemClickHelper.setOnItemLongClickListener(new ItemClickHelper.OnItemLongClickListener()
/*     */         {
/*     */           public boolean onItemLongClick(RecyclerView recyclerView, View v, final int position, long id) {
/* 246 */             if (UserBookmarkDialogFragment.this.mReadOnly) {
/* 247 */               return true;
/*     */             }
/* 249 */             UserBookmarkDialogFragment.this.mRecyclerView.post(new Runnable()
/*     */                 {
/*     */                   public void run() {
/* 252 */                     UserBookmarkDialogFragment.this.mTouchCallback.setDragging(true);
/* 253 */                     RecyclerView.ViewHolder holder = UserBookmarkDialogFragment.this.mRecyclerView.findViewHolderForAdapterPosition(position);
/* 254 */                     if (holder != null) {
/* 255 */                       UserBookmarkDialogFragment.this.mItemTouchHelper.startDrag(holder);
/*     */                     }
/*     */                   }
/*     */                 });
/*     */             
/* 260 */             return true;
/*     */           }
/*     */         });
/*     */     
/* 264 */     return view;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onResume() {
/* 272 */     super.onResume();
/* 273 */     loadBookmarks();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPause() {
/* 281 */     commitData((Context)getActivity());
/*     */     
/* 283 */     super.onPause();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUserBookmarkListener(UserBookmarkDialogListener listener) {
/* 292 */     this.mUserBookmarkDialogListener = listener;
/*     */   }
/*     */   
/*     */   private void onShowPopupMenu(final int position, View anchor) {
/* 296 */     PopupMenu popupMenu = new PopupMenu((Context)getActivity(), anchor);
/* 297 */     Menu menu = popupMenu.getMenu();
/*     */     
/* 299 */     String[] menuItems = getResources().getStringArray(R.array.user_bookmark_dialog_context_menu);
/* 300 */     menu.add(0, 0, 0, menuItems[0]);
/* 301 */     menu.add(0, 1, 1, menuItems[1]);
/* 302 */     menu.add(0, 2, 2, menuItems[2]);
/* 303 */     MenuItem.OnMenuItemClickListener listener = new MenuItem.OnMenuItemClickListener()
/*     */       {
/*     */         public boolean onMenuItemClick(MenuItem item) {
/* 306 */           UserBookmarkDialogFragment.this.onPopupItemSelected(item, position);
/* 307 */           return true;
/*     */         }
/*     */       };
/* 310 */     menu.getItem(0).setOnMenuItemClickListener(listener);
/* 311 */     menu.getItem(1).setOnMenuItemClickListener(listener);
/* 312 */     menu.getItem(2).setOnMenuItemClickListener(listener);
/* 313 */     popupMenu.show();
/*     */   } private void onPopupItemSelected(MenuItem item, int position) {
/*     */     UserBookmarkItem userBookmarkItem;
/*     */     AlertDialog.Builder builder;
/* 317 */     if (this.mPdfViewCtrl == null) {
/*     */       return;
/*     */     }
/*     */     
/* 321 */     int menuItemIndex = item.getItemId();
/* 322 */     switch (menuItemIndex) {
/*     */       case 0:
/* 324 */         this.mModified = true;
/* 325 */         this.mUserBookmarksAdapter.setEditing(true);
/* 326 */         this.mFab.setVisibility(8);
/* 327 */         this.mUserBookmarksAdapter.setSelectedIndex(position);
/* 328 */         Utils.safeNotifyDataSetChanged((RecyclerView.Adapter)this.mUserBookmarksAdapter);
/* 329 */         onEventAction();
/* 330 */         AnalyticsHandlerAdapter.getInstance().sendEvent(34, 
/* 331 */             AnalyticsParam.userBookmarksActionParam(4));
/*     */         break;
/*     */       case 1:
/* 334 */         userBookmarkItem = this.mUserBookmarksAdapter.getItem(position);
/* 335 */         if (userBookmarkItem == null) {
/*     */           return;
/*     */         }
/* 338 */         this.mModified = true;
/* 339 */         if (!this.mReadOnly) {
/* 340 */           PDFDoc pdfDoc = this.mPdfViewCtrl.getDoc();
/* 341 */           if (pdfDoc != null) {
/* 342 */             boolean hasChange = false;
/* 343 */             boolean shouldUnlock = false;
/*     */             try {
/* 345 */               pdfDoc.lock();
/* 346 */               shouldUnlock = true;
/* 347 */               if (userBookmarkItem.pdfBookmark != null) {
/* 348 */                 userBookmarkItem.pdfBookmark.delete();
/*     */               }
/* 350 */               hasChange = pdfDoc.hasChangesSinceSnapshot();
/* 351 */             } catch (Exception e) {
/* 352 */               AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */             } finally {
/* 354 */               if (shouldUnlock) {
/* 355 */                 Utils.unlockQuietly(pdfDoc);
/*     */               }
/*     */             } 
/* 358 */             if (hasChange) {
/* 359 */               ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 360 */               if (toolManager != null) {
/* 361 */                 toolManager.raiseBookmarkModified();
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/* 366 */         this.mUserBookmarksAdapter.remove(userBookmarkItem);
/* 367 */         Utils.safeNotifyDataSetChanged((RecyclerView.Adapter)this.mUserBookmarksAdapter);
/*     */         
/* 369 */         onEventAction();
/* 370 */         AnalyticsHandlerAdapter.getInstance().sendEvent(34, 
/* 371 */             AnalyticsParam.userBookmarksActionParam(2));
/*     */         break;
/*     */       case 2:
/* 374 */         this.mModified = true;
/*     */         
/* 376 */         builder = new AlertDialog.Builder((Context)getActivity());
/* 377 */         builder.setMessage(R.string.controls_bookmark_dialog_delete_all_message)
/* 378 */           .setTitle(R.string.controls_misc_delete_all)
/* 379 */           .setPositiveButton(R.string.tools_misc_yes, new DialogInterface.OnClickListener()
/*     */             {
/*     */               public void onClick(DialogInterface dialog, int which) {
/* 382 */                 if (!UserBookmarkDialogFragment.this.mReadOnly) {
/* 383 */                   BookmarkManager.removeRootPdfBookmark(UserBookmarkDialogFragment.this.mPdfViewCtrl, true);
/*     */                 }
/* 385 */                 UserBookmarkDialogFragment.this.mUserBookmarksAdapter.clear();
/* 386 */                 Utils.safeNotifyDataSetChanged((RecyclerView.Adapter)UserBookmarkDialogFragment.this.mUserBookmarksAdapter);
/*     */                 
/* 388 */                 UserBookmarkDialogFragment.this.onEventAction();
/* 389 */                 AnalyticsHandlerAdapter.getInstance().sendEvent(34, 
/* 390 */                     AnalyticsParam.userBookmarksActionParam(3));
/*     */               }
/* 393 */             }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
/*     */             {
/*     */ 
/*     */ 
/*     */               
/*     */               public void onClick(DialogInterface dialog, int which) {}
/* 399 */             }).create()
/* 400 */           .show();
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void loadBookmarks() {
/* 406 */     if (this.mPdfViewCtrl == null || this.mPdfViewCtrl.getDoc() == null) {
/*     */       return;
/*     */     }
/*     */     
/* 410 */     if (this.mTask != null && this.mTask.getStatus() == AsyncTask.Status.RUNNING) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/* 415 */       Bookmark rootBookmark = BookmarkManager.getRootPdfBookmark(this.mPdfViewCtrl.getDoc(), false);
/* 416 */       if (Utils.isNullOrEmpty(this.mFilePath)) {
/* 417 */         this.mFilePath = this.mPdfViewCtrl.getDoc().getFileName();
/*     */       }
/* 419 */       this.mTask = new PopulateUserBookmarkListTask(getContext(), this.mFilePath, rootBookmark, this.mReadOnly);
/* 420 */       this.mTask.setCallback(new PopulateUserBookmarkListTask.Callback()
/*     */           {
/*     */             public void getUserBookmarks(List<UserBookmarkItem> bookmarks, boolean modified) {
/* 423 */               UserBookmarkDialogFragment.this.mModified = modified;
/* 424 */               UserBookmarkDialogFragment.this.mUserBookmarksAdapter.clear();
/* 425 */               UserBookmarkDialogFragment.this.mUserBookmarksAdapter.addAll(bookmarks);
/* 426 */               Utils.safeNotifyDataSetChanged((RecyclerView.Adapter)UserBookmarkDialogFragment.this.mUserBookmarksAdapter);
/*     */             }
/*     */           });
/* 429 */       this.mTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Object[])new Void[0]);
/* 430 */     } catch (Exception e) {
/* 431 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void commitData(Context context) {
/* 436 */     if (this.mPdfViewCtrl == null || this.mPdfViewCtrl.getDoc() == null) {
/*     */       return;
/*     */     }
/*     */     
/* 440 */     if (!this.mModified) {
/*     */       return;
/*     */     }
/* 443 */     if (this.mReadOnly) {
/*     */       try {
/* 445 */         if (Utils.isNullOrEmpty(this.mFilePath)) {
/* 446 */           this.mFilePath = this.mPdfViewCtrl.getDoc().getFileName();
/*     */         }
/* 448 */         BookmarkManager.saveUserBookmarks(context, this.mFilePath, this.mSource);
/* 449 */       } catch (Exception e) {
/* 450 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */       } 
/*     */     } else {
/* 453 */       BookmarkManager.savePdfBookmarks(this.mPdfViewCtrl, this.mSource, true, this.mRebuild);
/* 454 */       this.mRebuild = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   private class UserBookmarksAdapter
/*     */     extends EditListAdapter<UserBookmarkItem>
/*     */     implements ItemTouchHelperAdapter {
/*     */     private ArrayList<UserBookmarkItem> mBookmarks;
/*     */     private Context mContext;
/*     */     
/*     */     UserBookmarksAdapter(Context context, ArrayList<UserBookmarkItem> bookmarks, ViewHolderBindListener bindListener) {
/* 465 */       super(bindListener);
/*     */       
/* 467 */       this.mContext = context;
/* 468 */       this.mBookmarks = bookmarks;
/*     */     }
/*     */     
/*     */     public void clear() {
/* 472 */       this.mBookmarks.clear();
/*     */     }
/*     */     
/*     */     public void addAll(List<UserBookmarkItem> listBookmarks) {
/* 476 */       this.mBookmarks.addAll(listBookmarks);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean onItemMove(int fromPosition, int toPosition) {
/* 481 */       UserBookmarkItem oldItem = this.mBookmarks.get(fromPosition);
/* 482 */       UserBookmarkItem item = new UserBookmarkItem();
/* 483 */       item.pageObjNum = oldItem.pageObjNum;
/* 484 */       item.pageNumber = oldItem.pageNumber;
/* 485 */       item.title = oldItem.title;
/*     */       
/* 487 */       for (UserBookmarkItem uitem : this.mBookmarks) {
/* 488 */         uitem.pdfBookmark = null;
/*     */       }
/* 490 */       UserBookmarkDialogFragment.this.mRebuild = true;
/*     */       
/* 492 */       this.mBookmarks.remove(fromPosition);
/* 493 */       this.mBookmarks.add(toPosition, item);
/*     */       
/* 495 */       notifyItemMoved(fromPosition, toPosition);
/* 496 */       UserBookmarkDialogFragment.this.mModified = true;
/*     */       
/* 498 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void onItemDrop(int fromPosition, int toPosition) {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void onItemDismiss(int position) {}
/*     */ 
/*     */ 
/*     */     
/*     */     public UserBookmarkItem getItem(int position) {
/* 513 */       if (this.mBookmarks != null && isValidIndex(position)) {
/* 514 */         return this.mBookmarks.get(position);
/*     */       }
/* 516 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public void add(UserBookmarkItem item) {
/* 521 */       this.mBookmarks.add(item);
/*     */     }
/*     */     
/*     */     public boolean contains(UserBookmarkItem item) {
/* 525 */       return this.mBookmarks.contains(item);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(UserBookmarkItem item) {
/* 530 */       if (this.mBookmarks.contains(item)) {
/* 531 */         this.mBookmarks.remove(item);
/* 532 */         return true;
/*     */       } 
/* 534 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public UserBookmarkItem removeAt(int location) {
/* 539 */       if (isValidIndex(location)) {
/* 540 */         return this.mBookmarks.remove(location);
/*     */       }
/* 542 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public void insert(UserBookmarkItem item, int position) {
/* 547 */       this.mBookmarks.add(position, item);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void updateSpanCount(int count) {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void onBindViewHolder(@NonNull EditListViewHolder holder, int position) {
/* 557 */       super.onBindViewHolder(holder, position);
/*     */       
/* 559 */       UserBookmarkItem item = this.mBookmarks.get(position);
/*     */       
/* 561 */       holder.itemView.getBackground().setColorFilter(null);
/* 562 */       holder.itemView.getBackground().invalidateSelf();
/* 563 */       holder.textView.setText(item.title);
/*     */       
/* 565 */       if (this.mEditing && position == this.mSelectedIndex) {
/* 566 */         holder.editText.setText(item.title);
/* 567 */         holder.editText.requestFocus();
/* 568 */         holder.editText.selectAll();
/* 569 */         Utils.showSoftKeyboard(holder.editText.getContext(), null);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     protected void contextButtonClicked(@NonNull EditListViewHolder holder, View v) {
/* 575 */       if (!this.mEditing) {
/* 576 */         UserBookmarkDialogFragment.this.onShowPopupMenu(holder.getAdapterPosition(), v);
/*     */       } else {
/* 578 */         holder.itemView.requestFocus();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     protected void handleEditTextFocusChange(@NonNull EditListViewHolder holder, View v, boolean hasFocus) {
/* 584 */       if (hasFocus) {
/*     */         return;
/*     */       }
/* 587 */       int pos = holder.getAdapterPosition();
/* 588 */       if (pos == -1) {
/*     */         return;
/*     */       }
/*     */       
/* 592 */       Utils.hideSoftKeyboard(v.getContext(), v);
/*     */       
/* 594 */       saveEditTextChanges((TextView)v, pos);
/*     */     }
/*     */ 
/*     */     
/*     */     public int getItemCount() {
/* 599 */       return this.mBookmarks.size();
/*     */     }
/*     */     
/*     */     private void saveEditTextChanges(TextView v, int position) {
/* 603 */       v.clearFocus();
/*     */       
/* 605 */       setEditing(false);
/* 606 */       UserBookmarkDialogFragment.this.mFab.setVisibility(0);
/* 607 */       String title = v.getText().toString();
/* 608 */       if (title.isEmpty()) {
/* 609 */         title = this.mContext.getString(R.string.empty_title);
/*     */       }
/* 611 */       UserBookmarkItem userBookmarkItem = UserBookmarkDialogFragment.this.mUserBookmarksAdapter.getItem(position);
/* 612 */       if (userBookmarkItem == null) {
/*     */         return;
/*     */       }
/* 615 */       userBookmarkItem.title = title;
/* 616 */       userBookmarkItem.isBookmarkEdited = true;
/* 617 */       Utils.safeNotifyDataSetChanged((RecyclerView.Adapter)this);
/*     */     }
/*     */   }
/*     */   
/*     */   public static interface UserBookmarkDialogListener {
/*     */     void onUserBookmarkClicked(int param1Int);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\controls\UserBookmarkDialogFragment.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */