/*     */ package com.pdftron.pdf.config;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.net.Uri;
/*     */ import android.os.Bundle;
/*     */ import android.os.Parcel;
/*     */ import android.os.Parcelable;
/*     */ import androidx.annotation.DrawableRes;
/*     */ import androidx.annotation.MenuRes;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.annotation.RestrictTo;
/*     */ import androidx.fragment.app.Fragment;
/*     */ import com.pdftron.pdf.controls.PdfViewCtrlTabFragment;
/*     */ import com.pdftron.pdf.controls.PdfViewCtrlTabHostFragment;
/*     */ import com.pdftron.pdf.interfaces.builder.SkeletalFragmentBuilder;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import java.util.Arrays;
/*     */ import org.json.JSONObject;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class GenericViewerBuilder<TH extends PdfViewCtrlTabHostFragment, T extends PdfViewCtrlTabFragment, VB extends GenericViewerBuilder>
/*     */   extends SkeletalFragmentBuilder<TH>
/*     */ {
/*     */   @Nullable
/*     */   protected String mTabTitle;
/*     */   @Nullable
/*     */   protected Uri mFile;
/*     */   @Nullable
/*     */   protected String mPassword;
/*     */   protected boolean mUseCacheFolder = true;
/*     */   protected boolean mUseQuitAppMode = false;
/*     */   @Nullable
/*     */   protected ViewerConfig mConfig;
/*     */   @DrawableRes
/*  40 */   protected int mNavigationIcon = R.drawable.ic_arrow_back_white_24dp;
/*     */   @Nullable
/*  42 */   protected int[] mCustomToolbarMenu = null;
/*     */   
/*  44 */   protected int mFileType = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected Class<T> mTabFragmentClass;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected Class<TH> mTabHostFragmentClass;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected String mCustomHeaders;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VB usingTabClass(@NonNull Class<? extends T> tabClass) {
/*  73 */     ((GenericViewerBuilder)useBuilder()).mTabFragmentClass = (Class)tabClass;
/*  74 */     return useBuilder();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VB usingTabHostClass(@NonNull Class<TH> tabHostClass) {
/*  84 */     ((GenericViewerBuilder)useBuilder()).mTabHostFragmentClass = tabHostClass;
/*  85 */     return useBuilder();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VB usingNavIcon(@DrawableRes int navIconRes) {
/*  96 */     ((GenericViewerBuilder)useBuilder()).mNavigationIcon = navIconRes;
/*  97 */     return useBuilder();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VB usingConfig(@NonNull ViewerConfig config) {
/* 108 */     ((GenericViewerBuilder)useBuilder()).mConfig = config;
/* 109 */     return useBuilder();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VB usingCacheFolder(boolean useCacheFolder) {
/* 120 */     ((GenericViewerBuilder)useBuilder()).mUseCacheFolder = useCacheFolder;
/* 121 */     return useBuilder();
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
/*     */   public VB usingFileType(int fileType) {
/* 136 */     ((GenericViewerBuilder)useBuilder()).mFileType = fileType;
/* 137 */     return useBuilder();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VB usingTabTitle(@Nullable String title) {
/* 148 */     ((GenericViewerBuilder)useBuilder()).mTabTitle = title;
/* 149 */     return useBuilder();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VB usingCustomToolbar(@MenuRes int[] menu) {
/* 159 */     ((GenericViewerBuilder)useBuilder()).mCustomToolbarMenu = menu;
/* 160 */     return useBuilder();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VB usingCustomHeaders(@Nullable JSONObject headers) {
/* 169 */     ((GenericViewerBuilder)useBuilder()).mCustomHeaders = (headers != null) ? headers.toString() : null;
/* 170 */     return useBuilder();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/*     */   public VB usingQuitAppMode(boolean useQuitAppMode) {
/* 180 */     ((GenericViewerBuilder)useBuilder()).mUseQuitAppMode = useQuitAppMode;
/* 181 */     return useBuilder();
/*     */   }
/*     */ 
/*     */   
/*     */   public TH build(@NonNull Context context) {
/* 186 */     return (TH)build(context, (this.mTabHostFragmentClass == null) ? useDefaultTabHostFragmentClass() : this.mTabHostFragmentClass);
/*     */   }
/*     */ 
/*     */   
/*     */   public Bundle createBundle(@NonNull Context context) {
/*     */     Bundle args;
/* 192 */     if (this.mFile == null) {
/* 193 */       args = new Bundle();
/*     */     } else {
/* 195 */       args = PdfViewCtrlTabFragment.createBasicPdfViewCtrlTabBundle(context, this.mFile, this.mPassword, this.mConfig);
/* 196 */       if (this.mFileType != -1) {
/* 197 */         args.putInt("bundle_tab_item_source", this.mFileType);
/*     */       }
/*     */     } 
/* 200 */     if (this.mTabTitle != null) {
/* 201 */       args.putString("bundle_tab_title", this.mTabTitle);
/*     */     }
/* 203 */     args.putSerializable("PdfViewCtrlTabHostFragment_tab_fragment_class", (this.mTabFragmentClass == null) ? useDefaultTabFragmentClass() : this.mTabFragmentClass);
/* 204 */     args.putParcelable("bundle_tab_host_config", this.mConfig);
/* 205 */     args.putInt("bundle_tab_host_nav_icon", this.mNavigationIcon);
/* 206 */     args.putBoolean("PdfViewCtrlTabFragment_bundle_cache_folder_uri", this.mUseCacheFolder);
/* 207 */     args.putIntArray("bundle_tab_host_toolbar_menu", this.mCustomToolbarMenu);
/* 208 */     args.putBoolean("bundle_tab_host_quit_app_when_done_viewing", this.mUseQuitAppMode);
/* 209 */     if (this.mCustomHeaders != null) {
/* 210 */       args.putString("bundle_tab_custom_headers", this.mCustomHeaders);
/*     */     }
/*     */     
/* 213 */     return args;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkArgs(@NonNull Context context) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected GenericViewerBuilder(Parcel in) {
/* 226 */     this.mTabTitle = in.readString();
/* 227 */     this.mFile = (Uri)in.readParcelable(Uri.class.getClassLoader());
/* 228 */     this.mPassword = in.readString();
/* 229 */     this.mUseCacheFolder = (in.readByte() != 0);
/* 230 */     this.mUseQuitAppMode = (in.readByte() != 0);
/* 231 */     this.mConfig = (ViewerConfig)in.readParcelable(ViewerConfig.class.getClassLoader());
/* 232 */     this.mNavigationIcon = in.readInt();
/* 233 */     this.mCustomToolbarMenu = in.createIntArray();
/* 234 */     this.mFileType = in.readInt();
/* 235 */     this.mTabFragmentClass = (Class<T>)in.readSerializable();
/* 236 */     this.mTabHostFragmentClass = (Class<TH>)in.readSerializable();
/* 237 */     this.mCustomHeaders = in.readString();
/*     */   }
/*     */ 
/*     */   
/*     */   public int describeContents() {
/* 242 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeToParcel(Parcel dest, int flags) {
/* 247 */     dest.writeString(this.mTabTitle);
/* 248 */     dest.writeParcelable((Parcelable)this.mFile, flags);
/* 249 */     dest.writeString(this.mPassword);
/* 250 */     dest.writeByte(this.mUseCacheFolder ? 1 : 0);
/* 251 */     dest.writeByte(this.mUseQuitAppMode ? 1 : 0);
/* 252 */     dest.writeParcelable(this.mConfig, flags);
/* 253 */     dest.writeInt(this.mNavigationIcon);
/* 254 */     dest.writeIntArray(this.mCustomToolbarMenu);
/* 255 */     dest.writeInt(this.mFileType);
/* 256 */     dest.writeSerializable(this.mTabFragmentClass);
/* 257 */     dest.writeSerializable(this.mTabHostFragmentClass);
/* 258 */     dest.writeString(this.mCustomHeaders);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 263 */     if (this == o) return true; 
/* 264 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/* 266 */     GenericViewerBuilder<?, ?, ?> that = (GenericViewerBuilder<?, ?, ?>)o;
/*     */     
/* 268 */     if (this.mUseCacheFolder != that.mUseCacheFolder) return false; 
/* 269 */     if (this.mUseQuitAppMode != that.mUseQuitAppMode) return false; 
/* 270 */     if (this.mNavigationIcon != that.mNavigationIcon) return false; 
/* 271 */     if (this.mFileType != that.mFileType) return false; 
/* 272 */     if ((this.mTabTitle != null) ? !this.mTabTitle.equals(that.mTabTitle) : (that.mTabTitle != null))
/* 273 */       return false; 
/* 274 */     if ((this.mFile != null) ? !this.mFile.equals(that.mFile) : (that.mFile != null)) return false; 
/* 275 */     if ((this.mPassword != null) ? !this.mPassword.equals(that.mPassword) : (that.mPassword != null))
/* 276 */       return false; 
/* 277 */     if ((this.mConfig != null) ? !this.mConfig.equals(that.mConfig) : (that.mConfig != null)) return false; 
/* 278 */     if (!Arrays.equals(this.mCustomToolbarMenu, that.mCustomToolbarMenu)) return false; 
/* 279 */     if ((this.mTabFragmentClass != null) ? !this.mTabFragmentClass.equals(that.mTabFragmentClass) : (that.mTabFragmentClass != null))
/* 280 */       return false; 
/* 281 */     if ((this.mTabHostFragmentClass != null) ? !this.mTabHostFragmentClass.equals(that.mTabHostFragmentClass) : (that.mTabHostFragmentClass != null))
/* 282 */       return false; 
/* 283 */     return (this.mCustomHeaders != null) ? this.mCustomHeaders.equals(that.mCustomHeaders) : ((that.mCustomHeaders == null));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 288 */     int result = (this.mTabTitle != null) ? this.mTabTitle.hashCode() : 0;
/* 289 */     result = 31 * result + ((this.mFile != null) ? this.mFile.hashCode() : 0);
/* 290 */     result = 31 * result + ((this.mPassword != null) ? this.mPassword.hashCode() : 0);
/* 291 */     result = 31 * result + (this.mUseCacheFolder ? 1 : 0);
/* 292 */     result = 31 * result + (this.mUseQuitAppMode ? 1 : 0);
/* 293 */     result = 31 * result + ((this.mConfig != null) ? this.mConfig.hashCode() : 0);
/* 294 */     result = 31 * result + this.mNavigationIcon;
/* 295 */     result = 31 * result + Arrays.hashCode(this.mCustomToolbarMenu);
/* 296 */     result = 31 * result + this.mFileType;
/* 297 */     result = 31 * result + ((this.mTabFragmentClass != null) ? this.mTabFragmentClass.hashCode() : 0);
/* 298 */     result = 31 * result + ((this.mTabHostFragmentClass != null) ? this.mTabHostFragmentClass.hashCode() : 0);
/* 299 */     result = 31 * result + ((this.mCustomHeaders != null) ? this.mCustomHeaders.hashCode() : 0);
/* 300 */     return result;
/*     */   }
/*     */   
/*     */   protected GenericViewerBuilder() {}
/*     */   
/*     */   @NonNull
/*     */   protected abstract Class<T> useDefaultTabFragmentClass();
/*     */   
/*     */   @NonNull
/*     */   protected abstract Class<TH> useDefaultTabHostFragmentClass();
/*     */   
/*     */   @NonNull
/*     */   protected abstract VB useBuilder();
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\config\GenericViewerBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */