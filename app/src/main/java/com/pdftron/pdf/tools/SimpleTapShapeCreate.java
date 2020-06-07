/*     */ package com.pdftron.pdf.tools;
/*     */ 
/*     */ import android.content.SharedPreferences;
/*     */ import android.graphics.PointF;
/*     */ import android.view.MotionEvent;
/*     */ import androidx.annotation.Keep;
/*     */ import androidx.annotation.NonNull;
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.Annot;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.Page;
/*     */ import com.pdftron.pdf.Rect;
/*     */ import com.pdftron.pdf.config.ToolStyleConfig;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ 
/*     */ @Keep
/*     */ public abstract class SimpleTapShapeCreate
/*     */   extends SimpleShapeCreate
/*     */ {
/*     */   private static final int ICON_SIZE = 25;
/*  24 */   protected int mIconSize = 25;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleTapShapeCreate(@NonNull PDFViewCtrl ctrl) {
/*  30 */     super(ctrl);
/*  31 */     this.mNextToolMode = getToolMode();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onDown(MotionEvent e) {
/*  36 */     this.mAnnotPushedBack = false;
/*  37 */     return super.onDown(e);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onUp(MotionEvent e, PDFViewCtrl.PriorEventMode priorEventMode) {
/*  45 */     if (this.mAnnotPushedBack) {
/*  46 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  50 */     if (this.mAllowTwoFingerScroll) {
/*  51 */       doneTwoFingerScrolling();
/*  52 */       return false;
/*     */     } 
/*     */     
/*  55 */     ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/*     */ 
/*     */     
/*  58 */     if (toolManager.isQuickMenuJustClosed()) {
/*  59 */       return true;
/*     */     }
/*     */     
/*  62 */     if (priorEventMode == PDFViewCtrl.PriorEventMode.PAGE_SLIDING) {
/*  63 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  67 */     if (priorEventMode == PDFViewCtrl.PriorEventMode.FLING || priorEventMode == PDFViewCtrl.PriorEventMode.PINCH)
/*     */     {
/*     */       
/*  70 */       return true;
/*     */     }
/*     */     
/*  73 */     boolean shouldCreate = true;
/*  74 */     int x = (int)e.getX();
/*  75 */     int y = (int)e.getY();
/*  76 */     ArrayList<Annot> annots = this.mPdfViewCtrl.getAnnotationListAt(x, y, x, y);
/*  77 */     int page = this.mPdfViewCtrl.getPageNumberFromScreenPt(x, y);
/*     */     try {
/*  79 */       for (Annot annot : annots) {
/*  80 */         if (annot.isValid() && annot.getType() == getCreateAnnotType()) {
/*  81 */           shouldCreate = false;
/*     */           
/*  83 */           toolManager.selectAnnot(annot, page);
/*     */           break;
/*     */         } 
/*     */       } 
/*  87 */     } catch (PDFNetException ex) {
/*  88 */       AnalyticsHandlerAdapter.getInstance().sendException((Exception)ex);
/*     */     } 
/*     */     
/*  91 */     if (shouldCreate && page > 0) {
/*  92 */       this.mPt2 = new PointF(e.getX(), e.getY());
/*  93 */       addAnnotation();
/*  94 */       return true;
/*     */     } 
/*  96 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTargetPoint(PointF point, boolean createAnnot) {
/* 107 */     this.mPt2.x = point.x;
/* 108 */     this.mPt2.y = point.y;
/* 109 */     this.mDownPageNum = this.mPdfViewCtrl.getPageNumberFromScreenPt(point.x, point.y);
/*     */     
/* 111 */     if (createAnnot) {
/* 112 */       addAnnotation();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void addAnnotation();
/*     */ 
/*     */   
/*     */   protected boolean createAnnotation(PointF targetPoint, int pageNum) {
/* 122 */     SharedPreferences settings = Tool.getToolPreferences(this.mPdfViewCtrl.getContext());
/* 123 */     this.mStrokeColor = settings.getInt(getColorKey(getCreateAnnotType()), ToolStyleConfig.getInstance().getDefaultColor(this.mPdfViewCtrl.getContext(), getCreateAnnotType()));
/* 124 */     this.mOpacity = settings.getFloat(getOpacityKey(getCreateAnnotType()), ToolStyleConfig.getInstance().getDefaultOpacity(this.mPdfViewCtrl.getContext(), getCreateAnnotType()));
/*     */     
/* 126 */     boolean success = false;
/* 127 */     boolean shouldUnlock = false;
/*     */     try {
/* 129 */       this.mPdfViewCtrl.docLock(true);
/* 130 */       shouldUnlock = true;
/* 131 */       Rect rect = getBBox(targetPoint, pageNum);
/* 132 */       if (rect != null) {
/* 133 */         Annot annot = createMarkup(this.mPdfViewCtrl.getDoc(), rect);
/* 134 */         setStyle(annot);
/* 135 */         Page page = this.mPdfViewCtrl.getDoc().getPage(pageNum);
/* 136 */         if (annot != null && page != null) {
/* 137 */           annot.refreshAppearance();
/* 138 */           page.annotPushBack(annot);
/* 139 */           this.mAnnotPushedBack = true;
/* 140 */           setAnnot(annot, pageNum);
/* 141 */           buildAnnotBBox();
/* 142 */           this.mPdfViewCtrl.update(this.mAnnot, this.mAnnotPageNum);
/* 143 */           raiseAnnotationAddedEvent(this.mAnnot, this.mAnnotPageNum);
/* 144 */           success = true;
/*     */         } 
/*     */       } 
/* 147 */     } catch (Exception ex) {
/* 148 */       this.mNextToolMode = ToolManager.ToolMode.PAN;
/* 149 */       ((ToolManager)this.mPdfViewCtrl.getToolManager()).annotationCouldNotBeAdded(ex.getMessage());
/* 150 */       AnalyticsHandlerAdapter.getInstance().sendException(ex);
/* 151 */       onCreateMarkupFailed(ex);
/*     */     } finally {
/* 153 */       if (shouldUnlock) {
/* 154 */         this.mPdfViewCtrl.docUnlock();
/*     */       }
/* 156 */       clearTargetPoint();
/* 157 */       setNextToolModeHelper();
/*     */     } 
/* 159 */     return success;
/*     */   }
/*     */   
/*     */   protected Rect getBBox(PointF targetPoint, int pageNum) throws PDFNetException {
/* 163 */     if (targetPoint == null) {
/* 164 */       return null;
/*     */     }
/* 166 */     double[] pts = this.mPdfViewCtrl.convScreenPtToPagePt(targetPoint.x, targetPoint.y, pageNum);
/* 167 */     return new Rect(pts[0], pts[1], pts[0] + this.mIconSize, pts[1] + this.mIconSize);
/*     */   }
/*     */   
/*     */   protected void setNextToolModeHelper() {
/* 171 */     ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 172 */     if (toolManager.isAutoSelectAnnotation() || !this.mForceSameNextToolMode) {
/* 173 */       this.mNextToolMode = getDefaultNextTool();
/*     */     } else {
/* 175 */       this.mNextToolMode = getToolMode();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\SimpleTapShapeCreate.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */