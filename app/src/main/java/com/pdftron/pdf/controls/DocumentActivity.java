/*     */ package com.pdftron.pdf.controls;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.content.Intent;
/*     */ import android.net.Uri;
/*     */ import android.os.Bundle;
/*     */ import android.os.Parcelable;
/*     */ import android.util.Log;
/*     */ import android.view.Menu;
/*     */ import android.view.MenuInflater;
/*     */ import android.view.MenuItem;
/*     */ import androidx.annotation.DrawableRes;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.appcompat.app.AppCompatActivity;
/*     */ import androidx.fragment.app.Fragment;
/*     */ import androidx.fragment.app.FragmentManager;
/*     */ import androidx.fragment.app.FragmentTransaction;
/*     */ import com.pdftron.pdf.config.ViewerBuilder;
/*     */ import com.pdftron.pdf.config.ViewerConfig;
/*     */ import com.pdftron.pdf.dialog.SoundDialogFragment;
/*     */ import com.pdftron.pdf.model.FileInfo;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.utils.AppUtils;
/*     */ import com.pdftron.pdf.utils.PdfViewCtrlTabsManager;
/*     */ import com.pdftron.pdf.utils.ShortcutHelper;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.json.JSONException;
/*     */ import org.json.JSONObject;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DocumentActivity
/*     */   extends AppCompatActivity
/*     */   implements PdfViewCtrlTabHostFragment.TabHostListener
/*     */ {
/*     */   public static final String EXTRA_FILE_URI = "extra_file_uri";
/*     */   public static final String EXTRA_FILE_RES_ID = "extra_file_res_id";
/*     */   public static final String EXTRA_FILE_PASSWORD = "extra_file_password";
/*     */   public static final String EXTRA_CONFIG = "extra_config";
/*     */   public static final String EXTRA_CUSTOM_HEADERS = "extra_custom_headers";
/*     */   public static final String EXTRA_FILE_URI_LIST = "extra_file_uri_list";
/*     */   public static final String EXTRA_NAV_ICON = "extra_nav_icon";
/*     */   @DrawableRes
/*  53 */   public static final int DEFAULT_NAV_ICON_ID = R.drawable.ic_arrow_back_white_24dp;
/*     */   
/*     */   private static final String SAVE_INSTANCE_TABBED_HOST_FRAGMENT_TAG = "tabbed_host_fragment";
/*     */   
/*     */   protected PdfViewCtrlTabHostFragment mPdfViewCtrlTabHostFragment;
/*     */   protected ViewerConfig mViewerConfig;
/*     */   @DrawableRes
/*  60 */   protected int mNavigationIconId = DEFAULT_NAV_ICON_ID;
/*     */   
/*     */   protected JSONObject mCustomHeaders;
/*     */   
/*     */   protected ArrayList<Uri> mFileUris;
/*     */   
/*     */   private boolean mShouldOpenDocuments;
/*  67 */   protected int mSampleRes = 0;
/*  68 */   protected int[] mToolbarMenuResArray = new int[] { R.menu.fragment_viewer };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void openDocument(Context packageContext, Uri fileUri) {
/*  77 */     openDocument(packageContext, fileUri, "");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void openDocument(Context packageContext, Uri fileUri, @Nullable ViewerConfig config) {
/*  88 */     openDocument(packageContext, fileUri, "", config);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void openDocument(Context packageContext, int resId) {
/*  98 */     openDocument(packageContext, resId, "");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void openDocument(Context packageContext, int resId, @Nullable ViewerConfig config) {
/* 109 */     openDocument(packageContext, resId, "", config);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void openDocument(Context packageContext, Uri fileUri, String password) {
/* 120 */     openDocument(packageContext, fileUri, password, (ViewerConfig)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void openDocument(Context packageContext, int resId, String password) {
/* 131 */     openDocument(packageContext, resId, password, (ViewerConfig)null);
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
/*     */   public static void openDocument(Context packageContext, Uri fileUri, String password, @Nullable ViewerConfig config) {
/* 143 */     openDocument(packageContext, fileUri, password, (JSONObject)null, config);
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
/*     */   public static void openDocument(Context packageContext, Uri fileUri, String password, @Nullable JSONObject customHeaders, @Nullable ViewerConfig config) {
/* 156 */     openDocument(packageContext, fileUri, password, customHeaders, config, DEFAULT_NAV_ICON_ID);
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
/*     */   public static void openDocument(Context packageContext, Uri fileUri, String password, @Nullable JSONObject customHeaders, @Nullable ViewerConfig config, @DrawableRes int navIconId) {
/* 171 */     Intent intent = new Intent(packageContext, DocumentActivity.class);
/* 172 */     if (null != fileUri) {
/* 173 */       intent.putExtra("extra_file_uri", (Parcelable)fileUri);
/*     */     }
/* 175 */     if (null != password) {
/* 176 */       intent.putExtra("extra_file_password", password);
/*     */     }
/* 178 */     if (null != customHeaders) {
/* 179 */       intent.putExtra("extra_custom_headers", customHeaders.toString());
/*     */     }
/* 181 */     intent.putExtra("extra_nav_icon", navIconId);
/* 182 */     intent.putExtra("extra_config", (Parcelable)config);
/* 183 */     packageContext.startActivity(intent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void openDocuments(Context packageContext, ArrayList<Uri> fileUris, @Nullable ViewerConfig config) {
/* 193 */     Intent intent = new Intent(packageContext, DocumentActivity.class);
/* 194 */     if (null != fileUris) {
/* 195 */       intent.putParcelableArrayListExtra("extra_file_uri_list", fileUris);
/*     */     }
/* 197 */     intent.putExtra("extra_config", (Parcelable)config);
/* 198 */     packageContext.startActivity(intent);
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
/*     */   public static void openDocument(Context packageContext, int resId, String password, @Nullable ViewerConfig config) {
/* 210 */     Intent intent = new Intent(packageContext, DocumentActivity.class);
/* 211 */     intent.putExtra("extra_file_res_id", resId);
/* 212 */     if (null != password) {
/* 213 */       intent.putExtra("extra_file_password", password);
/*     */     }
/* 215 */     intent.putExtra("extra_config", (Parcelable)config);
/* 216 */     packageContext.startActivity(intent);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onCreate(@Nullable Bundle savedInstanceState) {
/* 221 */     super.onCreate(savedInstanceState);
/*     */     
/*     */     try {
/* 224 */       AppUtils.initializePDFNetApplication(getApplicationContext());
/* 225 */     } catch (Exception ex) {
/* 226 */       ex.printStackTrace();
/*     */       
/*     */       return;
/*     */     } 
/* 230 */     if (Utils.applyDayNight(this)) {
/*     */       return;
/*     */     }
/*     */     
/* 234 */     if (savedInstanceState != null) {
/*     */       
/* 236 */       this.mPdfViewCtrlTabHostFragment = (PdfViewCtrlTabHostFragment)getSupportFragmentManager().getFragment(savedInstanceState, "tabbed_host_fragment");
/*     */       
/* 238 */       if (this.mPdfViewCtrlTabHostFragment != null) {
/* 239 */         this.mPdfViewCtrlTabHostFragment.addHostListener(this);
/*     */       }
/*     */ 
/*     */       
/* 243 */       FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
/* 244 */       List<Fragment> fragments = getSupportFragmentManager().getFragments();
/* 245 */       for (Fragment fragment : fragments) {
/* 246 */         if (fragment instanceof PdfViewCtrlTabFragment || fragment instanceof androidx.fragment.app.DialogFragment)
/*     */         {
/* 248 */           ft.remove(fragment);
/*     */         }
/*     */       } 
/* 251 */       ft.commitNow();
/*     */     } 
/*     */     
/* 254 */     setContentView(R.layout.activity_document);
/*     */     
/* 256 */     if (getIntent() != null && getIntent().getExtras() != null) {
/* 257 */       this.mViewerConfig = (ViewerConfig)getIntent().getExtras().getParcelable("extra_config");
/* 258 */       this.mNavigationIconId = getIntent().getExtras().getInt("extra_nav_icon", DEFAULT_NAV_ICON_ID);
/*     */       try {
/* 260 */         String headers = getIntent().getExtras().getString("extra_custom_headers");
/* 261 */         if (headers != null) {
/* 262 */           this.mCustomHeaders = new JSONObject(headers);
/*     */         }
/* 264 */       } catch (JSONException jSONException) {}
/*     */     } 
/*     */ 
/*     */     
/* 268 */     ShortcutHelper.enable(true);
/*     */     
/* 270 */     if (null == this.mPdfViewCtrlTabHostFragment) {
/* 271 */       onDocumentSelected();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onResume() {
/* 277 */     super.onResume();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onDestroy() {
/* 282 */     super.onDestroy();
/*     */     
/* 284 */     if (null != this.mPdfViewCtrlTabHostFragment) {
/* 285 */       this.mPdfViewCtrlTabHostFragment.removeHostListener(this);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onSaveInstanceState(Bundle outState) {
/* 291 */     Log.v("LifeCycle", "Main.onSaveInstanceState");
/* 292 */     super.onSaveInstanceState(outState);
/*     */     
/* 294 */     FragmentManager fm = getSupportFragmentManager();
/* 295 */     List<Fragment> fragments = fm.getFragments();
/* 296 */     if (this.mPdfViewCtrlTabHostFragment != null && fragments.contains(this.mPdfViewCtrlTabHostFragment)) {
/* 297 */       fm.putFragment(outState, "tabbed_host_fragment", this.mPdfViewCtrlTabHostFragment);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBackPressed() {
/* 303 */     boolean handled = false;
/* 304 */     if (this.mPdfViewCtrlTabHostFragment != null) {
/* 305 */       handled = this.mPdfViewCtrlTabHostFragment.handleBackPressed();
/*     */     }
/* 307 */     if (!handled) {
/* 308 */       super.onBackPressed();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
/* 314 */     if (requestCode == 10015) {
/* 315 */       Fragment fragment = getSupportFragmentManager().findFragmentByTag(SoundDialogFragment.TAG);
/* 316 */       if (fragment != null && fragment instanceof SoundDialogFragment) {
/* 317 */         fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
/*     */       }
/*     */     } else {
/* 320 */       super.onRequestPermissionsResult(requestCode, permissions, grantResults);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void onDocumentSelected(Uri fileUri) {
/* 325 */     onDocumentSelected(fileUri, "");
/*     */   }
/*     */   
/*     */   protected void onDocumentSelected(Uri fileUri, String password) {
/* 329 */     ViewerBuilder builder = ViewerBuilder.withUri(fileUri, password);
/* 330 */     startTabHostFragment(builder);
/*     */   }
/*     */   
/*     */   protected void onDocumentsSelected(ArrayList<Uri> fileUris) {
/* 334 */     this.mFileUris = fileUris;
/* 335 */     if (this.mFileUris == null || this.mFileUris.isEmpty()) {
/*     */       return;
/*     */     }
/* 338 */     this.mShouldOpenDocuments = true;
/*     */     
/* 340 */     onDocumentSelected(this.mFileUris.get(0));
/*     */   }
/*     */   
/*     */   protected void onDocumentSelected() {
/* 344 */     if (isFinishing()) {
/*     */       return;
/*     */     }
/*     */     
/* 348 */     Uri fileUri = null;
/* 349 */     ArrayList<Uri> fileUris = null;
/* 350 */     String password = "";
/*     */     try {
/* 352 */       if (getIntent() != null && getIntent().getExtras() != null) {
/* 353 */         fileUri = (Uri)getIntent().getExtras().getParcelable("extra_file_uri");
/* 354 */         fileUris = getIntent().getExtras().getParcelableArrayList("extra_file_uri_list");
/* 355 */         if (fileUris != null && fileUris.size() > 0) {
/* 356 */           onDocumentsSelected(fileUris);
/*     */           return;
/*     */         } 
/* 359 */         int fileResId = getIntent().getExtras().getInt("extra_file_res_id", 0);
/* 360 */         password = getIntent().getExtras().getString("extra_file_password");
/*     */         
/* 362 */         if (null == fileUri && fileResId != 0) {
/* 363 */           File file = Utils.copyResourceToLocal((Context)this, fileResId, "untitled", ".pdf");
/*     */           
/* 365 */           if (null != file && file.exists()) {
/* 366 */             fileUri = Uri.fromFile(file);
/*     */           }
/*     */         } 
/*     */       } 
/* 370 */       int tabCount = PdfViewCtrlTabsManager.getInstance().getDocuments((Context)this).size();
/* 371 */       if (null == fileUri && tabCount == 0 && this.mSampleRes != 0) {
/* 372 */         File file = Utils.copyResourceToLocal((Context)this, this.mSampleRes, "getting_started", ".pdf");
/*     */         
/* 374 */         if (null != file && file.exists()) {
/* 375 */           fileUri = Uri.fromFile(file);
/* 376 */           password = "";
/*     */         } 
/*     */       } 
/* 379 */     } catch (Exception ex) {
/* 380 */       ex.printStackTrace();
/*     */     } 
/* 382 */     onDocumentSelected(fileUri, password);
/*     */   }
/*     */   
/*     */   protected void startTabHostFragment(@NonNull ViewerBuilder builder) {
/* 386 */     if (isFinishing()) {
/*     */       return;
/*     */     }
/*     */     
/* 390 */     ((ViewerBuilder)((ViewerBuilder)((ViewerBuilder)builder.usingNavIcon(this.mNavigationIconId))
/* 391 */       .usingCustomToolbar(this.mToolbarMenuResArray))
/* 392 */       .usingConfig(this.mViewerConfig))
/* 393 */       .usingCustomHeaders(this.mCustomHeaders);
/*     */     
/* 395 */     if (this.mPdfViewCtrlTabHostFragment != null) {
/* 396 */       this.mPdfViewCtrlTabHostFragment.onOpenAddNewTab(builder.createBundle((Context)this));
/*     */       
/*     */       return;
/*     */     } 
/* 400 */     FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
/* 401 */     this.mPdfViewCtrlTabHostFragment = builder.build((Context)this);
/* 402 */     this.mPdfViewCtrlTabHostFragment.addHostListener(this);
/*     */     
/* 404 */     ft.replace(R.id.container, this.mPdfViewCtrlTabHostFragment, null);
/* 405 */     ft.commitAllowingStateLoss();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onTabHostShown() {
/* 410 */     if (this.mShouldOpenDocuments) {
/* 411 */       this.mShouldOpenDocuments = false;
/* 412 */       if (this.mFileUris != null) {
/* 413 */         for (int i = 0; i < this.mFileUris.size(); i++) {
/* 414 */           if (i != 0) {
/* 415 */             Uri fileUri = this.mFileUris.get(i);
/* 416 */             onDocumentSelected(fileUri);
/*     */           } 
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
/* 430 */     finish();
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
/* 445 */     finish();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onShowFileInFolder(String fileName, String filepath, int itemSource) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canShowFileInFolder() {
/* 455 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canShowFileCloseSnackbar() {
/* 460 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onToolbarCreateOptionsMenu(Menu menu, MenuInflater inflater) {
/* 465 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onToolbarPrepareOptionsMenu(Menu menu) {
/* 470 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onToolbarOptionsItemSelected(MenuItem item) {
/* 475 */     return false;
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
/* 490 */     return true;
/*     */   }
/*     */   
/*     */   public void onTabPaused(FileInfo fileInfo, boolean isDocModifiedAfterOpening) {}
/*     */   
/*     */   public void onJumpToSdCardFolder() {}
/*     */   
/*     */   public void onTabDocumentLoaded(String tag) {}
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\controls\DocumentActivity.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */