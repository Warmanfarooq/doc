/*     */ package com.pdftron.pdf.controls;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.util.AttributeSet;
/*     */ import android.util.Log;
/*     */ import android.view.MotionEvent;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.viewpager.widget.ViewPager;
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.ColorPt;
/*     */ import com.pdftron.pdf.PDFDoc;
/*     */ import com.pdftron.pdf.ReflowProcessor;
/*     */ import com.pdftron.pdf.tools.R;
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
/*     */ public class ReflowControl
/*     */   extends ViewPager
/*     */   implements ReflowPagerAdapter.ReflowPagerAdapterCallback
/*     */ {
/*  30 */   private static final String TAG = ReflowControl.class.getName();
/*     */   
/*     */   private static final String THROW_MESSAGE = "No PDF document has been set. Call setup(PDFDoc) or setup(PDFDoc, OnPostProcessColorListener) first.";
/*     */   
/*     */   public static final int HORIZONTAL = 0;
/*     */   public static final int VERTICAL = 1;
/*     */   private ReflowPagerAdapter mReflowPagerAdapter;
/*     */   Context mContext;
/*  38 */   private int mOrientation = 0;
/*     */   
/*     */   private List<OnReflowTapListener> mOnTapListeners;
/*     */ 
/*     */   
/*     */   public ReflowControl(Context context) {
/*  44 */     this(context, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReflowControl(Context context, AttributeSet attrs) {
/*  51 */     super(context, attrs);
/*  52 */     this.mContext = context;
/*  53 */     initializeReflowProcessor();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setup(@NonNull PDFDoc pdfDoc) {
/*  61 */     setup(pdfDoc, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setup(@NonNull PDFDoc pdfDoc, OnPostProcessColorListener listener) {
/*  71 */     this.mReflowPagerAdapter = new ReflowPagerAdapter(this, this.mContext, pdfDoc);
/*  72 */     this.mReflowPagerAdapter.setListener(this);
/*  73 */     this.mReflowPagerAdapter.setOnPostProcessColorListener(listener);
/*  74 */     setAdapter(this.mReflowPagerAdapter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOrientation() {
/*  81 */     return this.mOrientation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOrientation(int orientation) {
/*  88 */     this.mOrientation = orientation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isReady() {
/*  97 */     return (this.mReflowPagerAdapter != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyPagesModified() throws PDFNetException {
/* 107 */     if (this.mReflowPagerAdapter == null) {
/*     */       
/* 109 */       String name = (new Object() {  }).getClass().getEnclosingMethod().getName();
/* 110 */       Log.e(TAG, name + ": " + "No PDF document has been set. Call setup(PDFDoc) or setup(PDFDoc, OnPostProcessColorListener) first.");
/* 111 */       throw new PDFNetException("", 0L, TAG, name, "No PDF document has been set. Call setup(PDFDoc) or setup(PDFDoc, OnPostProcessColorListener) first.");
/*     */     } 
/* 113 */     this.mReflowPagerAdapter.onPagesModified();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() throws PDFNetException {
/* 124 */     initializeReflowProcessor();
/* 125 */     if (this.mReflowPagerAdapter == null) {
/*     */       
/* 127 */       String name = (new Object() {  }).getClass().getEnclosingMethod().getName();
/* 128 */       Log.e(TAG, name + ": " + "No PDF document has been set. Call setup(PDFDoc) or setup(PDFDoc, OnPostProcessColorListener) first.");
/* 129 */       throw new PDFNetException("", 0L, TAG, name, "No PDF document has been set. Call setup(PDFDoc) or setup(PDFDoc, OnPostProcessColorListener) first.");
/*     */     } 
/* 131 */     this.mReflowPagerAdapter.notifyDataSetChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cleanUp() {
/* 139 */     if (this.mReflowPagerAdapter != null) {
/* 140 */       this.mReflowPagerAdapter.cleanup();
/*     */     }
/*     */   }
/*     */   
/*     */   private void initializeReflowProcessor() {
/* 145 */     if (!ReflowProcessor.isInitialized()) {
/* 146 */       ReflowProcessor.initialize(this.mContext
/* 147 */           .getResources().getString(R.string.reflow_no_content), this.mContext
/* 148 */           .getResources().getString(R.string.reflow_failed));
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
/*     */   public void setTextSizeInPercent(int textSizeInPercent) throws PDFNetException {
/* 163 */     if (this.mReflowPagerAdapter == null) {
/*     */       
/* 165 */       String name = (new Object() {  }).getClass().getEnclosingMethod().getName();
/* 166 */       Log.e(TAG, name + ": " + "No PDF document has been set. Call setup(PDFDoc) or setup(PDFDoc, OnPostProcessColorListener) first.");
/* 167 */       throw new PDFNetException("", 0L, TAG, name, "No PDF document has been set. Call setup(PDFDoc) or setup(PDFDoc, OnPostProcessColorListener) first.");
/*     */     } 
/* 169 */     this.mReflowPagerAdapter.setTextSizeInPercent(textSizeInPercent);
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
/*     */   public int getTextSizeInPercent() throws PDFNetException {
/* 182 */     if (this.mReflowPagerAdapter == null) {
/*     */       
/* 184 */       String name = (new Object() {  }).getClass().getEnclosingMethod().getName();
/* 185 */       Log.e(TAG, name + ": " + "No PDF document has been set. Call setup(PDFDoc) or setup(PDFDoc, OnPostProcessColorListener) first.");
/* 186 */       throw new PDFNetException("", 0L, TAG, name, "No PDF document has been set. Call setup(PDFDoc) or setup(PDFDoc, OnPostProcessColorListener) first.");
/*     */     } 
/* 188 */     return this.mReflowPagerAdapter.getTextSizeInPercent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTextSize() {
/* 196 */     if (this.mReflowPagerAdapter != null) {
/* 197 */       this.mReflowPagerAdapter.setTextZoom();
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
/*     */   public void setCurrentPage(int pageNum) throws PDFNetException {
/* 210 */     if (this.mReflowPagerAdapter == null) {
/*     */       
/* 212 */       String name = (new Object() {  }).getClass().getEnclosingMethod().getName();
/* 213 */       Log.e(TAG, name + ": " + "No PDF document has been set. Call setup(PDFDoc) or setup(PDFDoc, OnPostProcessColorListener) first.");
/* 214 */       throw new PDFNetException("", 0L, TAG, name, "No PDF document has been set. Call setup(PDFDoc) or setup(PDFDoc, OnPostProcessColorListener) first.");
/*     */     } 
/* 216 */     this.mReflowPagerAdapter.setCurrentPage(pageNum);
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
/*     */   public int getCurrentPage() throws PDFNetException {
/* 229 */     if (this.mReflowPagerAdapter == null) {
/*     */       
/* 231 */       String name = (new Object() {  }).getClass().getEnclosingMethod().getName();
/* 232 */       Log.e(TAG, name + ": " + "No PDF document has been set. Call setup(PDFDoc) or setup(PDFDoc, OnPostProcessColorListener) first.");
/* 233 */       throw new PDFNetException("", 0L, TAG, name, "No PDF document has been set. Call setup(PDFDoc) or setup(PDFDoc, OnPostProcessColorListener) first.");
/*     */     } 
/* 235 */     return this.mReflowPagerAdapter.getCurrentPage();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDayMode() throws PDFNetException {
/* 246 */     if (this.mReflowPagerAdapter == null) {
/*     */       
/* 248 */       String name = (new Object() {  }).getClass().getEnclosingMethod().getName();
/* 249 */       Log.e(TAG, name + ": " + "No PDF document has been set. Call setup(PDFDoc) or setup(PDFDoc, OnPostProcessColorListener) first.");
/* 250 */       throw new PDFNetException("", 0L, TAG, name, "No PDF document has been set. Call setup(PDFDoc) or setup(PDFDoc, OnPostProcessColorListener) first.");
/*     */     } 
/* 252 */     this.mReflowPagerAdapter.setDayMode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNightMode() throws PDFNetException {
/* 263 */     if (this.mReflowPagerAdapter == null) {
/*     */       
/* 265 */       String name = (new Object() {  }).getClass().getEnclosingMethod().getName();
/* 266 */       Log.e(TAG, name + ": " + "No PDF document has been set. Call setup(PDFDoc) or setup(PDFDoc, OnPostProcessColorListener) first.");
/* 267 */       throw new PDFNetException("", 0L, TAG, name, "No PDF document has been set. Call setup(PDFDoc) or setup(PDFDoc, OnPostProcessColorListener) first.");
/*     */     } 
/* 269 */     this.mReflowPagerAdapter.setNightMode();
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
/*     */   public void setCustomColorMode(int backgroundColorMode) throws PDFNetException {
/* 282 */     if (this.mReflowPagerAdapter == null) {
/*     */       
/* 284 */       String name = (new Object() {  }).getClass().getEnclosingMethod().getName();
/* 285 */       Log.e(TAG, name + ": " + "No PDF document has been set. Call setup(PDFDoc) or setup(PDFDoc, OnPostProcessColorListener) first.");
/* 286 */       throw new PDFNetException("", 0L, TAG, name, "No PDF document has been set. Call setup(PDFDoc) or setup(PDFDoc, OnPostProcessColorListener) first.");
/*     */     } 
/* 288 */     this.mReflowPagerAdapter.setCustomColorMode(backgroundColorMode);
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
/*     */   public boolean isDayMode() throws PDFNetException {
/* 302 */     if (this.mReflowPagerAdapter == null) {
/*     */       
/* 304 */       String name = (new Object() {  }).getClass().getEnclosingMethod().getName();
/* 305 */       Log.e(TAG, name + ": " + "No PDF document has been set. Call setup(PDFDoc) or setup(PDFDoc, OnPostProcessColorListener) first.");
/* 306 */       throw new PDFNetException("", 0L, TAG, name, "No PDF document has been set. Call setup(PDFDoc) or setup(PDFDoc, OnPostProcessColorListener) first.");
/*     */     } 
/* 308 */     return this.mReflowPagerAdapter.isDayMode();
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
/*     */   public boolean isNightMode() throws PDFNetException {
/* 322 */     if (this.mReflowPagerAdapter == null) {
/*     */       
/* 324 */       String name = (new Object() {  }).getClass().getEnclosingMethod().getName();
/* 325 */       Log.e(TAG, name + ": " + "No PDF document has been set. Call setup(PDFDoc) or setup(PDFDoc, OnPostProcessColorListener) first.");
/* 326 */       throw new PDFNetException("", 0L, TAG, name, "No PDF document has been set. Call setup(PDFDoc) or setup(PDFDoc, OnPostProcessColorListener) first.");
/*     */     } 
/* 328 */     return this.mReflowPagerAdapter.isNightMode();
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
/*     */   public boolean isCustomColorMode() throws PDFNetException {
/* 342 */     if (this.mReflowPagerAdapter == null) {
/*     */       
/* 344 */       String name = (new Object() {  }).getClass().getEnclosingMethod().getName();
/* 345 */       Log.e(TAG, name + ": " + "No PDF document has been set. Call setup(PDFDoc) or setup(PDFDoc, OnPostProcessColorListener) first.");
/* 346 */       throw new PDFNetException("", 0L, TAG, name, "No PDF document has been set. Call setup(PDFDoc) or setup(PDFDoc, OnPostProcessColorListener) first.");
/*     */     } 
/* 348 */     return this.mReflowPagerAdapter.isCustomColorMode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRightToLeftDirection(boolean isRtlMode) throws PDFNetException {
/* 359 */     if (this.mReflowPagerAdapter == null) {
/*     */       
/* 361 */       String name = (new Object() {  }).getClass().getEnclosingMethod().getName();
/* 362 */       Log.e(TAG, name + ": " + "No PDF document has been set. Call setup(PDFDoc) or setup(PDFDoc, OnPostProcessColorListener) first.");
/* 363 */       throw new PDFNetException("", 0L, TAG, name, "No PDF document has been set. Call setup(PDFDoc) or setup(PDFDoc, OnPostProcessColorListener) first.");
/*     */     } 
/* 365 */     this.mReflowPagerAdapter.setRightToLeftDirection(isRtlMode);
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
/*     */   public boolean isRightToLeftDirection() throws PDFNetException {
/* 379 */     if (this.mReflowPagerAdapter == null) {
/*     */       
/* 381 */       String name = (new Object() {  }).getClass().getEnclosingMethod().getName();
/* 382 */       Log.e(TAG, name + ": " + "No PDF document has been set. Call setup(PDFDoc) or setup(PDFDoc, OnPostProcessColorListener) first.");
/* 383 */       throw new PDFNetException("", 0L, TAG, name, "No PDF document has been set. Call setup(PDFDoc) or setup(PDFDoc, OnPostProcessColorListener) first.");
/*     */     } 
/* 385 */     return this.mReflowPagerAdapter.isRightToLeftDirection();
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
/*     */   public void enableTurnPageOnTap(boolean enabled) throws PDFNetException {
/* 398 */     if (this.mReflowPagerAdapter == null) {
/*     */       
/* 400 */       String name = (new Object() {  }).getClass().getEnclosingMethod().getName();
/* 401 */       Log.e(TAG, name + ": " + "No PDF document has been set. Call setup(PDFDoc) or setup(PDFDoc, OnPostProcessColorListener) first.");
/* 402 */       throw new PDFNetException("", 0L, TAG, name, "No PDF document has been set. Call setup(PDFDoc) or setup(PDFDoc, OnPostProcessColorListener) first.");
/*     */     } 
/* 404 */     this.mReflowPagerAdapter.enableTurnPageOnTap(enabled);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void zoomIn() throws PDFNetException {
/* 415 */     if (this.mReflowPagerAdapter == null) {
/*     */       
/* 417 */       String name = (new Object() {  }).getClass().getEnclosingMethod().getName();
/* 418 */       Log.e(TAG, name + ": " + "No PDF document has been set. Call setup(PDFDoc) or setup(PDFDoc, OnPostProcessColorListener) first.");
/* 419 */       throw new PDFNetException("", 0L, TAG, name, "No PDF document has been set. Call setup(PDFDoc) or setup(PDFDoc, OnPostProcessColorListener) first.");
/*     */     } 
/* 421 */     this.mReflowPagerAdapter.zoomIn();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void zoomOut() throws PDFNetException {
/* 432 */     if (this.mReflowPagerAdapter == null) {
/*     */       
/* 434 */       String name = (new Object() {  }).getClass().getEnclosingMethod().getName();
/* 435 */       Log.e(TAG, name + ": " + "No PDF document has been set. Call setup(PDFDoc) or setup(PDFDoc, OnPostProcessColorListener) first.");
/* 436 */       throw new PDFNetException("", 0L, TAG, name, "No PDF document has been set. Call setup(PDFDoc) or setup(PDFDoc, OnPostProcessColorListener) first.");
/*     */     } 
/* 438 */     this.mReflowPagerAdapter.zoomOut();
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
/*     */   public boolean isInternalLinkClicked() throws PDFNetException {
/* 451 */     if (this.mReflowPagerAdapter == null) {
/*     */       
/* 453 */       String name = (new Object() {  }).getClass().getEnclosingMethod().getName();
/* 454 */       Log.e(TAG, name + ": " + "No PDF document has been set. Call setup(PDFDoc) or setup(PDFDoc, OnPostProcessColorListener) first.");
/* 455 */       throw new PDFNetException("", 0L, TAG, name, "No PDF document has been set. Call setup(PDFDoc) or setup(PDFDoc, OnPostProcessColorListener) first.");
/*     */     } 
/* 457 */     return this.mReflowPagerAdapter.isInternalLinkClicked();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetInternalLinkClicked() throws PDFNetException {
/* 468 */     if (this.mReflowPagerAdapter == null) {
/*     */       
/* 470 */       String name = (new Object() {  }).getClass().getEnclosingMethod().getName();
/* 471 */       Log.e(TAG, name + ": " + "No PDF document has been set. Call setup(PDFDoc) or setup(PDFDoc, OnPostProcessColorListener) first.");
/* 472 */       throw new PDFNetException("", 0L, TAG, name, "No PDF document has been set. Call setup(PDFDoc) or setup(PDFDoc, OnPostProcessColorListener) first.");
/*     */     } 
/* 474 */     this.mReflowPagerAdapter.resetInternalLinkClicked();
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
/*     */   public void setOnPostProcessColorListener(OnPostProcessColorListener listener) throws PDFNetException {
/* 488 */     if (this.mReflowPagerAdapter == null) {
/*     */       
/* 490 */       String name = (new Object() {  }).getClass().getEnclosingMethod().getName();
/* 491 */       Log.e(TAG, name + ": " + "No PDF document has been set. Call setup(PDFDoc) or setup(PDFDoc, OnPostProcessColorListener) first.");
/* 492 */       throw new PDFNetException("", 0L, TAG, name, "No PDF document has been set. Call setup(PDFDoc) or setup(PDFDoc, OnPostProcessColorListener) first.");
/*     */     } 
/* 494 */     this.mReflowPagerAdapter.setOnPostProcessColorListener(listener);
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
/*     */   public void addReflowOnTapListener(OnReflowTapListener listener) {
/* 508 */     if (this.mOnTapListeners == null) {
/* 509 */       this.mOnTapListeners = new ArrayList<>();
/*     */     }
/* 511 */     if (!this.mOnTapListeners.contains(listener)) {
/* 512 */       this.mOnTapListeners.add(listener);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeReflowOnTapListener(OnReflowTapListener listener) {
/* 523 */     if (this.mOnTapListeners != null) {
/* 524 */       this.mOnTapListeners.remove(listener);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearReflowOnTapListeners() {
/* 532 */     if (this.mOnTapListeners != null) {
/* 533 */       this.mOnTapListeners.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onReflowPagerSingleTapUp(MotionEvent event) {
/* 544 */     if (this.mOnTapListeners != null) {
/* 545 */       for (OnReflowTapListener listener : this.mOnTapListeners) {
/* 546 */         listener.onReflowSingleTapUp(event);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onInterceptTouchEvent(MotionEvent ev) {
/* 553 */     if (this.mOrientation == 1) {
/* 554 */       return false;
/*     */     }
/* 556 */     return super.onInterceptTouchEvent(ev);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onTouchEvent(MotionEvent ev) {
/* 561 */     if (this.mOrientation == 1) {
/* 562 */       return false;
/*     */     }
/* 564 */     return super.onTouchEvent(ev);
/*     */   }
/*     */   
/*     */   public static interface OnPostProcessColorListener {
/*     */     ColorPt getPostProcessedColor(ColorPt param1ColorPt);
/*     */   }
/*     */   
/*     */   public static interface OnReflowTapListener {
/*     */     void onReflowSingleTapUp(MotionEvent param1MotionEvent);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\controls\ReflowControl.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */