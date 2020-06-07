/*     */ package com.pdftron.pdf.controls;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.graphics.PorterDuff;
/*     */ import android.graphics.drawable.BitmapDrawable;
/*     */ import android.graphics.drawable.Drawable;
/*     */ import android.graphics.drawable.StateListDrawable;
/*     */ import android.util.AttributeSet;
/*     */ import android.view.MotionEvent;
/*     */ import android.view.PointerIcon;
/*     */ import android.view.View;
/*     */ import androidx.annotation.DrawableRes;
/*     */ import androidx.annotation.IdRes;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.annotation.RequiresApi;
/*     */ import androidx.appcompat.widget.AppCompatImageButton;
/*     */ import androidx.appcompat.widget.TooltipCompat;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.tools.ToolManager;
/*     */ import com.pdftron.pdf.utils.AnnotUtils;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import com.pdftron.pdf.utils.ViewerUtils;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ public abstract class BaseToolbar
/*     */   extends InsectHandlerToolbar
/*     */ {
/*     */   protected int mToolbarBackgroundColor;
/*     */   protected int mToolbarToolBackgroundColor;
/*     */   protected int mToolbarToolIconColor;
/*     */   protected int mToolbarCloseIconColor;
/*     */   protected ToolManager mToolManager;
/*     */   protected int mSelectedButtonId;
/*  36 */   protected int mSelectedToolId = -1;
/*     */   protected ArrayList<View> mButtons;
/*     */   protected boolean mButtonStayDown;
/*     */   protected boolean mEventAction;
/*     */   
/*     */   public BaseToolbar(@NonNull Context context) {
/*  42 */     super(context);
/*     */   }
/*     */   
/*     */   public BaseToolbar(@NonNull Context context, @Nullable AttributeSet attrs) {
/*  46 */     super(context, attrs);
/*     */   }
/*     */   
/*     */   public BaseToolbar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
/*  50 */     super(context, attrs, defStyleAttr);
/*     */   }
/*     */   
/*     */   @RequiresApi(api = 21)
/*     */   public BaseToolbar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
/*  55 */     super(context, attrs, defStyleAttr, defStyleRes);
/*     */   }
/*     */   
/*     */   public abstract void selectTool(View paramView, int paramInt);
/*     */   
/*     */   public boolean isShowing() {
/*  61 */     return (getVisibility() == 0);
/*     */   }
/*     */   
/*     */   abstract void addButtons();
/*     */   
/*     */   protected Drawable getSpinnerBitmapDrawable(Context context, int width, int height, int color, boolean expanded) {
/*  67 */     int spinnerDrawableId = R.drawable.controls_toolbar_spinner_selected_blue;
/*  68 */     return (Drawable)ViewerUtils.getBitmapDrawable(context, spinnerDrawableId, width, height, color, expanded, true);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Drawable getNormalBitmapDrawable(Context context, int width, int height, int color, boolean expanded) {
/*     */     BitmapDrawable bitmapDrawable;
/*  74 */     if (expanded) {
/*  75 */       Drawable normalBitmapDrawable = Utils.getDrawable(context, R.drawable.rounded_corners);
/*  76 */       if (normalBitmapDrawable != null) {
/*  77 */         normalBitmapDrawable = normalBitmapDrawable.mutate();
/*  78 */         normalBitmapDrawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
/*     */       } 
/*     */     } else {
/*  81 */       bitmapDrawable = ViewerUtils.getBitmapDrawable(context, R.drawable.controls_annotation_toolbar_bg_selected_blue, width, height, color, false, true);
/*     */     } 
/*     */     
/*  84 */     return (Drawable)bitmapDrawable;
/*     */   }
/*     */   
/*     */   protected void setViewDrawable(Context context, int id, boolean spinner, int resId, Drawable spinnerBitmapDrawable, Drawable normalBitmapDrawable, int iconColor) {
/*  88 */     View v = findViewById(id);
/*  89 */     if (v != null) {
/*  90 */       v.setBackground((Drawable)ViewerUtils.createBackgroundSelector(spinner ? spinnerBitmapDrawable : normalBitmapDrawable));
/*  91 */       StateListDrawable stateListDrawable = Utils.createImageDrawableSelector(context, resId, iconColor);
/*  92 */       ((AppCompatImageButton)v).setImageDrawable((Drawable)stateListDrawable);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void safeAddButtons(@IdRes int viewId) {
/*  97 */     View v = findViewById(viewId);
/*  98 */     if (v != null) {
/*  99 */       this.mButtons.add(v);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void initializeButtons() {
/* 104 */     this.mButtons = new ArrayList<>();
/* 105 */     addButtons();
/*     */     
/* 107 */     View.OnClickListener clickListener = getButtonsClickListener();
/* 108 */     for (View view : this.mButtons) {
/* 109 */       if (view != null) {
/* 110 */         view.setOnClickListener(clickListener);
/* 111 */         TooltipCompat.setTooltipText(view, view.getContentDescription());
/* 112 */         if (Utils.isNougat()) {
/* 113 */           view.setOnGenericMotionListener(new View.OnGenericMotionListener()
/*     */               {
/*     */                 public boolean onGenericMotion(View view, MotionEvent motionEvent) {
/* 116 */                   Context context = BaseToolbar.this.getContext();
/* 117 */                   if (context == null) {
/* 118 */                     return false;
/*     */                   }
/*     */                   
/* 121 */                   if (view.isShown() && Utils.isNougat()) {
/* 122 */                     BaseToolbar.this.mToolManager.onChangePointerIcon(PointerIcon.getSystemIcon(context, 1002));
/*     */                   }
/* 124 */                   return true;
/*     */                 }
/*     */               });
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected View.OnClickListener getButtonsClickListener() {
/* 133 */     return new View.OnClickListener()
/*     */       {
/*     */         public void onClick(View view) {
/* 136 */           BaseToolbar.this.selectTool(view, view.getId());
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   protected void selectButton(int id) {
/* 142 */     this.mSelectedButtonId = id;
/* 143 */     for (View view : this.mButtons) {
/* 144 */       if (view.getId() == id) {
/* 145 */         view.setSelected(true); continue;
/*     */       } 
/* 147 */       view.setSelected(false);
/*     */     } 
/*     */     
/* 150 */     if (this.mToolManager.isSkipNextTapEvent())
/*     */     {
/*     */       
/* 153 */       this.mToolManager.resetSkipNextTapEvent();
/*     */     }
/*     */   }
/*     */   
/*     */   class ToolItem {
/*     */     int color;
/*     */     @IdRes
/*     */     int id;
/*     */     @DrawableRes
/*     */     int drawable;
/*     */     boolean spinner;
/*     */     
/*     */     ToolItem(@IdRes int type, int id, boolean spinner) {
/* 166 */       this(type, id, AnnotUtils.getAnnotImageResId(type), spinner);
/*     */     }
/*     */     
/*     */     ToolItem(@IdRes int type, @DrawableRes int id, int drawable, boolean spinner) {
/* 170 */       this(type, id, drawable, spinner, BaseToolbar.this.mToolbarToolIconColor);
/*     */     }
/*     */     
/*     */     ToolItem(@IdRes int type, @DrawableRes int id, int drawable, boolean spinner, int color) {
/* 174 */       this.id = id;
/* 175 */       this.drawable = drawable;
/* 176 */       this.spinner = spinner;
/* 177 */       this.color = color;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\controls\BaseToolbar.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */