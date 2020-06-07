/*     */ package com.pdftron.pdf.controls;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.os.Bundle;
/*     */ import android.os.Parcel;
/*     */ import android.os.Parcelable;
/*     */ import android.util.AttributeSet;
/*     */ import android.util.Log;
/*     */ import android.view.View;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.fragment.app.Fragment;
/*     */ import androidx.fragment.app.FragmentManager;
/*     */ import androidx.fragment.app.FragmentTransaction;
/*     */ import com.google.android.material.tabs.TabLayout;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CustomFragmentTabLayout
/*     */   extends TabLayout
/*     */   implements TabLayout.BaseOnTabSelectedListener
/*     */ {
/*  30 */   private static final String TAG = CustomFragmentTabLayout.class.getName();
/*     */   
/*     */   private static boolean sDebug;
/*     */   protected Context mContext;
/*     */   protected FragmentManager mFragmentManager;
/*  35 */   protected final ArrayList<TabInfo> mTabs = new ArrayList<>();
/*     */   
/*     */   protected int mContainerId;
/*     */   
/*     */   protected TabInfo mLastTabInfo;
/*     */   protected boolean mShouldMemorizeTab = true;
/*  41 */   private final ArrayList<TabLayout.BaseOnTabSelectedListener> mSelectedListeners = new ArrayList<>();
/*     */   
/*     */   static final class TabInfo {
/*     */     final Bundle mArgs;
/*     */     final Class<?> mClass;
/*     */     String mTag;
/*     */     Fragment mFragment;
/*     */     
/*     */     TabInfo(String tag, Class<?> _class, Bundle args) {
/*  50 */       this.mTag = tag;
/*  51 */       this.mClass = _class;
/*  52 */       this.mArgs = args;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class SavedState extends View.BaseSavedState {
/*     */     String curTab;
/*     */     
/*     */     SavedState(Parcelable superState) {
/*  60 */       super(superState);
/*     */     }
/*     */     
/*     */     private SavedState(Parcel in) {
/*  64 */       super(in);
/*  65 */       this.curTab = in.readString();
/*     */     }
/*     */ 
/*     */     
/*     */     public void writeToParcel(Parcel out, int flags) {
/*  70 */       super.writeToParcel(out, flags);
/*  71 */       out.writeString(this.curTab);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  76 */       return "FragmentTabLayout.SavedState{" + 
/*  77 */         Integer.toHexString(System.identityHashCode(this)) + " curTab=" + this.curTab + "}";
/*     */     }
/*     */ 
/*     */     
/*  81 */     public static final Creator<SavedState> CREATOR = new Creator<SavedState>()
/*     */       {
/*     */         public SavedState createFromParcel(Parcel in) {
/*  84 */           return new SavedState(in);
/*     */         }
/*     */         
/*     */         public SavedState[] newArray(int size) {
/*  88 */           return new SavedState[size];
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CustomFragmentTabLayout(Context context) {
/*  99 */     this(context, (AttributeSet)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CustomFragmentTabLayout(Context context, AttributeSet attrs) {
/* 106 */     this(context, attrs, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CustomFragmentTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
/* 113 */     super(context, attrs, defStyleAttr);
/* 114 */     super.addOnTabSelectedListener(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Parcelable onSaveInstanceState() {
/* 122 */     Parcelable superState = super.onSaveInstanceState();
/* 123 */     SavedState ss = new SavedState(superState);
/* 124 */     if (this.mShouldMemorizeTab) {
/* 125 */       ss.curTab = getCurrentTabTag();
/*     */     }
/* 127 */     return (Parcelable)ss;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onRestoreInstanceState(Parcelable state) {
/* 135 */     SavedState ss = (SavedState)state;
/* 136 */     super.onRestoreInstanceState(ss.getSuperState());
/* 137 */     if (this.mShouldMemorizeTab) {
/* 138 */       TabLayout.Tab tab = getTabByTag(ss.curTab);
/* 139 */       if (tab != null) {
/* 140 */         tab.select();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addOnTabSelectedListener(@NonNull TabLayout.BaseOnTabSelectedListener listener) {
/* 150 */     super.addOnTabSelectedListener(listener);
/* 151 */     if (!this.mSelectedListeners.contains(listener)) {
/* 152 */       this.mSelectedListeners.add(listener);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeOnTabSelectedListener(@NonNull TabLayout.BaseOnTabSelectedListener listener) {
/* 161 */     super.removeOnTabSelectedListener(listener);
/* 162 */     this.mSelectedListeners.remove(listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearOnTabSelectedListeners() {
/* 170 */     super.clearOnTabSelectedListeners();
/* 171 */     this.mSelectedListeners.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onTabSelected(TabLayout.Tab tab) {
/* 179 */     if (tab == null || tab.getTag() == null) {
/*     */       return;
/*     */     }
/*     */     
/* 183 */     startFragment((String)tab.getTag());
/*     */     
/* 185 */     for (int i = this.mSelectedListeners.size() - 1; i >= 0; i--) {
/* 186 */       ((TabLayout.BaseOnTabSelectedListener)this.mSelectedListeners.get(i)).onTabSelected(tab);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onTabUnselected(TabLayout.Tab tab) {
/* 195 */     if (tab == null) {
/*     */       return;
/*     */     }
/*     */     
/* 199 */     for (int i = this.mSelectedListeners.size() - 1; i >= 0; i--) {
/* 200 */       ((TabLayout.BaseOnTabSelectedListener)this.mSelectedListeners.get(i)).onTabUnselected(tab);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onTabReselected(TabLayout.Tab tab) {
/* 209 */     if (tab == null) {
/*     */       return;
/*     */     }
/*     */     
/* 213 */     for (int i = this.mSelectedListeners.size() - 1; i >= 0; i--) {
/* 214 */       ((TabLayout.BaseOnTabSelectedListener)this.mSelectedListeners.get(i)).onTabReselected(tab);
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
/*     */   public void setup(Context context, FragmentManager manager, int containerId) {
/* 226 */     this.mContext = context;
/* 227 */     this.mFragmentManager = manager;
/* 228 */     this.mContainerId = containerId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addTab(@NonNull TabLayout.Tab tab, Class<?> _class, Bundle args) {
/* 239 */     String tag = (String)tab.getTag();
/* 240 */     if (Utils.isNullOrEmpty(tag)) {
/*     */       return;
/*     */     }
/*     */     
/* 244 */     TabInfo tabInfo = new TabInfo(tag, _class, args);
/* 245 */     this.mTabs.add(tabInfo);
/* 246 */     addTab(tab, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startFragment(String tag) {
/* 255 */     if (tag == null) {
/*     */       return;
/*     */     }
/*     */     
/* 259 */     TabInfo newTabInfo = null;
/* 260 */     for (int i = 0, sz = this.mTabs.size(); i < sz; i++) {
/* 261 */       TabInfo tabInfo = this.mTabs.get(i);
/* 262 */       if (tabInfo.mTag.equals(tag)) {
/* 263 */         newTabInfo = tabInfo;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 268 */     if (newTabInfo == null) {
/* 269 */       throw new IllegalStateException("No tab known for tag " + tag);
/*     */     }
/*     */     
/* 272 */     if (this.mLastTabInfo != newTabInfo) {
/* 273 */       if (sDebug) {
/* 274 */         Log.d(TAG, "start fragment " + newTabInfo.mTag);
/*     */       }
/* 276 */       FragmentTransaction ft = this.mFragmentManager.beginTransaction();
/*     */       
/* 278 */       if (this.mLastTabInfo != null && this.mLastTabInfo.mFragment != null) {
/* 279 */         ft.hide(this.mLastTabInfo.mFragment);
/*     */       }
/*     */       
/* 282 */       boolean isAdded = false;
/* 283 */       if (newTabInfo.mFragment == null) {
/* 284 */         Fragment fragmentInManager = null;
/* 285 */         for (Fragment fragment : this.mFragmentManager.getFragments()) {
/* 286 */           if (fragment instanceof PdfViewCtrlTabFragment) {
/* 287 */             String tabTag = ((PdfViewCtrlTabFragment)fragment).getTabTag();
/* 288 */             if (newTabInfo.mTag.equals(tabTag)) {
/* 289 */               fragmentInManager = fragment;
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/* 294 */         if (fragmentInManager != null) {
/* 295 */           newTabInfo.mFragment = fragmentInManager;
/*     */         } else {
/* 297 */           newTabInfo.mFragment = Fragment.instantiate(this.mContext, newTabInfo.mClass
/* 298 */               .getName(), newTabInfo.mArgs);
/* 299 */           ft.add(this.mContainerId, newTabInfo.mFragment);
/* 300 */           isAdded = true;
/*     */         } 
/*     */       } 
/*     */       
/* 304 */       if (!isAdded) {
/* 305 */         if (newTabInfo.mFragment.isHidden()) {
/* 306 */           ft.show(newTabInfo.mFragment);
/* 307 */         } else if (!newTabInfo.mFragment.isDetached()) {
/*     */ 
/*     */ 
/*     */           
/* 311 */           ft.show(newTabInfo.mFragment);
/*     */         } else {
/* 313 */           ft.attach(newTabInfo.mFragment);
/*     */         } 
/*     */       }
/*     */       
/* 317 */       ft.commitAllowingStateLoss();
/*     */       
/* 319 */       this.mLastTabInfo = newTabInfo;
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
/*     */   public void removeTab(TabLayout.Tab tab) {
/* 332 */     String tag = (String)tab.getTag();
/*     */     
/* 334 */     if (Utils.isNullOrEmpty(tag)) {
/*     */       return;
/*     */     }
/*     */     
/* 338 */     TabInfo curTabInfo = null;
/* 339 */     for (int i = 0, sz = this.mTabs.size(); i < sz; i++) {
/* 340 */       TabInfo tabInfo = this.mTabs.get(i);
/* 341 */       if (tabInfo.mTag.equals(tag)) {
/* 342 */         curTabInfo = tabInfo;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 347 */     if (curTabInfo != null) {
/* 348 */       if (curTabInfo.mFragment != null) {
/* 349 */         FragmentTransaction ft = this.mFragmentManager.beginTransaction();
/* 350 */         ft.remove(curTabInfo.mFragment);
/* 351 */         ft.commitAllowingStateLoss();
/*     */       } 
/*     */       
/* 354 */       this.mTabs.remove(curTabInfo);
/*     */     } 
/*     */     
/* 357 */     super.removeTab(tab);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAllFragments() {
/* 364 */     FragmentTransaction ft = this.mFragmentManager.beginTransaction();
/* 365 */     for (TabInfo tabInfo : this.mTabs) {
/* 366 */       if (tabInfo.mFragment != null) {
/* 367 */         ft.remove(tabInfo.mFragment);
/*     */       }
/*     */     } 
/* 370 */     ft.commitAllowingStateLoss();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TabLayout.Tab getTabByTag(String tag) {
/* 380 */     TabLayout.Tab tab = null;
/* 381 */     if (tag != null) {
/* 382 */       for (int i = 0, sz = getTabCount(); i < sz; i++) {
/* 383 */         TabLayout.Tab tab_i = getTabAt(i);
/* 384 */         if (tab_i != null) {
/* 385 */           String tag_i = (String)tab_i.getTag();
/* 386 */           if (tag_i != null && tag.equals(tag_i)) {
/* 387 */             tab = tab_i;
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/* 393 */     return tab;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void replaceTag(@NonNull TabLayout.Tab tab, @NonNull String newTag) {
/* 403 */     TabInfo curTabInfo = null;
/* 404 */     String oldTag = (String)tab.getTag();
/* 405 */     for (int i = 0, sz = this.mTabs.size(); i < sz; i++) {
/* 406 */       TabInfo tabInfo = this.mTabs.get(i);
/* 407 */       if (tabInfo.mTag.equals(oldTag)) {
/* 408 */         curTabInfo = tabInfo;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 413 */     if (curTabInfo == null) {
/*     */       return;
/*     */     }
/*     */     
/* 417 */     curTabInfo.mTag = newTag;
/* 418 */     tab.setTag(newTag);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getCurrentTabTag() {
/* 427 */     int selectedTabPosition = getSelectedTabPosition();
/* 428 */     if (selectedTabPosition == -1) {
/* 429 */       return null;
/*     */     }
/* 431 */     TabLayout.Tab tab = getTabAt(selectedTabPosition);
/* 432 */     return (tab == null) ? null : (String)tab.getTag();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Fragment getFragmentByTag(String tag) {
/* 442 */     if (tag != null) {
/* 443 */       for (int i = 0, sz = this.mTabs.size(); i < sz; i++) {
/* 444 */         TabInfo tabInfo = this.mTabs.get(i);
/* 445 */         if (tabInfo.mTag.equals(tag)) {
/* 446 */           return tabInfo.mFragment;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 451 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Fragment getCurrentFragment() {
/* 460 */     return getFragmentByTag(getCurrentTabTag());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<Fragment> getLiveFragments() {
/* 469 */     ArrayList<Fragment> fragments = new ArrayList<>();
/* 470 */     for (int i = 0, sz = this.mTabs.size(); i < sz; i++) {
/* 471 */       TabInfo tabInfo = this.mTabs.get(i);
/* 472 */       if (tabInfo.mFragment != null) {
/* 473 */         fragments.add(tabInfo.mFragment);
/*     */       }
/*     */     } 
/*     */     
/* 477 */     return fragments;
/*     */   }
/*     */   
/*     */   public static void setDebug(boolean debug) {
/* 481 */     sDebug = debug;
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\controls\CustomFragmentTabLayout.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */