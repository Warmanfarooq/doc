/*     */ package com.pdftron.pdf.tools;
/*     */ 
/*     */ import android.annotation.TargetApi;
/*     */ import android.content.Context;
/*     */ import android.content.Intent;
/*     */ import android.content.res.ColorStateList;
/*     */ import android.content.res.TypedArray;
/*     */ import android.graphics.PorterDuff;
/*     */ import android.graphics.drawable.Drawable;
/*     */ import android.view.ActionProvider;
/*     */ import android.view.ContextMenu;
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.MenuItem;
/*     */ import android.view.SubMenu;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.widget.Button;
/*     */ import android.widget.ImageButton;
/*     */ import android.widget.ImageView;
/*     */ import android.widget.RelativeLayout;
/*     */ import android.widget.TextView;
/*     */ import androidx.annotation.ColorInt;
/*     */ import androidx.annotation.IdRes;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.annotation.RestrictTo;
/*     */ import androidx.appcompat.widget.AppCompatImageButton;
/*     */ import androidx.appcompat.widget.TooltipCompat;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import java.lang.annotation.Retention;
/*     */ import java.lang.annotation.RetentionPolicy;
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
/*     */ public class QuickMenuItem
/*     */   implements MenuItem, View.OnClickListener
/*     */ {
/*     */   public static final int FIRST_ROW_MENU = 0;
/*     */   public static final int SECOND_ROW_MENU = 1;
/*     */   public static final int OVERFLOW_ROW_MENU = 2;
/*     */   public static final int ORDER_START = -1;
/*     */   private String mTitle;
/*     */   private Drawable mIcon;
/*     */   private int mDisPlayMode;
/*  79 */   private int mIconColor = -1;
/*     */   private float mOpacity;
/*     */   private boolean mHasCustomOpacity;
/*     */   private Context mContext;
/*  83 */   private int mId = -1;
/*  84 */   private int mOrder = 0;
/*     */   private ColorStateList mIconTintList;
/*     */   private PorterDuff.Mode mIconTintMode;
/*     */   private CharSequence mTitleCondensed;
/*     */   private boolean mIsVisible = true;
/*     */   private boolean mIsEnable = true;
/*  90 */   private QuickMenuBuilder mSubMenu = null;
/*  91 */   private View mButton = null;
/*  92 */   private OnMenuItemClickListener mMenuItemClickListener = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean mAllCaps = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QuickMenuItem(Context context, String text, int displayMode, @ColorInt int color, float opacity) {
/* 104 */     this(context, text, displayMode);
/* 105 */     this.mIconColor = color;
/* 106 */     this.mOpacity = opacity;
/* 107 */     this.mHasCustomOpacity = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QuickMenuItem(Context context, @IdRes int itemId, int displayMode) {
/* 117 */     this(context, "", displayMode);
/* 118 */     setItemId(itemId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QuickMenuItem(Context context, String text, int displayMode) {
/* 128 */     this.mContext = context;
/* 129 */     this.mTitle = text;
/* 130 */     this.mDisPlayMode = displayMode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QuickMenuItem(Context context, @IdRes int id) {
/* 139 */     this(context, "");
/* 140 */     setItemId(id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QuickMenuItem(Context context, String text) {
/* 149 */     this(context, text, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AppCompatImageButton createImageButton() {
/* 158 */     AppCompatImageButton imageButton = new AppCompatImageButton(this.mContext);
/* 159 */     loadAttributes((View)imageButton);
/* 160 */     if (hasIcon()) {
/* 161 */       imageButton.setImageDrawable(getIcon());
/*     */     }
/* 163 */     if (hasColor()) {
/* 164 */       imageButton.setColorFilter(this.mIconColor);
/*     */     }
/* 166 */     if (hasOpacity()) {
/* 167 */       imageButton.setAlpha(this.mOpacity);
/*     */     }
/* 169 */     if (!Utils.isNullOrEmpty(this.mTitle)) {
/* 170 */       TooltipCompat.setTooltipText((View)imageButton, this.mTitle);
/* 171 */       imageButton.setContentDescription(this.mTitle);
/*     */     } 
/* 173 */     imageButton.setLayoutParams((ViewGroup.LayoutParams)new RelativeLayout.LayoutParams(this.mContext
/*     */           
/* 175 */           .getResources().getDimensionPixelSize(R.dimen.quick_menu_button_size), this.mContext
/* 176 */           .getResources().getDimensionPixelSize(R.dimen.quick_menu_button_size)));
/* 177 */     imageButton.setPadding(this.mContext.getResources().getDimensionPixelSize(R.dimen.quick_menu_button_padding), this.mContext
/* 178 */         .getResources().getDimensionPixelSize(R.dimen.quick_menu_button_padding), this.mContext
/* 179 */         .getResources().getDimensionPixelSize(R.dimen.quick_menu_button_padding), this.mContext
/* 180 */         .getResources().getDimensionPixelSize(R.dimen.quick_menu_button_padding));
/* 181 */     imageButton.setOnClickListener(this);
/* 182 */     this.mButton = (View)imageButton;
/* 183 */     return imageButton;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Button createTextButton() {
/* 192 */     Button button = new Button(this.mContext);
/* 193 */     loadAttributes((View)button);
/* 194 */     if (hasOpacity()) {
/* 195 */       button.setAlpha(this.mOpacity);
/*     */     }
/* 197 */     if (!Utils.isNullOrEmpty(this.mTitle)) {
/* 198 */       String title = this.mAllCaps ? this.mTitle.toUpperCase() : this.mTitle;
/* 199 */       button.setText(title);
/*     */     } 
/* 201 */     button.setTextAppearance(this.mContext, 16974253);
/* 202 */     button.setLayoutParams((ViewGroup.LayoutParams)new RelativeLayout.LayoutParams(-2, 
/* 203 */           (int)Utils.convDp2Pix(this.mContext, 48.0F)));
/* 204 */     button.setPadding(this.mContext.getResources().getDimensionPixelSize(R.dimen.quick_menu_button_padding), 0, this.mContext
/*     */         
/* 206 */         .getResources().getDimensionPixelSize(R.dimen.quick_menu_button_padding), 0);
/*     */ 
/*     */     
/* 209 */     int gravity = Utils.isRtlLayout(this.mContext) ? 8388613 : 8388611;
/* 210 */     button.setGravity(0x10 | gravity);
/* 211 */     button.setSingleLine(true);
/* 212 */     button.setOnClickListener(this);
/* 213 */     this.mButton = (View)button;
/* 214 */     return button;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public View createSubMenuView() {
/* 223 */     LayoutInflater inflater = LayoutInflater.from(this.mContext);
/* 224 */     View rootView = inflater.inflate(R.layout.quick_menu_submenu_item, null);
/* 225 */     TextView text = (TextView)rootView.findViewById(R.id.submenu_item_text);
/* 226 */     ImageView icon = (ImageView)rootView.findViewById(R.id.submenu_item_image);
/* 227 */     if (!Utils.isNullOrEmpty(this.mTitle)) {
/* 228 */       String title = this.mAllCaps ? this.mTitle.toUpperCase() : this.mTitle;
/* 229 */       text.setText(title);
/*     */     } 
/* 231 */     if (Utils.isRtlLayout(this.mContext) && 
/* 232 */       Utils.isJellyBeanMR1()) {
/* 233 */       rootView.setLayoutDirection(1);
/* 234 */       text.setGravity(8388629);
/*     */     } 
/*     */ 
/*     */     
/* 238 */     loadAttributes((View)text);
/* 239 */     loadAttributes((View)icon);
/*     */     
/* 241 */     text.setBackground(null);
/* 242 */     icon.setBackground(null);
/* 243 */     rootView.setOnClickListener(this);
/* 244 */     return rootView;
/*     */   }
/*     */   
/*     */   private void loadAttributes(View v) {
/* 248 */     TypedArray a = this.mContext.obtainStyledAttributes(null, R.styleable.QuickMenuItem, R.attr.quick_menu_item, R.style.QuickMenuButton);
/*     */     
/*     */     try {
/* 251 */       int tintColor = a.getColor(R.styleable.QuickMenuItem_android_tint, Utils.getThemeAttrColor(this.mContext, 16842806));
/* 252 */       float opacity = 1.0F;
/* 253 */       if (v instanceof ImageView) {
/* 254 */         ((ImageView)v).setColorFilter(tintColor);
/* 255 */         opacity = a.getFloat(R.styleable.QuickMenuItem_icon_alpha, 0.54F);
/* 256 */       } else if (v instanceof TextView) {
/* 257 */         ((TextView)v).setTextColor(tintColor);
/* 258 */         opacity = a.getFloat(R.styleable.QuickMenuItem_text_alpha, 0.87F);
/*     */       } 
/* 260 */       v.setAlpha(opacity);
/*     */ 
/*     */       
/*     */       try {
/* 264 */         Drawable background = a.getDrawable(R.styleable.QuickMenuItem_android_background);
/* 265 */         if (background != null) {
/* 266 */           v.setBackground(background);
/*     */         }
/* 268 */       } catch (UnsupportedOperationException e) {
/* 269 */         v.setBackground(this.mContext.getResources().getDrawable(R.drawable.btn_borderless));
/*     */       } 
/*     */ 
/*     */       
/* 273 */       this.mAllCaps = a.getBoolean(R.styleable.QuickMenuItem_android_textAllCaps, true);
/*     */     } finally {
/*     */       
/* 276 */       a.recycle();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onClick(View v) {
/* 287 */     if (this.mMenuItemClickListener != null) {
/* 288 */       this.mMenuItemClickListener.onMenuItemClick(this);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasIcon() {
/* 298 */     return (this.mIcon != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getText() {
/* 308 */     return this.mTitle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDisplayMode() {
/* 318 */     return this.mDisPlayMode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ColorInt
/*     */   public int getIconColor() {
/* 328 */     return this.mIconColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setColor(@ColorInt int iconColor) {
/* 337 */     this.mIconColor = iconColor;
/* 338 */     if (this.mButton != null) {
/* 339 */       if (this.mButton instanceof ImageButton) {
/* 340 */         ((ImageButton)this.mButton).setColorFilter(iconColor);
/* 341 */       } else if (this.mButton instanceof Button) {
/* 342 */         ((Button)this.mButton).setTextColor(iconColor);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasColor() {
/* 353 */     return (this.mIconColor != -1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getOpacity() {
/* 362 */     return this.mOpacity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOpacity(float opacity) {
/* 371 */     this.mOpacity = opacity;
/* 372 */     this.mHasCustomOpacity = true;
/* 373 */     if (this.mButton != null) {
/* 374 */       this.mButton.setAlpha(opacity);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasOpacity() {
/* 384 */     return this.mHasCustomOpacity;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 389 */     if (obj instanceof QuickMenuItem && (
/* 390 */       (QuickMenuItem)obj).getItemId() == this.mId) {
/* 391 */       return true;
/*     */     }
/*     */     
/* 394 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getItemId() {
/* 404 */     return this.mId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MenuItem setItemId(@IdRes int id) {
/* 414 */     this.mId = id;
/* 415 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOrder() {
/* 425 */     if (this.mOrder == 0)
/* 426 */       return -1; 
/* 427 */     if (this.mOrder == -1) {
/* 428 */       return 0;
/*     */     }
/* 430 */     return this.mOrder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictTo({RestrictTo.Scope.SUBCLASSES})
/*     */   public int getGroupId() {
/* 440 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MenuItem setOrder(int order) {
/* 450 */     this.mOrder = order;
/* 451 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MenuItem setTitle(CharSequence title) {
/* 462 */     this.mTitle = title.toString();
/* 463 */     if (this.mButton != null && this.mButton instanceof Button) {
/* 464 */       ((Button)this.mButton).setText(this.mTitle);
/*     */     }
/* 466 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MenuItem setTitle(int title) {
/* 477 */     this.mTitle = this.mContext.getString(title);
/* 478 */     if (this.mButton != null && this.mButton instanceof Button) {
/* 479 */       ((Button)this.mButton).setText(this.mTitle);
/*     */     }
/* 481 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CharSequence getTitle() {
/* 491 */     return getText();
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
/*     */   public MenuItem setTitleCondensed(CharSequence title) {
/* 504 */     this.mTitleCondensed = title;
/* 505 */     return this;
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
/*     */   public CharSequence getTitleCondensed() {
/* 517 */     return this.mTitleCondensed;
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
/*     */   public MenuItem setIcon(Drawable icon) {
/* 530 */     this.mIcon = icon;
/* 531 */     if (this.mIconTintList != null) {
/* 532 */       setIconTintList(this.mIconTintList);
/*     */     }
/* 534 */     if (this.mIconTintList != null) {
/* 535 */       setIconTintMode(this.mIconTintMode);
/*     */     }
/* 537 */     if (this.mButton != null && this.mButton instanceof ImageButton) {
/* 538 */       ((ImageButton)this.mButton).setImageDrawable(this.mIcon);
/*     */     }
/* 540 */     return this;
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
/*     */   public MenuItem setIcon(int iconRes) {
/* 556 */     if (iconRes != 0) {
/* 557 */       setIcon(Utils.getDrawable(this.mContext, iconRes));
/*     */     }
/* 559 */     return this;
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
/*     */   @TargetApi(21)
/*     */   public MenuItem setIconTintList(@Nullable ColorStateList tint) {
/* 577 */     if (this.mIcon != null) {
/* 578 */       this.mIcon.mutate();
/* 579 */       this.mIcon.setTintList(tint);
/*     */     } 
/* 581 */     this.mIconTintList = tint;
/* 582 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ColorStateList getIconTintList() {
/* 592 */     return this.mIconTintList;
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
/*     */   @TargetApi(21)
/*     */   public MenuItem setIconTintMode(@Nullable PorterDuff.Mode tintMode) {
/* 608 */     if (this.mIcon != null) {
/* 609 */       this.mIcon.mutate();
/* 610 */       this.mIcon.setTintMode(tintMode);
/*     */     } 
/* 612 */     this.mIconTintMode = tintMode;
/* 613 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public PorterDuff.Mode getIconTintMode() {
/* 625 */     return this.mIconTintMode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 635 */     return this.mTitle;
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
/*     */   public Drawable getIcon() {
/* 649 */     return this.mIcon;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictTo({RestrictTo.Scope.SUBCLASSES})
/*     */   public MenuItem setIntent(Intent intent) {
/* 658 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictTo({RestrictTo.Scope.SUBCLASSES})
/*     */   public Intent getIntent() {
/* 667 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictTo({RestrictTo.Scope.SUBCLASSES})
/*     */   public MenuItem setShortcut(char numericChar, char alphaChar) {
/* 676 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictTo({RestrictTo.Scope.SUBCLASSES})
/*     */   public MenuItem setNumericShortcut(char numericChar) {
/* 685 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictTo({RestrictTo.Scope.SUBCLASSES})
/*     */   public char getNumericShortcut() {
/* 694 */     return Character.MIN_VALUE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictTo({RestrictTo.Scope.SUBCLASSES})
/*     */   public MenuItem setAlphabeticShortcut(char alphaChar) {
/* 703 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictTo({RestrictTo.Scope.SUBCLASSES})
/*     */   public char getAlphabeticShortcut() {
/* 712 */     return Character.MIN_VALUE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictTo({RestrictTo.Scope.SUBCLASSES})
/*     */   public MenuItem setCheckable(boolean checkable) {
/* 721 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictTo({RestrictTo.Scope.SUBCLASSES})
/*     */   public boolean isCheckable() {
/* 730 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictTo({RestrictTo.Scope.SUBCLASSES})
/*     */   public MenuItem setChecked(boolean checked) {
/* 739 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictTo({RestrictTo.Scope.SUBCLASSES})
/*     */   public boolean isChecked() {
/* 748 */     return false;
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
/*     */   public MenuItem setVisible(boolean visible) {
/* 763 */     this.mIsVisible = visible;
/* 764 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isVisible() {
/* 774 */     return this.mIsVisible;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MenuItem setEnabled(boolean enabled) {
/* 784 */     this.mIsEnable = enabled;
/* 785 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEnabled() {
/* 790 */     return this.mIsEnable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QuickMenuItem initSubMenu(ToolManager toolManager, boolean annotationPermission) {
/* 801 */     this.mSubMenu = new QuickMenuBuilder(this.mContext, toolManager, annotationPermission);
/* 802 */     this.mSubMenu.setParentMenuItem(this);
/* 803 */     return this;
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
/*     */   public boolean hasSubMenu() {
/* 815 */     return (this.mSubMenu != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SubMenu getSubMenu() {
/* 826 */     return this.mSubMenu;
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
/*     */   public MenuItem setOnMenuItemClickListener(OnMenuItemClickListener menuItemClickListener) {
/* 838 */     this.mMenuItemClickListener = menuItemClickListener;
/* 839 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictTo({RestrictTo.Scope.SUBCLASSES})
/*     */   public ContextMenu.ContextMenuInfo getMenuInfo() {
/* 848 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictTo({RestrictTo.Scope.SUBCLASSES})
/*     */   public void setShowAsAction(int actionEnum) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictTo({RestrictTo.Scope.SUBCLASSES})
/*     */   public MenuItem setShowAsActionFlags(int actionEnum) {
/* 866 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictTo({RestrictTo.Scope.SUBCLASSES})
/*     */   public MenuItem setActionView(View view) {
/* 875 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictTo({RestrictTo.Scope.SUBCLASSES})
/*     */   public MenuItem setActionView(int resId) {
/* 884 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictTo({RestrictTo.Scope.SUBCLASSES})
/*     */   public View getActionView() {
/* 893 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   @RestrictTo({RestrictTo.Scope.SUBCLASSES})
/*     */   public MenuItem setActionProvider(ActionProvider actionProvider) {
/* 899 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictTo({RestrictTo.Scope.SUBCLASSES})
/*     */   public ActionProvider getActionProvider() {
/* 908 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictTo({RestrictTo.Scope.SUBCLASSES})
/*     */   public boolean expandActionView() {
/* 917 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictTo({RestrictTo.Scope.SUBCLASSES})
/*     */   public boolean collapseActionView() {
/* 926 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictTo({RestrictTo.Scope.SUBCLASSES})
/*     */   public boolean isActionViewExpanded() {
/* 935 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictTo({RestrictTo.Scope.SUBCLASSES})
/*     */   public MenuItem setOnActionExpandListener(OnActionExpandListener listener) {
/* 944 */     return null;
/*     */   }
/*     */   
/*     */   @Retention(RetentionPolicy.SOURCE)
/*     */   public static @interface MenuDisplayMode {}
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\QuickMenuItem.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */