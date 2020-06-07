/*     */ package com.pdftron.pdf.asynctask;
/*     */ 
/*     */ import android.content.Context;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import com.pdftron.pdf.Bookmark;
/*     */ import com.pdftron.pdf.model.UserBookmarkItem;
/*     */ import com.pdftron.pdf.utils.BookmarkManager;
/*     */ import com.pdftron.pdf.utils.CustomAsyncTask;
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
/*     */ public class PopulateUserBookmarkListTask
/*     */   extends CustomAsyncTask<Void, Void, Void>
/*     */ {
/*     */   private List<UserBookmarkItem> mBookmarkList;
/*     */   private boolean mModified;
/*     */   private String mFilePath;
/*     */   private Bookmark mBookmark;
/*     */   private boolean mReadOnly;
/*     */   private Callback mCallback;
/*     */   
/*     */   public PopulateUserBookmarkListTask(@NonNull Context context, String filePath, Bookmark bookmark, boolean readOnly) {
/*  52 */     super(context);
/*  53 */     this.mBookmark = bookmark;
/*  54 */     this.mFilePath = filePath;
/*  55 */     this.mReadOnly = readOnly;
/*     */     
/*  57 */     this.mBookmarkList = new ArrayList<>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCallback(@Nullable Callback callback) {
/*  68 */     this.mCallback = callback;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Void doInBackground(Void... params) {
/*  76 */     if (this.mReadOnly) {
/*  77 */       this.mBookmarkList.addAll(BookmarkManager.getUserBookmarks(getContext(), this.mFilePath));
/*  78 */       if (this.mBookmarkList.isEmpty()) {
/*  79 */         this.mBookmarkList.addAll(BookmarkManager.getPdfBookmarks(this.mBookmark));
/*     */       }
/*     */     } else {
/*  82 */       this.mBookmarkList.addAll(BookmarkManager.getPdfBookmarks(this.mBookmark));
/*  83 */       if (this.mBookmarkList.isEmpty()) {
/*     */ 
/*     */         
/*  86 */         this.mBookmarkList.addAll(BookmarkManager.getUserBookmarks(getContext(), this.mFilePath));
/*  87 */         if (!this.mBookmarkList.isEmpty()) {
/*  88 */           this.mModified = true;
/*     */           
/*  90 */           BookmarkManager.removeUserBookmarks(getContext(), this.mFilePath);
/*     */         } 
/*     */       } 
/*     */     } 
/*  94 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onPostExecute(Void aVoid) {
/* 102 */     if (this.mCallback != null)
/* 103 */       this.mCallback.getUserBookmarks(this.mBookmarkList, this.mModified); 
/*     */   }
/*     */   
/*     */   public static interface Callback {
/*     */     void getUserBookmarks(List<UserBookmarkItem> param1List, boolean param1Boolean);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\asynctask\PopulateUserBookmarkListTask.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */