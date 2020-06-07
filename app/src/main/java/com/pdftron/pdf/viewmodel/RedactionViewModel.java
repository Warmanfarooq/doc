/*    */ package com.pdftron.pdf.viewmodel;
/*    */ 
/*    */ import android.app.Application;
/*    */ import android.util.Pair;
/*    */ import androidx.annotation.NonNull;
/*    */ import androidx.lifecycle.AndroidViewModel;
/*    */ import com.pdftron.pdf.TextSearchResult;
/*    */ import io.reactivex.Observable;
/*    */ import io.reactivex.subjects.PublishSubject;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ public class RedactionViewModel
/*    */   extends AndroidViewModel {
/*    */   @NonNull
/*    */   private final PublishSubject<RedactionEvent> mObservable;
/*    */   
/*    */   public RedactionViewModel(@NonNull Application application) {
/* 18 */     super(application);
/*    */ 
/*    */     
/* 21 */     this
/* 22 */       .mObservable = PublishSubject.create();
/*    */   }
/*    */   public void onRedactBySearch(@NonNull ArrayList<Pair<Integer, ArrayList<Double>>> searchResults) {
/* 25 */     RedactionEvent redactionEvent = new RedactionEvent(RedactionEvent.Type.REDACT_BY_SEARCH);
/* 26 */     redactionEvent.setSearchResults(searchResults);
/* 27 */     this.mObservable.onNext(redactionEvent);
/*    */   }
/*    */   
/*    */   public void onRedactByPage(@NonNull ArrayList<Integer> pages) {
/* 31 */     RedactionEvent redactionEvent = new RedactionEvent(RedactionEvent.Type.REDACT_BY_PAGE);
/* 32 */     redactionEvent.setPages(pages);
/* 33 */     this.mObservable.onNext(redactionEvent);
/*    */   }
/*    */   
/*    */   public void onRedactBySearchOpenSheet() {
/* 37 */     RedactionEvent redactionEvent = new RedactionEvent(RedactionEvent.Type.REDACT_BY_SEARCH_OPEN_SHEET);
/* 38 */     this.mObservable.onNext(redactionEvent);
/*    */   }
/*    */   
/*    */   public void onRedactBySearchItemClicked(@NonNull TextSearchResult result) {
/* 42 */     RedactionEvent redactionEvent = new RedactionEvent(RedactionEvent.Type.REDACT_BY_SEARCH_ITEM_CLICKED);
/* 43 */     redactionEvent.setSelectedItem(result);
/* 44 */     this.mObservable.onNext(redactionEvent);
/*    */   }
/*    */   
/*    */   public void onRedactBySearchCloseClicked() {
/* 48 */     this.mObservable.onNext(new RedactionEvent(RedactionEvent.Type.REDACT_BY_SEARCH_CLOSE_CLICKED));
/*    */   }
/*    */   
/*    */   public final Observable<RedactionEvent> getObservable() {
/* 52 */     return this.mObservable.serialize();
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\viewmodel\RedactionViewModel.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */