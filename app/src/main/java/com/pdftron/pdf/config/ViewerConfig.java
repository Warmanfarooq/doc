/*      */ package com.pdftron.pdf.config;
/*      */ 
/*      */ import android.os.Parcel;
/*      */ import android.os.Parcelable;
/*      */ import androidx.annotation.NonNull;
/*      */ import androidx.annotation.Nullable;
/*      */ import androidx.annotation.RequiresApi;
/*      */ import androidx.annotation.StyleRes;
/*      */ import com.pdftron.pdf.dialog.ViewModePickerDialogFragment;
/*      */ import java.util.Arrays;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ViewerConfig
/*      */   implements Parcelable
/*      */ {
/*      */   public boolean isFullscreenModeEnabled() {
/*   28 */     return this.fullscreenModeEnabled;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isMultiTabEnabled() {
/*   35 */     return this.multiTabEnabled;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDocumentEditingEnabled() {
/*   42 */     return this.documentEditingEnabled;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isLongPressQuickMenuEnabled() {
/*   49 */     return this.longPressQuickMenuEnabled;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isShowPageNumberIndicator() {
/*   56 */     return this.showPageNumberIndicator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isShowBottomNavBar() {
/*   63 */     return this.showBottomNavBar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isShowTopToolbar() {
/*   70 */     return this.showTopToolbar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isPermanentTopToolbar() {
/*   77 */     return this.permanentTopToolbar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isShowThumbnailView() {
/*   84 */     return this.showThumbnailView;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isShowBookmarksView() {
/*   91 */     return this.showBookmarksView;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getToolbarTitle() {
/*   98 */     return this.toolbarTitle;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isShowSearchView() {
/*  105 */     return this.showSearchView;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isShowShareOption() {
/*  112 */     return this.showShareOption;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isShowDocumentSettingsOption() {
/*  119 */     return this.showDocumentSettingsOption;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isShowAnnotationToolbarOption() {
/*  126 */     return this.showAnnotationToolbarOption;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isShowFormToolbarOption() {
/*  133 */     return this.showFormToolbarOption;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isShowFillAndSignToolbarOption() {
/*  140 */     return this.showFillAndSignToolbarOption;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isShowViewLayersToolbarOption() {
/*  147 */     return this.showViewLayersOption;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isShowOpenFileOption() {
/*  154 */     return this.showOpenFileOption;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isShowOpenUrlOption() {
/*  161 */     return this.showOpenUrlOption;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isShowEditPagesOption() {
/*  168 */     return this.showEditPagesOption;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isShowPrintOption() {
/*  175 */     return this.showPrintOption;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isShowCloseTabOption() {
/*  182 */     return this.showCloseTabOption;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isShowAnnotationsList() {
/*  189 */     return this.showAnnotationsList;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isShowOutlineList() {
/*  196 */     return this.showOutlineList;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isShowUserBookmarksList() {
/*  203 */     return this.showUserBookmarksList;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isNavigationListAsSheetOnLargeDevice() {
/*  210 */     return this.navigationListAsSheetOnLargeDevice;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRightToLeftModeEnabled() {
/*  217 */     return this.rightToLeftModeEnabled;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isShowRightToLeftOption() {
/*  224 */     return this.showRightToLeftOption;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PDFViewCtrlConfig getPdfViewCtrlConfig() {
/*  231 */     return this.pdfViewCtrlConfig;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getToolManagerBuilderStyleRes() {
/*  238 */     return this.toolManagerBuilderStyleRes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ToolManagerBuilder getToolManagerBuilder() {
/*  245 */     return this.toolManagerBuilder;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getConversionOptions() {
/*  252 */     return this.conversionOptions;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getOpenUrlCachePath() {
/*  259 */     return this.openUrlCachePath;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getSaveCopyExportPath() {
/*  266 */     return this.saveCopyExportPath;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isUseSupportActionBar() {
/*  273 */     return this.useSupportActionBar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isShowSaveCopyOption() {
/*  280 */     return this.showSaveCopyOption;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isShowCropOption() {
/*  287 */     return this.showCropOption;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRestrictDownloadUsage() {
/*  294 */     return this.restrictDownloadUsage;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getLayoutInDisplayCutoutMode() {
/*  301 */     return this.layoutInDisplayCutoutMode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isThumbnailViewEditingEnabled() {
/*  308 */     return this.thumbnailViewEditingEnabled;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isUserBookmarksListEditingEnabled() {
/*  315 */     return this.userBookmarksListEditingEnabled;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean annotationsListEditingEnabled() {
/*  322 */     return this.annotationsListEditingEnabled;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaximumTabCount() {
/*  329 */     return this.maximumTabCount;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAutoHideToolbarEnabled() {
/*  336 */     return this.enableAutoHideToolbar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isUseStandardLibrary() {
/*  343 */     return this.useStandardLibrary;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public int[] getHideViewModeIds() {
/*  351 */     return this.hideViewModeIds;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isShowReflowOption() {
/*  358 */     return this.showReflowOption;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isShowEditMenuOption() {
/*  365 */     return this.showEditMenuOption;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isPageStackEnabled() {
/*  372 */     return this.pageStackEnabled;
/*      */   }
/*      */   
/*      */   private boolean fullscreenModeEnabled = true;
/*      */   private boolean multiTabEnabled = true;
/*      */   private boolean documentEditingEnabled = true;
/*      */   private boolean longPressQuickMenuEnabled = true;
/*      */   private boolean showPageNumberIndicator = true;
/*      */   private boolean showBottomNavBar = true;
/*      */   private boolean showThumbnailView = true;
/*      */   private boolean showBookmarksView = true;
/*      */   private String toolbarTitle;
/*      */   private boolean showSearchView = true;
/*      */   private boolean showShareOption = true;
/*      */   private boolean showDocumentSettingsOption = true;
/*      */   private boolean showAnnotationToolbarOption = true;
/*      */   private boolean showOpenFileOption = true;
/*      */   private boolean showOpenUrlOption = true;
/*      */   private boolean showEditPagesOption = true;
/*      */   private boolean showPrintOption = true;
/*      */   private boolean showCloseTabOption = true;
/*      */   private boolean showAnnotationsList = true;
/*      */   private boolean showOutlineList = true;
/*      */   private boolean showUserBookmarksList = true;
/*      */   private boolean rightToLeftModeEnabled = false;
/*      */   private boolean showRightToLeftOption = false;
/*      */   private PDFViewCtrlConfig pdfViewCtrlConfig;
/*  399 */   private int toolManagerBuilderStyleRes = 0;
/*      */   private ToolManagerBuilder toolManagerBuilder;
/*      */   private String conversionOptions;
/*      */   private String openUrlCachePath;
/*      */   private String saveCopyExportPath;
/*      */   private boolean useSupportActionBar = true;
/*      */   private boolean showSaveCopyOption = true;
/*      */   private boolean showCropOption = true;
/*      */   private boolean restrictDownloadUsage;
/*  408 */   private int layoutInDisplayCutoutMode = 0;
/*      */   private boolean thumbnailViewEditingEnabled = true;
/*      */   private boolean userBookmarksListEditingEnabled = true;
/*      */   private boolean annotationsListEditingEnabled = true;
/*  412 */   private int maximumTabCount = 0;
/*      */   
/*      */   private boolean enableAutoHideToolbar = true;
/*      */   
/*      */   private boolean showFormToolbarOption = true;
/*      */   
/*      */   private boolean showFillAndSignToolbarOption = true;
/*      */   private boolean navigationListAsSheetOnLargeDevice = true;
/*      */   private boolean showViewLayersOption = true;
/*      */   private boolean showTopToolbar = true;
/*      */   private boolean permanentTopToolbar;
/*      */   private boolean useStandardLibrary;
/*      */   private int[] hideViewModeIds;
/*      */   private boolean showReflowOption = true;
/*      */   private boolean showEditMenuOption = true;
/*      */   private boolean pageStackEnabled = true;
/*      */   
/*      */   protected ViewerConfig(Parcel in) {
/*  430 */     this.fullscreenModeEnabled = (in.readByte() != 0);
/*  431 */     this.multiTabEnabled = (in.readByte() != 0);
/*  432 */     this.documentEditingEnabled = (in.readByte() != 0);
/*  433 */     this.longPressQuickMenuEnabled = (in.readByte() != 0);
/*  434 */     this.showPageNumberIndicator = (in.readByte() != 0);
/*  435 */     this.showBottomNavBar = (in.readByte() != 0);
/*  436 */     this.showThumbnailView = (in.readByte() != 0);
/*  437 */     this.showBookmarksView = (in.readByte() != 0);
/*  438 */     this.toolbarTitle = in.readString();
/*  439 */     this.showSearchView = (in.readByte() != 0);
/*  440 */     this.showShareOption = (in.readByte() != 0);
/*  441 */     this.showDocumentSettingsOption = (in.readByte() != 0);
/*  442 */     this.showAnnotationToolbarOption = (in.readByte() != 0);
/*  443 */     this.showOpenFileOption = (in.readByte() != 0);
/*  444 */     this.showOpenUrlOption = (in.readByte() != 0);
/*  445 */     this.showEditPagesOption = (in.readByte() != 0);
/*  446 */     this.showPrintOption = (in.readByte() != 0);
/*  447 */     this.showCloseTabOption = (in.readByte() != 0);
/*  448 */     this.showAnnotationsList = (in.readByte() != 0);
/*  449 */     this.showOutlineList = (in.readByte() != 0);
/*  450 */     this.showUserBookmarksList = (in.readByte() != 0);
/*  451 */     this.rightToLeftModeEnabled = (in.readByte() != 0);
/*  452 */     this.showRightToLeftOption = (in.readByte() != 0);
/*  453 */     this.pdfViewCtrlConfig = (PDFViewCtrlConfig)in.readParcelable(PDFViewCtrlConfig.class.getClassLoader());
/*  454 */     this.toolManagerBuilderStyleRes = in.readInt();
/*  455 */     this.toolManagerBuilder = (ToolManagerBuilder)in.readParcelable(ToolManagerBuilder.class.getClassLoader());
/*  456 */     this.conversionOptions = in.readString();
/*  457 */     this.openUrlCachePath = in.readString();
/*  458 */     this.saveCopyExportPath = in.readString();
/*  459 */     this.useSupportActionBar = (in.readByte() != 0);
/*  460 */     this.showSaveCopyOption = (in.readByte() != 0);
/*  461 */     this.restrictDownloadUsage = (in.readByte() != 0);
/*  462 */     this.showCropOption = (in.readByte() != 0);
/*  463 */     this.layoutInDisplayCutoutMode = in.readInt();
/*  464 */     this.thumbnailViewEditingEnabled = (in.readByte() != 0);
/*  465 */     this.userBookmarksListEditingEnabled = (in.readByte() != 0);
/*  466 */     this.annotationsListEditingEnabled = (in.readByte() != 0);
/*  467 */     this.maximumTabCount = in.readInt();
/*  468 */     this.enableAutoHideToolbar = (in.readByte() != 0);
/*  469 */     this.showFormToolbarOption = (in.readByte() != 0);
/*  470 */     this.showFillAndSignToolbarOption = (in.readByte() != 0);
/*  471 */     this.navigationListAsSheetOnLargeDevice = (in.readByte() != 0);
/*  472 */     this.showViewLayersOption = (in.readByte() != 0);
/*  473 */     this.showTopToolbar = (in.readByte() != 0);
/*  474 */     this.permanentTopToolbar = (in.readByte() != 0);
/*  475 */     this.useStandardLibrary = (in.readByte() != 0);
/*  476 */     int hideViewModeIdsSize = in.readInt();
/*  477 */     this.hideViewModeIds = new int[hideViewModeIdsSize];
/*  478 */     in.readIntArray(this.hideViewModeIds);
/*  479 */     this.showReflowOption = (in.readByte() != 0);
/*  480 */     this.showEditMenuOption = (in.readByte() != 0);
/*  481 */     this.pageStackEnabled = (in.readByte() != 0);
/*      */   }
/*      */   
/*  484 */   public static final Creator<ViewerConfig> CREATOR = new Creator<ViewerConfig>()
/*      */     {
/*      */       public ViewerConfig createFromParcel(Parcel in) {
/*  487 */         return new ViewerConfig(in);
/*      */       }
/*      */ 
/*      */       
/*      */       public ViewerConfig[] newArray(int size) {
/*  492 */         return new ViewerConfig[size];
/*      */       }
/*      */     };
/*      */ 
/*      */   
/*      */   public int describeContents() {
/*  498 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeToParcel(Parcel parcel, int i) {
/*  503 */     parcel.writeByte((byte)(this.fullscreenModeEnabled ? 1 : 0));
/*  504 */     parcel.writeByte((byte)(this.multiTabEnabled ? 1 : 0));
/*  505 */     parcel.writeByte((byte)(this.documentEditingEnabled ? 1 : 0));
/*  506 */     parcel.writeByte((byte)(this.longPressQuickMenuEnabled ? 1 : 0));
/*  507 */     parcel.writeByte((byte)(this.showPageNumberIndicator ? 1 : 0));
/*  508 */     parcel.writeByte((byte)(this.showBottomNavBar ? 1 : 0));
/*  509 */     parcel.writeByte((byte)(this.showThumbnailView ? 1 : 0));
/*  510 */     parcel.writeByte((byte)(this.showBookmarksView ? 1 : 0));
/*  511 */     parcel.writeString(this.toolbarTitle);
/*  512 */     parcel.writeByte((byte)(this.showSearchView ? 1 : 0));
/*  513 */     parcel.writeByte((byte)(this.showShareOption ? 1 : 0));
/*  514 */     parcel.writeByte((byte)(this.showDocumentSettingsOption ? 1 : 0));
/*  515 */     parcel.writeByte((byte)(this.showAnnotationToolbarOption ? 1 : 0));
/*  516 */     parcel.writeByte((byte)(this.showOpenFileOption ? 1 : 0));
/*  517 */     parcel.writeByte((byte)(this.showOpenUrlOption ? 1 : 0));
/*  518 */     parcel.writeByte((byte)(this.showEditPagesOption ? 1 : 0));
/*  519 */     parcel.writeByte((byte)(this.showPrintOption ? 1 : 0));
/*  520 */     parcel.writeByte((byte)(this.showCloseTabOption ? 1 : 0));
/*  521 */     parcel.writeByte((byte)(this.showAnnotationsList ? 1 : 0));
/*  522 */     parcel.writeByte((byte)(this.showOutlineList ? 1 : 0));
/*  523 */     parcel.writeByte((byte)(this.showUserBookmarksList ? 1 : 0));
/*  524 */     parcel.writeByte((byte)(this.rightToLeftModeEnabled ? 1 : 0));
/*  525 */     parcel.writeByte((byte)(this.showRightToLeftOption ? 1 : 0));
/*  526 */     parcel.writeParcelable(this.pdfViewCtrlConfig, i);
/*  527 */     parcel.writeInt(this.toolManagerBuilderStyleRes);
/*  528 */     parcel.writeParcelable(this.toolManagerBuilder, i);
/*  529 */     parcel.writeString(this.conversionOptions);
/*  530 */     parcel.writeString(this.openUrlCachePath);
/*  531 */     parcel.writeString(this.saveCopyExportPath);
/*  532 */     parcel.writeByte((byte)(this.useSupportActionBar ? 1 : 0));
/*  533 */     parcel.writeByte((byte)(this.showSaveCopyOption ? 1 : 0));
/*  534 */     parcel.writeByte((byte)(this.restrictDownloadUsage ? 1 : 0));
/*  535 */     parcel.writeByte((byte)(this.showCropOption ? 1 : 0));
/*  536 */     parcel.writeInt(this.layoutInDisplayCutoutMode);
/*  537 */     parcel.writeByte((byte)(this.thumbnailViewEditingEnabled ? 1 : 0));
/*  538 */     parcel.writeByte((byte)(this.userBookmarksListEditingEnabled ? 1 : 0));
/*  539 */     parcel.writeByte((byte)(this.annotationsListEditingEnabled ? 1 : 0));
/*  540 */     parcel.writeInt(this.maximumTabCount);
/*  541 */     parcel.writeByte((byte)(this.enableAutoHideToolbar ? 1 : 0));
/*  542 */     parcel.writeByte((byte)(this.showFormToolbarOption ? 1 : 0));
/*  543 */     parcel.writeByte((byte)(this.showFillAndSignToolbarOption ? 1 : 0));
/*  544 */     parcel.writeByte((byte)(this.navigationListAsSheetOnLargeDevice ? 1 : 0));
/*  545 */     parcel.writeByte((byte)(this.showViewLayersOption ? 1 : 0));
/*  546 */     parcel.writeByte((byte)(this.showTopToolbar ? 1 : 0));
/*  547 */     parcel.writeByte((byte)(this.permanentTopToolbar ? 1 : 0));
/*  548 */     parcel.writeByte((byte)(this.useStandardLibrary ? 1 : 0));
/*  549 */     if (null == this.hideViewModeIds) {
/*  550 */       this.hideViewModeIds = new int[0];
/*      */     }
/*  552 */     int hideViewModeIdsSize = this.hideViewModeIds.length;
/*  553 */     parcel.writeInt(hideViewModeIdsSize);
/*  554 */     parcel.writeIntArray(this.hideViewModeIds);
/*  555 */     parcel.writeByte((byte)(this.showReflowOption ? 1 : 0));
/*  556 */     parcel.writeByte((byte)(this.showEditMenuOption ? 1 : 0));
/*  557 */     parcel.writeByte((byte)(this.pageStackEnabled ? 1 : 0));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static class Builder
/*      */   {
/*  564 */     private ViewerConfig mViewerConfig = new ViewerConfig();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder fullscreenModeEnabled(boolean fullscreenModeEnabled) {
/*  570 */       this.mViewerConfig.fullscreenModeEnabled = fullscreenModeEnabled;
/*  571 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder multiTabEnabled(boolean multiTab) {
/*  578 */       this.mViewerConfig.multiTabEnabled = multiTab;
/*  579 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder documentEditingEnabled(boolean documentEditingEnabled) {
/*  587 */       this.mViewerConfig.documentEditingEnabled = documentEditingEnabled;
/*  588 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder longPressQuickMenuEnabled(boolean longPressQuickMenuEnabled) {
/*  595 */       this.mViewerConfig.longPressQuickMenuEnabled = longPressQuickMenuEnabled;
/*  596 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder showPageNumberIndicator(boolean showPageNumberIndicator) {
/*  603 */       this.mViewerConfig.showPageNumberIndicator = showPageNumberIndicator;
/*  604 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder pageStackEnabled(boolean pageStackEnabled) {
/*  611 */       this.mViewerConfig.pageStackEnabled = pageStackEnabled;
/*  612 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder showBottomNavBar(boolean showBottomNavBar) {
/*  619 */       this.mViewerConfig.showBottomNavBar = showBottomNavBar;
/*  620 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder showTopToolbar(boolean showTopToolbar) {
/*  627 */       this.mViewerConfig.showTopToolbar = showTopToolbar;
/*  628 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder permanentTopToolbar(boolean permanentTopToolbar) {
/*  638 */       this.mViewerConfig.permanentTopToolbar = permanentTopToolbar;
/*  639 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder showThumbnailView(boolean showThumbnailView) {
/*  648 */       this.mViewerConfig.showThumbnailView = showThumbnailView;
/*  649 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder showBookmarksView(boolean showBookmarksView) {
/*  662 */       this.mViewerConfig.showBookmarksView = showBookmarksView;
/*  663 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder toolbarTitle(String toolbarTitle) {
/*  672 */       this.mViewerConfig.toolbarTitle = toolbarTitle;
/*  673 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder showSearchView(boolean showSearchView) {
/*  682 */       this.mViewerConfig.showSearchView = showSearchView;
/*  683 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder showShareOption(boolean showShareOption) {
/*  692 */       this.mViewerConfig.showShareOption = showShareOption;
/*  693 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder showDocumentSettingsOption(boolean showDocumentSettingsOption) {
/*  702 */       this.mViewerConfig.showDocumentSettingsOption = showDocumentSettingsOption;
/*  703 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder showAnnotationToolbarOption(boolean showAnnotationToolbarOption) {
/*  712 */       this.mViewerConfig.showAnnotationToolbarOption = showAnnotationToolbarOption;
/*  713 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder showFormToolbarOption(boolean showFormToolbarOption) {
/*  722 */       this.mViewerConfig.showFormToolbarOption = showFormToolbarOption;
/*  723 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder showFillAndSignToolbarOption(boolean showFillAndSignToolbarOption) {
/*  732 */       this.mViewerConfig.showFillAndSignToolbarOption = showFillAndSignToolbarOption;
/*  733 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder showReflowOption(boolean showReflowOption) {
/*  745 */       this.mViewerConfig.showReflowOption = showReflowOption;
/*  746 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder showEditMenuOption(boolean showEditMenuOption) {
/*  755 */       this.mViewerConfig.showEditMenuOption = showEditMenuOption;
/*  756 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder showViewLayersToolbarOption(boolean showViewLayersOption) {
/*  765 */       this.mViewerConfig.showViewLayersOption = showViewLayersOption;
/*  766 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder showOpenFileOption(boolean showOpenFileOption) {
/*  775 */       this.mViewerConfig.showOpenFileOption = showOpenFileOption;
/*  776 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder showOpenUrlOption(boolean showOpenUrlOption) {
/*  785 */       this.mViewerConfig.showOpenUrlOption = showOpenUrlOption;
/*  786 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder showEditPagesOption(boolean showEditPagesOption) {
/*  795 */       this.mViewerConfig.showEditPagesOption = showEditPagesOption;
/*  796 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder showPrintOption(boolean showPrintOption) {
/*  805 */       this.mViewerConfig.showPrintOption = showPrintOption;
/*  806 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder showSaveCopyOption(boolean showSaveCopyOption) {
/*  815 */       this.mViewerConfig.showSaveCopyOption = showSaveCopyOption;
/*  816 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder hideViewModeItems(@NonNull ViewModePickerDialogFragment.ViewModePickerItems[] ids) {
/*  826 */       this.mViewerConfig.hideViewModeIds = new int[ids.length];
/*  827 */       for (int i = 0; i < ids.length; i++) {
/*  828 */         this.mViewerConfig.hideViewModeIds[i] = ids[i].getValue();
/*      */       }
/*  830 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Builder showCropOption(boolean showCropOption) {
/*  841 */       this.mViewerConfig.showCropOption = showCropOption;
/*  842 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder showCloseTabOption(boolean showCloseTabOption) {
/*  851 */       this.mViewerConfig.showCloseTabOption = showCloseTabOption;
/*  852 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder showAnnotationsList(boolean showAnnotationsList) {
/*  861 */       this.mViewerConfig.showAnnotationsList = showAnnotationsList;
/*  862 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder showOutlineList(boolean showOutlineList) {
/*  871 */       this.mViewerConfig.showOutlineList = showOutlineList;
/*  872 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder showUserBookmarksList(boolean showUserBookmarksList) {
/*  881 */       this.mViewerConfig.showUserBookmarksList = showUserBookmarksList;
/*  882 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder navigationListAsSheetOnLargeDevice(boolean navigationListAsSheetOnLargeDevice) {
/*  889 */       this.mViewerConfig.navigationListAsSheetOnLargeDevice = navigationListAsSheetOnLargeDevice;
/*  890 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder rightToLeftModeEnabled(boolean rightToLeftModeEnabled) {
/*  899 */       this.mViewerConfig.rightToLeftModeEnabled = rightToLeftModeEnabled;
/*  900 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder showRightToLeftOption(boolean showRightToLeftOption) {
/*  909 */       this.mViewerConfig.showRightToLeftOption = showRightToLeftOption;
/*  910 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder pdfViewCtrlConfig(PDFViewCtrlConfig config) {
/*  917 */       this.mViewerConfig.pdfViewCtrlConfig = config;
/*  918 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder toolManagerBuilderStyleRes(@StyleRes int styleRes) {
/*  925 */       this.mViewerConfig.toolManagerBuilderStyleRes = styleRes;
/*  926 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Builder setToolManagerBuilder(ToolManagerBuilder toolManagerBuilder) {
/*  935 */       this.mViewerConfig.toolManagerBuilder = toolManagerBuilder;
/*  936 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder toolManagerBuilder(ToolManagerBuilder toolManagerBuilder) {
/*  943 */       this.mViewerConfig.toolManagerBuilder = toolManagerBuilder;
/*  944 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder conversionOptions(String conversionOptions) {
/*  951 */       this.mViewerConfig.conversionOptions = conversionOptions;
/*  952 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder openUrlCachePath(String openUrlCachePath) {
/*  959 */       this.mViewerConfig.openUrlCachePath = openUrlCachePath;
/*  960 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder saveCopyExportPath(String exportPath) {
/*  967 */       this.mViewerConfig.saveCopyExportPath = exportPath;
/*  968 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder useSupportActionBar(boolean useSupportActionBar) {
/*  975 */       this.mViewerConfig.useSupportActionBar = useSupportActionBar;
/*  976 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder restrictDownloadUsage(boolean restrictDownloadUsage) {
/*  983 */       this.mViewerConfig.restrictDownloadUsage = restrictDownloadUsage;
/*  984 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @RequiresApi(api = 28)
/*      */     public Builder layoutInDisplayCutoutMode(int cutoutMode) {
/*  992 */       this.mViewerConfig.layoutInDisplayCutoutMode = cutoutMode;
/*  993 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder thumbnailViewEditingEnabled(boolean thumbnailViewEditingEnabled) {
/* 1001 */       this.mViewerConfig.thumbnailViewEditingEnabled = thumbnailViewEditingEnabled;
/* 1002 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder userBookmarksListEditingEnabled(boolean userBookmarksListEditingEnabled) {
/* 1010 */       this.mViewerConfig.userBookmarksListEditingEnabled = userBookmarksListEditingEnabled;
/* 1011 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder annotationsListEditingEnabled(boolean annotationsListEditingEnabled) {
/* 1019 */       this.mViewerConfig.annotationsListEditingEnabled = annotationsListEditingEnabled;
/* 1020 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder maximumTabCount(int count) {
/* 1028 */       this.mViewerConfig.maximumTabCount = count;
/* 1029 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder autoHideToolbarEnabled(boolean shouldAutoHide) {
/* 1037 */       this.mViewerConfig.enableAutoHideToolbar = shouldAutoHide;
/* 1038 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder useStandardLibrary(boolean useStandard) {
/* 1048 */       this.mViewerConfig.useStandardLibrary = useStandard;
/* 1049 */       return this;
/*      */     }
/*      */     
/*      */     public ViewerConfig build() {
/* 1053 */       if (this.mViewerConfig.useStandardLibrary) {
/*      */         
/* 1055 */         this.mViewerConfig.showReflowOption = false;
/* 1056 */         this.mViewerConfig.showRightToLeftOption = false;
/*      */       } 
/* 1058 */       if (!this.mViewerConfig.documentEditingEnabled) {
/*      */         
/* 1060 */         this.mViewerConfig.annotationsListEditingEnabled = false;
/* 1061 */         this.mViewerConfig.thumbnailViewEditingEnabled = false;
/* 1062 */         this.mViewerConfig.showEditPagesOption = false;
/* 1063 */         this.mViewerConfig.showAnnotationToolbarOption = false;
/* 1064 */         this.mViewerConfig.showFormToolbarOption = false;
/* 1065 */         this.mViewerConfig.showFillAndSignToolbarOption = false;
/*      */       } 
/* 1067 */       return this.mViewerConfig;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object o) {
/* 1074 */     if (this == o) return true; 
/* 1075 */     if (o == null || getClass() != o.getClass()) return false;
/*      */     
/* 1077 */     ViewerConfig that = (ViewerConfig)o;
/*      */     
/* 1079 */     if (this.fullscreenModeEnabled != that.fullscreenModeEnabled) return false; 
/* 1080 */     if (this.multiTabEnabled != that.multiTabEnabled) return false; 
/* 1081 */     if (this.documentEditingEnabled != that.documentEditingEnabled) return false; 
/* 1082 */     if (this.longPressQuickMenuEnabled != that.longPressQuickMenuEnabled) return false; 
/* 1083 */     if (this.showPageNumberIndicator != that.showPageNumberIndicator) return false; 
/* 1084 */     if (this.showBottomNavBar != that.showBottomNavBar) return false; 
/* 1085 */     if (this.showThumbnailView != that.showThumbnailView) return false; 
/* 1086 */     if (this.showBookmarksView != that.showBookmarksView) return false; 
/* 1087 */     if (this.showSearchView != that.showSearchView) return false; 
/* 1088 */     if (this.showShareOption != that.showShareOption) return false; 
/* 1089 */     if (this.showDocumentSettingsOption != that.showDocumentSettingsOption) return false; 
/* 1090 */     if (this.showAnnotationToolbarOption != that.showAnnotationToolbarOption) return false; 
/* 1091 */     if (this.showOpenFileOption != that.showOpenFileOption) return false; 
/* 1092 */     if (this.showOpenUrlOption != that.showOpenUrlOption) return false; 
/* 1093 */     if (this.showEditPagesOption != that.showEditPagesOption) return false; 
/* 1094 */     if (this.showPrintOption != that.showPrintOption) return false; 
/* 1095 */     if (this.showCloseTabOption != that.showCloseTabOption) return false; 
/* 1096 */     if (this.showAnnotationsList != that.showAnnotationsList) return false; 
/* 1097 */     if (this.showOutlineList != that.showOutlineList) return false; 
/* 1098 */     if (this.showUserBookmarksList != that.showUserBookmarksList) return false; 
/* 1099 */     if (this.rightToLeftModeEnabled != that.rightToLeftModeEnabled) return false; 
/* 1100 */     if (this.showRightToLeftOption != that.showRightToLeftOption) return false; 
/* 1101 */     if (this.toolManagerBuilderStyleRes != that.toolManagerBuilderStyleRes) return false; 
/* 1102 */     if (this.useSupportActionBar != that.useSupportActionBar) return false; 
/* 1103 */     if (this.showSaveCopyOption != that.showSaveCopyOption) return false; 
/* 1104 */     if (this.showCropOption != that.showCropOption) return false; 
/* 1105 */     if (this.restrictDownloadUsage != that.restrictDownloadUsage) return false; 
/* 1106 */     if (this.layoutInDisplayCutoutMode != that.layoutInDisplayCutoutMode) return false; 
/* 1107 */     if (this.thumbnailViewEditingEnabled != that.thumbnailViewEditingEnabled) return false; 
/* 1108 */     if (this.userBookmarksListEditingEnabled != that.userBookmarksListEditingEnabled) return false; 
/* 1109 */     if (this.annotationsListEditingEnabled != that.annotationsListEditingEnabled) return false; 
/* 1110 */     if (this.maximumTabCount != that.maximumTabCount) return false; 
/* 1111 */     if (this.enableAutoHideToolbar != that.enableAutoHideToolbar) return false; 
/* 1112 */     if (this.showFormToolbarOption != that.showFormToolbarOption) return false; 
/* 1113 */     if (this.showFillAndSignToolbarOption != that.showFillAndSignToolbarOption) return false; 
/* 1114 */     if (this.navigationListAsSheetOnLargeDevice != that.navigationListAsSheetOnLargeDevice)
/* 1115 */       return false; 
/* 1116 */     if (this.showViewLayersOption != that.showViewLayersOption) return false; 
/* 1117 */     if (this.showTopToolbar != that.showTopToolbar) return false; 
/* 1118 */     if (this.permanentTopToolbar != that.permanentTopToolbar) return false; 
/* 1119 */     if (this.useStandardLibrary != that.useStandardLibrary) return false; 
/* 1120 */     if (this.showReflowOption != that.showReflowOption) return false; 
/* 1121 */     if (this.showEditMenuOption != that.showEditMenuOption) return false; 
/* 1122 */     if (this.pageStackEnabled != that.pageStackEnabled) return false; 
/* 1123 */     if ((this.toolbarTitle != null) ? !this.toolbarTitle.equals(that.toolbarTitle) : (that.toolbarTitle != null))
/* 1124 */       return false; 
/* 1125 */     if ((this.pdfViewCtrlConfig != null) ? !this.pdfViewCtrlConfig.equals(that.pdfViewCtrlConfig) : (that.pdfViewCtrlConfig != null))
/* 1126 */       return false; 
/* 1127 */     if ((this.toolManagerBuilder != null) ? !this.toolManagerBuilder.equals(that.toolManagerBuilder) : (that.toolManagerBuilder != null))
/* 1128 */       return false; 
/* 1129 */     if ((this.conversionOptions != null) ? !this.conversionOptions.equals(that.conversionOptions) : (that.conversionOptions != null))
/* 1130 */       return false; 
/* 1131 */     if ((this.openUrlCachePath != null) ? !this.openUrlCachePath.equals(that.openUrlCachePath) : (that.openUrlCachePath != null))
/* 1132 */       return false; 
/* 1133 */     if ((this.saveCopyExportPath != null) ? !this.saveCopyExportPath.equals(that.saveCopyExportPath) : (that.saveCopyExportPath != null))
/* 1134 */       return false; 
/* 1135 */     return Arrays.equals(this.hideViewModeIds, that.hideViewModeIds);
/*      */   }
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1140 */     int result = this.fullscreenModeEnabled ? 1 : 0;
/* 1141 */     result = 31 * result + (this.multiTabEnabled ? 1 : 0);
/* 1142 */     result = 31 * result + (this.documentEditingEnabled ? 1 : 0);
/* 1143 */     result = 31 * result + (this.longPressQuickMenuEnabled ? 1 : 0);
/* 1144 */     result = 31 * result + (this.showPageNumberIndicator ? 1 : 0);
/* 1145 */     result = 31 * result + (this.showBottomNavBar ? 1 : 0);
/* 1146 */     result = 31 * result + (this.showThumbnailView ? 1 : 0);
/* 1147 */     result = 31 * result + (this.showBookmarksView ? 1 : 0);
/* 1148 */     result = 31 * result + ((this.toolbarTitle != null) ? this.toolbarTitle.hashCode() : 0);
/* 1149 */     result = 31 * result + (this.showSearchView ? 1 : 0);
/* 1150 */     result = 31 * result + (this.showShareOption ? 1 : 0);
/* 1151 */     result = 31 * result + (this.showDocumentSettingsOption ? 1 : 0);
/* 1152 */     result = 31 * result + (this.showAnnotationToolbarOption ? 1 : 0);
/* 1153 */     result = 31 * result + (this.showOpenFileOption ? 1 : 0);
/* 1154 */     result = 31 * result + (this.showOpenUrlOption ? 1 : 0);
/* 1155 */     result = 31 * result + (this.showEditPagesOption ? 1 : 0);
/* 1156 */     result = 31 * result + (this.showPrintOption ? 1 : 0);
/* 1157 */     result = 31 * result + (this.showCloseTabOption ? 1 : 0);
/* 1158 */     result = 31 * result + (this.showAnnotationsList ? 1 : 0);
/* 1159 */     result = 31 * result + (this.showOutlineList ? 1 : 0);
/* 1160 */     result = 31 * result + (this.showUserBookmarksList ? 1 : 0);
/* 1161 */     result = 31 * result + (this.rightToLeftModeEnabled ? 1 : 0);
/* 1162 */     result = 31 * result + (this.showRightToLeftOption ? 1 : 0);
/* 1163 */     result = 31 * result + ((this.pdfViewCtrlConfig != null) ? this.pdfViewCtrlConfig.hashCode() : 0);
/* 1164 */     result = 31 * result + this.toolManagerBuilderStyleRes;
/* 1165 */     result = 31 * result + ((this.toolManagerBuilder != null) ? this.toolManagerBuilder.hashCode() : 0);
/* 1166 */     result = 31 * result + ((this.conversionOptions != null) ? this.conversionOptions.hashCode() : 0);
/* 1167 */     result = 31 * result + ((this.openUrlCachePath != null) ? this.openUrlCachePath.hashCode() : 0);
/* 1168 */     result = 31 * result + ((this.saveCopyExportPath != null) ? this.saveCopyExportPath.hashCode() : 0);
/* 1169 */     result = 31 * result + (this.useSupportActionBar ? 1 : 0);
/* 1170 */     result = 31 * result + (this.showSaveCopyOption ? 1 : 0);
/* 1171 */     result = 31 * result + (this.showCropOption ? 1 : 0);
/* 1172 */     result = 31 * result + (this.restrictDownloadUsage ? 1 : 0);
/* 1173 */     result = 31 * result + this.layoutInDisplayCutoutMode;
/* 1174 */     result = 31 * result + (this.thumbnailViewEditingEnabled ? 1 : 0);
/* 1175 */     result = 31 * result + (this.userBookmarksListEditingEnabled ? 1 : 0);
/* 1176 */     result = 31 * result + (this.annotationsListEditingEnabled ? 1 : 0);
/* 1177 */     result = 31 * result + this.maximumTabCount;
/* 1178 */     result = 31 * result + (this.enableAutoHideToolbar ? 1 : 0);
/* 1179 */     result = 31 * result + (this.showFormToolbarOption ? 1 : 0);
/* 1180 */     result = 31 * result + (this.showFillAndSignToolbarOption ? 1 : 0);
/* 1181 */     result = 31 * result + (this.navigationListAsSheetOnLargeDevice ? 1 : 0);
/* 1182 */     result = 31 * result + (this.showViewLayersOption ? 1 : 0);
/* 1183 */     result = 31 * result + (this.showTopToolbar ? 1 : 0);
/* 1184 */     result = 31 * result + (this.permanentTopToolbar ? 1 : 0);
/* 1185 */     result = 31 * result + (this.useStandardLibrary ? 1 : 0);
/* 1186 */     result = 31 * result + (this.showReflowOption ? 1 : 0);
/* 1187 */     result = 31 * result + (this.showEditMenuOption ? 1 : 0);
/* 1188 */     result = 31 * result + (this.pageStackEnabled ? 1 : 0);
/* 1189 */     result = 31 * result + Arrays.hashCode(this.hideViewModeIds);
/* 1190 */     return result;
/*      */   }
/*      */   
/*      */   public ViewerConfig() {}
/*      */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\config\ViewerConfig.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */