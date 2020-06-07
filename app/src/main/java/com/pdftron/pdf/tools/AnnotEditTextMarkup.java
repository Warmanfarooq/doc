/*     */ package com.pdftron.pdf.tools;
/*     */ 
/*     */ import android.content.res.Configuration;
/*     */ import android.graphics.Canvas;
/*     */ import android.graphics.Matrix;
/*     */ import android.graphics.RectF;
/*     */ import android.os.Bundle;
/*     */ import android.view.KeyEvent;
/*     */ import android.view.MotionEvent;
/*     */ import androidx.annotation.Keep;
/*     */ import androidx.annotation.NonNull;
/*     */ import com.pdftron.pdf.Annot;
/*     */ import com.pdftron.pdf.PDFDoc;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.Page;
/*     */ import com.pdftron.pdf.Point;
/*     */ import com.pdftron.pdf.QuadPoint;
/*     */ import com.pdftron.pdf.Rect;
/*     */ import com.pdftron.pdf.annots.Highlight;
/*     */ import com.pdftron.pdf.annots.Markup;
/*     */ import com.pdftron.pdf.annots.Popup;
/*     */ import com.pdftron.pdf.annots.Squiggly;
/*     */ import com.pdftron.pdf.annots.StrikeOut;
/*     */ import com.pdftron.pdf.annots.TextMarkup;
/*     */ import com.pdftron.pdf.annots.Underline;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.ShortcutHelper;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import com.pdftron.sdf.Doc;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedList;
/*     */ import java.util.Map;
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
/*     */ public class AnnotEditTextMarkup
/*     */   extends TextSelect
/*     */   implements DialogAnnotNote.DialogAnnotNoteListener
/*     */ {
/*  47 */   private static final String TAG = AnnotEditTextMarkup.class.getName();
/*     */   
/*     */   private boolean mScaled;
/*     */   
/*     */   private boolean mModifiedAnnot;
/*     */   
/*     */   private boolean mCtrlPtsSet;
/*     */   private boolean mOnUpCalled = false;
/*     */   
/*     */   public AnnotEditTextMarkup(@NonNull PDFViewCtrl ctrl) {
/*  57 */     super(ctrl);
/*     */     
/*  59 */     this.mScaled = false;
/*  60 */     this.mModifiedAnnot = false;
/*  61 */     this.mCtrlPtsSet = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCreate() {
/*  69 */     super.onCreate();
/*  70 */     if (this.mAnnot != null) {
/*  71 */       boolean shouldUnlockRead = false;
/*     */ 
/*     */       
/*     */       try {
/*  75 */         this.mPdfViewCtrl.docLockRead();
/*  76 */         shouldUnlockRead = true;
/*     */         
/*  78 */         this.mHasSelectionPermission = hasPermission(this.mAnnot, 0);
/*  79 */         this.mHasMenuPermission = hasPermission(this.mAnnot, 1);
/*  80 */       } catch (Exception e) {
/*  81 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */       } finally {
/*  83 */         if (shouldUnlockRead) {
/*  84 */           this.mPdfViewCtrl.docUnlockRead();
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected QuickMenu createQuickMenu() {
/*  92 */     QuickMenu quickMenu = new QuickMenu(this.mPdfViewCtrl, this.mHasMenuPermission);
/*  93 */     quickMenu.inflate(R.menu.annot_edit_text_markup);
/*  94 */     customizeQuickMenuItems(quickMenu);
/*  95 */     quickMenu.initMenuEntries();
/*  96 */     return quickMenu;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEditAnnotTool() {
/* 107 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onSingleTapConfirmed(MotionEvent e) {
/* 116 */     int x = (int)(e.getX() + 0.5D);
/* 117 */     int y = (int)(e.getY() + 0.5D);
/*     */     
/* 119 */     if (this.mAnnot != null) {
/* 120 */       Annot tempAnnot = this.mPdfViewCtrl.getAnnotationAt(x, y);
/* 121 */       if (this.mAnnot.equals(tempAnnot)) {
/*     */ 
/*     */         
/* 124 */         this.mNextToolMode = ToolManager.ToolMode.ANNOT_EDIT_TEXT_MARKUP;
/* 125 */         if (!this.mCtrlPtsSet) {
/* 126 */           setCtrlPts(false);
/*     */         }
/* 128 */         if (!this.mOnUpCalled) {
/* 129 */           showMenu(getQMAnchorRect());
/*     */         }
/*     */       } else {
/*     */         
/* 133 */         setNextToolMode(this.mCurrentDefaultToolMode);
/*     */       } 
/*     */     } else {
/* 136 */       this.mNextToolMode = this.mCurrentDefaultToolMode;
/*     */     } 
/* 138 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPageTurning(int old_page, int cur_page) {
/* 146 */     super.onPageTurning(old_page, cur_page);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onAnnotButtonPressed(int button) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onQuickMenuClicked(QuickMenuItem menuItem) {
/* 161 */     if (!this.mPdfViewCtrl.hasSelection() && this.mAnnot != null) {
/* 162 */       selectAnnot(this.mAnnot, this.mAnnotPageNum);
/*     */     }
/* 164 */     if (menuItem.getItemId() != R.id.qm_highlight && menuItem
/* 165 */       .getItemId() != R.id.qm_strikeout && menuItem
/* 166 */       .getItemId() != R.id.qm_underline && menuItem
/* 167 */       .getItemId() != R.id.qm_squiggly)
/*     */     {
/* 169 */       if (super.onQuickMenuClicked(menuItem)) {
/* 170 */         return true;
/*     */       }
/*     */     }
/* 173 */     if (this.mAnnot != null) {
/*     */       
/* 175 */       if (menuItem.getItemId() == R.id.qm_delete) {
/* 176 */         deleteAnnot();
/* 177 */       } else if (menuItem.getItemId() == R.id.qm_highlight || menuItem
/* 178 */         .getItemId() == R.id.qm_underline || menuItem
/* 179 */         .getItemId() == R.id.qm_strikeout || menuItem
/* 180 */         .getItemId() == R.id.qm_squiggly) {
/* 181 */         changeAnnotType(menuItem);
/* 182 */         setNextToolMode(this.mCurrentDefaultToolMode);
/*     */       
/*     */       }
/* 185 */       else if (menuItem.getItemId() == R.id.qm_note || menuItem.getItemId() == R.id.qm_appearance) {
/* 186 */         ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 187 */         AnnotEdit annotEdit = (AnnotEdit)toolManager.createTool(ToolManager.ToolMode.ANNOT_EDIT, this);
/* 188 */         if (annotEdit.onQuickMenuClicked(menuItem)) {
/* 189 */           return true;
/*     */         }
/* 191 */       } else if (menuItem.getItemId() == R.id.qm_flatten) {
/* 192 */         handleFlattenAnnot();
/* 193 */         setNextToolMode(this.mCurrentDefaultToolMode);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 198 */       this.mNextToolMode = ToolManager.ToolMode.PAN;
/*     */     } 
/* 200 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void changeAnnotType(QuickMenuItem menuItem) {
/* 209 */     if (this.mAnnot == null) {
/*     */       return;
/*     */     }
/*     */     
/* 213 */     Bundle bundle = new Bundle();
/* 214 */     bundle.putString("METHOD_FROM", "changeAnnotType");
/* 215 */     bundle.putStringArray("PDFTRON_KEYS", new String[] { "menuItemId" });
/* 216 */     bundle.putInt("menuItemId", menuItem.getItemId());
/* 217 */     if (onInterceptAnnotationHandling(this.mAnnot, bundle)) {
/*     */       return;
/*     */     }
/* 220 */     boolean shouldUnlock = false;
/*     */ 
/*     */     
/*     */     try {
/* 224 */       this.mPdfViewCtrl.docLock(true);
/* 225 */       shouldUnlock = true;
/* 226 */       raiseAnnotationPreModifyEvent(this.mAnnot, this.mAnnotPageNum);
/* 227 */       if (menuItem.getItemId() == R.id.qm_highlight) {
/* 228 */         this.mAnnot.getSDFObj().putName("Subtype", "Highlight");
/* 229 */       } else if (menuItem.getItemId() == R.id.qm_underline) {
/* 230 */         this.mAnnot.getSDFObj().putName("Subtype", "Underline");
/*     */       }
/* 232 */       else if (menuItem.getItemId() == R.id.qm_strikeout) {
/* 233 */         this.mAnnot.getSDFObj().putName("Subtype", "StrikeOut");
/*     */       } else {
/* 235 */         this.mAnnot.getSDFObj().putName("Subtype", "Squiggly");
/*     */       } 
/* 237 */       this.mAnnot.refreshAppearance();
/* 238 */       this.mPdfViewCtrl.update(this.mAnnot, this.mAnnotPageNum);
/*     */ 
/*     */       
/* 241 */       raiseAnnotationModifiedEvent(this.mAnnot, this.mAnnotPageNum, bundle);
/* 242 */     } catch (Exception e) {
/* 243 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */     } finally {
/* 245 */       if (shouldUnlock) {
/* 246 */         this.mPdfViewCtrl.docUnlock();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void deleteAnnot() {
/* 256 */     super.deleteAnnot();
/* 257 */     setNextToolMode(this.mCurrentDefaultToolMode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolManager.ToolModeBase getToolMode() {
/* 265 */     return ToolManager.ToolMode.ANNOT_EDIT_TEXT_MARKUP;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void selectAnnot(Annot annot, int pageNum) {
/* 273 */     super.selectAnnot(annot, pageNum);
/*     */     
/* 275 */     this.mNextToolMode = ToolManager.ToolMode.ANNOT_EDIT_TEXT_MARKUP;
/* 276 */     setCtrlPts();
/*     */     
/* 278 */     showMenu(getQMAnchorRect());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onScrollChanged(int l, int t, int oldl, int oldt) {
/* 286 */     if (this.mAnnot != null && Math.abs(t - oldt) <= 1 && !isQuickMenuShown()) {
/* 287 */       showMenu(getQMAnchorRect());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDown(MotionEvent e) {
/* 296 */     super.onDown(e);
/* 297 */     this.mOnUpCalled = false;
/*     */     
/* 299 */     if (this.mAnnot != null && !isInsideAnnot(e) && this.mEffSelWidgetId < 0) {
/* 300 */       unsetAnnot();
/* 301 */       setNextToolMode(this.mCurrentDefaultToolMode);
/*     */     } 
/*     */     
/* 304 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onMove(MotionEvent e1, MotionEvent e2, float x_dist, float y_dist) {
/* 312 */     if (this.mScaled)
/*     */     {
/* 314 */       return false;
/*     */     }
/* 316 */     if (!this.mHasSelectionPermission)
/*     */     {
/* 318 */       return false;
/*     */     }
/* 320 */     if (this.mEffSelWidgetId < 0) {
/* 321 */       if (this.mBeingLongPressed) {
/* 322 */         this.mModifiedAnnot = true;
/*     */       }
/*     */     } else {
/* 325 */       this.mModifiedAnnot = true;
/*     */     } 
/* 327 */     return super.onMove(e1, e2, x_dist, y_dist);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onKeyUp(int keyCode, KeyEvent event) {
/* 335 */     if (isQuickMenuShown() && hasMenuEntry(R.id.qm_delete) && ShortcutHelper.isDeleteAnnot(keyCode, event)) {
/* 336 */       closeQuickMenu();
/* 337 */       deleteAnnot();
/* 338 */       return true;
/*     */     } 
/*     */     
/* 341 */     if (this.mPdfViewCtrl.hasSelection() && isQuickMenuShown()) {
/* 342 */       if ((hasMenuEntry(R.id.qm_type) || hasMenuEntry(R.id.qm_highlight)) && ShortcutHelper.isHighlightAnnot(keyCode, event)) {
/* 343 */         onQuickMenuClicked(new QuickMenuItem(this.mPdfViewCtrl.getContext(), R.id.qm_highlight));
/* 344 */         return true;
/*     */       } 
/*     */       
/* 347 */       if ((hasMenuEntry(R.id.qm_type) || hasMenuEntry(R.id.qm_underline)) && ShortcutHelper.isUnderlineAnnot(keyCode, event)) {
/* 348 */         onQuickMenuClicked(new QuickMenuItem(this.mPdfViewCtrl.getContext(), R.id.qm_underline));
/* 349 */         return true;
/*     */       } 
/*     */       
/* 352 */       if ((hasMenuEntry(R.id.qm_type) || hasMenuEntry(R.id.qm_strikeout)) && ShortcutHelper.isStrikethroughAnnot(keyCode, event)) {
/* 353 */         onQuickMenuClicked(new QuickMenuItem(this.mPdfViewCtrl.getContext(), R.id.qm_strikeout));
/*     */         
/* 355 */         return true;
/*     */       } 
/*     */       
/* 358 */       if ((hasMenuEntry(R.id.qm_type) || hasMenuEntry(R.id.qm_squiggly)) && ShortcutHelper.isSquigglyAnnot(keyCode, event)) {
/* 359 */         onQuickMenuClicked(new QuickMenuItem(this.mPdfViewCtrl.getContext(), R.id.qm_squiggly));
/* 360 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 364 */     return super.onKeyUp(keyCode, event);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onUp(MotionEvent e, PDFViewCtrl.PriorEventMode priorEventMode) {
/* 372 */     super.onUp(e, priorEventMode);
/* 373 */     this.mNextToolMode = ToolManager.ToolMode.ANNOT_EDIT_TEXT_MARKUP;
/* 374 */     this.mScaled = false;
/* 375 */     this.mOnUpCalled = true;
/*     */     
/* 377 */     if (this.mAnnot != null) {
/* 378 */       if (this.mModifiedAnnot) {
/* 379 */         this.mModifiedAnnot = false;
/* 380 */         expandTextMarkup();
/*     */       } 
/* 382 */       if (!this.mCtrlPtsSet) {
/* 383 */         setCtrlPts(false);
/*     */       }
/*     */       
/* 386 */       showMenu(getQMAnchorRect());
/*     */ 
/*     */       
/* 389 */       return (priorEventMode == PDFViewCtrl.PriorEventMode.SCROLLING || priorEventMode == PDFViewCtrl.PriorEventMode.FLING);
/*     */     } 
/* 391 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onLongPress(MotionEvent e) {
/* 400 */     super.onLongPress(e);
/* 401 */     if (this.mAnnot != null) {
/* 402 */       this.mNextToolMode = ToolManager.ToolMode.ANNOT_EDIT_TEXT_MARKUP;
/* 403 */       setCtrlPts();
/*     */     } 
/* 405 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDraw(Canvas canvas, Matrix tfm) {
/* 413 */     super.onDraw(canvas, tfm);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onScaleEnd(float x, float y) {
/* 421 */     super.onScaleEnd(x, y);
/*     */     
/* 423 */     if (this.mAnnot != null) {
/*     */ 
/*     */       
/* 426 */       this.mScaled = true;
/* 427 */       setCtrlPts();
/*     */     } 
/* 429 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onFlingStop() {
/* 437 */     super.onFlingStop();
/*     */     
/* 439 */     if (this.mAnnot != null);
/*     */ 
/*     */     
/* 442 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLayout(boolean changed, int l, int t, int r, int b) {
/* 450 */     super.onLayout(changed, l, t, r, b);
/* 451 */     if (this.mAnnot != null) {
/* 452 */       if (!this.mPdfViewCtrl.isContinuousPagePresentationMode(this.mPdfViewCtrl.getPagePresentationMode()) && 
/* 453 */         this.mAnnotPageNum != this.mPdfViewCtrl.getCurrentPage()) {
/*     */ 
/*     */         
/* 456 */         unsetAnnot();
/* 457 */         this.mNextToolMode = this.mCurrentDefaultToolMode;
/* 458 */         setCtrlPts();
/* 459 */         closeQuickMenu();
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 464 */       setCtrlPts();
/* 465 */       if (isQuickMenuShown() && changed) {
/* 466 */         closeQuickMenu();
/* 467 */         showMenu(getQMAnchorRect());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onConfigurationChanged(Configuration newConfig) {
/* 477 */     if (this.mAnnot != null) {
/* 478 */       this.mNextToolMode = getToolMode();
/*     */     } else {
/* 480 */       setNextToolMode(this.mCurrentDefaultToolMode);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setCtrlPts() {
/* 485 */     setCtrlPts(false);
/*     */   }
/*     */   
/*     */   private void setCtrlPts(boolean bySnap) {
/* 489 */     if (this.mAnnot == null) {
/*     */       return;
/*     */     }
/*     */     
/* 493 */     Bundle bundle = new Bundle();
/* 494 */     bundle.putStringArray("PDFTRON_KEYS", new String[] { "bySnap" });
/* 495 */     bundle.putBoolean("bySnap", bySnap);
/* 496 */     if (onInterceptAnnotationHandling(this.mAnnot, bundle)) {
/*     */       return;
/*     */     }
/* 499 */     this.mCtrlPtsSet = true;
/* 500 */     boolean shouldUnlockRead = false;
/*     */     try {
/* 502 */       this.mPdfViewCtrl.docLockRead();
/* 503 */       shouldUnlockRead = true;
/*     */       
/* 505 */       double[] pts1 = new double[2];
/* 506 */       double[] pts3 = new double[2];
/*     */       
/* 508 */       double[] screen1 = new double[2];
/* 509 */       double[] screen2 = new double[2];
/* 510 */       double[] screen3 = new double[2];
/* 511 */       double[] screen4 = new double[2];
/*     */       
/* 513 */       TextMarkup markup = new TextMarkup(this.mAnnot);
/* 514 */       if (markup.isValid())
/*     */       {
/* 516 */         QuadPoint pt1 = null;
/* 517 */         QuadPoint pt2 = null;
/*     */         try {
/* 519 */           int count = markup.getQuadPointCount();
/* 520 */           pt1 = markup.getQuadPoint(0);
/* 521 */           pt2 = markup.getQuadPoint(count - 1);
/* 522 */         } catch (Exception ex) {
/* 523 */           AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*     */         } 
/*     */         
/* 526 */         if (pt1 != null && pt2 != null) {
/* 527 */           screen1 = this.mPdfViewCtrl.convPagePtToScreenPt(pt1.p1.x, pt1.p1.y, this.mAnnotPageNum);
/* 528 */           screen2 = this.mPdfViewCtrl.convPagePtToScreenPt(pt1.p2.x, pt1.p2.y, this.mAnnotPageNum);
/* 529 */           screen3 = this.mPdfViewCtrl.convPagePtToScreenPt(pt1.p3.x, pt1.p3.y, this.mAnnotPageNum);
/* 530 */           screen4 = this.mPdfViewCtrl.convPagePtToScreenPt(pt1.p4.x, pt1.p4.y, this.mAnnotPageNum);
/*     */           
/* 532 */           double minX = Math.min(Math.min(screen1[0], screen2[0]), Math.min(screen3[0], screen4[0]));
/* 533 */           double maxX = Math.max(Math.max(screen1[0], screen2[0]), Math.max(screen3[0], screen4[0]));
/* 534 */           double minY = Math.min(Math.min(screen1[1], screen2[1]), Math.min(screen3[1], screen4[1]));
/* 535 */           double maxY = Math.max(Math.max(screen1[1], screen2[1]), Math.max(screen3[1], screen4[1]));
/*     */           
/* 537 */           boolean rightToLeft = this.mPdfViewCtrl.getRightToLeftLanguage();
/*     */           
/* 539 */           double newX1 = minX;
/* 540 */           if (rightToLeft) {
/* 541 */             newX1 = maxX;
/*     */           }
/* 543 */           double newY1 = 0.6D * maxY + 0.4D * minY;
/*     */           
/* 545 */           screen1 = this.mPdfViewCtrl.convPagePtToScreenPt(pt2.p1.x, pt2.p1.y, this.mAnnotPageNum);
/* 546 */           screen2 = this.mPdfViewCtrl.convPagePtToScreenPt(pt2.p2.x, pt2.p2.y, this.mAnnotPageNum);
/* 547 */           screen3 = this.mPdfViewCtrl.convPagePtToScreenPt(pt2.p3.x, pt2.p3.y, this.mAnnotPageNum);
/* 548 */           screen4 = this.mPdfViewCtrl.convPagePtToScreenPt(pt2.p4.x, pt2.p4.y, this.mAnnotPageNum);
/*     */           
/* 550 */           minX = Math.min(Math.min(screen1[0], screen2[0]), Math.min(screen3[0], screen4[0]));
/* 551 */           maxX = Math.max(Math.max(screen1[0], screen2[0]), Math.max(screen3[0], screen4[0]));
/* 552 */           minY = Math.min(Math.min(screen1[1], screen2[1]), Math.min(screen3[1], screen4[1]));
/* 553 */           maxY = Math.max(Math.max(screen1[1], screen2[1]), Math.max(screen3[1], screen4[1]));
/*     */           
/* 555 */           double newX2 = maxX;
/* 556 */           if (rightToLeft) {
/* 557 */             newX2 = minX;
/*     */           }
/* 559 */           double newY2 = 0.4D * maxY + 0.6D * minY;
/*     */           
/* 561 */           pts1 = new double[] { newX1, newY1 };
/* 562 */           pts3 = new double[] { newX2, newY2 };
/*     */         } 
/* 564 */         selectText((float)pts1[0], (float)pts1[1], (float)pts3[0], (float)pts3[1], false, bySnap);
/* 565 */         this.mSelWidgetEnabled = true;
/* 566 */         this.mPdfViewCtrl.invalidate(this.mInvalidateBBox);
/*     */       }
/*     */     
/* 569 */     } catch (Exception e) {
/* 570 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */     } finally {
/* 572 */       if (shouldUnlockRead) {
/* 573 */         this.mPdfViewCtrl.docUnlockRead();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void expandTextMarkup() {
/* 579 */     if (this.mAnnot == null || onInterceptAnnotationHandling(this.mAnnot)) {
/*     */       return;
/*     */     }
/*     */     
/* 583 */     if (this.mPdfViewCtrl.hasSelection()) {
/* 584 */       int sel_pg_begin = this.mPdfViewCtrl.getSelectionBeginPage();
/* 585 */       int sel_pg_end = this.mPdfViewCtrl.getSelectionEndPage();
/* 586 */       int sel_up_page = this.mAnnotPageNum;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 605 */       LinkedList<AnnotUpdateInfo> updateInfoList = new LinkedList<>();
/*     */       
/* 607 */       boolean multiPageSel = false;
/* 608 */       boolean shouldUnlock = false;
/*     */       try {
/* 610 */         this.mPdfViewCtrl.docLock(true);
/* 611 */         shouldUnlock = true;
/* 612 */         PDFDoc doc = this.mPdfViewCtrl.getDoc();
/*     */         
/* 614 */         ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 615 */         boolean useAdobeHack = toolManager.isTextMarkupAdobeHack();
/*     */         
/* 617 */         for (int pg = sel_pg_begin; pg <= sel_pg_end; pg++) {
/* 618 */           PDFViewCtrl.Selection sel = this.mPdfViewCtrl.getSelection(pg);
/* 619 */           double[] quads = sel.getQuads();
/* 620 */           int sz = quads.length / 8;
/* 621 */           if (sz != 0) {
/*     */             Squiggly squiggly;
/*     */ 
/*     */             
/* 625 */             Point p1 = new Point();
/* 626 */             Point p2 = new Point();
/* 627 */             Point p3 = new Point();
/* 628 */             Point p4 = new Point();
/* 629 */             QuadPoint qp = new QuadPoint(p1, p2, p3, p4);
/*     */             
/* 631 */             TextMarkup tm = new TextMarkup();
/* 632 */             Rect bbox = new Rect(quads[0], quads[1], quads[4], quads[5]);
/*     */             
/* 634 */             if (pg == this.mAnnotPageNum) {
/* 635 */               raiseAnnotationPreModifyEvent(this.mAnnot, pg);
/* 636 */               tm = new TextMarkup(this.mAnnot);
/*     */ 
/*     */ 
/*     */               
/* 640 */               Rect rect1 = tm.getRect();
/* 641 */               double[] pts1 = this.mPdfViewCtrl.convPagePtToScreenPt(rect1.getX1(), rect1.getY1(), this.mAnnotPageNum);
/* 642 */               double[] pts2 = this.mPdfViewCtrl.convPagePtToScreenPt(rect1.getX2(), rect1.getY2(), this.mAnnotPageNum);
/* 643 */               Rect old_update_rect = new Rect(pts1[0], pts1[1], pts2[0], pts2[1]);
/* 644 */               old_update_rect.normalize();
/* 645 */               updateInfoList.add(new AnnotUpdateInfo(null, 0, old_update_rect, false, false));
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 650 */               tm.getSDFObj().erase("QuadPoints");
/* 651 */               tm.getSDFObj().erase("Rect");
/*     */               
/* 653 */               tm.setRect(bbox);
/*     */               
/* 655 */               if (((ToolManager)this.mPdfViewCtrl.getToolManager()).isCopyAnnotatedTextToNoteEnabled() && 
/* 656 */                 Utils.isTextCopy(this.mAnnot)) {
/*     */                 
/*     */                 try {
/* 659 */                   Popup p = tm.getPopup();
/* 660 */                   if (p == null || !p.isValid()) {
/* 661 */                     p = Popup.create((Doc)this.mPdfViewCtrl.getDoc(), tm.getRect());
/* 662 */                     p.setParent((Annot)tm);
/* 663 */                     tm.setPopup(p);
/*     */                   } 
/* 665 */                   String content = getHighlightedText(pg);
/* 666 */                   p.setContents(content);
/* 667 */                 } catch (Exception e) {
/* 668 */                   AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */                 } 
/*     */               }
/*     */             } else {
/* 672 */               if (this.mAnnot.getType() == 8) {
/* 673 */                 Highlight highlight = Highlight.create((Doc)doc, bbox);
/* 674 */               } else if (this.mAnnot.getType() == 9) {
/* 675 */                 Underline underline = Underline.create((Doc)doc, bbox);
/* 676 */               } else if (this.mAnnot.getType() == 11) {
/* 677 */                 StrikeOut strikeOut = StrikeOut.create((Doc)doc, bbox);
/* 678 */               } else if (this.mAnnot.getType() == 10) {
/* 679 */                 squiggly = Squiggly.create((Doc)doc, bbox);
/*     */               } 
/* 681 */               multiPageSel = true;
/*     */             } 
/*     */             
/* 684 */             boolean isModified = false;
/* 685 */             boolean isAdded = false;
/* 686 */             int k = 0;
/* 687 */             for (int i = 0; i < sz; i++, k += 8) {
/* 688 */               p1.x = quads[k];
/* 689 */               p1.y = quads[k + 1];
/*     */               
/* 691 */               p2.x = quads[k + 2];
/* 692 */               p2.y = quads[k + 3];
/*     */               
/* 694 */               p3.x = quads[k + 4];
/* 695 */               p3.y = quads[k + 5];
/*     */               
/* 697 */               p4.x = quads[k + 6];
/* 698 */               p4.y = quads[k + 7];
/*     */               
/* 700 */               if (useAdobeHack) {
/* 701 */                 qp.p1 = p4;
/* 702 */                 qp.p2 = p3;
/* 703 */                 qp.p3 = p1;
/* 704 */                 qp.p4 = p2;
/*     */               } else {
/* 706 */                 qp.p1 = p1;
/* 707 */                 qp.p2 = p2;
/* 708 */                 qp.p3 = p3;
/* 709 */                 qp.p4 = p4;
/*     */               } 
/*     */               
/* 712 */               squiggly.setQuadPoint(i, qp);
/* 713 */               isModified = true;
/*     */             } 
/*     */             
/* 716 */             if (pg != this.mAnnotPageNum) {
/* 717 */               TextMarkup org_tm = new TextMarkup(this.mAnnot);
/* 718 */               squiggly.setColor(org_tm.getColorAsRGB(), 3);
/* 719 */               squiggly.setBorderStyle(org_tm.getBorderStyle());
/* 720 */               squiggly.setOpacity(org_tm.getOpacity());
/* 721 */               squiggly.setContents(org_tm.getContents());
/* 722 */               setAuthor((Markup)squiggly);
/* 723 */               Page page = this.mPdfViewCtrl.getDoc().getPage(pg);
/* 724 */               int index = TextMarkupCreate.getAnnotIndexForAddingMarkup(page);
/* 725 */               page.annotInsert(index, (Annot)squiggly);
/*     */               
/* 727 */               setAnnot((Annot)squiggly, pg);
/* 728 */               sel_up_page = pg;
/*     */               
/* 730 */               if (((ToolManager)this.mPdfViewCtrl.getToolManager()).isCopyAnnotatedTextToNoteEnabled() && 
/* 731 */                 Utils.isTextCopy(this.mAnnot)) {
/*     */                 
/*     */                 try {
/* 734 */                   Popup p = squiggly.getPopup();
/* 735 */                   if (p == null || !p.isValid()) {
/* 736 */                     p = Popup.create((Doc)this.mPdfViewCtrl.getDoc(), squiggly.getRect());
/* 737 */                     p.setParent((Annot)squiggly);
/* 738 */                     squiggly.setPopup(p);
/*     */                   } 
/* 740 */                   p.setContents(getHighlightedText(pg));
/* 741 */                 } catch (Exception e) {
/* 742 */                   AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */                 } 
/*     */               }
/*     */               
/* 746 */               isAdded = true;
/*     */             } 
/*     */             
/* 749 */             squiggly.refreshAppearance();
/*     */ 
/*     */             
/* 752 */             Rect r = squiggly.getRect();
/* 753 */             Rect ur = new Rect();
/* 754 */             r.normalize();
/*     */             
/* 756 */             double[] pts = this.mPdfViewCtrl.convPagePtToScreenPt(r.getX1(), r.getY2(), pg);
/* 757 */             ur.setX1(pts[0]);
/* 758 */             ur.setY1(pts[1]);
/* 759 */             pts = this.mPdfViewCtrl.convPagePtToScreenPt(r.getX2(), r.getY1(), pg);
/* 760 */             ur.setX2(pts[0]);
/* 761 */             ur.setY2(pts[1]);
/* 762 */             if (isModified) {
/* 763 */               updateInfoList.add(new AnnotUpdateInfo(this, (TextMarkup)squiggly, this.mAnnotPageNum, ur, false, true));
/*     */             }
/* 765 */             if (isAdded) {
/* 766 */               updateInfoList.add(new AnnotUpdateInfo(this, (TextMarkup)squiggly, pg, ur, true, false));
/*     */             }
/*     */           } 
/*     */         } 
/* 770 */         buildAnnotBBox();
/*     */         
/* 772 */         if (multiPageSel) {
/* 773 */           this.mAnnotPageNum = sel_up_page;
/* 774 */           buildAnnotBBox();
/* 775 */           setCtrlPts(true);
/* 776 */           showMenu(getQMAnchorRect());
/*     */         } 
/*     */ 
/*     */         
/* 780 */         Map<Annot, Integer> annotsModifiedList = new HashMap<>();
/* 781 */         Map<Annot, Integer> annotsAddedList = new HashMap<>();
/* 782 */         for (AnnotUpdateInfo updateInfo : updateInfoList) {
/* 783 */           if (!this.mPdfViewCtrl.isAnnotationLayerEnabled()) {
/* 784 */             Rect rect = AnnotUpdateInfo.access$000(updateInfo);
/* 785 */             this.mPdfViewCtrl.update(rect);
/*     */           } 
/* 787 */           TextMarkup textMarkup = AnnotUpdateInfo.access$100(updateInfo);
/* 788 */           int pageNum = AnnotUpdateInfo.access$200(updateInfo);
/*     */           
/* 790 */           if (textMarkup != null) {
/* 791 */             this.mPdfViewCtrl.update((Annot)textMarkup, pageNum);
/* 792 */             if (AnnotUpdateInfo.access$300(updateInfo)) {
/* 793 */               annotsModifiedList.put(textMarkup, Integer.valueOf(pageNum));
/*     */             }
/* 795 */             if (AnnotUpdateInfo.access$400(updateInfo)) {
/* 796 */               annotsAddedList.put(textMarkup, Integer.valueOf(pageNum));
/*     */             }
/*     */           } 
/*     */         } 
/* 800 */         if (annotsAddedList.size() > 0) {
/* 801 */           raiseAnnotationAddedEvent(annotsAddedList);
/* 802 */         } else if (annotsModifiedList.size() > 0) {
/*     */           
/* 804 */           raiseAnnotationModifiedEvent(annotsModifiedList);
/*     */         }
/*     */       
/* 807 */       } catch (Exception e) {
/* 808 */         ((ToolManager)this.mPdfViewCtrl.getToolManager()).annotationCouldNotBeAdded(e.getMessage());
/* 809 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */       } finally {
/* 811 */         if (shouldUnlock) {
/* 812 */           this.mPdfViewCtrl.docUnlock();
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setNextToolMode(ToolManager.ToolModeBase mode) {
/* 819 */     unsetAnnot();
/* 820 */     this.mNextToolMode = mode;
/*     */     
/* 822 */     this.mPdfViewCtrl.clearSelection();
/* 823 */     this.mEffSelWidgetId = -1;
/* 824 */     this.mSelWidgetEnabled = false;
/* 825 */     this.mPdfViewCtrl.invalidate();
/* 826 */     if (!this.mSelPath.isEmpty())
/*     */     {
/* 828 */       this.mSelPath.reset();
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
/*     */   public String getHighlightedText(int pageNum) {
/* 840 */     String text = "";
/* 841 */     if (this.mPdfViewCtrl.hasSelection()) {
/* 842 */       boolean shouldUnlockRead = false;
/*     */       try {
/* 844 */         this.mPdfViewCtrl.docLockRead();
/* 845 */         shouldUnlockRead = true;
/* 846 */         PDFViewCtrl.Selection sel = this.mPdfViewCtrl.getSelection(pageNum);
/* 847 */         text = sel.getAsUnicode();
/* 848 */       } catch (Exception e) {
/* 849 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */       } finally {
/* 851 */         if (shouldUnlockRead) {
/* 852 */           this.mPdfViewCtrl.docUnlockRead();
/*     */         }
/*     */       } 
/*     */     } 
/* 856 */     return text;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getModeAHLabel() {
/* 864 */     return 105;
/*     */   }
/*     */   
/*     */   private RectF getQMAnchorRect() {
/* 868 */     RectF annotRect = getAnnotRect();
/* 869 */     if (annotRect == null) {
/* 870 */       return null;
/*     */     }
/* 872 */     return new RectF(annotRect.left, annotRect.top, annotRect.right, annotRect.bottom + Utils.convDp2Pix(this.mPdfViewCtrl.getContext(), 24.0F));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getQuickMenuAnalyticType() {
/* 880 */     return 4;
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\AnnotEditTextMarkup.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */