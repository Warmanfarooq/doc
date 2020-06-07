/*     */ package com.pdftron.pdf.model;
/*     */ 
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.reflect.TypeToken;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import java.io.File;
/*     */ import java.lang.reflect.Type;
/*     */ import java.text.DateFormat;
/*     */ import java.util.Date;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FileInfo
/*     */   implements BaseFileInfo
/*     */ {
/*     */   protected static final String VAR_TYPE = "mType";
/*     */   protected static final String VAR_FILE = "mFile";
/*     */   protected static final String VAR_FILE_URI = "mFileUri";
/*     */   protected static final String VAR_FILE_NAME = "mFileName";
/*     */   protected static final String VAR_LAST_PAGE = "mLastPage";
/*     */   protected static final String VAR_PAGE_ROTATION = "mPageRotation";
/*     */   protected static final String VAR_PAGE_PRESENTATION_MODE = "mPagePresentationMode";
/*     */   protected static final String VAR_H_SCROLL_POS = "mHScrollPos";
/*     */   protected static final String VAR_V_SCROLL_POS = "mVScrollPos";
/*     */   protected static final String VAR_ZOOM = "mZoom";
/*     */   protected static final String VAR_IS_SECURED = "mIsSecured";
/*     */   protected static final String VAR_IS_PACKAGE = "mIsPackage";
/*     */   protected static final String VAR_IS_REFLOW_MODE = "mIsReflowMode";
/*     */   protected static final String VAR_REFLOW_TEXT_SIZE = "mReflowTextSize";
/*     */   protected static final String VAR_IS_RTL_MODE = "mIsRtlMode";
/*     */   protected static final String VAR_IS_HIDDEN = "mIsHidden";
/*     */   protected static final String VAR_BOOKMARK_DIALOG_CURRENT_TAB = "mBookmarkDialogCurrentTab";
/*     */   protected static final String VAR_IS_HEADER = "mIsHeader";
/*     */   protected static final String VAR_SECTION_FIRST_POS = "mSectionFirstPos";
/*     */   protected int mType;
/*     */   protected File mFile;
/*     */   protected String mFileUri;
/*     */   protected String mFileName;
/*     */   protected int mLastPage;
/*     */   protected int mPageRotation;
/*     */   protected int mPagePresentationMode;
/*     */   protected int mHScrollPos;
/*     */   protected int mVScrollPos;
/*     */   protected double mZoom;
/*     */   protected boolean mIsSecured;
/*     */   protected boolean mIsPackage;
/*     */   protected boolean mIsReflowMode;
/*     */   protected int mReflowTextSize;
/*     */   protected boolean mIsRtlMode;
/*     */   protected boolean mIsHidden;
/*     */   protected int mBookmarkDialogCurrentTab;
/*     */   protected boolean mIsHeader;
/* 195 */   protected int mSectionFirstPos = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FileInfo(FileInfo another) {
/* 201 */     this.mType = another.mType;
/* 202 */     if (another.mFile != null) {
/*     */       try {
/* 204 */         this.mFile = new File(another.mFile.getAbsolutePath());
/* 205 */       } catch (Exception e) {
/* 206 */         sendException(another.mFile.getPath(), another.getType());
/*     */       } 
/*     */     }
/* 209 */     this.mFileUri = another.mFileUri;
/* 210 */     this.mFileName = another.mFileName;
/* 211 */     this.mLastPage = another.mLastPage;
/* 212 */     this.mPageRotation = another.mPageRotation;
/* 213 */     this.mPagePresentationMode = another.mPagePresentationMode;
/* 214 */     this.mHScrollPos = another.mHScrollPos;
/* 215 */     this.mVScrollPos = another.mVScrollPos;
/* 216 */     this.mZoom = another.mZoom;
/* 217 */     this.mIsSecured = another.mIsSecured;
/* 218 */     this.mIsPackage = another.mIsPackage;
/* 219 */     this.mIsReflowMode = another.mIsReflowMode;
/* 220 */     this.mReflowTextSize = another.mReflowTextSize;
/* 221 */     this.mIsRtlMode = another.mIsRtlMode;
/* 222 */     this.mIsHidden = another.mIsHidden;
/* 223 */     this.mBookmarkDialogCurrentTab = another.mBookmarkDialogCurrentTab;
/* 224 */     this.mIsHeader = another.mIsHeader;
/* 225 */     this.mSectionFirstPos = another.mSectionFirstPos;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FileInfo(int type, File file) {
/* 232 */     this(type, file, false, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FileInfo(int type, File file, int lastPage) {
/* 239 */     this(type, file, false, lastPage);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FileInfo(int type, File file, boolean isSecured, int lastPage) {
/* 246 */     this.mType = type;
/* 247 */     this.mFile = file;
/* 248 */     this.mIsSecured = isSecured;
/* 249 */     this.mLastPage = lastPage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FileInfo(int type, String fileUri, String filename, boolean isSecured, int lastPage) {
/* 256 */     this.mType = type;
/* 257 */     this.mFileUri = fileUri;
/* 258 */     this.mFileName = filename;
/* 259 */     this.mIsSecured = isSecured;
/* 260 */     this.mLastPage = lastPage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FileInfo(JSONObject jsonObject) {
/* 267 */     if (!jsonObject.has("mType")) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/* 272 */       this.mType = jsonObject.getInt("mType");
/*     */       
/* 274 */       if (jsonObject.has("mFile")) {
/* 275 */         JSONObject fileObject = jsonObject.getJSONObject("mFile");
/*     */         
/* 277 */         Type fileType = (new TypeToken<File>() {  }).getType();
/* 278 */         Gson gson = new Gson();
/* 279 */         this.mFile = (File)gson.fromJson(fileObject.toString(), fileType);
/*     */       } 
/*     */       
/* 282 */       if (jsonObject.has("mFileUri")) {
/* 283 */         String fileUri = jsonObject.getString("mFileUri");
/* 284 */         if (!Utils.isNullOrEmpty(fileUri)) {
/* 285 */           this.mFileUri = fileUri;
/*     */         }
/*     */       } 
/*     */       
/* 289 */       if (jsonObject.has("mFileName")) {
/* 290 */         String fileName = jsonObject.getString("mFileName");
/* 291 */         if (!Utils.isNullOrEmpty(fileName)) {
/* 292 */           this.mFileName = fileName;
/*     */         }
/*     */       } 
/*     */       
/* 296 */       if (jsonObject.has("mLastPage")) {
/* 297 */         this.mLastPage = jsonObject.getInt("mLastPage");
/*     */       }
/*     */       
/* 300 */       if (jsonObject.has("mPageRotation")) {
/* 301 */         this.mPageRotation = jsonObject.getInt("mPageRotation");
/*     */       }
/*     */       
/* 304 */       if (jsonObject.has("mPagePresentationMode")) {
/* 305 */         this.mPagePresentationMode = jsonObject.getInt("mPagePresentationMode");
/*     */       }
/*     */       
/* 308 */       if (jsonObject.has("mHScrollPos")) {
/* 309 */         this.mHScrollPos = jsonObject.getInt("mHScrollPos");
/*     */       }
/*     */       
/* 312 */       if (jsonObject.has("mVScrollPos")) {
/* 313 */         this.mVScrollPos = jsonObject.getInt("mVScrollPos");
/*     */       }
/*     */       
/* 316 */       if (jsonObject.has("mZoom")) {
/* 317 */         this.mZoom = jsonObject.getDouble("mZoom");
/*     */       }
/*     */       
/* 320 */       if (jsonObject.has("mIsSecured")) {
/* 321 */         this.mIsSecured = jsonObject.getBoolean("mIsSecured");
/*     */       }
/*     */       
/* 324 */       if (jsonObject.has("mIsPackage")) {
/* 325 */         this.mIsPackage = jsonObject.getBoolean("mIsPackage");
/*     */       }
/*     */       
/* 328 */       if (jsonObject.has("mIsReflowMode")) {
/* 329 */         this.mIsReflowMode = jsonObject.getBoolean("mIsReflowMode");
/*     */       }
/*     */       
/* 332 */       if (jsonObject.has("mReflowTextSize")) {
/* 333 */         this.mReflowTextSize = jsonObject.getInt("mReflowTextSize");
/*     */       }
/*     */       
/* 336 */       if (jsonObject.has("mIsRtlMode")) {
/* 337 */         this.mIsRtlMode = jsonObject.getBoolean("mIsRtlMode");
/*     */       }
/*     */       
/* 340 */       if (jsonObject.has("mIsHidden")) {
/* 341 */         this.mIsHidden = jsonObject.getBoolean("mIsHidden");
/*     */       }
/*     */       
/* 344 */       if (jsonObject.has("mBookmarkDialogCurrentTab")) {
/* 345 */         this.mBookmarkDialogCurrentTab = jsonObject.getInt("mBookmarkDialogCurrentTab");
/*     */       }
/*     */       
/* 348 */       if (jsonObject.has("mIsHeader")) {
/* 349 */         this.mIsHeader = jsonObject.getBoolean("mIsHeader");
/*     */       }
/*     */       
/* 352 */       if (jsonObject.has("mSectionFirstPos")) {
/* 353 */         this.mSectionFirstPos = jsonObject.getInt("mSectionFirstPos");
/*     */       }
/* 355 */     } catch (Exception e) {
/* 356 */       AnalyticsHandlerAdapter.getInstance().sendException(e, "\nJson from: " + jsonObject);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getType() {
/* 366 */     return (this.mType == 9) ? 6 : this.mType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public File getFile() {
/* 373 */     return this.mFile;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public String getAbsolutePath() {
/* 381 */     if (this.mType == 6 || this.mType == 9 || this.mType == 13 || this.mType == 15)
/*     */     {
/*     */ 
/*     */       
/* 385 */       return (this.mFileUri == null) ? "" : this.mFileUri;
/*     */     }
/*     */     try {
/* 388 */       return (this.mFile == null) ? "" : this.mFile.getAbsolutePath();
/* 389 */     } catch (Exception e) {
/* 390 */       sendException(this.mFile.getPath(), this.mType);
/* 391 */       return "";
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getParentDirectoryPath() {
/* 400 */     if (this.mType == 6 || this.mType == 9) {
/* 401 */       return "";
/*     */     }
/*     */     try {
/* 404 */       return (this.mFile != null && this.mFile.getParentFile() != null) ? this.mFile
/* 405 */         .getParentFile().getAbsolutePath() : "";
/* 406 */     } catch (Exception e) {
/* 407 */       sendException(this.mFile.getPath(), this.mType);
/* 408 */       return "";
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public String getName() {
/* 418 */     if (this.mFileName == null) {
/* 419 */       this.mFileName = "";
/*     */     }
/* 421 */     if (this.mType == 6) {
/*     */       
/* 423 */       if (Utils.isNotPdf(this.mFileUri)) {
/* 424 */         String ext = Utils.getExtension(this.mFileUri); return 
/* 425 */           this.mFileName.toLowerCase().endsWith("." + ext) ? this.mFileName : (this.mFileName + "." + ext);
/*     */       } 
/* 427 */       return (this.mFileName.toLowerCase().endsWith(".pdf") || Utils.isNotPdf(this.mFileName)) ? this.mFileName : (this.mFileName + ".pdf");
/*     */     } 
/* 429 */     if (this.mType == 9 || this.mType == 13 || this.mType == 15)
/*     */     {
/*     */       
/* 432 */       return this.mFileName;
/*     */     }
/* 434 */     return (this.mFile != null) ? this.mFile.getName() : "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSizeInfo() {
/* 443 */     if (this.mFile != null)
/*     */     {
/* 445 */       return Utils.humanReadableByteCount(this.mFile.length(), false);
/*     */     }
/* 447 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public long getSize() {
/* 453 */     return (this.mFile == null) ? 0L : this.mFile.length();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIdentifier() {
/* 458 */     return getAbsolutePath();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getFileCount() {
/* 465 */     int[] count = new int[2];
/* 466 */     if (null != this.mFile) {
/* 467 */       File[] files = this.mFile.listFiles();
/* 468 */       if (null != files) {
/* 469 */         for (File file : files) {
/* 470 */           if (file.isDirectory()) {
/* 471 */             count[1] = count[1] + 1;
/*     */           } else {
/* 473 */             count[0] = count[0] + 1;
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/* 478 */     return count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBytes() {
/* 485 */     return (this.mFile != null) ? 
/* 486 */       Utils.getByteCount(this.mFile.length()) : Utils.getLocaleDigits("0");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getModifiedDate() {
/* 493 */     return (this.mFile != null) ? 
/* 494 */       DateFormat.getInstance().format(new Date(this.mFile.lastModified())) : "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Long getRawModifiedDate() {
/* 501 */     return Long.valueOf((this.mFile != null) ? this.mFile
/* 502 */         .lastModified() : 0L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIsSecured(boolean value) {
/* 511 */     this.mIsSecured = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSecured() {
/* 518 */     return this.mIsSecured;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIsPackage(boolean value) {
/* 527 */     this.mIsPackage = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPackage() {
/* 534 */     return this.mIsPackage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLastPage() {
/* 541 */     return this.mLastPage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLastPage(int lastPage) {
/* 550 */     this.mLastPage = lastPage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPageRotation(int rotation) {
/* 559 */     this.mPageRotation = rotation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPageRotation() {
/* 566 */     return this.mPageRotation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPagePresentationMode(@Nullable PDFViewCtrl.PagePresentationMode pagePresentationMode) {
/* 575 */     if (pagePresentationMode != null) {
/* 576 */       this.mPagePresentationMode = pagePresentationMode.getValue();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PDFViewCtrl.PagePresentationMode getPagePresentationMode() {
/* 584 */     return PDFViewCtrl.PagePresentationMode.valueOf(this.mPagePresentationMode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setReflowMode(boolean inReflowMode) {
/* 593 */     this.mIsReflowMode = inReflowMode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isReflowMode() {
/* 600 */     return this.mIsReflowMode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setReflowTextSize(int textSize) {
/* 609 */     this.mReflowTextSize = textSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getReflowTextSize() {
/* 616 */     return this.mReflowTextSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRtlMode() {
/* 623 */     return this.mIsRtlMode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRtlMode(boolean isRtlMode) {
/* 632 */     this.mIsRtlMode = isRtlMode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHScrollPos() {
/* 639 */     return this.mHScrollPos;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHScrollPos(int hScrollPos) {
/* 648 */     this.mHScrollPos = hScrollPos;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getVScrollPos() {
/* 655 */     return this.mVScrollPos;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVScrollPos(int vScrollPos) {
/* 664 */     this.mVScrollPos = vScrollPos;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getZoom() {
/* 671 */     return this.mZoom;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setZoom(double zoom) {
/* 680 */     this.mZoom = zoom;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBookmarkDialogCurrentTab() {
/* 687 */     return this.mBookmarkDialogCurrentTab;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBookmarkDialogCurrentTab(int index) {
/* 696 */     this.mBookmarkDialogCurrentTab = index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHeader() {
/* 703 */     return this.mIsHeader;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHeader(boolean isHeader) {
/* 712 */     this.mIsHeader = isHeader;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSectionFirstPos() {
/* 720 */     return this.mSectionFirstPos;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSectionFirstPos(int sectionFirstPos) {
/* 729 */     this.mSectionFirstPos = sectionFirstPos;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 734 */     if (this == o) {
/* 735 */       return true;
/*     */     }
/* 737 */     if (o == null || !(o instanceof FileInfo)) {
/* 738 */       return false;
/*     */     }
/* 740 */     FileInfo that = (FileInfo)o;
/*     */     try {
/* 742 */       return (getType() == that.getType() && getAbsolutePath().equalsIgnoreCase(that.getAbsolutePath()));
/* 743 */     } catch (Exception e) {
/* 744 */       sendException(that.getAbsolutePath(), that.getType());
/* 745 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 751 */     int hash = 7;
/* 752 */     hash = 31 * hash + getAbsolutePath().hashCode();
/* 753 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFileName() {
/* 761 */     return getName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getExtension() {
/* 768 */     return Utils.getExtension(getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean exists() {
/* 778 */     return (this.mType != 2 || (this.mFile != null && this.mFile.exists()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFileType() {
/* 788 */     return this.mType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDirectory() {
/* 796 */     return (this.mType == 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHidden() {
/* 804 */     return this.mIsHidden;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHidden(boolean isHidden) {
/* 814 */     this.mIsHidden = isHidden;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void sendException(String path, int type) {
/* 821 */     if (path == null) {
/* 822 */       path = "null";
/*     */     }
/* 824 */     AnalyticsHandlerAdapter.getInstance().sendException(new Exception("filepath:" + path + ", type:" + type));
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\model\FileInfo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */