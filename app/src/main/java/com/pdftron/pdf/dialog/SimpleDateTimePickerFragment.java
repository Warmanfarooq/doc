/*     */ package com.pdftron.pdf.dialog;
/*     */ 
/*     */ import android.app.DatePickerDialog;
/*     */ import android.app.Dialog;
/*     */ import android.app.TimePickerDialog;
/*     */ import android.content.Context;
/*     */ import android.content.DialogInterface;
/*     */ import android.os.Bundle;
/*     */ import android.text.format.DateFormat;
/*     */ import android.widget.DatePicker;
/*     */ import android.widget.TimePicker;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.fragment.app.DialogFragment;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import java.lang.annotation.Retention;
/*     */ import java.lang.annotation.RetentionPolicy;
/*     */ import java.util.Calendar;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimpleDateTimePickerFragment
/*     */   extends DialogFragment
/*     */ {
/*  24 */   public static final String TAG = SimpleDateTimePickerFragment.class.getName();
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String BUNDLE_MODE = "SimpleDateTimePicker_mode";
/*     */ 
/*     */   
/*     */   public static final String BUNDLE_ALLOW_ENTER_VALUE = "SimpleDateTimePicker_allow_enter_value";
/*     */ 
/*     */   
/*     */   public static final int MODE_DATE = 0;
/*     */ 
/*     */   
/*     */   public static final int MODE_TIME = 1;
/*     */ 
/*     */   
/*     */   private boolean mManuallyEnterValue = false;
/*     */ 
/*     */   
/*     */   private boolean mDismissedWithNoSelection = true;
/*     */ 
/*     */   
/*     */   private SimpleDatePickerListener mListener;
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSimpleDatePickerListener(SimpleDatePickerListener listener) {
/*  51 */     this.mListener = listener;
/*     */   }
/*     */   
/*     */   public static SimpleDateTimePickerFragment newInstance(int mode) {
/*  55 */     return newInstance(mode, true);
/*     */   }
/*     */   
/*     */   public static SimpleDateTimePickerFragment newInstance(int mode, boolean allowEnterValue) {
/*  59 */     SimpleDateTimePickerFragment fragment = new SimpleDateTimePickerFragment();
/*  60 */     Bundle args = new Bundle();
/*  61 */     args.putInt("SimpleDateTimePicker_mode", mode);
/*  62 */     args.putBoolean("SimpleDateTimePicker_allow_enter_value", allowEnterValue);
/*  63 */     fragment.setArguments(args);
/*  64 */     return fragment;
/*     */   }
/*     */   
/*     */   @NonNull
/*     */   public Dialog onCreateDialog(Bundle savedInstanceState) {
/*     */     TimePickerDialog timePickerDialog;
/*  70 */     int mode = 0;
/*  71 */     boolean allowEnterValue = true;
/*  72 */     if (getArguments() != null) {
/*  73 */       mode = getArguments().getInt("SimpleDateTimePicker_mode", 0);
/*  74 */       allowEnterValue = getArguments().getBoolean("SimpleDateTimePicker_allow_enter_value", true);
/*     */     } 
/*     */ 
/*     */     
/*  78 */     if (mode == 0) {
/*     */       
/*  80 */       Calendar c = Calendar.getInstance();
/*  81 */       int year = c.get(1);
/*  82 */       int month = c.get(2);
/*  83 */       int day = c.get(5);
/*     */ 
/*     */       
/*  86 */       DatePickerDialog datePickerDialog = new DatePickerDialog((Context)getActivity(), this.dateSetListener, year, month, day);
/*     */     } else {
/*     */       
/*  89 */       Calendar c = Calendar.getInstance();
/*  90 */       int hour = c.get(11);
/*  91 */       int minute = c.get(12);
/*     */ 
/*     */ 
/*     */       
/*  95 */       timePickerDialog = new TimePickerDialog((Context)getActivity(), this.timeSetListener, hour, minute, DateFormat.is24HourFormat((Context)getActivity()));
/*     */     } 
/*  97 */     if (allowEnterValue) {
/*  98 */       timePickerDialog.setButton(-2, getString(R.string.clear), new DialogInterface.OnClickListener()
/*     */           {
/*     */             public void onClick(DialogInterface dialog, int which) {
/* 101 */               SimpleDateTimePickerFragment.this.mDismissedWithNoSelection = false;
/* 102 */               if (SimpleDateTimePickerFragment.this.mListener != null) {
/* 103 */                 SimpleDateTimePickerFragment.this.mListener.onClear();
/*     */               }
/*     */             }
/*     */           });
/* 107 */       timePickerDialog.setButton(-3, getString(R.string.enter_value), new DialogInterface.OnClickListener()
/*     */           {
/*     */             public void onClick(DialogInterface dialog, int which) {
/* 110 */               SimpleDateTimePickerFragment.this.mDismissedWithNoSelection = false;
/* 111 */               SimpleDateTimePickerFragment.this.mManuallyEnterValue = true;
/*     */             }
/*     */           });
/*     */     } 
/* 115 */     return (Dialog)timePickerDialog;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDismiss(DialogInterface dialog) {
/* 120 */     super.onDismiss(dialog);
/* 121 */     if (this.mListener != null) {
/* 122 */       this.mListener.onDismiss(this.mManuallyEnterValue, this.mDismissedWithNoSelection);
/*     */     }
/*     */   }
/*     */   
/* 126 */   private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
/*     */     {
/*     */       public void onDateSet(DatePicker view, int year, int month, int day) {
/* 129 */         SimpleDateTimePickerFragment.this.mDismissedWithNoSelection = false;
/* 130 */         if (SimpleDateTimePickerFragment.this.mListener != null) {
/* 131 */           SimpleDateTimePickerFragment.this.mListener.onDateSet(view, year, month, day);
/*     */         }
/*     */       }
/*     */     };
/*     */   
/* 136 */   private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener()
/*     */     {
/*     */       public void onTimeSet(TimePicker view, int hourOfDay, int minute)
/*     */       {
/* 140 */         SimpleDateTimePickerFragment.this.mDismissedWithNoSelection = false;
/* 141 */         if (SimpleDateTimePickerFragment.this.mListener != null)
/* 142 */           SimpleDateTimePickerFragment.this.mListener.onTimeSet(view, hourOfDay, minute); 
/*     */       }
/*     */     };
/*     */   
/*     */   @Retention(RetentionPolicy.SOURCE)
/*     */   public static @interface DialogMode {}
/*     */   
/*     */   public static interface SimpleDatePickerListener {
/*     */     void onDateSet(DatePicker param1DatePicker, int param1Int1, int param1Int2, int param1Int3);
/*     */     
/*     */     void onTimeSet(TimePicker param1TimePicker, int param1Int1, int param1Int2);
/*     */     
/*     */     void onClear();
/*     */     
/*     */     void onDismiss(boolean param1Boolean1, boolean param1Boolean2);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\dialog\SimpleDateTimePickerFragment.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */