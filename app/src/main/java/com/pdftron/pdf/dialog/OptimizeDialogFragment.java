/*     */ package com.pdftron.pdf.dialog;
/*     */ 
/*     */ import android.app.AlertDialog;
/*     */ import android.app.Dialog;
/*     */ import android.content.Context;
/*     */ import android.content.DialogInterface;
/*     */ import android.os.Bundle;
/*     */ import android.text.SpannableString;
/*     */ import android.text.style.UnderlineSpan;
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.widget.AdapterView;
/*     */ import android.widget.ArrayAdapter;
/*     */ import android.widget.RadioGroup;
/*     */ import android.widget.Spinner;
/*     */ import android.widget.SpinnerAdapter;
/*     */ import android.widget.TextView;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.fragment.app.DialogFragment;
/*     */ import com.pdftron.pdf.Optimizer;
/*     */ import com.pdftron.pdf.PDFDoc;
/*     */ import com.pdftron.pdf.model.OptimizeParams;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.Utils;
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
/*     */ public class OptimizeDialogFragment
/*     */   extends DialogFragment
/*     */ {
/*     */   static final int COMPRESSION_COLOR_MODE_RETAIN = 0;
/*     */   static final int COMPRESSION_COLOR_MODE_PNG = 1;
/*     */   static final int COMPRESSION_COLOR_MODE_JPEG = 2;
/*     */   static final int COMPRESSION_COLOR_MODE_JPEG2000 = 3;
/*     */   static final int COMPRESSION_MONO_MODE_PNG = 0;
/*     */   static final int COMPRESSION_MONO_MODE_JPIG2 = 1;
/*     */   static final int COMPRESSION_QUALITY_LOW = 0;
/*     */   static final int COMPRESSION_QUALITY_MEDIUM = 1;
/*     */   static final int COMPRESSION_QUALITY_HIGH = 2;
/*     */   static final int COMPRESSION_QUALITY_MAX = 3;
/*     */   static final int DOWNSAMPLING_MAX_50 = 0;
/*     */   static final int DOWNSAMPLING_MAX_72 = 1;
/*     */   static final int DOWNSAMPLING_MAX_96 = 2;
/*     */   static final int DOWNSAMPLING_MAX_120 = 3;
/*     */   static final int DOWNSAMPLING_MAX_150 = 4;
/*     */   static final int DOWNSAMPLING_MAX_225 = 5;
/*     */   static final int DOWNSAMPLING_MAX_300 = 6;
/*     */   static final int DOWNSAMPLING_MAX_600 = 7;
/*     */   static final int DOWNSAMPLING_RESAMPLE_50 = 0;
/*     */   static final int DOWNSAMPLING_RESAMPLE_72 = 1;
/*     */   static final int DOWNSAMPLING_RESAMPLE_96 = 2;
/*     */   static final int DOWNSAMPLING_RESAMPLE_150 = 3;
/*     */   static final int DOWNSAMPLING_RESAMPLE_225 = 4;
/*  65 */   private int mCurrentView = 0; private OptimizeDialogFragmentListener mListener; Spinner mMaxDpiSpinner; Spinner mResampleDpiSpinner;
/*     */   Spinner mCompressionColorModeSpinner;
/*     */   Spinner mCompressionMonoModeSpinner;
/*     */   
/*     */   public void setListener(OptimizeDialogFragmentListener listener) {
/*  70 */     this.mListener = listener;
/*     */   }
/*     */   Spinner mCompressionQualitySpinner; ViewGroup mPresetsLayout;
/*     */   ViewGroup mCustomLayout;
/*     */   
/*     */   public static OptimizeDialogFragment newInstance() {
/*  76 */     return new OptimizeDialogFragment();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ViewGroup mQualityLayout;
/*     */ 
/*     */ 
/*     */   
/*     */   TextView mToggleButton;
/*     */ 
/*     */ 
/*     */   
/*     */   RadioGroup mBasicOptions;
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCreate(Bundle savedInstanceState) {
/*  95 */     super.onCreate(savedInstanceState);
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public Dialog onCreateDialog(Bundle savedInstanceState) {
/* 101 */     AlertDialog.Builder builder = new AlertDialog.Builder((Context)getActivity());
/* 102 */     LayoutInflater inflater = getActivity().getLayoutInflater();
/* 103 */     View view = inflater.inflate(R.layout.controls_fragment_optimize_dialog, null);
/* 104 */     builder.setView(view);
/*     */     
/* 106 */     builder.setPositiveButton(getActivity().getResources().getString(R.string.ok), new DialogInterface.OnClickListener()
/*     */         {
/*     */           public void onClick(DialogInterface dialog, int which) {
/* 109 */             if (OptimizeDialogFragment.this.mListener != null) {
/* 110 */               OptimizeParams result = OptimizeDialogFragment.this.processOptimizeRequest();
/* 111 */               OptimizeDialogFragment.this.mListener.onOptimizeClicked(result);
/*     */             } 
/* 113 */             OptimizeDialogFragment.this.dismiss();
/*     */           }
/*     */         });
/* 116 */     builder.setNegativeButton(getActivity().getResources().getString(R.string.cancel), new DialogInterface.OnClickListener()
/*     */         {
/*     */           public void onClick(DialogInterface dialog, int which) {
/* 119 */             OptimizeDialogFragment.this.dismiss();
/*     */           }
/*     */         });
/*     */     
/* 123 */     this.mBasicOptions = (RadioGroup)view.findViewById(R.id.radio_basic_group);
/* 124 */     this.mBasicOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
/*     */         {
/*     */           public void onCheckedChanged(RadioGroup group, int checkedId) {
/* 127 */             OptimizeDialogFragment.this.updateAdvancedToMatchPresets();
/*     */           }
/*     */         });
/*     */     
/* 131 */     this.mMaxDpiSpinner = (Spinner)view.findViewById(R.id.max_dpi_spinner);
/* 132 */     ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.optimize_downsampling_max_options, 17367048);
/*     */     
/* 134 */     adapter.setDropDownViewResource(17367049);
/* 135 */     this.mMaxDpiSpinner.setAdapter((SpinnerAdapter)adapter);
/* 136 */     this.mMaxDpiSpinner.setSelection(5);
/*     */     
/* 138 */     this.mResampleDpiSpinner = (Spinner)view.findViewById(R.id.resample_dpi_spinner);
/* 139 */     adapter = ArrayAdapter.createFromResource(getContext(), R.array.optimize_downsampling_resample_options, 17367048);
/*     */     
/* 141 */     adapter.setDropDownViewResource(17367049);
/* 142 */     this.mResampleDpiSpinner.setAdapter((SpinnerAdapter)adapter);
/* 143 */     this.mResampleDpiSpinner.setSelection(3);
/*     */     
/* 145 */     this.mCompressionColorModeSpinner = (Spinner)view.findViewById(R.id.compression_color_mode_spinner);
/* 146 */     adapter = ArrayAdapter.createFromResource(getContext(), R.array.optimize_compression_color_mode, 17367048);
/*     */     
/* 148 */     adapter.setDropDownViewResource(17367049);
/* 149 */     this.mCompressionColorModeSpinner.setAdapter((SpinnerAdapter)adapter);
/* 150 */     this.mCompressionColorModeSpinner.setSelection(2);
/* 151 */     this.mCompressionColorModeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
/*     */         {
/*     */           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
/* 154 */             if (position == 2 || position == 3) {
/*     */               
/* 156 */               OptimizeDialogFragment.this.mQualityLayout.setVisibility(0);
/*     */             } else {
/* 158 */               OptimizeDialogFragment.this.mQualityLayout.setVisibility(8);
/*     */             } 
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onNothingSelected(AdapterView<?> parent) {}
/*     */         });
/* 168 */     this.mCompressionMonoModeSpinner = (Spinner)view.findViewById(R.id.compression_mono_mode_spinner);
/* 169 */     adapter = ArrayAdapter.createFromResource(getContext(), R.array.optimize_compression_mono_mode, 17367048);
/*     */     
/* 171 */     adapter.setDropDownViewResource(17367049);
/* 172 */     this.mCompressionMonoModeSpinner.setAdapter((SpinnerAdapter)adapter);
/* 173 */     this.mCompressionMonoModeSpinner.setSelection(1);
/*     */     
/* 175 */     this.mCompressionQualitySpinner = (Spinner)view.findViewById(R.id.compression_quality_spinner);
/* 176 */     adapter = ArrayAdapter.createFromResource(getContext(), R.array.optimize_quality_options, 17367048);
/*     */     
/* 178 */     adapter.setDropDownViewResource(17367049);
/* 179 */     this.mCompressionQualitySpinner.setAdapter((SpinnerAdapter)adapter);
/* 180 */     this.mCompressionQualitySpinner.setSelection(2);
/*     */     
/* 182 */     this.mPresetsLayout = (ViewGroup)view.findViewById(R.id.basic_layout);
/* 183 */     this.mCustomLayout = (ViewGroup)view.findViewById(R.id.advanced_layout);
/*     */     
/* 185 */     this.mToggleButton = (TextView)view.findViewById(R.id.optimize_advanced);
/* 186 */     this.mToggleButton.setOnClickListener(new View.OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/* 189 */             if (OptimizeDialogFragment.this.mCurrentView == 0) {
/* 190 */               OptimizeDialogFragment.this.mCurrentView = 1;
/* 191 */               SpannableString content = new SpannableString(OptimizeDialogFragment.this.getString(R.string.optimize_basic));
/* 192 */               content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
/* 193 */               OptimizeDialogFragment.this.mToggleButton.setText((CharSequence)content);
/* 194 */               OptimizeDialogFragment.this.mPresetsLayout.setVisibility(8);
/* 195 */               OptimizeDialogFragment.this.mCustomLayout.setVisibility(0);
/*     */             } else {
/* 197 */               OptimizeDialogFragment.this.mCurrentView = 0;
/* 198 */               OptimizeDialogFragment.this.updateAdvancedToMatchPresets();
/* 199 */               SpannableString content = new SpannableString(OptimizeDialogFragment.this.getString(R.string.optimize_advanced));
/* 200 */               content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
/* 201 */               OptimizeDialogFragment.this.mToggleButton.setText((CharSequence)content);
/* 202 */               OptimizeDialogFragment.this.mPresetsLayout.setVisibility(0);
/* 203 */               OptimizeDialogFragment.this.mCustomLayout.setVisibility(8);
/*     */             } 
/*     */           }
/*     */         });
/*     */     
/* 208 */     this.mQualityLayout = (ViewGroup)view.findViewById(R.id.quality_layout);
/* 209 */     if (this.mCompressionColorModeSpinner.getSelectedItemPosition() == 2 || this.mCompressionColorModeSpinner
/* 210 */       .getSelectedItemPosition() == 3) {
/* 211 */       this.mQualityLayout.setVisibility(0);
/*     */     } else {
/* 213 */       this.mQualityLayout.setVisibility(8);
/*     */     } 
/*     */     
/* 216 */     return (Dialog)builder.create();
/*     */   }
/*     */   
/*     */   private void updateAdvancedToMatchPresets() {
/* 220 */     int id = this.mBasicOptions.getCheckedRadioButtonId();
/* 221 */     if (id == R.id.radio_first) {
/* 222 */       this.mMaxDpiSpinner.setSelection(7);
/* 223 */       this.mResampleDpiSpinner.setSelection(4);
/* 224 */       this.mCompressionQualitySpinner.setSelection(3);
/* 225 */     } else if (id == R.id.radio_second) {
/* 226 */       this.mMaxDpiSpinner.setSelection(5);
/* 227 */       this.mResampleDpiSpinner.setSelection(3);
/* 228 */       this.mCompressionQualitySpinner.setSelection(2);
/*     */     } else {
/* 230 */       this.mMaxDpiSpinner.setSelection(3);
/* 231 */       this.mResampleDpiSpinner.setSelection(2);
/* 232 */       this.mCompressionQualitySpinner.setSelection(1);
/*     */     } 
/*     */   }
/*     */   
/*     */   private OptimizeParams processOptimizeRequest() {
/* 237 */     OptimizeParams result = new OptimizeParams();
/* 238 */     result.forceChanges = false;
/* 239 */     if (this.mCurrentView == 0)
/* 240 */     { int id = this.mBasicOptions.getCheckedRadioButtonId();
/* 241 */       if (id == R.id.radio_first) {
/* 242 */         result.colorDownsampleMode = 0;
/* 243 */         result.colorCompressionMode = 0;
/* 244 */         result.colorQuality = 10L;
/*     */         
/* 246 */         result.monoDownsampleMode = 0;
/* 247 */         result.monoCompressionMode = 0;
/*     */         
/* 249 */         result.forceRecompression = false;
/* 250 */       } else if (id == R.id.radio_second) {
/* 251 */         result.colorDownsampleMode = 1;
/* 252 */         result.colorMaxDpi = 225.0D;
/* 253 */         result.colorResampleDpi = 150.0D;
/* 254 */         result.colorCompressionMode = 2;
/* 255 */         result.colorQuality = 8L;
/*     */         
/* 257 */         result.monoDownsampleMode = 1;
/* 258 */         result.monoMaxDpi = result.colorMaxDpi * 2.0D;
/* 259 */         result.monoResampleDpi = result.colorResampleDpi * 2.0D;
/* 260 */         result.monoCompressionMode = 0;
/*     */         
/* 262 */         result.forceRecompression = true;
/*     */       } else {
/* 264 */         result.colorDownsampleMode = 1;
/* 265 */         result.colorMaxDpi = 120.0D;
/* 266 */         result.colorResampleDpi = 96.0D;
/* 267 */         result.colorCompressionMode = 2;
/* 268 */         result.colorQuality = 6L;
/*     */         
/* 270 */         result.monoDownsampleMode = 1;
/* 271 */         result.monoMaxDpi = result.colorMaxDpi * 2.0D;
/* 272 */         result.monoResampleDpi = result.colorResampleDpi * 2.0D;
/* 273 */         result.monoCompressionMode = 0;
/*     */         
/* 275 */         result.forceRecompression = true;
/*     */       }  }
/*     */     else
/* 278 */     { result.forceRecompression = true;
/* 279 */       result.colorDownsampleMode = 1;
/* 280 */       result.monoDownsampleMode = 1;
/*     */ 
/*     */       
/* 283 */       switch (this.mMaxDpiSpinner.getSelectedItemPosition()) {
/*     */         case 0:
/* 285 */           result.colorMaxDpi = 50.0D;
/*     */           break;
/*     */         case 1:
/* 288 */           result.colorMaxDpi = 72.0D;
/*     */           break;
/*     */         case 2:
/* 291 */           result.colorMaxDpi = 96.0D;
/*     */           break;
/*     */         case 3:
/* 294 */           result.colorMaxDpi = 120.0D;
/*     */           break;
/*     */         case 4:
/* 297 */           result.colorMaxDpi = 150.0D;
/*     */           break;
/*     */         case 6:
/* 300 */           result.colorMaxDpi = 300.0D;
/*     */           break;
/*     */         case 7:
/* 303 */           result.colorMaxDpi = 600.0D;
/*     */           break;
/*     */         
/*     */         default:
/* 307 */           result.colorMaxDpi = 225.0D;
/*     */           break;
/*     */       } 
/*     */ 
/*     */       
/* 312 */       switch (this.mResampleDpiSpinner.getSelectedItemPosition()) {
/*     */         case 0:
/* 314 */           result.colorResampleDpi = 50.0D;
/*     */           break;
/*     */         case 1:
/* 317 */           result.colorResampleDpi = 72.0D;
/*     */           break;
/*     */         case 2:
/* 320 */           result.colorResampleDpi = 96.0D;
/*     */           break;
/*     */         case 4:
/* 323 */           result.colorResampleDpi = 225.0D;
/*     */           break;
/*     */         
/*     */         default:
/* 327 */           result.colorResampleDpi = 150.0D;
/*     */           break;
/*     */       } 
/*     */ 
/*     */       
/* 332 */       switch (this.mCompressionColorModeSpinner.getSelectedItemPosition()) {
/*     */         case 0:
/* 334 */           result.colorCompressionMode = 0;
/*     */           break;
/*     */         case 1:
/* 337 */           result.colorCompressionMode = 1;
/*     */           break;
/*     */         case 3:
/* 340 */           result.colorCompressionMode = 3;
/*     */           break;
/*     */         
/*     */         default:
/* 344 */           result.colorCompressionMode = 2;
/*     */           break;
/*     */       } 
/*     */ 
/*     */       
/* 349 */       switch (this.mCompressionQualitySpinner.getSelectedItemPosition()) {
/*     */         case 3:
/* 351 */           result.colorQuality = 10L;
/*     */           break;
/*     */         case 2:
/* 354 */           result.colorQuality = 8L;
/*     */           break;
/*     */         case 0:
/* 357 */           result.colorQuality = 4L;
/*     */           break;
/*     */         
/*     */         default:
/* 361 */           result.colorQuality = 6L;
/*     */           break;
/*     */       } 
/*     */       
/* 365 */       result.monoMaxDpi = result.colorMaxDpi * 2.0D;
/* 366 */       result.monoResampleDpi = result.colorResampleDpi * 2.0D;
/*     */ 
/*     */       
/* 369 */       switch (this.mCompressionMonoModeSpinner.getSelectedItemPosition())
/*     */       { case 0:
/* 371 */           result.monoCompressionMode = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 379 */           return result; }  result.monoCompressionMode = 0; }  return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void optimize(PDFDoc doc, OptimizeParams optimizeParams) {
/* 388 */     if (doc != null) {
/* 389 */       boolean shouldUnlock = false;
/*     */       try {
/* 391 */         Optimizer.ImageSettings ims = new Optimizer.ImageSettings();
/* 392 */         ims.setDownsampleMode(optimizeParams.colorDownsampleMode);
/* 393 */         ims.setImageDPI(optimizeParams.colorMaxDpi, optimizeParams.colorResampleDpi);
/* 394 */         ims.setCompressionMode(optimizeParams.colorCompressionMode);
/* 395 */         ims.setQuality(optimizeParams.colorQuality);
/* 396 */         ims.forceChanges(optimizeParams.forceChanges);
/* 397 */         ims.forceRecompression(optimizeParams.forceRecompression);
/*     */         
/* 399 */         Optimizer.MonoImageSettings mims = new Optimizer.MonoImageSettings();
/* 400 */         mims.setDownsampleMode(optimizeParams.monoDownsampleMode);
/* 401 */         mims.setImageDPI(optimizeParams.monoMaxDpi, optimizeParams.monoResampleDpi);
/* 402 */         mims.setCompressionMode(optimizeParams.monoCompressionMode);
/* 403 */         mims.forceChanges(optimizeParams.forceChanges);
/* 404 */         mims.forceRecompression(optimizeParams.forceRecompression);
/*     */         
/* 406 */         Optimizer.OptimizerSettings os = new Optimizer.OptimizerSettings();
/* 407 */         os.setColorImageSettings(ims);
/* 408 */         os.setGrayscaleImageSettings(ims);
/* 409 */         os.setMonoImageSettings(mims);
/*     */         
/* 411 */         doc.lock();
/* 412 */         shouldUnlock = true;
/* 413 */         Optimizer.optimize(doc, os);
/* 414 */       } catch (Exception e) {
/* 415 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */       } finally {
/* 417 */         if (shouldUnlock)
/* 418 */           Utils.unlockQuietly(doc); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static interface OptimizeDialogFragmentListener {
/*     */     void onOptimizeClicked(OptimizeParams param1OptimizeParams);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\dialog\OptimizeDialogFragment.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */