/*    */ package com.pdftron.pdf.utils;
/*    */ 
/*    */ import android.content.Context;
/*    */ import java.io.IOException;
/*    */ import java.net.HttpURLConnection;
/*    */ import java.net.MalformedURLException;
/*    */ import java.net.URL;
/*    */ import java.util.Iterator;
/*    */ import org.json.JSONObject;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BasicHeadRequestTask
/*    */   extends CustomAsyncTask<String, Void, Boolean>
/*    */ {
/*    */   private String mURL;
/*    */   private BasicHeadRequestTaskListener mListener;
/*    */   private JSONObject mCustomHeaders;
/*    */   private String mResult;
/*    */   
/*    */   public BasicHeadRequestTask(Context context, BasicHeadRequestTaskListener listener, String url) {
/* 26 */     this(context, listener, url, null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BasicHeadRequestTask(Context context, BasicHeadRequestTaskListener listener, String url, JSONObject customHeaders) {
/* 36 */     super(context);
/* 37 */     this.mURL = url;
/* 38 */     this.mListener = listener;
/* 39 */     this.mCustomHeaders = customHeaders;
/*    */   }
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
/*    */   protected Boolean doInBackground(String... params) {
/* 52 */     HttpURLConnection urlConnection = null;
/*    */     try {
/* 54 */       URL url = new URL(this.mURL);
/* 55 */       urlConnection = (HttpURLConnection)url.openConnection();
/* 56 */       urlConnection.setRequestMethod("HEAD");
/* 57 */       if (this.mCustomHeaders != null) {
/* 58 */         Iterator<String> iter = this.mCustomHeaders.keys();
/* 59 */         while (iter.hasNext()) {
/* 60 */           String key = iter.next();
/* 61 */           String val = this.mCustomHeaders.optString(key);
/* 62 */           if (!Utils.isNullOrEmpty(val)) {
/* 63 */             urlConnection.setRequestProperty(key, val);
/*    */           }
/*    */         } 
/*    */       } 
/* 67 */       this.mResult = urlConnection.getContentType();
/* 68 */     } catch (MalformedURLException e) {
/* 69 */       return Boolean.valueOf(false);
/* 70 */     } catch (IOException e) {
/* 71 */       return Boolean.valueOf(false);
/*    */     } finally {
/* 73 */       if (urlConnection != null) {
/* 74 */         urlConnection.disconnect();
/*    */       }
/*    */     } 
/*    */     
/* 78 */     return Boolean.valueOf(true);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void onPostExecute(Boolean pass) {
/* 89 */     this.mListener.onHeadRequestTask(pass, this.mResult);
/*    */   }
/*    */   
/*    */   public static interface BasicHeadRequestTaskListener {
/*    */     void onHeadRequestTask(Boolean param1Boolean, String param1String);
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pd\\utils\BasicHeadRequestTask.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */