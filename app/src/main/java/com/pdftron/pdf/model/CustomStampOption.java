/*     */ package com.pdftron.pdf.model;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.graphics.Bitmap;
/*     */ import android.graphics.BitmapFactory;
/*     */ import android.graphics.Color;
/*     */ import androidx.annotation.ColorInt;
/*     */ import androidx.annotation.FloatRange;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.reflect.TypeToken;
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import com.pdftron.sdf.Obj;
/*     */ import com.pdftron.sdf.ObjSet;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Type;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.json.JSONArray;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CustomStampOption
/*     */ {
/*  48 */   private static final String TAG = CustomStampOption.class.getName();
/*     */   
/*     */   private static final String FILE_CUSTOM_STAMPS_INFO = "com_pdftron_pdf_model_file_custom_stamps";
/*     */   
/*     */   private static final String STAMP_OPTION_TEXT = "TEXT";
/*     */   
/*     */   private static final String STAMP_OPTION_TEXT_BELOW = "TEXT_BELOW";
/*     */   
/*     */   private static final String STAMP_OPTION_FILL_COLOR_START = "FILL_COLOR_START";
/*     */   private static final String STAMP_OPTION_FILL_COLOR_END = "FILL_COLOR_END";
/*     */   private static final String STAMP_OPTION_TEXT_COLOR = "TEXT_COLOR";
/*     */   private static final String STAMP_OPTION_BORDER_COLOR = "BORDER_COLOR";
/*     */   private static final String STAMP_OPTION_FILL_OPACITY = "FILL_OPACITY";
/*     */   private static final String STAMP_OPTION_POINTING_LEFT = "POINTING_LEFT";
/*     */   private static final String STAMP_OPTION_POINTING_RIGHT = "POINTING_RIGHT";
/*     */   private static final String VAR_TEXT = "text";
/*     */   private static final String VAR_SECOND_TEXT = "secondText";
/*     */   private static final String VAR_BG_COLOR_START = "bgColorStart";
/*     */   private static final String VAR_BG_COLOR_END = "bgColorEnd";
/*     */   private static final String VAR_TEXT_COLOR = "textColor";
/*     */   private static final String VAR_BORDER_COLOR = "borderColor";
/*     */   private static final String VAR_FILL_OPACITY = "fillOpacity";
/*     */   private static final String VAR_POINTING_LEFT = "isPointingLeft";
/*     */   private static final String VAR_POINTING_RIGHT = "isPointingRight";
/*  72 */   public String text = "";
/*     */   
/*     */   public String secondText;
/*     */   
/*     */   @ColorInt
/*     */   public int bgColorStart;
/*     */   
/*     */   @ColorInt
/*     */   public int bgColorEnd;
/*     */   
/*     */   @ColorInt
/*     */   public int textColor;
/*     */   
/*     */   @ColorInt
/*     */   public int borderColor;
/*     */   @FloatRange(from = 0.0D, to = 1.0D)
/*     */   public double fillOpacity;
/*     */   public boolean isPointingLeft;
/*     */   public boolean isPointingRight;
/*     */   
/*     */   public CustomStampOption(@NonNull String text, @Nullable String secondText, @ColorInt int bgColorStart, @ColorInt int bgColorEnd, @ColorInt int textColor, @ColorInt int borderColor, @FloatRange(from = 0.0D, to = 1.0D) double fillOpacity, boolean isPointingLeft, boolean isPointingRight) {
/*  93 */     this.text = text;
/*  94 */     this.secondText = secondText;
/*  95 */     this.bgColorStart = bgColorStart;
/*  96 */     this.bgColorEnd = bgColorEnd;
/*  97 */     this.textColor = textColor;
/*  98 */     this.borderColor = borderColor;
/*  99 */     this.fillOpacity = fillOpacity;
/* 100 */     this.isPointingLeft = isPointingLeft;
/* 101 */     this.isPointingRight = isPointingRight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CustomStampOption(@NonNull CustomStampOption another) {
/* 110 */     this.text = another.text;
/* 111 */     this.secondText = another.secondText;
/* 112 */     this.bgColorStart = another.bgColorStart;
/* 113 */     this.bgColorEnd = another.bgColorEnd;
/* 114 */     this.textColor = another.textColor;
/* 115 */     this.borderColor = another.borderColor;
/* 116 */     this.fillOpacity = another.fillOpacity;
/* 117 */     this.isPointingLeft = another.isPointingLeft;
/* 118 */     this.isPointingRight = another.isPointingRight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CustomStampOption(@NonNull Obj stampObj) throws PDFNetException {
/* 128 */     Obj found = stampObj.findObj("TEXT");
/* 129 */     if (found == null || !found.isString()) {
/*     */       
/* 131 */       String name = (new Object() {  }).getClass().getEnclosingMethod().getName();
/* 132 */       throw new PDFNetException("", 0L, TAG, name, "TEXT is mandatory in custom rubber stamp's SDF Obj");
/*     */     } 
/* 134 */     this.text = found.getAsPDFText();
/* 135 */     found = stampObj.findObj("TEXT_BELOW");
/* 136 */     if (found != null && found.isString()) {
/* 137 */       this.secondText = found.getAsPDFText();
/*     */     }
/* 139 */     found = stampObj.findObj("FILL_COLOR_START");
/* 140 */     if (found != null && found.isArray() && (found.size() == 3L || found.size() == 4L)) {
/* 141 */       this.bgColorStart = getColorFromOption(found);
/*     */     }
/* 143 */     found = stampObj.findObj("FILL_COLOR_END");
/* 144 */     if (found != null && found.isArray() && (found.size() == 3L || found.size() == 4L)) {
/* 145 */       this.bgColorEnd = getColorFromOption(found);
/*     */     }
/* 147 */     found = stampObj.findObj("TEXT_COLOR");
/* 148 */     if (found != null && found.isArray() && (found.size() == 3L || found.size() == 4L)) {
/* 149 */       this.textColor = getColorFromOption(found);
/*     */     }
/* 151 */     found = stampObj.findObj("BORDER_COLOR");
/* 152 */     if (found != null && found.isArray() && (found.size() == 3L || found.size() == 4L)) {
/* 153 */       this.borderColor = getColorFromOption(found);
/*     */     }
/* 155 */     found = stampObj.findObj("FILL_OPACITY");
/* 156 */     if (found != null && found.isNumber()) {
/* 157 */       this.fillOpacity = found.getNumber();
/*     */     }
/* 159 */     found = stampObj.findObj("POINTING_LEFT");
/* 160 */     if (found != null && found.isBool()) {
/* 161 */       this.isPointingLeft = found.getBool();
/*     */     }
/* 163 */     found = stampObj.findObj("POINTING_RIGHT");
/* 164 */     if (found != null && found.isBool()) {
/* 165 */       this.isPointingRight = found.getBool();
/*     */     }
/*     */   }
/*     */   
/*     */   protected CustomStampOption(JSONObject jsonObject) throws PDFNetException {
/* 170 */     if (!jsonObject.has("text")) {
/*     */       
/* 172 */       String name = (new Object() {  }).getClass().getEnclosingMethod().getName();
/* 173 */       throw new PDFNetException("", 0L, TAG, name, "TEXT is mandatory in custom rubber stamp's SDF Obj");
/*     */     } 
/*     */     
/*     */     try {
/* 177 */       this.text = jsonObject.getString("text");
/* 178 */       if (jsonObject.has("secondText")) {
/* 179 */         this.secondText = jsonObject.getString("secondText");
/*     */       }
/* 181 */       if (jsonObject.has("bgColorStart")) {
/* 182 */         this.bgColorStart = jsonObject.getInt("bgColorStart");
/*     */       }
/* 184 */       if (jsonObject.has("bgColorEnd")) {
/* 185 */         this.bgColorEnd = jsonObject.getInt("bgColorEnd");
/*     */       }
/* 187 */       if (jsonObject.has("textColor")) {
/* 188 */         this.textColor = jsonObject.getInt("textColor");
/*     */       }
/* 190 */       if (jsonObject.has("borderColor")) {
/* 191 */         this.borderColor = jsonObject.getInt("borderColor");
/*     */       }
/* 193 */       if (jsonObject.has("fillOpacity")) {
/* 194 */         this.fillOpacity = jsonObject.getDouble("fillOpacity");
/*     */       }
/* 196 */       if (jsonObject.has("isPointingLeft")) {
/* 197 */         this.isPointingLeft = jsonObject.getBoolean("isPointingLeft");
/*     */       }
/* 199 */       if (jsonObject.has("isPointingRight")) {
/* 200 */         this.isPointingRight = jsonObject.getBoolean("isPointingRight");
/*     */       }
/* 202 */     } catch (JSONException e) {
/* 203 */       AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasTimeStamp() {
/* 212 */     return (!Utils.isNullOrEmpty(this.secondText) && this.secondText.contains(":"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasDateStamp() {
/* 220 */     return (!Utils.isNullOrEmpty(this.secondText) && this.secondText.contains(","));
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
/*     */   public static String createSecondText(boolean hasDate, boolean hasTime) {
/* 232 */     String secondText = null;
/* 233 */     Date currentTime = Calendar.getInstance().getTime();
/* 234 */     if (hasDate && hasTime) {
/* 235 */       SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy, h:mm a", Locale.US);
/* 236 */       secondText = dateFormat.format(currentTime);
/* 237 */     } else if (hasDate) {
/* 238 */       SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy", Locale.US);
/* 239 */       secondText = dateFormat.format(currentTime);
/* 240 */     } else if (hasTime) {
/* 241 */       SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a", Locale.US);
/* 242 */       secondText = dateFormat.format(currentTime);
/*     */     } 
/* 244 */     return secondText;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateDateTime() {
/* 252 */     this.secondText = createSecondText(hasDateStamp(), hasTimeStamp());
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
/*     */   public static synchronized List<Obj> loadCustomStamps(@NonNull Context context) {
/* 264 */     List<Obj> customStamps = new ArrayList<>();
/* 265 */     List<CustomStampOption> customStampOptions = loadCustomStampOptions(context);
/* 266 */     for (int i = 0, count = customStampOptions.size(); i < count; i++) {
/*     */       try {
/* 268 */         Obj obj = convertToObj(customStampOptions.get(i));
/* 269 */         customStamps.add(obj);
/* 270 */       } catch (PDFNetException e) {
/* 271 */         AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*     */       } 
/*     */     } 
/*     */     
/* 275 */     return customStamps;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized int getCustomStampsCount(@NonNull Context context) {
/* 285 */     String customStampsInfo = getCustomStampsInfo(context);
/* 286 */     if (Utils.isNullOrEmpty(customStampsInfo)) {
/* 287 */       return 0;
/*     */     }
/*     */     
/*     */     try {
/* 291 */       JSONArray jsonArray = new JSONArray(customStampsInfo);
/* 292 */       return jsonArray.length();
/* 293 */     } catch (Exception e) {
/* 294 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */ 
/*     */       
/* 297 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static synchronized Obj getCustomStampObj(@NonNull Context context, int index) {
/* 309 */     String customStampsInfo = getCustomStampsInfo(context);
/* 310 */     if (Utils.isNullOrEmpty(customStampsInfo)) {
/* 311 */       return null;
/*     */     }
/*     */     
/*     */     try {
/* 315 */       JSONArray jsonArray = new JSONArray(customStampsInfo);
/* 316 */       JSONObject jsonObject = jsonArray.getJSONObject(index);
/* 317 */       CustomStampOption customStampOption = new CustomStampOption(jsonObject);
/* 318 */       return convertToObj(customStampOption);
/* 319 */     } catch (Exception e) {
/* 320 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */ 
/*     */       
/* 323 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized Bitmap getCustomStampBitmap(@NonNull Context context, int index) {
/* 334 */     String path = getCustomStampBitmapPath(context, index);
/* 335 */     return BitmapFactory.decodeFile(path);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized void addCustomStamp(@Nullable Context context, @NonNull CustomStampOption customStampOption, @NonNull Bitmap bitmap) {
/* 346 */     if (context == null) {
/*     */       return;
/*     */     }
/*     */     
/* 350 */     List<CustomStampOption> customStampOptions = loadCustomStampOptions(context);
/*     */ 
/*     */     
/* 353 */     FileOutputStream fos = null;
/*     */     try {
/* 355 */       String path = getCustomStampBitmapPath(context, customStampOptions.size());
/* 356 */       fos = new FileOutputStream(path);
/* 357 */       bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
/* 358 */     } catch (Exception e) {
/* 359 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */     } finally {
/* 361 */       Utils.closeQuietly(fos);
/*     */     } 
/*     */     
/* 364 */     customStampOptions.add(customStampOption);
/* 365 */     saveCustomStamps(context, customStampOptions);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized void duplicateCustomStamp(@Nullable Context context, int index) {
/* 375 */     if (context == null) {
/*     */       return;
/*     */     }
/*     */     
/* 379 */     List<CustomStampOption> customStampOptions = loadCustomStampOptions(context);
/* 380 */     int size = customStampOptions.size();
/* 381 */     if (index < 0 || index >= size) {
/*     */       return;
/*     */     }
/*     */     
/* 385 */     for (int i = size - 1; i >= index; i--) {
/* 386 */       String oldPath = getCustomStampBitmapPath(context, i);
/* 387 */       String newPath = getCustomStampBitmapPath(context, i + 1);
/* 388 */       File oldFile = new File(oldPath);
/* 389 */       File newFile = new File(newPath);
/* 390 */       if (oldFile.exists()) {
/* 391 */         if (i == index) {
/*     */           try {
/* 393 */             Utils.copy(oldFile, newFile);
/* 394 */           } catch (IOException e) {
/* 395 */             AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */           } 
/*     */         } else {
/* 398 */           oldFile.renameTo(newFile);
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 403 */     CustomStampOption customStampOption = customStampOptions.get(index);
/* 404 */     customStampOptions.add(index, customStampOption);
/* 405 */     saveCustomStamps(context, customStampOptions);
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
/*     */   public static synchronized void updateCustomStamp(@Nullable Context context, int index, @NonNull CustomStampOption customStampOption, @NonNull Bitmap bitmap) {
/* 417 */     if (context == null) {
/*     */       return;
/*     */     }
/*     */     
/* 421 */     List<CustomStampOption> customStampOptions = loadCustomStampOptions(context);
/*     */ 
/*     */     
/* 424 */     FileOutputStream fos = null;
/*     */     try {
/* 426 */       String path = getCustomStampBitmapPath(context, index);
/* 427 */       fos = new FileOutputStream(path);
/* 428 */       bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
/* 429 */     } catch (Exception e) {
/* 430 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */     } finally {
/* 432 */       Utils.closeQuietly(fos);
/*     */     } 
/*     */     
/* 435 */     customStampOptions.set(index, customStampOption);
/* 436 */     saveCustomStamps(context, customStampOptions);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized void removeCustomStamp(@Nullable Context context, int index) {
/* 447 */     if (context == null) {
/*     */       return;
/*     */     }
/*     */     
/* 451 */     List<CustomStampOption> customStampOptions = loadCustomStampOptions(context);
/* 452 */     int size = customStampOptions.size();
/* 453 */     if (index < 0 || index >= size) {
/*     */       return;
/*     */     }
/*     */     
/* 457 */     for (int i = index + 1; i < size; i++) {
/* 458 */       String oldPath = getCustomStampBitmapPath(context, i);
/* 459 */       String newPath = getCustomStampBitmapPath(context, i - 1);
/* 460 */       File oldFile = new File(oldPath);
/* 461 */       File newFile = new File(newPath);
/* 462 */       if (oldFile.exists()) {
/* 463 */         oldFile.renameTo(newFile);
/*     */       }
/*     */     } 
/*     */     
/* 467 */     customStampOptions.remove(index);
/* 468 */     saveCustomStamps(context, customStampOptions);
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
/*     */   public static synchronized void removeCustomStamps(@Nullable Context context, @NonNull List<Integer> indexes) {
/* 480 */     if (context == null || indexes.size() == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 484 */     List<CustomStampOption> customStampOptions = loadCustomStampOptions(context);
/* 485 */     int size = customStampOptions.size();
/*     */     
/* 487 */     for (int j = indexes.size() - 1; j >= 0; j--) {
/* 488 */       int index = ((Integer)indexes.get(j)).intValue();
/* 489 */       if (index >= 0 && index < size) {
/*     */ 
/*     */ 
/*     */         
/* 493 */         for (int i = index + 1; i < size; i++) {
/* 494 */           String oldPath = getCustomStampBitmapPath(context, i);
/* 495 */           String newPath = getCustomStampBitmapPath(context, i - 1);
/* 496 */           File oldFile = new File(oldPath);
/* 497 */           File newFile = new File(newPath);
/* 498 */           if (oldFile.exists()) {
/* 499 */             oldFile.renameTo(newFile);
/*     */           }
/*     */         } 
/*     */         
/* 503 */         customStampOptions.remove(index);
/*     */       } 
/*     */     } 
/* 506 */     saveCustomStamps(context, customStampOptions);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized void removeAllCustomStamps(@Nullable Context context) {
/* 516 */     if (context == null) {
/*     */       return;
/*     */     }
/*     */     
/* 520 */     List<CustomStampOption> customStampOptions = loadCustomStampOptions(context);
/* 521 */     int size = customStampOptions.size();
/*     */     
/* 523 */     for (int i = 0; i < size; i++) {
/* 524 */       String path = getCustomStampBitmapPath(context, i);
/* 525 */       File file = new File(path);
/* 526 */       if (file.exists()) {
/* 527 */         file.delete();
/*     */       }
/*     */     } 
/*     */     
/* 531 */     saveCustomStampsInfo(context, "");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized void moveCustomStamp(@Nullable Context context, int fromPosition, int toPosition) {
/* 542 */     if (context == null) {
/*     */       return;
/*     */     }
/*     */     
/* 546 */     List<CustomStampOption> customStampOptions = loadCustomStampOptions(context);
/* 547 */     int size = customStampOptions.size();
/* 548 */     if (fromPosition < 0 || fromPosition == toPosition || fromPosition >= size || toPosition < 0 || toPosition >= size) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 553 */     String path = getCustomStampBitmapPath(context, fromPosition);
/* 554 */     File file = new File(path);
/* 555 */     File tempFile = null;
/* 556 */     if (file.exists()) {
/* 557 */       tempFile = new File(getCustomStampBitmapPath(context, "temp"));
/* 558 */       file.renameTo(tempFile);
/*     */     } 
/*     */     
/* 561 */     if (fromPosition < toPosition) {
/* 562 */       for (int i = fromPosition; i < toPosition; i++) {
/* 563 */         String oldPath = getCustomStampBitmapPath(context, i + 1);
/* 564 */         String newPath = getCustomStampBitmapPath(context, i);
/* 565 */         File oldFile = new File(oldPath);
/* 566 */         File newFile = new File(newPath);
/* 567 */         if (oldFile.exists()) {
/* 568 */           oldFile.renameTo(newFile);
/* 569 */         } else if (newFile.exists()) {
/* 570 */           newFile.delete();
/*     */         } 
/*     */       } 
/*     */     } else {
/* 574 */       for (int i = fromPosition; i > toPosition; i--) {
/* 575 */         String oldPath = getCustomStampBitmapPath(context, i - 1);
/* 576 */         String newPath = getCustomStampBitmapPath(context, i);
/* 577 */         File oldFile = new File(oldPath);
/* 578 */         File newFile = new File(newPath);
/* 579 */         if (oldFile.exists()) {
/* 580 */           oldFile.renameTo(newFile);
/* 581 */         } else if (newFile.exists()) {
/* 582 */           newFile.delete();
/*     */         } 
/*     */       } 
/*     */     } 
/* 586 */     path = getCustomStampBitmapPath(context, toPosition);
/* 587 */     file = new File(path);
/* 588 */     if (tempFile != null && tempFile.exists()) {
/* 589 */       tempFile.renameTo(file);
/* 590 */     } else if (file.exists()) {
/* 591 */       file.delete();
/*     */     } 
/*     */     
/* 594 */     CustomStampOption item = customStampOptions.get(fromPosition);
/* 595 */     customStampOptions.remove(fromPosition);
/* 596 */     customStampOptions.add(toPosition, item);
/*     */     
/* 598 */     saveCustomStamps(context, customStampOptions);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Obj convertToObj(@NonNull CustomStampOption customStampOption) throws PDFNetException {
/* 609 */     customStampOption.updateDateTime();
/* 610 */     ObjSet objSet = new ObjSet();
/* 611 */     Obj stampObj = objSet.createDict();
/* 612 */     stampObj.putText("TEXT", customStampOption.text);
/* 613 */     if (!Utils.isNullOrEmpty(customStampOption.secondText)) {
/* 614 */       stampObj.putText("TEXT_BELOW", customStampOption.secondText);
/*     */     }
/* 616 */     addColorToOption(stampObj, "FILL_COLOR_START", customStampOption.bgColorStart);
/* 617 */     addColorToOption(stampObj, "FILL_COLOR_END", customStampOption.bgColorEnd);
/* 618 */     addColorToOption(stampObj, "TEXT_COLOR", customStampOption.textColor);
/* 619 */     addColorToOption(stampObj, "BORDER_COLOR", customStampOption.borderColor);
/* 620 */     stampObj.putNumber("FILL_OPACITY", customStampOption.fillOpacity);
/* 621 */     stampObj.putBool("POINTING_LEFT", customStampOption.isPointingLeft);
/* 622 */     stampObj.putBool("POINTING_RIGHT", customStampOption.isPointingRight);
/* 623 */     return stampObj;
/*     */   }
/*     */   
/*     */   @NonNull
/*     */   private static List<CustomStampOption> loadCustomStampOptions(@NonNull Context context) {
/* 628 */     List<CustomStampOption> customStampOptions = new ArrayList<>();
/* 629 */     String customStampsInfo = getCustomStampsInfo(context);
/* 630 */     if (Utils.isNullOrEmpty(customStampsInfo)) {
/* 631 */       return customStampOptions;
/*     */     }
/*     */     
/*     */     try {
/* 635 */       JSONArray jsonArray = new JSONArray(customStampsInfo);
/* 636 */       for (int i = 0, count = jsonArray.length(); i < count; i++) {
/* 637 */         JSONObject jsonObject = jsonArray.getJSONObject(i);
/* 638 */         CustomStampOption customStampOption = new CustomStampOption(jsonObject);
/* 639 */         customStampOptions.add(customStampOption);
/*     */       } 
/* 641 */     } catch (Exception e) {
/* 642 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */     } 
/*     */     
/* 645 */     return customStampOptions;
/*     */   }
/*     */   
/*     */   private static void saveCustomStamps(@NonNull Context context, @NonNull List<CustomStampOption> customStampOptions) {
/* 649 */     Gson gson = new Gson();
/*     */     
/* 651 */     Type collectionType = (new TypeToken<ArrayList<CustomStampOption>>() {  }).getType();
/* 652 */     String serializedData = gson.toJson(customStampOptions, collectionType);
/* 653 */     saveCustomStampsInfo(context, serializedData);
/*     */   }
/*     */   
/*     */   private static void addColorToOption(@NonNull Obj stampObj, @NonNull String arrName, int color) throws PDFNetException {
/* 657 */     Obj objColor = stampObj.putArray(arrName);
/* 658 */     double red = Color.red(color) / 255.0D;
/* 659 */     double green = Color.green(color) / 255.0D;
/* 660 */     double blue = Color.blue(color) / 255.0D;
/* 661 */     objColor.pushBackNumber(red);
/* 662 */     objColor.pushBackNumber(green);
/* 663 */     objColor.pushBackNumber(blue);
/*     */   }
/*     */   
/*     */   private static int getColorFromOption(@NonNull Obj stampObj) throws PDFNetException {
/* 667 */     int red = (int)(255.0D * stampObj.getAt(0).getNumber() + 0.5D);
/* 668 */     int green = (int)(255.0D * stampObj.getAt(1).getNumber() + 0.5D);
/* 669 */     int blue = (int)(255.0D * stampObj.getAt(2).getNumber() + 0.5D);
/* 670 */     return Color.rgb(red, green, blue);
/*     */   }
/*     */   
/*     */   private static String getCustomStampBitmapPath(@NonNull Context context, int index) {
/* 674 */     return getCustomStampBitmapPath(context, String.valueOf(index));
/*     */   }
/*     */   
/*     */   private static String getCustomStampBitmapPath(@NonNull Context context, String index) {
/* 678 */     return context.getFilesDir().getAbsolutePath() + File.separator + "custom_stamp_bitmap_" + index + ".png";
/*     */   }
/*     */   
/*     */   private static String getCustomStampsInfoPath(@NonNull Context context) {
/* 682 */     return context.getFilesDir().getAbsolutePath() + File.separator + "com_pdftron_pdf_model_file_custom_stamps";
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static String getCustomStampsInfo(@NonNull Context context) {
/* 687 */     FileInputStream fis = null;
/*     */     try {
/* 689 */       fis = new FileInputStream(getCustomStampsInfoPath(context));
/* 690 */       return IOUtils.toString(fis);
/* 691 */     } catch (IOException ignored) {
/* 692 */       return null;
/*     */     } finally {
/* 694 */       Utils.closeQuietly(fis);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void saveCustomStampsInfo(@NonNull Context context, String fileContent) {
/* 699 */     FileOutputStream fos = null;
/*     */     try {
/* 701 */       fos = new FileOutputStream(getCustomStampsInfoPath(context));
/* 702 */       IOUtils.write(fileContent, fos);
/* 703 */     } catch (IOException e) {
/* 704 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */     } finally {
/* 706 */       Utils.closeQuietly(fos);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\model\CustomStampOption.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */