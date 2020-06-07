/*     */ package com.pdftron.pdf.controls;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.graphics.Color;
/*     */ import android.graphics.PorterDuff;
/*     */ import android.graphics.drawable.Drawable;
/*     */ import android.util.AttributeSet;
/*     */ import android.view.KeyEvent;
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.widget.EditText;
/*     */ import android.widget.ImageView;
/*     */ import android.widget.TextView;
/*     */ import androidx.annotation.ColorInt;
/*     */ import androidx.constraintlayout.widget.ConstraintLayout;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.CommonToast;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import com.pdftron.pdf.widget.ColorHuePickerWheel;
/*     */ import com.pdftron.pdf.widget.ColorSatValuePickerBoard;
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
/*     */ public class AdvancedColorView
/*     */   extends ConstraintLayout
/*     */   implements ColorHuePickerWheel.OnHueChangeListener, ColorSatValuePickerBoard.OnHSVChangeListener
/*     */ {
/*     */   private ColorHuePickerWheel mColorHuePicker;
/*     */   private ColorSatValuePickerBoard mColorSaturationPicker;
/*     */   private ImageView mPrevColorImage;
/*     */   private ImageView mCurrColorImage;
/*     */   private EditText mColorEditText;
/*     */   @ColorInt
/*     */   private int mColor;
/*     */   private ColorPickerView.OnColorChangeListener mColorChangeListener;
/*     */   
/*     */   public AdvancedColorView(Context context) {
/*  54 */     super(context);
/*  55 */     init();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AdvancedColorView(Context context, AttributeSet attrs) {
/*  61 */     super(context, attrs);
/*  62 */     init();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AdvancedColorView(Context context, AttributeSet attrs, int defStyleAttr) {
/*  68 */     super(context, attrs, defStyleAttr);
/*  69 */     init();
/*     */   }
/*     */   
/*     */   private void init() {
/*  73 */     LayoutInflater.from(getContext()).inflate(R.layout.color_picker_layout_advanced, (ViewGroup)this);
/*  74 */     this.mColorHuePicker = (ColorHuePickerWheel)findViewById(R.id.color_hue_picker);
/*  75 */     this.mColorEditText = (EditText)findViewById(R.id.color_edit_text);
/*  76 */     this.mColorEditText.setOnEditorActionListener(new TextView.OnEditorActionListener()
/*     */         {
/*     */           public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
/*  79 */             return AdvancedColorView.this.onColorEditTextActionChanged(textView, i, keyEvent);
/*     */           }
/*     */         });
/*  82 */     this.mColorEditText.setOnFocusChangeListener(new OnFocusChangeListener()
/*     */         {
/*     */           public void onFocusChange(View view, boolean b) {
/*  85 */             AdvancedColorView.this.onColorEditTextFocusChanged(view, b);
/*     */           }
/*     */         });
/*     */     
/*  89 */     this.mColorSaturationPicker = (ColorSatValuePickerBoard)findViewById(R.id.color_saturation_picker);
/*     */     
/*  91 */     this.mPrevColorImage = (ImageView)findViewById(R.id.prev_color);
/*  92 */     this.mCurrColorImage = (ImageView)findViewById(R.id.curr_color);
/*     */     
/*  94 */     this.mColorHuePicker.setOnHueChangeListener(this);
/*  95 */     this.mColorSaturationPicker.setOnSaturationValueChangelistener(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSelectedColor(@ColorInt int color) {
/* 104 */     Drawable preColor = this.mPrevColorImage.getBackground();
/* 105 */     preColor.mutate();
/* 106 */     preColor.setColorFilter(color, PorterDuff.Mode.SRC_IN);
/* 107 */     setColor(color);
/*     */   }
/*     */   
/*     */   private void setColor(@ColorInt int color) {
/* 111 */     this.mColor = color;
/* 112 */     this.mColorHuePicker.setColor(color);
/* 113 */     this.mColorEditText.setText(Utils.getColorHexString(color));
/* 114 */     this.mColorSaturationPicker.setColor(color);
/* 115 */     updateCurrColorPreview(color);
/*     */   }
/*     */   
/*     */   private void updateCurrColorPreview(@ColorInt int color) {
/* 119 */     Drawable currColor = this.mCurrColorImage.getBackground();
/* 120 */     currColor.mutate();
/* 121 */     currColor.setColorFilter(color, PorterDuff.Mode.SRC_IN);
/*     */   }
/*     */   
/*     */   private boolean onColorEditTextActionChanged(TextView v, int actionId, KeyEvent event) {
/* 125 */     if (actionId == 6) {
/* 126 */       Utils.hideSoftKeyboard(getContext(), (View)this.mColorEditText);
/* 127 */       this.mColorEditText.clearFocus();
/* 128 */       return true;
/*     */     } 
/* 130 */     return false;
/*     */   }
/*     */   
/*     */   private void onColorEditTextFocusChanged(View v, boolean hasFocus) {
/* 134 */     if (this.mColorEditText == null || Utils.isNullOrEmpty(this.mColorEditText.getText().toString())) {
/*     */       return;
/*     */     }
/* 137 */     if (!hasFocus) {
/*     */       try {
/* 139 */         int color = Color.parseColor(this.mColorEditText.getText().toString());
/* 140 */         setColor(color);
/* 141 */         invokeColorChangeListener();
/* 142 */       } catch (IllegalArgumentException e) {
/* 143 */         this.mColorEditText.setText(Utils.getColorHexString(this.mColor));
/* 144 */         CommonToast.showText(getContext(), R.string.error_illegal_color, 0);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private void invokeColorChangeListener() {
/* 150 */     if (this.mColorChangeListener != null) {
/* 151 */       AnalyticsHandlerAdapter.getInstance().sendEvent(12, 
/* 152 */           String.format("color selected %s", new Object[] { Utils.getColorHexString(this.mColor) }), 119);
/*     */       
/* 154 */       this.mColorChangeListener.OnColorChanged((View)this, this.mColor);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColor() {
/* 163 */     return this.mColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onHSVChanged(float[] hsv) {
/* 173 */     this.mColor = Color.HSVToColor(hsv);
/* 174 */     this.mColorEditText.setText(Utils.getColorHexString(this.mColor));
/* 175 */     updateCurrColorPreview(this.mColor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onHueChanged(float newHue) {
/* 185 */     float[] hsv = new float[3];
/* 186 */     Color.colorToHSV(this.mColor, hsv);
/* 187 */     hsv[0] = newHue;
/* 188 */     this.mColor = Color.HSVToColor(hsv);
/* 189 */     this.mColorSaturationPicker.setHue(newHue);
/* 190 */     this.mColorEditText.setText(Utils.getColorHexString(this.mColor));
/* 191 */     updateCurrColorPreview(this.mColor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOnColorChangeListener(ColorPickerView.OnColorChangeListener listener) {
/* 200 */     this.mColorChangeListener = listener;
/*     */   }
/*     */ 
/*     */   
/*     */   public void OnColorChanged(View view, int color) {
/* 205 */     invokeColorChangeListener();
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\controls\AdvancedColorView.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */