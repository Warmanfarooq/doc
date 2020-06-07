/*     */ package com.pdftron.pdf.dialog.digitalsignature;
/*     */ 
/*     */ import android.app.Activity;
/*     */ import android.content.ContentResolver;
/*     */ import android.content.Context;
/*     */ import android.content.Intent;
/*     */ import android.graphics.PointF;
/*     */ import android.net.Uri;
/*     */ import android.os.Bundle;
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.fragment.app.Fragment;
/*     */ import androidx.fragment.app.FragmentActivity;
/*     */ import androidx.lifecycle.LifecycleOwner;
/*     */ import androidx.lifecycle.Observer;
/*     */ import androidx.lifecycle.ViewModelProviders;
/*     */ import com.pdftron.pdf.Page;
/*     */ import com.pdftron.pdf.dialog.signature.SignatureDialogFragment;
/*     */ import com.pdftron.pdf.interfaces.OnCreateSignatureListener;
/*     */ import com.pdftron.pdf.tools.DigitalSignature;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.utils.CommonToast;
/*     */ import com.pdftron.pdf.utils.StampManager;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import com.pdftron.pdf.utils.ViewerUtils;
/*     */ import io.reactivex.functions.Consumer;
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
/*     */ public class DigitalSignatureDialogFragment
/*     */   extends SignatureDialogFragment
/*     */ {
/*     */   private static final String DIGITAL_SIG_USER_INPUT_FRAGMENT_ID = "digital_signature_user_input_fragment";
/*     */   private boolean mHasDefaultKeystore = false;
/*  46 */   private Uri mImageSignature = null;
/*     */   
/*     */   private String mSignatureFilePath;
/*     */   private OnCreateSignatureListener.OnKeystoreUpdatedListener mOnKeystoreUpdatedListener;
/*     */   
/*     */   public void onCreate(@Nullable Bundle savedInstanceState) {
/*  52 */     super.onCreate(savedInstanceState);
/*     */     
/*  54 */     Bundle args = getArguments();
/*  55 */     if (args != null) {
/*  56 */       this.mHasDefaultKeystore = args.getBoolean("bundle_digital_signature", false);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
/*  64 */     View root = super.onCreateView(inflater, container, savedInstanceState);
/*     */     
/*  66 */     DigitalSignatureViewModel viewmodel = (DigitalSignatureViewModel)ViewModelProviders.of((Fragment)this).get(DigitalSignatureViewModel.class);
/*  67 */     viewmodel.mKeyStoreFile.observe((LifecycleOwner)this, new Observer<Uri>()
/*     */         {
/*     */           public void onChanged(@Nullable Uri uri)
/*     */           {
/*  71 */             if (DigitalSignatureDialogFragment.this.mOnKeystoreUpdatedListener != null) {
/*  72 */               DigitalSignatureDialogFragment.this.mOnKeystoreUpdatedListener.onKeystoreFileUpdated(uri);
/*     */             }
/*     */           }
/*     */         });
/*  76 */     viewmodel.mPassword.observe((LifecycleOwner)this, new Observer<String>()
/*     */         {
/*     */           public void onChanged(@Nullable String s)
/*     */           {
/*  80 */             if (DigitalSignatureDialogFragment.this.mOnKeystoreUpdatedListener != null) {
/*  81 */               DigitalSignatureDialogFragment.this.mOnKeystoreUpdatedListener.onKeystorePasswordUpdated(s);
/*     */             }
/*     */           }
/*     */         });
/*  85 */     return root;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onActivityResult(int requestCode, int resultCode, Intent data) {
/*  90 */     super.onActivityResult(requestCode, resultCode, data);
/*  91 */     FragmentActivity activity = getActivity();
/*  92 */     if (activity == null) {
/*     */       return;
/*     */     }
/*     */     
/*  96 */     if (requestCode == 10018 || requestCode == 10019) {
/*     */       
/*  98 */       if (data == null) {
/*     */         return;
/*     */       }
/* 101 */       Uri uri = data.getData();
/* 102 */       if (uri != null) {
/*     */         
/* 104 */         ContentResolver contentResolver = activity.getContentResolver();
/* 105 */         if (contentResolver != null)
/*     */         {
/* 107 */           if (Utils.uriHasReadPermission(getContext(), uri)) {
/*     */             
/* 109 */             DigitalSignatureViewModel vm = (DigitalSignatureViewModel)ViewModelProviders.of((Fragment)this).get(DigitalSignatureViewModel.class);
/*     */             
/* 111 */             vm.setKeystoreFileUri(uri);
/*     */             
/* 113 */             String name = Utils.getUriDisplayName((Context)activity, uri);
/* 114 */             vm.setFileName(name);
/*     */           } 
/*     */         }
/*     */       } 
/* 118 */     } else if (requestCode == 10003) {
/* 119 */       Uri uri = ViewerUtils.getImageUriFromIntent(data, (Activity)activity, this.mImageSignature);
/* 120 */       if (uri != null) {
/* 121 */         this.mSignatureFilePath = StampManager.getInstance().createSignatureFromImage((Context)activity, uri);
/* 122 */         if (this.mSignatureFilePath != null) {
/* 123 */           showInputScreen(this.mSignatureFilePath);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void showInputScreen(@NonNull String signatureFilePath) {
/* 130 */     if (this.mHasDefaultKeystore) {
/* 131 */       if (this.mOnCreateSignatureListener != null && this.mSignatureFilePath != null) {
/* 132 */         this.mOnCreateSignatureListener.onSignatureCreated(this.mSignatureFilePath);
/*     */       }
/* 134 */       dismiss();
/*     */     } else {
/*     */       
/* 137 */       Context context = getContext();
/* 138 */       if (context == null) {
/* 139 */         throw new RuntimeException("This fragment must have a context");
/*     */       }
/*     */       
/* 142 */       Fragment fragment = new DigitalSignatureUserInputFragment();
/*     */       
/* 144 */       DigitalSignatureViewModel viewModel = (DigitalSignatureViewModel)ViewModelProviders.of((Fragment)this).get(DigitalSignatureViewModel.class);
/*     */       
/* 146 */       Page page = StampManager.getInstance().getSignature(signatureFilePath);
/* 147 */       String imageFile = DigitalSignature.createSignatureImageFile(context, page);
/* 148 */       viewModel.setImageFilePath(imageFile);
/* 149 */       viewModel.subscribeEventSubject(new Consumer<DigitalSignaturePasswordView.UserEvent>()
/*     */           {
/*     */             public void accept(DigitalSignaturePasswordView.UserEvent userEvent) throws Exception {
/* 152 */               switch (userEvent) {
/*     */                 case ON_CANCEL:
/* 154 */                   DigitalSignatureDialogFragment.this.dismiss();
/*     */                   break;
/*     */ 
/*     */                 
/*     */                 case ON_FINISH_PASSWORD:
/* 159 */                   if (DigitalSignatureDialogFragment.this.mOnCreateSignatureListener != null && DigitalSignatureDialogFragment.this.mSignatureFilePath != null) {
/* 160 */                     DigitalSignatureDialogFragment.this.mOnCreateSignatureListener.onSignatureCreated(DigitalSignatureDialogFragment.this.mSignatureFilePath);
/*     */                   }
/* 162 */                   DigitalSignatureDialogFragment.this.dismiss();
/*     */                   break;
/*     */                 
/*     */                 case ON_ADD_CERTIFICATE:
/* 166 */                   DigitalSignatureDialogFragment.this.startKeystoreFilePicker();
/*     */                   break;
/*     */               } 
/*     */ 
/*     */             
/*     */             }
/*     */           });
/* 173 */       getChildFragmentManager().beginTransaction()
/* 174 */         .add(R.id.fragment_container, fragment, "digital_signature_user_input_fragment")
/* 175 */         .addToBackStack("digital_signature_user_input_fragment")
/* 176 */         .commit();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onSignatureFromImage(@Nullable PointF targetPoint, int targetPage, @Nullable Long widget) {
/* 183 */     this.mImageSignature = ViewerUtils.openImageIntent((Fragment)this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onSignatureSelected(@NonNull String filepath) {
/* 188 */     this.mSignatureFilePath = filepath;
/* 189 */     showInputScreen(this.mSignatureFilePath);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onSignatureCreated(@Nullable String filepath) {
/* 194 */     if (filepath != null) {
/* 195 */       this.mSignatureFilePath = filepath;
/* 196 */       showInputScreen(this.mSignatureFilePath);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void startKeystoreFilePicker() {
/* 201 */     CommonToast.showText(getContext(), R.string.tools_digitalsignature_add_certificate, 1);
/*     */     
/* 203 */     if (Utils.isKitKat()) {
/* 204 */       String[] fileMimeTypes = { "application/x-pkcs12" };
/*     */ 
/*     */       
/* 207 */       Intent intent = Utils.createSystemPickerIntent(fileMimeTypes);
/* 208 */       startActivityForResult(intent, 10018);
/*     */     } else {
/* 210 */       Intent intent = new Intent("android.intent.action.GET_CONTENT");
/* 211 */       intent.setAction("android.intent.action.GET_CONTENT");
/* 212 */       intent.setType("application/x-pkcs12");
/* 213 */       intent.putExtra("android.content.extra.SHOW_ADVANCED", true);
/* 214 */       startActivityForResult(intent, 10018);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOnKeystoreUpdatedListener(OnCreateSignatureListener.OnKeystoreUpdatedListener onKeystoreUpdatedListener) {
/* 220 */     this.mOnKeystoreUpdatedListener = onKeystoreUpdatedListener;
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\dialog\digitalsignature\DigitalSignatureDialogFragment.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */