/*     */ package com.pdftron.pdf.controls;
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
/*     */ import com.pdftron.pdf.adapter.StampFragmentAdapter;
/*     */ import com.pdftron.pdf.interfaces.OnDialogDismissListener;
/*     */ import com.pdftron.pdf.interfaces.OnRubberStampSelectedListener;
/*     */ import com.pdftron.pdf.model.CustomStampPreviewAppearance;
/*     */ import com.pdftron.pdf.model.StandardStampPreviewAppearance;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.tools.Tool;
/*     */ import com.pdftron.sdf.Obj;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RubberStampDialogFragment
/*     */   extends CustomSizeDialogFragment
/*     */ {
/*  36 */   public static final String TAG = RubberStampDialogFragment.class.getName();
/*     */   
/*     */   private static final String PREF_LAST_SELECTED_TAB_IN_RUBBER_STAMP_DIALOG = "last_selected_tab_in_rubber_stamp_dialog";
/*     */   
/*     */   private static final String BUNDLE_TARGET_POINT_X = "target_point_x";
/*     */   private static final String BUNDLE_TARGET_POINT_Y = "target_point_y";
/*     */   private PointF mTargetPoint;
/*     */   private OnRubberStampSelectedListener mOnRubberStampSelectedListener;
/*     */   private OnDialogDismissListener mOnDialogDismissListener;
/*     */   
/*     */   public static RubberStampDialogFragment newInstance(@NonNull PointF targetPoint, StandardStampPreviewAppearance[] standardStampPreviewAppearance, CustomStampPreviewAppearance[] customStampPreviewAppearances) {
/*  47 */     RubberStampDialogFragment fragment = new RubberStampDialogFragment();
/*  48 */     Bundle bundle = new Bundle();
/*  49 */     bundle.putFloat("target_point_x", targetPoint.x);
/*  50 */     bundle.putFloat("target_point_y", targetPoint.y);
/*  51 */     StandardStampPreviewAppearance.putStandardStampAppearancesToBundle(bundle, standardStampPreviewAppearance);
/*  52 */     CustomStampPreviewAppearance.putCustomStampAppearancesToBundle(bundle, customStampPreviewAppearances);
/*  53 */     fragment.setArguments(bundle);
/*  54 */     return fragment;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PointF getTargetPoint() {
/*  64 */     return this.mTargetPoint;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onCreate(@Nullable Bundle savedInstanceState) {
/*  69 */     super.onCreate(savedInstanceState);
/*     */     
/*  71 */     Bundle arg = getArguments();
/*  72 */     if (arg != null) {
/*  73 */       float x = arg.getFloat("target_point_x");
/*  74 */       float y = arg.getFloat("target_point_y");
/*  75 */       this.mTargetPoint = new PointF(x, y);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
/*  81 */     View view = inflater.inflate(R.layout.fragment_rubber_stamp_dialog, container);
/*     */     
/*  83 */     Toolbar toolbar = (Toolbar)view.findViewById(R.id.stamp_dialog_toolbar);
/*  84 */     Toolbar cabToolbar = (Toolbar)view.findViewById(R.id.stamp_dialog_toolbar_cab);
/*     */     
/*  86 */     toolbar.setNavigationOnClickListener(new View.OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/*  89 */             RubberStampDialogFragment.this.dismiss();
/*     */           }
/*     */         });
/*  92 */     toolbar.inflateMenu(R.menu.controls_fragment_edit_toolbar);
/*     */     
/*  94 */     Bundle bundle = getArguments();
/*  95 */     StandardStampPreviewAppearance[] standardStampPreviewAppearances = null;
/*  96 */     CustomStampPreviewAppearance[] customStampPreviewAppearances = null;
/*  97 */     if (bundle != null) {
/*  98 */       standardStampPreviewAppearances = StandardStampPreviewAppearance.getStandardStampAppearancesFromBundle(bundle);
/*  99 */       customStampPreviewAppearances = CustomStampPreviewAppearance.getCustomStampAppearancesFromBundle(bundle);
/*     */     } 
/* 101 */     ViewPager pager = (ViewPager)view.findViewById(R.id.stamp_dialog_view_pager);
/*     */     
/* 103 */     StampFragmentAdapter adapter = new StampFragmentAdapter(getChildFragmentManager(), getString(R.string.standard), getString(R.string.custom), standardStampPreviewAppearances, customStampPreviewAppearances, toolbar, cabToolbar);
/* 104 */     adapter.setOnRubberStampSelectedListener(new OnRubberStampSelectedListener()
/*     */         {
/*     */           public void onRubberStampSelected(@NonNull String stampLabel) {
/* 107 */             if (RubberStampDialogFragment.this.mOnRubberStampSelectedListener != null) {
/* 108 */               RubberStampDialogFragment.this.mOnRubberStampSelectedListener.onRubberStampSelected(stampLabel);
/*     */             }
/* 110 */             RubberStampDialogFragment.this.dismiss();
/*     */           }
/*     */ 
/*     */           
/*     */           public void onRubberStampSelected(@Nullable Obj stampObj) {
/* 115 */             if (RubberStampDialogFragment.this.mOnRubberStampSelectedListener != null) {
/* 116 */               RubberStampDialogFragment.this.mOnRubberStampSelectedListener.onRubberStampSelected(stampObj);
/*     */             }
/* 118 */             RubberStampDialogFragment.this.dismiss();
/*     */           }
/*     */         });
/* 121 */     pager.setAdapter((PagerAdapter)adapter);
/*     */     
/* 123 */     TabLayout tabLayout = (TabLayout)view.findViewById(R.id.stamp_dialog_tab_layout);
/* 124 */     tabLayout.setupWithViewPager(pager);
/*     */     
/* 126 */     SharedPreferences settings = Tool.getToolPreferences(view.getContext());
/* 127 */     int lastSelectedTab = settings.getInt("last_selected_tab_in_rubber_stamp_dialog", 0);
/* 128 */     pager.setCurrentItem(lastSelectedTab);
/*     */     
/* 130 */     tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener()
/*     */         {
/*     */           public void onTabSelected(TabLayout.Tab tab) {
/* 133 */             Context context = RubberStampDialogFragment.this.getContext();
/* 134 */             if (context == null) {
/*     */               return;
/*     */             }
/* 137 */             SharedPreferences settings = Tool.getToolPreferences(context);
/* 138 */             SharedPreferences.Editor editor = settings.edit();
/* 139 */             editor.putInt("last_selected_tab_in_rubber_stamp_dialog", tab.getPosition());
/* 140 */             editor.apply();
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
/* 154 */     return view;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDismiss(DialogInterface dialog) {
/* 159 */     super.onDismiss(dialog);
/* 160 */     if (this.mOnDialogDismissListener != null) {
/* 161 */       this.mOnDialogDismissListener.onDialogDismiss();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOnRubberStampSelectedListener(OnRubberStampSelectedListener listener) {
/* 171 */     this.mOnRubberStampSelectedListener = listener;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOnDialogDismissListener(OnDialogDismissListener listener) {
/* 180 */     this.mOnDialogDismissListener = listener;
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\controls\RubberStampDialogFragment.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */