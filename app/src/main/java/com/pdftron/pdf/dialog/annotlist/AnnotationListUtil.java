/*     */ package com.pdftron.pdf.dialog.annotlist;
/*     */ 
/*     */ import androidx.annotation.Nullable;
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.Annot;
/*     */ import com.pdftron.pdf.PDFDoc;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.Page;
/*     */ import com.pdftron.pdf.PageIterator;
/*     */ import com.pdftron.pdf.Rect;
/*     */ import com.pdftron.pdf.TextExtractor;
/*     */ import com.pdftron.pdf.annots.Markup;
/*     */ import com.pdftron.pdf.controls.AnnotationDialogFragment;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.AnnotUtils;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import io.reactivex.Observable;
/*     */ import io.reactivex.ObservableEmitter;
/*     */ import io.reactivex.ObservableOnSubscribe;
/*     */ import java.text.DateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
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
/*     */ public class AnnotationListUtil
/*     */ {
/*     */   public static Observable<List<AnnotationDialogFragment.AnnotationInfo>> from(@Nullable final PDFViewCtrl pdfViewCtrl) {
/*  40 */     return Observable.create(new ObservableOnSubscribe<List<AnnotationDialogFragment.AnnotationInfo>>()
/*     */         {
/*     */           public void subscribe(ObservableEmitter<List<AnnotationDialogFragment.AnnotationInfo>> emitter) throws Exception {
/*  43 */             if (pdfViewCtrl == null) {
/*  44 */               emitter.onComplete();
/*     */               return;
/*     */             } 
/*  47 */             PageIterator pageIterator = null;
/*  48 */             boolean shouldUnlockRead = false;
/*     */             try {
/*     */               try {
/*  51 */                 pdfViewCtrl.docLockRead();
/*  52 */                 shouldUnlockRead = true;
/*  53 */                 PDFDoc doc = pdfViewCtrl.getDoc();
/*  54 */                 if (doc != null) {
/*  55 */                   pageIterator = doc.getPageIterator(1);
/*     */                 }
/*  57 */               } catch (Exception e) {
/*  58 */                 AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */               } finally {
/*  60 */                 if (shouldUnlockRead) {
/*  61 */                   pdfViewCtrl.docUnlockRead();
/*     */                 }
/*  63 */                 shouldUnlockRead = false;
/*     */               } 
/*  65 */               if (pageIterator != null) {
/*     */                 
/*  67 */                 int pageNum = 0;
/*  68 */                 TextExtractor textExtractor = new TextExtractor();
/*     */                 
/*  70 */                 while (pageIterator.hasNext() || pdfViewCtrl == null) {
/*  71 */                   if (emitter.isDisposed()) {
/*  72 */                     emitter.onComplete();
/*     */                     
/*     */                     return;
/*     */                   } 
/*  76 */                   pageNum++;
/*  77 */                   Page page = null;
/*     */                   try {
/*  79 */                     pdfViewCtrl.docLockRead();
/*  80 */                     shouldUnlockRead = true;
/*  81 */                     page = pageIterator.next();
/*  82 */                   } catch (Exception e) {
/*  83 */                     AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */                   } finally {
/*  85 */                     if (shouldUnlockRead) {
/*  86 */                       pdfViewCtrl.docUnlockRead();
/*     */                     }
/*  88 */                     shouldUnlockRead = false;
/*     */                   } 
/*     */                   
/*  91 */                   List<AnnotationDialogFragment.AnnotationInfo> pageAnnots = new ArrayList<>();
/*  92 */                   if (page != null && page.isValid()) {
/*  93 */                     ArrayList<Annot> annotations = new ArrayList<>();
/*     */                     try {
/*  95 */                       pdfViewCtrl.docLockRead();
/*  96 */                       shouldUnlockRead = true;
/*  97 */                       annotations = pdfViewCtrl.getAnnotationsOnPage(pageNum);
/*  98 */                     } catch (Exception ex) {
/*  99 */                       AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*     */                     } finally {
/* 101 */                       if (shouldUnlockRead) {
/* 102 */                         pdfViewCtrl.docUnlockRead();
/*     */                       }
/* 104 */                       shouldUnlockRead = false;
/*     */                     } 
/* 106 */                     for (Annot annotation : annotations) {
/* 107 */                       if (emitter.isDisposed() || pdfViewCtrl == null) {
/* 108 */                         emitter.onComplete();
/*     */                         return;
/*     */                       } 
/*     */                       
/* 112 */                       try { pdfViewCtrl.docLockRead();
/* 113 */                         shouldUnlockRead = true;
/* 114 */                         AnnotationDialogFragment.AnnotationInfo info = AnnotationListUtil.toAnnotationInfo(annotation, page, textExtractor);
/* 115 */                         if (info == null)
/*     */                         
/*     */                         { 
/*     */ 
/*     */ 
/*     */ 
/*     */                           
/* 122 */                           if (shouldUnlockRead) {
/* 123 */                             pdfViewCtrl.docUnlockRead();
/*     */                           }
/* 125 */                           shouldUnlockRead = false; continue; }  pageAnnots.add(info); } catch (PDFNetException pDFNetException) {  } finally { if (shouldUnlockRead) pdfViewCtrl.docUnlockRead();  shouldUnlockRead = false; }
/*     */                     
/*     */                     } 
/*     */                     
/* 129 */                     emitter.onNext(pageAnnots);
/*     */                   } 
/*     */                 } 
/*     */               } 
/* 133 */             } catch (Exception e) {
/* 134 */               AnalyticsHandlerAdapter.getInstance().sendException(e);
/* 135 */               emitter.onError(e);
/*     */             } finally {
/* 137 */               emitter.onComplete();
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static AnnotationDialogFragment.AnnotationInfo toAnnotationInfo(Annot annotation, Page page, TextExtractor textExtractor) {
/*     */     try {
/* 146 */       if (annotation == null || !annotation.isValid()) {
/* 147 */         return null;
/*     */       }
/*     */       
/* 150 */       String contents = "";
/* 151 */       int type = AnnotUtils.getAnnotType(annotation);
/*     */       
/* 153 */       if (AnnotUtils.getAnnotImageResId(type) == 16908292) {
/* 154 */         return null;
/*     */       }
/*     */       
/* 157 */       Markup markup = new Markup(annotation);
/* 158 */       switch (type) {
/*     */         case 2:
/* 160 */           contents = annotation.getContents();
/*     */           break;
/*     */         
/*     */         case 8:
/*     */         case 9:
/*     */         case 10:
/*     */         case 11:
/* 167 */           textExtractor.begin(page);
/* 168 */           contents = textExtractor.getTextUnderAnnot(annotation);
/*     */           break;
/*     */       } 
/*     */ 
/*     */       
/* 173 */       if (markup.getPopup() != null && markup.getPopup().isValid()) {
/* 174 */         String popupContent = markup.getPopup().getContents();
/* 175 */         if (!Utils.isNullOrEmpty(popupContent)) {
/* 176 */           contents = popupContent;
/*     */         }
/*     */       } 
/* 179 */       Date annotLocalDate = AnnotUtils.getAnnotLocalDate(annotation);
/* 180 */       String dateStr = DateFormat.getDateTimeInstance(2, 3).format(annotLocalDate);
/* 181 */       Rect rect = markup.getRect();
/* 182 */       rect.normalize();
/* 183 */       return new AnnotationDialogFragment.AnnotationInfo(type, page
/*     */           
/* 185 */           .getIndex(), contents, markup
/*     */           
/* 187 */           .getTitle(), dateStr, annotation, rect
/*     */ 
/*     */           
/* 190 */           .getY2());
/* 191 */     } catch (PDFNetException pDFNetException) {
/*     */ 
/*     */       
/* 194 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\dialog\annotlist\AnnotationListUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */