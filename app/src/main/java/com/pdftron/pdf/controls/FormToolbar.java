/*     */ package com.pdftron.pdf.controls;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.content.DialogInterface;
/*     */ import android.content.res.TypedArray;
/*     */ import android.graphics.drawable.Drawable;
/*     */ import android.util.AttributeSet;
/*     */ import android.view.KeyEvent;
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.annotation.RequiresApi;
/*     */ import androidx.fragment.app.FragmentActivity;
/*     */ import com.pdftron.pdf.config.ToolConfig;
/*     */ import com.pdftron.pdf.config.ToolStyleConfig;
/*     */ import com.pdftron.pdf.model.AnnotStyle;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.tools.RubberStampCreate;
/*     */ import com.pdftron.pdf.tools.Tool;
/*     */ import com.pdftron.pdf.tools.ToolManager;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.AnalyticsParam;
/*     */ import com.pdftron.pdf.utils.ShortcutHelper;
/*     */ import java.util.ArrayList;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FormToolbar
/*     */   extends BaseToolbar
/*     */   implements ToolManager.ToolChangedListener
/*     */ {
/*     */   protected String mSelectedButtonExtra;
/*     */   public static final int START_MODE_PREPARE_FORM_TOOLBAR = 0;
/*     */   public static final int START_MODE_FILL_AND_SIGN_TOOLBAR = 1;
/*     */   private static final String sCHECK_MARK_LABEL = "FILL_CHECK";
/*     */   private static final String sCROSS_LABEL = "FILL_CROSS";
/*     */   private static final String sDOT_LABEL = "FILL_DOT";
/*     */   private FormToolbarListener mFormToolbarListener;
/*     */   private AnnotStyleDialogFragment mAnnotStyleDialog;
/*     */   
/*     */   public FormToolbar(@NonNull Context context) {
/*  66 */     this(context, (AttributeSet)null);
/*     */   }
/*     */   
/*     */   public FormToolbar(@NonNull Context context, @Nullable AttributeSet attrs) {
/*  70 */     this(context, attrs, R.attr.form_toolbar);
/*     */   }
/*     */   
/*     */   public FormToolbar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
/*  74 */     super(context, attrs, defStyleAttr);
/*  75 */     init(context, attrs, defStyleAttr, R.style.FormToolbarStyle);
/*     */   }
/*     */   
/*     */   @RequiresApi(api = 21)
/*     */   public FormToolbar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
/*  80 */     super(context, attrs, defStyleAttr, defStyleRes);
/*  81 */     init(context, attrs, defStyleAttr, defStyleRes);
/*     */   }
/*     */ 
/*     */   
/*     */   private void init(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
/*  86 */     TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FormToolbar, defStyleAttr, defStyleRes);
/*     */     try {
/*  88 */       this.mToolbarBackgroundColor = typedArray.getColor(R.styleable.FormToolbar_colorBackground, -16777216);
/*  89 */       this.mToolbarToolBackgroundColor = typedArray.getColor(R.styleable.FormToolbar_colorToolBackground, -16777216);
/*  90 */       this.mToolbarToolIconColor = typedArray.getColor(R.styleable.FormToolbar_colorToolIcon, -1);
/*  91 */       this.mToolbarCloseIconColor = typedArray.getColor(R.styleable.FormToolbar_colorCloseIcon, -1);
/*     */     } finally {
/*  93 */       typedArray.recycle();
/*     */     } 
/*     */     
/*  96 */     LayoutInflater.from(context).inflate(R.layout.controls_form_toolbar, (ViewGroup)this, true);
/*     */     
/*  98 */     setBackgroundColor(this.mToolbarBackgroundColor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFormToolbarListener(FormToolbarListener listener) {
/* 107 */     this.mFormToolbarListener = listener;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMode(int mode) {
/* 117 */     View prepareFormLayout = findViewById(R.id.prepare_form_layout);
/* 118 */     View fillAndSignLayout = findViewById(R.id.fill_and_sign_layout);
/*     */     
/* 120 */     if (mode == 0) {
/* 121 */       prepareFormLayout.setVisibility(0);
/* 122 */       fillAndSignLayout.setVisibility(8);
/*     */     } else {
/* 124 */       prepareFormLayout.setVisibility(8);
/* 125 */       fillAndSignLayout.setVisibility(0);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setup(@NonNull ToolManager toolManager) {
/* 130 */     this.mToolManager = toolManager;
/*     */     
/* 132 */     initButtons();
/*     */     
/* 134 */     this.mToolManager.addToolChangedListener(this);
/*     */ 
/*     */     
/* 137 */     this.mToolManager.setTool(this.mToolManager.createTool((ToolManager.ToolModeBase)ToolManager.ToolMode.PAN, null));
/* 138 */     initSelectedButton();
/*     */     
/* 140 */     setVisibility(8);
/*     */   }
/*     */   
/*     */   public void selectTool(View view, int id) {
/* 144 */     Context context = getContext();
/* 145 */     if (context == null || this.mToolManager == null) {
/*     */       return;
/*     */     }
/*     */     
/* 149 */     View button = findViewById(id);
/* 150 */     if (null == button) {
/*     */       return;
/*     */     }
/*     */     
/* 154 */     ToolManager.ToolMode toolMode = null;
/* 155 */     int analyticId = -1;
/* 156 */     this.mSelectedButtonExtra = null;
/* 157 */     if (id == R.id.controls_form_field_toolbar_widget_text) {
/* 158 */       toolMode = ToolManager.ToolMode.FORM_TEXT_FIELD_CREATE;
/* 159 */       analyticId = 31;
/* 160 */     } else if (id == R.id.controls_form_field_toolbar_widget_checkbox) {
/* 161 */       toolMode = ToolManager.ToolMode.FORM_CHECKBOX_CREATE;
/* 162 */       analyticId = 33;
/* 163 */     } else if (id == R.id.controls_form_field_toolbar_widget_signature) {
/* 164 */       toolMode = ToolManager.ToolMode.FORM_SIGNATURE_CREATE;
/* 165 */       analyticId = 32;
/* 166 */     } else if (id == R.id.controls_form_field_toolbar_widget_radio) {
/* 167 */       toolMode = ToolManager.ToolMode.FORM_RADIO_GROUP_CREATE;
/* 168 */       analyticId = 34;
/* 169 */     } else if (id == R.id.controls_form_field_toolbar_widget_listbox) {
/* 170 */       toolMode = ToolManager.ToolMode.FORM_LIST_BOX_CREATE;
/* 171 */       analyticId = 35;
/* 172 */     } else if (id == R.id.controls_form_field_toolbar_widget_combobox) {
/* 173 */       toolMode = ToolManager.ToolMode.FORM_COMBO_BOX_CREATE;
/* 174 */       analyticId = 36;
/* 175 */     } else if (id == R.id.controls_fill_and_sign_toolbar_text) {
/* 176 */       toolMode = ToolManager.ToolMode.FREE_TEXT_SPACING_CREATE;
/* 177 */       analyticId = 37;
/* 178 */     } else if (id == R.id.controls_fill_and_sign_toolbar_signature) {
/* 179 */       toolMode = ToolManager.ToolMode.SIGNATURE;
/* 180 */       analyticId = 9;
/* 181 */     } else if (id == R.id.controls_fill_and_sign_toolbar_date) {
/* 182 */       toolMode = ToolManager.ToolMode.FREE_TEXT_DATE_CREATE;
/* 183 */       analyticId = 38;
/* 184 */     } else if (id == R.id.controls_fill_and_sign_toolbar_checkmark) {
/* 185 */       toolMode = ToolManager.ToolMode.RUBBER_STAMPER;
/* 186 */       analyticId = 39;
/* 187 */       this.mSelectedButtonExtra = "FILL_CHECK";
/* 188 */     } else if (id == R.id.controls_fill_and_sign_toolbar_cross) {
/* 189 */       toolMode = ToolManager.ToolMode.RUBBER_STAMPER;
/* 190 */       analyticId = 40;
/* 191 */       this.mSelectedButtonExtra = "FILL_CROSS";
/* 192 */     } else if (id == R.id.controls_fill_and_sign_toolbar_dot) {
/* 193 */       toolMode = ToolManager.ToolMode.RUBBER_STAMPER;
/* 194 */       analyticId = 41;
/* 195 */       this.mSelectedButtonExtra = "FILL_DOT";
/*     */     } 
/* 197 */     boolean sendAnalytics = (this.mSelectedButtonId != id);
/* 198 */     if (toolMode != null) {
/* 199 */       if (this.mSelectedButtonId == id && hasStyle(getToolOfResourceId(id))) {
/* 200 */         AnnotStyle annotStyle = ToolStyleConfig.getInstance().getCustomAnnotStyle(context, getAnnotTypeOfResourceId(id), "");
/* 201 */         if (annotStyle == null) {
/*     */           return;
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 207 */         AnnotStyleDialogFragment popupWindow = (new AnnotStyleDialogFragment.Builder(annotStyle)).setAnchorView(button).setWhiteListFont(this.mToolManager.getFreeTextFonts()).build();
/* 208 */         showAnnotPropertyPopup(popupWindow, view, analyticId);
/*     */       } 
/* 210 */       this.mToolManager.setTool(this.mToolManager.createTool((ToolManager.ToolModeBase)toolMode, this.mToolManager.getTool()));
/* 211 */       ToolManager.Tool tool = this.mToolManager.getTool();
/* 212 */       ((Tool)tool).setForceSameNextToolMode(this.mButtonStayDown);
/* 213 */       if (tool instanceof RubberStampCreate && this.mSelectedButtonExtra != null) {
/* 214 */         ((RubberStampCreate)tool).setStampName(this.mSelectedButtonExtra);
/*     */       }
/* 216 */       selectButton(id);
/* 217 */       this.mEventAction = true;
/* 218 */     } else if (id == R.id.controls_annotation_toolbar_tool_pan) {
/* 219 */       analyticId = 16;
/* 220 */       this.mToolManager.setTool(this.mToolManager.createTool((ToolManager.ToolModeBase)ToolManager.ToolMode.PAN, null));
/* 221 */       selectButton(id);
/* 222 */     } else if (id == R.id.controls_annotation_toolbar_btn_close) {
/* 223 */       sendAnalytics = false;
/* 224 */       close();
/* 225 */       this.mEventAction = false;
/*     */     } 
/*     */     
/* 228 */     if (sendAnalytics) {
/* 229 */       AnalyticsHandlerAdapter.getInstance().sendEvent(73, AnalyticsParam.annotationToolbarParam(analyticId));
/*     */     }
/*     */   }
/*     */   
/*     */   public void show(@Nullable ToolManager.ToolMode toolMode) {
/* 234 */     Context context = getContext();
/* 235 */     if (context == null) {
/*     */       return;
/*     */     }
/*     */     
/* 239 */     updateButtonsVisibility();
/* 240 */     setVisibility(0);
/*     */     
/* 242 */     if (toolMode != null) {
/* 243 */       this.mSelectedToolId = getResourceIdOfTool(toolMode, (String)null);
/*     */     }
/*     */     
/* 246 */     if (this.mSelectedToolId != -1) {
/* 247 */       selectTool((View)null, this.mSelectedToolId);
/* 248 */       this.mSelectedToolId = -1;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void close() {
/* 253 */     closePopups();
/* 254 */     if (this.mFormToolbarListener != null) {
/* 255 */       this.mFormToolbarListener.onFormToolbarWillClose();
/*     */     }
/*     */   }
/*     */   
/*     */   public void setButtonStayDown(boolean value) {
/* 260 */     this.mButtonStayDown = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateButtonsVisibility() {
/* 267 */     Context context = getContext();
/* 268 */     if (context == null || this.mToolManager == null) {
/*     */       return;
/*     */     }
/*     */     
/* 272 */     for (View button : this.mButtons) {
/* 273 */       int viewId = button.getId();
/* 274 */       safeShowHideButton(viewId, 0);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean handleKeyUp(int keyCode, KeyEvent event) {
/* 281 */     Tool tool = (Tool)this.mToolManager.getTool();
/* 282 */     if (tool == null) {
/* 283 */       return false;
/*     */     }
/*     */     
/* 286 */     if (findViewById(R.id.controls_annotation_toolbar_tool_pan).isShown() && !(tool instanceof com.pdftron.pdf.tools.Pan) && 
/*     */       
/* 288 */       ShortcutHelper.isCancelTool(keyCode, event)) {
/* 289 */       closePopups();
/* 290 */       selectTool((View)null, R.id.controls_annotation_toolbar_tool_pan);
/* 291 */       return true;
/*     */     } 
/*     */     
/* 294 */     if (findViewById(R.id.controls_annotation_toolbar_btn_close).isShown() && 
/* 295 */       ShortcutHelper.isCloseMenu(keyCode, event)) {
/* 296 */       closePopups();
/* 297 */       selectTool((View)null, R.id.controls_annotation_toolbar_btn_close);
/* 298 */       return true;
/*     */     } 
/*     */     
/* 301 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void closePopups() {
/* 308 */     if (this.mAnnotStyleDialog != null) {
/* 309 */       this.mAnnotStyleDialog.dismiss();
/* 310 */       this.mAnnotStyleDialog = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void toolChanged(ToolManager.Tool newTool, ToolManager.Tool oldTool) {
/* 316 */     if (newTool == null || !isShowing()) {
/*     */       return;
/*     */     }
/*     */     
/* 320 */     boolean canSelectButton = false;
/*     */     
/* 322 */     if (oldTool != null && oldTool instanceof Tool && newTool instanceof Tool) {
/* 323 */       Tool oldT = (Tool)oldTool;
/* 324 */       Tool newT = (Tool)newTool;
/* 325 */       canSelectButton = (!oldT.isForceSameNextToolMode() || !newT.isEditAnnotTool());
/*     */     } 
/*     */     
/* 328 */     if (canSelectButton) {
/* 329 */       ToolManager.ToolMode newToolMode = ToolManager.getDefaultToolMode(newTool.getToolMode());
/* 330 */       selectButton(getResourceIdOfTool(newToolMode, this.mSelectedButtonExtra));
/* 331 */       if (newTool instanceof RubberStampCreate && this.mSelectedButtonExtra != null) {
/* 332 */         ((RubberStampCreate)newTool).setStampName(this.mSelectedButtonExtra);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void safeShowHideButton(int viewId, int visibility) {
/* 338 */     View button = findViewById(viewId);
/* 339 */     ToolManager.ToolMode toolMode = ToolConfig.getInstance().getToolModeByAnnotationToolbarItemId(viewId);
/* 340 */     if (toolMode != null && button != null) {
/* 341 */       if (this.mToolManager.isToolModeDisabled(toolMode)) {
/* 342 */         button.setVisibility(8);
/*     */       } else {
/* 344 */         button.setVisibility(visibility);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private void showAnnotPropertyPopup(final AnnotStyleDialogFragment popupWindow, View view, int annotToolId) {
/* 350 */     if (view == null || popupWindow == null) {
/*     */       return;
/*     */     }
/* 353 */     if (this.mToolManager.isSkipNextTapEvent()) {
/* 354 */       this.mToolManager.resetSkipNextTapEvent();
/*     */       
/*     */       return;
/*     */     } 
/* 358 */     if (this.mAnnotStyleDialog != null) {
/*     */       return;
/*     */     }
/*     */     
/* 362 */     this.mAnnotStyleDialog = popupWindow;
/*     */     
/* 364 */     popupWindow.setOnDismissListener(new DialogInterface.OnDismissListener()
/*     */         {
/*     */           public void onDismiss(DialogInterface dialogInterface) {
/* 367 */             FormToolbar.this.mAnnotStyleDialog = null;
/*     */             
/* 369 */             Context context = FormToolbar.this.getContext();
/* 370 */             if (context == null || FormToolbar.this.mToolManager == null) {
/*     */               return;
/*     */             }
/*     */             
/* 374 */             AnnotStyle annotStyle = popupWindow.getAnnotStyle();
/* 375 */             ToolStyleConfig.getInstance().saveAnnotStyle(context, annotStyle, "");
/*     */             
/* 377 */             Tool tool = (Tool)FormToolbar.this.mToolManager.getTool();
/* 378 */             if (tool != null) {
/* 379 */               tool.setupAnnotProperty(annotStyle);
/*     */             }
/*     */           }
/*     */         });
/*     */     
/* 384 */     FragmentActivity activity = null;
/* 385 */     if (getContext() instanceof FragmentActivity) {
/* 386 */       activity = (FragmentActivity)getContext();
/* 387 */     } else if (this.mToolManager.getCurrentActivity() != null) {
/* 388 */       activity = this.mToolManager.getCurrentActivity();
/*     */     } 
/* 390 */     if (activity == null) {
/* 391 */       AnalyticsHandlerAdapter.getInstance().sendException(new Exception("AnnotationToolbar is not attached to with an Activity"));
/*     */       return;
/*     */     } 
/* 394 */     popupWindow.show(activity.getSupportFragmentManager(), 2, 
/* 395 */         AnalyticsHandlerAdapter.getInstance().getAnnotationTool(annotToolId));
/*     */   }
/*     */   
/*     */   private void initSelectedButton() {
/* 399 */     if (this.mToolManager == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 404 */     ToolManager.ToolMode toolMode = ToolManager.getDefaultToolMode(this.mToolManager.getTool().getToolMode());
/* 405 */     selectButton(getResourceIdOfTool(toolMode, (String)null));
/*     */   }
/*     */   
/*     */   private ToolManager.ToolMode getToolOfResourceId(int resId) {
/* 409 */     if (resId == R.id.controls_form_field_toolbar_widget_text)
/* 410 */       return ToolManager.ToolMode.FORM_TEXT_FIELD_CREATE; 
/* 411 */     if (resId == R.id.controls_form_field_toolbar_widget_checkbox)
/* 412 */       return ToolManager.ToolMode.FORM_CHECKBOX_CREATE; 
/* 413 */     if (resId == R.id.controls_form_field_toolbar_widget_radio)
/* 414 */       return ToolManager.ToolMode.FORM_RADIO_GROUP_CREATE; 
/* 415 */     if (resId == R.id.controls_form_field_toolbar_widget_signature)
/* 416 */       return ToolManager.ToolMode.FORM_SIGNATURE_CREATE; 
/* 417 */     if (resId == R.id.controls_form_field_toolbar_widget_listbox)
/* 418 */       return ToolManager.ToolMode.FORM_LIST_BOX_CREATE; 
/* 419 */     if (resId == R.id.controls_form_field_toolbar_widget_combobox)
/* 420 */       return ToolManager.ToolMode.FORM_COMBO_BOX_CREATE; 
/* 421 */     if (resId == R.id.controls_fill_and_sign_toolbar_text)
/* 422 */       return ToolManager.ToolMode.FREE_TEXT_SPACING_CREATE; 
/* 423 */     if (resId == R.id.controls_fill_and_sign_toolbar_signature)
/* 424 */       return ToolManager.ToolMode.SIGNATURE; 
/* 425 */     if (resId == R.id.controls_fill_and_sign_toolbar_date)
/* 426 */       return ToolManager.ToolMode.FREE_TEXT_DATE_CREATE; 
/* 427 */     if (resId == R.id.controls_fill_and_sign_toolbar_checkmark || resId == R.id.controls_fill_and_sign_toolbar_cross || resId == R.id.controls_fill_and_sign_toolbar_dot)
/*     */     {
/*     */       
/* 430 */       return ToolManager.ToolMode.RUBBER_STAMPER;
/*     */     }
/* 432 */     return ToolManager.ToolMode.PAN;
/*     */   }
/*     */   
/*     */   private int getResourceIdOfTool(ToolManager.ToolMode toolMode, @Nullable String extra) {
/* 436 */     switch (toolMode) {
/*     */       case FORM_TEXT_FIELD_CREATE:
/* 438 */         return R.id.controls_form_field_toolbar_widget_text;
/*     */       case FORM_CHECKBOX_CREATE:
/* 440 */         return R.id.controls_form_field_toolbar_widget_checkbox;
/*     */       case FORM_RADIO_GROUP_CREATE:
/* 442 */         return R.id.controls_form_field_toolbar_widget_radio;
/*     */       case FORM_SIGNATURE_CREATE:
/* 444 */         return R.id.controls_form_field_toolbar_widget_signature;
/*     */       case FORM_LIST_BOX_CREATE:
/* 446 */         return R.id.controls_form_field_toolbar_widget_listbox;
/*     */       case FORM_COMBO_BOX_CREATE:
/* 448 */         return R.id.controls_form_field_toolbar_widget_combobox;
/*     */       case SIGNATURE:
/* 450 */         return R.id.controls_fill_and_sign_toolbar_signature;
/*     */       case FREE_TEXT_SPACING_CREATE:
/* 452 */         return R.id.controls_fill_and_sign_toolbar_text;
/*     */       case FREE_TEXT_DATE_CREATE:
/* 454 */         return R.id.controls_fill_and_sign_toolbar_date;
/*     */       case RUBBER_STAMPER:
/* 456 */         if ("FILL_CHECK".equals(extra))
/* 457 */           return R.id.controls_fill_and_sign_toolbar_checkmark; 
/* 458 */         if ("FILL_CROSS".equals(extra))
/* 459 */           return R.id.controls_fill_and_sign_toolbar_cross; 
/* 460 */         if ("FILL_DOT".equals(extra)) {
/* 461 */           return R.id.controls_fill_and_sign_toolbar_dot;
/*     */         }
/* 463 */         return 0;
/*     */     } 
/*     */ 
/*     */     
/* 467 */     return R.id.controls_annotation_toolbar_tool_pan;
/*     */   }
/*     */ 
/*     */   
/*     */   private int getDrawableIdOfTool(ToolManager.ToolMode toolMode, String extra) {
/* 472 */     switch (toolMode) {
/*     */       case FORM_TEXT_FIELD_CREATE:
/* 474 */         return R.drawable.ic_text_fields_black_24dp;
/*     */       case FORM_CHECKBOX_CREATE:
/* 476 */         return R.drawable.ic_check_box_black_24dp;
/*     */       case FORM_RADIO_GROUP_CREATE:
/* 478 */         return R.drawable.ic_radio_button_checked_black_24dp;
/*     */       case FORM_SIGNATURE_CREATE:
/* 480 */         return R.drawable.ic_annotation_signature_black_24dp;
/*     */       case FORM_LIST_BOX_CREATE:
/* 482 */         return R.drawable.ic_annotation_listbox_black;
/*     */       case FORM_COMBO_BOX_CREATE:
/* 484 */         return R.drawable.ic_annotation_combo_black;
/*     */       case SIGNATURE:
/* 486 */         return R.drawable.ic_annotation_signature_black_24dp;
/*     */       case FREE_TEXT_SPACING_CREATE:
/* 488 */         return R.drawable.ic_fill_and_sign_spacing_text;
/*     */       case FREE_TEXT_DATE_CREATE:
/* 490 */         return R.drawable.ic_date_range_24px;
/*     */       case RUBBER_STAMPER:
/* 492 */         if ("FILL_CHECK".equals(extra))
/* 493 */           return R.drawable.ic_fill_and_sign_checkmark; 
/* 494 */         if ("FILL_CROSS".equals(extra))
/* 495 */           return R.drawable.ic_fill_and_sign_crossmark; 
/* 496 */         if ("FILL_DOT".equals(extra)) {
/* 497 */           return R.drawable.ic_fill_and_sign_dot;
/*     */         }
/* 499 */         return 0;
/*     */     } 
/*     */ 
/*     */     
/* 503 */     return R.drawable.ic_pan_black_24dp;
/*     */   }
/*     */   public static interface FormToolbarListener {
/*     */     void onFormToolbarWillClose(); }
/*     */   private int getAnnotTypeOfResourceId(int resId) {
/* 508 */     if (resId == R.id.controls_form_field_toolbar_widget_text || resId == R.id.controls_form_field_toolbar_widget_checkbox || resId == R.id.controls_form_field_toolbar_widget_radio || resId == R.id.controls_form_field_toolbar_widget_signature || resId == R.id.controls_form_field_toolbar_widget_listbox || resId == R.id.controls_form_field_toolbar_widget_combobox)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 514 */       return 19; } 
/* 515 */     if (resId == R.id.controls_fill_and_sign_toolbar_text)
/* 516 */       return 1010; 
/* 517 */     if (resId == R.id.controls_fill_and_sign_toolbar_signature)
/* 518 */       return 1002; 
/* 519 */     if (resId == R.id.controls_fill_and_sign_toolbar_date)
/* 520 */       return 1011; 
/* 521 */     if (resId == R.id.controls_fill_and_sign_toolbar_checkmark || resId == R.id.controls_fill_and_sign_toolbar_cross || resId == R.id.controls_fill_and_sign_toolbar_dot)
/*     */     {
/*     */       
/* 524 */       return 12;
/*     */     }
/* 526 */     return 28;
/*     */   }
/*     */   
/*     */   private void initButtons() {
/* 530 */     Context context = getContext();
/* 531 */     if (context == null || this.mToolManager == null) {
/*     */       return;
/*     */     }
/*     */     
/* 535 */     initializeButtons();
/*     */     
/* 537 */     ArrayList<ToolItem> tools = new ArrayList<>();
/*     */ 
/*     */     
/* 540 */     addTools(tools, ToolManager.ToolMode.FORM_TEXT_FIELD_CREATE);
/* 541 */     addTools(tools, ToolManager.ToolMode.FORM_SIGNATURE_CREATE);
/* 542 */     addTools(tools, ToolManager.ToolMode.FORM_CHECKBOX_CREATE);
/* 543 */     addTools(tools, ToolManager.ToolMode.FORM_RADIO_GROUP_CREATE);
/* 544 */     addTools(tools, ToolManager.ToolMode.FORM_LIST_BOX_CREATE);
/* 545 */     addTools(tools, ToolManager.ToolMode.FORM_COMBO_BOX_CREATE);
/*     */ 
/*     */     
/* 548 */     addTools(1010, tools, ToolManager.ToolMode.FREE_TEXT_SPACING_CREATE, (String)null);
/* 549 */     addTools(12, tools, ToolManager.ToolMode.SIGNATURE, (String)null);
/* 550 */     addTools(1011, tools, ToolManager.ToolMode.FREE_TEXT_DATE_CREATE, (String)null);
/* 551 */     addTools(12, tools, ToolManager.ToolMode.RUBBER_STAMPER, "FILL_CHECK");
/* 552 */     addTools(12, tools, ToolManager.ToolMode.RUBBER_STAMPER, "FILL_CROSS");
/* 553 */     addTools(12, tools, ToolManager.ToolMode.RUBBER_STAMPER, "FILL_DOT");
/*     */ 
/*     */     
/* 556 */     tools.add(new ToolItem(this, -1, R.id.controls_annotation_toolbar_tool_pan, R.drawable.ic_pan_black_24dp, false));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 561 */     tools.add(new ToolItem(this, -1, R.id.controls_annotation_toolbar_btn_close, R.drawable.ic_close_black_24dp, false, this.mToolbarCloseIconColor));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 566 */     int width = getToolWidth();
/* 567 */     int height = getToolHeight();
/*     */     
/* 569 */     Drawable spinnerBitmapDrawable = getSpinnerBitmapDrawable(context, width, height, this.mToolbarToolBackgroundColor, false);
/*     */     
/* 571 */     Drawable normalBitmapDrawable = getNormalBitmapDrawable(context, width, height, this.mToolbarToolBackgroundColor, false);
/*     */ 
/*     */     
/* 574 */     for (ToolItem tool : tools) {
/* 575 */       setViewDrawable(context, tool.id, tool.spinner, tool.drawable, spinnerBitmapDrawable, normalBitmapDrawable, tool.color);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean hasStyle(ToolManager.ToolMode toolMode) {
/* 582 */     return (ToolManager.ToolMode.FORM_TEXT_FIELD_CREATE == toolMode || ToolManager.ToolMode.FORM_LIST_BOX_CREATE == toolMode || ToolManager.ToolMode.FORM_COMBO_BOX_CREATE == toolMode || ToolManager.ToolMode.FREE_TEXT_SPACING_CREATE == toolMode || ToolManager.ToolMode.FREE_TEXT_DATE_CREATE == toolMode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addTools(ArrayList<ToolItem> tools, ToolManager.ToolMode toolMode) {
/* 590 */     addTools(19, tools, toolMode, (String)null);
/*     */   }
/*     */   
/*     */   private void addTools(int annotType, ArrayList<ToolItem> tools, ToolManager.ToolMode toolMode, @Nullable String extra) {
/* 594 */     tools.add(new ToolItem(this, annotType,
/* 595 */           getResourceIdOfTool(toolMode, extra), 
/* 596 */           getDrawableIdOfTool(toolMode, extra), 
/* 597 */           hasStyle(toolMode)));
/*     */   }
/*     */ 
/*     */   
/*     */   void addButtons() {
/* 602 */     safeAddButtons(R.id.controls_form_field_toolbar_widget_text);
/* 603 */     safeAddButtons(R.id.controls_form_field_toolbar_widget_checkbox);
/* 604 */     safeAddButtons(R.id.controls_form_field_toolbar_widget_signature);
/* 605 */     safeAddButtons(R.id.controls_form_field_toolbar_widget_radio);
/* 606 */     safeAddButtons(R.id.controls_form_field_toolbar_widget_listbox);
/* 607 */     safeAddButtons(R.id.controls_form_field_toolbar_widget_combobox);
/*     */     
/* 609 */     safeAddButtons(R.id.controls_fill_and_sign_toolbar_text);
/* 610 */     safeAddButtons(R.id.controls_fill_and_sign_toolbar_signature);
/* 611 */     safeAddButtons(R.id.controls_fill_and_sign_toolbar_date);
/* 612 */     safeAddButtons(R.id.controls_fill_and_sign_toolbar_checkmark);
/* 613 */     safeAddButtons(R.id.controls_fill_and_sign_toolbar_cross);
/* 614 */     safeAddButtons(R.id.controls_fill_and_sign_toolbar_dot);
/*     */     
/* 616 */     safeAddButtons(R.id.controls_annotation_toolbar_tool_pan);
/* 617 */     safeAddButtons(R.id.controls_annotation_toolbar_btn_close);
/*     */   }
/*     */   
/*     */   private int getToolWidth() {
/* 621 */     View panBtn = findViewById(R.id.controls_annotation_toolbar_tool_pan);
/* 622 */     if (panBtn != null) {
/* 623 */       panBtn.measure(0, 0);
/* 624 */       return panBtn.getMeasuredWidth();
/*     */     } 
/* 626 */     return 0;
/*     */   }
/*     */   
/*     */   private int getToolHeight() {
/* 630 */     View panBtn = findViewById(R.id.controls_annotation_toolbar_tool_pan);
/* 631 */     if (panBtn != null) {
/* 632 */       panBtn.measure(0, 0);
/* 633 */       return panBtn.getMeasuredHeight();
/*     */     } 
/* 635 */     return 0;
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\controls\FormToolbar.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */