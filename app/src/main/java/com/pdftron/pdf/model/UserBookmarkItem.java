/*     */ package com.pdftron.pdf.model;
/*     */ 
/*     */ import android.content.Context;
/*     */ import androidx.annotation.NonNull;
/*     */ import com.google.gson.Gson;
/*     */ import com.pdftron.pdf.Bookmark;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import org.json.JSONObject;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UserBookmarkItem
/*     */ {
/*     */   private static final String VAR_PAGE_NUMBER = "pageNumber";
/*     */   private static final String VAR_PAGE_OBJ_NUM = "pageObjNum";
/*     */   private static final String VAR_TITLE = "title";
/*     */   private static final String VAR_PDF_BOOKMARK = "pdfBookmark";
/*     */   public int pageNumber;
/*     */   public long pageObjNum;
/*     */   public String title;
/*     */   public boolean isBookmarkEdited;
/*     */   public Bookmark pdfBookmark;
/*     */   
/*     */   public UserBookmarkItem() {}
/*     */   
/*     */   public UserBookmarkItem(@NonNull Context context, long pageObjNum, int pageNumber) {
/*  70 */     this.pageObjNum = pageObjNum;
/*  71 */     this.pageNumber = pageNumber;
/*  72 */     this.title = context.getString(R.string.controls_bookmark_dialog_default_title) + Integer.toString(pageNumber);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UserBookmarkItem(JSONObject jsonObject) {
/*     */     try {
/*  84 */       this.pageNumber = jsonObject.getInt("pageNumber");
/*  85 */       this.pageObjNum = jsonObject.getLong("pageObjNum");
/*  86 */       this.title = jsonObject.getString("title");
/*  87 */       if (jsonObject.has("pdfBookmark")) {
/*  88 */         JSONObject bookmarkObject = jsonObject.getJSONObject("pdfBookmark");
/*  89 */         Gson gson = new Gson();
/*  90 */         this.pdfBookmark = (Bookmark)gson.fromJson(bookmarkObject.toString(), Bookmark.class);
/*     */       } 
/*  92 */     } catch (Exception e) {
/*  93 */       AnalyticsHandlerAdapter.getInstance().sendException(e, "\nJson from: " + jsonObject);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  99 */     if (this == o) return true; 
/* 100 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/* 102 */     UserBookmarkItem that = (UserBookmarkItem)o;
/*     */     
/* 104 */     if (this.pageNumber != that.pageNumber) return false; 
/* 105 */     if (this.pageObjNum != that.pageObjNum) return false; 
/* 106 */     return (this.title != null) ? this.title.equals(that.title) : ((that.title == null));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 111 */     int result = this.pageNumber;
/* 112 */     result = 31 * result + (int)(this.pageObjNum ^ this.pageObjNum >>> 32L);
/* 113 */     result = 31 * result + ((this.title != null) ? this.title.hashCode() : 0);
/* 114 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\model\UserBookmarkItem.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */