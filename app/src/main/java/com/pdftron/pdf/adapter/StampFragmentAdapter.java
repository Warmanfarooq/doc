/*     */ package com.pdftron.pdf.adapter;
/*     */ 
/*     */ import android.view.MenuItem;
/*     */ import android.view.ViewGroup;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.appcompat.widget.Toolbar;
/*     */ import androidx.fragment.app.Fragment;
/*     */ import androidx.fragment.app.FragmentManager;
/*     */ import androidx.fragment.app.FragmentPagerAdapter;
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.dialog.CustomStampPickerFragment;
/*     */ import com.pdftron.pdf.dialog.StandardRubberStampPickerFragment;
/*     */ import com.pdftron.pdf.interfaces.OnCustomStampSelectedListener;
/*     */ import com.pdftron.pdf.interfaces.OnRubberStampSelectedListener;
/*     */ import com.pdftron.pdf.model.CustomStampOption;
/*     */ import com.pdftron.pdf.model.CustomStampPreviewAppearance;
/*     */ import com.pdftron.pdf.model.StandardStampPreviewAppearance;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.AnalyticsParam;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import com.pdftron.sdf.Obj;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StampFragmentAdapter
/*     */   extends FragmentPagerAdapter
/*     */   implements OnRubberStampSelectedListener, OnCustomStampSelectedListener
/*     */ {
/*  36 */   private static final String TAG = StampFragmentAdapter.class.getName();
/*     */   
/*     */   private final String mStandardTitle;
/*     */   
/*     */   private final String mCustomTitle;
/*     */   private StandardStampPreviewAppearance[] mStandardStampPreviewAppearance;
/*     */   private CustomStampPreviewAppearance[] mCustomStampPreviewAppearances;
/*     */   private Toolbar mToolbar;
/*     */   private Toolbar mCabToolbar;
/*     */   private Fragment mCurrentFragment;
/*     */   private OnRubberStampSelectedListener mOnRubberStampSelectedListener;
/*     */   
/*     */   public StampFragmentAdapter(FragmentManager fm, String standardTitle, String customTitle, StandardStampPreviewAppearance[] standardStampPreviewAppearance, CustomStampPreviewAppearance[] customStampPreviewAppearances, @NonNull Toolbar toolbar, @NonNull Toolbar cabToolbar) {
/*  49 */     super(fm);
/*  50 */     this.mStandardTitle = standardTitle;
/*  51 */     this.mCustomTitle = customTitle;
/*  52 */     this.mCustomStampPreviewAppearances = customStampPreviewAppearances;
/*  53 */     this.mStandardStampPreviewAppearance = standardStampPreviewAppearance;
/*  54 */     this.mToolbar = toolbar;
/*  55 */     this.mCabToolbar = cabToolbar;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPrimaryItem(ViewGroup container, int position, Object object) {
/*  60 */     super.setPrimaryItem(container, position, object);
/*     */     
/*  62 */     Fragment fragment = (Fragment)object;
/*  63 */     if (this.mCurrentFragment != fragment) {
/*  64 */       this.mCurrentFragment = fragment;
/*  65 */       if (this.mCurrentFragment instanceof StandardRubberStampPickerFragment) {
/*  66 */         ((StandardRubberStampPickerFragment)this.mCurrentFragment).setOnRubberStampSelectedListener(this);
/*  67 */         MenuItem menuEdit = this.mToolbar.getMenu().findItem(R.id.controls_action_edit);
/*  68 */         menuEdit.setVisible(false);
/*     */       } 
/*  70 */       if (this.mCurrentFragment instanceof CustomStampPickerFragment) {
/*  71 */         CustomStampPickerFragment customStampPickerFragment = (CustomStampPickerFragment)this.mCurrentFragment;
/*  72 */         customStampPickerFragment.setOnCustomStampSelectedListener(this);
/*  73 */         customStampPickerFragment.setToolbars(this.mToolbar, this.mCabToolbar);
/*     */       } 
/*     */       
/*  76 */       this.mToolbar.setVisibility(0);
/*  77 */       this.mCabToolbar.setVisibility(8);
/*     */     } 
/*     */   }
/*     */   public Fragment getItem(int position) {
/*     */     StandardRubberStampPickerFragment standardStampPickerFragment;
/*     */     CustomStampPickerFragment customStampPickerFragment;
/*  83 */     switch (position) {
/*     */       
/*     */       case 0:
/*  86 */         standardStampPickerFragment = StandardRubberStampPickerFragment.newInstance(this.mStandardStampPreviewAppearance);
/*  87 */         standardStampPickerFragment.setOnRubberStampSelectedListener(this);
/*  88 */         return (Fragment)standardStampPickerFragment;
/*     */       
/*     */       case 1:
/*  91 */         customStampPickerFragment = CustomStampPickerFragment.newInstance(this.mCustomStampPreviewAppearances);
/*  92 */         customStampPickerFragment.setOnCustomStampSelectedListener(this);
/*  93 */         customStampPickerFragment.setToolbars(this.mToolbar, this.mCabToolbar);
/*  94 */         return (Fragment)customStampPickerFragment;
/*     */     } 
/*  96 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public CharSequence getPageTitle(int position) {
/* 103 */     switch (position) {
/*     */       case 0:
/* 105 */         return this.mStandardTitle;
/*     */       case 1:
/* 107 */         return this.mCustomTitle;
/*     */     } 
/* 109 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCount() {
/* 115 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRubberStampSelected(@NonNull String stampLabel) {
/* 120 */     if (this.mOnRubberStampSelectedListener != null) {
/* 121 */       this.mOnRubberStampSelectedListener.onRubberStampSelected(stampLabel);
/*     */     }
/* 123 */     sendStandardStampAnalytics(stampLabel);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRubberStampSelected(@Nullable Obj stampObj) {
/* 128 */     if (this.mOnRubberStampSelectedListener != null) {
/* 129 */       this.mOnRubberStampSelectedListener.onRubberStampSelected(stampObj);
/*     */     }
/* 131 */     sendStandardStampAnalytics(getStampName(stampObj));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onCustomStampSelected(@Nullable Obj stampObj) {
/* 136 */     if (this.mOnRubberStampSelectedListener != null) {
/* 137 */       this.mOnRubberStampSelectedListener.onRubberStampSelected(stampObj);
/*     */     }
/* 139 */     sendCustomStampAnalytics(stampObj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOnRubberStampSelectedListener(OnRubberStampSelectedListener listener) {
/* 148 */     this.mOnRubberStampSelectedListener = listener;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private String getStampName(@Nullable Obj stampObj) {
/* 153 */     if (stampObj == null) {
/* 154 */       return null;
/*     */     }
/*     */     try {
/* 157 */       Obj found = stampObj.findObj("TEXT");
/* 158 */       if (found != null && found.isString()) {
/* 159 */         return found.getAsPDFText();
/*     */       }
/* 161 */     } catch (PDFNetException e) {
/* 162 */       AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*     */     } 
/* 164 */     return null;
/*     */   }
/*     */   
/*     */   private void sendStandardStampAnalytics(@Nullable String stampName) {
/* 168 */     if (Utils.isNullOrEmpty(stampName)) {
/*     */       return;
/*     */     }
/* 171 */     AnalyticsHandlerAdapter.getInstance().sendEvent(62, 
/* 172 */         AnalyticsParam.addRubberStampParam(1, stampName));
/*     */   }
/*     */   
/*     */   private void sendCustomStampAnalytics(@Nullable Obj stampObj) {
/* 176 */     if (stampObj == null) {
/*     */       return;
/*     */     }
/*     */     try {
/* 180 */       CustomStampOption stamp = new CustomStampOption(stampObj);
/* 181 */       String color = null;
/* 182 */       for (CustomStampPreviewAppearance mCustomStampPreviewAppearance : this.mCustomStampPreviewAppearances) {
/* 183 */         if (mCustomStampPreviewAppearance.bgColorStart == stamp.bgColorStart) {
/* 184 */           color = mCustomStampPreviewAppearance.colorName;
/*     */           break;
/*     */         } 
/*     */       } 
/* 188 */       AnalyticsHandlerAdapter.getInstance().sendEvent(62, 
/* 189 */           AnalyticsParam.addCustomStampParam(2, stamp, color));
/* 190 */     } catch (PDFNetException e) {
/* 191 */       AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\adapter\StampFragmentAdapter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */