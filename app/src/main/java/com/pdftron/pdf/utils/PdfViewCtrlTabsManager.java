/*     */ package com.pdftron.pdf.utils;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.content.SharedPreferences;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.preference.PreferenceManager;
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.reflect.TypeToken;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.model.PdfViewCtrlTabInfo;
/*     */ import java.lang.reflect.Type;
/*     */ import java.sql.Timestamp;
/*     */ import java.text.ParsePosition;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.io.FilenameUtils;
/*     */ import org.json.JSONObject;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PdfViewCtrlTabsManager
/*     */ {
/*     */   public static final int MAX_NUM_TABS_PHONE = 3;
/*     */   public static final int MAX_NUM_TABS_TABLET = 5;
/*     */   private static final String KEY_PREFS_PDFVIEWCTRL_TAB_MANAGER = "prefs_pdfviewctrl_tab_manager";
/*  51 */   private final Type mCollectionType = (new TypeToken<LinkedHashMap<String, PdfViewCtrlTabInfo>>() {  }
/*  52 */     ).getType();
/*     */   
/*     */   private ArrayList<String> mDocumentList;
/*     */   private LinkedHashMap<String, PdfViewCtrlTabInfo> mInternalTabsInfo;
/*  56 */   private HashMap<String, String> mUpdatedPaths = new HashMap<>();
/*     */   
/*     */   private static class LazyHolder {
/*  59 */     private static final PdfViewCtrlTabsManager INSTANCE = new PdfViewCtrlTabsManager();
/*     */   }
/*     */   
/*     */   public static PdfViewCtrlTabsManager getInstance() {
/*  63 */     return LazyHolder.INSTANCE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static SharedPreferences getDefaultSharedPreferences(@NonNull Context context) {
/*  71 */     return PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized ArrayList<String> getDocuments(@NonNull Context context) {
/*  81 */     if (this.mDocumentList != null) {
/*  82 */       return this.mDocumentList;
/*     */     }
/*     */     
/*  85 */     loadAllPdfViewCtrlTabInfo(context);
/*  86 */     this.mDocumentList = new ArrayList<>(this.mInternalTabsInfo.keySet());
/*  87 */     return this.mDocumentList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void addDocument(@NonNull Context context, @Nullable String filepath) {
/*  97 */     if (filepath == null) {
/*     */       return;
/*     */     }
/*     */     
/* 101 */     if (this.mDocumentList == null) {
/* 102 */       loadAllPdfViewCtrlTabInfo(context);
/* 103 */       this.mDocumentList = new ArrayList<>(this.mInternalTabsInfo.keySet());
/*     */     } 
/*     */ 
/*     */     
/* 107 */     if (this.mDocumentList.contains(filepath)) {
/* 108 */       this.mDocumentList.remove(filepath);
/*     */     }
/* 110 */     this.mDocumentList.add(filepath);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void removeDocument(@NonNull Context context, @Nullable String filepath) {
/* 120 */     if (this.mDocumentList == null) {
/* 121 */       loadAllPdfViewCtrlTabInfo(context);
/* 122 */       this.mDocumentList = new ArrayList<>(this.mInternalTabsInfo.keySet());
/*     */     } 
/* 124 */     this.mDocumentList.remove(filepath);
/*     */ 
/*     */     
/* 127 */     removePdfViewCtrlTabInfo(context, filepath);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cleanup() {
/* 134 */     this.mDocumentList = null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized void updateAllPdfViewCtrlTabInfo(@NonNull Context context) {
/* 140 */     if (this.mInternalTabsInfo == null || this.mInternalTabsInfo.isEmpty()) {
/* 141 */       clearAllPdfViewCtrlTabInfo(context);
/*     */       
/*     */       return;
/*     */     } 
/* 145 */     SharedPreferences prefs = getDefaultSharedPreferences(context);
/* 146 */     if (prefs != null) {
/* 147 */       Gson gson = new Gson();
/* 148 */       String serializedInfo = gson.toJson(this.mInternalTabsInfo, this.mCollectionType);
/* 149 */       SharedPreferences.Editor editor = prefs.edit();
/* 150 */       editor.putString("prefs_pdfviewctrl_tab_manager", serializedInfo);
/* 151 */       editor.apply();
/*     */     } 
/*     */   }
/*     */   
/*     */   private synchronized void loadAllPdfViewCtrlTabInfo(@NonNull Context context) {
/* 156 */     if (this.mInternalTabsInfo != null) {
/*     */       return;
/*     */     }
/* 159 */     this.mInternalTabsInfo = new LinkedHashMap<>();
/*     */     
/*     */     try {
/* 162 */       SharedPreferences prefs = getDefaultSharedPreferences(context);
/* 163 */       if (prefs != null) {
/* 164 */         String serializedInfo = prefs.getString("prefs_pdfviewctrl_tab_manager", "");
/* 165 */         if (!Utils.isNullOrEmpty(serializedInfo)) {
/* 166 */           JSONObject jsonObject = new JSONObject(serializedInfo);
/* 167 */           Iterator<?> keys = jsonObject.keys();
/* 168 */           while (keys.hasNext()) {
/*     */             try {
/* 170 */               String key = (String)keys.next();
/* 171 */               if (jsonObject.get(key) instanceof JSONObject) {
/* 172 */                 JSONObject infoJsonObject = (JSONObject)jsonObject.get(key);
/* 173 */                 PdfViewCtrlTabInfo info = new PdfViewCtrlTabInfo(infoJsonObject);
/* 174 */                 this.mInternalTabsInfo.put(key, info);
/*     */               } 
/* 176 */             } catch (Exception e) {
/* 177 */               AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 182 */     } catch (Exception e) {
/* 183 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized PdfViewCtrlTabInfo getPdfFViewCtrlTabInfo(@NonNull Context context, @NonNull String filepath) {
/* 195 */     loadAllPdfViewCtrlTabInfo(context);
/* 196 */     return this.mInternalTabsInfo.get(filepath);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void addPdfViewCtrlTabInfo(@NonNull Context context, String filepath, PdfViewCtrlTabInfo info) {
/* 207 */     if (filepath == null || info == null) {
/*     */       return;
/*     */     }
/*     */     
/* 211 */     if (info.tabTitle == null) {
/* 212 */       AnalyticsHandlerAdapter.getInstance().sendException(new Exception("tab title is null:" + info));
/*     */       
/*     */       return;
/*     */     } 
/* 216 */     loadAllPdfViewCtrlTabInfo(context);
/* 217 */     this.mInternalTabsInfo.put(filepath, info);
/* 218 */     updateAllPdfViewCtrlTabInfo(context);
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
/*     */   public synchronized void updatePdfViewCtrlTabInfo(@NonNull Context context, String oldFilepath, String newFilepath, String newFilename) {
/* 230 */     if (Utils.isNullOrEmpty(oldFilepath) || Utils.isNullOrEmpty(newFilepath)) {
/*     */       return;
/*     */     }
/*     */     
/* 234 */     loadAllPdfViewCtrlTabInfo(context);
/* 235 */     PdfViewCtrlTabInfo info = this.mInternalTabsInfo.get(oldFilepath);
/* 236 */     if (info != null) {
/*     */       
/* 238 */       this.mInternalTabsInfo.remove(oldFilepath);
/*     */ 
/*     */       
/* 241 */       info.tabTitle = FilenameUtils.removeExtension(newFilename);
/* 242 */       this.mInternalTabsInfo.put(newFilepath, info);
/*     */       
/* 244 */       updateAllPdfViewCtrlTabInfo(context);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 249 */       for (Map.Entry<String, String> entry : this.mUpdatedPaths.entrySet()) {
/* 250 */         if (((String)entry.getValue()).equals(oldFilepath)) {
/* 251 */           this.mUpdatedPaths.put(entry.getKey(), newFilepath);
/*     */         }
/*     */       } 
/* 254 */       this.mUpdatedPaths.put(oldFilepath, newFilepath);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void clearAllPdfViewCtrlTabInfo(@NonNull Context context) {
/* 264 */     SharedPreferences prefs = getDefaultSharedPreferences(context);
/* 265 */     if (prefs != null) {
/* 266 */       SharedPreferences.Editor editor = prefs.edit();
/* 267 */       editor.remove("prefs_pdfviewctrl_tab_manager");
/* 268 */       editor.apply();
/*     */     } 
/* 270 */     if (this.mInternalTabsInfo == null) {
/* 271 */       this.mInternalTabsInfo = new LinkedHashMap<>();
/*     */     } else {
/* 273 */       this.mInternalTabsInfo.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void removePdfViewCtrlTabInfo(@Nullable Context context, String filepath) {
/* 284 */     if (context == null || filepath == null) {
/*     */       return;
/*     */     }
/*     */     
/* 288 */     loadAllPdfViewCtrlTabInfo(context);
/* 289 */     this.mInternalTabsInfo.remove(filepath);
/* 290 */     updateAllPdfViewCtrlTabInfo(context);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateViewModeForTab(@NonNull Context context, String filepath, PDFViewCtrl.PagePresentationMode mode) {
/* 301 */     if (filepath == null) {
/*     */       return;
/*     */     }
/*     */     
/* 305 */     loadAllPdfViewCtrlTabInfo(context);
/* 306 */     PdfViewCtrlTabInfo info = this.mInternalTabsInfo.get(filepath);
/* 307 */     if (info != null) {
/* 308 */       info.setPagePresentationMode(mode);
/* 309 */       addPdfViewCtrlTabInfo(context, filepath, info);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateLastViewedTabTimestamp(@NonNull Context context, String filepath) {
/* 320 */     if (filepath == null) {
/*     */       return;
/*     */     }
/*     */     
/* 324 */     loadAllPdfViewCtrlTabInfo(context);
/* 325 */     PdfViewCtrlTabInfo info = this.mInternalTabsInfo.get(filepath);
/* 326 */     if (info != null) {
/* 327 */       Date date = new Date();
/* 328 */       info.tabLastViewedTimestamp = (new Timestamp(date.getTime())).toString();
/* 329 */       addPdfViewCtrlTabInfo(context, filepath, info);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLatestViewedTabTag(@NonNull Context context) {
/* 339 */     String latestViewedTag = null;
/* 340 */     loadAllPdfViewCtrlTabInfo(context);
/* 341 */     Timestamp timestamp = null;
/* 342 */     for (Map.Entry<String, PdfViewCtrlTabInfo> entry : this.mInternalTabsInfo.entrySet()) {
/* 343 */       String key = entry.getKey();
/* 344 */       PdfViewCtrlTabInfo value = entry.getValue();
/* 345 */       if (timestamp == null) {
/* 346 */         latestViewedTag = key;
/* 347 */         timestamp = safeGetTimestamp(value.tabLastViewedTimestamp);
/*     */       } 
/* 349 */       if (timestamp != null && value.tabLastViewedTimestamp != null) {
/* 350 */         Timestamp tabLastViewedTimestamp = safeGetTimestamp(value.tabLastViewedTimestamp);
/* 351 */         if (tabLastViewedTimestamp != null && tabLastViewedTimestamp.after(timestamp)) {
/* 352 */           latestViewedTag = key;
/* 353 */           timestamp = tabLastViewedTimestamp;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 358 */     return latestViewedTag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String removeOldestViewedTab(@NonNull Context context) {
/* 367 */     String fileToRemove = null;
/* 368 */     loadAllPdfViewCtrlTabInfo(context);
/* 369 */     Date date = new Date();
/* 370 */     Timestamp timestamp = new Timestamp(date.getTime());
/* 371 */     Set<Map.Entry<String, PdfViewCtrlTabInfo>> entries = this.mInternalTabsInfo.entrySet();
/* 372 */     for (Map.Entry<String, PdfViewCtrlTabInfo> entry : entries) {
/* 373 */       String key = entry.getKey();
/* 374 */       PdfViewCtrlTabInfo value = entry.getValue();
/* 375 */       if (null != value.tabLastViewedTimestamp) {
/* 376 */         Timestamp tabLastViewedTimestamp = safeGetTimestamp(value.tabLastViewedTimestamp);
/* 377 */         if (tabLastViewedTimestamp != null && tabLastViewedTimestamp.before(timestamp)) {
/* 378 */           fileToRemove = key;
/* 379 */           timestamp = tabLastViewedTimestamp;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 384 */     if (fileToRemove != null) {
/* 385 */       getInstance().removeDocument(context, fileToRemove);
/*     */     }
/*     */     
/* 388 */     return fileToRemove;
/*     */   }
/*     */   private static Timestamp safeGetTimestamp(String timeStr) {
/*     */     Timestamp timestamp;
/* 392 */     if (timeStr == null) {
/* 393 */       return null;
/*     */     }
/*     */     
/*     */     try {
/* 397 */       timestamp = Timestamp.valueOf(timeStr);
/* 398 */     } catch (Exception e) {
/*     */       
/* 400 */       timestamp = getFallbackTimestamp(timeStr);
/*     */     } 
/* 402 */     return timestamp;
/*     */   }
/*     */   
/*     */   private static Timestamp getFallbackTimestamp(String timeStr) {
/*     */     Timestamp result;
/* 407 */     SimpleDateFormat df = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a", Locale.US);
/* 408 */     ParsePosition pp = new ParsePosition(0);
/*     */     
/*     */     try {
/* 411 */       Date tmpDate = df.parse(timeStr, pp);
/* 412 */       result = new Timestamp(tmpDate.getTime());
/* 413 */     } catch (Exception e2) {
/* 414 */       result = null;
/*     */     } 
/* 416 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getNewPath(String filepath) {
/* 428 */     if (Utils.isNullOrEmpty(filepath)) {
/* 429 */       return null;
/*     */     }
/*     */     
/* 432 */     return this.mUpdatedPaths.get(filepath);
/*     */   }
/*     */   
/*     */   private PdfViewCtrlTabsManager() {}
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pd\\utils\PdfViewCtrlTabsManager.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */