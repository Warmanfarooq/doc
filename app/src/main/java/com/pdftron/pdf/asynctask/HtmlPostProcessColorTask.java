/*     */ package com.pdftron.pdf.asynctask;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.os.Environment;
/*     */ import android.os.StatFs;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.fragment.app.FragmentActivity;
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.ColorPt;
/*     */ import com.pdftron.pdf.controls.ReflowControl;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.CustomAsyncTask;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
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
/*     */ public class HtmlPostProcessColorTask
/*     */   extends CustomAsyncTask<Void, Void, Void>
/*     */ {
/*     */   private String mHtmlFilename;
/*     */   private ReflowControl.OnPostProcessColorListener mOnPostProcessColorListener;
/*     */   private Callback mCallback;
/*     */   
/*     */   public HtmlPostProcessColorTask(Context context, @NonNull String htmlFilename) {
/*  63 */     super(context);
/*  64 */     this.mHtmlFilename = htmlFilename;
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
/*  75 */     this.mCallback = callback;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOnPostProcessColorListener(@Nullable ReflowControl.OnPostProcessColorListener listener) {
/*  86 */     this.mOnPostProcessColorListener = listener;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Void doInBackground(Void... voids) {
/*  94 */     if (Utils.isNullOrEmpty(this.mHtmlFilename) || this.mOnPostProcessColorListener == null) {
/*  95 */       return null;
/*     */     }
/*  97 */     InputStream inputStream = null;
/*  98 */     OutputStream outputStream = null;
/*     */     
/*     */     try {
/* 101 */       File inputFile = new File(this.mHtmlFilename);
/* 102 */       byte[] input = new byte[(int)inputFile.length()];
/* 103 */       inputStream = new FileInputStream(inputFile);
/*     */       
/* 105 */       inputStream.read(input);
/* 106 */       String data = new String(input, "UTF-8");
/*     */       
/* 108 */       if (isCancelled()) {
/* 109 */         return null;
/*     */       }
/*     */       
/* 112 */       File outputFile = new File(getCustomColorPath(this.mHtmlFilename), getCustomColorName(this.mHtmlFilename));
/* 113 */       outputStream = new FileOutputStream(outputFile);
/* 114 */       StringBuilder buffer = new StringBuilder();
/* 115 */       int index = 0;
/*     */       
/* 117 */       if (isCancelled()) {
/* 118 */         return null;
/*     */       }
/*     */       
/* 121 */       Pattern tagPattern = Pattern.compile("<.*?>");
/* 122 */       Pattern colorPattern = Pattern.compile("color:#[0-9|a-f|A-F]{6}");
/* 123 */       Pattern highlightPattern = Pattern.compile("background-color:#[0-9|a-f|A-F]{6}");
/* 124 */       Pattern paragraphPattern = Pattern.compile("<p.*?>");
/* 125 */       Pattern spanPattern = Pattern.compile("<span.*?>");
/*     */       
/* 127 */       Matcher tagMatcher = tagPattern.matcher(data);
/* 128 */       while (tagMatcher.find()) {
/* 129 */         String tag = tagMatcher.group();
/* 130 */         Matcher colorMatcher = colorPattern.matcher(tag);
/* 131 */         while (colorMatcher.find() && !isCancelled()) {
/*     */           
/* 133 */           String subStr = data.substring(index, tagMatcher.start() + colorMatcher.start());
/* 134 */           buffer.append(subStr);
/* 135 */           index += subStr.length();
/*     */           
/* 137 */           if (!highlightPattern.matcher(tag).find() && (paragraphPattern
/* 138 */             .matcher(tag).find() || spanPattern.matcher(tag).find())) {
/*     */ 
/*     */             
/* 141 */             String color = colorMatcher.group();
/* 142 */             ColorPt inputCP = getColorPt(color.substring(7, 13));
/* 143 */             if (inputCP == null) {
/* 144 */               inputCP = new ColorPt();
/*     */             }
/* 146 */             ReflowControl.OnPostProcessColorListener listener = this.mOnPostProcessColorListener;
/* 147 */             if (listener == null) {
/* 148 */               return null;
/*     */             }
/* 150 */             ColorPt outputCP = listener.getPostProcessedColor(inputCP);
/* 151 */             if (outputCP == null) {
/* 152 */               outputCP = inputCP;
/*     */             }
/*     */ 
/*     */             
/* 156 */             String customColor = "color:#" + getHexadecimal(outputCP);
/* 157 */             buffer.append(customColor);
/* 158 */             index += customColor.length();
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 163 */       if (isCancelled()) {
/* 164 */         return null;
/*     */       }
/*     */       
/* 167 */       buffer.append(data.substring(index));
/* 168 */       outputStream.write(buffer.toString().getBytes());
/* 169 */     } catch (FileNotFoundException e) {
/* 170 */       Context context = getContext();
/* 171 */       if (context instanceof FragmentActivity && !((FragmentActivity)context).isFinishing()) {
/* 172 */         AnalyticsHandlerAdapter.getInstance().sendException(e, "avail memory: " + 
/* 173 */             getAvailableInternalMemorySize());
/*     */       }
/* 175 */     } catch (IOException e) {
/* 176 */       AnalyticsHandlerAdapter.getInstance().sendException(e, "Can not read/write file");
/* 177 */     } catch (PDFNetException e) {
/* 178 */       AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*     */     } finally {
/* 180 */       Utils.closeQuietly(inputStream);
/* 181 */       Utils.closeQuietly(outputStream);
/*     */     } 
/* 183 */     return null;
/*     */   }
/*     */   
/*     */   private static String getAvailableInternalMemorySize() {
/* 187 */     if (!Utils.isJellyBeanMR2()) {
/* 188 */       return "";
/*     */     }
/* 190 */     File path = Environment.getDataDirectory();
/* 191 */     StatFs stat = new StatFs(path.getPath());
/* 192 */     long blockSize = stat.getBlockSizeLong();
/* 193 */     long availableBlocks = stat.getAvailableBlocksLong();
/* 194 */     return formatSize(availableBlocks * blockSize);
/*     */   }
/*     */   
/*     */   private static String formatSize(long size) {
/* 198 */     String suffix = null;
/*     */     
/* 200 */     if (size >= 1024L) {
/* 201 */       suffix = "KB";
/* 202 */       size /= 1024L;
/* 203 */       if (size >= 1024L) {
/* 204 */         suffix = "MB";
/* 205 */         size /= 1024L;
/*     */       } 
/*     */     } 
/*     */     
/* 209 */     StringBuilder resultBuffer = new StringBuilder(Long.toString(size));
/*     */     
/* 211 */     int commaOffset = resultBuffer.length() - 3;
/* 212 */     while (commaOffset > 0) {
/* 213 */       resultBuffer.insert(commaOffset, ',');
/* 214 */       commaOffset -= 3;
/*     */     } 
/*     */     
/* 217 */     if (suffix != null) resultBuffer.append(suffix); 
/* 218 */     return resultBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onPostExecute(Void aVoid) {
/* 227 */     super.onPostExecute(aVoid);
/*     */     
/* 229 */     Callback callback = this.mCallback;
/* 230 */     if (callback != null) {
/* 231 */       callback.onPostProcessColorFinished(this, getCustomColorFullName(this.mHtmlFilename));
/*     */     }
/*     */   }
/*     */   
/*     */   private String getCustomColorFullName(String inputFilename) {
/* 236 */     String outputFilepath = "";
/* 237 */     int index = inputFilename.lastIndexOf('/');
/* 238 */     if (index != -1) {
/* 239 */       outputFilepath = inputFilename.substring(0, index);
/*     */     }
/* 241 */     String outputFilename = inputFilename.substring(index + 1);
/* 242 */     index = outputFilename.lastIndexOf('.');
/* 243 */     if (index != -1) {
/* 244 */       outputFilename = outputFilename.substring(0, index);
/*     */     }
/* 246 */     outputFilename = outputFilename + "-cus.html";
/*     */     
/* 248 */     return outputFilepath + "/" + outputFilename;
/*     */   }
/*     */ 
/*     */   
/*     */   private static String getCustomColorName(String inputFilename) {
/* 253 */     int index = inputFilename.lastIndexOf('/');
/* 254 */     String outputFilename = inputFilename.substring(index + 1);
/* 255 */     index = outputFilename.lastIndexOf('.');
/* 256 */     if (index != -1) {
/* 257 */       outputFilename = outputFilename.substring(0, index);
/*     */     }
/* 259 */     outputFilename = outputFilename + "-cus.html";
/*     */     
/* 261 */     return outputFilename;
/*     */   }
/*     */   
/*     */   private static String getCustomColorPath(String inputFilename) {
/* 265 */     String outputFilepath = "";
/* 266 */     int index = inputFilename.lastIndexOf('/');
/* 267 */     if (index != -1) {
/* 268 */       outputFilepath = inputFilename.substring(0, index);
/*     */     }
/* 270 */     return outputFilepath;
/*     */   }
/*     */   
/*     */   private static ColorPt getColorPt(String str) {
/* 274 */     int len = str.length();
/* 275 */     if (len != 6 && len != 8) {
/* 276 */       return null;
/*     */     }
/*     */     
/* 279 */     double w = 0.0D;
/* 280 */     if (len == 8) {
/* 281 */       w = Integer.parseInt(str.substring(len - 2, len), 16);
/* 282 */       len -= 2;
/*     */     } 
/* 284 */     double z = Integer.parseInt(str.substring(len - 2, len), 16);
/* 285 */     len -= 2;
/* 286 */     double y = Integer.parseInt(str.substring(len - 2, len), 16);
/* 287 */     len -= 2;
/* 288 */     double x = Integer.parseInt(str.substring(len - 2, len), 16);
/*     */     
/*     */     try {
/* 291 */       len = str.length();
/* 292 */       if (len == 6) {
/* 293 */         return new ColorPt(x / 255.0D, y / 255.0D, z / 255.0D);
/*     */       }
/* 295 */       return new ColorPt(x / 255.0D, y / 255.0D, z / 255.0D, w / 255.0D);
/*     */     }
/* 297 */     catch (PDFNetException e) {
/* 298 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String getHexadecimal(ColorPt cp) {
/*     */     try {
/* 304 */       int x = (int)(cp.get(0) * 255.0D);
/* 305 */       int y = (int)(cp.get(1) * 255.0D);
/* 306 */       int z = (int)(cp.get(2) * 255.0D);
/* 307 */       return String.format("%1$02X%2$02X%3$02X", new Object[] { Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z) });
/* 308 */     } catch (PDFNetException e) {
/* 309 */       return "";
/*     */     } 
/*     */   }
/*     */   
/*     */   public static interface Callback {
/*     */     void onPostProcessColorFinished(@NonNull HtmlPostProcessColorTask param1HtmlPostProcessColorTask, String param1String);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\asynctask\HtmlPostProcessColorTask.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */