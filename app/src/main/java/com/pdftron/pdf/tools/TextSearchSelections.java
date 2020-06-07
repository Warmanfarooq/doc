/*     */ package com.pdftron.pdf.tools;
/*     */ 
/*     */ import android.animation.TimeInterpolator;
/*     */ import android.annotation.SuppressLint;
/*     */ import android.content.Context;
/*     */ import android.graphics.Canvas;
/*     */ import android.graphics.Paint;
/*     */ import android.graphics.PorterDuff;
/*     */ import android.graphics.PorterDuffXfermode;
/*     */ import android.graphics.RectF;
/*     */ import android.graphics.Xfermode;
/*     */ import android.util.AttributeSet;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.view.animation.OvershootInterpolator;
/*     */ import androidx.annotation.ColorInt;
/*     */ import androidx.annotation.NonNull;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.Rect;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ class TextSearchSelections {
/*  25 */   private final List<CustomRelativeLayout> mSelectionViews = new ArrayList<>();
/*     */   
/*     */   void createAndAddSelectionView(PDFViewCtrl pdfViewCtrl, Rect rect, int pageNum, @ColorInt int color, PorterDuff.Mode blendMode) {
/*  28 */     TextSearchSelectionView selectionView = new TextSearchSelectionView(pdfViewCtrl.getContext());
/*  29 */     selectionView.setZoomWithParent(true);
/*  30 */     selectionView.setSelectionColor(color);
/*  31 */     selectionView.setBlendMode(blendMode);
/*  32 */     selectionView.setRect(pdfViewCtrl, rect, pageNum);
/*  33 */     animateSelectionView((View)selectionView, pdfViewCtrl);
/*  34 */     this.mSelectionViews.add(selectionView);
/*     */   }
/*     */   
/*     */   private void animateSelectionView(View view, PDFViewCtrl pdfViewCtrl) {
/*  38 */     view.setScaleX(0.9F);
/*  39 */     view.setScaleY(0.9F);
/*  40 */     pdfViewCtrl.addView(view);
/*  41 */     view.animate()
/*  42 */       .scaleX(1.2F)
/*  43 */       .scaleY(1.2F)
/*  44 */       .setDuration(200L)
/*  45 */       .setInterpolator((TimeInterpolator)new OvershootInterpolator(5.0F));
/*     */   }
/*     */   
/*     */   void clear(ViewGroup parent) {
/*  49 */     for (CustomRelativeLayout selectionView : this.mSelectionViews) {
/*  50 */       parent.removeView((View)selectionView);
/*     */     }
/*  52 */     this.mSelectionViews.clear();
/*     */   }
/*     */   
/*     */   private static class TextSearchSelectionView extends CustomRelativeLayout {
/*  56 */     private String TAG = "TextSearchSelectionView";
/*     */     private Paint mPaint;
/*     */     private float mCornerRadius;
/*  59 */     private RectF mRect = new RectF();
/*     */     
/*     */     public TextSearchSelectionView(Context context, PDFViewCtrl parent, double x, double y, int page_num) {
/*  62 */       super(context, parent, x, y, page_num);
/*  63 */       init();
/*     */     }
/*     */     
/*     */     public TextSearchSelectionView(Context context) {
/*  67 */       super(context);
/*  68 */       init();
/*     */     }
/*     */     
/*     */     public TextSearchSelectionView(Context context, AttributeSet attrs) {
/*  72 */       super(context, attrs);
/*  73 */       init();
/*     */     }
/*     */     
/*     */     public TextSearchSelectionView(Context context, AttributeSet attrs, int defStyleAttr) {
/*  77 */       super(context, attrs, defStyleAttr);
/*  78 */       init();
/*     */     }
/*     */     
/*     */     public TextSearchSelectionView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
/*  82 */       super(context, attrs, defStyleAttr, defStyleRes);
/*  83 */       init();
/*     */     }
/*     */     
/*     */     private void init() {
/*  87 */       setWillNotDraw(false);
/*  88 */       this.mPaint = new Paint(1);
/*  89 */       this.mPaint.setAntiAlias(true);
/*  90 */       this.mPaint.setStyle(Paint.Style.FILL);
/*  91 */       this.mCornerRadius = getCornerRadius(getContext());
/*  92 */       setTag(this.TAG);
/*     */     }
/*     */     
/*     */     private static float getCornerRadius(@NonNull Context context) {
/*  96 */       return Utils.convDp2Pix(context, 2.0F);
/*     */     }
/*     */     
/*     */     private void setSelectionColor(@ColorInt int color) {
/* 100 */       this.mPaint.setColor(color);
/*     */     }
/*     */     
/*     */     private void setBlendMode(PorterDuff.Mode blendMode) {
/* 104 */       this.mPaint.setXfermode((Xfermode)new PorterDuffXfermode(blendMode));
/*     */     }
/*     */ 
/*     */     
/*     */     @SuppressLint({"NewApi"})
/*     */     protected void onDraw(Canvas canvas) {
/* 110 */       super.onDraw(canvas);
/* 111 */       this.mRect.set(0.0F, 0.0F, getWidth(), getHeight());
/* 112 */       canvas.drawRoundRect(this.mRect, this.mCornerRadius, this.mCornerRadius, this.mPaint);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\TextSearchSelections.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */