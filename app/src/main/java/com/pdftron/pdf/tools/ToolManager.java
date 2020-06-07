/*      */ package com.pdftron.pdf.tools;
/*      */ 
/*      */ import android.app.AlertDialog;
/*      */ import android.content.SharedPreferences;
/*      */ import android.content.res.Configuration;
/*      */ import android.graphics.Bitmap;
/*      */ import android.graphics.Canvas;
/*      */ import android.graphics.Matrix;
/*      */ import android.graphics.PointF;
/*      */ import android.graphics.RectF;
/*      */ import android.os.Bundle;
/*      */ import android.speech.tts.TextToSpeech;
/*      */ import android.util.Pair;
/*      */ import android.util.SparseArray;
/*      */ import android.view.KeyEvent;
/*      */ import android.view.MotionEvent;
/*      */ import android.view.PointerIcon;
/*      */ import android.view.View;
/*      */ import android.view.ViewGroup;
/*      */ import android.widget.PopupWindow;
/*      */ import androidx.annotation.NonNull;
/*      */ import androidx.annotation.Nullable;
/*      */ import androidx.annotation.RequiresApi;
/*      */ import androidx.fragment.app.FragmentActivity;
/*      */ import com.pdftron.common.PDFNetException;
/*      */ import com.pdftron.pdf.Action;
/*      */ import com.pdftron.pdf.Annot;
/*      */ import com.pdftron.pdf.CurvePainter;
/*      */ import com.pdftron.pdf.PDFViewCtrl;
/*      */ import com.pdftron.pdf.annots.FileAttachment;
/*      */ import com.pdftron.pdf.config.ToolConfig;
/*      */ import com.pdftron.pdf.controls.AnnotIndicatorManger;
/*      */ import com.pdftron.pdf.controls.PageIndicatorLayout;
/*      */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*      */ import com.pdftron.pdf.utils.AnnotUtils;
/*      */ import com.pdftron.pdf.utils.Logger;
/*      */ import com.pdftron.pdf.utils.RedactionManager;
/*      */ import com.pdftron.pdf.utils.ShortcutHelper;
/*      */ import com.pdftron.pdf.utils.Utils;
/*      */ import com.pdftron.pdf.utils.ViewerUtils;
/*      */ import com.pdftron.pdf.widget.SelectionLoupe;
/*      */ import io.reactivex.android.schedulers.AndroidSchedulers;
/*      */ import io.reactivex.disposables.CompositeDisposable;
/*      */ import io.reactivex.functions.Consumer;
/*      */ import io.reactivex.schedulers.Schedulers;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.UUID;
/*      */ import java.util.concurrent.atomic.AtomicInteger;
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
/*      */ public class ToolManager
/*      */   implements PDFViewCtrl.ToolManager, PDFViewCtrl.ActionCompletedListener
/*      */ {
/*      */   private ArrayList<ToolChangedListener> mToolChangedListeners;
/*      */   private CopyOnWriteArray<OnLayoutListener> mOnLayoutListeners;
/*      */   private PreToolManagerListener mPreToolManagerListener;
/*      */   private QuickMenuListener mQuickMenuListener;
/*      */   private ArrayList<AnnotationModificationListener> mAnnotationModificationListeners;
/*      */   private ArrayList<PdfDocModificationListener> mPdfDocModificationListeners;
/*      */   private AdvancedAnnotationListener mAdvancedAnnotationListener;
/*      */   private SpecialAnnotationListener mSpecialAnnotationListener;
/*      */   private BasicAnnotationListener mBasicAnnotationListener;
/*      */   private ArrayList<AnnotationsSelectionListener> mAnnotationsSelectionListeners;
/*      */   private AnnotationToolbarListener mAnnotationToolbarListener;
/*      */   private OnGenericMotionEventListener mOnGenericMotionEventListener;
/*      */   private ExternalAnnotationManagerListener mExternalAnnotationManagerListener;
/*      */   
/*      */   public static interface AdvancedAnnotationListener
/*      */   {
/*      */     void fileAttachmentSelected(FileAttachment param1FileAttachment);
/*      */     
/*      */     void freehandStylusUsedFirstTime();
/*      */     
/*      */     void imageStamperSelected(PointF param1PointF);
/*      */     
/*      */     void imageSignatureSelected(PointF param1PointF, int param1Int, Long param1Long);
/*      */     
/*      */     void attachFileSelected(PointF param1PointF);
/*      */     
/*      */     void freeTextInlineEditingStarted();
/*      */     
/*      */     boolean newFileSelectedFromTool(String param1String);
/*      */     
/*      */     void fileCreated(String param1String, AnnotAction param1AnnotAction);
/*      */     
/*      */     public enum AnnotAction
/*      */     {
/*  626 */       SCREENSHOT_CREATE; } } public enum AnnotAction { SCREENSHOT_CREATE; }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public enum ToolMode
/*      */     implements ToolModeBase
/*      */   {
/*  781 */     PAN(1),
/*      */ 
/*      */ 
/*      */     
/*  785 */     ANNOT_EDIT(2),
/*      */ 
/*      */ 
/*      */     
/*  789 */     LINE_CREATE(3),
/*      */ 
/*      */ 
/*      */     
/*  793 */     ARROW_CREATE(4),
/*      */ 
/*      */ 
/*      */     
/*  797 */     RECT_CREATE(5),
/*      */ 
/*      */ 
/*      */     
/*  801 */     OVAL_CREATE(6),
/*      */ 
/*      */ 
/*      */     
/*  805 */     INK_CREATE(7),
/*      */ 
/*      */ 
/*      */     
/*  809 */     TEXT_ANNOT_CREATE(8),
/*      */ 
/*      */ 
/*      */     
/*  813 */     LINK_ACTION(9),
/*      */ 
/*      */ 
/*      */     
/*  817 */     TEXT_SELECT(10),
/*      */ 
/*      */ 
/*      */     
/*  821 */     FORM_FILL(11),
/*      */ 
/*      */ 
/*      */     
/*  825 */     TEXT_CREATE(12),
/*      */ 
/*      */ 
/*      */     
/*  829 */     ANNOT_EDIT_LINE(13),
/*      */ 
/*      */ 
/*      */     
/*  833 */     RICH_MEDIA(14),
/*      */ 
/*      */ 
/*      */     
/*  837 */     DIGITAL_SIGNATURE(15),
/*      */ 
/*      */ 
/*      */     
/*  841 */     TEXT_UNDERLINE(16),
/*      */ 
/*      */ 
/*      */     
/*  845 */     TEXT_HIGHLIGHT(17),
/*      */ 
/*      */ 
/*      */     
/*  849 */     TEXT_SQUIGGLY(18),
/*      */ 
/*      */ 
/*      */     
/*  853 */     TEXT_STRIKEOUT(19),
/*      */ 
/*      */ 
/*      */     
/*  857 */     INK_ERASER(20),
/*      */ 
/*      */ 
/*      */     
/*  861 */     ANNOT_EDIT_TEXT_MARKUP(21),
/*      */ 
/*      */ 
/*      */     
/*  865 */     TEXT_HIGHLIGHTER(22),
/*      */ 
/*      */ 
/*      */     
/*  869 */     SIGNATURE(23),
/*      */ 
/*      */ 
/*      */     
/*  873 */     STAMPER(24),
/*      */ 
/*      */ 
/*      */     
/*  877 */     RUBBER_STAMPER(25),
/*      */ 
/*      */ 
/*      */     
/*  881 */     RECT_LINK(26),
/*      */ 
/*      */ 
/*      */     
/*  885 */     FORM_SIGNATURE_CREATE(27),
/*      */ 
/*      */ 
/*      */     
/*  889 */     FORM_TEXT_FIELD_CREATE(28),
/*      */ 
/*      */ 
/*      */     
/*  893 */     TEXT_LINK_CREATE(29),
/*      */ 
/*      */ 
/*      */     
/*  897 */     FORM_CHECKBOX_CREATE(30),
/*      */ 
/*      */ 
/*      */     
/*  901 */     FORM_RADIO_GROUP_CREATE(31),
/*      */ 
/*      */ 
/*      */     
/*  905 */     TEXT_REDACTION(32),
/*      */ 
/*      */ 
/*      */     
/*  909 */     FREE_HIGHLIGHTER(33),
/*      */ 
/*      */ 
/*      */     
/*  913 */     POLYLINE_CREATE(34),
/*      */ 
/*      */ 
/*      */     
/*  917 */     POLYGON_CREATE(35),
/*      */ 
/*      */ 
/*      */     
/*  921 */     CLOUD_CREATE(36),
/*      */ 
/*      */ 
/*      */     
/*  925 */     ANNOT_EDIT_RECT_GROUP(37),
/*      */ 
/*      */ 
/*      */     
/*  929 */     ANNOT_EDIT_ADVANCED_SHAPE(38),
/*      */ 
/*      */ 
/*      */     
/*  933 */     RULER_CREATE(39),
/*      */ 
/*      */ 
/*      */     
/*  937 */     CALLOUT_CREATE(40),
/*      */ 
/*      */ 
/*      */     
/*  941 */     SOUND_CREATE(41),
/*      */ 
/*      */ 
/*      */     
/*  945 */     FILE_ATTACHMENT_CREATE(42),
/*      */ 
/*      */ 
/*      */     
/*  949 */     RECT_REDACTION(43),
/*      */ 
/*      */ 
/*      */     
/*  953 */     PERIMETER_MEASURE_CREATE(44),
/*      */ 
/*      */ 
/*      */     
/*  957 */     AREA_MEASURE_CREATE(45),
/*      */ 
/*      */ 
/*      */     
/*  961 */     FORM_COMBO_BOX_CREATE(46),
/*      */ 
/*      */ 
/*      */     
/*  965 */     FORM_LIST_BOX_CREATE(47),
/*      */ 
/*      */ 
/*      */     
/*  969 */     FREE_TEXT_SPACING_CREATE(48),
/*      */ 
/*      */ 
/*      */     
/*  973 */     FREE_TEXT_DATE_CREATE(49),
/*      */ 
/*      */ 
/*      */     
/*  977 */     RECT_AREA_MEASURE_CREATE(50);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static final int NUM_TOOL_MODE = 50;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final int mode;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  992 */     private static SparseArray<ToolModeBase> map = new SparseArray();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1000 */     private static AtomicInteger modeGenerator = new AtomicInteger(100); ToolMode(int mode) {
/*      */       this.mode = mode;
/*      */     }
/*      */     public int getValue() {
/*      */       return this.mode;
/*      */     }
/*      */     static {
/*      */     
/*      */     }
/*      */     public static ToolModeBase toolModeFor(int toolMode) {
/* 1010 */       return (ToolModeBase)map.get(toolMode);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static ToolModeBase addNewMode() {
/* 1019 */       return addNewMode(28);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static ToolModeBase addNewMode(int annotType) {
/* 1029 */       final int newMode = modeGenerator.incrementAndGet();
/* 1030 */       ToolModeBase mode = new ToolModeBase()
/*      */         {
/*      */           public int getValue() {
/* 1033 */             return newMode;
/*      */           }
/*      */         };
/* 1036 */       map.put(newMode, mode);
/* 1037 */       return mode;
/*      */     }
/*      */   }
/*      */   
/*      */   public static ToolMode getDefaultToolMode(ToolModeBase toolModeBase) {
/* 1042 */     if (toolModeBase != null && toolModeBase instanceof ToolMode) {
/* 1043 */       return (ToolMode)toolModeBase;
/*      */     }
/* 1045 */     return ToolMode.PAN;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean mPageNumberIndicatorVisible = true;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean mSkipNextTapEvent = false;
/*      */ 
/*      */   
/*      */   private boolean mSkipNextTouchEvent = false;
/*      */ 
/*      */   
/*      */   private Tool mTool;
/*      */ 
/*      */   
/*      */   private PDFViewCtrl mPdfViewCtrl;
/*      */ 
/*      */   
/*      */   private UndoRedoManager mUndoRedoManger;
/*      */ 
/*      */   
/* 1070 */   private AnnotManager mAnnotManager = null;
/*      */   
/*      */   private RedactionManager mRedactionManager;
/*      */   
/*      */   private boolean mReadOnly = false;
/*      */   
/*      */   private boolean mTextMarkupAdobeHack = true;
/*      */   
/*      */   private boolean mCanCheckAnnotPermission = false;
/*      */   
/*      */   private boolean mShowAuthorDialog = false;
/*      */   
/*      */   private String mAuthorId;
/*      */   
/*      */   private String mAuthorName;
/*      */   
/*      */   private boolean mCopyAnnotatedTextToNote = false;
/*      */   
/*      */   private boolean mStylusAsPen = false;
/*      */   
/*      */   private boolean mIsNightMode = false;
/*      */   
/*      */   private boolean mInkSmoothing = true;
/*      */   
/*      */   private boolean mStickyNoteShowPopup = true;
/*      */   
/*      */   private boolean mEditInkAnnots = false;
/*      */   
/*      */   private boolean mCanOpenEditToolbarFromPan = true;
/*      */   private boolean mAddImageStamperTool = false;
/*      */   private Set<String> mFreeTextFonts;
/*      */   private Set<ToolMode> mDisabledToolModes;
/*      */   private Set<ToolMode> mDisabledToolModesSave;
/*      */   private ArrayList<ToolMode> mAnnotToolbarPrecedence;
/*      */   private TextToSpeech mTTS;
/*      */   private SelectionLoupe mSelectionLoupe;
/*      */   private SelectionLoupe mSelectionLoupeRound;
/*      */   private Canvas mSelectionLoupeCanvas;
/*      */   private Canvas mSelectionLoupeCanvasRound;
/*      */   private Bitmap mSelectionLoupeBitmap;
/*      */   private Bitmap mSelectionLoupeBitmapRound;
/*      */   private ArrayList<Tool> mOldTools;
/*      */   private String mSelectedAnnotId;
/* 1113 */   private int mSelectedAnnotPageNum = -1;
/*      */   
/*      */   private boolean mQuickMenuJustClosed = false;
/*      */   
/*      */   private boolean mIsAutoSelectAnnotation = true;
/*      */   
/*      */   private boolean mDisableQuickMenu = false;
/*      */   
/*      */   private boolean mDoubleTapToZoom = true;
/*      */   
/*      */   private boolean mAutoResizeFreeText = false;
/*      */   
/*      */   private boolean mRealTimeAnnotEdit = true;
/*      */   
/*      */   private boolean mEditFreeTextOnTap = false;
/*      */   
/*      */   private boolean freeTextInlineToggleEnabled = true;
/*      */   
/*      */   private boolean mDeleteEmptyFreeText = true;
/*      */   
/*      */   private boolean mShowSavedSignature = true;
/*      */   
/*      */   private boolean mShowSignatureFromImage = true;
/*      */   
/*      */   private boolean mUsingDigitalSignature;
/*      */   
/*      */   private String mDigitalSignatureKeystorePath;
/*      */   
/*      */   private String mDigitalSignatureKeystorePassword;
/*      */   
/*      */   private boolean mUsePressureSensitiveSignatures = true;
/*      */   
/* 1145 */   private Eraser.EraserType mEraserType = Eraser.EraserType.INK_ERASER;
/*      */   
/*      */   private boolean mShowUndoRedo = true;
/*      */   
/* 1149 */   private int mSelectionBoxMargin = 16;
/*      */ 
/*      */   
/*      */   private String mCacheFileName;
/*      */   
/*      */   private boolean mCanResumePdfDocWithoutReloading;
/*      */   
/*      */   private boolean mSnappingEnabled;
/*      */   
/*      */   private boolean mRichContentEnabled;
/*      */   
/*      */   private boolean mShowRichContentOption;
/*      */   
/*      */   private boolean mFontLoaded;
/*      */   
/*      */   private boolean mShowAnnotIndicators;
/*      */   
/*      */   private AnnotIndicatorManger mAnnotIndicatorManger;
/*      */   
/*      */   private HashMap<ToolModeBase, Class<? extends Tool>> mCustomizedToolClassMap;
/*      */   
/*      */   private HashMap<ToolModeBase, Object[]> mCustomizedToolParamMap;
/*      */   
/* 1172 */   private Class<? extends Pan> mDefaultToolClass = Pan.class;
/*      */   
/*      */   private PopupWindow mPageIndicatorPopup;
/*      */   
/*      */   @Nullable
/*      */   private WeakReference<FragmentActivity> mCurrentActivity;
/*      */   
/* 1179 */   protected CompositeDisposable mDisposables = new CompositeDisposable();
/* 1180 */   final TextSearchSelections mTextSearchSelections = new TextSearchSelections();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ToolManager(@NonNull PDFViewCtrl pdfViewCtrl) {
/* 1188 */     this.mPdfViewCtrl = pdfViewCtrl;
/* 1189 */     this.mPdfViewCtrl.setActionCompletedListener(this);
/*      */     try {
/* 1191 */       this.mPdfViewCtrl.enableUndoRedo();
/* 1192 */     } catch (PDFNetException e) {
/* 1193 */       AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*      */     } 
/* 1195 */     this.mUndoRedoManger = new UndoRedoManager(this);
/* 1196 */     this.mOldTools = new ArrayList<>();
/*      */ 
/*      */     
/* 1199 */     this.mDisposables.add(AnnotUtils.loadSystemFonts()
/* 1200 */         .subscribeOn(Schedulers.io())
/* 1201 */         .observeOn(AndroidSchedulers.mainThread())
/* 1202 */         .subscribe(new Consumer<String>()
/*      */           {
/*      */             public void accept(String s) throws Exception {
/* 1205 */               ToolManager.this.mFontLoaded = true;
/* 1206 */               Logger.INSTANCE.LogD("PDFNet", "getSystemFontList completed.");
/*      */ 
/*      */               
/* 1209 */               SharedPreferences settings = Tool.getToolPreferences(ToolManager.this.mPdfViewCtrl.getContext());
/* 1210 */               String fontInfo = settings.getString("annotation_property_free_text_fonts_list", "");
/* 1211 */               if (fontInfo.equals("")) {
/* 1212 */                 SharedPreferences.Editor editor = settings.edit();
/* 1213 */                 editor.putString("annotation_property_free_text_fonts_list", s);
/* 1214 */                 editor.apply();
/* 1215 */                 Logger.INSTANCE.LogD("PDFNet", "getSystemFontList write to disk.");
/*      */               } 
/*      */             }
/*      */           }new Consumer<Throwable>()
/*      */           {
/*      */             public void accept(Throwable throwable) throws Exception {
/* 1221 */               ToolManager.this.mFontLoaded = false;
/*      */             }
/*      */           }));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void resetIndicator() {
/* 1231 */     if (this.mAnnotIndicatorManger != null) {
/* 1232 */       this.mAnnotIndicatorManger.reset(true);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Pan createDefaultTool() {
/*      */     Pan tool;
/*      */     try {
/* 1245 */       tool = this.mDefaultToolClass.getDeclaredConstructor(new Class[] { this.mPdfViewCtrl.getClass() }).newInstance(new Object[] { this.mPdfViewCtrl });
/* 1246 */     } catch (Exception e) {
/* 1247 */       AnalyticsHandlerAdapter.getInstance().sendException(e, "failed to instantiate default tool");
/* 1248 */       tool = new Pan(this.mPdfViewCtrl);
/*      */     } 
/* 1250 */     tool.setPageNumberIndicatorVisible(this.mPageNumberIndicatorVisible);
/* 1251 */     tool.onCreate();
/*      */     
/* 1253 */     return tool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Tool createTool(ToolModeBase newTool, Tool currentTool) {
/* 1264 */     Tool tool = safeCreateTool(newTool);
/*      */     
/* 1266 */     tool.setPageNumberIndicatorVisible(this.mPageNumberIndicatorVisible);
/*      */     
/* 1268 */     if (currentTool == null || tool.getToolMode() != currentTool.getToolMode()) {
/* 1269 */       if (this.mTool != null && currentTool == null) {
/* 1270 */         this.mTool.onClose();
/*      */       }
/* 1272 */       if (this.mToolChangedListeners != null) {
/* 1273 */         for (ToolChangedListener listener : this.mToolChangedListeners) {
/* 1274 */           listener.toolChanged(tool, currentTool);
/*      */         }
/*      */       }
/*      */     } 
/*      */     
/* 1279 */     if (currentTool != null) {
/* 1280 */       Tool oldTool = (Tool)currentTool;
/* 1281 */       tool.mAnnot = oldTool.mAnnot;
/* 1282 */       tool.mAnnotBBox = oldTool.mAnnotBBox;
/* 1283 */       tool.mAnnotPageNum = oldTool.mAnnotPageNum;
/* 1284 */       tool.mGroupAnnots = oldTool.mGroupAnnots;
/* 1285 */       tool.mAvoidLongPressAttempt = oldTool.mAvoidLongPressAttempt;
/* 1286 */       if (tool.getToolMode() != ToolMode.PAN) {
/* 1287 */         tool.mCurrentDefaultToolMode = oldTool.mCurrentDefaultToolMode;
/*      */       } else {
/* 1289 */         tool.mCurrentDefaultToolMode = ToolMode.PAN;
/*      */       } 
/* 1291 */       tool.mForceSameNextToolMode = oldTool.mForceSameNextToolMode;
/* 1292 */       if (oldTool.mForceSameNextToolMode) {
/* 1293 */         tool.mStylusUsed = oldTool.mStylusUsed;
/*      */       }
/* 1295 */       tool.mAnnotView = oldTool.mAnnotView;
/* 1296 */       oldTool.onClose();
/*      */       
/* 1298 */       if (oldTool.getToolMode() != tool.getToolMode()) {
/* 1299 */         tool.setJustCreatedFromAnotherTool();
/*      */       }
/*      */       
/* 1302 */       if (oldTool.mCurrentDefaultToolMode != tool.getToolMode()) {
/* 1303 */         setQuickMenuJustClosed(false);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1308 */       if (oldTool.getToolMode() == ToolMode.TEXT_ANNOT_CREATE && tool.getToolMode() == ToolMode.ANNOT_EDIT) {
/*      */         
/* 1310 */         AnnotEdit at = (AnnotEdit)tool;
/* 1311 */         at.setUpFromStickyCreate(true);
/* 1312 */         at.mForceSameNextToolMode = oldTool.mForceSameNextToolMode;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1317 */       if ((oldTool.getToolMode() == ToolMode.TEXT_CREATE || oldTool
/* 1318 */         .getToolMode() == ToolMode.CALLOUT_CREATE) && (tool
/* 1319 */         .getToolMode() == ToolMode.ANNOT_EDIT || tool
/* 1320 */         .getToolMode() == ToolMode.ANNOT_EDIT_ADVANCED_SHAPE)) {
/*      */         
/* 1322 */         AnnotEdit at = (AnnotEdit)tool;
/* 1323 */         if (oldTool.getToolMode() == ToolMode.TEXT_CREATE) {
/* 1324 */           at.setUpFromFreeTextCreate(true);
/* 1325 */         } else if (oldTool.getToolMode() == ToolMode.CALLOUT_CREATE) {
/* 1326 */           at.mUpFromCalloutCreate = oldTool.mUpFromCalloutCreate;
/*      */         } 
/* 1328 */         at.mForceSameNextToolMode = oldTool.mForceSameNextToolMode;
/*      */       } 
/*      */ 
/*      */       
/* 1332 */       if (oldTool.getToolMode() == ToolMode.INK_ERASER && tool.getToolMode() == ToolMode.PAN) {
/*      */         
/* 1334 */         Pan pan = (Pan)tool;
/* 1335 */         pan.mSuppressSingleTapConfirmed = true;
/*      */       } 
/* 1337 */     } else if (getTool() != null && getTool() instanceof Tool) {
/*      */       
/* 1339 */       Tool oldTool = (Tool)getTool();
/* 1340 */       if (oldTool.mAnnotView != null) {
/* 1341 */         oldTool.removeAnnotView(false);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1346 */     tool.onCreate();
/*      */     
/* 1348 */     return tool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Tool safeCreateTool(ToolModeBase mode) {
/*      */     Tool tool;
/* 1359 */     ToolModeBase actualMode = mode;
/* 1360 */     if (this.mDisabledToolModes != null && this.mDisabledToolModes.contains(mode)) {
/* 1361 */       actualMode = ToolMode.PAN;
/*      */     }
/*      */     try {
/* 1364 */       Object[] toolArgs = getToolArguments(actualMode);
/* 1365 */       Class<? extends Tool> toolClass = getToolClassByMode(actualMode);
/* 1366 */       tool = instantiateTool((Class)toolClass, toolArgs);
/* 1367 */     } catch (Exception e) {
/* 1368 */       tool = createDefaultTool();
/* 1369 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/* 1370 */     } catch (OutOfMemoryError oom) {
/* 1371 */       Utils.manageOOM(this.mPdfViewCtrl);
/* 1372 */       tool = createDefaultTool();
/*      */     } 
/* 1374 */     return tool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Class<? extends Tool> getToolClassByMode(ToolModeBase modeBase) {
/* 1384 */     if (null != this.mCustomizedToolClassMap && this.mCustomizedToolClassMap.containsKey(modeBase)) {
/* 1385 */       return this.mCustomizedToolClassMap.get(modeBase);
/*      */     }
/* 1387 */     ToolMode mode = getDefaultToolMode(modeBase);
/* 1388 */     switch (mode) {
/*      */       case PAN:
/* 1390 */         return (Class)Pan.class;
/*      */       case ANNOT_EDIT:
/* 1392 */         return (Class)AnnotEdit.class;
/*      */       case LINE_CREATE:
/* 1394 */         return (Class)LineCreate.class;
/*      */       case ARROW_CREATE:
/* 1396 */         return (Class)ArrowCreate.class;
/*      */       case RULER_CREATE:
/* 1398 */         return (Class)RulerCreate.class;
/*      */       case PERIMETER_MEASURE_CREATE:
/* 1400 */         return (Class)PerimeterMeasureCreate.class;
/*      */       case AREA_MEASURE_CREATE:
/* 1402 */         return (Class)AreaMeasureCreate.class;
/*      */       case RECT_AREA_MEASURE_CREATE:
/* 1404 */         return (Class)RectAreaMeasureCreate.class;
/*      */       case POLYLINE_CREATE:
/* 1406 */         return (Class)PolylineCreate.class;
/*      */       case RECT_CREATE:
/* 1408 */         return (Class)RectCreate.class;
/*      */       case OVAL_CREATE:
/* 1410 */         return (Class)OvalCreate.class;
/*      */       case SOUND_CREATE:
/* 1412 */         return (Class)SoundCreate.class;
/*      */       case FILE_ATTACHMENT_CREATE:
/* 1414 */         return (Class)FileAttachmentCreate.class;
/*      */       case POLYGON_CREATE:
/* 1416 */         return (Class)PolygonCreate.class;
/*      */       case CLOUD_CREATE:
/* 1418 */         return (Class)CloudCreate.class;
/*      */       case INK_CREATE:
/* 1420 */         return (Class)FreehandCreate.class;
/*      */       case FREE_HIGHLIGHTER:
/* 1422 */         return (Class)FreeHighlighterCreate.class;
/*      */       case TEXT_ANNOT_CREATE:
/* 1424 */         return (Class)StickyNoteCreate.class;
/*      */       case LINK_ACTION:
/* 1426 */         return (Class)LinkAction.class;
/*      */       case TEXT_SELECT:
/* 1428 */         return (Class)TextSelect.class;
/*      */       case FORM_FILL:
/* 1430 */         return (Class)FormFill.class;
/*      */       case TEXT_CREATE:
/* 1432 */         return (Class)FreeTextCreate.class;
/*      */       case CALLOUT_CREATE:
/* 1434 */         return (Class)CalloutCreate.class;
/*      */       case ANNOT_EDIT_LINE:
/* 1436 */         return (Class)AnnotEditLine.class;
/*      */       case ANNOT_EDIT_ADVANCED_SHAPE:
/* 1438 */         return (Class)AnnotEditAdvancedShape.class;
/*      */       case RICH_MEDIA:
/* 1440 */         return (Class)RichMedia.class;
/*      */       case TEXT_UNDERLINE:
/* 1442 */         return (Class)TextUnderlineCreate.class;
/*      */       case TEXT_HIGHLIGHT:
/* 1444 */         return (Class)TextHighlightCreate.class;
/*      */       case TEXT_SQUIGGLY:
/* 1446 */         return (Class)TextSquigglyCreate.class;
/*      */       case TEXT_STRIKEOUT:
/* 1448 */         return (Class)TextStrikeoutCreate.class;
/*      */       case TEXT_REDACTION:
/* 1450 */         return (Class)TextRedactionCreate.class;
/*      */       case RECT_REDACTION:
/* 1452 */         return (Class)RectRedactionCreate.class;
/*      */       case INK_ERASER:
/* 1454 */         return (Class)Eraser.class;
/*      */       case ANNOT_EDIT_TEXT_MARKUP:
/* 1456 */         return (Class)AnnotEditTextMarkup.class;
/*      */       case TEXT_HIGHLIGHTER:
/* 1458 */         return (Class)TextHighlighter.class;
/*      */       case DIGITAL_SIGNATURE:
/* 1460 */         return (Class)DigitalSignature.class;
/*      */       case SIGNATURE:
/* 1462 */         return (Class)Signature.class;
/*      */       case STAMPER:
/* 1464 */         return (Class)Stamper.class;
/*      */       case RUBBER_STAMPER:
/* 1466 */         return (Class)RubberStampCreate.class;
/*      */       case RECT_LINK:
/* 1468 */         return (Class)RectLinkCreate.class;
/*      */       case FORM_SIGNATURE_CREATE:
/* 1470 */         return (Class)SignatureFieldCreate.class;
/*      */       case FORM_TEXT_FIELD_CREATE:
/* 1472 */         return (Class)TextFieldCreate.class;
/*      */       case TEXT_LINK_CREATE:
/* 1474 */         return (Class)TextLinkCreate.class;
/*      */       case FORM_CHECKBOX_CREATE:
/* 1476 */         return (Class)CheckboxFieldCreate.class;
/*      */       case FORM_COMBO_BOX_CREATE:
/* 1478 */         return (Class)ComboBoxFieldCreate.class;
/*      */       case FORM_LIST_BOX_CREATE:
/* 1480 */         return (Class)ListBoxFieldCreate.class;
/*      */       case FORM_RADIO_GROUP_CREATE:
/* 1482 */         return (Class)RadioGroupFieldCreate.class;
/*      */       case ANNOT_EDIT_RECT_GROUP:
/* 1484 */         return (Class)AnnotEditRectGroup.class;
/*      */       case FREE_TEXT_SPACING_CREATE:
/* 1486 */         return (Class)FreeTextSpacingCreate.class;
/*      */       case FREE_TEXT_DATE_CREATE:
/* 1488 */         return (Class)FreeTextDateCreate.class;
/*      */     } 
/* 1490 */     return (Class)Pan.class;
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
/*      */   private Object[] getToolArguments(ToolModeBase mode) {
/* 1502 */     if (null != this.mCustomizedToolParamMap && this.mCustomizedToolParamMap.containsKey(mode)) {
/* 1503 */       return this.mCustomizedToolParamMap.get(mode);
/*      */     }
/* 1505 */     if (mode == ToolMode.INK_ERASER) {
/* 1506 */       return new Object[] { this.mPdfViewCtrl, this.mEraserType };
/*      */     }
/* 1508 */     return new Object[] { this.mPdfViewCtrl };
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
/*      */   private Tool instantiateTool(Class<? extends Tool> toolClass, Object... args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
/* 1540 */     Class[] cArg = processArguments(args);
/* 1541 */     return toolClass.getDeclaredConstructor(cArg).newInstance(args);
/*      */   }
/*      */   
/*      */   private Class[] processArguments(Object... args) {
/* 1545 */     Class[] cArg = new Class[args.length];
/* 1546 */     int i = 0;
/* 1547 */     for (Object arg : args) {
/* 1548 */       if (arg instanceof PDFViewCtrl) {
/* 1549 */         cArg[i] = PDFViewCtrl.class;
/*      */       } else {
/* 1551 */         cArg[i] = arg.getClass();
/*      */       } 
/* 1553 */       i++;
/*      */     } 
/* 1555 */     return cArg;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onControlReady() {
/* 1563 */     if (this.mTool == null) {
/* 1564 */       this.mTool = createDefaultTool();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onClose() {
/* 1573 */     if (this.mTool != null) {
/* 1574 */       this.mTool.onClose();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onCustomEvent(Object obj) {
/* 1583 */     if (this.mTool != null) {
/* 1584 */       ToolModeBase prev_tm = this.mTool.getToolMode();
/*      */       while (true) {
/* 1586 */         this.mTool.onCustomEvent(obj);
/* 1587 */         ToolModeBase next_tm = this.mTool.getNextToolMode();
/* 1588 */         if (prev_tm != next_tm) {
/* 1589 */           this.mTool = createTool(next_tm, this.mTool);
/* 1590 */           prev_tm = next_tm;
/*      */           continue;
/*      */         } 
/*      */         break;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onKeyUp(int keyCode, KeyEvent event) {
/* 1603 */     if (this.mPreToolManagerListener != null && 
/* 1604 */       this.mPreToolManagerListener.onKeyUp(keyCode, event)) {
/* 1605 */       return true;
/*      */     }
/*      */     
/* 1608 */     boolean handled = false;
/*      */     
/* 1610 */     if (this.mTool != null) {
/* 1611 */       ToolModeBase prev_tm = this.mTool.getToolMode();
/*      */       while (true) {
/* 1613 */         handled = this.mTool.onKeyUp(keyCode, event);
/* 1614 */         ToolModeBase next_tm = this.mTool.getNextToolMode();
/* 1615 */         if (prev_tm != next_tm) {
/* 1616 */           this.mTool = createTool(next_tm, this.mTool);
/* 1617 */           prev_tm = next_tm;
/*      */           
/*      */           continue;
/*      */         } 
/*      */         break;
/*      */       } 
/*      */     } 
/* 1624 */     return handled;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onDoubleTap(MotionEvent e) {
/* 1632 */     if (this.mPreToolManagerListener != null && 
/* 1633 */       this.mPreToolManagerListener.onDoubleTap(e)) {
/* 1634 */       return true;
/*      */     }
/*      */     
/* 1637 */     boolean handled = false;
/*      */     
/* 1639 */     if (this.mTool != null) {
/* 1640 */       ToolModeBase prev_tm = this.mTool.getToolMode();
/*      */       while (true) {
/* 1642 */         handled = this.mTool.onDoubleTap(e);
/* 1643 */         ToolModeBase next_tm = this.mTool.getNextToolMode();
/* 1644 */         if (prev_tm != next_tm) {
/* 1645 */           this.mTool = createTool(next_tm, this.mTool);
/* 1646 */           prev_tm = next_tm;
/*      */           
/*      */           continue;
/*      */         } 
/*      */         break;
/*      */       } 
/*      */     } 
/* 1653 */     return handled;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onDoubleTapEvent(MotionEvent e) {
/* 1661 */     boolean handled = false;
/*      */     
/* 1663 */     if (this.mTool != null) {
/* 1664 */       ToolModeBase prev_tm = this.mTool.getToolMode();
/*      */       while (true) {
/* 1666 */         handled = this.mTool.onDoubleTapEvent(e);
/* 1667 */         ToolModeBase next_tm = this.mTool.getNextToolMode();
/* 1668 */         if (prev_tm != next_tm) {
/* 1669 */           this.mTool = createTool(next_tm, this.mTool);
/* 1670 */           prev_tm = next_tm;
/*      */           
/*      */           continue;
/*      */         } 
/*      */         break;
/*      */       } 
/*      */     } 
/* 1677 */     return handled;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDoubleTapEnd(MotionEvent e) {
/* 1685 */     if (this.mTool != null) {
/* 1686 */       ToolModeBase prev_tm = this.mTool.getToolMode();
/*      */       while (true) {
/* 1688 */         this.mTool.onDoubleTapEnd(e);
/* 1689 */         ToolModeBase next_tm = this.mTool.getNextToolMode();
/* 1690 */         if (prev_tm != next_tm) {
/* 1691 */           this.mTool = createTool(next_tm, this.mTool);
/* 1692 */           prev_tm = next_tm;
/*      */           continue;
/*      */         } 
/*      */         break;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onDown(MotionEvent e) {
/* 1705 */     if (this.mSkipNextTouchEvent) {
/* 1706 */       return true;
/*      */     }
/*      */     
/* 1709 */     if (this.mPreToolManagerListener != null && 
/* 1710 */       this.mPreToolManagerListener.onDown(e)) {
/* 1711 */       return true;
/*      */     }
/*      */ 
/*      */     
/* 1715 */     if (this.mTool != null) {
/* 1716 */       ToolModeBase prev_tm = this.mTool.getToolMode();
/*      */       while (true) {
/* 1718 */         this.mTool.onDown(e);
/* 1719 */         ToolModeBase next_tm = this.mTool.getNextToolMode();
/* 1720 */         if (prev_tm != next_tm) {
/* 1721 */           this.mTool = createTool(next_tm, this.mTool);
/*      */           
/* 1723 */           prev_tm = next_tm;
/*      */           
/*      */           continue;
/*      */         } 
/*      */         break;
/*      */       } 
/*      */     } 
/* 1730 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onPointerDown(MotionEvent e) {
/* 1738 */     if (this.mSkipNextTouchEvent) {
/* 1739 */       return true;
/*      */     }
/*      */     
/* 1742 */     if (this.mTool != null) {
/* 1743 */       ToolModeBase prev_tm = this.mTool.getToolMode();
/*      */       while (true) {
/* 1745 */         this.mTool.onPointerDown(e);
/* 1746 */         ToolModeBase next_tm = this.mTool.getNextToolMode();
/* 1747 */         if (prev_tm != next_tm) {
/* 1748 */           this.mTool = createTool(next_tm, this.mTool);
/*      */           
/* 1750 */           prev_tm = next_tm;
/*      */           
/*      */           continue;
/*      */         } 
/*      */         break;
/*      */       } 
/*      */     } 
/* 1757 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDocumentDownloadEvent(PDFViewCtrl.DownloadState mode, int page_num, int page_downloaded, int page_count, String message) {
/* 1765 */     if (this.mTool != null) {
/* 1766 */       ToolModeBase prev_tm = this.mTool.getToolMode();
/*      */       while (true) {
/* 1768 */         this.mTool.onDocumentDownloadEvent(mode, page_num, page_downloaded, page_count, message);
/* 1769 */         ToolModeBase next_tm = this.mTool.getNextToolMode();
/* 1770 */         if (prev_tm != next_tm) {
/* 1771 */           this.mTool = createTool(next_tm, this.mTool);
/* 1772 */           prev_tm = next_tm;
/*      */           continue;
/*      */         } 
/*      */         break;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDraw(Canvas canvas, Matrix tfm) {
/* 1786 */     if (this.mOldTools != null) {
/* 1787 */       for (Tool t : this.mOldTools) {
/* 1788 */         t.onDraw(canvas, tfm);
/*      */       }
/*      */     }
/*      */     
/* 1792 */     if (this.mTool != null) {
/* 1793 */       this.mTool.onDraw(canvas, tfm);
/*      */     }
/*      */     
/* 1796 */     if (this.mAnnotIndicatorManger != null) {
/* 1797 */       boolean canDrawIndicator = true;
/* 1798 */       if (this.mTool instanceof BaseTool) {
/* 1799 */         canDrawIndicator = !((BaseTool)this.mTool).isDrawingLoupe();
/*      */       }
/* 1801 */       if (canDrawIndicator) {
/* 1802 */         this.mAnnotIndicatorManger.drawAnnotIndicators(canvas);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onFlingStop() {
/* 1812 */     if (this.mAnnotIndicatorManger != null) {
/* 1813 */       this.mAnnotIndicatorManger.updateState(0);
/*      */     }
/*      */     
/* 1816 */     if (this.mTool != null) {
/* 1817 */       ToolModeBase prev_tm = this.mTool.getToolMode();
/*      */       while (true) {
/* 1819 */         this.mTool.onFlingStop();
/* 1820 */         ToolModeBase next_tm = this.mTool.getNextToolMode();
/* 1821 */         if (prev_tm != next_tm) {
/* 1822 */           this.mTool = createTool(next_tm, this.mTool);
/* 1823 */           prev_tm = next_tm;
/*      */           
/*      */           continue;
/*      */         } 
/*      */         break;
/*      */       } 
/*      */     } 
/* 1830 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onLayout(boolean changed, int l, int t, int r, int b) {
/* 1838 */     if (this.mAnnotIndicatorManger != null) {
/* 1839 */       this.mAnnotIndicatorManger.reset(true);
/*      */     }
/*      */     
/* 1842 */     if (this.mTool != null) {
/* 1843 */       this.mTool.onLayout(changed, l, t, r, b);
/*      */     }
/*      */     
/* 1846 */     if (this.mOldTools != null) {
/* 1847 */       for (Tool tool : this.mOldTools) {
/* 1848 */         tool.onLayout(changed, l, t, r, b);
/*      */       }
/*      */     }
/*      */     
/* 1852 */     CopyOnWriteArray<OnLayoutListener> listeners = this.mOnLayoutListeners;
/* 1853 */     if (listeners != null && listeners.size() > 0) {
/* 1854 */       CopyOnWriteArray.Access<OnLayoutListener> access = listeners.start();
/*      */       try {
/* 1856 */         int count = access.size();
/* 1857 */         for (int i = 0; i < count; i++) {
/* 1858 */           ((OnLayoutListener)access.get(i)).onLayout(changed, l, t, r, b);
/*      */         }
/*      */       } finally {
/* 1861 */         listeners.end();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onLongPress(MotionEvent e) {
/* 1871 */     if (this.mPreToolManagerListener != null && 
/* 1872 */       this.mPreToolManagerListener.onLongPress(e)) {
/* 1873 */       return true;
/*      */     }
/*      */     
/* 1876 */     if (this.mTool != null) {
/* 1877 */       ToolModeBase prev_tm = this.mTool.getToolMode();
/*      */       while (true) {
/* 1879 */         boolean handled = this.mTool.onLongPress(e);
/* 1880 */         if (!handled) {
/* 1881 */           ToolModeBase next_tm = this.mTool.getNextToolMode();
/* 1882 */           if (prev_tm != next_tm) {
/* 1883 */             this.mTool = createTool(next_tm, this.mTool);
/* 1884 */             prev_tm = next_tm;
/*      */             
/*      */             continue;
/*      */           } 
/*      */         } 
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/* 1894 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onMove(MotionEvent e1, MotionEvent e2, float x_dist, float y_dist) {
/* 1902 */     if (this.mSkipNextTouchEvent) {
/* 1903 */       return true;
/*      */     }
/* 1905 */     if (this.mPreToolManagerListener != null && 
/* 1906 */       this.mPreToolManagerListener.onMove(e1, e2, x_dist, y_dist)) {
/* 1907 */       return true;
/*      */     }
/*      */ 
/*      */     
/* 1911 */     boolean bool = false;
/* 1912 */     if (this.mTool != null) {
/* 1913 */       ToolModeBase prev_tm = this.mTool.getToolMode();
/*      */ 
/*      */       
/*      */       while (true) {
/* 1917 */         if (this.mTool.getToolMode() == ToolMode.INK_CREATE || this.mTool
/* 1918 */           .getToolMode() == ToolMode.INK_ERASER) {
/*      */           
/* 1920 */           bool |= this.mTool.onMove(e1, e2, x_dist, y_dist);
/*      */         } else {
/*      */           int i;
/*      */           
/* 1924 */           if (Float.compare(x_dist, -1.0F) == 0 && Float.compare(y_dist, -1.0F) == 0) {
/* 1925 */             i = bool | true;
/*      */           } else {
/* 1927 */             bool = i | this.mTool.onMove(e1, e2, x_dist, y_dist);
/*      */           } 
/*      */         } 
/*      */         
/* 1931 */         ToolModeBase next_tm = this.mTool.getNextToolMode();
/* 1932 */         if (prev_tm != next_tm) {
/* 1933 */           this.mTool = createTool(next_tm, this.mTool);
/* 1934 */           prev_tm = next_tm;
/*      */           
/*      */           continue;
/*      */         } 
/*      */         break;
/*      */       } 
/*      */     } 
/* 1941 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onScrollChanged(int l, int t, int oldl, int oldt) {
/* 1949 */     this.mQuickMenuJustClosed = false;
/* 1950 */     if (this.mPreToolManagerListener != null) {
/* 1951 */       this.mPreToolManagerListener.onScrollChanged(l, t, oldl, oldt);
/*      */     }
/* 1953 */     if (this.mTool != null) {
/* 1954 */       ToolModeBase prev_tm = this.mTool.getToolMode();
/*      */       while (true) {
/* 1956 */         this.mTool.onScrollChanged(l, t, oldl, oldt);
/* 1957 */         ToolModeBase next_tm = this.mTool.getNextToolMode();
/* 1958 */         if (prev_tm != next_tm) {
/* 1959 */           this.mTool = createTool(next_tm, this.mTool);
/* 1960 */           prev_tm = next_tm;
/*      */           continue;
/*      */         } 
/*      */         break;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onPageTurning(int old_page, int cur_page) {
/* 1973 */     if (this.mAnnotIndicatorManger != null) {
/* 1974 */       this.mAnnotIndicatorManger.updateState(0);
/* 1975 */       this.mAnnotIndicatorManger.reset(false);
/*      */     } 
/*      */     
/* 1978 */     if (this.mTool != null) {
/* 1979 */       ToolModeBase prev_tm = this.mTool.getToolMode();
/*      */       while (true) {
/* 1981 */         this.mTool.onPageTurning(old_page, cur_page);
/* 1982 */         ToolModeBase next_tm = this.mTool.getNextToolMode();
/* 1983 */         if (prev_tm != next_tm) {
/* 1984 */           this.mTool = createTool(next_tm, this.mTool);
/* 1985 */           prev_tm = next_tm;
/*      */           continue;
/*      */         } 
/*      */         break;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onScale(float x, float y) {
/* 1998 */     if (this.mPreToolManagerListener != null && 
/* 1999 */       this.mPreToolManagerListener.onScale(x, y)) {
/* 2000 */       return true;
/*      */     }
/*      */ 
/*      */     
/* 2004 */     if (this.mTool != null) {
/*      */       boolean handled;
/* 2006 */       ToolModeBase prev_tm = this.mTool.getToolMode();
/*      */       
/*      */       while (true) {
/* 2009 */         handled = this.mTool.onScale(x, y);
/* 2010 */         ToolModeBase next_tm = this.mTool.getNextToolMode();
/* 2011 */         if (prev_tm != next_tm) {
/* 2012 */           this.mTool = createTool(next_tm, this.mTool);
/* 2013 */           prev_tm = next_tm;
/*      */           
/*      */           continue;
/*      */         } 
/*      */         break;
/*      */       } 
/* 2019 */       if (handled) {
/* 2020 */         return true;
/*      */       }
/*      */     } 
/*      */     
/* 2024 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onScaleBegin(float x, float y) {
/* 2032 */     if (this.mAnnotIndicatorManger != null) {
/* 2033 */       this.mAnnotIndicatorManger.updateState(1);
/*      */     }
/*      */     
/* 2036 */     if (this.mPreToolManagerListener != null && 
/* 2037 */       this.mPreToolManagerListener.onScaleBegin(x, y)) {
/* 2038 */       return true;
/*      */     }
/*      */ 
/*      */     
/* 2042 */     if (this.mTool != null) {
/*      */       boolean handled;
/* 2044 */       ToolModeBase prev_tm = this.mTool.getToolMode();
/*      */       
/*      */       while (true) {
/* 2047 */         handled = this.mTool.onScaleBegin(x, y);
/* 2048 */         ToolModeBase next_tm = this.mTool.getNextToolMode();
/* 2049 */         if (prev_tm != next_tm) {
/* 2050 */           this.mTool = createTool(next_tm, this.mTool);
/* 2051 */           prev_tm = next_tm;
/*      */           
/*      */           continue;
/*      */         } 
/*      */         break;
/*      */       } 
/* 2057 */       if (handled) {
/* 2058 */         return true;
/*      */       }
/*      */     } 
/*      */     
/* 2062 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onScaleEnd(float x, float y) {
/* 2070 */     if (this.mAnnotIndicatorManger != null) {
/* 2071 */       this.mAnnotIndicatorManger.updateState(0);
/* 2072 */       this.mAnnotIndicatorManger.reset(true);
/*      */     } 
/*      */     
/* 2075 */     if (this.mPreToolManagerListener != null && 
/* 2076 */       this.mPreToolManagerListener.onScaleEnd(x, y)) {
/* 2077 */       return true;
/*      */     }
/*      */ 
/*      */     
/* 2081 */     if (this.mTool != null) {
/* 2082 */       ToolModeBase prev_tm = this.mTool.getToolMode();
/*      */       while (true) {
/* 2084 */         this.mTool.onScaleEnd(x, y);
/* 2085 */         ToolModeBase next_tm = this.mTool.getNextToolMode();
/* 2086 */         if (prev_tm != next_tm) {
/* 2087 */           this.mTool = createTool(next_tm, this.mTool);
/* 2088 */           prev_tm = next_tm;
/*      */           
/*      */           continue;
/*      */         } 
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/* 2096 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onSetDoc() {
/* 2104 */     if (this.mTool != null) {
/* 2105 */       ToolModeBase prev_tm = this.mTool.getToolMode();
/*      */       while (true) {
/* 2107 */         this.mTool.onSetDoc();
/* 2108 */         ToolModeBase next_tm = this.mTool.getNextToolMode();
/* 2109 */         if (prev_tm != next_tm) {
/* 2110 */           this.mTool = createTool(next_tm, this.mTool);
/* 2111 */           prev_tm = next_tm;
/*      */           continue;
/*      */         } 
/*      */         break;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onShowPress(MotionEvent e) {
/* 2124 */     if (this.mTool != null) {
/* 2125 */       ToolModeBase prev_tm = this.mTool.getToolMode();
/*      */       while (true) {
/* 2127 */         this.mTool.onShowPress(e);
/* 2128 */         ToolModeBase next_tm = this.mTool.getNextToolMode();
/* 2129 */         if (prev_tm != next_tm) {
/* 2130 */           this.mTool = createTool(next_tm, this.mTool);
/* 2131 */           prev_tm = next_tm;
/*      */           
/*      */           continue;
/*      */         } 
/*      */         break;
/*      */       } 
/*      */     } 
/* 2138 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onSingleTapConfirmed(MotionEvent e) {
/* 2146 */     if (this.mSkipNextTapEvent) {
/* 2147 */       this.mSkipNextTapEvent = false;
/* 2148 */       return true;
/*      */     } 
/* 2150 */     if (this.mPreToolManagerListener != null && 
/* 2151 */       this.mPreToolManagerListener.onSingleTapConfirmed(e)) {
/* 2152 */       return true;
/*      */     }
/*      */     
/* 2155 */     if (this.mTool != null) {
/* 2156 */       ToolModeBase prev_tm = this.mTool.getToolMode();
/*      */       while (true) {
/* 2158 */         boolean handled = this.mTool.onSingleTapConfirmed(e);
/* 2159 */         if (!handled) {
/* 2160 */           ToolModeBase next_tm = this.mTool.getNextToolMode();
/* 2161 */           if (prev_tm != next_tm) {
/* 2162 */             this.mTool = createTool(next_tm, this.mTool);
/* 2163 */             prev_tm = next_tm;
/*      */ 
/*      */             
/*      */             continue;
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/* 2175 */     onGenericMotionEvent(e);
/*      */     
/* 2177 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onPostSingleTapConfirmed() {
/* 2185 */     if (this.mTool != null) {
/* 2186 */       ToolModeBase prev_tm = this.mTool.getToolMode();
/*      */       while (true) {
/* 2188 */         this.mTool.onPostSingleTapConfirmed();
/* 2189 */         ToolModeBase next_tm = this.mTool.getNextToolMode();
/* 2190 */         if (prev_tm != next_tm) {
/* 2191 */           this.mTool = createTool(next_tm, this.mTool);
/* 2192 */           prev_tm = next_tm;
/*      */           continue;
/*      */         } 
/*      */         break;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onSingleTapUp(MotionEvent e) {
/* 2205 */     if (this.mTool != null) {
/* 2206 */       ToolModeBase prev_tm = this.mTool.getToolMode();
/*      */       while (true) {
/* 2208 */         this.mTool.onSingleTapUp(e);
/* 2209 */         ToolModeBase next_tm = this.mTool.getNextToolMode();
/* 2210 */         if (prev_tm != next_tm) {
/* 2211 */           this.mTool = createTool(next_tm, this.mTool);
/* 2212 */           prev_tm = next_tm;
/*      */           
/*      */           continue;
/*      */         } 
/*      */         break;
/*      */       } 
/*      */     } 
/* 2219 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onUp(MotionEvent e, PDFViewCtrl.PriorEventMode priorEventMode) {
/* 2227 */     if (this.mAnnotIndicatorManger != null && priorEventMode == PDFViewCtrl.PriorEventMode.FLING) {
/* 2228 */       this.mAnnotIndicatorManger.updateState(2);
/*      */     }
/*      */     
/* 2231 */     if (this.mSkipNextTouchEvent) {
/* 2232 */       this.mSkipNextTouchEvent = false;
/* 2233 */       return true;
/*      */     } 
/* 2235 */     if (this.mPreToolManagerListener != null && 
/* 2236 */       this.mPreToolManagerListener.onUp(e, priorEventMode)) {
/* 2237 */       return true;
/*      */     }
/*      */ 
/*      */     
/* 2241 */     boolean handled = false;
/* 2242 */     if (this.mTool != null) {
/* 2243 */       ToolModeBase prev_tm = this.mTool.getToolMode();
/*      */       while (true) {
/* 2245 */         handled |= this.mTool.onUp(e, priorEventMode);
/* 2246 */         ToolModeBase next_tm = this.mTool.getNextToolMode();
/* 2247 */         if (prev_tm != next_tm) {
/* 2248 */           this.mTool = createTool(next_tm, this.mTool);
/* 2249 */           prev_tm = next_tm;
/*      */           
/*      */           continue;
/*      */         } 
/*      */         break;
/*      */       } 
/*      */     } 
/* 2256 */     return handled;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onGenericMotionEvent(MotionEvent event) {
/* 2264 */     if (this.mOnGenericMotionEventListener != null) {
/* 2265 */       this.mOnGenericMotionEventListener.onGenericMotionEvent(event);
/*      */     }
/*      */     
/* 2268 */     if (ShortcutHelper.isLongPress(event)) {
/* 2269 */       onLongPress(event);
/* 2270 */       return true;
/*      */     } 
/*      */     
/* 2273 */     return (this.mTool != null && ((Tool)this.mTool).onGenericMotionEvent(event));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onChangePointerIcon(PointerIcon pointerIcon) {
/* 2281 */     if (this.mOnGenericMotionEventListener != null) {
/* 2282 */       this.mOnGenericMotionEventListener.onChangePointerIcon(pointerIcon);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onConfigurationChanged(Configuration newConfig) {
/* 2291 */     if (this.mTool != null) {
/* 2292 */       ToolModeBase prev_tm = this.mTool.getToolMode();
/*      */       while (true) {
/* 2294 */         this.mTool.onConfigurationChanged(newConfig);
/* 2295 */         ToolModeBase next_tm = this.mTool.getNextToolMode();
/* 2296 */         if (prev_tm != next_tm) {
/* 2297 */           this.mTool = createTool(next_tm, this.mTool);
/* 2298 */           prev_tm = next_tm;
/*      */           continue;
/*      */         } 
/*      */         break;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onDrawEdgeEffects(Canvas canvas, int width, int verticalOffset) {
/* 2311 */     boolean handled = false;
/* 2312 */     if (this.mTool != null) {
/* 2313 */       handled = this.mTool.onDrawEdgeEffects(canvas, width, verticalOffset);
/*      */     }
/*      */     
/* 2316 */     return handled;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onReleaseEdgeEffects() {
/* 2324 */     if (this.mTool != null) {
/* 2325 */       this.mTool.onReleaseEdgeEffects();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onPullEdgeEffects(int which_edge, float delta_distance) {
/* 2334 */     if (this.mTool != null) {
/* 2335 */       this.mTool.onPullEdgeEffects(which_edge, delta_distance);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDoubleTapZoomAnimationBegin() {
/* 2344 */     if (this.mAnnotIndicatorManger != null) {
/* 2345 */       this.mAnnotIndicatorManger.updateState(1);
/* 2346 */       this.mAnnotIndicatorManger.reset(true);
/*      */     } 
/*      */     
/* 2349 */     if (this.mTool != null) {
/* 2350 */       ToolModeBase prev_tm = this.mTool.getToolMode();
/*      */       while (true) {
/* 2352 */         this.mTool.onDoubleTapZoomAnimationBegin();
/* 2353 */         ToolModeBase next_tm = this.mTool.getNextToolMode();
/* 2354 */         if (prev_tm != next_tm) {
/* 2355 */           this.mTool = createTool(next_tm, this.mTool);
/* 2356 */           prev_tm = next_tm;
/*      */           continue;
/*      */         } 
/*      */         break;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDoubleTapZoomAnimationEnd() {
/* 2369 */     if (this.mAnnotIndicatorManger != null) {
/* 2370 */       this.mAnnotIndicatorManger.updateState(0);
/*      */     }
/*      */     
/* 2373 */     if (this.mTool != null) {
/* 2374 */       ToolModeBase prev_tm = this.mTool.getToolMode();
/*      */       while (true) {
/* 2376 */         this.mTool.onDoubleTapZoomAnimationEnd();
/* 2377 */         ToolModeBase next_tm = this.mTool.getNextToolMode();
/* 2378 */         if (prev_tm != next_tm) {
/* 2379 */           this.mTool = createTool(next_tm, this.mTool);
/* 2380 */           prev_tm = next_tm;
/*      */           continue;
/*      */         } 
/*      */         break;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onRenderingFinished() {
/* 2393 */     if (this.mPdfViewCtrl.isAnnotationLayerEnabled()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 2398 */     if (this.mOldTools != null) {
/* 2399 */       for (Tool tool : this.mOldTools) {
/* 2400 */         tool.onRenderingFinished();
/*      */       }
/* 2402 */       this.mOldTools.clear();
/*      */     } 
/*      */     
/* 2405 */     if (this.mTool != null && (this.mTool
/* 2406 */       .getToolMode() == ToolMode.ANNOT_EDIT || this.mTool
/* 2407 */       .getToolMode() == ToolMode.ANNOT_EDIT_LINE || this.mTool
/* 2408 */       .getToolMode() == ToolMode.ANNOT_EDIT_ADVANCED_SHAPE || this.mTool
/* 2409 */       .getToolMode() == ToolMode.TEXT_CREATE || this.mTool
/* 2410 */       .getToolMode() == ToolMode.CALLOUT_CREATE)) {
/* 2411 */       ToolModeBase prev_tm = this.mTool.getToolMode();
/*      */       while (true) {
/* 2413 */         this.mTool.onRenderingFinished();
/* 2414 */         ToolModeBase next_tm = this.mTool.getNextToolMode();
/* 2415 */         if (prev_tm != next_tm) {
/* 2416 */           this.mTool = createTool(next_tm, this.mTool);
/* 2417 */           prev_tm = next_tm;
/*      */           continue;
/*      */         } 
/*      */         break;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isCreatingAnnotation() {
/* 2430 */     return (this.mTool != null && this.mTool.isCreatingAnnotation());
/*      */   }
/*      */ 
/*      */   
/*      */   public void onDestroy() {
/* 2435 */     destroy();
/*      */   }
/*      */ 
/*      */   
/*      */   public void onAnnotPainterUpdated(final int page, final long which, final CurvePainter painter) {
/* 2440 */     FragmentActivity activity = getCurrentActivity();
/* 2441 */     if (activity == null) {
/*      */       return;
/*      */     }
/* 2444 */     activity.runOnUiThread(new Runnable()
/*      */         {
/*      */           public void run() {
/* 2447 */             if (ToolManager.this.mTool != null) {
/* 2448 */               ToolManager.this.mTool.onAnnotPainterUpdated(page, which, painter);
/*      */             }
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onActionCompleted(Action action) {
/* 2459 */     boolean hasChange = false;
/* 2460 */     boolean shouldUnlockRead = false;
/*      */     try {
/* 2462 */       this.mPdfViewCtrl.docLockRead();
/* 2463 */       shouldUnlockRead = true;
/* 2464 */       hasChange = this.mPdfViewCtrl.getDoc().hasChangesSinceSnapshot();
/* 2465 */     } catch (Exception ex) {
/* 2466 */       AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*      */     } finally {
/* 2468 */       if (shouldUnlockRead) {
/* 2469 */         this.mPdfViewCtrl.docUnlockRead();
/* 2470 */         if (hasChange) {
/* 2471 */           raiseAnnotationActionEvent();
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Tool getTool() {
/* 2481 */     return this.mTool;
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
/*      */   public void setTool(Tool t) {
/* 2493 */     this.mTool = t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PDFViewCtrl getPDFViewCtrl() {
/* 2500 */     return this.mPdfViewCtrl;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public UndoRedoManager getUndoRedoManger() {
/* 2509 */     return this.mUndoRedoManger;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void enableAnnotManager(String userId) {
/* 2518 */     enableAnnotManager(userId, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void enableAnnotManager(String userId, AnnotManager.AnnotationSyncingListener listener) {
/* 2528 */     enableAnnotManager(userId, null, null, listener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void enableAnnotManager(String userId, String userName, AnnotManager.AnnotationSyncingListener listener) {
/* 2539 */     enableAnnotManager(userId, userName, null, listener);
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
/*      */   public void enableAnnotManager(String userId, String userName, Bundle initialAnnot, AnnotManager.AnnotationSyncingListener listener) {
/* 2551 */     if (null == userId) {
/* 2552 */       this.mAnnotManager = null;
/*      */       return;
/*      */     } 
/*      */     try {
/* 2556 */       this.mAnnotManager = new AnnotManager(this, userId, userName, initialAnnot, listener);
/* 2557 */     } catch (Exception e) {
/* 2558 */       e.printStackTrace();
/* 2559 */       this.mAnnotManager = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public AnnotManager getAnnotManager() {
/* 2568 */     return this.mAnnotManager;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   public RedactionManager getRedactionManager() {
/* 2576 */     if (null == this.mRedactionManager) {
/* 2577 */       this.mRedactionManager = new RedactionManager(this.mPdfViewCtrl);
/*      */     }
/* 2579 */     return this.mRedactionManager;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addToolChangedListener(ToolChangedListener listener) {
/* 2588 */     if (this.mToolChangedListeners == null) {
/* 2589 */       this.mToolChangedListeners = new ArrayList<>();
/*      */     }
/* 2591 */     if (!this.mToolChangedListeners.contains(listener)) {
/* 2592 */       this.mToolChangedListeners.add(listener);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeToolChangedListener(ToolChangedListener listener) {
/* 2602 */     if (this.mToolChangedListeners != null) {
/* 2603 */       this.mToolChangedListeners.remove(listener);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addOnLayoutListener(OnLayoutListener listener) {
/* 2613 */     if (this.mOnLayoutListeners == null) {
/* 2614 */       this.mOnLayoutListeners = new CopyOnWriteArray<>();
/*      */     }
/*      */     
/* 2617 */     this.mOnLayoutListeners.add(listener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeOnLayoutListener(OnLayoutListener listener) {
/* 2626 */     if (this.mOnLayoutListeners != null) {
/* 2627 */       this.mOnLayoutListeners.remove(listener);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPreToolManagerListener(PreToolManagerListener listener) {
/* 2637 */     this.mPreToolManagerListener = listener;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setQuickMenuListener(QuickMenuListener listener) {
/* 2646 */     this.mQuickMenuListener = listener;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addAnnotationModificationListener(AnnotationModificationListener listener) {
/* 2655 */     if (this.mAnnotationModificationListeners == null) {
/* 2656 */       this.mAnnotationModificationListeners = new ArrayList<>();
/*      */     }
/* 2658 */     if (!this.mAnnotationModificationListeners.contains(listener)) {
/* 2659 */       this.mAnnotationModificationListeners.add(listener);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeAnnotationModificationListener(AnnotationModificationListener listener) {
/* 2669 */     if (this.mAnnotationModificationListeners != null) {
/* 2670 */       this.mAnnotationModificationListeners.remove(listener);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addPdfDocModificationListener(PdfDocModificationListener listener) {
/* 2680 */     if (this.mPdfDocModificationListeners == null) {
/* 2681 */       this.mPdfDocModificationListeners = new ArrayList<>();
/*      */     }
/* 2683 */     if (!this.mPdfDocModificationListeners.contains(listener)) {
/* 2684 */       this.mPdfDocModificationListeners.add(listener);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removePdfDocModificationListener(PdfDocModificationListener listener) {
/* 2694 */     if (this.mPdfDocModificationListeners != null) {
/* 2695 */       this.mPdfDocModificationListeners.remove(listener);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addAnnotationsSelectionListener(AnnotationsSelectionListener listener) {
/* 2705 */     if (this.mAnnotationsSelectionListeners == null) {
/* 2706 */       this.mAnnotationsSelectionListeners = new ArrayList<>();
/*      */     }
/* 2708 */     if (!this.mAnnotationsSelectionListeners.contains(listener)) {
/* 2709 */       this.mAnnotationsSelectionListeners.add(listener);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeAnnotationsSelectionListener(AnnotationsSelectionListener listener) {
/* 2719 */     if (this.mAnnotationsSelectionListeners != null) {
/* 2720 */       this.mAnnotationsSelectionListeners.remove(listener);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBasicAnnotationListener(BasicAnnotationListener listener) {
/* 2730 */     this.mBasicAnnotationListener = listener;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAdvancedAnnotationListener(AdvancedAnnotationListener listener) {
/* 2739 */     this.mAdvancedAnnotationListener = listener;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSpecialAnnotationListener(SpecialAnnotationListener listener) {
/* 2747 */     this.mSpecialAnnotationListener = listener;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onQuickMenuClicked(QuickMenuItem menuItem) {
/* 2757 */     if (this.mQuickMenuListener != null && this.mQuickMenuListener.onQuickMenuClicked(menuItem)) {
/* 2758 */       return true;
/*      */     }
/*      */     
/* 2761 */     boolean handled = false;
/* 2762 */     if (this.mTool != null && this.mTool instanceof Tool) {
/* 2763 */       ToolModeBase prev_tm = this.mTool.getToolMode();
/*      */       while (true) {
/* 2765 */         handled = ((Tool)this.mTool).onQuickMenuClicked(menuItem);
/* 2766 */         ToolModeBase next_tm = this.mTool.getNextToolMode();
/*      */         
/* 2768 */         if (!handled && prev_tm != next_tm) {
/* 2769 */           this.mTool = createTool(next_tm, this.mTool);
/* 2770 */           prev_tm = next_tm;
/*      */           
/*      */           continue;
/*      */         } 
/*      */         break;
/*      */       } 
/*      */     } 
/* 2777 */     return handled;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onShowQuickMenu(QuickMenu quickMenu, Annot annot) {
/* 2784 */     return (this.mQuickMenuListener != null && this.mQuickMenuListener.onShowQuickMenu(quickMenu, annot));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onQuickMenuShown() {
/* 2791 */     if (this.mQuickMenuListener != null) {
/* 2792 */       this.mQuickMenuListener.onQuickMenuShown();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onQuickMenuDismissed() {
/* 2800 */     if (this.mQuickMenuListener != null) {
/* 2801 */       this.mQuickMenuListener.onQuickMenuDismissed();
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
/*      */   public boolean raiseInterceptAnnotationHandlingEvent(@Nullable Annot annot, Bundle extra, ToolMode toolMode) {
/* 2813 */     return (this.mBasicAnnotationListener != null && this.mBasicAnnotationListener.onInterceptAnnotationHandling(annot, extra, toolMode));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean raiseInterceptDialogEvent(AlertDialog dialog) {
/* 2820 */     return (this.mBasicAnnotationListener != null && this.mBasicAnnotationListener.onInterceptDialog(dialog));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void raiseAnnotationsAddedEvent(Map<Annot, Integer> annots) {
/* 2829 */     if (this.mAnnotIndicatorManger != null) {
/* 2830 */       this.mAnnotIndicatorManger.reset(true);
/*      */     }
/*      */     
/* 2833 */     if (this.mAnnotationModificationListeners != null) {
/* 2834 */       for (AnnotationModificationListener listener : this.mAnnotationModificationListeners) {
/* 2835 */         listener.onAnnotationsAdded(annots);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void raiseAnnotationsPreModifyEvent(Map<Annot, Integer> annots) {
/* 2846 */     if (this.mAnnotationModificationListeners != null) {
/* 2847 */       for (AnnotationModificationListener listener : this.mAnnotationModificationListeners) {
/* 2848 */         listener.onAnnotationsPreModify(annots);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void raiseAnnotationsModifiedEvent(Map<Annot, Integer> annots, Bundle bundle) {
/* 2859 */     if (this.mAnnotIndicatorManger != null) {
/* 2860 */       this.mAnnotIndicatorManger.reset(true);
/*      */     }
/*      */     
/* 2863 */     if (this.mAnnotationModificationListeners != null) {
/* 2864 */       for (AnnotationModificationListener listener : this.mAnnotationModificationListeners) {
/* 2865 */         listener.onAnnotationsModified(annots, bundle);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void raiseAnnotationsPreRemoveEvent(Map<Annot, Integer> annots) {
/* 2876 */     if (this.mAnnotationModificationListeners != null) {
/* 2877 */       for (AnnotationModificationListener listener : this.mAnnotationModificationListeners) {
/* 2878 */         listener.onAnnotationsPreRemove(annots);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void raiseAnnotationsRemovedEvent(Map<Annot, Integer> annots) {
/* 2889 */     if (this.mAnnotIndicatorManger != null) {
/* 2890 */       this.mAnnotIndicatorManger.reset(true);
/*      */     }
/*      */     
/* 2893 */     if (this.mAnnotationModificationListeners != null) {
/* 2894 */       for (AnnotationModificationListener listener : this.mAnnotationModificationListeners) {
/* 2895 */         listener.onAnnotationsRemoved(annots);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void raiseAnnotationsRemovedEvent(int pageNum) {
/* 2906 */     if (this.mAnnotIndicatorManger != null) {
/* 2907 */       this.mAnnotIndicatorManger.reset(true);
/*      */     }
/*      */     
/* 2910 */     if (this.mAnnotationModificationListeners != null) {
/* 2911 */       for (AnnotationModificationListener listener : this.mAnnotationModificationListeners) {
/* 2912 */         listener.onAnnotationsRemovedOnPage(pageNum);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void annotationCouldNotBeAdded(String errorMessage) {
/* 2922 */     if (this.mAnnotationModificationListeners != null) {
/* 2923 */       for (AnnotationModificationListener listener : this.mAnnotationModificationListeners) {
/* 2924 */         listener.annotationsCouldNotBeAdded(errorMessage);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void raiseBookmarkModified() {
/* 2933 */     if (this.mPdfDocModificationListeners != null) {
/* 2934 */       for (PdfDocModificationListener listener : this.mPdfDocModificationListeners) {
/* 2935 */         listener.onBookmarkModified();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void raisePagesCropped() {
/* 2944 */     if (this.mPdfDocModificationListeners != null) {
/* 2945 */       for (PdfDocModificationListener listener : this.mPdfDocModificationListeners) {
/* 2946 */         listener.onPagesCropped();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void raisePagesAdded(List<Integer> pageList) {
/* 2955 */     if (this.mPdfDocModificationListeners != null) {
/* 2956 */       for (PdfDocModificationListener listener : this.mPdfDocModificationListeners) {
/* 2957 */         listener.onPagesAdded(pageList);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void raisePagesDeleted(List<Integer> pageList) {
/* 2966 */     if (this.mPdfDocModificationListeners != null) {
/* 2967 */       for (PdfDocModificationListener listener : this.mPdfDocModificationListeners) {
/* 2968 */         listener.onPagesDeleted(pageList);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void raisePagesRotated(List<Integer> pageList) {
/* 2977 */     if (this.mPdfDocModificationListeners != null) {
/* 2978 */       for (PdfDocModificationListener listener : this.mPdfDocModificationListeners) {
/* 2979 */         listener.onPagesRotated(pageList);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void raisePageMoved(int from, int to) {
/* 2988 */     if (this.mPdfDocModificationListeners != null) {
/* 2989 */       for (PdfDocModificationListener listener : this.mPdfDocModificationListeners) {
/* 2990 */         listener.onPageMoved(from, to);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void raiseAllAnnotationsRemovedEvent() {
/* 2999 */     if (this.mAnnotIndicatorManger != null) {
/* 3000 */       this.mAnnotIndicatorManger.reset(true);
/*      */     }
/*      */     
/* 3003 */     if (this.mPdfDocModificationListeners != null) {
/* 3004 */       for (PdfDocModificationListener listener : this.mPdfDocModificationListeners) {
/* 3005 */         listener.onAllAnnotationsRemoved();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void raiseAnnotationActionEvent() {
/* 3014 */     if (this.mPdfDocModificationListeners != null) {
/* 3015 */       for (PdfDocModificationListener listener : this.mPdfDocModificationListeners) {
/* 3016 */         listener.onAnnotationAction();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void raisePageLabelChangedEvent() {
/* 3025 */     if (this.mPdfDocModificationListeners != null) {
/* 3026 */       for (PdfDocModificationListener listener : this.mPdfDocModificationListeners) {
/* 3027 */         listener.onPageLabelsChanged();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void raiseAnnotationsSelectionChangedEvent(HashMap<Annot, Integer> annots) {
/* 3038 */     if (this.mAnnotationsSelectionListeners != null) {
/* 3039 */       for (AnnotationsSelectionListener listener : this.mAnnotationsSelectionListeners) {
/* 3040 */         listener.onAnnotationsSelectionChanged(annots);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onFileAttachmentSelected(FileAttachment fileAttachment) {
/* 3051 */     if (this.mAdvancedAnnotationListener != null) {
/* 3052 */       this.mAdvancedAnnotationListener.fileAttachmentSelected(fileAttachment);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onFileCreated(String fileLocation, AdvancedAnnotationListener.AnnotAction action) {
/* 3063 */     if (this.mAdvancedAnnotationListener != null) {
/* 3064 */       this.mAdvancedAnnotationListener.fileCreated(fileLocation, action);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onFreehandStylusUsedFirstTime() {
/* 3072 */     if (this.mAdvancedAnnotationListener != null) {
/* 3073 */       this.mAdvancedAnnotationListener.freehandStylusUsedFirstTime();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onImageStamperSelected(PointF targetPoint) {
/* 3083 */     if (this.mAdvancedAnnotationListener != null) {
/* 3084 */       this.mAdvancedAnnotationListener.imageStamperSelected(targetPoint);
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
/*      */   public void onImageSignatureSelected(PointF targetPoint, int targetPage, Long widget) {
/* 3096 */     if (this.mAdvancedAnnotationListener != null) {
/* 3097 */       this.mAdvancedAnnotationListener.imageSignatureSelected(targetPoint, targetPage, widget);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onAttachFileSelected(PointF targetPoint) {
/* 3107 */     if (this.mAdvancedAnnotationListener != null) {
/* 3108 */       this.mAdvancedAnnotationListener.attachFileSelected(targetPoint);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onInlineFreeTextEditingStarted() {
/* 3116 */     if (this.mAdvancedAnnotationListener != null) {
/* 3117 */       this.mAdvancedAnnotationListener.freeTextInlineEditingStarted();
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean onNewFileCreated(String filePath) {
/* 3122 */     if (this.mAdvancedAnnotationListener != null) {
/* 3123 */       return this.mAdvancedAnnotationListener.newFileSelectedFromTool(filePath);
/*      */     }
/* 3125 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void defineTranslateSelected(String text, RectF anchor, Boolean isDefine) {
/* 3135 */     if (this.mSpecialAnnotationListener != null) {
/* 3136 */       this.mSpecialAnnotationListener.defineTranslateSelected(text, anchor, isDefine);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onInkEditSelected(Annot inkAnnot, int pageNum) {
/* 3146 */     if (this.mAnnotationToolbarListener != null) {
/* 3147 */       this.mAnnotationToolbarListener.inkEditSelected(inkAnnot, pageNum);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onOpenAnnotationToolbar(ToolMode mode) {
/* 3158 */     if (this.mAnnotationToolbarListener != null) {
/* 3159 */       this.mAnnotationToolbarListener.openAnnotationToolbar(mode);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onOpenEditToolbar(ToolMode mode) {
/* 3169 */     if (this.mAnnotationToolbarListener != null) {
/* 3170 */       this.mAnnotationToolbarListener.openEditToolbar(mode);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getAnnotationToolbarHeight() {
/* 3180 */     if (this.mAnnotationToolbarListener != null) {
/* 3181 */       return this.mAnnotationToolbarListener.annotationToolbarHeight();
/*      */     }
/* 3183 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getToolbarHeight() {
/* 3192 */     if (this.mAnnotationToolbarListener != null) {
/* 3193 */       return this.mAnnotationToolbarListener.toolbarHeight();
/*      */     }
/* 3195 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAnnotationToolbarListener(AnnotationToolbarListener annotationToolbarListener) {
/* 3204 */     this.mAnnotationToolbarListener = annotationToolbarListener;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOnGenericMotionEventListener(OnGenericMotionEventListener onGenericMotionEventListener) {
/* 3213 */     this.mOnGenericMotionEventListener = onGenericMotionEventListener;
/*      */   }
/*      */   
/*      */   public void setExternalAnnotationManagerListener(ExternalAnnotationManagerListener externalAnnotationManagerListener) {
/* 3217 */     this.mExternalAnnotationManagerListener = externalAnnotationManagerListener;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBuiltInPageNumberIndicatorVisible(boolean visible) {
/* 3227 */     this.mPageNumberIndicatorVisible = visible;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isBuiltInPageNumberIndicatorVisible() {
/* 3237 */     return this.mPageNumberIndicatorVisible;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setReadOnly(boolean readOnly) {
/* 3244 */     this.mReadOnly = readOnly;
/* 3245 */     ToolMode[] editableToolModes = { ToolMode.ANNOT_EDIT, ToolMode.ANNOT_EDIT_LINE, ToolMode.ANNOT_EDIT_TEXT_MARKUP, ToolMode.ANNOT_EDIT_ADVANCED_SHAPE, ToolMode.ANNOT_EDIT_RECT_GROUP, ToolMode.FORM_FILL, ToolMode.RECT_LINK, ToolMode.INK_CREATE, ToolMode.FREE_HIGHLIGHTER, ToolMode.LINE_CREATE, ToolMode.ARROW_CREATE, ToolMode.RULER_CREATE, ToolMode.PERIMETER_MEASURE_CREATE, ToolMode.AREA_MEASURE_CREATE, ToolMode.RECT_AREA_MEASURE_CREATE, ToolMode.POLYLINE_CREATE, ToolMode.RECT_CREATE, ToolMode.OVAL_CREATE, ToolMode.SOUND_CREATE, ToolMode.FILE_ATTACHMENT_CREATE, ToolMode.POLYGON_CREATE, ToolMode.CLOUD_CREATE, ToolMode.TEXT_CREATE, ToolMode.CALLOUT_CREATE, ToolMode.TEXT_ANNOT_CREATE, ToolMode.TEXT_LINK_CREATE, ToolMode.FORM_CHECKBOX_CREATE, ToolMode.FORM_COMBO_BOX_CREATE, ToolMode.FORM_LIST_BOX_CREATE, ToolMode.FORM_SIGNATURE_CREATE, ToolMode.FORM_TEXT_FIELD_CREATE, ToolMode.FORM_RADIO_GROUP_CREATE, ToolMode.SIGNATURE, ToolMode.STAMPER, ToolMode.RUBBER_STAMPER, ToolMode.INK_ERASER, ToolMode.TEXT_HIGHLIGHT, ToolMode.TEXT_SQUIGGLY, ToolMode.TEXT_STRIKEOUT, ToolMode.TEXT_UNDERLINE, ToolMode.TEXT_REDACTION, ToolMode.RECT_REDACTION, ToolMode.FREE_TEXT_SPACING_CREATE, ToolMode.FREE_TEXT_DATE_CREATE };
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
/* 3291 */     if (readOnly) {
/*      */       
/* 3293 */       if (this.mDisabledToolModes != null && this.mDisabledToolModes.size() > 0) {
/* 3294 */         if (this.mDisabledToolModesSave == null) {
/* 3295 */           this.mDisabledToolModesSave = new HashSet<>();
/*      */         }
/* 3297 */         this.mDisabledToolModesSave.clear();
/* 3298 */         this.mDisabledToolModesSave.addAll(this.mDisabledToolModes);
/*      */       } 
/*      */       
/* 3301 */       disableToolMode(editableToolModes);
/*      */     } else {
/* 3303 */       enableToolMode(editableToolModes);
/*      */ 
/*      */       
/* 3306 */       if (this.mDisabledToolModesSave != null && this.mDisabledToolModesSave.size() > 0) {
/* 3307 */         disableToolMode(this.mDisabledToolModesSave.<ToolMode>toArray(new ToolMode[this.mDisabledToolModesSave.size()]));
/* 3308 */         this.mDisabledToolModesSave.clear();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isReadOnly() {
/* 3317 */     return this.mReadOnly;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAnnotPermissionCheckEnabled(boolean enable) {
/* 3327 */     this.mCanCheckAnnotPermission = enable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAnnotPermissionCheckEnabled() {
/* 3336 */     return this.mCanCheckAnnotPermission;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAuthorId(String authorId) {
/* 3345 */     this.mAuthorId = authorId;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getAuthorId() {
/* 3354 */     return this.mAuthorId;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAuthorName(String authorName) {
/* 3363 */     this.mAuthorName = authorName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getAuthorName() {
/* 3372 */     return this.mAuthorName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSelectedAnnot(Annot annot, int pageNum) {
/*      */     try {
/* 3383 */       this.mSelectedAnnotId = (annot == null) ? null : ((annot.getUniqueID() == null) ? null : annot.getUniqueID().getAsPDFText());
/* 3384 */     } catch (Exception e) {
/* 3385 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } 
/* 3387 */     this.mSelectedAnnotPageNum = pageNum;
/* 3388 */     if (this.mBasicAnnotationListener != null) {
/* 3389 */       if (null == annot) {
/* 3390 */         this.mBasicAnnotationListener.onAnnotationUnselected();
/*      */       } else {
/* 3392 */         this.mBasicAnnotationListener.onAnnotationSelected(annot, pageNum);
/*      */       } 
/*      */     }
/* 3395 */     if (null == annot) {
/* 3396 */       raiseAnnotationsSelectionChangedEvent(new HashMap<>());
/*      */     } else {
/* 3398 */       HashMap<Annot, Integer> map = new HashMap<>(1);
/* 3399 */       map.put(annot, Integer.valueOf(pageNum));
/* 3400 */       raiseAnnotationsSelectionChangedEvent(map);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getSelectedAnnotId() {
/* 3410 */     return this.mSelectedAnnotId;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSelectedAnnotPageNum() {
/* 3419 */     return this.mSelectedAnnotPageNum;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void deselectAll() {
/* 3426 */     this.mTool.onClose();
/*      */     
/* 3428 */     setTool(createTool(getTool().getToolMode(), null));
/* 3429 */     this.mPdfViewCtrl.invalidate();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void selectAnnot(String annotId, int pageNum) {
/* 3439 */     Annot annot = ViewerUtils.getAnnotById(this.mPdfViewCtrl, annotId, pageNum);
/* 3440 */     if (null != annot) {
/* 3441 */       selectAnnot(annot, pageNum);
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
/*      */   public void selectAnnot(@Nullable Annot annot, int pageNum) {
/*      */     try {
/* 3456 */       if (annot == null || !annot.isValid()) {
/*      */         return;
/*      */       }
/*      */       
/* 3460 */       ToolMode mode = ToolMode.ANNOT_EDIT;
/* 3461 */       int annotType = annot.getType();
/* 3462 */       if (annotType == 3) {
/* 3463 */         mode = ToolMode.ANNOT_EDIT_LINE;
/* 3464 */       } else if (annotType == 7 || (annotType == 6 && 
/* 3465 */         !AnnotUtils.isRectAreaMeasure(annot)) || 
/* 3466 */         AnnotUtils.isCallout(annot)) {
/* 3467 */         mode = ToolMode.ANNOT_EDIT_ADVANCED_SHAPE;
/* 3468 */       } else if (annotType == 8 || annotType == 11 || annotType == 9 || annotType == 10) {
/*      */ 
/*      */ 
/*      */         
/* 3472 */         mode = ToolMode.ANNOT_EDIT_TEXT_MARKUP;
/*      */       } 
/*      */       
/* 3475 */       Pair<ToolMode, ArrayList<Annot>> pair = Tool.canSelectGroupAnnot(this.mPdfViewCtrl, annot, pageNum);
/* 3476 */       if (pair != null && pair.first != null) {
/* 3477 */         mode = (ToolMode)pair.first;
/*      */       }
/*      */       
/* 3480 */       if (this.mTool.getToolMode() != mode) {
/* 3481 */         this.mTool = createTool(mode, this.mTool);
/*      */       }
/* 3483 */       if (this.mTool instanceof AnnotEdit || this.mTool instanceof AnnotEditTextMarkup)
/*      */       {
/* 3485 */         ((Tool)this.mTool).selectAnnot(annot, pageNum);
/*      */       }
/* 3487 */     } catch (Exception e) {
/* 3488 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void reselectAnnot() {
/* 3496 */     if (null != this.mSelectedAnnotId && this.mSelectedAnnotPageNum > 0 && this.mPdfViewCtrl.getDoc() != null) {
/* 3497 */       Annot annot = ViewerUtils.getAnnotById(this.mPdfViewCtrl, this.mSelectedAnnotId, this.mSelectedAnnotPageNum);
/* 3498 */       if (annot != null) {
/* 3499 */         selectAnnot(annot, this.mSelectedAnnotPageNum);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setQuickMenuJustClosed(boolean closed) {
/* 3510 */     this.mQuickMenuJustClosed = closed;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isQuickMenuJustClosed() {
/* 3517 */     boolean result = this.mQuickMenuJustClosed;
/* 3518 */     this.mQuickMenuJustClosed = false;
/* 3519 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setShowAuthorDialog(boolean show) {
/* 3526 */     this.mShowAuthorDialog = show;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isShowAuthorDialog() {
/* 3533 */     return this.mShowAuthorDialog;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTextMarkupAdobeHack(boolean enable) {
/* 3541 */     this.mTextMarkupAdobeHack = enable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isTextMarkupAdobeHack() {
/* 3549 */     return this.mTextMarkupAdobeHack;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCopyAnnotatedTextToNoteEnabled(boolean enable) {
/* 3558 */     this.mCopyAnnotatedTextToNote = enable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isCopyAnnotatedTextToNoteEnabled() {
/* 3567 */     return this.mCopyAnnotatedTextToNote;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setStylusAsPen(boolean stylusAsPen) {
/* 3576 */     this.mStylusAsPen = stylusAsPen;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isStylusAsPen() {
/* 3585 */     return this.mStylusAsPen;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setInkSmoothingEnabled(boolean enable) {
/* 3594 */     this.mInkSmoothing = enable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInkSmoothingEnabled() {
/* 3603 */     return this.mInkSmoothing;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFreeTextFonts(Set<String> freeTextFonts) {
/* 3611 */     this.mFreeTextFonts = freeTextFonts;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<String> getFreeTextFonts() {
/* 3619 */     return this.mFreeTextFonts;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNightMode(boolean isNightMode) {
/* 3628 */     this.mIsNightMode = isNightMode;
/* 3629 */     if (this.mTool != null) {
/* 3630 */       this.mTool.onNightModeUpdated(isNightMode);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isNightMode() {
/* 3640 */     return this.mIsNightMode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setShowAnnotIndicators(boolean showAnnotIndicators) {
/* 3650 */     this.mShowAnnotIndicators = showAnnotIndicators;
/* 3651 */     if (showAnnotIndicators) {
/* 3652 */       this.mAnnotIndicatorManger = new AnnotIndicatorManger(this);
/*      */     } else {
/* 3654 */       if (this.mAnnotIndicatorManger != null) {
/* 3655 */         this.mAnnotIndicatorManger.cleanup();
/*      */       }
/* 3657 */       this.mAnnotIndicatorManger = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isShowAnnotIndicators() {
/* 3668 */     return this.mShowAnnotIndicators;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ArrayList<Tool> getOldTools() {
/* 3675 */     if (this.mOldTools == null) {
/* 3676 */       this.mOldTools = new ArrayList<>();
/*      */     }
/* 3678 */     return this.mOldTools;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void initTTS() {
/*      */     try {
/* 3687 */       this.mTTS = new TextToSpeech(this.mPdfViewCtrl.getContext().getApplicationContext(), new TextToSpeech.OnInitListener()
/*      */           {
/*      */ 
/*      */             
/*      */             public void onInit(int status) {}
/*      */           });
/* 3693 */     } catch (Exception ignored) {
/* 3694 */       ignored.printStackTrace();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TextToSpeech getTTS() {
/* 3704 */     return this.mTTS;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEditInkAnnots(boolean editInkAnnots) {
/* 3713 */     this.mEditInkAnnots = editInkAnnots;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean editInkAnnots() {
/* 3722 */     return this.mEditInkAnnots;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAddImageStamperTool(boolean addImageStamperTool) {
/* 3732 */     this.mAddImageStamperTool = addImageStamperTool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCanOpenEditToolbarFromPan(boolean canOpenEditToolbarFromPan) {
/* 3743 */     this.mCanOpenEditToolbarFromPan = canOpenEditToolbarFromPan;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isOpenEditToolbarFromPan() {
/* 3753 */     return this.mCanOpenEditToolbarFromPan;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAutoSelectAnnotation(boolean autoSelect) {
/* 3762 */     this.mIsAutoSelectAnnotation = autoSelect;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAutoSelectAnnotation() {
/* 3771 */     return this.mIsAutoSelectAnnotation;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDisableQuickMenu(boolean disabled) {
/* 3780 */     this.mDisableQuickMenu = disabled;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isQuickMenuDisabled() {
/* 3789 */     return this.mDisableQuickMenu;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDoubleTapToZoom(boolean doubleTapToZoom) {
/* 3798 */     this.mDoubleTapToZoom = doubleTapToZoom;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDoubleTapToZoom() {
/* 3807 */     return this.mDoubleTapToZoom;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAutoResizeFreeText() {
/* 3816 */     return this.mAutoResizeFreeText;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAutoResizeFreeText(boolean autoResizeFreeText) {
/* 3825 */     this.mAutoResizeFreeText = autoResizeFreeText;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRealTimeAnnotEdit() {
/* 3834 */     return this.mRealTimeAnnotEdit;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRealTimeAnnotEdit(boolean realTimeAnnotEdit) {
/* 3843 */     this.mRealTimeAnnotEdit = realTimeAnnotEdit;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEditFreeTextOnTap() {
/* 3852 */     return this.mEditFreeTextOnTap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEditFreeTextOnTap(boolean editFreeTextOnTap) {
/* 3861 */     this.mEditFreeTextOnTap = editFreeTextOnTap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isfreeTextInlineToggleEnabled() {
/* 3870 */     return this.freeTextInlineToggleEnabled;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void freeTextInlineToggleEnabled(boolean freeTextInlineToggleEnabled) {
/* 3879 */     this.freeTextInlineToggleEnabled = freeTextInlineToggleEnabled;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDeleteEmptyFreeText() {
/* 3888 */     return this.mDeleteEmptyFreeText;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDeleteEmptyFreeText(boolean deleteEmptyFreeText) {
/* 3897 */     this.mDeleteEmptyFreeText = deleteEmptyFreeText;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setShowSavedSignatures(boolean showSavedSignature) {
/* 3906 */     this.mShowSavedSignature = showSavedSignature;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isShowSavedSignature() {
/* 3915 */     return this.mShowSavedSignature;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setShowSignatureFromImage(boolean showSignatureFromImage) {
/* 3924 */     this.mShowSignatureFromImage = showSignatureFromImage;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isShowSignatureFromImage() {
/* 3933 */     return this.mShowSignatureFromImage;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUsingDigitalSignature(boolean usingDigitalSignature) {
/* 3942 */     this.mUsingDigitalSignature = usingDigitalSignature;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isUsingDigitalSignature() {
/* 3951 */     return this.mUsingDigitalSignature;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDigitalSignatureKeystorePath(String digitalSignatureKeystore) {
/* 3958 */     this.mDigitalSignatureKeystorePath = digitalSignatureKeystore;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDigitalSignatureKeystore() {
/* 3967 */     return this.mDigitalSignatureKeystorePath;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDigitalSignatureKeystorePassword(String digitalSignatureKeystore) {
/* 3974 */     this.mDigitalSignatureKeystorePassword = digitalSignatureKeystore;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDigitalSignatureKeystorePassword() {
/* 3983 */     return this.mDigitalSignatureKeystorePassword;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void enableAnnotationLayer() {
/* 3991 */     this.mPdfViewCtrl.enableAnnotationLayer();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void skipNextTapEvent() {
/* 3998 */     this.mSkipNextTouchEvent = true;
/* 3999 */     this.mSkipNextTapEvent = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSkipNextTapEvent() {
/* 4006 */     return (this.mSkipNextTouchEvent || this.mSkipNextTapEvent);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void resetSkipNextTapEvent() {
/* 4013 */     this.mSkipNextTouchEvent = false;
/* 4014 */     this.mSkipNextTapEvent = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getCacheFileName() {
/* 4021 */     return this.mCacheFileName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCacheFileName(String tag) {
/* 4030 */     this.mCacheFileName = String.valueOf(tag.hashCode());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getFreeTextCacheFileName() {
/* 4037 */     return "freetext_" + this.mCacheFileName + ".srl";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canResumePdfDocWithoutReloading() {
/* 4044 */     return this.mCanResumePdfDocWithoutReloading;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCanResumePdfDocWithoutReloading(boolean canResumePdfDocWithoutReloading) {
/* 4053 */     this.mCanResumePdfDocWithoutReloading = canResumePdfDocWithoutReloading;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void disableAnnotEditing(Integer[] annotTypes) {
/* 4062 */     ToolConfig.getInstance().disableAnnotEditing(annotTypes);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void enableAnnotEditing(Integer[] annotTypes) {
/* 4071 */     ToolConfig.getInstance().disableAnnotEditing(annotTypes);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUsePressureSensitiveSignatures(boolean usePressureSensitiveSignatures) {
/* 4080 */     this.mUsePressureSensitiveSignatures = usePressureSensitiveSignatures;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isUsingPressureSensitiveSignatures() {
/* 4089 */     return this.mUsePressureSensitiveSignatures;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEraserType(Eraser.EraserType type) {
/* 4098 */     this.mEraserType = type;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Eraser.EraserType getEraserType() {
/* 4107 */     return this.mEraserType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setShowUndoRedo(boolean showUndoRedo) {
/* 4116 */     this.mShowUndoRedo = showUndoRedo;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isShowUndoRedo() {
/* 4125 */     return this.mShowUndoRedo;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSelectionBoxMargin(int marginInDp) {
/* 4134 */     this.mSelectionBoxMargin = marginInDp;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSelectionBoxMargin() {
/* 4143 */     return this.mSelectionBoxMargin;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAnnotEditingDisabled(int annotType) {
/* 4153 */     return ToolConfig.getInstance().isAnnotEditingDisabled(annotType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void disableToolMode(ToolMode[] toolModes) {
/* 4162 */     if (this.mDisabledToolModes == null) {
/* 4163 */       this.mDisabledToolModes = new HashSet<>();
/*      */     }
/* 4165 */     Collections.addAll(this.mDisabledToolModes, toolModes);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void enableToolMode(ToolMode[] toolModes) {
/* 4174 */     if (this.mDisabledToolModes == null) {
/*      */       return;
/*      */     }
/* 4177 */     List<ToolMode> toolModeList = Arrays.asList(toolModes);
/* 4178 */     this.mDisabledToolModes.removeAll(toolModeList);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isToolModeDisabled(ToolMode toolMode) {
/* 4188 */     return (this.mDisabledToolModes != null && this.mDisabledToolModes.contains(toolMode));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAnnotToolbarPrecedence(ToolMode[] toolModes) {
/* 4198 */     if (this.mAnnotToolbarPrecedence == null) {
/* 4199 */       this.mAnnotToolbarPrecedence = new ArrayList<>();
/*      */     }
/* 4201 */     this.mAnnotToolbarPrecedence.clear();
/* 4202 */     Collections.addAll(this.mAnnotToolbarPrecedence, toolModes);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ArrayList<ToolMode> getAnnotToolbarPrecedence() {
/* 4211 */     return this.mAnnotToolbarPrecedence;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasAnnotToolbarPrecedence() {
/* 4220 */     return (this.mAnnotToolbarPrecedence != null && this.mAnnotToolbarPrecedence.size() > 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSnappingEnabledForMeasurementTools(boolean enabled) {
/* 4229 */     this.mSnappingEnabled = enabled;
/* 4230 */     if (getTool() != null && getTool() instanceof Tool) {
/* 4231 */       ((Tool)getTool()).setSnappingEnabled(enabled);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSnappingEnabledForMeasurementTools() {
/* 4241 */     return this.mSnappingEnabled;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @RequiresApi(api = 21)
/*      */   public void setRichContentEnabledForFreeText(boolean enabled) {
/* 4251 */     if (!this.mShowRichContentOption) {
/* 4252 */       enabled = false;
/*      */     }
/* 4254 */     this.mRichContentEnabled = enabled;
/* 4255 */     if (getTool() != null && getTool() instanceof FreeTextCreate) {
/* 4256 */       ((FreeTextCreate)getTool()).setRichContentEnabled(enabled);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @RequiresApi(api = 21)
/*      */   public boolean isRichContentEnabledForFreeText() {
/* 4267 */     return this.mRichContentEnabled;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @RequiresApi(api = 21)
/*      */   public void setShowRichContentOption(boolean showRichContentOption) {
/* 4277 */     if (Utils.isLollipop()) {
/* 4278 */       this.mShowRichContentOption = showRichContentOption;
/*      */     } else {
/* 4280 */       this.mShowRichContentOption = false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @RequiresApi(api = 21)
/*      */   public boolean isShowRichContentOption() {
/* 4291 */     return this.mShowRichContentOption;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String generateKey() {
/* 4298 */     if (this.mExternalAnnotationManagerListener != null) {
/* 4299 */       return this.mExternalAnnotationManagerListener.onGenerateKey();
/*      */     }
/* 4301 */     return UUID.randomUUID().toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void destroy() {
/* 4308 */     this.mDisposables.clear();
/* 4309 */     if (this.mAnnotIndicatorManger != null) {
/* 4310 */       this.mAnnotIndicatorManger.cleanup();
/*      */     }
/* 4312 */     if (this.mSelectionLoupeBitmap != null) {
/* 4313 */       this.mSelectionLoupeBitmap.recycle();
/* 4314 */       this.mSelectionLoupeBitmap = null;
/*      */     } 
/* 4316 */     if (this.mSelectionLoupeBitmapRound != null) {
/* 4317 */       this.mSelectionLoupeBitmapRound.recycle();
/* 4318 */       this.mSelectionLoupeBitmapRound = null;
/*      */     } 
/*      */     
/* 4321 */     if (this.mToolChangedListeners != null) {
/* 4322 */       this.mToolChangedListeners.clear();
/*      */     }
/* 4324 */     if (this.mOnLayoutListeners != null) {
/* 4325 */       this.mOnLayoutListeners.clear();
/*      */     }
/* 4327 */     if (this.mAnnotationModificationListeners != null) {
/* 4328 */       this.mAnnotationModificationListeners.clear();
/*      */     }
/* 4330 */     if (this.mPdfDocModificationListeners != null) {
/* 4331 */       this.mPdfDocModificationListeners.clear();
/*      */     }
/* 4333 */     if (this.mAnnotationsSelectionListeners != null) {
/* 4334 */       this.mAnnotationsSelectionListeners.clear();
/*      */     }
/* 4336 */     if (this.mRedactionManager != null) {
/* 4337 */       this.mRedactionManager.destroy();
/*      */     }
/*      */     
/* 4340 */     this.mCurrentActivity = null;
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
/*      */   private static class CopyOnWriteArray<T>
/*      */   {
/* 4361 */     private ArrayList<T> mData = new ArrayList<>();
/*      */     
/*      */     private ArrayList<T> mDataCopy;
/* 4364 */     private final Access<T> mAccess = new Access<>();
/*      */     private boolean mStart;
/*      */     
/*      */     static class Access<T>
/*      */     {
/*      */       private ArrayList<T> mData;
/*      */       private int mSize;
/*      */       
/*      */       T get(int index) {
/* 4373 */         return this.mData.get(index);
/*      */       }
/*      */       
/*      */       int size() {
/* 4377 */         return this.mSize;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private ArrayList<T> getArray() {
/* 4385 */       if (this.mStart) {
/* 4386 */         if (this.mDataCopy == null) this.mDataCopy = new ArrayList<>(this.mData); 
/* 4387 */         return this.mDataCopy;
/*      */       } 
/* 4389 */       return this.mData;
/*      */     }
/*      */     
/*      */     Access<T> start() {
/* 4393 */       if (this.mStart) throw new IllegalStateException("Iteration already started"); 
/* 4394 */       this.mStart = true;
/* 4395 */       this.mDataCopy = null;
/* 4396 */       this.mAccess.mData = this.mData;
/* 4397 */       this.mAccess.mSize = this.mData.size();
/* 4398 */       return this.mAccess;
/*      */     }
/*      */     
/*      */     void end() {
/* 4402 */       if (!this.mStart) throw new IllegalStateException("Iteration not started"); 
/* 4403 */       this.mStart = false;
/* 4404 */       if (this.mDataCopy != null) {
/* 4405 */         this.mData = this.mDataCopy;
/* 4406 */         this.mAccess.mData.clear();
/* 4407 */         this.mAccess.mSize = 0;
/*      */       } 
/* 4409 */       this.mDataCopy = null;
/*      */     }
/*      */     
/*      */     int size() {
/* 4413 */       return getArray().size();
/*      */     }
/*      */     
/*      */     void add(T item) {
/* 4417 */       getArray().add(item);
/*      */     }
/*      */     
/*      */     void addAll(CopyOnWriteArray<T> array) {
/* 4421 */       getArray().addAll(array.mData);
/*      */     }
/*      */     
/*      */     void remove(T item) {
/* 4425 */       getArray().remove(item);
/*      */     }
/*      */     
/*      */     void clear() {
/* 4429 */       getArray().clear();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setStickyNoteShowPopup(boolean show) {
/* 4439 */     this.mStickyNoteShowPopup = show;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getStickyNoteShowPopup() {
/* 4448 */     return this.mStickyNoteShowPopup;
/*      */   } static class Access<T> {
/*      */     private ArrayList<T> mData; private int mSize; T get(int index) {
/*      */       return this.mData.get(index);
/*      */     }
/*      */     int size() {
/*      */       return this.mSize;
/*      */     } }
/*      */   public void addCustomizedTool(Tool tool) {
/* 4457 */     if (null == this.mCustomizedToolClassMap) {
/* 4458 */       this.mCustomizedToolClassMap = new HashMap<>();
/*      */     }
/* 4460 */     this.mCustomizedToolClassMap.put(tool.getToolMode(), tool.getClass());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addCustomizedTool(HashMap<ToolModeBase, Class<? extends Tool>> toolClassMap) {
/* 4469 */     if (null == this.mCustomizedToolClassMap) {
/* 4470 */       this.mCustomizedToolClassMap = new HashMap<>();
/*      */     }
/* 4472 */     this.mCustomizedToolClassMap.putAll(toolClassMap);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addCustomizedTool(Tool tool, Object... params) {
/* 4482 */     addCustomizedTool(tool);
/* 4483 */     if (null == this.mCustomizedToolParamMap) {
/* 4484 */       this.mCustomizedToolParamMap = (HashMap)new HashMap<>();
/*      */     }
/* 4486 */     this.mCustomizedToolParamMap.put(tool.getToolMode(), params);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addCustomizedToolParams(HashMap<ToolModeBase, Object[]> toolParamMap) {
/* 4495 */     if (null == this.mCustomizedToolParamMap) {
/* 4496 */       this.mCustomizedToolParamMap = (HashMap)new HashMap<>();
/*      */     }
/* 4498 */     this.mCustomizedToolParamMap.putAll(toolParamMap);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDefaultToolCLass(Class<? extends Pan> cLass) {
/* 4507 */     this.mDefaultToolClass = cLass;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void showBuiltInPageNumber() {
/*      */     PageIndicatorLayout pageIndicator;
/* 4514 */     if (!this.mPageNumberIndicatorVisible || (this.mPageIndicatorPopup != null && this.mPageIndicatorPopup.isShowing())) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 4519 */     if (this.mPageIndicatorPopup != null) {
/* 4520 */       pageIndicator = (PageIndicatorLayout)this.mPageIndicatorPopup.getContentView();
/*      */     } else {
/* 4522 */       pageIndicator = new PageIndicatorLayout(this.mPdfViewCtrl.getContext());
/* 4523 */       pageIndicator.setPdfViewCtrl(this.mPdfViewCtrl);
/* 4524 */       pageIndicator.setAutoAdjustPosition(false);
/* 4525 */       pageIndicator.setVisibility(0);
/* 4526 */       ViewGroup.MarginLayoutParams mlp = new ViewGroup.MarginLayoutParams(-2, -2);
/* 4527 */       pageIndicator.setLayoutParams((ViewGroup.LayoutParams)mlp);
/* 4528 */       pageIndicator.setOnPdfViewCtrlVisibilityChangeListener(new PageIndicatorLayout.OnPDFViewVisibilityChanged()
/*      */           {
/*      */             public void onPDFViewVisibilityChanged(int prevVisibility, int currVisibility) {
/* 4531 */               if (currVisibility != 0) {
/* 4532 */                 ToolManager.this.hideBuiltInPageNumber();
/*      */               }
/*      */             }
/*      */           });
/*      */ 
/*      */       
/* 4538 */       this.mPageIndicatorPopup = new PopupWindow((View)pageIndicator, -2, -2);
/*      */     } 
/*      */ 
/*      */     
/* 4542 */     int[] position = pageIndicator.calculateAutoAdjustPosition();
/* 4543 */     this.mPageIndicatorPopup.showAtLocation((View)this.mPdfViewCtrl, 8388659, position[0], position[1]);
/* 4544 */     pageIndicator.onPageChange(0, this.mPdfViewCtrl.getCurrentPage(), PDFViewCtrl.PageChangeState.END);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void hideBuiltInPageNumber() {
/* 4551 */     if (this.mPageNumberIndicatorVisible && this.mPageIndicatorPopup != null) {
/* 4552 */       this.mPageIndicatorPopup.dismiss();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasCurrentActivity() {
/* 4560 */     return (getCurrentActivity() != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCurrentActivity(@Nullable FragmentActivity activity) {
/* 4567 */     this.mCurrentActivity = new WeakReference<>(activity);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public FragmentActivity getCurrentActivity() {
/* 4577 */     FragmentActivity activity = null;
/* 4578 */     if (this.mPdfViewCtrl != null && this.mPdfViewCtrl.getContext() instanceof FragmentActivity) {
/* 4579 */       activity = (FragmentActivity)this.mPdfViewCtrl.getContext();
/* 4580 */     } else if (this.mCurrentActivity != null && this.mCurrentActivity.get() != null) {
/* 4581 */       activity = this.mCurrentActivity.get();
/*      */     } 
/* 4583 */     return activity;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   SelectionLoupe getSelectionLoupe(int type) {
/* 4588 */     if (null == this.mPdfViewCtrl) {
/* 4589 */       return null;
/*      */     }
/* 4591 */     if (null == this.mSelectionLoupe) {
/* 4592 */       int width = this.mPdfViewCtrl.getContext().getResources().getDimensionPixelSize(R.dimen.pdftron_magnifier_width);
/* 4593 */       int height = this.mPdfViewCtrl.getContext().getResources().getDimensionPixelSize(R.dimen.pdftron_magnifier_height);
/* 4594 */       this.mSelectionLoupeBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
/* 4595 */       this.mSelectionLoupeCanvas = new Canvas();
/* 4596 */       this.mSelectionLoupeCanvas.setBitmap(this.mSelectionLoupeBitmap);
/* 4597 */       this.mSelectionLoupe = new SelectionLoupe(this.mPdfViewCtrl);
/* 4598 */       this.mSelectionLoupe.setup(this.mSelectionLoupeBitmap);
/*      */     } 
/* 4600 */     if (null == this.mSelectionLoupeRound) {
/* 4601 */       int loupeSize = (int)Utils.convDp2Pix(this.mPdfViewCtrl.getContext(), 120.0F);
/* 4602 */       int loupeRadius = (int)Utils.convDp2Pix(this.mPdfViewCtrl.getContext(), 60.0F);
/* 4603 */       this.mSelectionLoupeBitmapRound = Bitmap.createBitmap(loupeSize, loupeSize, Bitmap.Config.ARGB_8888);
/* 4604 */       this.mSelectionLoupeCanvasRound = new Canvas();
/* 4605 */       this.mSelectionLoupeCanvasRound.setBitmap(this.mSelectionLoupeBitmapRound);
/* 4606 */       this.mSelectionLoupeRound = new SelectionLoupe(this.mPdfViewCtrl, 2);
/* 4607 */       this.mSelectionLoupeRound.setup(this.mSelectionLoupeBitmapRound, loupeRadius);
/*      */     } 
/* 4609 */     if (2 == type) {
/* 4610 */       return this.mSelectionLoupeRound;
/*      */     }
/* 4612 */     return this.mSelectionLoupe;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   Bitmap getSelectionLoupeBitmap(int type) {
/* 4617 */     if (2 == type) {
/* 4618 */       return this.mSelectionLoupeBitmapRound;
/*      */     }
/* 4620 */     return this.mSelectionLoupeBitmap;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   Canvas getSelectionLoupeCanvas(int type) {
/* 4625 */     if (2 == type) {
/* 4626 */       return this.mSelectionLoupeCanvasRound;
/*      */     }
/* 4628 */     return this.mSelectionLoupeCanvas;
/*      */   }
/*      */   
/*      */   boolean isFontLoaded() {
/* 4632 */     return this.mFontLoaded;
/*      */   }
/*      */   
/*      */   @NonNull
/*      */   CompositeDisposable getDisposable() {
/* 4637 */     return this.mDisposables;
/*      */   }
/*      */   
/*      */   public static interface ToolModeBase {
/*      */     int getValue();
/*      */   }
/*      */   
/*      */   public static interface ExternalAnnotationManagerListener {
/*      */     String onGenerateKey();
/*      */   }
/*      */   
/*      */   public static interface OnGenericMotionEventListener {
/*      */     void onGenericMotionEvent(MotionEvent param1MotionEvent);
/*      */     
/*      */     void onChangePointerIcon(PointerIcon param1PointerIcon);
/*      */   }
/*      */   
/*      */   public static interface AnnotationToolbarListener {
/*      */     void inkEditSelected(Annot param1Annot, int param1Int);
/*      */     
/*      */     int annotationToolbarHeight();
/*      */     
/*      */     int toolbarHeight();
/*      */     
/*      */     void openEditToolbar(ToolMode param1ToolMode);
/*      */     
/*      */     void openAnnotationToolbar(ToolMode param1ToolMode);
/*      */   }
/*      */   
/*      */   public static interface SpecialAnnotationListener {
/*      */     void defineTranslateSelected(String param1String, RectF param1RectF, Boolean param1Boolean);
/*      */   }
/*      */   
/*      */   public static interface BasicAnnotationListener {
/*      */     @Deprecated
/*      */     void onAnnotationSelected(Annot param1Annot, int param1Int);
/*      */     
/*      */     @Deprecated
/*      */     void onAnnotationUnselected();
/*      */     
/*      */     boolean onInterceptAnnotationHandling(@Nullable Annot param1Annot, Bundle param1Bundle, ToolMode param1ToolMode);
/*      */     
/*      */     boolean onInterceptDialog(AlertDialog param1AlertDialog);
/*      */   }
/*      */   
/*      */   public static interface AnnotationsSelectionListener {
/*      */     void onAnnotationsSelectionChanged(HashMap<Annot, Integer> param1HashMap);
/*      */   }
/*      */   
/*      */   public static interface AnnotationModificationListener {
/*      */     void onAnnotationsAdded(Map<Annot, Integer> param1Map);
/*      */     
/*      */     void onAnnotationsPreModify(Map<Annot, Integer> param1Map);
/*      */     
/*      */     void onAnnotationsModified(Map<Annot, Integer> param1Map, Bundle param1Bundle);
/*      */     
/*      */     void onAnnotationsPreRemove(Map<Annot, Integer> param1Map);
/*      */     
/*      */     void onAnnotationsRemoved(Map<Annot, Integer> param1Map);
/*      */     
/*      */     void onAnnotationsRemovedOnPage(int param1Int);
/*      */     
/*      */     void annotationsCouldNotBeAdded(String param1String);
/*      */   }
/*      */   
/*      */   public static interface PdfDocModificationListener {
/*      */     void onBookmarkModified();
/*      */     
/*      */     void onPagesCropped();
/*      */     
/*      */     void onPagesAdded(List<Integer> param1List);
/*      */     
/*      */     void onPagesDeleted(List<Integer> param1List);
/*      */     
/*      */     void onPagesRotated(List<Integer> param1List);
/*      */     
/*      */     void onPageMoved(int param1Int1, int param1Int2);
/*      */     
/*      */     void onPageLabelsChanged();
/*      */     
/*      */     void onAllAnnotationsRemoved();
/*      */     
/*      */     void onAnnotationAction();
/*      */   }
/*      */   
/*      */   public static interface QuickMenuListener {
/*      */     boolean onQuickMenuClicked(QuickMenuItem param1QuickMenuItem);
/*      */     
/*      */     boolean onShowQuickMenu(QuickMenu param1QuickMenu, Annot param1Annot);
/*      */     
/*      */     void onQuickMenuShown();
/*      */     
/*      */     void onQuickMenuDismissed();
/*      */   }
/*      */   
/*      */   public static interface PreToolManagerListener {
/*      */     boolean onSingleTapConfirmed(MotionEvent param1MotionEvent);
/*      */     
/*      */     boolean onMove(MotionEvent param1MotionEvent1, MotionEvent param1MotionEvent2, float param1Float1, float param1Float2);
/*      */     
/*      */     boolean onDown(MotionEvent param1MotionEvent);
/*      */     
/*      */     boolean onUp(MotionEvent param1MotionEvent, PDFViewCtrl.PriorEventMode param1PriorEventMode);
/*      */     
/*      */     boolean onScaleBegin(float param1Float1, float param1Float2);
/*      */     
/*      */     boolean onScale(float param1Float1, float param1Float2);
/*      */     
/*      */     boolean onScaleEnd(float param1Float1, float param1Float2);
/*      */     
/*      */     boolean onLongPress(MotionEvent param1MotionEvent);
/*      */     
/*      */     void onScrollChanged(int param1Int1, int param1Int2, int param1Int3, int param1Int4);
/*      */     
/*      */     boolean onDoubleTap(MotionEvent param1MotionEvent);
/*      */     
/*      */     boolean onKeyUp(int param1Int, KeyEvent param1KeyEvent);
/*      */   }
/*      */   
/*      */   public static interface OnLayoutListener {
/*      */     void onLayout(boolean param1Boolean, int param1Int1, int param1Int2, int param1Int3, int param1Int4);
/*      */   }
/*      */   
/*      */   public static interface ToolChangedListener {
/*      */     void toolChanged(Tool param1Tool1, Tool param1Tool2);
/*      */   }
/*      */   
/*      */   public static interface Tool {
/*      */     ToolModeBase getToolMode();
/*      */     
/*      */     int getCreateAnnotType();
/*      */     
/*      */     ToolModeBase getNextToolMode();
/*      */     
/*      */     void onNightModeUpdated(boolean param1Boolean);
/*      */     
/*      */     void onSetDoc();
/*      */     
/*      */     boolean onKeyUp(int param1Int, KeyEvent param1KeyEvent);
/*      */     
/*      */     boolean onDoubleTap(MotionEvent param1MotionEvent);
/*      */     
/*      */     void onDoubleTapEnd(MotionEvent param1MotionEvent);
/*      */     
/*      */     boolean onDoubleTapEvent(MotionEvent param1MotionEvent);
/*      */     
/*      */     boolean onDown(MotionEvent param1MotionEvent);
/*      */     
/*      */     boolean onPointerDown(MotionEvent param1MotionEvent);
/*      */     
/*      */     boolean onUp(MotionEvent param1MotionEvent, PDFViewCtrl.PriorEventMode param1PriorEventMode);
/*      */     
/*      */     boolean onFlingStop();
/*      */     
/*      */     void onLayout(boolean param1Boolean, int param1Int1, int param1Int2, int param1Int3, int param1Int4);
/*      */     
/*      */     boolean onLongPress(MotionEvent param1MotionEvent);
/*      */     
/*      */     boolean onScaleBegin(float param1Float1, float param1Float2);
/*      */     
/*      */     boolean onScale(float param1Float1, float param1Float2);
/*      */     
/*      */     boolean onScaleEnd(float param1Float1, float param1Float2);
/*      */     
/*      */     boolean onSingleTapConfirmed(MotionEvent param1MotionEvent);
/*      */     
/*      */     boolean onMove(MotionEvent param1MotionEvent1, MotionEvent param1MotionEvent2, float param1Float1, float param1Float2);
/*      */     
/*      */     void onScrollChanged(int param1Int1, int param1Int2, int param1Int3, int param1Int4);
/*      */     
/*      */     boolean onShowPress(MotionEvent param1MotionEvent);
/*      */     
/*      */     boolean onSingleTapUp(MotionEvent param1MotionEvent);
/*      */     
/*      */     void onClose();
/*      */     
/*      */     void onConfigurationChanged(Configuration param1Configuration);
/*      */     
/*      */     void onPageTurning(int param1Int1, int param1Int2);
/*      */     
/*      */     void onPostSingleTapConfirmed();
/*      */     
/*      */     void onCustomEvent(Object param1Object);
/*      */     
/*      */     void onDocumentDownloadEvent(PDFViewCtrl.DownloadState param1DownloadState, int param1Int1, int param1Int2, int param1Int3, String param1String);
/*      */     
/*      */     void onDraw(Canvas param1Canvas, Matrix param1Matrix);
/*      */     
/*      */     boolean onDrawEdgeEffects(Canvas param1Canvas, int param1Int1, int param1Int2);
/*      */     
/*      */     void onReleaseEdgeEffects();
/*      */     
/*      */     void onPullEdgeEffects(int param1Int, float param1Float);
/*      */     
/*      */     void onDoubleTapZoomAnimationBegin();
/*      */     
/*      */     void onDoubleTapZoomAnimationEnd();
/*      */     
/*      */     void onRenderingFinished();
/*      */     
/*      */     boolean isCreatingAnnotation();
/*      */     
/*      */     void onAnnotPainterUpdated(int param1Int, long param1Long, CurvePainter param1CurvePainter);
/*      */   }
/*      */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\ToolManager.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */