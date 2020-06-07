/*     */ package com.pdftron.pdf.utils;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.content.SharedPreferences;
/*     */ import android.util.Pair;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.fragment.app.FragmentActivity;
/*     */ import androidx.lifecycle.ViewModelProviders;
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.Annot;
/*     */ import com.pdftron.pdf.PDFDoc;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.Page;
/*     */ import com.pdftron.pdf.Point;
/*     */ import com.pdftron.pdf.QuadPoint;
/*     */ import com.pdftron.pdf.Rect;
/*     */ import com.pdftron.pdf.annots.Redaction;
/*     */ import com.pdftron.pdf.config.ToolStyleConfig;
/*     */ import com.pdftron.pdf.dialog.redaction.PageRedactionDialogFragment;
/*     */ import com.pdftron.pdf.dialog.redaction.SearchRedactionDialogFragment;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.tools.Tool;
/*     */ import com.pdftron.pdf.tools.ToolManager;
/*     */ import com.pdftron.pdf.viewmodel.RedactionEvent;
/*     */ import com.pdftron.pdf.viewmodel.RedactionViewModel;
/*     */ import com.pdftron.sdf.Doc;
/*     */ import io.reactivex.disposables.CompositeDisposable;
/*     */ import io.reactivex.functions.Consumer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ 
/*     */ public class RedactionManager
/*     */ {
/*     */   @NonNull
/*     */   private PDFViewCtrl mPdfViewCtrl;
/*  38 */   private CompositeDisposable mDisposables = new CompositeDisposable();
/*     */   
/*     */   public RedactionManager(@NonNull PDFViewCtrl pdfViewCtrl) {
/*  41 */     this.mPdfViewCtrl = pdfViewCtrl;
/*     */     
/*  43 */     ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/*  44 */     FragmentActivity activity = toolManager.getCurrentActivity();
/*  45 */     if (activity == null) {
/*     */       return;
/*     */     }
/*  48 */     RedactionViewModel redactionViewModel = (RedactionViewModel)ViewModelProviders.of(activity).get(RedactionViewModel.class);
/*     */     
/*  50 */     this.mDisposables.add(redactionViewModel.getObservable()
/*  51 */         .subscribe(new Consumer<RedactionEvent>()
/*     */           {
/*     */             public void accept(RedactionEvent redactionEvent) throws Exception {
/*  54 */               if (redactionEvent.getEventType() == RedactionEvent.Type.REDACT_BY_PAGE) {
/*  55 */                 ArrayList<Integer> pages = redactionEvent.getPages();
/*  56 */                 RedactionManager.this.redactPages(pages);
/*  57 */               } else if (redactionEvent.getEventType() == RedactionEvent.Type.REDACT_BY_SEARCH) {
/*  58 */                 ArrayList<Pair<Integer, ArrayList<Double>>> results = redactionEvent.getSearchResults();
/*  59 */                 RedactionManager.this.redactSearchResults(results);
/*  60 */               } else if (redactionEvent.getEventType() == RedactionEvent.Type.REDACT_BY_SEARCH_ITEM_CLICKED) {
/*     */               
/*     */               } 
/*     */             }
/*     */           }));
/*     */   }
/*     */ 
/*     */   
/*     */   public void destroy() {
/*  69 */     this.mDisposables.clear();
/*     */   }
/*     */   
/*     */   public void openPageRedactionDialog() {
/*  73 */     ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/*  74 */     FragmentActivity activity = toolManager.getCurrentActivity();
/*  75 */     if (activity == null) {
/*     */       return;
/*     */     }
/*  78 */     PageRedactionDialogFragment dialog = PageRedactionDialogFragment.newInstance(this.mPdfViewCtrl
/*  79 */         .getCurrentPage(), this.mPdfViewCtrl.getPageCount());
/*  80 */     dialog.setStyle(1, R.style.CustomAppTheme);
/*  81 */     dialog.show(activity.getSupportFragmentManager(), PageRedactionDialogFragment.TAG);
/*     */   }
/*     */   
/*     */   public void openRedactionBySearchDialog() {
/*  85 */     ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/*  86 */     FragmentActivity activity = toolManager.getCurrentActivity();
/*  87 */     if (activity == null) {
/*     */       return;
/*     */     }
/*  90 */     if (Utils.isLargeTablet((Context)activity)) {
/*  91 */       RedactionViewModel redactionViewModel = (RedactionViewModel)ViewModelProviders.of(activity).get(RedactionViewModel.class);
/*  92 */       redactionViewModel.onRedactBySearchOpenSheet();
/*     */     } else {
/*  94 */       SearchRedactionDialogFragment dialog = SearchRedactionDialogFragment.newInstance();
/*  95 */       dialog.setPdfViewCtrl(this.mPdfViewCtrl);
/*  96 */       dialog.setStyle(0, R.style.CustomAppTheme);
/*  97 */       dialog.show(activity.getSupportFragmentManager(), SearchRedactionDialogFragment.TAG);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void redactPages(@NonNull final ArrayList<Integer> pages) throws Exception {
/* 102 */     this.mPdfViewCtrl.docLock(true, new PDFViewCtrl.LockRunnable()
/*     */         {
/*     */           public void run() throws Exception {
/* 105 */             PDFDoc pdfDoc = RedactionManager.this.mPdfViewCtrl.getDoc();
/*     */             
/* 107 */             HashMap<Annot, Integer> annots = new HashMap<>(pages.size());
/* 108 */             for (Iterator<Integer> iterator = pages.iterator(); iterator.hasNext(); ) { int pageNum = ((Integer)iterator.next()).intValue();
/* 109 */               Page page = pdfDoc.getPage(pageNum);
/* 110 */               Redaction redaction = Redaction.create((Doc)pdfDoc, page.getCropBox());
/*     */               
/* 112 */               RedactionManager.this.setStyle(redaction);
/*     */               
/* 114 */               redaction.refreshAppearance();
/* 115 */               page.annotPushBack((Annot)redaction);
/*     */               
/* 117 */               RedactionManager.this.mPdfViewCtrl.update((Annot)redaction, pageNum);
/* 118 */               annots.put(redaction, Integer.valueOf(pageNum)); }
/*     */ 
/*     */ 
/*     */             
/* 122 */             if (!annots.isEmpty()) {
/* 123 */               ToolManager toolManager = (ToolManager)RedactionManager.this.mPdfViewCtrl.getToolManager();
/* 124 */               toolManager.raiseAnnotationsAddedEvent(annots);
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void redactSearchResults(@NonNull final ArrayList<Pair<Integer, ArrayList<Double>>> searchResults) throws Exception {
/* 131 */     this.mPdfViewCtrl.docLock(true, new PDFViewCtrl.LockRunnable()
/*     */         {
/*     */           public void run() throws Exception {
/* 134 */             PDFDoc pdfDoc = RedactionManager.this.mPdfViewCtrl.getDoc();
/* 135 */             ToolManager toolManager = (ToolManager)RedactionManager.this.mPdfViewCtrl.getToolManager();
/*     */             
/* 137 */             HashMap<Annot, Integer> annots = new HashMap<>(searchResults.size());
/*     */             
/* 139 */             for (Pair<Integer, ArrayList<Double>> pair : (Iterable<Pair<Integer, ArrayList<Double>>>)searchResults) {
/* 140 */               int pageNum = ((Integer)pair.first).intValue();
/* 141 */               ArrayList<Double> quadList = (ArrayList<Double>)pair.second;
/* 142 */               if (quadList == null) {
/*     */                 continue;
/*     */               }
/* 145 */               Double[] quads = quadList.<Double>toArray(new Double[0]);
/*     */               
/* 147 */               int quad_count = quads.length / 8;
/*     */               
/* 149 */               if (quad_count == 0) {
/*     */                 continue;
/*     */               }
/*     */               
/* 153 */               Rect bbox = new Rect(quads[0].doubleValue(), quads[1].doubleValue(), quads[4].doubleValue(), quads[5].doubleValue());
/* 154 */               Redaction redaction = Redaction.create((Doc)pdfDoc, bbox);
/* 155 */               Page page = pdfDoc.getPage(pageNum);
/* 156 */               page.annotPushBack((Annot)redaction);
/*     */               
/* 158 */               boolean useAdobeHack = toolManager.isTextMarkupAdobeHack();
/*     */               
/* 160 */               Point p1 = new Point();
/* 161 */               Point p2 = new Point();
/* 162 */               Point p3 = new Point();
/* 163 */               Point p4 = new Point();
/* 164 */               QuadPoint qp = new QuadPoint(p1, p2, p3, p4);
/*     */               
/* 166 */               int k = 0;
/*     */               
/* 168 */               double left = 0.0D;
/* 169 */               double right = 0.0D;
/* 170 */               double top = 0.0D;
/* 171 */               double bottom = 0.0D;
/* 172 */               for (int i = 0; i < quad_count; i++, k += 8) {
/* 173 */                 p1.x = quads[k].doubleValue();
/* 174 */                 p1.y = quads[k + 1].doubleValue();
/*     */                 
/* 176 */                 p2.x = quads[k + 2].doubleValue();
/* 177 */                 p2.y = quads[k + 3].doubleValue();
/*     */                 
/* 179 */                 p3.x = quads[k + 4].doubleValue();
/* 180 */                 p3.y = quads[k + 5].doubleValue();
/*     */                 
/* 182 */                 p4.x = quads[k + 6].doubleValue();
/* 183 */                 p4.y = quads[k + 7].doubleValue();
/*     */                 
/* 185 */                 if (useAdobeHack) {
/* 186 */                   qp.p1 = p4;
/* 187 */                   qp.p2 = p3;
/* 188 */                   qp.p3 = p1;
/* 189 */                   qp.p4 = p2;
/*     */                 } else {
/* 191 */                   qp.p1 = p1;
/* 192 */                   qp.p2 = p2;
/* 193 */                   qp.p3 = p3;
/* 194 */                   qp.p4 = p4;
/*     */                 } 
/* 196 */                 redaction.setQuadPoint(i, qp);
/*     */               } 
/* 198 */               RedactionManager.this.setStyle(redaction);
/* 199 */               redaction.refreshAppearance();
/*     */               
/* 201 */               RedactionManager.this.mPdfViewCtrl.update((Annot)redaction, pageNum);
/* 202 */               annots.put(redaction, Integer.valueOf(pageNum));
/*     */             } 
/*     */ 
/*     */             
/* 206 */             if (!annots.isEmpty()) {
/* 207 */               toolManager.raiseAnnotationsAddedEvent(annots);
/*     */             }
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private void setStyle(@NonNull Redaction redaction) throws PDFNetException {
/* 215 */     SharedPreferences settings = Tool.getToolPreferences(this.mPdfViewCtrl.getContext());
/* 216 */     float thickness = settings.getFloat(ToolStyleConfig.getInstance().getThicknessKey(25, ""), 
/* 217 */         ToolStyleConfig.getInstance().getDefaultThickness(this.mPdfViewCtrl.getContext(), 25));
/* 218 */     int strokeColor = settings.getInt(ToolStyleConfig.getInstance().getColorKey(25, ""), 
/* 219 */         ToolStyleConfig.getInstance().getDefaultColor(this.mPdfViewCtrl.getContext(), 25));
/* 220 */     int fillColor = settings.getInt(ToolStyleConfig.getInstance().getFillColorKey(25, ""), 
/* 221 */         ToolStyleConfig.getInstance().getDefaultFillColor(this.mPdfViewCtrl.getContext(), 25));
/* 222 */     float opacity = settings.getFloat(ToolStyleConfig.getInstance().getOpacityKey(25, ""), 
/* 223 */         ToolStyleConfig.getInstance().getDefaultOpacity(this.mPdfViewCtrl.getContext(), 25));
/*     */ 
/*     */     
/* 226 */     AnnotUtils.setStyle((Annot)redaction, true, strokeColor, fillColor, thickness, opacity);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pd\\utils\RedactionManager.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */