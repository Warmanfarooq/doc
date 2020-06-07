/*      */ package com.pdftron.pdf.tools;
/*      */ 
/*      */ import android.accounts.Account;
/*      */ import android.accounts.AccountManager;
/*      */ import android.app.AlertDialog;
/*      */ import android.content.Context;
/*      */ import android.content.DialogInterface;
/*      */ import android.content.SharedPreferences;
/*      */ import android.content.res.Configuration;
/*      */ import android.graphics.Bitmap;
/*      */ import android.graphics.Canvas;
/*      */ import android.graphics.Color;
/*      */ import android.graphics.Matrix;
/*      */ import android.graphics.PointF;
/*      */ import android.graphics.Rect;
/*      */ import android.graphics.RectF;
/*      */ import android.os.Bundle;
/*      */ import android.os.Handler;
/*      */ import android.os.Message;
/*      */ import android.os.Parcelable;
/*      */ import android.text.Editable;
/*      */ import android.text.TextWatcher;
/*      */ import android.util.Log;
/*      */ import android.util.Pair;
/*      */ import android.util.Patterns;
/*      */ import android.view.KeyEvent;
/*      */ import android.view.LayoutInflater;
/*      */ import android.view.MotionEvent;
/*      */ import android.view.View;
/*      */ import android.widget.CheckBox;
/*      */ import android.widget.EditText;
/*      */ import android.widget.TextView;
/*      */ import androidx.annotation.ColorInt;
/*      */ import androidx.annotation.IdRes;
/*      */ import androidx.annotation.NonNull;
/*      */ import androidx.annotation.Nullable;
/*      */ import androidx.annotation.RestrictTo;
/*      */ import androidx.annotation.StringRes;
/*      */ import androidx.core.graphics.ColorUtils;
/*      */ import androidx.core.widget.EdgeEffectCompat;
/*      */ import androidx.fragment.app.FragmentActivity;
/*      */ import androidx.lifecycle.LifecycleOwner;
/*      */ import androidx.lifecycle.Observer;
/*      */ import androidx.lifecycle.ViewModelProviders;
/*      */ import com.pdftron.common.PDFNetException;
/*      */ import com.pdftron.pdf.Action;
/*      */ import com.pdftron.pdf.ActionParameter;
/*      */ import com.pdftron.pdf.Annot;
/*      */ import com.pdftron.pdf.ColorPt;
/*      */ import com.pdftron.pdf.CurvePainter;
/*      */ import com.pdftron.pdf.Field;
/*      */ import com.pdftron.pdf.Font;
/*      */ import com.pdftron.pdf.PDFViewCtrl;
/*      */ import com.pdftron.pdf.Page;
/*      */ import com.pdftron.pdf.Rect;
/*      */ import com.pdftron.pdf.annots.ComboBoxWidget;
/*      */ import com.pdftron.pdf.annots.FreeText;
/*      */ import com.pdftron.pdf.annots.Link;
/*      */ import com.pdftron.pdf.annots.ListBoxWidget;
/*      */ import com.pdftron.pdf.annots.Markup;
/*      */ import com.pdftron.pdf.annots.Widget;
/*      */ import com.pdftron.pdf.config.ToolStyleConfig;
/*      */ import com.pdftron.pdf.dialog.widgetchoice.ChoiceDialogFragment;
/*      */ import com.pdftron.pdf.dialog.widgetchoice.ChoiceResult;
/*      */ import com.pdftron.pdf.dialog.widgetchoice.ChoiceViewModel;
/*      */ import com.pdftron.pdf.model.AnnotStyle;
/*      */ import com.pdftron.pdf.utils.ActionUtils;
/*      */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*      */ import com.pdftron.pdf.utils.AnnotUtils;
/*      */ import com.pdftron.pdf.utils.AnnotationClipboardHelper;
/*      */ import com.pdftron.pdf.utils.Event;
/*      */ import com.pdftron.pdf.utils.PdfViewCtrlSettingsManager;
/*      */ import com.pdftron.pdf.utils.ShortcutHelper;
/*      */ import com.pdftron.pdf.utils.Utils;
/*      */ import com.pdftron.pdf.utils.ViewerUtils;
/*      */ import com.pdftron.pdf.widget.AnnotView;
/*      */ import com.pdftron.pdf.widget.RotateHandleView;
/*      */ import com.pdftron.sdf.Doc;
/*      */ import io.reactivex.android.schedulers.AndroidSchedulers;
/*      */ import io.reactivex.disposables.CompositeDisposable;
/*      */ import io.reactivex.functions.Consumer;
/*      */ import io.reactivex.schedulers.Schedulers;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.HashMap;
/*      */ import java.util.Map;
/*      */ import java.util.regex.Pattern;
/*      */ import org.json.JSONArray;
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
/*      */ public abstract class Tool
/*      */   implements ToolManager.Tool
/*      */ {
/*  108 */   private static final String TAG = Tool.class.getName();
/*      */   
/*      */   protected static boolean sDebug;
/*      */   
/*      */   public static final String PDFTRON_ID = "pdftron";
/*      */   
/*      */   public static final String PDFTRON_THICKNESS = "pdftron_thickness";
/*      */   
/*      */   public static final String KEYS = "PDFTRON_KEYS";
/*      */   
/*      */   public static final String METHOD_FROM = "METHOD_FROM";
/*      */   
/*      */   public static final String PAGE_NUMBER = "PAGE_NUMBER";
/*      */   
/*      */   public static final String IS_LINK = "IS_LINK";
/*      */   
/*      */   public static final String LINK_URL = "LINK_URL";
/*      */   
/*      */   public static final String LINK_RECTF = "LINK_RECTF";
/*      */   
/*      */   private static final String PREFS_FILE_NAME = "com_pdftron_pdfnet_pdfviewctrl_prefs_file";
/*      */   public static final String PREF_ANNOTATION_CREATION_LINE = "annotation_creation";
/*      */   public static final String PREF_ANNOTATION_CREATION_ARROW = "annotation_creation_arrow";
/*      */   public static final String PREF_ANNOTATION_CREATION_POLYLINE = "annotation_creation_polyline";
/*      */   public static final String PREF_ANNOTATION_CREATION_RECTANGLE = "annotation_creation_rectangle";
/*      */   public static final String PREF_ANNOTATION_CREATION_OVAL = "annotation_creation_oval";
/*      */   public static final String PREF_ANNOTATION_CREATION_POLYGON = "annotation_creation_polygon";
/*      */   public static final String PREF_ANNOTATION_CREATION_CLOUD = "annotation_creation_cloud";
/*      */   public static final String PREF_ANNOTATION_CREATION_HIGHLIGHT = "annotation_creation_highlight";
/*      */   public static final String PREF_ANNOTATION_CREATION_LINK = "annotation_creation_link";
/*      */   public static final String PREF_ANNOTATION_CREATION_UNDERLINE = "annotation_creation_text_markup";
/*      */   public static final String PREF_ANNOTATION_CREATION_STRIKEOUT = "annotation_creation_strikeout";
/*      */   public static final String PREF_ANNOTATION_CREATION_SQUIGGLY = "annotation_creation_squiggly";
/*      */   public static final String PREF_ANNOTATION_CREATION_FREETEXT = "annotation_creation_freetext";
/*      */   public static final String PREF_ANNOTATION_CREATION_FREEHAND = "annotation_creation_freehand";
/*      */   public static final String PREF_ANNOTATION_CREATION_NOTE = "annotation_creation_note";
/*      */   public static final String PREF_ANNOTATION_CREATION_ERASER = "annotation_creation_eraser";
/*      */   public static final String PREF_ANNOTATION_CREATION_SIGNATURE = "annotation_creation_signature";
/*      */   public static final String PREF_ANNOTATION_CREATION_FREE_HIGHLIGHTER = "annotation_creation_free_highlighter";
/*      */   public static final String PREF_ANNOTATION_CREATION_COLOR = "_color";
/*      */   public static final String PREF_ANNOTATION_CREATION_TEXT_COLOR = "_text_color";
/*      */   public static final String PREF_ANNOTATION_CREATION_FILL_COLOR = "_fill_color";
/*      */   public static final String PREF_ANNOTATION_CREATION_OPACITY = "_opacity";
/*      */   public static final String PREF_ANNOTATION_CREATION_THICKNESS = "_thickness";
/*      */   public static final String PREF_ANNOTATION_CREATION_TEXT_SIZE = "_text_size";
/*      */   public static final String PREF_ANNOTATION_CREATION_ICON = "_icon";
/*      */   public static final String PREF_ANNOTATION_CREATION_FONT = "_font";
/*      */   public static final String ANNOTATION_NOTE_ICON_FILE_PREFIX = "annotation_note_icon_";
/*      */   public static final String ANNOTATION_NOTE_ICON_FILE_POSTFIX_FILL = "_fill";
/*      */   public static final String ANNOTATION_NOTE_ICON_FILE_POSTFIX_OUTLINE = "_outline";
/*      */   public static final String ANNOTATION_TOOLBAR_SIGNATURE_STATE = "annotation_toolbar_signature_state";
/*      */   public static final String ANNOTATION_FREE_TEXT_FONTS = "annotation_property_free_text_fonts_list";
/*      */   public static final String ANNOTATION_FREE_TEXT_JSON_FONT_FILE_PATH = "filepath";
/*      */   public static final String ANNOTATION_FREE_TEXT_JSON_FONT_DISPLAY_NAME = "display name";
/*      */   public static final String ANNOTATION_FREE_TEXT_JSON_FONT_DISPLAY_IN_LIST = "display font";
/*      */   public static final String ANNOTATION_FREE_TEXT_JSON_FONT_PDFTRON_NAME = "pdftron name";
/*      */   public static final String ANNOTATION_FREE_TEXT_JSON_FONT_NAME = "font name";
/*      */   public static final String ANNOTATION_FREE_TEXT_JSON_FONT = "fonts";
/*      */   public static final String STAMP_SHOW_FLATTEN_WARNING = "stamp_show_flatten_warning";
/*  167 */   public static final String[] ANNOTATION_FREE_TEXT_WHITELIST_FONTS = new String[] { "Gill", "Calibri", "Arial", "SimSun", "Curlz", "Times", "Lucida", "Rockwell", "Old English", "Abadi", "Twentieth Century", "News Gothic", "Bodoni", "Candara", "PMingLiU", "Palace Script", "Helvetica", "Courier", "Roboto", "Comic", "Droid", "Georgia", "MotoyaLManu", "NanumGothic", "Kaiti", "Miaowu", "ShaoNV", "Rosemary" };
/*      */   
/*      */   public static final int ANNOTATION_FREE_TEXT_PREFERENCE_INLINE = 1;
/*      */   
/*      */   public static final int ANNOTATION_FREE_TEXT_PREFERENCE_DIALOG = 2;
/*      */   
/*      */   public static final String ANNOTATION_FREE_TEXT_PREFERENCE_EDITING = "annotation_free_text_preference_editing";
/*      */   
/*      */   public static final int ANNOTATION_FREE_TEXT_PREFERENCE_EDITING_DEFAULT = 1;
/*      */   
/*      */   public static final String FORM_FIELD_SYMBOL_CHECKBOX = "4";
/*      */   
/*      */   public static final String FORM_FIELD_SYMBOL_CIRCLE = "l";
/*      */   
/*      */   public static final String FORM_FIELD_SYMBOL_CROSS = "8";
/*      */   
/*      */   public static final String FORM_FIELD_SYMBOL_DIAMOND = "u";
/*      */   
/*      */   public static final String FORM_FIELD_SYMBOL_SQUARE = "n";
/*      */   
/*      */   public static final String FORM_FIELD_SYMBOL_STAR = "H";
/*      */   
/*      */   public static final String PREF_TRANSLATION_SOURCE_LANGUAGE_CODE_KEY = "translation_source_language_code";
/*      */   
/*      */   public static final String PREF_TRANSLATION_TARGET_LANGUAGE_CODE_KEY = "translation_target_language_code";
/*      */   
/*      */   public static final String LAST_DEVICE_LOCALE_LANGUAGE = "last_device_locale_language";
/*      */   
/*      */   public static final String PREF_TRANSLATION_SOURCE_LANGUAGE_CODE_DEFAULT = "en";
/*      */   
/*      */   public static final String PREF_TRANSLATION_TARGET_LANGUAGE_CODE_DEFAULT = "fr";
/*      */   
/*      */   public static final int ANNOT_PERMISSION_SELECTION = 0;
/*      */   
/*      */   public static final int ANNOT_PERMISSION_MENU = 1;
/*      */   
/*      */   public static final int QM_MAX_ROW_SIZE = 4;
/*      */   
/*      */   static final int sRichContentSpaceDelay = 100;
/*      */   
/*      */   protected PDFViewCtrl mPdfViewCtrl;
/*      */   
/*      */   protected ToolManager.ToolModeBase mNextToolMode;
/*      */   
/*      */   protected ToolManager.ToolModeBase mCurrentDefaultToolMode;
/*      */   
/*      */   protected Annot mAnnot;
/*      */   
/*      */   protected int mAnnotPageNum;
/*      */   
/*      */   protected int mSelectPageNum;
/*      */   
/*      */   protected RectF mAnnotBBox;
/*      */   
/*      */   protected QuickMenu mQuickMenu;
/*      */   
/*      */   protected String[] mMruMenuItems;
/*      */   
/*      */   protected String[] mOverflowMenuItems;
/*      */   
/*      */   protected boolean mJustSwitchedFromAnotherTool;
/*      */   protected boolean mAvoidLongPressAttempt;
/*      */   protected float mPageNumPosAdjust;
/*      */   protected RectF mTempPageDrawingRectF;
/*      */   protected boolean mForceSameNextToolMode;
/*      */   protected boolean mAnnotPushedBack;
/*      */   protected boolean mAllowTwoFingerScroll;
/*      */   protected boolean mAllowOneFingerScrollWithStylus;
/*      */   protected boolean mUpFromCalloutCreate;
/*      */   protected ArrayList<Annot> mGroupAnnots;
/*      */   protected boolean mIsStylus;
/*      */   protected boolean mStylusUsed;
/*      */   protected boolean mAllowZoom;
/*      */   protected boolean mHasMenuPermission = true;
/*      */   protected boolean mHasSelectionPermission = true;
/*      */   private EdgeEffectCompat mEdgeEffectLeft;
/*      */   private EdgeEffectCompat mEdgeEffectRight;
/*      */   private Markup mMarkupToAuthor;
/*      */   private boolean mPageNumberIndicatorVisible;
/*      */   protected AnnotView mAnnotView;
/*      */   protected RotateHandleView mRotateHandle;
/*      */   protected AnnotStyle mAnnotStyle;
/*      */   private boolean mSnappingEnabled;
/*  250 */   private PageNumberRemovalHandler mPageNumberRemovalHandler = new PageNumberRemovalHandler(this);
/*      */   
/*  252 */   protected CompositeDisposable mBitmapDisposable = new CompositeDisposable();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Tool(@NonNull PDFViewCtrl ctrl) {
/*  258 */     this.mPdfViewCtrl = ctrl;
/*  259 */     this.mNextToolMode = ToolManager.ToolMode.PAN;
/*  260 */     this.mCurrentDefaultToolMode = ToolManager.ToolMode.PAN;
/*  261 */     this.mAnnot = null;
/*  262 */     this.mAnnotBBox = new RectF();
/*  263 */     this.mJustSwitchedFromAnotherTool = false;
/*  264 */     this.mForceSameNextToolMode = false;
/*  265 */     this.mAvoidLongPressAttempt = false;
/*  266 */     this.mAnnotPushedBack = false;
/*  267 */     this.mPageNumPosAdjust = 0.0F;
/*  268 */     this.mTempPageDrawingRectF = new RectF();
/*      */     
/*  270 */     this.mPageNumberIndicatorVisible = true;
/*      */     
/*  272 */     this.mAllowTwoFingerScroll = false;
/*  273 */     this.mAllowOneFingerScrollWithStylus = false;
/*  274 */     this.mAllowZoom = true;
/*      */ 
/*      */ 
/*      */     
/*  278 */     this.mPdfViewCtrl.setBuiltInPageSlidingState(false);
/*      */ 
/*      */     
/*  281 */     this.mEdgeEffectLeft = new EdgeEffectCompat(ctrl.getContext());
/*  282 */     this.mEdgeEffectRight = new EdgeEffectCompat(ctrl.getContext());
/*      */ 
/*      */     
/*  285 */     int childCount = this.mPdfViewCtrl.getChildCount();
/*      */     
/*  287 */     for (int i = 0; i < this.mPdfViewCtrl.getChildCount(); i++) {
/*  288 */       if (this.mPdfViewCtrl.getChildAt(i) instanceof QuickMenu) {
/*  289 */         this.mQuickMenu = (QuickMenu)this.mPdfViewCtrl.getChildAt(i);
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*  294 */     boolean stylusAsPen = ((ToolManager)this.mPdfViewCtrl.getToolManager()).isStylusAsPen();
/*  295 */     this.mPdfViewCtrl.setStylusScaleEnabled(!stylusAsPen);
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
/*      */   public final ToolManager.ToolModeBase getNextToolMode() {
/*  311 */     return this.mNextToolMode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isCreatingAnnotation() {
/*  319 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEditAnnotTool() {
/*  328 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasMenuEntry(@IdRes int menuId) {
/*  337 */     if (!isQuickMenuShown()) {
/*  338 */       return false;
/*      */     }
/*  340 */     if (this.mQuickMenu.getMenu().findItem(menuId) != null) {
/*  341 */       return true;
/*      */     }
/*  343 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void pasteAnnot(PointF targetPoint) {
/*  352 */     if (this.mPdfViewCtrl == null || !AnnotationClipboardHelper.isItemCopied(this.mPdfViewCtrl.getContext())) {
/*      */       return;
/*      */     }
/*      */     
/*  356 */     int pageNumber = this.mPdfViewCtrl.getPageNumberFromScreenPt(targetPoint.x, targetPoint.y);
/*  357 */     if (pageNumber == -1) {
/*  358 */       pageNumber = this.mPdfViewCtrl.getCurrentPage();
/*      */     }
/*      */     
/*  361 */     if (Utils.isImageCopied(this.mPdfViewCtrl.getContext())) {
/*      */       try {
/*  363 */         ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/*  364 */         ToolManager.Tool tool = toolManager.createTool(ToolManager.ToolMode.STAMPER, this);
/*  365 */         toolManager.setTool(tool);
/*  366 */         ((Stamper)tool).addStampFromClipboard(targetPoint);
/*  367 */       } catch (Exception ex) {
/*  368 */         AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*      */       } 
/*  370 */     } else if (AnnotationClipboardHelper.isAnnotCopied()) {
/*  371 */       AnnotationClipboardHelper.pasteAnnot(this.mPdfViewCtrl.getContext(), this.mPdfViewCtrl, pageNumber, targetPoint, null);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ToolManager.ToolModeBase safeSetNextToolMode(ToolManager.ToolModeBase toolMode) {
/*  382 */     if (null != toolMode && toolMode instanceof ToolManager.ToolMode) {
/*  383 */       boolean disabled = ((ToolManager)this.mPdfViewCtrl.getToolManager()).isToolModeDisabled((ToolManager.ToolMode)toolMode);
/*  384 */       if (disabled) {
/*  385 */         return ToolManager.ToolMode.PAN;
/*      */       }
/*      */     } 
/*  388 */     return toolMode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected QuickMenu createQuickMenu() {
/*  397 */     QuickMenu quickMenu = new QuickMenu(this.mPdfViewCtrl, this.mHasMenuPermission, getToolMode());
/*  398 */     return quickMenu;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void customizeQuickMenuItems(QuickMenu quickMenu) {
/*  407 */     if (null == this.mAnnot) {
/*      */       return;
/*      */     }
/*  410 */     boolean shouldUnlockRead = false;
/*      */ 
/*      */     
/*      */     try {
/*  414 */       this.mPdfViewCtrl.docLockRead();
/*  415 */       shouldUnlockRead = true;
/*      */ 
/*      */       
/*  418 */       QuickMenuItem appearanceMenuItem = quickMenu.findMenuItem(R.id.qm_appearance);
/*  419 */       if (appearanceMenuItem != null) {
/*      */         
/*  421 */         float opacity = 1.0F;
/*  422 */         ColorPt colorPt = this.mAnnot.getColorAsRGB();
/*  423 */         int color = Utils.colorPt2color(colorPt);
/*  424 */         if (this.mAnnot.getType() == 2) {
/*  425 */           FreeText freeText = new FreeText(this.mAnnot);
/*  426 */           if (freeText.getTextColorCompNum() == 3) {
/*  427 */             ColorPt fillColorPt = freeText.getTextColor();
/*  428 */             color = Utils.colorPt2color(fillColorPt);
/*      */           } 
/*      */           
/*  431 */           String rawHTML = freeText.getCustomData(AnnotUtils.KEY_RawRichContent);
/*  432 */           if (!Utils.isNullOrEmpty(rawHTML)) {
/*  433 */             appearanceMenuItem.setVisible(false);
/*      */           }
/*      */         } 
/*  436 */         if (this.mAnnot.isMarkup()) {
/*      */           
/*  438 */           Markup m = new Markup(this.mAnnot);
/*  439 */           if (m.getInteriorColorCompNum() == 3) {
/*  440 */             ColorPt fillColorPt = m.getInteriorColor();
/*  441 */             int fillColor = Utils.colorPt2color(fillColorPt);
/*  442 */             if (fillColor != 0) {
/*  443 */               color = fillColor;
/*      */             }
/*      */           } 
/*      */           
/*  447 */           opacity = (float)m.getOpacity();
/*      */         } 
/*  449 */         int background = Utils.getBackgroundColor(this.mPdfViewCtrl.getContext());
/*  450 */         int foreground = ColorUtils.compositeColors(Color.argb((int)(opacity * 255.0F), Color.red(color), Color.green(color), Color.blue(color)), background);
/*      */         
/*  452 */         boolean isColorSpaceClose = Utils.isTwoColorSimilar(background, foreground, 12.0F);
/*      */         
/*  454 */         if (!isColorSpaceClose) {
/*  455 */           appearanceMenuItem.setColor(color);
/*  456 */           appearanceMenuItem.setOpacity(opacity);
/*      */         } 
/*      */       } 
/*      */       
/*  460 */       QuickMenuItem noteItem = quickMenu.findMenuItem(R.id.qm_note);
/*  461 */       if (noteItem != null && this.mAnnot
/*  462 */         .isMarkup() && this.mAnnot
/*  463 */         .getType() != 2) {
/*  464 */         String contents = this.mAnnot.getContents();
/*  465 */         if (Utils.isNullOrEmpty(contents)) {
/*  466 */           if (noteItem.getIcon() != null) {
/*  467 */             noteItem.setIcon(R.drawable.ic_annotation_sticky_note_black_24dp);
/*      */           }
/*  469 */           noteItem.setTitle(R.string.tools_qm_add_note);
/*      */         } else {
/*  471 */           if (noteItem.getIcon() != null) {
/*  472 */             noteItem.setIcon(R.drawable.ic_chat_black_24dp);
/*      */           }
/*  474 */           noteItem.setTitle(R.string.tools_qm_view_note);
/*      */         } 
/*      */       } 
/*      */       
/*  478 */       QuickMenuItem typeItem = quickMenu.findMenuItem(R.id.qm_type);
/*  479 */       if (typeItem != null && typeItem.hasSubMenu()) {
/*  480 */         QuickMenuBuilder subMenu = (QuickMenuBuilder)typeItem.getSubMenu();
/*  481 */         if (this.mAnnot != null) {
/*  482 */           if (this.mAnnot.getType() == 8) {
/*  483 */             subMenu.removeItem(R.id.qm_highlight);
/*  484 */           } else if (this.mAnnot.getType() == 11) {
/*  485 */             subMenu.removeItem(R.id.qm_strikeout);
/*  486 */           } else if (this.mAnnot.getType() == 9) {
/*  487 */             subMenu.removeItem(R.id.qm_underline);
/*  488 */           } else if (this.mAnnot.getType() == 10) {
/*  489 */             subMenu.removeItem(R.id.qm_squiggly);
/*      */           } 
/*      */         }
/*      */       } 
/*      */       
/*  494 */       QuickMenuItem editItem = quickMenu.findMenuItem(R.id.qm_edit);
/*  495 */       if (editItem != null) {
/*  496 */         if (this.mAnnot.getType() == 14) {
/*  497 */           editItem.setVisible(((ToolManager)this.mPdfViewCtrl.getToolManager()).editInkAnnots());
/*  498 */         } else if (this.mAnnot.getType() == 19) {
/*  499 */           Field field = (new Widget(this.mAnnot)).getField();
/*  500 */           if (field.isValid()) {
/*  501 */             boolean readOnly = field.getFlag(0);
/*  502 */             editItem.setVisible(!readOnly);
/*      */           } 
/*      */         } 
/*      */       }
/*  506 */     } catch (PDFNetException e) {
/*  507 */       e.printStackTrace();
/*  508 */       AnalyticsHandlerAdapter.getInstance().sendException((Exception)e, "failed in AnnotEdit.createQuickMenu");
/*      */     } finally {
/*  510 */       if (shouldUnlockRead) {
/*  511 */         this.mPdfViewCtrl.docUnlockRead();
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
/*      */ 
/*      */   
/*      */   protected boolean hasPermission(Annot annot, int kind) {
/*  526 */     boolean hasPermission = true;
/*  527 */     if (this.mPdfViewCtrl.getToolManager() instanceof ToolManager) {
/*  528 */       boolean shouldUnlockRead = false;
/*  529 */       if (((ToolManager)this.mPdfViewCtrl.getToolManager()).getAnnotManager() != null) {
/*      */         try {
/*  531 */           this.mPdfViewCtrl.docLockRead();
/*  532 */           shouldUnlockRead = true;
/*  533 */           String currentAuthor = ((ToolManager)this.mPdfViewCtrl.getToolManager()).getAuthorId();
/*  534 */           Markup mp = new Markup(annot);
/*  535 */           if (mp.isValid()) {
/*  536 */             String userId = (mp.getTitle() != null) ? mp.getTitle() : null;
/*  537 */             if (null != userId && null != currentAuthor) {
/*  538 */               hasPermission = currentAuthor.equals(userId);
/*      */             }
/*      */           } 
/*  541 */         } catch (Exception e) {
/*  542 */           hasPermission = true;
/*  543 */           AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */         } finally {
/*  545 */           if (shouldUnlockRead) {
/*  546 */             this.mPdfViewCtrl.docUnlockRead();
/*      */           }
/*      */         } 
/*  549 */       } else if (((ToolManager)this.mPdfViewCtrl.getToolManager()).isAnnotPermissionCheckEnabled()) {
/*      */         try {
/*  551 */           this.mPdfViewCtrl.docLockRead();
/*  552 */           shouldUnlockRead = true;
/*  553 */           if (kind == 0) {
/*  554 */             if (annot.getFlag(6) || annot.getFlag(7)) {
/*  555 */               hasPermission = false;
/*      */             }
/*  557 */           } else if (kind == 1 && 
/*  558 */             annot.getFlag(6)) {
/*  559 */             hasPermission = false;
/*      */           }
/*      */         
/*  562 */         } catch (Exception e) {
/*  563 */           hasPermission = true;
/*  564 */           AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */         } finally {
/*  566 */           if (shouldUnlockRead) {
/*  567 */             this.mPdfViewCtrl.docUnlockRead();
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*  572 */     return hasPermission;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getModeAHLabel() {
/*  579 */     return 104;
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
/*      */   public void executeAction(ActionParameter actionParam) {
/*  593 */     ActionUtils.getInstance().executeAction(actionParam, this.mPdfViewCtrl);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDocumentDownloadEvent(PDFViewCtrl.DownloadState state, int page_num, int page_downloaded, int page_count, String message) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean onGenericMotionEvent(MotionEvent event) {
/*  610 */     if (this.mPdfViewCtrl == null) {
/*  611 */       return false;
/*      */     }
/*      */     
/*  614 */     if (ShortcutHelper.isZoomIn(event)) {
/*  615 */       this.mPdfViewCtrl.setZoom((int)event.getX(), (int)event.getY(), this.mPdfViewCtrl
/*  616 */           .getZoom() * 1.5D, true, true);
/*  617 */       return true;
/*  618 */     }  if (ShortcutHelper.isZoomOut(event)) {
/*  619 */       this.mPdfViewCtrl.setZoom((int)event.getX(), (int)event.getY(), this.mPdfViewCtrl
/*  620 */           .getZoom() / 1.5D, true, true);
/*  621 */       return true;
/*  622 */     }  if (ShortcutHelper.isScroll(event)) {
/*  623 */       int dx = (int)(event.getAxisValue(10) * 100.0F);
/*  624 */       int dy = (int)(event.getAxisValue(9) * 100.0F);
/*  625 */       if (dx != 0 || dy != 0) {
/*  626 */         this.mPdfViewCtrl.scrollBy(dx, -dy);
/*      */       }
/*  628 */       return true;
/*      */     } 
/*      */     
/*  631 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onKeyUp(int keyCode, KeyEvent event) {
/*  639 */     if (this.mPdfViewCtrl == null) {
/*  640 */       return false;
/*      */     }
/*      */     
/*  643 */     if (ShortcutHelper.isPaste(keyCode, event)) {
/*  644 */       pasteAnnot(this.mPdfViewCtrl.getCurrentMousePosition());
/*  645 */       return true;
/*      */     } 
/*      */     
/*  648 */     if (isQuickMenuShown() && ShortcutHelper.isCloseMenu(keyCode, event)) {
/*  649 */       closeQuickMenu();
/*  650 */       unsetAnnot();
/*  651 */       this.mNextToolMode = this.mCurrentDefaultToolMode;
/*  652 */       this.mPdfViewCtrl.invalidate();
/*  653 */       return true;
/*      */     } 
/*      */     
/*  656 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onDown(MotionEvent e) {
/*  664 */     this.mAllowZoom = (this.mPdfViewCtrl.isZoomingInAddingAnnotationEnabled() || !isCreatingAnnotation());
/*  665 */     this.mPdfViewCtrl.setZoomEnabled(this.mAllowZoom);
/*  666 */     closeQuickMenu();
/*      */     
/*  668 */     if (isCreatingAnnotation()) {
/*      */       
/*  670 */       if (this.mIsStylus && e.getPointerCount() == 1 && e.getToolType(0) != 2) {
/*  671 */         this.mIsStylus = false;
/*  672 */       } else if (!this.mIsStylus && e.getPointerCount() == 1 && e.getToolType(0) == 2) {
/*  673 */         this.mIsStylus = true;
/*      */       } 
/*      */       
/*  676 */       if (!this.mStylusUsed) {
/*  677 */         this.mStylusUsed = this.mIsStylus;
/*      */       }
/*      */     } 
/*  680 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onPointerDown(MotionEvent e) {
/*  688 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onUp(MotionEvent e, PDFViewCtrl.PriorEventMode priorEventMode) {
/*  696 */     this.mPageNumberRemovalHandler.sendEmptyMessageDelayed(1, 3000L);
/*  697 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onFlingStop() {
/*  705 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onMove(MotionEvent e1, MotionEvent e2, float x_dist, float y_dist) {
/*  713 */     if (ShortcutHelper.isZoomInOut(e2)) {
/*  714 */       if (e1.getY() < e2.getY()) {
/*  715 */         this.mPdfViewCtrl.setZoom((int)e2.getX(), (int)e2.getY(), this.mPdfViewCtrl
/*  716 */             .getZoom() * 1.5D, true, true);
/*  717 */         return true;
/*  718 */       }  if (e1.getY() > e2.getY()) {
/*  719 */         this.mPdfViewCtrl.setZoom((int)e2.getX(), (int)e2.getY(), this.mPdfViewCtrl
/*  720 */             .getZoom() / 1.5D, true, true);
/*  721 */         return true;
/*      */       } 
/*      */     } 
/*      */     
/*  725 */     if (isCreatingAnnotation()) {
/*  726 */       if (e1.getPointerCount() == 2 || e2.getPointerCount() == 2) {
/*  727 */         this.mAllowTwoFingerScroll = true;
/*      */       }
/*      */ 
/*      */       
/*  731 */       this.mAllowOneFingerScrollWithStylus = (this.mStylusUsed && e2.getToolType(0) != 2);
/*      */     } else {
/*  733 */       this.mAllowTwoFingerScroll = false;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  739 */     if (getToolMode() == ToolManager.ToolMode.PAN || 
/*  740 */       getToolMode() == ToolManager.ToolMode.TEXT_SELECT || 
/*  741 */       getToolMode() == ToolManager.ToolMode.TEXT_HIGHLIGHTER) {
/*  742 */       this.mPdfViewCtrl.setBuiltInPageSlidingState(true);
/*      */     }
/*  744 */     else if (this.mAllowTwoFingerScroll || this.mAllowOneFingerScrollWithStylus) {
/*  745 */       this.mPdfViewCtrl.setBuiltInPageSlidingState(true);
/*      */     } else {
/*  747 */       this.mPdfViewCtrl.setBuiltInPageSlidingState(false);
/*      */     } 
/*      */ 
/*      */     
/*  751 */     showTransientPageNumber();
/*  752 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onScrollChanged(int l, int t, int oldl, int oldt) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onPageTurning(int old_page, int cur_page) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onSingleTapConfirmed(MotionEvent e) {
/*  774 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onPostSingleTapConfirmed() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onSingleTapUp(MotionEvent e) {
/*  789 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onDoubleTap(MotionEvent e) {
/*      */     PDFViewCtrl.PageViewMode refMode;
/*  797 */     showTransientPageNumber();
/*      */ 
/*      */     
/*  800 */     boolean customize = true;
/*  801 */     if (!customize)
/*      */     {
/*  803 */       return false;
/*      */     }
/*  805 */     if (isCreatingAnnotation())
/*      */     {
/*  807 */       if (this.mStylusUsed && this.mIsStylus) {
/*  808 */         return true;
/*      */       }
/*      */     }
/*  811 */     ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/*  812 */     if (!toolManager.isDoubleTapToZoom())
/*      */     {
/*  814 */       return true;
/*      */     }
/*  816 */     boolean animate = true;
/*      */     
/*  818 */     int x = (int)(e.getX() + 0.5D);
/*  819 */     int y = (int)(e.getY() + 0.5D);
/*      */     
/*  821 */     if (this.mPdfViewCtrl.isMaintainZoomEnabled()) {
/*  822 */       refMode = this.mPdfViewCtrl.getPreferredViewMode();
/*      */     } else {
/*  824 */       refMode = this.mPdfViewCtrl.getPageRefViewMode();
/*      */     } 
/*  826 */     if (!ViewerUtils.isViewerZoomed(this.mPdfViewCtrl)) {
/*      */       
/*  828 */       boolean result = this.mPdfViewCtrl.smartZoom(x, y, animate);
/*  829 */       if (!result) {
/*      */         
/*  831 */         boolean use_snapshot = true;
/*  832 */         this.mPdfViewCtrl.setZoom(x, y, this.mPdfViewCtrl.getZoom() * 2.5D, use_snapshot, animate);
/*      */       } 
/*      */     } else {
/*  835 */       this.mPdfViewCtrl.setPageViewMode(refMode, x, y, animate);
/*      */     } 
/*  837 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDoubleTapEnd(MotionEvent e) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onDoubleTapEvent(MotionEvent e) {
/*  853 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onLayout(boolean changed, int l, int t, int r, int b) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onLongPress(MotionEvent e) {
/*  868 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onScaleBegin(float x, float y) {
/*  877 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onScale(float x, float y) {
/*  886 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onScaleEnd(float x, float y) {
/*  895 */     showTransientPageNumber();
/*  896 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onConfigurationChanged(Configuration newConfig) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onShowPress(MotionEvent e) {
/*  911 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onClose() {
/*  919 */     this.mPageNumberRemovalHandler.removeCallbacksAndMessages(null);
/*  920 */     if (this.mPdfViewCtrl.hasSelection()) {
/*  921 */       this.mPdfViewCtrl.clearSelection();
/*      */     }
/*  923 */     closeQuickMenu();
/*  924 */     this.mBitmapDisposable.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onCustomEvent(Object o) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onSetDoc() {
/*  939 */     boolean shouldUnlock = false;
/*  940 */     boolean hasExecutionChanges = false;
/*      */     try {
/*  942 */       Action open_action = this.mPdfViewCtrl.getDoc().getOpenAction();
/*  943 */       if (open_action.isValid() && open_action.getType() == 13) {
/*  944 */         this.mPdfViewCtrl.docLock(true);
/*  945 */         shouldUnlock = true;
/*  946 */         ActionParameter action_param = new ActionParameter(open_action);
/*  947 */         executeAction(action_param);
/*  948 */         hasExecutionChanges = this.mPdfViewCtrl.getDoc().hasChangesSinceSnapshot();
/*      */       } 
/*  950 */     } catch (Exception e) {
/*  951 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } finally {
/*  953 */       if (shouldUnlock) {
/*  954 */         this.mPdfViewCtrl.docUnlock();
/*  955 */         if (hasExecutionChanges) {
/*  956 */           raiseAnnotationActionEvent();
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onCreate() {
/*  966 */     if (this.mPageNumberIndicatorVisible) {
/*  967 */       showTransientPageNumber();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDraw(Canvas canvas, Matrix tfm) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setJustCreatedFromAnotherTool() {
/*  983 */     this.mJustSwitchedFromAnotherTool = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearTargetPoint() {
/*  990 */     if (getToolMode() == ToolManager.ToolMode.STAMPER) {
/*      */       try {
/*  992 */         ((Stamper)this).clearTargetPoint();
/*  993 */       } catch (Exception e) {
/*  994 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */       } 
/*  996 */     } else if (getToolMode() == ToolManager.ToolMode.FILE_ATTACHMENT_CREATE) {
/*      */       try {
/*  998 */         ((FileAttachmentCreate)this).clearTargetPoint();
/*  999 */       } catch (Exception e) {
/* 1000 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void closeQuickMenu() {
/* 1009 */     if (this.mQuickMenu != null && this.mQuickMenu.isShowing()) {
/* 1010 */       this.mQuickMenu.dismiss();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onDrawEdgeEffects(Canvas canvas, int width, int verticalOffset) {
/* 1019 */     boolean needsInvalidate = false;
/*      */     
/* 1021 */     if (!this.mEdgeEffectLeft.isFinished()) {
/* 1022 */       canvas.save();
/*      */       try {
/* 1024 */         canvas.translate(0.0F, (canvas.getHeight() + verticalOffset));
/* 1025 */         canvas.rotate(-90.0F, 0.0F, 0.0F);
/* 1026 */         this.mEdgeEffectLeft.setSize(canvas.getHeight(), canvas.getWidth());
/* 1027 */         if (this.mEdgeEffectLeft.draw(canvas)) {
/* 1028 */           needsInvalidate = true;
/*      */         }
/*      */       } finally {
/* 1031 */         canvas.restore();
/*      */       } 
/*      */     } 
/*      */     
/* 1035 */     if (!this.mEdgeEffectRight.isFinished()) {
/* 1036 */       canvas.save();
/*      */       try {
/* 1038 */         canvas.translate(width, verticalOffset);
/* 1039 */         canvas.rotate(90.0F, 0.0F, 0.0F);
/* 1040 */         this.mEdgeEffectRight.setSize(canvas.getHeight(), canvas.getWidth());
/* 1041 */         if (this.mEdgeEffectRight.draw(canvas)) {
/* 1042 */           needsInvalidate = true;
/*      */         }
/*      */       } finally {
/* 1045 */         canvas.restore();
/*      */       } 
/*      */     } 
/* 1048 */     return needsInvalidate;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onReleaseEdgeEffects() {
/* 1056 */     this.mEdgeEffectLeft.onRelease();
/* 1057 */     this.mEdgeEffectRight.onRelease();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onPullEdgeEffects(int whichEdge, float deltaDistance) {
/* 1065 */     if (whichEdge < 0) {
/*      */       
/* 1067 */       this.mEdgeEffectLeft.onPull(deltaDistance);
/* 1068 */     } else if (whichEdge > 0) {
/*      */       
/* 1070 */       this.mEdgeEffectRight.onPull(deltaDistance);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDoubleTapZoomAnimationBegin() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDoubleTapZoomAnimationEnd() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onNightModeUpdated(boolean isNightMode) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onRenderingFinished() {
/* 1100 */     if (this.mAnnotView != null && this.mAnnotView.isDelayViewRemoval()) {
/* 1101 */       this.mPdfViewCtrl.removeView((View)this.mAnnotView);
/* 1102 */       this.mAnnotView = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onAnnotPainterUpdated(int page, long which, CurvePainter painter) {
/* 1111 */     if (this.mAnnotView != null && this.mAnnotView.getCurvePainterId() == which) {
/* 1112 */       this.mAnnotView.setCurvePainter(which, painter);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onQuickMenuClicked(QuickMenuItem menuItem) {
/* 1123 */     this.mNextToolMode = getToolMode();
/* 1124 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInsideQuickMenu(float x, float y) {
/* 1135 */     return (isQuickMenuShown() && x > this.mQuickMenu.getLeft() && x < this.mQuickMenu.getRight() && y < this.mQuickMenu.getBottom() && y > this.mQuickMenu.getTop());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isQuickMenuShown() {
/* 1144 */     return (this.mQuickMenu != null && this.mQuickMenu.isShowing());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateQuickMenuNoteText(String note) {
/* 1153 */     if (!isQuickMenuShown()) {
/*      */       return;
/*      */     }
/* 1156 */     QuickMenuItem menuItem = (QuickMenuItem)this.mQuickMenu.getMenu().findItem(R.id.qm_note);
/* 1157 */     if (menuItem != null)
/*      */     {
/* 1159 */       if (note != null && !note.equals("")) {
/* 1160 */         menuItem.setTitle(R.string.tools_qm_view_note);
/*      */       } else {
/* 1162 */         menuItem.setTitle(R.string.tools_qm_add_note);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateQuickMenuStyleColor(int color) {
/* 1173 */     if (color == 0 || this.mQuickMenu == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1177 */     QuickMenuItem menuItem = (QuickMenuItem)this.mQuickMenu.getMenu().findItem(R.id.qm_appearance);
/* 1178 */     if (menuItem != null) {
/* 1179 */       menuItem.setColor(color);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateQuickMenuStyleOpacity(float opacity) {
/* 1187 */     if (this.mQuickMenu == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1191 */     QuickMenuItem menuItem = (QuickMenuItem)this.mQuickMenu.getMenu().findItem(R.id.qm_appearance);
/* 1192 */     if (menuItem != null) {
/* 1193 */       menuItem.setOpacity(opacity);
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
/*      */ 
/*      */ 
/*      */   
/*      */   public void setupAnnotProperty(int color, float opacity, float thickness, int fillColor, String icon, String pdfTronFontName) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setupAnnotProperty(int color, float opacity, float thickness, int fillColor, String icon, String pdfTronFontName, @ColorInt int textColor, float textSize) {
/* 1224 */     setupAnnotProperty(color, opacity, thickness, fillColor, icon, pdfTronFontName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setupAnnotProperty(AnnotStyle annotStyle) {
/* 1233 */     int color = annotStyle.getColor();
/* 1234 */     int fill = annotStyle.getFillColor();
/* 1235 */     float thickness = annotStyle.getThickness();
/* 1236 */     float opacity = annotStyle.getOpacity();
/* 1237 */     String icon = annotStyle.getIcon();
/* 1238 */     String pdftronFontName = annotStyle.getPDFTronFontName();
/* 1239 */     float textSize = annotStyle.getTextSize();
/* 1240 */     int textColor = annotStyle.getTextColor();
/*      */     
/* 1242 */     ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 1243 */     toolManager.setSnappingEnabledForMeasurementTools(annotStyle.getSnap());
/* 1244 */     toolManager.setRichContentEnabledForFreeText(!Utils.isNullOrEmpty(annotStyle.getTextHTMLContent()));
/*      */     
/* 1246 */     setupAnnotProperty(color, opacity, thickness, fill, icon, pdftronFontName, textColor, textSize);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setForceSameNextToolMode(boolean mode) {
/* 1255 */     this.mForceSameNextToolMode = mode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isForceSameNextToolMode() {
/* 1264 */     return this.mForceSameNextToolMode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEditingAnnot() {
/*      */     try {
/* 1272 */       if (getToolMode() == ToolManager.ToolMode.TEXT_CREATE || getToolMode() == ToolManager.ToolMode.CALLOUT_CREATE)
/* 1273 */         return ((FreeTextCreate)this).isFreeTextEditing(); 
/* 1274 */       if (getToolMode() == ToolManager.ToolMode.ANNOT_EDIT) {
/* 1275 */         return ((AnnotEdit)this).isFreeTextEditing();
/*      */       }
/* 1277 */     } catch (Exception e) {
/* 1278 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } 
/* 1280 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPageNumberIndicatorVisible(boolean visible) {
/* 1289 */     this.mPageNumberIndicatorVisible = visible;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNextToolModeHelper(ToolManager.ToolMode nextToolMode) {
/* 1298 */     if (this.mForceSameNextToolMode) {
/* 1299 */       this.mNextToolMode = getToolMode();
/*      */     } else {
/* 1301 */       this.mNextToolMode = nextToolMode;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setCurrentDefaultToolModeHelper(ToolManager.ToolModeBase defaultToolMode) {
/* 1311 */     if (this.mForceSameNextToolMode) {
/* 1312 */       this.mCurrentDefaultToolMode = defaultToolMode;
/*      */     } else {
/* 1314 */       this.mCurrentDefaultToolMode = ToolManager.ToolMode.PAN;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ToolManager.ToolModeBase getCurrentDefaultToolMode() {
/* 1322 */     return this.mCurrentDefaultToolMode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean skipOnUpPriorEvent(PDFViewCtrl.PriorEventMode priorEventMode) {
/* 1332 */     return (priorEventMode == PDFViewCtrl.PriorEventMode.FLING || priorEventMode == PDFViewCtrl.PriorEventMode.SCROLLING);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void showTransientPageNumber() {
/* 1340 */     ((ToolManager)this.mPdfViewCtrl.getToolManager()).showBuiltInPageNumber();
/* 1341 */     this.mPageNumberRemovalHandler.removeMessages(1);
/* 1342 */     this.mPageNumberRemovalHandler.sendEmptyMessageDelayed(1, 3000L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void buildAnnotBBox() throws PDFNetException {
/* 1349 */     if (isValidAnnot(this.mAnnot)) {
/* 1350 */       this.mAnnotBBox.set(0.0F, 0.0F, 0.0F, 0.0F);
/*      */       try {
/* 1352 */         Rect r = this.mAnnot.getVisibleContentBox();
/* 1353 */         this.mAnnotBBox.set((float)r.getX1(), (float)r.getY1(), (float)r.getX2(), (float)r.getY2());
/* 1354 */       } catch (Exception e) {
/* 1355 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
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
/*      */   protected boolean isInsideAnnot(MotionEvent e) {
/* 1368 */     double x = e.getX();
/* 1369 */     double y = e.getY();
/* 1370 */     if (this.mAnnot != null && this.mAnnotPageNum == this.mPdfViewCtrl.getPageNumberFromScreenPt(x, y)) {
/* 1371 */       double[] pts = this.mPdfViewCtrl.convScreenPtToPagePt(x, y, this.mAnnotPageNum);
/* 1372 */       if (this.mAnnotBBox.contains((float)pts[0], (float)pts[1])) {
/* 1373 */         return true;
/*      */       }
/*      */     } 
/* 1376 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected RectF getAnnotRect() {
/* 1385 */     if (this.mAnnot != null && this.mAnnotPageNum > 0) {
/* 1386 */       double[] pts1 = this.mPdfViewCtrl.convPagePtToScreenPt(this.mAnnotBBox.left, this.mAnnotBBox.bottom, this.mAnnotPageNum);
/* 1387 */       double[] pts2 = this.mPdfViewCtrl.convPagePtToScreenPt(this.mAnnotBBox.right, this.mAnnotBBox.top, this.mAnnotPageNum);
/* 1388 */       float left = (float)((pts1[0] < pts2[0]) ? pts1[0] : pts2[0]);
/* 1389 */       float right = (float)((pts1[0] > pts2[0]) ? pts1[0] : pts2[0]);
/* 1390 */       float top = (float)((pts1[1] < pts2[1]) ? pts1[1] : pts2[1]);
/* 1391 */       float bottom = (float)((pts1[1] > pts2[1]) ? pts1[1] : pts2[1]);
/* 1392 */       return new RectF(left, top, right, bottom);
/*      */     } 
/* 1394 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   protected Rect getRectFromRectF(@Nullable RectF rectF) {
/* 1399 */     if (rectF == null) {
/* 1400 */       return null;
/*      */     }
/* 1402 */     Rect rect = new Rect();
/* 1403 */     rectF.round(rect);
/* 1404 */     return rect;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected RectF getAnnotCanvasRect() {
/* 1413 */     if (this.mAnnot != null) {
/* 1414 */       double[] pts1 = this.mPdfViewCtrl.convPagePtToCanvasPt(this.mAnnotBBox.left, this.mAnnotBBox.bottom, this.mAnnotPageNum);
/* 1415 */       double[] pts2 = this.mPdfViewCtrl.convPagePtToCanvasPt(this.mAnnotBBox.right, this.mAnnotBBox.top, this.mAnnotPageNum);
/* 1416 */       float left = (float)((pts1[0] < pts2[0]) ? pts1[0] : pts2[0]);
/* 1417 */       float right = (float)((pts1[0] > pts2[0]) ? pts1[0] : pts2[0]);
/* 1418 */       float top = (float)((pts1[1] < pts2[1]) ? pts1[1] : pts2[1]);
/* 1419 */       float bottom = (float)((pts1[1] > pts2[1]) ? pts1[1] : pts2[1]);
/* 1420 */       return new RectF(left, top, right, bottom);
/*      */     } 
/* 1422 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void doneTwoFingerScrolling() {
/* 1430 */     this.mAllowTwoFingerScroll = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void doneOneFingerScrollingWithStylus() {
/* 1437 */     this.mAllowOneFingerScrollWithStylus = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean showMenu(RectF anchor_rect) {
/* 1444 */     return showMenu(anchor_rect, createQuickMenu());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getQuickMenuAnalyticType() {
/* 1454 */     return 3;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean showMenu(RectF anchor_rect, QuickMenu quickMenu) {
/* 1461 */     if (anchor_rect == null) {
/* 1462 */       return false;
/*      */     }
/* 1464 */     ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/*      */     
/* 1466 */     if (this.mQuickMenu != null) {
/* 1467 */       if (this.mQuickMenu.isShowing() && this.mAnnot != null && this.mQuickMenu.getAnnot() != null && 
/* 1468 */         this.mAnnot.equals(this.mQuickMenu.getAnnot())) {
/* 1469 */         return false;
/*      */       }
/*      */       
/* 1472 */       closeQuickMenu();
/* 1473 */       this.mQuickMenu = null;
/*      */     } 
/*      */     
/* 1476 */     RectF client_r = new RectF(0.0F, 0.0F, this.mPdfViewCtrl.getWidth(), this.mPdfViewCtrl.getHeight());
/* 1477 */     if (!client_r.intersect(anchor_rect)) {
/* 1478 */       return false;
/*      */     }
/*      */     
/* 1481 */     toolManager.setQuickMenuJustClosed(false);
/*      */     
/* 1483 */     this.mQuickMenu = quickMenu;
/*      */     
/* 1485 */     this.mQuickMenu.setAnchorRect(anchor_rect);
/* 1486 */     this.mQuickMenu.setAnnot(this.mAnnot);
/*      */     
/* 1488 */     this.mQuickMenu.setOnDismissListener(new QuickMenuDismissListener());
/*      */     
/* 1490 */     this.mQuickMenu.show(getQuickMenuAnalyticType());
/*      */     
/* 1492 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected RectF calculateQMAnchor(RectF anchorRect) {
/* 1502 */     if (anchorRect != null) {
/* 1503 */       int left = (int)anchorRect.left;
/* 1504 */       int top = (int)anchorRect.top;
/* 1505 */       int right = (int)anchorRect.right;
/* 1506 */       int bottom = (int)anchorRect.bottom;
/*      */ 
/*      */       
/*      */       try {
/* 1510 */         Rect rect = new Rect(anchorRect.left, anchorRect.top, anchorRect.right, anchorRect.bottom);
/* 1511 */         rect.normalize();
/* 1512 */         left = (int)rect.getX1();
/* 1513 */         top = (int)rect.getY1();
/* 1514 */         right = (int)rect.getX2();
/* 1515 */         bottom = (int)rect.getY2();
/* 1516 */       } catch (PDFNetException e) {
/* 1517 */         AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*      */       } 
/*      */       
/* 1520 */       int[] location = new int[2];
/* 1521 */       this.mPdfViewCtrl.getLocationInWindow(location);
/*      */       
/* 1523 */       int atop = top + location[1];
/* 1524 */       int aleft = left + location[0];
/* 1525 */       int aright = right + location[0];
/* 1526 */       int abottom = bottom + location[1];
/*      */       
/* 1528 */       RectF qmAnchor = new RectF(aleft, atop, aright, abottom);
/* 1529 */       return qmAnchor;
/*      */     } 
/* 1531 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void selectAnnot(Annot annot, int pageNum) {
/* 1542 */     this.mPdfViewCtrl.cancelFindText();
/* 1543 */     boolean shouldUnlockRead = false;
/*      */     try {
/* 1545 */       this.mPdfViewCtrl.docLockRead();
/* 1546 */       shouldUnlockRead = true;
/* 1547 */       if (isValidAnnot(annot)) {
/* 1548 */         setAnnot(annot, pageNum);
/* 1549 */         buildAnnotBBox();
/*      */       } 
/* 1551 */     } catch (Exception ex) {
/* 1552 */       AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*      */     } finally {
/* 1554 */       if (shouldUnlockRead) {
/* 1555 */         this.mPdfViewCtrl.docUnlockRead();
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1561 */     onCreate();
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
/*      */   public static Pair<ToolManager.ToolMode, ArrayList<Annot>> canSelectGroupAnnot(PDFViewCtrl pdfViewCtrl, Annot annot, int pageNum) {
/* 1573 */     if (null == annot) {
/* 1574 */       return null;
/*      */     }
/*      */     
/*      */     try {
/* 1578 */       ArrayList<Annot> annotsInGroup = AnnotUtils.getAnnotationsInGroup(pdfViewCtrl, annot, pageNum);
/* 1579 */       if (annotsInGroup != null && annotsInGroup.size() > 1)
/*      */       {
/* 1581 */         return new Pair(ToolManager.ToolMode.ANNOT_EDIT_RECT_GROUP, annotsInGroup);
/*      */       }
/* 1583 */     } catch (Exception ex) {
/* 1584 */       AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*      */     } 
/* 1586 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected float convDp2Pix(float dp) {
/* 1593 */     return Utils.convDp2Pix(this.mPdfViewCtrl.getContext(), dp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected float convPix2Dp(float pix) {
/* 1600 */     return Utils.convPix2Dp(this.mPdfViewCtrl.getContext(), pix);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RectF getTextSelectRect(float x, float y) {
/* 1607 */     float delta = 0.5F;
/* 1608 */     float x2 = x + delta;
/* 1609 */     float y2 = y + delta;
/* 1610 */     delta *= 2.0F;
/* 1611 */     float x1 = (x2 - delta >= 0.0F) ? (x2 - delta) : 0.0F;
/* 1612 */     float y1 = (y2 - delta >= 0.0F) ? (y2 - delta) : 0.0F;
/*      */     
/* 1614 */     return new RectF(x1, y1, x2, y2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getStringFromResId(@StringRes int id) {
/* 1624 */     return this.mPdfViewCtrl.getResources().getString(id);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setAuthor(Markup annot) {
/* 1633 */     final Context context = this.mPdfViewCtrl.getContext();
/* 1634 */     if (context == null) {
/*      */       return;
/*      */     }
/*      */     try {
/* 1638 */       boolean generateUID = (annot.getUniqueID() == null);
/* 1639 */       if (annot.getUniqueID() != null) {
/* 1640 */         String uid = annot.getUniqueID().getAsPDFText();
/* 1641 */         if (Utils.isNullOrEmpty(uid)) {
/* 1642 */           generateUID = true;
/*      */         }
/*      */       } 
/* 1645 */       if (generateUID) {
/* 1646 */         setUniqueID(annot);
/*      */       }
/* 1648 */     } catch (PDFNetException e) {
/* 1649 */       e.printStackTrace();
/*      */     } 
/* 1651 */     if (this.mPdfViewCtrl.getToolManager() instanceof ToolManager) {
/* 1652 */       ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 1653 */       if (toolManager.getAuthorId() != null) {
/* 1654 */         setAuthor(annot, toolManager.getAuthorId());
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/* 1659 */     boolean authorNameHasBeenAsked = PdfViewCtrlSettingsManager.getAuthorNameHasBeenAsked(context);
/* 1660 */     String authorName = PdfViewCtrlSettingsManager.getAuthorName(context);
/* 1661 */     if (!authorNameHasBeenAsked && authorName.isEmpty()) {
/*      */       
/* 1663 */       boolean askAuthor = false;
/* 1664 */       if (this.mPdfViewCtrl.getToolManager() instanceof ToolManager && (
/* 1665 */         (ToolManager)this.mPdfViewCtrl.getToolManager()).isShowAuthorDialog()) {
/* 1666 */         askAuthor = true;
/*      */       }
/*      */ 
/*      */       
/* 1670 */       this.mMarkupToAuthor = annot;
/*      */       
/* 1672 */       String possibleName = "";
/*      */ 
/*      */       
/* 1675 */       int res = context.checkCallingOrSelfPermission("android.permission.GET_ACCOUNTS");
/* 1676 */       if (res == 0) {
/* 1677 */         Pattern emailPattern = Patterns.EMAIL_ADDRESS;
/* 1678 */         Account[] accounts = AccountManager.get(context).getAccounts();
/* 1679 */         for (Account account : accounts) {
/* 1680 */           if (emailPattern.matcher(account.name).matches()) {
/* 1681 */             possibleName = account.name;
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/* 1687 */       PdfViewCtrlSettingsManager.setAuthorNameHasBeenAsked(context);
/*      */       
/* 1689 */       if (askAuthor) {
/* 1690 */         LayoutInflater inflater = (LayoutInflater)context.getSystemService("layout_inflater");
/* 1691 */         View authorNameDialog = inflater.inflate(R.layout.tools_dialog_author_name, null);
/* 1692 */         final EditText authorNameEditText = (EditText)authorNameDialog.findViewById(R.id.tools_dialog_author_name_edittext);
/* 1693 */         authorNameEditText.setText(possibleName);
/* 1694 */         authorNameEditText.selectAll();
/*      */         
/* 1696 */         AlertDialog.Builder builder = new AlertDialog.Builder(context);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1713 */         final AlertDialog authorDialog = builder.setView(authorNameDialog).setTitle(R.string.tools_dialog_author_name_title).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() { public void onClick(DialogInterface dialog, int which) { String author = authorNameEditText.getText().toString().trim(); Tool.this.setAuthor(Tool.this.mMarkupToAuthor, author); PdfViewCtrlSettingsManager.updateAuthorName(context, author); } }).setNegativeButton(R.string.tools_misc_skip, new DialogInterface.OnClickListener() { public void onClick(DialogInterface dialog, int which) {} }).create();
/* 1714 */         authorDialog.show();
/* 1715 */         if (authorNameEditText.getText().length() == 0) {
/*      */           
/* 1717 */           authorDialog.getButton(-1).setEnabled(false);
/*      */         } else {
/* 1719 */           authorDialog.getButton(-1).setEnabled(true);
/*      */         } 
/* 1721 */         authorNameEditText.addTextChangedListener(new TextWatcher()
/*      */             {
/*      */               public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
/*      */ 
/*      */ 
/*      */               
/*      */               public void onTextChanged(CharSequence s, int start, int before, int count) {}
/*      */ 
/*      */ 
/*      */               
/*      */               public void afterTextChanged(Editable s) {
/* 1732 */                 if (authorDialog != null) {
/* 1733 */                   if (s.length() == 0) {
/*      */                     
/* 1735 */                     authorDialog.getButton(-1).setEnabled(false);
/*      */                   } else {
/* 1737 */                     authorDialog.getButton(-1).setEnabled(true);
/*      */                   } 
/*      */                 }
/*      */               }
/*      */             });
/*      */       } else {
/*      */         
/* 1744 */         setAuthor(this.mMarkupToAuthor, possibleName);
/*      */         
/* 1746 */         PdfViewCtrlSettingsManager.updateAuthorName(context, possibleName);
/*      */       } 
/*      */     } else {
/*      */       
/* 1750 */       String author = PdfViewCtrlSettingsManager.getAuthorName(context);
/* 1751 */       setAuthor(annot, author);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void setAuthor(Markup annot, String author) {
/* 1756 */     boolean shouldUnlock = false;
/*      */     try {
/* 1758 */       this.mPdfViewCtrl.docLock(true);
/* 1759 */       shouldUnlock = true;
/* 1760 */       annot.setTitle(author);
/* 1761 */     } catch (Exception e) {
/* 1762 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } finally {
/* 1764 */       if (shouldUnlock) {
/* 1765 */         this.mPdfViewCtrl.docUnlock();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setUniqueID(Markup annot) {
/* 1776 */     boolean shouldUnlock = false;
/*      */     try {
/* 1778 */       ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 1779 */       String key = toolManager.generateKey();
/* 1780 */       if (key != null) {
/* 1781 */         this.mPdfViewCtrl.docLock(true);
/* 1782 */         shouldUnlock = true;
/* 1783 */         annot.setUniqueID(key);
/*      */       } 
/* 1785 */     } catch (Exception e) {
/* 1786 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } finally {
/* 1788 */       if (shouldUnlock) {
/* 1789 */         this.mPdfViewCtrl.docUnlock();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setDateToNow(Annot annot) {
/* 1800 */     AnnotUtils.setDateToNow(this.mPdfViewCtrl, annot);
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
/*      */   protected String getColorKey(int annotType) {
/* 1812 */     return ToolStyleConfig.getInstance().getColorKey(annotType, "");
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
/*      */   protected String getTextColorKey(int annotType) {
/* 1881 */     return ToolStyleConfig.getInstance().getTextColorKey(annotType, "");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getTextSizeKey(int annotType) {
/* 1888 */     return ToolStyleConfig.getInstance().getTextSizeKey(annotType, "");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getDateFormatKey(int annotType) {
/* 1895 */     return ToolStyleConfig.getInstance().getDateFormatKey(annotType, "");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getThicknessKey(int annotType) {
/* 1905 */     return ToolStyleConfig.getInstance().getThicknessKey(annotType, "");
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
/*      */   protected String getOpacityKey(int annotType) {
/* 1971 */     return ToolStyleConfig.getInstance().getOpacityKey(annotType, "");
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
/*      */   protected String getColorFillKey(int annotType) {
/* 2037 */     return ToolStyleConfig.getInstance().getFillColorKey(annotType, "");
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
/*      */   protected String getIconKey(int annotType) {
/* 2072 */     return ToolStyleConfig.getInstance().getIconKey(annotType, "");
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
/*      */   public static String getFontKey(int annotType) {
/* 2095 */     return ToolStyleConfig.getInstance().getFontKey(annotType, "");
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
/*      */   protected String getRulerBaseUnitKey(int annotType) {
/* 2117 */     return ToolStyleConfig.getInstance().getRulerBaseUnitKey(annotType, "");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getRulerTranslateUnitKey(int annotType) {
/* 2126 */     return ToolStyleConfig.getInstance().getRulerTranslateUnitKey(annotType, "");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getRulerBaseValueKey(int annotType) {
/* 2135 */     return ToolStyleConfig.getInstance().getRulerBaseValueKey(annotType, "");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getRulerTranslateValueKey(int annotType) {
/* 2144 */     return ToolStyleConfig.getInstance().getRulerTranslateValueKey(annotType, "");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getEraserTypeKey(int annotType) {
/* 2153 */     return ToolStyleConfig.getInstance().getEraserTypeKey(annotType, "");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getInkEraserModeKey(int annotType) {
/* 2162 */     return ToolStyleConfig.getInstance().getInkEraserModeKey(annotType, "");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static ToolManager.ToolMode getModeFromAnnotType(Annot annot) {
/* 2172 */     ToolManager.ToolMode mode = ToolManager.ToolMode.LINE_CREATE;
/* 2173 */     if (annot != null) {
/*      */       try {
/* 2175 */         int annotType = annot.getType();
/* 2176 */         switch (annotType) {
/*      */           case 3:
/* 2178 */             if (AnnotUtils.isArrow(annot))
/* 2179 */               return ToolManager.ToolMode.ARROW_CREATE; 
/* 2180 */             if (AnnotUtils.isRuler(annot)) {
/* 2181 */               return ToolManager.ToolMode.RULER_CREATE;
/*      */             }
/* 2183 */             return ToolManager.ToolMode.LINE_CREATE;
/*      */           case 7:
/* 2185 */             if (AnnotUtils.isPerimeterMeasure(annot)) {
/* 2186 */               return ToolManager.ToolMode.PERIMETER_MEASURE_CREATE;
/*      */             }
/* 2188 */             return ToolManager.ToolMode.POLYLINE_CREATE;
/*      */           case 4:
/* 2190 */             return ToolManager.ToolMode.RECT_CREATE;
/*      */           case 5:
/* 2192 */             return ToolManager.ToolMode.OVAL_CREATE;
/*      */           case 17:
/* 2194 */             return ToolManager.ToolMode.SOUND_CREATE;
/*      */           case 16:
/* 2196 */             return ToolManager.ToolMode.FILE_ATTACHMENT_CREATE;
/*      */           case 6:
/* 2198 */             if (AnnotUtils.isCloud(annot))
/* 2199 */               return ToolManager.ToolMode.CLOUD_CREATE; 
/* 2200 */             if (AnnotUtils.isAreaMeasure(annot)) {
/* 2201 */               if (AnnotUtils.isRectAreaMeasure(annot)) {
/* 2202 */                 return ToolManager.ToolMode.RECT_AREA_MEASURE_CREATE;
/*      */               }
/* 2204 */               return ToolManager.ToolMode.AREA_MEASURE_CREATE;
/*      */             } 
/* 2206 */             return ToolManager.ToolMode.POLYGON_CREATE;
/*      */           case 8:
/* 2208 */             return ToolManager.ToolMode.TEXT_HIGHLIGHT;
/*      */           case 9:
/* 2210 */             return ToolManager.ToolMode.TEXT_UNDERLINE;
/*      */           case 11:
/* 2212 */             return ToolManager.ToolMode.TEXT_STRIKEOUT;
/*      */           case 10:
/* 2214 */             return ToolManager.ToolMode.TEXT_SQUIGGLY;
/*      */           case 2:
/* 2216 */             if (AnnotUtils.isCallout(annot)) {
/* 2217 */               return ToolManager.ToolMode.CALLOUT_CREATE;
/*      */             }
/* 2219 */             return ToolManager.ToolMode.TEXT_CREATE;
/*      */           case 14:
/* 2221 */             if (AnnotUtils.isFreeHighlighter(annot)) {
/* 2222 */               return ToolManager.ToolMode.FREE_HIGHLIGHTER;
/*      */             }
/* 2224 */             return ToolManager.ToolMode.INK_CREATE;
/*      */           case 0:
/* 2226 */             return ToolManager.ToolMode.TEXT_ANNOT_CREATE;
/*      */           case 1:
/* 2228 */             return ToolManager.ToolMode.RECT_LINK;
/*      */         } 
/* 2230 */         return ToolManager.ToolMode.LINE_CREATE;
/*      */       }
/* 2232 */       catch (PDFNetException e) {
/* 2233 */         AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*      */       } 
/*      */     }
/* 2236 */     return mode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void raiseAnnotationAddedEvent(Annot annot, int page) {
/* 2246 */     if (annot == null) {
/* 2247 */       AnalyticsHandlerAdapter.getInstance().sendException(new Exception("Annot is null"));
/*      */       return;
/*      */     } 
/* 2250 */     ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 2251 */     HashMap<Annot, Integer> annots = new HashMap<>(1);
/* 2252 */     annots.put(annot, Integer.valueOf(page));
/* 2253 */     toolManager.raiseAnnotationsAddedEvent(annots);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void raiseAnnotationAddedEvent(Map<Annot, Integer> annots) {
/* 2263 */     ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 2264 */     toolManager.raiseAnnotationsAddedEvent(annots);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void raiseAnnotationPreModifyEvent(Annot annot, int page) {
/* 2274 */     if (annot == null) {
/* 2275 */       AnalyticsHandlerAdapter.getInstance().sendException(new Exception("Annot is null"));
/*      */       return;
/*      */     } 
/* 2278 */     ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 2279 */     HashMap<Annot, Integer> annots = new HashMap<>(1);
/* 2280 */     annots.put(annot, Integer.valueOf(page));
/* 2281 */     toolManager.raiseAnnotationsPreModifyEvent(annots);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void raiseAnnotationPreModifyEvent(Map<Annot, Integer> annots) {
/* 2291 */     ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 2292 */     toolManager.raiseAnnotationsPreModifyEvent(annots);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void raiseAnnotationModifiedEvent(Annot annot, int page) {
/* 2302 */     if (annot == null) {
/* 2303 */       AnalyticsHandlerAdapter.getInstance().sendException(new Exception("Annot is null"));
/*      */       return;
/*      */     } 
/* 2306 */     setDateToNow(annot);
/* 2307 */     ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 2308 */     HashMap<Annot, Integer> annots = new HashMap<>(1);
/* 2309 */     annots.put(annot, Integer.valueOf(page));
/* 2310 */     toolManager.raiseAnnotationsModifiedEvent(annots, getAnnotationModificationBundle(null));
/*      */   }
/*      */   
/*      */   protected void raiseAnnotationModifiedEvent(Annot annot, int page, Bundle bundle) {
/* 2314 */     if (annot == null) {
/* 2315 */       AnalyticsHandlerAdapter.getInstance().sendException(new Exception("Annot is null"));
/*      */       return;
/*      */     } 
/* 2318 */     setDateToNow(annot);
/* 2319 */     ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 2320 */     HashMap<Annot, Integer> annots = new HashMap<>(1);
/* 2321 */     annots.put(annot, Integer.valueOf(page));
/* 2322 */     toolManager.raiseAnnotationsModifiedEvent(annots, getAnnotationModificationBundle(bundle));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void raiseAnnotationModifiedEvent(Map<Annot, Integer> annots) {
/* 2332 */     for (Map.Entry<Annot, Integer> entry : annots.entrySet()) {
/* 2333 */       setDateToNow(entry.getKey());
/*      */     }
/* 2335 */     ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 2336 */     toolManager.raiseAnnotationsModifiedEvent(annots, getAnnotationModificationBundle(null));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void raiseAnnotationPreRemoveEvent(Annot annot, int page) {
/* 2346 */     if (annot == null) {
/* 2347 */       AnalyticsHandlerAdapter.getInstance().sendException(new Exception("Annot is null"));
/*      */       return;
/*      */     } 
/* 2350 */     ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 2351 */     HashMap<Annot, Integer> annots = new HashMap<>(1);
/* 2352 */     annots.put(annot, Integer.valueOf(page));
/* 2353 */     toolManager.raiseAnnotationsPreRemoveEvent(annots);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void raiseAnnotationPreRemoveEvent(Map<Annot, Integer> annots) {
/* 2363 */     ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 2364 */     toolManager.raiseAnnotationsPreRemoveEvent(annots);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void raiseAnnotationRemovedEvent(Annot annot, int page) {
/* 2374 */     if (annot == null) {
/* 2375 */       AnalyticsHandlerAdapter.getInstance().sendException(new Exception("Annot is null"));
/*      */       return;
/*      */     } 
/* 2378 */     ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 2379 */     HashMap<Annot, Integer> annots = new HashMap<>(1);
/* 2380 */     annots.put(annot, Integer.valueOf(page));
/* 2381 */     toolManager.raiseAnnotationsRemovedEvent(annots);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void raiseAnnotationRemovedEvent(Map<Annot, Integer> annots) {
/* 2391 */     ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 2392 */     toolManager.raiseAnnotationsRemovedEvent(annots);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void raiseAnnotationActionEvent() {
/* 2399 */     ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 2400 */     toolManager.raiseAnnotationActionEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final boolean onInterceptAnnotationHandling(@Nullable Annot annot) {
/* 2410 */     Bundle bundle = getAnnotationModificationBundle(null);
/* 2411 */     return onInterceptAnnotationHandling(annot, bundle);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final boolean onInterceptAnnotationHandling(@Nullable Annot annot, @NonNull Bundle bundle) {
/* 2422 */     bundle = getAnnotationModificationBundle(bundle);
/* 2423 */     bundle.putInt("PAGE_NUMBER", this.mAnnotPageNum);
/* 2424 */     ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 2425 */     return toolManager.raiseInterceptAnnotationHandlingEvent(annot, bundle, ToolManager.getDefaultToolMode(getToolMode()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final boolean onInterceptAnnotationHandling(@Nullable PDFViewCtrl.LinkInfo linkInfo, int pageNum) {
/* 2436 */     Bundle bundle = new Bundle();
/* 2437 */     bundle.putInt("PAGE_NUMBER", pageNum);
/* 2438 */     if (linkInfo != null) {
/* 2439 */       bundle.putBoolean("IS_LINK", true);
/* 2440 */       bundle.putString("LINK_URL", linkInfo.getURL());
/* 2441 */       Rect rect = linkInfo.getRect();
/*      */       try {
/* 2443 */         RectF rectF = AnnotUtils.getScreenRectFromPageRect(this.mPdfViewCtrl, rect, pageNum);
/* 2444 */         bundle.putParcelable("LINK_RECTF", (Parcelable)rectF);
/* 2445 */       } catch (Exception ex) {
/* 2446 */         AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*      */       } 
/*      */     } 
/* 2449 */     ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 2450 */     return toolManager.raiseInterceptAnnotationHandlingEvent((Annot)new Link(), bundle, ToolManager.getDefaultToolMode(getToolMode()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean onInterceptDialogEvent(AlertDialog dialog) {
/* 2457 */     ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 2458 */     return toolManager.raiseInterceptDialogEvent(dialog);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addOldTools() {
/* 2465 */     if (!this.mPdfViewCtrl.isAnnotationLayerEnabled()) {
/* 2466 */       ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 2467 */       if (null != toolManager) {
/* 2468 */         toolManager.getOldTools().add(this);
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
/*      */   protected Rect convertFromPageRectToScreenRect(Rect pageRect, int page) {
/* 2481 */     Rect screenRect = null;
/*      */     
/* 2483 */     if (pageRect != null) {
/*      */       try {
/* 2485 */         float sx = this.mPdfViewCtrl.getScrollX();
/* 2486 */         float sy = this.mPdfViewCtrl.getScrollY();
/*      */ 
/*      */ 
/*      */         
/* 2490 */         double[] pts1 = this.mPdfViewCtrl.convPagePtToScreenPt(pageRect.getX1(), pageRect.getY1(), page);
/* 2491 */         double[] pts2 = this.mPdfViewCtrl.convPagePtToScreenPt(pageRect.getX2(), pageRect.getY2(), page);
/*      */         
/* 2493 */         float x1 = (float)pts1[0] + sx;
/* 2494 */         float y1 = (float)pts1[1] + sy;
/* 2495 */         float x2 = (float)pts2[0] + sx;
/* 2496 */         float y2 = (float)pts2[1] + sy;
/*      */         
/* 2498 */         screenRect = new Rect(x1, y1, x2, y2);
/* 2499 */       } catch (PDFNetException ex) {
/* 2500 */         AnalyticsHandlerAdapter.getInstance().sendException((Exception)ex);
/*      */       } 
/*      */     }
/*      */     
/* 2504 */     return screenRect;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setAnnot(Annot annot, int pageNum) {
/* 2514 */     this.mAnnot = annot;
/* 2515 */     this.mAnnotPageNum = pageNum;
/*      */     try {
/* 2517 */       if (this.mPdfViewCtrl.getToolManager() instanceof ToolManager) {
/* 2518 */         ((ToolManager)this.mPdfViewCtrl.getToolManager()).setSelectedAnnot(this.mAnnot, this.mAnnotPageNum);
/*      */       }
/* 2520 */     } catch (Exception e) {
/* 2521 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void unsetAnnot() {
/* 2529 */     removeAnnotView();
/* 2530 */     this.mAnnot = null;
/*      */     try {
/* 2532 */       if (this.mPdfViewCtrl.getToolManager() instanceof ToolManager) {
/* 2533 */         ((ToolManager)this.mPdfViewCtrl.getToolManager()).setSelectedAnnot(null, -1);
/*      */       }
/* 2535 */     } catch (Exception e) {
/* 2536 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected boolean addRotateHandle() {
/* 2541 */     if (this.mAnnot == null) {
/* 2542 */       return false;
/*      */     }
/* 2544 */     if (this.mRotateHandle != null) {
/* 2545 */       removeRotateHandle();
/*      */     }
/* 2547 */     if (canAddRotateView(this.mAnnot)) {
/* 2548 */       this.mRotateHandle = new RotateHandleView(this.mPdfViewCtrl.getContext());
/* 2549 */       this.mPdfViewCtrl.addView((View)this.mRotateHandle);
/*      */     } 
/* 2551 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean addAnnotView() {
/* 2560 */     if (this.mAnnot == null) {
/* 2561 */       return false;
/*      */     }
/* 2563 */     if (this.mAnnotView != null) {
/* 2564 */       removeAnnotView(false);
/*      */     }
/*      */     try {
/* 2567 */       if (null == this.mAnnotStyle) {
/* 2568 */         boolean shouldUnlockRead = false;
/*      */         try {
/* 2570 */           this.mPdfViewCtrl.docLockRead();
/* 2571 */           shouldUnlockRead = true;
/* 2572 */           this.mAnnotStyle = AnnotUtils.getAnnotStyle(this.mAnnot);
/*      */         } finally {
/* 2574 */           if (shouldUnlockRead) {
/* 2575 */             this.mPdfViewCtrl.docUnlockRead();
/*      */           }
/*      */         } 
/*      */       } 
/* 2579 */       boolean canDraw = canAddAnnotView(this.mAnnot, this.mAnnotStyle);
/*      */       
/* 2581 */       this.mAnnotView = new AnnotView(this.mPdfViewCtrl.getContext());
/* 2582 */       this.mAnnotView.setAnnotStyle(this.mPdfViewCtrl, this.mAnnotStyle);
/* 2583 */       this.mAnnotView.setPage(this.mAnnotPageNum);
/* 2584 */       this.mAnnotView.setCanDraw(canDraw);
/* 2585 */       this.mAnnotView.setCurvePainter(this.mAnnot.__GetHandle(), this.mPdfViewCtrl.getAnnotationPainter(this.mAnnotPageNum, this.mAnnot.__GetHandle()));
/*      */       
/* 2587 */       if (canDraw) {
/* 2588 */         if (!AnnotUtils.canUseBitmapAppearance(this.mAnnot)) {
/* 2589 */           hideAnnot();
/*      */         }
/* 2591 */         updateAnnotViewBitmap(true);
/*      */       } 
/* 2593 */       this.mAnnotView.setZoom(this.mPdfViewCtrl.getZoom());
/* 2594 */       this.mAnnotView.setPageNum(this.mAnnotPageNum);
/* 2595 */       this.mAnnotView.setHasPermission(this.mHasSelectionPermission);
/* 2596 */       this.mPdfViewCtrl.addView((View)this.mAnnotView);
/* 2597 */       return true;
/* 2598 */     } catch (Exception ex) {
/* 2599 */       AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*      */       
/* 2601 */       return false;
/*      */     } 
/*      */   }
/*      */   protected void removeRotateHandle() {
/* 2605 */     if (this.mRotateHandle != null) {
/* 2606 */       this.mRotateHandle.setListener(null);
/* 2607 */       this.mPdfViewCtrl.removeView((View)this.mRotateHandle);
/* 2608 */       this.mRotateHandle = null;
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void removeAnnotView() {
/* 2613 */     removeAnnotView(!this.mPdfViewCtrl.isAnnotationLayerEnabled());
/*      */   }
/*      */   
/*      */   protected void removeAnnotView(boolean delayRemoval) {
/* 2617 */     removeAnnotView(delayRemoval, true);
/*      */   }
/*      */   
/*      */   protected void removeAnnotView(boolean delayRemoval, boolean removeRotateView) {
/* 2621 */     removeAnnotView(delayRemoval, removeRotateView, true);
/*      */   }
/*      */   
/*      */   protected void removeAnnotView(boolean delayRemoval, boolean removeRotateView, boolean showAnnotation) {
/* 2625 */     if (removeRotateView) {
/* 2626 */       removeRotateHandle();
/*      */     }
/* 2628 */     this.mBitmapDisposable.clear();
/* 2629 */     if (this.mAnnotView != null) {
/* 2630 */       this.mAnnotView.prepareRemoval();
/* 2631 */       if (delayRemoval) {
/* 2632 */         this.mAnnotView.setDelayViewRemoval(delayRemoval);
/* 2633 */         addOldTools();
/*      */       } else {
/* 2635 */         this.mPdfViewCtrl.removeView((View)this.mAnnotView);
/* 2636 */         this.mAnnotView = null;
/*      */       } 
/*      */       
/* 2639 */       if (this.mAnnot != null && 
/* 2640 */         showAnnotation) {
/* 2641 */         this.mPdfViewCtrl.showAnnotation(this.mAnnot);
/* 2642 */         boolean shouldUnlockRead = false;
/*      */         try {
/* 2644 */           if (!this.mPdfViewCtrl.isAnnotationLayerEnabled()) {
/* 2645 */             this.mPdfViewCtrl.docLockRead();
/* 2646 */             shouldUnlockRead = true;
/* 2647 */             this.mPdfViewCtrl.update(this.mAnnot, this.mAnnotPageNum);
/*      */           } 
/* 2649 */         } catch (Exception ex) {
/* 2650 */           AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*      */         } finally {
/* 2652 */           if (shouldUnlockRead) {
/* 2653 */             this.mPdfViewCtrl.docUnlockRead();
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean canAddAnnotView(Annot annot, AnnotStyle annotStyle) {
/* 2662 */     return false;
/*      */   }
/*      */   
/*      */   protected boolean canAddRotateView(Annot annot) {
/* 2666 */     return false;
/*      */   }
/*      */   
/*      */   void hideAnnot() throws PDFNetException {
/* 2670 */     this.mPdfViewCtrl.hideAnnotation(this.mAnnot);
/* 2671 */     boolean shouldUnlockRead = false;
/*      */     try {
/* 2673 */       if (!this.mPdfViewCtrl.isAnnotationLayerEnabled()) {
/* 2674 */         this.mPdfViewCtrl.docLockRead();
/* 2675 */         shouldUnlockRead = true;
/* 2676 */         this.mPdfViewCtrl.update(this.mAnnot, this.mAnnotPageNum);
/*      */       } 
/*      */     } finally {
/* 2679 */       if (shouldUnlockRead) {
/* 2680 */         this.mPdfViewCtrl.docUnlockRead();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   void updateAnnotViewBitmap() {
/* 2686 */     updateAnnotViewBitmap(false);
/*      */   }
/*      */   
/*      */   void updateAnnotViewBitmap(final boolean shouldHideAnnot) {
/* 2690 */     if (this.mAnnotView != null && this.mAnnotView.getCanDraw() && !this.mPdfViewCtrl.isAnnotationLayerEnabled()) {
/* 2691 */       this.mBitmapDisposable.add(AnnotUtils.getAnnotationAppearanceAsync(this.mPdfViewCtrl, this.mAnnot)
/* 2692 */           .subscribeOn(Schedulers.io())
/* 2693 */           .observeOn(AndroidSchedulers.mainThread())
/* 2694 */           .subscribe(new Consumer<Bitmap>()
/*      */             {
/*      */               public void accept(Bitmap bitmap) throws Exception {
/* 2697 */                 if (shouldHideAnnot) {
/* 2698 */                   Tool.this.hideAnnot();
/*      */                 }
/* 2700 */                 if (Tool.this.mAnnotView != null) {
/* 2701 */                   Tool.this.mAnnotView.setAnnotBitmap(bitmap);
/*      */                 }
/*      */               }
/*      */             },  new Consumer<Throwable>()
/*      */             {
/*      */               public void accept(Throwable throwable) throws Exception {}
/*      */             }));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void deleteAnnot() {
/* 2718 */     if (this.mAnnot == null || this.mPdfViewCtrl == null) {
/*      */       return;
/*      */     }
/*      */     
/* 2722 */     boolean shouldUnlock = false;
/*      */     try {
/* 2724 */       this.mPdfViewCtrl.docLock(true);
/* 2725 */       shouldUnlock = true;
/*      */       
/* 2727 */       this.mNextToolMode = this.mCurrentDefaultToolMode;
/* 2728 */       raiseAnnotationPreRemoveEvent(this.mAnnot, this.mAnnotPageNum);
/* 2729 */       Page page = this.mPdfViewCtrl.getDoc().getPage(this.mAnnotPageNum);
/* 2730 */       page.annotRemove(this.mAnnot);
/* 2731 */       this.mPdfViewCtrl.update(this.mAnnot, this.mAnnotPageNum);
/*      */ 
/*      */       
/* 2734 */       raiseAnnotationRemovedEvent(this.mAnnot, this.mAnnotPageNum);
/* 2735 */       if (sDebug) Log.d(TAG, "going to unsetAnnot: onQuickMenuclicked");
/*      */       
/* 2737 */       unsetAnnot();
/* 2738 */     } catch (Exception e) {
/* 2739 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } finally {
/* 2741 */       if (shouldUnlock) {
/* 2742 */         this.mPdfViewCtrl.docUnlock();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void flattenAnnot() {
/* 2751 */     if (this.mAnnot == null || this.mPdfViewCtrl == null) {
/*      */       return;
/*      */     }
/*      */     
/* 2755 */     boolean shouldUnlock = false;
/*      */ 
/*      */     
/*      */     try {
/* 2759 */       this.mPdfViewCtrl.docLock(true);
/* 2760 */       shouldUnlock = true;
/* 2761 */       raiseAnnotationPreModifyEvent(this.mAnnot, this.mAnnotPageNum);
/*      */       
/* 2763 */       AnnotUtils.flattenAnnot(this.mPdfViewCtrl, this.mAnnot, this.mAnnotPageNum);
/*      */       
/* 2765 */       raiseAnnotationModifiedEvent(this.mAnnot, this.mAnnotPageNum);
/*      */       
/* 2767 */       unsetAnnot();
/* 2768 */     } catch (Exception e) {
/* 2769 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } finally {
/* 2771 */       if (shouldUnlock) {
/* 2772 */         this.mPdfViewCtrl.docUnlock();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void handleFlattenAnnot() {
/* 2782 */     SharedPreferences settings = getToolPreferences(this.mPdfViewCtrl.getContext());
/* 2783 */     if (settings.getBoolean("stamp_show_flatten_warning", true)) {
/*      */       
/* 2785 */       LayoutInflater inflater = LayoutInflater.from(this.mPdfViewCtrl.getContext());
/* 2786 */       View customLayout = inflater.inflate(R.layout.alert_dialog_with_checkbox, null);
/* 2787 */       String text = this.mPdfViewCtrl.getContext().getResources().getString(R.string.tools_dialog_flatten_dialog_msg);
/* 2788 */       TextView dialogTextView = (TextView)customLayout.findViewById(R.id.dialog_message);
/* 2789 */       dialogTextView.setText(text);
/* 2790 */       final CheckBox dialogCheckBox = (CheckBox)customLayout.findViewById(R.id.dialog_checkbox);
/* 2791 */       dialogCheckBox.setChecked(true);
/*      */       
/* 2793 */       final long annotImpl = (this.mAnnot != null) ? this.mAnnot.__GetHandle() : 0L;
/* 2794 */       final int pageNum = this.mAnnotPageNum;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2815 */       AlertDialog.Builder dialogBuilder = (new AlertDialog.Builder(this.mPdfViewCtrl.getContext())).setView(customLayout).setTitle(this.mPdfViewCtrl.getContext().getResources().getString(R.string.tools_dialog_flatten_dialog_title)).setPositiveButton(R.string.tools_qm_flatten, new DialogInterface.OnClickListener() { public void onClick(DialogInterface dialog, int which) { boolean showAgain = !dialogCheckBox.isChecked(); SharedPreferences settings = Tool.getToolPreferences(Tool.this.mPdfViewCtrl.getContext()); SharedPreferences.Editor editor = settings.edit(); editor.putBoolean("stamp_show_flatten_warning", showAgain); editor.apply(); Tool.this.mAnnot = Annot.__Create(annotImpl, Tool.this.mPdfViewCtrl.getDoc()); Tool.this.mAnnotPageNum = pageNum; Tool.this.flattenAnnot(); } }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
/*      */           {
/*      */             public void onClick(DialogInterface dialog, int which) {
/* 2818 */               boolean showAgain = !dialogCheckBox.isChecked();
/* 2819 */               SharedPreferences settings = Tool.getToolPreferences(Tool.this.mPdfViewCtrl.getContext());
/* 2820 */               SharedPreferences.Editor editor = settings.edit();
/* 2821 */               editor.putBoolean("stamp_show_flatten_warning", showAgain);
/* 2822 */               editor.apply();
/*      */ 
/*      */               
/* 2825 */               Tool.this.showMenu(Tool.this.getAnnotRect());
/*      */             }
/*      */           });
/*      */       
/* 2829 */       dialogBuilder.create().show();
/*      */     }
/*      */     else {
/*      */       
/* 2833 */       flattenAnnot();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final boolean isMadeByPDFTron(Annot annot) throws PDFNetException {
/* 2841 */     return AnnotUtils.isMadeByPDFTron(annot);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void initializeSnapToNearest() {
/* 2846 */     this.mPdfViewCtrl.snapToNearestInDoc(0.0D, 0.0D);
/*      */   }
/*      */   
/*      */   public void setSnappingEnabled(boolean enabled) {
/* 2850 */     this.mSnappingEnabled = enabled;
/* 2851 */     if (enabled) {
/* 2852 */       initializeSnapToNearest();
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean getSnappingEnabled() {
/* 2857 */     return this.mSnappingEnabled;
/*      */   }
/*      */   
/*      */   protected PointF snapToNearestIfEnabled(PointF point) {
/* 2861 */     if (this.mSnappingEnabled) {
/* 2862 */       return this.mPdfViewCtrl.snapToNearestInDoc(point.x, point.y);
/*      */     }
/* 2864 */     return point;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setDebug(boolean debug) {
/* 2869 */     sDebug = debug;
/*      */   }
/*      */   
/*      */   public static Bundle getAnnotationModificationBundle(Bundle bundle) {
/* 2873 */     if (bundle == null) {
/* 2874 */       bundle = new Bundle();
/*      */     }
/* 2876 */     return bundle;
/*      */   }
/*      */   
/*      */   public static SharedPreferences getToolPreferences(@NonNull Context context) {
/* 2880 */     return context.getApplicationContext().getSharedPreferences("com_pdftron_pdfnet_pdfviewctrl_prefs_file", 0);
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
/*      */   @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/*      */   @NonNull
/*      */   public static String findPDFTronFontName(@NonNull Context context, @NonNull String fontName) throws JSONException {
/* 2894 */     String pdftronFontName = "";
/* 2895 */     SharedPreferences settings = getToolPreferences(context);
/* 2896 */     String fontInfo = settings.getString("annotation_property_free_text_fonts_list", "");
/* 2897 */     if (!Utils.isNullOrEmpty(fontInfo)) {
/* 2898 */       JSONObject systemFontObject = new JSONObject(fontInfo);
/* 2899 */       JSONArray systemFontArray = systemFontObject.getJSONArray("fonts");
/*      */       
/* 2901 */       for (int i = 0; i < systemFontArray.length(); i++) {
/*      */         
/* 2903 */         JSONObject fontJson = systemFontArray.getJSONObject(i);
/* 2904 */         if (fontJson.has("font name")) {
/* 2905 */           String fontNameCompare = fontJson.getString("font name");
/* 2906 */           if (fontName.equals(fontNameCompare)) {
/* 2907 */             pdftronFontName = fontJson.getString("pdftron name");
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/* 2914 */     return pdftronFontName;
/*      */   }
/*      */   
/*      */   @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/*      */   public static void updateFontMap(@NonNull Context context, int annotType, @NonNull String pdfFontName, @NonNull String fontName) {
/* 2919 */     SharedPreferences settings = getToolPreferences(context);
/* 2920 */     String fontInfo = settings.getString("annotation_property_free_text_fonts_list", "");
/*      */     try {
/* 2922 */       if (!Utils.isNullOrEmpty(fontInfo)) {
/* 2923 */         JSONObject systemFontObject = new JSONObject(fontInfo);
/* 2924 */         JSONArray systemFontArray = systemFontObject.getJSONArray("fonts");
/*      */         
/* 2926 */         for (int i = 0; i < systemFontArray.length(); i++) {
/* 2927 */           JSONObject fontObj = systemFontArray.getJSONObject(i);
/*      */           
/* 2929 */           if (fontObj.getString("pdftron name").equals(pdfFontName)) {
/* 2930 */             fontObj.put("font name", fontName);
/*      */             break;
/*      */           } 
/*      */         } 
/* 2934 */         fontInfo = systemFontObject.toString();
/*      */       } 
/* 2936 */     } catch (JSONException e) {
/* 2937 */       AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*      */     } 
/*      */     
/* 2940 */     SharedPreferences.Editor editor = settings.edit();
/* 2941 */     editor.putString("annotation_property_free_text_fonts_list", fontInfo);
/* 2942 */     editor.putString(getFontKey(annotType), pdfFontName);
/* 2943 */     editor.apply();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void updateFont(@NonNull PDFViewCtrl pdfViewCtrl, @NonNull Widget widget, String contents) throws PDFNetException, JSONException {
/* 2950 */     Font font = widget.getFont();
/* 2951 */     String fontName = font.getName();
/*      */ 
/*      */     
/* 2954 */     String pdftronFontName = findPDFTronFontName(pdfViewCtrl.getContext(), fontName);
/* 2955 */     ToolManager toolManager = (ToolManager)pdfViewCtrl.getToolManager();
/* 2956 */     if (!Utils.isNullOrEmpty(pdftronFontName) && toolManager.isFontLoaded()) {
/* 2957 */       Font newFont = Font.create((Doc)pdfViewCtrl.getDoc(), pdftronFontName, contents);
/* 2958 */       widget.setFont(newFont);
/* 2959 */       widget.refreshAppearance();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void showWidgetChoiceDialog(long widget, int page, boolean isSingleChoice, boolean isCombo, @Nullable String[] options) {
/* 2965 */     ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 2966 */     FragmentActivity activity = toolManager.getCurrentActivity();
/* 2967 */     if (activity == null) {
/* 2968 */       Log.e(Signature.class.getName(), "ToolManager is not attached to with an Activity");
/*      */       
/*      */       return;
/*      */     } 
/* 2972 */     ChoiceViewModel viewModel = (ChoiceViewModel)ViewModelProviders.of(activity).get(ChoiceViewModel.class);
/* 2973 */     viewModel.observeOnComplete((LifecycleOwner)activity, new Observer<Event<ChoiceResult>>()
/*      */         {
/*      */           public void onChanged(@Nullable Event<ChoiceResult> resultEvent) {
/* 2976 */             if (resultEvent != null && !resultEvent.hasBeenHandled()) {
/* 2977 */               ChoiceResult result = (ChoiceResult)resultEvent.getContentIfNotHandled();
/* 2978 */               if (result != null) {
/* 2979 */                 String[] options = result.getOptions();
/* 2980 */                 if (null == options) {
/*      */                   return;
/*      */                 }
/* 2983 */                 boolean shouldUnlock = false;
/* 2984 */                 Annot annot = null;
/* 2985 */                 int pageNum = 0;
/*      */                 try {
/* 2987 */                   Tool.this.mPdfViewCtrl.docLock(true);
/* 2988 */                   shouldUnlock = true;
/*      */                   
/* 2990 */                   annot = Annot.__Create(result.getWidget(), Tool.this.mPdfViewCtrl.getDoc());
/* 2991 */                   pageNum = result.getPage();
/* 2992 */                   boolean singleChoice = result.isSingleChoice();
/* 2993 */                   if (Tool.this.isValidAnnot(annot)) {
/* 2994 */                     Tool.this.raiseAnnotationPreModifyEvent(annot, pageNum);
/*      */                     
/* 2996 */                     Widget widget = new Widget(annot);
/*      */ 
/*      */                     
/* 2999 */                     Field field = widget.getField();
/* 3000 */                     field.setFlag(17, !singleChoice);
/*      */                     
/* 3002 */                     boolean isCombo = field.getFlag(14);
/* 3003 */                     if (isCombo) {
/* 3004 */                       ComboBoxWidget combo = new ComboBoxWidget(annot);
/* 3005 */                       combo.replaceOptions(options);
/*      */                     } else {
/* 3007 */                       ListBoxWidget list = new ListBoxWidget(annot);
/* 3008 */                       list.replaceOptions(options);
/*      */                     } 
/*      */ 
/*      */                     
/* 3012 */                     String allOptions = Arrays.toString((Object[])options);
/* 3013 */                     Tool.updateFont(Tool.this.mPdfViewCtrl, widget, allOptions);
/*      */ 
/*      */                     
/* 3016 */                     if (field.getFlag(14) && options.length > 0) {
/* 3017 */                       ComboBoxWidget combo = new ComboBoxWidget(annot);
/* 3018 */                       String selected = combo.getSelectedOption();
/* 3019 */                       if (Utils.isNullOrEmpty(selected) || !Arrays.<String>asList(options).contains(selected)) {
/* 3020 */                         combo.setSelectedOption(options[0]);
/*      */                       }
/*      */                     } 
/*      */                     
/* 3024 */                     widget.refreshAppearance();
/* 3025 */                     Tool.this.mPdfViewCtrl.update(annot, pageNum);
/*      */                     
/* 3027 */                     Tool.this.raiseAnnotationModifiedEvent(annot, pageNum);
/*      */                   } 
/* 3029 */                 } catch (Exception ex) {
/* 3030 */                   AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*      */                 } finally {
/* 3032 */                   if (shouldUnlock) {
/* 3033 */                     Tool.this.mPdfViewCtrl.docUnlock();
/*      */                   }
/*      */                 } 
/* 3036 */                 if (annot != null) {
/* 3037 */                   ToolManager toolManager = (ToolManager)Tool.this.mPdfViewCtrl.getToolManager();
/* 3038 */                   toolManager.selectAnnot(annot, pageNum);
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           }
/*      */         });
/*      */ 
/*      */     
/* 3046 */     ChoiceDialogFragment fragment = ChoiceDialogFragment.newInstance(widget, page, isSingleChoice, isCombo, options);
/* 3047 */     fragment.setStyle(0, R.style.CustomAppTheme);
/* 3048 */     fragment.show(activity.getSupportFragmentManager(), ChoiceDialogFragment.TAG);
/*      */   }
/*      */   
/*      */   protected boolean isValidAnnot(Annot annot) throws PDFNetException {
/* 3052 */     return (annot != null && annot.isValid());
/*      */   }
/*      */   public abstract ToolManager.ToolModeBase getToolMode();
/*      */   public abstract int getCreateAnnotType();
/*      */   private static class PageNumberRemovalHandler extends Handler { private final WeakReference<Tool> mTool;
/*      */     
/*      */     public PageNumberRemovalHandler(Tool tool) {
/* 3059 */       this.mTool = new WeakReference<>(tool);
/*      */     }
/*      */ 
/*      */     
/*      */     public void handleMessage(Message msg) {
/* 3064 */       Tool tool = this.mTool.get();
/* 3065 */       if (tool != null) {
/* 3066 */         ToolManager toolManager = (ToolManager)tool.mPdfViewCtrl.getToolManager();
/* 3067 */         toolManager.hideBuiltInPageNumber();
/*      */       } 
/*      */     } }
/*      */ 
/*      */   
/*      */   private class QuickMenuDismissListener implements QuickMenu.OnDismissListener {
/*      */     private QuickMenuDismissListener() {}
/*      */     
/*      */     public void onDismiss() {
/* 3076 */       if (Tool.this.mPdfViewCtrl.getToolManager() instanceof ToolManager) {
/* 3077 */         ToolManager toolManager = (ToolManager)Tool.this.mPdfViewCtrl.getToolManager();
/* 3078 */         toolManager.setQuickMenuJustClosed(true);
/* 3079 */         toolManager.onQuickMenuDismissed();
/*      */       } 
/*      */       
/* 3082 */       if (Tool.this.mQuickMenu != null) {
/* 3083 */         QuickMenuItem selectedMenuItem = Tool.this.mQuickMenu.getSelectedMenuItem();
/* 3084 */         if (selectedMenuItem != null) {
/* 3085 */           AnalyticsHandlerAdapter.getInstance().sendEvent(8, selectedMenuItem.getText(), Tool.this.getModeAHLabel());
/* 3086 */           ToolManager toolManager = (ToolManager)Tool.this.mPdfViewCtrl.getToolManager();
/* 3087 */           toolManager.onQuickMenuClicked(selectedMenuItem);
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\Tool.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */