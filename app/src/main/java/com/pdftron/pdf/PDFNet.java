/*      */ package com.pdftron.pdf;
/*      */ 
/*      */ import android.content.Context;
/*      */ import android.content.res.Resources;
/*      */ import android.os.Build;
/*      */ import android.os.StatFs;
/*      */ import android.util.Log;
/*      */ import com.pdftron.common.PDFNetException;
/*      */ import com.pdftron.sdf.Obj;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.File;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.FileReader;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
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
/*      */ public class PDFNet
/*      */ {
/*   30 */   private static final String a = PDFNet.class.getName();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static {
/*      */     StringBuffer stringBuffer;
/*   53 */     (stringBuffer = new StringBuffer()).append("abi: ").append(Build.CPU_ABI).append("\n");
/*   54 */     if ((new File("/proc/cpuinfo")).exists()) {
/*      */       try {
/*   56 */         BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("/proc/cpuinfo")));
/*      */         
/*      */         String str1;
/*   59 */         while ((str1 = bufferedReader.readLine()) != null) {
/*   60 */           stringBuffer.append(str1 + "\n");
/*      */         }
/*      */         
/*   63 */         bufferedReader.close();
/*      */       }
/*   65 */       catch (IOException iOException2) {
/*   66 */         IOException iOException1; (iOException1 = null).printStackTrace();
/*      */       } 
/*      */     }
/*   69 */     String str = stringBuffer.toString().toLowerCase();
/*      */     
/*      */     try {
/*   72 */       System.loadLibrary("PDFNetC");
/*   73 */     } catch (UnsatisfiedLinkError unsatisfiedLinkError) {
/*   74 */       Log.e("PDFNet", "PDFNetC loadLibrary error with cpu_info: " + str);
/*   75 */       Log.e("PDFNet", "PDFNetC loadLibrary error: " + unsatisfiedLinkError.getMessage());
/*   76 */       throw new ExceptionInInitializerError("Unexpected loadLibrary error. If this error persists please go to pdftron.com/kb_android_loadlibrary_error");
/*      */     } 
/*      */   }
/*      */   private static volatile boolean b = false;
/*      */   public static final int e_lcms = 0;
/*      */   public static final int e_icm = 1;
/*      */   public static final int e_no_cms = 2;
/*      */   public static final int e_Identity = 0;
/*      */   
/*      */   public static boolean hasBeenInitialized() {
/*   86 */     return b;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int e_Japan1 = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int e_Japan2 = 2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int e_GB1 = 3;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int e_CNS1 = 4;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int e_Korea1 = 5;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void initialize(Context paramContext, int paramInt, String paramString) throws PDFNetException {
/*  123 */     if (paramContext == null) {
/*  124 */       throw new PDFNetException("", 0L, "com.pdftron.pdf.PDFNet", "initialize()", "Context cannot be null to initialize PDFNet library.");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  131 */     if (b) {
/*  132 */       Log.e(a, "PDFNet has already been initialized! `PDFNet.initialize(...)` should only be called 1 single time!");
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/*  137 */       setTempPath(paramContext.getCacheDir().getPath());
/*  138 */       setPersistentCachePath(paramContext.getFilesDir().getPath());
/*  139 */     } catch (Exception exception2) {
/*  140 */       Exception exception1; if ((exception1 = null).getMessage() != null) {
/*  141 */         Log.e("PDFNet", exception1.getMessage());
/*      */       }
/*      */     } 
/*      */     
/*      */     try {
/*  146 */       initialize(paramString);
/*  147 */     } catch (Exception exception) {
/*      */       
/*  149 */       initialize("demo:demo@pdftron.com:73b0e0bd01e77b55b3c29607184e8750c2d5e94da67da8f1d0");
/*  150 */       Log.e(a, "PDFNet has been initialized in demo mode! A valid key is required for production mode!");
/*  151 */       if (exception.getMessage() != null) {
/*  152 */         Log.e("PDFNet", exception.getMessage());
/*      */       }
/*      */     } 
/*      */     
/*  156 */     paramInt = paramInt;
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
/* 1207 */     a(paramContext = paramContext, paramInt, false);
/*      */     InitAndroidFonts();
/*      */     try {
/*      */       setViewerCache(0, false);
/*      */       setColorManagement(2);
/*      */     } catch (Exception exception) {}
/*      */     try {
/*      */       Obj.__Create(0L, Integer.valueOf(0));
/*      */     } catch (Exception exception) {}
/*      */     b = true;
/*      */   }
/*      */   
/*      */   private static void a(Context paramContext, int paramInt, boolean paramBoolean) throws PDFNetException {
/*      */     if (paramContext == null)
/*      */       throw new PDFNetException("", 0L, "com.pdftron.pdf.PDFNet", "initializeResource()", "Context cannot be null to initialize resource file."); 
/*      */     File file;
/*      */     if (!(file = new File(paramContext.getFilesDir() + File.separator + "pdfnet.res")).exists()) {
/*      */       File file1 = paramContext.getFilesDir();
/*      */       StatFs statFs;
/*      */       long l;
/*      */       if ((l = (statFs = new StatFs(file1.getPath())).getAvailableBlocks() * statFs.getBlockSize()) < 2903023L)
/*      */         throw new PDFNetException("", 0L, "com.pdftron.pdf.PDFNet", "initializeResource()", "Not enough space available to copy resources file."); 
/*      */       Resources resources = paramContext.getResources();
/*      */       try {
/*      */         InputStream inputStream = resources.openRawResource(paramInt);
/*      */         FileOutputStream fileOutputStream = paramContext.openFileOutput("pdfnet.res", 0);
/*      */         byte[] arrayOfByte = new byte[1024];
/*      */         int i;
/*      */         while ((i = inputStream.read(arrayOfByte)) != -1)
/*      */           fileOutputStream.write(arrayOfByte, 0, i); 
/*      */         inputStream.close();
/*      */         fileOutputStream.flush();
/*      */         fileOutputStream.close();
/*      */       } catch (Resources.NotFoundException notFoundException) {
/*      */         throw new PDFNetException("", 0L, "com.pdftron.pdf.PDFNet", "initializeResource()", "Resource file ID does not exist.");
/*      */       } catch (FileNotFoundException fileNotFoundException) {
/*      */         throw new PDFNetException("", 0L, "com.pdftron.pdf.PDFNet", "initializeResource()", "Resource file not found.");
/*      */       } catch (IOException iOException) {
/*      */         throw new PDFNetException("", 0L, "com.pdftron.pdf.PDFNet", "initializeResource()", "Error writing resource file to internal storage.");
/*      */       } catch (Exception exception) {
/*      */         throw new PDFNetException("", 0L, "com.pdftron.pdf.PDFNet", "initializeResource()", "Unknown error.");
/*      */       } 
/*      */     } 
/*      */     setResourcesPath(file.getAbsolutePath());
/*      */   }
/*      */   
/*      */   static native void initialize(String paramString);
/*      */   
/*      */   public static native void terminate();
/*      */   
/*      */   public static native boolean setResourcesPath(String paramString);
/*      */   
/*      */   public static native String getResourcesPath() throws PDFNetException;
/*      */   
/*      */   public static native void addResourceSearchPath(String paramString);
/*      */   
/*      */   public static native void setColorManagement(int paramInt) throws PDFNetException;
/*      */   
/*      */   public static native void setDefaultDeviceCMYKProfile(String paramString) throws PDFNetException;
/*      */   
/*      */   public static native void setDefaultDeviceRGBProfile(String paramString) throws PDFNetException;
/*      */   
/*      */   public static native void setDefaultDiskCachingEnabled(boolean paramBoolean) throws PDFNetException;
/*      */   
/*      */   public static native void setViewerCache(int paramInt, boolean paramBoolean);
/*      */   
/*      */   public static native void enableJavaScript(boolean paramBoolean);
/*      */   
/*      */   public static native boolean isJavaScriptEnabled();
/*      */   
/*      */   public static native boolean addFontSubst(String paramString1, String paramString2) throws PDFNetException;
/*      */   
/*      */   public static native boolean addFontSubst(int paramInt, String paramString) throws PDFNetException;
/*      */   
/*      */   public static native void setTempPath(String paramString) throws PDFNetException;
/*      */   
/*      */   public static native void setPersistentCachePath(String paramString) throws PDFNetException;
/*      */   
/*      */   public static native double getVersion() throws PDFNetException;
/*      */   
/*      */   public static native boolean IsARMv7();
/*      */   
/*      */   public static native boolean IsARM();
/*      */   
/*      */   static native boolean InitAndroidFonts();
/*      */   
/*      */   public static native String getSystemFontList() throws PDFNetException;
/*      */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\PDFNet.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */