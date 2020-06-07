/*      */ package com.pdftron.pdf.tools;
/*      */ 
/*      */ import android.content.Context;
/*      */ import android.content.DialogInterface;
/*      */ import android.content.SharedPreferences;
/*      */ import android.content.res.Configuration;
/*      */ import android.graphics.Color;
/*      */ import android.graphics.PointF;
/*      */ import android.graphics.RectF;
/*      */ import android.text.Editable;
/*      */ import android.text.Html;
/*      */ import android.text.TextUtils;
/*      */ import android.text.TextWatcher;
/*      */ import android.view.KeyEvent;
/*      */ import android.view.MotionEvent;
/*      */ import android.view.View;
/*      */ import android.view.inputmethod.InputMethodManager;
/*      */ import androidx.annotation.Keep;
/*      */ import androidx.annotation.NonNull;
/*      */ import androidx.annotation.Nullable;
/*      */ import androidx.fragment.app.FragmentActivity;
/*      */ import androidx.lifecycle.ViewModelProviders;
/*      */ import com.pdftron.common.PDFNetException;
/*      */ import com.pdftron.pdf.Annot;
/*      */ import com.pdftron.pdf.ColorPt;
/*      */ import com.pdftron.pdf.Element;
/*      */ import com.pdftron.pdf.ElementReader;
/*      */ import com.pdftron.pdf.PDFViewCtrl;
/*      */ import com.pdftron.pdf.Page;
/*      */ import com.pdftron.pdf.Point;
/*      */ import com.pdftron.pdf.Rect;
/*      */ import com.pdftron.pdf.annots.FreeText;
/*      */ import com.pdftron.pdf.annots.Markup;
/*      */ import com.pdftron.pdf.config.ToolStyleConfig;
/*      */ import com.pdftron.pdf.model.FreeTextCacheStruct;
/*      */ import com.pdftron.pdf.model.FreeTextInfo;
/*      */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*      */ import com.pdftron.pdf.utils.AnnotUtils;
/*      */ import com.pdftron.pdf.utils.InlineEditText;
/*      */ import com.pdftron.pdf.utils.ShortcutHelper;
/*      */ import com.pdftron.pdf.utils.Utils;
/*      */ import com.pdftron.pdf.viewmodel.RichTextViewModel;
/*      */ import com.pdftron.pdf.widget.AutoScrollEditText;
/*      */ import com.pdftron.sdf.Doc;
/*      */ import com.pdftron.sdf.Obj;
/*      */ import java.util.ArrayList;
/*      */ import org.json.JSONException;
/*      */ import org.json.JSONObject;
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
/*      */ public class FreeTextCreate
/*      */   extends Tool
/*      */   implements DialogAnnotNote.DialogAnnotNoteListener, InlineEditText.InlineEditTextListener, TextWatcher
/*      */ {
/*   63 */   private static final String TAG = FreeTextCreate.class.getName();
/*      */   
/*      */   private static final int THRESHOLD_FROM_PAGE_EDGE = 3;
/*      */   
/*      */   private static final int MINIMUM_BBOX_WIDTH = 50;
/*      */   
/*      */   protected PointF mTargetPointCanvasSpace;
/*      */   
/*      */   protected Point mTargetPointPageSpace;
/*      */   protected int mPageNum;
/*      */   private int mTextColor;
/*      */   private float mTextSize;
/*      */   private int mStrokeColor;
/*      */   private float mThickness;
/*      */   private int mFillColor;
/*      */   private float mOpacity;
/*      */   private String mPDFTronFontName;
/*      */   private int mCurrentEditMode;
/*      */   private boolean mUpdateEditMode;
/*      */   protected InlineEditText mInlineEditText;
/*      */   private int mAnnotButtonPressed;
/*      */   private long mStoredTimeStamp;
/*      */   protected boolean mOnUpOccurred;
/*   86 */   private float mStoredPointX = 0.0F;
/*   87 */   private float mStoredPointY = 0.0F;
/*      */   
/*      */   private String mCacheFileName;
/*      */   
/*      */   private DialogFreeTextNote mDialogFreeTextNote;
/*      */   
/*      */   protected boolean mFreeTextInlineToggleEnabled;
/*      */   
/*      */   protected boolean mOnCloseOccurred;
/*      */   
/*      */   private static final String DEFAULT_RECT_X1_KEY = "pdftron_defaultX1";
/*      */   
/*      */   private static final String DEFAULT_RECT_Y1_KEY = "pdftron_defaultY1";
/*      */   
/*      */   private static final String DEFAULT_RECT_X2_KEY = "pdftron_defaultX2";
/*      */   
/*      */   private static final String DEFAULT_RECT_Y2_KEY = "pdftron_defaultY2";
/*      */   private boolean mRichContentEnabled;
/*      */   @Nullable
/*      */   private RichTextViewModel mRichTextViewModel;
/*      */   
/*      */   public FreeTextCreate(@NonNull PDFViewCtrl ctrl) {
/*  109 */     super(ctrl);
/*  110 */     this.mNextToolMode = getToolMode();
/*  111 */     this.mTargetPointCanvasSpace = new PointF(0.0F, 0.0F);
/*  112 */     this.mTargetPointPageSpace = new Point(0.0D, 0.0D);
/*  113 */     this.mStoredTimeStamp = System.currentTimeMillis();
/*      */     
/*  115 */     ToolManager toolManager = (ToolManager)ctrl.getToolManager();
/*  116 */     this.mCacheFileName = toolManager.getFreeTextCacheFileName();
/*  117 */     this.mFreeTextInlineToggleEnabled = toolManager.isfreeTextInlineToggleEnabled();
/*  118 */     this.mRichContentEnabled = toolManager.isRichContentEnabledForFreeText();
/*      */   }
/*      */ 
/*      */   
/*      */   public void onCreate() {
/*  123 */     super.onCreate();
/*      */     
/*  125 */     setRichContentEnabled(this.mRichContentEnabled);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ToolManager.ToolModeBase getToolMode() {
/*  133 */     return ToolManager.ToolMode.TEXT_CREATE;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getCreateAnnotType() {
/*  138 */     return 2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isCreatingAnnotation() {
/*  146 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRichContentEnabled() {
/*  153 */     return this.mRichContentEnabled;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRichContentEnabled(boolean richContentEnabled) {
/*  160 */     this.mRichContentEnabled = richContentEnabled;
/*      */     
/*  162 */     if (richContentEnabled) {
/*  163 */       FragmentActivity activity = ((ToolManager)this.mPdfViewCtrl.getToolManager()).getCurrentActivity();
/*  164 */       if (activity != null) {
/*  165 */         this.mRichTextViewModel = (RichTextViewModel)ViewModelProviders.of(activity).get(RichTextViewModel.class);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setupAnnotProperty(int color, float opacity, float thickness, int fillColor, String icon, String pdftronFontName, int textColor, float textSize) {
/*  175 */     this.mStrokeColor = color;
/*  176 */     this.mThickness = thickness;
/*  177 */     this.mTextColor = textColor;
/*  178 */     this.mTextSize = (int)textSize;
/*  179 */     this.mOpacity = opacity;
/*  180 */     this.mFillColor = fillColor;
/*  181 */     this.mPDFTronFontName = pdftronFontName;
/*      */     
/*  183 */     SharedPreferences settings = Tool.getToolPreferences(this.mPdfViewCtrl.getContext());
/*  184 */     SharedPreferences.Editor editor = settings.edit();
/*      */     
/*  186 */     editor.putInt(getTextColorKey(getCreateAnnotType()), this.mTextColor);
/*  187 */     editor.putFloat(getOpacityKey(getCreateAnnotType()), this.mOpacity);
/*  188 */     editor.putFloat(getTextSizeKey(getCreateAnnotType()), this.mTextSize);
/*  189 */     editor.putInt(getColorFillKey(getCreateAnnotType()), this.mFillColor);
/*  190 */     editor.putInt(getColorKey(getCreateAnnotType()), this.mStrokeColor);
/*  191 */     editor.putFloat(getThicknessKey(getCreateAnnotType()), this.mThickness);
/*  192 */     editor.putString(getFontKey(getCreateAnnotType()), this.mPDFTronFontName);
/*      */     
/*  194 */     editor.apply();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onDown(MotionEvent e) {
/*  202 */     super.onDown(e);
/*  203 */     if (this.mInlineEditText == null || !this.mInlineEditText.isEditing().booleanValue()) {
/*  204 */       initTextStyle();
/*  205 */       this.mAnnotPushedBack = false;
/*  206 */       setTargetPoints(new PointF(e.getX(), e.getY()));
/*      */     } 
/*  208 */     this.mOnUpOccurred = false;
/*      */     
/*  210 */     return super.onDown(e);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onMove(MotionEvent e1, MotionEvent e2, float x_dist, float y_dist) {
/*  218 */     super.onMove(e1, e2, x_dist, y_dist);
/*      */ 
/*      */     
/*  221 */     if (this.mAllowTwoFingerScroll) {
/*  222 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  226 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onScaleBegin(float x, float y) {
/*  235 */     if (this.mInlineEditText != null && this.mInlineEditText.isEditing().booleanValue()) {
/*  236 */       saveAndQuitInlineEditText(true);
/*      */     }
/*  238 */     return super.onScaleBegin(x, y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onFlingStop() {
/*  246 */     if (this.mAllowTwoFingerScroll) {
/*  247 */       doneTwoFingerScrolling();
/*      */     }
/*  249 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onUp(MotionEvent e, PDFViewCtrl.PriorEventMode priorEventMode) {
/*  259 */     if (this.mOnUpOccurred) {
/*  260 */       return false;
/*      */     }
/*  262 */     this.mOnUpOccurred = true;
/*      */     
/*  264 */     if (this.mInlineEditText != null && this.mInlineEditText.isEditing().booleanValue()) {
/*  265 */       saveAndQuitInlineEditText(false);
/*  266 */       return false;
/*      */     } 
/*      */ 
/*      */     
/*  270 */     if (this.mAllowTwoFingerScroll) {
/*  271 */       doneTwoFingerScrolling();
/*  272 */       return false;
/*      */     } 
/*      */     
/*  275 */     ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/*      */ 
/*      */     
/*  278 */     if (toolManager.isQuickMenuJustClosed()) {
/*  279 */       return true;
/*      */     }
/*      */     
/*  282 */     if (priorEventMode == PDFViewCtrl.PriorEventMode.PAGE_SLIDING) {
/*  283 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  287 */     if (priorEventMode == PDFViewCtrl.PriorEventMode.FLING || priorEventMode == PDFViewCtrl.PriorEventMode.PINCH)
/*      */     {
/*      */       
/*  290 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  295 */     if (this.mAnnotPushedBack && this.mForceSameNextToolMode) {
/*  296 */       return true;
/*      */     }
/*      */     
/*  299 */     setTargetPoints(new PointF(e.getX(), e.getY()));
/*  300 */     this.mStoredPointX = e.getX();
/*  301 */     this.mStoredPointY = e.getY();
/*  302 */     if (this.mPageNum >= 1) {
/*      */ 
/*      */ 
/*      */       
/*  306 */       Annot tappedAnnot = didTapOnSameTypeAnnot(e);
/*  307 */       int x = (int)e.getX();
/*  308 */       int y = (int)e.getY();
/*  309 */       int page = this.mPdfViewCtrl.getPageNumberFromScreenPt(x, y);
/*  310 */       if (tappedAnnot != null) {
/*      */         
/*  312 */         setCurrentDefaultToolModeHelper(getToolMode());
/*  313 */         toolManager.selectAnnot(tappedAnnot, page);
/*      */       } else {
/*  315 */         createFreeText();
/*  316 */         return true;
/*      */       } 
/*      */     } 
/*      */     
/*  320 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   protected Annot didTapOnSameTypeAnnot(MotionEvent e) {
/*  325 */     int x = (int)e.getX();
/*  326 */     int y = (int)e.getY();
/*  327 */     ArrayList<Annot> annots = this.mPdfViewCtrl.getAnnotationListAt(x, y, x, y);
/*      */     try {
/*  329 */       for (Annot annot : annots) {
/*  330 */         if (annot.getType() == 2) {
/*  331 */           return annot;
/*      */         }
/*      */       } 
/*  334 */     } catch (PDFNetException ex) {
/*  335 */       AnalyticsHandlerAdapter.getInstance().sendException((Exception)ex);
/*      */     } 
/*      */     
/*  338 */     return null;
/*      */   }
/*      */   
/*      */   private static Rect getRectUnion(Rect rect1, Rect rect2) {
/*  342 */     Rect rectUnion = null;
/*      */     try {
/*  344 */       rectUnion = new Rect();
/*  345 */       rectUnion.setX1(Math.min(rect1.getX1(), rect2.getX1()));
/*  346 */       rectUnion.setY1(Math.min(rect1.getY1(), rect2.getY1()));
/*  347 */       rectUnion.setX2(Math.max(rect1.getX2(), rect2.getX2()));
/*  348 */       rectUnion.setY2(Math.max(rect1.getY2(), rect2.getY2()));
/*  349 */     } catch (PDFNetException e) {
/*  350 */       e.printStackTrace();
/*      */     } 
/*      */     
/*  353 */     return rectUnion;
/*      */   }
/*      */   public static Rect getTextBBoxOnPage(PDFViewCtrl pdfViewCtrl, int pageNum, Point targetPoint) {
/*      */     try {
/*      */       double right, bottom;
/*  358 */       Page page = pdfViewCtrl.getDoc().getPage(pageNum);
/*  359 */       Rect pageCropOnClientF = page.getBox(pdfViewCtrl.getPageBox());
/*  360 */       pageCropOnClientF.normalize();
/*      */ 
/*      */       
/*  363 */       if (targetPoint.x < pageCropOnClientF.getX1()) {
/*  364 */         targetPoint.x = (float)pageCropOnClientF.getX1();
/*      */       }
/*  366 */       if (targetPoint.y < pageCropOnClientF.getY1()) {
/*  367 */         targetPoint.y = (float)pageCropOnClientF.getY1();
/*      */       }
/*  369 */       if (targetPoint.x > pageCropOnClientF.getX2()) {
/*  370 */         targetPoint.x = (float)pageCropOnClientF.getX2();
/*      */       }
/*  372 */       if (targetPoint.y > pageCropOnClientF.getY2()) {
/*  373 */         targetPoint.y = (float)pageCropOnClientF.getY2();
/*      */       }
/*      */ 
/*      */       
/*  377 */       int pageRotation = pdfViewCtrl.getDoc().getPage(pageNum).getRotation();
/*  378 */       int viewRotation = pdfViewCtrl.getPageRotation();
/*  379 */       int annotRotation = (pageRotation + viewRotation) % 4 * 90;
/*      */ 
/*      */       
/*  382 */       double left = targetPoint.x;
/*  383 */       double top = targetPoint.y;
/*  384 */       if (annotRotation == 0) {
/*  385 */         right = pageCropOnClientF.getX2();
/*  386 */         bottom = pageCropOnClientF.getY1();
/*  387 */       } else if (annotRotation == 90) {
/*  388 */         right = pageCropOnClientF.getX2();
/*  389 */         bottom = pageCropOnClientF.getY2();
/*  390 */       } else if (annotRotation == 180) {
/*  391 */         right = pageCropOnClientF.getX1();
/*  392 */         bottom = pageCropOnClientF.getY2();
/*      */       } else {
/*  394 */         right = pageCropOnClientF.getX1();
/*  395 */         bottom = pageCropOnClientF.getY1();
/*      */       } 
/*      */       
/*  398 */       if (Math.abs(right - left) < 3.0D) {
/*  399 */         left = right - 3.0D;
/*      */       }
/*  401 */       if (Math.abs(top - bottom) < 3.0D) {
/*  402 */         top = bottom + 3.0D;
/*      */       }
/*      */       
/*  405 */       Rect rect = new Rect(left, top, right, bottom);
/*  406 */       rect.normalize();
/*  407 */       return rect;
/*  408 */     } catch (Exception e) {
/*  409 */       e.printStackTrace();
/*  410 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void initFreeText(PointF point) {
/*  421 */     this.mAnnotPushedBack = false;
/*  422 */     initTextStyle();
/*  423 */     setTargetPoints(point);
/*  424 */     createFreeText();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onClose() {
/*  432 */     super.onClose();
/*  433 */     this.mOnCloseOccurred = true;
/*      */     
/*  435 */     if (this.mInlineEditText != null && this.mInlineEditText.isEditing().booleanValue()) {
/*  436 */       saveAndQuitInlineEditText(false);
/*      */     }
/*      */     
/*  439 */     if (this.mDialogFreeTextNote != null && this.mDialogFreeTextNote.isShowing()) {
/*      */       
/*  441 */       this.mAnnotButtonPressed = -1;
/*  442 */       prepareDialogFreeTextNoteDismiss();
/*  443 */       this.mDialogFreeTextNote.dismiss();
/*      */     } 
/*      */     
/*  446 */     if (this.mNextToolMode != ToolManager.ToolMode.ANNOT_EDIT) {
/*  447 */       unsetAnnot();
/*      */     }
/*      */ 
/*      */     
/*  451 */     InputMethodManager imm = (InputMethodManager)this.mPdfViewCtrl.getContext().getSystemService("input_method");
/*  452 */     if (imm != null) {
/*  453 */       imm.hideSoftInputFromWindow(this.mPdfViewCtrl.getRootView().getWindowToken(), 0);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onAnnotButtonPressed(int button) {
/*  462 */     this.mAnnotButtonPressed = button;
/*      */   }
/*      */   
/*      */   protected void createFreeText() {
/*      */     try {
/*  467 */       this.mAnnotPushedBack = true;
/*      */       
/*  469 */       if (this.mPageNum < 1) {
/*  470 */         this.mPageNum = this.mPdfViewCtrl.getCurrentPage();
/*      */       }
/*      */ 
/*      */       
/*  474 */       SharedPreferences settings = Tool.getToolPreferences(this.mPdfViewCtrl.getContext());
/*  475 */       this.mCurrentEditMode = settings.getInt("annotation_free_text_preference_editing", 1);
/*  476 */       String cacheStr = null;
/*  477 */       if (Utils.cacheFileExists(this.mPdfViewCtrl.getContext(), this.mCacheFileName)) {
/*  478 */         JSONObject cacheJson = Utils.retrieveToolCache(this.mPdfViewCtrl.getContext(), this.mCacheFileName);
/*  479 */         if (cacheJson != null) {
/*  480 */           cacheStr = cacheJson.getString("contents");
/*      */         }
/*      */       } 
/*      */       
/*  484 */       if (!Utils.isTablet(this.mPdfViewCtrl.getContext()) && 
/*  485 */         (this.mPdfViewCtrl.getContext().getResources().getConfiguration()).orientation == 2) {
/*  486 */         fallbackDialog(cacheStr, true);
/*  487 */       } else if (this.mCurrentEditMode == 2) {
/*  488 */         fallbackDialog(cacheStr, false);
/*      */       } else {
/*  490 */         inlineTextEditing(cacheStr);
/*      */       } 
/*  492 */     } catch (Exception ex) {
/*  493 */       AnalyticsHandlerAdapter.getInstance().sendException(ex, "createFreeText");
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
/*      */ 
/*      */   
/*      */   public RectF getInlineEditTextPosition() {
/*  507 */     int bottom, right = Math.round(this.mTargetPointCanvasSpace.x), left = right;
/*  508 */     int top = Math.round(this.mTargetPointCanvasSpace.y);
/*      */ 
/*      */     
/*  511 */     int viewBottom = this.mPdfViewCtrl.getHeight() + this.mPdfViewCtrl.getScrollY();
/*  512 */     int viewRight = this.mPdfViewCtrl.getWidth() + this.mPdfViewCtrl.getScrollX();
/*  513 */     int viewLeft = this.mPdfViewCtrl.getScrollX();
/*      */ 
/*      */     
/*  516 */     RectF pageCropOnClientF = Utils.buildPageBoundBoxOnClient(this.mPdfViewCtrl, this.mPageNum);
/*  517 */     if (pageCropOnClientF != null) {
/*  518 */       int pageRight = Math.round(pageCropOnClientF.right);
/*  519 */       int pageLeft = Math.round(pageCropOnClientF.left);
/*  520 */       int pageBottom = Math.round(pageCropOnClientF.bottom);
/*      */       
/*  522 */       if (this.mPdfViewCtrl.getRightToLeftLanguage()) {
/*  523 */         left = (viewLeft > pageLeft) ? viewLeft : pageLeft;
/*      */       } else {
/*  525 */         right = (viewRight < pageRight) ? viewRight : pageRight;
/*      */       } 
/*  527 */       bottom = (viewBottom < pageBottom) ? viewBottom : pageBottom;
/*      */     }
/*      */     else {
/*      */       
/*  531 */       if (this.mPdfViewCtrl.getRightToLeftLanguage()) {
/*  532 */         left = viewLeft;
/*      */       } else {
/*  534 */         right = viewRight;
/*      */       } 
/*  536 */       bottom = viewBottom;
/*      */     } 
/*      */     
/*  539 */     return new RectF(left, top, right, bottom);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onConfigurationChanged(Configuration newConfig) {
/*  547 */     super.onConfigurationChanged(newConfig);
/*      */     
/*  549 */     if (this.mInlineEditText != null && this.mInlineEditText.isEditing().booleanValue()) {
/*  550 */       saveAndQuitInlineEditText(false);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void toggleToFreeTextDialog(String interimText) {
/*  559 */     this.mCurrentEditMode = 2;
/*  560 */     this.mUpdateEditMode = true;
/*  561 */     fallbackDialog(interimText, false);
/*  562 */     if (this.mInlineEditText != null && this.mInlineEditText.isEditing().booleanValue()) {
/*  563 */       this.mInlineEditText.getEditText().setCursorVisible(false);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void setNextToolMode() {
/*  568 */     if (this.mAnnot != null && (((ToolManager)this.mPdfViewCtrl.getToolManager()).isAutoSelectAnnotation() || !this.mForceSameNextToolMode)) {
/*  569 */       this.mNextToolMode = ToolManager.ToolMode.ANNOT_EDIT;
/*  570 */       setCurrentDefaultToolModeHelper(getToolMode());
/*  571 */     } else if (this.mForceSameNextToolMode) {
/*  572 */       this.mNextToolMode = getToolMode();
/*      */     } else {
/*  574 */       this.mNextToolMode = ToolManager.ToolMode.PAN;
/*  575 */       if (!this.mOnCloseOccurred) {
/*  576 */         ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/*  577 */         ToolManager.Tool tool = toolManager.createTool(this.mNextToolMode, null);
/*  578 */         toolManager.setTool(tool);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void setTargetPoints(PointF point) {
/*  584 */     this.mPageNum = this.mPdfViewCtrl.getPageNumberFromScreenPt(point.x, point.y);
/*      */ 
/*      */     
/*  587 */     point.x += this.mPdfViewCtrl.getScrollX();
/*  588 */     point.y += this.mPdfViewCtrl.getScrollY();
/*      */     
/*  590 */     double[] pagePt = this.mPdfViewCtrl.convScreenPtToPagePt(point.x, point.y, this.mPageNum);
/*  591 */     this.mTargetPointPageSpace = new Point((int)pagePt[0], (int)pagePt[1]);
/*      */     
/*  593 */     this.mStoredPointX = point.x;
/*  594 */     this.mStoredPointY = point.y;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onPageTurning(int old_page, int cur_page) {
/*  602 */     super.onPageTurning(old_page, cur_page);
/*  603 */     saveAndQuitInlineEditText(false);
/*      */   }
/*      */   
/*      */   protected void saveAndQuitInlineEditText(boolean immediateEditTextRemoval) {
/*  607 */     if (this.mRichTextViewModel != null) {
/*  608 */       this.mRichTextViewModel.onCloseToolbar();
/*      */     }
/*  610 */     if (this.mPdfViewCtrl.isAnnotationLayerEnabled())
/*      */     {
/*  612 */       immediateEditTextRemoval = true;
/*      */     }
/*  614 */     if (this.mInlineEditText != null) {
/*  615 */       String contents = this.mInlineEditText.getContents();
/*  616 */       if (this.mInlineEditText != null && this.mInlineEditText.getRichEditor().getVisibility() == 0) {
/*  617 */         contents = Html.fromHtml(contents).toString().trim();
/*      */       }
/*  619 */       final String text = contents;
/*  620 */       if (!TextUtils.isEmpty(text)) {
/*  621 */         if (this.mRichContentEnabled) {
/*      */           
/*  623 */           this.mInlineEditText.getRichEditor().clearFocusEditor();
/*  624 */           final boolean finalImmediateEditTextRemoval = immediateEditTextRemoval;
/*  625 */           this.mInlineEditText.getRichEditor().postDelayed(new Runnable()
/*      */               {
/*      */                 public void run() {
/*  628 */                   FreeTextCreate.this.commitFreeTextImpl(text, finalImmediateEditTextRemoval);
/*      */                 }
/*      */               }100L);
/*      */         } else {
/*  632 */           commitFreeTextImpl(text, immediateEditTextRemoval);
/*      */         } 
/*      */       } else {
/*  635 */         Utils.deleteCacheFile(this.mPdfViewCtrl.getContext(), this.mCacheFileName);
/*  636 */         quitInlineEditText();
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  641 */     if (this.mUpdateEditMode) {
/*  642 */       SharedPreferences settings = Tool.getToolPreferences(this.mPdfViewCtrl.getContext());
/*  643 */       SharedPreferences.Editor editor = settings.edit();
/*  644 */       editor.putInt("annotation_free_text_preference_editing", this.mCurrentEditMode);
/*  645 */       editor.apply();
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void commitFreeTextImpl(String text, boolean immediateEditTextRemoval) {
/*  650 */     boolean shouldUnlock = false;
/*      */     try {
/*  652 */       this.mPdfViewCtrl.docLock(true);
/*  653 */       shouldUnlock = true;
/*  654 */       createAnnot(text);
/*  655 */       raiseAnnotationAddedEvent(this.mAnnot, this.mAnnotPageNum);
/*      */       
/*  657 */       if (this.mInlineEditText != null && this.mInlineEditText
/*  658 */         .getRichEditor().getVisibility() == 0) {
/*  659 */         AnnotUtils.createRCFreeTextAppearance(this.mInlineEditText
/*  660 */             .getRichEditor(), this.mPdfViewCtrl, this.mAnnot, this.mAnnotPageNum);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  665 */         this.mPdfViewCtrl.update(this.mAnnot, this.mPageNum);
/*      */       } 
/*      */       
/*  668 */       if (this.mInlineEditText != null) {
/*  669 */         this.mInlineEditText.close(immediateEditTextRemoval);
/*      */       }
/*  671 */       Utils.deleteCacheFile(this.mPdfViewCtrl.getContext(), this.mCacheFileName);
/*      */       
/*  673 */       if (!immediateEditTextRemoval) {
/*  674 */         addOldTools();
/*      */       }
/*  676 */     } catch (Exception e) {
/*  677 */       ((ToolManager)this.mPdfViewCtrl.getToolManager()).annotationCouldNotBeAdded(e.getMessage());
/*  678 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*  679 */       if (this.mInlineEditText != null) {
/*  680 */         this.mInlineEditText.removeView();
/*      */       }
/*      */     } finally {
/*  683 */       if (shouldUnlock) {
/*  684 */         this.mPdfViewCtrl.docUnlock();
/*      */       }
/*      */       
/*  687 */       if (immediateEditTextRemoval) {
/*  688 */         this.mInlineEditText = null;
/*      */       }
/*      */       
/*  691 */       setNextToolMode();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void quitInlineEditText() {
/*  696 */     this.mInlineEditText.close(true);
/*  697 */     this.mInlineEditText = null;
/*      */ 
/*      */     
/*  700 */     setNextToolMode();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isFreeTextEditing() {
/*  707 */     if (this.mInlineEditText != null) {
/*  708 */       return this.mInlineEditText.isEditing().booleanValue();
/*      */     }
/*  710 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void inlineTextEditing(String interimText) {
/*  715 */     setNextToolModeHelper(ToolManager.ToolMode.PAN);
/*  716 */     this.mNextToolMode = getToolMode();
/*      */     
/*  718 */     this.mOnUpOccurred = false;
/*      */     
/*  720 */     if (this.mRichTextViewModel != null && this.mRichContentEnabled) {
/*  721 */       this.mRichTextViewModel.onOpenToolbar();
/*      */     }
/*      */ 
/*      */     
/*  725 */     this.mInlineEditText = new InlineEditText(this.mPdfViewCtrl, null, this.mPageNum, this.mTargetPointPageSpace, this.mFreeTextInlineToggleEnabled, this.mRichContentEnabled, this);
/*  726 */     this.mInlineEditText.setRichTextViewModel(this.mRichTextViewModel);
/*  727 */     this.mInlineEditText.addTextWatcher(this);
/*      */ 
/*      */     
/*  730 */     this.mInlineEditText.setTextSize((int)this.mTextSize);
/*      */     
/*  732 */     this.mInlineEditText.getEditText().setAutoScrollEditTextListener(new AutoScrollEditText.AutoScrollEditTextListener()
/*      */         {
/*      */           public boolean onKeyUp(int keyCode, KeyEvent event) {
/*  735 */             if (ShortcutHelper.isCommitText(keyCode, event)) {
/*      */ 
/*      */               
/*  738 */               FreeTextCreate.this.saveAndQuitInlineEditText(false);
/*      */               
/*  740 */               InputMethodManager imm = (InputMethodManager)FreeTextCreate.this.mPdfViewCtrl.getContext().getSystemService("input_method");
/*  741 */               if (imm != null) {
/*  742 */                 imm.hideSoftInputFromWindow(FreeTextCreate.this.mPdfViewCtrl.getRootView().getWindowToken(), 0);
/*      */               }
/*      */             } 
/*  745 */             return true;
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean onKeyPreIme(int keyCode, KeyEvent event) {
/*  750 */             return false;
/*      */           }
/*      */         });
/*      */ 
/*      */     
/*  755 */     int textColor = Utils.getPostProcessedColor(this.mPdfViewCtrl, this.mTextColor);
/*  756 */     int r = Color.red(textColor);
/*  757 */     int g = Color.green(textColor);
/*  758 */     int b = Color.blue(textColor);
/*  759 */     int opacity = (int)(this.mOpacity * 255.0F);
/*  760 */     int fontColor = Color.argb(opacity, r, g, b);
/*  761 */     this.mInlineEditText.setTextColor(fontColor);
/*      */ 
/*      */     
/*  764 */     this.mInlineEditText.setBackgroundColor(0);
/*      */ 
/*      */     
/*  767 */     if (interimText != null) {
/*  768 */       this.mInlineEditText.setContents(interimText);
/*      */     }
/*      */   }
/*      */   
/*      */   private void fallbackDialog(String interimText, boolean disableToggleButton) {
/*  773 */     boolean enableSave = true;
/*  774 */     if (interimText == null) {
/*  775 */       interimText = "";
/*  776 */       enableSave = false;
/*      */     } 
/*      */     
/*  779 */     this.mDialogFreeTextNote = new DialogFreeTextNote(this.mPdfViewCtrl, interimText, enableSave);
/*  780 */     this.mDialogFreeTextNote.addTextWatcher(this);
/*  781 */     this.mDialogFreeTextNote.setAnnotNoteListener(this);
/*  782 */     this.mDialogFreeTextNote.setOnDismissListener(new DialogInterface.OnDismissListener()
/*      */         {
/*      */           public void onDismiss(DialogInterface dialogInterface) {
/*  785 */             FreeTextCreate.this.prepareDialogFreeTextNoteDismiss();
/*      */           }
/*      */         });
/*      */     
/*  789 */     this.mDialogFreeTextNote.show();
/*  790 */     if (disableToggleButton) {
/*  791 */       this.mDialogFreeTextNote.disableToggleButton();
/*      */     }
/*      */   }
/*      */   
/*      */   private void prepareDialogFreeTextNoteDismiss() {
/*  796 */     if (this.mPdfViewCtrl == null || this.mDialogFreeTextNote == null) {
/*      */       return;
/*      */     }
/*      */     
/*  800 */     if (this.mAnnotButtonPressed == -1) {
/*  801 */       boolean shouldUnlock = false;
/*      */       try {
/*  803 */         this.mPdfViewCtrl.docLock(true);
/*  804 */         shouldUnlock = true;
/*  805 */         if (!TextUtils.isEmpty(this.mDialogFreeTextNote.getNote())) {
/*  806 */           createAnnot(this.mDialogFreeTextNote.getNote());
/*  807 */           raiseAnnotationAddedEvent(this.mAnnot, this.mAnnotPageNum);
/*      */         } 
/*  809 */         Utils.deleteCacheFile(this.mPdfViewCtrl.getContext(), this.mCacheFileName);
/*  810 */       } catch (Exception e) {
/*  811 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */       } finally {
/*  813 */         if (shouldUnlock) {
/*  814 */           this.mPdfViewCtrl.docUnlock();
/*      */         }
/*      */       } 
/*      */       
/*  818 */       if (this.mInlineEditText != null && this.mInlineEditText.isEditing().booleanValue()) {
/*  819 */         quitInlineEditText();
/*      */       } else {
/*      */         
/*  822 */         setNextToolMode();
/*      */       } 
/*      */ 
/*      */       
/*  826 */       if (this.mUpdateEditMode) {
/*  827 */         SharedPreferences settings = Tool.getToolPreferences(this.mPdfViewCtrl.getContext());
/*  828 */         SharedPreferences.Editor editor = settings.edit();
/*  829 */         editor.putInt("annotation_free_text_preference_editing", this.mCurrentEditMode);
/*  830 */         editor.apply();
/*      */       } 
/*  832 */     } else if (this.mAnnotButtonPressed == -3) {
/*  833 */       this.mCurrentEditMode = 1;
/*  834 */       this.mUpdateEditMode = true;
/*  835 */       if (this.mInlineEditText != null) {
/*  836 */         this.mInlineEditText.setContents(this.mDialogFreeTextNote.getNote());
/*      */ 
/*      */         
/*  839 */         Utils.showSoftKeyboard(this.mPdfViewCtrl.getContext(), (View)this.mInlineEditText.getEditText());
/*      */       } else {
/*  841 */         inlineTextEditing(this.mDialogFreeTextNote.getNote());
/*      */       } 
/*      */     } else {
/*  844 */       if (this.mInlineEditText != null && this.mInlineEditText.isEditing().booleanValue()) {
/*  845 */         quitInlineEditText();
/*      */       } else {
/*      */         
/*  848 */         setNextToolMode();
/*      */       } 
/*      */ 
/*      */       
/*  852 */       if (this.mUpdateEditMode) {
/*  853 */         SharedPreferences settings = Tool.getToolPreferences(this.mPdfViewCtrl.getContext());
/*  854 */         SharedPreferences.Editor editor = settings.edit();
/*  855 */         editor.putInt("annotation_free_text_preference_editing", this.mCurrentEditMode);
/*  856 */         editor.apply();
/*      */       } 
/*      */       
/*  859 */       Utils.deleteCacheFile(this.mPdfViewCtrl.getContext(), this.mCacheFileName);
/*      */     } 
/*  861 */     this.mAnnotButtonPressed = 0;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void createAnnot(String contents) throws PDFNetException, JSONException {
/*  866 */     Rect bbox = getTextBBoxOnPage(this.mPdfViewCtrl, this.mPageNum, this.mTargetPointPageSpace);
/*  867 */     if (bbox == null) {
/*      */       return;
/*      */     }
/*      */     
/*  871 */     FreeText freeText = FreeText.create((Doc)this.mPdfViewCtrl.getDoc(), bbox);
/*      */     
/*  873 */     freeText.setContents(contents);
/*  874 */     boolean isRightToLeft = Utils.isRightToLeftString(contents);
/*  875 */     if (isRightToLeft) {
/*  876 */       freeText.setQuaddingFormat(2);
/*      */     }
/*  878 */     freeText.setFontSize(this.mTextSize);
/*  879 */     if (this.mFillColor == 0) {
/*  880 */       freeText.setColor(new ColorPt(0.0D, 0.0D, 0.0D, 0.0D), 0);
/*      */     } else {
/*  882 */       ColorPt colorPt = Utils.color2ColorPt(this.mFillColor);
/*  883 */       freeText.setColor(colorPt, 3);
/*      */     } 
/*  885 */     freeText.setOpacity(this.mOpacity);
/*      */ 
/*      */     
/*  888 */     float thickness = this.mThickness;
/*      */     
/*  890 */     if (this.mStrokeColor == 0) {
/*  891 */       freeText.setLineColor(new ColorPt(0.0D, 0.0D, 0.0D, 0.0D), 0);
/*  892 */       thickness = 0.0F;
/*  893 */       freeText.getSDFObj().putNumber("pdftron_thickness", this.mThickness);
/*      */     } else {
/*  895 */       freeText.setLineColor(Utils.color2ColorPt(this.mStrokeColor), 3);
/*      */     } 
/*      */     
/*  898 */     Annot.BorderStyle border = freeText.getBorderStyle();
/*  899 */     border.setWidth(thickness);
/*  900 */     freeText.setBorderStyle(border);
/*  901 */     ColorPt color = Utils.color2ColorPt(this.mTextColor);
/*  902 */     freeText.setTextColor(color, 3);
/*  903 */     freeText.refreshAppearance();
/*  904 */     FreeTextInfo.setFont(this.mPdfViewCtrl, freeText, this.mPDFTronFontName);
/*      */     
/*  906 */     setAuthor((Markup)freeText);
/*      */     
/*  908 */     bbox = getFreeTextBBox(freeText, isRightToLeft);
/*  909 */     bbox.normalize();
/*      */ 
/*      */ 
/*      */     
/*  913 */     double padding = thickness * 1.5D;
/*      */ 
/*      */     
/*  916 */     bbox = new Rect(bbox.getX1(), bbox.getY1() - padding * 2.0D - thickness * 0.5D, bbox.getX2() + padding * 2.0D + thickness * 0.5D, bbox.getY2());
/*      */     
/*  918 */     freeText.resize(bbox);
/*      */     
/*  920 */     Page page = this.mPdfViewCtrl.getDoc().getPage(this.mPageNum);
/*  921 */     page.annotPushBack((Annot)freeText);
/*      */ 
/*      */ 
/*      */     
/*  925 */     int pageRotation = this.mPdfViewCtrl.getDoc().getPage(this.mPageNum).getRotation();
/*  926 */     int viewRotation = this.mPdfViewCtrl.getPageRotation();
/*  927 */     int annotRotation = (pageRotation + viewRotation) % 4 * 90;
/*  928 */     freeText.setRotation(annotRotation);
/*      */     
/*  930 */     setExtraFreeTextProps(freeText, bbox);
/*      */     
/*  932 */     freeText.refreshAppearance();
/*      */     
/*  934 */     setAnnot((Annot)freeText, this.mPageNum);
/*  935 */     buildAnnotBBox();
/*      */     
/*  937 */     this.mPdfViewCtrl.update(this.mAnnot, this.mPageNum);
/*      */   }
/*      */   
/*      */   protected Rect getFreeTextBBox(FreeText freeText, boolean isRightToLeft) throws PDFNetException {
/*  941 */     return calcFreeTextBBox(this.mPdfViewCtrl, freeText, this.mPageNum, isRightToLeft, this.mTargetPointPageSpace);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setExtraFreeTextProps(FreeText freetext, Rect bbox) throws PDFNetException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Rect calcFreeTextBBox(PDFViewCtrl pdfViewCtrl, FreeText freeText, int pageNum, boolean isRightToLeft, Point targetPoint) throws PDFNetException {
/*  953 */     Obj contentStream = freeText.getSDFObj().findObj("AP").findObj("N");
/*  954 */     ElementReader er = new ElementReader();
/*  955 */     Rect unionRect = null;
/*      */ 
/*      */     
/*  958 */     er.begin(contentStream);
/*  959 */     for (Element element = er.next(); element != null; element = er.next()) {
/*  960 */       Rect rect1 = element.getBBox();
/*  961 */       if (rect1 != null && element.getType() == 3) {
/*  962 */         if (unionRect == null) {
/*  963 */           unionRect = rect1;
/*      */         }
/*  965 */         unionRect = getRectUnion(rect1, unionRect);
/*      */       } 
/*      */     } 
/*  968 */     er.end();
/*  969 */     er.destroy();
/*      */ 
/*      */     
/*  972 */     int pageRotation = pdfViewCtrl.getDoc().getPage(pageNum).getRotation();
/*  973 */     int viewRotation = pdfViewCtrl.getPageRotation();
/*  974 */     int annotRotation = (pageRotation + viewRotation) % 4 * 90;
/*      */     
/*  976 */     double xDist = 0.0D;
/*  977 */     double yDist = 0.0D;
/*      */     
/*  979 */     if (unionRect != null) {
/*  980 */       unionRect.normalize();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  986 */       if (annotRotation == 90 || annotRotation == 270) {
/*  987 */         xDist = unionRect.getHeight();
/*  988 */         yDist = unionRect.getWidth();
/*      */       } else {
/*  990 */         xDist = unionRect.getWidth();
/*  991 */         yDist = unionRect.getHeight();
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  996 */     if (xDist == 0.0D || yDist == 0.0D) {
/*  997 */       xDist = 60.0D;
/*  998 */       yDist = 60.0D;
/*      */     } else {
/* 1000 */       xDist += 25.0D;
/* 1001 */       yDist += 5.0D;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1008 */     if (annotRotation == 90) {
/* 1009 */       if (pdfViewCtrl.getRightToLeftLanguage()) {
/* 1010 */         xDist *= -1.0D;
/*      */       } else {
/* 1012 */         yDist *= -1.0D;
/*      */       } 
/* 1014 */     } else if (annotRotation == 270) {
/* 1015 */       if (pdfViewCtrl.getRightToLeftLanguage()) {
/* 1016 */         yDist *= -1.0D;
/*      */       } else {
/* 1018 */         xDist *= -1.0D;
/*      */       } 
/* 1020 */     } else if (annotRotation == 180) {
/* 1021 */       xDist *= -1.0D;
/* 1022 */       yDist *= -1.0D;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1027 */     double left = targetPoint.x - (isRightToLeft ? xDist : 0.0D);
/* 1028 */     double top = targetPoint.y;
/* 1029 */     double right = targetPoint.x + (isRightToLeft ? 0.0D : xDist);
/* 1030 */     double bottom = targetPoint.y - yDist;
/*      */ 
/*      */     
/* 1033 */     Rect rect = new Rect(left, top, right, bottom);
/* 1034 */     rect.normalize();
/* 1035 */     left = rect.getX1();
/* 1036 */     top = rect.getY1();
/* 1037 */     right = rect.getX2();
/* 1038 */     bottom = rect.getY2();
/*      */ 
/*      */     
/* 1041 */     Page page = pdfViewCtrl.getDoc().getPage(pageNum);
/* 1042 */     Rect pageCropOnClientF = page.getBox(pdfViewCtrl.getPageBox());
/* 1043 */     pageCropOnClientF.normalize();
/*      */     
/* 1045 */     if (left < pageCropOnClientF.getX1()) {
/* 1046 */       left = pageCropOnClientF.getX1();
/*      */     }
/* 1048 */     if (top < pageCropOnClientF.getY1()) {
/* 1049 */       top = pageCropOnClientF.getY1();
/*      */     }
/* 1051 */     if (right > pageCropOnClientF.getX2()) {
/* 1052 */       right = pageCropOnClientF.getX2();
/*      */     }
/* 1054 */     if (bottom > pageCropOnClientF.getY2()) {
/* 1055 */       bottom = pageCropOnClientF.getY2();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1060 */     if (Math.abs(pageCropOnClientF.getY1() - top) < 3.0D) {
/* 1061 */       top = pageCropOnClientF.getY1() + 3.0D;
/*      */     }
/* 1063 */     if (Math.abs(pageCropOnClientF.getX2() - right) < 3.0D) {
/* 1064 */       right = pageCropOnClientF.getX2() - 3.0D;
/*      */     }
/*      */     
/* 1067 */     if (right - left < 50.0D) {
/* 1068 */       right = left + 50.0D;
/*      */     }
/* 1070 */     if (right > pageCropOnClientF.getX2()) {
/* 1071 */       right = pageCropOnClientF.getX2();
/* 1072 */       left = right - 50.0D;
/*      */     } 
/* 1074 */     return new Rect(left, top, right, bottom);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onRenderingFinished() {
/* 1082 */     super.onRenderingFinished();
/*      */     
/* 1084 */     if (this.mInlineEditText != null && this.mInlineEditText.delayViewRemoval()) {
/* 1085 */       this.mInlineEditText.removeView();
/* 1086 */       this.mInlineEditText = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onTextChanged(CharSequence s, int start, int before, int count) {
/* 1103 */     long currentTimeStamp = System.currentTimeMillis();
/* 1104 */     if (currentTimeStamp - this.mStoredTimeStamp > 3000L) {
/* 1105 */       this.mStoredTimeStamp = currentTimeStamp;
/* 1106 */       if (s != null && s.length() > 0) {
/* 1107 */         FreeTextCacheStruct freeTextCacheStruct = new FreeTextCacheStruct();
/* 1108 */         freeTextCacheStruct.contents = s.toString();
/* 1109 */         freeTextCacheStruct.pageNum = this.mPageNum;
/* 1110 */         freeTextCacheStruct.x = this.mStoredPointX;
/* 1111 */         freeTextCacheStruct.y = this.mStoredPointY;
/* 1112 */         AnnotUtils.saveFreeTextCache(freeTextCacheStruct, this.mPdfViewCtrl);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void afterTextChanged(Editable s) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void initTextStyle() {
/* 1126 */     Context context = this.mPdfViewCtrl.getContext();
/* 1127 */     SharedPreferences settings = Tool.getToolPreferences(context);
/* 1128 */     this.mTextColor = settings.getInt(getTextColorKey(getCreateAnnotType()), ToolStyleConfig.getInstance().getDefaultTextColor(context, getCreateAnnotType()));
/* 1129 */     this.mTextSize = settings.getFloat(getTextSizeKey(getCreateAnnotType()), ToolStyleConfig.getInstance().getDefaultTextSize(context, getCreateAnnotType()));
/* 1130 */     this.mStrokeColor = settings.getInt(getColorKey(getCreateAnnotType()), ToolStyleConfig.getInstance().getDefaultColor(context, getCreateAnnotType()));
/* 1131 */     this.mThickness = settings.getFloat(getThicknessKey(getCreateAnnotType()), ToolStyleConfig.getInstance().getDefaultThickness(context, getCreateAnnotType()));
/* 1132 */     this.mFillColor = settings.getInt(getColorFillKey(getCreateAnnotType()), ToolStyleConfig.getInstance().getDefaultFillColor(context, getCreateAnnotType()));
/* 1133 */     this.mOpacity = settings.getFloat(getOpacityKey(getCreateAnnotType()), ToolStyleConfig.getInstance().getDefaultOpacity(context, getCreateAnnotType()));
/* 1134 */     this.mPDFTronFontName = settings.getString(getFontKey(getCreateAnnotType()), ToolStyleConfig.getInstance().getDefaultFont(context, getCreateAnnotType()));
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
/*      */   public static void putDefaultRect(@NonNull FreeText freeText, @NonNull Rect defaultRect) throws PDFNetException {
/* 1150 */     freeText.setCustomData("pdftron_defaultX1", String.valueOf(defaultRect.getX1()));
/* 1151 */     freeText.setCustomData("pdftron_defaultY1", String.valueOf(defaultRect.getY1()));
/* 1152 */     freeText.setCustomData("pdftron_defaultX2", String.valueOf(defaultRect.getX2()));
/* 1153 */     freeText.setCustomData("pdftron_defaultY2", String.valueOf(defaultRect.getY2()));
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
/*      */   @Nullable
/*      */   public static Rect getDefaultRect(@NonNull FreeText freeText) throws PDFNetException {
/* 1168 */     String origX1Str = freeText.getCustomData("pdftron_defaultX1");
/* 1169 */     String origY1Str = freeText.getCustomData("pdftron_defaultY1");
/* 1170 */     String origX2Str = freeText.getCustomData("pdftron_defaultX2");
/* 1171 */     String origY2Str = freeText.getCustomData("pdftron_defaultY2");
/*      */     try {
/* 1173 */       double origX1 = Double.parseDouble(origX1Str);
/* 1174 */       double origY1 = Double.parseDouble(origY1Str);
/* 1175 */       double origX2 = Double.parseDouble(origX2Str);
/* 1176 */       double origY2 = Double.parseDouble(origY2Str);
/* 1177 */       return new Rect(origX1, origY1, origX2, origY2);
/* 1178 */     } catch (NullPointerException|NumberFormatException e) {
/* 1179 */       return null;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\FreeTextCreate.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */