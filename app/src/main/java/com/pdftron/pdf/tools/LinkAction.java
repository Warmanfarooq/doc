/*     */ package com.pdftron.pdf.tools;
/*     */ 
/*     */ import android.graphics.Canvas;
/*     */ import android.graphics.Matrix;
/*     */ import android.graphics.Paint;
/*     */ import android.view.MotionEvent;
/*     */ import androidx.annotation.Keep;
/*     */ import androidx.annotation.NonNull;
/*     */ import com.pdftron.pdf.Action;
/*     */ import com.pdftron.pdf.ActionParameter;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.QuadPoint;
/*     */ import com.pdftron.pdf.Rect;
/*     */ import com.pdftron.pdf.annots.Link;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.AnnotUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class LinkAction
/*     */   extends Tool
/*     */ {
/*     */   private Link mLink;
/*     */   private Paint mPaint;
/*     */   
/*     */   public LinkAction(@NonNull PDFViewCtrl ctrl) {
/*  38 */     super(ctrl);
/*  39 */     this.mPaint = new Paint();
/*  40 */     this.mPaint.setAntiAlias(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolManager.ToolModeBase getToolMode() {
/*  48 */     return ToolManager.ToolMode.LINK_ACTION;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCreateAnnotType() {
/*  53 */     return 28;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onSingleTapConfirmed(MotionEvent e) {
/*  61 */     if (this.mAnnot != null) {
/*  62 */       this.mNextToolMode = ToolManager.ToolMode.LINK_ACTION;
/*  63 */       boolean shouldUnlockRead = false;
/*     */       try {
/*  65 */         this.mPdfViewCtrl.docLockRead();
/*  66 */         shouldUnlockRead = true;
/*  67 */         this.mLink = new Link(this.mAnnot);
/*  68 */         this.mPdfViewCtrl.invalidate();
/*  69 */       } catch (Exception ex) {
/*  70 */         AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*     */       } finally {
/*  72 */         if (shouldUnlockRead) {
/*  73 */           this.mPdfViewCtrl.docUnlockRead();
/*     */         }
/*     */       } 
/*     */     } else {
/*  77 */       this.mNextToolMode = ToolManager.ToolMode.PAN;
/*     */     } 
/*  79 */     return false;
/*     */   }
/*     */   
/*     */   private boolean handleLink() {
/*  83 */     if (onInterceptAnnotationHandling(this.mAnnot)) {
/*  84 */       return true;
/*     */     }
/*  86 */     if (this.mLink == null) {
/*  87 */       return false;
/*     */     }
/*     */     
/*  90 */     this.mNextToolMode = this.mCurrentDefaultToolMode;
/*     */     
/*  92 */     Action a = null;
/*  93 */     boolean shouldUnlockRead = false;
/*     */     try {
/*  95 */       this.mPdfViewCtrl.docLockRead();
/*  96 */       shouldUnlockRead = true;
/*  97 */       a = this.mLink.getAction();
/*  98 */     } catch (Exception e) {
/*  99 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */     } finally {
/* 101 */       if (shouldUnlockRead) {
/* 102 */         this.mPdfViewCtrl.docUnlockRead();
/*     */       }
/*     */     } 
/* 105 */     if (a != null) {
/* 106 */       shouldUnlockRead = false;
/* 107 */       boolean shouldUnlock = false;
/* 108 */       boolean hasChanges = false;
/*     */       try {
/* 110 */         if (a.needsWriteLock()) {
/* 111 */           this.mPdfViewCtrl.docLock(true);
/* 112 */           shouldUnlock = true;
/*     */         } else {
/* 114 */           this.mPdfViewCtrl.docLockRead();
/* 115 */           shouldUnlockRead = true;
/*     */         } 
/* 117 */         ActionParameter action_param = new ActionParameter(a);
/* 118 */         executeAction(action_param);
/* 119 */         this.mPdfViewCtrl.invalidate();
/* 120 */         hasChanges = this.mPdfViewCtrl.getDoc().hasChangesSinceSnapshot();
/* 121 */       } catch (Exception ex) {
/* 122 */         AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*     */       } finally {
/* 124 */         if (shouldUnlock) {
/* 125 */           this.mPdfViewCtrl.docUnlock();
/*     */         }
/* 127 */         if (shouldUnlockRead) {
/* 128 */           this.mPdfViewCtrl.docUnlockRead();
/*     */         }
/* 130 */         if (hasChanges) {
/* 131 */           raiseAnnotationActionEvent();
/*     */         }
/*     */       } 
/*     */     } 
/* 135 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPostSingleTapConfirmed() {
/* 143 */     handleLink();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onLongPress(MotionEvent e) {
/* 151 */     if (this.mAnnot != null) {
/* 152 */       this.mNextToolMode = ToolManager.ToolMode.LINK_ACTION;
/* 153 */       boolean shouldUnlockRead = false;
/*     */       try {
/* 155 */         this.mPdfViewCtrl.docLockRead();
/* 156 */         shouldUnlockRead = true;
/* 157 */         this.mLink = new Link(this.mAnnot);
/* 158 */         this.mPdfViewCtrl.invalidate();
/* 159 */       } catch (Exception ex) {
/* 160 */         AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*     */       } finally {
/* 162 */         if (shouldUnlockRead) {
/* 163 */           this.mPdfViewCtrl.docUnlockRead();
/*     */         }
/*     */       } 
/*     */     } else {
/* 167 */       this.mNextToolMode = ToolManager.ToolMode.PAN;
/*     */     } 
/*     */     
/* 170 */     this.mAvoidLongPressAttempt = true;
/*     */     
/* 172 */     handleLink();
/*     */     
/* 174 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDraw(Canvas canvas, Matrix tfm) {
/*     */     try {
/* 183 */       int qn = this.mLink.getQuadPointCount();
/* 184 */       float sx = this.mPdfViewCtrl.getScrollX();
/* 185 */       float sy = this.mPdfViewCtrl.getScrollY();
/* 186 */       for (int i = 0; i < qn; i++) {
/* 187 */         QuadPoint qp = this.mLink.getQuadPoint(i);
/* 188 */         Rect quadRect = AnnotUtils.quadToRect(qp);
/* 189 */         double[] pts1 = this.mPdfViewCtrl.convPagePtToScreenPt(quadRect.getX1(), quadRect.getY1(), this.mAnnotPageNum);
/* 190 */         double[] pts2 = this.mPdfViewCtrl.convPagePtToScreenPt(quadRect.getX2(), quadRect.getY2(), this.mAnnotPageNum);
/* 191 */         double minX = Math.min(pts1[0], pts2[0]);
/* 192 */         double minY = Math.min(pts1[1], pts2[1]);
/* 193 */         double maxX = Math.max(pts1[0], pts2[0]);
/* 194 */         double maxY = Math.max(pts1[1], pts2[1]);
/*     */         
/* 196 */         float left = (float)minX + sx;
/* 197 */         float top = (float)minY + sy;
/* 198 */         float right = (float)maxX + sx;
/* 199 */         float bottom = (float)maxY + sy;
/*     */         
/* 201 */         this.mPaint.setStyle(Paint.Style.FILL);
/* 202 */         this.mPaint.setColor(this.mPdfViewCtrl.getResources().getColor(R.color.tools_link_fill));
/* 203 */         canvas.drawRect(left, top, right, bottom, this.mPaint);
/*     */         
/* 205 */         float len = Math.min(right - left, bottom - top);
/* 206 */         this.mPaint.setStyle(Paint.Style.STROKE);
/* 207 */         this.mPaint.setStrokeWidth(Math.max(len / 15.0F, 2.0F));
/* 208 */         this.mPaint.setColor(this.mPdfViewCtrl.getResources().getColor(R.color.tools_link_stroke));
/* 209 */         canvas.drawRect(left, top, right, bottom, this.mPaint);
/*     */       } 
/* 211 */     } catch (Exception exception) {}
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\LinkAction.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */