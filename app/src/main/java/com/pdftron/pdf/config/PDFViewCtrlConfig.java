/*     */ package com.pdftron.pdf.config;
/*     */ 
/*     */ import android.annotation.TargetApi;
/*     */ import android.content.Context;
/*     */ import android.graphics.Point;
/*     */ import android.os.Parcel;
/*     */ import android.os.Parcelable;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.utils.Utils;
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
/*     */ public class PDFViewCtrlConfig
/*     */   implements Parcelable
/*     */ {
/*  27 */   private static final String TAG = PDFViewCtrlConfig.class.getName();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final double MIN_RELATIVE_ZOOM_LIMIT = 1.0D;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final double MAX_RELATIVE_ZOOM_LIMIT = 20.0D;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final double MIN_RELATIVE_REF_ZOOM_DP = 0.5D;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PDFViewCtrlConfig(Context context)
/*     */   {
/* 415 */     this.directionalScrollLockEnabled = true;
/*     */     
/* 417 */     this.imageSmoothing = true;
/*     */     
/* 419 */     this.highlightFields = true;
/* 420 */     this.pageViewMode = PDFViewCtrl.PageViewMode.FIT_PAGE;
/* 421 */     this.pageRefViewMode = PDFViewCtrl.PageViewMode.FIT_PAGE;
/* 422 */     this.pagePreferredViewMode = PDFViewCtrl.PageViewMode.FIT_PAGE;
/* 423 */     this.maintainZoomEnabled = true;
/* 424 */     this.deviceDensityScaleFactor = 1;
/*     */     
/* 426 */     this.urlExtraction = true;
/* 427 */     this.thumbnailUseEmbedded = false;
/* 428 */     this.thumbnailGenerateAtRuntime = true;
/* 429 */     this.thumbnailUseDiskCache = true;
/*     */     
/* 431 */     this.thumbnailMaxAbsoluteCacheSize = 52428800L;
/* 432 */     this.thumbnailMaxPercentageCacheSize = 0.1D;
/* 433 */     this.pageHorizontalColumnSpacing = 3;
/* 434 */     this.pageVerticalColumnSpacing = 3;
/* 435 */     this.pageHorizontalPadding = 0;
/* 436 */     this.pageVerticalPadding = 0;
/* 437 */     this.clientBackgroundColor = PDFViewCtrl.DEFAULT_BG_COLOR;
/* 438 */     this.clientBackgroundColorDark = PDFViewCtrl.DEFAULT_DARK_BG_COLOR; this.deviceDensity = (context.getResources().getDisplayMetrics()).density; Point displaySize = new Point(0, 0); Utils.getDisplaySize(context, displaySize); this.thumbnailMaxSideLength = Math.max(displaySize.x, displaySize.y) / 4; long allowedMax = Runtime.getRuntime().maxMemory() / 1048576L; this.renderedContentCacheSize = (long)(allowedMax * 0.25D); this.minimumRefZoomForMaximumZoomLimit = 0.5D * this.deviceDensity; } public static final Creator<PDFViewCtrlConfig> CREATOR = new Creator<PDFViewCtrlConfig>() { public PDFViewCtrlConfig createFromParcel(Parcel in) { return new PDFViewCtrlConfig(in); } public PDFViewCtrlConfig[] newArray(int size) { return new PDFViewCtrlConfig[size]; } }; private boolean directionalScrollLockEnabled; private double minimumRefZoomForMaximumZoomLimit; private boolean imageSmoothing; private long renderedContentCacheSize; private boolean highlightFields; private PDFViewCtrl.PageViewMode pageViewMode; private PDFViewCtrl.PageViewMode pageRefViewMode; private PDFViewCtrl.PageViewMode pagePreferredViewMode; private boolean maintainZoomEnabled; private int deviceDensityScaleFactor; private double deviceDensity; private boolean urlExtraction; private boolean thumbnailUseEmbedded; private boolean thumbnailGenerateAtRuntime; private boolean thumbnailUseDiskCache; private int thumbnailMaxSideLength; private long thumbnailMaxAbsoluteCacheSize; private double thumbnailMaxPercentageCacheSize; protected PDFViewCtrlConfig(Parcel in) { this.directionalScrollLockEnabled = true; this.imageSmoothing = true; this.highlightFields = true; this.pageViewMode = PDFViewCtrl.PageViewMode.FIT_PAGE; this.pageRefViewMode = PDFViewCtrl.PageViewMode.FIT_PAGE; this.pagePreferredViewMode = PDFViewCtrl.PageViewMode.FIT_PAGE; this.maintainZoomEnabled = true; this.deviceDensityScaleFactor = 1; this.urlExtraction = true; this.thumbnailUseEmbedded = false; this.thumbnailGenerateAtRuntime = true; this.thumbnailUseDiskCache = true; this.thumbnailMaxAbsoluteCacheSize = 52428800L; this.thumbnailMaxPercentageCacheSize = 0.1D; this.pageHorizontalColumnSpacing = 3; this.pageVerticalColumnSpacing = 3; this.pageHorizontalPadding = 0; this.pageVerticalPadding = 0; this.clientBackgroundColor = PDFViewCtrl.DEFAULT_BG_COLOR; this.clientBackgroundColorDark = PDFViewCtrl.DEFAULT_DARK_BG_COLOR; this.directionalScrollLockEnabled = (in.readByte() != 0); this.minimumRefZoomForMaximumZoomLimit = in.readDouble(); this.imageSmoothing = (in.readByte() != 0); this.renderedContentCacheSize = in.readLong(); this.highlightFields = (in.readByte() != 0); this.pageViewMode = PDFViewCtrl.PageViewMode.valueOf(in.readInt()); this.pageRefViewMode = PDFViewCtrl.PageViewMode.valueOf(in.readInt()); this.pagePreferredViewMode = PDFViewCtrl.PageViewMode.valueOf(in.readInt()); this.maintainZoomEnabled = (in.readByte() != 0); this.deviceDensityScaleFactor = in.readInt(); this.deviceDensity = in.readDouble(); this.urlExtraction = (in.readByte() != 0); this.thumbnailUseEmbedded = (in.readByte() != 0); this.thumbnailGenerateAtRuntime = (in.readByte() != 0); this.thumbnailUseDiskCache = (in.readByte() != 0); this.thumbnailMaxSideLength = in.readInt(); this.thumbnailMaxAbsoluteCacheSize = in.readLong(); this.thumbnailMaxPercentageCacheSize = in.readDouble(); this.pageHorizontalColumnSpacing = in.readInt(); this.pageVerticalColumnSpacing = in.readInt(); this.pageHorizontalPadding = in.readInt(); this.pageVerticalPadding = in.readInt(); this.clientBackgroundColor = in.readInt(); this.clientBackgroundColorDark = in.readInt(); this.quickScaleEnabled = (in.readByte() != 0); }
/*     */   private int pageHorizontalColumnSpacing;
/*     */   private int pageVerticalColumnSpacing;
/*     */   private int pageHorizontalPadding;
/*     */   private int pageVerticalPadding;
/*     */   private int clientBackgroundColor;
/*     */   private int clientBackgroundColorDark;
/*     */   private boolean quickScaleEnabled;
/*     */   public boolean isUrlExtraction() { return this.urlExtraction; }
/*     */   public PDFViewCtrlConfig setUrlExtraction(boolean urlExtraction) { this.urlExtraction = urlExtraction; return this; }
/* 448 */   public boolean isThumbnailUseEmbedded() { return this.thumbnailUseEmbedded; } public PDFViewCtrlConfig setThumbnailUseEmbedded(boolean thumbnailUseEmbedded) { this.thumbnailUseEmbedded = thumbnailUseEmbedded; return this; } public boolean isThumbnailGenerateAtRuntime() { return this.thumbnailGenerateAtRuntime; } public PDFViewCtrlConfig setThumbnailGenerateAtRuntime(boolean thumbnailGenerateAtRuntime) { this.thumbnailGenerateAtRuntime = thumbnailGenerateAtRuntime; return this; } public boolean isThumbnailUseDiskCache() { return this.thumbnailUseDiskCache; } public PDFViewCtrlConfig setThumbnailUseDiskCache(boolean thumbnailUseDiskCache) { this.thumbnailUseDiskCache = thumbnailUseDiskCache; return this; } public int getThumbnailMaxSideLength() { return this.thumbnailMaxSideLength; } public PDFViewCtrlConfig setThumbnailMaxSideLength(int thumbnailMaxSideLength) { this.thumbnailMaxSideLength = thumbnailMaxSideLength; return this; } public long getThumbnailMaxAbsoluteCacheSize() { return this.thumbnailMaxAbsoluteCacheSize; } public PDFViewCtrlConfig setThumbnailMaxAbsoluteCacheSize(long thumbnailMaxAbsoluteCacheSize) { this.thumbnailMaxAbsoluteCacheSize = thumbnailMaxAbsoluteCacheSize; return this; } public static PDFViewCtrlConfig getDefaultConfig(Context context) { return new PDFViewCtrlConfig(context); } public double getThumbnailMaxPercentageCacheSize() { return this.thumbnailMaxPercentageCacheSize; } public PDFViewCtrlConfig setThumbnailMaxPercentageCacheSize(double thumbnailMaxPercentageCacheSize) { this.thumbnailMaxPercentageCacheSize = thumbnailMaxPercentageCacheSize; return this; } public int getPageHorizontalColumnSpacing() { return this.pageHorizontalColumnSpacing; } public PDFViewCtrlConfig setPageHorizontalColumnSpacing(int pageHorizontalColumnSpacing) { this.pageHorizontalColumnSpacing = pageHorizontalColumnSpacing; return this; } public int getPageVerticalColumnSpacing() { return this.pageVerticalColumnSpacing; } public PDFViewCtrlConfig setPageVerticalColumnSpacing(int pageVerticalColumnSpacing) { this.pageVerticalColumnSpacing = pageVerticalColumnSpacing; return this; } public int getPageHorizontalPadding() { return this.pageHorizontalPadding; } public PDFViewCtrlConfig setPageHorizontalPadding(int pageHorizontalPadding) { this.pageHorizontalPadding = pageHorizontalPadding; return this; } public int getPageVerticalPadding() { return this.pageVerticalPadding; } public PDFViewCtrlConfig setPageVerticalPadding(int pageVerticalPadding) { this.pageVerticalPadding = pageVerticalPadding; return this; } public double getDeviceDensity() { return this.deviceDensity; } public PDFViewCtrlConfig setDeviceDensity(double deviceDensity) { this.deviceDensity = deviceDensity; return this; } public int getDeviceDensityScaleFactor() { return this.deviceDensityScaleFactor; } public PDFViewCtrlConfig setDeviceDensityScaleFactor(int deviceDensityScaleFactor) { this.deviceDensityScaleFactor = deviceDensityScaleFactor; return this; } public boolean isHighlightFields() { return this.highlightFields; }
/*     */   public PDFViewCtrlConfig setHighlightFields(boolean highlightFields) { this.highlightFields = highlightFields; return this; }
/*     */   public PDFViewCtrl.PageViewMode getPageViewMode() { return this.pageViewMode; }
/*     */   public PDFViewCtrlConfig setPageViewMode(PDFViewCtrl.PageViewMode pageViewMode) { this.pageViewMode = pageViewMode; return this; }
/*     */   public PDFViewCtrl.PageViewMode getPageRefViewMode() { return this.pageRefViewMode; }
/* 453 */   public int describeContents() { return 0; } public PDFViewCtrlConfig setPageRefViewMode(PDFViewCtrl.PageViewMode pageRefViewMode) { this.pageRefViewMode = pageRefViewMode; return this; } public PDFViewCtrl.PageViewMode getPagePreferredViewMode() { return this.pagePreferredViewMode; } public PDFViewCtrlConfig setPagePreferredViewMode(PDFViewCtrl.PageViewMode pagePreferredViewMode) { this.pagePreferredViewMode = pagePreferredViewMode; return this; } public boolean isMaintainZoomEnabled() { return this.maintainZoomEnabled; } public PDFViewCtrlConfig setMaintainZoomEnabled(boolean maintainZoomEnabled) { this.maintainZoomEnabled = maintainZoomEnabled; return this; } public double getMinimumRefZoomForMaximumZoomLimit() { return this.minimumRefZoomForMaximumZoomLimit; } public PDFViewCtrlConfig setMinimumRefZoomForMaximumZoomLimit(double minimumRefZoomForMaximumZoomLimit) { this.minimumRefZoomForMaximumZoomLimit = minimumRefZoomForMaximumZoomLimit; return this; } public boolean isImageSmoothing() { return this.imageSmoothing; } public PDFViewCtrlConfig setImageSmoothing(boolean imageSmoothing) { this.imageSmoothing = imageSmoothing; return this; } public long getRenderedContentCacheSize() { return this.renderedContentCacheSize; } public PDFViewCtrlConfig setRenderedContentCacheSize(long renderedContentCacheSize) { this.renderedContentCacheSize = renderedContentCacheSize; return this; } public boolean isDirectionalScrollLockEnabled() { return this.directionalScrollLockEnabled; } public PDFViewCtrlConfig setDirectionalScrollLockEnabled(boolean directionalScrollLockEnabled) { this.directionalScrollLockEnabled = directionalScrollLockEnabled; return this; } public int getClientBackgroundColor() { return this.clientBackgroundColor; } public PDFViewCtrlConfig setClientBackgroundColor(int clientBackgroundColor) { this.clientBackgroundColor = clientBackgroundColor; return this; }
/*     */   public int getClientBackgroundColorDark() { return this.clientBackgroundColorDark; }
/*     */   public PDFViewCtrlConfig setClientBackgroundColorDark(int clientBackgroundColorDark) { this.clientBackgroundColorDark = clientBackgroundColorDark; return this; }
/*     */   public boolean isQuickScaleEnabled() { return this.quickScaleEnabled; }
/*     */   @TargetApi(19) public PDFViewCtrlConfig setQuickScaleEnabled(boolean enabled) { if (Utils.isKitKat()) { this.quickScaleEnabled = enabled; } else { this.quickScaleEnabled = false; }  return this; }
/* 458 */   public void writeToParcel(Parcel parcel, int i) { parcel.writeByte((byte)(this.directionalScrollLockEnabled ? 1 : 0));
/* 459 */     parcel.writeDouble(this.minimumRefZoomForMaximumZoomLimit);
/* 460 */     parcel.writeByte((byte)(this.imageSmoothing ? 1 : 0));
/* 461 */     parcel.writeLong(this.renderedContentCacheSize);
/* 462 */     parcel.writeByte((byte)(this.highlightFields ? 1 : 0));
/* 463 */     parcel.writeInt(this.pageViewMode.getValue());
/* 464 */     parcel.writeInt(this.pageRefViewMode.getValue());
/* 465 */     parcel.writeInt(this.pagePreferredViewMode.getValue());
/* 466 */     parcel.writeByte((byte)(this.maintainZoomEnabled ? 1 : 0));
/* 467 */     parcel.writeInt(this.deviceDensityScaleFactor);
/* 468 */     parcel.writeDouble(this.deviceDensity);
/* 469 */     parcel.writeByte((byte)(this.urlExtraction ? 1 : 0));
/* 470 */     parcel.writeByte((byte)(this.thumbnailUseEmbedded ? 1 : 0));
/* 471 */     parcel.writeByte((byte)(this.thumbnailGenerateAtRuntime ? 1 : 0));
/* 472 */     parcel.writeByte((byte)(this.thumbnailUseDiskCache ? 1 : 0));
/* 473 */     parcel.writeInt(this.thumbnailMaxSideLength);
/* 474 */     parcel.writeLong(this.thumbnailMaxAbsoluteCacheSize);
/* 475 */     parcel.writeDouble(this.thumbnailMaxPercentageCacheSize);
/* 476 */     parcel.writeInt(this.pageHorizontalColumnSpacing);
/* 477 */     parcel.writeInt(this.pageVerticalColumnSpacing);
/* 478 */     parcel.writeInt(this.pageHorizontalPadding);
/* 479 */     parcel.writeInt(this.pageVerticalPadding);
/* 480 */     parcel.writeInt(this.clientBackgroundColor);
/* 481 */     parcel.writeInt(this.clientBackgroundColorDark);
/* 482 */     parcel.writeByte((byte)(this.quickScaleEnabled ? 1 : 0)); }
/*     */ 
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\config\PDFViewCtrlConfig.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */