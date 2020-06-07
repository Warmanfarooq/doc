/*     */ package com.pdftron.pdf.controls;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.content.res.TypedArray;
/*     */ import android.util.AttributeSet;
/*     */ import android.widget.Adapter;
/*     */ import android.widget.ListAdapter;
/*     */ import androidx.annotation.ColorInt;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.utils.ColorPickerGridViewAdapter;
/*     */ import com.pdftron.pdf.utils.ExpandableGridView;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PresetColorGridView
/*     */   extends ExpandableGridView
/*     */ {
/*  26 */   private static final String TAG = PresetColorGridView.class.getName();
/*     */   private ColorPickerGridViewAdapter mAdapter;
/*  28 */   private int mTransparentColorPosition = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PresetColorGridView(Context context) {
/*  34 */     super(context);
/*  35 */     init((AttributeSet)null, R.attr.preset_colors_style);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PresetColorGridView(Context context, AttributeSet attrs) {
/*  42 */     super(context, attrs);
/*  43 */     init(attrs, R.attr.preset_colors_style);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PresetColorGridView(Context context, AttributeSet attrs, int defStyle) {
/*  50 */     super(context, attrs, defStyle);
/*  51 */     init(attrs, defStyle);
/*     */   }
/*     */   
/*     */   private void init(AttributeSet attrs, int defStyle) {
/*  55 */     TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.PresetColorGridView, defStyle, R.style.PresetColorGridViewStyle);
/*     */     
/*     */     try {
/*  58 */       int presetColorRef = a.getResourceId(R.styleable.PresetColorGridView_color_list, -1);
/*  59 */       if (presetColorRef != -1) {
/*  60 */         String[] colors = getContext().getResources().getStringArray(presetColorRef);
/*  61 */         if (colors.length > 0) {
/*  62 */           ArrayList<String> colorlist = new ArrayList<>(Arrays.asList(colors));
/*  63 */           this.mAdapter = new ColorPickerGridViewAdapter(getContext(), colorlist);
/*     */ 
/*     */           
/*  66 */           setAdapter((ListAdapter)this.mAdapter);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/*  71 */       int hSpacing = a.getDimensionPixelOffset(R.styleable.PresetColorGridView_android_horizontalSpacing, 0);
/*  72 */       setHorizontalSpacing(hSpacing);
/*  73 */       int vSpacing = a.getDimensionPixelOffset(R.styleable.PresetColorGridView_android_verticalSpacing, 0);
/*  74 */       setVerticalSpacing(vSpacing);
/*     */ 
/*     */       
/*  77 */       int columnWidth = a.getDimensionPixelOffset(R.styleable.PresetColorGridView_android_columnWidth, -1);
/*  78 */       if (columnWidth > 0) {
/*  79 */         setColumnWidth(columnWidth);
/*     */       }
/*     */ 
/*     */       
/*  83 */       int index = a.getInt(R.styleable.PresetColorGridView_android_stretchMode, 2);
/*  84 */       if (index >= 0) {
/*  85 */         setStretchMode(index);
/*     */       }
/*     */ 
/*     */       
/*  89 */       int numColumns = a.getInt(R.styleable.PresetColorGridView_android_numColumns, 1);
/*  90 */       setNumColumns(numColumns);
/*     */ 
/*     */       
/*  93 */       index = a.getInt(R.styleable.PresetColorGridView_android_gravity, -1);
/*  94 */       if (index >= 0) {
/*  95 */         setGravity(index);
/*     */       }
/*     */     } finally {
/*     */       
/*  99 */       a.recycle();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSelectedColor(@ColorInt int color) {
/* 108 */     if (color == 0) {
/* 109 */       this.mAdapter.setSelected("no_fill_color");
/*     */     } else {
/* 111 */       this.mAdapter.setSelected(Utils.getColorHexString(color));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ColorPickerGridViewAdapter getAdapter() {
/* 121 */     return this.mAdapter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSelectedColor(String colorSrc) {
/* 129 */     this.mAdapter.setSelected(colorSrc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void showTransparentColor(boolean showTransparent) {
/* 137 */     if (showTransparent && this.mTransparentColorPosition > 0) {
/* 138 */       this.mAdapter.addItem(this.mTransparentColorPosition, "no_fill_color");
/* 139 */       this.mTransparentColorPosition = -1;
/* 140 */     } else if (!showTransparent) {
/* 141 */       this.mTransparentColorPosition = this.mAdapter.removeItem("no_fill_color");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\controls\PresetColorGridView.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */