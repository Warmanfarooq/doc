/*     */ package com.pdftron.pdf.model;
/*     */ 
/*     */ import com.pdftron.pdf.tools.Tool;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import org.json.JSONArray;
/*     */ import org.json.JSONObject;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FontResource
/*     */ {
/*  13 */   private String mDisplayName = "";
/*  14 */   private String mFontName = "";
/*  15 */   private String mFilePath = "";
/*  16 */   private String mPDFTronName = "";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FontResource(String displayName, String filePath, String fontName, String pdftronName) {
/*  27 */     if (displayName != null) {
/*  28 */       this.mDisplayName = displayName;
/*     */     }
/*  30 */     if (filePath != null) {
/*  31 */       this.mFilePath = filePath;
/*     */     }
/*  33 */     if (fontName != null) {
/*  34 */       this.mFontName = fontName;
/*     */     }
/*  36 */     if (pdftronName != null) {
/*  37 */       this.mPDFTronName = pdftronName;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FontResource(String fontName) {
/*  46 */     if (fontName == null) {
/*     */       return;
/*     */     }
/*  49 */     this.mFontName = fontName;
/*  50 */     this.mDisplayName = fontName;
/*  51 */     this.mPDFTronName = fontName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDisplayName(String displayName) {
/*  59 */     this.mDisplayName = displayName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFontName(String fontName) {
/*  67 */     this.mFontName = fontName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFilePath(String filePath) {
/*  75 */     this.mFilePath = filePath;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPDFTronName(String PDFTronName) {
/*  83 */     this.mPDFTronName = PDFTronName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDisplayName() {
/*  91 */     return this.mDisplayName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFilePath() {
/*  99 */     return this.mFilePath;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFontName() {
/* 107 */     return this.mFontName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPDFTronName() {
/* 115 */     return this.mPDFTronName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean hasFontName() {
/* 123 */     return Boolean.valueOf(!Utils.isNullOrEmpty(this.mFontName));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean hasFilePath() {
/* 131 */     return Boolean.valueOf(!Utils.isNullOrEmpty(this.mFilePath));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean hasPDFTronName() {
/* 139 */     return Boolean.valueOf(!Utils.isNullOrEmpty(this.mPDFTronName));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 146 */     return (!hasFontName().booleanValue() && !hasFilePath().booleanValue() && !hasPDFTronName().booleanValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 155 */     return "FontResource{mDisplayName='" + this.mDisplayName + '\'' + ", mFontName='" + this.mFontName + '\'' + ", mFilePath='" + this.mFilePath + '\'' + ", mPDFTronName='" + this.mPDFTronName + '\'' + '}';
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
/*     */   public boolean equals(Object obj) {
/* 170 */     if (obj instanceof FontResource) {
/* 171 */       FontResource other = (FontResource)obj;
/* 172 */       if (other.hasPDFTronName().booleanValue() && hasPDFTronName().booleanValue()) {
/* 173 */         return other.getPDFTronName().equals(getPDFTronName());
/*     */       }
/* 175 */       if (other.hasFilePath().booleanValue() && hasFilePath().booleanValue()) {
/* 176 */         return other.getFilePath().equals(getFilePath());
/*     */       }
/* 178 */       if (other.hasFontName().booleanValue() && hasFontName().booleanValue()) {
/* 179 */         return other.getFontName().equals(getFontName());
/*     */       }
/* 181 */       if (other.isEmpty() && isEmpty()) {
/* 182 */         return true;
/*     */       }
/*     */     } 
/* 185 */     return super.equals(obj);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getFileName(String filePath) {
/* 190 */     String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
/*     */     
/* 192 */     int lastPeriodPos = fileName.lastIndexOf(".");
/* 193 */     if (lastPeriodPos > 0) {
/* 194 */       return fileName.substring(0, lastPeriodPos);
/*     */     }
/* 196 */     return fileName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String whiteListFonts(String fontInfo) {
/*     */     try {
/* 207 */       JSONObject systemFontObject = new JSONObject(fontInfo);
/* 208 */       JSONArray systemFontArray = systemFontObject.getJSONArray("fonts");
/*     */       
/* 210 */       for (int i = 0; i < systemFontArray.length(); i++) {
/* 211 */         JSONObject font = systemFontArray.getJSONObject(i);
/* 212 */         String displayName = font.getString("display name");
/* 213 */         String fontFilePath = font.getString("filepath");
/*     */ 
/*     */         
/* 216 */         if (displayName == null || displayName.equals("")) {
/* 217 */           displayName = getFileName(font.getString("filepath"));
/* 218 */           font.put("display name", displayName);
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 223 */         Boolean whiteListFont = Boolean.valueOf(whiteListFont(displayName, fontFilePath));
/* 224 */         font.put("display font", whiteListFont);
/*     */       } 
/*     */       
/* 227 */       fontInfo = systemFontObject.toString();
/* 228 */     } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */     
/* 232 */     return fontInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean whiteListFont(String fontName, String fontFilePath) {
/* 243 */     String[] whiteList = Tool.ANNOTATION_FREE_TEXT_WHITELIST_FONTS;
/* 244 */     int size = whiteList.length;
/* 245 */     for (String aWhiteList : whiteList) {
/* 246 */       if (fontName.contains(aWhiteList)) {
/* 247 */         return true;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 252 */     String dir1 = "/system/fonts";
/* 253 */     String dir2 = "/system/font";
/* 254 */     String dir3 = "/data/fonts";
/*     */     
/* 256 */     return (!fontFilePath.contains(dir1) && !fontFilePath.contains(dir2) && !fontFilePath.contains(dir3));
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\model\FontResource.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */