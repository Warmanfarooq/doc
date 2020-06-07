/*    */ package com.pdftron.pdf.dialog.menueditor;
/*    */ 
/*    */ import android.app.Application;
/*    */ import androidx.annotation.NonNull;
/*    */ import androidx.lifecycle.AndroidViewModel;
/*    */ import androidx.lifecycle.LiveData;
/*    */ import androidx.lifecycle.MutableLiveData;
/*    */ import com.pdftron.pdf.dialog.menueditor.model.MenuEditorItem;
/*    */ import io.reactivex.Observable;
/*    */ import io.reactivex.subjects.PublishSubject;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ 
/*    */ public class MenuEditorViewModel
/*    */   extends AndroidViewModel
/*    */ {
/*    */   @NonNull
/* 18 */   private final MutableLiveData<ArrayList<MenuEditorItem>> mItems = new MutableLiveData();
/*    */ 
/*    */   
/*    */   @NonNull
/* 22 */   private final PublishSubject<MenuEditorEvent> mObservable = PublishSubject.create();
/*    */   
/*    */   public MenuEditorViewModel(@NonNull Application application) {
/* 25 */     super(application);
/*    */   }
/*    */   
/*    */   public LiveData<ArrayList<MenuEditorItem>> getItemsLiveData() {
/* 29 */     return (LiveData<ArrayList<MenuEditorItem>>)this.mItems;
/*    */   }
/*    */   
/*    */   public void setItems(ArrayList<MenuEditorItem> items) {
/* 33 */     this.mItems.setValue(items);
/*    */   }
/*    */   
/*    */   public void onReset() {
/* 37 */     this.mObservable.onNext(new MenuEditorEvent(MenuEditorEvent.Type.RESET));
/*    */   }
/*    */   
/*    */   public final Observable<MenuEditorEvent> getObservable() {
/* 41 */     return this.mObservable.serialize();
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\dialog\menueditor\MenuEditorViewModel.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */