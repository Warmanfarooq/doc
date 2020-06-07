/*     */ package com.pdftron.pdf.dialog.signature;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.content.DialogInterface;
/*     */ import android.content.SharedPreferences;
/*     */ import android.graphics.PointF;
/*     */ import android.os.Bundle;
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.appcompat.widget.Toolbar;
/*     */ import androidx.viewpager.widget.PagerAdapter;
/*     */ import androidx.viewpager.widget.ViewPager;
/*     */ import com.google.android.material.tabs.TabLayout;
/*     */ import com.pdftron.pdf.controls.AnnotStyleDialogFragment;
/*     */ import com.pdftron.pdf.controls.CustomSizeDialogFragment;
/*     */ import com.pdftron.pdf.interfaces.OnCreateSignatureListener;
/*     */ import com.pdftron.pdf.interfaces.OnDialogDismissListener;
/*     */ import com.pdftron.pdf.interfaces.OnSavedSignatureListener;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.tools.Tool;
/*     */ import com.pdftron.pdf.widget.CustomViewPager;
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
/*     */ public class SignatureDialogFragment
/*     */   extends CustomSizeDialogFragment
/*     */   implements OnCreateSignatureListener, OnSavedSignatureListener
/*     */ {
/*  39 */   public static final String TAG = SignatureDialogFragment.class.getName();
/*     */   
/*     */   protected static final String PREF_LAST_SELECTED_TAB_IN_SIGNATURE_DIALOG = "last_selected_tab_in_signature_dialog";
/*     */   
/*     */   protected OnCreateSignatureListener mOnCreateSignatureListener;
/*     */   
/*     */   protected PointF mTargetPointPage;
/*     */   
/*     */   protected int mTargetPage;
/*     */   
/*     */   protected Long mTargetWidget;
/*     */   
/*     */   protected int mColor;
/*     */   
/*     */   protected float mStrokeWidth;
/*     */   
/*     */   protected CustomViewPager mViewPager;
/*     */   protected boolean mShowSavedSignatures;
/*     */   protected boolean mShowSignatureFromImage;
/*     */   protected boolean mPressureSensitive = true;
/*     */   protected int mConfirmBtnStrRes;
/*     */   protected OnDialogDismissListener mOnDialogDismissListener;
/*     */   
/*     */   public void onCreate(@Nullable Bundle savedInstanceState) {
/*  63 */     super.onCreate(savedInstanceState);
/*     */     
/*  65 */     Bundle args = getArguments();
/*  66 */     if (args != null) {
/*  67 */       float x = args.getFloat("target_point_x", -1.0F);
/*  68 */       float y = args.getFloat("target_point_y", -1.0F);
/*  69 */       if (x > 0.0F && y > 0.0F) {
/*  70 */         this.mTargetPointPage = new PointF(x, y);
/*     */       }
/*  72 */       this.mTargetPage = args.getInt("target_page", -1);
/*  73 */       this.mTargetWidget = Long.valueOf(args.getLong("target_widget"));
/*  74 */       this.mColor = args.getInt("bundle_color");
/*  75 */       this.mStrokeWidth = args.getFloat("bundle_stroke_width");
/*  76 */       this.mShowSavedSignatures = args.getBoolean("bundle_show_saved_signatures", true);
/*  77 */       this.mShowSignatureFromImage = args.getBoolean("bundle_signature_from_image", true);
/*  78 */       this.mConfirmBtnStrRes = args.getInt("bundle_confirm_button_string_res", SignatureDialogFragmentBuilder.CONFIRM_BUTTON_RES);
/*  79 */       this.mPressureSensitive = args.getBoolean("bundle_pressure_sensitive", true);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
/*  86 */     View view = inflater.inflate(R.layout.fragment_rubber_stamp_dialog, container);
/*     */     
/*  88 */     Toolbar toolbar = (Toolbar)view.findViewById(R.id.stamp_dialog_toolbar);
/*  89 */     toolbar.setTitle(R.string.annot_signature_plural);
/*  90 */     toolbar.inflateMenu(R.menu.controls_fragment_edit_toolbar);
/*  91 */     Toolbar cabToolbar = (Toolbar)view.findViewById(R.id.stamp_dialog_toolbar_cab);
/*     */     
/*  93 */     toolbar.setNavigationOnClickListener(new View.OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/*  96 */             SignatureDialogFragment.this.dismiss();
/*     */           }
/*     */         });
/*  99 */     this.mViewPager = (CustomViewPager)view.findViewById(R.id.stamp_dialog_view_pager);
/*     */     
/* 101 */     SignatureFragmentAdapter viewPagerAdapter = new SignatureFragmentAdapter(getChildFragmentManager(), getString(R.string.saved), getString(R.string.create), toolbar, cabToolbar, this.mColor, this.mStrokeWidth, this.mShowSavedSignatures, this.mShowSignatureFromImage, this.mConfirmBtnStrRes, this, this, this.mPressureSensitive);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 110 */     this.mViewPager.setAdapter((PagerAdapter)viewPagerAdapter);
/*     */     
/* 112 */     TabLayout tabLayout = (TabLayout)view.findViewById(R.id.stamp_dialog_tab_layout);
/* 113 */     tabLayout.setupWithViewPager((ViewPager)this.mViewPager);
/*     */     
/* 115 */     if (this.mShowSavedSignatures) {
/* 116 */       SharedPreferences settings = Tool.getToolPreferences(view.getContext());
/* 117 */       int lastSelectedTab = settings.getInt("last_selected_tab_in_signature_dialog", 0);
/* 118 */       this.mViewPager.setCurrentItem(lastSelectedTab);
/* 119 */       updateViewPager(lastSelectedTab);
/*     */     } else {
/* 121 */       tabLayout.setVisibility(8);
/* 122 */       this.mViewPager.setSwippingEnabled(false);
/*     */     } 
/*     */     
/* 125 */     tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener()
/*     */         {
/*     */           public void onTabSelected(TabLayout.Tab tab) {
/* 128 */             Context context = SignatureDialogFragment.this.getContext();
/* 129 */             if (context == null) {
/*     */               return;
/*     */             }
/* 132 */             SharedPreferences settings = Tool.getToolPreferences(context);
/* 133 */             SharedPreferences.Editor editor = settings.edit();
/* 134 */             editor.putInt("last_selected_tab_in_signature_dialog", tab.getPosition());
/* 135 */             editor.apply();
/*     */             
/* 137 */             SignatureDialogFragment.this.updateViewPager(tab.getPosition());
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onTabUnselected(TabLayout.Tab tab) {}
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onTabReselected(TabLayout.Tab tab) {}
/*     */         });
/* 151 */     return view;
/*     */   }
/*     */   
/*     */   private void updateViewPager(int selectedTab) {
/* 155 */     if (selectedTab == 1) {
/*     */ 
/*     */       
/* 158 */       this.mViewPager.setSwippingEnabled(false);
/*     */     } else {
/*     */       
/* 161 */       this.mViewPager.setSwippingEnabled(true);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDismiss(DialogInterface dialog) {
/* 167 */     super.onDismiss(dialog);
/* 168 */     if (this.mOnDialogDismissListener != null) {
/* 169 */       this.mOnDialogDismissListener.onDialogDismiss();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOnDialogDismissListener(OnDialogDismissListener listener) {
/* 179 */     this.mOnDialogDismissListener = listener;
/*     */   }
/*     */   
/*     */   public void setOnCreateSignatureListener(OnCreateSignatureListener listener) {
/* 183 */     this.mOnCreateSignatureListener = listener;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onSignatureCreated(@Nullable String filepath) {
/* 188 */     if (filepath != null) {
/* 189 */       onSignatureSelected(filepath);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onSignatureFromImage(@Nullable PointF targetPoint, int targetPage, @Nullable Long widget) {
/* 195 */     if (this.mOnCreateSignatureListener != null) {
/* 196 */       this.mOnCreateSignatureListener.onSignatureFromImage(this.mTargetPointPage, this.mTargetPage, this.mTargetWidget);
/*     */     }
/* 198 */     dismiss();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onAnnotStyleDialogFragmentDismissed(AnnotStyleDialogFragment styleDialog) {
/* 203 */     if (this.mOnCreateSignatureListener != null) {
/* 204 */       this.mOnCreateSignatureListener.onAnnotStyleDialogFragmentDismissed(styleDialog);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onSignatureSelected(@NonNull String filepath) {
/* 210 */     if (this.mOnCreateSignatureListener != null) {
/* 211 */       this.mOnCreateSignatureListener.onSignatureCreated(filepath);
/*     */     }
/* 213 */     dismiss();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onCreateSignatureClicked() {
/* 218 */     this.mViewPager.setCurrentItem(1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEditModeChanged(boolean isEdit) {
/* 223 */     this.mViewPager.setSwippingEnabled(!isEdit);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\dialog\signature\SignatureDialogFragment.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */