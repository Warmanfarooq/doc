/*     */ package com.pdftron.pdf;
/*     */ 
/*     */ import com.pdftron.common.PDFNetException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PDFNetInternalTools
/*     */ {
/*     */   public static final int e_debugger = 0;
/*     */   public static final int e_disk = 1;
/*     */   public static final int e_callback = 2;
/*     */   public static final int e_console = 3;
/*     */   public static final int e_trace = 0;
/*     */   public static final int e_debug = 1;
/*     */   public static final int e_info = 2;
/*     */   public static final int e_warning = 3;
/*     */   public static final int e_error = 4;
/*     */   public static final int e_fatal = 5;
/*     */   public static final int e_disabled = 6;
/*  36 */   private static AnalyticsHandlerCallback a = null;
/*  37 */   private static AssertHandlerCallback b = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isLogSystemAvailable() throws PDFNetException {
/*  54 */     return IsLogSystemAvailable();
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
/*     */   public static boolean configureLogFromJsonString(String paramString) throws PDFNetException {
/*  66 */     return ConfigureLogFromJsonString(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getDefaultConfigFile() throws PDFNetException {
/*  77 */     return GetDefaultConfigFile();
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
/*     */   public static String runUniversalConversionTests(String paramString) throws PDFNetException {
/*  89 */     return RunUniversalConversionTests(paramString);
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
/*     */   public static void logMessage(int paramInt1, String paramString1, String paramString2, int paramInt2) throws PDFNetException {
/* 103 */     LogMessage(paramInt1, paramString1, paramString2, paramInt2);
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
/*     */   public static void logStreamMessage(int paramInt1, String paramString1, String paramString2, String paramString3, int paramInt2) throws PDFNetException {
/* 118 */     LogStreamMessage(paramInt1, paramString1, paramString2, paramString3, paramInt2);
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
/*     */   public static boolean setLogLocation(String paramString1, String paramString2) throws PDFNetException {
/* 131 */     return SetLogLocation(paramString1, paramString2);
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
/*     */   public static boolean setLogFileName(String paramString) throws PDFNetException {
/* 143 */     return SetLogFileName(paramString);
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
/*     */   public static void setThresholdForLogStream(String paramString, int paramInt) throws PDFNetException {
/* 155 */     SetThresholdForLogStream(paramString, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setDefaultLogThreshold(int paramInt) throws PDFNetException {
/* 166 */     SetDefaultLogThreshold(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setCutoffLogThreshold(int paramInt) throws PDFNetException {
/* 177 */     SetCutoffLogThreshold(paramInt);
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
/*     */   public static boolean enableLogBackend(int paramInt) throws PDFNetException {
/* 189 */     return EnableLogBackend(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void disableLogBackend(int paramInt) throws PDFNetException {
/* 200 */     DisableLogBackend(paramInt);
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
/*     */   public static void setAnalyticsHandler(AnalyticsHandlerCallback paramAnalyticsHandlerCallback) throws PDFNetException {
/* 212 */     SetAnalyticsHandler(a = paramAnalyticsHandlerCallback);
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
/*     */   public static void setAssertHandler(AssertHandlerCallback paramAssertHandlerCallback) throws PDFNetException {
/* 224 */     SetAssertHandler(b = paramAssertHandlerCallback);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getPDFViewTileSummary() throws PDFNetException {
/* 235 */     return GetPDFViewTileSummary();
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
/*     */   public static boolean checkDocIntegrity(PDFDoc paramPDFDoc) throws PDFNetException {
/* 247 */     return CheckDocIntegrity(paramPDFDoc.__GetHandle());
/*     */   }
/*     */   
/*     */   static native boolean IsLogSystemAvailable();
/*     */   
/*     */   static native boolean ConfigureLogFromJsonString(String paramString);
/*     */   
/*     */   static native String GetDefaultConfigFile();
/*     */   
/*     */   static native String RunUniversalConversionTests(String paramString);
/*     */   
/*     */   static native void LogMessage(int paramInt1, String paramString1, String paramString2, int paramInt2);
/*     */   
/*     */   static native void LogStreamMessage(int paramInt1, String paramString1, String paramString2, String paramString3, int paramInt2);
/*     */   
/*     */   static native boolean SetLogLocation(String paramString1, String paramString2);
/*     */   
/*     */   static native boolean SetLogFileName(String paramString);
/*     */   
/*     */   static native void SetThresholdForLogStream(String paramString, int paramInt);
/*     */   
/*     */   static native void SetDefaultLogThreshold(int paramInt);
/*     */   
/*     */   static native void SetCutoffLogThreshold(int paramInt);
/*     */   
/*     */   static native boolean EnableLogBackend(int paramInt);
/*     */   
/*     */   static native void DisableLogBackend(int paramInt);
/*     */   
/*     */   static native String GetPDFViewTileSummary();
/*     */   
/*     */   static native void SetAnalyticsHandler(AnalyticsHandlerCallback paramAnalyticsHandlerCallback);
/*     */   
/*     */   static native void SetAssertHandler(AssertHandlerCallback paramAssertHandlerCallback);
/*     */   
/*     */   static native boolean CheckDocIntegrity(long paramLong);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\PDFNetInternalTools.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */