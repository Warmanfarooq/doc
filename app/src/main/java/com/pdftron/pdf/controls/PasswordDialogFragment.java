/*     */ package com.pdftron.pdf.controls;
/*     */ 
/*     */ import android.app.AlertDialog;
/*     */ import android.app.Dialog;
/*     */ import android.content.Context;
/*     */ import android.content.DialogInterface;
/*     */ import android.os.Bundle;
/*     */ import android.text.method.HideReturnsTransformationMethod;
/*     */ import android.text.method.PasswordTransformationMethod;
/*     */ import android.text.method.TransformationMethod;
/*     */ import android.view.KeyEvent;
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.View;
/*     */ import android.widget.CheckBox;
/*     */ import android.widget.CompoundButton;
/*     */ import android.widget.EditText;
/*     */ import android.widget.TextView;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.fragment.app.DialogFragment;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import java.io.File;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PasswordDialogFragment
/*     */   extends DialogFragment
/*     */ {
/*     */   private PasswordDialogFragmentListener mListener;
/*     */   private File mFile;
/*     */   private boolean mForcedDismiss;
/*     */   private String mPath;
/*     */   private int mFileType;
/*  73 */   private int mMessageId = -1;
/*     */ 
/*     */   
/*     */   private String mId;
/*     */ 
/*     */   
/*     */   private EditText mPasswordEditText;
/*     */ 
/*     */   
/*     */   private static final String KEY_FILE = "key_file";
/*     */ 
/*     */   
/*     */   private static final String KEY_FILETYPE = "key_filetype";
/*     */   
/*     */   private static final String KEY_PATH = "key_path";
/*     */   
/*     */   private static final String KEY_ID = "key_id";
/*     */   
/*     */   private static final String KEY_HINT = "key_hint";
/*     */ 
/*     */   
/*     */   public static PasswordDialogFragment newInstance(int fileType, File file, String path, String id) {
/*  95 */     PasswordDialogFragment fragment = new PasswordDialogFragment();
/*  96 */     Bundle bundle = new Bundle();
/*  97 */     bundle.putSerializable("key_file", file);
/*  98 */     bundle.putInt("key_filetype", fileType);
/*  99 */     bundle.putString("key_path", path);
/* 100 */     bundle.putString("key_id", id);
/* 101 */     fragment.setArguments(bundle);
/*     */     
/* 103 */     return fragment;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PasswordDialogFragment newInstance(int fileType, File file, String path, String id, String hint) {
/* 110 */     PasswordDialogFragment fragment = new PasswordDialogFragment();
/* 111 */     Bundle bundle = new Bundle();
/* 112 */     bundle.putSerializable("key_file", file);
/* 113 */     bundle.putInt("key_filetype", fileType);
/* 114 */     bundle.putString("key_path", path);
/* 115 */     bundle.putString("key_id", id);
/* 116 */     bundle.putString("key_hint", hint);
/* 117 */     fragment.setArguments(bundle);
/*     */     
/* 119 */     return fragment;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setListener(PasswordDialogFragmentListener listener) {
/* 127 */     this.mListener = listener;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public Dialog onCreateDialog(Bundle savedInstanceState) {
/* 137 */     this.mFile = (File)getArguments().getSerializable("key_file");
/* 138 */     this.mFileType = getArguments().getInt("key_filetype");
/* 139 */     this.mPath = getArguments().getString("key_path");
/* 140 */     this.mId = getArguments().getString("key_id");
/* 141 */     String hint = getArguments().getString("key_hint");
/*     */     
/* 143 */     AlertDialog.Builder builder = new AlertDialog.Builder((Context)getActivity());
/*     */     
/* 145 */     LayoutInflater inflater = getActivity().getLayoutInflater();
/* 146 */     View dialog = inflater.inflate(R.layout.fragment_password_dialog, null);
/*     */     
/* 148 */     this.mPasswordEditText = (EditText)dialog.findViewById(R.id.fragment_password_dialog_password);
/* 149 */     if (!Utils.isNullOrEmpty(hint)) {
/* 150 */       this.mPasswordEditText.setHint(hint);
/*     */     }
/* 152 */     this.mPasswordEditText.setImeOptions(2);
/* 153 */     this.mPasswordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener()
/*     */         {
/*     */           
/*     */           public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
/*     */           {
/* 158 */             if (actionId == 2) {
/* 159 */               PasswordDialogFragment.this.onPositiveClicked();
/* 160 */               return true;
/*     */             } 
/* 162 */             return false;
/*     */           }
/*     */         });
/* 165 */     this.mPasswordEditText.setOnKeyListener(new View.OnKeyListener()
/*     */         {
/*     */           public boolean onKey(View v, int keyCode, KeyEvent event) {
/* 168 */             if (keyCode == 66) {
/* 169 */               PasswordDialogFragment.this.onPositiveClicked();
/* 170 */               return true;
/*     */             } 
/* 172 */             return false;
/*     */           }
/*     */         });
/*     */     
/* 176 */     CheckBox showPassword = (CheckBox)dialog.findViewById(R.id.show_password);
/* 177 */     showPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
/*     */         {
/*     */           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
/* 180 */             if (!isChecked) {
/*     */               
/* 182 */               PasswordDialogFragment.this.mPasswordEditText.setTransformationMethod((TransformationMethod)PasswordTransformationMethod.getInstance());
/* 183 */               PasswordDialogFragment.this.mPasswordEditText.setSelection(PasswordDialogFragment.this.mPasswordEditText.getText().length());
/*     */             } else {
/*     */               
/* 186 */               PasswordDialogFragment.this.mPasswordEditText.setTransformationMethod((TransformationMethod)HideReturnsTransformationMethod.getInstance());
/* 187 */               PasswordDialogFragment.this.mPasswordEditText.setSelection(PasswordDialogFragment.this.mPasswordEditText.getText().length());
/*     */             } 
/*     */           }
/*     */         });
/*     */     
/* 192 */     builder.setView(dialog)
/* 193 */       .setTitle(R.string.dialog_password_title)
/* 194 */       .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener()
/*     */         {
/*     */           public void onClick(DialogInterface dialog, int which)
/*     */           {
/* 198 */             PasswordDialogFragment.this.onPositiveClicked();
/*     */           }
/* 201 */         }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
/*     */         {
/*     */           public void onClick(DialogInterface dialog, int which)
/*     */           {
/* 205 */             PasswordDialogFragment.this.getDialog().cancel();
/* 206 */             if (null != PasswordDialogFragment.this.mListener) {
/* 207 */               PasswordDialogFragment.this.mListener.onPasswordDialogNegativeClick(PasswordDialogFragment.this.mFileType, PasswordDialogFragment.this.mFile, PasswordDialogFragment.this.mPath);
/*     */             }
/*     */           }
/*     */         });
/* 211 */     if (this.mMessageId != -1) {
/* 212 */       builder.setMessage(this.mMessageId);
/*     */     }
/*     */     
/* 215 */     final AlertDialog alertDialog = builder.create();
/*     */ 
/*     */     
/* 218 */     this.mPasswordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener()
/*     */         {
/*     */           public void onFocusChange(View view, boolean hasFocus) {
/* 221 */             if (hasFocus && alertDialog.getWindow() != null) {
/* 222 */               alertDialog.getWindow().setSoftInputMode(5);
/*     */             }
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 228 */     return (Dialog)alertDialog;
/*     */   }
/*     */   
/*     */   private void onPositiveClicked() {
/* 232 */     String password = this.mPasswordEditText.getText().toString().trim();
/* 233 */     this.mForcedDismiss = true;
/* 234 */     if (getDialog().isShowing()) {
/* 235 */       getDialog().dismiss();
/*     */     }
/* 237 */     if (null != this.mListener) {
/* 238 */       this.mListener.onPasswordDialogPositiveClick(this.mFileType, this.mFile, this.mPath, password, this.mId);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDismiss(DialogInterface dialog) {
/* 247 */     super.onDismiss(dialog);
/* 248 */     if (null != this.mListener) {
/* 249 */       this.mListener.onPasswordDialogDismiss(this.mForcedDismiss);
/* 250 */       this.mListener = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCancel(DialogInterface dialog) {
/* 259 */     super.onCancel(dialog);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMessage(int messageId) {
/* 268 */     this.mMessageId = messageId;
/*     */   }
/*     */   
/*     */   public static interface PasswordDialogFragmentListener {
/*     */     void onPasswordDialogPositiveClick(int param1Int, File param1File, String param1String1, String param1String2, String param1String3);
/*     */     
/*     */     void onPasswordDialogNegativeClick(int param1Int, File param1File, String param1String);
/*     */     
/*     */     void onPasswordDialogDismiss(boolean param1Boolean);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\controls\PasswordDialogFragment.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */