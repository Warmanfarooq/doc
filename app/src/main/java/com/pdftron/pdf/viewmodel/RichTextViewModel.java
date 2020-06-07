/*    */ package com.pdftron.pdf.viewmodel;
/*    */ 
/*    */ import androidx.annotation.NonNull;
/*    */ import androidx.lifecycle.ViewModel;
/*    */ import com.pdftron.pdf.model.AnnotStyle;
/*    */ import io.reactivex.Observable;
/*    */ import io.reactivex.subjects.PublishSubject;
/*    */ import java.util.List;
/*    */ import jp.wasabeef.richeditor.RichEditor;
/*    */ 
/*    */ 
/*    */ public class RichTextViewModel
/*    */   extends ViewModel
/*    */ {
/*    */   @NonNull
/* 16 */   private final PublishSubject<RichTextEvent> mObservable = PublishSubject.create();
/*    */   
/*    */   public void onCloseToolbar() {
/* 19 */     this.mObservable.onNext(new RichTextEvent(RichTextEvent.Type.CLOSE_TOOLBAR));
/*    */   }
/*    */   
/*    */   public void onOpenToolbar() {
/* 23 */     this.mObservable.onNext(new RichTextEvent(RichTextEvent.Type.OPEN_TOOLBAR));
/*    */   }
/*    */   
/*    */   public void onUpdateTextStyle(AnnotStyle style) {
/* 27 */     this.mObservable.onNext(new RichTextEvent(RichTextEvent.Type.TEXT_STYLE, style));
/*    */   }
/*    */   
/*    */   public void onEditorAction(RichTextEvent.Type actionType) {
/* 31 */     this.mObservable.onNext(new RichTextEvent(actionType));
/*    */   }
/*    */   
/*    */   public void onUpdateDecorationTypes(List<RichEditor.Type> decorationTypes) {
/* 35 */     this.mObservable.onNext(new RichTextEvent(RichTextEvent.Type.UPDATE_TOOLBAR, decorationTypes));
/*    */   }
/*    */   
/*    */   public final Observable<RichTextEvent> getObservable() {
/* 39 */     return this.mObservable.serialize();
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\viewmodel\RichTextViewModel.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */