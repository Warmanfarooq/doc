/*     */ package com.pdftron.pdf.controls;
/*     */ 
/*     */ import android.annotation.SuppressLint;
/*     */ import android.app.Dialog;
/*     */ import android.content.Context;
/*     */ import android.content.DialogInterface;
/*     */ import android.content.res.Configuration;
/*     */ import android.content.res.TypedArray;
/*     */ import android.graphics.Rect;
/*     */ import android.graphics.RectF;
/*     */ import android.os.Bundle;
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.MotionEvent;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.view.WindowManager;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.coordinatorlayout.widget.CoordinatorLayout;
/*     */ import androidx.core.view.ViewCompat;
/*     */ import androidx.core.widget.NestedScrollView;
/*     */ import androidx.fragment.app.DialogFragment;
/*     */ import androidx.fragment.app.FragmentManager;
/*     */ import androidx.transition.ChangeBounds;
/*     */ import androidx.transition.Fade;
/*     */ import androidx.transition.Slide;
/*     */ import androidx.transition.Transition;
/*     */ import androidx.transition.TransitionManager;
/*     */ import androidx.transition.TransitionSet;
/*     */ import com.google.android.material.bottomsheet.BottomSheetBehavior;
/*     */ import com.pdftron.pdf.model.AnnotStyle;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.utils.AnalyticsAnnotStylePicker;
/*     */ import com.pdftron.pdf.utils.AnnotationPropertyPreviewView;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import java.lang.annotation.Retention;
/*     */ import java.lang.annotation.RetentionPolicy;
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Set;
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
/*     */ 
/*     */ 
/*     */ public class AnnotStyleDialogFragment
/*     */   extends DialogFragment
/*     */   implements AnnotStyleView.OnPresetSelectedListener, AnnotStyle.AnnotStyleHolder
/*     */ {
/*  72 */   public static final String TAG = AnnotStyleDialogFragment.class.getName();
/*     */   
/*     */   public static final int STROKE_COLOR = 0;
/*     */   
/*     */   public static final int FILL_COLOR = 1;
/*     */   
/*     */   public static final int TEXT_COLOR = 2;
/*     */   
/*     */   public static final int COLOR = 3;
/*     */   
/*     */   protected static final String ARGS_KEY_ANNOT_STYLE = "annotStyle";
/*     */   
/*     */   protected static final String ARGS_KEY_WHITE_LIST_FONT = "whiteListFont";
/*     */   
/*     */   protected static final String ARGS_KEY_ANCHOR = "anchor";
/*     */   
/*     */   protected static final String ARGS_KEY_ANCHOR_SCREEN = "anchor_screen";
/*     */   
/*     */   protected static final String ARGS_KEY_MORE_ANNOT_TYPES = "more_tools";
/*     */   
/*     */   protected static final String ARGS_KEY_PRESSURE_SENSITIVE = "show_pressure_sensitive_preview";
/*     */   
/*     */   private AnnotStyleView mAnnotStyleView;
/*     */   
/*     */   private ColorPickerView mColorPickerView;
/*     */   
/*     */   private CoordinatorLayout.Behavior mDialogBehavior;
/*     */   
/*     */   private AnnotStyle mAnnotStyle;
/*     */   
/*     */   private BottomSheetCallback mBottomSheetCallback;
/*     */   
/*     */   private AnnotStyle.OnAnnotStyleChangeListener mAnnotAppearanceListener;
/*     */   
/*     */   private AnnotationPropertyPreviewView mPreview;
/*     */   
/*     */   private Set<String> mWhiteFontList;
/*     */   
/*     */   private DialogInterface.OnDismissListener mDismissListener;
/*     */   
/*     */   private NestedScrollView mBottomSheet;
/*     */   
/*     */   private Rect mAnchor;
/*     */   
/*     */   private boolean mIsAnchorInScreen;
/*     */   
/*     */   private ArrayList<Integer> mMoreAnnotTypes;
/*     */   
/*     */   private boolean mShowPressureSensitivePreview = false;
/*     */   private AnnotStyleView.OnMoreAnnotTypeClickedListener mMoreAnnotTypesClickListener;
/*     */   private boolean mCanShowRCOption;
/*     */   private boolean mCanShowPressureOption;
/*     */   
/*     */   public static AnnotStyleDialogFragment newInstance() {
/* 126 */     return new AnnotStyleDialogFragment();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOnDismissListener(DialogInterface.OnDismissListener listener) {
/* 135 */     this.mDismissListener = listener;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onSaveInstanceState(Bundle outState) {
/* 145 */     super.onSaveInstanceState(outState);
/* 146 */     outState.putString("annotStyle", this.mAnnotStyle.toJSONString());
/* 147 */     if (this.mWhiteFontList != null) {
/* 148 */       outState.putStringArrayList("whiteListFont", new ArrayList<>(this.mWhiteFontList));
/*     */     }
/* 150 */     if (this.mAnchor != null) {
/* 151 */       Bundle rect = new Bundle();
/* 152 */       rect.putInt("left", this.mAnchor.left);
/* 153 */       rect.putInt("top", this.mAnchor.top);
/* 154 */       rect.putInt("right", this.mAnchor.right);
/* 155 */       rect.putInt("bottom", this.mAnchor.bottom);
/* 156 */       outState.putBundle("anchor", rect);
/*     */     } 
/* 158 */     outState.putBoolean("anchor_screen", this.mIsAnchorInScreen);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onViewStateRestored(Bundle savedInstanceState) {
/* 169 */     super.onViewStateRestored(savedInstanceState);
/* 170 */     if (savedInstanceState != null) {
/* 171 */       String annotStyleJSON = savedInstanceState.getString("annotStyle");
/* 172 */       if (!Utils.isNullOrEmpty(annotStyleJSON)) {
/* 173 */         this.mAnnotStyle = AnnotStyle.loadJSONString(annotStyleJSON);
/*     */       }
/* 175 */       if (savedInstanceState.containsKey("whiteListFont")) {
/* 176 */         ArrayList<String> whiteFontList = savedInstanceState.getStringArrayList("whiteListFont");
/* 177 */         if (whiteFontList != null) {
/* 178 */           this.mWhiteFontList = new LinkedHashSet<>(whiteFontList);
/*     */         }
/*     */       } 
/* 181 */       if (savedInstanceState.containsKey("anchor")) {
/* 182 */         Bundle rect = savedInstanceState.getBundle("anchor");
/* 183 */         if (rect != null) {
/* 184 */           this.mAnchor = new Rect(rect.getInt("left"), rect.getInt("top"), rect.getInt("right"), rect.getInt("bottom"));
/*     */         }
/*     */       } 
/*     */       
/* 188 */       if (savedInstanceState.containsKey("anchor_screen")) {
/* 189 */         this.mIsAnchorInScreen = savedInstanceState.getBoolean("anchor_screen");
/*     */       }
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
/*     */   public void onCreate(Bundle savedInstanceState) {
/* 202 */     super.onCreate(savedInstanceState);
/* 203 */     setCancelable(false);
/* 204 */     Bundle arguments = getArguments();
/* 205 */     if (arguments == null) {
/*     */       return;
/*     */     }
/* 208 */     if (arguments.containsKey("annotStyle")) {
/* 209 */       String annotStyleJSON = arguments.getString("annotStyle");
/* 210 */       if (!Utils.isNullOrEmpty(annotStyleJSON)) {
/* 211 */         this.mAnnotStyle = AnnotStyle.loadJSONString(annotStyleJSON);
/*     */       }
/*     */     } 
/*     */     
/* 215 */     if (arguments.containsKey("whiteListFont")) {
/* 216 */       ArrayList<String> whiteListFont = arguments.getStringArrayList("whiteListFont");
/* 217 */       if (whiteListFont != null) {
/* 218 */         this.mWhiteFontList = new LinkedHashSet<>(whiteListFont);
/*     */       }
/*     */     } 
/*     */     
/* 222 */     if (arguments.containsKey("anchor")) {
/* 223 */       Bundle bundle = arguments.getBundle("anchor");
/* 224 */       if (bundle != null) {
/* 225 */         this.mAnchor = new Rect(bundle.getInt("left"), bundle.getInt("top"), bundle.getInt("right"), bundle.getInt("bottom"));
/*     */       }
/*     */     } 
/*     */     
/* 229 */     if (arguments.containsKey("anchor_screen")) {
/* 230 */       this.mIsAnchorInScreen = arguments.getBoolean("anchor_screen");
/*     */     }
/*     */     
/* 233 */     if (arguments.containsKey("more_tools")) {
/* 234 */       ArrayList<Integer> annotTypes = arguments.getIntegerArrayList("more_tools");
/* 235 */       if (annotTypes != null) {
/* 236 */         this.mMoreAnnotTypes = new ArrayList<>(annotTypes);
/*     */       }
/*     */     } 
/*     */     
/* 240 */     if (arguments.containsKey("show_pressure_sensitive_preview")) {
/* 241 */       this.mShowPressureSensitivePreview = arguments.getBoolean("show_pressure_sensitive_preview");
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
/*     */   @NonNull
/*     */   public Dialog onCreateDialog(Bundle savedInstanceState) {
/* 254 */     Dialog d = new Dialog((Context)getActivity(), R.style.FullScreenDialogStyle)
/*     */       {
/*     */         public void onBackPressed() {
/* 257 */           if (AnnotStyleDialogFragment.this.mAnnotStyleView.getVisibility() == 0) {
/* 258 */             AnnotStyleDialogFragment.this.dismiss();
/*     */           } else {
/* 260 */             AnnotStyleDialogFragment.this.dismissColorPickerView();
/*     */           } 
/*     */         }
/*     */       };
/* 264 */     WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
/* 265 */     if (d.getWindow() != null) {
/* 266 */       lp.copyFrom(d.getWindow().getAttributes());
/* 267 */       lp.width = -1;
/* 268 */       lp.height = -1;
/* 269 */       d.getWindow().setAttributes(lp);
/*     */     } 
/* 271 */     return d;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SuppressLint({"ClickableViewAccessibility"})
/*     */   @Nullable
/*     */   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
/* 281 */     View view = inflater.inflate(R.layout.controls_annot_style_container, container, false);
/* 282 */     this.mAnnotStyleView = (AnnotStyleView)view.findViewById(R.id.annot_style);
/* 283 */     this.mAnnotStyleView.setCanShowRichContentSwitch(this.mCanShowRCOption);
/* 284 */     this.mAnnotStyleView.setCanShowPressureSwitch(this.mCanShowPressureOption);
/* 285 */     this.mColorPickerView = (ColorPickerView)view.findViewById(R.id.color_picker);
/*     */ 
/*     */     
/* 288 */     TypedArray typedArray = getActivity().obtainStyledAttributes(new int[] { R.attr.colorBackgroundLight });
/* 289 */     int background = typedArray.getColor(0, getActivity().getResources().getColor(R.color.controls_annot_style_preview_bg));
/* 290 */     typedArray.recycle();
/*     */     
/* 292 */     this.mPreview = (AnnotationPropertyPreviewView)view.findViewById(R.id.preview);
/* 293 */     this.mPreview.setParentBackgroundColor(background);
/* 294 */     this.mPreview.setShowPressurePreview(this.mShowPressureSensitivePreview);
/*     */     
/* 296 */     this.mBottomSheet = (NestedScrollView)view.findViewById(R.id.bottom_sheet);
/* 297 */     this.mBottomSheet.setOnTouchListener(new View.OnTouchListener()
/*     */         {
/*     */           public boolean onTouch(View view, MotionEvent motionEvent) {
/* 300 */             return AnnotStyleDialogFragment.this.mBottomSheetCallback.isLocked();
/*     */           }
/*     */         });
/*     */     
/* 304 */     adjustBottomSheetWidth();
/*     */     
/* 306 */     this.mBottomSheetCallback = new BottomSheetCallback();
/*     */     
/* 308 */     if (isBottomSheet()) {
/* 309 */       BottomSheetBehavior behavior = new BottomSheetBehavior();
/* 310 */       behavior.setHideable(true);
/* 311 */       behavior.setPeekHeight((int)Utils.convDp2Pix(view.getContext(), 1.0F));
/* 312 */       behavior.setBottomSheetCallback(this.mBottomSheetCallback);
/* 313 */       this.mDialogBehavior = (CoordinatorLayout.Behavior)behavior;
/*     */     } else {
/* 315 */       this.mDialogBehavior = new StyleDialogBehavior();
/*     */     } 
/*     */     
/* 318 */     CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams)this.mBottomSheet.getLayoutParams();
/* 319 */     lp.setBehavior(this.mDialogBehavior);
/*     */     
/* 321 */     this.mColorPickerView.setActivity(getActivity());
/* 322 */     init();
/* 323 */     view.findViewById(R.id.background).setOnClickListener(new View.OnClickListener()
/*     */         {
/*     */           public void onClick(View view) {
/* 326 */             if (AnnotStyleDialogFragment.this.mAnnotStyleView.findFocus() != null && AnnotStyleDialogFragment.this.mAnnotStyleView.findFocus() instanceof android.widget.EditText) {
/* 327 */               AnnotStyleDialogFragment.this.mAnnotStyleView.findFocus().clearFocus();
/*     */             } else {
/* 329 */               AnnotStyleDialogFragment.this.dismiss();
/*     */             } 
/*     */           }
/*     */         });
/*     */     
/* 334 */     return view;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
/* 342 */     super.onViewCreated(view, savedInstanceState);
/* 343 */     view.postDelayed(new Runnable()
/*     */         {
/*     */           public void run() {
/* 346 */             if (AnnotStyleDialogFragment.this.mDialogBehavior instanceof BottomSheetBehavior) {
/* 347 */               ((BottomSheetBehavior)AnnotStyleDialogFragment.this.mDialogBehavior).setState(3);
/*     */             }
/*     */           }
/*     */         },  10L);
/*     */   }
/*     */   
/*     */   private boolean isBottomSheet() {
/* 354 */     return (!Utils.isTablet(this.mBottomSheet.getContext()) || this.mAnchor == null);
/*     */   }
/*     */   
/*     */   private void adjustBottomSheetWidth() {
/* 358 */     CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams)this.mBottomSheet.getLayoutParams();
/* 359 */     View child = this.mBottomSheet.getChildAt(0);
/* 360 */     child.measure(0, 0);
/* 361 */     lp
/* 362 */       .width = (Utils.isLandscape(this.mBottomSheet.getContext()) || Utils.isTablet(this.mBottomSheet.getContext())) ? this.mBottomSheet.getContext().getResources().getDimensionPixelSize(R.dimen.annot_style_picker_width) : -1;
/* 363 */     lp.gravity = isBottomSheet() ? 1 : 3;
/* 364 */     this.mBottomSheet.setLayoutParams((ViewGroup.LayoutParams)lp);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onConfigurationChanged(Configuration newConfig) {
/* 372 */     super.onConfigurationChanged(newConfig);
/* 373 */     adjustBottomSheetWidth();
/*     */   }
/*     */   
/*     */   private void init() {
/* 377 */     this.mAnnotStyleView.setAnnotStyleHolder(this);
/* 378 */     this.mColorPickerView.setAnnotStyleHolder(this);
/* 379 */     this.mAnnotStyleView.setOnPresetSelectedListener(this);
/* 380 */     this.mAnnotStyleView.setOnColorLayoutClickedListener(new AnnotStyleView.OnColorLayoutClickedListener()
/*     */         {
/*     */           public void onColorLayoutClicked(int colorMode) {
/* 383 */             AnnotStyleDialogFragment.this.openColorPickerView(colorMode);
/*     */           }
/*     */         });
/* 386 */     this.mColorPickerView.setOnBackButtonPressedListener(new ColorPickerView.OnBackButtonPressedListener()
/*     */         {
/*     */           public void onBackPressed() {
/* 389 */             AnnotStyleDialogFragment.this.dismissColorPickerView();
/*     */           }
/*     */         });
/* 392 */     if (this.mMoreAnnotTypesClickListener != null) {
/* 393 */       this.mAnnotStyleView.setOnMoreAnnotTypesClickListener(this.mMoreAnnotTypesClickListener);
/*     */     }
/* 395 */     if (this.mWhiteFontList != null && !this.mWhiteFontList.isEmpty()) {
/* 396 */       this.mAnnotStyleView.setWhiteFontList(this.mWhiteFontList);
/*     */     }
/* 398 */     if (this.mMoreAnnotTypes != null) {
/* 399 */       this.mAnnotStyleView.setMoreAnnotTypes(this.mMoreAnnotTypes);
/*     */     }
/* 401 */     this.mAnnotStyleView.setAnnotType(this.mAnnotStyle.getAnnotType());
/* 402 */     this.mAnnotStyleView.updateLayout();
/*     */     
/* 404 */     this.mAnnotStyleView.checkPresets();
/*     */     
/* 406 */     if (this.mAnnotAppearanceListener != null) {
/* 407 */       this.mAnnotStyle.setAnnotAppearanceChangeListener(this.mAnnotAppearanceListener);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setAnnotStyle(AnnotStyle annotStyle) {
/* 412 */     this.mAnnotStyle = annotStyle;
/* 413 */     this.mAnnotStyleView.setAnnotType(annotStyle.getAnnotType());
/* 414 */     this.mAnnotStyleView.updateLayout();
/* 415 */     this.mAnnotStyleView.deselectAllPresetsPreview();
/* 416 */     this.mAnnotStyleView.checkPresets();
/* 417 */     this.mAnnotStyleView.updateAnnotTypes();
/* 418 */     if (this.mAnnotAppearanceListener != null) {
/* 419 */       this.mAnnotStyle.setAnnotAppearanceChangeListener(this.mAnnotAppearanceListener);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setCanShowRichContentSwitch(boolean canShow) {
/* 424 */     this.mCanShowRCOption = canShow;
/* 425 */     if (this.mAnnotStyleView != null) {
/* 426 */       this.mAnnotStyleView.setCanShowRichContentSwitch(canShow);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setCanShowPressureSwitch(boolean canShow) {
/* 431 */     this.mCanShowPressureOption = canShow;
/* 432 */     if (this.mAnnotStyleView != null) {
/* 433 */       this.mAnnotStyleView.setCanShowPressureSwitch(canShow);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOnAnnotStyleChangeListener(AnnotStyle.OnAnnotStyleChangeListener listener) {
/* 443 */     this.mAnnotAppearanceListener = listener;
/*     */   }
/*     */   
/*     */   private void openColorPickerView(int colorMode) {
/* 447 */     TransitionManager.beginDelayedTransition((ViewGroup)this.mBottomSheet, (Transition)getLayoutChangeTransition());
/* 448 */     this.mAnnotStyleView.dismiss();
/* 449 */     this.mColorPickerView.show(colorMode);
/*     */   }
/*     */   
/*     */   private void dismissColorPickerView() {
/* 453 */     TransitionManager.beginDelayedTransition((ViewGroup)this.mBottomSheet, (Transition)getLayoutChangeTransition());
/* 454 */     this.mColorPickerView.dismiss();
/* 455 */     this.mAnnotStyleView.show();
/*     */   }
/*     */   
/*     */   private TransitionSet getLayoutChangeTransition() {
/* 459 */     TransitionSet transition = new TransitionSet();
/* 460 */     transition.addTransition((Transition)new ChangeBounds());
/* 461 */     Slide slideFromEnd = new Slide(8388613);
/* 462 */     slideFromEnd.addTarget((View)this.mColorPickerView);
/* 463 */     transition.addTransition((Transition)slideFromEnd);
/* 464 */     Slide slideFromStart = new Slide(8388611);
/* 465 */     slideFromStart.addTarget((View)this.mAnnotStyleView);
/* 466 */     transition.addTransition((Transition)slideFromStart);
/* 467 */     Fade fade = new Fade();
/* 468 */     fade.setDuration(100L);
/* 469 */     fade.setStartDelay(50L);
/* 470 */     transition.addTransition((Transition)fade);
/* 471 */     return transition;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPresetSelected(AnnotStyle presetStyle) {
/* 482 */     if (this.mAnnotAppearanceListener != null) {
/* 483 */       presetStyle.setAnnotAppearanceChangeListener(this.mAnnotAppearanceListener);
/*     */     }
/*     */     
/* 486 */     if (!presetStyle.equals(this.mAnnotStyle)) {
/* 487 */       presetStyle.updateAllListeners();
/*     */     }
/* 489 */     this.mAnnotStyle = presetStyle;
/* 490 */     this.mAnnotStyleView.updateLayout();
/* 491 */     this.mAnnotStyleView.deselectAllPresetsPreview();
/* 492 */     if (presetStyle.getBindedPreview() != null) {
/* 493 */       presetStyle.getBindedPreview().setSelected(true);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPresetDeselected(AnnotStyle presetStyle) {
/* 504 */     this.mAnnotStyle = new AnnotStyle(presetStyle);
/* 505 */     this.mAnnotStyle.bindPreview(null);
/* 506 */     if (this.mAnnotAppearanceListener != null) {
/* 507 */       this.mAnnotStyle.setAnnotAppearanceChangeListener(this.mAnnotAppearanceListener);
/*     */     }
/* 509 */     this.mAnnotStyleView.deselectAllPresetsPreview();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dismiss(boolean waitBottomSheet) {
/* 518 */     if (waitBottomSheet && this.mDialogBehavior instanceof BottomSheetBehavior) {
/* 519 */       ((BottomSheetBehavior)this.mDialogBehavior).setState(5);
/*     */     } else {
/* 521 */       super.dismiss();
/* 522 */       saveAnnotStyles();
/* 523 */       if (this.mDismissListener != null) {
/* 524 */         this.mDismissListener.onDismiss((DialogInterface)getDialog());
/*     */       }
/* 526 */       AnalyticsAnnotStylePicker.getInstance().dismissAnnotStyleDialog();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveAnnotStyles() {
/* 534 */     this.mAnnotStyleView.savePresets();
/* 535 */     this.mColorPickerView.saveColors();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dismiss() {
/* 543 */     dismiss(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void show(@NonNull FragmentManager fragmentManager, int from, String annotation) {
/* 553 */     AnalyticsAnnotStylePicker.getInstance().showAnnotStyleDialog(from, annotation);
/* 554 */     show(fragmentManager);
/*     */     
/* 556 */     if (this.mDialogBehavior != null && this.mDialogBehavior instanceof StyleDialogBehavior) {
/* 557 */       ((StyleDialogBehavior)this.mDialogBehavior).setShowBottom((from == 2));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void show(@NonNull FragmentManager fragmentManager) {
/* 568 */     if (isAdded()) {
/*     */       return;
/*     */     }
/* 571 */     show(fragmentManager, TAG);
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
/*     */   public AnnotStyle getAnnotStyle() {
/* 583 */     if (this.mAnnotStyle != null)
/* 584 */       return this.mAnnotStyle; 
/* 585 */     if (getArguments() != null && getArguments().containsKey("annotStyle")) {
/* 586 */       String annotStyleJSON = getArguments().getString("annotStyle");
/* 587 */       if (!Utils.isNullOrEmpty(annotStyleJSON)) {
/* 588 */         this.mAnnotStyle = AnnotStyle.loadJSONString(annotStyleJSON);
/*     */       }
/* 590 */       return this.mAnnotStyle;
/*     */     } 
/* 592 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public AnnotationPropertyPreviewView getAnnotPreview() {
/* 597 */     return this.mPreview;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAnnotPreviewVisibility(int visibility) {
/* 602 */     this.mPreview.setVisibility(visibility);
/* 603 */     if (getView() != null) {
/* 604 */       getView().findViewById(R.id.divider).setVisibility(visibility);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOnMoreAnnotTypesClickListener(AnnotStyleView.OnMoreAnnotTypeClickedListener listener) {
/* 614 */     this.mMoreAnnotTypesClickListener = listener;
/* 615 */     if (this.mAnnotStyleView != null) {
/* 616 */       this.mAnnotStyleView.setOnMoreAnnotTypesClickListener(this.mMoreAnnotTypesClickListener);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Retention(RetentionPolicy.SOURCE)
/*     */   public static @interface SelectColorMode {}
/*     */ 
/*     */ 
/*     */   
/*     */   private class BottomSheetCallback
/*     */     extends BottomSheetBehavior.BottomSheetCallback
/*     */   {
/*     */     boolean mLocked = false;
/*     */ 
/*     */     
/*     */     public void onStateChanged(@NonNull View bottomSheet, int newState) {
/* 634 */       if (this.mLocked && (newState == 1 || newState == 4)) {
/*     */         
/* 636 */         ((BottomSheetBehavior)AnnotStyleDialogFragment.this.mDialogBehavior).setState(3);
/* 637 */       } else if (newState == 5) {
/* 638 */         AnnotStyleDialogFragment.this.dismiss(false);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void onSlide(@NonNull View bottomSheet, float slideOffset) {}
/*     */ 
/*     */     
/*     */     public boolean isLocked() {
/* 647 */       return this.mLocked;
/*     */     }
/*     */     
/*     */     public void setLocked(boolean locked) {
/* 651 */       this.mLocked = locked;
/*     */     }
/*     */     
/*     */     private BottomSheetCallback() {} }
/*     */   
/*     */   private class StyleDialogBehavior extends CoordinatorLayout.Behavior<View> {
/*     */     private boolean mIsShowBottom = false;
/*     */     private boolean mInitialized = false;
/*     */     
/*     */     public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
/* 661 */       if (ViewCompat.getFitsSystemWindows((View)parent) && !ViewCompat.getFitsSystemWindows(child)) {
/* 662 */         child.setFitsSystemWindows(true);
/*     */       }
/*     */       
/* 665 */       parent.onLayoutChild(child, layoutDirection);
/*     */       
/* 667 */       int margin = AnnotStyleDialogFragment.this.mBottomSheet.getContext().getResources().getDimensionPixelSize(R.dimen.padding_large);
/* 668 */       Rect anchor = new Rect(AnnotStyleDialogFragment.this.mAnchor);
/*     */       
/* 670 */       if (AnnotStyleDialogFragment.this.mIsAnchorInScreen) {
/* 671 */         int[] parentScreenPos = new int[2];
/* 672 */         parent.getLocationOnScreen(parentScreenPos);
/* 673 */         anchor.offset(-parentScreenPos[0], -parentScreenPos[1]);
/*     */       } 
/* 675 */       int midAnchorX = anchor.left + anchor.width() / 2;
/* 676 */       int midAnchorY = anchor.top + anchor.height() / 2;
/*     */       
/* 678 */       int midPosY = midAnchorY - child.getHeight() / 2;
/* 679 */       int midPosX = midAnchorX - child.getWidth() / 2;
/*     */       
/* 681 */       int posX = 0;
/* 682 */       int posY = 0;
/* 683 */       boolean showTop = !this.mIsShowBottom;
/* 684 */       boolean showBottom = this.mIsShowBottom;
/* 685 */       boolean showLeft = false;
/* 686 */       boolean showRight = false;
/* 687 */       if (showTop) {
/* 688 */         posX = midPosX;
/* 689 */         posY = anchor.top - margin - child.getHeight();
/* 690 */         if (!this.mInitialized) {
/* 691 */           this.mIsShowBottom = showBottom = (posY < margin);
/* 692 */           showTop = !showBottom;
/*     */         } 
/*     */       } 
/*     */       
/* 696 */       if (showBottom) {
/* 697 */         posX = midPosX;
/* 698 */         posY = anchor.bottom + margin;
/* 699 */         showLeft = (posY + child.getHeight() > parent.getHeight());
/* 700 */         showBottom = !showLeft;
/*     */       } 
/*     */       
/* 703 */       if (showLeft) {
/* 704 */         posX = anchor.left - margin - child.getWidth();
/* 705 */         posY = (anchor.top < margin) ? midPosY : anchor.top;
/* 706 */         showRight = (posX < 0);
/* 707 */         showLeft = !showRight;
/*     */       } 
/*     */       
/* 710 */       if (showRight) {
/* 711 */         posX = anchor.right + margin;
/* 712 */         posY = (anchor.top < margin) ? midPosY : anchor.top;
/* 713 */         showRight = (posX + child.getWidth() <= parent.getWidth());
/*     */       } 
/*     */       
/* 716 */       if (!showTop && !showBottom && !showLeft && !showRight) {
/* 717 */         posX = midPosX;
/* 718 */         posY = midPosY;
/*     */       } 
/*     */       
/* 721 */       if (posX < margin) {
/* 722 */         posX = margin;
/* 723 */       } else if (posX + child.getWidth() > parent.getWidth() - margin) {
/* 724 */         posX = parent.getWidth() - child.getWidth() - margin;
/*     */       } 
/*     */ 
/*     */       
/* 728 */       if (posY < 2 * margin) {
/* 729 */         posY = 2 * margin;
/* 730 */       } else if (posY + child.getHeight() > parent.getHeight()) {
/* 731 */         posY = parent.getHeight() - child.getHeight();
/*     */       } 
/*     */       
/* 734 */       this.mInitialized = true;
/*     */       
/* 736 */       ViewCompat.offsetTopAndBottom(child, posY);
/* 737 */       ViewCompat.offsetLeftAndRight(child, posX);
/*     */       
/* 739 */       return true;
/*     */     }
/*     */     
/*     */     void setShowBottom(boolean showBottom) {
/* 743 */       this.mIsShowBottom = showBottom;
/* 744 */       this.mInitialized = true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private StyleDialogBehavior() {}
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Builder
/*     */   {
/*     */     Bundle bundle;
/*     */ 
/*     */     
/*     */     public Builder() {
/* 759 */       this.bundle = new Bundle();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder(AnnotStyle annotStyle) {
/* 768 */       this.bundle = new Bundle();
/* 769 */       setAnnotStyle(annotStyle);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setShowPressureSensitivePreview(boolean showPressurePreview) {
/* 778 */       this.bundle.putBoolean("show_pressure_sensitive_preview", showPressurePreview);
/* 779 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setAnnotStyle(AnnotStyle annotStyle) {
/* 789 */       this.bundle.putString("annotStyle", annotStyle.toJSONString());
/* 790 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setWhiteListFont(@Nullable Set<String> whiteListFont) {
/* 800 */       if (whiteListFont != null) {
/* 801 */         ArrayList<String> whiteListFontArr = new ArrayList<>(whiteListFont);
/* 802 */         this.bundle.putStringArrayList("whiteListFont", whiteListFontArr);
/*     */       } 
/* 804 */       return this;
/*     */     }
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
/*     */     public Builder setAnchor(RectF anchor) {
/* 825 */       Bundle rect = new Bundle();
/* 826 */       rect.putInt("left", (int)anchor.left);
/* 827 */       rect.putInt("top", (int)anchor.top);
/* 828 */       rect.putInt("right", (int)anchor.right);
/* 829 */       rect.putInt("bottom", (int)anchor.bottom);
/* 830 */       this.bundle.putBundle("anchor", rect);
/* 831 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setAnchor(Rect anchor) {
/* 842 */       Bundle rect = new Bundle();
/* 843 */       rect.putInt("left", anchor.left);
/* 844 */       rect.putInt("top", anchor.top);
/* 845 */       rect.putInt("right", anchor.right);
/* 846 */       rect.putInt("bottom", anchor.bottom);
/* 847 */       this.bundle.putBundle("anchor", rect);
/* 848 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setAnchorView(View view) {
/* 858 */       int[] pos = new int[2];
/* 859 */       view.getLocationInWindow(pos);
/* 860 */       return setAnchor(new Rect(pos[0], pos[1], pos[0] + view.getWidth(), pos[1] + view.getHeight()));
/*     */     }
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
/*     */     public Builder setAnchorInScreen(Rect anchor) {
/* 880 */       setAnchor(anchor);
/* 881 */       this.bundle.putBoolean("anchor_screen", true);
/* 882 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setMoreAnnotTypes(@Nullable ArrayList<Integer> annotTypes) {
/* 891 */       if (annotTypes != null) {
/* 892 */         this.bundle.putIntegerArrayList("more_tools", annotTypes);
/*     */       }
/* 894 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public AnnotStyleDialogFragment build() {
/* 903 */       AnnotStyleDialogFragment annotStyleDialogFragment = AnnotStyleDialogFragment.newInstance();
/* 904 */       annotStyleDialogFragment.setArguments(this.bundle);
/* 905 */       return annotStyleDialogFragment;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\controls\AnnotStyleDialogFragment.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */