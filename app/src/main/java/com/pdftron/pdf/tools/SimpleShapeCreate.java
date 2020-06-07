/*     */ package com.pdftron.pdf.tools;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.content.SharedPreferences;
/*     */ import android.content.res.Configuration;
/*     */ import android.graphics.Paint;
/*     */ import android.graphics.PointF;
/*     */ import android.graphics.RectF;
/*     */ import android.util.Log;
/*     */ import android.view.MotionEvent;
/*     */ import androidx.annotation.Keep;
/*     */ import androidx.annotation.NonNull;
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.Annot;
/*     */ import com.pdftron.pdf.PDFDoc;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.Page;
/*     */ import com.pdftron.pdf.Rect;
/*     */ import com.pdftron.pdf.annots.Markup;
/*     */ import com.pdftron.pdf.config.ToolStyleConfig;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.AnnotUtils;
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
/*     */ @Keep
/*     */ public abstract class SimpleShapeCreate
/*     */   extends BaseTool
/*     */ {
/*  40 */   private static final String TAG = SimpleShapeCreate.class.getName();
/*     */   protected PointF mPt1;
/*     */   protected PointF mPt2;
/*     */   protected Paint mPaint;
/*     */   protected Paint mFillPaint;
/*     */   protected int mDownPageNum;
/*     */   protected RectF mPageCropOnClientF;
/*     */   protected float mThickness;
/*     */   protected float mThicknessDraw;
/*     */   protected int mStrokeColor;
/*     */   protected int mFillColor;
/*     */   protected float mOpacity;
/*     */   protected boolean mIsAllPointsOutsidePage;
/*     */   protected boolean mHasFill;
/*  54 */   protected final int START_DRAWING_THRESHOLD = 5;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleShapeCreate(@NonNull PDFViewCtrl ctrl) {
/*  60 */     super(ctrl);
/*  61 */     this.mPt1 = new PointF(0.0F, 0.0F);
/*  62 */     this.mPt2 = new PointF(0.0F, 0.0F);
/*  63 */     this.mPaint = new Paint();
/*  64 */     this.mPaint.setAntiAlias(true);
/*  65 */     this.mPaint.setColor(-16776961);
/*  66 */     this.mPaint.setStyle(Paint.Style.STROKE);
/*  67 */     this.mFillPaint = new Paint(this.mPaint);
/*  68 */     this.mFillPaint.setStyle(Paint.Style.FILL);
/*  69 */     this.mFillPaint.setColor(0);
/*  70 */     this.mThickness = 1.0F;
/*  71 */     this.mThicknessDraw = 1.0F;
/*  72 */     this.mHasFill = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract ToolManager.ToolModeBase getToolMode();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int getCreateAnnotType();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setupAnnotProperty(int color, float opacity, float thickness, int fillColor, String icon, String pdfTronFontName) {
/*  90 */     this.mStrokeColor = color;
/*  91 */     this.mFillColor = fillColor;
/*  92 */     this.mOpacity = opacity;
/*  93 */     this.mThickness = thickness;
/*     */     
/*  95 */     SharedPreferences settings = Tool.getToolPreferences(this.mPdfViewCtrl.getContext());
/*  96 */     SharedPreferences.Editor editor = settings.edit();
/*  97 */     editor.putInt(getColorKey(getCreateAnnotType()), this.mStrokeColor);
/*  98 */     editor.putInt(getColorFillKey(getCreateAnnotType()), this.mFillColor);
/*  99 */     editor.putFloat(getOpacityKey(getCreateAnnotType()), this.mOpacity);
/* 100 */     editor.putFloat(getThicknessKey(getCreateAnnotType()), this.mThickness);
/* 101 */     editor.apply();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCreatingAnnotation() {
/* 109 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onConfigurationChanged(Configuration newConfig) {
/* 118 */     resetPts();
/* 119 */     this.mPdfViewCtrl.invalidate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onSingleTapConfirmed(MotionEvent e) {
/* 127 */     ToolManager.ToolMode toolMode = ToolManager.getDefaultToolMode(getToolMode());
/* 128 */     if (this.mForceSameNextToolMode && toolMode != ToolManager.ToolMode.INK_CREATE && toolMode != ToolManager.ToolMode.INK_ERASER && toolMode != ToolManager.ToolMode.TEXT_ANNOT_CREATE) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 133 */       int x = (int)(e.getX() + 0.5D);
/* 134 */       int y = (int)(e.getY() + 0.5D);
/*     */       
/* 136 */       Annot tempAnnot = this.mPdfViewCtrl.getAnnotationAt(x, y);
/* 137 */       int page = this.mPdfViewCtrl.getPageNumberFromScreenPt(x, y);
/*     */       
/* 139 */       setCurrentDefaultToolModeHelper(toolMode);
/*     */       try {
/* 141 */         if (null != tempAnnot && tempAnnot.isValid()) {
/* 142 */           ((ToolManager)this.mPdfViewCtrl.getToolManager()).selectAnnot(tempAnnot, page);
/*     */         } else {
/* 144 */           this.mNextToolMode = getToolMode();
/*     */         } 
/* 146 */       } catch (PDFNetException pDFNetException) {}
/*     */ 
/*     */       
/* 149 */       return false;
/*     */     } 
/* 151 */     return super.onSingleTapConfirmed(e);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDown(MotionEvent e) {
/* 159 */     super.onDown(e);
/*     */ 
/*     */     
/* 162 */     PointF snapPoint = snapToNearestIfEnabled(new PointF(e.getX(), e.getY()));
/* 163 */     snapPoint.x += this.mPdfViewCtrl.getScrollX();
/* 164 */     snapPoint.y += this.mPdfViewCtrl.getScrollY();
/*     */ 
/*     */ 
/*     */     
/* 168 */     this.mDownPageNum = this.mPdfViewCtrl.getPageNumberFromScreenPt(e.getX(), e.getY());
/* 169 */     if (this.mDownPageNum < 1) {
/* 170 */       this.mIsAllPointsOutsidePage = true;
/* 171 */       this.mDownPageNum = this.mPdfViewCtrl.getCurrentPage();
/*     */     } else {
/* 173 */       this.mIsAllPointsOutsidePage = false;
/*     */     } 
/* 175 */     if (this.mDownPageNum >= 1) {
/* 176 */       this.mPageCropOnClientF = Utils.buildPageBoundBoxOnClient(this.mPdfViewCtrl, this.mDownPageNum);
/* 177 */       Utils.snapPointToRect(this.mPt1, this.mPageCropOnClientF);
/*     */     } 
/*     */ 
/*     */     
/* 181 */     this.mPt2.set(this.mPt1);
/*     */ 
/*     */ 
/*     */     
/* 185 */     Context context = this.mPdfViewCtrl.getContext();
/* 186 */     SharedPreferences settings = Tool.getToolPreferences(context);
/* 187 */     this.mThickness = settings.getFloat(getThicknessKey(getCreateAnnotType()), ToolStyleConfig.getInstance().getDefaultThickness(context, getCreateAnnotType()));
/* 188 */     this.mStrokeColor = settings.getInt(getColorKey(getCreateAnnotType()), ToolStyleConfig.getInstance().getDefaultColor(context, getCreateAnnotType()));
/* 189 */     this.mFillColor = settings.getInt(getColorFillKey(getCreateAnnotType()), ToolStyleConfig.getInstance().getDefaultFillColor(context, getCreateAnnotType()));
/* 190 */     this.mOpacity = settings.getFloat(getOpacityKey(getCreateAnnotType()), ToolStyleConfig.getInstance().getDefaultOpacity(context, getCreateAnnotType()));
/*     */     
/* 192 */     float zoom = (float)this.mPdfViewCtrl.getZoom();
/* 193 */     this.mThicknessDraw = this.mThickness * zoom;
/* 194 */     this.mPaint.setStrokeWidth(this.mThicknessDraw);
/* 195 */     int color = Utils.getPostProcessedColor(this.mPdfViewCtrl, this.mStrokeColor);
/* 196 */     this.mPaint.setColor(color);
/* 197 */     this.mPaint.setAlpha((int)(255.0F * this.mOpacity));
/* 198 */     if (this.mHasFill) {
/* 199 */       this.mFillPaint.setColor(Utils.getPostProcessedColor(this.mPdfViewCtrl, this.mFillColor));
/* 200 */       this.mFillPaint.setAlpha((int)(255.0F * this.mOpacity));
/*     */     } 
/*     */     
/* 203 */     this.mAnnotPushedBack = false;
/*     */     
/* 205 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onMove(MotionEvent e1, MotionEvent e2, float x_dist, float y_dist) {
/* 213 */     super.onMove(e1, e2, x_dist, y_dist);
/*     */     
/* 215 */     if (this.mAllowTwoFingerScroll) {
/* 216 */       return false;
/*     */     }
/*     */     
/* 219 */     if (this.mAllowOneFingerScrollWithStylus) {
/* 220 */       return false;
/*     */     }
/*     */     
/* 223 */     if (this.mIsAllPointsOutsidePage)
/*     */     {
/* 225 */       if (this.mPdfViewCtrl.getPageNumberFromScreenPt(e2.getX(), e2.getY()) >= 1) {
/* 226 */         this.mIsAllPointsOutsidePage = false;
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 232 */     float x = this.mPt2.x;
/* 233 */     float y = this.mPt2.y;
/* 234 */     PointF snapPoint = snapToNearestIfEnabled(new PointF(e2.getX(), e2.getY()));
/* 235 */     snapPoint.x += this.mPdfViewCtrl.getScrollX();
/* 236 */     snapPoint.y += this.mPdfViewCtrl.getScrollY();
/*     */     
/* 238 */     Utils.snapPointToRect(this.mPt2, this.mPageCropOnClientF);
/*     */     
/* 240 */     float min_x = Math.min(Math.min(x, this.mPt2.x), this.mPt1.x) - this.mThicknessDraw;
/* 241 */     float max_x = Math.max(Math.max(x, this.mPt2.x), this.mPt1.x) + this.mThicknessDraw;
/* 242 */     float min_y = Math.min(Math.min(y, this.mPt2.y), this.mPt1.y) - this.mThicknessDraw;
/* 243 */     float max_y = Math.max(Math.max(y, this.mPt2.y), this.mPt1.y) + this.mThicknessDraw;
/*     */     
/* 245 */     this.mPdfViewCtrl.invalidate((int)min_x, (int)min_y, (int)Math.ceil(max_x), (int)Math.ceil(max_y));
/* 246 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onUp(MotionEvent e, PDFViewCtrl.PriorEventMode priorEventMode) {
/* 254 */     super.onUp(e, priorEventMode);
/*     */ 
/*     */     
/* 257 */     if (this.mAllowTwoFingerScroll) {
/* 258 */       doneTwoFingerScrolling();
/* 259 */       return false;
/*     */     } 
/*     */     
/* 262 */     if (priorEventMode == PDFViewCtrl.PriorEventMode.PAGE_SLIDING) {
/* 263 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 267 */     if (this.mPt1.x == this.mPt2.x && this.mPt1.y == this.mPt2.y) {
/* 268 */       resetPts();
/* 269 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 274 */     if (this.mAnnotPushedBack && this.mForceSameNextToolMode) {
/* 275 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 279 */     if (this.mIsAllPointsOutsidePage) {
/* 280 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 284 */     this.mAllowOneFingerScrollWithStylus = (this.mStylusUsed && e.getToolType(0) != 2);
/* 285 */     if (this.mAllowOneFingerScrollWithStylus) {
/* 286 */       return true;
/*     */     }
/*     */     
/* 289 */     setNextToolModeHelper();
/* 290 */     setCurrentDefaultToolModeHelper(getToolMode());
/*     */     
/* 292 */     addOldTools();
/*     */     
/* 294 */     boolean shouldUnlock = false;
/*     */     try {
/* 296 */       this.mPdfViewCtrl.docLock(true);
/* 297 */       shouldUnlock = true;
/* 298 */       Rect rect = getShapeBBox();
/* 299 */       if (rect != null) {
/* 300 */         Annot markup = createMarkup(this.mPdfViewCtrl.getDoc(), rect);
/* 301 */         setStyle(markup);
/*     */         
/* 303 */         markup.refreshAppearance();
/*     */         
/* 305 */         Page page = this.mPdfViewCtrl.getDoc().getPage(this.mDownPageNum);
/* 306 */         if (page != null) {
/* 307 */           page.annotPushBack(markup);
/* 308 */           this.mAnnotPushedBack = true;
/* 309 */           setAnnot(markup, this.mDownPageNum);
/* 310 */           buildAnnotBBox();
/* 311 */           this.mPdfViewCtrl.update(this.mAnnot, this.mAnnotPageNum);
/* 312 */           raiseAnnotationAddedEvent(this.mAnnot, this.mAnnotPageNum);
/*     */         } 
/*     */       } 
/* 315 */     } catch (Exception ex) {
/* 316 */       this.mNextToolMode = ToolManager.ToolMode.PAN;
/* 317 */       ((ToolManager)this.mPdfViewCtrl.getToolManager()).annotationCouldNotBeAdded(ex.getMessage());
/* 318 */       AnalyticsHandlerAdapter.getInstance().sendException(ex);
/* 319 */       onCreateMarkupFailed(ex);
/*     */     } finally {
/* 321 */       if (shouldUnlock) {
/* 322 */         this.mPdfViewCtrl.docUnlock();
/*     */       }
/*     */     } 
/*     */     
/* 326 */     return skipOnUpPriorEvent(priorEventMode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doneTwoFingerScrolling() {
/* 334 */     super.doneTwoFingerScrolling();
/*     */     
/* 336 */     this.mPt2.set(this.mPt1);
/* 337 */     this.mPdfViewCtrl.invalidate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onFlingStop() {
/* 346 */     if (this.mAllowTwoFingerScroll) {
/* 347 */       doneTwoFingerScrolling();
/*     */     }
/* 349 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onScaleBegin(float x, float y) {
/* 358 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Rect getShapeBBox() {
/* 368 */     double[] pts1 = this.mPdfViewCtrl.convScreenPtToPagePt((this.mPt1.x - this.mPdfViewCtrl.getScrollX()), (this.mPt1.y - this.mPdfViewCtrl.getScrollY()), this.mDownPageNum);
/* 369 */     double[] pts2 = this.mPdfViewCtrl.convScreenPtToPagePt((this.mPt2.x - this.mPdfViewCtrl.getScrollX()), (this.mPt2.y - this.mPdfViewCtrl.getScrollY()), this.mDownPageNum);
/*     */     
/*     */     try {
/* 372 */       Rect rect = new Rect(pts1[0], pts1[1], pts2[0], pts2[1]);
/* 373 */       return rect;
/* 374 */     } catch (Exception e) {
/* 375 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setStyle(Annot annot) {
/* 385 */     setStyle(annot, this.mHasFill);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setStyle(Annot annot, boolean hasFill) {
/*     */     try {
/* 396 */       AnnotUtils.setStyle(annot, hasFill, this.mStrokeColor, this.mFillColor, this.mThickness, this.mOpacity);
/*     */ 
/*     */ 
/*     */       
/* 400 */       if (annot instanceof Markup) {
/* 401 */         setAuthor((Markup)annot);
/*     */       }
/* 403 */     } catch (PDFNetException pDFNetException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract Annot createMarkup(@NonNull PDFDoc paramPDFDoc, Rect paramRect) throws PDFNetException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void resetPts() {
/* 422 */     this.mPt1.set(0.0F, 0.0F);
/* 423 */     this.mPt2.set(0.0F, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setNextToolModeHelper() {
/* 431 */     ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 432 */     if (toolManager.isAutoSelectAnnotation() || !this.mForceSameNextToolMode) {
/* 433 */       this.mNextToolMode = getDefaultNextTool();
/*     */     } else {
/* 435 */       this.mNextToolMode = getToolMode();
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
/*     */   protected ToolManager.ToolMode getDefaultNextTool() {
/* 447 */     return ToolManager.ToolMode.ANNOT_EDIT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onCreateMarkupFailed(Exception e) {
/* 457 */     Log.e(TAG, "onCreateMarkupFailed", e);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canDrawLoupe() {
/* 462 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getLoupeType() {
/* 467 */     return 1;
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\SimpleShapeCreate.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */