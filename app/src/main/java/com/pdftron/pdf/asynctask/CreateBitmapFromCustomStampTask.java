/*     */ package com.pdftron.pdf.asynctask;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.graphics.Bitmap;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.Annot;
/*     */ import com.pdftron.pdf.PDFDoc;
/*     */ import com.pdftron.pdf.PDFDraw;
/*     */ import com.pdftron.pdf.Page;
/*     */ import com.pdftron.pdf.Rect;
/*     */ import com.pdftron.pdf.annots.RubberStamp;
/*     */ import com.pdftron.pdf.model.CustomStampOption;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.CustomAsyncTask;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import com.pdftron.sdf.Doc;
/*     */ import com.pdftron.sdf.Obj;
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
/*     */ public class CreateBitmapFromCustomStampTask
/*     */   extends CustomAsyncTask<Void, Void, Bitmap>
/*     */ {
/*     */   private CustomStampOption mCustomStampOption;
/*     */   private int mSingleLineHeight;
/*     */   private int mTwoLinesHeight;
/*     */   private OnCustomStampCreatedCallback mOnCustomStampCreatedCallback;
/*     */   
/*     */   public CreateBitmapFromCustomStampTask(@NonNull Context context, @NonNull CustomStampOption customStampOption) {
/*  42 */     super(context);
/*  43 */     this.mCustomStampOption = customStampOption;
/*  44 */     this.mSingleLineHeight = context.getResources().getDimensionPixelSize(R.dimen.stamp_image_height);
/*  45 */     this.mTwoLinesHeight = context.getResources().getDimensionPixelSize(R.dimen.stamp_image_height_two_lines);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOnCustomStampCreatedCallback(@Nullable OnCustomStampCreatedCallback callback) {
/*  56 */     this.mOnCustomStampCreatedCallback = callback;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Bitmap doInBackground(Void... voids) {
/*     */     try {
/*  62 */       return createBitmapFromCustomStamp(this.mCustomStampOption, this.mSingleLineHeight, this.mTwoLinesHeight);
/*  63 */     } catch (Exception e) {
/*  64 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */ 
/*     */       
/*  67 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void onPostExecute(Bitmap bitmap) {
/*  72 */     super.onPostExecute(bitmap);
/*  73 */     if (this.mOnCustomStampCreatedCallback != null) {
/*  74 */       this.mOnCustomStampCreatedCallback.onCustomStampCreated(bitmap);
/*     */     }
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
/*     */   public static Bitmap createBitmapFromCustomStamp(@NonNull CustomStampOption customStampOption, int singleLineHeight, int twoLinesHeight) {
/*  89 */     return createBitmapFromCustomStamp(customStampOption, singleLineHeight, twoLinesHeight, -1);
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
/*     */   public static Bitmap createBitmapFromCustomStamp(@NonNull CustomStampOption customStampOption, int singleLineHeight, int twoLinesHeight, int width) {
/* 103 */     CustomStampOption stampOption = new CustomStampOption(customStampOption);
/* 104 */     stampOption.fillOpacity = 1.0D;
/*     */     
/* 106 */     PDFDoc tempDoc = null;
/* 107 */     PDFDraw pdfDraw = null;
/* 108 */     boolean shouldUnlock = false;
/*     */     try {
/* 110 */       tempDoc = new PDFDoc();
/* 111 */       tempDoc.lock();
/* 112 */       shouldUnlock = true;
/* 113 */       tempDoc.initSecurityHandler();
/* 114 */       Page page = tempDoc.pageCreate();
/*     */       
/* 116 */       Rect rect = new Rect();
/* 117 */       Obj obj = CustomStampOption.convertToObj(stampOption);
/* 118 */       RubberStamp rubberStamp = RubberStamp.createCustom((Doc)tempDoc, rect, obj);
/*     */       
/* 120 */       page.annotPushBack((Annot)rubberStamp);
/* 121 */       page.setCropBox(rubberStamp.getRect());
/*     */       
/* 123 */       pdfDraw = new PDFDraw();
/* 124 */       pdfDraw.setPageTransparent(true);
/*     */       
/* 126 */       int height = Utils.isNullOrEmpty(stampOption.secondText) ? singleLineHeight : twoLinesHeight;
/* 127 */       if (width == -1) {
/* 128 */         double ratio = height / page.getPageHeight();
/* 129 */         width = (int)(page.getPageWidth() * ratio + 0.5D);
/*     */       } 
/* 131 */       pdfDraw.setImageSize(width, height, false);
/*     */       
/* 133 */       return pdfDraw.getBitmap(page);
/* 134 */     } catch (Exception e) {
/* 135 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */     } finally {
/* 137 */       if (shouldUnlock) {
/* 138 */         Utils.unlockQuietly(tempDoc);
/*     */       }
/* 140 */       Utils.closeQuietly(tempDoc);
/* 141 */       if (pdfDraw != null) {
/*     */         try {
/* 143 */           pdfDraw.destroy();
/* 144 */         } catch (PDFNetException pDFNetException) {}
/*     */       }
/*     */     } 
/*     */     
/* 148 */     return null;
/*     */   }
/*     */   
/*     */   public static interface OnCustomStampCreatedCallback {
/*     */     void onCustomStampCreated(@Nullable Bitmap param1Bitmap);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\asynctask\CreateBitmapFromCustomStampTask.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */