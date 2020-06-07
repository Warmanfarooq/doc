/*     */ package com.pdftron.pdf.dialog.pdflayer;
/*     */ 
/*     */ import androidx.annotation.Nullable;
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.PDFDoc;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.ocg.Config;
/*     */ import com.pdftron.pdf.ocg.Context;
/*     */ import com.pdftron.pdf.ocg.Group;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import com.pdftron.sdf.Obj;
/*     */ import java.util.ArrayList;
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
/*     */ public class PdfLayerUtils
/*     */ {
/*     */   public static ArrayList<LayerInfo> getLayers(@Nullable PDFViewCtrl pdfViewCtrl, @Nullable PDFDoc pdfDoc) throws PDFNetException {
/*  31 */     if (null == pdfDoc) {
/*  32 */       return new ArrayList<>();
/*     */     }
/*  34 */     ArrayList<LayerInfo> result = new ArrayList<>();
/*  35 */     Config cfg = pdfDoc.getOCGConfig();
/*  36 */     Obj ocgs = cfg.getOrder();
/*  37 */     if (ocgs != null) {
/*  38 */       int sz = (int)ocgs.size();
/*  39 */       for (int i = 0; i < sz; i++) {
/*  40 */         Group ocg = new Group(ocgs.getAt(i));
/*  41 */         result.add(new LayerInfo(ocg, getChecked(pdfViewCtrl, ocg)));
/*     */       } 
/*     */     } 
/*  44 */     return result;
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
/*     */   public static boolean getChecked(@Nullable PDFViewCtrl pdfViewCtrl, @Nullable Group group) throws PDFNetException {
/*  56 */     if (pdfViewCtrl == null || group == null) {
/*  57 */       return false;
/*     */     }
/*  59 */     Context ctx = pdfViewCtrl.getOCGContext();
/*  60 */     return ctx.getState(group);
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
/*     */   public static void setLayerCheckedChange(@Nullable PDFViewCtrl pdfViewCtrl, @Nullable Group group, boolean checked) throws PDFNetException {
/*  72 */     if (pdfViewCtrl == null || group == null) {
/*     */       return;
/*     */     }
/*  75 */     Context ctx = pdfViewCtrl.getOCGContext();
/*  76 */     ctx.setState(group, checked);
/*  77 */     pdfViewCtrl.update(true);
/*     */   }
/*     */   
/*     */   public static boolean hasPdfLayer(PDFDoc doc) {
/*  81 */     boolean shouldUnlockRead = false;
/*     */     try {
/*  83 */       doc.lockRead();
/*  84 */       shouldUnlockRead = true;
/*  85 */       return doc.hasOC();
/*  86 */     } catch (PDFNetException e) {
/*  87 */       AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*     */     } finally {
/*  89 */       if (shouldUnlockRead) {
/*  90 */         Utils.unlockReadQuietly(doc);
/*     */       }
/*     */     } 
/*  93 */     return false;
/*     */   }
/*     */   
/*     */   public static class LayerInfo {
/*     */     public Group group;
/*     */     public boolean checked;
/*     */     
/*     */     LayerInfo(Group group, boolean checked) {
/* 101 */       this.group = group;
/* 102 */       this.checked = checked;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\dialog\pdflayer\PdfLayerUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */