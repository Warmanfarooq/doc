/*     */ package com.pdftron.pdf.controls;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.net.Uri;
/*     */ import android.util.AttributeSet;
/*     */ import android.view.Menu;
/*     */ import android.view.MenuInflater;
/*     */ import android.view.MenuItem;
/*     */ import android.view.View;
/*     */ import android.widget.FrameLayout;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.fragment.app.FragmentManager;
/*     */ import com.pdftron.pdf.config.ViewerBuilder;
/*     */ import com.pdftron.pdf.config.ViewerConfig;
/*     */ import com.pdftron.pdf.model.FileInfo;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import org.json.JSONObject;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DocumentView
/*     */   extends FrameLayout
/*     */   implements PdfViewCtrlTabHostFragment.TabHostListener
/*     */ {
/*  27 */   private static final String TAG = DocumentView.class.getSimpleName();
/*     */   
/*     */   public PdfViewCtrlTabHostFragment mPdfViewCtrlTabHostFragment;
/*     */   
/*     */   public FragmentManager mFragmentManager;
/*  32 */   public int mNavIconRes = R.drawable.ic_arrow_back_white_24dp;
/*     */   public boolean mShowNavIcon = true;
/*     */   public Uri mDocumentUri;
/*  35 */   public String mPassword = "";
/*     */   public ViewerConfig mViewerConfig;
/*     */   public ViewerBuilder mViewerBuilder;
/*     */   public JSONObject mCustomHeaders;
/*     */   public PdfViewCtrlTabHostFragment.TabHostListener mTabHostListener;
/*     */   
/*     */   public DocumentView(@NonNull Context context) {
/*  42 */     super(context);
/*     */   }
/*     */   
/*     */   public DocumentView(@NonNull Context context, @Nullable AttributeSet attrs) {
/*  46 */     super(context, attrs);
/*     */   }
/*     */   
/*     */   public DocumentView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
/*  50 */     super(context, attrs, defStyleAttr);
/*     */   }
/*     */   
/*     */   public void setDocumentUri(Uri documentUri) {
/*  54 */     this.mDocumentUri = documentUri;
/*     */   }
/*     */   
/*     */   public void setPassword(String password) {
/*  58 */     this.mPassword = password;
/*     */   }
/*     */   
/*     */   public void setViewerConfig(ViewerConfig config) {
/*  62 */     this.mViewerConfig = config;
/*     */   }
/*     */   
/*     */   public void setCustomHeaders(JSONObject customHeaders) {
/*  66 */     this.mCustomHeaders = customHeaders;
/*     */   }
/*     */   
/*     */   public void setSupportFragmentManager(FragmentManager fragmentManager) {
/*  70 */     this.mFragmentManager = fragmentManager;
/*     */   }
/*     */   
/*     */   public void setNavIconResName(String resName) {
/*  74 */     if (resName == null) {
/*     */       return;
/*     */     }
/*  77 */     int res = Utils.getResourceDrawable(getContext(), resName);
/*  78 */     if (res != 0) {
/*  79 */       this.mNavIconRes = res;
/*     */     }
/*     */   }
/*     */   
/*     */   public void setShowNavIcon(boolean showNavIcon) {
/*  84 */     this.mShowNavIcon = showNavIcon;
/*     */   }
/*     */   
/*     */   public void setTabHostListener(PdfViewCtrlTabHostFragment.TabHostListener listener) {
/*  88 */     this.mTabHostListener = listener;
/*     */   }
/*     */   
/*     */   protected void buildViewer() {
/*  92 */     if (this.mDocumentUri == null) {
/*     */       return;
/*     */     }
/*  95 */     this
/*     */ 
/*     */       
/*  98 */       .mViewerBuilder = (ViewerBuilder)((ViewerBuilder)((ViewerBuilder)ViewerBuilder.withUri(this.mDocumentUri, this.mPassword).usingConfig(this.mViewerConfig)).usingNavIcon(this.mShowNavIcon ? this.mNavIconRes : 0)).usingCustomHeaders(this.mCustomHeaders);
/*     */   }
/*     */   
/*     */   protected PdfViewCtrlTabHostFragment getViewer() {
/* 102 */     return this.mViewerBuilder.build(getContext());
/*     */   }
/*     */ 
/*     */   
/*     */   private void prepView() {
/* 107 */     buildViewer();
/* 108 */     if (this.mViewerBuilder == null) {
/*     */       return;
/*     */     }
/*     */     
/* 112 */     if (this.mPdfViewCtrlTabHostFragment != null) {
/* 113 */       this.mPdfViewCtrlTabHostFragment.onOpenAddNewTab(this.mViewerBuilder.createBundle(getContext()));
/*     */     } else {
/* 115 */       this.mPdfViewCtrlTabHostFragment = getViewer();
/*     */       
/* 117 */       if (this.mFragmentManager != null) {
/* 118 */         this.mFragmentManager.beginTransaction()
/* 119 */           .add(this.mPdfViewCtrlTabHostFragment, TAG)
/* 120 */           .commitNow();
/*     */         
/* 122 */         View fragmentView = this.mPdfViewCtrlTabHostFragment.getView();
/* 123 */         if (fragmentView != null) {
/* 124 */           addView(fragmentView, -1, -1);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void cleanup() {
/* 131 */     if (this.mFragmentManager != null) {
/* 132 */       PdfViewCtrlTabHostFragment fragment = (PdfViewCtrlTabHostFragment)this.mFragmentManager.findFragmentByTag(TAG);
/* 133 */       if (fragment != null) {
/* 134 */         this.mFragmentManager.beginTransaction()
/* 135 */           .remove(fragment)
/* 136 */           .commitAllowingStateLoss();
/*     */       }
/*     */     } 
/* 139 */     this.mPdfViewCtrlTabHostFragment = null;
/* 140 */     this.mFragmentManager = null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onAttachedToWindow() {
/* 145 */     super.onAttachedToWindow();
/*     */     
/* 147 */     prepView();
/*     */     
/* 149 */     if (this.mPdfViewCtrlTabHostFragment != null) {
/* 150 */       this.mPdfViewCtrlTabHostFragment.addHostListener(this);
/* 151 */       if (this.mTabHostListener != null) {
/* 152 */         this.mPdfViewCtrlTabHostFragment.addHostListener(this.mTabHostListener);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onDetachedFromWindow() {
/* 159 */     super.onDetachedFromWindow();
/*     */     
/* 161 */     if (this.mPdfViewCtrlTabHostFragment != null) {
/* 162 */       this.mPdfViewCtrlTabHostFragment.removeHostListener(this);
/* 163 */       if (this.mTabHostListener != null) {
/* 164 */         this.mPdfViewCtrlTabHostFragment.removeHostListener(this.mTabHostListener);
/* 165 */         this.mTabHostListener = null;
/*     */       } 
/*     */     } 
/*     */     
/* 169 */     cleanup();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onTabHostShown() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onTabHostHidden() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLastTabClosed() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onTabChanged(String tag) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onOpenDocError() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNavButtonPressed() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void onShowFileInFolder(String fileName, String filepath, int itemSource) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canShowFileInFolder() {
/* 209 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canShowFileCloseSnackbar() {
/* 214 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onToolbarCreateOptionsMenu(Menu menu, MenuInflater inflater) {
/* 219 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onToolbarPrepareOptionsMenu(Menu menu) {
/* 224 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onToolbarOptionsItemSelected(MenuItem item) {
/* 229 */     return false;
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
/* 244 */     return false;
/*     */   }
/*     */   
/*     */   public void onTabPaused(FileInfo fileInfo, boolean b) {}
/*     */   
/*     */   public void onJumpToSdCardFolder() {}
/*     */   
/*     */   public void onTabDocumentLoaded(String tag) {}
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\controls\DocumentView.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */