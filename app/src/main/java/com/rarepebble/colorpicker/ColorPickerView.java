/*    */ package com.rarepebble.colorpicker;
/*    */ 
/*    */ import android.content.Context;
/*    */ import android.content.res.TypedArray;
/*    */ import android.util.AttributeSet;
/*    */ import android.view.LayoutInflater;
/*    */ import android.view.ViewGroup;
/*    */ import android.widget.FrameLayout;
/*    */ import com.pdftron.pdf.tools.R;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ColorPickerView
/*    */   extends FrameLayout
/*    */   implements ObservableColor.Observer
/*    */ {
/* 29 */   private final ObservableColor observableColor = new ObservableColor(0);
/* 30 */   private ColorPickerViewListener mListener = null;
/*    */ 
/*    */   
/*    */   public void updateColor(ObservableColor observableColor) {
/* 34 */     if (this.mListener != null) {
/* 35 */       this.mListener.onColorChanged(observableColor.getColor());
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ColorPickerView(Context context) {
/* 43 */     this(context, (AttributeSet)null);
/*    */   }
/*    */   
/*    */   public ColorPickerView(Context context, AttributeSet attrs) {
/* 47 */     super(context, attrs);
/* 48 */     LayoutInflater.from(context).inflate(R.layout.view_colorpicker, (ViewGroup)this);
/*    */     
/* 50 */     HueSatView hueSatView = (HueSatView)findViewById(R.id.hueSatView);
/* 51 */     hueSatView.observeColor(this.observableColor);
/*    */     
/* 53 */     ValueView valueView = (ValueView)findViewById(R.id.valueView);
/* 54 */     valueView.observeColor(this.observableColor);
/*    */     
/* 56 */     this.observableColor.addObserver(this);
/*    */     
/* 58 */     applyAttributes(attrs);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   void applyAttributes(AttributeSet attrs) {
/* 65 */     if (attrs != null) {
/* 66 */       TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.ColorPicker, 0, 0);
/* 67 */       showAlpha(a.getBoolean(R.styleable.ColorPicker_colorpicker_showAlpha, true));
/* 68 */       showHex(a.getBoolean(R.styleable.ColorPicker_colorpicker_showHex, true));
/*    */     } 
/*    */   }
/*    */   
/*    */   public void setListener(ColorPickerViewListener listener) {
/* 73 */     this.mListener = listener;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getColor() {
/* 78 */     return this.observableColor.getColor();
/*    */   }
/*    */ 
/*    */   
/*    */   public void setColor(int color) {
/* 83 */     setOriginalColor(color);
/* 84 */     setCurrentColor(color);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setOriginalColor(int color) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void setCurrentColor(int color) {
/* 94 */     this.observableColor.updateColor(color, null);
/*    */   }
/*    */   
/*    */   public void showAlpha(boolean showAlpha) {}
/*    */   
/*    */   public void showHex(boolean showHex) {}
/*    */   
/*    */   public static interface ColorPickerViewListener {
/*    */     void onColorChanged(int param1Int);
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\rarepebble\colorpicker\ColorPickerView.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */