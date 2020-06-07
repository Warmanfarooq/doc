/*     */ package com.pdftron.pdf.dialog.measure;
/*     */ import android.app.AlertDialog;
/*     */ import android.app.Dialog;
/*     */ import android.content.Context;
/*     */ import android.content.DialogInterface;
/*     */ import android.os.Bundle;
/*     */ import android.text.Editable;
/*     */ import android.text.TextWatcher;
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.View;
/*     */ import android.widget.AdapterView;
/*     */ import android.widget.ArrayAdapter;
/*     */ import android.widget.Button;
/*     */ import android.widget.EditText;
/*     */ import android.widget.Spinner;
/*     */ import android.widget.SpinnerAdapter;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.fragment.app.DialogFragment;
/*     */ import androidx.fragment.app.FragmentActivity;
/*     */ import androidx.lifecycle.LifecycleOwner;
/*     */ import androidx.lifecycle.Observer;
/*     */ import androidx.lifecycle.ViewModelProviders;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ 
/*     */ public class CalibrateDialog extends DialogFragment implements AdapterView.OnItemSelectedListener {
/*  26 */   public static final String TAG = CalibrateDialog.class.getName();
/*     */   
/*     */   public static final String ANNOT_SDFOBJ = "CalibrateDialog_sdfObj";
/*     */   
/*     */   public static final String ANNOT_PAGE = "CalibrateDialog_page";
/*     */   
/*     */   public static final String WORLD_UNIT = "CalibrateDialog_worldUnit";
/*     */   
/*     */   private CalibrateViewModel mViewModel;
/*     */   private CalibrateResult mCalibrateResult;
/*     */   private Spinner mSpinner;
/*     */   private ArrayAdapter<CharSequence> mSpinnerAdapter;
/*     */   
/*     */   public static CalibrateDialog newInstance(long annot, int page, String worldUnit) {
/*  40 */     CalibrateDialog dialog = new CalibrateDialog();
/*  41 */     Bundle args = new Bundle();
/*  42 */     args.putLong("CalibrateDialog_sdfObj", annot);
/*  43 */     args.putInt("CalibrateDialog_page", page);
/*  44 */     args.putString("CalibrateDialog_worldUnit", worldUnit);
/*  45 */     dialog.setArguments(args);
/*  46 */     return dialog;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onCreate(@Nullable Bundle savedInstanceState) {
/*  51 */     super.onCreate(savedInstanceState);
/*     */     
/*  53 */     Bundle args = getArguments();
/*  54 */     if (args != null) {
/*  55 */       long annot = args.getLong("CalibrateDialog_sdfObj");
/*  56 */       int page = args.getInt("CalibrateDialog_page");
/*  57 */       this.mCalibrateResult = new CalibrateResult(annot, page);
/*  58 */       this.mCalibrateResult.worldUnit = args.getString("CalibrateDialog_worldUnit");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
/*  65 */     FragmentActivity activity = getActivity();
/*  66 */     if (activity == null) {
/*  67 */       return super.onCreateDialog(savedInstanceState);
/*     */     }
/*  69 */     this.mViewModel = (CalibrateViewModel)ViewModelProviders.of(activity).get(CalibrateViewModel.class);
/*  70 */     LayoutInflater inflater = activity.getLayoutInflater();
/*  71 */     View view = inflater.inflate(R.layout.dialog_calibrate, null);
/*  72 */     EditText editText = (EditText)view.findViewById(R.id.measure_edit_text);
/*  73 */     editText.addTextChangedListener(new TextWatcher()
/*     */         {
/*     */           public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onTextChanged(CharSequence s, int start, int before, int count) {
/*  81 */             String input = s.toString();
/*     */             try {
/*  83 */               CalibrateDialog.this.mCalibrateResult.userInput = Float.valueOf(Float.parseFloat(input));
/*  84 */             } catch (Exception exception) {}
/*     */ 
/*     */             
/*  87 */             CalibrateDialog.this.setResult();
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void afterTextChanged(Editable s) {}
/*     */         });
/*  96 */     this.mSpinner = (Spinner)view.findViewById(R.id.measure_unit_spinner);
/*     */     
/*  98 */     this.mSpinnerAdapter = ArrayAdapter.createFromResource((Context)activity, R.array.ruler_translate_unit, 17367048);
/*     */     
/* 100 */     this.mSpinnerAdapter.setDropDownViewResource(17367049);
/* 101 */     this.mSpinner.setAdapter((SpinnerAdapter)this.mSpinnerAdapter);
/* 102 */     this.mSpinner.setOnItemSelectedListener(this);
/*     */     
/* 104 */     if (this.mCalibrateResult != null && this.mCalibrateResult.worldUnit != null) {
/* 105 */       int index = this.mSpinnerAdapter.getPosition(this.mCalibrateResult.worldUnit);
/* 106 */       if (index >= 0 && index < this.mSpinnerAdapter.getCount()) {
/* 107 */         this.mSpinner.setSelection(index);
/*     */       }
/*     */     } 
/*     */     
/* 111 */     this.mViewModel.observeChanges((LifecycleOwner)this, new Observer<CalibrateResult>()
/*     */         {
/*     */           public void onChanged(@Nullable CalibrateResult result) {
/* 114 */             if (result == null) {
/* 115 */               CalibrateDialog.this.setPositiveButtonEnabled(false);
/*     */             } else {
/* 117 */               CalibrateDialog.this.setPositiveButtonEnabled(true);
/*     */             } 
/*     */           }
/*     */         });
/* 121 */     setPositiveButtonEnabled(false);
/*     */     
/* 123 */     AlertDialog.Builder builder = new AlertDialog.Builder((Context)getActivity());
/* 124 */     builder.setView(view)
/* 125 */       .setTitle(R.string.measure_calibrate_title)
/* 126 */       .setMessage(R.string.measure_calibrate_body)
/* 127 */       .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener()
/*     */         {
/*     */           public void onClick(DialogInterface dialog, int which) {
/* 130 */             CalibrateDialog.this.setResult();
/* 131 */             CalibrateDialog.this.dismiss();
/*     */           }
/* 134 */         }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
/*     */         {
/*     */           public void onClick(DialogInterface dialog, int which) {
/* 137 */             CalibrateDialog.this.mCalibrateResult.userInput = null;
/* 138 */             CalibrateDialog.this.setResult();
/* 139 */             CalibrateDialog.this.dismiss();
/*     */           }
/*     */         });
/* 142 */     return (Dialog)builder.create();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
/* 147 */     if (parent.getId() == this.mSpinner.getId() && 
/* 148 */       position >= 0 && this.mSpinnerAdapter != null) {
/* 149 */       CharSequence unit = (CharSequence)this.mSpinnerAdapter.getItem(position);
/* 150 */       if (unit != null && this.mCalibrateResult != null) {
/* 151 */         this.mCalibrateResult.worldUnit = unit.toString();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNothingSelected(AdapterView<?> parent) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDismiss(DialogInterface dialog) {
/* 164 */     super.onDismiss(dialog);
/* 165 */     this.mViewModel.complete();
/*     */   }
/*     */   
/*     */   private void setResult() {
/* 169 */     this.mViewModel.set(this.mCalibrateResult);
/*     */   }
/*     */   
/*     */   private void setPositiveButtonEnabled(boolean enabled) {
/* 173 */     AlertDialog dialog = (AlertDialog)getDialog();
/* 174 */     Button posButton = (dialog == null) ? null : dialog.getButton(-1);
/* 175 */     if (posButton != null)
/* 176 */       posButton.setEnabled(enabled); 
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\dialog\measure\CalibrateDialog.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */