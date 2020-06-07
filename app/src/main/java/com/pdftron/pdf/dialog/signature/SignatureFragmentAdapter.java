/*     */ package com.pdftron.pdf.dialog.signature;
/*     */ 
/*     */ import android.view.MenuItem;
/*     */ import android.view.ViewGroup;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.appcompat.widget.Toolbar;
/*     */ import androidx.fragment.app.Fragment;
/*     */ import androidx.fragment.app.FragmentManager;
/*     */ import androidx.fragment.app.FragmentPagerAdapter;
/*     */ import com.pdftron.pdf.interfaces.OnCreateSignatureListener;
/*     */ import com.pdftron.pdf.interfaces.OnSavedSignatureListener;
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
/*     */ public class SignatureFragmentAdapter
/*     */   extends FragmentPagerAdapter
/*     */ {
/*     */   private final String mStandardTitle;
/*     */   private final String mCustomTitle;
/*     */   private Toolbar mToolbar;
/*     */   private Toolbar mCabToolbar;
/*     */   private int mColor;
/*     */   private float mStrokeWidth;
/*     */   private Fragment mCurrentFragment;
/*     */   private boolean mShowSavedSignatures;
/*     */   private boolean mShowSignatureFromImage;
/*     */   private int mConfirmBtnStrRes;
/*     */   private boolean mIsPressureSensitive;
/*     */   private OnCreateSignatureListener mOnCreateSignatureListener;
/*     */   private OnSavedSignatureListener mOnSavedSignatureListener;
/*     */   
/*     */   public SignatureFragmentAdapter(FragmentManager fm, String standardTitle, String customTitle, @NonNull Toolbar toolbar, @NonNull Toolbar cabToolbar, int color, float thickness, boolean showSavedSignatures, boolean showSignatureFromImage, int confirmBtnStrRes, OnCreateSignatureListener onCreateSignatureListener, OnSavedSignatureListener onSavedSignatureListener, boolean isPressureSensitive) {
/*  46 */     super(fm);
/*  47 */     this.mStandardTitle = standardTitle;
/*  48 */     this.mCustomTitle = customTitle;
/*  49 */     this.mToolbar = toolbar;
/*  50 */     this.mCabToolbar = cabToolbar;
/*  51 */     this.mColor = color;
/*  52 */     this.mStrokeWidth = thickness;
/*  53 */     this.mShowSavedSignatures = showSavedSignatures;
/*  54 */     this.mShowSignatureFromImage = showSignatureFromImage;
/*  55 */     this.mOnCreateSignatureListener = onCreateSignatureListener;
/*  56 */     this.mOnSavedSignatureListener = onSavedSignatureListener;
/*  57 */     this.mConfirmBtnStrRes = confirmBtnStrRes;
/*  58 */     this.mIsPressureSensitive = isPressureSensitive;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
/*  63 */     super.setPrimaryItem(container, position, object);
/*     */     
/*  65 */     Fragment fragment = (Fragment)object;
/*  66 */     if (this.mCurrentFragment != fragment) {
/*  67 */       this.mCurrentFragment = fragment;
/*  68 */       if (this.mCurrentFragment instanceof SavedSignaturePickerFragment) {
/*  69 */         ((SavedSignaturePickerFragment)this.mCurrentFragment).setOnSavedSignatureListener(this.mOnSavedSignatureListener);
/*  70 */         ((SavedSignaturePickerFragment)this.mCurrentFragment).resetToolbar(container.getContext());
/*  71 */         MenuItem menuEdit = this.mToolbar.getMenu().findItem(R.id.controls_action_edit);
/*  72 */         menuEdit.setTitle(R.string.tools_qm_edit);
/*  73 */       } else if (this.mCurrentFragment instanceof CreateSignatureFragment) {
/*  74 */         ((CreateSignatureFragment)this.mCurrentFragment).setOnCreateSignatureListener(this.mOnCreateSignatureListener);
/*  75 */         ((CreateSignatureFragment)this.mCurrentFragment).resetToolbar(container.getContext());
/*  76 */         MenuItem menuEdit = this.mToolbar.getMenu().findItem(R.id.controls_action_edit);
/*  77 */         menuEdit.setTitle(this.mConfirmBtnStrRes);
/*     */       } 
/*     */       
/*  80 */       this.mToolbar.setVisibility(0);
/*  81 */       this.mCabToolbar.setVisibility(8);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Fragment getItem(int position) {
/*  87 */     if (this.mShowSavedSignatures) {
/*  88 */       SavedSignaturePickerFragment pickerFragment; CreateSignatureFragment createSignatureFragment; switch (position) {
/*     */         case 0:
/*  90 */           pickerFragment = SavedSignaturePickerFragment.newInstance();
/*  91 */           pickerFragment.setToolbars(this.mToolbar, this.mCabToolbar);
/*  92 */           pickerFragment.setOnSavedSignatureListener(this.mOnSavedSignatureListener);
/*  93 */           return pickerFragment;
/*     */         case 1:
/*  95 */           createSignatureFragment = CreateSignatureFragment.newInstance(this.mColor, this.mStrokeWidth, this.mShowSignatureFromImage, this.mIsPressureSensitive);
/*     */           
/*  97 */           createSignatureFragment.setOnCreateSignatureListener(this.mOnCreateSignatureListener);
/*  98 */           createSignatureFragment.setToolbar(this.mToolbar);
/*  99 */           return createSignatureFragment;
/*     */       } 
/* 101 */       return null;
/*     */     } 
/*     */     
/* 104 */     CreateSignatureFragment signatureFragment = CreateSignatureFragment.newInstance(this.mColor, this.mStrokeWidth, this.mShowSignatureFromImage, this.mIsPressureSensitive);
/*     */     
/* 106 */     signatureFragment.setOnCreateSignatureListener(this.mOnCreateSignatureListener);
/* 107 */     signatureFragment.setToolbar(this.mToolbar);
/* 108 */     return signatureFragment;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCount() {
/* 114 */     return this.mShowSavedSignatures ? 2 : 1;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public CharSequence getPageTitle(int position) {
/* 120 */     if (this.mShowSavedSignatures) {
/* 121 */       switch (position) {
/*     */         case 0:
/* 123 */           return this.mStandardTitle;
/*     */         case 1:
/* 125 */           return this.mCustomTitle;
/*     */       } 
/* 127 */       return null;
/*     */     } 
/*     */     
/* 130 */     return this.mCustomTitle;
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\dialog\signature\SignatureFragmentAdapter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */