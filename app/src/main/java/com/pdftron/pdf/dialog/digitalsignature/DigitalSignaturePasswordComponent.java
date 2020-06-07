/*    */ package com.pdftron.pdf.dialog.digitalsignature;
/*    */ 
/*    */ import android.view.ViewGroup;
/*    */ import androidx.annotation.NonNull;
/*    */ import androidx.annotation.Nullable;
/*    */ import androidx.lifecycle.LifecycleOwner;
/*    */ import androidx.lifecycle.Observer;
/*    */ import io.reactivex.functions.Consumer;
/*    */ import io.reactivex.subjects.PublishSubject;
/*    */ import java.io.File;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class DigitalSignaturePasswordComponent
/*    */ {
/*    */   private final DigitalSignaturePasswordView mView;
/*    */   private final DigitalSignatureViewModel mViewmodel;
/*    */   
/*    */   DigitalSignaturePasswordComponent(@NonNull ViewGroup parent, @NonNull LifecycleOwner lifecycleOwner, @NonNull DigitalSignatureViewModel viewmodel) {
/* 23 */     PublishSubject<DigitalSignaturePasswordView.UserEvent> eventSubject = viewmodel.getEventSubject();
/* 24 */     PublishSubject<String> passwordChangeSubject = viewmodel.getPasswordChangeSubject();
/* 25 */     this.mView = new DigitalSignaturePasswordView(parent, eventSubject, passwordChangeSubject);
/* 26 */     this.mViewmodel = viewmodel;
/* 27 */     this.mViewmodel.subscribePasswordChangeSubject(new Consumer<String>()
/*    */         {
/*    */           public void accept(String password) throws Exception {
/* 30 */             DigitalSignaturePasswordComponent.this.mViewmodel.mPassword.setValue(password);
/*    */           }
/*    */         });
/*    */     
/* 34 */     this.mViewmodel.signatureImageFile.observe(lifecycleOwner, new Observer<File>()
/*    */         {
/*    */           public void onChanged(@Nullable File file) {
/* 37 */             if (file != null) {
/* 38 */               DigitalSignaturePasswordComponent.this.mView.setSignaturePreview(file);
/*    */             }
/*    */           }
/*    */         });
/*    */     
/* 43 */     this.mViewmodel.mFileName.observe(lifecycleOwner, new Observer<String>()
/*    */         {
/*    */           public void onChanged(@Nullable String s) {
/* 46 */             if (s != null) {
/* 47 */               DigitalSignaturePasswordComponent.this.mViewmodel.mIsPasswordState.setValue(Boolean.TRUE);
/* 48 */               DigitalSignaturePasswordComponent.this.mView.setFileName(s);
/*    */             } else {
/* 50 */               DigitalSignaturePasswordComponent.this.mViewmodel.mIsPasswordState.setValue(Boolean.FALSE);
/*    */             } 
/*    */           }
/*    */         });
/*    */     
/* 55 */     this.mViewmodel.mIsPasswordState.observe(lifecycleOwner, new Observer<Boolean>()
/*    */         {
/*    */           public void onChanged(@Nullable Boolean isPasswordState) {
/* 58 */             if (isPasswordState != null)
/* 59 */               if (isPasswordState.booleanValue()) {
/* 60 */                 DigitalSignaturePasswordComponent.this.mView.enablePasswordMode();
/*    */               } else {
/* 62 */                 DigitalSignaturePasswordComponent.this.mView.disablePasswordMode();
/*    */               }  
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\dialog\digitalsignature\DigitalSignaturePasswordComponent.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */