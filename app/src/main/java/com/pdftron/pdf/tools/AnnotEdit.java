/*      */ package com.pdftron.pdf.tools;
/*      */ 
/*      */ import android.content.Context;
/*      */ import android.content.DialogInterface;
/*      */ import android.content.SharedPreferences;
/*      */ import android.content.res.Configuration;
/*      */ import android.graphics.Canvas;
/*      */ import android.graphics.Color;
/*      */ import android.graphics.DashPathEffect;
/*      */ import android.graphics.Matrix;
/*      */ import android.graphics.Paint;
/*      */ import android.graphics.PointF;
/*      */ import android.graphics.RectF;
/*      */ import android.os.Bundle;
/*      */ import android.os.Handler;
/*      */ import android.os.Parcelable;
/*      */ import android.text.Editable;
/*      */ import android.text.Html;
/*      */ import android.text.TextWatcher;
/*      */ import android.util.Log;
/*      */ import android.util.Pair;
/*      */ import android.view.KeyEvent;
/*      */ import android.view.MotionEvent;
/*      */ import android.view.ViewTreeObserver;
/*      */ import android.view.inputmethod.InputMethodManager;
/*      */ import android.widget.DatePicker;
/*      */ import android.widget.EditText;
/*      */ import android.widget.TimePicker;
/*      */ import androidx.annotation.ColorInt;
/*      */ import androidx.annotation.Keep;
/*      */ import androidx.annotation.MenuRes;
/*      */ import androidx.annotation.NonNull;
/*      */ import androidx.annotation.Nullable;
/*      */ import androidx.fragment.app.FragmentActivity;
/*      */ import androidx.lifecycle.LifecycleOwner;
/*      */ import androidx.lifecycle.Observer;
/*      */ import androidx.lifecycle.ViewModelProviders;
/*      */ import com.pdftron.common.PDFNetException;
/*      */ import com.pdftron.filters.Filter;
/*      */ import com.pdftron.pdf.Annot;
/*      */ import com.pdftron.pdf.ColorPt;
/*      */ import com.pdftron.pdf.Field;
/*      */ import com.pdftron.pdf.Font;
/*      */ import com.pdftron.pdf.PDFViewCtrl;
/*      */ import com.pdftron.pdf.Page;
/*      */ import com.pdftron.pdf.Point;
/*      */ import com.pdftron.pdf.QuadPoint;
/*      */ import com.pdftron.pdf.Rect;
/*      */ import com.pdftron.pdf.Redactor;
/*      */ import com.pdftron.pdf.annots.ComboBoxWidget;
/*      */ import com.pdftron.pdf.annots.FileAttachment;
/*      */ import com.pdftron.pdf.annots.FreeText;
/*      */ import com.pdftron.pdf.annots.Line;
/*      */ import com.pdftron.pdf.annots.Link;
/*      */ import com.pdftron.pdf.annots.ListBoxWidget;
/*      */ import com.pdftron.pdf.annots.Markup;
/*      */ import com.pdftron.pdf.annots.PolyLine;
/*      */ import com.pdftron.pdf.annots.Polygon;
/*      */ import com.pdftron.pdf.annots.Popup;
/*      */ import com.pdftron.pdf.annots.RadioButtonGroup;
/*      */ import com.pdftron.pdf.annots.RadioButtonWidget;
/*      */ import com.pdftron.pdf.annots.Redaction;
/*      */ import com.pdftron.pdf.annots.Sound;
/*      */ import com.pdftron.pdf.annots.Text;
/*      */ import com.pdftron.pdf.annots.Widget;
/*      */ import com.pdftron.pdf.controls.AnnotStyleDialogFragment;
/*      */ import com.pdftron.pdf.dialog.SimpleDateTimePickerFragment;
/*      */ import com.pdftron.pdf.dialog.SoundDialogFragment;
/*      */ import com.pdftron.pdf.dialog.measure.CalibrateDialog;
/*      */ import com.pdftron.pdf.dialog.measure.CalibrateResult;
/*      */ import com.pdftron.pdf.dialog.measure.CalibrateViewModel;
/*      */ import com.pdftron.pdf.model.AnnotStyle;
/*      */ import com.pdftron.pdf.model.FontResource;
/*      */ import com.pdftron.pdf.model.FreeTextCacheStruct;
/*      */ import com.pdftron.pdf.model.RotateInfo;
/*      */ import com.pdftron.pdf.model.RulerItem;
/*      */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*      */ import com.pdftron.pdf.utils.AnnotUtils;
/*      */ import com.pdftron.pdf.utils.AnnotationClipboardHelper;
/*      */ import com.pdftron.pdf.utils.CommonToast;
/*      */ import com.pdftron.pdf.utils.DrawingUtils;
/*      */ import com.pdftron.pdf.utils.Event;
/*      */ import com.pdftron.pdf.utils.InlineEditText;
/*      */ import com.pdftron.pdf.utils.MeasureUtils;
/*      */ import com.pdftron.pdf.utils.PdfViewCtrlSettingsManager;
/*      */ import com.pdftron.pdf.utils.PressureInkUtils;
/*      */ import com.pdftron.pdf.utils.ShortcutHelper;
/*      */ import com.pdftron.pdf.utils.Utils;
/*      */ import com.pdftron.pdf.viewmodel.RichTextViewModel;
/*      */ import com.pdftron.pdf.widget.AnnotView;
/*      */ import com.pdftron.pdf.widget.AutoScrollEditText;
/*      */ import com.pdftron.pdf.widget.RotateHandleView;
/*      */ import com.pdftron.pdf.widget.richtext.PTRichEditor;
/*      */ import com.pdftron.sdf.DictIterator;
/*      */ import com.pdftron.sdf.Doc;
/*      */ import com.pdftron.sdf.Obj;
/*      */ import io.reactivex.android.schedulers.AndroidSchedulers;
/*      */ import io.reactivex.disposables.CompositeDisposable;
/*      */ import io.reactivex.disposables.Disposable;
/*      */ import io.reactivex.functions.Consumer;
/*      */ import io.reactivex.schedulers.Schedulers;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Calendar;
/*      */ import java.util.Date;
/*      */ import java.util.Locale;
/*      */ import java.util.Set;
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
/*      */ public class AnnotEdit
/*      */   extends BaseTool
/*      */   implements DialogAnnotNote.DialogAnnotNoteListener, InlineEditText.InlineEditTextListener, TextWatcher, AnnotStyle.OnAnnotStyleChangeListener
/*      */ {
/*      */   public static final int RECTANGULAR_CTRL_PTS_CNT = 8;
/*  135 */   private static final String TAG = AnnotEdit.class.getName();
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean sDebug;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int e_unknown = -1;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int e_moving = -2;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int e_ll = 0;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int e_lr = 1;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int e_ur = 2;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int e_ul = 3;
/*      */ 
/*      */   
/*      */   public static final int e_mr = 4;
/*      */ 
/*      */   
/*      */   public static final int e_um = 5;
/*      */ 
/*      */   
/*      */   public static final int e_lm = 6;
/*      */ 
/*      */   
/*      */   public static final int e_ml = 7;
/*      */ 
/*      */   
/*      */   protected final DashPathEffect mDashPathEffect;
/*      */ 
/*      */   
/*  181 */   protected RectF mBBox = new RectF();
/*  182 */   protected RectF mBBoxOnDown = new RectF();
/*      */   protected RectF mContentBox;
/*      */   protected RectF mContentBoxOnDown;
/*      */   protected RectF mPageCropOnClientF;
/*  186 */   protected int mEffCtrlPtId = -1;
/*      */   protected boolean mModifiedAnnot;
/*      */   protected boolean mCtrlPtsSet;
/*      */   private boolean mAnnotIsSticky;
/*      */   private boolean mAnnotIsSound;
/*      */   private boolean mAnnotIsFileAttachment;
/*      */   protected boolean mAnnotIsTextMarkup;
/*      */   private boolean mAnnotIsFreeText;
/*      */   private boolean mAnnotIsStamper;
/*      */   private boolean mAnnotIsSignature;
/*      */   private boolean mAnnotIsLine;
/*      */   private boolean mAnnotIsMeasurement;
/*      */   private boolean mAnnotHasFont;
/*      */   protected boolean mScaled;
/*  200 */   protected Paint mPaint = new Paint();
/*      */   
/*      */   private boolean mUpFromStickyCreate;
/*      */   
/*      */   private boolean mUpFromFreeTextCreate;
/*      */   private boolean mUpFromStickyCreateDlgShown;
/*      */   protected boolean mMaintainAspectRatio;
/*      */   @Nullable
/*      */   private InlineEditText mInlineEditText;
/*      */   private boolean mTapToSaveFreeTextAnnot;
/*      */   private boolean mSaveFreeTextAnnotInOnUp;
/*      */   private boolean mHasOnCloseCalled;
/*      */   private boolean mIsScaleBegun;
/*      */   private boolean mStamperToolSelected;
/*  214 */   protected int CTRL_PTS_CNT = 8;
/*      */   
/*  216 */   protected PointF[] mCtrlPts = new PointF[this.CTRL_PTS_CNT];
/*  217 */   protected PointF[] mCtrlPtsOnDown = new PointF[this.CTRL_PTS_CNT];
/*      */   
/*  219 */   protected PointF[] mCtrlPtsInflated = new PointF[this.CTRL_PTS_CNT];
/*      */   
/*      */   protected float mCtrlRadius;
/*      */   
/*      */   protected boolean mHideCtrlPts;
/*      */   
/*      */   private int mAnnotButtonPressed;
/*      */   
/*      */   private int mCurrentFreeTextEditMode;
/*      */   
/*      */   private boolean mUpdateFreeTextEditMode;
/*      */   
/*      */   private boolean mInEditMode;
/*      */   
/*      */   private float mAspectRatio;
/*      */   
/*      */   private String mCacheFileName;
/*      */   private long mStoredTimeStamp;
/*      */   private DialogStickyNote mDialogStickyNote;
/*      */   private DialogAnnotNote mDialogAnnotNote;
/*      */   private AnnotStyleDialogFragment mAnnotStyleDialog;
/*      */   protected boolean mHandleEffCtrlPtsDisabled;
/*  241 */   private PointF mRotateDown = new PointF();
/*  242 */   private PointF mRotateMove = new PointF();
/*      */   private float mRotateDegree;
/*      */   private float mRotateStartDegree;
/*  245 */   private final float mRotateThreshold = 6.0F;
/*      */   
/*      */   private Integer mSnapDegree;
/*      */   
/*      */   private static final float sSnapAspectRatioThreshold = 0.1F;
/*      */   
/*      */   private static final int sSnapThresholdDP = 8;
/*      */   
/*      */   private final float mSnapThreshold;
/*      */   
/*      */   protected boolean mSnapEnabled = true;
/*      */   
/*      */   protected int mSelectionBoxMargin;
/*      */   
/*      */   @Nullable
/*      */   private RichTextViewModel mRichTextViewModel;
/*      */   
/*      */   public AnnotEdit(@NonNull PDFViewCtrl ctrl) {
/*  263 */     super(ctrl);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  268 */     this.mDashPathEffect = new DashPathEffect(new float[] { convDp2Pix(4.5F), convDp2Pix(2.5F) }, 0.0F);
/*      */     
/*  270 */     for (int i = 0; i < this.CTRL_PTS_CNT; i++) {
/*  271 */       this.mCtrlPts[i] = new PointF();
/*  272 */       this.mCtrlPtsOnDown[i] = new PointF();
/*  273 */       this.mCtrlPtsInflated[i] = new PointF();
/*      */     } 
/*      */     
/*  276 */     this.mPaint.setAntiAlias(true);
/*      */     
/*  278 */     this.mSelectionBoxMargin = (int)Utils.convDp2Pix(this.mPdfViewCtrl.getContext(), ((ToolManager)this.mPdfViewCtrl.getToolManager()).getSelectionBoxMargin());
/*  279 */     this.mCtrlRadius = this.mPdfViewCtrl.getContext().getResources().getDimensionPixelSize(R.dimen.selection_widget_size_w_margin) / 2.0F;
/*  280 */     this.mSnapThreshold = Utils.convDp2Pix(this.mPdfViewCtrl.getContext(), 8.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onCreate() {
/*  288 */     super.onCreate();
/*      */     
/*  290 */     if (this.mAnnot == null) {
/*      */       return;
/*      */     }
/*      */     
/*  294 */     boolean shouldUnlockRead = false;
/*      */ 
/*      */     
/*      */     try {
/*  298 */       this.mPdfViewCtrl.docLockRead();
/*  299 */       shouldUnlockRead = true;
/*      */       
/*  301 */       this.mHasSelectionPermission = hasPermission(this.mAnnot, 0);
/*  302 */       this.mHasMenuPermission = hasPermission(this.mAnnot, 1);
/*      */       
/*  304 */       int type = this.mAnnot.getType();
/*  305 */       this.mAnnotStyle = AnnotUtils.getAnnotStyle(this.mAnnot);
/*      */       
/*  307 */       this.mAnnotIsLine = (type == 3);
/*  308 */       this.mAnnotIsSticky = (type == 0);
/*  309 */       this.mAnnotIsSound = (type == 17);
/*  310 */       this.mAnnotIsFileAttachment = (type == 16);
/*  311 */       this.mAnnotIsFreeText = (type == 2);
/*  312 */       this.mAnnotIsTextMarkup = (type == 8 || type == 9 || type == 11 || type == 10);
/*      */ 
/*      */ 
/*      */       
/*  316 */       this.mAnnotHasFont = this.mAnnotStyle.hasFont();
/*      */       
/*  318 */       if (type == 25)
/*      */       {
/*  320 */         this.mHasSelectionPermission = false;
/*      */       }
/*  322 */       this.mAnnotIsMeasurement = this.mAnnotStyle.isMeasurement();
/*      */ 
/*      */       
/*  325 */       if (this.mAnnot.isMarkup() && type == 12) {
/*  326 */         Obj sigObj = this.mAnnot.getSDFObj();
/*  327 */         sigObj = sigObj.findObj(Signature.SIGNATURE_ANNOTATION_ID);
/*  328 */         this.mMaintainAspectRatio = true;
/*  329 */         if (sigObj != null) {
/*  330 */           this.mAnnotIsSignature = true;
/*      */         } else {
/*  332 */           this.mAnnotIsStamper = true;
/*  333 */           this.mRotateStartDegree = AnnotUtils.getStampDegree(this.mAnnot);
/*      */         } 
/*  335 */         this.mStamperToolSelected = true;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  340 */       this.mPageCropOnClientF = Utils.buildPageBoundBoxOnClient(this.mPdfViewCtrl, this.mAnnotPageNum);
/*      */       
/*  342 */       if (!isAnnotResizable() || this.mAnnotIsMeasurement)
/*      */       {
/*  344 */         this.mSelectionBoxMargin = 0;
/*      */       }
/*  346 */     } catch (Exception e) {
/*  347 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } finally {
/*  349 */       if (shouldUnlockRead) {
/*  350 */         this.mPdfViewCtrl.docUnlockRead();
/*      */       }
/*      */     } 
/*      */     
/*  354 */     ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/*  355 */     if (this.mAnnotIsMeasurement) {
/*  356 */       setSnappingEnabled(toolManager.isSnappingEnabledForMeasurementTools());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ToolManager.ToolModeBase getToolMode() {
/*  365 */     return ToolManager.ToolMode.ANNOT_EDIT;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getCreateAnnotType() {
/*  370 */     return 28;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setUpFromStickyCreate(boolean flag) {
/*  375 */     this.mUpFromStickyCreate = flag;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setUpFromFreeTextCreate(boolean flag) {
/*  380 */     this.mUpFromFreeTextCreate = flag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEditAnnotTool() {
/*  390 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @MenuRes
/*      */   protected int getMenuResByAnnot(@Nullable Annot annot) throws PDFNetException {
/*  401 */     if (annot == null) {
/*  402 */       return R.menu.annot_general;
/*      */     }
/*      */     
/*  405 */     int type = 28;
/*  406 */     int fieldType = 6;
/*      */     
/*  408 */     boolean shouldUnlockRead = false;
/*      */ 
/*      */     
/*      */     try {
/*  412 */       this.mPdfViewCtrl.docLockRead();
/*  413 */       shouldUnlockRead = true;
/*      */       
/*  415 */       type = annot.getType();
/*      */       
/*  417 */       if (19 == type) {
/*  418 */         Widget widget = new Widget(annot);
/*  419 */         Field field = widget.getField();
/*  420 */         fieldType = field.getType();
/*      */       } 
/*  422 */     } catch (Exception e) {
/*  423 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } finally {
/*  425 */       if (shouldUnlockRead) {
/*  426 */         this.mPdfViewCtrl.docUnlockRead();
/*      */       }
/*      */     } 
/*      */     
/*  430 */     switch (type) {
/*      */       case 17:
/*  432 */         return R.menu.annot_edit_sound;
/*      */       case 4:
/*  434 */         return R.menu.annot_rect_create;
/*      */       case 0:
/*      */       case 3:
/*      */       case 5:
/*      */       case 6:
/*      */       case 7:
/*  440 */         if (this.mAnnotIsMeasurement && type == 3) {
/*  441 */           return R.menu.annot_ruler;
/*      */         }
/*  443 */         return R.menu.annot_simple_shape;
/*      */       case 8:
/*      */       case 9:
/*      */       case 10:
/*      */       case 11:
/*  448 */         return R.menu.annot_edit_text_markup;
/*      */       case 25:
/*  450 */         return R.menu.annot_edit_text_redaction;
/*      */       case 2:
/*  452 */         return R.menu.annot_free_text;
/*      */       case 1:
/*  454 */         return R.menu.annot_link;
/*      */       case 12:
/*  456 */         if (this.mAnnotIsSignature) {
/*  457 */           return R.menu.annot_signature;
/*      */         }
/*  459 */         if (this.mAnnotIsStamper) {
/*  460 */           return R.menu.annot_stamper;
/*      */         }
/*      */       case 16:
/*  463 */         return R.menu.annot_file_attachment;
/*      */       case 14:
/*  465 */         if (AnnotUtils.isFreeHighlighter(annot)) {
/*  466 */           return R.menu.annot_simple_shape;
/*      */         }
/*  468 */         return R.menu.annot_free_hand;
/*      */       case 19:
/*  470 */         if (fieldType == 2)
/*  471 */           return R.menu.annot_radio_field; 
/*  472 */         if (fieldType == 3)
/*  473 */           return R.menu.annot_text_field; 
/*  474 */         if (fieldType == 4)
/*  475 */           return R.menu.annot_choice_field; 
/*      */         break;
/*      */     } 
/*  478 */     return R.menu.annot_general;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected QuickMenu createQuickMenu() {
/*  487 */     QuickMenu quickMenu = super.createQuickMenu();
/*      */     try {
/*  489 */       quickMenu.inflate(getMenuResByAnnot(this.mAnnot));
/*  490 */       customizeQuickMenuItems(quickMenu);
/*  491 */     } catch (PDFNetException e) {
/*  492 */       AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*      */     } 
/*      */     
/*  495 */     quickMenu.initMenuEntries();
/*  496 */     return quickMenu;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean showMenu(RectF anchor_rect) {
/*  501 */     if (anchor_rect != null && this.mHasSelectionPermission)
/*      */     {
/*  503 */       anchor_rect.set(anchor_rect.left - this.mSelectionBoxMargin, anchor_rect.top - this.mSelectionBoxMargin, anchor_rect.right + this.mSelectionBoxMargin, anchor_rect.bottom + this.mSelectionBoxMargin);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  508 */     return super.showMenu(anchor_rect);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void selectAnnot(Annot annot, int pageNum) {
/*  516 */     super.selectAnnot(annot, pageNum);
/*      */     
/*  518 */     this.mNextToolMode = getToolMode();
/*  519 */     Pair<ToolManager.ToolMode, ArrayList<Annot>> pair = canSelectGroupAnnot(this.mPdfViewCtrl, annot, pageNum);
/*  520 */     if (null == pair) {
/*  521 */       if (this.mAnnotStyle != null && this.mAnnotStyle.isSpacingFreeText()) {
/*  522 */         setCtrlPts();
/*      */       } else {
/*  524 */         setCtrlPts(false);
/*      */       } 
/*  526 */       this.mPdfViewCtrl.invalidate((int)Math.floor(this.mBBox.left), (int)Math.floor(this.mBBox.top), (int)Math.ceil(this.mBBox.right), (int)Math.ceil(this.mBBox.bottom));
/*  527 */       showMenu(getAnnotRect());
/*      */     } else {
/*  529 */       this.mAnnot = null;
/*  530 */       this.mGroupAnnots = (ArrayList<Annot>)pair.second;
/*      */     } 
/*      */   }
/*      */   
/*      */   protected Rect getAnnotScreenBBox() throws PDFNetException {
/*  535 */     if (this.mAnnot == null) {
/*  536 */       return null;
/*      */     }
/*  538 */     return this.mPdfViewCtrl.getScreenRectForAnnot(this.mAnnot, this.mAnnotPageNum);
/*      */   }
/*      */   
/*      */   protected Rect getAnnotScreenContentBox() throws PDFNetException {
/*  542 */     if (this.mAnnot == null) {
/*  543 */       return null;
/*      */     }
/*  545 */     if (AnnotUtils.isCallout(this.mAnnot)) {
/*  546 */       FreeText freeText = new FreeText(this.mAnnot);
/*  547 */       Rect contentRect = freeText.getContentRect();
/*  548 */       double[] pts1 = this.mPdfViewCtrl.convPagePtToScreenPt(contentRect.getX1(), contentRect.getY1(), this.mAnnotPageNum);
/*  549 */       double[] pts2 = this.mPdfViewCtrl.convPagePtToScreenPt(contentRect.getX2(), contentRect.getY2(), this.mAnnotPageNum);
/*  550 */       double x1 = pts1[0];
/*  551 */       double y1 = pts1[1];
/*  552 */       double x2 = pts2[0];
/*  553 */       double y2 = pts2[1];
/*  554 */       return new Rect(x1, y1, x2, y2);
/*      */     } 
/*  556 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected RectF getScreenRect(Rect screen_rect) {
/*  563 */     if (screen_rect == null) {
/*  564 */       return null;
/*      */     }
/*      */     
/*  567 */     float x1 = 0.0F, x2 = 0.0F, y1 = 0.0F, y2 = 0.0F;
/*      */     try {
/*  569 */       x1 = (float)screen_rect.getX1();
/*  570 */       y1 = (float)screen_rect.getY1();
/*  571 */       x2 = (float)screen_rect.getX2();
/*  572 */       y2 = (float)screen_rect.getY2();
/*  573 */     } catch (PDFNetException e) {
/*  574 */       AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*      */     } 
/*      */     
/*  577 */     float sx = this.mPdfViewCtrl.getScrollX();
/*  578 */     float sy = this.mPdfViewCtrl.getScrollY();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  588 */     float max_x = x1 + sx, min_x = max_x;
/*  589 */     float max_y = y2 + sy, min_y = max_y;
/*      */ 
/*      */     
/*  592 */     float x = (x1 + x2) / 2.0F + sx;
/*  593 */     float y = y2 + sy;
/*  594 */     min_x = Math.min(x, min_x);
/*  595 */     max_x = Math.max(x, max_x);
/*  596 */     min_y = Math.min(y, min_y);
/*  597 */     max_y = Math.max(y, max_y);
/*      */ 
/*      */     
/*  600 */     x = x2 + sx;
/*  601 */     y = y2 + sy;
/*  602 */     min_x = Math.min(x, min_x);
/*  603 */     max_x = Math.max(x, max_x);
/*  604 */     min_y = Math.min(y, min_y);
/*  605 */     max_y = Math.max(y, max_y);
/*      */ 
/*      */     
/*  608 */     x = x2 + sx;
/*  609 */     y = (y1 + y2) / 2.0F + sy;
/*  610 */     min_x = Math.min(x, min_x);
/*  611 */     max_x = Math.max(x, max_x);
/*  612 */     min_y = Math.min(y, min_y);
/*  613 */     max_y = Math.max(y, max_y);
/*      */ 
/*      */     
/*  616 */     x = x2 + sx;
/*  617 */     y = y1 + sy;
/*  618 */     min_x = Math.min(x, min_x);
/*  619 */     max_x = Math.max(x, max_x);
/*  620 */     min_y = Math.min(y, min_y);
/*  621 */     max_y = Math.max(y, max_y);
/*      */ 
/*      */     
/*  624 */     x = (x1 + x2) / 2.0F + sx;
/*  625 */     y = y1 + sy;
/*  626 */     min_x = Math.min(x, min_x);
/*  627 */     max_x = Math.max(x, max_x);
/*  628 */     min_y = Math.min(y, min_y);
/*  629 */     max_y = Math.max(y, max_y);
/*      */ 
/*      */     
/*  632 */     x = x1 + sx;
/*  633 */     y = y1 + sy;
/*  634 */     min_x = Math.min(x, min_x);
/*  635 */     max_x = Math.max(x, max_x);
/*  636 */     min_y = Math.min(y, min_y);
/*  637 */     max_y = Math.max(y, max_y);
/*      */ 
/*      */     
/*  640 */     x = x1 + sx;
/*  641 */     y = (y1 + y2) / 2.0F + sy;
/*  642 */     min_x = Math.min(x, min_x);
/*  643 */     max_x = Math.max(x, max_x);
/*  644 */     min_y = Math.min(y, min_y);
/*  645 */     max_y = Math.max(y, max_y);
/*  646 */     return new RectF(min_x, min_y, max_x, max_y);
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean canAddAnnotView(Annot annot, AnnotStyle annotStyle) {
/*  651 */     if (!((ToolManager)this.mPdfViewCtrl.getToolManager()).isRealTimeAnnotEdit()) {
/*  652 */       return false;
/*      */     }
/*  654 */     return (this.mPdfViewCtrl.isAnnotationLayerEnabled() || !annotStyle.hasAppearance());
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean canAddRotateView(Annot annot) {
/*  659 */     if (!this.mHasSelectionPermission) {
/*  660 */       return false;
/*      */     }
/*  662 */     return this.mAnnotIsStamper;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setCtrlPts() {
/*  670 */     setCtrlPts(true);
/*      */   }
/*      */   
/*      */   protected void setCtrlPts(boolean resetAnnotView) {
/*  674 */     if (onInterceptAnnotationHandling(this.mAnnot)) {
/*      */       return;
/*      */     }
/*      */     
/*  678 */     RectF screenRect = null;
/*  679 */     RectF contentRect = null;
/*      */     try {
/*  681 */       screenRect = getScreenRect(getAnnotScreenBBox());
/*  682 */       contentRect = getScreenRect(getAnnotScreenContentBox());
/*  683 */     } catch (PDFNetException pDFNetException) {}
/*      */     
/*  685 */     if (screenRect == null) {
/*      */       return;
/*      */     }
/*      */     
/*  689 */     this.mCtrlPtsSet = true;
/*  690 */     float min_x = screenRect.left;
/*  691 */     float min_y = screenRect.top;
/*  692 */     float max_x = screenRect.right;
/*  693 */     float max_y = screenRect.bottom;
/*      */     
/*  695 */     this.mBBox.left = min_x - this.mCtrlRadius;
/*  696 */     this.mBBox.top = min_y - this.mCtrlRadius;
/*  697 */     this.mBBox.right = max_x + this.mCtrlRadius;
/*  698 */     this.mBBox.bottom = max_y + this.mCtrlRadius;
/*      */     
/*  700 */     if (contentRect != null) {
/*  701 */       min_x = contentRect.left;
/*  702 */       min_y = contentRect.top;
/*  703 */       max_x = contentRect.right;
/*  704 */       max_y = contentRect.bottom;
/*      */       
/*  706 */       if (this.mContentBox == null) {
/*  707 */         this.mContentBox = new RectF();
/*      */       }
/*  709 */       this.mContentBox.left = min_x - this.mCtrlRadius;
/*  710 */       this.mContentBox.top = min_y - this.mCtrlRadius;
/*  711 */       this.mContentBox.right = max_x + this.mCtrlRadius;
/*  712 */       this.mContentBox.bottom = max_y + this.mCtrlRadius;
/*      */     } 
/*      */     
/*  715 */     if (resetAnnotView || null == this.mAnnotView) {
/*  716 */       addAnnotView();
/*  717 */       if (this.mAnnotStyle != null && this.mAnnotStyle.isSpacingFreeText()) {
/*  718 */         InlineEditText textView = new InlineEditText(this.mPdfViewCtrl, this.mAnnot, this.mAnnotPageNum, null, false, false, false, this);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  728 */         textView.getEditText().setAutoScrollEditTextSpacingListener(new AutoScrollEditText.AutoScrollEditTextSpacingListener()
/*      */             {
/*      */               public void onUp() {
/*  731 */                 AnnotEdit.this.editTextSpacing();
/*      */               }
/*      */             });
/*  734 */         this.mAnnotView.setInlineEditText(textView);
/*      */       } 
/*      */     } else {
/*  737 */       updateAnnotViewBitmap();
/*      */     } 
/*  739 */     if (this.mAnnotView != null) {
/*  740 */       if (this.mAnnotView.getDrawingView() != null) {
/*  741 */         int xOffset = this.mPdfViewCtrl.getScrollX();
/*  742 */         int yOffset = this.mPdfViewCtrl.getScrollY();
/*  743 */         boolean shouldUnlockRead = false;
/*      */         
/*  745 */         try { this.mPdfViewCtrl.docLockRead();
/*  746 */           shouldUnlockRead = true;
/*      */           
/*  748 */           this.mAnnotView.getDrawingView().initInkItem(this.mAnnot, this.mAnnotPageNum, new PointF(xOffset, yOffset));
/*      */ 
/*      */           
/*  751 */           if (AnnotUtils.isRectAreaMeasure(this.mAnnot)) {
/*      */ 
/*      */             
/*  754 */             Polygon poly = new Polygon(this.mAnnot);
/*  755 */             PointF[] pts = Utils.getVerticesFromPoly(this.mPdfViewCtrl, poly, this.mAnnotPageNum);
/*  756 */             this.mAnnotView.setVertices(pts);
/*      */           }  }
/*  758 */         catch (Exception exception) {  }
/*      */         finally
/*  760 */         { if (shouldUnlockRead) {
/*  761 */             this.mPdfViewCtrl.docUnlockRead();
/*      */           } }
/*      */       
/*      */       } 
/*  765 */       updateAnnotView(min_x, min_y, max_x, max_y);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  770 */     addRotateHandle();
/*  771 */     if (this.mRotateHandle != null) {
/*  772 */       final int[] viewOrigin = new int[2];
/*  773 */       this.mPdfViewCtrl.getLocationInWindow(viewOrigin);
/*      */       
/*  775 */       this.mRotateHandle.setListener(new RotateHandleView.RotateHandleViewListener()
/*      */           {
/*      */             public void onDown(float rawX, float rawY)
/*      */             {
/*  779 */               AnnotEdit.this.closeQuickMenu();
/*      */               
/*  781 */               AnnotEdit.this.mRotateDown.set(rawX - viewOrigin[0], rawY - viewOrigin[1]);
/*      */             }
/*      */ 
/*      */             
/*      */             public void onMove(float rawX, float rawY) {
/*  786 */               AnnotEdit.this.mRotateMove.set(rawX - viewOrigin[0], rawY - viewOrigin[1]);
/*      */               
/*  788 */               if (AnnotEdit.this.mAnnotView != null && AnnotEdit.this.mAnnotView.getDrawingView() != null) {
/*  789 */                 RotateInfo info = AnnotEdit.this.mAnnotView.handleRotation(AnnotEdit.this.mRotateDown, AnnotEdit.this.mRotateMove, false);
/*  790 */                 AnnotEdit.this.mRotateDegree = info.getDegree();
/*      */                 
/*  792 */                 boolean canSnap = false;
/*  793 */                 float totalDegree = AnnotEdit.this.mRotateDegree + AnnotEdit.this.mRotateStartDegree;
/*  794 */                 totalDegree %= 360.0F;
/*  795 */                 if (totalDegree > 270.0F) {
/*  796 */                   totalDegree -= 360.0F;
/*      */                 }
/*  798 */                 if (AnnotEdit.sDebug) Log.d(AnnotEdit.TAG, "totalDegree: " + totalDegree); 
/*  799 */                 if (totalDegree > 0.0F) {
/*  800 */                   if (Math.abs(totalDegree - 45.0F) < 6.0F) {
/*  801 */                     AnnotEdit.this.mSnapDegree = Integer.valueOf(45);
/*  802 */                     canSnap = true;
/*  803 */                   } else if (Math.abs(totalDegree - 90.0F) < 6.0F) {
/*  804 */                     AnnotEdit.this.mSnapDegree = Integer.valueOf(90);
/*  805 */                     canSnap = true;
/*  806 */                   } else if (Math.abs(totalDegree - 135.0F) < 6.0F) {
/*  807 */                     AnnotEdit.this.mSnapDegree = Integer.valueOf(135);
/*  808 */                     canSnap = true;
/*  809 */                   } else if (Math.abs(totalDegree - 180.0F) < 6.0F) {
/*  810 */                     AnnotEdit.this.mSnapDegree = Integer.valueOf(180);
/*  811 */                     canSnap = true;
/*  812 */                   } else if (Math.abs(totalDegree - 225.0F) < 6.0F) {
/*  813 */                     AnnotEdit.this.mSnapDegree = Integer.valueOf(225);
/*  814 */                     canSnap = true;
/*  815 */                   } else if (totalDegree < 6.0F) {
/*  816 */                     AnnotEdit.this.mSnapDegree = Integer.valueOf(0);
/*  817 */                     canSnap = true;
/*      */                   }
/*      */                 
/*  820 */                 } else if (Math.abs(Math.abs(totalDegree) - 45.0F) < 6.0F) {
/*  821 */                   AnnotEdit.this.mSnapDegree = Integer.valueOf(-45);
/*  822 */                   canSnap = true;
/*  823 */                 } else if (Math.abs(Math.abs(totalDegree) - 90.0F) < 6.0F) {
/*  824 */                   AnnotEdit.this.mSnapDegree = Integer.valueOf(-90);
/*  825 */                   canSnap = true;
/*  826 */                 } else if (Math.abs(totalDegree) < 6.0F) {
/*  827 */                   AnnotEdit.this.mSnapDegree = Integer.valueOf(0);
/*  828 */                   canSnap = true;
/*      */                 } 
/*      */                 
/*  831 */                 if (!canSnap) {
/*  832 */                   AnnotEdit.this.mSnapDegree = null;
/*      */                 }
/*  834 */                 if (AnnotEdit.this.mAnnotView != null && AnnotEdit.this.mAnnotView.getDrawingView() != null) {
/*  835 */                   AnnotEdit.this.mAnnotView.getDrawingView().snapToDegree(AnnotEdit.this.mSnapDegree, AnnotEdit.this.mRotateStartDegree);
/*      */                 }
/*  837 */                 AnnotEdit.this.mPdfViewCtrl.invalidate();
/*  838 */                 if (AnnotEdit.sDebug) Log.d(AnnotEdit.TAG, "mSnapDegree: " + AnnotEdit.this.mSnapDegree);
/*      */               
/*      */               } 
/*      */             }
/*      */             
/*      */             public void onUp(float rawX, float rawY, float x, float y) {
/*  844 */               AnnotEdit.this.mRotateMove.set(rawX - viewOrigin[0], rawY - viewOrigin[1]);
/*      */               
/*  846 */               if (AnnotEdit.this.mAnnotView != null && AnnotEdit.this.mAnnotView.getDrawingView() != null) {
/*  847 */                 RotateInfo info = AnnotEdit.this.mAnnotView.handleRotation(AnnotEdit.this.mRotateDown, AnnotEdit.this.mRotateMove, true);
/*  848 */                 AnnotEdit.this.mRotateDegree = info.getDegree();
/*      */               } 
/*      */               
/*  851 */               int degree = (int)(AnnotEdit.this.mRotateDegree + 0.5D);
/*  852 */               if (AnnotEdit.this.mSnapDegree != null) {
/*  853 */                 degree = AnnotEdit.this.mSnapDegree.intValue();
/*      */               }
/*  855 */               AnnotEdit.this.rotateStampAnnot(degree, (AnnotEdit.this.mSnapDegree != null));
/*      */               
/*  857 */               if (AnnotEdit.this.mRotateHandle != null) {
/*  858 */                 int l = (int)((AnnotEdit.this.mRotateMove.x - x) + 0.5D);
/*  859 */                 int t = (int)((AnnotEdit.this.mRotateMove.y - y) + 0.5D);
/*      */                 
/*  861 */                 int xOffset = AnnotEdit.this.mPdfViewCtrl.getScrollX();
/*  862 */                 int yOffset = AnnotEdit.this.mPdfViewCtrl.getScrollY();
/*      */                 
/*  864 */                 AnnotEdit.this.updateRotateView(xOffset + l, yOffset + t);
/*      */               } 
/*      */               
/*  867 */               AnnotEdit.this.mSnapDegree = null;
/*  868 */               if (AnnotEdit.this.mAnnotView != null && AnnotEdit.this.mAnnotView.getDrawingView() != null) {
/*  869 */                 AnnotEdit.this.mAnnotView.getDrawingView().snapToDegree(AnnotEdit.this.mSnapDegree, AnnotEdit.this.mRotateStartDegree);
/*      */               }
/*  871 */               AnnotEdit.this.mPdfViewCtrl.invalidate();
/*      */             }
/*      */           });
/*  874 */       updateRotateView(min_x, min_y, max_x, max_y);
/*      */     } 
/*      */ 
/*      */     
/*  878 */     float height = max_y - min_y;
/*  879 */     float width = max_x - min_x;
/*  880 */     this.mAspectRatio = height / width;
/*      */     
/*  882 */     if (!this.mHandleEffCtrlPtsDisabled) {
/*  883 */       (this.mCtrlPts[0]).x = min_x;
/*  884 */       (this.mCtrlPts[0]).y = max_y;
/*      */       
/*  886 */       (this.mCtrlPts[6]).x = (min_x + max_x) / 2.0F;
/*  887 */       (this.mCtrlPts[6]).y = max_y;
/*      */       
/*  889 */       (this.mCtrlPts[1]).x = max_x;
/*  890 */       (this.mCtrlPts[1]).y = max_y;
/*      */       
/*  892 */       (this.mCtrlPts[4]).x = max_x;
/*  893 */       (this.mCtrlPts[4]).y = (min_y + max_y) / 2.0F;
/*      */       
/*  895 */       (this.mCtrlPts[2]).x = max_x;
/*  896 */       (this.mCtrlPts[2]).y = min_y;
/*      */       
/*  898 */       (this.mCtrlPts[5]).x = (min_x + max_x) / 2.0F;
/*  899 */       (this.mCtrlPts[5]).y = min_y;
/*      */       
/*  901 */       (this.mCtrlPts[3]).x = min_x;
/*  902 */       (this.mCtrlPts[3]).y = min_y;
/*      */       
/*  904 */       (this.mCtrlPts[7]).x = min_x;
/*  905 */       (this.mCtrlPts[7]).y = (min_y + max_y) / 2.0F;
/*      */ 
/*      */       
/*  908 */       (this.mCtrlPts[0]).x -= this.mSelectionBoxMargin;
/*  909 */       (this.mCtrlPts[0]).y += this.mSelectionBoxMargin;
/*      */       
/*  911 */       (this.mCtrlPtsInflated[6]).x = (this.mCtrlPts[6]).x;
/*  912 */       (this.mCtrlPts[6]).y += this.mSelectionBoxMargin;
/*      */       
/*  914 */       (this.mCtrlPts[1]).x += this.mSelectionBoxMargin;
/*  915 */       (this.mCtrlPts[1]).y += this.mSelectionBoxMargin;
/*      */       
/*  917 */       (this.mCtrlPts[4]).x += this.mSelectionBoxMargin;
/*  918 */       (this.mCtrlPtsInflated[4]).y = (this.mCtrlPts[4]).y;
/*      */       
/*  920 */       (this.mCtrlPts[2]).x += this.mSelectionBoxMargin;
/*  921 */       (this.mCtrlPts[2]).y -= this.mSelectionBoxMargin;
/*      */       
/*  923 */       (this.mCtrlPtsInflated[5]).x = (this.mCtrlPts[5]).x;
/*  924 */       (this.mCtrlPts[5]).y -= this.mSelectionBoxMargin;
/*      */       
/*  926 */       (this.mCtrlPts[3]).x -= this.mSelectionBoxMargin;
/*  927 */       (this.mCtrlPts[3]).y -= this.mSelectionBoxMargin;
/*      */       
/*  929 */       (this.mCtrlPts[7]).x -= this.mSelectionBoxMargin;
/*  930 */       (this.mCtrlPtsInflated[7]).y = (this.mCtrlPts[7]).y;
/*      */       
/*  932 */       updateAnnotViewCtrlPt();
/*      */     } 
/*      */   }
/*      */   
/*      */   protected boolean isAnnotResizable() {
/*  937 */     if (this.mAnnotStyle != null && (this.mAnnotStyle
/*  938 */       .isRCFreeText() || this.mAnnotStyle.isSpacingFreeText())) {
/*  939 */       return false;
/*      */     }
/*  941 */     return (!this.mAnnotIsSticky && !this.mAnnotIsTextMarkup && !this.mAnnotIsSound && !this.mAnnotIsFileAttachment);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDraw(Canvas canvas, Matrix tfm) {
/*  949 */     super.onDraw(canvas, tfm);
/*      */     
/*  951 */     if (!hasAnnotSelected()) {
/*      */       return;
/*      */     }
/*      */     
/*  955 */     if (this.mHideCtrlPts) {
/*      */       return;
/*      */     }
/*      */     
/*  959 */     drawLoupe();
/*      */     
/*  961 */     if (this.mAnnotIsLine) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  966 */     if (this.mAnnotStyle != null && this.mAnnotStyle.isSpacingFreeText()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  971 */     float left = this.mBBox.left + this.mCtrlRadius;
/*  972 */     float right = this.mBBox.right - this.mCtrlRadius;
/*  973 */     float top = this.mBBox.top + this.mCtrlRadius;
/*  974 */     float bottom = this.mBBox.bottom - this.mCtrlRadius;
/*  975 */     if (this.mContentBox != null) {
/*  976 */       left = this.mContentBox.left + this.mCtrlRadius;
/*  977 */       right = this.mContentBox.right - this.mCtrlRadius;
/*  978 */       top = this.mContentBox.top + this.mCtrlRadius;
/*  979 */       bottom = this.mContentBox.bottom - this.mCtrlRadius;
/*      */     } 
/*      */     
/*  982 */     if (right - left <= 0.0F && bottom - top <= 0.0F) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  987 */     if (!this.mHasSelectionPermission || this.mSelectionBoxMargin == 0) {
/*  988 */       drawSelectionBox(canvas, left, top, right, bottom);
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
/*      */   public boolean onSingleTapConfirmed(MotionEvent e) {
/* 1002 */     super.onSingleTapConfirmed(e);
/*      */     
/* 1004 */     if (this.mAnnot == null) {
/* 1005 */       this.mNextToolMode = this.mCurrentDefaultToolMode;
/* 1006 */       return false;
/*      */     } 
/*      */     
/* 1009 */     int x = (int)(e.getX() + 0.5D);
/* 1010 */     int y = (int)(e.getY() + 0.5D);
/* 1011 */     Annot tempAnnot = this.mPdfViewCtrl.getAnnotationAt(x, y);
/* 1012 */     if (this.mAnnot.equals(tempAnnot) || this.mUpFromStickyCreate || this.mUpFromFreeTextCreate) {
/*      */ 
/*      */       
/* 1015 */       this.mNextToolMode = getToolMode();
/* 1016 */       setCtrlPts();
/* 1017 */       this.mPdfViewCtrl.invalidate((int)Math.floor(this.mBBox.left), (int)Math.floor(this.mBBox.top), (int)Math.ceil(this.mBBox.right), (int)Math.ceil(this.mBBox.bottom));
/*      */       
/* 1019 */       if (this.mAnnotIsSticky) {
/* 1020 */         handleStickyNote(this.mForceSameNextToolMode, this.mUpFromStickyCreate);
/* 1021 */       } else if (!this.mUpFromStickyCreate && !this.mUpFromFreeTextCreate && (
/* 1022 */         this.mInlineEditText == null || !this.mInlineEditText.isEditing().booleanValue())) {
/*      */         
/* 1024 */         if (this.mAnnotIsFreeText) {
/* 1025 */           ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 1026 */           if (toolManager.isEditFreeTextOnTap()) {
/* 1027 */             enterText();
/*      */           } else {
/* 1029 */             showMenu(getAnnotRect());
/*      */           } 
/*      */         } else {
/* 1032 */           showMenu(getAnnotRect());
/*      */         } 
/*      */       } 
/*      */     } else {
/* 1036 */       if (this.mTapToSaveFreeTextAnnot) {
/* 1037 */         this.mTapToSaveFreeTextAnnot = false;
/* 1038 */         return true;
/*      */       } 
/*      */       
/* 1041 */       if (sDebug) Log.d(TAG, "going to unsetAnnot: onSingleTapConfirmed"); 
/* 1042 */       unsetAnnot();
/* 1043 */       this.mNextToolMode = this.mCurrentDefaultToolMode;
/*      */ 
/*      */       
/* 1046 */       if (this.mCurrentDefaultToolMode == ToolManager.ToolMode.SIGNATURE || this.mCurrentDefaultToolMode == ToolManager.ToolMode.STAMPER || this.mCurrentDefaultToolMode == ToolManager.ToolMode.RUBBER_STAMPER)
/*      */       {
/*      */         
/* 1049 */         this.mNextToolMode = ToolManager.ToolMode.PAN;
/*      */       }
/*      */       
/* 1052 */       setCtrlPts();
/*      */       
/* 1054 */       this.mPdfViewCtrl.invalidate((int)Math.floor(this.mBBox.left), (int)Math.floor(this.mBBox.top), (int)Math.ceil(this.mBBox.right), (int)Math.ceil(this.mBBox.bottom));
/*      */     } 
/*      */     
/* 1057 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onPageTurning(int old_page, int cur_page) {
/* 1065 */     super.onPageTurning(old_page, cur_page);
/* 1066 */     this.mNextToolMode = this.mCurrentDefaultToolMode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onClose() {
/* 1074 */     super.onClose();
/*      */     
/* 1076 */     if (this.mHasOnCloseCalled) {
/*      */       return;
/*      */     }
/* 1079 */     this.mHasOnCloseCalled = true;
/*      */     
/* 1081 */     if (this.mInlineEditText != null && this.mInlineEditText.isEditing().booleanValue()) {
/* 1082 */       InputMethodManager imm = (InputMethodManager)this.mPdfViewCtrl.getContext().getSystemService("input_method");
/* 1083 */       if (imm != null) {
/* 1084 */         imm.hideSoftInputFromWindow(this.mPdfViewCtrl.getRootView().getWindowToken(), 0);
/*      */       }
/*      */       
/* 1087 */       saveAndQuitInlineEditText(false);
/*      */     } 
/*      */     
/* 1090 */     if (this.mDialogStickyNote != null && this.mDialogStickyNote.isShowing()) {
/*      */       
/* 1092 */       this.mAnnotButtonPressed = -1;
/* 1093 */       prepareDialogStickyNoteDismiss(false);
/* 1094 */       this.mDialogStickyNote.dismiss();
/*      */     } 
/*      */     
/* 1097 */     unsetAnnot();
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean canDrawLoupe() {
/* 1102 */     if (null == this.mAnnot) {
/* 1103 */       return false;
/*      */     }
/* 1105 */     if (this.mAnnotIsMeasurement) {
/* 1106 */       return !this.mDrawingLoupe;
/*      */     }
/* 1108 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   protected int getLoupeType() {
/* 1113 */     return 2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onQuickMenuClicked(QuickMenuItem menuItem) {
/* 1121 */     if (super.onQuickMenuClicked(menuItem)) {
/* 1122 */       return true;
/*      */     }
/*      */     
/* 1125 */     if (!hasAnnotSelected()) {
/* 1126 */       this.mNextToolMode = ToolManager.ToolMode.PAN;
/* 1127 */       return true;
/*      */     } 
/*      */     
/* 1130 */     int type = 28;
/* 1131 */     boolean shouldUnlockRead = false;
/*      */     try {
/* 1133 */       this.mPdfViewCtrl.docLockRead();
/* 1134 */       shouldUnlockRead = true;
/* 1135 */       type = this.mAnnot.getType();
/* 1136 */     } catch (Exception e) {
/* 1137 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } finally {
/* 1139 */       if (shouldUnlockRead) {
/* 1140 */         this.mPdfViewCtrl.docUnlockRead();
/*      */       }
/*      */     } 
/*      */     
/* 1144 */     if (menuItem.getItemId() == R.id.qm_delete) {
/* 1145 */       deleteAnnot();
/*      */     
/*      */     }
/* 1148 */     else if (menuItem.getItemId() == R.id.qm_note) {
/* 1149 */       if (this.mAnnotIsSticky) {
/* 1150 */         handleStickyNote(false, false);
/*      */       } else {
/* 1152 */         handleAnnotNote(false);
/*      */       }
/*      */     
/*      */     }
/* 1156 */     else if (menuItem.getItemId() == R.id.qm_appearance) {
/* 1157 */       changeAnnotAppearance();
/*      */     
/*      */     }
/* 1160 */     else if (menuItem.getItemId() == R.id.qm_flatten) {
/* 1161 */       handleFlattenAnnot();
/* 1162 */       this.mNextToolMode = this.mCurrentDefaultToolMode;
/* 1163 */     } else if (menuItem.getItemId() == R.id.qm_screencap_create) {
/* 1164 */       ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 1165 */       CompositeDisposable disposable = toolManager.getDisposable();
/* 1166 */       final Context appContext = this.mPdfViewCtrl.getContext().getApplicationContext();
/*      */       
/* 1168 */       disposable.add(AnnotUtils.createScreenshotAsync(this.mPdfViewCtrl.getContext().getCacheDir(), this.mPdfViewCtrl.getDoc(), this.mAnnot, this.mAnnotPageNum)
/* 1169 */           .subscribeOn(Schedulers.io())
/* 1170 */           .observeOn(AndroidSchedulers.mainThread())
/* 1171 */           .doOnSubscribe(new Consumer<Disposable>()
/*      */             {
/*      */               public void accept(Disposable disposable) throws Exception {
/* 1174 */                 CommonToast.showText(appContext, R.string.tools_screenshot_creating, 1);
/*      */               }
/* 1177 */             }).subscribe(new Consumer<String>()
/*      */             {
/*      */               public void accept(String s) {
/* 1180 */                 ((ToolManager)AnnotEdit.this.mPdfViewCtrl.getToolManager()).onFileCreated(s, ToolManager.AdvancedAnnotationListener.AnnotAction.SCREENSHOT_CREATE);
/*      */               }
/*      */             }new Consumer<Throwable>()
/*      */             {
/*      */               public void accept(Throwable throwable) throws Exception {
/* 1185 */                 CommonToast.showText(appContext, R.string.tools_screenshot_creating_error, 1);
/*      */               }
/*      */             }));
/*      */       
/* 1189 */       this.mNextToolMode = ToolManager.ToolMode.PAN;
/*      */     } else {
/*      */       
/* 1192 */       if (type == 9 || type == 8 || type == 11 || type == 10) {
/*      */ 
/*      */ 
/*      */         
/* 1196 */         this.mNextToolMode = ToolManager.ToolMode.ANNOT_EDIT_TEXT_MARKUP;
/* 1197 */         return false;
/* 1198 */       }  if (menuItem.getItemId() == R.id.qm_text) {
/* 1199 */         if (this.mAnnotStyle != null && this.mAnnotStyle.isDateFreeText()) {
/* 1200 */           enterDate();
/*      */         } else {
/* 1202 */           enterText();
/*      */         } 
/* 1204 */       } else if (menuItem.getItemId() == R.id.qm_copy) {
/* 1205 */         AnnotationClipboardHelper.copyAnnot(this.mPdfViewCtrl.getContext(), this.mAnnot, this.mPdfViewCtrl, new AnnotationClipboardHelper.OnClipboardTaskListener()
/*      */             {
/*      */               public void onnClipboardTaskDone(String error)
/*      */               {
/* 1209 */                 if (error == null && AnnotEdit.this.mPdfViewCtrl.getContext() != null) {
/* 1210 */                   CommonToast.showText(AnnotEdit.this.mPdfViewCtrl.getContext(), R.string.tools_copy_annot_confirmation, 0);
/*      */                 }
/*      */               }
/*      */             });
/* 1214 */       } else if (menuItem.getItemId() == R.id.qm_open_attachment) {
/* 1215 */         FileAttachment fileAttachment = null;
/* 1216 */         shouldUnlockRead = false;
/*      */         try {
/* 1218 */           this.mPdfViewCtrl.docLockRead();
/* 1219 */           shouldUnlockRead = true;
/*      */           
/* 1221 */           fileAttachment = new FileAttachment(this.mAnnot);
/* 1222 */         } catch (Exception e) {
/* 1223 */           AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */         } finally {
/* 1225 */           if (shouldUnlockRead) {
/* 1226 */             this.mPdfViewCtrl.docUnlockRead();
/*      */           }
/*      */         } 
/*      */         
/* 1230 */         ((ToolManager)this.mPdfViewCtrl.getToolManager()).onFileAttachmentSelected(fileAttachment);
/* 1231 */         this.mNextToolMode = ToolManager.ToolMode.PAN;
/* 1232 */       } else if (menuItem.getItemId() == R.id.qm_edit) {
/* 1233 */         if (type == 14) {
/* 1234 */           editInk();
/* 1235 */         } else if (type == 19) {
/* 1236 */           editWidget();
/*      */         } 
/* 1238 */       } else if (menuItem.getItemId() == R.id.qm_link) {
/* 1239 */         if (type == 1) {
/* 1240 */           shouldUnlockRead = false;
/*      */           try {
/* 1242 */             this.mPdfViewCtrl.docLockRead();
/* 1243 */             shouldUnlockRead = true;
/*      */             
/* 1245 */             Link link = new Link(this.mAnnot);
/* 1246 */             DialogLinkEditor linkEditorDialog = new DialogLinkEditor(this.mPdfViewCtrl, this, link);
/* 1247 */             linkEditorDialog.show();
/* 1248 */           } catch (Exception e) {
/* 1249 */             AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */           } finally {
/* 1251 */             if (shouldUnlockRead) {
/* 1252 */               this.mPdfViewCtrl.docUnlockRead();
/*      */             }
/*      */           } 
/*      */         } 
/* 1256 */       } else if (menuItem.getItemId() == R.id.qm_form_radio_add_item) {
/* 1257 */         RadioButtonGroup group = null;
/* 1258 */         shouldUnlockRead = false;
/*      */         try {
/* 1260 */           this.mPdfViewCtrl.docLockRead();
/* 1261 */           shouldUnlockRead = true;
/* 1262 */           RadioButtonWidget radioWidget = new RadioButtonWidget(this.mAnnot);
/* 1263 */           group = radioWidget.getGroup();
/* 1264 */         } catch (Exception e) {
/* 1265 */           AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */         } finally {
/* 1267 */           if (shouldUnlockRead) {
/* 1268 */             this.mPdfViewCtrl.docUnlockRead();
/*      */           }
/*      */         } 
/* 1271 */         this.mNextToolMode = ToolManager.ToolMode.FORM_RADIO_GROUP_CREATE;
/* 1272 */         RadioGroupFieldCreate radioGroupTool = (RadioGroupFieldCreate)((ToolManager)this.mPdfViewCtrl.getToolManager()).createTool(ToolManager.ToolMode.FORM_RADIO_GROUP_CREATE, this);
/* 1273 */         ((ToolManager)this.mPdfViewCtrl.getToolManager()).setTool(radioGroupTool);
/* 1274 */         radioGroupTool.setTargetGroup(group);
/* 1275 */       } else if (menuItem.getItemId() == R.id.qm_redact) {
/* 1276 */         redactAnnot();
/* 1277 */       } else if (menuItem.getItemId() == R.id.qm_play_sound) {
/* 1278 */         playSoundAnnot();
/* 1279 */       } else if (menuItem.getItemId() == R.id.qm_calibrate) {
/* 1280 */         calibration();
/*      */       } 
/* 1282 */     }  return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void changeAnnotAppearance() {
/* 1289 */     boolean shouldUnlock = false;
/*      */     try {
/* 1291 */       this.mPdfViewCtrl.docLockRead();
/* 1292 */       shouldUnlock = true;
/* 1293 */       if (this.mAnnot == null || !this.mAnnot.isValid()) {
/*      */         return;
/*      */       }
/*      */       
/* 1297 */       AnnotStyleDialogFragment.Builder styleDialogBuilder = new AnnotStyleDialogFragment.Builder();
/*      */       
/* 1299 */       if (this.mAnnotHasFont) {
/* 1300 */         ToolManager toolManager1 = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 1301 */         Set<String> whiteListFonts = toolManager1.getFreeTextFonts();
/* 1302 */         styleDialogBuilder.setWhiteListFont(whiteListFonts);
/*      */       } 
/*      */       
/* 1305 */       ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 1306 */       if (null == this.mAnnotStyle) {
/* 1307 */         this.mAnnotStyle = AnnotUtils.getAnnotStyle(this.mAnnot);
/*      */       }
/* 1309 */       this.mAnnotStyle.setSnap(toolManager.isSnappingEnabledForMeasurementTools());
/*      */       
/* 1311 */       int[] pdfViewCtrlOrigin = new int[2];
/* 1312 */       this.mPdfViewCtrl.getLocationInWindow(pdfViewCtrlOrigin);
/* 1313 */       RectF annotRect = getAnnotRect();
/* 1314 */       annotRect.offset(pdfViewCtrlOrigin[0], pdfViewCtrlOrigin[1]);
/* 1315 */       this.mAnnotStyleDialog = styleDialogBuilder.setAnnotStyle(this.mAnnotStyle).setAnchor(annotRect).build();
/*      */       
/* 1317 */       this.mAnnotStyleDialog.setOnAnnotStyleChangeListener(this);
/* 1318 */       this.mAnnotStyleDialog.setOnDismissListener(new DialogInterface.OnDismissListener()
/*      */           {
/*      */             public void onDismiss(DialogInterface dialogInterface) {
/* 1321 */               AnnotEdit.this.mAnnotStyleDialog = null;
/* 1322 */               ToolManager toolManager = (ToolManager)AnnotEdit.this.mPdfViewCtrl.getToolManager();
/* 1323 */               toolManager.selectAnnot(AnnotEdit.this.mAnnot, AnnotEdit.this.mAnnotPageNum);
/*      */             }
/*      */           });
/* 1326 */       FragmentActivity activity = ((ToolManager)this.mPdfViewCtrl.getToolManager()).getCurrentActivity();
/* 1327 */       if (activity == null) {
/* 1328 */         AnalyticsHandlerAdapter.getInstance().sendException(new Exception("ToolManager is not attached with an Activity"));
/*      */         return;
/*      */       } 
/* 1331 */       this.mAnnotStyleDialog.show(activity.getSupportFragmentManager(), 1, 
/*      */           
/* 1333 */           AnalyticsHandlerAdapter.getInstance().getAnnotToolByAnnotType(this.mAnnotStyle.getAnnotType()));
/* 1334 */     } catch (Exception e) {
/* 1335 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } finally {
/* 1337 */       if (shouldUnlock) {
/* 1338 */         this.mPdfViewCtrl.docUnlockRead();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void rotateStampAnnot(int degree, boolean snap) {
/* 1344 */     if (!this.mAnnotIsStamper || this.mAnnot == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1348 */     Bundle bundle = new Bundle();
/* 1349 */     bundle.putString("METHOD_FROM", "rotateStampAnnot");
/* 1350 */     if (onInterceptAnnotationHandling(this.mAnnot, bundle)) {
/*      */       return;
/*      */     }
/*      */     
/* 1354 */     boolean shouldUnlock = false;
/*      */     try {
/* 1356 */       this.mPdfViewCtrl.docLock(true);
/* 1357 */       shouldUnlock = true;
/*      */       
/* 1359 */       raiseAnnotationPreModifyEvent(this.mAnnot, this.mAnnotPageNum);
/*      */ 
/*      */       
/* 1362 */       int rotation = AnnotUtils.getStampDegree(this.mAnnot);
/* 1363 */       rotation = degree + rotation;
/* 1364 */       if (snap) {
/* 1365 */         rotation = degree;
/*      */       } else {
/* 1367 */         rotation %= 360;
/* 1368 */         if (rotation > 270) {
/* 1369 */           rotation -= 360;
/*      */         }
/*      */       } 
/* 1372 */       AnnotUtils.putStampDegree(this.mAnnot, rotation);
/* 1373 */       this.mAnnot.setRotation(rotation);
/* 1374 */       this.mAnnot.refreshAppearance();
/* 1375 */       this.mPdfViewCtrl.update(this.mAnnot, this.mAnnotPageNum);
/*      */       
/* 1377 */       raiseAnnotationModifiedEvent(this.mAnnot, this.mAnnotPageNum, bundle);
/* 1378 */     } catch (Exception e) {
/* 1379 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } finally {
/* 1381 */       if (shouldUnlock) {
/* 1382 */         this.mPdfViewCtrl.docUnlock();
/*      */       }
/*      */     } 
/*      */     
/* 1386 */     removeAnnotView(false, false);
/* 1387 */     selectAnnot(this.mAnnot, this.mAnnotPageNum);
/*      */   }
/*      */   
/*      */   public void enterDate() {
/* 1391 */     SimpleDateTimePickerFragment picker = SimpleDateTimePickerFragment.newInstance(0, false);
/* 1392 */     picker.setSimpleDatePickerListener(new SimpleDateTimePickerFragment.SimpleDatePickerListener()
/*      */         {
/*      */           public void onDateSet(DatePicker view, int year, int month, int day)
/*      */           {
/* 1396 */             if (AnnotEdit.this.mAnnotStyle != null && AnnotEdit.this.mAnnotStyle.getDateFormat() != null) {
/* 1397 */               SimpleDateFormat dateFormat = new SimpleDateFormat(AnnotEdit.this.mAnnotStyle.getDateFormat(), Locale.getDefault());
/* 1398 */               Calendar cal = Calendar.getInstance();
/* 1399 */               cal.set(view.getYear(), view.getMonth(), view.getDayOfMonth());
/* 1400 */               String dateStr = dateFormat.format(cal.getTime());
/* 1401 */               AnnotEdit.this.updateFreeText(dateStr);
/* 1402 */               AnnotEdit.this.setCtrlPts();
/*      */             } 
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public void onTimeSet(TimePicker view, int hourOfDay, int minute) {}
/*      */ 
/*      */ 
/*      */           
/*      */           public void onClear() {}
/*      */ 
/*      */ 
/*      */           
/*      */           public void onDismiss(boolean manuallyEnterValue, boolean dismissedWithNoSelection) {
/* 1418 */             AnnotEdit.this.showMenu(AnnotEdit.this.getAnnotRect());
/*      */           }
/*      */         });
/* 1421 */     FragmentActivity fragmentActivity = ((ToolManager)this.mPdfViewCtrl.getToolManager()).getCurrentActivity();
/* 1422 */     if (fragmentActivity != null) {
/* 1423 */       picker.show(fragmentActivity.getSupportFragmentManager(), SimpleDateTimePickerFragment.TAG);
/*      */     }
/*      */   }
/*      */   
/*      */   public void enterText() {
/* 1428 */     if (!this.mHasMenuPermission) {
/*      */       return;
/*      */     }
/* 1431 */     this.mInEditMode = true;
/* 1432 */     this.mSaveFreeTextAnnotInOnUp = true;
/* 1433 */     if (!this.mCtrlPtsSet) {
/* 1434 */       setCtrlPts();
/*      */     }
/*      */ 
/*      */     
/* 1438 */     SharedPreferences settings = Tool.getToolPreferences(this.mPdfViewCtrl.getContext());
/* 1439 */     this.mCurrentFreeTextEditMode = settings.getInt("annotation_free_text_preference_editing", 1);
/* 1440 */     this.mCacheFileName = ((ToolManager)this.mPdfViewCtrl.getToolManager()).getFreeTextCacheFileName();
/*      */     try {
/* 1442 */       if (!Utils.isTablet(this.mPdfViewCtrl.getContext()) && 
/* 1443 */         (this.mPdfViewCtrl.getContext().getResources().getConfiguration()).orientation == 2) {
/* 1444 */         fallbackFreeTextDialog((String)null, true);
/* 1445 */       } else if (this.mCurrentFreeTextEditMode == 2) {
/* 1446 */         fallbackFreeTextDialog((String)null, false);
/*      */       } else {
/* 1448 */         initInlineFreeTextEditing((String)null);
/*      */       } 
/* 1450 */     } catch (Exception e) {
/* 1451 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void editInk() {
/*      */     try {
/* 1457 */       if (((ToolManager)this.mPdfViewCtrl.getToolManager()).editInkAnnots() && this.mAnnot != null) {
/* 1458 */         ((ToolManager)this.mPdfViewCtrl.getToolManager()).onInkEditSelected(this.mAnnot, this.mAnnotPageNum);
/*      */       }
/* 1460 */     } catch (Exception e) {
/* 1461 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void editWidget() {
/* 1466 */     ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 1467 */     FragmentActivity activity = toolManager.getCurrentActivity();
/* 1468 */     if (activity == null) {
/* 1469 */       Log.e(Signature.class.getName(), "ToolManager is not attached to with an Activity");
/*      */       
/*      */       return;
/*      */     } 
/* 1473 */     String[] options = null;
/* 1474 */     boolean isSingleChoice = false;
/* 1475 */     boolean isCombo = false;
/* 1476 */     boolean shouldUnlockRead = false;
/* 1477 */     long widget = 0L;
/*      */     try {
/* 1479 */       this.mPdfViewCtrl.docLockRead();
/* 1480 */       shouldUnlockRead = true;
/* 1481 */       Field field = (new Widget(this.mAnnot)).getField();
/* 1482 */       isCombo = field.getFlag(14);
/* 1483 */       isSingleChoice = (isCombo || !field.getFlag(17));
/* 1484 */       if (isCombo) {
/* 1485 */         ComboBoxWidget combo = new ComboBoxWidget(this.mAnnot);
/* 1486 */         options = combo.getOptions();
/*      */       } else {
/* 1488 */         ListBoxWidget list = new ListBoxWidget(this.mAnnot);
/* 1489 */         options = list.getOptions();
/*      */       } 
/* 1491 */       widget = this.mAnnot.__GetHandle();
/* 1492 */     } catch (Exception ex) {
/* 1493 */       AnalyticsHandlerAdapter.getInstance().sendException(ex);
/* 1494 */       options = null;
/*      */     } finally {
/* 1496 */       if (shouldUnlockRead) {
/* 1497 */         this.mPdfViewCtrl.docUnlockRead();
/*      */       }
/*      */     } 
/*      */     
/* 1501 */     showWidgetChoiceDialog(widget, this.mAnnotPageNum, isSingleChoice, isCombo, options);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateAnnot() throws PDFNetException {
/* 1510 */     if (this.mAnnot == null || onInterceptAnnotationHandling(this.mAnnot)) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 1515 */     Rect newAnnotRect = getNewAnnotPagePosition();
/* 1516 */     if (newAnnotRect == null) {
/*      */       return;
/*      */     }
/* 1519 */     Rect oldUpdateRect = null;
/* 1520 */     if (!this.mPdfViewCtrl.isAnnotationLayerEnabled()) {
/* 1521 */       oldUpdateRect = getOldAnnotScreenPosition();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1527 */     if (this.mEffCtrlPtId != -2 && this.mAnnot.getType() != 12)
/*      */     {
/* 1529 */       AnnotUtils.refreshAnnotAppearance(this.mPdfViewCtrl.getContext(), this.mAnnot);
/*      */     }
/*      */     
/* 1532 */     if (this.mContentBox != null && this.mEffCtrlPtId != -2) {
/* 1533 */       FreeText freeText = new FreeText(this.mAnnot);
/* 1534 */       Rect oldContentRect = freeText.getContentRect();
/* 1535 */       Rect newContentRect = getNewContentRectPagePosition();
/* 1536 */       resizeCallout(freeText, oldContentRect, newContentRect);
/*      */     } else {
/* 1538 */       this.mAnnot.resize(newAnnotRect);
/* 1539 */       if (-2 != this.mEffCtrlPtId) {
/*      */ 
/*      */         
/* 1542 */         if (AnnotUtils.isListBox(this.mAnnot)) {
/* 1543 */           ListBoxWidget listBoxWidget = new ListBoxWidget(this.mAnnot);
/* 1544 */           String[] options = listBoxWidget.getSelectedOptions();
/* 1545 */           if (options != null) {
/* 1546 */             String str = Arrays.toString((Object[])options);
/*      */             try {
/* 1548 */               Tool.updateFont(this.mPdfViewCtrl, (Widget)listBoxWidget, str);
/* 1549 */             } catch (JSONException jSONException) {}
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/* 1554 */         if (AnnotUtils.isRectAreaMeasure(this.mAnnot)) {
/*      */ 
/*      */           
/* 1557 */           Polygon poly = new Polygon(this.mAnnot);
/* 1558 */           RulerItem rulerItem = MeasureUtils.getRulerItemFromAnnot((Annot)poly);
/* 1559 */           ArrayList<Point> points = AnnotUtils.getPolyVertices((Annot)poly);
/* 1560 */           if (null != rulerItem && null != points) {
/* 1561 */             AreaMeasureCreate.adjustContents((Annot)poly, rulerItem, points);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1568 */     if (this.mEffCtrlPtId != -2 && this.mAnnot.getType() != 12) {
/* 1569 */       boolean containsRC = false;
/* 1570 */       if (this.mAnnot.getType() == 2) {
/* 1571 */         containsRC = !Utils.isNullOrEmpty(this.mAnnot.getCustomData(AnnotUtils.KEY_RawRichContent));
/*      */       }
/* 1573 */       if (containsRC && this.mAnnotView != null) {
/* 1574 */         AnnotUtils.createRCFreeTextAppearance((PTRichEditor)this.mAnnotView
/* 1575 */             .getRichEditor(), this.mPdfViewCtrl, this.mAnnot, this.mAnnotPageNum);
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */         
/* 1581 */         AnnotUtils.refreshAnnotAppearance(this.mPdfViewCtrl.getContext(), this.mAnnot);
/*      */       } 
/*      */     } 
/* 1584 */     buildAnnotBBox();
/*      */     
/* 1586 */     if (null != oldUpdateRect) {
/* 1587 */       this.mPdfViewCtrl.update(oldUpdateRect);
/*      */     }
/* 1589 */     this.mPdfViewCtrl.update(this.mAnnot, this.mAnnotPageNum);
/*      */     
/* 1591 */     if (!this.mMaintainAspectRatio) {
/* 1592 */       this.mAspectRatio = (float)(newAnnotRect.getHeight() / newAnnotRect.getWidth());
/*      */     }
/*      */     
/* 1595 */     if (this.mAnnotStyle != null && this.mAnnotStyle.isSpacingFreeText())
/*      */     {
/* 1597 */       setCtrlPts();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void adjustExtraFreeTextProps(Rect oldContentRect, Rect newContentRect) {}
/*      */ 
/*      */ 
/*      */   
/*      */   protected Rect getNewAnnotPagePosition() throws PDFNetException {
/*      */     Rect newAnnotRect;
/* 1610 */     if (this.mAnnot == null) {
/* 1611 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1615 */     float x1 = this.mBBox.left + this.mCtrlRadius - this.mPdfViewCtrl.getScrollX();
/* 1616 */     float y1 = this.mBBox.top + this.mCtrlRadius - this.mPdfViewCtrl.getScrollY();
/* 1617 */     float x2 = this.mBBox.right - this.mCtrlRadius - this.mPdfViewCtrl.getScrollX();
/* 1618 */     float y2 = this.mBBox.bottom - this.mCtrlRadius - this.mPdfViewCtrl.getScrollY();
/*      */     
/* 1620 */     double[] pts1 = this.mPdfViewCtrl.convScreenPtToPagePt(x1, y1, this.mAnnotPageNum);
/* 1621 */     double[] pts2 = this.mPdfViewCtrl.convScreenPtToPagePt(x2, y2, this.mAnnotPageNum);
/*      */ 
/*      */     
/* 1624 */     if (this.mAnnot.getFlag(3)) {
/* 1625 */       newAnnotRect = new Rect(pts1[0], pts1[1] - this.mAnnot.getRect().getHeight(), pts1[0] + this.mAnnot.getRect().getWidth(), pts1[1]);
/*      */     } else {
/* 1627 */       newAnnotRect = new Rect(pts1[0], pts1[1], pts2[0], pts2[1]);
/*      */     } 
/* 1629 */     newAnnotRect.normalize();
/* 1630 */     return newAnnotRect;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Rect getNewContentRectPagePosition() throws PDFNetException {
/*      */     Rect newAnnotRect;
/* 1638 */     if (this.mAnnot == null || this.mContentBox == null) {
/* 1639 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1643 */     float x1 = this.mContentBox.left + this.mCtrlRadius - this.mPdfViewCtrl.getScrollX();
/* 1644 */     float y1 = this.mContentBox.top + this.mCtrlRadius - this.mPdfViewCtrl.getScrollY();
/* 1645 */     float x2 = this.mContentBox.right - this.mCtrlRadius - this.mPdfViewCtrl.getScrollX();
/* 1646 */     float y2 = this.mContentBox.bottom - this.mCtrlRadius - this.mPdfViewCtrl.getScrollY();
/*      */     
/* 1648 */     double[] pts1 = this.mPdfViewCtrl.convScreenPtToPagePt(x1, y1, this.mAnnotPageNum);
/* 1649 */     double[] pts2 = this.mPdfViewCtrl.convScreenPtToPagePt(x2, y2, this.mAnnotPageNum);
/*      */ 
/*      */     
/* 1652 */     if (this.mAnnot.getFlag(3)) {
/* 1653 */       newAnnotRect = new Rect(pts1[0], pts1[1] - this.mAnnot.getRect().getHeight(), pts1[0] + this.mAnnot.getRect().getWidth(), pts1[1]);
/*      */     } else {
/* 1655 */       newAnnotRect = new Rect(pts1[0], pts1[1], pts2[0], pts2[1]);
/*      */     } 
/* 1657 */     newAnnotRect.normalize();
/* 1658 */     return newAnnotRect;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Rect getOldAnnotScreenPosition() throws PDFNetException {
/* 1666 */     if (this.mAnnot == null) {
/* 1667 */       return null;
/*      */     }
/*      */     
/* 1670 */     Rect oldUpdateRect = this.mPdfViewCtrl.getScreenRectForAnnot(this.mAnnot, this.mAnnotPageNum);
/* 1671 */     oldUpdateRect.normalize();
/* 1672 */     return oldUpdateRect;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onKeyUp(int keyCode, KeyEvent event) {
/* 1680 */     if (this.mInlineEditText != null && this.mInlineEditText.isEditing().booleanValue()) {
/* 1681 */       return true;
/*      */     }
/*      */     
/* 1684 */     if (this.mAnnot != null && isQuickMenuShown()) {
/* 1685 */       if (hasMenuEntry(R.id.qm_copy) && ShortcutHelper.isCopy(keyCode, event)) {
/* 1686 */         closeQuickMenu();
/* 1687 */         AnnotationClipboardHelper.copyAnnot(this.mPdfViewCtrl.getContext(), this.mAnnot, this.mPdfViewCtrl, new AnnotationClipboardHelper.OnClipboardTaskListener()
/*      */             {
/*      */               public void onnClipboardTaskDone(String error)
/*      */               {
/* 1691 */                 if (error == null && AnnotEdit.this.mPdfViewCtrl.getContext() != null && 
/* 1692 */                   PdfViewCtrlSettingsManager.shouldShowHowToPaste(AnnotEdit.this.mPdfViewCtrl.getContext())) {
/*      */                   
/* 1694 */                   PointF point = AnnotEdit.this.mPdfViewCtrl.getCurrentMousePosition();
/* 1695 */                   if (point.x != 0.0F || point.y != 0.0F) {
/* 1696 */                     CommonToast.showText(AnnotEdit.this.mPdfViewCtrl.getContext(), R.string.tools_copy_annot_teach, 0);
/*      */                   }
/*      */                 } 
/*      */               }
/*      */             });
/*      */ 
/*      */         
/* 1703 */         return true;
/*      */       } 
/*      */       
/* 1706 */       if (hasMenuEntry(R.id.qm_copy) && hasMenuEntry(R.id.qm_delete) && ShortcutHelper.isCut(keyCode, event)) {
/* 1707 */         closeQuickMenu();
/* 1708 */         AnnotationClipboardHelper.copyAnnot(this.mPdfViewCtrl.getContext(), this.mAnnot, this.mPdfViewCtrl, new AnnotationClipboardHelper.OnClipboardTaskListener()
/*      */             {
/*      */               public void onnClipboardTaskDone(String error)
/*      */               {
/* 1712 */                 if (error == null && AnnotEdit.this.mPdfViewCtrl.getContext() != null) {
/* 1713 */                   if (PdfViewCtrlSettingsManager.shouldShowHowToPaste(AnnotEdit.this.mPdfViewCtrl.getContext())) {
/*      */                     
/* 1715 */                     PointF point = AnnotEdit.this.mPdfViewCtrl.getCurrentMousePosition();
/* 1716 */                     if (point.x != 0.0F || point.y != 0.0F) {
/* 1717 */                       CommonToast.showText(AnnotEdit.this.mPdfViewCtrl.getContext(), R.string.tools_copy_annot_teach, 0);
/*      */                     }
/*      */                   } 
/* 1720 */                   AnnotEdit.this.deleteAnnot();
/*      */                 } 
/*      */               }
/*      */             });
/* 1724 */         return true;
/*      */       } 
/*      */       
/* 1727 */       if (hasMenuEntry(R.id.qm_delete) && ShortcutHelper.isDeleteAnnot(keyCode, event)) {
/* 1728 */         closeQuickMenu();
/* 1729 */         deleteAnnot();
/* 1730 */         return true;
/*      */       } 
/*      */       
/* 1733 */       if (ShortcutHelper.isStartEdit(keyCode, event)) {
/* 1734 */         if (hasMenuEntry(R.id.qm_text)) {
/* 1735 */           closeQuickMenu();
/* 1736 */           enterText();
/* 1737 */           return true;
/* 1738 */         }  if (hasMenuEntry(R.id.qm_edit)) {
/* 1739 */           closeQuickMenu();
/* 1740 */           editInk();
/* 1741 */           return true;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1746 */     if (this.mInEditMode) {
/* 1747 */       if (ShortcutHelper.isCommitText(keyCode, event)) {
/*      */ 
/*      */         
/* 1750 */         saveAndQuitInlineEditText(false);
/*      */         
/* 1752 */         InputMethodManager imm = (InputMethodManager)this.mPdfViewCtrl.getContext().getSystemService("input_method");
/* 1753 */         if (imm != null) {
/* 1754 */           imm.hideSoftInputFromWindow(this.mPdfViewCtrl.getRootView().getWindowToken(), 0);
/*      */         }
/*      */       } 
/* 1757 */       return true;
/*      */     } 
/*      */     
/* 1760 */     return super.onKeyUp(keyCode, event);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean editAnnotSize(PDFViewCtrl.PriorEventMode priorEventMode) {
/* 1770 */     if (this.mAnnot == null) {
/* 1771 */       return false;
/*      */     }
/*      */     
/* 1774 */     boolean shouldUnlock = false;
/*      */     try {
/* 1776 */       this.mPdfViewCtrl.docLock(true);
/* 1777 */       shouldUnlock = true;
/* 1778 */       if (this.mModifiedAnnot) {
/* 1779 */         this.mModifiedAnnot = false;
/* 1780 */         raiseAnnotationPreModifyEvent(this.mAnnot, this.mAnnotPageNum);
/* 1781 */         updateAnnot();
/* 1782 */         raiseAnnotationModifiedEvent(this.mAnnot, this.mAnnotPageNum);
/* 1783 */       } else if (priorEventMode == PDFViewCtrl.PriorEventMode.PINCH || priorEventMode == PDFViewCtrl.PriorEventMode.DOUBLE_TAP) {
/* 1784 */         setCtrlPts();
/*      */       } 
/*      */ 
/*      */       
/* 1788 */       if (this.mUpFromStickyCreate && !this.mUpFromStickyCreateDlgShown) {
/* 1789 */         handleStickyNote(this.mForceSameNextToolMode, true);
/* 1790 */         return false;
/*      */       } 
/* 1792 */     } catch (Exception ex) {
/* 1793 */       AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*      */     } finally {
/* 1795 */       if (shouldUnlock) {
/* 1796 */         this.mPdfViewCtrl.docUnlock();
/*      */       }
/*      */     } 
/* 1799 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onScaleBegin(float x, float y) {
/* 1807 */     this.mIsScaleBegun = true;
/*      */     
/* 1809 */     if (this.mInlineEditText != null && this.mInlineEditText.isEditing().booleanValue()) {
/* 1810 */       saveAndQuitInlineEditText(true);
/*      */     }
/* 1812 */     return super.onScaleBegin(x, y);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean onScale(float x, float y) {
/* 1817 */     return super.onScale(x, y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onScaleEnd(float x, float y) {
/* 1825 */     super.onScaleEnd(x, y);
/*      */     
/* 1827 */     this.mIsScaleBegun = false;
/*      */     
/* 1829 */     if (this.mAnnot != null) {
/*      */ 
/*      */       
/* 1832 */       this.mScaled = true;
/* 1833 */       setCtrlPts();
/* 1834 */       this.mPdfViewCtrl.invalidate((int)Math.floor(this.mBBox.left), (int)Math.floor(this.mBBox.top), (int)Math.ceil(this.mBBox.right), (int)Math.ceil(this.mBBox.bottom));
/* 1835 */       if (isQuickMenuShown()) {
/* 1836 */         closeQuickMenu();
/* 1837 */         showMenu(getAnnotRect());
/*      */       } 
/*      */     } 
/* 1840 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onFlingStop() {
/* 1848 */     super.onFlingStop();
/*      */     
/* 1850 */     this.mIsScaleBegun = false;
/*      */     
/* 1852 */     if (this.mAnnot != null) {
/* 1853 */       if (!this.mCtrlPtsSet) {
/* 1854 */         setCtrlPts();
/*      */       }
/* 1856 */       this.mPdfViewCtrl.invalidate((int)Math.floor(this.mBBox.left), (int)Math.floor(this.mBBox.top), (int)Math.ceil(this.mBBox.right), (int)Math.ceil(this.mBBox.bottom));
/* 1857 */       if (isQuickMenuShown()) {
/* 1858 */         closeQuickMenu();
/* 1859 */         showMenu(getAnnotRect());
/*      */       } 
/*      */     } 
/* 1862 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onLayout(boolean changed, int l, int t, int r, int b) {
/* 1870 */     super.onLayout(changed, l, t, r, b);
/* 1871 */     if (sDebug) Log.d("AnnotEdit", "onLayout: " + changed); 
/* 1872 */     if (this.mAnnot != null) {
/* 1873 */       if (!this.mPdfViewCtrl.isContinuousPagePresentationMode(this.mPdfViewCtrl.getPagePresentationMode()) && 
/* 1874 */         this.mAnnotPageNum != this.mPdfViewCtrl.getCurrentPage()) {
/*      */ 
/*      */         
/* 1877 */         if (sDebug) Log.d(TAG, "going to unsetAnnot: onLayout"); 
/* 1878 */         unsetAnnot();
/* 1879 */         this.mNextToolMode = ToolManager.ToolMode.PAN;
/* 1880 */         setCtrlPts();
/* 1881 */         this.mEffCtrlPtId = -1;
/* 1882 */         closeQuickMenu();
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/* 1887 */       setCtrlPts();
/* 1888 */       if (isQuickMenuShown() && changed) {
/* 1889 */         closeQuickMenu();
/* 1890 */         showMenu(getAnnotRect());
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onLongPress(MotionEvent e) {
/* 1900 */     super.onLongPress(e);
/*      */     
/* 1902 */     if (!hasAnnotSelected()) {
/* 1903 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1907 */     if (this.mInlineEditText != null && this.mInlineEditText.isEditing().booleanValue()) {
/* 1908 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1913 */     if (this.mEffCtrlPtId == -1) {
/* 1914 */       int x = (int)(e.getX() + 0.5D);
/* 1915 */       int y = (int)(e.getY() + 0.5D);
/* 1916 */       Annot tempAnnot = this.mPdfViewCtrl.getAnnotationAt(x, y);
/* 1917 */       if (this.mAnnot != null && this.mAnnot.equals(tempAnnot)) {
/* 1918 */         setCtrlPts();
/* 1919 */         this.mEffCtrlPtId = -2;
/*      */       } 
/*      */     } 
/*      */     
/* 1923 */     if (this.mEffCtrlPtId != -1 && !onInterceptAnnotationHandling(this.mAnnot)) {
/* 1924 */       this.mNextToolMode = getToolMode();
/* 1925 */       setCtrlPts();
/* 1926 */       this.mEffCtrlPtId = -2;
/*      */       try {
/* 1928 */         if (this.mAnnot != null && (this.mAnnot.getType() == 1 || this.mAnnot.getType() == 19)) {
/* 1929 */           showMenu(getAnnotRect());
/*      */         }
/* 1931 */       } catch (Exception ex) {
/* 1932 */         AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*      */       } 
/*      */     } else {
/* 1935 */       if (sDebug) Log.d(TAG, "going to unsetAnnot"); 
/* 1936 */       unsetAnnot();
/* 1937 */       this.mNextToolMode = ToolManager.ToolMode.PAN;
/* 1938 */       setCtrlPts();
/* 1939 */       this.mEffCtrlPtId = -1;
/*      */     } 
/*      */     
/* 1942 */     this.mBBoxOnDown.set(this.mBBox);
/* 1943 */     if (this.mContentBox != null) {
/* 1944 */       if (this.mContentBoxOnDown == null) {
/* 1945 */         this.mContentBoxOnDown = new RectF();
/*      */       }
/* 1947 */       this.mContentBoxOnDown.set(this.mContentBox);
/*      */     } 
/* 1949 */     this.mPdfViewCtrl.invalidate((int)Math.floor(this.mBBox.left), (int)Math.floor(this.mBBox.top), (int)Math.ceil(this.mBBox.right), (int)Math.ceil(this.mBBox.bottom));
/*      */     
/* 1951 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onScrollChanged(int l, int t, int oldl, int oldt) {
/* 1960 */     if (!this.mIsScaleBegun && this.mAnnot != null && Math.abs(t - oldt) <= 1 && !isQuickMenuShown() && (this.mInlineEditText == null || !this.mInlineEditText.isEditing().booleanValue())) {
/* 1961 */       showMenu(getAnnotRect());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onDown(MotionEvent e) {
/* 1970 */     super.onDown(e);
/*      */     
/* 1972 */     float x = e.getX() + this.mPdfViewCtrl.getScrollX();
/* 1973 */     float y = e.getY() + this.mPdfViewCtrl.getScrollY();
/*      */ 
/*      */     
/* 1976 */     if (!this.mBBox.contains(x, y) && this.mInlineEditText != null && this.mInlineEditText.isEditing().booleanValue() && this.mAnnotIsFreeText) {
/*      */       
/* 1978 */       this.mTapToSaveFreeTextAnnot = true;
/* 1979 */       this.mSaveFreeTextAnnotInOnUp = true;
/* 1980 */       return true;
/*      */     } 
/*      */ 
/*      */     
/* 1984 */     this.mLoupeEnabled = this.mAnnotIsMeasurement;
/* 1985 */     this.mPressedPoint.x = x;
/* 1986 */     this.mPressedPoint.y = y;
/* 1987 */     setLoupeInfo(e.getX(), e.getY());
/* 1988 */     animateLoupe(true);
/*      */     
/* 1990 */     if (this.mAnnotIsLine)
/*      */     {
/* 1992 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1997 */     if (this.mAnnot != null) {
/* 1998 */       this.mPageCropOnClientF = Utils.buildPageBoundBoxOnClient(this.mPdfViewCtrl, this.mAnnotPageNum);
/*      */     }
/*      */ 
/*      */     
/* 2002 */     this.mEffCtrlPtId = -1;
/* 2003 */     float thresh = this.mCtrlRadius * 2.25F;
/* 2004 */     float shortest_dist = -1.0F;
/* 2005 */     int pointsCnt = this.CTRL_PTS_CNT;
/* 2006 */     if (this.mAnnotIsSignature || this.mAnnotIsStamper) {
/* 2007 */       pointsCnt = 4;
/*      */     }
/* 2009 */     for (int i = 0; i < pointsCnt; i++) {
/* 2010 */       if (isAnnotResizable()) {
/*      */         
/* 2012 */         float s = (getVisualCtrlPts()[i]).x;
/* 2013 */         float t = (getVisualCtrlPts()[i]).y;
/*      */         
/* 2015 */         float dist = (x - s) * (x - s) + (y - t) * (y - t);
/* 2016 */         dist = (float)Math.sqrt(dist);
/* 2017 */         if (dist <= thresh && (dist < shortest_dist || shortest_dist < 0.0F)) {
/* 2018 */           this.mEffCtrlPtId = i;
/* 2019 */           shortest_dist = dist;
/*      */         } 
/*      */       } 
/*      */       
/* 2023 */       this.mCtrlPtsOnDown[i].set(this.mCtrlPts[i]);
/*      */     } 
/* 2025 */     this.mBBoxOnDown.set(this.mBBox);
/* 2026 */     if (this.mContentBox != null) {
/* 2027 */       if (this.mContentBoxOnDown == null) {
/* 2028 */         this.mContentBoxOnDown = new RectF();
/*      */       }
/* 2030 */       this.mContentBoxOnDown.set(this.mContentBox);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2035 */     if (!this.mAnnotIsTextMarkup && this.mEffCtrlPtId == -1 && this.mBBox.contains(x, y)) {
/* 2036 */       this.mEffCtrlPtId = -2;
/*      */     }
/*      */     
/* 2039 */     if (this.mAnnotView != null) {
/* 2040 */       this.mAnnotView.setActiveHandle(this.mEffCtrlPtId);
/*      */     }
/*      */     
/* 2043 */     if (this.mAnnot != null && 
/* 2044 */       !isInsideAnnot(e) && this.mEffCtrlPtId == -1 && (
/* 2045 */       this.mInlineEditText == null || !this.mInlineEditText.isEditing().booleanValue())) {
/* 2046 */       if (sDebug) Log.d(TAG, "going to unsetAnnot: onDown"); 
/* 2047 */       removeAnnotView(true);
/* 2048 */       unsetAnnot();
/* 2049 */       this.mNextToolMode = this.mCurrentDefaultToolMode;
/* 2050 */       setCtrlPts();
/*      */       
/* 2052 */       this.mPdfViewCtrl.invalidate((int)Math.floor(this.mBBox.left), (int)Math.floor(this.mBBox.top), (int)Math.ceil(this.mBBox.right), (int)Math.ceil(this.mBBox.bottom));
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2057 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onMove(MotionEvent e1, MotionEvent e2, float x_dist, float y_dist) {
/* 2065 */     if (this.mScaled)
/*      */     {
/* 2067 */       return false;
/*      */     }
/* 2069 */     if (!this.mHasSelectionPermission)
/*      */     {
/* 2071 */       return false;
/*      */     }
/*      */     
/* 2074 */     if (this.mEffCtrlPtId != -1) {
/* 2075 */       PointF snapPoint = snapToNearestIfEnabled(new PointF(e2.getX(), e2.getY()));
/*      */ 
/*      */       
/* 2078 */       float sx = this.mPdfViewCtrl.getScrollX();
/* 2079 */       float sy = this.mPdfViewCtrl.getScrollY();
/* 2080 */       snapPoint.x += sx;
/* 2081 */       snapPoint.y += sy;
/* 2082 */       setLoupeInfo(snapPoint.x, snapPoint.y);
/*      */       
/* 2084 */       float totalMoveX = snapPoint.x - e1.getX();
/* 2085 */       float totalMoveY = snapPoint.y - e1.getY();
/* 2086 */       float thresh = 2.0F * this.mCtrlRadius;
/* 2087 */       RectF tempRect = new RectF(this.mBBox);
/*      */       
/* 2089 */       float left = this.mBBoxOnDown.left + this.mCtrlRadius;
/* 2090 */       float right = this.mBBoxOnDown.right - this.mCtrlRadius;
/* 2091 */       float top = this.mBBoxOnDown.top + this.mCtrlRadius;
/* 2092 */       float bottom = this.mBBoxOnDown.bottom - this.mCtrlRadius;
/*      */       
/* 2094 */       if (this.mEffCtrlPtId == -2) {
/* 2095 */         left += totalMoveX;
/* 2096 */         right += totalMoveX;
/* 2097 */         top += totalMoveY;
/* 2098 */         bottom += totalMoveY;
/* 2099 */         updateCtrlPts(true, left, right, top, bottom, this.mBBox);
/* 2100 */         if (this.mContentBox != null) {
/*      */           
/* 2102 */           float diffLeft = this.mBBox.left - tempRect.left;
/* 2103 */           float diffRight = this.mBBox.right - tempRect.right;
/* 2104 */           float diffTop = this.mBBox.top - tempRect.top;
/* 2105 */           float diffBottom = this.mBBox.bottom - tempRect.bottom;
/* 2106 */           this.mContentBox.left += diffLeft;
/* 2107 */           this.mContentBox.right += diffRight;
/* 2108 */           this.mContentBox.top += diffTop;
/* 2109 */           this.mContentBox.bottom += diffBottom;
/*      */         } 
/* 2111 */         this.mModifiedAnnot = true;
/* 2112 */       } else if (!this.mHandleEffCtrlPtsDisabled) {
/* 2113 */         if (this.mContentBoxOnDown != null) {
/* 2114 */           left = this.mContentBoxOnDown.left + this.mCtrlRadius;
/* 2115 */           right = this.mContentBoxOnDown.right - this.mCtrlRadius;
/* 2116 */           top = this.mContentBoxOnDown.top + this.mCtrlRadius;
/* 2117 */           bottom = this.mContentBoxOnDown.bottom - this.mCtrlRadius;
/*      */         } 
/* 2119 */         boolean valid = false;
/* 2120 */         switch (this.mEffCtrlPtId) {
/*      */           case 0:
/* 2122 */             if ((this.mCtrlPtsOnDown[0]).x + totalMoveX < (this.mCtrlPtsOnDown[1]).x - thresh && (this.mCtrlPtsOnDown[0]).y + totalMoveY > (this.mCtrlPtsOnDown[3]).y + thresh) {
/* 2123 */               left = (this.mCtrlPtsOnDown[0]).x + totalMoveX;
/* 2124 */               if (this.mMaintainAspectRatio) {
/* 2125 */                 bottom = (this.mCtrlPtsOnDown[0]).y + totalMoveX * -1.0F * this.mAspectRatio;
/*      */               } else {
/* 2127 */                 bottom = (this.mCtrlPtsOnDown[0]).y + totalMoveY;
/*      */                 
/* 2129 */                 boolean snap = snapToAspectRatio(left, right, top, bottom);
/* 2130 */                 if (snap) {
/* 2131 */                   bottom = (this.mCtrlPtsOnDown[0]).y + totalMoveX * -1.0F * this.mAspectRatio;
/*      */                 }
/*      */               } 
/* 2134 */               valid = true;
/*      */             } 
/*      */             break;
/*      */           case 6:
/* 2138 */             if (!this.mMaintainAspectRatio && (this.mCtrlPtsOnDown[6]).y + totalMoveY > (this.mCtrlPtsOnDown[3]).y + thresh) {
/* 2139 */               bottom = (this.mCtrlPtsOnDown[6]).y + totalMoveY;
/* 2140 */               valid = true;
/*      */               
/* 2142 */               Float snap = snapToPerfectShape(left, right, top, bottom);
/* 2143 */               if (snap != null) {
/* 2144 */                 bottom = top + snap.floatValue();
/*      */               }
/*      */             } 
/*      */             break;
/*      */           case 1:
/* 2149 */             if ((this.mCtrlPtsOnDown[0]).x < (this.mCtrlPtsOnDown[1]).x + totalMoveX - thresh && (this.mCtrlPtsOnDown[0]).y + totalMoveY > (this.mCtrlPtsOnDown[3]).y + thresh) {
/* 2150 */               right = (this.mCtrlPtsOnDown[1]).x + totalMoveX;
/* 2151 */               if (this.mMaintainAspectRatio) {
/* 2152 */                 bottom = (this.mCtrlPtsOnDown[1]).y + totalMoveX * this.mAspectRatio;
/*      */               } else {
/* 2154 */                 bottom = (this.mCtrlPtsOnDown[1]).y + totalMoveY;
/*      */                 
/* 2156 */                 boolean snap = snapToAspectRatio(left, right, top, bottom);
/* 2157 */                 if (snap) {
/* 2158 */                   bottom = (this.mCtrlPtsOnDown[1]).y + totalMoveX * this.mAspectRatio;
/*      */                 }
/*      */               } 
/* 2161 */               valid = true;
/*      */             } 
/*      */             break;
/*      */           case 4:
/* 2165 */             if (!this.mMaintainAspectRatio && (this.mCtrlPtsOnDown[0]).x < (this.mCtrlPtsOnDown[1]).x + totalMoveX - thresh) {
/* 2166 */               right = (this.mCtrlPtsOnDown[4]).x + totalMoveX;
/* 2167 */               valid = true;
/*      */               
/* 2169 */               Float snap = snapToPerfectShape(left, right, top, bottom);
/* 2170 */               if (snap != null) {
/* 2171 */                 right = left + snap.floatValue();
/*      */               }
/*      */             } 
/*      */             break;
/*      */           case 2:
/* 2176 */             if ((this.mCtrlPtsOnDown[0]).x < (this.mCtrlPtsOnDown[1]).x + totalMoveX - thresh && (this.mCtrlPtsOnDown[0]).y > (this.mCtrlPtsOnDown[3]).y + totalMoveY + thresh) {
/* 2177 */               right = (this.mCtrlPtsOnDown[2]).x + totalMoveX;
/* 2178 */               if (this.mMaintainAspectRatio) {
/* 2179 */                 top = (this.mCtrlPtsOnDown[2]).y + totalMoveX * -1.0F * this.mAspectRatio;
/*      */               } else {
/* 2181 */                 top = (this.mCtrlPtsOnDown[2]).y + totalMoveY;
/*      */                 
/* 2183 */                 boolean snap = snapToAspectRatio(left, right, top, bottom);
/* 2184 */                 if (snap) {
/* 2185 */                   top = (this.mCtrlPtsOnDown[2]).y + totalMoveX * -1.0F * this.mAspectRatio;
/*      */                 }
/*      */               } 
/* 2188 */               valid = true;
/*      */             } 
/*      */             break;
/*      */           case 5:
/* 2192 */             if (!this.mMaintainAspectRatio && (this.mCtrlPtsOnDown[0]).y > (this.mCtrlPtsOnDown[3]).y + totalMoveY + thresh) {
/* 2193 */               top = (this.mCtrlPtsOnDown[5]).y + totalMoveY;
/* 2194 */               valid = true;
/*      */               
/* 2196 */               Float snap = snapToPerfectShape(left, right, top, bottom);
/* 2197 */               if (snap != null) {
/* 2198 */                 top = bottom - snap.floatValue();
/*      */               }
/*      */             } 
/*      */             break;
/*      */           case 3:
/* 2203 */             if ((this.mCtrlPtsOnDown[0]).x + totalMoveX < (this.mCtrlPtsOnDown[1]).x - thresh && (this.mCtrlPtsOnDown[0]).y > (this.mCtrlPtsOnDown[3]).y + totalMoveY + thresh) {
/* 2204 */               left = (this.mCtrlPtsOnDown[3]).x + totalMoveX;
/* 2205 */               if (this.mMaintainAspectRatio) {
/* 2206 */                 top = (this.mCtrlPtsOnDown[3]).y + totalMoveX * this.mAspectRatio;
/*      */               } else {
/* 2208 */                 top = (this.mCtrlPtsOnDown[3]).y + totalMoveY;
/*      */                 
/* 2210 */                 boolean snap = snapToAspectRatio(left, right, top, bottom);
/* 2211 */                 if (snap) {
/* 2212 */                   top = (this.mCtrlPtsOnDown[3]).y + totalMoveX * this.mAspectRatio;
/*      */                 }
/*      */               } 
/* 2215 */               valid = true;
/*      */             } 
/*      */             break;
/*      */           case 7:
/* 2219 */             if (!this.mMaintainAspectRatio && (this.mCtrlPtsOnDown[0]).x + totalMoveX < (this.mCtrlPtsOnDown[1]).x - thresh) {
/* 2220 */               left = (this.mCtrlPtsOnDown[7]).x + totalMoveX;
/* 2221 */               valid = true;
/*      */               
/* 2223 */               Float snap = snapToPerfectShape(left, right, top, bottom);
/* 2224 */               if (snap != null) {
/* 2225 */                 left = right - snap.floatValue();
/*      */               }
/*      */             } 
/*      */             break;
/*      */         } 
/*      */         
/* 2231 */         if (valid) {
/* 2232 */           if (this.mContentBox != null) {
/* 2233 */             updateCtrlPts(false, left, right, top, bottom, this.mContentBox);
/*      */           } else {
/* 2235 */             updateCtrlPts(false, left, right, top, bottom, this.mBBox);
/*      */           } 
/* 2237 */           this.mModifiedAnnot = true;
/*      */         } 
/*      */       } 
/*      */       
/* 2241 */       float min_x = Math.min(tempRect.left, this.mBBox.left);
/* 2242 */       float max_x = Math.max(tempRect.right, this.mBBox.right);
/* 2243 */       float min_y = Math.min(tempRect.top, this.mBBox.top);
/* 2244 */       float max_y = Math.max(tempRect.bottom, this.mBBox.bottom);
/* 2245 */       this.mPdfViewCtrl.invalidate((int)min_x - 1, (int)min_y - 1, (int)Math.ceil(max_x) + 1, (int)Math.ceil(max_y) + 1);
/* 2246 */       return true;
/*      */     } 
/* 2248 */     showTransientPageNumber();
/* 2249 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onUp(MotionEvent e, PDFViewCtrl.PriorEventMode priorEventMode) {
/* 2258 */     animateLoupe(false);
/* 2259 */     super.onUp(e, priorEventMode);
/* 2260 */     if (sDebug) Log.d(TAG, "onUp");
/*      */     
/* 2262 */     if (this.mAnnotView != null) {
/* 2263 */       this.mAnnotView.setActiveHandle(-1);
/*      */     }
/*      */ 
/*      */     
/* 2267 */     if (this.mUpFromStickyCreateDlgShown) {
/* 2268 */       return false;
/*      */     }
/*      */     
/* 2271 */     if (this.mUpFromCalloutCreate) {
/* 2272 */       this.mUpFromCalloutCreate = false;
/* 2273 */       closeQuickMenu();
/* 2274 */       enterText();
/*      */       
/* 2276 */       this.mNextToolMode = getToolMode();
/* 2277 */       return false;
/*      */     } 
/*      */     
/* 2280 */     if (this.mAnnotIsLine)
/*      */     {
/* 2282 */       return false;
/*      */     }
/*      */     
/* 2285 */     if (this.mScaled) {
/* 2286 */       this.mScaled = false;
/* 2287 */       if (this.mAnnot != null && 
/* 2288 */         this.mModifiedAnnot) {
/* 2289 */         this.mModifiedAnnot = false;
/*      */       }
/*      */       
/* 2292 */       return false;
/*      */     } 
/*      */     
/* 2295 */     if (this.mSaveFreeTextAnnotInOnUp) {
/* 2296 */       saveAndQuitInlineEditText(false);
/* 2297 */       this.mSaveFreeTextAnnotInOnUp = false;
/* 2298 */       ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 2299 */       if (toolManager.isEditFreeTextOnTap()) {
/* 2300 */         unsetAnnot();
/*      */       }
/*      */     } 
/*      */     
/* 2304 */     if (!this.mHasMenuPermission && this.mAnnot != null) {
/* 2305 */       showMenu(getAnnotRect());
/*      */     }
/*      */     
/* 2308 */     this.mNextToolMode = getToolMode();
/* 2309 */     this.mScaled = false;
/*      */     
/* 2311 */     if (hasAnnotSelected() && (this.mModifiedAnnot || !this.mCtrlPtsSet || priorEventMode == PDFViewCtrl.PriorEventMode.SCROLLING || priorEventMode == PDFViewCtrl.PriorEventMode.PINCH || priorEventMode == PDFViewCtrl.PriorEventMode.DOUBLE_TAP)) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2316 */       if (!this.mCtrlPtsSet) {
/* 2317 */         setCtrlPts();
/*      */       }
/*      */       
/* 2320 */       if (!editAnnotSize(priorEventMode)) {
/* 2321 */         return false;
/*      */       }
/*      */       
/* 2324 */       showMenu(getAnnotRect());
/*      */       
/* 2326 */       if (-2 != this.mEffCtrlPtId) {
/* 2327 */         updateAnnotViewBitmap();
/* 2328 */         if (this.mAnnotView != null && this.mAnnotView.getDrawingView() != null) {
/* 2329 */           this.mAnnotView.snapToPerfectShape(null);
/*      */         }
/*      */       } 
/*      */       
/* 2333 */       return (priorEventMode == PDFViewCtrl.PriorEventMode.SCROLLING || priorEventMode == PDFViewCtrl.PriorEventMode.FLING);
/*      */     } 
/* 2335 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean snapToAspectRatio(float left, float right, float top, float bottom) {
/* 2343 */     if (!this.mSnapEnabled) {
/* 2344 */       return false;
/*      */     }
/*      */     
/* 2347 */     float width = right - left;
/* 2348 */     float height = bottom - top;
/*      */     
/* 2350 */     float aspectRatio = height / width;
/*      */     
/* 2352 */     if (Math.abs(aspectRatio - this.mAspectRatio) < 0.1F) {
/* 2353 */       switch (this.mEffCtrlPtId) {
/*      */         case 0:
/*      */         case 2:
/* 2356 */           if (this.mAnnotView != null && this.mAnnotView.getDrawingView() != null) {
/* 2357 */             this.mAnnotView.snapToPerfectShape(AnnotView.SnapMode.ASPECT_RATIO_L);
/*      */           }
/* 2359 */           return true;
/*      */         case 1:
/*      */         case 3:
/* 2362 */           if (this.mAnnotView != null && this.mAnnotView.getDrawingView() != null) {
/* 2363 */             this.mAnnotView.snapToPerfectShape(AnnotView.SnapMode.ASPECT_RATIO_R);
/*      */           }
/* 2365 */           return true;
/*      */       } 
/*      */     }
/* 2368 */     if (this.mAnnotView != null && this.mAnnotView.getDrawingView() != null) {
/* 2369 */       this.mAnnotView.snapToPerfectShape(null);
/*      */     }
/* 2371 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Float snapToPerfectShape(float left, float right, float top, float bottom) {
/* 2378 */     if (!this.mSnapEnabled) {
/* 2379 */       return null;
/*      */     }
/*      */     
/* 2382 */     float width = right - left;
/* 2383 */     float height = bottom - top;
/*      */     
/* 2385 */     if (Math.abs(width - height) < this.mSnapThreshold) {
/* 2386 */       switch (this.mEffCtrlPtId) {
/*      */         case 5:
/*      */         case 6:
/* 2389 */           if (this.mAnnotView != null && this.mAnnotView.getDrawingView() != null) {
/* 2390 */             this.mAnnotView.snapToPerfectShape(AnnotView.SnapMode.VERTICAL);
/*      */           }
/* 2392 */           return Float.valueOf(width);
/*      */         case 4:
/*      */         case 7:
/* 2395 */           if (this.mAnnotView != null && this.mAnnotView.getDrawingView() != null) {
/* 2396 */             this.mAnnotView.snapToPerfectShape(AnnotView.SnapMode.HORIZONTAL);
/*      */           }
/* 2398 */           return Float.valueOf(height);
/*      */       } 
/*      */     }
/* 2401 */     if (this.mAnnotView != null && this.mAnnotView.getDrawingView() != null) {
/* 2402 */       this.mAnnotView.snapToPerfectShape(null);
/*      */     }
/* 2404 */     return null;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateCtrlPts(boolean translate, float left, float right, float top, float bottom, RectF which) {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getfield mPageCropOnClientF : Landroid/graphics/RectF;
/*      */     //   4: ifnull -> 369
/*      */     //   7: fload_3
/*      */     //   8: fload_2
/*      */     //   9: fsub
/*      */     //   10: fstore #7
/*      */     //   12: fload #5
/*      */     //   14: fload #4
/*      */     //   16: fsub
/*      */     //   17: fstore #8
/*      */     //   19: fload_3
/*      */     //   20: aload_0
/*      */     //   21: getfield mPageCropOnClientF : Landroid/graphics/RectF;
/*      */     //   24: getfield right : F
/*      */     //   27: fcmpl
/*      */     //   28: ifle -> 105
/*      */     //   31: aload_0
/*      */     //   32: getfield mPageCropOnClientF : Landroid/graphics/RectF;
/*      */     //   35: getfield right : F
/*      */     //   38: fstore_3
/*      */     //   39: iload_1
/*      */     //   40: ifeq -> 51
/*      */     //   43: fload_3
/*      */     //   44: fload #7
/*      */     //   46: fsub
/*      */     //   47: fstore_2
/*      */     //   48: goto -> 105
/*      */     //   51: aload_0
/*      */     //   52: getfield mMaintainAspectRatio : Z
/*      */     //   55: ifeq -> 105
/*      */     //   58: fload_3
/*      */     //   59: fload_2
/*      */     //   60: fsub
/*      */     //   61: fstore #9
/*      */     //   63: fload #9
/*      */     //   65: aload_0
/*      */     //   66: getfield mAspectRatio : F
/*      */     //   69: fmul
/*      */     //   70: fstore #10
/*      */     //   72: aload_0
/*      */     //   73: getfield mEffCtrlPtId : I
/*      */     //   76: iconst_1
/*      */     //   77: if_icmpne -> 90
/*      */     //   80: fload #4
/*      */     //   82: fload #10
/*      */     //   84: fadd
/*      */     //   85: fstore #5
/*      */     //   87: goto -> 105
/*      */     //   90: aload_0
/*      */     //   91: getfield mEffCtrlPtId : I
/*      */     //   94: iconst_2
/*      */     //   95: if_icmpne -> 105
/*      */     //   98: fload #5
/*      */     //   100: fload #10
/*      */     //   102: fsub
/*      */     //   103: fstore #4
/*      */     //   105: fload_2
/*      */     //   106: aload_0
/*      */     //   107: getfield mPageCropOnClientF : Landroid/graphics/RectF;
/*      */     //   110: getfield left : F
/*      */     //   113: fcmpg
/*      */     //   114: ifge -> 190
/*      */     //   117: aload_0
/*      */     //   118: getfield mPageCropOnClientF : Landroid/graphics/RectF;
/*      */     //   121: getfield left : F
/*      */     //   124: fstore_2
/*      */     //   125: iload_1
/*      */     //   126: ifeq -> 137
/*      */     //   129: fload_2
/*      */     //   130: fload #7
/*      */     //   132: fadd
/*      */     //   133: fstore_3
/*      */     //   134: goto -> 190
/*      */     //   137: aload_0
/*      */     //   138: getfield mMaintainAspectRatio : Z
/*      */     //   141: ifeq -> 190
/*      */     //   144: fload_3
/*      */     //   145: fload_2
/*      */     //   146: fsub
/*      */     //   147: fstore #9
/*      */     //   149: fload #9
/*      */     //   151: aload_0
/*      */     //   152: getfield mAspectRatio : F
/*      */     //   155: fmul
/*      */     //   156: fstore #10
/*      */     //   158: aload_0
/*      */     //   159: getfield mEffCtrlPtId : I
/*      */     //   162: ifne -> 175
/*      */     //   165: fload #4
/*      */     //   167: fload #10
/*      */     //   169: fadd
/*      */     //   170: fstore #5
/*      */     //   172: goto -> 190
/*      */     //   175: aload_0
/*      */     //   176: getfield mEffCtrlPtId : I
/*      */     //   179: iconst_3
/*      */     //   180: if_icmpne -> 190
/*      */     //   183: fload #5
/*      */     //   185: fload #10
/*      */     //   187: fsub
/*      */     //   188: fstore #4
/*      */     //   190: fload #4
/*      */     //   192: aload_0
/*      */     //   193: getfield mPageCropOnClientF : Landroid/graphics/RectF;
/*      */     //   196: getfield top : F
/*      */     //   199: fcmpg
/*      */     //   200: ifge -> 280
/*      */     //   203: aload_0
/*      */     //   204: getfield mPageCropOnClientF : Landroid/graphics/RectF;
/*      */     //   207: getfield top : F
/*      */     //   210: fstore #4
/*      */     //   212: iload_1
/*      */     //   213: ifeq -> 226
/*      */     //   216: fload #4
/*      */     //   218: fload #8
/*      */     //   220: fadd
/*      */     //   221: fstore #5
/*      */     //   223: goto -> 280
/*      */     //   226: aload_0
/*      */     //   227: getfield mMaintainAspectRatio : Z
/*      */     //   230: ifeq -> 280
/*      */     //   233: fload #5
/*      */     //   235: fload #4
/*      */     //   237: fsub
/*      */     //   238: fstore #9
/*      */     //   240: fload #9
/*      */     //   242: fconst_1
/*      */     //   243: aload_0
/*      */     //   244: getfield mAspectRatio : F
/*      */     //   247: fdiv
/*      */     //   248: fmul
/*      */     //   249: fstore #10
/*      */     //   251: aload_0
/*      */     //   252: getfield mEffCtrlPtId : I
/*      */     //   255: iconst_3
/*      */     //   256: if_icmpne -> 267
/*      */     //   259: fload_3
/*      */     //   260: fload #10
/*      */     //   262: fsub
/*      */     //   263: fstore_2
/*      */     //   264: goto -> 280
/*      */     //   267: aload_0
/*      */     //   268: getfield mEffCtrlPtId : I
/*      */     //   271: iconst_2
/*      */     //   272: if_icmpne -> 280
/*      */     //   275: fload_2
/*      */     //   276: fload #10
/*      */     //   278: fadd
/*      */     //   279: fstore_3
/*      */     //   280: fload #5
/*      */     //   282: aload_0
/*      */     //   283: getfield mPageCropOnClientF : Landroid/graphics/RectF;
/*      */     //   286: getfield bottom : F
/*      */     //   289: fcmpl
/*      */     //   290: ifle -> 369
/*      */     //   293: aload_0
/*      */     //   294: getfield mPageCropOnClientF : Landroid/graphics/RectF;
/*      */     //   297: getfield bottom : F
/*      */     //   300: fstore #5
/*      */     //   302: iload_1
/*      */     //   303: ifeq -> 316
/*      */     //   306: fload #5
/*      */     //   308: fload #8
/*      */     //   310: fsub
/*      */     //   311: fstore #4
/*      */     //   313: goto -> 369
/*      */     //   316: aload_0
/*      */     //   317: getfield mMaintainAspectRatio : Z
/*      */     //   320: ifeq -> 369
/*      */     //   323: fload #5
/*      */     //   325: fload #4
/*      */     //   327: fsub
/*      */     //   328: fstore #9
/*      */     //   330: fload #9
/*      */     //   332: fconst_1
/*      */     //   333: aload_0
/*      */     //   334: getfield mAspectRatio : F
/*      */     //   337: fdiv
/*      */     //   338: fmul
/*      */     //   339: fstore #10
/*      */     //   341: aload_0
/*      */     //   342: getfield mEffCtrlPtId : I
/*      */     //   345: ifne -> 356
/*      */     //   348: fload_3
/*      */     //   349: fload #10
/*      */     //   351: fsub
/*      */     //   352: fstore_2
/*      */     //   353: goto -> 369
/*      */     //   356: aload_0
/*      */     //   357: getfield mEffCtrlPtId : I
/*      */     //   360: iconst_1
/*      */     //   361: if_icmpne -> 369
/*      */     //   364: fload_2
/*      */     //   365: fload #10
/*      */     //   367: fadd
/*      */     //   368: fstore_3
/*      */     //   369: aload_0
/*      */     //   370: fload_2
/*      */     //   371: fload #4
/*      */     //   373: fload_3
/*      */     //   374: fload #5
/*      */     //   376: invokevirtual updateAnnotView : (FFFF)V
/*      */     //   379: aload_0
/*      */     //   380: fload_2
/*      */     //   381: fload #4
/*      */     //   383: fload_3
/*      */     //   384: fload #5
/*      */     //   386: invokevirtual updateRotateView : (FFFF)V
/*      */     //   389: aload_0
/*      */     //   390: getfield mHandleEffCtrlPtsDisabled : Z
/*      */     //   393: ifne -> 823
/*      */     //   396: aload_0
/*      */     //   397: getfield mEffCtrlPtId : I
/*      */     //   400: bipush #8
/*      */     //   402: if_icmpge -> 823
/*      */     //   405: aload_0
/*      */     //   406: getfield mCtrlPts : [Landroid/graphics/PointF;
/*      */     //   409: iconst_0
/*      */     //   410: aaload
/*      */     //   411: aload_0
/*      */     //   412: getfield mCtrlPts : [Landroid/graphics/PointF;
/*      */     //   415: iconst_3
/*      */     //   416: aaload
/*      */     //   417: aload_0
/*      */     //   418: getfield mCtrlPts : [Landroid/graphics/PointF;
/*      */     //   421: bipush #7
/*      */     //   423: aaload
/*      */     //   424: fload_2
/*      */     //   425: dup_x1
/*      */     //   426: putfield x : F
/*      */     //   429: dup_x1
/*      */     //   430: putfield x : F
/*      */     //   433: putfield x : F
/*      */     //   436: aload_0
/*      */     //   437: getfield mCtrlPts : [Landroid/graphics/PointF;
/*      */     //   440: iconst_1
/*      */     //   441: aaload
/*      */     //   442: aload_0
/*      */     //   443: getfield mCtrlPts : [Landroid/graphics/PointF;
/*      */     //   446: iconst_2
/*      */     //   447: aaload
/*      */     //   448: aload_0
/*      */     //   449: getfield mCtrlPts : [Landroid/graphics/PointF;
/*      */     //   452: iconst_4
/*      */     //   453: aaload
/*      */     //   454: fload_3
/*      */     //   455: dup_x1
/*      */     //   456: putfield x : F
/*      */     //   459: dup_x1
/*      */     //   460: putfield x : F
/*      */     //   463: putfield x : F
/*      */     //   466: aload_0
/*      */     //   467: getfield mCtrlPts : [Landroid/graphics/PointF;
/*      */     //   470: iconst_0
/*      */     //   471: aaload
/*      */     //   472: aload_0
/*      */     //   473: getfield mCtrlPts : [Landroid/graphics/PointF;
/*      */     //   476: iconst_1
/*      */     //   477: aaload
/*      */     //   478: aload_0
/*      */     //   479: getfield mCtrlPts : [Landroid/graphics/PointF;
/*      */     //   482: bipush #6
/*      */     //   484: aaload
/*      */     //   485: fload #5
/*      */     //   487: dup_x1
/*      */     //   488: putfield y : F
/*      */     //   491: dup_x1
/*      */     //   492: putfield y : F
/*      */     //   495: putfield y : F
/*      */     //   498: aload_0
/*      */     //   499: getfield mCtrlPts : [Landroid/graphics/PointF;
/*      */     //   502: iconst_2
/*      */     //   503: aaload
/*      */     //   504: aload_0
/*      */     //   505: getfield mCtrlPts : [Landroid/graphics/PointF;
/*      */     //   508: iconst_3
/*      */     //   509: aaload
/*      */     //   510: aload_0
/*      */     //   511: getfield mCtrlPts : [Landroid/graphics/PointF;
/*      */     //   514: iconst_5
/*      */     //   515: aaload
/*      */     //   516: fload #4
/*      */     //   518: dup_x1
/*      */     //   519: putfield y : F
/*      */     //   522: dup_x1
/*      */     //   523: putfield y : F
/*      */     //   526: putfield y : F
/*      */     //   529: aload_0
/*      */     //   530: getfield mCtrlPts : [Landroid/graphics/PointF;
/*      */     //   533: bipush #7
/*      */     //   535: aaload
/*      */     //   536: aload_0
/*      */     //   537: getfield mCtrlPts : [Landroid/graphics/PointF;
/*      */     //   540: iconst_4
/*      */     //   541: aaload
/*      */     //   542: fload #5
/*      */     //   544: fload #4
/*      */     //   546: fadd
/*      */     //   547: fconst_2
/*      */     //   548: fdiv
/*      */     //   549: dup_x1
/*      */     //   550: putfield y : F
/*      */     //   553: putfield y : F
/*      */     //   556: aload_0
/*      */     //   557: getfield mCtrlPts : [Landroid/graphics/PointF;
/*      */     //   560: bipush #6
/*      */     //   562: aaload
/*      */     //   563: aload_0
/*      */     //   564: getfield mCtrlPts : [Landroid/graphics/PointF;
/*      */     //   567: iconst_5
/*      */     //   568: aaload
/*      */     //   569: fload_2
/*      */     //   570: fload_3
/*      */     //   571: fadd
/*      */     //   572: fconst_2
/*      */     //   573: fdiv
/*      */     //   574: dup_x1
/*      */     //   575: putfield x : F
/*      */     //   578: putfield x : F
/*      */     //   581: aload_0
/*      */     //   582: getfield mCtrlPtsInflated : [Landroid/graphics/PointF;
/*      */     //   585: iconst_0
/*      */     //   586: aaload
/*      */     //   587: aload_0
/*      */     //   588: getfield mCtrlPtsInflated : [Landroid/graphics/PointF;
/*      */     //   591: iconst_3
/*      */     //   592: aaload
/*      */     //   593: aload_0
/*      */     //   594: getfield mCtrlPtsInflated : [Landroid/graphics/PointF;
/*      */     //   597: bipush #7
/*      */     //   599: aaload
/*      */     //   600: aload_0
/*      */     //   601: getfield mCtrlPts : [Landroid/graphics/PointF;
/*      */     //   604: iconst_0
/*      */     //   605: aaload
/*      */     //   606: getfield x : F
/*      */     //   609: aload_0
/*      */     //   610: getfield mSelectionBoxMargin : I
/*      */     //   613: i2f
/*      */     //   614: fsub
/*      */     //   615: dup_x1
/*      */     //   616: putfield x : F
/*      */     //   619: dup_x1
/*      */     //   620: putfield x : F
/*      */     //   623: putfield x : F
/*      */     //   626: aload_0
/*      */     //   627: getfield mCtrlPtsInflated : [Landroid/graphics/PointF;
/*      */     //   630: iconst_1
/*      */     //   631: aaload
/*      */     //   632: aload_0
/*      */     //   633: getfield mCtrlPtsInflated : [Landroid/graphics/PointF;
/*      */     //   636: iconst_2
/*      */     //   637: aaload
/*      */     //   638: aload_0
/*      */     //   639: getfield mCtrlPtsInflated : [Landroid/graphics/PointF;
/*      */     //   642: iconst_4
/*      */     //   643: aaload
/*      */     //   644: aload_0
/*      */     //   645: getfield mCtrlPts : [Landroid/graphics/PointF;
/*      */     //   648: iconst_1
/*      */     //   649: aaload
/*      */     //   650: getfield x : F
/*      */     //   653: aload_0
/*      */     //   654: getfield mSelectionBoxMargin : I
/*      */     //   657: i2f
/*      */     //   658: fadd
/*      */     //   659: dup_x1
/*      */     //   660: putfield x : F
/*      */     //   663: dup_x1
/*      */     //   664: putfield x : F
/*      */     //   667: putfield x : F
/*      */     //   670: aload_0
/*      */     //   671: getfield mCtrlPtsInflated : [Landroid/graphics/PointF;
/*      */     //   674: iconst_0
/*      */     //   675: aaload
/*      */     //   676: aload_0
/*      */     //   677: getfield mCtrlPtsInflated : [Landroid/graphics/PointF;
/*      */     //   680: iconst_1
/*      */     //   681: aaload
/*      */     //   682: aload_0
/*      */     //   683: getfield mCtrlPtsInflated : [Landroid/graphics/PointF;
/*      */     //   686: bipush #6
/*      */     //   688: aaload
/*      */     //   689: aload_0
/*      */     //   690: getfield mCtrlPts : [Landroid/graphics/PointF;
/*      */     //   693: iconst_0
/*      */     //   694: aaload
/*      */     //   695: getfield y : F
/*      */     //   698: aload_0
/*      */     //   699: getfield mSelectionBoxMargin : I
/*      */     //   702: i2f
/*      */     //   703: fadd
/*      */     //   704: dup_x1
/*      */     //   705: putfield y : F
/*      */     //   708: dup_x1
/*      */     //   709: putfield y : F
/*      */     //   712: putfield y : F
/*      */     //   715: aload_0
/*      */     //   716: getfield mCtrlPtsInflated : [Landroid/graphics/PointF;
/*      */     //   719: iconst_2
/*      */     //   720: aaload
/*      */     //   721: aload_0
/*      */     //   722: getfield mCtrlPtsInflated : [Landroid/graphics/PointF;
/*      */     //   725: iconst_3
/*      */     //   726: aaload
/*      */     //   727: aload_0
/*      */     //   728: getfield mCtrlPtsInflated : [Landroid/graphics/PointF;
/*      */     //   731: iconst_5
/*      */     //   732: aaload
/*      */     //   733: aload_0
/*      */     //   734: getfield mCtrlPts : [Landroid/graphics/PointF;
/*      */     //   737: iconst_2
/*      */     //   738: aaload
/*      */     //   739: getfield y : F
/*      */     //   742: aload_0
/*      */     //   743: getfield mSelectionBoxMargin : I
/*      */     //   746: i2f
/*      */     //   747: fsub
/*      */     //   748: dup_x1
/*      */     //   749: putfield y : F
/*      */     //   752: dup_x1
/*      */     //   753: putfield y : F
/*      */     //   756: putfield y : F
/*      */     //   759: aload_0
/*      */     //   760: getfield mCtrlPtsInflated : [Landroid/graphics/PointF;
/*      */     //   763: bipush #7
/*      */     //   765: aaload
/*      */     //   766: aload_0
/*      */     //   767: getfield mCtrlPtsInflated : [Landroid/graphics/PointF;
/*      */     //   770: iconst_4
/*      */     //   771: aaload
/*      */     //   772: aload_0
/*      */     //   773: getfield mCtrlPts : [Landroid/graphics/PointF;
/*      */     //   776: bipush #7
/*      */     //   778: aaload
/*      */     //   779: getfield y : F
/*      */     //   782: dup_x1
/*      */     //   783: putfield y : F
/*      */     //   786: putfield y : F
/*      */     //   789: aload_0
/*      */     //   790: getfield mCtrlPtsInflated : [Landroid/graphics/PointF;
/*      */     //   793: bipush #6
/*      */     //   795: aaload
/*      */     //   796: aload_0
/*      */     //   797: getfield mCtrlPtsInflated : [Landroid/graphics/PointF;
/*      */     //   800: iconst_5
/*      */     //   801: aaload
/*      */     //   802: aload_0
/*      */     //   803: getfield mCtrlPts : [Landroid/graphics/PointF;
/*      */     //   806: bipush #6
/*      */     //   808: aaload
/*      */     //   809: getfield x : F
/*      */     //   812: dup_x1
/*      */     //   813: putfield x : F
/*      */     //   816: putfield x : F
/*      */     //   819: aload_0
/*      */     //   820: invokevirtual updateAnnotViewCtrlPt : ()V
/*      */     //   823: aload #6
/*      */     //   825: ifnull -> 874
/*      */     //   828: aload #6
/*      */     //   830: fload_2
/*      */     //   831: aload_0
/*      */     //   832: getfield mCtrlRadius : F
/*      */     //   835: fsub
/*      */     //   836: putfield left : F
/*      */     //   839: aload #6
/*      */     //   841: fload #4
/*      */     //   843: aload_0
/*      */     //   844: getfield mCtrlRadius : F
/*      */     //   847: fsub
/*      */     //   848: putfield top : F
/*      */     //   851: aload #6
/*      */     //   853: fload_3
/*      */     //   854: aload_0
/*      */     //   855: getfield mCtrlRadius : F
/*      */     //   858: fadd
/*      */     //   859: putfield right : F
/*      */     //   862: aload #6
/*      */     //   864: fload #5
/*      */     //   866: aload_0
/*      */     //   867: getfield mCtrlRadius : F
/*      */     //   870: fadd
/*      */     //   871: putfield bottom : F
/*      */     //   874: return
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #2422	-> 0
/*      */     //   #2423	-> 7
/*      */     //   #2424	-> 12
/*      */     //   #2427	-> 19
/*      */     //   #2428	-> 31
/*      */     //   #2429	-> 39
/*      */     //   #2430	-> 43
/*      */     //   #2431	-> 51
/*      */     //   #2435	-> 58
/*      */     //   #2436	-> 63
/*      */     //   #2438	-> 72
/*      */     //   #2440	-> 80
/*      */     //   #2441	-> 90
/*      */     //   #2443	-> 98
/*      */     //   #2447	-> 105
/*      */     //   #2448	-> 117
/*      */     //   #2449	-> 125
/*      */     //   #2450	-> 129
/*      */     //   #2451	-> 137
/*      */     //   #2455	-> 144
/*      */     //   #2456	-> 149
/*      */     //   #2458	-> 158
/*      */     //   #2460	-> 165
/*      */     //   #2461	-> 175
/*      */     //   #2463	-> 183
/*      */     //   #2469	-> 190
/*      */     //   #2470	-> 203
/*      */     //   #2471	-> 212
/*      */     //   #2472	-> 216
/*      */     //   #2473	-> 226
/*      */     //   #2477	-> 233
/*      */     //   #2478	-> 240
/*      */     //   #2480	-> 251
/*      */     //   #2482	-> 259
/*      */     //   #2483	-> 267
/*      */     //   #2485	-> 275
/*      */     //   #2489	-> 280
/*      */     //   #2490	-> 293
/*      */     //   #2491	-> 302
/*      */     //   #2492	-> 306
/*      */     //   #2493	-> 316
/*      */     //   #2497	-> 323
/*      */     //   #2498	-> 330
/*      */     //   #2500	-> 341
/*      */     //   #2502	-> 348
/*      */     //   #2503	-> 356
/*      */     //   #2505	-> 364
/*      */     //   #2511	-> 369
/*      */     //   #2516	-> 379
/*      */     //   #2521	-> 389
/*      */     //   #2523	-> 405
/*      */     //   #2524	-> 436
/*      */     //   #2525	-> 466
/*      */     //   #2526	-> 498
/*      */     //   #2527	-> 529
/*      */     //   #2528	-> 556
/*      */     //   #2531	-> 581
/*      */     //   #2532	-> 626
/*      */     //   #2533	-> 670
/*      */     //   #2534	-> 715
/*      */     //   #2535	-> 759
/*      */     //   #2536	-> 789
/*      */     //   #2538	-> 819
/*      */     //   #2542	-> 823
/*      */     //   #2543	-> 828
/*      */     //   #2544	-> 839
/*      */     //   #2545	-> 851
/*      */     //   #2546	-> 862
/*      */     //   #2548	-> 874
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   63	42	9	width	F
/*      */     //   72	33	10	height	F
/*      */     //   149	41	9	width	F
/*      */     //   158	32	10	height	F
/*      */     //   240	40	9	height	F
/*      */     //   251	29	10	width	F
/*      */     //   330	39	9	height	F
/*      */     //   341	28	10	width	F
/*      */     //   12	357	7	w	F
/*      */     //   19	350	8	h	F
/*      */     //   0	875	0	this	Lcom/pdftron/pdf/tools/AnnotEdit;
/*      */     //   0	875	1	translate	Z
/*      */     //   0	875	2	left	F
/*      */     //   0	875	3	right	F
/*      */     //   0	875	4	top	F
/*      */     //   0	875	5	bottom	F
/*      */     //   0	875	6	which	Landroid/graphics/RectF;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateAnnotView(float left, float top, float right, float bottom) {
/* 2551 */     if (this.mAnnotView != null) {
/* 2552 */       int xOffset = this.mPdfViewCtrl.getScrollX();
/* 2553 */       int yOffset = this.mPdfViewCtrl.getScrollY();
/* 2554 */       this.mAnnotView.setAnnotRect(new RectF(left - xOffset, top - yOffset, right - xOffset, bottom - yOffset));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2560 */       this.mAnnotView.layout(xOffset, yOffset, xOffset + this.mPdfViewCtrl
/*      */           
/* 2562 */           .getWidth(), yOffset + this.mPdfViewCtrl
/* 2563 */           .getHeight());
/* 2564 */       this.mAnnotView.invalidate();
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void updateRotateView(float min_x, float min_y, float max_x, float max_y) {
/* 2569 */     if (this.mRotateHandle != null) {
/* 2570 */       int size = this.mPdfViewCtrl.getContext().getResources().getDimensionPixelSize(R.dimen.rotate_button_size_w_margin);
/* 2571 */       int left = (int)(((max_x - min_x) / 2.0F + min_x) - size / 2.0D + 0.5D);
/* 2572 */       int top = (int)((max_y + size) + 0.5D);
/*      */       
/* 2574 */       if (this.mPageCropOnClientF != null)
/*      */       {
/* 2576 */         if ((top + size) > this.mPageCropOnClientF.bottom || min_y - this.mPageCropOnClientF.top < (size * 2)) {
/* 2577 */           top = (int)(((max_y - min_y) / 2.0F + min_y) - size / 2.0D + 0.5D);
/* 2578 */           if (min_x <= this.mPageCropOnClientF.centerX() && this.mPageCropOnClientF.right - max_x > (size * 2)) {
/* 2579 */             left = (int)(Math.min(max_x + size, this.mPageCropOnClientF.right - size) + 0.5D);
/*      */           } else {
/* 2581 */             left = (int)(Math.max(min_x - (size * 2), this.mPageCropOnClientF.left) + 0.5D);
/*      */           } 
/*      */         } 
/*      */       }
/*      */       
/* 2586 */       updateRotateView(left, top);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void updateRotateView(int left, int top) {
/* 2591 */     if (this.mRotateHandle != null) {
/* 2592 */       int size = this.mPdfViewCtrl.getContext().getResources().getDimensionPixelSize(R.dimen.rotate_button_size_w_margin);
/* 2593 */       this.mRotateHandle.layout(left, top, left + size, top + size);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateAnnotViewCtrlPt() {
/* 2601 */     if (this.mAnnotView != null) {
/* 2602 */       int xOffset = this.mPdfViewCtrl.getScrollX();
/* 2603 */       int yOffset = this.mPdfViewCtrl.getScrollY();
/* 2604 */       PointF[] ctrlPts = new PointF[this.CTRL_PTS_CNT];
/* 2605 */       ctrlPts[3] = new PointF((this.mCtrlPtsInflated[3]).x - xOffset, (this.mCtrlPtsInflated[3]).y - yOffset);
/* 2606 */       ctrlPts[1] = new PointF((this.mCtrlPtsInflated[1]).x - xOffset, (this.mCtrlPtsInflated[1]).y - yOffset);
/* 2607 */       ctrlPts[6] = new PointF((this.mCtrlPtsInflated[6]).x - xOffset, (this.mCtrlPtsInflated[6]).y - yOffset);
/* 2608 */       ctrlPts[7] = new PointF((this.mCtrlPtsInflated[7]).x - xOffset, (this.mCtrlPtsInflated[7]).y - yOffset);
/* 2609 */       this.mAnnotView.setCtrlPts(ctrlPts);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onConfigurationChanged(Configuration newConfig) {
/* 2618 */     super.onConfigurationChanged(newConfig);
/*      */     
/* 2620 */     if (this.mInlineEditText != null && this.mInlineEditText.isEditing().booleanValue()) {
/* 2621 */       saveAndQuitInlineEditText(false);
/* 2622 */       closeQuickMenu();
/*      */     } 
/*      */     
/* 2625 */     ViewTreeObserver observer = this.mPdfViewCtrl.getViewTreeObserver();
/* 2626 */     observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
/*      */         {
/*      */           public void onGlobalLayout()
/*      */           {
/* 2630 */             AnnotEdit.this.mPdfViewCtrl.getViewTreeObserver().removeOnGlobalLayoutListener(this);
/* 2631 */             if (AnnotEdit.this.mQuickMenu != null && AnnotEdit.this.mQuickMenu.isShowing()) {
/* 2632 */               AnnotEdit.this.mQuickMenu.requestLocation();
/*      */             }
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isCreatingAnnotation() {
/* 2643 */     return (this.mInlineEditText != null && this.mInlineEditText.isEditing().booleanValue());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onAnnotButtonPressed(int button) {
/* 2651 */     this.mAnnotButtonPressed = button;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean showMenu(RectF anchor_rect, QuickMenu quickMenu) {
/* 2659 */     return (!onInterceptAnnotationHandling(this.mAnnot) && super.showMenu(anchor_rect, quickMenu));
/*      */   }
/*      */   
/*      */   private void deleteStickyAnnot() {
/* 2663 */     if (this.mAnnot == null || onInterceptAnnotationHandling(this.mAnnot)) {
/*      */       return;
/*      */     }
/* 2666 */     boolean shouldUnlock = false;
/*      */ 
/*      */     
/*      */     try {
/* 2670 */       this.mPdfViewCtrl.docLock(true);
/* 2671 */       shouldUnlock = true;
/* 2672 */       raiseAnnotationPreRemoveEvent(this.mAnnot, this.mAnnotPageNum);
/* 2673 */       Page page = this.mPdfViewCtrl.getDoc().getPage(this.mAnnotPageNum);
/* 2674 */       page.annotRemove(this.mAnnot);
/* 2675 */       this.mPdfViewCtrl.update(this.mAnnot, this.mAnnotPageNum);
/*      */ 
/*      */       
/* 2678 */       raiseAnnotationRemovedEvent(this.mAnnot, this.mAnnotPageNum);
/*      */       
/* 2680 */       unsetAnnot();
/* 2681 */     } catch (Exception e) {
/* 2682 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } finally {
/* 2684 */       if (shouldUnlock) {
/* 2685 */         this.mPdfViewCtrl.docUnlock();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void cancelNoteCreate(boolean forceSameNextToolMode, boolean backToPan) {
/* 2691 */     this.mUpFromStickyCreate = false;
/* 2692 */     this.mUpFromStickyCreateDlgShown = false;
/*      */     
/* 2694 */     if (backToPan) {
/* 2695 */       this.mNextToolMode = ToolManager.ToolMode.PAN;
/* 2696 */       ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 2697 */       ToolManager.Tool tool = toolManager.createTool(this.mNextToolMode, null);
/* 2698 */       ((Tool)tool).mForceSameNextToolMode = forceSameNextToolMode;
/* 2699 */       toolManager.setTool(tool);
/* 2700 */     } else if (this.mAnnot != null) {
/* 2701 */       ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 2702 */       toolManager.selectAnnot(this.mAnnot, this.mAnnotPageNum);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void handleAnnotNote(final boolean forceSameNextToolMode) {
/* 2708 */     if (this.mAnnot == null) {
/*      */       return;
/*      */     }
/*      */     
/* 2712 */     Bundle bundle = new Bundle();
/* 2713 */     bundle.putStringArray("PDFTRON_KEYS", new String[] { "forceSameNextTool" });
/* 2714 */     bundle.putBoolean("forceSameNextTool", forceSameNextToolMode);
/* 2715 */     if (onInterceptAnnotationHandling(this.mAnnot, bundle)) {
/*      */       return;
/*      */     }
/* 2718 */     boolean shouldUnlock = false;
/*      */     try {
/* 2720 */       this.mPdfViewCtrl.docLockRead();
/* 2721 */       shouldUnlock = true;
/* 2722 */       Markup markup = new Markup(this.mAnnot);
/*      */ 
/*      */       
/* 2725 */       this.mDialogAnnotNote = new DialogAnnotNote(this.mPdfViewCtrl, markup.getContents(), this.mHasMenuPermission);
/* 2726 */       this.mDialogAnnotNote.setAnnotNoteListener(this);
/*      */       
/* 2728 */       this.mDialogAnnotNote.setOnDismissListener(new DialogInterface.OnDismissListener()
/*      */           {
/*      */             public void onDismiss(DialogInterface dialogInterface) {
/* 2731 */               AnnotEdit.this.prepareDialogAnnotNoteDismiss(forceSameNextToolMode);
/*      */             }
/*      */           });
/* 2734 */       this.mDialogAnnotNote.setOnCancelListener(new DialogInterface.OnCancelListener()
/*      */           {
/*      */             public void onCancel(DialogInterface dialogInterface) {
/* 2737 */               AnnotEdit.this.cancelNoteCreate(forceSameNextToolMode, false);
/*      */             }
/*      */           });
/*      */       
/* 2741 */       this.mDialogAnnotNote.show();
/* 2742 */       this.mUpFromStickyCreateDlgShown = true;
/* 2743 */     } catch (PDFNetException e) {
/* 2744 */       AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*      */     } finally {
/* 2746 */       if (shouldUnlock) {
/* 2747 */         this.mPdfViewCtrl.docUnlockRead();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void handleStickyNote(final boolean forceSameNextToolMode, boolean upFromStickyCreate) {
/* 2753 */     if (this.mAnnot == null || this.mUpFromStickyCreateDlgShown) {
/*      */       return;
/*      */     }
/*      */     
/* 2757 */     Bundle bundle = new Bundle();
/* 2758 */     bundle.putStringArray("PDFTRON_KEYS", new String[] { "forceSameNextTool", "upFromStickyCreate" });
/* 2759 */     bundle.putBoolean("forceSameNextTool", forceSameNextToolMode);
/* 2760 */     bundle.putBoolean("upFromStickyCreate", upFromStickyCreate);
/* 2761 */     if (onInterceptAnnotationHandling(this.mAnnot, bundle)) {
/*      */       return;
/*      */     }
/*      */     
/* 2765 */     boolean canShow = ((ToolManager)this.mPdfViewCtrl.getToolManager()).getStickyNoteShowPopup();
/* 2766 */     if (!canShow) {
/*      */       return;
/*      */     }
/*      */     
/* 2770 */     boolean shouldUnlock = false;
/*      */     try {
/* 2772 */       this.mPdfViewCtrl.docLockRead();
/* 2773 */       shouldUnlock = true;
/*      */       
/* 2775 */       Markup markup = new Markup(this.mAnnot);
/*      */ 
/*      */       
/* 2778 */       boolean existingStickyNote = !upFromStickyCreate;
/* 2779 */       Text t = new Text(this.mAnnot);
/* 2780 */       String iconType = t.getIconName();
/*      */       
/* 2782 */       ColorPt colorPt = this.mAnnot.getColorAsRGB();
/* 2783 */       int r = (int)Math.round(colorPt.get(0) * 255.0D);
/* 2784 */       int g = (int)Math.round(colorPt.get(1) * 255.0D);
/* 2785 */       int b = (int)Math.round(colorPt.get(2) * 255.0D);
/* 2786 */       int iconColor = Color.rgb(r, g, b);
/* 2787 */       double iconOpacity = markup.getOpacity();
/* 2788 */       String contents = markup.getContents();
/* 2789 */       if (!Utils.isNullOrEmpty(contents)) {
/* 2790 */         existingStickyNote = true;
/*      */       }
/*      */       
/* 2793 */       this.mDialogStickyNote = new DialogStickyNote(this.mPdfViewCtrl, markup.getContents(), existingStickyNote, iconType, iconColor, (float)iconOpacity, this.mHasMenuPermission);
/* 2794 */       this.mDialogStickyNote.setAnnotNoteListener(this);
/*      */       
/* 2796 */       this.mDialogStickyNote.setOnDismissListener(new DialogInterface.OnDismissListener()
/*      */           {
/*      */             public void onDismiss(DialogInterface dialogInterface) {
/* 2799 */               AnnotEdit.this.prepareDialogStickyNoteDismiss(forceSameNextToolMode);
/*      */             }
/*      */           });
/* 2802 */       this.mDialogStickyNote.setAnnotAppearanceChangeListener(this);
/* 2803 */       this.mDialogStickyNote.setOnCancelListener(new DialogInterface.OnCancelListener()
/*      */           {
/*      */             public void onCancel(DialogInterface dialogInterface) {
/* 2806 */               AnnotEdit.this.cancelNoteCreate(forceSameNextToolMode, false);
/*      */             }
/*      */           });
/*      */       
/* 2810 */       this.mDialogStickyNote.show();
/* 2811 */       this.mUpFromStickyCreateDlgShown = true;
/* 2812 */     } catch (Exception e) {
/* 2813 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } finally {
/* 2815 */       if (shouldUnlock) {
/* 2816 */         this.mPdfViewCtrl.docUnlockRead();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void prepareDialogStickyNoteDismiss(boolean forceSameNextToolMode) {
/* 2822 */     if (this.mPdfViewCtrl == null || this.mAnnot == null || this.mDialogStickyNote == null) {
/*      */       return;
/*      */     }
/*      */     
/* 2826 */     Bundle bundle = new Bundle();
/* 2827 */     bundle.putString("METHOD_FROM", "prepareDialogStickyNoteDismiss");
/* 2828 */     bundle.putStringArray("PDFTRON_KEYS", new String[] { "contents", "pressedButton", "forceSameNextTool" });
/* 2829 */     bundle.putBoolean("forceSameNextTool", forceSameNextToolMode);
/* 2830 */     bundle.putInt("pressedButton", this.mAnnotButtonPressed);
/* 2831 */     bundle.putString("contents", this.mDialogStickyNote.getNote());
/* 2832 */     if (onInterceptAnnotationHandling(this.mAnnot, bundle)) {
/*      */       return;
/*      */     }
/*      */     
/*      */     try {
/* 2837 */       Markup markup = new Markup(this.mAnnot);
/* 2838 */       boolean existingStickyNote = this.mDialogStickyNote.isExistingNote();
/*      */ 
/*      */       
/* 2841 */       if (this.mAnnotButtonPressed == -1) {
/* 2842 */         boolean shouldUnlock = false;
/*      */         
/* 2844 */         if (!existingStickyNote || this.mDialogStickyNote.isEditEnabled()) {
/*      */           try {
/* 2846 */             String newContent = this.mDialogStickyNote.getNote();
/* 2847 */             Popup popup = markup.getPopup();
/* 2848 */             if (!existingStickyNote || (newContent != null && (popup == null || !popup.isValid() || 
/* 2849 */               !newContent.equals(popup.getContents())))) {
/*      */ 
/*      */               
/* 2852 */               this.mPdfViewCtrl.docLock(true);
/* 2853 */               shouldUnlock = true;
/* 2854 */               raiseAnnotationPreModifyEvent(this.mAnnot, this.mAnnotPageNum);
/* 2855 */               Utils.handleEmptyPopup(this.mPdfViewCtrl, markup);
/* 2856 */               popup = markup.getPopup();
/* 2857 */               popup.setContents(newContent);
/* 2858 */               if (!existingStickyNote) {
/* 2859 */                 setAuthor(markup);
/*      */               }
/* 2861 */               raiseAnnotationModifiedEvent(this.mAnnot, this.mAnnotPageNum, bundle);
/*      */             } 
/* 2863 */           } catch (Exception e) {
/* 2864 */             AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */           } finally {
/* 2866 */             if (shouldUnlock) {
/* 2867 */               this.mPdfViewCtrl.docUnlock();
/*      */             }
/*      */           } 
/* 2870 */           this.mUpFromStickyCreate = false;
/* 2871 */           this.mUpFromStickyCreateDlgShown = false;
/*      */           
/* 2873 */           showMenu(getAnnotRect());
/*      */         } else {
/*      */           
/* 2876 */           showMenu(getAnnotRect());
/* 2877 */           cancelNoteCreate(forceSameNextToolMode, false);
/*      */         } 
/* 2879 */       } else if (this.mAnnotButtonPressed == -2 && existingStickyNote) {
/*      */ 
/*      */ 
/*      */         
/* 2883 */         if (this.mDialogStickyNote.isEditEnabled()) {
/* 2884 */           showMenu(getAnnotRect());
/* 2885 */           cancelNoteCreate(forceSameNextToolMode, false);
/*      */         } else {
/*      */           
/* 2888 */           deleteStickyAnnot();
/* 2889 */           cancelNoteCreate(forceSameNextToolMode, !this.mForceSameNextToolMode);
/*      */         }
/*      */       
/*      */       }
/* 2893 */       else if (!existingStickyNote) {
/* 2894 */         cancelNoteCreate(forceSameNextToolMode, true);
/*      */       } 
/*      */       
/* 2897 */       this.mAnnotButtonPressed = 0;
/* 2898 */       this.mDialogStickyNote.prepareDismiss();
/* 2899 */     } catch (PDFNetException e) {
/* 2900 */       AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void prepareDialogAnnotNoteDismiss(boolean forceSameNextToolMode) {
/* 2905 */     if (this.mPdfViewCtrl == null || this.mAnnot == null || this.mDialogAnnotNote == null) {
/*      */       return;
/*      */     }
/*      */     
/* 2909 */     Bundle bundle = new Bundle();
/* 2910 */     bundle.putString("METHOD_FROM", "prepareDialogAnnotNoteDismiss");
/* 2911 */     bundle.putStringArray("PDFTRON_KEYS", new String[] { "contents", "pressedButton", "forceSameNextTool" });
/* 2912 */     bundle.putBoolean("forceSameNextTool", forceSameNextToolMode);
/* 2913 */     bundle.putInt("pressedButton", this.mAnnotButtonPressed);
/* 2914 */     bundle.putString("contents", this.mDialogAnnotNote.getNote());
/* 2915 */     if (onInterceptAnnotationHandling(this.mAnnot, bundle)) {
/*      */       return;
/*      */     }
/*      */     
/*      */     try {
/* 2920 */       Markup markup = new Markup(this.mAnnot);
/*      */       
/* 2922 */       if (this.mAnnotButtonPressed != -2) {
/* 2923 */         boolean shouldUnlock = false;
/*      */ 
/*      */         
/*      */         try {
/* 2927 */           this.mPdfViewCtrl.docLock(true);
/* 2928 */           shouldUnlock = true;
/* 2929 */           raiseAnnotationPreModifyEvent(this.mAnnot, this.mAnnotPageNum);
/* 2930 */           Utils.handleEmptyPopup(this.mPdfViewCtrl, markup);
/* 2931 */           Popup popup = markup.getPopup();
/* 2932 */           String oldContent = popup.getContents();
/* 2933 */           String newContent = this.mDialogAnnotNote.getNote();
/* 2934 */           popup.setContents(this.mDialogAnnotNote.getNote());
/* 2935 */           if (Utils.isTextCopy((Annot)markup) && newContent != null && !newContent.equals(oldContent)) {
/* 2936 */             Utils.removeTextCopy((Annot)markup);
/*      */           }
/* 2938 */           updateQuickMenuNoteText(this.mDialogAnnotNote.getNote());
/* 2939 */           raiseAnnotationModifiedEvent(this.mAnnot, this.mAnnotPageNum, bundle);
/* 2940 */         } catch (Exception e) {
/* 2941 */           AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */         } finally {
/* 2943 */           if (shouldUnlock) {
/* 2944 */             this.mPdfViewCtrl.docUnlock();
/*      */           }
/*      */         } 
/* 2947 */         this.mUpFromStickyCreate = false;
/* 2948 */         this.mUpFromStickyCreateDlgShown = false;
/*      */         
/* 2950 */         if (forceSameNextToolMode) {
/* 2951 */           if (this.mCurrentDefaultToolMode != ToolManager.ToolMode.PAN) {
/* 2952 */             this.mNextToolMode = this.mCurrentDefaultToolMode;
/*      */           } else {
/* 2954 */             this.mNextToolMode = ToolManager.ToolMode.TEXT_ANNOT_CREATE;
/*      */           } 
/* 2956 */           ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 2957 */           ToolManager.Tool tool = toolManager.createTool(this.mNextToolMode, null);
/* 2958 */           ((Tool)tool).mForceSameNextToolMode = true;
/* 2959 */           ((Tool)tool).mCurrentDefaultToolMode = this.mCurrentDefaultToolMode;
/* 2960 */           toolManager.setTool(tool);
/*      */         } else {
/* 2962 */           ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 2963 */           toolManager.selectAnnot(this.mAnnot, this.mAnnotPageNum);
/*      */         } 
/*      */       } else {
/*      */         
/* 2967 */         if (!this.mDialogAnnotNote.isEditEnabled()) {
/* 2968 */           boolean shouldUnlock = false;
/*      */ 
/*      */           
/*      */           try {
/* 2972 */             this.mPdfViewCtrl.docLock(true);
/* 2973 */             shouldUnlock = true;
/* 2974 */             raiseAnnotationPreModifyEvent(this.mAnnot, this.mAnnotPageNum);
/* 2975 */             Utils.handleEmptyPopup(this.mPdfViewCtrl, markup);
/* 2976 */             Popup popup = markup.getPopup();
/* 2977 */             popup.setContents("");
/* 2978 */             Utils.removeTextCopy((Annot)markup);
/* 2979 */             setAuthor(markup);
/* 2980 */             updateQuickMenuNoteText("");
/* 2981 */             raiseAnnotationModifiedEvent(this.mAnnot, this.mAnnotPageNum, bundle);
/* 2982 */           } catch (Exception e) {
/* 2983 */             AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */           } finally {
/* 2985 */             if (shouldUnlock) {
/* 2986 */               this.mPdfViewCtrl.docUnlock();
/*      */             }
/*      */           } 
/*      */         } 
/* 2990 */         cancelNoteCreate(forceSameNextToolMode, false);
/*      */       } 
/* 2992 */       this.mAnnotButtonPressed = 0;
/* 2993 */     } catch (PDFNetException e) {
/* 2994 */       AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void editTextColor(@ColorInt int color) {
/* 3000 */     if (this.mAnnot == null) {
/*      */       return;
/*      */     }
/*      */     
/* 3004 */     Bundle interceptInfo = new Bundle();
/* 3005 */     interceptInfo.putInt("textColor", color);
/* 3006 */     interceptInfo.putStringArray("PDFTRON_KEYS", new String[] { "textColor" });
/* 3007 */     if (onInterceptAnnotationHandling(this.mAnnot, interceptInfo)) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 3013 */     boolean shouldUnlock = false;
/*      */     try {
/*      */       Widget widget;
/*      */       FreeText freeText;
/* 3017 */       this.mPdfViewCtrl.docLock(true);
/* 3018 */       shouldUnlock = true;
/*      */       
/* 3020 */       raiseAnnotationPreModifyEvent(this.mAnnot, this.mAnnotPageNum);
/*      */       
/* 3022 */       int annotType = this.mAnnot.getType();
/* 3023 */       ColorPt colorPt = Utils.color2ColorPt(color);
/* 3024 */       switch (annotType) {
/*      */         case 19:
/* 3026 */           widget = new Widget(this.mAnnot);
/* 3027 */           widget.setTextColor(colorPt, 3);
/* 3028 */           widget.refreshAppearance();
/*      */           break;
/*      */         
/*      */         case 2:
/* 3032 */           freeText = new FreeText(this.mAnnot);
/* 3033 */           freeText.setTextColor(colorPt, 3);
/* 3034 */           if (AnnotUtils.isCallout((Annot)freeText)) {
/* 3035 */             annotType = 1007;
/* 3036 */           } else if (AnnotUtils.isFreeTextDate((Annot)freeText)) {
/* 3037 */             annotType = 1011;
/* 3038 */           } else if (AnnotUtils.isFreeTextSpacing((Annot)freeText)) {
/* 3039 */             annotType = 1010;
/*      */           } 
/* 3041 */           refreshAppearanceImpl();
/*      */           break;
/*      */         
/*      */         default:
/* 3045 */           throw new RuntimeException("Annotation should not have text color style");
/*      */       } 
/*      */       
/* 3048 */       this.mPdfViewCtrl.update(this.mAnnot, this.mAnnotPageNum);
/*      */       
/* 3050 */       raiseAnnotationModifiedEvent(this.mAnnot, this.mAnnotPageNum);
/*      */       
/* 3052 */       SharedPreferences settings = Tool.getToolPreferences(this.mPdfViewCtrl.getContext());
/* 3053 */       SharedPreferences.Editor editor = settings.edit();
/* 3054 */       editor.putInt(getTextColorKey(annotType), color);
/* 3055 */       editor.apply();
/* 3056 */     } catch (Exception e) {
/* 3057 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } finally {
/* 3059 */       if (shouldUnlock) {
/* 3060 */         this.mPdfViewCtrl.docUnlock();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void editColor(int color) {
/* 3066 */     if (this.mAnnot == null) {
/*      */       return;
/*      */     }
/*      */     
/* 3070 */     Bundle bundle = new Bundle();
/* 3071 */     bundle.putString("METHOD_FROM", "editColor");
/* 3072 */     bundle.putInt("color", color);
/* 3073 */     bundle.putStringArray("PDFTRON_KEYS", new String[] { "color" });
/* 3074 */     if (onInterceptAnnotationHandling(this.mAnnot, bundle)) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 3080 */     boolean shouldUnlock = false;
/*      */ 
/*      */     
/*      */     try {
/* 3084 */       this.mPdfViewCtrl.docLock(true);
/* 3085 */       shouldUnlock = true;
/*      */       
/* 3087 */       raiseAnnotationPreModifyEvent(this.mAnnot, this.mAnnotPageNum);
/*      */       
/* 3089 */       ColorPt colorPt = Utils.color2ColorPt(color);
/*      */       
/* 3091 */       if (this.mAnnotIsFreeText) {
/* 3092 */         FreeText freeText = new FreeText(this.mAnnot);
/* 3093 */         if (color != 0) {
/* 3094 */           freeText.setLineColor(colorPt, 3);
/*      */         } else {
/* 3096 */           freeText.setLineColor(colorPt, 0);
/*      */         }
/*      */       
/* 3099 */       } else if (color != 0) {
/* 3100 */         this.mAnnot.setColor(colorPt, 3);
/*      */       } else {
/* 3102 */         this.mAnnot.setColor(colorPt, 0);
/*      */       } 
/*      */ 
/*      */       
/* 3106 */       if (!this.mAnnotIsSticky)
/*      */       {
/*      */         
/* 3109 */         if (color == 0) {
/* 3110 */           Annot.BorderStyle bs = this.mAnnot.getBorderStyle();
/* 3111 */           double thickness = bs.getWidth();
/* 3112 */           if (thickness > 0.0D) {
/* 3113 */             this.mAnnot.getSDFObj().putNumber("pdftron_thickness", thickness);
/*      */           }
/* 3115 */           bs.setWidth(0.0D);
/* 3116 */           this.mAnnot.setBorderStyle(bs);
/* 3117 */           this.mAnnot.getSDFObj().erase("AP");
/*      */         }
/*      */         else {
/*      */           
/* 3121 */           Obj sdfObj = this.mAnnot.getSDFObj();
/* 3122 */           Obj thicknessObj = sdfObj.findObj("pdftron_thickness");
/* 3123 */           if (thicknessObj != null) {
/*      */             
/* 3125 */             double storedThickness = thicknessObj.getNumber();
/* 3126 */             Annot.BorderStyle bs = this.mAnnot.getBorderStyle();
/* 3127 */             bs.setWidth(storedThickness);
/* 3128 */             this.mAnnot.setBorderStyle(bs);
/* 3129 */             this.mAnnot.getSDFObj().erase("AP");
/*      */             
/* 3131 */             this.mAnnot.getSDFObj().erase("pdftron_thickness");
/*      */           } 
/*      */         } 
/*      */       }
/* 3135 */       refreshAppearanceImpl();
/* 3136 */       this.mPdfViewCtrl.update(this.mAnnot, this.mAnnotPageNum);
/*      */       
/* 3138 */       raiseAnnotationModifiedEvent(this.mAnnot, this.mAnnotPageNum, bundle);
/*      */       
/* 3140 */       SharedPreferences settings = Tool.getToolPreferences(this.mPdfViewCtrl.getContext());
/* 3141 */       SharedPreferences.Editor editor = settings.edit();
/* 3142 */       editor.putInt(getColorKey(AnnotUtils.getAnnotType(this.mAnnot)), color);
/* 3143 */       editor.apply();
/* 3144 */     } catch (Exception e) {
/* 3145 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } finally {
/* 3147 */       if (shouldUnlock) {
/* 3148 */         this.mPdfViewCtrl.docUnlock();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void editIcon(String icon) {
/* 3154 */     if (this.mAnnot == null) {
/*      */       return;
/*      */     }
/*      */     
/* 3158 */     Bundle bundle = new Bundle();
/* 3159 */     bundle.putString("METHOD_FROM", "editIcon");
/* 3160 */     bundle.putString("icon", icon);
/* 3161 */     bundle.putStringArray("PDFTRON_KEYS", new String[] { "icon" });
/* 3162 */     if (onInterceptAnnotationHandling(this.mAnnot, bundle)) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 3167 */     boolean shouldUnlock = false;
/*      */ 
/*      */     
/*      */     try {
/* 3171 */       this.mPdfViewCtrl.docLock(true);
/* 3172 */       shouldUnlock = true;
/*      */       
/* 3174 */       raiseAnnotationPreModifyEvent(this.mAnnot, this.mAnnotPageNum);
/*      */       
/* 3176 */       if (this.mAnnotIsSticky) {
/* 3177 */         Text text = new Text(this.mAnnot);
/* 3178 */         text.setIcon(icon);
/*      */       } 
/* 3180 */       AnnotUtils.refreshAnnotAppearance(this.mPdfViewCtrl.getContext(), this.mAnnot);
/* 3181 */       this.mPdfViewCtrl.update(this.mAnnot, this.mAnnotPageNum);
/*      */       
/* 3183 */       raiseAnnotationModifiedEvent(this.mAnnot, this.mAnnotPageNum, bundle);
/*      */       
/* 3185 */       SharedPreferences settings = Tool.getToolPreferences(this.mPdfViewCtrl.getContext());
/* 3186 */       SharedPreferences.Editor editor = settings.edit();
/* 3187 */       editor.putString(getIconKey(AnnotUtils.getAnnotType(this.mAnnot)), icon);
/* 3188 */       editor.apply();
/* 3189 */     } catch (Exception e) {
/* 3190 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } finally {
/* 3192 */       if (shouldUnlock) {
/* 3193 */         this.mPdfViewCtrl.docUnlock();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void editFont(String pdftronFontName) {
/* 3200 */     if (this.mAnnot == null) {
/*      */       return;
/*      */     }
/*      */     
/* 3204 */     Bundle bundle = new Bundle();
/* 3205 */     bundle.putString("METHOD_FROM", "editFont");
/* 3206 */     bundle.putString("fontName", pdftronFontName);
/* 3207 */     bundle.putStringArray("PDFTRON_KEYS", new String[] { "fontName" });
/* 3208 */     if (onInterceptAnnotationHandling(this.mAnnot, bundle)) {
/*      */       return;
/*      */     }
/* 3211 */     if (Utils.isNullOrEmpty(pdftronFontName)) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 3216 */     boolean shouldUnlock = false; try {
/*      */       String fontName; Widget widget; FreeText textAnnot; Field field; String fontDRName; Font font; Obj annotObj, drDict, fontDict; Font font1; String DA; int slashPosition;
/* 3218 */       this.mPdfViewCtrl.docLock(true);
/* 3219 */       shouldUnlock = true;
/*      */       
/* 3221 */       raiseAnnotationPreModifyEvent(this.mAnnot, this.mAnnotPageNum);
/*      */       
/* 3223 */       int annotType = this.mAnnot.getType();
/*      */ 
/*      */       
/* 3226 */       switch (annotType) {
/*      */         case 19:
/* 3228 */           widget = new Widget(this.mAnnot);
/* 3229 */           field = widget.getField();
/* 3230 */           font = Font.create((Doc)this.mPdfViewCtrl.getDoc(), pdftronFontName, field.getValueAsString());
/* 3231 */           fontName = font.getName();
/* 3232 */           widget.setFont(font);
/* 3233 */           widget.refreshAppearance();
/*      */           break;
/*      */         
/*      */         case 2:
/* 3237 */           textAnnot = new FreeText(this.mAnnot);
/* 3238 */           fontDRName = "F0";
/*      */ 
/*      */           
/* 3241 */           annotObj = textAnnot.getSDFObj();
/* 3242 */           drDict = annotObj.putDict("DR");
/* 3243 */           fontDict = drDict.putDict("Font");
/*      */ 
/*      */           
/* 3246 */           font1 = Font.create((Doc)this.mPdfViewCtrl.getDoc(), pdftronFontName, textAnnot.getContents());
/* 3247 */           fontDict.put(fontDRName, font1.GetSDFObj());
/* 3248 */           fontName = font1.getName();
/*      */ 
/*      */           
/* 3251 */           DA = textAnnot.getDefaultAppearance();
/* 3252 */           slashPosition = DA.indexOf("/", 0);
/*      */ 
/*      */           
/* 3255 */           if (slashPosition > 0) {
/* 3256 */             String beforeSlash = DA.substring(0, slashPosition);
/* 3257 */             String afterSlash = DA.substring(slashPosition);
/* 3258 */             String afterFont = afterSlash.substring(afterSlash.indexOf(" "));
/* 3259 */             String updatedDA = beforeSlash + "/" + fontDRName + afterFont;
/*      */             
/* 3261 */             textAnnot.setDefaultAppearance(updatedDA);
/*      */             
/* 3263 */             refreshAppearanceImpl();
/*      */           } 
/*      */           break;
/*      */         
/*      */         default:
/* 3268 */           throw new RuntimeException("Annotation should not have font style.");
/*      */       } 
/*      */       
/* 3271 */       this.mPdfViewCtrl.update(this.mAnnot, this.mAnnotPageNum);
/*      */       
/* 3273 */       raiseAnnotationModifiedEvent(this.mAnnot, this.mAnnotPageNum, bundle);
/*      */ 
/*      */       
/* 3276 */       updateFontMap(this.mPdfViewCtrl.getContext(), annotType, pdftronFontName, fontName);
/* 3277 */     } catch (Exception e) {
/* 3278 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } finally {
/* 3280 */       if (shouldUnlock) {
/* 3281 */         this.mPdfViewCtrl.docUnlock();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void editFillColor(int color) {
/* 3287 */     if (this.mAnnot == null) {
/*      */       return;
/*      */     }
/*      */     
/* 3291 */     Bundle bundle = new Bundle();
/* 3292 */     bundle.putString("METHOD_FROM", "editFillColor");
/* 3293 */     bundle.putInt("color", color);
/* 3294 */     bundle.putStringArray("PDFTRON_KEYS", new String[] { "color" });
/* 3295 */     if (onInterceptAnnotationHandling(this.mAnnot, bundle)) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 3301 */     boolean shouldUnlock = false;
/*      */ 
/*      */     
/*      */     try {
/* 3305 */       this.mPdfViewCtrl.docLock(true);
/* 3306 */       shouldUnlock = true;
/*      */       
/* 3308 */       raiseAnnotationPreModifyEvent(this.mAnnot, this.mAnnotPageNum);
/*      */       
/* 3310 */       if (this.mAnnot.isMarkup() && !this.mAnnotIsFreeText) {
/* 3311 */         Markup m = new Markup(this.mAnnot);
/*      */         
/* 3313 */         if (color == 0) {
/* 3314 */           ColorPt emptyColorPt = new ColorPt(0.0D, 0.0D, 0.0D, 0.0D);
/* 3315 */           m.setInteriorColor(emptyColorPt, 0);
/*      */         } else {
/* 3317 */           ColorPt colorPt = Utils.color2ColorPt(color);
/* 3318 */           m.setInteriorColor(colorPt, 3);
/*      */         } 
/* 3320 */         SharedPreferences settings = Tool.getToolPreferences(this.mPdfViewCtrl.getContext());
/* 3321 */         SharedPreferences.Editor editor = settings.edit();
/* 3322 */         editor.putInt(getColorFillKey(AnnotUtils.getAnnotType(this.mAnnot)), color);
/* 3323 */         editor.apply();
/* 3324 */       } else if (this.mAnnotIsFreeText) {
/* 3325 */         FreeText freeText = new FreeText(this.mAnnot);
/* 3326 */         if (color == 0) {
/* 3327 */           ColorPt emptyColorPt = new ColorPt(0.0D, 0.0D, 0.0D, 0.0D);
/* 3328 */           freeText.setColor(emptyColorPt, 0);
/*      */         } else {
/* 3330 */           ColorPt colorPt = Utils.color2ColorPt(color);
/* 3331 */           freeText.setColor(colorPt, 3);
/*      */         } 
/* 3333 */         SharedPreferences settings = Tool.getToolPreferences(this.mPdfViewCtrl.getContext());
/* 3334 */         SharedPreferences.Editor editor = settings.edit();
/* 3335 */         editor.putInt(getColorFillKey(AnnotUtils.getAnnotType(this.mAnnot)), color);
/* 3336 */         editor.apply();
/*      */       } 
/*      */       
/* 3339 */       refreshAppearanceImpl();
/* 3340 */       this.mPdfViewCtrl.update(this.mAnnot, this.mAnnotPageNum);
/*      */       
/* 3342 */       raiseAnnotationModifiedEvent(this.mAnnot, this.mAnnotPageNum, bundle);
/* 3343 */     } catch (Exception e) {
/* 3344 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } finally {
/* 3346 */       if (shouldUnlock) {
/* 3347 */         this.mPdfViewCtrl.docUnlock();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void editTextSize(float textSize) {
/* 3354 */     if (this.mAnnot == null) {
/*      */       return;
/*      */     }
/*      */     
/* 3358 */     Bundle bundle = new Bundle();
/* 3359 */     bundle.putString("METHOD_FROM", "editTextSize");
/* 3360 */     bundle.putFloat("textSize", textSize);
/* 3361 */     bundle.putStringArray("PDFTRON_KEYS", new String[] { "textSize" });
/* 3362 */     if (onInterceptAnnotationHandling(this.mAnnot, bundle)) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 3367 */     boolean shouldUnlock = false; try {
/*      */       Widget widget; FreeText freeText; boolean isRightToLeft;
/* 3369 */       this.mPdfViewCtrl.docLock(true);
/* 3370 */       shouldUnlock = true;
/*      */       
/* 3372 */       raiseAnnotationPreModifyEvent(this.mAnnot, this.mAnnotPageNum);
/*      */       
/* 3374 */       int type = this.mAnnot.getType();
/*      */       
/* 3376 */       switch (type) {
/*      */         case 19:
/* 3378 */           widget = new Widget(this.mAnnot);
/* 3379 */           widget.setFontSize(textSize);
/* 3380 */           widget.refreshAppearance();
/*      */           break;
/*      */         
/*      */         case 2:
/* 3384 */           freeText = new FreeText(this.mAnnot);
/* 3385 */           freeText.setFontSize(textSize);
/* 3386 */           freeText.refreshAppearance();
/*      */           
/* 3388 */           if (AnnotUtils.isCallout((Annot)freeText)) {
/* 3389 */             type = 1007;
/* 3390 */           } else if (AnnotUtils.isFreeTextDate((Annot)freeText)) {
/* 3391 */             type = 1011;
/* 3392 */           } else if (AnnotUtils.isFreeTextSpacing((Annot)freeText)) {
/* 3393 */             type = 1010;
/*      */           } 
/* 3395 */           refreshAppearanceImpl();
/*      */           
/* 3397 */           isRightToLeft = Utils.isRightToLeftString(freeText.getContents());
/* 3398 */           resizeFreeText(freeText, freeText.getContentRect(), isRightToLeft);
/*      */ 
/*      */           
/* 3401 */           buildAnnotBBox();
/* 3402 */           setCtrlPts();
/*      */           break;
/*      */         
/*      */         default:
/* 3406 */           throw new RuntimeException("Annotation should not have text size.");
/*      */       } 
/*      */       
/* 3409 */       this.mPdfViewCtrl.update(this.mAnnot, this.mAnnotPageNum);
/*      */       
/* 3411 */       raiseAnnotationModifiedEvent(this.mAnnot, this.mAnnotPageNum, bundle);
/*      */       
/* 3413 */       SharedPreferences settings = Tool.getToolPreferences(this.mPdfViewCtrl.getContext());
/* 3414 */       SharedPreferences.Editor editor = settings.edit();
/* 3415 */       editor.putFloat(getTextSizeKey(type), textSize);
/* 3416 */       editor.apply();
/* 3417 */     } catch (Exception e) {
/* 3418 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } finally {
/* 3420 */       if (shouldUnlock) {
/* 3421 */         this.mPdfViewCtrl.docUnlock();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void editFreeTextDateFormat(String dateFormat) {
/* 3427 */     if (this.mAnnot == null) {
/*      */       return;
/*      */     }
/*      */     
/* 3431 */     Bundle bundle = new Bundle();
/* 3432 */     bundle.putString("METHOD_FROM", "editFreeTextDateFormat");
/* 3433 */     bundle.putString("format", dateFormat);
/* 3434 */     bundle.putStringArray("PDFTRON_KEYS", new String[] { "format" });
/* 3435 */     if (onInterceptAnnotationHandling(this.mAnnot, bundle)) {
/*      */       return;
/*      */     }
/*      */     
/* 3439 */     String newContent = null;
/* 3440 */     boolean shouldUnlock = false;
/*      */ 
/*      */     
/*      */     try {
/* 3444 */       this.mPdfViewCtrl.docLock(true);
/* 3445 */       shouldUnlock = true;
/*      */       
/* 3447 */       if (this.mAnnot.getType() == 2) {
/* 3448 */         FreeText freeText = new FreeText(this.mAnnot);
/* 3449 */         String currentDate = freeText.getContents();
/* 3450 */         String currentFormat = freeText.getCustomData(AnnotUtils.KEY_FreeTextDate);
/* 3451 */         if (currentFormat != null) {
/* 3452 */           SimpleDateFormat formatter = new SimpleDateFormat(currentFormat, Locale.getDefault());
/* 3453 */           Date date = formatter.parse(currentDate);
/* 3454 */           SimpleDateFormat newFormat = new SimpleDateFormat(dateFormat, Locale.getDefault());
/* 3455 */           if (date != null) {
/* 3456 */             newContent = newFormat.format(date);
/* 3457 */             freeText.setCustomData(AnnotUtils.KEY_FreeTextDate, dateFormat);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 3462 */       SharedPreferences settings = Tool.getToolPreferences(this.mPdfViewCtrl.getContext());
/* 3463 */       SharedPreferences.Editor editor = settings.edit();
/* 3464 */       editor.putString(getDateFormatKey(AnnotUtils.getAnnotType(this.mAnnot)), dateFormat);
/* 3465 */       editor.apply();
/* 3466 */     } catch (Exception e) {
/* 3467 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } finally {
/* 3469 */       if (shouldUnlock) {
/* 3470 */         this.mPdfViewCtrl.docUnlock();
/*      */       }
/*      */     } 
/*      */     
/* 3474 */     if (newContent != null) {
/* 3475 */       updateFreeText(newContent);
/* 3476 */       setCtrlPts();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void editThickness(float thickness) {
/* 3481 */     if (this.mAnnot == null) {
/*      */       return;
/*      */     }
/*      */     
/* 3485 */     Bundle bundle = new Bundle();
/* 3486 */     bundle.putString("METHOD_FROM", "editThickness");
/* 3487 */     bundle.putFloat("thickness", thickness);
/* 3488 */     bundle.putStringArray("PDFTRON_KEYS", new String[] { "thickness" });
/* 3489 */     if (onInterceptAnnotationHandling(this.mAnnot, bundle)) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 3494 */     boolean shouldUnlock = false;
/*      */     
/*      */     try {
/*      */       int colorCompNum;
/* 3498 */       this.mPdfViewCtrl.docLock(true);
/* 3499 */       shouldUnlock = true;
/*      */ 
/*      */       
/* 3502 */       if (this.mAnnotIsFreeText) {
/* 3503 */         FreeText freeText = new FreeText(this.mAnnot);
/* 3504 */         colorCompNum = freeText.getLineColorCompNum();
/*      */       } else {
/* 3506 */         colorCompNum = this.mAnnot.getColorCompNum();
/*      */       } 
/* 3508 */       raiseAnnotationPreModifyEvent(this.mAnnot, this.mAnnotPageNum);
/*      */       
/* 3510 */       Annot.BorderStyle bs = this.mAnnot.getBorderStyle();
/* 3511 */       double annotThickness = bs.getWidth();
/* 3512 */       boolean canSetWidth = true;
/*      */ 
/*      */       
/* 3515 */       if (colorCompNum == 0 && annotThickness == 0.0D) {
/* 3516 */         Obj storedThicknessObj = this.mAnnot.getSDFObj().findObj("pdftron_thickness");
/* 3517 */         if (storedThicknessObj != null) {
/* 3518 */           this.mAnnot.getSDFObj().putNumber("pdftron_thickness", thickness);
/* 3519 */           canSetWidth = false;
/*      */         } 
/*      */       } 
/* 3522 */       if (canSetWidth) {
/* 3523 */         bs.setWidth(thickness);
/* 3524 */         this.mAnnot.setBorderStyle(bs);
/* 3525 */         if (thickness == 0.0F) {
/* 3526 */           this.mAnnot.getSDFObj().erase("AP");
/*      */         }
/*      */       } 
/* 3529 */       if (this.mAnnot.getType() == 14 && PressureInkUtils.isPressureSensitive(this.mAnnot)) {
/* 3530 */         PressureInkUtils.refreshCustomInkAppearanceForExistingAnnot(this.mAnnot);
/*      */       } else {
/* 3532 */         refreshAppearanceImpl();
/*      */       } 
/* 3534 */       this.mPdfViewCtrl.update(this.mAnnot, this.mAnnotPageNum);
/*      */       
/* 3536 */       raiseAnnotationModifiedEvent(this.mAnnot, this.mAnnotPageNum, bundle);
/*      */       
/* 3538 */       SharedPreferences settings = Tool.getToolPreferences(this.mPdfViewCtrl.getContext());
/* 3539 */       SharedPreferences.Editor editor = settings.edit();
/* 3540 */       editor.putFloat(getThicknessKey(AnnotUtils.getAnnotType(this.mAnnot)), thickness);
/* 3541 */       editor.apply();
/* 3542 */     } catch (Exception e) {
/* 3543 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } finally {
/* 3545 */       if (shouldUnlock) {
/* 3546 */         this.mPdfViewCtrl.docUnlock();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void editOpacity(float opacity) {
/* 3552 */     if (this.mAnnot == null) {
/*      */       return;
/*      */     }
/*      */     
/* 3556 */     Bundle bundle = new Bundle();
/* 3557 */     bundle.putString("METHOD_FROM", "editOpacity");
/* 3558 */     bundle.putFloat("opacity", opacity);
/* 3559 */     bundle.putStringArray("PDFTRON_KEYS", new String[] { "opacity" });
/* 3560 */     if (onInterceptAnnotationHandling(this.mAnnot, bundle)) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 3565 */     boolean shouldUnlock = false;
/*      */ 
/*      */     
/*      */     try {
/* 3569 */       this.mPdfViewCtrl.docLock(true);
/* 3570 */       shouldUnlock = true;
/*      */       
/* 3572 */       raiseAnnotationPreModifyEvent(this.mAnnot, this.mAnnotPageNum);
/*      */       
/* 3574 */       if (this.mAnnot.isMarkup()) {
/* 3575 */         Markup m = new Markup(this.mAnnot);
/* 3576 */         m.setOpacity(opacity);
/*      */       } 
/* 3578 */       refreshAppearanceImpl();
/* 3579 */       this.mPdfViewCtrl.update(this.mAnnot, this.mAnnotPageNum);
/*      */       
/* 3581 */       raiseAnnotationModifiedEvent(this.mAnnot, this.mAnnotPageNum, bundle);
/*      */       
/* 3583 */       SharedPreferences settings = Tool.getToolPreferences(this.mPdfViewCtrl.getContext());
/* 3584 */       SharedPreferences.Editor editor = settings.edit();
/* 3585 */       editor.putFloat(getOpacityKey(AnnotUtils.getAnnotType(this.mAnnot)), opacity);
/* 3586 */       editor.apply();
/* 3587 */     } catch (Exception e) {
/* 3588 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } finally {
/* 3590 */       if (shouldUnlock) {
/* 3591 */         this.mPdfViewCtrl.docUnlock();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void editRuler(RulerItem rulerItem) {
/* 3597 */     if (this.mAnnot == null) {
/*      */       return;
/*      */     }
/*      */     
/* 3601 */     Bundle bundle = new Bundle();
/* 3602 */     bundle.putString("METHOD_FROM", "editRuler");
/* 3603 */     bundle.putParcelable("rulerItem", (Parcelable)rulerItem);
/* 3604 */     bundle.putStringArray("PDFTRON_KEYS", new String[] { "rulerItem" });
/* 3605 */     if (onInterceptAnnotationHandling(this.mAnnot, bundle)) {
/*      */       return;
/*      */     }
/* 3608 */     boolean shouldUnlock = false;
/*      */ 
/*      */     
/*      */     try {
/* 3612 */       this.mPdfViewCtrl.docLock(true);
/* 3613 */       shouldUnlock = true;
/*      */       
/* 3615 */       if (AnnotUtils.isRuler(this.mAnnot) || 
/* 3616 */         AnnotUtils.isPerimeterMeasure(this.mAnnot) || 
/* 3617 */         AnnotUtils.isAreaMeasure(this.mAnnot)) {
/* 3618 */         raiseAnnotationPreModifyEvent(this.mAnnot, this.mAnnotPageNum);
/*      */         
/* 3620 */         if (AnnotUtils.isRuler(this.mAnnot)) {
/* 3621 */           Line line = new Line(this.mAnnot);
/* 3622 */           RulerItem.removeRulerItem((Annot)line);
/* 3623 */           RulerCreate.adjustContents((Annot)line, rulerItem, (line.getStartPoint()).x, (line.getStartPoint()).y, 
/* 3624 */               (line.getEndPoint()).x, (line.getEndPoint()).y);
/* 3625 */         } else if (AnnotUtils.isPerimeterMeasure(this.mAnnot)) {
/* 3626 */           PolyLine polyLine = new PolyLine(this.mAnnot);
/* 3627 */           ArrayList<Point> points = AnnotUtils.getPolyVertices((Annot)polyLine);
/* 3628 */           PerimeterMeasureCreate.adjustContents((Annot)polyLine, rulerItem, points);
/* 3629 */         } else if (AnnotUtils.isAreaMeasure(this.mAnnot)) {
/* 3630 */           Polygon polygon = new Polygon(this.mAnnot);
/* 3631 */           ArrayList<Point> points = AnnotUtils.getPolyVertices((Annot)polygon);
/* 3632 */           AreaMeasureCreate.adjustContents((Annot)polygon, rulerItem, points);
/*      */         } 
/*      */         
/* 3635 */         AnnotUtils.refreshAnnotAppearance(this.mPdfViewCtrl.getContext(), this.mAnnot);
/* 3636 */         this.mPdfViewCtrl.update(this.mAnnot, this.mAnnotPageNum);
/*      */         
/* 3638 */         raiseAnnotationModifiedEvent(this.mAnnot, this.mAnnotPageNum, bundle);
/*      */         
/* 3640 */         SharedPreferences settings = Tool.getToolPreferences(this.mPdfViewCtrl.getContext());
/* 3641 */         SharedPreferences.Editor editor = settings.edit();
/* 3642 */         editor.putFloat(getRulerBaseValueKey(AnnotUtils.getAnnotType(this.mAnnot)), rulerItem.mRulerBase);
/* 3643 */         editor.putString(getRulerBaseUnitKey(AnnotUtils.getAnnotType(this.mAnnot)), rulerItem.mRulerBaseUnit);
/* 3644 */         editor.putFloat(getRulerTranslateValueKey(AnnotUtils.getAnnotType(this.mAnnot)), rulerItem.mRulerTranslate);
/* 3645 */         editor.putString(getRulerTranslateUnitKey(AnnotUtils.getAnnotType(this.mAnnot)), rulerItem.mRulerTranslateUnit);
/* 3646 */         editor.apply();
/*      */       } 
/* 3648 */     } catch (Exception e) {
/* 3649 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } finally {
/* 3651 */       if (shouldUnlock) {
/* 3652 */         this.mPdfViewCtrl.docUnlock();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void editRedactionOverlayText(String overlayText) {
/* 3658 */     if (this.mAnnot == null) {
/*      */       return;
/*      */     }
/*      */     
/* 3662 */     Bundle bundle = new Bundle();
/* 3663 */     bundle.putString("METHOD_FROM", "editRedactionOverlayText");
/* 3664 */     bundle.putString("overlayText", overlayText);
/* 3665 */     bundle.putStringArray("PDFTRON_KEYS", new String[] { "overlayText" });
/* 3666 */     if (onInterceptAnnotationHandling(this.mAnnot, bundle)) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 3671 */     boolean shouldUnlock = false;
/*      */ 
/*      */     
/*      */     try {
/* 3675 */       this.mPdfViewCtrl.docLock(true);
/* 3676 */       shouldUnlock = true;
/*      */       
/* 3678 */       raiseAnnotationPreModifyEvent(this.mAnnot, this.mAnnotPageNum);
/*      */       
/* 3680 */       Redaction redaction = new Redaction(this.mAnnot);
/* 3681 */       redaction.setOverlayText(overlayText);
/*      */       
/* 3683 */       this.mAnnot.refreshAppearance();
/* 3684 */       this.mPdfViewCtrl.update(this.mAnnot, this.mAnnotPageNum);
/*      */       
/* 3686 */       raiseAnnotationModifiedEvent(this.mAnnot, this.mAnnotPageNum, bundle);
/* 3687 */     } catch (Exception e) {
/* 3688 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } finally {
/* 3690 */       if (shouldUnlock) {
/* 3691 */         this.mPdfViewCtrl.docUnlock();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void editTextSpacing() {
/* 3697 */     if (this.mAnnot == null) {
/*      */       return;
/*      */     }
/*      */     
/* 3701 */     float spacing = 0.0F;
/* 3702 */     if (Utils.isLollipop())
/*      */     {
/* 3704 */       spacing = (this.mAnnotView != null && this.mAnnotView.getTextView() != null) ? this.mAnnotView.getTextView().getLetterSpacing() : 0.0F;
/*      */     }
/* 3706 */     final Bundle bundle = new Bundle();
/* 3707 */     bundle.putString("METHOD_FROM", "editTextSpacing");
/* 3708 */     bundle.putFloat("spacing", spacing);
/* 3709 */     bundle.putStringArray("PDFTRON_KEYS", new String[] { "spacing" });
/* 3710 */     if (onInterceptAnnotationHandling(this.mAnnot, bundle)) {
/*      */       return;
/*      */     }
/* 3713 */     if (this.mAnnotView != null && this.mAnnotView.getTextView() != null && this.mAnnot != null) {
/*      */       try {
/* 3715 */         this.mPdfViewCtrl.docLock(true, new PDFViewCtrl.LockRunnable()
/*      */             {
/*      */               public void run() throws Exception {
/* 3718 */                 AnnotEdit.this.raiseAnnotationPreModifyEvent(AnnotEdit.this.mAnnot, AnnotEdit.this.mAnnotPageNum);
/*      */                 
/* 3720 */                 AnnotUtils.applyCustomFreeTextAppearance(AnnotEdit.this.mPdfViewCtrl, AnnotEdit.this.mAnnotView
/* 3721 */                     .getTextView(), AnnotEdit.this.mAnnot, AnnotEdit.this.mAnnotPageNum);
/*      */ 
/*      */ 
/*      */                 
/* 3725 */                 AnnotEdit.this.mAnnotStyle = AnnotUtils.getAnnotStyle(AnnotEdit.this.mAnnot);
/*      */                 
/* 3727 */                 AnnotEdit.this.mPdfViewCtrl.update(AnnotEdit.this.mAnnot, AnnotEdit.this.mAnnotPageNum);
/*      */                 
/* 3729 */                 AnnotEdit.this.raiseAnnotationModifiedEvent(AnnotEdit.this.mAnnot, AnnotEdit.this.mAnnotPageNum, bundle);
/*      */               }
/*      */             });
/*      */         
/* 3733 */         setCtrlPts();
/* 3734 */       } catch (Exception e) {
/* 3735 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void refreshAppearanceImpl() throws PDFNetException {
/* 3742 */     if (null == this.mAnnot) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 3747 */     if (this.mAnnotStyle != null && this.mAnnotStyle.isSpacingFreeText() && this.mAnnotView != null) {
/* 3748 */       AnnotUtils.applyCustomFreeTextAppearance(this.mPdfViewCtrl, this.mAnnotView
/* 3749 */           .getTextView(), this.mAnnot, this.mAnnotPageNum);
/*      */       
/* 3751 */       resumeEditing(this.mAnnotView.getTextView(), false);
/* 3752 */       this.mAnnotStyle = AnnotUtils.getAnnotStyle(this.mAnnot);
/*      */     } else {
/* 3754 */       AnnotUtils.refreshAnnotAppearance(this.mPdfViewCtrl.getContext(), this.mAnnot);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void resumeEditing(AutoScrollEditText editText, boolean textEditingEnabled) {
/* 3759 */     if (editText != null) {
/* 3760 */       if (Utils.isLollipop()) {
/* 3761 */         editText.addLetterSpacingHandle();
/*      */       }
/* 3763 */       if (textEditingEnabled) {
/* 3764 */         editText.requestFocus();
/* 3765 */         editText.setCursorVisible(true);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onRenderingFinished() {
/* 3775 */     super.onRenderingFinished();
/*      */     
/* 3777 */     if (this.mInlineEditText != null && this.mInlineEditText.delayViewRemoval()) {
/* 3778 */       this.mInlineEditText.removeView();
/* 3779 */       this.mInlineEditText = null;
/*      */       
/* 3781 */       if (!this.mHasOnCloseCalled) {
/* 3782 */         Handler handler = new Handler();
/* 3783 */         handler.postDelayed(new Runnable()
/*      */             {
/*      */               public void run() {
/* 3786 */                 RectF annotRect = AnnotEdit.this.getAnnotRect();
/* 3787 */                 if (annotRect != null) {
/* 3788 */                   AnnotEdit.this.showMenu(annotRect);
/*      */                 }
/*      */               }
/*      */             },  100L);
/*      */       } 
/*      */     } 
/*      */     
/* 3795 */     if (this.mInlineEditText != null && this.mInlineEditText.delaySetContents()) {
/* 3796 */       this.mInlineEditText.setContents();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isFreeTextEditing() {
/* 3804 */     if (this.mInlineEditText != null) {
/* 3805 */       return this.mInlineEditText.isEditing().booleanValue();
/*      */     }
/* 3807 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RectF getInlineEditTextPosition() {
/* 3815 */     RectF box = this.mBBox;
/* 3816 */     if (this.mContentBox != null) {
/* 3817 */       box = this.mContentBox;
/*      */     }
/* 3819 */     int left = (int)(box.left + this.mCtrlRadius);
/* 3820 */     int right = (int)(box.right - this.mCtrlRadius);
/* 3821 */     int top = (int)(box.top + this.mCtrlRadius);
/* 3822 */     int bottom = (int)(box.bottom - this.mCtrlRadius);
/*      */ 
/*      */ 
/*      */     
/* 3826 */     int screenWidth = Utils.getScreenWidth(this.mPdfViewCtrl.getContext());
/* 3827 */     if (box.width() > screenWidth) {
/* 3828 */       right = left + screenWidth;
/*      */     }
/*      */     
/* 3831 */     return new RectF(left, top, right, bottom);
/*      */   }
/*      */   
/*      */   protected void saveAndQuitInlineEditText(boolean immediatelyRemoveView) {
/* 3835 */     if (this.mRichTextViewModel != null) {
/* 3836 */       this.mRichTextViewModel.onCloseToolbar();
/*      */     }
/* 3838 */     if (this.mPdfViewCtrl.isAnnotationLayerEnabled())
/*      */     {
/* 3840 */       immediatelyRemoveView = true;
/*      */     }
/* 3842 */     this.mInEditMode = false;
/* 3843 */     if (this.mInlineEditText != null) {
/* 3844 */       final String contents = this.mInlineEditText.getContents();
/* 3845 */       final long handle = (this.mAnnot != null) ? this.mAnnot.__GetHandle() : 0L;
/* 3846 */       final int pageNum = this.mAnnotPageNum;
/*      */       
/* 3848 */       if (this.mInlineEditText.getRichEditor().getVisibility() == 0) {
/* 3849 */         this.mInlineEditText.getRichEditor().clearFocusEditor();
/* 3850 */         final boolean finalImmediatelyRemoveView = immediatelyRemoveView;
/* 3851 */         this.mInlineEditText.getRichEditor().postDelayed(new Runnable()
/*      */             {
/*      */               public void run() {
/* 3854 */                 AnnotEdit.this.updateFreeText(contents);
/* 3855 */                 boolean shouldUnlock = false;
/* 3856 */                 Annot annot = null;
/*      */ 
/*      */                 
/*      */                 try {
/* 3860 */                   AnnotEdit.this.mPdfViewCtrl.docLock(true);
/* 3861 */                   shouldUnlock = true;
/*      */                   
/* 3863 */                   annot = Annot.__Create(handle, AnnotEdit.this.mPdfViewCtrl.getDoc());
/*      */                   
/* 3865 */                   if (AnnotEdit.this.isValidAnnot(annot)) {
/* 3866 */                     AnnotEdit.this.raiseAnnotationPreModifyEvent(annot, pageNum);
/* 3867 */                     AnnotUtils.createRCFreeTextAppearance(AnnotEdit.this
/* 3868 */                         .mInlineEditText.getRichEditor(), AnnotEdit.this.mPdfViewCtrl, annot, pageNum);
/*      */ 
/*      */ 
/*      */ 
/*      */                     
/* 3873 */                     AnnotEdit.this.mPdfViewCtrl.update(annot, pageNum);
/* 3874 */                     AnnotEdit.this.raiseAnnotationModifiedEvent(annot, pageNum);
/*      */                   } 
/*      */                   
/* 3877 */                   AnnotEdit.this.mAnnotStyle = null;
/* 3878 */                 } catch (Exception e) {
/* 3879 */                   AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */                 } finally {
/* 3881 */                   if (shouldUnlock) {
/* 3882 */                     AnnotEdit.this.mPdfViewCtrl.docUnlock();
/*      */                   }
/*      */                 } 
/* 3885 */                 AnnotEdit.this.postSaveAndQuitInlineEditText(finalImmediatelyRemoveView);
/* 3886 */                 if (annot != null) {
/* 3887 */                   ToolManager toolManager = (ToolManager)AnnotEdit.this.mPdfViewCtrl.getToolManager();
/* 3888 */                   toolManager.selectAnnot(annot, pageNum);
/*      */                 } 
/*      */               }
/*      */             }100L);
/*      */       } else {
/* 3893 */         updateFreeText(contents);
/* 3894 */         postSaveAndQuitInlineEditText(immediatelyRemoveView);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 3899 */     if (this.mUpdateFreeTextEditMode) {
/* 3900 */       SharedPreferences settings = Tool.getToolPreferences(this.mPdfViewCtrl.getContext());
/* 3901 */       SharedPreferences.Editor editor = settings.edit();
/* 3902 */       editor.putInt("annotation_free_text_preference_editing", this.mCurrentFreeTextEditMode);
/* 3903 */       editor.apply();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void postSaveAndQuitInlineEditText(boolean immediatelyRemoveView) {
/* 3908 */     if (null == this.mInlineEditText) {
/*      */       return;
/*      */     }
/* 3911 */     this.mInlineEditText.close(immediatelyRemoveView);
/* 3912 */     addOldTools();
/*      */     
/* 3914 */     this.mHideCtrlPts = false;
/* 3915 */     setCtrlPts();
/* 3916 */     this.mPdfViewCtrl.invalidate((int)Math.floor(this.mBBox.left), (int)Math.floor(this.mBBox.top), (int)Math.ceil(this.mBBox.right), (int)Math.ceil(this.mBBox.bottom));
/*      */     
/* 3918 */     if (immediatelyRemoveView) {
/* 3919 */       this.mInlineEditText = null;
/* 3920 */       if (isQuickMenuShown()) {
/* 3921 */         closeQuickMenu();
/*      */       }
/*      */ 
/*      */       
/* 3925 */       Handler handler = new Handler();
/* 3926 */       handler.postDelayed(new Runnable()
/*      */           {
/*      */             public void run() {
/* 3929 */               AnnotEdit.this.showMenu(AnnotEdit.this.getAnnotRect());
/*      */             }
/*      */           },  300L);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void toggleToFreeTextDialog(String interImText) {
/* 3940 */     this.mCurrentFreeTextEditMode = 2;
/* 3941 */     this.mUpdateFreeTextEditMode = true;
/*      */     
/*      */     try {
/* 3944 */       fallbackFreeTextDialog(interImText, false);
/* 3945 */     } catch (Exception e) {
/* 3946 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void fallbackFreeTextDialog(String contents, boolean disableToggleButton) throws PDFNetException {
/* 3951 */     Bundle bundle = new Bundle();
/* 3952 */     bundle.putString("contents", contents);
/* 3953 */     bundle.putBoolean("disableToggleButton", disableToggleButton);
/* 3954 */     bundle.putStringArray("PDFTRON_KEYS", new String[] { "contents", "disableToggleButton" });
/* 3955 */     if (onInterceptAnnotationHandling(this.mAnnot, bundle)) {
/*      */       return;
/*      */     }
/*      */     
/* 3959 */     removeAnnotView();
/*      */     
/* 3961 */     boolean enableSave = true;
/* 3962 */     if (contents == null && this.mAnnot != null) {
/* 3963 */       boolean shouldUnlockRead = false;
/*      */       try {
/* 3965 */         this.mPdfViewCtrl.docLockRead();
/* 3966 */         shouldUnlockRead = true;
/* 3967 */         Markup m = new Markup(this.mAnnot);
/* 3968 */         contents = m.getContents();
/*      */       } finally {
/* 3970 */         if (shouldUnlockRead) {
/* 3971 */           this.mPdfViewCtrl.docUnlockRead();
/*      */         }
/*      */       } 
/* 3974 */       enableSave = false;
/*      */     } 
/* 3976 */     final DialogFreeTextNote d = new DialogFreeTextNote(this.mPdfViewCtrl, contents, enableSave);
/* 3977 */     d.addTextWatcher(this);
/* 3978 */     d.setAnnotNoteListener(this);
/* 3979 */     d.setOnDismissListener(new DialogInterface.OnDismissListener()
/*      */         {
/*      */           public void onDismiss(DialogInterface dialog) {
/* 3982 */             AnnotEdit.this.mInEditMode = false;
/* 3983 */             ToolManager toolManager = (ToolManager)AnnotEdit.this.mPdfViewCtrl.getToolManager();
/* 3984 */             if (AnnotEdit.this.mAnnotButtonPressed == -1) {
/* 3985 */               AnnotEdit.this.updateFreeText(d.getNote());
/*      */ 
/*      */               
/* 3988 */               if (AnnotEdit.this.mUpdateFreeTextEditMode) {
/* 3989 */                 SharedPreferences settings = Tool.getToolPreferences(AnnotEdit.this.mPdfViewCtrl.getContext());
/* 3990 */                 SharedPreferences.Editor editor = settings.edit();
/* 3991 */                 editor.putInt("annotation_free_text_preference_editing", AnnotEdit.this.mCurrentFreeTextEditMode);
/* 3992 */                 editor.apply();
/*      */               } 
/*      */ 
/*      */               
/* 3996 */               if (AnnotEdit.this.mInlineEditText != null && AnnotEdit.this.mInlineEditText.isEditing().booleanValue()) {
/* 3997 */                 AnnotEdit.this.mInlineEditText.close(true);
/* 3998 */                 AnnotEdit.this.mInlineEditText = null;
/*      */               } 
/* 4000 */               if (!toolManager.isEditFreeTextOnTap()) {
/* 4001 */                 AnnotEdit.this.mHideCtrlPts = false;
/* 4002 */                 AnnotEdit.this.setCtrlPts();
/*      */                 
/* 4004 */                 Handler handler = new Handler();
/* 4005 */                 handler.postDelayed(new Runnable()
/*      */                     {
/*      */                       public void run() {
/* 4008 */                         AnnotEdit.this.showMenu(AnnotEdit.this.getAnnotRect());
/*      */                       }
/*      */                     },  300L);
/*      */               } else {
/* 4012 */                 AnnotEdit.this.unsetAnnot();
/* 4013 */                 AnnotEdit.this.closeQuickMenu();
/* 4014 */                 AnnotEdit.this.mNextToolMode = ToolManager.ToolMode.PAN;
/*      */               } 
/* 4016 */             } else if (AnnotEdit.this.mAnnotButtonPressed == -3) {
/*      */               
/* 4018 */               AnnotEdit.this.mCurrentFreeTextEditMode = 1;
/* 4019 */               AnnotEdit.this.mUpdateFreeTextEditMode = true;
/*      */ 
/*      */ 
/*      */               
/* 4023 */               if (AnnotEdit.this.mInlineEditText != null) {
/* 4024 */                 AnnotEdit.this.mInlineEditText.setContents(d.getNote());
/*      */               } else {
/*      */                 
/*      */                 try {
/* 4028 */                   AnnotEdit.this.initInlineFreeTextEditing(d.getNote());
/* 4029 */                 } catch (Exception e) {
/* 4030 */                   AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */                 }
/*      */               
/*      */               } 
/*      */             } else {
/*      */               
/* 4036 */               if (AnnotEdit.this.mAnnot != null && AnnotEdit.this.mInlineEditText != null && AnnotEdit.this.mInlineEditText.isEditing().booleanValue()) {
/* 4037 */                 AnnotEdit.this.mInlineEditText.close(true);
/* 4038 */                 AnnotEdit.this.mInlineEditText = null;
/*      */ 
/*      */                 
/*      */                 try {
/* 4042 */                   AnnotEdit.this.mPdfViewCtrl.showAnnotation(AnnotEdit.this.mAnnot);
/* 4043 */                   AnnotEdit.this.mPdfViewCtrl.update(AnnotEdit.this.mAnnot, AnnotEdit.this.mAnnotPageNum);
/* 4044 */                   AnnotEdit.this.mPdfViewCtrl.invalidate();
/* 4045 */                 } catch (Exception e) {
/* 4046 */                   AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */                 } 
/* 4048 */                 Utils.deleteCacheFile(AnnotEdit.this.mPdfViewCtrl.getContext(), AnnotEdit.this.mCacheFileName);
/*      */               } 
/* 4050 */               if (!toolManager.isEditFreeTextOnTap()) {
/* 4051 */                 AnnotEdit.this.mHideCtrlPts = false;
/* 4052 */                 AnnotEdit.this.setCtrlPts();
/*      */                 
/* 4054 */                 Handler handler = new Handler();
/* 4055 */                 handler.postDelayed(new Runnable()
/*      */                     {
/*      */                       public void run() {
/* 4058 */                         AnnotEdit.this.showMenu(AnnotEdit.this.getAnnotRect());
/*      */                       }
/*      */                     },  300L);
/*      */               } else {
/* 4062 */                 AnnotEdit.this.unsetAnnot();
/* 4063 */                 AnnotEdit.this.closeQuickMenu();
/* 4064 */                 AnnotEdit.this.mNextToolMode = ToolManager.ToolMode.PAN;
/*      */               } 
/*      */               
/* 4067 */               if (AnnotEdit.this.mUpdateFreeTextEditMode) {
/* 4068 */                 SharedPreferences settings = Tool.getToolPreferences(AnnotEdit.this.mPdfViewCtrl.getContext());
/* 4069 */                 SharedPreferences.Editor editor = settings.edit();
/* 4070 */                 editor.putInt("annotation_free_text_preference_editing", AnnotEdit.this.mCurrentFreeTextEditMode);
/* 4071 */                 editor.apply();
/*      */               } 
/*      */             } 
/* 4074 */             AnnotEdit.this.mAnnotButtonPressed = 0;
/*      */           }
/*      */         });
/* 4077 */     d.show();
/* 4078 */     this.mStoredTimeStamp = System.currentTimeMillis();
/* 4079 */     if (disableToggleButton) {
/* 4080 */       d.disableToggleButton();
/*      */     }
/*      */   }
/*      */   
/*      */   private void initInlineFreeTextEditing(String interimText) throws PDFNetException {
/* 4085 */     if (this.mAnnot == null) {
/*      */       return;
/*      */     }
/*      */     
/* 4089 */     removeAnnotView(false, true, false);
/*      */     
/* 4091 */     if (interimText == null) {
/* 4092 */       boolean bool = false;
/*      */       try {
/* 4094 */         this.mPdfViewCtrl.docLockRead();
/* 4095 */         bool = true;
/* 4096 */         Markup m = new Markup(this.mAnnot);
/* 4097 */         interimText = m.getContents();
/*      */       } finally {
/* 4099 */         if (bool) {
/* 4100 */           this.mPdfViewCtrl.docUnlockRead();
/*      */         }
/*      */       } 
/*      */     } 
/* 4104 */     boolean isRC = (this.mAnnotStyle != null && this.mAnnotStyle.isRCFreeText());
/* 4105 */     if (null == this.mRichTextViewModel) {
/* 4106 */       FragmentActivity activity = ((ToolManager)this.mPdfViewCtrl.getToolManager()).getCurrentActivity();
/* 4107 */       if (activity != null) {
/* 4108 */         this.mRichTextViewModel = (RichTextViewModel)ViewModelProviders.of(activity).get(RichTextViewModel.class);
/*      */       }
/*      */     } 
/* 4111 */     if (this.mRichTextViewModel != null && isRC) {
/* 4112 */       this.mRichTextViewModel.onOpenToolbar();
/*      */     }
/* 4114 */     ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 4115 */     boolean freeTextInlineToggleEnabled = toolManager.isfreeTextInlineToggleEnabled();
/* 4116 */     if (this.mAnnotStyle != null && this.mAnnotStyle.isSpacingFreeText()) {
/* 4117 */       freeTextInlineToggleEnabled = false;
/*      */     }
/* 4119 */     this.mInlineEditText = new InlineEditText(this.mPdfViewCtrl, this.mAnnot, this.mAnnotPageNum, null, freeTextInlineToggleEnabled, isRC, this);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4128 */     this.mInlineEditText.setRichTextViewModel(this.mRichTextViewModel);
/* 4129 */     this.mInlineEditText.addTextWatcher(this);
/*      */     
/* 4131 */     this.mInlineEditText.getEditText().setAutoScrollEditTextListener(new AutoScrollEditText.AutoScrollEditTextListener()
/*      */         {
/*      */           public boolean onKeyUp(int keyCode, KeyEvent event) {
/* 4134 */             if (ShortcutHelper.isCommitText(keyCode, event)) {
/*      */ 
/*      */               
/* 4137 */               AnnotEdit.this.saveAndQuitInlineEditText(false);
/*      */               
/* 4139 */               InputMethodManager imm = (InputMethodManager)AnnotEdit.this.mPdfViewCtrl.getContext().getSystemService("input_method");
/* 4140 */               if (imm != null) {
/* 4141 */                 imm.hideSoftInputFromWindow(AnnotEdit.this.mPdfViewCtrl.getRootView().getWindowToken(), 0);
/*      */               }
/*      */             } 
/* 4144 */             return true;
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean onKeyPreIme(int keyCode, KeyEvent event) {
/* 4149 */             return false;
/*      */           }
/*      */         });
/* 4152 */     if (this.mAnnotStyle != null && this.mAnnotStyle.isSpacingFreeText()) {
/* 4153 */       if (Utils.isLollipop()) {
/* 4154 */         this.mInlineEditText.getEditText().setLetterSpacing(this.mAnnotStyle.getLetterSpacing());
/* 4155 */         this.mInlineEditText.getEditText().addLetterSpacingHandle();
/*      */       } 
/* 4157 */       this.mInlineEditText.getEditText().setAutoScrollEditTextSpacingListener(new AutoScrollEditText.AutoScrollEditTextSpacingListener()
/*      */           {
/*      */             public void onUp() {
/* 4160 */               AnnotEdit.this.editTextSpacing();
/* 4161 */               AnnotEdit.this.resumeEditing(AnnotEdit.this.mInlineEditText.getEditText(), true);
/*      */             }
/*      */           });
/*      */     } 
/* 4165 */     boolean shouldUnlockRead = false;
/*      */     try {
/* 4167 */       this.mPdfViewCtrl.hideAnnotation(this.mAnnot);
/* 4168 */       this.mPdfViewCtrl.docLockRead();
/* 4169 */       shouldUnlockRead = true;
/* 4170 */       this.mPdfViewCtrl.update(this.mAnnot, this.mAnnotPageNum);
/*      */       
/* 4172 */       this.mHideCtrlPts = true;
/* 4173 */       this.mPdfViewCtrl.invalidate((int)this.mBBox.left - 1, (int)this.mBBox.top - 1, (int)this.mBBox.right + 1, (int)this.mBBox.bottom + 1);
/*      */ 
/*      */ 
/*      */       
/* 4177 */       FreeText freeText = new FreeText(this.mAnnot);
/* 4178 */       int fontSize = (int)freeText.getFontSize();
/* 4179 */       if (fontSize == 0) {
/* 4180 */         fontSize = 12;
/*      */       }
/* 4182 */       this.mInlineEditText.setTextSize(fontSize);
/*      */ 
/*      */       
/* 4185 */       Markup m = new Markup(this.mAnnot);
/* 4186 */       int alpha = (int)(m.getOpacity() * 255.0D);
/*      */ 
/*      */ 
/*      */       
/* 4190 */       if (freeText.getTextColorCompNum() == 3) {
/* 4191 */         ColorPt fillColorPt = freeText.getTextColor();
/* 4192 */         int r = (int)Math.round(fillColorPt.get(0) * 255.0D);
/* 4193 */         int g = (int)Math.round(fillColorPt.get(1) * 255.0D);
/* 4194 */         int b = (int)Math.round(fillColorPt.get(2) * 255.0D);
/* 4195 */         int fontColor = Color.argb(alpha, r, g, b);
/* 4196 */         this.mInlineEditText.setTextColor(fontColor);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 4201 */       int fillColor = 0;
/* 4202 */       if (freeText.getColorCompNum() == 3) {
/* 4203 */         ColorPt fillColorPt = freeText.getColorAsRGB();
/* 4204 */         int r = (int)Math.round(fillColorPt.get(0) * 255.0D);
/* 4205 */         int g = (int)Math.round(fillColorPt.get(1) * 255.0D);
/* 4206 */         int b = (int)Math.round(fillColorPt.get(2) * 255.0D);
/* 4207 */         fillColor = Color.argb(alpha, r, g, b);
/*      */       } 
/* 4209 */       this.mInlineEditText.setBackgroundColor(fillColor);
/*      */ 
/*      */       
/* 4212 */       if (isRC) {
/* 4213 */         this.mInlineEditText.setHTMLContents(this.mAnnotStyle.getTextHTMLContent());
/*      */       } else {
/* 4215 */         this.mInlineEditText.setDelaySetContents(interimText);
/*      */       } 
/* 4217 */       this.mStoredTimeStamp = System.currentTimeMillis();
/*      */ 
/*      */       
/* 4220 */       Bundle bundle = new Bundle();
/* 4221 */       bundle.putString("METHOD_FROM", "initInlineFreeTextEditing");
/* 4222 */       bundle.putString("text", interimText);
/* 4223 */       bundle.putStringArray("PDFTRON_KEYS", new String[] { "text" });
/* 4224 */       onInterceptAnnotationHandling(this.mAnnot, bundle);
/* 4225 */     } catch (Exception e) {
/* 4226 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } finally {
/* 4228 */       if (shouldUnlockRead) {
/* 4229 */         this.mPdfViewCtrl.docUnlockRead();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void resizeFreeText(FreeText freeText, Rect adjustedAnnotRect, boolean isRightToLeft) throws PDFNetException {
/* 4235 */     if (this.mAnnotStyle != null && this.mAnnotStyle.isSpacingFreeText()) {
/*      */       return;
/*      */     }
/* 4238 */     double left = adjustedAnnotRect.getX1();
/* 4239 */     double top = adjustedAnnotRect.getY1();
/* 4240 */     double right = adjustedAnnotRect.getX2();
/* 4241 */     double bottom = adjustedAnnotRect.getY2();
/*      */     
/* 4243 */     boolean isCallout = AnnotUtils.isCallout((Annot)freeText);
/* 4244 */     Rect temp = freeText.getContentRect();
/*      */ 
/*      */     
/* 4247 */     if (((ToolManager)this.mPdfViewCtrl.getToolManager()).isAutoResizeFreeText()) {
/* 4248 */       double[] pt1s = this.mPdfViewCtrl.convPagePtToScreenPt(left, top, this.mAnnotPageNum);
/* 4249 */       double[] pt2s = this.mPdfViewCtrl.convPagePtToScreenPt(right, bottom, this.mAnnotPageNum);
/* 4250 */       double scLeft = pt1s[0];
/* 4251 */       double scTop = pt1s[1];
/* 4252 */       double scRight = pt2s[0];
/* 4253 */       double scBottom = pt2s[1];
/*      */ 
/*      */       
/* 4256 */       double x = Math.min(scLeft, scRight);
/* 4257 */       double y = Math.min(scTop, scBottom);
/* 4258 */       if (isRightToLeft) {
/* 4259 */         x = Math.max(scLeft, scRight);
/*      */       }
/*      */       
/* 4262 */       double[] pt3s = this.mPdfViewCtrl.convScreenPtToPagePt(x, y, this.mAnnotPageNum);
/* 4263 */       Point targetPoint = new Point(pt3s[0], pt3s[1]);
/*      */       
/* 4265 */       Rect bbox = FreeTextCreate.getTextBBoxOnPage(this.mPdfViewCtrl, this.mAnnotPageNum, targetPoint);
/* 4266 */       if (bbox != null) {
/* 4267 */         if (isCallout) {
/* 4268 */           freeText.setRect(bbox);
/* 4269 */           freeText.setContentRect(bbox);
/*      */         } else {
/* 4271 */           freeText.resize(bbox);
/*      */         } 
/* 4273 */         freeText.refreshAppearance();
/*      */         
/* 4275 */         Rect resizeRect = FreeTextCreate.calcFreeTextBBox(this.mPdfViewCtrl, freeText, this.mAnnotPageNum, isRightToLeft, targetPoint);
/*      */         
/* 4277 */         if (isCallout) {
/* 4278 */           resizeCallout(freeText, temp, resizeRect);
/*      */         } else {
/* 4280 */           freeText.resize(resizeRect);
/*      */         } 
/* 4282 */         freeText.refreshAppearance();
/*      */       }
/*      */     
/* 4285 */     } else if (isCallout) {
/* 4286 */       resizeCallout(freeText, temp, adjustedAnnotRect);
/*      */     } else {
/* 4288 */       freeText.setRect(adjustedAnnotRect);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void resizeCallout(FreeText callout, Rect originalAnnotRect, Rect adjustedAnnotRect) throws PDFNetException {
/* 4296 */     adjustExtraFreeTextProps(originalAnnotRect, adjustedAnnotRect);
/* 4297 */     callout.setRect(adjustedAnnotRect);
/* 4298 */     callout.setContentRect(adjustedAnnotRect);
/* 4299 */     callout.refreshAppearance();
/* 4300 */     setCtrlPts();
/*      */   }
/*      */   
/*      */   private void updateFreeText(String contents) {
/* 4304 */     Bundle bundle = new Bundle();
/* 4305 */     bundle.putString("METHOD_FROM", "updateFreeText");
/* 4306 */     bundle.putStringArray("PDFTRON_KEYS", new String[] { "contents" });
/* 4307 */     bundle.putString("contents", contents);
/* 4308 */     if (this.mAnnot == null || onInterceptAnnotationHandling(this.mAnnot, bundle)) {
/*      */       return;
/*      */     }
/* 4311 */     boolean shouldUnlock = false;
/*      */ 
/*      */     
/*      */     try {
/* 4315 */       this.mPdfViewCtrl.docLock(true);
/* 4316 */       shouldUnlock = true;
/*      */       
/* 4318 */       FreeText freeText = new FreeText(this.mAnnot);
/*      */ 
/*      */       
/* 4321 */       Rect defaultRect = null;
/* 4322 */       ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 4323 */       if (!toolManager.isDeleteEmptyFreeText() && toolManager.isAutoResizeFreeText()) {
/* 4324 */         defaultRect = FreeTextCreate.getDefaultRect(freeText);
/*      */       }
/*      */       
/* 4327 */       String oldContents = freeText.getContents();
/* 4328 */       if (this.mInlineEditText != null && this.mInlineEditText.getRichEditor().getVisibility() == 0) {
/* 4329 */         contents = Html.fromHtml(contents).toString().trim();
/*      */       }
/* 4331 */       if (!Utils.isNullOrEmpty(contents)) {
/* 4332 */         raiseAnnotationPreModifyEvent(this.mAnnot, this.mAnnotPageNum);
/* 4333 */         freeText.setContents(contents);
/* 4334 */         boolean isRightToLeft = Utils.isRightToLeftString(contents);
/* 4335 */         if (isRightToLeft) {
/* 4336 */           freeText.setQuaddingFormat(2);
/* 4337 */         } else if (Utils.isLeftToRightString(contents)) {
/* 4338 */           freeText.setQuaddingFormat(0);
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 4344 */         Rect contentRect = freeText.getContentRect();
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 4349 */         RectF contentBox = new RectF((float)contentRect.getX1(), (float)contentRect.getY1(), (float)contentRect.getX2(), (float)contentRect.getY2());
/*      */ 
/*      */ 
/*      */         
/* 4353 */         double right = contentBox.right;
/* 4354 */         double left = contentBox.left;
/* 4355 */         double top = contentBox.bottom;
/* 4356 */         double bottom = contentBox.top;
/*      */         
/* 4358 */         if (this.mInlineEditText != null) {
/*      */ 
/*      */           
/* 4361 */           int pageRotation = this.mPdfViewCtrl.getDoc().getPage(this.mAnnotPageNum).getRotation();
/* 4362 */           int viewRotation = this.mPdfViewCtrl.getPageRotation();
/* 4363 */           int annotRotation = (pageRotation + viewRotation) % 4 * 90;
/*      */ 
/*      */           
/* 4366 */           AutoScrollEditText autoScrollEditText = this.mInlineEditText.getEditText();
/* 4367 */           int editTextHeight = autoScrollEditText.getHeight();
/* 4368 */           int editTextWidth = autoScrollEditText.getWidth();
/*      */ 
/*      */ 
/*      */           
/* 4372 */           float annotBBoxWidth = contentBox.width();
/* 4373 */           if (annotRotation == 90 || annotRotation == 270) {
/* 4374 */             annotBBoxWidth = contentBox.height();
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 4380 */           float convRatio = annotBBoxWidth / editTextWidth;
/* 4381 */           int heightInPageUnits = (int)(editTextHeight * convRatio);
/*      */           
/* 4383 */           if (annotRotation == 0) {
/* 4384 */             bottom = (contentBox.bottom - heightInPageUnits);
/* 4385 */             if (bottom > contentBox.top) {
/* 4386 */               bottom = contentBox.top;
/*      */             }
/* 4388 */           } else if (annotRotation == 90) {
/* 4389 */             right = (contentBox.left + heightInPageUnits);
/* 4390 */             if (right < contentBox.right) {
/* 4391 */               right = contentBox.right;
/*      */             }
/* 4393 */           } else if (annotRotation == 180) {
/* 4394 */             top = (contentBox.top + heightInPageUnits);
/* 4395 */             if (top < contentBox.bottom) {
/* 4396 */               top = contentBox.bottom;
/*      */             }
/*      */           } else {
/* 4399 */             left = (contentBox.right - heightInPageUnits);
/* 4400 */             if (left > contentBox.left) {
/* 4401 */               left = contentBox.left;
/*      */             }
/*      */           } 
/*      */           
/* 4405 */           freeText.refreshAppearance();
/*      */         } 
/* 4407 */         Rect adjustedAnnotRect = new Rect(left, top, right, bottom);
/* 4408 */         adjustedAnnotRect.normalize();
/* 4409 */         resizeFreeText(freeText, adjustedAnnotRect, isRightToLeft);
/*      */ 
/*      */ 
/*      */         
/* 4413 */         String fontName = "";
/* 4414 */         Obj freeTextObj = freeText.getSDFObj();
/* 4415 */         Obj drDict = freeTextObj.findObj("DR");
/* 4416 */         if (drDict != null && drDict.isDict()) {
/* 4417 */           Obj fontDict = drDict.findObj("Font");
/* 4418 */           if (fontDict != null && fontDict.isDict()) {
/* 4419 */             DictIterator fItr = fontDict.getDictIterator();
/* 4420 */             if (fItr.hasNext()) {
/* 4421 */               Font f = new Font(fItr.value());
/* 4422 */               fontName = f.getName();
/*      */             } 
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 4429 */         if (!fontName.equals("")) {
/*      */ 
/*      */           
/* 4432 */           String pdftronFontName = Tool.findPDFTronFontName(this.mPdfViewCtrl.getContext(), fontName);
/*      */ 
/*      */ 
/*      */           
/* 4436 */           if (!pdftronFontName.equals("")) {
/* 4437 */             String fontDRName = "F0";
/*      */ 
/*      */             
/* 4440 */             Obj annotObj = freeText.getSDFObj();
/* 4441 */             drDict = annotObj.putDict("DR");
/* 4442 */             Obj fontDict = drDict.putDict("Font");
/*      */ 
/*      */             
/* 4445 */             Font font = Font.create((Doc)this.mPdfViewCtrl.getDoc(), pdftronFontName, contents);
/* 4446 */             fontDict.put(fontDRName, font.GetSDFObj());
/*      */           }
/*      */           else {
/*      */             
/* 4450 */             String DA = freeText.getDefaultAppearance();
/* 4451 */             int slashPosition = DA.indexOf("/", 0);
/*      */ 
/*      */             
/* 4454 */             if (slashPosition > 0) {
/* 4455 */               String beforeSlash = DA.substring(0, slashPosition);
/* 4456 */               String afterSlash = DA.substring(slashPosition);
/* 4457 */               String afterFont = afterSlash.substring(afterSlash.indexOf(" "));
/* 4458 */               String updatedDA = beforeSlash + "/helv" + afterFont;
/*      */               
/* 4460 */               freeText.setDefaultAppearance(updatedDA);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */         
/* 4465 */         buildAnnotBBox();
/* 4466 */         this.mPdfViewCtrl.showAnnotation(this.mAnnot);
/* 4467 */         this.mAnnot.refreshAppearance();
/*      */ 
/*      */         
/* 4470 */         if (this.mAnnotStyle.isSpacingFreeText() && this.mInlineEditText != null) {
/* 4471 */           AnnotUtils.applyCustomFreeTextAppearance(this.mPdfViewCtrl, this.mInlineEditText
/* 4472 */               .getEditText(), this.mAnnot, this.mAnnotPageNum);
/*      */ 
/*      */           
/* 4475 */           this.mAnnotStyle = AnnotUtils.getAnnotStyle(this.mAnnot);
/*      */         } 
/*      */         
/* 4478 */         this.mPdfViewCtrl.update(this.mAnnot, this.mAnnotPageNum);
/* 4479 */         raiseAnnotationModifiedEvent(this.mAnnot, this.mAnnotPageNum, bundle);
/* 4480 */         Utils.deleteCacheFile(this.mPdfViewCtrl.getContext(), this.mCacheFileName);
/*      */       }
/* 4482 */       else if (toolManager.isDeleteEmptyFreeText()) {
/*      */         
/* 4484 */         raiseAnnotationPreRemoveEvent(this.mAnnot, this.mAnnotPageNum);
/* 4485 */         Page page = this.mPdfViewCtrl.getDoc().getPage(this.mAnnotPageNum);
/* 4486 */         page.annotRemove(this.mAnnot);
/* 4487 */         this.mPdfViewCtrl.update(this.mAnnot, this.mAnnotPageNum);
/*      */ 
/*      */         
/* 4490 */         raiseAnnotationRemovedEvent(this.mAnnot, this.mAnnotPageNum);
/* 4491 */         Utils.deleteCacheFile(this.mPdfViewCtrl.getContext(), this.mCacheFileName);
/* 4492 */         if (sDebug) Log.d(TAG, "update free text");
/*      */         
/* 4494 */         unsetAnnot();
/*      */       } else {
/*      */         
/* 4497 */         if (!Utils.isNullOrEmpty(oldContents)) {
/*      */           
/* 4499 */           raiseAnnotationPreModifyEvent(this.mAnnot, this.mAnnotPageNum);
/*      */           
/* 4501 */           if (defaultRect != null) {
/* 4502 */             freeText.setRect(defaultRect);
/*      */           }
/* 4504 */           freeText.setContents("");
/* 4505 */           freeText.refreshAppearance();
/* 4506 */           raiseAnnotationModifiedEvent(this.mAnnot, this.mAnnotPageNum, bundle);
/*      */         } 
/* 4508 */         this.mPdfViewCtrl.showAnnotation(this.mAnnot);
/* 4509 */         this.mPdfViewCtrl.update(this.mAnnot, this.mAnnotPageNum);
/*      */       }
/*      */     
/* 4512 */     } catch (Exception e) {
/* 4513 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/* 4514 */       dismissUpdatingFreeText();
/*      */     } finally {
/* 4516 */       if (shouldUnlock) {
/* 4517 */         this.mPdfViewCtrl.docUnlock();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void dismissUpdatingFreeText() {
/* 4523 */     if (this.mInlineEditText != null) {
/* 4524 */       this.mInlineEditText.close(true);
/*      */     }
/* 4526 */     if (this.mPdfViewCtrl == null || this.mAnnot == null) {
/*      */       return;
/*      */     }
/*      */     try {
/* 4530 */       this.mPdfViewCtrl.showAnnotation(this.mAnnot);
/* 4531 */       this.mAnnot.refreshAppearance();
/* 4532 */       this.mPdfViewCtrl.update(this.mAnnot, this.mAnnotPageNum);
/* 4533 */     } catch (Exception e) {
/* 4534 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } 
/*      */     
/* 4537 */     Utils.deleteCacheFile(this.mPdfViewCtrl.getContext(), this.mCacheFileName);
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
/* 4553 */     if (this.mPdfViewCtrl == null || this.mAnnot == null) {
/*      */       return;
/*      */     }
/*      */     
/* 4557 */     long currentTimeStamp = System.currentTimeMillis();
/* 4558 */     if (currentTimeStamp - this.mStoredTimeStamp > 3000L) {
/* 4559 */       this.mStoredTimeStamp = currentTimeStamp;
/* 4560 */       if (s != null && s.length() > 0) {
/* 4561 */         Bundle bundle = new Bundle();
/* 4562 */         bundle.putStringArray("PDFTRON_KEYS", new String[] { "contents" });
/* 4563 */         bundle.putCharSequence("contents", s);
/* 4564 */         if (onInterceptAnnotationHandling(this.mAnnot, bundle)) {
/*      */           return;
/*      */         }
/*      */         try {
/* 4568 */           FreeTextCacheStruct freeTextCacheStruct = new FreeTextCacheStruct();
/* 4569 */           freeTextCacheStruct.contents = s.toString();
/* 4570 */           freeTextCacheStruct.pageNum = this.mAnnotPageNum;
/* 4571 */           Rect rect = this.mPdfViewCtrl.getScreenRectForAnnot(this.mAnnot, this.mAnnotPageNum);
/* 4572 */           freeTextCacheStruct.x = (float)Math.min(rect.getX1(), rect.getX2());
/* 4573 */           freeTextCacheStruct.y = (float)Math.min(rect.getX2(), rect.getY2());
/* 4574 */           AnnotUtils.saveFreeTextCache(freeTextCacheStruct, this.mPdfViewCtrl);
/* 4575 */         } catch (Exception e) {
/* 4576 */           AnalyticsHandlerAdapter.getInstance().sendException(e);
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
/*      */   public void afterTextChanged(Editable s) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getModeAHLabel() {
/* 4595 */     if (this.mStamperToolSelected) {
/* 4596 */       return 106;
/*      */     }
/* 4598 */     return super.getModeAHLabel();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getEffectCtrlPointId(float x, float y) {
/* 4619 */     if (this.mHandleEffCtrlPtsDisabled) {
/* 4620 */       return -1;
/*      */     }
/*      */     
/* 4623 */     int effCtrlPtId = -1;
/* 4624 */     float thresh = this.mCtrlRadius * 2.25F;
/* 4625 */     float shortest_dist = -1.0F;
/* 4626 */     for (int i = 0; i < 8; i++) {
/* 4627 */       if (isAnnotResizable()) {
/*      */         
/* 4629 */         float s = (getVisualCtrlPts()[i]).x;
/* 4630 */         float t = (getVisualCtrlPts()[i]).y;
/*      */         
/* 4632 */         float dist = (x - s) * (x - s) + (y - t) * (y - t);
/* 4633 */         dist = (float)Math.sqrt(dist);
/* 4634 */         if (dist <= thresh && (dist < shortest_dist || shortest_dist < 0.0F)) {
/* 4635 */           effCtrlPtId = i;
/* 4636 */           shortest_dist = dist;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 4643 */     if (!this.mAnnotIsTextMarkup && effCtrlPtId == -1 && this.mBBox.contains(x, y)) {
/* 4644 */       effCtrlPtId = -2;
/*      */     }
/*      */     
/* 4647 */     return effCtrlPtId;
/*      */   }
/*      */   
/*      */   protected PointF[] getVisualCtrlPts() {
/* 4651 */     if (this.mSelectionBoxMargin > 0) {
/* 4652 */       return this.mCtrlPtsInflated;
/*      */     }
/* 4654 */     return this.mCtrlPts;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isCtrlPtsHidden() {
/* 4661 */     return this.mHideCtrlPts;
/*      */   }
/*      */ 
/*      */   
/*      */   public void onChangeAnnotThickness(float thickness, boolean done) {
/* 4666 */     if (this.mAnnotView != null) {
/* 4667 */       this.mAnnotView.updateThickness(thickness);
/*      */     }
/* 4669 */     if (done) {
/*      */       
/* 4671 */       editThickness(thickness);
/* 4672 */       updateAnnotViewBitmap();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void onChangeAnnotTextSize(float textSize, boolean done) {
/* 4678 */     if (this.mAnnotView != null) {
/* 4679 */       this.mAnnotView.updateTextSize(textSize);
/*      */     }
/* 4681 */     if (done) {
/* 4682 */       editTextSize(textSize);
/* 4683 */       updateAnnotViewBitmap();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void onChangeAnnotOpacity(float opacity, boolean done) {
/* 4689 */     if (this.mAnnotView != null) {
/* 4690 */       this.mAnnotView.updateOpacity(opacity);
/*      */     }
/* 4692 */     if (done) {
/* 4693 */       editOpacity(opacity);
/* 4694 */       updateAnnotViewBitmap();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void onChangeAnnotStrokeColor(int color) {
/* 4700 */     if (this.mAnnot == null) {
/*      */       return;
/*      */     }
/*      */     
/* 4704 */     if (this.mAnnotView != null) {
/* 4705 */       this.mAnnotView.updateColor(color);
/*      */     }
/*      */     
/* 4708 */     editColor(color);
/* 4709 */     updateAnnotViewBitmap();
/*      */     
/*      */     try {
/* 4712 */       if (this.mAnnot.getType() == 4 || this.mAnnot.getType() == 5) {
/* 4713 */         Markup markup = new Markup(this.mAnnot);
/* 4714 */         ColorPt fillColorPt = markup.getInteriorColor();
/* 4715 */         int fillColor = Utils.colorPt2color(fillColorPt);
/* 4716 */         if (fillColor == 0) {
/* 4717 */           updateQuickMenuStyleColor(color);
/*      */         }
/*      */       } else {
/* 4720 */         updateQuickMenuStyleColor(color);
/*      */       } 
/* 4722 */     } catch (PDFNetException e) {
/* 4723 */       AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void onChangeAnnotFillColor(int color) {
/* 4729 */     if (this.mAnnotView != null) {
/* 4730 */       this.mAnnotView.updateFillColor(color);
/*      */     }
/* 4732 */     editFillColor(color);
/* 4733 */     updateAnnotViewBitmap();
/*      */     
/* 4735 */     if (color != 0) {
/* 4736 */       updateQuickMenuStyleColor(color);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void onChangeAnnotIcon(String icon) {
/* 4742 */     if (this.mAnnotView != null) {
/* 4743 */       this.mAnnotView.updateIcon(icon);
/*      */     }
/* 4745 */     editIcon(icon);
/* 4746 */     updateAnnotViewBitmap();
/*      */   }
/*      */ 
/*      */   
/*      */   public void onChangeAnnotFont(FontResource font) {
/* 4751 */     if (this.mAnnotView != null) {
/* 4752 */       this.mAnnotView.updateFont(font);
/*      */     }
/* 4754 */     editFont(font.getPDFTronName());
/* 4755 */     updateAnnotViewBitmap();
/*      */   }
/*      */ 
/*      */   
/*      */   public void onChangeAnnotTextColor(int textColor) {
/* 4760 */     if (this.mAnnotView != null) {
/* 4761 */       this.mAnnotView.updateTextColor(textColor);
/*      */     }
/* 4763 */     editTextColor(textColor);
/* 4764 */     updateAnnotViewBitmap();
/*      */   }
/*      */ 
/*      */   
/*      */   public void onChangeRulerProperty(RulerItem rulerItem) {
/* 4769 */     if (this.mAnnotView != null) {
/* 4770 */       this.mAnnotView.updateRulerItem(rulerItem);
/*      */     }
/*      */     
/* 4773 */     editRuler(rulerItem);
/*      */   }
/*      */ 
/*      */   
/*      */   public void onChangeOverlayText(String overlayText) {
/* 4778 */     editRedactionOverlayText(overlayText);
/*      */   }
/*      */ 
/*      */   
/*      */   public void onChangeSnapping(boolean snap) {
/* 4783 */     ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 4784 */     toolManager.setSnappingEnabledForMeasurementTools(snap);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void onChangeRichContentEnabled(boolean enabled) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void onChangeDateFormat(String dateFormat) {
/* 4794 */     if (this.mAnnotStyle != null && this.mAnnotStyle.isDateFreeText() && dateFormat != null) {
/* 4795 */       editFreeTextDateFormat(dateFormat);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean hasAnnotSelected() {
/* 4805 */     return (this.mAnnot != null);
/*      */   }
/*      */   
/*      */   private void redactAnnot() {
/* 4809 */     if (this.mAnnot == null) {
/*      */       return;
/*      */     }
/* 4812 */     Bundle bundle = new Bundle();
/* 4813 */     bundle.putString("METHOD_FROM", "redactAnnot");
/* 4814 */     if (onInterceptAnnotationHandling(this.mAnnot, bundle)) {
/*      */       return;
/*      */     }
/*      */     
/* 4818 */     boolean shouldUnlock = false;
/*      */     try {
/* 4820 */       this.mPdfViewCtrl.docLock(true);
/* 4821 */       shouldUnlock = true;
/*      */ 
/*      */ 
/*      */       
/* 4825 */       Redaction redactAnnot = new Redaction(this.mAnnot);
/* 4826 */       ColorPt fillColorPt = redactAnnot.getInteriorColor();
/* 4827 */       String overlayText = redactAnnot.getOverlayText();
/* 4828 */       if (overlayText == null) {
/* 4829 */         overlayText = "";
/*      */       }
/* 4831 */       int sz = redactAnnot.getQuadPointCount();
/* 4832 */       ArrayList<Redactor.Redaction> rarr = new ArrayList<>();
/* 4833 */       double minX = 0.0D;
/* 4834 */       double maxX = 0.0D;
/* 4835 */       double minY = 0.0D;
/* 4836 */       double maxY = 0.0D;
/* 4837 */       if (sz > 0) {
/* 4838 */         for (int i = 0; i < sz; i++) {
/* 4839 */           QuadPoint qp = redactAnnot.getQuadPoint(i);
/* 4840 */           Rect quadRect = AnnotUtils.quadToRect(qp);
/*      */           
/* 4842 */           if (i == 0) {
/* 4843 */             minX = Math.min(quadRect.getX1(), quadRect.getX2());
/* 4844 */             maxX = Math.max(quadRect.getX1(), quadRect.getX2());
/* 4845 */             minY = Math.min(quadRect.getY1(), quadRect.getY2());
/* 4846 */             maxY = Math.max(quadRect.getY1(), quadRect.getY2());
/*      */           } else {
/* 4848 */             minX = Math.min(minX, Math.min(quadRect.getX1(), quadRect.getX2()));
/* 4849 */             maxX = Math.max(maxX, Math.max(quadRect.getX1(), quadRect.getX2()));
/* 4850 */             minY = Math.min(minY, Math.min(quadRect.getY1(), quadRect.getY2()));
/* 4851 */             maxY = Math.max(maxY, Math.max(quadRect.getY1(), quadRect.getY2()));
/*      */           } 
/*      */           
/* 4854 */           rarr.add(new Redactor.Redaction(this.mAnnotPageNum, quadRect, false, overlayText));
/*      */         } 
/*      */       }
/*      */       
/* 4858 */       ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 4859 */       UndoRedoManager undoRedoManager = toolManager.getUndoRedoManger();
/*      */       
/* 4861 */       JSONObject jsonObject = new JSONObject();
/* 4862 */       if (undoRedoManager != null) {
/* 4863 */         jsonObject = undoRedoManager.getAnnotSnapshot(this.mAnnot, this.mAnnotPageNum);
/*      */       }
/*      */       
/* 4866 */       deleteAnnot();
/*      */       
/* 4868 */       Redactor.Appearance app = new Redactor.Appearance();
/* 4869 */       app.useOverlayText = true;
/* 4870 */       app.positiveOverlayColor = fillColorPt;
/* 4871 */       app.redactionOverlay = true;
/* 4872 */       app.border = false;
/* 4873 */       Redactor.redact((Doc)this.mPdfViewCtrl.getDoc(), rarr.<Redactor.Redaction>toArray(new Redactor.Redaction[rarr.size()]), app, false, false);
/*      */       
/* 4875 */       this.mPdfViewCtrl.update(true);
/*      */       
/* 4877 */       if (undoRedoManager != null) {
/* 4878 */         undoRedoManager.onRedaction(jsonObject);
/*      */       }
/* 4880 */     } catch (Exception e) {
/* 4881 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } finally {
/* 4883 */       if (shouldUnlock) {
/* 4884 */         this.mPdfViewCtrl.docUnlock();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void playSoundAnnot() {
/* 4890 */     if (this.mAnnot == null) {
/*      */       return;
/*      */     }
/* 4893 */     Bundle bundle = new Bundle();
/* 4894 */     bundle.putString("METHOD_FROM", "playSoundAnnot");
/* 4895 */     if (onInterceptAnnotationHandling(this.mAnnot, bundle)) {
/*      */       return;
/*      */     }
/*      */     
/* 4899 */     ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 4900 */     FragmentActivity activity = toolManager.getCurrentActivity();
/* 4901 */     if (activity == null) {
/*      */       return;
/*      */     }
/*      */     
/* 4905 */     String audioPath = activity.getFilesDir().getAbsolutePath();
/* 4906 */     audioPath = audioPath + "/audiorecord.out";
/* 4907 */     Integer sampleRate = null;
/* 4908 */     int encodingBitRate = 8;
/* 4909 */     int numChannel = 1;
/*      */     
/* 4911 */     boolean shouldUnlockRead = false;
/*      */     try {
/* 4913 */       this.mPdfViewCtrl.docLockRead();
/* 4914 */       shouldUnlockRead = true;
/*      */       
/* 4916 */       Sound sound = new Sound(this.mAnnot);
/* 4917 */       Obj soundStream = sound.getSoundStream();
/* 4918 */       if (soundStream != null) {
/* 4919 */         Obj item = soundStream.findObj("R");
/* 4920 */         if (item != null && item.isNumber()) {
/* 4921 */           sampleRate = Integer.valueOf((int)item.getNumber());
/*      */         }
/* 4923 */         item = soundStream.findObj("B");
/* 4924 */         if (item != null && item.isNumber()) {
/* 4925 */           encodingBitRate = (int)item.getNumber();
/*      */         }
/* 4927 */         item = soundStream.findObj("C");
/* 4928 */         if (item != null && item.isNumber()) {
/* 4929 */           numChannel = (int)item.getNumber();
/*      */         }
/*      */         
/* 4932 */         Filter soundFilter = soundStream.getDecodedStream();
/* 4933 */         soundFilter.writeToFile(audioPath, false);
/*      */       } 
/* 4935 */     } catch (Exception e) {
/* 4936 */       audioPath = null;
/* 4937 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } finally {
/* 4939 */       if (shouldUnlockRead) {
/* 4940 */         this.mPdfViewCtrl.docUnlockRead();
/*      */       }
/*      */     } 
/*      */     
/* 4944 */     this.mNextToolMode = ToolManager.ToolMode.PAN;
/*      */     
/* 4946 */     if (!Utils.isNullOrEmpty(audioPath) && sampleRate != null) {
/* 4947 */       encodingBitRate = (encodingBitRate == 16) ? 2 : 3;
/* 4948 */       numChannel = (numChannel == 2) ? 12 : 4;
/* 4949 */       SoundDialogFragment fragment = SoundDialogFragment.newInstance(audioPath, sampleRate.intValue(), encodingBitRate, numChannel);
/* 4950 */       fragment.setStyle(0, R.style.CustomAppTheme);
/* 4951 */       fragment.show(activity.getSupportFragmentManager(), SoundDialogFragment.TAG);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void calibration() {
/* 4956 */     if (this.mAnnot == null || this.mPdfViewCtrl == null) {
/*      */       return;
/*      */     }
/* 4959 */     ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 4960 */     FragmentActivity activity = toolManager.getCurrentActivity();
/* 4961 */     if (activity == null) {
/*      */       return;
/*      */     }
/* 4964 */     final RulerItem rulerItem = MeasureUtils.getRulerItemFromAnnot(this.mAnnot);
/* 4965 */     if (null == rulerItem) {
/*      */       return;
/*      */     }
/* 4968 */     CalibrateDialog dialog = CalibrateDialog.newInstance(this.mAnnot.__GetHandle(), this.mAnnotPageNum, rulerItem.mRulerTranslateUnit);
/* 4969 */     dialog.setStyle(1, R.style.CustomAppTheme);
/* 4970 */     dialog.show(activity.getSupportFragmentManager(), CalibrateDialog.TAG);
/*      */     
/* 4972 */     CalibrateViewModel viewModel = (CalibrateViewModel)ViewModelProviders.of(activity).get(CalibrateViewModel.class);
/* 4973 */     viewModel.observeOnComplete((LifecycleOwner)activity, new Observer<Event<CalibrateResult>>()
/*      */         {
/*      */           public void onChanged(@Nullable Event<CalibrateResult> resultEvent) {
/* 4976 */             if (resultEvent != null && !resultEvent.hasBeenHandled()) {
/* 4977 */               CalibrateResult result = (CalibrateResult)resultEvent.getContentIfNotHandled();
/* 4978 */               RulerItem newRulerItem = null;
/* 4979 */               Annot annot = null;
/* 4980 */               int pageNum = -1;
/* 4981 */               if (result != null) {
/* 4982 */                 annot = Annot.__Create(result.annot, AnnotEdit.this.mPdfViewCtrl.getDoc());
/* 4983 */                 pageNum = result.page;
/*      */               } 
/* 4985 */               if (result != null && result.userInput != null) {
/* 4986 */                 Bundle bundle = new Bundle();
/* 4987 */                 bundle.putString("METHOD_FROM", "calibration");
/* 4988 */                 bundle.putParcelable("calibrateResult", (Parcelable)result);
/* 4989 */                 bundle.putStringArray("PDFTRON_KEYS", new String[] { "calibrateResult" });
/* 4990 */                 if (AnnotEdit.this.onInterceptAnnotationHandling(annot, bundle)) {
/*      */                   return;
/*      */                 }
/* 4993 */                 boolean shouldUnlock = false;
/*      */ 
/*      */                 
/*      */                 try {
/* 4997 */                   AnnotEdit.this.mPdfViewCtrl.docLock(true);
/* 4998 */                   shouldUnlock = true;
/*      */                   
/* 5000 */                   AnnotEdit.this.raiseAnnotationPreModifyEvent(annot, pageNum);
/*      */                   
/* 5002 */                   rulerItem.mRulerTranslateUnit = result.worldUnit;
/* 5003 */                   newRulerItem = MeasureUtils.calibrate(annot, rulerItem, result.userInput.floatValue());
/*      */                   
/* 5005 */                   AnnotUtils.refreshAnnotAppearance(AnnotEdit.this.mPdfViewCtrl.getContext(), annot);
/* 5006 */                   AnnotEdit.this.mPdfViewCtrl.update(annot, pageNum);
/*      */                   
/* 5008 */                   AnnotEdit.this.raiseAnnotationModifiedEvent(annot, pageNum, bundle);
/* 5009 */                 } catch (Exception e) {
/* 5010 */                   AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */                 } finally {
/* 5012 */                   if (shouldUnlock) {
/* 5013 */                     AnnotEdit.this.mPdfViewCtrl.docUnlock();
/*      */                   }
/*      */                 } 
/* 5016 */                 ToolManager toolManager = (ToolManager)AnnotEdit.this.mPdfViewCtrl.getToolManager();
/* 5017 */                 toolManager.selectAnnot(annot, pageNum);
/* 5018 */                 if (newRulerItem != null) {
/* 5019 */                   SharedPreferences settings = Tool.getToolPreferences(AnnotEdit.this.mPdfViewCtrl.getContext());
/* 5020 */                   SharedPreferences.Editor editor = settings.edit();
/*      */                   
/* 5022 */                   int[] types = { 1006, 1008, 1009, 1012 };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 5028 */                   for (int type : types) {
/* 5029 */                     editor.putFloat(AnnotEdit.this.getRulerBaseValueKey(type), newRulerItem.mRulerBase);
/* 5030 */                     editor.putString(AnnotEdit.this.getRulerBaseUnitKey(type), newRulerItem.mRulerBaseUnit);
/* 5031 */                     editor.putFloat(AnnotEdit.this.getRulerTranslateValueKey(type), newRulerItem.mRulerTranslate);
/* 5032 */                     editor.putString(AnnotEdit.this.getRulerTranslateUnitKey(type), newRulerItem.mRulerTranslateUnit);
/*      */                   } 
/* 5034 */                   editor.apply();
/*      */                 } 
/*      */               } else {
/*      */                 
/* 5038 */                 ToolManager toolManager = (ToolManager)AnnotEdit.this.mPdfViewCtrl.getToolManager();
/* 5039 */                 toolManager.selectAnnot(annot, pageNum);
/*      */               } 
/*      */             } 
/*      */           }
/*      */         });
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
/*      */   protected void drawSelectionBox(@NonNull Canvas canvas, float left, float top, float right, float bottom) {
/* 5058 */     DrawingUtils.drawSelectionBox(this.mPaint, this.mPdfViewCtrl.getContext(), canvas, left, top, right, bottom, this.mHasSelectionPermission);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setOriginalCtrlPtsDisabled(boolean disabled) {
/* 5069 */     this.mHandleEffCtrlPtsDisabled = disabled;
/*      */   }
/*      */   
/*      */   public static void setDebug(boolean debug) {
/* 5073 */     sDebug = debug;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public EditText getFreeTextEditText() {
/* 5083 */     return (this.mInlineEditText != null) ? (EditText)this.mInlineEditText.getEditText() : null;
/*      */   }
/*      */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\AnnotEdit.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */