/*      */ package com.pdftron.pdf.utils;
/*      */ 
/*      */ import android.content.Context;
/*      */ import android.content.SharedPreferences;
/*      */ import android.content.pm.PackageInfo;
/*      */ import android.content.pm.PackageManager;
/*      */ import androidx.annotation.NonNull;
/*      */ import androidx.preference.PreferenceManager;
/*      */ import com.pdftron.pdf.PDFViewCtrl;
/*      */ import com.pdftron.pdf.dialog.annotlist.BaseAnnotationSortOrder;
/*      */ import java.util.HashSet;
/*      */ import java.util.Set;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class PdfViewCtrlSettingsManager
/*      */ {
/*      */   public static final String KEY_PREF_SUFFIX_RECENT_FILES = "recent";
/*      */   public static final String KEY_PREF_SUFFIX_FAVOURITE_FILES = "favourites";
/*      */   public static final String KEY_PREF_SUFFIX_FOLDER_FILES = "folders";
/*      */   public static final String KEY_PREF_SUFFIX_EXTERNAL_FILES = "external";
/*      */   public static final String KEY_PREF_SUFFIX_DOCUMENTS_FILES = "documents";
/*      */   public static final String KEY_PREF_SUFFIX_MERGE_FILES = "merge";
/*      */   public static final String KEY_PREF_VIEWMODE_CONTINUOUS_VALUE = "continuous";
/*      */   public static final String KEY_PREF_VIEWMODE_SINGLEPAGE_VALUE = "singlepage";
/*      */   public static final String KEY_PREF_VIEWMODE_FACING_VALUE = "facing";
/*      */   public static final String KEY_PREF_VIEWMODE_FACINGCOVER_VALUE = "facingcover";
/*      */   public static final String KEY_PREF_VIEWMODE_FACING_CONT_VALUE = "facing_cont";
/*      */   public static final String KEY_PREF_VIEWMODE_FACINGCOVER_CONT_VALUE = "facingcover_cont";
/*      */   public static final String KEY_PREF_VIEWMODE_THUMBNAILS_VALUE = "thumbnails";
/*      */   public static final String KEY_PREF_VIEWMODE_ROTATION_VALUE = "rotation";
/*      */   public static final String KEY_PREF_VIEWMODE_USERCROP_VALUE = "user_crop";
/*      */   public static final String KEY_PREF_VIEWMODE = "pref_viewmode";
/*      */   public static final String KEY_PREF_VIEWMODE_DEFAULT_VALUE = "singlepage";
/*      */   public static final String KEY_PREF_FREE_TEXT_FONTS = "pref_free_text_fonts";
/*      */   public static final String KEY_PREF_FREE_TEXT_FONTS_INIT = "pref_free_text_fonts_init";
/*      */   public static final String KEY_PREF_SAVED_EXTERNAL_FOLDER_URI = "external_folder_uri";
/*      */   public static final String KEY_PREF_SAVED_EXTERNAL_FOLDER_URI_DEFAULT_VALUE = "";
/*      */   public static final String KEY_PREF_SAVED_EXTERNAL_FOLDER_TREE_URI = "external_folder_tree_uri";
/*      */   public static final String KEY_PREF_SAVED_EXTERNAL_FOLDER_TREE_URI_DEFAULT_VALUE = "";
/*      */   public static final String KEY_PREF_LOCAL_FOLDER_PATH = "pref_local_folder_path";
/*      */   public static final String KEY_PREF_LOCAL_FOLDER_PATH_DEFAULT_VALUE = "";
/*      */   public static final String KEY_PREF_LOCAL_FOLDER_TREE = "pref_local_folder_tree";
/*      */   public static final String KEY_PREF_LOCAL_FOLDER_TREE_DEFAULT_VALUE = "";
/*      */   private static final String KEY_PREF_LOCAL_APP_VERSION = "pref_local_app_version";
/*      */   private static final String KEY_PREF_LOCAL_APP_VERSION_DEFAULT_VALUE = "";
/*      */   public static final String KEY_PREF_SUFFIX_LOCAL_FILES = "all";
/*      */   public static final String KEY_PREF_FILE_TYPE_FILTER = "pref_file_type_filter_";
/*      */   public static final String KEY_PREF_FILE_TYPE_PDF = "_pdf";
/*      */   public static final String KEY_PREF_FILE_TYPE_DOCX = "_docx";
/*      */   public static final String KEY_PREF_FILE_TYPE_IMAGE = "_image";
/*      */   public static final String KEY_PREF_SAVED_FOLDER_PICKER_LOCATION = "saved_folder_picker_location";
/*  207 */   public static final String KEY_PREF_SAVED_FOLDER_PICKER_LOCATION_DEFAULT_VALUE = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String KEY_PREF_SAVED_FOLDER_PICKER_FILE_TYPE = "saved_folder_picker_file_type";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int KEY_PREF_SAVED_FOLDER_PICKER_FILE_TYPE_DEFAULT_VALUE = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String KEY_PREF_SAVED_FILE_PICKER_LOCATION = "saved_file_picker_location";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  227 */   public static final String KEY_PREF_SAVED_FILE_PICKER_LOCATION_DEFAULT_VALUE = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String KEY_PREF_SAVED_FILE_PICKER_FILE_TYPE = "saved_file_picker_file_type";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int KEY_PREF_SAVED_FILE_PICKER_FILE_TYPE_DEFAULT_VALUE = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String KEY_PREF_GRID_SIZE = "pref_grid_size_new_";
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String KEY_PREF_GRID_SIZE_SMALL_VALUE = "small";
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String KEY_PREF_GRID_SIZE_MEDIUM_VALUE = "medium";
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String KEY_PREF_GRID_SIZE_LARGE_VALUE = "large";
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int KEY_PREF_GRID_SPAN_DEFAULT_VALUE = 0;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String KEY_PREF_GRID_SIZE_DEFAULT_VALUE = "medium";
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String KEY_PREF_ANNOT_LIST_SORT = "pref_annot_list_sort";
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String KEY_PREF_THUMB_LIST_FILTER = "pref_thumbnails_list_filter";
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String KEY_PREF_SORT = "pref_sort";
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String KEY_PREF_SORT_BY_ACTIVITY_DATE = "activity_date";
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String KEY_PREF_SORT_BY_CREATED_DATE = "created_date";
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String KEY_PREF_SORT_BY_FILE_NAME = "file_name";
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String KEY_PREF_SORT_BY_NAME = "name";
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String KEY_PREF_SORT_BY_DATE = "date";
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String KEY_PREF_SORT_DEFAULT_VALUE = "name";
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String KEY_PREF_SHOW_OPEN_READ_ONLY_SDCARD_FILE_WARNING = "pref_show_open_read_only_sdcard_file_warning";
/*      */ 
/*      */ 
/*      */   
/*      */   public static final boolean KEY_PREF_SHOW_OPEN_READ_ONLY_SDCARD_FILE_WARNING_DEFAULT_VALUE = true;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String KEY_PREF_STORAGE_PERMISSION_ASKED = "pref_storage_permission_asked";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final boolean KEY_PREF_STORAGE_PERMISSION_ASKED_DEFAULT_VALUE = false;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String KEY_PREF_STORAGE_PERMISSION_DENIED = "pref_storage_permission_denied";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final boolean KEY_PREF_STORAGE_PERMISSION_DENIED_DEFAULT_VALUE = false;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String KEY_PREF_DOUBLE_ROW_TOOLBAR_IN_USE = "pref_double_row_toolbar_in_use";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final boolean KEY_PREF_DOUBLE_ROW_TOOLBAR_IN_USE_DEFAULT_VALUE = false;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String KEY_PREF_EDIT_URI_BACKUP_FILE_PATH = "pref_edit_uri_backup_file_path";
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String KEY_PREF_EDIT_URI_BACKUP_FILE_PATH_DEFAULT_VALUE = "";
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int KEY_PREF_COLOR_MODE_NORMAL = 1;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int KEY_PREF_COLOR_MODE_SEPIA = 2;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int KEY_PREF_COLOR_MODE_NIGHT = 3;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int KEY_PREF_COLOR_MODE_CUSTOM = 4;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String KEY_PREF_COLOR_MODE_CUSTOM_TEXTCOLOR = "pref_color_mode_custom_textcolor";
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int KEY_PREF_COLOR_MODE_CUSTOM_TEXTCOLOR_DEFAULT_VALUE = -16777216;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String KEY_PREF_COLOR_MODE_CUSTOM_BGCOLOR = "pref_color_mode_custom_bgcolor";
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int KEY_PREF_COLOR_MODE_CUSTOM_BGCOLOR_DEFAULT_VALUE = -1;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String KEY_PREF_COLOR_MODE_PRESETS = "pref_color_mode_presets";
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String KEY_PREF_COLOR_MODE_SELECTED_PRESET = "pref_color_mode_selected_preset";
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int KEY_PREF_COLOR_MODE_SELECTED_PRESET_DEFAULT_VALUE = -1;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String KEY_PREF_COLOR_MODE = "pref_color_mode";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int KEY_PREF_COLOR_MODE_DEFAULT_VALUE = 1;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String KEY_PREF_FOLLOW_SYSTEM_DARK_MODE = "pref_follow_system_dark_mode";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final boolean KEY_PREF_FOLLOW_SYSTEM_DARK_MODE_DEFAULT_VALUE = true;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String KEY_PREF_PRINT_DOCUMENT = "pref_print_document";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final boolean KEY_PREF_PRINT_DOCUMENT_DEFAULT_VALUE = true;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String KEY_PREF_PRINT_ANNOTATIONS = "pref_print_annotations";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final boolean KEY_PREF_PRINT_ANNOTATIONS_DEFAULT_VALUE = true;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String KEY_PREF_PRINT_SUMMARY = "pref_print_summary";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final boolean KEY_PREF_PRINT_SUMMARY_DEFAULT_VALUE = false;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String KEY_PREF_RTLMODE = "pref_rtlmode";
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String KEY_PREF_REFLOWMODE = "pref_reflowmode";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final boolean KEY_PREF_RTLMODE_DEFAULT_VALUE = false;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String KEY_PREF_RTL_MODE_OPTION = "pref_rtl_mode_option";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final boolean KEY_PREF_RTL_MODE_OPTION_DEFAULT_VALUE = false;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String KEY_PREF_CONT_ANNOT_EDIT = "pref_cont_annot_edit";
/*      */ 
/*      */ 
/*      */   
/*      */   public static final boolean KEY_PREF_CONT_ANNOT_EDIT_DEFAULT_VALUE = true;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String KEY_PREF_FULL_SCREEN_MODE = "pref_full_screen_mode";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final boolean KEY_PREF_FULL_SCREEN_MODE_DEFAULT_VALUE = true;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String KEY_PREF_DESKTOP_UI_MODE = "pref_enable_desktop_ui";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final boolean KEY_PREF_DESKTOP_UI_MODE_DEFAULT_VALUE = false;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String KEY_PREF_PAGE_VIEW_MODE = "pref_page_view_mode";
/*      */ 
/*      */ 
/*      */   
/*  474 */   private static final String KEY_PREF_PAGE_VIEW_MODE_DEFAULT_VALUE = String.valueOf(PDFViewCtrl.PageViewMode.FIT_PAGE.getValue());
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String KEY_PREF_MULTI_TABS = "pref_multiple_tabs";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final boolean KEY_PREF_MULTI_TABS_DEFAULT_VALUE = true;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String KEY_PREF_SCREEN_STAY_LOCK = "pref_screen_stay_lock";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final boolean KEY_PREF_SCREEN_STAY_LOCK_DEFAULT_VALUE = false;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String KEY_PREF_MAINTAIN_ZOOM_OPTION = "pref_maintain_zoom_option";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final boolean KEY_PREF_MAINTAIN_ZOOM_OPTION_DEFAULT_VALUE = true;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String KEY_PREF_IMAGE_SMOOTHING = "pref_image_smoothing";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final boolean KEY_PREF_IMAGE_SMOOTHING_DEFAULT_VALUE = true;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String KEY_PREF_COPY_ANNOTATED_TEXT_TO_NOTE = "pref_copy_annotated_text_to_note";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final boolean KEY_PREF_COPY_ANNOTATED_TEXT_TO_NOTE_DEFAULT_VALUE = false;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String KEY_PREF_STYLUS_AS_PEN = "pref_stylus_as_pen";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final boolean KEY_PREF_STYLUS_AS_PEN_DEFAULT_VALUE = false;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String KEY_PREF_INK_SMOOTHING = "pref_ink_smoothing";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final boolean KEY_PREF_INK_SMOOTHING_DEFAULT_VALUE = true;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String KEY_PREF_ANNOT_LIST_SHOW_AUTHOR = "pref_annot_list_show_author";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final boolean KEY_PREF_ANNOT_LIST_SHOW_AUTHOR_DEFAULT_VALUE = false;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String KEY_PREF_ALLOW_PAGE_CHANGE_ANIMATION = "pref_page_change_animation";
/*      */ 
/*      */ 
/*      */   
/*      */   public static final boolean KEY_PREF_ALLOW_PAGE_CHANGE_ANIMATION_DEFAULT_VALUE = true;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String KEY_PREF_ALLOW_PAGE_CHANGE_ON_TAP = "pref_allow_page_change_on_tap";
/*      */ 
/*      */ 
/*      */   
/*      */   public static final boolean KEY_PREF_ALLOW_PAGE_CHANGE_ON_TAP_DEFAULT_VALUE = true;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String KEY_PREF_PAGE_NUMBER_OVERLAY = "pref_page_number_overlay";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final boolean KEY_PREF_PAGE_NUMBER_OVERLAY_DEFAULT_VALUE = true;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String KEY_PREF_REMEMBER_LAST_PAGE = "pref_remember_last_page";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final boolean KEY_PREF_REMEMBER_LAST_PAGE_DEFAULT_VALUE = true;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String KEY_PREF_ENABLE_JAVASCRIPT = "pref_enable_javascript";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final boolean KEY_PREF_ENABLE_JAVASCRIPT_DEFAULT_VALUE = true;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String KEY_PREF_SHOW_ANNOT_INDICATOR = "pref_show_annot_indicator";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final boolean KEY_PREF_SHOW_ANNOT_INDICATOR_DEFAULT_VALUE = true;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String KEY_PREF_SHOW_QUICK_MENU = "pref_show_quick_menu";
/*      */ 
/*      */ 
/*      */   
/*      */   public static final boolean KEY_PREF_SHOW_QUICK_MENU_DEFAULT_VALUE = true;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String KEY_PREF_COPY_ANNOT_TEACH_SHOWN = "copy_annot_teach_shown_count";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int KEY_PREF_COPY_ANNOT_TEACH_SHOWN_MAX = 3;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String KEY_PREF_COLOR_PICKER_PAGE = "pref_color_picker_page";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int KEY_PREF_COLOR_PICKER_DEFAULT_PAGE = 1;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String KEY_PREF_TOOLBAR_VISIBLE_ANNOT_TYPE = "pref_annot_toolbar_visible_annot_types";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String KEY_PREF_UNLIMITED_TABS = "pref_unlimited_tabs";
/*      */ 
/*      */ 
/*      */   
/*      */   public static final boolean KEY_PREF_UNLIMITED_TABS_DEFAULT_VALUE = false;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String KEY_PREF_AUTHOR_NAME = "pref_author_name";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String KEY_PREF_AUTHOR_NAME_DEFAULT_VALUE = "";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String KEY_PREF_AUTHOR_NAME_HAS_BEEN_ASKED = "pref_author_name_has_been_asked";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final boolean KEY_PREF_AUTHOR_NAME_HAS_BEEN_ASKED_DEFAULT_VALUE = false;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String KEY_PREF_LINK_EDIT_OPTION = "pref_link_edit_option";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int KEY_PREF_LINK_EDIT_OPTION_DEFAULT_VALUE = 0;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String KEY_PREF_PRESET_ANNOT_STYLE = "pref_preset_ annot_style";
/*      */ 
/*      */   
/*  653 */   private static final String KEY_PREF_PRESET_ANNOT_STYLE_DEFAULT_VALUE = null;
/*      */ 
/*      */   
/*      */   private static final String KEY_PREF_RECENT_COLORS = "pref_recent_colors";
/*      */ 
/*      */   
/*      */   private static final String KEY_PREF_FAVORITE_COLORS = "pref_favorite_colors";
/*      */   
/*      */   private static final String KEY_PREF_OPEN_URL_LAST_PAGE = "pref_open_url_last_page";
/*      */   
/*      */   private static final String KEY_PREF_SHOW_RAGE_SCROLLING_INFO = "pref_show_rage_scrolling_info";
/*      */   
/*      */   public static final boolean KEY_PREF_SHOW_RAGE_SCROLLING_INFO_DEFAULT_VALUE = true;
/*      */   
/*      */   private static final String KEY_PREF_VERTICAL_SCROLL_SNAP = "pref_vertical_page_snapping";
/*      */   
/*      */   public static final boolean KEY_PREF_VERTICAL_SCROLL_SNAP_DEFAULT_VALUE = false;
/*      */   
/*      */   private static final String KEY_PREF_HOME_TOOLBAR_MENU = "toolbar_menu_home_saved";
/*      */ 
/*      */   
/*      */   public static SharedPreferences getDefaultSharedPreferences(@NonNull Context context) {
/*  675 */     return PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
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
/*      */   public static String getViewMode(@NonNull Context context) {
/*  687 */     return getDefaultSharedPreferences(context).getString("pref_viewmode", "singlepage");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void updateViewMode(@NonNull Context context, String mode) {
/*  698 */     SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
/*  699 */     editor.putString("pref_viewmode", mode);
/*  700 */     editor.apply();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean getInRTLMode(@NonNull Context context) {
/*  710 */     return getDefaultSharedPreferences(context).getBoolean("pref_rtlmode", false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void updateInRTLMode(@NonNull Context context, boolean isRTL) {
/*  719 */     SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
/*  720 */     editor.putBoolean("pref_rtlmode", isRTL);
/*  721 */     editor.apply();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getColorMode(@NonNull Context context) {
/*  732 */     return getDefaultSharedPreferences(context).getInt("pref_color_mode", 1);
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
/*      */   public static void setColorMode(@NonNull Context context, int mode) {
/*  744 */     SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
/*  745 */     editor.putInt("pref_color_mode", mode);
/*  746 */     editor.apply();
/*      */     
/*  748 */     int newMode = getColorMode(context);
/*  749 */     if (mode != newMode) {
/*  750 */       AnalyticsHandlerAdapter.getInstance().sendEvent(3, "Error: SharedPreferences.Editor apply value does not match get");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean getFollowSystemDarkMode(@NonNull Context context) {
/*  760 */     return getDefaultSharedPreferences(context).getBoolean("pref_follow_system_dark_mode", true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setFollowSystemDarkMode(@NonNull Context context, boolean followSystem) {
/*  769 */     SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
/*  770 */     editor.putBoolean("pref_follow_system_dark_mode", followSystem);
/*  771 */     editor.apply();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean hasRtlModeOption(@NonNull Context context) {
/*  782 */     return getDefaultSharedPreferences(context).getBoolean("pref_rtl_mode_option", false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void updateRtlModeOption(@NonNull Context context, boolean rtlModeOption) {
/*  793 */     SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
/*  794 */     editor.putBoolean("pref_rtl_mode_option", rtlModeOption);
/*  795 */     editor.apply();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean getContinuousAnnotationEdit(@NonNull Context context) {
/*  805 */     return getDefaultSharedPreferences(context).getBoolean("pref_cont_annot_edit", true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setContinuousAnnotationEdit(@NonNull Context context, boolean value) {
/*  815 */     SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
/*  816 */     editor.putBoolean("pref_cont_annot_edit", value);
/*  817 */     editor.apply();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isAutoSelectAnnotation(@NonNull Context context) {
/*  827 */     return isAutoSelectAnnotation(context, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isAutoSelectAnnotation(@NonNull Context context, boolean defaultValue) {
/*  838 */     return getDefaultSharedPreferences(context).getBoolean("pref_show_quick_menu", defaultValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setAutoSelectAnnotation(@NonNull Context context, boolean value) {
/*  848 */     SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
/*  849 */     editor.putBoolean("pref_show_quick_menu", value);
/*  850 */     editor.apply();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setFullScreenMode(@NonNull Context context, boolean enabled) {
/*  860 */     SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
/*  861 */     editor.putBoolean("pref_full_screen_mode", enabled);
/*  862 */     editor.apply();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean getFullScreenMode(@NonNull Context context) {
/*  872 */     return getDefaultSharedPreferences(context).getBoolean("pref_full_screen_mode", true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isDesktopUI(@NonNull Context context) {
/*  883 */     return getDefaultSharedPreferences(context).getBoolean("pref_enable_desktop_ui", false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isVerticalScrollSnap(@NonNull Context context) {
/*  893 */     return getDefaultSharedPreferences(context).getBoolean("pref_vertical_page_snapping", false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setVerticalScrollSnap(@NonNull Context context, boolean enabled) {
/*  904 */     SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
/*  905 */     editor.putBoolean("pref_vertical_page_snapping", enabled);
/*  906 */     editor.apply();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isPrintDocumentMode(@NonNull Context context) {
/*  917 */     return getDefaultSharedPreferences(context).getBoolean("pref_print_document", true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setPrintDocumentMode(@NonNull Context context, boolean enabled) {
/*  927 */     SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
/*  928 */     editor.putBoolean("pref_print_document", enabled);
/*  929 */     editor.apply();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isPrintAnnotationsMode(@NonNull Context context) {
/*  940 */     return getDefaultSharedPreferences(context).getBoolean("pref_print_annotations", true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setPrintAnnotationsMode(@NonNull Context context, boolean enabled) {
/*  950 */     SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
/*  951 */     editor.putBoolean("pref_print_annotations", enabled);
/*  952 */     editor.apply();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isPrintSummaryMode(@NonNull Context context) {
/*  963 */     return getDefaultSharedPreferences(context).getBoolean("pref_print_summary", false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setPrintSummaryMode(@NonNull Context context, boolean enabled) {
/*  973 */     SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
/*  974 */     editor.putBoolean("pref_print_summary", enabled);
/*  975 */     editor.apply();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setMultipleTabs(@NonNull Context context, boolean enabled) {
/*  985 */     SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
/*  986 */     editor.putBoolean("pref_multiple_tabs", enabled);
/*  987 */     editor.apply();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean getMultipleTabs(@NonNull Context context) {
/*  997 */     return getDefaultSharedPreferences(context).getBoolean("pref_multiple_tabs", true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean getScreenStayLock(@NonNull Context context) {
/* 1008 */     return getDefaultSharedPreferences(context).getBoolean("pref_screen_stay_lock", false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean getMaintainZoomOption(@NonNull Context context) {
/* 1019 */     return getDefaultSharedPreferences(context).getBoolean("pref_maintain_zoom_option", true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean getImageSmoothing(@NonNull Context context) {
/* 1030 */     return getDefaultSharedPreferences(context).getBoolean("pref_image_smoothing", true);
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
/*      */   public static boolean getCopyAnnotatedTextToNote(@NonNull Context context) {
/* 1042 */     return getCopyAnnotatedTextToNote(context, false);
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
/*      */   public static boolean getCopyAnnotatedTextToNote(@NonNull Context context, boolean defaultValue) {
/* 1054 */     return getDefaultSharedPreferences(context).getBoolean("pref_copy_annotated_text_to_note", defaultValue);
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
/*      */   public static void setCopyAnnotatedTextToNote(@NonNull Context context, boolean value) {
/* 1066 */     SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
/* 1067 */     editor.putBoolean("pref_copy_annotated_text_to_note", value);
/* 1068 */     editor.apply();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean getStylusAsPen(@NonNull Context context) {
/* 1078 */     return getStylusAsPen(context, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean getStylusAsPen(@NonNull Context context, boolean defaultValue) {
/* 1089 */     return getDefaultSharedPreferences(context).getBoolean("pref_stylus_as_pen", defaultValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void updateStylusAsPen(@NonNull Context context, boolean enable) {
/* 1099 */     SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
/* 1100 */     editor.putBoolean("pref_stylus_as_pen", enable);
/* 1101 */     editor.apply();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean getInkSmoothing(@NonNull Context context) {
/* 1111 */     return getInkSmoothing(context, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean getInkSmoothing(@NonNull Context context, boolean defaultValue) {
/* 1122 */     return getDefaultSharedPreferences(context).getBoolean("pref_ink_smoothing", defaultValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setInkSmoothing(@NonNull Context context, boolean value) {
/* 1132 */     SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
/* 1133 */     editor.putBoolean("pref_ink_smoothing", value);
/* 1134 */     editor.apply();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean getAnnotListShowAuthor(@NonNull Context context) {
/* 1144 */     return getDefaultSharedPreferences(context).getBoolean("pref_annot_list_show_author", false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setAnnotListShowAuthor(@NonNull Context context, boolean showAuthor) {
/* 1154 */     SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
/* 1155 */     editor.putBoolean("pref_annot_list_show_author", showAuthor);
/* 1156 */     editor.apply();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Set<String> getFreeTextFonts(@NonNull Context context) {
/* 1166 */     return getDefaultSharedPreferences(context).getStringSet("pref_free_text_fonts", new HashSet());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setFreeTextFonts(@NonNull Context context, Set<String> value) {
/* 1176 */     SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
/* 1177 */     editor.putStringSet("pref_free_text_fonts", value);
/* 1178 */     editor.apply();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean getAllowPageChangeAnimation(@NonNull Context context) {
/* 1189 */     return getDefaultSharedPreferences(context).getBoolean("pref_page_change_animation", true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean getAllowPageChangeOnTap(@NonNull Context context) {
/* 1200 */     return getDefaultSharedPreferences(context).getBoolean("pref_allow_page_change_on_tap", true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setAllowPageChangeOnTap(@NonNull Context context, boolean changePage) {
/* 1210 */     SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
/* 1211 */     editor.putBoolean("pref_allow_page_change_on_tap", changePage);
/* 1212 */     editor.apply();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean getPageNumberOverlayOption(@NonNull Context context) {
/* 1222 */     return getDefaultSharedPreferences(context).getBoolean("pref_page_number_overlay", true);
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
/*      */   public static boolean getRememberLastPage(@NonNull Context context) {
/* 1234 */     return getDefaultSharedPreferences(context).getBoolean("pref_remember_last_page", true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean getEnableJavaScript(@NonNull Context context) {
/* 1245 */     return getDefaultSharedPreferences(context).getBoolean("pref_enable_javascript", true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setEnableJavaScript(@NonNull Context context, boolean value) {
/* 1255 */     SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
/* 1256 */     editor.putBoolean("pref_enable_javascript", value);
/* 1257 */     editor.apply();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean getShowAnnotationIndicator(@NonNull Context context) {
/* 1268 */     return getDefaultSharedPreferences(context).getBoolean("pref_show_annot_indicator", true);
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
/*      */   public static boolean getShowAnnotationIndicator(@NonNull Context context, boolean defaultValue) {
/* 1281 */     return getDefaultSharedPreferences(context).getBoolean("pref_show_annot_indicator", defaultValue);
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
/*      */   public static void setShowAnnotationIndicator(@NonNull Context context, boolean value) {
/* 1293 */     SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
/* 1294 */     editor.putBoolean("pref_show_annot_indicator", value);
/* 1295 */     editor.apply();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static PDFViewCtrl.PageViewMode getPageViewMode(@NonNull Context context) {
/* 1306 */     int mode = Integer.parseInt(getDefaultSharedPreferences(context).getString("pref_page_view_mode", KEY_PREF_PAGE_VIEW_MODE_DEFAULT_VALUE));
/*      */     
/* 1308 */     return PDFViewCtrl.PageViewMode.valueOf(mode);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setPageViewMode(@NonNull Context context, @NonNull PDFViewCtrl.PageViewMode value) {
/* 1317 */     SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
/* 1318 */     editor.putString("pref_page_view_mode", String.valueOf(value.getValue()));
/* 1319 */     editor.apply();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getLocalAppVersion(@NonNull Context context) {
/* 1329 */     return getDefaultSharedPreferences(context).getString("pref_local_app_version", "");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void updateLocalAppVersion(@NonNull Context context) {
/* 1339 */     SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
/* 1340 */     editor.putString("pref_local_app_version", getAppVersionName(context));
/* 1341 */     editor.apply();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean getAppUpdated(Context context) {
/* 1351 */     return !getAppVersionName(context).equalsIgnoreCase(getLocalAppVersion(context));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getAppVersionName(Context context) {
/* 1361 */     String versionName = "";
/*      */     try {
/* 1363 */       PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
/* 1364 */       if (info.versionName.length() > 0) {
/* 1365 */         versionName = info.versionName;
/*      */       }
/* 1367 */     } catch (PackageManager.NameNotFoundException e) {
/* 1368 */       versionName = "";
/*      */     } 
/* 1370 */     return versionName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean getStoragePermissionHasBeenAsked(@NonNull Context context) {
/* 1381 */     return getDefaultSharedPreferences(context).getBoolean("pref_storage_permission_asked", false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void updateStoragePermissionHasBeenAsked(@NonNull Context context, boolean value) {
/* 1391 */     SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
/* 1392 */     editor.putBoolean("pref_storage_permission_asked", value);
/* 1393 */     editor.apply();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean getStoragePermissionDenied(@NonNull Context context) {
/* 1404 */     return getDefaultSharedPreferences(context).getBoolean("pref_storage_permission_denied", false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void updateStoragePermissionDenied(@NonNull Context context, boolean value) {
/* 1414 */     SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
/* 1415 */     editor.putBoolean("pref_storage_permission_denied", value);
/* 1416 */     editor.apply();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean getDoubleRowToolbarInUse(@NonNull Context context) {
/* 1427 */     return getDefaultSharedPreferences(context).getBoolean("pref_double_row_toolbar_in_use", false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void updateDoubleRowToolbarInUse(@NonNull Context context, boolean value) {
/* 1437 */     SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
/* 1438 */     editor.putBoolean("pref_double_row_toolbar_in_use", value);
/* 1439 */     editor.apply();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean shouldShowHowToPaste(@NonNull Context context) {
/* 1449 */     int curCount = getDefaultSharedPreferences(context).getInt("copy_annot_teach_shown_count", 0);
/* 1450 */     if (curCount > 3) {
/* 1451 */       return false;
/*      */     }
/* 1453 */     SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
/* 1454 */     editor.putInt("copy_annot_teach_shown_count", curCount + 1);
/* 1455 */     editor.apply();
/* 1456 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getAuthorName(@NonNull Context context) {
/* 1467 */     return getDefaultSharedPreferences(context).getString("pref_author_name", "");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void updateAuthorName(@NonNull Context context, String value) {
/* 1477 */     SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
/* 1478 */     editor.putString("pref_author_name", value);
/* 1479 */     editor.apply();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean getAuthorNameHasBeenAsked(@NonNull Context context) {
/* 1490 */     return getDefaultSharedPreferences(context).getBoolean("pref_author_name_has_been_asked", false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setAuthorNameHasBeenAsked(@NonNull Context context) {
/* 1499 */     SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
/* 1500 */     editor.putBoolean("pref_author_name_has_been_asked", true);
/* 1501 */     editor.apply();
/*      */   }
/*      */   
/*      */   private static String getKeyPrefFromFileType(int fileType) {
/* 1505 */     switch (fileType) {
/*      */       case 0:
/* 1507 */         return "_pdf";
/*      */       case 1:
/* 1509 */         return "_docx";
/*      */       case 2:
/* 1511 */         return "_image";
/*      */     } 
/* 1513 */     return "";
/*      */   }
/*      */   
/*      */   private static boolean getFileTypeDefaultVisibility(String fileType, String suffix) {
/* 1517 */     if (suffix.equals("all") && (
/* 1518 */       fileType.equals("_pdf") || fileType.equals("_docx"))) {
/* 1519 */       return true;
/*      */     }
/* 1521 */     return false;
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
/*      */   public static boolean getFileFilter(@NonNull Context context, int fileType, String suffix) {
/* 1533 */     String fileTypeString = getKeyPrefFromFileType(fileType);
/* 1534 */     return getDefaultSharedPreferences(context).getBoolean("pref_file_type_filter_" + fileTypeString + suffix, 
/* 1535 */         getFileTypeDefaultVisibility(fileTypeString, suffix));
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
/*      */   public static void updateFileFilter(@NonNull Context context, int fileType, String suffix, boolean visibility) {
/* 1547 */     String fileTypeString = getKeyPrefFromFileType(fileType);
/* 1548 */     SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
/* 1549 */     editor.putBoolean("pref_file_type_filter_" + fileTypeString + suffix, visibility);
/* 1550 */     editor.apply();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void updateSavedFolderPickerLocation(@NonNull Context context, String location) {
/* 1560 */     SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
/* 1561 */     editor.putString("saved_folder_picker_location", location);
/* 1562 */     editor.apply();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getSavedFolderPickerFileType(@NonNull Context context) {
/* 1572 */     return getDefaultSharedPreferences(context).getInt("saved_folder_picker_file_type", -1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void updateSavedFolderPickerFileType(@NonNull Context context, int fileType) {
/* 1583 */     SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
/* 1584 */     editor.putInt("saved_folder_picker_file_type", fileType);
/* 1585 */     editor.apply();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getSavedFilePickerLocation(@NonNull Context context) {
/* 1595 */     return getDefaultSharedPreferences(context).getString("saved_file_picker_location", KEY_PREF_SAVED_FILE_PICKER_LOCATION_DEFAULT_VALUE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void updateSavedFilePickerLocation(@NonNull Context context, String location) {
/* 1606 */     SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
/* 1607 */     editor.putString("saved_file_picker_location", location);
/* 1608 */     editor.apply();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getSavedFilePickerFileType(@NonNull Context context) {
/* 1618 */     return getDefaultSharedPreferences(context).getInt("saved_file_picker_file_type", -1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void updateSavedFilePickerFileType(@NonNull Context context, int fileType) {
/* 1629 */     SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
/* 1630 */     editor.putInt("saved_file_picker_file_type", fileType);
/* 1631 */     editor.apply();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getSavedFolderPickerLocation(@NonNull Context context) {
/* 1641 */     return getDefaultSharedPreferences(context).getString("saved_folder_picker_location", KEY_PREF_SAVED_FOLDER_PICKER_LOCATION_DEFAULT_VALUE);
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
/*      */   public static int getGridSize(@NonNull Context context, String suffix) {
/* 1653 */     return getDefaultSharedPreferences(context).getInt("pref_grid_size_new_" + suffix, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void updateGridSize(@NonNull Context context, String suffix, int size) {
/* 1664 */     SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
/* 1665 */     editor.putInt("pref_grid_size_new_" + suffix, size);
/* 1666 */     editor.apply();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getSortMode(@NonNull Context context) {
/* 1676 */     return getDefaultSharedPreferences(context).getString("pref_sort", "name");
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
/*      */   public static int getAnnotListSortOrder(@NonNull Context context, @NonNull BaseAnnotationSortOrder defaultSortOrder) {
/* 1688 */     return getDefaultSharedPreferences(context).getInt("pref_annot_list_sort" + defaultSortOrder.getType(), defaultSortOrder
/* 1689 */         .getValue());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void updateSortMode(@NonNull Context context, String mode) {
/* 1699 */     SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
/* 1700 */     editor.putString("pref_sort", mode);
/* 1701 */     editor.apply();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void updateAnnotListSortOrder(@NonNull Context context, @NonNull BaseAnnotationSortOrder sortOrder) {
/* 1712 */     SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
/* 1713 */     editor.putInt("pref_annot_list_sort" + sortOrder.getType(), sortOrder.getValue());
/* 1714 */     editor.apply();
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
/*      */   public static int getThumbListFilterMode(@NonNull Context context, int defaultMode) {
/* 1726 */     return getDefaultSharedPreferences(context).getInt("pref_thumbnails_list_filter", defaultMode);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void updateThumbListFilterMode(@NonNull Context context, int mode) {
/* 1737 */     SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
/* 1738 */     editor.putInt("pref_thumbnails_list_filter", mode);
/* 1739 */     editor.apply();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean getShowOpenReadOnlySdCardFileWarning(@NonNull Context context) {
/* 1749 */     return getDefaultSharedPreferences(context).getBoolean("pref_show_open_read_only_sdcard_file_warning", true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void updateShowOpenReadOnlySdCardFileWarning(@NonNull Context context, boolean val) {
/* 1760 */     SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
/* 1761 */     editor.putBoolean("pref_show_open_read_only_sdcard_file_warning", val);
/* 1762 */     editor.apply();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getSavedExternalFolderUri(@NonNull Context context) {
/* 1772 */     return getDefaultSharedPreferences(context).getString("external_folder_uri", "");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void updateSavedExternalFolderUri(@NonNull Context context, String uri) {
/* 1783 */     SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
/* 1784 */     editor.putString("external_folder_uri", uri);
/* 1785 */     editor.apply();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getSavedExternalFolderTreeUri(@NonNull Context context) {
/* 1795 */     return getDefaultSharedPreferences(context).getString("external_folder_tree_uri", "");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void updateSavedExternalFolderTreeUri(@NonNull Context context, String uri) {
/* 1806 */     SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
/* 1807 */     editor.putString("external_folder_tree_uri", uri);
/* 1808 */     editor.apply();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getLocalFolderPath(@NonNull Context context) {
/* 1818 */     return getDefaultSharedPreferences(context).getString("pref_local_folder_path", "");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void updateLocalFolderPath(@NonNull Context context, String path) {
/* 1829 */     SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
/* 1830 */     editor.putString("pref_local_folder_path", path);
/* 1831 */     editor.apply();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getLocalFolderTree(@NonNull Context context) {
/* 1841 */     return getDefaultSharedPreferences(context).getString("pref_local_folder_tree", "");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void updateLocalFolderTree(@NonNull Context context, String path) {
/* 1852 */     SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
/* 1853 */     editor.putString("pref_local_folder_tree", path);
/* 1854 */     editor.apply();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getCustomColorModeBGColor(@NonNull Context context) {
/* 1864 */     return getDefaultSharedPreferences(context).getInt("pref_color_mode_custom_bgcolor", -1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setCustomColorModeBGColor(@NonNull Context context, int color) {
/* 1874 */     SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
/* 1875 */     editor.putInt("pref_color_mode_custom_bgcolor", color);
/* 1876 */     editor.apply();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getCustomColorModeTextColor(@NonNull Context context) {
/* 1886 */     return getDefaultSharedPreferences(context).getInt("pref_color_mode_custom_textcolor", -16777216);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setCustomColorModeTextColor(@NonNull Context context, int color) {
/* 1896 */     SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
/* 1897 */     editor.putInt("pref_color_mode_custom_textcolor", color);
/* 1898 */     editor.apply();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setColorModePresets(@NonNull Context context, String jsonSerializedArray) {
/* 1908 */     SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
/* 1909 */     editor.putString("pref_color_mode_presets", jsonSerializedArray);
/* 1910 */     editor.apply();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getColorModePresets(@NonNull Context context) {
/* 1920 */     return getDefaultSharedPreferences(context).getString("pref_color_mode_presets", "");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setSelectedColorModePreset(@NonNull Context context, int position) {
/* 1930 */     SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
/* 1931 */     editor.putInt("pref_color_mode_selected_preset", position);
/* 1932 */     editor.apply();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getSelectedColorModePreset(@NonNull Context context) {
/* 1942 */     return getDefaultSharedPreferences(context).getInt("pref_color_mode_selected_preset", -1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setLinkEditLastOption(@NonNull Context context, int option) {
/* 1952 */     SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
/* 1953 */     editor.putInt("pref_link_edit_option", option);
/* 1954 */     editor.apply();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getLinkEditLastOption(@NonNull Context context) {
/* 1964 */     return getDefaultSharedPreferences(context).getInt("pref_link_edit_option", 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isDarkMode(@NonNull Context context) {
/* 1974 */     return (getColorMode(context) == 3 || (
/* 1975 */       getColorMode(context) == 4 && Utils.isColorDark(getCustomColorModeBGColor(context))));
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
/*      */   public static void setAnnotStylePreset(@NonNull Context context, int annotType, int presetIndex, String annotStyleJSON) {
/* 1987 */     SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
/* 1988 */     editor.putString("pref_preset_ annot_style_" + annotType + "_" + presetIndex, annotStyleJSON);
/* 1989 */     editor.apply();
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
/*      */   public static String getAnnotStylePreset(@NonNull Context context, int annotType, int presetIndex) {
/* 2001 */     return getDefaultSharedPreferences(context).getString("pref_preset_ annot_style_" + annotType + "_" + presetIndex, KEY_PREF_PRESET_ANNOT_STYLE_DEFAULT_VALUE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getRecentColors(@NonNull Context context) {
/* 2011 */     return getDefaultSharedPreferences(context).getString("pref_recent_colors", "");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setRecentColors(@NonNull Context context, String colors) {
/* 2021 */     SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
/* 2022 */     editor.putString("pref_recent_colors", colors);
/* 2023 */     editor.apply();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getFavoriteColors(@NonNull Context context) {
/* 2033 */     return getDefaultSharedPreferences(context).getString("pref_favorite_colors", "");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setFavoriteColors(@NonNull Context context, String colors) {
/* 2043 */     SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
/* 2044 */     editor.putString("pref_favorite_colors", colors);
/* 2045 */     editor.apply();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setColorPickerPage(@NonNull Context context, int page) {
/* 2055 */     SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
/* 2056 */     editor.putInt("pref_color_picker_page", page);
/* 2057 */     editor.apply();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getColorPickerPage(@NonNull Context context) {
/* 2067 */     return getDefaultSharedPreferences(context).getInt("pref_color_picker_page", 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setAnnotToolbarVisibleAnnotTypes(@NonNull Context context, String visibleAnnotTypes) {
/* 2077 */     SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
/* 2078 */     editor.putString("pref_annot_toolbar_visible_annot_types", visibleAnnotTypes);
/* 2079 */     editor.apply();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getAnnotToolbarVisibleAnnotTypes(@NonNull Context context) {
/* 2089 */     return getDefaultSharedPreferences(context).getString("pref_annot_toolbar_visible_annot_types", "");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setOpenUrlAsyncCache(@NonNull Context context, String jsonString) {
/* 2098 */     SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
/* 2099 */     editor.putString("pref_open_url_last_page", jsonString);
/* 2100 */     editor.apply();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getOpenUrlAsyncCache(@NonNull Context context) {
/* 2109 */     return getDefaultSharedPreferences(context).getString("pref_open_url_last_page", "");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean getShowRageScrollingInfo(Context context) {
/* 2116 */     return getDefaultSharedPreferences(context).getBoolean("pref_show_rage_scrolling_info", true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void updateShowRageScrollingInfo(Context context, boolean value) {
/* 2124 */     SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
/* 2125 */     editor.putBoolean("pref_show_rage_scrolling_info", value);
/* 2126 */     editor.apply();
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
/*      */   public static boolean getUnlimitedTabsEnabled(@NonNull Context context, boolean defaultValue) {
/* 2140 */     return getDefaultSharedPreferences(context).getBoolean("pref_unlimited_tabs", defaultValue);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setSavedHomeToolbarMenu(@NonNull Context context, @NonNull String toolbarMenu) {
/* 2145 */     SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
/* 2146 */     editor.putString("toolbar_menu_home_saved", toolbarMenu);
/* 2147 */     editor.apply();
/*      */   }
/*      */   
/*      */   public static String getSavedHomeToolbarMenu(@NonNull Context context) {
/* 2151 */     return getDefaultSharedPreferences(context).getString("toolbar_menu_home_saved", null);
/*      */   }
/*      */   
/*      */   public static String getEditUriBackupFilePath(@NonNull Context context) {
/* 2155 */     return getDefaultSharedPreferences(context).getString("pref_edit_uri_backup_file_path", "");
/*      */   }
/*      */ 
/*      */   
/*      */   public static void updateEditUriBackupFilePath(@NonNull Context context, String filepath) {
/* 2160 */     SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
/* 2161 */     editor.putString("pref_edit_uri_backup_file_path", filepath);
/* 2162 */     editor.apply();
/*      */   }
/*      */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pd\\utils\PdfViewCtrlSettingsManager.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */