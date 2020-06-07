/*    */ package com.pdftron.pdf.dialog.pagelabel;
/*    */ 
/*    */ import androidx.annotation.NonNull;
/*    */ import androidx.lifecycle.LifecycleOwner;
/*    */ import androidx.lifecycle.MutableLiveData;
/*    */ import androidx.lifecycle.Observer;
/*    */ import androidx.lifecycle.ViewModel;
/*    */ import com.pdftron.pdf.utils.Event;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PageLabelSettingViewModel
/*    */   extends ViewModel
/*    */ {
/*    */   @NonNull
/* 17 */   private final MutableLiveData<Event<PageLabelSetting>> mPageLabelObservable = new MutableLiveData();
/*    */   
/*    */   @NonNull
/*    */   private PageLabelSetting mPageLabelSettings;
/*    */   
/*    */   void set(PageLabelSetting setting) {
/* 23 */     this.mPageLabelSettings = setting;
/*    */   }
/*    */   
/*    */   PageLabelSetting get() {
/* 27 */     return this.mPageLabelSettings;
/*    */   }
/*    */   
/*    */   void complete() {
/* 31 */     this.mPageLabelObservable.setValue(new Event(this.mPageLabelSettings));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void observeOnComplete(@NonNull LifecycleOwner owner, @NonNull Observer<Event<PageLabelSetting>> observer) {
/* 46 */     this.mPageLabelObservable.observe(owner, observer);
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\dialog\pagelabel\PageLabelSettingViewModel.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */