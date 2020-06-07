/*     */ package com.pdftron.pdf.tools;
/*     */ 
/*     */ import android.graphics.Canvas;
/*     */ import android.graphics.Matrix;
/*     */ import android.view.MotionEvent;
/*     */ import androidx.annotation.Keep;
/*     */ import androidx.annotation.NonNull;
/*     */ import com.pdftron.pdf.Annot;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.Rect;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class RectLinkCreate
/*     */   extends RectCreate
/*     */ {
/*     */   public RectLinkCreate(@NonNull PDFViewCtrl ctrl) {
/*  32 */     super(ctrl);
/*  33 */     this.mNextToolMode = ToolManager.ToolMode.RECT_LINK;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolManager.ToolModeBase getToolMode() {
/*  41 */     return ToolManager.ToolMode.RECT_LINK;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCreateAnnotType() {
/*  46 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onUp(MotionEvent e, PDFViewCtrl.PriorEventMode priorEventMode) {
/*  55 */     if (this.mAllowTwoFingerScroll) {
/*  56 */       doneTwoFingerScrolling();
/*  57 */       return false;
/*     */     } 
/*     */     
/*  60 */     if (priorEventMode == PDFViewCtrl.PriorEventMode.PAGE_SLIDING) {
/*  61 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  65 */     if (this.mPt1.x == this.mPt2.x && this.mPt1.y == this.mPt2.y) {
/*  66 */       this.mPt1.set(0.0F, 0.0F);
/*  67 */       this.mPt2.set(0.0F, 0.0F);
/*  68 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  73 */     if (this.mAnnotPushedBack && this.mForceSameNextToolMode) {
/*  74 */       return true;
/*     */     }
/*     */ 
/*     */     
/*  78 */     if (this.mIsAllPointsOutsidePage) {
/*  79 */       return true;
/*     */     }
/*     */     try {
/*  82 */       if (!this.mForceSameNextToolMode) {
/*  83 */         this.mNextToolMode = ToolManager.ToolMode.PAN;
/*     */       } else {
/*  85 */         this.mNextToolMode = getToolMode();
/*     */       } 
/*  87 */       setCurrentDefaultToolModeHelper(getToolMode());
/*     */ 
/*     */ 
/*     */       
/*  91 */       Rect rect = getShapeBBox();
/*  92 */       if (rect != null) {
/*  93 */         HashMap<Rect, Integer> annotInfo = new HashMap<>();
/*  94 */         annotInfo.put(rect, Integer.valueOf(this.mDownPageNum));
/*  95 */         DialogLinkEditor linkEditorDialog = new DialogLinkEditor(this.mPdfViewCtrl, this, annotInfo);
/*  96 */         linkEditorDialog.setColor(this.mStrokeColor);
/*  97 */         linkEditorDialog.setThickness(this.mThickness);
/*  98 */         linkEditorDialog.show();
/*  99 */         this.mPt1.set(0.0F, 0.0F);
/* 100 */         this.mPt2.set(0.0F, 0.0F);
/* 101 */         this.mAnnotPushedBack = true;
/*     */       }
/*     */     
/* 104 */     } catch (Exception ex) {
/* 105 */       this.mNextToolMode = ToolManager.ToolMode.PAN;
/* 106 */       ((ToolManager)this.mPdfViewCtrl.getToolManager()).annotationCouldNotBeAdded(ex.getMessage());
/* 107 */       AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*     */     } 
/*     */ 
/*     */     
/* 111 */     return skipOnUpPriorEvent(priorEventMode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onSingleTapConfirmed(MotionEvent e) {
/* 119 */     unsetAnnot();
/* 120 */     return super.onSingleTapConfirmed(e);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onLongPress(MotionEvent e) {
/* 125 */     unsetAnnot();
/* 126 */     if (this.mForceSameNextToolMode) {
/*     */       
/* 128 */       int x = (int)(e.getX() + 0.5D);
/* 129 */       int y = (int)(e.getY() + 0.5D);
/* 130 */       selectAnnot(x, y);
/*     */       
/* 132 */       boolean shouldUnlockRead = false;
/*     */       try {
/* 134 */         this.mPdfViewCtrl.docLockRead();
/* 135 */         shouldUnlockRead = true;
/*     */ 
/*     */         
/* 138 */         if (this.mAnnot != null && this.mAnnot.getType() == 1 && 
/* 139 */           isMadeByPDFTron(this.mAnnot)) {
/* 140 */           this.mNextToolMode = ToolManager.ToolMode.ANNOT_EDIT;
/* 141 */           ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 142 */           ToolManager.Tool tool = toolManager.createTool(this.mNextToolMode, null);
/* 143 */           tool.onLongPress(e);
/*     */           
/* 145 */           if (this.mNextToolMode != tool.getNextToolMode()) {
/* 146 */             this.mNextToolMode = tool.getNextToolMode();
/*     */           } else {
/* 148 */             this.mNextToolMode = getToolMode();
/*     */           }
/*     */         
/*     */         } 
/* 152 */       } catch (Exception ex) {
/* 153 */         AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*     */       } finally {
/* 155 */         if (shouldUnlockRead) {
/* 156 */           this.mPdfViewCtrl.docUnlockRead();
/*     */         }
/*     */       } 
/*     */     } 
/* 160 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDraw(Canvas canvas, Matrix tfm) {
/* 166 */     super.onDraw(canvas, tfm);
/*     */   }
/*     */ 
/*     */   
/*     */   private void selectAnnot(int x, int y) {
/* 171 */     this.mAnnotPageNum = 0;
/*     */     
/* 173 */     this.mPdfViewCtrl.cancelFindText();
/* 174 */     boolean shouldUnlockRead = false;
/*     */     try {
/* 176 */       this.mPdfViewCtrl.docLockRead();
/* 177 */       shouldUnlockRead = true;
/* 178 */       Annot a = this.mPdfViewCtrl.getAnnotationAt(x, y);
/*     */       
/* 180 */       if (a != null && a.isValid()) {
/* 181 */         setAnnot(a, this.mPdfViewCtrl.getPageNumberFromScreenPt(x, y));
/* 182 */         buildAnnotBBox();
/*     */       } 
/* 184 */     } catch (Exception ex) {
/* 185 */       AnalyticsHandlerAdapter.getInstance().sendException(ex, "RectLinCreate failed to selectAnnot");
/*     */     } finally {
/* 187 */       if (shouldUnlockRead)
/* 188 */         this.mPdfViewCtrl.docUnlockRead(); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\RectLinkCreate.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */