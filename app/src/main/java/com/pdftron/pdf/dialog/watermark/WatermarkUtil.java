/*    */ package com.pdftron.pdf.dialog.watermark;
/*    */ 
/*    */ import androidx.annotation.ColorInt;
/*    */ import androidx.annotation.NonNull;
/*    */ import androidx.annotation.Nullable;
/*    */ import com.pdftron.pdf.ColorPt;
/*    */ import com.pdftron.pdf.PDFDoc;
/*    */ import com.pdftron.pdf.PDFViewCtrl;
/*    */ import com.pdftron.pdf.PageSet;
/*    */ import com.pdftron.pdf.Stamper;
/*    */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*    */ import com.pdftron.pdf.utils.Utils;
/*    */ import java.util.Calendar;
/*    */ import java.util.TimeZone;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WatermarkUtil
/*    */ {
/*    */   public static void setTextWatermark(@NonNull PDFViewCtrl pdfViewCtrl, @Nullable String watermarkText, @ColorInt int textColor, float textOpacity, float textSize, boolean showTimestamp) {
/* 29 */     boolean shouldUnlock = false;
/*    */     try {
/* 31 */       pdfViewCtrl.docLock(true);
/* 32 */       shouldUnlock = true;
/* 33 */       PDFDoc doc = pdfViewCtrl.getDoc();
/* 34 */       PageSet ps = new PageSet(1, pdfViewCtrl.getPageCount(), 0);
/*    */ 
/*    */       
/* 37 */       int rot = -45;
/* 38 */       ColorPt colorPt = Utils.color2ColorPt(textColor);
/*    */ 
/*    */       
/* 41 */       Stamper watermark = new Stamper(3, textSize, 0.05D);
/* 42 */       watermark.setPosition(0.0D, 0.0D);
/* 43 */       watermark.setFontColor(colorPt);
/* 44 */       watermark.setOpacity(textOpacity);
/* 45 */       watermark.setRotation(rot);
/* 46 */       watermark.setTextAlignment(0);
/* 47 */       watermark.stampText(doc, watermarkText, ps);
/*    */ 
/*    */       
/* 50 */       if (showTimestamp) {
/* 51 */         Calendar cal = Calendar.getInstance();
/* 52 */         String date = cal.getTime().toString();
/* 53 */         String timeZone = TimeZone.getDefault().getDisplayName();
/* 54 */         String timeStampStr = String.format("%s \n (%s)", new Object[] { date, timeZone });
/* 55 */         Stamper timeStamp = new Stamper(3, 24.0D, 0.05D);
/* 56 */         timeStamp.setPosition(0.0D, 0.0D);
/* 57 */         timeStamp.setFontColor(colorPt);
/* 58 */         timeStamp.setOpacity(textOpacity);
/* 59 */         timeStamp.setTextAlignment(0);
/* 60 */         timeStamp.setAlignment(0, 1);
/* 61 */         timeStamp.stampText(doc, timeStampStr, ps);
/*    */       } 
/*    */       
/* 64 */       pdfViewCtrl.update(true);
/* 65 */     } catch (Exception e) {
/* 66 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*    */     } finally {
/* 68 */       if (shouldUnlock) {
/* 69 */         pdfViewCtrl.docUnlock();
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void clearWatermark(@NonNull PDFViewCtrl pdfViewCtrl) {
/* 78 */     boolean shouldUnlock = false;
/*    */     try {
/* 80 */       pdfViewCtrl.docLock(true);
/* 81 */       shouldUnlock = true;
/* 82 */       PDFDoc doc = pdfViewCtrl.getDoc();
/*    */       
/* 84 */       PageSet ps = new PageSet(1, pdfViewCtrl.getPageCount(), 0);
/* 85 */       Stamper.deleteStamps(doc, ps);
/* 86 */       pdfViewCtrl.update(true);
/*    */     }
/* 88 */     catch (Exception e) {
/* 89 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*    */     } finally {
/* 91 */       if (shouldUnlock)
/* 92 */         pdfViewCtrl.docUnlock(); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\dialog\watermark\WatermarkUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */