/*     */ package com.pdftron.pdf.utils;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.content.pm.ApplicationInfo;
/*     */ import android.os.Bundle;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.appcompat.app.AppCompatDelegate;
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.PDFNet;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.config.PDFNetConfig;
/*     */ import com.pdftron.pdf.config.PDFViewCtrlConfig;
import com.ppt.R;

/*     */ import java.io.File;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AppUtils
/*     */ {
/*  23 */   private static final String TAG = AppUtils.class.getName();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void initializePDFNetApplication(Context applicationContext) throws PDFNetException {
/*  32 */     initializePDFNetApplication(applicationContext, getLicenseKey(applicationContext), PDFNetConfig.getDefaultConfig());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void initializePDFNetApplication(Context applicationContext, String licenseKey) throws PDFNetException {
/*  42 */     initializePDFNetApplication(applicationContext, licenseKey, PDFNetConfig.getDefaultConfig());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void initializePDFNetApplication(Context applicationContext, PDFNetConfig config) throws PDFNetException {
/*  53 */     initializePDFNetApplication(applicationContext, getLicenseKey(applicationContext), config);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void initializePDFNetApplication(Context applicationContext, String licenseKey, PDFNetConfig config) throws PDFNetException {
/*  64 */     if (config.getExtraResourcePaths() != null) {
/*  65 */       for (File resPath : config.getExtraResourcePaths()) {
/*  66 */         if (resPath != null && resPath.exists()) {
/*  67 */           PDFNet.addResourceSearchPath(resPath.getAbsolutePath());
/*     */         }
/*     */       } 
/*     */     }
/*  71 */     if (!PDFNet.hasBeenInitialized()) {
/*  72 */       PDFNet.initialize(applicationContext, R.raw.pdfnet, licenseKey);
/*     */     }
/*  74 */     PDFNet.enableJavaScript(config.isJavaScriptEnabled());
/*  75 */     PDFNet.setDefaultDiskCachingEnabled(config.isDiskCachingEnabled());
/*  76 */     if (config.getPersistentCachePath() != null) {
/*  77 */       PDFNet.setPersistentCachePath(config.getPersistentCachePath());
/*     */     }
/*  79 */     if (config.getTempPath() != null) {
/*  80 */       PDFNet.setTempPath(config.getTempPath());
/*     */     }
/*  82 */     PDFNet.setViewerCache(config.getViewerCacheMaxSize(), config.isViewerCacheOnDisk());
/*     */     
/*  84 */     String layoutPluginPath = config.getLayoutPluginPath(applicationContext);
/*  85 */     if (null != layoutPluginPath) {
/*  86 */       PDFNet.addResourceSearchPath(layoutPluginPath);
/*     */     }
/*  88 */     String layoutSmartPluginPath = config.getLayoutSmartPluginPath(applicationContext);
/*  89 */     if (null != layoutSmartPluginPath) {
/*  90 */       PDFNet.addResourceSearchPath(layoutSmartPluginPath);
/*     */     }
/*     */     
/*  93 */     AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
/*     */   }
/*     */   
/*     */   public static String getLicenseKey(Context applicationContext) {
/*     */     try {
/*  98 */       ApplicationInfo ai = applicationContext.getPackageManager().getApplicationInfo(applicationContext
/*  99 */           .getPackageName(), 128);
/*     */       
/* 101 */       Bundle bundle = ai.metaData;
/* 102 */       return (bundle == null) ? null : bundle.getString("pdftron_license_key");
/* 103 */     } catch (Exception ex) {
/* 104 */       Logger.INSTANCE.LogE(TAG, ex);
/*     */       
/* 106 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setupPDFViewCtrl(@NonNull PDFViewCtrl pdfViewCtrl) throws PDFNetException {
/* 115 */     setupPDFViewCtrl(pdfViewCtrl, PDFViewCtrlConfig.getDefaultConfig(pdfViewCtrl.getContext()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setupPDFViewCtrl(@NonNull PDFViewCtrl pdfViewCtrl, PDFViewCtrlConfig config) throws PDFNetException {
/* 125 */     pdfViewCtrl.setDevicePixelDensity(config.getDeviceDensity(), config
/* 126 */         .getDeviceDensityScaleFactor());
/*     */     
/* 128 */     pdfViewCtrl.setUrlExtraction(config.isUrlExtraction());
/*     */     
/* 130 */     pdfViewCtrl.setupThumbnails(config.isThumbnailUseEmbedded(), config
/* 131 */         .isThumbnailGenerateAtRuntime(), config
/* 132 */         .isThumbnailUseDiskCache(), config
/* 133 */         .getThumbnailMaxSideLength(), config
/* 134 */         .getThumbnailMaxAbsoluteCacheSize(), config
/* 135 */         .getThumbnailMaxPercentageCacheSize());
/*     */     
/* 137 */     pdfViewCtrl.setPageSpacingDP(config.getPageHorizontalColumnSpacing(), config
/* 138 */         .getPageVerticalColumnSpacing(), config
/* 139 */         .getPageHorizontalPadding(), config
/* 140 */         .getPageVerticalPadding());
/*     */     
/* 142 */     pdfViewCtrl.setHighlightFields(config.isHighlightFields());
/*     */     
/* 144 */     pdfViewCtrl.setMaintainZoomEnabled(config.isMaintainZoomEnabled());
/*     */     
/* 146 */     if (config.isMaintainZoomEnabled()) {
/* 147 */       pdfViewCtrl.setPreferredViewMode(config.getPagePreferredViewMode());
/*     */     } else {
/* 149 */       pdfViewCtrl.setPageRefViewMode(config.getPageRefViewMode());
/*     */     } 
/* 151 */     pdfViewCtrl.setPageViewMode(config.getPageViewMode());
/*     */     
/* 153 */     pdfViewCtrl.setZoomLimits(PDFViewCtrl.ZoomLimitMode.RELATIVE, 1.0D, 20.0D);
/*     */     
/* 155 */     pdfViewCtrl.setRenderedContentCacheSize(config.getRenderedContentCacheSize());
/*     */     
/* 157 */     pdfViewCtrl.setImageSmoothing(config.isImageSmoothing());
/*     */     
/* 159 */     pdfViewCtrl.setMinimumRefZoomForMaximumZoomLimit(config.getMinimumRefZoomForMaximumZoomLimit());
/*     */     
/* 161 */     pdfViewCtrl.setDirectionalLockEnabled(config.isDirectionalScrollLockEnabled());
/*     */     
/* 163 */     pdfViewCtrl.setQuickScaleEnabled(config.isQuickScaleEnabled());
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pd\\utils\AppUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */