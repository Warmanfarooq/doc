/*    */ package com.pdftron.pdf.viewmodel;
/*    */ 
/*    */ import androidx.annotation.NonNull;
/*    */ import androidx.lifecycle.LifecycleOwner;
/*    */ import androidx.lifecycle.MutableLiveData;
/*    */ import androidx.lifecycle.Observer;
/*    */ import androidx.lifecycle.ViewModel;
/*    */ import com.pdftron.pdf.utils.Event;
/*    */ 
/*    */ public abstract class SimpleDialogViewModel<T> extends ViewModel {
/*    */   @NonNull
/* 12 */   private final MutableLiveData<Event<T>> mCompletable = new MutableLiveData();
/*    */   @NonNull
/* 14 */   private MutableLiveData<T> mResult = new MutableLiveData();
/*    */ 
/*    */   
/*    */   public SimpleDialogViewModel() {
/* 18 */     this.mCompletable.setValue(null);
/* 19 */     this.mResult.setValue(null);
/*    */   }
/*    */   
/*    */   public void set(T result) {
/* 23 */     this.mResult.setValue(result);
/*    */   }
/*    */   
/*    */   public void complete() {
/* 27 */     this.mCompletable.setValue((this.mResult.getValue() == null) ? null : new Event(this.mResult
/* 28 */           .getValue()));
/* 29 */     this.mResult.setValue(null);
/*    */   }
/*    */ 
/*    */   
/*    */   public void observeOnComplete(@NonNull LifecycleOwner owner, @NonNull Observer<Event<T>> observer) {
/* 34 */     this.mCompletable.observe(owner, observer);
/*    */   }
/*    */ 
/*    */   
/*    */   public void observeChanges(@NonNull LifecycleOwner owner, @NonNull Observer<T> observer) {
/* 39 */     this.mResult.observe(owner, observer);
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\viewmodel\SimpleDialogViewModel.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */