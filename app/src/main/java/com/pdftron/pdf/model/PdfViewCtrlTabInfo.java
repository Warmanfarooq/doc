/*     */ package com.pdftron.pdf.model;
/*     */ 
/*     */ import androidx.annotation.Nullable;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import org.json.JSONObject;
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
/*     */ public class PdfViewCtrlTabInfo
/*     */ {
/*     */   private static final String VAR_TAB_TITLE = "tabTitle";
/*     */   private static final String VAR_TAB_SOURCE = "tabSource";
/*     */   private static final String VAR_H_SCROLL_POS = "hScrollPos";
/*     */   private static final String VAR_V_SCROLL_POS = "vScrollPos";
/*     */   private static final String VAR_ZOOM = "zoom";
/*     */   private static final String VAR_LAST_PAGE = "lastPage";
/*     */   private static final String VAR_PAGE_ROTATION = "pageRotation";
/*     */   private static final String VAR_PAGE_PRESENTATION_MODE = "pagePresentationMode";
/*     */   private static final String VAR_TAB_LAST_VIEWED_TIMESTAMP = "tabLastViewedTimestamp";
/*     */   private static final String VAR_PASSWORD = "password";
/*     */   private static final String VAR_FILE_EXTENSION = "fileExtension";
/*     */   private static final String VAR_IS_REFLOW_MODE = "isReflowMode";
/*     */   private static final String VAR_REFLOW_TEXT_SIZE = "reflowTextSize";
/*     */   private static final String VAR_IS_RTL_MODE = "isRtlMode";
/*     */   private static final String VAR_BOOKMARK_DIALOG_CURRENT_TAB = "bookmarkDialogCurrentTab";
/*     */   public String tabTitle;
/*     */   public int tabSource;
/*     */   public int hScrollPos;
/*     */   public int vScrollPos;
/*     */   public double zoom;
/*     */   public int lastPage;
/*     */   public int pageRotation;
/*  74 */   public int pagePresentationMode = PDFViewCtrl.PagePresentationMode.SINGLE.getValue();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String tabLastViewedTimestamp;
/*     */ 
/*     */ 
/*     */   
/*     */   public String password;
/*     */ 
/*     */ 
/*     */   
/*     */   public String fileExtension;
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isReflowMode;
/*     */ 
/*     */ 
/*     */   
/*     */   public int reflowTextSize;
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRtlMode;
/*     */ 
/*     */ 
/*     */   
/* 103 */   public int bookmarkDialogCurrentTab = -1;
/*     */ 
/*     */   
/*     */   public PdfViewCtrlTabInfo() {}
/*     */ 
/*     */   
/*     */   public PdfViewCtrlTabInfo(JSONObject jsonObject) {
/* 110 */     if (!jsonObject.has("tabTitle")) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/* 115 */       this.tabTitle = jsonObject.getString("tabTitle");
/* 116 */       this.tabSource = jsonObject.getInt("tabSource");
/* 117 */       this.hScrollPos = jsonObject.getInt("hScrollPos");
/* 118 */       this.vScrollPos = jsonObject.getInt("vScrollPos");
/* 119 */       this.zoom = jsonObject.getDouble("zoom");
/* 120 */       this.lastPage = jsonObject.getInt("lastPage");
/* 121 */       this.pageRotation = jsonObject.getInt("pageRotation");
/* 122 */       this.pagePresentationMode = jsonObject.getInt("pagePresentationMode");
/*     */       
/* 124 */       if (jsonObject.has("tabLastViewedTimestamp")) {
/* 125 */         this.tabLastViewedTimestamp = jsonObject.getString("tabLastViewedTimestamp");
/*     */       }
/*     */       
/* 128 */       if (jsonObject.has("password")) {
/* 129 */         this.password = jsonObject.getString("password");
/*     */       }
/*     */       
/* 132 */       if (jsonObject.has("fileExtension")) {
/* 133 */         this.fileExtension = jsonObject.getString("fileExtension");
/*     */       }
/* 135 */       if (jsonObject.has("isReflowMode")) {
/* 136 */         this.isReflowMode = jsonObject.getBoolean("isReflowMode");
/*     */       }
/*     */       
/* 139 */       if (jsonObject.has("reflowTextSize")) {
/* 140 */         this.reflowTextSize = jsonObject.getInt("reflowTextSize");
/*     */       }
/*     */       
/* 143 */       if (jsonObject.has("isRtlMode")) {
/* 144 */         this.isRtlMode = jsonObject.getBoolean("isRtlMode");
/*     */       }
/*     */       
/* 147 */       if (jsonObject.has("bookmarkDialogCurrentTab")) {
/* 148 */         this.bookmarkDialogCurrentTab = jsonObject.getInt("bookmarkDialogCurrentTab");
/*     */       }
/* 150 */     } catch (Exception e) {
/* 151 */       AnalyticsHandlerAdapter.getInstance().sendException(e, "\nJson from: " + jsonObject);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public PDFViewCtrl.PagePresentationMode getPagePresentationMode() {
/* 161 */     return PDFViewCtrl.PagePresentationMode.valueOf(this.pagePresentationMode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPagePresentationMode(@Nullable PDFViewCtrl.PagePresentationMode pagePresentationMode) {
/* 171 */     if (pagePresentationMode == null) {
/* 172 */       this.pagePresentationMode = 0;
/*     */     } else {
/* 174 */       this.pagePresentationMode = pagePresentationMode.getValue();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasPagePresentationMode() {
/* 182 */     return (this.pagePresentationMode > 0);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\model\PdfViewCtrlTabInfo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */