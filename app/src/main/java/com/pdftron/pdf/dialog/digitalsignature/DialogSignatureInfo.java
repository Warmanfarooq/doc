/*    */ package com.pdftron.pdf.dialog.digitalsignature;
/*    */ 
/*    */ import android.app.AlertDialog;
/*    */ import android.content.Context;
/*    */ import android.content.DialogInterface;
/*    */ import android.view.LayoutInflater;
/*    */ import android.view.View;
/*    */ import androidx.annotation.Nullable;
/*    */ import com.pdftron.pdf.tools.R;
/*    */ import com.pdftron.pdf.utils.Utils;
/*    */ 
/*    */ public class DialogSignatureInfo
/*    */   extends AlertDialog
/*    */ {
/*    */   private SignatureInfoView mPermission;
/*    */   private SignatureInfoView mName;
/*    */   private SignatureInfoView mLocation;
/*    */   private SignatureInfoView mContactInfo;
/*    */   private SignatureInfoView mReason;
/*    */   private SignatureInfoView mSigningTime;
/*    */   
/*    */   public DialogSignatureInfo(Context context) {
/* 23 */     super(context);
/*    */     
/* 25 */     View view = LayoutInflater.from(getContext()).inflate(R.layout.tools_dialog_signatureinfo, null);
/* 26 */     this.mPermission = (SignatureInfoView)view.findViewById(R.id.sig_info_permission);
/* 27 */     this.mName = (SignatureInfoView)view.findViewById(R.id.sig_info_name);
/* 28 */     this.mLocation = (SignatureInfoView)view.findViewById(R.id.sig_info_location);
/* 29 */     this.mContactInfo = (SignatureInfoView)view.findViewById(R.id.sig_info_contact);
/* 30 */     this.mReason = (SignatureInfoView)view.findViewById(R.id.sig_info_reason);
/* 31 */     this.mSigningTime = (SignatureInfoView)view.findViewById(R.id.sig_info_signing_time);
/*    */     
/* 33 */     setView(view);
/*    */     
/* 35 */     setTitle(context.getString(R.string.tools_digitalsignature_signature_info));
/*    */     
/* 37 */     setButton(-1, context.getString(R.string.ok), (OnClickListener)null);
/*    */   }
/*    */   
/*    */   public void setLocation(@Nullable String text) {
/* 41 */     this.mLocation.setDetails(text);
/* 42 */     this.mLocation.setVisibility(Utils.isNullOrEmpty(text) ? 8 : 0);
/*    */   }
/*    */   
/*    */   public void setReason(@Nullable String reason) {
/* 46 */     this.mReason.setDetails(reason);
/* 47 */     this.mReason.setVisibility(Utils.isNullOrEmpty(reason) ? 8 : 0);
/*    */   }
/*    */   
/*    */   public void setName(@Nullable String name) {
/* 51 */     this.mName.setDetails(name);
/* 52 */     this.mName.setVisibility(Utils.isNullOrEmpty(name) ? 8 : 0);
/*    */   }
/*    */   
/*    */   public void setContactInfo(@Nullable String contactInfo) {
/* 56 */     this.mContactInfo.setDetails(contactInfo);
/* 57 */     this.mContactInfo.setVisibility(Utils.isNullOrEmpty(contactInfo) ? 8 : 0);
/*    */   }
/*    */   
/*    */   public void setSigningTime(@Nullable String signingTimeString) {
/* 61 */     this.mSigningTime.setDetails(signingTimeString);
/* 62 */     this.mSigningTime.setVisibility(Utils.isNullOrEmpty(signingTimeString) ? 8 : 0);
/*    */   }
/*    */   
/*    */   public void setDocumentPermission(@Nullable String documentPermissionMsg) {
/* 66 */     this.mPermission.setDetails(documentPermissionMsg);
/* 67 */     this.mPermission.setVisibility(Utils.isNullOrEmpty(documentPermissionMsg) ? 8 : 0);
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\dialog\digitalsignature\DialogSignatureInfo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */