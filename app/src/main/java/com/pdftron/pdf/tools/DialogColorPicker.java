/*     */ package com.pdftron.pdf.tools;
/*     */ 
/*     */ import android.app.Activity;
/*     */ import android.app.AlertDialog;
/*     */ import android.content.Context;
/*     */ import android.util.DisplayMetrics;
/*     */ import android.util.TypedValue;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.widget.LinearLayout;
/*     */ import android.widget.RelativeLayout;
/*     */ import android.widget.TextView;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
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
/*     */ 
/*     */ class DialogColorPicker
/*     */   extends AlertDialog
/*     */   implements ColorPickerView.OnColorChangedListener
/*     */ {
/*     */   private ColorPickerView mColorPicker;
/*     */   private ColorPanelView mOldColor;
/*     */   private ColorPanelView mNewColor;
/*     */   private ColorPickerView.OnColorChangedListener mListener;
/*     */   private Context mContext;
/*     */   private TextView mText;
/*     */   
/*     */   public DialogColorPicker(Context context, int initialColor) {
/*  69 */     super(context);
/*  70 */     this.mContext = context;
/*     */     
/*  72 */     init(initialColor);
/*     */   }
/*     */ 
/*     */   
/*     */   private void init(int color) {
/*  77 */     getWindow().setFormat(1);
/*     */     
/*  79 */     setUp(color);
/*     */   }
/*     */   
/*     */   private void setUp(int color) {
/*     */     try {
/*  84 */       setTitle(this.mContext.getResources().getString(R.string.tools_dialog_colorpicker_title));
/*     */       
/*  86 */       int margin = 10;
/*  87 */       DisplayMetrics metrics = new DisplayMetrics();
/*  88 */       ((Activity)this.mContext).getWindowManager().getDefaultDisplay().getMetrics(metrics);
/*  89 */       margin = Math.round(TypedValue.applyDimension(2, margin, metrics));
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
/* 149 */       RelativeLayout rlayout = new RelativeLayout(this.mContext);
/* 150 */       rlayout.setLayoutParams((ViewGroup.LayoutParams)new RelativeLayout.LayoutParams(-1, -1));
/* 151 */       setView((View)rlayout);
/*     */       
/* 153 */       this.mColorPicker = new ColorPickerView(this.mContext);
/*     */       
/* 155 */       this.mColorPicker.setId(1);
/* 156 */       RelativeLayout.LayoutParams rp = new RelativeLayout.LayoutParams(-1, -2);
/* 157 */       rp.addRule(14, -1);
/* 158 */       rp.setMargins(margin, 0, margin, 0);
/* 159 */       this.mColorPicker.setLayoutParams((ViewGroup.LayoutParams)rp);
/*     */       
/* 161 */       LinearLayout llayout = new LinearLayout(this.mContext);
/* 162 */       llayout.setOrientation(0);
/* 163 */       rp = new RelativeLayout.LayoutParams(-1, margin * 4);
/* 164 */       rp.addRule(3, this.mColorPicker.getId());
/* 165 */       rp.addRule(5, this.mColorPicker.getId());
/* 166 */       rp.addRule(7, this.mColorPicker.getId());
/* 167 */       rp.setMargins(0, margin, 0, 0);
/* 168 */       llayout.setLayoutParams((ViewGroup.LayoutParams)rp);
/*     */       
/* 170 */       LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, -1, 0.5F);
/* 171 */       this.mOldColor = new ColorPanelView(this.mContext);
/* 172 */       this.mNewColor = new ColorPanelView(this.mContext);
/* 173 */       this.mOldColor.setLayoutParams((ViewGroup.LayoutParams)lp);
/* 174 */       this.mNewColor.setLayoutParams((ViewGroup.LayoutParams)lp);
/*     */       
/* 176 */       this.mText = new TextView(this.mContext);
/* 177 */       String str = " → ";
/* 178 */       this.mText.setText(str);
/* 179 */       this.mText.setTextSize(2, 20.0F);
/* 180 */       this.mText.setTextColor(-1);
/* 181 */       RelativeLayout.LayoutParams rp4 = new RelativeLayout.LayoutParams(-2, -1);
/* 182 */       rp4.setMargins(margin, 0, margin, 0);
/* 183 */       this.mText.setLayoutParams((ViewGroup.LayoutParams)rp4);
/*     */       
/* 185 */       llayout.addView(this.mOldColor);
/* 186 */       llayout.addView((View)this.mText);
/* 187 */       llayout.addView(this.mNewColor);
/* 188 */       rlayout.addView(this.mColorPicker);
/* 189 */       rlayout.addView((View)llayout);
/*     */       
/* 191 */       ((LinearLayout)this.mOldColor.getParent()).setPadding(Math.round(this.mColorPicker.getDrawingOffset()), 0, 
/* 192 */           Math.round(this.mColorPicker.getDrawingOffset()), 0);
/*     */       
/* 194 */       this.mColorPicker.setOnColorChangedListener(this);
/*     */       
/* 196 */       this.mOldColor.setColor(color);
/* 197 */       this.mColorPicker.setColor(color, true);
/*     */     }
/* 199 */     catch (Exception e) {
/* 200 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onColorChanged(int color) {
/* 206 */     this.mNewColor.setColor(color);
/*     */     
/* 208 */     if (this.mListener != null) {
/* 209 */       this.mListener.onColorChanged(color);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setAlphaSliderVisible(boolean visible) {
/* 214 */     this.mColorPicker.setAlphaSliderVisible(visible);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColor() {
/* 221 */     return this.mColorPicker.getColor();
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\DialogColorPicker.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */