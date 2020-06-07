/*     */ package com.pdftron.pdf.adapter;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.widget.ImageView;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.appcompat.widget.AppCompatImageView;
/*     */ import androidx.recyclerview.widget.RecyclerView;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.StampManager;
/*     */ import com.pdftron.pdf.widget.recyclerview.SimpleRecyclerViewAdapter;
/*     */ import com.pdftron.pdf.widget.recyclerview.ViewHolderBindListener;
/*     */ import com.squareup.picasso.MemoryPolicy;
/*     */ import com.squareup.picasso.Picasso;
/*     */ import io.reactivex.Single;
/*     */ import io.reactivex.android.schedulers.AndroidSchedulers;
/*     */ import io.reactivex.disposables.CompositeDisposable;
/*     */ import io.reactivex.functions.Consumer;
/*     */ import io.reactivex.functions.Function;
/*     */ import io.reactivex.schedulers.Schedulers;
/*     */ import java.io.File;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SavedSignatureAdapter
/*     */   extends SimpleRecyclerViewAdapter<File, SavedSignatureAdapter.ViewHolder>
/*     */ {
/*     */   private final WeakReference<Context> mContextRef;
/*  36 */   private List<File> mSignatureFiles = new ArrayList<>();
/*     */   
/*  38 */   private CompositeDisposable mDisposables = new CompositeDisposable();
/*     */   
/*     */   public SavedSignatureAdapter(@NonNull Context context, ViewHolderBindListener bindListener) {
/*  41 */     super(bindListener);
/*     */     
/*  43 */     this.mContextRef = new WeakReference<>(context);
/*  44 */     File[] files = StampManager.getInstance().getSavedSignatures(context);
/*  45 */     if (files != null) {
/*  46 */       this.mSignatureFiles = new ArrayList<>(Arrays.asList(files));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public File getItem(int position) {
/*  52 */     if (position < 0 || position >= this.mSignatureFiles.size()) {
/*  53 */       return null;
/*     */     }
/*  55 */     return this.mSignatureFiles.get(position);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(File item) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean remove(File item) {
/*  65 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public File removeAt(int position) {
/*  71 */     File file = this.mSignatureFiles.get(position);
/*  72 */     File savedSignatureJpegFile = StampManager.getInstance().getSavedSignatureJpegFile(this.mContextRef.get(), file);
/*  73 */     if (savedSignatureJpegFile != null && savedSignatureJpegFile.exists())
/*     */     {
/*  75 */       savedSignatureJpegFile.delete();
/*     */     }
/*  77 */     boolean success = file.delete();
/*  78 */     if (success) {
/*  79 */       return this.mSignatureFiles.remove(position);
/*     */     }
/*  81 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void insert(File item, int position) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateSpanCount(int count) {}
/*     */ 
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
/*  97 */     View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_rubber_stamp, parent, false);
/*  98 */     return new ViewHolder(view);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
/* 103 */     super.onBindViewHolder(holder, position);
/*     */     
/* 105 */     this.mDisposables.add(
/* 106 */         Single.just(getItem(position))
/* 107 */         .subscribeOn(Schedulers.io())
/* 108 */         .observeOn(AndroidSchedulers.mainThread())
/* 109 */         .map(new Function<File, File>()
/*     */           {
/*     */             public File apply(File file) throws Exception {
/* 112 */               return StampManager.getInstance().getSavedSignatureJpegFile(SavedSignatureAdapter.this.mContextRef.get(), file);
/*     */             }
/* 115 */           }).subscribe(new Consumer<File>()
/*     */           {
/*     */             public void accept(File jpgFile) throws Exception {
/* 118 */               Picasso.get()
/* 119 */                 .load(jpgFile)
/* 120 */                 .memoryPolicy(MemoryPolicy.NO_CACHE, new MemoryPolicy[] { MemoryPolicy.NO_STORE
/* 121 */                   }).into((ImageView)holder.mImageView);
/*     */             }
/*     */           }new Consumer<Throwable>()
/*     */           {
/*     */             public void accept(Throwable throwable) throws Exception {
/* 126 */               AnalyticsHandlerAdapter.getInstance().sendException(new Exception(throwable));
/*     */             }
/*     */           }));
/*     */   }
/*     */ 
/*     */   
/*     */   public void dispose() {
/* 133 */     this.mDisposables.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getItemCount() {
/* 138 */     return this.mSignatureFiles.size();
/*     */   }
/*     */   
/*     */   class ViewHolder
/*     */     extends RecyclerView.ViewHolder {
/*     */     AppCompatImageView mImageView;
/*     */     
/*     */     ViewHolder(View itemView) {
/* 146 */       super(itemView);
/* 147 */       this.mImageView = (AppCompatImageView)itemView.findViewById(R.id.stamp_image_view);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\adapter\SavedSignatureAdapter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */