/*     */ package com.pdftron.pdf.controls;
/*     */ 
/*     */ import android.app.AlertDialog;
/*     */ import android.app.Dialog;
/*     */ import android.content.Context;
/*     */ import android.content.DialogInterface;
/*     */ import android.os.Bundle;
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.widget.AdapterView;
/*     */ import android.widget.ArrayAdapter;
/*     */ import android.widget.ImageView;
/*     */ import android.widget.ListAdapter;
/*     */ import android.widget.ListView;
/*     */ import android.widget.RadioButton;
/*     */ import android.widget.TextView;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.fragment.app.DialogFragment;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.AnalyticsParam;
/*     */ import com.pdftron.pdf.widget.InertSwitch;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
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
/*     */ public class UserCropSelectionDialogFragment
/*     */   extends DialogFragment
/*     */ {
/*     */   public static final int MODE_AUTO_CROP = 0;
/*     */   public static final int MODE_MANUAL_CROP = 1;
/*     */   public static final int MODE_RESET_CROP = 2;
/*     */   private static final String KEY_ITEM_TEXT = "item_crop_mode_picker_list_text";
/*  58 */   private UserCropSelectionDialogFragmentListener mUserCropSelectionDialogFragmentListener = null;
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
/*     */   private boolean mHasAction = false;
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
/*     */   public static UserCropSelectionDialogFragment newInstance() {
/*  85 */     return new UserCropSelectionDialogFragment();
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
/*     */   public void onDismiss(DialogInterface dialog) {
/*  97 */     if (this.mUserCropSelectionDialogFragmentListener != null) {
/*  98 */       this.mUserCropSelectionDialogFragmentListener.onUserCropSelectionDialogFragmentDismiss();
/*     */     }
/* 100 */     super.onDismiss(dialog);
/* 101 */     if (!this.mHasAction) {
/* 102 */       AnalyticsHandlerAdapter.getInstance().sendEvent(56, 
/* 103 */           AnalyticsParam.cropPageParam(4));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public Dialog onCreateDialog(Bundle savedInstanceState) {
/* 113 */     AlertDialog.Builder builder = new AlertDialog.Builder((Context)getActivity());
/* 114 */     LayoutInflater inflater = getActivity().getLayoutInflater();
/* 115 */     View view = inflater.inflate(R.layout.fragment_user_crop_selection_dialog, null);
/* 116 */     builder.setView(view);
/*     */     
/* 118 */     ListView cropModeListView = (ListView)view.findViewById(R.id.fragment_user_crop_slection_dialog_listview);
/* 119 */     cropModeListView.setItemsCanFocus(false);
/*     */     
/* 121 */     CropModeEntryAdapter cropModeEntryAdapter = new CropModeEntryAdapter((Context)getActivity(), getViewModeList());
/* 122 */     cropModeListView.setAdapter((ListAdapter)cropModeEntryAdapter);
/*     */     
/* 124 */     cropModeListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
/*     */         {
/*     */           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
/* 127 */             UserCropSelectionDialogFragment.this.mHasAction = true;
/* 128 */             UserCropSelectionDialogFragment.this.dismiss();
/* 129 */             if (UserCropSelectionDialogFragment.this.mUserCropSelectionDialogFragmentListener != null) {
/* 130 */               UserCropSelectionDialogFragment.this.mUserCropSelectionDialogFragmentListener.onUserCropMethodSelected(position);
/*     */             }
/* 132 */             if (position == 0) {
/* 133 */               AnalyticsHandlerAdapter.getInstance().sendEvent(56, 
/* 134 */                   AnalyticsParam.cropPageParam(1));
/* 135 */             } else if (position == 2) {
/* 136 */               AnalyticsHandlerAdapter.getInstance().sendEvent(56, 
/* 137 */                   AnalyticsParam.cropPageParam(3));
/*     */             } 
/*     */           }
/*     */         });
/*     */     
/* 142 */     return (Dialog)builder.create();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUserCropSelectionDialogFragmentListener(UserCropSelectionDialogFragmentListener listener) {
/* 150 */     this.mUserCropSelectionDialogFragmentListener = listener;
/*     */   }
/*     */   public static interface UserCropSelectionDialogFragmentListener {
/*     */     void onUserCropMethodSelected(int param1Int);
/*     */     
/*     */     void onUserCropSelectionDialogFragmentDismiss(); }
/*     */   
/*     */   private class CropModeEntryAdapter extends ArrayAdapter<HashMap<String, Object>> { CropModeEntryAdapter(Context context, List<HashMap<String, Object>> entries) {
/* 158 */       super(context, 0, entries);
/* 159 */       this.mContext = context;
/* 160 */       this.mEntries = entries;
/*     */     }
/*     */     private Context mContext;
/*     */     private List<HashMap<String, Object>> mEntries;
/*     */     
/*     */     @NonNull
/*     */     public View getView(int position, View convertView, @NonNull ViewGroup parent) {
/*     */       ViewHolder holder;
/* 168 */       if (convertView == null) {
/* 169 */         convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_item_view_mode_picker_list, parent, false);
/*     */         
/* 171 */         holder = new ViewHolder();
/* 172 */         holder.icon = (ImageView)convertView.findViewById(R.id.item_view_mode_picker_list_icon);
/* 173 */         holder.text = (TextView)convertView.findViewById(R.id.item_view_mode_picker_list_text);
/* 174 */         holder.radioButton = (RadioButton)convertView.findViewById(R.id.item_view_mode_picker_list_radiobutton);
/* 175 */         holder.switchButton = (InertSwitch)convertView.findViewById(R.id.item_view_mode_picker_list_switch);
/*     */         
/* 177 */         convertView.setTag(holder);
/*     */       } else {
/*     */         
/* 180 */         holder = (ViewHolder)convertView.getTag();
/*     */       } 
/*     */       
/* 183 */       HashMap<String, Object> map = this.mEntries.get(position);
/* 184 */       holder.icon.setVisibility(8);
/* 185 */       holder.text.setText((String)map.get("item_crop_mode_picker_list_text"));
/* 186 */       holder.radioButton.setVisibility(8);
/* 187 */       holder.switchButton.setVisibility(8);
/*     */       
/* 189 */       return convertView;
/*     */     }
/*     */     
/*     */     private class ViewHolder {
/*     */       protected ImageView icon;
/*     */       protected TextView text;
/*     */       protected RadioButton radioButton;
/*     */       protected InertSwitch switchButton;
/*     */       
/*     */       private ViewHolder() {} } }
/*     */   
/*     */   private List<HashMap<String, Object>> getViewModeList() {
/* 201 */     List<HashMap<String, Object>> viewModesList = new ArrayList<>();
/*     */     
/* 203 */     HashMap<String, Object> mapSinglePage = new HashMap<>();
/* 204 */     mapSinglePage.put("item_crop_mode_picker_list_text", getActivity().getResources().getString(R.string.user_crop_selection_auto_crop));
/* 205 */     viewModesList.add(mapSinglePage);
/*     */     
/* 207 */     HashMap<String, Object> mapFacing = new HashMap<>();
/* 208 */     mapFacing.put("item_crop_mode_picker_list_text", getActivity().getResources().getString(R.string.user_crop_selection_manual_crop));
/* 209 */     viewModesList.add(mapFacing);
/*     */     
/* 211 */     HashMap<String, Object> mapFacingCover = new HashMap<>();
/* 212 */     mapFacingCover.put("item_crop_mode_picker_list_text", getActivity().getResources().getString(R.string.user_crop_selection_remove_crop));
/* 213 */     viewModesList.add(mapFacingCover);
/*     */     
/* 215 */     return viewModesList;
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\controls\UserCropSelectionDialogFragment.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */