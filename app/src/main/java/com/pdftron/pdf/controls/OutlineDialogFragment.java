/*     */ package com.pdftron.pdf.controls;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.os.Bundle;
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.widget.ImageView;
/*     */ import android.widget.RelativeLayout;
/*     */ import android.widget.TextView;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.recyclerview.widget.LinearLayoutManager;
/*     */ import androidx.recyclerview.widget.RecyclerView;
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.Action;
/*     */ import com.pdftron.pdf.ActionParameter;
/*     */ import com.pdftron.pdf.Bookmark;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.tools.ToolManager;
/*     */ import com.pdftron.pdf.utils.ActionUtils;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.AnalyticsParam;
/*     */ import com.pdftron.pdf.utils.BookmarkManager;
/*     */ import com.pdftron.pdf.utils.CommonToast;
/*     */ import com.pdftron.pdf.widget.recyclerview.ItemClickHelper;
/*     */ import java.util.ArrayList;
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
/*     */ public class OutlineDialogFragment
/*     */   extends NavigationListDialogFragment
/*     */ {
/*     */   private PDFViewCtrl mPdfViewCtrl;
/*     */   private ArrayList<Bookmark> mBookmarks;
/*     */   private OutlineAdapter mOutlineAdapter;
/*     */   private RelativeLayout mNavigation;
/*     */   private TextView mNavigationText;
/*     */   private Bookmark mCurrentBookmark;
/*     */   private OutlineDialogListener mOutlineDialogListener;
/*     */   
/*     */   public static OutlineDialogFragment newInstance() {
/*  78 */     return new OutlineDialogFragment();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OutlineDialogFragment setPdfViewCtrl(@NonNull PDFViewCtrl pdfViewCtrl) {
/*  88 */     this.mPdfViewCtrl = pdfViewCtrl;
/*  89 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OutlineDialogFragment setCurrentBookmark(@Nullable Bookmark currentBookmark) {
/*  99 */     this.mCurrentBookmark = currentBookmark;
/* 100 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOutlineDialogListener(OutlineDialogListener listener) {
/* 109 */     this.mOutlineDialogListener = listener;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
/* 117 */     View view = inflater.inflate(R.layout.controls_fragment_outline_dialog, null);
/* 118 */     if (this.mPdfViewCtrl == null || this.mPdfViewCtrl.getDoc() == null) {
/* 119 */       return view;
/*     */     }
/*     */ 
/*     */     
/* 123 */     this.mNavigation = (RelativeLayout)view.findViewById(R.id.control_outline_layout_navigation);
/* 124 */     this.mNavigationText = (TextView)this.mNavigation.findViewById(R.id.control_outline_layout_navigation_title);
/* 125 */     this.mNavigation.setVisibility(8);
/*     */     try {
/* 127 */       if (this.mCurrentBookmark != null) {
/* 128 */         if (this.mCurrentBookmark.getIndent() > 0) {
/* 129 */           this.mNavigationText.setText(this.mCurrentBookmark.getTitle());
/* 130 */           this.mNavigation.setVisibility(0);
/*     */         } else {
/* 132 */           this.mCurrentBookmark = null;
/*     */         } 
/*     */       }
/* 135 */     } catch (PDFNetException e) {
/* 136 */       this.mCurrentBookmark = null;
/*     */     } 
/* 138 */     this.mNavigation.setOnClickListener(new View.OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/* 141 */             OutlineDialogFragment.this.navigateToParentBookmark();
/*     */           }
/*     */         });
/*     */     
/* 145 */     this.mBookmarks = new ArrayList<>();
/* 146 */     if (this.mCurrentBookmark != null) {
/*     */       try {
/* 148 */         this.mBookmarks.addAll(BookmarkManager.getBookmarkList(this.mPdfViewCtrl.getDoc(), this.mCurrentBookmark.getFirstChild()));
/* 149 */       } catch (PDFNetException e) {
/* 150 */         this.mBookmarks.clear();
/* 151 */         this.mBookmarks.addAll(BookmarkManager.getBookmarkList(this.mPdfViewCtrl.getDoc(), null));
/* 152 */         this.mCurrentBookmark = null;
/*     */       } 
/*     */     } else {
/* 155 */       this.mBookmarks.addAll(BookmarkManager.getBookmarkList(this.mPdfViewCtrl.getDoc(), null));
/*     */     } 
/*     */     
/* 158 */     View emptyView = view.findViewById(R.id.control_outline_textview_empty);
/* 159 */     emptyView.setVisibility(this.mBookmarks.isEmpty() ? 0 : 8);
/*     */     
/* 161 */     RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview_control_outline);
/* 162 */     recyclerView.setLayoutManager((RecyclerView.LayoutManager)new LinearLayoutManager(view.getContext()));
/* 163 */     this.mOutlineAdapter = new OutlineAdapter(this.mBookmarks);
/* 164 */     recyclerView.setAdapter(this.mOutlineAdapter);
/*     */     
/* 166 */     ItemClickHelper itemClickHelper = new ItemClickHelper();
/* 167 */     itemClickHelper.attachToRecyclerView(recyclerView);
/* 168 */     itemClickHelper.setOnItemClickListener(new ItemClickHelper.OnItemClickListener()
/*     */         {
/*     */           public void onItemClick(RecyclerView recyclerView, View view, int position, long id) {
/*     */             try {
/* 172 */               OutlineDialogFragment.this.onEventAction();
/* 173 */               AnalyticsHandlerAdapter.getInstance().sendEvent(30, 
/* 174 */                   AnalyticsParam.viewerNavigateByParam(1));
/* 175 */               Action action = ((Bookmark)OutlineDialogFragment.this.mBookmarks.get(position)).getAction();
/* 176 */               if (action != null && action.isValid()) {
/* 177 */                 if (OutlineDialogFragment.this.mPdfViewCtrl != null) {
/* 178 */                   boolean shouldUnlock = false;
/* 179 */                   boolean shouldUnlockRead = false;
/* 180 */                   boolean hasChanges = false;
/*     */                   try {
/* 182 */                     if (action.needsWriteLock()) {
/* 183 */                       OutlineDialogFragment.this.mPdfViewCtrl.docLock(true);
/* 184 */                       shouldUnlock = true;
/*     */                     } else {
/* 186 */                       OutlineDialogFragment.this.mPdfViewCtrl.docLockRead();
/* 187 */                       shouldUnlockRead = true;
/*     */                     } 
/* 189 */                     ActionParameter action_param = new ActionParameter(action);
/* 190 */                     ActionUtils.getInstance().executeAction(action_param, OutlineDialogFragment.this.mPdfViewCtrl);
/* 191 */                     hasChanges = OutlineDialogFragment.this.mPdfViewCtrl.getDoc().hasChangesSinceSnapshot();
/* 192 */                   } catch (Exception e) {
/* 193 */                     AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */                   } finally {
/* 195 */                     if (shouldUnlock || shouldUnlockRead) {
/* 196 */                       if (shouldUnlock) {
/* 197 */                         OutlineDialogFragment.this.mPdfViewCtrl.docUnlock();
/*     */                       }
/* 199 */                       if (shouldUnlockRead) {
/* 200 */                         OutlineDialogFragment.this.mPdfViewCtrl.docUnlockRead();
/*     */                       }
/* 202 */                       if (hasChanges) {
/* 203 */                         ToolManager toolManager = (ToolManager)OutlineDialogFragment.this.mPdfViewCtrl.getToolManager();
/* 204 */                         toolManager.raiseAnnotationActionEvent();
/*     */                       } 
/*     */                     } 
/*     */                   } 
/*     */                 } 
/* 209 */                 if (OutlineDialogFragment.this.mOutlineDialogListener != null) {
/* 210 */                   OutlineDialogFragment.this.mOutlineDialogListener.onOutlineClicked(OutlineDialogFragment.this.mCurrentBookmark, OutlineDialogFragment.this.mBookmarks.get(position));
/*     */                 }
/*     */               } 
/* 213 */             } catch (PDFNetException e) {
/* 214 */               AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/* 215 */               CommonToast.showText((Context)OutlineDialogFragment.this.getActivity(), "This bookmark has an invalid action", 0);
/*     */             } 
/*     */           }
/*     */         });
/*     */     
/* 220 */     return view;
/*     */   }
/*     */   private void navigateToParentBookmark() {
/*     */     ArrayList<Bookmark> temp;
/* 224 */     if (this.mPdfViewCtrl == null || this.mPdfViewCtrl.getDoc() == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 230 */       if (this.mCurrentBookmark != null && this.mCurrentBookmark.getIndent() > 0) {
/* 231 */         this.mCurrentBookmark = this.mCurrentBookmark.getParent();
/* 232 */         temp = BookmarkManager.getBookmarkList(this.mPdfViewCtrl.getDoc(), this.mCurrentBookmark.getFirstChild());
/* 233 */         this.mNavigationText.setText(this.mCurrentBookmark.getTitle());
/* 234 */         if (this.mCurrentBookmark.getIndent() <= 0) {
/* 235 */           this.mNavigation.setVisibility(8);
/*     */         }
/*     */       } else {
/* 238 */         temp = BookmarkManager.getBookmarkList(this.mPdfViewCtrl.getDoc(), null);
/* 239 */         this.mCurrentBookmark = null;
/* 240 */         this.mNavigation.setVisibility(8);
/*     */       } 
/* 242 */     } catch (PDFNetException e) {
/* 243 */       this.mCurrentBookmark = null;
/* 244 */       temp = null;
/*     */     } 
/*     */     
/* 247 */     if (temp != null) {
/* 248 */       this.mBookmarks.clear();
/* 249 */       this.mBookmarks.addAll(temp);
/* 250 */       this.mOutlineAdapter.notifyDataSetChanged();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static interface OutlineDialogListener {
/*     */     void onOutlineClicked(Bookmark param1Bookmark1, Bookmark param1Bookmark2); }
/*     */   
/*     */   private class OutlineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
/*     */     OutlineAdapter(ArrayList<Bookmark> objects) {
/* 259 */       this.mBookmarks = objects;
/*     */     }
/*     */     private ArrayList<Bookmark> mBookmarks;
/*     */     
/*     */     @NonNull
/*     */     public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
/* 265 */       View view = LayoutInflater.from(OutlineDialogFragment.this.getContext()).inflate(R.layout.controls_fragment_outline_listview_item, parent, false);
/*     */       
/* 267 */       return new ViewHolder(view);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
/* 272 */       ViewHolder viewHolder = (ViewHolder)holder;
/* 273 */       viewHolder.bookmarkArrow.setOnClickListener(new View.OnClickListener()
/*     */           {
/*     */             public void onClick(View v) {
/* 276 */               OutlineDialogFragment.this.mCurrentBookmark = OutlineAdapter.this.mBookmarks.get(position);
/* 277 */               if (OutlineDialogFragment.this.mCurrentBookmark == null) {
/*     */                 return;
/*     */               }
/*     */               
/* 281 */               ArrayList<Bookmark> temp = new ArrayList<>();
/* 282 */               if (OutlineDialogFragment.this.mPdfViewCtrl != null && OutlineDialogFragment.this.mPdfViewCtrl.getDoc() != null) {
/*     */                 try {
/* 284 */                   temp = BookmarkManager.getBookmarkList(OutlineDialogFragment.this.mPdfViewCtrl.getDoc(), OutlineDialogFragment.this.mCurrentBookmark.getFirstChild());
/* 285 */                 } catch (PDFNetException pDFNetException) {}
/*     */               }
/*     */ 
/*     */               
/* 289 */               OutlineAdapter.this.mBookmarks.clear();
/* 290 */               OutlineAdapter.this.mBookmarks.addAll(temp);
/* 291 */               OutlineAdapter.this.notifyDataSetChanged();
/* 292 */               OutlineDialogFragment.this.mNavigation.setVisibility(0);
/*     */               try {
/* 294 */                 OutlineDialogFragment.this.mNavigationText.setText(OutlineDialogFragment.this.mCurrentBookmark.getTitle());
/* 295 */               } catch (PDFNetException e) {
/* 296 */                 AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*     */               } 
/*     */             }
/*     */           });
/*     */       
/* 301 */       Bookmark bookmark = this.mBookmarks.get(position);
/*     */       try {
/* 303 */         viewHolder.bookmarkText.setText(bookmark.getTitle());
/* 304 */         if (bookmark.hasChildren()) {
/* 305 */           viewHolder.bookmarkArrow.setVisibility(0);
/*     */         } else {
/* 307 */           viewHolder.bookmarkArrow.setVisibility(8);
/*     */         } 
/* 309 */       } catch (PDFNetException e) {
/* 310 */         AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int getItemCount() {
/* 316 */       if (this.mBookmarks != null) {
/* 317 */         return this.mBookmarks.size();
/*     */       }
/* 319 */       return 0;
/*     */     }
/*     */     
/*     */     private class ViewHolder
/*     */       extends RecyclerView.ViewHolder {
/*     */       TextView bookmarkText;
/*     */       ImageView bookmarkArrow;
/*     */       
/*     */       ViewHolder(View itemView) {
/* 328 */         super(itemView);
/*     */         
/* 330 */         this.bookmarkText = (TextView)itemView.findViewById(R.id.control_outline_listview_item_textview);
/* 331 */         this.bookmarkArrow = (ImageView)itemView.findViewById(R.id.control_outline_listview_item_imageview);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\controls\OutlineDialogFragment.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */