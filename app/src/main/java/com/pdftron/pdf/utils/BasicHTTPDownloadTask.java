/*     */ package com.pdftron.pdf.utils;
/*     */ 
/*     */ import android.content.Context;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.util.Iterator;
/*     */ import org.apache.commons.io.IOUtils;
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
/*     */ public class BasicHTTPDownloadTask
/*     */   extends CustomAsyncTask<String, Void, Boolean>
/*     */ {
/*     */   private String mURL;
/*     */   private File mSaveFile;
/*     */   private JSONObject mCustomHeaders;
/*     */   private BasicHTTPDownloadTaskListener mListener;
/*     */   
/*     */   public BasicHTTPDownloadTask(Context context, BasicHTTPDownloadTaskListener listener, String url, File saveFile) {
/*  40 */     this(context, listener, url, null, saveFile);
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
/*     */   public BasicHTTPDownloadTask(Context context, BasicHTTPDownloadTaskListener listener, String url, JSONObject customHeaders, File saveFile) {
/*  52 */     super(context);
/*  53 */     this.mURL = url;
/*  54 */     this.mSaveFile = saveFile;
/*  55 */     this.mListener = listener;
/*  56 */     this.mCustomHeaders = customHeaders;
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
/*     */   protected Boolean doInBackground(String... params) {
/*  69 */     HttpURLConnection urlConnection = null;
/*  70 */     OutputStream filestream = null;
/*     */     try {
/*  72 */       URL url = new URL(this.mURL);
/*  73 */       urlConnection = (HttpURLConnection)url.openConnection();
/*  74 */       if (this.mCustomHeaders != null) {
/*  75 */         Iterator<String> iter = this.mCustomHeaders.keys();
/*  76 */         while (iter.hasNext()) {
/*  77 */           String key = iter.next();
/*  78 */           String val = this.mCustomHeaders.optString(key);
/*  79 */           if (!Utils.isNullOrEmpty(val)) {
/*  80 */             urlConnection.setRequestProperty(key, val);
/*     */           }
/*     */         } 
/*     */       } 
/*  84 */       filestream = new BufferedOutputStream(new FileOutputStream(this.mSaveFile));
/*  85 */       IOUtils.copy(urlConnection.getInputStream(), filestream);
/*  86 */     } catch (MalformedURLException e) {
/*  87 */       return Boolean.valueOf(false);
/*  88 */     } catch (IOException e) {
/*  89 */       return Boolean.valueOf(false);
/*     */     } finally {
/*  91 */       IOUtils.closeQuietly(filestream);
/*  92 */       if (urlConnection != null) {
/*  93 */         urlConnection.disconnect();
/*     */       }
/*     */     } 
/*     */     
/*  97 */     return Boolean.valueOf(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onPostExecute(Boolean pass) {
/* 108 */     this.mListener.onDownloadTask(pass, this.mSaveFile);
/*     */   }
/*     */   
/*     */   public static interface BasicHTTPDownloadTaskListener {
/*     */     void onDownloadTask(Boolean param1Boolean, File param1File);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pd\\utils\BasicHTTPDownloadTask.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */