/*      */ package com.pdftron.pdf.utils;
/*      */ 
/*      */ import android.util.Log;
/*      */ import androidx.annotation.IdRes;
/*      */ import androidx.annotation.NonNull;
/*      */ import androidx.annotation.Nullable;
/*      */ import com.pdftron.pdf.tools.R;
/*      */ import com.pdftron.pdf.tools.ToolManager;
/*      */ import java.lang.annotation.Retention;
/*      */ import java.lang.annotation.RetentionPolicy;
/*      */ import java.util.Map;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class AnalyticsHandlerAdapter
/*      */ {
/*   32 */   private static final String TAG = AnalyticsHandlerAdapter.class.getName();
/*      */   
/*      */   public static final int CATEGORY_VIEWER = 1;
/*      */   
/*      */   public static final int CATEGORY_FILEBROWSER = 2;
/*      */   
/*      */   public static final int CATEGORY_GENERAL = 3;
/*      */   
/*      */   public static final int CATEGORY_ANNOTATIONTOOLBAR = 4;
/*      */   public static final int CATEGORY_BOOKMARK = 5;
/*      */   public static final int CATEGORY_THUMBSLIDER = 6;
/*      */   public static final int CATEGORY_THUMBVIEW = 7;
/*      */   public static final int CATEGORY_QUICKTOOL = 8;
/*      */   public static final int CATEGORY_UNDOREDO = 9;
/*      */   public static final int CATEGORY_SHORTCUTS = 10;
/*      */   public static final int CATEGORY_CREATE_DOCUMENTS = 11;
/*      */   public static final int CATEGORY_COLOR_PICKER = 12;
/*      */   public static final int LABEL_STYLEEDITOR = 101;
/*      */   public static final int LABEL_QM_EMPTY = 102;
/*      */   public static final int LABEL_QM_TEXTSELECT = 103;
/*      */   public static final int LABEL_QM_ANNOTSELECT = 104;
/*      */   public static final int LABEL_QM_TEXTANNOTSELECT = 105;
/*      */   public static final int LABEL_QM_STAMPERSELECT = 106;
/*      */   public static final int LABEL_UNIVERSAL_VIEWING = 107;
/*      */   public static final int LABEL_VIEWER = 108;
/*      */   public static final int LABEL_FOLDERS = 109;
/*      */   public static final int LABEL_ALL_DOCUMENTS = 110;
/*      */   public static final int LABEL_SD_CARD = 111;
/*      */   public static final int LABEL_FILE_PICKER = 112;
/*      */   public static final int LABEL_EXTERNAL = 113;
/*      */   public static final int LABEL_RECENT = 114;
/*      */   public static final int LABEL_FAVORITES = 115;
/*      */   public static final int LABEL_MERGE = 116;
/*      */   public static final int LABEL_EDIT = 117;
/*      */   public static final int LABEL_STANDARD = 118;
/*      */   public static final int LABEL_WHEEL = 119;
/*      */   public static final int LABEL_PICKER_STANDARD = 120;
/*      */   public static final int LABEL_PICKER_RECENT = 121;
/*      */   public static final int LABEL_DIALOG_STANDARD = 122;
/*      */   public static final int LABEL_DIALOG_WHEEL = 123;
/*      */   public static final int LABEL_DIALOG_RECENT = 124;
/*      */   private static AnalyticsHandlerAdapter _INSTANCE;
/*   74 */   protected static final Object sLock = new Object(); public static final int CREATE_NEW_ITEM_FOLDER = 1; public static final int CREATE_NEW_ITEM_BLANK_PDF = 2; public static final int CREATE_NEW_ITEM_PDF_FROM_DOCS = 3; public static final int CREATE_NEW_ITEM_PDF_FROM_IMAGE = 4; public static final int CREATE_NEW_ITEM_PDF_FROM_CAMERA = 5; public static final int CREATE_NEW_ITEM_PDF_FROM_WEBPAGE = 6; public static final int CREATE_NEW_ITEM_IMAGE_FROM_IMAGE = 7; public static final int CREATE_NEW_ITEM_IMAGE_FROM_CAMERA = 8; public static final int OPEN_FILE_IN_APP = 1; public static final int OPEN_FILE_3RD_PARTY = 2; public static final int LOCATION_VIEWER = 1; public static final int LOCATION_ANNOTATION_TOOLBAR = 2; public static final int LOCATION_THUMBNAILS_VIEW = 3; public static final int LOCATION_THUMBNAILS_SLIDER = 4; public static final int SCREEN_VIEWER = 1; public static final int SCREEN_RECENT = 2; public static final int SCREEN_FAVORITES = 3; public static final int SCREEN_FOLDERS = 4; public static final int SCREEN_ALL_DOCUMENTS = 5; public static final int SCREEN_SD_CARD = 6; public static final int SCREEN_SETTINGS = 7; public static final int SCREEN_URL = 8; public static final int VIEWER_SAVE_COPY_IDENTICAL = 1; public static final int VIEWER_SAVE_COPY_FLATTENED = 2; public static final int VIEWER_SAVE_COPY_CROPPED = 3; public static final int VIEWER_SAVE_COPY_REDUCED = 4; public static final int VIEWER_SAVE_COPY_PASSWORD_PROTECTED = 5; public static final int ANNOTATION_TOOL_LINE = 1; public static final int ANNOTATION_TOOL_RECTANGLE = 2; public static final int ANNOTATION_TOOL_OVAL = 3; public static final int ANNOTATION_TOOL_ERASER = 4; public static final int ANNOTATION_TOOL_FREEHAND = 5; public static final int ANNOTATION_TOOL_ARROW = 6; public static final int ANNOTATION_TOOL_STICKY_NOTE = 7; public static final int ANNOTATION_TOOL_FREE_TEXT = 8; public static final int ANNOTATION_TOOL_SIGNATURE = 9; public static final int ANNOTATION_TOOL_STAMP = 10; public static final int ANNOTATION_TOOL_RUBBER_STAMP = 11; public static final int ANNOTATION_TOOL_UNDERLINE = 12; public static final int ANNOTATION_TOOL_HIGHLIGHT = 13; public static final int ANNOTATION_TOOL_SQUIGGLY = 14; public static final int ANNOTATION_TOOL_STRIKEOUT = 15; public static final int ANNOTATION_TOOL_PAN = 16; public static final int ANNOTATION_TOOL_LINK = 17; public static final int ANNOTATION_TOOL_FREE_HIGHLIGHTER = 18; public static final int ANNOTATION_TOOL_SHOW_ALL_TOOLS = 19; public static final int ANNOTATION_TOOL_SHOW_FEW_TOOLS = 20; public static final int ANNOTATION_TOOL_POLYLINE = 21; public static final int ANNOTATION_TOOL_POLYGON = 22; public static final int ANNOTATION_TOOL_CLOUD = 23; public static final int ANNOTATION_TOOL_MULTI_SELECT = 24; public static final int ANNOTATION_TOOL_RULER = 25; public static final int ANNOTATION_TOOL_CALLOUT = 26; public static final int ANNOTATION_TOOL_SOUND = 27; public static final int ANNOTATION_TOOL_REDACTION = 28; public static final int ANNOTATION_TOOL_PERIMETER_MEASURE = 29; public static final int ANNOTATION_TOOL_AREA_MEASURE = 30; public static final int FORM_TOOL_TEXT = 31; public static final int FORM_TOOL_SIGNATURE = 32; public static final int FORM_TOOL_CHECKBOX = 33; public static final int FORM_TOOL_RADIOGROUP = 34; public static final int FORM_TOOL_LISTBOX = 35; public static final int FORM_TOOL_COMBOBOX = 36; public static final int FILL_AND_SIGN_TOOL_TEXT = 37; public static final int FILL_AND_SIGN_TOOL_DATE = 38; public static final int FILL_AND_SIGN_TOOL_CHECKMARK = 39; public static final int FILL_AND_SIGN_TOOL_CROSS = 40; public static final int FILL_AND_SIGN_TOOL_DOT = 41; public static final int EDIT_TOOLBAR_TOOL_PEN_1 = 1; public static final int EDIT_TOOLBAR_TOOL_PEN_2 = 2; public static final int EDIT_TOOLBAR_TOOL_PEN_3 = 3; public static final int EDIT_TOOLBAR_TOOL_PEN_4 = 4; public static final int EDIT_TOOLBAR_TOOL_PEN_5 = 5; public static final int EDIT_TOOLBAR_TOOL_ERASER = 6; public static final int EDIT_TOOLBAR_TOOL_CLEAR = 7; public static final int EDIT_TOOLBAR_TOOL_CLOSE = 8; public static final int EDIT_TOOLBAR_TOOL_UNDO = 9; public static final int EDIT_TOOLBAR_TOOL_REDO = 10; public static final int VIEW_MODE_SINGLE = 1; public static final int VIEW_MODE_DOUBLE = 2; public static final int VIEW_MODE_COVER = 3; public static final int VIEW_MODE_REFLOW = 4; public static final int VIEW_MODE_VERTICAL_ON = 5; public static final int VIEW_MODE_VERTICAL_OFF = 6; public static final int VIEW_MODE_DAY_MODE = 7; public static final int VIEW_MODE_NIGHT_MODE = 8; public static final int VIEW_MODE_SEPIA_MODE = 9; public static final int VIEW_MODE_CUSTOM_MODE = 10; public static final int VIEW_MODE_RTL_ON = 11; public static final int VIEW_MODE_RTL_OFF = 12; public static final int VIEW_MODE_ROTATE = 13; public static final int VIEW_MODE_CROP = 14; public static final int VIEW_MODE_REFLOW_ZOOM_IN = 15; public static final int VIEW_MODE_REFLOW_ZOOM_OUT = 16; public static final int THUMBNAILS_VIEW_DUPLICATE = 1; public static final int THUMBNAILS_VIEW_ROTATE = 2; public static final int THUMBNAILS_VIEW_DELETE = 3; public static final int THUMBNAILS_VIEW_EXPORT = 4; public static final int THUMBNAILS_VIEW_ADD_BLANK_PAGES = 5; public static final int THUMBNAILS_VIEW_ADD_PAGES_FROM_DOCS = 6; public static final int THUMBNAILS_VIEW_ADD_PAGE_FROM_IMAGE = 7; public static final int THUMBNAILS_VIEW_ADD_PAGE_FROM_CAMERA = 8; public static final int THUMBNAILS_VIEW_MOVE = 9; public static final int NAVIGATION_TAB_OUTLINE = 1; public static final int NAVIGATION_TAB_USER_BOOKMARKS = 2; public static final int NAVIGATION_TAB_ANNOTATIONS = 3; public static final int USER_BOOKMARKS_ADD = 1; public static final int USER_BOOKMARKS_DELETE = 2; public static final int USER_BOOKMARKS_DELETE_ALL = 3; public static final int USER_BOOKMARKS_RENAME = 4; public static final int ANNOTATIONS_LIST_EXPORT = 1; public static final int ANNOTATIONS_LIST_DELETE = 2; public static final int ANNOTATIONS_LIST_DELETE_ALL_ON_PAGE = 3; public static final int ANNOTATIONS_LIST_DELETE_ALL_IN_DOC = 4; public static final int VIEWER_NAVIGATE_BY_OUTLINE = 1; public static final int VIEWER_NAVIGATE_BY_USER_BOOKMARK = 2; public static final int VIEWER_NAVIGATE_BY_ANNOTATIONS_LIST = 3; public static final int VIEWER_NAVIGATE_BY_THUMBNAILS_VIEW = 4; public static final int SHORTCUT_ANNOTATION_HIGHLIGHT = 1; public static final int SHORTCUT_ANNOTATION_UNDERLINE = 2; public static final int SHORTCUT_ANNOTATION_STRIKETHROUGH = 3; public static final int SHORTCUT_ANNOTATION_SQUIGGLY = 4; public static final int SHORTCUT_ANNOTATION_TEXTBOX = 5; public static final int SHORTCUT_ANNOTATION_COMMENT = 6; public static final int SHORTCUT_ANNOTATION_RECTANGLE = 7; public static final int SHORTCUT_ANNOTATION_OVAL = 8; public static final int SHORTCUT_ANNOTATION_DRAW = 9; public static final int SHORTCUT_ANNOTATION_ERASER = 10; public static final int SHORTCUT_ANNOTATION_LINE = 11; public static final int SHORTCUT_ANNOTATION_ARROW = 12; public static final int SHORTCUT_ANNOTATION_SIGNATURE = 13; public static final int SHORTCUT_ANNOTATION_IMAGE = 14; public static final int SHORTCUT_ANNOTATION_HYPERLINK = 15; public static final int SHORTCUT_DELETE_ANNOTATION = 16; public static final int SHORTCUT_GO_TO_NEXT_DOC = 17; public static final int SHORTCUT_GO_TO_PREV_DOC = 18; public static final int SHORTCUT_FIND = 19; public static final int SHORTCUT_GO_TO_NEXT_SEARCH = 20; public static final int SHORTCUT_GO_TO_PREV_SEARCH = 21; public static final int SHORTCUT_UNDO = 22; public static final int SHORTCUT_REDO = 23; public static final int SHORTCUT_COPY = 24; public static final int SHORTCUT_CUT = 25; public static final int SHORTCUT_PASTE = 26; public static final int SHORTCUT_PRINT = 27;
/*      */   
/*      */   public static AnalyticsHandlerAdapter getInstance() {
/*   77 */     synchronized (sLock) {
/*   78 */       if (_INSTANCE == null) {
/*   79 */         _INSTANCE = new AnalyticsHandlerAdapter();
/*      */       }
/*      */     } 
/*   82 */     return _INSTANCE;
/*      */   } public static final int SHORTCUT_ADD_BOOKMARK = 28; public static final int SHORTCUT_PAGE_UP = 29; public static final int SHORTCUT_PAGE_DOWN = 30; public static final int SHORTCUT_GO_TO_FIRST_PAGE = 31; public static final int SHORTCUT_GO_TO_LAST_PAGE = 32; public static final int SHORTCUT_JUMP_PAGE_BACK = 33; public static final int SHORTCUT_JUMP_PAGE_FORWARD = 34; public static final int SHORTCUT_START_EDIT_SELECTED_ANNOT = 35; public static final int SHORTCUT_SWITCH_FORM = 36; public static final int SHORTCUT_ROTATE_CLOCKWISE = 37; public static final int SHORTCUT_ROTATE_COUNTER_CLOCKWISE = 38; public static final int SHORTCUT_ZOOM_IN = 39; public static final int SHORTCUT_ZOOM_OUT = 40; public static final int SHORTCUT_RESET_ZOOM = 41; public static final int SHORTCUT_OPEN_DRAWER = 42; public static final int SHORTCUT_COMMIT_CLOSE_TEXT = 43; public static final int SHORTCUT_COMMIT_CLOSE_DRAW = 44; public static final int SHORTCUT_SWITCH_INK = 45; public static final int SHORTCUT_ERASE_INK = 46; public static final int SHORTCUT_CANCEL_TOOL = 47; public static final int SHORTCUT_CLOSE_MENU = 48; public static final int SHORTCUT_CLOSE_TAB = 49; public static final int SHORTCUT_CLOSE_APP = 50; public static final int EVENT_SCREEN_VIEWER = 1; public static final int EVENT_SCREEN_RECENT = 2; public static final int EVENT_SCREEN_FAVORITES = 3; public static final int EVENT_SCREEN_FOLDERS = 4; public static final int EVENT_SCREEN_ALL_DOCUMENTS = 5; public static final int EVENT_SCREEN_SD_CARD = 6; public static final int EVENT_SCREEN_SETTINGS = 7; public static final int EVENT_OPEN_FILE = 8; public static final int EVENT_CREATE_NEW = 9; public static final int EVENT_MERGE = 10; public static final int EVENT_VIEWER_SEARCH = 11; public static final int EVENT_VIEWER_SEARCH_LIST_ALL = 12; public static final int EVENT_VIEWER_SHARE = 13; public static final int EVENT_VIEWER_PRINT = 14; public static final int EVENT_VIEWER_EDIT_PAGES_ADD = 15; public static final int EVENT_VIEWER_EDIT_PAGES_DELETE = 16; public static final int EVENT_VIEWER_EDIT_PAGES_ROTATE = 17; public static final int EVENT_VIEWER_EDIT_PAGES_REARRANGE = 18; public static final int EVENT_VIEWER_UNDO = 19; public static final int EVENT_VIEWER_REDO = 20; public static final int EVENT_VIEWER_ANNOTATION_TOOLBAR_OPEN = 21; public static final int EVENT_VIEWER_ANNOTATION_TOOLBAR_CLOSE = 22; public static final int EVENT_ANNOTATION_TOOLBAR = 23; public static final int EVENT_VIEWER_VIEW_MODE_OPEN = 24; public static final int EVENT_VIEWER_VIEW_MODE_CLOSE = 25; public static final int EVENT_VIEW_MODE = 26; public static final int EVENT_VIEWER_THUMBNAILS_VIEW_OPEN = 27; public static final int EVENT_VIEWER_THUMBNAILS_VIEW_CLOSE = 28; public static final int EVENT_THUMBNAILS_VIEW = 29; public static final int EVENT_VIEWER_NAVIGATE_BY = 30; public static final int EVENT_VIEWER_NAVIGATION_LISTS_OPEN = 31; public static final int EVENT_VIEWER_NAVIGATION_LISTS_CLOSE = 32; public static final int EVENT_VIEWER_NAVIGATION_LISTS_CHANGE = 33; public static final int EVENT_USER_BOOKMARKS = 34; public static final int EVENT_ANNOTATIONS_LIST = 35; public static final int EVENT_SHORTCUT = 36; public static final int EVENT_QUICK_MENU_OPEN = 37; public static final int EVENT_QUICK_MENU_CLOSE = 38; public static final int EVENT_QUICK_MENU_ACTION = 39; public static final int EVENT_STYLE_PICKER_OPEN = 40; public static final int EVENT_STYLE_PICKER_SELECT_COLOR = 41; public static final int EVENT_STYLE_PICKER_ADD_FAVORITE = 42; public static final int EVENT_STYLE_PICKER_REMOVE_FAVORITE = 43; public static final int EVENT_STYLE_PICKER_CLOSE = 44; public static final int EVENT_SIG_STATE_POPUP_OPEN = 45; public static final int EVENT_HUGE_THUMBNAIL = 46; public static final int EVENT_FIRST_TIME = 47; public static final int EVENT_CREATE_IMAGE_STAMP = 48; public static final int EVENT_EDIT_TOOLBAR = 49; public static final int EVENT_LOW_MEMORY = 50; public static final int EVENT_STYLE_PICKER_SELECT_THICKNESS = 51; public static final int EVENT_STYLE_PICKER_SELECT_OPACITY = 52; public static final int EVENT_STYLE_PICKER_SELECT_TEXT_SIZE = 53; public static final int EVENT_STYLE_PICKER_SELECT_PRESET = 54; public static final int EVENT_STYLE_PICKER_DESELECT_PRESET = 55; public static final int EVENT_CROP_PAGES = 56; public static final int EVENT_CUSTOM_COLOR_MODE = 57; public static final int EVENT_VIEWER_SHOW_ALL_TOOLS = 58; public static final int EVENT_UNDO_REDO_DISMISSED_NO_ACTION = 59; public static final int EVENT_STYLE_PICKER_SELECT_RULER_BASE_VALUE = 60; public static final int EVENT_STYLE_PICKER_SELECT_RULER_TRANSLATE_VALUE = 61; public static final int EVENT_ADD_RUBBER_STAMP = 62; public static final int EVENT_VIEWER_RAGE_SCROLLING = 63; public static final int EVENT_CREATE_IMAGE_SIGNATURE = 64; public static final int EVENT_VIEWER_SAVE_COPY = 65; public static final int EVENT_SCREEN_SYSTEM_PICKER = 66; public static final int POPULATE_FILES_TASK = 67; public static final int FILE_PICKER_DIALOG_LOCAL = 68; public static final int FILE_PICKER_DIALOG_EXTERNAL = 69; public static final int FILE_PICKER_DIALOG_FAVORITE = 70; public static final int FILE_PICKER_DIALOG_RECENT = 71; public static final int HTML2PDF_CONVERSION = 72; public static final int EVENT_FORM_FIELD_TOOLBAR = 73; public static final int EVENT_AUTO_DRAW_ITEM_SELECTED = 74; public static final int EVENT_AUTO_DRAW_OPTION = 75; public static final int EVENT_STYLE_PICKER_OPTIONS = 76; public static final int QUICK_MENU_TYPE_EMPTY_SPACE = 1; public static final int QUICK_MENU_TYPE_TEXT_SELECT = 2; public static final int QUICK_MENU_TYPE_ANNOTATION_SELECT = 3; public static final int QUICK_MENU_TYPE_TEXT_ANNOTATION_SELECT = 4; public static final int QUICK_MENU_TYPE_TOOL_SELECT = 5; public static final int STYLE_PICKER_STANDARD = 1; public static final int STYLE_PICKER_RECENT = 2; public static final int STYLE_PICKER_FAVORITE = 3; public static final int STYLE_PICKER_COLOR_WHEEL = 4; public static final int STYLE_PICKER_LOC_QM = 1; public static final int STYLE_PICKER_LOC_ANNOT_TOOLBAR = 2; public static final int STYLE_PICKER_LOC_SIGNATURE = 3; public static final int STYLE_PICKER_LOC_STICKY_NOTE = 4; @Deprecated
/*      */   public static final int NEXT_ACTION_SIGNATURE_FIRST_TIME = 1; public static final int NEXT_ACTION_SIGNATURE_NEW = 2; @Deprecated
/*      */   public static final int NEXT_ACTION_SIGNATURE_USE_SAVED = 3; public static final int NEXT_ACTION_SIGNATURE_DISMISS = 4; public static final int NEXT_ACTION_SHAPE_ARROW = 5; public static final int NEXT_ACTION_SHAPE_POLYLINE = 6; public static final int NEXT_ACTION_SHAPE_OVAL = 7; public static final int NEXT_ACTION_SHAPE_POLYGON = 8; public static final int NEXT_ACTION_SHAPE_CLOUD = 9; public static final int NEXT_ACTION_SHAPES_DISMISS = 10; public static final int NEXT_ACTION_FORM_TEXT_FIELD = 11; public static final int NEXT_ACTION_FORM_CHECKBOX = 12; public static final int NEXT_ACTION_FORM_SIGNATURE = 13; public static final int NEXT_ACTION_FORM_DISMISS = 14; public static final int NEXT_ACTION_SHAPE_RULER = 15; public static final int NEXT_ACTION_SHAPE_CALLOUT = 16; public static final int CROP_PAGE_AUTO = 1; public static final int CROP_PAGE_MANUAL = 2; public static final int CROP_PAGE_REMOVE = 3; public static final int CROP_PAGE_NO_ACTION = 4; public static final int CROP_PAGE_CANCEL_MANUAL = 5; public static final int CROP_PAGE_ONE_PAGE = 1; public static final int CROP_PAGE_ALL_PAGES = 2; public static final int CROP_PAGE_EVEN_ODD_PAGES = 3; public static final int CUSTOM_COLOR_MODE_SELECT = 1; public static final int CUSTOM_COLOR_MODE_EDIT = 2; public static final int CUSTOM_COLOR_MODE_CANCEL_EDIT = 3; public static final int CUSTOM_COLOR_MODE_RESTORE_DEFAULT = 4; public static final int CUSTOM_COLOR_MODE_NO_ACTION = 5; public static final int CUSTOM_COLOR_MODE_CANCEL_RESTORE_DEFAULT = 6; public static final int RUBBER_STAMP_STANDARD = 1; public static final int RUBBER_STAMP_CUSTOM = 2; public static final int RUBBER_STAMP_GOOGLE_IMAGE_SEARCH = 3; public static void setInstance(AnalyticsHandlerAdapter value) {
/*   86 */     synchronized (sLock) {
/*   87 */       _INSTANCE = value;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendEvent(int categoryId, String action) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendEvent(String category, String action) {}
/*      */ 
/*      */   
/*      */   public void sendEvent(int categoryId, String action, int labelId) {}
/*      */ 
/*      */   
/*      */   public void sendEvent(int eventId) {}
/*      */ 
/*      */   
/*      */   public void sendTimedEvent(int eventId) {}
/*      */ 
/*      */   
/*      */   public void sendEvent(int eventId, @NonNull Map<String, String> params) {}
/*      */ 
/*      */   
/*      */   public void sendTimedEvent(int eventId, @NonNull Map<String, String> params) {}
/*      */ 
/*      */   
/*      */   public void endTimedEvent(int eventId) {}
/*      */ 
/*      */   
/*      */   public void endTimedEvent(int eventId, @NonNull Map<String, String> params) {}
/*      */ 
/*      */   
/*      */   public void sendException(Exception paramException) {
/*  122 */     if (null == paramException) {
/*      */       return;
/*      */     }
/*  125 */     if (paramException.getMessage() != null) {
/*  126 */       Log.e("PDFNet", paramException.getMessage());
/*      */     }
/*  128 */     paramException.printStackTrace();
/*      */   }
/*      */   
/*      */   public void sendException(Exception paramException, String moreInfo) {
/*  132 */     if (null == paramException) {
/*      */       return;
/*      */     }
/*  135 */     if (paramException.getMessage() != null) {
/*  136 */       Log.e("PDFNet", paramException.getMessage() + moreInfo);
/*      */     }
/*  138 */     paramException.printStackTrace();
/*      */   }
/*      */   
/*      */   public String getString(int id) {
/*  142 */     switch (id) {
/*      */       case 1:
/*  144 */         return "Viewer";
/*      */       case 2:
/*  146 */         return "File Browser";
/*      */       case 3:
/*  148 */         return "General";
/*      */       case 4:
/*  150 */         return "Annotation Toolbar";
/*      */       case 5:
/*  152 */         return "Bookmark";
/*      */       case 8:
/*  154 */         return "QuickMenu Tool";
/*      */       case 6:
/*  156 */         return "ThumbSlider";
/*      */       case 7:
/*  158 */         return "ThumbnailsView";
/*      */       case 9:
/*  160 */         return "UndoRedo";
/*      */       case 10:
/*  162 */         return "Shortcuts";
/*      */       case 11:
/*  164 */         return "Create Documents";
/*      */       case 12:
/*  166 */         return "Color Picker";
/*      */       case 101:
/*  168 */         return "StyleEditor";
/*      */       case 102:
/*  170 */         return "EmptySpace";
/*      */       case 103:
/*  172 */         return "TextSelected";
/*      */       case 104:
/*  174 */         return "AnnotationSelected";
/*      */       case 105:
/*  176 */         return "TextAnnotationSelected";
/*      */       case 106:
/*  178 */         return "StampSelected";
/*      */       case 107:
/*  180 */         return "Universal Viewing";
/*      */       case 108:
/*  182 */         return "Viewer";
/*      */       case 109:
/*  184 */         return "Folders";
/*      */       case 110:
/*  186 */         return "All Documents";
/*      */       case 111:
/*  188 */         return "SD Card";
/*      */       case 112:
/*  190 */         return "File Picker";
/*      */       case 113:
/*  192 */         return "External";
/*      */       case 114:
/*  194 */         return "Recent";
/*      */       case 115:
/*  196 */         return "Favorites";
/*      */       case 116:
/*  198 */         return "Merge";
/*      */       case 117:
/*  200 */         return "Edit";
/*      */       case 118:
/*  202 */         return "Standard";
/*      */       case 119:
/*  204 */         return "Wheel";
/*      */       case 120:
/*  206 */         return "Picker standard color";
/*      */       case 121:
/*  208 */         return "Picker recent color";
/*      */       case 122:
/*  210 */         return "Dialog standard color";
/*      */       case 123:
/*  212 */         return "Dialog wheel color";
/*      */       case 124:
/*  214 */         return "Dialog recent color";
/*      */     } 
/*  216 */     return "";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public enum CustomKeys
/*      */   {
/*  224 */     ALL_FILE_BROWSER_EVENTS,
/*  225 */     ALL_FILE_BROWSER_MODE,
/*  226 */     TAB_ERROR;
/*      */   }
/*      */   
/*      */   @NonNull
/*      */   public String getKeyString(CustomKeys keyId) {
/*  231 */     switch (keyId) {
/*      */       case ALL_FILE_BROWSER_EVENTS:
/*  233 */         return "all_files_events";
/*      */       case ALL_FILE_BROWSER_MODE:
/*  235 */         return "all_files_browser_mode";
/*      */       case TAB_ERROR:
/*  237 */         return "tab_error";
/*      */     } 
/*  239 */     return "not_known";
/*      */   }
/*      */   @Retention(RetentionPolicy.SOURCE)
/*      */   public static @interface QuickMenuType {}
/*      */   @Retention(RetentionPolicy.SOURCE)
/*      */   public static @interface StylePickerLocation {}
/*      */   @Retention(RetentionPolicy.SOURCE)
/*      */   public static @interface StylePickerOpenedLocation {}
/*      */   
/*      */   @Retention(RetentionPolicy.SOURCE)
/*      */   public static @interface CropPageAction {}
/*      */   
/*      */   @Retention(RetentionPolicy.SOURCE)
/*      */   public static @interface CropPageDetails {}
/*      */   
/*      */   public void setString(CustomKeys key, String value) {}
/*      */   
/*      */   @NonNull
/*      */   public String getCreateNewItem(int itemId) {
/*  258 */     switch (itemId) {
/*      */       case 1:
/*  260 */         return "ic_folder_black_24dp";
/*      */       case 2:
/*  262 */         return "blank_pdf";
/*      */       case 3:
/*  264 */         return "pdf_from_docs";
/*      */       case 4:
/*  266 */         return "pdf_from_image";
/*      */       case 5:
/*  268 */         return "pdf_from_camera";
/*      */       case 6:
/*  270 */         return "pdf_from_webpage";
/*      */       case 7:
/*  272 */         return "image_stamp_from_image";
/*      */       case 8:
/*  274 */         return "image_stamp_from_camera";
/*      */     } 
/*  276 */     return "not_known";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getOpenFileOrigin(int originId) {
/*  285 */     switch (originId) {
/*      */       case 1:
/*  287 */         return "in_app";
/*      */       case 2:
/*  289 */         return "3rd_party";
/*      */     } 
/*  291 */     return "not_known";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getLocation(int locationId) {
/*  302 */     switch (locationId) {
/*      */       case 1:
/*  304 */         return "viewer";
/*      */       case 2:
/*  306 */         return "annotation_toolbar";
/*      */       case 3:
/*  308 */         return "thumbnails_view";
/*      */       case 4:
/*  310 */         return "thumbnails_slider";
/*      */     } 
/*  312 */     sendException(new Exception("the location is not known"));
/*  313 */     return "not_known";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getScreen(int screenId) {
/*  328 */     switch (screenId) {
/*      */       case 1:
/*  330 */         return "Viewer";
/*      */       case 2:
/*  332 */         return "Recent";
/*      */       case 3:
/*  334 */         return "Favorites";
/*      */       case 4:
/*  336 */         return "Folders";
/*      */       case 5:
/*  338 */         return "AllDocuments";
/*      */       case 6:
/*  340 */         return "SDCard";
/*      */       case 7:
/*  342 */         return "Settings";
/*      */       case 8:
/*  344 */         return "URL";
/*      */     } 
/*  346 */     return "not_known";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getViewerSaveCopy(int itemId) {
/*  358 */     switch (itemId) {
/*      */       case 1:
/*  360 */         return "identical";
/*      */       case 2:
/*  362 */         return "flattened";
/*      */       case 3:
/*  364 */         return "cropped";
/*      */       case 4:
/*  366 */         return "reduced";
/*      */       case 5:
/*  368 */         return "password_protected";
/*      */     } 
/*  370 */     return "not_known";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getAnnotationTool(int screenId) {
/*  421 */     switch (screenId) {
/*      */       case 1:
/*  423 */         return "line";
/*      */       case 2:
/*  425 */         return "rectangle";
/*      */       case 3:
/*  427 */         return "oval";
/*      */       case 4:
/*  429 */         return "eraser";
/*      */       case 5:
/*  431 */         return "freehand";
/*      */       case 6:
/*  433 */         return "arrow";
/*      */       case 7:
/*  435 */         return "sticky_note";
/*      */       case 8:
/*  437 */         return "free_text";
/*      */       case 26:
/*  439 */         return "callout";
/*      */       case 9:
/*  441 */         return "signature";
/*      */       case 10:
/*  443 */         return "image_stamp";
/*      */       case 11:
/*  445 */         return "rubber_stamp";
/*      */       case 12:
/*  447 */         return "underline";
/*      */       case 13:
/*  449 */         return "highlight";
/*      */       case 14:
/*  451 */         return "squiggly";
/*      */       case 15:
/*  453 */         return "strikeout";
/*      */       case 16:
/*  455 */         return "pan";
/*      */       case 17:
/*  457 */         return "link";
/*      */       case 18:
/*  459 */         return "free_highlighter";
/*      */       case 19:
/*  461 */         return "show_all_tools";
/*      */       case 20:
/*  463 */         return "show_fewer_tools";
/*      */       case 21:
/*  465 */         return "polyline";
/*      */       case 22:
/*  467 */         return "polygon";
/*      */       case 23:
/*  469 */         return "cloud";
/*      */       case 25:
/*  471 */         return "ruler";
/*      */       case 28:
/*  473 */         return "redaction";
/*      */       case 24:
/*  475 */         return "multi_select";
/*      */       case 27:
/*  477 */         return "sound";
/*      */       case 29:
/*  479 */         return "perimeter_measure";
/*      */       case 30:
/*  481 */         return "area_measure";
/*      */       case 33:
/*  483 */         return "checkbox";
/*      */       case 34:
/*  485 */         return "radio_group";
/*      */       case 32:
/*  487 */         return "signature_widget";
/*      */       case 31:
/*  489 */         return "text_widget";
/*      */       case 35:
/*  491 */         return "listbox_widget";
/*      */       case 36:
/*  493 */         return "combobox_widget";
/*      */       case 37:
/*  495 */         return "fill_text";
/*      */       case 38:
/*  497 */         return "fill_date";
/*      */       case 39:
/*  499 */         return "fill_check_mark";
/*      */       case 40:
/*  501 */         return "fill_cross";
/*      */       case 41:
/*  503 */         return "fill_dot";
/*      */     } 
/*  505 */     return "not_known";
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getAnnotToolByAnnotType(int annotType) {
/*  511 */     switch (annotType)
/*      */     { case 8:
/*  513 */         annotTool = 13;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  567 */         return getAnnotationTool(annotTool);case 9: annotTool = 12; return getAnnotationTool(annotTool);case 10: annotTool = 14; return getAnnotationTool(annotTool);case 11: annotTool = 15; return getAnnotationTool(annotTool);case 2: annotTool = 8; return getAnnotationTool(annotTool);case 1007: annotTool = 26; return getAnnotationTool(annotTool);case 1011: annotTool = 38; return getAnnotationTool(annotTool);case 1010: annotTool = 37; return getAnnotationTool(annotTool);case 0: annotTool = 7; return getAnnotationTool(annotTool);case 1002: annotTool = 9; return getAnnotationTool(annotTool);case 1: annotTool = 17; return getAnnotationTool(annotTool);case 14: annotTool = 5; return getAnnotationTool(annotTool);case 4: annotTool = 2; return getAnnotationTool(annotTool);case 5: annotTool = 3; return getAnnotationTool(annotTool);case 3: annotTool = 1; return getAnnotationTool(annotTool);case 1001: annotTool = 6; return getAnnotationTool(annotTool);case 1006: annotTool = 25; return getAnnotationTool(annotTool); }  int annotTool = -1; return getAnnotationTool(annotTool);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getEditToolbarTool(int screenId) {
/*  582 */     switch (screenId) {
/*      */       case 1:
/*  584 */         return "pen1";
/*      */       case 2:
/*  586 */         return "pen2";
/*      */       case 3:
/*  588 */         return "pen3";
/*      */       case 4:
/*  590 */         return "pen4";
/*      */       case 5:
/*  592 */         return "pen5";
/*      */       case 6:
/*  594 */         return "eraser";
/*      */       case 7:
/*  596 */         return "clear";
/*      */       case 8:
/*  598 */         return "close";
/*      */       case 9:
/*  600 */         return "undo";
/*      */       case 10:
/*  602 */         return "redo";
/*      */     } 
/*  604 */     return "not_known";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getViewMode(int itemId) {
/*  627 */     switch (itemId) {
/*      */       case 1:
/*  629 */         return "single";
/*      */       case 2:
/*  631 */         return "double";
/*      */       case 3:
/*  633 */         return "cover";
/*      */       case 4:
/*  635 */         return "reflow";
/*      */       case 5:
/*  637 */         return "vertical_on";
/*      */       case 6:
/*  639 */         return "vertical_off";
/*      */       case 7:
/*  641 */         return "day_mode";
/*      */       case 8:
/*  643 */         return "night_mode";
/*      */       case 9:
/*  645 */         return "sepia_mode";
/*      */       case 10:
/*  647 */         return "custom_mode";
/*      */       case 11:
/*  649 */         return "rtl_on";
/*      */       case 12:
/*  651 */         return "rtl_off";
/*      */       case 13:
/*  653 */         return "rotate";
/*      */       case 14:
/*  655 */         return "crop";
/*      */       case 15:
/*  657 */         return "reflow_zoom_in";
/*      */       case 16:
/*  659 */         return "reflow_zoom_out";
/*      */     } 
/*  661 */     return "not_known";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getThumbnailsView(int itemId) {
/*  677 */     switch (itemId) {
/*      */       case 1:
/*  679 */         return "duplicate_pages";
/*      */       case 2:
/*  681 */         return "rotate_pages";
/*      */       case 3:
/*  683 */         return "delete_pages";
/*      */       case 4:
/*  685 */         return "export_pages";
/*      */       case 5:
/*  687 */         return "add_blank_pages";
/*      */       case 6:
/*  689 */         return "add_pages_from_doc";
/*      */       case 7:
/*  691 */         return "add_page_from_image";
/*      */       case 8:
/*  693 */         return "add_page_from_camera";
/*      */       case 9:
/*  695 */         return "move_pages";
/*      */     } 
/*  697 */     return "not_known";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getNavigationListsTab(int itemId) {
/*  707 */     switch (itemId) {
/*      */       case 1:
/*  709 */         return "outline";
/*      */       case 2:
/*  711 */         return "user_bookmarks";
/*      */       case 3:
/*  713 */         return "annotations";
/*      */     } 
/*  715 */     return "not_known";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getUerBookmarksAction(int itemId) {
/*  726 */     switch (itemId) {
/*      */       case 1:
/*  728 */         return "add";
/*      */       case 2:
/*  730 */         return "delete";
/*      */       case 3:
/*  732 */         return "delete_all";
/*      */       case 4:
/*  734 */         return "rename";
/*      */     } 
/*  736 */     return "not_known";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getAnnotationsListAction(int itemId) {
/*  747 */     switch (itemId) {
/*      */       case 1:
/*  749 */         return "export";
/*      */       case 2:
/*  751 */         return "delete";
/*      */       case 3:
/*  753 */         return "delete_all_on_page";
/*      */       case 4:
/*  755 */         return "delete_all_in_document";
/*      */     } 
/*  757 */     return "not_known";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getViewerNavigateBy(int itemId) {
/*  768 */     switch (itemId) {
/*      */       case 1:
/*  770 */         return "outline";
/*      */       case 2:
/*  772 */         return "user_bookmarks";
/*      */       case 3:
/*  774 */         return "annotations_list";
/*      */       case 4:
/*  776 */         return "thumbnails_view ";
/*      */     } 
/*  778 */     return "not_known";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getShortcut(int itemId) {
/*  835 */     switch (itemId) {
/*      */       case 1:
/*  837 */         return "annotation_highlight";
/*      */       case 2:
/*  839 */         return "annotation_underline";
/*      */       case 3:
/*  841 */         return "annotation_strikethrough";
/*      */       case 4:
/*  843 */         return "annotation_squiggly";
/*      */       case 5:
/*  845 */         return "annotation_textbox";
/*      */       case 6:
/*  847 */         return "annotation_comment";
/*      */       case 7:
/*  849 */         return "annotation_rectangle";
/*      */       case 8:
/*  851 */         return "annotation_oval";
/*      */       case 9:
/*  853 */         return "annotation_draw";
/*      */       case 10:
/*  855 */         return "annotation_eraser";
/*      */       case 11:
/*  857 */         return "annotation_line";
/*      */       case 12:
/*  859 */         return "annotation_arrow";
/*      */       case 13:
/*  861 */         return "annotation_signature";
/*      */       case 14:
/*  863 */         return "annotation_image";
/*      */       case 15:
/*  865 */         return "annotation_hyperlink";
/*      */       case 16:
/*  867 */         return "delete_annotation";
/*      */       case 17:
/*  869 */         return "go_to_next_doc";
/*      */       case 18:
/*  871 */         return "go_to_prev_doc";
/*      */       case 19:
/*  873 */         return "find";
/*      */       case 20:
/*  875 */         return "go_to_next_search";
/*      */       case 21:
/*  877 */         return "go_to_prev_search";
/*      */       case 22:
/*  879 */         return "undo";
/*      */       case 23:
/*  881 */         return "redo";
/*      */       case 24:
/*  883 */         return "copy";
/*      */       case 25:
/*  885 */         return "cut";
/*      */       case 26:
/*  887 */         return "paste";
/*      */       case 27:
/*  889 */         return "print";
/*      */       case 28:
/*  891 */         return "add_bookmark";
/*      */       case 29:
/*  893 */         return "page_up";
/*      */       case 30:
/*  895 */         return "page_down";
/*      */       case 31:
/*  897 */         return "go_to_first_page";
/*      */       case 32:
/*  899 */         return "go_to_last_page";
/*      */       case 33:
/*  901 */         return "jump_page_back";
/*      */       case 34:
/*  903 */         return "jump_page_forward";
/*      */       case 35:
/*  905 */         return "start_edit_selected_annot";
/*      */       case 36:
/*  907 */         return "switch_form";
/*      */       case 37:
/*  909 */         return "rotate_clockwise";
/*      */       case 38:
/*  911 */         return "rotate_counter_clockwise";
/*      */       case 39:
/*  913 */         return "zoom_in";
/*      */       case 40:
/*  915 */         return "zoom_out";
/*      */       case 41:
/*  917 */         return "reset_zoom";
/*      */       case 42:
/*  919 */         return "open_drawer";
/*      */       case 43:
/*  921 */         return "commit_close_text";
/*      */       case 44:
/*  923 */         return "commit_close_draw";
/*      */       case 45:
/*  925 */         return "switch_ink";
/*      */       case 46:
/*  927 */         return "erase_ink";
/*      */       case 47:
/*  929 */         return "cancel_tool";
/*      */       case 48:
/*  931 */         return "close_menu";
/*      */       case 49:
/*  933 */         return "close_tab";
/*      */       case 50:
/*  935 */         return "close_app";
/*      */     } 
/*  937 */     return "not_known";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getEvent(int eventId) {
/* 1020 */     switch (eventId) {
/*      */       case 1:
/* 1022 */         return "screen_Viewer";
/*      */       case 2:
/* 1024 */         return "screen_Recent";
/*      */       case 3:
/* 1026 */         return "screen_Favorites";
/*      */       case 4:
/* 1028 */         return "screen_Folders";
/*      */       case 5:
/* 1030 */         return "screen_AllDocuments";
/*      */       case 6:
/* 1032 */         return "screen_SDCard";
/*      */       case 7:
/* 1034 */         return "screen_Settings";
/*      */       case 8:
/* 1036 */         return "open_file";
/*      */       case 9:
/* 1038 */         return "create_new";
/*      */       case 10:
/* 1040 */         return "merge";
/*      */       case 11:
/* 1042 */         return "viewer_search";
/*      */       case 12:
/* 1044 */         return "viewer_search_list_all";
/*      */       case 13:
/* 1046 */         return "viewer_share";
/*      */       case 14:
/* 1048 */         return "viewer_print";
/*      */       case 15:
/* 1050 */         return "viewer_edit_pages_add";
/*      */       case 16:
/* 1052 */         return "…viewer_edit_pages_delete";
/*      */       case 17:
/* 1054 */         return "viewer_edit_pages_rotate";
/*      */       case 18:
/* 1056 */         return "viewer_edit_pages_rearrange";
/*      */       case 19:
/* 1058 */         return "viewer_undo";
/*      */       case 20:
/* 1060 */         return "viewer_redo";
/*      */       case 21:
/* 1062 */         return "viewer_annotation_toolbar_open";
/*      */       case 22:
/* 1064 */         return "viewer_annotation_toolbar_close";
/*      */       case 23:
/* 1066 */         return "annotation_toolbar";
/*      */       case 24:
/* 1068 */         return "viewer_view_mode_open";
/*      */       case 25:
/* 1070 */         return "viewer_view_mode_close";
/*      */       case 26:
/* 1072 */         return "view_mode";
/*      */       case 27:
/* 1074 */         return "viewer_thumbnails_view_open";
/*      */       case 28:
/* 1076 */         return "viewer_thumbnails_view_close";
/*      */       case 29:
/* 1078 */         return "thumbnails_view";
/*      */       case 30:
/* 1080 */         return "viewer_navigate_by";
/*      */       case 31:
/* 1082 */         return "viewer_navigation_lists_open";
/*      */       case 32:
/* 1084 */         return "viewer_navigation_lists_close";
/*      */       case 33:
/* 1086 */         return "viewer_navigation_lists_change";
/*      */       case 34:
/* 1088 */         return "user_bookmarks";
/*      */       case 35:
/* 1090 */         return "annotations_list";
/*      */       case 36:
/* 1092 */         return "shortcut";
/*      */       case 50:
/* 1094 */         return "low_memory";
/*      */       case 37:
/* 1096 */         return "quickmenu_open";
/*      */       case 38:
/* 1098 */         return "quickmenu_dismiss";
/*      */       case 39:
/* 1100 */         return "quickmenu";
/*      */       case 40:
/* 1102 */         return "stylepicker_open";
/*      */       case 41:
/* 1104 */         return "stylepicker_select_color";
/*      */       case 42:
/* 1106 */         return "stylepicker_add_favorite";
/*      */       case 43:
/* 1108 */         return "stylepicker_remove_favorite";
/*      */       case 44:
/* 1110 */         return "stylepicker_close";
/*      */       case 45:
/* 1112 */         return "signature_state_popup_opened";
/*      */       case 46:
/* 1114 */         return "huge_thumbnail";
/*      */       case 47:
/* 1116 */         return "first_time_run";
/*      */       case 48:
/* 1118 */         return "create_image_stamp";
/*      */       case 49:
/* 1120 */         return "edit_toolbar";
/*      */       case 51:
/* 1122 */         return "stylepicker_select_thickness";
/*      */       case 52:
/* 1124 */         return "stylepicker_select_opacity";
/*      */       case 53:
/* 1126 */         return "stylepicker_select_text_size";
/*      */       case 60:
/* 1128 */         return "stylepicker_select_ruler_base_value";
/*      */       case 61:
/* 1130 */         return "stylepicker_select_ruler_translate_value";
/*      */       case 54:
/* 1132 */         return "stylepicker_select_preset";
/*      */       case 55:
/* 1134 */         return "stylepicker_deselect_preset";
/*      */       case 76:
/* 1136 */         return "stylepicker_options";
/*      */       case 56:
/* 1138 */         return "crop_pages";
/*      */       case 57:
/* 1140 */         return "custom_color_mode";
/*      */       case 58:
/* 1142 */         return "viewer_show_all_tools";
/*      */       case 59:
/* 1144 */         return "undoredo_dismissed_noaction";
/*      */       case 62:
/* 1146 */         return "add_rubber_stamp";
/*      */       case 63:
/* 1148 */         return "switch_to_vertical_scroll";
/*      */       case 64:
/* 1150 */         return "create_image_signature";
/*      */       case 65:
/* 1152 */         return "viewer_save_copy";
/*      */       case 66:
/* 1154 */         return "screen_system_picker";
/*      */       case 67:
/* 1156 */         return "populate_file_task";
/*      */       case 70:
/* 1158 */         return "file_picker_dialog_favorite";
/*      */       case 68:
/* 1160 */         return "file_picker_dialog_local";
/*      */       case 71:
/* 1162 */         return "file_picker_dialog_recent";
/*      */       case 69:
/* 1164 */         return "file_picker_dialog_external";
/*      */       case 72:
/* 1166 */         return "HTML2PDF_webpage_conversion";
/*      */       case 73:
/* 1168 */         return "form_field_toolbar";
/*      */       case 74:
/* 1170 */         return "auto_draw_item_selected";
/*      */       case 75:
/* 1172 */         return "auto_draw_option";
/*      */     } 
/* 1174 */     return "not_known";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getQuickMenuType(int itemId) {
/* 1196 */     switch (itemId) {
/*      */       case 1:
/* 1198 */         return "empty_space";
/*      */       case 2:
/* 1200 */         return "text_selected";
/*      */       case 3:
/* 1202 */         return "annotation_selected";
/*      */       case 4:
/* 1204 */         return "text_annotation_selected";
/*      */       case 5:
/* 1206 */         return "tool_selected";
/*      */     } 
/* 1208 */     return "not_known";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getStylePickerLocation(int location) {
/* 1224 */     switch (location) {
/*      */       case 1:
/* 1226 */         return "standard";
/*      */       case 2:
/* 1228 */         return "recent";
/*      */       case 3:
/* 1230 */         return "favorite";
/*      */     } 
/* 1232 */     return "color_wheel";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getStylePickerOpenedFromLocation(int location) {
/* 1247 */     switch (location) {
/*      */       case 1:
/* 1249 */         return "quickmenu";
/*      */       case 2:
/* 1251 */         return "annotation_toolbar";
/*      */       case 3:
/* 1253 */         return "signature_picker";
/*      */       case 4:
/* 1255 */         return "sticky_note";
/*      */     } 
/* 1257 */     return "not_known";
/*      */   }
/*      */ 
/*      */   
/*      */   public String getQuickMenuAction(@IdRes int itemId, @Nullable ToolManager.ToolModeBase toolMode) {
/* 1262 */     if (itemId == R.id.qm_image_stamper) return "stamper"; 
/* 1263 */     if (itemId == R.id.qm_define) return "define"; 
/* 1264 */     if (itemId == R.id.qm_translate) return "translate"; 
/* 1265 */     if (itemId == R.id.qm_overflow) return "overflow"; 
/* 1266 */     if (itemId == R.id.qm_appearance) return "appearance"; 
/* 1267 */     if (itemId == R.id.qm_text) return "edit_free_text"; 
/* 1268 */     if (itemId == R.id.qm_copy)
/* 1269 */       return (toolMode == ToolManager.ToolMode.TEXT_SELECT) ? "copy_text" : "copy_annotation"; 
/* 1270 */     if (itemId == R.id.qm_paste) return "paste_annotation"; 
/* 1271 */     if (itemId == R.id.qm_delete) return "delete"; 
/* 1272 */     if (itemId == R.id.qm_flatten) return "flatten"; 
/* 1273 */     if (itemId == R.id.qm_note) return "note"; 
/* 1274 */     if (itemId == R.id.qm_arrow) return "arrow"; 
/* 1275 */     if (itemId == R.id.qm_free_text) return "free_text"; 
/* 1276 */     if (itemId == R.id.qm_sticky_note) return "sticky_note"; 
/* 1277 */     if (itemId == R.id.qm_floating_sig) return "signature"; 
/* 1278 */     if (itemId == R.id.qm_form_text) return "form_text"; 
/* 1279 */     if (itemId == R.id.qm_form_check_box) return "form_check_box"; 
/* 1280 */     if (itemId == R.id.qm_form_signature) return "form_signature"; 
/* 1281 */     if (itemId == R.id.qm_rectangle) return "rectangle"; 
/* 1282 */     if (itemId == R.id.qm_link) return "link"; 
/* 1283 */     if (itemId == R.id.qm_line) return "line"; 
/* 1284 */     if (itemId == R.id.qm_oval) return "oval"; 
/* 1285 */     if (itemId == R.id.qm_ink_eraser) return "ink_eraser"; 
/* 1286 */     if (itemId == R.id.qm_free_hand) return "free_hand"; 
/* 1287 */     if (itemId == R.id.qm_form) return "form) return"; 
/* 1288 */     if (itemId == R.id.qm_highlight) return "highlight"; 
/* 1289 */     if (itemId == R.id.qm_underline) return "underline"; 
/* 1290 */     if (itemId == R.id.qm_squiggly) return "squiggly"; 
/* 1291 */     if (itemId == R.id.qm_strikeout) return "strikeout"; 
/* 1292 */     if (itemId == R.id.qm_search) return "search"; 
/* 1293 */     if (itemId == R.id.qm_share) return "share"; 
/* 1294 */     if (itemId == R.id.qm_tts) return "tts"; 
/* 1295 */     if (itemId == R.id.qm_type) return "textmarkup_type:"; 
/* 1296 */     if (itemId == R.id.qm_field_signed) return "field_signed"; 
/* 1297 */     if (itemId == R.id.qm_form_radio_add_item) return "form_radio_add_item"; 
/* 1298 */     if (itemId == R.id.qm_use_saved_sig) return "use_saved_signature"; 
/* 1299 */     if (itemId == R.id.qm_new_signature) return "new_signature"; 
/* 1300 */     if (itemId == R.id.qm_sign_and_save) return "sign_and_save"; 
/* 1301 */     if (itemId == R.id.qm_open_attachment) return "open_attachment"; 
/* 1302 */     if (itemId == R.id.qm_edit) return "edit_freehand"; 
/* 1303 */     if (itemId == R.id.qm_thickness) return "thickness"; 
/* 1304 */     if (itemId == R.id.qm_color) return "color"; 
/* 1305 */     if (itemId == R.id.qm_1) return "value_1"; 
/* 1306 */     if (itemId == R.id.qm_2) return "value_2"; 
/* 1307 */     if (itemId == R.id.qm_3) return "value_3"; 
/* 1308 */     if (itemId == R.id.qm_4) return "value_4"; 
/* 1309 */     if (itemId == R.id.qm_5) return "value_5"; 
/* 1310 */     if (itemId == R.id.qm_redaction) return "redaction"; 
/* 1311 */     if (itemId == R.id.qm_redact) return "redact"; 
/* 1312 */     return "not_known";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getQuickMenuNextAction(int type) {
/* 1358 */     switch (type) {
/*      */       case 1:
/* 1360 */         return "signature_first_time";
/*      */       case 5:
/* 1362 */         return "shape_arrow";
/*      */       case 9:
/* 1364 */         return "shape_cloud";
/*      */       case 7:
/* 1366 */         return "shape_oval";
/*      */       case 8:
/* 1368 */         return "shape_polygon";
/*      */       case 6:
/* 1370 */         return "shape_polyline";
/*      */       case 2:
/* 1372 */         return "signature_new";
/*      */       case 4:
/* 1374 */         return "signature_dismiss";
/*      */       case 3:
/* 1376 */         return "signature_use_saved";
/*      */       case 12:
/* 1378 */         return "form_checkbox";
/*      */       case 14:
/* 1380 */         return "forms_dismiss";
/*      */       case 13:
/* 1382 */         return "form_signature";
/*      */       case 11:
/* 1384 */         return "form_text_field";
/*      */       case 10:
/* 1386 */         return "shapes_dismiss";
/*      */     } 
/* 1388 */     return "not_known";
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getColorPickerType(int type) {
/* 1394 */     switch (type) {
/*      */       case 0:
/* 1396 */         return "stroke_color";
/*      */       case 1:
/* 1398 */         return "fill_color";
/*      */       case 2:
/* 1400 */         return "text_color";
/*      */       case 3:
/* 1402 */         return "color";
/*      */     } 
/* 1404 */     return "not_known";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getCropPageAction(int action) {
/* 1422 */     switch (action) {
/*      */       case 1:
/* 1424 */         return "automatic";
/*      */       case 2:
/* 1426 */         return "manual";
/*      */       case 3:
/* 1428 */         return "remove";
/*      */       case 4:
/* 1430 */         return "noaction";
/*      */       case 5:
/* 1432 */         return "manual_then_cancel";
/*      */     } 
/* 1434 */     return "not_known";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getCropPageDetails(int details) {
/* 1449 */     switch (details) {
/*      */       case 1:
/* 1451 */         return "one_page";
/*      */       case 2:
/* 1453 */         return "all_pages";
/*      */       case 3:
/* 1455 */         return "even_or_odd_pages";
/*      */     } 
/* 1457 */     return "not_known";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getCustomColorModeAction(int action) {
/* 1469 */     switch (action) {
/*      */       case 1:
/* 1471 */         return "select";
/*      */       case 2:
/* 1473 */         return "edit";
/*      */       case 5:
/* 1475 */         return "noaction";
/*      */       case 4:
/* 1477 */         return "restore_defaults";
/*      */       case 3:
/* 1479 */         return "edit_then_cancel";
/*      */       case 6:
/* 1481 */         return "cancel_restore_defaults";
/*      */     } 
/* 1483 */     return "not_known";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getRubberStampType(int type) {
/* 1492 */     switch (type) {
/*      */       case 1:
/* 1494 */         return "standard";
/*      */       case 2:
/* 1496 */         return "custom";
/*      */       case 3:
/* 1498 */         return "google_image_search";
/*      */     } 
/* 1500 */     return "not_known";
/*      */   }
/*      */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pd\\utils\AnalyticsHandlerAdapter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */