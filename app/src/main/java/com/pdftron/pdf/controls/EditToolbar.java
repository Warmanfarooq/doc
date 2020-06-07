/*     */ package com.pdftron.pdf.controls;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.content.res.Configuration;
/*     */ import android.content.res.TypedArray;
/*     */ import android.graphics.PorterDuff;
/*     */ import android.graphics.drawable.BitmapDrawable;
/*     */ import android.graphics.drawable.ColorDrawable;
/*     */ import android.graphics.drawable.Drawable;
/*     */ import android.graphics.drawable.StateListDrawable;
/*     */ import android.util.AttributeSet;
/*     */ import android.view.KeyEvent;
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.widget.ImageButton;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.annotation.RequiresApi;
/*     */ import androidx.appcompat.widget.AppCompatImageButton;
/*     */ import androidx.transition.Slide;
/*     */ import androidx.transition.Transition;
/*     */ import androidx.transition.TransitionManager;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.model.AnnotStyle;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.AnalyticsParam;
/*     */ import com.pdftron.pdf.utils.ShortcutHelper;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import com.pdftron.pdf.utils.ViewerUtils;
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
/*     */ public class EditToolbar
/*     */   extends InsectHandlerToolbar
/*     */ {
/*     */   private static final int ANIMATION_DURATION = 250;
/*     */   private PDFViewCtrl mPdfViewCtrl;
/*     */   private ArrayList<View> mButtons;
/*     */   private int mSelectedButtonId;
/*     */   private ArrayList<AnnotStyle> mDrawStyles;
/*     */   private boolean mLayoutChanged;
/*     */   private boolean mForceUpdateView;
/*  60 */   private int mSelectedToolId = -1;
/*     */   
/*     */   boolean mHasClearButton;
/*     */   
/*     */   boolean mHasEraserButton;
/*     */   
/*     */   boolean mHasUndoRedoButtons;
/*     */   
/*     */   private int mBackgroundColor;
/*     */   
/*     */   private int mToolBackgroundColor;
/*     */   
/*     */   private int mToolIconColor;
/*     */   
/*     */   private int mCloseIconColor;
/*     */   
/*     */   private boolean mShouldExpand;
/*     */   
/*     */   private boolean mIsExpanded;
/*     */   
/*     */   private boolean mIsStyleFixed;
/*     */   
/*     */   private OnToolSelectedListener mOnToolSelectedListener;
/*     */   @Nullable
/*     */   private OnEditToolbarChangedListener mOnEditToolbarChangeListener;
/*     */   
/*     */   public EditToolbar(@NonNull Context context) {
/*  87 */     super(context);
/*  88 */     init(context, (AttributeSet)null, R.attr.edit_toolbar, R.style.EditToolbarStyle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EditToolbar(@NonNull Context context, @Nullable AttributeSet attrs) {
/*  96 */     super(context, attrs);
/*  97 */     init(context, attrs, R.attr.edit_toolbar, R.style.EditToolbarStyle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EditToolbar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
/* 106 */     super(context, attrs, defStyleAttr);
/* 107 */     init(context, attrs, defStyleAttr, R.style.EditToolbarStyle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RequiresApi(api = 21)
/*     */   public EditToolbar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
/* 119 */     super(context, attrs, defStyleAttr, defStyleRes);
/* 120 */     init(context, attrs, defStyleAttr, defStyleRes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void init(@NonNull Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
/* 128 */     TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EditToolbar, defStyleAttr, defStyleRes);
/*     */     try {
/* 130 */       this.mBackgroundColor = typedArray.getColor(R.styleable.EditToolbar_colorBackground, -16777216);
/* 131 */       this.mToolBackgroundColor = typedArray.getColor(R.styleable.EditToolbar_colorToolBackground, -16777216);
/* 132 */       this.mToolIconColor = typedArray.getColor(R.styleable.EditToolbar_colorToolIcon, -1);
/* 133 */       this.mCloseIconColor = typedArray.getColor(R.styleable.EditToolbar_colorCloseIcon, -1);
/*     */     } finally {
/* 135 */       typedArray.recycle();
/*     */     } 
/*     */     
/* 138 */     LayoutInflater.from(context).inflate(R.layout.controls_edit_toolbar_collapsed_layout, (ViewGroup)this);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setup(@NonNull PDFViewCtrl pdfViewCtrl, OnToolSelectedListener onToolSelectedListener, @NonNull ArrayList<AnnotStyle> drawStyles, boolean hasClearButton, boolean hasEraserButton, boolean hasUndoRedoButtons, boolean shouldExpanded, boolean isStyleFixed) {
/* 161 */     this.mPdfViewCtrl = pdfViewCtrl;
/* 162 */     this.mOnToolSelectedListener = onToolSelectedListener;
/* 163 */     this.mDrawStyles = drawStyles;
/* 164 */     this.mHasClearButton = hasClearButton;
/* 165 */     this.mHasEraserButton = hasEraserButton;
/* 166 */     this.mHasUndoRedoButtons = hasUndoRedoButtons;
/* 167 */     this.mShouldExpand = shouldExpanded;
/* 168 */     this.mIsStyleFixed = isStyleFixed;
/*     */     
/* 170 */     this.mSelectedButtonId = R.id.controls_edit_toolbar_tool_style1;
/* 171 */     updateExpanded((getResources().getConfiguration()).orientation);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void show() {
/* 178 */     if (getWidth() != 0) {
/*     */       
/* 180 */       initViews();
/* 181 */       updateButtonsVisibility();
/*     */     } 
/*     */     
/* 184 */     if (getVisibility() != 0) {
/* 185 */       Transition slide = (new Slide(48)).setDuration(250L);
/* 186 */       TransitionManager.beginDelayedTransition((ViewGroup)getParent(), slide);
/* 187 */       setVisibility(0);
/*     */     } 
/*     */     
/* 190 */     if (this.mSelectedToolId != -1) {
/* 191 */       selectTool(this.mSelectedToolId);
/* 192 */       this.mSelectedToolId = -1;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateButtonsVisibility() {
/* 197 */     if (this.mDrawStyles == null) {
/*     */       return;
/*     */     }
/* 200 */     boolean allVisible = canShowAllDrawButtons();
/*     */     
/* 202 */     int visibility = (this.mDrawStyles.size() > 0) ? 0 : 8;
/* 203 */     findViewById(R.id.controls_edit_toolbar_tool_style1).setVisibility(visibility);
/* 204 */     visibility = (allVisible && this.mDrawStyles.size() > 1) ? 0 : 8;
/* 205 */     findViewById(R.id.controls_edit_toolbar_tool_style2).setVisibility(visibility);
/* 206 */     visibility = (allVisible && this.mDrawStyles.size() > 2) ? 0 : 8;
/* 207 */     findViewById(R.id.controls_edit_toolbar_tool_style3).setVisibility(visibility);
/* 208 */     visibility = (allVisible && this.mDrawStyles.size() > 3) ? 0 : 8;
/* 209 */     findViewById(R.id.controls_edit_toolbar_tool_style4).setVisibility(visibility);
/* 210 */     visibility = (allVisible && this.mDrawStyles.size() > 4) ? 0 : 8;
/* 211 */     findViewById(R.id.controls_edit_toolbar_tool_style5).setVisibility(visibility);
/*     */     
/* 213 */     findViewById(R.id.controls_edit_toolbar_tool_clear).setVisibility(this.mHasClearButton ? 0 : 8);
/* 214 */     findViewById(R.id.controls_edit_toolbar_tool_eraser).setVisibility(this.mHasEraserButton ? 0 : 8);
/* 215 */     findViewById(R.id.controls_edit_toolbar_tool_undo).setVisibility(this.mHasUndoRedoButtons ? 0 : 8);
/* 216 */     findViewById(R.id.controls_edit_toolbar_tool_redo).setVisibility(this.mHasUndoRedoButtons ? 0 : 8);
/*     */   }
/*     */   
/*     */   private boolean canShowAllDrawButtons() {
/* 220 */     Context context = getContext();
/* 221 */     return (context != null && (this.mShouldExpand || Utils.isTablet(context) || (Utils.isLandscape(context) && getWidth() > Utils.getRealScreenHeight(context))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onConfigurationChanged(Configuration newConfig) {
/* 229 */     super.onConfigurationChanged(newConfig);
/* 230 */     updateExpanded(newConfig.orientation);
/* 231 */     this.mForceUpdateView = true;
/*     */     
/* 233 */     if (this.mOnEditToolbarChangeListener != null) {
/* 234 */       this.mOnEditToolbarChangeListener.onOrientationChanged();
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
/*     */   protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
/* 247 */     super.onLayout(changed, left, top, right, bottom);
/*     */     
/* 249 */     if (getWidth() == 0 || getHeight() == 0) {
/* 250 */       this.mLayoutChanged = false;
/*     */       
/*     */       return;
/*     */     } 
/* 254 */     if (this.mForceUpdateView && !changed) {
/* 255 */       this.mForceUpdateView = false;
/*     */ 
/*     */       
/* 258 */       initViews();
/*     */     } 
/*     */     
/* 261 */     if (changed) {
/* 262 */       this.mForceUpdateView = false;
/*     */       
/* 264 */       initViews();
/* 265 */       if (!this.mLayoutChanged) {
/* 266 */         updateButtonsVisibility();
/*     */       }
/*     */     } 
/* 269 */     this.mLayoutChanged = changed;
/*     */   }
/*     */ 
/*     */   
/*     */   private void initViews() {
/* 274 */     initButtonsIcons();
/*     */   }
/*     */   private void initButtonsIcons() {
/*     */     BitmapDrawable bitmapDrawable1;
/* 278 */     Context context = getContext();
/* 279 */     if (context == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 284 */     setBackgroundColor(this.mBackgroundColor);
/*     */     
/* 286 */     int size = getResources().getDimensionPixelSize(R.dimen.quick_menu_button_size);
/*     */ 
/*     */     
/* 289 */     BitmapDrawable spinnerBitmapDrawable = ViewerUtils.getBitmapDrawable(context, R.drawable.controls_toolbar_spinner_selected, size, size, this.mToolBackgroundColor, this.mIsExpanded);
/*     */     
/* 291 */     findViewById(R.id.controls_edit_toolbar_tool_style1).setBackground(getStyleBackground(size, size));
/* 292 */     findViewById(R.id.controls_edit_toolbar_tool_style2).setBackground(getStyleBackground(size, size));
/* 293 */     findViewById(R.id.controls_edit_toolbar_tool_style3).setBackground(getStyleBackground(size, size));
/* 294 */     findViewById(R.id.controls_edit_toolbar_tool_style4).setBackground(getStyleBackground(size, size));
/* 295 */     findViewById(R.id.controls_edit_toolbar_tool_style5).setBackground(getStyleBackground(size, size));
/* 296 */     findViewById(R.id.controls_edit_toolbar_tool_eraser).setBackground(
/* 297 */         (Drawable)ViewerUtils.createBackgroundSelector((Drawable)spinnerBitmapDrawable));
/*     */ 
/*     */     
/* 300 */     if (this.mIsExpanded) {
/* 301 */       Drawable normalBitmapDrawable = getResources().getDrawable(R.drawable.rounded_corners);
/* 302 */       normalBitmapDrawable.mutate();
/* 303 */       normalBitmapDrawable.setColorFilter(this.mToolBackgroundColor, PorterDuff.Mode.SRC_ATOP);
/*     */     } else {
/* 305 */       bitmapDrawable1 = ViewerUtils.getBitmapDrawable(context, R.drawable.controls_annotation_toolbar_bg_selected_blue, size, size, this.mToolBackgroundColor, false);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 310 */     findViewById(R.id.controls_edit_toolbar_tool_clear).setBackground(
/* 311 */         (Drawable)ViewerUtils.createBackgroundSelector((Drawable)bitmapDrawable1));
/* 312 */     findViewById(R.id.controls_edit_toolbar_tool_undo).setBackground(
/* 313 */         (Drawable)ViewerUtils.createBackgroundSelector((Drawable)bitmapDrawable1));
/* 314 */     findViewById(R.id.controls_edit_toolbar_tool_redo).setBackground(
/* 315 */         (Drawable)ViewerUtils.createBackgroundSelector((Drawable)bitmapDrawable1));
/* 316 */     findViewById(R.id.controls_edit_toolbar_tool_close).setBackground(
/* 317 */         (Drawable)ViewerUtils.createBackgroundSelector((Drawable)bitmapDrawable1));
/*     */ 
/*     */     
/* 320 */     if (Utils.isRtlLayout(context)) {
/* 321 */       ImageButton undo = (ImageButton)findViewById(R.id.controls_edit_toolbar_tool_undo);
/* 322 */       ImageButton redo = (ImageButton)findViewById(R.id.controls_edit_toolbar_tool_redo);
/* 323 */       Drawable newRedoDrawable = undo.getDrawable();
/* 324 */       Drawable newUndoDrawable = redo.getDrawable();
/* 325 */       redo.setImageDrawable(newRedoDrawable);
/* 326 */       undo.setImageDrawable(newUndoDrawable);
/*     */     } 
/*     */ 
/*     */     
/* 330 */     StateListDrawable stateListDrawable = Utils.createImageDrawableSelector(context, R.drawable.ic_delete_black_24dp, this.mToolIconColor);
/* 331 */     ((AppCompatImageButton)findViewById(R.id.controls_edit_toolbar_tool_clear)).setImageDrawable((Drawable)stateListDrawable);
/* 332 */     stateListDrawable = Utils.createImageDrawableSelector(context, R.drawable.ic_annotation_eraser_black_24dp, this.mToolIconColor);
/* 333 */     ((AppCompatImageButton)findViewById(R.id.controls_edit_toolbar_tool_eraser)).setImageDrawable((Drawable)stateListDrawable);
/* 334 */     stateListDrawable = Utils.createImageDrawableSelector(context, R.drawable.ic_undo_black_24dp, this.mToolIconColor);
/* 335 */     ((AppCompatImageButton)findViewById(R.id.controls_edit_toolbar_tool_undo)).setImageDrawable((Drawable)stateListDrawable);
/* 336 */     stateListDrawable = Utils.createImageDrawableSelector(context, R.drawable.ic_redo_black_24dp, this.mToolIconColor);
/* 337 */     ((AppCompatImageButton)findViewById(R.id.controls_edit_toolbar_tool_redo)).setImageDrawable((Drawable)stateListDrawable);
/*     */ 
/*     */     
/* 340 */     stateListDrawable = Utils.createImageDrawableSelector(context, R.drawable.controls_edit_toolbar_close_24dp, this.mCloseIconColor);
/* 341 */     ((AppCompatImageButton)findViewById(R.id.controls_edit_toolbar_tool_close)).setImageDrawable((Drawable)stateListDrawable);
/*     */     
/* 343 */     updateDrawButtons();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private Drawable getStyleBackground(int width, int height) {
/* 348 */     Context context = getContext();
/* 349 */     if (context == null) {
/* 350 */       return null;
/*     */     }
/*     */     
/* 353 */     if (this.mIsStyleFixed) {
/* 354 */       return (Drawable)new ColorDrawable(getResources().getColor(R.color.tools_quickmenu_button_pressed));
/*     */     }
/* 356 */     BitmapDrawable spinnerBitmapDrawable = ViewerUtils.getBitmapDrawable(context, R.drawable.controls_toolbar_spinner_selected, width, height, this.mToolBackgroundColor, this.mIsExpanded);
/*     */     
/* 358 */     return (Drawable)ViewerUtils.createBackgroundSelector((Drawable)spinnerBitmapDrawable);
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
/*     */   
/*     */   public void updateControlButtons(boolean canClear, boolean canErase, boolean canUndo, boolean canRedo) {
/* 373 */     enableButton(R.id.controls_edit_toolbar_tool_clear, canClear);
/* 374 */     enableButton(R.id.controls_edit_toolbar_tool_eraser, canErase);
/* 375 */     enableButton(R.id.controls_edit_toolbar_tool_undo, canUndo);
/* 376 */     enableButton(R.id.controls_edit_toolbar_tool_redo, canRedo);
/*     */   }
/*     */ 
/*     */   
/*     */   private void enableButton(int id, boolean enabled) {
/* 381 */     if (this.mButtons == null) {
/*     */       return;
/*     */     }
/* 384 */     for (View view : this.mButtons) {
/* 385 */       if (view.getId() == id) {
/* 386 */         view.setEnabled(enabled);
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateDrawStyles(ArrayList<AnnotStyle> drawStyles) {
/* 398 */     this.mDrawStyles = drawStyles;
/* 399 */     updateDrawButtons();
/*     */   }
/*     */   
/*     */   public void updateDrawColor(int styleIndex, int color) {
/* 403 */     switch (styleIndex) {
/*     */       case 0:
/* 405 */         updateDrawButtonColor(R.id.controls_edit_toolbar_tool_style1, color);
/*     */         break;
/*     */       case 1:
/* 408 */         updateDrawButtonColor(R.id.controls_edit_toolbar_tool_style2, color);
/*     */         break;
/*     */       case 2:
/* 411 */         updateDrawButtonColor(R.id.controls_edit_toolbar_tool_style3, color);
/*     */         break;
/*     */       case 3:
/* 414 */         updateDrawButtonColor(R.id.controls_edit_toolbar_tool_style4, color);
/*     */         break;
/*     */       case 4:
/* 417 */         updateDrawButtonColor(R.id.controls_edit_toolbar_tool_style5, color);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateDrawButtons() {
/* 423 */     updateDrawButton(R.id.controls_edit_toolbar_tool_style1, 0);
/* 424 */     updateDrawButton(R.id.controls_edit_toolbar_tool_style2, 1);
/* 425 */     updateDrawButton(R.id.controls_edit_toolbar_tool_style3, 2);
/* 426 */     updateDrawButton(R.id.controls_edit_toolbar_tool_style4, 3);
/* 427 */     updateDrawButton(R.id.controls_edit_toolbar_tool_style5, 4);
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateDrawButton(int id, int styleIndex) {
/* 432 */     if (this.mDrawStyles == null || this.mDrawStyles.size() <= styleIndex) {
/*     */       return;
/*     */     }
/* 435 */     int color = ((AnnotStyle)this.mDrawStyles.get(styleIndex)).getColor();
/* 436 */     updateDrawButtonColor(id, color);
/*     */   }
/*     */   
/*     */   private void updateDrawButtonColor(int id, int color) {
/* 440 */     color = Utils.getPostProcessedColor(this.mPdfViewCtrl, color);
/* 441 */     ImageButton button = (ImageButton)findViewById(id);
/* 442 */     button.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
/*     */   }
/*     */   
/*     */   private void setSelectedButton(int id) {
/* 446 */     this.mSelectedButtonId = id;
/* 447 */     if (this.mButtons == null) {
/*     */       return;
/*     */     }
/* 450 */     for (View view : this.mButtons) {
/* 451 */       if (view.getId() == id) {
/* 452 */         view.setSelected(true); continue;
/*     */       } 
/* 454 */       view.setSelected(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void selectTool(int id) {
/* 465 */     Context context = getContext();
/* 466 */     if (context == null) {
/*     */       return;
/*     */     }
/*     */     
/* 470 */     View button = findViewById(id);
/*     */     
/* 472 */     int drawIndex = getDrawIndex(id);
/* 473 */     int analyticsId = getAnalyticsId(id);
/* 474 */     if (drawIndex >= 0) {
/* 475 */       if (this.mOnToolSelectedListener != null) {
/* 476 */         this.mOnToolSelectedListener.onDrawSelected(drawIndex, (this.mSelectedButtonId == id), button);
/*     */       }
/* 478 */       setSelectedButton(id);
/* 479 */       AnalyticsHandlerAdapter.getInstance().sendEvent(49, 
/* 480 */           AnalyticsParam.editToolbarParam(analyticsId));
/* 481 */     } else if (id == R.id.controls_edit_toolbar_tool_eraser) {
/* 482 */       if (this.mOnToolSelectedListener != null) {
/* 483 */         this.mOnToolSelectedListener.onEraserSelected((this.mSelectedButtonId == id), button);
/*     */       }
/* 485 */       setSelectedButton(id);
/* 486 */       AnalyticsHandlerAdapter.getInstance().sendEvent(49, 
/* 487 */           AnalyticsParam.editToolbarParam(6));
/* 488 */     } else if (id == R.id.controls_edit_toolbar_tool_clear) {
/* 489 */       if (this.mOnToolSelectedListener != null) {
/* 490 */         this.mOnToolSelectedListener.onClearSelected();
/*     */       }
/* 492 */       AnalyticsHandlerAdapter.getInstance().sendEvent(49, 
/* 493 */           AnalyticsParam.editToolbarParam(7));
/* 494 */     } else if (id == R.id.controls_edit_toolbar_tool_undo) {
/* 495 */       if (this.mOnToolSelectedListener != null) {
/* 496 */         this.mOnToolSelectedListener.onUndoSelected();
/*     */       }
/* 498 */       AnalyticsHandlerAdapter.getInstance().sendEvent(49, 
/* 499 */           AnalyticsParam.editToolbarParam(9));
/* 500 */     } else if (id == R.id.controls_edit_toolbar_tool_redo) {
/* 501 */       if (this.mOnToolSelectedListener != null) {
/* 502 */         this.mOnToolSelectedListener.onRedoSelected();
/*     */       }
/* 504 */       AnalyticsHandlerAdapter.getInstance().sendEvent(49, 
/* 505 */           AnalyticsParam.editToolbarParam(10));
/* 506 */     } else if (id == R.id.controls_edit_toolbar_tool_close && 
/* 507 */       this.mSelectedButtonId != id) {
/* 508 */       if (this.mOnToolSelectedListener != null) {
/* 509 */         this.mOnToolSelectedListener.onCloseSelected();
/*     */       }
/* 511 */       AnalyticsHandlerAdapter.getInstance().sendEvent(49, 
/* 512 */           AnalyticsParam.editToolbarParam(8));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private int getDrawIndex(int id) {
/* 518 */     if (id == R.id.controls_edit_toolbar_tool_style1) {
/* 519 */       return 0;
/*     */     }
/* 521 */     if (id == R.id.controls_edit_toolbar_tool_style2) {
/* 522 */       return 1;
/*     */     }
/* 524 */     if (id == R.id.controls_edit_toolbar_tool_style3) {
/* 525 */       return 2;
/*     */     }
/* 527 */     if (id == R.id.controls_edit_toolbar_tool_style4) {
/* 528 */       return 3;
/*     */     }
/* 530 */     if (id == R.id.controls_edit_toolbar_tool_style5) {
/* 531 */       return 4;
/*     */     }
/*     */     
/* 534 */     return -1;
/*     */   }
/*     */   
/*     */   private int getAnalyticsId(int id) {
/* 538 */     if (id == R.id.controls_edit_toolbar_tool_style1) {
/* 539 */       return 1;
/*     */     }
/* 541 */     if (id == R.id.controls_edit_toolbar_tool_style2) {
/* 542 */       return 2;
/*     */     }
/* 544 */     if (id == R.id.controls_edit_toolbar_tool_style3) {
/* 545 */       return 3;
/*     */     }
/* 547 */     if (id == R.id.controls_edit_toolbar_tool_style4) {
/* 548 */       return 4;
/*     */     }
/* 550 */     if (id == R.id.controls_edit_toolbar_tool_style5) {
/* 551 */       return 5;
/*     */     }
/*     */     
/* 554 */     return -1;
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
/*     */   public boolean handleKeyUp(int keyCode, KeyEvent event) {
/* 566 */     if (keyCode == 8 && 
/* 567 */       findViewById(R.id.controls_edit_toolbar_tool_style1).isShown() && 
/* 568 */       ShortcutHelper.isSwitchInk(keyCode, event)) {
/* 569 */       selectTool(R.id.controls_edit_toolbar_tool_style1);
/* 570 */       return true;
/*     */     } 
/*     */     
/* 573 */     if (keyCode == 9 && 
/* 574 */       findViewById(R.id.controls_edit_toolbar_tool_style2).isShown() && 
/* 575 */       ShortcutHelper.isSwitchInk(keyCode, event)) {
/* 576 */       selectTool(R.id.controls_edit_toolbar_tool_style2);
/* 577 */       return true;
/*     */     } 
/*     */     
/* 580 */     if (keyCode == 10 && 
/* 581 */       findViewById(R.id.controls_edit_toolbar_tool_style3).isShown() && 
/* 582 */       ShortcutHelper.isSwitchInk(keyCode, event)) {
/* 583 */       selectTool(R.id.controls_edit_toolbar_tool_style3);
/* 584 */       return true;
/*     */     } 
/*     */     
/* 587 */     if (keyCode == 11 && 
/* 588 */       findViewById(R.id.controls_edit_toolbar_tool_style4).isShown() && 
/* 589 */       ShortcutHelper.isSwitchInk(keyCode, event)) {
/* 590 */       selectTool(R.id.controls_edit_toolbar_tool_style4);
/* 591 */       return true;
/*     */     } 
/*     */     
/* 594 */     if (keyCode == 12 && 
/* 595 */       findViewById(R.id.controls_edit_toolbar_tool_style5).isShown() && 
/* 596 */       ShortcutHelper.isSwitchInk(keyCode, event)) {
/* 597 */       selectTool(R.id.controls_edit_toolbar_tool_style5);
/* 598 */       return true;
/*     */     } 
/*     */     
/* 601 */     if (findViewById(R.id.controls_edit_toolbar_tool_eraser).isShown() && 
/* 602 */       findViewById(R.id.controls_edit_toolbar_tool_eraser).isEnabled() && 
/* 603 */       ShortcutHelper.isEraseInk(keyCode, event)) {
/* 604 */       selectTool(R.id.controls_edit_toolbar_tool_eraser);
/* 605 */       return true;
/*     */     } 
/*     */     
/* 608 */     if (findViewById(R.id.controls_edit_toolbar_tool_undo).isShown() && 
/* 609 */       findViewById(R.id.controls_edit_toolbar_tool_undo).isEnabled() && 
/* 610 */       ShortcutHelper.isUndo(keyCode, event)) {
/* 611 */       selectTool(R.id.controls_edit_toolbar_tool_undo);
/* 612 */       return true;
/*     */     } 
/*     */     
/* 615 */     if (findViewById(R.id.controls_edit_toolbar_tool_redo).isShown() && 
/* 616 */       findViewById(R.id.controls_edit_toolbar_tool_redo).isEnabled() && 
/* 617 */       ShortcutHelper.isRedo(keyCode, event)) {
/* 618 */       selectTool(R.id.controls_edit_toolbar_tool_redo);
/* 619 */       return true;
/*     */     } 
/*     */     
/* 622 */     if (findViewById(R.id.controls_edit_toolbar_tool_close).isShown() && 
/* 623 */       ShortcutHelper.isCommitDraw(keyCode, event)) {
/* 624 */       selectTool(R.id.controls_edit_toolbar_tool_close);
/* 625 */       return true;
/*     */     } 
/*     */     
/* 628 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isExpanded() {
/* 637 */     return this.mIsExpanded;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateExpanded(int orientation) {
/* 644 */     Context context = getContext();
/* 645 */     if (context == null || this.mDrawStyles == null) {
/*     */       return;
/*     */     }
/*     */     
/* 649 */     this
/* 650 */       .mIsExpanded = (this.mShouldExpand && this.mDrawStyles.size() > 1 && orientation == 1 && !Utils.isTablet(context));
/*     */     
/* 652 */     removeAllViews();
/* 653 */     int nextLayoutRes = this.mIsExpanded ? R.layout.controls_edit_toolbar_expanded_layout : R.layout.controls_edit_toolbar_collapsed_layout;
/*     */     
/* 655 */     LayoutInflater.from(context).inflate(nextLayoutRes, (ViewGroup)this);
/*     */     
/* 657 */     collectButtons();
/* 658 */     updateButtonsVisibility();
/* 659 */     setSelectedButton(this.mSelectedButtonId);
/*     */   }
/*     */   
/*     */   private void collectButtons() {
/* 663 */     this.mButtons = new ArrayList<>();
/* 664 */     this.mButtons.add(findViewById(R.id.controls_edit_toolbar_tool_style1));
/* 665 */     this.mButtons.add(findViewById(R.id.controls_edit_toolbar_tool_style2));
/* 666 */     this.mButtons.add(findViewById(R.id.controls_edit_toolbar_tool_style3));
/* 667 */     this.mButtons.add(findViewById(R.id.controls_edit_toolbar_tool_style4));
/* 668 */     this.mButtons.add(findViewById(R.id.controls_edit_toolbar_tool_style5));
/* 669 */     this.mButtons.add(findViewById(R.id.controls_edit_toolbar_tool_clear));
/* 670 */     this.mButtons.add(findViewById(R.id.controls_edit_toolbar_tool_eraser));
/* 671 */     this.mButtons.add(findViewById(R.id.controls_edit_toolbar_tool_redo));
/* 672 */     this.mButtons.add(findViewById(R.id.controls_edit_toolbar_tool_undo));
/* 673 */     this.mButtons.add(findViewById(R.id.controls_edit_toolbar_tool_close));
/*     */     
/* 675 */     for (View view : this.mButtons) {
/* 676 */       if (view != null) {
/* 677 */         view.setOnClickListener(new View.OnClickListener()
/*     */             {
/*     */               public void onClick(View view) {
/* 680 */                 EditToolbar.this.selectTool(view.getId());
/*     */               }
/*     */             });
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   void setOnEditToolbarChangeListener(OnEditToolbarChangedListener listener) {
/* 688 */     this.mOnEditToolbarChangeListener = listener;
/*     */   }
/*     */   
/*     */   static interface OnEditToolbarChangedListener {
/*     */     void onOrientationChanged();
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\controls\EditToolbar.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */