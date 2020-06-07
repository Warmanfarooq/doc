/*     */ package com.pdftron.pdf.utils;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.content.SharedPreferences;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.preference.PreferenceManager;
/*     */ import com.google.gson.Gson;
/*     */ import com.pdftron.pdf.model.FileInfo;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public class FileInfoManager
/*     */ {
/*     */   private static final String DELIMITER = " ";
/*  30 */   private final Object locker = new Object();
/*     */   
/*     */   protected final String mKeyPreferenceFiles;
/*     */   
/*     */   private final int mMaxNumFiles;
/*     */   private List<Integer> mUsedRefSet;
/*  36 */   private List<Integer> mAvailableRefSet = new ArrayList<>();
/*     */   private List<FileInfo> mInternalFiles;
/*     */   private boolean mAllLoaded;
/*  39 */   private Gson mGson = new Gson();
/*     */   
/*     */   protected FileInfoManager(String keyPreferenceFiles, int maxNumFiles) {
/*  42 */     this.mKeyPreferenceFiles = keyPreferenceFiles;
/*  43 */     this.mMaxNumFiles = maxNumFiles;
/*     */   }
/*     */   
/*     */   private static SharedPreferences getDefaultSharedPreferences(@NonNull Context context) {
/*  47 */     return PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size(@NonNull Context context) {
/*  57 */     SharedPreferences prefs = getDefaultSharedPreferences(context);
/*  58 */     if (prefs == null) {
/*  59 */       return 0;
/*     */     }
/*     */     
/*  62 */     loadPreferenceRefs(prefs);
/*     */     
/*  64 */     return this.mUsedRefSet.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty(@NonNull Context context) {
/*  74 */     return (size(context) == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsFile(@NonNull Context context, @Nullable FileInfo fileInfo) {
/*  85 */     SharedPreferences prefs = getDefaultSharedPreferences(context);
/*  86 */     if (prefs == null || fileInfo == null) {
/*  87 */       return false;
/*     */     }
/*     */     
/*  90 */     createInternalFiles();
/*     */     
/*  92 */     synchronized (this.locker) {
/*  93 */       if (this.mInternalFiles.contains(fileInfo)) {
/*  94 */         return true;
/*     */       }
/*  96 */       if (!this.mAllLoaded) {
/*  97 */         load(prefs, fileInfo);
/*     */       }
/*  99 */       return this.mInternalFiles.contains(fileInfo);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveFiles(@NonNull Context context, @NonNull List<FileInfo> files) {
/* 110 */     SharedPreferences prefs = getDefaultSharedPreferences(context);
/* 111 */     if (prefs == null) {
/*     */       return;
/*     */     }
/*     */     
/* 115 */     createInternalFiles();
/*     */     
/* 117 */     synchronized (this.locker) {
/* 118 */       save(prefs, files);
/*     */     } 
/* 120 */     this.mAllLoaded = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public List<FileInfo> getFiles(@Nullable Context context) {
/* 131 */     List<FileInfo> files = new ArrayList<>();
/* 132 */     if (context == null) {
/* 133 */       return files;
/*     */     }
/* 135 */     SharedPreferences prefs = getDefaultSharedPreferences(context);
/* 136 */     if (prefs == null) {
/* 137 */       return files;
/*     */     }
/*     */     
/* 140 */     createInternalFiles();
/*     */ 
/*     */     
/* 143 */     for (int i = 0; i < size(context) && i < this.mMaxNumFiles; i++) {
/* 144 */       synchronized (this.locker) {
/* 145 */         if (this.mInternalFiles.get(i) == null) {
/* 146 */           load(prefs, i);
/*     */         }
/*     */       } 
/*     */       
/* 150 */       synchronized (this.locker) {
/* 151 */         FileInfo fileInfo = this.mInternalFiles.get(i);
/* 152 */         if (fileInfo != null) {
/* 153 */           files.add(cloneFileInfo(fileInfo));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 158 */     this.mAllLoaded = true;
/* 159 */     return files;
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
/*     */   public FileInfo getFile(@NonNull Context context, int index) {
/* 171 */     SharedPreferences prefs = getDefaultSharedPreferences(context);
/* 172 */     if (prefs == null || index < 0 || index >= this.mMaxNumFiles) {
/* 173 */       return null;
/*     */     }
/*     */     
/* 176 */     createInternalFiles();
/*     */     
/* 178 */     synchronized (this.locker) {
/* 179 */       if (this.mInternalFiles.get(index) == null) {
/* 180 */         load(prefs, index);
/*     */       }
/*     */       
/* 183 */       return cloneFileInfo(this.mInternalFiles.get(index));
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
/*     */   
/*     */   @Nullable
/*     */   public FileInfo getFile(@NonNull Context context, @Nullable FileInfo fileInfo) {
/* 197 */     SharedPreferences prefs = getDefaultSharedPreferences(context);
/* 198 */     if (prefs == null || fileInfo == null) {
/* 199 */       return null;
/*     */     }
/*     */     
/* 202 */     createInternalFiles();
/*     */     
/* 204 */     synchronized (this.locker) {
/* 205 */       if (!this.mInternalFiles.contains(fileInfo)) {
/* 206 */         if (!this.mAllLoaded) {
/* 207 */           load(prefs, fileInfo);
/*     */         }
/* 209 */         if (!this.mInternalFiles.contains(fileInfo)) {
/* 210 */           return null;
/*     */         }
/*     */       } 
/*     */       
/* 214 */       int index = this.mInternalFiles.indexOf(fileInfo);
/* 215 */       return cloneFileInfo(this.mInternalFiles.get(index));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addFile(@NonNull Context context, @Nullable FileInfo fileInfo) {
/* 226 */     SharedPreferences prefs = getDefaultSharedPreferences(context);
/* 227 */     if (prefs == null || fileInfo == null) {
/*     */       return;
/*     */     }
/*     */     
/* 231 */     createInternalFiles();
/*     */     
/* 233 */     synchronized (this.locker) {
/* 234 */       if (!this.mAllLoaded && !this.mInternalFiles.contains(fileInfo)) {
/* 235 */         load(prefs, fileInfo);
/*     */       }
/*     */     } 
/*     */     
/* 239 */     synchronized (this.locker) {
/*     */       
/* 241 */       if (this.mInternalFiles.contains(fileInfo)) {
/* 242 */         int index = this.mInternalFiles.indexOf(fileInfo);
/* 243 */         remove(prefs, index);
/*     */       } 
/* 245 */       add(prefs, 0, fileInfo);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearFiles(@NonNull Context context) {
/* 255 */     SharedPreferences prefs = getDefaultSharedPreferences(context);
/* 256 */     if (prefs == null) {
/*     */       return;
/*     */     }
/*     */     
/* 260 */     createInternalFiles();
/*     */     
/* 262 */     synchronized (this.locker) {
/* 263 */       for (int i = 0; i < this.mMaxNumFiles; i++) {
/* 264 */         this.mInternalFiles.set(i, null);
/*     */       }
/* 266 */       clearPreferenceRefs(prefs);
/*     */     } 
/* 268 */     this.mAllLoaded = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean removeFile(@Nullable Context context, @Nullable FileInfo fileToRemove) {
/* 279 */     if (context == null) {
/* 280 */       return false;
/*     */     }
/*     */     
/* 283 */     SharedPreferences prefs = getDefaultSharedPreferences(context);
/* 284 */     if (prefs == null || fileToRemove == null) {
/* 285 */       return false;
/*     */     }
/*     */     
/* 288 */     createInternalFiles();
/*     */     
/* 290 */     synchronized (this.locker) {
/* 291 */       if (!this.mInternalFiles.contains(fileToRemove) && !this.mAllLoaded) {
/* 292 */         load(prefs, fileToRemove);
/*     */       }
/*     */     } 
/*     */     
/* 296 */     synchronized (this.locker) {
/* 297 */       if (this.mInternalFiles.contains(fileToRemove)) {
/* 298 */         int index = this.mInternalFiles.indexOf(fileToRemove);
/* 299 */         remove(prefs, index);
/* 300 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 304 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public List<FileInfo> removeFiles(@Nullable Context context, @Nullable List<FileInfo> filesToRemove) {
/* 316 */     List<FileInfo> filesRemoved = new ArrayList<>();
/* 317 */     if (context == null) {
/* 318 */       return filesRemoved;
/*     */     }
/* 320 */     SharedPreferences prefs = getDefaultSharedPreferences(context);
/* 321 */     if (prefs == null || filesToRemove == null) {
/* 322 */       return filesRemoved;
/*     */     }
/*     */     
/* 325 */     createInternalFiles();
/*     */     
/* 327 */     for (FileInfo fileToRemove : filesToRemove) {
/* 328 */       synchronized (this.locker) {
/* 329 */         if (!this.mInternalFiles.contains(fileToRemove) && !this.mAllLoaded) {
/* 330 */           load(prefs, fileToRemove);
/*     */         }
/*     */       } 
/*     */       
/* 334 */       synchronized (this.locker) {
/* 335 */         if (this.mInternalFiles.contains(fileToRemove)) {
/* 336 */           int index = this.mInternalFiles.indexOf(fileToRemove);
/* 337 */           remove(prefs, index);
/* 338 */           filesRemoved.add(fileToRemove);
/*     */         } 
/*     */       } 
/*     */     } 
/* 342 */     return filesRemoved;
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
/*     */   public boolean updateFile(@NonNull Context context, @Nullable FileInfo oldFile, @Nullable FileInfo newFile) {
/* 354 */     SharedPreferences prefs = getDefaultSharedPreferences(context);
/* 355 */     if (prefs == null || oldFile == null || newFile == null) {
/* 356 */       return false;
/*     */     }
/*     */     
/* 359 */     createInternalFiles();
/*     */     
/* 361 */     synchronized (this.locker) {
/* 362 */       if (!this.mInternalFiles.contains(oldFile) && !this.mAllLoaded) {
/* 363 */         load(prefs, oldFile);
/*     */       }
/*     */     } 
/*     */     
/* 367 */     synchronized (this.locker) {
/* 368 */       if (this.mInternalFiles.contains(oldFile)) {
/* 369 */         int index = this.mInternalFiles.indexOf(oldFile);
/* 370 */         set(prefs, index, newFile);
/* 371 */         return true;
/*     */       } 
/*     */     } 
/* 374 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateFile(@NonNull Context context, @Nullable FileInfo fileInfo) {
/* 384 */     SharedPreferences prefs = getDefaultSharedPreferences(context);
/* 385 */     if (prefs == null || fileInfo == null) {
/*     */       return;
/*     */     }
/*     */     
/* 389 */     createInternalFiles();
/*     */     
/* 391 */     synchronized (this.locker) {
/* 392 */       if (!this.mInternalFiles.contains(fileInfo) && !this.mAllLoaded) {
/* 393 */         load(prefs, fileInfo);
/*     */       }
/*     */     } 
/*     */     
/* 397 */     synchronized (this.locker) {
/* 398 */       if (this.mInternalFiles.contains(fileInfo)) {
/* 399 */         int index = this.mInternalFiles.indexOf(fileInfo);
/*     */         
/* 401 */         set(prefs, index, fileInfo);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void save(@NonNull SharedPreferences prefs, @NonNull List<FileInfo> files) {
/* 407 */     this.mInternalFiles.clear();
/* 408 */     this.mInternalFiles.addAll(files);
/* 409 */     for (int i = this.mInternalFiles.size(); i < this.mMaxNumFiles; i++) {
/* 410 */       this.mInternalFiles.add(i, null);
/*     */     }
/* 412 */     this.mAllLoaded = true;
/*     */     
/* 414 */     clearPreferenceRefs(prefs);
/* 415 */     int index = 0;
/* 416 */     for (FileInfo fileInfo : files) {
/* 417 */       String serializedDoc = this.mGson.toJson(fileInfo);
/* 418 */       addPreferenceItem(prefs, serializedDoc, index++);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void load(@NonNull SharedPreferences prefs, int index) {
/* 423 */     String data = getPreferenceItem(prefs, index);
/* 424 */     FileInfo fileInfo = null;
/* 425 */     if (!Utils.isNullOrEmpty(data)) {
/*     */       try {
/* 427 */         JSONObject jsonObject = new JSONObject(data);
/* 428 */         fileInfo = getFileInfo(jsonObject);
/* 429 */       } catch (Exception e) {
/* 430 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */       } 
/*     */     }
/* 433 */     this.mInternalFiles.set(index, fileInfo);
/*     */   }
/*     */ 
/*     */   
/*     */   private void load(@NonNull SharedPreferences prefs, FileInfo fileInfo) {
/* 438 */     loadPreferenceRefs(prefs);
/*     */     
/*     */     int index, cnt;
/* 441 */     for (index = 0, cnt = this.mUsedRefSet.size(); index < cnt; index++) {
/* 442 */       if (this.mInternalFiles.get(index) == null) {
/*     */ 
/*     */         
/* 445 */         String data = getPreferenceItem(prefs, index);
/* 446 */         if (!Utils.isNullOrEmpty(data))
/*     */           try {
/* 448 */             JSONObject jsonObject = new JSONObject(data);
/* 449 */             FileInfo file = getFileInfo(jsonObject);
/* 450 */             this.mInternalFiles.set(index, file);
/* 451 */             if (fileInfo.equals(file)) {
/*     */               break;
/*     */             }
/* 454 */           } catch (Exception e) {
/* 455 */             AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */           }  
/*     */       } 
/*     */     } 
/* 459 */     if (index == cnt) {
/* 460 */       this.mAllLoaded = true;
/*     */     }
/*     */   }
/*     */   
/*     */   private void set(@NonNull SharedPreferences prefs, int index, @NonNull FileInfo fileInfo) {
/* 465 */     this.mInternalFiles.set(index, fileInfo);
/* 466 */     String serializedDoc = this.mGson.toJson(fileInfo);
/* 467 */     setPreferenceItem(prefs, serializedDoc, index);
/*     */   }
/*     */   
/*     */   private void add(@NonNull SharedPreferences prefs, int index, @NonNull FileInfo fileInfo) {
/* 471 */     this.mInternalFiles.add(index, fileInfo);
/* 472 */     this.mInternalFiles.remove(this.mMaxNumFiles);
/* 473 */     String serializedDoc = this.mGson.toJson(fileInfo);
/* 474 */     addPreferenceItem(prefs, serializedDoc, index);
/*     */   }
/*     */   
/*     */   private void remove(@NonNull SharedPreferences prefs, int index) {
/* 478 */     this.mInternalFiles.remove(index);
/* 479 */     this.mInternalFiles.add(this.mMaxNumFiles - 1, null);
/* 480 */     removePreferenceItem(prefs, index);
/*     */   }
/*     */   
/*     */   private void createInternalFiles() {
/* 484 */     synchronized (this.locker) {
/* 485 */       if (this.mInternalFiles == null) {
/* 486 */         this.mInternalFiles = new ArrayList<>(this.mMaxNumFiles);
/* 487 */         for (int i = 0; i < this.mMaxNumFiles; i++) {
/* 488 */           this.mInternalFiles.add(null);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void loadPreferenceRefs(@NonNull SharedPreferences prefs) {
/* 495 */     if (this.mUsedRefSet != null) {
/*     */       return;
/*     */     }
/*     */     
/* 499 */     this.mUsedRefSet = new ArrayList<>();
/* 500 */     for (Integer ref = Integer.valueOf(0); ref.intValue() < this.mMaxNumFiles; ref = Integer.valueOf(ref.intValue() + 1)) {
/* 501 */       this.mAvailableRefSet.add(ref);
/*     */     }
/*     */     
/* 504 */     String data = prefs.getString(this.mKeyPreferenceFiles + "_refs", "");
/* 505 */     if (!Utils.isNullOrEmpty(data)) {
/* 506 */       String[] refs = data.split(" ");
/* 507 */       for (String r : refs) {
/* 508 */         Integer integer = Integer.valueOf(Integer.parseInt(r));
/* 509 */         this.mAvailableRefSet.remove(integer);
/* 510 */         this.mUsedRefSet.add(integer);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   protected void addPreferenceItem(@NonNull SharedPreferences prefs, String data, int index) {
/*     */     Integer ref;
/* 516 */     loadPreferenceRefs(prefs);
/*     */ 
/*     */     
/* 519 */     if (this.mAvailableRefSet.isEmpty()) {
/* 520 */       ref = this.mUsedRefSet.remove(this.mUsedRefSet.size() - 1);
/*     */     } else {
/* 522 */       ref = this.mAvailableRefSet.remove(0);
/*     */     } 
/*     */     
/* 525 */     this.mUsedRefSet.add(index, ref);
/* 526 */     StringBuilder refs = new StringBuilder();
/* 527 */     String delimiter = "";
/* 528 */     for (Integer r : this.mUsedRefSet) {
/* 529 */       refs.append(delimiter).append(r);
/* 530 */       delimiter = " ";
/*     */     } 
/*     */     
/* 533 */     SharedPreferences.Editor editor = prefs.edit();
/* 534 */     editor.putString(this.mKeyPreferenceFiles + "_" + ref, data);
/* 535 */     editor.putString(this.mKeyPreferenceFiles + "_refs", refs.toString());
/* 536 */     editor.apply();
/*     */   }
/*     */   
/*     */   private void setPreferenceItem(@NonNull SharedPreferences prefs, String data, int index) {
/* 540 */     loadPreferenceRefs(prefs);
/*     */     
/* 542 */     if (index < 0 || index > this.mUsedRefSet.size()) {
/* 543 */       AnalyticsHandlerAdapter.getInstance().sendException(new Exception("out of bound index! (index: " + index + ") size: " + this.mUsedRefSet
/* 544 */             .size() + ")"));
/*     */       
/*     */       return;
/*     */     } 
/* 548 */     if (index == this.mUsedRefSet.size()) {
/* 549 */       addPreferenceItem(prefs, data, index);
/*     */       
/*     */       return;
/*     */     } 
/* 553 */     SharedPreferences.Editor editor = prefs.edit();
/* 554 */     Integer ref = this.mUsedRefSet.get(index);
/* 555 */     editor.putString(this.mKeyPreferenceFiles + "_" + ref, data);
/* 556 */     editor.apply();
/*     */   }
/*     */   
/*     */   private void removePreferenceItem(@NonNull SharedPreferences prefs, int index) {
/* 560 */     loadPreferenceRefs(prefs);
/*     */     
/* 562 */     if (index < 0 || index >= this.mUsedRefSet.size()) {
/* 563 */       AnalyticsHandlerAdapter.getInstance().sendException(new Exception("out of bound index! (index: " + index + ") size: " + this.mUsedRefSet
/* 564 */             .size() + ")"));
/*     */       
/*     */       return;
/*     */     } 
/* 568 */     Integer ref = this.mUsedRefSet.remove(index);
/* 569 */     this.mAvailableRefSet.add(ref);
/* 570 */     StringBuilder refs = new StringBuilder();
/* 571 */     String delimiter = "";
/* 572 */     for (Integer r : this.mUsedRefSet) {
/* 573 */       refs.append(delimiter).append(r);
/* 574 */       delimiter = " ";
/*     */     } 
/*     */     
/* 577 */     SharedPreferences.Editor editor = prefs.edit();
/* 578 */     editor.putString(this.mKeyPreferenceFiles + "_refs", refs.toString());
/* 579 */     editor.apply();
/*     */   }
/*     */   
/*     */   private String getPreferenceItem(@NonNull SharedPreferences prefs, int index) {
/* 583 */     loadPreferenceRefs(prefs);
/*     */     
/* 585 */     if (index >= this.mUsedRefSet.size()) {
/* 586 */       return "";
/*     */     }
/* 588 */     Integer ref = this.mUsedRefSet.get(index);
/* 589 */     if (ref == null) {
/* 590 */       return "";
/*     */     }
/* 592 */     return prefs.getString(this.mKeyPreferenceFiles + "_" + ref, "");
/*     */   }
/*     */   
/*     */   private void clearPreferenceRefs(@NonNull SharedPreferences prefs) {
/* 596 */     this.mUsedRefSet = new ArrayList<>();
/* 597 */     for (Integer ref = Integer.valueOf(0); ref.intValue() < this.mMaxNumFiles; ref = Integer.valueOf(ref.intValue() + 1)) {
/* 598 */       this.mAvailableRefSet.add(ref);
/*     */     }
/*     */     
/* 601 */     SharedPreferences.Editor editor = prefs.edit();
/* 602 */     editor.remove(this.mKeyPreferenceFiles + "_refs");
/* 603 */     editor.apply();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected FileInfo cloneFileInfo(@Nullable FileInfo fileInfo) {
/* 608 */     if (fileInfo == null) {
/* 609 */       return null;
/*     */     }
/* 611 */     return new FileInfo(fileInfo);
/*     */   }
/*     */   
/*     */   protected FileInfo getFileInfo(JSONObject jsonObject) {
/* 615 */     return new FileInfo(jsonObject);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pd\\utils\FileInfoManager.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */