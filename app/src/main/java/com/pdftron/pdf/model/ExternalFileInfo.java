/*     */ package com.pdftron.pdf.model;
/*     */ 
/*     */ import android.annotation.TargetApi;
/*     */ import android.content.ContentResolver;
/*     */ import android.content.Context;
/*     */ import android.database.Cursor;
/*     */ import android.net.Uri;
/*     */ import android.provider.DocumentsContract;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import java.text.DateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @TargetApi(21)
/*     */ public class ExternalFileInfo
/*     */   implements Cloneable, BaseFileInfo
/*     */ {
/*  31 */   private static final String TAG = ExternalFileInfo.class.getName();
/*     */   
/*     */   private Context mContext;
/*     */   private Uri mUri;
/*     */   private ExternalFileInfo mParent;
/*     */   private Uri mRootUri;
/*     */   private String mName;
/*  38 */   private long mDateLastModified = -1L;
/*  39 */   private long mSize = -1L;
/*     */   
/*     */   private String mType;
/*     */   
/*     */   private boolean mIsSecured;
/*     */   
/*     */   private boolean mIsPackage;
/*     */   
/*     */   private boolean mIsHidden = false;
/*     */   
/*     */   public ExternalFileInfo(Context context) {
/*  50 */     this.mContext = context;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ExternalFileInfo(Context context, ExternalFileInfo parent, Uri uri) {
/*  60 */     this.mContext = context;
/*  61 */     this.mParent = parent;
/*  62 */     if (parent != null) {
/*  63 */       this.mUri = DocumentsContract.buildDocumentUriUsingTree(parent.mUri, DocumentsContract.getDocumentId(uri));
/*  64 */       this.mRootUri = parent.mRootUri;
/*     */     } else {
/*  66 */       this.mUri = DocumentsContract.buildDocumentUriUsingTree(uri, DocumentsContract.getTreeDocumentId(uri));
/*  67 */       this.mRootUri = uri;
/*     */     } 
/*     */     
/*  70 */     initFields();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initFields() {
/*  77 */     Cursor cursor = null;
/*  78 */     ContentResolver contentResolver = Utils.getContentResolver(this.mContext);
/*  79 */     if (contentResolver == null) {
/*     */       return;
/*     */     }
/*     */     try {
/*  83 */       cursor = contentResolver.query(this.mUri, null, null, null, null);
/*  84 */       if (cursor != null && cursor.moveToFirst() && cursor.getCount() > 0 && cursor.getColumnCount() > 0) {
/*  85 */         int columnName = cursor.getColumnIndexOrThrow("_display_name");
/*  86 */         int columnDate = cursor.getColumnIndexOrThrow("last_modified");
/*  87 */         int columnSize = cursor.getColumnIndexOrThrow("_size");
/*  88 */         int columnType = cursor.getColumnIndexOrThrow("mime_type");
/*     */         
/*  90 */         this.mName = cursor.getString(columnName);
/*  91 */         this.mDateLastModified = cursor.getLong(columnDate);
/*  92 */         this.mSize = cursor.getLong(columnSize);
/*  93 */         this.mType = cursor.getString(columnType);
/*     */       } 
/*  95 */     } catch (Exception exception) {
/*     */ 
/*     */     
/*     */     } finally {
/*  99 */       if (cursor != null) {
/* 100 */         cursor.close();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Context getContext() {
/* 110 */     return this.mContext;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Uri getUri() {
/* 118 */     return this.mUri;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUri(Uri uri) {
/* 127 */     this.mUri = uri;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ExternalFileInfo getParent() {
/* 135 */     return this.mParent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Uri getRootUri() {
/* 143 */     return this.mRootUri;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRootUri(Uri rootUri) {
/* 152 */     this.mRootUri = rootUri;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public String getName() {
/* 161 */     return (this.mName == null) ? "" : this.mName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getType() {
/* 169 */     return this.mType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getSize() {
/* 177 */     return this.mSize;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIdentifier() {
/* 182 */     return getAbsolutePath() + getSize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSizeInfo() {
/* 192 */     return Utils.humanReadableByteCount(this.mSize, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getFileCount() {
/* 199 */     int[] count = new int[2];
/* 200 */     for (ExternalFileInfo file : listFiles()) {
/* 201 */       if (file.isDirectory()) {
/* 202 */         count[1] = count[1] + 1; continue;
/*     */       } 
/* 204 */       count[0] = count[0] + 1;
/*     */     } 
/*     */     
/* 207 */     return count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getModifiedDate() {
/* 215 */     return DateFormat.getInstance().format(new Date(this.mDateLastModified));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Long getRawModifiedDate() {
/* 223 */     return Long.valueOf(this.mDateLastModified);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTreePath() {
/* 231 */     return Utils.getUriTreePath(this.mUri);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDocumentPath() {
/* 239 */     return Utils.getUriDocumentPath(this.mUri);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getParentRelativePath(Uri uri, String name) {
/* 247 */     String parentRelativePath = Utils.getUriDocumentPath(uri);
/* 248 */     if (!Utils.isNullOrEmpty(parentRelativePath))
/*     */     {
/* 250 */       if (parentRelativePath.endsWith(name)) {
/* 251 */         parentRelativePath = parentRelativePath.substring(0, parentRelativePath.length() - name.length());
/*     */         
/* 253 */         if (parentRelativePath.endsWith("/")) {
/* 254 */           parentRelativePath = parentRelativePath.substring(0, parentRelativePath.length() - 1);
/*     */         }
/*     */       } 
/*     */     }
/* 258 */     return parentRelativePath;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getParentRelativePath() {
/* 266 */     String parentRelativePath = getDocumentPath();
/* 267 */     if (!Utils.isNullOrEmpty(parentRelativePath))
/*     */     {
/* 269 */       if (parentRelativePath.endsWith(this.mName)) {
/* 270 */         parentRelativePath = parentRelativePath.substring(0, parentRelativePath.length() - this.mName.length());
/*     */         
/* 272 */         if (parentRelativePath.endsWith("/")) {
/* 273 */           parentRelativePath = parentRelativePath.substring(0, parentRelativePath.length() - 1);
/*     */         }
/*     */       } 
/*     */     }
/* 277 */     return parentRelativePath;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVolume() {
/* 285 */     String volume = "";
/* 286 */     String docPath = getDocumentPath();
/* 287 */     if (!Utils.isNullOrEmpty(docPath)) {
/* 288 */       int volumeSeparatorIndex = docPath.indexOf(':');
/* 289 */       if (volumeSeparatorIndex >= 0) {
/* 290 */         volume = docPath.substring(0, volumeSeparatorIndex);
/*     */       }
/*     */     } 
/* 293 */     return volume;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<ExternalFileInfo> listFiles() {
/* 303 */     ArrayList<ExternalFileInfo> files = new ArrayList<>();
/* 304 */     Uri childrenUri = DocumentsContract.buildChildDocumentsUriUsingTree(this.mUri, 
/* 305 */         DocumentsContract.getDocumentId(this.mUri));
/* 306 */     String[] projection = { "document_id", "_display_name", "last_modified", "_size", "mime_type" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 313 */     Cursor cursor = null;
/* 314 */     ContentResolver contentResolver = Utils.getContentResolver(this.mContext);
/* 315 */     if (contentResolver == null) {
/* 316 */       return files;
/*     */     }
/*     */     try {
/* 319 */       cursor = contentResolver.query(childrenUri, projection, null, null, null);
/* 320 */       if (cursor == null || cursor.getCount() <= 0 || cursor.getColumnCount() <= 0) {
/* 321 */         return files;
/*     */       }
/* 323 */       int columnId = cursor.getColumnIndexOrThrow("document_id");
/* 324 */       int columnName = cursor.getColumnIndexOrThrow("_display_name");
/* 325 */       int columnDate = cursor.getColumnIndexOrThrow("last_modified");
/* 326 */       int columnSize = cursor.getColumnIndexOrThrow("_size");
/* 327 */       int columnType = cursor.getColumnIndexOrThrow("mime_type");
/* 328 */       while (cursor.moveToNext()) {
/* 329 */         ExternalFileInfo file = new ExternalFileInfo(this.mContext);
/* 330 */         String documentId = cursor.getString(columnId);
/* 331 */         file.mUri = DocumentsContract.buildDocumentUriUsingTree(this.mUri, documentId);
/* 332 */         file.mParent = this;
/* 333 */         file.mRootUri = this.mRootUri;
/* 334 */         file.mName = cursor.getString(columnName);
/* 335 */         file.mDateLastModified = cursor.getLong(columnDate);
/* 336 */         file.mSize = cursor.getLong(columnSize);
/* 337 */         file.mType = cursor.getString(columnType);
/* 338 */         files.add(file);
/*     */       } 
/* 340 */     } catch (Exception e) {
/* 341 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */     } finally {
/* 343 */       if (cursor != null) {
/* 344 */         cursor.close();
/*     */       }
/*     */     } 
/*     */     
/* 348 */     return files;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean exists() {
/*     */     boolean exists;
/* 356 */     if (this.mType == null)
/*     */     {
/*     */       
/* 359 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 363 */     Cursor cursor = null;
/*     */     try {
/* 365 */       if (DocumentsContract.isDocumentUri(this.mContext, this.mUri)) {
/* 366 */         ContentResolver contentResolver = Utils.getContentResolver(this.mContext);
/* 367 */         if (contentResolver == null) {
/* 368 */           return false;
/*     */         }
/* 370 */         cursor = contentResolver.query(this.mUri, new String[] { "document_id" }, null, null, null);
/*     */         
/* 372 */         exists = (cursor != null && cursor.getCount() > 0 && cursor.getColumnCount() > 0);
/*     */       } else {
/* 374 */         exists = Utils.uriHasReadPermission(this.mContext, this.mUri);
/*     */       } 
/* 376 */     } catch (Exception ignored) {
/* 377 */       exists = false;
/*     */     } finally {
/* 379 */       if (cursor != null) {
/* 380 */         cursor.close();
/*     */       }
/*     */     } 
/*     */     
/* 384 */     return exists;
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
/*     */   public ExternalFileInfo findFile(String displayName) {
/* 397 */     for (ExternalFileInfo file : listFiles()) {
/* 398 */       if (displayName.equals(file.getFileName())) {
/* 399 */         return file;
/*     */       }
/*     */     } 
/* 402 */     return null;
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
/*     */   public static Uri appendPathComponent(Uri baseUri, String component) {
/*     */     String separator;
/* 415 */     if (Uri.decode(baseUri.toString()).endsWith(":")) {
/*     */       
/* 417 */       separator = "";
/*     */     } else {
/*     */       
/* 420 */       separator = "%2F";
/*     */     } 
/* 422 */     return Uri.parse(baseUri.toString() + separator + Uri.encode(component));
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
/*     */   public ExternalFileInfo getFile(String displayName) {
/*     */     ExternalFileInfo file;
/* 436 */     Uri uri = appendPathComponent(this.mUri, displayName);
/*     */ 
/*     */     
/*     */     try {
/* 440 */       file = new ExternalFileInfo(this.mContext, this, uri);
/* 441 */       if (!file.exists()) {
/* 442 */         file = null;
/*     */       }
/* 444 */     } catch (Exception e) {
/* 445 */       file = null;
/* 446 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */     } 
/* 448 */     return file;
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
/*     */   public ExternalFileInfo buildTree(Uri uri) {
/* 462 */     String parentPath = Utils.getUriDocumentPath(this.mUri);
/* 463 */     String documentPath = Utils.getUriDocumentPath(uri);
/*     */     
/* 465 */     if (!documentPath.startsWith(parentPath))
/*     */     {
/* 467 */       return null;
/*     */     }
/*     */     
/* 470 */     String relPath = documentPath.substring(parentPath.length());
/* 471 */     if (Utils.isNullOrEmpty(relPath))
/*     */     {
/* 473 */       return this;
/*     */     }
/*     */     
/* 476 */     if (relPath.startsWith("/")) {
/* 477 */       relPath = relPath.substring(1);
/*     */     }
/* 479 */     if (relPath.endsWith("/")) {
/* 480 */       relPath = relPath.substring(0, relPath.length() - 1);
/*     */     }
/* 482 */     ExternalFileInfo parent = this;
/* 483 */     ExternalFileInfo file = null;
/* 484 */     String[] parts = relPath.split("/");
/* 485 */     for (String part : parts) {
/*     */       
/* 487 */       file = parent.getFile(part);
/* 488 */       if (file == null) {
/*     */         break;
/*     */       }
/*     */       
/* 492 */       parent = file;
/*     */     } 
/*     */     
/* 495 */     return file;
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
/*     */   @Nullable
/*     */   public ExternalFileInfo createFile(String mimeType, String displayName) {
/* 510 */     ContentResolver contentResolver = Utils.getContentResolver(this.mContext);
/* 511 */     if (contentResolver == null) {
/* 512 */       return null;
/*     */     }
/* 514 */     ExternalFileInfo newFile = null;
/*     */     try {
/* 516 */       Uri newUri = DocumentsContract.createDocument(contentResolver, this.mUri, mimeType, displayName);
/*     */       
/* 518 */       if (newUri != null) {
/* 519 */         newFile = new ExternalFileInfo(this.mContext);
/* 520 */         newFile.mUri = newUri;
/* 521 */         newFile.mParent = this;
/* 522 */         newFile.mRootUri = this.mRootUri;
/* 523 */         newFile.initFields();
/*     */       } 
/* 525 */     } catch (Exception ex) {
/* 526 */       AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*     */     } 
/*     */     
/* 529 */     return newFile;
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
/*     */   public ExternalFileInfo createDirectory(String displayName) {
/* 544 */     return createFile("vnd.android.document/directory", displayName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean delete() {
/* 553 */     ContentResolver contentResolver = Utils.getContentResolver(this.mContext);
/* 554 */     if (contentResolver == null) {
/* 555 */       return false;
/*     */     }
/*     */     try {
/* 558 */       return DocumentsContract.deleteDocument(contentResolver, this.mUri);
/* 559 */     } catch (Exception ex) {
/* 560 */       AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*     */       
/* 562 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean renameTo(String displayName) {
/* 573 */     ContentResolver contentResolver = Utils.getContentResolver(this.mContext);
/* 574 */     if (contentResolver == null) {
/* 575 */       return false;
/*     */     }
/*     */     try {
/* 578 */       Uri newUri = DocumentsContract.renameDocument(contentResolver, this.mUri, displayName);
/* 579 */       if (newUri != null) {
/* 580 */         this.mUri = newUri;
/* 581 */         this.mName = displayName;
/* 582 */         return true;
/*     */       } 
/* 584 */       return false;
/*     */     }
/* 586 */     catch (Exception ex) {
/* 587 */       AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*     */       
/* 589 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ExternalFileInfo clone() {
/*     */     ExternalFileInfo clone;
/*     */     try {
/* 600 */       clone = (ExternalFileInfo)super.clone();
/* 601 */     } catch (CloneNotSupportedException e) {
/* 602 */       return null;
/*     */     } 
/* 604 */     return clone;
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
/*     */   public static String getUriMimeType(@Nullable Context context, String encodedUri) {
/* 617 */     if (context == null) {
/* 618 */       return null;
/*     */     }
/*     */     
/* 621 */     String mimeType = null;
/* 622 */     Uri uri = Uri.parse(encodedUri);
/* 623 */     Cursor cursor = null;
/* 624 */     ContentResolver contentResolver = Utils.getContentResolver(context);
/* 625 */     if (contentResolver == null) {
/* 626 */       return null;
/*     */     }
/*     */     try {
/* 629 */       cursor = contentResolver.query(uri, null, null, null, null);
/* 630 */       if (cursor != null && cursor.moveToFirst() && cursor.getCount() > 0 && cursor.getColumnCount() > 0) {
/* 631 */         int columnType = cursor.getColumnIndexOrThrow("mime_type");
/*     */         
/* 633 */         mimeType = cursor.getString(columnType);
/*     */       } 
/* 635 */     } catch (Exception e) {
/* 636 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/* 637 */       return null;
/*     */     } finally {
/* 639 */       if (cursor != null) {
/* 640 */         cursor.close();
/*     */       }
/*     */     } 
/* 643 */     return mimeType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFileType() {
/* 651 */     if (!isDirectory()) {
/* 652 */       return 6;
/*     */     }
/* 654 */     return 9;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSecured() {
/* 663 */     return this.mIsSecured;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPackage() {
/* 671 */     return this.mIsPackage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIsSecured(boolean value) {
/* 679 */     this.mIsSecured = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIsPackage(boolean value) {
/* 687 */     this.mIsPackage = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAbsolutePath() {
/* 695 */     return this.mUri.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFileName() {
/* 703 */     return getName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getExtension() {
/* 710 */     return Utils.getExtension(getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDirectory() {
/* 719 */     return "vnd.android.document/directory".equals(this.mType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 727 */     if (this == o) {
/* 728 */       return true;
/*     */     }
/* 730 */     if (o == null || !(o instanceof ExternalFileInfo)) {
/* 731 */       return false;
/*     */     }
/* 733 */     ExternalFileInfo that = (ExternalFileInfo)o;
/* 734 */     return (getType().equals(that.getType()) && getAbsolutePath().equals(that.getAbsolutePath()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHidden() {
/* 743 */     return this.mIsHidden;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHidden(boolean isHidden) {
/* 752 */     this.mIsHidden = isHidden;
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\model\ExternalFileInfo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */