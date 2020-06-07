/*     */ package com.pdftron.pdf.utils;
/*     */ 
/*     */ import android.graphics.PointF;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.Annot;
/*     */ import com.pdftron.pdf.ColorSpace;
/*     */ import com.pdftron.pdf.Element;
/*     */ import com.pdftron.pdf.ElementBuilder;
/*     */ import com.pdftron.pdf.ElementWriter;
/*     */ import com.pdftron.pdf.GState;
/*     */ import com.pdftron.pdf.PDFDoc;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.Point;
/*     */ import com.pdftron.pdf.Rect;
/*     */ import com.pdftron.pdf.StrokeOutlineBuilder;
/*     */ import com.pdftron.pdf.annots.Ink;
/*     */ import com.pdftron.pdf.tools.FreehandCreate;
/*     */ import com.pdftron.sdf.Doc;
/*     */ import com.pdftron.sdf.Obj;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PressureInkUtils
/*     */ {
/*  34 */   private static String KEY_THICKNESS = "PDFTron_Pressure_Thickness";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setInkList(@NonNull Ink ink, @NonNull List<List<PointF>> inkList) throws PDFNetException {
/*  44 */     Obj annotObj = ink.getSDFObj();
/*  45 */     annotObj.erase("InkList");
/*     */ 
/*     */     
/*  48 */     int pathIdx = 0;
/*  49 */     for (List<PointF> inkListItem : inkList) {
/*  50 */       Point p = new Point();
/*  51 */       int pointIdx = 0;
/*  52 */       for (PointF point : inkListItem) {
/*  53 */         p.x = point.x;
/*  54 */         p.y = point.y;
/*  55 */         ink.setPoint(pathIdx, pointIdx, p);
/*  56 */         pointIdx++;
/*     */       } 
/*  58 */       pathIdx++;
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
/*     */   public static void clearThicknessList(Annot annot) throws PDFNetException {
/*  70 */     Obj annotObj = annot.getSDFObj();
/*  71 */     annotObj.erase(KEY_THICKNESS);
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
/*     */   public static void setThicknessList(Ink annot, List<List<Float>> thicknessesList) throws PDFNetException {
/*  84 */     Obj thicknessArray = annot.getSDFObj().findObj(KEY_THICKNESS);
/*  85 */     int arrayOffset = 0;
/*  86 */     if (thicknessArray == null) {
/*  87 */       thicknessArray = annot.getSDFObj().putArray(KEY_THICKNESS);
/*  88 */       arrayOffset = 0;
/*     */     }
/*  90 */     else if (thicknessArray.isArray()) {
/*  91 */       arrayOffset = (int)thicknessArray.size();
/*     */     } 
/*     */ 
/*     */     
/*  95 */     for (int i = 0; i < thicknessesList.size(); i++) {
/*  96 */       Obj thicknessObj = thicknessArray.insertArray(i + arrayOffset);
/*  97 */       List<Float> thicknesses = thicknessesList.get(i);
/*  98 */       for (Iterator<Float> iterator = thicknesses.iterator(); iterator.hasNext(); ) { float thickness = ((Float)iterator.next()).floatValue();
/*  99 */         thicknessObj.pushBackNumber(thickness); }
/*     */     
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
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static List<List<Float>> getThicknessList(@NonNull Ink ink) throws PDFNetException {
/* 116 */     Obj thicknessesObj = ink.getSDFObj().findObj(KEY_THICKNESS);
/* 117 */     if (thicknessesObj == null) {
/* 118 */       return null;
/*     */     }
/* 120 */     int numStrokes = (int)thicknessesObj.size();
/* 121 */     List<List<Float>> thicknessesList = new ArrayList<>(numStrokes);
/* 122 */     for (int i = 0; i < numStrokes; i++) {
/*     */       
/* 124 */       Obj thicknessObj = thicknessesObj.getAt(i);
/* 125 */       long numCoords = thicknessObj.size();
/* 126 */       List<Float> outlinePath = new ArrayList<>();
/* 127 */       for (int k = 0; k < numCoords; k++) {
/* 128 */         outlinePath.add(Float.valueOf((float)thicknessObj.getAt(k).getNumber()));
/*     */       }
/* 130 */       thicknessesList.add(outlinePath);
/*     */     } 
/* 132 */     return thicknessesList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static List<double[]> getThicknessArrays(@NonNull Ink ink) throws PDFNetException {
/* 142 */     Obj thicknessesObj = ink.getSDFObj().findObj(KEY_THICKNESS);
/* 143 */     if (thicknessesObj == null) {
/* 144 */       return null;
/*     */     }
/* 146 */     int numStrokes = (int)thicknessesObj.size();
/* 147 */     List<double[]> thicknessesList = (List)new ArrayList<>(numStrokes);
/* 148 */     for (int i = 0; i < numStrokes; i++) {
/*     */       
/* 150 */       Obj thicknessObj = thicknessesObj.getAt(i);
/* 151 */       long numCoords = thicknessObj.size();
/* 152 */       double[] outlinePath = new double[(int)numCoords];
/* 153 */       for (int k = 0; k < numCoords; k++) {
/* 154 */         outlinePath[k] = thicknessObj.getAt(k).getNumber();
/*     */       }
/* 156 */       thicknessesList.add(outlinePath);
/*     */     } 
/* 158 */     return thicknessesList;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Rect getInkItemBBox(@NonNull List<List<PointF>> strokes, float thickness, int pageNumber, @Nullable PDFViewCtrl pdfViewCtrl, boolean inScreenCoordinates) {
/* 180 */     float min_x = Float.MAX_VALUE;
/* 181 */     float min_y = Float.MAX_VALUE;
/* 182 */     float max_x = Float.MIN_VALUE;
/* 183 */     float max_y = Float.MIN_VALUE;
/*     */     
/* 185 */     for (List<PointF> pageStroke : strokes) {
/* 186 */       for (PointF point : pageStroke) {
/* 187 */         min_x = Math.min(min_x, point.x);
/* 188 */         max_x = Math.max(max_x, point.x);
/* 189 */         min_y = Math.min(min_y, point.y);
/* 190 */         max_y = Math.max(max_y, point.y);
/*     */       } 
/*     */     } 
/*     */     try {
/*     */       double[] min, max;
/* 195 */       if (min_x == Float.MAX_VALUE && min_y == Float.MAX_VALUE && max_x == Float.MIN_VALUE && max_y == Float.MIN_VALUE)
/*     */       {
/*     */         
/* 198 */         return new Rect(0.0D, 0.0D, 0.0D, 0.0D);
/*     */       }
/*     */ 
/*     */       
/* 202 */       if (inScreenCoordinates) {
/* 203 */         min = pdfViewCtrl.convScreenPtToPagePt(min_x, min_y, pageNumber);
/* 204 */         max = pdfViewCtrl.convScreenPtToPagePt(max_x, max_y, pageNumber);
/*     */       } else {
/* 206 */         min = new double[] { min_x, min_y };
/* 207 */         max = new double[] { max_x, max_y };
/*     */       } 
/* 209 */       Rect rect = new Rect(min[0], min[1], max[0], max[1]);
/* 210 */       rect.normalize();
/* 211 */       rect.inflate(thickness);
/* 212 */       return rect;
/*     */     }
/* 214 */     catch (Exception e) {
/* 215 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static class EraserData {
/*     */     public final boolean hasErased;
/*     */     public final List<List<PointF>> newStrokeList;
/*     */     public final List<List<Float>> newThicknessList;
/*     */     
/*     */     public EraserData(boolean hasErased, List<List<PointF>> newStrokeList, List<List<Float>> newThicknessList) {
/* 225 */       this.hasErased = hasErased;
/* 226 */       this.newStrokeList = newStrokeList;
/* 227 */       this.newThicknessList = newThicknessList;
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
/*     */   public static EraserData erasePressureStrokes(@NonNull PDFViewCtrl pdfViewCtrl, @NonNull Ink ink, @NonNull List<PointF> eraserStroke, float eraserHalfWidth) throws PDFNetException {
/* 251 */     Obj inkList = ink.getSDFObj().findObj("InkList");
/*     */     
/* 253 */     List<List<PointF>> pathList = FreehandCreate.createStrokeListFromArrayObj(inkList);
/*     */ 
/*     */     
/* 256 */     List<List<Float>> thickness = getThicknessList(ink);
/* 257 */     ArrayList<List<PointF>> eraseStrokes = new ArrayList<>();
/* 258 */     eraseStrokes.add(eraserStroke);
/*     */     
/* 260 */     return erasePressureStrokesAndThickness(pathList, thickness, pdfViewCtrl, eraseStrokes, eraserHalfWidth, ink.getRect());
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
/*     */   @NonNull
/*     */   public static EraserData erasePressureStrokesAndThickness(@NonNull List<List<PointF>> oldStrokeList, @Nullable List<List<Float>> oldThicknessList, @NonNull PDFViewCtrl mPdfViewCtrl, @NonNull List<List<PointF>> eraserStrokes, float eraserHalfWidth, @NonNull Rect annotRect) throws PDFNetException {
/* 287 */     PDFDoc tempDoc = new PDFDoc();
/*     */     try {
/* 289 */       int size = oldStrokeList.size();
/* 290 */       List<Obj> strokesArrays = createStrokeArrays(tempDoc, mPdfViewCtrl, oldStrokeList);
/*     */       
/* 292 */       List<List<PointF>> outputStrokeList = new ArrayList<>();
/* 293 */       List<List<Float>> outputThicknessList = new ArrayList<>();
/*     */ 
/*     */ 
/*     */       
/* 297 */       Point prevPt = null;
/*     */       
/* 299 */       boolean hasErasedAnything = false;
/* 300 */       for (int i = 0; i < size; i++) {
/* 301 */         boolean hasErasedStroke = false;
/* 302 */         List<PointF> tmpOldStrokeList = oldStrokeList.get(i);
/* 303 */         Obj strokeArr = strokesArrays.get(i);
/*     */ 
/*     */         
/* 306 */         for (List<PointF> eraserStroke : eraserStrokes) {
/* 307 */           for (PointF eraserPoint : eraserStroke) {
/* 308 */             if (prevPt != null) {
/* 309 */               Point currPt = new Point(eraserPoint.x, eraserPoint.y);
/* 310 */               boolean erasedPoints = Ink.erasePoints(strokeArr, annotRect, prevPt, currPt, eraserHalfWidth);
/* 311 */               if (erasedPoints) {
/* 312 */                 hasErasedAnything = true;
/* 313 */                 hasErasedStroke = true;
/*     */                 break;
/*     */               } 
/* 316 */               prevPt = currPt; continue;
/*     */             } 
/* 318 */             prevPt = new Point(eraserPoint.x, eraserPoint.y);
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 324 */         if (!hasErasedStroke) {
/* 325 */           outputStrokeList.add(new ArrayList<>(tmpOldStrokeList));
/*     */           
/* 327 */           if (oldThicknessList != null) {
/* 328 */             outputThicknessList.add(oldThicknessList.get(i));
/*     */           }
/*     */         } 
/*     */       } 
/* 332 */       return new EraserData(hasErasedAnything, outputStrokeList, outputThicknessList);
/*     */     
/*     */     }
/*     */     finally {
/*     */ 
/*     */       
/* 338 */       tempDoc.close();
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
/*     */   public static EraserData erasePoints(@NonNull PDFViewCtrl pdfViewCtrl, @NonNull Ink ink, @NonNull List<PointF> eraserStroke, float eraserHalfWidth) throws PDFNetException {
/* 361 */     Obj inkList = ink.getSDFObj().findObj("InkList");
/*     */     
/* 363 */     List<List<PointF>> pathList = FreehandCreate.createStrokeListFromArrayObj(inkList);
/*     */ 
/*     */     
/* 366 */     List<List<Float>> thickness = getThicknessList(ink);
/* 367 */     ArrayList<List<PointF>> eraseStrokes = new ArrayList<>();
/* 368 */     eraseStrokes.add(eraserStroke);
/*     */     
/* 370 */     return erasePointsAndThickness(pathList, thickness, pdfViewCtrl, eraseStrokes, eraserHalfWidth, ink.getRect());
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
/*     */   @NonNull
/*     */   public static EraserData erasePointsAndThickness(@NonNull List<List<PointF>> oldStrokeList, @Nullable List<List<Float>> oldThicknessList, @NonNull PDFViewCtrl mPdfViewCtrl, @NonNull List<List<PointF>> eraserStrokes, float eraserHalfWidth, @NonNull Rect annotRect) throws PDFNetException {
/* 396 */     PDFDoc tempDoc = new PDFDoc();
/*     */     try {
/* 398 */       int size = oldStrokeList.size();
/* 399 */       List<Obj> strokesArrays = createStrokeArrays(tempDoc, mPdfViewCtrl, oldStrokeList);
/*     */       
/* 401 */       List<List<PointF>> outputStrokeList = new ArrayList<>();
/* 402 */       List<List<Float>> outputThicknessList = new ArrayList<>();
/*     */ 
/*     */ 
/*     */       
/* 406 */       Point prevPt = null;
/*     */       
/* 408 */       boolean hasErasedAnything = false;
/* 409 */       for (int i = 0; i < size; i++) {
/* 410 */         List<PointF> tmpOldStrokeList = oldStrokeList.get(i);
/* 411 */         Obj strokeArr = strokesArrays.get(i);
/*     */ 
/*     */         
/* 414 */         for (List<PointF> eraserStroke : eraserStrokes) {
/* 415 */           for (PointF eraserPoint : eraserStroke) {
/* 416 */             if (prevPt != null) {
/* 417 */               Point currPt = new Point(eraserPoint.x, eraserPoint.y);
/* 418 */               boolean erasedPoints = Ink.erasePoints(strokeArr, annotRect, prevPt, currPt, eraserHalfWidth);
/* 419 */               if (erasedPoints) {
/* 420 */                 hasErasedAnything = true;
/*     */               }
/* 422 */               prevPt = currPt; continue;
/*     */             } 
/* 424 */             prevPt = new Point(eraserPoint.x, eraserPoint.y);
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 430 */         if (hasErasedAnything) {
/* 431 */           List<List<PointF>> strokesList = FreehandCreate.createStrokeListFromArrayObj(strokeArr);
/* 432 */           outputStrokeList.addAll(strokesList);
/* 433 */           if (oldThicknessList != null) {
/* 434 */             List<List<Float>> newThickness = splitThicknessList(tmpOldStrokeList, oldThicknessList.get(i), strokesList);
/* 435 */             outputThicknessList.addAll(newThickness);
/*     */           } 
/*     */         } else {
/* 438 */           outputStrokeList.add(new ArrayList<>(tmpOldStrokeList));
/*     */           
/* 440 */           if (oldThicknessList != null) {
/* 441 */             outputThicknessList.add(oldThicknessList.get(i));
/*     */           }
/*     */         } 
/*     */       } 
/* 445 */       return new EraserData(hasErasedAnything, outputStrokeList, outputThicknessList);
/*     */     
/*     */     }
/*     */     finally {
/*     */ 
/*     */       
/* 451 */       tempDoc.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static List<Obj> createStrokeArrays(@NonNull PDFDoc tempDoc, @NonNull PDFViewCtrl pdfviewCtrl, @NonNull List<List<PointF>> strokeList) throws PDFNetException {
/* 460 */     int size = strokeList.size();
/* 461 */     List<Obj> strokesArrays = new ArrayList<>();
/*     */     
/* 463 */     for (List<PointF> pageStroke : strokeList) {
/* 464 */       Obj tempStrokesArray = tempDoc.createIndirectArray();
/* 465 */       Obj strokeArray = tempStrokesArray.pushBackArray();
/*     */ 
/*     */       
/* 468 */       int pointIndex = 0;
/* 469 */       for (PointF point : pageStroke) {
/* 470 */         while (strokeArray.size() < ((pointIndex + 1) * 2)) {
/* 471 */           strokeArray.pushBackNumber(0.0D);
/* 472 */           strokeArray.pushBackNumber(0.0D);
/*     */         } 
/*     */         
/* 475 */         strokeArray.getAt(pointIndex * 2).setNumber(point.x);
/* 476 */         strokeArray.getAt(pointIndex * 2 + 1).setNumber(point.y);
/* 477 */         pointIndex++;
/*     */       } 
/* 479 */       strokesArrays.add(tempStrokesArray);
/*     */     } 
/*     */     
/* 482 */     return strokesArrays;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static List<List<Float>> splitThicknessList(List<PointF> oldStroke, List<Float> oldThickness, List<List<PointF>> newStrokes) {
/* 489 */     HashMap<PointF, Float> thicknessMap = new HashMap<>();
/* 490 */     for (int i = 0; i < oldStroke.size(); i++) {
/* 491 */       thicknessMap.put(oldStroke.get(i), oldThickness.get(i));
/*     */     }
/*     */ 
/*     */     
/* 495 */     List<List<Float>> newThicknesses = new ArrayList<>();
/* 496 */     for (List<PointF> stroke : newStrokes) {
/* 497 */       List<Float> tempThickness = new ArrayList<>();
/* 498 */       for (PointF pt : stroke) {
/* 499 */         Float thickness = thicknessMap.get(pt);
/* 500 */         tempThickness.add(Float.valueOf((thickness != null) ? thickness.floatValue() : -1.0F));
/*     */       } 
/* 502 */       newThicknesses.add(tempThickness);
/*     */     } 
/*     */ 
/*     */     
/* 506 */     for (List<Float> thicknesses : newThicknesses) {
/* 507 */       if (thicknesses.size() == 2) {
/* 508 */         float first = ((Float)thicknesses.get(0)).floatValue();
/* 509 */         float second = ((Float)thicknesses.get(1)).floatValue();
/* 510 */         if (first == -1.0D && second == -1.0D) {
/* 511 */           thicknesses.set(0, Float.valueOf(1.0F));
/* 512 */           thicknesses.set(1, Float.valueOf(1.0F)); continue;
/* 513 */         }  if (second == -1.0D) {
/* 514 */           thicknesses.set(1, thicknesses.get(0)); continue;
/*     */         } 
/* 516 */         thicknesses.set(0, thicknesses.get(1));
/*     */         continue;
/*     */       } 
/* 519 */       for (int j = 0; j < thicknesses.size(); j++) {
/* 520 */         if (((Float)thicknesses.get(j)).floatValue() == -1.0F) {
/* 521 */           if (thicknesses.size() > 1) {
/* 522 */             if (j == 0) {
/* 523 */               thicknesses.set(j, thicknesses.get(j + 1));
/* 524 */             } else if (j == thicknesses.size() - 1) {
/* 525 */               thicknesses.set(j, thicknesses.get(j - 1));
/*     */             } else {
/* 527 */               throw new RuntimeException("This should never happen!");
/*     */             } 
/*     */           } else {
/* 530 */             thicknesses.set(j, Float.valueOf(1.0F));
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 537 */     if (newStrokes.size() != newThicknesses.size()) {
/* 538 */       throw new RuntimeException("This should never happen!");
/*     */     }
/*     */     
/* 541 */     return newThicknesses;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isPressureSensitive(@NonNull Annot ink) throws PDFNetException {
/* 552 */     Obj thicknessPathsObj = ink.getSDFObj().findObj(KEY_THICKNESS);
/* 553 */     return (thicknessPathsObj != null);
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
/*     */   private static List<double[]> generateOutlinesFromArray(List<ArrayList<PointF>> pathList, List<double[]> thicknessesList, double baseThickness) {
/* 565 */     if (pathList.size() != thicknessesList.size()) {
/* 566 */       return null;
/*     */     }
/* 568 */     List<double[]> outlines = (List)new ArrayList<>();
/*     */     
/* 570 */     for (int i = 0; i < pathList.size(); i++) {
/* 571 */       double[] thicknesses = thicknessesList.get(i);
/* 572 */       ArrayList<PointF> path = pathList.get(i);
/* 573 */       if (thicknesses.length != path.size()) {
/* 574 */         return null;
/*     */       }
/*     */       
/* 577 */       StrokeOutlineBuilder strokeOutlineBuilder = new StrokeOutlineBuilder(baseThickness);
/* 578 */       for (int k = 0; k < path.size(); k++) {
/* 579 */         strokeOutlineBuilder.addPoint(((PointF)path.get(k)).x, ((PointF)path.get(k)).y, thicknesses[k]);
/*     */       }
/* 581 */       outlines.add(strokeOutlineBuilder.getOutline());
/*     */     } 
/* 583 */     return outlines;
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
/*     */   @Nullable
/*     */   public static List<double[]> generateOutlines(List<List<PointF>> pathList, List<List<Float>> thicknessesList, float baseThickness) {
/* 599 */     if (pathList.size() != thicknessesList.size()) {
/* 600 */       return null;
/*     */     }
/* 602 */     List<double[]> outlines = (List)new ArrayList<>();
/*     */     
/* 604 */     for (int i = 0; i < pathList.size(); i++) {
/* 605 */       List<Float> thicknesses = thicknessesList.get(i);
/* 606 */       List<PointF> path = pathList.get(i);
/* 607 */       if (thicknesses.size() != path.size()) {
/* 608 */         return null;
/*     */       }
/*     */       
/* 611 */       StrokeOutlineBuilder strokeOutlineBuilder = new StrokeOutlineBuilder(baseThickness);
/* 612 */       for (int k = 0; k < path.size(); k++) {
/* 613 */         strokeOutlineBuilder.addPoint(((PointF)path.get(k)).x, ((PointF)path.get(k)).y, ((Float)thicknesses.get(k)).floatValue());
/*     */       }
/* 615 */       outlines.add(strokeOutlineBuilder.getOutline());
/*     */     } 
/* 617 */     return outlines;
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
/*     */   private static Obj writeStrokeOutline(ElementBuilder eb, ElementWriter writer, List<double[]> outlines, Ink annot, boolean shouldInflate) throws PDFNetException {
/* 629 */     for (double[] path : outlines) {
/*     */       
/* 631 */       if (path.length == 0) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */       
/* 636 */       int numOperators = (path.length / 2 - 1) / 3 + 1;
/* 637 */       byte[] operators = new byte[numOperators];
/* 638 */       operators[0] = 1;
/* 639 */       for (int j = 1; j < numOperators; j++) {
/* 640 */         operators[j] = 3;
/*     */       }
/*     */ 
/*     */       
/* 644 */       eb.createPath(path, operators);
/*     */       
/* 646 */       Element element = eb.pathEnd();
/* 647 */       element.setPathFill(true);
/* 648 */       element.setWindingFill(true);
/* 649 */       GState gstate = element.getGState();
/*     */       
/* 651 */       gstate.setFillColorSpace(ColorSpace.createDeviceRGB());
/* 652 */       gstate.setFillColor(annot.getColorAsRGB());
/* 653 */       gstate.setFillOpacity(annot.getOpacity());
/* 654 */       writer.writePlacedElement(element);
/*     */     } 
/*     */     
/* 657 */     Obj newAppearanceStream = writer.end();
/* 658 */     Rect bbox = annot.getRect();
/* 659 */     if (shouldInflate) {
/* 660 */       bbox.inflate(10.0D);
/*     */     }
/* 662 */     newAppearanceStream.putRect("BBox", bbox
/* 663 */         .getX1(), bbox
/* 664 */         .getY1(), bbox
/* 665 */         .getX2(), bbox
/* 666 */         .getY2());
/* 667 */     annot.setRect(bbox);
/*     */     
/* 669 */     return newAppearanceStream;
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
/*     */   public static boolean refreshCustomInkAppearanceForExistingAnnot(@NonNull Annot annot) {
/* 682 */     ElementWriter writer = null;
/* 683 */     ElementBuilder eb = null;
/*     */     try {
/* 685 */       Ink ink = new Ink(annot);
/* 686 */       writer = new ElementWriter();
/* 687 */       writer.begin(annot.getAppearance());
/*     */ 
/*     */       
/* 690 */       List<double[]> thicknessesList = getThicknessArrays(ink);
/* 691 */       if (thicknessesList == null) {
/* 692 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 696 */       Obj inkList = annot.getSDFObj().findObj("InkList");
/*     */       
/* 698 */       List<ArrayList<PointF>> pathList = FreehandCreate.createPageStrokesFromArrayObj(inkList);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 703 */       double baseThickness = annot.getBorderStyle().getWidth();
/* 704 */       List<double[]> outlines = generateOutlinesFromArray(pathList, thicknessesList, baseThickness);
/* 705 */       if (outlines == null) {
/* 706 */         return false;
/*     */       }
/*     */       
/* 709 */       eb = new ElementBuilder();
/*     */ 
/*     */       
/* 712 */       ink.setAppearance(writeStrokeOutline(eb, writer, outlines, ink, false));
/* 713 */       return true;
/* 714 */     } catch (Exception e) {
/* 715 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */     } finally {
/* 717 */       if (writer != null) {
/*     */         try {
/* 719 */           writer.destroy();
/* 720 */         } catch (Exception exception) {}
/*     */       }
/*     */ 
/*     */       
/* 724 */       if (eb != null) {
/*     */         try {
/* 726 */           eb.destroy();
/* 727 */         } catch (Exception exception) {}
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 732 */     return false;
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
/*     */   public static boolean refreshCustomAppearanceForNewAnnot(@NonNull PDFViewCtrl pdfViewCtrl, @NonNull Annot annot) {
/* 746 */     ElementWriter writer = null;
/* 747 */     ElementBuilder eb = null;
/*     */     try {
/* 749 */       Ink ink = new Ink(annot);
/* 750 */       writer = new ElementWriter();
/* 751 */       writer.begin((Doc)pdfViewCtrl.getDoc());
/*     */       
/* 753 */       List<double[]> allThicknessesList = getThicknessArrays(ink);
/* 754 */       if (allThicknessesList == null) {
/* 755 */         return false;
/*     */       }
/*     */       
/* 758 */       Obj inkList = annot.getSDFObj().findObj("InkList");
/*     */       
/* 760 */       List<ArrayList<PointF>> pathList = FreehandCreate.createPageStrokesFromArrayObj(inkList);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 765 */       double baseThickness = annot.getBorderStyle().getWidth();
/* 766 */       List<double[]> outlines = generateOutlinesFromArray(pathList, allThicknessesList, baseThickness);
/* 767 */       if (outlines == null) {
/* 768 */         return false;
/*     */       }
/*     */       
/* 771 */       eb = new ElementBuilder();
/*     */ 
/*     */       
/* 774 */       ink.setAppearance(writeStrokeOutline(eb, writer, outlines, ink, true));
/* 775 */       return true;
/* 776 */     } catch (Exception e) {
/* 777 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */     } finally {
/* 779 */       if (writer != null) {
/*     */         try {
/* 781 */           writer.destroy();
/* 782 */         } catch (Exception exception) {}
/*     */       }
/*     */ 
/*     */       
/* 786 */       if (eb != null) {
/*     */         try {
/* 788 */           eb.destroy();
/* 789 */         } catch (Exception exception) {}
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 794 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pd\\utils\PressureInkUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */