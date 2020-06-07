/*    */ package com.pdftron.pdf.viewmodel;
/*    */ 
/*    */ import android.util.Pair;
/*    */ import androidx.annotation.NonNull;
/*    */ import com.pdftron.pdf.TextSearchResult;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ 
/*    */ public class RedactionEvent
/*    */ {
/*    */   private final Type mType;
/* 12 */   private ArrayList<Integer> mPages = new ArrayList<>();
/* 13 */   private ArrayList<Pair<Integer, ArrayList<Double>>> mSearchResults = new ArrayList<>();
/*    */   private TextSearchResult mSelectedItem;
/*    */   
/*    */   RedactionEvent(@NonNull Type eventType) {
/* 17 */     this.mType = eventType;
/*    */   }
/*    */   
/*    */   public void setPages(@NonNull ArrayList<Integer> pages) {
/* 21 */     this.mPages.clear();
/* 22 */     this.mPages.addAll(pages);
/*    */   }
/*    */   
/*    */   public void setSearchResults(@NonNull ArrayList<Pair<Integer, ArrayList<Double>>> searchResults) {
/* 26 */     this.mSearchResults.clear();
/* 27 */     this.mSearchResults.addAll(searchResults);
/*    */   }
/*    */   
/*    */   public void setSelectedItem(@NonNull TextSearchResult selectedItem) {
/* 31 */     this.mSelectedItem = selectedItem;
/*    */   }
/*    */   
/*    */   @NonNull
/*    */   public Type getEventType() {
/* 36 */     return this.mType;
/*    */   }
/*    */   
/*    */   @NonNull
/*    */   public ArrayList<Integer> getPages() {
/* 41 */     return this.mPages;
/*    */   }
/*    */   
/*    */   @NonNull
/*    */   public ArrayList<Pair<Integer, ArrayList<Double>>> getSearchResults() {
/* 46 */     return this.mSearchResults;
/*    */   }
/*    */   
/*    */   public enum Type {
/* 50 */     REDACT_BY_PAGE,
/* 51 */     REDACT_BY_SEARCH,
/* 52 */     REDACT_BY_SEARCH_OPEN_SHEET,
/* 53 */     REDACT_BY_SEARCH_ITEM_CLICKED,
/* 54 */     REDACT_BY_SEARCH_CLOSE_CLICKED;
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\viewmodel\RedactionEvent.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */