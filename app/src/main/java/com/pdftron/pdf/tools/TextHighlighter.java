/*      */ package com.pdftron.pdf.tools;
/*      */ 
/*      */ import android.graphics.Canvas;
/*      */ import android.graphics.Matrix;
/*      */ import android.graphics.Paint;
/*      */ import android.graphics.Path;
/*      */ import android.graphics.PointF;
/*      */ import android.graphics.PorterDuff;
/*      */ import android.graphics.PorterDuffXfermode;
/*      */ import android.graphics.RectF;
/*      */ import android.graphics.Xfermode;
/*      */ import android.os.AsyncTask;
/*      */ import android.util.Log;
/*      */ import android.util.SparseArray;
/*      */ import android.view.MotionEvent;
/*      */ import android.view.ViewGroup;
/*      */ import androidx.annotation.Keep;
/*      */ import androidx.annotation.NonNull;
/*      */ import androidx.annotation.Nullable;
/*      */ import com.pdftron.common.PDFNetException;
/*      */ import com.pdftron.pdf.Highlights;
/*      */ import com.pdftron.pdf.PDFDoc;
/*      */ import com.pdftron.pdf.PDFViewCtrl;
/*      */ import com.pdftron.pdf.Page;
/*      */ import com.pdftron.pdf.Rect;
/*      */ import com.pdftron.pdf.asynctask.GenerateHighlightsTask;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import javax.microedition.khronos.egl.EGL10;
/*      */ import javax.microedition.khronos.egl.EGLConfig;
/*      */ import javax.microedition.khronos.egl.EGLContext;
/*      */ import javax.microedition.khronos.egl.EGLDisplay;
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
/*      */ public class TextHighlighter
/*      */   extends Tool
/*      */   implements GenerateHighlightsTask.Callback
/*      */ {
/*   48 */   private static final String TAG = TextHighlighter.class.getName();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int LOAD_PAGE_RANGE = 5;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int STATE_IS_NORMAL = 0;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int STATE_IS_ZOOMING = 1;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int STATE_IS_FLUNG = 2;
/*      */ 
/*      */ 
/*      */   
/*   70 */   private Paint mHighlightPaintInDayMode = new Paint();
/*   71 */   private Paint mHighlightPaintInNightMode = new Paint();
/*      */   
/*      */   private Paint mHighlightPen;
/*      */   private int mSelectionColor;
/*      */   private PorterDuff.Mode mSelectionBlendMode;
/*      */   private String mSearchPattern;
/*   77 */   private TextHighlighterSettings mSettings = new TextHighlighterSettings();
/*      */   
/*      */   private GenerateHighlightsTask mMultiPageTask;
/*   80 */   private SparseArray<GenerateHighlightsTask> mSinglePageTasks = new SparseArray();
/*   81 */   private SparseArray<PageHighlights> mPageHighlights = new SparseArray();
/*   82 */   private SparseArray<ArrayList<QuadPath>> mSelQuadPaths = new SparseArray();
/*      */   
/*      */   private boolean mIsRunning = false;
/*      */   private boolean mPathsClipped = false;
/*      */   private Rect mVisRect;
/*      */   private Rect mQuadRect;
/*      */   private RectF mTempRectF;
/*   89 */   private int mState = 0;
/*      */ 
/*      */   
/*      */   private int mMaxTextureSize;
/*      */ 
/*      */ 
/*      */   
/*      */   public TextHighlighter(@NonNull PDFViewCtrl pdfViewCtrl) {
/*   97 */     super(pdfViewCtrl);
/*      */     
/*   99 */     this.mNextToolMode = getToolMode();
/*      */     
/*  101 */     init();
/*      */   }
/*      */ 
/*      */   
/*      */   private void init() {
/*  106 */     this.mHighlightPaintInDayMode.setAntiAlias(true);
/*  107 */     this.mHighlightPaintInNightMode.setAntiAlias(true);
/*  108 */     this.mHighlightPaintInDayMode.setStyle(Paint.Style.FILL);
/*  109 */     this.mHighlightPaintInNightMode.setStyle(Paint.Style.FILL);
/*  110 */     setHighlightColors(this.mPdfViewCtrl.getContext().getResources().getColor(R.color.tools_text_highlighter_highlight_color), this.mPdfViewCtrl
/*  111 */         .getContext().getResources().getColor(R.color.tools_text_highlighter_highlight_color_inverse), this.mPdfViewCtrl
/*  112 */         .getContext().getResources().getColor(R.color.tools_text_highlighter_selection_color), this.mPdfViewCtrl
/*  113 */         .getContext().getResources().getColor(R.color.tools_text_highlighter_selection_color_inverse));
/*      */     
/*      */     try {
/*  116 */       this.mVisRect = new Rect();
/*  117 */       this.mQuadRect = new Rect();
/*  118 */     } catch (PDFNetException e) {
/*  119 */       Log.e(TAG, e.getMessage());
/*      */     } 
/*  121 */     this.mTempRectF = new RectF();
/*      */ 
/*      */     
/*  124 */     this.mPdfViewCtrl.setBuiltInPageSlidingState(true);
/*      */ 
/*      */     
/*  127 */     this.mMaxTextureSize = getMaxTextureSize();
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
/*      */   public void setHighlightColors(int highlightColorInDayMode, int highlightColorInNightMode, int selColorInDayMode, int selColorInNightMode) {
/*  140 */     this.mHighlightPaintInNightMode.setColor(highlightColorInNightMode);
/*  141 */     this.mHighlightPaintInNightMode.setXfermode((Xfermode)new PorterDuffXfermode(PorterDuff.Mode.SCREEN));
/*  142 */     this.mHighlightPaintInDayMode.setColor(highlightColorInDayMode);
/*  143 */     this.mHighlightPaintInDayMode.setXfermode((Xfermode)new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
/*      */     
/*  145 */     boolean isNightMode = ((ToolManager)this.mPdfViewCtrl.getToolManager()).isNightMode();
/*  146 */     this.mHighlightPen = isNightMode ? this.mHighlightPaintInNightMode : this.mHighlightPaintInDayMode;
/*      */     
/*  148 */     this.mSelectionColor = isNightMode ? selColorInNightMode : selColorInDayMode;
/*  149 */     this.mSelectionBlendMode = isNightMode ? PorterDuff.Mode.SCREEN : PorterDuff.Mode.MULTIPLY;
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
/*      */   public void start(String searchPattern) {
/*  163 */     start(searchPattern, false, false, false);
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
/*      */   public void start(String searchPattern, boolean matchCase, boolean matchWholeWords, boolean useRegularExpressions) {
/*  178 */     clear();
/*      */     
/*  180 */     this.mSearchPattern = searchPattern;
/*  181 */     this.mSettings.mMatchCase = matchCase;
/*  182 */     this.mSettings.mMatchWholeWords = matchWholeWords;
/*  183 */     this.mSettings.mUseRegularExpressions = useRegularExpressions;
/*      */     
/*  185 */     if (this.mPdfViewCtrl.getDoc() != null) {
/*  186 */       this.mIsRunning = true;
/*      */       
/*  188 */       highlightPage(this.mPdfViewCtrl.getCurrentPage());
/*  189 */       highlightPageRange();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clear() {
/*  197 */     stop();
/*  198 */     getSelections().clear((ViewGroup)this.mPdfViewCtrl);
/*  199 */     this.mPageHighlights.clear();
/*  200 */     resetSearchPaths(true);
/*  201 */     resetHighlightPaths(true);
/*  202 */     this.mSearchPattern = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void resetSearchPaths(boolean all) {
/*  213 */     if (all) {
/*  214 */       for (int i = 0; i < this.mSelQuadPaths.size(); i++) {
/*  215 */         ArrayList<QuadPath> quadPaths = (ArrayList<QuadPath>)this.mSelQuadPaths.valueAt(i);
/*  216 */         for (QuadPath quadPath : quadPaths) {
/*  217 */           quadPath.mPath.reset();
/*      */         }
/*      */       } 
/*      */     } else {
/*  221 */       for (int pageNum : this.mPdfViewCtrl.getVisiblePagesInTransition()) {
/*  222 */         ArrayList<QuadPath> quadPaths = (ArrayList<QuadPath>)this.mSelQuadPaths.get(pageNum);
/*  223 */         if (quadPaths != null) {
/*  224 */           for (QuadPath quadPath : quadPaths) {
/*  225 */             quadPath.mPath.reset();
/*      */           }
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void resetHighlightPaths(boolean all) {
/*  238 */     if (all) {
/*  239 */       for (int i = 0; i < this.mPageHighlights.size(); i++) {
/*  240 */         PageHighlights pgHlts = (PageHighlights)this.mPageHighlights.valueAt(i);
/*  241 */         for (QuadPath quadPath : pgHlts.mQuadPaths) {
/*  242 */           quadPath.mPath.reset();
/*      */         }
/*      */       } 
/*      */     } else {
/*  246 */       for (int pageNum : this.mPdfViewCtrl.getVisiblePagesInTransition()) {
/*  247 */         PageHighlights pgHlts = (PageHighlights)this.mPageHighlights.get(pageNum);
/*  248 */         if (pgHlts != null) {
/*  249 */           for (QuadPath quadPath : pgHlts.mQuadPaths) {
/*  250 */             quadPath.mPath.reset();
/*      */           }
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void resetClipPaths(boolean all) {
/*  265 */     if (all) {
/*      */       int i;
/*  267 */       for (i = 0; i < this.mPageHighlights.size(); i++) {
/*  268 */         PageHighlights pgHlts = (PageHighlights)this.mPageHighlights.valueAt(i);
/*  269 */         for (QuadPath quadPath : pgHlts.mQuadPaths) {
/*  270 */           quadPath.mClip = false;
/*      */         }
/*      */       } 
/*      */       
/*  274 */       for (i = 0; i < this.mSelQuadPaths.size(); i++) {
/*  275 */         ArrayList<QuadPath> quadPaths = (ArrayList<QuadPath>)this.mSelQuadPaths.valueAt(i);
/*  276 */         for (QuadPath quadPath : quadPaths) {
/*  277 */           quadPath.mClip = false;
/*      */         }
/*      */       } 
/*      */     } else {
/*      */       
/*  282 */       for (int pageNum : this.mPdfViewCtrl.getVisiblePagesInTransition()) {
/*  283 */         PageHighlights pgHlts = (PageHighlights)this.mPageHighlights.get(pageNum);
/*  284 */         if (pgHlts != null) {
/*  285 */           for (QuadPath quadPath : pgHlts.mQuadPaths) {
/*  286 */             quadPath.mClip = false;
/*      */           }
/*      */         }
/*      */       } 
/*      */       
/*  291 */       for (int pageNum : this.mPdfViewCtrl.getVisiblePagesInTransition()) {
/*  292 */         ArrayList<QuadPath> quadPaths = (ArrayList<QuadPath>)this.mSelQuadPaths.get(pageNum);
/*  293 */         if (quadPaths != null) {
/*  294 */           for (QuadPath quadPath : quadPaths) {
/*  295 */             quadPath.mClip = false;
/*      */           }
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void resetClipPaths() {
/*  307 */     resetClipPaths(true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void stop() {
/*  316 */     cancel();
/*  317 */     this.mIsRunning = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void cancel() {
/*  326 */     if (this.mMultiPageTask != null) {
/*  327 */       this.mMultiPageTask.cancel(true);
/*      */     }
/*  329 */     for (int i = 0; i < this.mSinglePageTasks.size(); i++) {
/*  330 */       ((GenerateHighlightsTask)this.mSinglePageTasks.valueAt(i)).cancel(true);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRunning() {
/*  338 */     return this.mIsRunning;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getSearchPattern() {
/*  345 */     return this.mSearchPattern;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void highlightPageRange() {
/*  352 */     int pageStart = getReversePageRange();
/*  353 */     int pageEnd = getForwardPageRange();
/*  354 */     boolean shouldSearch = false;
/*      */     int pageNum;
/*  356 */     for (pageNum = pageStart; pageNum <= pageEnd; pageNum++) {
/*  357 */       PageHighlights pageHlts = (PageHighlights)this.mPageHighlights.get(pageNum);
/*  358 */       if (pageHlts == null) {
/*  359 */         pageHlts = new PageHighlights();
/*  360 */         this.mPageHighlights.put(pageNum, pageHlts);
/*  361 */         shouldSearch = true;
/*      */       } 
/*      */     } 
/*      */     
/*  365 */     if (!shouldSearch) {
/*      */       return;
/*      */     }
/*      */     
/*  369 */     for (pageNum = pageStart; pageNum <= pageEnd; pageNum++) {
/*  370 */       PageHighlights pageHlts = (PageHighlights)this.mPageHighlights.get(pageNum);
/*      */       
/*  372 */       pageHlts.mNumTasks++;
/*      */     } 
/*      */ 
/*      */     
/*  376 */     if (this.mMultiPageTask != null && this.mMultiPageTask.getStatus() != AsyncTask.Status.FINISHED) {
/*  377 */       this.mMultiPageTask.cancel(true);
/*      */     }
/*  379 */     this.mMultiPageTask = new GenerateHighlightsTask(this.mPdfViewCtrl, pageStart, pageEnd, this.mSearchPattern, this.mSettings.mMatchCase, this.mSettings.mMatchWholeWords, this.mSettings.mUseRegularExpressions);
/*      */     
/*  381 */     this.mMultiPageTask.setCallback(this);
/*  382 */     this.mMultiPageTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Object[])new Void[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void highlightPage(int pageNum) {
/*  392 */     PageHighlights pageHlts = (PageHighlights)this.mPageHighlights.get(pageNum);
/*  393 */     if (pageHlts != null) {
/*      */       return;
/*      */     }
/*      */     
/*  397 */     pageHlts = new PageHighlights();
/*  398 */     pageHlts.mNumTasks++;
/*  399 */     this.mPageHighlights.put(pageNum, pageHlts);
/*      */     
/*  401 */     GenerateHighlightsTask task = (GenerateHighlightsTask)this.mSinglePageTasks.get(pageNum);
/*      */     
/*  403 */     if (task != null && task.getStatus() != AsyncTask.Status.FINISHED) {
/*  404 */       task.cancel(true);
/*      */     }
/*  406 */     task = new GenerateHighlightsTask(this.mPdfViewCtrl, pageNum, pageNum, this.mSearchPattern, this.mSettings.mMatchCase, this.mSettings.mMatchWholeWords, this.mSettings.mUseRegularExpressions);
/*      */     
/*  408 */     task.setCallback(this);
/*  409 */     task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Object[])new Void[0]);
/*  410 */     this.mSinglePageTasks.put(pageNum, task);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateSearchSelection() {
/*  418 */     resetSearchPaths(true);
/*      */     
/*  420 */     this.mSelQuadPaths.clear();
/*  421 */     getSelections().clear((ViewGroup)this.mPdfViewCtrl);
/*      */     
/*  423 */     int selPageBegin = this.mPdfViewCtrl.getSelectionBeginPage();
/*  424 */     int selPageEnd = this.mPdfViewCtrl.getSelectionEndPage();
/*      */ 
/*      */     
/*  427 */     for (int page = selPageBegin; page <= selPageEnd; page++) {
/*  428 */       if (this.mPdfViewCtrl.hasSelectionOnPage(page)) {
/*      */ 
/*      */ 
/*      */         
/*  432 */         PDFViewCtrl.Selection sel = this.mPdfViewCtrl.getSelection(page);
/*  433 */         double[] quads = sel.getQuads();
/*  434 */         int quad_count = quads.length / 8;
/*      */         
/*  436 */         if (quad_count != 0) {
/*      */ 
/*      */ 
/*      */           
/*  440 */           ArrayList<QuadPath> quadPaths = new ArrayList<>();
/*      */           
/*  442 */           int k = 0;
/*  443 */           for (int j = 0; j < quad_count; j++, k += 8) {
/*  444 */             QuadPath quadPath = new QuadPath(Arrays.copyOfRange(quads, k, k + 8));
/*  445 */             quadPaths.add(quadPath);
/*      */           } 
/*      */ 
/*      */           
/*  449 */           this.mSelQuadPaths.put(page, quadPaths);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateVisibleHighlights() {
/*  458 */     resetHighlightPaths(false);
/*      */ 
/*      */     
/*  461 */     if (!this.mPdfViewCtrl.isContinuousPagePresentationMode(this.mPdfViewCtrl.getPagePresentationMode())) {
/*  462 */       this.mState = 0;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void update() {
/*  470 */     updateSearchSelection();
/*  471 */     updateVisibleHighlights();
/*  472 */     this.mPdfViewCtrl.invalidate();
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
/*      */   protected void makeQuadPath(int page, QuadPath quadPath) {
/*  484 */     float sx = this.mPdfViewCtrl.getScrollX();
/*  485 */     float sy = this.mPdfViewCtrl.getScrollY();
/*      */     
/*  487 */     float minX = Float.MAX_VALUE, minY = Float.MAX_VALUE, maxX = Float.MIN_VALUE, maxY = Float.MIN_VALUE;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  492 */     if (quadPath.mClip) {
/*      */       try {
/*  494 */         getVisibleRect(page);
/*      */ 
/*      */         
/*  497 */         this.mQuadRect.set(quadPath.mQuads[2], quadPath.mQuads[3], quadPath.mQuads[6], quadPath.mQuads[7]);
/*      */         
/*  499 */         if (!intersect(this.mQuadRect, this.mVisRect)) {
/*      */           return;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*  505 */         double[] pts = this.mPdfViewCtrl.convPagePtToScreenPt(this.mQuadRect.getX1(), this.mQuadRect.getY1(), page);
/*  506 */         this.mTempRectF.left = (float)pts[0] + sx;
/*  507 */         this.mTempRectF.bottom = (float)pts[1] + sy;
/*  508 */         pts = this.mPdfViewCtrl.convPagePtToScreenPt(this.mQuadRect.getX2(), this.mQuadRect.getY2(), page);
/*  509 */         this.mTempRectF.right = (float)pts[0] + sx;
/*  510 */         this.mTempRectF.top = (float)pts[1] + sy;
/*  511 */         Rect tempRect = new Rect(this.mTempRectF.left, this.mTempRectF.top, this.mTempRectF.right, this.mTempRectF.bottom);
/*  512 */         tempRect.normalize();
/*  513 */         this.mTempRectF = new RectF((float)tempRect.getX1(), (float)tempRect.getY1(), (float)tempRect.getX2(), (float)tempRect.getY2());
/*  514 */         quadPath.mPath.addRect(this.mTempRectF, Path.Direction.CW);
/*  515 */       } catch (PDFNetException e) {
/*  516 */         Log.e(TAG, e.getMessage());
/*      */       } 
/*      */     } else {
/*      */       float x, y;
/*      */       
/*  521 */       if (this.mPdfViewCtrl.isContinuousPagePresentationMode(this.mPdfViewCtrl.getPagePresentationMode())) {
/*  522 */         double[] pts = this.mPdfViewCtrl.convPagePtToScreenPt(quadPath.mQuads[0], quadPath.mQuads[1], page);
/*  523 */         x = (float)pts[0] + sx;
/*  524 */         y = (float)pts[1] + sy;
/*      */       } else {
/*  526 */         double[] pts = this.mPdfViewCtrl.convPagePtToHorizontalScrollingPt(quadPath.mQuads[0], quadPath.mQuads[1], page);
/*  527 */         x = (float)pts[0];
/*  528 */         y = (float)pts[1];
/*      */       } 
/*  530 */       this.mTempRectF.left = x;
/*  531 */       this.mTempRectF.bottom = y;
/*  532 */       PointF pt1 = new PointF(x, y);
/*  533 */       if (x < minX) minX = x; 
/*  534 */       if (y < minY) minY = y; 
/*  535 */       if (x > maxX) maxX = x; 
/*  536 */       if (y > maxY) maxY = y;
/*      */       
/*  538 */       if (this.mPdfViewCtrl.isContinuousPagePresentationMode(this.mPdfViewCtrl.getPagePresentationMode())) {
/*  539 */         double[] arrayOfDouble = this.mPdfViewCtrl.convPagePtToScreenPt(quadPath.mQuads[2], quadPath.mQuads[3], page);
/*  540 */         x = (float)arrayOfDouble[0] + sx;
/*  541 */         y = (float)arrayOfDouble[1] + sy;
/*      */       } else {
/*  543 */         double[] arrayOfDouble = this.mPdfViewCtrl.convPagePtToHorizontalScrollingPt(quadPath.mQuads[2], quadPath.mQuads[3], page);
/*  544 */         x = (float)arrayOfDouble[0];
/*  545 */         y = (float)arrayOfDouble[1];
/*      */       } 
/*  547 */       this.mTempRectF.right = x;
/*  548 */       if (x < minX) minX = x; 
/*  549 */       if (y < minY) minY = y; 
/*  550 */       if (x > maxX) maxX = x; 
/*  551 */       if (y > maxY) maxY = y;
/*      */       
/*  553 */       if (this.mPdfViewCtrl.isContinuousPagePresentationMode(this.mPdfViewCtrl.getPagePresentationMode())) {
/*  554 */         double[] arrayOfDouble = this.mPdfViewCtrl.convPagePtToScreenPt(quadPath.mQuads[4], quadPath.mQuads[5], page);
/*  555 */         x = (float)arrayOfDouble[0] + sx;
/*  556 */         y = (float)arrayOfDouble[1] + sy;
/*      */       } else {
/*  558 */         double[] arrayOfDouble = this.mPdfViewCtrl.convPagePtToHorizontalScrollingPt(quadPath.mQuads[4], quadPath.mQuads[5], page);
/*  559 */         x = (float)arrayOfDouble[0];
/*  560 */         y = (float)arrayOfDouble[1];
/*      */       } 
/*  562 */       this.mTempRectF.top = y;
/*  563 */       PointF pt3 = new PointF(x, y);
/*  564 */       if (x < minX) minX = x; 
/*  565 */       if (y < minY) minY = y; 
/*  566 */       if (x > maxX) maxX = x; 
/*  567 */       if (y > maxY) maxY = y;
/*      */       
/*  569 */       if (this.mPdfViewCtrl.isContinuousPagePresentationMode(this.mPdfViewCtrl.getPagePresentationMode())) {
/*  570 */         double[] arrayOfDouble = this.mPdfViewCtrl.convPagePtToScreenPt(quadPath.mQuads[6], quadPath.mQuads[7], page);
/*  571 */         x = (float)arrayOfDouble[0] + sx;
/*  572 */         y = (float)arrayOfDouble[1] + sy;
/*      */       } else {
/*  574 */         double[] arrayOfDouble = this.mPdfViewCtrl.convPagePtToHorizontalScrollingPt(quadPath.mQuads[6], quadPath.mQuads[7], page);
/*  575 */         x = (float)arrayOfDouble[0];
/*  576 */         y = (float)arrayOfDouble[1];
/*      */       } 
/*  578 */       PointF pt4 = new PointF(x, y);
/*  579 */       if (x < minX) minX = x; 
/*  580 */       if (y < minY) minY = y; 
/*  581 */       if (x > maxX) maxX = x; 
/*  582 */       if (y > maxY) maxY = y; 
/*      */       try {
/*      */         Rect tempRect;
/*  585 */         Page page1 = this.mPdfViewCtrl.getDoc().getPage(page);
/*  586 */         if (page1.getRotation() == 1 || page1
/*  587 */           .getRotation() == 3 || this.mPdfViewCtrl
/*  588 */           .getPageRotation() == 1 || this.mPdfViewCtrl
/*  589 */           .getPageRotation() == 3) {
/*      */           
/*  591 */           tempRect = new Rect(pt1.x, pt1.y, pt3.x, pt3.y);
/*      */         } else {
/*  593 */           tempRect = new Rect(pt1.x, pt1.y, pt3.x, pt4.y);
/*      */         } 
/*  595 */         tempRect.normalize();
/*  596 */         this.mTempRectF = new RectF((float)tempRect.getX1(), (float)tempRect.getY1(), (float)tempRect.getX2(), (float)tempRect.getY2());
/*  597 */       } catch (PDFNetException e) {
/*  598 */         Log.e(TAG, e.getMessage());
/*      */       } 
/*  600 */       quadPath.mPath.addRect(this.mTempRectF, Path.Direction.CW);
/*      */ 
/*      */       
/*  603 */       if (minX < Float.MAX_VALUE && minY < Float.MAX_VALUE && maxX > Float.MIN_VALUE && maxY > Float.MIN_VALUE) {
/*      */         
/*  605 */         float pathWidth = maxX - minX;
/*  606 */         float pathHeight = maxY - minY;
/*  607 */         if (this.mMaxTextureSize > 0 && (pathWidth > this.mMaxTextureSize || pathHeight > this.mMaxTextureSize)) {
/*      */           
/*  609 */           quadPath.mClip = true;
/*  610 */           quadPath.mPath.reset();
/*  611 */           this.mPathsClipped = true;
/*  612 */           makeQuadPath(page, quadPath);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getReversePageRange() {
/*  622 */     int prev = this.mPdfViewCtrl.getCurrentPage();
/*      */ 
/*      */     
/*  625 */     for (int i = 0; i < 5 && prev - 1 > 0; i++) {
/*  626 */       prev--;
/*  627 */       if (this.mPageHighlights.get(prev) != null) {
/*      */         
/*  629 */         prev++;
/*      */         break;
/*      */       } 
/*      */     } 
/*  633 */     return prev;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getForwardPageRange() {
/*  640 */     int pageCount = this.mPdfViewCtrl.getPageCount();
/*  641 */     int next = this.mPdfViewCtrl.getCurrentPage();
/*      */ 
/*      */     
/*  644 */     for (int i = 0; i < 5 && next + 1 <= pageCount; i++) {
/*  645 */       next++;
/*  646 */       if (this.mPageHighlights.get(next) != null) {
/*      */         
/*  648 */         next--;
/*      */         break;
/*      */       } 
/*      */     } 
/*  652 */     return next;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ToolManager.ToolModeBase getToolMode() {
/*  660 */     return ToolManager.ToolMode.TEXT_HIGHLIGHTER;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getCreateAnnotType() {
/*  665 */     return 28;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDraw(Canvas canvas, Matrix tfm) {
/*  673 */     super.onDraw(canvas, tfm);
/*      */     
/*  675 */     for (int pageNum : this.mPdfViewCtrl.getVisiblePagesInTransition()) {
/*  676 */       PageHighlights pgHlts = (PageHighlights)this.mPageHighlights.get(pageNum);
/*  677 */       if (pgHlts != null && pgHlts.mLoaded) {
/*  678 */         ArrayList<QuadPath> quadPaths = pgHlts.mQuadPaths;
/*  679 */         for (QuadPath quadPath : quadPaths) {
/*  680 */           if (quadPath.mPath.isEmpty() && this.mState != 2 && this.mState != 1) {
/*      */ 
/*      */ 
/*      */             
/*  684 */             boolean skipCurrentPage = false;
/*      */             
/*  686 */             if (this.mPdfViewCtrl.isSlidingWhileZoomed()) {
/*  687 */               for (int pageNum2 : this.mPdfViewCtrl.getVisiblePages()) {
/*  688 */                 if (pageNum == pageNum2) {
/*  689 */                   skipCurrentPage = true;
/*      */                   break;
/*      */                 } 
/*      */               } 
/*      */             }
/*  694 */             if (!skipCurrentPage) {
/*  695 */               makeQuadPath(pageNum, quadPath);
/*      */             }
/*      */           } 
/*  698 */           ArrayList<QuadPath> selQuadPaths = (ArrayList<QuadPath>)this.mSelQuadPaths.get(pageNum);
/*  699 */           boolean drawQuad = true;
/*  700 */           if (selQuadPaths != null) {
/*  701 */             for (QuadPath selQuadPath : selQuadPaths) {
/*  702 */               if (selQuadPath.compareQuads(quadPath)) {
/*      */                 
/*  704 */                 drawQuad = false;
/*      */                 break;
/*      */               } 
/*      */             } 
/*      */           }
/*  709 */           if (drawQuad) {
/*  710 */             if (this.mPdfViewCtrl.isMaintainZoomEnabled()) {
/*  711 */               canvas.save();
/*      */               try {
/*  713 */                 int dx = (this.mPdfViewCtrl.isCurrentSlidingCanvas(pageNum) ? 0 : this.mPdfViewCtrl.getSlidingScrollX()) - this.mPdfViewCtrl.getScrollXOffsetInTools(pageNum);
/*  714 */                 int dy = (this.mPdfViewCtrl.isCurrentSlidingCanvas(pageNum) ? 0 : this.mPdfViewCtrl.getSlidingScrollY()) - this.mPdfViewCtrl.getScrollYOffsetInTools(pageNum);
/*  715 */                 canvas.translate(dx, dy);
/*  716 */                 canvas.drawPath(quadPath.mPath, this.mHighlightPen);
/*      */               } finally {
/*  718 */                 canvas.restore();
/*      */               }  continue;
/*      */             } 
/*  721 */             canvas.drawPath(quadPath.mPath, this.mHighlightPen);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void highlightSelection() {
/*  730 */     getSelections().clear((ViewGroup)this.mPdfViewCtrl);
/*  731 */     for (int pageNum : this.mPdfViewCtrl.getVisiblePagesInTransition()) {
/*  732 */       ArrayList<QuadPath> quadPaths = (ArrayList<QuadPath>)this.mSelQuadPaths.get(pageNum);
/*  733 */       if (quadPaths != null) {
/*  734 */         for (QuadPath quadPath : quadPaths) {
/*  735 */           if (quadPath.mPath.isEmpty() && this.mState != 2 && this.mState != 1)
/*      */           {
/*      */ 
/*      */             
/*  739 */             makeQuadPath(pageNum, quadPath);
/*      */           }
/*  741 */           getSelections().createAndAddSelectionView(this.mPdfViewCtrl, quadPath.getRect(), pageNum, this.mSelectionColor, this.mSelectionBlendMode);
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onLayout(boolean changed, int l, int t, int r, int b) {
/*  752 */     super.onLayout(changed, l, t, r, b);
/*      */ 
/*      */     
/*  755 */     resetSearchPaths(true);
/*  756 */     resetHighlightPaths(true);
/*      */     
/*  758 */     this.mPdfViewCtrl.invalidate();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onScaleBegin(float x, float y) {
/*  766 */     this.mState = 1;
/*  767 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onScaleEnd(float x, float y) {
/*  775 */     this.mState = 0;
/*      */     
/*  777 */     resetSearchPaths(true);
/*  778 */     resetHighlightPaths(true);
/*  779 */     this.mPathsClipped = false;
/*  780 */     resetClipPaths(true);
/*      */     
/*  782 */     this.mPdfViewCtrl.invalidate();
/*      */     
/*  784 */     return super.onScaleEnd(x, y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onScrollChanged(int l, int t, int oldl, int oldt) {
/*  792 */     super.onScrollChanged(l, t, oldl, oldt);
/*      */     
/*  794 */     if (this.mState != 2)
/*      */     {
/*  796 */       for (int pageNum : this.mPdfViewCtrl.getVisiblePagesInTransition()) {
/*  797 */         PageHighlights pgHlts = (PageHighlights)this.mPageHighlights.get(pageNum);
/*  798 */         if (pgHlts == null) {
/*  799 */           highlightPageRange();
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     }
/*  805 */     if (this.mPathsClipped) {
/*  806 */       resetSearchPaths(false);
/*  807 */       resetHighlightPaths(false);
/*  808 */       if (this.mState != 2) {
/*  809 */         this.mPdfViewCtrl.invalidate();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onUp(MotionEvent e, PDFViewCtrl.PriorEventMode priorEventMode) {
/*  819 */     boolean handled = super.onUp(e, priorEventMode);
/*      */     
/*  821 */     if (priorEventMode == PDFViewCtrl.PriorEventMode.FLING) {
/*  822 */       this.mState = 2;
/*      */     }
/*      */     
/*  825 */     return handled;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onFlingStop() {
/*  833 */     this.mState = 0;
/*      */     
/*  835 */     for (int pageNum : this.mPdfViewCtrl.getVisiblePagesInTransition()) {
/*  836 */       PageHighlights pgHlts = (PageHighlights)this.mPageHighlights.get(pageNum);
/*  837 */       if (pgHlts == null) {
/*  838 */         highlightPageRange();
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*  843 */     if (this.mPathsClipped) {
/*  844 */       this.mPdfViewCtrl.invalidate();
/*      */     }
/*      */     
/*  847 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onPageTurning(int old_page, int cur_page) {
/*  855 */     this.mState = 0;
/*      */ 
/*      */     
/*  858 */     resetSearchPaths(false);
/*  859 */     resetHighlightPaths(false);
/*  860 */     this.mPdfViewCtrl.invalidate();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onClose() {
/*  868 */     super.onClose();
/*  869 */     clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDoubleTapZoomAnimationBegin() {
/*  877 */     this.mState = 1;
/*  878 */     resetSearchPaths(true);
/*  879 */     resetHighlightPaths(true);
/*  880 */     this.mPdfViewCtrl.invalidate();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDoubleTapZoomAnimationEnd() {
/*  888 */     this.mState = 0;
/*  889 */     this.mPathsClipped = false;
/*  890 */     resetClipPaths();
/*      */     
/*  892 */     this.mPdfViewCtrl.invalidate();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean intersect(Rect rect1, Rect rect2) {
/*      */     try {
/*  903 */       rect1.normalize();
/*  904 */       rect2.normalize();
/*  905 */       if (rect1.intersectRect(rect1, rect2)) {
/*  906 */         if (rect1.getX1() < rect2.getX1()) {
/*  907 */           rect1.setX1(rect2.getX1());
/*      */         }
/*  909 */         if (rect1.getY1() < rect2.getY1()) {
/*  910 */           rect1.setY1(rect2.getY1());
/*      */         }
/*  912 */         if (rect1.getX2() > rect2.getX2()) {
/*  913 */           rect1.setX2(rect2.getX2());
/*      */         }
/*  915 */         if (rect1.getY2() > rect2.getY2()) {
/*  916 */           rect1.setY2(rect2.getY2());
/*      */         }
/*  918 */         return true;
/*      */       } 
/*  920 */     } catch (PDFNetException e) {
/*  921 */       Log.e(TAG, e.getMessage());
/*      */     } 
/*  923 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void getVisibleRect(int pageNum) {
/*  930 */     double[] visPts1 = this.mPdfViewCtrl.convScreenPtToPagePt(0.0D, this.mPdfViewCtrl.getHeight(), pageNum);
/*  931 */     double[] visPts2 = this.mPdfViewCtrl.convScreenPtToPagePt(this.mPdfViewCtrl.getWidth(), 0.0D, pageNum);
/*      */     try {
/*  933 */       this.mVisRect.set(visPts1[0], visPts1[1], visPts2[0], visPts2[1]);
/*  934 */     } catch (PDFNetException e) {
/*  935 */       Log.e(TAG, e.getMessage());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static int getMaxTextureSize() {
/*  946 */     int IMAGE_MAX_BITMAP_DIMENSION = 2048;
/*      */ 
/*      */     
/*  949 */     EGL10 egl = (EGL10)EGLContext.getEGL();
/*  950 */     EGLDisplay display = egl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
/*      */ 
/*      */     
/*  953 */     int[] version = new int[2];
/*  954 */     egl.eglInitialize(display, version);
/*      */ 
/*      */     
/*  957 */     int[] totalConfigurations = new int[1];
/*  958 */     egl.eglGetConfigs(display, null, 0, totalConfigurations);
/*      */ 
/*      */     
/*  961 */     EGLConfig[] configurationsList = new EGLConfig[totalConfigurations[0]];
/*  962 */     egl.eglGetConfigs(display, configurationsList, totalConfigurations[0], totalConfigurations);
/*      */     
/*  964 */     int[] textureSize = new int[1];
/*  965 */     int maximumTextureSize = 0;
/*      */ 
/*      */     
/*  968 */     for (int i = 0; i < totalConfigurations[0]; i++) {
/*      */       
/*  970 */       egl.eglGetConfigAttrib(display, configurationsList[i], 12332, textureSize);
/*      */ 
/*      */       
/*  973 */       if (maximumTextureSize < textureSize[0]) {
/*  974 */         maximumTextureSize = textureSize[0];
/*      */       }
/*      */     } 
/*      */     
/*  978 */     egl.eglTerminate(display);
/*      */ 
/*      */     
/*  981 */     return Math.max(maximumTextureSize, 2048);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onNightModeUpdated(boolean isNightMode) {
/*  989 */     this.mHighlightPen = isNightMode ? this.mHighlightPaintInNightMode : this.mHighlightPaintInDayMode;
/*  990 */     this
/*      */       
/*  992 */       .mSelectionColor = isNightMode ? this.mPdfViewCtrl.getContext().getResources().getColor(R.color.tools_text_highlighter_selection_color_inverse) : this.mPdfViewCtrl.getContext().getResources().getColor(R.color.tools_text_highlighter_selection_color);
/*  993 */     this.mSelectionBlendMode = isNightMode ? PorterDuff.Mode.SCREEN : PorterDuff.Mode.MULTIPLY;
/*      */   }
/*      */ 
/*      */   
/*      */   public void onHighlightsTaskCancelled(int pageStart, int pageEnd) {
/*  998 */     for (int pageNum = pageStart; pageNum <= pageEnd; pageNum++) {
/*      */       
/* 1000 */       PageHighlights pgHlts = (PageHighlights)this.mPageHighlights.get(pageNum);
/* 1001 */       if (pgHlts != null) {
/* 1002 */         if (!pgHlts.mLoaded && pgHlts.mNumTasks > 1) {
/*      */ 
/*      */           
/* 1005 */           pgHlts.mNumTasks--;
/*      */         } else {
/*      */           
/* 1008 */           this.mPageHighlights.remove(pageNum);
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void onHighlightsTaskFinished(Highlights[] highlights, int pageStart, int pageEnd) {
/* 1017 */     PDFDoc doc = this.mPdfViewCtrl.getDoc();
/* 1018 */     for (int i = 0, cnt = highlights.length; i < cnt; i++) {
/* 1019 */       PageHighlights pageHlts = (PageHighlights)this.mPageHighlights.get(pageStart + i);
/*      */       
/* 1021 */       if (pageHlts == null) {
/* 1022 */         Log.e(TAG, "Page result for page " + (pageStart + i) + " is null");
/*      */         
/*      */         return;
/*      */       } 
/* 1026 */       pageHlts.mQuadPaths.clear();
/*      */       
/* 1028 */       boolean shouldUnlockRead = false;
/*      */       try {
/* 1030 */         this.mPdfViewCtrl.docLockRead();
/* 1031 */         shouldUnlockRead = true;
/*      */         
/* 1033 */         highlights[i].begin(doc);
/*      */         
/* 1035 */         while (highlights[i].hasNext()) {
/* 1036 */           double[] quads = highlights[i].getCurrentQuads();
/* 1037 */           int quad_count = quads.length / 8;
/*      */           
/* 1039 */           if (quad_count == 0) {
/* 1040 */             highlights[i].next();
/*      */             
/*      */             continue;
/*      */           } 
/* 1044 */           int k = 0;
/* 1045 */           for (int j = 0; j < quad_count; j++, k += 8) {
/* 1046 */             QuadPath quadPath = new QuadPath(Arrays.copyOfRange(quads, k, k + 8));
/* 1047 */             pageHlts.mQuadPaths.add(quadPath);
/*      */           } 
/*      */           
/* 1050 */           highlights[i].next();
/*      */         } 
/* 1052 */       } catch (PDFNetException e) {
/*      */       
/*      */       } finally {
/* 1055 */         if (shouldUnlockRead) {
/* 1056 */           this.mPdfViewCtrl.docUnlockRead();
/*      */         }
/*      */       } 
/*      */       
/* 1060 */       pageHlts.mLoaded = true;
/* 1061 */       pageHlts.mNumTasks--;
/*      */     } 
/*      */     
/* 1064 */     if (this.mState == 0)
/* 1065 */       this.mPdfViewCtrl.invalidate(); 
/*      */   }
/*      */   
/*      */   private class PageHighlights { private PageHighlights() {}
/*      */     
/* 1070 */     ArrayList<QuadPath> mQuadPaths = new ArrayList<>();
/*      */     
/*      */     boolean mLoaded;
/*      */     
/*      */     int mNumTasks; }
/*      */ 
/*      */   
/*      */   private static final class QuadPath
/*      */   {
/*      */     double[] mQuads;
/*      */     Path mPath;
/*      */     boolean mClip;
/*      */     
/*      */     QuadPath(double[] quads) {
/* 1084 */       this.mQuads = quads;
/* 1085 */       this.mPath = new Path();
/* 1086 */       this.mClip = false;
/*      */     }
/*      */     
/*      */     boolean compareQuads(QuadPath quadPath) {
/* 1090 */       if (quadPath != null && 
/* 1091 */         this.mQuads.length == 8 && quadPath.mQuads.length == 8) {
/* 1092 */         for (int i = 0; i < 8; i++) {
/* 1093 */           if (this.mQuads[i] != quadPath.mQuads[i]) {
/* 1094 */             return false;
/*      */           }
/*      */         } 
/* 1097 */         return true;
/*      */       } 
/*      */       
/* 1100 */       return false;
/*      */     }
/*      */     
/*      */     @Nullable
/*      */     Rect getRect() {
/* 1105 */       Rect rect = null;
/*      */       try {
/* 1107 */         rect = new Rect(this.mQuads[0], this.mQuads[1], this.mQuads[4], this.mQuads[5]);
/* 1108 */         rect.normalize();
/* 1109 */       } catch (PDFNetException e) {
/* 1110 */         e.printStackTrace();
/*      */       } 
/* 1112 */       return rect;
/*      */     }
/*      */   }
/*      */   
/*      */   private TextSearchSelections getSelections() {
/* 1117 */     return ((ToolManager)this.mPdfViewCtrl.getToolManager()).mTextSearchSelections;
/*      */   }
/*      */   
/*      */   private class TextHighlighterSettings {
/*      */     boolean mMatchCase;
/*      */     boolean mMatchWholeWords;
/*      */     boolean mUseRegularExpressions;
/*      */     
/*      */     private TextHighlighterSettings() {}
/*      */   }
/*      */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\TextHighlighter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */