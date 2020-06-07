/*     */ package com.pdftron.pdf.dialog;
/*     */ 
/*     */ import android.app.AlertDialog;
/*     */ import android.app.Dialog;
/*     */ import android.content.Context;
/*     */ import android.content.DialogInterface;
/*     */ import android.graphics.Bitmap;
/*     */ import android.graphics.Matrix;
/*     */ import android.graphics.drawable.BitmapDrawable;
/*     */ import android.graphics.drawable.Drawable;
/*     */ import android.os.AsyncTask;
/*     */ import android.os.Bundle;
/*     */ import android.util.Log;
/*     */ import android.util.Pair;
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.widget.AdapterView;
/*     */ import android.widget.ArrayAdapter;
/*     */ import android.widget.ImageButton;
/*     */ import android.widget.ImageView;
/*     */ import android.widget.LinearLayout;
/*     */ import android.widget.ProgressBar;
/*     */ import android.widget.Spinner;
/*     */ import android.widget.SpinnerAdapter;
/*     */ import android.widget.TextView;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.fragment.app.DialogFragment;
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.Page;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.tools.ToolManager;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.ImageMemoryCache;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RotateDialogFragment
/*     */   extends DialogFragment
/*     */   implements PDFViewCtrl.ThumbAsyncListener
/*     */ {
/*  46 */   private static final String TAG = RotateDialogFragment.class.getName();
/*     */   
/*     */   private PDFViewCtrl mPdfViewCtrl;
/*     */   private int mCurrentPage;
/*  50 */   private int mRotationDelta = 0;
/*     */   private LinearLayout mThumbImgParent;
/*     */   private ImageView mThumbImg;
/*     */   private LinearLayout mThumbImgVertParent;
/*     */   private ImageView mThumbImgVert;
/*     */   private TextView mRotationDeltaTextView;
/*     */   private ProgressBar mProgressBar;
/*     */   private boolean mDebug = false;
/*     */   
/*     */   private enum ApplyMode
/*     */   {
/*  61 */     CurrentPage,
/*  62 */     AllPages,
/*  63 */     EvenPages,
/*  64 */     OddPages;
/*     */   }
/*     */   
/*  67 */   private ApplyMode mCurrentApplyMode = ApplyMode.CurrentPage;
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
/*     */   public static RotateDialogFragment newInstance() {
/*  79 */     return new RotateDialogFragment();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RotateDialogFragment setPdfViewCtrl(PDFViewCtrl pdfViewCtrl) {
/*  90 */     this.mPdfViewCtrl = pdfViewCtrl;
/*  91 */     this.mCurrentPage = pdfViewCtrl.getCurrentPage();
/*  92 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public Dialog onCreateDialog(Bundle savedInstanceState) {
/* 101 */     AlertDialog.Builder builder = new AlertDialog.Builder((Context)getActivity());
/* 102 */     LayoutInflater inflater = getActivity().getLayoutInflater();
/*     */     
/* 104 */     View view = inflater.inflate(R.layout.tools_dialog_rotate, null);
/* 105 */     builder.setView(view);
/*     */     
/* 107 */     builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener()
/*     */         {
/*     */           public void onClick(DialogInterface dialog, int which) {
/* 110 */             RotateDialogFragment.this.performRotation();
/* 111 */             RotateDialogFragment.this.dismiss();
/*     */           }
/*     */         });
/*     */     
/* 115 */     builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
/*     */         {
/*     */           public void onClick(DialogInterface dialog, int which) {
/* 118 */             RotateDialogFragment.this.dismiss();
/*     */           }
/*     */         });
/*     */     
/* 122 */     this.mThumbImgParent = (LinearLayout)view.findViewById(R.id.rotate_thumbnail_parent);
/* 123 */     this.mThumbImg = (ImageView)view.findViewById(R.id.rotate_thumbnail);
/* 124 */     this.mThumbImgVert = (ImageView)view.findViewById(R.id.rotate_thumbnail_vert);
/* 125 */     this.mThumbImgVertParent = (LinearLayout)view.findViewById(R.id.rotate_thumbnail_vert_parent);
/*     */     
/* 127 */     this.mProgressBar = (ProgressBar)view.findViewById(R.id.progressBar);
/*     */     
/* 129 */     this.mRotationDeltaTextView = (TextView)view.findViewById(R.id.rotation_delta_text_view);
/*     */     
/* 131 */     ImageButton cwButton = (ImageButton)view.findViewById(R.id.tools_dialog_rotate_clockwise_btn);
/* 132 */     cwButton.setOnClickListener(new View.OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/* 135 */             RotateDialogFragment.this.mRotationDelta = (RotateDialogFragment.this.mRotationDelta + 1) % 4;
/* 136 */             if (RotateDialogFragment.this.mRotationDelta == 1 || RotateDialogFragment.this.mRotationDelta == 3) {
/* 137 */               RotateDialogFragment.this.mThumbImgVert.setRotation((RotateDialogFragment.this.mRotationDelta == 1) ? 0.0F : 180.0F);
/* 138 */               RotateDialogFragment.this.mThumbImgVertParent.setVisibility(0);
/* 139 */               RotateDialogFragment.this.mThumbImgParent.setVisibility(4);
/*     */             } else {
/* 141 */               RotateDialogFragment.this.mThumbImg.setRotation((RotateDialogFragment.this.mRotationDelta == 0) ? 0.0F : 180.0F);
/* 142 */               RotateDialogFragment.this.mThumbImgParent.setVisibility(0);
/* 143 */               RotateDialogFragment.this.mThumbImgVertParent.setVisibility(4);
/*     */             } 
/*     */             
/* 146 */             RotateDialogFragment.this.updateRotationDeltaText();
/*     */           }
/*     */         });
/* 149 */     ImageButton ccwButton = (ImageButton)view.findViewById(R.id.tools_dialog_rotate_counter_clockwise_btn);
/* 150 */     ccwButton.setOnClickListener(new View.OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/* 153 */             RotateDialogFragment.this.mRotationDelta = RotateDialogFragment.this.mRotationDelta - 1;
/* 154 */             if (RotateDialogFragment.this.mRotationDelta < 0) {
/* 155 */               RotateDialogFragment.this.mRotationDelta = 4 + RotateDialogFragment.this.mRotationDelta;
/*     */             }
/* 157 */             if (RotateDialogFragment.this.mRotationDelta == 1 || RotateDialogFragment.this.mRotationDelta == 3) {
/* 158 */               RotateDialogFragment.this.mThumbImgVert.setRotation((RotateDialogFragment.this.mRotationDelta == 1) ? 0.0F : 180.0F);
/* 159 */               RotateDialogFragment.this.mThumbImgVertParent.setVisibility(0);
/* 160 */               RotateDialogFragment.this.mThumbImgParent.setVisibility(4);
/*     */             } else {
/* 162 */               RotateDialogFragment.this.mThumbImg.setRotation((RotateDialogFragment.this.mRotationDelta == 0) ? 0.0F : 180.0F);
/* 163 */               RotateDialogFragment.this.mThumbImgParent.setVisibility(0);
/* 164 */               RotateDialogFragment.this.mThumbImgVertParent.setVisibility(4);
/*     */             } 
/*     */             
/* 167 */             RotateDialogFragment.this.updateRotationDeltaText();
/*     */           }
/*     */         });
/*     */     
/* 171 */     Spinner rotateModeSpinner = (Spinner)view.findViewById(R.id.rotate_mode_spinner);
/* 172 */     ArrayAdapter<String> rotateModeAdapter = new ArrayAdapter(getContext(), 17367049);
/* 173 */     rotateModeAdapter.setDropDownViewResource(17367049);
/* 174 */     for (ApplyMode i : ApplyMode.values()) {
/* 175 */       String str = null;
/* 176 */       if (i == ApplyMode.CurrentPage) {
/* 177 */         str = getString(R.string.dialog_rotate_current_page, new Object[] { Integer.valueOf(this.mCurrentPage) });
/* 178 */       } else if (i == ApplyMode.AllPages) {
/* 179 */         str = getString(R.string.dialog_rotate_all_pages);
/* 180 */       } else if (i == ApplyMode.EvenPages) {
/* 181 */         str = getString(R.string.dialog_rotate_even_pages);
/* 182 */       } else if (i == ApplyMode.OddPages) {
/* 183 */         str = getString(R.string.dialog_rotate_odd_pages);
/*     */       } 
/* 185 */       rotateModeAdapter.add(str);
/*     */     } 
/*     */     
/* 188 */     rotateModeSpinner.setAdapter((SpinnerAdapter)rotateModeAdapter);
/* 189 */     rotateModeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
/*     */         {
/*     */           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
/* 192 */             RotateDialogFragment.this.mCurrentApplyMode = ApplyMode.values()[position];
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onNothingSelected(AdapterView<?> parent) {}
/*     */         });
/*     */     try {
/* 201 */       if (this.mPdfViewCtrl != null) {
/* 202 */         this.mPdfViewCtrl.addThumbAsyncListener(this);
/* 203 */         this.mPdfViewCtrl.getThumbAsync(this.mCurrentPage);
/*     */       } 
/* 205 */     } catch (PDFNetException pDFNetException) {}
/*     */ 
/*     */ 
/*     */     
/* 209 */     return (Dialog)builder.create();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDestroy() {
/* 217 */     if (this.mPdfViewCtrl != null) {
/* 218 */       this.mPdfViewCtrl.removeThumbAsyncListener(this);
/*     */     }
/*     */     
/* 221 */     super.onDestroy();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onThumbReceived(int page, int[] buf, int width, int height) {
/* 229 */     (new LoadThumbnailTask(this.mThumbImgParent.getLayoutParams(), page, buf, width, height)).execute((Object[])new Void[0]);
/*     */   }
/*     */   
/*     */   private void updateRotationDeltaText() {
/* 233 */     String degree = "";
/* 234 */     switch (this.mRotationDelta) {
/*     */       case 0:
/* 236 */         degree = "0";
/*     */         break;
/*     */       case 1:
/* 239 */         degree = "90";
/*     */         break;
/*     */       case 2:
/* 242 */         degree = "180";
/*     */         break;
/*     */       case 3:
/* 245 */         degree = "270";
/*     */         break;
/*     */     } 
/* 248 */     degree = degree + "°";
/* 249 */     this.mRotationDeltaTextView.setText(degree);
/*     */   }
/*     */   
/*     */   private void performRotation() {
/* 253 */     if (this.mPdfViewCtrl == null) {
/*     */       return;
/*     */     }
/*     */     
/* 257 */     boolean shouldUnlock = false;
/*     */     try {
/* 259 */       this.mPdfViewCtrl.docLock(true);
/* 260 */       shouldUnlock = true;
/* 261 */       int pageCount = this.mPdfViewCtrl.getDoc().getPageCount();
/* 262 */       Page[] pages = null;
/* 263 */       List<Integer> pageList = new ArrayList<>();
/* 264 */       if (this.mCurrentApplyMode == ApplyMode.CurrentPage) {
/* 265 */         pages = new Page[1];
/* 266 */         pageList.add(Integer.valueOf(this.mCurrentPage));
/* 267 */         pages[0] = this.mPdfViewCtrl.getDoc().getPage(this.mCurrentPage);
/* 268 */       } else if (this.mCurrentApplyMode == ApplyMode.AllPages) {
/* 269 */         pages = new Page[pageCount];
/* 270 */         for (int i = 1; i <= pageCount; i++) {
/* 271 */           pageList.add(Integer.valueOf(i));
/* 272 */           pages[i - 1] = this.mPdfViewCtrl.getDoc().getPage(i);
/*     */         } 
/* 274 */       } else if (this.mCurrentApplyMode == ApplyMode.OddPages) {
/* 275 */         int newPageCount = (int)Math.ceil(pageCount / 2.0D);
/* 276 */         pages = new Page[newPageCount];
/* 277 */         int arrayIndex = 0;
/* 278 */         for (int i = 1; i <= pageCount; i += 2) {
/* 279 */           pageList.add(Integer.valueOf(i));
/* 280 */           pages[arrayIndex++] = this.mPdfViewCtrl.getDoc().getPage(i);
/*     */         } 
/* 282 */       } else if (this.mCurrentApplyMode == ApplyMode.EvenPages && 
/* 283 */         pageCount >= 2) {
/* 284 */         int newPageCount = (int)Math.floor(pageCount / 2.0D);
/* 285 */         pages = new Page[newPageCount];
/* 286 */         int arrayIndex = 0;
/* 287 */         for (int i = 2; i <= pageCount; i += 2) {
/* 288 */           pageList.add(Integer.valueOf(i));
/* 289 */           pages[arrayIndex++] = this.mPdfViewCtrl.getDoc().getPage(i);
/*     */         } 
/*     */       } 
/*     */       
/* 293 */       if (pages != null) {
/* 294 */         for (Page page : pages) {
/* 295 */           page.setRotation((page.getRotation() + this.mRotationDelta) % 4);
/*     */         }
/*     */       }
/*     */       
/* 299 */       ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 300 */       if (toolManager != null) {
/* 301 */         toolManager.raisePagesRotated(pageList);
/*     */       }
/* 303 */     } catch (Exception ex) {
/* 304 */       AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*     */     } finally {
/* 306 */       if (shouldUnlock) {
/* 307 */         this.mPdfViewCtrl.docUnlock();
/*     */       }
/*     */       try {
/* 310 */         this.mPdfViewCtrl.updatePageLayout();
/* 311 */       } catch (PDFNetException e) {
/* 312 */         AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private class LoadThumbnailTask
/*     */     extends AsyncTask<Void, Void, Pair<BitmapDrawable, BitmapDrawable>>
/*     */   {
/*     */     private final ViewGroup.LayoutParams mLParams;
/*     */     private final int mPage;
/*     */     private int mWidth;
/*     */     private int mHeight;
/*     */     private int[] mBuffer;
/*     */     
/*     */     LoadThumbnailTask(ViewGroup.LayoutParams lParams, int page, int[] buffer, int width, int height) {
/* 327 */       this.mLParams = lParams;
/* 328 */       this.mPage = page;
/* 329 */       this.mBuffer = buffer;
/* 330 */       this.mWidth = width;
/* 331 */       this.mHeight = height;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected Pair<BitmapDrawable, BitmapDrawable> doInBackground(Void... voids) {
/* 340 */       Pair<BitmapDrawable, BitmapDrawable> retPair = null;
/*     */       try {
/* 342 */         if (this.mBuffer != null && this.mBuffer.length > 0) {
/* 343 */           ImageMemoryCache imageMemoryCache = ImageMemoryCache.getInstance();
/* 344 */           Bitmap bitmap = imageMemoryCache.getBitmapFromReusableSet(this.mWidth, this.mHeight, Bitmap.Config.ARGB_8888);
/* 345 */           if (bitmap == null) {
/* 346 */             bitmap = Bitmap.createBitmap(this.mWidth, this.mHeight, Bitmap.Config.ARGB_8888);
/*     */           }
/* 348 */           bitmap.setPixels(this.mBuffer, 0, this.mWidth, 0, 0, this.mWidth, this.mHeight);
/* 349 */           Pair<Bitmap, Bitmap> bitmapPair = RotateDialogFragment.this.scaleBitmap(this.mLParams, bitmap);
/* 350 */           if (bitmapPair == null) {
/* 351 */             return null;
/*     */           }
/*     */           
/* 354 */           BitmapDrawable drawable1 = new BitmapDrawable(RotateDialogFragment.this.getContext().getResources(), (Bitmap)bitmapPair.first);
/* 355 */           BitmapDrawable drawable2 = new BitmapDrawable(RotateDialogFragment.this.getContext().getResources(), (Bitmap)bitmapPair.second);
/*     */           
/* 357 */           retPair = new Pair(drawable1, drawable2);
/* 358 */           if (RotateDialogFragment.this.mDebug) {
/* 359 */             Log.d(RotateDialogFragment.TAG, "doInBackground - finished work for page: " + Integer.toString(this.mPage));
/*     */           }
/* 361 */         } else if (RotateDialogFragment.this.mDebug) {
/* 362 */           Log.d(RotateDialogFragment.TAG, "doInBackground - Buffer is empty for page: " + Integer.toString(this.mPage));
/*     */         } 
/* 364 */       } catch (Exception e) {
/* 365 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/* 366 */       } catch (OutOfMemoryError oom) {
/* 367 */         Utils.manageOOM(RotateDialogFragment.this.getContext(), RotateDialogFragment.this.mPdfViewCtrl);
/*     */       } 
/*     */       
/* 370 */       return retPair;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void onPostExecute(Pair<BitmapDrawable, BitmapDrawable> result) {
/* 375 */       if (isCancelled()) {
/*     */         return;
/*     */       }
/*     */       
/* 379 */       if (result != null) {
/* 380 */         if (RotateDialogFragment.this.mDebug)
/* 381 */           Log.d(RotateDialogFragment.TAG, "onPostExecute - setting bitmap for page: " + Integer.toString(this.mPage)); 
/* 382 */         RotateDialogFragment.this.mThumbImgVert.setImageDrawable((Drawable)result.second);
/*     */         
/* 384 */         RotateDialogFragment.this.mThumbImg.setImageDrawable((Drawable)result.first);
/* 385 */         RotateDialogFragment.this.mThumbImgParent.setVisibility(0);
/*     */         
/* 387 */         RotateDialogFragment.this.mProgressBar.setVisibility(8);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected void onCancelled(Pair<BitmapDrawable, BitmapDrawable> value) {}
/*     */   }
/*     */ 
/*     */   
/*     */   private Pair<Bitmap, Bitmap> scaleBitmap(ViewGroup.LayoutParams parentParams, Bitmap bitmap) {
/* 398 */     if (bitmap == null) {
/* 399 */       return null;
/*     */     }
/*     */     
/* 402 */     int bitmapWidth = bitmap.getWidth();
/* 403 */     int bitmapHeight = bitmap.getHeight();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 408 */     float xScale = parentParams.width / bitmapWidth;
/* 409 */     float yScale = parentParams.height / bitmapHeight;
/* 410 */     float scale = (xScale > yScale) ? xScale : yScale;
/*     */ 
/*     */     
/* 413 */     Matrix matrix = new Matrix();
/* 414 */     matrix.postScale(scale, scale);
/*     */ 
/*     */     
/*     */     try {
/* 418 */       Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmapWidth, bitmapHeight, matrix, true);
/*     */ 
/*     */       
/* 421 */       matrix = new Matrix();
/* 422 */       matrix.postRotate(90.0F);
/* 423 */       matrix.postScale(scale, scale);
/*     */ 
/*     */       
/* 426 */       Bitmap scaledBitmapVert = Bitmap.createBitmap(bitmap, 0, 0, bitmapWidth, bitmapHeight, matrix, true);
/*     */       
/* 428 */       return new Pair(scaledBitmap, scaledBitmapVert);
/* 429 */     } catch (OutOfMemoryError oom) {
/* 430 */       Utils.manageOOM(getContext(), this.mPdfViewCtrl);
/* 431 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDebug(boolean debug) {
/* 439 */     this.mDebug = debug;
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\dialog\RotateDialogFragment.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */