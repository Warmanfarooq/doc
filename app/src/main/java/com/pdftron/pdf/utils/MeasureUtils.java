/*     */ package com.pdftron.pdf.utils;
/*     */ 
/*     */ import android.util.Log;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import com.google.gson.Gson;
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.Annot;
/*     */ import com.pdftron.pdf.Point;
/*     */ import com.pdftron.pdf.annots.Line;
/*     */ import com.pdftron.pdf.model.MeasureInfo;
/*     */ import com.pdftron.pdf.model.RulerItem;
/*     */ import com.pdftron.pdf.tools.RulerCreate;
/*     */ import com.pdftron.sdf.Obj;
/*     */ import java.math.BigDecimal;
/*     */ import java.util.HashMap;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.json.JSONObject;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MeasureUtils
/*     */ {
/*  27 */   public static final String TAG = MeasureUtils.class.getName();
/*     */   
/*     */   public static final String U_PT = "pt";
/*     */   
/*     */   public static final String U_IN = "in";
/*     */   
/*     */   public static final String U_MM = "mm";
/*     */   
/*     */   public static final String U_CM = "cm";
/*     */   public static final String U_M = "m";
/*     */   public static final String U_KM = "km";
/*     */   public static final String U_FT = "ft";
/*     */   public static final String U_YD = "yd";
/*     */   public static final String U_MI = "mi";
/*  41 */   public static String K_Measure = "Measure";
/*  42 */   public static String K_IT = "IT";
/*     */   
/*  44 */   public static String K_LineDimension = "LineDimension";
/*  45 */   public static String K_PolyLineDimension = "PolyLineDimension";
/*  46 */   public static String K_PolygonDimension = "PolygonDimension";
/*     */   
/*  48 */   public static String K_scale = "scale";
/*  49 */   public static String K_axis = "axis";
/*  50 */   public static String K_distance = "distance";
/*  51 */   public static String K_area = "area";
/*     */ 
/*     */   
/*  54 */   public static String K_RECT_AREA = "pdftron_rect_area";
/*     */   
/*  56 */   public static int PRECISION_DEFAULT = 100;
/*     */ 
/*     */   
/*     */   public static String getDefaultMeasureInfo() {
/*     */     try {
/*  61 */       Gson gson = new Gson();
/*     */ 
/*     */       
/*  64 */       MeasureInfo axis = new MeasureInfo();
/*  65 */       axis.setFactor(0.0138889D);
/*  66 */       axis.setUnit("in");
/*  67 */       axis.setDecimalSymbol(".");
/*  68 */       axis.setThousandSymbol(",");
/*  69 */       axis.setDisplay("D");
/*  70 */       axis.setPrecision(PRECISION_DEFAULT);
/*  71 */       axis.setUnitPrefix("");
/*  72 */       axis.setUnitSuffix("");
/*  73 */       axis.setUnitPosition("S");
/*     */       
/*  75 */       String axisJson = gson.toJson(axis);
/*     */ 
/*     */       
/*  78 */       MeasureInfo distance = new MeasureInfo();
/*  79 */       distance.setFactor(1.0D);
/*  80 */       distance.setUnit("in");
/*  81 */       distance.setDecimalSymbol(".");
/*  82 */       distance.setThousandSymbol(",");
/*  83 */       distance.setDisplay("D");
/*  84 */       distance.setPrecision(PRECISION_DEFAULT);
/*  85 */       distance.setUnitPrefix("");
/*  86 */       distance.setUnitSuffix("");
/*  87 */       distance.setUnitPosition("S");
/*     */       
/*  89 */       String distanceJson = gson.toJson(distance);
/*     */ 
/*     */       
/*  92 */       MeasureInfo area = new MeasureInfo();
/*  93 */       area.setFactor(1.0D);
/*  94 */       area.setUnit("sq in");
/*  95 */       area.setDecimalSymbol(".");
/*  96 */       area.setThousandSymbol(",");
/*  97 */       area.setDisplay("D");
/*  98 */       area.setPrecision(PRECISION_DEFAULT);
/*  99 */       area.setUnitPrefix("");
/* 100 */       area.setUnitSuffix("");
/* 101 */       area.setUnitPosition("S");
/*     */       
/* 103 */       String areaJson = gson.toJson(area);
/*     */       
/* 105 */       JSONObject jsonObject = new JSONObject();
/* 106 */       jsonObject.put(K_scale, "1 in = 1 in");
/* 107 */       jsonObject.put(K_axis, new JSONObject(axisJson));
/* 108 */       jsonObject.put(K_distance, new JSONObject(distanceJson));
/* 109 */       jsonObject.put(K_area, new JSONObject(areaJson));
/*     */       
/* 111 */       String measureInfo = jsonObject.toString();
/* 112 */       Log.d(TAG, "getDefaultMeasureInfo: " + measureInfo);
/*     */       
/* 114 */       return jsonObject.toString();
/* 115 */     } catch (Exception ex) {
/* 116 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String getAnnotMeasureInfo(Annot annot) throws Exception {
/* 121 */     if (annot == null || !annot.isValid()) {
/* 122 */       return null;
/*     */     }
/* 124 */     int annotType = AnnotUtils.getAnnotType(annot);
/* 125 */     if (annotType != 1006 && annotType != 1008 && annotType != 1009 && annotType != 1012)
/*     */     {
/*     */ 
/*     */       
/* 129 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 133 */     String defaultMeasureInfo = getDefaultMeasureInfo();
/* 134 */     JSONObject measureJson = new JSONObject(defaultMeasureInfo);
/* 135 */     JSONObject axisJson = measureJson.getJSONObject(K_axis);
/* 136 */     JSONObject distanceJson = measureJson.getJSONObject(K_distance);
/* 137 */     JSONObject areaJson = measureJson.getJSONObject(K_area);
/*     */     
/* 139 */     MeasureInfo axisInfo = getFromJSON(axisJson.toString());
/* 140 */     MeasureInfo distanceInfo = getFromJSON(distanceJson.toString());
/* 141 */     MeasureInfo areaInfo = getFromJSON(areaJson.toString());
/*     */     
/* 143 */     if (axisInfo == null || distanceInfo == null || areaInfo == null) {
/* 144 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 148 */     Obj obj = annot.getSDFObj();
/*     */     
/* 150 */     Obj measureObj = obj.findObj(K_Measure);
/* 151 */     if (measureObj != null) {
/* 152 */       Obj scaleObj = measureObj.findObj(getScaleKey());
/* 153 */       if (scaleObj != null && scaleObj.isString()) {
/* 154 */         measureJson.put(K_scale, scaleObj.getAsPDFText());
/*     */       }
/*     */       
/* 157 */       Obj axisArray = measureObj.findObj(getAxisKey());
/* 158 */       if (axisArray != null && axisArray.isArray() && axisArray.size() > 0L) {
/* 159 */         Obj axis = axisArray.getAt(0);
/* 160 */         if (axis != null && axis.isDict()) {
/* 161 */           Obj factor = axis.findObj(getFactorKey());
/* 162 */           if (factor != null && factor.isNumber()) {
/* 163 */             axisInfo.setFactor(factor.getNumber());
/*     */           }
/* 165 */           Obj precision = axis.findObj(getPrecisionKey());
/* 166 */           if (precision != null && precision.isNumber()) {
/* 167 */             axisInfo.setPrecision((int)precision.getNumber());
/*     */           }
/* 169 */           Obj display = axis.findObj(getDistanceKey());
/* 170 */           if (display != null && display.isName()) {
/* 171 */             axisInfo.setDisplay(display.getName());
/*     */           }
/* 173 */           Obj decimalSymbol = axis.findObj(getDecimalSymbolKey());
/* 174 */           if (decimalSymbol != null && decimalSymbol.isString()) {
/* 175 */             axisInfo.setDecimalSymbol(decimalSymbol.getAsPDFText());
/*     */           }
/* 177 */           Obj thousandSymbol = axis.findObj(getThousandSymbolKey());
/* 178 */           if (thousandSymbol != null && thousandSymbol.isString()) {
/* 179 */             axisInfo.setThousandSymbol(thousandSymbol.getAsPDFText());
/*     */           }
/* 181 */           Obj unitSuffix = axis.findObj(getUnitSuffixKey());
/* 182 */           if (unitSuffix != null && unitSuffix.isString()) {
/* 183 */             axisInfo.setUnitSuffix(unitSuffix.getAsPDFText());
/*     */           }
/* 185 */           Obj unit = axis.findObj(getUnitKey());
/* 186 */           if (unit != null && unit.isString()) {
/* 187 */             axisInfo.setUnit(unit.getAsPDFText());
/*     */           }
/* 189 */           Obj unitPrefix = axis.findObj(getUnitPrefixKey());
/* 190 */           if (unitPrefix != null && unitPrefix.isString()) {
/* 191 */             axisInfo.setUnitPrefix(unitPrefix.getAsPDFText());
/*     */           }
/* 193 */           Obj unitPosition = axis.findObj(getUnitPositionKey());
/* 194 */           if (unitPosition != null && unitPosition.isName()) {
/* 195 */             axisInfo.setUnitPosition(unitPosition.getName());
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 201 */       Obj distanceArray = measureObj.findObj(getDistanceKey());
/* 202 */       if (distanceArray != null && distanceArray.isArray() && distanceArray.size() > 0L) {
/* 203 */         Obj distance = distanceArray.getAt(0);
/* 204 */         if (distance != null && distance.isDict()) {
/* 205 */           Obj factor = distance.findObj(getFactorKey());
/* 206 */           if (factor != null && factor.isNumber()) {
/* 207 */             distanceInfo.setFactor(factor.getNumber());
/*     */           }
/* 209 */           Obj precision = distance.findObj(getPrecisionKey());
/* 210 */           if (precision != null && precision.isNumber()) {
/* 211 */             distanceInfo.setPrecision((int)precision.getNumber());
/*     */           }
/* 213 */           Obj display = distance.findObj(getDistanceKey());
/* 214 */           if (display != null && display.isName()) {
/* 215 */             distanceInfo.setDisplay(display.getName());
/*     */           }
/* 217 */           Obj decimalSymbol = distance.findObj(getDecimalSymbolKey());
/* 218 */           if (decimalSymbol != null && decimalSymbol.isString()) {
/* 219 */             distanceInfo.setDecimalSymbol(decimalSymbol.getAsPDFText());
/*     */           }
/* 221 */           Obj thousandSymbol = distance.findObj(getThousandSymbolKey());
/* 222 */           if (thousandSymbol != null && thousandSymbol.isString()) {
/* 223 */             distanceInfo.setThousandSymbol(thousandSymbol.getAsPDFText());
/*     */           }
/* 225 */           Obj unitSuffix = distance.findObj(getUnitSuffixKey());
/* 226 */           if (unitSuffix != null && unitSuffix.isString()) {
/* 227 */             distanceInfo.setUnitSuffix(unitSuffix.getAsPDFText());
/*     */           }
/* 229 */           Obj unit = distance.findObj(getUnitKey());
/* 230 */           if (unit != null && unit.isString()) {
/* 231 */             distanceInfo.setUnit(unit.getAsPDFText());
/*     */           }
/* 233 */           Obj unitPrefix = distance.findObj(getUnitPrefixKey());
/* 234 */           if (unitPrefix != null && unitPrefix.isString()) {
/* 235 */             distanceInfo.setUnitPrefix(unitPrefix.getAsPDFText());
/*     */           }
/* 237 */           Obj unitPosition = distance.findObj(getUnitPositionKey());
/* 238 */           if (unitPosition != null && unitPosition.isName()) {
/* 239 */             distanceInfo.setUnitPosition(unitPosition.getName());
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 245 */       Obj areaArray = measureObj.findObj(getAreaKey());
/* 246 */       if (areaArray != null && areaArray.isArray() && areaArray.size() > 0L) {
/* 247 */         Obj area = areaArray.getAt(0);
/* 248 */         if (area != null && area.isDict()) {
/* 249 */           Obj factor = area.findObj(getFactorKey());
/* 250 */           if (factor != null && factor.isNumber()) {
/* 251 */             areaInfo.setFactor(factor.getNumber());
/*     */           }
/* 253 */           Obj precision = area.findObj(getPrecisionKey());
/* 254 */           if (precision != null && precision.isNumber()) {
/* 255 */             areaInfo.setPrecision((int)precision.getNumber());
/*     */           }
/* 257 */           Obj display = area.findObj(getDistanceKey());
/* 258 */           if (display != null && display.isName()) {
/* 259 */             areaInfo.setDisplay(display.getName());
/*     */           }
/* 261 */           Obj decimalSymbol = area.findObj(getDecimalSymbolKey());
/* 262 */           if (decimalSymbol != null && decimalSymbol.isString()) {
/* 263 */             areaInfo.setDecimalSymbol(decimalSymbol.getAsPDFText());
/*     */           }
/* 265 */           Obj thousandSymbol = area.findObj(getThousandSymbolKey());
/* 266 */           if (thousandSymbol != null && thousandSymbol.isString()) {
/* 267 */             areaInfo.setThousandSymbol(thousandSymbol.getAsPDFText());
/*     */           }
/* 269 */           Obj unitSuffix = area.findObj(getUnitSuffixKey());
/* 270 */           if (unitSuffix != null && unitSuffix.isString()) {
/* 271 */             areaInfo.setUnitSuffix(unitSuffix.getAsPDFText());
/*     */           }
/* 273 */           Obj unit = area.findObj(getUnitKey());
/* 274 */           if (unit != null && unit.isString()) {
/* 275 */             areaInfo.setUnit(unit.getAsPDFText());
/*     */           }
/* 277 */           Obj unitPrefix = area.findObj(getUnitPrefixKey());
/* 278 */           if (unitPrefix != null && unitPrefix.isString()) {
/* 279 */             areaInfo.setUnitPrefix(unitPrefix.getAsPDFText());
/*     */           }
/* 281 */           Obj unitPosition = area.findObj(getUnitPositionKey());
/* 282 */           if (unitPosition != null && unitPosition.isName()) {
/* 283 */             areaInfo.setUnitPosition(unitPosition.getName());
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 288 */       updateMeasureInfo(measureJson, K_axis, axisInfo);
/* 289 */       updateMeasureInfo(measureJson, K_distance, distanceInfo);
/* 290 */       updateMeasureInfo(measureJson, K_area, areaInfo);
/*     */       
/* 292 */       String measureInfo = measureJson.toString();
/* 293 */       Log.d(TAG, "getAnnotMeasureInfo: " + measureInfo);
/*     */       
/* 295 */       return measureJson.toString();
/*     */     } 
/*     */     
/* 298 */     return null;
/*     */   }
/*     */   
/*     */   public static MeasureInfo getFromJSON(String json) {
/*     */     try {
/* 303 */       Gson gson = new Gson();
/* 304 */       return (MeasureInfo)gson.fromJson(json, MeasureInfo.class);
/* 305 */     } catch (Exception ex) {
/* 306 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String getIT(Annot annot) throws PDFNetException {
/* 311 */     if (annot == null || !annot.isValid()) {
/* 312 */       return null;
/*     */     }
/* 314 */     Obj obj = annot.getSDFObj();
/* 315 */     Obj itObj = obj.findObj(K_IT);
/* 316 */     if (itObj != null && itObj.isName()) {
/* 317 */       return itObj.getName();
/*     */     }
/* 319 */     return null;
/*     */   }
/*     */   
/*     */   public static void putMeasurementInfo(Annot annot, String json) throws Exception {
/* 323 */     if (annot == null || !annot.isValid()) {
/*     */       return;
/*     */     }
/* 326 */     int annotType = annot.getType();
/* 327 */     if (annotType != 3 && annotType != 7 && annotType != 6) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 332 */     JSONObject measureJson = new JSONObject(json);
/* 333 */     String scale = measureJson.getString(K_scale);
/* 334 */     JSONObject axisJson = measureJson.getJSONObject(K_axis);
/* 335 */     JSONObject distanceJson = measureJson.getJSONObject(K_distance);
/* 336 */     JSONObject areaJson = measureJson.getJSONObject(K_area);
/*     */     
/* 338 */     MeasureInfo axisInfo = getFromJSON(axisJson.toString());
/* 339 */     MeasureInfo distanceInfo = getFromJSON(distanceJson.toString());
/* 340 */     MeasureInfo areaInfo = getFromJSON(areaJson.toString());
/*     */     
/* 342 */     if (axisInfo == null || distanceInfo == null || areaInfo == null) {
/*     */       return;
/*     */     }
/*     */     
/* 346 */     Obj obj = annot.getSDFObj();
/*     */ 
/*     */     
/* 349 */     String it = K_LineDimension;
/* 350 */     if (annotType == 7) {
/* 351 */       it = K_PolyLineDimension;
/* 352 */     } else if (annotType == 6) {
/* 353 */       it = K_PolygonDimension;
/*     */     } 
/* 355 */     obj.putName(K_IT, it);
/*     */ 
/*     */     
/* 358 */     Obj measureObj = obj.putDict(K_Measure);
/* 359 */     measureObj.putName(getTypeKey(), K_Measure);
/* 360 */     measureObj.putString(getScaleKey(), scale);
/*     */     
/* 362 */     Obj axisArray = measureObj.putArray(getAxisKey());
/* 363 */     Obj distanceArray = measureObj.putArray(getDistanceKey());
/* 364 */     Obj areaArray = measureObj.putArray(getAreaKey());
/*     */     
/* 366 */     Obj axis = axisArray.pushBackDict();
/* 367 */     axis.putNumber(getFactorKey(), axisInfo.getFactor());
/* 368 */     axis.putNumber(getPrecisionKey(), axisInfo.getPrecision());
/* 369 */     axis.putName(getDisplayKey(), axisInfo.getDisplay());
/* 370 */     axis.putString(getDecimalSymbolKey(), axisInfo.getDecimalSymbol());
/* 371 */     axis.putString(getThousandSymbolKey(), axisInfo.getThousandSymbol());
/* 372 */     axis.putString(getUnitSuffixKey(), axisInfo.getUnitSuffix());
/* 373 */     axis.putString(getUnitKey(), axisInfo.getUnit());
/* 374 */     axis.putString(getUnitPrefixKey(), axisInfo.getUnitPrefix());
/* 375 */     axis.putName(getUnitPositionKey(), axisInfo.getUnitPosition());
/*     */     
/* 377 */     Obj distance = distanceArray.pushBackDict();
/* 378 */     distance.putNumber(getFactorKey(), distanceInfo.getFactor());
/* 379 */     distance.putNumber(getPrecisionKey(), distanceInfo.getPrecision());
/* 380 */     distance.putName(getDisplayKey(), distanceInfo.getDisplay());
/* 381 */     distance.putString(getDecimalSymbolKey(), distanceInfo.getDecimalSymbol());
/* 382 */     distance.putString(getThousandSymbolKey(), distanceInfo.getThousandSymbol());
/* 383 */     distance.putString(getUnitSuffixKey(), distanceInfo.getUnitSuffix());
/* 384 */     distance.putString(getUnitKey(), distanceInfo.getUnit());
/* 385 */     distance.putString(getUnitPrefixKey(), distanceInfo.getUnitPrefix());
/* 386 */     distance.putName(getUnitPositionKey(), distanceInfo.getUnitPosition());
/*     */     
/* 388 */     Obj area = areaArray.pushBackDict();
/* 389 */     area.putNumber(getFactorKey(), areaInfo.getFactor());
/* 390 */     area.putNumber(getPrecisionKey(), areaInfo.getPrecision());
/* 391 */     area.putName(getDisplayKey(), areaInfo.getDisplay());
/* 392 */     area.putString(getDecimalSymbolKey(), areaInfo.getDecimalSymbol());
/* 393 */     area.putString(getThousandSymbolKey(), areaInfo.getThousandSymbol());
/* 394 */     area.putString(getUnitSuffixKey(), areaInfo.getUnitSuffix());
/* 395 */     area.putString(getUnitKey(), areaInfo.getUnit());
/* 396 */     area.putString(getUnitPrefixKey(), areaInfo.getUnitPrefix());
/* 397 */     area.putName(getUnitPositionKey(), areaInfo.getUnitPosition());
/*     */   }
/*     */   
/*     */   public static String getTypeKey() {
/* 401 */     return "Type";
/*     */   }
/*     */   
/*     */   public static String getScaleKey() {
/* 405 */     return "R";
/*     */   }
/*     */   
/*     */   public static String getAxisKey() {
/* 409 */     return "X";
/*     */   }
/*     */   
/*     */   public static String getDistanceKey() {
/* 413 */     return "D";
/*     */   }
/*     */   
/*     */   public static String getAreaKey() {
/* 417 */     return "A";
/*     */   }
/*     */   
/*     */   public static String getUnitKey() {
/* 421 */     return "U";
/*     */   }
/*     */   
/*     */   public static String getFactorKey() {
/* 425 */     return "C";
/*     */   }
/*     */   
/*     */   public static String getDecimalSymbolKey() {
/* 429 */     return "RD";
/*     */   }
/*     */   
/*     */   public static String getThousandSymbolKey() {
/* 433 */     return "RT";
/*     */   }
/*     */   
/*     */   public static String getPrecisionKey() {
/* 437 */     return "D";
/*     */   }
/*     */   
/*     */   public static String getDisplayKey() {
/* 441 */     return "F";
/*     */   }
/*     */   
/*     */   public static String getUnitPrefixKey() {
/* 445 */     return "PS";
/*     */   }
/*     */   
/*     */   public static String getUnitSuffixKey() {
/* 449 */     return "SS";
/*     */   }
/*     */   
/*     */   public static String getUnitPositionKey() {
/* 453 */     return "O";
/*     */   }
/*     */   
/*     */   public static String getMeasurementText(double value, MeasureInfo numberFormat) {
/* 457 */     String unit = numberFormat.getUnit();
/*     */     
/* 459 */     return modifyLastUnitValue(value, numberFormat) + " " + unit;
/*     */   }
/*     */   
/*     */   public static String modifyLastUnitValue(double value, MeasureInfo numberFormat) {
/* 463 */     String integerPart = "", fractionalPart = "";
/*     */     
/* 465 */     String display = numberFormat.getDisplay();
/*     */     
/* 467 */     if (display.equals("D")) {
/* 468 */       int precision = numberFormat.getPrecision();
/* 469 */       if (precision % 10 != 0) {
/* 470 */         Log.w(TAG, "precision for decimal display must be a multiple of 10");
/*     */       }
/*     */       
/* 473 */       int scale = String.valueOf(precision / 10).length();
/*     */ 
/*     */ 
/*     */       
/* 477 */       double result = BigDecimal.valueOf(value).setScale(scale, 4).doubleValue();
/*     */       
/* 479 */       String[] temp = String.valueOf(result).split("\\.");
/* 480 */       integerPart = temp[0];
/* 481 */       fractionalPart = (precision == 1) ? "" : (numberFormat.getDecimalSymbol() + temp[1]);
/* 482 */     } else if (display.equals("F")) {
/* 483 */       integerPart = trunc(value);
/*     */       
/* 485 */       int precision = numberFormat.getPrecision();
/* 486 */       fractionalPart = " " + Math.round(value % 1.0D * precision) + "/" + precision;
/* 487 */     } else if (display.equals("R")) {
/* 488 */       integerPart = String.valueOf(Math.round(value));
/* 489 */       fractionalPart = "";
/* 490 */     } else if (display.equals("T")) {
/* 491 */       integerPart = trunc(value);
/* 492 */       fractionalPart = "";
/*     */     } 
/*     */     
/* 495 */     return addThousandsSymbol(integerPart, numberFormat.getThousandSymbol()) + fractionalPart;
/*     */   }
/*     */   
/*     */   private static String trunc(double value) {
/* 499 */     String[] temp = String.valueOf(value).split("\\.");
/* 500 */     return temp[0];
/*     */   }
/*     */   
/*     */   public static String addThousandsSymbol(String value, String thousandsSymbol) {
/* 504 */     for (int i = value.length() - 3; i > 0; i -= 3) {
/* 505 */       value = value.substring(0, i) + thousandsSymbol + value.substring(i);
/*     */     }
/*     */     
/* 508 */     return value;
/*     */   }
/*     */   
/*     */   public static HashMap<String, Integer> getPrecisions() {
/* 512 */     HashMap<String, Integer> precisions = new HashMap<>(5);
/* 513 */     precisions.put("1", Integer.valueOf(1));
/* 514 */     precisions.put("0.1", Integer.valueOf(10));
/* 515 */     precisions.put("0.01", Integer.valueOf(100));
/* 516 */     precisions.put("0.001", Integer.valueOf(1000));
/* 517 */     precisions.put("0.0001", Integer.valueOf(10000));
/* 518 */     return precisions;
/*     */   }
/*     */   
/*     */   public static int getPrecision(String which) {
/* 522 */     HashMap<String, Integer> precisions = getPrecisions();
/* 523 */     if (precisions.get(which) != null) {
/* 524 */       return ((Integer)precisions.get(which)).intValue();
/*     */     }
/* 526 */     return PRECISION_DEFAULT;
/*     */   }
/*     */ 
/*     */   
/*     */   public static HashMap<String, Double> getUnitConversion() {
/* 531 */     HashMap<String, Double> unitConversion = new HashMap<>(9);
/* 532 */     unitConversion.put("mm", Double.valueOf(0.1D));
/* 533 */     unitConversion.put("cm", Double.valueOf(1.0D));
/* 534 */     unitConversion.put("m", Double.valueOf(100.0D));
/* 535 */     unitConversion.put("km", Double.valueOf(100000.0D));
/* 536 */     unitConversion.put("mi", Double.valueOf(160394.0D));
/* 537 */     unitConversion.put("yd", Double.valueOf(91.44D));
/* 538 */     unitConversion.put("ft", Double.valueOf(30.48D));
/* 539 */     unitConversion.put("in", Double.valueOf(2.54D));
/* 540 */     unitConversion.put("pt", Double.valueOf(0.0352778D));
/* 541 */     return unitConversion;
/*     */   }
/*     */   
/*     */   public static double getUnitConversion(String unit) {
/* 545 */     HashMap<String, Double> conversion = getUnitConversion();
/* 546 */     if (conversion.get(unit) != null) {
/* 547 */       return ((Double)conversion.get(unit)).doubleValue();
/*     */     }
/* 549 */     return 1.0D;
/*     */   }
/*     */   
/*     */   public static void updateMeasureInfo(@NonNull JSONObject jsonObject, String key, MeasureInfo info) throws Exception {
/* 553 */     if (key == null || info == null) {
/*     */       return;
/*     */     }
/* 556 */     Gson gson = new Gson();
/* 557 */     String jsonStr = gson.toJson(info);
/* 558 */     jsonObject.put(key, new JSONObject(jsonStr));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String setScaleAndPrecision(int annotType, @NonNull JSONObject jsonObject, @NonNull RulerItem rulerItem) {
/*     */     try {
/* 565 */       StringBuilder builder = new StringBuilder();
/* 566 */       builder.append(rulerItem.mRulerBase);
/* 567 */       builder.append(" ");
/* 568 */       builder.append(rulerItem.mRulerBaseUnit);
/* 569 */       builder.append(" = ");
/* 570 */       builder.append(rulerItem.mRulerTranslate);
/* 571 */       builder.append(" ");
/* 572 */       builder.append(rulerItem.mRulerTranslateUnit);
/* 573 */       Log.d(TAG, "setScale: " + builder.toString());
/* 574 */       jsonObject.put(K_scale, builder.toString());
/*     */       
/* 576 */       MeasureInfo info = getMeasureInfo(annotType, jsonObject);
/* 577 */       if (info != null) {
/* 578 */         String unit = rulerItem.mRulerTranslateUnit;
/* 579 */         if (annotType == 1009 || annotType == 1012)
/*     */         {
/* 581 */           unit = "sq " + rulerItem.mRulerTranslateUnit;
/*     */         }
/* 583 */         info.setUnit(unit);
/* 584 */         info.setPrecision(rulerItem.mPrecision);
/* 585 */         String key = getMeasureKey(annotType);
/* 586 */         updateMeasureInfo(jsonObject, key, info);
/*     */       } 
/*     */       
/* 589 */       MeasureInfo axisInfo = getAxisInfo(jsonObject);
/* 590 */       if (axisInfo != null) {
/* 591 */         axisInfo.setFactor((rulerItem.mRulerTranslate / rulerItem.mRulerBase) * getUnitConversion("pt") / getUnitConversion(rulerItem.mRulerBaseUnit));
/* 592 */         updateMeasureInfo(jsonObject, K_axis, axisInfo);
/*     */       } 
/*     */       
/* 595 */       Log.d(TAG, "setScale final: " + jsonObject.toString());
/* 596 */       return jsonObject.toString();
/* 597 */     } catch (Exception ex) {
/* 598 */       ex.printStackTrace();
/*     */       
/* 600 */       return null;
/*     */     } 
/*     */   }
/*     */   public static RulerItem calibrate(Annot annot, RulerItem rulerItem, float userInput) throws Exception {
/* 604 */     if (annot == null || !annot.isValid() || annot.getType() != 3) {
/* 605 */       return null;
/*     */     }
/* 607 */     Line line = new Line(annot);
/* 608 */     Point pt1 = line.getStartPoint();
/* 609 */     Point pt2 = line.getEndPoint();
/* 610 */     double lineLength = getLineLength(pt1.x, pt1.y, pt2.x, pt2.y);
/* 611 */     MeasureImpl measure = new MeasureImpl(AnnotUtils.getAnnotType(annot));
/* 612 */     if (measure.getMeasure() != null) {
/* 613 */       double axisFactor = userInput / lineLength / measure.getMeasure().getFactor();
/* 614 */       double rulerTranslate = axisFactor / getUnitConversion("pt") / getUnitConversion(rulerItem.mRulerBaseUnit) * rulerItem.mRulerBase;
/* 615 */       rulerItem.mRulerTranslate = (float)rulerTranslate;
/*     */ 
/*     */       
/* 618 */       RulerCreate.adjustContents((Annot)line, rulerItem, (line.getStartPoint()).x, (line.getStartPoint()).y, 
/* 619 */           (line.getEndPoint()).x, (line.getEndPoint()).y);
/* 620 */       return rulerItem;
/*     */     } 
/* 622 */     return null;
/*     */   }
/*     */   
/*     */   public static double getLineLength(double pt1x, double pt1y, double pt2x, double pt2y) {
/* 626 */     return Math.sqrt(Math.pow(pt2x - pt1x, 2.0D) + Math.pow(pt2y - pt1y, 2.0D));
/*     */   }
/*     */   
/*     */   public static RulerItem getRulerItemFromAnnot(Annot annot) {
/*     */     try {
/* 631 */       String measureInfo = getAnnotMeasureInfo(annot);
/* 632 */       if (measureInfo != null) {
/* 633 */         JSONObject jsonObject = new JSONObject(measureInfo);
/* 634 */         RulerItem item = getScale(jsonObject);
/* 635 */         if (item != null) {
/* 636 */           item.mPrecision = getPrecision(AnnotUtils.getAnnotType(annot), jsonObject);
/* 637 */           Log.d(TAG, "getRulerItemFromAnnot: " + item.toString());
/* 638 */           return item;
/*     */         } 
/*     */       } 
/* 641 */     } catch (Exception ex) {
/* 642 */       ex.printStackTrace();
/*     */     } 
/* 644 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static RulerItem getScale(@NonNull JSONObject jsonObject) {
/* 652 */     String scale = safeGetString(jsonObject, K_scale);
/* 653 */     if (scale != null) {
/* 654 */       Pattern p = Pattern.compile("(\\d*(?:.\\d+)?\\s\\w+)\\s=\\s(\\d*(?:.\\d+)?\\s\\w+)");
/* 655 */       Matcher m = p.matcher(scale);
/* 656 */       while (m.find()) {
/*     */         
/* 658 */         RulerItem item = new RulerItem();
/* 659 */         String found = m.group();
/*     */         
/* 661 */         String[] parts = found.split("=");
/* 662 */         if (parts.length == 2) {
/* 663 */           String[] g1 = parts[0].trim().split(" ");
/* 664 */           String[] g2 = parts[1].trim().split(" ");
/* 665 */           item.mRulerBase = Float.parseFloat(g1[0]);
/* 666 */           item.mRulerBaseUnit = g1[1];
/* 667 */           item.mRulerTranslate = Float.parseFloat(g2[0]);
/* 668 */           item.mRulerTranslateUnit = g2[1];
/* 669 */           Log.d(TAG, "getScale:" + item.toString());
/* 670 */           return item;
/*     */         } 
/*     */       } 
/*     */     } 
/* 674 */     return null;
/*     */   }
/*     */   
/*     */   public static int getPrecision(int annotType, @NonNull JSONObject jsonObject) {
/* 678 */     MeasureInfo measureInfo = getMeasureInfo(annotType, jsonObject);
/* 679 */     if (measureInfo != null) {
/* 680 */       return measureInfo.getPrecision();
/*     */     }
/* 682 */     return PRECISION_DEFAULT;
/*     */   }
/*     */   
/*     */   public static MeasureInfo getAxisInfo(@NonNull JSONObject jsonObject) {
/* 686 */     JSONObject result = safeGetJSON(jsonObject, K_axis);
/* 687 */     if (result != null) {
/* 688 */       return getFromJSON(result.toString());
/*     */     }
/* 690 */     return null;
/*     */   }
/*     */   
/*     */   public static String getMeasureKey(int annotType) {
/* 694 */     if (annotType == 1006 || annotType == 1008)
/*     */     {
/* 696 */       return K_distance; } 
/* 697 */     if (annotType == 1009 || annotType == 1012)
/*     */     {
/* 699 */       return K_area;
/*     */     }
/* 701 */     return null;
/*     */   }
/*     */   
/*     */   public static MeasureInfo getMeasureInfo(int annotType, @NonNull JSONObject jsonObject) {
/* 705 */     JSONObject result = getMeasureJSON(annotType, jsonObject);
/* 706 */     if (result != null) {
/* 707 */       return getFromJSON(result.toString());
/*     */     }
/* 709 */     return null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static JSONObject getMeasureJSON(int annotType, @NonNull JSONObject jsonObject) {
/* 714 */     if (annotType == 1006 || annotType == 1008)
/*     */     {
/* 716 */       return safeGetJSON(jsonObject, K_distance); } 
/* 717 */     if (annotType == 1009 || annotType == 1012)
/*     */     {
/* 719 */       return safeGetJSON(jsonObject, K_area);
/*     */     }
/* 721 */     return null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static String safeGetString(JSONObject json, String key) {
/*     */     try {
/* 727 */       if (json.has(key)) {
/* 728 */         return json.getString(key);
/*     */       }
/* 730 */     } catch (Exception ex) {
/* 731 */       ex.printStackTrace();
/*     */     } 
/* 733 */     return null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static int safeGetInt(JSONObject json, String key, int defaultValue) {
/*     */     try {
/* 739 */       if (json.has(key)) {
/* 740 */         return json.getInt(key);
/*     */       }
/* 742 */     } catch (Exception ex) {
/* 743 */       ex.printStackTrace();
/*     */     } 
/* 745 */     return defaultValue;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static JSONObject safeGetJSON(JSONObject json, String key) {
/*     */     try {
/* 751 */       if (json.has(key)) {
/* 752 */         return json.getJSONObject(key);
/*     */       }
/* 754 */     } catch (Exception ex) {
/* 755 */       ex.printStackTrace();
/*     */     } 
/* 757 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pd\\utils\MeasureUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */