/*     */ package com.pdftron.pdf.tools;
/*     */ 
/*     */ import android.content.res.TypedArray;
/*     */ import android.graphics.Canvas;
/*     */ import android.graphics.Matrix;
/*     */ import android.graphics.Paint;
/*     */ import android.graphics.PointF;
/*     */ import android.graphics.Rect;
/*     */ import android.graphics.RectF;
/*     */ import android.view.MotionEvent;
/*     */ import androidx.annotation.Keep;
/*     */ import androidx.annotation.NonNull;
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.Annot;
/*     */ import com.pdftron.pdf.PDFDoc;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.Page;
/*     */ import com.pdftron.pdf.Rect;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.AnnotUtils;
/*     */ import com.pdftron.pdf.utils.AnnotationClipboardHelper;
/*     */ import com.pdftron.pdf.utils.CommonToast;
/*     */ import com.pdftron.pdf.utils.DrawingUtils;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ public class AnnotEditRectGroup
/*     */   extends AnnotEdit
/*     */ {
/*  49 */   private static final String TAG = AnnotEditRectGroup.class.getName();
/*  50 */   private HashMap<Annot, Integer> mSelectedAnnotsMap = new HashMap<>();
/*     */   
/*  52 */   protected PointF mPt1 = new PointF(0.0F, 0.0F);
/*  53 */   protected PointF mPt2 = new PointF(0.0F, 0.0F);
/*  54 */   private Paint mFillPaint = new Paint(1);
/*  55 */   private RectF mSelectedArea = new RectF();
/*     */   
/*     */   private int mDownPageNum;
/*     */   
/*     */   private boolean mResizeAnnots;
/*     */   private boolean mGroupSelected;
/*     */   private boolean mCanCopy;
/*     */   private final Comparator<Annot> mDateComparator;
/*     */   
/*     */   public AnnotEditRectGroup(@NonNull PDFViewCtrl ctrl)
/*     */   {
/*  66 */     super(ctrl);
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
/* 804 */     this.mDateComparator = new Comparator<Annot>()
/*     */       {
/*     */         public int compare(Annot o1, Annot o2)
/*     */         {
/* 808 */           return AnnotUtils.compareCreationDate(o2, o1); } }; this.mNextToolMode = getToolMode(); this.mFillPaint.setStyle(Paint.Style.FILL); TypedArray a = ctrl.getContext().obtainStyledAttributes(null, R.styleable.RectGroupAnnotEdit, R.attr.rect_group_annot_edit_style, R.style.RectGroupAnnotEdit); try {
/*     */       int color = a.getColor(R.styleable.RectGroupAnnotEdit_fillColor, -16776961); float opacity = a.getFloat(R.styleable.RectGroupAnnotEdit_fillOpacity, 0.38F); this.mFillPaint.setColor(color); this.mFillPaint.setAlpha((int)(opacity * 255.0F));
/*     */     } finally {
/*     */       a.recycle();
/*     */     }  this.mSelectionBoxMargin = 0;
/* 813 */     this.mCtrlRadius = convDp2Pix(7.5F); } private void sort(@NonNull List<Annot> annots) { Collections.sort(annots, this.mDateComparator); }
/*     */ 
/*     */   
/*     */   public ToolManager.ToolModeBase getToolMode() {
/*     */     return ToolManager.ToolMode.ANNOT_EDIT_RECT_GROUP;
/*     */   }
/*     */   
/*     */   public boolean onDown(MotionEvent e) {
/*     */     super.onDown(e);
/*     */     if (this.mEffCtrlPtId == -1) {
/*     */       this.mPt1.x = e.getX() + this.mPdfViewCtrl.getScrollX();
/*     */       this.mPt1.y = e.getY() + this.mPdfViewCtrl.getScrollY();
/*     */       this.mDownPageNum = this.mPdfViewCtrl.getPageNumberFromScreenPt(e.getX(), e.getY());
/*     */       if (this.mDownPageNum < 1)
/*     */         this.mDownPageNum = this.mPdfViewCtrl.getCurrentPage(); 
/*     */       this.mPt2.set(this.mPt1);
/*     */       this.mResizeAnnots = false;
/*     */       this.mSelectedArea.setEmpty();
/*     */     } 
/*     */     if (this.mDownPageNum >= 1) {
/*     */       this.mPageCropOnClientF = Utils.buildPageBoundBoxOnClient(this.mPdfViewCtrl, this.mDownPageNum);
/*     */       Utils.snapPointToRect(this.mPt1, this.mPageCropOnClientF);
/*     */     } 
/*     */     return false;
/*     */   }
/*     */   
/*     */   public boolean onMove(MotionEvent e1, MotionEvent e2, float x_dist, float y_dist) {
/*     */     if (this.mScaled)
/*     */       return false; 
/*     */     super.onMove(e1, e2, x_dist, y_dist);
/*     */     if (this.mEffCtrlPtId != -1) {
/*     */       this.mResizeAnnots = true;
/*     */       return true;
/*     */     } 
/*     */     this.mAllowTwoFingerScroll = (e1.getPointerCount() == 2 || e2.getPointerCount() == 2);
/*     */     this.mAllowOneFingerScrollWithStylus = (this.mStylusUsed && e2.getToolType(0) != 2);
/*     */     if (this.mAllowTwoFingerScroll || this.mAllowOneFingerScrollWithStylus) {
/*     */       this.mPdfViewCtrl.setBuiltInPageSlidingState(true);
/*     */     } else {
/*     */       this.mPdfViewCtrl.setBuiltInPageSlidingState(false);
/*     */     } 
/*     */     if (this.mAllowTwoFingerScroll)
/*     */       return false; 
/*     */     if (this.mAllowOneFingerScrollWithStylus)
/*     */       return false; 
/*     */     float x = this.mPt2.x;
/*     */     float y = this.mPt2.y;
/*     */     this.mPt2.x = e2.getX() + this.mPdfViewCtrl.getScrollX();
/*     */     this.mPt2.y = e2.getY() + this.mPdfViewCtrl.getScrollY();
/*     */     Utils.snapPointToRect(this.mPt2, this.mPageCropOnClientF);
/*     */     float min_x = Math.min(Math.min(x, this.mPt2.x), this.mPt1.x);
/*     */     float max_x = Math.max(Math.max(x, this.mPt2.x), this.mPt1.x);
/*     */     float min_y = Math.min(Math.min(y, this.mPt2.y), this.mPt1.y);
/*     */     float max_y = Math.max(Math.max(y, this.mPt2.y), this.mPt1.y);
/*     */     this.mPdfViewCtrl.invalidate((int)min_x, (int)min_y, (int)Math.ceil(max_x), (int)Math.ceil(max_y));
/*     */     return true;
/*     */   }
/*     */   
/*     */   public boolean onUp(MotionEvent e, PDFViewCtrl.PriorEventMode priorEventMode) {
/*     */     if (this.mAllowTwoFingerScroll) {
/*     */       doneTwoFingerScrolling();
/*     */       return false;
/*     */     } 
/*     */     if (priorEventMode == PDFViewCtrl.PriorEventMode.PAGE_SLIDING)
/*     */       return false; 
/*     */     if (hasAnnotSelected() && this.mResizeAnnots) {
/*     */       this.mResizeAnnots = false;
/*     */       return super.onUp(e, priorEventMode);
/*     */     } 
/*     */     if (this.mPt1.equals(this.mPt2))
/*     */       return true; 
/*     */     this.mAllowOneFingerScrollWithStylus = (this.mStylusUsed && e.getToolType(0) != 2);
/*     */     if (this.mAllowOneFingerScrollWithStylus)
/*     */       return true; 
/*     */     if (!this.mSelectedArea.isEmpty())
/*     */       return skipOnUpPriorEvent(priorEventMode); 
/*     */     try {
/*     */       float min_x = Math.min(this.mPt1.x, this.mPt2.x);
/*     */       float max_x = Math.max(this.mPt1.x, this.mPt2.x);
/*     */       float min_y = Math.min(this.mPt1.y, this.mPt2.y);
/*     */       float max_y = Math.max(this.mPt1.y, this.mPt2.y);
/*     */       this.mPt1.x = min_x;
/*     */       this.mPt1.y = min_y;
/*     */       this.mPt2.x = max_x;
/*     */       this.mPt2.y = max_y;
/*     */       Rect pageRect = getShapeBBox();
/*     */       ArrayList<Annot> annotsInPage = this.mPdfViewCtrl.getAnnotationsOnPage(this.mDownPageNum);
/*     */       RectF selectedRectInPage = new RectF();
/*     */       this.mSelectedAnnotsMap.clear();
/*     */       this.mAnnotIsTextMarkup = false;
/*     */       if (pageRect != null) {
/*     */         boolean reCalculateBBox = false;
/*     */         this.mCanCopy = true;
/*     */         Set<Annot> annotsGroupSet = new HashSet<>();
/*     */         for (Annot annot : annotsInPage) {
/*     */           Rect annotRect = this.mPdfViewCtrl.getPageRectForAnnot(annot, this.mDownPageNum);
/*     */           annotRect.normalize();
/*     */           RectF annotPageRect = getAnnotPageRect(annot);
/*     */           if (annotRect.intersectRect(pageRect, annotRect) && hasPermission(annot, 0) && isAnnotSupportEdit(annot)) {
/*     */             if (!isAnnotSupportResize(annot))
/*     */               this.mAnnotIsTextMarkup = true; 
/*     */             if (!isAnnotSupportCopy(annot))
/*     */               this.mCanCopy = false; 
/*     */             this.mSelectedAnnotsMap.put(annot, Integer.valueOf(this.mDownPageNum));
/*     */             selectedRectInPage.union(annotPageRect);
/*     */             annotsGroupSet.add(annot);
/*     */             ArrayList<Annot> groupAnnots = AnnotUtils.getAnnotationsInGroup(this.mPdfViewCtrl, annot, this.mDownPageNum);
/*     */             if (groupAnnots != null && groupAnnots.size() > 1) {
/*     */               annotsGroupSet.addAll(groupAnnots);
/*     */               reCalculateBBox = true;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         if (reCalculateBBox) {
/*     */           this.mSelectedAnnotsMap.clear();
/*     */           selectedRectInPage = new RectF();
/*     */           for (Annot annot : annotsGroupSet) {
/*     */             if (hasPermission(annot, 0) && isAnnotSupportEdit(annot)) {
/*     */               if (!isAnnotSupportResize(annot))
/*     */                 this.mAnnotIsTextMarkup = true; 
/*     */               this.mSelectedAnnotsMap.put(annot, Integer.valueOf(this.mDownPageNum));
/*     */               selectedRectInPage.union(getAnnotPageRect(annot));
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       if (hasAnnotSelected() && this.mSelectedAnnotsMap.size() == 1) {
/*     */         ArrayList<Annot> entry = new ArrayList<>(this.mSelectedAnnotsMap.keySet());
/*     */         this.mAnnot = entry.get(0);
/*     */         this.mAnnotPageNum = this.mDownPageNum;
/*     */         buildAnnotBBox();
/*     */         ((ToolManager)this.mPdfViewCtrl.getToolManager()).selectAnnot(this.mAnnot, this.mDownPageNum);
/*     */         return false;
/*     */       } 
/*     */       setupCtrlPts(selectedRectInPage);
/*     */     } catch (PDFNetException ex) {
/*     */       AnalyticsHandlerAdapter.getInstance().sendException((Exception)ex);
/*     */     } 
/*     */     Rect dirtyRect = new Rect((int)this.mPt1.x, (int)this.mPt1.y, (int)this.mPt2.x, (int)this.mPt2.y);
/*     */     if (!hasAnnotSelected()) {
/*     */       resetPts();
/*     */       setNextToolModeHelper(ToolManager.ToolMode.PAN);
/*     */     } else {
/*     */       setNextToolModeHelper((ToolManager.ToolMode)getToolMode());
/*     */     } 
/*     */     this.mPdfViewCtrl.invalidate(dirtyRect);
/*     */     return skipOnUpPriorEvent(priorEventMode);
/*     */   }
/*     */   
/*     */   private RectF getAnnotPageRect(Annot annot) throws PDFNetException {
/*     */     Rect annotRect = this.mPdfViewCtrl.getPageRectForAnnot(annot, this.mDownPageNum);
/*     */     annotRect.normalize();
/*     */     return new RectF((float)Math.min(annotRect.getX1(), annotRect.getX2()), (float)Math.min(annotRect.getY1(), annotRect.getY2()), (float)Math.max(annotRect.getX1(), annotRect.getX2()), (float)Math.max(annotRect.getY1(), annotRect.getY2()));
/*     */   }
/*     */   
/*     */   public boolean onSingleTapConfirmed(MotionEvent e) {
/*     */     PointF pt = new PointF(e.getX() + this.mPdfViewCtrl.getScrollX(), e.getY() + this.mPdfViewCtrl.getScrollY());
/*     */     if (this.mSelectedArea.contains(pt.x, pt.y)) {
/*     */       showMenu(getAnnotRect());
/*     */     } else if (this.mGroupAnnots != null) {
/*     */       prepareGroupAnnots();
/*     */     } else {
/*     */       backToPan();
/*     */     } 
/*     */     return false;
/*     */   }
/*     */   
/*     */   public void onPageTurning(int old_page, int cur_page) {
/*     */     super.onPageTurning(old_page, cur_page);
/*     */     backToPan();
/*     */   }
/*     */   
/*     */   public boolean onQuickMenuClicked(QuickMenuItem menuItem) {
/*     */     if (menuItem.getItemId() == R.id.qm_group) {
/*     */       createAnnotGroup();
/*     */       return true;
/*     */     } 
/*     */     if (menuItem.getItemId() == R.id.qm_ungroup) {
/*     */       ungroupAnnots();
/*     */       return true;
/*     */     } 
/*     */     if (menuItem.getItemId() == R.id.qm_note) {
/*     */       ArrayList<Annot> annots = new ArrayList<>(this.mSelectedAnnotsMap.keySet());
/*     */       try {
/*     */         Annot primary = AnnotUtils.getPrimaryAnnotInGroup(this.mPdfViewCtrl, annots);
/*     */         if (primary != null) {
/*     */           this.mAnnot = primary;
/*     */           super.onQuickMenuClicked(menuItem);
/*     */         } 
/*     */       } catch (Exception ex) {
/*     */         AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*     */       } 
/*     */       return true;
/*     */     } 
/*     */     if (menuItem.getItemId() == R.id.qm_copy) {
/*     */       ArrayList<Annot> annots = new ArrayList<>(this.mSelectedAnnotsMap.keySet());
/*     */       if (!annots.isEmpty())
/*     */         AnnotationClipboardHelper.copyAnnot(this.mPdfViewCtrl.getContext(), annots, this.mPdfViewCtrl, new AnnotationClipboardHelper.OnClipboardTaskListener() {
/*     */               public void onnClipboardTaskDone(String error) {
/*     */                 if (error == null && AnnotEditRectGroup.this.mPdfViewCtrl.getContext() != null)
/*     */                   CommonToast.showText(AnnotEditRectGroup.this.mPdfViewCtrl.getContext(), R.string.tools_copy_annot_confirmation, 0); 
/*     */               }
/*     */             }); 
/*     */       return true;
/*     */     } 
/*     */     return super.onQuickMenuClicked(menuItem);
/*     */   }
/*     */   
/*     */   protected void customizeQuickMenuItems(QuickMenu quickMenu) {
/*     */     super.customizeQuickMenuItems(quickMenu);
/*     */     QuickMenuItem noteItem = quickMenu.findMenuItem(R.id.qm_note);
/*     */     QuickMenuItem groupItem = quickMenu.findMenuItem(R.id.qm_group);
/*     */     QuickMenuItem ungroupItem = quickMenu.findMenuItem(R.id.qm_ungroup);
/*     */     QuickMenuItem copyItem = quickMenu.findMenuItem(R.id.qm_copy);
/*     */     if (noteItem == null || ungroupItem == null || groupItem == null)
/*     */       return; 
/*     */     ArrayList<Annot> annots = new ArrayList<>(this.mSelectedAnnotsMap.keySet());
/*     */     boolean sameGroup = false;
/*     */     try {
/*     */       sameGroup = AnnotUtils.isGroupSelected(this.mPdfViewCtrl, annots, this.mDownPageNum);
/*     */     } catch (Exception ex) {
/*     */       AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*     */     } 
/*     */     if (sameGroup) {
/*     */       groupItem.setVisible(false);
/*     */     } else {
/*     */       groupItem.setVisible(true);
/*     */     } 
/*     */     if (copyItem != null)
/*     */       copyItem.setVisible(this.mCanCopy); 
/*     */     if (this.mGroupSelected) {
/*     */       noteItem.setVisible(true);
/*     */       ungroupItem.setVisible(true);
/*     */     } else {
/*     */       noteItem.setVisible(false);
/*     */       if (sameGroup) {
/*     */         ungroupItem.setVisible(true);
/*     */       } else {
/*     */         ungroupItem.setVisible(false);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void selectAnnot(Annot annot, int pageNum) {
/*     */     super.selectAnnot(annot, pageNum);
/*     */     prepareGroupAnnots();
/*     */   }
/*     */   
/*     */   private void backToPan() {
/*     */     resetPts();
/*     */     this.mSelectedArea.setEmpty();
/*     */     this.mSelectedAnnotsMap.clear();
/*     */     this.mEffCtrlPtId = -1;
/*     */     this.mAnnot = null;
/*     */     this.mGroupAnnots = null;
/*     */     this.mGroupSelected = false;
/*     */     closeQuickMenu();
/*     */     setNextToolModeHelper(ToolManager.ToolMode.PAN);
/*     */     this.mPdfViewCtrl.invalidate();
/*     */     ((ToolManager)this.mPdfViewCtrl.getToolManager()).raiseAnnotationsSelectionChangedEvent(this.mSelectedAnnotsMap);
/*     */   }
/*     */   
/*     */   private void prepareGroupAnnots() {
/*     */     if (null == this.mGroupAnnots)
/*     */       return; 
/*     */     try {
/*     */       RectF selectedRectInPage = new RectF();
/*     */       this.mAnnotIsTextMarkup = false;
/*     */       this.mDownPageNum = this.mAnnotPageNum;
/*     */       for (Annot annot : this.mGroupAnnots) {
/*     */         Rect annotRect = this.mPdfViewCtrl.getPageRectForAnnot(annot, this.mDownPageNum);
/*     */         annotRect.normalize();
/*     */         RectF annotPageRect = new RectF((float)Math.min(annotRect.getX1(), annotRect.getX2()), (float)Math.min(annotRect.getY1(), annotRect.getY2()), (float)Math.max(annotRect.getX1(), annotRect.getX2()), (float)Math.max(annotRect.getY1(), annotRect.getY2()));
/*     */         if (hasPermission(annot, 0)) {
/*     */           if (!isAnnotSupportResize(annot))
/*     */             this.mAnnotIsTextMarkup = true; 
/*     */           this.mSelectedAnnotsMap.put(annot, Integer.valueOf(this.mDownPageNum));
/*     */           selectedRectInPage.union(annotPageRect);
/*     */         } 
/*     */       } 
/*     */       this.mGroupSelected = true;
/*     */       this.mGroupAnnots = null;
/*     */       this.mAnnot = null;
/*     */       this.mForceSameNextToolMode = false;
/*     */       setupCtrlPts(selectedRectInPage);
/*     */     } catch (PDFNetException ex) {
/*     */       AnalyticsHandlerAdapter.getInstance().sendException((Exception)ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void createAnnotGroup() {
/*     */     ArrayList<Annot> annots = new ArrayList<>(this.mSelectedAnnotsMap.keySet());
/*     */     if (annots.isEmpty())
/*     */       return; 
/*     */     sort(annots);
/*     */     Annot primary = annots.get(0);
/*     */     try {
/*     */       raiseAnnotationPreModifyEvent(this.mSelectedAnnotsMap);
/*     */       AnnotUtils.createAnnotationGroup(this.mPdfViewCtrl, primary, annots);
/*     */       raiseAnnotationModifiedEvent(this.mSelectedAnnotsMap);
/*     */     } catch (Exception ex) {
/*     */       AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*     */     } 
/*     */     this.mForceSameNextToolMode = false;
/*     */     backToPan();
/*     */   }
/*     */   
/*     */   private void ungroupAnnots() {
/*     */     ArrayList<Annot> annots = new ArrayList<>(this.mSelectedAnnotsMap.keySet());
/*     */     if (annots.isEmpty())
/*     */       return; 
/*     */     try {
/*     */       raiseAnnotationPreModifyEvent(this.mSelectedAnnotsMap);
/*     */       AnnotUtils.ungroupAnnotations(this.mPdfViewCtrl, annots);
/*     */       raiseAnnotationModifiedEvent(this.mSelectedAnnotsMap);
/*     */     } catch (Exception ex) {
/*     */       AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*     */     } 
/*     */     this.mForceSameNextToolMode = false;
/*     */     backToPan();
/*     */   }
/*     */   
/*     */   private void setupCtrlPts(@NonNull RectF selectedRectInPage) {
/*     */     if (!selectedRectInPage.isEmpty()) {
/*     */       double[] pts1 = this.mPdfViewCtrl.convPagePtToScreenPt(selectedRectInPage.left, selectedRectInPage.top, this.mDownPageNum);
/*     */       double[] pts2 = this.mPdfViewCtrl.convPagePtToScreenPt(selectedRectInPage.right, selectedRectInPage.bottom, this.mDownPageNum);
/*     */       int scrollX = this.mPdfViewCtrl.getScrollX();
/*     */       int scrollY = this.mPdfViewCtrl.getScrollY();
/*     */       double minX = Math.min(pts1[0] + scrollX, pts2[0] + scrollX);
/*     */       double minY = Math.min(pts1[1] + scrollY, pts2[1] + scrollY);
/*     */       double maxX = Math.max(pts1[0] + scrollX, pts2[0] + scrollX);
/*     */       double maxY = Math.max(pts1[1] + scrollY, pts2[1] + scrollY);
/*     */       this.mSelectedArea = new RectF((float)minX, (float)minY, (float)maxX, (float)maxY);
/*     */       this.mAnnotPageNum = this.mDownPageNum;
/*     */       setCtrlPts();
/*     */       showMenu(getAnnotRect());
/*     */       ((ToolManager)this.mPdfViewCtrl.getToolManager()).raiseAnnotationsSelectionChangedEvent(this.mSelectedAnnotsMap);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected int getMenuResByAnnot(Annot annot) {
/*     */     return R.menu.annot_group;
/*     */   }
/*     */   
/*     */   protected void deleteAnnot() {
/*     */     if (this.mPdfViewCtrl == null)
/*     */       return; 
/*     */     boolean shouldUnlock = false;
/*     */     try {
/*     */       this.mPdfViewCtrl.docLock(true);
/*     */       shouldUnlock = true;
/*     */       PDFDoc pdfDoc = this.mPdfViewCtrl.getDoc();
/*     */       raiseAnnotationPreRemoveEvent(this.mSelectedAnnotsMap);
/*     */       for (Map.Entry<Annot, Integer> entry : this.mSelectedAnnotsMap.entrySet()) {
/*     */         Annot annot = entry.getKey();
/*     */         if (annot == null)
/*     */           continue; 
/*     */         int annotPageNum = ((Integer)entry.getValue()).intValue();
/*     */         Page page = pdfDoc.getPage(annotPageNum);
/*     */         page.annotRemove(annot);
/*     */         this.mPdfViewCtrl.update(annot, annotPageNum);
/*     */       } 
/*     */       raiseAnnotationRemovedEvent(this.mSelectedAnnotsMap);
/*     */     } catch (Exception e) {
/*     */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */     } finally {
/*     */       if (shouldUnlock)
/*     */         this.mPdfViewCtrl.docUnlock(); 
/*     */     } 
/*     */     backToPan();
/*     */   }
/*     */   
/*     */   protected void flattenAnnot() {
/*     */     if (this.mPdfViewCtrl == null)
/*     */       return; 
/*     */     boolean shouldUnlock = false;
/*     */     try {
/*     */       this.mPdfViewCtrl.docLock(true);
/*     */       shouldUnlock = true;
/*     */       raiseAnnotationPreModifyEvent(this.mSelectedAnnotsMap);
/*     */       for (Map.Entry<Annot, Integer> entry : this.mSelectedAnnotsMap.entrySet()) {
/*     */         Annot annot = entry.getKey();
/*     */         if (annot == null)
/*     */           continue; 
/*     */         int annotPageNum = ((Integer)entry.getValue()).intValue();
/*     */         AnnotUtils.flattenAnnot(this.mPdfViewCtrl, annot, annotPageNum);
/*     */       } 
/*     */       raiseAnnotationModifiedEvent(this.mSelectedAnnotsMap);
/*     */     } catch (Exception e) {
/*     */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */     } finally {
/*     */       if (shouldUnlock)
/*     */         this.mPdfViewCtrl.docUnlock(); 
/*     */     } 
/*     */     backToPan();
/*     */   }
/*     */   
/*     */   protected RectF getAnnotRect() {
/*     */     RectF annotsRect = new RectF(this.mSelectedArea);
/*     */     annotsRect.offset(-this.mPdfViewCtrl.getScrollX(), -this.mPdfViewCtrl.getScrollY());
/*     */     return annotsRect;
/*     */   }
/*     */   
/*     */   protected boolean editAnnotSize(PDFViewCtrl.PriorEventMode priorEventMode) {
/*     */     float x1 = (this.mCtrlPts[3]).x;
/*     */     float y1 = (this.mCtrlPts[3]).y;
/*     */     float x2 = (this.mCtrlPts[1]).x;
/*     */     float y2 = (this.mCtrlPts[1]).y;
/*     */     RectF ctrlRect = new RectF(x1, y1, x2, y2);
/*     */     if (ctrlRect.equals(this.mSelectedArea))
/*     */       return true; 
/*     */     boolean shouldUnlock = false;
/*     */     try {
/*     */       this.mPdfViewCtrl.docLock(true);
/*     */       shouldUnlock = true;
/*     */       raiseAnnotationPreModifyEvent(this.mSelectedAnnotsMap);
/*     */       updateSelectedAnnotSize();
/*     */       raiseAnnotationModifiedEvent(this.mSelectedAnnotsMap);
/*     */     } catch (Exception ex) {
/*     */       AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*     */     } finally {
/*     */       if (shouldUnlock)
/*     */         this.mPdfViewCtrl.docUnlock(); 
/*     */     } 
/*     */     return true;
/*     */   }
/*     */   
/*     */   private void updateSelectedAnnotSize() throws PDFNetException {
/*     */     int scrollX = this.mPdfViewCtrl.getScrollX();
/*     */     int scrollY = this.mPdfViewCtrl.getScrollY();
/*     */     float x1 = (this.mCtrlPts[3]).x - scrollX;
/*     */     float y1 = (this.mCtrlPts[3]).y - scrollY;
/*     */     float x2 = (this.mCtrlPts[1]).x - scrollX;
/*     */     float y2 = (this.mCtrlPts[1]).y - scrollY;
/*     */     RectF selectedArea = new RectF(this.mSelectedArea);
/*     */     selectedArea.offset(-scrollX, -scrollY);
/*     */     double[] ctrlPagePt1 = this.mPdfViewCtrl.convScreenPtToPagePt(x1, y1, this.mDownPageNum);
/*     */     double[] ctrlPagePt2 = this.mPdfViewCtrl.convScreenPtToPagePt(x2, y2, this.mDownPageNum);
/*     */     double[] selectedPagePt1 = this.mPdfViewCtrl.convScreenPtToPagePt(selectedArea.left, selectedArea.top, this.mDownPageNum);
/*     */     double[] selectedPagePt2 = this.mPdfViewCtrl.convScreenPtToPagePt(selectedArea.right, selectedArea.bottom, this.mDownPageNum);
/*     */     float offsetX = (float)ctrlPagePt1[0];
/*     */     float offsetY = (float)ctrlPagePt2[1];
/*     */     float scaleX = (float)((ctrlPagePt2[0] - ctrlPagePt1[0]) / (selectedPagePt2[0] - selectedPagePt1[0]));
/*     */     float scaleY = (float)((ctrlPagePt2[1] - ctrlPagePt1[1]) / (selectedPagePt2[1] - selectedPagePt1[1]));
/*     */     RectF nextSelectArea = new RectF();
/*     */     for (Map.Entry<Annot, Integer> entry : this.mSelectedAnnotsMap.entrySet()) {
/*     */       Annot annot = entry.getKey();
/*     */       int annotPageNum = ((Integer)entry.getValue()).intValue();
/*     */       if (!isAnnotSupportResize(annot))
/*     */         continue; 
/*     */       Rect rect = this.mPdfViewCtrl.getPageRectForAnnot(annot, annotPageNum);
/*     */       rect.normalize();
/*     */       RectF annotRect = new RectF((float)rect.getX1(), (float)rect.getY1(), (float)rect.getX2(), (float)rect.getY2());
/*     */       annotRect.offset((float)-selectedPagePt1[0], (float)-selectedPagePt2[1]);
/*     */       Rect newAnnotRect = new Rect(annotRect.left * scaleX + offsetX, annotRect.top * scaleY + offsetY, annotRect.right * scaleX + offsetX, annotRect.bottom * scaleY + offsetY);
/*     */       newAnnotRect.normalize();
/*     */       if (annot.getType() != 12 && annot.getType() != 0)
/*     */         annot.refreshAppearance(); 
/*     */       annot.resize(newAnnotRect);
/*     */       if (annot.getType() != 12)
/*     */         AnnotUtils.refreshAnnotAppearance(this.mPdfViewCtrl.getContext(), annot); 
/*     */       this.mPdfViewCtrl.update(annot, annotPageNum);
/*     */       Rect newAnnotScreenRect = this.mPdfViewCtrl.getScreenRectForAnnot(annot, annotPageNum);
/*     */       nextSelectArea.union(new RectF((float)newAnnotScreenRect.getX1(), (float)newAnnotScreenRect.getY1(), (float)newAnnotScreenRect.getX2(), (float)newAnnotScreenRect.getY2()));
/*     */     } 
/*     */     if (!this.mPdfViewCtrl.isAnnotationLayerEnabled())
/*     */       this.mPdfViewCtrl.update(new Rect(selectedArea.left, selectedArea.top, selectedArea.right, selectedArea.bottom)); 
/*     */     nextSelectArea.offset(scrollX, scrollY);
/*     */     this.mSelectedArea.set(nextSelectArea);
/*     */     this.mPt1.set(this.mSelectedArea.left, this.mSelectedArea.top);
/*     */     this.mPt2.set(this.mSelectedArea.right, this.mSelectedArea.bottom);
/*     */     setCtrlPts();
/*     */   }
/*     */   
/*     */   private boolean isAnnotSupportEdit(Annot annot) throws PDFNetException {
/*     */     return (annot.isValid() && ((annot.getType() != 19 && annot.getType() != 1) || isMadeByPDFTron(annot)));
/*     */   }
/*     */   
/*     */   private boolean isAnnotSupportResize(Annot annot) throws PDFNetException {
/*     */     return (annot.getType() != 8 && annot.getType() != 11 && annot.getType() != 9 && annot.getType() != 10 && annot.getType() != 0 && annot.getType() != 12);
/*     */   }
/*     */   
/*     */   private boolean isAnnotSupportCopy(Annot annot) throws PDFNetException {
/*     */     return (annot.getType() != 8 && annot.getType() != 11 && annot.getType() != 9 && annot.getType() != 10);
/*     */   }
/*     */   
/*     */   private void resetPts() {
/*     */     this.mPt1.set(0.0F, 0.0F);
/*     */     this.mPt2.set(0.0F, 0.0F);
/*     */     this.mBBox.setEmpty();
/*     */   }
/*     */   
/*     */   private Rect getShapeBBox() {
/*     */     double[] pts1 = this.mPdfViewCtrl.convScreenPtToPagePt((this.mPt1.x - this.mPdfViewCtrl.getScrollX()), (this.mPt1.y - this.mPdfViewCtrl.getScrollY()), this.mDownPageNum);
/*     */     double[] pts2 = this.mPdfViewCtrl.convScreenPtToPagePt((this.mPt2.x - this.mPdfViewCtrl.getScrollX()), (this.mPt2.y - this.mPdfViewCtrl.getScrollY()), this.mDownPageNum);
/*     */     try {
/*     */       Rect rect = new Rect(pts1[0], pts1[1], pts2[0], pts2[1]);
/*     */       rect.normalize();
/*     */       return rect;
/*     */     } catch (Exception e) {
/*     */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected RectF getScreenRect(Rect screen_rect) {
/*     */     if (hasAnnotSelected() && this.mSelectedAnnotsMap.size() == 1)
/*     */       return super.getScreenRect(screen_rect); 
/*     */     return new RectF(this.mSelectedArea);
/*     */   }
/*     */   
/*     */   public void onDraw(Canvas canvas, Matrix tfm) {
/*     */     if (this.mAllowTwoFingerScroll)
/*     */       return; 
/*     */     if (!this.mSelectedArea.isEmpty()) {
/*     */       super.onDraw(canvas, tfm);
/*     */       if (isAnnotResizable() && !this.mHandleEffCtrlPtsDisabled)
/*     */         DrawingUtils.drawCtrlPts(this.mPdfViewCtrl.getResources(), canvas, this.mPaint, this.mCtrlPts[3], this.mCtrlPts[1], this.mCtrlPts[6], this.mCtrlPts[7], this.mCtrlRadius, this.mHasSelectionPermission, this.mMaintainAspectRatio); 
/*     */     } else {
/*     */       int min_x = (int)Math.min(this.mPt1.x, this.mPt2.x);
/*     */       int max_x = (int)Math.max(this.mPt1.x, this.mPt2.x);
/*     */       int min_y = (int)Math.min(this.mPt1.y, this.mPt2.y);
/*     */       int max_y = (int)Math.max(this.mPt1.y, this.mPt2.y);
/*     */       Rect fillRect = new Rect(min_x, min_y, max_x, max_y);
/*     */       if (!fillRect.isEmpty())
/*     */         canvas.drawRect(fillRect, this.mFillPaint); 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean hasAnnotSelected() {
/*     */     return !this.mSelectedAnnotsMap.isEmpty();
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\AnnotEditRectGroup.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */