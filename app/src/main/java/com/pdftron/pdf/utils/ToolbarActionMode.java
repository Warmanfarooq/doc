/*     */ package com.pdftron.pdf.utils;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.graphics.drawable.Drawable;
/*     */ import android.view.Menu;
/*     */ import android.view.MenuItem;
/*     */ import android.view.View;
/*     */ import android.view.animation.Animation;
/*     */ import android.view.animation.AnimationUtils;
/*     */ import androidx.annotation.AnimRes;
/*     */ import androidx.annotation.DrawableRes;
/*     */ import androidx.annotation.MenuRes;
/*     */ import androidx.annotation.StringRes;
/*     */ import androidx.appcompat.widget.Toolbar;
/*     */ import com.pdftron.pdf.tools.R;
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
/*     */ public class ToolbarActionMode
/*     */ {
/*     */   private Context mContext;
/*     */   private Toolbar mToolbar;
/*     */   private Callback mCallback;
/*     */   private Animation mEnterAnimation;
/*     */   private Animation mExitAnimation;
/*     */   private Animation.AnimationListener mEnterAnimationListener;
/*     */   private Animation.AnimationListener mExitAnimationListener;
/*     */   private View mCustomView;
/*     */   
/*     */   public ToolbarActionMode(Context context, Toolbar toolbar) {
/*  43 */     if (context == null || toolbar == null) {
/*  44 */       throw new IllegalArgumentException("Context and Toolbar must be non-null");
/*     */     }
/*  46 */     this.mContext = context;
/*  47 */     this.mToolbar = toolbar;
/*     */     
/*  49 */     setEnterAnimation(R.anim.action_mode_enter);
/*  50 */     setExitAnimation(R.anim.action_mode_exit);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startActionMode(Callback callback) {
/*  58 */     this.mCallback = callback;
/*  59 */     if (this.mToolbar == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  64 */     this.mToolbar.setNavigationOnClickListener(new View.OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/*  67 */             ToolbarActionMode.this.finish();
/*     */           }
/*     */         });
/*  70 */     this.mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener()
/*     */         {
/*     */           public boolean onMenuItemClick(MenuItem item) {
/*  73 */             return ToolbarActionMode.this.mCallback.onActionItemClicked(ToolbarActionMode.this, item);
/*     */           }
/*     */         });
/*     */     
/*  77 */     this.mCallback.onCreateActionMode(this, this.mToolbar.getMenu());
/*  78 */     invalidate();
/*  79 */     show();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void invalidate() {
/*  86 */     Menu menu = (this.mToolbar != null) ? this.mToolbar.getMenu() : null;
/*  87 */     this.mCallback.onPrepareActionMode(this, menu);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void finish() {
/*  94 */     this.mCallback.onDestroyActionMode(this);
/*  95 */     hide();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTitle(@StringRes int resId) {
/* 103 */     if (this.mToolbar != null) {
/* 104 */       this.mToolbar.setTitle(resId);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTitle(CharSequence title) {
/* 113 */     if (this.mToolbar != null) {
/* 114 */       this.mToolbar.setTitle(title);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSubtitle(@StringRes int resId) {
/* 123 */     if (this.mToolbar != null) {
/* 124 */       this.mToolbar.setSubtitle(resId);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSubtitle(CharSequence subtitle) {
/* 133 */     if (this.mToolbar != null) {
/* 134 */       this.mToolbar.setSubtitle(subtitle);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void inflateMenu(@MenuRes int menuRes) {
/* 143 */     if (this.mToolbar != null) {
/* 144 */       this.mToolbar.getMenu().clear();
/* 145 */       this.mToolbar.inflateMenu(menuRes);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCloseDrawable(@DrawableRes int resId) {
/* 154 */     if (this.mToolbar != null) {
/* 155 */       this.mToolbar.setNavigationIcon(resId);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCloseDrawable(Drawable drawable) {
/* 164 */     if (this.mToolbar != null) {
/* 165 */       this.mToolbar.setNavigationIcon(drawable);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCustomView(View view) {
/* 174 */     if (this.mToolbar != null) {
/* 175 */       if (this.mCustomView != null) {
/* 176 */         this.mToolbar.removeView(this.mCustomView);
/*     */       }
/* 178 */       this.mToolbar.addView(view);
/* 179 */       this.mCustomView = view;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEnterAnimation(@AnimRes int animId) {
/* 188 */     this.mEnterAnimation = AnimationUtils.loadAnimation(this.mContext, animId);
/* 189 */     initEnterAnimationListener();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEnterAnimation(Animation animation) {
/* 197 */     this.mEnterAnimation = animation;
/* 198 */     initEnterAnimationListener();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setExitAnimation(@AnimRes int animId) {
/* 206 */     this.mExitAnimation = AnimationUtils.loadAnimation(this.mContext, animId);
/* 207 */     initExitAnimationListener();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setExitAnimation(Animation animation) {
/* 215 */     this.mExitAnimation = animation;
/* 216 */     initExitAnimationListener();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Menu getMenu() {
/* 224 */     return (this.mToolbar != null) ? this.mToolbar.getMenu() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CharSequence getTitle() {
/* 232 */     return (this.mToolbar != null) ? this.mToolbar.getTitle() : "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CharSequence getSubtitle() {
/* 240 */     return (this.mToolbar != null) ? this.mToolbar.getSubtitle() : "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public View getCustomView() {
/* 248 */     return this.mCustomView;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void show() {
/* 255 */     if (this.mToolbar != null) {
/* 256 */       if (this.mEnterAnimation != null) {
/* 257 */         this.mToolbar.startAnimation(this.mEnterAnimation);
/*     */       } else {
/* 259 */         this.mToolbar.setVisibility(0);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void hide() {
/* 268 */     if (this.mToolbar != null) {
/* 269 */       if (this.mExitAnimation != null) {
/* 270 */         this.mToolbar.startAnimation(this.mExitAnimation);
/*     */       } else {
/* 272 */         this.mToolbar.setVisibility(8);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void initEnterAnimationListener() {
/* 279 */     if (this.mEnterAnimationListener == null) {
/* 280 */       this.mEnterAnimationListener = new Animation.AnimationListener()
/*     */         {
/*     */           public void onAnimationStart(Animation animation) {
/* 283 */             if (ToolbarActionMode.this.mToolbar != null) {
/* 284 */               ToolbarActionMode.this.mToolbar.setVisibility(0);
/*     */             }
/*     */           }
/*     */ 
/*     */           
/*     */           public void onAnimationEnd(Animation animation) {}
/*     */ 
/*     */           
/*     */           public void onAnimationRepeat(Animation animation) {}
/*     */         };
/*     */     }
/* 295 */     this.mEnterAnimation.setAnimationListener(this.mEnterAnimationListener);
/*     */   }
/*     */ 
/*     */   
/*     */   private void initExitAnimationListener() {
/* 300 */     if (this.mExitAnimationListener == null) {
/* 301 */       this.mExitAnimationListener = new Animation.AnimationListener()
/*     */         {
/*     */           public void onAnimationStart(Animation animation) {}
/*     */ 
/*     */           
/*     */           public void onAnimationEnd(Animation animation) {
/* 307 */             if (ToolbarActionMode.this.mToolbar != null) {
/* 308 */               ToolbarActionMode.this.mToolbar.setVisibility(8);
/*     */             }
/*     */           }
/*     */ 
/*     */           
/*     */           public void onAnimationRepeat(Animation animation) {}
/*     */         };
/*     */     }
/* 316 */     this.mExitAnimation.setAnimationListener(this.mExitAnimationListener);
/*     */   }
/*     */   
/*     */   public static interface Callback {
/*     */     boolean onCreateActionMode(ToolbarActionMode param1ToolbarActionMode, Menu param1Menu);
/*     */     
/*     */     boolean onActionItemClicked(ToolbarActionMode param1ToolbarActionMode, MenuItem param1MenuItem);
/*     */     
/*     */     void onDestroyActionMode(ToolbarActionMode param1ToolbarActionMode);
/*     */     
/*     */     boolean onPrepareActionMode(ToolbarActionMode param1ToolbarActionMode, Menu param1Menu);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pd\\utils\ToolbarActionMode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */