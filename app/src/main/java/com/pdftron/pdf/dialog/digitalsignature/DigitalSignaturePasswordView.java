/*     */ package com.pdftron.pdf.dialog.digitalsignature;
/*     */ 
/*     */ import android.text.Editable;
/*     */ import android.text.TextWatcher;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.widget.Button;
/*     */ import android.widget.ImageView;
/*     */ import android.widget.TextView;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.appcompat.widget.Toolbar;
/*     */ import androidx.constraintlayout.widget.ConstraintLayout;
/*     */ import com.google.android.material.textfield.TextInputEditText;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.squareup.picasso.Picasso;
/*     */ import io.reactivex.subjects.PublishSubject;
/*     */ import java.io.File;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class DigitalSignaturePasswordView
/*     */ {
/*     */   private final ConstraintLayout mCertificateForm;
/*     */   private final ConstraintLayout mPasswordForm;
/*     */   private final ImageView mSigPreview;
/*     */   private final TextView mFileName;
/*     */   
/*     */   DigitalSignaturePasswordView(@NonNull ViewGroup parent, @NonNull final PublishSubject<UserEvent> eventSubject, @NonNull final PublishSubject<String> passwordChangeSubject) {
/*  33 */     this.mCertificateForm = (ConstraintLayout)parent.findViewById(R.id.certificate_form);
/*  34 */     this.mPasswordForm = (ConstraintLayout)parent.findViewById(R.id.password_form);
/*  35 */     this.mSigPreview = (ImageView)parent.findViewById(R.id.signature_preview);
/*  36 */     this.mFileName = (TextView)parent.findViewById(R.id.file_name);
/*  37 */     TextInputEditText editText = (TextInputEditText)parent.findViewById(R.id.fragment_password_dialog_password);
/*  38 */     Button okayButton = (Button)parent.findViewById(R.id.okay_button);
/*  39 */     Button addCertificateButton = (Button)parent.findViewById(R.id.add_cert_button);
/*  40 */     Toolbar navToolbar = (Toolbar)parent.findViewById(R.id.digital_sig_input_toolbar);
/*     */     
/*  42 */     navToolbar.setNavigationOnClickListener(new View.OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/*  45 */             eventSubject.onNext(UserEvent.ON_CANCEL);
/*     */           }
/*     */         });
/*     */     
/*  49 */     editText.addTextChangedListener(new TextWatcher()
/*     */         {
/*     */           public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onTextChanged(CharSequence s, int start, int before, int count) {}
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void afterTextChanged(Editable s) {
/*  62 */             passwordChangeSubject.onNext(s.toString());
/*     */           }
/*     */         });
/*     */     
/*  66 */     okayButton.setOnClickListener(new View.OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/*  69 */             eventSubject.onNext(UserEvent.ON_FINISH_PASSWORD);
/*     */           }
/*     */         });
/*     */     
/*  73 */     addCertificateButton.setOnClickListener(new View.OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/*  76 */             eventSubject.onNext(UserEvent.ON_ADD_CERTIFICATE);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   void enablePasswordMode() {
/*  82 */     this.mCertificateForm.setVisibility(8);
/*  83 */     this.mPasswordForm.setVisibility(0);
/*     */   }
/*     */   
/*     */   void disablePasswordMode() {
/*  87 */     this.mCertificateForm.setVisibility(0);
/*  88 */     this.mPasswordForm.setVisibility(8);
/*     */   }
/*     */   
/*     */   void setSignaturePreview(@NonNull File sigImage) {
/*  92 */     Picasso.get().load(sigImage).into(this.mSigPreview);
/*     */   }
/*     */   
/*     */   void setFileName(@NonNull String fileName) {
/*  96 */     this.mFileName.setText(fileName);
/*     */   }
/*     */   
/*     */   enum UserEvent {
/* 100 */     ON_FINISH_PASSWORD, ON_CANCEL, ON_ADD_CERTIFICATE;
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\dialog\digitalsignature\DigitalSignaturePasswordView.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */