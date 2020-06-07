/*     */ package com.pdftron.pdf.dialog.pagelabel;
/*     */ 
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.PDFDoc;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.PageLabel;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.sdf.Doc;
/*     */ import java.text.Collator;
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
/*     */ public class PageLabelUtils
/*     */ {
/*     */   @Nullable
/*     */   public static boolean setPageLabel(@NonNull PDFViewCtrl pdfViewCtrl, int style, @NonNull String prefix, int startValue, int fromPage, int toPage) {
/*  35 */     return setDocPageLabel(pdfViewCtrl, style, prefix, startValue, fromPage, toPage);
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
/*     */   public static boolean setPageLabel(@NonNull PDFViewCtrl pdfViewCtrl, @NonNull PageLabelSetting pageLabelSetting) {
/*  47 */     return setPageLabel(pdfViewCtrl, pageLabelSetting
/*  48 */         .getPageLabelStyle(), pageLabelSetting.getPrefix(), pageLabelSetting
/*  49 */         .getStartNum(), pageLabelSetting.getFromPage(), pageLabelSetting
/*  50 */         .getToPage());
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   static boolean setDocPageLabel(@NonNull PDFViewCtrl pdfViewCtrl, int style, @NonNull String prefix, int startValue, int fromPage, int toPage) {
/*  56 */     boolean shouldUnlock = false;
/*  57 */     boolean result = false;
/*     */     try {
/*  59 */       pdfViewCtrl.docLock(true);
/*  60 */       shouldUnlock = true;
/*  61 */       PDFDoc doc = pdfViewCtrl.getDoc();
/*  62 */       int totalPages = doc.getPageCount();
/*     */       
/*  64 */       if (fromPage < 1 || toPage > totalPages || toPage < fromPage) {
/*  65 */         throw new IndexOutOfBoundsException("Invalid to and from pages. Was given from page %d to page %d");
/*     */       }
/*     */ 
/*     */       
/*  69 */       PageLabel lastOverridenLabel = null;
/*  70 */       for (int i = fromPage; i <= toPage; i++) {
/*  71 */         PageLabel currentLabel = doc.getPageLabel(i);
/*  72 */         if (currentLabel.isValid()) {
/*  73 */           lastOverridenLabel = currentLabel;
/*     */           
/*  75 */           if (currentLabel.getFirstPageNum() >= fromPage) {
/*  76 */             doc.removePageLabel(i);
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/*  82 */       PageLabel newLabel = PageLabel.create((Doc)doc, style, prefix, startValue);
/*  83 */       doc.setPageLabel(fromPage, newLabel);
/*  84 */       newLabel = doc.getPageLabel(fromPage);
/*     */ 
/*     */       
/*  87 */       int nextPage = toPage + 1;
/*  88 */       if (nextPage <= totalPages) {
/*  89 */         PageLabel nextLabel = doc.getPageLabel(nextPage);
/*  90 */         if (pageLabelEquals(nextLabel, newLabel)) {
/*  91 */           if (lastOverridenLabel == null) {
/*     */             
/*  93 */             nextLabel = PageLabel.create((Doc)doc, 0, "", fromPage);
/*     */           }
/*     */           else {
/*     */             
/*  97 */             int nextStart = lastOverridenLabel.getStart() + fromPage - lastOverridenLabel.getFirstPageNum();
/*  98 */             nextLabel = PageLabel.create((Doc)doc, lastOverridenLabel.getStyle(), lastOverridenLabel
/*  99 */                 .getPrefix(), nextStart);
/*     */           } 
/* 101 */           doc.setPageLabel(nextPage, nextLabel);
/*     */         } 
/*     */       } 
/* 104 */       result = true;
/* 105 */     } catch (Exception e) {
/* 106 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */     } finally {
/* 108 */       if (shouldUnlock) {
/* 109 */         pdfViewCtrl.docUnlock();
/*     */       }
/*     */     } 
/* 112 */     return result;
/*     */   }
/*     */   
/*     */   private static boolean pageLabelEquals(PageLabel nextLabel, PageLabel newLabel) throws PDFNetException {
/* 116 */     return (nextLabel.getPrefix().equals(newLabel.getPrefix()) && nextLabel
/* 117 */       .getFirstPageNum() == newLabel.getFirstPageNum() && nextLabel
/* 118 */       .getStyle() == newLabel.getStyle());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PageLabel getPageLabel(@NonNull PDFViewCtrl pdfViewCtrl, int page) {
/* 129 */     boolean shouldUnlockRead = false;
/*     */     try {
/* 131 */       pdfViewCtrl.docLockRead();
/* 132 */       shouldUnlockRead = true;
/* 133 */       PageLabel pageLabel = pdfViewCtrl.getDoc().getPageLabel(page);
/* 134 */       if (pageLabel.isValid()) {
/* 135 */         return pageLabel;
/*     */       }
/* 137 */     } catch (Exception e) {
/* 138 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */     } finally {
/* 140 */       if (shouldUnlockRead) {
/* 141 */         pdfViewCtrl.docUnlockRead();
/*     */       }
/*     */     } 
/* 144 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getPageLabelPrefix(@NonNull PDFViewCtrl pdfViewCtrl, int page) {
/* 155 */     boolean shouldUnlockRead = false;
/*     */     try {
/* 157 */       pdfViewCtrl.docLockRead();
/* 158 */       shouldUnlockRead = true;
/* 159 */       PageLabel pageLabel = pdfViewCtrl.getDoc().getPageLabel(page);
/* 160 */       if (pageLabel.isValid()) {
/* 161 */         return pageLabel.getPrefix();
/*     */       }
/* 163 */     } catch (Exception e) {
/* 164 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */     } finally {
/* 166 */       if (shouldUnlockRead) {
/* 167 */         pdfViewCtrl.docUnlockRead();
/*     */       }
/*     */     } 
/* 170 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getPageLabelTitle(@NonNull PDFViewCtrl pdfViewCtrl, int page) {
/* 181 */     boolean shouldUnlockRead = false;
/*     */     try {
/* 183 */       pdfViewCtrl.docLockRead();
/* 184 */       shouldUnlockRead = true;
/* 185 */       PageLabel pageLabel = pdfViewCtrl.getDoc().getPageLabel(page);
/* 186 */       if (pageLabel.isValid()) {
/* 187 */         return pageLabel.getLabelTitle(page);
/*     */       }
/* 189 */     } catch (Exception e) {
/* 190 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */     } finally {
/* 192 */       if (shouldUnlockRead) {
/* 193 */         pdfViewCtrl.docUnlockRead();
/*     */       }
/*     */     } 
/* 196 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getPageNumberIndicator(@NonNull PDFViewCtrl pdfViewCtrl, int curPage) {
/* 207 */     return getPageNumberIndicator(pdfViewCtrl, curPage, pdfViewCtrl.getPageCount());
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
/*     */   public static String getPageNumberIndicator(@NonNull PDFViewCtrl pdfViewCtrl, int curPage, int pageCount) {
/* 219 */     boolean shouldUnlockRead = false;
/* 220 */     String pageRange = String.format(pdfViewCtrl.getContext().getResources().getString(R.string.page_range), new Object[] { Integer.valueOf(curPage), Integer.valueOf(pageCount) });
/*     */     try {
/* 222 */       pdfViewCtrl.docLockRead();
/* 223 */       shouldUnlockRead = true;
/* 224 */       String curPageStr = getPageLabelTitle(pdfViewCtrl, curPage);
/* 225 */       if (curPageStr != null) {
/* 226 */         pageRange = String.format(pdfViewCtrl.getContext().getResources().getString(R.string.page_label_range), new Object[] { curPageStr, Integer.valueOf(curPage), Integer.valueOf(pageCount) });
/*     */       }
/* 228 */     } catch (Exception e) {
/* 229 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */     } finally {
/* 231 */       if (shouldUnlockRead) {
/* 232 */         pdfViewCtrl.docUnlockRead();
/*     */       }
/*     */     } 
/* 235 */     return pageRange;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getPageNumberFromLabel(@NonNull PDFViewCtrl pdfViewCtrl, String pageLabel) {
/* 246 */     Collator collator = Collator.getInstance();
/*     */     
/* 248 */     boolean shouldUnlockRead = false;
/*     */     try {
/* 250 */       pdfViewCtrl.docLockRead();
/* 251 */       shouldUnlockRead = true;
/* 252 */       int page_num = pdfViewCtrl.getDoc().getPageCount();
/*     */       
/* 254 */       for (int i = 1; i <= page_num; i++) {
/* 255 */         PageLabel label = pdfViewCtrl.getDoc().getPageLabel(i);
/* 256 */         if (label.isValid()) {
/* 257 */           String labelStr = label.getLabelTitle(i);
/*     */           
/* 259 */           if (collator.equals(labelStr, pageLabel)) {
/* 260 */             return i;
/*     */           }
/*     */         } 
/*     */       } 
/* 264 */     } catch (Exception e) {
/* 265 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */     } finally {
/* 267 */       if (shouldUnlockRead) {
/* 268 */         pdfViewCtrl.docUnlockRead();
/*     */       }
/*     */     } 
/* 271 */     return -1;
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\dialog\pagelabel\PageLabelUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */