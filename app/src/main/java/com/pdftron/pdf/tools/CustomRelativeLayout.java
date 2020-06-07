/*     */ package com.pdftron.pdf.tools;
/*     */ 
/*     */ import android.annotation.TargetApi;
/*     */ import android.content.Context;
/*     */ import android.content.res.TypedArray;
/*     */ import android.util.AttributeSet;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.view.ViewParent;
/*     */ import android.widget.RelativeLayout;
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.Annot;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.Rect;
/*     */ import com.pdftron.pdf.annots.FreeText;
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
/*     */ public class CustomRelativeLayout
/*     */   extends RelativeLayout
/*     */   implements PDFViewCtrl.OnCanvasSizeChangeListener
/*     */ {
/*  36 */   private static final String TAG = CustomRelativeLayout.class.getName();
/*     */   
/*     */   private static final boolean DEFAULT_ZOOM_WITH_PARENT = true;
/*     */   protected PDFViewCtrl mParentView;
/*  40 */   protected double mPagePosLeft = 0.0D;
/*  41 */   protected double mPagePosRight = 0.0D;
/*  42 */   protected double mPagePosTop = 0.0D;
/*  43 */   protected double mPagePosBottom = 0.0D;
/*  44 */   protected double[] mScreenPt = new double[2];
/*  45 */   protected int mPageNum = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean mZoomWithParent = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CustomRelativeLayout(Context context, PDFViewCtrl parent, double x, double y, int page_num) {
/*  58 */     this(context);
/*  59 */     this.mParentView = parent;
/*  60 */     setPagePosition(x, y, page_num);
/*     */   }
/*     */   
/*     */   public CustomRelativeLayout(Context context) {
/*  64 */     this(context, (AttributeSet)null);
/*     */   }
/*     */   
/*     */   public CustomRelativeLayout(Context context, AttributeSet attrs) {
/*  68 */     this(context, attrs, 0);
/*     */   }
/*     */   
/*     */   public CustomRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
/*  72 */     super(context, attrs, defStyleAttr);
/*  73 */     init(context, attrs, defStyleAttr, 0);
/*     */   }
/*     */   
/*     */   @TargetApi(21)
/*     */   public CustomRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
/*  78 */     super(context, attrs, defStyleAttr, defStyleRes);
/*  79 */     init(context, attrs, defStyleAttr, defStyleRes);
/*     */   }
/*     */   
/*     */   private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
/*  83 */     TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomRelativeLayout, defStyleAttr, defStyleRes);
/*     */     try {
/*  85 */       double x = a.getFloat(R.styleable.CustomRelativeLayout_posX, 0.0F);
/*  86 */       double y = a.getFloat(R.styleable.CustomRelativeLayout_posY, 0.0F);
/*  87 */       int page = a.getInt(R.styleable.CustomRelativeLayout_pageNum, 1);
/*  88 */       setPagePosition(x, y, page);
/*  89 */       setZoomWithParent(a.getBoolean(R.styleable.CustomRelativeLayout_zoomWithParent, true));
/*     */     } finally {
/*  91 */       a.recycle();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setZoomWithParent(boolean zoomWithParent) {
/* 102 */     this.mZoomWithParent = zoomWithParent;
/*     */   }
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
/*     */   public void setPagePosition(double x, double y, int pageNum) {
/* 119 */     this.mPagePosLeft = x;
/* 120 */     this.mPagePosBottom = y;
/* 121 */     this.mPageNum = pageNum;
/*     */   }
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
/*     */   public void setScreenPosition(double left, double top, int pageNum) {
/* 135 */     double[] pt1 = this.mParentView.convScreenPtToPagePt(left, top, pageNum);
/* 136 */     this.mPagePosLeft = pt1[0];
/* 137 */     this.mPagePosTop = pt1[1];
/* 138 */     this.mPageNum = pageNum;
/*     */     
/* 140 */     requestLayout();
/* 141 */     invalidate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAnnot(PDFViewCtrl pdfViewCtrl, Annot annot, int annotPageNum) {
/* 152 */     if (null == pdfViewCtrl || null == annot)
/*     */       return; 
/*     */     try {
/*     */       Rect rect;
/* 156 */       if (!annot.isValid()) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/*     */       try {
/* 162 */         rect = annot.getVisibleContentBox();
/* 163 */       } catch (PDFNetException ex) {
/* 164 */         rect = annot.getRect();
/*     */       } 
/* 166 */       if (annot.getType() == 2) {
/* 167 */         FreeText freeText = new FreeText(annot);
/* 168 */         rect = freeText.getContentRect();
/*     */       } 
/* 170 */       rect.normalize();
/*     */       
/* 172 */       setRect(pdfViewCtrl, rect, annotPageNum);
/* 173 */     } catch (Exception ex) {
/* 174 */       ex.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRect(PDFViewCtrl pdfViewCtrl, Rect rect, int pageNum) {
/*     */     try {
/* 187 */       double minX = Math.min(rect.getX1(), rect.getX2());
/* 188 */       double minY = Math.min(rect.getY1(), rect.getY2());
/* 189 */       double maxX = Math.max(rect.getX1(), rect.getX2());
/* 190 */       double maxY = Math.max(rect.getY1(), rect.getY2());
/*     */       
/* 192 */       this.mParentView = pdfViewCtrl;
/*     */ 
/*     */       
/* 195 */       double[] pt1 = this.mParentView.convPagePtToScreenPt(minX, minY, pageNum);
/* 196 */       double[] pt2 = this.mParentView.convPagePtToScreenPt(maxX, maxY, pageNum);
/*     */       
/* 198 */       double screenMinX = Math.min(pt1[0], pt2[0]);
/* 199 */       double screenMinY = Math.min(pt1[1], pt2[1]);
/* 200 */       double screenMaxX = Math.max(pt1[0], pt2[0]);
/* 201 */       double screenMaxY = Math.max(pt1[1], pt2[1]);
/*     */ 
/*     */       
/* 204 */       pt1 = this.mParentView.convScreenPtToPagePt(screenMinX, screenMaxY);
/* 205 */       pt2 = this.mParentView.convScreenPtToPagePt(screenMaxX, screenMinY);
/* 206 */       minX = pt1[0];
/* 207 */       minY = pt1[1];
/* 208 */       maxX = pt2[0];
/* 209 */       maxY = pt2[1];
/*     */       
/* 211 */       setPagePosition(minX, minY, pageNum);
/* 212 */       this.mPagePosRight = maxX;
/* 213 */       this.mPagePosTop = maxY;
/*     */       
/* 215 */       double[] screenBounds = this.mParentView.convPagePtToHorizontalScrollingPt(this.mPagePosRight, this.mPagePosTop, this.mPageNum);
/* 216 */       this.mScreenPt = this.mParentView.convPagePtToHorizontalScrollingPt(this.mPagePosLeft, this.mPagePosBottom, this.mPageNum);
/*     */       
/* 218 */       int width = (int)(Math.abs(screenBounds[0] - this.mScreenPt[0]) + 0.5D);
/* 219 */       int height = (int)(Math.abs(screenBounds[1] - this.mScreenPt[1]) + 0.5D);
/*     */       
/* 221 */       measure(
/* 222 */           MeasureSpec.makeMeasureSpec(width, 1073741824),
/* 223 */           MeasureSpec.makeMeasureSpec(height, 1073741824));
/* 224 */       setLayoutParams(new ViewGroup.LayoutParams(width, height));
/* 225 */     } catch (Exception ex) {
/* 226 */       ex.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onAttachedToWindow() {
/* 232 */     super.onAttachedToWindow();
/* 233 */     ViewParent parent = getParent();
/* 234 */     if (parent instanceof PDFViewCtrl) {
/* 235 */       this.mParentView = (PDFViewCtrl)parent;
/* 236 */       this.mParentView.addOnCanvasSizeChangeListener(this);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onDetachedFromWindow() {
/* 242 */     super.onDetachedFromWindow();
/* 243 */     if (this.mParentView != null) {
/* 244 */       this.mParentView.removeOnCanvasSizeChangeListener(this);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
/* 250 */     if (this.mParentView == null) {
/* 251 */       super.onMeasure(widthMeasureSpec, heightMeasureSpec);
/*     */       return;
/*     */     } 
/* 254 */     int width = MeasureSpec.getSize(widthMeasureSpec);
/* 255 */     int height = MeasureSpec.getSize(heightMeasureSpec);
/* 256 */     this.mScreenPt = this.mParentView.convPagePtToHorizontalScrollingPt(this.mPagePosLeft, this.mPagePosBottom, this.mPageNum);
/* 257 */     if (this.mZoomWithParent) {
/* 258 */       double[] screenBounds = this.mParentView.convPagePtToHorizontalScrollingPt(this.mPagePosRight, this.mPagePosTop, this.mPageNum);
/*     */       
/* 260 */       width = (int)(Math.abs(screenBounds[0] - this.mScreenPt[0]) + 0.5D);
/* 261 */       height = (int)(Math.abs(screenBounds[1] - this.mScreenPt[1]) + 0.5D);
/* 262 */       int nextWidthMeasureSpec = MeasureSpec.makeMeasureSpec(width, 1073741824);
/* 263 */       int nextHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, 1073741824);
/* 264 */       super.onMeasure(nextWidthMeasureSpec, nextHeightMeasureSpec);
/*     */     } else {
/* 266 */       super.onMeasure(widthMeasureSpec, heightMeasureSpec);
/*     */     } 
/*     */     
/* 269 */     int l = (int)this.mScreenPt[0];
/* 270 */     int t = (int)this.mScreenPt[1] - height;
/* 271 */     int r = (int)this.mScreenPt[0] + width;
/* 272 */     int b = (int)this.mScreenPt[1];
/* 273 */     layout(l, t, r, b);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onCanvasSizeChanged() {
/* 278 */     measure(getMeasuredWidthAndState(), getMeasuredHeightAndState());
/* 279 */     requestLayout();
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\CustomRelativeLayout.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */