/*     */ package com.pdftron.pdf.widget;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.graphics.Rect;
/*     */ import android.util.AttributeSet;
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.view.WindowInsets;
/*     */ import androidx.fragment.app.FragmentActivity;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.model.AnnotStyle;
/*     */ import com.pdftron.pdf.tools.CustomRelativeLayout;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import com.pdftron.pdf.widget.richtext.PTRichEditor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AutoScrollEditor
/*     */   extends CustomRelativeLayout
/*     */ {
/*     */   private AutoScrollEditText mEditText;
/*     */   private PTRichEditor mRichEditor;
/*  28 */   private int mInputMode = 32;
/*  29 */   private int mParentMarginBottom = 0;
/*     */   
/*     */   private int mPadding;
/*     */   
/*     */   private Rect mViRect;
/*     */   
/*     */   private Rect mTempRect;
/*     */ 
/*     */   
/*     */   public AutoScrollEditText getEditText() {
/*  39 */     return this.mEditText;
/*     */   }
/*     */   
/*     */   public PTRichEditor getRichEditor() {
/*  43 */     return this.mRichEditor;
/*     */   }
/*     */   
/*     */   public View getActiveEditor() {
/*  47 */     if (this.mRichEditor.getVisibility() == 0) {
/*  48 */       return (View)this.mRichEditor;
/*     */     }
/*  50 */     return (View)this.mEditText;
/*     */   }
/*     */   
/*     */   public String getActiveText() {
/*  54 */     if (this.mRichEditor.getVisibility() == 0) {
/*  55 */       return (this.mRichEditor.getHtml() != null) ? this.mRichEditor.getHtml() : "";
/*     */     }
/*  57 */     return this.mEditText.getText().toString();
/*     */   }
/*     */   
/*     */   public AutoScrollEditor(Context context) {
/*  61 */     this(context, (AttributeSet)null);
/*     */   }
/*     */   
/*     */   public AutoScrollEditor(Context context, AttributeSet attrs) {
/*  65 */     this(context, attrs, 0);
/*     */   }
/*     */   private void init(Context context) { View view = LayoutInflater.from(context).inflate(R.layout.widget_auto_scroll_editor, (ViewGroup)this); this.mEditText = (AutoScrollEditText)view.findViewById(R.id.editText); this.mRichEditor = (PTRichEditor)view.findViewById(R.id.richEditor); this.mRichEditor.setPadding(0, 0, 0, 0); this.mRichEditor.setEditorBackgroundColor(0);
/*     */     this.mZoomWithParent = true;
/*  69 */     this.mPadding = (int)Utils.convDp2Pix(getContext(), 8.0F); } public boolean isRichContentEnabled() { return (this.mRichEditor.getVisibility() == 0); } public AutoScrollEditor(Context context, AttributeSet attrs, int defStyleAttr) { super(context, attrs, defStyleAttr);
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
/* 154 */     this.mViRect = new Rect();
/* 155 */     this.mTempRect = new Rect();
/*     */     init(context); }
/*     */   public void setRichContentEnabled(boolean enabled) { this.mEditText.setVisibility(enabled ? 8 : 0);
/*     */     this.mRichEditor.setVisibility(enabled ? 0 : 8); } protected void onLayout(boolean changed, int l, int t, int r, int b) {
/* 159 */     super.onLayout(changed, l, t, r, b);
/*     */     
/* 161 */     if (isAdjustResize() && this.mParentView != null)
/* 162 */     { this.mParentView.getDrawingRect(this.mViRect);
/* 163 */       this.mTempRect.set(l, t - this.mPadding, r, b + this.mPadding);
/* 164 */       if (!this.mViRect.intersect(this.mTempRect))
/*     */       {
/* 166 */         this.mParentView.scrollBy(0, b + this.mPadding - this.mParentView.getBottom()); }  }  } public void setAnnotStyle(PDFViewCtrl pdfViewCtrl, AnnotStyle annotStyle) { this.mEditText.setAnnotStyle(pdfViewCtrl, annotStyle); }
/*     */   public WindowInsets onApplyWindowInsets(WindowInsets insets) { if (isAdjustResize() && Utils.isLollipop()) { int insetBottom = insets.getSystemWindowInsetBottom(); int keyboardHeight = insets.getSystemWindowInsetBottom() - insets.getStableInsetBottom(); if (this.mParentView.getLayoutParams() instanceof MarginLayoutParams) { MarginLayoutParams lp = (MarginLayoutParams)this.mParentView.getLayoutParams(); if (keyboardHeight != 0) { lp.bottomMargin = insetBottom; }
/*     */         else { lp.bottomMargin = this.mParentMarginBottom; }
/*     */          this.mParentView.setLayoutParams((ViewGroup.LayoutParams)lp); }
/*     */        }
/*     */      return super.onApplyWindowInsets(insets); }
/* 172 */   protected boolean isAdjustResize() { return (this.mInputMode == 16); }
/*     */ 
/*     */   
/*     */   protected void onAttachedToWindow() {
/*     */     super.onAttachedToWindow();
/*     */     Context context = getContext();
/*     */     if (context instanceof FragmentActivity)
/*     */       this.mInputMode = (((FragmentActivity)context).getWindow().getAttributes()).softInputMode; 
/*     */     if (isAdjustResize()) {
/*     */       this.mParentView.setPageViewMode(PDFViewCtrl.PageViewMode.ZOOM);
/*     */       if (this.mParentView.getLayoutParams() instanceof MarginLayoutParams) {
/*     */         MarginLayoutParams lp = (MarginLayoutParams)this.mParentView.getLayoutParams();
/*     */         this.mParentMarginBottom = lp.bottomMargin;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void onDetachedFromWindow() {
/*     */     super.onDetachedFromWindow();
/*     */     if (isAdjustResize() && this.mParentView.getLayoutParams() instanceof MarginLayoutParams) {
/*     */       MarginLayoutParams lp = (MarginLayoutParams)this.mParentView.getLayoutParams();
/*     */       if (lp.bottomMargin != this.mParentMarginBottom) {
/*     */         lp.bottomMargin = this.mParentMarginBottom;
/*     */         this.mParentView.setLayoutParams((ViewGroup.LayoutParams)lp);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\widget\AutoScrollEditor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */