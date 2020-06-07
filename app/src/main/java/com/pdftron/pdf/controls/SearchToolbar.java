/*     */ package com.pdftron.pdf.controls;
/*     */ 
/*     */ import android.content.ActivityNotFoundException;
/*     */ import android.content.Context;
/*     */ import android.content.Intent;
/*     */ import android.content.res.TypedArray;
/*     */ import android.os.Handler;
/*     */ import android.os.Looper;
/*     */ import android.util.AttributeSet;
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.Menu;
/*     */ import android.view.MenuItem;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.widget.EditText;
/*     */ import android.widget.ImageView;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.appcompat.widget.SearchView;
/*     */ import androidx.appcompat.widget.Toolbar;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.utils.ShortcutHelper;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SearchToolbar
/*     */   extends Toolbar
/*     */ {
/*     */   private static final int SHOW_SEARCH_PROGRESS_TIMER = 500;
/*     */   protected MenuItem mMenuListAll;
/*     */   protected MenuItem mMenuMatchCase;
/*     */   protected MenuItem mMenuWholeWord;
/*     */   protected MenuItem mMenuSearchWeb;
/*     */   protected MenuItem mMenuProgress;
/*     */   protected SearchView mSearchView;
/*     */   protected String mSearchQuery;
/*     */   private boolean mJustSubmittedQuery;
/*     */   private SearchToolbarListener mSearchToolbarListener;
/*     */   
/*     */   public void setSearchToolbarListener(SearchToolbarListener listener) {
/*  45 */     this.mSearchToolbarListener = listener;
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
/*  57 */   private Handler mShowSearchProgressHandler = new Handler(Looper.getMainLooper());
/*  58 */   private Runnable mShowSearchProgressRunnable = new Runnable()
/*     */     {
/*     */       public void run() {
/*  61 */         if (SearchToolbar.this.mMenuProgress == null) {
/*     */           return;
/*     */         }
/*  64 */         if (!SearchToolbar.this.mMenuProgress.isVisible()) {
/*  65 */           SearchToolbar.this.mMenuProgress.setVisible(true);
/*     */         }
/*     */       }
/*     */     };
/*     */   
/*     */   public SearchToolbar(Context context) {
/*  71 */     this(context, (AttributeSet)null);
/*     */   }
/*     */   
/*     */   public SearchToolbar(Context context, @Nullable AttributeSet attrs) {
/*  75 */     this(context, attrs, R.attr.search_toolbar);
/*     */   }
/*     */   
/*     */   public SearchToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
/*  79 */     super(context, attrs, defStyleAttr);
/*  80 */     init(context, attrs, defStyleAttr, R.style.SearchToolbarStyle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void init(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
/*     */     boolean listAllVisible, matchCaseVisible, wholeWordVisible, searchWebVisible;
/*  89 */     TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SearchToolbar, defStyleAttr, defStyleRes);
/*     */     try {
/*  91 */       listAllVisible = typedArray.getBoolean(R.styleable.SearchToolbar_showListAll, true);
/*  92 */       matchCaseVisible = typedArray.getBoolean(R.styleable.SearchToolbar_showMatchCase, true);
/*  93 */       wholeWordVisible = typedArray.getBoolean(R.styleable.SearchToolbar_showWholeWord, true);
/*  94 */       searchWebVisible = typedArray.getBoolean(R.styleable.SearchToolbar_showSearchWeb, true);
/*     */     } finally {
/*  96 */       typedArray.recycle();
/*     */     } 
/*     */     
/*  99 */     inflateMenu(R.menu.controls_search_toolbar);
/* 100 */     Menu menu = getMenu();
/*     */     
/* 102 */     this.mMenuListAll = menu.findItem(R.id.action_list_all);
/* 103 */     if (this.mMenuListAll != null) {
/* 104 */       this.mMenuListAll.setEnabled(false);
/* 105 */       this.mMenuListAll.setVisible(listAllVisible);
/*     */     } 
/* 107 */     this.mMenuMatchCase = menu.findItem(R.id.action_match_case);
/* 108 */     if (this.mMenuMatchCase != null) {
/* 109 */       this.mMenuMatchCase.setVisible(matchCaseVisible);
/*     */     }
/* 111 */     this.mMenuWholeWord = menu.findItem(R.id.action_whole_word);
/* 112 */     if (this.mMenuWholeWord != null) {
/* 113 */       this.mMenuWholeWord.setVisible(wholeWordVisible);
/*     */     }
/*     */     
/* 116 */     this.mMenuSearchWeb = menu.findItem(R.id.action_search_web);
/* 117 */     if (this.mMenuSearchWeb != null) {
/* 118 */       this.mMenuSearchWeb.setVisible(searchWebVisible);
/*     */     }
/*     */     
/* 121 */     this.mMenuProgress = menu.findItem(R.id.search_progress);
/*     */     
/* 123 */     setNavigationOnClickListener(new OnClickListener()
/*     */         {
/*     */           public void onClick(View view) {
/* 126 */             if (SearchToolbar.this.mSearchToolbarListener != null) {
/* 127 */               SearchToolbar.this.mSearchToolbarListener.onExitSearch();
/*     */             }
/*     */           }
/*     */         });
/*     */     
/* 132 */     setOnMenuItemClickListener(new OnMenuItemClickListener()
/*     */         {
/*     */           public boolean onMenuItemClick(MenuItem item) {
/* 135 */             int id = item.getItemId();
/* 136 */             if (id == R.id.action_search_web) {
/* 137 */               if (SearchToolbar.this.mSearchView != null && SearchToolbar.this.mSearchView.getQuery() != null) {
/* 138 */                 String query = SearchToolbar.this.mSearchView.getQuery().toString();
/* 139 */                 if (!Utils.isNullOrEmpty(query)) {
/* 140 */                   Intent intent = new Intent("android.intent.action.WEB_SEARCH");
/* 141 */                   intent.putExtra("query", query);
/* 142 */                   if (intent.resolveActivity(SearchToolbar.this.getContext().getPackageManager()) != null) {
/*     */                     try {
/* 144 */                       SearchToolbar.this.getContext().startActivity(intent);
/* 145 */                     } catch (ActivityNotFoundException activityNotFoundException) {}
/*     */                   }
/*     */                 }
/*     */               
/*     */               }
/*     */             
/* 151 */             } else if (id == R.id.action_list_all || id == R.id.action_match_case || id == R.id.action_whole_word) {
/*     */ 
/*     */               
/* 154 */               String searchQuery = null;
/* 155 */               if (SearchToolbar.this.mSearchView != null && SearchToolbar.this.mSearchView.getQuery().length() > 0) {
/* 156 */                 searchQuery = SearchToolbar.this.mSearchView.getQuery().toString();
/*     */               }
/* 158 */               if (id == R.id.action_list_all) {
/* 159 */                 Utils.hideSoftKeyboard(SearchToolbar.this.getContext(), (View)SearchToolbar.this.mSearchView);
/*     */               }
/* 161 */               if (SearchToolbar.this.mSearchToolbarListener != null) {
/* 162 */                 SearchToolbar.this.mSearchToolbarListener.onSearchOptionsItemSelected(item, searchQuery);
/*     */               }
/*     */             } 
/* 165 */             return false;
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 170 */     LayoutInflater inflater = (LayoutInflater)getContext().getSystemService("layout_inflater");
/* 171 */     View view = inflater.inflate(R.layout.controls_search_toolbar, (ViewGroup)this);
/* 172 */     this.mSearchView = (SearchView)view.findViewById(R.id.searchView);
/*     */     
/* 174 */     if (Utils.isTablet(getContext()))
/*     */     {
/* 176 */       this.mSearchView.setIconifiedByDefault(false);
/*     */     }
/* 178 */     if (Utils.isTablet(getContext()) && !Utils.isRtlLayout(getContext())) {
/*     */       
/* 180 */       LayoutParams params = new LayoutParams(8388613);
/* 181 */       params.width = getResources().getDimensionPixelSize(R.dimen.viewer_search_bar_width);
/* 182 */       this.mSearchView.setLayoutParams((ViewGroup.LayoutParams)params);
/*     */     } 
/* 184 */     this.mSearchView.setFocusable(true);
/* 185 */     this.mSearchView.setFocusableInTouchMode(true);
/* 186 */     this.mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
/*     */         {
/*     */           public boolean onQueryTextSubmit(String query) {
/* 189 */             if (ShortcutHelper.isEnabled() && SearchToolbar.this.mSearchView != null)
/*     */             {
/*     */               
/* 192 */               SearchToolbar.this.mSearchView.clearFocus();
/*     */             }
/*     */ 
/*     */             
/* 196 */             SearchToolbar.this.mJustSubmittedQuery = true;
/* 197 */             (new Handler()).postDelayed(new Runnable()
/*     */                 {
/*     */                   public void run() {
/* 200 */                     SearchToolbar.this.mJustSubmittedQuery = false;
/*     */                   }
/*     */                 },  250L);
/*     */             
/* 204 */             if (SearchToolbar.this.mSearchView != null) {
/* 205 */               Utils.hideSoftKeyboard(SearchToolbar.this.getContext(), (View)SearchToolbar.this.mSearchView);
/*     */             }
/* 207 */             if (SearchToolbar.this.mSearchToolbarListener != null) {
/* 208 */               SearchToolbar.this.mSearchToolbarListener.onSearchQuerySubmit(query);
/*     */             }
/* 210 */             return true;
/*     */           }
/*     */ 
/*     */           
/*     */           public boolean onQueryTextChange(String newText) {
/* 215 */             if (SearchToolbar.this.mMenuListAll != null) {
/* 216 */               if (!Utils.isNullOrEmpty(newText)) {
/* 217 */                 SearchToolbar.this.mMenuListAll.setEnabled(true);
/*     */               } else {
/* 219 */                 SearchToolbar.this.mMenuListAll.setEnabled(false);
/*     */               } 
/*     */             }
/* 222 */             if (SearchToolbar.this.mSearchToolbarListener != null) {
/* 223 */               SearchToolbar.this.mSearchToolbarListener.onSearchQueryChange(newText);
/*     */             }
/* 225 */             return false;
/*     */           }
/*     */         });
/* 228 */     if (Utils.isTablet(getContext())) {
/* 229 */       this.mSearchView.setQueryHint(getContext().getString(R.string.search_hint));
/*     */     } else {
/* 231 */       this.mSearchView.setQueryHint(getContext().getString(R.string.action_search));
/*     */     } 
/* 233 */     ImageView searchCloseButton = (ImageView)this.mSearchView.findViewById(R.id.search_close_btn);
/* 234 */     searchCloseButton.setOnClickListener(new OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/* 237 */             EditText editText = (EditText)SearchToolbar.this.mSearchView.findViewById(R.id.search_src_text);
/* 238 */             if (editText != null)
/*     */             {
/* 240 */               editText.setText("");
/*     */             }
/* 242 */             SearchToolbar.this.mSearchView.setQuery("", false);
/*     */             
/* 244 */             if (SearchToolbar.this.mSearchToolbarListener != null) {
/* 245 */               SearchToolbar.this.mSearchToolbarListener.onClearSearchQuery();
/*     */             }
/* 247 */             SearchToolbar.this.setSearchProgressBarVisible(false);
/*     */             
/* 249 */             SearchToolbar.this.mSearchView.requestFocus();
/* 250 */             Utils.showSoftKeyboard(SearchToolbar.this.getContext(), (View)editText);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void setVisibility(int visibility) {
/* 257 */     super.setVisibility(visibility);
/*     */     
/* 259 */     if (visibility == 0) {
/* 260 */       if (this.mSearchView != null) {
/* 261 */         this.mSearchView.setIconified(false);
/*     */       }
/* 263 */       startSearchMode();
/*     */     } else {
/*     */       
/* 266 */       if (this.mSearchView != null) {
/* 267 */         this.mSearchQuery = this.mSearchView.getQuery().toString();
/* 268 */         this.mSearchView.setIconified(true);
/*     */       } 
/* 270 */       exitSearchMode();
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void startSearchMode() {
/* 275 */     if (this.mSearchView != null) {
/* 276 */       if (this.mSearchQuery != null && this.mSearchQuery.length() > 0)
/*     */       {
/* 278 */         this.mSearchView.setQuery(this.mSearchQuery, false);
/*     */       }
/* 280 */       this.mSearchView.requestFocus();
/* 281 */       EditText editText = (EditText)this.mSearchView.findViewById(R.id.search_src_text);
/*     */       
/* 283 */       Utils.showSoftKeyboard(getContext(), (View)editText);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void exitSearchMode() {
/* 288 */     if (this.mSearchView != null) {
/* 289 */       this.mSearchView.clearFocus();
/* 290 */       Utils.hideSoftKeyboard(getContext(), (View)this.mSearchView);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setSearchProgressBarVisible(boolean visible) {
/* 295 */     if (visible) {
/* 296 */       startShowSearchProgressTimer();
/*     */     } else {
/* 298 */       stopShowSearchProgressTimer();
/* 299 */       if (this.mMenuProgress != null) {
/* 300 */         this.mMenuProgress.setVisible(false);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void startShowSearchProgressTimer() {
/* 306 */     stopShowSearchProgressTimer();
/* 307 */     if (this.mShowSearchProgressHandler != null) {
/* 308 */       this.mShowSearchProgressHandler.postDelayed(this.mShowSearchProgressRunnable, 500L);
/*     */     }
/*     */   }
/*     */   
/*     */   public void stopShowSearchProgressTimer() {
/* 313 */     if (this.mShowSearchProgressHandler != null) {
/* 314 */       this.mShowSearchProgressHandler.removeCallbacksAndMessages(null);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isJustSubmittedQuery() {
/* 319 */     return this.mJustSubmittedQuery;
/*     */   }
/*     */   
/*     */   public void setJustSubmittedQuery(boolean justSubmittedQuery) {
/* 323 */     this.mJustSubmittedQuery = justSubmittedQuery;
/*     */   }
/*     */   
/*     */   public SearchView getSearchView() {
/* 327 */     return this.mSearchView;
/*     */   }
/*     */   
/*     */   public void pause() {
/* 331 */     stopShowSearchProgressTimer();
/*     */   }
/*     */   
/*     */   public static interface SearchToolbarListener {
/*     */     void onExitSearch();
/*     */     
/*     */     void onClearSearchQuery();
/*     */     
/*     */     void onSearchQuerySubmit(String param1String);
/*     */     
/*     */     void onSearchQueryChange(String param1String);
/*     */     
/*     */     void onSearchOptionsItemSelected(MenuItem param1MenuItem, String param1String);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\controls\SearchToolbar.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */