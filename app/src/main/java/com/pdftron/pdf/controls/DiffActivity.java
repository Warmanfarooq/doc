/*     */ package com.pdftron.pdf.controls;
/*     */ 
/*     */ import android.annotation.TargetApi;
/*     */ import android.app.Activity;
/*     */ import android.content.Context;
/*     */ import android.content.Intent;
/*     */ import android.net.Uri;
/*     */ import android.os.Bundle;
/*     */ import android.view.Menu;
/*     */ import android.view.MenuInflater;
/*     */ import android.view.MenuItem;
/*     */ import android.view.View;
/*     */ import android.widget.LinearLayout;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.annotation.RawRes;
/*     */ import androidx.appcompat.app.AppCompatActivity;
/*     */ import androidx.appcompat.widget.Toolbar;
/*     */ import androidx.fragment.app.FragmentTransaction;
/*     */ import com.pdftron.pdf.config.ViewerBuilder;
/*     */ import com.pdftron.pdf.config.ViewerConfig;
/*     */ import com.pdftron.pdf.dialog.diffing.DiffOptionsDialogFragment;
/*     */ import com.pdftron.pdf.dialog.diffing.DiffUtils;
/*     */ import com.pdftron.pdf.model.FileInfo;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.utils.AppUtils;
/*     */ import com.pdftron.pdf.utils.PdfViewCtrlTabsManager;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import com.pdftron.pdf.widget.DiffOptionsView;
/*     */ import com.pdftron.pdf.widget.FragmentLayout;
/*     */ import io.reactivex.android.schedulers.AndroidSchedulers;
/*     */ import io.reactivex.disposables.CompositeDisposable;
/*     */ import io.reactivex.functions.Consumer;
/*     */ import io.reactivex.schedulers.Schedulers;
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @TargetApi(19)
/*     */ public class DiffActivity
/*     */   extends AppCompatActivity
/*     */   implements PdfViewCtrlTabHostFragment.TabHostListener
/*     */ {
/*  46 */   private static String DEFAULT_FILE_1 = "DiffActivity_Default_File_1";
/*  47 */   private static String DEFAULT_FILE_2 = "DiffActivity_Default_File_2";
/*     */   
/*     */   private DiffOptionsView mDiffOptions;
/*     */   
/*     */   private LinearLayout mDiffLayout;
/*     */   
/*     */   private FragmentLayout mFragmentLayout;
/*     */   private View mSelectedView;
/*     */   private PdfViewCtrlTabHostFragment mPdfViewCtrlTabHostFragment;
/*  56 */   protected ArrayList<Uri> mFileUris = new ArrayList<>();
/*     */   
/*     */   private boolean mShouldOpenDocuments;
/*  59 */   private int mDefaultFile1 = 0;
/*  60 */   private int mDefaultFile2 = 0;
/*     */   
/*  62 */   private final CompositeDisposable mDisposable = new CompositeDisposable();
/*     */   
/*     */   public static void open(Context packageContext) {
/*  65 */     Intent intent = new Intent(packageContext, DiffActivity.class);
/*  66 */     packageContext.startActivity(intent);
/*     */   }
/*     */   
/*     */   public static void open(Context packageContext, @RawRes int defaultFile1, @RawRes int defaultFile2) {
/*  70 */     Intent intent = new Intent(packageContext, DiffActivity.class);
/*  71 */     intent.putExtra(DEFAULT_FILE_1, defaultFile1);
/*  72 */     intent.putExtra(DEFAULT_FILE_2, defaultFile2);
/*  73 */     packageContext.startActivity(intent);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onCreate(@Nullable Bundle savedInstanceState) {
/*  78 */     super.onCreate(savedInstanceState);
/*     */     
/*     */     try {
/*  81 */       AppUtils.initializePDFNetApplication(getApplicationContext());
/*  82 */     } catch (Exception ex) {
/*  83 */       ex.printStackTrace();
/*     */       
/*     */       return;
/*     */     } 
/*  87 */     if (getIntent() != null && getIntent().getExtras() != null) {
/*  88 */       this.mDefaultFile1 = getIntent().getExtras().getInt(DEFAULT_FILE_1);
/*  89 */       this.mDefaultFile2 = getIntent().getExtras().getInt(DEFAULT_FILE_2);
/*     */     } 
/*     */     
/*  92 */     setContentView(R.layout.activity_diff_tool);
/*  93 */     this.mDiffLayout = (LinearLayout)findViewById(R.id.diff_layout);
/*  94 */     this.mFragmentLayout = (FragmentLayout)findViewById(R.id.container);
/*  95 */     Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
/*  96 */     toolbar.setTitle(R.string.diff_compare);
/*  97 */     toolbar.setNavigationOnClickListener(new View.OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/* 100 */             DiffActivity.this.finish();
/*     */           }
/*     */         });
/* 103 */     this.mDiffOptions = (DiffOptionsView)findViewById(R.id.diff_options_view);
/* 104 */     this.mDiffOptions.setAnnotationToggleVisibility(false);
/* 105 */     if (this.mDefaultFile1 != 0 && this.mDefaultFile2 != 0) {
/* 106 */       this.mDiffOptions.setFiles(DiffUtils.getUriInfo((Context)this, Uri.fromFile(Utils.copyResourceToLocal((Context)this, this.mDefaultFile1, "diff_1", ".pdf"))), 
/* 107 */           DiffUtils.getUriInfo((Context)this, Uri.fromFile(Utils.copyResourceToLocal((Context)this, this.mDefaultFile2, "diff_2", ".pdf"))));
/*     */     }
/* 109 */     this.mDiffOptions.setDiffOptionsViewListener(new DiffOptionsView.DiffOptionsViewListener()
/*     */         {
/*     */           public void onSelectFile(View which) {
/* 112 */             DiffActivity.this.mSelectedView = which;
/* 113 */             DiffUtils.selectFile((Activity)DiffActivity.this);
/*     */           }
/*     */ 
/*     */           
/*     */           public void onCompareFiles(ArrayList<Uri> files) {
/* 118 */             DiffActivity.this.compareFiles(files, DiffActivity.this
/* 119 */                 .mDiffOptions.getColor1(), DiffActivity.this
/* 120 */                 .mDiffOptions.getColor2(), DiffActivity.this
/* 121 */                 .mDiffOptions.getBlendMode());
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
/* 128 */     super.onActivityResult(requestCode, resultCode, data);
/*     */     
/* 130 */     FileInfo fileInfo = DiffUtils.handleActivityResult((Context)this, requestCode, resultCode, data);
/* 131 */     if (fileInfo != null) {
/* 132 */       this.mDiffOptions.handleFileSelected(fileInfo, this.mSelectedView);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onStop() {
/* 138 */     super.onStop();
/*     */     
/* 140 */     this.mDisposable.clear();
/*     */   }
/*     */   
/*     */   private void compareFiles(ArrayList<Uri> files, int color1, int color2, int blendMode) {
/* 144 */     this.mFileUris.clear();
/* 145 */     this.mFileUris.addAll(files);
/*     */     
/* 147 */     String fileName = "pdf-diff.pdf";
/* 148 */     File diffFile = new File(getFilesDir(), fileName);
/* 149 */     String diffName = Utils.getFileNameNotInUse(diffFile.getAbsolutePath());
/* 150 */     diffFile = new File(diffName);
/*     */     
/* 152 */     this.mDisposable.add(DiffUtils.compareFiles((Context)this, files, color1, color2, blendMode, diffFile)
/* 153 */         .subscribeOn(Schedulers.io())
/* 154 */         .observeOn(AndroidSchedulers.mainThread())
/* 155 */         .subscribe(new Consumer<Uri>()
/*     */           {
/*     */             public void accept(Uri uri) throws Exception
/*     */             {
/* 159 */               DiffActivity.this.mFileUris.add(uri);
/*     */               
/* 161 */               PdfViewCtrlTabsManager.getInstance().clearAllPdfViewCtrlTabInfo((Context)DiffActivity.this);
/*     */               
/* 163 */               DiffActivity.this.mDiffLayout.setVisibility(8);
/* 164 */               DiffActivity.this.mFragmentLayout.setVisibility(0);
/* 165 */               DiffActivity.this.onDocumentSelected(uri);
/*     */             }
/*     */           },  new Consumer<Throwable>()
/*     */           {
/*     */             public void accept(Throwable throwable) throws Exception {}
/*     */           }));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateCompare(int color1, int color2, int blendMode) {
/* 176 */     if (this.mFileUris.size() != 3) {
/*     */       return;
/*     */     }
/*     */     
/* 180 */     ArrayList<Uri> files = new ArrayList<>(this.mFileUris.subList(0, 2));
/*     */     
/* 182 */     if (this.mPdfViewCtrlTabHostFragment != null) {
/*     */       try {
/* 184 */         PdfViewCtrlTabFragment pdfViewCtrlTabFragment = this.mPdfViewCtrlTabHostFragment.getCurrentPdfViewCtrlFragment();
/* 185 */         DiffUtils.updateDiff(pdfViewCtrlTabFragment, files, color1, color2, blendMode);
/* 186 */       } catch (Exception ex) {
/* 187 */         ex.printStackTrace();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private void onDocumentsSelected() {
/* 193 */     if (this.mFileUris.isEmpty()) {
/*     */       return;
/*     */     }
/* 196 */     this.mShouldOpenDocuments = true;
/* 197 */     onDocumentSelected(this.mFileUris.get(0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void onDocumentSelected(Uri fileUri) {
/* 204 */     ViewerBuilder builder = (ViewerBuilder)((ViewerBuilder)((ViewerBuilder)ViewerBuilder.withUri(fileUri, "").usingConfig(getConfig())).usingNavIcon(R.drawable.ic_arrow_back_white_24dp)).usingCustomToolbar(new int[] { R.menu.diff_viewer_addon, R.menu.fragment_viewer });
/* 205 */     startTabHostFragment(builder);
/*     */   }
/*     */   
/*     */   private void startTabHostFragment(@NonNull ViewerBuilder builder) {
/* 209 */     if (isFinishing()) {
/*     */       return;
/*     */     }
/*     */     
/* 213 */     if (this.mPdfViewCtrlTabHostFragment != null) {
/* 214 */       this.mPdfViewCtrlTabHostFragment.onOpenAddNewTab(builder.createBundle((Context)this));
/*     */     } else {
/* 216 */       FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
/* 217 */       this.mPdfViewCtrlTabHostFragment = builder.build((Context)this);
/* 218 */       this.mPdfViewCtrlTabHostFragment.addHostListener(this);
/*     */       
/* 220 */       ft.replace(R.id.container, this.mPdfViewCtrlTabHostFragment, null);
/* 221 */       ft.commitAllowingStateLoss();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ViewerConfig getConfig() {
/* 229 */     return (new ViewerConfig.Builder()).useSupportActionBar(false).multiTabEnabled(false).build();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBackPressed() {
/* 234 */     if (this.mFragmentLayout.getVisibility() == 0) {
/* 235 */       onNavButtonPressed();
/*     */     } else {
/* 237 */       super.onBackPressed();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onTabHostShown() {
/* 243 */     if (this.mShouldOpenDocuments) {
/* 244 */       this.mShouldOpenDocuments = false;
/* 245 */       for (int i = 0; i < this.mFileUris.size(); i++) {
/* 246 */         if (i != 0) {
/* 247 */           Uri fileUri = this.mFileUris.get(i);
/* 248 */           onDocumentSelected(fileUri);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onTabHostHidden() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLastTabClosed() {
/* 261 */     onNavButtonPressed();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onTabChanged(String tag) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void onOpenDocError() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNavButtonPressed() {
/* 276 */     this.mDiffLayout.setVisibility(0);
/* 277 */     this.mFragmentLayout.setVisibility(8);
/* 278 */     if (this.mPdfViewCtrlTabHostFragment != null) {
/* 279 */       FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
/* 280 */       ft.remove(this.mPdfViewCtrlTabHostFragment);
/* 281 */       ft.commit();
/* 282 */       this.mPdfViewCtrlTabHostFragment = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onShowFileInFolder(String fileName, String filepath, int itemSource) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canShowFileInFolder() {
/* 293 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canShowFileCloseSnackbar() {
/* 298 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onToolbarCreateOptionsMenu(Menu menu, MenuInflater inflater) {
/* 303 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onToolbarPrepareOptionsMenu(Menu menu) {
/* 308 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onToolbarOptionsItemSelected(MenuItem item) {
/* 313 */     if (item.getItemId() == R.id.action_diff_options && 
/* 314 */       this.mFileUris.size() >= 2) {
/* 315 */       DiffOptionsDialogFragment fragment = DiffOptionsDialogFragment.newInstance(this.mFileUris
/* 316 */           .get(0), this.mFileUris.get(1));
/*     */       
/* 318 */       fragment.setStyle(0, R.style.CustomAppTheme);
/* 319 */       fragment.setDiffOptionsDialogListener(new DiffOptionsDialogFragment.DiffOptionsDialogListener()
/*     */           {
/*     */             public void onDiffOptionsConfirmed(int color1, int color2, int blendMode) {
/* 322 */               DiffActivity.this.updateCompare(color1, color2, blendMode);
/*     */             }
/*     */           });
/* 325 */       fragment.show(getSupportFragmentManager(), DiffOptionsDialogFragment.TAG);
/*     */     } 
/*     */     
/* 328 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onStartSearchMode() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void onExitSearchMode() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canRecreateActivity() {
/* 343 */     return false;
/*     */   }
/*     */   
/*     */   public void onTabPaused(FileInfo fileInfo, boolean isDocModifiedAfterOpening) {}
/*     */   
/*     */   public void onJumpToSdCardFolder() {}
/*     */   
/*     */   public void onTabDocumentLoaded(String tag) {}
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\controls\DiffActivity.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */