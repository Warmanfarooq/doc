/*     */ package com.pdftron.pdf.controls;
/*     */ 
/*     */ import android.content.Context;
/*     */ import androidx.annotation.NonNull;
/*     */ import com.pdftron.common.Matrix2D;
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.Annot;
/*     */ import com.pdftron.pdf.Element;
/*     */ import com.pdftron.pdf.ElementBuilder;
/*     */ import com.pdftron.pdf.ElementReader;
/*     */ import com.pdftron.pdf.ElementWriter;
/*     */ import com.pdftron.pdf.Font;
/*     */ import com.pdftron.pdf.Image;
/*     */ import com.pdftron.pdf.PDFDoc;
/*     */ import com.pdftron.pdf.Page;
/*     */ import com.pdftron.pdf.PageSet;
/*     */ import com.pdftron.pdf.Point;
/*     */ import com.pdftron.pdf.Rect;
/*     */ import com.pdftron.sdf.Doc;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ 
/*     */ 
/*     */ class ReflowUtils
/*     */ {
/*     */   static class PageRectTwo
/*     */   {
/*     */     public int pageNumber;
/*     */     public Rect rect;
/*     */     public String imagePath;
/*     */     
/*     */     public PageRectTwo(int page, Rect rect) {
/*  37 */       this.pageNumber = page;
/*  38 */       this.rect = rect;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void removeLinksFromPage(Page page, Rect overlapRect) throws PDFNetException {
/*  46 */     Rect nothingRect = new Rect();
/*     */ 
/*     */ 
/*     */     
/*  50 */     int numAnnots = page.getNumAnnots();
/*  51 */     for (int i = numAnnots - 1; i >= 0; i--) {
/*  52 */       Annot annot = page.getAnnot(i);
/*  53 */       Rect bbox = annot.getRect();
/*  54 */       if (nothingRect.intersectRect(overlapRect, bbox)) {
/*  55 */         page.annotRemove(annot);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Page writeTextOverImage(PDFDoc pdfDoc, @NonNull ArrayList<PageRectTwo> pageRects, @NonNull List<PDFDoc> toCleanup) throws PDFNetException {
/*  67 */     ElementBuilder eb = new ElementBuilder();
/*     */     
/*  69 */     ElementWriter writer = new ElementWriter();
/*     */ 
/*     */ 
/*     */     
/*  73 */     int imgNum = 1;
/*  74 */     int fontSize = 6;
/*     */ 
/*     */     
/*  77 */     PDFDoc docToBeCleanupLater = new PDFDoc();
/*  78 */     toCleanup.add(docToBeCleanupLater);
/*  79 */     PageSet pageSet = new PageSet(((PageRectTwo)pageRects.get(0)).pageNumber);
/*  80 */     docToBeCleanupLater.insertPages(0, pdfDoc, pageSet, PDFDoc.InsertBookmarkMode.NONE, null);
/*  81 */     Page page = docToBeCleanupLater.getPage(1);
/*     */     
/*  83 */     for (PageRectTwo pageRect : pageRects) {
/*  84 */       removeLinksFromPage(page, pageRect.rect);
/*     */       
/*  86 */       Rect rect = pageRect.rect;
/*     */ 
/*     */ 
/*     */       
/*  90 */       if (rect.getWidth() > page.getPageWidth(1) / 2.0D * 0.95D) {
/*     */         
/*  92 */         fontSize = 3;
/*     */       } else {
/*     */         
/*  95 */         fontSize = 8;
/*     */       } 
/*  97 */       writer.begin(page, 1, true, true, page.getResourceDict());
/*     */       
/*  99 */       eb.reset();
/*     */ 
/*     */       
/* 102 */       Element element = eb.createTextBegin(Font.create((Doc)docToBeCleanupLater.getSDFDoc(), 0, false), fontSize);
/* 103 */       writer.writeElement(element);
/*     */ 
/*     */       
/* 106 */       String stringToWrite = String.format(Locale.getDefault(), "reflowimaae%04d", new Object[] { Integer.valueOf(imgNum++) });
/* 107 */       element = eb.createTextRun(stringToWrite);
/*     */       
/* 109 */       Rect bbbbox = element.getBBox();
/* 110 */       double hScale = Math.abs(rect.getX2() - rect.getX1()) / Math.abs(bbbbox.getX2() - bbbbox.getX1());
/* 111 */       double vScale = Math.abs(rect.getY2() - rect.getY1()) / Math.abs(bbbbox.getY2() - bbbbox.getY1());
/*     */       
/* 113 */       vScale = Math.min(vScale, 1.0D);
/* 114 */       element.setTextMatrix(hScale, 0.0D, 0.0D, vScale, rect.getX1(), rect.getY1() + rect.getY2() - rect.getY1() - fontSize);
/* 115 */       writer.writeElement(element);
/*     */       
/* 117 */       Rect newbbBox = null;
/* 118 */       for (int ii = 1; ii < 500; ii++) {
/* 119 */         element = eb.createTextNewLine(0.0D, -fontSize);
/* 120 */         writer.writeElement(element);
/*     */         
/* 122 */         stringToWrite = String.format(Locale.getDefault(), "reflowimaae%04d", new Object[] { Integer.valueOf(imgNum - 1) });
/*     */         
/* 124 */         element = eb.createTextRun(stringToWrite);
/* 125 */         writer.writeElement(element);
/*     */         
/* 127 */         newbbBox = element.getBBox();
/*     */         
/* 129 */         if (newbbBox.getY1() < rect.getY1() + fontSize) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 135 */       writer.writeElement(eb.createTextEnd());
/*     */ 
/*     */       
/* 138 */       writer.end();
/*     */     } 
/* 140 */     return page;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static ArrayList<PageRectTwo> imageExtract(Context context, ElementReader reader, Page page, int pageNumber, int startIndex) throws PDFNetException, IOException {
/* 146 */     ArrayList<PageRectTwo> imageRects = new ArrayList<>();
/*     */     
/* 148 */     Rect pdfRectCropBox = page.getCropBox();
/* 149 */     int exportedImageCounterTwo = startIndex;
/*     */     
/* 151 */     File cacheDir = context.getCacheDir();
/* 152 */     File reflowCache = new File(cacheDir, "reflow");
/* 153 */     FileUtils.forceMkdir(reflowCache);
/*     */     Element element;
/* 155 */     while ((element = reader.next()) != null) {
/* 156 */       Matrix2D ctm; double x2; double y2; Point pt; Rect rect; Rect newRect; ArrayList<PageRectTwo> more; switch (element.getType()) {
/*     */         case 6:
/*     */         case 7:
/* 159 */           ctm = element.getCTM();
/*     */           
/* 161 */           x2 = 1.0D; y2 = 1.0D;
/*     */           
/* 163 */           pt = ctm.multPoint(x2, y2);
/* 164 */           rect = new Rect(ctm.getH(), ctm.getV(), pt.x, pt.y);
/*     */           
/* 166 */           rect.normalize();
/*     */           
/* 168 */           rect.setX1(rect.getX1() - pdfRectCropBox.getX1());
/* 169 */           rect.setX2(rect.getX2() - pdfRectCropBox.getX1());
/* 170 */           rect.setY1(rect.getY1() - pdfRectCropBox.getY1());
/* 171 */           rect.setY2(rect.getY2() - pdfRectCropBox.getY1());
/*     */           
/* 173 */           rect.normalize();
/*     */           
/* 175 */           newRect = new Rect();
/* 176 */           if (newRect.intersectRect(rect, pdfRectCropBox)) {
/* 177 */             rect.setX1(newRect.getX1());
/* 178 */             rect.setX2(newRect.getX2());
/* 179 */             rect.setY1(newRect.getY1());
/* 180 */             rect.setY2(newRect.getY2());
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 185 */             PageRectTwo pageRect = new PageRectTwo(pageNumber, rect);
/*     */             
/* 187 */             if (element.getType() == 6) {
/* 188 */               Image image = new Image(element.getXObject());
/*     */               
/* 190 */               Matrix2D transform = element.getCTM();
/*     */               
/* 192 */               if (transform.equals(Matrix2D.identityMatrix()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 198 */               String filename = String.format(Locale.getDefault(), "image_extract_%d_%04d.png", new Object[] { Integer.valueOf(pageNumber), Integer.valueOf(++exportedImageCounterTwo) });
/* 199 */               File tempFile = new File(reflowCache, filename);
/*     */ 
/*     */ 
/*     */               
/* 203 */               pageRect.imagePath = tempFile.getAbsolutePath();
/*     */               
/* 205 */               image.exportAsPng(tempFile.getAbsolutePath());
/*     */               
/* 207 */               imageRects.add(pageRect);
/*     */             } 
/*     */           } 
/*     */ 
/*     */ 
/*     */         
/*     */         case 9:
/* 214 */           reader.formBegin();
/* 215 */           more = imageExtract(context, reader, page, pageNumber, exportedImageCounterTwo);
/* 216 */           exportedImageCounterTwo += more.size();
/* 217 */           imageRects.addAll(more);
/* 218 */           reader.end();
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     } 
/* 225 */     ArrayList<PageRectTwo> result = new ArrayList<>();
/* 226 */     result.addAll(imageRects);
/* 227 */     return result;
/*     */   }
/*     */   
/*     */   static Page prepareImagesForPage(Context context, PDFDoc pdfDoc, int pageNumber, List<PDFDoc> toCleanup) throws PDFNetException, IOException {
/* 231 */     ElementReader reader = new ElementReader();
/* 232 */     ArrayList<PageRectTwo> pageImageRects = new ArrayList<>();
/*     */     
/* 234 */     Page page = pdfDoc.getPage(pageNumber);
/* 235 */     reader.begin(page);
/* 236 */     ArrayList<PageRectTwo> pageImageRectsForCurrentPage = imageExtract(context, reader, page, pageNumber, 0);
/* 237 */     pageImageRects.addAll(pageImageRectsForCurrentPage);
/* 238 */     reader.end();
/*     */     
/* 240 */     if (pageImageRectsForCurrentPage.size() == 0) {
/* 241 */       return null;
/*     */     }
/* 243 */     return writeTextOverImage(pdfDoc, pageImageRects, toCleanup);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\controls\ReflowUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */