/*     */ package com.pdftron.pdf.dialog;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.graphics.Bitmap;
/*     */ import android.graphics.Color;
/*     */ import android.graphics.drawable.ColorDrawable;
/*     */ import android.graphics.drawable.Drawable;
/*     */ import android.os.AsyncTask;
/*     */ import android.os.Bundle;
/*     */ import android.util.TypedValue;
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.widget.ProgressBar;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.fragment.app.Fragment;
/*     */ import androidx.recyclerview.widget.GridLayoutManager;
/*     */ import androidx.recyclerview.widget.RecyclerView;
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.PDFDoc;
/*     */ import com.pdftron.pdf.PDFDraw;
/*     */ import com.pdftron.pdf.Page;
/*     */ import com.pdftron.pdf.adapter.StandardRubberStampAdapter;
/*     */ import com.pdftron.pdf.asynctask.CreateBitmapFromCustomStampTask;
/*     */ import com.pdftron.pdf.interfaces.OnRubberStampSelectedListener;
/*     */ import com.pdftron.pdf.model.CustomStampOption;
/*     */ import com.pdftron.pdf.model.StandardStampOption;
/*     */ import com.pdftron.pdf.model.StandardStampPreviewAppearance;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.CustomAsyncTask;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import com.pdftron.pdf.widget.recyclerview.ItemClickHelper;
/*     */ import com.pdftron.pdf.widget.recyclerview.SimpleRecyclerView;
/*     */ import com.pdftron.sdf.Obj;
/*     */ import java.io.InputStream;
/*     */ import java.lang.ref.WeakReference;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StandardRubberStampPickerFragment
/*     */   extends Fragment
/*     */ {
/*  48 */   public static final String TAG = StandardRubberStampPickerFragment.class.getName();
/*     */   
/*     */   private StandardStampPreviewAppearance[] mStandardStampPreviewAppearances;
/*     */   private OnRubberStampSelectedListener mOnRubberStampSelectedListener;
/*     */   private AttachStampsToAdapterTask mAttachStampsToAdapterTask;
/*     */   
/*     */   public static StandardRubberStampPickerFragment newInstance(StandardStampPreviewAppearance[] standardStampPreviewAppearances) {
/*  55 */     StandardRubberStampPickerFragment fragment = new StandardRubberStampPickerFragment();
/*  56 */     Bundle bundle = new Bundle();
/*  57 */     StandardStampPreviewAppearance.putStandardStampAppearancesToBundle(bundle, standardStampPreviewAppearances);
/*  58 */     fragment.setArguments(bundle);
/*  59 */     return fragment;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
/*  65 */     return inflater.inflate(R.layout.fragment_standard_rubber_stamp_picker, container, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
/*  70 */     super.onViewCreated(view, savedInstanceState);
/*     */     
/*  72 */     Bundle bundle = getArguments();
/*  73 */     if (bundle != null) {
/*  74 */       this.mStandardStampPreviewAppearances = StandardStampPreviewAppearance.getStandardStampAppearancesFromBundle(bundle);
/*     */     }
/*     */     
/*  77 */     SimpleRecyclerView recyclerView = (SimpleRecyclerView)view.findViewById(R.id.stamp_list);
/*  78 */     recyclerView.setLayoutManager((RecyclerView.LayoutManager)new GridLayoutManager(getContext(), 2));
/*     */     
/*  80 */     ItemClickHelper itemClickHelper = new ItemClickHelper();
/*  81 */     itemClickHelper.attachToRecyclerView((RecyclerView)recyclerView);
/*  82 */     itemClickHelper.setOnItemClickListener(new ItemClickHelper.OnItemClickListener()
/*     */         {
/*     */           public void onItemClick(RecyclerView parent, View view, int position, long id) {
/*  85 */             if (StandardRubberStampPickerFragment.this.mOnRubberStampSelectedListener != null && StandardRubberStampPickerFragment.this
/*  86 */               .mStandardStampPreviewAppearances != null && StandardRubberStampPickerFragment.this.mStandardStampPreviewAppearances.length > position) {
/*  87 */               String name = (StandardRubberStampPickerFragment.this.mStandardStampPreviewAppearances[position]).text;
/*  88 */               if ((StandardRubberStampPickerFragment.this.mStandardStampPreviewAppearances[position]).previewAppearance == null) {
/*  89 */                 StandardRubberStampPickerFragment.this.mOnRubberStampSelectedListener.onRubberStampSelected(name);
/*     */               } else {
/*  91 */                 Obj stampObj = StandardStampOption.getStandardStampObj(view.getContext(), name);
/*  92 */                 StandardRubberStampPickerFragment.this.mOnRubberStampSelectedListener.onRubberStampSelected(stampObj);
/*     */               } 
/*     */             } 
/*     */           }
/*     */         });
/*     */     
/*  98 */     Context context = view.getContext();
/*     */     
/* 100 */     int bgColor = Utils.isDeviceNightMode(context) ? -16777216 : -1;
/*     */     
/* 102 */     if (getView() != null && getView().getBackground() instanceof ColorDrawable) {
/* 103 */       Drawable background = getView().getBackground();
/* 104 */       bgColor = ((ColorDrawable)background).getColor();
/*     */     } else {
/* 106 */       TypedValue a = new TypedValue();
/* 107 */       context.getTheme().resolveAttribute(16842836, a, true);
/* 108 */       if (a.type >= 28 && a.type <= 31) {
/* 109 */         bgColor = a.data;
/*     */       }
/*     */     } 
/*     */     
/* 113 */     this
/* 114 */       .mAttachStampsToAdapterTask = new AttachStampsToAdapterTask(context, (RecyclerView)recyclerView, (ProgressBar)view.findViewById(R.id.progress_bar), this.mStandardStampPreviewAppearances, bgColor);
/*     */     
/* 116 */     this.mAttachStampsToAdapterTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Object[])new Void[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onStop() {
/* 121 */     super.onStop();
/* 122 */     if (this.mAttachStampsToAdapterTask != null) {
/* 123 */       this.mAttachStampsToAdapterTask.cancel(true);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOnRubberStampSelectedListener(OnRubberStampSelectedListener listener) {
/* 133 */     this.mOnRubberStampSelectedListener = listener;
/*     */   }
/*     */   
/*     */   private static class AttachStampsToAdapterTask
/*     */     extends CustomAsyncTask<Void, Void, Bitmap[]> {
/*     */     WeakReference<RecyclerView> mRecyclerViewRef;
/*     */     WeakReference<ProgressBar> mProgressBarRef;
/*     */     StandardStampPreviewAppearance[] mStandardStampPreviewAppearances;
/*     */     int mBgColor;
/*     */     int mHeight;
/*     */     
/*     */     AttachStampsToAdapterTask(Context context, RecyclerView recyclerView, ProgressBar progressBar, StandardStampPreviewAppearance[] standardStampPreviewAppearances, int bgColor) {
/* 145 */       super(context);
/* 146 */       this.mRecyclerViewRef = new WeakReference<>(recyclerView);
/* 147 */       this.mProgressBarRef = new WeakReference<>(progressBar);
/* 148 */       this.mStandardStampPreviewAppearances = standardStampPreviewAppearances;
/* 149 */       this.mBgColor = bgColor;
/* 150 */       this.mHeight = context.getResources().getDimensionPixelSize(R.dimen.stamp_image_height);
/*     */     }
/*     */ 
/*     */     
/*     */     protected Bitmap[] doInBackground(Void... voids) {
/* 155 */       int count = this.mStandardStampPreviewAppearances.length;
/* 156 */       Bitmap[] bitmaps = new Bitmap[count];
/* 157 */       boolean progressShown = false;
/* 158 */       for (int i = 0; i < count && !isCancelled(); i++) {
/* 159 */         String name = (this.mStandardStampPreviewAppearances[i]).text;
/* 160 */         if ((this.mStandardStampPreviewAppearances[i]).previewAppearance == null) {
/*     */           
/* 162 */           bitmaps[i] = getStandardStampBitmapFromPdf(getContext(), (this.mStandardStampPreviewAppearances[i]).text, this.mBgColor);
/*     */ 
/*     */         
/*     */         }
/* 166 */         else if (StandardStampOption.checkStandardStamp(getContext(), name)) {
/* 167 */           bitmaps[i] = StandardStampOption.getStandardStampBitmap(getContext(), name);
/*     */         }
/*     */         else {
/*     */           
/* 171 */           if (!progressShown) {
/* 172 */             publishProgress((Object[])new Void[0]);
/* 173 */             progressShown = true;
/*     */           } 
/*     */           
/* 176 */           StandardStampOption stampOption = new StandardStampOption((this.mStandardStampPreviewAppearances[i]).text, null, (this.mStandardStampPreviewAppearances[i]).previewAppearance.bgColorStart, (this.mStandardStampPreviewAppearances[i]).previewAppearance.bgColorEnd, (this.mStandardStampPreviewAppearances[i]).previewAppearance.textColor, (this.mStandardStampPreviewAppearances[i]).previewAppearance.borderColor, (this.mStandardStampPreviewAppearances[i]).previewAppearance.fillOpacity, (this.mStandardStampPreviewAppearances[i]).pointLeft, false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           try {
/* 186 */             Bitmap bitmap = CreateBitmapFromCustomStampTask.createBitmapFromCustomStamp((CustomStampOption)stampOption, this.mHeight, this.mHeight);
/* 187 */             if (bitmap != null) {
/* 188 */               StandardStampOption.saveStandardStamp(getContext(), name, (CustomStampOption)stampOption, bitmap);
/* 189 */               bitmaps[i] = bitmap;
/*     */             } 
/* 191 */           } catch (Exception e) {
/* 192 */             AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */           } 
/*     */         } 
/* 195 */       }  return bitmaps;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void onProgressUpdate(Void... values) {
/* 200 */       super.onProgressUpdate((Object[])values);
/* 201 */       ProgressBar progressBar = this.mProgressBarRef.get();
/* 202 */       if (progressBar != null) {
/* 203 */         progressBar.setVisibility(0);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     protected void onPostExecute(Bitmap[] bitmaps) {
/* 209 */       super.onPostExecute(bitmaps);
/* 210 */       RecyclerView recyclerView = this.mRecyclerViewRef.get();
/* 211 */       if (recyclerView != null && bitmaps != null) {
/* 212 */         recyclerView.setAdapter((RecyclerView.Adapter)new StandardRubberStampAdapter(bitmaps));
/*     */       }
/* 214 */       ProgressBar progressBar = this.mProgressBarRef.get();
/* 215 */       if (progressBar != null) {
/* 216 */         progressBar.setVisibility(8);
/*     */       }
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     private Bitmap getStandardStampBitmapFromPdf(@Nullable Context context, @NonNull String stampLabel, int bgColor) {
/* 222 */       if (context == null || Utils.isNullOrEmpty(stampLabel)) {
/* 223 */         return null;
/*     */       }
/*     */       
/* 226 */       InputStream fis = null;
/* 227 */       PDFDoc template = null;
/* 228 */       PDFDraw pdfDraw = null;
/*     */       try {
/* 230 */         fis = context.getResources().openRawResource(R.raw.stamps_icons);
/* 231 */         template = new PDFDoc(fis);
/*     */         
/* 233 */         pdfDraw = new PDFDraw();
/*     */         
/* 235 */         int r = Color.red(bgColor);
/* 236 */         int g = Color.green(bgColor);
/* 237 */         int b = Color.blue(bgColor);
/* 238 */         pdfDraw.setDefaultPageColor((byte)r, (byte)g, (byte)b);
/*     */         
/* 240 */         int pageCount = template.getPageCount();
/* 241 */         int maxWidth = (int)Utils.convDp2Pix(context, 200.0F);
/* 242 */         int marginWidth = (int)Utils.convDp2Pix(context, 175.0F);
/* 243 */         for (int pageNum = 1; pageNum <= pageCount; pageNum++) {
/* 244 */           if (stampLabel.equals(template.getPageLabel(pageNum).getPrefix())) {
/* 245 */             Page page = template.getPage(pageNum);
/* 246 */             int width = (int)Math.min(maxWidth, this.mHeight * page.getPageWidth() / page.getPageHeight() + 0.5D);
/* 247 */             if (width > marginWidth && width < maxWidth) {
/* 248 */               width = maxWidth;
/*     */             }
/* 250 */             pdfDraw.setImageSize(width, this.mHeight, false);
/* 251 */             return pdfDraw.getBitmap(page);
/*     */           } 
/*     */         } 
/* 254 */       } catch (Exception e) {
/* 255 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */       } finally {
/* 257 */         Utils.closeQuietly(template);
/* 258 */         Utils.closeQuietly(fis);
/* 259 */         if (pdfDraw != null) {
/*     */           try {
/* 261 */             pdfDraw.destroy();
/* 262 */           } catch (PDFNetException pDFNetException) {}
/*     */         }
/*     */       } 
/*     */       
/* 266 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\dialog\StandardRubberStampPickerFragment.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */