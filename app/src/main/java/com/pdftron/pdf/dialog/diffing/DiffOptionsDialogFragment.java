/*     */ package com.pdftron.pdf.dialog.diffing;
/*     */ 
/*     */ import android.net.Uri;
/*     */ import android.os.Bundle;
/*     */ import android.os.Parcelable;
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.appcompat.widget.Toolbar;
/*     */ import com.pdftron.pdf.controls.CustomSizeDialogFragment;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import com.pdftron.pdf.widget.DiffOptionsView;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class DiffOptionsDialogFragment
/*     */   extends CustomSizeDialogFragment
/*     */ {
/*  21 */   public static final String TAG = DiffOptionsDialogFragment.class.getName();
/*     */ 
/*     */   
/*     */   private static final String BUNDLE_FILE_1 = "bundle_file_1";
/*     */   
/*     */   private static final String BUNDLE_FILE_2 = "bundle_file_2";
/*     */   
/*     */   private Uri mFile1;
/*     */   
/*     */   private Uri mFile2;
/*     */   
/*     */   private DiffOptionsView mDiffOptions;
/*     */   
/*     */   private DiffOptionsDialogListener mDiffOptionsDialogListener;
/*     */ 
/*     */   
/*     */   public static DiffOptionsDialogFragment newInstance(Uri file1, Uri file2) {
/*  38 */     DiffOptionsDialogFragment fragment = new DiffOptionsDialogFragment();
/*  39 */     Bundle bundle = new Bundle();
/*  40 */     bundle.putParcelable("bundle_file_1", (Parcelable)file1);
/*  41 */     bundle.putParcelable("bundle_file_2", (Parcelable)file2);
/*  42 */     fragment.setArguments(bundle);
/*  43 */     return fragment;
/*     */   }
/*     */   
/*     */   public void setDiffOptionsDialogListener(DiffOptionsDialogListener listener) {
/*  47 */     this.mDiffOptionsDialogListener = listener;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onCreate(@Nullable Bundle savedInstanceState) {
/*  52 */     super.onCreate(savedInstanceState);
/*     */     
/*  54 */     if (Utils.isTablet(getContext())) {
/*  55 */       this.mHeight = 500;
/*     */     }
/*     */     
/*  58 */     Bundle args = getArguments();
/*  59 */     if (args != null) {
/*  60 */       this.mFile1 = (Uri)args.getParcelable("bundle_file_1");
/*  61 */       this.mFile2 = (Uri)args.getParcelable("bundle_file_2");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
/*  68 */     return inflater.inflate(R.layout.activity_diff_tool, container, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
/*  73 */     super.onViewCreated(view, savedInstanceState);
/*     */     
/*  75 */     Toolbar toolbar = (Toolbar)view.findViewById(R.id.toolbar);
/*  76 */     toolbar.setTitle(R.string.diff_options_title);
/*  77 */     toolbar.setNavigationOnClickListener(new View.OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/*  80 */             DiffOptionsDialogFragment.this.dismiss();
/*     */           }
/*     */         });
/*     */     
/*  84 */     this.mDiffOptions = (DiffOptionsView)view.findViewById(R.id.diff_options_view);
/*  85 */     this.mDiffOptions.setSelectFileButtonVisibility(false);
/*  86 */     this.mDiffOptions.setAnnotationToggleVisibility(false);
/*  87 */     this.mDiffOptions.setFiles(DiffUtils.getUriInfo(view.getContext(), this.mFile1), DiffUtils.getUriInfo(view.getContext(), this.mFile2));
/*  88 */     this.mDiffOptions.setDiffOptionsViewListener(new DiffOptionsView.DiffOptionsViewListener()
/*     */         {
/*     */           public void onSelectFile(View which) {}
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onCompareFiles(ArrayList<Uri> files) {
/*  96 */             if (DiffOptionsDialogFragment.this.mDiffOptionsDialogListener != null) {
/*  97 */               DiffOptionsDialogFragment.this.mDiffOptionsDialogListener.onDiffOptionsConfirmed(DiffOptionsDialogFragment.this
/*  98 */                   .mDiffOptions.getColor1(), DiffOptionsDialogFragment.this
/*  99 */                   .mDiffOptions.getColor2(), DiffOptionsDialogFragment.this
/* 100 */                   .mDiffOptions.getBlendMode());
/*     */             }
/*     */             
/* 103 */             DiffOptionsDialogFragment.this.dismiss();
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public static interface DiffOptionsDialogListener {
/*     */     void onDiffOptionsConfirmed(int param1Int1, int param1Int2, int param1Int3);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\dialog\diffing\DiffOptionsDialogFragment.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */