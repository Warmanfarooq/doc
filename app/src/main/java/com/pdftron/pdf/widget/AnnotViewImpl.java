/*     */ package com.pdftron.pdf.widget;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.graphics.DashPathEffect;
/*     */ import android.graphics.Paint;
/*     */ import android.graphics.Path;
/*     */ import android.graphics.PathEffect;
/*     */ import android.graphics.PointF;
/*     */ import android.graphics.PorterDuff;
/*     */ import android.graphics.PorterDuffXfermode;
/*     */ import android.graphics.Rect;
/*     */ import android.graphics.RectF;
/*     */ import android.graphics.Xfermode;
/*     */ import android.os.AsyncTask;
/*     */ import androidx.annotation.NonNull;
/*     */ import com.pdftron.pdf.CurvePainter;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.asynctask.LoadFontAsyncTask;
/*     */ import com.pdftron.pdf.config.ToolConfig;
/*     */ import com.pdftron.pdf.model.AnnotStyle;
/*     */ import com.pdftron.pdf.model.FontResource;
/*     */ import com.pdftron.pdf.model.RulerItem;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.tools.ToolManager;
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
/*     */ public class AnnotViewImpl
/*     */ {
/*     */   public AnnotStyle mAnnotStyle;
/*     */   public CurvePainter mCurvePainter;
/*     */   public PDFViewCtrl mPdfViewCtrl;
/*     */   public int mPageNum;
/*  41 */   public PointF mPt1 = new PointF(0.0F, 0.0F);
/*  42 */   public PointF mPt2 = new PointF(0.0F, 0.0F);
/*     */   
/*     */   public Paint mPaint;
/*     */   public Paint mFillPaint;
/*     */   public Paint mCtrlPtsPaint;
/*     */   public Paint mBmpPaint;
/*     */   public Paint mBmpMultBlendPaint;
/*     */   public Paint mGuidelinePaint;
/*     */   public float mGuidelinExtend;
/*     */   public float mRotateCenterRadius;
/*     */   public float mThickness;
/*     */   public float mThicknessReserve;
/*     */   public float mThicknessDraw;
/*     */   public int mStrokeColor;
/*     */   public int mFillColor;
/*     */   public float mOpacity;
/*  58 */   public double mZoom = 1.0D;
/*     */   
/*     */   public float mCtrlRadius;
/*     */   public boolean mHasSelectionPermission = true;
/*     */   public PointF[] mCtrlPts;
/*  63 */   public ArrayList<PointF> mVertices = new ArrayList<>();
/*     */   @NonNull
/*  65 */   public RectF mAnnotRectF = new RectF();
/*     */   
/*  67 */   public Rect mAnnotRect = new Rect();
/*  68 */   Path mGuidelinePath = new Path();
/*     */   
/*     */   public boolean mCanDrawCtrlPts = true;
/*     */   
/*     */   AnnotView.SnapMode mSnapMode;
/*  73 */   RectF mBBox = new RectF();
/*     */   
/*     */   public AnnotViewImpl(Context context) {
/*  76 */     init(context);
/*     */   }
/*     */   
/*     */   public AnnotViewImpl(PDFViewCtrl pdfViewCtrl, AnnotStyle annotStyle) {
/*  80 */     init(pdfViewCtrl.getContext());
/*     */     
/*  82 */     setAnnotStyle(pdfViewCtrl, annotStyle);
/*     */   }
/*     */   
/*     */   public void init(Context context) {
/*  86 */     this.mPaint = new Paint();
/*  87 */     this.mPaint.setAntiAlias(true);
/*  88 */     this.mPaint.setColor(-65536);
/*  89 */     this.mPaint.setStyle(Paint.Style.STROKE);
/*  90 */     this.mPaint.setStrokeJoin(Paint.Join.MITER);
/*  91 */     this.mPaint.setStrokeCap(Paint.Cap.SQUARE);
/*     */     
/*  93 */     this.mFillPaint = new Paint(this.mPaint);
/*  94 */     this.mFillPaint.setStyle(Paint.Style.FILL);
/*  95 */     this.mFillPaint.setColor(0);
/*     */     
/*  97 */     this.mCtrlPtsPaint = new Paint(this.mPaint);
/*     */     
/*  99 */     this.mBmpPaint = new Paint();
/* 100 */     this.mBmpPaint.setStyle(Paint.Style.FILL);
/* 101 */     this.mBmpPaint.setAntiAlias(true);
/* 102 */     this.mBmpPaint.setFilterBitmap(false);
/*     */     
/* 104 */     this.mBmpMultBlendPaint = new Paint(this.mBmpPaint);
/* 105 */     this.mBmpMultBlendPaint.setXfermode((Xfermode)new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
/*     */     
/* 107 */     this.mGuidelinePaint = new Paint(this.mPaint);
/* 108 */     this.mGuidelinePaint.setStyle(Paint.Style.STROKE);
/* 109 */     DashPathEffect dashPathEffect = new DashPathEffect(new float[] { Utils.convDp2Pix(context, 4.5F), Utils.convDp2Pix(context, 2.5F) }0.0F);
/* 110 */     this.mGuidelinePaint.setPathEffect((PathEffect)dashPathEffect);
/* 111 */     this.mGuidelinePaint.setStrokeWidth(Utils.convDp2Pix(context, 1.0F));
/* 112 */     this.mGuidelinePaint.setColor(context.getResources().getColor(R.color.tools_annot_edit_rotate_guideline));
/* 113 */     this.mGuidelinExtend = Utils.convDp2Pix(context, 16.0F) * 3.0F;
/* 114 */     this.mRotateCenterRadius = context.getResources().getDimensionPixelSize(R.dimen.rotate_guideline_center_radius);
/*     */     
/* 116 */     this.mThicknessDraw = 1.0F;
/* 117 */     this.mOpacity = 1.0F;
/*     */     
/* 119 */     this.mCtrlRadius = (context.getResources().getDimensionPixelSize(R.dimen.selection_widget_size_w_margin) / 2);
/*     */   }
/*     */   
/*     */   public void setAnnotStyle(PDFViewCtrl pdfViewCtrl, AnnotStyle annotStyle) {
/* 123 */     this.mPdfViewCtrl = pdfViewCtrl;
/* 124 */     this.mAnnotStyle = annotStyle;
/*     */     
/* 126 */     this.mStrokeColor = annotStyle.getColor();
/* 127 */     this.mFillColor = annotStyle.getFillColor();
/* 128 */     this.mThickness = this.mThicknessReserve = annotStyle.getThickness();
/* 129 */     this.mOpacity = annotStyle.getOpacity();
/*     */     
/* 131 */     this.mPaint.setColor(Utils.getPostProcessedColor(this.mPdfViewCtrl, this.mStrokeColor));
/* 132 */     this.mFillPaint.setColor(Utils.getPostProcessedColor(this.mPdfViewCtrl, this.mFillColor));
/*     */     
/* 134 */     this.mPaint.setAlpha((int)(255.0F * this.mOpacity));
/* 135 */     this.mFillPaint.setAlpha((int)(255.0F * this.mOpacity));
/*     */     
/* 137 */     updateColor(this.mStrokeColor);
/*     */   }
/*     */   
/*     */   public void updateColor(int color) {
/* 141 */     this.mStrokeColor = color;
/* 142 */     this.mPaint.setColor(Utils.getPostProcessedColor(this.mPdfViewCtrl, this.mStrokeColor));
/* 143 */     updateOpacity(this.mOpacity);
/*     */     
/* 145 */     updateThickness(this.mThicknessReserve);
/*     */   }
/*     */   
/*     */   public void updateFillColor(int color) {
/* 149 */     this.mFillColor = color;
/* 150 */     this.mFillPaint.setColor(Utils.getPostProcessedColor(this.mPdfViewCtrl, this.mFillColor));
/* 151 */     updateOpacity(this.mOpacity);
/*     */   }
/*     */   
/*     */   public void updateThickness(float thickness) {
/* 155 */     this.mThickness = this.mThicknessReserve = thickness;
/* 156 */     if (this.mStrokeColor == 0) {
/* 157 */       this.mThickness = 1.0F;
/*     */     } else {
/* 159 */       this.mThickness = thickness;
/*     */     } 
/* 161 */     this.mThicknessDraw = (float)this.mZoom * this.mThickness;
/* 162 */     this.mPaint.setStrokeWidth(this.mThicknessDraw);
/*     */   }
/*     */   
/*     */   public void updateOpacity(float opacity) {
/* 166 */     this.mOpacity = opacity;
/* 167 */     this.mPaint.setAlpha((int)(255.0F * this.mOpacity));
/* 168 */     this.mFillPaint.setAlpha((int)(255.0F * this.mOpacity));
/*     */   }
/*     */   
/*     */   public void updateRulerItem(RulerItem rulerItem) {
/* 172 */     this.mAnnotStyle.setRulerItem(rulerItem);
/*     */   }
/*     */   
/*     */   public void setZoom(double zoom) {
/* 176 */     this.mZoom = zoom;
/* 177 */     this.mThicknessDraw = (float)this.mZoom * this.mThickness;
/* 178 */     this.mPaint.setStrokeWidth(this.mThicknessDraw);
/*     */   }
/*     */   
/*     */   public void setVertices(PointF... points) {
/* 182 */     this.mVertices.clear();
/* 183 */     if (points != null) {
/* 184 */       this.mVertices.addAll(Arrays.asList(points));
/*     */     }
/*     */   }
/*     */   
/*     */   public void removeCtrlPts() {
/* 189 */     this.mCanDrawCtrlPts = false;
/*     */   }
/*     */   
/*     */   public void loadFont() {
/* 193 */     ArrayList<FontResource> fonts = ToolConfig.getInstance().getFontList();
/* 194 */     if (null == fonts || fonts.size() == 0) {
/* 195 */       ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 196 */       LoadFontAsyncTask fontAsyncTask = new LoadFontAsyncTask(this.mPdfViewCtrl.getContext(), toolManager.getFreeTextFonts());
/* 197 */       fontAsyncTask.setCallback(new LoadFontAsyncTask.Callback()
/*     */           {
/*     */             public void onFinish(ArrayList<FontResource> fonts) {
/* 200 */               FontResource font = AnnotViewImpl.this.getMatchingFont(fonts);
/* 201 */               AnnotViewImpl.this.mAnnotStyle.setFont(font);
/*     */             }
/*     */           });
/* 204 */       fontAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Object[])new Void[0]);
/*     */     } else {
/* 206 */       FontResource font = getMatchingFont(fonts);
/* 207 */       this.mAnnotStyle.setFont(font);
/*     */     } 
/*     */   }
/*     */   
/*     */   private FontResource getMatchingFont(ArrayList<FontResource> fonts) {
/* 212 */     for (FontResource font : fonts) {
/* 213 */       if (font.equals(this.mAnnotStyle.getFont())) {
/* 214 */         this.mAnnotStyle.getFont().setFilePath(font.getFilePath());
/*     */         break;
/*     */       } 
/*     */     } 
/* 218 */     return this.mAnnotStyle.getFont();
/*     */   }
/*     */   
/*     */   public boolean isNightMode() {
/*     */     try {
/* 223 */       return (this.mPdfViewCtrl.getColorPostProcessMode() == 3 || this.mPdfViewCtrl
/* 224 */         .getColorPostProcessMode() == 1);
/* 225 */     } catch (Exception ex) {
/* 226 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isAnnotResizable() {
/* 231 */     return (this.mAnnotStyle.getAnnotType() != 0 && this.mAnnotStyle
/* 232 */       .getAnnotType() != 17 && this.mAnnotStyle
/* 233 */       .getAnnotType() != 16 && this.mAnnotStyle
/* 234 */       .getAnnotType() != 1010 && 
/* 235 */       !this.mAnnotStyle.isRCFreeText());
/*     */   }
/*     */   
/*     */   public boolean isFreeHighlighter() {
/* 239 */     return (this.mAnnotStyle.getAnnotType() == 1004);
/*     */   }
/*     */   
/*     */   public boolean isStamp() {
/* 243 */     return (this.mAnnotStyle.getAnnotType() == 12);
/*     */   }
/*     */   
/*     */   public boolean isCallout() {
/* 247 */     return (this.mAnnotStyle.getAnnotType() == 1007);
/*     */   }
/*     */   
/*     */   public boolean isAnnotEditLine() {
/* 251 */     return (this.mAnnotStyle.getAnnotType() == 3 || this.mAnnotStyle
/* 252 */       .getAnnotType() == 1001 || this.mAnnotStyle
/* 253 */       .getAnnotType() == 1006);
/*     */   }
/*     */   
/*     */   public boolean isAnnotEditAdvancedShape() {
/* 257 */     return (this.mAnnotStyle.getAnnotType() == 7 || this.mAnnotStyle
/* 258 */       .getAnnotType() == 6 || this.mAnnotStyle
/* 259 */       .getAnnotType() == 1005 || this.mAnnotStyle
/* 260 */       .getAnnotType() == 1007 || this.mAnnotStyle
/* 261 */       .getAnnotType() == 1008 || this.mAnnotStyle
/* 262 */       .getAnnotType() == 1009);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\widget\AnnotViewImpl.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */