/*      */ package com.pdftron.pdf.controls;
/*      */ 
/*      */ import android.content.Context;
/*      */ import android.content.DialogInterface;
/*      */ import android.content.SharedPreferences;
/*      */ import android.content.res.Configuration;
/*      */ import android.content.res.TypedArray;
/*      */ import android.graphics.drawable.Drawable;
/*      */ import android.graphics.drawable.StateListDrawable;
/*      */ import android.util.AttributeSet;
/*      */ import android.util.SparseIntArray;
/*      */ import android.view.KeyEvent;
/*      */ import android.view.LayoutInflater;
/*      */ import android.view.View;
/*      */ import android.view.ViewGroup;
/*      */ import android.widget.PopupWindow;
/*      */ import android.widget.RelativeLayout;
/*      */ import androidx.annotation.NonNull;
/*      */ import androidx.annotation.Nullable;
/*      */ import androidx.annotation.RequiresApi;
/*      */ import androidx.appcompat.widget.AppCompatImageButton;
/*      */ import androidx.fragment.app.FragmentActivity;
/*      */ import androidx.transition.ChangeBounds;
/*      */ import androidx.transition.Fade;
/*      */ import androidx.transition.Slide;
/*      */ import androidx.transition.Transition;
/*      */ import androidx.transition.TransitionManager;
/*      */ import androidx.transition.TransitionSet;
/*      */ import com.pdftron.pdf.Annot;
/*      */ import com.pdftron.pdf.PDFViewCtrl;
/*      */ import com.pdftron.pdf.config.ToolConfig;
/*      */ import com.pdftron.pdf.config.ToolStyleConfig;
/*      */ import com.pdftron.pdf.model.AnnotStyle;
/*      */ import com.pdftron.pdf.model.GroupedItem;
/*      */ import com.pdftron.pdf.tools.AdvancedShapeCreate;
/*      */ import com.pdftron.pdf.tools.FreehandCreate;
/*      */ import com.pdftron.pdf.tools.R;
/*      */ import com.pdftron.pdf.tools.Tool;
/*      */ import com.pdftron.pdf.tools.ToolManager;
/*      */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*      */ import com.pdftron.pdf.utils.AnalyticsParam;
/*      */ import com.pdftron.pdf.utils.PdfViewCtrlSettingsManager;
/*      */ import com.pdftron.pdf.utils.ShortcutHelper;
/*      */ import com.pdftron.pdf.utils.StampStatePopup;
/*      */ import com.pdftron.pdf.utils.Utils;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class AnnotationToolbar
/*      */   extends BaseToolbar
/*      */   implements ToolManager.ToolChangedListener, EditToolbarImpl.OnEditToolbarListener, AdvancedShapeCreate.OnEditToolbarListener, FormToolbar.FormToolbarListener
/*      */ {
/*      */   public static final int START_MODE_NORMAL_TOOLBAR = 0;
/*      */   public static final int START_MODE_EDIT_TOOLBAR = 1;
/*      */   public static final int START_MODE_FORM_TOOLBAR = 2;
/*      */   public static final int START_MODE_FILL_AND_SIGN_TOOLBAR = 3;
/*      */   public static final String PREF_KEY_NOTE = "pref_note";
/*      */   public static final String PREF_KEY_LINE = "pref_line";
/*      */   public static final String PREF_KEY_RECT = "pref_rect";
/*      */   public static final String PREF_KEY_TEXT = "pref_text";
/*      */   private static final int NUM_TABLET_NORMAL_STATE_ICONS = 16;
/*      */   private static final int NUM_PHONE_NORMAL_STATE_ICONS = 9;
/*      */   private static final int START_MODE_UNKNOWN = -1;
/*      */   private static final int ANIMATION_DURATION = 250;
/*      */   private EditToolbarImpl mEditToolbarImpl;
/*      */   private PDFViewCtrl mPdfViewCtrl;
/*      */   private AnnotToolbarOverflowPopupWindow mOverflowPopupWindow;
/*  113 */   private SparseIntArray mButtonsVisibility = AnnotationToolbarButtonId.getButtonVisibilityArray();
/*      */   
/*      */   private boolean mDismissAfterExitEdit;
/*      */   
/*      */   private String mStampState;
/*      */   
/*      */   private FormToolbar mFormToolbar;
/*      */   
/*      */   private AnnotStyleDialogFragment mAnnotStyleDialog;
/*      */   
/*      */   private StampStatePopup mStampStatePopup;
/*      */   
/*      */   private boolean mLayoutChanged;
/*      */   
/*      */   private boolean mForceUpdateView;
/*      */   
/*      */   private AnnotationToolbarListener mAnnotationToolbarListener;
/*      */   private UndoRedoPopupWindow.OnUndoRedoListener mOnUndoRedoListener;
/*      */   private SparseIntArray mButtonAnnotTypeMap;
/*      */   private HashMap<String, Integer> mVisibleAnnotTypeMap;
/*      */   private boolean mShouldExpand;
/*      */   private boolean mIsExpanded;
/*      */   private ArrayList<GroupedItem> mGroupItems;
/*      */   
/*      */   public AnnotationToolbar(@NonNull Context context) {
/*  138 */     this(context, (AttributeSet)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AnnotationToolbar(@NonNull Context context, @Nullable AttributeSet attrs) {
/*  145 */     this(context, attrs, R.attr.annotation_toolbar);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AnnotationToolbar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
/*  152 */     super(context, attrs, defStyleAttr);
/*  153 */     init(context, attrs, defStyleAttr, R.style.AnnotationToolbarStyle);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @RequiresApi(api = 21)
/*      */   public AnnotationToolbar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
/*  161 */     super(context, attrs, defStyleAttr, defStyleRes);
/*  162 */     init(context, attrs, defStyleAttr, defStyleRes);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void init(@NonNull Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
/*  168 */     TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AnnotationToolbar, defStyleAttr, defStyleRes);
/*      */     try {
/*  170 */       this.mToolbarBackgroundColor = typedArray.getColor(R.styleable.AnnotationToolbar_colorBackground, -16777216);
/*  171 */       this.mToolbarToolBackgroundColor = typedArray.getColor(R.styleable.AnnotationToolbar_colorToolBackground, -16777216);
/*  172 */       this.mToolbarToolIconColor = typedArray.getColor(R.styleable.AnnotationToolbar_colorToolIcon, -1);
/*  173 */       this.mToolbarCloseIconColor = typedArray.getColor(R.styleable.AnnotationToolbar_colorCloseIcon, -1);
/*      */     } finally {
/*  175 */       typedArray.recycle();
/*      */     } 
/*      */ 
/*      */     
/*  179 */     LayoutInflater.from(context).inflate(R.layout.controls_annotation_toolbar_layout, (ViewGroup)this, true);
/*  180 */     this.mButtonAnnotTypeMap = new SparseIntArray();
/*  181 */     this.mButtonAnnotTypeMap.put(R.id.controls_annotation_toolbar_tool_stickynote, 0);
/*  182 */     this.mButtonAnnotTypeMap.put(R.id.controls_annotation_toolbar_tool_text_highlight, 8);
/*  183 */     this.mButtonAnnotTypeMap.put(R.id.controls_annotation_toolbar_tool_text_strikeout, 11);
/*  184 */     this.mButtonAnnotTypeMap.put(R.id.controls_annotation_toolbar_tool_text_underline, 9);
/*  185 */     this.mButtonAnnotTypeMap.put(R.id.controls_annotation_toolbar_tool_text_squiggly, 10);
/*  186 */     this.mButtonAnnotTypeMap.put(R.id.controls_annotation_toolbar_tool_free_highlighter, 1004);
/*  187 */     this.mButtonAnnotTypeMap.put(R.id.controls_annotation_toolbar_tool_stamp, 1002);
/*  188 */     this.mButtonAnnotTypeMap.put(R.id.controls_annotation_toolbar_tool_freehand, 14);
/*  189 */     this.mButtonAnnotTypeMap.put(R.id.controls_annotation_toolbar_tool_eraser, 1003);
/*  190 */     this.mButtonAnnotTypeMap.put(R.id.controls_annotation_toolbar_tool_freetext, 2);
/*  191 */     this.mButtonAnnotTypeMap.put(R.id.controls_annotation_toolbar_tool_callout, 1007);
/*  192 */     this.mButtonAnnotTypeMap.put(R.id.controls_annotation_toolbar_tool_arrow, 1001);
/*  193 */     this.mButtonAnnotTypeMap.put(R.id.controls_annotation_toolbar_tool_ruler, 1006);
/*  194 */     this.mButtonAnnotTypeMap.put(R.id.controls_annotation_toolbar_tool_line, 3);
/*  195 */     this.mButtonAnnotTypeMap.put(R.id.controls_annotation_toolbar_tool_polyline, 7);
/*  196 */     this.mButtonAnnotTypeMap.put(R.id.controls_annotation_toolbar_tool_rectangle, 4);
/*  197 */     this.mButtonAnnotTypeMap.put(R.id.controls_annotation_toolbar_tool_oval, 5);
/*  198 */     this.mButtonAnnotTypeMap.put(R.id.controls_annotation_toolbar_tool_polygon, 6);
/*  199 */     this.mButtonAnnotTypeMap.put(R.id.controls_annotation_toolbar_tool_cloud, 1005);
/*  200 */     this.mButtonAnnotTypeMap.put(R.id.controls_annotation_toolbar_tool_sound, 17);
/*  201 */     this.mButtonAnnotTypeMap.put(R.id.controls_annotation_toolbar_tool_perimeter_measure, 1008);
/*  202 */     this.mButtonAnnotTypeMap.put(R.id.controls_annotation_toolbar_tool_area_measure, 1009);
/*      */ 
/*      */     
/*  205 */     String visibleAnnotTypesJsonStr = PdfViewCtrlSettingsManager.getAnnotToolbarVisibleAnnotTypes(context);
/*  206 */     this.mVisibleAnnotTypeMap = new HashMap<>();
/*  207 */     if (!Utils.isNullOrEmpty(visibleAnnotTypesJsonStr)) {
/*      */       try {
/*  209 */         JSONObject object = new JSONObject(visibleAnnotTypesJsonStr);
/*  210 */         if (object.has("pref_line")) {
/*  211 */           this.mVisibleAnnotTypeMap.put("pref_line", Integer.valueOf(object.getInt("pref_line")));
/*      */         }
/*  213 */         if (object.has("pref_rect")) {
/*  214 */           this.mVisibleAnnotTypeMap.put("pref_rect", Integer.valueOf(object.getInt("pref_rect")));
/*      */         }
/*  216 */         if (object.has("pref_text")) {
/*  217 */           this.mVisibleAnnotTypeMap.put("pref_text", Integer.valueOf(object.getInt("pref_text")));
/*      */         }
/*  219 */         if (object.has("pref_note")) {
/*  220 */           this.mVisibleAnnotTypeMap.put("pref_note", Integer.valueOf(object.getInt("pref_note")));
/*      */         }
/*  222 */       } catch (JSONException e) {
/*  223 */         AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*  228 */     this.mGroupItems = new ArrayList<>();
/*  229 */     this.mGroupItems.add(new GroupedItem(this, "pref_line", new int[] { 3, 1001, 7, 1006, 1008 }));
/*  230 */     this.mGroupItems.add(new GroupedItem(this, "pref_rect", new int[] { 4, 5, 6, 1005, 1009 }));
/*  231 */     this.mGroupItems.add(new GroupedItem(this, "pref_text", new int[] { 2, 1007 }));
/*  232 */     this.mGroupItems.add(new GroupedItem(this, "pref_note", new int[] { 0, 17 }));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setup(@NonNull ToolManager toolManager) {
/*  241 */     setup(toolManager, (UndoRedoPopupWindow.OnUndoRedoListener)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setup(@NonNull ToolManager toolManager, @Nullable UndoRedoPopupWindow.OnUndoRedoListener listener) {
/*  252 */     Context context = getContext();
/*  253 */     if (context == null) {
/*      */       return;
/*      */     }
/*      */     
/*  257 */     this.mToolManager = toolManager;
/*  258 */     this.mPdfViewCtrl = this.mToolManager.getPDFViewCtrl();
/*  259 */     this.mOnUndoRedoListener = listener;
/*      */ 
/*      */     
/*  262 */     SharedPreferences settings = Tool.getToolPreferences(context);
/*  263 */     this.mStampState = settings.getString("annotation_toolbar_signature_state", "signature");
/*  264 */     if ("stamper".equals(this.mStampState)) {
/*      */ 
/*      */       
/*  267 */       this.mStampState = "stamp";
/*  268 */       SharedPreferences.Editor editor = settings.edit();
/*  269 */       editor.putString("annotation_toolbar_signature_state", this.mStampState);
/*  270 */       editor.apply();
/*      */     } 
/*  272 */     checkStampState();
/*      */ 
/*      */     
/*  275 */     initButtons();
/*      */     
/*  277 */     this.mToolManager.addToolChangedListener(this);
/*      */     
/*  279 */     this.mToolManager.setTool(this.mToolManager.createTool((ToolManager.ToolModeBase)ToolManager.ToolMode.PAN, null));
/*      */     
/*  281 */     initSelectedButton();
/*      */     
/*  283 */     setVisibility(8);
/*      */   }
/*      */   
/*      */   private void initViews() {
/*  287 */     Context context = getContext();
/*  288 */     if (context == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  293 */     setBackgroundColor(this.mToolbarBackgroundColor);
/*      */ 
/*      */ 
/*      */     
/*  297 */     ArrayList<ToolItem> tools = new ArrayList<>();
/*  298 */     tools.add(new ToolItem(this, 0, R.id.controls_annotation_toolbar_tool_stickynote, true));
/*  299 */     tools.add(new ToolItem(this, 8, R.id.controls_annotation_toolbar_tool_text_highlight, true));
/*  300 */     tools.add(new ToolItem(this, 11, R.id.controls_annotation_toolbar_tool_text_strikeout, true));
/*  301 */     tools.add(new ToolItem(this, 9, R.id.controls_annotation_toolbar_tool_text_underline, true));
/*  302 */     tools.add(new ToolItem(this, 10, R.id.controls_annotation_toolbar_tool_text_squiggly, true));
/*  303 */     tools.add(new ToolItem(this, 1004, R.id.controls_annotation_toolbar_tool_free_highlighter, true));
/*  304 */     tools.add(new ToolItem(this, 1002, R.id.controls_annotation_toolbar_tool_stamp, (!this.mIsExpanded && getStampsEnabledCount() >= 2)));
/*  305 */     tools.add(new ToolItem(this, 14, R.id.controls_annotation_toolbar_tool_freehand, false));
/*  306 */     tools.add(new ToolItem(this, 1003, R.id.controls_annotation_toolbar_tool_eraser, true));
/*  307 */     tools.add(new ToolItem(this, 2, R.id.controls_annotation_toolbar_tool_freetext, true));
/*  308 */     tools.add(new ToolItem(this, 1007, R.id.controls_annotation_toolbar_tool_callout, true));
/*  309 */     tools.add(new ToolItem(this, -1, R.id.controls_annotation_toolbar_tool_image_stamper, R.drawable.ic_annotation_image_black_24dp, false));
/*  310 */     tools.add(new ToolItem(this, -1, R.id.controls_annotation_toolbar_tool_rubber_stamper, R.drawable.ic_annotation_stamp_black_24dp, false));
/*  311 */     tools.add(new ToolItem(this, 3, R.id.controls_annotation_toolbar_tool_line, true));
/*  312 */     tools.add(new ToolItem(this, 1001, R.id.controls_annotation_toolbar_tool_arrow, true));
/*  313 */     tools.add(new ToolItem(this, 1006, R.id.controls_annotation_toolbar_tool_ruler, true));
/*  314 */     tools.add(new ToolItem(this, 1008, R.id.controls_annotation_toolbar_tool_perimeter_measure, true));
/*  315 */     tools.add(new ToolItem(this, 1009, R.id.controls_annotation_toolbar_tool_area_measure, true));
/*  316 */     tools.add(new ToolItem(this, 7, R.id.controls_annotation_toolbar_tool_polyline, true));
/*  317 */     tools.add(new ToolItem(this, 4, R.id.controls_annotation_toolbar_tool_rectangle, true));
/*  318 */     tools.add(new ToolItem(this, 5, R.id.controls_annotation_toolbar_tool_oval, true));
/*  319 */     tools.add(new ToolItem(this, 6, R.id.controls_annotation_toolbar_tool_polygon, true));
/*  320 */     tools.add(new ToolItem(this, 1005, R.id.controls_annotation_toolbar_tool_cloud, true));
/*  321 */     tools.add(new ToolItem(this, -1, R.id.controls_annotation_toolbar_tool_multi_select, R.drawable.ic_select_rectangular_black_24dp, false));
/*  322 */     tools.add(new ToolItem(this, -1, R.id.controls_annotation_toolbar_tool_pan, R.drawable.ic_pan_black_24dp, false));
/*  323 */     tools.add(new ToolItem(this, -1, R.id.controls_annotation_toolbar_btn_close, R.drawable.ic_close_black_24dp, false, this.mToolbarCloseIconColor));
/*  324 */     tools.add(new ToolItem(this, -1, R.id.controls_annotation_toolbar_btn_more, R.drawable.ic_overflow_white_24dp, false));
/*  325 */     tools.add(new ToolItem(this, 17, R.id.controls_annotation_toolbar_tool_sound, R.drawable.ic_mic_black_24dp, true));
/*      */     
/*  327 */     int width = getToolWidth();
/*  328 */     int height = getToolHeight();
/*      */ 
/*      */     
/*  331 */     Drawable spinnerBitmapDrawable = getSpinnerBitmapDrawable(context, width, height, this.mToolbarToolBackgroundColor, this.mIsExpanded);
/*      */     
/*  333 */     Drawable normalBitmapDrawable = getNormalBitmapDrawable(context, width, height, this.mToolbarToolBackgroundColor, this.mIsExpanded);
/*      */ 
/*      */     
/*  336 */     for (ToolItem tool : tools) {
/*  337 */       setViewDrawable(context, tool.id, tool.spinner, tool.drawable, spinnerBitmapDrawable, normalBitmapDrawable, tool.color);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  342 */     updateStampBtnState();
/*  343 */     updateStampPopupSize();
/*      */   }
/*      */ 
/*      */   
/*      */   private int getStampsEnabledCount() {
/*  348 */     int stampsEnabledCounts = 0;
/*  349 */     if (!this.mToolManager.isToolModeDisabled(ToolManager.ToolMode.SIGNATURE)) {
/*  350 */       stampsEnabledCounts++;
/*      */     }
/*  352 */     if (!this.mToolManager.isToolModeDisabled(ToolManager.ToolMode.STAMPER)) {
/*  353 */       stampsEnabledCounts++;
/*      */     }
/*  355 */     if (!this.mToolManager.isToolModeDisabled(ToolManager.ToolMode.RUBBER_STAMPER)) {
/*  356 */       stampsEnabledCounts++;
/*      */     }
/*  358 */     return stampsEnabledCounts;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ToolManager getToolManager() {
/*  367 */     return this.mToolManager;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public HashMap<String, Integer> getVisibleAnnotTypeMap() {
/*  376 */     return this.mVisibleAnnotTypeMap;
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
/*      */   public ArrayList<GroupedItem> getGroupItems() {
/*  389 */     return this.mGroupItems;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void show() {
/*  396 */     show(0);
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
/*      */   public void show(int mode) {
/*  409 */     show(mode, (Annot)null, 0, (ToolManager.ToolMode)null, false);
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
/*      */   public void show(int mode, Annot inkAnnot, int pageNum, ToolManager.ToolMode toolMode, boolean dismissAfterExitEdit) {
/*  428 */     Context context = getContext();
/*  429 */     if (context == null) {
/*      */       return;
/*      */     }
/*      */     
/*  433 */     this.mForceUpdateView = true;
/*      */     
/*  435 */     if (getWidth() > 0 && getHeight() > 0) {
/*  436 */       initViews();
/*      */     }
/*      */     
/*  439 */     if (mode == 1) {
/*  440 */       if (toolMode != null) {
/*  441 */         this.mDismissAfterExitEdit = dismissAfterExitEdit;
/*  442 */         showEditToolbar(toolMode, inkAnnot, pageNum);
/*      */ 
/*      */         
/*  445 */         updateVisibleAnnotType(this.mToolManager.getTool().getCreateAnnotType());
/*      */       } 
/*  447 */     } else if (mode == 2) {
/*  448 */       showFormToolbar(toolMode, 0);
/*  449 */     } else if (mode == 3) {
/*  450 */       showFormToolbar(toolMode, 1);
/*      */     } else {
/*  452 */       updateButtonsVisibility();
/*  453 */       showAnnotationToolbar();
/*      */     } 
/*      */     
/*  456 */     if (getVisibility() != 0) {
/*  457 */       Transition slide = getOpenTransition();
/*  458 */       TransitionManager.beginDelayedTransition((ViewGroup)getParent(), slide);
/*  459 */       setVisibility(0);
/*      */     } 
/*      */     
/*  462 */     this.mShouldExpand = PdfViewCtrlSettingsManager.getDoubleRowToolbarInUse(context);
/*  463 */     if ((this.mShouldExpand && !this.mIsExpanded) || (!this.mShouldExpand && this.mIsExpanded)) {
/*  464 */       updateExpanded((getResources().getConfiguration()).orientation);
/*      */     }
/*      */     
/*  467 */     if (toolMode != null && mode != 1) {
/*  468 */       this.mSelectedToolId = getResourceIdOfTool(toolMode);
/*      */     }
/*      */     
/*  471 */     if (this.mSelectedToolId != -1) {
/*  472 */       selectTool((View)null, this.mSelectedToolId);
/*  473 */       this.mSelectedToolId = -1;
/*      */     } 
/*      */     
/*  476 */     AnalyticsHandlerAdapter.getInstance().sendTimedEvent(21);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setButtonStayDown(boolean value) {
/*  485 */     this.mButtonStayDown = value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onConfigurationChanged(Configuration newConfig) {
/*  493 */     super.onConfigurationChanged(newConfig);
/*      */     
/*  495 */     if (this.mOverflowPopupWindow != null && this.mOverflowPopupWindow.isShowing()) {
/*  496 */       this.mOverflowPopupWindow.dismiss();
/*      */     }
/*  498 */     if (this.mStampStatePopup != null && this.mStampStatePopup.isShowing()) {
/*  499 */       this.mStampStatePopup.dismiss();
/*      */     }
/*  501 */     updateExpanded(newConfig.orientation);
/*  502 */     this.mForceUpdateView = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
/*  510 */     super.onLayout(changed, left, top, right, bottom);
/*      */     
/*  512 */     if (getWidth() == 0 || getHeight() == 0) {
/*  513 */       this.mLayoutChanged = false;
/*      */       
/*      */       return;
/*      */     } 
/*  517 */     if (this.mForceUpdateView && !changed) {
/*  518 */       this.mForceUpdateView = false;
/*      */ 
/*      */       
/*  521 */       initViews();
/*      */     } 
/*      */     
/*  524 */     if (changed) {
/*  525 */       this.mForceUpdateView = false;
/*  526 */       initViews();
/*      */       
/*  528 */       if (!this.mLayoutChanged) {
/*  529 */         updateButtonsVisibility();
/*  530 */         initSelectedButton();
/*      */       } 
/*      */     } 
/*  533 */     this.mLayoutChanged = changed;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void close() {
/*  540 */     closePopups();
/*      */     
/*  542 */     if (isInEditMode()) {
/*  543 */       this.mEditToolbarImpl.close();
/*  544 */       setBackgroundColor(this.mToolbarBackgroundColor);
/*      */       
/*      */       return;
/*      */     } 
/*  548 */     if (this.mToolManager == null) {
/*      */       return;
/*      */     }
/*      */     
/*  552 */     this.mToolManager.onClose();
/*  553 */     reset();
/*  554 */     ((Tool)this.mToolManager.getTool()).setForceSameNextToolMode(false);
/*      */     
/*  556 */     Transition slide = (new Slide(48)).setDuration(250L);
/*  557 */     slide.addListener(new Transition.TransitionListener()
/*      */         {
/*      */           public void onTransitionStart(@NonNull Transition transition) {}
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public void onTransitionEnd(@NonNull Transition transition) {
/*  565 */             if (AnnotationToolbar.this.mFormToolbar != null) {
/*  566 */               AnnotationToolbar.this.mFormToolbar.setVisibility(8);
/*      */             }
/*  568 */             if (AnnotationToolbar.this.mAnnotationToolbarListener != null) {
/*  569 */               AnnotationToolbar.this.mAnnotationToolbarListener.onAnnotationToolbarClosed();
/*      */             }
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public void onTransitionCancel(@NonNull Transition transition) {}
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public void onTransitionPause(@NonNull Transition transition) {}
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public void onTransitionResume(@NonNull Transition transition) {}
/*      */         });
/*  589 */     TransitionManager.beginDelayedTransition((ViewGroup)getParent(), slide);
/*      */     
/*  591 */     setVisibility(8);
/*      */     
/*  593 */     saveVisibleAnnotTypes();
/*  594 */     AnalyticsHandlerAdapter.getInstance().endTimedEvent(21);
/*      */   }
/*      */   
/*      */   private void reset() {
/*  598 */     if (this.mToolManager == null || this.mPdfViewCtrl == null) {
/*      */       return;
/*      */     }
/*  601 */     this.mToolManager.setTool(this.mToolManager.createTool((ToolManager.ToolModeBase)ToolManager.ToolMode.PAN, null));
/*  602 */     selectButton(getResourceIdOfTool(ToolManager.ToolMode.PAN));
/*      */     
/*  604 */     ((Tool)this.mToolManager.getTool()).setForceSameNextToolMode(this.mButtonStayDown);
/*  605 */     this.mPdfViewCtrl.clearSelection();
/*      */     
/*  607 */     this.mDismissAfterExitEdit = false;
/*      */   }
/*      */   
/*      */   private Transition getOpenTransition() {
/*  611 */     Transition slide = (new Slide(48)).setDuration(250L);
/*  612 */     slide.addListener(new Transition.TransitionListener()
/*      */         {
/*      */           public void onTransitionStart(@NonNull Transition transition) {}
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public void onTransitionEnd(@NonNull Transition transition) {
/*  620 */             if (AnnotationToolbar.this.mAnnotationToolbarListener != null) {
/*  621 */               AnnotationToolbar.this.mAnnotationToolbarListener.onAnnotationToolbarShown();
/*      */             }
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public void onTransitionCancel(@NonNull Transition transition) {}
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public void onTransitionPause(@NonNull Transition transition) {}
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public void onTransitionResume(@NonNull Transition transition) {}
/*      */         });
/*  640 */     return slide;
/*      */   }
/*      */   
/*      */   private void showAnnotationToolbar() {
/*  644 */     Transition slide = getOpenTransition();
/*  645 */     TransitionManager.beginDelayedTransition((ViewGroup)getParent(), slide);
/*  646 */     findViewById(R.id.controls_annotation_toolbar_state_normal).setVisibility(0);
/*      */   }
/*      */   
/*      */   private void hideAnnotationToolbar() {
/*  650 */     findViewById(R.id.controls_annotation_toolbar_state_normal).setVisibility(8);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateButtonsVisibility() {
/*  660 */     Context context = getContext();
/*  661 */     if (context == null || this.mToolManager == null || this.mPdfViewCtrl == null) {
/*      */       return;
/*      */     }
/*      */     
/*  665 */     boolean hasAllTools = hasAllTool();
/*  666 */     ArrayList<ToolManager.ToolMode> precedence = this.mToolManager.getAnnotToolbarPrecedence();
/*  667 */     boolean hasPrecedence = (precedence != null && precedence.size() > 0);
/*  668 */     boolean needsPrecedenceCheck = (!hasAllTools && hasPrecedence);
/*      */     
/*  670 */     for (View button : this.mButtons) {
/*  671 */       int viewId = button.getId();
/*  672 */       ToolManager.ToolMode toolMode = ToolConfig.getInstance().getToolModeByAnnotationToolbarItemId(viewId);
/*  673 */       safeShowHideButton(viewId, needsPrecedenceCheck, (!hasPrecedence || precedence.contains(toolMode)), 0);
/*      */       
/*  675 */       int idx = this.mButtonsVisibility.indexOfKey(viewId);
/*  676 */       if (idx >= 0) {
/*  677 */         button.setVisibility(this.mButtonsVisibility.valueAt(idx));
/*      */       }
/*      */     } 
/*  680 */     int visibility = hasAllTools ? 0 : 8;
/*  681 */     if (!hasPrecedence) {
/*      */       
/*  683 */       safeShowHideButton(R.id.controls_annotation_toolbar_tool_text_squiggly, visibility);
/*  684 */       safeShowHideButton(R.id.controls_annotation_toolbar_tool_text_strikeout, visibility);
/*  685 */       safeShowHideButton(R.id.controls_annotation_toolbar_tool_eraser, visibility);
/*  686 */       safeShowHideButton(R.id.controls_annotation_toolbar_tool_free_highlighter, visibility);
/*  687 */       safeShowHideButton(R.id.controls_annotation_toolbar_tool_multi_select, visibility);
/*  688 */       safeShowHideButton(R.id.controls_annotation_toolbar_tool_rubber_stamper, this.mIsExpanded ? visibility : 8);
/*  689 */       safeShowHideButton(R.id.controls_annotation_toolbar_tool_image_stamper, this.mIsExpanded ? visibility : 8);
/*      */     } 
/*      */     
/*  692 */     for (GroupedItem item : this.mGroupItems) {
/*  693 */       boolean groupVisible = (hasAllTools || (!hasPrecedence && (item.getPrefKey().equals("pref_text") || item.getPrefKey().equals("pref_note"))));
/*  694 */       for (Iterator<Integer> iterator = item.getButtonIds().iterator(); iterator.hasNext(); ) { int buttonId = ((Integer)iterator.next()).intValue();
/*  695 */         ToolManager.ToolMode toolMode = ToolConfig.getInstance().getToolModeByAnnotationToolbarItemId(buttonId);
/*  696 */         if (hasPrecedence && precedence.contains(toolMode)) {
/*  697 */           groupVisible = true;
/*      */         }
/*  699 */         View v = findViewById(buttonId);
/*  700 */         if (v != null) {
/*  701 */           v.setVisibility(8);
/*      */         } }
/*      */       
/*  704 */       if (groupVisible) {
/*      */ 
/*      */         
/*  707 */         int visibleButtonId = item.getVisibleButtonId();
/*  708 */         if (visibleButtonId != -1) {
/*  709 */           safeShowHideButton(visibleButtonId, needsPrecedenceCheck, true, 0);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  714 */     if (getStampsEnabledCount() == 0) {
/*  715 */       findViewById(R.id.controls_annotation_toolbar_tool_stamp).setVisibility(8);
/*      */     }
/*  717 */     if (!canShowMoreButton()) {
/*  718 */       findViewById(R.id.controls_annotation_toolbar_btn_more).setVisibility(8);
/*      */     }
/*      */   }
/*      */   
/*      */   private void safeShowHideButton(int viewId, int visibility) {
/*  723 */     safeShowHideButton(viewId, false, true, visibility);
/*      */   }
/*      */   
/*      */   private void safeShowHideButton(int viewId, boolean needsPrecedenceCheck, boolean groupVisible, int visibility) {
/*  727 */     View button = findViewById(viewId);
/*  728 */     ToolManager.ToolMode toolMode = ToolConfig.getInstance().getToolModeByAnnotationToolbarItemId(viewId);
/*  729 */     if (toolMode != null && button != null) {
/*  730 */       if (this.mToolManager.isToolModeDisabled(toolMode)) {
/*  731 */         button.setVisibility(8);
/*      */       }
/*  733 */       else if (needsPrecedenceCheck) {
/*      */         
/*  735 */         if (visibility == 0) {
/*  736 */           button.setVisibility(groupVisible ? 0 : 8);
/*      */         } else {
/*  738 */           button.setVisibility(visibility);
/*      */         } 
/*      */       } else {
/*  741 */         button.setVisibility(visibility);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasAllTool() {
/*  748 */     Context context = getContext();
/*  749 */     return (context != null && (Utils.isTablet(context) || this.mIsExpanded || (Utils.isLandscape(context) && getWidth() > Utils.getRealScreenHeight(context))));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void closePopups() {
/*  756 */     if (this.mAnnotStyleDialog != null) {
/*  757 */       this.mAnnotStyleDialog.dismiss();
/*  758 */       this.mAnnotStyleDialog = null;
/*      */     } 
/*  760 */     if (this.mStampStatePopup != null && this.mStampStatePopup.isShowing()) {
/*  761 */       this.mStampStatePopup.dismiss();
/*      */     }
/*  763 */     if (this.mOverflowPopupWindow != null && this.mOverflowPopupWindow.isShowing()) {
/*  764 */       this.mOverflowPopupWindow.dismiss();
/*      */     }
/*      */   }
/*      */   
/*      */   private void initButtons() {
/*  769 */     Context context = getContext();
/*  770 */     if (context == null || this.mToolManager == null || this.mPdfViewCtrl == null) {
/*      */       return;
/*      */     }
/*      */     
/*  774 */     updateStampBtnState();
/*  775 */     initializeButtons();
/*      */   }
/*      */   
/*      */   private void updateStampBtnState() {
/*  779 */     Context context = getContext();
/*  780 */     if (context == null) {
/*      */       return;
/*      */     }
/*      */     
/*      */     try {
/*      */       int imageResId;
/*  786 */       if (this.mIsExpanded) {
/*  787 */         imageResId = R.drawable.ic_annotation_signature_black_24dp;
/*      */       } else {
/*  789 */         switch (this.mStampState) {
/*      */           case "signature":
/*  791 */             imageResId = R.drawable.ic_annotation_signature_black_24dp;
/*      */             break;
/*      */           case "rubber_stamp":
/*  794 */             imageResId = R.drawable.ic_annotation_stamp_black_24dp;
/*      */             break;
/*      */           case "stamp":
/*  797 */             imageResId = R.drawable.ic_annotation_image_black_24dp;
/*      */             break;
/*      */           default:
/*      */             return;
/*      */         } 
/*      */       } 
/*  803 */       int iconColor = this.mToolbarToolIconColor;
/*  804 */       StateListDrawable stateListDrawable = Utils.createImageDrawableSelector(context, imageResId, iconColor);
/*  805 */       ((AppCompatImageButton)findViewById(R.id.controls_annotation_toolbar_tool_stamp)).setImageDrawable((Drawable)stateListDrawable);
/*  806 */     } catch (Exception e) {
/*  807 */       Exception exception1; AnalyticsHandlerAdapter.getInstance().sendException(exception1);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void initSelectedButton() {
/*  812 */     if (this.mToolManager == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  817 */     ToolManager.ToolMode toolMode = ToolManager.getDefaultToolMode(this.mToolManager.getTool().getToolMode());
/*  818 */     selectButton(getResourceIdOfTool(toolMode));
/*      */   }
/*      */   
/*      */   private int getResourceIdOfTool(ToolManager.ToolMode toolMode) {
/*  822 */     switch (toolMode) {
/*      */       case LINE_CREATE:
/*  824 */         return R.id.controls_annotation_toolbar_tool_line;
/*      */       case ARROW_CREATE:
/*  826 */         return R.id.controls_annotation_toolbar_tool_arrow;
/*      */       case RULER_CREATE:
/*  828 */         return R.id.controls_annotation_toolbar_tool_ruler;
/*      */       case PERIMETER_MEASURE_CREATE:
/*  830 */         return R.id.controls_annotation_toolbar_tool_perimeter_measure;
/*      */       case AREA_MEASURE_CREATE:
/*  832 */         return R.id.controls_annotation_toolbar_tool_area_measure;
/*      */       case POLYLINE_CREATE:
/*  834 */         return R.id.controls_annotation_toolbar_tool_polyline;
/*      */       case RECT_CREATE:
/*  836 */         return R.id.controls_annotation_toolbar_tool_rectangle;
/*      */       case OVAL_CREATE:
/*  838 */         return R.id.controls_annotation_toolbar_tool_oval;
/*      */       case POLYGON_CREATE:
/*  840 */         return R.id.controls_annotation_toolbar_tool_polygon;
/*      */       case CLOUD_CREATE:
/*  842 */         return R.id.controls_annotation_toolbar_tool_cloud;
/*      */       case INK_ERASER:
/*  844 */         return R.id.controls_annotation_toolbar_tool_eraser;
/*      */       case TEXT_ANNOT_CREATE:
/*  846 */         return R.id.controls_annotation_toolbar_tool_stickynote;
/*      */       case SOUND_CREATE:
/*  848 */         return R.id.controls_annotation_toolbar_tool_sound;
/*      */       case TEXT_CREATE:
/*  850 */         return R.id.controls_annotation_toolbar_tool_freetext;
/*      */       case CALLOUT_CREATE:
/*  852 */         return R.id.controls_annotation_toolbar_tool_callout;
/*      */       case TEXT_UNDERLINE:
/*  854 */         return R.id.controls_annotation_toolbar_tool_text_underline;
/*      */       case TEXT_HIGHLIGHT:
/*  856 */         return R.id.controls_annotation_toolbar_tool_text_highlight;
/*      */       case TEXT_SQUIGGLY:
/*  858 */         return R.id.controls_annotation_toolbar_tool_text_squiggly;
/*      */       case TEXT_STRIKEOUT:
/*  860 */         return R.id.controls_annotation_toolbar_tool_text_strikeout;
/*      */       case FREE_HIGHLIGHTER:
/*  862 */         return R.id.controls_annotation_toolbar_tool_free_highlighter;
/*      */       case ANNOT_EDIT_RECT_GROUP:
/*  864 */         return R.id.controls_annotation_toolbar_tool_multi_select;
/*      */       case SIGNATURE:
/*  866 */         return R.id.controls_annotation_toolbar_tool_stamp;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case STAMPER:
/*  873 */         return this.mIsExpanded ? R.id.controls_annotation_toolbar_tool_image_stamper : R.id.controls_annotation_toolbar_tool_stamp;
/*      */       
/*      */       case RUBBER_STAMPER:
/*  876 */         return this.mIsExpanded ? R.id.controls_annotation_toolbar_tool_rubber_stamper : R.id.controls_annotation_toolbar_tool_stamp;
/*      */     } 
/*      */ 
/*      */     
/*  880 */     return R.id.controls_annotation_toolbar_tool_pan;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void selectTool(View view, int id) {
/*  891 */     Context context = getContext();
/*  892 */     if (context == null || this.mToolManager == null) {
/*      */       return;
/*      */     }
/*      */     
/*  896 */     ToolManager.ToolMode annotMode = ToolManager.getDefaultToolMode(this.mToolManager.getTool().getToolMode());
/*  897 */     if (Utils.isAnnotationHandlerToolMode(annotMode) || annotMode == ToolManager.ToolMode.TEXT_CREATE || annotMode == ToolManager.ToolMode.CALLOUT_CREATE || annotMode == ToolManager.ToolMode.PAN)
/*      */     {
/*      */ 
/*      */       
/*  901 */       this.mToolManager.onClose();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  908 */     View button = findViewById(id);
/*  909 */     if (null == button) {
/*      */       return;
/*      */     }
/*  912 */     int annotType = getAnnotTypeFromButtonId(id);
/*      */     
/*  914 */     ToolManager.ToolMode toolMode = null;
/*  915 */     int analyticId = -1;
/*  916 */     Set<String> whiteListFonts = null;
/*  917 */     if (id == R.id.controls_annotation_toolbar_tool_line) {
/*  918 */       toolMode = ToolManager.ToolMode.LINE_CREATE;
/*  919 */       analyticId = 1;
/*  920 */     } else if (id == R.id.controls_annotation_toolbar_tool_arrow) {
/*  921 */       toolMode = ToolManager.ToolMode.ARROW_CREATE;
/*  922 */       analyticId = 6;
/*  923 */     } else if (id == R.id.controls_annotation_toolbar_tool_ruler) {
/*  924 */       toolMode = ToolManager.ToolMode.RULER_CREATE;
/*  925 */       analyticId = 25;
/*  926 */     } else if (id == R.id.controls_annotation_toolbar_tool_perimeter_measure) {
/*  927 */       toolMode = ToolManager.ToolMode.PERIMETER_MEASURE_CREATE;
/*  928 */       analyticId = 29;
/*  929 */     } else if (id == R.id.controls_annotation_toolbar_tool_area_measure) {
/*  930 */       toolMode = ToolManager.ToolMode.AREA_MEASURE_CREATE;
/*  931 */       analyticId = 30;
/*  932 */     } else if (id == R.id.controls_annotation_toolbar_tool_polyline) {
/*  933 */       toolMode = ToolManager.ToolMode.POLYLINE_CREATE;
/*  934 */       analyticId = 21;
/*  935 */     } else if (id == R.id.controls_annotation_toolbar_tool_rectangle) {
/*  936 */       toolMode = ToolManager.ToolMode.RECT_CREATE;
/*  937 */       analyticId = 2;
/*  938 */     } else if (id == R.id.controls_annotation_toolbar_tool_oval) {
/*  939 */       toolMode = ToolManager.ToolMode.OVAL_CREATE;
/*  940 */       analyticId = 3;
/*  941 */     } else if (id == R.id.controls_annotation_toolbar_tool_polygon) {
/*  942 */       toolMode = ToolManager.ToolMode.POLYGON_CREATE;
/*  943 */       analyticId = 22;
/*  944 */     } else if (id == R.id.controls_annotation_toolbar_tool_cloud) {
/*  945 */       toolMode = ToolManager.ToolMode.CLOUD_CREATE;
/*  946 */       analyticId = 23;
/*  947 */     } else if (id == R.id.controls_annotation_toolbar_tool_eraser) {
/*  948 */       toolMode = ToolManager.ToolMode.INK_ERASER;
/*  949 */       analyticId = 4;
/*  950 */     } else if (id == R.id.controls_annotation_toolbar_tool_free_highlighter) {
/*  951 */       toolMode = ToolManager.ToolMode.FREE_HIGHLIGHTER;
/*  952 */       analyticId = 18;
/*  953 */     } else if (id == R.id.controls_annotation_toolbar_tool_stickynote) {
/*  954 */       toolMode = ToolManager.ToolMode.TEXT_ANNOT_CREATE;
/*  955 */       analyticId = 7;
/*  956 */     } else if (id == R.id.controls_annotation_toolbar_tool_sound) {
/*  957 */       toolMode = ToolManager.ToolMode.SOUND_CREATE;
/*  958 */       analyticId = 27;
/*  959 */     } else if (id == R.id.controls_annotation_toolbar_tool_freetext) {
/*  960 */       toolMode = ToolManager.ToolMode.TEXT_CREATE;
/*  961 */       analyticId = 8;
/*  962 */       whiteListFonts = this.mToolManager.getFreeTextFonts();
/*  963 */     } else if (id == R.id.controls_annotation_toolbar_tool_callout) {
/*  964 */       toolMode = ToolManager.ToolMode.CALLOUT_CREATE;
/*  965 */       analyticId = 26;
/*  966 */       whiteListFonts = this.mToolManager.getFreeTextFonts();
/*  967 */     } else if (id == R.id.controls_annotation_toolbar_tool_text_highlight) {
/*  968 */       toolMode = ToolManager.ToolMode.TEXT_HIGHLIGHT;
/*  969 */       analyticId = 13;
/*  970 */     } else if (id == R.id.controls_annotation_toolbar_tool_text_underline) {
/*  971 */       toolMode = ToolManager.ToolMode.TEXT_UNDERLINE;
/*  972 */       analyticId = 12;
/*  973 */     } else if (id == R.id.controls_annotation_toolbar_tool_text_squiggly) {
/*  974 */       toolMode = ToolManager.ToolMode.TEXT_SQUIGGLY;
/*  975 */       analyticId = 14;
/*  976 */     } else if (id == R.id.controls_annotation_toolbar_tool_text_strikeout) {
/*  977 */       toolMode = ToolManager.ToolMode.TEXT_STRIKEOUT;
/*  978 */       analyticId = 15;
/*      */     } 
/*  980 */     boolean sendAnalytics = (this.mSelectedButtonId != id);
/*  981 */     if (toolMode != null) {
/*  982 */       if (this.mSelectedButtonId == id) {
/*  983 */         AnnotStyle annotStyle = getCustomAnnotStyle(annotType);
/*  984 */         if (annotStyle == null) {
/*      */           return;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  991 */         AnnotStyleDialogFragment popupWindow = (new AnnotStyleDialogFragment.Builder(annotStyle)).setAnchorView(button).setMoreAnnotTypes(getMoreAnnotTypes(annotType)).setWhiteListFont(whiteListFonts).build();
/*  992 */         showAnnotPropertyPopup(popupWindow, view, analyticId);
/*      */       } 
/*  994 */       this.mToolManager.setTool(this.mToolManager.createTool((ToolManager.ToolModeBase)toolMode, this.mToolManager.getTool()));
/*  995 */       ToolManager.Tool tool = this.mToolManager.getTool();
/*  996 */       ((Tool)tool).setForceSameNextToolMode(this.mButtonStayDown);
/*  997 */       selectButton(id);
/*  998 */       this.mEventAction = true;
/*  999 */       if (tool instanceof AdvancedShapeCreate) {
/* 1000 */         ((AdvancedShapeCreate)tool).setOnEditToolbarListener(this);
/*      */       }
/* 1002 */     } else if (id == R.id.controls_annotation_toolbar_tool_freehand) {
/* 1003 */       showEditToolbar(ToolManager.ToolMode.INK_CREATE);
/* 1004 */       this.mEventAction = true;
/* 1005 */       analyticId = 5;
/* 1006 */     } else if (id == R.id.controls_annotation_toolbar_tool_stamp || id == R.id.controls_annotation_toolbar_tool_image_stamper || id == R.id.controls_annotation_toolbar_tool_rubber_stamper) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1011 */       if (getStampsEnabledCount() >= 2) {
/* 1012 */         boolean imageStamperEnabled = !this.mToolManager.isToolModeDisabled(ToolManager.ToolMode.STAMPER);
/* 1013 */         boolean rubberStamperEnabled = !this.mToolManager.isToolModeDisabled(ToolManager.ToolMode.RUBBER_STAMPER);
/* 1014 */         if (!this.mIsExpanded && (imageStamperEnabled || rubberStamperEnabled) && 
/* 1015 */           this.mSelectedButtonId == id) {
/* 1016 */           showStampStatePopup(id, view);
/*      */         }
/*      */       } 
/*      */       
/* 1020 */       if (id == R.id.controls_annotation_toolbar_tool_stamp && (this.mIsExpanded || "signature".equals(this.mStampState))) {
/* 1021 */         analyticId = 9;
/* 1022 */         this.mToolManager.setTool(this.mToolManager.createTool((ToolManager.ToolModeBase)ToolManager.ToolMode.SIGNATURE, this.mToolManager.getTool()));
/* 1023 */         ((Tool)this.mToolManager.getTool()).setForceSameNextToolMode(this.mButtonStayDown);
/* 1024 */         selectButton(id);
/* 1025 */         this.mEventAction = true;
/* 1026 */       } else if (id == R.id.controls_annotation_toolbar_tool_image_stamper || (!this.mIsExpanded && "stamp"
/* 1027 */         .equals(this.mStampState))) {
/* 1028 */         analyticId = 10;
/* 1029 */         this.mToolManager.setTool(this.mToolManager.createTool((ToolManager.ToolModeBase)ToolManager.ToolMode.STAMPER, this.mToolManager.getTool()));
/* 1030 */         ((Tool)this.mToolManager.getTool()).setForceSameNextToolMode(this.mButtonStayDown);
/* 1031 */         selectButton(id);
/* 1032 */         this.mEventAction = true;
/* 1033 */       } else if (id == R.id.controls_annotation_toolbar_tool_rubber_stamper || (!this.mIsExpanded && "rubber_stamp"
/* 1034 */         .equals(this.mStampState))) {
/* 1035 */         analyticId = 11;
/* 1036 */         this.mToolManager.setTool(this.mToolManager.createTool((ToolManager.ToolModeBase)ToolManager.ToolMode.RUBBER_STAMPER, this.mToolManager.getTool()));
/* 1037 */         ((Tool)this.mToolManager.getTool()).setForceSameNextToolMode(this.mButtonStayDown);
/* 1038 */         selectButton(id);
/* 1039 */         this.mEventAction = true;
/*      */       } 
/* 1041 */     } else if (id == R.id.controls_annotation_toolbar_tool_multi_select) {
/* 1042 */       analyticId = 24;
/* 1043 */       this.mToolManager.setTool(this.mToolManager.createTool((ToolManager.ToolModeBase)ToolManager.ToolMode.ANNOT_EDIT_RECT_GROUP, null));
/* 1044 */       ((Tool)this.mToolManager.getTool()).setForceSameNextToolMode(this.mButtonStayDown);
/* 1045 */       selectButton(id);
/* 1046 */       this.mEventAction = true;
/* 1047 */     } else if (id == R.id.controls_annotation_toolbar_tool_pan) {
/* 1048 */       analyticId = 16;
/* 1049 */       this.mToolManager.setTool(this.mToolManager.createTool((ToolManager.ToolModeBase)ToolManager.ToolMode.PAN, null));
/* 1050 */       selectButton(id);
/* 1051 */     } else if (id == R.id.controls_annotation_toolbar_btn_close) {
/* 1052 */       sendAnalytics = false;
/* 1053 */       close();
/* 1054 */       AnalyticsHandlerAdapter.getInstance().sendEvent(22, 
/* 1055 */           AnalyticsParam.noActionParam(this.mEventAction));
/* 1056 */       this.mEventAction = false;
/* 1057 */     } else if (id == R.id.controls_annotation_toolbar_btn_more) {
/* 1058 */       if (this.mOverflowPopupWindow != null && this.mOverflowPopupWindow.isShowing()) {
/* 1059 */         this.mOverflowPopupWindow.dismiss();
/*      */       }
/* 1061 */       this
/*      */ 
/*      */         
/* 1064 */         .mOverflowPopupWindow = new AnnotToolbarOverflowPopupWindow(context, this.mToolManager.getUndoRedoManger(), this.mToolManager.isShowUndoRedo() ? this.mOnUndoRedoListener : null, this);
/*      */ 
/*      */       
/*      */       try {
/* 1068 */         this.mOverflowPopupWindow.showAsDropDown(view);
/* 1069 */       } catch (Exception ex) {
/* 1070 */         AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*      */       } 
/* 1072 */       sendAnalytics = false;
/*      */     } 
/*      */     
/* 1075 */     if (sendAnalytics) {
/* 1076 */       AnalyticsHandlerAdapter.getInstance().sendEvent(23, AnalyticsParam.annotationToolbarParam(analyticId));
/*      */     }
/*      */   }
/*      */   
/*      */   private void saveVisibleAnnotTypes() {
/* 1081 */     Context context = getContext();
/* 1082 */     if (context == null || this.mVisibleAnnotTypeMap == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1086 */     JSONObject object = new JSONObject();
/* 1087 */     for (Map.Entry<String, Integer> entry : this.mVisibleAnnotTypeMap.entrySet()) {
/*      */       try {
/* 1089 */         object.put(entry.getKey(), entry.getValue());
/* 1090 */       } catch (JSONException e) {
/* 1091 */         e.printStackTrace();
/*      */       } 
/*      */     } 
/* 1094 */     PdfViewCtrlSettingsManager.setAnnotToolbarVisibleAnnotTypes(context, object.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void toolChanged(ToolManager.Tool newTool, ToolManager.Tool oldTool) {
/* 1103 */     if (newTool == null || !isShowing()) {
/*      */       return;
/*      */     }
/*      */     
/* 1107 */     boolean canSelectButton = false;
/*      */     
/* 1109 */     if (oldTool != null && oldTool instanceof Tool && newTool instanceof Tool) {
/* 1110 */       Tool oldT = (Tool)oldTool;
/* 1111 */       Tool newT = (Tool)newTool;
/* 1112 */       canSelectButton = (!oldT.isForceSameNextToolMode() || !newT.isEditAnnotTool());
/*      */       
/* 1114 */       if (isInEditMode() && newTool instanceof FreehandCreate) {
/* 1115 */         ((FreehandCreate)newTool).setFromEditToolbar(true);
/*      */       }
/*      */     } 
/*      */     
/* 1119 */     if (canSelectButton) {
/* 1120 */       ToolManager.ToolMode newToolMode = ToolManager.getDefaultToolMode(newTool.getToolMode());
/*      */ 
/*      */       
/* 1123 */       updateVisibleAnnotType(newTool.getCreateAnnotType());
/* 1124 */       updateButtonsVisibility();
/* 1125 */       selectButton(getResourceIdOfTool(newToolMode));
/* 1126 */       ToolManager.ToolModeBase toolModeBase = newTool.getToolMode();
/*      */       
/* 1128 */       if (ToolManager.ToolMode.SIGNATURE.equals(toolModeBase)) {
/* 1129 */         this.mStampState = "signature";
/* 1130 */       } else if (ToolManager.ToolMode.RUBBER_STAMPER.equals(toolModeBase)) {
/* 1131 */         this.mStampState = "rubber_stamp";
/* 1132 */       } else if (ToolManager.ToolMode.STAMPER.equals(toolModeBase)) {
/* 1133 */         this.mStampState = "stamp";
/*      */       } 
/*      */ 
/*      */       
/* 1137 */       if (this.mSelectedButtonId == R.id.controls_annotation_toolbar_tool_stamp) {
/* 1138 */         updateStampBtnState();
/*      */       }
/*      */     } 
/*      */     
/* 1142 */     if (newTool instanceof AdvancedShapeCreate) {
/* 1143 */       ((AdvancedShapeCreate)newTool).setOnEditToolbarListener(this);
/*      */     }
/*      */   }
/*      */   
/*      */   private void showAnnotPropertyPopup(final AnnotStyleDialogFragment popupWindow, View view, int annotToolId) {
/* 1148 */     if (view == null || popupWindow == null) {
/*      */       return;
/*      */     }
/* 1151 */     if (this.mToolManager.isSkipNextTapEvent()) {
/* 1152 */       this.mToolManager.resetSkipNextTapEvent();
/*      */       
/*      */       return;
/*      */     } 
/* 1156 */     if (this.mAnnotStyleDialog != null) {
/*      */       return;
/*      */     }
/*      */     
/* 1160 */     this.mAnnotStyleDialog = popupWindow;
/* 1161 */     this.mAnnotStyleDialog.setCanShowRichContentSwitch(this.mToolManager.isShowRichContentOption());
/* 1162 */     this.mAnnotStyleDialog.setCanShowPressureSwitch(true);
/*      */     
/* 1164 */     popupWindow.setOnDismissListener(new DialogInterface.OnDismissListener()
/*      */         {
/*      */           public void onDismiss(DialogInterface dialogInterface) {
/* 1167 */             AnnotationToolbar.this.mAnnotStyleDialog = null;
/*      */             
/* 1169 */             Context context = AnnotationToolbar.this.getContext();
/* 1170 */             if (context == null || AnnotationToolbar.this.mToolManager == null) {
/*      */               return;
/*      */             }
/*      */             
/* 1174 */             AnnotStyle annotStyle = popupWindow.getAnnotStyle();
/* 1175 */             ToolStyleConfig.getInstance().saveAnnotStyle(context, annotStyle, "");
/*      */             
/* 1177 */             Tool tool = (Tool)AnnotationToolbar.this.mToolManager.getTool();
/* 1178 */             if (tool != null) {
/* 1179 */               tool.setupAnnotProperty(annotStyle);
/*      */             }
/*      */           }
/*      */         });
/* 1183 */     popupWindow.setOnMoreAnnotTypesClickListener(new AnnotStyleView.OnMoreAnnotTypeClickedListener()
/*      */         {
/*      */           public void onAnnotTypeClicked(int annotType) {
/* 1186 */             Context context = AnnotationToolbar.this.getContext();
/* 1187 */             if (context == null) {
/*      */               return;
/*      */             }
/*      */             
/* 1191 */             popupWindow.saveAnnotStyles();
/* 1192 */             ToolStyleConfig.getInstance().saveAnnotStyle(context, popupWindow.getAnnotStyle(), "");
/* 1193 */             AnnotationToolbar.this.updateAnnotStyleDialog(popupWindow, annotType);
/*      */           }
/*      */         });
/*      */     
/* 1197 */     FragmentActivity activity = null;
/* 1198 */     if (getContext() instanceof FragmentActivity) {
/* 1199 */       activity = (FragmentActivity)getContext();
/* 1200 */     } else if (this.mToolManager.getCurrentActivity() != null) {
/* 1201 */       activity = this.mToolManager.getCurrentActivity();
/*      */     } 
/* 1203 */     if (activity == null) {
/* 1204 */       AnalyticsHandlerAdapter.getInstance().sendException(new Exception("AnnotationToolbar is not attached to with an Activity"));
/*      */       return;
/*      */     } 
/* 1207 */     popupWindow.show(activity.getSupportFragmentManager(), 2, 
/* 1208 */         AnalyticsHandlerAdapter.getInstance().getAnnotationTool(annotToolId));
/*      */   }
/*      */   
/*      */   private void updateAnnotStyleDialog(AnnotStyleDialogFragment dialog, int annotType) {
/* 1212 */     AnnotStyle previousAnnotStyle = dialog.getAnnotStyle();
/* 1213 */     int previousAnnotType = previousAnnotStyle.getAnnotType();
/* 1214 */     AnnotStyle annotStyle = getCustomAnnotStyle(annotType);
/* 1215 */     if (annotStyle == null) {
/*      */       return;
/*      */     }
/* 1218 */     dialog.setAnnotStyle(annotStyle);
/* 1219 */     dialog.setCanShowRichContentSwitch(this.mToolManager.isShowRichContentOption());
/* 1220 */     int nextToolId = getButtonIdFromAnnotType(annotType);
/* 1221 */     View nextToolButton = findViewById(nextToolId);
/* 1222 */     View previousToolButton = findViewById(getButtonIdFromAnnotType(previousAnnotType));
/* 1223 */     if (nextToolButton != null && previousToolButton != null && previousToolButton.getVisibility() == 0) {
/* 1224 */       previousToolButton.setVisibility(8);
/* 1225 */       nextToolButton.setVisibility(0);
/*      */     } 
/* 1227 */     updateVisibleAnnotType(annotType);
/* 1228 */     selectTool((View)null, nextToolId);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateVisibleAnnotType(int annotType) {
/* 1234 */     if (this.mVisibleAnnotTypeMap == null) {
/*      */       return;
/*      */     }
/* 1237 */     for (GroupedItem item : this.mGroupItems) {
/* 1238 */       if (item.contains(annotType)) {
/* 1239 */         this.mVisibleAnnotTypeMap.put(item.getPrefKey(), Integer.valueOf(annotType));
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public int getButtonIdFromAnnotType(int annotType) {
/* 1245 */     int indexAtValue = this.mButtonAnnotTypeMap.indexOfValue(annotType);
/* 1246 */     if (indexAtValue > -1) {
/* 1247 */       return this.mButtonAnnotTypeMap.keyAt(indexAtValue);
/*      */     }
/* 1249 */     return -1;
/*      */   }
/*      */   
/*      */   private int getAnnotTypeFromButtonId(int buttonId) {
/* 1253 */     return this.mButtonAnnotTypeMap.get(buttonId);
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   private ArrayList<Integer> getMoreAnnotTypes(int annotType) {
/* 1258 */     for (GroupedItem item : this.mGroupItems) {
/* 1259 */       if (item.contains(annotType)) {
/* 1260 */         return item.getAvailableAnnotTypes();
/*      */       }
/*      */     } 
/* 1263 */     return null;
/*      */   }
/*      */   
/*      */   private void showStampStatePopup(final int id, View view) {
/* 1267 */     Context context = getContext();
/* 1268 */     if (context == null || view == null || this.mToolManager == null || getStampsEnabledCount() < 2) {
/*      */       return;
/*      */     }
/*      */     
/* 1272 */     if (this.mStampStatePopup == null) {
/* 1273 */       this.mStampStatePopup = new StampStatePopup(context, this.mToolManager, this.mStampState, this.mToolbarBackgroundColor, this.mToolbarToolIconColor);
/* 1274 */       updateStampPopupSize();
/*      */     } else {
/* 1276 */       this.mStampStatePopup.updateView(this.mStampState);
/*      */     } 
/*      */     
/* 1279 */     this.mStampStatePopup.setOnDismissListener(new PopupWindow.OnDismissListener()
/*      */         {
/*      */           public void onDismiss() {
/* 1282 */             Context context = AnnotationToolbar.this.getContext();
/* 1283 */             if (context == null || AnnotationToolbar.this.mToolManager == null) {
/*      */               return;
/*      */             }
/*      */             
/* 1287 */             String currentStampState = AnnotationToolbar.this.mStampState;
/* 1288 */             AnnotationToolbar.this.mStampState = AnnotationToolbar.this.mStampStatePopup.getStampState();
/* 1289 */             if (AnnotationToolbar.this.mStampState == null) {
/*      */               return;
/*      */             }
/* 1292 */             AnnotationToolbar.this.checkStampState();
/* 1293 */             AnnotationToolbar.this.updateStampBtnState();
/*      */             
/* 1295 */             SharedPreferences settings = Tool.getToolPreferences(context);
/* 1296 */             SharedPreferences.Editor editor = settings.edit();
/* 1297 */             editor.putString("annotation_toolbar_signature_state", AnnotationToolbar.this.mStampState);
/* 1298 */             editor.apply();
/* 1299 */             int analyticsId = 0;
/* 1300 */             boolean differentState = !AnnotationToolbar.this.mStampState.equals(currentStampState);
/* 1301 */             switch (AnnotationToolbar.this.mStampState) {
/*      */               case "signature":
/* 1303 */                 AnnotationToolbar.this.mToolManager.setTool(AnnotationToolbar.this.mToolManager.createTool((ToolManager.ToolModeBase)ToolManager.ToolMode.SIGNATURE, AnnotationToolbar.this.mToolManager.getTool()));
/* 1304 */                 ((Tool)AnnotationToolbar.this.mToolManager.getTool()).setForceSameNextToolMode(AnnotationToolbar.this.mButtonStayDown);
/* 1305 */                 AnnotationToolbar.this.selectButton(id);
/* 1306 */                 analyticsId = 9;
/* 1307 */                 AnnotationToolbar.this.mEventAction = true;
/*      */                 break;
/*      */               case "stamp":
/* 1310 */                 AnnotationToolbar.this.mToolManager.setTool(AnnotationToolbar.this.mToolManager.createTool((ToolManager.ToolModeBase)ToolManager.ToolMode.STAMPER, AnnotationToolbar.this.mToolManager.getTool()));
/* 1311 */                 ((Tool)AnnotationToolbar.this.mToolManager.getTool()).setForceSameNextToolMode(AnnotationToolbar.this.mButtonStayDown);
/* 1312 */                 AnnotationToolbar.this.selectButton(id);
/* 1313 */                 analyticsId = 10;
/* 1314 */                 AnnotationToolbar.this.mEventAction = true;
/*      */                 break;
/*      */               case "rubber_stamp":
/* 1317 */                 AnnotationToolbar.this.mToolManager.setTool(AnnotationToolbar.this.mToolManager.createTool((ToolManager.ToolModeBase)ToolManager.ToolMode.RUBBER_STAMPER, AnnotationToolbar.this.mToolManager.getTool()));
/* 1318 */                 ((Tool)AnnotationToolbar.this.mToolManager.getTool()).setForceSameNextToolMode(AnnotationToolbar.this.mButtonStayDown);
/* 1319 */                 AnnotationToolbar.this.selectButton(id);
/* 1320 */                 analyticsId = 11;
/* 1321 */                 AnnotationToolbar.this.mEventAction = true;
/*      */                 break;
/*      */             } 
/* 1324 */             if (differentState) {
/* 1325 */               AnalyticsHandlerAdapter.getInstance().sendEvent(23, 
/* 1326 */                   AnalyticsParam.annotationToolbarParam(analyticsId));
/*      */             } else {
/* 1328 */               AnnotationToolbar.this.mToolManager.skipNextTapEvent();
/*      */             } 
/*      */           }
/*      */         });
/* 1332 */     this.mStampStatePopup.showAsDropDown(view);
/*      */   }
/*      */ 
/*      */   
/*      */   private void updateStampPopupSize() {
/* 1337 */     if (this.mStampStatePopup != null) {
/* 1338 */       this.mStampStatePopup.setWidth(getToolWidth());
/* 1339 */       this.mStampStatePopup.setHeight(getToolHeight() * (getStampsEnabledCount() - 1));
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean canShowMoreButton() {
/* 1344 */     if (this.mPdfViewCtrl != null && this.mPdfViewCtrl.isUndoRedoEnabled() && this.mOnUndoRedoListener != null && this.mToolManager.isShowUndoRedo()) {
/* 1345 */       return true;
/*      */     }
/*      */     
/* 1348 */     Context context = getContext();
/* 1349 */     return (!Utils.isLandscape(context) && !Utils.isTablet(context));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAnnotationToolbarListener(AnnotationToolbarListener listener) {
/* 1358 */     this.mAnnotationToolbarListener = listener;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOnUndoRedoListener(UndoRedoPopupWindow.OnUndoRedoListener listener) {
/* 1368 */     this.mOnUndoRedoListener = listener;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isShowing() {
/* 1375 */     return (getVisibility() == 0);
/*      */   }
/*      */ 
/*      */   
/*      */   void addButtons() {
/* 1380 */     safeAddButtons(R.id.controls_annotation_toolbar_tool_text_highlight);
/* 1381 */     safeAddButtons(R.id.controls_annotation_toolbar_tool_text_underline);
/* 1382 */     safeAddButtons(R.id.controls_annotation_toolbar_tool_stickynote);
/* 1383 */     safeAddButtons(R.id.controls_annotation_toolbar_tool_sound);
/* 1384 */     safeAddButtons(R.id.controls_annotation_toolbar_tool_text_squiggly);
/* 1385 */     safeAddButtons(R.id.controls_annotation_toolbar_tool_text_strikeout);
/* 1386 */     safeAddButtons(R.id.controls_annotation_toolbar_tool_free_highlighter);
/* 1387 */     safeAddButtons(R.id.controls_annotation_toolbar_tool_stamp);
/* 1388 */     safeAddButtons(R.id.controls_annotation_toolbar_tool_image_stamper);
/* 1389 */     safeAddButtons(R.id.controls_annotation_toolbar_tool_rubber_stamper);
/* 1390 */     safeAddButtons(R.id.controls_annotation_toolbar_tool_line);
/* 1391 */     safeAddButtons(R.id.controls_annotation_toolbar_tool_arrow);
/* 1392 */     safeAddButtons(R.id.controls_annotation_toolbar_tool_ruler);
/* 1393 */     safeAddButtons(R.id.controls_annotation_toolbar_tool_perimeter_measure);
/* 1394 */     safeAddButtons(R.id.controls_annotation_toolbar_tool_area_measure);
/* 1395 */     safeAddButtons(R.id.controls_annotation_toolbar_tool_polyline);
/* 1396 */     safeAddButtons(R.id.controls_annotation_toolbar_tool_freehand);
/* 1397 */     safeAddButtons(R.id.controls_annotation_toolbar_tool_eraser);
/* 1398 */     safeAddButtons(R.id.controls_annotation_toolbar_tool_freetext);
/* 1399 */     safeAddButtons(R.id.controls_annotation_toolbar_tool_callout);
/* 1400 */     safeAddButtons(R.id.controls_annotation_toolbar_tool_rectangle);
/* 1401 */     safeAddButtons(R.id.controls_annotation_toolbar_tool_oval);
/* 1402 */     safeAddButtons(R.id.controls_annotation_toolbar_tool_polygon);
/* 1403 */     safeAddButtons(R.id.controls_annotation_toolbar_tool_cloud);
/* 1404 */     safeAddButtons(R.id.controls_annotation_toolbar_tool_multi_select);
/* 1405 */     safeAddButtons(R.id.controls_annotation_toolbar_tool_pan);
/* 1406 */     safeAddButtons(R.id.controls_annotation_toolbar_btn_close);
/* 1407 */     if (canShowMoreButton()) {
/* 1408 */       safeAddButtons(R.id.controls_annotation_toolbar_btn_more);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInEditMode() {
/* 1417 */     return (this.mEditToolbarImpl != null && this.mEditToolbarImpl.isToolbarShown());
/*      */   }
/*      */   
/*      */   public boolean isInFormMode() {
/* 1421 */     return (this.mFormToolbar != null && this.mFormToolbar.isShowing());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void hideButton(@NonNull AnnotationToolbarButtonId id) {
/* 1430 */     setToolbarButtonVisibility(id, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void showButton(@NonNull AnnotationToolbarButtonId id) {
/* 1439 */     setToolbarButtonVisibility(id, true);
/*      */   }
/*      */   
/*      */   private void setToolbarButtonVisibility(@NonNull AnnotationToolbarButtonId id, boolean visible) {
/* 1443 */     this.mButtonsVisibility.put(id.id, visible ? 0 : 8);
/* 1444 */     updateButtonsVisibility();
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
/*      */   public boolean handleKeyUp(int keyCode, KeyEvent event) {
/* 1458 */     Context context = getContext();
/* 1459 */     if (context == null || this.mToolManager == null) {
/* 1460 */       return false;
/*      */     }
/*      */     
/* 1463 */     if (isInEditMode()) {
/* 1464 */       return this.mEditToolbarImpl.handleKeyUp(keyCode, event);
/*      */     }
/*      */     
/* 1467 */     if (isInFormMode()) {
/* 1468 */       return this.mFormToolbar.handleKeyUp(keyCode, event);
/*      */     }
/*      */     
/* 1471 */     Tool tool = (Tool)this.mToolManager.getTool();
/* 1472 */     if (tool == null) {
/* 1473 */       return false;
/*      */     }
/*      */     
/* 1476 */     if (findViewById(R.id.controls_annotation_toolbar_tool_pan).isShown() && !(tool instanceof com.pdftron.pdf.tools.Pan) && 
/*      */       
/* 1478 */       ShortcutHelper.isCancelTool(keyCode, event)) {
/* 1479 */       closePopups();
/* 1480 */       selectTool((View)null, R.id.controls_annotation_toolbar_tool_pan);
/* 1481 */       return true;
/*      */     } 
/*      */     
/* 1484 */     if (findViewById(R.id.controls_annotation_toolbar_btn_close).isShown() && 
/* 1485 */       ShortcutHelper.isCloseMenu(keyCode, event)) {
/* 1486 */       closePopups();
/* 1487 */       selectTool((View)null, R.id.controls_annotation_toolbar_btn_close);
/* 1488 */       return true;
/*      */     } 
/*      */     
/* 1491 */     int mode = -1;
/* 1492 */     this.mSelectedToolId = -1;
/*      */     
/* 1494 */     if (ShortcutHelper.isHighlightAnnot(keyCode, event)) {
/* 1495 */       mode = 0;
/* 1496 */       this.mSelectedToolId = R.id.controls_annotation_toolbar_tool_text_highlight;
/*      */     } 
/*      */     
/* 1499 */     if (ShortcutHelper.isUnderlineAnnot(keyCode, event)) {
/* 1500 */       mode = 0;
/* 1501 */       this.mSelectedToolId = R.id.controls_annotation_toolbar_tool_text_underline;
/*      */     } 
/*      */     
/* 1504 */     if (ShortcutHelper.isStrikethroughAnnot(keyCode, event)) {
/* 1505 */       mode = 0;
/* 1506 */       this.mSelectedToolId = R.id.controls_annotation_toolbar_tool_text_strikeout;
/*      */     } 
/*      */     
/* 1509 */     if (ShortcutHelper.isSquigglyAnnot(keyCode, event)) {
/* 1510 */       mode = 0;
/* 1511 */       this.mSelectedToolId = R.id.controls_annotation_toolbar_tool_text_squiggly;
/*      */     } 
/*      */     
/* 1514 */     if (ShortcutHelper.isTextboxAnnot(keyCode, event)) {
/* 1515 */       mode = 0;
/* 1516 */       this.mSelectedToolId = R.id.controls_annotation_toolbar_tool_freetext;
/*      */     } 
/*      */     
/* 1519 */     if (ShortcutHelper.isCommentAnnot(keyCode, event)) {
/* 1520 */       mode = 0;
/* 1521 */       this.mSelectedToolId = R.id.controls_annotation_toolbar_tool_stickynote;
/*      */     } 
/*      */     
/* 1524 */     if (ShortcutHelper.isRectangleAnnot(keyCode, event)) {
/* 1525 */       mode = 0;
/* 1526 */       this.mSelectedToolId = R.id.controls_annotation_toolbar_tool_rectangle;
/*      */     } 
/*      */     
/* 1529 */     if (ShortcutHelper.isOvalAnnot(keyCode, event)) {
/* 1530 */       mode = 0;
/* 1531 */       this.mSelectedToolId = R.id.controls_annotation_toolbar_tool_oval;
/*      */     } 
/*      */     
/* 1534 */     if (ShortcutHelper.isDrawAnnot(keyCode, event)) {
/* 1535 */       mode = 0;
/* 1536 */       this.mSelectedToolId = R.id.controls_annotation_toolbar_tool_freehand;
/*      */     } 
/*      */     
/* 1539 */     if (findViewById(R.id.controls_annotation_toolbar_tool_eraser).isShown() && 
/* 1540 */       ShortcutHelper.isEraserAnnot(keyCode, event)) {
/*      */       
/* 1542 */       mode = 0;
/* 1543 */       this.mSelectedToolId = R.id.controls_annotation_toolbar_tool_eraser;
/*      */     } 
/*      */     
/* 1546 */     if (ShortcutHelper.isLineAnnot(keyCode, event)) {
/* 1547 */       mode = 0;
/* 1548 */       this.mSelectedToolId = R.id.controls_annotation_toolbar_tool_line;
/*      */     } 
/*      */     
/* 1551 */     if (ShortcutHelper.isArrowAnnot(keyCode, event)) {
/* 1552 */       mode = 0;
/* 1553 */       this.mSelectedToolId = R.id.controls_annotation_toolbar_tool_arrow;
/*      */     } 
/*      */     
/* 1556 */     if (ShortcutHelper.isSignatureAnnot(keyCode, event) && !this.mToolManager.isToolModeDisabled(ToolManager.ToolMode.SIGNATURE)) {
/* 1557 */       mode = 0;
/* 1558 */       this.mStampState = "signature";
/* 1559 */       checkStampState();
/* 1560 */       updateStampBtnState();
/* 1561 */       this.mSelectedToolId = R.id.controls_annotation_toolbar_tool_stamp;
/*      */     } 
/*      */     
/* 1564 */     if (ShortcutHelper.isImageAnnot(keyCode, event) && !this.mToolManager.isToolModeDisabled(ToolManager.ToolMode.STAMPER)) {
/* 1565 */       mode = 0;
/* 1566 */       this.mStampState = "stamp";
/* 1567 */       checkStampState();
/* 1568 */       updateStampBtnState();
/* 1569 */       if (this.mIsExpanded) {
/* 1570 */         this.mSelectedToolId = R.id.controls_annotation_toolbar_tool_image_stamper;
/*      */       } else {
/* 1572 */         this.mSelectedToolId = R.id.controls_annotation_toolbar_tool_stamp;
/*      */       } 
/*      */     } 
/*      */     
/* 1576 */     if (mode != -1) {
/* 1577 */       closePopups();
/* 1578 */       if (!isShowing()) {
/* 1579 */         if (this.mAnnotationToolbarListener != null) {
/* 1580 */           this.mAnnotationToolbarListener.onShowAnnotationToolbarByShortcut(mode);
/*      */         }
/*      */       } else {
/* 1583 */         selectTool((View)null, this.mSelectedToolId);
/*      */       } 
/* 1585 */       return true;
/*      */     } 
/*      */     
/* 1588 */     return false;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   private AnnotStyle getCustomAnnotStyle(int annotType) {
/* 1593 */     Context context = getContext();
/* 1594 */     if (context == null) {
/* 1595 */       return null;
/*      */     }
/*      */     
/* 1598 */     AnnotStyle annotStyle = ToolStyleConfig.getInstance().getCustomAnnotStyle(context, annotType, "");
/* 1599 */     annotStyle.setSnap(this.mToolManager.isSnappingEnabledForMeasurementTools());
/* 1600 */     annotStyle.setTextHTMLContent(this.mToolManager.isRichContentEnabledForFreeText() ? "rc" : "");
/* 1601 */     return annotStyle;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isExpanded() {
/* 1610 */     return this.mIsExpanded;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void toggleExpanded() {
/* 1617 */     this.mShouldExpand = !this.mShouldExpand;
/* 1618 */     PdfViewCtrlSettingsManager.updateDoubleRowToolbarInUse(getContext(), this.mShouldExpand);
/* 1619 */     updateExpanded((getResources().getConfiguration()).orientation);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateExpanded(int orientation) {
/*      */     int height;
/* 1626 */     Context context = getContext();
/* 1627 */     if (context == null || this.mToolManager == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1631 */     this.mIsExpanded = (this.mShouldExpand && orientation == 1 && !Utils.isTablet(context));
/*      */     
/* 1633 */     if (!this.mIsExpanded) {
/* 1634 */       if (this.mSelectedButtonId == R.id.controls_annotation_toolbar_tool_image_stamper) {
/* 1635 */         this.mStampState = "stamp";
/* 1636 */       } else if (this.mSelectedButtonId == R.id.controls_annotation_toolbar_tool_rubber_stamper) {
/* 1637 */         this.mStampState = "rubber_stamp";
/*      */       } 
/*      */     } else {
/* 1640 */       this.mStampState = "signature";
/*      */     } 
/* 1642 */     checkStampState();
/*      */     
/* 1644 */     ViewGroup root = (ViewGroup)findViewById(R.id.controls_annotation_toolbar_state_normal);
/* 1645 */     int nextLayoutRes = this.mIsExpanded ? R.layout.controls_annotation_toolbar_expanded_layout : R.layout.controls_annotation_toolbar_collapsed_layout;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1650 */     if (!this.mIsExpanded) {
/* 1651 */       int[] attrs = { R.attr.actionBarSize };
/* 1652 */       TypedArray ta = context.obtainStyledAttributes(attrs);
/*      */       try {
/* 1654 */         height = ta.getDimensionPixelSize(0, (int)Utils.convDp2Pix(context, 56.0F));
/*      */       } finally {
/* 1656 */         ta.recycle();
/*      */       } 
/*      */     } else {
/* 1659 */       height = -2;
/*      */     } 
/*      */     
/* 1662 */     View nextLayout = LayoutInflater.from(context).inflate(nextLayoutRes, null);
/* 1663 */     nextLayout.setLayoutParams((ViewGroup.LayoutParams)new RelativeLayout.LayoutParams(-1, height));
/*      */ 
/*      */     
/* 1666 */     TransitionSet transitionSet = new TransitionSet();
/* 1667 */     transitionSet.addTransition((Transition)new ChangeBounds());
/* 1668 */     transitionSet.addTransition((Transition)new Fade());
/* 1669 */     TransitionManager.beginDelayedTransition((ViewGroup)getParent(), (Transition)transitionSet);
/* 1670 */     root.removeViewAt(0);
/* 1671 */     root.addView(nextLayout);
/*      */     
/* 1673 */     initButtons();
/* 1674 */     updateButtonsVisibility();
/* 1675 */     initSelectedButton();
/*      */   }
/*      */   
/*      */   public void showFormToolbar(@Nullable ToolManager.ToolMode toolMode, int mode) {
/* 1679 */     hideAnnotationToolbar();
/* 1680 */     this.mFormToolbar = (FormToolbar)findViewById(R.id.controls_form_toolbar);
/* 1681 */     this.mFormToolbar.setup(this.mToolManager);
/* 1682 */     this.mFormToolbar.setMode(mode);
/* 1683 */     this.mFormToolbar.setButtonStayDown(this.mButtonStayDown);
/* 1684 */     this.mFormToolbar.setFormToolbarListener(this);
/* 1685 */     this.mFormToolbar.show(toolMode);
/*      */   }
/*      */   
/*      */   public void showEditToolbar(@NonNull ToolManager.ToolMode toolMode) {
/* 1689 */     showEditToolbar(toolMode, (Annot)null, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void showEditToolbar(@NonNull ToolManager.ToolMode toolMode, @Nullable Annot inkAnnot, int pageNum) {
/* 1700 */     FragmentActivity activity = this.mToolManager.getCurrentActivity();
/* 1701 */     if (activity == null || isInEditMode()) {
/*      */       return;
/*      */     }
/* 1704 */     setBackgroundColor(0);
/* 1705 */     hideAnnotationToolbar();
/* 1706 */     EditToolbar editToolbar = (EditToolbar)findViewById(R.id.controls_annotation_toolbar_state_edit);
/* 1707 */     this.mEditToolbarImpl = new EditToolbarImpl(activity, editToolbar, this.mToolManager, toolMode, inkAnnot, pageNum, this.mShouldExpand);
/* 1708 */     this.mEditToolbarImpl.setOnEditToolbarListener(this);
/* 1709 */     this.mEditToolbarImpl.showToolbar();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void closeEditToolbar() {
/* 1718 */     if (this.mEditToolbarImpl != null) {
/* 1719 */       this.mEditToolbarImpl.close();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onEditToolbarDismissed() {
/* 1729 */     if (this.mToolManager == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1733 */     setBackgroundColor(this.mToolbarBackgroundColor);
/* 1734 */     if (this.mDismissAfterExitEdit) {
/* 1735 */       close();
/*      */     } else {
/* 1737 */       showAnnotationToolbar();
/*      */     } 
/* 1739 */     ToolManager.Tool tool = this.mToolManager.getTool();
/* 1740 */     if (tool == null) {
/*      */       return;
/*      */     }
/* 1743 */     ToolManager.ToolMode toolMode = ToolManager.getDefaultToolMode(tool.getToolMode());
/* 1744 */     if (toolMode == ToolManager.ToolMode.INK_CREATE) {
/* 1745 */       this.mToolManager.setTool(this.mToolManager.createTool((ToolManager.ToolModeBase)ToolManager.ToolMode.PAN, null));
/* 1746 */       selectButton(R.id.controls_annotation_toolbar_tool_pan);
/*      */     } else {
/*      */       
/* 1749 */       this.mToolManager.setTool(this.mToolManager.createTool((ToolManager.ToolModeBase)toolMode, tool));
/* 1750 */       selectTool((View)null, getResourceIdOfTool(toolMode));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void onFormToolbarWillClose() {
/* 1756 */     close();
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
/*      */   private void checkStampState() {
/* 1788 */     if ("rubber_stamp".equals(this.mStampState) && this.mToolManager
/* 1789 */       .isToolModeDisabled(ToolManager.ToolMode.RUBBER_STAMPER)) {
/* 1790 */       AnalyticsHandlerAdapter.getInstance().sendException(new Exception("rubber stamper is selected while it is disabled"));
/*      */       
/* 1792 */       this.mStampState = "signature";
/*      */     } 
/* 1794 */     if ("stamp".equals(this.mStampState) && this.mToolManager
/* 1795 */       .isToolModeDisabled(ToolManager.ToolMode.STAMPER)) {
/* 1796 */       AnalyticsHandlerAdapter.getInstance().sendException(new Exception("image stamper is selected while it is disabled"));
/*      */       
/* 1798 */       this.mStampState = "signature";
/*      */     } 
/* 1800 */     if ("signature".equals(this.mStampState) && this.mToolManager
/* 1801 */       .isToolModeDisabled(ToolManager.ToolMode.SIGNATURE)) {
/* 1802 */       AnalyticsHandlerAdapter.getInstance().sendException(new Exception("signature is selected while it is disabled"));
/*      */       
/* 1804 */       this.mStampState = "stamp";
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private int getToolWidth() {
/* 1810 */     Context context = getContext();
/* 1811 */     if (context == null) {
/* 1812 */       return 0;
/*      */     }
/*      */     
/* 1815 */     int numIcons = 9;
/* 1816 */     if (Utils.isLandscape(context) || Utils.isTablet(context)) {
/* 1817 */       numIcons = 16;
/*      */     }
/* 1819 */     int width = getWidth() / numIcons;
/* 1820 */     View panBtn = findViewById(R.id.controls_annotation_toolbar_tool_pan);
/* 1821 */     if (this.mIsExpanded && panBtn != null) {
/* 1822 */       panBtn.measure(0, 0);
/* 1823 */       width = panBtn.getMeasuredWidth();
/*      */     } 
/*      */     
/* 1826 */     return width;
/*      */   }
/*      */ 
/*      */   
/*      */   private int getToolHeight() {
/* 1831 */     int height = getHeight();
/* 1832 */     View panBtn = findViewById(R.id.controls_annotation_toolbar_tool_pan);
/* 1833 */     if (this.mIsExpanded && panBtn != null) {
/* 1834 */       panBtn.measure(0, 0);
/* 1835 */       height = panBtn.getMeasuredHeight();
/*      */     } 
/*      */     
/* 1838 */     return height;
/*      */   }
/*      */   
/*      */   public static interface AnnotationToolbarListener {
/*      */     void onAnnotationToolbarShown();
/*      */     
/*      */     void onAnnotationToolbarClosed();
/*      */     
/*      */     void onShowAnnotationToolbarByShortcut(int param1Int);
/*      */   }
/*      */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\controls\AnnotationToolbar.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */