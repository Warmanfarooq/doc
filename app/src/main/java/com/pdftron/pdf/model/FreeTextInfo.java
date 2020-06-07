/*    */ package com.pdftron.pdf.model;
/*    */ 
/*    */ import android.content.Context;
/*    */ import android.content.SharedPreferences;
/*    */ import com.pdftron.common.PDFNetException;
/*    */ import com.pdftron.pdf.Annot;
/*    */ import com.pdftron.pdf.Font;
/*    */ import com.pdftron.pdf.PDFViewCtrl;
/*    */ import com.pdftron.pdf.annots.FreeText;
/*    */ import com.pdftron.pdf.tools.Tool;
/*    */ import com.pdftron.sdf.Doc;
/*    */ import com.pdftron.sdf.Obj;
/*    */ import org.json.JSONArray;
/*    */ import org.json.JSONException;
/*    */ import org.json.JSONObject;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FreeTextInfo
/*    */ {
/*    */   public int mAnnotIndex;
/*    */   public int mPageIndex;
/*    */   public String mFontName;
/*    */   
/*    */   public FreeTextInfo(int mAnnotIndex, int mPageIndex, String mFontName) {
/* 29 */     this.mAnnotIndex = mAnnotIndex;
/* 30 */     this.mPageIndex = mPageIndex;
/* 31 */     this.mFontName = mFontName;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setFont(Context context, PDFViewCtrl pdfViewCtrl) throws PDFNetException, JSONException {
/* 36 */     Annot annot = pdfViewCtrl.getDoc().getPage(this.mPageIndex).getAnnot(this.mAnnotIndex);
/* 37 */     if (annot != null) {
/* 38 */       FreeText freeText = new FreeText(annot);
/* 39 */       setFont(pdfViewCtrl, freeText, this.mFontName);
/* 40 */       freeText.refreshAppearance();
/* 41 */       pdfViewCtrl.update((Annot)freeText, this.mPageIndex);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void setFont(PDFViewCtrl pdfViewCtrl, FreeText freeText, String pdfFontName) throws JSONException, PDFNetException {
/* 49 */     if (pdfFontName != null && !pdfFontName.equals("")) {
/* 50 */       String fontDRName = "F0";
/*    */ 
/*    */       
/* 53 */       Obj annotObj = freeText.getSDFObj();
/* 54 */       Obj drDict = annotObj.putDict("DR");
/*    */ 
/*    */       
/* 57 */       Obj fontDict = drDict.putDict("Font");
/* 58 */       Font font = Font.create((Doc)pdfViewCtrl.getDoc(), pdfFontName, freeText.getContents());
/* 59 */       fontDict.put(fontDRName, font.GetSDFObj());
/* 60 */       String fontName = font.getName();
/*    */ 
/*    */       
/* 63 */       String DA = freeText.getDefaultAppearance();
/* 64 */       int slashPosition = DA.indexOf("/", 0);
/*    */ 
/*    */       
/* 67 */       if (slashPosition > 0) {
/* 68 */         String beforeSlash = DA.substring(0, slashPosition);
/* 69 */         String afterSlash = DA.substring(slashPosition);
/* 70 */         String afterFont = afterSlash.substring(afterSlash.indexOf(" "));
/* 71 */         String updatedDA = beforeSlash + "/" + fontDRName + afterFont;
/*    */         
/* 73 */         freeText.setDefaultAppearance(updatedDA);
/* 74 */         freeText.refreshAppearance();
/*    */       } 
/*    */ 
/*    */       
/* 78 */       SharedPreferences settings = Tool.getToolPreferences(pdfViewCtrl.getContext());
/* 79 */       String fontInfo = settings.getString("annotation_property_free_text_fonts_list", "");
/* 80 */       if (!fontInfo.equals("")) {
/* 81 */         JSONObject systemFontObject = new JSONObject(fontInfo);
/* 82 */         JSONArray systemFontArray = systemFontObject.getJSONArray("fonts");
/*    */         
/* 84 */         for (int i = 0; i < systemFontArray.length(); i++) {
/* 85 */           JSONObject fontObj = systemFontArray.getJSONObject(i);
/*    */           
/* 87 */           if (fontObj.getString("pdftron name").equals(pdfFontName)) {
/* 88 */             fontObj.put("font name", fontName);
/*    */             break;
/*    */           } 
/*    */         } 
/* 92 */         fontInfo = systemFontObject.toString();
/*    */       } 
/*    */       
/* 95 */       SharedPreferences.Editor editor = settings.edit();
/* 96 */       editor.putString("annotation_property_free_text_fonts_list", fontInfo);
/* 97 */       editor.putString(Tool.getFontKey(2), pdfFontName);
/* 98 */       editor.apply();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\model\FreeTextInfo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */