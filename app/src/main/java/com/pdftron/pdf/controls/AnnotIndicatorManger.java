/*     */ package com.pdftron.pdf.controls;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.graphics.Bitmap;
/*     */ import android.graphics.BitmapFactory;
/*     */ import android.graphics.Canvas;
/*     */ import android.graphics.ColorFilter;
/*     */ import android.graphics.Paint;
/*     */ import android.graphics.PorterDuff;
/*     */ import android.graphics.PorterDuffColorFilter;
/*     */ import android.graphics.RectF;
/*     */ import android.util.SparseArray;
/*     */ import androidx.annotation.DrawableRes;
/*     */ import androidx.annotation.IntRange;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.annotation.UiThread;
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.Annot;
/*     */ import com.pdftron.pdf.PDFDoc;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.Page;
/*     */ import com.pdftron.pdf.Point;
/*     */ import com.pdftron.pdf.Rect;
/*     */ import com.pdftron.pdf.annots.Ink;
/*     */ import com.pdftron.pdf.annots.Line;
/*     */ import com.pdftron.pdf.annots.Markup;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.tools.ToolManager;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.AnnotUtils;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.ArrayList;
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
/*     */ public class AnnotIndicatorManger
/*     */ {
/*     */   public static final int STATE_IS_NORMAL = 0;
/*     */   public static final int STATE_IS_ZOOMING = 1;
/*     */   public static final int STATE_IS_FLUNG = 2;
/*     */   private static final int INDICATOR_ICON_SIZE = 8;
/*  62 */   private final Object mPrepareAnnotIndicatorLock = new Object();
/*  63 */   private final List<Integer> mAnnotIndicatorTaskList = new ArrayList<>();
/*  64 */   private final SparseArray<List<AnnotIndicator>> mAnnotIndicators = new SparseArray();
/*  65 */   private final Paint mAnnotInsideIndicatorPaint = new Paint();
/*  66 */   private final Paint mAnnotOutsideIndicatorPaint = new Paint();
/*     */   
/*  68 */   private int mState = 0;
/*     */   private Bitmap mInsideIndicatorBitmap;
/*     */   private Bitmap mOutsideIndicatorBitmap;
/*     */   private Bitmap mCustomBitmap;
/*     */   private int mIndicatorIconSize;
/*     */   private PDFViewCtrl.PagePresentationMode mLastPagePresentationMode;
/*     */   private WeakReference<Context> mContextRef;
/*     */   private WeakReference<PDFViewCtrl> mPdfViewCtrlRef;
/*     */   private WeakReference<ToolManager> mToolManagerRef;
/*     */   private boolean shouldRunAgain = true;
/*     */   private boolean mCanceled;
/*     */   
/*     */   public AnnotIndicatorManger(ToolManager toolManager) {
/*  81 */     this.mToolManagerRef = new WeakReference<>(toolManager);
/*  82 */     if (toolManager.getPDFViewCtrl() == null) {
/*  83 */       throw new NullPointerException("PDFfViewCtrl can't be null");
/*     */     }
/*  85 */     Context context = toolManager.getPDFViewCtrl().getContext();
/*  86 */     this.mContextRef = new WeakReference<>(context);
/*  87 */     this.mPdfViewCtrlRef = new WeakReference<>(toolManager.getPDFViewCtrl());
/*     */     
/*  89 */     this.mInsideIndicatorBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.indicator_inside);
/*  90 */     this.mIndicatorIconSize = (int)Utils.convDp2Pix(context, 8.0F);
/*  91 */     this.mAnnotInsideIndicatorPaint.setAlpha(178);
/*  92 */     this.mOutsideIndicatorBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.indicator_outside);
/*  93 */     this.mAnnotOutsideIndicatorPaint.setAlpha(178);
/*  94 */     this.mAnnotOutsideIndicatorPaint.setColor(context.getResources().getColor(R.color.dark_gray));
/*     */     
/*  96 */     (new Thread(new Runnable() {
/*     */           public void run() {
/*  98 */             while (AnnotIndicatorManger.this.shouldRunAgain && AnnotIndicatorManger.this.mContextRef.get() != null) {
/*  99 */               AnnotIndicatorManger.this.execute();
/* 100 */               AnnotIndicatorManger.this.mCanceled = false;
/*     */             } 
/*     */           }
/* 103 */         })).start();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @UiThread
/*     */   public void drawAnnotIndicators(Canvas canvas) {
/* 113 */     PDFViewCtrl pdfViewCtrl = this.mPdfViewCtrlRef.get();
/* 114 */     if (pdfViewCtrl == null) {
/*     */       return;
/*     */     }
/*     */     
/* 118 */     if (this.mLastPagePresentationMode != pdfViewCtrl.getPagePresentationMode()) {
/* 119 */       this.mLastPagePresentationMode = pdfViewCtrl.getPagePresentationMode();
/* 120 */       reset(true);
/*     */     } 
/*     */     
/* 123 */     for (int pageNum : pdfViewCtrl.getVisiblePagesInTransition()) {
/*     */       List<AnnotIndicator> annotIndicators;
/* 125 */       synchronized (this.mAnnotIndicators) {
/* 126 */         annotIndicators = (List<AnnotIndicator>)this.mAnnotIndicators.get(pageNum);
/*     */       } 
/* 128 */       if (annotIndicators == null && this.mState != 2 && this.mState != 1)
/*     */       {
/*     */         
/* 131 */         makeAnnotIndicators(pageNum);
/*     */       }
/*     */       
/* 134 */       if (annotIndicators != null) {
/* 135 */         for (AnnotIndicator annotIndicator : annotIndicators) {
/* 136 */           PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(annotIndicator.color, PorterDuff.Mode.SRC_IN);
/* 137 */           this.mAnnotInsideIndicatorPaint.setColorFilter((ColorFilter)porterDuffColorFilter);
/* 138 */           RectF locRect = new RectF(annotIndicator.x, annotIndicator.y - this.mIndicatorIconSize, annotIndicator.x + this.mIndicatorIconSize, annotIndicator.y);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 143 */           if (pdfViewCtrl.isMaintainZoomEnabled()) {
/* 144 */             canvas.save();
/*     */             
/*     */             try {
/* 147 */               int dx = (pdfViewCtrl.isCurrentSlidingCanvas(pageNum) ? 0 : pdfViewCtrl.getSlidingScrollX()) - pdfViewCtrl.getScrollXOffsetInTools(pageNum);
/*     */               
/* 149 */               int dy = (pdfViewCtrl.isCurrentSlidingCanvas(pageNum) ? 0 : pdfViewCtrl.getSlidingScrollY()) - pdfViewCtrl.getScrollYOffsetInTools(pageNum);
/* 150 */               canvas.translate(dx, dy);
/* 151 */               if (this.mCustomBitmap != null) {
/* 152 */                 canvas.drawBitmap(this.mCustomBitmap, null, locRect, null);
/*     */               } else {
/* 154 */                 canvas.drawBitmap(this.mOutsideIndicatorBitmap, null, locRect, this.mAnnotOutsideIndicatorPaint);
/* 155 */                 canvas.drawBitmap(this.mInsideIndicatorBitmap, null, locRect, this.mAnnotInsideIndicatorPaint);
/* 156 */                 canvas.drawBitmap(this.mOutsideIndicatorBitmap, null, locRect, this.mAnnotInsideIndicatorPaint);
/*     */               } 
/*     */             } finally {
/* 159 */               canvas.restore();
/*     */             }  continue;
/*     */           } 
/* 162 */           if (this.mCustomBitmap != null) {
/* 163 */             canvas.drawBitmap(this.mCustomBitmap, null, locRect, null); continue;
/*     */           } 
/* 165 */           canvas.drawBitmap(this.mOutsideIndicatorBitmap, null, locRect, this.mAnnotOutsideIndicatorPaint);
/* 166 */           canvas.drawBitmap(this.mInsideIndicatorBitmap, null, locRect, this.mAnnotInsideIndicatorPaint);
/* 167 */           canvas.drawBitmap(this.mOutsideIndicatorBitmap, null, locRect, this.mAnnotInsideIndicatorPaint);
/*     */         } 
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
/*     */   public void setCustomIndicatorBitmap(@Nullable Bitmap icon) {
/* 183 */     this.mCustomBitmap = icon;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCustomIndicatorBitmap(@DrawableRes int drawableId) {
/* 194 */     Context context = this.mContextRef.get();
/* 195 */     if (context != null) {
/* 196 */       this.mCustomBitmap = BitmapFactory.decodeResource(context.getResources(), drawableId);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIndicatorIconSize(@IntRange(from = 0L) int size) {
/* 207 */     this.mIndicatorIconSize = size;
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
/*     */   public void updateState(int state) {
/* 219 */     this.mState = state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset(boolean all) {
/* 229 */     PDFViewCtrl pdfViewCtrl = this.mPdfViewCtrlRef.get();
/* 230 */     if (pdfViewCtrl == null) {
/*     */       return;
/*     */     }
/*     */     
/* 234 */     if (all) {
/* 235 */       synchronized (this.mAnnotIndicators) {
/* 236 */         this.mAnnotIndicators.clear();
/*     */       } 
/* 238 */       synchronized (this.mAnnotIndicatorTaskList) {
/* 239 */         this.mAnnotIndicatorTaskList.clear();
/*     */       } 
/*     */       
/* 242 */       this.mCanceled = true;
/* 243 */       synchronized (this.mPrepareAnnotIndicatorLock) {
/* 244 */         this.mPrepareAnnotIndicatorLock.notifyAll();
/*     */       } 
/*     */     } else {
/* 247 */       for (int pageNum : pdfViewCtrl.getVisiblePagesInTransition()) {
/* 248 */         synchronized (this.mAnnotIndicators) {
/* 249 */           this.mAnnotIndicators.remove(pageNum);
/*     */         } 
/* 251 */         synchronized (this.mAnnotIndicatorTaskList) {
/* 252 */           this.mAnnotIndicatorTaskList.remove(Integer.valueOf(pageNum));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cleanup() {
/* 262 */     this.shouldRunAgain = false;
/* 263 */     this.mCanceled = true;
/* 264 */     synchronized (this.mPrepareAnnotIndicatorLock) {
/* 265 */       this.mPrepareAnnotIndicatorLock.notifyAll();
/*     */     } 
/*     */   }
/*     */   
/*     */   @UiThread
/*     */   private void makeAnnotIndicators(int pageNum) {
/* 271 */     PDFViewCtrl pdfViewCtrl = this.mPdfViewCtrlRef.get();
/* 272 */     if (pdfViewCtrl == null) {
/*     */       return;
/*     */     }
/*     */     
/* 276 */     boolean visible = false;
/* 277 */     for (int i : pdfViewCtrl.getVisiblePages()) {
/* 278 */       if (i == pageNum) {
/* 279 */         visible = true;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 284 */     synchronized (this.mAnnotIndicatorTaskList) {
/* 285 */       if (this.mAnnotIndicatorTaskList.contains(Integer.valueOf(pageNum))) {
/* 286 */         if (((Integer)this.mAnnotIndicatorTaskList.get(0)).intValue() == pageNum) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/* 291 */         if (!visible) {
/*     */           return;
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 297 */         this.mAnnotIndicatorTaskList.remove(Integer.valueOf(pageNum));
/*     */       } 
/*     */       
/* 300 */       if (visible) {
/*     */         
/* 302 */         this.mAnnotIndicatorTaskList.add(0, Integer.valueOf(pageNum));
/*     */       } else {
/*     */         
/* 305 */         this.mAnnotIndicatorTaskList.add(Integer.valueOf(pageNum));
/*     */       } 
/*     */     } 
/* 308 */     synchronized (this.mPrepareAnnotIndicatorLock) {
/* 309 */       this.mPrepareAnnotIndicatorLock.notifyAll();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void execute() {
/* 314 */     Integer pageNum = null;
/*     */     
/* 316 */     while (!isCanceled()) {
/*     */       try {
/* 318 */         while (!isCanceled()) {
/* 319 */           synchronized (this.mAnnotIndicatorTaskList) {
/* 320 */             if (!this.mAnnotIndicatorTaskList.isEmpty()) {
/* 321 */               pageNum = this.mAnnotIndicatorTaskList.get(0);
/*     */               break;
/*     */             } 
/*     */           } 
/* 325 */           synchronized (this.mPrepareAnnotIndicatorLock) {
/* 326 */             this.mPrepareAnnotIndicatorLock.wait();
/*     */           } 
/*     */         } 
/*     */         
/* 330 */         if (isCanceled()) {
/*     */           return;
/*     */         }
/*     */         
/* 334 */         prepareAnnotIndicators(pageNum);
/* 335 */       } catch (InterruptedException interruptedException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void prepareAnnotIndicators(Integer pageNumI) {
/* 341 */     PDFViewCtrl pdfViewCtrl = this.mPdfViewCtrlRef.get();
/* 342 */     if (pdfViewCtrl == null) {
/*     */       return;
/*     */     }
/*     */     
/* 346 */     final int pageNum = pageNumI.intValue();
/* 347 */     PDFDoc doc = pdfViewCtrl.getDoc();
/* 348 */     if (doc == null) {
/*     */       return;
/*     */     }
/*     */     
/* 352 */     if (isCanceled()) {
/*     */       return;
/*     */     }
/*     */     
/* 356 */     final List<AnnotIndicator> annotIndicatorList = new ArrayList<>();
/* 357 */     ArrayList<Annot> annots = null;
/* 358 */     Page page = null;
/* 359 */     boolean shouldUnlockRead = false;
/*     */     try {
/* 361 */       shouldUnlockRead = pdfViewCtrl.docTryLockRead(100);
/* 362 */       if (shouldUnlockRead) {
/* 363 */         page = doc.getPage(pageNum);
/* 364 */         if (page != null && page.isValid()) {
/* 365 */           annots = pdfViewCtrl.getAnnotationsOnPage(pageNum);
/*     */         }
/*     */       } 
/* 368 */     } catch (PDFNetException e) {
/* 369 */       AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*     */     } finally {
/* 371 */       if (shouldUnlockRead) {
/* 372 */         pdfViewCtrl.docUnlockRead();
/*     */       }
/*     */     } 
/*     */     
/* 376 */     if (annots != null) {
/* 377 */       for (Annot annot : annots) {
/* 378 */         if (isCanceled()) {
/*     */           return;
/*     */         }
/*     */         
/* 382 */         synchronized (this.mAnnotIndicatorTaskList) {
/* 383 */           if (!this.mAnnotIndicatorTaskList.contains(pageNumI)) {
/*     */             return;
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/* 389 */         boolean shouldUnlockTryRead = false;
/*     */         
/* 391 */         try { shouldUnlockTryRead = pdfViewCtrl.docTryLockRead(100);
/* 392 */           if (shouldUnlockTryRead)
/* 393 */           { if (annot == null || !annot.isValid())
/*     */             
/*     */             { 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 409 */               if (shouldUnlockTryRead)
/* 410 */                 pdfViewCtrl.docUnlockRead();  continue; }  if (!shouldShowIndicator(annot)) { if (shouldUnlockTryRead) pdfViewCtrl.docUnlockRead();  continue; }  if (isCanceled()) return;  addAnnot(pdfViewCtrl, annotIndicatorList, annot, page, pageNum); }  } catch (PDFNetException e) { AnalyticsHandlerAdapter.getInstance().sendException((Exception)e); } finally { if (shouldUnlockTryRead) pdfViewCtrl.docUnlockRead();
/*     */            }
/*     */       
/*     */       } 
/*     */     }
/*     */     
/* 416 */     boolean invalidate = false;
/* 417 */     synchronized (this.mAnnotIndicatorTaskList) {
/* 418 */       if (this.mAnnotIndicatorTaskList.contains(Integer.valueOf(pageNum))) {
/* 419 */         this.mAnnotIndicatorTaskList.remove(pageNumI);
/* 420 */         invalidate = true;
/*     */       } 
/*     */     } 
/* 423 */     if (invalidate)
/* 424 */       pdfViewCtrl.post(new Runnable()
/*     */           {
/*     */             public void run() {
/* 427 */               if (AnnotIndicatorManger.this.isCanceled()) {
/*     */                 return;
/*     */               }
/* 430 */               PDFViewCtrl pdfViewCtrl = AnnotIndicatorManger.this.mPdfViewCtrlRef.get();
/* 431 */               if (pdfViewCtrl == null) {
/*     */                 return;
/*     */               }
/* 434 */               synchronized (AnnotIndicatorManger.this.mAnnotIndicators) {
/* 435 */                 AnnotIndicatorManger.this.mAnnotIndicators.put(pageNum, annotIndicatorList);
/*     */               } 
/* 437 */               pdfViewCtrl.invalidate();
/*     */             }
/*     */           }); 
/*     */   }
/*     */   
/*     */   private void addAnnot(PDFViewCtrl pdfViewCtrl, List<AnnotIndicator> annotList, Annot annot, Page page, int pageNum) throws PDFNetException {
/*     */     Line line;
/*     */     Point ptStart, ptEnd;
/*     */     double c1, c2, r1, r2, thresh, temp;
/*     */     Ink ink;
/*     */     int pathIndex, cnt;
/* 448 */     Rect rect = pdfViewCtrl.getPageRectForAnnot(annot, pageNum);
/*     */ 
/*     */     
/* 451 */     double x1 = rect.getX1();
/* 452 */     double x2 = rect.getX2();
/* 453 */     double y1 = rect.getY1();
/* 454 */     double y2 = rect.getY2();
/* 455 */     double x = x2;
/* 456 */     double y = y2;
/* 457 */     int rotation = page.getRotation();
/* 458 */     if (rotation == 1 || rotation == 2) {
/* 459 */       x = x1;
/*     */     }
/* 461 */     if (rotation == 3 || rotation == 2) {
/* 462 */       y = y1;
/*     */     }
/* 464 */     int type = annot.getType();
/* 465 */     switch (type) {
/*     */       case 3:
/* 467 */         line = new Line(annot);
/* 468 */         ptStart = line.getStartPoint();
/* 469 */         ptEnd = line.getEndPoint();
/*     */         
/* 471 */         switch (page.getRotation()) {
/*     */           case 1:
/* 473 */             if (ptStart.x < ptEnd.x) {
/* 474 */               y = ptStart.y; break;
/*     */             } 
/* 476 */             y = ptEnd.y;
/*     */             break;
/*     */           
/*     */           case 2:
/* 480 */             if (ptStart.y < ptEnd.y) {
/* 481 */               x = ptStart.x; break;
/*     */             } 
/* 483 */             x = ptEnd.x;
/*     */             break;
/*     */           
/*     */           case 3:
/* 487 */             if (ptStart.x > ptEnd.x) {
/* 488 */               y = ptStart.y; break;
/*     */             } 
/* 490 */             y = ptEnd.y;
/*     */             break;
/*     */         } 
/*     */         
/* 494 */         if (ptStart.y > ptEnd.y) {
/* 495 */           x = ptStart.x; break;
/*     */         } 
/* 497 */         x = ptEnd.x;
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 5:
/* 504 */         c1 = (x1 + x2) / 2.0D;
/* 505 */         c2 = (y1 + y2) / 2.0D;
/* 506 */         r1 = Math.abs(x1 - x2) * Math.abs(x1 - x2) / 4.0D;
/* 507 */         r2 = Math.abs(y1 - y2) * Math.abs(y1 - y2) / 4.0D;
/*     */         
/* 509 */         switch (page.getRotation()) {
/*     */           case 1:
/* 511 */             thresh = Math.min(x1, x2) + Math.abs(x1 - x2) * 0.15D;
/* 512 */             x = thresh;
/* 513 */             temp = (x - c1) * (x - c1) / r1;
/* 514 */             temp = (1.0D - temp) * r2;
/* 515 */             y = c2 + Math.sqrt(temp);
/*     */             break;
/*     */           case 2:
/* 518 */             thresh = Math.min(y1, y2) + Math.abs(y1 - y2) * 0.15D;
/* 519 */             y = thresh;
/* 520 */             temp = (y - c2) * (y - c2) / r2;
/* 521 */             temp = (1.0D - temp) * r1;
/* 522 */             x = c1 - Math.sqrt(temp);
/*     */             break;
/*     */           case 3:
/* 525 */             thresh = Math.min(x1, x2) + Math.abs(x1 - x2) * 0.85D;
/* 526 */             x = thresh;
/* 527 */             temp = (x - c1) * (x - c1) / r1;
/* 528 */             temp = (1.0D - temp) * r2;
/* 529 */             y = c2 - Math.sqrt(temp);
/*     */             break;
/*     */         } 
/* 532 */         thresh = Math.min(y1, y2) + Math.abs(y1 - y2) * 0.85D;
/* 533 */         y = thresh;
/* 534 */         temp = (y - c2) * (y - c2) / r2;
/* 535 */         temp = (1.0D - temp) * r1;
/* 536 */         x = Math.sqrt(temp) + c1;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 14:
/* 541 */         ink = new Ink(annot);
/* 542 */         switch (page.getRotation()) {
/*     */           case 1:
/* 544 */             thresh = Math.min(x1, x2) + Math.abs(x1 - x2) * 0.15D;
/* 545 */             y = y1;
/*     */             break;
/*     */           case 2:
/* 548 */             thresh = Math.min(y1, y2) + Math.abs(y1 - y2) * 0.15D;
/* 549 */             x = x2;
/*     */             break;
/*     */           case 3:
/* 552 */             thresh = Math.min(x1, x2) + Math.abs(x1 - x2) * 0.85D;
/* 553 */             y = y2;
/*     */             break;
/*     */           default:
/* 556 */             thresh = Math.min(y1, y2) + Math.abs(y1 - y2) * 0.85D;
/* 557 */             x = x1;
/*     */             break;
/*     */         } 
/*     */         
/* 561 */         for (pathIndex = 0, cnt = ink.getPathCount(); pathIndex < cnt; pathIndex++) {
/* 562 */           if (isCanceled()) {
/*     */             return;
/*     */           }
/*     */           
/* 566 */           int pointCount = ink.getPointCount(pathIndex);
/* 567 */           for (int pointIndex = 0; pointIndex < pointCount; pointIndex++) {
/* 568 */             if (isCanceled()) {
/*     */               return;
/*     */             }
/*     */             
/* 572 */             Point p = ink.GetPoint(pathIndex, pointIndex);
/*     */             
/* 574 */             switch (page.getRotation()) {
/*     */               case 1:
/* 576 */                 if (p.x < thresh && 
/* 577 */                   p.y > y) {
/* 578 */                   x = p.x;
/* 579 */                   y = p.y;
/*     */                 } 
/*     */                 break;
/*     */               
/*     */               case 2:
/* 584 */                 if (p.y < thresh && 
/* 585 */                   p.x < x) {
/* 586 */                   x = p.x;
/* 587 */                   y = p.y;
/*     */                 } 
/*     */                 break;
/*     */               
/*     */               case 3:
/* 592 */                 if (p.x > thresh && 
/* 593 */                   p.y < y) {
/* 594 */                   x = p.x;
/* 595 */                   y = p.y;
/*     */                 } 
/*     */                 break;
/*     */               
/*     */               default:
/* 600 */                 if (p.y > thresh && 
/* 601 */                   p.x > x) {
/* 602 */                   x = p.x;
/* 603 */                   y = p.y;
/*     */                 } 
/*     */                 break;
/*     */             } 
/*     */           
/*     */           } 
/*     */         } 
/*     */         break;
/*     */     } 
/*     */     
/* 613 */     if (pdfViewCtrl.isContinuousPagePresentationMode(pdfViewCtrl.getPagePresentationMode())) {
/* 614 */       double[] point = pdfViewCtrl.convPagePtToScreenPt(x, y, pageNum);
/* 615 */       x = point[0] + pdfViewCtrl.getScrollX();
/* 616 */       y = point[1] + pdfViewCtrl.getScrollY();
/*     */     } else {
/* 618 */       double[] point = pdfViewCtrl.convPagePtToHorizontalScrollingPt(x, y, pageNum);
/* 619 */       x = point[0];
/* 620 */       y = point[1];
/*     */     } 
/*     */     
/* 623 */     if (isCanceled()) {
/*     */       return;
/*     */     }
/*     */     
/* 627 */     annotList.add(new AnnotIndicator(pageNum, Utils.colorPt2color(annot.getColorAsRGB()), (float)x, (float)y));
/*     */   }
/*     */   
/*     */   private boolean shouldShowIndicator(Annot annot) {
/* 631 */     if (annot == null) {
/* 632 */       return false;
/*     */     }
/* 634 */     ToolManager toolManager = this.mToolManagerRef.get();
/* 635 */     if (toolManager == null) {
/* 636 */       return false;
/*     */     }
/*     */     
/*     */     try {
/* 640 */       int type = annot.getType();
/* 641 */       if (!annot.isValid() || 
/* 642 */         !annot.isMarkup() || annot
/* 643 */         .getFlag(1) || type == 2 || type == 0)
/*     */       {
/*     */         
/* 646 */         return false;
/*     */       }
/*     */       
/* 649 */       if (AnnotUtils.isRuler(annot)) {
/* 650 */         return false;
/*     */       }
/*     */       
/* 653 */       if (toolManager.getAnnotManager() != null) {
/* 654 */         return toolManager.getAnnotManager().shouldShowIndicator(annot);
/*     */       }
/*     */       
/* 657 */       Markup markup = new Markup(annot);
/* 658 */       String note = markup.getContents();
/* 659 */       if (note == null || note.equals("")) {
/* 660 */         return false;
/*     */       }
/*     */       
/* 663 */       if (Utils.isTextCopy((Annot)markup)) {
/* 664 */         return false;
/*     */       }
/* 666 */     } catch (Exception ignored) {
/* 667 */       return false;
/*     */     } 
/*     */     
/* 670 */     return true;
/*     */   }
/*     */   
/*     */   private boolean isCanceled() {
/* 674 */     return (this.mCanceled || this.mContextRef.get() == null);
/*     */   }
/*     */   
/*     */   private class AnnotIndicator { int pageNum;
/*     */     int color;
/*     */     float x;
/*     */     float y;
/*     */     
/*     */     AnnotIndicator(int pageNum, int color, float x, float y) {
/* 683 */       this.pageNum = pageNum;
/* 684 */       this.color = color;
/* 685 */       this.x = x;
/* 686 */       this.y = y;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\controls\AnnotIndicatorManger.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */