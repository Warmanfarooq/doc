/*     */ package com.pdftron.pdf.tools;
/*     */ 
/*     */ import android.annotation.SuppressLint;
/*     */ import android.content.Context;
/*     */ import android.content.SharedPreferences;
/*     */ import android.graphics.Canvas;
/*     */ import android.graphics.Color;
/*     */ import android.graphics.Matrix;
/*     */ import android.graphics.Paint;
/*     */ import android.graphics.Path;
/*     */ import android.graphics.PointF;
/*     */ import android.graphics.Rect;
/*     */ import android.graphics.RectF;
/*     */ import android.os.Bundle;
/*     */ import android.view.MotionEvent;
/*     */ import androidx.annotation.Keep;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.Annot;
/*     */ import com.pdftron.pdf.ColorPt;
/*     */ import com.pdftron.pdf.PDFDoc;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.Page;
/*     */ import com.pdftron.pdf.Point;
/*     */ import com.pdftron.pdf.QuadPoint;
/*     */ import com.pdftron.pdf.Rect;
/*     */ import com.pdftron.pdf.annots.Markup;
/*     */ import com.pdftron.pdf.annots.Popup;
/*     */ import com.pdftron.pdf.annots.Redaction;
/*     */ import com.pdftron.pdf.annots.TextMarkup;
/*     */ import com.pdftron.pdf.config.ToolStyleConfig;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.AnnotUtils;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import com.pdftron.sdf.Doc;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public abstract class TextMarkupCreate
/*     */   extends BaseTool
/*     */ {
/*     */   Rect mInvalidateBBox;
/*     */   float mOpacity;
/*     */   int mColor;
/*     */   float mThickness;
/*     */   int mFillColor;
/*     */   Paint mPaint;
/*     */   Path mSelPath;
/*     */   RectF mTempRect;
/*     */   RectF mSelBBox;
/*     */   PointF mStationPt;
/*     */   boolean mOnUpCalled;
/*     */   protected boolean mIsPointOutsidePage;
/*     */   
/*     */   public TextMarkupCreate(@NonNull PDFViewCtrl ctrl) {
/*  75 */     super(ctrl);
/*     */     
/*  77 */     this.mSelPath = new Path();
/*  78 */     this.mTempRect = new RectF();
/*  79 */     this.mSelBBox = new RectF();
/*  80 */     this.mStationPt = new PointF();
/*     */     
/*  82 */     this.mOnUpCalled = false;
/*  83 */     this.mPaint = new Paint();
/*  84 */     this.mPaint.setAntiAlias(true);
/*     */     
/*  86 */     this.mInvalidateBBox = new Rect();
/*     */     
/*  88 */     this.mNextToolMode = getToolMode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static int getAnnotIndexForAddingMarkup(Page page) {
/*  98 */     int index = 0;
/*  99 */     boolean foundMarkupAnnot = false;
/*     */     
/*     */     try {
/* 102 */       for (index = page.getNumAnnots() - 1; index > 0; index--) {
/* 103 */         int type = page.getAnnot(index).getType();
/* 104 */         if (type == 8 || type == 9 || type == 10 || type == 11 || type == 1 || type == 19) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 110 */           foundMarkupAnnot = true;
/*     */           break;
/*     */         } 
/*     */       } 
/* 114 */     } catch (PDFNetException pDFNetException) {}
/*     */ 
/*     */ 
/*     */     
/* 118 */     if (foundMarkupAnnot) {
/* 119 */       index++;
/*     */     }
/* 121 */     return index;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCreateAnnotType() {
/* 126 */     return 28;
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
/*     */   
/*     */   public boolean isCreatingAnnotation() {
/* 140 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setupAnnotProperty(int color, float opacity, float thickness, int fillColor, String icon, String pdfTronFontName) {
/* 148 */     this.mColor = color;
/* 149 */     this.mOpacity = opacity;
/* 150 */     this.mThickness = thickness;
/* 151 */     this.mFillColor = fillColor;
/*     */     
/* 153 */     SharedPreferences settings = Tool.getToolPreferences(this.mPdfViewCtrl.getContext());
/* 154 */     SharedPreferences.Editor editor = settings.edit();
/*     */     
/* 156 */     editor.putInt(getColorKey(getCreateAnnotType()), this.mColor);
/* 157 */     editor.putFloat(getOpacityKey(getCreateAnnotType()), this.mOpacity);
/* 158 */     editor.putFloat(getThicknessKey(getCreateAnnotType()), this.mThickness);
/* 159 */     editor.putInt(getColorFillKey(getCreateAnnotType()), this.mFillColor);
/*     */     
/* 161 */     editor.apply();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onSingleTapConfirmed(MotionEvent e) {
/* 169 */     if (this.mForceSameNextToolMode) {
/*     */       
/* 171 */       if (this.mAnnotPushedBack) {
/* 172 */         return true;
/*     */       }
/*     */       
/* 175 */       int x = (int)(e.getX() + 0.5D);
/* 176 */       int y = (int)(e.getY() + 0.5D);
/*     */       
/* 178 */       Annot tempAnnot = this.mPdfViewCtrl.getAnnotationAt(x, y);
/* 179 */       int page = this.mPdfViewCtrl.getPageNumberFromScreenPt(x, y);
/*     */       
/* 181 */       setCurrentDefaultToolModeHelper(getToolMode());
/*     */       
/*     */       try {
/* 184 */         if (null != tempAnnot && tempAnnot.isValid()) {
/* 185 */           ((ToolManager)this.mPdfViewCtrl.getToolManager()).selectAnnot(tempAnnot, page);
/*     */         } else {
/* 187 */           this.mNextToolMode = getToolMode();
/*     */         } 
/* 189 */       } catch (PDFNetException pDFNetException) {}
/*     */ 
/*     */       
/* 192 */       return false;
/*     */     } 
/*     */     
/* 195 */     return super.onSingleTapConfirmed(e);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDown(MotionEvent e) {
/* 203 */     super.onDown(e);
/* 204 */     this.mLoupeEnabled = true;
/* 205 */     this.mOnUpCalled = true;
/*     */     
/* 207 */     this.mColor = 16776960;
/* 208 */     this.mOpacity = 1.0F;
/* 209 */     this.mThickness = 1.0F;
/* 210 */     this.mFillColor = 0;
/*     */     
/* 212 */     this.mAnnotPushedBack = false;
/*     */     
/* 214 */     float x = e.getX() + this.mPdfViewCtrl.getScrollX();
/* 215 */     float y = e.getY() + this.mPdfViewCtrl.getScrollY();
/*     */     
/* 217 */     this.mStationPt.set(x, y);
/*     */     
/* 219 */     setLoupeInfo(e.getX(), e.getY());
/* 220 */     this.mPdfViewCtrl.invalidate();
/* 221 */     animateLoupe(true);
/*     */     
/* 223 */     int page = this.mPdfViewCtrl.getPageNumberFromScreenPt(e.getX(), e.getY());
/* 224 */     if (page < 1) {
/* 225 */       this.mIsPointOutsidePage = true;
/*     */     } else {
/* 227 */       this.mIsPointOutsidePage = false;
/*     */     } 
/*     */     
/* 230 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onFlingStop() {
/* 238 */     if (this.mAllowTwoFingerScroll) {
/* 239 */       doneTwoFingerScrolling();
/*     */     }
/* 241 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onUp(MotionEvent e, PDFViewCtrl.PriorEventMode priorEventMode) {
/* 250 */     if (this.mAllowTwoFingerScroll) {
/* 251 */       doneTwoFingerScrolling();
/* 252 */       animateLoupe(false);
/* 253 */       this.mLoupeEnabled = false;
/* 254 */       return false;
/*     */     } 
/*     */     
/* 257 */     if (priorEventMode == PDFViewCtrl.PriorEventMode.PAGE_SLIDING) {
/* 258 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 262 */     if (this.mAnnotPushedBack && this.mForceSameNextToolMode) {
/* 263 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 267 */     this.mAllowOneFingerScrollWithStylus = (this.mStylusUsed && e.getToolType(0) != 2);
/* 268 */     if (this.mAllowOneFingerScrollWithStylus) {
/* 269 */       animateLoupe(false);
/* 270 */       this.mLoupeEnabled = false;
/* 271 */       return true;
/*     */     } 
/*     */     
/* 274 */     if (this.mOnUpCalled) {
/*     */       
/* 276 */       this.mOnUpCalled = false;
/*     */       
/* 278 */       if (!this.mPdfViewCtrl.hasSelection()) {
/*     */         try {
/* 280 */           if (!((ToolManager)this.mPdfViewCtrl.getToolManager()).isQuickMenuJustClosed()) {
/*     */             
/* 282 */             int x = (int)(e.getX() + 0.5D);
/* 283 */             int y = (int)(e.getY() + 0.5D);
/* 284 */             if (!hasAnnot(x, y)) {
/* 285 */               float sx = this.mPdfViewCtrl.getScrollX();
/* 286 */               float sy = this.mPdfViewCtrl.getScrollY();
/* 287 */               this.mPressedPoint.x = e.getX() + sx;
/* 288 */               this.mPressedPoint.y = e.getY() + sy;
/*     */               
/* 290 */               selectText(this.mStationPt.x - sx, this.mStationPt.y - sy, e.getX(), e.getY(), true);
/* 291 */               this.mPdfViewCtrl.invalidate(this.mInvalidateBBox);
/*     */             } 
/*     */           } 
/* 294 */         } catch (Exception ex) {
/* 295 */           AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*     */         } 
/*     */       }
/*     */       
/* 299 */       this.mPdfViewCtrl.invalidate();
/* 300 */       createTextMarkup();
/*     */     } 
/*     */     
/* 303 */     animateLoupe(false);
/*     */     
/* 305 */     return skipOnUpPriorEvent(priorEventMode);
/*     */   }
/*     */   
/*     */   protected void createTextMarkup() {
/* 309 */     if (!this.mPdfViewCtrl.hasSelection()) {
/*     */       return;
/*     */     }
/* 312 */     int sel_pg_begin = this.mPdfViewCtrl.getSelectionBeginPage();
/* 313 */     int sel_pg_end = this.mPdfViewCtrl.getSelectionEndPage();
/*     */     class AnnotUpdateInfo
/*     */     {
/*     */       Annot mAnnot;
/*     */       int mPageNum;
/*     */       Rect mRect;
/*     */       
/*     */       public AnnotUpdateInfo(Annot annot, int pageNum, Rect rect) {
/* 321 */         this.mAnnot = annot;
/* 322 */         this.mPageNum = pageNum;
/* 323 */         this.mRect = rect;
/*     */       }
/*     */     };
/*     */     
/* 327 */     LinkedList<AnnotUpdateInfo> updateInfoList = new LinkedList<>();
/*     */     
/* 329 */     boolean shouldUnlock = false;
/*     */     
/*     */     try {
/* 332 */       ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 333 */       if (toolManager.isAutoSelectAnnotation() || !this.mForceSameNextToolMode) {
/* 334 */         this.mNextToolMode = ToolManager.ToolMode.ANNOT_EDIT_TEXT_MARKUP;
/*     */       } else {
/* 336 */         this.mNextToolMode = getToolMode();
/*     */       } 
/*     */       
/* 339 */       setCurrentDefaultToolModeHelper(getToolMode());
/*     */       
/* 341 */       addOldTools();
/*     */       
/* 343 */       this.mPdfViewCtrl.docLock(true);
/* 344 */       shouldUnlock = true;
/* 345 */       PDFDoc doc = this.mPdfViewCtrl.getDoc();
/* 346 */       for (int pg = sel_pg_begin; pg <= sel_pg_end; pg++) {
/* 347 */         PDFViewCtrl.Selection sel = this.mPdfViewCtrl.getSelection(pg);
/* 348 */         double[] quads = sel.getQuads();
/* 349 */         int sz = quads.length / 8;
/* 350 */         if (sz != 0) {
/*     */ 
/*     */ 
/*     */           
/* 354 */           Point p1 = new Point();
/* 355 */           Point p2 = new Point();
/* 356 */           Point p3 = new Point();
/* 357 */           Point p4 = new Point();
/* 358 */           QuadPoint qp = new QuadPoint(p1, p2, p3, p4);
/*     */           
/* 360 */           Rect bbox = new Rect(quads[0], quads[1], quads[4], quads[5]);
/* 361 */           Annot tm = createMarkup(doc, bbox);
/*     */           
/* 363 */           Context context = this.mPdfViewCtrl.getContext();
/* 364 */           SharedPreferences settings = Tool.getToolPreferences(context);
/* 365 */           this.mColor = settings.getInt(getColorKey(getCreateAnnotType()), ToolStyleConfig.getInstance().getDefaultColor(context, getCreateAnnotType()));
/* 366 */           this.mOpacity = settings.getFloat(getOpacityKey(getCreateAnnotType()), ToolStyleConfig.getInstance().getDefaultOpacity(context, getCreateAnnotType()));
/* 367 */           this.mThickness = settings.getFloat(getThicknessKey(getCreateAnnotType()), ToolStyleConfig.getInstance().getDefaultThickness(context, getCreateAnnotType()));
/* 368 */           this.mFillColor = settings.getInt(getColorFillKey(getCreateAnnotType()), ToolStyleConfig.getInstance().getDefaultFillColor(context, getCreateAnnotType()));
/*     */           
/* 370 */           boolean useAdobeHack = toolManager.isTextMarkupAdobeHack();
/*     */           
/* 372 */           int k = 0;
/*     */           
/* 374 */           double left = 0.0D;
/* 375 */           double right = 0.0D;
/* 376 */           double top = 0.0D;
/* 377 */           double bottom = 0.0D;
/* 378 */           for (int i = 0; i < sz; i++, k += 8) {
/* 379 */             p1.x = quads[k];
/* 380 */             p1.y = quads[k + 1];
/*     */             
/* 382 */             p2.x = quads[k + 2];
/* 383 */             p2.y = quads[k + 3];
/*     */             
/* 385 */             p3.x = quads[k + 4];
/* 386 */             p3.y = quads[k + 5];
/*     */             
/* 388 */             p4.x = quads[k + 6];
/* 389 */             p4.y = quads[k + 7];
/*     */             
/* 391 */             if (useAdobeHack) {
/* 392 */               qp.p1 = p4;
/* 393 */               qp.p2 = p3;
/* 394 */               qp.p3 = p1;
/* 395 */               qp.p4 = p2;
/*     */             } else {
/* 397 */               qp.p1 = p1;
/* 398 */               qp.p2 = p2;
/* 399 */               qp.p3 = p3;
/* 400 */               qp.p4 = p4;
/*     */             } 
/* 402 */             if (tm != null && tm instanceof TextMarkup) {
/* 403 */               ((TextMarkup)tm).setQuadPoint(i, qp);
/* 404 */             } else if (tm != null && tm instanceof Redaction) {
/* 405 */               ((Redaction)tm).setQuadPoint(i, qp);
/*     */             } else {
/*     */               
/* 408 */               if (0.0D == left) {
/* 409 */                 left = p1.x;
/*     */               } else {
/* 411 */                 left = Math.min(left, p1.x);
/*     */               } 
/* 413 */               right = Math.max(right, p2.x);
/* 414 */               if (0.0D == top) {
/* 415 */                 top = p1.y;
/*     */               } else {
/* 417 */                 top = Math.min(top, p1.y);
/*     */               } 
/* 419 */               bottom = Math.max(bottom, p3.y);
/* 420 */               setAnnotRect(tm, new Rect(left, top, right, bottom), pg);
/*     */             } 
/*     */           } 
/*     */           
/* 424 */           if (tm != null) {
/* 425 */             ColorPt colorPt = Utils.color2ColorPt(this.mColor);
/* 426 */             tm.setColor(colorPt, 3);
/*     */             
/* 428 */             if (tm instanceof Markup) {
/* 429 */               ((Markup)tm).setOpacity(this.mOpacity);
/* 430 */               setAuthor((Markup)tm);
/* 431 */               if (((ToolManager)this.mPdfViewCtrl.getToolManager()).isCopyAnnotatedTextToNoteEnabled()) {
/*     */                 
/*     */                 try {
/* 434 */                   Popup p = Popup.create((Doc)this.mPdfViewCtrl.getDoc(), tm.getRect());
/* 435 */                   p.setParent(tm);
/* 436 */                   ((Markup)tm).setPopup(p);
/* 437 */                   p.setContents(sel.getAsUnicode());
/* 438 */                   Utils.setTextCopy(tm);
/* 439 */                 } catch (PDFNetException ex) {
/* 440 */                   AnalyticsHandlerAdapter.getInstance().sendException((Exception)ex);
/*     */                 } 
/*     */               }
/*     */             } 
/*     */             
/* 445 */             if (tm instanceof Redaction) {
/* 446 */               ColorPt fillColorPt = Utils.color2ColorPt(this.mFillColor);
/* 447 */               ((Redaction)tm).setInteriorColor(fillColorPt, 3);
/*     */             } 
/*     */             
/* 450 */             if (tm.getType() != 8) {
/* 451 */               Annot.BorderStyle bs = tm.getBorderStyle();
/* 452 */               bs.setWidth(this.mThickness);
/* 453 */               tm.setBorderStyle(bs);
/*     */             } 
/*     */             
/* 456 */             Page page = this.mPdfViewCtrl.getDoc().getPage(pg);
/* 457 */             int index = getAnnotIndexForAddingMarkup(page);
/* 458 */             page.annotInsert(index, tm);
/* 459 */             tm.refreshAppearance();
/*     */             
/* 461 */             this.mAnnotPushedBack = true;
/* 462 */             setAnnot(tm, pg);
/* 463 */             buildAnnotBBox();
/*     */ 
/*     */             
/* 466 */             Rect ur = AnnotUtils.computeAnnotInbox(this.mPdfViewCtrl, tm, pg);
/* 467 */             updateInfoList.add(new AnnotUpdateInfo(tm, pg, ur));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 472 */       if (!this.mSelPath.isEmpty()) {
/* 473 */         this.mSelPath.reset();
/*     */       }
/* 475 */       this.mPdfViewCtrl.clearSelection();
/*     */ 
/*     */       
/* 478 */       HashMap<Annot, Integer> annots = new HashMap<>();
/* 479 */       Iterator<AnnotUpdateInfo> itr = updateInfoList.iterator();
/* 480 */       while (itr.hasNext()) {
/* 481 */         AnnotUpdateInfo updateInfo = itr.next();
/* 482 */         Annot annot = updateInfo.mAnnot;
/* 483 */         int pageNum = updateInfo.mPageNum;
/* 484 */         if (annot != null) {
/* 485 */           annots.put(annot, Integer.valueOf(pageNum));
/* 486 */           if (this.mPdfViewCtrl.isAnnotationLayerEnabled()) {
/* 487 */             this.mPdfViewCtrl.update(annot, pageNum); continue;
/*     */           } 
/* 489 */           Rect rect = updateInfo.mRect;
/* 490 */           this.mPdfViewCtrl.update(rect);
/*     */         } 
/*     */       } 
/*     */       
/* 494 */       raiseAnnotationAddedEvent(annots);
/*     */       
/* 496 */       this.mPdfViewCtrl.invalidate();
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 501 */     catch (Exception ex) {
/* 502 */       ((ToolManager)this.mPdfViewCtrl.getToolManager()).annotationCouldNotBeAdded(ex.getMessage());
/* 503 */       AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*     */     } finally {
/* 505 */       if (shouldUnlock) {
/* 506 */         this.mPdfViewCtrl.docUnlock();
/*     */       }
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
/*     */   protected void setAnnotRect(@Nullable Annot annot, Rect rect, int pageNum) throws PDFNetException {
/* 519 */     if (annot == null) {
/*     */       return;
/*     */     }
/* 522 */     annot.setRect(rect);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onMove(MotionEvent e1, MotionEvent e2, float x_dist, float y_dist) {
/* 530 */     super.onMove(e1, e2, x_dist, y_dist);
/*     */     
/* 532 */     if (this.mAllowTwoFingerScroll) {
/* 533 */       animateLoupe(false);
/* 534 */       this.mLoupeEnabled = false;
/* 535 */       return false;
/*     */     } 
/*     */     
/* 538 */     if (this.mAllowOneFingerScrollWithStylus) {
/* 539 */       animateLoupe(false);
/* 540 */       this.mLoupeEnabled = false;
/* 541 */       return false;
/*     */     } 
/*     */     
/* 544 */     float sx = this.mPdfViewCtrl.getScrollX();
/* 545 */     float sy = this.mPdfViewCtrl.getScrollY();
/*     */     
/* 547 */     selectText(this.mStationPt.x - sx, this.mStationPt.y - sy, e2.getX(), e2.getY(), false);
/* 548 */     this.mPdfViewCtrl.invalidate(this.mInvalidateBBox);
/* 549 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SuppressLint({"NewApi"})
/*     */   public void onDraw(Canvas canvas, Matrix tfm) {
/* 560 */     if (this.mAllowTwoFingerScroll) {
/*     */       return;
/*     */     }
/*     */     
/* 564 */     if (this.mIsPointOutsidePage) {
/*     */       return;
/*     */     }
/*     */     
/* 568 */     if (this.mAllowOneFingerScrollWithStylus) {
/*     */       return;
/*     */     }
/*     */     
/* 572 */     boolean loupeEnabled = this.mLoupeEnabled;
/* 573 */     this.mLoupeEnabled = false;
/* 574 */     if (!this.mDrawingLoupe) {
/* 575 */       super.onDraw(canvas, tfm);
/*     */     }
/* 577 */     this.mLoupeEnabled = loupeEnabled;
/*     */     
/* 579 */     if (this.mOnUpCalled) {
/*     */       
/* 581 */       drawLoupe();
/*     */ 
/*     */       
/* 584 */       if (!this.mSelPath.isEmpty()) {
/* 585 */         this.mPaint.setStyle(Paint.Style.FILL);
/* 586 */         this.mPaint.setColor(Color.rgb(0, 100, 175));
/* 587 */         this.mPaint.setAlpha(127);
/* 588 */         canvas.drawPath(this.mSelPath, this.mPaint);
/*     */       } 
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
/*     */ 
/*     */   
/*     */   protected boolean selectText(float x1, float y1, float x2, float y2, boolean byRect) {
/* 605 */     if (byRect) {
/* 606 */       float delta = 0.01F;
/* 607 */       x2 += delta;
/* 608 */       y2 += delta;
/* 609 */       delta *= 2.0F;
/* 610 */       x1 = (x2 - delta >= 0.0F) ? (x2 - delta) : 0.0F;
/* 611 */       y1 = (y2 - delta >= 0.0F) ? (y2 - delta) : 0.0F;
/*     */     } 
/* 613 */     boolean result = false;
/*     */ 
/*     */     
/* 616 */     boolean had_sel = !this.mSelPath.isEmpty();
/* 617 */     this.mSelPath.reset();
/*     */ 
/*     */     
/* 620 */     boolean shouldUnlockRead = false;
/*     */     
/*     */     try {
/* 623 */       this.mPdfViewCtrl.docLockRead();
/* 624 */       shouldUnlockRead = true;
/* 625 */       if (byRect) {
/* 626 */         result = this.mPdfViewCtrl.selectByRect(x1, y1, x2, y2);
/*     */       } else {
/* 628 */         result = this.mPdfViewCtrl.selectByStructWithSmartSnapping(x1, y1, x2, y2);
/*     */       } 
/* 630 */     } catch (Exception e) {
/* 631 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */     } finally {
/* 633 */       if (shouldUnlockRead) {
/* 634 */         this.mPdfViewCtrl.docUnlockRead();
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 641 */     if (had_sel) {
/* 642 */       this.mTempRect.set(this.mSelBBox);
/*     */     }
/* 644 */     populateSelectionResult();
/* 645 */     if (!had_sel) {
/* 646 */       this.mTempRect.set(this.mSelBBox);
/*     */     } else {
/* 648 */       this.mTempRect.union(this.mSelBBox);
/*     */     } 
/*     */     
/* 651 */     this.mTempRect.union(this.mLoupeBBox);
/* 652 */     setLoupeInfo(x2, y2);
/* 653 */     this.mTempRect.union(this.mLoupeBBox);
/*     */     
/* 655 */     return result;
/*     */   }
/*     */   
/*     */   private void populateSelectionResult() {
/* 659 */     float sx = this.mPdfViewCtrl.getScrollX();
/* 660 */     float sy = this.mPdfViewCtrl.getScrollY();
/* 661 */     int sel_pg_begin = this.mPdfViewCtrl.getSelectionBeginPage();
/* 662 */     int sel_pg_end = this.mPdfViewCtrl.getSelectionEndPage();
/* 663 */     float min_x = 1.0E10F, min_y = 1.0E10F, max_x = 0.0F, max_y = 0.0F;
/* 664 */     boolean has_sel = false;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 669 */     for (int pg = sel_pg_begin; pg <= sel_pg_end; pg++) {
/* 670 */       PDFViewCtrl.Selection sel = this.mPdfViewCtrl.getSelection(pg);
/* 671 */       double[] quads = sel.getQuads();
/*     */       
/* 673 */       int sz = quads.length / 8;
/*     */       
/* 675 */       if (sz != 0) {
/*     */ 
/*     */         
/* 678 */         int k = 0;
/*     */         
/* 680 */         for (int i = 0; i < sz; i++, k += 8) {
/* 681 */           has_sel = true;
/*     */           
/* 683 */           double[] pts = this.mPdfViewCtrl.convPagePtToScreenPt(quads[k], quads[k + 1], pg);
/* 684 */           float x = (float)pts[0] + sx;
/* 685 */           float y = (float)pts[1] + sy;
/* 686 */           this.mSelPath.moveTo(x, y);
/* 687 */           min_x = (min_x > x) ? x : min_x;
/* 688 */           max_x = (max_x < x) ? x : max_x;
/* 689 */           min_y = (min_y > y) ? y : min_y;
/* 690 */           max_y = (max_y < y) ? y : max_y;
/*     */           
/* 692 */           if (pg == sel_pg_begin && i == 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 697 */             min_x = (min_x > x) ? x : min_x;
/* 698 */             max_x = (max_x < x) ? x : max_x;
/*     */           } 
/*     */           
/* 701 */           pts = this.mPdfViewCtrl.convPagePtToScreenPt(quads[k + 2], quads[k + 3], pg);
/* 702 */           x = (float)pts[0] + sx;
/* 703 */           y = (float)pts[1] + sy;
/* 704 */           this.mSelPath.lineTo(x, y);
/* 705 */           min_x = (min_x > x) ? x : min_x;
/* 706 */           max_x = (max_x < x) ? x : max_x;
/* 707 */           min_y = (min_y > y) ? y : min_y;
/* 708 */           max_y = (max_y < y) ? y : max_y;
/*     */           
/* 710 */           if (pg == sel_pg_end && i == sz - 1) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 716 */             min_x = (min_x > x) ? x : min_x;
/* 717 */             max_x = (max_x < x) ? x : max_x;
/* 718 */             min_y = (min_y > y) ? y : min_y;
/* 719 */             max_y = (max_y < y) ? y : max_y;
/*     */           } 
/*     */           
/* 722 */           pts = this.mPdfViewCtrl.convPagePtToScreenPt(quads[k + 4], quads[k + 5], pg);
/* 723 */           x = (float)pts[0] + sx;
/* 724 */           y = (float)pts[1] + sy;
/* 725 */           this.mSelPath.lineTo(x, y);
/* 726 */           min_x = (min_x > x) ? x : min_x;
/* 727 */           max_x = (max_x < x) ? x : max_x;
/* 728 */           min_y = (min_y > y) ? y : min_y;
/* 729 */           max_y = (max_y < y) ? y : max_y;
/*     */           
/* 731 */           if (pg == sel_pg_end && i == sz - 1) {
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 736 */             min_x = (min_x > x) ? x : min_x;
/* 737 */             max_x = (max_x < x) ? x : max_x;
/*     */           } 
/*     */           
/* 740 */           pts = this.mPdfViewCtrl.convPagePtToScreenPt(quads[k + 6], quads[k + 7], pg);
/* 741 */           x = (float)pts[0] + sx;
/* 742 */           y = (float)pts[1] + sy;
/* 743 */           this.mSelPath.lineTo(x, y);
/* 744 */           min_x = (min_x > x) ? x : min_x;
/* 745 */           max_x = (max_x < x) ? x : max_x;
/* 746 */           min_y = (min_y > y) ? y : min_y;
/* 747 */           max_y = (max_y < y) ? y : max_y;
/*     */           
/* 749 */           if (pg == sel_pg_begin && i == 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 755 */             min_x = (min_x > x) ? x : min_x;
/* 756 */             max_x = (max_x < x) ? x : max_x;
/* 757 */             min_y = (min_y > y) ? y : min_y;
/* 758 */             max_y = (max_y < y) ? y : max_y;
/*     */           } 
/*     */           
/* 761 */           this.mSelPath.close();
/*     */         } 
/*     */       } 
/*     */     } 
/* 765 */     if (has_sel) {
/* 766 */       this.mSelBBox.set(min_x, min_y, max_x, max_y);
/* 767 */       this.mSelBBox.round(this.mInvalidateBBox);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doneTwoFingerScrolling() {
/* 776 */     super.doneTwoFingerScrolling();
/*     */ 
/*     */     
/* 779 */     if (this.mPdfViewCtrl.hasSelection()) {
/*     */       
/* 781 */       if (!this.mSelPath.isEmpty()) {
/* 782 */         this.mSelPath.reset();
/*     */       }
/* 784 */       this.mPdfViewCtrl.clearSelection();
/*     */     } 
/* 786 */     this.mPdfViewCtrl.invalidate();
/*     */   }
/*     */   
/*     */   private boolean hasAnnot(int x, int y) {
/* 790 */     boolean shouldUnlockRead = false;
/*     */     try {
/* 792 */       this.mPdfViewCtrl.docLockRead();
/* 793 */       shouldUnlockRead = true;
/* 794 */       Annot annot = this.mPdfViewCtrl.getAnnotationAt(x, y);
/* 795 */       if (isValidAnnot(annot)) {
/* 796 */         return true;
/*     */       }
/* 798 */     } catch (Exception ex) {
/* 799 */       AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*     */     } finally {
/* 801 */       if (shouldUnlockRead) {
/* 802 */         this.mPdfViewCtrl.docUnlockRead();
/*     */       }
/*     */     } 
/* 805 */     return false;
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
/*     */   protected abstract Annot createMarkup(PDFDoc paramPDFDoc, Rect paramRect) throws PDFNetException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onQuickMenuClicked(QuickMenuItem menuItem) {
/* 826 */     safeSetNextToolMode(getCurrentDefaultToolMode());
/* 827 */     Bundle bundle = new Bundle();
/* 828 */     bundle.putStringArray("PDFTRON_KEYS", new String[] { "menuItemId" });
/* 829 */     bundle.putInt("menuItemId", menuItem.getItemId());
/* 830 */     if (onInterceptAnnotationHandling(this.mAnnot, bundle)) {
/* 831 */       return true;
/*     */     }
/*     */     
/* 834 */     createTextMarkup();
/*     */     
/* 836 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canDrawLoupe() {
/* 841 */     return !this.mDrawingLoupe;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getLoupeType() {
/* 846 */     return 1;
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\TextMarkupCreate.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */