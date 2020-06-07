/*     */ package com.pdftron.pdf.tools;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.content.SharedPreferences;
/*     */ import android.graphics.Canvas;
/*     */ import android.graphics.Matrix;
/*     */ import android.graphics.Paint;
/*     */ import android.graphics.Path;
/*     */ import android.graphics.PointF;
/*     */ import android.view.MotionEvent;
/*     */ import androidx.annotation.Keep;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.annotation.StringRes;
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.Annot;
/*     */ import com.pdftron.pdf.PDFDoc;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.Page;
/*     */ import com.pdftron.pdf.Point;
/*     */ import com.pdftron.pdf.Rect;
/*     */ import com.pdftron.pdf.annots.Ink;
/*     */ import com.pdftron.pdf.config.ToolStyleConfig;
/*     */ import com.pdftron.pdf.model.AnnotStyle;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.PressureInkUtils;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
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
/*     */ @Keep
/*     */ public class Eraser
/*     */   extends SimpleShapeCreate
/*     */ {
/*     */   private boolean mIsStartPointOutsidePage;
/*     */   private HashMap<Ink, Rect> mErasedInks;
/*     */   @NonNull
/*  60 */   private List<Ink> mPressureInkList = new ArrayList<>(); private EraserType mEraserType; private InkEraserMode mInkEraserMode; private Path mPath; private LinkedList<PointF> mStrokePoints; private LinkedList<LinkedList<PointF>> mStrokes; private LinkedList<Path> mPaths; private LinkedList<Annot> mAnnotList; private PointF mPt1BBox; private PointF mPt2BBox; private Point mCurrentPt;
/*     */   @NonNull
/*  62 */   private List<PointF> mEraseStroke = new ArrayList<>(); private Point mPrevPt; private Paint.Join oldStrokeJoin; private Paint.Cap oldStrokeCap; private EraserListener mListener; private float mX; private float mY; private static final float TOUCH_TOLERANCE = 1.0F; private float mEraserHalfThickness;
/*     */   public static interface EraserListener {
/*     */     void strokeErased(); }
/*     */   
/*  66 */   public enum EraserType { INK_ERASER,
/*  67 */     ANNOTATION_ERASER,
/*  68 */     HYBRID_ERASER; }
/*     */ 
/*     */   
/*     */   public enum InkEraserMode {
/*  72 */     PIXEL(R.string.tools_eraser_ink_mode_pixel),
/*  73 */     STROKE(R.string.tools_eraser_ink_mode_stroke);
/*     */     
/*     */     @StringRes
/*     */     public final int mLabelRes;
/*     */     
/*     */     InkEraserMode(int labelRes) {
/*  79 */       this.mLabelRes = labelRes;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public static InkEraserMode fromLabel(@NonNull Context context, @NonNull String eraserModeString) {
/*  91 */       for (InkEraserMode mode : values()) {
/*  92 */         if (context.getResources().getString(mode.mLabelRes).equals(eraserModeString)) {
/*  93 */           return mode;
/*     */         }
/*     */       } 
/*  96 */       return null;
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
/*     */ 
/*     */   
/*     */   private boolean mDoUpdate = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean mInitializedOnMove;
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
/*     */   public Eraser(@NonNull PDFViewCtrl ctrl) {
/* 131 */     this(ctrl, EraserType.HYBRID_ERASER, InkEraserMode.PIXEL);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Eraser(@NonNull PDFViewCtrl ctrl, @NonNull EraserType eraserType) {
/* 139 */     this(ctrl, eraserType, InkEraserMode.PIXEL);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Eraser(@NonNull PDFViewCtrl ctrl, @NonNull EraserType eraserType, @NonNull InkEraserMode inkEraserMode) {
/* 146 */     super(ctrl);
/* 147 */     this.mEraserType = eraserType;
/* 148 */     this.mInkEraserMode = inkEraserMode;
/*     */     
/* 150 */     this.mNextToolMode = ToolManager.ToolMode.INK_ERASER;
/*     */     
/* 152 */     this.mPath = new Path();
/* 153 */     this.mStrokePoints = new LinkedList<>();
/* 154 */     this.mStrokes = new LinkedList<>();
/* 155 */     this.mPaths = new LinkedList<>();
/* 156 */     this.mAnnotList = new LinkedList<>();
/*     */     
/* 158 */     this.mErasedInks = new HashMap<>();
/*     */     
/* 160 */     this.mPt1BBox = new PointF(0.0F, 0.0F);
/* 161 */     this.mPt2BBox = new PointF(0.0F, 0.0F);
/*     */     
/* 163 */     this.mCurrentPt = new Point(0.0D, 0.0D);
/* 164 */     this.mPrevPt = new Point(0.0D, 0.0D);
/*     */     
/* 166 */     this.oldStrokeJoin = this.mPaint.getStrokeJoin();
/* 167 */     this.oldStrokeCap = this.mPaint.getStrokeCap();
/* 168 */     this.mPaint.setStrokeJoin(Paint.Join.ROUND);
/* 169 */     this.mPaint.setStrokeCap(Paint.Cap.ROUND);
/*     */     
/* 171 */     SharedPreferences settings = Tool.getToolPreferences(this.mPdfViewCtrl.getContext());
/* 172 */     this.mEraserHalfThickness = 0.5F * settings.getFloat(getThicknessKey(getCreateAnnotType()), ToolStyleConfig.getInstance().getDefaultThickness(this.mPdfViewCtrl.getContext(), getCreateAnnotType()));
/* 173 */     String eraserTypeStr = settings.getString(getEraserTypeKey(getCreateAnnotType()), null);
/* 174 */     if (eraserTypeStr != null) {
/* 175 */       this.mEraserType = EraserType.valueOf(eraserTypeStr);
/*     */     }
/* 177 */     String inkEraserModeStr = settings.getString(getInkEraserModeKey(getCreateAnnotType()), null);
/* 178 */     if (inkEraserModeStr != null) {
/* 179 */       this.mInkEraserMode = InkEraserMode.valueOf(inkEraserModeStr);
/*     */     }
/*     */     
/* 182 */     this.mPdfViewCtrl.setStylusScaleEnabled(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolManager.ToolModeBase getToolMode() {
/* 190 */     return ToolManager.ToolMode.INK_ERASER;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCreateAnnotType() {
/* 195 */     return 1003;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setListener(EraserListener listener) {
/* 204 */     this.mListener = listener;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setupAnnotProperty(AnnotStyle annotStyle) {
/* 209 */     float thickness = annotStyle.getThickness();
/* 210 */     this.mEraserHalfThickness = thickness / 2.0F;
/*     */     
/* 212 */     this.mEraserType = annotStyle.getEraserType();
/* 213 */     this.mInkEraserMode = annotStyle.getInkEraserMode();
/*     */     
/* 215 */     SharedPreferences settings = Tool.getToolPreferences(this.mPdfViewCtrl.getContext());
/* 216 */     SharedPreferences.Editor editor = settings.edit();
/* 217 */     editor.putFloat(getThicknessKey(getCreateAnnotType()), thickness);
/* 218 */     editor.putString(getEraserTypeKey(getCreateAnnotType()), this.mEraserType.name());
/* 219 */     editor.putString(getInkEraserModeKey(getCreateAnnotType()), this.mInkEraserMode.name());
/* 220 */     editor.apply();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDown(MotionEvent e) {
/* 231 */     this.mPt1.x = e.getX() + this.mPdfViewCtrl.getScrollX();
/* 232 */     this.mPt1.y = e.getY() + this.mPdfViewCtrl.getScrollY();
/*     */ 
/*     */     
/* 235 */     this.mPt2.set(this.mPt1);
/*     */ 
/*     */ 
/*     */     
/* 239 */     this.mDownPageNum = this.mPdfViewCtrl.getPageNumberFromScreenPt(e.getX(), e.getY());
/* 240 */     if (this.mDownPageNum < 1) {
/* 241 */       this.mIsStartPointOutsidePage = true;
/* 242 */       this.mDownPageNum = this.mPdfViewCtrl.getCurrentPage();
/*     */     } else {
/* 244 */       this.mIsStartPointOutsidePage = false;
/*     */     } 
/* 246 */     if (this.mDownPageNum >= 1) {
/* 247 */       this.mPageCropOnClientF = Utils.buildPageBoundBoxOnClient(this.mPdfViewCtrl, this.mDownPageNum);
/*     */     }
/* 249 */     this.mThickness = this.mEraserHalfThickness * 2.0F;
/* 250 */     float zoom = (float)this.mPdfViewCtrl.getZoom();
/* 251 */     this.mThicknessDraw = this.mThickness * zoom;
/* 252 */     this.mPaint.setStrokeWidth(this.mThicknessDraw);
/* 253 */     this.mPaint.setColor(-3355444);
/* 254 */     this.mPaint.setAlpha(178);
/*     */ 
/*     */     
/* 257 */     if (this.mPageCropOnClientF != null && (
/* 258 */       this.mPt1.x < this.mPageCropOnClientF.left || this.mPt1.x > this.mPageCropOnClientF.right || this.mPt1.y < this.mPageCropOnClientF.top || this.mPt1.y > this.mPageCropOnClientF.bottom)) {
/*     */ 
/*     */ 
/*     */       
/* 262 */       this.mInitializedOnMove = false;
/* 263 */       setNextToolModeHelper(ToolManager.ToolMode.PAN);
/* 264 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 268 */     this.mInitializedOnMove = true;
/*     */     
/* 270 */     this.mPt1BBox.set(this.mPt1);
/* 271 */     this.mPt2BBox.set(this.mPt2);
/*     */     
/* 273 */     this.mPath.moveTo(this.mPt1.x, this.mPt1.y);
/* 274 */     this.mX = this.mPt1.x;
/* 275 */     this.mY = this.mPt1.y;
/* 276 */     this.mStrokePoints = new LinkedList<>();
/* 277 */     this.mStrokePoints.add(new PointF(this.mPt1.x, this.mPt1.y));
/*     */ 
/*     */     
/* 280 */     float sx = this.mPdfViewCtrl.getScrollX();
/* 281 */     float sy = this.mPdfViewCtrl.getScrollY();
/*     */     
/* 283 */     this.mCurrentPt.x = (this.mPt1.x - sx);
/* 284 */     this.mCurrentPt.y = (this.mPt1.y - sy);
/* 285 */     this.mPrevPt.x = (this.mPt1.x - sx);
/* 286 */     this.mPrevPt.y = (this.mPt1.y - sy);
/*     */     
/*     */     try {
/* 289 */       Page page = this.mPdfViewCtrl.getDoc().getPage(this.mDownPageNum);
/* 290 */       int annot_num = page.getNumAnnots();
/* 291 */       for (int i = annot_num - 1; i >= 0; i--) {
/* 292 */         Annot annot = page.getAnnot(i);
/* 293 */         if (annot.isValid())
/*     */         {
/* 295 */           if (annot.getType() == 14) {
/* 296 */             Ink ink = new Ink(annot);
/* 297 */             if (PressureInkUtils.isPressureSensitive((Annot)ink))
/* 298 */               this.mPressureInkList.add(ink); 
/*     */           } 
/*     */         }
/*     */       } 
/* 302 */     } catch (PDFNetException ex) {
/* 303 */       ex.printStackTrace();
/*     */     } 
/*     */     
/* 306 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onMove(MotionEvent e1, MotionEvent e2, float x_dist, float y_dist) {
/* 314 */     super.onMove(e1, e2, x_dist, y_dist);
/*     */     
/* 316 */     if (this.mAllowTwoFingerScroll) {
/* 317 */       this.mPath.reset();
/* 318 */       return false;
/*     */     } 
/*     */     
/* 321 */     if (!this.mInitializedOnMove) {
/* 322 */       return false;
/*     */     }
/*     */     
/* 325 */     float x = e2.getX() + this.mPdfViewCtrl.getScrollX();
/* 326 */     float y = e2.getY() + this.mPdfViewCtrl.getScrollY();
/*     */ 
/*     */     
/* 329 */     if (this.mPageCropOnClientF != null) {
/* 330 */       if (x < this.mPageCropOnClientF.left) {
/* 331 */         x = this.mPageCropOnClientF.left;
/* 332 */       } else if (x > this.mPageCropOnClientF.right) {
/* 333 */         x = this.mPageCropOnClientF.right;
/*     */       } 
/* 335 */       if (y < this.mPageCropOnClientF.top) {
/* 336 */         y = this.mPageCropOnClientF.top;
/* 337 */       } else if (y > this.mPageCropOnClientF.bottom) {
/* 338 */         y = this.mPageCropOnClientF.bottom;
/*     */       } 
/*     */     } 
/*     */     
/* 342 */     float dx = Math.abs(x - this.mX);
/* 343 */     float dy = Math.abs(y - this.mY);
/* 344 */     if (this.mEraserType == EraserType.ANNOTATION_ERASER || this.mEraserType == EraserType.HYBRID_ERASER || dx >= 1.0F || dy >= 1.0F) {
/*     */ 
/*     */       
/* 347 */       this.mPath.quadTo(this.mX, this.mY, (x + this.mX) / 2.0F, (y + this.mY) / 2.0F);
/* 348 */       this.mX = x;
/* 349 */       this.mY = y;
/*     */       
/* 351 */       this.mStrokePoints.add(new PointF(x, y));
/*     */       
/* 353 */       float sx = this.mPdfViewCtrl.getScrollX();
/* 354 */       float sy = this.mPdfViewCtrl.getScrollY();
/*     */       
/* 356 */       this.mCurrentPt.x = (x - sx);
/* 357 */       this.mCurrentPt.y = (y - sy);
/*     */       
/* 359 */       this.mPt1.x = Math.min(Math.min(x, this.mPt1.x), this.mPt1.x);
/* 360 */       this.mPt1.y = Math.min(Math.min(y, this.mPt1.y), this.mPt1.y);
/* 361 */       this.mPt2.x = Math.max(Math.max(x, this.mPt2.x), this.mPt2.x);
/* 362 */       this.mPt2.y = Math.max(Math.max(y, this.mPt2.y), this.mPt2.y);
/*     */       
/* 364 */       this.mPt1BBox.x = Math.min(Math.min(this.mPt1.x, this.mPt1BBox.x), this.mPt1BBox.x);
/* 365 */       this.mPt1BBox.y = Math.min(Math.min(this.mPt1.y, this.mPt1BBox.y), this.mPt1BBox.y);
/* 366 */       this.mPt2BBox.x = Math.max(Math.max(this.mPt2.x, this.mPt2BBox.x), this.mPt2BBox.x);
/* 367 */       this.mPt2BBox.y = Math.max(Math.max(this.mPt2.y, this.mPt2BBox.y), this.mPt2BBox.y);
/*     */       
/* 369 */       float min_x = this.mPt1BBox.x - this.mThicknessDraw;
/* 370 */       float max_x = this.mPt2BBox.x + this.mThicknessDraw;
/* 371 */       float min_y = this.mPt1BBox.y - this.mThicknessDraw;
/* 372 */       float max_y = this.mPt2BBox.y + this.mThicknessDraw;
/*     */       
/* 374 */       this.mPdfViewCtrl.invalidate((int)min_x, (int)min_y, (int)Math.ceil(max_x), (int)Math.ceil(max_y));
/*     */ 
/*     */       
/* 377 */       double[] pt1points = this.mPdfViewCtrl.convScreenPtToPagePt(this.mPrevPt.x, this.mPrevPt.y, this.mDownPageNum);
/* 378 */       double[] pt2points = this.mPdfViewCtrl.convScreenPtToPagePt(this.mCurrentPt.x, this.mCurrentPt.y, this.mDownPageNum);
/* 379 */       Point pdfPt1 = new Point(pt1points[0], pt1points[1]);
/* 380 */       Point pdfPt2 = new Point(pt2points[0], pt2points[1]);
/*     */ 
/*     */       
/* 383 */       boolean shouldUnlock = false;
/*     */       try {
/* 385 */         this.mPdfViewCtrl.docLock(true);
/* 386 */         shouldUnlock = true;
/* 387 */         Page page = this.mPdfViewCtrl.getDoc().getPage(this.mDownPageNum);
/* 388 */         switch (this.mEraserType) {
/*     */           case PIXEL:
/* 390 */             handleMoveInk(page, pdfPt1, pdfPt2);
/*     */             break;
/*     */           case STROKE:
/* 393 */             handleMoveAnnot(false);
/*     */             break;
/*     */           case null:
/* 396 */             handleMoveInk(page, pdfPt1, pdfPt2);
/* 397 */             handleMoveAnnot(true);
/*     */             break;
/*     */         } 
/* 400 */       } catch (Exception e) {
/* 401 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */       } finally {
/* 403 */         if (shouldUnlock) {
/* 404 */           this.mPdfViewCtrl.docUnlock();
/*     */         }
/* 406 */         this.mPrevPt.x = this.mCurrentPt.x;
/* 407 */         this.mPrevPt.y = this.mCurrentPt.y;
/*     */       } 
/*     */     } 
/*     */     
/* 411 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onUp(MotionEvent e, PDFViewCtrl.PriorEventMode priorEventMode) {
/* 420 */     if (this.mAllowTwoFingerScroll) {
/* 421 */       doneTwoFingerScrolling();
/* 422 */       return false;
/*     */     } 
/*     */     
/* 425 */     if (priorEventMode == PDFViewCtrl.PriorEventMode.PAGE_SLIDING) {
/* 426 */       return false;
/*     */     }
/*     */     
/* 429 */     if (!this.mInitializedOnMove) {
/* 430 */       return false;
/*     */     }
/*     */     
/* 433 */     this.mPaths.add(this.mPath);
/* 434 */     this.mPath = new Path();
/*     */     
/* 436 */     boolean shouldUnlock = false;
/*     */     try {
/* 438 */       safeSetNextToolMode();
/*     */       
/* 440 */       this.mPdfViewCtrl.docLock(true);
/* 441 */       shouldUnlock = true;
/*     */       
/* 443 */       if (this.mStrokePoints.size() == 1) {
/*     */         
/* 445 */         double[] pt1points = this.mPdfViewCtrl.convScreenPtToPagePt(this.mPrevPt.x, this.mPrevPt.y, this.mDownPageNum);
/* 446 */         Point pdfPt1 = new Point(pt1points[0], pt1points[1]);
/*     */         
/*     */         try {
/* 449 */           switch (this.mEraserType) {
/*     */             case PIXEL:
/* 451 */               handleUpInk(pdfPt1);
/*     */               break;
/*     */             case STROKE:
/* 454 */               handleUpAnnot(false);
/*     */               break;
/*     */             case null:
/* 457 */               handleUpInk(pdfPt1);
/* 458 */               handleUpAnnot(true);
/*     */               break;
/*     */           } 
/* 461 */         } catch (Exception exception) {}
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 466 */       if (commitAnnotation())
/*     */       {
/* 468 */         addOldTools();
/*     */       }
/*     */       
/* 471 */       if (!this.mForceSameNextToolMode) {
/* 472 */         this.mPaint.setStrokeJoin(this.oldStrokeJoin);
/* 473 */         this.mPaint.setStrokeCap(this.oldStrokeCap);
/*     */       } 
/* 475 */     } catch (Exception ex) {
/* 476 */       this.mNextToolMode = ToolManager.ToolMode.PAN;
/* 477 */       AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*     */     } finally {
/* 479 */       if (shouldUnlock) {
/* 480 */         this.mPdfViewCtrl.docUnlock();
/*     */       }
/* 482 */       this.mStrokePoints.clear();
/* 483 */       this.mStrokes.clear();
/* 484 */       this.mErasedInks.clear();
/* 485 */       this.mAnnotList.clear();
/* 486 */       this.mEraseStroke.clear();
/* 487 */       this.mPressureInkList.clear();
/*     */     } 
/*     */     
/* 490 */     if (this.mDoUpdate && 
/* 491 */       this.mListener != null) {
/* 492 */       this.mListener.strokeErased();
/*     */     }
/*     */ 
/*     */     
/* 496 */     if (this.mForceSameNextToolMode) {
/* 497 */       erasePaths();
/*     */     }
/*     */     
/* 500 */     this.mDoUpdate = false;
/*     */     
/* 502 */     return skipOnUpPriorEvent(priorEventMode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Annot createMarkup(@NonNull PDFDoc doc, Rect bbox) throws PDFNetException {
/* 510 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doneTwoFingerScrolling() {
/* 518 */     super.doneTwoFingerScrolling();
/*     */     
/* 520 */     erasePaths();
/*     */   }
/*     */   
/*     */   private void safeSetNextToolMode() {
/* 524 */     if (this.mForceSameNextToolMode) {
/* 525 */       if (this.mCurrentDefaultToolMode == ToolManager.ToolMode.INK_CREATE) {
/* 526 */         this.mNextToolMode = this.mCurrentDefaultToolMode;
/*     */       } else {
/* 528 */         this.mNextToolMode = getToolMode();
/*     */       } 
/*     */     } else {
/* 531 */       this.mNextToolMode = ToolManager.ToolMode.PAN;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void handleMoveInk(Page page, Point pdfPt1, Point pdfPt2) throws PDFNetException {
/* 536 */     int annot_num = page.getNumAnnots();
/* 537 */     for (int i = annot_num - 1; i >= 0; i--) {
/* 538 */       Annot annot = page.getAnnot(i);
/* 539 */       if (annot.isValid())
/*     */       {
/* 541 */         if (annot.getType() == 14) {
/* 542 */           Ink ink = new Ink(annot);
/* 543 */           if (PressureInkUtils.isPressureSensitive((Annot)ink)) {
/* 544 */             this.mEraseStroke.add(new PointF((float)pdfPt1.x, (float)pdfPt1.y));
/*     */           } else {
/* 546 */             boolean erased = false;
/* 547 */             Rect updateRegion = ink.getContentRect();
/* 548 */             switch (this.mInkEraserMode) {
/*     */               case PIXEL:
/* 550 */                 erased = ink.erase(pdfPt1, pdfPt2, this.mEraserHalfThickness);
/*     */                 break;
/*     */               case STROKE:
/* 553 */                 erased = ink.erasePaths(pdfPt1, pdfPt2, this.mEraserHalfThickness); break;
/*     */             } 
/* 555 */             if (erased && 
/* 556 */               !this.mErasedInks.containsKey(ink)) {
/* 557 */               this.mErasedInks.put(ink, updateRegion);
/* 558 */               this.mDoUpdate = true;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void handleUpInk(Point pdfPt1) throws PDFNetException {
/* 567 */     Page page = this.mPdfViewCtrl.getDoc().getPage(this.mDownPageNum);
/* 568 */     int annot_num = page.getNumAnnots();
/* 569 */     for (int i = annot_num - 1; i >= 0; i--) {
/* 570 */       Annot annot = page.getAnnot(i);
/* 571 */       if (annot.isValid())
/*     */       {
/* 573 */         if (annot.getType() == 14) {
/* 574 */           Ink ink = new Ink(annot);
/* 575 */           if (PressureInkUtils.isPressureSensitive((Annot)ink)) {
/* 576 */             this.mEraseStroke.add(new PointF((float)pdfPt1.x, (float)pdfPt1.y));
/*     */           } else {
/* 578 */             boolean erased = false;
/* 579 */             Rect updateRegion = ink.getContentRect();
/* 580 */             switch (this.mInkEraserMode) {
/*     */               case PIXEL:
/* 582 */                 erased = ink.erase(pdfPt1, pdfPt1, this.mEraserHalfThickness);
/*     */                 break;
/*     */               case STROKE:
/* 585 */                 erased = ink.erasePaths(pdfPt1, pdfPt1, this.mEraserHalfThickness);
/*     */                 break;
/*     */             } 
/* 588 */             if (erased && 
/* 589 */               !this.mErasedInks.containsKey(ink)) {
/* 590 */               this.mErasedInks.put(ink, updateRegion);
/* 591 */               this.mDoUpdate = true;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void handleMoveAnnot(boolean skipInk) throws PDFNetException {
/* 600 */     ArrayList<Annot> annots = this.mPdfViewCtrl.getAnnotationListAt((int)this.mPrevPt.x, (int)this.mPrevPt.y, (int)this.mCurrentPt.x, (int)this.mCurrentPt.y);
/*     */     
/* 602 */     for (Annot annot : annots) {
/* 603 */       int type = annot.getType();
/* 604 */       if (skipInk && 14 == type) {
/*     */         continue;
/*     */       }
/* 607 */       if (!this.mAnnotList.contains(annot)) {
/* 608 */         this.mAnnotList.add(annot);
/* 609 */         this.mDoUpdate = true;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void handleUpAnnot(boolean skipInk) throws PDFNetException {
/* 615 */     ArrayList<Annot> annots = this.mPdfViewCtrl.getAnnotationListAt((int)this.mPrevPt.x, (int)this.mPrevPt.y, (int)this.mPrevPt.x, (int)this.mPrevPt.y);
/*     */     
/* 617 */     for (Annot annot : annots) {
/* 618 */       int type = annot.getType();
/* 619 */       if (skipInk && 14 == type) {
/*     */         continue;
/*     */       }
/* 622 */       if (!this.mAnnotList.contains(annot)) {
/* 623 */         this.mAnnotList.add(annot);
/* 624 */         this.mDoUpdate = true;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void erasePaths() {
/* 633 */     this.mPaths.clear();
/* 634 */     this.mPath.reset();
/*     */     
/* 636 */     float min_x = this.mPt1BBox.x - this.mThicknessDraw;
/* 637 */     float max_x = this.mPt2BBox.x + this.mThicknessDraw;
/* 638 */     float min_y = this.mPt1BBox.y - this.mThicknessDraw;
/* 639 */     float max_y = this.mPt2BBox.y + this.mThicknessDraw;
/*     */     
/* 641 */     this.mPdfViewCtrl.invalidate((int)min_x, (int)min_y, (int)Math.ceil(max_x), (int)Math.ceil(max_y));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean commitAnnotation() {
/* 650 */     boolean needsRender = false; try {
/*     */       boolean inkResult, annotResult;
/* 652 */       Page page = this.mPdfViewCtrl.getDoc().getPage(this.mDownPageNum);
/* 653 */       switch (this.mEraserType) {
/*     */         case PIXEL:
/* 655 */           needsRender = commitInk(page);
/*     */           break;
/*     */         case STROKE:
/* 658 */           needsRender = commitAnnot(page);
/*     */           break;
/*     */         case null:
/* 661 */           inkResult = commitInk(page);
/* 662 */           annotResult = commitAnnot(page);
/* 663 */           needsRender = (inkResult || annotResult);
/*     */           break;
/*     */       } 
/* 666 */     } catch (Exception e) {
/* 667 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */     } 
/* 669 */     return needsRender;
/*     */   }
/*     */   
/*     */   private boolean commitInk(Page page) throws PDFNetException {
/* 673 */     boolean needsRender = false;
/* 674 */     this.mAnnotPageNum = this.mDownPageNum;
/* 675 */     HashMap<Annot, Integer> inksToRemove = new HashMap<>();
/* 676 */     HashMap<Annot, Integer> inksToModify = new HashMap<>();
/* 677 */     HashMap<Annot, PressureInkUtils.EraserData> inkEraserData = new HashMap<>();
/*     */ 
/*     */     
/* 680 */     for (Ink ink : this.mErasedInks.keySet()) {
/* 681 */       if (ink.getPathCount() != 0) {
/* 682 */         boolean shouldRemove = true;
/* 683 */         for (int i = 0; i < ink.getPathCount(); i++) {
/* 684 */           if (ink.getPointCount(i) > 0) {
/* 685 */             shouldRemove = false;
/*     */             break;
/*     */           } 
/*     */         } 
/* 689 */         if (shouldRemove) {
/* 690 */           inksToRemove.put(ink, Integer.valueOf(this.mAnnotPageNum)); continue;
/*     */         } 
/* 692 */         inksToModify.put(ink, Integer.valueOf(this.mAnnotPageNum));
/*     */         continue;
/*     */       } 
/* 695 */       inksToRemove.put(ink, Integer.valueOf(this.mAnnotPageNum));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 700 */     if (!this.mEraseStroke.isEmpty()) {
/* 701 */       for (Ink ink : this.mPressureInkList) {
/*     */         PressureInkUtils.EraserData eraserData;
/* 703 */         Rect updateRegion = ink.getRect();
/* 704 */         switch (this.mInkEraserMode) {
/*     */           case PIXEL:
/* 706 */             eraserData = PressureInkUtils.erasePoints(this.mPdfViewCtrl, ink, this.mEraseStroke, this.mEraserHalfThickness);
/*     */             break;
/*     */           
/*     */           default:
/* 710 */             eraserData = PressureInkUtils.erasePressureStrokes(this.mPdfViewCtrl, ink, this.mEraseStroke, this.mEraserHalfThickness);
/*     */             break;
/*     */         } 
/* 713 */         if (eraserData.hasErased) {
/* 714 */           this.mErasedInks.put(ink, updateRegion);
/* 715 */           this.mDoUpdate = true;
/* 716 */           boolean isRemoved = true;
/* 717 */           if (!eraserData.newThicknessList.isEmpty()) {
/* 718 */             for (List<Float> thickness : (Iterable<List<Float>>)eraserData.newThicknessList) {
/* 719 */               if (!thickness.isEmpty()) {
/* 720 */                 isRemoved = false;
/*     */                 
/*     */                 break;
/*     */               } 
/*     */             } 
/*     */           }
/* 726 */           if (isRemoved) {
/* 727 */             inksToRemove.put(ink, Integer.valueOf(this.mAnnotPageNum)); continue;
/*     */           } 
/* 729 */           inksToModify.put(ink, Integer.valueOf(this.mAnnotPageNum));
/* 730 */           inkEraserData.put(ink, eraserData);
/*     */         } 
/*     */       } 
/*     */       
/* 734 */       this.mEraseStroke.clear();
/* 735 */       this.mPressureInkList.clear();
/*     */     } 
/*     */ 
/*     */     
/* 739 */     if (!inksToRemove.isEmpty()) {
/* 740 */       raiseAnnotationPreRemoveEvent(inksToRemove);
/* 741 */       for (Annot ink : inksToRemove.keySet()) {
/* 742 */         page.annotRemove(ink);
/* 743 */         if (ink instanceof Ink) {
/* 744 */           Rect rect = this.mErasedInks.get(ink);
/* 745 */           if (rect != null) {
/* 746 */             PDFViewCtrUpdateRect(rect);
/*     */           }
/*     */         } 
/* 749 */         needsRender = true;
/*     */       } 
/* 751 */       raiseAnnotationRemovedEvent(inksToRemove);
/*     */     } 
/*     */ 
/*     */     
/* 755 */     if (!inksToModify.isEmpty()) {
/* 756 */       raiseAnnotationPreModifyEvent(inksToModify);
/* 757 */       for (Annot ink : inksToModify.keySet()) {
/* 758 */         if (inkEraserData.containsKey(ink) && PressureInkUtils.isPressureSensitive(ink)) {
/* 759 */           PressureInkUtils.EraserData eraserData = inkEraserData.get(ink);
/* 760 */           PressureInkUtils.clearThicknessList(ink);
/*     */           
/* 762 */           PressureInkUtils.setThicknessList((Ink)ink, eraserData.newThicknessList);
/* 763 */           PressureInkUtils.setInkList((Ink)ink, eraserData.newStrokeList);
/*     */ 
/*     */           
/* 766 */           Annot.BorderStyle borderStyle = ink.getBorderStyle();
/* 767 */           float width = (float)borderStyle.getWidth();
/* 768 */           Rect rect = PressureInkUtils.getInkItemBBox(eraserData.newStrokeList, width, 0, null, false);
/* 769 */           ink.setRect(rect);
/*     */ 
/*     */           
/* 772 */           PressureInkUtils.refreshCustomInkAppearanceForExistingAnnot(ink);
/*     */         } else {
/* 774 */           ink.refreshAppearance();
/*     */         } 
/* 776 */         if (ink instanceof Ink) {
/* 777 */           Rect rect = this.mErasedInks.get(ink);
/* 778 */           if (rect != null) {
/* 779 */             PDFViewCtrUpdateRect(rect);
/*     */           }
/*     */         } 
/* 782 */         needsRender = true;
/*     */       } 
/* 784 */       raiseAnnotationModifiedEvent(inksToModify);
/*     */     } 
/*     */     
/* 787 */     return needsRender;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void PDFViewCtrUpdateRect(@NonNull Rect updateRegionInPagePt) throws PDFNetException {
/* 793 */     double[] screenPt1 = this.mPdfViewCtrl.convPagePtToScreenPt(updateRegionInPagePt.getX1(), updateRegionInPagePt.getY1(), this.mDownPageNum);
/* 794 */     double[] screenPt2 = this.mPdfViewCtrl.convPagePtToScreenPt(updateRegionInPagePt.getX2(), updateRegionInPagePt.getY2(), this.mDownPageNum);
/* 795 */     Rect screenBox = new Rect(screenPt1[0], screenPt1[1], screenPt2[0], screenPt2[1]);
/* 796 */     this.mPdfViewCtrl.update(screenBox);
/*     */   }
/*     */   
/*     */   private boolean commitAnnot(Page page) throws PDFNetException {
/* 800 */     boolean needsRender = false;
/* 801 */     for (Annot annot : this.mAnnotList) {
/* 802 */       raiseAnnotationPreRemoveEvent(annot, this.mAnnotPageNum);
/* 803 */       page.annotRemove(annot);
/* 804 */       if (this.mPdfViewCtrl.isAnnotationLayerEnabled()) {
/* 805 */         this.mPdfViewCtrl.update(annot, this.mDownPageNum);
/*     */       } else {
/* 807 */         Rect bbox = annot.getRect();
/* 808 */         double[] screenPt1 = this.mPdfViewCtrl.convPagePtToScreenPt(bbox.getX1(), bbox.getY1(), this.mDownPageNum);
/* 809 */         double[] screenPt2 = this.mPdfViewCtrl.convPagePtToScreenPt(bbox.getX2(), bbox.getY2(), this.mDownPageNum);
/* 810 */         Rect screenBox = new Rect(screenPt1[0], screenPt1[1], screenPt2[0], screenPt2[1]);
/* 811 */         this.mPdfViewCtrl.update(screenBox);
/*     */       } 
/* 813 */       needsRender = true;
/* 814 */       raiseAnnotationRemovedEvent(annot, this.mAnnotPageNum);
/*     */     } 
/* 816 */     return needsRender;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDraw(Canvas canvas, Matrix tfm) {
/* 824 */     if (this.mAllowTwoFingerScroll || this.mIsStartPointOutsidePage) {
/*     */       return;
/*     */     }
/* 827 */     for (Path p : this.mPaths) {
/* 828 */       canvas.drawPath(p, this.mPaint);
/*     */     }
/* 830 */     canvas.drawPath(this.mPath, this.mPaint);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\Eraser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */