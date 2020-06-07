/*    */ package com.pdftron.pdf.dialog.digitalsignature;
/*    */ 
/*    */ import android.app.Application;
/*    */ import android.net.Uri;
/*    */ import androidx.annotation.NonNull;
/*    */ import androidx.annotation.Nullable;
/*    */ import androidx.lifecycle.AndroidViewModel;
/*    */ import androidx.lifecycle.MutableLiveData;
/*    */ import com.squareup.picasso.Picasso;
/*    */ import io.reactivex.disposables.CompositeDisposable;
/*    */ import io.reactivex.functions.Consumer;
/*    */ import io.reactivex.subjects.PublishSubject;
/*    */ import java.io.File;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DigitalSignatureViewModel
/*    */   extends AndroidViewModel
/*    */ {
/* 23 */   final MutableLiveData<File> signatureImageFile = new MutableLiveData();
/* 24 */   final MutableLiveData<String> mPassword = new MutableLiveData();
/* 25 */   final MutableLiveData<String> mFileName = new MutableLiveData();
/* 26 */   final MutableLiveData<Boolean> mIsPasswordState = new MutableLiveData();
/*    */   
/* 28 */   final MutableLiveData<Uri> mKeyStoreFile = new MutableLiveData();
/*    */   
/* 30 */   private final CompositeDisposable mDisposables = new CompositeDisposable();
/*    */   
/*    */   @NonNull
/* 33 */   private PublishSubject<DigitalSignaturePasswordView.UserEvent> mEventSubject = PublishSubject.create();
/*    */   @NonNull
/* 35 */   private PublishSubject<String> mPasswordChangeSubject = PublishSubject.create();
/*    */   
/*    */   public DigitalSignatureViewModel(@NonNull Application application) {
/* 38 */     super(application);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setKeystoreFileUri(@NonNull Uri keystoreFileUri) {
/* 47 */     this.mKeyStoreFile.setValue(keystoreFileUri);
/*    */   }
/*    */   
/*    */   void setFileName(@Nullable String fileName) {
/* 51 */     this.mFileName.setValue(fileName);
/*    */   }
/*    */   
/*    */   void setImageFilePath(@Nullable String fileName) {
/* 55 */     this.signatureImageFile.setValue(new File(fileName));
/*    */   }
/*    */   
/*    */   @NonNull
/*    */   PublishSubject<DigitalSignaturePasswordView.UserEvent> getEventSubject() {
/* 60 */     return this.mEventSubject;
/*    */   }
/*    */   
/*    */   @NonNull
/*    */   PublishSubject<String> getPasswordChangeSubject() {
/* 65 */     return this.mPasswordChangeSubject;
/*    */   }
/*    */   
/*    */   void subscribeEventSubject(Consumer<DigitalSignaturePasswordView.UserEvent> onNext) {
/* 69 */     this.mDisposables.add(this.mEventSubject
/* 70 */         .subscribe(onNext));
/*    */   }
/*    */ 
/*    */   
/*    */   void subscribePasswordChangeSubject(Consumer<String> onNext) {
/* 75 */     this.mDisposables.add(this.mPasswordChangeSubject
/* 76 */         .subscribe(onNext));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void onCleared() {
/* 82 */     super.onCleared();
/* 83 */     File sigImageFile = (File)this.signatureImageFile.getValue();
/* 84 */     if (sigImageFile != null) {
/* 85 */       Picasso.get().invalidate(sigImageFile);
/*    */     }
/* 87 */     this.mPassword.setValue(null);
/* 88 */     this.mFileName.setValue(null);
/* 89 */     this.signatureImageFile.setValue(null);
/* 90 */     this.mDisposables.clear();
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\dialog\digitalsignature\DigitalSignatureViewModel.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */