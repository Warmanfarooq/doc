/*    */ package com.pdftron.pdf.dialog.digitalsignature;
/*    */ 
/*    */ import android.os.Bundle;
/*    */ import android.view.LayoutInflater;
/*    */ import android.view.View;
/*    */ import android.view.ViewGroup;
/*    */ import androidx.annotation.NonNull;
/*    */ import androidx.annotation.Nullable;
/*    */ import androidx.fragment.app.Fragment;
/*    */ import androidx.lifecycle.LifecycleOwner;
/*    */ import androidx.lifecycle.ViewModelProviders;
/*    */ import com.pdftron.pdf.tools.R;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DigitalSignatureUserInputFragment
/*    */   extends Fragment
/*    */ {
/*    */   @Nullable
/*    */   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
/* 23 */     return inflater.inflate(R.layout.tools_dialog_digital_signature_user_input, container, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
/* 28 */     super.onViewCreated(view, savedInstanceState);
/*    */     
/* 30 */     ViewGroup viewContainer = (ViewGroup)view.findViewById(R.id.container);
/* 31 */     Fragment parentFragment = getParentFragment();
/* 32 */     if (parentFragment == null) {
/* 33 */       throw new RuntimeException("This fragment should run as a child fragment of a containing parent fragment.");
/*    */     }
/* 35 */     DigitalSignatureViewModel viewModel = (DigitalSignatureViewModel)ViewModelProviders.of(parentFragment).get(DigitalSignatureViewModel.class);
/*    */ 
/*    */     
/* 38 */     new DigitalSignaturePasswordComponent(viewContainer, (LifecycleOwner)this, viewModel);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onCreate(@Nullable Bundle savedInstanceState) {
/* 43 */     super.onCreate(savedInstanceState);
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\dialog\digitalsignature\DigitalSignatureUserInputFragment.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */