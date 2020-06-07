/*     */ package com.pdftron.pdf.utils;
/*     */ 
/*     */ import android.content.ContentResolver;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import com.google.android.material.tabs.TabLayout;
/*     */ import com.pdftron.pdf.controls.BookmarksTabLayout;
/*     */ import com.pdftron.pdf.model.CustomStampOption;
/*     */ import com.pdftron.pdf.model.ExternalFileInfo;
/*     */ import com.pdftron.pdf.model.FileInfo;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AnalyticsParam
/*     */ {
/*     */   public static HashMap<String, String> viewerSaveCopyParam(int typeId) {
/*  27 */     HashMap<String, String> result = new HashMap<>();
/*  28 */     result.put("type", AnalyticsHandlerAdapter.getInstance().getViewerSaveCopy(typeId));
/*  29 */     return result;
/*     */   }
/*     */   
/*     */   public static HashMap<String, String> lowMemoryParam(@NonNull String location) {
/*  33 */     HashMap<String, String> result = new HashMap<>();
/*  34 */     result.put("location", location);
/*  35 */     result.put("device", Utils.getDeviceName());
/*  36 */     return result;
/*     */   }
/*     */   
/*     */   public static HashMap<String, String> noActionParam(boolean hasEventAction) {
/*  40 */     HashMap<String, String> result = new HashMap<>();
/*  41 */     result.put("noaction", hasEventAction ? "false" : "true");
/*  42 */     return result;
/*     */   }
/*     */   
/*     */   public static HashMap<String, String> createNewParam(int itemId, int originId) {
/*  46 */     return createNewParam(itemId, AnalyticsHandlerAdapter.getInstance().getScreen(originId));
/*     */   }
/*     */   
/*     */   private static HashMap<String, String> createNewParam(int itemId, @NonNull String origin) {
/*  50 */     HashMap<String, String> result = new HashMap<>();
/*  51 */     result.put("item", AnalyticsHandlerAdapter.getInstance().getCreateNewItem(itemId));
/*  52 */     result.put("origin", origin);
/*  53 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HashMap<String, String> openFileParam(@NonNull ExternalFileInfo externalFileInfo, @NonNull ContentResolver contentResolver, int screenId) {
/*  59 */     return openFileParam(
/*  60 */         Utils.getUriExtension(contentResolver, externalFileInfo.getUri()), 1, 
/*     */         
/*  62 */         AnalyticsHandlerAdapter.getInstance().getScreen(screenId));
/*     */   }
/*     */ 
/*     */   
/*     */   public static HashMap<String, String> openFileParam(@NonNull FileInfo fileInfo, int screenId) {
/*  67 */     return openFileParam(fileInfo
/*  68 */         .getExtension(), 1, 
/*     */         
/*  70 */         AnalyticsHandlerAdapter.getInstance().getScreen(screenId));
/*     */   }
/*     */ 
/*     */   
/*     */   public static HashMap<String, String> openFileParam(@Nullable String extension, int screenId) {
/*  75 */     return openFileParam(extension, 1, 
/*     */ 
/*     */         
/*  78 */         AnalyticsHandlerAdapter.getInstance().getScreen(screenId));
/*     */   }
/*     */ 
/*     */   
/*     */   public static HashMap<String, String> openFileParam(@Nullable String extension, @Nullable String uri) {
/*  83 */     return openFileParam(extension, 2, uri);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static HashMap<String, String> openFileParam(@Nullable String extension, int originId, @Nullable String detail) {
/*  90 */     HashMap<String, String> result = new HashMap<>();
/*  91 */     if (Utils.isNullOrEmpty(extension)) {
/*  92 */       extension = "not_known";
/*     */     }
/*  94 */     if (Utils.isNullOrEmpty(detail)) {
/*  95 */       detail = "not_known";
/*     */     }
/*  97 */     result.put("format", extension.toLowerCase());
/*  98 */     result.put("origin", AnalyticsHandlerAdapter.getInstance().getOpenFileOrigin(originId));
/*  99 */     result.put("detail", detail);
/* 100 */     return result;
/*     */   }
/*     */   
/*     */   public static HashMap<String, String> mergeParam(int screenId, int numNonPdf, int numPdf) {
/* 104 */     HashMap<String, String> result = new HashMap<>();
/* 105 */     result.put("screen", AnalyticsHandlerAdapter.getInstance().getScreen(screenId));
/* 106 */     result.put("num_non_pdf", String.valueOf(numNonPdf));
/* 107 */     result.put("num_pdf", String.valueOf(numPdf));
/* 108 */     return result;
/*     */   }
/*     */   
/*     */   public static HashMap<String, String> viewerUndoRedoParam(@Nullable String action, int locationId) {
/* 112 */     HashMap<String, String> result = new HashMap<>();
/* 113 */     if (Utils.isNullOrEmpty(action)) {
/* 114 */       action = "not_known";
/*     */     }
/* 116 */     result.put("action", action);
/* 117 */     result.put("location", AnalyticsHandlerAdapter.getInstance().getLocation(locationId));
/* 118 */     return result;
/*     */   }
/*     */   
/*     */   public static HashMap<String, String> viewerUndoRedoParam(@Nullable String action, int locationId, int undoCount, int redoCount) {
/* 122 */     HashMap<String, String> result = viewerUndoRedoParam(action, locationId);
/* 123 */     result.put("undo_count", String.valueOf(undoCount));
/* 124 */     result.put("redo_count", String.valueOf(redoCount));
/* 125 */     return result;
/*     */   }
/*     */   
/*     */   public static HashMap<String, String> annotationToolbarParam(int selectId) {
/* 129 */     HashMap<String, String> result = new HashMap<>();
/* 130 */     result.put("select", AnalyticsHandlerAdapter.getInstance().getAnnotationTool(selectId));
/* 131 */     return result;
/*     */   }
/*     */   
/*     */   public static HashMap<String, String> editToolbarParam(int selectId) {
/* 135 */     HashMap<String, String> result = new HashMap<>();
/* 136 */     result.put("select", AnalyticsHandlerAdapter.getInstance().getEditToolbarTool(selectId));
/* 137 */     return result;
/*     */   }
/*     */   
/*     */   public static HashMap<String, String> viewModeParam(int selectId) {
/* 141 */     HashMap<String, String> result = new HashMap<>();
/* 142 */     result.put("select", AnalyticsHandlerAdapter.getInstance().getViewMode(selectId));
/* 143 */     return result;
/*     */   }
/*     */   
/*     */   public static HashMap<String, String> viewModeParam(int selectId, boolean isCurrentSameMode) {
/* 147 */     HashMap<String, String> result = viewModeParam(selectId);
/* 148 */     result.put("current", String.valueOf(isCurrentSameMode));
/* 149 */     return result;
/*     */   }
/*     */   
/*     */   public static HashMap<String, String> thumbnailsViewParam(int actionId) {
/* 153 */     HashMap<String, String> result = new HashMap<>();
/* 154 */     result.put("action", AnalyticsHandlerAdapter.getInstance().getThumbnailsView(actionId));
/* 155 */     return result;
/*     */   }
/*     */   
/*     */   public static HashMap<String, String> thumbnailsViewCountParam(int actionId, int pageCount) {
/* 159 */     HashMap<String, String> result = thumbnailsViewParam(actionId);
/* 160 */     result.put("count", String.valueOf(pageCount));
/* 161 */     return result;
/*     */   }
/*     */   
/*     */   public static HashMap<String, String> navigationListsTabParam(int tabId) {
/* 165 */     HashMap<String, String> result = new HashMap<>();
/* 166 */     result.put("tab", AnalyticsHandlerAdapter.getInstance().getNavigationListsTab(tabId));
/* 167 */     return result;
/*     */   }
/*     */   
/*     */   public static HashMap<String, String> userBookmarksActionParam(int actionId) {
/* 171 */     HashMap<String, String> result = new HashMap<>();
/* 172 */     result.put("action", AnalyticsHandlerAdapter.getInstance().getUerBookmarksAction(actionId));
/* 173 */     return result;
/*     */   }
/*     */   
/*     */   public static HashMap<String, String> annotationsListActionParam(int actionId) {
/* 177 */     HashMap<String, String> result = new HashMap<>();
/* 178 */     result.put("action", AnalyticsHandlerAdapter.getInstance().getAnnotationsListAction(actionId));
/* 179 */     return result;
/*     */   }
/*     */   
/*     */   public static HashMap<String, String> viewerNavigateByParam(int itemId) {
/* 183 */     HashMap<String, String> result = new HashMap<>();
/* 184 */     result.put("by", AnalyticsHandlerAdapter.getInstance().getViewerNavigateBy(itemId));
/* 185 */     return result;
/*     */   }
/*     */   
/*     */   public static HashMap<String, String> shortcutParam(int itemId) {
/* 189 */     HashMap<String, String> result = new HashMap<>();
/* 190 */     result.put("action", AnalyticsHandlerAdapter.getInstance().getShortcut(itemId));
/* 191 */     return result;
/*     */   }
/*     */   
/*     */   public static HashMap<String, String> quickMenuOpenParam(int typeId) {
/* 195 */     HashMap<String, String> result = new HashMap<>();
/* 196 */     result.put("type", AnalyticsHandlerAdapter.getInstance().getQuickMenuType(typeId));
/* 197 */     return result;
/*     */   }
/*     */   
/*     */   public static HashMap<String, String> quickMenuDismissParam(int typeId, boolean hasAction) {
/* 201 */     HashMap<String, String> result = noActionParam(hasAction);
/* 202 */     result.putAll(quickMenuOpenParam(typeId));
/* 203 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static HashMap<String, String> quickMenuParam(int typeId, @NonNull String action) {
/* 211 */     return quickMenuParam(typeId, action, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static HashMap<String, String> quickMenuParam(int typeId, @NonNull String action, @Nullable String nextAction) {
/* 221 */     HashMap<String, String> result = quickMenuOpenParam(typeId);
/* 222 */     result.put("action", action);
/* 223 */     if (nextAction != null) {
/* 224 */       result.put("next_action", nextAction);
/*     */     }
/* 226 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static HashMap<String, String> stylePickerSelectColorParam(String colorStr, int picker, int type, int location, String annotation, int presetIndex) {
/* 236 */     HashMap<String, String> result = stylePickerBasicParam(location, annotation);
/* 237 */     result.put("color", colorStr);
/* 238 */     result.put("picker", AnalyticsHandlerAdapter.getInstance().getStylePickerLocation(picker));
/* 239 */     result.put("type", AnalyticsHandlerAdapter.getInstance().getColorPickerType(type));
/* 240 */     result.put("preset", (presetIndex > -1) ? String.valueOf(presetIndex + 1) : "none");
/* 241 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static HashMap<String, String> stylePickerSelectThicknessParam(float thickness, int location, String annotation, int presetIndex) {
/* 248 */     HashMap<String, String> result = stylePickerBasicParam(location, annotation);
/* 249 */     result.put("thickness", String.valueOf(thickness));
/* 250 */     result.put("preset", (presetIndex > -1) ? String.valueOf(presetIndex + 1) : "none");
/* 251 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static HashMap<String, String> stylePickerSelectOpacityParam(float opacity, int location, String annotation, int presetIndex) {
/* 258 */     HashMap<String, String> result = stylePickerBasicParam(location, annotation);
/* 259 */     result.put("opacity", String.valueOf(opacity * 100.0F));
/* 260 */     result.put("preset", (presetIndex > -1) ? String.valueOf(presetIndex + 1) : "none");
/* 261 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static HashMap<String, String> stylePickerSelectTextSizeParam(float textSize, int location, String annotation, int presetIndex) {
/* 268 */     HashMap<String, String> result = stylePickerBasicParam(location, annotation);
/* 269 */     result.put("textSize", String.valueOf(textSize));
/* 270 */     result.put("preset", (presetIndex > -1) ? String.valueOf(presetIndex + 1) : "none");
/* 271 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static HashMap<String, String> stylePickerSelectRulerBaseParam(float base, int location, String annotation, int presetIndex) {
/* 278 */     HashMap<String, String> result = stylePickerBasicParam(location, annotation);
/* 279 */     result.put("rulerBase", String.valueOf(base));
/* 280 */     result.put("preset", (presetIndex > -1) ? String.valueOf(presetIndex + 1) : "none");
/* 281 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static HashMap<String, String> stylePickerSelectRulerTranslateParam(float translate, int location, String annotation, int presetIndex) {
/* 288 */     HashMap<String, String> result = stylePickerBasicParam(location, annotation);
/* 289 */     result.put("rulerTranslate", String.valueOf(translate));
/* 290 */     result.put("preset", (presetIndex > -1) ? String.valueOf(presetIndex + 1) : "none");
/* 291 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static HashMap<String, String> stylePickerSelectPresetParam(int location, String annotation, int presetIndex, boolean defaultPreset) {
/* 298 */     HashMap<String, String> result = stylePickerBasicParam(location, annotation);
/* 299 */     result.put("preset", (presetIndex > -1) ? String.valueOf(presetIndex + 1) : "none");
/* 300 */     result.put("default", String.valueOf(defaultPreset));
/* 301 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HashMap<String, String> stylePickerDeselectPresetParam(int location, String annotation, int presetIndex) {
/* 307 */     HashMap<String, String> result = stylePickerBasicParam(location, annotation);
/* 308 */     result.put("preset", (presetIndex > -1) ? String.valueOf(presetIndex + 1) : "none");
/* 309 */     return result;
/*     */   }
/*     */   
/*     */   public static HashMap<String, String> stylePickerBasicParam(int location, String annotation) {
/* 313 */     HashMap<String, String> result = new HashMap<>();
/* 314 */     result.put("location", AnalyticsHandlerAdapter.getInstance().getStylePickerOpenedFromLocation(location));
/* 315 */     result.put("annotation", annotation);
/* 316 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public static HashMap<String, String> stylePickerSelectRichTextParam(boolean enabled, int location, String annotation) {
/* 321 */     HashMap<String, String> result = stylePickerBasicParam(location, annotation);
/* 322 */     result.put("option", enabled ? "rich_content_on" : "rich_content_off");
/* 323 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public static HashMap<String, String> stylePickerSelectEraserParam(boolean enabled, int location, String annotation) {
/* 328 */     HashMap<String, String> result = stylePickerBasicParam(location, annotation);
/* 329 */     result.put("option", enabled ? "erase_ink_only_on" : "erase_ink_only_off");
/* 330 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public static HashMap<String, String> stylePickerSelectPressureParam(boolean enabled, int location, String annotation) {
/* 335 */     HashMap<String, String> result = stylePickerBasicParam(location, annotation);
/* 336 */     result.put("option", enabled ? "pressure_on" : "pressure_off");
/* 337 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public static HashMap<String, String> stylePickerCloseParam(boolean hasAction, int location, String annotation) {
/* 342 */     HashMap<String, String> result = noActionParam(hasAction);
/* 343 */     result.putAll(stylePickerBasicParam(location, annotation));
/* 344 */     return result;
/*     */   }
/*     */   
/*     */   public static HashMap<String, String> colorParam(String colorStr) {
/* 348 */     HashMap<String, String> result = new HashMap<>();
/* 349 */     result.put("color", colorStr);
/* 350 */     return result;
/*     */   }
/*     */   
/*     */   public static HashMap<String, String> hugeThumbParam(int width, int height, int bufferLength, int location) {
/* 354 */     HashMap<String, String> result = new HashMap<>();
/* 355 */     result.put("width", String.valueOf(width));
/* 356 */     result.put("height", String.valueOf(height));
/* 357 */     result.put("buffer_length", String.valueOf(bufferLength));
/* 358 */     result.put("location", AnalyticsHandlerAdapter.getInstance().getLocation(location));
/* 359 */     return result;
/*     */   }
/*     */   
/*     */   public static HashMap<String, String> firstTimeParam(boolean rtl) {
/* 363 */     HashMap<String, String> result = new HashMap<>();
/* 364 */     result.put("rtl_mode", String.valueOf(rtl));
/* 365 */     return result;
/*     */   }
/*     */   
/*     */   public static HashMap<String, String> navigateListCloseParam(TabLayout.Tab tab, boolean hasAction) {
/* 369 */     HashMap<String, String> result = navigationListsTabParam(BookmarksTabLayout.getNavigationId(tab));
/* 370 */     result.putAll(noActionParam(hasAction));
/* 371 */     return result;
/*     */   }
/*     */   
/*     */   private static HashMap<String, String> actionParam(String action) {
/* 375 */     HashMap<String, String> result = new HashMap<>();
/* 376 */     result.put("action", action);
/* 377 */     return result;
/*     */   }
/*     */   
/*     */   public static HashMap<String, String> cropPageParam(int action) {
/* 381 */     return actionParam(AnalyticsHandlerAdapter.getInstance().getCropPageAction(action));
/*     */   }
/*     */   
/*     */   public static HashMap<String, String> cropPageParam(int action, int details) {
/* 385 */     HashMap<String, String> result = cropPageParam(action);
/* 386 */     result.put("details", AnalyticsHandlerAdapter.getInstance().getCropPageDetails(details));
/* 387 */     return result;
/*     */   }
/*     */   
/*     */   public static HashMap<String, String> customColorParam(int action) {
/* 391 */     return actionParam(AnalyticsHandlerAdapter.getInstance().getCustomColorModeAction(action));
/*     */   }
/*     */   
/*     */   public static HashMap<String, String> customColorParam(int action, int position, boolean isDefault, int bgColor, int textColor) {
/* 395 */     HashMap<String, String> result = customColorParam(action);
/* 396 */     result.put("position", String.valueOf(position + 1));
/* 397 */     result.put("default", String.valueOf(isDefault));
/* 398 */     result.put("colors", Utils.getColorHexString(bgColor) + " " + Utils.getColorHexString(textColor));
/* 399 */     return result;
/*     */   }
/*     */   
/*     */   public static HashMap<String, String> customColorParam(int action, int position, boolean isDefault, int bgColor, int textColor, boolean applySelection) {
/* 403 */     HashMap<String, String> result = customColorParam(action, position, isDefault, bgColor, textColor);
/* 404 */     result.put("apply_selection", String.valueOf(applySelection));
/* 405 */     return result;
/*     */   }
/*     */   
/*     */   public static HashMap<String, String> showAllToolsParam(boolean showAllTools) {
/* 409 */     HashMap<String, String> result = new HashMap<>();
/* 410 */     result.put("show_all", String.valueOf(showAllTools));
/* 411 */     return result;
/*     */   }
/*     */   
/*     */   public static HashMap<String, String> addRubberStampParam(int stampType, @NonNull String stampName) {
/* 415 */     HashMap<String, String> result = new HashMap<>();
/* 416 */     result.put("type", AnalyticsHandlerAdapter.getInstance().getRubberStampType(stampType));
/* 417 */     result.put("details", stampName);
/* 418 */     return result;
/*     */   }
/*     */   public static HashMap<String, String> addCustomStampParam(int stampType, @NonNull CustomStampOption stamp, @Nullable String colorName) {
/*     */     String shape;
/* 422 */     if (Utils.isNullOrEmpty(colorName)) {
/* 423 */       colorName = "not_known";
/*     */     }
/*     */ 
/*     */     
/* 427 */     if (stamp.isPointingLeft) {
/* 428 */       shape = "left";
/* 429 */     } else if (stamp.isPointingRight) {
/* 430 */       shape = "right";
/*     */     } else {
/* 432 */       shape = "rounded";
/*     */     } 
/*     */     
/* 435 */     HashMap<String, String> result = new HashMap<>();
/* 436 */     result.put("type", AnalyticsHandlerAdapter.getInstance().getRubberStampType(stampType));
/* 437 */     result.put("details", stamp.text);
/* 438 */     result.put("date", String.valueOf(stamp.hasDateStamp()));
/* 439 */     result.put("time", String.valueOf(stamp.hasTimeStamp()));
/* 440 */     result.put("shape", shape);
/* 441 */     result.put("color", colorName);
/*     */     
/* 443 */     return result;
/*     */   }
/*     */   
/*     */   public static HashMap<String, String> rageScrollingParam(@NonNull String action, boolean neverShowAgain) {
/* 447 */     HashMap<String, String> result = new HashMap<>();
/* 448 */     result.put("action", action);
/* 449 */     result.put("never_show_again", String.valueOf(neverShowAgain));
/* 450 */     return result;
/*     */   }
/*     */   
/*     */   public static HashMap<String, String> autoDrawParam(@NonNull String action, @NonNull String svg) {
/* 454 */     HashMap<String, String> result = new HashMap<>();
/* 455 */     result.put("action", action);
/* 456 */     result.put("svg", svg);
/* 457 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pd\\utils\AnalyticsParam.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */