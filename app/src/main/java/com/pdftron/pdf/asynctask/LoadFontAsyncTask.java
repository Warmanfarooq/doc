/*     */ package com.pdftron.pdf.asynctask;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.content.SharedPreferences;
/*     */ import androidx.annotation.Nullable;
/*     */ import com.pdftron.pdf.PDFNet;
/*     */ import com.pdftron.pdf.config.ToolConfig;
/*     */ import com.pdftron.pdf.model.FontResource;
/*     */ import com.pdftron.pdf.tools.Tool;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.CustomAsyncTask;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import org.json.JSONArray;
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
/*     */ public class LoadFontAsyncTask
/*     */   extends CustomAsyncTask<Void, Void, ArrayList<FontResource>>
/*     */ {
/*     */   private ArrayList<FontResource> mFonts;
/*     */   private Set<String> mWhiteListFonts;
/*     */   private Callback mCallback;
/*     */   
/*     */   public LoadFontAsyncTask(Context context, Set<String> whiteListFonts) {
/*  55 */     super(context);
/*  56 */     this.mFonts = new ArrayList<>();
/*  57 */     this.mWhiteListFonts = whiteListFonts;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCallback(@Nullable Callback callback) {
/*  68 */     this.mCallback = callback;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadFontResources() {
/*     */     try {
/*  75 */       Context context = getContext();
/*  76 */       if (context == null) {
/*     */         return;
/*     */       }
/*  79 */       SharedPreferences settings = Tool.getToolPreferences(context);
/*     */       
/*  81 */       String fontInfo = settings.getString("annotation_property_free_text_fonts_list", "");
/*     */       
/*  83 */       if (fontInfo.equals("")) {
/*  84 */         fontInfo = PDFNet.getSystemFontList();
/*     */ 
/*     */         
/*  87 */         SharedPreferences.Editor editor = settings.edit();
/*  88 */         editor.putString("annotation_property_free_text_fonts_list", fontInfo);
/*  89 */         editor.apply();
/*     */       } 
/*     */       
/*  92 */       JSONObject fontInfoObject = new JSONObject(fontInfo);
/*  93 */       JSONArray fontInfoArray = fontInfoObject.getJSONArray("fonts");
/*     */       
/*  95 */       int numWhiteListFonts = 0;
/*  96 */       int numFoundWhiteListFonts = 0;
/*  97 */       if (this.mWhiteListFonts != null && !this.mWhiteListFonts.isEmpty()) {
/*  98 */         numWhiteListFonts = this.mWhiteListFonts.size();
/*     */       } else {
/* 100 */         this.mWhiteListFonts = new HashSet<>();
/* 101 */         fontInfo = FontResource.whiteListFonts(fontInfo);
/*     */         
/* 103 */         fontInfoObject = new JSONObject(fontInfo);
/* 104 */         fontInfoArray = fontInfoObject.getJSONArray("fonts");
/*     */ 
/*     */         
/* 107 */         for (int j = 0; j < fontInfoArray.length(); j++) {
/* 108 */           JSONObject font = fontInfoArray.getJSONObject(j);
/* 109 */           String entryValue = font.getString("filepath");
/*     */ 
/*     */           
/* 112 */           String whiteList = font.getString("display font");
/* 113 */           if (whiteList.equals("true")) {
/* 114 */             this.mWhiteListFonts.add(entryValue);
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 119 */       for (int i = 0; i < fontInfoArray.length(); i++) {
/*     */         
/* 121 */         JSONObject font = fontInfoArray.getJSONObject(i);
/* 122 */         String filePath = font.getString("filepath");
/*     */ 
/*     */ 
/*     */         
/* 126 */         if (this.mWhiteListFonts.contains(filePath)) {
/*     */           
/* 128 */           String displayName = font.getString("display name");
/* 129 */           if (displayName == null || displayName.equals("")) {
/* 130 */             displayName = Utils.getFontFileName(font.getString("filepath"));
/* 131 */             font.put("display name", displayName);
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 136 */           String fontName = "";
/* 137 */           if (font.has("font name")) {
/* 138 */             fontName = font.getString("font name");
/*     */           }
/*     */ 
/*     */           
/* 142 */           String pdfTronName = font.getString("pdftron name");
/* 143 */           FontResource fontResource = new FontResource(displayName, filePath, fontName, pdfTronName);
/* 144 */           this.mFonts.add(fontResource);
/*     */           
/* 146 */           numFoundWhiteListFonts++;
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 152 */       if (numFoundWhiteListFonts != numWhiteListFonts)
/*     */       {
/*     */         
/* 155 */         String fontSystemInfo = PDFNet.getSystemFontList();
/* 156 */         JSONObject fontSystemObject = new JSONObject(fontSystemInfo);
/* 157 */         JSONArray fontSystemArray = fontSystemObject.getJSONArray("fonts");
/*     */         
/* 159 */         for (int j = 0; j < fontSystemArray.length(); j++) {
/* 160 */           String fontSystemFilePath = fontSystemArray.getJSONObject(j).getString("filepath");
/* 161 */           Boolean found = Boolean.valueOf(false);
/*     */           
/* 163 */           for (int k = 0; k < fontInfoArray.length(); k++) {
/* 164 */             String fontInfoFilePath = fontInfoArray.getJSONObject(k).getString("filepath");
/*     */ 
/*     */ 
/*     */             
/* 168 */             if (fontSystemFilePath.equals(fontInfoFilePath)) {
/* 169 */               found = Boolean.valueOf(true);
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/* 174 */           if (!found.booleanValue()) {
/*     */             
/* 176 */             fontInfoArray.put(fontSystemArray.getJSONObject(j));
/*     */ 
/*     */             
/* 179 */             String filePath = fontSystemArray.getJSONObject(j).getString("filepath");
/* 180 */             if (this.mWhiteListFonts.contains(filePath)) {
/* 181 */               String displayName = fontSystemArray.getJSONObject(j).getString("display name");
/* 182 */               String pdftronName = fontSystemArray.getJSONObject(j).getString("pdftron name");
/* 183 */               FontResource fontResource = new FontResource(displayName, filePath, "", pdftronName);
/* 184 */               this.mFonts.add(fontResource);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 189 */         fontInfo = fontInfoObject.toString();
/* 190 */         SharedPreferences.Editor editor = settings.edit();
/* 191 */         editor.putString("annotation_property_free_text_fonts_list", fontInfo);
/* 192 */         editor.apply();
/*     */       }
/*     */     
/* 195 */     } catch (Exception e) {
/* 196 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */     } 
/*     */ 
/*     */     
/* 200 */     Collections.sort(this.mFonts, new Comparator<FontResource>()
/*     */         {
/*     */           public int compare(FontResource lhs, FontResource rhs) {
/* 203 */             return lhs.getDisplayName().compareTo(rhs.getDisplayName());
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public static interface Callback
/*     */   {
/*     */     void onFinish(ArrayList<FontResource> param1ArrayList);
/*     */   }
/*     */ 
/*     */   
/*     */   protected ArrayList<FontResource> doInBackground(Void... voids) {
/* 216 */     loadFontResources();
/* 217 */     return this.mFonts;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onPostExecute(ArrayList<FontResource> fontResources) {
/* 227 */     super.onPostExecute(fontResources);
/*     */     
/* 229 */     ToolConfig.getInstance().setFontList(fontResources);
/* 230 */     if (this.mCallback != null)
/* 231 */       this.mCallback.onFinish(this.mFonts); 
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\asynctask\LoadFontAsyncTask.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */