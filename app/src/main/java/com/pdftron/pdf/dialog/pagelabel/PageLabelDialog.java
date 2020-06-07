/*     */ package com.pdftron.pdf.dialog.pagelabel;
/*     */ 
/*     */ import android.app.AlertDialog;
/*     */ import android.app.Dialog;
/*     */ import android.content.Context;
/*     */ import android.content.DialogInterface;
/*     */ import android.os.Bundle;
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.widget.Button;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.fragment.app.DialogFragment;
/*     */ import androidx.fragment.app.FragmentActivity;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class PageLabelDialog
/*     */   extends DialogFragment
/*     */   implements PageLabelComponent.DialogButtonInteractionListener
/*     */ {
/*  24 */   public static final String TAG = PageLabelComponent.class.getName();
/*     */ 
/*     */   
/*     */   public static final String FROM_PAGE = "PageLabelDialogView_Initial_frompage";
/*     */ 
/*     */   
/*     */   public static final String TO_PAGE = "PageLabelDialogView_Initial_topage";
/*     */ 
/*     */   
/*     */   public static final String MAX_PAGE = "PageLabelDialogView_Initial_maxpage";
/*     */ 
/*     */   
/*     */   public static final String PREFIX = "PageLabelDialogView_Initial_prefix";
/*     */   
/*     */   @NonNull
/*     */   private PageLabelComponent pageLabelComponent;
/*     */ 
/*     */   
/*     */   public static PageLabelDialog newInstance(int fromPage, int toPage, int maxPage) {
/*  43 */     PageLabelDialog fragment = new PageLabelDialog();
/*  44 */     Bundle args = new Bundle();
/*  45 */     args.putInt("PageLabelDialogView_Initial_frompage", fromPage);
/*  46 */     args.putInt("PageLabelDialogView_Initial_topage", toPage);
/*  47 */     args.putInt("PageLabelDialogView_Initial_maxpage", maxPage);
/*  48 */     fragment.setArguments(args);
/*  49 */     return fragment;
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
/*     */   public static PageLabelDialog newInstance(int fromPage, int toPage, int maxPage, @Nullable String initialPrefix) {
/*  62 */     PageLabelDialog fragment = new PageLabelDialog();
/*  63 */     Bundle args = new Bundle();
/*  64 */     args.putInt("PageLabelDialogView_Initial_frompage", fromPage);
/*  65 */     args.putInt("PageLabelDialogView_Initial_topage", toPage);
/*  66 */     args.putInt("PageLabelDialogView_Initial_maxpage", maxPage);
/*  67 */     args.putString("PageLabelDialogView_Initial_prefix", initialPrefix);
/*  68 */     fragment.setArguments(args);
/*  69 */     return fragment;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
/*  74 */     super.onViewCreated(view, savedInstanceState);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
/*  81 */     FragmentActivity activity = getActivity();
/*  82 */     if (activity == null) {
/*  83 */       return super.onCreateDialog(savedInstanceState);
/*     */     }
/*     */     
/*  86 */     LayoutInflater inflater = activity.getLayoutInflater();
/*  87 */     View view = inflater.inflate(R.layout.fragment_container, null);
/*  88 */     ViewGroup container = (ViewGroup)view.findViewById(R.id.container);
/*     */     
/*  90 */     Bundle args = getArguments();
/*  91 */     if (args != null) {
/*  92 */       int fromPage = args.getInt("PageLabelDialogView_Initial_frompage");
/*  93 */       int toPage = args.getInt("PageLabelDialogView_Initial_topage");
/*  94 */       int maxPage = args.getInt("PageLabelDialogView_Initial_maxpage");
/*  95 */       String prefix = args.getString("PageLabelDialogView_Initial_prefix");
/*  96 */       this.pageLabelComponent = (prefix == null) ? new PageLabelComponent(activity, container, fromPage, toPage, maxPage, this) : new PageLabelComponent(activity, container, fromPage, toPage, maxPage, prefix, this);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 101 */       this.pageLabelComponent = new PageLabelComponent(activity, container, 1, 1, this);
/*     */     } 
/*     */ 
/*     */     
/* 105 */     AlertDialog.Builder builder = new AlertDialog.Builder((Context)getActivity());
/* 106 */     builder.setView(view)
/* 107 */       .setTitle(R.string.page_label_setting_title)
/* 108 */       .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener()
/*     */         {
/*     */           public void onClick(DialogInterface dialog, int which) {
/* 111 */             PageLabelDialog.this.pageLabelComponent.completeSettings();
/* 112 */             PageLabelDialog.this.dismiss();
/*     */           }
/* 115 */         }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
/*     */         {
/*     */           public void onClick(DialogInterface dialog, int which) {
/* 118 */             PageLabelDialog.this.dismiss();
/*     */           }
/*     */         });
/* 121 */     return (Dialog)builder.create();
/*     */   }
/*     */   
/*     */   private void setPositiveButtonEnabled(boolean enabled) {
/* 125 */     AlertDialog dialog = (AlertDialog)getDialog();
/* 126 */     Button posButton = (dialog == null) ? null : dialog.getButton(-1);
/* 127 */     if (posButton != null) {
/* 128 */       posButton.setEnabled(enabled);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void disallowSave() {
/* 134 */     setPositiveButtonEnabled(false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void allowSave() {
/* 139 */     setPositiveButtonEnabled(true);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\dialog\pagelabel\PageLabelDialog.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */