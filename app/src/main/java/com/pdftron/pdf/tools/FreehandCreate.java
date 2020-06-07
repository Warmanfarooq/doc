/*      */ package com.pdftron.pdf.tools;
/*      */ 
/*      */ import android.content.Context;
/*      */ import android.content.SharedPreferences;
/*      */ import android.graphics.Canvas;
/*      */ import android.graphics.Matrix;
/*      */ import android.graphics.Paint;
/*      */ import android.graphics.PointF;
/*      */ import android.graphics.Rect;
/*      */ import android.util.DisplayMetrics;
/*      */ import android.util.Log;
/*      */ import android.view.MotionEvent;
/*      */ import androidx.annotation.Keep;
/*      */ import androidx.annotation.NonNull;
/*      */ import androidx.annotation.Nullable;
/*      */ import androidx.preference.PreferenceManager;
/*      */ import com.pdftron.common.PDFNetException;
/*      */ import com.pdftron.pdf.Annot;
/*      */ import com.pdftron.pdf.ColorPt;
/*      */ import com.pdftron.pdf.PDFDoc;
/*      */ import com.pdftron.pdf.PDFViewCtrl;
/*      */ import com.pdftron.pdf.Page;
/*      */ import com.pdftron.pdf.Rect;
/*      */ import com.pdftron.pdf.annots.Ink;
/*      */ import com.pdftron.pdf.annots.Markup;
/*      */ import com.pdftron.pdf.config.ToolStyleConfig;
/*      */ import com.pdftron.pdf.controls.OnToolbarStateUpdateListener;
/*      */ import com.pdftron.pdf.model.AnnotStyle;
/*      */ import com.pdftron.pdf.model.ink.InkItem;
/*      */ import com.pdftron.pdf.model.ink.PressureInkItem;
/*      */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*      */ import com.pdftron.pdf.utils.PressureInkUtils;
/*      */ import com.pdftron.pdf.utils.Utils;
/*      */ import com.pdftron.pdf.utils.ViewerUtils;
/*      */ import com.pdftron.sdf.Doc;
/*      */ import com.pdftron.sdf.Obj;
/*      */ import io.reactivex.Observable;
/*      */ import io.reactivex.ObservableSource;
/*      */ import io.reactivex.android.schedulers.AndroidSchedulers;
/*      */ import io.reactivex.disposables.Disposable;
/*      */ import io.reactivex.functions.Action;
/*      */ import io.reactivex.functions.Consumer;
/*      */ import io.reactivex.functions.Function;
/*      */ import io.reactivex.functions.Predicate;
/*      */ import io.reactivex.subjects.PublishSubject;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Stack;
/*      */ import java.util.concurrent.TimeUnit;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ @Keep
/*      */ public class FreehandCreate
/*      */   extends SimpleShapeCreate
/*      */ {
/*   66 */   private static final String TAG = FreehandCreate.class.getName();
/*      */   
/*      */   private static boolean sDebug = false;
/*      */   
/*      */   private static final double SAVE_INK_MARGIN = 200.0D;
/*      */   
/*      */   private boolean mMultiStrokeMode = true;
/*      */   
/*      */   private boolean mTimedModeEnabled = true;
/*      */   
/*      */   private boolean mIsPressureSensitive = false;
/*      */   
/*   78 */   private EraserState mEraserState = new EraserState();
/*      */   private boolean mEraserFromSpen = false;
/*      */   private boolean mEraserFromToolbar = false;
/*   81 */   private float mEraserThickness = 5.0F;
/*   82 */   private Eraser.InkEraserMode mInkEraserMode = Eraser.InkEraserMode.PIXEL;
/*      */ 
/*      */   
/*   85 */   private CanvasStateManager mCanvasStateManager = new CanvasStateManager();
/*   86 */   private float mPrevX = Float.MAX_VALUE;
/*   87 */   private float mPrevY = Float.MAX_VALUE;
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*   92 */   private InkState mStateToPush = null;
/*      */   
/*      */   private boolean mIsFromEditToolbar = false;
/*      */   
/*      */   private boolean mIsFirstPointNotOnPage;
/*      */   
/*      */   private boolean mFlinging;
/*      */   
/*      */   private boolean mIsScaleBegun;
/*      */   
/*      */   private boolean mIsStartPointOutsidePage;
/*      */   
/*      */   private boolean mIsEditingAnnot;
/*      */   
/*      */   private boolean mNeedNewAnnot = false;
/*      */   
/*      */   private Ink mEditInkAnnot;
/*      */   private int mEditInkPageNum;
/*      */   private boolean mScrollEventOccurred = true;
/*      */   private boolean mRegisteredDownEvent;
/*      */   private OnToolbarStateUpdateListener mOnToolbarStateUpdateListener;
/*      */   @Nullable
/*      */   private InkCommitter mInkCommitter;
/*  115 */   private float mSampleDelta = 10.0F;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FreehandCreate(@NonNull PDFViewCtrl ctrl) {
/*  121 */     super(ctrl);
/*  122 */     this.mNextToolMode = ToolManager.ToolMode.INK_CREATE;
/*      */     
/*  124 */     this.mPaint.setStrokeJoin(Paint.Join.ROUND);
/*  125 */     this.mPaint.setStrokeCap(Paint.Cap.ROUND);
/*      */     
/*  127 */     this.mPdfViewCtrl.setStylusScaleEnabled(false);
/*  128 */     addOldTools();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  137 */     DisplayMetrics metrics = this.mPdfViewCtrl.getResources().getDisplayMetrics();
/*  138 */     float xDel = 2.0F * metrics.xdpi * 1.0F / 25.4F;
/*  139 */     float yDel = 2.0F * metrics.ydpi * 1.0F / 25.4F;
/*  140 */     float del = Math.max(xDel, yDel);
/*  141 */     if (del > this.mSampleDelta) {
/*  142 */       this.mSampleDelta = del;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ToolManager.ToolModeBase getToolMode() {
/*  151 */     return ToolManager.ToolMode.INK_CREATE;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getCreateAnnotType() {
/*  156 */     return 14;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMultiStrokeMode(boolean mode) {
/*  166 */     this.mMultiStrokeMode = mode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFromEditToolbar(boolean fromEditToolbar) {
/*  176 */     this.mIsFromEditToolbar = fromEditToolbar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTimedModeEnabled(boolean enabled) {
/*  186 */     this.mTimedModeEnabled = enabled;
/*  187 */     if (enabled) {
/*  188 */       this.mMultiStrokeMode = true;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPressureSensitive(boolean isPressureSensitive) {
/*  202 */     if (this.mIsPressureSensitive != isPressureSensitive && 
/*  203 */       (this.mCanvasStateManager.getCurrentState()).currentInk != null) {
/*  204 */       this.mNeedNewAnnot = true;
/*      */     }
/*      */     
/*  207 */     this.mIsPressureSensitive = isPressureSensitive;
/*      */ 
/*      */     
/*  210 */     SharedPreferences settings = Tool.getToolPreferences(this.mPdfViewCtrl.getContext());
/*  211 */     SharedPreferences.Editor editor = settings.edit();
/*  212 */     editor.putBoolean(getPressureSensitiveKey(), this.mIsPressureSensitive);
/*  213 */     editor.apply();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOnToolbarStateUpdateListener(OnToolbarStateUpdateListener listener) {
/*  222 */     this.mOnToolbarStateUpdateListener = listener;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setupAnnotProperty(AnnotStyle annotStyle) {
/*  227 */     super.setupAnnotProperty(annotStyle);
/*      */     
/*  229 */     boolean isPressure = annotStyle.getPressureSensitive();
/*  230 */     setPressureSensitive(isPressure);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setupAnnotProperty(int color, float opacity, float thickness, int fillColor, String icon, String pdfTronFontName) {
/*  240 */     if (this.mStrokeColor != color || this.mOpacity != opacity || this.mThickness != thickness) {
/*  241 */       super.setupAnnotProperty(color, opacity, thickness, fillColor, icon, pdfTronFontName);
/*      */       
/*  243 */       float zoom = (float)this.mPdfViewCtrl.getZoom();
/*  244 */       this.mThicknessDraw = this.mThickness * zoom;
/*  245 */       this.mPaint.setStrokeWidth(this.mThicknessDraw);
/*  246 */       color = Utils.getPostProcessedColor(this.mPdfViewCtrl, this.mStrokeColor);
/*  247 */       this.mPaint.setColor(color);
/*  248 */       this.mPaint.setAlpha((int)(255.0F * this.mOpacity));
/*      */ 
/*      */       
/*  251 */       if ((this.mCanvasStateManager.getCurrentState()).currentInk != null) {
/*  252 */         this.mNeedNewAnnot = true;
/*      */       }
/*      */     } 
/*  255 */     this.mEraserFromToolbar = false;
/*  256 */     this.mEraserFromSpen = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setupEraserProperty(AnnotStyle annotStyle) {
/*  265 */     float thickness = annotStyle.getThickness();
/*  266 */     Eraser.InkEraserMode inkEraserMode = annotStyle.getInkEraserMode();
/*  267 */     SharedPreferences settings = Tool.getToolPreferences(this.mPdfViewCtrl.getContext());
/*  268 */     SharedPreferences.Editor editor = settings.edit();
/*  269 */     editor.putFloat(getThicknessKey(1003), thickness);
/*  270 */     editor.putString(getEraserTypeKey(1003), annotStyle.getEraserType().name());
/*  271 */     editor.putString(getInkEraserModeKey(1003), inkEraserMode.name());
/*  272 */     editor.apply();
/*      */     
/*  274 */     this.mEraserThickness = thickness;
/*  275 */     this.mEraserFromSpen = false;
/*  276 */     this.mEraserFromToolbar = true;
/*  277 */     this.mInkEraserMode = inkEraserMode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setInitInkItem(Annot inkAnnot, int pageNum) {
/*      */     try {
/*  288 */       if (inkAnnot == null || inkAnnot.getType() != 14) {
/*      */         return;
/*      */       }
/*  291 */     } catch (PDFNetException e) {
/*      */       return;
/*      */     } 
/*      */     
/*  295 */     boolean shouldUnlockRead = false;
/*      */     try {
/*  297 */       this.mPdfViewCtrl.docLockRead();
/*  298 */       shouldUnlockRead = true;
/*      */       
/*  300 */       Ink castedInkAnnot = new Ink(inkAnnot);
/*  301 */       this.mIsEditingAnnot = true;
/*      */       
/*  303 */       this.mEditInkAnnot = castedInkAnnot;
/*  304 */       this.mEditInkPageNum = pageNum;
/*      */ 
/*      */ 
/*      */       
/*  308 */       ColorPt colorPt = this.mEditInkAnnot.getColorAsRGB();
/*  309 */       int color = Utils.colorPt2color(colorPt);
/*      */ 
/*      */       
/*  312 */       Markup m = new Markup((Annot)this.mEditInkAnnot);
/*  313 */       float opacity = (float)m.getOpacity();
/*      */ 
/*      */       
/*  316 */       float thickness = (float)this.mEditInkAnnot.getBorderStyle().getWidth();
/*  317 */       setupAnnotProperty(color, opacity, thickness, color, (String)null, (String)null);
/*      */ 
/*      */       
/*  320 */       setupInitInkItem(this.mEditInkAnnot, pageNum);
/*      */       
/*  322 */       this.mPdfViewCtrl.hideAnnotation((Annot)this.mEditInkAnnot);
/*  323 */       this.mPdfViewCtrl.update((Annot)this.mEditInkAnnot, pageNum);
/*      */       
/*  325 */       this.mPdfViewCtrl.invalidate();
/*      */ 
/*      */       
/*  328 */       if (this.mOnToolbarStateUpdateListener != null) {
/*  329 */         this.mOnToolbarStateUpdateListener.onToolbarStateUpdated();
/*      */       }
/*  331 */     } catch (Exception e) {
/*  332 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } finally {
/*  334 */       if (shouldUnlockRead) {
/*  335 */         this.mPdfViewCtrl.docUnlockRead();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void setupInitInkItem(Ink ink, int pageNum) throws PDFNetException {
/*  343 */     this.mCanvasStateManager.initializeStateForEditing(this.mPdfViewCtrl, pageNum, this.mStrokeColor, this.mOpacity, this.mThickness, this.mIsStylus, ink);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  353 */     this.mInkCommitter = new InkCommitter(this.mIsEditingAnnot);
/*  354 */     this.mInkCommitter.initializeWithInkAnnot(this.mEditInkAnnot, this.mCanvasStateManager.getCurrentState());
/*      */ 
/*      */     
/*  357 */     updateEditToolbar();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onDown(MotionEvent e) {
/*  365 */     super.onDown(e);
/*  366 */     Context context = this.mPdfViewCtrl.getContext();
/*  367 */     SharedPreferences settings = Tool.getToolPreferences(context);
/*  368 */     this.mIsPressureSensitive = settings.getBoolean(getPressureSensitiveKey(), ToolStyleConfig.getInstance().getDefaultPressureSensitivity(context, getCreateAnnotType()));
/*  369 */     this.mScrollEventOccurred = false;
/*      */     
/*  371 */     if (this.mStylusUsed && !this.mIsStylus) {
/*  372 */       if (this.mIsFromEditToolbar) {
/*  373 */         return false;
/*      */       }
/*  375 */       this.mNextToolMode = ToolManager.ToolMode.PAN;
/*  376 */       return false;
/*      */     } 
/*      */ 
/*      */     
/*  380 */     this.mDownPageNum = this.mPdfViewCtrl.getPageNumberFromScreenPt(e.getX(), e.getY());
/*  381 */     this.mIsStartPointOutsidePage = (this.mDownPageNum < 1);
/*  382 */     if (this.mIsStartPointOutsidePage) {
/*  383 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  387 */     this.mEraserFromSpen = false;
/*      */ 
/*      */     
/*  390 */     if (Utils.isMarshmallow() && e
/*  391 */       .getToolType(0) == 2) {
/*  392 */       int state = e.getButtonState();
/*  393 */       if (state == 32)
/*      */       {
/*      */         
/*  396 */         if (this.mIsFromEditToolbar) {
/*      */           
/*  398 */           this.mEraserFromSpen = true;
/*  399 */           this.mEraserThickness = settings.getFloat(getThicknessKey(1003), this.mEraserThickness);
/*  400 */           this.mInkEraserMode = Eraser.InkEraserMode.valueOf(settings
/*  401 */               .getString(
/*  402 */                 getInkEraserModeKey(1003), Eraser.InkEraserMode.PIXEL
/*  403 */                 .name()));
/*      */         
/*      */         }
/*      */         else {
/*      */           
/*  408 */           this.mNextToolMode = ToolManager.ToolMode.INK_ERASER;
/*  409 */           setCurrentDefaultToolModeHelper(getToolMode());
/*  410 */           return false;
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/*  415 */     if (this.mAllowTwoFingerScroll) {
/*  416 */       this.mRegisteredDownEvent = false;
/*  417 */       return false;
/*      */     } 
/*  419 */     this.mRegisteredDownEvent = true;
/*      */ 
/*      */     
/*  422 */     if (this.mIsEditingAnnot && this.mDownPageNum != this.mEditInkPageNum) {
/*  423 */       return false;
/*      */     }
/*      */     
/*  426 */     if (this.mTimedModeEnabled && 
/*  427 */       this.mIsStylus && e.getToolType(0) != 2)
/*      */     {
/*  429 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  434 */     if (this.mPageCropOnClientF != null) {
/*  435 */       if (this.mPt1.x < this.mPageCropOnClientF.left || this.mPt1.x > this.mPageCropOnClientF.right || this.mPt1.y < this.mPageCropOnClientF.top || this.mPt1.y > this.mPageCropOnClientF.bottom) {
/*      */ 
/*      */ 
/*      */         
/*  439 */         if (!this.mMultiStrokeMode) {
/*  440 */           setNextToolModeHelper(ToolManager.ToolMode.ANNOT_EDIT);
/*      */         } else {
/*  442 */           this.mIsFirstPointNotOnPage = true;
/*      */         } 
/*  444 */         return false;
/*      */       } 
/*  446 */       this.mIsFirstPointNotOnPage = false;
/*      */     } 
/*      */ 
/*      */     
/*  450 */     if (!this.mIsFromEditToolbar && (this.mCanvasStateManager.getCurrentState()).currentInk != null) {
/*      */       
/*      */       try {
/*  453 */         Rect rect = this.mCanvasStateManager.getCurrentState().getBoundingBox();
/*  454 */         if (rect != null) {
/*  455 */           double[] pt1 = this.mPdfViewCtrl.convScreenPtToPagePt(e.getX(), e.getY(), (this.mCanvasStateManager.getCurrentState()).currentInk.pageNumber);
/*  456 */           rect.normalize();
/*  457 */           rect.inflate(200.0D);
/*  458 */           if (!rect.contains(pt1[0], pt1[1])) {
/*  459 */             this.mNeedNewAnnot = true;
/*      */           }
/*      */         } 
/*  462 */       } catch (Exception ex) {
/*  463 */         AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*      */       } 
/*      */     }
/*      */     
/*  467 */     if (isEraserEnabled()) {
/*  468 */       this.mEraserState = new EraserState();
/*  469 */       this.mEraserState.pushInk(this.mPdfViewCtrl, this.mDownPageNum, -3355444, 0.7F, this.mEraserThickness, this.mIsStylus);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  477 */       if ((this.mCanvasStateManager.getCurrentState()).currentInk != null) {
/*  478 */         this.mStateToPush = new InkState(this.mCanvasStateManager.getCurrentState());
/*      */       }
/*  480 */       this.mEraserState.addPoint(e.getX(), e.getY(), e.getPressure(), e.getAction());
/*      */     } else {
/*  482 */       InkState currentState = this.mCanvasStateManager.getCurrentState();
/*  483 */       if (currentState.currentInk == null || currentState.currentInk.pageNumber != this.mDownPageNum || this.mNeedNewAnnot) {
/*  484 */         currentState.pushInk(this.mPdfViewCtrl, this.mDownPageNum, this.mStrokeColor, this.mOpacity, this.mThickness, this.mIsStylus, this.mIsPressureSensitive);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  493 */         this.mNeedNewAnnot = false;
/*      */       } 
/*  495 */       this.mStateToPush = new InkState(this.mCanvasStateManager.getCurrentState());
/*  496 */       currentState.addPoint(e.getX(), e.getY(), e.getPressure(), e.getAction());
/*  497 */       this.mPrevX = e.getX();
/*  498 */       this.mPrevY = e.getY();
/*      */     } 
/*      */ 
/*      */     
/*  502 */     if (this.mInkCommitter == null) {
/*  503 */       this.mInkCommitter = new InkCommitter(this.mIsEditingAnnot);
/*      */     } else {
/*  505 */       this.mInkCommitter.restartIfStopped();
/*      */     } 
/*      */     
/*  508 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onMove(MotionEvent e1, MotionEvent e2, float x_dist, float y_dist) {
/*  516 */     super.onMove(e1, e2, x_dist, y_dist);
/*      */     
/*  518 */     if (this.mIsStartPointOutsidePage) {
/*  519 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  523 */     if (!this.mRegisteredDownEvent) {
/*  524 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  528 */     if (this.mAllowTwoFingerScroll) {
/*  529 */       return false;
/*      */     }
/*      */     
/*  532 */     if (this.mAllowOneFingerScrollWithStylus) {
/*  533 */       return false;
/*      */     }
/*      */     
/*  536 */     if (this.mIsEditingAnnot && this.mDownPageNum != this.mEditInkPageNum) {
/*  537 */       return false;
/*      */     }
/*      */     
/*  540 */     if (isEraserEnabled()) {
/*  541 */       processOnMoveHistoricalMotionPoints(e2, true);
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  546 */       InkState currentState = this.mCanvasStateManager.getCurrentState();
/*  547 */       if (currentState.currentInk == null) {
/*  548 */         AnalyticsHandlerAdapter.getInstance().sendException(new Exception("Current ink item is null"));
/*  549 */         return false;
/*      */       } 
/*  551 */       if (this.mMultiStrokeMode && (this.mIsFirstPointNotOnPage || currentState.currentInk.pageNumber != this.mDownPageNum)) {
/*  552 */         return false;
/*      */       }
/*      */       
/*  555 */       processOnMoveHistoricalMotionPoints(e2, false);
/*      */     } 
/*      */     
/*  558 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onUp(MotionEvent e, PDFViewCtrl.PriorEventMode priorEventMode) {
/*  567 */     if (this.mIsStartPointOutsidePage) {
/*  568 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  572 */     if (!this.mRegisteredDownEvent) {
/*  573 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  577 */     if (priorEventMode == PDFViewCtrl.PriorEventMode.FLING) {
/*  578 */       this.mFlinging = true;
/*      */     }
/*      */ 
/*      */     
/*  582 */     if (this.mAllowTwoFingerScroll) {
/*  583 */       doneTwoFingerScrolling();
/*  584 */       this.mScrollEventOccurred = true;
/*  585 */       return false;
/*      */     } 
/*      */     
/*  588 */     if (priorEventMode == PDFViewCtrl.PriorEventMode.PAGE_SLIDING) {
/*  589 */       return false;
/*      */     }
/*      */     
/*  592 */     if (this.mAllowOneFingerScrollWithStylus) {
/*  593 */       doneOneFingerScrollingWithStylus();
/*  594 */       this.mScrollEventOccurred = true;
/*  595 */       return false;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  602 */     if (this.mScrollEventOccurred) {
/*  603 */       this.mScrollEventOccurred = false;
/*  604 */       return false;
/*      */     } 
/*      */     
/*  607 */     if (this.mIsStylus && e.getToolType(0) != 2) {
/*  608 */       return false;
/*      */     }
/*      */     
/*  611 */     if (this.mStylusUsed && e.getToolType(0) != 2) {
/*  612 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  616 */     if (this.mAnnotPushedBack) {
/*  617 */       this.mAnnotPushedBack = false;
/*  618 */       return false;
/*      */     } 
/*      */     
/*  621 */     if (this.mIsEditingAnnot && this.mDownPageNum != this.mEditInkPageNum) {
/*  622 */       return false;
/*      */     }
/*  624 */     InkState inkState = this.mCanvasStateManager.getCurrentState();
/*  625 */     if (isEraserEnabled()) {
/*  626 */       float x = e.getX();
/*  627 */       float y = e.getY();
/*      */       
/*  629 */       processEraserMotionPoint(x, y, e.getAction());
/*  630 */       if (this.mEraserState != null)
/*      */       {
/*  632 */         InkItem currentInk = inkState.currentInk;
/*  633 */         if (currentInk != null) {
/*  634 */           processEraser(currentInk);
/*      */         }
/*  636 */         this.mEraserState = null;
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  641 */       if (inkState.currentInk == null) {
/*  642 */         AnalyticsHandlerAdapter.getInstance().sendException(new Exception("Current ink item is null"));
/*  643 */         return false;
/*      */       } 
/*  645 */       if ((this.mMultiStrokeMode && this.mIsFirstPointNotOnPage) || inkState.currentInk.pageNumber != this.mDownPageNum)
/*      */       {
/*  647 */         return false;
/*      */       }
/*      */       
/*  650 */       float x = e.getX();
/*  651 */       float y = e.getY();
/*      */       
/*  653 */       processMotionPoint(x, y, e.getPressure(), e.getAction());
/*      */     } 
/*      */     
/*  656 */     if (this.mStateToPush != null) {
/*  657 */       this.mCanvasStateManager.saveState(this.mStateToPush);
/*  658 */       this.mStateToPush = null;
/*      */     } 
/*  660 */     this.mAnnotPushedBack = true;
/*  661 */     updateEditToolbar();
/*      */     
/*  663 */     this.mPdfViewCtrl.invalidate();
/*      */     
/*  665 */     if (this.mIsStylus) {
/*  666 */       raiseStylusUsedFirstTimeEvent();
/*      */     }
/*      */     
/*  669 */     if (!this.mMultiStrokeMode) {
/*  670 */       if (this.mInkCommitter != null) {
/*  671 */         commitAnnotation();
/*  672 */         this.mNeedNewAnnot = true;
/*      */       } 
/*  674 */       setNextToolModeHelper();
/*  675 */       setCurrentDefaultToolModeHelper(getToolMode());
/*      */     } 
/*      */     
/*  678 */     return skipOnUpPriorEvent(priorEventMode);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void processOnMoveHistoricalMotionPoints(MotionEvent ev, boolean isEraser) {
/*  684 */     if (isEraser) {
/*      */       
/*  686 */       processEraserMotionPoint(ev.getX(), ev.getY(), ev.getAction());
/*      */     } else {
/*  688 */       float eventX = ev.getX();
/*  689 */       float eventY = ev.getY();
/*  690 */       float eventPressure = ev.getPressure();
/*  691 */       int historySize = ev.getHistorySize();
/*  692 */       int pointerCount = ev.getPointerCount();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  698 */       for (int h = 0; h < historySize; h++) {
/*  699 */         if (pointerCount >= 1) {
/*      */           
/*  701 */           float historicalX = ev.getHistoricalX(0, h);
/*  702 */           float historicalY = ev.getHistoricalY(0, h);
/*  703 */           float historicalPressure = ev.getHistoricalPressure(0, h);
/*      */           
/*  705 */           if (distance(historicalX, historicalY, this.mPrevX, this.mPrevY) > this.mSampleDelta && 
/*  706 */             distance(historicalX, historicalY, eventX, eventY) > this.mSampleDelta) {
/*  707 */             processMotionPoint(historicalX, historicalY, historicalPressure, ev.getAction());
/*      */           }
/*      */         } 
/*      */       } 
/*  711 */       processMotionPoint(eventX, eventY, eventPressure, ev.getAction());
/*      */     } 
/*      */   }
/*      */   
/*      */   private float distance(float x1, float y1, float x2, float y2) {
/*  716 */     float dx = x2 - x1;
/*  717 */     float dy = y2 - y1;
/*  718 */     return (float)Math.sqrt((dx * dx + dy * dy));
/*      */   }
/*      */   
/*      */   private void processEraserMotionPoint(float x, float y, int action) {
/*  722 */     if (this.mEraserState != null) {
/*  723 */       this.mEraserState.addPoint(x, y, -1.0F, action);
/*      */     } else {
/*  725 */       AnalyticsHandlerAdapter.getInstance().sendException(new RuntimeException("Eraser state is not initialized"));
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void processMotionPoint(float x, float y, float pressure, int action) {
/*  730 */     int sx = this.mPdfViewCtrl.getScrollX();
/*  731 */     int sy = this.mPdfViewCtrl.getScrollY();
/*  732 */     float canvasX = x + sx;
/*  733 */     float canvasY = y + sy;
/*      */ 
/*      */     
/*  736 */     if (this.mPageCropOnClientF != null) {
/*  737 */       if (canvasX < this.mPageCropOnClientF.left) {
/*  738 */         canvasX = this.mPageCropOnClientF.left;
/*  739 */         x = canvasX - sx;
/*  740 */       } else if (canvasX > this.mPageCropOnClientF.right) {
/*  741 */         canvasX = this.mPageCropOnClientF.right;
/*  742 */         x = canvasX - sx;
/*      */       } 
/*  744 */       if (canvasY < this.mPageCropOnClientF.top) {
/*  745 */         canvasY = this.mPageCropOnClientF.top;
/*  746 */         y = canvasY - sy;
/*  747 */       } else if (canvasY > this.mPageCropOnClientF.bottom) {
/*  748 */         canvasY = this.mPageCropOnClientF.bottom;
/*  749 */         y = canvasY - sy;
/*      */       } 
/*      */     } 
/*      */     
/*  753 */     this.mCanvasStateManager.getCurrentState().addPoint(x, y, pressure, action);
/*      */     
/*  755 */     this.mPt1.x = Math.min(canvasX, this.mPt1.x);
/*  756 */     this.mPt1.y = Math.min(canvasY, this.mPt1.y);
/*  757 */     this.mPt2.x = Math.max(canvasX, this.mPt2.x);
/*  758 */     this.mPt2.y = Math.max(canvasY, this.mPt2.y);
/*      */     
/*  760 */     if (Utils.isLollipop()) {
/*  761 */       this.mPdfViewCtrl.invalidate();
/*      */     } else {
/*      */       
/*  764 */       float minX = Math.min(this.mPrevX, x) - 2.0F * this.mThicknessDraw + sx;
/*  765 */       float maxX = Math.max(this.mPrevX, x) + 2.0F * this.mThicknessDraw + sx;
/*  766 */       float minY = Math.min(this.mPrevY, y) - 2.0F * this.mThicknessDraw + sy;
/*  767 */       float maxY = Math.max(this.mPrevY, y) + 2.0F * this.mThicknessDraw + sy;
/*      */ 
/*      */       
/*  770 */       Rect boundingRect = new Rect((int)minX, (int)minY, (int)maxX, (int)maxY);
/*  771 */       Rect drawingRect = new Rect();
/*  772 */       this.mPdfViewCtrl.getDrawingRect(drawingRect);
/*  773 */       boolean hasIntersect = boundingRect.intersect(drawingRect);
/*      */       
/*  775 */       if (hasIntersect) {
/*  776 */         this.mPdfViewCtrl.invalidate(boundingRect);
/*      */       }
/*      */     } 
/*      */     
/*  780 */     this.mPrevX = x;
/*  781 */     this.mPrevY = y;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onFlingStop() {
/*  789 */     super.onFlingStop();
/*  790 */     if (this.mAllowOneFingerScrollWithStylus) {
/*  791 */       doneOneFingerScrollingWithStylus();
/*      */     }
/*  793 */     this.mFlinging = false;
/*  794 */     this.mIsScaleBegun = false;
/*  795 */     this.mPdfViewCtrl.invalidate();
/*  796 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onScaleBegin(float x, float y) {
/*  804 */     this.mIsScaleBegun = true;
/*  805 */     return super.onScaleBegin(x, y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Annot createMarkup(@NonNull PDFDoc doc, Rect bbox) throws PDFNetException {
/*  813 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean onDoubleTap(MotionEvent e) {
/*  818 */     onDoubleTapEvent(e);
/*  819 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onDoubleTapEvent(MotionEvent e) {
/*  829 */     if (e.getAction() == 2) {
/*  830 */       onMove(e, e, 0.0F, 0.0F);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  844 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDraw(Canvas canvas, Matrix tfm) {
/*  853 */     if ((this.mFlinging && this.mIsScaleBegun) || this.mPdfViewCtrl.isSlidingWhileZoomed()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  858 */     InkState inkState = this.mCanvasStateManager.getCurrentState();
/*  859 */     inkState.drawInk(canvas, this.mPdfViewCtrl);
/*      */ 
/*      */     
/*  862 */     if (isEraserEnabled() && this.mEraserState != null) {
/*  863 */       this.mEraserState.drawInk(canvas, this.mPdfViewCtrl);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isCreatingAnnotation() {
/*  872 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onClose() {
/*  880 */     super.onClose();
/*  881 */     unsetAnnot();
/*  882 */     if (this.mInkCommitter != null) {
/*  883 */       this.mInkCommitter.stop();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void commitAnnotation() {
/*  897 */     if (this.mInkCommitter != null) {
/*  898 */       this.mInkCommitter.finish();
/*  899 */       this.mInkCommitter = null;
/*  900 */       this.mCanvasStateManager.reset();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void processEraser(@NonNull InkItem currentInkItem) {
/*      */     InkItem newCurrentInk;
/*  909 */     boolean eraserTouchedAnyStroke = false;
/*  910 */     InkState currentState = this.mCanvasStateManager.getCurrentState();
/*  911 */     InkState newState = new InkState();
/*      */ 
/*      */     
/*  914 */     InkItem erasedCurrentInk = processEraserOnInkItem(currentInkItem);
/*      */     
/*  916 */     if (erasedCurrentInk != null) {
/*  917 */       eraserTouchedAnyStroke = true;
/*  918 */       newCurrentInk = erasedCurrentInk;
/*  919 */       newCurrentInk.shouldAnimateUndoRedo = false;
/*      */     } else {
/*  921 */       newCurrentInk = currentState.currentInk.copy();
/*      */     } 
/*      */ 
/*      */     
/*  925 */     List<InkItem> newPreviousInks = new ArrayList<>();
/*  926 */     for (int index = 0, count = currentState.previousInks.size(); index < count; index++) {
/*  927 */       InkItem previousInk = currentState.previousInks.get(index);
/*  928 */       InkItem erasedPreviousInk = processEraserOnInkItem(previousInk);
/*  929 */       if (erasedPreviousInk != null) {
/*  930 */         eraserTouchedAnyStroke = true;
/*  931 */         erasedPreviousInk.shouldAnimateUndoRedo = false;
/*  932 */         newPreviousInks.add(erasedPreviousInk);
/*      */       } else {
/*  934 */         InkItem inkItem = previousInk.copy();
/*  935 */         newPreviousInks.add(inkItem);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  940 */     if (eraserTouchedAnyStroke) {
/*  941 */       newState.currentInk = newCurrentInk;
/*  942 */       newState.previousInks.addAll(newPreviousInks);
/*  943 */       if (this.mStateToPush != null) {
/*  944 */         this.mCanvasStateManager.saveState(this.mStateToPush);
/*  945 */         this.mStateToPush = null;
/*      */       } 
/*  947 */       this.mCanvasStateManager.mCurrentState = newState;
/*      */     } 
/*      */     
/*  950 */     if (sDebug) {
/*  951 */       if (eraserTouchedAnyStroke) {
/*  952 */         Log.d(TAG, "Eraser has erased");
/*      */       } else {
/*  954 */         Log.d(TAG, "Eraser did nothing");
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*  959 */     this.mPdfViewCtrl.invalidate();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private InkItem processEraserOnInkItem(@NonNull InkItem inkItem) {
/*  973 */     if (this.mEraserState.getPageNumber() != inkItem.pageNumber) {
/*  974 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  979 */     if (inkItem.finishedStrokes.isEmpty()) {
/*  980 */       return null;
/*      */     }
/*      */     
/*      */     try {
/*      */       PressureInkUtils.EraserData eraserData;
/*  985 */       Rect annotRect = getInkItemBBox(inkItem, this.mPdfViewCtrl);
/*      */       
/*  987 */       List<List<Float>> thicknessList = (inkItem instanceof PressureInkItem) ? ((PressureInkItem)inkItem).finishedPressures : null;
/*      */       
/*  989 */       switch (this.mInkEraserMode) {
/*      */         case PIXEL:
/*  991 */           eraserData = PressureInkUtils.erasePointsAndThickness(inkItem.finishedStrokes, thicknessList, this.mPdfViewCtrl, this.mEraserState
/*      */ 
/*      */ 
/*      */               
/*  995 */               .getEraserStrokes(), this.mEraserState
/*  996 */               .getEraserWidth() / 2.0F, annotRect);
/*      */           break;
/*      */ 
/*      */ 
/*      */         
/*      */         default:
/* 1002 */           eraserData = PressureInkUtils.erasePressureStrokesAndThickness(inkItem.finishedStrokes, thicknessList, this.mPdfViewCtrl, this.mEraserState
/*      */ 
/*      */ 
/*      */               
/* 1006 */               .getEraserStrokes(), this.mEraserState
/* 1007 */               .getEraserWidth() / 2.0F, annotRect);
/*      */           break;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1013 */       if (eraserData.hasErased) {
/* 1014 */         if (inkItem instanceof PressureInkItem) {
/* 1015 */           return (InkItem)new PressureInkItem(inkItem.id, null, null, eraserData.newStrokeList, eraserData.newThicknessList, inkItem.pageNumber, inkItem.color, inkItem.opacity, inkItem.baseThickness, inkItem.paintThickness, inkItem.isStylus);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1029 */         return new InkItem(inkItem.id, null, eraserData.newStrokeList, inkItem.pageNumber, inkItem.color, inkItem.opacity, inkItem.baseThickness, inkItem.paintThickness, inkItem.isStylus);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1042 */       return null;
/*      */     }
/* 1044 */     catch (Exception e) {
/* 1045 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/* 1046 */       return null;
/*      */     } 
/*      */   }
/*      */   
/*      */   @NonNull
/*      */   public static ArrayList<ArrayList<PointF>> createPageStrokesFromArrayObj(Obj strokesArray) throws PDFNetException {
/* 1052 */     ArrayList<ArrayList<PointF>> pageStrokes = new ArrayList<>();
/* 1053 */     if (!strokesArray.isArray()) {
/* 1054 */       return pageStrokes;
/*      */     }
/*      */     
/* 1057 */     for (long i = 0L, cnt = strokesArray.size(); i < cnt; i++) {
/* 1058 */       Obj strokeArray = strokesArray.getAt((int)i);
/* 1059 */       if (strokeArray.isArray()) {
/* 1060 */         ArrayList<PointF> pageStroke = new ArrayList<>(); long j, count;
/* 1061 */         for (j = 0L, count = strokeArray.size(); j < count; j += 2L) {
/* 1062 */           float x = (float)strokeArray.getAt((int)j).getNumber();
/* 1063 */           float y = (float)strokeArray.getAt((int)j + 1).getNumber();
/* 1064 */           PointF p = new PointF(x, y);
/* 1065 */           pageStroke.add(p);
/*      */         } 
/* 1067 */         pageStrokes.add(pageStroke);
/*      */       } 
/*      */     } 
/* 1070 */     return pageStrokes;
/*      */   }
/*      */   
/*      */   @NonNull
/*      */   public static List<List<PointF>> createStrokeListFromArrayObj(Obj strokesArray) throws PDFNetException {
/* 1075 */     ArrayList<ArrayList<PointF>> pageStrokes = createPageStrokesFromArrayObj(strokesArray);
/* 1076 */     List<List<PointF>> output = new ArrayList<>();
/* 1077 */     for (ArrayList<PointF> pageStroke : pageStrokes)
/*      */     {
/* 1079 */       output.add(pageStroke);
/*      */     }
/* 1081 */     return output;
/*      */   }
/*      */   
/*      */   private void raiseStylusUsedFirstTimeEvent() {
/* 1085 */     SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this.mPdfViewCtrl.getContext());
/* 1086 */     boolean setStylusHasBeenAsked = pref.getBoolean("pref_set_stylus_as_default_has_been_asked", false);
/* 1087 */     if (!setStylusHasBeenAsked) {
/* 1088 */       ((ToolManager)this.mPdfViewCtrl.getToolManager()).onFreehandStylusUsedFirstTime();
/* 1089 */       SharedPreferences.Editor editor = pref.edit();
/* 1090 */       editor.putBoolean("pref_set_stylus_as_default_has_been_asked", true);
/* 1091 */       editor.apply();
/*      */     } 
/*      */   }
/*      */   
/*      */   void setStyle(Markup annot, InkItem inkItem) {
/*      */     try {
/* 1097 */       ColorPt color = Utils.color2ColorPt(inkItem.color);
/* 1098 */       annot.setColor(color, 3);
/*      */       
/* 1100 */       annot.setOpacity(inkItem.opacity);
/*      */       
/* 1102 */       Annot.BorderStyle bs = annot.getBorderStyle();
/* 1103 */       bs.setWidth(inkItem.baseThickness);
/* 1104 */       annot.setBorderStyle(bs);
/*      */       
/* 1106 */       setAuthor(annot);
/* 1107 */     } catch (PDFNetException e) {
/* 1108 */       AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearStrokes() {
/* 1116 */     this.mCanvasStateManager.clear();
/* 1117 */     updateEditToolbar();
/* 1118 */     this.mPdfViewCtrl.invalidate();
/* 1119 */     this.mNeedNewAnnot = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void undoStroke() {
/* 1126 */     if (this.mCanvasStateManager.canUndo()) {
/* 1127 */       InkState oldState = this.mCanvasStateManager.getCurrentState();
/* 1128 */       if (oldState.currentInk == null) {
/* 1129 */         AnalyticsHandlerAdapter.getInstance().sendException(new IllegalStateException("Current undo state should not be null"));
/*      */         return;
/*      */       } 
/* 1132 */       List<PointF> previousStroke = oldState.currentInk.previousStroke;
/* 1133 */       int previousPage = oldState.currentInk.pageNumber;
/* 1134 */       if (!oldState.currentInk.shouldAnimateUndoRedo) {
/* 1135 */         previousStroke = null;
/* 1136 */       } else if (previousStroke == null && 
/* 1137 */         oldState.previousInks.size() > 0) {
/* 1138 */         previousStroke = ((InkItem)oldState.previousInks.get(oldState.previousInks.size() - 1)).previousStroke;
/* 1139 */         previousPage = ((InkItem)oldState.previousInks.get(oldState.previousInks.size() - 1)).pageNumber;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1144 */       this.mCanvasStateManager.undo();
/* 1145 */       updateEditToolbar();
/*      */ 
/*      */       
/* 1148 */       InkState newState = this.mCanvasStateManager.getCurrentState();
/* 1149 */       InkItem currentInk = newState.currentInk;
/* 1150 */       if (currentInk == null) {
/* 1151 */         AnalyticsHandlerAdapter.getInstance().sendException(new IllegalStateException("New undo state should not be null"));
/*      */         return;
/*      */       } 
/* 1154 */       if (currentInk.shouldAnimateUndoRedo && previousStroke != null) {
/* 1155 */         List<List<PointF>> stroke = new ArrayList<>();
/* 1156 */         stroke.add(previousStroke);
/* 1157 */         float thickness = currentInk.baseThickness;
/* 1158 */         Rect bbox = PressureInkUtils.getInkItemBBox(stroke, thickness, previousPage, this.mPdfViewCtrl, false);
/* 1159 */         ViewerUtils.animateUndoRedo(this.mPdfViewCtrl, bbox, previousPage);
/*      */       } 
/* 1161 */       this.mPdfViewCtrl.invalidate();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void redoStroke() {
/* 1170 */     if (this.mCanvasStateManager.canRedo()) {
/* 1171 */       this.mCanvasStateManager.redo();
/* 1172 */       updateEditToolbar();
/*      */       
/* 1174 */       InkState newState = this.mCanvasStateManager.getCurrentState();
/*      */       
/* 1176 */       InkItem newCurrentInk = newState.currentInk;
/* 1177 */       if (newCurrentInk == null) {
/* 1178 */         AnalyticsHandlerAdapter.getInstance().sendException(new IllegalStateException("New redo state should not be null"));
/*      */         return;
/*      */       } 
/* 1181 */       List<PointF> nextStroke = newCurrentInk.previousStroke;
/* 1182 */       int nextPage = newCurrentInk.pageNumber;
/* 1183 */       if (!newCurrentInk.shouldAnimateUndoRedo) {
/* 1184 */         nextStroke = null;
/* 1185 */       } else if (nextStroke == null && 
/* 1186 */         newState.previousInks.size() > 0) {
/* 1187 */         nextStroke = ((InkItem)newState.previousInks.get(newState.previousInks.size() - 1)).previousStroke;
/* 1188 */         nextPage = ((InkItem)newState.previousInks.get(newState.previousInks.size() - 1)).pageNumber;
/*      */       } 
/*      */ 
/*      */       
/* 1192 */       if (nextStroke != null) {
/* 1193 */         List<List<PointF>> stroke = new ArrayList<>();
/* 1194 */         stroke.add(nextStroke);
/* 1195 */         float thickness = newCurrentInk.baseThickness;
/* 1196 */         Rect bbox = PressureInkUtils.getInkItemBBox(stroke, thickness, nextPage, this.mPdfViewCtrl, false);
/* 1197 */         ViewerUtils.animateUndoRedo(this.mPdfViewCtrl, bbox, nextPage);
/*      */       } 
/* 1199 */       this.mPdfViewCtrl.invalidate();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canUndoStroke() {
/* 1207 */     return this.mCanvasStateManager.canUndo();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canRedoStroke() {
/* 1214 */     return this.mCanvasStateManager.canRedo();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canEraseStroke() {
/* 1221 */     return this.mCanvasStateManager.canClear();
/*      */   }
/*      */   
/*      */   private void updateEditToolbar() {
/* 1225 */     if (this.mOnToolbarStateUpdateListener != null) {
/* 1226 */       this.mOnToolbarStateUpdateListener.onToolbarStateUpdated();
/*      */     }
/*      */   }
/*      */   
/*      */   public static void setDebug(boolean debug) {
/* 1231 */     sDebug = debug;
/*      */   }
/*      */   
/*      */   private static Rect getInkItemBBox(InkItem inkData, PDFViewCtrl pdfViewCtrl) {
/* 1235 */     return PressureInkUtils.getInkItemBBox(inkData.finishedStrokes, inkData.baseThickness, inkData.pageNumber, pdfViewCtrl, true);
/*      */   }
/*      */   
/*      */   public String getPressureSensitiveKey() {
/* 1239 */     return ToolStyleConfig.getInstance().getPressureSensitiveKey(getCreateAnnotType(), "");
/*      */   }
/*      */   
/*      */   private boolean isEraserEnabled() {
/* 1243 */     return (this.mEraserFromSpen || this.mEraserFromToolbar);
/*      */   }
/*      */   private class EraserState extends InkState { boolean pushInksCalled;
/*      */     
/*      */     private EraserState() {
/* 1248 */       this.pushInksCalled = false;
/*      */     }
/*      */     
/*      */     void pushInk(PDFViewCtrl pdfViewCtrl, int pageNumber, int strokeColor, float opacity, float baseThickness, boolean isStylus) {
/* 1252 */       if (!this.pushInksCalled) {
/* 1253 */         super.pushInk(pdfViewCtrl, pageNumber, strokeColor, opacity, baseThickness, isStylus);
/*      */       } else {
/* 1255 */         throw new RuntimeException("PushInk should not be called multiple times for EraserState");
/*      */       } 
/*      */     }
/*      */     
/*      */     @NonNull
/*      */     private List<List<PointF>> getEraserStrokes() {
/* 1261 */       if (this.currentInk == null) {
/* 1262 */         return new ArrayList<>();
/*      */       }
/* 1264 */       return this.currentInk.finishedStrokes;
/*      */     }
/*      */ 
/*      */     
/*      */     private float getEraserWidth() {
/* 1269 */       if (this.currentInk != null) {
/* 1270 */         return this.currentInk.baseThickness;
/*      */       }
/* 1272 */       AnalyticsHandlerAdapter.getInstance().sendException(new IllegalStateException("Could not get eraser width from current ink"));
/* 1273 */       return 2.0F;
/*      */     }
/*      */ 
/*      */     
/*      */     private int getPageNumber() {
/* 1278 */       if (this.currentInk != null) {
/* 1279 */         return this.currentInk.pageNumber;
/*      */       }
/* 1281 */       AnalyticsHandlerAdapter.getInstance().sendException(new IllegalStateException("Could not get eraser page number from current ink"));
/* 1282 */       return 0;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class InkCommitter
/*      */   {
/*      */     @NonNull
/* 1298 */     private final PublishSubject<Boolean> mObjectPublishSubject = PublishSubject.create(); @NonNull
/* 1299 */     private HashMap<InkItem, Ink> mPreviouslyPushedAnnotations = new HashMap<>();
/*      */ 
/*      */     
/*      */     private Disposable mSaveDisposable;
/*      */     
/*      */     private final boolean mIsModifyingInk;
/*      */ 
/*      */     
/*      */     InkCommitter(boolean isModified) {
/* 1308 */       this.mIsModifyingInk = isModified;
/*      */       
/* 1310 */       if (FreehandCreate.this.mTimedModeEnabled && this.mIsModifyingInk) {
/* 1311 */         throw new RuntimeException("Timer mode while modifying ink is not currently supported");
/*      */       }
/* 1313 */       initalizeObservables();
/*      */     }
/*      */ 
/*      */     
/*      */     private void initalizeObservables() {
/* 1318 */       if (FreehandCreate.this.mMultiStrokeMode && FreehandCreate.this.mTimedModeEnabled) {
/*      */         
/* 1320 */         this
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1330 */           .mSaveDisposable = getTimerObservable(!FreehandCreate.this.mIsFromEditToolbar, FreehandCreate.this.mCanvasStateManager).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).doOnDispose(new Action() { public void run() throws Exception { InkCommitter.this.commitAndShowAnnotation(true); } }).subscribe(new Consumer<Boolean>()
/*      */             {
/*      */               public void accept(Boolean shouldRaiseEvent) throws Exception {
/* 1333 */                 InkState currentState = FreehandCreate.this.mCanvasStateManager.getCurrentState();
/* 1334 */                 if (FreehandCreate.sDebug) {
/* 1335 */                   Log.d(FreehandCreate.TAG, "There are " + currentState.previousInks.size() + "previous inks");
/*      */                 }
/* 1337 */                 InkCommitter.this.commitInkState(currentState, FreehandCreate.this.mPdfViewCtrl, FreehandCreate.this, shouldRaiseEvent.booleanValue());
/*      */               }
/*      */             });
/*      */       } else {
/* 1341 */         this
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1350 */           .mSaveDisposable = this.mObjectPublishSubject.serialize().subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).doOnDispose(new Action() { public void run() throws Exception { InkCommitter.this.commitAndShowAnnotation(true); } }).subscribe(new Consumer<Boolean>()
/*      */             {
/*      */               public void accept(Boolean shouldRaiseEvent) throws Exception {
/* 1353 */                 InkCommitter.this.commitAndShowAnnotation(shouldRaiseEvent.booleanValue());
/*      */               }
/*      */             });
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     private void commitAndShowAnnotation(boolean shouldRaiseEvent) {
/* 1361 */       commitInkState(FreehandCreate.this.mCanvasStateManager.getCurrentState(), FreehandCreate.this.mPdfViewCtrl, FreehandCreate.this, shouldRaiseEvent);
/*      */ 
/*      */       
/* 1364 */       for (Map.Entry<InkItem, Ink> inkItemEntry : this.mPreviouslyPushedAnnotations.entrySet()) {
/* 1365 */         InkItem inkItem = inkItemEntry.getKey();
/* 1366 */         Ink annot = inkItemEntry.getValue();
/* 1367 */         boolean shouldUnlockRead = false;
/*      */         try {
/* 1369 */           FreehandCreate.this.mPdfViewCtrl.showAnnotation((Annot)annot);
/* 1370 */           FreehandCreate.this.mPdfViewCtrl.docLockRead();
/* 1371 */           shouldUnlockRead = true;
/* 1372 */           FreehandCreate.this.mPdfViewCtrl.update((Annot)annot, inkItem.pageNumber);
/* 1373 */         } catch (PDFNetException e) {
/* 1374 */           AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*      */         } finally {
/* 1376 */           if (shouldUnlockRead) {
/* 1377 */             FreehandCreate.this.mPdfViewCtrl.docUnlockRead();
/*      */           }
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Observable<Boolean> getTimerObservable(boolean isStylusAsPen, final CanvasStateManager canvasStateManager) {
/* 1389 */       int timePeriod = isStylusAsPen ? 3 : 30;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1399 */       Observable<Boolean> observable = Observable.interval(timePeriod, TimeUnit.SECONDS).map(new Function<Long, Boolean>() { public Boolean apply(Long aLong) throws Exception { return Boolean.FALSE; } }).mergeWith((ObservableSource)this.mObjectPublishSubject.serialize()).filter(new Predicate<Boolean>()
/*      */           {
/*      */             public boolean test(Boolean aLong) throws Exception
/*      */             {
/* 1403 */               return (canvasStateManager.getCurrentState() != null && (canvasStateManager.getCurrentState()).currentInk != null);
/*      */             }
/*      */           });
/* 1406 */       return observable;
/*      */     }
/*      */     
/*      */     void initializeWithInkAnnot(@Nullable Ink inkAnnot, @NonNull InkState inkState) {
/* 1410 */       if (inkAnnot != null) {
/* 1411 */         this.mPreviouslyPushedAnnotations.put(inkState.currentInk, inkAnnot);
/*      */       } else {
/* 1413 */         AnalyticsHandlerAdapter.getInstance().sendException(new IllegalStateException("Edit ink annot is null and can not be initialized."));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     void finish() {
/* 1419 */       this.mSaveDisposable.dispose();
/* 1420 */       this.mPreviouslyPushedAnnotations.clear();
/*      */     }
/*      */     
/*      */     void stop() {
/* 1424 */       this.mSaveDisposable.dispose();
/*      */     }
/*      */     
/*      */     void restartIfStopped() {
/* 1428 */       if (this.mSaveDisposable.isDisposed()) {
/* 1429 */         initalizeObservables();
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void commitInkState(InkState inkState, PDFViewCtrl pdfViewCtrl, FreehandCreate tool, boolean lastCommit) {
/* 1442 */       if (inkState.currentInk != null) {
/* 1443 */         InkState inkStateCopy = new InkState(inkState);
/*      */         
/* 1445 */         List<InkItem> inksToSave = new ArrayList<>();
/*      */ 
/*      */ 
/*      */         
/* 1449 */         inksToSave.addAll(inkState.previousInks);
/* 1450 */         inksToSave.add(inkStateCopy.currentInk);
/*      */ 
/*      */         
/* 1453 */         commitInks(inksToSave, pdfViewCtrl, tool, lastCommit);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void commitInks(List<InkItem> allInkAnnotData, PDFViewCtrl pdfViewCtrl, FreehandCreate tool, boolean lastCommit) {
/* 1466 */       if (FreehandCreate.sDebug) {
/* 1467 */         Log.d(FreehandCreate.TAG, "Committing annotations, is last commit = " + lastCommit);
/*      */       }
/* 1469 */       boolean shouldUnlock = false;
/*      */       try {
/* 1471 */         pdfViewCtrl.docLock(true);
/* 1472 */         shouldUnlock = true;
/*      */ 
/*      */         
/* 1475 */         if (this.mIsModifyingInk) {
/* 1476 */           if (!lastCommit) {
/* 1477 */             throw new RuntimeException("When editing annot, commit can only happen once so lastCommit must be true");
/*      */           }
/* 1479 */           commitEditAnnotToDoc(allInkAnnotData, pdfViewCtrl, tool);
/*      */         } else {
/* 1481 */           commitToDoc(allInkAnnotData, pdfViewCtrl, tool, lastCommit);
/*      */         } 
/* 1483 */       } catch (Exception e) {
/* 1484 */         FreehandCreate.this.mNextToolMode = ToolManager.ToolMode.PAN;
/* 1485 */         ((ToolManager)pdfViewCtrl.getToolManager()).annotationCouldNotBeAdded(e.getMessage());
/* 1486 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */       } finally {
/* 1488 */         if (shouldUnlock) {
/* 1489 */           pdfViewCtrl.docUnlock();
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     private void updatePressureInkItem(@NonNull Ink ink, @NonNull PressureInkItem pressureInkItem) throws PDFNetException {
/* 1495 */       List<List<Float>> thicknessList = pressureInkItem.finishedPressures;
/* 1496 */       if (thicknessList != null && thicknessList.size() == pressureInkItem.finishedStrokes.size()) {
/* 1497 */         PressureInkUtils.clearThicknessList((Annot)ink);
/* 1498 */         PressureInkUtils.setThicknessList(ink, thicknessList);
/* 1499 */         PressureInkUtils.refreshCustomAppearanceForNewAnnot(FreehandCreate.this.mPdfViewCtrl, (Annot)ink);
/*      */       } else {
/* 1501 */         ink.refreshAppearance();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void commitEditAnnotToDoc(List<InkItem> allInkAnnotData, PDFViewCtrl pdfViewCtrl, FreehandCreate tool) throws PDFNetException {
/* 1517 */       if (!this.mIsModifyingInk) {
/* 1518 */         throw new RuntimeException("This should not be called for modifying inks");
/*      */       }
/*      */       
/* 1521 */       PDFDoc pdfDoc = FreehandCreate.this.mPdfViewCtrl.getDoc();
/*      */       
/* 1523 */       for (InkItem inkItem : allInkAnnotData) {
/* 1524 */         Ink ink; Rect annotRect = FreehandCreate.getInkItemBBox(inkItem, pdfViewCtrl);
/* 1525 */         if (annotRect == null) {
/*      */           continue;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 1531 */         if (this.mPreviouslyPushedAnnotations.containsKey(inkItem)) {
/* 1532 */           ink = this.mPreviouslyPushedAnnotations.get(inkItem);
/*      */         } else {
/* 1534 */           AnalyticsHandlerAdapter.getInstance().sendException(new RuntimeException("The edit annot must exist"));
/*      */ 
/*      */           
/*      */           return;
/*      */         } 
/*      */         
/* 1540 */         boolean shouldUpdateInkList = shouldUpdateInkList(ink, inkItem);
/* 1541 */         boolean isErased = inkItem.finishedStrokes.isEmpty();
/*      */         
/* 1543 */         if (isErased) {
/* 1544 */           if (FreehandCreate.sDebug) {
/* 1545 */             Log.d(FreehandCreate.TAG, "Edit Annotation deleted");
/*      */           }
/* 1547 */           FreehandCreate.this.raiseAnnotationPreRemoveEvent((Annot)ink, inkItem.pageNumber);
/* 1548 */           FreehandCreate.this.mPdfViewCtrl.showAnnotation((Annot)ink);
/* 1549 */           Page page = pdfDoc.getPage(inkItem.pageNumber);
/* 1550 */           page.annotRemove((Annot)ink);
/* 1551 */           pdfViewCtrl.update((Annot)ink, inkItem.pageNumber);
/* 1552 */           FreehandCreate.this.raiseAnnotationRemovedEvent((Annot)ink, inkItem.pageNumber); continue;
/* 1553 */         }  if (shouldUpdateInkList) {
/* 1554 */           if (FreehandCreate.sDebug) {
/* 1555 */             Log.d(FreehandCreate.TAG, "Edit Annotation updated");
/*      */           }
/* 1557 */           FreehandCreate.this.raiseAnnotationPreModifyEvent((Annot)ink, inkItem.pageNumber);
/*      */ 
/*      */           
/* 1560 */           boolean inkSmoothing = (!inkItem.isStylus && ((ToolManager)pdfViewCtrl.getToolManager()).isInkSmoothingEnabled());
/* 1561 */           ink.setSmoothing(inkSmoothing);
/*      */ 
/*      */           
/* 1564 */           PressureInkUtils.setInkList(ink, inkItem.finishedStrokes);
/*      */           
/* 1566 */           buildAnnotBBox((Annot)ink, annotRect);
/* 1567 */           ink.setRect(annotRect);
/* 1568 */           tool.setStyle((Markup)ink, inkItem);
/* 1569 */           if (inkItem instanceof PressureInkItem) {
/* 1570 */             updatePressureInkItem(ink, (PressureInkItem)inkItem);
/*      */           } else {
/* 1572 */             ink.refreshAppearance();
/*      */           } 
/*      */           
/* 1575 */           FreehandCreate.this.setAnnot((Annot)ink, inkItem.pageNumber);
/*      */ 
/*      */           
/* 1578 */           pdfViewCtrl.update((Annot)ink, inkItem.pageNumber);
/* 1579 */           FreehandCreate.this.raiseAnnotationModifiedEvent((Annot)ink, inkItem.pageNumber);
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void commitToDoc(List<InkItem> allInkAnnotData, PDFViewCtrl pdfViewCtrl, FreehandCreate tool, boolean lastCommit) throws PDFNetException {
/* 1597 */       PDFDoc pdfDoc = pdfViewCtrl.getDoc();
/* 1598 */       HashMap<InkItem, Ink> annotsToDelete = new HashMap<>(this.mPreviouslyPushedAnnotations);
/* 1599 */       for (InkItem inkItem : allInkAnnotData) {
/* 1600 */         Ink ink; Rect annotRect = FreehandCreate.getInkItemBBox(inkItem, pdfViewCtrl);
/* 1601 */         if (annotRect == null) {
/*      */           continue;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 1607 */         if (this.mPreviouslyPushedAnnotations.containsKey(inkItem)) {
/* 1608 */           if (lastCommit) {
/* 1609 */             Page page = pdfDoc.getPage(inkItem.pageNumber);
/*      */             
/* 1611 */             page.annotRemove((Annot)this.mPreviouslyPushedAnnotations.get(inkItem));
/* 1612 */             this.mPreviouslyPushedAnnotations.remove(inkItem);
/* 1613 */             annotsToDelete.remove(inkItem);
/* 1614 */             ink = Ink.create((Doc)pdfDoc, annotRect);
/*      */           } else {
/* 1616 */             ink = this.mPreviouslyPushedAnnotations.get(inkItem);
/*      */           } 
/*      */ 
/*      */           
/* 1620 */           if (inkItem.finishedStrokes.isEmpty()) {
/*      */             continue;
/*      */           }
/*      */         } else {
/* 1624 */           if (inkItem.finishedStrokes.isEmpty()) {
/*      */             continue;
/*      */           }
/* 1627 */           ink = Ink.create((Doc)pdfDoc, annotRect);
/* 1628 */           pdfViewCtrl.hideAnnotation((Annot)ink);
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1633 */         boolean shouldUpdateInkList = shouldUpdateInkList(ink, inkItem);
/*      */         
/* 1635 */         if (shouldUpdateInkList) {
/* 1636 */           if (FreehandCreate.sDebug) {
/* 1637 */             Obj annotObj = ink.getSDFObj();
/* 1638 */             if (annotObj.findObj("InkList") == null) {
/* 1639 */               Log.d(FreehandCreate.TAG, "Annotation pushed");
/*      */             } else {
/* 1641 */               Log.d(FreehandCreate.TAG, "Annotation updated");
/*      */             } 
/*      */           } 
/*      */ 
/*      */           
/* 1646 */           boolean inkSmoothing = (!inkItem.isStylus && ((ToolManager)pdfViewCtrl.getToolManager()).isInkSmoothingEnabled());
/* 1647 */           ink.setSmoothing(inkSmoothing);
/*      */ 
/*      */           
/* 1650 */           PressureInkUtils.setInkList(ink, inkItem.finishedStrokes);
/*      */           
/* 1652 */           buildAnnotBBox((Annot)ink, annotRect);
/* 1653 */           ink.setRect(annotRect);
/* 1654 */           tool.setStyle((Markup)ink, inkItem);
/* 1655 */           if (inkItem instanceof PressureInkItem) {
/* 1656 */             updatePressureInkItem(ink, (PressureInkItem)inkItem);
/*      */           } else {
/* 1658 */             ink.refreshAppearance();
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/* 1663 */         if (!this.mPreviouslyPushedAnnotations.keySet().contains(inkItem)) {
/* 1664 */           Page page = pdfDoc.getPage(inkItem.pageNumber);
/* 1665 */           page.annotPushBack((Annot)ink);
/* 1666 */           this.mPreviouslyPushedAnnotations.put(inkItem, ink);
/*      */         } else {
/* 1668 */           annotsToDelete.remove(inkItem);
/*      */         } 
/*      */         
/* 1671 */         if (lastCommit) {
/*      */           
/* 1673 */           pdfViewCtrl.update((Annot)ink, inkItem.pageNumber);
/* 1674 */           FreehandCreate.this.raiseAnnotationAddedEvent((Annot)ink, inkItem.pageNumber);
/* 1675 */           FreehandCreate.this.setAnnot((Annot)ink, inkItem.pageNumber);
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1680 */       for (Map.Entry<InkItem, Ink> inkItemEntry : annotsToDelete.entrySet()) {
/* 1681 */         Ink annot = inkItemEntry.getValue();
/* 1682 */         InkItem inkItem = inkItemEntry.getKey();
/* 1683 */         int pageNumber = inkItem.pageNumber;
/*      */ 
/*      */         
/* 1686 */         Page page = pdfDoc.getPage(pageNumber);
/* 1687 */         page.annotRemove((Annot)annot);
/* 1688 */         this.mPreviouslyPushedAnnotations.remove(inkItem);
/*      */       } 
/*      */     }
/*      */     
/*      */     private boolean shouldUpdateInkList(Ink inkAnnot, InkItem inkItem) throws PDFNetException {
/*      */       List<List<PointF>> pageStrokesFromArrayObj;
/* 1694 */       Obj inkList = inkAnnot.getSDFObj().findObj("InkList");
/*      */       
/* 1696 */       if (inkList != null) {
/* 1697 */         pageStrokesFromArrayObj = FreehandCreate.createStrokeListFromArrayObj(inkList);
/*      */       } else {
/* 1699 */         pageStrokesFromArrayObj = new ArrayList<>();
/*      */       } 
/* 1701 */       boolean shouldUpdateInkList = false;
/* 1702 */       if (pageStrokesFromArrayObj.isEmpty()) {
/* 1703 */         shouldUpdateInkList = true;
/* 1704 */       } else if (pageStrokesFromArrayObj.size() != inkItem.finishedStrokes.size()) {
/* 1705 */         shouldUpdateInkList = true;
/*      */       } else {
/* 1707 */         int size = pageStrokesFromArrayObj.size();
/* 1708 */         for (int i = 0; i < size; i++) {
/* 1709 */           if (((List)pageStrokesFromArrayObj.get(i)).size() != ((List)inkItem.finishedStrokes.get(i)).size()) {
/* 1710 */             shouldUpdateInkList = true;
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/* 1716 */       return shouldUpdateInkList;
/*      */     }
/*      */     
/*      */     protected void buildAnnotBBox(Annot ink, Rect bbox) throws PDFNetException {
/* 1720 */       if (ink != null && ink.isValid()) {
/* 1721 */         bbox.set(0.0D, 0.0D, 0.0D, 0.0D);
/*      */         try {
/* 1723 */           Rect r = ink.getVisibleContentBox();
/* 1724 */           bbox.set((float)r.getX1(), (float)r.getY1(), (float)r.getX2(), (float)r.getY2());
/* 1725 */         } catch (Exception e) {
/* 1726 */           AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private class CanvasStateManager
/*      */   {
/*      */     @NonNull
/* 1737 */     private InkState mCurrentState = new InkState();
/*      */     
/* 1739 */     private Stack<InkState> mUndoStateStack = new Stack<>();
/* 1740 */     private Stack<InkState> mRedoStateStack = new Stack<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void initializeStateForEditing(PDFViewCtrl pdfViewCtrl, int pageNum, int mStrokeColor, float mOpacity, float mThicknessDraw, boolean mIsStylus, Ink inkAnnot) throws PDFNetException {
/* 1759 */       boolean isPressure = PressureInkUtils.isPressureSensitive((Annot)inkAnnot);
/*      */ 
/*      */       
/* 1762 */       this.mCurrentState.pushInk(pdfViewCtrl, pageNum, mStrokeColor, mOpacity, mThicknessDraw, mIsStylus, isPressure);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1772 */       this.mUndoStateStack.push(new InkState(this.mCurrentState));
/* 1773 */       this.mRedoStateStack.clear();
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/* 1778 */         Obj annotObj = inkAnnot.getSDFObj();
/* 1779 */         Obj inkList = annotObj.findObj("InkList");
/* 1780 */         List<List<PointF>> pageStrokesFromArrayObj = FreehandCreate.createStrokeListFromArrayObj(inkList);
/* 1781 */         List<double[]> thickneses = PressureInkUtils.getThicknessArrays(inkAnnot);
/* 1782 */         boolean isPressureInk = (thickneses != null && pageStrokesFromArrayObj.size() == thickneses.size());
/*      */         
/* 1784 */         for (int k = 0; k < pageStrokesFromArrayObj.size(); k++) {
/* 1785 */           List<PointF> stroke = pageStrokesFromArrayObj.get(k);
/* 1786 */           double[] thickness = null;
/* 1787 */           if (isPressureInk) {
/* 1788 */             thickness = thickneses.get(k);
/*      */           }
/* 1790 */           for (int i = 0; i < stroke.size(); i++) {
/* 1791 */             int motionEvent; PointF pt = stroke.get(i);
/* 1792 */             double[] pagePt = pdfViewCtrl.convPagePtToScreenPt(pt.x, pt.y, pageNum);
/*      */             
/* 1794 */             if (i == 0) {
/* 1795 */               motionEvent = 0;
/*      */             } else {
/* 1797 */               motionEvent = 2;
/*      */             } 
/* 1799 */             double pressure = (thickness == null) ? 1.0D : thickness[i];
/* 1800 */             this.mCurrentState.addPoint((float)pagePt[0], (float)pagePt[1], (float)pressure, motionEvent);
/*      */           } 
/* 1802 */           this.mCurrentState.addPoint(-1.0F, -1.0F, -1.0F, 1);
/*      */         } 
/* 1804 */       } catch (PDFNetException e) {
/* 1805 */         AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*      */       } 
/*      */     }
/*      */     
/*      */     @NonNull
/*      */     InkState getCurrentState() {
/* 1811 */       return this.mCurrentState;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     void saveState(@NonNull InkState state) {
/* 1817 */       this.mUndoStateStack.push(state);
/* 1818 */       this.mRedoStateStack.clear();
/*      */     }
/*      */     
/*      */     void undo() {
/* 1822 */       this.mRedoStateStack.push(new InkState(this.mCurrentState));
/* 1823 */       this.mCurrentState = this.mUndoStateStack.pop();
/*      */     }
/*      */     
/*      */     void redo() {
/* 1827 */       this.mUndoStateStack.push(new InkState(this.mCurrentState));
/* 1828 */       this.mCurrentState = this.mRedoStateStack.pop();
/*      */     }
/*      */     
/*      */     boolean canUndo() {
/* 1832 */       return !this.mUndoStateStack.isEmpty();
/*      */     }
/*      */     
/*      */     boolean canRedo() {
/* 1836 */       return !this.mRedoStateStack.isEmpty();
/*      */     }
/*      */     
/*      */     boolean canClear() {
/* 1840 */       return this.mCurrentState.canClear();
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1844 */       saveState(new InkState(this.mCurrentState));
/* 1845 */       getCurrentState().clear();
/*      */     }
/*      */     
/*      */     public void reset() {
/* 1849 */       this.mCurrentState = new InkState();
/* 1850 */       this.mUndoStateStack = new Stack<>();
/* 1851 */       this.mRedoStateStack = new Stack<>();
/*      */     }
/*      */     
/*      */     private CanvasStateManager() {}
/*      */   }
/*      */   
/*      */   private class InkState
/*      */   {
/*      */     @Nullable
/*      */     InkItem currentInk;
/* 1861 */     final Stack<InkItem> previousInks = new Stack<>();
/*      */ 
/*      */ 
/*      */     
/*      */     InkState() {}
/*      */ 
/*      */     
/*      */     @Nullable
/*      */     Rect getBoundingBox() {
/* 1870 */       if (this.currentInk != null) {
/* 1871 */         return PressureInkUtils.getInkItemBBox(this.currentInk.finishedStrokes, this.currentInk.baseThickness, this.currentInk.pageNumber, FreehandCreate.this.mPdfViewCtrl, false);
/*      */       }
/* 1873 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     InkState(InkState thatState) {
/* 1878 */       this.currentInk = (thatState.currentInk == null) ? null : thatState.currentInk.copy();
/* 1879 */       for (InkItem item : thatState.previousInks) {
/* 1880 */         this.previousInks.push(item);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     void addPoint(float x, float y, float pressure, int action) {
/* 1887 */       if (this.currentInk != null) {
/* 1888 */         double[] pt = FreehandCreate.this.mPdfViewCtrl.convScreenPtToPagePt(x, y, this.currentInk.pageNumber);
/* 1889 */         this.currentInk.addPoint((float)pt[0], (float)pt[1], pressure, action);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pushInk(PDFViewCtrl pdfViewCtrl, int pageNumber, int strokeColor, float opacity, float baseThickness, boolean isStylus) {
/* 1901 */       pushInk(pdfViewCtrl, pageNumber, strokeColor, opacity, baseThickness, isStylus, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pushInk(PDFViewCtrl pdfViewCtrl, int pageNumber, int strokeColor, float opacity, float baseThickness, boolean isStylus, boolean isPressure) {
/* 1911 */       if (this.currentInk != null) {
/* 1912 */         this.previousInks.push(this.currentInk);
/*      */       }
/* 1914 */       if (isPressure) {
/* 1915 */         this.currentInk = (InkItem)new PressureInkItem(pageNumber, strokeColor, opacity, baseThickness, isStylus, pdfViewCtrl);
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */ 
/*      */         
/* 1923 */         this.currentInk = new InkItem(pageNumber, strokeColor, opacity, baseThickness, isStylus, pdfViewCtrl);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void drawInk(Canvas canvas, PDFViewCtrl pdfViewCtrl) {
/* 1934 */       if (this.currentInk != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1939 */         for (InkItem inkItem : this.previousInks) {
/* 1940 */           inkItem.draw(canvas, pdfViewCtrl, null, null);
/*      */         }
/*      */         
/* 1943 */         this.currentInk.draw(canvas, pdfViewCtrl, null, null);
/*      */       } 
/*      */     }
/*      */     
/*      */     void clear() {
/* 1948 */       if (this.currentInk != null) {
/* 1949 */         this.currentInk.finishedStrokes.clear();
/* 1950 */         this.previousInks.clear();
/*      */       } 
/*      */     }
/*      */     
/*      */     boolean canClear() {
/* 1955 */       return ((this.currentInk != null && !this.currentInk.finishedStrokes.isEmpty()) || this.previousInks.size() > 0);
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\FreehandCreate.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */