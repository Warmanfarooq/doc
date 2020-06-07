/*     */ package com.pdftron.pdf.utils;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.content.SharedPreferences;
/*     */ import android.util.Log;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.reflect.TypeToken;
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.Action;
/*     */ import com.pdftron.pdf.Bookmark;
/*     */ import com.pdftron.pdf.Destination;
/*     */ import com.pdftron.pdf.PDFDoc;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.model.UserBookmarkItem;
/*     */ import com.pdftron.pdf.tools.ToolManager;
/*     */ import com.pdftron.sdf.Obj;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.json.JSONArray;
/*     */ import org.json.JSONException;
/*     */ import org.json.JSONObject;
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
/*     */ public class BookmarkManager
/*     */ {
/*  43 */   private static final String TAG = BookmarkManager.class.getName();
/*     */ 
/*     */   
/*     */   private static final String PREFS_CONTROLS_FILE_NAME = "com_pdftron_pdfnet_pdfviewctrl_controls_prefs_file";
/*     */ 
/*     */   
/*     */   private static final String KEY_PREF_USER_BOOKMARK = "user_bookmarks_key";
/*     */ 
/*     */   
/*     */   private static final String KEY_PREF_USER_BOOKMARK_OBJ_TITLE = "pdftronUserBookmarks";
/*     */ 
/*     */   
/*     */   public static ArrayList<UserBookmarkItem> fromJSON(String bookmarkJson) throws JSONException {
/*  56 */     JSONObject jsonObject = new JSONObject(bookmarkJson);
/*  57 */     ArrayList<UserBookmarkItem> bookmarkItems = new ArrayList<>();
/*     */     
/*  59 */     Iterator<String> keys = jsonObject.keys();
/*     */     
/*  61 */     while (keys.hasNext()) {
/*  62 */       String key = keys.next();
/*     */       try {
/*  64 */         if (jsonObject.get(key) instanceof String) {
/*  65 */           String title = jsonObject.getString(key);
/*  66 */           int pageIndex = Integer.parseInt(key);
/*     */           
/*  68 */           UserBookmarkItem item = new UserBookmarkItem();
/*  69 */           item.pageNumber = pageIndex + 1;
/*  70 */           item.title = title;
/*     */           
/*  72 */           bookmarkItems.add(item);
/*     */         } 
/*  74 */       } catch (NumberFormatException ex) {
/*  75 */         ex.printStackTrace();
/*     */       } 
/*     */     } 
/*  78 */     return bookmarkItems;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String toJSON(List<UserBookmarkItem> bookmarkItems) throws JSONException {
/*  88 */     JSONObject jsonObject = new JSONObject();
/*  89 */     for (UserBookmarkItem item : bookmarkItems) {
/*  90 */       int pageIndex = item.pageNumber - 1;
/*  91 */       jsonObject.put(String.valueOf(pageIndex), item.title);
/*     */     } 
/*  93 */     return jsonObject.toString();
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
/*     */   public static void importPdfBookmarks(PDFViewCtrl pdfViewCtrl, String bookmarkJson) throws JSONException {
/* 106 */     ArrayList<UserBookmarkItem> bookmarkItems = fromJSON(bookmarkJson);
/* 107 */     savePdfBookmarks(pdfViewCtrl, bookmarkItems, false, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String exportPdfBookmarks(PDFDoc pdfDoc) throws JSONException {
/* 117 */     List<UserBookmarkItem> bookmarkItems = getPdfBookmarks(pdfDoc);
/* 118 */     return toJSON(bookmarkItems);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Bookmark getRootPdfBookmark(PDFDoc pdfDoc, boolean createNew) {
/* 129 */     Bookmark bookmark = null;
/* 130 */     if (null != pdfDoc) {
/* 131 */       boolean shouldUnlockRead = false;
/*     */       try {
/* 133 */         pdfDoc.lockRead();
/* 134 */         shouldUnlockRead = true;
/* 135 */         Obj catalog = pdfDoc.getRoot();
/* 136 */         Obj bookmark_obj = catalog.findObj("pdftronUserBookmarks");
/* 137 */         if (null != bookmark_obj)
/*     */         {
/* 139 */           bookmark = new Bookmark(bookmark_obj);
/*     */         }
/* 141 */         else if (createNew)
/*     */         {
/* 143 */           bookmark = Bookmark.create(pdfDoc, "pdftronUserBookmarks");
/* 144 */           pdfDoc.getRoot().put("pdftronUserBookmarks", bookmark.getSDFObj());
/*     */         }
/*     */       
/* 147 */       } catch (PDFNetException e) {
/* 148 */         bookmark = null;
/*     */       } finally {
/* 150 */         if (shouldUnlockRead) {
/* 151 */           Utils.unlockReadQuietly(pdfDoc);
/*     */         }
/*     */       } 
/*     */     } 
/* 155 */     return bookmark;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ArrayList<Integer> getPdfBookmarkedPageNumbers(PDFDoc pdfDoc) {
/* 162 */     List<UserBookmarkItem> bookmarkItems = getPdfBookmarks(pdfDoc);
/* 163 */     Set<Integer> pages = new HashSet<>();
/* 164 */     for (UserBookmarkItem item : bookmarkItems) {
/* 165 */       pages.add(Integer.valueOf(item.pageNumber));
/*     */     }
/* 167 */     return new ArrayList<>(pages);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<UserBookmarkItem> getPdfBookmarks(PDFDoc pdfDoc) {
/* 176 */     Bookmark bookmark = getRootPdfBookmark(pdfDoc, false);
/* 177 */     return getPdfBookmarks(bookmark);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<UserBookmarkItem> getPdfBookmarks(Bookmark rootBookmark) {
/* 187 */     ArrayList<UserBookmarkItem> data = new ArrayList<>();
/* 188 */     if (null != rootBookmark) {
/*     */       try {
/* 190 */         if (rootBookmark.hasChildren()) {
/* 191 */           Bookmark item = rootBookmark.getFirstChild();
/* 192 */           for (; item.isValid(); item = item.getNext()) {
/* 193 */             UserBookmarkItem bookmarkItem = new UserBookmarkItem();
/* 194 */             bookmarkItem.isBookmarkEdited = false;
/* 195 */             bookmarkItem.pdfBookmark = item;
/* 196 */             bookmarkItem.title = item.getTitle();
/* 197 */             Action action = item.getAction();
/* 198 */             if (null != action && action.isValid() && 
/* 199 */               action.getType() == 0) {
/* 200 */               Destination dest = action.getDest();
/* 201 */               if (null != dest && dest.isValid()) {
/* 202 */                 bookmarkItem.pageNumber = dest.getPage().getIndex();
/* 203 */                 bookmarkItem.pageObjNum = dest.getPage().getSDFObj().getObjNum();
/* 204 */                 data.add(bookmarkItem);
/*     */               }
/*     */             
/*     */             } 
/*     */           } 
/*     */         } 
/* 210 */       } catch (PDFNetException ex) {
/* 211 */         AnalyticsHandlerAdapter.getInstance().sendException((Exception)ex);
/* 212 */         Log.e("PDFNet", ex.getMessage());
/*     */       } 
/*     */     }
/* 215 */     return data;
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
/*     */   public static void savePdfBookmarks(PDFViewCtrl pdfViewCtrl, List<UserBookmarkItem> data, boolean shouldTakeUndoSnapshot, boolean rebuild) {
/* 227 */     if (pdfViewCtrl == null) {
/*     */       return;
/*     */     }
/*     */     
/* 231 */     PDFDoc pdfDoc = pdfViewCtrl.getDoc();
/* 232 */     if (pdfDoc == null) {
/*     */       return;
/*     */     }
/*     */     
/* 236 */     if (data.size() > 0) {
/* 237 */       Bookmark rootBookmark = getRootPdfBookmark(pdfDoc, true);
/* 238 */       Bookmark firstBookmark = null;
/* 239 */       Bookmark currentBookmark = null;
/*     */       
/* 241 */       if (null != rootBookmark) {
/* 242 */         boolean hasChange = false;
/* 243 */         boolean shouldUnlock = false;
/*     */         try {
/* 245 */           if (rootBookmark.hasChildren()) {
/* 246 */             firstBookmark = rootBookmark.getFirstChild();
/*     */           }
/* 248 */           pdfDoc.lock();
/* 249 */           shouldUnlock = true;
/*     */           
/* 251 */           if (rebuild) {
/* 252 */             Obj catalog = pdfDoc.getRoot();
/* 253 */             if (catalog != null) {
/* 254 */               catalog.erase("pdftronUserBookmarks");
/*     */             }
/* 256 */             rootBookmark = getRootPdfBookmark(pdfDoc, true);
/* 257 */             firstBookmark = null;
/*     */           } 
/* 259 */           for (UserBookmarkItem item : data) {
/* 260 */             if (null == item.pdfBookmark) {
/* 261 */               if (null == currentBookmark) {
/*     */                 
/* 263 */                 if (null == firstBookmark) {
/*     */                   
/* 265 */                   currentBookmark = rootBookmark.addChild(item.title);
/* 266 */                   currentBookmark.setAction(Action.createGoto(Destination.createFit(pdfDoc.getPage(item.pageNumber))));
/* 267 */                   firstBookmark = currentBookmark;
/*     */                 } else {
/*     */                   
/* 270 */                   currentBookmark = firstBookmark.addPrev(item.title);
/* 271 */                   currentBookmark.setAction(Action.createGoto(Destination.createFit(pdfDoc.getPage(item.pageNumber))));
/* 272 */                   firstBookmark = currentBookmark;
/*     */                 } 
/*     */               } else {
/*     */                 
/* 276 */                 currentBookmark = currentBookmark.addNext(item.title);
/* 277 */                 currentBookmark.setAction(Action.createGoto(Destination.createFit(pdfDoc.getPage(item.pageNumber))));
/*     */               } 
/* 279 */               item.pdfBookmark = currentBookmark; continue;
/*     */             } 
/* 281 */             currentBookmark = item.pdfBookmark;
/* 282 */             if (item.isBookmarkEdited) {
/* 283 */               Action action = item.pdfBookmark.getAction();
/* 284 */               Destination dest = action.getDest();
/* 285 */               dest.setPage(pdfDoc.getPage(item.pageNumber));
/* 286 */               item.pdfBookmark.setTitle(item.title);
/*     */             } 
/*     */           } 
/*     */           
/* 290 */           hasChange = pdfDoc.hasChangesSinceSnapshot();
/* 291 */         } catch (Exception ex) {
/* 292 */           AnalyticsHandlerAdapter.getInstance().sendException(ex);
/* 293 */           Log.e("PDFNet", ex.getMessage());
/*     */         } finally {
/* 295 */           if (shouldUnlock) {
/* 296 */             Utils.unlockQuietly(pdfDoc);
/*     */           }
/*     */         } 
/*     */         
/* 300 */         if (shouldTakeUndoSnapshot && hasChange) {
/* 301 */           ToolManager toolManager = (ToolManager)pdfViewCtrl.getToolManager();
/* 302 */           if (toolManager != null) {
/* 303 */             toolManager.raiseBookmarkModified();
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } else {
/* 308 */       removeRootPdfBookmark(pdfViewCtrl, shouldTakeUndoSnapshot);
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
/*     */   public static boolean removeRootPdfBookmark(PDFViewCtrl pdfViewCtrl, boolean shouldTakeUndoSnapshot) {
/*     */     boolean hasChange;
/* 321 */     if (pdfViewCtrl == null) {
/* 322 */       return false;
/*     */     }
/*     */     
/* 325 */     PDFDoc pdfDoc = pdfViewCtrl.getDoc();
/* 326 */     if (pdfDoc == null) {
/* 327 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 331 */     boolean shouldUnlock = false;
/*     */     try {
/* 333 */       pdfDoc.lock();
/* 334 */       shouldUnlock = true;
/* 335 */       Obj catalog = pdfDoc.getRoot();
/* 336 */       if (catalog != null) {
/* 337 */         catalog.erase("pdftronUserBookmarks");
/*     */       }
/* 339 */       hasChange = pdfDoc.hasChangesSinceSnapshot();
/* 340 */     } catch (PDFNetException e) {
/* 341 */       AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/* 342 */       return false;
/*     */     } finally {
/* 344 */       if (shouldUnlock) {
/* 345 */         Utils.unlockQuietly(pdfDoc);
/*     */       }
/*     */     } 
/*     */     
/* 349 */     if (shouldTakeUndoSnapshot && hasChange) {
/* 350 */       ToolManager toolManager = (ToolManager)pdfViewCtrl.getToolManager();
/* 351 */       if (toolManager != null) {
/* 352 */         toolManager.raiseBookmarkModified();
/*     */       }
/*     */     } 
/*     */     
/* 356 */     return true;
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
/*     */   public static void addPdfBookmark(Context context, PDFViewCtrl pdfViewCtrl, long pageObjNum, int pageNumber) {
/* 368 */     if (context == null || pdfViewCtrl == null) {
/*     */       return;
/*     */     }
/*     */     
/* 372 */     PDFDoc pdfDoc = pdfViewCtrl.getDoc();
/* 373 */     if (pdfDoc == null) {
/*     */       return;
/*     */     }
/*     */     
/* 377 */     List<UserBookmarkItem> bookmarks = getPdfBookmarks(getRootPdfBookmark(pdfDoc, true));
/* 378 */     bookmarks.add(new UserBookmarkItem(context, pageObjNum, pageNumber));
/* 379 */     savePdfBookmarks(pdfViewCtrl, bookmarks, true, false);
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
/*     */   public static void onPageDeleted(PDFViewCtrl pdfViewCtrl, Long objNumber) {
/* 391 */     if (pdfViewCtrl == null) {
/*     */       return;
/*     */     }
/*     */     
/* 395 */     PDFDoc pdfDoc = pdfViewCtrl.getDoc();
/* 396 */     if (pdfDoc == null) {
/*     */       return;
/*     */     }
/*     */     
/* 400 */     List<UserBookmarkItem> items = getPdfBookmarks(getRootPdfBookmark(pdfDoc, false));
/* 401 */     boolean shouldUnlock = false;
/*     */     try {
/* 403 */       pdfDoc.lock();
/* 404 */       shouldUnlock = true;
/* 405 */       for (UserBookmarkItem item : items) {
/* 406 */         if (item.pageObjNum == objNumber.longValue()) {
/* 407 */           item.pdfBookmark.delete();
/*     */         }
/*     */       } 
/* 410 */     } catch (Exception e) {
/* 411 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */     } finally {
/* 413 */       if (shouldUnlock) {
/* 414 */         Utils.unlockQuietly(pdfDoc);
/*     */       }
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
/*     */   
/*     */   public static void onPageMoved(PDFViewCtrl pdfViewCtrl, long objNumber, long newObjNumber, int newPageNumber, boolean rebuild) {
/* 431 */     if (pdfViewCtrl == null) {
/*     */       return;
/*     */     }
/*     */     
/* 435 */     PDFDoc pdfDoc = pdfViewCtrl.getDoc();
/* 436 */     if (pdfDoc == null) {
/*     */       return;
/*     */     }
/*     */     
/* 440 */     List<UserBookmarkItem> items = getPdfBookmarks(getRootPdfBookmark(pdfDoc, false));
/* 441 */     boolean shouldUnlock = false;
/*     */     try {
/* 443 */       pdfDoc.lock();
/* 444 */       shouldUnlock = true;
/* 445 */       for (UserBookmarkItem item : items) {
/* 446 */         if (item.pageObjNum == objNumber) {
/* 447 */           item.pageObjNum = newObjNumber;
/* 448 */           item.pageNumber = newPageNumber;
/* 449 */           item.pdfBookmark.delete();
/* 450 */           item.pdfBookmark = null;
/*     */           break;
/*     */         } 
/*     */       } 
/* 454 */       savePdfBookmarks(pdfViewCtrl, items, false, rebuild);
/* 455 */     } catch (Exception e) {
/* 456 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */     } finally {
/* 458 */       if (shouldUnlock) {
/* 459 */         Utils.unlockQuietly(pdfDoc);
/*     */       }
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
/*     */   @NonNull
/*     */   public static ArrayList<Bookmark> getBookmarkList(@NonNull PDFDoc pdfDoc, @Nullable Bookmark firstSibling) {
/* 476 */     ArrayList<Bookmark> bookmarkList = new ArrayList<>();
/*     */     
/* 478 */     boolean shouldUnlockRead = false;
/*     */     try {
/*     */       Bookmark current;
/* 481 */       pdfDoc.lockRead();
/* 482 */       shouldUnlockRead = true;
/* 483 */       if (firstSibling == null || !firstSibling.isValid()) {
/* 484 */         current = pdfDoc.getFirstBookmark();
/*     */       } else {
/* 486 */         current = firstSibling;
/*     */       } 
/*     */       
/* 489 */       int numBookmarks = 0;
/* 490 */       while (current.isValid()) {
/* 491 */         bookmarkList.add(current);
/* 492 */         current = current.getNext();
/* 493 */         numBookmarks++;
/*     */       } 
/* 495 */       if (firstSibling == null && numBookmarks == 1) {
/* 496 */         ArrayList<Bookmark> bookmarkListNextLevel = getBookmarkList(pdfDoc, pdfDoc.getFirstBookmark().getFirstChild());
/* 497 */         if (bookmarkListNextLevel.size() > 0) {
/* 498 */           return bookmarkListNextLevel;
/*     */         }
/*     */       } 
/* 501 */     } catch (PDFNetException e) {
/* 502 */       bookmarkList.clear();
/*     */     } finally {
/* 504 */       if (shouldUnlockRead) {
/* 505 */         Utils.unlockReadQuietly(pdfDoc);
/*     */       }
/*     */     } 
/*     */     
/* 509 */     return bookmarkList;
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
/*     */   
/*     */   public static void importUserBookmarks(Context context, String filePath, String bookmarkJson) throws JSONException {
/* 525 */     ArrayList<UserBookmarkItem> bookmarkItems = fromJSON(bookmarkJson);
/* 526 */     saveUserBookmarks(context, filePath, bookmarkItems);
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
/*     */   public static String exportUserBookmarks(@NonNull Context context, @NonNull String filePath) throws JSONException {
/* 538 */     List<UserBookmarkItem> bookmarkItems = getUserBookmarks(context, filePath);
/* 539 */     return toJSON(bookmarkItems);
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
/*     */   public static List<UserBookmarkItem> getUserBookmarks(@Nullable Context context, String filePath) {
/* 551 */     ArrayList<UserBookmarkItem> userBookmarks = new ArrayList<>();
/* 552 */     if (context == null) {
/* 553 */       return userBookmarks;
/*     */     }
/* 555 */     SharedPreferences settings = context.getSharedPreferences("com_pdftron_pdfnet_pdfviewctrl_controls_prefs_file", 0);
/* 556 */     if (settings != null) {
/* 557 */       String serializedDocs = settings.getString("user_bookmarks_key" + filePath, "");
/* 558 */       if (!Utils.isNullOrEmpty(serializedDocs)) {
/*     */         try {
/* 560 */           JSONArray jsonArray = new JSONArray(serializedDocs);
/* 561 */           int count = jsonArray.length();
/* 562 */           for (int i = 0; i < count; i++) {
/* 563 */             UserBookmarkItem bookmarkItem = null;
/*     */             try {
/* 565 */               JSONObject jsonObject = jsonArray.getJSONObject(i);
/* 566 */               bookmarkItem = new UserBookmarkItem(jsonObject);
/* 567 */             } catch (Exception e) {
/* 568 */               AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */             } 
/* 570 */             if (bookmarkItem != null) {
/* 571 */               userBookmarks.add(bookmarkItem);
/*     */             }
/*     */           } 
/* 574 */         } catch (Exception e) {
/* 575 */           Log.e(TAG, e.toString());
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 580 */     return userBookmarks;
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
/*     */   public static void saveUserBookmarks(Context context, String filePath, List<UserBookmarkItem> data) {
/* 592 */     SharedPreferences settings = context.getSharedPreferences("com_pdftron_pdfnet_pdfviewctrl_controls_prefs_file", 0);
/* 593 */     SharedPreferences.Editor editor = settings.edit();
/* 594 */     Gson gson = new Gson();
/*     */     
/* 596 */     Type collectionType = (new TypeToken<ArrayList<UserBookmarkItem>>() {  }).getType();
/* 597 */     String serializedDocs = gson.toJson(data, collectionType);
/*     */     
/* 599 */     editor.putString("user_bookmarks_key" + filePath, serializedDocs);
/* 600 */     editor.apply();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void removeUserBookmarks(@Nullable Context context, String filePath) {
/* 611 */     if (context == null) {
/*     */       return;
/*     */     }
/* 614 */     SharedPreferences settings = context.getSharedPreferences("com_pdftron_pdfnet_pdfviewctrl_controls_prefs_file", 0);
/* 615 */     SharedPreferences.Editor editor = settings.edit();
/* 616 */     editor.remove("user_bookmarks_key" + filePath);
/* 617 */     editor.apply();
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
/*     */   public static void addUserBookmark(Context context, String filePath, long pageObjNum, int pageNumber) {
/* 630 */     if (context == null || Utils.isNullOrEmpty(filePath)) {
/*     */       return;
/*     */     }
/* 633 */     List<UserBookmarkItem> bookmarks = getUserBookmarks(context, filePath);
/* 634 */     bookmarks.add(new UserBookmarkItem(context, pageObjNum, pageNumber));
/* 635 */     saveUserBookmarks(context, filePath, bookmarks);
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
/*     */   public static void updateUserBookmarkPageObj(Context context, String filePath, long pageObjNum, long newPageObjNum, int newPageNum) {
/* 650 */     List<UserBookmarkItem> items = getUserBookmarks(context, filePath);
/* 651 */     for (UserBookmarkItem item : items) {
/* 652 */       if (item.pageObjNum == pageObjNum) {
/* 653 */         item.pageObjNum = newPageObjNum;
/* 654 */         item.pageNumber = newPageNum;
/*     */       } 
/*     */     } 
/* 657 */     saveUserBookmarks(context, filePath, items);
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
/*     */   public static void onPageDeleted(Context context, String filePath, Long objNumber, int pageNumber, int pageCount) {
/* 671 */     List<UserBookmarkItem> items = getUserBookmarks(context, filePath);
/* 672 */     List<UserBookmarkItem> newItems = new ArrayList<>();
/* 673 */     for (UserBookmarkItem item : items) {
/* 674 */       if (item.pageObjNum != objNumber.longValue()) {
/* 675 */         newItems.add(item);
/*     */       }
/*     */     } 
/* 678 */     saveUserBookmarks(context, filePath, newItems);
/* 679 */     updateUserBookmarksAfterRearranging(context, filePath, pageNumber, pageCount, false, -1L);
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
/*     */   public static void onPageMoved(Context context, String filePath, long objNumber, long newObjNumber, int oldPageNumber, int newPageNumber) {
/* 694 */     updateUserBookmarkPageObj(context, filePath, objNumber, newObjNumber, newPageNumber);
/* 695 */     if (oldPageNumber < newPageNumber) {
/* 696 */       updateUserBookmarksAfterRearranging(context, filePath, oldPageNumber + 1, newPageNumber, false, newObjNumber);
/*     */     } else {
/* 698 */       updateUserBookmarksAfterRearranging(context, filePath, newPageNumber, oldPageNumber - 1, true, newObjNumber);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void updateUserBookmarksAfterRearranging(Context context, String filePath, int fromPage, int toPage, boolean increment, long ignoreObjNumber) {
/* 703 */     if (fromPage > toPage) {
/* 704 */       int temp = fromPage;
/* 705 */       fromPage = toPage;
/* 706 */       toPage = temp;
/*     */     } 
/* 708 */     int change = -1;
/* 709 */     if (increment) {
/* 710 */       change = 1;
/*     */     }
/* 712 */     List<UserBookmarkItem> items = getUserBookmarks(context, filePath);
/* 713 */     for (UserBookmarkItem item : items) {
/* 714 */       if (item.pageNumber >= fromPage && item.pageNumber <= toPage && item.pageObjNum != ignoreObjNumber) {
/* 715 */         item.pageNumber += change;
/*     */       }
/*     */     } 
/* 718 */     saveUserBookmarks(context, filePath, items);
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
/*     */   public static void updateUserBookmarksFilePath(Context context, String oldPath, String newPath) {
/* 730 */     List<UserBookmarkItem> items = getUserBookmarks(context, oldPath);
/* 731 */     if (items.size() > 0) {
/* 732 */       saveUserBookmarks(context, newPath, items);
/* 733 */       removeUserBookmarks(context, oldPath);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pd\\utils\BookmarkManager.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */