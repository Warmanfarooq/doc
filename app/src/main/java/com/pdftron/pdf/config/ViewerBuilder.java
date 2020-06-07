/*    */ package com.pdftron.pdf.config;
/*    */ 
/*    */ import android.net.Uri;
/*    */ import android.os.Parcel;
/*    */ import android.os.Parcelable;
/*    */ import androidx.annotation.NonNull;
/*    */ import androidx.annotation.Nullable;
/*    */ import com.pdftron.pdf.controls.PdfViewCtrlTabFragment;
/*    */ import com.pdftron.pdf.controls.PdfViewCtrlTabHostFragment;
/*    */ import java.io.File;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ViewerBuilder
/*    */   extends GenericViewerBuilder<PdfViewCtrlTabHostFragment, PdfViewCtrlTabFragment, ViewerBuilder>
/*    */ {
/*    */   private ViewerBuilder() {}
/*    */   
/*    */   protected Class<PdfViewCtrlTabFragment> useDefaultTabFragmentClass() {
/* 24 */     return PdfViewCtrlTabFragment.class;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Class<PdfViewCtrlTabHostFragment> useDefaultTabHostFragmentClass() {
/* 29 */     return PdfViewCtrlTabHostFragment.class;
/*    */   }
/*    */ 
/*    */   
/*    */   @NonNull
/*    */   protected ViewerBuilder useBuilder() {
/* 35 */     return this;
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
/*    */   public static ViewerBuilder withUri(@Nullable Uri file, @Nullable String password) {
/* 47 */     ViewerBuilder builder = new ViewerBuilder();
/* 48 */     builder.mFile = file;
/* 49 */     builder.mPassword = password;
/* 50 */     return builder;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ViewerBuilder withUri(@Nullable Uri file) {
/* 57 */     return withUri(file, (String)null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ViewerBuilder withFile(@Nullable File file, @Nullable String password) {
/* 64 */     return withUri((file != null) ? Uri.fromFile(file) : null, password);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ViewerBuilder withFile(@Nullable File file) {
/* 71 */     return withUri((file != null) ? Uri.fromFile(file) : null, (String)null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ViewerBuilder(Parcel in) {
/* 78 */     super(in);
/*    */   }
/*    */   
/* 81 */   public static final Parcelable.Creator<ViewerBuilder> CREATOR = new Parcelable.Creator<ViewerBuilder>()
/*    */     {
/*    */       public ViewerBuilder createFromParcel(Parcel source) {
/* 84 */         return new ViewerBuilder(source);
/*    */       }
/*    */ 
/*    */       
/*    */       public ViewerBuilder[] newArray(int size) {
/* 89 */         return new ViewerBuilder[size];
/*    */       }
/*    */     };
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\config\ViewerBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */