/*     */ package com.pdftron.pdf.dialog.simpleinput;
/*     */ import android.app.AlertDialog;
/*     */ import android.app.Dialog;
/*     */ import android.content.Context;
/*     */ import android.content.DialogInterface;
/*     */ import android.os.Bundle;
/*     */ import android.text.Editable;
/*     */ import android.text.TextWatcher;
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.View;
/*     */ import android.widget.Button;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.annotation.StringRes;
/*     */ import androidx.fragment.app.DialogFragment;
/*     */ import androidx.fragment.app.FragmentActivity;
/*     */ import androidx.lifecycle.LifecycleOwner;
/*     */ import androidx.lifecycle.Observer;
/*     */ import androidx.lifecycle.ViewModelProviders;
/*     */ import com.google.android.material.textfield.TextInputEditText;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ 
/*     */ public class TextInputDialog extends DialogFragment {
/*  25 */   public static final String TAG = TextInputDialog.class.getName();
/*     */   
/*     */   public static final String REQUEST_CODE = "TextInputDialog_requestcode";
/*     */   
/*     */   public static final String TITLE_RES = "TextInputDialog_titleres";
/*     */   
/*     */   public static final String HINT_RES = "TextInputDialog_hintres";
/*     */   
/*     */   public static final String POSITIVE_BUTTON_RES = "TextInputDialog_positivebtnres";
/*     */   
/*     */   public static final String NEGATIVE_BUTTON_RES = "TextInputDialog_negativebtnres";
/*     */   private int mRequestCode;
/*     */   @StringRes
/*     */   private int mTitleRes;
/*     */   @StringRes
/*     */   private int mHintRes;
/*     */   @StringRes
/*     */   private int mPosRes;
/*     */   @StringRes
/*     */   private int mNegRes;
/*     */   private TextInputViewModel mViewModel;
/*     */   
/*     */   public static TextInputDialog newInstance(int requestCode, @StringRes int titleRes, @StringRes int hintRes, @StringRes int posRes, @StringRes int negRes) {
/*  48 */     TextInputDialog dialog = new TextInputDialog();
/*  49 */     Bundle args = new Bundle();
/*  50 */     args.putInt("TextInputDialog_requestcode", requestCode);
/*  51 */     args.putInt("TextInputDialog_titleres", titleRes);
/*  52 */     args.putInt("TextInputDialog_hintres", hintRes);
/*  53 */     args.putInt("TextInputDialog_positivebtnres", posRes);
/*  54 */     args.putInt("TextInputDialog_negativebtnres", negRes);
/*  55 */     dialog.setArguments(args);
/*  56 */     return dialog;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onCreate(@Nullable Bundle savedInstanceState) {
/*  61 */     super.onCreate(savedInstanceState);
/*     */     
/*  63 */     Bundle args = getArguments();
/*  64 */     if (args != null) {
/*  65 */       this.mRequestCode = args.getInt("TextInputDialog_requestcode");
/*  66 */       this.mTitleRes = args.getInt("TextInputDialog_titleres");
/*  67 */       this.mHintRes = args.getInt("TextInputDialog_hintres");
/*  68 */       this.mPosRes = args.getInt("TextInputDialog_positivebtnres");
/*  69 */       this.mNegRes = args.getInt("TextInputDialog_negativebtnres");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
/*  76 */     FragmentActivity activity = getActivity();
/*  77 */     if (activity == null) {
/*  78 */       return super.onCreateDialog(savedInstanceState);
/*     */     }
/*     */     
/*  81 */     this.mViewModel = (TextInputViewModel)ViewModelProviders.of(activity).get(TextInputViewModel.class);
/*     */     
/*  83 */     LayoutInflater inflater = activity.getLayoutInflater();
/*  84 */     View view = inflater.inflate(R.layout.dialog_text_input, null);
/*  85 */     TextInputEditText editText = (TextInputEditText)view.findViewById(R.id.text_input);
/*  86 */     editText.setHint(this.mHintRes);
/*  87 */     editText.addTextChangedListener(new TextWatcher()
/*     */         {
/*     */           public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onTextChanged(CharSequence s, int start, int before, int count) {
/*  95 */             String input = s.toString();
/*  96 */             TextInputResult textInputResult = new TextInputResult(TextInputDialog.this.mRequestCode, input);
/*  97 */             TextInputDialog.this.mViewModel.set(textInputResult);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void afterTextChanged(Editable s) {}
/*     */         });
/* 106 */     this.mViewModel.observeChanges((LifecycleOwner)this, new Observer<TextInputResult>()
/*     */         {
/*     */           public void onChanged(@Nullable TextInputResult textInputResult) {
/* 109 */             if (textInputResult == null || Utils.isNullOrEmpty(textInputResult.getResult())) {
/* 110 */               TextInputDialog.this.setPositiveButtonEnabled(false);
/*     */             } else {
/* 112 */               TextInputDialog.this.setPositiveButtonEnabled(true);
/*     */             } 
/*     */           }
/*     */         });
/* 116 */     setPositiveButtonEnabled(Utils.isNullOrEmpty((editText.getText() == null) ? null : editText
/* 117 */           .getText().toString()));
/*     */     
/* 119 */     AlertDialog.Builder builder = new AlertDialog.Builder((Context)getActivity());
/* 120 */     builder.setView(view)
/* 121 */       .setTitle(this.mTitleRes)
/* 122 */       .setPositiveButton(this.mPosRes, new DialogInterface.OnClickListener()
/*     */         {
/*     */           public void onClick(DialogInterface dialog, int which) {
/* 125 */             TextInputDialog.this.dismiss();
/*     */           }
/* 128 */         }).setNegativeButton(this.mNegRes, new DialogInterface.OnClickListener()
/*     */         {
/*     */           public void onClick(DialogInterface dialog, int which) {
/* 131 */             TextInputDialog.this.mViewModel.set(null);
/* 132 */             TextInputDialog.this.dismiss();
/*     */           }
/*     */         });
/* 135 */     return (Dialog)builder.create();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDismiss(DialogInterface dialog) {
/* 140 */     super.onDismiss(dialog);
/* 141 */     this.mViewModel.complete();
/*     */   }
/*     */   
/*     */   private void setPositiveButtonEnabled(boolean enabled) {
/* 145 */     AlertDialog dialog = (AlertDialog)getDialog();
/* 146 */     Button posButton = (dialog == null) ? null : dialog.getButton(-1);
/* 147 */     if (posButton != null)
/* 148 */       posButton.setEnabled(enabled); 
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\dialog\simpleinput\TextInputDialog.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */