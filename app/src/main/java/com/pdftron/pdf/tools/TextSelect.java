/*      */ package com.pdftron.pdf.tools;
/*      */ 
/*      */ import android.content.ActivityNotFoundException;
/*      */ import android.content.ClipData;
/*      */ import android.content.ClipboardManager;
/*      */ import android.content.Intent;
/*      */ import android.content.res.Configuration;
/*      */ import android.graphics.Bitmap;
/*      */ import android.graphics.Canvas;
/*      */ import android.graphics.Matrix;
/*      */ import android.graphics.Paint;
/*      */ import android.graphics.Path;
/*      */ import android.graphics.PointF;
/*      */ import android.graphics.PorterDuff;
/*      */ import android.graphics.PorterDuffXfermode;
/*      */ import android.graphics.Rect;
/*      */ import android.graphics.RectF;
/*      */ import android.graphics.Xfermode;
/*      */ import android.graphics.drawable.BitmapDrawable;
/*      */ import android.media.AudioManager;
/*      */ import android.speech.tts.TextToSpeech;
/*      */ import android.view.KeyEvent;
/*      */ import android.view.MotionEvent;
/*      */ import androidx.annotation.Keep;
/*      */ import androidx.annotation.NonNull;
/*      */ import com.pdftron.common.PDFNetException;
/*      */ import com.pdftron.pdf.PDFViewCtrl;
/*      */ import com.pdftron.pdf.Page;
/*      */ import com.pdftron.pdf.Rect;
/*      */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*      */ import com.pdftron.pdf.utils.CommonToast;
/*      */ import com.pdftron.pdf.utils.ShortcutHelper;
/*      */ import com.pdftron.pdf.utils.Utils;
/*      */ import com.pdftron.pdf.utils.ViewerUtils;
/*      */ import java.io.File;
/*      */ import java.util.LinkedList;
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
/*      */ @Keep
/*      */ public class TextSelect
/*      */   extends BaseTool
/*      */ {
/*      */   protected final float mTSWidgetThickness;
/*      */   protected boolean mIsRightToLeft;
/*      */   protected boolean mIsNightMode = false;
/*      */   LinkedList<RectF> mSelRects;
/*      */   Path mSelPath;
/*      */   boolean mBeingLongPressed;
/*      */   boolean mBeingPressed;
/*      */   RectF mSelBBox;
/*      */   RectF mTempRect;
/*      */   Rect mTempRotationRect;
/*      */   PointF[] mQuadPoints;
/*      */   Rect mInvalidateBBox;
/*      */   boolean mScaled;
/*      */   PDFViewCtrl.PagePresentationMode mPagePresModeWhileSelected;
/*      */   int mEffSelWidgetId;
/*      */   boolean mSelWidgetEnabled;
/*      */   SelWidget[] mSelWidgets;
/*      */   PointF mStationPt;
/*      */   Paint mPaint;
/*      */   int mSelColor;
/*      */   PorterDuffXfermode mBlendmode;
/*      */   private TextToSpeech mTTS;
/*      */   
/*      */   public TextSelect(@NonNull PDFViewCtrl ctrl) {
/*   82 */     super(ctrl);
/*      */     
/*   84 */     this.mTSWidgetThickness = convDp2Pix(2.0F);
/*      */     
/*   86 */     this.mSelRects = new LinkedList<>();
/*   87 */     this.mSelPath = new Path();
/*   88 */     this.mPaint = new Paint();
/*   89 */     this.mPaint.setAntiAlias(true);
/*   90 */     this.mBeingLongPressed = false;
/*   91 */     this.mBeingPressed = false;
/*   92 */     this.mSelBBox = new RectF();
/*   93 */     this.mTempRect = new RectF();
/*      */     try {
/*   95 */       this.mTempRotationRect = new Rect();
/*   96 */     } catch (PDFNetException e) {
/*   97 */       this.mTempRotationRect = null;
/*      */     } 
/*   99 */     this.mQuadPoints = new PointF[4];
/*  100 */     this.mQuadPoints[0] = new PointF();
/*  101 */     this.mQuadPoints[1] = new PointF();
/*  102 */     this.mQuadPoints[2] = new PointF();
/*  103 */     this.mQuadPoints[3] = new PointF();
/*  104 */     this.mInvalidateBBox = new Rect();
/*  105 */     this.mEffSelWidgetId = -1;
/*  106 */     this.mSelWidgetEnabled = false;
/*  107 */     this.mSelWidgets = new SelWidget[2];
/*  108 */     this.mSelWidgets[0] = new SelWidget();
/*  109 */     this.mSelWidgets[1] = new SelWidget();
/*  110 */     (this.mSelWidgets[0]).mStrPt = new PointF();
/*  111 */     (this.mSelWidgets[0]).mEndPt = new PointF();
/*  112 */     (this.mSelWidgets[1]).mStrPt = new PointF();
/*  113 */     (this.mSelWidgets[1]).mEndPt = new PointF();
/*  114 */     this.mStationPt = new PointF();
/*  115 */     this.mDrawingLoupe = false;
/*  116 */     this.mScaled = false;
/*      */     
/*  118 */     this.mIsRightToLeft = this.mPdfViewCtrl.getRightToLeftLanguage();
/*      */     try {
/*  120 */       this.mIsNightMode = ((ToolManager)this.mPdfViewCtrl.getToolManager()).isNightMode();
/*  121 */     } catch (Exception e) {
/*  122 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } 
/*      */     
/*  125 */     this.mSelColor = this.mPdfViewCtrl.getContext().getResources().getColor(this.mIsNightMode ? R.color.tools_text_select_color_dark : R.color.tools_text_select_color);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  130 */     this.mBlendmode = this.mIsNightMode ? new PorterDuffXfermode(PorterDuff.Mode.SCREEN) : new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ToolManager.ToolModeBase getToolMode() {
/*  139 */     return ToolManager.ToolMode.TEXT_SELECT;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getCreateAnnotType() {
/*  144 */     return 28;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onCreate() {
/*  152 */     super.onCreate();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected QuickMenu createQuickMenu() {
/*  158 */     QuickMenu quickMenu = super.createQuickMenu();
/*  159 */     quickMenu.initMenuEntries(R.menu.text_select, 4);
/*  160 */     return quickMenu;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onCustomEvent(Object o) {
/*  168 */     this.mNextToolMode = ToolManager.ToolMode.PAN;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onDown(MotionEvent e) {
/*  176 */     super.onDown(e);
/*  177 */     this.mLoupeEnabled = true;
/*  178 */     float x = e.getX() + this.mPdfViewCtrl.getScrollX();
/*  179 */     float y = e.getY() + this.mPdfViewCtrl.getScrollY();
/*  180 */     this.mBeingPressed = true;
/*      */ 
/*      */     
/*  183 */     this.mEffSelWidgetId = hitTest(x, y);
/*      */     
/*  185 */     if (getToolMode() == ToolManager.ToolMode.TEXT_SELECT && ShortcutHelper.isTextSelect(e)) {
/*  186 */       this.mNextToolMode = ToolManager.ToolMode.TEXT_SELECT;
/*  187 */       this.mStationPt.set(this.mPressedPoint);
/*      */     } 
/*      */     
/*  190 */     if (this.mEffSelWidgetId >= 0) {
/*      */       
/*  192 */       x = ((this.mSelWidgets[1 - this.mEffSelWidgetId]).mStrPt.x + (this.mSelWidgets[1 - this.mEffSelWidgetId]).mEndPt.x) / 2.0F;
/*  193 */       y = ((this.mSelWidgets[1 - this.mEffSelWidgetId]).mStrPt.y + (this.mSelWidgets[1 - this.mEffSelWidgetId]).mEndPt.y) / 2.0F;
/*  194 */       this.mStationPt.set(x, y);
/*      */ 
/*      */       
/*  197 */       setLoupeInfo(e.getX(), e.getY());
/*  198 */       this.mPdfViewCtrl.invalidate();
/*  199 */       animateLoupe(true);
/*      */     } 
/*  201 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onScaleEnd(float x, float y) {
/*  209 */     super.onScaleEnd(x, y);
/*  210 */     this.mScaled = true;
/*  211 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onKeyUp(int keyCode, KeyEvent event) {
/*  219 */     if (this.mPdfViewCtrl == null) {
/*  220 */       return false;
/*      */     }
/*      */     
/*  223 */     if (isQuickMenuShown() && hasMenuEntry(R.id.qm_copy) && ShortcutHelper.isCopy(keyCode, event)) {
/*  224 */       closeQuickMenu();
/*  225 */       copyAnnot(ViewerUtils.getSelectedString(this.mPdfViewCtrl), (String)null);
/*  226 */       return true;
/*      */     } 
/*      */     
/*  229 */     if (this.mPdfViewCtrl.hasSelection() && isQuickMenuShown()) {
/*  230 */       if (hasMenuEntry(R.id.qm_highlight) && ShortcutHelper.isHighlightAnnot(keyCode, event)) {
/*  231 */         onQuickMenuClicked(new QuickMenuItem(this.mPdfViewCtrl.getContext(), R.id.qm_highlight));
/*  232 */         return true;
/*      */       } 
/*      */       
/*  235 */       if (hasMenuEntry(R.id.qm_underline) && ShortcutHelper.isUnderlineAnnot(keyCode, event)) {
/*  236 */         onQuickMenuClicked(new QuickMenuItem(this.mPdfViewCtrl.getContext(), R.id.qm_underline));
/*  237 */         return true;
/*      */       } 
/*      */       
/*  240 */       if (hasMenuEntry(R.id.qm_strikeout) && ShortcutHelper.isStrikethroughAnnot(keyCode, event)) {
/*  241 */         onQuickMenuClicked(new QuickMenuItem(this.mPdfViewCtrl.getContext(), R.id.qm_strikeout));
/*  242 */         return true;
/*      */       } 
/*      */       
/*  245 */       if (hasMenuEntry(R.id.qm_squiggly) && ShortcutHelper.isSquigglyAnnot(keyCode, event)) {
/*  246 */         onQuickMenuClicked(new QuickMenuItem(this.mPdfViewCtrl.getContext(), R.id.qm_squiggly));
/*  247 */         return true;
/*      */       } 
/*      */       
/*  250 */       if (hasMenuEntry(R.id.qm_link) && ShortcutHelper.isHyperlinkAnnot(keyCode, event)) {
/*  251 */         onQuickMenuClicked(new QuickMenuItem(this.mPdfViewCtrl.getContext(), R.id.qm_link));
/*  252 */         return true;
/*      */       } 
/*      */     } 
/*      */     
/*  256 */     if (isQuickMenuShown() && ShortcutHelper.isCloseMenu(keyCode, event)) {
/*  257 */       onClose();
/*  258 */       exitCurrentMode();
/*  259 */       return true;
/*      */     } 
/*      */     
/*  262 */     return super.onKeyUp(keyCode, event);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onUp(MotionEvent e, PDFViewCtrl.PriorEventMode priorEventMode) {
/*  270 */     if (hasSelection()) {
/*  271 */       this.mPagePresModeWhileSelected = this.mPdfViewCtrl.getPagePresentationMode();
/*  272 */       this.mSelWidgetEnabled = true;
/*      */       
/*  274 */       if (this.mScaled || priorEventMode == PDFViewCtrl.PriorEventMode.SCROLLING || priorEventMode == PDFViewCtrl.PriorEventMode.PINCH || priorEventMode == PDFViewCtrl.PriorEventMode.DOUBLE_TAP || (this.mBeingLongPressed && priorEventMode != PDFViewCtrl.PriorEventMode.FLING)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  280 */         this.mSelPath.reset();
/*  281 */         populateSelectionResult();
/*      */       } 
/*  283 */       showMenu(getQMAnchorRect());
/*      */     } else {
/*      */       
/*  286 */       exitCurrentMode();
/*      */     } 
/*      */     
/*  289 */     this.mScaled = false;
/*  290 */     this.mBeingLongPressed = false;
/*  291 */     this.mBeingPressed = false;
/*  292 */     this.mEffSelWidgetId = -1;
/*  293 */     this.mPdfViewCtrl.invalidate();
/*  294 */     animateLoupe(false);
/*      */     
/*  296 */     return skipOnUpPriorEvent(priorEventMode);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onConfigurationChanged(Configuration newConfig) {
/*  304 */     if (this.mPdfViewCtrl.hasSelection()) {
/*  305 */       this.mNextToolMode = getToolMode();
/*      */     } else {
/*  307 */       exitCurrentMode();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onClose() {
/*  316 */     closeQuickMenu();
/*  317 */     if (this.mTTS != null) {
/*  318 */       this.mTTS.stop();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean canDrawLoupe() {
/*  325 */     return (!this.mDrawingLoupe && (this.mEffSelWidgetId >= 0 || this.mBeingLongPressed));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getLoupeType() {
/*  331 */     return 1;
/*      */   }
/*      */   
/*      */   private boolean hasSelection() {
/*  335 */     boolean hasSelectionForReal = false;
/*  336 */     if (this.mPdfViewCtrl.hasSelection()) {
/*      */       
/*      */       try {
/*      */         
/*  340 */         int sel_pg_begin = this.mPdfViewCtrl.getSelectionBeginPage();
/*  341 */         int sel_pg_end = this.mPdfViewCtrl.getSelectionEndPage();
/*  342 */         for (int pg = sel_pg_begin; pg <= sel_pg_end; pg++) {
/*  343 */           PDFViewCtrl.Selection sel = this.mPdfViewCtrl.getSelection(pg);
/*  344 */           double[] quads = sel.getQuads();
/*  345 */           if (quads.length != 0) {
/*  346 */             hasSelectionForReal = true;
/*      */           }
/*      */         } 
/*  349 */       } catch (Exception ex) {
/*  350 */         ex.printStackTrace();
/*      */       } 
/*      */     }
/*  353 */     return hasSelectionForReal;
/*      */   }
/*      */ 
/*      */   
/*      */   private void exitCurrentMode() {
/*  358 */     this.mNextToolMode = this.mCurrentDefaultToolMode;
/*  359 */     this.mPdfViewCtrl.clearSelection();
/*  360 */     this.mEffSelWidgetId = -1;
/*  361 */     this.mSelWidgetEnabled = false;
/*  362 */     if (!this.mSelPath.isEmpty()) {
/*      */       
/*  364 */       this.mSelPath.reset();
/*  365 */       this.mPdfViewCtrl.invalidate();
/*      */     } 
/*  367 */     animateLoupe(false);
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
/*      */ 
/*      */ 
/*      */   
/*      */   protected void selectText(float x1, float y1, float x2, float y2, boolean byRect, boolean bySnap) {
/*  383 */     if (byRect) {
/*  384 */       RectF textSelectRect = getTextSelectRect(x2, y2);
/*  385 */       x1 = textSelectRect.left;
/*  386 */       y1 = textSelectRect.top;
/*  387 */       x2 = textSelectRect.right;
/*  388 */       y2 = textSelectRect.bottom;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  393 */     boolean had_sel = !this.mSelPath.isEmpty();
/*  394 */     this.mSelPath.reset();
/*      */ 
/*      */     
/*  397 */     boolean shouldUnlockRead = false;
/*      */     
/*      */     try {
/*  400 */       this.mPdfViewCtrl.docLockRead();
/*  401 */       shouldUnlockRead = true;
/*  402 */       if (byRect) {
/*  403 */         this.mPdfViewCtrl.selectByRect(x1, y1, x2, y2);
/*  404 */       } else if (bySnap) {
/*  405 */         this.mPdfViewCtrl.selectByStructWithSmartSnapping(x1, y1, x2, y2);
/*      */       } else {
/*  407 */         this.mPdfViewCtrl.setTextSelectionMode(PDFViewCtrl.TextSelectionMode.STRUCTURAL);
/*  408 */         this.mPdfViewCtrl.select(x1, y1, x2, y2);
/*      */       } 
/*  410 */     } catch (Exception e) {
/*  411 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } finally {
/*  413 */       if (shouldUnlockRead) {
/*  414 */         this.mPdfViewCtrl.docUnlockRead();
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  421 */     if (had_sel) {
/*  422 */       this.mTempRect.set(this.mSelBBox);
/*      */     }
/*  424 */     populateSelectionResult();
/*  425 */     if (!had_sel) {
/*  426 */       this.mTempRect.set(this.mSelBBox);
/*      */     } else {
/*  428 */       this.mTempRect.union(this.mSelBBox);
/*      */     } 
/*      */     
/*  431 */     this.mTempRect.union(this.mLoupeBBox);
/*  432 */     setLoupeInfo(x2, y2);
/*  433 */     this.mTempRect.union(this.mLoupeBBox);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onMove(MotionEvent e1, MotionEvent e2, float x_dist, float y_dist) {
/*  441 */     super.onMove(e1, e2, x_dist, y_dist);
/*      */ 
/*      */     
/*  444 */     if (ShortcutHelper.isTextSelect(e2) && 0 == Float.compare(0.0F, e2.getPressure(0))) {
/*  445 */       this.mNextToolMode = ToolManager.ToolMode.PAN;
/*  446 */       return false;
/*      */     } 
/*      */     
/*  449 */     float sx = this.mPdfViewCtrl.getScrollX();
/*  450 */     float sy = this.mPdfViewCtrl.getScrollY();
/*      */     
/*  452 */     if ((ShortcutHelper.isTextSelect(e2) && getToolMode() == ToolManager.ToolMode.TEXT_SELECT) || this.mEffSelWidgetId >= 0) {
/*      */       
/*  454 */       selectText(this.mStationPt.x - sx, this.mStationPt.y - sy, e2.getX(), e2.getY(), false, true);
/*  455 */       this.mPdfViewCtrl.invalidate(this.mInvalidateBBox);
/*  456 */       return true;
/*      */     } 
/*  458 */     if (this.mBeingLongPressed) {
/*      */       
/*  460 */       selectText(0.0F, 0.0F, e2.getX(), e2.getY(), true, false);
/*  461 */       this.mPdfViewCtrl.invalidate(this.mInvalidateBBox);
/*  462 */       return true;
/*      */     } 
/*  464 */     showTransientPageNumber();
/*  465 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onLayout(boolean changed, int l, int t, int r, int b) {
/*  475 */     super.onLayout(changed, l, t, r, b);
/*      */     
/*  477 */     if (this.mPdfViewCtrl.hasSelection()) {
/*      */ 
/*      */ 
/*      */       
/*  481 */       if (this.mPdfViewCtrl.isContinuousPagePresentationMode(this.mPagePresModeWhileSelected) && 
/*  482 */         !this.mPdfViewCtrl.isContinuousPagePresentationMode(this.mPdfViewCtrl.getPagePresentationMode())) {
/*  483 */         this.mPdfViewCtrl.clearSelection();
/*  484 */         closeQuickMenu();
/*  485 */         this.mSelPath.reset();
/*  486 */         this.mNextToolMode = ToolManager.ToolMode.PAN;
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/*  491 */       this.mSelPath.reset();
/*  492 */       populateSelectionResult();
/*  493 */       this.mPdfViewCtrl.invalidate();
/*      */       
/*  495 */       if (isQuickMenuShown()) {
/*  496 */         closeQuickMenu();
/*  497 */         showMenu(getQMAnchorRect());
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onLongPress(MotionEvent e) {
/*  507 */     this.mNextToolMode = ToolManager.ToolMode.TEXT_SELECT;
/*  508 */     this.mBeingLongPressed = true;
/*  509 */     this.mBeingPressed = true;
/*  510 */     this.mLoupeEnabled = true;
/*      */     
/*  512 */     float sx = this.mPdfViewCtrl.getScrollX();
/*  513 */     float sy = this.mPdfViewCtrl.getScrollY();
/*  514 */     this.mPressedPoint.x = e.getX() + sx;
/*  515 */     this.mPressedPoint.y = e.getY() + sy;
/*      */     
/*  517 */     if (this.mEffSelWidgetId < 0) {
/*      */ 
/*      */       
/*  520 */       this.mEffSelWidgetId = -1;
/*  521 */       this.mSelWidgetEnabled = false;
/*  522 */       selectText(0.0F, 0.0F, e.getX(), e.getY(), true, false);
/*  523 */       this.mPdfViewCtrl.invalidate(this.mInvalidateBBox);
/*  524 */       animateLoupe(true);
/*      */       
/*  526 */       if (!this.mPdfViewCtrl.hasSelection()) {
/*      */         
/*  528 */         animateLoupe(false);
/*  529 */         this.mNextToolMode = ToolManager.ToolMode.PAN;
/*      */       } 
/*      */     } 
/*      */     
/*  533 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onSingleTapConfirmed(MotionEvent e) {
/*  541 */     super.onSingleTapConfirmed(e);
/*      */     
/*  543 */     exitCurrentMode();
/*      */     
/*  545 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onPageTurning(int old_page, int cur_page) {
/*  553 */     super.onPageTurning(old_page, cur_page);
/*      */ 
/*      */     
/*  556 */     if (!this.mPdfViewCtrl.isContinuousPagePresentationMode(this.mPdfViewCtrl.getPagePresentationMode()) && 
/*  557 */       this.mPdfViewCtrl.hasSelection()) {
/*  558 */       exitCurrentMode();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onNightModeUpdated(boolean isNightMode) {
/*  568 */     if (this.mIsNightMode != isNightMode)
/*      */     {
/*  570 */       if (isNightMode) {
/*  571 */         this.mBlendmode = new PorterDuffXfermode(PorterDuff.Mode.SCREEN);
/*      */       } else {
/*  573 */         this.mBlendmode = new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY);
/*      */       } 
/*      */     }
/*  576 */     this.mIsNightMode = isNightMode;
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
/*      */   public int hitTest(float x, float y) {
/*  589 */     float dist = -1.0F;
/*  590 */     int id = -1;
/*  591 */     for (int i = 0; i < 2; i++) {
/*  592 */       PointF mPt = (i == 0) ? (this.mSelWidgets[i]).mStrPt : (this.mSelWidgets[i]).mEndPt;
/*  593 */       float s = x - mPt.x;
/*  594 */       float t = y - mPt.y;
/*  595 */       float d = (float)Math.sqrt((s * s + t * t));
/*  596 */       if (d < this.mTSWidgetRadius * 4.0F && (
/*  597 */         dist < 0.0F || dist > d)) {
/*  598 */         dist = d;
/*  599 */         id = i;
/*      */       } 
/*      */     } 
/*      */     
/*  603 */     return id;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void populateSelectionResult() {
/*  610 */     float sx = this.mPdfViewCtrl.getScrollX();
/*  611 */     float sy = this.mPdfViewCtrl.getScrollY();
/*  612 */     int sel_pg_begin = this.mPdfViewCtrl.getSelectionBeginPage();
/*  613 */     int sel_pg_end = this.mPdfViewCtrl.getSelectionEndPage();
/*  614 */     float min_x = 1.0E10F, min_y = 1.0E10F, max_x = 0.0F, max_y = 0.0F;
/*  615 */     boolean has_sel = false;
/*      */     
/*      */     try {
/*  618 */       Rect firstQuad = getFirstQuad();
/*  619 */       Rect lastQuad = getLastQuad();
/*  620 */       firstQuad = convertFromPageRectToScreenRect(firstQuad, sel_pg_begin);
/*  621 */       lastQuad = convertFromPageRectToScreenRect(lastQuad, sel_pg_end);
/*      */       
/*  623 */       if (firstQuad == null || lastQuad == null) {
/*      */         return;
/*      */       }
/*      */       
/*  627 */       firstQuad.normalize();
/*  628 */       lastQuad.normalize();
/*      */       
/*  630 */       (this.mSelWidgets[0]).mStrPt.set((float)firstQuad.getX1() - this.mTSWidgetThickness / 2.0F, (float)firstQuad.getY2());
/*  631 */       (this.mSelWidgets[1]).mEndPt.set((float)lastQuad.getX2() + this.mTSWidgetThickness / 2.0F, (float)lastQuad.getY2());
/*      */       
/*  633 */       if (this.mIsRightToLeft) {
/*  634 */         (this.mSelWidgets[0]).mStrPt.x = (float)firstQuad.getX2() - this.mTSWidgetThickness / 2.0F;
/*  635 */         (this.mSelWidgets[1]).mEndPt.x = (float)lastQuad.getX1() + this.mTSWidgetThickness / 2.0F;
/*      */       } 
/*      */       
/*  638 */       (this.mSelWidgets[0]).mEndPt.set((this.mSelWidgets[0]).mStrPt.x, (float)((this.mSelWidgets[0]).mStrPt.y - firstQuad.getHeight()));
/*  639 */       (this.mSelWidgets[1]).mStrPt.set((this.mSelWidgets[1]).mEndPt.x, (float)((this.mSelWidgets[1]).mEndPt.y - lastQuad.getHeight()));
/*  640 */     } catch (PDFNetException e) {
/*  641 */       AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  648 */     for (int pg = sel_pg_begin; pg <= sel_pg_end; pg++) {
/*  649 */       PDFViewCtrl.Selection sel = this.mPdfViewCtrl.getSelection(pg);
/*  650 */       double[] quads = sel.getQuads();
/*      */       
/*  652 */       int sz = quads.length / 8;
/*      */       
/*  654 */       if (sz != 0) {
/*      */ 
/*      */         
/*  657 */         int k = 0;
/*      */         
/*  659 */         for (int i = 0; i < sz; i++, k += 8) {
/*  660 */           has_sel = true;
/*      */           
/*  662 */           double[] pts = this.mPdfViewCtrl.convPagePtToScreenPt(quads[k], quads[k + 1], pg);
/*  663 */           float x = (float)pts[0] + sx;
/*  664 */           float y = (float)pts[1] + sy;
/*  665 */           this.mTempRect.left = x;
/*  666 */           this.mTempRect.bottom = y;
/*  667 */           (this.mQuadPoints[0]).x = x;
/*  668 */           (this.mQuadPoints[0]).y = y;
/*  669 */           min_x = (min_x > x) ? x : min_x;
/*  670 */           max_x = (max_x < x) ? x : max_x;
/*  671 */           min_y = (min_y > y) ? y : min_y;
/*  672 */           max_y = (max_y < y) ? y : max_y;
/*      */           
/*  674 */           if (pg == sel_pg_begin && i == 0) {
/*      */ 
/*      */ 
/*      */             
/*  678 */             x -= this.mTSWidgetThickness + this.mTSWidgetRadius;
/*  679 */             min_x = (min_x > x) ? x : min_x;
/*  680 */             max_x = (max_x < x) ? x : max_x;
/*      */           } 
/*      */           
/*  683 */           pts = this.mPdfViewCtrl.convPagePtToScreenPt(quads[k + 2], quads[k + 3], pg);
/*  684 */           x = (float)pts[0] + sx;
/*  685 */           y = (float)pts[1] + sy;
/*  686 */           this.mTempRect.right = x;
/*  687 */           (this.mQuadPoints[1]).x = x;
/*  688 */           (this.mQuadPoints[1]).y = y;
/*  689 */           min_x = (min_x > x) ? x : min_x;
/*  690 */           max_x = (max_x < x) ? x : max_x;
/*  691 */           min_y = (min_y > y) ? y : min_y;
/*  692 */           max_y = (max_y < y) ? y : max_y;
/*      */           
/*  694 */           if (pg == sel_pg_end && i == sz - 1) {
/*      */ 
/*      */ 
/*      */             
/*  698 */             x += this.mTSWidgetThickness + this.mTSWidgetRadius;
/*  699 */             y += this.mTSWidgetRadius * 2.0F;
/*  700 */             min_x = (min_x > x) ? x : min_x;
/*  701 */             max_x = (max_x < x) ? x : max_x;
/*  702 */             min_y = (min_y > y) ? y : min_y;
/*  703 */             max_y = (max_y < y) ? y : max_y;
/*      */           } 
/*      */           
/*  706 */           pts = this.mPdfViewCtrl.convPagePtToScreenPt(quads[k + 4], quads[k + 5], pg);
/*  707 */           x = (float)pts[0] + sx;
/*  708 */           y = (float)pts[1] + sy;
/*  709 */           this.mTempRect.top = y;
/*  710 */           (this.mQuadPoints[2]).x = x;
/*  711 */           (this.mQuadPoints[2]).y = y;
/*  712 */           min_x = (min_x > x) ? x : min_x;
/*  713 */           max_x = (max_x < x) ? x : max_x;
/*  714 */           min_y = (min_y > y) ? y : min_y;
/*  715 */           max_y = (max_y < y) ? y : max_y;
/*      */           
/*  717 */           if (pg == sel_pg_end && i == sz - 1) {
/*      */ 
/*      */ 
/*      */             
/*  721 */             x += this.mTSWidgetThickness + this.mTSWidgetRadius;
/*  722 */             min_x = (min_x > x) ? x : min_x;
/*  723 */             max_x = (max_x < x) ? x : max_x;
/*      */           } 
/*      */           
/*  726 */           pts = this.mPdfViewCtrl.convPagePtToScreenPt(quads[k + 6], quads[k + 7], pg);
/*  727 */           x = (float)pts[0] + sx;
/*  728 */           y = (float)pts[1] + sy;
/*  729 */           (this.mQuadPoints[3]).x = x;
/*  730 */           (this.mQuadPoints[3]).y = y;
/*  731 */           min_x = (min_x > x) ? x : min_x;
/*  732 */           max_x = (max_x < x) ? x : max_x;
/*  733 */           min_y = (min_y > y) ? y : min_y;
/*  734 */           max_y = (max_y < y) ? y : max_y;
/*      */           
/*  736 */           if (pg == sel_pg_begin && i == 0) {
/*      */ 
/*      */ 
/*      */             
/*  740 */             x -= this.mTSWidgetThickness + this.mTSWidgetRadius;
/*  741 */             y -= this.mTSWidgetRadius * 2.0F;
/*  742 */             min_x = (min_x > x) ? x : min_x;
/*  743 */             max_x = (max_x < x) ? x : max_x;
/*  744 */             min_y = (min_y > y) ? y : min_y;
/*  745 */             max_y = (max_y < y) ? y : max_y;
/*      */           } 
/*      */           
/*      */           try {
/*  749 */             Page page1 = this.mPdfViewCtrl.getDoc().getPage(pg);
/*  750 */             if (page1 != null && (page1.getRotation() == 1 || page1
/*  751 */               .getRotation() == 3 || this.mPdfViewCtrl
/*  752 */               .getPageRotation() == 1 || this.mPdfViewCtrl
/*  753 */               .getPageRotation() == 3)) {
/*      */               
/*  755 */               this.mTempRotationRect.setX1((this.mQuadPoints[0]).x);
/*  756 */               this.mTempRotationRect.setY1((this.mQuadPoints[0]).y);
/*  757 */               this.mTempRotationRect.setX2((this.mQuadPoints[2]).x);
/*  758 */               this.mTempRotationRect.setY2((this.mQuadPoints[2]).y);
/*      */             } else {
/*  760 */               this.mTempRotationRect.setX1((this.mQuadPoints[0]).x);
/*  761 */               this.mTempRotationRect.setY1((this.mQuadPoints[0]).y);
/*  762 */               this.mTempRotationRect.setX2((this.mQuadPoints[2]).x);
/*  763 */               this.mTempRotationRect.setY2((this.mQuadPoints[3]).y);
/*      */             } 
/*  765 */             this.mTempRotationRect.normalize();
/*      */             
/*  767 */             this.mTempRect.left = (float)this.mTempRotationRect.getX1();
/*  768 */             this.mTempRect.top = (float)this.mTempRotationRect.getY1();
/*  769 */             this.mTempRect.right = (float)this.mTempRotationRect.getX2();
/*  770 */             this.mTempRect.bottom = (float)this.mTempRotationRect.getY2();
/*  771 */           } catch (PDFNetException e) {
/*  772 */             this.mTempRect.left = (this.mQuadPoints[0]).x;
/*  773 */             this.mTempRect.top = (this.mQuadPoints[2]).y;
/*  774 */             this.mTempRect.right = (this.mQuadPoints[1]).x;
/*  775 */             this.mTempRect.bottom = (this.mQuadPoints[0]).y;
/*      */           } 
/*  777 */           this.mSelPath.addRect(this.mTempRect, Path.Direction.CW);
/*      */         } 
/*      */       } 
/*      */     } 
/*  781 */     if (has_sel) {
/*  782 */       this.mSelBBox.set(min_x, min_y, max_x, max_y);
/*  783 */       this.mSelBBox.round(this.mInvalidateBBox);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Rect getFirstQuad() {
/*  791 */     int sel_pg_begin = this.mPdfViewCtrl.getSelectionBeginPage();
/*  792 */     int sel_pg_end = this.mPdfViewCtrl.getSelectionEndPage();
/*  793 */     for (int pg = sel_pg_begin; pg <= sel_pg_end; pg++) {
/*  794 */       PDFViewCtrl.Selection sel = this.mPdfViewCtrl.getSelection(pg);
/*  795 */       double[] quads = sel.getQuads();
/*  796 */       int sz = quads.length / 8;
/*      */       
/*  798 */       if (sz > 0) {
/*      */         try {
/*  800 */           return new Rect(quads[0], quads[1], quads[4], quads[5]);
/*  801 */         } catch (PDFNetException e) {
/*  802 */           return null;
/*      */         } 
/*      */       }
/*      */     } 
/*  806 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Rect getLastQuad() {
/*  813 */     int sel_pg_begin = this.mPdfViewCtrl.getSelectionBeginPage();
/*  814 */     int sel_pg_end = this.mPdfViewCtrl.getSelectionEndPage();
/*  815 */     for (int pg = sel_pg_end; pg >= sel_pg_begin; pg--) {
/*  816 */       PDFViewCtrl.Selection sel = this.mPdfViewCtrl.getSelection(pg);
/*  817 */       double[] quads = sel.getQuads();
/*  818 */       int sz = quads.length / 8;
/*  819 */       if (sz > 0) {
/*      */         try {
/*  821 */           return new Rect(quads[quads.length - 8], quads[quads.length - 7], quads[quads.length - 4], quads[quads.length - 3]);
/*  822 */         } catch (PDFNetException e) {
/*  823 */           return null;
/*      */         } 
/*      */       }
/*      */     } 
/*  827 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onQuickMenuClicked(QuickMenuItem menuItem) {
/*  836 */     if (super.onQuickMenuClicked(menuItem)) {
/*  837 */       return true;
/*      */     }
/*      */     
/*  840 */     if (this.mPdfViewCtrl.hasSelection()) {
/*  841 */       String text = ViewerUtils.getSelectedString(this.mPdfViewCtrl);
/*  842 */       if (menuItem.getItemId() == R.id.qm_define || menuItem.getItemId() == R.id.qm_translate) {
/*      */         RectF anchor;
/*      */         
/*  845 */         if (this instanceof AnnotEditTextMarkup) {
/*  846 */           anchor = getAnnotRect();
/*      */         } else {
/*  848 */           int sx = this.mPdfViewCtrl.getScrollX();
/*  849 */           int sy = this.mPdfViewCtrl.getScrollY();
/*  850 */           RectF selectBox = new RectF(this.mSelBBox.left - sx, this.mSelBBox.top - sy, this.mSelBBox.right - sx, this.mSelBBox.bottom - sy);
/*  851 */           anchor = calculateQMAnchor(selectBox);
/*      */         } 
/*      */ 
/*      */         
/*  855 */         boolean isDefine = (menuItem.getItemId() == R.id.qm_define);
/*      */         
/*  857 */         ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/*  858 */         toolManager.defineTranslateSelected(text, anchor, Boolean.valueOf(isDefine));
/*      */         
/*  860 */         exitCurrentMode();
/*  861 */         return true;
/*  862 */       }  if (menuItem.getItemId() == R.id.qm_share) {
/*  863 */         String subject = this.mPdfViewCtrl.getContext().getResources().getString(R.string.empty_title);
/*      */         try {
/*  865 */           File file = new File(this.mPdfViewCtrl.getDoc().getFileName());
/*  866 */           subject = this.mPdfViewCtrl.getContext().getResources().getString(R.string.tools_share_subject) + " " + file.getName();
/*  867 */         } catch (Exception e) {
/*  868 */           AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */         } 
/*      */         
/*  871 */         Intent sharingIntent = new Intent("android.intent.action.SEND");
/*  872 */         sharingIntent.setType("text/plain");
/*  873 */         sharingIntent.putExtra("android.intent.extra.SUBJECT", subject);
/*  874 */         sharingIntent.putExtra("android.intent.extra.TEXT", text);
/*  875 */         this.mPdfViewCtrl.getContext().startActivity(Intent.createChooser(sharingIntent, this.mPdfViewCtrl.getContext().getResources().getString(R.string.tools_share_title)));
/*      */         
/*  877 */         exitCurrentMode();
/*      */         
/*  879 */         return true;
/*  880 */       }  if (menuItem.getItemId() == R.id.qm_copy) {
/*  881 */         copyAnnot(text, this.mPdfViewCtrl.getContext().getResources().getString(R.string.tools_copy_confirmation));
/*  882 */         exitCurrentMode();
/*  883 */         return true;
/*  884 */       }  if (menuItem.getItemId() == R.id.qm_search) {
/*  885 */         Intent intent = new Intent("android.intent.action.WEB_SEARCH");
/*  886 */         intent.putExtra("query", text);
/*      */         
/*      */         try {
/*  889 */           this.mPdfViewCtrl.getContext().startActivity(intent);
/*  890 */         } catch (ActivityNotFoundException activityNotFoundException) {}
/*      */ 
/*      */         
/*  893 */         exitCurrentMode();
/*  894 */         return true;
/*  895 */       }  if (menuItem.getItemId() == R.id.qm_highlight) {
/*  896 */         this.mNextToolMode = ToolManager.ToolMode.TEXT_HIGHLIGHT;
/*  897 */       } else if (menuItem.getItemId() == R.id.qm_underline) {
/*  898 */         this.mNextToolMode = ToolManager.ToolMode.TEXT_UNDERLINE;
/*  899 */       } else if (menuItem.getItemId() == R.id.qm_squiggly) {
/*  900 */         this.mNextToolMode = ToolManager.ToolMode.TEXT_SQUIGGLY;
/*  901 */       } else if (menuItem.getItemId() == R.id.qm_strikeout) {
/*  902 */         this.mNextToolMode = ToolManager.ToolMode.TEXT_STRIKEOUT;
/*  903 */       } else if (menuItem.getItemId() == R.id.qm_link) {
/*  904 */         this.mNextToolMode = ToolManager.ToolMode.TEXT_LINK_CREATE;
/*  905 */       } else if (menuItem.getItemId() == R.id.qm_redaction) {
/*  906 */         this.mNextToolMode = ToolManager.ToolMode.TEXT_REDACTION;
/*  907 */       } else if (menuItem.getItemId() == R.id.qm_tts) {
/*      */         
/*  909 */         AudioManager audio = (AudioManager)this.mPdfViewCtrl.getContext().getSystemService("audio");
/*  910 */         int currentVolume = audio.getStreamVolume(3);
/*  911 */         if (currentVolume == 0) {
/*  912 */           CommonToast.showText(this.mPdfViewCtrl.getContext(), this.mPdfViewCtrl.getContext().getString(R.string.text_to_speech_mute_volume), 0);
/*      */         }
/*      */         try {
/*  915 */           this.mTTS = ((ToolManager)this.mPdfViewCtrl.getToolManager()).getTTS();
/*  916 */           if (this.mTTS != null) {
/*  917 */             this.mTTS.speak(text, 0, null);
/*      */           } else {
/*  919 */             final String finalText = text;
/*  920 */             this.mTTS = new TextToSpeech(this.mPdfViewCtrl.getContext(), new TextToSpeech.OnInitListener()
/*      */                 {
/*      */                   public void onInit(int status) {
/*      */                     try {
/*  924 */                       if (status == 0) {
/*  925 */                         TextSelect.this.mTTS.speak(finalText, 0, null);
/*      */                       } else {
/*  927 */                         CommonToast.showText(TextSelect.this.mPdfViewCtrl.getContext(), TextSelect.this.mPdfViewCtrl.getContext().getString(R.string.error_text_to_speech), 0);
/*      */                       } 
/*  929 */                     } catch (Exception e) {
/*  930 */                       Utils.showAlertDialogWithLink(TextSelect.this.mPdfViewCtrl.getContext(), TextSelect.this.mPdfViewCtrl.getContext().getResources().getString(R.string.error_thrown_text_to_speech), "");
/*      */                     } 
/*      */                   }
/*      */                 });
/*      */           } 
/*  935 */         } catch (Exception e) {
/*  936 */           CommonToast.showText(this.mPdfViewCtrl.getContext(), this.mPdfViewCtrl.getContext().getString(R.string.error_text_to_speech), 0);
/*      */         } 
/*  938 */         return true;
/*      */       } 
/*      */     } 
/*  941 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean showMenu(RectF anchor_rect) {
/*  946 */     if (onInterceptAnnotationHandling(this.mAnnot)) {
/*  947 */       return true;
/*      */     }
/*      */     
/*  950 */     if (this.mPdfViewCtrl == null) {
/*  951 */       return false;
/*      */     }
/*      */     
/*  954 */     ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/*  955 */     return (toolManager != null && !toolManager.isQuickMenuDisabled() && super.showMenu(anchor_rect));
/*      */   }
/*      */   
/*      */   private void copyAnnot(String text, String toastMsg) {
/*  959 */     ClipboardManager clipboard = (ClipboardManager)this.mPdfViewCtrl.getContext().getSystemService("clipboard");
/*  960 */     if (clipboard != null) {
/*  961 */       ClipData clip = ClipData.newPlainText("text", text);
/*  962 */       clipboard.setPrimaryClip(clip);
/*      */     } 
/*  964 */     if (!Utils.isNullOrEmpty(toastMsg)) {
/*  965 */       CommonToast.showText(this.mPdfViewCtrl.getContext(), toastMsg, 0);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDraw(Canvas canvas, Matrix tfm) {
/*  974 */     if (!this.mDrawingLoupe) {
/*  975 */       super.onDraw(canvas, tfm);
/*      */     }
/*      */     
/*  978 */     if (this.mPdfViewCtrl.isSlidingWhileZoomed()) {
/*      */       return;
/*      */     }
/*      */     
/*  982 */     drawLoupe();
/*      */ 
/*      */     
/*  985 */     if (!this.mSelPath.isEmpty()) {
/*  986 */       this.mPaint.setStyle(Paint.Style.FILL);
/*  987 */       this.mPaint.setXfermode((Xfermode)this.mBlendmode);
/*  988 */       this.mPaint.setColor(this.mSelColor);
/*  989 */       canvas.drawPath(this.mSelPath, this.mPaint);
/*  990 */       this.mPaint.setXfermode(null);
/*      */ 
/*      */       
/*  993 */       if (this.mSelWidgetEnabled) {
/*  994 */         this.mPaint.setColor(this.mPdfViewCtrl.getResources().getColor(R.color.fab_light_blue));
/*      */ 
/*      */ 
/*      */         
/*  998 */         float x1 = (this.mSelWidgets[0]).mStrPt.x;
/*  999 */         float y1 = (this.mSelWidgets[0]).mStrPt.y;
/* 1000 */         if (this.mHasSelectionPermission) {
/*      */           
/* 1002 */           this.mPaint.setStyle(Paint.Style.FILL);
/*      */ 
/*      */           
/* 1005 */           BitmapDrawable drawable = (BitmapDrawable)this.mPdfViewCtrl.getContext().getResources().getDrawable(R.drawable.text_select_handle_left);
/* 1006 */           Bitmap handle = drawable.getBitmap();
/*      */           
/* 1008 */           Rect locRect = new Rect((int)(x1 - 2.0F * this.mTSWidgetRadius), (int)y1, (int)x1, (int)(y1 + 2.0F * this.mTSWidgetRadius));
/*      */           
/* 1010 */           canvas.drawBitmap(handle, null, locRect, this.mPaint);
/*      */         } 
/*      */         
/* 1013 */         float x2 = (this.mSelWidgets[1]).mEndPt.x;
/* 1014 */         float y2 = (this.mSelWidgets[1]).mEndPt.y;
/* 1015 */         if (this.mHasSelectionPermission) {
/*      */           
/* 1017 */           this.mPaint.setStyle(Paint.Style.FILL);
/*      */ 
/*      */           
/* 1020 */           BitmapDrawable drawable = (BitmapDrawable)this.mPdfViewCtrl.getContext().getResources().getDrawable(R.drawable.text_select_handle_right);
/* 1021 */           Bitmap handle = drawable.getBitmap();
/*      */           
/* 1023 */           Rect locRect = new Rect((int)x2, (int)y2, (int)(x2 + 2.0F * this.mTSWidgetRadius), (int)(y2 + 2.0F * this.mTSWidgetRadius));
/*      */           
/* 1025 */           canvas.drawBitmap(handle, null, locRect, this.mPaint);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   class SelWidget
/*      */   {
/*      */     PointF mStrPt;
/*      */ 
/*      */     
/*      */     PointF mEndPt;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getModeAHLabel() {
/* 1045 */     return 103;
/*      */   }
/*      */   
/*      */   private RectF getQMAnchorRect() {
/* 1049 */     RectF annotRect = this.mSelBBox;
/* 1050 */     if (annotRect == null) {
/* 1051 */       return null;
/*      */     }
/* 1053 */     int sx = this.mPdfViewCtrl.getScrollX();
/* 1054 */     int sy = this.mPdfViewCtrl.getScrollY();
/* 1055 */     return new RectF(annotRect.left - sx, annotRect.top + 2.0F * this.mTSWidgetRadius - sy, annotRect.right - sx, annotRect.bottom - sy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void resetSelection() {
/* 1062 */     if (hasSelection()) {
/* 1063 */       closeQuickMenu();
/* 1064 */       this.mSelPath.reset();
/* 1065 */       populateSelectionResult();
/* 1066 */       showMenu(getQMAnchorRect());
/*      */     } 
/* 1068 */     this.mPdfViewCtrl.invalidate();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearSelection() {
/* 1075 */     this.mSelPath.reset();
/* 1076 */     this.mPdfViewCtrl.invalidate();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getQuickMenuAnalyticType() {
/* 1084 */     return 2;
/*      */   }
/*      */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\TextSelect.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */