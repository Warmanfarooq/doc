/*     */ package com.pdftron.pdf.tools;
/*     */ 
/*     */ import androidx.annotation.Keep;
/*     */ import androidx.annotation.NonNull;
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.Point;
/*     */ import com.pdftron.pdf.Rect;
/*     */ import com.pdftron.pdf.annots.FreeText;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Keep
/*     */ public class CalloutCreate
/*     */   extends FreeTextCreate
/*     */ {
/*  22 */   private static int THRESHOLD = 40;
/*     */   
/*     */   private Point mStart;
/*     */   
/*     */   private Point mKnee;
/*     */   
/*     */   private Point mEnd;
/*     */   
/*     */   private Rect mContentRect;
/*     */   
/*     */   public CalloutCreate(@NonNull PDFViewCtrl ctrl) {
/*  33 */     super(ctrl);
/*     */   }
/*     */ 
/*     */   
/*     */   public ToolManager.ToolModeBase getToolMode() {
/*  38 */     return ToolManager.ToolMode.CALLOUT_CREATE;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCreateAnnotType() {
/*  43 */     return 1007;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createFreeText() {
/*     */     try {
/*  49 */       preCreateCallout();
/*     */ 
/*     */       
/*  52 */       boolean shouldUnlock = false;
/*     */       try {
/*  54 */         this.mPdfViewCtrl.docLock(true);
/*  55 */         shouldUnlock = true;
/*  56 */         createAnnot("");
/*  57 */         raiseAnnotationAddedEvent(this.mAnnot, this.mAnnotPageNum);
/*  58 */       } catch (Exception e) {
/*  59 */         ((ToolManager)this.mPdfViewCtrl.getToolManager()).annotationCouldNotBeAdded(e.getMessage());
/*  60 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */       } finally {
/*  62 */         if (shouldUnlock) {
/*  63 */           this.mPdfViewCtrl.docUnlock();
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/*  68 */       this.mNextToolMode = ToolManager.ToolMode.ANNOT_EDIT_ADVANCED_SHAPE;
/*  69 */       setCurrentDefaultToolModeHelper(getToolMode());
/*  70 */       this.mUpFromCalloutCreate = this.mOnUpOccurred;
/*  71 */     } catch (Exception ex) {
/*  72 */       AnalyticsHandlerAdapter.getInstance().sendException(ex, "CalloutCreate::createFreeText");
/*     */     } 
/*     */   }
/*     */   
/*     */   private void preCreateCallout() throws PDFNetException {
/*  77 */     if (this.mTargetPointPageSpace == null) {
/*     */       return;
/*     */     }
/*  80 */     Rect pageRect = Utils.getPageRect(this.mPdfViewCtrl, this.mPageNum);
/*  81 */     if (pageRect != null) {
/*  82 */       pageRect.normalize();
/*     */       
/*  84 */       this.mStart = new Point(this.mTargetPointPageSpace.x, this.mTargetPointPageSpace.y);
/*  85 */       this.mKnee = calcCalloutKneePt(pageRect);
/*  86 */       this.mEnd = calcCalloutEndPt(pageRect);
/*  87 */       this.mContentRect = calcCalloutContentRect(pageRect);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected Rect getFreeTextBBox(FreeText freeText, boolean isRightToLeft) throws PDFNetException {
/*  93 */     return this.mContentRect;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setExtraFreeTextProps(FreeText freetext, Rect bbox) throws PDFNetException {
/*  98 */     super.setExtraFreeTextProps(freetext, bbox);
/*     */     
/* 100 */     if (this.mStart == null || this.mKnee == null || this.mEnd == null) {
/*     */       return;
/*     */     }
/*     */     
/* 104 */     freetext.setCalloutLinePoints(this.mStart, this.mKnee, this.mEnd);
/*     */     
/* 106 */     freetext.setEndingStyle(3);
/*     */     
/* 108 */     freetext.setContentRect(bbox);
/* 109 */     freetext.setIntentName(1);
/*     */   }
/*     */ 
/*     */   
/*     */   private Point calcCalloutKneePt(Rect pageRect) throws PDFNetException {
/* 114 */     double x, y, pageX1 = pageRect.getX1();
/* 115 */     double pageX2 = pageRect.getX2();
/* 116 */     double pageMidX = (pageX1 + pageX2) / 2.0D;
/* 117 */     double pageY1 = pageRect.getY1();
/* 118 */     double pageY2 = pageRect.getY2();
/* 119 */     double pageMidY = (pageY1 + pageY2) / 2.0D;
/*     */     
/* 121 */     if (this.mStart.x > pageMidX) {
/* 122 */       if (this.mStart.y > pageMidY) {
/* 123 */         x = this.mStart.x - THRESHOLD;
/* 124 */         y = this.mStart.y - THRESHOLD;
/*     */       } else {
/* 126 */         x = this.mStart.x - THRESHOLD;
/* 127 */         y = this.mStart.y + THRESHOLD;
/*     */       }
/*     */     
/* 130 */     } else if (this.mStart.y > pageMidY) {
/* 131 */       x = this.mStart.x + THRESHOLD;
/* 132 */       y = this.mStart.y - THRESHOLD;
/*     */     } else {
/* 134 */       x = this.mStart.x + THRESHOLD;
/* 135 */       y = this.mStart.y + THRESHOLD;
/*     */     } 
/*     */     
/* 138 */     return new Point(x, y);
/*     */   }
/*     */   
/*     */   private Point calcCalloutEndPt(Rect pageRect) throws PDFNetException {
/* 142 */     double x = (this.mStart.x > this.mKnee.x) ? Math.min(this.mKnee.x - THRESHOLD, pageRect.getX2()) : Math.max(this.mKnee.x + THRESHOLD, pageRect.getX1());
/* 143 */     double y = this.mKnee.y;
/*     */     
/* 145 */     return new Point(x, y);
/*     */   }
/*     */   
/*     */   private Rect calcCalloutContentRect(Rect pageRect) throws PDFNetException {
/* 149 */     double x1 = (this.mEnd.x > this.mKnee.x) ? this.mEnd.x : Math.max(this.mEnd.x - (THRESHOLD * 2), pageRect.getX1());
/* 150 */     double y1 = Math.max(this.mEnd.y - (THRESHOLD / 2), pageRect.getY1());
/* 151 */     double x2 = (this.mEnd.x > this.mKnee.x) ? Math.min(x1 + (THRESHOLD * 2), pageRect.getX2()) : this.mEnd.x;
/* 152 */     double y2 = Math.min(y1 + THRESHOLD, pageRect.getY2());
/* 153 */     return new Rect(x1, y1, x2, y2);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\CalloutCreate.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */