/*     */ package com.pdftron.pdf.widget;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.graphics.PorterDuff;
/*     */ import android.net.Uri;
/*     */ import android.util.AttributeSet;
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.widget.ArrayAdapter;
/*     */ import android.widget.Button;
/*     */ import android.widget.CompoundButton;
/*     */ import android.widget.ImageView;
/*     */ import android.widget.LinearLayout;
/*     */ import android.widget.Spinner;
/*     */ import android.widget.SpinnerAdapter;
/*     */ import android.widget.TextView;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.appcompat.widget.SwitchCompat;
/*     */ import androidx.transition.TransitionManager;
/*     */ import com.pdftron.pdf.controls.AdvancedColorView;
/*     */ import com.pdftron.pdf.controls.ColorPickerView;
/*     */ import com.pdftron.pdf.model.FileInfo;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import java.util.ArrayList;
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
/*     */ public class DiffOptionsView
/*     */   extends LinearLayout
/*     */   implements CompoundButton.OnCheckedChangeListener, View.OnClickListener, ColorPickerView.OnColorChangeListener
/*     */ {
/*     */   private DiffOptionsViewListener mDiffOptionsViewListener;
/*     */   private Uri mFile1;
/*     */   private Uri mFile2;
/*     */   private TextView mFilename1;
/*     */   private Button mSelectButton1;
/*     */   private SwitchCompat mAnnotSwitch1;
/*     */   private LinearLayout mColorLayout1;
/*     */   private ImageView mColor1;
/*     */   private ImageView mColorChevron1;
/*     */   private AdvancedColorView mColorPicker1;
/*     */   private TextView mFilename2;
/*     */   private Button mSelectButton2;
/*     */   private SwitchCompat mAnnotSwitch2;
/*     */   private LinearLayout mColorLayout2;
/*     */   private ImageView mColor2;
/*     */   private ImageView mColorChevron2;
/*     */   private AdvancedColorView mColorPicker2;
/*     */   private Spinner mBlendSpinner;
/*     */   private Button mCompareButton;
/*     */   
/*     */   public DiffOptionsView(Context context) {
/*  72 */     this(context, (AttributeSet)null);
/*     */   }
/*     */   
/*     */   public DiffOptionsView(Context context, @Nullable AttributeSet attrs) {
/*  76 */     this(context, attrs, 0);
/*     */   }
/*     */   
/*     */   public DiffOptionsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
/*  80 */     super(context, attrs, defStyleAttr);
/*  81 */     init();
/*     */   }
/*     */   
/*     */   private void init() {
/*  85 */     LayoutInflater.from(getContext()).inflate(R.layout.view_diff_options, (ViewGroup)this);
/*  86 */     setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
/*  87 */     setOrientation(1);
/*     */     
/*  89 */     this.mFilename1 = (TextView)findViewById(R.id.diff_file_1);
/*  90 */     this.mSelectButton1 = (Button)findViewById(R.id.diff_select_file_1);
/*  91 */     this.mSelectButton1.setOnClickListener(this);
/*  92 */     this.mAnnotSwitch1 = (SwitchCompat)findViewById(R.id.diff_annotation_switch_1);
/*  93 */     this.mAnnotSwitch1.setOnCheckedChangeListener(this);
/*  94 */     this.mColorLayout1 = (LinearLayout)findViewById(R.id.diff_color_layout_1);
/*  95 */     this.mColorLayout1.setOnClickListener(this);
/*  96 */     this.mColor1 = (ImageView)findViewById(R.id.diff_color_1);
/*  97 */     int color1 = getContext().getResources().getColor(R.color.diff_color_1);
/*  98 */     this.mColor1.getDrawable()
/*  99 */       .mutate()
/* 100 */       .setColorFilter(color1, PorterDuff.Mode.SRC_IN);
/* 101 */     this.mColorChevron1 = (ImageView)findViewById(R.id.diff_color_chevron_1);
/* 102 */     this.mColorPicker1 = (AdvancedColorView)findViewById(R.id.diff_color_picker_1);
/* 103 */     this.mColorPicker1.setSelectedColor(color1);
/* 104 */     this.mColorPicker1.setOnColorChangeListener(this);
/*     */     
/* 106 */     this.mFilename2 = (TextView)findViewById(R.id.diff_file_2);
/* 107 */     this.mSelectButton2 = (Button)findViewById(R.id.diff_select_file_2);
/* 108 */     this.mSelectButton2.setOnClickListener(this);
/* 109 */     this.mAnnotSwitch2 = (SwitchCompat)findViewById(R.id.diff_annotation_switch_2);
/* 110 */     this.mAnnotSwitch2.setOnCheckedChangeListener(this);
/* 111 */     this.mColorLayout2 = (LinearLayout)findViewById(R.id.diff_color_layout_2);
/* 112 */     this.mColorLayout2.setOnClickListener(this);
/* 113 */     this.mColor2 = (ImageView)findViewById(R.id.diff_color_2);
/* 114 */     int color2 = getContext().getResources().getColor(R.color.diff_color_2);
/* 115 */     this.mColor2.getDrawable()
/* 116 */       .mutate()
/* 117 */       .setColorFilter(color2, PorterDuff.Mode.SRC_IN);
/* 118 */     this.mColorChevron2 = (ImageView)findViewById(R.id.diff_color_chevron_2);
/* 119 */     this.mColorPicker2 = (AdvancedColorView)findViewById(R.id.diff_color_picker_2);
/* 120 */     this.mColorPicker2.setSelectedColor(color2);
/* 121 */     this.mColorPicker2.setOnColorChangeListener(this);
/*     */     
/* 123 */     this.mBlendSpinner = (Spinner)findViewById(R.id.diff_blend_spinner);
/* 124 */     ArrayAdapter<CharSequence> blendAdapter = ArrayAdapter.createFromResource(getContext(), R.array.diff_blend_array, 17367048);
/*     */     
/* 126 */     blendAdapter.setDropDownViewResource(17367049);
/* 127 */     this.mBlendSpinner.setAdapter((SpinnerAdapter)blendAdapter);
/* 128 */     this.mBlendSpinner.setSelection(5);
/*     */     
/* 130 */     this.mCompareButton = (Button)findViewById(R.id.diff_compare);
/* 131 */     this.mCompareButton.setOnClickListener(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
/* 136 */     buttonView.setText(isChecked ? R.string.diff_annotations_on : R.string.diff_annotations_off);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onClick(View v) {
/* 141 */     if (v.getId() == this.mSelectButton1.getId() || v.getId() == this.mSelectButton2.getId()) {
/* 142 */       if (this.mDiffOptionsViewListener != null) {
/* 143 */         this.mDiffOptionsViewListener.onSelectFile(v);
/*     */       }
/* 145 */     } else if (v.getId() == this.mCompareButton.getId()) {
/* 146 */       if (this.mFile1 != null && this.mFile2 != null) {
/* 147 */         if (this.mDiffOptionsViewListener != null) {
/* 148 */           ArrayList<Uri> files = new ArrayList<>(2);
/* 149 */           files.add(this.mFile1);
/* 150 */           files.add(this.mFile2);
/* 151 */           this.mDiffOptionsViewListener.onCompareFiles(files);
/*     */         } 
/*     */       } else {
/* 154 */         Utils.safeShowAlertDialog(v.getContext(), R.string.diff_select_file_title, R.string.diff_compare);
/*     */       } 
/* 156 */     } else if (v.getId() == this.mColorLayout1.getId()) {
/* 157 */       TransitionManager.beginDelayedTransition((ViewGroup)this);
/* 158 */       if (this.mColorPicker1.getVisibility() == 8) {
/* 159 */         this.mColorPicker1.setVisibility(0);
/* 160 */         this.mColorChevron1.setImageResource(R.drawable.ic_arrow_down_white_24dp);
/*     */       } else {
/* 162 */         this.mColorPicker1.setVisibility(8);
/* 163 */         this.mColorChevron1.setImageResource(R.drawable.ic_chevron_right_black_24dp);
/*     */       } 
/* 165 */     } else if (v.getId() == this.mColorLayout2.getId()) {
/* 166 */       TransitionManager.beginDelayedTransition((ViewGroup)this);
/* 167 */       if (this.mColorPicker2.getVisibility() == 8) {
/* 168 */         this.mColorPicker2.setVisibility(0);
/* 169 */         this.mColorChevron2.setImageResource(R.drawable.ic_arrow_down_white_24dp);
/*     */       } else {
/* 171 */         this.mColorPicker2.setVisibility(8);
/* 172 */         this.mColorChevron2.setImageResource(R.drawable.ic_chevron_right_black_24dp);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void OnColorChanged(View view, int color) {
/* 179 */     if (view.getId() == this.mColorPicker1.getId()) {
/* 180 */       this.mColor1.getDrawable()
/* 181 */         .mutate()
/* 182 */         .setColorFilter(color, PorterDuff.Mode.SRC_IN);
/* 183 */     } else if (view.getId() == this.mColorPicker2.getId()) {
/* 184 */       this.mColor2.getDrawable()
/* 185 */         .mutate()
/* 186 */         .setColorFilter(color, PorterDuff.Mode.SRC_IN);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setDiffOptionsViewListener(@NonNull DiffOptionsViewListener listener) {
/* 191 */     this.mDiffOptionsViewListener = listener;
/*     */   }
/*     */   
/*     */   public void setFiles(FileInfo fileInfo1, FileInfo fileInfo2) {
/* 195 */     if (fileInfo1 != null) {
/* 196 */       this.mFilename1.setText(fileInfo1.getName());
/* 197 */       this.mFile1 = Uri.parse(fileInfo1.getAbsolutePath());
/*     */     } 
/* 199 */     if (fileInfo2 != null) {
/* 200 */       this.mFilename2.setText(fileInfo2.getName());
/* 201 */       this.mFile2 = Uri.parse(fileInfo2.getAbsolutePath());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setSelectFileButtonVisibility(boolean visible) {
/* 206 */     this.mSelectButton1.setVisibility(visible ? 0 : 8);
/* 207 */     this.mSelectButton2.setVisibility(visible ? 0 : 8);
/*     */   }
/*     */   
/*     */   public void setAnnotationToggleVisibility(boolean visible) {
/* 211 */     this.mAnnotSwitch1.setVisibility(visible ? 0 : 8);
/* 212 */     this.mAnnotSwitch2.setVisibility(visible ? 0 : 8);
/*     */   }
/*     */   
/*     */   public void setColorOptionVisibility(boolean visible) {
/* 216 */     this.mColorLayout1.setVisibility(visible ? 0 : 8);
/* 217 */     this.mColorLayout2.setVisibility(visible ? 0 : 8);
/*     */   }
/*     */   
/*     */   public void setCompareButtonVisibility(boolean visible) {
/* 221 */     this.mCompareButton.setVisibility(visible ? 0 : 8);
/*     */   }
/*     */   
/*     */   public void handleFileSelected(FileInfo fileInfo, View which) {
/* 225 */     if (fileInfo == null || which == null) {
/*     */       return;
/*     */     }
/* 228 */     if (which.getId() == this.mSelectButton1.getId()) {
/* 229 */       this.mFilename1.setText(fileInfo.getName());
/* 230 */       this.mFile1 = Uri.parse(fileInfo.getAbsolutePath());
/* 231 */     } else if (which.getId() == this.mSelectButton2.getId()) {
/* 232 */       this.mFilename2.setText(fileInfo.getName());
/* 233 */       this.mFile2 = Uri.parse(fileInfo.getAbsolutePath());
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getColor1() {
/* 238 */     return this.mColorPicker1.getColor();
/*     */   }
/*     */   
/*     */   public int getColor2() {
/* 242 */     return this.mColorPicker2.getColor();
/*     */   }
/*     */   
/*     */   public int getBlendMode() {
/* 246 */     return this.mBlendSpinner.getSelectedItemPosition();
/*     */   }
/*     */   
/*     */   public static interface DiffOptionsViewListener {
/*     */     void onSelectFile(View param1View);
/*     */     
/*     */     void onCompareFiles(ArrayList<Uri> param1ArrayList);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\widget\DiffOptionsView.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */